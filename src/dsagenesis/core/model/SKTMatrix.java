/*
 *  ___  ___   _      ___                 _    
 * |   \/ __| /_\    / __|___ _ _  ___ __(_)___
 * | |) \__ \/ _ \  | (_ / -_) ' \/ -_|_-< (_-<
 * |___/|___/_/ \_\  \___\___|_||_\___/__/_/__/
 *
 * -----------------------------------------------------------------------------
 * @author: Herbert Veitengruber 
 * @version: 1.0.0
 * -----------------------------------------------------------------------------
 *
 * Copyright (c) 2013 Herbert Veitengruber 
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/mit-license.php
 */
package dsagenesis.core.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import jhv.util.debug.logger.ApplicationLogger;

import dsagenesis.core.model.sql.SKT;
import dsagenesis.core.model.sql.SKTShifts;
import dsagenesis.core.sqlite.DBConnector;
import dsagenesis.core.sqlite.TableHelper;


/**
 * SKTMatrix
 * 
 * is needed everywhere when it comes to calculating cost for level all the
 * different attributes, talents and spells.
 * 
 * This class is a singleton.
 *
 * The matrix' values are stored in the database as SKT.
 * but it also use the SKTShifts Table for generating the SKT Column.
 */
public class SKTMatrix 
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	/**
	 * for SKT columns lower "A" 
	 */
	public static final char STAR = '*';
	
	/**
	 * for every star the adventure point costs are reduced by 2.
	 * but not lower than 1.
	 */
	public static final int COST_REDUCTION_PER_STAR = 2;

	// ============================================================================
	//  Variables
	// ============================================================================
		
	/**
	 * singleton instance.
	 */
	private static SKTMatrix instance;
	
	/**
	 * SKT sql model
	 */
	private SKT skt;
	
	/**
	 * SKTShifts sql model
	 */
	private SKTShifts sktShifts;
	
	/**
	 * stored here to reduce DB queries.
	 * since we will need it a lot.
	 */
	private Vector<Vector<String>> columnNamesAndLabels;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor
	 */
	private SKTMatrix()
	{
		try
		{
			this.skt = new SKT();
			this.columnNamesAndLabels = TableHelper.getColumnLabelsForTable(
					skt.getDBTableName()
				);
		
		} catch( SQLException e ) {
			ApplicationLogger.logError(e);
		}
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================
		
	/**
	 * getInstance
	 * 
	 * @return
	 */
	public static SKTMatrix getInstance()
	{
		if( instance == null )
			instance = new SKTMatrix();
		
		return instance;
	}
	
	/**
	 * getColumnLabels
	 * 
	 * @return
	 */
	public Vector<String> getColumnLabels()
	{
		Vector<String> labels = new Vector<String>();
		
		for( int i=0; i<this.columnNamesAndLabels.size(); i++ )
			labels.addElement(this.columnNamesAndLabels.get(i).get(1));
		
		return labels;
	}
	
	/**
	 * getColumnNamesAndLabels
	 * 
	 * @return
	 */
	public Vector<Vector<String>> getColumnNamesAndLabels()
	{
		return this.columnNamesAndLabels;
	}
	
	/**
	 * getColumnNamesAndLabelsAsObject
	 * 
	 * since there is no cast
	 * 
	 * @return
	 */
	public Vector<Vector<Object>> getColumnNamesAndLabelsAsObject()
	{
		Vector<Vector<Object>> objs = new Vector<Vector<Object>>();
		for( int i=0; i<columnNamesAndLabels.size(); i++ )
		{
			Vector<Object> obj = new Vector<Object>();
			obj.add(columnNamesAndLabels.get(i).get(0));
			obj.add(columnNamesAndLabels.get(i).get(1));
			objs.add(obj);
		}
		
		return objs;
	}
	
	/**
	 * getColumnLabelForName
	 * 
	 * @param dbColName
	 * @return
	 */
	public String getColumnLabelForName(String dbColName)
	{
		for( int i=0; i<this.columnNamesAndLabels.size(); i++ )
			if( this.columnNamesAndLabels.get(i).get(0).equals(dbColName) )
				return this.columnNamesAndLabels.get(i).get(1);
		
		return "";
	}
	
	
	/**
	 * countStars
	 * 
	 * @param col
	 * @return
	 */
	private int countStars(String col)
	{
		int count = 0;
		for( int i=0; i < col.length(); i++ )
			if( col.charAt(i) == STAR )
				count++;
	
		return count;
	}
	
	/**
	 * removeStars
	 * 
	 * @param col
	 * @return
	 */
	private String removeStars(String col)
	{
		return col.substring(0,col.indexOf(STAR));
	}
	
	/**
	 * addStars
	 * 
	 * @param col
	 * @param count
	 * 
	 * @return
	 */
	private String addStars(String col, int count)
	{
		for( int i=0; i < count; i++ )
			col += STAR;
		
		return col;
	}
	
	/**
	 * getSKTColumnIndex
	 * 
	 * @param col can be label or db name but without stars.
	 * @param stars number of stars.
	 * @return
	 */
	private int getSKTColumnIndex(
			String col, 
			int stars
		) 
	{
		int idx = 0;
		boolean found = false;
		
		if( stars == 0 )
		{
			for( int i=0; i<this.columnNamesAndLabels.size(); i++ )
			{
				if( this.columnNamesAndLabels.get(i).get(0).equals(col) )
				{
					idx = i;
					found = true;
					break;
				} else if ( this.columnNamesAndLabels.get(i).get(1).equals(col) ) {
					found = true;
					idx = i;
					break;
				}
			}
		} else {
			idx = -1*stars;
			found = true;
		}
		
        if( !found )
            ApplicationLogger.logError("SKT Column ["+col+"] not found! return 0");
        
        return idx;
    }
	
	/**
	 * shiftUp
	 * 
	 * shifts by the give value (positive or negative)
	 * 
	 * @param sktColumn can be label or db name.
	 * @param shiftValue
	 * @param returnAsName 
	 * 		if true the column name is returned.
	 * 		if false the column label is returned.
	 * @return
	 */
	public String shiftByValue(
			String sktColumn, 
			int shiftValue,
			boolean returnAsName
		)
	{
		// prepare everything
		int stars = countStars(sktColumn);
		String colWithoutStars = sktColumn;
		if( stars > 0)
			colWithoutStars = removeStars(sktColumn);
		
		int idx = getSKTColumnIndex(colWithoutStars,stars);
		
		// add value positive or negative
		idx += shiftValue;
		
		// upper end is fixed
		if( idx >= this.columnNamesAndLabels.size() )
			idx = this.columnNamesAndLabels.size()-1;
		
		// adjust stars
		if( idx <= 0 )
		{
			stars = -1*idx;
			idx = 0;
		}
		
		String newCol;
		if( returnAsName ) {
			newCol = this.columnNamesAndLabels.get(idx).get(0);
		} else {
			newCol = this.columnNamesAndLabels.get(idx).get(1);
		}
		return addStars(newCol,stars);
	}
	
	/**
	 * shiftUp
	 * 
	 * shifts one column up, e.g. A becomes B
	 * 
	 * @param sktColumn can be label or db name.
	 * @param returnAsName 
	 * 		if true the column name is returned.
	 * 		if false the column label is returned.
	 * @return
	 */
	public String shiftUp(
			String sktColumn, 
			boolean returnAsName
		) 
	{
		return shiftByValue(sktColumn,1,returnAsName);
	}
	    
	/**
	 * shiftDown
	 * 
	 * shifts one column down, e.g. B becomes A
	 * 
	 * @param sktColumn can be label or db name.
	 * @param returnAsName 
	 * 		if true the column name is returned.
	 * 		if false the column label is returned.
	 * @return
	 */
	public String shiftDown(
			String sktColumn, 
			boolean returnAsName
		) 
	{
		return shiftByValue(sktColumn,-1,returnAsName);
	}
	
	/**
	 * getAdventurePointCosts
	 * 
	 * for activation costs from = -1 and to = 0;
	 * 
	 * @param from 
	 * @param to
	 * @param sktColumn must be column name
	 * 
	 * @return
	 */
	public int getAdventurePointCosts(
			int from, 
			int to, 
			String sktColumn
		) 
	{
		if( from > to )
		{
			ApplicationLogger.logError("SKTMatrix.getAdventurePointCosts from > to!");
			return -1;
		}
		int stars = countStars(sktColumn);
		String colWithoutStars = sktColumn;
		if( stars > 0)
			colWithoutStars = removeStars(sktColumn);
		
		int value = 0;
        
		String query = "SELECT SUM("
				+ colWithoutStars 
				+ ") AS ap FROM "
				+ skt.getDBTableName()
				+ " WHERE ID BETWEEN "
				+ (from+1) + " AND " + to; 
		try 
		{
			ResultSet rs = DBConnector.getInstance().executeQuery(query);
			
			if( rs.next() )
			{
				value = rs.getInt("ap");
				value -= COST_REDUCTION_PER_STAR*stars;
				
				if( value < 1 )
					value = 1;
				
				rs.close();
			}
		} catch( SQLException e ) {
			// do nothing
		}
		
		return value;
    }
	
	// TODO skt shifting
}
