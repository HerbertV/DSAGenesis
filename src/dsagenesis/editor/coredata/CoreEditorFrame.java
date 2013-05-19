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
package dsagenesis.editor.coredata;

import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.config.IGenesisConfigKeys;
import dsagenesis.core.model.sql.CoreDataTableIndex;
import dsagenesis.core.model.sql.CoreDataVersion;
import dsagenesis.core.model.sql.TableColumnLabels;
import dsagenesis.core.sqlite.DBConnector;
import dsagenesis.core.sqlite.TableHelper;
import dsagenesis.core.ui.AbstractGenesisFrame;
import dsagenesis.core.ui.HelpDialog;
import dsagenesis.core.ui.InfoDialog;
import dsagenesis.editor.coredata.table.CoreEditorTable;

import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import jhv.component.LabelResource;
import jhv.image.ImageResource;
import jhv.util.debug.logger.ApplicationLogger;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JTabbedPane;

/**
 * JFrame for the Core Data Editor.
 */
public class CoreEditorFrame 
		extends AbstractGenesisFrame 
		implements TableModelListener, ActionListener, ChangeListener
{

	// ============================================================================
	//  Constants
	// ============================================================================
			
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	/**
	 * for status messages
	 */
	private JLabel lblStatus;
	
	/**
	 * Note for the top split
	 */
	private JLabel lblTopNote;
	
	/**
	 * Note for the bottom split
	 */
	private JLabel lblBottomNote;
	
	/**
	 * panel for the editable core data tables
	 */
	private JTabbedPane tabbedPaneCore;
	
	/**
	 * panel for mostly uneditable system and cross reference tables.
	 */
	private JTabbedPane tabbedPaneInternal;
	
	/**
	 * Vectors for accessing the tables
	 */
	private Vector<CoreEditorTable> coreTables;
	private Vector<CoreEditorTable> internalTables;
	
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
		
	/**
	 * Constructor.
	 */
	public CoreEditorFrame()
	{
		super(IGenesisConfigKeys.KEY_WIN_BASE);
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(3);
		borderLayout.setHgap(3);
		
		this.loadLabels();
		this.setTitle(
				GenesisConfig.getInstance().getAppTitle()
					+ " - "
					+ labelResource.getProperty("title", "title")
			);
		
		initBars();
		
		// top split
		JPanel panelSplitTop = new JPanel();
		panelSplitTop.setLayout(new BorderLayout(0, 0));
		panelSplitTop.setMinimumSize(new Dimension(50,50));
		{
			TitledBorder titleBorder = BorderFactory.createTitledBorder(
					labelResource.getProperty("topNoteTitle", "topNoteTitle")
				);
			panelSplitTop.setLayout(new BorderLayout(0, 0));
			this.lblTopNote = new JLabel("Alle Tabellen die direkt mit Inhalten für die Heldenverwaltung in verbindung stehen.");
			this.lblTopNote.setBorder(titleBorder);
			panelSplitTop.add(this.lblTopNote,BorderLayout.NORTH);
		}
		{
			this.tabbedPaneCore = new JTabbedPane(JTabbedPane.TOP);
			panelSplitTop.add(this.tabbedPaneCore, BorderLayout.CENTER);
		}
		
		// bottom split
		JPanel panelSplitBottom = new JPanel();
		panelSplitBottom.setLayout(new BorderLayout(0, 0));
		panelSplitBottom.setMinimumSize(new Dimension(50,50));
		{
			// TODO labels
			TitledBorder titleBorder = BorderFactory.createTitledBorder(
					labelResource.getProperty("bottomNoteTitle", "bottomNoteTitle")
				);
			this.lblBottomNote = new JLabel("System Tabellen und Referenz Tabellen.");
			this.lblBottomNote.setBorder(titleBorder);
			panelSplitBottom.add(this.lblBottomNote,BorderLayout.NORTH);
		}
		{
			this.tabbedPaneInternal = new JTabbedPane(JTabbedPane.TOP);
			panelSplitBottom.add(this.tabbedPaneInternal, BorderLayout.CENTER);
			this.tabbedPaneInternal.addChangeListener(this);
		}
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		splitPane.setTopComponent(panelSplitTop);
		splitPane.setBottomComponent(panelSplitBottom);
		splitPane.setDividerLocation(getHeight()/2);
		
		this.lblStatus = new JLabel(
				labelResource.getProperty("status.init", "status.init")
			);
		this.lblStatus.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		getContentPane().add(this.lblStatus, BorderLayout.SOUTH);
		
		try {
			initDBAndTabs();
		} catch (SQLException e) {
			ApplicationLogger.logError("Cannot init tabs for CoreEditorFrame.");
			ApplicationLogger.logError(e);
		}
	}
	

	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * connection to the database
	 * and initializing tabs
	 */
	private void initDBAndTabs()
		throws SQLException
	{
		DBConnector connector = DBConnector.getInstance();
		connector.openConnection(GenesisConfig.getInstance().getDBFile(),false);
		
		//fail save
		if( connector.getConnection() == null )
			return;
		
		ApplicationLogger.separator();
		ApplicationLogger.logInfo(
				"DB Version: "
					+ TableHelper.getDBVersion() + " "
					+ TableHelper.getDBLanguage()
			);
		
		
		coreTables = new Vector<CoreEditorTable>();
		internalTables = new Vector<CoreEditorTable>();
		
		CoreEditorTable table;
		
		// init internal Tables
		{
			table = new CoreEditorTable(new CoreDataVersion());
			internalTables.add(table);
			
			tabbedPaneInternal.addTab("CoreDataVersion", new JScrollPane(table)); 
			tabbedPaneInternal.setIconAt(
					0, 
					(new ImageResource("images/icons/dbTableSystem.gif",this)).getImageIcon()
				);
		}
		{
			table = new CoreEditorTable(new CoreDataTableIndex());
			internalTables.add(table);
			
			tabbedPaneInternal.addTab("CoreDataTableIndex",	new JScrollPane(table)); 
			tabbedPaneInternal.setIconAt(
					1, 
					(new ImageResource("images/icons/dbTableSystem.gif",this)).getImageIcon()
				);
		}
		{
			table = new CoreEditorTable(new TableColumnLabels());
			internalTables.add(table);
			
			tabbedPaneInternal.addTab("TableColumnLabels",	new JScrollPane(table)); 
			tabbedPaneInternal.setIconAt(
					2, 
					(new ImageResource("images/icons/dbTableSystem.gif",this)).getImageIcon()
				);
		}
		
		
		
		// TODO refresh button in tab
		// TODO Commit Button per row becomes enabled after the first change
		
		// TODO coredata tables
		
		this.lblStatus.setText(
				labelResource.getProperty("status.ready", "status.ready")
			);
	}
		
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
	 * so it returns always false.
	 */
	@Override
	public boolean hasContentChanged() 
	{
		//TODO if uncommited changes are there.
		return false;
	}

	@Override
	public void contentSaved()
	{
		// TODO
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
	
	@Override
	public void close( WindowEvent e )
	{
		DBConnector.getInstance().closeConnection();
		super.close(e);
	}
	
	@Override
	public void dispose()
	{
		DBConnector.getInstance().closeConnection();
		super.dispose();
	}

	/**
	 * called if a tab has changed
	 * 
	 * @param e
	 */
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		if( e.getSource() == tabbedPaneInternal )
		{
			int idx = tabbedPaneInternal.getSelectedIndex();
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			internalTables.elementAt(idx).loadData();
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
		} else if( e.getSource() == tabbedPaneCore ) {
			int idx = tabbedPaneCore.getSelectedIndex();
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			coreTables.elementAt(idx).loadData();
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		} 
	}

	/**
	 * for menu and toolbar handling
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("actionPerformed");
		// TODO Auto-generated method stub
		
	}

	/** 
	 * table data changes 
	 * 
	 * @param e
	 */
	@Override
	public void tableChanged(TableModelEvent e)
	{
		System.out.println("tableChanged");
		// TODO Auto-generated method stub
		
	}

}
