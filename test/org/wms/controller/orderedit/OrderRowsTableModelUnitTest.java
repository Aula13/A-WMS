package org.wms.controller.orderedit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.order.Material;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;
import org.wms.model.order.OrderStatus;
import org.wms.model.order.OrderType;
import org.wms.model.order.Orders;
import org.wms.model.order.Priority;

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

}
