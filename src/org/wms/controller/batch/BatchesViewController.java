package org.wms.controller.batch;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;

import java.util.HashMap;
import java.util.List;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

import org.wms.config.Utils;
import org.wms.controller.common.AbstractTableSelectionListener;
import org.wms.model.batch.Batch;
import org.wms.model.batch.BatchRow;
import org.wms.model.batch.Batches;
import org.wms.view.batch.BatchView;
import org.wms.view.batch.BatchesView;

/**
 * Controller for batches view
 * 
 * manage all the view actions and connections between BatchesView and BatchesModel
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
/**
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class BatchesViewController {

	/**
	 * Reference to the batch view
	 */
	protected BatchesView view;
	
	/**
	 * Reference to the batchs model
	 */
	protected Batches batchesModel;
	
	/**
	 * Batch details view JDialog reference
	 */
	protected BatchView batchDialog;
	
	/**
	 * Constructor
	 * add action to view components
	 * 
	 * @param view reference to the batchesView
	 * @param batchesModel reference to the batches model
	 */
	public BatchesViewController(BatchesView view, Batches batchesModel) {
		super();
		this.view = view;
		this.batchesModel = batchesModel;
		
		view.getBtnMarkBatchAsAllocated().setVisible(false);
		view.getBtnPrintBatch().setVisible(false);
		view.getBtnMarkBatchAsComplete().setVisible(false);
		
		new AbstractJButtonActionListener(view.getBtnRefreshBatches()) {
			
			@Override
			public void actionTriggered() {
				btnRefreshAction();
			}
		};
		
		new AbstractJButtonActionListener(view.getBtnMarkBatchAsAllocated()) {
			
			@Override
			public void actionTriggered() {
				btnAllocateAction();
			}
		};
		
		new AbstractJButtonActionListener(view.getBtnPrintBatch()) {
			
			@Override
			public void actionTriggered() {
				btnPrintAction();
			}
		};
		
		new AbstractJButtonActionListener(view.getBtnMarkBatchAsComplete()) {
			
			@Override
			public void actionTriggered() {
				btnCompleteAction();
			}
		};
		
		view.getBatchesTable().addMouseListener(new AbstractTableSelectionListener() {
			
			@Override
			public void validSelectionTrigger(boolean doubleClick, int rowIndex, boolean requireMenu) {
				tblBatchesValidSelectionAction(doubleClick, rowIndex);
			}
			
			@Override
			public void invalidSelectionTriggered() {
				tblBatchesInvalidSelectionAction();
			}
		});
		
	}
	
	

	/**
	 * Open batch details view
	 * 
	 * @param batch to show
	 */
	protected void launchBatchView(Batch batch){
		batchDialog = new BatchView(batch);
		new BatchViewController(batchDialog, batch, batchesModel);
		batchDialog.setVisible(true);
	}
	

	/**
	 * Refresh button update batch computation
	 * 
	 */
	protected void btnRefreshAction() {
		batchesModel.update(batchesModel, null);
	}
	

	/**
	 * Mark a batch and it's order row associated
	 * as allocated or assigned.
	 * This mean the operator is started with
	 * pickup or place of the list
	 * 
	 * @return
	 */
	protected boolean btnAllocateAction() {
		if(view.getBatchesTable().getSelectedRow()==-1) {
			Utils.msg.errorBox("No order selected", "Error");
			return false;
		}
		
		int rowIndex = view.getBatchesTable().getSelectedRow();
		
		Batch batch = batchesModel.getUnmodificableBatchList().get(rowIndex);
		
		if(!batchesModel.setBatchAsAllocate(batch)) {
			Utils.msg.errorBox("Error during batch allocation", "Error");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Print a report of pickup/place fork lift list
	 * 
	 * @return
	 */
	protected boolean btnPrintAction() {
		if(view.getBatchesTable().getSelectedRow()==-1) {
			Utils.msg.errorBox("No order selected", "Error");
			return false;
		}
		
		int rowIndex = view.getBatchesTable().getSelectedRow();
		
		Batch batch = batchesModel.getUnmodificableBatchList().get(rowIndex);
		
		JasperPrint jasperPrint = null;
        
		String[] columnNames = {"BATCHID", "ORDID", "ORDROWID", 
				"MAT", "QTA", "WHPICK"};
		List<BatchRow> rows = batch.getRows();
        String[][] data = new String[rows.size()][6];
        
        int i=0;
        for (BatchRow row : rows) {
			data[i][0]=String.valueOf(batch.getId());
			data[i][1]=String.valueOf(row.getReferredOrderRow().getOrder().getId());
			data[i][2]=String.valueOf(row.getReferredOrderRow().getId());
			data[i][3]=String.valueOf(row.getJobWarehouseCell().getMaterial().getCode());
			data[i][4]=String.valueOf(row.getQuantity());
			data[i][5]=row.getJobWarehouseCell().getPublicId();
        	i++;
		}
        
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        
        try {
            jasperPrint = JasperFillManager.fillReport("report/batchtemplate.jasper", new HashMap(),
                    new JRTableModelDataSource(tableModel));
            JasperViewer jasperViewer = new JasperViewer(jasperPrint,false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
        	Utils.msg.errorBox("Error during start jasper preview tool", "Error");
        	return false;
        }
		
		return true;
	}
	
	/**
	 * Mark an allocated batch as completed
	 */
	protected boolean btnCompleteAction() {
		if(view.getBatchesTable().getSelectedRow()==-1) {
			Utils.msg.errorBox("No order selected", "Error");
			return false;
		}
		
		int rowIndex = view.getBatchesTable().getSelectedRow();
		
		Batch batch = batchesModel.getUnmodificableBatchList().get(rowIndex);
		
		if(!batchesModel.setBatchAsCompleted(batch)) {
			Utils.msg.errorBox("Error during batch mark as completed", "Error");
			return false;
		}
		
		return true;
	}
	
	/**
	 * When the user selected an batch in table
	 * the mark as allocated/completed and print buttons will be enabled
	 * 
	 * Moreover if the user press twice
	 * the details view will be automatically showed
	 * 
	 * @param doubleClick if the user press mouse button twice
	 * @param rowIndex index of the table selection
	 */
	protected void tblBatchesValidSelectionAction(boolean doubleClick, int rowIndex) {
		view.getBtnMarkBatchAsAllocated().setVisible(true);
		view.getBtnPrintBatch().setVisible(true);
		view.getBtnMarkBatchAsComplete().setVisible(true);
		
		if(doubleClick) {
			Batch batch = batchesModel.getUnmodificableBatchList().get(rowIndex);
			launchBatchView(batch); 
		}
	}
	
	/**
	 * Hide mark as allocated/completed and print buttons
	 */
	protected void tblBatchesInvalidSelectionAction() {
		view.getBtnMarkBatchAsAllocated().setVisible(false);
		view.getBtnPrintBatch().setVisible(false);
		view.getBtnMarkBatchAsComplete().setVisible(false);
	}
}
