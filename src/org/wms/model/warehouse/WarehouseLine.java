package org.wms.model.warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="wms_warehouse_line")
public class WarehouseLine {

	@Id
	@Column(name="warehouse_line_id")
	protected long id;
	
	@Column(name="warehouse_line_code", nullable=false, unique=true)
	protected String code;
	
	@OneToMany(mappedBy="warehouseLine", cascade=CascadeType.REMOVE)
	@LazyCollection(LazyCollectionOption.FALSE)
	protected List<WarehouseShelf> shelfs = new ArrayList<>();
	
	public WarehouseLine() {
	
	}

	public WarehouseLine(long id, String code) {
		super();
		this.id = id;
		this.code = code;
	}
	
	public long getId() {
		return id;
	}
	
	public String getCode() {
		return code;
	}
	
	public List<WarehouseShelf> getUnmodifiableListShelfs() {
		return Collections.unmodifiableList(shelfs);
	}
	
}
