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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.ui.AbstractGenesisFrame;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.hero.HeroEditorFrame;
import dsagenesis.editor.metadata.MetaEditorFrame;

import jhv.swing.launcher.AbstractLauncher;
import jhv.util.debug.DebugLevel;
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
	
	public static AbstractGenesisFrame openFrame;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor.
	 * 
	 * @param title
	 * @param iconfile
	 * @param bgfile
	 */
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
		GenesisConfig.APP_START_TIME = time;
		
		// init logger
		if( conf.isLoggerEnabled() )
		{
			ApplicationLogger.getInstance(
					conf.getAppTitle(), 
					time, 
					DebugLevel.INFO
				);
			
			// log some infos about the system
			ApplicationLogger.separator();
			ApplicationLogger.logInfo(
					"DSA Genesis v" + GenesisConfig.APP_VERSION + " started."
				);
			ApplicationLogger.separator();
			
			int jbit = 32;
			if( System.getProperty("sun.arch.data.model").contains("64") )
				jbit = 64;
			
			String systemInfo = "JRE: " + System.getProperty("java.runtime.version")
					+ " " + jbit + "-bit\n"
					+ "Vendor: " +	System.getProperty("java.vendor") + "\n"
					+ "Platform: " + System.getProperty("os.name") + " " 
					+ System.getProperty("os.version") + "\n\n"
					+ "Display(s):\n";
			
			Rectangle[] resolutions = GenesisConfig.getDisplayResolutions();
			for( int i=0; i< resolutions.length ; i++ )
				systemInfo += "["+ (i+1) + "]: "
						+ resolutions[i].width + "x" 
						+ resolutions[i].height + "\n";
			
			ApplicationLogger.logInfo(systemInfo);
			ApplicationLogger.separator();
				
			
			
			ApplicationLogger.setLevel(conf.getDebugLevel());
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
				"<html><center>ÑDAS SCHWARZE AUGE, AVENTURIEN, DERE, MYRANOR, THARUN, UTHURIA und RIESLAND " 
					+ "sind eingetragene Marken<br>" 
					+ "der Significant Fantasy Medienrechte GbR.<br>" 
					+ "Ohne vorherige schriftliche Genehmigung der Ulisses Medien und Spiel Distribution GmbH " 
					+ "ist eine Verwendung der genannten Markenzeichen nicht gestattet.ì</center></html>"
			);
		lblD.setBounds(margin, 325, defaultWidth-2*margin, 70);
		
		// to avoid z fighting
		imgPanel.add(lblV);
		imgPanel.add(lblD);
		
		String[][] btnList = {
				{ "Setup", ACMD_SETUP, "0" }, 
				{ "Core Data Editor", ACMD_LAUNCH_CORE, "10" },
				{ "Meta Data Editor", ACMD_LAUNCH_META, "0" },
				{ "Hero Editor", ACMD_LAUNCH_HERO, "0" },
				{ "Schlieﬂen", ACMD_EXIT, "10" }
			};
		
		int additionalY = 0;
		for( int i=0; i< btnList.length; i++ )
		{
			additionalY += Integer.parseInt(btnList[i][2]);
			
			this.addButton(
					margin,	//x
					90 + (i*(btnHeight +btnPadding)) + additionalY, //y 
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
		btn.setOpaque(false);
		btn.setFocusPainted(false);
		
		// to avoid z fighting
		imgPanel.add(btn);
	}
	
	
	/**
	 * actionPerformed.
	 * handler for the launcher buttons.
	 * 
	 * @param ae
	 */
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		if( ae.getActionCommand().equals(ACMD_EXIT))
		{
			boolean doClose = true;
			
			if( openFrame != null )
			{
				doClose = false;
				
				if( openFrame.isSaved() )
				{
					openFrame.close(null);
					doClose = true;
					
				} else {
					int result = JOptionPane.showConfirmDialog(
							openFrame,
							openFrame.getTitle() 
								+ " enth‰lt ungesicherte Daten.\nWillst du wirklich schlieﬂen?",
							"Schlieﬂen best‰tigen",
							JOptionPane.YES_NO_OPTION
						);
					
					if( result == JOptionPane.YES_OPTION )
					{
						openFrame.close(null);
						doClose = true;
					}
				}
			}
			
			if( doClose )
			{
				ApplicationLogger.logInfo("Application exited by user.");
				System.exit(0);
			}
			
		} else if( ae.getActionCommand().equals(ACMD_LAUNCH_CORE)) {
			if( openFrame != null )
				return;
			
			openFrame = new CoreEditorFrame();
			openFrame.setVisible(true);
			
		} else if( ae.getActionCommand().equals(ACMD_LAUNCH_META)) {
			if( openFrame != null )
				return;
			
			openFrame = new MetaEditorFrame();
			openFrame.setVisible(true);
			
		} else if( ae.getActionCommand().equals(ACMD_LAUNCH_HERO)) {
			if( openFrame != null )
				return;
			
			openFrame = new HeroEditorFrame();
			openFrame.setVisible(true);
			
		} else if( ae.getActionCommand().equals(ACMD_SETUP)) {
			if( openFrame != null )
				return;
			
			// TODO
		}
	}
}
