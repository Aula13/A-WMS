package org.wms.controller.common;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

public abstract class AbstractTableSelectionListener implements MouseListener {

	private boolean isJTable(Component eventComponent) {
		if(eventComponent instanceof JTable)
			return true;
		else
			return false;
	}
	
	private JTable getTable(Component eventComponent) {
		return (JTable) eventComponent;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!isJTable(e.getComponent()))
			return;
		JTable table = getTable(e.getComponent());
		
		boolean valid = table.getSelectedRow()!=-1;
		int rowIndex = table.getSelectedRow();
		boolean requireMenu = e.getButton()==MouseEvent.BUTTON2;
		
		if(valid)
			validSelectionTrigger(e.getClickCount()==2,rowIndex, requireMenu);
		else
			invalidSelectionTriggered();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	public abstract void validSelectionTrigger(boolean doubleClick, int rowIndex, boolean requireMenu);
	
	public abstract void invalidSelectionTriggered();
	
}
