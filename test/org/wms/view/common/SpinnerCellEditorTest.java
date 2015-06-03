package org.wms.view.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test spinner cell editor for tables
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class SpinnerCellEditorTest {

	private static SpinnerCellEditor spnCellEditor;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		spnCellEditor = new SpinnerCellEditor();
	}

	/**
	 * Test value should be set/getted correctly
	 * in the spinner cell editor
	 */
	@Test
	public void testGetCellEditorValue() {
		JComponent editor = (JComponent) spnCellEditor.getTableCellEditorComponent(null, 10, false, 0, 0);
		assertTrue(editor instanceof JSpinner);
		assertTrue(((int) spnCellEditor.getCellEditorValue())==10);
	}

	/**
	 * Test combobox should be not editable
	 * after user single click on table cell
	 */
	@Test
	public void testIsCellEditableEventObjectSingleClick() {
		MouseEvent e = new MouseEvent(new JComboBox<>(), 0, 0l, 0, 0, 0, 0, 0, 1, false, 0);
		assertFalse(spnCellEditor.isCellEditable(e));
	}
	
	/**
	 * Test combobox should be editable
	 * after user double click on table cell
	 */
	@Test
	public void testIsCellEditableEventObjectDoubleClick() {
		MouseEvent e = new MouseEvent(new JComboBox<>(), 0, 0l, 0, 0, 0, 0, 0, 2, false, 0);
		assertTrue(spnCellEditor.isCellEditable(e));
	}
	
	/**
	 * Test combobox should be not editable
	 * after user other event
	 */
	@Test
	public void testIsCellEditableEventObjectOtherEvent() {
		ChangeEvent e = new ChangeEvent(new JComboBox<>());
		assertFalse(spnCellEditor.isCellEditable(e));
	}

}
