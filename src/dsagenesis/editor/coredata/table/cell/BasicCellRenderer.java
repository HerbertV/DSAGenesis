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
import java.awt.SystemColor;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import dsagenesis.core.ui.Colors;
import dsagenesis.core.util.logic.ISyntax;

/**
 * CoreEditorTableCellRenderer
 * 
 * Custom cell renderer that mark readonly columns
 */
public class BasicCellRenderer
		extends DefaultTableCellRenderer 
{

	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	public BasicCellRenderer() 
	{
		super();
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
     * getTableCellRendererComponent
     * 
     * @param table
     * @param value
     * @param isSelected
     * @param hasFocus
     * @param row
     * @param column
     */
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
		int numcols = table.getSelectedColumnCount();
		int numrows = table.getSelectedRowCount();
		int[] rowsselected = table.getSelectedRows();
		int[] colsselected = table.getSelectedColumns();
		boolean isSelectedRow = false;
		boolean isSelectedCol = false;
		
		for( int i=0; i<numcols; i++ )
		{
		    if( colsselected[i] == column )
		    {
		        isSelectedCol = true;
		        break;
		    }
		}
		
		for( int i=0; i<numrows; i++ )
		{
		    if( rowsselected[i] == row )
		    {
		        isSelectedRow = true;
		        break;
		    }
		}
		
		if( isSelectedRow && isSelectedCol )
		{
		    // selection Color
			if( table.isCellEditable(row,column) )
			{
				this.setBackground(Colors.colorTableCellActive);
				this.setForeground(SystemColor.textHighlightText);
			} else {
				// read only 
		        this.setBackground(Colors.colorTableCellActiveReadonly);
		        this.setForeground(SystemColor.textHighlightText);
		    }
			
		} else if (isSelectedRow) {
		    // active row
			if( table.isCellEditable(row,column) ) 
			{
				this.setBackground(Colors.colorTableRowActive);
				this.setForeground(SystemColor.textHighlightText);
			
			} else {
				// read only
		        this.setBackground(Colors.colorTableRowActiveReadonly);
		        this.setForeground(SystemColor.textHighlightText);
		    }
		} else {
		    // inactive
			if( row%2 == 0 )
			{
				this.setBackground(Colors.colorTableRowInactive2);
				this.setForeground(SystemColor.textText);
			} else {
				this.setBackground(Colors.colorTableRowInactive1);
				this.setForeground(SystemColor.textText);
				
			}
		}
		
		if( value == null )
		{
			this.setText("");
		} else {
			if( value instanceof ISyntax )
			{
				this.setText(((ISyntax)value).renderStringForCell());
				this.setToolTipText(
						((ISyntax)value).renderStringForCellTooltip()
					);
				
			} else {
				this.setText(value.toString());
			}
		}
		return this;
    }

}
