package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class WorkforceExperienceSystemRequirementsPage extends CommonMethod {
	
	private WorkforceExperienceSystemRequirementsPage instance;
	private ObjectReader WorkforceExperienceSystemRequirementsPagePropertiesReader = new ObjectReader();
	private Properties WorkforceExperienceSystemRequirementsPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(WorkforceExperienceSystemRequirementsPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WorkforceExperienceSystemRequirementsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WorkforceExperienceSystemRequirementsPage.class) {
				if (instance == null) {
					instance = new WorkforceExperienceSystemRequirementsPage(DRIVER);

				}
			}
		}
		return instance;
	} 
	
	public WorkforceExperienceSystemRequirementsPage(WebDriver driver) throws IOException {
		WorkforceExperienceSystemRequirementsPageProperties = WorkforceExperienceSystemRequirementsPagePropertiesReader.getObjectRepository("WorkforceExperienceSystemRequirements");	
	}
	
	public final boolean verifyElementsOfSytemRequirementPage(String key) throws Exception {
		return verifyElementIsPresent(WorkforceExperienceSystemRequirementsPageProperties.getProperty(key));
	}
	
	public final boolean verifyElementIsClickableOfSytemRequirementPage(String key) throws Exception {
		return verifyElementIsClickable(WorkforceExperienceSystemRequirementsPageProperties.getProperty(key));
	}

	public final void scrollOnSytemRequirementPage(String key) throws Exception {
		scrollTillView(WorkforceExperienceSystemRequirementsPageProperties.getProperty(key));
	}

	public final String getTextOfSytemRequirementPage(String key) throws Exception {
		return getTextBy(WorkforceExperienceSystemRequirementsPageProperties.getProperty(key));
	
	}
	public final void clickOnElementsOfSytemRequirementPage(String key) throws Exception {
		click(WorkforceExperienceSystemRequirementsPageProperties.getProperty(key));
	}
	
	
	public final boolean waitForElementsOfSytemRequirementPage(String key,int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(WorkforceExperienceSystemRequirementsPageProperties.getProperty(key),waitTime);
	} 
	
}
