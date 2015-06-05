package org.wms.controller.batch;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.wms.model.batch.Batch;
import org.wms.model.batch.Batches;
import org.wms.model.order.Order;

/**
 * Batches table model
 * 
 * implements standard methods for a table model
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class BatchesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 295473410143480706L;

	/**
	 * Reference to batchesModel to fetch batches
	 */
	protected Batches batchesModel;
	
	/**
	 * Columns name
	 */
	protected String[] headers = {"Code", "Type", "Priority", "Status", "Allocated"};
	
	/**
	 * cache of the batches list to reduce the #call to batchesModel
	 */
	protected List<Batch> batches;
	
	/**
	 * Constructor
	 * 
	 * @param batchesModel reference to the batchesModel
	 */
	public BatchesTableModel(Batches batchesModel) {
		super();
		this.batchesModel = batchesModel;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		batches = batchesModel.getUnmodificableBatchList();
		return batches.size();
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
		Batch batch = batches!=null? 
				batches.get(rowIndex) : 
				(Batch) batchesModel.getUnmodificableBatchList().toArray()[rowIndex];
		
		switch (columnIndex) {
		case 0:
			return batch.getId();
		case 1:
			return batch.getType().name();
		case 2:
			return batch.getPriority().name();
		case 3:
			return batch.getBatchStatus().name();
		case 4:
			return batch.isAllocated();
		default:
			break;
		}
		
		return "Unknow column: " + columnIndex;
	}

}
