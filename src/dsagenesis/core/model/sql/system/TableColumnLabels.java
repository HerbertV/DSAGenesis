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
package dsagenesis.core.model.sql.system;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.TableColumn;

import dsagenesis.core.model.sql.AbstractSQLTableModel;
import dsagenesis.core.model.xml.AbstractGenesisModel;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;

/**
 * TableColumnLabels
 * 
 * SQL Model Class.
 * 
 * This table is used to store the labels for each non-system
 * table column.
 */
public class TableColumnLabels
		extends AbstractSQLTableModel 
{
	// ============================================================================
	//  Variables
	// ============================================================================
		
	// ============================================================================
	//  Constructors
	// ============================================================================
			
	/**
	 * Constructor.	
	 */
	public TableColumnLabels()
	{
		super();
		
		this.prefix = "tcl_";
		this.usesPrefix = false;
		this.isEditable = true;
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================

	@Override
	protected void setupDBColumns()
	{
		super.setupDBColumns();
		this.dbColumnNames.addElement("tcl_table_name");
		this.dbColumnNames.addElement("tcl_column_name");
		this.dbColumnNames.addElement("tcl_label");
	}
	
	/**
	 * since this is a system table there is no AbstractGenesisModel 
	 */
	@Override
	public AbstractGenesisModel getRow(String id) 
	{
		return null;
	}
	
	@Override
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		super.setupJTableColumnModels(ceframe, cetable);
		
		TableColumn currColumn;
        
        currColumn = cetable.getColumnModel().getColumn(1);
        currColumn.setMinWidth(150);
        currColumn = cetable.getColumnModel().getColumn(2);
        currColumn.setMinWidth(150);
        currColumn = cetable.getColumnModel().getColumn(3);
        currColumn.setMinWidth(150);
    }
	
	@Override
	public Vector<Class<?>> getTableColumnClasses()
	{
		Vector<Class<?>> vec = super.getTableColumnClasses();
		vec.add(String.class);
		vec.add(String.class);
		vec.add(String.class);
		
		return vec;
	}

	@Override
	public void queryReferences() throws SQLException
	{
		// not needed
	}

}
