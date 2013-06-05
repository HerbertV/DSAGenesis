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
import dsagenesis.editor.coredata.table.cell.NumericCellEditor;

/**
 * SKT
 * 
 * SQL Model Class.
 * 
 * this class is only used by Core Data Editor (for generating the JTable), 
 * for accessing the SKT use SKTMatrix! 
 */
public class SKT
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
	public SKT() 
	{
		super();
	}
	
	/**
	 * Constructor 2.
	 * 
	 * @param rs	
	 * @throws SQLException 
	 */
	public SKT(ResultSet rs) 
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
		this.dbColumnNames.addElement("skt_a");
		this.dbColumnNames.addElement("skt_b");
		this.dbColumnNames.addElement("skt_c");
		this.dbColumnNames.addElement("skt_d");
		this.dbColumnNames.addElement("skt_e");
		this.dbColumnNames.addElement("skt_f");
		this.dbColumnNames.addElement("skt_g");
		this.dbColumnNames.addElement("skt_h");
	}
	
	@Override
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		super.setupJTableColumnModels(ceframe, cetable);
		
		TableColumn currColumn;

		currColumn = cetable.getColumnModel().getColumn(1);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		currColumn = cetable.getColumnModel().getColumn(2);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		currColumn = cetable.getColumnModel().getColumn(3);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		currColumn = cetable.getColumnModel().getColumn(4);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		currColumn = cetable.getColumnModel().getColumn(5);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		currColumn = cetable.getColumnModel().getColumn(6);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		currColumn = cetable.getColumnModel().getColumn(7);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
		currColumn = cetable.getColumnModel().getColumn(8);
		currColumn.setCellEditor(new NumericCellEditor(
				Integer.class,
				ceframe.getStatusBar()
			));
	}
	
	@Override
	public Vector<Class<?>> getTableColumnClasses()
	{
		Vector<Class<?>> vec = super.getTableColumnClasses();
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		
		return vec;
	}

	/**
	 * for accessing the SKT use class SKTMatrix. 
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
