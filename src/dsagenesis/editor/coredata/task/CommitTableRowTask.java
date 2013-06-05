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

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import dsagenesis.editor.coredata.table.CoreEditorTable;
import dsagenesis.editor.coredata.table.CoreEditorTableModel;

import jhv.swing.task.AbstractSerialTask;

/**
 * CommitTableRowTask
 * 
 * SwingWorker Task for inserting/updating table rows and junctions
 */
public class CommitTableRowTask 
		extends AbstractSerialTask 
{

	// ============================================================================
	//  Variables
	// ============================================================================

	private CoreEditorTable table;
	
	private int row;
	
	private String statusMsg;
	
	// ============================================================================
	//  Constructors
	// ============================================================================

	/**
	 * Constructor.
	 * 
	 * @param lbl
	 * @param table
	 * @param msg
	 */
	public CommitTableRowTask(
			JLabel lbl,
			CoreEditorTable table,
			int row,
			String msg
		) 
	{
		super(lbl);
		this.table = table;
		this.statusMsg = msg;
		this.row = row;
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
	@SuppressWarnings("unchecked")
	protected void doNextStep() 
			throws Exception 
	{
		CoreEditorTableModel model = ((CoreEditorTableModel)table.getModel());
		table.getSQLTable().commitRow((Vector<Object>) model.getDataVector().get(row));
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() 
			{
				table.disableCommitButtonFor(row);
				table.repaint();
			}
		});
	}

}
