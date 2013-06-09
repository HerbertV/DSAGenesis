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
import java.util.ArrayList;
import java.util.Vector;

import javax.script.ScriptException;

import dsagenesis.core.sqlite.TableHelper;

import jhv.util.script.JXJavaScriptMethod;
import jhv.util.script.JXScriptArgument;
import jhv.util.script.JXScriptFactory;

/**
 * Formula
 * 
 * A class for converting, parsing formula's from DB string
 * and execute them with javax.script API
 * 
 * Formula is commonly used 
 * for basic calculations and modifiers
 * 
 * The first argument is also returned, e.g. (Pseudo code):
 * 		args: a,b,c 
 * 		script: a = (do something with b and c); return a;
 *  
 * @see jhv.util.script.*
 * 
 * since we are calculating values here, the formula return value is an integer.
 * The arguments can be anything, but are commonly integers as well. 
 */
public class Formula 
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	// for formatting db String
	public static final String PREFIX_ARGUMENTS = "@rgs[";
	public static final String PREFIX_LABELS = "@lbls[";
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
	 * 		[0] id
	 * 		[1] label
	 * 		[2] column name for the label
	 */
	private Vector<Vector<String>> arguments;
		
	private String scriptcode;

	private boolean queryWasDone = false;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Empty Constructor.
	 */
	public Formula() 
	{
		this.arguments = new Vector<Vector<String>>();
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
		this();
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
		this.arguments = idLabels;
		this.scriptcode = scriptcode;
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================

	/**
	 * parseDBString
	 * 
	 * string format: 
	 * 		"@rgs[a,...,n]@lbls[a,....,n]@f(x)[the scriptcode]"
	 * 
	 * @param dbString
	 */
	private void parseDBString(String dbString)
	{
		String subArgs = dbString.substring(
				PREFIX_ARGUMENTS.length(),
				dbString.indexOf(PARENTESIS_CLOSE)
			);
		
		String[] argIds = subArgs.split(",");
		Vector<String> pair;
		
		// extract args
		for( int i=0; i< argIds.length; i++ )
		{
			pair = new Vector<String>(2);
			pair.add(argIds[i]);
			pair.add(argIds[i]);
			this.arguments.add(pair);
		}

		// extract labels
		int start = dbString.indexOf(PREFIX_LABELS) + PREFIX_LABELS.length();
		int end = dbString.indexOf(PARENTESIS_CLOSE,start);
		String subLbls = dbString.substring(start,end);
		String[] lbls = subLbls.split(",");
		
		for( int i=0; i< lbls.length; i++ )
			this.arguments.get(i).add(lbls[i]);
		
		// exctract code
		start = dbString.indexOf(PREFIX_CODE) + PREFIX_CODE.length();
		end = dbString.lastIndexOf(PARENTESIS_CLOSE);
		String subScript = dbString.substring(start,end);
		
		subScript = subScript.replaceAll(MASK_NEWLINE,"\n");
		this.scriptcode = subScript.replaceAll(MASK_TAB,"\t");
	}
	
	/**
	 * queryLabels
	 * 
	 * if you need to query the labels for their ids,
	 * 
	 * before we queried the labels the vector contains
	 * the column name which is used as label.
	 * 
	 * @throws SQLException
	 */
	public void queryLabels() 
			throws SQLException
	{
		if( queryWasDone )
			return;
		
		for( int i=0; i< arguments.size(); i++ )
		{
			String id = arguments.get(i).get(0);
			String column = arguments.get(i).get(2);
			
			arguments.get(i).set(
					1,
					(String)TableHelper.getColumnEntryFor(id, column)
				);
		}
		queryWasDone = true;
	}
	
	/**
	 * calculate
	 * 
	 * @param values
	 * @return
	 * @throws ScriptException
	 */
	public int calculate(
			Vector<Object> values
		) throws ScriptException
	{
		ArrayList<JXScriptArgument<?>> args = new ArrayList<JXScriptArgument<?>>();
		
		for( int i=0; i<arguments.size(); i++ )
		{
			JXScriptArgument<?> arg = new JXScriptArgument<Object>(
					arguments.get(i).get(0),
					values.get(i)
				);
			args.add(arg);
		}
		
		JXJavaScriptMethod<Integer> method = new JXJavaScriptMethod<Integer>(
				new JXScriptFactory(), 
				Integer.class
			);
		
		return method.invoke(scriptcode,args);
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
		try 
		{
			queryLabels();
		} catch (SQLException e) {
			// nothing to do comes from db and should be correct.
		}
		
		String code = scriptcode.replaceAll("\n", " ");
		code = code.replaceAll("\t", " "); 
		for( int i=0; i<arguments.size(); i++ )
			code = code.replaceAll(
					arguments.get(i).get(0),
					arguments.get(i).get(1)
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
		try 
		{
			queryLabels();
		} catch (SQLException e) {
			// nothing to do comes from db and should be correct.
		}
		
		String code = scriptcode.replaceAll("\n", "<br>");
		code = code.replaceAll("\t", "&nbsp; &nbsp; ");
		
		// replace ids with labels
		for( int i=0; i<arguments.size(); i++ )
			code = code.replaceAll(
					arguments.get(i).get(0),
					arguments.get(i).get(1)
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
		String idpart = "";
		String lblpart = "";
		
		for( int i=0; i<arguments.size(); i++ )
		{
			idpart += arguments.get(i).get(0);
			lblpart += arguments.get(i).get(2);
			if( i < (arguments.size()-1) )
			{
				idpart += ",";
				lblpart += ",";
			}
		}
		
		String str = PREFIX_ARGUMENTS
				+ idpart
				+ PARENTESIS_CLOSE
				+ PREFIX_LABELS
				+ lblpart
				+ PARENTESIS_CLOSE
				+ PREFIX_CODE;
		
		String code = scriptcode.replaceAll("\n", MASK_NEWLINE);
		str += code.replaceAll("\t", MASK_TAB) 
				+ PARENTESIS_CLOSE;
		
		return str;
	}

	/**
	 * getArguments
	 * 
	 * @return Vector Vector of arguments
	 * 		Inner Vector:
	 * 		[0] id
	 * 		[1] label
	 * 		[2] column name for the label
	 * 
	 */
	public Vector<Vector<String>> getArguments()
	{
		return this.arguments;
	}

	/**
	 * setArguments
	 * 
	 * @param arguments
	 * 		Inner Vector:
	 * 		[0] id
	 * 		[1] label 
	 * 		[2] column name for the label 
	 */
	public void setArguments(Vector<Vector<String>> arguments) 
	{
		this.arguments = arguments;
	}
	
	/**
	 * addArgument
	 * 
	 * @param id
	 * @param label
	 * @param column
	 */
	public void addArgument(String id, String label, String column)
	{
		Vector<String> arg = new Vector<String>();
		arg.add(id);
		arg.add(label);
		arg.add(column);
		
		this.arguments.add(arg);
	}

	/**
	 * removeArgument
	 * 
	 * @param id
	 */
	public void removeArgument(String id)
	{
		for( int i=0; i<this.arguments.size(); i ++ )
		{
			if( this.arguments.get(i).get(0).equals(id) )
			{
				this.arguments.remove(i);
				return;
			}
		}
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
