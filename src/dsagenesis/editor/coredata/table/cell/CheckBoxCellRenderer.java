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

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

import dsagenesis.core.sqlite.DBConnector;

/**
 * CheckBoxCellRenderer.
 * 
 * Renders Booleans as a Checkbox.
 */
public class CheckBoxCellRenderer 
		extends BasicCellRenderer 
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
			
	private JCheckBox checkBox;
	private JPanel panel;

	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 */
	public CheckBoxCellRenderer()
	{
		super();
		
		checkBox = new JCheckBox();
		checkBox.setHorizontalAlignment(AbstractButton.CENTER);
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(checkBox,BorderLayout.CENTER);
        checkBox.setBackground(this.getBackground());
        checkBox.setForeground(this.getForeground());
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
		// call super for coloring
		super.getTableCellRendererComponent(
				table,
				value,
				isSelected,
				hasFocus,
				row,
				column
			);
		checkBox.setBackground(this.getBackground());
		checkBox.setForeground(this.getForeground());

		checkBox.setSelected(
				DBConnector.convertBooleanFromDB(value)
			);
		return panel;
	}
}
