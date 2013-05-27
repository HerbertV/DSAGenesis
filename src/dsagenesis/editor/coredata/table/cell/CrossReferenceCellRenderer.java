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
import java.util.Vector;

import javax.swing.JTable;

/**
 * CrossReferenceCellRenderer
 * 
 * renders a 1-n Cross Reference as label 
 */
public class CrossReferenceCellRenderer 
		extends BasicCellRenderer 
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	private Vector<Vector<Object>> idLabelPairs;

	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor.
	 * 
	 * @param idLabels
	 */
	public CrossReferenceCellRenderer(Vector<Vector<Object>> idLabels) 
	{
		super();
		this.idLabelPairs = idLabels;
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
		Object label = "";
		// look for crossreference.
		for( int i=0; i< this.idLabelPairs.size(); i++ )
		{
			if( this.idLabelPairs.get(i).get(0).equals(value) )
			{
				label = this.idLabelPairs.get(i).get(1);
				break;
			}
		}
		
		// call super for coloring
		return super.getTableCellRendererComponent(
				table,
				label,
				isSelected,
				hasFocus,
				row,
				column
			);
	}
}
