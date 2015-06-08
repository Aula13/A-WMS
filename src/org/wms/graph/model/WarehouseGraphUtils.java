package org.wms.graph.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.wms.model.warehouse.Warehouse;
import org.wms.model.warehouse.WarehouseCell;
import org.wms.model.warehouse.WarehouseLine;
import org.wms.model.warehouse.WarehouseShelf;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;

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
//		System.out.println("The shortest unweighted path from " + start + " to " + end + " is:");
//		System.out.println(path.toString());
		return path;
	}
	
	public static List<WarehouseLink> generatePickerRoute(WarehouseGraph graph, List<WarehouseCell> targetList){
		List<WarehouseCell> targets = new ArrayList<>(targetList);
		
		List<WarehouseLink> pickerRoute = new ArrayList<>();
		DijkstraShortestPath<WarehouseNode, WarehouseLink> alg = new DijkstraShortestPath<WarehouseNode, WarehouseLink>(graph);
		List<List<WarehouseLink>> paths = new ArrayList<>();
		
		WarehouseNode checkPointNode = graph.getCheckPointNode();
		WarehouseNode source = checkPointNode;
		WarehouseNode target = null;
		WarehouseNode nearestNode = null;
		WarehouseCell nearestCell = null;
		
		while(targets.size() > 0){
			int minDistance = Integer.MAX_VALUE;
			List<WarehouseLink> shortestPath = null;
			for (WarehouseCell cell : targets) {
				target = graph.getNode(cell);
				int distance = alg.getDistance(source, target).intValue();
				if (distance < minDistance) {
					minDistance = distance;
					shortestPath = alg.getPath(source, target);
					nearestCell = cell;
					nearestNode = target; 
				}
			}
			paths.add(shortestPath);
			source = nearestNode;
			targets.remove(nearestCell);
		}
		paths.add(alg.getPath(source, checkPointNode));
		return pickerRoute;
		
	}
	
	public static List<WarehouseLink> shortestPath(WarehouseGraph graph, WarehouseNode end) {
		return shortestPath(graph, graph.getCheckPointNode(), end);
	}
	
	public static List<WarehouseLink> shortestPath(WarehouseGraph graph, WarehouseCell start, WarehouseCell end) {
		return shortestPath(graph, graph.getNode(start), graph.getNode(end));
	}
	
	public static List<WarehouseLink> shortestPath(WarehouseGraph graph, WarehouseCell end) {
		return shortestPath(graph, graph.getCheckPointNode(), graph.getNode(end));
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

		WarehouseNode node1 = graph.getCheckPointNode();
		WarehouseNode node2 = null;
		WarehouseLine line1 = null;
		WarehouseLine line2 = null;

		List<WarehouseNode> lastNodes =  new ArrayList<>();

		for (WarehouseLine line : warehouse.getUnmodifiableLines()) {
			
			String lineId = Character.toChars(65 + lineNumber).toString(); // returns 'A', 'B', etc.
			
			node2 = new WarehouseNode(lineId); 	
			// link lines together
			addEdgeToGraph(graph, nodeDistanceBetweenLines(lineNumber), node1, node2);
			graph.addLineNodeCorr(line, node1);
			node1 = node2;
			
			lastNodes.add(generateLineGraph(graph, line, node1));
			
			if (lineNumber % 2 == 0){
				line1 = line;
				line2 = null;
			}
			else {
				line2 = line;
			}
			
			if (line1 != null && line2 != null) {
				addCrossLinesEdges(graph, line1, line2);
				line1 = null;
			}
			
			lineNumber ++;	
		}

		int nodeNumber = 0;
		for (WarehouseNode node : lastNodes) {
			if (nodeNumber == 0) {
				node1 = node;
			}
			else{
				node2 = node;
				addEdgeToGraph(graph, nodeDistanceBetweenLines(nodeNumber), node1, node2);
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
				addEdgeToGraph(graph, cellWidth,node1, node2);
				graph.addShelfNodeCorr(shelf, node1);
				node1 = node2;
			}
			generateShelfGraph(graph, shelf, node1);
			shelfNumber ++;	
		}
		return node1;
	}
	
	private static void addCrossLinesEdges(WarehouseGraph graph, WarehouseLine line1, WarehouseLine line2){
		Iterator<WarehouseShelf> shelvesIterator1 = line1.getUnmodifiableListShelfs().iterator();
		Iterator<WarehouseShelf> shelvesIterator2 = line2.getUnmodifiableListShelfs().iterator();

		while(shelvesIterator1.hasNext() && shelvesIterator2.hasNext()) {
		    addEdgeToGraph(graph, laneWidth, graph.getNode(shelvesIterator1.next()), graph.getNode(shelvesIterator2.next()));
		}
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
				addEdgeToGraph(graph, cellHeight, node1, node2);
				graph.addCellNodeCorr(cell, node1);
				node1 = node2;
			}
			cellNumber ++;
		}
	}
	
	private static void addEdgeToGraph(WarehouseGraph graph, double weight, WarehouseNode node1, WarehouseNode node2){
		graph.addEdge(new WarehouseLink(linkLabel(node1, node2), weight, linkCapacity), node1, node2);
	}
	
	private static double nodeDistanceBetweenLines(int lineNumber){
		if (lineNumber % 2 == 0) {
			return 2* shelfWidth;
		}
		return laneWidth;
	}
	
}
