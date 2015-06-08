package org.wms.controller.batch;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;

import org.wms.config.Utils;
import org.wms.model.batch.Batch;
import org.wms.model.batch.Batches;
import org.wms.view.batch.BatchView;

/**
 * Controller for order edit view
 * 
 * manage all the view actions and connections between OrderEditView and Order model
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class BatchViewController {

	/**
	 * Reference to the order edit view
	 */
	protected BatchView view;
	
	/**
	 * Reference to the order model
	 */
	protected Batch batch;
	
	/**
	 * Reference to the orders model
	 */
	protected Batches batchesModel;

	public BatchViewController(BatchView view, Batch batch,
			Batches batchesModel) {
		super();
		this.view = view;
		this.batch = batch;
		this.batchesModel = batchesModel;
		
		new AbstractJButtonActionListener(view.getConfirmButton()) {
			
			@Override
			public void actionTriggered() {
				btnConfirmButtonAction();			
			}
		};
		
		new AbstractJButtonActionListener(view.getCancelButton()) {
			
			@Override
			public void actionTriggered() {
				btnCancelButtonAction();
			}
		};
	}	
	
	/**
	 * Confirm
	 * dispose batch view
	 */
	protected void btnConfirmButtonAction() {
		view.setVisible(false);
	}
	
	/**
	 * Cancel
	 * dispose batch view
	 */
	protected void btnCancelButtonAction() {
		view.setVisible(false);
	}
}
