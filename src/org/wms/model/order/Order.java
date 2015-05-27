package org.wms.model.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * check if all the data are provided for the order
	 * 
	 * @return true = the data are complete 
	 */
	public boolean isDataComplete() {
		if(getId()==0l)
			return false;
		
		if(getEmissionDate()==null)
			return false;
		return true;
	}

	public Date getEmissionDate() {
		return emissionDate;
	}
	
	public void setEmissionDate(Date emissionDate) {
		this.emissionDate = emissionDate;
	}
	
	public OrderType getType() {
		return type;
	}

	public Priority getPriority() {
		return priority;
	}
	
	public boolean setPriority(Priority priority) {
		if(!isEditable())
			return false;
		this.priority = priority;
		return true;
	}

	public List<OrderRow> getUnmodificableMaterials() {
		return Collections.unmodifiableList(rows);
	}
	
	public boolean addMaterial(OrderRow orderRow) {
		if(!isEditable() || !orderRow.isDataComplete())
			return false;
		rows.add(orderRow);
		updateAllocatedPercentual();
		updateCompletedPercentual();
		return false;
	}
	
	public boolean removeMaterial(OrderRow orderRow) {
		if(!isEditable())
			return false;
		rows.remove(orderRow);
		updateAllocatedPercentual();
		updateCompletedPercentual();
		return true;
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
	
	public void setMaterialAsAllocated(Material material) {
		setMaterialAsAllocated(material.getCode());
	}
	
	public void setMaterialAsAllocated(long materialId) {
		Optional<OrderRow> rowToAllocate = rows.stream()
			.filter(row -> row.getMaterial().getCode()==materialId)
			.findFirst();
		
		if(rowToAllocate.isPresent()) {
			rowToAllocate.get().setAllocated();		
			updateAllocatedPercentual();
		}
	}
	
	public void setMaterialAsCompleted(Material material) {
		setMaterialAsCompleted(material.getCode());
	}
	
	public void setMaterialAsCompleted(long materialId) {
		Optional<OrderRow> rowToComplete = rows.stream()
			.filter(row -> row.getMaterial().getCode()==materialId)
			.findFirst();
		
		if(rowToComplete.isPresent()) {
			rowToComplete.get().setCompleted();
			updateCompletedPercentual();
		}
	}
	
	public void updateAllocatedPercentual() {
		if(rows.size()==0) {
			allocationPercentual=0.0f;
			return;
		}
		
		int allocatedOrderRowSize = rows.stream()
				.filter(row -> row.isAllocated())
				.collect(Collectors.toList()).size();
		allocationPercentual = (((float) allocatedOrderRowSize)/rows.size())*100;
	}
	
	public void updateCompletedPercentual() {
		if(rows.size()==0) {
			completePercentual=0.0f;
			return;
		}
		
		int compleOrderRowRowSize = rows.stream()
				.filter(row -> row.isCompleted())
				.collect(Collectors.toList()).size();
		completePercentual = (((float) compleOrderRowRowSize)/rows.size())*100;
	}
	
	public boolean isEditable() {
		return allocationPercentual<100.0f && completePercentual<100.0f;
	}
}
