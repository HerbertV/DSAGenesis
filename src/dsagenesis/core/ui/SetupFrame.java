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
package dsagenesis.core.ui;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.config.IGenesisConfigKeys;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;

/**
 * SetupFrame for configuring the users presets. 
 */
public class SetupFrame
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
	 * 
	 */
	public SetupFrame()
	{
		// TODO use other constructor and implement labels
		super(
				GenesisConfig.getInstance().getAppTitle() + " - Setup",
				IGenesisConfigKeys.KEY_WIN_SETUP
			);
		this.setSize(500, 400);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panGeneric = new JPanel();
		tabbedPane.addTab("Allgemein", null, panGeneric, null);
		
		JPanel panDebug = new JPanel();
		tabbedPane.addTab("Debug", null, panDebug, null);
		
		JPanel paDB = new JPanel();
		tabbedPane.addTab("Datenbank", null, paDB, null);
		
		JPanel panTemplates = new JPanel();
		tabbedPane.addTab("Templates", null, panTemplates, null);
		
		JPanel panButtons = new JPanel();
		getContentPane().add(panButtons, BorderLayout.SOUTH);
		
		JButton btnReset = new JButton("Zur\u00FCcksetzen");
		panButtons.add(btnReset);
		
		JButton btnSave = new JButton("Speichern");
		panButtons.add(btnSave);
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	@Override
	public boolean isSaved()
	{
		return true;
	}

	@Override
	public void applyConfig()
	{
		// we dont need the default handling
	}
	
	@Override
	public void saveConfig()
	{
		// we dont need the default handling
	}


	@Override
	public void loadLabels() 
	{
		// TODO Auto-generated method stub
		
	}
	
}
