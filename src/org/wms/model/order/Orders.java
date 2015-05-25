package org.wms.model.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.hibernate.Session;
import org.wms.config.HibernateUtil;

public class Orders extends Observable {

	private Map<Long, Order> orders = new HashMap<>();

	public Orders(List<Order> orders) {
		super();
		for (Order ord : orders)
			this.orders.put(ord.getId(), ord);
	}	
	
	public boolean addOrder(Order order) {
		if(!orders.containsKey(order.getId()))
			return false;
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(order);
		session.getTransaction().commit();
		
		orders.put(order.getId(), order);
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public boolean deleteOrder(Order order) {
		if(!orders.containsKey(order.getId()))
			return false;
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.delete(order);
		session.getTransaction().commit();
		
		orders.remove(order.getId());
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public boolean updateOrder(Order order) {
		if(!orders.containsKey(order.getId()))
			return false;
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(order);
		session.getTransaction().commit();
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public List<Order> getUnmodificableOrderList() {
		return Collections.unmodifiableList(new ArrayList<>(orders.values()));
	}
}
