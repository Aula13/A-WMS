package org.wms.config;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigurationUnitTest {
	
	private static Properties mockCfgFile;
	private static Configuration cfg;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockCfgFile = mock(Properties.class);
		cfg.props = mockCfgFile;
		when(mockCfgFile.load()).thenReturn
	}

	@Test
	public void testBasicInfoFromFile() {
		
	}

	@Test
	public void testGetDbConnString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetDbDriverClass() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetDbUser() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetDbPassword() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetDbConfiguration() {
		fail("Not yet implemented"); // TODO
	}

}
