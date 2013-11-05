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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jhv.swing.gridbag.GridBagConstraintsFactory;
import jhv.swing.gridbag.GridBagPanel;

import dsagenesis.core.sqlite.DBConnector;
import dsagenesis.core.sqlite.TableHelper;
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
	
	private JList<SelectionItem> listEntries;
		
	/**
	 * contains all tables and labels for the table selection.
	 */
	private Vector<Vector<String>> tableList;
	
	/**
	 * contains all column labels and names for the current selected table.
	 */
	private Vector<Vector<String>> columnList;
	
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

		listEntries = new JList<SelectionItem>();
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
		DefaultListModel<SelectionItem> listModel = new DefaultListModel<SelectionItem>();
		listEntries.setModel(listModel);
		listEntries.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
				public void valueChanged(ListSelectionEvent lse) {
					queryTableColumns(lse.getFirstIndex());
				}
			});

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
		btnSort.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) 
				{
					sortUserItems();
				}
			});
		
		gbcf.nextX();
		
		btnRemove = gbcf.addButton(
				labelResource.getProperty("btnRemove","btnRemove"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		btnRemove.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) 
				{
					removeUserItem();
				}
			});
		
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
		btnAdd.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) 
				{
					addUserItem();
				}
			});	
		
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
		DefaultListModel<SelectionItem> listModel 
				= (DefaultListModel<SelectionItem>)listEntries.getModel();
		listModel.clear();
		
		this.comboBoxTableColumn.setEnabled(false);
		this.comboBoxColumnValue.setEnabled(false);
		this.txtUserInput.setEnabled(false);
		this.txtUserInput.setText("");
		this.btnAdd.setEnabled(false);
		this.btnRemove.setEnabled(false);
		this.btnSort.setEnabled(false);
		
		if( selection.getType().equals(Selection.TYPE_TABLE) )
		{
			// Table
			this.comboBoxTableColumn.setEnabled(true);
			this.comboBoxColumnValue.setEnabled(true);
			
			Vector<String> item;
			for( int i=0; i<tableList.size(); i++ )
			{
				item = tableList.get(i);
				listModel.addElement( new SelectionItem(item.get(1), item.get(2)) );
			}
			// TODO select item here?
		} else {
			// User
			this.txtUserInput.setEnabled(true);
			this.btnAdd.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.btnSort.setEnabled(true);	
		}
	}
	
	/**
	 * queryTables
	 */
	private void queryTables()
	{
		String query = "SELECT ID, ti_table_name, ti_label FROM CoreDataTableIndex" 
				+ " WHERE ti_editable=1"
				+ " ORDER BY ti_label ASC";
		
		try 
		{
			ResultSet rs = DBConnector.getInstance().executeQuery(query);
			tableList = new Vector<Vector<String>>();
			
			while( rs.next() )
			{
				String id = rs.getString("ID");
				String tablename = rs.getString("ti_table_name");
				String label = rs.getString("ti_label");
				
				Vector<String> entry = new Vector<String>();
				entry.add(id);
				entry.add(tablename);
				entry.add(label);
				
				tableList.add(entry);
			}
			
		} catch( SQLException e ) {
			// nothing to do 
		}
	}
	
	/**
	 * queryTableColumns
	 * 
	 * queries the TableColumns
	 * and fills the comboBoxTableColumn
	 * 
	 * @param listIndex
	 */
	private void queryTableColumns(int listIndex)
	{
		comboBoxTableColumn.removeAllItems();
		comboBoxColumnValue.removeAllItems();
		
		if( comboBoxType.getSelectedIndex() != 0 )
			return;
		
		if( listEntries == null || listEntries.getSelectedValue() == null )
			return;
		
		if( listIndex < 0 )
			return;
		
		try 
		{
			columnList = TableHelper.getColumnLabelsForTable(
					listEntries.getSelectedValue().id
				);
			
			// add no filter selection
			comboBoxTableColumn.addItem(
					labelResource.getProperty("tableColumn.noFilter","tableColumn.noFilter")
				);
			
			for( int i=0; i<columnList.size(); i++ )
				comboBoxTableColumn.addItem(columnList.get(i).get(1));
			
		} catch (SQLException e) {
		
		}
		
		
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
		if( this.selection.getType().equals(Selection.TYPE_TABLE) )
		{
			if( listEntries.getSelectedValue() == null )
				return new Selection();
			
			this.selection.setTableName(listEntries.getSelectedValue().id);
			
			String colName = "";
			String colValue = "";
			
			if( comboBoxColumnValue.getSelectedIndex() > 0 )
			{
				colName = columnList.get(comboBoxColumnValue.getSelectedIndex()).get(0);
				colValue = comboBoxColumnValue.getSelectedItem().toString();
			}
			this.selection.setColumnName(colName);
			this.selection.setColumnValue(colValue);
			
		} else {
			
			Vector<String> userSelections = new Vector<String>();
			DefaultListModel<SelectionItem> listModel 
				= (DefaultListModel<SelectionItem>)listEntries.getModel();
	
			for( int i=0; i<listModel.size(); i++ )
				userSelections.add(listModel.get(i).id);
			
			this.selection.setUserSelections(userSelections);
			
			if( listModel.size() == 0 )
				return new Selection();
		}
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
			this.selection.setType(Selection.TYPE_USER);
		
		try 
		{
			this.selection.queryLabels();
		} catch (SQLException e) {
		
		}
		
		queryTables();
		updateLayout();
		
		if( this.selection.getType().equals(Selection.TYPE_USER) )
		{
			this.comboBoxType.setSelectedIndex(1);
			
			DefaultListModel<SelectionItem> listModel 
					= (DefaultListModel<SelectionItem>)listEntries.getModel();

			Vector<String> userSelections = this.selection.getUserSelections();
			
			for( int i=0; i< userSelections.size(); i++)
				listModel.addElement(new SelectionItem(
						userSelections.get(i), 
						userSelections.get(i)
					));
			
		} else {
			this.comboBoxType.setSelectedIndex(0);
			
			
			// TODO read table selection
			
		}
		
	}
	
	private void changeType(int index)
	{
		String newType;
		
		if( index == 0 )
		{
			newType = Selection.TYPE_TABLE;
		} else {
			newType = Selection.TYPE_USER;
		}
		
		if( newType.equals(selection.getType()) )
			return;
		
		selection.setType(newType);
		updateLayout();
	}
	
	private void changeTableColumn(String col)
	{
		// TODO
		
	}

	private void addUserItem()
	{
		String input = this.txtUserInput.getText().trim();
		
		if( input.equals("") )
			return;
		
		DefaultListModel<SelectionItem> listModel 
				= (DefaultListModel<SelectionItem>)listEntries.getModel();
		listModel.addElement(new SelectionItem(input,input));
		
		this.txtUserInput.setText("");
	}
	
	private void removeUserItem()
	{
		if( listEntries.getSelectedIndex() < 0 )
			return;
		
		DefaultListModel<SelectionItem> listModel 
				= (DefaultListModel<SelectionItem>)listEntries.getModel();
		
		listModel.remove(listEntries.getSelectedIndex());
	}
		
	private void sortUserItems()
	{
		if( listEntries.getModel().getSize() < 2 )
			return;
		
		DefaultListModel<SelectionItem> listModel 
			= (DefaultListModel<SelectionItem>)listEntries.getModel();
		
		ArrayList<SelectionItem> arr = new ArrayList<SelectionItem>();
		
		for(int i=0; i< listModel.size(); i++ )
			arr.add(listModel.get(i));
		
		listModel.clear();
		Collections.sort(arr);
		
		for(int i=0; i< arr.size(); i++ )
			listModel.addElement(arr.get(i));
		
	}
	
	
	
	@Override
	public void itemStateChanged(ItemEvent ie)
	{
		if( ie.getSource() == comboBoxType )
		{
			changeType( comboBoxType.getSelectedIndex() );
			
		} else if( ie.getSource() == comboBoxTableColumn ) {
			changeTableColumn( (String)comboBoxTableColumn.getSelectedItem() );
		}
	}
	
	
	// ============================================================================
	//  Inner Classes
	// ============================================================================
	
	/**
	 * used for JList listEntries
	 */
	private class SelectionItem
			implements Comparable<SelectionItem>
	{
		
		public String id;
		public String label;
		
		public SelectionItem( String id, String label )
		{
			this.id = id;
			this.label = label;
		}
				
		@Override
		public int compareTo(SelectionItem si) 
		{
			return this.id.compareTo(si.id);
		}
		
		public String toString()
		{
			return this.label;
		}
		
	}
	
}
