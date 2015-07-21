package org.wms.controller.warehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.controller.batch.BatchesTableModel;
import org.wms.controller.batch.BatchesTableModelUnitTest;
import org.wms.model.material.Material;
import org.wms.model.warehouse.WarehouseCell;
import org.wms.model.warehouse.WarehouseShelf;

public class WarehouseCellsTableModelUnitTest {

	private static WarehouseShelf mockShelf;

	private static WarehouseCell mockCell;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockShelf = mock(WarehouseShelf.class);
		mockCell = mock(WarehouseCell.class);
		
		when(mockCell.getPublicId()).thenReturn("A/1/1");
		when(mockCell.getMaterial()).thenReturn(new Material(1000l));
		when(mockCell.getQuantity()).thenReturn(10);
		when(mockCell.getAlreadyReservedQuantity()).thenReturn(10);
		
		List<WarehouseCell> testList = new ArrayList<>();
		testList.add(mockCell);
		
		when(mockShelf.getUnmodificableListCells())
			.thenReturn(testList);
	}
	
	/**
	 * Test shelf reference should be
	 * correctly initialized
	 */
	@Test
	public void testWarehouseCellsTableModel() {
		WarehouseCellsTableModel tableModel = new WarehouseCellsTableModel(mockShelf);
		assertEquals(tableModel.shelf, mockShelf);
	}
	
	/**
	 * Test rows count should be 1
	 */
	@Test
	public void testGetRowCount() {
		WarehouseCellsTableModel tableModel = new WarehouseCellsTableModel(mockShelf);
		assertTrue(tableModel.getRowCount()==1);
	}
	
	/**
	 * Test columns count should be 4
	 */
	@Test
	public void testGetColumnCount() {
		WarehouseCellsTableModel tableModel = new WarehouseCellsTableModel(mockShelf);
		assertTrue(tableModel.getColumnCount()==4);
	}
	
	/**
	 * Test column names should be
	 * correctly loaded
	 */
	@Test
	public void testGetColumnsName() {
		WarehouseCellsTableModel tableModel = new WarehouseCellsTableModel(mockShelf);
		
		assertTrue(tableModel.getColumnName(0).compareTo(tableModel.headers[0])==0);
		assertTrue(tableModel.getColumnName(1).compareTo(tableModel.headers[1])==0);
		assertTrue(tableModel.getColumnName(2).compareTo(tableModel.headers[2])==0);
		assertTrue(tableModel.getColumnName(3).compareTo(tableModel.headers[3])==0);
	}
	
	/**
	 * The value provided should be the data
	 * stored in the test cell
	 */
	@Test
	public void testGetValueAt() {
		WarehouseCellsTableModel tableModel = new WarehouseCellsTableModel(mockShelf);
		tableModel.getRowCount();
		
		assertTrue(((String) tableModel.getValueAt(0, 0))
				.compareTo(WarehouseCellsTableModelUnitTest.mockCell.getPublicId())==0);
		
		assertTrue(((long) tableModel.getValueAt(0, 1))==
				WarehouseCellsTableModelUnitTest.mockCell.getMaterial().getCode());
		
		assertTrue(((int) tableModel.getValueAt(0, 2))==
				WarehouseCellsTableModelUnitTest.mockCell.getQuantity());
		
		assertTrue(((int) tableModel.getValueAt(0, 3))==
				WarehouseCellsTableModelUnitTest.mockCell.getAlreadyReservedQuantity());
		
	}
	
	/**
	 * Test invalid get column index
	 */
	@Test
	public void testInvalidIndexGetValueAt() {
		WarehouseCellsTableModel tableModel = new WarehouseCellsTableModel(mockShelf);
		
		assertTrue(((String) tableModel.getValueAt(0, tableModel.getColumnCount()+1))
				.compareTo("Unknow column: " + (tableModel.getColumnCount()+1))==0);
	}
	
	

}
