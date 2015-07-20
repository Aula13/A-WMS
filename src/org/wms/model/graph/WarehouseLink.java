package org.wms.model.graph;


/**
 * A Warehouse link representing a Warehouse graph's edge
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WarehouseLink {

	/**
	 * the capacity of the link
	 */
	private double capacity;
	/**
	 * the weight of the link (distance)
	 */
	private double weight;
	/**
	 * the label of the link
	 */
	private String label;

	/**
	 * Creates a link
	 * 
	 * @param label the link's label
	 * @param weight the link's weight
	 * @param capacity the link's capacity
	 */
	public WarehouseLink(String label, double weight, double capacity) {
		this.label = label; // This is defined in the outer class.
		this.setWeight(weight);
		this.setCapacity(capacity);
	}
	
	/**
	 * @return the link label
	 */
	public String getLabel() {
		return label;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() { // Always good for debugging
		return "L"+label;
	}

	/**
	 * @return the link weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * set the link's weight
	 * 
	 * @param weight the new link's weight
	 */
	private void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the link's capacity
	 */
	public double getCapacity() {
		return capacity;
	}

	/**
	 * set the link's capacity
	 * 
	 * @param capacity the new link's capacity
	 */
	private void setCapacity(double capacity) {
		this.capacity = capacity;
	}

}
