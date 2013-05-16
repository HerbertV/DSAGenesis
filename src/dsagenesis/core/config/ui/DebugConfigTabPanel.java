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

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import jhv.component.LabelResource;
import dsagenesis.core.config.GenesisConfig;

/**
 * Tab Panel for the General Configuration.
 */
public class DebugConfigTabPanel 
		extends AbstractConfigTabPanel 
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;

	private static final String[] debugLevelStrings = {
			"OFF",
			"ALL",
			"INFO",
			"DEBUG",
			"WARNING",
			"ERROR",
			"FATAL ERROR"	
		};
	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	private JCheckBox cbxLoggerEnabled;
	private JComboBox<String> comboDebugLevel;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public DebugConfigTabPanel() 
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
		
		cbxLoggerEnabled = this.addCheckbox(
				labelResource.getProperty("cbxLoggerEnabled", "cbxLoggerEnabled"), 
				conf.getBoolean(GenesisConfig.KEY_IS_LOGGER_ENABLED), 
				0, 
				1
			);
		
		JComponent[] comps = this.addLabeledComboBox(
				labelResource.getProperty("comboDebugLevel", "comboDebugLevel"), 
				debugLevelStrings, 
				0, 
				2, 
				debugLevelStrings[conf.getInt(GenesisConfig.KEY_DEBUG_LEVEL)] 
			);
		
		comboDebugLevel = (JComboBox<String>)comps[1];
		
		// TODO Add changed listener
		
		this.addEmptyPanel(3);
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
				GenesisConfig.KEY_IS_LOGGER_ENABLED, 
				Boolean.toString(cbxLoggerEnabled.isSelected())
			);
		conf.setUserProperty(
				GenesisConfig.KEY_DEBUG_LEVEL, 
				Integer.toString(comboDebugLevel.getSelectedIndex())
			);
		
	}

	@Override
	public void loadSetup() 
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		
		cbxLoggerEnabled.setSelected(
				conf.getBoolean(GenesisConfig.KEY_IS_LOGGER_ENABLED)
			);
		comboDebugLevel.setSelectedIndex(
				conf.getInt(GenesisConfig.KEY_DEBUG_LEVEL)
			);
		
	}

}
