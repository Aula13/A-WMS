package org.wms.controller.common;

import it.rmautomazioni.security.SecurityLevel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.wms.config.SecurityConfig;
import org.wms.view.common.MainGUI;

/**
 * Main GUI controller manage the connection between models and the main GUI
 * 
 * @author stefano
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
