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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dsagenesis.core.model.xml.AbstractGenesisModel;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;

/**
 * ScriptTriggers
 * 
 * SQL Model Class.
 * 
 * For assigning triggered scripts.
 */
public class ScriptTriggers 
		extends AbstractSQLTableModel 
{
	// ============================================================================
	//  Variables
	// ============================================================================
		
	// ============================================================================
	//  Constructors
	// ============================================================================

	/**
	 * Constructor 1.
	 */
	public ScriptTriggers() 
	{
		super();
	}

	/**
	 * Constructor 2.
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	public ScriptTriggers(ResultSet rs) 
			throws SQLException 
	{
		super(rs);
	}

	// ============================================================================
	//  Functions
	// ============================================================================
	
		@Override
	protected void setupDBColumns() 
	{
		super.setupDBColumns();
		
		this.dbColumnNames.addElement("sctr_table_name");
		this.dbColumnNames.addElement("sctr_ref_id");
		this.dbColumnNames.addElement("sctr_event");
		this.dbColumnNames.addElement("sctr_script");
	}

	@Override
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		super.setupJTableColumnModels(ceframe, cetable);
		
		// TODO
	}
	
	@Override
	public Vector<Class<?>> getTableColumnClasses()
	{
		Vector<Class<?>> vec = super.getTableColumnClasses();
		vec.add(String.class);
		vec.add(String.class);
		vec.add(String.class);
		vec.add(String.class);
		
		return vec;
	}

	/**
	 * for accessing the SKTShifts use class SKTMatrix. 
	 */
	@Override
	public AbstractGenesisModel getRow(String id) 
	{
		return null;
	}	
	
	
	@Override
	public void queryReferences() throws SQLException
	{
		// TODO Auto-generated method stub
		
	}

}
