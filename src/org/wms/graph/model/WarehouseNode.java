package org.wms.graph.model;

import org.wms.model.warehouse.WarehouseCell;


public class WarehouseNode {

	private String label;
	private WarehouseCell cell;
	
	public WarehouseNode(String id) {
		this.label = id;
	}
	
	public WarehouseNode(String label, WarehouseCell cell) {
		this.label = label;
		this.setCell(cell);
	}
	
	public String getLabel() {
		return label;
	}

	public String toString() {
		return "N"+label;
	}

	public WarehouseCell getCell() {
		return cell;
	}

	public void setCell(WarehouseCell cell) {
		this.cell = cell;
	} 
}
