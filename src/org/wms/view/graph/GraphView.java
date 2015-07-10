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
	private Layout<WarehouseNode, WarehouseLink> layout ;

	public GraphView(WarehouseGraph graph) {
		this.graph = graph;		
		initComponents();
		initUI();
	}
	
	/**
	 * Init components to be placed in the view
	 */
	private void initComponents() {
		layout = new CircleLayout<WarehouseNode, WarehouseLink>(graph);
		visualizer =  new GraphVisualizationServer(layout);
		add(visualizer);

	}
	

	/**
	 * Set view appearance
	 */
	private void initUI() {
		layout.setSize(new Dimension(300,300));
		setSize(700, 500);
	}
}
