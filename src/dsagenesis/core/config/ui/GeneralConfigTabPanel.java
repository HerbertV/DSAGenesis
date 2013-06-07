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

import javax.swing.JPanel;
import javax.swing.JSpinner;

import jhv.component.LabelResource;
import jhv.swing.gridbag.GridBagConstraintsFactory;
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
	
	private JPanel colorPickerPositive;
	private JPanel colorPickerNegative;
	private JPanel colorPickerComment;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 * 
	 * @param frame
	 */
	public GeneralConfigTabPanel(ConfigFrame frame) 
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
		
		this.gbcFactory.nextLine();
		this.gbcFactory.addLabel(
				labelResource.getProperty("lblDefaultStartCP", "lblDefaultStartCP"), 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		this.gbcFactory.nextX();
		spinStartCP = this.gbcFactory.addNumericSpinner(
				conf.getInt(GenesisConfig.KEY_DEFAULT_START_CP), 
				0, 
				200, 
				1, 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		spinStartCP.addChangeListener(this);
				
		this.gbcFactory.nextLine();
		this.gbcFactory.addLabel(
				labelResource.getProperty("lblDefaultMaxAttributeCP", "lblDefaultMaxAttributeCP"), 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		this.gbcFactory.nextX();
		spinMaxAttributeCP = this.gbcFactory.addNumericSpinner(
				conf.getInt(GenesisConfig.KEY_DEFAULT_MAX_ATTRIBUTE_CP), 
				0, 
				200, 
				1, 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		spinMaxAttributeCP.addChangeListener(this);
		
		this.gbcFactory.nextLine();
		this.gbcFactory.addLabel(
				labelResource.getProperty("lblDefaultMaxDisadvantageCP", "lblDefaultMaxDisadvantageCP"), 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		this.gbcFactory.nextX();
		spinMaxDisadvantageCP = this.gbcFactory.addNumericSpinner(
				conf.getInt(GenesisConfig.KEY_DEFAULT_MAX_DISADVANTAGE_CP), 
				0, 
				200, 
				1, 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		spinMaxDisadvantageCP.addChangeListener(this);
		
		this.gbcFactory.nextLine();
		this.gbcFactory.addLabel(
				labelResource.getProperty("lblDefaultMaxNegativeAttributeCP", "lblDefaultMaxNegativeAttributeCP"), 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		this.gbcFactory.nextX();
		spinMaxNegativeAttributeCP = this.gbcFactory.addNumericSpinner(
				conf.getInt(GenesisConfig.KEY_DEFAULT_MAX_NEGATIVEATTRIBUTE_CP), 
				0, 
				200, 
				1, 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		spinMaxNegativeAttributeCP.addChangeListener(this);
		
		this.gbcFactory.nextLine();
		this.gbcFactory.addInfoPanel(
				labelResource.getProperty("colors.title", "colors.title"), 
				labelResource.getProperty("colors.message", "colors.message"), 
				GridBagConstraintsFactory.CURRENT
			);
		
		this.gbcFactory.nextLine();
		this.gbcFactory.addLabel(
				labelResource.getProperty("lblColorPostive", "lblColorPostive"), 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		this.gbcFactory.nextX();
		colorPickerPositive = this.gbcFactory.addColorPicker(
				labelResource.getProperty("dlgTitlePostive", "dlgTitlePostive"), 
				conf.getColor(GenesisConfig.KEY_COLOR_POSITIVE), 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		colorPickerPositive.addPropertyChangeListener("background", this);
		
		this.gbcFactory.nextLine();
		this.gbcFactory.addLabel(
				labelResource.getProperty("lblColorNegative", "lblColorNegative"), 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		this.gbcFactory.nextX();
		colorPickerNegative = this.gbcFactory.addColorPicker(
				labelResource.getProperty("dlgTitleNegative", "dlgTitleNegative"), 
				conf.getColor(GenesisConfig.KEY_COLOR_NEGATIVE), 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		colorPickerNegative.addPropertyChangeListener("background", this);
		
		this.gbcFactory.nextLine();
		this.gbcFactory.addLabel(
				labelResource.getProperty("lblColorComment", "lblColorComment"), 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		this.gbcFactory.nextX();
		colorPickerComment = this.gbcFactory.addColorPicker(
				labelResource.getProperty("dlgTitleComment", "dlgTitleComment"), 
				conf.getColor(GenesisConfig.KEY_COLOR_COMMENT), 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.CURRENT
			);
		colorPickerComment.addPropertyChangeListener("background", this);
		
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
		
		conf.setUserProperty(
				GenesisConfig.KEY_COLOR_POSITIVE,
				GenesisConfig.colorToHexString(colorPickerPositive.getBackground())
			);
		conf.setUserProperty(
				GenesisConfig.KEY_COLOR_NEGATIVE,
				GenesisConfig.colorToHexString(colorPickerNegative.getBackground())
			);
		conf.setUserProperty(
				GenesisConfig.KEY_COLOR_COMMENT,
				GenesisConfig.colorToHexString(colorPickerComment.getBackground())
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
		
		colorPickerPositive.setBackground(
				conf.getColor(GenesisConfig.KEY_COLOR_POSITIVE)
			);
		colorPickerNegative.setBackground(
				conf.getColor(GenesisConfig.KEY_COLOR_NEGATIVE)
			);
		colorPickerComment.setBackground(
				conf.getColor(GenesisConfig.KEY_COLOR_COMMENT)
			);
	}

	

}
