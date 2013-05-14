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
 *
 */
@SuppressWarnings("rawtypes")
public class CoreDataTableModel 
		extends DefaultTableModel 
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;
	
	private boolean isReadonly = false;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor 1.
	 */
	public CoreDataTableModel() 
	{
		super();
	}

	/**
	 * Constructor 2.
	 * 
	 * @param rowCount
	 * @param columnCount
	 */
	public CoreDataTableModel(int rowCount, int columnCount)
	{
		super(rowCount, columnCount);
	}
	
	/**
	 * Constructor 3.
	 * 
	 * @param columnNames
	 * @param rowCount
	 */
	public CoreDataTableModel(Vector columnNames, int rowCount) 
	{
		super(columnNames, rowCount);
	}

	/**
	 * Constructor 4.
	 * 
	 * @param columnNames
	 * @param rowCount
	 */
	public CoreDataTableModel(Object[] columnNames, int rowCount) 
	{
		super(columnNames, rowCount);
	}

	/**
	 * Constructor 5.
	 * 
	 * @param data
	 * @param columnNames
	 */
	public CoreDataTableModel(Vector data, Vector columnNames) 
	{
		super(data, columnNames);
	}

	/**
	 * Constructor 6.
	 * 
	 * @param data
	 * @param columnNames
	 */
	public CoreDataTableModel(Object[][] data, Object[] columnNames) 
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
