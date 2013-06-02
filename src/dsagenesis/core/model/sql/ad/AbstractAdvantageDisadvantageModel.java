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
import dsagenesis.core.model.sql.ProfessionCategories;
import dsagenesis.core.sqlite.DBConnector;
import dsagenesis.core.sqlite.TableHelper;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.dialog.JunctionCellDialog;
import dsagenesis.editor.coredata.table.CoreEditorTable;
import dsagenesis.editor.coredata.table.CoreEditorTableModel;
import dsagenesis.editor.coredata.table.cell.JunctionCellEditor;
import dsagenesis.editor.coredata.table.cell.JunctionCellRenderer;
import dsagenesis.editor.coredata.table.cell.NumericCellEditor;

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
	//  Variables
	// ============================================================================
	
	/**
	 * reference data from ProfessionCategories Table
	 */
	private Vector<Vector<Object>> professionCategories;
	
	
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
		
		this.dbColumnNames.addElement(this.prefix+"is_arbitrary");
		this.dbColumnNames.addElement(this.prefix+"cp_cost");
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
	@Override
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		super.setupJTableColumnModels(ceframe, cetable);
		TableColumn currColumn;
		
		// col 2
		currColumn = cetable.getColumnModel().getColumn(2);
		currColumn.setMinWidth(30);
		// col 3
		currColumn = cetable.getColumnModel().getColumn(3);
		currColumn.setMinWidth(30);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		// col 4
		currColumn = cetable.getColumnModel().getColumn(4);
		currColumn.setMinWidth(120);
		currColumn.setCellRenderer(
				new JunctionCellRenderer(professionCategories)
			);
		try 
		{
			currColumn.setCellEditor(new JunctionCellEditor(
					new JunctionCellDialog(
							ceframe,
							TableHelper.getLabelForTable("ProfessionCategories"),
							professionCategories
						),
					professionCategories	
				));
		} catch (SQLException e) {
			// nothing to do
		}
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
		// col 2
		vec.add(Boolean.class);
		// col 3
		vec.add(Integer.class);
		// col 4 N:N Cross References Table Column
		// contains ids as Vector
		vec.add(Vector.class);
		
		return vec;
	}
	
	@Override
	public Vector<String> getColumnLabels() 
	{
		Vector<String> vec = super.getColumnLabels();
		// inject cross reference column
		try {
			vec.add(4, TableHelper.getLabelForTable("ProfessionCategories"));
		} catch (SQLException e) {
			vec.add(4, "ProfessionCategories");
		}		
		
		return vec;
	}
	
	@Override
	public void setupReferences() 
			throws SQLException
	{
		ProfessionCategories pc = new ProfessionCategories();
		Vector<String> columns = new Vector<String>();
		columns.add("ID");
		columns.add("pc_name");
		
		this.professionCategories = pc.queryListAsVector(
				columns, 
				"pc_name", 
				true
			);	
	}

	@Override
	public void queryReferences(CoreEditorTableModel model)
			throws SQLException
	{
		String junctname = this.getJunctionTableName("ProfessionCategories");
		String query;
		Object id;
		Vector<Object> entries;
		for( int i=0; i<model.getRowCount(); i++ )
		{
			id = model.getValueAt(i, 0);
			query = "SELECT * FROM "
					+ junctname
					+ " WHERE ref_" +this.prefix+ "ID='"
					+ id + "' ORDER BY ref_pc_ID";
			
			ResultSet rs = DBConnector.getInstance().executeQuery(query);
			entries = new Vector<Object>();
			while( rs.next() )
				entries.add(rs.getString("ref_pc_ID"));
			
			model.setValueAt(entries, i, 4);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void updateReferencesFor(
			Object id,
			int row,
			CoreEditorTableModel model
		) 
			throws SQLException 
	{
		String junctname = this.getJunctionTableName("ProfessionCategories");
		
		String query = "SELECT * FROM "
				+ junctname
				+ " WHERE ref_" +this.prefix+ "ID='"
				+ id + "' ORDER BY ref_pc_ID";
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		Vector<Object> oldJunctions = new Vector<Object>();
		Vector<Object> newJunctions = new Vector<Object>();
		
		while( rs.next() )
			oldJunctions.add(rs.getString("ref_pc_ID"));
		
		// if row == -1 delete only
		if( row > -1 )
		{
			newJunctions = (Vector<Object>) ((Vector<Object>)model.getValueAt(row, 4)).clone();
			for( int i=(oldJunctions.size()-1); i>-1;  i-- )
			{
				int idx = newJunctions.indexOf(oldJunctions.get(i));
				if( idx > -1 )
				{
					// nothing changed still there
					newJunctions.remove(idx);
					oldJunctions.remove(i);
				}
			}
		}
		
		String update = prepareJunctionStatements(
				id.toString(), 
				junctname, 
				"pc_", 
				newJunctions, 
				oldJunctions
			);

		DBConnector.getInstance().executeUpdate(update);
	}

}
