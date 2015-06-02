package org.wms.model.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.config.HibernateUtil;
import org.wms.model.order.Material;

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
public class MaterialDaoIntegTest {

	private static MaterialDao materialDao = new MaterialDao();
	
	private static Material newMaterial = new Material(1000l);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateUtil.buildSessionFactory("config/hibernatetest.cfg.xml");
	}
	
	@After
	public void tearDownAfterTest() {
		materialDao.delete(newMaterial);
	}

	/**
	 * Test material should be not created
	 * because already exists
	 */
	@Test
	public void testCreateFail() {
		materialDao.create(newMaterial);
		assertFalse(materialDao.create(newMaterial));
		assertTrue(materialDao.get(newMaterial.getCode()).isPresent());
	}
	
	/**
	 * Test material should be created
	 */
	@Test
	public void testCreate() {
		assertTrue(materialDao.create(newMaterial));
		assertTrue(materialDao.get(newMaterial.getCode()).isPresent());
	}

	/**
	 * Test material should be updated
	 */
	@Test
	public void testUpdate() {
		materialDao.create(newMaterial);
		assertTrue(materialDao.update(newMaterial));
	}

	/**
	 * Test material should be deleted
	 */
	@Test
	public void testDelete() {
		materialDao.create(newMaterial);
		assertTrue(materialDao.delete(newMaterial));
		assertFalse(materialDao.get(newMaterial.getCode()).isPresent());
	}

	/**
	 * Test material should be fetched
	 */
	@Test
	public void testGet() {
		materialDao.create(newMaterial);
		assertTrue(materialDao.get(newMaterial.getCode()).isPresent());
		materialDao.delete(newMaterial);
		assertFalse(materialDao.get(newMaterial.getCode()).isPresent());
	}

	/**
	 * Test materials list should be fetched
	 */
	@Test
	public void testSelectAll() {
		materialDao.create(newMaterial);
		Optional<List<Material>> opt = materialDao.selectAll();
		assertTrue(opt.isPresent());
		assertTrue(opt.get().size()==1);
		assertTrue(opt.get().get(0).getCode()==newMaterial.getCode());
	}

}
