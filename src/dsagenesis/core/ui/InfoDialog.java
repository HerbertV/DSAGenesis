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

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;

import dsagenesis.core.config.GenesisConfig;

import jhv.image.ImageResource;
import jhv.swing.JImagePanel;

/** 
 * Default info/about dialog used by all DSAGenesis Editors. 
 */
public class InfoDialog 
		extends AbstractGenesisDialog 
{

	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;
	
	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor with frame.
	 * 
	 * @param f
	 */
	public InfoDialog(JFrame f)
	{
		super(f);
		setupLayout();
	}
	
	/**
	 * Constructor with window.
	 * 
	 * @param w
	 */
	public InfoDialog(JWindow w)
	{
		super(w);
		setupLayout();
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * 
	 */
	private void setupLayout()
	{
		int margin = 10;
		int w = 400;
		int h = 300;
		
		this.setTitle(labelResource.getProperty("title", "title"));
		
		this.setSize(w,h);
		this.setResizable(false);
		
		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(this.getParent());
		
		//background
		ImageResource irBackground = new ImageResource("resources/images/info.png",this);
		JImagePanel bgPanel = new JImagePanel(irBackground.getImage());
        this.getContentPane().add(bgPanel,0);
        bgPanel.setLayout(null);
        bgPanel.setBounds(0,0,w,h);
        
        // version label
     	JLabel lblV = new JLabel();
     	lblV.setHorizontalAlignment(JLabel.CENTER);
     	lblV.setForeground(Color.LIGHT_GRAY);
 		lblV.setFont(lblV.getFont().deriveFont(9.0f));
 		lblV.setText(
 				"<html><center>Version " 
 					+ GenesisConfig.APP_VERSION
 					+ "</center></html>"
 				);
 		lblV.setBounds(margin, 40, w-2*margin, 25);
 		bgPanel.add(lblV);
 		
 		// disclaimer label
 		JLabel lblD = new JLabel();
 		lblD.setForeground(Color.LIGHT_GRAY);
 		lblD.setFont(lblD.getFont().deriveFont(9.0f));
 		lblD.setText(
 				labelResource.getProperty("lblDisclaimer", "lblDisclaimer")
 			);
 		lblD.setBounds(margin, 100, w-(2*margin + 30), 60);
 		bgPanel.add(lblD);
 		
 		WebLinkButton btnUlisses = new WebLinkButton(
 				labelResource.getProperty("btnUlisses", "btnUlisses")
 			);
 		btnUlisses.setLocation(w-margin-btnUlisses.getWidth(), 100);
 		btnUlisses.setOpaque(false);
		bgPanel.add(btnUlisses);
		
		JLabel lblCopyright = new JLabel();
		lblCopyright.setForeground(Color.LIGHT_GRAY);
		lblCopyright.setFont(lblCopyright.getFont().deriveFont(9.0f));
		lblCopyright.setText(
				labelResource.getProperty("lblCopyright", "lblCopyright")
 			);
		lblCopyright.setBounds(margin, 170, w-(2*margin + 30), 30);
 		bgPanel.add(lblCopyright);
 		
 		WebLinkButton btnMIT = new WebLinkButton(
 				labelResource.getProperty("btnMIT", "btnMIT")
 			);
 		btnMIT.setLocation(w-margin-btnMIT.getWidth(), 170);
 		btnMIT.setOpaque(false);
		bgPanel.add(btnMIT);
		
		
 		JLabel lblGithub = new JLabel();
 		lblGithub.setForeground(Color.LIGHT_GRAY);
 		lblGithub.setFont(lblD.getFont().deriveFont(9.0f));
 		lblGithub.setText(
 				labelResource.getProperty("lblGithub", "lblGithub")
 			);
 		lblGithub.setBounds(margin, 210, w-(2*margin + 30), 12);
 		lblGithub.setHorizontalAlignment(JLabel.RIGHT);
		bgPanel.add(lblGithub);
		
 		WebLinkButton btnGithub = new WebLinkButton(
 				labelResource.getProperty("btnGithub", "btnGithub")
 			);
 		btnGithub.setLocation(w-margin-btnGithub.getWidth(), 200);
 		btnGithub.setOpaque(false);
		bgPanel.add(btnGithub);
		
 		JLabel lblWiki = new JLabel();
 		lblWiki.setForeground(Color.LIGHT_GRAY);
 		lblWiki.setFont(lblD.getFont().deriveFont(9.0f));
 		lblWiki.setText(
 				labelResource.getProperty("lblWiki", "lblWiki")
 			);
 		lblWiki.setBounds(margin, 240, w-(2*margin + 30), 12);
 		lblWiki.setHorizontalAlignment(JLabel.RIGHT);
		bgPanel.add(lblWiki);
		
 		WebLinkButton btnWiki = new WebLinkButton(
 				labelResource.getProperty("btnWiki", "btnWiki")
 			);
 		btnWiki.setLocation(w-margin-btnWiki.getWidth(), 230);
 		btnWiki.setOpaque(false);
		bgPanel.add(btnWiki);
	}

}
