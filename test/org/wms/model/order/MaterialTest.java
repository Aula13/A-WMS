package org.wms.model.order;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class MaterialTest {

	private static Material m;
	
	@BeforeClass
	public static void setupBeforeClass() {
		m = new Material(10l);
	}
	
	/**
	 * Test material code is assigned
	 * correctly
	 */
	@Test
	public void testMaterialLong() {
		assertTrue(m.code==10l);
	}

	/**
	 * Test code is corretly provided
	 * by getter method
	 */
	@Test
	public void testGetCode() {
		assertTrue(m.getCode()==10l);
	}

}
