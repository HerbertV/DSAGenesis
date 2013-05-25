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
import dsagenesis.core.ui.PopupDialogFactory;
import dsagenesis.editor.coredata.table.CoreEditorTable;
import dsagenesis.editor.coredata.table.CoreEditorTableModel;
import dsagenesis.editor.coredata.table.cell.CommitButtonCell;

import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
	
	/**
	 * for setCommitStatus
	 */
	public static final int STATUS_COMMIT_SUCCESS = 0;
	public static final int STATUS_COMMIT_ERROR = 1;
	
	/**
	 * action commands for menus.
	 */
	public static final String ACMD_COPY = "copy";
	public static final String ACMD_PASTE = "paste";
	public static final String ACMD_ADDROW = "addRow";
	public static final String ACMD_DELETEROW = "deleteRow";
	
	public static final String ACMD_NEW = "new";
	public static final String ACMD_BACKUP = "backup";
	public static final String ACMD_IMPORT = "import";
	public static final String ACMD_EXPORT = "export";
	
	public static final String ACMD_INFO = "info";
	public static final String ACMD_HELP = "help";
	
	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	/**
	 * for status messages
	 */
	private JLabel lblStatus;
	
	/**
	 * TitleBorder for the not
	 */
	private TitledBorder titleBorder;
	
	/**
	 * for displaying Notes on a tab
	 */
	private JLabel lblNote;
	
	/**
	 * tab panel for the tables
	 */
	private JTabbedPane tabbedPane;
	
	/**
	 * Vector for accessing the tables
	 */
	private Vector<CoreEditorTable> vecTables;

	    
	
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
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		getContentPane().add(panel, BorderLayout.CENTER);
		{
			this.titleBorder = BorderFactory.createTitledBorder("");
			this.lblNote = new JLabel("");
			this.lblNote.setBorder(titleBorder);
			panel.add(this.lblNote,BorderLayout.SOUTH);
		}
		{
			this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			panel.add(this.tabbedPane, BorderLayout.CENTER);
		}
		
		this.lblStatus = new JLabel(
				labelResource.getProperty("status.init", "status.init")
			);
		this.lblStatus.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		getContentPane().add(this.lblStatus, BorderLayout.SOUTH);
		
		try 
		{
			initDBAndTabs();
			
			int tabIdx = GenesisConfig.getInstance().getInt(
					GenesisConfig.KEY_WIN_CORE_ACTIVE_TAB
				);
			tabbedPane.setSelectedIndex(tabIdx);
			
			this.stateChanged(null);
			tabbedPane.addChangeListener(this);
			
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
		
		
		vecTables = new Vector<CoreEditorTable>();
		
		CoreEditorTable table;
		
		// init system Tables
		{
			table = new CoreEditorTable(this, new CoreDataVersion());
			vecTables.add(table);
			
			tabbedPane.addTab("CoreDataVersion", new JScrollPane(table)); 
			tabbedPane.setIconAt(
					0, 
					(new ImageResource("images/icons/dbTableSystem.gif",this)).getImageIcon()
				);
		}
		{
			table = new CoreEditorTable(this, new CoreDataTableIndex());
			vecTables.add(table);
			tabbedPane.addTab("CoreDataTableIndex", new JScrollPane(table)); 
			tabbedPane.setIconAt(
					1, 
					(new ImageResource("images/icons/dbTableSystem.gif",this)).getImageIcon()
				);
		}
		{
			table = new CoreEditorTable(this, new TableColumnLabels());
			vecTables.add(table);
			
			tabbedPane.addTab("TableColumnLabels",	new JScrollPane(table)); 
			tabbedPane.setIconAt(
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
			
			KeyStroke ksCopy = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);
	        KeyStroke ksPaste = KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK,false);
	        KeyStroke ksAddRow = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,ActionEvent.CTRL_MASK,false);
	        KeyStroke ksDeleteRow = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,ActionEvent.CTRL_MASK,false);
	        table.registerKeyboardAction(this,ACMD_COPY,ksCopy,JComponent.WHEN_FOCUSED);
	        table.registerKeyboardAction(this,ACMD_PASTE,ksPaste,JComponent.WHEN_FOCUSED);
	        table.registerKeyboardAction(this,ACMD_ADDROW,ksAddRow,JComponent.WHEN_FOCUSED);
	        table.registerKeyboardAction(this,ACMD_DELETEROW,ksDeleteRow,JComponent.WHEN_FOCUSED);
            
			tabbedPane.addTab(
					rs.getString("ti_label"),
					new JScrollPane(table)
				); 
			
			vecTables.add(table);
	
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
			btnCopy.setActionCommand(ACMD_COPY);
			btnCopy.addActionListener(this);
			toolBar.add(btnCopy);
			
			JButton btnPaste = new JButton("");
			btnPaste.setToolTipText(labelResource.getProperty("paste", "paste"));
			btnPaste.setIcon(irPaste.getImageIcon());
			btnPaste.setActionCommand(ACMD_PASTE);
			btnPaste.addActionListener(this);
			toolBar.add(btnPaste);
			
			JSeparator separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setSize(10, 16);
			separator.setMaximumSize(separator.getSize());
			toolBar.add(separator);
			
			JButton btnAddRow = new JButton("");
			btnAddRow.setToolTipText(labelResource.getProperty("addRow", "addRow"));
			btnAddRow.setIcon(irAddRow.getImageIcon());
			btnAddRow.setActionCommand(ACMD_ADDROW);
			btnAddRow.addActionListener(this);
			toolBar.add(btnAddRow);
			
			JButton btnDeleteRow = new JButton("");
			btnDeleteRow.setToolTipText(labelResource.getProperty("deleteRow", "deleteRow"));
			btnDeleteRow.setIcon(irDeleteRow.getImageIcon());
			btnDeleteRow.setActionCommand(ACMD_DELETEROW);
			btnDeleteRow.addActionListener(this);
			toolBar.add(btnDeleteRow);
		}
		
		
		// menubar
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			
			JMenu mnFile = new JMenu(labelResource.getProperty("mnFile", "mnFile"));
			menuBar.add(mnFile);
			
			JMenuItem mntmNew = new JMenuItem(labelResource.getProperty("mntmNew", "mntmNew"));
			mntmNew.setActionCommand(ACMD_NEW);
			mntmNew.addActionListener(this);
			mnFile.add(mntmNew);
			
			JSeparator separator = new JSeparator();
			mnFile.add(separator);
			
			JMenuItem mntmBackup = new JMenuItem(labelResource.getProperty("mntmBackup", "mntmBackup"));
			mntmBackup.setIcon(irBackup.getImageIcon());
			mntmBackup.setActionCommand(ACMD_BACKUP);
			mntmBackup.addActionListener(this);
			mnFile.add(mntmBackup);
			
			JSeparator separator2 = new JSeparator();
			mnFile.add(separator2);
			
			JMenuItem mntmImport = new JMenuItem(labelResource.getProperty("mntmImport", "mntmImport"));
			mntmImport.setIcon(irImport.getImageIcon());
			mntmImport.setActionCommand(ACMD_IMPORT);
			mntmImport.addActionListener(this);
			mnFile.add(mntmImport);
			
			JMenuItem mntmExport = new JMenuItem(labelResource.getProperty("mntmExport", "mntmExport"));
			mntmExport.setIcon(irExport.getImageIcon());
			mntmExport.setActionCommand(ACMD_EXPORT);
			mntmExport.addActionListener(this);
			mnFile.add(mntmExport);
			
			JMenu mnEdit = new JMenu(labelResource.getProperty("mnEdit", "mnEdit"));
			menuBar.add(mnEdit);
			
			JMenuItem mntmCopy = new JMenuItem(labelResource.getProperty("copy", "copy"));
			mntmCopy.setIcon(irCopy.getImageIcon());
			mntmCopy.setActionCommand(ACMD_COPY);
			mntmCopy.addActionListener(this);
			mnEdit.add(mntmCopy);
			
			JMenuItem mntmPaste = new JMenuItem(labelResource.getProperty("paste", "paste"));
			mntmPaste.setIcon(irPaste.getImageIcon());
			mntmPaste.setActionCommand(ACMD_PASTE);
			mntmPaste.addActionListener(this);
			mnEdit.add(mntmPaste);
			
			JSeparator separator_1 = new JSeparator();
			mnEdit.add(separator_1);
			
			JMenuItem mntmAddRow = new JMenuItem(labelResource.getProperty("addRow", "addRow"));
			mntmAddRow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK));
			mntmAddRow.setIcon(irAddRow.getImageIcon());
			mntmAddRow.setActionCommand(ACMD_ADDROW);
			mntmAddRow.addActionListener(this);
			mnEdit.add(mntmAddRow);
			
			JMenuItem mntmDeleteRow = new JMenuItem(labelResource.getProperty("deleteRow", "deleteRow"));
			mntmDeleteRow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_MASK));
			mntmDeleteRow.setIcon(irDeleteRow.getImageIcon());
			mntmDeleteRow.setActionCommand(ACMD_DELETEROW);
			mntmDeleteRow.addActionListener(this);
			mnEdit.add(mntmDeleteRow);
			
			JMenu mnHelp = new JMenu(labelResource.getProperty("mnHelp", "mnHelp"));
			menuBar.add(mnHelp);
			
			JMenuItem mntmInfo = new JMenuItem(labelResource.getProperty("mntmInfo", "mntmInfo"));
			mntmInfo.setActionCommand(ACMD_INFO);
			mntmInfo.addActionListener(this);
			mntmInfo.setIcon(irInfo.getImageIcon());
			mnHelp.add(mntmInfo);
			
			JMenuItem mntmHelp = new JMenuItem(labelResource.getProperty("mntmHelp", "mntmHelp"));
			mntmHelp.setIcon(irHelp.getImageIcon());
			mntmHelp.setActionCommand(ACMD_HELP);
			mntmHelp.addActionListener(this);
			mntmHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			mnHelp.add(mntmHelp);
		}
		
	}
	
	@Override
	public void saveConfig() 
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		
		conf.setUserProperty(
				GenesisConfig.KEY_WIN_CORE_ACTIVE_TAB, 
				Integer.toString(tabbedPane.getSelectedIndex())
			);
		
		super.saveConfig();
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
		int idx = vecTables.indexOf(table);
		String title = tabbedPane.getTitleAt(idx);
		title = CoreEditorFrame.markUnsaved(title, unsaved);
		tabbedPane.setTitleAt(idx, title);
	}
	
	
	@Override
	public boolean hasContentChanged() 
	{
		for( int i=0; i< vecTables.size(); i++ )
			if( vecTables.elementAt(i).containsUncommitedData() )
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
		
		CoreEditorTable table = vecTables.elementAt(
				tabbedPane.getSelectedIndex()
			);
		
		table.loadData();
		titleBorder.setTitle(table.getLabel());
		lblNote.setText(
				"<html>"
					+ table.getNote()
					+ "</html>"
			);
		
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * actionCopy
	 */
	private void actionCopy()
	{
		int idx = this.tabbedPane.getSelectedIndex();
		CoreEditorTable table = vecTables.elementAt(idx);

		if( table.isReadOnly() )
		{
			lblStatus.setText(
					labelResource.getProperty(
							"status.readonly.error",
							"status.readonly.error"
						)
				);
			return;
		}
		ClipboardCellTransfer.getInstance().setClipboardContents(table);
	}
	
	/**
	 * actionPaste
	 */
	private void actionPaste()
	{
		int idx = this.tabbedPane.getSelectedIndex();
		CoreEditorTable table = vecTables.elementAt(idx);

		if( table.isReadOnly() )
		{
			lblStatus.setText(
					labelResource.getProperty("status.readonly.error","status.readonly.error")
				);
			return;
		}
		
		ClipboardCellTransfer.getInstance().getClipboardContents(table);
	}
	
	/**
	 * actionAddRow
	 */
	private void actionAddRow()
	{
		int idx = this.tabbedPane.getSelectedIndex();
	    CoreEditorTable table = vecTables.elementAt(idx);
	   
	    if( table.isReadOnly() )
	    {
	    	lblStatus.setText(
	    			labelResource.getProperty("status.readonly.error","status.readonly.error")
	    		);
	    	return;
	    }
	    
	    table.addEmptyRow();
	    
	    // now scroll to new entry
        int height = table.getHeight();
        table.scrollRectToVisible(new Rectangle(0, height - 1,1, height));
        
        markUnsavedTabTitle(table,true);
		
		// set frame title
		String title = CoreEditorFrame.markUnsaved(this.getTitle(), true);
		this.setTitle(title);
	}
	
	/**
	 * actionDeleteRow
	 */
	private void actionDeleteRow()
	{
		int idx = this.tabbedPane.getSelectedIndex();
	    CoreEditorTable table = vecTables.elementAt(idx);
	    
	    if( table.isReadOnly() )
	    {
	    	lblStatus.setText(
	    			labelResource.getProperty("status.readonly.error","status.readonly.error")
	    		);
	    	return;
	    }
	    
	    int rowidx = table.getSelectedRow();  
	    
	    if( rowidx == -1 )
	    	return;
	    
	    // confirm
	    int result = PopupDialogFactory.confirmDeleteDatabaseRow(this);
	   
	    // delete
	    if( result == JOptionPane.YES_OPTION )
	    	table.removeRow(rowidx);
	    
	    // update marks
	    if( !table.containsUncommitedData() )
			markUnsavedTabTitle(table,false);
		
		if( !hasContentChanged() )
		{
			String title = CoreEditorFrame.markUnsaved(this.getTitle(), false);
			this.setTitle(title);
		}
	}
	
	/**
	 * actionNew
	 */
	private void actionNew()
	{
		// TODO
	}
	
	/**
	 * actionBackup
	 */
	private void actionBackup()
	{
		// TODO
	}
	
	/**
	 * actionImport
	 */
	private void actionImport()
	{
		// TODO
	}
	
	/**
	 * actionExport
	 */
	private void actionExport()
	{
		// TODO
	}
	
	/**
	 * actionPerformed
	 * 
	 * for menu and toolbar handling
	 * @param ae
	 */
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		if( ae.getActionCommand().equals(ACMD_COPY) )
		{
			this.actionCopy();
		} else if( ae.getActionCommand().equals(ACMD_PASTE) ) {
			this.actionPaste();
			
		} else if( ae.getActionCommand().equals(ACMD_ADDROW) ) {
			this.actionAddRow();
			
		} else if( ae.getActionCommand().equals(ACMD_DELETEROW) ) {
			this.actionDeleteRow();
			
		} else if( ae.getActionCommand().equals(ACMD_NEW) ) {
			this.actionNew();
			
		} else if( ae.getActionCommand().equals(ACMD_BACKUP) ) {
			this.actionBackup();
			
		} else if( ae.getActionCommand().equals(ACMD_IMPORT) ) {
			this.actionImport();
			
		} else if( ae.getActionCommand().equals(ACMD_EXPORT) ) {
			this.actionExport();
			
		} else if( ae.getActionCommand().equals(ACMD_INFO) ) {
			InfoDialog d = new InfoDialog(CoreEditorFrame.this);
			d.setVisible(true);
			
		} else if( ae.getActionCommand().equals(ACMD_HELP) ) {
			File page = new File("./help/coreDataEditor.html");
			try {
				HelpDialog d = HelpDialog.getInstance();
				d.openURL(page.toURI().toURL().toExternalForm());
				d.setVisible(true);
			} catch (MalformedURLException e) {
				// nothing to do
			}
		}
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
