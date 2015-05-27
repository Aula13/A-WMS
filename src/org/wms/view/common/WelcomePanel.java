package org.wms.view.common;

import it.rmautomazioni.view.factories.ImagePanel;
import it.rmautomazioni.view.factories.RMColour;
import it.rmautomazioni.view.indicator.MultiStateComponent;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.wms.config.ImageTypeAWMS;
import org.wms.config.ResourceUtil;

/**
 * Welcome pane when no user is logged in the system
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WelcomePanel extends JPanel {
	
	private JLabel lblWelcome;
	
	private JLabel lblHelp;
	
	private MultiStateComponent imgWelcome;
	
	public WelcomePanel() {
		initComponents();
		initUI();
	}
	
	/**
	 * Init graphic components
	 * to be placed in the gui
	 */
	private void initComponents() {
		lblWelcome = new JLabel("Welcome to AWMS system!");
		lblWelcome.setFont(new Font("Arial", Font.BOLD, 40));
		lblWelcome.setOpaque(false);
		lblWelcome.setForeground(RMColour.RM_ORANGE);
		lblWelcome.setHorizontalAlignment(JLabel.CENTER);
		
		lblHelp = new JLabel("Please login to start fun");
		lblHelp.setFont(new Font("Arial", Font.PLAIN, 30));
		lblHelp.setOpaque(false);
		lblHelp.setForeground(RMColour.RM_WHITE);
		lblHelp.setHorizontalAlignment(JLabel.CENTER);
		
		imgWelcome = new MultiStateComponent(ResourceUtil.imageResource.getResource(ImageTypeAWMS.WELCOME.name()));
	}
	
	/**
	 * Place each component
	 */
	private void initUI() {
		setLayout(new GridBagLayout());
		setBackground(RMColour.RM_DARK_GRAY);
		
		add(lblWelcome, 
				new GridBagConstraints(0, 0, 1, 1, 1.0, 0.2, 
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
						new Insets(10, 10, 10, 10), 0, 0));
		add(lblHelp, 
				new GridBagConstraints(0, 1, 1, 1, 1.0, 0.2, 
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
						new Insets(10, 10, 10, 10), 0, 0));
		
		ImagePanel imgPanel = new ImagePanel(ResourceUtil.imageResource.getResource(ImageTypeAWMS.WELCOME.name()));
		imgPanel.setOpaque(false);
		add(imgPanel,
				new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, 
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
						new Insets(0, 20, 0, 20), 0, 0));
	}
	
}
