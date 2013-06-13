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

import jhv.util.debug.logger.ApplicationLogger;

import dsagenesis.core.model.xml.AbstractGenesisModel;
import dsagenesis.core.sqlite.DBConnector;
import dsagenesis.core.sqlite.TableHelper;
import dsagenesis.core.util.logic.Formula;
import dsagenesis.core.util.logic.ISyntax;
import dsagenesis.core.util.logic.Selection;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;
import dsagenesis.editor.coredata.table.CoreEditorTableModel;
import dsagenesis.editor.coredata.table.cell.IDCellEditor;

/**
 * AbstractSQLTableModel
 * 
 * Abstract base for accessing the database for a table.
 * Has also functions for working with our JTable implementation. 
 * 
 * All implementations represent the whole table not a single object.
 * So in most cases the Names of these classes are plural.
 * 
 * The Classes have the same name as their referring database tables.
 * Also setups everything for the ID column.
 */
public abstract class AbstractSQLTableModel
{
	// ============================================================================
	//  Variables
	// ============================================================================
	
	/**
	 * the internal database column names.
	 * 
	 * they are used to determinate the column order.
	 */
	protected Vector<String>dbColumnNames;
	
	
	/**
	 * prefix used by column names (except ID)
	 * and for the ID generation if usesPrefix = true;
	 * 
	 * prefix must end with _
	 */
	protected String prefix = "";
	
	/**
	 * 
	 */
	protected boolean usesPrefix = true;
	
	/**
	 * 
	 */
	protected String note = "";
	
	/**
	 * 
	 */
	protected String label = "";
	
	/**
	 * 
	 */
	protected boolean isEditable = false;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor 1.
	 * 
	 * used by system tables or for cross-references
	 * or for the AbstractGenesisModel
	 */
	public AbstractSQLTableModel()
	{
		try
		{
			this.prefix = TableHelper.getPrefixForTable(this.getDBTableName());
		} catch( SQLException e ) {
			ApplicationLogger.logError("cannot aquire prefix for " + this.getDBTableName() );
			ApplicationLogger.logError(e);
		}
		setupDBColumns();
	}
	
