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
import org.wms.model.material.Material;
import org.wms.model.warehouse.WarehouseCell;
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
public class WarehouseCellDaoIntegTest {

	private static WarehouseCellDao warehouseCellDao = new WarehouseCellDao();
	
	private static WarehouseShelfDao warehouseShelfDao = new WarehouseShelfDao();
	
	private static WarehouseLineDao warehouseLineDao = new WarehouseLineDao();
	
	private static MaterialDao materialDao = new MaterialDao();
	
	private static WarehouseCell newWarehouseCell;
	
	private static WarehouseShelf newShelf;
	
	private static WarehouseLine newLine;
	
	private static Material material = new Material(1000l);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateUtil.buildSessionFactory("config/hibernatetest.cfg.xml");
		
		newLine = new WarehouseLine(1l, "A");
		warehouseLineDao.create(newLine);
		
		newShelf = new WarehouseShelf(1l, newLine);
		warehouseShelfDao.create(newShelf);
		
		materialDao.create(material);
		
		newWarehouseCell = new WarehouseCell(1l, material, 100, newShelf);	
	}
	
	@After
	public void tearDownAfterTest() {
		warehouseCellDao.delete(newWarehouseCell);
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		warehouseShelfDao.delete(newShelf);
		warehouseLineDao.delete(newLine);
		materialDao.delete(material);
		HibernateUtil.closeSessionFactory();
	}
	
	/**
	 * Test warehouseCell should be created
	 */
	@Test
	public void testCreate() {
		assertTrue(warehouseCellDao.create(newWarehouseCell));
		assertTrue(warehouseCellDao.get(newWarehouseCell.getId()).isPresent());
	}

	/**
	 * Test warehouseCell should be updated
	 */
	@Test
	public void testUpdate() {
		warehouseCellDao.create(newWarehouseCell);
		assertTrue(warehouseCellDao.update(newWarehouseCell));
	}

	/**
	 * Test warehouseCell should be deleted
	 */
	@Test
	public void testDelete() {
		warehouseCellDao.create(newWarehouseCell);
		assertTrue(warehouseCellDao.delete(newWarehouseCell));
		assertFalse(warehouseCellDao.get(newWarehouseCell.getId()).isPresent());
	}

	/**
	 * Test warehouseCell should be fetched
	 */
	@Test
	public void testGet() {
		assertTrue(warehouseCellDao.create(newWarehouseCell));
		assertTrue(warehouseCellDao.get(newWarehouseCell.getId()).isPresent());
		assertTrue(warehouseCellDao.delete(newWarehouseCell));
		assertFalse(warehouseCellDao.get(newWarehouseCell.getId()).isPresent());
	}

	/**
	 * Test warehouseCells list should be fetched
	 */
	@Test
	public void testSelectAll() {
		warehouseCellDao.create(newWarehouseCell);
		Optional<List<WarehouseCell>> opt = warehouseCellDao.selectAll();
		assertTrue(opt.isPresent());
		assertTrue(opt.get().size()==1);
		assertTrue(opt.get().get(0).getId()==newWarehouseCell.getId());
	}

}
