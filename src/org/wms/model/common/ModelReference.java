package org.wms.model.common;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.wms.config.HibernateUtil;
import org.wms.model.order.Material;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;
import org.wms.model.order.OrderType;
import org.wms.model.order.Orders;

public class ModelReference {

	public static  Orders ordersModel;
	
	public static void initModel() {
		initOrders();
	}
	
	/**
	 * Load order from db
	 */
	private static void initOrders() {
		Session session = HibernateUtil.getSession();
		
		session.beginTransaction();
		List<Material> materials = session.createCriteria(Material.class).list();
		session.getTransaction().commit();
		
		session = HibernateUtil.getSession();
		
		session.beginTransaction();
		List<Order> orders = session.createCriteria(Order.class).list();
		session.getTransaction().commit();
		
		ordersModel = new Orders(orders, materials);
		
		Material material = new Material(1234);
		ordersModel.addMaterial(material);
		
		Order test = new Order(10223455, new Date(System.currentTimeMillis()), OrderType.INPUT);
		test.getMaterials().add(new OrderRow(test, material, 1));
		
		ordersModel.addOrder(test);
	}
}
