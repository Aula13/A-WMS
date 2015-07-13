package org.wms.view.common;

import it.rmautomazioni.view.factories.RMColour;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.wms.model.common.Status;

public class TableStatusHighlighter {

	JXTable table;
	
	int columnToCheck;

	public TableStatusHighlighter(JTable table, int columnToCheck) {
		super();
		if(table instanceof JXTable) {
			this.table = (JXTable) table;
			this.columnToCheck = columnToCheck;
			configureTableHighlights();
		}
	}
	
	private void configureTableHighlights() {
		// Visualizzo la riga in rosso se la bobina è in errore
		final HighlightPredicate waitingPredicate = new HighlightPredicate() {
			@Override 
			public boolean isHighlighted(Component renderer, ComponentAdapter adapter) {
				Status value = Status.valueOf((String) adapter.getValue(columnToCheck));
				return value==Status.WAITING;
			}
		};
		// Visualizzo la riga in arancio se la bobina è in raffreddamento
		final HighlightPredicate assignedPredicate = new HighlightPredicate() {
			@Override 
			public boolean isHighlighted(Component renderer, ComponentAdapter adapter) {
				Status value = Status.valueOf((String) adapter.getValue(columnToCheck));
				return value==Status.ASSIGNED;
			}
		};
		// Visualizzo la riga in grigio chiaro se la bobina è stata eliminata
		final HighlightPredicate completedPredicate = new HighlightPredicate() {
			@Override 
			public boolean isHighlighted(Component renderer, ComponentAdapter adapter) {
				Status value = Status.valueOf((String) adapter.getValue(columnToCheck));
				return value==Status.COMPLETED;
			}
		};		
		
		ColorHighlighter waitingHL = new ColorHighlighter(waitingPredicate, RMColour.RM_DARK_RED, Color.WHITE);
		ColorHighlighter assignedHL = new ColorHighlighter(assignedPredicate, Color.YELLOW, Color.BLACK);
		ColorHighlighter completedHL = new ColorHighlighter(completedPredicate, RMColour.RM_GREEN, Color.WHITE);
		
		
		table.addHighlighter(waitingHL);
		table.addHighlighter(assignedHL);
		table.addHighlighter(completedHL);
	}
	
}
