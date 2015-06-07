/**
 * 
 */
package org.wms.graph.model;

import org.wms.model.warehouse.Warehouse;

import edu.uci.ics.jung.graph.UndirectedOrderedSparseMultigraph;
/**
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WarehouseGraph extends UndirectedOrderedSparseMultigraph<WarehouseNode, WarehouseLink> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WarehouseNode checkPointNode = null;

	public WarehouseGraph() {
		super();
	}
	
	public WarehouseGraph(WarehouseNode checkPoint) {
		super();
		this.checkPointNode = checkPoint;
	}

	public WarehouseGraph(Warehouse warehouse) {
		super();
		generateGraph(warehouse);
	}

	private void generateGraph(Warehouse warehouse) {
		WarehouseGraphUtils.populateGraph(warehouse, this);
	}

	public WarehouseNode getCheckPointnode() {
		return checkPointNode;
	}

	public void setCheckPointnode(WarehouseNode checkPointnode) {
		this.checkPointNode = checkPointnode;
	}
	
}
