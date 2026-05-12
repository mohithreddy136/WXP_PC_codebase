package com.daasui.pages;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.OnboardingVariables;


/**
 * This class implements methods related to Onboarding page
 *
 */
public class OnboardingPage extends CommonMethod {

	private Properties onboardingPageProperties;
	private OnboardingPage instance;
	private ObjectReader onboardingPagePropertiesReader = new ObjectReader();
	public static String uiVersion = System.getProperty("uiVersion");
	public OnboardingPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (UserPage.class) {
				if (instance == null) {
					instance = new OnboardingPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public OnboardingPage(WebDriver driver) throws IOException {
		onboardingPageProperties = onboardingPagePropertiesReader.getObjectRepository("OnboardingPageV3");
	}

	/**
	 * This is a method to match text on an element
	 * 
	 * @param key - Locator of element
	 * @param Text - Text to be matched
	 * @return - boolean value of whether the text present on element matches or not
	 */
	public final boolean matchTextOfOnboardingPage(String key, String Text) {
		try {
			return verifyTextPresentOnElement(onboardingPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error("Exception in matchTextOfOnboardingPage" + e.getMessage());
			return false;
		}

	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfOnboardingPage(String key) {
		try {
			return verifyElementIsPresent(onboardingPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error("Exception in verifyElementsOfOnboardingPage" + e.getMessage());
			return false;
		}

	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key Locator of element
	 * @return true
	 */
	public final boolean waitForElementsOfOnboardingPage(String key) {
		try {
		return verifyElementIsVisible(onboardingPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error("Exception in waitForElementsOfOnboardingPage" + e.getMessage());
			return false;
	}
	
	}
	
	/**
	 * This is a method to get text of an element
	 * 
	 * @param key Locator of element
	 * @return true
	 */

	public final String getTextOfOnboardingPage(String key) {
		
		try {
			return getTextBy(onboardingPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error("getTextOfOnboardingPage method failed");
			return "";
		}
	
	}
	
	
	/**
	 * This is a method to to click on an element
	 * 
	 * @param key Locator of element
	 */

	public final void clickOnElementsOfOnboardingPage(String key)  {
		try {
			click(onboardingPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error("Exception in waitForElementsOfOnboardingPage" + e.getMessage());
			return;
	}
		
	}
	
	/**
	 * This is a method to enter text on an element
	 * 
	 * @param key Locator of element
	 */

	public final void enterTextForOnboardingPage(String key, String Text) {
		try {
			enterText(onboardingPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error("Exception in enterTextForLogPage" + e.getMessage());
			return;
		}
	}
	
	
	/**
	 * This is a method to select element from a dropdown by passing dynamic xpath
	 * 
	 * @param dropdownId - Locator of the arrow present on dropdown
	 * @param key - Locator of the list of dropdown options
	 * @param text - Text of the option which is to be selected
	 * @return - Option selected
	 */
	public final boolean selectElementFromDropDownofOnboardingPage(String dropdownId, String key, String text) {
		try {
			click(onboardingPageProperties.getProperty(dropdownId));
			return selectFromDropdown(onboardingPageProperties.getProperty(dropdownId), onboardingPageProperties.getProperty(key), text);
		} catch (Exception e) {
			return false;
		} 
		
	}
	
	public final void clickByJavaScriptOnOnboardingPage(String key) {
		try {
			clickByJavaScript(onboardingPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error("clickByJavaScriptOnOnboardingPage method failed");
		}
	}
	
	
	/**
	 * This is a method to get all the elements
	 * 
	 * @param key locator of element
	 * @return List<WebElement>
	 */

	public final List<WebElement> getElementsOfOnboardingPage(String key) {
		try {
			return getElementsTillAllElementsPresent(onboardingPageProperties.getProperty(key));
		}catch (Exception e) {
			LOGGER.error("getElementsOfOnboardingPage method failed");
			return null;
		}
		
		
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key Locator of element
	 * @return true
	 */
	public final boolean verifyElementsOfOnboardingPage(List<WebElement> stepperElements) {
		boolean flag = false;
		try {
		Iterator<WebElement> stepperIterator = stepperElements.iterator();
		while (stepperIterator.hasNext()) {
			
			verifyElementIsVisible(stepperIterator.next());
			flag = true;
		}
		} catch (Exception e) {
			LOGGER.error("Exception in waitForElementsOfOnboardingPage" + e.getMessage());
			return false;
	}
		return flag;
	
	}
	
	
	/**
	 * This is a method to set window variable to true which will block API call
	 * 
	 * @return Boolean if variable value set without any error flag returned is true else false
	 */
	
	public final boolean setAutomationVariable() {
		boolean flag = false;
		try {
			jsDriver().executeScript("is_automation_test = true");
			flag = Boolean.parseBoolean((jsDriver().executeScript("return is_automation_test").toString()));
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception in setAutomationVariable" + e.getMessage());
			
			return flag;
		}

	}
	
	/**
	 * This is a method to get value from window variable 
	 * 
	 * @return string variable contains text that will be sent in mail
	 */
	
	
	public final String getMailContent() {
		String mailContent = null;
		try {
			mailContent = jsDriver().executeScript("return onboarding_request_body.content").toString();
		} catch (Exception e) {
			LOGGER.error("Exception in getMailContent" + e.getMessage());
			return null;
		}
		return mailContent;
	}
	
	/**
	 * This is a method to verify redirection to HP Terms and conditions and Privacy statement page.
	 * 
	 * @return boolean-Returns the result for terms and condition and privacy page visibility
	 */
	
	
	public final boolean verifyRedirectionForTermsAndCondition() {
		boolean urlCheck = false,printButtonCheck = false;
		try {
			clickOnElementsOfOnboardingPage("hpTermsAndConditionLinkWf");
			switchToDifferentTabOfOnboardingPage();
			waitUntilElementIsVisibleOfOnboardingPage("spinner");
			String workflowTermsAndConditionUrl = getEnvironmentSpecificData(System.getProperty("environment"), "WORKFLOW_TERMSCOND_URL");
			urlCheck=workflowTermsAndConditionUrl.equalsIgnoreCase(getUrlOfCurrentPage());
			verifyElementsOfOnboardingPage("printButtonTermsAndConditionsPage");
			printButtonCheck=matchTextOfOnboardingPage("hpTermsAndConditionPageHeading","HP Workflow Transformation Solution with Analytics Services: Terms and Conditions");
			switchBackToPreviousTabOfOnboardingPage();
			clickOnElementsOfOnboardingPage("hpPrivacyStatement");
			switchToDifferentTabOfOnboardingPage();
			waitUntilElementIsVisibleOfOnboardingPage("spinner");
			verifyElementsOfOnboardingPage("hpPrivacyStatementPageHeading");
			urlCheck=urlCheck && matchTextOfOnboardingPage("hpPrivacyStatementPageHeading","HP Privacy Statement");
			switchToDifferentTabOfOnboardingPage();
		} catch (Exception e) {
			LOGGER.error("Exception in verifyRedirectionForTermsAndCondition" + e.getMessage());
			urlCheck=false;
			printButtonCheck = false;
		}
		return urlCheck&&printButtonCheck;
	}
	
	/**
	 * This method waits till the required element is visible
	 * @param key-Element to wait for
	 * @throws Exception
	 */
	public final void waitUntilElementIsVisibleOfOnboardingPage(String key) throws Exception {
		waitUntilElementIsVisible(onboardingPageProperties.getProperty(key));
	}
	
	/**
	 * This method switches the focus to another window opened
	 * @throws Exception
	 */
	public final void switchToDifferentTabOfOnboardingPage() throws Exception {
		switchToDifferentTab();
	}
	
	/**
	 * This method switches the focus back on previously opened window
	 * @throws Exception
	 */
	public final void switchBackToPreviousTabOfOnboardingPage() throws Exception {
		switchBackToPreviousTab();
	}
public final void addCompanies(String firstName, String lastName, String compEmail, String phoneNumber) throws Exception {
		
		LOGGER.info("Add Company Information screen");
		enterTextForOnboardingPage("compName", CommonVariables.FIRSTNAME);
		enterTextForOnboardingPage("lastName", CommonVariables.LAST_NAME);
		enterTextForOnboardingPage("compEmail", compEmail);
		enterTextForOnboardingPage("phoneNumber", CommonVariables.PHONE_NUMBER);
	}

public final void addPartner(String partnerName, String partnerFirstName1,String partnerLastName, String partnerEmail, String phoneNumber) throws Exception {
	
	LOGGER.info("Add Company Information screen");
	enterTextForOnboardingPage("partnerName", OnboardingVariables.PARTNERNAME);
	enterTextForOnboardingPage("partnerFirstName1", OnboardingVariables.FIRSTNAME);
	enterTextForOnboardingPage("partnerLastName", OnboardingVariables.LASTNAME);
	enterTextForOnboardingPage("partnerEmail", partnerEmail);
	enterTextForOnboardingPage("partnerPhoneNumber", OnboardingVariables.PHONE_NUMBER);
}
	
}