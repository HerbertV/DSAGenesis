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
package dsagenesis.editor.coredata.table;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import dsagenesis.core.model.sql.AbstractSQLTableModel;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.cell.BasicCellRenderer;
import dsagenesis.editor.coredata.table.cell.CheckBoxCellRenderer;

/**
 * CoreEditorTable
 * 
 * JTable implementation that is filled with AbstractSQLTabelModel's.
 */
public class CoreEditorTable 
		extends JTable 
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	private AbstractSQLTableModel sqlTable;
	
	private CoreEditorFrame jframe;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
			
	/**
	 * Constructor.
	 * 
	 * @param sqlTable
	 */
	public CoreEditorTable(CoreEditorFrame frame, AbstractSQLTableModel sqlTable)
	{
		super();
		
		this.sqlTable = sqlTable;
		this.jframe = frame;
				
		this.setName(sqlTable.getDBTableName());
		setup();
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================
		
	private void setup()
	{
		this.setAutoCreateRowSorter(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.getTableHeader().setReorderingAllowed(false);
		this.setDefaultRenderer(Object.class, new BasicCellRenderer());
	}
	
	
	public void loadData()
	{
		if( sqlTable == null )
			return;
		
		Vector<Vector<Object>> data = sqlTable.queryList();
		CoreEditorTableModel cetm = new CoreEditorTableModel(
				data, 
				sqlTable.getDBColumnNames()
			);
		cetm.setReadOnly(!sqlTable.isEditable());
		this.setModel(cetm);
		
		this.sqlTable.setupJTableColumnModels(jframe, this);
	}

	
}
