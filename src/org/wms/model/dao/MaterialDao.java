package org.wms.model.dao;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
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
			return true;
		} catch (Exception e) {
			logger.error(formatLogMessage("Error during create material " + material.getCode() + "; Exception: " + e.getMessage()));
		}
		return false;
	}

	public static boolean update(Material material) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(material);		
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(formatLogMessage("Error during get update material " + material.getCode() + "; Exception: " +e.getMessage()));
		}
		return true;
	}

	public static boolean delete(Long materialId) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.delete(new Material(materialId));		
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(formatLogMessage("Error during delete material by id " + materialId + "; Exception: "+ e.getMessage()));
		}
		return true;
	}

	public static Optional<Material> get(Long materialId) {
		Material material = null;

		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			material = (Material) session.get(Material.class, materialId);
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(formatLogMessage("Error during get material by id " + e.getMessage()));
		}
		return Optional.of(material);
	}

	public static Optional<List<Material>> selectAll() {
		List<Material> materials = null;
		
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			materials = session.createCriteria(Material.class).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(formatLogMessage("Error during get all material " + e.getMessage()));
		}
		
		return Optional.of(materials);
	}	

	private static String formatLogMessage(String message) {
		return MaterialDao.class.getSimpleName() + " - " + message;
	}
}
