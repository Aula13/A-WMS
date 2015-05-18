package org.wms.config;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test hibernate configuration
 * 
 * this test require a hibernate configuration file correctly
 * configured in /config/hiberante.cfg.xml
 * 
 * @author stefano
 *
 */
public class HibernateUtilIntegTest {

	/**
	 * Test the factory should be configured correctly
	 */
	@Test
	public void testGetSessionFactory() {
		assertTrue(HibernateUtil.getSessionFactory()!=null);
	}

	/**
	 * Test the session should be closed correctly
	 */
	@Test
	public void testCloseSession() {
		HibernateUtil.closeSession();
		assertTrue(HibernateUtil.getSessionFactory().isClosed());
	}

}
