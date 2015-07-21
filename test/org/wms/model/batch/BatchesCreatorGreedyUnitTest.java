package org.wms.model.batch;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.common.ListType;
import org.wms.model.common.Priority;
import org.wms.model.common.Status;
import org.wms.model.material.Material;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;
import org.wms.model.warehouse.Warehouse;
import org.wms.model.warehouse.WarehouseCell;
import org.wms.model.warehouse.WarehouseLine;
import org.wms.model.warehouse.WarehouseShelf;

public class BatchesCreatorGreedyUnitTest {

	private static List<Batch> alreadyAllocatedBatches = new ArrayList<>();
	
	private static Batch mockBatch;
	
	private static BatchRow mockBatchRow;
	
	private static List<Order> orders = new ArrayList<>();
	
	private static Order mockOrder;
	
	private static OrderRow mockOrderRow1;
	
	private static OrderRow mockOrderRow2;
	
	private static Date emissionDate = new Date();
	
	private static Warehouse mockWarehouse;
	
	private static WarehouseLine mockWarehouseLine;
	
	private static WarehouseShelf mockWarehouseShelf;
	
	private static WarehouseCell mockWarehouseCell1;
	
	private static WarehouseCell mockWarehouseCell2;
	
	private static List<Material> materials = new ArrayList<>();
	
	private static Material material;
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockBatch = mock(Batch.class);
		mockBatchRow = mock(BatchRow.class);
		mockOrder = mock(Order.class);
		mockOrderRow1 = mock(OrderRow.class);
		mockOrderRow2 = mock(OrderRow.class);
		mockWarehouse = mock(Warehouse.class);
		mockWarehouseLine = mock(WarehouseLine.class);
		mockWarehouseShelf = mock(WarehouseShelf.class);
		mockWarehouseCell1 = mock(WarehouseCell.class);
		mockWarehouseCell2 = mock(WarehouseCell.class);
		
		List<WarehouseLine> lines = new ArrayList<>();
		lines.add(mockWarehouseLine);
		
		List<WarehouseShelf> shelfs = new ArrayList<>();
		shelfs.add(mockWarehouseShelf);
		
		List<WarehouseCell> cells = new ArrayList<>();
		cells.add(mockWarehouseCell1);
		cells.add(mockWarehouseCell2);
		
		when(mockWarehouse.getUnmodifiableLines()).thenReturn(lines);
		when(mockWarehouseLine.getUnmodifiableListShelfs()).thenReturn(shelfs);
		when(mockWarehouseShelf.getUnmodificableListCells()).thenReturn(cells);
		
		when(mockWarehouseCell1.getMaterial()).thenReturn(new Material(1000l));
		when(mockWarehouseCell1.getId()).thenReturn(10l);
		when(mockWarehouseCell1.getAlreadyReservedQuantity()).thenReturn(0);
		when(mockWarehouseCell1.getQuantity()).thenReturn(100);
		
		when(mockWarehouseCell2.getMaterial()).thenReturn(new Material(1001l));
		when(mockWarehouseCell2.getId()).thenReturn(11l);
		when(mockWarehouseCell2.getAlreadyReservedQuantity()).thenReturn(0);
		when(mockWarehouseCell2.getQuantity()).thenReturn(10);
		
		List<BatchRow> batchrows = new ArrayList<>();
		batchrows.add(mockBatchRow);
		when(mockBatch.getRows()).thenReturn(batchrows);
		when(mockBatch.getBatchStatus()).thenReturn(Status.ASSIGNED);
		when(mockBatchRow.getJobWarehouseCell()).thenReturn(mockWarehouseCell1);
		when(mockBatchRow.getQuantity()).thenReturn(5);
		
		List<OrderRow> orderrows = new ArrayList<>();
		orderrows.add(mockOrderRow1);
		orderrows.add(mockOrderRow2);
		when(mockOrder.getUnmodificableMaterials()).thenReturn(orderrows);
		when(mockOrder.getType()).thenReturn(ListType.OUTPUT);
		when(mockOrder.getPriority()).thenReturn(Priority.MEDIUM);
		when(mockOrder.getOrderStatus()).thenReturn(Status.WAITING);
		when(mockOrder.getEmissionDate()).thenReturn(emissionDate);
		
		when(mockOrderRow1.isAllocated()).thenReturn(false);
		when(mockOrderRow1.getMaterial()).thenReturn(new Material(1000l));
		when(mockOrderRow1.getQuantity()).thenReturn(5);
		when(mockOrderRow1.getOrder()).thenReturn(mockOrder);
		
		when(mockOrderRow2.isAllocated()).thenReturn(false);
		when(mockOrderRow2.getMaterial()).thenReturn(new Material(1001l));
		when(mockOrderRow2.getQuantity()).thenReturn(11);
		when(mockOrderRow2.getOrder()).thenReturn(mockOrder);
		
		materials.add(new Material(1000l));
		materials.add(new Material(1001l));
		materials.add(new Material(1002l));
		
		alreadyAllocatedBatches.add(mockBatch);
		orders.add(mockOrder);
	}

	@Test
	public void testComputeListOfBatch() {
		BatchesCreatorGreedy alg = new BatchesCreatorGreedy();
		alg.computeListOfBatch(alreadyAllocatedBatches, orders, mockWarehouse, materials);
	}

	@Test
	public void testGreedyBatch() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testPrepareWarehouseCell() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testPrepareOrders() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testIsValidSelection() {
		BatchesCreatorGreedy alg = new BatchesCreatorGreedy();
		
		OrderRow row = new OrderRow(new Order(), new Material(), 15);
		
		List<WarehouseCell> cells = new ArrayList<>();
		cells.add(new WarehouseCell(1l, new Material(), 20, new WarehouseShelf()));
		cells.add(new WarehouseCell(2l, new Material(), 30, new WarehouseShelf()));
		cells.add(new WarehouseCell(3l, new Material(), 10, new WarehouseShelf()));
		cells.add(new WarehouseCell(4l, new Material(), 50, new WarehouseShelf()));
		
		assertTrue(alg.isValidSelection(row, cells).isPresent());
		
		row = new OrderRow(new Order(), new Material(), 90);
		
		assertFalse(alg.isValidSelection(row, cells).isPresent());
		
		
		
	}

	@Test
	public void testGetMinorQuantityCell() {
		BatchesCreatorGreedy alg = new BatchesCreatorGreedy();
		
		List<WarehouseCell> cells = new ArrayList<>();
		cells.add(new WarehouseCell(1l, new Material(), 20, new WarehouseShelf()));
		cells.add(new WarehouseCell(2l, new Material(), 30, new WarehouseShelf()));
		cells.add(new WarehouseCell(3l, new Material(), 10, new WarehouseShelf()));
		cells.add(new WarehouseCell(4l, new Material(), 50, new WarehouseShelf()));
		WarehouseCell cell = alg.getMinorQuantityCell(cells);
		assertTrue(cell.getId()==3l);
	}

}
