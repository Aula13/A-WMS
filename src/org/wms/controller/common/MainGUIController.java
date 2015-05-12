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

public class MainGUIController {
	
	private final MainGUI gui;
	
	private final LoginPopupMenu loginMenu;

	public MainGUIController(MainGUI gui) {
		super();
		this.gui = gui;
		loginMenu = new LoginPopupMenu();
		
		gui.getLblUsers().setComponentPopupMenu(loginMenu);
	}

	public MainGUI getGui() {
		return gui;
	}

	public LoginPopupMenu getLoginMenu() {
		return loginMenu;
	}
	
	
}
