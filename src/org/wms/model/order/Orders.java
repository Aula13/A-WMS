package org.wms.model.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.wms.config.HibernateUtil;
import org.wms.model.dao.OrderDao;

public class Orders extends Observable {
	
	public synchronized boolean addOrder(Order order) {
		if(!OrderDao.create(order))
			return false;
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public synchronized boolean deleteOrder(Order order) {
		if(!OrderDao.delete(order))
			return false;
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public synchronized boolean updateOrder(Order order) {
		if(!OrderDao.update(order))
			return false;
		
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	public List<Order> getUnmodificableOrderList() {
		Optional<List<Order>> opt = OrderDao.selectAll();
		List<Order> orders = opt.isPresent()? opt.get() : new ArrayList<>();
		return Collections.unmodifiableList(orders);
	}
	
	public List<Order> getUnmodificableOrderList(OrderType orderType) {
		Optional<List<Order>> opt = OrderDao.selectAll();
		List<Order> orders = opt.isPresent()? opt.get() : new ArrayList<>();
		List<Order> filteredOrders = orders.stream()
				.filter(order -> order.getType()==orderType)
				.collect(Collectors.toList());
		return Collections.unmodifiableList(filteredOrders);
	}
	
	public Long newOrderId(){
//		Long newOrderId;
//		Random random = new Random();
//		long LOWER_RANGE = 0001000;
//		long UPPER_RANGE = 9999999;
//		
//		do {
//			newOrderId = LOWER_RANGE + 
//                    (long)(random.nextDouble()*(UPPER_RANGE - LOWER_RANGE));
//		} while (orders.containsKey(newOrderId));
//		return newOrderId;
		return 00000001l;
	}
}
