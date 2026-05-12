package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class WEPEmptyDashboardPage  extends CommonMethod {
	private WEPEmptyDashboardPage instance;
	private ObjectReader WEPEmptyDashboardPagePropertiesReader = new ObjectReader();
	private Properties WEPEmptyDashboardPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEPEmptyDashboardPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPEmptyDashboardPage.class) {
				if (instance == null) {
					instance = new WEPEmptyDashboardPage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public WEPEmptyDashboardPage(WebDriver driver) throws IOException {
		WEPEmptyDashboardPageProperties = WEPEmptyDashboardPagePropertiesReader.getObjectRepository("WEPEmptyDashboard");	
	}
	
	public final boolean verifyElementsOfWEPEmptyDashboardPage(String key) throws Exception {
		return verifyElementIsPresent(WEPEmptyDashboardPageProperties.getProperty(key));
	}
	
	public final boolean verifyElementIsClickableOfWEPEmptyDashboardPage(String key) throws Exception {
		return verifyElementIsClickable(WEPEmptyDashboardPageProperties.getProperty(key));
	}

	public final void scrollOnWEPEmptyDashboardPage(String key) throws Exception {
		scrollTillView(WEPEmptyDashboardPageProperties.getProperty(key));
	}

	public final String getTextOfWEPEmptyDashboardPage(String key) throws Exception {
		return getTextBy(WEPEmptyDashboardPageProperties.getProperty(key));
	
	}
	public final void clickOnElementsOfWEPEmptyDashboardPage(String key) throws Exception {
		click(WEPEmptyDashboardPageProperties.getProperty(key));
	}
	
	
	public final boolean waitForElementsOfWEPEmptyDashboardPage(String key,int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(WEPEmptyDashboardPageProperties.getProperty(key),waitTime);
	}

}
