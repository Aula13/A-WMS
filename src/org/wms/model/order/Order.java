package org.wms.model.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="wms_order")
public class Order {
	
	@Id
	@Column(name="order_id")
	private long id;
	
	@Column(name="emission_date", nullable=false)
	private Date emissionDate;
	
	@Column(name="prioriry", nullable=false)
	private Priority priority = Priority.LOW;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="order", cascade=CascadeType.REMOVE)
	private List<OrderRow> rows = new ArrayList<>();
	
	@Column(name="order_type", nullable=false)
	private OrderType type; 
	
	private OrderStatus orderStatus = OrderStatus.WAITING;
	
	private float completePercentual = 0.0f;
	
	private float allocationPercentual = 0.0f;
	
	@Column(name="done_date")
	private Date doneDate;
	
	public Order() {
	}
	
	
	
	public Order(long id) {
		this(id,
			new Date(),
			OrderType.INPUT,
			Priority.LOW);
	}



	public Order(long id, Date emissionDate, OrderType orderType) {
		super();
		this.id = id;
		this.emissionDate = emissionDate;
		this.type = orderType;
	}

	public Order(long id, Date emissionDate, OrderType orderType, Priority priority) {
		this(id, emissionDate, orderType);
		this.priority = priority;
	}

	public Order(long id, Date emissionDate, OrderType orderType, Priority priority,
			List<OrderRow> rows) {
		this(id, emissionDate, orderType, priority);
		this.rows = rows;
	}

	public long getId() {
		return id;
	}
	
	/**
	 * check if all the data are provided for the order
	 * 
	 * @return true = the data are complete 
	 */
	public boolean isDataComplete() {
		if(getEmissionDate()==null)
			return false;
		return true;
	}

	public Date getEmissionDate() {
		return emissionDate;
	}
	
	public OrderType getType() {
		return type;
	}

	public Priority getPriority() {
		return priority;
	}

	public List<OrderRow> getMaterials() {
		return rows;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public float getCompletePercentual() {
		return completePercentual;
	}

	public float getAllocationPercentual() {
		return allocationPercentual;
	}

	public Date getDoneDate() {
		return doneDate;
	}	
}
