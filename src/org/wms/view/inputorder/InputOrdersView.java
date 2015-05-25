package org.wms.view.inputorder;

import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.wms.controller.inputorder.OrdersTableModel;
import org.wms.model.order.Orders;

public class InputOrdersView extends JPanel implements Observer {

	private Orders ordersModel;
	
	private JTable inputOrdersTable;
	
	private OrdersTableModel tableModel;
	
	public InputOrdersView(Orders ordersModel) {
		this.ordersModel = ordersModel;
		initComponent();
		initUI();
		ordersModel.addObserver(this);
		updateValue();
	}
	
	private void initComponent() {
		tableModel = new OrdersTableModel(ordersModel);
		inputOrdersTable = FactoryReferences.appStyle.getTableClass(tableModel);
		
	}
	
	private void initUI() {
		setName("INPUT ORDERS");
		
		setLayout(new GridLayout(1,1));
		
		JScrollPane scrollPane = new JScrollPane(inputOrdersTable);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
	}
	
	private void updateValue() {
		tableModel.fireTableDataChanged();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updateValue();
	}
	
}
