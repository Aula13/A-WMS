package org.wms.controller.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.config.Utils;
import org.wms.model.common.ListType;
import org.wms.model.material.Materials;
import org.wms.model.order.Order;
import org.wms.model.order.Orders;
import org.wms.test.TestUtils;
import org.wms.view.common.MessageBox;
import org.wms.view.order.OrdersView;

/**
 * Test orders view controller class
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrdersViewControllerUnitTest {
	
	private static Orders mockOrdersModel;
	private static Materials mockMaterialsModel;
	
	private static OrdersView mockView;
	
	private static JButton mockBtnAddOrder;
	private static JButton mockBtnEditOrder;
	private static JButton mockBtnDeleteOrder;
	
	private static JTable mockOrdersTable;
	
	private static Order mockOrder;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TestUtils.mockMessageBox();
		
		mockView = mock(OrdersView.class);
		
		mockBtnAddOrder = new JButton();
		mockBtnEditOrder = new JButton();
		mockBtnDeleteOrder = new JButton();
		
		doReturn(mockBtnAddOrder).when(mockView).getBtnAddOrder();
		doReturn(mockBtnEditOrder).when(mockView).getBtnEditOrder();
		doReturn(mockBtnDeleteOrder).when(mockView).getBtnDeleteOrder();
		
		doReturn(ListType.INPUT).when(mockView).getOrdersType();
		
		mockOrdersTable = mock(JTable.class);
		doReturn(mockOrdersTable).when(mockView).getOrdersTable();
		
		mockOrder = mock(Order.class);
		doReturn(ListType.INPUT).when(mockOrder).getType();
		
		List<Order> orders = new ArrayList<>();
		orders.add(mockOrder);
		
		mockOrdersModel = mock(Orders.class);
		mockMaterialsModel = mock(Materials.class);
		
		doReturn(orders).when(mockOrdersModel).getUnmodificableOrderList(ListType.INPUT);
		doReturn(new ArrayList<>()).when(mockMaterialsModel).getUnmodificableMaterialList();
	}

	
	/**
	 * Test edit and delete order button should be
	 * not visible at controller start
	 */
	@Test
	public void testOnStartEditDeleteButtonNotVisible() {
		new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		assertFalse(mockBtnEditOrder.isVisible());
		assertFalse(mockBtnDeleteOrder.isVisible());
	}
	
	/**
	 * Test dialog should be visible
	 */
	@Test
	public void testLaunchOrderEditView() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				controller.launchOrderEditView(new Order(), false);
			}
		});
		try {
			Thread.sleep(2000); //This allow edit screen to be created before test
			assertTrue(controller.editOrderDialog.isVisible());
			controller.editOrderDialog.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Error during thread sleep");
		}
	}

	/**
	 * Test order details view should be
	 * showed in new order mode
	 */
	@Test
	public void testBtnAddOrderAction() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				controller.btnAddOrderAction();
			}
		});
		
		try {
			Thread.sleep(2000); //This allow edit screen to be created before test
			assertTrue(controller.editOrderDialog.isVisible());
			assertTrue(controller.editOrderDialog.isNew());
			controller.editOrderDialog.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Error during thread sleep");
		}
	}

	/**
	 * Test the order detail view is not showed if
	 * the order is not selected in the table
	 */
	@Test
	public void testBtnEditOrderActionFail() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		doReturn(-1).when(mockOrdersTable).getSelectedRow();
		
		assertFalse(controller.btnEditOrderAction());
	}
	
	/**
	 * Test order details view should be
	 * showed in edit order mode
	 */
	@Test
	public void testBtnEditOrderAction() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		doReturn(0).when(mockOrdersTable).getSelectedRow();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				controller.btnEditOrderAction();
			}
		});
		try {
			Thread.sleep(2000); //This allow edit screen to be created before test
			assertTrue(controller.editOrderDialog.isVisible());
			assertFalse(controller.editOrderDialog.isNew());
			controller.editOrderDialog.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Error during thread sleep");
		}
	}

	/**
	 * Test delete order should be not 
	 * possible without order selected
	 */
	@Test
	public void testBtnDeleteOrderActionNoOrderSelected() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		doReturn(-1).when(mockOrdersTable).getSelectedRow();
		
		assertFalse(controller.btnDeleteOrderAction());
	}
	
	/**
	 * Test delete order should be not 
	 * possible if the order is not editable
	 */
	@Test
	public void testBtnDeleteOrderActionNoOrderNotEditable() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		doReturn(0).when(mockOrdersTable).getSelectedRow();
		
		doReturn(false).when(mockOrder).isEditable();
		
		assertFalse(controller.btnDeleteOrderAction());
	}
	
	/**
	 * Test delete order should be not 
	 * possible if the order can't be deleted
	 */
	@Test
	public void testBtnDeleteOrderActionOrderCantBeDeleted() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		doReturn(0).when(mockOrdersTable).getSelectedRow();
		
		doReturn(true).when(mockOrder).isEditable();
		doReturn(false).when(mockOrder).canDelete();
		
		assertFalse(controller.btnDeleteOrderAction());
	}
	
	/**
	 * Test delete order should be not 
	 * possible if the order can't be deleted
	 */
	@Test
	public void testBtnDeleteOrderActionOrderErrorDuringDelete() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		doReturn(0).when(mockOrdersTable).getSelectedRow();
		
		doReturn(true).when(mockOrder).isEditable();
		doReturn(true).when(mockOrder).canDelete();
		
		doReturn(false).when(mockOrdersModel).deleteOrder(mockOrder);
		
		assertFalse(controller.btnDeleteOrderAction());
	}
	
	/**
	 * Test delete order should be possible
	 * and user confirm the operation
	 */
	@Test
	public void testBtnDeleteOrderActionConfirmed() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		doReturn(0).when(mockOrdersTable).getSelectedRow();
		
		doReturn(true).when(mockOrder).isEditable();
		doReturn(true).when(mockOrder).canDelete();
		
		doReturn(true).when(mockOrdersModel).deleteOrder(mockOrder);
		
		doReturn(0).when(TestUtils.mockMsgBox).questionBox(anyString(), anyString());
		
		assertTrue(controller.btnDeleteOrderAction());
	}
	
	/**
	 * Test delete order should be possible
	 * and user abort the operation
	 */
	@Test
	public void testBtnDeleteOrderActionAbort() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		doReturn(0).when(mockOrdersTable).getSelectedRow();
		
		doReturn(true).when(mockOrder).isEditable();
		doReturn(true).when(mockOrder).canDelete();
		
		doReturn(true).when(mockOrdersModel).deleteOrder(mockOrder);
		
		doReturn(1).when(TestUtils.mockMsgBox).questionBox(anyString(), anyString());
		
		assertFalse(controller.btnDeleteOrderAction());
	}

	/**
	 * Test edit/delete buttons should be visible
	 * and order view should be automatically
	 * showed after double click
	 */
	@Test
	public void testTblOrdersValidSelectionActionDoubleClick() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				controller.tblOrdersValidSelectionAction(true, 0);
			}
		});
		
		try {
			Thread.sleep(2000); //This allow edit screen to be created before test
			assertTrue(controller.editOrderDialog.isVisible());
			assertFalse(controller.editOrderDialog.isNew());
			assertTrue(mockView.getBtnEditOrder().isVisible());
			assertTrue(mockView.getBtnDeleteOrder().isVisible());
			controller.editOrderDialog.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Error during thread sleep");
		}
	}
	
	/**
	 * Test edit/delete buttons should be visible
	 * and order view should be not automatically
	 * showed without double click
	 */
	@Test
	public void testTblOrdersValidSelectionActionNoDoubleClick() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				controller.tblOrdersValidSelectionAction(false, 0);
			}
		});
		
		try {
			Thread.sleep(2000); //This allow edit screen to be created before test
			assertTrue(controller.editOrderDialog==null);
			assertTrue(mockView.getBtnEditOrder().isVisible());
			assertTrue(mockView.getBtnDeleteOrder().isVisible());
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Error during thread sleep");
		}
	}

	/**
	 * Test edit and delete buttons should be
	 * not visible if the table selection is not valid
	 */
	@Test
	public void testTblOrdersInvalidSelectionAction() {
		OrdersViewController controller = new OrdersViewController(mockView, mockOrdersModel, mockMaterialsModel);
		
		mockBtnEditOrder.setVisible(false);
		mockBtnDeleteOrder.setVisible(false);
		
		controller.tblOrdersInvalidSelectionAction();
		
		assertFalse(mockView.getBtnEditOrder().isVisible());
		assertFalse(mockView.getBtnDeleteOrder().isVisible());
	}

}
