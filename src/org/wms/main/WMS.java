package org.wms.main;

import it.rmautomazioni.database.common.DbConnectionProvider;
import it.rmautomazioni.database.common.DbStatusChecker;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.jx.ConcreteJXAppStyleFactory;
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
import org.wms.config.Utils;
import org.wms.controller.common.MainGUIController;
import org.wms.exception.AlreadyInstantiatedException;
import org.wms.exception.ConfigFileLoadingException;
import org.wms.exception.DBConnectionException;
import org.wms.model.common.ModelReference;
import org.wms.view.common.MainGUI;

/**
 * Application's main class, it contains the main method and invoke MainGUI
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class WMS {

	protected static DbStatusChecker dbStatusChecker;
	
	/**
	 * Main method
	 * 
	 * @param args
	 * 
	 * @throws Exception 
	 */
	public static void main(String[] args) {
		if(!launchWMS())
			System.exit(1);;
	}
	
	/**
	 * Magic happens here
	 * 
	 * @throws Exception 
	 */
	public static boolean launchWMS() {
		
		Logger logger = setupLogger();
		
		try {
			
			checkIfAlreadyInstantiated();
			
			loadConfigFile();
			
			//Init hibernate
			HibernateUtil.buildSessionFactory("config/hibernate.cfg.xml");
			
			configDBConnectionChecker();
			
			initFactories();
			
			SecurityConfig.initializeSecurity(Configuration.USER_LOGOUT_TIME_MIN);
			
			ModelReference.initModel();
			
			invokeGUI();
			
//			SecurityConfig.getSecurityManager().openLoginScreen(SecurityLevel.OPERATOR);
			
			startBackgroudTasks();
			
			return true;
			
		} catch (Exception e) {
			stopBackgroudTasks();
			logger.error(e);
			Utils.msg.errorBox("Error", "Error during application initialization");
			return false;
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
	private static void configDBConnectionChecker() throws Exception {
		dbStatusChecker = new DbStatusChecker(
				"DBChecker", 
				Configuration.DBCHECKER_LOGGER, 
				1000, 
				Configuration.getDbConfiguration());
		
		if(!dbStatusChecker.checkDatabaseConnection())
			throw new DBConnectionException();
	}
	
	/**
	 * Initialize the factories
	 */
	public static void initFactories(){
		FactoryReferences.fields = new ConcreteFieldFactory();
		FactoryReferences.appStyle = new ConcreteJXAppStyleFactory();
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
		new MainGUIController(mgui, 
				ModelReference.ordersModel, 
				ModelReference.materialsModel,
				ModelReference.batchesModel);
			
		SwingUtilities.invokeLater(new Runnable() {
	
			@Override
			public void run() {
				mgui.setVisible(true);
			}
		});
	}
	
	/**
	 * Start background tasks
	 */
	private static void startBackgroudTasks() {
		dbStatusChecker.start();
	}
	
	
	/**
	 * Stop background tasks
	 */
	private static void stopBackgroudTasks() {
//		dbStatusChecker.
	}
}
