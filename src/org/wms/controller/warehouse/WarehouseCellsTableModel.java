package org.wms.controller.warehouse;

import javax.swing.table.AbstractTableModel;

import org.wms.model.warehouse.WarehouseCell;
import org.wms.model.warehouse.WarehouseShelf;

/**
 * Materials table model
 * 
 * implements standard methods for a table model
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WarehouseCellsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 7985896633962121613L;

	/**
	 * Order model
	 * to fetch order row
	 */
	protected WarehouseShelf shelf;
	
	/**
	 * Columns name
	 */
	protected String[] headers = {"Code", "Material", "Q.ty", "Res. q.ty"};
	
	/**
	 * Costructor
	 * 
	 * @param shelf reference to the order model
	 */
	public WarehouseCellsTableModel(WarehouseShelf shelf) {
		super();
		this.shelf = shelf;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return shelf.getUnmodificableListCells().size();		
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
		//TODO: Make q.ty editable
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		WarehouseCell row = (WarehouseCell) shelf.getUnmodificableListCells().toArray()[rowIndex]; 
		
		switch (columnIndex) {
		case 0:
			return row.getPublicId();
		case 1:
			return row.getMaterial().getCode();
		case 2:
			return row.getQuantity();
		case 3:
			return row.getAlreadyReservedQuantity();
		default:
			break;
		}
		
		return "Unknow column: " + columnIndex;
	}
	

}
