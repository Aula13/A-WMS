package org.wms.view.orderedit;

import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.toedter.calendar.JDateChooser;

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
	private JDateChooser dateField;
	
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
	
	private boolean isNew;
	/**
	 * @param order to modify
	 * @throws ParseException 
	 */
	public OrderEditView(Order order, List<Material> availableMaterials, boolean isNew) {
		super();
		this.order = order;
		this.availableMaterials = availableMaterials;
		this.isNew = isNew;
		initComponents();		
		initUI();
		initFieldsValue();
	}
	
	private void initComponents() {
		initFieldsPanel();
		initMaterialPanel();
		initConfirmationPanel();
		initContainerPanel();
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

		
//		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		
		dateField = new JDateChooser();
		
//		dateField = new JFormattedTextField(dateFormat);
//		dateField.setEditable(false);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		fieldsPanel.add(FactoryReferences.fields.getParameterLabel("ID"), constraints);
		constraints.gridx += 1;
		fieldsPanel.add(idField, constraints);

		constraints.gridx += 1;
		fieldsPanel.add(FactoryReferences.fields.getParameterLabel("Priority"), constraints);
		
		constraints.gridx += 1;
		fieldsPanel.add(priorityField, constraints);
		
		constraints.gridx = 0; 
		constraints.gridy = 1;
		fieldsPanel.add(FactoryReferences.fields.getParameterLabel("Date"), constraints);
		
		constraints.gridx += 1;
		fieldsPanel.add(dateField, constraints);
	}
	
	private void initMaterialPanel(){
		materialsTablePanel = new MaterialListView(order, availableMaterials);
		new MaterialListViewController(materialsTablePanel, order, availableMaterials);
		materialsTablePanel.setBackground(Color.BLUE);
	}
	
	private void initConfirmationPanel(){
		confirmationPanel = FactoryReferences.appStyle.getPanelClass();
		confirmationPanel.setBackground(Color.BLUE);
		confirmationPanel.setLayout(new GridLayout(1,1));
		
		confirmButton = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.CONFIRM.name());
		cancelButton = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.CANCEL.name());
		
		confirmationPanel.add(confirmButton);
		confirmationPanel.add(cancelButton);
	}

	private void initFieldsValue() {
		if(isNew) {
			idField.setText("");
			idField.setEditable(true);
			dateField.setDate(new Date(System.currentTimeMillis()));
			dateField.setEnabled(true);
		} else {
			idField.setText(Long.toString(order.getId()));
			idField.setEditable(false);
			dateField.setDate(order.getEmissionDate());
			dateField.setEnabled(false);
		}	
		
		priorityField.setSelectedItem(order.getPriority());
	}
	
	public JButton getConfirmButton() {
		return confirmButton;
	}
	
	public JButton getCancelButton() {
		return cancelButton;
	}
	
	public long getSelectedId() {
		return Long.parseLong(idField.getText());
	}
	
	public Date getSelectedEmissionDate() {
		return dateField.getDate();
	}
	
	public Priority getSelectedPriority() {
		return priorityField.getItemAt(priorityField.getSelectedIndex());	
	}
}
