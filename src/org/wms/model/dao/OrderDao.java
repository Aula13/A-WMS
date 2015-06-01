package org.wms.model.dao;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.model.order.ICRUDLayer;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;

/**
 * Order Database Access Object
 * 
 * Store CRUD method to the database, 
 * manage open/close connection hibernate method
 * 
 * Moreover manage errors from the database
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrderDao implements ICRUDLayer<Order> {

	private static Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER); 

	
	/* (non-Javadoc)
	 * @see org.wms.model.order.ICRUDLayer#create(java.lang.Object)
	 */
	@Override
	public boolean create(Order order) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(order);
			
			for (OrderRow orderRow : order.getUnmodificableMaterials())
				session.save(orderRow);
			
			session.getTransaction().commit();	
			
			HibernateUtil.closeSession();
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during create order " + order.getId() + "; Exception: " + e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.order.ICRUDLayer#update(java.lang.Object)
	 */
	@Override
	public boolean update(Order order) {
		try {
			//If some orderRows item was deleted, delete it also from the db
			Session session = HibernateUtil.getSession();
			session.beginTransaction();		
			Criteria criteria = session.createCriteria(OrderRow.class);
			criteria.add(Restrictions.eq("order", order));
			List<OrderRow> orderRows = criteria.list();
			for (OrderRow orderRow : orderRows) {
				if(!order.getUnmodificableMaterials().contains(orderRow))
					session.delete(orderRow);
			}
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			
			//Save or update other orderRow
			session = HibernateUtil.getSession();
			session.beginTransaction();	
			for (OrderRow orderRow : order.getUnmodificableMaterials())
				session.saveOrUpdate(orderRow);
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			
			session = HibernateUtil.getSession();
			session.beginTransaction();	
			session.saveOrUpdate(order);
			session.getTransaction().commit();
			
			HibernateUtil.closeSession();
			
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get update order " + order.getId() + "; Exception: " + e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.order.ICRUDLayer#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(Order order) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.delete(order);		
			session.getTransaction().commit();
			
			HibernateUtil.closeSession();
			
			return true;
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during delete order by id " + order.getId() + "; Exception: "+ e));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wms.model.order.ICRUDLayer#get(java.lang.Long)
	 */
	@Override
	public Optional<Order> get(Long orderId) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			Order order = (Order) session.get(Order.class, orderId);
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			
			if(order!=null)
				return Optional.of(order);
			else
				return Optional.empty();
			
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get order by id " + orderId + "; Exception: "+ e));
		}
		
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see org.wms.model.order.ICRUDLayer#selectAll()
	 */
	@Override
	public Optional<List<Order>> selectAll() {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			List<Order> orders = session.createCriteria(Order.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) //Filter hibernate join duplicated results
					.list();
			session.getTransaction().commit();
			HibernateUtil.closeSession();
			return Optional.of(orders);
		} catch (Exception e) {
			HibernateUtil.closeSession();
			logger.error(formatLogMessage("Error during get all order " + e));
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
