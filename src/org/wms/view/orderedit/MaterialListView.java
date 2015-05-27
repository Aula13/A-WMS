package org.wms.view.orderedit;

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
import org.wms.controller.orderedit.MaterialListTableModel;
import org.wms.model.order.Material;
import org.wms.model.order.Order;

public class MaterialListView extends JPanel {
	
	private Order order;
	
	private JTable tblMaterials;
	private ComboBoxCellEditor cmbCellEditor;
	private SpinnerCellEditor spnCellEditor;
	
	private MaterialListTableModel tblMaterialsModel;

	private JButton btnAddMaterial;
	private JButton btnRemoveMaterial;
	
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
		
		cmbCellEditor = new ComboBoxCellEditor(new JComboBox<>(availableMaterials.stream().
				map(material -> material.getId()).toArray()));
		tblMaterials.getColumnModel().getColumn(0).setCellEditor(cmbCellEditor);		
		spnCellEditor = new SpinnerCellEditor();
		tblMaterials.getColumnModel().getColumn(1).setCellEditor(spnCellEditor);		
		
		tblMaterials.setRowHeight(40);
		
		btnAddMaterial = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.PLUS.name());
		btnRemoveMaterial = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.MINUS.name());
	}
	
	private void initUI() {
		setLayout(new BorderLayout());		
		
		JScrollPane scrollPane = new JScrollPane(tblMaterials);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel cmdPanel = new JPanel();
		cmdPanel.setLayout(new BoxLayout(cmdPanel, BoxLayout.Y_AXIS));
		cmdPanel.add(btnAddMaterial);
		cmdPanel.add(btnRemoveMaterial);
		
		add(cmdPanel, BorderLayout.EAST);
	}
	
	public JTable getTblMaterials() {
		return tblMaterials;
	}
	
	public ComboBoxCellEditor getCmbCellEditor() {
		return cmbCellEditor;
	}
	
	public SpinnerCellEditor getSpnCellEditor() {
		return spnCellEditor;
	}
	
	public MaterialListTableModel getTblMaterialsModel() {
		return tblMaterialsModel;
	}

	public JButton getBtnAddMaterial() {
		return btnAddMaterial;
	}
	
	public JButton getBtnRemoveMaterial() {
		return btnRemoveMaterial;
	}
}
