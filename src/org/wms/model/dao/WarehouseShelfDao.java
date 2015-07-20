package org.wms.model.dao;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.model.common.ICRUDLayer;
import org.wms.model.warehouse.WarehouseShelf;

/**
 * Warehouse shelf Database Access Object
 * 
 * Store CRUD method to the database, 
 * manage open/close connection hibernate method
 * 
 * Moreover manage errors from the database
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WarehouseShelfDao implements ICRUDLayer<WarehouseShelf> {

	private static Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER); 

	
	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#create(java.lang.Object)
	 */
	@Override
	public boolean create(WarehouseShelf warehouseShelf) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(warehouseShelf);			
			session.getTransaction().commit();	
			
//			HibernateUtil.closeSession();
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during create warehouseShelf " + warehouseShelf.getId() + "; Exception: " + e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#update(java.lang.Object)
	 */
	@Override
	public boolean update(WarehouseShelf warehouseShelf) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();	
			session.saveOrUpdate(warehouseShelf);
			session.getTransaction().commit();
			
//			HibernateUtil.closeSession();
			
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get update warehouseShelf " + warehouseShelf.getId() + "; Exception: " + e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(WarehouseShelf warehouseShelf) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.delete(warehouseShelf);		
			session.getTransaction().commit();
			
//			HibernateUtil.closeSession();
			
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during delete warehouseShelf by id " + warehouseShelf.getId() + "; Exception: "+ e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#get(java.lang.Long)
	 */
	@Override
	public Optional<WarehouseShelf> get(Long warehouseShelfId) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			WarehouseShelf warehouseShelf = (WarehouseShelf) session.get(WarehouseShelf.class, warehouseShelfId);
			session.getTransaction().commit();
//			HibernateUtil.closeSession();
			
			if(warehouseShelf!=null)
				return Optional.of(warehouseShelf);
			else
				return Optional.empty();
			
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get warehouseShelf by id " + warehouseShelfId + "; Exception: "+ e));
		}
		
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#selectAll()
	 */
	@Override
	public Optional<List<WarehouseShelf>> selectAll() {
		try {
			//Fetch warehouse lines
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			List<WarehouseShelf> warehouseShelfs = session.createCriteria(WarehouseShelf.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) //Filter hibernate join duplicated results
					.list();
			session.getTransaction().commit();
//			HibernateUtil.closeSession();
			return Optional.of(warehouseShelfs);
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get all warehouseShelf " + e));
		}
		
		return Optional.empty();
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
