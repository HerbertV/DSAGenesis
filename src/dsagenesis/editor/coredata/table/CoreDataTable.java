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
package dsagenesis.editor.coredata.table;

import javax.swing.JTable;


public class CoreDataTable 
		extends JTable 
{

	private static final long serialVersionUID = 1L;

	// TODO 
	public CoreDataTable() 
	{
		super();
		
		this.setModel(new CoreDataTableModel());
		this.setAutoCreateRowSorter(true);
		this.getTableHeader().setReorderingAllowed(false);
		
	}

	
}
