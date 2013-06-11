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
package dsagenesis.core.util.logic;

import java.sql.SQLException;
import java.util.Vector;

import jhv.util.debug.logger.ApplicationLogger;


/**
 * Selection
 * 
 * Some Advantages/Disadvantages,etc. have sub selections
 * where the user must choose one.
 * 
 * This class represents limits the selection to a subset of a DB Table
 * There are 2 types of selections: user based and table based.
 * 
 * A user based selection has some default values but can be extended
 * on-the-fly if you acquire it for your hero.
 * 
 * A table based selection allows only the predefined values it gets
 * from the DB.
 */
public class Selection
		implements ISyntax 
{

	// ============================================================================
	//  Constants
	// ============================================================================
	
	/**
	 * version of the selection format.
	 * can be used to update selection strings.
	 */
	public static final String VERSION = "1.0";
	
	public static final String TYPE_USER = "USR";
	
	public static final String TYPE_TABLE = "TAB";
	
	public static final String PREFIX_TYPE = "_stype_[";
	
	public static final String PREFIX_SELECTION = "_sel_[";

	
	// ============================================================================
	//  Variables
	// ============================================================================

	/**
	 * the selection type USR or TAB
	 */
	private String type;
	
	/**
	 * if type U stores the presets
	 */
	private Vector<String> userSelections;
	
	/**
	 * if type T contains the table name
	 */
	private String tableName;
	
	/**
	 * optional if you want only a subset of the table
	 */
	private String columnName;
	
	/**
	 * optional if you want only a subset of the table
	 */
	private String columnValue;
	
	/**
	 * to reduce db access and query only once
	 */
	private boolean queryWasDone = false;
	

	// ============================================================================
	//  Constructors
	// ============================================================================

	/**
	 * Empty Constructor
	 */
	public Selection() 
	{
		this.type = null;
	}

	/**
	 * Constructor 2.
	 * 
	 * @param dbString
	 */
	public Selection(String dbString)
	{
		this();
		
		if( dbString != null )
			parseStringFromDB(dbString);
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================

	@Override
	public void parseStringFromDB(String dbString) 
	{
		if( !dbString.startsWith(PREFIX_LOGIC+PREFIX_VERSION) )
		{
			ApplicationLogger.logError("Not a Selection:\n"+dbString);
			return;
		}
		
		int start = PREFIX_LOGIC.length()
				+ PREFIX_VERSION.length();
		int end = dbString.indexOf(PARENTHESIS_CLOSE,start);
		String version = dbString.substring(start,end);
		
		if( !version.equals(VERSION) )
		{
			ApplicationLogger.logWarning(
					"Selection has Version "
						+ version 
						+ " but needs "
						+ VERSION
					);
// TODO implement update
			ApplicationLogger.logWarning(
					"TODO implement update!"
				);
		}
		
		// extract type
		start = dbString.indexOf(PREFIX_TYPE,end)
						+ PREFIX_TYPE.length();
		end = dbString.indexOf(PARENTHESIS_CLOSE,start);
		this.type = dbString.substring(start,end);
		
		// extract selections
		start = dbString.indexOf(PREFIX_SELECTION,end)
				+ PREFIX_TYPE.length();
		end = dbString.lastIndexOf(PARENTHESIS_CLOSE);
		
		String[] selections =
				dbString.substring(start, end).split(Character.toString(ARRAY_SEPARATOR));
		
		if( this.type.equals(TYPE_USER) )
		{
			parseUserSelection(selections);
		} else if( this.type.equals(TYPE_TABLE) ) {
			parseTableSelection(selections);
		} 
	}
	
	/**
	 * parseUserSelection
	 * 
	 * @param dbString
	 */
	private void parseUserSelection(String[] selections)
	{
		userSelections = new Vector<String>();
		
		for( int i=0; i<selections.length; i++ )
			userSelections.add(selections[i]);
	}
	
	/**
	 * parseTableSelection
	 * 
	 * @param dbString
	 */
	private void parseTableSelection(String[] selections)
	{
		tableName = selections[0];
		columnName = "";
		columnValue = "";
		
		if( selections.length > 1 )
			columnName = selections[1];
		
		if( selections.length > 2 )
			columnValue = selections[2];
	}
	
	
	
	@Override
	public void queryLabels() 
			throws SQLException 
	{
		if( type.equals(TYPE_USER) )
			return;
		
		if( queryWasDone )
			return;
		
		// TODO Auto-generated method stub
		
		queryWasDone = true;
	}

	@Override
	public String renderStringForDB() 
	{
		if( isEmpty() ) 
			return null;		

		String str = PREFIX_LOGIC
				+ PREFIX_VERSION
				+ VERSION
				+ PARENTHESIS_CLOSE
				+ PREFIX_TYPE
				+ this.type
				+ PARENTHESIS_CLOSE
				+ PREFIX_SELECTION;
	
		if( type.equals(TYPE_USER) )
		{
			for( int i=0; i<userSelections.size(); i++ )
			{
				str += userSelections.get(i);
				if( i < (userSelections.size() - 1) )
					str += ARRAY_SEPARATOR;
			}
			
		} else if( type.equals(TYPE_TABLE) )	{
			str += tableName;
			if( !columnName.equals("") )
				str += ARRAY_SEPARATOR
					+ columnName;
			
			if( !columnValue.equals("") )
				str += ARRAY_SEPARATOR
					+ columnValue;
		}
		str += PARENTHESIS_CLOSE;
		
		return str;
	}

	@Override
	public String renderStringForCell()
	{
		if( isEmpty() )
			return "S:{null}";
		
		String str = type + ":{";
		
		if( type.equals(TYPE_USER) )
		{
			for( int i=0; i<userSelections.size(); i++ )
			{
				str += userSelections.get(i);
				if( i < (userSelections.size() - 1) )
					str += ",";
			}
		} else if( type.equals(TYPE_TABLE) ) {
			
		}
		
		str += "}";
		return str;
	}

	@Override
	public String renderStringForCellTooltip() 
	{
		if( isEmpty() ) 
			return null;		

		String str = "<html>"+ type + ":<br>";
		
		if( type.equals(TYPE_USER) )
		{
			for( int i=0; i<userSelections.size(); i++ )
			{
				str += userSelections.get(i);
				if( i < (userSelections.size() - 1) )
					str += "<br>";
			}
		} else if( type.equals(TYPE_TABLE) ) {
			
		}
		
		str += "</html>";
		return str;
	}
	
	@Override
	public boolean isEmpty()
	{
		if( type == null ) 
			return true;		

		return false;
	}
	
	
	public String getType() 
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Vector<String> getUserSelections() 
	{
		return userSelections;
	}

	public void setUserSelections(Vector<String> userSelections) 
	{
		this.userSelections = userSelections;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName) 
	{
		this.tableName = tableName;
	}

	public String getColumnName() 
	{
		return columnName;
	}

	public void setColumnName(String columnName)
{
		this.columnName = columnName;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue)
	{
		this.columnValue = columnValue;
	}
	
	/**
	 * toString
	 */
	public String toString()
	{
		return this.renderStringForCell();
	}

}
