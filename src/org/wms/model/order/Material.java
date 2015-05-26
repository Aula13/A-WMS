package org.wms.model.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="wms_material")
public class Material {
	
	@Id
	@Column(name="material_id")
	private long code;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="material")
	private List<OrderRow> orders = new ArrayList<>();
	
	public Material() {
	}

	public Material(long code) {
		super();
		this.code = code;
	}
	
	public long getCode() {
		return code;
	}
}
