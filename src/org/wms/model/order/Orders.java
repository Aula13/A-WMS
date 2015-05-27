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
import org.wms.model.dao.MaterialDao;
import org.wms.model.dao.OrderDao;

/**
 * Orders model
 * This model provide high level CRUD for orders
 * 
 * This model is sinchronized though a semaphore(1)
 * 
 * This model provide observable methods
 * for get signal about orders list modifications
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class Orders extends Observable {
	
	private Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER);
	
	/**
	 * Sinchronization semaphore
	 */
	private Semaphore semaphore = new Semaphore(1);
	
	/**
	 * Add a new order
	 * 
	 * @param order order to add
	 * @return true=order created succefully
	 */
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
	
	/**
	 * Delete a order
	 * 
	 * @param order order to delele
	 * @return true=order deleted succefully
	 */
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
	
	/**
	 * Update a order
	 * 
	 * @param order order to update
	 * @return true=order updated
	 */
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
	
	/**
	 * Get a order
	 * 
	 * @param orderId orderId to fetch
	 * @return optionally the order
	 */
	public Optional<Order> get(Long orderId) {
		
		Optional<Order> result = Optional.empty();
		
		try {
			semaphore.acquire();
			
			result = OrderDao.get(orderId);
			
		} catch (InterruptedException e) {
			logger.error(formatLogMessage("Error during semaphore acquire " + e));
		}
		
		semaphore.release();
		return result;
	}
	
	/**
	 * Provide an unmodificable list of materials
	 * 
	 * @return list of the materials
	 */
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
	
	/**
	 * Provide an unmodificable list of materials
	 * filtered by the order type
	 * 
	 * @return list of the materials
	 */
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
