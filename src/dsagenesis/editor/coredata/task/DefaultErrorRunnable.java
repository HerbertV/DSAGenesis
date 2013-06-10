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
package dsagenesis.editor.coredata.task;

import java.awt.Toolkit;

import dsagenesis.core.task.AbstractEditorRunnable;
import dsagenesis.core.ui.StatusBar;
import dsagenesis.editor.coredata.CoreEditorFrame;

/**
 * DefaultFinishedRunnable
 */
public class DefaultErrorRunnable 
		extends AbstractEditorRunnable 
{

	/**
	 * Constructor.
	 * 
	 * @param frame
	 * @param statusBar
	 * @param statusMessage
	 */
	public DefaultErrorRunnable(
			CoreEditorFrame frame, 
			StatusBar statusBar,
			String statusMessage
		)
	{
		super(
				frame, 
				statusBar, 
				statusMessage, 
				StatusBar.STATUS_ERROR
			);
	}

		@Override
	public void run() 
	{
		super.run();
		
		statusBar.setProgress(0);
		statusBar.revalidate();
		
		((CoreEditorFrame)this.window).setEnabled(true);
		Toolkit.getDefaultToolkit().beep();
	}
}
