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

/**
 * OrderRow model
 * Include the business logic
 * Include hibernate annotations for persistence
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
@Entity
@Table(name="wms_order_row")
public class OrderRow implements Serializable {

	private static final long serialVersionUID = -4341205797647447187L;

	@Id
	@GeneratedValue
	@Column(name="order_row_id")
	private long id;
	
	/**
	 * Order to this order row is referred
	 */
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	/**
	 * Material to this order row is referred
	 */
	@ManyToOne
	@JoinColumn(name="material_id")
	private Material material;
	
	@Column(name="quantity")
	private int quantity;
	
	/**
	 * Allocated means this order row
	 * is in a input/output list
	 * and an operator is processing it
	 */
	@Column(name="allocated")
	private boolean allocated = false;
	
	/**
	 * Completed means this order row
	 * was in a input/output list
	 * and an operator has process it
	 */
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

	/**
	 * @return get material quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Update quantity
	 * if the new quantity is greater than 0
	 * 
	 * @param quantity
	 * @return true = quantity set; false = quantity<0
	 */
	public boolean setQuantity(int quantity) {
		if(quantity<0)
			return false;
		this.quantity = quantity;
		return true;
	}

	/**
	 * @return order that this order row is referred
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @return material to keep
	 */
	public Material getMaterial() {
		return material;
	}
	
	/**
	 * Update material
	 * 
	 * @param material
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	/**
	 * 
	 * Allocated means this order row
	 * is in a input/output list
	 * and an operator is processing it
	 * 
	 * @return this order row is allocated
	 */
	public boolean isAllocated() {
		return allocated;
	}
	
	/**
	 * Set this order row as allocated
	 */
	public void setAllocated() {
		this.allocated = true;
	}

	/**
	 * Completed means this order row
	 * was in a input/output list
	 * and an operator has process it
	 * 
	 * @return this order row is completed
	 */
	public boolean isCompleted() {
		return completed;
	}
	
	/**
	 * Set this order row as completed
	 */
	public void setCompleted() {
		this.completed = true;
	}
	
	/**
	 * 
	 * @return true if this order row isn't allocated or completed
	 */
	public boolean isEditable() {
		return !(allocated || completed);
	}
	
	/**
	 * Order row is valid
	 * if order, material are not valid
	 * or quantity<=0, the order row isn't valid 
	 * 
	 * @return order row data are complete
	 */
	public boolean isDataComplete() {
		if(order==null)
			return false;
		if(material==null)
			return false;
		if(quantity<=0)
			return false;
		return true;
	}
	
	/**
	 * Check equals order row id
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof OrderRow)
			return id == ((OrderRow) obj).id;
		return super.equals(obj);
	}
}
