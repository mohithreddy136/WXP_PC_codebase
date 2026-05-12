package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class AmazonSSOPage extends CommonMethod {

	private ObjectReader AmazonSSOPagePropertiesReader = new ObjectReader();
	private Properties AmazonSSOPageProperties;
	private Properties selecteCredentialsProperties;
	public static String environment;

	private AmazonSSOPage instance;
	public static String uiVersion = System.getProperty("uiVersion");

	public AmazonSSOPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (AmazonSSOPage.class) {
				if (instance == null) {
					instance = new AmazonSSOPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public AmazonSSOPage(WebDriver driver) throws IOException {
		AmazonSSOPageProperties = AmazonSSOPagePropertiesReader.getObjectRepository("AmazonSSOPageV3");
	}

	public final String getCredentials(String credentials, String key) throws Exception {
		selecteCredentialsProperties = AmazonSSOPagePropertiesReader.getCredentials(credentials);
		return selecteCredentialsProperties.getProperty(key);
	}

	public final void clickByJavaScriptOnAmazonSsoPage(String key) {
		try {
			clickByJavaScript(AmazonSSOPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnAmzonSSOPage " + e.getMessage()));
		}
	}

	public final void enterTextForAmazonSsoPage(String key, String Text) throws Exception {
		enterText(AmazonSSOPageProperties.getProperty(key), Text);

	}
	public final boolean verifyElementsOfAmazonSsoPage(String key) throws Exception {
		return verifyElementIsPresent(AmazonSSOPageProperties.getProperty(key));
	}
}

