package org.wms.view.orderedit;

import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.JSpinnerType;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class SpinnerCellEditor extends AbstractCellEditor implements TableCellEditor {

	private JSpinner spinnerCell;
	
	public SpinnerCellEditor() {
		spinnerCell = FactoryReferences.fields.getParameterSpinner(JSpinnerType.INT);
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		spinnerCell.setValue(value);
		return spinnerCell;
	}

	@Override
	public Object getCellEditorValue() {
		return spinnerCell.getValue();
	}
	
	
}
