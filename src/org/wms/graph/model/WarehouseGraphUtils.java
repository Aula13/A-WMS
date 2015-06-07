package org.wms.graph.model;

import java.util.ArrayList;
import java.util.List;

import org.wms.model.warehouse.Warehouse;
import org.wms.model.warehouse.WarehouseCell;
import org.wms.model.warehouse.WarehouseLine;
import org.wms.model.warehouse.WarehouseShelf;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.util.EdgeType;

public class WarehouseGraphUtils {
	
	//TODO put in config file
	static double firstLinkDistance = 3; //distance between checkpoint node and fisrt node

	static double laneWidth 	= 4; // width of the empty space between two lanes
	static double shelfWidth 	= 1;
	static double cellWidth 	= 1;
	static double cellHeight 	= 0;
	static double linkCapacity 	= 1;

	public static List<WarehouseLink> shortestPath(WarehouseGraph graph, WarehouseNode start, WarehouseNode end) {
		DijkstraShortestPath<WarehouseNode, WarehouseLink> alg = new DijkstraShortestPath<WarehouseNode, WarehouseLink>(graph);
		List<WarehouseLink> path = alg.getPath(start, end);
		System.out.println("The shortest unweighted path from " + start + " to " + end + " is:");
		System.out.println(path.toString());
		return path;
	}
	
	public static List<WarehouseLink> shortestPath(WarehouseGraph graph, WarehouseNode end) {
		return shortestPath(graph, graph.getCheckPointnode(), end);
	}
	
	public static List<WarehouseLink> shortestPath(WarehouseGraph graph, WarehouseCell start, WarehouseCell end) {
		return shortestPath(graph, graph.getNode(start), graph.getNode(end));
	}
	
	public static List<WarehouseLink> shortestPath(WarehouseGraph graph, WarehouseCell end) {
		return shortestPath(graph, graph.getCheckPointnode(), graph.getNode(end));
	}
	
	public static String linkLabel(WarehouseNode node1, WarehouseNode node2){
		return node1.getLabel() + "->" + node2.getLabel();
	}
	
	public static void populateGraph(Warehouse warehouse, WarehouseGraph graph){
		generateGraph(warehouse, graph);
	}
	
	private static WarehouseGraph initGraph(Warehouse warehouse){
		WarehouseGraph graph = new WarehouseGraph(new WarehouseNode("checkpoint"));
		return generateGraph(warehouse, graph);
	}
	
	private static WarehouseGraph generateGraph(Warehouse warehouse, WarehouseGraph graph){
		int lineNumber = 0;

		WarehouseNode node1 = graph.getCheckPointnode();
		WarehouseNode node2 = null;

		List<WarehouseNode> lastNodes =  new ArrayList<>();

		for (WarehouseLine line : warehouse.getUnmodifiableLines()) {
			
			String lineId = Character.toChars(65 + lineNumber).toString(); // returns 'A', 'B', etc.
			
			node2 = new WarehouseNode(lineId); 	
			// link lines together
			addEdgeToGraph(graph, node1, node2);
			graph.addLineNodeCorr(line, node1);
			node1 = node2;
			
			lastNodes.add(generateLineGraph(graph, line, node1));
			lineNumber ++;	
		}

		int nodeNumber = 0;
		for (WarehouseNode node : lastNodes) {
			if (nodeNumber == 0) {
				node1 = node;
			}
			else{
				node2 = node;
				addEdgeToGraph(graph, node1, node2);
				node1 = node;
			}
			nodeNumber++;
		}
		return graph;
	}
	
	private static WarehouseNode generateLineGraph(WarehouseGraph graph, WarehouseLine line, WarehouseNode lineNode){

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
				node2 = new WarehouseNode(lineNode.getLabel() + '/' + String.format("%02d", shelfNumber));
				addEdgeToGraph(graph, node1, node2);
				graph.addShelfNodeCorr(shelf, node1);
				node1 = node2;
			}
			generateShelfGraph(graph, shelf, node1);

			shelfNumber ++;	
		}
		return node1;
	}
	
	private static void generateShelfGraph(WarehouseGraph graph, WarehouseShelf shelf, WarehouseNode shelfNode){

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
				node2 = new WarehouseNode(shelfNode.getLabel() + '/' + String.format("%02d", cellNumber));
				addEdgeToGraph(graph, node1, node2);
				graph.addCellNodeCorr(cell, node1);
				node1 = node2;
			}
			cellNumber ++;
		}
	}
	
	private static void addEdgeToGraph(WarehouseGraph graph, WarehouseNode node1, WarehouseNode node2){
		graph.addEdge(new WarehouseLink(linkLabel(node1, node2), cellHeight, linkCapacity), node1, node2);
	}
	
	private static double nodeDistanceBetweenLines(int lineNumber){
		if (lineNumber % 2 == 0) {
			return 2* shelfWidth;
		}
		return laneWidth;
	}
	
}
