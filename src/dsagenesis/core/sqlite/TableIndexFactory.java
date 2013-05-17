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
package dsagenesis.core.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	public static String getPrefixForTable(String tablename) 
			throws SQLException
	{
		String query = 
				"SELECT ti_prefix, ti_table_name from CoreDataTableIndex WHERE ti_table_name='"
					+ tablename+"'";
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		if( rs.next() )
			return rs.getString("ti_prefix");
		
		return "";
	}
	
	/**
	 * getLabelForTable
	 * 
	 * @param tablename
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	public static String getLabelForTable(String tablename) 
			throws SQLException
	{
		String query = 
				"SELECT ti_label, ti_table_name from CoreDataTableIndex WHERE ti_table_name="
						+ tablename;
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		if( rs.next() )
			return rs.getString("ti_label");
		
		return "";
	}
	
	/**
	 * getColumnLabelsForTable
	 * 
	 * @param tablename
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
// TODO change to Vector<String>
	public static String[] getColumnLabelsForTable(String tablename)
			throws SQLException
	{
		String query = 
				"SELECT tcl_column_name, tcl_label, tlc_table_name from TableColumnLabels WHERE tlc_table_name="
						+ tablename;
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		
		ArrayList<String> arr = new ArrayList<String>();
		while( rs.next() )
			arr.add(rs.getString("ti_label"));
		
		String[] sarr = new String[arr.size()];
		sarr = arr.toArray(sarr);
		
		return sarr;
	}
// TODO get db version
	
}
