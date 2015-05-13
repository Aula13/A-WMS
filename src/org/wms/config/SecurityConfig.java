package org.wms.config;

import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.security.SecurityManager;
import it.rmautomazioni.security.User;
import it.rmautomazioni.security.UserTimeoutChecker;

/**
 * SecurityConfig is a static class
 * where is stored the reference to the security manager
 * 
 * @author stefano
 *
 */
public final class SecurityConfig {
	
	/**
	 * provide methods for login, logout etc
	 */
	private static SecurityManager SECURITY_MANAGER;
	
	/**
	 * Initialize the security manager
	 * 
	 * @param logoutMin programmatically logout timeout
	 * @return reference to the static manager
	 */
	public static SecurityManager initializeSecurity(int logoutMin) {
		SECURITY_MANAGER = new SecurityManager(logoutMin);
		
		SECURITY_MANAGER.addUser(new User("OPERATOR", "OPERATOR", SecurityLevel.OPERATOR));
		SECURITY_MANAGER.addUser(new User("SUPERVISOR", "SUPERVISOR", SecurityLevel.SUPERVISOR));
		SECURITY_MANAGER.addUser(new User("ADMIN", "ADMIN", SecurityLevel.ADMIN));
		
		UserTimeoutChecker timeoutChecker = new UserTimeoutChecker(SECURITY_MANAGER);
		timeoutChecker.start();
		
		return SECURITY_MANAGER;
	}
	
	/**
	 * @return the reference to the security manager
	 */
	public static SecurityManager getSecurityManager() {
		return SECURITY_MANAGER;
	}
}
