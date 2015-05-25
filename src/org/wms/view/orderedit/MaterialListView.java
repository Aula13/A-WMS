package org.wms.view.orderedit;

import javax.swing.JPanel;

import org.wms.model.order.Order;

public class MaterialListView extends JPanel {
	
	private Order order;

	public MaterialListView(Order order) {
		super();
		this.order = order;
	}	
}
