/**
 * 
 */
package org.wms.graph.model;

import java.util.Collection;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * @author Stefano Pessina, Daniele Ciriello
 *
 * @param <E>
 * @param <V>
 */
public class WarehouseGraph extends DelegatingGraph<V, E> implements Graph<V, E> {

	public WarehouseGraph(Graph<WarehouseVertex, WarehouseEdge> graph) {
		super(graph);
		// TODO Auto-generated constructor stub
	}
	
	private static UndirectedGraph<Integer, Integer> generateGraph(int numberOfVertices) {
		CompleteGraphGenerator<Integer, Integer> generator = new CompleteGraphGenerator<Integer, Integer>(
				new Factory<UndirectedGraph<Integer, Integer>>() {

					@Override
					public UndirectedGraph<Integer, Integer> create() {
						return new UndirectedSparseGraph<Integer, Integer>();
					}
				}, new IntegerFactory(), new IntegerFactory(), numberOfVertices);
		return (UndirectedGraph<Integer, Integer>) generator.create();
	}


}
