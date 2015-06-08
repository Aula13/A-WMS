package org.wms.model.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.common.ListType;
import org.wms.model.common.Priority;
import org.wms.model.common.Status;

/**
 * Order Unit Testing Class
 * 
 * @author Daniele Ciriello, Stefano Pessina
 *
 */
public class OrderUnitTest {
	
	private static OrderRow mockOrderRow;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockOrderRow = mock(OrderRow.class);
	}
	
	/**
	 * Test empty constructor
	 */
	@Test
	public void testOrderEmptyConstr() {
		Order order = new Order();
	}
	
	/**
	 * Test initialization should be correctly
	 */
	@Test
	public void testOrderConstr2() {
		Order order = new Order(10l);
		assertTrue(order.id==10l);
		assertTrue(order.orderStatus==Status.WAITING);
		assertNotNull(order.emissionDate);
		assertTrue(order.priority==Priority.LOW);
	}
	
	/**
	 * Test initialization should be correctly
	 */
	@Test
	public void testOrderConstr3() {
		Date date = new Date();
		Order order = new Order(10l, date, ListType.OUTPUT);
		assertTrue(order.id==10l);
		assertTrue(order.orderStatus==Status.WAITING);
		assertSame(date, order.emissionDate);
		assertTrue(order.priority==Priority.LOW);
		assertTrue(order.type==ListType.OUTPUT);
	}
	
	/**
	 * Test initialization should be correctly
	 */
	@Test
	public void testOrderConstr4() {
		Date date = new Date();
		Order order = new Order(10l, date, ListType.OUTPUT, Priority.MEDIUM);
		assertTrue(order.id==10l);
		assertTrue(order.orderStatus==Status.WAITING);
		assertSame(date, order.emissionDate);
		assertTrue(order.priority==Priority.MEDIUM);
		assertTrue(order.type==ListType.OUTPUT);
	}
	
	/**
	 * Test initialization should be correctly
	 */
	@Test
	public void testOrderConstr5() {
		Date date = new Date();
		List<OrderRow> list = new ArrayList<>();
		
		Order order = new Order(10l, date, ListType.OUTPUT, Priority.MEDIUM, list);
		assertTrue(order.id==10l);
		assertTrue(order.orderStatus==Status.WAITING);
		assertSame(date, order.emissionDate);
		assertTrue(order.priority==Priority.MEDIUM);
		assertTrue(order.type==ListType.OUTPUT);
		assertSame(list, order.rows);
	}

	/**
	 * Test id should be not assigned
	 * because it's already assigned
	 */
	@Test
	public void testSetIdFail() {
		Order order = new Order(10l);
		assertFalse(order.setId(15l));
	}
	
	/**
	 * Test id should be assigned
	 */
	@Test
	public void testSetId() {
		Order order = new Order(0l);
		assertTrue(order.setId(15l));
	}

	/**
	 * Test data should be incomplete
	 * because order id is not assigned
	 */
	@Test
	public void testIsDataCompleteFailForId() {
		Order order = new Order(0l);
		assertFalse(order.isDataComplete());
	}
	
	/**
	 * Test data should be incomplete
	 * because emission date is not assigned
	 */
	@Test
	public void testIsDataCompleteFailForEmissionDate() {
		Order order = new Order(10l);
		order.emissionDate = null;
		assertFalse(order.isDataComplete());
	}
	
	/**
	 * Test data should be complete
	 */
	@Test
	public void testIsDataComplete() {
		Order order = new Order(10l);
		assertTrue(order.isDataComplete());
	}
	
	/**
	 * Test emission date can't be changed
	 * because the order is not editable
	 */
	@Test
	public void testSetEmissionDateNotEditable() {
		Order order = new Order(10l);
		order.allocationPercentual=100.0f;
		assertFalse(order.setEmissionDate(new Date()));
	}
	
	/**
	 * Test emission date should be updated
	 */
	@Test
	public void testSetEmissionDate() {
		Order order = new Order(10l);
		Date emissionDate = new Date();
		assertTrue(order.setEmissionDate(emissionDate));
		assertSame(emissionDate, order.emissionDate);
	}
	
	/**
	 * Test order type getter
	 */
	@Test
	public void testGetType() {
		Order order = new Order();
		order.type = ListType.INPUT;
		assertSame(ListType.INPUT, order.getType());
	}
	
	/**
	 * Test priority can't be changed
	 * because the order is not editable
	 */
	@Test
	public void testSetPriorityNotEditable() {
		Order order = new Order(10l);
		order.allocationPercentual=100.0f;
		assertFalse(order.setPriority(Priority.LOW));
	}
	
	/**
	 * Test priority should be updated
	 */
	@Test
	public void testSetPriority() {
		Order order = new Order(10l);
		assertTrue(order.setPriority(Priority.MEDIUM));
		assertSame(Priority.MEDIUM, order.priority);
	}
	
	/**
	 * Test material should be not added
	 * because the order is not editable
	 */
	@Test
	public void testAddMaterialNotEditable() {
		Order order = new Order(10l);
		order.completePercentual = 100.0f;
		doReturn(true).when(mockOrderRow).isDataComplete();
		assertFalse(order.addMaterial(mockOrderRow));
	}
	
