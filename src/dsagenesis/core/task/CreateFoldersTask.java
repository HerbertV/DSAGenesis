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
package dsagenesis.core.task;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JLabel;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.sqlite.DBConnector;

import jhv.io.PathCreator;
import jhv.swing.task.AbstractSerialTask;

/**
 * FirstLaunchTask
 * 
 * SwingWorker Task for first launch tasks
 * 
 * creates the path structure
 */
public class CreateFoldersTask 
		extends AbstractSerialTask
{
	// ============================================================================
	//  Variables
	// ============================================================================

	/**
	 * header is printed before the filename.
	 */
	private String message;
	
	// ============================================================================
	//  Constructors
	// ============================================================================

	/**
	 * Constructor with label.
	 * 
	 * @param lbl
	 * @param message
	 */
	public CreateFoldersTask(
			JLabel lbl,
			String message
		)
	{
		super(lbl);
		
		this.message = message;
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
		this.maxSteps = 1;
	}
	
	
	@Override
	protected String prepareNextStep()
	{
		return message;
	}

	@Override
	protected void doNextStep() 
			throws Exception 
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		DBConnector connector = DBConnector.getInstance();
		ArrayList<String> arr = new ArrayList<String>();
		
		String pathRace = conf.getPathRace();
		String pathCulture = conf.getPathCulture();
		String pathProfession = conf.getPathProfession();
		
		ResultSet rs = connector.executeQuery("SELECT rcg_path FROM RaceCultureGroups");
		while( rs.next() )
		{
			arr.add( pathRace + rs.getString(1) );
			arr.add( pathCulture + rs.getString(1) );
		}
		
		rs = connector.executeQuery("SELECT pg_path FROM ProfessionGroups");
		while( rs.next() )
			arr.add( pathProfession + rs.getString(1) );
		
		// add other pathes
		arr.add(conf.getPathArchtype());
		arr.add(conf.getPathHero());
		arr.add(conf.getPathTemplate());
		arr.add(conf.getPathScript());
		
		String[]a =	arr.toArray(new String[arr.size()]);
		PathCreator.createPathes(a);
	}

}
