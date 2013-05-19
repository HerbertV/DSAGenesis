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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import dsagenesis.core.model.xml.AbstractGenesisModel;

/**
 * AbstractXMLDocument.
 * 
 * Abstract base for XML documents.
 */
public abstract class AbstractXMLDocument 
{
	// ============================================================================
	//  Variables
	// ============================================================================
	
	protected String filename;
	
	protected Document document;
	
	protected Element rootElement;
	
	protected String fileVersion;
	
	protected AbstractGenesisModel model;
	
	// ============================================================================
	//  Constructors 
	// ============================================================================
	
	/**
	 * Constructor
	 */
	public AbstractXMLDocument() 
	{
	
	}

	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * checkFileVersion
	 * 
	 * checks if the file has the correct version.
	 * 
	 * @return
	 */
	public boolean checkFileVersion()
	{
		// TODO 
		return true;
	}
	
	/**
	 * getFilename
	 * 
	 * @return
	 */
	public String getFilename() 
	{
		return filename;
	}
	
	/**
	 * setFilename
	 * 
	 * @param filename
	 */
	public void setFilename(String filename) 
	{
		this.filename = filename;
	}

	/**
	 * getDocument
	 * 
	 * @return
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * setDocument
	 * 
	 * @param document
	 */
	public void setDocument(Document document) 
	{
		this.document = document;
	}

	/**
	 * getRootElement
	 * 
	 * @return
	 */
	public Element getRootElement() 
	{
		return rootElement;
	}

	/**
	 * setRootElement
	 * 
	 * @param rootElement
	 */
	public void setRootElement(Element rootElement) 
	{
		this.rootElement = rootElement;
	}

	/**
	 * getFileVersion
	 * 
	 * @return
	 */
	public String getFileVersion() 
	{
		return fileVersion;
	}

	/**
	 * createModel
	 * 
	 * creates the model structure
	 * 
	 * @return
	 */
	public abstract void createModel();
	
	/**
	 * getModel
	 * 
	 * @return
	 */
	public AbstractGenesisModel getModel()
	{
		return model;
	}
	
	/**
	 * newDocument
	 * 
	 * creates an empty new document
	 */
	public abstract void newDocument();
	
	/**
	 * loadDocument
	 * 
	 * loads the document
	 * 
	 * @param filename
	 */
	public void loadDocument(String filename)
	{
		XMLProcessor p = GenesisXMLProcessor.getInstance();
		this.document = p.loadXML(filename);
	}
	
	/**
	 * saveDocument
	 * 
	 * saves the document
	 * 
	 * @param filename
	 */
	public void saveDocument(String filename)
	{
		XMLProcessor p = GenesisXMLProcessor.getInstance();
		p.saveXML(this.document, filename);
	}

	/**
	 * updateDocumentFile
	 * 
	 * if the file version has changed the function updates the document
	 * for the new file.
	 */
	public abstract void updateDocumentFile();
}
