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

import java.util.Vector;

import dsagenesis.core.model.xml.AbstractGenesisModel;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;

/**
 * CoreDataVersion
 * 
 * SQL Model Class.
 * 
 * This table is used to determinate the database version for the contents.
 */
public class CoreDataVersion
		extends AbstractSQLTableModel 
{
	// ============================================================================
	//  Variables
	// ============================================================================
		
	// ============================================================================
	//  Constructors
	// ============================================================================
			
	/**
	 * Constructor.	
	 */
	public CoreDataVersion() 
	{
		super();
		
		this.prefix = "ver_";
		this.usesPrefix = false;
		
		this.dbColumnNames = new Vector<String>();
		this.dbColumnNames.addElement("ID");
		this.dbColumnNames.addElement("ver_major");
		this.dbColumnNames.addElement("ver_minor");
		this.dbColumnNames.addElement("ver_language");
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================

	@Override
	public String getDBTableName() 
	{
		return "CoreDataVersion";
	}

	@Override
	public boolean isEditable()
	{
		return true;
	}

	@Override
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		super.setupJTableColumnModels(ceframe, cetable);
		
		//TODO integer cell renderer and editor
	}

	/**
	 * since this is a system table there is no AbstractGenesisModel 
	 */
	@Override
	public AbstractGenesisModel getRow(String id) 
	{
		return null;
	}

}