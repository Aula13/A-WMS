package org.wms.controller.order;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;
import it.rmautomazioni.view.common.MessageBox;

import java.text.ParseException;
import java.util.Date;

import org.wms.controller.common.AbstractTableSelectionListener;
import org.wms.controller.orderedit.OrderViewController;
import org.wms.model.common.ModelReference;
import org.wms.model.order.Materials;
import org.wms.model.order.Order;
import org.wms.model.order.OrderType;
import org.wms.model.order.Orders;
import org.wms.view.order.OrdersView;
import org.wms.view.orderedit.OrderView;

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
	private OrdersView view;
	
	/**
	 * Reference to the orders model
	 */
	private Orders ordersModel;

	/**
	 * Reference to the materials model
	 * need for provide materials list
	 * to the edit/show order view
	 */
	private Materials materialsModel;
	
	/**
	 * Type of order to manage
	 */
	private OrderType orderType;
	
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
	private void launchOrderEditView(Order order, boolean isNew){
		OrderView editOrderDialog = new OrderView(order, materialsModel.getUnmodificableMaterialList(), isNew);
		new OrderViewController(editOrderDialog, order, ordersModel, isNew);
		editOrderDialog.setVisible(true);
	}
	
	/**
	 * This method call the order details view
	 * to create a new order
	 */
	private void btnAddOrderAction() {
		Order order = new Order(0l, new Date(), orderType);
		launchOrderEditView(order, true);
	}
	
	/**
	 * This method call the order details view
	 * to edit an existent order
	 */
	private void btnEditOrderAction() {
		if(view.getOrdersTable().getSelectedRow()==-1) {
			MessageBox.errorBox("No order selected", "Error");
			return;
		}
		
		int rowIndex = view.getOrdersTable().getSelectedRow();
		
		Order order = ordersModel.getUnmodificableOrderList(orderType).get(rowIndex);
		launchOrderEditView(order, false);
	}
	
	/**
	 * This method delete an existent order
	 * selected in the table
	 * if it's possible
	 */
	private void btnDeleteOrderAction() {
		//TODO: Check if a order is delete able
		
		if(view.getOrdersTable().getSelectedRow()==-1) {
			MessageBox.errorBox("No order selected", "Error");
			return;
		}
		
		int rowIndex = view.getOrdersTable().getSelectedRow();
		
		Order order = ordersModel.getUnmodificableOrderList(orderType).get(rowIndex);
		
		if(MessageBox.questionBox("Are you sure to delete the order " + order.getId(), "Confirm")==0)
			if(!ordersModel.deleteOrder(order))
				MessageBox.errorBox("Error during order deleting operation", "Error");
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
	private void tblOrdersValidSelectionAction(boolean doubleClick, int rowIndex) {
		if(doubleClick) {
			Order order = ordersModel.getUnmodificableOrderList(orderType).get(rowIndex);
			launchOrderEditView(order, false); 
		}
		
		view.getBtnDeleteOrder().setVisible(true);
		view.getBtnEditOrder().setVisible(true);
	}
	
	/**
	 * Hide edit and delete buttons
	 */
	private void tblOrdersInvalidSelectionAction() {
		view.getBtnDeleteOrder().setVisible(false);
		view.getBtnEditOrder().setVisible(false);
	}
}
