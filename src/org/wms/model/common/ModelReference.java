package org.wms.model.common;

import org.wms.model.dao.MaterialDao;
import org.wms.model.dao.OrderDao;
import org.wms.model.order.Materials;
import org.wms.model.order.Orders;

/**
 * Store reference to orders and materials models
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class ModelReference {

	/**
	 * Reference to orders model
	 */
	public static Orders ordersModel;
	
	/**
	 * Reference to materials model
	 */
	public static Materials materialsModel;
	
	/**
	 * Init models
	 */
	public static void initModel() {
		ordersModel = new Orders(new OrderDao());
		materialsModel = new Materials(new MaterialDao());
	}
}
