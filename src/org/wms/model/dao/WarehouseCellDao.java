package org.wms.model.dao;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.model.common.ICRUDLayer;
import org.wms.model.warehouse.WarehouseCell;

/**
 * Warehouse cell Database Access Object
 * 
 * Store CRUD method to the database, 
 * manage open/close connection hibernate method
 * 
 * Moreover manage errors from the database
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WarehouseCellDao implements ICRUDLayer<WarehouseCell> {

	private static Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER); 

	
	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#create(java.lang.Object)
	 */
	@Override
	public boolean create(WarehouseCell warehouseCell) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(warehouseCell);			
			session.getTransaction().commit();	
			
//			HibernateUtil.closeSession();
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during create warehouseCell " + warehouseCell.getId() + "; Exception: " + e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#update(java.lang.Object)
	 */
	@Override
	public boolean update(WarehouseCell warehouseCell) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();	
			session.saveOrUpdate(warehouseCell);
			session.getTransaction().commit();
			
//			HibernateUtil.closeSession();
			
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get update warehouseCell " + warehouseCell.getId() + "; Exception: " + e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(WarehouseCell warehouseCell) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.delete(warehouseCell);		
			session.getTransaction().commit();
			
//			HibernateUtil.closeSession();
			
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during delete warehouseCell by id " + warehouseCell.getId() + "; Exception: "+ e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#get(java.lang.Long)
	 */
	@Override
	public Optional<WarehouseCell> get(Long warehouseCellId) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			WarehouseCell warehouseCell = (WarehouseCell) session.get(WarehouseCell.class, warehouseCellId);
			session.getTransaction().commit();
//			HibernateUtil.closeSession();
			
			if(warehouseCell!=null)
				return Optional.of(warehouseCell);
			else
				return Optional.empty();
			
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get warehouseCell by id " + warehouseCellId + "; Exception: "+ e));
		}
		
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#selectAll()
	 */
	@Override
	public Optional<List<WarehouseCell>> selectAll() {
		try {
			//Fetch warehouse lines
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			List<WarehouseCell> warehouseCells = session.createCriteria(WarehouseCell.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) //Filter hibernate join duplicated results
					.list();
			session.getTransaction().commit();
//			HibernateUtil.closeSession();
			return Optional.of(warehouseCells);
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get all warehouseCell " + e));
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
