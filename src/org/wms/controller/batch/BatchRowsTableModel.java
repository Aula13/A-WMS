package org.wms.controller.batch;

import javax.swing.table.AbstractTableModel;

import org.wms.model.batch.Batch;
import org.wms.model.batch.BatchRow;

/**
 * Materials table model
 * 
 * implements standard methods for a table model
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class BatchRowsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 7985896633962121613L;

	/**
	 * Order model
	 * to fetch order row
	 */
	protected Batch batch;
	
	/**
	 * Columns name
	 */
	protected String[] headers = {"Code", "Order", "Order row", "Material", "Warehouse position", "Quantity"};
	
	/**
	 * Costructor
	 * 
	 * @param batch reference to the order model
	 */
	public BatchRowsTableModel(Batch batch) {
		super();
		this.batch = batch;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return batch.getRows().size();		
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
		BatchRow row = (BatchRow) batch.getRows().toArray()[rowIndex]; 
		
		switch (columnIndex) {
		case 0:
			return row.getId();
		case 1:
			return row.getReferredOrderRow().getOrder().getId();
		case 2:
			return row.getReferredOrderRow().getId();
		case 3:
			return row.getReferredOrderRow().getMaterial().getCode();
		case 4:
			return row.getJobWarehouseCell().getPublicId();
		case 5:
			return row.getQuantity();
		default:
			break;
		}
		
		return "Unknow column: " + columnIndex;
	}
	

}
