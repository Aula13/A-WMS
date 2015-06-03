package org.wms.view.order;

import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.jx.ConcreteJXAppStyleFactory;
import it.rmautomazioni.view.factories.swing.ConcreteButtonFactory;
import it.rmautomazioni.view.factories.swing.ConcreteFieldFactory;
import it.rmautomazioni.view.factories.swing.ConcretePanelFactory;

import org.junit.Before;
import org.junit.Test;
import org.wms.config.ResourceUtil;

/**
 * Test order view
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class OrderViewTest {

	private OrderView view;
	
	@Before
	public void initFactories(){
			FactoryReferences.fields = new ConcreteFieldFactory();
			FactoryReferences.appStyle = new ConcreteJXAppStyleFactory();
			FactoryReferences.buttons = new ConcreteButtonFactory(ResourceUtil.iconResource);
			FactoryReferences.panels = new ConcretePanelFactory(ResourceUtil.imageResource);
	}
	 
	@Test
	public void test() {
		
		//view = new OrderEditView();
		//view.setVisible(true);
	}


}
