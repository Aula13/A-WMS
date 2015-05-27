package org.wms.controller.orderedit;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;
import it.rmautomazioni.view.common.MessageBox;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import org.wms.controller.common.AbstractTableSelectionListener;
import org.wms.model.order.Material;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;
import org.wms.view.orderedit.MaterialListView;
import org.wms.view.orderedit.SpinnerCellEditor;

public class MaterialListViewController {

	private MaterialListView view;

	private Order order;

	private List<Material> availableMaterials;

	public MaterialListViewController(MaterialListView view, Order order, List<Material> availableMaterials) {
		super();
		this.view = view;
		this.order = order;
		this.availableMaterials = availableMaterials;
		
		view.getBtnRemoveMaterial().setVisible(false);
		
		view.getTblMaterials().addMouseListener(new AbstractTableSelectionListener() {
			
			@Override
			public void validSelectionTrigger(boolean doubleClick, int rowIndex, boolean requireMenu) {
				view.getBtnRemoveMaterial().setVisible(true);
			}
			
			@Override
			public void invalidSelectionTriggered() {
				view.getBtnRemoveMaterial().setVisible(false);
			}
		});
		
		view.getCmbCellEditor().addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				int editIndex = view.getTblMaterials().getSelectedRow();
				OrderRow orderRow = order.getMaterials().get(editIndex);

				Long materialId = (Long) ((ComboBoxCellEditor) e.getSource()).getCellEditorValue();

				if(availableMaterials.stream().anyMatch(material -> material.getId()==materialId))
					orderRow.setMaterial(availableMaterials.stream()
							.filter(m->m.getId()==materialId)
							.collect(Collectors.toList())
							.get(0));
			}

			@Override
			public void editingCanceled(ChangeEvent e) {

			}
		});

		view.getSpnCellEditor().addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				int editIndex = view.getTblMaterials().getSelectedRow();
				OrderRow orderRow = order.getMaterials().get(editIndex);
				Integer quantity = (Integer) ((SpinnerCellEditor) e.getSource()).getCellEditorValue();
				orderRow.setQuantity(quantity.intValue());
			}

			@Override
			public void editingCanceled(ChangeEvent e) {

			}
		});
		
		new AbstractJButtonActionListener(view.getBtnAddMaterial()) {
			
			@Override
			public void actionTriggered() {
				if(availableMaterials.size()==0) {
					MessageBox.errorBox("No materials available!", "Error");
					return;
				}
				
				order.getMaterials().add(new OrderRow(order, availableMaterials.get(0), 0));
				
				view.getTblMaterialsModel().fireTableDataChanged();
			}
		};
		
		new AbstractJButtonActionListener(view.getBtnRemoveMaterial()) {
			
			@Override
			public void actionTriggered() {
				int index = view.getTblMaterials().getSelectedRow();
				
				if(index==-1) {
					MessageBox.errorBox("No material selected", "Error");
					return;
				}
				
				OrderRow orderRow = order.getMaterials().get(index);
				if(MessageBox.questionBox("Are you sure to delete material " + orderRow.getMaterial().getId() + "?", "Confirm")==0)
					order.getMaterials().remove(index);
				
				view.getTblMaterialsModel().fireTableDataChanged();
			}
		};
	}
}

