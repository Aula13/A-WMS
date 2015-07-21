package org.wms.model.batch;

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
import org.wms.model.material.Material;
import org.wms.model.material.Materials;
import org.wms.model.order.Order;
import org.wms.model.order.Orders;
import org.wms.model.warehouse.Warehouse;

public class BatchesUnitTest {

	private static Batches batches;
	
	private static Semaphore mockSemaphore;
	
	private static ICRUDLayer<Batch> mockPersistenceInterface;
	
	private static Warehouse mockWarehouse;
	
	private static Orders mockOrders;
	
	private static Materials mockMaterials;
	
	private static Batch mockBatch;
	
	private static Observer observer;
	
	private static boolean isUpdated;
	
	@Before
	public void setUpBefore() throws Exception {
		mockPersistenceInterface = mock(ICRUDLayer.class);
		mockBatch = mock(Batch.class);
		mockWarehouse = mock(Warehouse.class);
		mockOrders = mock(Orders.class);
		mockMaterials = mock(Materials.class);
		
		
		doReturn(1l).when(mockBatch).getId();
		
		observer = new Observer() {
			
			@Override
			public void update(Observable o, Object arg) {
				isUpdated = true;
			}
		};
		
		doReturn(true).when(mockPersistenceInterface).create(any(Batch.class));
		doReturn(true).when(mockPersistenceInterface).delete(any(Batch.class));
		doReturn(true).when(mockPersistenceInterface).update(any(Batch.class));
		doReturn(Optional.of(mockBatch)).when(mockPersistenceInterface).get(any(Long.class));
		
		List<Batch> materialList = new ArrayList<>();
		materialList.add(mockBatch);
		
		doReturn(Optional.of(materialList)).when(mockPersistenceInterface).selectAll();
		
		batches = new Batches(
				mockOrders,
				mockWarehouse,
				mockMaterials,
				mockPersistenceInterface,
				new IBatchesCreatorStrategy() {
					
					@Override
					public List<Batch> computeListOfBatch(List<Batch> alreadyAllocatedBatches,
							List<Order> orders, Warehouse warehouse, List<Material> materials) {
						return new ArrayList<>();
					}
				});
		batches.addObserver(observer);
		mockSemaphore = mock(Semaphore.class);
		batches.semaphore = mockSemaphore;
		
		isUpdated = false;
	}

	/**
	 * Test batch to add should be already
	 * present
	 */
	@Test
	public void testAddBatchAlreadyPresent() {
		assertFalse(batches.addBatch(mockBatch));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test batch should be not added
	 * for semaphore interruption exception
	 */
	@Test
	public void testAddBatchException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(batches.addBatch(mockBatch));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test batch should be added correctly
	 */
	@Test
	public void testAddBatch() {
		isUpdated = false;
		doReturn(Optional.empty()).when(mockPersistenceInterface).get(any(Long.class));
		assertTrue(batches.addBatch(mockBatch));
		assertTrue(isUpdated);
	}

	/**
	 * Test batch should be not present
	 * and delete operation should fail
	 */
	@Test
	public void testDeleteBatchNotPresent() {
		isUpdated = false;
		doReturn(Optional.empty()).when(mockPersistenceInterface).get(any(Long.class));
		assertFalse(batches.deleteBatch(mockBatch));
		assertFalse(isUpdated);
	}

	/**
	 * Test batch should be not deleted
	 * for semaphore interruption exception
	 */
	@Test
	public void testDeleteBatchException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(batches.deleteBatch(mockBatch));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test batch should be deleted
	 * correctly
	 */
	@Test
	public void testDeleteBatch() {
		assertTrue(batches.deleteBatch(mockBatch));
		assertTrue(isUpdated);
	}

	/**
	 * Test batch should be not updated
	 * because it's not present 
	 */
	@Test
	public void testUpdateBatchNotPresent() {
		doReturn(Optional.empty()).when(mockPersistenceInterface).get(any(Long.class));
		assertFalse(batches.updateBatch(mockBatch));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test batch should be not updated
	 * for semaphore interruption exception
	 */
	@Test
	public void testUpdateBatchException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(batches.updateBatch(mockBatch));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test batch should be updated correctly
	 */
	@Test
	public void testUpdateBatch() {
		assertTrue(batches.updateBatch(mockBatch));
		assertTrue(isUpdated);
	}
	
	/**
	 * Test batch should be not fetched
	 * because it's not present
	 */
	@Test
	public void testGetBatchNotPresent() {
		doReturn(Optional.empty()).when(mockPersistenceInterface).get(any(Long.class));
		assertFalse(batches.get(1l).isPresent());
	}
	
	/**
	 * Test batch should be not fetch
	 * for semaphore interruption exception
	 */
	@Test
	public void testGetBatchException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(batches.get(mockBatch.getId()).isPresent());
	}
	
	/**
	 * Test batch should be returned correctly
	 */
	@Test
	public void testGetBatch() {
		assertTrue(batches.get(1l).isPresent());
	}
	
	/**
	 * Test batches should be not fetched
	 * for semaphore interruption exception
	 */
	@Test
	public void testSelectAllBatchException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(batches.getUnmodificableBatchList().isEmpty());
	}

	/**
	 * Test batches should be not fetched correctly
	 */
	@Test
	public void testGetUnmodificableBatchListFail() {
		doReturn(Optional.empty()).when(mockPersistenceInterface).selectAll();
		assertTrue(batches.getUnmodificableBatchList().isEmpty());
	}
	
	/**
	 * Test batches should be fetched correctly
	 */
	@Test
	public void testGetUnmodificableBatchList() {
		assertFalse(batches.getUnmodificableBatchList().isEmpty());
	}
	
	/**
	 * Test batches should be fetched correctly
	 * with one INPUT batch
	 */
	@Test
	public void testGetUnmodificableBatchListByBatchType() {
		doReturn(ListType.INPUT).when(mockBatch).getType();
		assertFalse(batches.getUnmodificableBatchList(ListType.INPUT).isEmpty());
	}
	
	/**
	 * Test batches should be fetched correctly
	 * with no batch 
	 */
	@Test
	public void testGetUnmodificableBatchListByBatchTypeEmpty() {
		doReturn(ListType.OUTPUT).when(mockBatch).getType();
		assertTrue(batches.getUnmodificableBatchList(ListType.INPUT).isEmpty());
	}

	/**
	 * Test message for log
	 * should be formatted correctly
	 */
	@Test
	public void testFormatLogMessage() {
		assertTrue(batches.formatLogMessage("TEST")
				.compareTo(Batches.class.getSimpleName() + " - TEST")==0);
	}

}
