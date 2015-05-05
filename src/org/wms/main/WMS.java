package org.wms.main;

import it.rmautomazioni.database.common.ConnectionStatus;
import it.rmautomazioni.database.common.DbConnectionConfiguration;
import it.rmautomazioni.database.common.DbStatusChecker;
import it.rmautomazioni.security.Security;
import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.view.common.MessageBox;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.swing.ConcreteAppStyleFactory;
import it.rmautomazioni.view.factories.swing.ConcreteButtonFactory;
import it.rmautomazioni.view.factories.swing.ConcreteFieldFactory;
import it.rmautomazioni.view.factories.swing.ConcretePanelFactory;
import it.rmautomazioni.view.graphicsresource.IconResource;
import it.rmautomazioni.view.graphicsresource.ImageResource;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.wms.config.Configuration;
import org.wms.view.common.MainGUI;

public class WMS {

	public static void main(String[] args) {
		
		//Log4j configuration
		PropertyConfigurator.configure("config/log4j.properties"); //
		Logger logger = Logger.getLogger(Configuration.SUPERVISOR_LOGGER);
		
		try {
			//Check that another application instance doesn't exist
			if(LockFile.checkLockFile()) {
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
			
			ConnectionStatus cs = new ConnectionStatus();



			FactoryReferences.fields = new ConcreteFieldFactory();
			FactoryReferences.appStyle = new ConcreteAppStyleFactory();

			IconResource iconResource = new IconResource("");
			FactoryReferences.buttons = new ConcreteButtonFactory(iconResource);

			ImageResource imageResource = new ImageResource("");
			FactoryReferences.panels = new ConcretePanelFactory(imageResource);

			Security.initializeSecurity(Configuration.USER_LOGOUT_TIME_MIN);

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					MainGUI mgui = new MainGUI(cs, Security.getSecurityManager().getStatus());

					mgui.setVisible(true);
				}
			});

			Security.getSecurityManager().openLoginScreen(SecurityLevel.ADMIN);

		} catch (Exception e) {
			logger.error(e.getMessage());
			MessageBox.errorBox("Error", "Error during application initialization");
		}
	}

}
