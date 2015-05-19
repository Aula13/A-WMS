package org.wms.main;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test lock file 
 *
 * this test will create a real lock file (no mocks)
 * 
 * @author stefano
 *
 */
public class LockFileIntegTest {
	
	/**
	 * Test lock file should be locked correctly
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCheckLockFileRight() throws IOException {
		assertTrue(LockFile.checkLockFile());
	}

}
