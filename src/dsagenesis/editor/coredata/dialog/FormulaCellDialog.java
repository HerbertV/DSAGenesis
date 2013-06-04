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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import dsagenesis.editor.coredata.CoreEditorFrame;

/**
 * FormulaCellDialog
 */
public class FormulaCellDialog 
		extends AbstractCellDialog 
{
	// ============================================================================
	//  Constants
	// ============================================================================
			
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	/**
	 * current row Id
	 */
	private String rowId;
	
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
	
	private JComboBox<String> comboBoxTables;
	
	private JComboBox<String> comboBoxEntries;
	
	private JTextArea txtCode;
	
	private JButton btnAddArgument;
	
	private JButton btnRemoveArgument;
	
	private JButton btnTest;
	
	private JTextField txtTestOutput;
	
	private JList<Object> listUsedArguments;
	
	
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
	 */
	public FormulaCellDialog(
			CoreEditorFrame f, 
			String title,
			Vector<Vector<String>> allowedTables
		) 
	{
		super(f, 500, 450);
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
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weighty = 0;
		gbc.ipadx = 5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.8;
		gbc.insets = new Insets(10,10,0,10);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		{
			JLabel lbl = new JLabel(
					labelResource.getProperty("lblTable", "lblTable")
				);
			panel.add(lbl, gbc);
		}
		{
			gbc.gridx = 1;
			gbc.gridwidth = 1;
			JLabel lbl = new JLabel(
					labelResource.getProperty("lblEntry", "lblEntry")
				);
			panel.add(lbl, gbc);
		}
		gbc.gridx = 0;
		gbc.gridy = 1;	
		gbc.gridwidth = 1;
		comboBoxTables = new JComboBox<String>();
		panel.add(comboBoxTables, gbc);
			
		for(int i=0; i< allowedTables.size(); i++ )
			comboBoxTables.addItem(allowedTables.get(i).get(1));
			
		// TODO add change listener
		comboBoxTables.setSelectedIndex(0);
		
		gbc.gridx = 1;
		comboBoxEntries = new JComboBox<String>();
		panel.add(comboBoxEntries, gbc);
		
		gbc.weightx = 0.1;
		gbc.gridx = 2;
		btnAddArgument = new JButton(
				labelResource.getProperty("btnAddArgument", "btnAddArgument")
			);
		panel.add(btnAddArgument,gbc);
		btnAddArgument.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					actionAddArgument();
				}
			});
		
		gbc.weightx = 0.8;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		{	
			JLabel lbl = new JLabel(
					labelResource.getProperty("lblUsedArguments", "lblUsedArguments")
				);
			panel.add(lbl, gbc);
		}
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		// TODO Custom model with id label, label label and test value input
		listUsedArguments = new JList<Object>(new DefaultListModel<Object>());
		listUsedArguments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listUsedArguments.setVisibleRowCount(5);
		{
			JScrollPane scrollPane = new JScrollPane(listUsedArguments);
			panel.add(scrollPane, gbc);
		}
		
		gbc.weightx = 0.1;
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 4;
		btnRemoveArgument = new JButton(
				labelResource.getProperty("btnRemoveArgument", "btnRemoveArgument")
			);
		panel.add(btnRemoveArgument,gbc);
		btnRemoveArgument.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					actionRemoveArgument();
				}
			});
	
		gbc.weightx = 0.8;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 5;
		{
			JLabel lbl = new JLabel(
					labelResource.getProperty("lblScript", "lblScript")
					);
			panel.add(lbl, gbc);
		}
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		txtCode = new JTextArea(3,1);
		{
			JScrollPane scrollPane = new JScrollPane(txtCode);
			panel.add(scrollPane, gbc);
		}
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 7;
		{	
			JLabel lbl = new JLabel(
					labelResource.getProperty("lblOutput", "lblOutput")
				);
			lbl.setHorizontalAlignment(JLabel.RIGHT);
			panel.add(lbl, gbc);
		}
		gbc.gridx = 1;
		txtTestOutput = new JTextField("");
		txtTestOutput.setEditable(false);
		panel.add(txtTestOutput, gbc);
		
		gbc.gridx = 2;
		btnTest = new JButton(
				labelResource.getProperty("btnTest", "btnTest")
			);
		panel.add(btnTest,gbc);
		btnTest.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				actionTest();
			}
		});
		
		gbc.gridwidth = 3;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 8;
		panel.add(new JPanel(),gbc);
		gbc.weighty = 0;
		gbc.gridx = 0;
		gbc.gridy = 9;
		JSeparator s = new JSeparator(SwingConstants.HORIZONTAL);
		panel.add(s,gbc);
		
	}
	
	
	/**
	 * setRowParams
	 * 
	 * additional params:
	 * id and name of the currently edited row
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
			);
		
		rowId = id;
	}
	
	@Override
	public Object getValue() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(Object value) 
	{
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * actionAddArgument
	 */
	private void actionAddArgument()
	{
		// TODO
	}

	/**
	 * actionRemoveArgument
	 */
	private void actionRemoveArgument()
	{
		// TODO
	}
	
	/**
	 * actionTest
	 */
	private void actionTest()
	{
		// TODO
	}
}
