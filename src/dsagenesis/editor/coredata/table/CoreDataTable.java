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
		
	}

	
}
