package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class OnboardingChecklistFlowPage extends CommonMethod {
	
	private OnboardingChecklistFlowPage instance;
	private ObjectReader OnboardingChecklistFlowPropertiesReader = new ObjectReader();
	private Properties OnboardingChecklistFlowProperties;
	
	
	public OnboardingChecklistFlowPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (SettingsPage.class) {
				if (instance == null) {
					instance = new OnboardingChecklistFlowPage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public final String getTextOfOnboardingChecklistFlowPage(String key) {
		try {
			return getTextBy(OnboardingChecklistFlowProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfSystemRequirementsPage " + e.getMessage()));
			return null;
		}
	}
	
	public OnboardingChecklistFlowPage(WebDriver driver) throws IOException {
		OnboardingChecklistFlowProperties = OnboardingChecklistFlowPropertiesReader.getObjectRepository("OnboardingChecklistFlow");
	}
	
	public final boolean matchTextOfOnboardingChecklistFlowPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(OnboardingChecklistFlowProperties.getProperty(key), Text);
	}
	
	public final boolean verifyElementsOfOnboardingChecklistFlowPage(String key) throws Exception {
		return verifyElementIsPresent(OnboardingChecklistFlowProperties.getProperty(key));
	}
    
	public final void clickOnElementsOfOnboardingChecklistFlowPage(String key) throws Exception {
		click(OnboardingChecklistFlowProperties.getProperty(key));
	}
	
	public final void clickOnElementByJavaScriptOfOnboardingChecklistFlowPage(String key) throws Exception {
		clickByJavaScript(OnboardingChecklistFlowProperties.getProperty(key));
	}
	
	public final void clickOnWebElementsOfOnboardingChecklistFlowPage(WebElement key) throws Exception {
		clickWebelement(key);
	}
	
	public final void mouseHoverclickOfOnboardingChecklistFlowPage(WebElement key) throws Exception {
		mouseHoverclick(key);
	}
	
	public final WebElement getElementOfWEXOnboardingChecklistFlowPage(String key) throws Exception {
		return getElement(OnboardingChecklistFlowProperties.getProperty(key));
	}
	
	public final WebElement verifyElementIsVisibleOfWEXOnboardingChecklistFlowPage(String key) throws Exception {
		verifyElementIsVisible(OnboardingChecklistFlowProperties.getProperty(key));
		return null;
	}
	
	public final void scrollTillViewWEXOnboardingChecklistFlowPage(String locator) throws Exception {
		scrollTillView(OnboardingChecklistFlowProperties.getProperty(locator));
	}
	
	public final boolean waitForElementsOfWEXOnboardingChecklistFlowPage(String key) {
        try {
            return verifyElementIsVisible(OnboardingChecklistFlowProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }
	
	public final boolean verifyElementIsClickableInOnboardingChecklistFlowPage(String key) throws Exception {
		return verifyElementIsEnable(OnboardingChecklistFlowProperties.getProperty(key));
	}

	public final WebElement getElementOfOnboardingChecklistFlowPage(String key) throws Exception {
		return getElement(OnboardingChecklistFlowProperties.getProperty(key));
	}

	public void importScriptFile(String fileName) throws Exception {
		sleeper(2000);
		WebElement addFile = getElementOfOnboardingChecklistFlowPage("browseInputaddscript");
		addFile.sendKeys(fileName);
		//clickByJavaScriptOnScriptsPage("uploadBulkDeviceBtn");
		sleeper(3000);
	}

	public final void enterTextOnScriptsPageOfOnboardingChecklistFlowPage(String key, String Text) throws Exception {
		enterText(OnboardingChecklistFlowProperties.getProperty(key), Text);
	}
}