//	/**
//	 * Test material should be not added
//	 * because the order row data is not 
//	 * complete
//	 */
//	@Test
//	public void testAddMaterialNotComplete() {
//		Order order = new Order(10l);
//		doReturn(false).when(mockOrderRow).isDataComplete();
//		assertFalse(order.addMaterial(mockOrderRow));
//	}
	
	/**
	 * Test material should be not added
	 * because the order row data is not 
	 * complete
	 */
	@Test
	public void testAddMaterial() {
		Order order = new Order(10l);
		doReturn(true).when(mockOrderRow).isDataComplete();
		assertTrue(order.addMaterial(mockOrderRow));
		assertTrue(order.rows.size()==1);
	}
	
	/**
	 * Test material should be not removed
	 * because the order is not editable
	 */
	@Test
	public void testRemoveMaterialNotEditable() {
		Order order = new Order(10l);
		order.completePercentual = 100.0f;
		assertFalse(order.removeMaterial(mockOrderRow));
	}
	
	/**
	 * Test material should be not removed
	 * because the order doesn't contain
	 * the material
	 */
	@Test
	public void testRemoveMaterialNotExist() {
		Order order = new Order(10l);
		assertFalse(order.removeMaterial(mockOrderRow));
	}
	
	/**
	 * Test material should be removed
	 */
	@Test
	public void testRemoveMaterial() {
		Order order = new Order(10l);
		order.rows.add(mockOrderRow);
		assertTrue(order.removeMaterial(mockOrderRow));
		assertTrue(order.rows.size()==0);
	}
	
	/**
	 * Test order status should be correctly returned
	 */
	@Test
	public void testGetOrderStatus() {
		Order order = new Order();
		assertSame(Status.WAITING, order.getOrderStatus());
	}
	
	/**
	 * Test done date should be correctly returned
	 */
	@Test
	public void testGetDoneDate() {
		Order order = new Order();
		Date doneDate = new Date();
		order.doneDate = doneDate;
		assertSame(doneDate, order.getDoneDate());
	}
	
	//TODO: Leave test setMaterialAsAllocate and setMaterialsAsComplete undone, wait for it use in the next sprint
	
	/**
	 * Test allocate percentual should be 0
	 * because no materials are present
	 * in the order rows
	 */
	@Test
	public void testUpdateAllocatePercentualAs0() {
		Order order = new Order();
		order.updateAllocatedPercentual();
		assertTrue(order.getAllocationPercentual()==0.0f);
	}
	
	/**
	 * Test allocate percentual should be 50.0
	 * because no materials are present
	 * in the order rows
	 */
	@Test
	public void testUpdateAllocatePercentualAs50() {
		Order order = new Order();
		order.rows.add(mockOrderRow);
		doReturn(true).when(mockOrderRow).isAllocated();
		
		OrderRow mockOrderRow2 = mock(OrderRow.class);
		doReturn(false).when(mockOrderRow2).isAllocated();
		order.rows.add(mockOrderRow2);
		
		order.updateAllocatedPercentual();
		assertTrue(order.getAllocationPercentual()==50.0f);
	}
	
	/**
	 * Test allocate percentual should be 100.0
	 * because no materials are present
	 * in the order rows
	 */
	@Test
	public void testUpdateAllocatePercentualAs100() {
		Order order = new Order();
		order.rows.add(mockOrderRow);
		doReturn(true).when(mockOrderRow).isAllocated();
		
		OrderRow mockOrderRow2 = mock(OrderRow.class);
		doReturn(true).when(mockOrderRow2).isAllocated();
		order.rows.add(mockOrderRow2);
		
		order.updateAllocatedPercentual();
		assertTrue(order.getAllocationPercentual()==100.0f);
	}
	
	/**
	 * Test complete percentual should be 0
	 * because no materials are present
	 * in the order rows
	 */
	@Test
	public void testUpdateCompletePercentualAs0() {
		Order order = new Order();
		order.updateCompletedPercentual();
		assertTrue(order.getCompletePercentual()==0.0f);
	}
	
	/**
	 * Test complete percentual should be 50.0
	 * because no materials are present
	 * in the order rows
	 */
	@Test
	public void testUpdateCompletePercentualAs50() {
		Order order = new Order();
		order.rows.add(mockOrderRow);
		doReturn(true).when(mockOrderRow).isCompleted();
		
		OrderRow mockOrderRow2 = mock(OrderRow.class);
		doReturn(false).when(mockOrderRow2).isCompleted();
		order.rows.add(mockOrderRow2);
		
		order.updateCompletedPercentual();
		assertTrue(order.getCompletePercentual()==50.0f);
	}
	
	/**
	 * Test complete percentual should be 100.0
	 * because no materials are present
	 * in the order rows
	 */
	@Test
	public void testUpdateCompletePercentualAs100() {
		Order order = new Order();
		order.rows.add(mockOrderRow);
		doReturn(true).when(mockOrderRow).isCompleted();
		
		OrderRow mockOrderRow2 = mock(OrderRow.class);
		doReturn(true).when(mockOrderRow2).isCompleted();
		order.rows.add(mockOrderRow2);
		
		order.updateCompletedPercentual();
		assertTrue(order.getCompletePercentual()==100.0f);
	}
	
	/**
	 * Test can delete because allocation percentual is 0
	 * 
	 */
	@Test
	public void testCanDeletePerc0() {
		Order order = new Order();
		order.allocationPercentual=0.0f;
		order.completePercentual=0.0f;
		assertTrue(order.canDelete());
	}
	
	/**
	 * Test can't delete because allocation percentual 
	 * greater than 0
	 */
	@Test
	public void testCanDeleteAllocationPerc() {
		Order order = new Order();
		order.allocationPercentual=1.0f;
		order.completePercentual=0.0f;
		assertFalse(order.canDelete());
	}
	
	/**
	 * Test can't delete because complete percentual 
	 * greater than 0
	 */
	@Test
	public void testCanDeleteCompletePerc() {
		Order order = new Order();
		order.allocationPercentual=0.0f;
		order.completePercentual=1.0f;
		assertFalse(order.canDelete());
	}
}
