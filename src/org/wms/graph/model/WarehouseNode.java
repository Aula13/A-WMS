package org.wms.graph.model;

import org.wms.model.warehouse.WarehouseCell;


public class WarehouseNode{

	private String id;
	private WarehouseCell cell;
	
	public WarehouseNode(String id) {
		this.id = id;
	}
	
	public WarehouseNode(String id, WarehouseCell cell) {
		this.id = id;
		this.setCell(cell);
	}
	
	public String getId() {
		return id;
	}

	
	public String toString() {
		return "V"+id;
	}

	public WarehouseCell getCell() {
		return cell;
	}

	public void setCell(WarehouseCell cell) {
		this.cell = cell;
	} 
}
