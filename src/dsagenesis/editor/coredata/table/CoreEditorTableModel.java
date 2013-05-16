/*
 *  __  __      
 * /\ \/\ \  __________   
 * \ \ \_\ \/_______  /\   
 *  \ \  _  \  ____/ / /  
 *   \ \_\ \_\ \ \/ / / 
 *    \/_/\/_/\ \ \/ /  
 *             \ \  /
 *              \_\/
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
package dsagenesis.editor.coredata.table;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * JTableModel
 * 
 * Our default table model used by swing.table  
 * in our core data edtior
 */
public class CoreEditorTableModel 
		extends DefaultTableModel 
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;
	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	private boolean isReadonly = false;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor 1.
	 */
	public CoreEditorTableModel() 
	{
		super();
	}

	/**
	 * Constructor 2.
	 * 
	 * @param rowCount
	 * @param columnCount
	 */
	public CoreEditorTableModel(int rowCount, int columnCount)
	{
		super(rowCount, columnCount);
	}
	
	/**
	 * Constructor 2.
	 * 
	 * @param columnNames
	 * @param rowCount
	 */
	public CoreEditorTableModel(Vector<String> columnNames, int rowCount) 
	{
		super(columnNames, rowCount);
	}

	/**
	 * Constructor 3.
	 * 
	 * @param data
	 * @param columnNames
	 */
	public CoreEditorTableModel( Vector<Vector<Object>> data, Vector<String> columnNames) 
	{
		super(data, columnNames);
	}

	
	
	// ============================================================================
	//  Functions
	// ============================================================================
		
	/**
	 * isCellEditable
	 * 
	 * overridden to prevent that the id can change by hand.
	 * 
	 * @param row
	 * @param col
	 */
	@Override
	public boolean isCellEditable(int row, int col) 
	{
		if( 0 == col || isReadonly )
			return false;
		else
			return true;
	}

	/**
	 * setReadOnly
	 * 
	 * @param val
	 */
	public void setReadOnly(boolean val)
	{
		isReadonly = val;
	}

}
