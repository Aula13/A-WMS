package org.wms.view.warehouse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.wms.model.batch.Batches;
import org.wms.model.warehouse.Warehouse;
import org.wms.model.warehouse.WarehouseLine;

/**
 * WarehouseView shows the warehouse layout in a scrollable
 * panel.
 * 
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WarehouseView extends JPanel {

	private Warehouse warehouse;
	
	/**
	 * Create warehouse layout
	 * 
	 * @param warehouse
	 */
	public WarehouseView(Warehouse warehouse) {
		super();
		this.warehouse = warehouse;
		initUI();
	}
	
	/**
	 * Create UI
	 */
	private void initUI() {
		setName("WAREHOUSE STATUS");
		setLayout(new BorderLayout());
		
		JPanel warehousemap = new JPanel(new GridLayout(1,0));
		JScrollPane scrollpane = new JScrollPane(warehousemap, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		add(scrollpane, BorderLayout.CENTER);
		
		warehousemap.setLayout(new GridLayout(1, warehouse.getUnmodifiableLines().size()*2));
		warehousemap.setSize(1000, this.getHeight());
		warehousemap.setPreferredSize(new Dimension(1000, 1000));
		
		int i=0;
		for (WarehouseLine line : warehouse.getUnmodifiableLines()) {
			WarehouseLineView lineView = new WarehouseLineView(line);
			warehousemap.add(lineView);
			if(i%2==0)
				warehousemap.add(new JPanel());
			
			i+=2;
		}

	}	
}
