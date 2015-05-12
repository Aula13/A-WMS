package org.wms.config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class ConfigurationUnitTest {
	
	private Properties mockProp;
	private FileReader mockFileReader;
	
	private String testDbCon = "DBCONSTR";
	private String testDbDriver = "DBDRIVER";
	private String testDbPsw = "DBPSW";
	private String testDbUsr = "DBUSR";
	
	private static int testUserLogoutTime = 100;
	
	@Before
	public void setUpBefore() throws Exception {
		mockProp = mock(Properties.class);
		Configuration.props = mockProp;
		mockFileReader = mock(FileReader.class);
		Configuration.cfgFile = mockFileReader;
		
		when(mockProp.getProperty(Configuration.DATABASE_CONNECTION_STRING_PROP_NAME)).thenReturn(testDbCon);
		when(mockProp.getProperty(Configuration.DATABASE_DRIVER_CLASS_PROP_NAME)).thenReturn(testDbDriver);
		when(mockProp.getProperty(Configuration.DATABASE_PASSWORD_PROP_NAME)).thenReturn(testDbPsw);
		when(mockProp.getProperty(Configuration.DATABASE_USER_PROP_NAME)).thenReturn(testDbUsr);
		when(mockProp.getProperty(Configuration.USER_LOGOUT_TIME_MIN_PROP_NAME)).thenReturn(Integer.toString(testUserLogoutTime));
		
		Configuration.basicInfoFromFile();
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
		assertTrue(Configuration.getDbConnString()==testDbCon);
	}

	@Test
	public void testGetDbDriverClass() {
		assertTrue(Configuration.getDbDriverClass()==testDbDriver);
	}

	@Test
	public void testGetDbUser() {
		assertTrue(Configuration.getDbUser()==testDbUsr);
	}

	@Test
	public void testGetDbPassword() {
		assertTrue(Configuration.getDbPassword()==testDbPsw);
	}

	@Test
	public void testGetDbConfiguration() {
		assertTrue(Configuration.getDbConfiguration().getConnectionString()==testDbCon);
		assertTrue(Configuration.getDbConfiguration().getDriverName()==testDbDriver);
		assertTrue(Configuration.getDbConfiguration().getPassword()==testDbPsw);
		assertTrue(Configuration.getDbConfiguration().getUserName()==testDbUsr);
	}
	
	@Test
	public void testUserLogoutTime() {
		assertTrue(Configuration.USER_LOGOUT_TIME_MIN==testUserLogoutTime);
	}

}
