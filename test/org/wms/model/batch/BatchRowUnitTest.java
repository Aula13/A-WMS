package org.wms.model.batch;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;
import org.wms.model.warehouse.WarehouseCell;

public class BatchRowUnitTest {

	private static Batch mockBatch;
	
	private static BatchRow batchRowModel;
	
	private static OrderRow mockOrderRow;
	
	private static Order mockOrder;
	
	private static WarehouseCell mockCell;
	
	@BeforeClass
	public static void setupBeforeClass() {
		batchRowModel = new BatchRow();
		mockBatch = mock(Batch.class);
		mockOrder = mock(Order.class);
		mockOrderRow = mock(OrderRow.class);
		mockCell = mock(WarehouseCell.class);
		
		batchRowModel.referredBatch = mockBatch;
		batchRowModel.referredOrderRow = mockOrderRow;
		batchRowModel.jobWarehouseCell = mockCell;
		
		when(mockOrderRow.getOrder()).thenReturn(mockOrder);
		
		batchRowModel.id = 10l;
		batchRowModel.quantity = 20;
	}
	
	/**
	 * Test constructor should be initialize correctly
	 */
	@Test
	public void testBatchRow() {
		BatchRow batchRowTest = new BatchRow();
		batchRowTest = new BatchRow(1l, mockBatch, mockOrderRow);
	}

	/**
	 * Test getter and setter
	 */
	@Test
	public void testBatchRowGetter() {
		assertTrue(batchRowModel.getId()==10l);
		assertTrue(batchRowModel.getJobWarehouseCell()==mockCell);
		assertTrue(batchRowModel.getQuantity()==20);
		assertTrue(batchRowModel.getReferredBatch()==mockBatch);
		assertTrue(batchRowModel.getReferredOrderRow()==mockOrderRow);
	}
}
