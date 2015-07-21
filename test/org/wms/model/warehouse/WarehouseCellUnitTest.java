package org.wms.model.warehouse;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.material.Material;

public class WarehouseCellUnitTest {
	
	@BeforeClass
	public static void setupBeforeClass() {
		
	}
	
	@Test
	public void testWarehouseCell() {
		WarehouseCell cell = new WarehouseCell(1l, new Material(10l), 10, new WarehouseShelf());
		assertTrue(cell.getId()==1l);
		assertTrue(cell.getMaterial().getCode()==10l);
		assertTrue(cell.getQuantity()==10);
	}
	/**
	 * Test set cell already reserved quantity should be possible
	 * only if the new value is greater than quantity
	 */
	@Test
	public void testSetAlreadyReservedQuantity() {
		WarehouseCell cell = new WarehouseCell(1l, new Material(10l), 10, new WarehouseShelf());
		assertFalse(cell.setAlreadyReservedQuantity(-1));
		assertTrue(cell.setAlreadyReservedQuantity(9));
		assertFalse(cell.setAlreadyReservedQuantity(11));
	}


	/**
	 * Test set cell quantity should be possible
	 * only if the new value is greater than already
	 * reserved value
	 */
	@Test
	public void testSetQuantity() {
		WarehouseCell cell = new WarehouseCell(1l, new Material(10l), 10, new WarehouseShelf());
		cell.setAlreadyReservedQuantity(10);
		assertFalse(cell.setQuantity(-1));
		assertFalse(cell.setQuantity(9));
		assertTrue(cell.setQuantity(11));
	}

}
