package org.wms.model.order;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Order")
public class OrderRow {
	
	private Order order;
	
	private Material material;
	
	private int quantity;
	
}
