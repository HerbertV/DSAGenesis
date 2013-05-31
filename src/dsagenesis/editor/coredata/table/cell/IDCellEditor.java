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
package dsagenesis.editor.coredata.table.cell;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.border.Border;

import jhv.component.ILabeledComponent;
import jhv.component.LabelResource;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.sqlite.TableHelper;
import dsagenesis.core.ui.StatusBar;

/**
 * IDCellEditor
 * 
 * Special Cell Editor for IDs 
 * that prevents that the prefix is deleted.
 */
public class IDCellEditor 
		extends DefaultCellEditor 
		implements ILabeledComponent
{
	// ============================================================================
	//  Constants
	// ============================================================================

	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================

	private JTextField textField;
	
	private String prefix;
	
	private String tablename;
	
	private static LabelResource labelResource;
	
	private StatusBar statusBar;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor.
	 * 
	 * @param prefix
	 * @param tablename
	 * @param sb
	 */
	public IDCellEditor(
			String prefix, 
			String tablename,
			StatusBar sb
		)
	{
		super(new JTextField());

		this.textField = (JTextField)this.getComponent();
		this.prefix = prefix;
		this.tablename = tablename;
		this.statusBar = sb;
		
		if( labelResource == null )
			loadLabels();
	}

	// ============================================================================
	//  Functions
	// ============================================================================

	@Override
	public Object getCellEditorValue()
	{
		return this.textField.getText().trim();
	}
	
	@Override
	public boolean stopCellEditing() 
	{
		boolean isValid = false;
		String value = this.textField.getText().trim();
		
		if( !value.startsWith(prefix) )
		{
			isValid = false;
			statusBar.setStatus(
					labelResource.getProperty("error.missingPrefix","error.missingPrefix"), 
					StatusBar.STATUS_ERROR
				);
			
		} else if( TableHelper.idExists(value, tablename) ) {
			isValid = false;
			statusBar.setStatus(
					labelResource.getProperty("error.idExists","error.idExists"), 
					StatusBar.STATUS_ERROR
				);
		} else if( value.length() == prefix.length() ) {
			isValid = false;
			statusBar.setStatus(
					labelResource.getProperty("error.missingSuffix","error.missingSuffix"), 
					StatusBar.STATUS_ERROR
				);
			
		} else {
			isValid = true;
			statusBar.setStatus(
					labelResource.getProperty("ok","ok"), 
					StatusBar.STATUS_OK
				);
			
		}
		
		if( isValid )
		{
			textField.setBorder(null);
			return super.stopCellEditing();
		} else {
			Border b = BorderFactory.createLineBorder(Color.RED);
			textField.setBorder(b);
		}
		
		return isValid;
	}

	@Override
	public void loadLabels()
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		
		labelResource = new LabelResource(
				this,
				conf.getLanguage(), 
				"resources/labels"
			);
		
	}


	

}
