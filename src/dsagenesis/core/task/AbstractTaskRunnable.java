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

import javax.swing.JLabel;


/**
 * AbstractTaskRunnable
 * 
 * Abstract base for runnable's used as finished/error runnable's 
 * for SerialTaskExecutor.
 * 
 * Set the cursor back to default and updates the "status" label.
 */
public abstract class AbstractTaskRunnable 
		implements Runnable 
{

	// ============================================================================
	//  Variables
	// ============================================================================
	
	protected Window window;
	
	private JLabel statusLabel;
	
	protected String statusMessage;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 * 
	 * @param frameOrDialog
	 */
	public AbstractTaskRunnable(
			Window frameOrDialog,
			JLabel statusLabel,
			String statusMessage
		)
	{
		this.window = frameOrDialog;
		this.statusLabel = statusLabel;
		this.statusMessage = statusMessage;
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
		
		statusLabel.setText(statusMessage);
		statusLabel.revalidate();
	}

}
