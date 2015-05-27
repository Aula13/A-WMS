package org.wms.view.orderedit;

import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

/**
 * Custom combobox cell editor
 * 
 * Override isCellEditable method to allow edit only on user double click
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class ComboBoxCellEditorCustom extends ComboBoxCellEditor {
	
	/**
	 * @param comboBox
	 */
	public ComboBoxCellEditorCustom(JComboBox comboBox) {
		super(comboBox);
	}

	/** 
	 * Start edit only on user double click
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
