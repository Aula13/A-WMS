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
				tblMaterialsValidSelectionAction();
			}
			
			@Override
			public void invalidSelectionTriggered() {
				tblMaterialsInvalidSelectionAction();
			}
		});
		
		view.getCmbCellEditor().addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				tblMaterialsComboEditStopAction(e);
			}

			@Override
			public void editingCanceled(ChangeEvent e) {

			}
		});

		view.getSpnCellEditor().addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				tblMaterialsSpinnerEditStopAction(e);
			}

			@Override
			public void editingCanceled(ChangeEvent e) {

			}
		});
		
		new AbstractJButtonActionListener(view.getBtnAddMaterial()) {
			
			@Override
			public void actionTriggered() {
				btnAddMaterialAction();
			}
		};
		
		new AbstractJButtonActionListener(view.getBtnRemoveMaterial()) {
			
			@Override
			public void actionTriggered() {
				btnRemoveMaterialAction();
			}
		};
	}
	
	private void tblMaterialsValidSelectionAction() {
		view.getBtnRemoveMaterial().setVisible(true);
	}
	
	private void tblMaterialsInvalidSelectionAction() {
		view.getBtnRemoveMaterial().setVisible(false);
	}
	
	private void tblMaterialsComboEditStopAction(ChangeEvent e) {
		int editIndex = view.getTblMaterials().getSelectedRow();
		OrderRow orderRow = order.getUnmodificableMaterials().get(editIndex);

		Long materialId = (Long) ((ComboBoxCellEditor) e.getSource()).getCellEditorValue();

		if(availableMaterials.stream().anyMatch(material -> material.getCode()==materialId))
			orderRow.setMaterial(availableMaterials.stream()
					.filter(m->m.getCode()==materialId)
					.collect(Collectors.toList())
					.get(0));
	}
	
	private void tblMaterialsSpinnerEditStopAction(ChangeEvent e) {
		int editIndex = view.getTblMaterials().getSelectedRow();
		OrderRow orderRow = order.getUnmodificableMaterials().get(editIndex);
		Integer quantity = (Integer) ((SpinnerCellEditor) e.getSource()).getCellEditorValue();
		orderRow.setQuantity(quantity.intValue());
	}
	
	private void btnAddMaterialAction() {
		if(availableMaterials.size()==0) {
			MessageBox.errorBox("No materials available!", "Error");
			return;
		}
		
		if(!order.isEditable()) {
			MessageBox.errorBox("This order is not editable!", "Error");
			return;
		}
		
		if(order.addMaterial(new OrderRow(order, availableMaterials.get(0), 0))) {
			MessageBox.errorBox("The order row is not valid!", "Error");
			return;
		}
		
		view.getTblMaterialsModel().fireTableDataChanged();
	}
	
	private void btnRemoveMaterialAction() {
		int index = view.getTblMaterials().getSelectedRow();
		
		if(index==-1) {
			MessageBox.errorBox("No material selected", "Error");
			return;
		}
		
		if(!order.isEditable()) {
			MessageBox.errorBox("This order is not editable!", "Error");
			return;
		}
		
		OrderRow orderRow = order.getUnmodificableMaterials().get(index);
		
		if(MessageBox.questionBox("Are you sure to delete material " + orderRow.getMaterial().getCode() + "?", "Confirm")==0)
			order.removeMaterial(orderRow);
		
		view.getTblMaterialsModel().fireTableDataChanged();
	}
}

