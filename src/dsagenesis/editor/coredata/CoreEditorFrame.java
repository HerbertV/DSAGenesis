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
package dsagenesis.editor.coredata;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.config.IGenesisConfigKeys;
import dsagenesis.core.ui.AbstractGenesisFrame;
import dsagenesis.core.ui.HelpDialog;
import dsagenesis.core.ui.InfoDialog;

import javax.swing.JToolBar;
import java.awt.BorderLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import jhv.component.LabelResource;
import jhv.image.ImageResource;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;

/**
 * JFrame for the Core Data Editor.
 */
public class CoreEditorFrame 
		extends AbstractGenesisFrame 
{

	// ============================================================================
	//  Constants
	// ============================================================================
			
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	private LabelResource labelResource;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor.
	 */
	public CoreEditorFrame()
	{
		super(IGenesisConfigKeys.KEY_WIN_BASE);
		
		this.loadLabels();
		this.setTitle(
				GenesisConfig.getInstance().getAppTitle()
					+ " - "
					+ labelResource.getProperty("title", "title")
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
		ImageResource irAddRow = new ImageResource("images/icons/dbAddRow.gif",this);
		ImageResource irDeleteRow = new ImageResource("images/icons/dbRemoveRow.gif",this);
		
		ImageResource irExport = new ImageResource("images/icons/dbExport.gif",this);
		ImageResource irImport = new ImageResource("images/icons/dbImport.gif",this);
		ImageResource irBackup = new ImageResource("images/icons/dbBackup.gif",this);
		ImageResource irInfo = new ImageResource("images/icons/info.gif",this);
		ImageResource irHelp = new ImageResource("images/icons/help.gif",this);
		
		// toolbar
		{
			JToolBar toolBar = new JToolBar();
			toolBar.setFloatable(false);
			getContentPane().add(toolBar, BorderLayout.NORTH);
			
			JButton btnCopy = new JButton("");
			btnCopy.setToolTipText(labelResource.getProperty("copy", "copy"));
			btnCopy.setIcon(irCopy.getImageIcon());
			toolBar.add(btnCopy);
			
			JButton btnPaste = new JButton("");
			btnPaste.setToolTipText(labelResource.getProperty("paste", "paste"));
			btnPaste.setIcon(irPaste.getImageIcon());
			toolBar.add(btnPaste);
			
			JSeparator separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setSize(10, 16);
			separator.setMaximumSize(separator.getSize());
			toolBar.add(separator);
			
			JButton btnAddRow = new JButton("");
			btnAddRow.setToolTipText(labelResource.getProperty("addRow", "addRow"));
			btnAddRow.setIcon(irAddRow.getImageIcon());
			toolBar.add(btnAddRow);
			
			JButton btnDeleteRow = new JButton("");
			btnDeleteRow.setToolTipText(labelResource.getProperty("deleteRow", "deleteRow"));
			btnDeleteRow.setIcon(irDeleteRow.getImageIcon());
			toolBar.add(btnDeleteRow);
		}
		
		// menubar
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			
			JMenu mnFile = new JMenu(labelResource.getProperty("mnFile", "mnFile"));
			menuBar.add(mnFile);
			
			JMenuItem mntmBackup = new JMenuItem(labelResource.getProperty("mntmBackup", "mntmBackup"));
			mntmBackup.setIcon(irBackup.getImageIcon());
			mnFile.add(mntmBackup);
			
			JSeparator separator = new JSeparator();
			mnFile.add(separator);
			
			JMenuItem mntmImport = new JMenuItem(labelResource.getProperty("mntmImport", "mntmImport"));
			mntmImport.setIcon(irImport.getImageIcon());
			mnFile.add(mntmImport);
			
			JMenuItem mntmExport = new JMenuItem(labelResource.getProperty("mntmExport", "mntmExport"));
			mntmExport.setIcon(irExport.getImageIcon());
			mnFile.add(mntmExport);
			
			JMenu mnEdit = new JMenu(labelResource.getProperty("mnEdit", "mnEdit"));
			menuBar.add(mnEdit);
			
			JMenuItem mntmCopy = new JMenuItem(labelResource.getProperty("copy", "copy"));
			mntmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
			mntmCopy.setIcon(irCopy.getImageIcon());
			mnEdit.add(mntmCopy);
			
			JMenuItem mntmPaste = new JMenuItem(labelResource.getProperty("paste", "paste"));
			mntmPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
			mntmPaste.setIcon(irPaste.getImageIcon());
			mnEdit.add(mntmPaste);
			
			JSeparator separator_1 = new JSeparator();
			mnEdit.add(separator_1);
			
			JMenuItem mntmAddRow = new JMenuItem(labelResource.getProperty("addRow", "addRow"));
			mntmAddRow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
			mntmAddRow.setIcon(irAddRow.getImageIcon());
			mnEdit.add(mntmAddRow);
			
			JMenuItem mntmDeleteRow = new JMenuItem(labelResource.getProperty("deleteRow", "deleteRow"));
			mntmDeleteRow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
			mntmDeleteRow.setIcon(irDeleteRow.getImageIcon());
			mnEdit.add(mntmDeleteRow);
			
			JMenu mnHelp = new JMenu(labelResource.getProperty("mnHelp", "mnHelp"));
			menuBar.add(mnHelp);
			
			JMenuItem mntmInfo = new JMenuItem(labelResource.getProperty("mntmInfo", "mntmInfo"));
			mntmInfo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						InfoDialog d = new InfoDialog(CoreEditorFrame.this);
						d.setVisible(true);
					}
				});
			mntmInfo.setIcon(irInfo.getImageIcon());
			mnHelp.add(mntmInfo);
			
			JMenuItem mntmHelp = new JMenuItem(labelResource.getProperty("mntmHelp", "mntmHelp"));
			mntmHelp.setIcon(irHelp.getImageIcon());
			mntmHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			mntmHelp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						File page = new File("./help/coreDataEditor.html");
						try {
							HelpDialog d = HelpDialog.getInstance();
							d.openURL(page.toURI().toURL().toExternalForm());
							d.setVisible(true);
						} catch (MalformedURLException e) {
							// nothing to do
						}
					}
				});
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


	@Override
	public void loadLabels()
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		
		labelResource = new LabelResource(
				this,
				conf.getLanguage(), 
				"labels"
			);
	}
}
