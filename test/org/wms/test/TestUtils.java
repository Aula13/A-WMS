package org.wms.test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.jx.ConcreteJXAppStyleFactory;
import it.rmautomazioni.view.factories.swing.ConcreteButtonFactory;
import it.rmautomazioni.view.factories.swing.ConcreteFieldFactory;
import it.rmautomazioni.view.factories.swing.ConcretePanelFactory;

import org.wms.config.ResourceUtil;
import org.wms.config.Utils;
import org.wms.view.common.MessageBox;

/**
 * Utils for testing
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class TestUtils {
	
	public static MessageBox mockMsgBox;
	
	public static void mockMessageBox() {
		mockMsgBox = mock(MessageBox.class);
		
		doNothing().when(mockMsgBox).errorBox(anyString(), anyString());
		doNothing().when(mockMsgBox).errorBox(anyString(), anyString());
		doNothing().when(mockMsgBox).errorBox(anyString(), anyString());
		doReturn("").when(mockMsgBox).inputBox(anyString());
		doReturn(1).when(mockMsgBox).questionBox(anyString(), anyString());
		
		Utils.msg = mockMsgBox;
	}
	
	public static void initGUIFactories() {
		FactoryReferences.fields = new ConcreteFieldFactory();
		FactoryReferences.appStyle = new ConcreteJXAppStyleFactory();
		FactoryReferences.buttons = new ConcreteButtonFactory(ResourceUtil.iconResource);
		FactoryReferences.panels = new ConcretePanelFactory(ResourceUtil.imageResource);
		
	}
	
}
