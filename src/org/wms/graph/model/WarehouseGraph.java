/**
 * 
 */
package org.wms.graph.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.wms.model.warehouse.Warehouse;
import org.wms.model.warehouse.WarehouseCell;
import org.wms.model.warehouse.WarehouseLine;
import org.wms.model.warehouse.WarehouseShelf;

import edu.uci.ics.jung.graph.UndirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
/**
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
@SuppressWarnings("serial")
public class WarehouseGraph extends UndirectedOrderedSparseMultigraph<WarehouseNode, WarehouseLink> {

	private WarehouseNode checkPointNode = null;
	private Map<WarehouseCell, WarehouseNode> cellsNodesCorrs = new LinkedHashMap<>();
	private Map<WarehouseShelf, WarehouseNode> ShelvesNodesCorrs = new LinkedHashMap<>();
	private Map<WarehouseLine, WarehouseNode> linesNodesCorrs = new LinkedHashMap<>();
	
	
	public boolean addEdge(WarehouseLink link, WarehouseNode node1, WarehouseNode node2){
		return super.addEdge(link, node1, node2, EdgeType.UNDIRECTED);
	}
	
	public boolean addCellNodeCorr(WarehouseCell cell, WarehouseNode node){
		if (this.containsVertex(node))
			return false;
		cellsNodesCorrs.put(cell, node);
		return true;
	}	
	
	public boolean addShelfNodeCorr(WarehouseShelf shelf, WarehouseNode node){
		if (this.containsVertex(node))
			return false;
		ShelvesNodesCorrs.put(shelf, node);
		return true;
	}	
	
	public boolean addLineNodeCorr(WarehouseLine line, WarehouseNode node){
		if (this.containsVertex(node))
			return false;
		linesNodesCorrs.put(line, node);
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
