package org.wms.exception;

/**
 * To be thrown in case it's impossible to load the configuration's file
 * 
 * @author Daniele Ciriello, Pessina Stefano
 *
 */
@SuppressWarnings("serial")
public class ConfigFileLoadingException extends Exception {
	
	/**
	 * Constructor
	 */
	public ConfigFileLoadingException(){
		super("Error during application configuration file initialization.");
	}

	/**
	 * Constructor
	 * 
	 * @param message custom message to be shown
	 */
	public ConfigFileLoadingException(String message){
		     super(message);
		  }
}
