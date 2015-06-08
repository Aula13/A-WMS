package org.wms.view.batch;

import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.wms.config.IconTypeAWMS;
import org.wms.controller.batch.BatchesTableModel;
import org.wms.controller.order.OrdersTableModel;
import org.wms.model.batch.Batches;
import org.wms.model.common.ListType;

/**
 * Batches view show a table with the batch list
 * For each batch the most important information are showed
 * 
 * Moreover a command bar it's placed for refresh/print/delete an order
 * 
 * This view observe the ordersModel for updates
 * 
 * This view can be configured for input or output order type
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class BatchesView extends JPanel implements Observer {

	protected Batches batchesModel;
	
	protected JTable batchesTable;
	
	protected BatchesTableModel tableModel;
	
	protected JButton btnRefreshBatches;
	
	protected JButton btnMarkBatchAsAllocated;

	protected JButton btnPrintBatch;
	
	protected JButton btnMarkBatchAsComplete;
	
	/**
	 * Constructor
	 * 
	 * @param batchesModel reference to the batches model
	 * @param inputType true if the view is for input orders
	 */
	public BatchesView(Batches batchesModel) {
		this.batchesModel = batchesModel;
		initComponent();
		initUI();
		batchesModel.addObserver(this);
		updateValue();
	}
	
	/**
	 * Init graphic components
	 * to be placed in the gui
	 */
	protected void initComponent() {
		tableModel = new BatchesTableModel(batchesModel);
		batchesTable = FactoryReferences.appStyle.getTableClass(tableModel);

		btnRefreshBatches = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.REFRESH.name());
		btnMarkBatchAsAllocated = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.START.name());
		btnPrintBatch = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.PRINT.name());
		btnMarkBatchAsComplete = FactoryReferences.buttons.getButtonWithIcon(IconTypeAWMS.COMPLETED.name());
		
	}
	
	/**
	 * Place each component in the gui
	 */
	protected void initUI() {
		setName("BATCHES");
		
		setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane(batchesTable);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);
		
		List<JButton> buttons = new ArrayList<>();
		
		buttons.add(btnRefreshBatches);
		buttons.add(btnMarkBatchAsAllocated);
		buttons.add(btnPrintBatch);
		buttons.add(btnMarkBatchAsComplete);
		
		JPanel operationPanel = FactoryReferences.panels.getVerticalNavigationBarPanel(buttons);
		add(operationPanel, BorderLayout.EAST);		
		
	}
	
	/**
	 * Update the table
	 * if a row is selected, show mark as allocated/completed and print batch buttons
	 * otherwise they hide them
	 */
	protected void updateValue() {
		tableModel.fireTableDataChanged();
		if(batchesTable.getSelectedRow()!=-1) {
			btnMarkBatchAsAllocated.setVisible(true);
			btnPrintBatch.setVisible(true);
			btnMarkBatchAsComplete.setVisible(true);
		} else {
			btnMarkBatchAsAllocated.setVisible(false);
			btnPrintBatch.setVisible(false);
			btnMarkBatchAsComplete.setVisible(false);
		}
	}
	
	/** 
	 * Call update view
	 * 
	 * @see org.wms.view.batch.BatchesView#updateValue()
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		updateValue();
	}
	
	/**
	 * @return get reference to refresh batches button
	 */
	public JButton getBtnRefreshBatches() {
		return btnRefreshBatches;
	}
	
	/**
	 * @return get reference to mark batch as allocated button
	 */
	public JButton getBtnMarkBatchAsAllocated() {
		return btnMarkBatchAsAllocated;
	}
	
	/**
	 * @return get reference to print batch button
	 */
	public JButton getBtnPrintBatch() {
		return btnPrintBatch;
	}
	
	/**
	 * @return get reference to mark batch as complete button
	 */
	public JButton getBtnMarkBatchAsComplete() {
		return btnMarkBatchAsComplete;
	}
	
	/**
	 * @return get reference to batches table
	 */
	public JTable getBatchesTable() {
		return batchesTable;
	}
}
