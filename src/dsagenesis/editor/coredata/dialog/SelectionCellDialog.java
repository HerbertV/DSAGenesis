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
package dsagenesis.editor.coredata.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import jhv.swing.gridbag.GridBagConstraintsFactory;
import jhv.swing.gridbag.GridBagPanel;

import dsagenesis.core.util.logic.Selection;
import dsagenesis.editor.coredata.CoreEditorFrame;

/**
 * SelectionCellDialog
 */
public class SelectionCellDialog 
		extends AbstractCellDialog 
		implements ItemListener
{
	// ============================================================================
	//  Constants
	// ============================================================================
			
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	private String titleColumnPart;
	
	private JComboBox<Object> comboBoxType;
	
	private Object[] types;
	
	// TODO inner class for type
	private JList listEntries;
		
	private JComboBox<Object> comboBoxTableColumn;
	
	private JComboBox<Object> comboBoxColumnValue;
	
	private JTextField txtUserInput;
	
	private JButton btnAdd;
	
	private JButton btnRemove;
	
	private JButton btnSort;
		
	/**
	 * the selection
	 */
	private Selection selection;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 * 
	 * @param f
	 * @param title
	 */
	public SelectionCellDialog(
			CoreEditorFrame f, 
			String title
		) 
	{
		super(f, 500, 500);
		
		this.titleColumnPart = title;
		
		types = new Object[2];
		types[0] = labelResource.getProperty("type.table","type.table");
		types[1] = labelResource.getProperty("type.user","type.user");
				
		this.setTitle(
				labelResource.getProperty("title", "title") 
				+ " "+ title
			);
		
		this.setup();
	}

	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	/**
	 * setup
	 */
	private void setup()
	{
		GridBagPanel panel = new GridBagPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagConstraints gbc = panel.getConstraints();
		GridBagConstraintsFactory gbcf = new GridBagConstraintsFactory(panel,gbc,3);
		
		JLabel lbl = gbcf.addLabel(
				labelResource.getProperty("lblType","lblType"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		lbl.setHorizontalAlignment(JLabel.RIGHT);
		
		gbcf.nextX();
		
		comboBoxType = gbcf.addComboBox(
				types, 
				null, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		comboBoxType.addItemListener(this);
		
		gbcf.nextLine();
		
		gbcf.addLabel(
				labelResource.getProperty("lblList","lblList"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.USE_FULL_WIDTH
			);
		
		gbcf.nextLine();
		
		gbcf.getConstraints().fill = GridBagConstraints.BOTH;
		gbcf.getConstraints().weightx = 1.0;
		gbcf.getConstraints().weighty = 1.0;
// TODO		
		listEntries = new JList();
		JScrollPane codeScroll = new JScrollPane(listEntries);
		codeScroll.setViewportView(listEntries);
		Dimension d = new Dimension(400,100);
		codeScroll.setPreferredSize(d);
		codeScroll.setMinimumSize(d);
		gbcf.addComponent(
				codeScroll, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.USE_FULL_WIDTH
			);
		

		gbcf.nextLine();
		
		gbcf.getConstraints().fill = GridBagConstraints.HORIZONTAL;
		gbcf.getConstraints().weighty = 0.0;		
		gbcf.getConstraints().weightx = 0.8;
		
		gbcf.addLabel(
				labelResource.getProperty("lblUser","lblUser"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.USE_FULL_WIDTH
			);
		
		gbcf.nextLine();
		gbcf.nextX();
		
		btnSort = gbcf.addButton(
				labelResource.getProperty("btnSort","btnSort"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		// TODO actionListener
		
		gbcf.nextX();
		
		btnRemove = gbcf.addButton(
				labelResource.getProperty("btnRemove","btnRemove"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		// TODO actionListener
		
		gbcf.nextLine();
		
		txtUserInput = gbcf.addInput(
				"", 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				2
			);
		
		gbcf.nextX();
		gbcf.nextX();
		
		btnAdd = gbcf.addButton(
				labelResource.getProperty("btnAdd","btnAdd"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		// TODO actionListener		
		
		gbcf.nextLine();
		gbcf.addHorizontalSeparator(GridBagConstraintsFactory.CURRENT);
		
		gbcf.nextLine();
		
		gbcf.addLabel(
				labelResource.getProperty("lblTable","lblTable"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.USE_FULL_WIDTH
			);
		
		gbcf.nextLine();
		
		gbcf.addLabel(
				labelResource.getProperty("lblTableColumn","lblTableColumn"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		
		gbcf.nextX();
		
		gbcf.addLabel(
				labelResource.getProperty("lblColumnValue","lblColumnValue"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		
		gbcf.nextLine();
		
		comboBoxTableColumn = gbcf.addComboBox(
				null, 
				null, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		comboBoxTableColumn.addItemListener(this);
		
		gbcf.nextX();
		
		comboBoxColumnValue = gbcf.addComboBox(
				null, 
				null, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		comboBoxColumnValue.setEditable(true);
		comboBoxColumnValue.addItemListener(this);
		
		gbcf.nextLine();
		gbcf.addFooter(GridBagConstraintsFactory.CURRENT);
	}
	
	/**
	 * called everytime comboBoxTypeChanged
	 */
	private void updateLayout()
	{
		// TODO updateLayout
	}
	
	private void setupTables()
	{
		// TODO query and store all tables (with labels) selectable except the same and internal tables.
	}
	
	/**
	 * setRowParams
	 * 
	 * @param id
	 * @param name
	 */
	public void setRowParams(String id, String name) 
	{
		this.setTitle(
				labelResource.getProperty("title", "title") 
				+ " " + titleColumnPart 
				+ " - " + name 
				+ " ( " + id + " ) "
			);
	}
	
	@Override
	public Object getValue() 
	{
		return this.selection;
	}

	@Override
	public void setValue(Object value) 
	{
		if( value != null )
		{
			this.selection = (Selection)value;
		} else {
			this.selection = new Selection();
		}
		
		if( this.selection.isEmpty() )
		{
			// TODO
		}
		
		try 
		{
			this.selection.queryLabels();
		} catch (SQLException e) {
		
		}
		
		// TODO
	}

	@Override
	public void itemStateChanged(ItemEvent ie)
	{
		// TODO comboboxtype changed
		
		// TODO cb Table column changed
				
	}

}
