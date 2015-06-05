package org.wms.model.batch;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.wms.model.common.ListType;
import org.wms.model.common.Priority;
import org.wms.model.common.Status;

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
	@Column(name="batch_id")
	protected long id;
	
	@Column(name="batch_type", nullable=false)
	protected ListType type; 
	
	@Column(name="priority", nullable=false)
	protected Priority priority = Priority.LOW;
	
	/**
	 * List of the OrderRow that this order contains
	 */
	@OneToMany(mappedBy="referredBatch", cascade=CascadeType.REMOVE)
	@LazyCollection(LazyCollectionOption.FALSE)
	protected List<BatchRow> batchRows = new ArrayList<>();
	
	@Column(name="batch_status", nullable=false)
	protected Status batchStatus = Status.WAITING;

	@Column(name="batch_allocated", nullable=false)
	protected boolean allocated = false;
	
	
	
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
	
	public List<BatchRow> getRows() {
		return batchRows;
	}

	public Status getBatchStatus() {
		return batchStatus;
	}
	
	public boolean isAllocated() {
		return allocated;
	}
	
}
