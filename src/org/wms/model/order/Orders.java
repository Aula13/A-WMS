package org.wms.model.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.wms.config.HibernateUtil;

public class Orders extends Observable {

	private Map<Long, Order> orders = new HashMap<>();
	
	private Map<Long, Material> materials = new HashMap<>();

	public Orders(List<Order> orders, List<Material> materials) {
		super();
		for (Order ord : orders)
			this.orders.put(ord.getId(), ord);
		for (Material mat : materials)
			this.materials.put(mat.getCode(), mat);
	}	
	
	public synchronized boolean addOrder(Order order) {
		if(orders.containsKey(order.getId()))
			return false;
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(order);
		
		for (OrderRow material : order.getMaterials()) {
			session.save(material);
		}
		
		session.getTransaction().commit();
		
		orders.put(order.getId(), order);
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public synchronized boolean deleteOrder(Order order) {
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
	
	public synchronized boolean updateOrder(Order order) {
		if(!orders.containsKey(order.getId()))
			return false;
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.update(order);
		session.getTransaction().commit();
		
		for (OrderRow material : order.getMaterials()) {
			session.saveOrUpdate(material);
		}
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public List<Order> getUnmodificableOrderList() {
		return Collections.unmodifiableList(new ArrayList<>(orders.values()));
	}
	
	public List<Order> getUnmodificableOrderList(OrderType orderType) {
		List<Order> orderList = (new ArrayList<>(orders.values())).stream()
				.filter(order -> order.getType()==orderType)
				.collect(Collectors.toList());
		return orderList;
	}
	
	public synchronized boolean addMaterial(Material material) {
		if(materials.containsKey(material.getCode()))
			return false;
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(material);
		session.getTransaction().commit();
		
		materials.put(material.getCode(), material);
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public synchronized boolean deleteMaterial(Material material) {
		if(!materials.containsKey(material.getCode()))
			return false;
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.delete(material);
		session.getTransaction().commit();
		
		materials.remove(material.getCode());
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public synchronized boolean updateMaterial(Material material) {
		if(!materials.containsKey(material.getCode()))
			return false;
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.update(material);
		session.getTransaction().commit();
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public List<Material> getUnmodificableMaterialList() {
		return Collections.unmodifiableList(new ArrayList<>(materials.values()));
	}
}
