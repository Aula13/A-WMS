package org.wms.view.batch;

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
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.wms.config.IconTypeAWMS;
import org.wms.controller.order.OrderRowsViewController;
import org.wms.model.batch.Batch;
import org.wms.model.common.Priority;
import org.wms.model.material.Material;
import org.wms.model.order.Order;
import org.wms.view.order.OrderRowsView;

import com.toedter.calendar.JDateChooser;

/**
 * Batch view shows details about an order
 * 
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class BatchView extends JDialog {
	
	private Batch batch;
	
	private JPanel containerPanel;
	
	/**
	 * Contains the order's fields and labels
	 */
	private JPanel fieldsPanel;
	private JTextField idField;
	private JComboBox<Priority> priorityField;
	
	/**
	 * Contains the materials/quantity table and the right sidebar with the edit buttons
	 */
	private BatchRowsView batchRowsPanel;
	
	/**
	 * Contains the confirm/cancel buttons
	 */
	private JPanel confirmationPanel;
	private JButton confirmButton;
	private JButton cancelButton;

	/**
	 * Constructor
	 * 
	 * create the component
	 * and place it the view
	 * 
	 * init components value
	 * 
	 * @param batch to show
	 * @throws ParseException 
	 */
	public BatchView(Batch batch) {
		super();
		this.batch = batch;
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
		containerPanel.add(batchRowsPanel);
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
	}
	
	/**
	 * Init order rows panel
	 */
	private void initOrderRowsPanel(){
		batchRowsPanel = new BatchRowsView(batch);
		batchRowsPanel.setBackground(Color.BLUE);
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
		idField.setText(Long.toString(batch.getId()));
		idField.setEditable(false);
		
		priorityField.setSelectedItem(batch.getPriority());
		priorityField.setEditable(false);
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
}
