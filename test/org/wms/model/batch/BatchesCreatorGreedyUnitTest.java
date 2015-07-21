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
	
	private static Order mockOrder1;
	
	private static OrderRow mockOrderRow11;
	
	private static OrderRow mockOrderRow12;
	
	private static Order mockOrder2;
	
	private static OrderRow mockOrderRow21;
	
	private static OrderRow mockOrderRow22;
	
	private static Order mockOrder3;
	
	private static OrderRow mockOrderRow31;
	
	private static OrderRow mockOrderRow32;
	
	private static Order mockOrder4;
	
	private static Date emissionDate = new Date();
	
	private static Warehouse mockWarehouse;
	
	private static WarehouseLine mockWarehouseLine;
	
	private static WarehouseShelf mockWarehouseShelf;
	
	private static WarehouseCell mockWarehouseCell1;
	
	private static WarehouseCell mockWarehouseCell2;
	
	private static WarehouseCell mockWarehouseCell3;
	
	private static List<Material> materials = new ArrayList<>();
	
	private static Material material;
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockBatch = mock(Batch.class);
		mockBatchRow = mock(BatchRow.class);
		mockOrder1 = mock(Order.class);
		mockOrder2 = mock(Order.class);
		mockOrder3 = mock(Order.class);
		mockOrder4 = mock(Order.class);
		mockOrderRow11 = mock(OrderRow.class);
		mockOrderRow12 = mock(OrderRow.class);
		mockOrderRow21 = mock(OrderRow.class);
		mockOrderRow22 = mock(OrderRow.class);
		mockOrderRow31 = mock(OrderRow.class);
		mockOrderRow32 = mock(OrderRow.class);
		mockWarehouse = mock(Warehouse.class);
		mockWarehouseLine = mock(WarehouseLine.class);
		mockWarehouseShelf = mock(WarehouseShelf.class);
		mockWarehouseCell1 = mock(WarehouseCell.class);
		mockWarehouseCell2 = mock(WarehouseCell.class);
		mockWarehouseCell3 = mock(WarehouseCell.class);
		
		List<WarehouseLine> lines = new ArrayList<>();
		lines.add(mockWarehouseLine);
		
		List<WarehouseShelf> shelfs = new ArrayList<>();
		shelfs.add(mockWarehouseShelf);
		
		List<WarehouseCell> cells = new ArrayList<>();
		cells.add(mockWarehouseCell1);
		cells.add(mockWarehouseCell2);
		cells.add(mockWarehouseCell3);
		
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
		
		when(mockWarehouseCell3.getMaterial()).thenReturn(new Material(1001l));
		when(mockWarehouseCell3.getId()).thenReturn(12l);
		when(mockWarehouseCell3.getAlreadyReservedQuantity()).thenReturn(0);
		when(mockWarehouseCell3.getQuantity()).thenReturn(10);
		
		List<BatchRow> batchrows = new ArrayList<>();
		batchrows.add(mockBatchRow);
		when(mockBatch.getRows()).thenReturn(batchrows);
		when(mockBatch.getBatchStatus()).thenReturn(Status.ASSIGNED);
		when(mockBatchRow.getJobWarehouseCell()).thenReturn(mockWarehouseCell1);
		when(mockBatchRow.getQuantity()).thenReturn(5);
		
		List<OrderRow> orderrows1 = new ArrayList<>();
		orderrows1.add(mockOrderRow11);
		orderrows1.add(mockOrderRow12);
		when(mockOrder1.getUnmodificableMaterials()).thenReturn(orderrows1);
		when(mockOrder1.getType()).thenReturn(ListType.OUTPUT);
		when(mockOrder1.getPriority()).thenReturn(Priority.MEDIUM);
		when(mockOrder1.getOrderStatus()).thenReturn(Status.WAITING);
		when(mockOrder1.getEmissionDate()).thenReturn(emissionDate);
		
		when(mockOrderRow11.isAllocated()).thenReturn(false);
		when(mockOrderRow11.getMaterial()).thenReturn(new Material(1000l));
		when(mockOrderRow11.getQuantity()).thenReturn(5);
		when(mockOrderRow11.getOrder()).thenReturn(mockOrder1);
		
		when(mockOrderRow12.isAllocated()).thenReturn(false);
		when(mockOrderRow12.getMaterial()).thenReturn(new Material(1001l));
		when(mockOrderRow12.getQuantity()).thenReturn(11);
		when(mockOrderRow12.getOrder()).thenReturn(mockOrder1);
		
		List<OrderRow> orderrows2 = new ArrayList<>();
		orderrows2.add(mockOrderRow21);
		orderrows2.add(mockOrderRow22);
		when(mockOrder2.getUnmodificableMaterials()).thenReturn(orderrows1);
		when(mockOrder2.getType()).thenReturn(ListType.OUTPUT);
		when(mockOrder2.getPriority()).thenReturn(Priority.MEDIUM);
		when(mockOrder2.getOrderStatus()).thenReturn(Status.WAITING);
		when(mockOrder2.getEmissionDate()).thenReturn(emissionDate);
		
		when(mockOrderRow21.isAllocated()).thenReturn(false);
		when(mockOrderRow21.getMaterial()).thenReturn(new Material(1000l));
		when(mockOrderRow21.getQuantity()).thenReturn(20);
		when(mockOrderRow21.getOrder()).thenReturn(mockOrder2);
		
		when(mockOrderRow22.isAllocated()).thenReturn(false);
		when(mockOrderRow22.getMaterial()).thenReturn(new Material(1001l));
		when(mockOrderRow22.getQuantity()).thenReturn(100);
		when(mockOrderRow22.getOrder()).thenReturn(mockOrder2);
		
		List<OrderRow> orderrows3 = new ArrayList<>();
		orderrows3.add(mockOrderRow31);
		orderrows3.add(mockOrderRow32);
		when(mockOrder3.getUnmodificableMaterials()).thenReturn(orderrows1);
		when(mockOrder3.getType()).thenReturn(ListType.INPUT);
		when(mockOrder3.getPriority()).thenReturn(Priority.MEDIUM);
		when(mockOrder3.getOrderStatus()).thenReturn(Status.WAITING);
		when(mockOrder3.getEmissionDate()).thenReturn(emissionDate);
		
		when(mockOrderRow31.isAllocated()).thenReturn(false);
		when(mockOrderRow31.getMaterial()).thenReturn(new Material(1000l));
		when(mockOrderRow31.getQuantity()).thenReturn(30);
		when(mockOrderRow31.getOrder()).thenReturn(mockOrder3);
		
		when(mockOrderRow32.isAllocated()).thenReturn(false);
		when(mockOrderRow32.getMaterial()).thenReturn(new Material(1001l));
		when(mockOrderRow32.getQuantity()).thenReturn(100);
		when(mockOrderRow32.getOrder()).thenReturn(mockOrder3);
		
		when(mockOrder4.getOrderStatus()).thenReturn(Status.COMPLETED);
		
		materials.add(new Material(1000l));
		materials.add(new Material(1001l));
		materials.add(new Material(1002l));
		
		alreadyAllocatedBatches.add(mockBatch);
		orders.add(mockOrder1);
		orders.add(mockOrder2);
		orders.add(mockOrder3);
		orders.add(mockOrder4);
	}

	/**
	 * Test compute batch algorithm
	 */
	@Test
	public void testComputeListOfBatch() {
		BatchesCreatorGreedy alg = new BatchesCreatorGreedy();
		alg.computeListOfBatch(alreadyAllocatedBatches, orders, mockWarehouse, materials);
	}

	/**
	 * Test valid selection function
	 */
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

	/**
	 * Test minot quantity cell research algorithm
	 */
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
