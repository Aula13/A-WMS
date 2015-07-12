package org.wms.model.warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
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
	
	@OneToMany(mappedBy="warehouseShelf", cascade=CascadeType.REMOVE)
	@Fetch(FetchMode.JOIN)
	protected Set<WarehouseCell> cells = new HashSet<>();
	
	public WarehouseShelf() {
	
	}
	
	public WarehouseShelf(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	public String getPublicId() {
		return warehouseLine.getCode() + "/" + id;
	}
	
	public WarehouseLine getWarehouseLine() {
		return warehouseLine;
	}
	
	public List<WarehouseCell> getUnmodificableListCells() {
		return Collections.unmodifiableList(new ArrayList<WarehouseCell>(cells));
	}
	
}
