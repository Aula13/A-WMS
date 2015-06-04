package org.wms.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.Test;
import org.wms.model.common.ICRUDLayer;
import org.wms.model.common.ListType;

/**
 * Test Orders model class
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrdersUnitTest {

	private static Orders orders;
	
	private static Semaphore mockSemaphore;
	
	private static ICRUDLayer<Order> mockPersistenceInterface;
	
	private static Order mockOrder;
	
	private static Observer observer;
	
	private static boolean isUpdated;
	
	@Before
	public void setUpBefore() throws Exception {
		mockPersistenceInterface = mock(ICRUDLayer.class);
		mockOrder = mock(Order.class);
		
		doReturn(1l).when(mockOrder).getId();
		
		observer = new Observer() {
			
			@Override
			public void update(Observable o, Object arg) {
				isUpdated = true;
			}
		};
		
		doReturn(true).when(mockPersistenceInterface).create(any(Order.class));
		doReturn(true).when(mockPersistenceInterface).delete(any(Order.class));
		doReturn(true).when(mockPersistenceInterface).update(any(Order.class));
		doReturn(Optional.of(mockOrder)).when(mockPersistenceInterface).get(any(Long.class));
		
		List<Order> materialList = new ArrayList<>();
		materialList.add(mockOrder);
		
		doReturn(Optional.of(materialList)).when(mockPersistenceInterface).selectAll();
		
		orders = new Orders(mockPersistenceInterface);
		orders.addObserver(observer);
		mockSemaphore = mock(Semaphore.class);
		orders.semaphore = mockSemaphore;
		
		isUpdated = false;
	}

	/**
	 * Test order to add should be already
	 * present
	 */
	@Test
	public void testAddOrderAlreadyPresent() {
		assertFalse(orders.addOrder(mockOrder));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test order should be not added
	 * for semaphore interruption exception
	 */
	@Test
	public void testAddOrderException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(orders.addOrder(mockOrder));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test order should be added correctly
	 */
	@Test
	public void testAddOrder() {
		isUpdated = false;
		doReturn(Optional.empty()).when(mockPersistenceInterface).get(any(Long.class));
		assertTrue(orders.addOrder(mockOrder));
		assertTrue(isUpdated);
	}

	/**
	 * Test order should be not present
	 * and delete operation should fail
	 */
	@Test
	public void testDeleteOrderNotPresent() {
		isUpdated = false;
		doReturn(Optional.empty()).when(mockPersistenceInterface).get(any(Long.class));
		assertFalse(orders.deleteOrder(mockOrder));
		assertFalse(isUpdated);
	}

	/**
	 * Test order should be not deleted
	 * for semaphore interruption exception
	 */
	@Test
	public void testDeleteOrderException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(orders.deleteOrder(mockOrder));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test order should be deleted
	 * correctly
	 */
	@Test
	public void testDeleteOrder() {
		assertTrue(orders.deleteOrder(mockOrder));
		assertTrue(isUpdated);
	}

	/**
	 * Test order should be not updated
	 * because it's not present 
	 */
	@Test
	public void testUpdateOrderNotPresent() {
		doReturn(Optional.empty()).when(mockPersistenceInterface).get(any(Long.class));
		assertFalse(orders.updateOrder(mockOrder));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test order should be not updated
	 * for semaphore interruption exception
	 */
	@Test
	public void testUpdateOrderException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(orders.updateOrder(mockOrder));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test order should be updated correctly
	 */
	@Test
	public void testUpdateOrder() {
		assertTrue(orders.updateOrder(mockOrder));
		assertTrue(isUpdated);
	}
	
	/**
	 * Test order should be not fetched
	 * because it's not present
	 */
	@Test
	public void testGetOrderNotPresent() {
		doReturn(Optional.empty()).when(mockPersistenceInterface).get(any(Long.class));
		assertFalse(orders.get(1l).isPresent());
	}
	
	/**
	 * Test order should be not fetch
	 * for semaphore interruption exception
	 */
	@Test
	public void testGetOrderException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(orders.get(mockOrder.getId()).isPresent());
	}
	
	/**
	 * Test order should be returned correctly
	 */
	@Test
	public void testGetOrder() {
		assertTrue(orders.get(1l).isPresent());
	}
	
	/**
	 * Test orders should be not fetched
	 * for semaphore interruption exception
	 */
	@Test
	public void testSelectAllOrderException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(orders.getUnmodificableOrderList().isEmpty());
	}

	/**
	 * Test orders should be not fetched correctly
	 */
	@Test
	public void testGetUnmodificableOrderListFail() {
		doReturn(Optional.empty()).when(mockPersistenceInterface).selectAll();
		assertTrue(orders.getUnmodificableOrderList().isEmpty());
	}
	
	/**
	 * Test orders should be fetched correctly
	 */
	@Test
	public void testGetUnmodificableOrderList() {
		assertFalse(orders.getUnmodificableOrderList().isEmpty());
	}
	
	/**
	 * Test orders should be fetched correctly
	 * with one INPUT order
	 */
	@Test
	public void testGetUnmodificableOrderListByOrderType() {
		doReturn(ListType.INPUT).when(mockOrder).getType();
		assertFalse(orders.getUnmodificableOrderList(ListType.INPUT).isEmpty());
	}
	
	/**
	 * Test orders should be fetched correctly
	 * with no order 
	 */
	@Test
	public void testGetUnmodificableOrderListByOrderTypeEmpty() {
		doReturn(ListType.OUTPUT).when(mockOrder).getType();
		assertTrue(orders.getUnmodificableOrderList(ListType.INPUT).isEmpty());
	}

	/**
	 * Test message for log
	 * should be formatted correctly
	 */
	@Test
	public void testFormatLogMessage() {
		assertTrue(orders.formatLogMessage("TEST")
				.compareTo(Orders.class.getSimpleName() + " - TEST")==0);
	}
	
}
