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
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.ui.AbstractGenesisFrame;
import dsagenesis.core.ui.SetupFrame;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.hero.HeroEditorFrame;
import dsagenesis.editor.metadata.MetaEditorFrame;

import jhv.component.ILabeledComponent;
import jhv.component.LabelResource;
import jhv.image.ImageResource;
import jhv.io.PathCreator;
import jhv.swing.launcher.AbstractLauncher;
import jhv.util.debug.DebugLevel;
import jhv.util.debug.logger.ApplicationLogger;

/**
 * Launcher for DSA Genesis
 *
 */
public class GenesisLauncher 
		extends AbstractLauncher 
		implements ActionListener, ILabeledComponent
{
	
	// ============================================================================
	//  Constants
	// ============================================================================
	
	public static final String ACMD_LAUNCH_CORE = "launchCoreEditor";
	
	public static final String ACMD_LAUNCH_META = "launchMetaEditor";
	
	public static final String ACMD_LAUNCH_HERO = "launchHeroEditor";
	
	public static final String ACMD_SETUP = "setup";
	
	public static final String ACMD_EXIT = "exit";
	
	public static final String ACMD_MINIMIZE = "minimize";
	
	private static final long serialVersionUID = 1L;
	
	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	public static AbstractGenesisFrame openFrame;
	
	private LabelResource labelResource;
	
	
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
		if( conf.isLoggerEnabled() 
				|| conf.isFirstLaunch() )
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
			
			systemInfo += "\nuser.dir: " 
					+ System.getProperty("user.dir") + "\n"
					+ "user.home: " 
					+ System.getProperty("user.home");
			
			ApplicationLogger.logInfo(systemInfo);
			ApplicationLogger.separator();
			ApplicationLogger.newLine();
		}
		
		firstLaunch();
		
		// set now the correct level
		ApplicationLogger.setLevel(conf.getDebugLevel());
		
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
	 * firstLaunch
	 * 
	 * executed if the application starts for the very first time.
	 */
	private static void firstLaunch()
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		
		if( !conf.isFirstLaunch() )
			return;
		
		ApplicationLogger.logInfo("doing first launch ...");
		
		String[] arr = {
				conf.getPathArchtype(),
				conf.getPathCulture(),
				conf.getPathHero(),
				conf.getPathProfession(),
				conf.getPathRace(),
				conf.getPathTemplate()
			};
		
		try {
			PathCreator.createPathes(arr);
		} catch( IOException e ) {
			ApplicationLogger.logError(e);
		}
		
		conf.setFirstLaunchDone();	
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
		
		this.loadLabels();
		
		// win minimize
		this.addImageButton(
				defaultWidth-51,
				3, 
				24, 
				24, 
				(new ImageResource("images/icons/winMinimize.gif",this)).getImageIcon(), 
				ACMD_MINIMIZE,
				labelResource.getProperty("btnMinimize", "btnMinimize")
			);
		
		// win close 
		this.addImageButton(
				defaultWidth-28,
				3, 
				24, 
				24, 
				(new ImageResource("images/icons/winClose.gif",this)).getImageIcon(), 
				ACMD_EXIT,
				labelResource.getProperty("btnClose", "btnClose")
			);
		
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
		
		System.out.println(labelResource.getProperty("lblDisclaimer", "lblDisclaimer"));
		lblD.setText(
				labelResource.getProperty("lblDisclaimer", "lblDisclaimer")
			);
		lblD.setBounds(margin, 325, defaultWidth-2*margin, 70);
		
		// to avoid z fighting
		imgPanel.add(lblV);
		imgPanel.add(lblD);
		
		String[][] btnList = {
				{ labelResource.getProperty("btnSetup", "btnSetup"),
						ACMD_SETUP, 
						"0"
					}, 
				{  labelResource.getProperty("btnCoreDataEditor", "btnCoreDataEditor"), 
						ACMD_LAUNCH_CORE, 
						"10" 
					},
				{ labelResource.getProperty("btnMetaDataEditor", "btnMetaDataEditor"),
						ACMD_LAUNCH_META, 
						"0"
					},
				{ labelResource.getProperty("btnHeroEditor", "btnHeroEditor"), 
						ACMD_LAUNCH_HERO, 
						"0" 
					},
			};
		
		int additionalY = 0;
		for( int i=0; i< btnList.length; i++ )
		{
			additionalY += Integer.parseInt(btnList[i][2]);
			
			this.addTextButton(
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
	 * 
	 */
	public void loadLabels()
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		
		labelResource = new LabelResource(
				this,
				conf.getLanguage(), 
				"labels"
			);
	}
	
	/**
	 * addTextButton
	 *  
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param label
	 * @param cmd
	 */
	protected void addTextButton(
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
	 * addImageButton
	 *  
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param icon
	 * @param cmd
	 * @param tooltip
	 */
	protected void addImageButton(
			int x, 
			int y, 
			int width, 
			int height, 
			Icon icon, 
			String cmd,
			String tooltip
		)
	{
		JButton btn = new JButton();
		btn.setIcon(icon);
		btn.setActionCommand(cmd);
		btn.addActionListener(this);
		btn.setBounds(x, y, width, height);
		btn.setVisible(true);
		btn.setOpaque(false);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder());
		btn.setToolTipText(tooltip);
		// to avoid z fighting
		imgPanel.add(btn);
	}
	
	/**
	 * Overridden to de-iconfy the window. 
	 */
	public static void bringToFront()
	{
		AbstractLauncher.bringToFront();
		GenesisLauncher.openLauncher.setExtendedState(NORMAL);
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
		if( ae.getActionCommand().equals(ACMD_EXIT) )
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
								+ " enth�lt ungesicherte Daten.\nWillst du wirklich schlie�en?",
							"Schlie�en best�tigen",
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
				this.dispose();
				System.exit(0);
			}
			
		} else if( ae.getActionCommand().equals(ACMD_MINIMIZE) ) {
			this.setExtendedState(ICONIFIED);
			
		} else if( ae.getActionCommand().equals(ACMD_LAUNCH_CORE) ) {
		
			if( openFrame != null )
				return;
			
			openFrame = new CoreEditorFrame();
			openFrame.setVisible(true);
			
		} else if( ae.getActionCommand().equals(ACMD_LAUNCH_META) ) {
			if( openFrame != null )
				return;
			
			openFrame = new MetaEditorFrame();
			openFrame.setVisible(true);
			
		} else if( ae.getActionCommand().equals(ACMD_LAUNCH_HERO) ) {
			if( openFrame != null )
				return;
			
			openFrame = new HeroEditorFrame();
			openFrame.setVisible(true);
			
		} else if( ae.getActionCommand().equals(ACMD_SETUP) ) {
			if( openFrame != null )
				return;
			
			openFrame = new SetupFrame();
			openFrame.setVisible(true);
		}
	}
}
