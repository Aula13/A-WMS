package org.wms.controller.order;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.common.ListType;
import org.wms.model.common.Priority;
import org.wms.model.common.Status;
import org.wms.model.material.Material;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;
import org.wms.model.order.Orders;

/**
 * Test order rows table model class
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrderRowsTableModelUnitTest {
	
	private static Order mockOrder;
	
	private static OrderRow mockOrderRow;
	
	private static Material mockMaterial;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockOrder = mock(Order.class);
		mockOrderRow = mock(OrderRow.class);
		mockMaterial = mock(Material.class);
		
		doReturn(100l).when(mockMaterial).getCode();
		
		doReturn(mockMaterial).when(mockOrderRow).getMaterial();
		doReturn(200).when(mockOrderRow).getQuantity();
		doReturn(true).when(mockOrderRow).isAllocated();
		doReturn(true).when(mockOrderRow).isCompleted();		
		
		List<OrderRow> orderRows = new ArrayList<>();
		orderRows.add(mockOrderRow);
		
		doReturn(orderRows).when(mockOrder).getUnmodificableMaterials();
	}

	/**
	 * Test initialization should be correct
	 */
	@Test
	public void testOrderRowsTableModel() {
		OrderRowsTableModel tableModel = new OrderRowsTableModel(mockOrder);
		assertTrue(tableModel.order.equals(mockOrder));
	}

	/**
	 * Test row count should be 1
	 */
	@Test
	public void testGetRowCount() {
		OrderRowsTableModel tableModel = new OrderRowsTableModel(mockOrder);
		
		assertTrue(tableModel.getRowCount()==1);
	}

	/**
	 * Test column count should be 4
	 */
	@Test
	public void testGetColumnCount() {
		OrderRowsTableModel tableModel = new OrderRowsTableModel(mockOrder);
		
		assertTrue(tableModel.getColumnCount()==4);
	}

	/**
	 * Test count name should be the same as the
	 * headers array definition
	 */
	@Test
	public void testGetColumnNameInt() {
		OrderRowsTableModel tableModel = new OrderRowsTableModel(mockOrder);
		
		assertTrue(tableModel.getColumnName(0).compareTo(tableModel.headers[0])==0);
		assertTrue(tableModel.getColumnName(1).compareTo(tableModel.headers[1])==0);
		assertTrue(tableModel.getColumnName(2).compareTo(tableModel.headers[2])==0);
		assertTrue(tableModel.getColumnName(3).compareTo(tableModel.headers[3])==0);
	}

	/**
	 * The value provided should be the data
	 * stored in the test order row
	 */
	@Test
	public void testNoCacheGetValueAt() {
		OrderRowsTableModel tableModel = new OrderRowsTableModel(mockOrder);
		
		assertTrue(((long) tableModel.getValueAt(0, 0))==mockOrderRow.getMaterial().getCode());
		assertTrue(((int) tableModel.getValueAt(0, 1))==mockOrderRow.getQuantity());
		assertTrue(((boolean) tableModel.getValueAt(0, 2))==mockOrderRow.isAllocated());	
		assertTrue(((boolean) tableModel.getValueAt(0, 3))==mockOrderRow.isCompleted());	
	}
	
	/**
	 * Test invalid get column index
	 */
	@Test
	public void testInvalidIndexGetValueAt() {
		OrderRowsTableModel tableModel = new OrderRowsTableModel(mockOrder);
		
		assertTrue(((String) tableModel.getValueAt(0, tableModel.getColumnCount()+1))
				.compareTo("Unknow column: " + (tableModel.getColumnCount()+1))==0);
	}
	
	/**
	 * Test table cell should be not editable
	 * because column index >2
	 */
	@Test
	public void testIsCellEditableFalse() {
		OrderRowsTableModel tableModel = new OrderRowsTableModel(mockOrder);
		
		assertFalse(tableModel.isCellEditable(0, 2));
		assertFalse(tableModel.isCellEditable(0, 3));
	}
	
	/**
	 * Test table cell should be not editable
	 * because column index <2 but
	 * order row isn't editable
	 */
	@Test
	public void testIsCellEditableOrderRowNotEditable() {
		OrderRowsTableModel tableModel = new OrderRowsTableModel(mockOrder);
		
		doReturn(false).when(mockOrderRow).isEditable();
		
		assertFalse(tableModel.isCellEditable(0, 0));
		assertFalse(tableModel.isCellEditable(0, 1));
	}
	
	/**
	 * Test table cell should be editable
	 */
	@Test
	public void testIsCellEditable() {
		OrderRowsTableModel tableModel = new OrderRowsTableModel(mockOrder);
		
		doReturn(true).when(mockOrderRow).isEditable();
		
		assertTrue(tableModel.isCellEditable(0, 0));
		assertTrue(tableModel.isCellEditable(0, 1));
	}
	
	

}
