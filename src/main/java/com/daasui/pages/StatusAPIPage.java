package com.daasui.pages;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.StatusPageUtil;
import com.basesource.utils.ObjectReader;

public class StatusAPIPage extends CommonMethod {
	
	private ObjectReader statusAPIPagePropertiesReader = new ObjectReader();
	public Properties statusAPIPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private StatusAPIPage instance;

	public StatusAPIPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (StatusAPIPage.class) {
				if (instance == null) {
					instance = new StatusAPIPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public StatusAPIPage(WebDriver driver) throws IOException {
		statusAPIPageProperties = statusAPIPagePropertiesReader.getObjectRepository("StatusAPIPage");
	}

	
	public static void verifyComponentStatusUpdate(String componentId,String expectedStatus) {
		
		String currentStatus = StatusPageUtil.fetchComponentStatus(componentId);
        System.out.println("Component status before update: " + currentStatus);
        
		if (!currentStatus.equals(expectedStatus)) {
			 var patchResponse = StatusPageUtil.updateComponentStatus(componentId,expectedStatus);
		     assertEquals(patchResponse.getStatusCode(), 200, "PATCH failed!");
		     System.out.println("PATCH Response:\n" + patchResponse.asString());
		}

        String actualStatus = StatusPageUtil.fetchComponentStatus(componentId);
        System.out.println("Component status after update: " + actualStatus);

        assertEquals(actualStatus, expectedStatus, "Component status does not match expected!");
    }
}
