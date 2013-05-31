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

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import jhv.image.ImageResource;
import jhv.util.debug.logger.ApplicationLogger;

/**
 * Simple Icon Button which opens the default browser with an url.
 */
public class WebLinkButton 
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
	 */
	public WebLinkButton(String url)
	{
		super();
		
		if( icon == null )
			icon = new ImageResource("resources/images/icons/weblink.gif",this).getImageIcon();
		
		this.setSize(25, 25);
		this.setActionCommand(url);
		this.setToolTipText(url);
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
		if( !Desktop.isDesktopSupported() )
		{
			ApplicationLogger.logWarning(
					"java.awt.Desktop is not supported. Can't open Weblink."
				);
			return;
		}
		
		try {
			Desktop.getDesktop().browse(
					new URL("http://"+ae.getActionCommand()).toURI()
				);
			
		} catch ( IOException | URISyntaxException e) {
			ApplicationLogger.logError(e);
		}
	}
}
