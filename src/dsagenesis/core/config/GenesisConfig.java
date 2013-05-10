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
package dsagenesis.core.config;

import jhv.util.config.AbstractConfig;
import jhv.util.config.IDefaultConfigKeys;


public class GenesisConfig 
		extends AbstractConfig 
		implements IDefaultConfigKeys, IGenesisConfigKeys
{

	// ============================================================================
	//  Variables
	// ============================================================================
		
	/**
	 * application version.
	 */
	public static String APP_VERSION = "0.1.0";
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	private GenesisConfig() 
	{
		this.loadSystem();
		this.loadUser();
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * getInstance
	 * 
	 * Access the instance.
	 * 
	 * @return
	 */
	public static GenesisConfig getInstance()
	{
		if( GenesisConfig.instance == null )
			GenesisConfig.instance = new GenesisConfig();
		
		return (GenesisConfig)GenesisConfig.instance;
	}
	
	/**
	 * getAppTitle
	 * 
	 * @return
	 */
	public String getAppTitle()
	{
		return this.getString(KEY_APPTITLE);
	}
	
	/**
	 * getAppIcon
	 * 
	 * @return
	 */
	public String getAppIcon()
	{
		return this.getString(KEY_APPICON);
	}
	
	/**
	 * getDebugLevel
	 * 
	 * @return
	 */
	public int getDebugLevel()
	{
		return this.getInt(KEY_DEBUG_LEVEL);
	}
	
	/**
	 * isLoggerEnabled
	 * 
	 * @return
	 */
	public boolean isLoggerEnabled()
	{
		return this.getBoolean(KEY_IS_LOGGER_ENABLED);
	}
	
	
	/**
	 * for the first launch the system defaults are created.
	 */
	@Override
	protected void setSystemDefaults() 
	{
		// Global defaults
		this.setSystemProperty(KEY_APPICON, "images/icons/appIcon64.png");
		this.setSystemProperty(KEY_APPTITLE, "DSA Genesis");
		this.setSystemProperty(KEY_DEBUG_LEVEL, "0");
		this.setSystemProperty(KEY_IS_LOGGER_ENABLED, "false");
		this.setSystemProperty(KEY_LANGUAGE, "de_DE");
		
		// Core Editor defaults
		this.setSystemProperty(KEY_WIN_BASE+"."+KEY_SIZE, "800,600");
		this.setSystemProperty(KEY_WIN_BASE+"."+KEY_ISFULLSCREEN, "false");
		this.setSystemProperty(KEY_WIN_BASE+"."+KEY_POSITION, "0,0");
		
		// Meta Editor defaults
		this.setSystemProperty(KEY_WIN_META+"."+KEY_SIZE, "800,600");
		this.setSystemProperty(KEY_WIN_META+"."+KEY_ISFULLSCREEN, "false");
		this.setSystemProperty(KEY_WIN_META+"."+KEY_POSITION, "0,0");
				
		// Meta Editor defaults
		this.setSystemProperty(KEY_WIN_HERO+"."+KEY_SIZE, "800,600");
		this.setSystemProperty(KEY_WIN_HERO+"."+KEY_ISFULLSCREEN, "false");
		this.setSystemProperty(KEY_WIN_HERO+"."+KEY_POSITION, "0,0");
		
		// path defaults
		this.setSystemProperty(KEY_PATH_DATA, "data");
		this.setSystemProperty(KEY_PATH_TEMPLATE, "templates");
		this.setSystemProperty(KEY_PATH_HERO, "helden");
		this.setSystemProperty(KEY_PATH_ARCHTYPE, "archetypen");
		this.setSystemProperty(KEY_PATH_RACE, "rassen");
		this.setSystemProperty(KEY_PATH_CULTURE, "kulturen");
		this.setSystemProperty(KEY_PATH_PROFESSION, "professionen");
		this.setSystemProperty(KEY_PATH_NAME, "namen");
		//TODO
		this.setSystemProperty(KEY_DB_FILE, "TODO");
		
		// default settings hero editor
		this.setSystemProperty(KEY_DEFAULT_START_GP, "110");
		this.setSystemProperty(KEY_DEFAULT_MAX_DISADVANTAGE_GP, "50");
		this.setSystemProperty(KEY_DEFAULT_MAX_ATTRIBUTE_GP, "100");
		this.setSystemProperty(KEY_DEFAULT_MAX_BADATTRIBUTE_GP, "30");
	}

	@Override
	protected void setUserDefaults() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resetUser() 
	{
		// TODO Auto-generated method stub

	}


}
