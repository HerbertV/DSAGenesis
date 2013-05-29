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

import dsagenesis.core.model.xml.AbstractGenesisModel;

/**
 * ProfessionCategories
 * 
 * SQL Model Class.
 */
public class ProfessionCategories
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
	public ProfessionCategories() 
	{
		super();
	}
	
	/**
	 * Constructor 2.
	 * 
	 * @param rs	
	 * @throws SQLException 
	 */
	public ProfessionCategories(ResultSet rs) 
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
	public void queryReferences() throws SQLException 
	{
		// not needed
	}

}
