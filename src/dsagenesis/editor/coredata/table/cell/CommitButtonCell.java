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
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import dsagenesis.editor.coredata.table.CoreEditorTable;

import jhv.image.ImageResource;


/**
 * CommitButtonCell.
 * 
 * Is renderer and editor.
 * Fires the commit for its row to update the changes in the DB.
 */
public class CommitButtonCell 
		extends AbstractCellEditor 
		implements TableCellRenderer, TableCellEditor, MouseListener 
{
	// ============================================================================
	//  Constants
	// ============================================================================

	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	private static ImageIcon icon;
	private static ImageIcon iconDisabled;
	
	private JButton buttonRender;
	private JButton buttonEdit;
	
	private Vector<Boolean> changedRows;
	
	private CoreEditorTable table;

	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 * 
	 * @param cet
	 */
	public CommitButtonCell(CoreEditorTable cet) 
	{
		if( icon == null )
		{
			icon = new ImageResource("images/icons/dbCommit.gif",cet).getImageIcon();
			iconDisabled = new ImageResource("images/icons/dbCommitDisabled.gif",cet).getImageIcon();
		}
		buttonRender = setupButton();
		buttonEdit = setupButton();
		buttonEdit.addMouseListener(this);
		table = cet;
		changedRows = new Vector<Boolean>();
	}

	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * setupButton
	 * 
	 * @return
	 */
	private JButton setupButton()
	{
		JButton btn = new JButton();
		btn.setSize(25, 22);
		btn.setVisible(true);
		btn.setIcon(icon);
		btn.setDisabledIcon(iconDisabled);
		btn.setBorder(BorderFactory.createEmptyBorder());
		btn.setOpaque(false);
		btn.setFocusPainted(false);
		
		return btn;
	}
	
	/**
	 * initRows
	 * 
	 * @param count
	 */
	public void initRows(int count)
	{
		for(int i=0; i<count; i++)
			changedRows.add(false);
	}
	
	/**
	 * setEnabled
	 * 
	 * enables the commit button for a row
	 * 
	 * @param row
	 * @param enabled
	 */
	public void setEnabled(int row, boolean enabled)
	{
		changedRows.setElementAt(enabled, row);
	}
	
	/**
	 * getChangedRows
	 * 
	 * returns the changed rows vector.
	 * 
	 * @return
	 */
	public Vector<Boolean> getChangedRows()
	{
		return changedRows;
	}
	
	@Override
	public Object getCellEditorValue() 
	{
		return null;
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
		buttonEdit.setEnabled(changedRows.elementAt(row));
		
		return buttonEdit;
	}

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
		buttonRender.setEnabled(changedRows.elementAt(row));
		
		return buttonRender;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int row = table.getEditingRow();

		if( !changedRows.elementAt(row) )
		{
			fireEditingCanceled();
			return;
		}
		
		if( e.getButton() == MouseEvent.BUTTON1 ) 
		{
			table.commitRow(row);
			changedRows.setElementAt(false, row);
		}
		// canceled since the button does not change any data.
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
