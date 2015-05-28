package org.wms.controller.orderedit;

import javax.swing.table.AbstractTableModel;

import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;

/**
 * Materials table model
 * 
 * implements standard methods for a table model
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrderRowsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 7985896633962121613L;

	/**
	 * Order model
	 * to fetch order row
	 */
	protected Order order;
	
	/**
	 * Columns name
	 */
	protected String[] headers = {"Code", "Quantity", "Allocated", "Completed"};
	
	/**
	 * Costructor
	 * 
	 * @param order reference to the order model
	 */
	public OrderRowsTableModel(Order order) {
		super();
		this.order = order;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return order.getUnmodificableMaterials().size();		
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
	
	/** 
	 * The first two row are editable 
	 * only if the orderrow is editable
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex<2) {
			OrderRow row = (OrderRow) order.getUnmodificableMaterials().toArray()[rowIndex];
			return row.isEditable();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
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
