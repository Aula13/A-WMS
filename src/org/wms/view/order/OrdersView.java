package org.wms.view.order;

import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.wms.config.IconTypeAWMS;
import org.wms.controller.order.OrdersTableModel;
import org.wms.model.batch.Batches;
import org.wms.model.common.ListType;
import org.wms.model.order.Orders;
import org.wms.view.common.TableStatusHighlighter;

/**
 * Order view show a table with the orders list
 * For each order the most important information are showed
 * 
 * Moreover a command bar it's placed for add/edit/delete an order
 * 
 * This view observe the ordersModel for updates
 * 
 * This view can be configured for input or output order type
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrdersView extends JPanel implements Observer {

	protected Orders ordersModel;
	
	protected Batches batchesModel;
	
	protected JTable ordersTable;
	
	protected OrdersTableModel tableModel;
	
	protected ListType ordersType;
	
	protected JButton btnAddOrder;
	
	protected JButton btnEditOrder;
	
	protected JButton btnDeleteOrder;
	
	/**
	 * Constructor
	 * 
	 * @param ordersModel reference to the orders model
	 * @param inputType true if the view is for input orders
	 */
	public OrdersView(Orders ordersModel, Batches batchesModel, ListType ordersType) {
		this.ordersModel = ordersModel;
		this.ordersType = ordersType;
		initComponent();
		initUI();
		ordersModel.addObserver(this);
		batchesModel.addObserver(this);
		updateValue();
	}
	
	/**
	 * Init graphic components
	 * to be placed in the gui
	 */
	protected void initComponent() {
		tableModel = new OrdersTableModel(ordersModel, ordersType);
		ordersTable = FactoryReferences.appStyle.getTableClass(tableModel);
		new TableStatusHighlighter(ordersTable, 3);

		btnAddOrder = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.PLUS.name());
		btnEditOrder = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.EDIT.name());
		btnDeleteOrder = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.MINUS.name());
		
	}
	
	/**
	 * Place each component in the gui
	 */
	protected void initUI() {
		setName(ordersType.name() + " ORDERS");
		
		setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane(ordersTable);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);
		
		List<JButton> buttons = new ArrayList<>();
		
		buttons.add(btnAddOrder);
		buttons.add(btnEditOrder);
		buttons.add(btnDeleteOrder);
		
		JPanel operationPanel = FactoryReferences.panels.getVerticalNavigationBarPanel(buttons);
		add(operationPanel, BorderLayout.EAST);		
		
	}
	
	/**
	 * Update the table
	 * if a row is selected, show edit/delete buttons
	 * otherwise they hide them
	 */
	protected void updateValue() {
		tableModel.fireTableDataChanged();
		if(ordersTable.getSelectedRow()!=-1) {
			btnEditOrder.setVisible(true);
			btnDeleteOrder.setVisible(true);
		} else {
			btnEditOrder.setVisible(false);
			btnDeleteOrder.setVisible(false);
		}
	}
	
	/** 
	 * Call update view
	 * 
	 * @see org.wms.view.order.OrdersView#updateValue()
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		updateValue();
	}
	
	/**
	 * @return get reference to add order button
	 */
	public JButton getBtnAddOrder() {
		return btnAddOrder;
	}
	
	/**
	 * @return get reference to edit order button
	 */
	public JButton getBtnEditOrder() {
		return btnEditOrder;
	}
	
	/**
	 * @return get reference to delete order button
	 */
	public JButton getBtnDeleteOrder() {
		return btnDeleteOrder;
	}
	
	/**
	 * @return get order type configured
	 */
	public ListType getOrdersType() {
		return ordersType;
	}
	
	/**
	 * @return get reference to orders table
	 */
	public JTable getOrdersTable() {
		return ordersTable;
	}
}
