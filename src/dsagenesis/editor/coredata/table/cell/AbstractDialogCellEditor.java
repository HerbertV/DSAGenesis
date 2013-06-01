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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import dsagenesis.editor.coredata.dialog.AbstractCellDialog;

/**
 * AbstractDialogCellEditor
 * 
 * Abstract bas class for TableCellEditors 
 * that show a dialog.
 * 
 * has a label and a button.
 */
public abstract class AbstractDialogCellEditor 
		extends AbstractCellEditor 
		implements TableCellEditor, MouseListener
{
	// ============================================================================
	//  Constants
	// ============================================================================

	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================

	/** 
	 * the dialog to open
	 */
	protected AbstractCellDialog dialog;
	
	protected JPanel panel;
	
	protected JButton button;
	
	protected JLabel label;
    
	/**
	 * is stored for the case the dialog was canceled.
	 */
	protected Object oldValue;

	
	// ============================================================================
	//  Constructors
	// ============================================================================

	/**
	 * Constructor.
	 * 
	 * @param dialog
	 */
	public AbstractDialogCellEditor(AbstractCellDialog dialog) 
	{
		this.dialog = dialog;
		
		label = new JLabel();
        label.setBackground(SystemColor.BLUE);
        label.setForeground(SystemColor.textHighlightText);
    
        button = new JButton(" ... ");
        button.addMouseListener(this);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        
        panel = new JPanel(new BorderLayout()); 
        panel.setBackground(SystemColor.BLUE);
        panel.setForeground(SystemColor.textHighlightText);
        panel.add(label); 
        panel.add(button, BorderLayout.EAST); 
    }

	// ============================================================================
	//  Functions
	// ============================================================================

	@Override
	public Object getCellEditorValue() 
	{
		if( dialog.isChangeOk() )
			return dialog.getValue();
		
		return oldValue;
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
		oldValue = value;
		if( value != null )
			label.setText(value.toString());
		
		return panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if( e.getButton() == MouseEvent.BUTTON1 )
		{
			dialog.setValue(oldValue);
			dialog.setLocationRelativeTo(dialog.getParent());
			dialog.setVisible(true);
			
			if( dialog.isChangeOk() )
            {
				fireEditingStopped();
				return;
            } 
		} 
		fireEditingCanceled();
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// not used
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		// not used
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		// not used
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// not used
	}

}
