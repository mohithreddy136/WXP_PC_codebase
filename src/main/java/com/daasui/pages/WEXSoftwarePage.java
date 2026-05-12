package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class WEXSoftwarePage extends CommonMethod{
	
	private WEXSoftwarePage instance;
	private ObjectReader WEXSoftwarePropertiesReader = new ObjectReader();
	private Properties WEXSoftwareProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	
	public WEXSoftwarePage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXSoftwarePage.class) {
				if (instance == null) {
					instance = new WEXSoftwarePage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public WEXSoftwarePage(WebDriver driver) throws IOException {
		WEXSoftwareProperties = WEXSoftwarePropertiesReader.getObjectRepository("WEXSoftwarePage");
	}
	
	public final boolean verifyElementsOfSoftwarePage(String key) throws Exception {
		return verifyElementIsPresent(WEXSoftwareProperties.getProperty(key));
	}
	
	public final void clickOnElementsOfSytemRequirementPage(String key) throws Exception {
		click(WEXSoftwareProperties.getProperty(key));
	}

}
