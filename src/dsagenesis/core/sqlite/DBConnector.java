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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteJDBCLoader;

import jhv.util.debug.logger.ApplicationLogger;

/**
 * Connector for sqlite database.
 * 
 * Depends on SQLite JDBC Driver:
 * https://bitbucket.org/xerial/sqlite-jdbc/overview
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
	
	/**
	 * time for the last query.
	 */
	private long queryTime = 0;
	
	/**
	 * filename of the open DB
	 */
	private String openDBFilename;
		
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
			
			ApplicationLogger.separator();
			ApplicationLogger.logInfo(
					"SQLite JDBC Driver ("
						+ SQLiteJDBCLoader.getVersion()
						+ ") initilized."
					);
			ApplicationLogger.separator();
			
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
	 * convertBooleanFromDB
	 * 
	 * since sqlite has no boolean we have to convert it.
	 * it can be "true" or 1 for true and "false" or 0 for false.
	 * 
	 * also the ResultSet.getBoolean does not work.
	 * 
	 * @param val
	 * 
	 * @return
	 */
	public static boolean convertBooleanFromDB(Object val)
	{
		if( val.equals(1) || val.equals("true") )
			return true;
		
		return false;
	}
	
	/**
	 * convertBooleanForDB
	 * 
	 * converts a boolean back for sqlite DB.
	 * 
	 * @see convertBooleanFromDB
	 * 
	 * @param val
	 * 
	 * @return
	 */
	public static int convertBooleanForDB(boolean val)
	{
		if( val )
			return 1;
		
		return 0;
	}
	
	/**
	 * getDBFilename
	 * 
	 * @return
	 */
	public String getDBFilename()
	{
		return this.openDBFilename;
	}
	
	/**
	 * hasConnection
	 * 
	 * @return
	 */
	public boolean hasConnection()
	{
		if( connection != null )
			return true;
		
		return false;
	}
	
	/**
	 * getConnection
	 * 
	 * @return
	 */
	public Connection getConnection()
	{
		return connection;
	}
	
	/**
	 * openConnection
	 * 
	 * opens an existing DB file or creates a new empty DB.
	 * 
	 * @param dbfile absolute path
	 * @param readonly
	 */
	public void openConnection(String dbfile, boolean readonly)
	{
		try {
			ApplicationLogger.logInfo("open DB: "+dbfile);
			
			SQLiteConfig sqlconf = new SQLiteConfig();
			sqlconf.setReadOnly(readonly);   
			sqlconf.enableLoadExtension(true);
			sqlconf.setSharedCache(true);
			sqlconf.useLegacyFileFormat(false);
			sqlconf.enforceForeignKeys(true);
			
			connection = DriverManager.getConnection(
					"jdbc:sqlite:" + dbfile,
					sqlconf.toProperties()
				);
			
			openDBFilename = dbfile;
			
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
			{
				connection.close();
				connection = null;
				openDBFilename = null;
				
				ApplicationLogger.logInfo("closed DB connection.");
			}
		} catch( SQLException e ) {
			ApplicationLogger.logFatalError(e);
		}
	}
	
	/**
	 * executeQuery
	 * 
	 * @param query
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String query)
			throws SQLException
	{
		if( connection == null )
		{
			ApplicationLogger.logError(
					"DBConnector.executeQuery: Cannot query unless connected to a Database"
				);
			return null;
		}
		queryTime = 0;
		
		long startTime = System.currentTimeMillis();
		ResultSet rs = null;
		
		try
		{
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  
			rs = statement.executeQuery(query);
			
		} catch( SQLException e ) {
			ApplicationLogger.logError(e);
			ApplicationLogger.logError("Query String was:\n" +query);
			throw new SQLException(e);
		}
		
		queryTime = System.currentTimeMillis() - startTime;
		return rs;
	}
	
	/**
	 * executeUpdate
	 * 
	 * @param update
	 * @return
	 * @throws SQLException 
	 */
	public void executeUpdate(String update) 
			throws SQLException
	{
		if( connection == null )
		{
			ApplicationLogger.logError(
					"DBConnector.executeUpdate: Cannot update unless connected to a Database"
				);
			return;
		}
		queryTime = 0;
		
		long startTime = System.currentTimeMillis();
		Statement statement = connection.createStatement();
		
		try
		{
			statement.setQueryTimeout(30);  
			statement.executeUpdate(update);
		} catch( SQLException e ) {
			ApplicationLogger.logError(e);
			ApplicationLogger.logError("Update String was:\n" +update);
			statement.cancel();
			statement.close();
			throw new SQLException(e);
		} 
		
		queryTime = System.currentTimeMillis() - startTime;
		statement.close();
	}
	
	/**
	 * executeFile
	 * 
	 * executes the sql script from a file. 
	 * Till now only updates are possible (no queries)
	 * 
	 * @param filename
	 * @throws SQLException 
	 */
	public void executeFile(String filename) 
			throws SQLException 
	{
		String query = "";
		InputStream in;
		InputStreamReader isr;
		
		try 
    	{
    		in = getClass().getClassLoader().getResourceAsStream(filename);
    		isr = new InputStreamReader(in);
    		
        } catch( NullPointerException e1 ) {
        	// no resource found try to load from file
        	System.out.println("load sql file: "+ filename);
        	try {
        		in = new FileInputStream(filename);
        		isr = new InputStreamReader(in);
        		
        	} catch( Exception e2 ) {
        		// both loading tries failed
        		// so no property file is available
        		ApplicationLogger.logError("sql file: " + filename + " not found!");
        		return;
        	}
        }
		
		try 
		{
			BufferedReader br = new BufferedReader(isr);
			String read = br.readLine();
			while( read != null )
			{
				query += read + "\n";
			    read = br.readLine();
			}
			br.close();
			isr.close();
			in.close();
			
		} catch (IOException e) {
			ApplicationLogger.logError(e);
			return;
		}
		
		executeUpdate(query);
	}
	
	/**
	 * isDBEmpty
	 * 
	 * returns true if the db contains no tables.
	 * 
	 * @return
	 */
	public boolean isDBEmpty()
	{
		try
		{
			ResultSet rs = this.executeQuery(
					"SELECT count(*) FROM sqlite_master WHERE type='table' AND name='CoreDataTableIndex'"
				);
			if( rs == null || rs.getInt(1) == 0 )
				return true;
			
		} catch( SQLException e ) {
			ApplicationLogger.logError(e);
			return true;
		}
		
		return false;
	}
	
	/**
	 * getQueryTime
	 * 
	 * returns the time in ms the last query took.
	 * 
	 * @return
	 */
	public long getQueryTime()
	{
		return queryTime;
	}
	
}
