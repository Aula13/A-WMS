package org.wms.model.order;

import java.util.HashSet;
import java.util.Set;

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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="material")
	private Set<OrderRow> orders = new HashSet<>();
	
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
