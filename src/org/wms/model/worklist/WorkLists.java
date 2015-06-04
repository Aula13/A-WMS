package org.wms.model.worklist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.wms.config.Configuration;
import org.wms.model.common.ICRUDLayer;
import org.wms.model.common.ListType;

/**
 * WorkLists model
 * This model provide high level CRUD for worklists
 * 
 * This model is synchronized though a semaphore(1)
 * 
 * This model provide observable methods
 * for get signal about Work list modifications
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WorkLists extends Observable {
	
	private Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER);
	
	/**
	 * Synchronization semaphore
	 */
	protected Semaphore semaphore = new Semaphore(1);
	
	private ICRUDLayer<WorkList> persistentLayer;
	
	/**
	 * Constructor
	 * 
	 * @param persistentLayer class the provide method for persistence
	 */
	public WorkLists(ICRUDLayer<WorkList> persistentLayer) {
		super();
		this.persistentLayer = persistentLayer;
	}

	/**
	 * Add a new workList
	 * 
	 * @param workList workList to add
	 * @return true=workList created succefully
	 */
	public boolean addWorkList(WorkList workList) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(!persistentLayer.get(workList.getId()).isPresent()) {
				result = persistentLayer.create(workList);
			
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
	 * Delete a workList
	 * 
	 * @param workList workList to delele
	 * @return true=workList deleted succefully
	 */
	public boolean deleteWorkList(WorkList workList) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(persistentLayer.get(workList.getId()).isPresent()) {
				result = persistentLayer.delete(workList);
			
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
	 * Update a workList
	 * 
	 * @param workList workList to update
	 * @return true=workList updated
	 */
	public boolean updateWorkList(WorkList workList) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(persistentLayer.get(workList.getId()).isPresent()) {
				result = persistentLayer.update(workList);
				
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
	 * Get a workList
	 * 
	 * @param workListId workListId to fetch
	 * @return optionally the workList
	 */
	public Optional<WorkList> get(Long workListId) {
		
		Optional<WorkList> result = Optional.empty();
		
		try {
			semaphore.acquire();
			
			result = persistentLayer.get(workListId);
			
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
		}
		
		semaphore.release();
		return result;
	}
	
	/**
	 * Provide an unmodificable list of worklists
	 * 
	 * @return list of the worklists
	 */
	public List<WorkList> getUnmodificableWorkListList() {
		
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
			semaphore.release();
			return new ArrayList<>();
		}
		
		Optional<List<WorkList>> opt = persistentLayer.selectAll();
		List<WorkList> workLists = opt.isPresent()? opt.get() : new ArrayList<>();
		
		semaphore.release();
		
		return Collections.unmodifiableList(workLists);
	}
	
	/**
	 * Provide an unmodificable list of worklists
	 * filtered by the workList type
	 * 
	 * @return list of the worklists
	 */
	public List<WorkList> getUnmodificableWorkListList(ListType workListType) {
		
		List<WorkList> workLists = getUnmodificableWorkListList();
		List<WorkList> filteredWorkLists = workLists.stream()
				.filter(workList -> workList.getType()==workListType)
				.collect(Collectors.toList());
		
		semaphore.release();
		
		return Collections.unmodifiableList(filteredWorkLists);
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
