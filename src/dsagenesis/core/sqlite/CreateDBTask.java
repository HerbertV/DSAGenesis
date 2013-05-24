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
package dsagenesis.core.sqlite;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import jhv.swing.task.AbstractSerialTask;

/**
 * CreateDBTask
 * 
 * SwingWorker Task for creating the DB from SQL scripts.
 * the statusLabel output is a 2 line html:
 * 
 * [Header]
 * [currentfilename]
 * 
 * ProgressBar is optional
 */
public class CreateDBTask 
		extends AbstractSerialTask
{
	// ============================================================================
	//  Variables
	// ============================================================================
	
	/**
	 * header is printed before the filename.
	 */
	private String header;
	
	/**
	 * list of SQL files being executed.
	 */
	private String[] sqlfiles;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================

	/**
	 * Constructor with label.
	 * 
	 * @param lbl
	 * @param header
	 */
	public CreateDBTask(
			JLabel lbl,
			String header
		)
	{
		super(lbl);
		
		this.header = header;
		setup();
	}
	
	/**
	 * Constructor with label and progress bar.
	 * 
	 * @param lbl
	 * @param progress
	 * @param header
	 */
	public CreateDBTask(
			JLabel lbl, 
			JProgressBar progress,
			String header
		)
	{
		super(lbl,progress);
		
		this.header = header;
		setup();
	}

	// ============================================================================
	//  Functions
	// ============================================================================

	/**
	 * inits the sql file list
	 */
	private void setup()
	{
		this.sqlfiles = new String[]{
				"sql/00_createSystemTables.sql",
				"sql/01_createSKT.sql",
				"sql/02_createWorlds.sql",
				"sql/03_createCharacteristics.sql",
				"sql/04_metaDataGroups.sql"
			};
		
		this.maxSteps = this.sqlfiles.length;
	}
	
	
	@Override
	protected String prepareNextStep()
	{
		String status = "<html>" 
				+ header
				+ "<br>"
				+ sqlfiles[this.currentStep]
				+ "</html>";
		
		return status;
	}

	@Override
	protected void doNextStep() 
			throws Exception 
	{
		DBConnector.getInstance().executeFile(
				this.sqlfiles[this.currentStep]
			);
	}

}
