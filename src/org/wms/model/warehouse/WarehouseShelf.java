package org.wms.model.warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Warehouse shelf model store the main information about the shelf
 * and the reference to the warehouse cell contained in the shelf
 * 
 * Include the business logic
 * Include hibernate annotations for persistence
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
@Entity
@Table(name="wms_warehouse_shelf")
public class WarehouseShelf {

	@ManyToOne(optional=false)
	protected WarehouseLine warehouseLine;
	
	@Id
	@Column(name="warehouse_shelf_id")
	protected long id;
	
	@Column(name="warehouse_shelf_code")
	protected int code;
	
	@OneToMany(mappedBy="warehouseShelf", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	protected List<WarehouseCell> cells = new ArrayList<>();
	
	public WarehouseShelf() {
	
	}
	
	public WarehouseShelf(long id, WarehouseLine line) {
		super();
		this.id = id;
		this.warehouseLine = line;
	}

	/**
	 * Get the shelf id
	 * 
	 * @return shelf
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Get the shelf public id
	 * more usefull for user 
	 * 
	 * @return public id (contain line public id)
	 */
	public String getPublicId() {
		return warehouseLine.getPublicId() + "/" + id;
	}
	
	/**
	 * Get the line where the shelf is stored
	 * 
	 * @return line reference
	 */
	public WarehouseLine getWarehouseLine() {
		return warehouseLine;
	}
	
	/**
	 * Get cells contained in the shelf
	 * 
	 * @return cell list
	 */
	public List<WarehouseCell> getUnmodificableListCells() {
		return Collections.unmodifiableList(cells);
	}
	
}
