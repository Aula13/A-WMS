package org.wms.view.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.swing.ConcreteButtonFactory;
import it.rmautomazioni.view.factories.swing.ConcretePanelFactory;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;
import org.wms.config.ResourceUtil;

/**
 * Left navigation panel test class
 * 
 * @author Daniele
 *
 */
public class NavigationPanelUnitTest {
	
	/**
	 * Navigation panel being tested
	 */
	private static NavigationPanel navPanel;

	/**
	 * Set up environment for test
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUpBeforeClass() throws Exception {
		FactoryReferences.buttons = new ConcreteButtonFactory(ResourceUtil.iconResource);
		FactoryReferences.panels = new ConcretePanelFactory(ResourceUtil.imageResource);
		navPanel = new NavigationPanel();
		testInitComponents();
		
	}
	
	/**
	 * Initializing components test
	 */
	public void testInitComponents(){
		JPanel navBar = navPanel.getNavBar();
		
		assertNotNull(navBar);
		assertNotNull(navPanel.btnHome);
		assertNotNull(navPanel.btnInputOrders);
		assertNotNull(navPanel.btnVerifyList);
		assertNotNull(navPanel.btnJobsList);
		
		assertEquals(navBar, navPanel.btnHome.getParent());
		assertEquals(navBar, navPanel.btnInputOrders.getParent());
		assertEquals(navBar, navPanel.btnVerifyList.getParent());
		assertEquals(navBar, navPanel.btnJobsList.getParent());
		
		testNoLevelLevel();
	}
	
	
	/**
	 * Make sure the entry security level is NO_LEVEL and only home button is visible
	 */
	public void testNoLevelLevel(){
		assertEquals(SecurityLevel.NO_LEVEL, navPanel.getLevel());
		assertTrue(navPanel.btnHome.isVisible());
		assertFalse(navPanel.btnInputOrders.isVisible());
		assertFalse(navPanel.btnVerifyList.isVisible());
		assertFalse(navPanel.btnJobsList.isVisible());
	}
	
	/**
	 * Change security level to OPERATOR and check the visible buttons
	 */
	@Test
	public void testOperatorLevel(){
		navPanel.changeUser(SecurityLevel.OPERATOR);
		assertEquals(SecurityLevel.OPERATOR, navPanel.getLevel());
		assertTrue(navPanel.btnHome.isVisible());
		assertFalse(navPanel.btnInputOrders.isVisible());
		assertFalse(navPanel.btnVerifyList.isVisible());
		assertTrue(navPanel.btnJobsList.isVisible());
	}	
	
	/**
	 * Change security level to SUPERVISOR and check the visible buttons
	 */
	@Test
	public void testSupervisorLevel(){
		navPanel.changeUser(SecurityLevel.SUPERVISOR);
		assertEquals(SecurityLevel.SUPERVISOR, navPanel.getLevel());
		assertTrue(navPanel.btnHome.isVisible());
		assertFalse(navPanel.btnInputOrders.isVisible());
		assertTrue(navPanel.btnVerifyList.isVisible());
		assertFalse(navPanel.btnJobsList.isVisible());
	}	
	
	/**
	 * Change security level to ADMIN and check the visible buttons
	 */
	@Test
	public void testAdminLevel(){
		navPanel.changeUser(SecurityLevel.ADMIN);
		assertEquals(SecurityLevel.ADMIN, navPanel.getLevel());
		assertTrue(navPanel.btnHome.isVisible());
		assertTrue(navPanel.btnInputOrders.isVisible());
		assertTrue(navPanel.btnVerifyList.isVisible());
		assertTrue(navPanel.btnJobsList.isVisible());
	}

}
