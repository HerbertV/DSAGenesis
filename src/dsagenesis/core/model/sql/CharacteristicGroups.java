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

import dsagenesis.core.model.xml.AbstractGenesisModel;
import dsagenesis.editor.coredata.table.CoreEditorTableModel;

/**
 * CharacteristicGroups
 * 
 * SQL Model Class.
 * 
 * The Groups are used to group the Characteristics in
 * the edit Characteristics dialog.	
 */
public class CharacteristicGroups
		extends AbstractNamedTableModel 
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
	public CharacteristicGroups() 
	{
		super();
	}
	
	/**
	 * Constructor 2.
	 * 
	 * @param rs	
	 * @throws SQLException 
	 */
	public CharacteristicGroups(ResultSet rs) 
			throws SQLException 
	{
		super(rs);
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================

	
	/**
	 * not needed 
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
