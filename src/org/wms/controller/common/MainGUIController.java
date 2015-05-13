package org.wms.controller.common;

import org.wms.view.common.LoginPopupMenu;
import org.wms.view.common.MainGUI;

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
	private final MainGUI gui;
	
	/**
	 * Login popup menu, it's showed when users
	 * press on user label in the satus bar
	 */
	private final LoginPopupMenu loginMenu;

	public MainGUIController(MainGUI gui) {
		super();
		this.gui = gui;
		loginMenu = new LoginPopupMenu();
		
		gui.getLblUsers().setComponentPopupMenu(loginMenu);
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
