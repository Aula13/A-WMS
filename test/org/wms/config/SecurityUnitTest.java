package org.wms.config;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test configuration file should be correctly initialized
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class SecurityUnitTest {

	/**
	 * Setup test envirorment
	 * call security initialization method
	 */
	@BeforeClass
	public static void setupBeforeClass() {
		SecurityConfig.initializeSecurity(10);
	}
	
	/**
	 * test security manager should be initialized
	 */
	@Test
	public void testGetSecurityManager() {
		assertTrue(SecurityConfig.getSecurityManager()!=null);
	}
	
	/**
	 * test timeout user thread should be alive and running
	 */
	@Test
	public void testTimeoutUserCheckerStarted() {
		assertTrue(SecurityConfig.timeoutChecker.isAlive());
	}
}
