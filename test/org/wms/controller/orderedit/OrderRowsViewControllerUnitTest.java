package org.wms.controller.orderedit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.order.Material;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;
import org.wms.test.TestUtils;
import org.wms.view.orderedit.ComboBoxCellEditorCustom;
import org.wms.view.orderedit.OrderRowsView;
import org.wms.view.orderedit.SpinnerCellEditor;

public class OrderRowsViewControllerUnitTest {

	private static OrderRowsView mockOrderRowsView;
	
	private static Order mockOrder;
	
	private static Material mockMaterial;
	
	private static OrderRow mockOrderRow;
	
	private static List<Material> availableMaterials;
	
	private static JButton btnRemoveOrderRow;
	private static JButton btnAddOrderRow;
	
	private static JTable mockOrderRowsTable;
	private static ComboBoxCellEditorCustom mockCmbBoxCellEditor;
	private static SpinnerCellEditor mockSpnCellEditor;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TestUtils.mockMessageBox();
		
		mockOrderRowsView = mock(OrderRowsView.class);
		mockOrder = mock(Order.class);
		mockMaterial = mock(Material.class);
		
		availableMaterials = new ArrayList<>();
		availableMaterials.add(mockMaterial);
		
		mockOrderRow = mock(OrderRow.class);
		
		List<OrderRow> orderRows = new ArrayList<>();
		orderRows.add(mockOrderRow);
		
		doReturn(orderRows).when(mockOrder).getUnmodificableMaterials();
		
		btnAddOrderRow = new JButton();
		btnRemoveOrderRow = new JButton();
		doReturn(btnAddOrderRow).when(mockOrderRowsView).getBtnAddOrderRow();
		doReturn(btnRemoveOrderRow).when(mockOrderRowsView).getBtnRemoveOrderRow();
		
		mockOrderRowsTable = mock(JTable.class);
		doReturn(mockOrderRowsTable).when(mockOrderRowsView).getTblOrderRows();
		
		mockCmbBoxCellEditor = mock(ComboBoxCellEditorCustom.class);
		mockSpnCellEditor = mock(SpinnerCellEditor.class);
	}

	
	/**
	 * Test internal object initialization
	 */
	@Test
	public void testOrderRowsViewControllerConstructor() {
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		
	}
	
	@Test
	public void testTblOrderRowsValidSelectionAction() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testTblOrderRowsInvalidSelectionAction() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testTblOrderRowsComboEditStopAction() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testTblOrderRowsSpinnerEditStopAction() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testBtnAddOrderRowAction() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testBtnRemoveOrderRowAction() {
		fail("Not yet implemented"); // TODO
	}

}
