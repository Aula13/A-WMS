package org.wms.model.batch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.wms.model.order.OrderRow;

/**
 * Work list ror for a work list
 * Include the business logic
 * Include hibernate annotations for persistence
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
@Entity
@Table(name="wms_batch_row")
public class BatchRow {

	@Id
	@GeneratedValue
	@Column(name="batch_id")
	protected long id;
	
	/**
	 * Batch that this batch row is referred
	 */
	@ManyToOne
	@JoinColumn(name="batch_id", nullable=false)
	protected Batch referredBatch;
	
	/**
	 * Order row that this work list row is referred
	 */
	@ManyToOne
	@JoinColumn(name="order_row_id")
	protected OrderRow referredOrderRow;
	
	public BatchRow() {
	
	}
	
	public BatchRow(long id, Batch referredBatch, OrderRow referredOrderRow) {
		super();
		this.id = id;
		this.referredBatch = referredBatch;
		this.referredOrderRow = referredOrderRow;
	}



	/**
	 * 
	 * 
	 * @return the batch that this batch row is assegned
	 */
	public Batch getReferredBatch() {
		return referredBatch;
	}
	
	/**
	 * Set batch for this batch row
	 * 
	 * @param batch
	 */
	public void setReferredBatch(Batch referredBatch) {
		this.referredBatch = referredBatch;
	}
}
