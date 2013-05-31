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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import jhv.image.ImageResource;
import jhv.util.debug.logger.ApplicationLogger;

/**
 * Quick Help Icon button.
 * 
 * which opens the help dialog with a specific topic.
 */
public class QuickHelpButton 
		extends JButton 
		implements ActionListener
{
	// ============================================================================
	//  Variables
	// ============================================================================
	
	private static final long serialVersionUID = 1L;
	
	private static ImageIcon icon;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor.
	 * 
	 * @param url
	 * @param tooltip
	 */
	public QuickHelpButton(
			String url, 
			String tooltip
		)
	{
		super();
		
		if( icon == null )
			icon = new ImageResource("resources/images/icons/help.gif",this).getImageIcon();
		
		this.setSize(25, 25);
		this.setActionCommand(url);
		this.setToolTipText(tooltip);
		this.addActionListener(this);
		this.setVisible(true);
		this.setFocusPainted(false);
		this.setIcon(icon);
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * actionPerformed
	 * 
	 * @param ae 
	 */
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		File page = new File(ae.getActionCommand());
		try {
			HelpDialog d = HelpDialog.getInstance();
			d.openURL(page.toURI().toURL().toExternalForm());
			d.setVisible(true);
		} catch (MalformedURLException e) {
			ApplicationLogger.logError(e);
		}
	}
}
