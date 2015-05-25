package org.wms.model.common;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.wms.config.HibernateUtil;
import org.wms.model.order.Order;
import org.wms.model.order.OrderType;
import org.wms.model.order.Orders;

public class ModelReference {

	public static  Orders ordersModel;
	
	public static void initModel() {
		initInputOrders();
	}
	
	/**
	 * Load input order from db
	 */
	private static void initInputOrders() {
		Session session = HibernateUtil.getSession();
		
		session.beginTransaction();
		List<Order> orders = session.createCriteria(Order.class).list();
		session.getTransaction().commit();
		
		ordersModel = new Orders(orders);
		
		ordersModel.addOrder(new Order(10223455, new Date(System.currentTimeMillis()), OrderType.INPUT));
		ordersModel.addOrder(new Order(54982321, new Date(System.currentTimeMillis()), OrderType.OUTPUT));
	}
}
