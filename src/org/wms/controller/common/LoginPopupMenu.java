package org.wms.controller.common;

import it.rmautomazioni.security.SecurityLevel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.wms.config.SecurityConfig;

public class LoginPopupMenu extends JPopupMenu {
	
	private JMenuItem btnLogin;
	
	private JMenuItem btnLogout;
	
	public LoginPopupMenu() {
		initUI();
		initInPlaceController();
	}
	
	private void initUI() {
		btnLogin = new JMenuItem("Login");
		add(btnLogin);
		
		btnLogout = new JMenuItem("Logout");
		add(btnLogout);
	}

	private void initInPlaceController() {
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SecurityConfig.getSecurityManager().openLoginScreen(SecurityLevel.NO_LEVEL);
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SecurityConfig.getSecurityManager().logout();						
			}
		});
	}

	public JMenuItem getBtnLogin() {
		return btnLogin;
	}

	public JMenuItem getBtnLogout() {
		return btnLogout;
	}
	
	
}
