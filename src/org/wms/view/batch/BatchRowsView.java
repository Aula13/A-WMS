package org.wms.view.batch;

import it.rmautomazioni.view.factories.FactoryReferences;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.wms.controller.batch.BatchRowsTableModel;
import org.wms.model.batch.Batch;

/**
 * Batch rows view contains a table for batch rows
 * 
 * No editing feature is provided for the batch row
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class BatchRowsView extends JPanel {
	
	private Batch batch;
	
	private JTable tblOrderRows;
	
	private BatchRowsTableModel tblBatchRowsModel;
	
	public BatchRowsView(Batch batch) {
		super();
		this.batch = batch;
		initComponent();
		initUI();
	}	
	
	/**
	 * Init components to be placed in the gui
	 */
	private void initComponent() {
		tblBatchRowsModel = new BatchRowsTableModel(batch);
		tblOrderRows = FactoryReferences.appStyle.getTableClass(tblBatchRowsModel);
	}
	
	/**
	 * Place each component in the gui
	 */
	private void initUI() {
		setLayout(new BorderLayout());		
		
		JScrollPane scrollPane = new JScrollPane(tblOrderRows);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * @return reference to batch rows table
	 */
	public JTable getTblOrderRows() {
		return tblOrderRows;
	}
	
	/**
	 * @return reference to batch rows table model
	 */
	public BatchRowsTableModel getTblBatchRowsModel() {
		return tblBatchRowsModel;
	}
}
