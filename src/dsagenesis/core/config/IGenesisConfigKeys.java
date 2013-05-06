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
package dsagenesis.core.config;

/**
 * Interface containing system property keys.
 */
public interface IGenesisConfigKeys 
{
	/**
	 * basic partial keys
	 */
	public static final String KEY_WIN = "win";
	public static final String KEY_SIZE = "size";
	public static final String KEY_ISFULLSCREEN = "isFullScreen";
	public static final String KEY_POSITION = "pos";
	
	/**
	 * for storing the base editor window states
	 */
	public static final String KEY_WIN_BASE = "win.base";
	/**
	 * for storing the meta editor window states
	 */
	public static final String KEY_WIN_META = "win.meta";
	/**
	 * for storing the hero editor window states
	 */
	public static final String KEY_WIN_HERO = "win.hero";

}
