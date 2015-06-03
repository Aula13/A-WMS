package org.wms.main;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.rmautomazioni.database.common.DbConnectionConfiguration;
import it.rmautomazioni.database.common.DbStatusChecker;
import it.rmautomazioni.view.factories.FactoryReferences;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.config.Utils;
import org.wms.exception.AlreadyInstantiatedException;
import org.wms.exception.ConfigFileLoadingException;
import org.wms.exception.DBConnectionException;
import org.wms.test.TestUtils;

/**
 * WMS Unit test class
 * 
 * @author Daniele Ciriello, Stefano Pessina
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LockFile.class, 
	Logger.class, 
	Configuration.class,
	HibernateUtil.class})
@PowerMockIgnore("javax.management.*")
public class WMSUnitTest {
		
	/**
	 * Expected exception useful to check if a function throws an exception
	 */
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	public static DbStatusChecker dbStatusChecker;
	
	/**
	 * Initialize the mocks before each test
	 */
	@Before
	public void initMocks(){
		TestUtils.mockMessageBox();
		
		PowerMockito.mockStatic(LockFile.class);
		PowerMockito.mockStatic(Configuration.class);
		PowerMockito.mockStatic(HibernateUtil.class);
		dbStatusChecker = mock(DbStatusChecker.class);
		WMS.dbStatusChecker = dbStatusChecker;
		mock(Logger.class);
	}


	/**
	 * test if wms does launch an AlreadyInstantiatedException when
	 * the lock file is already locked
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAlreadyInstantiatedException() throws Exception {
		when(LockFile.checkLockFile()).thenReturn(false);
		
	    exception.expect(AlreadyInstantiatedException.class);
		WMS.launchWMS();
	}

	/**
	 * test if wms does launch a ConfigFileLoadingException when the file can't
	 * be loaded
	 * 
	 * @throws Exception
	 */
	@Test
	public void testConfigFileLoadingException() throws Exception {
		when(LockFile.checkLockFile()).thenReturn(true);
		when(Configuration.basicInfoFromFile()).thenReturn(false);
		
	    exception.expect(ConfigFileLoadingException.class);
		WMS.launchWMS();
	}
	
	/**
	 * Test should throw an exception for mission or wrong connection to the database
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDBConnectionException() throws Exception {
		when(LockFile.checkLockFile()).thenReturn(true);
		when(Configuration.basicInfoFromFile()).thenReturn(true);
		when(Configuration.getDbConfiguration()).thenReturn(new DbConnectionConfiguration("", "", "", ""));
		when(dbStatusChecker.checkDatabaseConnection()).thenReturn(false);
		
		exception.expect(DBConnectionException.class);
		WMS.launchWMS();
	}

	/**
	 * Test factories should be initialized
	 */
	@Test
	public void testInitFactory() throws Exception {
		WMS.initFactories();
		assertNotNull(FactoryReferences.appStyle);
		assertNotNull(FactoryReferences.buttons);
		assertNotNull(FactoryReferences.fields);
		assertNotNull(FactoryReferences.panels);
	}

}
