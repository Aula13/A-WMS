package org.wms.exception;

public class AlreadyInstantiatedException extends Exception {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AlreadyInstantiatedException(){
		super("Another instance of the application is running.");
	}

	public AlreadyInstantiatedException(String message){
		     super(message);
		  }
}
