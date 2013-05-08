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
package dsagenesis.editor.metadata;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.config.IGenesisConfigKeys;
import dsagenesis.core.ui.AbstractGenesisFrame;

public class MetaEditorFrame 
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
	public MetaEditorFrame()
	{
		super(
				GenesisConfig.getInstance().getAppTitle() + " - Meta Data Editor",
				GenesisConfig.APP_ICON,
				IGenesisConfigKeys.KEY_WIN_META		
			);
	}

	

	// ============================================================================
	//  Functions
	// ============================================================================
		
	@Override
	public boolean isSaved() 
	{
		// TODO
		return true;
	}
}
