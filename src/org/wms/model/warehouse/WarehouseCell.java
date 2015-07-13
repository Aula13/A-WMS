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

	public WarehouseCell(long id, Material material, int quantity) {
		super();
		this.id = id;
		this.material = material;
		this.quantity = quantity;
	}
	
	public WarehouseShelf getWarehouseShelf() {
		return warehouseShelf;
	}
	
	public int getAlreadyReservedQuantity() {
		return alreadyReservedQuantity;
	}

	public void setAlreadyReservedQuantity(int alreadyReservedQuantity) {
		this.alreadyReservedQuantity = alreadyReservedQuantity;
	}

	public long getId() {
		return id;
	}
	
	/**
	 * @return id with line and shelf identifier
	 */
	public String getPublicId() {
		return warehouseShelf.getPublicId() + "/" + id;
	}

	public Material getMaterial() {
		return material;
	}
	
	public int getQuantity() {
		return quantity;
	}
		
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
