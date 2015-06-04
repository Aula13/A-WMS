package org.wms.model.worklist;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.wms.model.order.OrderRow;
import org.wms.model.order.OrderStatus;
import org.wms.model.order.OrderType;

/**
 * Work list for an operator
 * Include the business logic
 * Include hibernate annotations for persistence
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
@Entity
@Table(name="wms_work_list")
public class WorkList {

	@Id
	@Column(name="work_list_id")
	protected long id;
	
	@Column(name="work_list_type", nullable=false)
	protected OrderType type; 
	
	/**
	 * List of the OrderRow that this order contains
	 */
	@OneToMany(fetch=FetchType.EAGER, mappedBy="worklist", cascade=CascadeType.REMOVE)
	protected List<OrderRow> rows = new ArrayList<>();
	
	@Column(name="work_list_status", nullable=false)
	protected OrderStatus orderStatus = OrderStatus.WAITING;

	public WorkList() {
	
	}

	public WorkList(long id, OrderType type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	public long getId() {
		return id;
	}

	public OrderType getType() {
		return type;
	}

	public List<OrderRow> getRows() {
		return rows;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	
}
