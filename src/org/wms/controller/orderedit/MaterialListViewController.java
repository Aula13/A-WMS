package org.wms.controller.orderedit;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
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

		view.getCmbCellEditor().addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				int editIndex = view.getTblMaterials().getSelectedRow();
				OrderRow orderRow = order.getMaterials().get(editIndex);

				Long materialId = (Long) ((ComboBoxCellEditor) e.getSource()).getCellEditorValue();

				if(availableMaterials.stream().anyMatch(material -> material.getCode()==materialId))
					orderRow.setMaterial(availableMaterials.stream()
							.filter(m->m.getCode()==materialId)
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
	}
}

