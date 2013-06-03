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
package dsagenesis.core.ui;

import java.awt.BorderLayout;

import jhv.image.ImageResource;
import jhv.swing.webView.JFXWebView;
import jhv.util.debug.logger.ApplicationLogger;

import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.Box;

/**
 * Genesis Help Dialog with access to online Wiki.
 */
public class HelpDialog 
		extends AbstractGenesisDialog 
		implements ActionListener
{
	// ============================================================================
	//  Constants
	// ============================================================================

	private static final long serialVersionUID = 1L;

	public static final String ACMD_HOME = "home";
	public static final String ACMD_BACK = "back";
	public static final String ACMD_FORWARD = "forward";
	public static final String ACMD_WIKI = "wiki";
	
	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	private static HelpDialog instance;
	
	private JFXWebView webView;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 */
	private HelpDialog() 
	{
		super();
		
		this.getContentPane().setLayout( new BorderLayout());
		
		this.setTitle(labelResource.getProperty("title", "title"));
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setRollover(true);
		toolBar.setFloatable(false);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		ImageResource irHome = new ImageResource("resources/images/icons/home.gif",this);
		ImageResource irBack = new ImageResource("resources/images/icons/arrowLeft.gif",this);
		ImageResource irForward = new ImageResource("resources/images/icons/arrowRight.gif",this);
		ImageResource irWiki = new ImageResource("resources/images/icons/wiki.gif",this);
				
		JButton btnHome = new JButton("");
		btnHome.setToolTipText(labelResource.getProperty("btnHome", "btnHome"));
		btnHome.setIcon(irHome.getImageIcon());
		btnHome.setActionCommand(ACMD_HOME);
		btnHome.addActionListener(this);
		toolBar.add(btnHome);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut);
		
		JButton btnBack = new JButton("");
		btnBack.setToolTipText(labelResource.getProperty("btnBack", "btnBack"));
		btnBack.setIcon(irBack.getImageIcon());
		btnBack.setActionCommand(ACMD_BACK);
		btnBack.addActionListener(this);
		toolBar.add(btnBack);
		
		JButton btnForward = new JButton("");
		btnForward.setToolTipText(labelResource.getProperty("btnForward", "btnForward"));
		btnForward.setIcon(irForward.getImageIcon());
		btnForward.setActionCommand(ACMD_FORWARD);
		btnForward.addActionListener(this);
		toolBar.add(btnForward);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut_1);
		
		JButton btnWiki = new JButton("");
		btnWiki.setToolTipText(labelResource.getProperty("btnWiki", "btnWiki"));
		btnWiki.setIcon(irWiki.getImageIcon());
		btnWiki.setActionCommand(ACMD_WIKI);
		btnWiki.addActionListener(this);
		toolBar.add(btnWiki);
		
		webView = new JFXWebView();
		webView.setupWindowTitle(
				this, 
				labelResource.getProperty("title", "title") +" | "
			);
		webView.createJavaFX();
		this.getContentPane().add(webView);		
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================
		
	/**
	 * getInstance
	 * 
	 * @return
	 */
	public static HelpDialog getInstance()
	{
		if( instance == null )
			instance = new HelpDialog();
		
		return instance;
	}
	
	/**
	 * openURL
	 * 
	 * @param url
	 */
	public void openURL(String url)
	{
		webView.openURL(url);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		if( ae.getActionCommand().equals(ACMD_HOME) )
		{
			File page = new File("./help/index.html");
			try {
				webView.openURL(page.toURI().toURL().toExternalForm());
			} catch (MalformedURLException e) {
				ApplicationLogger.logError(e);
			}
		
		} else if( ae.getActionCommand().equals(ACMD_FORWARD) ) {
			webView.browseForward();
			
		} else if( ae.getActionCommand().equals(ACMD_BACK) ) {
			webView.browseBack();
			
		} else if( ae.getActionCommand().equals(ACMD_WIKI) ) {
			webView.openURL("https://github.com/HerbertV/DSAGenesis/wiki");
		}
	}

}
