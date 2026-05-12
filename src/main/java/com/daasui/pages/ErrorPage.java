package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

/**
 * This class contains methods related error page. this page displayed when some issue occurred in login to DaaS
 * 
 * @author ajit
 *
 */
public class ErrorPage extends CommonMethod {
	private ObjectReader errorPagePropertiesReader = new ObjectReader();
	private Properties errorPageProperties;

	private ErrorPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	public ErrorPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (ErrorPage.class) {
				if (instance == null) {
					instance = new ErrorPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public ErrorPage(WebDriver driver) throws IOException {
		errorPageProperties = errorPagePropertiesReader.getObjectRepository("ErrorPageV3");
	}

	public final boolean verifyElementsOfErrorPage(String key) throws Exception {
		return verifyElementIsPresent(errorPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfErrorPage(String key) throws Exception {
		return verifyElementIsVisible(errorPageProperties.getProperty(key));
	}

	public final boolean matchTextOfErrorPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(errorPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfErrorPage(String key) throws Exception {
		return verifyElementIsEnable(errorPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfErrorPage(String key) throws Exception {
		return verifyElementIsSelected(errorPageProperties.getProperty(key));
	}

	public final String getTextOfErrorPage(String key) throws Exception {
		return getTextBy(errorPageProperties.getProperty(key));
	}

	public final String getAttributesOfErrorPage(String key, String value) throws Exception {
		return getAttribute(errorPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfErrorPage(String key) throws Exception {
		click(errorPageProperties.getProperty(key));
	}

	public final void enterTextForErrorPage(String key, String Text) throws Exception {
		enterText(errorPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfErrorPage(String key) throws Exception {
		return verifyElementIsClickable(errorPageProperties.getProperty(key));
	}

}
