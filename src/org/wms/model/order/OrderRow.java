package org.wms.model.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Order")
public class OrderRow {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OrderId")
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MaterialId")
	private Material material;
	
	@Column(name="Quantity")
	private int quantity;

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
