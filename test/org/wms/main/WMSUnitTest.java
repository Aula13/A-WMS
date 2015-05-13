package org.wms.main;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.rmautomazioni.view.common.MessageBox;

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
import org.wms.exception.AlreadyInstantiatedException;
import org.wms.exception.ConfigFileLoadingException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LockFile.class, Logger.class, MessageBox.class})
@PowerMockIgnore("javax.management.*")
public class WMSUnitTest {
	
	//private static WMS wmsTest;
	
	@Rule
	 public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void initMocks(){
		PowerMockito.mockStatic(LockFile.class);
		PowerMockito.mockStatic(MessageBox.class);
		//PowerMockito.mockStatic(Configuration.class);
		mock(Logger.class);
		mock(Configuration.class);
	}


	@Test
	public void testAlreadyInstantiatedException() throws Exception {
		when(LockFile.checkLockFile()).thenReturn(false);
		
	    exception.expect(AlreadyInstantiatedException.class);
		WMS.launchWMS();
	}

	@Test
	public void testConfigFileLoadingException() throws Exception {
		when(LockFile.checkLockFile()).thenReturn(true);
		when(Configuration.basicInfoFromFile()).thenReturn(false);
		
	    exception.expect(ConfigFileLoadingException.class);
		WMS.launchWMS();
	}
	


}
