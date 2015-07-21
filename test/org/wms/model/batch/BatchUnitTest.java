package org.wms.model.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.common.ListType;
import org.wms.model.common.Priority;
import org.wms.model.common.Status;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;
import org.wms.model.warehouse.WarehouseCell;

public class BatchUnitTest {

	private static Batch testBatch;
	
	private static BatchRow mockBatchRow1;
	
	private static BatchRow mockBatchRow2;
	
	private static Order mockOrder;
	
	private static OrderRow mockOrderRow1;
	
	private static OrderRow mockOrderRow2;
	
	@BeforeClass
	public static void setupBeforeClass() {
		testBatch = new Batch(10l, ListType.OUTPUT);
		testBatch.allocated = 0;
		testBatch.batchStatus = Status.ASSIGNED;
		testBatch.priority = Priority.MEDIUM;
		
		mockBatchRow1 = mock(BatchRow.class);
		mockBatchRow2 = mock(BatchRow.class);
		mockOrderRow1 = mock(OrderRow.class);
		mockOrderRow2 = mock(OrderRow.class);
		mockOrder = mock(Order.class);
		
		when(mockBatchRow1.getQuantity()).thenReturn(10);
		when(mockBatchRow1.getReferredOrderRow()).thenReturn(mockOrderRow1);
		
		when(mockBatchRow2.getQuantity()).thenReturn(10);
		when(mockBatchRow2.getReferredOrderRow()).thenReturn(mockOrderRow2);
		
		when(mockOrderRow1.getOrder()).thenReturn(mockOrder);
		when(mockOrderRow2.getOrder()).thenReturn(mockOrder);
		
		testBatch.addRow(mockBatchRow1);
		testBatch.addRow(mockBatchRow2);
	}

	/**
	 * Test id should be returned correctly
	 */
	@Test
	public void testGetId() {
		assertTrue(testBatch.getId()==10l);
	}

	/**
	 * Test list type should be
	 * returned correctly
	 */
	@Test
	public void testGetType() {
		assertTrue(testBatch.getType()==ListType.OUTPUT);
	}

	/**
	 * Test priority should be
	 * returned correctly
	 */
	@Test
	public void testGetPriority() {
		assertTrue(testBatch.getPriority()==Priority.MEDIUM);
	}

	/**
	 * Test get rows should be
	 * return batchrow1 and batchrow2
	 */
	@Test
	public void testGetRows() {
		assertEquals(testBatch.getRows().get(0), mockBatchRow1);
		assertEquals(testBatch.getRows().get(1), mockBatchRow2);
	}

	/**
	 * Test batch status should be
	 * returned correctly
	 */
	@Test
	public void testGetBatchStatus() {
		assertTrue(testBatch.getBatchStatus()==Status.ASSIGNED);
	}

	/**
	 * Test batch should be
	 * allocated or not allocated
	 */
	@Test
	public void testIsAllocated() {
		Batch batchModel = new Batch();
		assertFalse(batchModel.isAllocated());
		batchModel.allocated = 1;
		assertTrue(batchModel.isAllocated());
	}

	/**
	 * Test batch should be allocated
	 */
	@Test
	public void testSetAsAllocated() {
		testBatch.setAsAllocated();
		assertTrue(testBatch.isAllocated());
	}
	
	/**
	 * Test batch should be completed
	 */
	@Test
	public void testSetAsCompleted() {
		Batch model = new Batch();
		model.addRow(mockBatchRow1);
		model.addRow(mockBatchRow2);
		
		WarehouseCell mockCell = mock(WarehouseCell.class);
		when(mockBatchRow1.getJobWarehouseCell()).thenReturn(mockCell);
		when(mockBatchRow2.getJobWarehouseCell()).thenReturn(mockCell);
		
		assertFalse(model.setAsCompleted());
		
		model.setAsAllocated();
		assertTrue(model.isAllocated());
		
		assertTrue(model.setAsCompleted());
	}

}
