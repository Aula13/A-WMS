package org.wms.graph.model;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEvent.Vertex;

public class WarehouseVertex <V, E> extends Vertex<V, E>  {

	public WarehouseVertex(Graph<V,E> source, Type type, V vertex) {
		super(source,type, vertex);
	}

}
