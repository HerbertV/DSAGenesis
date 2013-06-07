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

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * ComponentList
 * 
 * A Scrollable list of components.
 * Each list item is a line of the same components.
 */
public class ComponentList 
		extends JScrollPane 
{

	// ============================================================================
	//  Constants
	// ============================================================================

	private static final long serialVersionUID = 1L;
	
	public static final int NO_SELECTION = 0;

	public static final int SINGLE_SELECTION = 1;
	
	public static final int MULTI_SELECTION = 2;
	

	// ============================================================================
	//  Variables
	// ============================================================================
	
	private JPanel paneList;
	
	private int maxVisibleItems;
	
	private int selectionType;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================

	
	
	public ComponentList(
			int visibleItems,
			int selectiontype
		)
	{
		super();
		
		this.maxVisibleItems = visibleItems;
		this.selectionType = selectiontype;
		
		this.paneList = new JPanel();
		GridLayout gl = new GridLayout(maxVisibleItems,1);
		gl.setVgap(-1);
		this.paneList.setLayout(gl);
		
		this.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
			);
		this.setViewportView(this.paneList);
		
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================


}
