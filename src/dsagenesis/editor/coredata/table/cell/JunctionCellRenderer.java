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

import javax.swing.JLabel;
import javax.swing.JTable;

/**
 * JunctionCellRenderer
 */
public class JunctionCellRenderer 
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
	 * Constructor
	 * 
	 * @param idLabels
	 */
	public JunctionCellRenderer(Vector<Vector<Object>> idLabels) 
	{
		super();
		
		this.idLabelPairs = idLabels;
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================
		
	@Override
	@SuppressWarnings("unchecked")
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
			return label;
		
		Vector<Object> ids = (Vector<Object>)value;
		
		String str = "";
		
		for( int i=0; i<ids.size(); i++ )
		{
			for( int j=0; j<idLabelPairs.size(); j++ )
			{
				if( idLabelPairs.get(j).get(0).equals(ids.get(i)) )
				{
					str += idLabelPairs.get(j).get(1).toString();
					
					if( i< ids.size()-1 )
						str += ", ";
					break;
				}
			}
		}
		label.setText(str);
		return label;
    }

}
