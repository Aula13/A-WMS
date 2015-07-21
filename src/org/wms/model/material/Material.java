package org.wms.model.material;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Material model
 * Include the business logic
 * Include hibernate annotations for persistence
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
@Entity
@Table(name="wms_material")
public class Material {

	@Id
	@Column(name="material_id", nullable=false)
	protected long code;
	
	/**
	 * List of the OrderRow that contains this material
	 */
//	@OneToMany(fetch=FetchType.EAGER, mappedBy="material", cascade=CascadeType.REMOVE)
//	protected List<OrderRow> orders = new ArrayList<>();
	
	public Material() {
	}

	public Material(long code) {
		super();
		this.code = code;
	}
	
	/**
	 * @return the code of this material
	 */
	public long getCode() {
		return code;
	}
}
