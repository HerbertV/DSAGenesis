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
package dsagenesis.editor.coredata.dialog;

import dsagenesis.editor.coredata.CoreEditorFrame;

/**
 * FormularCellDialog
 */
public class FormularCellDialog 
		extends AbstractCellDialog 
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
	 * 
	 * @param f
	 */
	public FormularCellDialog(
			CoreEditorFrame f, 
			String title
		) 
	{
		super(f, 500, 400);
		
		
		//this.setTitle(title);
		
		// TODO add allowed ids
	}

	// ============================================================================
	//  Variables
	// ============================================================================
		
	@Override
	public Object getValue() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(Object value) 
	{
		// TODO Auto-generated method stub

	}

}
