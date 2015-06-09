/**
 * 
 */
package org.wms.model.graph;

import java.util.HashMap;
import java.util.Map;

import org.wms.model.warehouse.Warehouse;
import org.wms.model.warehouse.WarehouseCell;
import org.wms.model.warehouse.WarehouseLine;
import org.wms.model.warehouse.WarehouseShelf;

import edu.uci.ics.jung.graph.UndirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
/**
 * Graph implementation for the warehouse 
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
@SuppressWarnings("serial")
public class WarehouseGraph extends UndirectedOrderedSparseMultigraph<WarehouseNode, WarehouseLink> {

	/**
	 * The start/end node
	 */
	private WarehouseNode checkPointNode = null;
	/**
	 * Cells-Node correspondeces map
	 */
	private Map<WarehouseCell, WarehouseNode> cellsNodesCorrs = new HashMap<>();
	/**
	 * Shelves-Node correspondeces map
	 */
	private Map<WarehouseShelf, WarehouseNode> shelvesNodesCorrs = new HashMap<>();
	/**
	 * Lines-Node correspondeces map
	 */
	private Map<WarehouseLine, WarehouseNode> linesNodesCorrs = new HashMap<>();
	
	/**
	 * Empty Constructor
	 */
	public WarehouseGraph() {
		super();
	}
	
	/**
	 * Constructor with custom checkpoint
	 * 
	 * @param checkPoint is the start/end node
	 */
	public WarehouseGraph(WarehouseNode checkPoint) {
		this();
		this.checkPointNode = checkPoint;
	}

	/**
	 * Constructor from a Warehouse
	 * 
	 * @param warehouse is the Warehouse to model in the graph
	 */
	public WarehouseGraph(Warehouse warehouse) {
		this(new WarehouseNode("checkpoint"));
		WarehouseGraphUtils.populateGraph(warehouse, this);
	}
	
	
	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.graph.AbstractGraph#addEdge(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public boolean addEdge(WarehouseLink link, WarehouseNode node1, WarehouseNode node2){
		return super.addEdge(link, node1, node2, EdgeType.UNDIRECTED);
	}
	
	/**
	 * Add a correspondence between a cell and a node
	 * 
	 * @param cell will be a key of cellsNodesCorrs
	 * @param node will be the value associated with cell
	 * @return true if the node is part of the graph
	 */
	public boolean addCellNodeCorr(WarehouseCell cell, WarehouseNode node){
		if (this.containsVertex(node))
			return false;
		cellsNodesCorrs.put(cell, node);
		return true;
	}	
	
	/**
	 * Add a correspondence between a shelf and a node
	 * 
	 * @param shelf will be a key of shelvesNodesCorrs
	 * @param node will be the value associated with shelf
	 * @return true if the node is part of the graph
	 */
	public boolean addShelfNodeCorr(WarehouseShelf shelf, WarehouseNode node){
		if (this.containsVertex(node))
			return false;
		shelvesNodesCorrs.put(shelf, node);
		return true;
	}	
	
	/**
	 * Add a correspondence between a line and a node
	 * 
	 * @param line will be a key of linesNodesCorrs
	 * @param node will be the value associated with line
	 * @return true if the node is part of the graph
	 */
	public boolean addLineNodeCorr(WarehouseLine line, WarehouseNode node){
		if (this.containsVertex(node))
			return false;
		linesNodesCorrs.put(line, node);
		return true;
	}
	
	/**
	 * Returns the node associated to the cell
	 * 
	 * @param cell is the cell associated with the node to be returned
	 * @return the node associated to cell
	 */
	public WarehouseNode getNode(WarehouseCell cell){
		return cellsNodesCorrs.get(cell);
	}	
	
	/**
	 * Returns the node associated to the shelf
	 * 
	 * @param shelf is the shelf associated with the node to be returned
	 * @return the node associated to shelf
	 */
	public WarehouseNode getNode(WarehouseShelf shelf){
		return shelvesNodesCorrs.get(shelf);
	}	
	
	/**
	 * Returns the node associated to the line
	 * 
	 * @param line is the line associated with the node to be returned
	 * @return the node associated to line
	 */
	public WarehouseNode getNode(WarehouseLine line){
		return linesNodesCorrs.get(line);
	}

	/**
	 * @return the checkPoint node
	 */
	public WarehouseNode getCheckPointNode() {
		return checkPointNode;
	}
}
