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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import dsagenesis.editor.coredata.CoreEditorFrame;

/**
 * JunctionCellDialog
 * 
 * for editing N-N junctions.
 */
public class JunctionCellDialog 
		extends AbstractCellDialog
{
	// ============================================================================
	//  Constants
	// ============================================================================
			
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
			
	private Vector<Vector<Object>> idLabelPairs;

	private JComboBox<Object> comboBox;
	
	private JList<Object> list;
	
	private JButton btnAdd;
	
	private JButton btnAddAll;
	
	private JButton btnRemove;
	
	private JButton btnRemoveAll;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor
	 * 
	 * @param f
	 * @param title
	 * @param idLabels
	 */
	public JunctionCellDialog(
			CoreEditorFrame f, 
			String title,
			Vector<Vector<Object>> idLabels
		) 
	{
		super(f,400,400);
		
		this.setTitle(
				labelResource.getProperty("title", "title") 
				+ " "+ title
			);
		this.idLabelPairs = idLabels;
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weighty = 0;

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.8;
		gbc.insets = new Insets(10,10,0,10);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		
		comboBox = new JComboBox<Object>();
		panel.add(comboBox, gbc);
		
		for(int i=0; i< idLabelPairs.size(); i++ )
			comboBox.addItem(idLabels.get(i).get(1));
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		
		btnAdd = new JButton(labelResource.getProperty("btnAdd", "btnAdd"));
		btnAdd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				actionAdd();
			}
		});
		panel.add(btnAdd, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		
		btnAddAll = new JButton(labelResource.getProperty("btnAddAll", "btnAddAll"));
		btnAddAll.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					actionAddAll();
				}
			});
		panel.add(btnAddAll, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		
		list = new JList<Object>(new DefaultListModel<Object>());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(12);
		JScrollPane scrollPane = new JScrollPane(list);
		panel.add(scrollPane, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		
		btnRemove = new JButton(labelResource.getProperty("btnRemove", "btnRemove"));
		btnRemove.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					actionRemove();
				}
			});
		panel.add(btnRemove, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		
		btnRemoveAll = new JButton(labelResource.getProperty("btnRemoveAll", "btnRemoveAll"));
		btnRemoveAll.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				actionRemoveAll();
			}
		});
		panel.add(btnRemoveAll, gbc);
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	@Override
	@SuppressWarnings("unchecked")
	public void setValue(Object value) 
	{
		DefaultListModel<Object> model = (DefaultListModel<Object>)list.getModel();
		model.removeAllElements();
		
		if( value == null )
			return;
		
		Vector<Object>selectedIds = (Vector<Object>)value;
		for( int i=0; i<selectedIds.size(); i++ )
		{
			for( int j=0; j<idLabelPairs.size(); j++ )
			{
				if( idLabelPairs.get(j).get(0).equals(selectedIds.get(i)) )
				{
					model.addElement(idLabelPairs.get(j).get(1));
					break;
				}
			}
		}
		list.setModel(model);
	}
	
	@Override
	public Object getValue() 
	{
		Vector<Object> selectedIds = new Vector<Object>();
		
		for( int i=0; i<list.getModel().getSize(); i++ )
		{
			for( int j=0; j<idLabelPairs.size(); j++ )
			{
				if( idLabelPairs.get(j).get(1).equals(list.getModel().getElementAt(i)) )
				{
					selectedIds.addElement(idLabelPairs.get(j).get(0));
					break;
				}
			}
		}
		
		return selectedIds;
	}
	
	/**
	 * actionAdd
	 */
	private void actionAdd()
	{
		int idx = comboBox.getSelectedIndex();
		Object label = idLabelPairs.get(idx).get(1);
		DefaultListModel<Object> model = (DefaultListModel<Object>)list.getModel();
		
		if( model.contains(label) )
			return;
		
		model.addElement(label);
	}
	
	/**
	 * actionAddAll
	 */
	private void actionAddAll()
	{
		for( int i=0; i< idLabelPairs.size(); i++ )
		{
			Object label = idLabelPairs.get(i).get(1);
			DefaultListModel<Object> model = (DefaultListModel<Object>)list.getModel();
		
			if( !model.contains(label) )
				model.addElement(label);
		}
	}
	
	/**
	 * actionRemove
	 */
	private void actionRemove()
	{
		DefaultListModel<Object> model = (DefaultListModel<Object>)list.getModel();
		
		if( list.getSelectedIndex() > -1 )
			model.remove(list.getSelectedIndex());
	}
	
	/**
	 * actionRemoveAll
	 */
	private void actionRemoveAll()
	{
		DefaultListModel<Object> model = (DefaultListModel<Object>)list.getModel();
		model.removeAllElements();
	}

}
