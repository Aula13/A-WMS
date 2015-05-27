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
import org.wms.view.orderedit.OrderRowsView;
import org.wms.view.orderedit.SpinnerCellEditor;

/**
 * Controller for material list view
 * 
 * manage all the view actions and connections between MaterialListView and Order model
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrderRowsViewController {

	/**
	 * reference to the material list view
	 */
	private OrderRowsView view;

	/**
	 * reference to the order
	 */
	private Order order;

	/**
	 * List of available materials
	 * to check valid materials
	 * and force user selection
	 */
	private List<Material> availableMaterials;

	/**
	 * Constructor
	 * 
	 * @param view reference to the material list view
	 * @param order reference to the order model
	 * @param availableMaterials list of available material (valid selection)
	 */
	public OrderRowsViewController(OrderRowsView view, Order order, List<Material> availableMaterials) {
		super();
		this.view = view;
		this.order = order;
		this.availableMaterials = availableMaterials;
		
		view.getBtnRemoveOrderRow().setVisible(false);
		
		view.getTblOrderRows().addMouseListener(new AbstractTableSelectionListener() {
			
			@Override
			public void validSelectionTrigger(boolean doubleClick, int rowIndex, boolean requireMenu) {
				tblMaterialsValidSelectionAction();
			}
			
			@Override
			public void invalidSelectionTriggered() {
				tblMaterialsInvalidSelectionAction();
			}
		});
		
		view.getCmbMaterialCodeCellEditor().addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				tblMaterialsComboEditStopAction(e);
			}

			@Override
			public void editingCanceled(ChangeEvent e) {

			}
		});

		view.getSpnOrderRowQuantityCellEditor().addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				tblMaterialsSpinnerEditStopAction(e);
			}

			@Override
			public void editingCanceled(ChangeEvent e) {

			}
		});
		
		new AbstractJButtonActionListener(view.getBtnAddOrderRow()) {
			
			@Override
			public void actionTriggered() {
				btnAddMaterialAction();
			}
		};
		
		new AbstractJButtonActionListener(view.getBtnRemoveOrderRow()) {
			
			@Override
			public void actionTriggered() {
				btnRemoveMaterialAction();
			}
		};
	}
	
	/**
	 * Show remove material button
	 * if the order row selected is editable
	 */
	private void tblMaterialsValidSelectionAction() {
		//TODO: show this button only if the order row is editable
		view.getBtnRemoveOrderRow().setVisible(true);
	}
	
	/**
	 * Hide remove material button
	 */
	private void tblMaterialsInvalidSelectionAction() {
		view.getBtnRemoveOrderRow().setVisible(false);
	}
	
	/**
	 * Update material reference after user edit
	 *  
	 * @param e change event from combobox cell editor
	 */
	private void tblMaterialsComboEditStopAction(ChangeEvent e) {
		int editIndex = view.getTblOrderRows().getSelectedRow();
		OrderRow orderRow = order.getUnmodificableMaterials().get(editIndex);

		Long materialId = (Long) ((ComboBoxCellEditor) e.getSource()).getCellEditorValue();

		if(availableMaterials.stream().anyMatch(material -> material.getCode()==materialId))
			orderRow.setMaterial(availableMaterials.stream()
					.filter(m->m.getCode()==materialId)
					.collect(Collectors.toList())
					.get(0));
	}
	
	/**
	 * Update material quantity after cell editor
	 * 
	 * @param e change event from spinner cell editor
	 */
	private void tblMaterialsSpinnerEditStopAction(ChangeEvent e) {
		int editIndex = view.getTblOrderRows().getSelectedRow();
		OrderRow orderRow = order.getUnmodificableMaterials().get(editIndex);
		Integer quantity = (Integer) ((SpinnerCellEditor) e.getSource()).getCellEditorValue();
		orderRow.setQuantity(quantity.intValue());
	}
	
	/**
	 * Show error if # of available material is 0
	 * 
	 * Show error if order is not editable
	 * 
	 * If no error, add new material to order
	 * 
	 * Finally fire update order materials table
	 * 
	 */
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
		
		view.getTblOrderRowsModel().fireTableDataChanged();
	}

	/**
	 * Show error if no material is selected
	 * 
	 * Show error if order is not editable
	 * 
	 * Require confirmation to the user
	 * 
	 * Remove the material selected
	 * 
	 * Finally fire update order materials table
	 * 
	 */	
	private void btnRemoveMaterialAction() {
		int index = view.getTblOrderRows().getSelectedRow();
		
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
		
		view.getTblOrderRowsModel().fireTableDataChanged();
	}
}

