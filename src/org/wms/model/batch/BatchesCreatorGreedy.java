package org.wms.model.batch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jdesktop.swingx.util.OS;
import org.wms.model.common.ListType;
import org.wms.model.material.Material;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;
import org.wms.model.warehouse.Warehouse;
import org.wms.model.warehouse.WarehouseCell;

public class BatchesCreatorGreedy implements IBatchesCreatorStrategy {

	@Override
	public List<Batch> computeListOfBatch(
			List<Batch> alreadyAllocatedBatches,
			List<Order> orders,
			Warehouse warehouse,
			List<Material> materials) {

		
		Map<Long, List<WarehouseCell>> warehouseCells = prepareWarehouseCell(alreadyAllocatedBatches, warehouse);

		List<Batch> solution = new ArrayList<Batch>();

		solution.addAll(greedyBatch(orders, ListType.INPUT, warehouseCells));
		solution.addAll(greedyBatch(orders, ListType.OUTPUT, warehouseCells));
		
		return solution;
	}
	
	protected List<Batch> greedyBatch(List<Order> orders, ListType orderType, Map<Long,List<WarehouseCell>> warehouseCells) {
		
		List<Batch> solution = new ArrayList<Batch>();
		
		List<OrderRow> ordersrows = prepareOrders(orders, orderType);
		
		for (OrderRow orderrow : ordersrows) {

			Optional<WarehouseCell> optWarehouseCell = isValidSelection(orderrow, warehouseCells.get(orderrow.getMaterial().getCode()));

			//Check if the warehouse have this material
			if(optWarehouseCell.isPresent()) {

				//Update already allocated material in warehouse
				//Retrive cell for pick the material
				WarehouseCell warehouseCell = optWarehouseCell.get();
				warehouseCell.setAlreadyReservedQuantity(
						warehouseCell.getAlreadyReservedQuantity()+orderrow.getQuantity());

				//If no batch => create it
				if(solution.isEmpty()) {
					Batch firstBatch = new Batch();
					firstBatch.type = orderType;
					solution.add(firstBatch);
				}
				
				//take the last batch
				Batch batch = solution.get(solution.size()-1);
				
				//Create order row
				BatchRow batchRow = new BatchRow();
				batchRow.jobWarehouseCell = warehouseCell;
				batchRow.quantity = orderrow.getQuantity();
				batchRow.referredOrderRow = orderrow;
				
//				orderrow.setReferredBatchRow(batchRow);
				
				//If there is place on the forklift
				if(!batch.checkCanAddRow(orderrow.getQuantity())) {
					batch = new Batch();
					batch.type = orderType;
					solution.add(batch);
				}
				
				batchRow.referredBatch = batch;
				batch.addRow(batchRow);
			}
		}
		
		return solution;
	}

	/**
	 * Update cell already allocated material
	 * with already allocated batches
	 * 
	 * @return map of material -> warehousecell
	 * 
	 */
	protected Map<Long, List<WarehouseCell>> prepareWarehouseCell(List<Batch> batches, Warehouse warehouse) {
		Map<Long,Integer> alreadyReservedQuantities = new HashMap<>();
		
		Map<Long, List<WarehouseCell>> materialsWarehouseMap = new HashMap<>();

		//Create already reversed map quantity for already allocated batches
		batches.stream()
		.forEach(batch -> batch.getRows().stream()
				.forEach(batchrow -> 
				{
					long cellid = batchrow.getJobWarehouseCell().getId();
					int oldQuantity = 0;
					if(alreadyReservedQuantities.containsKey(cellid))
						oldQuantity = alreadyReservedQuantities.remove(cellid);
					alreadyReservedQuantities.put(cellid, oldQuantity+batchrow.getQuantity());
				}
						)
				);

		//Update already reserved quantity and create material -> cell map
		warehouse.getUnmodifiableLines().stream()
		.forEach(line -> line.getUnmodifiableListShelfs().stream()
				.forEach(shelf -> shelf.getUnmodificableListCells().stream()
						.forEach(cell ->
						{
							//Update cell already reserved quantity
							int alreadyReservedQuantity = 0;
							if(alreadyReservedQuantities.containsKey(cell.getId()))
								alreadyReservedQuantity = alreadyReservedQuantities.get(cell.getId());
							cell.setAlreadyReservedQuantity(alreadyReservedQuantity);
							
							//Add cell to map material-> warehousecells
							List<WarehouseCell> warehouseCells;
							if(materialsWarehouseMap.containsKey(cell.getMaterial())) {
								warehouseCells = materialsWarehouseMap.get(cell.getMaterial());
								warehouseCells.add(cell);
							} else {
								warehouseCells = new ArrayList<>();
								warehouseCells.add(cell);
								materialsWarehouseMap.put(cell.getMaterial().getCode(), warehouseCells);
							}
						}
								)
						)
				);
		
		return materialsWarehouseMap;
	}

	/**
	 * Make order ordered for greedy selection
	 * Filter from completed order
	 * Filtered by already allocated, completed
	 * Ordered by priority, emission date, material type, (DEC) quantity 
	 * 
	 */
	protected List<OrderRow> prepareOrders(List<Order> orders, ListType orderType) {
		
		List<OrderRow> orderedOrderRows = new ArrayList<>();
		
		orders.stream()
			.filter(order -> order.getType()==orderType)
			.forEach(order -> 
			
				orderedOrderRows.addAll(order.getUnmodificableMaterials().stream()
						.filter(row -> !row.isAllocated())
						.collect(Collectors.toList()))
			);
		
		Collections.sort(orderedOrderRows, new Comparator<OrderRow>() {
			
			/* (non-Javadoc)
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			public int compare(OrderRow o1, OrderRow o2) {
				//First check order priority
				int priorityCompare = o1.getOrder().getPriority().compareTo(o2.getOrder().getPriority());
				if(priorityCompare!=0)
					return priorityCompare;
				
				//Second check material type
				if(o1.getMaterial().getCode()>o2.getMaterial().getCode())
					return 1;
				if(o1.getMaterial().getCode()<o2.getMaterial().getCode())
					return -1;
				
				//Third check emission date
 				int dateCompare = o1.getOrder().getEmissionDate().compareTo(o2.getOrder().getEmissionDate());
 				if(dateCompare!=0)
 					return dateCompare;
				
				//4th check order dimension (less before)
				if(o1.getQuantity()>o2.getQuantity())
					return 1;
				if(o1.getQuantity()<o2.getQuantity())
					return -1;
				
				return 0;
			};
			
		});
		
		
		return orderedOrderRows;
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
	 * If there is material in warehouse => return the cell for pick;
	 * if there is place on the fork lift => return empty optional;
	 * 
	 * @return optionally the warehouse cell for pick
	 *  
	 */
	protected Optional<WarehouseCell> isValidSelection(OrderRow row, List<WarehouseCell> warehouseCells) {
		List<WarehouseCell> availableCell = warehouseCells.stream()
			.filter(cell -> cell.getAlreadyReservedQuantity()+row.getQuantity()<=cell.getQuantity())
			.collect(Collectors.toList());
		if(availableCell.isEmpty())
			return Optional.empty();
		return Optional.of(availableCell.get(0));
	}

}
