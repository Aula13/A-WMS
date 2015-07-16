package org.wms.model.batch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.wms.config.Configuration;
import org.wms.config.Utils;
import org.wms.model.common.ICRUDLayer;
import org.wms.model.common.ListType;
import org.wms.model.common.ModelReference;
import org.wms.model.common.Status;
import org.wms.model.material.Materials;
import org.wms.model.order.Order;
import org.wms.model.order.Orders;
import org.wms.model.warehouse.Warehouse;

/**
 * Batchs model
 * This model provide high level CRUD for batches
 * 
 * This model is synchronized though a semaphore(1)
 * 
 * This model provide observable methods
 * for get signal about Work list modifications
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class Batches extends Observable implements Observer {
	
	private Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER);
	
	/**
	 * Synchronization semaphore
	 */
	protected Semaphore semaphore = new Semaphore(1);
	
	private ICRUDLayer<Batch> persistentLayer;
	
	private IBatchesCreatorStrategy batchesCreatorStrategy;
	
	Orders orders;
	
	Warehouse warehouse;
	
	Materials materials;
	
	/**
	 * Constructor
	 * 
	 * @param persistentLayer class the provide method for persistence
	 */
	public Batches(Orders orders,
			Warehouse warehouse,
			Materials materials,
			ICRUDLayer<Batch> persistentLayer, 
			IBatchesCreatorStrategy batchesCreatorStrategy) {
		super();
		this.persistentLayer = persistentLayer;
		this.batchesCreatorStrategy = batchesCreatorStrategy;
		this.orders = orders;
		this.warehouse = warehouse;
		this.materials = materials;
	}

	/**
	 * Add a new batch
	 * 
	 * @param batch batch to add
	 * @return true=batch created succefully
	 */
	public boolean addBatch(Batch batch) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(!persistentLayer.get(batch.getId()).isPresent()) {
				result = persistentLayer.create(batch);
			
				semaphore.release();
				
				setChanged();
				notifyObservers();
			}
			
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
		}
		
		semaphore.release();
		return result;
	}
	
	/**
	 * Delete a batch
	 * 
	 * @param batch batch to delele
	 * @return true=batch deleted succefully
	 */
	public boolean deleteBatch(Batch batch) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(persistentLayer.get(batch.getId()).isPresent()) {
				result = persistentLayer.delete(batch);
			
				semaphore.release();
				
				setChanged();
				notifyObservers();
			}
			
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
		}
		
		semaphore.release();
		return result;
	}
	
	/**
	 * Update a batch
	 * 
	 * @param batch batch to update
	 * @return true=batch updated
	 */
	public boolean updateBatch(Batch batch) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(persistentLayer.get(batch.getId()).isPresent()) {
				result = persistentLayer.update(batch);
				
				semaphore.release();
			
				setChanged();
				notifyObservers();
			}
			
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
		}
		
		semaphore.release();
		return result;
	}
	
	/**
	 * Get a batch
	 * 
	 * @param batchId batchId to fetch
	 * @return optionally the batch
	 */
	public Optional<Batch> get(Long batchId) {
		
		Optional<Batch> result = Optional.empty();
		
		try {
			semaphore.acquire();
			
			result = persistentLayer.get(batchId);
			
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
		}
		
		semaphore.release();
		return result;
	}
	
	/**
	 * Provide an unmodificable list of batches
	 * 
	 * @return list of the batches
	 */
	public List<Batch> getUnmodificableBatchList() {
		
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
			semaphore.release();
			return new ArrayList<>();
		}
		
		Optional<List<Batch>> opt = persistentLayer.selectAll();
		List<Batch> batchs = opt.isPresent()? opt.get() : new ArrayList<>();
		
		semaphore.release();
		
		return Collections.unmodifiableList(batchs);
	}
	
	/**
	 * Provide an unmodificable list of batches
	 * filtered by the batch type
	 * 
	 * @return list of the batches
	 */
	public List<Batch> getUnmodificableBatchList(ListType batchType) {
		
		List<Batch> batchs = getUnmodificableBatchList();
		List<Batch> filteredBatchs = batchs.stream()
				.filter(batch -> batch.getType()==batchType)
				.collect(Collectors.toList());
		
		semaphore.release();
		
		return Collections.unmodifiableList(filteredBatchs);
	}
	
	/**
	 * Set a batch as allocate and persist it
	 * 
	 * @param batch
	 * @return true if the batch is allocated
	 */
	public boolean setBatchAsAllocate(Batch batch) {
		if(!batch.setAsAllocated()) {
			return false;
		}
		
		updateBatch(batch);
		
		return true;
	}
	
	/**
	 * Set a batch as completed and persist it
	 * Update also warehouse cell residual quantity
	 * 
	 * @param batch
	 * @return true if the batch is completed
	 */
	public boolean setBatchAsCompleted(Batch batch) {
		if(!batch.isAllocated()) {
			return false;
		}
		
		if(!batch.setAsCompleted()) {
			return false;
		}
		
		updateBatch(batch);
		warehouse.updateCellStatus();
		
		return true;
	}
	
	/**
	 * Create a batch list using the order list to pickup
	 * 
	 * @param orders
	 * @param warehouse
	 */
	public void createBatches(List<Order> orders, Warehouse warehouse) {
		//Delete batch not already assigned
		getUnmodificableBatchList().stream()
			.filter(batch -> batch.getBatchStatus().compareTo(Status.WAITING)==0)
			.forEach(batch -> deleteBatch(batch));
		
		//Compute new list of batch
		List<Batch> batches = batchesCreatorStrategy.computeListOfBatch(
				getUnmodificableBatchList(),
				orders, 
				warehouse,
				materials.getUnmodificableMaterialList());
		
		//Add new batches
		batches.stream()
			.forEach(batch -> addBatch(batch));
		
		warehouse.updateCellStatus();
		
		setChanged();
		notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		createBatches(orders.getUnmodificableOrderList(), warehouse);
	}
	
	/**
	 * Add some general information to a specific
	 * log message like class name or moreover
	 * 
	 * @param message to log
	 * @return formatted message
	 */
	protected String formatLogMessage(String message) {
		return this.getClass().getSimpleName() + " - " + message;
	}
}
