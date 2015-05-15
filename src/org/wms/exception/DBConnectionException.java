package org.wms.exception;

/**
 * To be thrown in case it's impossible to connect at the database
 * 
 * @author Daniele Ciriello, Pessina Stefano
 *
 */
@SuppressWarnings("serial")
public class DBConnectionException extends Exception {

	/**
	 * Constructor
	 */
	public DBConnectionException(){
		super("Database connection error.");
	}

	/**
	 * Constructor
	 * 
	 * @param message custom message to be shown
	 */
	public DBConnectionException(String message){
		     super(message);
	}
}
