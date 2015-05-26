package org.wms.model.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

import org.hibernate.Session;
import org.wms.config.HibernateUtil;
import org.wms.model.dao.MaterialDao;
import org.wms.model.dao.OrderDao;

public class Materials extends Observable {
	public synchronized boolean addMaterial(Material material) {
		if(!MaterialDao.create(material))
			return false;
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public synchronized boolean deleteMaterial(Material material) {
		if(!MaterialDao.delete(material.getCode()))
			return false;

		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public synchronized boolean updateMaterial(Material material) {
		if(!MaterialDao.update(material))
			return false;
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public Optional<Material> get(Long materialId) {
		return MaterialDao.get(materialId);
	}
	
	public List<Material> getUnmodificableMaterialList() {
		Optional<List<Material>> opt = MaterialDao.selectAll();
		List<Material> materials = opt.isPresent()? opt.get() : new ArrayList<>();
		return Collections.unmodifiableList(materials);
	}
}
