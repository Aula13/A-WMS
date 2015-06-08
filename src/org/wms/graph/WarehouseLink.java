package org.wms.graph;


public class WarehouseLink {

	private double capacity;
	private double weight;
	private String label;

	public WarehouseLink(String id, double weight, double capacity) {
		this.label = id; // This is defined in the outer class.
		this.setWeight(weight);
		this.setCapacity(capacity);
	}
	
	public String getLabel() {
		return label;
	}

	public String toString() { // Always good for debugging
		return "L"+label;
	}

	public double getWeight() {
		return weight;
	}

	private void setWeight(double weight) {
		this.weight = weight;
	}

	public double getCapacity() {
		return capacity;
	}

	private void setCapacity(double capacity) {
		this.capacity = capacity;
	}

}
