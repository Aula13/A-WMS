package org.wms.model.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.config.HibernateUtil;
import org.wms.model.order.Order;

/**
 * Test integration with database
 * through hibernate layer
 * 
 * This test require hibernate correctly configured
 * in config/hibernatetest.cfg.xml
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrderDaoIntegTest {

	private static OrderDao orderDao = new OrderDao();
	
	private static Order newOrder = new Order(1000l);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateUtil.buildSessionFactory("config/hibernatetest.cfg.xml");
	}
	
	@After
	public void tearDownAfterTest() {
		orderDao.delete(newOrder);
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		HibernateUtil.closeSessionFactory();
	}

	/**
	 * Test order should be not created
	 * because already exists
	 */
	@Test
	public void testCreateFail() {
		orderDao.create(newOrder);
		assertFalse(orderDao.create(newOrder));
		assertTrue(orderDao.get(newOrder.getId()).isPresent());
	}
	
	/**
	 * Test order should be created
	 */
	@Test
	public void testCreate() {
		assertTrue(orderDao.create(newOrder));
		assertTrue(orderDao.get(newOrder.getId()).isPresent());
	}

	/**
	 * Test order should be updated
	 */
	@Test
	public void testUpdate() {
		orderDao.create(newOrder);
		assertTrue(orderDao.update(newOrder));
	}

	/**
	 * Test order should be deleted
	 */
	@Test
	public void testDelete() {
		orderDao.create(newOrder);
		assertTrue(orderDao.delete(newOrder));
		assertFalse(orderDao.get(newOrder.getId()).isPresent());
	}

	/**
	 * Test order should be fetched
	 */
	@Test
	public void testGet() {
		orderDao.create(newOrder);
		assertTrue(orderDao.get(newOrder.getId()).isPresent());
		orderDao.delete(newOrder);
		assertFalse(orderDao.get(newOrder.getId()).isPresent());
	}

	/**
	 * Test orders list should be fetched
	 */
	@Test
	public void testSelectAll() {
		orderDao.create(newOrder);
		Optional<List<Order>> opt = orderDao.selectAll();
		assertTrue(opt.isPresent());
		assertTrue(opt.get().size()==1);
		assertTrue(opt.get().get(0).getId()==newOrder.getId());
	}

}
