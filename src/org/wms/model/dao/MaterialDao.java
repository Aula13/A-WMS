package org.wms.model.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.wms.config.HibernateUtil;
import org.wms.model.order.Material;

public class MaterialDao {
	
	public static boolean create(Material material) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(material);
		session.getTransaction().commit();
		
		return true;
	}
	
	public static boolean update(Material material) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(material);		
		session.getTransaction().commit();
		
		return true;
	}
	
	public static boolean delete(Long materialId) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.delete(new Material(materialId));		
		session.getTransaction().commit();
		
		return true;
	}
	
	public static Optional<List<Material>> selectAll() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		List<Material> materials = session.createCriteria(Material.class).list();
		session.getTransaction().commit();
		return Optional.of(materials);
	}	
}
