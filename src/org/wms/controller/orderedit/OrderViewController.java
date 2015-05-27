package org.wms.controller.orderedit;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;
import it.rmautomazioni.view.common.MessageBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.wms.model.order.Order;
import org.wms.model.order.Orders;
import org.wms.view.orderedit.OrderView;

/**
 * Controller for order edit view
 * 
 * manage all the view actions and connections between OrderEditView and Order model
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrderViewController {

	/**
	 * Reference to the order edit view
	 */
	private OrderView view;
	
	/**
	 * Reference to the order model
	 */
	private Order order;
	
	/**
	 * Reference to the orders model
	 */
	private Orders ordersModel;
	
	/**
	 * Flag to store if it's a new order
	 */
	private boolean isNew;

	public OrderViewController(OrderView view, Order order,
			Orders ordersModel, boolean isNew) {
		super();
		this.view = view;
		this.order = order;
		this.ordersModel = ordersModel;
		this.isNew = isNew;
		
		new AbstractJButtonActionListener(view.getConfirmButton()) {
			
			@Override
			public void actionTriggered() {
				btnConfirmButtonAction();			
			}
		};
		
		new AbstractJButtonActionListener(view.getCancelButton()) {
			
			@Override
			public void actionTriggered() {
				btnCancelButtonAction();
			}
		};
	}	
	
	/**
	 * On user confirm
	 * If it's new create a new order
	 * otherwise update the existent order
	 * show an error if the data are incomplete
	 * show an error if problems appear during save process
	 * 
	 * if all it's ok dispose the edit view
	 */
	private void btnConfirmButtonAction() {
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
	
	/**
	 * Cancel edit
	 * dispose order edit view
	 */
	private void btnCancelButtonAction() {
		view.setVisible(false);
	}
}
