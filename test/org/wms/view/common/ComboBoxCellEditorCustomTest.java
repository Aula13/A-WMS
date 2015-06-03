package org.wms.view.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.event.ChangeEvent;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test combo box cell editor for tables
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class ComboBoxCellEditorCustomTest {

	private static ComboBoxCellEditorCustom cmbCellEditor;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cmbCellEditor = new ComboBoxCellEditorCustom(new JComboBox());
	}

	/**
	 * Test combobox should be not editable
	 * after user single click on table cell
	 */
	@Test
	public void testIsCellEditableEventObjectSingleClick() {
		MouseEvent e = new MouseEvent(new JComboBox<>(), 0, 0l, 0, 0, 0, 0, 0, 1, false, 0);
		assertFalse(cmbCellEditor.isCellEditable(e));
	}
	
	/**
	 * Test combobox should be editable
	 * after user double click on table cell
	 */
	@Test
	public void testIsCellEditableEventObjectDoubleClick() {
		MouseEvent e = new MouseEvent(new JComboBox<>(), 0, 0l, 0, 0, 0, 0, 0, 2, false, 0);
		assertTrue(cmbCellEditor.isCellEditable(e));
	}
	
	/**
	 * Test combobox should be not editable
	 * after user other event
	 */
	@Test
	public void testIsCellEditableEventObjectOtherEvent() {
		ChangeEvent e = new ChangeEvent(new JComboBox<>());
		assertFalse(cmbCellEditor.isCellEditable(e));
	}

}
