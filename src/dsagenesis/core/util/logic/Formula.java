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

import java.util.Vector;

/**
 * Formula
 * 
 * A class for converting, parsing formula's from DB
 * and execute them with javax.script API
 * 
 * Formula is commonly used 
 * for basic calculations and modifiers
 * 
 * The first argument is also returned, e.g.:
 * 		args: a,b,c 
 * 		script: a = (do something with b and c); return a;
 *  
 * @see jhv.util.script.*
 */
public class Formula 
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	// for formatting db String
	public static final String PREFIX_ARGUMENTS = "@rgs[";
	public static final String PREFIX_CODE = "@f(x)[";
	public static final char PARENTESIS_OPEN = '[';
	public static final char PARENTESIS_CLOSE = ']';
		
	//masks for DB storage
	public static final String MASK_NEWLINE = "@n;";
	public static final String MASK_TAB = "@t;";
	
	
	// ============================================================================
	//  Variables
	// ============================================================================
	
	/**
	 * Argument names are used inside the script code.
	 * they are stored as a paired id label Vector Vector.
	 */
	private Vector<Vector<String>> argumentIdLabels;
	
	private String scriptcode;

	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Empty Constructor.
	 */
	public Formula() 
	{
		this.argumentIdLabels = new Vector<Vector<String>>();
		this.scriptcode = "";
	}

	/**
	 * Constructor 2.
	 * used by table cells
	 * 
	 * @param dbString
	 */
	public Formula(String dbString)
	{
		super();
		parseDBString(dbString);
	}
	
	/**
	 * Constructor 3
	 * 
	 * used by FormulaCellDialog to create a new formula.
	 * 
	 * @param idLabels
	 * @param scriptcode
	 */
	public Formula(
			Vector<Vector<String>> idLabels, 
			String scriptcode
		)
	{
		this.argumentIdLabels = idLabels;
		this.scriptcode = scriptcode;
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================

	/**
	 * parseDBString
	 * 
	 * string format: "@rgs[a,...,n]@f(x)[the scriptcode]"
	 * 
	 * @param dbString
	 */
	private void parseDBString(String dbString)
	{
		String subArgs = dbString.substring(
				PREFIX_ARGUMENTS.length(),
				(dbString.indexOf(PARENTESIS_CLOSE)-1)
			);
		
		String[] argIds = subArgs.split(",");
		Vector<String> pair;
		
		// extract args
		for( int i=0; i< argIds.length; i++ )
		{
			pair = new Vector<String>(2);
			pair.add(argIds[i]);
			// by default fill also the label as id,
			// maybe some forgets to query the labels.
			pair.add(argIds[i]);
			this.argumentIdLabels.add(pair);
		}
		
		int startcode = PREFIX_CODE.length();
		String subScript = dbString.substring(
				startcode,
				(dbString.lastIndexOf(PARENTESIS_CLOSE)-1)
			);
		subScript = subScript.replaceAll(MASK_NEWLINE,"\n");
		this.scriptcode = subScript.replaceAll(MASK_TAB,"\t");
	}
	
	/**
	 * queryLabels
	 * 
	 * if you need to query the labels for their ids
	 */
	public void queryLabels()
	{
		// TODO query labels
	}
	
	
	/**
	 * getStringForCell
	 * 
	 * returns a string that is used by CellRenderer and CellEditor view
	 * 
	 * @return
	 */
	public String getStringForCell()
	{
		String code = scriptcode.replaceAll("\n", " ");
		code = code.replaceAll("\t", " "); 
		for( int i=0; i<argumentIdLabels.size(); i++ )
			code = code.replaceAll(
					argumentIdLabels.get(i).get(0),
					argumentIdLabels.get(i).get(1)
				);
		
		return "f(x):{"+ code + "}";
	}
	
	/**
	 * getStringForCellTooltip
	 * 
	 * returns a string for the cell renderer tooltip.
	 * 
	 * @return
	 */
	public String getStringForCellTooltip()
	{
		String code = scriptcode.replaceAll("\n", "<br>");
		code = code.replaceAll("\t", "&nbsp; &nbsp; ");
		
		// replace ids with labels
		for( int i=0; i<argumentIdLabels.size(); i++ )
			code = code.replaceAll(
					argumentIdLabels.get(i).get(0),
					argumentIdLabels.get(i).get(1)
				);
		
		return "<html>" + code + "</html>";
	}
	
	/**
	 * getStringDBValue
	 * 
	 * returns a string that is stored in the DB
	 * 
	 * @return
	 */
	public String getStringDBValue()
	{
		String str = PREFIX_ARGUMENTS;
		
		for( int i=0; i<argumentIdLabels.size(); i++ )
		{
			str += argumentIdLabels.get(i).get(0);
			if( i < (argumentIdLabels.size()-1) )
				str += ",";
		}
		str += PARENTESIS_CLOSE
				+ PREFIX_CODE;
		
		String code = scriptcode.replaceAll("\n", MASK_NEWLINE);
		str += code.replaceAll("\t", MASK_TAB) 
				+ PARENTESIS_CLOSE;
		
		return str;
	}

	/**
	 * getArguments
	 * 
	 * @return
	 */
	public Vector<Vector<String>> getArgumentIdLabels()
	{
		return this.argumentIdLabels;
	}

	/**
	 * setArguments
	 * 
	 * @param arguments
	 */
	public void setArgumentIdLabels(Vector<Vector<String>> idLabels) 
	{
		this.argumentIdLabels = idLabels;
	}

	/**
	 * getScriptcode
	 * 
	 * @return
	 */
	public String getScriptcode() 
	{
		return this.scriptcode;
	}

	/**
	 * setScriptcode
	 * 
	 * @param scriptcode
	 */
	public void setScriptcode(String scriptcode) 
	{
		this.scriptcode = scriptcode;
	}
	
	/**
	 * toString
	 */
	public String toString()
	{
		return this.getStringForCell();
	}

	
}
