package org.wms.model.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.model.dao.MaterialDao;
import org.wms.model.dao.OrderDao;

public class Materials extends Observable {
	
	private Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER);
	
	private Semaphore semaphore = new Semaphore(1);
	
	public boolean addMaterial(Material material) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(!MaterialDao.get(material.getCode()).isPresent()) {
				result = MaterialDao.create(material);	
				
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
	
	public boolean deleteMaterial(Material material) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(MaterialDao.get(material.getCode()).isPresent()) {
				result = MaterialDao.delete(material);	
				
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
	
	public boolean updateMaterial(Material material) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(MaterialDao.get(material.getCode()).isPresent()) {
				result = MaterialDao.update(material);		
				
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
	
	public Optional<Material> get(Long materialId) {
		
		Optional<Material> result = Optional.empty();
		
		try {
			semaphore.acquire();
			
			result = MaterialDao.get(materialId);
			
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
		}
		
		semaphore.release();
		return result;
	}
	
	public List<Material> getUnmodificableMaterialList() {
		
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
			semaphore.release();
			return new ArrayList<>();
		}
		
		Optional<List<Material>> opt = MaterialDao.selectAll();
		List<Material> materials = opt.isPresent()? opt.get() : new ArrayList<>();
		
		semaphore.release();
		
		return Collections.unmodifiableList(materials);
	}
	
	private String formatLogMessage(String message) {
		return this.getClass().getSimpleName() + " - " + message;
	}
}
