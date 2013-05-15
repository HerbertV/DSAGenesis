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
package dsagenesis.core.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * static helper class for working with the Genesis Database
 * 
 * the functions are helpers for accessing 
 * CoreDataTableIndex
 * and 
 * TableColumnLabels 
 */
public class TableIndexFactory 
{
	
	/**
	 * getPrefixForTable
	 * 
	 * @param tablename
	 * @return
	 * @throws SQLException
	 */
	public static String getPrefixForTable(String tablename) 
			throws SQLException
	{
		String query = 
				"SELECT ti_prefix from CoreDataTableIndex WHERE ti_table_name="
					+ tablename;
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		if( rs.next() )
			rs.getString("ti_prefix");
		
		return "";
	}
	
	/**
	 * getLabelForTable
	 * 
	 * @param tablename
	 * @return
	 * @throws SQLException
	 */
	public static String getLabelForTable(String tablename) 
			throws SQLException
	{
		String query = 
				"SELECT ti_label from CoreDataTableIndex WHERE ti_table_name="
						+ tablename;
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		if( rs.next() )
			rs.getString("ti_label");
		
		return "";
	}
	
	/**
	 * getColumnLabelsForTable
	 * 
	 * @param tablename
	 * @return
	 * @throws SQLException
	 */
	public static String[] getColumnLabelsForTable(String tablename)
			throws SQLException
	{
		String query = 
				"SELECT tcl_column_name, tcl_label from TableColumnLabels WHERE ti_table_name="
						+ tablename;
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		if( rs.next() )
			rs.getString("ti_label");
				
		return new String[0];
	}
	
}
