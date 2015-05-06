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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.wms.config.Configuration;
import org.wms.config.HibernateUtil;
import org.wms.config.ResourceUtil;
import org.wms.config.SecurityConfig;
import org.wms.controller.common.MainGUIController;
import org.wms.view.common.MainGUI;

public class WMS {

	public static void main(String[] args) {
		
		//Log4j configuration
		PropertyConfigurator.configure("config/log4j.properties"); //
		Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER);
		
		try {
			//Check that another application instance doesn't exist
			if(!LockFile.checkLockFile()) {
				MessageBox.errorBox("Error", "Another instance of the application is running.");
				return;
			}
			
			//Load configuration file 
			if(!Configuration.basicInfoFromFile())
			{
				MessageBox.errorBox("Error", "Error during application configuration file initialization.");
				return;	
			}
			
			//Start database connection checker
			DbStatusChecker dbStatusChecker = new DbStatusChecker(
					"DBChecker", 
					Configuration.DBCHECKER_LOGGER, 
					1000, 
					Configuration.getDbConfiguration());
			
			if(!dbStatusChecker.checkDatabaseConnection()) {
				MessageBox.errorBox("Database connection error.", "Error");
				return;
			}	
			
			dbStatusChecker.start();
			
			//Init hibernate
			HibernateUtil.getSessionFactory();


			FactoryReferences.fields = new ConcreteFieldFactory();
			FactoryReferences.appStyle = new ConcreteAppStyleFactory();

			FactoryReferences.buttons = new ConcreteButtonFactory(ResourceUtil.iconResource);

			FactoryReferences.panels = new ConcretePanelFactory(ResourceUtil.imageResource);

			SecurityConfig.initializeSecurity(Configuration.USER_LOGOUT_TIME_MIN);

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			
			
			MainGUI mgui = new MainGUI(DbConnectionProvider.CONNECTION_STATUS, SecurityConfig.getSecurityManager().getStatus());
			new MainGUIController(mgui);
				
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					mgui.setVisible(true);
				}
			});			
			
			SecurityConfig.getSecurityManager().openLoginScreen(SecurityLevel.OPERATOR);

		} catch (Exception e) {
			logger.error(e.getMessage());
			MessageBox.errorBox("Error", "Error during application initialization");
			return;
		}
	}

}
