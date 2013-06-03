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
package dsagenesis.editor.hero;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.config.IGenesisConfigKeys;
import dsagenesis.core.ui.AbstractGenesisFrame;

/**
 * JFrame for the Hero Editor
 */
public class HeroEditorFrame 
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
	public HeroEditorFrame()
	{
		super(
				GenesisConfig.getInstance().getAppTitle() + " - Hero Editor",
				IGenesisConfigKeys.KEY_WIN_HERO			
			);
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================
		
	@Override
	public boolean hasContentChanged() 
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void contentSaved()
	{
		// TODO Auto-generated method stub
		
	}

	
}
