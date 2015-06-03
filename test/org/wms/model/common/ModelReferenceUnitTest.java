package org.wms.model.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModelReferenceUnitTest {

	/**
	 * Test materials and orders model
	 * reference should be correctly
	 * initialized
	 */
	@Test
	public void testInitModel() {
		ModelReference.initModel();
		assertNotNull(ModelReference.materialsModel);
		assertNotNull(ModelReference.ordersModel);
	}

}
