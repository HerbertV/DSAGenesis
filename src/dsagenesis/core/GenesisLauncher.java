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
package dsagenesis.core;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dsagenesis.core.config.GenesisConfig;

import jhv.swing.launcher.AbstractLauncher;
import jhv.util.debug.logger.ApplicationLogger;

/**
 * Launcher for DSA Genesis
 *
 */
public class GenesisLauncher 
		extends AbstractLauncher 
		implements ActionListener
{
	
	// ============================================================================
	//  Constants
	// ============================================================================
	
	public static final String ACMD_LAUNCH_CORE = "launchCoreEditor";
	
	public static final String ACMD_LAUNCH_META = "launchMetaEditor";
	
	public static final String ACMD_LAUNCH_HERO = "launchHeroEditor";
	
	public static final String ACMD_SETUP = "setup";
	
	public static final String ACMD_EXIT = "exit";
	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	private static final long serialVersionUID = 1L;
	
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	
	public GenesisLauncher(
			String title, 
			String iconfile, 
			String bgfile
		)
	{
		super(title, iconfile, bgfile);
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================
		

	/**
	 * STATIC MAIN
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try {
			// Set System L&F
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
	    } catch( UnsupportedLookAndFeelException e ) {
	       e.printStackTrace();
	    } catch( ClassNotFoundException e ) {
	    	e.printStackTrace();
	    } catch( InstantiationException e ) {
	    	e.printStackTrace();
	    } catch( IllegalAccessException e ) {
	    	e.printStackTrace();
	    }
		
		long time = System.currentTimeMillis();
		
		// init config
		GenesisConfig conf = GenesisConfig.getInstance();
		
		// init logger
		if( conf.isLoggerEnabled() )
		{
			ApplicationLogger.getInstance(
					conf.getAppTitle(), 
					time, 
					conf.getDebugLevel()
				);
			
			ApplicationLogger.logInfo("DSA Genesis started.");
		}
		GenesisLauncher me = new GenesisLauncher(
				conf.getAppTitle(), 
				conf.getAppIcon(), 
				"images/launcher.png"
			);
		// set the app icon image for later use
		GenesisConfig.APP_ICON = me.getIconImage();
		GenesisLauncher.open();
	}
	
	
	/**
	 * 
	 */
	@Override
	protected void setupLayout(
			String title,
			Image icon,
			Image background 
		) 
	{
		int margin = 10;
		int btnHeight = 23;
		int btnPadding = 5;
		
		defaultWidth = 250;
		defaultHeight = 400;
		
		super.setupLayout(title, icon, background);
		
		// version label
		JLabel lblV = new JLabel();
		lblV.setHorizontalAlignment(JLabel.CENTER);
		lblV.setForeground(Color.LIGHT_GRAY);
		lblV.setFont(lblV.getFont().deriveFont(9.0f));
		lblV.setText(
				"<html><center>" 
					+ this.getTitle() 
					+ " v." 
					+ GenesisConfig.APP_VERSION
					+ "</center></html>"
				);
		lblV.setBounds(margin, 60, defaultWidth-2*margin, 25);
		
		// disclaimer label
		JLabel lblD = new JLabel();
		lblD.setHorizontalAlignment(JLabel.CENTER);
		lblD.setForeground(Color.LIGHT_GRAY);
		lblD.setFont(lblD.getFont().deriveFont(8.0f));
		lblD.setText(
				"<html><center>�DAS SCHWARZE AUGE, AVENTURIEN, DERE, MYRANOR, THARUN, UTHURIA und RIESLAND " 
					+ "sind eingetragene Marken<br>" 
					+ "der Significant Fantasy Medienrechte GbR.<br>" 
					+ "Ohne vorherige schriftliche Genehmigung der Ulisses Medien und Spiel Distribution GmbH " 
					+ "ist eine Verwendung der genannten Markenzeichen nicht gestattet.�</center></html>"
			);
		lblD.setBounds(margin, 325, defaultWidth-2*margin, 70);
		
		// to avoid z fighting
		imgPanel.add(lblV);
		imgPanel.add(lblD);
		
		String[][] btnList = {
				{"Setup", ACMD_SETUP}, 
				{"Core Data Editor", ACMD_LAUNCH_CORE },
				{"Meta Data Editor", ACMD_LAUNCH_META },
				{"Hero Editor", ACMD_LAUNCH_HERO },
				{"Schlie�en", ACMD_EXIT }
			};
		
		for( int i=0; i< btnList.length; i++ )
		{
			this.addButton(
					margin,	//x
					90 + (i*(btnHeight +btnPadding)),	//y 
					defaultWidth - 2*margin, 			//width
					btnHeight, 							//height
					btnList[i][0], 						//label text
					btnList[i][1]						//action command
				);
		}
	}
	
	/**
	 * addButton
	 *  
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param label
	 * @param cmd
	 */
	protected void addButton(
			int x, 
			int y, 
			int width, 
			int height, 
			String label, 
			String cmd 
		)
	{
		JButton btn = new JButton();
		btn.setText(label);
		btn.setActionCommand(cmd);
		btn.addActionListener(this);
		btn.setBounds(x, y, width, height);
		btn.setVisible(true);
		
		// to avoid z fighting
		imgPanel.add(btn);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		if( ae.getActionCommand().equals(ACMD_EXIT))
		{
			ApplicationLogger.logInfo("Application exited by user.");
			System.exit(0);
		
		} else if( ae.getActionCommand().equals(ACMD_LAUNCH_CORE)) {
			// TODO
			
		} else if( ae.getActionCommand().equals(ACMD_LAUNCH_META)) {
			// TODO
			
		} else if( ae.getActionCommand().equals(ACMD_LAUNCH_HERO)) {
			// TODO
			
		} else if( ae.getActionCommand().equals(ACMD_SETUP)) {
			
			// TODO
		}
		
	}
}
