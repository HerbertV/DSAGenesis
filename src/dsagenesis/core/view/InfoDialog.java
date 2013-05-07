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
package dsagenesis.core.view;

import javax.swing.JFrame;
import javax.swing.JWindow;

/** 
 * Default info/about dialog used by all DSAGenesis Editors. 
 */
public class InfoDialog 
		extends AbstractGenesisDialog 
{

	// ============================================================================
	//  Variables
	// ============================================================================
		
	private static final long serialVersionUID = 1L;

	
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
		initUI();
	}
	
	/**
	 * Constructor with window.
	 * 
	 * @param w
	 */
	public InfoDialog(JWindow w)
	{
		super(w);
		initUI();
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================
		
	private void initUI()
	{
		this.setTitle("Info");
	
		// TODO add ok button and labels 
		
		// TODO add links: with java.awt.Desktop
		
	}
}
