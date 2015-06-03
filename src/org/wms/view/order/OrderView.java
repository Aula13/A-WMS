package org.wms.view.order;

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
import org.wms.controller.order.OrderRowsViewController;
import org.wms.model.material.Material;
import org.wms.model.order.Order;
import org.wms.model.order.Priority;

import com.toedter.calendar.JDateChooser;

/**
 * Order view shows details about an order
 * 
 * this view allow also order editing
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrderView extends JDialog {
	
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
	private OrderRowsView orderRowsPanel;
	
	/**
	 * Contains the confirm/cancel buttons
	 */
	private JPanel confirmationPanel;
	private JButton confirmButton;
	private JButton cancelButton;

	/**
	 * List of available materials
	 * for new order row materials proposal
	 */
	private List<Material> availableMaterials;
	
	private boolean isNew;
	/**
	 * Constructor
	 * 
	 * create the component
	 * and place it the view
	 * 
	 * init components value
	 * 
	 * @param order to show/edit
	 * @throws ParseException 
	 */
	public OrderView(Order order, List<Material> availableMaterials, boolean isNew) {
		super();
		this.order = order;
		this.availableMaterials = availableMaterials;
		this.isNew = isNew;
		initComponents();		
		initUI();
		initFieldsValue();
	}
	
	/**
	 * Init components to be placed in the view
	 */
	private void initComponents() {
		initFieldsPanel();
		initOrderRowsPanel();
		initConfirmationPanel();
		initContainerPanel();
	}
	

	/**
	 * Set view appearance
	 */
	private void initUI() {
		setModal(true);
		setSize(700, 500);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Init container panel
	 */
	private void initContainerPanel(){
		containerPanel = FactoryReferences.appStyle.getPanelClass();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		add(containerPanel);
		containerPanel.add(fieldsPanel);
		containerPanel.add(orderRowsPanel);
		containerPanel.add(confirmationPanel);
	}

	/**
	 * Init field panel
	 * show most important order information
	 */
	private void initFieldsPanel(){
		fieldsPanel = FactoryReferences.appStyle.getPanelClass();
		fieldsPanel.setBackground(Color.BLUE);
		fieldsPanel.setLayout(new GridBagLayout());
		
		idField = FactoryReferences.fields.getParameterTextField();
		
		priorityField = new JComboBox<Priority>();
		priorityField.setModel(new DefaultComboBoxModel<Priority>(Priority.values()));

		dateField = new JDateChooser();

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
	
	/**
	 * Init order rows panel
	 */
	private void initOrderRowsPanel(){
		orderRowsPanel = new OrderRowsView(order, availableMaterials);
		new OrderRowsViewController(orderRowsPanel, order, availableMaterials);
		orderRowsPanel.setBackground(Color.BLUE);
	}
	
	/**
	 * Init panel with confirm/cancel buttons
	 */
	private void initConfirmationPanel(){
		confirmationPanel = FactoryReferences.appStyle.getPanelClass();
		confirmationPanel.setBackground(Color.BLUE);
		confirmationPanel.setLayout(new GridLayout(1,1));
		
		confirmButton = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.CONFIRM.name());
		cancelButton = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.CANCEL.name());
		
		confirmationPanel.add(confirmButton);
		confirmationPanel.add(cancelButton);
	}

	/**
	 * Setup fields values
	 */
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
	
	/**
	 * @return reference to confirm button
	 */
	public JButton getConfirmButton() {
		return confirmButton;
	}
	
	/**
	 * @return reference to cancel button
	 */
	public JButton getCancelButton() {
		return cancelButton;
	}
	
	/**
	 * @return id selected by the user
	 */
	public long getSelectedId() {
		return Long.parseLong(idField.getText());
	}
	
	/**
	 * @return emission date selected by the user
	 */
	public Date getSelectedEmissionDate() {
		return dateField.getDate();
	}
	
	/**
	 * @return priority selected by the user
	 */
	public Priority getSelectedPriority() {
		return priorityField.getItemAt(priorityField.getSelectedIndex());	
	}
	
	/**
	 * @return true if the order showed is new
	 */
	public boolean isNew() {
		return isNew;
	}
}
