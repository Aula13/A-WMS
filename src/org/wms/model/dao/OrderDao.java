package org.wms.model.dao;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;

public class OrderDao {

	private static Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER); 

	public static boolean create(Order order) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(order);
			
			for (OrderRow orderRow : order.getMaterials())
				session.save(orderRow);
			
			session.getTransaction().commit();			
			return true;
		} catch (Exception e) {
			logger.error(formatLogMessage("Error during create order " + order.getId() + "; Exception: " + e.getMessage()));
		}
		return false;
	}

	public static boolean update(Order order) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(order);		
			
			for (OrderRow orderRow : order.getMaterials())
				session.saveOrUpdate(orderRow);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(formatLogMessage("Error during get update order " + order.getId() + "; Exception: " +e.getMessage()));
		}
		return true;
	}

	public static boolean delete(Long orderId) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.delete(new Order(orderId, null, null));		
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(formatLogMessage("Error during delete order by id " + orderId + "; Exception: "+ e.getMessage()));
		}
		return true;
	}

	public static Optional<Order> get(Long orderId) {
		Order order = null;

		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			order = (Order) session.get(Order.class, orderId);
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(formatLogMessage("Error during get order by id " + e.getMessage()));
		}
		return Optional.of(order);
	}

	public static Optional<List<Order>> selectAll() {
		List<Order> orders = null;
		
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			orders = session.createCriteria(Order.class).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(formatLogMessage("Error during get all order " + e.getMessage()));
		}
		
		return Optional.of(orders);
	}		
	
	private static String formatLogMessage(String message) {
		return OrderDao.class.getSimpleName() + " - " + message;
	}
}
