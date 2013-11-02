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
import dsagenesis.core.sqlite.TableHelper;
import dsagenesis.core.util.logic.Formula;
import dsagenesis.core.util.logic.Selection;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;
import dsagenesis.editor.coredata.table.cell.FormulaCellEditor;
import dsagenesis.editor.coredata.table.cell.NumericCellEditor;
import dsagenesis.editor.coredata.table.cell.SelectionCellEditor;

/**
 * Disadvantages
 * 
 * SQL Model Class.
 */
public class Disadvantages 
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
	public Disadvantages()
	{
		super();
	}

	/**
	 * Constructor 2.
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	public Disadvantages(ResultSet rs) 
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
		
		this.dbColumnNames.addElement("add_name_suffix");
		this.dbColumnNames.addElement("add_is_removable");
		this.dbColumnNames.addElement("add_remove_ap_cost");
		this.dbColumnNames.addElement("add_has_level");
		this.dbColumnNames.addElement("add_step_cp_cost");
		this.dbColumnNames.addElement("add_min_level");
		this.dbColumnNames.addElement("add_max_level");
		this.dbColumnNames.addElement("add_has_selection");
		this.dbColumnNames.addElement("add_selection");
		this.dbColumnNames.addElement("add_has_modifier");
		this.dbColumnNames.addElement("add_modifier_formula");
		this.dbColumnNames.addElement("add_bookmark");
		this.dbColumnNames.addElement("add_note");
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
		
		// col 9
		currColumn = cetable.getColumnModel().getColumn(9);
		currColumn.setMinWidth(40);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		// col 10
		currColumn = cetable.getColumnModel().getColumn(10);
		currColumn.setMinWidth(40);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		// col 11
		currColumn = cetable.getColumnModel().getColumn(11);
		currColumn.setMinWidth(40);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		
		// coll 13
		currColumn = cetable.getColumnModel().getColumn(13);
		currColumn.setMinWidth(120);
		currColumn.setCellEditor(new SelectionCellEditor(
				ceframe, 
				(String)currColumn.getHeaderValue()
			));
		
		currColumn = cetable.getColumnModel().getColumn(15);
		currColumn.setMinWidth(120);
		
		Vector<Vector<String>> tables = new Vector<Vector<String>>();
		try 
		{
			Vector<String> t = new Vector<String>();
			t.add("Characteristics");
			t.add(TableHelper.getLabelForTable("Characteristics"));
			t.add("c_acronym");
			tables.add(t);
			t.add(this.getDBTableName());
			t.add(this.getDBTableLabel());
			t.add("add_name");
			tables.add(t);
			
		} catch (SQLException e) {
			// nothing to do
		}
		currColumn.setCellEditor(new FormulaCellEditor(
				ceframe, 
				(String)currColumn.getHeaderValue(),
				tables,
				"add_name"
			));
		
		
		currColumn = cetable.getColumnModel().getColumn(17);
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
		vec.add(Boolean.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Boolean.class);
		vec.add(Selection.class);
		vec.add(Boolean.class);
		vec.add(Formula.class);
		vec.add(String.class);
		vec.add(String.class);
				
		return vec;
	}

	@Override
	public AbstractGenesisModel getRow(String id) 
	{
		// TODO get row for Hero editor
		return null;
	}
}
