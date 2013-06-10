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
package dsagenesis.core.task;

import java.awt.Cursor;
import java.awt.Window;

import dsagenesis.core.ui.StatusBar;

/**
 * AbstractEditorRunnable
 * 
 * used by runnables for editors, adds the status bar
 */
public abstract class AbstractEditorRunnable
		extends AbstractTaskRunnable 
{
	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	protected StatusBar statusBar;

	private int status;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor.
	 * 
	 * @param frameOrDialog
	 * @param statusBar
	 * @param statusMessage
	 * 			StatusBar.STATUS_OK, StatusBar.STATUS_ERROR or StatusBar.STATUS_WORKING 
	 */
	public AbstractEditorRunnable(
			Window frameOrDialog, 
			StatusBar statusBar,
			String statusMessage,
			int status
		)
	{
		super(
				frameOrDialog, 
				null, 
				statusMessage
			);
		this.statusBar = statusBar;
		this.status = status;
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================

	/**
	 * Override and call super.run()
	 */
	@Override
	public void run() 
	{
		window.setCursor(
				Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)
			);
		
		statusBar.setStatus(statusMessage,status);
	}

}
