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

import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.dialog.JunctionCellDialog;

/**
 * JunctionCellEditor
 */
public class JunctionCellEditor 
		extends AbstractDialogCellEditor 
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
	 * @param dialog
	 * @param idLabels
	 */
	public JunctionCellEditor(
			CoreEditorFrame f, 
			String title,
			Vector<Vector<Object>> idLabels
		)
	{
		super(new JunctionCellDialog(f,title,idLabels));
		this.idLabelPairs = idLabels;
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	@Override
	@SuppressWarnings("unchecked")
	public Component getTableCellEditorComponent(
			JTable table, 
			Object value,
			boolean isSelected, 
			int row, 
			int column
		)
	{
		Component c = super.getTableCellEditorComponent(
				table, 
				value, 
				isSelected, 
				row, 
				column
			);
		
		if( value == null )
			return c;
		
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
		
		return c;
	}

}
