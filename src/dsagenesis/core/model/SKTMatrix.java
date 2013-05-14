/*
 *  __  __      
 * /\ \/\ \  __________   
 * \ \ \_\ \/_______  /\   
 *  \ \  _  \  ____/ / /  
 *   \ \_\ \_\ \ \/ / / 
 *    \/_/\/_/\ \ \/ /  
 *             \ \  /
 *              \_\/
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
package dsagenesis.core.model;


/**
 * SKTMatrix
 * 
 * is needed everywhere when it comes to calculating cost for level all the
 * different attributes, talents and spells.
 * 
 * This class is a singleton.
 *
 * The matrix' values are stored in the database as SKT.
 * but it also use the SKTShifts Table for generating the SKT Column.
 */
public class SKTMatrix 
{

	// ============================================================================
	//  Variables
	// ============================================================================
		
	/**
	 * singleton instance.
	 */
	private static SKTMatrix instance;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor
	 */
	private SKTMatrix()
	{
		// TODO Auto-generated constructor stub
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================
		
	/**
	 * getInstance
	 * 
	 * @return
	 */
	public SKTMatrix getInstance()
	{
		if( instance == null )
			instance = new SKTMatrix();
		
		return instance;
	}
	
	
}

