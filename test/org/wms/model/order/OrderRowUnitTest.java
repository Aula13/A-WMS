package org.wms.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.material.Material;


/**
 * Class for testing the OrderRow and Material objects
 * 
 * @author Daniele Ciriello, Stefano Pessina
 *
 */
public class OrderRowUnitTest {
	
	private static Order mockOrder;
	
	private static Material mockMaterial;
	
	private static int quantity = 200;
	
	@BeforeClass
	public static void setupBeforeClass() {
		mockOrder = mock(Order.class);
		mockMaterial = mock(Material.class);
	}
	/**
	 * Test empty constructor
	 */
	@Test
	public void testOrderRowEmpyConstructor() {
		OrderRow orderrow = new OrderRow();
	}
	
	/**
	 * Test order row constructor
	 */
	@Test
	public void testOrderRowConstructor() {
		OrderRow orderrow = new OrderRow(mockOrder, mockMaterial, quantity);
		assertSame(mockOrder, orderrow.getOrder());
		assertSame(mockMaterial, orderrow.getMaterial());
		assertTrue(orderrow.getQuantity()==quantity);
	}
	
	/**
	 * Test quantity should be not set
	 * because quantity is less than 0
	 */
	@Test
	public void testSetQuantityLessThan0() {
		OrderRow orderrow = new OrderRow();
		assertFalse(orderrow.setQuantity(-1));
	}
	
	/**
	 * Test quantity should be set
	 */
	@Test
	public void testSetQuantity() {
		OrderRow orderrow = new OrderRow();
		assertTrue(orderrow.setQuantity(100));
		assertTrue(orderrow.getQuantity()==100);
	}
	
	/**
	 * Test material should be not set
	 * because the order row is not editable
	 */
	@Test
	public void testSetMaterialNotEditable() {
		OrderRow orderrow = new OrderRow();
		orderrow.setAllocated();
		assertFalse(orderrow.setMaterial(mockMaterial));
	}
	
	/**
	 * Test material should be set
	 */
	@Test
	public void testSetMaterial() {
		OrderRow orderrow = new OrderRow();
		assertTrue(orderrow.setMaterial(mockMaterial));
		assertSame(mockMaterial, orderrow.getMaterial());
	}
	
	/**
	 * Test material should be allocated
	 */
	@Test
	public void testIsAllocated() {
		OrderRow orderrow = new OrderRow();
		orderrow.setAllocated();
		assertTrue(orderrow.isAllocated());
	}
	
	/**
	 * Test material should be completed
	 */
	@Test
	public void testIsCompleted() {
		OrderRow orderrow = new OrderRow();
		orderrow.setCompleted();
		assertTrue(orderrow.isCompleted());
	}
	
	/**
	 * Test order row should be not editable
	 * because it is allocated
	 */
	@Test
	public void testIsEditableNotEditableAllocation() {
		OrderRow orderrow = new OrderRow();
		orderrow.setAllocated();
		assertFalse(orderrow.isEditable());
	}
	
	/**
	 * Test order row should be not editable
	 * because it is completed
	 */
	@Test
	public void testIsEditableNotEditableComplete() {
		OrderRow orderrow = new OrderRow();
		orderrow.setCompleted();
		assertFalse(orderrow.isEditable());
	}
	
	/**
	 * Test order row should be editable
	 */
	@Test
	public void testIsEditable() {
		OrderRow orderrow = new OrderRow();
		assertTrue(orderrow.isEditable());
	}
	
	/**
	 * Test order row should be not complete
	 * because order is missing
	 */
	@Test
	public void testIsDataCompleteNoOrder() {
		OrderRow orderrow = new OrderRow();
		assertFalse(orderrow.isDataComplete());
	}
	
	/**
	 * Test order row should be not complete
	 * because material is missing
	 */
	@Test
	public void testIsDataCompleteNoMaterial() {
		OrderRow orderrow = new OrderRow();
		orderrow.order = mockOrder;
		assertFalse(orderrow.isDataComplete());
	}
	
	/**
	 * Test order row should be not complete
	 * because quantity is invalid
	 */
	@Test
	public void testIsDataCompleteQuantityNotValid() {
		OrderRow orderrow = new OrderRow();
		orderrow.order = mockOrder;
		orderrow.material = mockMaterial;
		orderrow.quantity = -1;
		assertFalse(orderrow.isDataComplete());
	}
	
	/**
	 * Test order row should be complete
	 */
	@Test
	public void testIsDataComplete() {
		OrderRow orderrow = new OrderRow();
		orderrow.order = mockOrder;
		orderrow.material = mockMaterial;
		orderrow.quantity = 100;
		assertTrue(orderrow.isDataComplete());
	}
	
	/**
	 * Test order rows should be not equals
	 */
	@Test
	public void testNotEquals() {
		OrderRow orderrow1 = new OrderRow();
		orderrow1.id=10l;
		OrderRow orderrow2 = new OrderRow();
		orderrow2.id=11l;
		assertFalse(orderrow1.equals(orderrow2));
	}
	
	/**
	 * Test order rows should be equals
	 */
	@Test
	public void testEquals() {
		OrderRow orderrow1 = new OrderRow();
		orderrow1.id=10l;
		OrderRow orderrow2 = new OrderRow();
		orderrow2.id=10l;
		assertTrue(orderrow1.equals(orderrow2));
	}
}
