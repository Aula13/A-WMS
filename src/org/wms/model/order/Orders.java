package org.wms.model.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.model.dao.OrderDao;

public class Orders extends Observable {
	
	private Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER);
	
	private Semaphore semaphore = new Semaphore(1);
	
	public boolean addOrder(Order order) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(!OrderDao.get(order.getId()).isPresent()) {
				result = OrderDao.create(order);
			
				semaphore.release();
				
				setChanged();
				notifyObservers();
			}
			
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
		}
		
		semaphore.release();
		return result;
	}
	
	public boolean deleteOrder(Order order) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(OrderDao.get(order.getId()).isPresent()) {
				result = OrderDao.delete(order);
			
				semaphore.release();
				
				setChanged();
				notifyObservers();
			}
			
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
		}
		
		semaphore.release();
		return result;
	}
	
	public boolean updateOrder(Order order) {
		
		boolean result = false;
		
		try {
			semaphore.acquire();
			
			if(OrderDao.get(order.getId()).isPresent()) {
				result = OrderDao.update(order);
				
				semaphore.release();
			
				setChanged();
				notifyObservers();
			}
			
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
		}
		
		semaphore.release();
		return result;
	}
	
	public List<Order> getUnmodificableOrderList() {
		
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
			semaphore.release();
			return new ArrayList<>();
		}
		
		Optional<List<Order>> opt = OrderDao.selectAll();
		List<Order> orders = opt.isPresent()? opt.get() : new ArrayList<>();
		
		semaphore.release();
		
		return Collections.unmodifiableList(orders);
	}
	
	public List<Order> getUnmodificableOrderList(OrderType orderType) {
		
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
			semaphore.release();
			return new ArrayList<>();
		}
		
		Optional<List<Order>> opt = OrderDao.selectAll();
		List<Order> orders = opt.isPresent()? opt.get() : new ArrayList<>();
		List<Order> filteredOrders = orders.stream()
				.filter(order -> order.getType()==orderType)
				.collect(Collectors.toList());
		
		semaphore.release();
		
		return Collections.unmodifiableList(filteredOrders);
	}
	
	private String formatLogMessage(String message) {
		return this.getClass().getSimpleName() + " - " + message;
	}
}
