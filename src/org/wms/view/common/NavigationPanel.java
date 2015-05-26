package org.wms.view.common;

import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.wms.config.IconTypeAWMS;

/**
 * Left Navigation panel, contains the menu buttons like the Home Button and show them 
 * based on the current user's security level. This class get initialized with a NO_LEVEL
 * security level.
 * 
 * @author Stefano Pessina, Daniele Ciriello 
 *
 */
public class NavigationPanel extends JPanel {

	/**
	 * Left menu bar
	 */
	private JPanel navBar;

	/**
	 * Home button
	 */
	public JButton btnLogin;	

	/**
	 * Orders list button
	 */
	public JButton btnInputOrders;
	public JButton btnOutputOrders;

	/**
	 * Verify list button
	 */
	public JButton btnVerifyList;

	/**
	 * Jobs list button
	 */
	public JButton btnJobsList;
	
	/**
	 * current security level
	 */
	private SecurityLevel level;

	/**
	 * Constructor
	 */
	public NavigationPanel() {
		initComponents();
		initUI();
		changeUser(SecurityLevel.NO_LEVEL);
	}
	
	/**
	 * Getter
	 * 
	 * @return the navigation bar
	 */
	public JPanel getNavBar(){
		return navBar;
	}
	
	/**
	 * Getter
	 * 
	 * @return the current security level
	 */
	public SecurityLevel getLevel(){
		return level;
	}

	/**
	 * Initialize navigation bar and buttons
	 */
	private void initComponents() {
		List<JButton> navigationButtonsList = new ArrayList<>();

		btnLogin = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.USER.name());
		navigationButtonsList.add(btnLogin);

		btnInputOrders = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.INPUT_ORDER.name());
		navigationButtonsList.add(btnInputOrders);
		
		btnOutputOrders = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.OUTPUT_ORDER.name());
		navigationButtonsList.add(btnOutputOrders);

		btnVerifyList = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.HOME.name());
		navigationButtonsList.add(btnVerifyList);

		btnJobsList = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.HOME.name());
		navigationButtonsList.add(btnJobsList);

		navBar = FactoryReferences.panels.getVerticalNavigationBarPanel(navigationButtonsList);
	}

	/**
	 * Initialize User Interface
	 */
	private void initUI() {
		setLayout(new GridLayout(1,1));
		
		add(navBar);
	}


	/**
	 * Change the current user and show/hide relative buttons
	 * 
	 * @param level reference to the current security level 
	 */
	public void changeUser(SecurityLevel level) {
		
		this.level = level;

		btnInputOrders.setVisible(false);
		btnOutputOrders.setVisible(false);
		btnVerifyList.setVisible(false);
		btnJobsList.setVisible(false);
		
		switch (level) {
		case NO_LEVEL:
			break;
		case OPERATOR:
			btnJobsList.setVisible(true);
			break;
		case SUPERVISOR:
			btnVerifyList.setVisible(true);
			break;
		case ADMIN:
			btnInputOrders.setVisible(true);
			btnOutputOrders.setVisible(true);
			btnVerifyList.setVisible(true);
			btnJobsList.setVisible(true);
			break;
		default:
			break;
		}
	}
}
