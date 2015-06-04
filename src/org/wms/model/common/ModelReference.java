package org.wms.model.common;

import org.wms.model.dao.MaterialDao;
import org.wms.model.dao.OrderDao;
import org.wms.model.dao.WarehouseCellDao;
import org.wms.model.dao.WarehouseLineDao;
import org.wms.model.dao.WarehouseShelfDao;
import org.wms.model.material.Materials;
import org.wms.model.order.Orders;
import org.wms.model.warehouse.Warehouse;
import org.wms.model.worklist.WorkLists;

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
	
	public static Warehouse warehouseModel;
	
	public static WorkLists workListsModel;
	
	/**
	 * Init models
	 */
	public static void initModel() {
		ordersModel = new Orders(new OrderDao());
		materialsModel = new Materials(new MaterialDao());
		warehouseModel = new Warehouse(new WarehouseLineDao(),
				new WarehouseShelfDao(),
				new WarehouseCellDao());
		
	}
}
