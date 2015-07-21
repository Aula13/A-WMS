package org.wms.view.warehouse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.model.warehouse.WarehouseLine;
import org.wms.model.warehouse.WarehouseShelf;
import org.wms.test.TestUtils;

public class WarehouseLineViewUnitTest {

	private static WarehouseLine mockWarehouseLine;
	
	private static WarehouseShelf mockWarehouseShelf;
	
	@BeforeClass
	public static void setupBeforeClass() {
		TestUtils.initGUIFactories();
		
		mockWarehouseLine = mock(WarehouseLine.class);
		mockWarehouseShelf = mock(WarehouseShelf.class);
		
		when(mockWarehouseLine.getPublicId()).thenReturn("A");
		
		List<WarehouseShelf> shelfs = new ArrayList<WarehouseShelf>();
		shelfs.add(mockWarehouseShelf);
		
		when(mockWarehouseLine.getUnmodifiableListShelfs()).thenReturn(shelfs);
		
		when(mockWarehouseShelf.getPublicId()).thenReturn("A/1");
	}
	
	@Test
	public void testWarehouseLineView() {
		WarehouseLineView view = new WarehouseLineView(mockWarehouseLine);
	}

}
