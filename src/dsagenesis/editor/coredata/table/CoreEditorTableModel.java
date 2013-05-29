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
package dsagenesis.editor.coredata.table;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import dsagenesis.core.sqlite.TableHelper;

/**
 * JTableModel
 * 
 * Our default table model used by swing.table  
 * in our core data editor
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
	
	private Vector<Class<?>> columnClasses;

	private CoreEditorTable table;
	
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
	 * In most cases we use this constructor
	 * 
	 * @param data
	 * @param columnNames
	 * @param columnClasses
	 */
	public CoreEditorTableModel( 
			Vector<Vector<Object>> data, 
			Vector<String> columnNames,
			Vector<Class<?>> columnClasses,
			CoreEditorTable table
		) 
	{
		super(data, columnNames);
		this.columnClasses = columnClasses;
		this.table = table;
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
	 * @param column
	 */
	@Override
	public boolean isCellEditable(int row, int column) 
	{
		if( isReadonly )
			return false;
		
		if( column != 0 )
			return true;
		
		String tablename = table.getSQLTable().getDBTableName();
		
		if( this.getValueAt(row, column) == null )
			return true;
		
		String id = this.getValueAt(row, column).toString();
		
		if( TableHelper.idExists(id, tablename) )
			return false;
		
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
	
	/**
	 * isReadOnly
	 * 
	 * @return
	 */
	public boolean isReadOnly()
	{
		return isReadonly;
	}
	
	/**
	 * getTable
	 * 
	 * @return
	 */
	public CoreEditorTable getTable()
	{
		return this.table;
	}
	
	@Override
	public Class<?> getColumnClass(int column)
	{
		return this.columnClasses.elementAt(column);
	}
}
