package org.wms.model.batch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.wms.model.common.ListType;
import org.wms.model.common.Priority;
import org.wms.model.common.Status;
import org.wms.model.warehouse.WarehouseCell;

/**
 * Batch is the job list for an operator (a warehouse tour)
 * Include the business logic
 * Include hibernate annotations for persistence
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
@Entity
@Table(name="wms_batch")
public class Batch {

	@Id
	@GeneratedValue
	@Column(name="batch_id")
	protected long id;
	
	@Column(name="batch_type", nullable=false)
	protected ListType type; 
	
	@Column(name="batch_priority", nullable=false)
	protected Priority priority = Priority.LOW;
	
	/**
	 * List of the OrderRow that this order contains
	 */
	@OneToMany(mappedBy="referredBatch", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	//@Fetch(FetchMode.JOIN)
	protected Set<BatchRow> batchRows = new HashSet<>();
	
	@Column(name="batch_status", nullable=false)
	protected Status batchStatus = Status.WAITING;

	@Column(name="batch_allocated", nullable=false)
	protected int allocated = 0;
	
	public static int capacity = 100;
	
	public Batch() {
	
	}

	public Batch(long id, ListType type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	public long getId() {
		return id;
	}

	public ListType getType() {
		return type;
	}

	public Priority getPriority() {
		return priority;
	}
	
	public void addRow(BatchRow row) {
		batchRows.add(row);
	}
	
	public List<BatchRow> getRows() {
		return new ArrayList<BatchRow>(batchRows);
	}

	public Status getBatchStatus() {
		return batchStatus;
	}
	
	public boolean isAllocated() {
		return allocated==1;
	}
	
	public int getActualQuantity() {
		OptionalInt actualQuantity = batchRows.stream()
				.mapToInt(row -> row.getQuantity())
				.reduce((row1, row2) -> row1+row2);
		if(actualQuantity.isPresent())
			return actualQuantity.getAsInt();
		return 0;
	}
	
	public boolean isFull() {
		return getActualQuantity()>=capacity;
	}
	
	public boolean checkCanAddRow(int quantity) {
		return getActualQuantity()+quantity<=capacity;
	}
	
	public boolean setAsAllocated() {
		for (BatchRow batchRow : batchRows)
			batchRow.getReferredOrderRow().setAllocated();
		for (BatchRow batchRow : batchRows)
			batchRow.getReferredOrderRow().getOrder().updateAllocatedPercentual();
		
		batchStatus=Status.ASSIGNED;
		allocated = 1;
		return true;
	}
	
	public boolean setAsCompleted() {
		if(batchStatus!=Status.ASSIGNED) {
			return false;
		}
		for (BatchRow batchRow : batchRows) {
			batchRow.getReferredOrderRow().setCompleted();
			WarehouseCell jobCell = batchRow.getJobWarehouseCell();
			int reservedQty = jobCell.getAlreadyReservedQuantity();
			int qty = jobCell.getQuantity();
			
			jobCell.setAlreadyReservedQuantity(reservedQty-batchRow.getQuantity());
			jobCell.setQuantity(qty-batchRow.getQuantity());
		}
		for (BatchRow batchRow : batchRows)
			batchRow.getReferredOrderRow().getOrder().updateCompletedPercentual();
		
		batchStatus=Status.COMPLETED;
		allocated = 1;
		return true;
	}
	
}
