package dsagenesis.editor.coredata.table.cell;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

public class CheckBoxCellRenderer 
		extends BasicCellRenderer 
{

	private static final long serialVersionUID = 1L;

	private JCheckBox checkBox;
	private JPanel panel;
	
	public CheckBoxCellRenderer()
	{
		super();
		
		checkBox = new JCheckBox();
		panel = new JPanel(new BorderLayout());
		panel.add(checkBox, BorderLayout.CENTER);
        checkBox.setBackground(this.getBackground());
        checkBox.setForeground(this.getForeground());
	}

	@Override
	public Component getTableCellRendererComponent(
			JTable table, 
			Object value, 
			boolean isSelected,
			boolean hasFocus,
			int row, 
			int column
		) 
	{
		// call super for coloring
		super.getTableCellRendererComponent(
				table,
				value,
				isSelected,
				hasFocus,
				row,
				column
			);

		checkBox.setBackground(this.getBackground());
		checkBox.setForeground(this.getForeground());

		// since sqlite has no boolean we have to convert it.
		// it can be "true" or 1 for true
		// and "false" or 0 for false
		if( value.equals(1) || value.equals("true") )
		{
			checkBox.setSelected(true);
		} else {
			checkBox.setSelected(false);
		}
		return panel;
	}
}
