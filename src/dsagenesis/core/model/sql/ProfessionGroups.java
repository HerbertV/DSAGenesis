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

import javax.swing.table.TableColumn;

import dsagenesis.core.model.xml.AbstractGenesisModel;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;
import dsagenesis.editor.coredata.table.CoreEditorTableModel;

/**
 * ProfessionGroups
 * 
 * SQL Model Class.
 * 
 * ProffesionGroups is used for getting the sub folders for professions.
 * It is also used by the filtering in the hero creation step.
 */
public class ProfessionGroups
		extends AbstractNamedTableModel 
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
	public ProfessionGroups() 
	{
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param rs	
	 * @throws SQLException 
	 */
	public ProfessionGroups(ResultSet rs) 
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
		this.dbColumnNames.addElement("pg_path");
	}	
	
	@Override
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		super.setupJTableColumnModels(ceframe, cetable);
		
		TableColumn currColumn;
        
        //col 2
        currColumn = cetable.getColumnModel().getColumn(2);
        currColumn.setMinWidth(150);
    }
	
	@Override
	public Vector<Class<?>> getTableColumnClasses()
	{
		Vector<Class<?>> vec = super.getTableColumnClasses();
		vec.add(String.class);
		
		return vec;
	}

	/**
	 *  not needed 
	 */
	@Override
	public AbstractGenesisModel getRow(String id) 
	{
		return null;
	}
	
	@Override
	public void setupReferences()
			throws SQLException
	{
		// not needed
	}
	
	@Override
	public void queryReferences(CoreEditorTableModel model)
			throws SQLException
	{
		// not needed
	}
	
	@Override
	protected void updateReferencesFor(
			String id,
			Vector<Object> rowData
		) throws SQLException
	{
		// not needed
	}

}
