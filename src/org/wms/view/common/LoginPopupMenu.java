package org.wms.view.common;

import it.rmautomazioni.security.SecurityLevel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.wms.config.SecurityConfig;

/**
 * Login popup menu class
 * menu with login and logout button
 * login button opens login form
 * if a user is logged, a user can logout 
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class LoginPopupMenu extends JPopupMenu {
	
	/**
	 * login button
	 */
	private JMenuItem btnLogin;
	
	/**
	 * logout button
	 */
	private JMenuItem btnLogout;
	
	/**
	 * Login popup menu constructor
	 * init UI
	 * init in place controller for login and logout button
	 */
	public LoginPopupMenu() {
		initUI();
		initInPlaceController();
	}
	
	/**
	 * create logout and login buttons
	 * and add them to popup menu
	 */
	private void initUI() {
		btnLogin = new JMenuItem("Login");
		add(btnLogin);
		
		btnLogout = new JMenuItem("Logout");
		add(btnLogout);
	}

	/**
	 * create action listeners for buttons
	 */
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

	/**
	 * @return reference to login button
	 */
	public JMenuItem getBtnLogin() {
		return btnLogin;
	}

	/**
	 * @return reference to logout button
	 */
	public JMenuItem getBtnLogout() {
		return btnLogout;
	}
	
	
}
