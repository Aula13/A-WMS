package org.wms.view.graph;

import org.wms.model.graph.WarehouseLink;
import org.wms.model.graph.WarehouseNode;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;


@SuppressWarnings("serial")
public class GraphVisualizationServer extends BasicVisualizationServer<WarehouseNode, WarehouseLink> {

	public GraphVisualizationServer(Layout<WarehouseNode, WarehouseLink> layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}
	
	
}
