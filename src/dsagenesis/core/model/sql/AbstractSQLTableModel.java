/*
 *  __  __      
 * /\ \/\ \  __________   
 * \ \ \_\ \/_______  /\   
 *  \ \  _  \  ____/ / /  
 *   \ \_\ \_\ \ \/ / / 
 *    \/_/\/_/\ \ \/ /  
 *             \ \  /
 *              \_\/
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

import java.util.Vector;

import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;

/**
 * AbstractSQLTableModel
 * 
 * Abstract base for accessing the database for a table.
 * Has also functions for working with our JTable implementation. 
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
	 * isEditable
	 * 
	 * @return
	 */
	public abstract boolean isEditable();
	
	/**
	 * setupJTableColumnModels
	 * 
	 * This function assigns custom renders and editors to each column.
	 * 
	 * @param ceframe
	 * @param cetable
	 */
	public abstract void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		);
	
	/**
	 * queryList
	 * 
	 * returns all entries sort by ID
	 * 
	 * @return
	 */
	public abstract Vector<Vector<Object>> queryList();
	
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
