/*
 *  __  __      
 * /\ \/\ \  __________   
 * \ \ \_\ \/_______  /\   
 *  \ \  _  \  ____/ / /  
 *   \ \_\ \_\ \ \/ / / 
 *    \/_/\/_/\ \ \/ /  
 *             \ \  /
 *              \_\/
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

import jhv.util.debug.logger.ApplicationLogger;

import dsagenesis.core.sqlite.DBConnector;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;
import dsagenesis.editor.coredata.table.cell.BasicCellRenderer;

/**
 * CoreDataTableIndex
 * 
 * SQL Model Class.
 * This Table is a special since it is used only by the Core Data Editor
 * to administrate the other tables the user can alter with the Editor.
 */
public class CoreDataTableIndex 
		extends AbstractSQLTableModel 
{
	// ============================================================================
	//  Variables
	// ============================================================================
	
	// ============================================================================
	//  Cosntructors
	// ============================================================================
		
	/**
	 * Constructor
	 */
	public CoreDataTableIndex() 
	{
		super();
		
		this.prefix = "ti_";
		this.usesPrefix = false;
		
		this.dbColumnNames = new Vector<String>();
		this.dbColumnNames.addElement("ID");
		this.dbColumnNames.addElement("ti_table_name");
		this.dbColumnNames.addElement("ti_uses_prefix");
		this.dbColumnNames.addElement("ti_prefix");
		this.dbColumnNames.addElement("ti_last_index_num");
		this.dbColumnNames.addElement("ti_label");
		this.dbColumnNames.addElement("ti_note");
		this.dbColumnNames.addElement("ti_is_internal");
		this.dbColumnNames.addElement("ti_tab_index");
		this.dbColumnNames.addElement("ti_editable");
		
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================
		
	@Override
	public String getDBTableName()
	{
		return "CoreDataTableIndex";
	}


	@Override
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		
		TableColumn currColumn;
        
        // ID col 0
        currColumn = cetable.getColumnModel().getColumn(0);
        currColumn.setPreferredWidth(20);
        currColumn.setCellRenderer(new BasicCellRenderer());
        
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isEditable()
	{
		return false;
	}

	@Override
	public Vector<Vector<Object>> queryList()
	{
		String query= "SELECT * FROM "
				+ getDBTableName()
				+ " ORDER BY ID ASC";
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);	
		
		ApplicationLogger.logInfo(
				"CoreDataTableIndex.queryList time: "
					+ DBConnector.getInstance().getQueryTime() 
					+ " ms"
			);
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		if( rs == null )
			return data;
		
		try 
		{
			int colCount = rs.getMetaData().getColumnCount();
			while( rs.next() )
			{
				Vector<Object> row = new Vector<Object>();
				for( int col=1; col <= colCount; col++) 
					row.add(rs.getObject(col));
				
				data.add(row);
			}
			
		} catch (SQLException e) {
			ApplicationLogger.logError(e);
		}
		
		return data;
	}
	
	
	
	

}
