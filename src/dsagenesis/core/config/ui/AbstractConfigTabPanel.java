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
package dsagenesis.core.config.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import jhv.component.IChangeableContentComponent;
import jhv.component.ILabeledComponent;
import jhv.component.LabelResource;
import jhv.swing.AbstractGridBagPanel;

/**
 * Abstract base for all Panels used in our SetupFrame.
 * 
 */
public abstract class AbstractConfigTabPanel 
		extends AbstractGridBagPanel 
		implements ILabeledComponent,
			IChangeableContentComponent,
			PropertyChangeListener,
			ChangeListener,
			DocumentListener,
			ItemListener
{
	// ============================================================================
	//  Constants
	// ============================================================================

	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	/**
	 * becomes true if one entry was altered by the user.
	 */
	protected boolean hasContentChanged = false;
	
	/**
	 * label property resource
	 */
	protected LabelResource labelResource;
	
	/**
	 * the frame
	 */
	protected ConfigFrame jFrame;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 * 
	 * @param frame
	 */
	public AbstractConfigTabPanel(ConfigFrame frame)
	{
		super();
		this.jFrame = frame;
	}

	
	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * 
	 */
	public abstract void saveSetup();
	
	/**
	 * 
	 */
	public abstract void loadSetup();

	/**
	 * for color picker JPanel, 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e) 
	{
		// used by color pickers
		if( e.getNewValue() == e.getOldValue() )
			return;
		
		this.jFrame.setUnsavedMarker(true);
	}
	
	/**
	 * for JSpinner
	 */
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		this.jFrame.setUnsavedMarker(true);	
	}
	
	/**
	 * for JTextFields
	 */
	@Override
	public void changedUpdate(DocumentEvent e)
	{
		this.jFrame.setUnsavedMarker(true);	
	}

	/**
	 * for JTextFields
	 */
	@Override
	public void insertUpdate(DocumentEvent e)
	{
		this.jFrame.setUnsavedMarker(true);	
	}

	/**
	 * for JTextFields
	 */
	@Override
	public void removeUpdate(DocumentEvent e)
	{
		this.jFrame.setUnsavedMarker(true);	
	}
	
	/**
	 * for JCheckbox, JCombobox
	 */
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if( e.getStateChange() == ItemEvent.SELECTED
				|| (e.getSource() instanceof JCheckBox)
			) 
			this.jFrame.setUnsavedMarker(true);	
	}
}
	 
