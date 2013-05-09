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

import java.awt.BorderLayout;

import jhv.image.ImageResource;
import jhv.swing.webView.JFXWebView;
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
		
		this.setTitle("Hilfe");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		ImageResource irHome = new ImageResource("images/icons/home.gif",this);
		ImageResource irBack = new ImageResource("images/icons/arrowLeft.gif",this);
		ImageResource irForward = new ImageResource("images/icons/arrowRight.gif",this);
		ImageResource irWiki = new ImageResource("images/icons/wiki.gif",this);
				
		JButton btnHome = new JButton("");
		btnHome.setToolTipText("Zum Hilfe Index");
		btnHome.setIcon(irHome.getImageIcon());
		btnHome.setActionCommand(ACMD_HOME);
		btnHome.addActionListener(this);
		toolBar.add(btnHome);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut);
		
		JButton btnBack = new JButton("");
		btnBack.setToolTipText("Seite Zur\u00FCck");
		btnBack.setIcon(irBack.getImageIcon());
		btnBack.setActionCommand(ACMD_BACK);
		btnBack.addActionListener(this);
		toolBar.add(btnBack);
		
		JButton btnForward = new JButton("");
		btnForward.setToolTipText("Seite Vor");
		btnForward.setIcon(irForward.getImageIcon());
		btnForward.setActionCommand(ACMD_FORWARD);
		btnForward.addActionListener(this);
		toolBar.add(btnForward);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut_1);
		
		JButton btnWiki = new JButton("");
		btnWiki.setToolTipText("DSA Genesis Wiki (online)");
		btnWiki.setIcon(irWiki.getImageIcon());
		btnWiki.setActionCommand(ACMD_WIKI);
		btnWiki.addActionListener(this);
		toolBar.add(btnWiki);
		
		webView = new JFXWebView();
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
				// nothing to do
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