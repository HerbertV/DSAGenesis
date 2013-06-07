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
package dsagenesis.core.config.ui;

import jhv.component.LabelResource;
import jhv.swing.gridbag.GridBagConstraintsFactory;
import dsagenesis.core.config.GenesisConfig;

/**
 * Tab Panel for the Database Configuration.
 */
public class TemplateConfigTabPanel 
		extends AbstractConfigTabPanel 
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 * 
	 * @param frame
	 */
	public TemplateConfigTabPanel(ConfigFrame frame) 
	{
		super(frame);
		
		GenesisConfig conf = GenesisConfig.getInstance();
		this.loadLabels();
		this.gbcFactory = new GridBagConstraintsFactory(this, this.gbc, 2);
		
		this.gbcFactory.addInfoPanel(
				labelResource.getProperty("info.title", "info.title"), 
				labelResource.getProperty("info.message", "info.message"), 
				0
			);
		
		// TODO insert ui elements for template setup
		
		
		this.gbcFactory.nextLine();	
		this.gbcFactory.addEmptyPanel(GridBagConstraintsFactory.CURRENT);
	}

	// ============================================================================
	//  Functions
	// ============================================================================
	
	
	@Override
	public void loadLabels() 
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		
		labelResource = new LabelResource(
				this,
				conf.getLanguage(), 
				"resources/labels"
			);
	}

	@Override
	public boolean hasContentChanged()
	{
		return hasContentChanged;
	}
	
	@Override
	public void contentSaved() 
	{
		hasContentChanged = false;
	}

	
	@Override
	public void saveSetup() 
	{
		// TODO
	}

	@Override
	public void loadSetup() 
	{
		// TODO
	}

}
