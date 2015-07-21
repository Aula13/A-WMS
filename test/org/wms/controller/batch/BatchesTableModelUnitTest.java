package org.wms.controller.batch;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.batch.Batch;
import org.wms.model.batch.Batches;
import org.wms.model.common.ListType;
import org.wms.model.common.Priority;
import org.wms.model.common.Status;

public class BatchesTableModelUnitTest {
	
	private static Batches mockBatchesModel;
	
	private static Batch mockBatch;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockBatchesModel = mock(Batches.class);
		mockBatch = mock(Batch.class);
		
		when(mockBatch.getId()).thenReturn(100l);
		when(mockBatch.getType()).thenReturn(ListType.INPUT);
		when(mockBatch.getBatchStatus()).thenReturn(Status.WAITING);
		when(mockBatch.getPriority()).thenReturn(Priority.LOW);
		
		List<Batch> testList = new ArrayList<>();
		testList.add(mockBatch);
		
		when(mockBatchesModel.getUnmodificableBatchList())
			.thenReturn(testList);
	}
	
	/**
	 * Test initialization should be correct
	 */
	@Test
	public void testBathessTableModel() {
		BatchesTableModel tableModel = new BatchesTableModel(mockBatchesModel);
		assertTrue(tableModel.batchesModel.equals(mockBatchesModel));
	}

	/**
	 * Test row count should be 1
	 */
	@Test
	public void testGetRowCount() {
		BatchesTableModel tableModel = new BatchesTableModel(mockBatchesModel);		
		assertTrue(tableModel.getRowCount()==1);
	}

	/**
	 * Test column count should be 5
	 */
	@Test
	public void testGetColumnCount() {
		BatchesTableModel tableModel = new BatchesTableModel(mockBatchesModel);
		
		assertTrue(tableModel.getColumnCount()==5);
	}

	/**
	 * Test count name should be the same as the
	 * headers array definition
	 */
	@Test
	public void testGetColumnNameInt() {
		BatchesTableModel tableModel = new BatchesTableModel(mockBatchesModel);
		
		assertTrue(tableModel.getColumnName(0).compareTo(tableModel.headers[0])==0);
		assertTrue(tableModel.getColumnName(1).compareTo(tableModel.headers[1])==0);
		assertTrue(tableModel.getColumnName(2).compareTo(tableModel.headers[2])==0);
		assertTrue(tableModel.getColumnName(3).compareTo(tableModel.headers[3])==0);
		assertTrue(tableModel.getColumnName(4).compareTo(tableModel.headers[4])==0);
	}

	/**
	 * The value provided should be the data
	 * stored in the test batch
	 */
	@Test
	public void testGetValueAt() {
		BatchesTableModel tableModel = new BatchesTableModel(mockBatchesModel);
		tableModel.getRowCount();
		
		assertTrue(((long) tableModel.getValueAt(0, 0))==BatchesTableModelUnitTest.mockBatch.getId());
		assertTrue(((String) tableModel.getValueAt(0, 1))
				.compareTo(BatchesTableModelUnitTest.mockBatch.getType().name())==0);
		
		assertTrue(((String) tableModel.getValueAt(0, 2))
				.compareTo(BatchesTableModelUnitTest.mockBatch.getPriority().name())==0);
		
		assertTrue(((String) tableModel.getValueAt(0, 3))
				.compareTo(BatchesTableModelUnitTest.mockBatch.getBatchStatus().name())==0);
		
		assertTrue(((boolean) tableModel.getValueAt(0, 4))==BatchesTableModelUnitTest.mockBatch.isAllocated());
	}
	
	/**
	 * Test invalid get column index
	 */
	@Test
	public void testInvalidIndexGetValueAt() {
		BatchesTableModel tableModel = new BatchesTableModel(mockBatchesModel);
		
		assertTrue(((String) tableModel.getValueAt(0, tableModel.getColumnCount()+1))
				.compareTo("Unknow column: " + (tableModel.getColumnCount()+1))==0);
	}

}
