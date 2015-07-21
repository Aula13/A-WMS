package org.wms.controller.warehouse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.controller.batch.BatchesViewController;
import org.wms.model.batch.Batch;
import org.wms.model.material.Material;
import org.wms.model.warehouse.WarehouseCell;
import org.wms.model.warehouse.WarehouseLine;
import org.wms.model.warehouse.WarehouseShelf;
import org.wms.test.TestUtils;
import org.wms.view.warehouse.WarehouseShelfView;

public class WarehouseShelfControllerUnitTest {

	private static WarehouseShelf mockShelf;
	
	private static WarehouseCell mockCell;
	
	private static WarehouseShelfView mockView;
	
	private static JTextField mockShelfCodeField;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TestUtils.mockMessageBox();
		TestUtils.initGUIFactories();
		
		mockShelf = mock(WarehouseShelf.class);
		mockView = mock(WarehouseShelfView.class);
		
		mockCell = mock(WarehouseCell.class);
		
		when(mockCell.getPublicId()).thenReturn("A/1/1");
		when(mockCell.getMaterial()).thenReturn(new Material(1000l));
		when(mockCell.getQuantity()).thenReturn(10);
		when(mockCell.getAlreadyReservedQuantity()).thenReturn(10);
		
		List<WarehouseCell> testList = new ArrayList<>();
		testList.add(mockCell);
		
		when(mockShelf.getUnmodificableListCells())
			.thenReturn(testList);
		
		mockShelfCodeField = new JTextField();
		
		when(mockShelf.getPublicId()).thenReturn("A/1");
		when(mockShelf.getId()).thenReturn(1l);
		when(mockShelf.getWarehouseLine()).thenReturn(new WarehouseLine(1, "A"));
		when(mockView.getShelfCodeField()).thenReturn(mockShelfCodeField);
	}

	/**
	 * Test references should be correctly initialized
	 */
	@Test
	public void testWarehouseShelfController() {
		WarehouseShelfController controller = new WarehouseShelfController(mockShelf, mockView);
		assertEquals(controller.shelf, mockShelf);
		assertEquals(controller.view, mockView);
	}

	/**
	 * Test open shelf details view
	 */
	@Test
	public void testOpenShelfDetailsView() {
		WarehouseShelfController controller = new WarehouseShelfController(mockShelf, mockView);
			
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				controller.openShelfDetailsView();
			}
		});
		try {
			Thread.sleep(2000); //This allow edit screen to be created before test
			assertTrue(controller.detailsView.isVisible());
			controller.detailsView.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Error during thread sleep");
		}
	}

}
