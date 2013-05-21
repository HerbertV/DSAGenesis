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

import dsagenesis.core.model.xml.AbstractGenesisModel;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;

/**
 * Characteristics
 * 
 * SQL Model Class.
 * 
 * contains Attributes and anything else that is leveled or calculated
 */
public class Characteristics
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
	 * 
	 * @param rs	
	 * @throws SQLException 
	 */
	public Characteristics(ResultSet rs) 
			throws SQLException 
	{
		super(rs);
		
		this.dbColumnNames = new Vector<String>();
		this.dbColumnNames.addElement("ID");
		this.dbColumnNames.addElement("c_priorty");
		this.dbColumnNames.addElement("c_acronym");
		this.dbColumnNames.addElement("c_name");
		this.dbColumnNames.addElement("c_ref_cg_ID");
		this.dbColumnNames.addElement("c_is_used_by_hero");
		this.dbColumnNames.addElement("c_h_cp_cost");
		this.dbColumnNames.addElement("c_h_min_value");
		this.dbColumnNames.addElement("c_h_max_value");
		this.dbColumnNames.addElement("c_h_can_increase");
		this.dbColumnNames.addElement("c_h_can_decrease");
		this.dbColumnNames.addElement("c_h_skt_column");
		this.dbColumnNames.addElement("c_h_has_Formular");
		this.dbColumnNames.addElement("c_h_formular");
		this.dbColumnNames.addElement("c_is_used_by_familiar");
		this.dbColumnNames.addElement("c_f_min_value");
		this.dbColumnNames.addElement("c_f_max_value");
		this.dbColumnNames.addElement("c_f_can_increase");
		this.dbColumnNames.addElement("c_f_can_decrease");
		this.dbColumnNames.addElement("c_f_skt_column");
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================

	/**
	 *  
	 */
	@Override
	public AbstractGenesisModel getRow(String id) 
	{
		// TODO
		return null;
	}
	
	@Override
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		super.setupJTableColumnModels(ceframe, cetable);
		
		TableColumn currColumn;
        
        //col 3
        currColumn = cetable.getColumnModel().getColumn(3);
        currColumn.setMinWidth(120);
        //col 4
        currColumn = cetable.getColumnModel().getColumn(4);
        currColumn.setMinWidth(120);
    }
	
	@Override
	public Vector<Class<?>> getTableColumnClasses()
	{
		Vector<Class<?>> vec = new Vector<Class<?>>(this.dbColumnNames.size());
		vec.add(String.class);
		vec.add(Integer.class);
		vec.add(String.class);
		vec.add(String.class);
		vec.add(String.class);
		vec.add(Boolean.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Boolean.class);
		vec.add(Boolean.class);
		vec.add(String.class);
		vec.add(Boolean.class);
		vec.add(String.class);
		vec.add(Boolean.class);
		vec.add(Integer.class);
		vec.add(Integer.class);
		vec.add(Boolean.class);
		vec.add(Boolean.class);
		vec.add(String.class);
		
		return vec;
	}

}
