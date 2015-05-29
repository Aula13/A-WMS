package org.wms.view.common;

import javax.swing.JOptionPane;

public class MessageBox {

	/**
	 * Visualizza finestra pop up con un messaggio (informazione)
	 * @param message: messaggio
	 * @param title: titolo
	 */
	public void infoBox(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
	
	/**
	 * Visualizza finestra pop up con un messaggio (avviso)
	 * @param message: messaggio
	 * @param title: titolo
	 */
	public void warningBox(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }
	
	/**
	 * Visualizza finestra pop up con un messaggio (errore)
	 * @param message: messaggio
	 * @param title: titolo
	 */
	public void errorBox(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
	
	/**
	 * Visualizza finestra pop up con un messaggio (domanda)
	 * @param message: messaggio
	 * @param title: titolo
	 * 
	 * @return selezione
	 */
	public int questionBox(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
    }
	
	public String inputBox(String message) {
		return JOptionPane.showInputDialog(null , message);
	}
	
}
