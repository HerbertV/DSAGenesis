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

public abstract class AbstractSQLTableModel
{
	// ============================================================================
	//  Variables
	// ============================================================================
		
	protected String dbTableName;
	
	protected String[]dbColumnNames;
	
	protected String prefix;
	
	protected boolean usesPrefix = true;
		
	/**
	 * getPrefix
	 * 
	 * prefix that is used for the ID and the column names
	 * 
	 * @return
	 */
	//public String getPrefix();
	
	/**
	 * getDBTableName
	 * 
	 * @return
	 */
	//public String getDBTableName();

	
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
