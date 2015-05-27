package org.wms.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.wms.exception.ConfigFileLoadingException;

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

	@Before
	public void beforeTest() throws ConfigFileLoadingException {
		if(HibernateUtil.getSessionFactory()==null)
			HibernateUtil.buildSessionFactory();
	}
	
	/**
	 * Test the factory should be configured correctly
	 * @throws ConfigFileLoadingException 
	 */
	@Test
	public void testGetSessionFactory() {
		assertNotNull(HibernateUtil.getSessionFactory());
	}

	/**
	 * Test the session should be closed correctly
	 */
	@Test
	public void testCloseSession() {
		HibernateUtil.closeSessionFactory();
		assertTrue(HibernateUtil.getSessionFactory().isClosed());
	}

}
