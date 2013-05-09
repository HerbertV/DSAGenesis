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
package dsagenesis.core.ui;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.config.IGenesisConfigKeys;

/**
 * SetupFrame for configuring the users presets. 
 */
public class SetupFrame
		extends AbstractGenesisFrame 
{
	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	private static final long serialVersionUID = 1L;

	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor.
	 * 
	 */
	public SetupFrame()
	{
		super(
				GenesisConfig.getInstance().getAppTitle() + " - Setup",
				IGenesisConfigKeys.KEY_WIN_SETUP
			);
		// TODO Auto-generated constructor stub
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	@Override
	public boolean isSaved()
	{
		return true;
	}

	@Override
	public void applyConfig()
	{
		// we dont need the default handling
	}
	
	@Override
	public void saveConfig()
	{
		// we dont need the default handling
	}
	
}
