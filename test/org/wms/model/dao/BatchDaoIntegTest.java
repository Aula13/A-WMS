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
import org.wms.model.batch.Batch;
import org.wms.model.common.ListType;

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
public class BatchDaoIntegTest {

	private static BatchDao batchDao = new BatchDao();
	
	private static Batch newBatch = new Batch(1000l, ListType.INPUT);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateUtil.buildSessionFactory("config/hibernatetest.cfg.xml");
	}
	
	@After
	public void tearDownAfterTest() {
		batchDao.delete(newBatch);
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		HibernateUtil.closeSessionFactory();
	}

	/**
	 * Test batch should be not created
	 * because already exists
	 */
	@Test
	public void testCreateFail() {
		batchDao.create(newBatch);
		assertFalse(batchDao.create(newBatch));
		assertTrue(batchDao.get(newBatch.getId()).isPresent());
	}
	
	/**
	 * Test batch should be created
	 */
	@Test
	public void testCreate() {
		assertTrue(batchDao.create(newBatch));
		assertTrue(batchDao.get(newBatch.getId()).isPresent());
	}

	/**
	 * Test batch should be updated
	 */
	@Test
	public void testUpdate() {
		batchDao.create(newBatch);
		assertTrue(batchDao.update(newBatch));
	}

	/**
	 * Test batch should be deleted
	 */
	@Test
	public void testDelete() {
		batchDao.create(newBatch);
		assertTrue(batchDao.delete(newBatch));
		assertFalse(batchDao.get(newBatch.getId()).isPresent());
	}

	/**
	 * Test batch should be fetched
	 */
	@Test
	public void testGet() {
		batchDao.create(newBatch);
		assertTrue(batchDao.get(newBatch.getId()).isPresent());
		batchDao.delete(newBatch);
		assertFalse(batchDao.get(newBatch.getId()).isPresent());
	}

	/**
	 * Test batches list should be fetched
	 */
	@Test
	public void testSelectAll() {
		batchDao.create(newBatch);
		Optional<List<Batch>> opt = batchDao.selectAll();
		assertTrue(opt.isPresent());
		assertTrue(opt.get().size()==1);
		assertTrue(opt.get().get(0).getId()==newBatch.getId());
	}

}
