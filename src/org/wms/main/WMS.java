package org.wms.main;

import it.rmautomazioni.database.common.DbConnectionProvider;
import it.rmautomazioni.database.common.DbStatusChecker;
import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.view.common.MessageBox;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.swing.ConcreteAppStyleFactory;
import it.rmautomazioni.view.factories.swing.ConcreteButtonFactory;
import it.rmautomazioni.view.factories.swing.ConcreteFieldFactory;
import it.rmautomazioni.view.factories.swing.ConcretePanelFactory;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.config.ResourceUtil;
import org.wms.config.SecurityConfig;
import org.wms.controller.common.MainGUIController;
import org.wms.exception.AlreadyInstantiatedException;
import org.wms.exception.ConfigFileLoadingException;
import org.wms.exception.DBConnectionException;
import org.wms.view.common.MainGUI;

/**
 * Application's main class, it contains the main method and invoke MainGUI
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WMS {

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Logger logger = setupLogger();
		
		try {
			
			checkIfAlreadyInstantiated();
			
			loadConfigFile();
			
			startDBConnectionChecker();
			
			//Init hibernate
			HibernateUtil.getSessionFactory();
			
			initFactories();
			
			SecurityConfig.initializeSecurity(Configuration.USER_LOGOUT_TIME_MIN);
			
			invokeGUI();
			
			SecurityConfig.getSecurityManager().openLoginScreen(SecurityLevel.OPERATOR);

		} catch (Exception e) {
			logger.error(e.getMessage());
			MessageBox.errorBox("Error", "Error during application initialization");
			return;
		}
	}
	
	/**
	 * Set up and return the logger
	 * 
	 * @return the application's logger
	 */
	private static Logger setupLogger(){
		//Log4j configuration
		PropertyConfigurator.configure("config/log4j.properties"); //
		return Logger.getLogger(Configuration.SUPERVISOR_LOGGER);
	}
	
	/**
	 * Check if another application is running
	 * 
	 * @throws Exeption, AlreadyInstantiatedException
	 */
	private static void checkIfAlreadyInstantiated() throws Exception{
		if(!LockFile.checkLockFile())
			throw new AlreadyInstantiatedException();
	}

	/**
	 * Try to load the configuration file
	 * 
	 * @throws Exception, ConfigFileLoadingException
	 */
	private static void loadConfigFile() throws Exception{
		if(!Configuration.basicInfoFromFile())
			throw new ConfigFileLoadingException();
	}
	
	/**
	 * Try to create a database status checker ad start it
	 * 
	 * @throws Exception, DBConnectionException
	 */
	private static void startDBConnectionChecker() throws Exception {
		DbStatusChecker dbStatusChecker = new DbStatusChecker(
				"DBChecker", 
				Configuration.DBCHECKER_LOGGER, 
				1000, 
				Configuration.getDbConfiguration());
		
		if(!dbStatusChecker.checkDatabaseConnection())
			throw new DBConnectionException();
		
		dbStatusChecker.start();
	}
	
	/**
	 * Initialize the factories
	 */
	private static void initFactories(){
		FactoryReferences.fields = new ConcreteFieldFactory();
		FactoryReferences.appStyle = new ConcreteAppStyleFactory();
		FactoryReferences.buttons = new ConcreteButtonFactory(ResourceUtil.iconResource);
		FactoryReferences.panels = new ConcretePanelFactory(ResourceUtil.imageResource);
	}
	
	/**
	 * Invoke the main GUI
	 * 
	 * @throws Exception
	 */
	private static void invokeGUI() throws Exception{
		
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		
		MainGUI mgui = new MainGUI(DbConnectionProvider.CONNECTION_STATUS, SecurityConfig.getSecurityManager().getStatus());
		new MainGUIController(mgui);
			
		SwingUtilities.invokeLater(new Runnable() {
	
			@Override
			public void run() {
				mgui.setVisible(true);
			}
		});
	}
	
}
