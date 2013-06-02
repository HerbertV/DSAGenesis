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

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;

public class FormularCellRenderer
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

	
	public FormularCellRenderer() 
	{
		super();
		// TODO
	}

	// ============================================================================
	//  Functions
	// ============================================================================
	
	@Override
	public Component getTableCellRendererComponent(
    		JTable table, 
    		Object value, 
    		boolean isSelected,
    		boolean hasFocus, 
    		int row, 
    		int column
    	)
    {
		JLabel label = (JLabel) super.getTableCellRendererComponent(
				table, 
				value, 
				isSelected, 
				hasFocus, 
				row, 
				column
			);
		
		if( value == null )
		{
			label.setText("f(x)= undefined");
		} else {	
			label.setText("f(x)= "+value);
		}
		// TODO label.setToolTipText(tooltip);
		return label;
    }
	
}
