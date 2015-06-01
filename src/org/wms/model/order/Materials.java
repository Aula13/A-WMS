package org.wms.model.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;
import org.wms.config.Configuration;
import org.wms.model.dao.MaterialDao;

/**
 * Materials model
 * This model provide high level CRUD for materials
 * 
 * This model is sinchronized though a semaphore(1)
 * 
 * This model provide observable methods
 * for get signal about materials list modifications
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class Materials extends Observable {
	
	private Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER);
	
	/**
	 * Sinchronization semaphore
	 */
	private Semaphore semaphore = new Semaphore(1);
	
	private final ICRUDLayer<Material> persistenceLayer;
	
	public Materials(ICRUDLayer<Material> persistenceLayer) {
		this.persistenceLayer = persistenceLayer;
	}
	
	/**
	 * Add a new material
	 * 
	 * @param material material to add
	 * @return true=material created succefully
	 */
	public boolean addMaterial(Material material) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(!persistenceLayer.get(material.getCode()).isPresent()) {
				result = persistenceLayer.create(material);	
				
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
	 * Delete a material
	 * 
	 * @param material material to delele
	 * @return true=material deleted succefully
	 */
	public boolean deleteMaterial(Material material) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(persistenceLayer.get(material.getCode()).isPresent()) {
				result = persistenceLayer.delete(material);	
				
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
	 * Update a material
	 * 
	 * @param material material to update
	 * @return true=material updated
	 */
	public boolean updateMaterial(Material material) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(persistenceLayer.get(material.getCode()).isPresent()) {
				result = persistenceLayer.update(material);		
				
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
	 * Get a material
	 * 
	 * @param materialId materialId to fetch
	 * @return optionally the material
	 */
	public Optional<Material> get(Long materialId) {
		
		Optional<Material> result = Optional.empty();
		
		try {
			semaphore.acquire();
			
			result = persistenceLayer.get(materialId);
			
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
		}
		
		semaphore.release();
		return result;
	}
	
	/**
	 * Provide an unmodificable list of materials
	 * 
	 * @return list of the materials
	 */
	public List<Material> getUnmodificableMaterialList() {
		
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
			semaphore.release();
			return new ArrayList<>();
		}
		
		Optional<List<Material>> opt = persistenceLayer.selectAll();
		List<Material> materials = opt.isPresent()? opt.get() : new ArrayList<>();
		
		semaphore.release();
		
		return Collections.unmodifiableList(materials);
	}
	
	/**
	 * Add some general information to a specific
	 * log message like class name or moreover
	 * 
	 * @param message to log
	 * @return formatted message
	 */
	private String formatLogMessage(String message) {
		return this.getClass().getSimpleName() + " - " + message;
	}
}
