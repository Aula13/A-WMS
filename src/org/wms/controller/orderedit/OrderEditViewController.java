package org.wms.controller.orderedit;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;
import it.rmautomazioni.view.common.MessageBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.wms.model.order.Order;
import org.wms.model.order.Orders;
import org.wms.view.orderedit.OrderEditView;

public class OrderEditViewController {

	private OrderEditView view;
	
	private Order order;
	
	private Orders ordersModel;
	
	private boolean isNew;

	public OrderEditViewController(OrderEditView view, Order order,
			Orders ordersModel, boolean isNew) {
		super();
		this.view = view;
		this.order = order;
		this.ordersModel = ordersModel;
		this.isNew = isNew;
		
		new AbstractJButtonActionListener(view.getConfirmButton()) {
			
			@Override
			public void actionTriggered() {
				boolean result;
				
				if(isNew) {
					order.setId(view.getSelectedId());
					order.setEmissionDate(view.getSelectedEmissionDate());
					order.setPriority(view.getSelectedPriority());
					
					if(!order.isDataComplete()) {
						MessageBox.errorBox("Incomplete data!", "Error");
						return;
					}
					
					result = ordersModel.addOrder(order);
				} else {
					order.setPriority(view.getSelectedPriority());
					result = ordersModel.updateOrder(order);
				}
				
				if(result)
					view.setVisible(false);
				else
					MessageBox.errorBox("An appear during order saving", "Error");
				
			}
		};
		
		new AbstractJButtonActionListener(view.getCancelButton()) {
			
			@Override
			public void actionTriggered() {
				view.setVisible(false);
			}
		};
	}	
}
