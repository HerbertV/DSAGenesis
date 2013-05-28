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

/**
 * SKT
 * 
 * SQL Model Class.
 * 
 * this class is only used by Core Data Editor (for generating the JTable), 
 * for accessing the SKT use SKTMatrix! 
 */
public class SKT
		extends AbstractSQLTableModel 
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
	public SKT() 
	{
		super();
	}
	
	/**
	 * Constructor 2.
	 * 
	 * @param rs	
	 * @throws SQLException 
	 */
	public SKT(ResultSet rs) 
			throws SQLException 
	{
		super(rs);
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================

	@Override
	protected void setupDBColumns() 
	{
		this.dbColumnNames = new Vector<String>();
		this.dbColumnNames.addElement("ID");
		this.dbColumnNames.addElement("skt_a");
		this.dbColumnNames.addElement("skt_b");
		this.dbColumnNames.addElement("skt_c");
		this.dbColumnNames.addElement("skt_d");
		this.dbColumnNames.addElement("skt_e");
		this.dbColumnNames.addElement("skt_f");
		this.dbColumnNames.addElement("skt_g");
		this.dbColumnNames.addElement("skt_h");
	}

	/**
	 * for accessing the SKT use class SKTMatrix. 
	 */
	@Override
	public AbstractGenesisModel getRow(String id) 
	{
		return null;
	}	
	
	@Override
	public Vector<Class<?>> getTableColumnClasses()
	{
		Vector<Class<?>> vec = new Vector<Class<?>>(this.dbColumnNames.size());
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		
		return vec;
	}

	@Override
	public void queryReferences() throws SQLException 
	{
		// not needed
	}

}
