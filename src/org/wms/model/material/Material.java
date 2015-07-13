package org.wms.model.material;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.wms.model.order.OrderRow;

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
	@Column(name="material_id")
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
