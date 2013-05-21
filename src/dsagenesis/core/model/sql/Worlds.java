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

/**
 * Worlds
 * 
 * SQL Model Class.
 */
public class Worlds
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
	 * 
	 * @param rs	
	 * @throws SQLException 
	 */
	public Worlds(ResultSet rs) 
			throws SQLException 
	{
		super(rs);
		
		this.dbColumnNames = new Vector<String>();
		this.dbColumnNames.addElement("ID");
		this.dbColumnNames.addElement("w_name");
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================

	/**
	 * not needed 
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
        
        //name col 1
        currColumn = cetable.getColumnModel().getColumn(1);
        currColumn.setMinWidth(150);
    }
	
	@Override
	public Vector<Class<?>> getTableColumnClasses()
	{
		Vector<Class<?>> vec = new Vector<Class<?>>(this.dbColumnNames.size());
		vec.add(String.class);
		vec.add(String.class);
		
		return vec;
	}

}
