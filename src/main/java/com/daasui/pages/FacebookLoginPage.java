package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

/**
 * This class contains methods related to facebook login in DaaS
 * 
 * @author ajit
 *
 */
public class FacebookLoginPage extends CommonMethod {
	private ObjectReader facebookLoginPagePropertiesReader = new ObjectReader();
	private Properties facebookLoginPagePropertiesPageProperties;

	private FacebookLoginPage instance;

	public FacebookLoginPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (FacebookLoginPage.class) {
				if (instance == null) {
					instance = new FacebookLoginPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public FacebookLoginPage(WebDriver driver) throws IOException {
		facebookLoginPagePropertiesPageProperties = facebookLoginPagePropertiesReader.getObjectRepository("GmailFacebookHPLoginPage");
	}

	public final boolean verifyElementsOfFacebookLoginPage(String key) throws Exception {
		return verifyElementIsPresent(facebookLoginPagePropertiesPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfFacebookLoginPage(String key) throws Exception {
		return verifyElementIsVisible(facebookLoginPagePropertiesPageProperties.getProperty(key));
	}

	public final boolean matchTextOfFacebookLoginPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(facebookLoginPagePropertiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfFacebookLoginPage(String key) throws Exception {
		return verifyElementIsEnable(facebookLoginPagePropertiesPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfFacebookLoginPage(String key) throws Exception {
		return verifyElementIsSelected(facebookLoginPagePropertiesPageProperties.getProperty(key));
	}

	public final String getTextOfFacebookLoginPage(String key) throws Exception {
		return getTextBy(facebookLoginPagePropertiesPageProperties.getProperty(key));
	}

	public final String getAttributesOfFacebookLoginPage(String key, String value) throws Exception {
		return getAttribute(facebookLoginPagePropertiesPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfFacebookLoginPage(String key) throws Exception {
		click(facebookLoginPagePropertiesPageProperties.getProperty(key));
	}

	public final void enterTextForFacebookLoginPage(String key, String Text) throws Exception {
		enterText(facebookLoginPagePropertiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfFacebookLoginPage(String key) throws Exception {
		return verifyElementIsClickable(facebookLoginPagePropertiesPageProperties.getProperty(key));
	}
}
