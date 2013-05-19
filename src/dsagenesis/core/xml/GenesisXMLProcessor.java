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
package dsagenesis.core.xml;

import jhv.xml.XMLProcessor;

/**
 * GenesisXMLProcessor.
 * 
 * @see jhv.xml.XMLProcessor
 */
public class GenesisXMLProcessor
		extends XMLProcessor
{

	// ============================================================================
	//  Constructors
	// ============================================================================
	
	/**
	 * Constructor
	 */
	protected GenesisXMLProcessor() 
	{
		super();
	
		XMLProcessor.XML_DOC_VERSION = "1.0";
		XMLProcessor.XML_ROOT_NODE = "DSAGenesis";
	}
	
}
