package org.wms.controller.order;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.AEADBadTagException;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.config.Utils;
import org.wms.model.material.Material;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;
import org.wms.test.TestUtils;
import org.wms.view.common.ComboBoxCellEditorCustom;
import org.wms.view.common.SpinnerCellEditor;
import org.wms.view.order.OrderRowsView;

public class OrderRowsViewControllerUnitTest {

	private static OrderRowsView mockOrderRowsView;
	
	private static Order mockOrder;
	
	private static Material mockMaterial;
	
	private static OrderRow mockOrderRow;
	
	private static List<Material> availableMaterials;
	
	private static JButton btnRemoveOrderRow;
	private static JButton btnAddOrderRow;
	
	private static JTable mockOrderRowsTable;
	private static OrderRowsTableModel mockOrderRowsTblModel;
	
	private static ComboBoxCellEditorCustom mockCmbBoxCellEditor;
	private static SpinnerCellEditor mockSpnCellEditor;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TestUtils.mockMessageBox();
		
		mockOrderRowsView = mock(OrderRowsView.class);
		mockOrder = mock(Order.class);
		mockOrderRow = mock(OrderRow.class);
		
		List<OrderRow> orderRows = new ArrayList<>();
		orderRows.add(mockOrderRow);
		
		doReturn(orderRows).when(mockOrder).getUnmodificableMaterials();
		
		btnAddOrderRow = new JButton();
		btnRemoveOrderRow = new JButton();
		doReturn(btnAddOrderRow).when(mockOrderRowsView).getBtnAddOrderRow();
		doReturn(btnRemoveOrderRow).when(mockOrderRowsView).getBtnRemoveOrderRow();
		
		mockOrderRowsTable = mock(JTable.class);
		mockOrderRowsTblModel = mock(OrderRowsTableModel.class);
		doReturn(mockOrderRowsTable).when(mockOrderRowsView).getTblOrderRows();
		doReturn(mockOrderRowsTblModel).when(mockOrderRowsView).getTblOrderRowsModel();
		
		
		mockCmbBoxCellEditor = mock(ComboBoxCellEditorCustom.class);
		mockSpnCellEditor = mock(SpinnerCellEditor.class);
		
