package org.wms.graph;

import org.wms.model.warehouse.WarehouseCell;


public class WarehouseNode {

	private String label;
	
	public WarehouseNode(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

	public String toString() {
		return "N"+label;
	}
}
