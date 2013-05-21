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
package dsagenesis.core.model.sql;

import java.util.Vector;

import dsagenesis.core.model.xml.AbstractGenesisModel;

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
		
		this.dbColumnNames = new Vector<String>();
		this.dbColumnNames.addElement("ID");
		this.dbColumnNames.addElement("ti_table_name");
		this.dbColumnNames.addElement("ti_uses_prefix");
		this.dbColumnNames.addElement("ti_prefix");
		this.dbColumnNames.addElement("ti_last_index_num");
		this.dbColumnNames.addElement("ti_label");
		this.dbColumnNames.addElement("ti_note");
		this.dbColumnNames.addElement("ti_is_internal");
		this.dbColumnNames.addElement("ti_tab_index");
		this.dbColumnNames.addElement("ti_editable");
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================
		
	/**
	 * since this is a system table there is no AbstractGenesisModel 
	 */
	public AbstractGenesisModel getRow(String id)
	{
		return null;
	}
	
	@Override
	public Vector<Class<?>> getTableColumnClasses()
	{
		Vector<Class<?>> vec = new Vector<Class<?>>(this.dbColumnNames.size());
		vec.add(Integer.class);
		vec.add(String.class);
		vec.add(Boolean.class);
		vec.add(String.class);
		vec.add(Integer.class);
		vec.add(String.class);
		vec.add(String.class);
		vec.add(Boolean.class);
		vec.add(Integer.class);
		vec.add(Boolean.class);
		
		return vec;
	}
	

}
