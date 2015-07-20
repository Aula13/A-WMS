package org.wms.model.batch;

import java.util.List;

import org.wms.model.material.Material;
import org.wms.model.order.Order;
import org.wms.model.warehouse.Warehouse;

/**
 * Strategy pattern interface for batch list creator algorithm
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public interface IBatchesCreatorStrategy {
	
	/**
	 * Compute batch list
	 * 
	 * @param alreadyAllocatedBatches
	 * @param orders
	 * @param warehouse
	 * @param materials
	 * @return batch list
	 */
	public List<Batch> computeListOfBatch(
			List<Batch> alreadyAllocatedBatches,
			List<Order> orders,
			Warehouse warehouse,
			List<Material> materials);
	
}
