package org.wms.model.dao;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.model.common.ICRUDLayer;
import org.wms.model.worklist.Batch;

/**
 * Batch Database Access Object
 * 
 * Store CRUD method to the database, 
 * manage open/close connection hibernate method
 * 
 * Moreover manage errors from the database
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class BatchDao implements ICRUDLayer<Batch> {

	private static Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER); 

	
	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#create(java.lang.Object)
	 */
	@Override
	public boolean create(Batch batch) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(batch);			
			session.getTransaction().commit();	
			
			HibernateUtil.closeSession();
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during create batch " + batch.getId() + "; Exception: " + e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#update(java.lang.Object)
	 */
	@Override
	public boolean update(Batch batch) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();	
			session.saveOrUpdate(batch);
			session.getTransaction().commit();
			
			HibernateUtil.closeSession();
			
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get update batch " + batch.getId() + "; Exception: " + e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(Batch batch) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.delete(batch);		
			session.getTransaction().commit();
			
			HibernateUtil.closeSession();
			
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during delete batch by id " + batch.getId() + "; Exception: "+ e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#get(java.lang.Long)
	 */
	@Override
	public Optional<Batch> get(Long batchId) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			Batch batch = (Batch) session.get(Batch.class, batchId);
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			
			if(batch!=null)
				return Optional.of(batch);
			else
				return Optional.empty();
			
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get batch by id " + batchId + "; Exception: "+ e));
		}
		
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see org.wms.model.common.ICRUDLayer#selectAll()
	 */
	@Override
	public Optional<List<Batch>> selectAll() {
		try {
			//Fetch warehouse lines
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			List<Batch> batches = session.createCriteria(Batch.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) //Filter hibernate join duplicated results
					.list();
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			return Optional.of(batches);
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get all batch " + e));
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
