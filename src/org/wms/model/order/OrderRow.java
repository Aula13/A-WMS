package org.wms.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="OrderRow")
public class OrderRow implements Serializable {
	
	private static final long serialVersionUID = -4341205797647447187L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Id")
	private Order order;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Code")
	private Material material;
	
	@Column(name="Quantity")
	private int quantity;

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

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public Material getMaterial() {
		return material;
	}	
}
