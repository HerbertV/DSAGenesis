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
package dsagenesis.editor.coredata.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import dsagenesis.core.ui.AbstractGenesisDialog;
import dsagenesis.editor.coredata.CoreEditorFrame;

/**
 * AbstractCellDialog
 * 
 * Abstract base for Dialogs called by AbstractDialogCellEditor
 * All Cell Dialogs are modal so the fireEditingStopped is called
 * when the dialog is closed.
 * 
 * comes with Ok and Cancel buttons and the basic handling.
 */
public abstract class AbstractCellDialog 
		extends AbstractGenesisDialog 
		implements ActionListener
{

	// ============================================================================
	//  Constants
	// ============================================================================

	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================

	protected boolean isChangeOk = false;
	   
	protected JButton btnOK;
	protected JButton btnCancel;
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================

	/**
	 * Constructor
	 * 
	 * @param f
	 * @param width
	 * @param height
	 */
	public AbstractCellDialog(
			CoreEditorFrame f,
			int width, 
			int height
		) 
	{
		super(f);
		
		this.setSize(width, height);
		this.setResizable(false);
		this.setModal(true);
		
		this.getContentPane().setLayout(new BorderLayout());
		
		// setup the buttons
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());
		this.getContentPane().add(btnPanel, BorderLayout.SOUTH);

		btnOK = new JButton();
		btnOK.setText(labelResource.getProperty("btnOk", "btnOk"));
		btnOK.addActionListener(this);
		btnPanel.add(btnOK);

		btnCancel = new JButton();
		btnCancel.setText(labelResource.getProperty("btnCancel", "btnCancel"));
		btnCancel.addActionListener(this);
		btnPanel.add(btnCancel);

		// window listener
		this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					isChangeOk = false;
					setVisible(false);
					CoreEditorFrame f = (CoreEditorFrame)AbstractCellDialog.this.getOwner();
					f.setFocusOnActiveTab();
				}
			});
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if( e.getSource() == btnOK )
		{
			isChangeOk = true;
		} else {
			isChangeOk = false;
		}
		setVisible(false);
		CoreEditorFrame f = (CoreEditorFrame)this.getOwner();
		f.setFocusOnActiveTab();
	}
	
	/**
	 * isChangeOk
	 * 
	 * true if the OK button was hit
	 * 
	 * @return
	 */
	public boolean isChangeOk()
	{
		return isChangeOk;
	}
	
	/**
	 * getValue
	 * 
	 * must return a value that fits into the cell
	 * 
	 * @return
	 */
	public abstract Object getValue();
	
	/**
	 * setValue
	 * 
	 * @param value
	 */
	public abstract void setValue(Object value);
	
}
