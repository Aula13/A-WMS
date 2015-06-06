package org.wms.graph.model;


public class WarehouseLink {

	double capacity; // should be private
	double weight; // should be private for good practice
	String id;

	public WarehouseLink(String id, double weight, double capacity) {
		this.id = id; // This is defined in the outer class.
		this.weight = weight;
		this.capacity = capacity;
	}
	
	public String getId() {
		return id;
	}
	

	public String toString() { // Always good for debugging
		return "E"+id;
	}

}
