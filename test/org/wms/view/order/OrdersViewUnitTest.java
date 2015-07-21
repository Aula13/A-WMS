package org.wms.view.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.jx.ConcreteJXAppStyleFactory;
import it.rmautomazioni.view.factories.swing.ConcreteButtonFactory;
import it.rmautomazioni.view.factories.swing.ConcreteFieldFactory;
import it.rmautomazioni.view.factories.swing.ConcretePanelFactory;

import javax.swing.JButton;
import javax.swing.JTable;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.config.ResourceUtil;
import org.wms.main.WMS;
import org.wms.model.batch.Batches;
import org.wms.model.common.ListType;
import org.wms.model.order.Orders;

/**
 * Test orders view
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrdersViewUnitTest {

	private static OrdersView view;
	
	private static Orders mockOrdersModel;
	
	private static Batches mockBatchesModel;
	
	private static JTable mockOrdersTable;
	
	private static JButton btnEditOrder;
	
	private static JButton btnDeleteOrder;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockOrdersTable = mock(JTable.class);
		mockOrdersModel = mock(Orders.class);
		mockBatchesModel = mock(Batches.class);
		
		WMS.initFactories();
		
		view = new OrdersView(mockOrdersModel, mockBatchesModel, ListType.INPUT);
		view.ordersTable = mockOrdersTable;
		
		btnEditOrder = new JButton();
		btnDeleteOrder = new JButton();
		
		view.btnEditOrder = btnEditOrder;
		view.btnDeleteOrder = btnDeleteOrder;
	}

	/**
	 * Test edit and delete buttons should be
	 * not visible, with no valid row selected
	 */
	@Test
	public void testUpdateValueNoSelection() {
		doReturn(-1).when(mockOrdersTable).getSelectedRow();
		view.update(null, null);
		assertFalse(btnEditOrder.isVisible());
		assertFalse(btnDeleteOrder.isVisible());
	}
	
	/**
	 * Test edit and delete buttons should be
	 * visible, with a valid row selected
	 */
	@Test
	public void testUpdateValueSelection() {
		doReturn(0).when(mockOrdersTable).getSelectedRow();
		view.update(null, null);
		assertTrue(btnEditOrder.isVisible());
		assertTrue(btnDeleteOrder.isVisible());
	}
}
