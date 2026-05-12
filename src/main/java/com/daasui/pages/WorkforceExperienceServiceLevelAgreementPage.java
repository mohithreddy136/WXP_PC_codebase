package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class WorkforceExperienceServiceLevelAgreementPage  extends CommonMethod {
	
	private WorkforceExperienceServiceLevelAgreementPage instance;
	private ObjectReader WorkforceExperienceServiceLevelAgreementPagePropertiesReader = new ObjectReader();
	private Properties WorkforceExperienceServiceLevelAgreementPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WorkforceExperienceServiceLevelAgreementPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WorkforceExperienceServiceLevelAgreementPage.class) {
				if (instance == null) {
					instance = new WorkforceExperienceServiceLevelAgreementPage(DRIVER);

				}
			}
		}
		return instance;
	}
	public WorkforceExperienceServiceLevelAgreementPage(WebDriver driver) throws IOException {
		WorkforceExperienceServiceLevelAgreementPageProperties = WorkforceExperienceServiceLevelAgreementPagePropertiesReader.getObjectRepository("WorkforceExperienceServiceLevelAgreementPage");	
	}
	
	public final boolean verifyElementsOfServiceLevelAgreementPage(String key) throws Exception {
		return verifyElementIsPresent(WorkforceExperienceServiceLevelAgreementPageProperties.getProperty(key));
	}
	
	public final boolean verifyElementIsClickableOfServiceLevelAgreementPage(String key) throws Exception {
		return verifyElementIsClickable(WorkforceExperienceServiceLevelAgreementPageProperties.getProperty(key));
	}

	public final void scrollOnServiceLevelAgreementPage(String key) throws Exception {
		scrollTillView(WorkforceExperienceServiceLevelAgreementPageProperties.getProperty(key));
	}

	public final String getTextOfServiceLevelAgreementPage(String key) throws Exception {
		return getTextBy(WorkforceExperienceServiceLevelAgreementPageProperties.getProperty(key));
	
	}
	public final void clickOnElementsOfServiceLevelAgreement(String key) throws Exception {
		click(WorkforceExperienceServiceLevelAgreementPageProperties.getProperty(key));
	}
	
	
	public final boolean waitForElementsOfServiceLevelAgreement(String key,int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(WorkforceExperienceServiceLevelAgreementPageProperties.getProperty(key),waitTime);
	} 
	

}
