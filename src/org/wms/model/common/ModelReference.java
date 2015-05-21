package org.wms.model.common;

import java.util.Date;
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
		
		Order order = new Order(1002453523, new Date(2015, 05, 3));
		session.save(order);
		
		inputOrders = session.createCriteria(Order.class).list();
				
		session.getTransaction().commit();
		
		for (Order ord : inputOrders) {
			System.out.println(ord.getId());
		}
	}
}
