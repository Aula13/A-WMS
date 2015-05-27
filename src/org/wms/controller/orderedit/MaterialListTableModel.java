package org.wms.controller.orderedit;

import javax.swing.table.AbstractTableModel;

import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;

public class MaterialListTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 7985896633962121613L;

	private Order order;
	
	private String[] headers = {"Code", "Quantity", "Allocated", "Completed"};
	
	public MaterialListTableModel(Order order) {
		super();
		this.order = order;
	}

	@Override
	public int getRowCount() {
		return order.getUnmodificableMaterials().size();		
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
		if(columnIndex<2) {
			OrderRow row = (OrderRow) order.getUnmodificableMaterials().toArray()[rowIndex];
			return row.isEditable();
		}
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		OrderRow row = (OrderRow) order.getUnmodificableMaterials().toArray()[rowIndex]; 
		
		switch (columnIndex) {
		case 0:
			return row.getMaterial().getCode();
		case 1:
			return row.getQuantity();
		case 2:
			return row.isAllocated();
		case 3:
			return row.isCompleted();
		default:
			break;
		}
		
		return "Unknow column: " + columnIndex;
	}
	

}
