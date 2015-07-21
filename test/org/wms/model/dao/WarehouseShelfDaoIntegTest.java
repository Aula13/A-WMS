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
import org.wms.model.warehouse.WarehouseLine;
import org.wms.model.warehouse.WarehouseShelf;

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
public class WarehouseShelfDaoIntegTest {

	private static WarehouseLineDao lineDao = new WarehouseLineDao();
	private static WarehouseShelfDao warehouseShelfDao = new WarehouseShelfDao();
	
	private static WarehouseShelf newWarehouseShelf;
	
	private static WarehouseLine newLine = new WarehouseLine(1l,"A");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateUtil.buildSessionFactory("config/hibernatetest.cfg.xml");
		
		lineDao.create(newLine);
		
		newWarehouseShelf =  new WarehouseShelf(1l, newLine);
	}
	
	@After
	public void tearDownAfterTest() {
		warehouseShelfDao.delete(newWarehouseShelf);	
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		lineDao.delete(newLine);
		HibernateUtil.closeSessionFactory();
	}

	/**
	 * Test warehouseShelf should be not created
	 * because already exists
	 */
	@Test
	public void testCreateFail() {
		warehouseShelfDao.create(newWarehouseShelf);
		assertFalse(warehouseShelfDao.create(newWarehouseShelf));
		assertTrue(warehouseShelfDao.get(newWarehouseShelf.getId()).isPresent());
	}
	
	/**
	 * Test warehouseShelf should be created
	 */
	@Test
	public void testCreate() {
		assertTrue(warehouseShelfDao.create(newWarehouseShelf));
		assertTrue(warehouseShelfDao.get(newWarehouseShelf.getId()).isPresent());
	}

	/**
	 * Test warehouseShelf should be updated
	 */
	@Test
	public void testUpdate() {
		warehouseShelfDao.create(newWarehouseShelf);
		assertTrue(warehouseShelfDao.update(newWarehouseShelf));
	}

	/**
	 * Test warehouseShelf should be deleted
	 */
	@Test
	public void testDelete() {
		warehouseShelfDao.create(newWarehouseShelf);
		assertTrue(warehouseShelfDao.delete(newWarehouseShelf));
		assertFalse(warehouseShelfDao.get(newWarehouseShelf.getId()).isPresent());
	}

	/**
	 * Test warehouseShelf should be fetched
	 */
	@Test
	public void testGet() {
		warehouseShelfDao.create(newWarehouseShelf);
		assertTrue(warehouseShelfDao.get(newWarehouseShelf.getId()).isPresent());
		warehouseShelfDao.delete(newWarehouseShelf);
		assertFalse(warehouseShelfDao.get(newWarehouseShelf.getId()).isPresent());
	}

	/**
	 * Test warehouseShelfs list should be fetched
	 */
	@Test
	public void testSelectAll() {
		warehouseShelfDao.create(newWarehouseShelf);
		Optional<List<WarehouseShelf>> opt = warehouseShelfDao.selectAll();
		assertTrue(opt.isPresent());
		assertTrue(opt.get().size()==1);
		assertTrue(opt.get().get(0).getId()==newWarehouseShelf.getId());
	}

}
