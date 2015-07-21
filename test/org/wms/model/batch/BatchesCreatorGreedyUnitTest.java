package org.wms.model.batch;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
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
	
	private static OrderRow mockOrderRow;
	
	private static Warehouse mockWarehouse;
	
	private static WarehouseLine mockWarehouseLine;
	
	private static WarehouseShelf mockWarehouseShelf;
	
	private static WarehouseCell mockWarehouseCell;
	
	private static List<Material> materials = new ArrayList<>();
	
	private static Material material;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockBatch = mock(Batch.class);
		mockBatchRow = mock(BatchRow.class);
		mockOrder = mock(Order.class);
		mockOrderRow = mock(OrderRow.class);
		mockWarehouse = mock(Warehouse.class);
		mockWarehouseLine = mock(WarehouseLine.class);
		mockWarehouseShelf = mock(WarehouseShelf.class);
		mockWarehouseCell = mock(WarehouseCell.class);
		
		List<BatchRow> batchrows = new ArrayList<>();
		batchrows.add(mockBatchRow);
		when(mockBatch.getRows()).thenReturn(batchrows);
		when(mockBatchRow.getJobWarehouseCell()).thenReturn(mockWarehouseCell);
		
		List<OrderRow> orderrows = new ArrayList<>();
		orderrows.add(mockOrderRow);
		when(mockOrder.getUnmodificableMaterials()).thenReturn(orderrows);
		
		List<WarehouseLine> lines = new ArrayList<>();
		lines.add(mockWarehouseLine);
		
		List<WarehouseShelf> shelfs = new ArrayList<>();
		shelfs.add(mockWarehouseShelf);
		
		List<WarehouseCell> cells = new ArrayList<>();
		cells.add(mockWarehouseCell);
		
		when(mockWarehouse.getUnmodifiableLines()).thenReturn(lines);
		when(mockWarehouseLine.getUnmodifiableListShelfs()).thenReturn(shelfs);
		when(mockWarehouseShelf.getUnmodificableListCells()).thenReturn(cells);
		
		when(mockWarehouseCell.getMaterial()).thenReturn(new Material(1000l));
		when(mockWarehouseCell.getId()).thenReturn(10l);
		
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
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetMinorQuantityCell() {
//		fail("Not yet implemented"); // TODO
	}

}
