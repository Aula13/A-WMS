package org.wms.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Load hibernate configurations
 * 
 * Keep reference to the session factory
 * 
 * @author stefano
 *
 */
public class HibernateUtil {
	
	/**
	 * Session factory reference
	 */
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	/**
	 * Service registry reference
	 */
	private static ServiceRegistry serviceRegistry;
	
	/**
	 * Session factory initialization
	 * change configure file 
	 * 
	 * @return session factory
	 */
	private static SessionFactory buildSessionFactory() {
		try {
			// load from different directory
			Configuration configuration = new Configuration();
		    configuration.configure("/config/hibernate.cfg.xml");
		    serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
		            configuration.getProperties()).build();
		    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		    return sessionFactory;
		    
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
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
	 * Close current session
	 */
	public static void closeSession() {
		sessionFactory.close();
	}
	
}
