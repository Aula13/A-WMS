package org.wms.view.common;

import it.rmautomazioni.view.factories.RMColour;

import java.awt.Color;
import java.awt.Component;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.wms.model.common.Status;

/**
 * This provide table rows highligheter
 * based on status cell
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class TableStatusHighlighter {

	JXTable table;
	
	int columnToCheck;

	/**
	 * Provide table and the column index of the status column
	 * to check
	 * 
	 * The table should be instance of JXTable
	 * The column data should contain string value of enum Status
	 * 
	 * @see org.wms.model.common.Status
	 * 
	 * @param table
	 * @param columnToCheck
	 * 
	 * 
	 */
	public TableStatusHighlighter(JXTable table, int columnToCheck) {
		super();
		this.table = table;
		this.columnToCheck = columnToCheck;
		configureTableHighlights();
	}
	
	/**
	 * Add table highlighters
	 */
	protected void configureTableHighlights() {
		// Row referred to element in WAITING status
		final HighlightPredicate waitingPredicate = new HighlightPredicate() {
			@Override 
			public boolean isHighlighted(Component renderer, ComponentAdapter adapter) {
				Status value = Status.valueOf((String) adapter.getValue(columnToCheck));
				return value==Status.WAITING;
			}
		};
		
		// Row referred to element in ASSIGNED status
		final HighlightPredicate assignedPredicate = new HighlightPredicate() {
			@Override 
			public boolean isHighlighted(Component renderer, ComponentAdapter adapter) {
				Status value = Status.valueOf((String) adapter.getValue(columnToCheck));
				return value==Status.ASSIGNED;
			}
		};
		
		// Row referred to element in COMPLETED status
		final HighlightPredicate completedPredicate = new HighlightPredicate() {
			@Override 
			public boolean isHighlighted(Component renderer, ComponentAdapter adapter) {
				Status value = Status.valueOf((String) adapter.getValue(columnToCheck));
				return value==Status.COMPLETED;
			}
		};		
		
		//Setup element backgroung based on the status
		ColorHighlighter waitingHL = new ColorHighlighter(waitingPredicate, RMColour.RM_DARK_RED, Color.WHITE);
		ColorHighlighter assignedHL = new ColorHighlighter(assignedPredicate, Color.YELLOW, Color.BLACK);
		ColorHighlighter completedHL = new ColorHighlighter(completedPredicate, RMColour.RM_GREEN, Color.WHITE);
		
		//Add highlighter to the table
		table.addHighlighter(waitingHL);
		table.addHighlighter(assignedHL);
		table.addHighlighter(completedHL);
	}
	
}
