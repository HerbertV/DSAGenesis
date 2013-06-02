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

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import jhv.util.debug.logger.ApplicationLogger;

import dsagenesis.core.model.sql.AbstractSQLTableModel;
import dsagenesis.core.sqlite.DBConnector;
import dsagenesis.core.sqlite.TableHelper;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.cell.BasicCellRenderer;
import dsagenesis.editor.coredata.table.cell.CheckBoxCellEditor;
import dsagenesis.editor.coredata.table.cell.CheckBoxCellRenderer;
import dsagenesis.editor.coredata.table.cell.CommitButtonCell;
import dsagenesis.editor.coredata.table.cell.NumericCellRenderer;

/**
 * CoreEditorTable
 * 
 * JTable implementation that is filled with AbstractSQLTabelModel's.
 */
public class CoreEditorTable 
		extends JTable 
		implements TableModelListener
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
	
	private CommitButtonCell btnCommit;
	
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
		setup();
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * setup
	 * 
	 * the defaults.
	 */
	private void setup()
	{
		// this is better for larger tables
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	
		this.setName(sqlTable.getDBTableName());
				
		this.setAutoCreateRowSorter(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.getTableHeader().setReorderingAllowed(false);
		
		// renderer
		this.setDefaultRenderer(String.class, new BasicCellRenderer());
		this.setDefaultRenderer(Boolean.class, new CheckBoxCellRenderer());
		this.setDefaultRenderer(Number.class, new NumericCellRenderer());
		this.setDefaultRenderer(Object.class, new BasicCellRenderer());
		
		// editors
		this.setDefaultEditor(Boolean.class, new CheckBoxCellEditor());
		
		this.setRowHeight(22);
	}
	
	/**
	 * getFrame
	 * 
	 * @return
	 */
	public CoreEditorFrame getFrame()
	{
		return jframe;
	}
	
	/**
	 * getSQLTable
	 * 
	 * @return
	 */
	public AbstractSQLTableModel getSQLTable()
	{
		return this.sqlTable;
	}
	
	/**
	 * getNote
	 * 
	 * @return
	 */
	public String getNote()
	{
		return this.sqlTable.getNote();
	}
	
	/**
	 * getLabel
	 * 
	 * @return
	 */
	public String getLabel()
	{
		return this.sqlTable.getDBTableLabel();
	}
	
	/**
	 * isReadOnly
	 * 
	 * @return
	 */
	public boolean isReadOnly()
	{
		return ((CoreEditorTableModel)this.getModel()).isReadOnly();
	}
	
	/**
	 * containsUncommitedData
	 * 
	 * @return
	 */
	public boolean containsUncommitedData()
	{
		if( btnCommit == null )
			return false;
		
		if( btnCommit.getChangedRows().contains(true) )
			return true;
		
		return false;
	}
	
	public Vector<Integer> getUncommitedRowIndices()
	{
		Vector<Integer> indices = new Vector<Integer>();
		
		Vector<Boolean> changedRows = btnCommit.getChangedRows();
		for( int i=0; i<changedRows.size(); i++ )
		{
			if( changedRows.elementAt(i) == true )
				indices.add(i);		
		}
		
		return indices;
	}
	
	/**
	 * loadData
	 * 
	 * updates the whole table for the DB
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public void loadData()
			throws SQLException
	{
		if( sqlTable == null )
			return;
			
		// we need to clone since we don't want to alter the original data.
		Vector<Vector<Object>> data = 
				(Vector<Vector<Object>>) sqlTable.queryListAsVector().clone();
		Vector<String> labels = 
				(Vector<String>) sqlTable.getColumnLabels().clone();
		Vector<Class<?>> classes = 
				(Vector<Class<?>>) sqlTable.getTableColumnClasses().clone();
		
		sqlTable.queryReferences();
		
		//if editable fill up cells for commit button
		if( sqlTable.isEditable() )
		{
			for( int i=0; i < data.size(); i++ )
				data.elementAt(i).addElement(null);
			
			labels.addElement("");
			classes.addElement(Object.class);
		}
		
		CoreEditorTableModel cetm = new CoreEditorTableModel(
				data, 
				labels,
				classes,
				this
			);
		cetm.setReadOnly(!sqlTable.isEditable());
		this.setModel(cetm);
		
		this.sqlTable.setupJTableColumnModels(jframe, this);
		
		if( sqlTable.isEditable() )
		{
			// setup commit button and add listener
			TableColumn currColumn = this.getColumnModel().getColumn(this.getColumnCount()-1);
			currColumn.setMaxWidth(25);
			currColumn.setMinWidth(25);
			this.btnCommit = new CommitButtonCell(this);
			this.btnCommit.initRows(data.size());
			
			currColumn.setCellEditor(this.btnCommit);
			currColumn.setCellRenderer(this.btnCommit);
			
			// add listener
			this.getModel().addTableModelListener(this);
			this.getModel().addTableModelListener(this.jframe);
		}
	}
	
	/**
	 * addEmptyRow
	 * 
	 * adds an empty row to the table.
	 * but not to the DB.
	 */
	public void addEmptyRow()
	{
		Vector<Class<?>> classes = this.sqlTable.getTableColumnClasses();
		Vector<Object> row = new Vector<Object>();
		
		// generate id
		row.addElement(TableHelper.createNewID(this.sqlTable.getDBTableName()));
		
		for(int i=1; i< classes.size(); i++ )
		{
			if( classes.elementAt(i).equals(Vector.class) )
			{
				row.addElement(new Vector<Object>());
			} else if( classes.elementAt(i).equals(String.class) ) {
				row.addElement("");
				
			} else if( classes.elementAt(i).equals(Integer.class) ) {
				row.addElement(0);
				
			} else if( classes.elementAt(i).equals(Float.class) ) {
				row.addElement(1.0);
				
			} else if( classes.elementAt(i).equals(Boolean.class) ) {
				row.addElement(0);
				
			} else {
				row.addElement(null);
			}
		}
		// commit button cell
		row.addElement(null);
		btnCommit.addRow();
		((CoreEditorTableModel)this.getModel()).addRow(row);
	}
	
	/**
	 * removeRow
	 * 
	 * removes the row from table and the DB!
	 * 
	 * @param row
	 */
	public void removeRow(int row)
	{
		CoreEditorTableModel model = ((CoreEditorTableModel)this.getModel());
		Object id = model.getValueAt(row, 0);
		boolean deleteOnlyTable = false;
		if( id == null )
		{
			deleteOnlyTable = true;
		} else if( !TableHelper.idExists(id.toString(), sqlTable.getDBTableName()) ) {
			deleteOnlyTable = true;
		}
		
		try
		{ 
			((CoreEditorTableModel)this.getModel()).removeRow(row);
		} catch( IndexOutOfBoundsException e) {
			// do nothing this is only here
			// because the default table sorter throws this exception. 
		}
		btnCommit.deleteRow(row);
		
		if( deleteOnlyTable )
		{
			jframe.setCommitStatus( 
					CoreEditorFrame.STATUS_DELETE_SUCCESS, 
					this, 
					row 
				);
			return;
		}
		
		String delete = "DELETE FROM "
				+ sqlTable.getDBTableName()
				+ " WHERE ID='"+id+"'";
		
		try 
		{
			DBConnector.getInstance().executeUpdate(delete);
			long querytime = DBConnector.getInstance().getQueryTime();
		
			// TODO junctions
			//querytime += DBConnector.getInstance().getQueryTime();
			
			ApplicationLogger.logInfo("delete time "+querytime+ " ms");
			
		} catch( SQLException e ) {
			jframe.setCommitStatus( 
					CoreEditorFrame.STATUS_DELETE_ERROR, 
					this, 
					row 
				);
			return;
		}
		
		jframe.setCommitStatus( 
				CoreEditorFrame.STATUS_DELETE_SUCCESS, 
				this, 
				row 
			);
	}
	
	/**
	 * prepareInsertStatement
	 * 
	 * @param row
	 * @return
	 */
	private String prepareInsertStatement(int row)
	{
		CoreEditorTableModel model = ((CoreEditorTableModel)this.getModel());
		Vector<String> colNames = sqlTable.getDBColumnNames();
		Vector<Class<?>> colClasses = sqlTable.getTableColumnClasses();
		int count = colNames.size();

		String insert = "INSERT INTO "
				+ sqlTable.getDBTableName()
				+ " \n( ";
		
		for(int i=0; i< count; i++ )
		{
			insert += colNames.get(i);
			
			if( i < (count-1) )
				insert += ", ";
		}
		
		insert += ")\n VALUES \n(";
		
		int junctcount = 0;
		for( int i=0; i< count; i++ )
		{
			Class<?> c =  colClasses.get(i+junctcount);		
			Object value = model.getValueAt(row, i+junctcount);
			
			if( c == Vector.class ) {
				// skip junctions
				junctcount++;
				c =  colClasses.get(i+junctcount);		
				value = model.getValueAt(row, i+junctcount);
			}
			
			if( c == Integer.class || c == Float.class )
			{
				insert += value.toString();
			
			} else if( c == Boolean.class )	{
				insert += DBConnector.convertBooleanForDB( value );
				
			} else {
				insert += "'"+ value.toString() + "'";
				
			}
			
			if( i < (count-1) )
				insert += ", ";
		}
		insert += " )";
		
		return insert;		
	}
	
	/**
	 * prepareUpdateStatement
	 * 
	 * @param id
	 * @param row
	 * @return
	 */
	private String prepareUpdateStatement(Object id, int row)
	{
		CoreEditorTableModel model = ((CoreEditorTableModel)this.getModel());
		Vector<String> colNames = sqlTable.getDBColumnNames();
		Vector<Class<?>> colClasses = sqlTable.getTableColumnClasses();
		int count = colNames.size();

		String update = "UPDATE "
				+ sqlTable.getDBTableName()
				+ " SET \n ";
		
		int junctcount = 0;
		// we start at column 1 since 0 is ID
		for( int i=1; i< count; i++ )
		{
			update += colNames.get(i) +"=";
			
			Object value = model.getValueAt(row, i+junctcount);
			Class<?> c =  colClasses.get(i+junctcount);		
			
			if( c == Vector.class )
			{
				// skip junctions
				junctcount++;
				c =  colClasses.get(i+junctcount);		
				value = model.getValueAt(row, i+junctcount);
			} 
			
			if( c == Integer.class || c == Float.class )
			{
				update += value.toString();
			
			} else if( c == Boolean.class )	{
				update += DBConnector.convertBooleanForDB( value );
				
			} else {
				update += "'"+ value.toString() + "'";
				
			}
			
			if( i < (count-1) )
				update += ", ";
		}
		update += " \n WHERE ID='"+id.toString()+"'";
		
		return update;
	}
	
	/**
	 * commitRow
	 * 
	 * commit changes of the row.
	 * 
	 * @param row
	 */
	public void commitRow(int row)
	{
		CoreEditorTableModel model = ((CoreEditorTableModel)this.getModel());
		Object id = model.getValueAt(row, 0);
		String update = null;
		long querytime = 0;
		
		if( id == null 
				|| !TableHelper.idExists(id.toString(), sqlTable.getDBTableName())
			)
		{
			// insert
			update = prepareInsertStatement(row);
		} else {
			// update
			update = prepareUpdateStatement(id,row);
		}

		try 
		{
			DBConnector.getInstance().executeUpdate(update);
			querytime = DBConnector.getInstance().getQueryTime();
			
			// TODO junctions
			//querytime += DBConnector.getInstance().getQueryTime();
			
			ApplicationLogger.logInfo("update time "+querytime+ " ms");
			
		} catch( SQLException e ) {
			jframe.setCommitStatus( 
					CoreEditorFrame.STATUS_COMMIT_ERROR, 
					this, 
					row 
				);
			return;
		}
		
		this.btnCommit.setEnabled(row, false);
		jframe.setCommitStatus( 
				CoreEditorFrame.STATUS_COMMIT_SUCCESS, 
				this, 
				row 
			);
	}

	/**
	 * overridden for handling the commit button updates
	 */
	@Override
	public void tableChanged(TableModelEvent e)
	{
		// default handling
		super.tableChanged(e);
		
		// now we check if the commit button needs to be enabled
		int row = e.getFirstRow();
		int column = e.getColumn();
        
		// fail saves
		// that the button is not enabled unnecessarily
		if( row < 0 || column < 0 )
			return;
		
		if( e.getType() != TableModelEvent.UPDATE )
			return;
		
		if( this.getCellRenderer(row, column) != null
        		&&  this.getCellRenderer(row, column) instanceof CommitButtonCell )
        	return;
        
        if( this.getCellEditor() != null
        		&&  this.getCellEditor() instanceof CommitButtonCell )
        	return;
        
        // now something changed enable button
        this.btnCommit.setEnabled(row, true);
        
		// to update the icon
		this.repaint();
	}
}
