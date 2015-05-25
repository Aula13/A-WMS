package org.wms.view.orderedit;

import javax.swing.JPanel;

import org.wms.model.order.Order;

public class OrderEditView extends JPanel {
	
	private Order order;
	
	private MaterialListView materialListView;

	/**
	 * @param order to modify
	 */
	public OrderEditView(Order order) {
		super();
		this.order = order;
	}
	
	public OrderEditView() {
		order = new Order();
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub

	}
	
	private void initUI() {
		// TODO Auto-generated method stub

	}
	
}
