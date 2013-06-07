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

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.config.IGenesisConfigKeys;
import dsagenesis.core.ui.AbstractGenesisFrame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;

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
		
		this.setTitle(
				GenesisConfig.getInstance().getAppTitle()
					+ " - "
					+ labelResource.getProperty("title", "title")
			);
		this.setResizable(false);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// TABS
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		AbstractConfigTabPanel paneGeneral = new GeneralConfigTabPanel(this);
		tabbedPane.addTab(
				labelResource.getProperty("tabGeneral", "tabGeneral"), 
				null, 
				paneGeneral, 
				null
			);
		
		AbstractConfigTabPanel paneDebug = new DebugConfigTabPanel(this);
		tabbedPane.addTab(
				labelResource.getProperty("tabDebug", "tabDebug"), 
				null, 
				paneDebug, 
				null
			);
		
		AbstractConfigTabPanel paneDB = new DBConfigTabPanel(this);
		tabbedPane.addTab(
				labelResource.getProperty("tabDB", "tabDB"), 
				null, 
				paneDB, 
				null
			);
		
		AbstractConfigTabPanel paneTemplates = new TemplateConfigTabPanel(this);
		tabbedPane.addTab(
				labelResource.getProperty("tabTemplate", "tabTemplate"), 
				null, 
				paneTemplates, 
				null
			);
		
		tabPanels = new AbstractConfigTabPanel[tabbedPane.getTabCount()];
		tabPanels[0] = paneGeneral;
		tabPanels[1] = paneDebug;
		tabPanels[2] = paneDB;
		tabPanels[3] = paneTemplates;
		
		
		// BUTTONS
		JPanel paneButtons = new JPanel();
		getContentPane().add(paneButtons, BorderLayout.SOUTH);
		
		JButton btnReset = new JButton(
				labelResource.getProperty("btnReset", "btnReset") 
			);
		btnReset.setActionCommand(ACMD_RESET);
		btnReset.addActionListener(this);
		paneButtons.add(btnReset);
		
		JButton btnSave = new JButton(
				labelResource.getProperty("btnSave", "btnSave")
			);
		btnSave.setActionCommand(ACMD_SAVE);
		btnSave.addActionListener(this);
		paneButtons.add(btnSave);
		
		pack();
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================
	
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
		
		setUnsavedMarker(false);
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
	
	/**
	 * setUnsavedMarker
	 * 
	 * @param unsaved
	 */
	public void setUnsavedMarker(boolean unsaved)
	{
		String title = ConfigFrame.markUnsaved(this.getTitle(), unsaved);
		this.setTitle(title);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if( ae.getActionCommand().equals(ACMD_SAVE) )
		{
			for( int i=0; i<tabPanels.length; i++ )
				tabPanels[i].saveSetup();
			
			GenesisConfig.getInstance().saveUser();
			contentSaved();
			
		} else if( ae.getActionCommand().equals(ACMD_RESET) ) {
			
			GenesisConfig.getInstance().resetUser();
			
			for( int i=0; i<tabPanels.length; i++ )
				tabPanels[i].loadSetup();
			
			GenesisConfig.getInstance().saveUser();
			contentSaved();
		}
	}
	
}
