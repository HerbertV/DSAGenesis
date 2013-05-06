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
	
	/**
	 * for storing the base editor window states
	 */
	public static final String KEY_WIN_BASE_SIZE = "win.base.size";
	public static final String KEY_WIN_BASE_ISFULLSCREEN = "win.base.isFullscreen";

	/**
	 * for storing the meta editor window states
	 */
	public static final String KEY_WIN_META_SIZE = "win.meta.size";
	public static final String KEY_WIN_META_ISFULLSCREEN = "win.meta.isFullscreen";

	/**
	 * for storing the hero editor window states
	 */
	public static final String KEY_WIN_HERO_SIZE = "win.hero.size";
	public static final String KEY_WIN_HERO_ISFULLSCREEN = "win.hero.isFullscreen";

}
