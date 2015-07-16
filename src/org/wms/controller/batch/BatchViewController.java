package org.wms.controller.batch;

import it.rmautomazioni.controller.listener.AbstractJButtonActionListener;

import org.wms.config.Utils;
import org.wms.model.batch.Batch;
import org.wms.model.batch.Batches;
import org.wms.view.batch.BatchView;

/**
 * Controller for batch details view
 * 
 * manage all the view actions and connections between BatchView and Batch model
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class BatchViewController {

	/**
	 * Reference to the batch view
	 */
	protected BatchView view;
	
	/**
	 * Reference to the batch model
	 */
	protected Batch batch;
	
	/**
	 * Reference to the batches model
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
