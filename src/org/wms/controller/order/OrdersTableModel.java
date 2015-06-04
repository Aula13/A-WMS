package org.wms.controller.order;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.wms.model.common.ListType;
import org.wms.model.order.Order;
import org.wms.model.order.Orders;

/**
 * Orders table model
 * 
 * implements standard methods for a table model
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrdersTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 295473410143480706L;

	/**
	 * Reference to ordersModel to fetch orders
	 */
	protected Orders ordersModel;
	
	/**
	 * Columns name
	 */
	protected String[] headers = {"Code", "Emission date", "Priority", "Order status", "Complete %", "Allocation %"};
	
	/**
	 * Type of order to show 
	 */
	protected ListType orderType;
	
	/**
	 * cache of the orders list to reduce the #call to ordersModel
	 */
	protected List<Order> orders;
	
	/**
	 * Constructor
	 * 
	 * @param ordersModel reference to the ordersModel
	 * @param orderType order type to manage
	 */
	public OrdersTableModel(Orders ordersModel, ListType orderType) {
		super();
		this.ordersModel = ordersModel;
		this.orderType = orderType;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		orders = ordersModel.getUnmodificableOrderList(orderType);
		return orders.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return headers.length;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		return headers[column];
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Order order = orders!=null? 
				orders.get(rowIndex) : 
				(Order) ordersModel.getUnmodificableOrderList(orderType).toArray()[rowIndex];
		
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
