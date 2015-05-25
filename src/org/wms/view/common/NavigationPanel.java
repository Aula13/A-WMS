package org.wms.view.common;

import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.graphicsresource.IconType;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

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
	public JButton btnHome;	

	/**
	 * Orders list button
	 */
	public JButton btnOrdersList;

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

		btnHome = FactoryReferences.buttons.getButtonWithIcon(IconType.SETTING.name());
		navigationButtonsList.add(btnHome);

		btnOrdersList = FactoryReferences.buttons.getButtonWithIcon(IconType.SETTING.name());
		navigationButtonsList.add(btnOrdersList);

		btnVerifyList = FactoryReferences.buttons.getButtonWithIcon(IconType.SETTING.name());
		navigationButtonsList.add(btnVerifyList);

		btnJobsList = FactoryReferences.buttons.getButtonWithIcon(IconType.SETTING.name());
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

		switch (level) {
		case NO_LEVEL:
			btnHome.setVisible(true);
			btnOrdersList.setVisible(false);
			btnVerifyList.setVisible(false);
			btnJobsList.setVisible(false);
			break;
		case OPERATOR:
			btnHome.setVisible(true);
			btnOrdersList.setVisible(false);
			btnVerifyList.setVisible(false);
			btnJobsList.setVisible(true);
			break;
		case SUPERVISOR:
			btnHome.setVisible(true);
			btnOrdersList.setVisible(false);
			btnVerifyList.setVisible(true);
			btnJobsList.setVisible(false);
			break;
		case ADMIN:
			btnHome.setVisible(true);
			btnOrdersList.setVisible(true);
			btnVerifyList.setVisible(true);
			btnJobsList.setVisible(true);
			break;
		default:
			break;
		}
	}
}
