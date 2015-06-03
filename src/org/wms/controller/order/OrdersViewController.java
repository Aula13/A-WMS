package org.wms.controller.order;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;

import java.util.Date;

import org.wms.config.Utils;
import org.wms.controller.common.AbstractTableSelectionListener;
import org.wms.model.material.Materials;
import org.wms.model.order.Order;
import org.wms.model.order.OrderType;
import org.wms.model.order.Orders;
import org.wms.view.order.OrderView;
import org.wms.view.order.OrdersView;

/**
 * Controller for orders view
 * 
 * manage all the view actions and connections between OrdersView and OrdersModel
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrdersViewController {

	/**
	 * Reference to the order view
	 */
	protected OrdersView view;
	
	/**
	 * Reference to the orders model
	 */
	protected Orders ordersModel;

	/**
	 * Reference to the materials model
	 * need for provide materials list
	 * to the edit/show order view
	 */
	protected Materials materialsModel;
	
	/**
	 * Type of order to manage
	 */
	protected OrderType orderType;
	
	/**
	 * Order details view JDialog reference
	 */
	protected OrderView editOrderDialog;
	
	/**
	 * Constructor
	 * add action to view components
	 * 
	 * @param view reference to the orderview
	 * @param ordersModel reference to the orders model
	 * @param materialsModel reference to the material model
	 */
	public OrdersViewController(OrdersView view, Orders ordersModel, Materials materialsModel) {
		super();
		this.view = view;
		this.ordersModel = ordersModel;
		this.materialsModel = materialsModel;
		
		orderType = view.getOrdersType();
		
		view.getBtnDeleteOrder().setVisible(false);
		view.getBtnEditOrder().setVisible(false);
		
		new AbstractJButtonActionListener(view.getBtnAddOrder()) {
			
			@Override
			public void actionTriggered() {
				btnAddOrderAction();
			}
		};
		
		new AbstractJButtonActionListener(view.getBtnEditOrder()) {
			
			@Override
			public void actionTriggered() {
				btnEditOrderAction();
			}
		};
		
		new AbstractJButtonActionListener(view.getBtnDeleteOrder()) {
			
			@Override
			public void actionTriggered() {
				btnDeleteOrderAction();
			}
		};
		
		view.getOrdersTable().addMouseListener(new AbstractTableSelectionListener() {
			
			@Override
			public void validSelectionTrigger(boolean doubleClick, int rowIndex, boolean requireMenu) {
				tblOrdersValidSelectionAction(doubleClick, rowIndex);
			}
			
			@Override
			public void invalidSelectionTriggered() {
				tblOrdersInvalidSelectionAction();
			}
		});
		
	}
	
	
	/**
	 * This method launch the order details view
	 * and its controller
	 * 
	 * @param order order to edit/show
	 * @param isNew true if the model is a new model, false if it's an existent model
	 */
	protected void launchOrderEditView(Order order, boolean isNew){
		editOrderDialog = new OrderView(order, materialsModel.getUnmodificableMaterialList(), isNew);
		new OrderViewController(editOrderDialog, order, ordersModel, isNew);
		editOrderDialog.setVisible(true);
	}
	
	/**
	 * This method call the order details view
	 * to create a new order
	 */
	protected void btnAddOrderAction() {
		Order order = new Order(0l, new Date(), orderType);
		launchOrderEditView(order, true);
	}
	
	/**
	 * This method call the order details view
	 * to edit an existent order
	 */
	protected boolean btnEditOrderAction() {
		if(view.getOrdersTable().getSelectedRow()==-1) {
			Utils.msg.errorBox("No order selected", "Error");
			return false;
		}
		
		int rowIndex = view.getOrdersTable().getSelectedRow();
		
		Order order = ordersModel.getUnmodificableOrderList(orderType).get(rowIndex);
		launchOrderEditView(order, false);
		return true;
	}
	
	/**
	 * This method delete an existent order
	 * selected in the table
	 * if it's possible
	 */
	protected boolean btnDeleteOrderAction() {
		if(view.getOrdersTable().getSelectedRow()==-1) {
			Utils.msg.errorBox("No order selected", "Error");
			return false;
		}
		
		int rowIndex = view.getOrdersTable().getSelectedRow();
		
		Order order = ordersModel.getUnmodificableOrderList(orderType).get(rowIndex);
		
		if(!order.isEditable()) {
			Utils.msg.errorBox("The order is not editable", "Error");
			return false;
		}
		
		if(!order.canDelete()) {
			Utils.msg.errorBox("The order can't be deleted", "Error");
			return false;
		}
		
		if(Utils.msg.questionBox("Are you sure to delete the order " + order.getId(), "Confirm")==0) {
			if(!ordersModel.deleteOrder(order)) {
				Utils.msg.errorBox("Error during order deleting operation", "Error");
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * When the user selected an order in table
	 * the delete and edit buttons will be enabled
	 * 
	 * Moreover if the user press twice
	 * the details view will be automatically showed
	 * 
	 * @param doubleClick if the user press mouse button twice
	 * @param rowIndex index of the table selection
	 */
	protected void tblOrdersValidSelectionAction(boolean doubleClick, int rowIndex) {
		view.getBtnDeleteOrder().setVisible(true);
		view.getBtnEditOrder().setVisible(true);
		
		if(doubleClick) {
			Order order = ordersModel.getUnmodificableOrderList(orderType).get(rowIndex);
			launchOrderEditView(order, false); 
		}
	}
	
	/**
	 * Hide edit and delete buttons
	 */
	protected void tblOrdersInvalidSelectionAction() {
		view.getBtnDeleteOrder().setVisible(false);
		view.getBtnEditOrder().setVisible(false);
	}
}
