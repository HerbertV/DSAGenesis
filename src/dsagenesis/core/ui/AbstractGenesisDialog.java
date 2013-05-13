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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JWindow;

import jhv.component.ILabeledComponent;
import jhv.component.LabelResource;

import dsagenesis.core.config.GenesisConfig;

/**
 * Abstract base for all Genesis JDialogs
 */
public abstract class AbstractGenesisDialog 
		extends JDialog
		implements ILabeledComponent
{

	// ============================================================================
	//  Variables
	// ============================================================================
	
	private static final long serialVersionUID = 1L;
	
	protected LabelResource labelResource;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor without owner.
	 */
	public AbstractGenesisDialog() 
	{
		super();
		
		this.setIconImage(GenesisConfig.APP_ICON);
	}
	
	/**
	 * Constructor with frame.
	 * 
	 * @param f
	 */
	public AbstractGenesisDialog(JFrame f) 
	{
		super(f);
		
		this.setIconImage(GenesisConfig.APP_ICON);
	}
	
	/**
	 * Constructor with window.
	 * 
	 * @param w
	 */
	public AbstractGenesisDialog(JWindow w) 
	{
		super(w);
		
		this.setIconImage(GenesisConfig.APP_ICON);
	}
}
