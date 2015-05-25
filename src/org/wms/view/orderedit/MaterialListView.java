package org.wms.view.orderedit;

import java.awt.GridLayout;

import it.rmautomazioni.view.factories.FactoryReferences;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.wms.controller.orderedit.MaterialListTableModel;
import org.wms.model.order.Order;

public class MaterialListView extends JPanel {
	
	private Order order;
	
	private JTable tblMaterials;
	
	private MaterialListTableModel tblMaterialsModel;

	public MaterialListView(Order order) {
		super();
		this.order = order;
		initComponent();
		initUI();
	}	
	
	private void initComponent() {
		tblMaterialsModel = new MaterialListTableModel(order);
		tblMaterials = FactoryReferences.appStyle.getTableClass(tblMaterialsModel);
	}
	
	private void initUI() {
		setLayout(new GridLayout(1,1));		
		
		JScrollPane scrollPane = new JScrollPane(tblMaterials);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(scrollPane);
	}
}
