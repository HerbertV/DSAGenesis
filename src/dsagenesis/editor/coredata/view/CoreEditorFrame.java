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
package dsagenesis.editor.coredata.view;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.config.IGenesisConfigKeys;
import dsagenesis.core.view.AbstractGenesisFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import jhv.image.ImageResource;

/**
 * JFrame for the Core Data Editor.
 */
public class CoreEditorFrame 
		extends AbstractGenesisFrame 
{

	// ============================================================================
	//  Variables
	// ============================================================================
			
	private static final long serialVersionUID = 1L;

	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor.
	 */
	public CoreEditorFrame()
	{
		super(
				GenesisConfig.getInstance().getAppTitle() + " - Core Editor",
				GenesisConfig.APP_ICON,
				IGenesisConfigKeys.KEY_WIN_BASE				
			);
		
		initBars();
		
		
	}

	

	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * initializes the bars.
	 */
	private void initBars() 
	{
		// icons
		ImageResource irCopy = new ImageResource("images/icons/copy.gif",this);
		ImageResource irPaste = new ImageResource("images/icons/paste.gif",this);
		ImageResource irAddRow = new ImageResource("images/icons/addRow.gif",this);
		ImageResource irDeleteRow = new ImageResource("images/icons/removeRow.gif",this);
				
		// toolbar
		{
			JToolBar toolBar = new JToolBar();
			toolBar.setFloatable(false);
			getContentPane().add(toolBar, BorderLayout.NORTH);
			
			JButton btnCopy = new JButton("");
			btnCopy.setToolTipText("Kopieren");
			btnCopy.setIcon(new ImageIcon(irCopy.getImage()));
			toolBar.add(btnCopy);
			
			JButton btnPaste = new JButton("");
			btnPaste.setToolTipText("Einf\u00FCgen");
			btnPaste.setIcon(new ImageIcon(irPaste.getImage()));
			toolBar.add(btnPaste);
			
			JSeparator separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setSize(10, 16);
			separator.setMaximumSize(separator.getSize());
			toolBar.add(separator);
			
			JButton btnAddRow = new JButton("");
			btnAddRow.setToolTipText("Zeile Einf\u00FCgen");
			btnAddRow.setIcon(new ImageIcon(irAddRow.getImage()));
			toolBar.add(btnAddRow);
			
			JButton btnDeleteRow = new JButton("");
			btnDeleteRow.setToolTipText("Zeile L\u00F6schen");
			btnDeleteRow.setIcon(new ImageIcon(irDeleteRow.getImage()));
			toolBar.add(btnDeleteRow);
		}
		
		// menubar
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			
			JMenu mnFile = new JMenu("Datei");
			menuBar.add(mnFile);
			
			JMenuItem mntmBackup = new JMenuItem("Backup");
			mnFile.add(mntmBackup);
			
			JSeparator separator = new JSeparator();
			mnFile.add(separator);
			
			JMenuItem mntmImport = new JMenuItem("Import");
			mnFile.add(mntmImport);
			
			JMenuItem mntmExport = new JMenuItem("Export");
			mnFile.add(mntmExport);
			
			JMenu mnEdit = new JMenu("Bearbeiten");
			menuBar.add(mnEdit);
			
			JMenuItem mntmCopy = new JMenuItem("Kopieren");
			mntmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
			mntmCopy.setIcon(new ImageIcon(irCopy.getImage()));
			mnEdit.add(mntmCopy);
			
			JMenuItem mntmPaste = new JMenuItem("Einf\u00FCgen");
			mntmPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
			mntmPaste.setIcon(new ImageIcon(irPaste.getImage()));
			mnEdit.add(mntmPaste);
			
			JSeparator separator_1 = new JSeparator();
			mnEdit.add(separator_1);
			
			JMenuItem mntmAddRow = new JMenuItem("Zeile Einf\u00FCgen");
			mntmAddRow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
			mntmAddRow.setIcon(new ImageIcon(irAddRow.getImage()));
			mnEdit.add(mntmAddRow);
			
			JMenuItem mntmDeleteRow = new JMenuItem("Zeile L\u00F6schen");
			mntmDeleteRow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
			mntmDeleteRow.setIcon(new ImageIcon(irDeleteRow.getImage()));
			mnEdit.add(mntmDeleteRow);
			
			JMenu mnHelp = new JMenu("Hilfe");
			menuBar.add(mnHelp);
			
			JMenuItem mntmInfo = new JMenuItem("Info");
			mnHelp.add(mntmInfo);
			
			JMenuItem mntmHelp = new JMenuItem("Hilfe");
			mntmHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			mnHelp.add(mntmHelp);
		}
		
	}
	
	/**
	 * core editor saves on the fly. since it accesses the sqlite database.
	 * so it returns always true.
	 */
	@Override
	public boolean isSaved() 
	{
		return true;
	}
}
