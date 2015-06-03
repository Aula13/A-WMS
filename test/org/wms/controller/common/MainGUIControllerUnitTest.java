package org.wms.controller.common;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.rmautomazioni.view.controls.StatusBarLabel;
import it.rmautomazioni.view.factories.RMColour;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.material.Materials;
import org.wms.model.order.OrderType;
import org.wms.model.order.Orders;
import org.wms.test.TestUtils;
import org.wms.view.common.MainGUI;
import org.wms.view.common.NavigationPanel;

/**
 * Test listeners created in MainGUIController
 * 
 * @author stefano
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
	
	private static Orders mockOrdersModel;
	private static Materials mockMaterialsModel;
	
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
		when(mockNavPanel.getBtnLogin()).thenReturn(btnLogin);
		when(mockNavPanel.getBtnInputOrders()).thenReturn(btnInputOrders);
		when(mockNavPanel.getBtnOutputOrders()).thenReturn(btnOutputOrders);
		
		mockOrdersModel = mock(Orders.class);
		mockMaterialsModel = mock(Materials.class);
		
		when(mockOrdersModel.getUnmodificableOrderList(OrderType.INPUT))
			.thenReturn(new ArrayList<>());
		when(mockOrdersModel.getUnmodificableOrderList(OrderType.OUTPUT))
			.thenReturn(new ArrayList<>());
	
		when(mockMaterialsModel.getUnmodificableMaterialList()).thenReturn(new ArrayList<>());
		
		ctrlTest = new MainGUIController(mockGUI, mockOrdersModel, mockMaterialsModel);
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
