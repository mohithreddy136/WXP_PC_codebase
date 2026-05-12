package com.daasui.pages;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class WorkforceExperienceTermsAndConditionsPage extends CommonMethod {
	
	private WorkforceExperienceTermsAndConditionsPage instance;
	private ObjectReader WorkforceExperienceTermsAndConditionsPagePropertiesReader = new ObjectReader();
	private Properties WorkforceExperienceTermsAndConditionsPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WorkforceExperienceTermsAndConditionsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WorkforceExperienceTermsAndConditionsPage.class) {
				if (instance == null) {
					instance = new WorkforceExperienceTermsAndConditionsPage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public WorkforceExperienceTermsAndConditionsPage(WebDriver driver) throws IOException {
		WorkforceExperienceTermsAndConditionsPageProperties =  WorkforceExperienceTermsAndConditionsPagePropertiesReader.getObjectRepository("WorkforceExperienceTermsAndConditionsPage");	
	}
	
	public final boolean verifyElementsOfTermsandconditionsPage(String key) throws Exception {
		return verifyElementIsPresent(WorkforceExperienceTermsAndConditionsPageProperties.getProperty(key));
	}
	
	public final boolean verifyElementIsClickableOfTermsandconditionsPage(String key) throws Exception {
		return verifyElementIsClickable(WorkforceExperienceTermsAndConditionsPageProperties.getProperty(key));
	}

	public final void scrollOnTermsandconditionsPage(String key) throws Exception {
		scrollTillView(WorkforceExperienceTermsAndConditionsPageProperties.getProperty(key));
	}

	public final String getTextOfTermsandconditionsPage(String key) throws Exception {
		return getTextBy(WorkforceExperienceTermsAndConditionsPageProperties.getProperty(key));
	
	}
	public final void clickOnElementsOfTermsandconditionsPage(String key) throws Exception {
		click(WorkforceExperienceTermsAndConditionsPageProperties.getProperty(key));
	}
	
	
	public final boolean waitForElementsOfTermsandconditions(String key,int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(WorkforceExperienceTermsAndConditionsPageProperties.getProperty(key),waitTime);
	} 
	
	public final void switchToIframePage(String key) throws Exception {
		switchToIframe(WorkforceExperienceTermsAndConditionsPageProperties.getProperty(key));
	}
}
