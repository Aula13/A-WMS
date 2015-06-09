package org.wms.picker;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wms.model.batch.Batch;
import org.wms.model.batch.BatchRow;
import org.wms.model.graph.WarehouseGraph;
import org.wms.model.graph.WarehouseLink;
import org.wms.model.graph.WarehouseNode;
import org.wms.model.warehouse.Warehouse;
import org.wms.model.warehouse.WarehouseCell;


public class Picker {
	private Warehouse warehouse;
	private WarehouseGraph graph;
	private WarehouseNode currentPosition;
	
	
	public Picker(Warehouse warehouse, WarehouseGraph graph){
		super();
		this.warehouse = warehouse;
		this.graph = graph;
		currentPosition = graph.getCheckPointNode();
	}
	
	public boolean executeRoute(Batch batch, List<WarehouseLink> route){
		System.out.println("batch received, going through the links: " + route);
		
		Map<WarehouseCell, BatchRow> jobs = new HashMap<>();
		for (BatchRow row : batch.getRows())
			jobs.put(row.getJobWarehouseCell(), row);
		
		for (WarehouseLink link : route) {
			
			WarehouseNode currentNode = graph.getSource(link);
			WarehouseCell currentCell = currentNode.getCell();
			int neededQuantity = jobs.get(currentCell).getQuantity();
			
			if (jobs.containsKey(currentCell) ) {
				if (!pick(neededQuantity, currentNode))
					return false;
				currentCell.setQuantity(currentCell.getQuantity() - neededQuantity);
				jobs.remove(currentCell);
				//TODO set batchrow as allocated
			}
			if (!moveThrough(link))
				return false;
		}
		return true;
	}

	private boolean moveThrough(WarehouseLink link){
		if (currentPosition != graph.getSource(link)) {
			System.out.println("currentPosition != graph.getSource(link)");
			return false;
		}
		System.out.println("Moving through the link: " + link);
		return true;
	}
	
	private boolean pick(int quantity, WarehouseNode node){
		if (currentPosition != node) {
			System.out.println("currentPosition != node");
			return false;
		}
		WarehouseCell cell = node.getCell();
		if (cell == null) {
			System.out.println("the node doesn't corresponds to a cell");
			return false;
		}
		if (cell.getQuantity() < quantity ) {
			System.out.println("node.getCell().getQuantity() < quantity");
			return false;
		}
		System.out.println("Picking: " + quantity + "from: " + cell);
		return true;
	}
	
}
