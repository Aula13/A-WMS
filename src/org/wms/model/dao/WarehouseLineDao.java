package org.wms.model.dao;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.model.common.ICRUDLayer;
import org.wms.model.warehouse.WarehouseLine;

/**
 * Warehouse lines Database Access Object
 * 
 * Store CRUD method to the database, 
 * manage open/close connection hibernate method
 * 
 * Moreover manage errors from the database
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WarehouseLineDao implements ICRUDLayer<WarehouseLine> {

	private static Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER); 

	
	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#create(java.lang.Object)
	 */
	@Override
	public boolean create(WarehouseLine warehouseLine) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(warehouseLine);			
			session.getTransaction().commit();	
			
			HibernateUtil.closeSession();
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during create warehouseLine " + warehouseLine.getId() + "; Exception: " + e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#update(java.lang.Object)
	 */
	@Override
	public boolean update(WarehouseLine warehouseLine) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();	
			session.saveOrUpdate(warehouseLine);
			session.getTransaction().commit();
			
			HibernateUtil.closeSession();
			
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get update warehouseLine " + warehouseLine.getId() + "; Exception: " + e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(WarehouseLine warehouseLine) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.delete(warehouseLine);		
			session.getTransaction().commit();
			
			HibernateUtil.closeSession();
			
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during delete warehouseLine by id " + warehouseLine.getId() + "; Exception: "+ e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#get(java.lang.Long)
	 */
	@Override
	public Optional<WarehouseLine> get(Long warehouseLineId) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			WarehouseLine warehouseLine = (WarehouseLine) session.get(WarehouseLine.class, warehouseLineId);
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			
			if(warehouseLine!=null)
				return Optional.of(warehouseLine);
			else
				return Optional.empty();
			
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get warehouseLine by id " + warehouseLineId + "; Exception: "+ e));
		}
		
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#selectAll()
	 */
	@Override
	public Optional<List<WarehouseLine>> selectAll() {
		try {
			//Fetch warehouse lines
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			List<WarehouseLine> warehouseLines = session.createCriteria(WarehouseLine.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) //Filter hibernate join duplicated results
					.list();
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			return Optional.of(warehouseLines);
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get all warehouseLine " + e));
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
