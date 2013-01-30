package extras;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class AlternatingColorTable extends JTable {

	public AlternatingColorTable() {
		super();
		super.getColumnModel().getColumn(0).setResizable(false);
		super.getTableHeader().setReorderingAllowed(false);
		super.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public AlternatingColorTable(DefaultTableModel model) {
		super(model);
		super.getColumnModel().getColumn(0).setResizable(false);
		super.getTableHeader().setReorderingAllowed(false);
		super.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	public Component prepareRenderer(TableCellRenderer renderer, int index_row,
			int index_col) {
		Component comp = super.prepareRenderer(renderer, index_row, index_col);
		// even index, selected or not selected

		if (index_row % 2 == 0) {
			comp.setBackground(Color.LIGHT_GRAY);
		} else {
			comp.setBackground(Color.white);
		}
		if (isCellSelected(index_row, index_col)) {
			// Light Blue
			comp.setBackground(new Color(30, 150, 255));
		}
		return comp;
	}

}
