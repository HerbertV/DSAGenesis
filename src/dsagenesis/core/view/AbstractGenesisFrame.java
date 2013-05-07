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
package dsagenesis.core.view;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import dsagenesis.core.GenesisLauncher;
import dsagenesis.core.config.GenesisConfig;

import jhv.IConfigurableComponent;

/**
 * Abstract base for all Genesis JFrames
 */
public abstract class AbstractGenesisFrame 
		extends JFrame 
		implements IConfigurableComponent
		
{
	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	private static final long serialVersionUID = 1L;
	
	/**
	 * config key prefix for the system.config file.
	 */
	protected String configKey;
	

	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor.
	 * 
	 * @param title
	 * @param icon
	 * @param configkey
	 */
	public AbstractGenesisFrame(
			String title, 
			Image icon, 
			String configkey
		) 
	{
		super();
		
		this.setTitle(title);
		this.setIconImage(icon);
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
	 * @param e
	 */
	public void close( WindowEvent e )
	{
		this.saveConfig();
		
		GenesisLauncher.openFrame = null;
		
		this.setVisible(false);
		this.dispose();
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
		
		conf.setSystemProperty(
				this.configKey + "."+ GenesisConfig.KEY_SIZE, 
				this.getSize().width + "," + this.getSize().height
			);
		
		conf.setSystemProperty(
				this.configKey + "."+ GenesisConfig.KEY_POSITION, 
				this.getLocation().x + "," + this.getLocation().y
			);
		
		if( this.getExtendedState() == MAXIMIZED_BOTH )
		{
			conf.setSystemProperty(
					this.configKey + "."+ GenesisConfig.KEY_ISFULLSCREEN, 
					"true"
				);
		} else {
			conf.setSystemProperty(
					this.configKey + "."+ GenesisConfig.KEY_ISFULLSCREEN, 
					"false"
				);
		}
		conf.saveSystem();
	}
	
	/**
	 * isSaved
	 * returns true if all contents are saved.
	 * 
	 * @return
	 */
	abstract public boolean isSaved();
	
}
