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

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;


/**
 * CrossReferenceCellEditor
 * 
 * edits a 1-n Cross Reference as combo box 
 */
public class CrossReferenceCellEditor 
		extends DefaultCellEditor 
{
	// ============================================================================
	//  Constants
	// ============================================================================
			
	private static final long serialVersionUID = 1L;

	// ============================================================================
	//  Variables
	// ============================================================================
			
	private Vector<Vector<Object>> idLabelPairs;

	private JComboBox<String> comboBox;
	
	// ============================================================================
	//  Constructors
	// ============================================================================

	/**
	 * Constructor
	 * 
	 * @param idLabels
	 */
	@SuppressWarnings("unchecked")
	public CrossReferenceCellEditor(Vector<Vector<Object>> idLabels) 
	{
		super(new JComboBox<String>());
		this.idLabelPairs = idLabels;
		
		comboBox = (JComboBox<String>)this.getComponent();
		
		for(int i=0; i< idLabelPairs.size(); i++ )
			comboBox.addItem((String)idLabels.get(i).get(1));
		
		this.setClickCountToStart(2);
	}

	// ============================================================================
	//  Functions
	// ============================================================================

	@Override
	public Object getCellEditorValue()
	{
		return this.idLabelPairs.get(comboBox.getSelectedIndex()).get(0);
	}


	@Override
	public Component getTableCellEditorComponent(
			JTable table, 
			Object value,
			boolean isSelected, 
			int row, 
			int column
		)
	{
		Component comp = super.getTableCellEditorComponent(table, value, isSelected, row, column);
		for (int i=0; i<this.idLabelPairs.size(); i++)
		{
			if (value.equals(this.idLabelPairs.get(i).get(0)))
			{
				comboBox.setSelectedIndex(i);
				break;
			}
		}
		return comp;
	}

}
