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
import org.wms.model.order.OrderType;
import org.wms.model.order.Orders;

public class OrdersView extends JPanel implements Observer {

	private Orders ordersModel;
	
	private JTable ordersTable;
	
	private OrdersTableModel tableModel;
	
	private OrderType ordersType;
	
	private JButton btnAddOrder;
	
	private JButton btnEditOrder;
	
	private JButton btnDeleteOrder;
	
	/**
	 * @param ordersModel reference to the orders model
	 * @param inputType true if the view is for input orders
	 */
	public OrdersView(Orders ordersModel, OrderType ordersType) {
		this.ordersModel = ordersModel;
		this.ordersType = ordersType;
		initComponent();
		initUI();
		ordersModel.addObserver(this);
		updateValue();
	}
	
	private void initComponent() {
		tableModel = new OrdersTableModel(ordersModel, ordersType);
		ordersTable = FactoryReferences.appStyle.getTableClass(tableModel);

		btnAddOrder = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.PLUS.name());
		btnEditOrder = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.EDIT.name());
		btnDeleteOrder = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.MINUS.name());
		
	}
	
	private void initUI() {
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
	
	private void updateValue() {
		tableModel.fireTableDataChanged();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updateValue();
	}
	
	public JButton getBtnAddOrder() {
		return btnAddOrder;
	}
	
	public JButton getBtnEditOrder() {
		return btnEditOrder;
	}
	
	public JButton getBtnDeleteOrder() {
		return btnDeleteOrder;
	}
	
	public OrderType getOrdersType() {
		return ordersType;
	}
	
	public JTable getOrdersTable() {
		return ordersTable;
	}
}
