package org.wms.view.warehouse;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;


import org.wms.controller.warehouse.WarehouseShelfController;
import org.wms.model.warehouse.WarehouseLine;
import org.wms.model.warehouse.WarehouseShelf;

public class WarehouseLineView extends JPanel {
	
	private WarehouseLine line;

	public WarehouseLineView(WarehouseLine line) {
		super();
		this.line = line;
		initComponents();
		initUI();
	}
	
	private void initComponents() {
		
	}
	
	private void initUI() {
		setLayout(new GridLayout(line.getUnmodifiableListShelfs().size(),0));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		List<WarehouseShelf> shelfs = new ArrayList<>(line.getUnmodifiableListShelfs());
				
		Collections.sort(shelfs, new Comparator<WarehouseShelf>() {
			
			@Override
			public int compare(WarehouseShelf o1, WarehouseShelf o2) {
				if(o1.getId()>o2.getId())
					return 1;
				if(o1.getId()<o2.getId())
					return -1;
				
				return 0;
			}
		});		
				
		shelfs.stream()
		.forEach(shelf -> 
		{
			WarehouseShelfView shelfView = new WarehouseShelfView(shelf);
			new WarehouseShelfController(shelf, shelfView);
			add(shelfView);
		});
	}

}
