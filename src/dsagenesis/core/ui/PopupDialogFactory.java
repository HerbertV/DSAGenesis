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
package dsagenesis.core.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dsagenesis.core.config.GenesisConfig;

import jhv.component.LabelResource;



/**
 * PopupDialogFactory
 * 
 * static helper class for creating all 
 * the warning, error and info popups.
 */
public class PopupDialogFactory 
{
	private static LabelResource labelResource;
	
	/**
	 * loads the labels
	 */
	private static void loadLabels()
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		
		labelResource = new LabelResource(
				PopupDialogFactory.class.getSimpleName()+ ".lbl",
				conf.getLanguage(), 
				"labels"
			);
	}
	
	/**
	 * confirmCloseWithUnsavedData
	 * 
	 * @param parent
	 * @return
	 */
	public static int confirmCloseWithUnsavedData(JFrame parent)
	{
		if( labelResource == null )
			loadLabels();
		
		return JOptionPane.showConfirmDialog(
				parent,
				parent.getTitle() 
					+ labelResource.getProperty("closeUnsaved.message","closeUnsaved.message"),
				labelResource.getProperty("closeUnsaved.title","closeUnsaved.title"),
				JOptionPane.YES_NO_OPTION
			);
	}
	
	// TODO save success dialog
	
	// TODO save failed dialog
	
	
}