	/**
	 * Constructor 2.
	 * 
	 * Dynamic setup with the ResultSet of CoreDataTableIndex
	 * 
	 * used by all tables with entries in CoreDataTableIndex
	 * 
	 * @param rs
	 * @throws SQLException 
	 */
	public AbstractSQLTableModel(ResultSet rs)
			throws SQLException
	{
		this.usesPrefix = DBConnector.convertBooleanFromDB(
				rs.getObject("ti_uses_prefix")
			);
		this.prefix = rs.getString("ti_prefix");
		this.note = rs.getString("ti_note");
		this.label = rs.getString("ti_label");
		this.isEditable = DBConnector.convertBooleanFromDB(
				rs.getObject("ti_editable")
			);
		
		setupDBColumns();
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
	protected void setupDBColumns() 
	{
		this.dbColumnNames = new Vector<String>();
		this.dbColumnNames.addElement("ID");
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
		TableColumn currColumn;

		// ID col 0
		// ID is always Left aligned
		currColumn = cetable.getColumnModel().getColumn(0);
		currColumn.setMinWidth(30);
		currColumn.setPreferredWidth(50);
		currColumn.setMaxWidth(100);

		currColumn.setCellEditor(new IDCellEditor(
				cetable.getSQLTable().getPrefix(),
				cetable.getSQLTable().getDBTableName(),
				ceframe.getStatusBar()
			));
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
	public Vector<Class<?>>getTableColumnClasses()
	{
		Vector<Class<?>> vec = new Vector<Class<?>>(this.dbColumnNames.size());
		if( usesPrefix )
		{
			vec.add(String.class);
		} else {
			vec.add(Integer.class);
		}
		return vec;
	}
	
	/**
	 * getDBTableName
	 * 
	 * The table name is the implementations simple name.
	 * 
	 * @return
	 */
	public String getDBTableName()
	{
		return this.getClass().getSimpleName();
	}
	
	/**
	 * getDBTableLabel
	 * 
	 * @return
	 */
	public String getDBTableLabel()
	{
		return this.label;
	}
	/**
	 * getPrefix
	 * 
	 * prefix that is used for the ID and the column names
	 * if there is no prefix it returns ""
	 * 
	 * @return
	 */
	public String getPrefix()
	{
		return prefix;
	}
	
	/**
	 * usesPrefix
	 * 
	 * @return
	 */
	public boolean usesPrefix()
	{
		return usesPrefix;
	}
	
	/**
	 * getDBColumnNames
	 * 
	 * @return
	 */
	public Vector<String> getDBColumnNames()
	{
		return dbColumnNames;
	}
	
	/**
	 * getColumnLabels
	 * 
	 * Returns the labels used to render the JTable Header.
	 * by default it returns the internal db column names.
	 *  
	 * @return
	 */
	public Vector<String> getColumnLabels() 
	{
		try
		{
			Vector<Vector<String>> rows = TableHelper.getColumnLabelsForTable(
					this.getDBTableName()
				);
			
			if( rows.size() == 0 )
			{
				ApplicationLogger.logWarning(
						"No Column Labels found for "
							+ this.getDBTableName() 
							+" !"
					);
				return this.dbColumnNames;
			}
			
			Vector<String> labels = new Vector<String>(this.dbColumnNames.size());
			labels.add("ID");
			for( int i=1; i< this.dbColumnNames.size(); i++ )
				labels.add("");
			
			for( int i=0; i<rows.size(); i++ )
			{
				Vector<String> row = rows.elementAt(i);
				
				if( this.dbColumnNames.indexOf(row.elementAt(0)) > -1 )
				{
					labels.setElementAt(
							row.elementAt(1), 
							this.dbColumnNames.indexOf(row.elementAt(0))
						);
				} else {
					ApplicationLogger.logWarning(
							"Column Label for "
								+ this.getDBTableName()
								+ " : "
								+ row.elementAt(0)
								+" not found ! Using internal name now."
						);
					return this.dbColumnNames;
				}
			}
			return labels;
			
		} catch( SQLException e) {
			ApplicationLogger.logWarning(
					"No Column Labels found for "
						+ this.getDBTableName() 
						+" !"
				);
		}
		
		return this.dbColumnNames;
	}
		
	/**
	 * getNote
	 * 
	 * @return
	 */
	public String getNote()
	{
		return note;
	}
	
	/**
	 * isEditable
	 * 
	 * @return
	 */
	public boolean isEditable()
	{
		return isEditable;
	}
	
	/**
	 * addSyntaxToRow
	 * 
	 * check if the value implements ISyntax
	 * adds it if it is and returns true.  
	 * 
	 * @param row
	 * @param c
	 * @param value
	 * 
	 * @return
	 */
	private boolean addSyntaxToRow(
			Vector<Object> row,
			Class<?> c, 
			Object value 
		)
	{
		Class<?>[] interfaces = c.getInterfaces();
		
		// check for syntax interface
		boolean isSyntax = false;
		for( int i=0; i<interfaces.length; i++ )
		{
			if( interfaces[i] == ISyntax.class )
			{
				isSyntax = true;
				break;
			}
		}
		
		if( !isSyntax )
			return false;
		
		// add syntax
		ISyntax s = null;
		if( c == Formula.class )
		{
			s = new Formula();
		} else if( c == Selection.class ) {
			s = new Selection();
		}
		
		if( value != null)
			s.parseStringFromDB(value.toString());
		
		row.add(s);
		
		return true;
	}
	
	/**
	 * queryList
	 * 
	 * returns all entries sort by ID
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	public ResultSet queryList()
			throws SQLException
	{
		return queryList(this.dbColumnNames,"ID",true);
	}
	
	/**
	 * queryListAsVector
	 * 
	 * returns all entries sort by ID 
	 * used for creating JTable's.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Vector<Vector<Object>> queryListAsVector()
			throws SQLException
	{
		ResultSet rs = queryList();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		if( rs == null )
			return data;
		
		Vector<Class <?>> classes = getTableColumnClasses();
		int colCount = dbColumnNames.size();
		
		while( rs.next() )
		{
			Vector<Object> row = new Vector<Object>();
			for( int col=0; col < colCount; col++) 
			{
				Object value = rs.getObject(dbColumnNames.elementAt(col));
				
				if( addSyntaxToRow(row, classes.get(col), value) )
					continue;
				
				// skip junctions
				int junctcount = 0;
				while( classes.get(col+junctcount) == Vector.class )
				{
					row.add(null);
					junctcount++;
				}
				// default
				row.add(value);
			}
			data.add(row);
		}
			
		return data;
	}
	
	/**
	 * queryList
	 * 
	 * returns the selected columns. 
	 * 
	 * @param columns
	 * @param orderby can be null
	 * @param isAsc
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	public ResultSet queryList(
			Vector<String> columns, 
			String orderby, 
			boolean isAsc 
		)
			throws SQLException
	{
		String query= "SELECT ";
		
		for(int i=0; i< columns.size(); i++ )
		{
			query += columns.elementAt(i);
			
			if( i< (columns.size()-1) )
				query += ", ";
		}
		
		query += " FROM " + getDBTableName();
		
		if( orderby != null )
		{
			query += " ORDER BY " + orderby;
			
			if( isAsc )
				query += " ASC";
			else
				query += " DESC";
		}
		
		ResultSet rs = DBConnector.getInstance().executeQuery(query);	
		
		ApplicationLogger.logDebug(
					this.getClass().getSimpleName()
					+ ".queryList time: "
					+ DBConnector.getInstance().getQueryTime() 
					+ " ms"
			);
		
		return rs;
	}
	
	/**
	 * queryListAsVector
	 * 
	 * returns the selected columns. 
	 * 
	 * @param columns
	 * @param orderby can be null
	 * @param isAsc
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Vector<Vector<Object>> queryListAsVector(
			Vector<String> columns, 
			String orderby, 
			boolean isAsc
		)
			throws SQLException
	{
		ResultSet rs = queryList(columns,orderby,isAsc);
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		if( rs == null )
			return data;
		
		Vector<Class <?>> classes = getTableColumnClasses();
		int colCount = dbColumnNames.size();
		
		while( rs.next() )
		{
			Vector<Object> row = new Vector<Object>();
			for( int col=0; col < colCount; col++) 
			{
				Object value = rs.getObject(dbColumnNames.elementAt(col));
				
				if( addSyntaxToRow(row, classes.get(col), value) )
					continue;
				
				// skip junctions
				int junctcount = 0;
				while( classes.get(col+junctcount) == Vector.class )
				{
					row.add(null);
					junctcount++;
				}
				// default
				row.add(value);
			}
			data.add(row);
		}
			
		return data;
	}
	
	/**
	 * commitRow
	 * 
	 * inserts or updates a row in DB
	 * 
	 * @param rowData
	 * @throws SQLException
	 */
	public void commitRow(Vector<Object> rowData)
			throws SQLException
	{
		Object id = rowData.get(0);
		
		String statement = null;
		long querytime = 0;
		
		if( id == null 
				|| !TableHelper.idExists(id.toString(), getDBTableName())
			)
		{
			// insert
			statement = prepareInsertStatement(rowData);
		} else {
			// update
			statement = prepareUpdateStatement(rowData);
		}

		DBConnector.getInstance().executeUpdate(statement);
		querytime = DBConnector.getInstance().getQueryTime();
		
		updateReferencesFor((String)id,rowData);
		querytime += DBConnector.getInstance().getQueryTime();
			
		ApplicationLogger.logDebug(
				this.getClass().getSimpleName()
					+ ".commitRow time: "+querytime+ " ms"
			);
	}
	
	/**
	 * deleteRow
	 * 
	 * @param id
	 * @throws SQLException
	 */
	public void deleteRow(String id)
			throws SQLException
	{
		// here we need also delete all db entries
		String delete = "DELETE FROM "
				+ getDBTableName()
				+ " WHERE ID='"+id+"'";
				
		updateReferencesFor(id,null);
		long querytime = DBConnector.getInstance().getQueryTime();
					
		DBConnector.getInstance().executeUpdate(delete);
		querytime += DBConnector.getInstance().getQueryTime();
					
		ApplicationLogger.logDebug(
				this.getClass().getSimpleName()
					+ ".deleteRow time: " + querytime + " ms"
			);
	}
	
	/**
	 * getJunctionTableName
	 * 
	 * returns the junction table name
	 * 
	 * @param table1
	 * @return
	 */
	public String getJunctionTableName(String table1)
	{
		return "Junct_"
				+ table1 
				+ "_"
				+ this.getDBTableName(); 
	}
	
	/**
	 * prepareJunctionStatements
	 * 
	 * returns a string with all needed inserts and deletes.
	 * 
	 * @param id
	 * @param tablename
	 * @param prefix
	 * @param adds
	 * @param removes
	 * 
	 * @return
	 */
	protected String prepareJunctionStatements(
			String id, 
			String tablename, 
			String prefix,
			Vector<Object> adds, 
			Vector<Object> removes
		)
	{
		String update = "";
		
		// insert Statements
		for( int i=0; i<adds.size(); i++ )
			update += "INSERT INTO " + tablename
					+ " ( ref_" + this.prefix + "ID , ref_" + prefix + "ID )" 
					+ " VALUES ('"
					+ id + "', '" + adds.get(i) + "' );\n";
	
		// delete Statements
		for( int i=0; i<removes.size(); i++ )
			update += "DELETE FROM " + tablename 
					+ " WHERE ref_"+this.prefix + "ID='"
					+ id + "' AND ref_" + prefix + "ID='" + removes.get(i) + "';\n";
		
		return update;
	}
	
	/**
	 * prepareInsertStatement
	 * 
	 * @param rowData
	 * 
	 * @return
	 */
	protected String prepareInsertStatement(
			Vector<Object> rowData
		)
	{
		Vector<Class<?>> colClasses = getTableColumnClasses();
		int count = dbColumnNames.size();
		String insert = "INSERT INTO "
				+ getDBTableName()
				+ " \n( ";
		
		for(int i=0; i< count; i++ )
		{
			insert += dbColumnNames.get(i);
			
			if( i < (count-1) )
				insert += ", ";
		}
		
		insert += ")\n VALUES \n(";
		
		int junctcount = 0;
		for( int i=0; i< count; i++ )
		{
			Class<?> c = colClasses.get(i+junctcount);		
			Object value = rowData.get(i+junctcount);
					
			if( c == Vector.class )
			{
				// skip junctions
				junctcount++;
				c =  colClasses.get(i+junctcount);		
				value = rowData.get(i+junctcount);
			}
			
			if( value == null )
			{
				insert += "NULL";
			
			} else if( c == Formula.class ) {
				insert += "'"+ ((Formula)value).renderStringForDB() + "'";
				
			} else if( c == Integer.class || c == Float.class ) {
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
	 * @param rowData
	 * 
	 * @return
	 */
	protected String prepareUpdateStatement(
			Vector<Object> rowData
		)
	{
		Vector<Class<?>> colClasses = getTableColumnClasses();
		int count = dbColumnNames.size();

		String update = "UPDATE "
				+ getDBTableName()
				+ " SET \n ";
		
		int junctcount = 0;
		// we start at column 1 since 0 is ID
		for( int i=1; i< count; i++ )
		{
			update += dbColumnNames.get(i) +"=";
			
			Object value = rowData.get(i+junctcount);
			Class<?> c = colClasses.get(i+junctcount);		

			if( c == Vector.class )
			{
				// skip junctions
				junctcount++;
				c =  colClasses.get(i+junctcount);		
				value = rowData.get(i+junctcount);
			} 
			
			if( value == null )
			{
				update += "NULL";
			
			} else if( c == Formula.class ) {
				update += "'"+ ((Formula)value).renderStringForDB() + "'";
				
			} else if( c == Integer.class || c == Float.class ) {
				update += value.toString();
			
			} else if( c == Boolean.class )	{
				update += DBConnector.convertBooleanForDB( value );
				
			} else {
				update += "'"+ value.toString() + "'";
				
			}
			
			if( i < (count-1) )
				update += ", ";
		}
		update += " \n WHERE ID='"+rowData.get(0).toString()+"'";
		
		return update;
	}
	
	
	/**
	 * setupReferences
	 * 
	 * this function is called by Core Editor before the table model is loaded.
	 * it is used to generated for example reference ComboBoxes
	 * 
	 *  @throws SQLException
	 */
	public abstract void setupReferences() throws SQLException;
	
	/**
	 * queryReferences
	 * 
	 * this function is called by Core Editor as last step of the table model creation
	 * to load the data from the junction table into the JTableModel.
	 * this is used by tables with junctions.
	 * 
	 * @param model
	 * 
	 * @throws SQLException
	 */
	public abstract void queryReferences(CoreEditorTableModel model) throws SQLException;
	
	/**
	 * updateReferencesFor
	 * 
	 * this function is called by Core Editor if an insert, update or delete
	 * was done.
	 * 
	 * it changes the references (most times junctions).
	 * 
	 * @param id
	 * @param row if -1 all entries need to be deleted.
	 * @param model
	 * 
	 * @throws SQLException
	 */
	protected abstract void updateReferencesFor(
			String id,
			Vector<Object> rowData
		) throws SQLException;
	
	/**
	 * getRow
	 * 
	 * returns a single row/entry by it's ID as Abstract GenesisModel.
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public abstract AbstractGenesisModel getRow(String id);
	
	
}
