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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * AbstractComponentListItem
 * 
 * Abstract base class for ComponentList items.
 */
public abstract class AbstractComponentListItem 
		extends JPanel 
		implements MouseListener 
{
	// ============================================================================
	//  Constants
	// ============================================================================

	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Constructors
	// ============================================================================

	public AbstractComponentListItem() 
	{
		super();
		
		// TODO
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================

	@Override
	public void mouseClicked(MouseEvent me) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent me)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent me) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent me) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent me) 
	{
		// TODO Auto-generated method stub
	}

}
