package org.wms.model.warehouse;

import static org.mockito.Matchers.isA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.wms.model.material.Material;

/**
 * Warehouse cell status and stocked material type
 * 
 * Include the business logic
 * Include hibernate annotations for persistence
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
@Entity
@Table(name="wms_warehouse_cell")
public class WarehouseCell {

	@Id
	@Column(name="warehouse_cell_id")
	protected long id;
	
	@ManyToOne
	@JoinColumn(name="warehouse_shelf_id", nullable=false)
	protected WarehouseShelf warehouseShelf;
	
	@Column(name="warehouse_cell_code")
	protected int code;
	
	@ManyToOne
	@JoinColumn(name="material_id")
	protected Material material;
	
	@Column(name="warehouse_material_quantity")
	protected int quantity;
	
	@Column(name="warehouse_material_res_quantity")
	protected int alreadyReservedQuantity;
	
	public WarehouseCell() {
		
	}

	public WarehouseCell(long id, Material material, int quantity,
			WarehouseShelf shelf) {
		super();
		this.id = id;
		this.material = material;
		this.quantity = quantity;
		this.warehouseShelf = shelf;
	}
	
	/**
	 * Get warehouse shelf where this cell
	 * is placed 
	 * 
	 * @return warehouse shelf
	 */
	public WarehouseShelf getWarehouseShelf() {
		return warehouseShelf;
	}
	
	/**
	 * Get already reserved quantity
	 * provided by batch creation algorithm
	 * 
	 * @return already reserved quantity
	 */
	public int getAlreadyReservedQuantity() {
		return alreadyReservedQuantity;
	}

	/**
	 * Set already reserved quantity
	 * 
	 * @param true if the already reserved quantity is set, false if the new value is not valid
	 */
	public boolean setAlreadyReservedQuantity(int alreadyReservedQuantity) {
		if(alreadyReservedQuantity>quantity)
			return false;
		if(alreadyReservedQuantity<0)
			return false;
		
		this.alreadyReservedQuantity = alreadyReservedQuantity;
		return true;
	}

	/**
	 * Get cell id
	 * 
	 * @return cell id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Get the id of this cell inside the warehouse
	 * more usefull for users
	 * 
	 * @return id with line and shelf identifier
	 */
	public String getPublicId() {
		return warehouseShelf.getPublicId() + "/" + id;
	}

	/**
	 * @return material stored in this cell
	 */
	public Material getMaterial() {
		return material;
	}
	
	/**
	 * @return quantity stored in this cell
	 */
	public int getQuantity() {
		return quantity;
	}
		
	/**
	 * Set cell material quantity
	 * the new value should be greater than zero and
	 * greater than the already reserved quantity
	 * 
	 * @param quantity
	 * @return true if the quantity is set, false if the new value is not valid
	 */
	public boolean setQuantity(int quantity) {
		if(quantity<0)
			return false;
		if(quantity<alreadyReservedQuantity)
			return false;
		this.quantity = quantity;
		return true;
	}
}
