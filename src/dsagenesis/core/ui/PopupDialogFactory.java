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
				PopupDialogFactory.class.getSimpleName(),
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
				AbstractGenesisFrame.markUnsaved( parent.getTitle(), false) 
					+ " "
					+ labelResource.getProperty("closeUnsaved.message","closeUnsaved.message"),
				labelResource.getProperty("closeUnsaved.title","closeUnsaved.title"),
				JOptionPane.YES_NO_OPTION
			);
	}
	
	/**
	 * confirmDeleteDatabaseRow
	 * 
	 * @param parent
	 * @return
	 */
	public static int confirmDeleteDatabaseRow(JFrame parent)
	{
		if( labelResource == null )
			loadLabels();
		
		return JOptionPane.showConfirmDialog(
				parent,
				AbstractGenesisFrame.markUnsaved( parent.getTitle(), false) 
					+ " "
					+ labelResource.getProperty("deleteDatabaseRow.message","deleteDatabaseRow.message"),
				labelResource.getProperty("deleteDatabaseRow.title","deleteDatabaseRow.title"),
				JOptionPane.YES_NO_OPTION
			);
	}
	
	/**
	 * copyDatabaseError
	 */
	public static void copyDatabaseError()
	{
		if( labelResource == null )
			loadLabels();
		
		JOptionPane.showMessageDialog(
				null,
				labelResource.getProperty("copyDatabaseError.message","copyDatabaseError.message"),
				labelResource.getProperty("copyDatabaseError.title","copyDatabaseError.title"),
				JOptionPane.ERROR_MESSAGE
			);
	}
	
	/**
	 * pasteDatabaseError
	 */
	public static void pasteDatabaseError()
	{
		if( labelResource == null )
			loadLabels();
		
		JOptionPane.showMessageDialog(
				null,
				labelResource.getProperty("pasteDatabaseError.message","pasteDatabaseError.message"),
				labelResource.getProperty("pasteDatabaseError.title","pasteDatabaseError.title"),
				JOptionPane.ERROR_MESSAGE
			);
	}
	
	// TODO save success dialog
	
	// TODO save failed dialog
	
	
}
