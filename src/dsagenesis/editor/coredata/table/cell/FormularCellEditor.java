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

import javax.swing.JTable;

import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.dialog.FormularCellDialog;

/**
 * FormularCellEditor
 */
public class FormularCellEditor 
		extends AbstractDialogCellEditor 
{
	// ============================================================================
	//  Constants
	// ============================================================================
				
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================

	private String currentId;
	
	// ============================================================================
	//  Constructors
	// ============================================================================

	public FormularCellEditor(
			CoreEditorFrame f, 
			String title
		) 
	{
		super(new FormularCellDialog(f,title));
		// TODO Auto-generated constructor stub
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
		
		currentId = (String)table.getValueAt(row,0);
		
		if( value == null )
		{
			label.setText("f(x)= undefined");
		} else {	
			label.setText("f(x)= "+value);
		}
		return c;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if( e.getButton() == MouseEvent.BUTTON1 )
		{
			((FormularCellDialog)dialog).setId(currentId);
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
