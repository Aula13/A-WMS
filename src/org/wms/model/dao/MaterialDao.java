package org.wms.model.dao;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.model.order.Material;

/**
 * Material Database Access Object
 * 
 * Store CRUD method to the database, 
 * manage open/close connection hibernate method
 * 
 * Moreover manage errors from the database
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class MaterialDao {

	private static Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER); 

	/**
	 * 
	 * Create a material on the database
	 * 
	 * @param material material to create
	 * @return true=material created succefully
	 */
	public static boolean create(Material material) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(material);
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during create material " + material.getCode() + "; Exception: " + e));
		}
		return false;
	}

	/**
	 * 
	 * Update a material on the database
	 * 
	 * @param material material to update
	 * @return true=material updated succefully
	 */
	public static boolean update(Material material) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.saveOrUpdate(material);		
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			return false;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get update material " + material.getCode() + "; Exception: " + e));
		}
		return true;
	}

	/**
	 * 
	 * Delete a material on the database
	 * 
	 * @param material material to delete
	 * @return true=material deleted succefully
	 */
	public static boolean delete(Material material) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.delete(material);		
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during delete material by id " + material.getCode() + "; Exception: "+ e));
		}
		return false;
	}

	/**
	 * 
	 * Get a specific material on the database
	 * 
	 * @param materialId materialId to fetch
	 * @return optionally the material searched
	 */
	public static Optional<Material> get(Long materialId) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			Material material = (Material) session.get(Material.class, materialId);
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			
			if(material!=null)
				return Optional.of(material);
			else
				return Optional.empty();
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get material by id " + e));
		}
		return Optional.empty();
	}


	/**
	 * Return all the materials stored on the database
	 * 
	 * @return optionally the list of materials
	 */
	public static Optional<List<Material>> selectAll() {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			List<Material> materials = session.createCriteria(Material.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) //Filter hibernate join duplicated results
					.list();
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			
			return Optional.of(materials);
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get all material " + e));
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
	private static String formatLogMessage(String message) {
		return MaterialDao.class.getSimpleName() + " - " + message;
	}
}
