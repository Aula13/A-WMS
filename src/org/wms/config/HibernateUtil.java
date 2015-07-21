package org.wms.config;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.wms.exception.ConfigFileLoadingException;

/**
 * Load hibernate configurations
 * 
 * Keep reference to the session factory
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class HibernateUtil {
	
	/**
	 * Session factory reference
	 */
	private static SessionFactory sessionFactory;
	
	/**
	 * Service registry reference
	 */
	private static ServiceRegistry serviceRegistry;
	
	/**
	 * Unique session reference for
	 * support lazy loading
	 */
	private static Session session;
	
	/**
	 * Session factory initialization
	 * change configure file 
	 * 
	 * @return session factory
	 * @throws ConfigFileLoadingException 
	 */
	public static void buildSessionFactory(String cfgFilePath) throws ConfigFileLoadingException {
		try {
			// load from different directory
			
			Configuration configuration = new Configuration();
			File cfgFile = new File(cfgFilePath);
		    configuration.configure(cfgFile);
		    serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
		            configuration.getProperties()).build();
		    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		    
		    session = sessionFactory.openSession();
		    
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ConfigFileLoadingException(ex.getMessage());
		}
	}
	
	/**
	 * Get a session to database
	 * 
	 * @return database session
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Get current session
	 * 
	 * @return current session
	 */
	public static Session getSession() {
		if(!session.isOpen())
			session = sessionFactory.openSession();
		return session;
	}
	
	/**
	 * Close current session
	 */
	public static void closeSession() {
		if(session!=null)
			if(session.isOpen())
				session.close();
	}
	
	public static void closeSessionFactory() {
		sessionFactory.close();
	}
	
}
