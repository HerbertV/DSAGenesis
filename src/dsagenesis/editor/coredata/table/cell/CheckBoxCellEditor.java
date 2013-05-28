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
import java.awt.SystemColor;

import javax.swing.AbstractButton;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

import dsagenesis.core.sqlite.DBConnector;

/**
 * CheckBoxCellEditor
 * 
 * A checkbox cell editor that can handle SQLlite "Boolean"
 */
public class CheckBoxCellEditor
		extends DefaultCellEditor 
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
	public CheckBoxCellEditor() 
	{
		super(new JCheckBox());
		this.checkBox = (JCheckBox)this.getComponent();
		this.panel = new JPanel();
		this.checkBox.setHorizontalAlignment(AbstractButton.CENTER);
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.panel.add(this.checkBox,BorderLayout.CENTER);
		
		this.setClickCountToStart(2);
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================

	@Override
	public Object getCellEditorValue()
	{
		return DBConnector.convertBooleanForDB(checkBox.isSelected());
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
		boolean b = DBConnector.convertBooleanFromDB(value);
		checkBox.setBackground(SystemColor.BLUE);
		checkBox.setSelected(b);
		
		return panel;
	}
	
}
