package org.wms.model.batch;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.wms.model.common.ListType;
import org.wms.model.common.Status;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;

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
	
	/**
	 * List of the OrderRow that this order contains
	 */
	@OneToMany(fetch=FetchType.EAGER, mappedBy="referredBatch", cascade=CascadeType.REMOVE)
	protected List<BatchRow> batchRows = new ArrayList<>();
	
	@Column(name="batch_status", nullable=false)
	protected Status batchStatus = Status.WAITING;

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

	public List<BatchRow> getRows() {
		return batchRows;
	}

	public Status getBatchStatus() {
		return batchStatus;
	}
	
}
