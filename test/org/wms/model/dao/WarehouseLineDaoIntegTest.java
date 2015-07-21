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
public class WarehouseLineDaoIntegTest {

	private static WarehouseLineDao warehouseLineDao = new WarehouseLineDao();
	
	private static WarehouseLine newWarehouseLine = new WarehouseLine(1l,"A");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateUtil.buildSessionFactory("config/hibernatetest.cfg.xml");
	}
	
	@After
	public void tearDownAfterTest() {
		warehouseLineDao.delete(newWarehouseLine);
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		HibernateUtil.closeSessionFactory();
	}

	/**
	 * Test warehouseLine should be not created
	 * because already exists
	 */
	@Test
	public void testCreateFail() {
		warehouseLineDao.create(newWarehouseLine);
		assertFalse(warehouseLineDao.create(newWarehouseLine));
		assertTrue(warehouseLineDao.get(newWarehouseLine.getId()).isPresent());
	}
	
	/**
	 * Test warehouseLine should be created
	 */
	@Test
	public void testCreate() {
		assertTrue(warehouseLineDao.create(newWarehouseLine));
		assertTrue(warehouseLineDao.get(newWarehouseLine.getId()).isPresent());
	}

	/**
	 * Test warehouseLine should be updated
	 */
	@Test
	public void testUpdate() {
		warehouseLineDao.create(newWarehouseLine);
		assertTrue(warehouseLineDao.update(newWarehouseLine));
	}

	/**
	 * Test warehouseLine should be deleted
	 */
	@Test
	public void testDelete() {
		warehouseLineDao.create(newWarehouseLine);
		assertTrue(warehouseLineDao.delete(newWarehouseLine));
		assertFalse(warehouseLineDao.get(newWarehouseLine.getId()).isPresent());
	}

	/**
	 * Test warehouseLine should be fetched
	 */
	@Test
	public void testGet() {
		warehouseLineDao.create(newWarehouseLine);
		assertTrue(warehouseLineDao.get(newWarehouseLine.getId()).isPresent());
		warehouseLineDao.delete(newWarehouseLine);
		assertFalse(warehouseLineDao.get(newWarehouseLine.getId()).isPresent());
	}

	/**
	 * Test warehouseLines list should be fetched
	 */
	@Test
	public void testSelectAll() {
		warehouseLineDao.create(newWarehouseLine);
		Optional<List<WarehouseLine>> opt = warehouseLineDao.selectAll();
		assertTrue(opt.isPresent());
		assertTrue(opt.get().size()==1);
		assertTrue(opt.get().get(0).getId()==newWarehouseLine.getId());
	}

}
