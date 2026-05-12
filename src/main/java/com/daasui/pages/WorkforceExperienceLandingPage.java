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

public class WorkforceExperienceLandingPage  extends CommonMethod {
	
	private WorkforceExperienceLandingPage instance;
	private ObjectReader workforceExperiencelandingPagePropertiesReader = new ObjectReader();
	private Properties workforceExperiencelandingPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	
	public WorkforceExperienceLandingPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WorkforceExperienceLandingPage.class) {
				if (instance == null) {
					instance = new WorkforceExperienceLandingPage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public WorkforceExperienceLandingPage(WebDriver driver) throws IOException {
		workforceExperiencelandingPageProperties = workforceExperiencelandingPagePropertiesReader.getObjectRepository("workforceExperiencelandingPage");
	}

	public final boolean verifyElementsOfLandingpagePage(String key) throws Exception {
		return verifyElementIsPresent(workforceExperiencelandingPageProperties.getProperty(key));
	}
	
	public final WebElement getElementOfLandingPage(String key) throws Exception {
		return getElement(workforceExperiencelandingPageProperties.getProperty(key));
	}
	
	public final List<WebElement> getllAllElementsVisibleofLandingPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(workforceExperiencelandingPageProperties.getProperty(key));
	}
	
	public final boolean verifyElementIsClickableOfLandingPage(String key) throws Exception {
		return verifyElementIsClickable(workforceExperiencelandingPageProperties.getProperty(key));
	}

	public final void scrollOnLandingPage(String key) throws Exception {
		scrollTillView(workforceExperiencelandingPageProperties.getProperty(key));
	}
	
	public final String getTextOfLandingPage(String key) throws Exception {
		return getTextBy(workforceExperiencelandingPageProperties.getProperty(key));
	}
	
	public final void clickOnElementsOfLandingPage(String key) throws Exception {
		click(workforceExperiencelandingPageProperties.getProperty(key));
	}

	public void switchToIframeofOfLandingPage(String key) throws Exception {
		switchToIframe(workforceExperiencelandingPageProperties.getProperty(key));
	}

	public void switchToDefaultContentofOfLandingPage() throws Exception {
		switchToDefaultContent();
	}

	public final void selectValuesFromDropDown(String key, String textToMatch) throws Exception {
		selecValueFromDropdown(workforceExperiencelandingPageProperties.getProperty(key), textToMatch);
	}
	
	public final boolean waitForElementsOfLandingPageDynamic(String key,int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(workforceExperiencelandingPageProperties.getProperty(key),waitTime);
	} 
	
	public final void switchToIframeLandingPage(String key) throws Exception {
		switchToIframe(workforceExperiencelandingPageProperties.getProperty(key));
	}
	
	public final void switchToIDefaultContentLandingPage() throws Exception {
		getDriver().switchTo().defaultContent();

	}
	
	public final boolean matchTextOnLandingpage(String key, String textToMatch) throws Exception {
		return verifyTextPresentOnElement(workforceExperiencelandingPageProperties.getProperty(key), textToMatch);
	}
	
	public final void enterTextForLandingpage(String key, String Text) throws Exception {
		enterText(workforceExperiencelandingPageProperties.getProperty(key), Text);
	}
	
	public final void clickByJavaScriptOnSignUpPage(String key) throws Exception {
		clickByJavaScript(workforceExperiencelandingPageProperties.getProperty(key));
	}
	
	public final String selectFirstValueFromDropdownOnLandingpage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getllAllElementsVisibleofLandingPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(1).click();
		return text;
	}
	
	public final void mouseHoverOnLandingpage(String key) throws Exception {
		mouseHover(workforceExperiencelandingPageProperties.getProperty(key));
	}


}
