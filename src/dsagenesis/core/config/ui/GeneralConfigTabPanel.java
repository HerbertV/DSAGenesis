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

import javax.swing.JComponent;
import javax.swing.JSpinner;

import jhv.component.LabelResource;
import dsagenesis.core.config.GenesisConfig;

/**
 * Tab Panel for the General Configuration.
 */
public class GeneralConfigTabPanel 
		extends AbstractConfigTabPanel 
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;

	// ============================================================================
	//  Variables
	// ============================================================================
	
	private JSpinner spinStartCP;
	private JSpinner spinMaxAttributeCP;
	private JSpinner spinMaxDisadvantageCP;
	private JSpinner spinMaxNegativeAttributeCP;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 */
	public GeneralConfigTabPanel() 
	{
		super();
		
		this.loadLabels();
		
		GenesisConfig conf = GenesisConfig.getInstance();
		
		this.addInfoPanel(
				labelResource.getProperty("info.title", "info.title"), 
				labelResource.getProperty("info.message", "info.message"), 
				0, 
				0
			);
		
		JComponent comps[] = this.addLabeledNumericSpinner(
				labelResource.getProperty("lblDefaultStartCP", "lblDefaultStartCP"), 
				conf.getInt(GenesisConfig.KEY_DEFAULT_START_CP), 
				0, 
				200, 
				1, 
				0, 
				1
			);
		spinStartCP = (JSpinner)comps[1];
		//TODO add change handler
		
		comps = this.addLabeledNumericSpinner(
				labelResource.getProperty("lblDefaultMaxAttributeCP", "lblDefaultMaxAttributeCP"), 
				conf.getInt(GenesisConfig.KEY_DEFAULT_MAX_ATTRIBUTE_CP), 
				0, 
				200, 
				1, 
				0, 
				2
			);
		spinMaxAttributeCP = (JSpinner)comps[1];
		//TODO add change handler
		
		comps = this.addLabeledNumericSpinner(
				labelResource.getProperty("lblDefaultMaxDisadvantageCP", "lblDefaultMaxDisadvantageCP"), 
				conf.getInt(GenesisConfig.KEY_DEFAULT_MAX_DISADVANTAGE_CP), 
				0, 
				200, 
				1, 
				0, 
				3
			);
		spinMaxDisadvantageCP = (JSpinner)comps[1];
		//TODO add change handler
		
		comps = this.addLabeledNumericSpinner(
				labelResource.getProperty("lblDefaultMaxNegativeAttributeCP", "lblDefaultMaxNegativeAttributeCP"), 
				conf.getInt(GenesisConfig.KEY_DEFAULT_MAX_NEGATIVEATTRIBUTE_CP), 
				0, 
				200, 
				1, 
				0, 
				4
			);
		spinMaxNegativeAttributeCP = (JSpinner)comps[1];
		//TODO spinMaxNegativeAttributeCP.addChangeListener(listener)
		
		
		this.addEmptyPanel(5);
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
				"labels"
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
		GenesisConfig conf = GenesisConfig.getInstance();
		conf.setUserProperty(
				GenesisConfig.KEY_DEFAULT_START_CP, 
				Integer.toString((Integer)spinStartCP.getValue())
			);
		conf.setUserProperty(
				GenesisConfig.KEY_DEFAULT_MAX_ATTRIBUTE_CP, 
				Integer.toString((Integer)spinMaxAttributeCP.getValue())
			);
		conf.setUserProperty(
				GenesisConfig.KEY_DEFAULT_MAX_DISADVANTAGE_CP, 
				Integer.toString((Integer)spinMaxDisadvantageCP.getValue())
			);
		conf.setUserProperty(
				GenesisConfig.KEY_DEFAULT_MAX_NEGATIVEATTRIBUTE_CP, 
				Integer.toString((Integer)spinMaxNegativeAttributeCP.getValue())
			);
	}

	@Override
	public void loadSetup() 
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		
		spinStartCP.setValue(
				conf.getInt(GenesisConfig.KEY_DEFAULT_START_CP)
			);
		spinMaxAttributeCP.setValue(
				conf.getInt(GenesisConfig.KEY_DEFAULT_MAX_ATTRIBUTE_CP)
			);
		spinMaxDisadvantageCP.setValue(
				conf.getInt(GenesisConfig.KEY_DEFAULT_MAX_DISADVANTAGE_CP)
			);
		spinMaxNegativeAttributeCP.setValue(
				conf.getInt(GenesisConfig.KEY_DEFAULT_MAX_NEGATIVEATTRIBUTE_CP)
			);
	}

}
