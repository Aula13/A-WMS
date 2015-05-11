package org.wms.config;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigurationUnitTest {
	
	private static Properties mockProp;
	private static FileReader mockFileReader;
	
	private static Configuration cfg;
	
	private static String testDbCon = "DBCONSTR";
	private static String testDbDriver = "DBDRIVER";
	private static String testDbPsw = "DBPSW";
	private static String testDbUsr = "DBUSR";
	
	private static int testUserLogoutTime = 100;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockProp = mock(Properties.class);
		cfg.props = mockProp;
		mockFileReader = mock(FileReader.class);
		cfg.cfgFile = mockFileReader;
		
		when(mockProp.getProperty(Configuration.DATABASE_CONNECTION_STRING_PROP_NAME)).thenReturn(testDbCon);
		when(mockProp.getProperty(Configuration.DATABASE_DRIVER_CLASS_PROP_NAME)).thenReturn(testDbDriver);
		when(mockProp.getProperty(Configuration.DATABASE_PASSWORD_PROP_NAME)).thenReturn(testDbPsw);
		when(mockProp.getProperty(Configuration.DATABASE_USER_PROP_NAME)).thenReturn(testDbUsr);
		when(mockProp.getProperty(Configuration.USER_LOGOUT_TIME_MIN_PROP_NAME)).thenReturn(Integer.toString(testUserLogoutTime));
	}

	@Test
	public void testBasicInfoFromFile() {
		assertTrue(Configuration.basicInfoFromFile());
	}
	
	@Test
	public void testBasicInfoFromFileFail() throws IOException {
		doThrow(new FileNotFoundException()).when(mockProp).load(mockFileReader);
		assertFalse(Configuration.basicInfoFromFile());
		
		doThrow(new IOException()).when(mockProp).load(mockFileReader);
		assertFalse(Configuration.basicInfoFromFile());
	}

	@Test
	public void testGetDbConnString() {
		assertTrue(cfg.getDbConnString().compareTo(testDbCon)==0);
	}

	@Test
	public void testGetDbDriverClass() {
		assertTrue(cfg.getDbDriverClass()==testDbDriver);
	}

	@Test
	public void testGetDbUser() {
		assertTrue(cfg.getDbUser()==testDbUsr);
	}

	@Test
	public void testGetDbPassword() {
		assertTrue(cfg.getDbPassword()==testDbPsw);
	}

	@Test
	public void testGetDbConfiguration() {
		assertTrue(cfg.getDbConfiguration().getConnectionString()==testDbCon);
		assertTrue(cfg.getDbConfiguration().getDriverName()==testDbDriver);
		assertTrue(cfg.getDbConfiguration().getPassword()==testDbPsw);
		assertTrue(cfg.getDbConfiguration().getUserName()==testDbUsr);
	}
	
	@Test
	public void testUserLogoutTime() {
		assertTrue(cfg.USER_LOGOUT_TIME_MIN==testUserLogoutTime);
	}

}
