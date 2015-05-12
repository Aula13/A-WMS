package org.wms.view.common;

import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.graphicsresource.IconType;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class NavigationPanel extends JPanel {

	private JPanel navBar;

	public JButton btnHome;	

	public JButton btnOrdersList;

	public JButton btnVerifyList;

	public JButton btnJobsList;

	public NavigationPanel() {
		initComponents();
		initUI();
		changeUser(SecurityLevel.NO_LEVEL);
	}

	private void initComponents() {
		List<JButton> navigationButtonsList = new ArrayList<>();

		btnHome = FactoryReferences.buttons.getButtonWithIcon(IconType.SETTING);
		navigationButtonsList.add(btnHome);

		btnOrdersList = FactoryReferences.buttons.getButtonWithIcon(IconType.SETTING);
		navigationButtonsList.add(btnOrdersList);

		btnVerifyList = FactoryReferences.buttons.getButtonWithIcon(IconType.SETTING);
		navigationButtonsList.add(btnVerifyList);

		btnJobsList = FactoryReferences.buttons.getButtonWithIcon(IconType.SETTING);
		navigationButtonsList.add(btnJobsList);

		navBar = FactoryReferences.panels.getVerticalNavigationBarPanel(navigationButtonsList);
	}

	private void initUI() {
		setLayout(new GridLayout(1,1));
		
		add(navBar);
	}


	public void changeUser(SecurityLevel level) {

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
