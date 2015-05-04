package org.wms.view.common;

import it.rmautomazioni.database.common.ConnectionStatus;
import it.rmautomazioni.security.SecurityStatus;
import it.rmautomazioni.view.common.Navigable;
import it.rmautomazioni.view.controls.JActiveButton;
import it.rmautomazioni.view.controls.StatusBarLabel;
import it.rmautomazioni.view.factories.AbstractAppStyleFactory;
import it.rmautomazioni.view.factories.ApplicationFont;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.RMColour;
import it.rmautomazioni.view.graphicsresource.IconType;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainGUI extends ContentPane implements Navigable, Observer {
	
	private static final long serialVersionUID = 4151709269001700322L;
	
    private StatusBarLabel lblDbConn = asf.getStatusBarLabel();
    private StatusBarLabel lblPlcConn = asf.getStatusBarLabel();
    private StatusBarLabel lblAlarms = asf.getStatusBarLabel();
    
    private StatusBarLabel lblWMSWorkMode;
    private StatusBarLabel lblUsers;
    
    private JTextField lblTitle;
    
    JPanel navigationPanel;
    JPanel titlePanel;
    
    //Pulsanti di navigazione
    public JButton btnHome;
    public JButton btnWarehouse;
    public JButton btnBays;
    
    public JButton btnMissions;
    
    public JButton btnAlarms;
    
    public JButton btnDiagnosticRM1;
    public JButton btnDiagnosticRC1;
    public JButton btnDiagnosticRC2;
    
    
    //Utility per cambio pagina
	private JPanel currentPanel;
	private Map<Integer, JPanel> panels;
	
	ConnectionStatus dbConnectionStatus;
	SecurityStatus securityStatus;
	
	List<JButton> navigationButtonsList = new ArrayList<>();
    
	public MainGUI(ConnectionStatus dbConnectionStatus, SecurityStatus securityStatus) {
		super(FactoryReferences.appStyle);
		panels = new HashMap<>();
		
		this.dbConnectionStatus = dbConnectionStatus;
		this.securityStatus = securityStatus;
		
		initComponents();
		initUI();
		dbConnectionStatus.addObserver(this);
		securityStatus.addObserver(this);
	}
	
	private void initComponents() {
		lblWMSWorkMode = asf.getStatusBarLabel();
		
		lblUsers = new StatusBarLabel(RMColour.RM_GREEN, RMColour.RM_DARK_GRAY);
		lblUsers.setBorder(
                BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(RMColour.RM_WHITE, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)));
		lblUsers.setForeground(RMColour.RM_WHITE);
		lblUsers.setFont(ApplicationFont.APP_FONT);
	}

    protected void initUI() {
    	//Frame title
    	setTitle("A-WMS");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	//Configurazione del pannello nord
    	titlePanel = FactoryReferences.panels.getPanelWithTitle("A-WMS");
        remove(npanel);
    	add(titlePanel, BorderLayout.NORTH);
        
        //Configurazione del pannello sud
//    	I18n i18n = Localization.i18n;
//    	lblDbConn.setTexts(i18n.tr("GeneralClient.lblDbConn.ok"), i18n.tr("GeneralClient.lblDbConn.error"));
//      lblPlcConn.setTexts(i18n.tr("GeneralClient.lblPlcConn.ok"), i18n.tr("GeneralClient.lblPlcConn.error"));
//      lblAlarms.setTexts(i18n.tr("GeneralClient.lblAlarms.ok"), i18n.tr("GeneralClient.lblAlarms.error"));
        spanel.add(lblDbConn);
        spanel.add(lblPlcConn);
        spanel.add(lblAlarms);
        spanel.add(lblWMSWorkMode);
        spanel.add(lblUsers);
                
        //Configurazione del pannello ovest (navigazione)
    
         
        
        navigationPanel = FactoryReferences.panels.getVerticalNavigationBarPanel(navigationButtonsList);
        
        
        remove(opanel);
        add(navigationPanel, BorderLayout.WEST);
        
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
	}
	
	@Override
    public void setActiveButton(JButton button) {
        for (JButton cmd : navigationButtonsList) {
            ((JActiveButton) cmd).setActive(button == cmd);
        }
    }
}
