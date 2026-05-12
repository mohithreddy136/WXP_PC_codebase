package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

/**
 * This class contains methods related to gmail login in DaaS
 * 
 * @author ajit
 *
 */
public class GmailLoginPage extends CommonMethod {
	private ObjectReader gmailLoginPagePropertiesReader = new ObjectReader();
	private Properties gmailLoginPagePropertiesPageProperties;

	private GmailLoginPage instance;

	public GmailLoginPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (GmailLoginPage.class) {
				if (instance == null) {
					instance = new GmailLoginPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public GmailLoginPage(WebDriver driver) throws IOException {
		gmailLoginPagePropertiesPageProperties = gmailLoginPagePropertiesReader.getObjectRepository("GmailFacebookHPLoginPage");
	}

	public final boolean verifyElementsOfGmailLoginPage(String key) throws Exception {
		return verifyElementIsPresent(gmailLoginPagePropertiesPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfGmailLoginPage(String key) throws Exception {
		return verifyElementIsVisible(gmailLoginPagePropertiesPageProperties.getProperty(key));
	}

	public final boolean matchTextOfGmailLoginPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(gmailLoginPagePropertiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfGmailLoginPage(String key) throws Exception {
		return verifyElementIsEnable(gmailLoginPagePropertiesPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfGmailLoginPage(String key) throws Exception {
		return verifyElementIsSelected(gmailLoginPagePropertiesPageProperties.getProperty(key));
	}

	public final String getTextOfGmailLoginPage(String key) throws Exception {
		return getTextBy(gmailLoginPagePropertiesPageProperties.getProperty(key));
	}

	public final String getAttributesOfGmailLoginPage(String key, String value) throws Exception {
		return getAttribute(gmailLoginPagePropertiesPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfGmailLoginPage(String key) throws Exception {
		click(gmailLoginPagePropertiesPageProperties.getProperty(key));
	}

	public final void enterTextForGmailLoginPage(String key, String Text) throws Exception {
		enterText(gmailLoginPagePropertiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfGmailLoginPage(String key) throws Exception {
		return verifyElementIsClickable(gmailLoginPagePropertiesPageProperties.getProperty(key));
	}
}
