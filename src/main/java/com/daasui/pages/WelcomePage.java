package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

/**
 * This class contain method for welcome tests
 */

import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class WelcomePage extends CommonMethod {
	private ObjectReader welcomePagePropertiesReader = new ObjectReader();
	private Properties welcomePagePropertiesPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private WelcomePage instance;
	public static String environment, URL;

	public WelcomePage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WelcomePage.class) {
				if (instance == null) {
					instance = new WelcomePage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WelcomePage(WebDriver driver) throws IOException {
		welcomePagePropertiesPageProperties = welcomePagePropertiesReader.getObjectRepository("LoginPageWEX");
	}

	public final boolean verifyElementsOfWelcomePage(String key) throws Exception {
		return verifyElementIsPresent(welcomePagePropertiesPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfWelcomePage(String key) throws Exception {
		return verifyElementIsVisible(welcomePagePropertiesPageProperties.getProperty(key));
	}
	
	public final boolean waitForElementsOfWelcomePageDynamic(String key,int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(welcomePagePropertiesPageProperties.getProperty(key),waitTime);
	} 

	public final boolean matchTextOfWelcomePage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(welcomePagePropertiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfWelcomePage(String key) throws Exception {
		return verifyElementIsEnable(welcomePagePropertiesPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfWelcomePage(String key) throws Exception {
		return verifyElementIsSelected(welcomePagePropertiesPageProperties.getProperty(key));
	}

	public final String getTextOfWelcomePage(String key) throws Exception {
		return getTextBy(welcomePagePropertiesPageProperties.getProperty(key));
	}

	public final String getAttributesOfWelcomePage(String key, String value) throws Exception {
		return getAttribute(welcomePagePropertiesPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfWelcomePage(String key) throws Exception {
		click(welcomePagePropertiesPageProperties.getProperty(key));
	}

	public final void enterTextForWelcomePage(String key, String Text) throws Exception {
		enterText(welcomePagePropertiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfWelcomePage(String key) throws Exception {
		return verifyElementIsClickable(welcomePagePropertiesPageProperties.getProperty(key));
	}

	public final void scrollOnWelcomePage(String key) throws Exception {
		scrollTillView(welcomePagePropertiesPageProperties.getProperty(key));
	}
}
