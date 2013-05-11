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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jhv.util.debug.logger.ApplicationLogger;

/**
 * Connector for sqlite database.
 */
public class DBConnector 
{
	// ============================================================================
	//  Variables
	// ============================================================================
	
	/**
	 * singleton instance
	 */
	private static DBConnector instance;
	
	/**
	 * open connection
	 */
	private Connection connection;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor
	 */
	private DBConnector()
	{
		try {
			// load the sqlite-JDBC driver using the current class loader
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			ApplicationLogger.logFatalError("SQLite JDBC Driver not found.");
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
	public static DBConnector getInstance()
	{
		if( instance == null )
			instance = new DBConnector();
		
		return instance;
	}
	
	/**
	 * openConnection
	 * 
	 * @param dbfile absolute path
	 */
	public void openConnection(String dbfile)
	{
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
		} catch (SQLException e) {
			ApplicationLogger.logFatalError(e);
		}
	}
	
	/**
	 * closeConnection
	 */
	public void closeConnection()
	{
		try
		{
			if( connection != null )
				connection.close();
			
		} catch( SQLException e ) {
			ApplicationLogger.logFatalError(e);
		}
	}
	
	public void doQuery(String query)
	{
		//TODO
	}

}
