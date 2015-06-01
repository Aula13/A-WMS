package org.wms.controller.orderedit;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;

import org.wms.config.Utils;
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
	protected OrderView view;
	
	/**
	 * Reference to the order model
	 */
	protected Order order;
	
	/**
	 * Reference to the orders model
	 */
	protected Orders ordersModel;
	
	/**
	 * Flag to store if it's a new order
	 */
	protected boolean isNew;

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
	protected boolean btnConfirmButtonAction() {
		boolean result;
		
		if(isNew) {
			order.setId(view.getSelectedId());
			order.setEmissionDate(view.getSelectedEmissionDate());
			order.setPriority(view.getSelectedPriority());
			
			if(!order.isDataComplete()) {
				Utils.msg.errorBox("Incomplete data!", "Error");
				return false;
			}
			
			result = ordersModel.addOrder(order);
		} else {
			order.setPriority(view.getSelectedPriority());
			result = ordersModel.updateOrder(order);
		}
		
		if(result)
			view.setVisible(false);
		else
			Utils.msg.errorBox("An appear during order saving", "Error");
		
		return result;
	}
	
	/**
	 * Cancel edit
	 * dispose order edit view
	 */
	protected void btnCancelButtonAction() {
		view.setVisible(false);
	}
}
