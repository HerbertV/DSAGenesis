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
 * SKTShifts
 * 
 * SQL Model Class.
 * 
 * this class is only used by Core Data Editor (for generating the JTable), 
 * SKTShifts are executed by SKTMatrix
 */
public class SKTShifts
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
	public SKTShifts() 
	{
		super();
	}
	
	/**
	 * Constructor 2.
	 * 
	 * @param rs	
	 * @throws SQLException 
	 */
	public SKTShifts(ResultSet rs) 
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
		this.dbColumnNames.addElement("skts_ref_source_ID");
		this.dbColumnNames.addElement("skts_target_table_name");
		this.dbColumnNames.addElement("skts_target_column_name");
		this.dbColumnNames.addElement("skts_target_value");
		this.dbColumnNames.addElement("skts_is_down_shift");
		this.dbColumnNames.addElement("skts_is_up_shift");
		this.dbColumnNames.addElement("skts_shift_factor");
		this.dbColumnNames.addElement("skts_is_absolute_shift");
		this.dbColumnNames.addElement("skts_skt_column");
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
		vec.add(Boolean.class);
		vec.add(Boolean.class);
		vec.add(Integer.class);
		vec.add(Boolean.class);
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
