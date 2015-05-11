
package org.wms.exception;

public class ConfigFileLoadingException extends Exception {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConfigFileLoadingException(){
		super("Error during application configuration file initialization.");
	}

	public ConfigFileLoadingException(String message){
		     super(message);
		  }
}
