package org.wms.config;

import it.rmautomazioni.database.common.DbConnectionConfiguration;
import it.rmautomazioni.view.common.MessageBox;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Load basic database and logger configurations from file
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class Configuration {
	
	/**
	 * reference to the properties in the file
	 */
	protected static Properties props = new Properties();
	protected static FileReader cfgFile;
	/**
	 * Database connection string
	 */
	private static String DATABASE_CONNECTION_STRING;
	public static String DATABASE_CONNECTION_STRING_PROP_NAME = "DB_CONNECTION_STRING";
	
	/**
	 * Classe di driver per la connessione al DB
	 */
	private static String DATABASE_DRIVER_CLASS;
	public static String DATABASE_DRIVER_CLASS_PROP_NAME = "DB_DRIVER_CLASS";
	
	/**
	 * Database user name
	 */
	private static String DATABASE_USER;
	public static String DATABASE_USER_PROP_NAME = "DB_USER";
	
	/**
	 * Database password
	 */
	private static String DATABASE_PASSWORD;
	public static String DATABASE_PASSWORD_PROP_NAME = "DB_PASSWORD";
		
	//Database configuration
	private static DbConnectionConfiguration dbConfig;
	
	/**
	 * Logger names
	 */
	public final static String SUPERVISOR_LOGGER = "SUPERVISOR_LOGGER";
	public final static String DBCHECKER_LOGGER = "DBCHECKER_LOGGER";
	
	public static int USER_LOGOUT_TIME_MIN = 100;
	public final static String USER_LOGOUT_TIME_MIN_PROP_NAME = "USER_LOGOUT_TIME_MIN";
	public final static String SECURITY_LOGGER = "SECURITY_LOGGER";
	
	public final static Logger logger = Logger.getLogger(SUPERVISOR_LOGGER);
	
	/**
	 * Load basic configuration from properties file
	 * 
	 * @return true if the configuration is done
	 */
	public static boolean basicInfoFromFile() {
		
        try {
        	if(cfgFile==null)
        		cfgFile = new FileReader("config/config.properties");
			props.load(cfgFile);
		} catch (FileNotFoundException e) {
			logger.error("Configuration file not found " + e.getMessage());
			MessageBox.errorBox("Configuration file not found " + e.getMessage(), "Error");
			return false;
		} catch (IOException e) {
			logger.error("Configuration file not file " + e.getMessage());
			MessageBox.errorBox("Configuration file error " + e.getMessage(), "Error");
			return false;
		}
        	
        DATABASE_CONNECTION_STRING = props.getProperty(DATABASE_CONNECTION_STRING_PROP_NAME);
        DATABASE_DRIVER_CLASS = props.getProperty(DATABASE_DRIVER_CLASS_PROP_NAME);
		DATABASE_USER = props.getProperty(DATABASE_USER_PROP_NAME);
		DATABASE_PASSWORD = props.getProperty(DATABASE_PASSWORD_PROP_NAME);
		
		dbConfig = new DbConnectionConfiguration(DATABASE_CONNECTION_STRING, DATABASE_DRIVER_CLASS, DATABASE_USER, DATABASE_PASSWORD);
				
		USER_LOGOUT_TIME_MIN = Integer.valueOf(props.getProperty(USER_LOGOUT_TIME_MIN_PROP_NAME));
				
		return true;
	}
	
	/**
	 * @return database connection string
	 */
	public static String getDbConnString() {
		return DATABASE_CONNECTION_STRING;
	}

	/**
	 * @return database driver class
	 */
	public static String getDbDriverClass() {
		return DATABASE_DRIVER_CLASS;
	}

	/**
	 * @return database user name
	 */
	public static String getDbUser() {
		return DATABASE_USER;
	}

	/**
	 * @return database password
	 */
	public static String getDbPassword() {
		return DATABASE_PASSWORD;
	}
	
	/**
	 * @return database configuration class
	 */
	public static DbConnectionConfiguration getDbConfiguration() {
		return dbConfig;
	}
}
