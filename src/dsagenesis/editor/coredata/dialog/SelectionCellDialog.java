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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

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
		
// TODO layout		
		gbcf.nextLine();
		gbcf.addFooter(GridBagConstraintsFactory.CURRENT);
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
		// TODO Auto-generated method stub
	}

}
