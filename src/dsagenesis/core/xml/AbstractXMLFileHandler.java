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
package dsagenesis.core.xml;


/**
 * Abstract base for file handlers.
 * 
 */
public abstract class AbstractXMLFileHandler 
{
	// ============================================================================
	//  Variables
	// ============================================================================
	
	/**
	 * view name is used to show the handler inside a component e.g. pulldown
	 * or list.
	 */
	protected String viewName;
	
	/**
	 * absolute path and file.
	 */
	protected String fileName;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 * 
	 * @param view
	 * @param file
	 */
	public AbstractXMLFileHandler(
			String view, 
			String file
		)
	{
		this.viewName = view;
		this.fileName = file;
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * getViewName
	 * 
	 * @return
	 */
	public String getViewName() 
	{
		return viewName;
	}

	/**
	 * getFileName
	 * 
	 * @return
	 */
	public String getFileName() 
	{
		return fileName;
	}

}
