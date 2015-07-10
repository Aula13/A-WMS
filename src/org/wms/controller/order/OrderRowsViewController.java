package org.wms.controller.order;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import org.wms.config.Utils;
import org.wms.controller.common.AbstractTableSelectionListener;
import org.wms.model.material.Material;
import org.wms.model.order.Order;
import org.wms.model.order.OrderRow;
import org.wms.view.common.SpinnerCellEditor;
import org.wms.view.order.OrderRowsView;

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
	protected OrderRowsView view;

	/**
	 * reference to the order
	 */
	protected Order order;

	/**
	 * List of available materials
	 * to check valid materials
	 * and force user selection
	 */
	protected List<Material> availableMaterials;

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
				tblOrderRowsValidSelectionAction(rowIndex);
			}
			
			@Override
			public void invalidSelectionTriggered() {
				tblOrderRowsInvalidSelectionAction();
			}
		});
		
		view.getCmbMaterialCodeCellEditor().addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				tblOrderRowsComboEditStopAction(e);
			}

			@Override
			public void editingCanceled(ChangeEvent e) {

			}
		});

		view.getSpnOrderRowQuantityCellEditor().addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				tblOrderRowsSpinnerEditStopAction(e);
			}

			@Override
			public void editingCanceled(ChangeEvent e) {

			}
		});
		
		new AbstractJButtonActionListener(view.getBtnAddOrderRow()) {
			
			@Override
			public void actionTriggered() {
				btnAddOrderRowAction();
			}
		};
		
		new AbstractJButtonActionListener(view.getBtnRemoveOrderRow()) {
			
			@Override
			public void actionTriggered() {
				btnRemoveOrderRowAction();
			}
		};
	}
	
	/**
	 * Show remove material button
	 * if the order row selected is editable
	 */
	protected void tblOrderRowsValidSelectionAction(int indexRow) {
		OrderRow orderRow = order.getUnmodificableMaterials().get(indexRow);
		if(orderRow.isEditable())
			view.getBtnRemoveOrderRow().setVisible(true);
	}
	
	/**
	 * Hide remove material button
	 */
	protected void tblOrderRowsInvalidSelectionAction() {
		view.getBtnRemoveOrderRow().setVisible(false);
	}
	
	/**
	 * Update material reference after user edit
	 *  
	 * @param e change event from combobox cell editor
	 * @return true=updated - false=material not found
	 */
	protected boolean tblOrderRowsComboEditStopAction(ChangeEvent e) {
		int editIndex = view.getTblOrderRows().getSelectedRow();
		OrderRow orderRow = order.getUnmodificableMaterials().get(editIndex);

		Long materialId = (Long) ((ComboBoxCellEditor) e.getSource()).getCellEditorValue();

		if(availableMaterials.stream().anyMatch(material -> material.getCode()==materialId)) {
			orderRow.setMaterial(availableMaterials.stream()
					.filter(m->m.getCode()==materialId)
					.collect(Collectors.toList())
					.get(0));
			return true;
		}
		return false;
	}
	
	/**
	 * Update material quantity after cell editor
	 * 
	 * @param e change event from spinner cell editor
	 */
	protected void tblOrderRowsSpinnerEditStopAction(ChangeEvent e) {
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
	protected boolean btnAddOrderRowAction() {
		if(availableMaterials.size()==0) {
			Utils.msg.errorBox("No materials available!", "Error");
			return false;
		}
		
		if(!order.isEditable()) {
			Utils.msg.errorBox("This order is not editable!", "Error");
			return false;
		}
		
		if(!order.addMaterial(new OrderRow(order, availableMaterials.get(0), 1))) {
			Utils.msg.errorBox("The order row is not valid!", "Error");
			return false;
		}
		
		view.getTblOrderRowsModel().fireTableDataChanged();
		return true;
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
	protected boolean btnRemoveOrderRowAction() {
		int index = view.getTblOrderRows().getSelectedRow();
		
		if(index==-1) {
			Utils.msg.errorBox("No material selected", "Error");
			return false;
		}
		
		if(!order.isEditable()) {
			Utils.msg.errorBox("This order is not editable!", "Error");
			return false;
		}
		
		OrderRow orderRow = order.getUnmodificableMaterials().get(index);
		
		if(Utils.msg.questionBox("Are you sure to delete material " + orderRow.getMaterial().getCode() + "?", "Confirm")!=0)
			return false;
	
		order.removeMaterial(orderRow);
		
		view.getTblOrderRowsModel().fireTableDataChanged();
		
		return true;
	}
}

