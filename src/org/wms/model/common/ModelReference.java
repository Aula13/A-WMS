package org.wms.model.common;

import org.wms.model.order.Materials;
import org.wms.model.order.Orders;

public class ModelReference {

	public static Orders ordersModel;
	
	public static Materials materialsModel;
	
	public static void initModel() {
		ordersModel = new Orders();
		materialsModel = new Materials();
	}
}
