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
	 * for storing the setup window states
	 */
	public static final String KEY_WIN_SETUP = "win.setup";
	
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

	/**
	 * path keys
	 */
	public static final String KEY_PATH_USER_HOME = "path.user.home";
	public static final String KEY_PATH_DATA = "path.data";
	public static final String KEY_PATH_TEMPLATE = "path.template";
	public static final String KEY_PATH_HERO = "path.hero";
	public static final String KEY_PATH_ARCHTYPE = "path.archtype";
	public static final String KEY_PATH_RACE = "path.race";
	public static final String KEY_PATH_CULTURE = "path.culture";
	public static final String KEY_PATH_PROFESSION = "path.profession";
	public static final String KEY_PATH_NAME = "path.name";
	
	/**
	 * database keys
	 */
	public static final String KEY_DB = "database";
	public static final String KEY_DB_FILE = "database.file";

	public static final String KEY_DEFAULT = "default";
	
	/**
	 * default start generation points
	 */
	public static final String KEY_DEFAULT_START_CP = "default.startCP";
	/**
	 * default maximum disadvantage generation points
	 */
	public static final String KEY_DEFAULT_MAX_DISADVANTAGE_CP = "default.maxDisadvantageCP";
	/**
	 * default maximum attribute generation points
	 */
	public static final String KEY_DEFAULT_MAX_ATTRIBUTE_CP = "default.maxAttributeCP";
	/**
	 * default maximum bad attributes generation points
	 */
	public static final String KEY_DEFAULT_MAX_NEGATIVEATTRIBUTE_CP = "default.maxNegativeAttributeCP";
	
	
}
