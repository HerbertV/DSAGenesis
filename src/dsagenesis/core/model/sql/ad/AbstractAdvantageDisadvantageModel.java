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
package dsagenesis.core.model.sql.ad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.TableColumn;

import dsagenesis.core.model.sql.AbstractNamedTableModel;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;

/**
 * AbstractAdvantageDisadvantageModel
 * 
 * This an intermediate abstract class for 
 * Advantages, Disadvantages, Negative Attributes and Gifts.
 * 
 * Since these tables share a lot similarities,
 * this class does everything all final implementations need.
 */
public abstract class AbstractAdvantageDisadvantageModel 
		extends AbstractNamedTableModel 
{
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor 1.
	 */
	public AbstractAdvantageDisadvantageModel() 
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
	public AbstractAdvantageDisadvantageModel(ResultSet rs) 
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
		//TODO
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
        
        // TODO
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
		
		// TODO
		
		return vec;
	}
	
	
	
	
	@Override
	public void queryReferences() 
			throws SQLException
	{
		// TODO Auto-generated method stub

	}

	

}
