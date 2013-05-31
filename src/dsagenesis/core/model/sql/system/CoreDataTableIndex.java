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
 * CoreDataTableIndex
 * 
 * SQL Model Class.
 * This Table is a special since it is used only by the Core Data Editor
 * to administrate the other tables the user can alter with the Editor.
 */
public class CoreDataTableIndex 
		extends AbstractSQLTableModel 
{
	// ============================================================================
	//  Variables
	// ============================================================================
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor
	 */
	public CoreDataTableIndex() 
	{
		super();
		
		this.prefix = "ti_";
		this.usesPrefix = false;
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	@Override
	protected void setupDBColumns() 
	{
		super.setupDBColumns();
		this.dbColumnNames.addElement("ti_table_name");
		this.dbColumnNames.addElement("ti_uses_prefix");
		this.dbColumnNames.addElement("ti_prefix");
		this.dbColumnNames.addElement("ti_last_index_num");
		this.dbColumnNames.addElement("ti_label");
		this.dbColumnNames.addElement("ti_note");
		this.dbColumnNames.addElement("ti_tab_index");
		this.dbColumnNames.addElement("ti_editable");
	}

	@Override
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		super.setupJTableColumnModels(ceframe, cetable);
		
		TableColumn currColumn;
        
        // ti_table_name col 1
        currColumn = cetable.getColumnModel().getColumn(1);
        currColumn.setMinWidth(120);
        
        // ti_table_name col 5
        currColumn = cetable.getColumnModel().getColumn(5);
        currColumn.setMinWidth(120);
        
        // ti_table_name col 6
        currColumn = cetable.getColumnModel().getColumn(6);
        currColumn.setMinWidth(120);
    }
	
	@Override
	public Vector<Class<?>> getTableColumnClasses()
	{
		Vector<Class<?>> vec = super.getTableColumnClasses();
		vec.add(String.class);
		vec.add(Boolean.class);
		vec.add(String.class);
		vec.add(Integer.class);
		vec.add(String.class);
		vec.add(String.class);
		vec.add(Boolean.class);
		vec.add(Boolean.class);
		
		return vec;
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
	public void queryReferences() throws SQLException
	{
		// not needed
	}
	

}
