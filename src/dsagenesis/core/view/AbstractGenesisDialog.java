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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JWindow;

/**
 * Abstract base for all Genesis JDialogs
 */
public abstract class AbstractGenesisDialog 
		extends JDialog 
{

	// ============================================================================
	//  Variables
	// ============================================================================
	
	private static final long serialVersionUID = 1L;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor 1 with frame.
	 * 
	 * @param f
	 */
	public AbstractGenesisDialog(JFrame f) 
	{
		super(f);
	}
	
	/**
	 * Constructor 2 with window.
	 * 
	 * @param w
	 */
	public AbstractGenesisDialog(JWindow w) 
	{
		super(w);
	}
}
