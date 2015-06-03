package org.wms.model.material;

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
import org.wms.model.material.Material;
import org.wms.model.material.Materials;

/**
 * Test Materials class
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class MaterialsUnitTest {

	private static Materials materials;
	
	private static Semaphore mockSemaphore;
	
	private static ICRUDLayer<Material> mockPersistenceInterface;
	
	private static Material mockMaterial;
	
	private static Observer observer;
	
	private static boolean isUpdated;
	
	@Before
	public void setUpBefore() throws Exception {
		mockPersistenceInterface = mock(ICRUDLayer.class);
		mockMaterial = mock(Material.class);
		
		doReturn(1l).when(mockMaterial).getCode();
		
		observer = new Observer() {
			
			@Override
			public void update(Observable o, Object arg) {
				isUpdated = true;
			}
		};
		
		doReturn(true).when(mockPersistenceInterface).create(any(Material.class));
		doReturn(true).when(mockPersistenceInterface).delete(any(Material.class));
		doReturn(true).when(mockPersistenceInterface).update(any(Material.class));
		doReturn(Optional.of(mockMaterial)).when(mockPersistenceInterface).get(any(Long.class));
		
		List<Material> materialList = new ArrayList<>();
		materialList.add(mockMaterial);
		
		doReturn(Optional.of(materialList)).when(mockPersistenceInterface).selectAll();
		
		materials = new Materials(mockPersistenceInterface);
		materials.addObserver(observer);
		mockSemaphore = mock(Semaphore.class);
		materials.semaphore = mockSemaphore;
		
		isUpdated = false;
	}

	/**
	 * Test material to add should be already
	 * present
	 */
	@Test
	public void testAddMaterialAlreadyPresent() {
		assertFalse(materials.addMaterial(mockMaterial));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test material should be not added
	 * for semaphore interruption exception
	 */
	@Test
	public void testAddMaterialException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(materials.addMaterial(mockMaterial));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test material should be added correctly
	 */
	@Test
	public void testAddMaterial() {
		isUpdated = false;
		doReturn(Optional.empty()).when(mockPersistenceInterface).get(any(Long.class));
		assertTrue(materials.addMaterial(mockMaterial));
		assertTrue(isUpdated);
	}

	/**
	 * Test material should be not present
	 * and delete operation should fail
	 */
	@Test
	public void testDeleteMaterialNotPresent() {
		isUpdated = false;
		doReturn(Optional.empty()).when(mockPersistenceInterface).get(any(Long.class));
		assertFalse(materials.deleteMaterial(mockMaterial));
		assertFalse(isUpdated);
	}

	/**
	 * Test material should be not deleted
	 * for semaphore interruption exception
	 */
	@Test
	public void testDeleteMaterialException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(materials.deleteMaterial(mockMaterial));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test material should be deleted
	 * correctly
	 */
	@Test
	public void testDeleteMaterial() {
		assertTrue(materials.deleteMaterial(mockMaterial));
		assertTrue(isUpdated);
	}

	/**
	 * Test material should be not updated
	 * because it's not present 
	 */
	@Test
	public void testUpdateMaterialNotPresent() {
		doReturn(Optional.empty()).when(mockPersistenceInterface).get(any(Long.class));
		assertFalse(materials.updateMaterial(mockMaterial));
		assertFalse(isUpdated);
	}
	
	/**
	 * Test material should be not updated
	 * for semaphore interruption exception
	 */
	@Test
	public void testUpdateMaterialException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(materials.updateMaterial(mockMaterial));
		assertFalse(isUpdated);
	}
	
	@Test
	public void testUpdateMaterial() {
		assertTrue(materials.updateMaterial(mockMaterial));
		assertTrue(isUpdated);
	}
	
	/**
	 * Test material should be returned correctly
	 */
	@Test
	public void testGetMaterialNotPresent() {
		doReturn(Optional.empty()).when(mockPersistenceInterface).get(any(Long.class));
		assertFalse(materials.get(1l).isPresent());
	}
	
	/**
	 * Test material should be not fetch
	 * for semaphore interruption exception
	 */
	@Test
	public void testGetMaterialException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(materials.get(mockMaterial.getCode()).isPresent());
	}
	
	/**
	 * Test material should be returned correctly
	 */
	@Test
	public void testGetMaterial() {
		assertTrue(materials.get(1l).isPresent());
	}
	
	/**
	 * Test materials should be not fetched
	 * for semaphore interruption exception
	 */
	@Test
	public void testSelectAllMaterialException() {
		try {
			doThrow(InterruptedException.class).when(mockSemaphore).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(materials.getUnmodificableMaterialList().isEmpty());
	}

	/**
	 * Test materials should be not fetched correctly
	 */
	@Test
	public void testGetUnmodificableMaterialListFail() {
		doReturn(Optional.empty()).when(mockPersistenceInterface).selectAll();
		assertTrue(materials.getUnmodificableMaterialList().isEmpty());
	}
	
	/**
	 * Test materials should be fetched correctly
	 */
	@Test
	public void testGetUnmodificableMaterialList() {
		assertFalse(materials.getUnmodificableMaterialList().isEmpty());
	}

	/**
	 * Test message for log
	 * should be formatted correctly
	 */
	@Test
	public void testFormatLogMessage() {
		assertTrue(materials.formatLogMessage("TEST")
				.compareTo(Materials.class.getSimpleName() + " - TEST")==0);
	}
	
}
