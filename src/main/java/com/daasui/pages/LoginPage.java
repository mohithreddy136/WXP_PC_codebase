package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class LoginPage extends CommonMethod {
	private Properties selectedLanguageProperties;
	private ObjectReader loginPagePropertiesReader = new ObjectReader();
	private Properties loginPagePropertiesPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private LoginPage instance;
	public static String environment, URL;

	public LoginPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (LoginPage.class) {
				if (instance == null) {
					instance = new LoginPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public LoginPage(WebDriver driver) throws IOException {
		loginPagePropertiesPageProperties = loginPagePropertiesReader.getObjectRepository("LoginPageWEX");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = loginPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	public final boolean verifyElementsOfLoginPage(String key) throws Exception {
		return verifyElementIsPresent(loginPagePropertiesPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfLoginPage(String key) throws Exception {
		return verifyElementIsVisible(loginPagePropertiesPageProperties.getProperty(key));
	}
	
	public final boolean waitForElementsOfLoginPageDynamic(String key,int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(loginPagePropertiesPageProperties.getProperty(key),waitTime);
	}
	
	public final boolean matchTextOfLoginPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(loginPagePropertiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfLoginPage(String key) throws Exception {
		return verifyElementIsEnable(loginPagePropertiesPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfLoginPage(String key) throws Exception {
		return verifyElementIsSelected(loginPagePropertiesPageProperties.getProperty(key));
	}

	public final String getTextOfLoginPage(String key) throws Exception {
		return getTextBy(loginPagePropertiesPageProperties.getProperty(key));
	}

	public final String getAttributesOfLoginPage(String key, String value) throws Exception {
		return getAttribute(loginPagePropertiesPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfLoginPage(String key) throws Exception {
		click(loginPagePropertiesPageProperties.getProperty(key));
	}

	public final void enterTextForLoginPage(String key, String Text) throws Exception {
		enterTextLogin(loginPagePropertiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfLoginPage(String key) throws Exception {
		return verifyElementIsClickable(loginPagePropertiesPageProperties.getProperty(key));
	}
	
	public final void clickByJavaScriptOfLoginPage(String key) throws Exception {
		clickByJavaScript(loginPagePropertiesPageProperties.getProperty(key));
	}

	public final void waitUntilElementIsInvisibleOfLoginPage(String key) throws Exception {
		verifyElementIsinvisibile(loginPagePropertiesPageProperties.getProperty(key));
	}
}
