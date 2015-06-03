package org.wms.controller.common;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;
import it.rmautomazioni.controller.listener.ChangePageListener;
import it.rmautomazioni.security.SecurityLevel;

import org.wms.config.SecurityConfig;
import org.wms.controller.order.OrdersViewController;
import org.wms.model.common.ModelReference;
import org.wms.model.material.Materials;
import org.wms.model.order.OrderType;
import org.wms.model.order.Orders;
import org.wms.view.common.LoginPopupMenu;
import org.wms.view.common.MainGUI;
import org.wms.view.order.OrdersView;

/**
 * Main GUI controller manage the connection between models and the main GUI
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class MainGUIController {
	
	/**
	 * Reference to the main GUI
	 */
	protected final MainGUI gui;
	
	/**
	 * Login popup menu, it's showed when users
	 * press on user label in the satus bar
	 */
	protected final LoginPopupMenu loginMenu;
	
	protected final OrdersView inputOrdersView;
	protected final OrdersView outputOrdersView;

	public MainGUIController(MainGUI gui, Orders ordersModel, Materials materialsModel) {
		super();
		this.gui = gui;
		loginMenu = new LoginPopupMenu();
		
		gui.getLblUsers().setComponentPopupMenu(loginMenu);
		
		new AbstractJButtonActionListener(gui.getNavPanel().getBtnLogin()) {
			
			@Override
			public void actionTriggered() {
				SecurityConfig.getSecurityManager().openLoginScreen(SecurityLevel.NO_LEVEL);
			}
		};
		
		inputOrdersView = new OrdersView(ordersModel, OrderType.INPUT);
		new OrdersViewController(inputOrdersView, ordersModel, materialsModel);
		new ChangePageListener(
				gui.getNavPanel().getBtnInputOrders(), 
				inputOrdersView, 
				gui);
		
		outputOrdersView = new OrdersView(ordersModel, OrderType.OUTPUT);
		new OrdersViewController(outputOrdersView, ordersModel, materialsModel);
		new ChangePageListener(
				gui.getNavPanel().getBtnOutputOrders(), 
				outputOrdersView, 
				gui);
	}

	/**
	 * @return the reference to the main GUI
	 */
	public MainGUI getGui() {
		return gui;
	}

	/**
	 * @return the reference to the login popup menu
	 */
	public LoginPopupMenu getLoginMenu() {
		return loginMenu;
	}	
}
