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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import jhv.swing.gridbag.GridBagConstraintsFactory;
import jhv.swing.gridbag.GridBagPanel;

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
		
		GridBagPanel panel = new GridBagPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagConstraints gbc = panel.getConstraints();
		GridBagConstraintsFactory gbcf = new GridBagConstraintsFactory(panel,gbc,2);
		
		comboBox = gbcf.addComboBox(
				null, 
				null, 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.USE_FULL_WIDTH
			);
		for(int i=0; i< idLabelPairs.size(); i++ )
			comboBox.addItem(idLabels.get(i).get(1));
		
		gbcf.nextLine();
		
		btnAdd = gbcf.addButton(
				labelResource.getProperty("btnAdd", "btnAdd"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT,
				1
			);
		btnAdd.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					actionAdd();
				}
			});
		
		gbcf.nextX();
		
		btnAddAll =  gbcf.addButton(
				labelResource.getProperty("btnAddAll", "btnAddAll"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT,
				1
			);
		btnAddAll.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					actionAddAll();
				}
			});
		
		gbcf.nextLine();
		
		list = new JList<Object>(new DefaultListModel<Object>());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(12);
		JScrollPane scrollPane = new JScrollPane(list);
		gbcf.addComponent(
				scrollPane,
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT,
				GridBagConstraintsFactory.USE_FULL_WIDTH
			);
		
		gbcf.nextLine();
		
		btnRemove = gbcf.addButton(
				labelResource.getProperty("btnRemove", "btnRemove"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT,
				1
			);
		btnRemove.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					actionRemove();
				}
			});
		
		gbcf.nextX();
		
		btnRemoveAll = gbcf.addButton(
				labelResource.getProperty("btnRemoveAll", "btnRemoveAll"), 
				GridBagConstraintsFactory.CURRENT, 
				GridBagConstraintsFactory.CURRENT,
				1
			);
		btnRemoveAll.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				actionRemoveAll();
			}
		});
		
		gbcf.nextLine();
		gbcf.addFooter(GridBagConstraintsFactory.CURRENT);
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
