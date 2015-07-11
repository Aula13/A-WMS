package org.wms.view.warehouse;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;


import javax.swing.JPanel;


import org.wms.model.warehouse.WarehouseLine;

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
		
		line.getUnmodifiableListShelfs().stream()
		.forEach(shelf -> 
		{
			WarehouseShelfView shelfView = new WarehouseShelfView(shelf);
			add(shelfView);
		});
	}

}
