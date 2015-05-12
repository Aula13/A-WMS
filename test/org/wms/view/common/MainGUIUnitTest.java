package org.wms.view.common;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.rmautomazioni.database.common.ConnectionStatus;
import it.rmautomazioni.security.SecurityStatus;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.RMColour;
import it.rmautomazioni.view.factories.swing.ConcreteAppStyleFactory;
import it.rmautomazioni.view.factories.swing.ConcreteButtonFactory;
import it.rmautomazioni.view.factories.swing.ConcreteFieldFactory;
import it.rmautomazioni.view.factories.swing.ConcretePanelFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wms.config.ResourceUtil;

public class MainGUIUnitTest {
	
	static MainGUI guiTest;
	
	static ConnectionStatus mockConStatus;
	static SecurityStatus mockSecStatus;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FactoryReferences.fields = new ConcreteFieldFactory();
		FactoryReferences.appStyle = new ConcreteAppStyleFactory();
		FactoryReferences.buttons = new ConcreteButtonFactory(ResourceUtil.iconResource);
		FactoryReferences.panels = new ConcretePanelFactory(ResourceUtil.imageResource);
		
		mockConStatus = mock(ConnectionStatus.class);	
		mockSecStatus = mock(SecurityStatus.class);
		
		guiTest = new MainGUI(mockConStatus, mockSecStatus);
	}
	
	@Test
	public void testUpdateConnectionFail() {
		when(mockConStatus.isDbConnectionStatus()).thenReturn(false);
		guiTest.update(mockConStatus, null);
		assertTrue(guiTest.getLblDbConn().getBackground()==RMColour.RM_LIGHT_RED);
		assertTrue(guiTest.getLblDbConn().getText().compareTo("DB offline")==0);
	}
	
	@Test
	public void testUpdateConnectionRight() {
		when(mockConStatus.isDbConnectionStatus()).thenReturn(true);
		guiTest.update(mockConStatus, null);
		assertTrue(guiTest.getLblDbConn().getBackground()==RMColour.RM_GREEN);
		assertTrue(guiTest.getLblDbConn().getText().compareTo("DB online")==0);
	}	
	
	@Test
	public void testSetContextTitle() {
		guiTest.setTitleName("ATITLE");
		assertTrue(guiTest.getLblTitle().getText().compareTo("A-WMS - ATITLE")==0);
	}

	@Test
	public void testChangePanel() {
		JPanel testPanel1 = new JPanel();
		guiTest.changePanel(testPanel1);
		assertTrue(guiTest.currentPanel==testPanel1);
		
		JPanel testPanel2 = new JPanel();
		guiTest.changePanel(testPanel2);
		assertTrue(guiTest.currentPanel==testPanel2);
		
		guiTest.changePanel(testPanel1);
		assertTrue(guiTest.currentPanel==testPanel1);
	}

	@Test
	public void testSetActiveButton() {
		fail("Not yet implemented"); // TODO
	}

}
