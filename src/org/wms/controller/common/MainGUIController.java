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

	public MainGUIController(MainGUI gui) {
		super();
		this.gui = gui;
		
		gui.getLblUsers().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				JPopupMenu menu = new JPopupMenu();
				
				JMenuItem btnLogin = new JMenuItem("Login");
				menu.add(btnLogin);
				
				btnLogin.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						SecurityConfig.getSecurityManager().openLoginScreen(SecurityLevel.NO_LEVEL);
					}
				});
				
				JMenuItem btnLogout = new JMenuItem("Logout");
				btnLogout.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						SecurityConfig.getSecurityManager().logout();						
					}
				});
				menu.add(btnLogout);
				
				menu.show(gui, e.getXOnScreen(), e.getYOnScreen());
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
}
