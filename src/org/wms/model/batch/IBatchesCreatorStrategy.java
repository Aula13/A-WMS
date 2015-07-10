package org.wms.model.batch;

import java.util.List;

import org.wms.model.order.Order;
import org.wms.model.warehouse.Warehouse;

/**
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public interface IBatchesCreatorStrategy {
	
	public List<Batch> computeListOfBatch(List<Order> orders, Warehouse warehouse);
	
}
