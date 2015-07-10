package org.wms.model.batch;

import java.util.ArrayList;
import java.util.List;

import org.wms.model.material.Material;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;
import org.wms.model.warehouse.Warehouse;

public class BatchesCreatorGreedy implements IBatchesCreatorStrategy {

	@Override
	public List<Batch> computeListOfBatch(
			List<Batch> alreadyAllocatedBatches,
			List<Order> orders,
			Warehouse warehouse,
			List<Material> materials) {
		
		updateAlreadyAllocatedMaterial();
		List<OrderRow> ordersrows = prepareOrders();
		
		List<Batch> solution = new ArrayList<Batch>();
		
		for (OrderRow orderrow : ordersrows) {
			if(isValidSelection(orderrow)) { //Check if the warehouse have this material
				
				//Update already allocated material in warehouse
				//Retrive cell for pick the material
				
				//If no batch => create it
				if(solution.isEmpty())
					solution.add(new Batch());
				
				//take the last batch
				Batch batch = solution.get(solution.size()-1);
				
				//If there is place on the forklift
				if(batch.checkCanAddRow(orderrow.getQuantity())) {
					//If there is already a batch for the warehouse cell
					//update only the batch row quantity
					
					//Otherwise add a new row in the batch
				} else {
					//Add a new batch
					//add a new row in the batch
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Update cell already allocated material
	 * with already allocated batches
	 * 
	 */
	protected void updateAlreadyAllocatedMaterial() {
		
	}
	
	/**
	 * Make order ordered for greedy selection
	 * Filter from completed order
	 * Filtered by already allocated, completed
	 * Ordered by priority, emission date, material type, (DEC) quantity 
	 * 
	 */
	protected List<OrderRow> prepareOrders() {
		return null;
	}
	
	
	/**
	 * Take prepared order
	 * take the first (greedy choice)
	 * 
	 * @return
	 */
	protected OrderRow selectNext() {
		
		return null;
	}
	
	/**
	 * If there is material in warehouse => return true;
	 * if there is place on the fork lift => return true;
	 *  
	 */
	protected boolean isValidSelection(OrderRow row) {
		return false;
	}
	
}
