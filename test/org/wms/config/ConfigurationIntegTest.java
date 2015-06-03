package org.wms.config;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Check configuration file loaded correctly
 * This test require a correct configuration file in /config/config.properties
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class ConfigurationIntegTest {

	/**
	 * Test file configuration loaded successfully
	 */
	@Test
	public void testBasicInfoFromFile() {
		assertTrue(Configuration.basicInfoFromFile());
	}

}
