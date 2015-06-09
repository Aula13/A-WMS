package org.wms.view.graph;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.wms.model.graph.WarehouseGraph;
import org.wms.model.graph.WarehouseLink;
import org.wms.model.graph.WarehouseNode;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

public class GraphView extends JPanel  {

	private WarehouseGraph graph;
	private GraphVisualizationServer visualizer;

	public GraphView(WarehouseGraph graph) {
		this.graph = graph;
		Layout<WarehouseNode, WarehouseLink> layout = new CircleLayout<WarehouseNode, WarehouseLink>(graph);
		layout.setSize(new Dimension(300,300));
		visualizer =  new GraphVisualizationServer(layout);
		add(visualizer);
	}
}
