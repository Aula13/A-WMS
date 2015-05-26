package org.wms.view.orderedit;

import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import org.wms.controller.orderedit.MaterialListTableModel;
import org.wms.model.order.Material;
import org.wms.model.order.Order;

public class MaterialListView extends JPanel {
	
	private Order order;
	
	private JTable tblMaterials;
	
	private MaterialListTableModel tblMaterialsModel;

	List<Material> availableMaterials;
	
	public MaterialListView(Order order, List<Material> availableMaterials) {
		super();
		this.order = order;
		this.availableMaterials = availableMaterials;
		initComponent();
		initUI();
	}	
	
	private void initComponent() {
		tblMaterialsModel = new MaterialListTableModel(order);
		tblMaterials = FactoryReferences.appStyle.getTableClass(tblMaterialsModel);
		
		ComboBoxCellEditor cmbCellEditor = new ComboBoxCellEditor(new JComboBox<>(availableMaterials.stream().
				map(material -> material.getCode()).toArray()));
		tblMaterials.getColumnModel().getColumn(0).setCellEditor(cmbCellEditor);		
		SpinnerCellEditor spnCellEditor = new SpinnerCellEditor();
		tblMaterials.getColumnModel().getColumn(1).setCellEditor(spnCellEditor);		
		
		tblMaterials.setRowHeight(40);
	}
	
	private void initUI() {
		setLayout(new GridLayout(1,1));		
		
		JScrollPane scrollPane = new JScrollPane(tblMaterials);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(scrollPane);
	}
}
