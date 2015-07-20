package org.wms.view.warehouse;

import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.wms.config.IconTypeAWMS;
import org.wms.controller.warehouse.WarehouseCellsTableModel;
import org.wms.model.warehouse.WarehouseShelf;

/**
 * The JDialog shows the cells status stored in a shelf  
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WarehouseShelfDetailsView extends JDialog {
	
	private WarehouseShelf shelf;
	
	private JPanel containerPanel;
	
	private JScrollPane cellsTablePanel;
	private WarehouseCellsTableModel tblWarehouseCellModel;
	private JTable tblWarehouseCell;
	
	private JPanel confirmationPanel;
	private JButton btnConfirm;
	
	/**
	 * Create JDialog to show the cells status of the shelf
	 * 
	 * @param shelf
	 */
	public WarehouseShelfDetailsView(WarehouseShelf shelf) {
		super();
		this.shelf = shelf;
		initUI();
		
	}

	/**
	 * Set view appearance
	 */
	private void initUI() {
		setModal(true);
		setSize(700, 500);
		setLocationRelativeTo(null);
		
		initCellsTablePanel();
		
		initConfirmationPanel();
		
		initContainerPanel();
	}
	
	/**
	 * Add the cell table panel
	 */
	private void initCellsTablePanel() {
		
		tblWarehouseCellModel = new WarehouseCellsTableModel(shelf);
		tblWarehouseCell = FactoryReferences.appStyle.getTableClass(tblWarehouseCellModel);
		
		tblWarehouseCell.setRowHeight(40);
		
		cellsTablePanel = new JScrollPane(tblWarehouseCell);
		cellsTablePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
	}
	
	/**
	 * Init container panel
	 */
	private void initContainerPanel(){
		containerPanel = FactoryReferences.appStyle.getPanelClass();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		add(containerPanel);
		containerPanel.add(cellsTablePanel);
		containerPanel.add(confirmationPanel);
	}
	
	/**
	 * Init panel with confirm/cancel buttons
	 */
	private void initConfirmationPanel(){
		confirmationPanel = FactoryReferences.appStyle.getPanelClass();
		confirmationPanel.setBackground(Color.BLUE);
		confirmationPanel.setLayout(new BoxLayout(confirmationPanel, BoxLayout.X_AXIS));
		
		btnConfirm = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.CONFIRM.name());
		
		confirmationPanel.add(Box.createHorizontalGlue());
		confirmationPanel.add(btnConfirm);
	}
	
	/**
	 * @return reference to the confirm button
	 */
	public JButton getBtnConfirm() {
		return btnConfirm;
	}
}
