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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;

import jhv.image.ImageResource;


/**
 * StatusBar
 * 
 * a status Bar with icon, label and progress bar.
 */
public class StatusBar 
		extends JPanel 
{

	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;

	/**
	 * possible states
	 */
	public static final int STATUS_OK = 0;
	public static final int STATUS_ERROR = 1;
	public static final int STATUS_WORKING = 2;
	
	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	/**
	 * ok icon
	 */
	private static ImageIcon iconOk;
	
	/**
	 * error icon
	 */
	private static ImageIcon iconError;
	
	/**
	 * working icon
	 */
	private static ImageIcon iconWorking;
		
	/**
	 * status label
	 */
	private JLabel statusLabel;
	
	/**
	 * status progress
	 */
	private JProgressBar statusProgressBar;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor.
	 */
	public StatusBar() 
	{
		this.setLayout(new GridBagLayout());
		this.setMinimumSize(new Dimension(100,30));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		if( iconOk == null )
		{
			iconOk = new ImageResource("resources/images/icons/statusOk.gif",this).getImageIcon();
			iconError = new ImageResource("resources/images/icons/statusError.gif",this).getImageIcon();
			iconWorking = new ImageResource("resources/images/icons/statusWorking.gif",this).getImageIcon();
		}
		
		statusLabel = new JLabel("READY");
		statusLabel.setIcon(iconOk);
		statusLabel.setIconTextGap(5);
		statusLabel.setPreferredSize(new Dimension(400,25));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		this.add(statusLabel,gbc);
		
		statusProgressBar = new JProgressBar();
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.2;
		this.add(statusProgressBar,gbc);
		
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}

	// ============================================================================
	//  Functions
	// ============================================================================
		
	/**
	 * getStatusLabel
	 * 
	 * @return
	 */
	public JLabel getStatusLabel()
	{
		return this.statusLabel;
	}
	
	/**
	 * getProgressBar
	 * 
	 * @return
	 */
	public JProgressBar getProgressBar()
	{
		return this.statusProgressBar;
	}

	/**
	 * setStatus
	 * 
	 * sets a new status message and the new status state
	 * 
	 * @param msg
	 * @param status STATUS_OK STATUS_ERROR or STATUS_WORKING
	 */
	public void setStatus(String msg, int status)
	{
		this.statusLabel.setText(msg);
	
		if( status == STATUS_OK )
			this.statusLabel.setIcon(iconOk);
		else if( status == STATUS_ERROR )
			this.statusLabel.setIcon(iconError);
		else if( status == STATUS_WORKING )
			this.statusLabel.setIcon(iconWorking);
	}
	
	/**
	 * setStatus
	 * 
	 * updates only the message
	 * 
	 * @param msg
	 */
	public void setStatus(String msg)
	{
		this.statusLabel.setText(msg);
	}

	/**
	 * setProgress
	 * 
	 * @param val
	 */
	public void setProgress(int val)
	{
		this.statusProgressBar.setValue(val);
	}
}
