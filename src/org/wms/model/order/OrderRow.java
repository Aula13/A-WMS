package org.wms.model.order;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;

@Entity
@Table(name="wms_order_row")
public class OrderRow implements Serializable {

	private static final long serialVersionUID = -4341205797647447187L;

	@Id
	@GeneratedValue
	@Column(name="order_row_id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name="material_id")
	private Material material;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="allocated")
	private boolean allocated = false;
	
	@Column(name="completed")
	private boolean completed = false;

	public OrderRow() {
	}
	
	public OrderRow(Order order, Material material, int quantity) {
		super();
		this.order = order;
		this.material = material;
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public boolean setQuantity(int quantity) {
		if(quantity<0)
			return false;
		this.quantity = quantity;
		return true;
	}

	public Order getOrder() {
		return order;
	}

	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public boolean isAllocated() {
		return allocated;
	}
	
	public void setAllocated() {
		this.allocated = true;
	}

	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted() {
		this.completed = true;
	}
	
	public boolean isEditable() {
		return !(allocated || completed);
	}
	
	public boolean isDataComplete() {
		if(order==null)
			return false;
		if(material==null)
			return false;
		if(quantity==0)
			return false;
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof OrderRow)
			return id == ((OrderRow) obj).id;
		return super.equals(obj);
	}
}