		doReturn(mockCmbBoxCellEditor).when(mockOrderRowsView).getCmbMaterialCodeCellEditor();
		doReturn(mockSpnCellEditor).when(mockOrderRowsView).getSpnOrderRowQuantityCellEditor();
	}
	
	private static void setupAvailableMaterialList(boolean empty) {
		if(empty) {
			availableMaterials = new ArrayList<>();
		} else {
			mockMaterial = mock(Material.class);
			doReturn(1993l).when(mockMaterial).getCode();
			availableMaterials = new ArrayList<>();
			availableMaterials.add(mockMaterial);
		}
	}

	
	/**
	 * Test internal object initialization
	 */
	@Test
	public void testOrderRowsViewControllerConstructor() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		assertSame(mockOrderRowsView, controller.view);
		assertSame(mockOrder, controller.order);
		assertSame(availableMaterials, controller.availableMaterials);
	}
	
	/**
	 * Test remove order row button should be
	 * not visible, because the order row
	 * is not editable
	 */
	@Test
	public void testTblOrderRowsValidSelectionActionNotEditable() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		btnRemoveOrderRow.setVisible(false);
		doReturn(false).when(mockOrderRow).isEditable();
		controller.tblOrderRowsValidSelectionAction(0);
		assertFalse(btnRemoveOrderRow.isVisible());
	}
	
	/**
	 * Test remove order row button should be
	 *  visible, because the order row
	 * is editable
	 */
	@Test
	public void testTblOrderRowsValidSelectionActionEditable() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		btnRemoveOrderRow.setVisible(false);
		doReturn(true).when(mockOrderRow).isEditable();
		controller.tblOrderRowsValidSelectionAction(0);
		assertTrue(btnRemoveOrderRow.isVisible());
	}

	/**
	 * Test remove order row button should be not
	 * visible
	 */
	@Test
	public void testTblOrderRowsInvalidSelectionAction() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		btnRemoveOrderRow.setVisible(true);
		controller.tblOrderRowsInvalidSelectionAction();
		assertFalse(btnRemoveOrderRow.isVisible());
	}

	/**
	 * Test order row material code should be not updated
	 * after combo edit stop action, because material doesn't exist
	 */
	@Test
	public void testTblOrderRowsComboEditStopActionFail() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		ChangeEvent e = new ChangeEvent(mockCmbBoxCellEditor);
		doReturn(10l).when(mockCmbBoxCellEditor).getCellEditorValue();
		assertFalse(controller.tblOrderRowsComboEditStopAction(e));
	}
	
	/**
	 * Test order row material code should be updated
	 * after combo edit stop action
	 */
	@Test
	public void testTblOrderRowsComboEditStopAction() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		ChangeEvent e = new ChangeEvent(mockCmbBoxCellEditor);
		doReturn(1993l).when(mockCmbBoxCellEditor).getCellEditorValue();
		assertTrue(controller.tblOrderRowsComboEditStopAction(e));
	}

	/**
	 * Test order row quantity should be updated
	 * after spinner edit stop action
	 */
	@Test
	public void testTblOrderRowsSpinnerEditStopAction() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		ChangeEvent e = new ChangeEvent(mockSpnCellEditor);
		doReturn(0).when(mockOrderRowsTable).getSelectedRow();
		doReturn(200).when(mockSpnCellEditor).getCellEditorValue();
		controller.tblOrderRowsSpinnerEditStopAction(e);
	}

	/**
	 * Test add order should fail because
	 * the available materials list is empty
	 */
	@Test
	public void testBtnAddOrderRowActionFailEmptyMaterials() {
		setupAvailableMaterialList(true);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		assertFalse(controller.btnAddOrderRowAction());
	}
	
	/**
	 * Test add order should fail because
	 * the order is not editable
	 */
	@Test
	public void testBtnAddOrderRowActionFailNotEditable() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		doReturn(false).when(mockOrder).isEditable();
		assertFalse(controller.btnAddOrderRowAction());
	}
	
	/**
	 * Test add order should fail because
	 * the order row is not valid
	 */
	@Test
	public void testBtnAddOrderRowActionFailNotValid() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		doReturn(true).when(mockOrder).isEditable();
		doReturn(false).when(mockOrder).addMaterial(any(OrderRow.class));
		assertFalse(controller.btnAddOrderRowAction());
	}
	
	/**
	 * Test add order should happen correctly
	 */
	@Test
	public void testBtnAddOrderRowAction() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		doReturn(true).when(mockOrder).isEditable();
		doReturn(true).when(mockOrder).addMaterial(any(OrderRow.class));
		assertTrue(controller.btnAddOrderRowAction());
	}

	/**
	 * Test remove order should fail because
	 * no valid selection
	 */
	@Test
	public void testBtnRemoveOrderRowActionFailNoSelection() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		doReturn(-1).when(mockOrderRowsTable).getSelectedRow();
		assertFalse(controller.btnRemoveOrderRowAction());
	}
	
	/**
	 * Test remove order should fail because
	 * order is not editable
	 */
	@Test
	public void testBtnRemoveOrderRowActionNotEditable() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		doReturn(-1).when(mockOrderRowsTable).getSelectedColumn();
		doReturn(false).when(mockOrder).isEditable();
		assertFalse(controller.btnRemoveOrderRowAction());
	}
	
	/**
	 * Test remove order should fail because
	 * not confirmed by the user
	 */
	@Test
	public void testBtnRemoveOrderRowActionNotConfirmed() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		doReturn(-1).when(mockOrderRowsTable).getSelectedColumn();
		doReturn(true).when(mockOrder).isEditable();
		doReturn(mockMaterial).when(mockOrderRow).getMaterial();
		doReturn(1).when(Utils.msg).questionBox(anyString(), anyString());
		assertFalse(controller.btnRemoveOrderRowAction());
	}
	
	/**
	 * Test remove order should happen correctly
	 */
	@Test
	public void testBtnRemoveOrderRowAction() {
		setupAvailableMaterialList(false);
		OrderRowsViewController controller = new OrderRowsViewController(mockOrderRowsView, mockOrder, availableMaterials);
		doReturn(-1).when(mockOrderRowsTable).getSelectedColumn();
		doReturn(true).when(mockOrder).isEditable();
		doReturn(mockMaterial).when(mockOrderRow).getMaterial();
		doReturn(0).when(Utils.msg).questionBox(anyString(), anyString());
		assertTrue(controller.btnRemoveOrderRowAction());
	}

}
