package org.wms.controller.orderedit;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;

public class MaterialListTableModel extends AbstractTableModel {
	
	private Order order;
	
	private String[] headers = {"Code", "Quantity"};
	
	public MaterialListTableModel(Order order) {
		super();
		this.order = order;
	}

	@Override
	public int getRowCount() {
		return order.getMaterials().size();		
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
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		OrderRow row = (OrderRow) order.getMaterials().toArray()[rowIndex]; 
		
		switch (columnIndex) {
		case 0:
			return row.getMaterial().getCode();
		case 1:
			return row.getQuantity();
		default:
			break;
		}
		
		return "Unknow column: " + columnIndex;
	}
	

}
