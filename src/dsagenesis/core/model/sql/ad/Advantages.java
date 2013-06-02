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

import dsagenesis.core.model.xml.AbstractGenesisModel;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;
import dsagenesis.editor.coredata.table.cell.NumericCellEditor;

/**
 * Advantages
 * 
 * SQL Model Class.
 */
public class Advantages 
		extends AbstractAdvantageDisadvantageModel 
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
	public Advantages()
	{
		super();
	}

	/**
	 * Constructor 2.
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	public Advantages(ResultSet rs) 
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
		
		this.dbColumnNames.addElement("ada_name_suffix");
		this.dbColumnNames.addElement("ada_has_level");
		this.dbColumnNames.addElement("ada_step_cp_cost");
		this.dbColumnNames.addElement("ada_min_level");
		this.dbColumnNames.addElement("ada_max_level");
		this.dbColumnNames.addElement("ada_has_choice");
		this.dbColumnNames.addElement("ada_choice_formular");
		this.dbColumnNames.addElement("ada_has_modifier");
		this.dbColumnNames.addElement("ada_modifier_formular");
		this.dbColumnNames.addElement("ada_bookmark");
		this.dbColumnNames.addElement("ada_note");
	}
	
	@Override
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		super.setupJTableColumnModels(ceframe, cetable);
		TableColumn currColumn;
		
		// col 5
		currColumn = cetable.getColumnModel().getColumn(5);
		currColumn.setMinWidth(50);
		
		// col 7
		currColumn = cetable.getColumnModel().getColumn(7);
		currColumn.setMinWidth(40);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		// col 8
		currColumn = cetable.getColumnModel().getColumn(8);
		currColumn.setMinWidth(40);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		// col 9
		currColumn = cetable.getColumnModel().getColumn(9);
		currColumn.setMinWidth(40);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		
		currColumn = cetable.getColumnModel().getColumn(11);
		currColumn.setMinWidth(120);
		
		currColumn = cetable.getColumnModel().getColumn(13);
		currColumn.setMinWidth(120);
		
		currColumn = cetable.getColumnModel().getColumn(15);
		currColumn.setMinWidth(120);
		
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
		// col 5
		vec.add(String.class);
		vec.add(Boolean.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Boolean.class);
		vec.add(String.class);
		vec.add(Boolean.class);
		vec.add(String.class);
		vec.add(String.class);
		vec.add(String.class);
				
		return vec;
	}

	@Override
	public AbstractGenesisModel getRow(String id) 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
