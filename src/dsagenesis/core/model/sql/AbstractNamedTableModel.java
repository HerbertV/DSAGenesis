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
 * AbstractNamedTableModel
 * 
 * This an intermediate abstract class for every table
 * that has a name column.
 */
public class AbstractNamedTableModel 
		extends AbstractSQLTableModel 
{
	
	/**
	 * Constructor 1.
	 */
	public AbstractNamedTableModel() 
	{
		super();
	}

	/**
	 * Constructor 2.
	 * 
	 * @param rs
	 * 
	 * @throws SQLException
	 */
	public AbstractNamedTableModel(ResultSet rs) 
			throws SQLException 
	{
		super(rs);
	}

	// ============================================================================
	//  Functions
	// ============================================================================

	/**
	 * setupDBColumns
	 * 
	 * for setting up the db column names.
	 * 
	 * needs further override!
	 * and call super.setupDBColumns(); !
	 */
	@Override
	protected void setupDBColumns() 
	{
		super.setupDBColumns();
		this.dbColumnNames.addElement(this.prefix+"name");
	}
	
	/**
	 * setupJTableColumnModels
	 * 
	 * This function assigns custom renders and editors to each column.
	 * 
	 * needs further override!
	 * and call super.setupJTableColumnModels(...); !
	 * 
	 * @param ceframe
	 * @param cetable
	 */
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		super.setupJTableColumnModels(ceframe, cetable);
		TableColumn currColumn;
        
        // Name col 1
		currColumn = cetable.getColumnModel().getColumn(1);
        currColumn.setMinWidth(200);
    }
	
	/**
	 * getTableColumnClasses
	 * 
	 * this is for the CoreEditorTableModel
	 * to get the correct class for each column 
	 * for cell renderer and editors.
	 * 
	 * needs further override!
	 * and call super.getTableColumnClasses(...); !
	 *
	 * @return
	 */
	@Override
	public Vector<Class<?>>getTableColumnClasses()
	{
		Vector<Class<?>> vec = super.getTableColumnClasses();
		vec.add(String.class);
		return vec;
	}
	
	
	@Override
	public void queryReferences() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public AbstractGenesisModel getRow(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
