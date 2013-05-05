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
	
	public static GenesisConfig getInstance()
	{
		if( GenesisConfig.instance == null )
			GenesisConfig.instance = new GenesisConfig();
		
		return (GenesisConfig)GenesisConfig.instance;
	}
	
	@Override
	protected void setSystemDefaults() 
	{
		// TODO Auto-generated method stub

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
