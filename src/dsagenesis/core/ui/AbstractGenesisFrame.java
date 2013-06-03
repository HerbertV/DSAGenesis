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
package dsagenesis.core.ui;

import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import dsagenesis.core.GenesisLauncher;
import dsagenesis.core.config.GenesisConfig;

import jhv.component.IChangeableContentComponent;
import jhv.component.IConfigurableComponent;
import jhv.component.ILabeledComponent;
import jhv.component.LabelResource;

/**
 * Abstract base for all Genesis JFrames
 */
public abstract class AbstractGenesisFrame 
		extends JFrame 
		implements IConfigurableComponent, 
			ILabeledComponent, 
			IChangeableContentComponent
{
	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	private static final long serialVersionUID = 1L;
	
	/**
	 * config key prefix for the system.config file.
	 */
	protected String configKey;
	
	protected LabelResource labelResource;
	

	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor 1.
	 * 
	 * @param configkey
	 */
	public AbstractGenesisFrame(
			String configkey
		) 
	{
		super();
		
		this.loadLabels();
		this.setIconImage(GenesisConfig.APP_ICON);
		this.configKey = configkey;
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close(e);
            }
        });
		
		this.applyConfig();
	}
	
	/**
	 * Constructor 2.
	 * 
	 * @param title
	 * @param configkey
	 */
	public AbstractGenesisFrame(
			String title, 
			String configkey
		) 
	{
		super();
		
		this.loadLabels();
		this.setTitle(title);
		this.setIconImage(GenesisConfig.APP_ICON);
		this.configKey = configkey;
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close(e);
            }
        });
		
		this.applyConfig();
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * basic close handler.
	 * can be overridden. 
	 * 
	 * returns true if the frame was really closed.
	 * 
	 * @param e
	 * @return
	 */
	public boolean close( WindowEvent e )
	{
		this.saveConfig();
		
		boolean doClose = false;
		if( !this.hasContentChanged() )
		{
			doClose = true;
		} else {
			int result = PopupDialogFactory.confirmCloseWithUnsavedData(this);
			
			if( result == JOptionPane.YES_OPTION )
			{
				doClose = true;
			}
		}
		
		if( doClose )
		{
			GenesisLauncher.openFrame = null;
			GenesisLauncher.bringToFront();
			
			this.setVisible(false);
			this.dispose();
		} 
		return doClose;
	}
	
	/**
	 * 
	 */
	@Override
	public void applyConfig() 
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		int[] arr = conf.getIntArray(
				this.configKey + "."+ GenesisConfig.KEY_SIZE 
			);
		this.setSize(arr[0], arr[1]);
		
		arr = conf.getIntArray(
				this.configKey + "."+ GenesisConfig.KEY_POSITION 
			);
		
		// check if window position is inside the visible area
		Rectangle[] rect = GenesisConfig.getDisplayResolutions();
		this.setLocation(0, 0);
		for( int i=0; i < rect.length; i++ )
		{
			if( rect[i].contains(arr[0], arr[1] ) )
			{
				this.setLocation(arr[0], arr[1]);
				break;
			}
		}
		
		boolean fullScreen = conf.getBoolean(
				this.configKey + "."+ GenesisConfig.KEY_ISFULLSCREEN 
			);
		if( fullScreen )
			this.setExtendedState(MAXIMIZED_BOTH);
	}

	/**
	 * 
	 */
	@Override
	public void saveConfig() 
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		
		conf.setUserProperty(
				this.configKey + "."+ GenesisConfig.KEY_SIZE, 
				this.getSize().width + "," + this.getSize().height
			);
		
		conf.setUserProperty(
				this.configKey + "."+ GenesisConfig.KEY_POSITION, 
				this.getLocation().x + "," + this.getLocation().y
			);
		
		if( this.getExtendedState() == MAXIMIZED_BOTH )
		{
			conf.setUserProperty(
					this.configKey + "."+ GenesisConfig.KEY_ISFULLSCREEN, 
					"true"
				);
		} else {
			conf.setUserProperty(
					this.configKey + "."+ GenesisConfig.KEY_ISFULLSCREEN, 
					"false"
				);
		}
		conf.saveUser();
	}
	
	/**
	 * markUnsaved
	 * 
	 * little helper for adding a * to unsaved titles.
	 * 
	 * @param text
	 * @param unsaved
	 * 
	 * @return
	 */
	public static String markUnsaved(String text, boolean unsaved)
	{
		if( unsaved )
		{
			if( text.indexOf("*") == -1 )
				text += "*";
		} else {
			int i = text.indexOf("*"); 
			if( i > -1 )
				text = text.substring(0, i);
		}
		return text;
	}
	
	@Override
	public void loadLabels()
	{
		GenesisConfig conf = GenesisConfig.getInstance();
		
		labelResource = new LabelResource(
				this,
				conf.getLanguage(), 
				"resources/labels"
			);
	}
	
}
