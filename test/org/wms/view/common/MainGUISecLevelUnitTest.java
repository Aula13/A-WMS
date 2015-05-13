package org.wms.view.common;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.rmautomazioni.database.common.ConnectionStatus;
import it.rmautomazioni.security.SecurityLevel;
import it.rmautomazioni.security.SecurityStatus;
import it.rmautomazioni.security.User;
import it.rmautomazioni.view.factories.FactoryReferences;
import it.rmautomazioni.view.factories.RMColour;
import it.rmautomazioni.view.factories.swing.ConcreteAppStyleFactory;
import it.rmautomazioni.view.factories.swing.ConcreteButtonFactory;
import it.rmautomazioni.view.factories.swing.ConcreteFieldFactory;
import it.rmautomazioni.view.factories.swing.ConcretePanelFactory;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.wms.config.ResourceUtil;

class TestParameter {
	
	SecurityLevel level;
	Color aspectedColor;
	String aspectedText;
	public TestParameter(SecurityLevel level, Color aspectedColor,
			String aspectedText) {
		super();
		this.level = level;
		this.aspectedColor = aspectedColor;
		this.aspectedText = aspectedText;
	}
}

/**
 * Parameterized class for test security status
 * on main gui status bar
 * 
 * 
 * @author stefano
 *
 */
@RunWith(Parameterized.class)
public class MainGUISecLevelUnitTest {
	
	static MainGUI guiTest;
	
	static ConnectionStatus mockConStatus;
	static SecurityStatus mockSecStatus;
	
	/**
	 * Test[0]: user not logged, label should be gray and show text No user logged
	 * Test[1]: user with NO_LEVEL logged, label should be green and show text NO_LEVEL
	 * Test[2]: user with OPERATOR logged, label should be green and show text OPERATOR
	 * Test[3]: user with SUPERVISOR logged, label should be green and show text SUPERVISOR
	 * Test[4]: user with ADMIN logged, label should be green and show text ADMIN
	 * 
	 * @return collection of parameters for test
	 */
	@Parameters
	public static Collection<TestParameter[]> data() {
		return Arrays.asList(new TestParameter[][] {
			{new TestParameter(null, RMColour.RM_DARK_GRAY, "No user logged")},
			{new TestParameter(SecurityLevel.NO_LEVEL, RMColour.RM_GREEN, "NO_LEVEL")},
			{new TestParameter(SecurityLevel.OPERATOR, RMColour.RM_GREEN, "OPERATOR")},
			{new TestParameter(SecurityLevel.SUPERVISOR, RMColour.RM_GREEN, "SUPERVISOR")},
			{new TestParameter(SecurityLevel.ADMIN, RMColour.RM_GREEN, "ADMIN")}
		});		
	}
	
	@Parameter
	public TestParameter aData;

	/**
	 * Setup environment for test (factories and mock)
	 * 
	 * @throws Exception
	 */
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
	
	/**
	 * Parameterized test
	 * check color and text in status bar security label 
	 */
	@Test
	public void testUpdateUserLevel() {
		if(aData.level==null) {
			when(mockSecStatus.getUser()).thenReturn(null);
			when(mockSecStatus.isLogged()).thenReturn(false);
		} else {
			when(mockSecStatus.getUser()).thenReturn(new User(aData.aspectedText, "", aData.level));
			when(mockSecStatus.isLogged()).thenReturn(true);
		}
		guiTest.update(mockSecStatus, null);
		assertTrue(guiTest.getLblUsers().getBackground()==aData.aspectedColor);
		assertTrue(guiTest.getLblUsers().getText()==aData.aspectedText);
	}
}
