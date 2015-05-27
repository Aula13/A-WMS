package org.wms.view.orderedit;

import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.JSpinnerType;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

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
	
	@Override
	public boolean isCellEditable(EventObject e) {
		if(e instanceof MouseEvent)
			return ((MouseEvent) e).getClickCount()==2;
		return false;
	}
}
