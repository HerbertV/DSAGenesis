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
package dsagenesis.core.config.ui;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.config.IGenesisConfigKeys;
import dsagenesis.core.ui.AbstractGenesisFrame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import jhv.component.LabelResource;

/**
 * SetupFrame for configuring the users presets. 
 */
public class ConfigFrame
		extends AbstractGenesisFrame
		implements ActionListener
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;
	
	public static final String ACMD_SAVE = "save";
	public static final String ACMD_RESET = "reset";
	
	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	/**
	 * all panels from the tab.
	 */
	private AbstractConfigTabPanel[] tabPanels;
	

	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor.
	 * 
	 */
	public ConfigFrame()
	{
		super(IGenesisConfigKeys.KEY_WIN_SETUP);
		
		this.loadLabels();
		this.setTitle(
				GenesisConfig.getInstance().getAppTitle()
					+ " - "
					+ labelResource.getProperty("title", "title")
			);
		this.setSize(500, 400);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// TABS
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		AbstractConfigTabPanel paneGeneral = new GeneralConfigTabPanel();
		tabbedPane.addTab(
				labelResource.getProperty("tabGeneral", "tabGeneral"), 
				null, 
				paneGeneral, 
				null
			);
		
		JPanel paneDebug = new JPanel();
		tabbedPane.addTab(
				labelResource.getProperty("tabDebug", "tabDebug"), 
				null, 
				paneDebug, 
				null
			);
		
		JPanel paneDB = new JPanel();
		tabbedPane.addTab(
				labelResource.getProperty("tabDB", "tabDB"), 
				null, 
				paneDB, 
				null
			);
		
		JPanel paneTemplates = new JPanel();
		tabbedPane.addTab(
				labelResource.getProperty("tabTemplate", "tabTemplate"), 
				null, 
				paneTemplates, 
				null
			);
		
		tabPanels = new AbstractConfigTabPanel[tabbedPane.getTabCount()];
		tabPanels[0] = paneGeneral;
		
		
		// BUTTONS
		JPanel paneButtons = new JPanel();
		getContentPane().add(paneButtons, BorderLayout.SOUTH);
		
		JButton btnReset = new JButton(
				labelResource.getProperty("btnReset", "btnReset") 
			);
		btnReset.setActionCommand(ACMD_RESET);
		paneButtons.add(btnReset);
		
		JButton btnSave = new JButton(
				labelResource.getProperty("btnSave", "btnSave")
			);
		btnSave.setActionCommand(ACMD_SAVE);
		paneButtons.add(btnSave);
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
		for( int i=0; i<tabPanels.length; i++ )
			if( tabPanels[i].hasContentChanged() )
				return true;
		
		return false;
	}
	
	@Override
	public void contentSaved() 
	{
		for( int i=0; i<tabPanels.length; i++ )
			tabPanels[i].contentSaved();
	}
	

	@Override
	public void applyConfig()
	{
		// we don't need the default handling
	}
	
	@Override
	public void saveConfig()
	{
		// we don't need the default handling
	}


	


	@Override
	public void actionPerformed(ActionEvent ae)
	{
		// TODO Auto-generated method stub
		// TODO save: call save from all tabs and resetcontent saved
		
		
		// TODO reset: call load from all tabs
	}


	
}
