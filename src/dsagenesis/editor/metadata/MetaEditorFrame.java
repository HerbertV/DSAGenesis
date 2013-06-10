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
package dsagenesis.editor.metadata;

import java.awt.BorderLayout;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.config.IGenesisConfigKeys;
import dsagenesis.core.ui.AbstractGenesisFrame;
import dsagenesis.core.ui.StatusBar;

public class MetaEditorFrame 
		extends AbstractGenesisFrame 
{
	// ============================================================================
	//  Constants
	// ============================================================================
			
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	/**
	 * for status messages
	 */
	private StatusBar statusBar;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor.
	 */
	public MetaEditorFrame()
	{
		super(IGenesisConfigKeys.KEY_WIN_META);
		
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(3);
		borderLayout.setHgap(3);
		
		this.setTitle(
				GenesisConfig.getInstance().getAppTitle()
					+ " - "
					+ labelResource.getProperty("title", "title")
			);
		
		
		// TODO init gui
		
		this.statusBar = new StatusBar();
		this.statusBar.setStatus("",
				StatusBar.STATUS_WORKING
			);
		getContentPane().add(this.statusBar, BorderLayout.SOUTH);
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
