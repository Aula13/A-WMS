package org.wms.main;

import it.rmautomazioni.database.common.DbConnectionProvider;
import it.rmautomazioni.database.common.DbStatusChecker;
import it.rmautomazioni.security.Security;
import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.view.common.MessageBox;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.swing.ConcreteAppStyleFactory;
import it.rmautomazioni.view.factories.swing.ConcreteButtonFactory;
import it.rmautomazioni.view.factories.swing.ConcreteFieldFactory;
import it.rmautomazioni.view.factories.swing.ConcretePanelFactory;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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

public class WMS {

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
	
	private static Logger setupLogger(){
		//Log4j configuration
		PropertyConfigurator.configure("config/log4j.properties"); //
		return Logger.getLogger(Configuration.SUPERVISOR_LOGGER);
	}
	
	private static void checkIfAlreadyInstantiated() throws Exception{
		if(!LockFile.checkLockFile())
			throw new AlreadyInstantiatedException();
	}

	private static void loadConfigFile() throws Exception{
		if(!Configuration.basicInfoFromFile())
			throw new ConfigFileLoadingException();
	}
	
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
	
	private static void initFactories(){
		FactoryReferences.fields = new ConcreteFieldFactory();
		FactoryReferences.appStyle = new ConcreteAppStyleFactory();
		FactoryReferences.buttons = new ConcreteButtonFactory(ResourceUtil.iconResource);
		FactoryReferences.panels = new ConcretePanelFactory(ResourceUtil.imageResource);
	}
	
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
