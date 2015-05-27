package org.wms.controller.common;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

/**
 * 
 * Listener that provided advanced method for the JTable
 * Implement this listener to have distinct method
 * that signalize valid or invalid selection on the JTable
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public abstract class AbstractTableSelectionListener implements MouseListener {

	/**
	 * Check if a component is a JTable
	 * 
	 * @param eventComponent
	 * @return true=is a JTable
	 */
	private boolean isJTable(Component eventComponent) {
		if(eventComponent instanceof JTable)
			return true;
		else
			return false;
	}
	
	/**
	 * Cast a component to an JTable
	 * Please use isJTable to check if this cast is possible
	 * 
	 * @param eventComponent
	 * @return JTable
	 */
	private JTable getTable(Component eventComponent) {
		return (JTable) eventComponent;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!isJTable(e.getComponent())) {
			invalidSelectionTriggered();
			return;
		}
		JTable table = getTable(e.getComponent());
		
		boolean valid = table.getSelectedRow()!=-1;
		int rowIndex = table.getSelectedRow();
		boolean requireMenu = e.isPopupTrigger();
		
		if(valid)
			validSelectionTrigger(e.getClickCount()==2,rowIndex, requireMenu);
		else
			invalidSelectionTriggered();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	/**
	 * 
	 * This method is called if the selection is valid
	 * 
	 * @param doubleClick if the user press mouse twice
	 * @param rowIndex the selected row
	 * @param requireMenu if the user press right button on the selection
	 */
	public abstract void validSelectionTrigger(boolean doubleClick, int rowIndex, boolean requireMenu);
	
	/**
	 * This method is called if the selection is not valid
	 * 
	 */
	public abstract void invalidSelectionTriggered();
	
}
