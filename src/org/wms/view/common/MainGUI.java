package org.wms.view.common;

import it.rmautomazioni.database.common.ConnectionStatus;
import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.security.SecurityStatus;
import it.rmautomazioni.view.common.Navigable;
import it.rmautomazioni.view.controls.StatusBarLabel;
import it.rmautomazioni.view.factories.ApplicationFont;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.ImagePanel;
import it.rmautomazioni.view.factories.RMColour;
import it.rmautomazioni.view.graphicsresource.ImageType;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.wms.config.ResourceUtil;

/**
 * Main GUI show the user interface parts always visible for the user
 * (status bar, title panel, navigation bar) * 
 * 
 * @author stefano
 *
 */
public class MainGUI extends ContentPane implements Navigable, Observer {

	private static final long serialVersionUID = 4151709269001700322L; 
	
	/**
	 * Connection label status
	 */
	private StatusBarLabel lblDbConn;

	/**
	 * Security label status
	 */
	private StatusBarLabel lblUsers;

	/**
	 * Panel title label
	 */
	private JLabel lblTitle;

	/**
	 * Navigation panel
	 */
	public NavigationPanel navPanel;

	/**
	 * Panel title, with background image
	 */
	ImagePanel titlePanel;

	
	/**
	 * Center panel (where the content are delivered)
	 */
	protected JPanel currentPanel;
	
	/**
	 * Map the possible center panels (pages) 
	 */
	private Map<Integer, JPanel> panels;

	/**
	 * Connection status observable
	 */
	ConnectionStatus dbConnectionStatus;
	
	/**
	 * Security status observable
	 */
	SecurityStatus securityStatus;

	/**
	 * Constructor
	 * 
	 * @param dbConnectionStatus reference to the connection status observable
	 * @param securityStatus reference to the security status observable
	 */
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

	/**
	 * Init components that need to be inserted in the gui
	 */
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

	/**
	 * Place the component inside the GUI
	 */
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

	/**
	 * Init components text
	 */
	private void setText() {
		lblDbConn.setTexts("DB online", "DB offline");
	}

	/* (non-Javadoc)
	 * @see it.rmautomazioni.view.common.Navigable#changePanel(javax.swing.JPanel)
	 */
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

	/* (non-Javadoc)
	 * @see it.rmautomazioni.view.common.Navigable#setTitleName(java.lang.String)
	 */
	@Override
	public void setTitleName(String name) {
		lblTitle.setText("A-WMS - " + name);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see it.rmautomazioni.view.common.Navigable#setActiveButton(javax.swing.JButton)
	 */
	@Override
	public void setActiveButton(JButton button) {
		//		for (JButton cmd : navigationButtonsList) {
		//			((JActiveButton) cmd).setActive(button == cmd);
		//		}
	}

	public StatusBarLabel getLblDbConn() {
		return lblDbConn;
	}

	public StatusBarLabel getLblUsers() {
		return lblUsers;
	}

	public JLabel getLblTitle() {
		return lblTitle;
	}
}
