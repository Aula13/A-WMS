package org.wms.controller.batch;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.batch.Batch;
import org.wms.model.batch.BatchRow;
import org.wms.model.common.ListType;
import org.wms.model.common.Priority;
import org.wms.model.common.Status;
import org.wms.model.material.Material;
import org.wms.model.order.OrderRow;
import org.wms.model.order.Order;
import org.wms.model.warehouse.WarehouseCell;

public class BatchRowsTableModelUnitTest {

	private static Batch mockBatch;
	
	private static BatchRow mockBatchRow;
	
	private static OrderRow mockOrderRow;
	
	private static Order mockOrder;
	
	private static WarehouseCell mockWarehouseCell;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockBatch = mock(Batch.class);
		mockBatchRow = mock(BatchRow.class);
		mockOrderRow = mock(OrderRow.class);
		mockOrder = mock(Order.class);
		mockWarehouseCell = mock(WarehouseCell.class);
		
		when(mockBatch.getId()).thenReturn(100l);
		when(mockBatch.getType()).thenReturn(ListType.INPUT);
		when(mockBatch.getBatchStatus()).thenReturn(Status.WAITING);
		when(mockBatch.getPriority()).thenReturn(Priority.LOW);
		
		when(mockBatchRow.getId()).thenReturn(1l);
		when(mockBatchRow.getQuantity()).thenReturn(1);
		when(mockBatchRow.getReferredBatch()).thenReturn(mockBatch);
		when(mockBatchRow.getReferredOrderRow()).thenReturn(mockOrderRow);
		when(mockBatchRow.getJobWarehouseCell()).thenReturn(mockWarehouseCell);
		
		when(mockOrderRow.getId()).thenReturn(2l);
		when(mockOrderRow.getMaterial()).thenReturn(new Material(1000l));
		when(mockOrderRow.getQuantity()).thenReturn(1);
		when(mockOrderRow.getOrder()).thenReturn(mockOrder);
		
		when(mockOrder.getId()).thenReturn(200l);
		
		when(mockWarehouseCell.getPublicId()).thenReturn("A/1/1");
		
		List<BatchRow> testList = new ArrayList<>();
		testList.add(mockBatchRow);
		
		when(mockBatch.getRows())
			.thenReturn(testList);
	}
	
	/**
	 * Test initialization should be correct
	 */
	@Test
	public void testBathRowsTableModel() {
		BatchRowsTableModel tableModel = new BatchRowsTableModel(mockBatch);
		assertTrue(tableModel.batch.equals(mockBatch));
	}

	/**
	 * Test row count should be 1
	 */
	@Test
	public void testGetRowCount() {
		BatchRowsTableModel tableModel = new BatchRowsTableModel(mockBatch);		
		assertTrue(tableModel.getRowCount()==1);
	}

	/**
	 * Test column count should be 5
	 */
	@Test
	public void testGetColumnCount() {
		BatchRowsTableModel tableModel = new BatchRowsTableModel(mockBatch);
		assertTrue(tableModel.getColumnCount()==6);
	}

	/**
	 * Test count name should be the same as the
	 * headers array definition
	 */
	@Test
	public void testGetColumnNameInt() {
		BatchRowsTableModel tableModel = new BatchRowsTableModel(mockBatch);
		
		assertTrue(tableModel.getColumnName(0).compareTo(tableModel.headers[0])==0);
		assertTrue(tableModel.getColumnName(1).compareTo(tableModel.headers[1])==0);
		assertTrue(tableModel.getColumnName(2).compareTo(tableModel.headers[2])==0);
		assertTrue(tableModel.getColumnName(3).compareTo(tableModel.headers[3])==0);
		assertTrue(tableModel.getColumnName(4).compareTo(tableModel.headers[4])==0);
		assertTrue(tableModel.getColumnName(5).compareTo(tableModel.headers[5])==0);
	}

	/**
	 * The value provided should be the data
	 * stored in the test batchrow
	 */
	@Test
	public void testGetValueAt() {
		BatchRowsTableModel tableModel = new BatchRowsTableModel(mockBatch);
		tableModel.getRowCount();
		
		assertTrue(((long) tableModel.getValueAt(0, 0))==BatchRowsTableModelUnitTest.mockBatchRow.getId());
		assertTrue(((long) tableModel.getValueAt(0, 1))==
				BatchRowsTableModelUnitTest.mockBatchRow.getReferredOrderRow().getOrder().getId());
		
		assertTrue(((long) tableModel.getValueAt(0, 2))==
				BatchRowsTableModelUnitTest.mockBatchRow.getReferredOrderRow().getId());
		
		assertTrue(((long) tableModel.getValueAt(0, 3))==
				BatchRowsTableModelUnitTest.mockBatchRow.getReferredOrderRow().getMaterial().getCode());
		
		assertTrue(((String) tableModel.getValueAt(0, 4))
				.compareTo(BatchRowsTableModelUnitTest.mockBatchRow.getJobWarehouseCell().getPublicId())==0);
		
		assertTrue(((int) tableModel.getValueAt(0, 5))==
				BatchRowsTableModelUnitTest.mockBatchRow.getQuantity());
		
	}
	
	/**
	 * Test invalid get column index
	 */
	@Test
	public void testInvalidIndexGetValueAt() {
		BatchRowsTableModel tableModel = new BatchRowsTableModel(mockBatch);
		
		assertTrue(((String) tableModel.getValueAt(0, tableModel.getColumnCount()+1))
				.compareTo("Unknow column: " + (tableModel.getColumnCount()+1))==0);
	}
}
