package org.wms.controller.orderedit;

import org.wms.model.order.Order;
import org.wms.view.orderedit.MaterialListView;

public class MaterialListViewController {

	private MaterialListView view;

	private Order order;

	public MaterialListViewController(MaterialListView view, Order order) {
		super();
		this.view = view;
		this.order = order;
		
		
	}	
}
