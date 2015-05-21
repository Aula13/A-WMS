package org.wms.model.common;

import java.util.List;

import org.hibernate.Session;
import org.wms.config.HibernateUtil;
import org.wms.model.order.Order;

public class ModelReference {

	public static List<Order> inputOrders;
	
	public static void initModel() {
		initInputOrders();
	}
	
	private static void initInputOrders() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		inputOrders = session.createCriteria(Order.class).list();
		session.getTransaction().commit();
	}
}
