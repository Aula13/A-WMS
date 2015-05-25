package org.wms.model.order;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	@Column(name="Id")
	private long id;
	
	@Column(name="EmissionDate", nullable=false)
	private Date emissionDate;
	
	@Column(name="Priority", nullable=false)
	private Priority priority = Priority.LOW;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<OrderRow> rows = new HashSet<>();
	
	@Column(name="OrderType", nullable=false)
	private OrderType type; 
	
	private OrderStatus orderStatus = OrderStatus.WAITING;
	
	private float completePercentual = 0.0f;
	
	private float allocationPercentual = 0.0f;
	
	@Column(name="DoneDate")
	private Date doneDate;
	
	public Order() {
	}
	
	public Order(long id, Date emissionDate, OrderType orderType) {
		super();
		this.id = id;
		this.emissionDate = emissionDate;
		this.type = orderType;
	}

	public Order(long id, Date emissionDate, OrderType orderType, Priority priority) {
		super();
		this.id = id;
		this.emissionDate = emissionDate;
		this.type = orderType;
		this.priority = priority;
	}

	public Order(long id, Date emissionDate, OrderType orderType, Priority priority,
			Set<OrderRow> rows) {
		super();
		this.id = id;
		this.emissionDate = emissionDate;
		this.type = orderType;
		this.priority = priority;
		this.rows = rows;
	}

	public long getId() {
		return id;
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

	public Set<OrderRow> getMaterials() {
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
