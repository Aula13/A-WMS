/**
 * 
 */
package org.wms.graph.model;

import org.apache.commons.collections15.Factory;
import org.wms.model.warehouse.Warehouse;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
/**
 * @author Stefano Pessina, Daniele Ciriello
 *
 * @param <E>
 * @param <V>
 */
public class WarehouseGraph extends DelegatingGraph<WarehouseVertex, WarehouseEdge> implements Graph<WarehouseVertex, WarehouseEdge> {

	public WarehouseGraph(Graph<WarehouseVertex, WarehouseEdge> graph) {
		super(graph);
		// TODO Auto-generated constructor stub
	}
	
	private static Graph<WarehouseVertex, WarehouseEdge> generateGraph(Warehouse warehouse) {
//		CompleteGraphGenerator<Integer, Integer> generator = new CompleteGraphGenerator<Integer, Integer>(
//				new Factory<UndirectedGraph<Integer, Integer>>() {
//
//					@Override
//					public UndirectedGraph<Integer, Integer> create() {
//						return new UndirectedSparseGraph<Integer, Integer>();
//					}
//				}, new IntegerFactory(), new IntegerFactory(), numberOfVertices);
//		return (Graph<WarehouseVertex, WarehouseEdge>) generator.create();
		
	
	}


}
