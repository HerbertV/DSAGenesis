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
package dsagenesis.core.model.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.TableColumn;

import jhv.util.debug.logger.ApplicationLogger;

import dsagenesis.core.model.xml.AbstractGenesisModel;
import dsagenesis.core.sqlite.DBConnector;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;
import dsagenesis.editor.coredata.table.cell.BasicCellRenderer;

/**
 * AbstractSQLTableModel
 * 
 * Abstract base for accessing the database for a table.
 * Has also functions for working with our JTable implementation. 
 * 
 * All implementations represent the whole table not a single object.
 * So in most cases the Names of these classes are plural.
 */
public abstract class AbstractSQLTableModel
{
	// ============================================================================
	//  Variables
	// ============================================================================
	
	/**
	 * the internal database column names.
	 */
	protected Vector<String>dbColumnNames;
	
	/**
	 * prefix used by column names (except ID)
	 * and for the ID generation if usesPrefix = true;
	 * 
	 * prefix must end with _
	 */
	protected String prefix = "";
	
	/**
	 * 
	 */
	protected boolean usesPrefix = true;
	
	
	// ============================================================================
	//  Functions
	// ============================================================================
		
	/**
	 * getDBTableName
	 * 
	 * @return
	 */
	public abstract String getDBTableName();
	
	/**
	 * getPrefix
	 * 
	 * prefix that is used for the ID and the column names
	 * if there is no prefix it returns ""
	 * 
	 * @return
	 */
	public String getPrefix()
	{
		return prefix;
	}
	
	/**
	 * usesPrefix
	 * 
	 * @return
	 */
	public boolean usesPrefix()
	{
		return usesPrefix;
	}
	
	/**
	 * getDBColumnNames
	 * 
	 * @return
	 */
	public Vector<String> getDBColumnNames()
	{
		return dbColumnNames;
	}
	
	/**
	 * getColumnLabels
	 * 
	 * Returns the labels used to render the JTable Header.
	 * by default it returns the internal db column names.
	 *  
	 * @return
	 */
	public Vector<String> getColumnLabels()
	{
		return dbColumnNames;
	}
	
	
	/**
	 * isEditable
	 * 
	 * @return
	 */
	public abstract boolean isEditable();
	
	/**
	 * setupJTableColumnModels
	 * 
	 * This function assigns custom renders and editors to each column.
	 * needs override
	 * 
	 * @param ceframe
	 * @param cetable
	 */
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		TableColumn currColumn;
        
        // ID col 0
        currColumn = cetable.getColumnModel().getColumn(0);
        currColumn.setPreferredWidth(20);
        currColumn.setCellRenderer(new BasicCellRenderer());
        
	}
	
	/**
	 * queryList
	 * 
	 * returns all entries sort by ID
	 * 
	 * @return
	 */
	public Vector<Vector<Object>> queryList()
	{
		String query= "SELECT * FROM "
				+ getDBTableName()
				+ " ORDER BY ID ASC";
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);	
		
		ApplicationLogger.logInfo(
					this.getClass().getSimpleName()
					+ ".queryList time: "
					+ DBConnector.getInstance().getQueryTime() 
					+ " ms"
			);
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		if( rs == null )
			return data;
		
		try 
		{
			int colCount = rs.getMetaData().getColumnCount();
			while( rs.next() )
			{
				Vector<Object> row = new Vector<Object>();
				for( int col=1; col <= colCount; col++) 
				{
					row.add(rs.getObject(col));
				}
				data.add(row);
			}
			
		} catch (SQLException e) {
			ApplicationLogger.logError(e);
		}
		
		return data;
	}
	
	/**
	 * getRow
	 * 
	 * returns a single row/entry by it's ID as Abstract GenesisModel.
	 * 
	 * @return
	 */
	public abstract AbstractGenesisModel getRow(String id);
	
	
	/**
	 * 
	 */
	//public void newDBEntry();
		
	/**
	 * 
	 */
	//public void deleteDBEntry();
	
	/**
	 * 
	 */
	//public void updateDBEntry();
}
