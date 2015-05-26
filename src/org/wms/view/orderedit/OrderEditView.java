package org.wms.view.orderedit;

import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.wms.config.IconTypeAWMS;
import org.wms.controller.orderedit.MaterialListViewController;
import org.wms.model.order.Material;
import org.wms.model.order.Order;
import org.wms.model.order.Priority;

public class OrderEditView extends JDialog {
	
	private Order order;
	
	
	private JPanel containerPanel;
	
	/**
	 * Contains the order's fields and labels
	 */
	private JPanel fieldsPanel;
	private JTextField idField;
	private JComboBox<Priority> priorityField;
	private SimpleDateFormat dateFormat;
	private JFormattedTextField dateField;
	
	/**
	 * Contains the materials/quantity table and the right sidebar with the edit buttons
	 */
	private MaterialListView materialsTablePanel;
	
	/**
	 * Contains the confirm/cancel buttons
	 */
	private JPanel confirmationPanel;
	private JButton confirmButton;
	private JButton cancelButton;

	private List<Material> availableMaterials;
	
	/**
	 * @param order to modify
	 * @throws ParseException 
	 */
	public OrderEditView(Order order, List<Material> availableMaterials) throws ParseException {
		super();
		this.order = order;
		this.availableMaterials = availableMaterials;
		initComponents();
		
		idField.setText(Long.toString(order.getId()));
		idField.setEditable(false);
		priorityField.setSelectedItem(order.getPriority());
		dateField.setValue(order.getEmissionDate());
		
		initUI();
	}
	
	private void initComponents() {
		initFieldsPanel();
		initMaterialPanel();
		initConfrimationPanel();
		initContainerPanel();
		initActions();
	}
	

	private void initUI() {
		setModal(true);
		setSize(500, 500);
		setLocationRelativeTo(null);
	}
	
	private void initContainerPanel(){
		containerPanel = FactoryReferences.appStyle.getPanelClass();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		add(containerPanel);
		containerPanel.add(fieldsPanel);
		containerPanel.add(materialsTablePanel);
		containerPanel.add(confirmationPanel);
	}

	private void initFieldsPanel(){
		fieldsPanel = FactoryReferences.appStyle.getPanelClass();
		fieldsPanel.setBackground(Color.BLUE);
		fieldsPanel.setLayout(new GridBagLayout());
		
		idField = FactoryReferences.fields.getParameterTextField();
		
		priorityField = new JComboBox<Priority>();
		priorityField.setModel(new DefaultComboBoxModel<Priority>(Priority.values()));

		
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		dateField = new JFormattedTextField(dateFormat);
		dateField.setEditable(false);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		fieldsPanel.add(FactoryReferences.fields.getParameterLabel("ID"), constraints);
		constraints.gridx += 1;
		fieldsPanel.add(idField, constraints);

		constraints.gridx += 1;
		fieldsPanel.add(FactoryReferences.fields.getParameterLabel("Priorità"), constraints);
		
		constraints.gridx += 1;
		fieldsPanel.add(priorityField, constraints);
		
		constraints.gridx = 0; 
		constraints.gridy = 1;
		fieldsPanel.add(FactoryReferences.fields.getParameterLabel("Data"), constraints);
		
		constraints.gridx += 1;
		fieldsPanel.add(dateField, constraints);
	}
	
	private void initMaterialPanel(){
		materialsTablePanel = new MaterialListView(order, availableMaterials);
		new MaterialListViewController(materialsTablePanel, order, availableMaterials);
		materialsTablePanel.setBackground(Color.BLUE);
	}
	
	private void initConfrimationPanel(){
		confirmationPanel = FactoryReferences.appStyle.getPanelClass();
		confirmationPanel.setBackground(Color.BLUE);
		confirmationPanel.setLayout(new GridLayout(1,1));
		
		confirmButton = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.CONFIRM.name());
		cancelButton = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.CANCEL.name());
		
		confirmationPanel.add(confirmButton);
		confirmationPanel.add(cancelButton);
	}

	/**
	 * create action listeners for buttons
	 */
	private void initActions() {
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO implementare bottone conferma
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);						
			}
		});
	}
	
	 
	
}
