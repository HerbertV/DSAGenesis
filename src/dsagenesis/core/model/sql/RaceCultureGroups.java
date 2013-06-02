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
 * RaceCultureGroups
 * 
 * SQL Model Class.
 * 
 * RaceCultureGroups is used for getting the sub folders for races and cultures.
 * It is also used by the filtering in the hero creation step.
 */
public class RaceCultureGroups
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
	public RaceCultureGroups() 
	{
		super();
	}
	
	/**
	 * Constructor 2.
	 * 
	 * @param rs	
	 * @throws SQLException 
	 */
	public RaceCultureGroups(ResultSet rs) 
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
		this.dbColumnNames.addElement("rcg_path");
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
	 *  
	 */
	@Override
	public AbstractGenesisModel getRow(String id) 
	{
		// TODO
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
	public void updateReferencesFor(
			Object id,
			int row,
			CoreEditorTableModel model
		) 
			throws SQLException 
	{
		// not needed
	}

}
