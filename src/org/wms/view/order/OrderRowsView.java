package org.wms.view.order;

import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import org.wms.config.IconTypeAWMS;
import org.wms.controller.order.OrderRowsTableModel;
import org.wms.model.material.Material;
import org.wms.model.order.Order;
import org.wms.view.common.ComboBoxCellEditorCustom;
import org.wms.view.common.SpinnerCellEditor;

/**
 * Order rows view contains a table for order row editing
 * 
 * It provide also a command bar for add or delete an order row
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrderRowsView extends JPanel {
	
	private Order order;
	
	private JTable tblOrderRows;
	private ComboBoxCellEditorCustom cmbMaterialCodeCellEditor;
	private SpinnerCellEditor spnOrderRowQuantityCellEditor;
	
	private OrderRowsTableModel tblOrderRowsModel;

	private JButton btnAddOrderRow;
	private JButton btnRemoveOrderRow;
	
	List<Material> availableMaterials;
	
	public OrderRowsView(Order order, List<Material> availableMaterials) {
		super();
		this.order = order;
		this.availableMaterials = availableMaterials;
		initComponent();
		initUI();
	}	
	
	/**
	 * Init components to be placed in the gui
	 */
	private void initComponent() {
		tblOrderRowsModel = new OrderRowsTableModel(order);
		tblOrderRows = FactoryReferences.appStyle.getTableClass(tblOrderRowsModel);
		
		cmbMaterialCodeCellEditor = new ComboBoxCellEditorCustom(new JComboBox<>(availableMaterials.stream().
				map(material -> material.getCode()).toArray()));
		tblOrderRows.getColumnModel().getColumn(0).setCellEditor(cmbMaterialCodeCellEditor);		
		spnOrderRowQuantityCellEditor = new SpinnerCellEditor();
		tblOrderRows.getColumnModel().getColumn(1).setCellEditor(spnOrderRowQuantityCellEditor);		
		
		tblOrderRows.setRowHeight(40);
		
		btnAddOrderRow = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.PLUS.name());
		btnRemoveOrderRow = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.MINUS.name());
	}
	
	/**
	 * Place each component in the gui
	 */
	private void initUI() {
		setLayout(new BorderLayout());		
		
		JScrollPane scrollPane = new JScrollPane(tblOrderRows);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel cmdPanel = new JPanel();
		cmdPanel.setLayout(new BoxLayout(cmdPanel, BoxLayout.Y_AXIS));
		cmdPanel.add(btnAddOrderRow);
		cmdPanel.add(btnRemoveOrderRow);
		
		add(cmdPanel, BorderLayout.EAST);
	}
	
	/**
	 * @return reference to order rows table
	 */
	public JTable getTblOrderRows() {
		return tblOrderRows;
	}
	
	/**
	 * @return reference to the material code combo table editor
	 */
	public ComboBoxCellEditor getCmbMaterialCodeCellEditor() {
		return cmbMaterialCodeCellEditor;
	}
	
	/**
	 * @return reference to the order row quantity spinner table editor
	 */
	public SpinnerCellEditor getSpnOrderRowQuantityCellEditor() {
		return spnOrderRowQuantityCellEditor;
	}
	
	/**
	 * @return reference to order rows table model
	 */
	public OrderRowsTableModel getTblOrderRowsModel() {
		return tblOrderRowsModel;
	}

	/**
	 * @return reference to add order row button
	 */
	public JButton getBtnAddOrderRow() {
		return btnAddOrderRow;
	}
	
	public JButton getBtnRemoveOrderRow() {
		return btnRemoveOrderRow;
	}
}
