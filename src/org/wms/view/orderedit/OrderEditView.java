package org.wms.view.orderedit;

import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.wms.model.order.Order;
import org.wms.model.order.Priority;

public class OrderEditView extends JDialog {
	
	private Order order;
	
	private MaterialListView materialListView;
	
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
	 * Contains the confirm/abort buttons
	 */
	private JPanel confirmationPanel;
	private JButton confirmButton;
	private JButton abortButton;

	/**
	 * @param order to modify
	 * @throws ParseException 
	 */
	public OrderEditView(Order order) {
		super();
		initComponents();
		this.order = order;
		
		idField.setText(Long.toString(order.getId()));
		idField.setEditable(false);
		priorityField.setSelectedItem(order.getPriority());
		dateField.setValue(order.getEmissionDate());
		
		initUI();
	}
	
	public OrderEditView() {
		super();
		initComponents();
		order = new Order();
		idField.setText("0"); //TODO generare in automatico l'id
		dateField.setValue(new Date());
		
		initUI();
	}
	
	private void initComponents() {
		fieldsPanel = FactoryReferences.appStyle.getPanelClass();
		idField = FactoryReferences.fields.getParameterTextField();
		priorityField = new JComboBox<Priority>();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateField = new JFormattedTextField(dateFormat);
		dateField.setEditable(false);
		
		materialsTablePanel = new MaterialListView(order);
		
		confirmationPanel = FactoryReferences.appStyle.getPanelClass();
		confirmButton = FactoryReferences.buttons.getButtonWithIcon("confirm");
		abortButton = FactoryReferences.buttons.getButtonWithIcon("abort");
		testfunc();
	}
	
	private void initUI() {
		setModal(true);
		setSize(500, 500);
		setLocationRelativeTo(null);
		
		containerPanel = FactoryReferences.appStyle.getPanelClass();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		add(containerPanel);
		
		fieldsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		fieldsPanel.add(FactoryReferences.fields.getParameterLabel("ID"), c);
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		fieldsPanel.add(idField, c);
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		fieldsPanel.add(FactoryReferences.fields.getParameterLabel("Priorità"), c);
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 0;
		fieldsPanel.add(priorityField, c);
		c.weightx = 0.5;
		c.gridx = 0; 
		c.gridy = 1;
		fieldsPanel.add(FactoryReferences.fields.getParameterLabel("Data"), c);
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		fieldsPanel.add(dateField, c);
		
		
		containerPanel.add(fieldsPanel);
		
		containerPanel.add(materialsTablePanel);
		
		confirmationPanel.add(confirmButton);
		confirmationPanel.add(abortButton);
		containerPanel.add(confirmationPanel);
		testfunc();
	}
	
	private void testfunc(){
		fieldsPanel.setBackground(Color.RED);
		fieldsPanel.setOpaque(true);
		materialsTablePanel.setBackground(Color.BLUE);
		materialsTablePanel.setOpaque(true);
		confirmationPanel.setBackground(Color.GREEN);
		confirmationPanel.setOpaque(true);
		setBackground(Color.RED);
		//setOpacity(1.0f);
	}
	
}
