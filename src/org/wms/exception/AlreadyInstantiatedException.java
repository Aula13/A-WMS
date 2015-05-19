package org.wms.exception;

/**
 * To be thrown in case another instance of the application is runing
 * 
 * @author Daniele Ciriello, Pessina Stefano
 *
 */
@SuppressWarnings("serial")
public class AlreadyInstantiatedException extends Exception {
	
	/**
	 * Constructor
	 */
	public AlreadyInstantiatedException(){
		super("Another instance of the application is running.");
	}
	

	/**
	 * Constructor
	 * 
	 * @param e an Exception
	 */
	public AlreadyInstantiatedException(Exception e){
		super(e.getMessage());
	}

	/**
	 * Constructor
	 * @param message custom message to be shown
	 */
	public AlreadyInstantiatedException(String message){
		     super(message);
		  }
}
