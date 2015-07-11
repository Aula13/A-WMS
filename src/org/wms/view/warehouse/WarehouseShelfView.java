package org.wms.view.warehouse;

import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.wms.model.warehouse.WarehouseShelf;

public class WarehouseShelfView extends JPanel {
	
	private JTextField shelfCode;
	
	private WarehouseShelf shelf;
	
	
	public WarehouseShelfView(WarehouseShelf shelf) {
		super();
		this.shelf = shelf;
		initComponents();
		initUI();
	}
	
	private void initComponents() {
		shelfCode = FactoryReferences.appStyle.getTitleClass();
		shelfCode.setText(shelf.getPublicId());
	}
	
	private void initUI() {
		setLayout(new GridBagLayout());
		
		add(shelfCode,
				new GridBagConstraints(0, 0, 0, 0, 1.0, 1.0, 
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
						new Insets(2, 2, 2, 2), 0, 0));
	}	
}
