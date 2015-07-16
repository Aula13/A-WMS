package org.wms.controller.warehouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.wms.model.warehouse.WarehouseShelf;
import org.wms.view.warehouse.WarehouseShelfDetailsView;
import org.wms.view.warehouse.WarehouseShelfView;

/**
 * Shelf view controller manage shelf details view behaviours
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WarehouseShelfController {

	private WarehouseShelf shelf;
	
	private WarehouseShelfView view;

	/**
	 * Open shelf details view when shelf code field
	 * is pressed 
	 * 
	 * @param shelf
	 * @param view
	 */
	public WarehouseShelfController(WarehouseShelf shelf,
			WarehouseShelfView view) {
		super();
		this.shelf = shelf;
		this.view = view;
		
		view.getShelfCodeField().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				openShelfDetailsView();
			}
		});
	}
	
	/**
	 * Open shelf details view
	 */
	protected void openShelfDetailsView() {
		WarehouseShelfDetailsView detailsView = new WarehouseShelfDetailsView(shelf);
		new WarehouseShelfDetailsController(shelf, detailsView);
		detailsView.setVisible(true);
	}

}
