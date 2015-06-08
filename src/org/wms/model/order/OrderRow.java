package org.wms.model.order;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.wms.model.batch.BatchRow;
import org.wms.model.material.Material;

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
	protected long id;
	
	/**
	 * Order to this order row is referred
	 */
	@ManyToOne
	@JoinColumn(name="order_id", nullable = false)
	protected Order order;
	
	/**
	 * List of the OrderRow that this order contains
	 */
	@ManyToMany(mappedBy="referredOrderRow", cascade=CascadeType.REMOVE)
	@Fetch(FetchMode.SUBSELECT)
	protected Set<BatchRow> referredBatchRows = new HashSet<>();
	
	/**
	 * Material to this order row is referred
	 */
	@ManyToOne
	@JoinColumn(name="order_row_material_id")
	protected Material material;
	
	@Column(name="order_row_quantity", nullable=false)
	protected int quantity;
	
	@Column(name="order_row_picked_quantity", nullable=false)
	protected int pickedQuantity = 0;
	
	/**
	 * Allocated means this order row
	 * is in a input/output list
	 * and an operator is processing it
	 */
	@Column(name="order_row_allocated", nullable = false)
	protected boolean allocated = false;
	
	/**
	 * Completed means this order row
	 * was in a input/output list
	 * and an operator has process it
	 */
	@Column(name="order_row_completed", nullable = false)
	protected boolean completed = false;

	public OrderRow() {
	}
	
	public OrderRow(Order order, Material material, int quantity) {
		super();
		this.order = order;
		this.material = material;
		this.quantity = quantity;
	}

	/**
	 * @return order row id
	 */
	public long getId() {
		return id;
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
	public boolean setMaterial(Material material) {
		if(!isEditable())
			return false;
		this.material = material;
		return true;
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
		if(this.completed)
			return true;
		return quantity==pickedQuantity;
	}
	
	/**
	 * Set this order row as completed
	 */
	public void setCompleted() {
		this.completed = true;
	}
	
	/**
	 * Set picked quantity
	 * 
	 * @param pickedQuantity
	 */
	public void setPickedQuantity(int pickedQuantity) {
		this.pickedQuantity = pickedQuantity;
	}
	
	/**
	 * Set delta picked quantity if it's valid
	 * 
	 * @param deltaPickedQuantity
	 * @return true=picked quantity updated, false=deltaPickQuantity greater than residual requested quantity
	 */
	public boolean setDeltaPickedQuantity(int deltaPickedQuantity) {
		if(deltaPickedQuantity>(quantity-pickedQuantity))
			return false;
		this.pickedQuantity -= deltaPickedQuantity;
		return true;
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
