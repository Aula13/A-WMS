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

/**
 * Spinner cell editor
 * for JTable cell
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class SpinnerCellEditor extends AbstractCellEditor implements TableCellEditor {

	private JSpinner spinnerCell;
	
	/**
	 * Create a new spinner cell editor
	 */
	public SpinnerCellEditor() {
		spinnerCell = FactoryReferences.fields.getParameterSpinner(JSpinnerType.INT);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		spinnerCell.setValue(value);
		return spinnerCell;
	}

	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		return spinnerCell.getValue();
	}
	
	/** 
	 * Enable editing only after user double click on the cell
	 * 
	 * @see javax.swing.AbstractCellEditor#isCellEditable(java.util.EventObject)
	 */
	@Override
	public boolean isCellEditable(EventObject e) {
		if(e instanceof MouseEvent)
			return ((MouseEvent) e).getClickCount()==2;
		return false;
	}
}
