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
package dsagenesis.editor.coredata.task;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import dsagenesis.core.model.sql.AbstractSQLTableModel;
import dsagenesis.editor.coredata.table.CoreEditorTable;
import dsagenesis.editor.coredata.table.CoreEditorTableModel;

import jhv.swing.task.AbstractSerialTask;

/**
 * LoadTableDataTask
 * 
 * SwingWorker Task for loading the table data
 */
public class LoadTableDataTask 
		extends AbstractSerialTask 
{

	// ============================================================================
	//  Variables
	// ============================================================================

	private CoreEditorTable table;
	
	private String statusMsg;
	
	private Vector<Vector<Object>> data; 
	
	private Vector<String> labels; 
	
	private Vector<Class<?>> classes; 
	
	private CoreEditorTableModel coreEditorTableModel;
	
	// ============================================================================
	//  Constructors
	// ============================================================================

	/**
	 * Constructor.
	 * 
	 * @param lbl
	 * @param pb
	 * @param table
	 * @param msg
	 */
	public LoadTableDataTask(
			JLabel lbl,
			JProgressBar pb,
			CoreEditorTable table,
			String msg
		) 
	{
		super(lbl,pb);
		this.table = table;
		this.statusMsg = msg;
		this.maxSteps = 3;
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================

	@Override
	protected String prepareNextStep() 
	{
		return statusMsg;
	}

	@Override
	protected void doNextStep() 
			throws Exception 
	{
		switch(this.currentStep)
		{
			case 0:
				loadData();
				break;
				
			case 1:
				createUI();
				break;
				
			case 2:
				queryReferencesAndCommitButtons();
				break;
		}
	}
	
	/**
	 * loadData
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	protected void loadData()
			throws SQLException
	{
		AbstractSQLTableModel sqlTable = this.table.getSQLTable();
		// we need to clone since we don't want to alter the original data.
		data = (Vector<Vector<Object>>) sqlTable.queryListAsVector().clone();
		labels = (Vector<String>) sqlTable.getColumnLabels().clone();
		classes = (Vector<Class<?>>) sqlTable.getTableColumnClasses().clone();
		
		sqlTable.setupReferences();
		
		//if editable fill up cells for commit button
		if( sqlTable.isEditable() )
		{
			for( int i=0; i < data.size(); i++ )
				data.elementAt(i).addElement(null);
			
			labels.addElement("");
			classes.addElement(Object.class);
		}
	}
	
	/**
	 * createUI
	 */
	protected void createUI() 
	{
		// must be done in EDT
		try
		{
			SwingUtilities.invokeAndWait(new Runnable(){
					public void run() 
					{
						coreEditorTableModel = new CoreEditorTableModel(
								data, 
								labels,
								classes,
								table
							);
						AbstractSQLTableModel sqlTable = table.getSQLTable();
						
						coreEditorTableModel.setReadOnly(!sqlTable.isEditable());
						table.setModel(coreEditorTableModel);
						sqlTable.setupJTableColumnModels(table.getFrame(), table);
					}
				});
		} catch( InvocationTargetException | InterruptedException e ) {
			// ignore it
		}
	}
	
	/**
	 * queryReferencesAndCommitButtons
	 * 
	 * @throws SQLException
	 */
	protected void queryReferencesAndCommitButtons() 
			throws SQLException
	{
		AbstractSQLTableModel sqlTable = this.table.getSQLTable();
		
		sqlTable.queryReferences(coreEditorTableModel);
		table.initCommitButtonCell(data);
	}

}
