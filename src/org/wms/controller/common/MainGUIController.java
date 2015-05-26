package org.wms.controller.common;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;
import it.rmautomazioni.controller.listener.ChangePageListener;
import it.rmautomazioni.security.SecurityLevel;

import org.wms.config.SecurityConfig;
import org.wms.controller.order.OrdersViewController;
import org.wms.model.common.ModelReference;
import org.wms.model.order.OrderType;
import org.wms.view.common.LoginPopupMenu;
import org.wms.view.common.MainGUI;
import org.wms.view.common.WelcomePanel;
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

	public MainGUIController(MainGUI gui) {
		super();
		this.gui = gui;
		loginMenu = new LoginPopupMenu();
		
		gui.getLblUsers().setComponentPopupMenu(loginMenu);
		
		new AbstractJButtonActionListener(gui.navPanel.btnLogin) {
			
			@Override
			public void actionTriggered() {
				SecurityConfig.getSecurityManager().openLoginScreen(SecurityLevel.NO_LEVEL);
			}
		};
		
		inputOrdersView = new OrdersView(ModelReference.ordersModel, OrderType.INPUT);
		new OrdersViewController(inputOrdersView, ModelReference.ordersModel, ModelReference.materialsModel);
		new ChangePageListener(
				gui.navPanel.btnInputOrders, 
				inputOrdersView, 
				gui);
		
		outputOrdersView = new OrdersView(ModelReference.ordersModel, OrderType.OUTPUT);
		new OrdersViewController(outputOrdersView, ModelReference.ordersModel, ModelReference.materialsModel);
		new ChangePageListener(
				gui.navPanel.btnOutputOrders, 
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
