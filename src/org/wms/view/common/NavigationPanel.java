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
	 * Login button
	 */
	private JButton btnLogin;	

	private JButton btnWarehouseView;
	
	/**
	 * Orders list button
	 */
	private JButton btnInputOrders;
	private JButton btnOutputOrders;

	/**
	 * Jobs list button
	 */
	private JButton btnBatches;
	
	/**
	 * Verify list button
	 */
	private JButton btnVerifyList;
	
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

		btnWarehouseView = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.WAREHOUSE.name());
		navigationButtonsList.add(btnWarehouseView);
		
		btnInputOrders = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.INPUT_ORDER.name());
		navigationButtonsList.add(btnInputOrders);
		
		btnOutputOrders = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.OUTPUT_ORDER.name());
		navigationButtonsList.add(btnOutputOrders);

		btnBatches = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.BATCHES.name());
		navigationButtonsList.add(btnBatches);
		
		btnVerifyList = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.HOME.name());
//		navigationButtonsList.add(btnVerifyList);

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
		btnBatches.setVisible(false);
		
		switch (level) {
		case NO_LEVEL:
			break;
		case OPERATOR:
			btnBatches.setVisible(true);
			break;
		case SUPERVISOR:
			btnVerifyList.setVisible(true);
			break;
		case ADMIN:
			btnInputOrders.setVisible(true);
			btnOutputOrders.setVisible(true);
			btnVerifyList.setVisible(true);
			btnBatches.setVisible(true);
			break;
		default:
			break;
		}
	}
	
	/**
	 * @return reference to login page button
	 */
	public JButton getBtnLogin() {
		return btnLogin;
	}
	
	/**
	 * @return reference to warehouse view
	 */
	public JButton getBtnWarehouseView() {
		return btnWarehouseView;
	}
	
	/**
	 * @return reference to input orders page button
	 */
	public JButton getBtnInputOrders() {
		return btnInputOrders;
	}
	
	/**
	 * @return reference to output orders page button
	 */
	public JButton getBtnOutputOrders() {
		return btnOutputOrders;
	}
	
	public JButton getBtnBatches() {
		return btnBatches;
	}
}
