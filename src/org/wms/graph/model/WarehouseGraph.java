/**
 * 
 */
package org.wms.graph.model;

import java.util.HashMap;
import java.util.Map;

import org.wms.model.warehouse.Warehouse;
import org.wms.model.warehouse.WarehouseCell;

import edu.uci.ics.jung.graph.UndirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
/**
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
@SuppressWarnings("serial")
public class WarehouseGraph extends UndirectedOrderedSparseMultigraph<WarehouseNode, WarehouseLink> {

	private WarehouseNode checkPointNode = null;
	private Map<WarehouseCell, WarehouseNode> cellsNodesCorrs = new HashMap<>();
	
	public boolean addEdge(WarehouseLink link, WarehouseNode node1, WarehouseNode node2){
		return super.addEdge(link, node1, node2, EdgeType.UNDIRECTED);
	}
	
	public boolean addCellNodeCorr(WarehouseCell cell, WarehouseNode node){
		if (this.containsVertex(node))
			return false;
		cellsNodesCorrs.put(cell, node);
		return true;
	}
	
	public WarehouseNode getNode(WarehouseCell cell){
		return cellsNodesCorrs.get(cell);
	}

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
