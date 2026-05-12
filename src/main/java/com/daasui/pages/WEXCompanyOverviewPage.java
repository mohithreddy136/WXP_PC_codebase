package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;


public class WEXCompanyOverviewPage extends CommonMethod {
	private WEXCompanyOverviewPage instance;
	private ObjectReader WEXCompanyOverviewPropertiesReader = new ObjectReader();
	private Properties WEXCompanyOverviewProperties;
	
	
	public WEXCompanyOverviewPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (SettingsPage.class) {
				if (instance == null) {
					instance = new WEXCompanyOverviewPage(DRIVER);
				}
			}
		}
		return instance;
	}
	
	public final String getTextOfWEXHelpAndSupportPage(String key) {
		try {
			return getTextBy(WEXCompanyOverviewProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfSystemRequirementsPage " + e.getMessage()));
			return null;
		}
	}
	
	public WEXCompanyOverviewPage(WebDriver driver) throws IOException {
		WEXCompanyOverviewProperties = WEXCompanyOverviewPropertiesReader.getObjectRepository("WEXCompanyOverview");
	}
	
	public final boolean matchTextOfWEXCompanyOverviewPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEXCompanyOverviewProperties.getProperty(key), Text);
	}
	
	public final boolean verifyElementsOfWEXCompanyOverviewPage(String key) throws Exception {
		return verifyElementIsPresent(WEXCompanyOverviewProperties.getProperty(key));
	}
	
	public final void clickOnElementsOfWEXCompanyOverviewPage(String key) throws Exception {
		click(WEXCompanyOverviewProperties.getProperty(key));
	}
	
	public final void clickOnWebElementsOfWEXCompanyOverviewPage(WebElement key) throws Exception {
		clickWebelement(key);
	}
	
	public final void mouseHoverclickOfWEXCompanyOverviewPage(WebElement key) throws Exception {
		mouseHoverclick(key);
	}
	
	public final WebElement getElementOfWEXCompanyOverviewPage(String key) throws Exception {
		return getElement(WEXCompanyOverviewProperties.getProperty(key));
	}
	
	public final boolean waitForElementsOfWEXCompanyOverviewPage(String key) throws Exception {
		return verifyElementIsVisible(WEXCompanyOverviewProperties.getProperty(key));
	}
	
	public final void scrollTillViewWEXCompanyOverviewPage(String locator) throws Exception {
		scrollTillView(WEXCompanyOverviewProperties.getProperty(locator));
	}
	
	public final boolean selectTextValueFromDropdownOfWEXCompanyOverviewPage(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		return selectTextValueFromDropdown(WEXCompanyOverviewProperties.getProperty(dropdownListKey), elementText, WEXCompanyOverviewProperties.getProperty(dropdownBox));
	}
	
	public final void SelectTextPresentInDropdownOfWEXCompanyOverviewPage(String locator, String text) throws Exception{
	   SelectTextPresentInDropdown(WEXCompanyOverviewProperties.getProperty(locator), text);
	}
	
	public final void enterTextForWEXCompanyOverviewPage(String key, String Text) throws Exception {
		enterText(WEXCompanyOverviewProperties.getProperty(key), Text);
	}
	
	
}
