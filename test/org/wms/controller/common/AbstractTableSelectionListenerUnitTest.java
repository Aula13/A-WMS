package org.wms.controller.common;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.MouseEvent;

import javax.swing.JSpinner;
import javax.swing.JTable;

import org.junit.BeforeClass;
import org.junit.Test;

public class AbstractTableSelectionListenerUnitTest {

	private static JTable mockTable;
	
	private static AbstractTableSelectionListener tableSelectionListener;
	
	private static boolean mockIsValid;
	private static boolean mockDoubleClick;
	private static int mockRowIndex;
	private static boolean mockRequireMenu;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockTable = mock(JTable.class);
		
		tableSelectionListener = new AbstractTableSelectionListener() {
			
			@Override
			public void validSelectionTrigger(boolean doubleClick, int rowIndex,
					boolean requireMenu) {
				mockIsValid = true;
				mockDoubleClick = doubleClick;
				mockRowIndex = rowIndex;
				mockRequireMenu = requireMenu;
			}
			
			@Override
			public void invalidSelectionTriggered() {
				mockIsValid = false;
			}
		};		
	}

	/**
	 * Selection on the table should be invalid
	 */
	@Test
	public void testInvalidObjectSelectionTest() {
		when(mockTable.getSelectedRow()).thenReturn(-1);
		
		MouseEvent e = new MouseEvent(
				new JSpinner(), 0, 0l, 0, 0, 0, 
				0, 0, 1, false, 0);
		tableSelectionListener.mouseClicked(e);
		
		assertFalse(AbstractTableSelectionListenerUnitTest.mockIsValid);
	}
	
	/**
	 * Selection on the table should be invalid
	 */
	@Test
	public void testInvalidSelectionTest() {
		when(mockTable.getSelectedRow()).thenReturn(-1);
		
		MouseEvent e = new MouseEvent(
				mockTable, 0, 0l, 0, 0, 0, 
				0, 0, 1, false, 0);
		tableSelectionListener.mouseClicked(e);
		
		assertFalse(AbstractTableSelectionListenerUnitTest.mockIsValid);
	}
	
	/**
	 * Selection on the table should be valid
	 * with single press, indexRow 1
	 */
	@Test
	public void testValidSelectionTest() {
		when(mockTable.getSelectedRow()).thenReturn(1);
		
		MouseEvent e = new MouseEvent(
				mockTable, 0, 0l, 0, 0, 0, 
				0, 0, 1, false, 0);
		tableSelectionListener.mouseClicked(e);
		
		assertTrue(AbstractTableSelectionListenerUnitTest.mockIsValid);
		assertFalse(AbstractTableSelectionListenerUnitTest.mockDoubleClick);
		assertTrue(AbstractTableSelectionListenerUnitTest.mockRowIndex==1);
		assertFalse(AbstractTableSelectionListenerUnitTest.mockRequireMenu);
	}
	
	/**
	 * Selection on the table should be valid
	 * with double press
	 */
	@Test
	public void testValidSelectionDoubleClickTest() {
		when(mockTable.getSelectedRow()).thenReturn(1);
		
		MouseEvent e = new MouseEvent(
				mockTable, 0, 0l, 0, 0, 0, 
				0, 0, 2, false, 0);
		tableSelectionListener.mouseClicked(e);
		
		assertTrue(AbstractTableSelectionListenerUnitTest.mockIsValid);
		assertTrue(AbstractTableSelectionListenerUnitTest.mockDoubleClick);
		assertTrue(AbstractTableSelectionListenerUnitTest.mockRowIndex==1);
		assertFalse(AbstractTableSelectionListenerUnitTest.mockRequireMenu);
	}
	
	/**
	 * Selection on the table should be valid
	 * with menu required
	 */
	@Test
	public void testValidSelectionMenuRequiredTest() {
		when(mockTable.getSelectedRow()).thenReturn(1);
		
		MouseEvent e = new MouseEvent(
				mockTable, 0, 0l, 0, 0, 0, 
				0, 0, 2, true, 0);
		tableSelectionListener.mouseClicked(e);
		
		assertTrue(AbstractTableSelectionListenerUnitTest.mockIsValid);
		assertTrue(AbstractTableSelectionListenerUnitTest.mockDoubleClick);
		assertTrue(AbstractTableSelectionListenerUnitTest.mockRowIndex==1);
		assertTrue(AbstractTableSelectionListenerUnitTest.mockRequireMenu);
	}

}
