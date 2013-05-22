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
import dsagenesis.core.model.sql.AbstractSQLTableModel;
import dsagenesis.core.model.sql.CoreDataTableIndex;
import dsagenesis.core.model.sql.CoreDataVersion;
import dsagenesis.core.model.sql.TableColumnLabels;
import dsagenesis.core.sqlite.DBConnector;
import dsagenesis.core.sqlite.TableHelper;
import dsagenesis.core.ui.AbstractGenesisFrame;
import dsagenesis.core.ui.HelpDialog;
import dsagenesis.core.ui.InfoDialog;
import dsagenesis.editor.coredata.table.CoreEditorTable;
import dsagenesis.editor.coredata.table.CoreEditorTableModel;
import dsagenesis.editor.coredata.table.cell.CommitButtonCell;

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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
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

	public static final int STATUS_COMMIT_SUCCESS = 0;
	public static final int STATUS_COMMIT_ERROR = 1;
	
	
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
					labelResource.getProperty("topNote.title", "topNote.title")
				);
			panelSplitTop.setLayout(new BorderLayout(0, 0));
			this.lblTopNote = new JLabel(
					labelResource.getProperty("topNote.body", "topNote.body")
				);
			this.lblTopNote.setBorder(titleBorder);
			panelSplitTop.add(this.lblTopNote,BorderLayout.SOUTH);
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
			TitledBorder titleBorder = BorderFactory.createTitledBorder(
					labelResource.getProperty("bottomNote.title", "bottomNote.title")
				);
			this.lblBottomNote = new JLabel(
					labelResource.getProperty("bottomNote.body", "bottomNote.body")
				);
			this.lblBottomNote.setBorder(titleBorder);
			panelSplitBottom.add(this.lblBottomNote,BorderLayout.SOUTH);
		}
		{
			this.tabbedPaneInternal = new JTabbedPane(JTabbedPane.TOP);
			panelSplitBottom.add(this.tabbedPaneInternal, BorderLayout.CENTER);
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
		
		try 
		{
			initDBAndTabs();
			
			internalTables.elementAt(tabbedPaneInternal.getSelectedIndex()).loadData();
			coreTables.elementAt(tabbedPaneCore.getSelectedIndex()).loadData();
			
			this.tabbedPaneInternal.addChangeListener(this);
			this.tabbedPaneCore.addChangeListener(this);
			
			
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
		
		// init system Tables
		{
			table = new CoreEditorTable(this, new CoreDataVersion());
			internalTables.add(table);
			
			tabbedPaneInternal.addTab("CoreDataVersion", new JScrollPane(table)); 
			tabbedPaneInternal.setIconAt(
					0, 
					(new ImageResource("images/icons/dbTableSystem.gif",this)).getImageIcon()
				);
		}
		{
			table = new CoreEditorTable(this, new CoreDataTableIndex());
			internalTables.add(table);
			tabbedPaneInternal.addTab("CoreDataTableIndex", new JScrollPane(table)); 
			tabbedPaneInternal.setIconAt(
					1, 
					(new ImageResource("images/icons/dbTableSystem.gif",this)).getImageIcon()
				);
		}
		{
			table = new CoreEditorTable(this, new TableColumnLabels());
			internalTables.add(table);
			
			tabbedPaneInternal.addTab("TableColumnLabels",	new JScrollPane(table)); 
			tabbedPaneInternal.setIconAt(
					2, 
					(new ImageResource("images/icons/dbTableSystem.gif",this)).getImageIcon()
				);
		}
		
		// create the rest.
		String query = "SELECT * FROM CoreDataTableIndex ORDER BY ti_tab_index ASC";
		ResultSet rs = DBConnector.getInstance().executeQuery(query);
		
		while( rs.next() )
			this.initDynamicTab(rs);
		
		// TODO refresh button in tab
		
		this.lblStatus.setText(
				labelResource.getProperty("status.ready", "status.ready")
			);
	}
	
	/**
	 * initDynamicTab
	 * 
	 * creates a tab with its table by class reflection.
	 * 
	 * @param rs
	 */
	private void initDynamicTab(ResultSet rs)
	{
		try 
		{
			Class<?> c = Class.forName(
					"dsagenesis.core.model.sql."
						+ rs.getString("ti_table_name")
				);
			
			Class<?> parameterTypes[] = new Class[1];
			parameterTypes[0] = ResultSet.class;
			
            Constructor<?> con = c.getConstructor(parameterTypes);
            
            Object args[] = new Object[1];
			args[0] = rs;
			
			AbstractSQLTableModel model = (AbstractSQLTableModel)con.newInstance(args);
			CoreEditorTable table = new CoreEditorTable(this, model);
			
			if( DBConnector.convertBooleanFromDB(rs.getObject("ti_is_internal")) )
			{
				tabbedPaneInternal.addTab(
						rs.getString("ti_label"),
						new JScrollPane(table)
					); 
				internalTables.add(table);
			} else {
				tabbedPaneCore.addTab(
						rs.getString("ti_label"),
						new JScrollPane(table)
					); 
				coreTables.add(table);
			}
			
		} catch ( SQLException 
				| InstantiationException 
				| IllegalAccessException 
				| NoSuchMethodException 
				| SecurityException
				| IllegalArgumentException
				| InvocationTargetException e 
			)
		{
			ApplicationLogger.logError(e);
		} catch ( ClassNotFoundException e2 ) {
			ApplicationLogger.logError(" ClassNotFoundException:"+e2.getMessage());
		}
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
	 * setStatus
	 * 
	 * callback for the CoreEditorTable commitRow
	 * 
	 * @param status STATUS_COMMIT_SUCCESS or STATUS_COMMIT_ERROR
	 * @param table
	 * @param row
	 */
	public void setCommitStatus( 
			int status, 
			CoreEditorTable table, 
			int row 
		)
	{
		if( status == STATUS_COMMIT_SUCCESS )
		{
			this.lblStatus.setText(
					labelResource.getProperty("status.commit.success", "status.commit.success")
				);
	
			if( !table.containsUncommitedData() )
				markUnsavedTabTitle(table,false);
			
			if( !hasContentChanged() )
			{
				String title = CoreEditorFrame.markUnsaved(this.getTitle(), false);
				this.setTitle(title);
			}
						
		} else {
			this.lblStatus.setText(
					labelResource.getProperty("status.commit.error", "status.commit.error")
				);
		}
	}
	
	/**
	 * markUnsavedTabTitle
	 * 
	 * @param table
	 * @param unsaved
	 */
	private void markUnsavedTabTitle(
			CoreEditorTable table, 
			boolean unsaved
		)
	{
		JTabbedPane tab = null;
		int idx = coreTables.indexOf(table);
		if( idx > -1 )
		{
			tab = tabbedPaneCore;
		} else {
			idx = internalTables.indexOf(table);
			if( idx > -1 ) 
				tab = tabbedPaneInternal;
		}

		if( tab == null )
			return;
		
		String title = tab.getTitleAt(idx);
		title = CoreEditorFrame.markUnsaved(title, unsaved);
		tab.setTitleAt(idx, title);
	}
	
	
	@Override
	public boolean hasContentChanged() 
	{
		for( int i=0; i< coreTables.size(); i++ )
			if( coreTables.elementAt(i).containsUncommitedData() )
				return true;
	
		for( int i=0; i< coreTables.size(); i++ )
			if( internalTables.elementAt(i).containsUncommitedData() )
				return true;
	
		return false;
	}

	@Override
	public void contentSaved()
	{
		// not used since we have the commit buttons.
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
	public boolean close( WindowEvent e )
	{
		boolean doClose = super.close(e);
		if( doClose )
			DBConnector.getInstance().closeConnection();
		
		return doClose;
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
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		CoreEditorTable table = null;
		JLabel lbl = null;
		if( e.getSource() == tabbedPaneInternal )
		{
			table = internalTables.elementAt(
					tabbedPaneInternal.getSelectedIndex()
				);
			lbl = lblBottomNote;
			
		} else if( e.getSource() == tabbedPaneCore ) {
			table = coreTables.elementAt(
					tabbedPaneCore.getSelectedIndex()
				);
			lbl = lblTopNote;
			
		}
		
		table.loadData();
		lbl.setText(
				"<html>"
					+ "<b>" + table.getLabel() + "</b>"
					+ "<br>"
					+ table.getNote()
					+ "</html>"
			);
		
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
		// now we check if the commit button needs to be enabled
		int row = e.getFirstRow();
		int column = e.getColumn();
        
		// fail saves
		// that the button is not enabled unnecessarily
		if( row < 0 || column < 0 )
			return;
		
		if( e.getType() != TableModelEvent.UPDATE )
			return;
	
		CoreEditorTableModel model = ((CoreEditorTableModel)e.getSource());
		CoreEditorTable table = model.getTable();
		
		if( table.getCellRenderer(row, column) != null
        		&& table.getCellRenderer(row, column) instanceof CommitButtonCell )
        	return;
        
        if( table.getCellEditor() != null
        		&&  table.getCellEditor() instanceof CommitButtonCell )
        	return;
		
        markUnsavedTabTitle(table,true);
		
		// set frame title
		String title = CoreEditorFrame.markUnsaved(this.getTitle(), true);
		this.setTitle(title);
	}
	
}
