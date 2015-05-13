package org.wms.exception;

/**
 * To be thrown in case another instance of the application is runing
 * 
 * @author Daniele Ciriello, Pessina Stefano
 *
 */
public class AlreadyInstantiatedException extends Exception {
	
	public AlreadyInstantiatedException(){
		super("Another instance of the application is running.");
	}

	public AlreadyInstantiatedException(String message){
		     super(message);
		  }
}
