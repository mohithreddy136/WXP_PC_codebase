package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class WEPNewLoginpage extends CommonMethod {
	
	private WEPNewLoginpage instance;
	private ObjectReader WEPNewloginpagePropertiesReader = new ObjectReader();
	private Properties WEPNewloginpageProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEPNewLoginpage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPNewLoginpage.class) {
				if (instance == null) {
					instance = new WEPNewLoginpage(DRIVER);

				}
			}
		}
		return instance;
	}
	public WEPNewLoginpage(WebDriver driver) throws IOException {
		WEPNewloginpageProperties = WEPNewloginpagePropertiesReader.getObjectRepository("WEPNewloginpage");	
	}
	public final boolean verifyElementsOfNewLoginPage(String key) throws Exception {
		return verifyElementIsPresent(WEPNewloginpageProperties.getProperty(key));
	}
	
	public final boolean verifyElementIsClickableOfNewLoginPagePage(String key) throws Exception {
		return verifyElementIsClickable(WEPNewloginpageProperties.getProperty(key));
	}

	public final void scrollOnNewLoginPage(String key) throws Exception {
		scrollTillView(WEPNewloginpageProperties.getProperty(key));
	}

	public final String getTextOfNewLoginPage(String key) throws Exception {
		return getTextBy(WEPNewloginpageProperties.getProperty(key));
	
	}
	
	public final void clickOnElementsOfNewLoginPage(String key) throws Exception {
		click(WEPNewloginpageProperties.getProperty(key));
	}
	
	public final void clickByJavaScriptOnElementsOfNewLoginPage(String key) throws Exception {
		clickByJavaScript(WEPNewloginpageProperties.getProperty(key));
	}
	
	public final boolean waitForElementsOfNewLoginPage(String key,int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(WEPNewloginpageProperties.getProperty(key),waitTime);
	} 
}
