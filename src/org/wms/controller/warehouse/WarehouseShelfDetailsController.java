package org.wms.controller.warehouse;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;

import org.wms.model.warehouse.WarehouseShelf;
import org.wms.view.warehouse.WarehouseShelfDetailsView;

public class WarehouseShelfDetailsController {
	
	WarehouseShelf shelf;
	WarehouseShelfDetailsView view;
	
	public WarehouseShelfDetailsController(WarehouseShelf shelf,
			WarehouseShelfDetailsView view) {
		super();
		this.shelf = shelf;
		this.view = view;
		
		new AbstractJButtonActionListener(view.getBtnConfirm()) {
			
			@Override
			public void actionTriggered() {
				view.dispose();
			}
		};
	}
	
	
}
