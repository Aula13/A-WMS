package org.wms.graph;

import org.wms.model.warehouse.WarehouseCell;


/**
 * The Warehouse node representing a vertex in the warehouse graph
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WarehouseNode {

	/**
	 * the node's label
	 */
	private String label;
	
	/**
	 * Creates a node with a label
	 * 
	 * @param label the label of the new node
	 */
	public WarehouseNode(String label) {
		this.label = label;
	}
	
	/**
	 * @return the node's label
	 */
	public String getLabel() {
		return label;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "N"+label;
	}
}
