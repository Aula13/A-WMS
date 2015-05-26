package org.wms.model.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.wms.config.HibernateUtil;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;

public class OrderDao {

	public static boolean create(Order order) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(order);
		
		for (OrderRow materialRow : order.getMaterials()) {
			session.save(materialRow);
		}
		
		session.getTransaction().commit();
		
		return true;
	}
	
	public static boolean update(Order order) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(order);
		
		for (OrderRow materialRow : order.getMaterials()) {
			session.saveOrUpdate(materialRow);
		}
		
		session.getTransaction().commit();
		
		return true;
	}
	
	public static boolean delete(Long orderId) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.delete(new Order(orderId, null, null));		
		session.getTransaction().commit();
		
		return true;
	}
	
	public static Optional<Order> select(Long orderId) {
		return null;
	}
	
	public static Optional<List<Order>> selectAll() {
		return null;
	}
	
}
