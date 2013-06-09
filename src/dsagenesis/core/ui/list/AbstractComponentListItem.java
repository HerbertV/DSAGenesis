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
package dsagenesis.core.ui.list;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * AbstractComponentListItem
 * 
 * Abstract base class for ComponentList items.
 */
public abstract class AbstractComponentListItem 
		extends JPanel 
{
	// ============================================================================
	//  Constants
	// ============================================================================

	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================

	private boolean isSelected = false;
	
	private boolean isHovered = false;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================

	/**
	 * Constructor
	 * 
	 * @param list
	 */
	@SuppressWarnings("rawtypes")
	public AbstractComponentListItem(ComponentList list) 
	{
		super();
		
		FlowLayout layout = new FlowLayout();
		layout.setHgap(4);
		layout.setVgap(1);
		
		this.setLayout(layout);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		Dimension d = this.getPreferredSize();
		d.height = 26;
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		
		this.addMouseListener(list);
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * setTextColor
	 * 
	 * @param c
	 */
	public abstract void setTextColor(Color c);
	
	/**
	 * isSelected
	 * 
	 * @return
	 */
	public synchronized boolean isSelected()
	{
		return isSelected;
	}
	
	/**
	 * setSelected
	 * 
	 * @param val
	 */
	public synchronized void setSelected(boolean val)
	{
		this.isSelected = val;
	}

	/**
	 * isHovered
	 * 
	 * @return
	 */
	public synchronized boolean isHovered()
	{
		return isHovered;
	}

	/**
	 * setHovered
	 * 
	 * @param val
	 */
	public synchronized void setHovered(boolean val)
	{
		this.isHovered = val;
	}
	

}
