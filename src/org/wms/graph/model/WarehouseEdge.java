package org.wms.graph.model;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEvent.Edge;
import edu.uci.ics.jung.graph.event.GraphEvent.Vertex;

public class WarehouseEdge<V, E> extends Edge<V, E> {

	public WarehouseEdge(Graph<V,E> source, Type type, E edge) {
		super(source,type, edge);
	}


}
