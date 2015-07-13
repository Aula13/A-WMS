package org.wms.model.batch;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.wms.model.order.OrderRow;
import org.wms.model.warehouse.WarehouseCell;

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
	@Column(name="batch_row_id")
	protected long id;

	/**
	 * Batch that this batch row is referred
	 */
	@ManyToOne
	@JoinColumn(name="batch_id", nullable = false)
	protected Batch referredBatch;

	/**
	 * Order row that this work list row is referred
	 */
	@OneToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="order_row_id", nullable = false)
	protected OrderRow referredOrderRow;

	/**
	 * Warehouse cell where need pickup/place material
	 */
	@OneToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="warehouse_cell_id", nullable = false)
	protected WarehouseCell jobWarehouseCell;

	@Column(name="batch_row_quantity", nullable=false)
	protected int quantity = 0;
	
	public BatchRow() {

	}

	public BatchRow(long id, Batch referredBatch, OrderRow referredOrderRow) {
		super();
		this.id = id;
		this.referredBatch = referredBatch;
		this.referredOrderRow = referredOrderRow;
	}

	public long getId() {
		return id;
	}
	
	public OrderRow getReferredOrderRow() {
		return referredOrderRow;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public WarehouseCell getJobWarehouseCell() {
		return jobWarehouseCell;
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
