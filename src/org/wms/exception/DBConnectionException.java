package org.wms.exception;

public class DBConnectionException extends Exception {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DBConnectionException(){
		super("Database connection error.");
	}

	public DBConnectionException(String message){
		     super(message);
		  }
}
