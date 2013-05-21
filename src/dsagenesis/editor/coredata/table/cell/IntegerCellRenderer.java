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
package dsagenesis.editor.coredata.table.cell;

import javax.swing.JLabel;

/**
 * IntegerCellRenderer.
 * 
 * renders Integer Right Aligned.
 */
public class IntegerCellRenderer 
		extends BasicCellRenderer 
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
	 * Constructor
	 */
	public IntegerCellRenderer()
	{
		super();
		this.setHorizontalAlignment(JLabel.RIGHT);
	}

	// ============================================================================
	//  Functions
	// ============================================================================	
	
}
