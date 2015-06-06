/**
 * 
 */
package org.wms.graph.model;

import java.util.ArrayList;
import java.util.List;

import org.wms.model.warehouse.Warehouse;
import org.wms.model.warehouse.WarehouseCell;
import org.wms.model.warehouse.WarehouseLine;
import org.wms.model.warehouse.WarehouseShelf;

import edu.uci.ics.jung.graph.UndirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
/**
 * @author Stefano Pessina, Daniele Ciriello
 *
 * @param <E>
 * @param <V>
 */
public class WarehouseGraph extends UndirectedOrderedSparseMultigraph<WarehouseNode, WarehouseLink> {

	//TODO put in config file
	double laneWidth = 4;
	double shelfWidth = 1;
	double cellWidth = 1;
	double cellHeight = 0;
	double linkCapacity = 1;

	public WarehouseGraph() {
		super();
	}

	public WarehouseGraph(Warehouse warehouse) {
		super();
		generateGraph(warehouse);
	}
	
	private void generateShelfGraph(WarehouseShelf shelf, WarehouseNode shelfNode){
		int cellNumber = 0;
		WarehouseNode firstCellNode;
		WarehouseNode node1 = null;
		WarehouseNode node2 = null;
		for (WarehouseCell cell : shelf.getUnmodiableListCells()) {
			if (cellNumber == 0) {
				firstCellNode = shelfNode;
				node1 = firstCellNode;
			}
			else {
				node2 = new WarehouseNode(shelfNode.getId() + '/' + String.format("%02d", cellNumber));
				addEdge(new WarehouseLink(node1.getId() + "->" + node2.getId(), cellHeight, linkCapacity), node1, node2, EdgeType.UNDIRECTED);
				node1.setCell(cell);
				node1 = node2;
			}
			cellNumber ++;
		}
	}

	private WarehouseNode generateLineGraph(WarehouseLine line, WarehouseNode lineNode){

		int shelfNumber = 0;
		WarehouseNode firstShelfNode;
		WarehouseNode node1 = null;
		WarehouseNode node2 = null;
		for (WarehouseShelf shelf : line.getUnmodifiableListShelfs()) {
			if (shelfNumber == 0) {
				firstShelfNode = lineNode;
				node1 = firstShelfNode;
			}
			else {
				node2 = new WarehouseNode(lineNode.getId() + '/' + String.format("%02d", shelfNumber));
				addEdge(new WarehouseLink(node1.getId() + "->" + node2.getId(), shelfWidth, linkCapacity), node1, node2, EdgeType.UNDIRECTED);
				node1 = node2;
			}
			generateShelfGraph(shelf, node1);

			shelfNumber ++;	
		}
		return node1;
	}

	private void generateGraph(Warehouse warehouse) {
		
		
		int lineNumber = 0;
		WarehouseNode firstLineNode;
		WarehouseNode node1 = null;
		WarehouseNode node2 = null;
		List<WarehouseNode> lastNodes =  new ArrayList<>();
		
		for (WarehouseLine line : warehouse.getUnmodifiableLines()) {
			String lineId = Character.toChars(65 + lineNumber).toString(); // returns 'A', 'B', etc.

			if (lineNumber == 0) {
				firstLineNode =  new WarehouseNode(lineId);
				node1 = firstLineNode;
			}
			else {
				node2 = new WarehouseNode(lineId); 				
				addEdge(new WarehouseLink(node1.getId() + "->" + node2.getId(), nodeDistance(lineNumber), linkCapacity), node1, node2, EdgeType.UNDIRECTED);
				node1 = node2;
			}
			lastNodes.add(generateLineGraph(line, node1));
			lineNumber ++;	
		}
		
		int nodeNumber = 0;
		for (WarehouseNode node : lastNodes) {
			if (nodeNumber == 0) {
				node1 = node;
			}
			else{
				node2 = node;
				addEdge(new WarehouseLink(node1.getId() + "->" + node2.getId(), nodeDistance(nodeNumber), linkCapacity), node1, node2, EdgeType.UNDIRECTED);
				node1 = node;
			}
			nodeNumber++;
		}

	}
	
	private double nodeDistance(int lineNumber){
		if (lineNumber % 2 == 0) {
			return 2* shelfWidth;
		}
		return laneWidth;
	}
}
