package org.wms.test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

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
	
}
