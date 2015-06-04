package org.wms.model.warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.wms.model.order.Order;

/**
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
@Entity
@Table(name="wms_warehouse_shelf")
public class WarehouseShelf {

	@Id
	@Column(name="warehouse_shelf_id")
	protected long id;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="warehouseShelf", cascade=CascadeType.REMOVE)
	protected List<WarehouseCell> cells = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="warehouse_line_id")
	protected WarehouseLine warehouseLine;
	
	public WarehouseShelf() {
	
	}
	
	public WarehouseShelf(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	public WarehouseLine getWarehouseLine() {
		return warehouseLine;
	}
	
	public List<WarehouseCell> getUnmodiableListCells() {
		return Collections.unmodifiableList(cells);
	}
	
}
