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
package dsagenesis.editor.coredata.view;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.config.IGenesisConfigKeys;
import dsagenesis.core.view.AbstractGenesisFrame;

/**
 * JFrame for the Core Data Editor.
 */
public class CoreEditorFrame 
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
	 */
	public CoreEditorFrame()
	{
		super(
				GenesisConfig.getInstance().getAppTitle() + " - Core Editor",
				GenesisConfig.APP_ICON,
				IGenesisConfigKeys.KEY_WIN_BASE				
			);
	}

	

	// ============================================================================
	//  Functions
	// ============================================================================
		
	/**
	 * core editor saves on the fly. since it accesses the sqlite database.
	 * so it returns always true.
	 */
	@Override
	public boolean isSaved() 
	{
		return true;
	}
}
