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
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JTable;

import dsagenesis.core.util.logic.Formula;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.dialog.FormulaCellDialog;

/**
 * FormulaCellEditor
 */
public class FormulaCellEditor 
		extends AbstractDialogCellEditor 
{
	// ============================================================================
	//  Constants
	// ============================================================================
				
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================

	private String rowId;
	
	private String rowName;
	
	private String rowColumn;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================

	/**
	 * Constructor
	 * 
	 * @param f
	 * @param title
	 * @param allowedTables format:
	 * 						[0] db tablename
	 * 						[1] table label
	 * 						[2] column used as label for entries
	 * @param labelColumn 
	 */
	public FormulaCellEditor(
			CoreEditorFrame f, 
			String title,
			Vector<Vector<String>> allowedTables,
			String labelColumn	
		) 
	{
		super(new FormulaCellDialog(
				f,
				title,
				allowedTables
			));
		this.rowColumn = labelColumn;
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	@Override
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
		
		rowId = (String)table.getValueAt(row,0);
		rowName = (String)table.getValueAt(row,1);
		
		if( value == null )
		{
			label.setText("f(x):{}");
		} else {	
			label.setText(value.toString());
			label.setToolTipText(((Formula)value).renderStringForCellTooltip());
		}
		return c;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if( e.getButton() == MouseEvent.BUTTON1 )
		{
			((FormulaCellDialog)dialog).setRowParams(rowId, rowName, rowColumn);
			dialog.setValue(oldValue);
			dialog.setLocationRelativeTo(dialog.getParent());
			dialog.setVisible(true);
			
			if( dialog.isChangeOk() )
            {
				oldValue = dialog.getValue();
				fireEditingStopped();
				return;
            } 
		} 
		fireEditingCanceled();
	}
}
