package org.wms.controller.common;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.rmautomazioni.view.controls.StatusBarLabel;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.RMColour;
import it.rmautomazioni.view.factories.jx.ConcreteJXAppStyleFactory;
import it.rmautomazioni.view.factories.swing.ConcreteButtonFactory;
import it.rmautomazioni.view.factories.swing.ConcreteFieldFactory;
import it.rmautomazioni.view.factories.swing.ConcretePanelFactory;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.config.ResourceUtil;
import org.wms.model.batch.Batches;
import org.wms.model.common.ListType;
import org.wms.model.material.Materials;
import org.wms.model.order.Orders;
import org.wms.model.warehouse.Warehouse;
import org.wms.test.TestUtils;
import org.wms.view.common.MainGUI;
import org.wms.view.common.NavigationPanel;

/**
 * Test MainGUIController for MainGUI
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class MainGUIControllerUnitTest {
	
	private static MainGUI mockGUI;
	
	private static MainGUIController ctrlTest;
	
	private static NavigationPanel mockNavPanel;
	
	private static StatusBarLabel lblUserTest;
	
	private static JButton btnLogin;
	private static JButton btnInputOrders;
	private static JButton btnOutputOrders;
	private static JButton btnWarehouseLayout;
	private static JButton btnBatches;
	
	private static Orders mockOrdersModel;
	private static Materials mockMaterialsModel;
	private static Warehouse mockWarehouseModel;
	private static Batches mockBatchesModel;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TestUtils.mockMessageBox();
		
		mockGUI = mock(MainGUI.class);
		mockNavPanel = mock(NavigationPanel.class);
		
		when(mockGUI.getNavPanel()).thenReturn(mockNavPanel);
		lblUserTest = new StatusBarLabel(RMColour.RM_DARK_BLUE, RMColour.RM_DARK_BLUE);		
		when(mockGUI.getLblUsers()).thenReturn(lblUserTest);
		
		btnLogin = new JButton();
		btnInputOrders = new JButton();
		btnOutputOrders = new JButton();
		btnWarehouseLayout = new JButton();
		btnBatches = new JButton();
		when(mockNavPanel.getBtnLogin()).thenReturn(btnLogin);
		when(mockNavPanel.getBtnInputOrders()).thenReturn(btnInputOrders);
		when(mockNavPanel.getBtnOutputOrders()).thenReturn(btnOutputOrders);
		when(mockNavPanel.getBtnWarehouseView()).thenReturn(btnWarehouseLayout);
		when(mockNavPanel.getBtnBatches()).thenReturn(btnBatches);
		
		mockOrdersModel = mock(Orders.class);
		mockMaterialsModel = mock(Materials.class);
		mockWarehouseModel = mock(Warehouse.class);
		mockBatchesModel = mock(Batches.class);
		
		when(mockOrdersModel.getUnmodificableOrderList(ListType.INPUT))
			.thenReturn(new ArrayList<>());
		when(mockOrdersModel.getUnmodificableOrderList(ListType.OUTPUT))
			.thenReturn(new ArrayList<>());
		when(mockWarehouseModel.getUnmodifiableLines())
			.thenReturn(new ArrayList<>());
		when(mockBatchesModel.getUnmodificableBatchList())
			.thenReturn(new ArrayList<>());
	
		when(mockMaterialsModel.getUnmodificableMaterialList()).thenReturn(new ArrayList<>());
		
		TestUtils.initGUIFactories();
		
		ctrlTest = new MainGUIController(mockGUI, mockWarehouseModel, mockOrdersModel, mockMaterialsModel, mockBatchesModel);
	}

	/**
	 * Test popup menu should be attached to the lblUser label
	 */
	@Test
	public void testPopupMenu() {
		assertTrue(lblUserTest.getComponentPopupMenu()!=null);
	}
	
	/**
	 * Test that listener is added
	 * to login button in navigation panel
	 */
	@Test
	public void testAddListenerBtnLogin() {
		assertTrue(btnLogin.getListeners(ActionListener.class).length==1);
	}
	
	/**
	 * Test that listener is added
	 * to input order button in navigation panel
	 */
	@Test
	public void testAddListenerBtnInputOrders() {
		assertTrue(btnInputOrders.getListeners(ActionListener.class).length==1);
	}
	
	/**
	 * Test that listener is added
	 * to output order button in navigation panel
	 */
	@Test
	public void testAddListenerBtnOutputOrders() {
		assertTrue(btnOutputOrders.getListeners(ActionListener.class).length==1);
	}
	
	/**
	 * Test gui should be returned by getGui method
	 */
	@Test
	public void testGetGUI() {
		assertTrue(ctrlTest.getGui().equals(mockGUI));
	}
	
	/**
	 * Test loginPopupMenu should be returned by getPopupMenu method
	 */
	@Test
	public void testGetPopupMenu() {
		assertTrue(ctrlTest.getLoginMenu().equals(ctrlTest.loginMenu));
	}
}
