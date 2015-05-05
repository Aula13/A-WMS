package org.wms.config;

import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.security.SecurityManager;
import it.rmautomazioni.security.User;
import it.rmautomazioni.security.UserTimeoutChecker;

public final class SecurityConfig {
	
	private static SecurityManager SECURITY_MANAGER;
	
	public static SecurityManager initializeSecurity(int logoutMin) {
		SECURITY_MANAGER = new SecurityManager(logoutMin);
		
		SECURITY_MANAGER.addUser(new User("OPERATOR", "OPERATOR", SecurityLevel.SUPERVISOR));
		SECURITY_MANAGER.addUser(new User("SUPERVISOR", "SUPERVISOR", SecurityLevel.SUPERVISOR));
		SECURITY_MANAGER.addUser(new User("ADMIN", "bonfanti", SecurityLevel.ADMIN));
		
		UserTimeoutChecker timeoutChecker = new UserTimeoutChecker(SECURITY_MANAGER);
		timeoutChecker.start();
		
		return SECURITY_MANAGER;
	}
	
	public static SecurityManager getSecurityManager() {
		return SECURITY_MANAGER;
	}
}
