package org.wms.view.common;

import it.rmautomazioni.database.common.ConnectionStatus;
import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.security.SecurityStatus;
import it.rmautomazioni.view.common.Navigable;
import it.rmautomazioni.view.controls.JActiveButton;
import it.rmautomazioni.view.controls.StatusBarLabel;
import it.rmautomazioni.view.factories.AbstractAppStyleFactory;
import it.rmautomazioni.view.factories.ApplicationFont;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.ImagePanel;
import it.rmautomazioni.view.factories.RMColour;
import it.rmautomazioni.view.graphicsresource.IconType;
import it.rmautomazioni.view.graphicsresource.ImageType;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.wms.config.ResourceUtil;

public class MainGUI extends ContentPane implements Navigable, Observer {

	private static final long serialVersionUID = 4151709269001700322L;

	private StatusBarLabel lblDbConn;

	public StatusBarLabel lblUsers;

	private JLabel lblTitle;

	public NavigationPanel navPanel;

	ImagePanel titlePanel;

	//Utility per cambio pagina
	private JPanel currentPanel;
	private Map<Integer, JPanel> panels;

	ConnectionStatus dbConnectionStatus;
	SecurityStatus securityStatus;

	public MainGUI(ConnectionStatus dbConnectionStatus, SecurityStatus securityStatus) {
		super(FactoryReferences.appStyle);
		panels = new HashMap<>();

		this.dbConnectionStatus = dbConnectionStatus;
		this.securityStatus = securityStatus;

		initComponents();
		initUI();
		dbConnectionStatus.addObserver(this);
		securityStatus.addObserver(this);
		update(null, null);
	}

	private void initComponents() {
		lblDbConn = asf.getStatusBarLabel();

		lblUsers = new StatusBarLabel(RMColour.RM_GREEN, RMColour.RM_DARK_GRAY);
		lblUsers.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(RMColour.RM_WHITE, 2),
						BorderFactory.createEmptyBorder(0, 5, 0, 5)));
		lblUsers.setForeground(RMColour.RM_WHITE);
		lblUsers.setFont(ApplicationFont.APP_FONT);

		navPanel = new NavigationPanel();

		titlePanel = new ImagePanel(ResourceUtil.imageResource.getResource(ImageType.HORIZONTAL_BAR.name()));
		lblTitle = FactoryReferences.panels.getPanelTitleLabel("A-WMS");
		lblTitle.setText("A-WMS");
		titlePanel.setOpaque(false);
		titlePanel.add(lblTitle);
	}

	protected void initUI() {
		//Frame title
		setTitle("A-WMS");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		//Application title configuration

		remove(npanel);
		add(titlePanel, BorderLayout.NORTH);

		//Status bar configuration
		spanel.add(lblDbConn);
		spanel.add(lblUsers);

		remove(opanel);
		add(navPanel, BorderLayout.WEST);

		setText();
	}

	private void setText() {
		lblDbConn.setTexts("DB online", "DB offline");
	}

	public void setContextTitle(String st) {
		//    	titlePanel.setTitle("A-WMS" + " - " + st);
	}

	@Override
	public void changePanel(JPanel panel) {

		if (currentPanel != null) {
			currentPanel.setVisible(false);
		}

		if (!panels.containsKey(Integer.valueOf(panel.hashCode()))) {
			panels.put(Integer.valueOf(panel.hashCode()),panel);
			add(panel, BorderLayout.CENTER);
		}

		currentPanel = panel;
		currentPanel.setVisible(true);
	}

	@Override
	public void setTitleName(String name) {
		setContextTitle(name);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(dbConnectionStatus.isDbConnectionStatus())
			lblDbConn.setOK();
		else
			lblDbConn.setError();

		if(securityStatus.isLogged()) {
			lblUsers.setOK();
			lblUsers.setText(securityStatus.getUser().getName());
		} else {
			lblUsers.setError();
			lblUsers.setText("No user logged");
		}

		if(securityStatus.getUser()==null)
			navPanel.changeUser(SecurityLevel.NO_LEVEL);
		else
			navPanel.changeUser(securityStatus.getUser().getLevel());

	}

	@Override
	public void setActiveButton(JButton button) {
		//		for (JButton cmd : navigationButtonsList) {
		//			((JActiveButton) cmd).setActive(button == cmd);
		//		}
	}
}
