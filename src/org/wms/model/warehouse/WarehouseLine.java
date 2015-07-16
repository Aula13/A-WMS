package org.wms.model.warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Warehouse line model store main information for a line
 * and reference to the shelfs contained in the line
 * 
 * Include the business logic
 * Include hibernate annotations for persistence
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
@Entity
@Table(name="wms_warehouse_line")
public class WarehouseLine {

	@Id
	@Column(name="warehouse_line_id")
	protected long id;
	
	@Column(name="warehouse_line_code", nullable=false, unique=true)
	protected String code;
	
	@OneToMany(mappedBy="warehouseLine", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	protected Set<WarehouseShelf> shelfs = new HashSet<>();
	
	public WarehouseLine() {
	
	}

	public WarehouseLine(long id, String code) {
		super();
		this.id = id;
		this.code = code;
	}
	
	/**
	 * Get line id
	 * 
	 * @return line id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Get alphabetical line code
	 * more usefull for users
	 * 
	 * @return line code
	 */
	public String getPublicId() {
		return code;
	}
	
	/**
	 * Get warehouse shelfs contained in the line
	 * 
	 * @return list of warehouse shelf
	 */
	public List<WarehouseShelf> getUnmodifiableListShelfs() {
		return Collections.unmodifiableList(new ArrayList<WarehouseShelf>(shelfs));
	}
	
}
