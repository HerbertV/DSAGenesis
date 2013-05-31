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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import jhv.component.ILabeledComponent;
import jhv.component.LabelResource;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.ui.StatusBar;

/**
 * IDCellEditor
 * 
 * Special Cell Editor for IDs 
 * that prevents that the prefix is deleted.
 */
public class NumericCellEditor 
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
	
	private Class<? extends Number> cellClass;
	
	private static LabelResource labelResource;
	
	private StatusBar statusBar;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	public NumericCellEditor(
			Class<? extends Number> c,
			StatusBar sb
		)
	{
		super(new JTextField());

		this.textField = (JTextField)this.getComponent();
		this.textField.setHorizontalAlignment(JLabel.RIGHT);
		this.cellClass = c;
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
		boolean isValid = true;
		String value = this.textField.getText().trim();
		
		try
		{
			if( cellClass == Integer.class )
			{
				Integer.parseInt(value);
			} else if( cellClass == Float.class ) {
				Float.parseFloat(value);
			}
			statusBar.setStatus(
					labelResource.getProperty("ok","ok"), 
					StatusBar.STATUS_OK
				);
		} catch( NumberFormatException e ) {
			isValid = false;
			
			if( cellClass == Integer.class )
			{
				statusBar.setStatus(
						labelResource.getProperty("error.notInteger","error.notInteger"), 
						StatusBar.STATUS_ERROR
					);
			} else if( cellClass == Float.class ) {
				statusBar.setStatus(
						labelResource.getProperty("error.notFloat","error.notFloat"), 
						StatusBar.STATUS_ERROR
					);
			}
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
