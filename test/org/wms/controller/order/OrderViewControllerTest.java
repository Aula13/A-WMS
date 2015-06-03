package org.wms.controller.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.sql.Date;

import javax.swing.JButton;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.order.Order;
import org.wms.model.order.Orders;
import org.wms.model.order.Priority;
import org.wms.view.order.OrderView;

public class OrderViewControllerTest {

	private static OrderView mockOrderView;
	
	private static Order mockOrder;
	
	private static Orders mockOrders;
	
	private static JButton btnConfirm;
	
	private static JButton btnRemove;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockOrderView = mock(OrderView.class);
		mockOrder = mock(Order.class);
		mockOrders = mock(Orders.class);
		
		doReturn(100l).when(mockOrderView).getSelectedId();
		doReturn(new Date(2015, 01, 01)).when(mockOrderView).getSelectedEmissionDate();
		doReturn(Priority.MEDIUM).when(mockOrderView).getSelectedPriority();
		
		btnConfirm = new JButton();
		btnRemove = new JButton();
		
		doReturn(btnConfirm).when(mockOrderView).getConfirmButton();
		doReturn(btnRemove).when(mockOrderView).getCancelButton();
	}

	/**
	 * Test constructor should init
	 * correctly other object instances
	 */
	@Test
	public void testOrderViewController() {
		OrderViewController controller = new OrderViewController(mockOrderView, 
				mockOrder, 
				mockOrders, 
				true);
		assertSame(mockOrderView, controller.view);
		assertSame(mockOrder, controller.order);
		assertSame(mockOrders, controller.ordersModel);
		assertSame(true, controller.isNew);
	}

	/**
	 * Test create new order should fail
	 * because order data is incomplete
	 */
	@Test
	public void testBtnConfirmButtonActionFailIncompleteData() {
		OrderViewController controller = new OrderViewController(mockOrderView, 
				mockOrder, 
				mockOrders, 
				true);
		doReturn(false).when(mockOrder).isDataComplete();
		assertFalse(controller.btnConfirmButtonAction());
	}
	
	/**
	 * Test create new order should fail
	 * because error during order saving process
	 */
	@Test
	public void testBtnConfirmButtonActionFailError() {
		OrderViewController controller = new OrderViewController(mockOrderView, 
				mockOrder, 
				mockOrders, 
				true);
		doReturn(true).when(mockOrder).isDataComplete();
		doReturn(false).when(mockOrders).addOrder(any(Order.class));
		assertFalse(controller.btnConfirmButtonAction());
	}
	
	/**
	 * Test the new order should be
	 * added correctly
	 */
	@Test
	public void testBtnConfirmButtonAction() {
		OrderViewController controller = new OrderViewController(mockOrderView, 
				mockOrder, 
				mockOrders, 
				true);
		doReturn(true).when(mockOrder).isDataComplete();
		doReturn(true).when(mockOrders).addOrder(any(Order.class));
		assertTrue(controller.btnConfirmButtonAction());
	}
	
	/**
	 * Test order should be
	 * updated correctly
	 */
	@Test
	public void testBtnConfirmButtonActionUpdate() {
		OrderViewController controller = new OrderViewController(mockOrderView, 
				mockOrder, 
				mockOrders, 
				false);
		doReturn(true).when(mockOrders).updateOrder(any(Order.class));
		assertTrue(controller.btnConfirmButtonAction());
	}
	
	/**
	 * Test cancel btn should dispose the window
	 */
	@Test
	public void  testBtnCancelButtonAction() {
		OrderViewController controller = new OrderViewController(mockOrderView, 
				mockOrder, 
				mockOrders, 
				true);
		controller.btnCancelButtonAction();
	}
	
}
