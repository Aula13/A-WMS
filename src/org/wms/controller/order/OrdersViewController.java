package org.wms.controller.order;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;
import it.rmautomazioni.view.common.MessageBox;

import java.text.ParseException;
import java.util.Date;

import org.wms.controller.common.AbstractTableSelectionListener;
import org.wms.controller.orderedit.OrderEditViewController;
import org.wms.model.common.ModelReference;
import org.wms.model.order.Materials;
import org.wms.model.order.Order;
import org.wms.model.order.OrderType;
import org.wms.model.order.Orders;
import org.wms.view.order.OrdersView;
import org.wms.view.orderedit.OrderEditView;

public class OrdersViewController {

	private OrdersView view;
	
	private Orders ordersModel;

	private Materials materialsModel;
	
	private OrderType orderType;
	
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
	
	
	private void launchOrderEditView(Order order, boolean isNew){
		OrderEditView editOrderDialog = new OrderEditView(order, materialsModel.getUnmodificableMaterialList(), isNew);
		new OrderEditViewController(editOrderDialog, order, ordersModel, isNew);
		editOrderDialog.setVisible(true);
	}
	
	private void btnAddOrderAction() {
		Order order = new Order(0l, new Date(), orderType);
		launchOrderEditView(order, true);
	}
	
	private void btnEditOrderAction() {
		if(view.getOrdersTable().getSelectedRow()==-1) {
			MessageBox.errorBox("No order selected", "Error");
			return;
		}
		
		int rowIndex = view.getOrdersTable().getSelectedRow();
		
		Order order = ordersModel.getUnmodificableOrderList(orderType).get(rowIndex);
		launchOrderEditView(order, false);
	}
	
	private void btnDeleteOrderAction() {
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
	
	private void tblOrdersValidSelectionAction(boolean doubleClick, int rowIndex) {
		if(doubleClick) {
			Order order = ordersModel.getUnmodificableOrderList(orderType).get(rowIndex);
			launchOrderEditView(order, false); 
		}
		
		view.getBtnDeleteOrder().setVisible(true);
		view.getBtnEditOrder().setVisible(true);
	}
	
	private void tblOrdersInvalidSelectionAction() {
		view.getBtnDeleteOrder().setVisible(false);
		view.getBtnEditOrder().setVisible(false);
	}
}
