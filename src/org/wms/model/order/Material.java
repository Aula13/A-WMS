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
@Table(name="Material")
public class Material {
	
	@Id
	@Column(name="Code")
	private long code;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<OrderRow> orders = new HashSet<>();
	
	
}
