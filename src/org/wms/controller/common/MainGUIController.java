package org.wms.controller.common;

import it.rmautomazioni.controller.listener.ChangePageListener;

import org.wms.model.common.ModelReference;
import org.wms.view.common.LoginPopupMenu;
import org.wms.view.common.MainGUI;
import org.wms.view.inputorder.InputOrdersView;

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
	
	protected final InputOrdersView inputOrdersView;

	public MainGUIController(MainGUI gui) {
		super();
		this.gui = gui;
		loginMenu = new LoginPopupMenu();
		
		gui.getLblUsers().setComponentPopupMenu(loginMenu);
		
		inputOrdersView = new InputOrdersView(ModelReference.ordersModel);
		
		ChangePageListener homePageListener = new ChangePageListener(
				gui.navPanel.btnHome, 
				inputOrdersView, 
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
