package org.wms.controller.order;

import javax.swing.table.AbstractTableModel;

import org.wms.model.order.Order;
import org.wms.model.order.OrderType;
import org.wms.model.order.Orders;

public class OrdersTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 295473410143480706L;

	private Orders ordersModel;
	
	private String[] headers = {"Code", "Emission date", "Priority", "Order status", "Complete %", "Allocation %"};
	
	private OrderType orderType;
	
	public OrdersTableModel(Orders ordersModel, OrderType orderType) {
		super();
		this.ordersModel = ordersModel;
		this.orderType = orderType;
	}

	@Override
	public int getRowCount() {
		return ordersModel.getUnmodificableOrderList(orderType).size();
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return headers[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Order order = ordersModel.getUnmodificableOrderList(orderType).get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return order.getId();
		case 1:
			return order.getEmissionDate();
		case 2:
			return order.getPriority().name();
		case 3:
			return order.getOrderStatus().name();
		case 4:
			return order.getCompletePercentual();
		case 5:
			return order.getAllocationPercentual();
		default:
			break;
		}
		
		return "Unknow column: " + columnIndex;
	}

}
