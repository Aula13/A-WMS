package org.wms.main;

import javax.swing.SwingUtilities;

import org.wms.view.common.MainGUI;

import it.rmautomazioni.database.common.ConnectionStatus;
import it.rmautomazioni.security.Security;
import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.swing.ConcreteAppStyleFactory;
import it.rmautomazioni.view.factories.swing.ConcreteButtonFactory;
import it.rmautomazioni.view.factories.swing.ConcreteFieldFactory;
import it.rmautomazioni.view.factories.swing.ConcretePanelFactory;
import it.rmautomazioni.view.graphicsresource.IconResource;
import it.rmautomazioni.view.graphicsresource.ImageResource;

public class WMS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ConnectionStatus cs = new ConnectionStatus();
		
		FactoryReferences.fields = new ConcreteFieldFactory();
		FactoryReferences.appStyle = new ConcreteAppStyleFactory();
		
		IconResource iconResource = new IconResource("");
		FactoryReferences.buttons = new ConcreteButtonFactory(iconResource);
		
		ImageResource imageResource = new ImageResource("");
		FactoryReferences.panels = new ConcretePanelFactory(imageResource);
		
		Security.initializeSecurity(100);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				MainGUI mgui = new MainGUI(cs, Security.getSecurityManager().getStatus());
				
				mgui.setVisible(true);
			}
		});
		
		Security.getSecurityManager().openLoginScreen(SecurityLevel.ADMIN);
		
	}

}
