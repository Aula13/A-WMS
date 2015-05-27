package org.wms.model.dao;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.model.order.Material;

public class MaterialDao {

	private static Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER); 

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
			logger.error(formatLogMessage("Error during create material " + material.getId() + "; Exception: " + e));
		}
		return false;
	}

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
			logger.error(formatLogMessage("Error during get update material " + material.getId() + "; Exception: " + e));
		}
		return true;
	}

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
			logger.error(formatLogMessage("Error during delete material by id " + material.getId() + "; Exception: "+ e));
		}
		return false;
	}

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

	private static String formatLogMessage(String message) {
		return MaterialDao.class.getSimpleName() + " - " + message;
	}
}
