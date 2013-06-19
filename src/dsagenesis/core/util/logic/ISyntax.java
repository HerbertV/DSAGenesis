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
package dsagenesis.core.util.logic;

import java.sql.SQLException;


/**
 * Syntax
 * 
 * Defines basic constants for all logic classes
 */
public interface ISyntax
{
	/**
	 * every logical string representation must start with this.
	 */
	public static final String PREFIX_LOGIC = "@";

	/**
	 * every syntax has a version prefix.
	 */
	public static final String PREFIX_VERSION = "_v_[";
	
	/**
	 * Parenthesis for grouping logical blocks.
	 */
	public static final char PARENTHESIS_OPEN = '[';
	public static final char PARENTHESIS_CLOSE = ']';
	
	/**
	 * Separator for array blocks.
	 */
	public static final char ARRAY_SEPARATOR = ';';
    
	/**
	 * for masking \n and \t 
	 */
	public static final String MASK_NEWLINE = "&n;";
	public static final String MASK_TAB = "&t;";
	
	
	/**
	 * parseStringFromDB
	 * 
	 * parses the string from the DB
	 * 
	 * @param dbString can be null
	 */
	public void parseStringFromDB(String dbString);
	
	/**
	 * queryLabels
	 * 
	 * for updating the string representation with labels.
	 * 
	 * @throws SQLException
	 */
	public void queryLabels() throws SQLException;
	
	/**
	 * renderStringForDB
	 * 
	 * returns a string that is stored in the DB
	 * can return null
	 * 
	 * @return
	 */
	public String renderStringForDB();
	
	/**
	 * renderStringForCell
	 * 
	 * returns a string that is used by CellRenderer and CellEditor view
	 * 
	 * @return
	 */
	public String renderStringForCell();
	
	/**
	 * renderStringForCellTooltip
	 * 
	 * returns a string for the cell renderer tooltip.
	 * can return null. 
	 * 
	 * @return
	 */
	public String renderStringForCellTooltip();
	
	/**
	 * isEmpty
	 * 
	 * returns true if no values are set.
	 * 
	 * @return
	 */
	public boolean isEmpty();
		
	/**
	 * clone
	 * 
	 * does NOT use the default java.lang.Cloneable interface.
	 * 
	 * @return
	 */
	public Object clone();
}
