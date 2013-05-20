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
import java.util.Vector;

/**
 * static helper class for working with the Genesis Database
 * 
 * the functions are helpers for accessing 
 * CoreDataTableIndex
 * and 
 * TableColumnLabels 
 */
public class TableHelper 
{
	/**
	 * getDBVersion
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	public static String getDBVersion() 
			throws SQLException
	{
		String query = 
				"SELECT ID, ver_major, ver_minor from CoreDataVersion WHERE ID=0";
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		if( rs.next() )
		{
			return rs.getString("ver_major")
				+ "."
				+ rs.getString("ver_minor");
		}
		return "0.0";
	}
	
	/**
	 * getDBLanguage
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	public static String getDBLanguage() 
			throws SQLException
	{
		String query = 
				"SELECT ID, ver_language from CoreDataVersion WHERE ID=0";
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		if( rs.next() )
		{
			return rs.getString("ver_language");
		}
		return "";
	}
	
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
				"SELECT ti_label, ti_table_name from CoreDataTableIndex WHERE ti_table_name='"
						+ tablename	+ "'";
		
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
	public static Vector<Vector<String>> getColumnLabelsForTable(String tablename)
			throws SQLException
	{
		String query = 
				"SELECT tcl_column_name, tcl_label, tlc_table_name from TableColumnLabels WHERE tlc_table_name='"
						+ tablename	+ "'";
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		
		Vector<Vector<String>> vec = new Vector<Vector<String>>();
		while( rs.next() )
		{
			Vector<String> vec2 = new Vector<String>(2);
			vec2.add(rs.getString("tcl_column_name"));
			vec2.add(rs.getString("tcl_label"));
			vec.add(vec2);
		}
		return vec;
	}
	
	/**
	 * getNoteForTable
	 * 
	 * @param tablename
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	public static String getNoteForTable(String tablename) 
			throws SQLException
	{
		String query = 
				"SELECT ti_note, ti_table_name from CoreDataTableIndex WHERE ti_table_name='"
						+ tablename	+ "'";
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		if( rs.next() )
			return rs.getString("ti_note");
		
		return "";
	}
		
	/**
	 * isTableEditable
	 * 
	 * @param tablename
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	public static boolean isTableEditable(String tablename) 
			throws SQLException
	{
		String query = 
				"SELECT ti_editable, ti_table_name from CoreDataTableIndex WHERE ti_table_name='"
					+ tablename+"'";
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		if( rs.next() )
			return rs.getBoolean("ti_editable");
		
		return false;
	}
	
}
