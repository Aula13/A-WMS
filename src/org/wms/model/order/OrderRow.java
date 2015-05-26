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
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof OrderRow)
			return id == ((OrderRow) obj).id;
		return super.equals(obj);
	}
}
