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

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import dsagenesis.core.sqlite.CreateDBTask;

/**
 * CreateDBTaskCoreEditor
 * 
 * uses progressbar and a single line status label.
 */
public class CreateDBTaskCoreEditor 
		extends CreateDBTask 
{

	/**
	 * Constructor.
	 * 
	 * @param lbl
	 * @param progress
	 * @param header
	 */
	public CreateDBTaskCoreEditor(
			JLabel lbl, 
			JProgressBar progress,
			String header
		)
	{
		super(lbl, progress, header);
	}
	
	/**
	 * adjustment for Core Editor single line status bar.
	 */
	@Override
	protected String prepareNextStep()
	{
		String status = header + sqlfiles[this.currentStep];
		
		return status;
	}
}
