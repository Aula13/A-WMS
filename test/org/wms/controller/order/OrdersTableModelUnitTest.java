package org.wms.controller.order;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.order.Order;
import org.wms.model.order.OrderStatus;
import org.wms.model.order.OrderType;
import org.wms.model.order.Orders;
import org.wms.model.order.Priority;

/**
 * Test orders table model class
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrdersTableModelUnitTest {

	private static Orders mockOrdersModel;
	
	private static Order mockOrder;
	
	private static Date mockEmissionDate;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockOrdersModel = mock(Orders.class);
		mockOrder = mock(Order.class);
		
		mockEmissionDate = new Date();
		
		when(mockOrder.getId()).thenReturn(100l);
		when(mockOrder.getEmissionDate()).thenReturn(mockEmissionDate);
		when(mockOrder.getOrderStatus()).thenReturn(OrderStatus.WAITING);
		when(mockOrder.getPriority()).thenReturn(Priority.LOW);
		when(mockOrder.getAllocationPercentual()).thenReturn(0.0f);
		when(mockOrder.getCompletePercentual()).thenReturn(0.0f);
		
		List<Order> testList = new ArrayList<>();
		testList.add(mockOrder);
		
		when(mockOrdersModel.getUnmodificableOrderList(OrderType.INPUT))
			.thenReturn(testList);
	}

	/**
	 * Test initialization should be correct
	 */
	@Test
	public void testOrdersTableModel() {
		OrdersTableModel tableModel = new OrdersTableModel(mockOrdersModel, OrderType.INPUT);
		assertTrue(tableModel.ordersModel.equals(mockOrdersModel));
		assertTrue(tableModel.orderType==OrderType.INPUT);
	}

	/**
	 * Test row count should be 1
	 */
	@Test
	public void testGetRowCount() {
		OrdersTableModel tableModel = new OrdersTableModel(mockOrdersModel, OrderType.INPUT);
		
		assertTrue(tableModel.getRowCount()==1);
	}

	/**
	 * Test column count should be 6
	 */
	@Test
	public void testGetColumnCount() {
		OrdersTableModel tableModel = new OrdersTableModel(mockOrdersModel, OrderType.INPUT);
		
		assertTrue(tableModel.getColumnCount()==6);
	}

	/**
	 * Test count name should be the same as the
	 * headers array definition
	 */
	@Test
	public void testGetColumnNameInt() {
		OrdersTableModel tableModel = new OrdersTableModel(mockOrdersModel, OrderType.INPUT);
		
		assertTrue(tableModel.getColumnName(0).compareTo(tableModel.headers[0])==0);
		assertTrue(tableModel.getColumnName(1).compareTo(tableModel.headers[1])==0);
		assertTrue(tableModel.getColumnName(2).compareTo(tableModel.headers[2])==0);
		assertTrue(tableModel.getColumnName(3).compareTo(tableModel.headers[3])==0);
		assertTrue(tableModel.getColumnName(4).compareTo(tableModel.headers[4])==0);
		assertTrue(tableModel.getColumnName(5).compareTo(tableModel.headers[5])==0);
	}

	/**
	 * The value provided should be the data
	 * stored in the test order
	 * without call getRowCount (cache orders)
	 */
	@Test
	public void testNoCacheGetValueAt() {
		OrdersTableModel tableModel = new OrdersTableModel(mockOrdersModel, OrderType.INPUT);
		
		assertTrue(tableModel.orders==null);
		
		assertTrue(((long) tableModel.getValueAt(0, 0))==OrdersTableModelUnitTest.mockOrder.getId());
		assertTrue(((Date) tableModel.getValueAt(0, 1))
				.compareTo(OrdersTableModelUnitTest.mockOrder.getEmissionDate())==0);
		
		assertTrue(((String) tableModel.getValueAt(0, 2))
				.compareTo(OrdersTableModelUnitTest.mockOrder.getPriority().name())==0);
		
		assertTrue(((String) tableModel.getValueAt(0, 3))
				.compareTo(OrdersTableModelUnitTest.mockOrder.getOrderStatus().name())==0);
		
		assertTrue(((float) tableModel.getValueAt(0, 4))==OrdersTableModelUnitTest.mockOrder.getAllocationPercentual());
		assertTrue(((float) tableModel.getValueAt(0, 5))==OrdersTableModelUnitTest.mockOrder.getCompletePercentual());
	}
	
	/**
	 * The value provided should be the data
	 * stored in the test order
	 * with cache value calling (cache orders)
	 */
	@Test
	public void testCachedGetValueAt() {
		OrdersTableModel tableModel = new OrdersTableModel(mockOrdersModel, OrderType.INPUT);
		tableModel.getRowCount();
		
		assertTrue(tableModel.orders!=null);
		
		assertTrue(((long) tableModel.getValueAt(0, 0))==OrdersTableModelUnitTest.mockOrder.getId());
		assertTrue(((Date) tableModel.getValueAt(0, 1))
				.compareTo(OrdersTableModelUnitTest.mockOrder.getEmissionDate())==0);
		
		assertTrue(((String) tableModel.getValueAt(0, 2))
				.compareTo(OrdersTableModelUnitTest.mockOrder.getPriority().name())==0);
		
		assertTrue(((String) tableModel.getValueAt(0, 3))
				.compareTo(OrdersTableModelUnitTest.mockOrder.getOrderStatus().name())==0);
		
		assertTrue(((float) tableModel.getValueAt(0, 4))==OrdersTableModelUnitTest.mockOrder.getAllocationPercentual());
		assertTrue(((float) tableModel.getValueAt(0, 5))==OrdersTableModelUnitTest.mockOrder.getCompletePercentual());
	}
	
	/**
	 * Test invalid get column index
	 */
	@Test
	public void testInvalidIndexGetValueAt() {
		OrdersTableModel tableModel = new OrdersTableModel(mockOrdersModel, OrderType.INPUT);
		
		assertTrue(((String) tableModel.getValueAt(0, tableModel.getColumnCount()+1))
				.compareTo("Unknow column: " + (tableModel.getColumnCount()+1))==0);
	}

}
