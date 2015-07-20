package org.wms.view.warehouse;

import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.wms.model.warehouse.WarehouseShelf;

/**
 * Create a view for a shelf
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WarehouseShelfView extends JPanel {
	
	private JTextField shelfCode;
	
	private WarehouseShelf shelf;
	
	
	/**
	 * Create shelf view
	 * 
	 * @param shelf
	 */
	public WarehouseShelfView(WarehouseShelf shelf) {
		super();
		this.shelf = shelf;
		initComponents();
		initUI();
	}
	
	/**
	 * Init UI components
	 */
	private void initComponents() {
		shelfCode = FactoryReferences.appStyle.getTitleClass();
		shelfCode.setText(shelf.getPublicId());
	}
	
	/**
	 * Place the compontents inside the UI
	 */
	private void initUI() {
		setLayout(new GridBagLayout());
		
		add(shelfCode,
				new GridBagConstraints(0, 0, 0, 0, 1.0, 1.0, 
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
						new Insets(2, 2, 2, 2), 0, 0));
	}	
	
	/**
	 * @return shelf code TextField
	 */
	public JTextField getShelfCodeField() {
		return shelfCode;
	}
}
