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
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import jhv.component.LabelResource;
import dsagenesis.core.config.GenesisConfig;

/**
 * Tab Panel for the Database Configuration.
 */
public class DBConfigTabPanel 
		extends AbstractConfigTabPanel 
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	private JTextField txtDBName;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 * 
	 * @param frame
	 */
	public DBConfigTabPanel(ConfigFrame frame) 
	{
		super(frame);
		
		this.loadLabels();
		GenesisConfig conf = GenesisConfig.getInstance();
		
		this.addInfoPanel(
				labelResource.getProperty("info.title", "info.title"), 
				labelResource.getProperty("info.message", "info.message"), 
				0, 
				0
			);
		JComponent[] comps = this.addLabeledFileChooser(
				labelResource.getProperty("lblChooser", "lblChooser"), 
				conf.getString(GenesisConfig.KEY_DB_FILE), 
				0, 
				1, 
				conf.getDBFile(), 
				new FileNameExtensionFilter("SQLite3 File", "s3db", "db") 
			);
		txtDBName = (JTextField)comps[1];
		txtDBName.getDocument().addDocumentListener(this);
		
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
				GenesisConfig.KEY_DB_FILE, 
				txtDBName.getText()
			);
	}

	@Override
	public void loadSetup() 
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		
		txtDBName.setText(
				conf.getString(GenesisConfig.KEY_DB_FILE)
			);
		
	}

}
