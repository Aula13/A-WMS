package org.wms.controller.batch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.config.Utils;
import org.wms.controller.order.OrdersViewController;
import org.wms.model.batch.Batch;
import org.wms.model.batch.Batches;
import org.wms.test.TestUtils;
import org.wms.view.batch.BatchesView;

public class BatchesViewControllerUnitTest {

	private static BatchesView mockBatchesView;
	
	private static Batches mockBatchesModel;
	
	private static Batch mockBatch;
	
	private static JButton mockBtnRefresh;
	private static JButton mockBtnMarkAsAllocated;
	private static JButton mockBtnPrint;
	private static JButton mockBtnMarkAsCompleted;
	
	private static JTable mockBatchesTable;
	
	@BeforeClass
	public static void setupBeforeClass() {
		TestUtils.mockMessageBox();
		
		mockBatchesView = mock(BatchesView.class);
		mockBatchesModel = mock(Batches.class);
		mockBatch = mock(Batch.class);
		
		
		mockBtnRefresh = new JButton();
		mockBtnMarkAsAllocated = new JButton();
		mockBtnPrint = new JButton();
		mockBtnMarkAsCompleted = new JButton();
		
		when(mockBatchesView.getBtnRefreshBatches()).thenReturn(mockBtnRefresh);
		when(mockBatchesView.getBtnMarkBatchAsAllocated()).thenReturn(mockBtnMarkAsAllocated);
		when(mockBatchesView.getBtnMarkBatchAsComplete()).thenReturn(mockBtnMarkAsCompleted);
		when(mockBatchesView.getBtnPrintBatch()).thenReturn(mockBtnPrint);
		
		List<Batch> testList = new ArrayList<>();
		testList.add(mockBatch);
		
		when(mockBatchesModel.getUnmodificableBatchList()).thenReturn(testList);
		
		mockBatchesTable = mock(JTable.class);
		when(mockBatchesView.getBatchesTable()).thenReturn(mockBatchesTable);
	}
	
	
	/**
	 * Test reference should be correctly initialized
	 */
	@Test
	public void testBatchesViewController() {
		BatchesViewController batchesViewController = new BatchesViewController(mockBatchesView, mockBatchesModel);
		assertEquals(batchesViewController.view, mockBatchesView);
		assertEquals(batchesViewController.batchesModel, mockBatchesModel);
	}
	
	/**
	 * Test buttons should be correctly hidden or visible
	 */
	@Test
	public void testOnStartButtonVisibility() {
		new BatchesViewController(mockBatchesView, mockBatchesModel);
		assertTrue(mockBtnRefresh.isVisible());
		assertFalse(mockBtnMarkAsAllocated.isVisible());
		assertFalse(mockBtnMarkAsCompleted.isVisible());
		assertFalse(mockBtnPrint.isVisible());
	}

	/**
	 * Test JDialog should be visible
	 * at launch batch view method call
	 */
	@Test
	public void testLaunchOrderEditView() {
		BatchesViewController batchesViewController = new BatchesViewController(mockBatchesView, mockBatchesModel);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				batchesViewController.launchBatchView(new Batch());
			}
		});
		try {
			Thread.sleep(2000); //This allow edit screen to be created before test
			assertTrue(batchesViewController.batchDialog.isVisible());
			batchesViewController.batchDialog.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Error during thread sleep");
		}
	}

	/**
	 * Test refresh action should be call
	 */
	@Test
	public void testBtnRefreshAction() {
		BatchesViewController batchesViewController = new BatchesViewController(mockBatchesView, mockBatchesModel);
		batchesViewController.btnRefreshAction();
	}

	/**
	 * Test allocate action
	 */
	@Test
	public void testBtnAllocateAction() {
		BatchesViewController batchesViewController = new BatchesViewController(mockBatchesView, mockBatchesModel);
		when(mockBatchesTable.getSelectedRow()).thenReturn(-1);
		assertFalse(batchesViewController.btnAllocateAction());
		
		when(mockBatchesTable.getSelectedRow()).thenReturn(0);
		when(mockBatchesModel.setBatchAsAllocate(mockBatch)).thenReturn(false);
		assertFalse(batchesViewController.btnAllocateAction());
		
		when(mockBatchesModel.setBatchAsAllocate(mockBatch)).thenReturn(true);
		assertTrue(batchesViewController.btnAllocateAction());
	}

	/**
	 * 
	 */
	@Test
	public void testBtnPrintAction() {
		//fail("Not yet implemented");
	}

	/**
	 * 
	 */
	@Test
	public void testBtnCompleteAction() {
		BatchesViewController batchesViewController = new BatchesViewController(mockBatchesView, mockBatchesModel);
		when(mockBatchesTable.getSelectedRow()).thenReturn(-1);
		assertFalse(batchesViewController.btnCompleteAction());
		
		when(mockBatchesTable.getSelectedRow()).thenReturn(0);
		when(mockBatchesModel.setBatchAsCompleted(mockBatch)).thenReturn(false);
		assertFalse(batchesViewController.btnCompleteAction());
		
		when(mockBatchesModel.setBatchAsCompleted(mockBatch)).thenReturn(true);
		assertTrue(batchesViewController.btnCompleteAction());
	}

	/**
	 * Test batch view should be open
	 */
	@Test
	public void testTblBatchesValidSelectionAction() {
		BatchesViewController controller = new BatchesViewController(mockBatchesView, mockBatchesModel);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				controller.tblBatchesValidSelectionAction(true, 0);
			}
		});
		
		try {
			Thread.sleep(2000); //This allow edit screen to be created before test
			assertTrue(controller.batchDialog.isVisible());
			assertTrue(mockBatchesView.getBtnMarkBatchAsAllocated().isVisible());
			assertTrue(mockBatchesView.getBtnMarkBatchAsComplete().isVisible());
			assertTrue(mockBatchesView.getBtnPrintBatch().isVisible());
			controller.batchDialog.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Error during thread sleep");
		}
	}
	
	/**
	 * Test buttons should be visible
	 * and batch view should be not automatically
	 * showed without double click
	 */
	@Test
	public void testTblBatchesValidSelectionActionNoDoubleClick() {
		BatchesViewController controller = new BatchesViewController(mockBatchesView, mockBatchesModel);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				controller.tblBatchesValidSelectionAction(false, 0);
			}
		});
		
		try {
			Thread.sleep(2000); //This allow edit screen to be created before test
			assertTrue(controller.batchDialog==null);
			assertTrue(mockBatchesView.getBtnMarkBatchAsAllocated().isVisible());
			assertTrue(mockBatchesView.getBtnMarkBatchAsComplete().isVisible());
			assertTrue(mockBatchesView.getBtnPrintBatch().isVisible());
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Error during thread sleep");
		}
	}

	/**
	 * Test buttons should be not visible
	 * if the selection is not valid
	 */
	@Test
	public void testTblBatchesInvalidSelectionAction() {
		BatchesViewController controller = new BatchesViewController(mockBatchesView, mockBatchesModel);
		
		mockBtnMarkAsAllocated.setVisible(true);
		mockBtnPrint.setVisible(true);
		mockBtnMarkAsCompleted.setVisible(true);
		
		controller.tblBatchesInvalidSelectionAction();
		
		assertFalse(mockBatchesView.getBtnMarkBatchAsAllocated().isVisible());
		assertFalse(mockBatchesView.getBtnPrintBatch().isVisible());
		assertFalse(mockBatchesView.getBtnMarkBatchAsComplete().isVisible());
	}

}
