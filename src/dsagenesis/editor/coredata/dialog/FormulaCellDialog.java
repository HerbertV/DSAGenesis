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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jhv.image.ImageResource;
import jhv.swing.gridbag.GridBagConstraintsFactory;
import jhv.swing.gridbag.GridBagPanel;
import jhv.util.debug.logger.ApplicationLogger;

import dsagenesis.core.sqlite.DBConnector;
import dsagenesis.core.ui.list.AbstractComponentListItem;
import dsagenesis.core.ui.list.ComponentList;
import dsagenesis.core.util.logic.Formula;
import dsagenesis.editor.coredata.CoreEditorFrame;

/**
 * FormulaCellDialog
 */
public class FormulaCellDialog 
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
	
	/**
	 * ok icon
	 */
	private static ImageIcon iconOk;
	
	/**
	 * error icon
	 */
	private static ImageIcon iconError;

	
	/**
	 * current row Id
	 */
	private String rowId;
	
	/**
	 * current row name
	 */
	private String rowName;
	
	/**
	 * current row columns used for label
	 */
	private String rowColumn;
	
	
	private String titleColumnPart;
	
	/**
	 * Vector Vector of table names and their column name used as label
	 * 
	 * allowedTables format:
	 * [0] db tablename
	 * [1] table label
	 * [2] column used as label for entries
	 */
	private Vector<Vector<String>> allowedTables;
	
	/**
	 * after selecting a table this selection is generated.
	 * 
	 */
	private Vector<Vector<String>> entrySelection;
	
	private JComboBox<Object> comboBoxTables;
	
	private JComboBox<Object> comboBoxEntries;
	
	private JLabel lblScript;
	
	private JTextArea txtCode;
	
	private JButton btnAddArgument;
	
	private JButton btnRemoveArgument;
	
	private JComboBox<Object> comboBoxReturnType;
	
	private JButton btnTest;
	
	private JTextField txtTestOutput;
	
	private ComponentList<ArgumentItem> componentList;
	
	private Formula formula;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 * 
	 * @param f
	 * @param title
	 * @param allowedTables format:
	 * 		[0] db tablename
	 * 		[1] table label
	 * 		[2] column used as label for entries
	 */
	public FormulaCellDialog(
			CoreEditorFrame f, 
			String title,
			Vector<Vector<String>> allowedTables
		) 
	{
		super(f, 500, 500);
		
		if( iconOk == null )
		{
			iconOk = new ImageResource("resources/images/icons/statusOk.gif",this).getImageIcon();
			iconError = new ImageResource("resources/images/icons/statusError.gif",this).getImageIcon();
		}
		
		this.allowedTables = allowedTables;
		this.titleColumnPart = title;
		
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
		GridBagConstraintsFactory gbcf = new GridBagConstraintsFactory(panel,gbc,5);
		
		gbcf.addLabel(
				labelResource.getProperty("lblTable", "lblTable"),
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				2
			);
		gbcf.nextX();
		gbcf.nextX();
		
		gbcf.addLabel(
				labelResource.getProperty("lblEntry", "lblEntry"),
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				2
			);
		
		gbcf.nextLine();
		
		comboBoxTables = gbcf.addComboBox(
				null, 
				null, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				2
			);
		for(int i=0; i< allowedTables.size(); i++ )
			comboBoxTables.addItem(allowedTables.get(i).get(1));
			
		comboBoxTables.addItemListener(this);
		comboBoxTables.setSelectedIndex(0);
		
		gbcf.nextX();
		gbcf.nextX();
		
		comboBoxEntries = gbcf.addComboBox(
				null, 
				null, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				2
			);
		
		gbcf.nextX();
		gbcf.nextX();
		
		gbcf.getConstraints().weightx = 0.0;
		btnAddArgument = gbcf.addButton(
				labelResource.getProperty("btnAddArgument", "btnAddArgument"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		btnAddArgument.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) 
				{
					actionAddArgument();
				}
			});
		
		gbcf.nextLine();
		
		gbcf.getConstraints().weightx = 0.8;
		gbcf.addLabel(
				labelResource.getProperty("lblUsedArguments", "lblUsedArguments"),
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.USE_FULL_WIDTH
			);
		
		gbcf.nextLine();
		
		gbcf.getConstraints().fill = GridBagConstraints.BOTH;
		gbcf.getConstraints().weightx = 1.0;
		gbcf.getConstraints().weighty = 1.0;
		{
			int visibleItems = 3;
			componentList = new ComponentList<ArgumentItem>(
					visibleItems, 
					ComponentList.MULTI_SELECTION
				);
			
			ArgumentItem tmp = new ArgumentItem("","","java.lang.Integer");
			tmp.validate();
			Dimension d = new Dimension(
					400, 
					visibleItems * (tmp.getPreferredSize().height - 1)
				);
			componentList.setPreferredSize(d);
			componentList.setMinimumSize(d);
			gbcf.addComponent(
					componentList, 
					GridBagConstraintsFactory.CURRENT, 
					GridBagConstraintsFactory.CURRENT, 
					GridBagConstraintsFactory.USE_FULL_WIDTH
				);
		}
		
		gbcf.nextLine();
		gbcf.lastX();
		
		gbcf.getConstraints().fill = GridBagConstraints.HORIZONTAL;
		gbcf.getConstraints().weightx = 0.0;
		gbcf.getConstraints().weighty = 0.0; // 0.1
		
		btnRemoveArgument = gbcf.addButton(
				labelResource.getProperty("btnRemoveArgument", "btnRemoveArgument"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		btnRemoveArgument.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) 
				{
					actionRemoveArgument();
				}
			});
	
		gbcf.nextLine();
		gbcf.getConstraints().weightx = 0.8;
		
		JLabel lbl = gbcf.addLabel(
				labelResource.getProperty("lblReturnType", "lblReturnType"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				2
			);
		lbl.setHorizontalAlignment(JLabel.RIGHT);
		gbcf.nextX();
		gbcf.nextX();
		
		
		comboBoxReturnType = gbcf.addComboBox(
				new String[]{"Integer", "Float", "String", "Boolean"}, 
				null, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			); 
		comboBoxReturnType.addItemListener(this);
		gbcf.nextLine();
		
		lblScript = gbcf.addLabel(
				labelResource.getProperty("lblScript", "lblScript"),
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.USE_FULL_WIDTH
			);
		
		gbcf.nextLine();
		
		gbcf.getConstraints().fill = GridBagConstraints.BOTH;
		gbcf.getConstraints().weightx = 1.0;
		gbcf.getConstraints().weighty = 1.0;
		
		txtCode = new JTextArea();
		JScrollPane codeScroll = new JScrollPane(txtCode);
		codeScroll.setViewportView(txtCode);
		Dimension d = new Dimension(400,65);
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
		
		lbl = gbcf.addLabel(
				labelResource.getProperty("lblOutput", "lblOutput"),
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				2
			);
		lbl.setHorizontalAlignment(JLabel.RIGHT);
	
		gbcf.nextX();
		gbcf.nextX();
		
		txtTestOutput = gbcf.addInput(
				"",
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				2
			);
		txtTestOutput.setEditable(false);
	
		gbcf.nextX();
		gbcf.nextX();
		
		gbcf.getConstraints().weightx = 0.0;
		
		btnTest = gbcf.addButton(
				labelResource.getProperty("btnTest", "btnTest"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT, 
				1
			);
		btnTest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{
				actionTest();
			}
		});
		gbcf.getConstraints().weightx = 0.8;
		
		gbcf.nextLine();
		gbcf.addFooter(GridBagConstraintsFactory.CURRENT);
	}
	
	
	/**
	 * setRowParams
	 * 
	 * additional params:
	 * id and name of the currently edited row
	 * 
	 * @param id
	 * @param name
	 * @param column
	 */
	public void setRowParams(String id, String name, String column) 
	{
		this.setTitle(
				labelResource.getProperty("title", "title") 
				+ " " + titleColumnPart 
				+ " - " + name
			);
		
		rowId = id;
		rowName = name;
		rowColumn = column;
		
		stateChangedTables();
	}
	
	@Override
	public Object getValue() 
	{
		this.formula.setScriptcode(txtCode.getText().trim());
		return this.formula;
	}

	@Override
	public void setValue(Object value) 
	{
		if( value != null )
		{
			this.formula = (Formula)value;
		} else {
			this.formula = new Formula();
		}
		
		if( this.formula.isEmpty() )
		{
			this.formula.addArgument(
					rowId, 
					"java.lang.Integer", 
					rowColumn, 
					rowName
				);
			this.formula.setScriptcode(
					rowId + " = 0;\nreturn " + rowId + ";"
				);
		}
		
		try 
		{
			this.formula.queryLabels();
		} catch (SQLException e) {
		
		}
		
		comboBoxReturnType.setSelectedItem(
				formula.getReturnType().getSimpleName()
			);
		txtCode.setText(this.formula.getScriptcode());
		Vector<Vector<String>> args = this.formula.getArguments();
		componentList.clearList();
		
		for( int i=0; i< args.size(); i++ )
		{
			componentList.addItem(new ArgumentItem(
					args.get(i).get(0),
					args.get(i).get(3),
					args.get(i).get(1)
				));
		}
		
		lblScript.setIcon(null);
	}
	
	
	/**
	 * actionAddArgument
	 */
	private void actionAddArgument()
	{
		int idx = comboBoxEntries.getSelectedIndex();
		String id = entrySelection.get(idx).get(0);
		String label = entrySelection.get(idx).get(1);
		
		ArgumentItem item = new ArgumentItem(id,label,"java.lang.Integer");
		formula.addArgument(
				id, 
				"java.lang.Integer", 
				allowedTables.get(comboBoxTables.getSelectedIndex()).get(2),
				label
			);
		componentList.addItem(item);
	}

	/**
	 * actionRemoveArgument
	 */
	private void actionRemoveArgument()
	{
		componentList.removeAllSelectedItems();
		
		Vector<ArgumentItem> items = componentList.getItems();
		Vector<Vector<String>> arguments = formula.getArguments();
		
		for( int i=(arguments.size()-1); i >-1; i-- )
		{
			boolean found = false;
			for( int j=0; j< items.size(); j++ )
			{
				if( items.get(j).lblId.getText().equals(arguments.get(i).get(0)) )
				{
					found = true;
					break;
				}
			}
			
			if( !found )
				arguments.remove(i);
		}
	}
	
	/**
	 * actionTest
	 */
	private void actionTest()
	{
		Vector<ArgumentItem> items = componentList.getItems();
		Vector<Object> values = new Vector<Object>();
		
		for( int i=0; i<items.size(); i++ )
		{
			String value = items.get(i).txtValue.getText();
			String type = (String)items.get(i).comboBox.getSelectedItem();
			
			if( type.equals("Integer") ) {
				values.add(Integer.parseInt(value));
			} else if( type.equals("Float") ) {
				values.add(Float.parseFloat(value));
			} else if( type.equals("Boolean") ) {
				values.add(Boolean.parseBoolean(value));
			} else {
				values.add(value);
			}
		}
		try
		{
			formula.setScriptcode(txtCode.getText());		
			Object result =  formula.calculate(values);
			txtTestOutput.setText(result.toString());
			lblScript.setIcon(iconOk);
			lblScript.setToolTipText(
					labelResource.getProperty("status.script.ok", "status.script.ok")
				);
			
		} catch (Exception e) {
			ApplicationLogger.logError(e);
			txtTestOutput.setText("");
			lblScript.setIcon(iconError);
			Toolkit.getDefaultToolkit().beep();
			lblScript.setToolTipText(
					"<html>"	
						+ labelResource.getProperty("status.script.error", "status.script.error")
						+ "<br>"
						+ e.getMessage()
						+ "</html>"
				);
		}
		
	}
	
	/**
	 * stateChangedTables
	 */
	private void stateChangedTables()
	{
		int idx = comboBoxTables.getSelectedIndex();
		String table = allowedTables.get(idx).get(0);
		String column = allowedTables.get(idx).get(2);
		
		String query = "SELECT ID, " 
				+ column 
				+ " FROM " 
				+ table
				+ " ORDER BY " 
				+ column 
				+ " ASC";
		
		try 
		{
			ResultSet rs = DBConnector.getInstance().executeQuery(query);
			entrySelection = new Vector<Vector<String>>();
			comboBoxEntries.removeAllItems();
			
			while( rs.next() )
			{
				String id = rs.getString("ID");
				String label = rs.getString(column);
				
				Vector<String> entry = new Vector<String>();
				entry.add(id);
				entry.add(label);
				
				entrySelection.add(entry);
				comboBoxEntries.addItem(label);
			}
			
		} catch( SQLException e ) {
			// nothing to do 
		}
	}
	
	/**
	 * stateChangedReturnType
	 */
	private void stateChangedReturnType() 
	{
		String classname = "java.lang."+comboBoxReturnType.getSelectedItem();
		
		try
		{
			formula.setReturnType(Class.forName(classname));
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent ie) 
	{
		if( ie != null )
			if( ie.getStateChange() != ItemEvent.SELECTED ) 
				return;
		
		if( ie.getSource() == comboBoxTables )
		{
			stateChangedTables();
		} else if( ie.getSource() == comboBoxReturnType ) {
			stateChangedReturnType();
		}
	}
	
	
	// ============================================================================
	//  Inner Classes
	// ============================================================================
		
	private class ArgumentItem 
			extends AbstractComponentListItem
			implements ActionListener 
	{
		
		private static final long serialVersionUID = 1L;

		JLabel lblId;
		
		JLabel lblName;
		
		JLabel lblValue;
		
		JTextField txtValue;
		
		JComboBox<String> comboBox;
		
		/**
		 * Constructor
		 * 
		 * @param id
		 * @param name
		 * @param clasName
		 */
		private ArgumentItem(String id, String name, String className) 
		{
			super(componentList);
					
			lblId = new JLabel(id);
			lblId.setPreferredSize(new Dimension(40,22));
			this.add(lblId);
			
			lblName = new JLabel(name);
			lblName.setPreferredSize(new Dimension(120,22));
			this.add(lblName);
			
			comboBox = new JComboBox<String>(new String[]{
					"Integer", "Float", "String", "Boolean" 
				});
			className = className.substring(className.lastIndexOf('.')+1);
			comboBox.setSelectedItem(className);
			comboBox.setPreferredSize(new Dimension(100,22));
			this.add(comboBox);
			comboBox.addActionListener(this);
			
			lblValue = new JLabel(labelResource.getProperty("lblTestValue","lblTestValue"));
			lblValue.setHorizontalAlignment(JLabel.RIGHT);
			lblValue.setPreferredSize(new Dimension(70,22));
			this.add(lblValue);
			
			txtValue = new JTextField("0");
			txtValue.setHorizontalAlignment(JTextField.CENTER);
			txtValue.setPreferredSize(new Dimension(50,22));
			this.add(txtValue);
		}
		
		@Override
		public void setTextColor(Color c)
		{
			lblId.setForeground(c);
			lblName.setForeground(c);
			lblValue.setForeground(c);
		}

		@Override
		public void actionPerformed(ActionEvent ae) 
		{
			Vector<String> arg = formula.getArgument(lblId.getText());
			arg.set(1, "java.lang."+comboBox.getSelectedItem());
		}
	}

}
