package org.wms.view.orderedit;

import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

public class ComboBoxCellEditorCustom extends ComboBoxCellEditor {
	
	public ComboBoxCellEditorCustom(JComboBox comboBox) {
		super(comboBox);
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		if(e instanceof MouseEvent)
			return ((MouseEvent) e).getClickCount()==2;
		return false;
	}
}
