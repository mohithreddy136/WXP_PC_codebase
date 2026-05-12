package com.daasui.pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;

import com.basesource.action.CommonMethod;
import com.basesource.action.MailinatorMail;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CSVFileReader;
import com.basesource.utils.EnrollFakeDevice;
import com.basesource.utils.Mailinator;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.SettingsVariables;
import com.google.common.base.Strings;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SettingsPage extends CommonMethod {
	private Properties selectedLanguageProperties;
	private ObjectReader settingsPagePropertiesReader = new ObjectReader();
	private Properties settingsPageProperties;
	private ObjectReader environmentPropertiesReader = new ObjectReader();
	private Properties environmentProperties;
	public static String partnerEmail = getEnvironmentSpecificData(System.getProperty("environment"), "HELP_AND_SUPPORT_PARTNER_EMAIL");
	public static String mspEmail = getEnvironmentSpecificData(System.getProperty("environment"), "HELP_AND_SUPPORT_MSP_EMAIL");

	private SettingsPage instance;

	public static String createdRole = null;
	public static String createdBaseRole = null;
	public static String uiVersion = System.getProperty("uiVersion");

	public SettingsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (SettingsPage.class) {
				if (instance == null) {
					instance = new SettingsPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public SettingsPage(WebDriver driver) throws IOException {
		environmentProperties = environmentPropertiesReader.getObjectRepository("Environment");
		settingsPageProperties = settingsPagePropertiesReader.getObjectRepository("SettingsPageV3");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = settingsPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	public final boolean verifyElementsOfSettingsPage(String key) throws Exception {
		return verifyElementIsPresent(settingsPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfSettingsPage(String key) throws Exception {
		return verifyElementIsVisible(settingsPageProperties.getProperty(key));
	}
	
	public final boolean waitForPresenceOfElementsOfSettingsPage(String key) throws Exception {
		return waitUntillElementIsPresent(settingsPageProperties.getProperty(key));
	}

	public final boolean matchTextOfSettingsPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(settingsPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfSettingsPage(String key) throws Exception {
		return verifyElementIsEnable(settingsPageProperties.getProperty(key));
	}

	public final String getTextOfSettingsPage(String key) throws Exception {
		return getTextBy(settingsPageProperties.getProperty(key));
	}

	public final void clearTextOnSettingsPage(String key) throws Exception {
		clearText(settingsPageProperties.getProperty(key));
	}

	public final String getAttributesOfSettingsPage(String key, String value) throws Exception {
		return getAttribute(settingsPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfSettingsPage(String key) throws Exception {
		click(settingsPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnSettingsPage(String key) throws Exception {
		clickByJavaScript(settingsPageProperties.getProperty(key));
	}

	public final void enterTextForSettingsPage(String key, String Text) throws Exception {
		enterText(settingsPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfSettingsPage(String key) throws Exception {
		return verifyElementIsClickable(settingsPageProperties.getProperty(key));
	}

	public final boolean selectElementFromDropDownForSettingsPage(String dropdownId, String key, String text) throws Exception {
		return selectFromDropdown(settingsPageProperties.getProperty(dropdownId), settingsPageProperties.getProperty(key), text);
	}
	
	public final boolean selectTextValueFromDropdownOfSettingsPage(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		return selectTextValueFromDropdown(settingsPageProperties.getProperty(dropdownListKey), elementText, settingsPageProperties.getProperty(dropdownBox));
	}

	public final void MoveoverElementForSettingsPage(String key) throws Exception {
		moveToElements(settingsPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsOfSettingsPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(settingsPageProperties.getProperty(key));
	}

	public final WebElement getElementOfSettingsPage(String key) throws Exception {
		return getElement(settingsPageProperties.getProperty(key));
	}

	public final void clearTextRefreshOfSettingPage(String key) throws Exception {
		clearTextRefresh(settingsPageProperties.getProperty(key));
	}

	public final void gotoPreferencesTab() throws Exception {
		clickOnElementsOfSettingsPage("preferencesTab");
	}

	public final void scrollTillViewSettingsPage(String locator) throws Exception {
		scrollTillView(settingsPageProperties.getProperty(locator));
	}

	public final boolean waitForElementsOfSettingsPageDynamic(String key, int wait) throws Exception {
		return verifyElementIsVisibleDynamic(settingsPageProperties.getProperty(key), wait);
	}

	public final boolean waitForElementsOfSettingsPageForinvisibile(String key) throws Exception {
		return verifyElementIsinvisibile(settingsPageProperties.getProperty(key));
	}
	
	public final boolean verifySingleSelectDropdownTextSettingsPage(String key, String text) throws Exception{
		return selectDropdownOptions(settingsPageProperties.getProperty(key),text);
	}
	/**
	 * This is the method to clear text refresh from an element present
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void clearTextRefreshFromsettingPageTextField(String key) throws Exception {
		clearTextRefresh(settingsPageProperties.getProperty(key));
	}
	
	/**
	 * This method Enables service now configuration at tenant level
	 * 
	 * @param url- service now instance url
	 * @param userName - service now instance username
	 * @param password- service now instance password
	 * @return return true if tenant configured SNOW successfully else return false
	 * @throws Exception
	 */
	public final boolean enableServiceNowConfigurationAtTenantLevel(String LanguageCode, String url, String userName, String password) throws Exception {
		waitForElementsOfSettingsPage("editServiceNow");
		String serviceNowStatus = getTextOfSettingsPage("serviceNowStatus");
		if (serviceNowStatus.contains(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			return true;
		} else if (serviceNowStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "global.disabled"))) {
			clickOnElementsOfSettingsPage("editServiceNow");
			clickOnElementsOfSettingsPage("serviceNowToggle");
			clickOnElementsOfSettingsPage("saveServiceNowConfig");
			verifyElementIsClickableOfSettingsPage("editServiceNow");
			serviceNowStatus = getTextOfSettingsPage("serviceNowStatus");
			if (serviceNowStatus.contains(getTextLanguage(LanguageCode, "daas_ui", "global.enabled")))
				return true;
			else
				return false;

		} else {
			clickOnElementsOfSettingsPage("editServiceNow");
			clickOnElementsOfSettingsPage("serviceNowToggle");
			enterTextForSettingsPage("serviceNowUrl", url);
			enterTextForSettingsPage("serviceNowUserName", userName);
			enterTextForSettingsPage("serviceNowUserPassword", password);
			clickOnElementsOfSettingsPage("saveServiceNowConfig");
			verifyElementIsClickableOfSettingsPage("editServiceNow");
			serviceNowStatus = getTextOfSettingsPage("serviceNowStatus");
			if (serviceNowStatus.contains(getTextLanguage(LanguageCode, "daas_ui", "global.enabled")))
				return true;
			else
				return false;
		}
	}

	/**
	 * This method disables tenant level SNOW configuration
	 * 
	 * @param LanguageCode - language code to check current language and check for the localization
	 * @return - returns true id tenant level SNOW configuration disable successfully
	 */
	public final boolean disableServiceNowConfigurationAtTenantLevel(String LanguageCode) throws Exception {
		waitForElementsOfSettingsPage("editServiceNow");
		String serviceNowStatus = getTextOfSettingsPage("serviceNowStatus");
		if (serviceNowStatus.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.disabled")) || serviceNowStatus.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))) {
			return true;
		} else {
			clickOnElementsOfSettingsPage("editServiceNow");
			clickOnElementsOfSettingsPage("serviceNowToggle");
			clickOnElementsOfSettingsPage("saveServiceNowConfig");
			verifyElementIsClickableOfSettingsPage("editServiceNow");
			serviceNowStatus = getTextOfSettingsPage("serviceNowStatus");
			if (serviceNowStatus.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.disabled")) || serviceNowStatus.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured")))
				return true;
			else
				return false;
		}
	}

	public final void gotoUserSettingsTab() throws Exception {
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (!dashboardPage.verifyElementsOfDashboardPage("dashboardTab")) {
			dashboardPage.clickOnElementsOfDashboardPage("menuIcon");
		}
		clickOnElementsOfSettingsPage("settingsTabUser");
	}

	/**
	 * This method is used to validate working of callback support on settings page.
	 * 
	 * @param LanguageCode : Language code is used for multiple languages code.
	 * @param textKey : Locator for status text of callback.
	 * @param callbackToggleKey : Locator for toggle of callback.
	 * @return true or false
	 * @throws Exception
	 */
	public final boolean verifyCallbackFunctionality(String LanguageCode, String textKey, String callbackToggleKey) throws Exception {
		boolean flag = false;
		String text = null;
		text = getTextOfSettingsPage(textKey);
		if (text.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
			clickOnElementsOfSettingsPage(callbackToggleKey);
			text = getTextOfSettingsPage(textKey);
			if (text.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
				flag = true;
			}
		} else {
			clickOnElementsOfSettingsPage(callbackToggleKey);
			text = getTextOfSettingsPage(textKey);
			if (text.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * This method is used to define parameters of direct support.
	 * 
	 * @return
	 */
	public final HashMap<String, String> getDirectSupportDetails() throws Exception {
		HashMap<String, String> directSupportInfo = new HashMap<String, String>();
		directSupportInfo.put("statusTextOfDirectSupportKey", "statusTextOfDirectSupport");
		directSupportInfo.put("phoneNumberTextDirectSupportKey", "phoneNumberTextDirectSupport");
		directSupportInfo.put("closeDirectSupportIconKey", "closeDirectSupportIcon");
		directSupportInfo.put("toggleDirectSupportKey", "toggleDirectSupport");
		directSupportInfo.put("editIconDirectSupportKey", "editIconDirectSupport");
		directSupportInfo.put("saveDirectSupportKey", "saveDirectSupport");
		directSupportInfo.put("cancelDirectSupportKey", "cancelDirectSupport");
		directSupportInfo.put("phoneNumberBoxDirectSupportKey", "phoneNumberBoxDirectSupport");
		directSupportInfo.put("successNotificationKey", "successNotification");
		directSupportInfo.put("phoneNumberBoxKey", "phoneNumberBox");
		directSupportInfo.put("successNotificationCloseKey", "successNotificationClose");
		return directSupportInfo;
	}

	/**
	 * This method is used to validate working of toggle of direct support(Save Button) on settings page.
	 * 
	 * @param LanguageCode : Language code is used for multiple languages code.
	 * @param directSupportInfo : Used to provide parameters.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyToggleForDirectSupportSave(String LanguageCode, HashMap<String, String> directSupportInfo) throws Exception {
		boolean flag = false;
		String statusText = null;
		String phoneNumberText = null;
		String phoneNumber = "454545454512";
		statusText = getTextOfSettingsPage(directSupportInfo.get("statusTextOfDirectSupportKey"));
		if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
			verifyElementsOfSettingsPage(directSupportInfo.get("editIconDirectSupportKey"));
			clickOnElementsOfSettingsPage(directSupportInfo.get("editIconDirectSupportKey"));
			// sleeper(2000);
			waitForPageLoaded();
			clickOnElementsOfSettingsPage(directSupportInfo.get("toggleDirectSupportKey"));
			// sleeper(2000);
			waitForPageLoaded();
			waitForElementsOfSettingsPage(directSupportInfo.get("phoneNumberBoxDirectSupportKey"));
			enterTextForSettingsPage(directSupportInfo.get("phoneNumberBoxDirectSupportKey"), phoneNumber);
			clickOnElementsOfSettingsPage(directSupportInfo.get("saveDirectSupportKey"));
			sleeper(2000);
			waitForPageLoaded();
			statusText = getTextOfSettingsPage(directSupportInfo.get("statusTextOfDirectSupportKey"));
			phoneNumberText = getTextOfSettingsPage(directSupportInfo.get("phoneNumberTextDirectSupportKey"));
			waitForElementsOfSettingsPage(directSupportInfo.get("successNotificationKey"));
			clickOnElementsOfSettingsPage(directSupportInfo.get("successNotificationCloseKey"));
			if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled")) && phoneNumberText.equalsIgnoreCase(phoneNumberText)) {
				flag = true;
			}
		} else if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
			waitForPageLoaded();
			// sleeper(2000);
			waitForElementsOfSettingsPage("editIconDirectSupport");
			clickOnElementsOfSettingsPage(directSupportInfo.get("editIconDirectSupportKey"));
			// sleeper(2000);
			waitForPageLoaded();
			clickOnElementsOfSettingsPage(directSupportInfo.get("toggleDirectSupportKey"));
			waitForElementsOfSettingsPage(directSupportInfo.get("saveDirectSupportKey"));
			clickOnElementsOfSettingsPage(directSupportInfo.get("saveDirectSupportKey"));
			// sleeper(2000);
			waitForPageLoaded();
			statusText = getTextOfSettingsPage(directSupportInfo.get("statusTextOfDirectSupportKey"));
			if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * This method is used to validate working of toggle of direct support (cancel button) on settings page.
	 * 
	 * @param LanguageCode : Language code is used for multiple languages code.
	 * @param directSupportInfo: Used to provide parameters.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyToggleForDirectSupportCancel(String LanguageCode, HashMap<String, String> directSupportInfo) throws Exception {
		boolean flag = false;
		String statusText = null;
		String phoneNumber = "454545454513";
		statusText = getTextOfSettingsPage(directSupportInfo.get("statusTextOfDirectSupportKey"));
		if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
			clickOnElementsOfSettingsPage(directSupportInfo.get("editIconDirectSupportKey"));
			sleeper(2000);
			clickOnElementsOfSettingsPage(directSupportInfo.get("toggleDirectSupportKey"));
			waitForElementsOfSettingsPage(directSupportInfo.get("phoneNumberBoxDirectSupportKey"));
			enterTextForSettingsPage(directSupportInfo.get("phoneNumberBoxDirectSupportKey"), phoneNumber);
			clickOnElementsOfSettingsPage(directSupportInfo.get("cancelDirectSupportKey"));
			sleeper(2000);
			statusText = getTextOfSettingsPage(directSupportInfo.get("statusTextOfDirectSupportKey"));
			if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
				flag = true;
			}
		} else if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
			sleeper(2000);
			clickOnElementsOfSettingsPage(directSupportInfo.get("editIconDirectSupportKey"));
			sleeper(2000);
			clickOnElementsOfSettingsPage(directSupportInfo.get("toggleDirectSupportKey"));
			waitForElementsOfSettingsPage(directSupportInfo.get("cancelDirectSupportKey"));
			clickOnElementsOfSettingsPage(directSupportInfo.get("cancelDirectSupportKey"));
			sleeper(2000);
			statusText = getTextOfSettingsPage(directSupportInfo.get("statusTextOfDirectSupportKey"));
			if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * This method is used to validate working of toggle of direct support on settings page.
	 * 
	 * @param LanguageCode : Language code is used for multiple languages code.
	 * @param directSupportInfo : Used to provide parameters.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyToggleForDirectSupport(String LanguageCode, HashMap<String, String> directSupportInfo) throws Exception {
		boolean flag = false;
		try {
			String statusText = null;
			statusText = getTextOfSettingsPage(directSupportInfo.get("statusTextOfDirectSupportKey"));
			if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
				clickOnElementsOfSettingsPage(directSupportInfo.get("editIconDirectSupportKey"));
				waitForPageLoaded();
				WebElement e = getElementOfSettingsPage(directSupportInfo.get("phoneNumberBoxKey"));
				if (!e.isEnabled()) {
					flag = true;
				} else {
					flag = false;
				}
				clickOnElementsOfSettingsPage("cancelDirectSupport");
			} else if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
				clickOnElementsOfSettingsPage(directSupportInfo.get("editIconDirectSupportKey"));
				waitForPageLoaded();
				clickOnElementsOfSettingsPage(directSupportInfo.get("toggleDirectSupportKey"));
				WebElement e = getElementOfSettingsPage(directSupportInfo.get("phoneNumberBoxKey"));
				if (!e.isEnabled()) {
					flag = true;
				} else {
					flag = false;
				}
				clickOnElementsOfSettingsPage(directSupportInfo.get("saveDirectSupportKey"));
				verifyElementsOfSettingsPage(directSupportInfo.get("successNotificationCloseKey"));
				clickOnElementsOfSettingsPage(directSupportInfo.get("successNotificationCloseKey"));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return flag;
	}

	/**
	 * This method is used to define parameters of email support.
	 * 
	 * @return
	 */
	public final HashMap<String, String> getEmailSupportDetails() throws Exception {
		HashMap<String, String> emailSupportInfo = new HashMap<String, String>();
		emailSupportInfo.put("teamNameTextEmailSupportKey", "teamNameTextEmailSupport");
		emailSupportInfo.put("emailTextEmailSupportKey", "emailTextEmailSupport");
		emailSupportInfo.put("editIconEmailSupportKey", "editIconEmailSupport");
		emailSupportInfo.put("saveEmailSupportKey", "saveEmailSupport");
		emailSupportInfo.put("cancelEmailSupportKey", "cancelEmailSupport");
		emailSupportInfo.put("teamNameBoxEmailSupportKey", "teamNameBoxEmailSupport");
		emailSupportInfo.put("emailBoxEmailSupportKey", "emailBoxEmailSupport");
		emailSupportInfo.put("successNotificationKey", "successNotification");
		emailSupportInfo.put("successNotificationCloseKey", "successNotificationClose");
		return emailSupportInfo;
	}

	/**
	 * This method is used to validate working of toggle of email support(Save Button) on settings page.
	 * 
	 * @param emailSupportInfo : Used to provide parameters.
	 * @param userType : User types :MSP/Partner
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyToggleForEmailSupportSave(HashMap<String, String> emailSupportInfo, String userType) throws Exception {
		boolean flag = false;
		String teamNameText = null;
		String emailText = null,email;
		int randomNumber = 0;
		Random random = new Random();
		randomNumber = random.nextInt(10000);
		String randomString = String.valueOf(randomNumber);
		String teamName = "Test team for email support";
		teamNameText = getTextOfSettingsPage(emailSupportInfo.get("teamNameTextEmailSupportKey"));
		scrollTillViewSettingsPage(emailSupportInfo.get("emailTextEmailSupportKey"));
		emailText = getTextOfSettingsPage(emailSupportInfo.get("emailTextEmailSupportKey"));
		clickOnElementsOfSettingsPage(emailSupportInfo.get("editIconEmailSupportKey"));
		sleeper(2000);
		enterTextForSettingsPage(emailSupportInfo.get("teamNameBoxEmailSupportKey"), teamName + randomString);
		if (userType.equals("MSP")) {
			email = mspEmail ;
			enterTextForSettingsPage(emailSupportInfo.get("emailBoxEmailSupportKey"), email);
		} else {
			email = partnerEmail ;
			enterTextForSettingsPage(emailSupportInfo.get("emailBoxEmailSupportKey"), email);
		}
		waitForElementsOfSettingsPage(emailSupportInfo.get("saveEmailSupportKey"));
		clickOnElementsOfSettingsPage(emailSupportInfo.get("saveEmailSupportKey"));
		sleeper(2000);
		teamNameText = getTextOfSettingsPage(emailSupportInfo.get("teamNameTextEmailSupportKey"));
		emailText = getTextOfSettingsPage(emailSupportInfo.get("emailTextEmailSupportKey"));
		waitForElementsOfSettingsPage(emailSupportInfo.get("successNotificationKey"));
		clickOnElementsOfSettingsPage(emailSupportInfo.get("successNotificationCloseKey"));
		sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
		if (teamNameText.equalsIgnoreCase(teamName + randomString) && emailText.equalsIgnoreCase(email)) {
			flag = true;
		} else {
			LOGGER.error("Email support for save is not working.");
		}
		return flag;
	}

	/**
	 * This method is used to extract email from email support.
	 * 
	 * @param emailSupportInfo : Used to provide parameters.
	 * @param userType : User types :MSP/Partner
	 * @return
	 * @throws Exception
	 */
	public final String extractEmailFromEmailSupport(HashMap<String, String> emailSupportInfo,String userType) throws Exception {
		String[] email = new String[2];
		try {
			boolean flag = verifyToggleForEmailSupportSave(emailSupportInfo,userType);
			String emailFull = null;
			if (flag) {
				emailFull = getTextOfSettingsPage(emailSupportInfo.get("emailTextEmailSupportKey"));
				email = emailFull.split("@");
			}
		} catch (Exception e) {
			LOGGER.error("extractEmailFromEmailSupport failed due to " + e.getMessage());
		}
		return email[0];
	}

	/**
	 * This method is used to validate working of toggle of email support(Save Button) on settings page.
	 * 
	 * @param emailSupportInfo : Used to provide parameters.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyToggleForEmailSupportCancel(HashMap<String, String> emailSupportInfo) throws Exception {
		boolean flag = false;
		String teamNameTextBefore = null;
		String emailTextBefore = null;
		String teamNameTextAfter = null;
		String emailTextAfter = null;
		String teamName = "Test team for email support - do not save";
		String email = "do_not_save@xyz.com";
		teamNameTextBefore = getTextOfSettingsPage(emailSupportInfo.get("teamNameTextEmailSupportKey"));
		emailTextBefore = getTextOfSettingsPage(emailSupportInfo.get("emailTextEmailSupportKey"));
		clickOnElementsOfSettingsPage(emailSupportInfo.get("editIconEmailSupportKey"));
		sleeper(2000);
		enterTextForSettingsPage(emailSupportInfo.get("teamNameBoxEmailSupportKey"), teamName);
		enterTextForSettingsPage(emailSupportInfo.get("emailBoxEmailSupportKey"), email);
		clickOnElementsOfSettingsPage(emailSupportInfo.get("cancelEmailSupportKey"));
		sleeper(2000);
		teamNameTextAfter = getTextOfSettingsPage(emailSupportInfo.get("teamNameTextEmailSupportKey"));
		emailTextAfter = getTextOfSettingsPage(emailSupportInfo.get("emailTextEmailSupportKey"));
		waitForElementsOfSettingsPage(emailSupportInfo.get("successNotificationKey"));
		if (teamNameTextBefore.equalsIgnoreCase(teamNameTextAfter) && emailTextBefore.equalsIgnoreCase(emailTextAfter)) {
			flag = true;
		} else {
			LOGGER.error("Email support for cancel is not working.");
		}
		return flag;
	}

	/**
	 * This method is used to select first value from any single select dropdown of settings page.
	 * 
	 * @param index : Index of element to be selected.
	 * @param elementsKey: List of elements in dropdown.
	 * @param boxKey : Locator of drop down box.
	 * @return
	 * @throws Exception
	 */
	public final boolean selectValueFromSingleSelectDropDownSettingsPage(int index, String elementsKey, String boxKey) throws Exception {
		try {
			boolean flag = false;
			String selectedElement = null;
			String elementToBeSelected = null;
			List<WebElement> listOfOptions = getElementsOfSettingsPage(elementsKey);
			elementToBeSelected = listOfOptions.get(index).getText();
			listOfOptions.get(index).click();
			selectedElement = getTextOfSettingsPage(boxKey);
			if (elementToBeSelected.equalsIgnoreCase(selectedElement)) {
				flag = true;
			}
			if (index > listOfOptions.size() || index < 0) {
				throw new IndexOutOfBoundsException("Please check your index, You can use index within limit of total size of drop down elements. ");
			}
			return flag;
		} catch (IndexOutOfBoundsException e) {
			LOGGER.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method is used to define parameters of country and time zone.
	 * 
	 * @return
	 */
	public final HashMap<String, String> getCountryAndTimeZoneDetails() throws Exception {
		HashMap<String, String> countryAndTimeZoneInfo = new HashMap<String, String>();
		countryAndTimeZoneInfo.put("countryTextKey", "countryText");
		countryAndTimeZoneInfo.put("timeZoneTextKey", "timeZoneText");
		countryAndTimeZoneInfo.put("countriesKey", "listOfCountries");
		countryAndTimeZoneInfo.put("countryBoxKey", "countryBox");
		countryAndTimeZoneInfo.put("timeZonesKey", "listOfTimeZones");
		countryAndTimeZoneInfo.put("timeZoneBoxMSPKey", "timeZoneBoxMSP");
		countryAndTimeZoneInfo.put("saveButtonKey", "saveCountry");
		countryAndTimeZoneInfo.put("cancelButtonKey", "cancelCountry");
		countryAndTimeZoneInfo.put("editIconCountryKey", "editIconCountry");
		countryAndTimeZoneInfo.put("successMessageKey", "successNotification");
		countryAndTimeZoneInfo.put("countryDropdownButtonKey", "countryDropdownbutton");
		countryAndTimeZoneInfo.put("timeZoneDropdownButtonKey", "timeZoneDropdownbutton");
		return countryAndTimeZoneInfo;
	}

	/**
	 * This method is used to validate working of Country and time zone(save) on settings page.
	 * 
	 * @param countryAndTimeZoneInfo: Used to provide parameters.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyCountryAndTimeZoneSave(HashMap<String, String> countryAndTimeZoneInfo) throws Exception {
		boolean flag = false;
		String selectedCountryText = null;
		String selectedTimeZoneText = null;
		String selectedCountry = null;
		String selectedTimeZone = null;
		int randomNumber = 0;
		Random random = new Random();
		randomNumber = random.nextInt(140);
		clickOnElementsOfSettingsPage(countryAndTimeZoneInfo.get("editIconCountryKey"));
		clickOnElementsOfSettingsPage(countryAndTimeZoneInfo.get("countryDropdownButtonKey"));
		selectValueFromSingleSelectDropDownSettingsPage(randomNumber, countryAndTimeZoneInfo.get("countriesKey"), countryAndTimeZoneInfo.get("countryBoxKey"));
		clickOnElementsOfSettingsPage(countryAndTimeZoneInfo.get("timeZoneDropdownButtonKey"));
		selectValueFromSingleSelectDropDownSettingsPage(randomNumber, countryAndTimeZoneInfo.get("timeZonesKey"), countryAndTimeZoneInfo.get("timeZoneBoxMSPKey"));
		selectedCountry = getTextOfSettingsPage(countryAndTimeZoneInfo.get("countryBoxKey"));
		selectedTimeZone = getTextOfSettingsPage(countryAndTimeZoneInfo.get("timeZoneBoxMSPKey"));
		clickOnElementsOfSettingsPage(countryAndTimeZoneInfo.get("saveButtonKey"));
		sleeper(3000);
		waitForElementsOfSettingsPage(countryAndTimeZoneInfo.get("successMessageKey"));
		selectedCountryText = getTextOfSettingsPage(countryAndTimeZoneInfo.get("countryTextKey"));
		selectedTimeZoneText = getTextOfSettingsPage(countryAndTimeZoneInfo.get("timeZoneTextKey"));
		sleeper(3000);
		if (selectedCountry.equalsIgnoreCase(selectedCountryText) && selectedTimeZone.equalsIgnoreCase(selectedTimeZoneText)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * This method is used to validate working of Country and time zone(cancel) on settings page.
	 * 
	 * @param countryAndTimeZoneInfo: Used to provide parameters.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyCountryAndTimeZoneCancel(HashMap<String, String> countryAndTimeZoneInfo) throws Exception {
		boolean flag = false;
		String selectedCountryTextBefore = null;
		String selectedTimeZoneTextBefore = null;
		String selectedCountryTextAfter = null;
		String selectedTimeZoneTextAfter = null;
		selectedCountryTextBefore = getTextOfSettingsPage(countryAndTimeZoneInfo.get("countryTextKey"));
		selectedTimeZoneTextBefore = getTextOfSettingsPage(countryAndTimeZoneInfo.get("timeZoneTextKey"));
		clickOnElementsOfSettingsPage(countryAndTimeZoneInfo.get("editIconCountryKey"));
		clickOnElementsOfSettingsPage(countryAndTimeZoneInfo.get("countryDropdownButtonKey"));
		selectValueFromSingleSelectDropDownSettingsPage(2, countryAndTimeZoneInfo.get("countriesKey"), countryAndTimeZoneInfo.get("countryBoxKey"));
		clickOnElementsOfSettingsPage(countryAndTimeZoneInfo.get("timeZoneDropdownButtonKey"));
		selectValueFromSingleSelectDropDownSettingsPage(2, countryAndTimeZoneInfo.get("timeZonesKey"), countryAndTimeZoneInfo.get("timeZoneBoxMSPKey"));
		clickOnElementsOfSettingsPage(countryAndTimeZoneInfo.get("cancelButtonKey"));
		selectedCountryTextAfter = getTextOfSettingsPage(countryAndTimeZoneInfo.get("countryTextKey"));
		selectedTimeZoneTextAfter = getTextOfSettingsPage(countryAndTimeZoneInfo.get("timeZoneTextKey"));
		if (selectedCountryTextAfter.equalsIgnoreCase(selectedCountryTextBefore) && selectedTimeZoneTextAfter.equalsIgnoreCase(selectedTimeZoneTextBefore)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * This method is used to define parameters of Hours of operation.
	 * 
	 * @return
	 */
	public final HashMap<String, String> getHoursOfOperationDetails() throws Exception {
		HashMap<String, String> hoursOfOperationInfo = new HashMap<String, String>();
		hoursOfOperationInfo.put("editIconHoursOfOperationKey", "editIconHoursOfOperation");
		hoursOfOperationInfo.put("daysHoursOfOperationKey", "daysHoursOfOperation");
		hoursOfOperationInfo.put("timingsHoursOfOperationKey", "timingsHoursOfOperation");
		hoursOfOperationInfo.put("deleteRowsIconKey", "deleteRowsIcon");
		hoursOfOperationInfo.put("deleteRowsIconFirstKey", "deleteRowIconFirst");
		hoursOfOperationInfo.put("dayDropdownButtonKey", "dayDropdownButton");
		hoursOfOperationInfo.put("dayDropdownButtonAfterKey", "dayDropdownButtonAfter");
		hoursOfOperationInfo.put("dayDropdownButtonSecondKey", "dayDropdownButtonSecond");
		hoursOfOperationInfo.put("dayDropdownButtonSecondAfterKey", "dayDropdownButtonSecondAfter");
		hoursOfOperationInfo.put("clearDropdownKey", "clearDropdown");
		hoursOfOperationInfo.put("listOfCheckBoxesKey", "listOfCheckBoxes");
		hoursOfOperationInfo.put("addButtonKey", "addHoursButton");
		hoursOfOperationInfo.put("open24HoursCheckBoxKey", "open24HoursCheckBox");
		hoursOfOperationInfo.put("hoursStartKey", "hoursStart");
		hoursOfOperationInfo.put("hoursEndKey", "hoursEnd");
		hoursOfOperationInfo.put("elementsHoursStartKey", "elementsHoursStart");
		hoursOfOperationInfo.put("elementsHoursEndKey", "elementsHoursEnd");
		hoursOfOperationInfo.put("saveHoursKey", "saveHours");
		hoursOfOperationInfo.put("cancelHoursKey", "cancelHours");
		hoursOfOperationInfo.put("hoursStartBoxKey", "hoursStartBox");
		hoursOfOperationInfo.put("hoursEndBoxKey", "hoursEndBox");
		hoursOfOperationInfo.put("successNotificationKey", "successNotification");
		hoursOfOperationInfo.put("listOfDaysModalKey", "listOfDaysModal");
		return hoursOfOperationInfo;
	}

	/**
	 * This method is used to validate working of Hours of operation(save) on settings page.
	 * 
	 * @param hoursOfOperationInfo : Used to provide parameters.
	 * @param LanguageCode : Language code is used for multiple languages code.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyHoursOfOperationSave(HashMap<String, String> hoursOfOperationInfo, String LanguageCode) throws Exception {
		boolean flag = false;
		String hoursStartText = null;
		String hoursEndText = null;
		clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("editIconHoursOfOperationKey"));
		waitForPageLoaded();
		if (waitForElementsOfSettingsPage(hoursOfOperationInfo.get("deleteRowsIconFirstKey"))) {
			List<WebElement> listOfDeleteIcon = getElementsOfSettingsPage(hoursOfOperationInfo.get("deleteRowsIconKey"));
			for (int listOfDeleteIconCounter = listOfDeleteIcon.size() - 1; listOfDeleteIconCounter >= 0; listOfDeleteIconCounter--) {
				listOfDeleteIcon.get(listOfDeleteIconCounter).click();
			}
		}
		if (waitForElementsOfSettingsPage(hoursOfOperationInfo.get("clearDropdownKey"))) {
			clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("clearDropdownKey"));
		}
		clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("dayDropdownButtonKey"));
		List<WebElement> listOfDaysModal = getElementsOfSettingsPage(hoursOfOperationInfo.get("listOfDaysModalKey"));
		for (int listOfCheckBoxesCounter = 0; listOfCheckBoxesCounter < 2; listOfCheckBoxesCounter++) {
			listOfDaysModal.get(listOfCheckBoxesCounter).click();
			listOfDaysModal = getElementsOfSettingsPage(hoursOfOperationInfo.get("listOfDaysModalKey"));
		}
		clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("dayDropdownButtonAfterKey"));
		WebElement checkBox24Hour = getElementOfSettingsPage(hoursOfOperationInfo.get("open24HoursCheckBoxKey"));
		if (checkBox24Hour.isSelected()) {
			checkBox24Hour.click();
		}
		if (!waitForElementsOfSettingsPage(hoursOfOperationInfo.get("addButtonKey"))) {
			flag = false;
		}
		clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("addButtonKey"));
		clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("dayDropdownButtonSecondKey"));
		List<WebElement> listOfCheckBoxesSecond = getElementsOfSettingsPage(hoursOfOperationInfo.get("listOfDaysModalKey"));
		for (int listOfCheckBoxesCounter = 2; listOfCheckBoxesCounter <= listOfCheckBoxesSecond.size() - 1; listOfCheckBoxesCounter++) {
			listOfCheckBoxesSecond.get(listOfCheckBoxesCounter).click();
			listOfCheckBoxesSecond = getElementsOfSettingsPage(hoursOfOperationInfo.get("listOfDaysModalKey"));
		}
		clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("dayDropdownButtonSecondAfterKey"));
		clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("hoursStartKey"));
		selectValueFromSingleSelectDropDownSettingsPage(1, hoursOfOperationInfo.get("elementsHoursStartKey"), hoursOfOperationInfo.get("hoursStartKey"));
		clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("hoursEndKey"));
		selectValueFromSingleSelectDropDownSettingsPage(2, hoursOfOperationInfo.get("elementsHoursEndKey"), hoursOfOperationInfo.get("hoursEndKey"));
		hoursStartText = getTextOfSettingsPage(hoursOfOperationInfo.get("hoursStartBoxKey"));
		hoursEndText = getTextOfSettingsPage(hoursOfOperationInfo.get("hoursEndBoxKey"));
		clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("saveHoursKey"));
		sleeper(3000);
		verifyElementsOfSettingsPage(hoursOfOperationInfo.get("successNotificationKey"));
		String successMessage = getTextOfSettingsPage(hoursOfOperationInfo.get("successNotificationKey"));
		if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.success.message"))) {
			verifyElementsOfSettingsPage(hoursOfOperationInfo.get("daysHoursOfOperationKey"));
			List<WebElement> listOfDays = getElementsOfSettingsPage(hoursOfOperationInfo.get("daysHoursOfOperationKey"));
			for (WebElement webElement : listOfDays) {
				for (int listOfDaysCounter = 0; listOfDaysCounter < SettingsVariables.DAYS_LIST.size(); listOfDaysCounter++) {
					if (webElement.getText().contains(getTextLanguage(LanguageCode, "daas_ui", SettingsVariables.DAYS_LIST.get(listOfDaysCounter)))) {
						flag = true;
					}
				}
			}
			if (flag) {
				List<WebElement> listOfTimings = getElementsOfSettingsPage(hoursOfOperationInfo.get("timingsHoursOfOperationKey"));
				for (WebElement webElement : listOfTimings) {
					for (int listOfTimingsCounter = 0; listOfTimingsCounter < 2; listOfTimingsCounter++) {
						if (webElement.getText().contains(getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.all_hours"))) {
							flag = true;
						}
					}
				}

				if (flag) {
					for (WebElement webElement : listOfTimings) {
						for (int listOfTimingsCounter = 2; listOfTimingsCounter < listOfTimings.size(); listOfTimingsCounter++) {
							if (webElement.getText().contains(hoursStartText + " - " + hoursEndText)) {
								flag = true;
							}
						}
					}
				}
			} else {
				LOGGER.error("Details are not saved for Hours of Operation");
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * This method is used to validate working of Hours of operation(cancel) on settings page.
	 * 
	 * @param hoursOfOperationInfo: Used to provide parameters.
	 * @param LanguageCode : Language code is used for multiple languages code.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyHoursOfOperationCancel(HashMap<String, String> hoursOfOperationInfo, String LanguageCode) throws Exception {
		boolean flag = false;
		List<WebElement> listOfDaysBefore = getElementsOfSettingsPage(hoursOfOperationInfo.get("daysHoursOfOperationKey"));
		ArrayList<String> listOfDaysBeforeText = new ArrayList<String>();
		for (int i = 0; i < listOfDaysBefore.size(); i++) {
			listOfDaysBeforeText.add(listOfDaysBefore.get(i).getText());
		}
		clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("editIconHoursOfOperationKey"));
			if (waitForElementsOfSettingsPage(hoursOfOperationInfo.get("deleteRowsIconFirstKey"))) {
				List<WebElement> listOfDeleteIcon = getElementsOfSettingsPage(hoursOfOperationInfo.get("deleteRowsIconKey"));
				for (int listOfDeleteIconCounter = listOfDeleteIcon.size() - 1; listOfDeleteIconCounter >= 0; listOfDeleteIconCounter--) {
					listOfDeleteIcon.get(listOfDeleteIconCounter).click();
				}
			}
			if (waitForElementsOfSettingsPage(hoursOfOperationInfo.get("clearDropdownKey"))) {
				clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("clearDropdownKey"));
			}
			clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("dayDropdownButtonKey"));
		List<WebElement> listOfDaysModal = getElementsOfSettingsPage(hoursOfOperationInfo.get("listOfDaysModalKey"));
		for (int listOfCheckBoxesCounter = 0; listOfCheckBoxesCounter < 2; listOfCheckBoxesCounter++) {
			listOfDaysModal.get(listOfCheckBoxesCounter).click();
			listOfDaysModal = getElementsOfSettingsPage(hoursOfOperationInfo.get("listOfDaysModalKey"));
		}
		clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("dayDropdownButtonAfterKey"));
		clickOnElementsOfSettingsPage(hoursOfOperationInfo.get("cancelHoursKey"));
		waitForElementsOfSettingsPage(hoursOfOperationInfo.get("daysHoursOfOperationKey"));
		sleeper(3000);
		List<WebElement> listOfDaysAfter = getElementsOfSettingsPage(hoursOfOperationInfo.get("daysHoursOfOperationKey"));
		for (WebElement webElement : listOfDaysAfter) {
			for (int j = 0; j < listOfDaysBeforeText.size(); j++) {
				if (webElement.getText().contains(listOfDaysBeforeText.get(j))) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to validate working of exclude public holiday on settings page.
	 * 
	 * @param LanguageCode : Language code is used for multiple languages code.
	 * @param textKey : Status text of exclude public holiday
	 * @param holidaysToggleKey : Toggle for exclude public holiday.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyExcludePublicHoliday(String LanguageCode, String textKey, String holidaysToggleKey) throws Exception {
		boolean flag = false;
		String text = null;
		text = getTextOfSettingsPage(textKey);
		if (text.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.yes"))) {
			clickOnElementsOfSettingsPage(holidaysToggleKey);
			text = getTextOfSettingsPage(textKey);
			if (text.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.no"))) {
				flag = true;
			}
		} else {
			clickOnElementsOfSettingsPage(holidaysToggleKey);
			text = getTextOfSettingsPage(textKey);
			if (text.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.yes"))) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * This method is used to define parameters of Hours of operation.
	 * 
	 * @return
	 */
	public final HashMap<String, String> getHoursOfOperationDetailsHelpAndSupport() {
		HashMap<String, String> hoursOfOperationInfoHnS = new HashMap<String, String>();
		try {
			hoursOfOperationInfoHnS.put("hoursOfOperationsTitleKey", "hoursOfOperationsTitle");
			hoursOfOperationInfoHnS.put("hoursOfOperationsDaysKey", "hoursOfOperationsDays");
			hoursOfOperationInfoHnS.put("hoursOfOperationsTimingsKey", "hoursOfOperationsTimings");
			hoursOfOperationInfoHnS.put("hoursOfOperationsCountryTitleKey", "hoursOfOperationsCountryTitle");
			hoursOfOperationInfoHnS.put("hoursOfOperationsCountryKey", "hoursOfOperationsCountry");
			hoursOfOperationInfoHnS.put("hoursOfOperationsTimeZoneTitleKey", "hoursOfOperationsTimeZoneTitle");
			hoursOfOperationInfoHnS.put("hoursOfOperationsTimeZoneKey", "hoursOfOperationsTimeZone");
			hoursOfOperationInfoHnS.put("hoursOfOperationsHolidaysStatusKey", "hoursOfOperationsHolidaysStatus");
			hoursOfOperationInfoHnS.put("hoursOfOperationsExceptionKey", "hoursOfOperationsException");
			hoursOfOperationInfoHnS.put("timingHoursOfOperationThirdKey", "timingHoursOfOperationThird");
			hoursOfOperationInfoHnS.put("countryTextKey", "countryText");
			hoursOfOperationInfoHnS.put("timeZoneTextKey", "timeZoneText");
			hoursOfOperationInfoHnS.put("statusTextHolidayKey", "statusTextHoliday");
		} catch (Exception e) {
			e.getMessage();
		}
		return hoursOfOperationInfoHnS;
	}

	/**
	 * This method is used to extract text from timings of hours of operation
	 * 
	 * @return
	 */
	public final String getTextFromTimings(HashMap<String, String> hoursOfOperationInfoHnS) throws Exception {
		String timing = null;
		WebElement e = getElementOfSettingsPage(hoursOfOperationInfoHnS.get("timingHoursOfOperationThirdKey"));
		if (e.isDisplayed()) {
			timing = getTextOfSettingsPage(hoursOfOperationInfoHnS.get("timingHoursOfOperationThirdKey"));
		} else {
			LOGGER.error("Timings are not reflected back correctly.");
		}
		return timing;
	}

	/**
	 * This method is used to validate working of Hours of Operation on settings page.
	 * 
	 * @param LanguageCode : Language code is used for multiple languages code.
	 * @param hoursOfOperationInfoHnS : this parameter provides details of hours of operation
	 * @param timing : timings from MSP settings
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyHoursOfOperationHelpAndSupport(HashMap<String, String> hoursOfOperationInfoHnS, String LanguageCode, String timing) throws Exception {
		boolean flag = false;
		verifyElementsOfSettingsPage(hoursOfOperationInfoHnS.get("hoursOfOperationsDaysKey"));
		List<WebElement> listOfDays = getElementsOfSettingsPage(hoursOfOperationInfoHnS.get("hoursOfOperationsDaysKey"));
		if (listOfDays.size() != 0) {
			for (WebElement webElement : listOfDays) {
				for (int listOfDaysCounter = 0; listOfDaysCounter < SettingsVariables.DAYS_LIST.size(); listOfDaysCounter++) {
					if (webElement.getText().contains(getTextLanguage(LanguageCode, "daas_ui", SettingsVariables.DAYS_LIST.get(listOfDaysCounter)))) {
						flag = true;
					}
				}
			}
		} else {
			LOGGER.error("Days are empty in list.");
		}
		if (flag) {
			List<WebElement> listOfTimings = getElementsOfSettingsPage(hoursOfOperationInfoHnS.get("hoursOfOperationsTimingsKey"));
			for (WebElement webElement : listOfTimings) {
				for (int listOfTimingsCounter = 0; listOfTimingsCounter < 2; listOfTimingsCounter++) {
					if (webElement.getText().contains(getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.all_hours"))) {
						flag = true;
					}
				}
			}
			if (flag) {
				for (WebElement webElement : listOfTimings) {
					for (int listOfTimingsCounter = 2; listOfTimingsCounter < listOfTimings.size(); listOfTimingsCounter++) {
						if (webElement.getText().contains(timing)) {
							flag = true;
						}
					}
				}
			}
		} else {
			LOGGER.error("Details are not saved for Hours of Operation");
		}
		return flag;
	}

	/**
	 * This method is used to validate working of Country and timezone on settings page.
	 * 
	 * @param hoursOfOperationInfoHnS : this parameter provides details of hours of operation
	 * @return
	 * @throws Exception
	 */
	public final String[] getTextFromCountryTimeZone(HashMap<String, String> hoursOfOperationInfoHnS) throws Exception {
		String[] countryAndTimezone = new String[2];
		WebElement countryWeb = getElementOfSettingsPage(hoursOfOperationInfoHnS.get("countryTextKey"));
		WebElement timezoneWeb = getElementOfSettingsPage(hoursOfOperationInfoHnS.get("timeZoneTextKey"));
		if (countryWeb.isDisplayed() && timezoneWeb.isDisplayed()) {
			countryAndTimezone[0] = getTextOfSettingsPage(hoursOfOperationInfoHnS.get("countryTextKey"));
			countryAndTimezone[1] = getTextOfSettingsPage(hoursOfOperationInfoHnS.get("timeZoneTextKey"));
		} else {
			LOGGER.error("Country And Timezone are not reflected back correctly.");
		}
		return countryAndTimezone;
	}

	/**
	 * This method is used to validate working of Country and timezone on settings page.
	 * 
	 * @param hoursOfOperationInfoHnS : this parameter provides details of hours of operation
	 * @param countryAndTimezone : provides country name and time zone
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyCountryAndTimezoneHelpAndSupport(HashMap<String, String> hoursOfOperationInfoHnS, String[] countryAndTimezone) throws Exception {
		boolean flag = false;
		String selectedCountryText = null;
		String selectedTimeZoneText = null;
		selectedCountryText = getTextOfSettingsPage(hoursOfOperationInfoHnS.get("hoursOfOperationsCountryKey"));
		selectedTimeZoneText = getTextOfSettingsPage(hoursOfOperationInfoHnS.get("hoursOfOperationsTimeZoneKey"));
		if (selectedCountryText.equalsIgnoreCase(countryAndTimezone[0]) && selectedTimeZoneText.equalsIgnoreCase(countryAndTimezone[1])) {
			flag = true;
		} else {
			LOGGER.error("Details are not saved for Country and Timezone");
		}
		return flag;
	}

	/**
	 * This method is used to extract text from exclude public holiday on settings page.
	 * 
	 * @param hoursOfOperationInfoHnS : this parameter provides details of hours of operation
	 * @return
	 * @throws Exception
	 */
	public final String getTextFromPublicHoliday(HashMap<String, String> hoursOfOperationInfoHnS) throws Exception {
		String statusText = null;
		try {
			WebElement e = getElementOfSettingsPage(hoursOfOperationInfoHnS.get("statusTextHolidayKey"));
			if (e.isDisplayed()) {
				statusText = getTextOfSettingsPage(hoursOfOperationInfoHnS.get("statusTextHolidayKey"));
			} else {
				LOGGER.error("Status for Public Holiday is not getting reflected back correctly.");
			}
		} catch (Exception e) {
			LOGGER.error("getTextFromPublicHoliday failed due to " + e.getMessage());
		}
		return statusText;
	}

	/**
	 * This method is used to validate working of exclude public holiday on help and support page.
	 * 
	 * @param hoursOfOperationInfoHnS : this parameter provides details of hours of operation
	 * @param statusTextPublicHoliday : provides status text of public holiday
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyPublicHolidayHelpAndSupport(HashMap<String, String> hoursOfOperationInfoHnS, String statusTextPublicHoliday, String LanguageCode) throws Exception {
		boolean flag = false;
		String statusTextHoliday = null;
		if (statusTextPublicHoliday.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.yes"))) {
			statusTextHoliday = getTextOfSettingsPage(hoursOfOperationInfoHnS.get("hoursOfOperationsHolidaysStatusKey"));
			if (statusTextHoliday.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "help.support_box.exceptions.closed"))) {
				flag = true;
			} else {
				LOGGER.error("Details are not saved for public holidays when turned ON.");
			}
		} else if (statusTextPublicHoliday.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.no"))) {
			statusTextHoliday = getTextOfSettingsPage(hoursOfOperationInfoHnS.get("hoursOfOperationsHolidaysStatusKey"));
			if (statusTextHoliday.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "help.support_box.exceptions.none"))) {
				flag = true;
			} else {
				LOGGER.error("Details are not saved for public holidays when turned OFF.");
			}
		}
		return flag;
	}

	/**
	 * This method is used to define parameters of Direct Support for RA.
	 * 
	 * @return
	 */
	public final HashMap<String, String> getDirectSupportDetailsHelpAndSupport() throws Exception {
		HashMap<String, String> directSupportInfoHnS = new HashMap<String, String>();
		directSupportInfoHnS.put("directSupportTileKey", "directSupportTile");
		directSupportInfoHnS.put("directSupportTitleKey", "directSupportTitle");
		directSupportInfoHnS.put("directSupportNumberKey", "directSupportNumber");
		return directSupportInfoHnS;
	}

	/**
	 * This method is used to extract text from direct support on settings page.
	 * 
	 * @param directSupportInfo : this parameter provides details of direct support
	 * @param LanguageCode : This parameter provides language code for multiple languages.
	 * @return
	 * @throws Exception
	 */
	public final String[] getTextFromDirectSupport(HashMap<String, String> directSupportInfo, String LanguageCode) throws Exception {
		String[] statusText = new String[2];
		WebElement e = getElementOfSettingsPage(directSupportInfo.get("statusTextOfDirectSupportKey"));
		if (e.isDisplayed()) {
			statusText[0] = getTextOfSettingsPage(directSupportInfo.get("statusTextOfDirectSupportKey"));
			if (statusText[0].equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
				statusText[0] = getTextOfSettingsPage(directSupportInfo.get("statusTextOfDirectSupportKey"));
			} else if (statusText[0].equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
				statusText[0] = getTextOfSettingsPage(directSupportInfo.get("statusTextOfDirectSupportKey"));
				statusText[1] = getTextOfSettingsPage(directSupportInfo.get("phoneNumberTextDirectSupportKey"));
			}
		} else {
			LOGGER.error("Direct Support is not working properly.");
		}
		return statusText;
	}

	/**
	 * This method is used to validate working of direct support on help and support page.
	 * 
	 * @param directSupportInfoHnS : this parameter provides details of direct support
	 * @param statusText : provides direct support text.
	 * @param LanguageCode : This parameter provides language code for multiple languages.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyDirectSupportHelpAndSupport(HashMap<String, String> directSupportInfoHnS, String[] statusText, String LanguageCode) throws Exception {
		boolean flag = false;
		String phoneNumber = null;
		if (statusText[0].equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
			waitForElementsOfSettingsPage(directSupportInfoHnS.get("directSupportTileKey"));
			if (!verifyElementsOfSettingsPage(directSupportInfoHnS.get("directSupportTileKey"))) {
				flag = true;
			} else {
				LOGGER.error("Direct Support is not updated on Help & Support");
			}
		} else if (statusText[0].equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
			waitForElementsOfSettingsPage(directSupportInfoHnS.get("directSupportTileKey"));
			if (verifyElementsOfSettingsPage(directSupportInfoHnS.get("directSupportTileKey"))) {
				WebElement e = getElementOfSettingsPage(directSupportInfoHnS.get("directSupportTileKey"));
				if (e.isDisplayed()) {
					phoneNumber = getTextOfSettingsPage(directSupportInfoHnS.get("directSupportNumberKey"));
					if (phoneNumber.equalsIgnoreCase(statusText[1])) {
						flag = true;
					} else {
						LOGGER.error("Phone number is not updated on Help & Support");
					}
				} else {
					LOGGER.error("Direct Support is not updated on Help & Support");
				}
			}
		}
		return flag;
	}

	/*
	 * This method is used to define parameters of servicenow.
	 * 
	 * @return parameters for SNOW common methods.
	 */
	public final HashMap<String, String> getServiceNowDetails() throws Exception {
		HashMap<String, String> serviceNowInfo = new HashMap<String, String>();
		serviceNowInfo.put("snowModalTitleKey", "snowModalTitle");
		serviceNowInfo.put("snowTextKey", "snowText");
		serviceNowInfo.put("snowTitleKey", "snowTitle");
		serviceNowInfo.put("editIconSnowKey", "editIconSnow");
		serviceNowInfo.put("snowStatusTextKey", "snowStatusText");
		serviceNowInfo.put("snowStatusTextSecondKey", "snowStatusTextSecond");
		serviceNowInfo.put("snowToggleKey", "snowToggle");
		serviceNowInfo.put("snowURLKey", "snowURL");
		serviceNowInfo.put("snowUsernameKey", "snowUsername");
		serviceNowInfo.put("snowPasswordKey", "snowPassword");
		serviceNowInfo.put("saveSnowKey", "saveSnow");
		serviceNowInfo.put("cancelSnowKey", "cancelSnow");
		serviceNowInfo.put("resetConfigSnowKey", "resetConfigSnow");
		serviceNowInfo.put("clientIDModalKey", "clientIDModal");
		serviceNowInfo.put("clientSecretModalKey", "clientSecretModal");
		serviceNowInfo.put("closeSnowModalKey", "closeSnowModal");
		serviceNowInfo.put("clientIDTextKey", "clientIDText");
		serviceNowInfo.put("clientSecretTextKey", "clientSecretText");
		serviceNowInfo.put("successNotificationKey", "successNotification");
		return serviceNowInfo;
	}

	/**
	 * This method is used to validate working of servicenow for Enable on settings page.
	 * 
	 * @param LanguageCode: This is used for multiple language code.
	 * @param serviceNowInfo: This is used to provide parameters.
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyServiceNowEnable(String LanguageCode, HashMap<String, String> serviceNowInfo) throws Exception {
		boolean flag = false;
		String statusText = null;
		sleeper(5000);
		statusText = getTextOfSettingsPage(serviceNowInfo.get("snowStatusTextKey"));
		if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
			clickOnElementsOfSettingsPage(serviceNowInfo.get("editIconSnowKey"));
			sleeper(3000);
			clickOnElementsOfSettingsPage(serviceNowInfo.get("resetConfigSnowKey"));
			flag = provideDetailsSnowSave(LanguageCode, serviceNowInfo);
		} else if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
			clickOnElementsOfSettingsPage(serviceNowInfo.get("editIconSnowKey"));
			flag = provideDetailsSnowSave(LanguageCode, serviceNowInfo);
		}
		return flag;
	}

	/**
	 * This method is used to provide details in SNOW on settings page.
	 * 
	 * @param LanguageCode: This is used for multiple language code.
	 * @param serviceNowInfo: This is used to provide parameters.
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean provideDetailsSnowSave(String LanguageCode, HashMap<String, String> serviceNowInfo) throws Exception {
		boolean flag = false;
		String urlText = null;
		WebElement url = getElementOfSettingsPage(serviceNowInfo.get("snowURLKey"));
		WebElement uname = getElementOfSettingsPage(serviceNowInfo.get("snowUsernameKey"));
		WebElement password = getElementOfSettingsPage(serviceNowInfo.get("snowPasswordKey"));
		WebElement save = getElementOfSettingsPage(serviceNowInfo.get("saveSnowKey"));
		if (!(url.isEnabled() && uname.isEnabled() && password.isEnabled() && save.isEnabled())) {
			sleeper(3000);
			clickOnElementsOfSettingsPage(serviceNowInfo.get("snowToggleKey"));
			enterTextForSettingsPage(serviceNowInfo.get("snowURLKey"), ConstantURL.SNOW_URL3);
			enterTextForSettingsPage(serviceNowInfo.get("snowUsernameKey"), SettingsVariables.USERNAME_SERVICENOW);
			enterTextForSettingsPage(serviceNowInfo.get("snowPasswordKey"), SettingsVariables.PASSWORD_SERVICENOW);
			clickOnElementsOfSettingsPage(serviceNowInfo.get("saveSnowKey"));
			WebElement resetConfig = getElementOfSettingsPage(serviceNowInfo.get("resetConfigSnowKey"));
			verifyElementsOfSettingsPage(serviceNowInfo.get("successNotificationKey"));
			String successMessage = getTextOfSettingsPage(serviceNowInfo.get("successNotificationKey"));
			if (resetConfig.isDisplayed() && successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", "ServiceNow integration"))) {
				WebElement clientIdModal = getElementOfSettingsPage(serviceNowInfo.get("clientIDModalKey"));
				WebElement clientSecretModal = getElementOfSettingsPage(serviceNowInfo.get("clientSecretModalKey"));
				if (clientIdModal.isDisplayed() && clientSecretModal.isDisplayed()) {
					flag = true;
					WebElement urlBox = getElementOfSettingsPage(serviceNowInfo.get("snowURLKey"));
					urlText = urlBox.getAttribute("value");
				} else {
					LOGGER.error("ServiceNow save is not working properly.");
				}
			} else {
				flag = false;
			}
			clickOnElementsOfSettingsPage(serviceNowInfo.get("closeSnowModalKey"));
			sleeper(3000);
			if (getTextOfSettingsPage(serviceNowInfo.get("snowStatusTextKey")).equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled")) && getTextOfSettingsPage(serviceNowInfo.get("snowStatusTextSecondKey")).equalsIgnoreCase(urlText)) {
				flag = true;
			} else {
				flag = false;
				LOGGER.error("ServiceNow save is not working properly.");
			}
		}
		return flag;
	}

	/**
	 * This method is used to validate working of servicenow for Disable on settings page.
	 * 
	 * @param LanguageCode:This is used for multiple language code.
	 * @param serviceNowInfo:This is used to provide parameters.
	 * @return boolean value.
	 * @throws Exception
	 */
	public final boolean verifyServiceNowDisable(String LanguageCode, HashMap<String, String> serviceNowInfo) throws Exception {
		boolean flag = false;
		String statusText = null;
		verifyElementsOfSettingsPage(serviceNowInfo.get("snowStatusTextKey"));
		sleeper(5000);
		statusText = getTextOfSettingsPage(serviceNowInfo.get("snowStatusTextKey"));
		if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
			clickOnElementsOfSettingsPage(serviceNowInfo.get("editIconSnowKey"));
			verifyElementsOfSettingsPage(serviceNowInfo.get("snowToggleKey"));
			clickOnElementsOfSettingsPage(serviceNowInfo.get("snowToggleKey"));
			verifyElementsOfSettingsPage(serviceNowInfo.get("saveSnowKey"));
			clickOnElementsOfSettingsPage(serviceNowInfo.get("saveSnowKey"));
			verifyElementsOfSettingsPage(serviceNowInfo.get("cancelSnowKey"));
			clickOnElementsOfSettingsPage(serviceNowInfo.get("cancelSnowKey"));
			sleeper(3000);
			statusText = getTextOfSettingsPage(serviceNowInfo.get("snowStatusTextKey"));
			if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
				flag = true;
			} else {
				LOGGER.error("ServiceNow Disable is not working properly.");
			}
		} else if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
			clickOnElementsOfSettingsPage(serviceNowInfo.get("editIconSnowKey"));
			flag = provideDetailsSnowSave(LanguageCode, serviceNowInfo);
			if (flag) {
				clickOnElementsOfSettingsPage(serviceNowInfo.get("editIconSnowKey"));
				clickOnElementsOfSettingsPage(serviceNowInfo.get("resetConfigSnowKey"));
				clickOnElementsOfSettingsPage(serviceNowInfo.get("closeSnowModalKey"));
				statusText = getTextOfSettingsPage(serviceNowInfo.get("snowStatusTextKey"));
				if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
					flag = true;
				}
			} else {
				LOGGER.error("ServiceNow Disable is not working properly.");
			}
		}
		return flag;
	}

	/**
	 * @param LanguageCode:This is used for multiple language code.
	 * @param serviceNowInfo:This is used to provide parameters.
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyServiceNowCancel(String LanguageCode, HashMap<String, String> serviceNowInfo) throws Exception {
		boolean flag = false;
		String urlTextBefore = null;
		String statusText = null;
		sleeper(3000);
		statusText = getTextOfSettingsPage(serviceNowInfo.get("snowStatusTextKey"));
		if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
			clickOnElementsOfSettingsPage(serviceNowInfo.get("editIconSnowKey"));
			WebElement urlBox = getElementOfSettingsPage(serviceNowInfo.get("snowURLKey"));
			urlTextBefore = urlBox.getAttribute("value");
			enterTextForSettingsPage(serviceNowInfo.get("snowURLKey"), ConstantURL.URL_SERVICENOW);
			clickOnElementsOfSettingsPage(serviceNowInfo.get("cancelSnowKey"));
			statusText = getTextOfSettingsPage(serviceNowInfo.get("snowStatusTextKey"));
			if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled")) && getTextOfSettingsPage(serviceNowInfo.get("snowStatusTextSecondKey")).equalsIgnoreCase(urlTextBefore)) {
				flag = true;
			} else {
				LOGGER.error("ServiceNow Cancel is not working properly.");
			}
		} else if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
			clickOnElementsOfSettingsPage(serviceNowInfo.get("editIconSnowKey"));
			clickOnElementsOfSettingsPage(serviceNowInfo.get("snowToggleKey"));
			clickOnElementsOfSettingsPage(serviceNowInfo.get("cancelSnowKey"));
			statusText = getTextOfSettingsPage(serviceNowInfo.get("snowStatusTextKey"));
			if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
				flag = true;
			} else {
				LOGGER.error("ServiceNow Cancel is not working properly.");
			}
		}
		return flag;
	}

	/**
	 * This method is used to define parameters of Email request for RA.
	 * 
	 * @return
	 */
	public final HashMap<String, String> getEmailRequestDetailsHelpAndSupport() throws Exception {
		HashMap<String, String> emailRequestInfoHnS = new HashMap<String, String>();
		emailRequestInfoHnS.put("emailTileKey", "emailTile");
		emailRequestInfoHnS.put("emailTitleKey", "emailTitle");
		emailRequestInfoHnS.put("emailModalTitleKey", "emailModalTitle");
		emailRequestInfoHnS.put("emailRequestButtonKey", "emailRequestButton");
		emailRequestInfoHnS.put("emailFieldKey", "emailField");
		emailRequestInfoHnS.put("emailSubjectKey", "emailSubject");
		emailRequestInfoHnS.put("emailDescriptionKey", "emailDescription");
		emailRequestInfoHnS.put("submitEmailKey", "submitEmail");
		emailRequestInfoHnS.put("cancelEmailKey", "cancelEmail");
		emailRequestInfoHnS.put("myselfRadioKey", "myselfRadio");
		emailRequestInfoHnS.put("otherUserRadioKey", "otherUserRadio");
		emailRequestInfoHnS.put("userIconKey", "userIcon");
		emailRequestInfoHnS.put("userProfileKey", "userProfile");
		emailRequestInfoHnS.put("userProfileEmailKey", "userProfileEmail");
		emailRequestInfoHnS.put("userProfileFormKey", "userProfileForm");
		return emailRequestInfoHnS;
	}

	/**
	 * This method is used to define parameters of Service Request for RA.
	 * 
	 * @return
	 */
	public final HashMap<String, String> getServiceRequestHelpAndSupport() throws Exception {
		HashMap<String, String> serviceRequestInfoHnS = new HashMap<String, String>();
		serviceRequestInfoHnS.put("serviceRequestTileKey", "serviceRequestTile");
		serviceRequestInfoHnS.put("serviceRequestTitleKey", "serviceRequestTitle");
		serviceRequestInfoHnS.put("serviceRequestModalTitleKey", "serviceRequestModalTitle");
		serviceRequestInfoHnS.put("serviceRequestButtonKey", "serviceRequestButton");
		serviceRequestInfoHnS.put("serviceRequestIconKey", "serviceRequestIcon");
		return serviceRequestInfoHnS;
	}

	/**
	 * This method is used to define parameters of Track Requests for RA.
	 * 
	 * @return
	 */
	public final HashMap<String, String> getTrackRequestsHelpAndSupport() throws Exception {
		HashMap<String, String> trackRequestsInfoHnS = new HashMap<String, String>();
		trackRequestsInfoHnS.put("trackRequestsTileKey", "trackRequestsTile");
		trackRequestsInfoHnS.put("trackRequestsTitleKey", "trackRequestsTitle");
		trackRequestsInfoHnS.put("trackRequestsModalTitleKey", "trackRequestsTitle");
		trackRequestsInfoHnS.put("trackRequestsButtonKey", "trackRequestsButton");
		trackRequestsInfoHnS.put("trackRequestsIconKey", "trackRequestsIcon");
		return trackRequestsInfoHnS;
	}
	
	/**
	 * This method is used to define parameters of Email content for RA.
	 * 
	 * @return
	 */
	public final HashMap<String, String> getEmailContentHelpAndSupport() throws Exception {
		HashMap<String, String> emailContentInfoHnS = new HashMap<String, String>();
		emailContentInfoHnS.put("to:", "testapi23");
		emailContentInfoHnS.put("From:", "HP DaaS");
		return emailContentInfoHnS;

	}

	/**
	 * This method is used to expand side menu
	 * 
	 */
	public final void expandMenuIconHnS() throws Exception {
		if (!waitForElementsOfSettingsPage("dashboardTab")) {
			clickOnElementsOfSettingsPage("menuIcon");
		}
	}

	/**
	 * @param emailRequestInfoHnS: Parameters of EMAIL request.
	 * @param LanguageCode: Language code for multiple languages.
	 * @param userType : User types :MSP/Partner
	 * @return true or false
	 * @throws Exception
	 */
	public final boolean verifyEmailRequestMyselfHelpAndSupport(HashMap<String, String> emailRequestInfoHnS, String LanguageCode, String UserType) throws Exception {
		boolean flag = false;
		String userProfileEmail = null;
		String email = null;
		String successMessage = null;
		clickOnElementsOfSettingsPage(emailRequestInfoHnS.get("userIconKey"));
		clickOnElementsOfSettingsPage(emailRequestInfoHnS.get("userProfileKey"));
		getDriver().navigate().refresh();
		sleeper(3000);
		userProfileEmail = getTextOfSettingsPage(emailRequestInfoHnS.get("userProfileEmailKey"));
		expandMenuIconHnS();
		clickOnElementsOfSettingsPage("helpAndSupportTab");
		if (UserType.equals("MSP")) {
			clickOnElementsOfSettingsPage("hpAssistanceTab");
			getTextOfSettingsPage("partnerAssistancetitle").equals(getTextLanguage(LanguageCode, "daas_ui", "help.section.title"));
		}
		clickOnElementsOfSettingsPage(emailRequestInfoHnS.get("emailRequestButtonKey"));
		email = getAttributesOfSettingsPage(emailRequestInfoHnS.get("emailFieldKey"), "value");
		WebElement e = getElementOfSettingsPage(emailRequestInfoHnS.get("submitEmailKey"));
		if (email.equalsIgnoreCase(userProfileEmail) && e.isEnabled()) {
			enterTextForSettingsPage(emailRequestInfoHnS.get("emailSubjectKey"), "test subject");
			enterTextForSettingsPage(emailRequestInfoHnS.get("emailDescriptionKey"), "test description");

			clickOnElementsOfSettingsPage(emailRequestInfoHnS.get("submitEmailKey"));
			successMessage = getTextOfSettingsPage("successNotificationHnS");

			if (verifyElementsOfSettingsPage("successNotificationHnS")) {
				if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "help.message.email.success"))) {
					flag = true;
				} else {
					LOGGER.error("Email request sending failed");
				}
			} else {
				LOGGER.error("Success message is not getting displayed after submitting email request");
			}
		} else {
			LOGGER.error("Email is not getting pre populated from user's profile.");
		}
		return flag;
	}

	/**
	 * @param emailRequestInfoHnS: Parameters of EMAIL request.
	 * @param LanguageCode: Language code for multiple languages.
	 * @return true or false
	 * @throws Exception
	 */
	public final boolean verifyEmailRequestOtherHelpAndSupport(HashMap<String, String> emailRequestInfoHnS, String LanguageCode) throws Exception {
		boolean flag = false;
		String successMessage = null;
		clickOnElementsOfSettingsPage(emailRequestInfoHnS.get("emailRequestButtonKey"));
		clickOnElementsOfSettingsPage(emailRequestInfoHnS.get("otherUserRadioKey"));
		WebElement submitButtonEmailReq = getElementOfSettingsPage(emailRequestInfoHnS.get("submitEmailKey"));
		if (submitButtonEmailReq.isEnabled()) {
			enterTextForSettingsPage(emailRequestInfoHnS.get("emailFieldKey"), "test@test.com");
			enterTextForSettingsPage(emailRequestInfoHnS.get("emailSubjectKey"), "test subject other");
			enterTextForSettingsPage(emailRequestInfoHnS.get("emailDescriptionKey"), "test description other");
			if (submitButtonEmailReq.isEnabled()) {
				clickOnElementsOfSettingsPage(emailRequestInfoHnS.get("submitEmailKey"));
				successMessage = getTextOfSettingsPage("successNotificationHnS");
			} else {
				LOGGER.error("Submit button is not enabled for email request on Help & Support");
			}
			if (verifyElementsOfSettingsPage("successNotificationHnS")) {
				if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "help.message.email.success"))) {
					flag = true;
				} else {
					LOGGER.error("Email request sending failed");
				}
			} else {
				LOGGER.error("Success message is not getting displayed after submitting email request");
			}
		} else {
			LOGGER.error("Submit button is disabled for email request of other user.");
		}
		return flag;
	}

	/**
	 * This method is used to define parameters of Email request for RA.
	 * 
	 * @return
	 */
	public final HashMap<String, String> getCallbackRequestDetailsHelpAndSupport() throws Exception {
		HashMap<String, String> callbackRequestInfoHnS = new HashMap<String, String>();
		callbackRequestInfoHnS.put("callbackTileKey", "callbackTile");
		callbackRequestInfoHnS.put("callbackTitleKey", "callbackTitle");
		callbackRequestInfoHnS.put("callbackModalTitleKey", "callbackModalTitle");
		callbackRequestInfoHnS.put("callbackRequestButtonKey", "callbackRequestButton");
		callbackRequestInfoHnS.put("phoneNumberFieldKey", "phoneNumberField");
		callbackRequestInfoHnS.put("timeZoneBoxKey", "timeZoneBox");
		callbackRequestInfoHnS.put("listOfTimezonesKey", "listOfTimezonesCallback");
		callbackRequestInfoHnS.put("timeZoneButtonKey", "timeZoneButton");
		callbackRequestInfoHnS.put("dateBoxKey", "dateBox");
		callbackRequestInfoHnS.put("dateButtonKey", "dateButton");
		callbackRequestInfoHnS.put("listOfDatesKey", "listOfDates");
		callbackRequestInfoHnS.put("timingBoxKey", "timingBox");
		callbackRequestInfoHnS.put("timingButtonKey", "timingButton");
		callbackRequestInfoHnS.put("listOfTimingsKey", "listOfTimings");
		callbackRequestInfoHnS.put("descriptionBoxKey", "descriptionBox");
		callbackRequestInfoHnS.put("userIconKey", "userIcon");
		callbackRequestInfoHnS.put("userProfileKey", "userProfile");
		callbackRequestInfoHnS.put("userProfileEmailKey", "userProfileEmail");
		callbackRequestInfoHnS.put("userProfileFormKey", "userProfileForm");
		callbackRequestInfoHnS.put("userOfficePhoneKey", "userOfficePhone");
		callbackRequestInfoHnS.put("userMobilePhoneKey", "userMobilePhone");
		callbackRequestInfoHnS.put("userSaveDetailsKey", "userSaveDetails");
		callbackRequestInfoHnS.put("userOfficePhoneEmptyKey", "userOfficePhoneEmpty");
		callbackRequestInfoHnS.put("userMobilePhoneEmptyKey", "userMobilePhoneEmpty");
		callbackRequestInfoHnS.put("userEditProfileKey", "userEditProfile");
		callbackRequestInfoHnS.put("submitCallbackKey", "submitCallback");
		callbackRequestInfoHnS.put("cancelCallbackKey", "cancelCallback");
		callbackRequestInfoHnS.put("userOfficePhoneSavedKey", "userOfficePhoneSaved");
		callbackRequestInfoHnS.put("userMobilePhoneSavedKey", "userMobilePhoneSaved");
		callbackRequestInfoHnS.put("otherFirstNameKey", "otherFirstName");
		callbackRequestInfoHnS.put("otherLastNameKey", "otherLastName");
		callbackRequestInfoHnS.put("otherTimezoneBoxKey", "otherTimezoneBox");
		callbackRequestInfoHnS.put("otherTimezoneListKey", "otherTimezoneList");
		callbackRequestInfoHnS.put("otherTimezoneButtonKey", "otherTimezoneButton");
		callbackRequestInfoHnS.put("otherDateBoxKey", "otherDateBox");
		callbackRequestInfoHnS.put("otherDateListKey", "otherDateList");
		callbackRequestInfoHnS.put("otherDateButtonKey", "otherDateButton");
		callbackRequestInfoHnS.put("otherTimingBoxKey", "otherTimingBox");
		callbackRequestInfoHnS.put("otherTimingListKey", "otherTimingList");
		callbackRequestInfoHnS.put("otherTimingButtonKey", "otherTimingButton");
		callbackRequestInfoHnS.put("otherRadioButtonKey", "otherRadioButton");
		callbackRequestInfoHnS.put("userOfficePhoneFieldKey", "userOfficePhoneField");
		return callbackRequestInfoHnS;
	}

	/**
	 * @param callbackRequestInfoHnS: Parameters of callback request.
	 * @param LanguageCode: Language code for multiple languages.
	 * @return true or false
	 * @throws Exception
	 */
	public final boolean verifyCallbackRequestMyselfHelpAndSupport(HashMap<String, String> callbackRequestInfoHnS, String LanguageCode,String UserType) throws Exception {
		boolean flag = false;
		String userPhone = null;
		String phone = null;
		String successMessage = null;
		clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("userIconKey"));
		clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("userProfileKey"));
		//getDriver().navigate().refresh();
		sleeper(3000);
		if (getTextOfSettingsPage(callbackRequestInfoHnS.get("userOfficePhoneKey")).equalsIgnoreCase("-")) {
			if (getTextOfSettingsPage(callbackRequestInfoHnS.get("userMobilePhoneKey")).equalsIgnoreCase("-")) {
				clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("userEditProfileKey"));
				enterTextForSettingsPage(callbackRequestInfoHnS.get("userOfficePhoneFieldKey"), "789456123");
				clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("userSaveDetailsKey"));
				waitForPageLoaded();
				sleeper(3000);
				if (verifyElementsOfSettingsPage("successNotificationHnS")) {
					userPhone = getTextOfSettingsPage(callbackRequestInfoHnS.get("userOfficePhoneKey"));
					if (!userPhone.equalsIgnoreCase("789456123")) {
						flag = false;
					} else {
						flag = true;
					}
				} else {
					LOGGER.error("Office number did not updated successfully.");
				}
			} else {
				userPhone = getTextOfSettingsPage(callbackRequestInfoHnS.get("userMobilePhoneKey"));
			}
		} else {
			userPhone = getTextOfSettingsPage(callbackRequestInfoHnS.get("userOfficePhoneKey"));
		}
		expandMenuIconHnS();
		waitForPageLoaded();
		sleeper(2000);
		clickOnElementsOfSettingsPage("helpAndSupportTab");
		if (UserType.equals("MSP")) {
			clickOnElementsOfSettingsPage("hpAssistanceTab");
			//waitForElementsOfSettingsPage("partnerAssistancetitle");
		}
		clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("callbackRequestButtonKey"));
		phone = getAttributesOfSettingsPage(callbackRequestInfoHnS.get("phoneNumberFieldKey"), "value");
		WebElement e = getElementOfSettingsPage(callbackRequestInfoHnS.get("submitCallbackKey"));
		waitForPageLoaded();
		if (phone.equalsIgnoreCase(userPhone) && e.isEnabled()) {
			clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("timeZoneButtonKey"));
			selectValueFromSingleSelectDropDownSettingsPage(1, callbackRequestInfoHnS.get("listOfTimezonesKey"), callbackRequestInfoHnS.get("timeZoneBoxKey"));
			clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("dateButtonKey"));
			selectValueFromSingleSelectDropDownSettingsPage(1, callbackRequestInfoHnS.get("listOfDatesKey"), callbackRequestInfoHnS.get("dateBoxKey"));
			clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("timingButtonKey"));
			selectValueFromSingleSelectDropDownSettingsPage(0, callbackRequestInfoHnS.get("otherTimingListKey"), callbackRequestInfoHnS.get("otherTimingBoxKey"));
			enterTextForSettingsPage(callbackRequestInfoHnS.get("descriptionBoxKey"), "test description");
			clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("submitCallbackKey"));
			successMessage = getTextOfSettingsPage("successNotificationHnS");
			if (verifyElementsOfSettingsPage("successNotificationHnS")) {
				if (!successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "help.message.callback.success"))) {
					flag = false;
					LOGGER.error("Callback request sending failed on Help & Support");
				} else {
					flag = true;
					waitForElementsOfSettingsPageForinvisibile("successNotificationHnS");
				}
			} else {
				LOGGER.error("Success message is not getting displayed after submitting callback request");
			}
		} else {
			LOGGER.error("Phone is not getting pre populated from user's profile.");
		}
		return flag;
	}

	/**
	 * @param callbackRequestInfoHnS: Parameters of callback request.
	 * @param LanguageCode: Language code for multiple languages.
	 * @return true or false
	 * @throws Exception
	 */
	public final boolean verifyCallbackRequestOtherHelpAndSupport(HashMap<String, String> callbackRequestInfoHnS, String LanguageCode) throws Exception {
		boolean flag = false;
		String successMessage = null;
		waitForElementsOfSettingsPage(callbackRequestInfoHnS.get("callbackRequestButtonKey"));
		sleeper(2000);
		clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("callbackRequestButtonKey"));
		clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("otherRadioButtonKey"));
		WebElement e = getElementOfSettingsPage(callbackRequestInfoHnS.get("submitCallbackKey"));
		if (e.isEnabled()) {
			enterTextForSettingsPage(callbackRequestInfoHnS.get("otherFirstNameKey"), "Test First Name");
			enterTextForSettingsPage(callbackRequestInfoHnS.get("otherLastNameKey"), "Test Last Name");
			enterTextForSettingsPage(callbackRequestInfoHnS.get("phoneNumberFieldKey"), "457894512");
			clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("otherTimezoneButtonKey"));
			selectValueFromSingleSelectDropDownSettingsPage(1, callbackRequestInfoHnS.get("otherTimezoneListKey"), callbackRequestInfoHnS.get("otherTimezoneBoxKey"));
			clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("otherDateButtonKey"));
			selectValueFromSingleSelectDropDownSettingsPage(1, callbackRequestInfoHnS.get("otherDateListKey"), callbackRequestInfoHnS.get("otherDateBoxKey"));
			clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("otherTimingButtonKey"));
			selectValueFromSingleSelectDropDownSettingsPage(0, callbackRequestInfoHnS.get("otherTimingListKey"), callbackRequestInfoHnS.get("otherTimingBoxKey"));
			enterTextForSettingsPage(callbackRequestInfoHnS.get("descriptionBoxKey"), "test description");
			if (e.isEnabled()) {
				clickOnElementsOfSettingsPage(callbackRequestInfoHnS.get("submitCallbackKey"));
				successMessage = getTextOfSettingsPage("successNotificationHnS");
			} else {
				flag = false;
				LOGGER.error("Submit button is not enabled for callback request of other user on Help & Support");
			}
			if (verifyElementsOfSettingsPage("successNotificationHnS")) {
				if (!successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "help.message.callback.success"))) {
					flag = false;
					LOGGER.error("Callback request sending failed for other user on Help & Support");
				} else {
					flag = true;
				}
			} else {
				LOGGER.error("Success message is not getting displayed after submitting callback request for other user");
			}
		} else {
			LOGGER.error("Submit button is disabled for callback request of other user on Help & Support.");
		}
		return flag;
	}

	/**
	 * This method is used to validate working of callback support on help and support page when configuration is done by MSP settings.
	 * 
	 * @param callbackRequestInfoHnS : this parameter provides details of callback support
	 * @param statusText : provides callback support text.
	 * @param LanguageCode : This parameter provides language code for multiple languages.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyCallbackHelpAndSupport(HashMap<String, String> callbackRequestInfoHnS, String statusText, String LanguageCode) throws Exception {
		boolean flag = false;
		if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
			if (!verifyElementsOfSettingsPage(callbackRequestInfoHnS.get("callbackTileKey"))) {
				flag = true;
			} else {
				LOGGER.error("Callback Support is not updated on Help & Support");
			}
		} else if (statusText.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
			sleeper(3000);
			if (verifyElementsOfSettingsPage(callbackRequestInfoHnS.get("callbackTileKey"))) {
				flag = true;
			} else {
				LOGGER.error("Callback Support is not updated on Help & Support");
			}
		}
		return flag;
	}

	/**
	 * This method is used to enable of callback on Settings page.
	 * 
	 * @param callbackToggleKey : this parameter provides details of toggle for callback support
	 * @param textKey : provides callback support text.
	 * @param LanguageCode : This parameter provides language code for multiple languages.
	 * @return
	 * @throws Exception
	 */
	public final boolean enableCallbackFunctionality(String LanguageCode, String textKey, String callbackToggleKey) throws Exception {
		boolean flag = false;
		String text = null;
		text = getTextOfSettingsPage(textKey);
		if (text.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
			flag = true;
		} else if (text.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
			clickOnElementsOfSettingsPage(callbackToggleKey);
			text = getTextOfSettingsPage(textKey);
			if (text.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * This method clears Help lightning and HP Forms IDs.
	 *
	 * @return Boolean
	 * @throws Exception
	 * 
	 */

	public final Boolean deletionOfWorkflowIds() throws Exception {
		boolean hlIdStatusflag = false;
		boolean wfIdStatusflag = false;
		try {
			clickByJavaScriptOnSettingsPage("preferencesTabWorkflow");
			clickByJavaScriptOnSettingsPage("hlIdEditButton");
			waitForElementsOfSettingsPage("hlIdEditBox");
			clearTextRefreshOfSettingPage("hlIdEditBox");
			clickByJavaScriptOnSettingsPage("hlIdSaveButton");
			waitForElementsOfSettingsPage("hlIdEditButton");
			// Need sleeper to read text successfully else script fails sporadically
			sleeper(3000);
			String hlIdStatus = getTextOfSettingsPage("hlIdStatus");
			if (hlIdStatus.contentEquals("Not Configured")) {
				hlIdStatusflag = true;
			} else {
				LOGGER.error("Help lightning ID not deleted.");
			}
			clickByJavaScriptOnSettingsPage("hpWorkformsTenantIdEditButton");
			waitForElementsOfSettingsPage("hpWorkformsTenantIdEditBox");
			clearTextRefreshOfSettingPage("hpWorkformsTenantIdEditBox");
			clickByJavaScriptOnSettingsPage("hpWorkformsTenantIdSaveButton");
			waitForElementsOfSettingsPage("hpWorkformsTenantIdEditButton");
			// Need sleeper to read text successfully else script fails sporadically
			sleeper(2000);
			String wfIdStatus = getTextOfSettingsPage("hpWorkformsTenantIdStatus");
			if (wfIdStatus.contentEquals("Not Configured")) {
				wfIdStatusflag = true;
			} else {
				LOGGER.error("HP Workforms ID not deleted.");
			}

		} catch (Exception e) {
			LOGGER.error("Exception caught while deleting Workflow IDs for user :" + e.getMessage());
		}
		return (hlIdStatusflag && wfIdStatusflag);
	}

	/**
	 * This method adds HP Forms ID for company user.
	 *
	 * @param id - HP Forms id to add for the company user.
	 * @return String
	 * @throws Exception
	 * 
	 */
	public final boolean addHpFormsId(String helplightningid,String hpFormsId) throws Exception {
		boolean wfIdStatusFlag=false;
		try {
			if (!getTextOfSettingsPage("hpWorkformsTenantIdStatus").equalsIgnoreCase(hpFormsId)) {
				waitForElementsOfSettingsPage("hpWorkformsTenantIdEditButton");
				clickByJavaScriptOnSettingsPage("hpWorkformsTenantIdEditButton");
				waitForElementsOfSettingsPage("hpWorkformsTenantIdEditBox");
				enterTextForSettingsPage("hpWorkformsTenantIdEditBox", hpFormsId);
				clickByJavaScriptOnSettingsPage("hpWorkformsTenantIdSaveButton");
				waitForElementsOfSettingsPage("hpWorkformsTenantIdEditButton");
				// Need sleeper to read text successfully else script fails sporadically
				sleeper(3000);
				wfIdStatusFlag = ((getTextOfSettingsPage("hpWorkformsTenantIdStatus").equalsIgnoreCase(hpFormsId))&&(getTextOfSettingsPage("hlIdStatus").equalsIgnoreCase(helplightningid)));
				LOGGER.info("HP Workforms ID added.");
			}else {
				wfIdStatusFlag=true;
			}
		} catch (Exception e) {
			LOGGER.error("HP Workforms ID not added.");
		}
		return wfIdStatusFlag;
	}

	/**
	 * This method is configured SNOW at Global level.
	 * 
	 * @param url -SNOW configuration url.
	 * @param username -SNOW username for SNOW login.
	 * @param password -SNOW password for SNOW login.
	 * @param languageCode -languageCode for the localization testing
	 * @return boolean -It will return SNOW configuration status(true/false) from SNOW Integration page.
	 */
	public final boolean configuredSNOWAtGlobalLevel(String url, String username, String password, String languageCode) {
		try {
			clickOnElementsOfSettingsPage("serviceNowTab");
			// Test Case 221743: [SNOW] Verify user is able to see 'ServiceNow integration' on MSP Settings -> ServiceNow -> Integration.
			if (getTextOfSettingsPage("snowHeading").equals(getTextLanguage(languageCode, "daas_ui", "settings.service_now.read_only.label")))
				LOGGER.info("SNOW heading is successfully displayed as expected.");
			else
				LOGGER.error("SNOW heading is not displayed as expected.");
			clickOnElementsOfSettingsPage("editIconOfSNOWMSP");
			sleeper(1000); // Here wait is needed
			if (!(getAttributesOfSettingsPage("snowToggleAtMSP", "class").contains("toggle--on"))) {
				clickOnElementsOfSettingsPage("snowToggleAtMSP");
				LOGGER.info("SNOW toggle is ON at Global level");
			} else if (waitForElementsOfSettingsPage("resetConfigurationAtMSP")) {
				clickOnElementsOfSettingsPage("resetConfigurationAtMSP");
				sleeper(1000); // Here wait is needed
				// Test Case 215449: [SNOW] Verify at MSP level after wrong SNOW configuration, User friendly error message should be displayed.
				if (verifySNOWToastNotificationOnSNOWForm(languageCode))
					LOGGER.info("SNOW settings successfully configured at Global level.");
				else
					LOGGER.info("SNOW settings is not configured at Global level as expected.");
				LOGGER.info("SNOW configuration Reset Successfully.");
				sleeper(1000); // Here wait is needed
				clickOnElementsOfSettingsPage("snowToggleAtMSP");
				LOGGER.info("SNOW toggle is ON at Global level");
			} else
				LOGGER.error("Something is wrong with SNOW Enable/Disable Toggle.");
			sleeper(2000); // Here wait is needed
			// Test Case 215220: [SNOW] Verify at MSP level for SNOW configuration mandatory fields is present.
			enterTextForSettingsPage("snowURLMSP", url);
			LOGGER.info("URL entered successfully on SNOW form.");
			enterTextForSettingsPage("snowMSPUserName", username);
			LOGGER.info("UserName entered successfully on SNOW form.");
			enterTextForSettingsPage("snowMSPPassword", password);
			LOGGER.info("Password entered successfully on SNOW form.");
			// Test Case 213923: [DaaS][SNOW][UI] Verify save button is enable after entering value in mandatory fields.
			if (verifyElementIsEnableOfSettingsPage("snowMSPSaveNewConf")) {
				clickOnElementsOfSettingsPage("snowMSPSaveNewConf");
				LOGGER.info("Clicked on SAVE button of SNOW form.");
				if (verifySNOWToastNotificationOnSNOWForm(languageCode))
					LOGGER.info("SNOW settings successfully configured at Global level.");
				else
					LOGGER.info("SNOW settings is not configured at Global level as expected.");
				// Test Case 221501: [SNOW] Verify clientID and clientSecret is displayed with copy icon in read only format at settings page.
				if (verifyElementsOfSettingsPage("clientMSP") && verifyElementsOfSettingsPage("clientSecret") && verifyElementsOfSettingsPage("clientCopyIconMSP") && verifyElementsOfSettingsPage("clientSecretCopyIconMSP")) {
					LOGGER.info("ClientId and ClientSecret and respective Copy icon is displayed on SNOW form.");
				} else
					LOGGER.error("ClientID or CLientSecret or respective Copy icons not dispalyed on Global Level.");
				// Test Case 221755: [SNOW] Verify MSP is able to see URL, UserName, ClientId, and ClientSecret at Settings page.
				if (verifyElementsOfSettingsPage("snowURLMSP") && verifyElementsOfSettingsPage("snowMSPUserName") && verifyElementsOfSettingsPage("snowMSPPassword"))
					LOGGER.info("SNOW url, username and password is displayed on SNOW form.");
				else
					LOGGER.info("SNOW url, username and password is not displayed on SNOW form.");
				clickOnElementsOfSettingsPage("cancelButtonMSP");
				LOGGER.info("Clicked on CANCEL button of SNOW form.");
				sleeper(1000);
				if (verifyElementsOfSettingsPage("editIconOfSNOWMSP")) {
					sleeper(1000); // Here this wait is needed
					if (getTextOfSettingsPage("snowStatusMSP").equals(getTextLanguage(languageCode, "daas_ui", "global.enabled")) && getTextOfSettingsPage("snowURLStatusMSP").equals(url)) {
						LOGGER.info("SNOW 'Enabled' Status is displayed with 'URL' on ServiceNOW Integration page.");
						return true;
					} else
						LOGGER.error("SNOW status is not visible at Global level.");
				} else
					LOGGER.error("Edit icon of Global SNOW is not visible");
			} else
				LOGGER.error("SAVE button is not visible on SNOW form at MSP level.");
			return false;
		} catch (Exception e) {
			LOGGER.error("Exception occured in configuredSNOWAtGlobalLevel " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is Reset existing SNOW configuration at Global level.
	 * 
	 * @param languageCode -languageCode for the localization testing
	 * @return boolean - It will return SNOW configuration status(true/false) from SNOW Integration page.
	 */
	public boolean resetConfigurationAtGlobalLevel(String languageCode) {
		try {
			clickOnElementsOfSettingsPage("serviceNowTab");
			clickOnElementsOfSettingsPage("editIconOfSNOWMSP");
			sleeper(1000);
			if (waitForElementsOfSettingsPage("resetConfigurationAtMSP"))
				clickOnElementsOfSettingsPage("resetConfigurationAtMSP");
			LOGGER.info("User Clicked on Reset Configuration Button.");
			if (verifySNOWToastNotificationOnSNOWForm(languageCode))
				LOGGER.info("SNOW settings successfully configured at Global level.");
			else
				LOGGER.info("SNOW settings is not configured at Global level as expected.");
			clickOnElementsOfSettingsPage("cancelSNOWForm");
			LOGGER.info("User clicked on Cancel button of SNOW form.");
			sleeper(1000);
			if (verifyElementsOfSettingsPage("editIconOfSNOWMSP")) {
				if (getTextOfSettingsPage("snowStatusMSP").equals(getTextLanguage(languageCode, "daas_ui", "global.disabled")) && getTextOfSettingsPage("snowURLStatusMSP").equals((getTextLanguage(languageCode, "daas_ui", "companies.details.label.not_assigned")))) {
					LOGGER.info("SNOW 'Disabled' Status is displayed with 'Not Assigned' on ServiceNOW Integration page.");
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			LOGGER.info("Exception occured in resetConfigurationAtGlobalLevel method " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is verifying SNOW success or error message after SNOW Configuration.
	 * 
	 * @param languageCode -languageCode for the localization testing
	 * @return boolean - It will return SNOW Success message status(true/false) from SNOW Integration page.
	 */
	public boolean verifySNOWToastNotificationOnSNOWForm(String languageCode) {
		try {
			verifyElementsOfSettingsPage("snowPopUpMessageMSPLevel");
			String snowPopUpMessage = getTextOfSettingsPage("snowPopUpMessageMSPLevel");
			String successMessage = getTextLanguage(languageCode, "daas_ui", "companies.details.update.success");
			String snowLabel = getTextLanguage(languageCode, "daas_ui", "settings.service_now.read_only.label");
			successMessage = successMessage.replace("{name}", snowLabel);
			if (snowPopUpMessage.equals(successMessage))
				return true;
			else if (snowPopUpMessage.equals(getTextLanguage(languageCode, "daas_ui", "settings.service_now.error_message_500")))
				return false;
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifySNOWToastNotificationOnSNOWForm method. " + e.getMessage());
		}
		return false;
	}

	/**
	 * This method is verify SNOW form at Global level.
	 * 
	 * @param languageCode -languageCode for the localization testing
	 * @param message - 'right URL' or 'wrong URL' message user can pass as per need.
	 * @param url -SNOW configuration url.
	 * @return boolean -It will return SNOW form status(true/false) from SNOW Integration page.
	 */
	public boolean verifySNOWFormAtGlobalLevel(String languageCode, String message, String url) throws Exception {
		clickOnElementsOfSettingsPage("serviceNowTab");
		clickOnElementsOfSettingsPage("editIconOfSNOWMSP");
		sleeper(1000);
		if (!(getAttributesOfSettingsPage("snowToggleAtMSP", "class").contains("toggle--on"))) {
			clickOnElementsOfSettingsPage("snowToggleAtMSP");
			LOGGER.info("SNOW toggle is ON at Global level");
		} else if (waitForElementsOfSettingsPage("resetConfigurationAtMSP")) {
			clickOnElementsOfSettingsPage("resetConfigurationAtMSP");
			sleeper(1000); // Here wait is needed
			// Test Case 215449: [SNOW] Verify at MSP level after wrong SNOW configuration, User friendly error message should be displayed.
			if (verifySNOWToastNotificationOnSNOWForm(languageCode))
				LOGGER.info("SNOW settings successfully configured at Global level.");
			else
				LOGGER.error("Error SNOW settings is not configured at Global level as expected.");
			LOGGER.info("SNOW configuration Reset Successfully.");
			clickOnElementsOfSettingsPage("snowToggleAtMSP");
			LOGGER.info("SNOW toggle is ON at Global level");
		} else
			LOGGER.error("Error Something is wrong with SNOW Enable/Disable Toggle.");
		sleeper(2000); // Here wait is needed
		// Test Case 215220: [SNOW] Verify at MSP level for SNOW configuration mandatory fields is present.
		enterTextForSettingsPage("snowURLMSP", url);
		LOGGER.info("URL entered successfully on SNOW form.");
		enterTextForSettingsPage("snowMSPUserName", ConstantURL.SNOW_USERNAME);
		LOGGER.info("UserName entered successfully on SNOW form.");
		enterTextForSettingsPage("snowMSPPassword", ConstantURL.SNOW_PASSWORD1);
		LOGGER.info("Password entered successfully on SNOW form.");
		// Test Case 213923: [DaaS][SNOW][UI] Verify save button is enable after entering value in mandatory fields.
		if (verifyElementIsEnableOfSettingsPage("snowMSPSaveNewConf")) {
			clickOnElementsOfSettingsPage("snowMSPSaveNewConf");
			LOGGER.info("Clicked on SAVE button of SNOW form.");
			sleeper(2000);
			// Test Case 215266: [SNOW] Verify at MSP level for URL field error message
			if (message.equals("wrong URL")) {
				if (getTextOfSettingsPage("snowInvalidURLSNOWForm").equals(getTextLanguage(languageCode, "daas_ui", "settings.service_now.error_message_408"))) {
					sleeper(1000);
					clickOnElementsOfSettingsPage("cancelSNOWForm");
					return true;
				} else {
					sleeper(1000);
					clickOnElementsOfSettingsPage("cancelSNOWForm");
					return false;
				}
			} else { // Test Case 211655: [SNOW] Verify if SNOW url is configured at A-MSP level then any B-MSP will not allowed to use same url for SNOW setting.
				if (getTextOfSettingsPage("snowInvalidURLSNOWForm").equals(getTextLanguage(languageCode, "daas_ui", "settings.service_now.error_message_409"))) {
					clickOnElementsOfSettingsPage("cancelSNOWForm");
					return true;
				} else {
					clickOnElementsOfSettingsPage("cancelSNOWForm");
					return false;
				}
			}
		} else
			LOGGER.error("SAVE button is not visible on SNOW form at MSP level.");
		return false;
	}

	/**
	 * This method will get clientID and clientSecret form Global level SNOW Form.
	 * 
	 * @return String[] - String array of clientID and clientSecret.
	 */
	public String[] getClientIDAndClientSecretFromGlobalSNOW() {
		try {
			if (verifyElementsOfSettingsPage("editIconOfSNOWMSP")) {
				clickOnElementsOfSettingsPage("editIconOfSNOWMSP");
				waitForElementsOfSettingsPage("resetConfigurationAtMSP");
				String[] data = new String[2];
				data[0] = getTextOfSettingsPage("clientMSP");
				data[1] = getTextOfSettingsPage("clientSecret");
				waitForElementsOfSettingsPage("cancelButtonMSP");
				clickOnElementsOfSettingsPage("cancelButtonMSP");
				return data;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in getClientIDAndClientSecretFromGlobalSNOW method." + e.getMessage());
		}
		return null;
	}

	/**
	 * This is a method to select first option from any dropdown
	 * 
	 * @param dropdownListKey - Locator of dropdown elements
	 * @return - String value of the option selecetd from the dropdown
	 */
	public final String selectFirstOptionFromDropdownOnSettingsPage(String dropdownListKey) {
		try {
			return selectFirstValueFromDropdown(settingsPageProperties.getProperty(dropdownListKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectFirstOptionFromDropdownOnSettingsPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to get the text of a list as a list itself
	 * 
	 * @param key - locator of element
	 * @return - arraylist of the text of all elements present in the list
	 */

	public final ArrayList<String> getTextOfListOfSettingsPage(String key) {
		try {
			return getTextOfList(settingsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfListOfSettingsPage " + e.getMessage()));
			return null;
		}
	}

	public final void waitUntilElementIsVisibleOfSettingsPage(String key) throws Exception {
		waitUntilElementIsVisible(settingsPageProperties.getProperty(key));
	}

	/**
	 * @param mspAuthToken: bearer token
	 * @param url: To specify the Authentication url.
	 * @throws Exception
	 */
	public final boolean getSkuDetails(String mspAuthToken, String url) throws Exception {
		boolean checkSkuId = false, checkSkuName = false;
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken);
		Response response = httpRequest.get(url);
		String expected = response.asString();
		JSONObject jsonObject;
		jsonObject = new JSONObject(expected);
		@SuppressWarnings("unchecked")
		List<Map<String, String>> list = new Gson().fromJson(jsonObject.get("transactions").toString(), List.class);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("status").equalsIgnoreCase("Active")) {
				checkSkuId = (list.get(i).get("sku_id") != null);
				checkSkuName = (!list.get(i).get("sku").isEmpty());
			}
		}
		return (checkSkuId && checkSkuName);
	}

	/** This method is used to create role.
	 * 
	 * @param LanguageCode
	 * @SelectAllPermission - Select all permission for creating custom role
	 * @return
	 * @throws Exception */
	public final boolean createPermission(String LanguageCode, String SelectAllPermission) throws Exception {
		boolean flag = false;
		clickOnElementsOfSettingsPage("addRolesButton");
		waitForPageLoaded();//Need to keep wait here as add roles popup is taking time to load in Veneer3
		waitForElementsOfSettingsPage("titleText");
		LOGGER.info("Clicked on Add roles button.");
		String generatedString = RandomStringUtils.random(5, true, false);
		waitForElementsOfSettingsPageForinvisibile("loaderIconRoleModalBox");
		//sleeper(5000); //Sleeper needed to wait untill modal text is being loaded.
		waitForElementsOfSettingsPage("nameField");
		if (getTextOfSettingsPage("titleText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "roles.add.information.title"))) {
			enterTextForSettingsPage("nameField", "SeleniumTestRole" + generatedString);
			enterTextForSettingsPage("descriptionField", generatedString);
			LOGGER.info("Name and Description entered.");
			clickOnElementsOfSettingsPage("nextButton");
			if (getTextOfSettingsPage("titleText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "roles.add.manageability.title"))) {
				clickOnElementsOfSettingsPage("dropdownButtonManaging");
				selectValueFromSingleSelectDropDownSettingsPage(1, "listOfManagingElements", "managingBox");
				sleeper(2000);
				clickByJavaScriptOnSettingsPage("dropdownButtonManagingAfter");
				clickOnElementsOfSettingsPage("nextButton");
				LOGGER.info("Managing dropdown entered.");
				if (SelectAllPermission.equals(getTextLanguage(LanguageCode, "daas_ui", "global.yes"))) {
					for (int i = 0; i < getElementsOfSettingsPage("listOfCheckboxes").size(); i++) {
						getElementsOfSettingsPage("listOfCheckboxes").get(i).click();
					}
				}
				clickOnElementsOfSettingsPage("addRoleButton");
				sleeper(20000);
					LOGGER.info("Role added successfully.");
					createdRole = "SeleniumTestRole" + generatedString;
					if (verifyCreatedRoleInList()) {
						flag = true;
					}
			} else {
				LOGGER.error("Manageability tab did not open successfully");
				flag = false;
			}
		} else {
			LOGGER.error("Information tab did not open successfully");
			flag = false;

		}
		return flag;
	}

	/**
	 * This method is used to verify created role in list page.
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean verifyCreatedRoleInList() throws Exception {
		boolean flag = false;
		try {
			refreshPage();
			waitForPageLoaded();
			sleeper(3000);
			List<WebElement> listOfRoles = getElementsOfSettingsPage("createdRoleList");
			if (listOfRoles.size() > 0) {
				for (int i = 0; i < listOfRoles.size(); i++) {
					if (listOfRoles.get(i).getText().equalsIgnoreCase(createdRole)) {
						flag = true;
						LOGGER.info("Created role got reflected on list.");
						break;
					}
				}
				if (!flag) {
					LOGGER.error("Created role did not reflect on list.");
				}
			} else {
				LOGGER.error("No role is present in list.");
			}
		} catch (Exception e) {
			LOGGER.error("Exception in verifying created role on list" + e.getMessage());
			return false;
		}
		return flag;
	}

	/**
	 * This method is used to fetch all the custom role Ids
	 * 
	 * @param api
	 * @param body
	 * @param index
	 * @param id
	 * @return
	 */
	public final List<String> getAllRoleIds(String api, String body, int index, String id) {
		try {
			List<String> listOfIds = new ArrayList<String>();
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.get(api);
			String expected = response.asString();
			JSONArray roleArray;
			roleArray = new JSONArray(expected);
			for (int i = 0; i < roleArray.length(); i++) {
				JSONObject jsonObject = roleArray.getJSONObject(i);
				if (jsonObject.get("roleType").toString().equalsIgnoreCase("CUSTOM")) {
					listOfIds.add(jsonObject.get("id").toString());
				}
			}
			LOGGER.info("Collected List of IDs");
			return listOfIds;		
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllRoleIds: " + e.getMessage());
			return null;
		}
	}

	/**
	 * This method will remove all USers
	 * 
	 * @param environment
	 * @return
	 * @throws Exception
	 */
	public final boolean removeAllRoles(String tenantID, String environmentURL, List<String> roleIDs, String environment) {
		try {
			boolean flag = false;
			if (roleIDs.size() > 0) {
				for (int i = 0; i < roleIDs.size(); i++) {
					int code = getStatusCode(environmentURL + ConstantURL.DELETE_API_ROLE1 + tenantID + ConstantURL.DELETE_API_ROLE2 + roleIDs.get(i), "{}", "DELETE", environment);
					if (code != CommonVariables.CODESUCCESS && code != CommonVariables.CODEDELETE && code != CommonVariables.CODEOK) {
						flag = false;
						LOGGER.error("Delete API got failed while removing Roles.");
						break;
					}
					flag = true;
				}
			} else {
				LOGGER.info("There are no Roles present.");
				flag = true;
			}
			refreshPage();
			waitForPageLoaded();
			return flag;
		} catch (Exception e) {
			LOGGER.error("Delete API got failed while removing Roles. Exception occured in removeAllRoles: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to add custom role using api
	 * 
	 * @param environment : Env name
	 * @param tenantID: tenant ID of User (MSP/Partner Admin)
	 * @return : Arraylist of added role details
	 */
	public final ArrayList<String> addCustomRoleApi(String environment, String tenantID, String userType) {
		try {
			ArrayList<String> roleDetails = new ArrayList<String>();
			String roleName = generateRandomString(10);
			String roleDescription = generateRandomString(10);
			String resourceURL = null;
			String body = null;
			if (userType == "MSP")
				body = "{\"displayName\":\"" + roleName + "\",\"description\":\"" + roleDescription + "\",\"tenantType\":\"" + userType + "\",\"tenantTypeVariation\":\"DEFAULT\",\"status\":\"ACTIVE\",\"visibleRoles\":[],\"visibleToRoles\":[\"" + SettingsVariables.VISIBLE_ROLE_ID_MSP + "\",\"" + SettingsVariables.VISIBLE_ROLE_ID_RESELLER + "\"],\"permissions\":[]}";
			else if (userType == "RESELLER")
				body = "{\"displayName\":\"" + roleName + "\",\"description\":\"" + roleDescription + "\",\"tenantType\":\"" + userType + "\",\"tenantTypeVariation\":\"DEFAULT\",\"status\":\"ACTIVE\",\"visibleRoles\":[],\"visibleToRoles\":[\"" + SettingsVariables.VISIBLE_ROLE_ID_MSP + "\",\"" + SettingsVariables.VISIBLE_ROLE_ID_RESELLER + "\"],\"permissions\":[]}";
			else if (userType == "CUSTOMER")
				body = "{\"displayName\":\"" + roleName + "\",\"description\":\"" + roleDescription + "\",\"tenantType\":\"" + userType + "\",\"tenantTypeVariation\":\"DEFAULT\",\"status\":\"ACTIVE\",\"visibleRoles\":[],\"visibleToRoles\":[\"" + SettingsVariables.VISIBLE_ROLE_ID_ITA + "\",\"" + SettingsVariables.VISIBLE_ROLE_ID_MSP + "\",\"" + SettingsVariables.VISIBLE_ROLE_ID_RESELLER + "\"],\"permissions\":[]}";
			else {
				LOGGER.error("Invalid usertype passed, please use MSP/RESELLER/CUSTOMER only.");
				return null;
			}
			roleDetails.add("Role" + roleName);
			roleDetails.add("Desc" + roleDescription);
			if (environment.equals("US-STABLE"))
				resourceURL = environmentProperties.getProperty("StableUSADDROLEURL");
			else if (environment.equals("EU-STABLE"))
				resourceURL = environmentProperties.getProperty("StableEUADDROLEURL");
			else if (environment.equals("US-STAGING"))
				resourceURL = environmentProperties.getProperty("StagingUSADDROLEURL");
			else
				resourceURL = environmentProperties.getProperty("StagingEUADDROLEURL");
			int code = getStatusCode(resourceURL + ConstantURL.ADD_ROLE_RESOURCE1 + tenantID + ConstantURL.ADD_ROLE_RESOURCE2, body, "POST", environment);
			if (code != CommonVariables.CODEPASSED) {
				LOGGER.error("Adding role using API failed");
				return null;
			} else {
				refreshPage();
				waitForPageLoaded();
				waitForElementsOfSettingsPage("tableOverlay");
				LOGGER.info("Adding role using API passed for role: " + roleDetails.get(0));
				return roleDetails;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in addCustomRoleApi: " + e.getMessage());
			return null;
		}
	}

	/**
	 * This method validates role edit.
	 * 
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyEditRole(String LanguageCode) throws Exception {
		boolean flag = false;
		String roleDescription = generateRandomString(10);
		Actions action = new Actions(getDriver());
			WebElement roleElement = getDriver().findElement(By.xpath("//*[contains(@title,'" + createdRole + "')]/ancestor::tr"));
			action.moveToElement(roleElement).build().perform();
		sleeper(5000);
		WebElement editRoleIcon = getDriver().findElement(By.xpath("//*[contains(@title,'" + createdRole + "')]/ancestor::tr/td[3]"));
		editRoleIcon.click();
		clickOnElementsOfSettingsPage("editRoleOption");
		enterTextForSettingsPage("descriptionField", roleDescription);
		clickOnElementsOfSettingsPage("nextButton");
		clickOnElementsOfSettingsPage("nextButton");
		clickOnElementsOfSettingsPage("addRoleButton");
		waitForElementsOfSettingsPage("successNotificationHnS");
		String successMessage = getTextOfSettingsPage("successNotificationHnS");
		waitForElementsOfSettingsPageForinvisibile("successNotificationHnS");
		if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_update_successful").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "users.details.role")))) {
			flag = true;
		} else {
			LOGGER.error("Error occured,role did not updated successfully.");
		}
		return flag;
	}

	/**
	 * This method validates duplicate role creation.
	 * 
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyDuplicateRole(String LanguageCode) throws Exception {
		boolean flag = false;
		Actions action = new Actions(getDriver());
			WebElement roleElement = getDriver().findElement(By.xpath("//*[contains(@title,'" + createdRole + "')]/ancestor::tr"));
			action.moveToElement(roleElement).build().perform();
		sleeper(5000);
		WebElement editRoleIcon = getDriver().findElement(By.xpath("//*[contains(@title,'" + createdRole + "')]/ancestor::tr/td[3]"));
		editRoleIcon.click();
		waitForElementsOfSettingsPageForinvisibile("loaderIconRoleModalBox");
		clickOnElementsOfSettingsPage("duplicateRoleOption");
		clickOnElementsOfSettingsPage("nextButton");
		clickOnElementsOfSettingsPage("nextButton");
		clickOnElementsOfSettingsPage("addRoleButton");
		waitForElementsOfSettingsPage("successNotificationHnS");
		String successMessage = getTextOfSettingsPage("successNotificationHnS");
		//waitForElementsOfSettingsPageForinvisibile("closeButtonNotification");
		if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_success").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "users.details.role")))) {
			if (verifyCreatedDuplicateRoleInList()) {
				flag = true;
			}
		} else {
			LOGGER.error("Error occured,duplicate role did not add successfully.");
		}
		return flag;
	}

	/**
	 * This method is used to verify created duplicate role in list page.
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean verifyCreatedDuplicateRoleInList() throws Exception {
		boolean flag = false;
		try {
			refreshPage();
			waitForElementsOfSettingsPage("settingsTitle");
			sleeper(5000);
			scrollByCoordinatesofAPage(0, 250);
			List<WebElement> listOfRoles = getElementsOfSettingsPage("createdRoleList");
			if (listOfRoles.size() > 0) {
				for (int i = 0; i < listOfRoles.size(); i++) {
					if (listOfRoles.get(i).getText().equalsIgnoreCase("Copy of " + createdRole)) {
						flag = true;
						LOGGER.info("Created duplicate role got reflected on list.");
						break;
					}
				}
				if (!flag) {
					LOGGER.error("Created duplicate role did not reflect on list.");
				}
			} else {
				LOGGER.error("No role is present in list.");
			}
		} catch (Exception e) {
			LOGGER.error("Exception in verifying created role on list" + e.getMessage());
			return false;
		}
		return flag;
	}

	/**
	 * This method validates role removal.
	 * 
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyRemoveRole(String LanguageCode) throws Exception {
		boolean flag = false;
		Actions action = new Actions(getDriver());
			WebElement roleElement = getDriver().findElement(By.xpath("//*[contains(@title,'" + createdRole + "')]/ancestor::tr"));
			action.moveToElement(roleElement).build().perform();
		sleeper(5000);
		WebElement editRoleIcon = getDriver().findElement(By.xpath("//*[contains(@title,'" + createdRole + "')]/ancestor::tr/td[3]"));
		editRoleIcon.click();
		clickOnElementsOfSettingsPage("removeRoleOption");
		waitForElementsOfSettingsPage("submitRemoveButton");
		clickOnElementsOfSettingsPage("submitRemoveButton");
		waitForElementsOfSettingsPage("successNotificationHnS");
		String successMessage = getTextOfSettingsPage("successNotificationHnS");
		//waitForElementsOfSettingsPageForinvisibile("closeButtonNotification");
		if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_remove_success").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "users.details.role")))) {
			if (verifyRoleRemovalOnList()) {
				flag = true;
			}
		} else {
			LOGGER.error("Error occured,role did not remove successfully.");
		}
		return flag;
	}
	
	/**
	 * This method is used to verify removed role in list page.
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean verifyRoleRemovalOnList() throws Exception {
		boolean flag = true;
		try {
			refreshPage();
			waitForElementsOfSettingsPage("settingsTitle");
			sleeper(5000);
			scrollByCoordinatesofAPage(0, 250);
			String deletedRole = "Copy of " + createdRole;
			List<WebElement> listOfRoles = getElementsOfSettingsPage("createdRoleList");
			if (listOfRoles.size() > 0) {
				for (int i = 0; i < listOfRoles.size(); i++) {
					if (listOfRoles.get(i).getText().equalsIgnoreCase(deletedRole)) {
						flag = false;
						LOGGER.error("Removed role is still present in list.");
						break;
					}
				}
				if (flag) {
					LOGGER.info("Removed role did not reflect on list.");
				}
			} else {
				LOGGER.info("No role is present in list.");
			}
		} catch (Exception e) {
			LOGGER.error("Exception in verifying removed role on list" + e.getMessage());
			return false;
		}
		return flag;
	}

	/** This method validate newly created custom role in role dropdown.
	 * @return
	 * @throws Exception
	 */
	public boolean verifyCustomRoleInRoleDropdown() throws Exception{
		boolean flag = false;
		enterTextForSettingsPage("roleSearchBox", createdRole);
		flag = verifySingleSelectDropdownTextSettingsPage("listOfRoles",createdRole);
		return flag;
		
	}
	
	/** This method validates custom role on details page.
	 * @return
	 * @throws Exception
	 */
	public boolean verifyCustomRoleDetailsPage() throws Exception{
		boolean flag = false;
		clickOnElementsOfSettingsPage("roleNameList");
		sleeper(3000);
		if(waitForElementsOfSettingsPage("supportTeamDetailsPageName")){
			if(getTextOfSettingsPage("roleDetailsPage").contains(createdRole)){
				flag= true;
				LOGGER.info("Custom role got reflected on role section of details page.");
			}else{
				LOGGER.error("Custom role is not present on support team details page.");
			}
		}else{
			LOGGER.error("Support team details page did not load.");
		}
		return flag;	
	}
	
	/** This method validates logs of add/removal of roles.
	 * @param activityType
	 * @return
	 */
	public boolean verifyDescriptionOnLogsPage(String activityType,String roleType) {
		try {
			LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
			TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
			tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableConfigurationButton");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
			LOGGER.info("Clicked on table configuration button");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			LOGGER.info("Clicked on save button");
			tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationButton");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
			LOGGER.info("Clicked on table configuration button");
//			if (roleType.equals("CUSTOM")) {
//				//tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
//				LOGGER.info("Clicked on reset to default");
//			}
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			LOGGER.info("Clicked on save button");
			//logPage.waitForElementsOfLogPage("tableOverlay");
            
			//logPage.waitForElementsOfLogPage("firstRecordOnLogsPage");
				logPage.waitForElementsOfLogPage("firstCheckbox");
				logPage.clickOnElementsOfLogPage("firstCheckbox");
			LOGGER.info("Clicked on first record on logs page");
			if (activityType.equalsIgnoreCase("Add")) {
				if (logPage.getTextOfLogPage("logsPageDescription").contains("was added to the system")) {
					//logPage.switchToDifferentTabOfLogsPage();
					logPage.clickOnElementsOfLogPage("closeInformationPanel");
					return true;
				} else {
					LOGGER.error("Description on logs page is incorrect when role is added successfully");
					return false;
				}
			} else if (activityType.equalsIgnoreCase("Remove")) {
				if (logPage.getTextOfLogPage("logsPageDescription").contains("was permanently removed from the system")) {
					//logPage.switchToDifferentTabOfLogsPage();
					logPage.clickOnElementsOfLogPage("closeInformationPanel");
					return true;
				} else {
					LOGGER.error("Description on logs page is incorrect when role is removed successfully");
					return false;
				}
			} else if(activityType.equalsIgnoreCase("Edit")) {
				if (logPage.getTextOfLogPage("logsPageDescription").contains("was modified")) {
					//logPage.switchToDifferentTabOfLogsPage();
					logPage.clickOnElementsOfLogPage("closeInformationPanel");
					return true;
				} else {
					LOGGER.error("Description on logs page is incorrect when role is edited successfully");
					return false;
				}
			}else{
				LOGGER.error("Incorrect activityType passed, Please pass ADD/REMOVE/EDIT");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyDescriptionOnLogsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to add Base role using api
	 * 
	 * @param environment : Env name
	 * @param levelType: level  for user (MSP/RESELLER/CUSTOMER/ROOT)
	 * @param resellerType :type of reseller (DEFAULT/ONBOARD_AUTHORIZED)
	 * @return : Arraylist of added role details
	 */
	public final ArrayList<String> addBaseRoleApi(String environment, String levelType, String resellerType) {
		try {
			ArrayList<String> roleDetails = new ArrayList<String>();
			String baseRoleName = generateRandomString(10);
			String baseRoleDescription = generateRandomString(10);
			String baseResourceURL = null;
			String body = null;
			if (levelType == "MSP")
				body = "{\"displayName\":\"" + baseRoleName + "\",\"description\":\"" + baseRoleDescription + "\",\"tenantType\":\"" + levelType + "\",\"tenantTypeVariation\":\"DEFAULT\",\"status\":\"ACTIVE\",\"visibleRoles\":[],\"visibleToRoles\":[\"" + getEnvironmentSpecificData(System.getProperty("environment"), "VISIBLE_BASE_ROLE_ID_HPADMIN") + "\",\"" + getEnvironmentSpecificData(System.getProperty("environment"), "VISIBLE_BASE_ROLE_ID_MSP") + "\"],\"permissions\":[]}";
			else if (levelType == "RESELLER" && resellerType == "DEFAULT")
				body = "{\"displayName\":\"" + baseRoleName + "\",\"description\":\"" + baseRoleDescription + "\",\"tenantType\":\"" + levelType + "\",\"tenantTypeVariation\":\"DEFAULT\",\"status\":\"ACTIVE\",\"visibleRoles\":[],\"visibleToRoles\":[\"" + getEnvironmentSpecificData(System.getProperty("environment"), "VISIBLE_BASE_ROLE_ID_HPADMIN") + "\",\"" + getEnvironmentSpecificData(System.getProperty("environment"), "VISIBLE_BASE_ROLE_ID_RESELLER") + "\"],\"permissions\":[]}";
			else if (levelType == "RESELLER" && resellerType == "ONBOARD_AUTHORIZED")
				body = "{\"displayName\":\"" + baseRoleName + "\",\"description\":\"" + baseRoleDescription + "\",\"tenantType\":\"" + levelType + "\",\"tenantTypeVariation\":\"ONBOARD_AUTHORIZED\",\"status\":\"ACTIVE\",\"visibleRoles\":[],\"visibleToRoles\":[\"" + getEnvironmentSpecificData(System.getProperty("environment"), "VISIBLE_BASE_ROLE_ID_HPADMIN") + "\",\"" + getEnvironmentSpecificData(System.getProperty("environment"), "VISIBLE_BASE_ROLE_ID_RESELLERPLUS") + "\"],\"permissions\":[]}";
			else if (levelType == "CUSTOMER")
				body = "{\"displayName\":\"" + baseRoleName + "\",\"description\":\"" + baseRoleDescription + "\",\"tenantType\":\"" + levelType + "\",\"tenantTypeVariation\":\"DEFAULT\",\"status\":\"ACTIVE\",\"visibleRoles\":[],\"visibleToRoles\":[\"" + getEnvironmentSpecificData(System.getProperty("environment"), "VISIBLE_BASE_ROLE_ID_ITA") + "\",\"" + getEnvironmentSpecificData(System.getProperty("environment"), "VISIBLE_BASE_ROLE_ID_MSP") + "\",\"" + getEnvironmentSpecificData(System.getProperty("environment"), "VISIBLE_BASE_ROLE_ID_RESELLERPLUS") + "\"],\"permissions\":[]}";
			else if (levelType == "ROOT")
				body = "{\"displayName\":\"" + baseRoleName + "\",\"description\":\"" + baseRoleDescription + "\",\"tenantType\":\"" + levelType + "\",\"tenantTypeVariation\":\"DEFAULT\",\"status\":\"ACTIVE\",\"visibleRoles\":[],\"visibleToRoles\":[\"" + getEnvironmentSpecificData(System.getProperty("environment"), "VISIBLE_BASE_ROLE_ID_HPADMIN") + "\"],\"permissions\":[]}";
			else {
				LOGGER.error("Invalid usertype passed, please use MSP/RESELLER/CUSTOMER/ROOT only.");
				return null;
			}
			roleDetails.add("Base Role " + baseRoleName);
			roleDetails.add("Base Desc " + baseRoleDescription);
			if (environment.equals("US-STABLE"))
				baseResourceURL = environmentProperties.getProperty("StableUSADDROLEURL");
			else if (environment.equals("EU-STABLE"))
				baseResourceURL = environmentProperties.getProperty("StableEUADDROLEURL");
			else if (environment.equals("US-STAGING"))
				baseResourceURL = environmentProperties.getProperty("StagingUSADDROLEURL");
			else
				baseResourceURL = environmentProperties.getProperty("StagingEUADDROLEURL");
			int code = getStatusCode(baseResourceURL + ConstantURL.ADD_BASE_ROLE_RESOURCE1, body, "POST", environment);
			if (code != CommonVariables.CODEPASSED) {
				LOGGER.error("Adding base role using API failed");
				return null;
			} else {
				waitForElementsOfSettingsPage("tableOverlay");
				createdBaseRole = baseRoleName;
				LOGGER.info("Adding base role using API passed for role: " + roleDetails.get(0));
				return roleDetails;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in addBaseRoleApi: " + e.getMessage());
			return null;
		}
	}
	

	
	
	/**
	 * This method is used to verify created base role in list page.
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean verifyCreatedBaseRoleInList() throws Exception {
		boolean flag = false;
		try {
			waitForPageLoaded();
			if(verifyElementsOfSettingsPage("clearFilter"))
			{
			clearFiltersOfRolesandPermissionListPage("clearFilter");
			}
				if(verifyElementsOfSettingsPage("clearName"))
				{
				clickOnElementsOfSettingsPage("clearName");
				}
			Thread.sleep(2000);
			waitForElementsOfSettingsPage("roleSearchBox");
			enterTextForSettingsPage("roleSearchBoxAppAdmin", createdBaseRole);
			Thread.sleep(5000);// after entering value in search box it take time to load the search result
			waitForElementsOfSettingsPage("firstRowRole");
			String expectedName = getTextOfSettingsPage("rolefirstName");
			if (expectedName.equalsIgnoreCase(createdBaseRole)) {
				LOGGER.info("Base Role is added successfully in base role list");
				flag = true;
			} else {
				LOGGER.error("Base Role not added successfully in base role list");
				return false;
			}
			
		} catch (Exception e) {
			LOGGER.error("Exception in verifying created base role on role list" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	/**
	 * This method is used to fetch all the base role Ids
	 * 
	 * @param api - URL from you which you want the data
	 * @param body - request body
	 * @param id - event name required
	 * @return
	 */
	public final List<String> getAllBaseRoleIds(String api, String body,String id) {
		try {
			List<String> listOfIds = new ArrayList<String>();
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.post(api);
			String expected = response.asString();
			JSONArray roleArray;
			roleArray = new JSONArray(expected);
			for (int i = 0; i < roleArray.length(); i++) {
				JSONObject jsonObject = roleArray.getJSONObject(i);
				if (jsonObject.get("roleType").toString().equalsIgnoreCase("BASE")) {
					listOfIds.add(jsonObject.get(id).toString());
				}
			}
			return listOfIds;
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllBaseRoleIds: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * This method will remove all base roles
	 * 
	 * @param environment : Env name
	 * @param environmentURL : Env url
	 * @param roleIDs -list of base role ids to remove
	 * @return
	 * @throws Exception
	 */
	public final boolean removeAllBaseRoles(String environmentURL, List<String> roleIDs, String environment) {
		try {
			boolean flag = false;
			if (roleIDs.size() > 0) {
				for (int i = 0; i < roleIDs.size(); i++) {
					int code = getStatusCode(environmentURL + ConstantURL.ADD_BASE_ROLE_RESOURCE1 + roleIDs.get(i), "{}", "DELETE", environment);
					if (code != CommonVariables.CODESUCCESS && code != CommonVariables.CODEDELETE && code != CommonVariables.CODEOK) {
						flag = false;
						LOGGER.error("Delete API got failed while removing Base Roles.");
						break;
					}
					flag = true;
				}
			} else {
				LOGGER.info("There are no Base Roles present.");
				flag = true;
			}
			refreshPage();
			waitForPageLoaded();
			return flag;
		} catch (Exception e) {
			LOGGER.error("Delete API got failed while removing Base Roles. Exception occured in removeAllBaseRoles: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * This method validates duplicate base role creation.
	 * @param LanguageCode - language code to check current language and check for the localization
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyDuplicateBaseRole(String LanguageCode) throws Exception {
		boolean flag = false;
		Actions action = new Actions(getDriver());
		
		waitForPageLoaded();
		waitForElementsOfSettingsPage("roleSearchBoxAppAdmin");
			if(verifyElementsOfSettingsPage("clearName"))
			{
			clickOnElementsOfSettingsPage("clearName");
			}
		if(verifyElementsOfSettingsPage("clearFilter"))
		{
		clearFiltersOfRolesandPermissionListPage("clearFilter");
		}
		Thread.sleep(3000);// after clearing filters it take time to load the search result
		waitForElementsOfSettingsPage("roleSearchBoxAppAdmin");
		enterTextForSettingsPage("roleSearchBoxAppAdmin", createdBaseRole);
		Thread.sleep(4000);// after entering value in search box it take time to load the search result
		waitForElementsOfSettingsPage("firstRowRole");
		WebElement roleElement = getDriver().findElement(By.linkText(createdBaseRole));
		action.moveToElement(roleElement).build().perform();
		sleeper(5000);
		clickOnElementsOfSettingsPage("editRoleIcon");
		clickOnElementsOfSettingsPage("duplicateRoleOptionAppAdmin");
		clickOnElementsOfSettingsPage("nextButton");
		clickOnElementsOfSettingsPage("nextButton");
		waitForElementsOfSettingsPage("addRoleButton");
		clickOnElementsOfSettingsPage("addRoleButton");
		waitForElementsOfSettingsPage("successNotificationHnS");
		String successMessage = getTextOfSettingsPage("successNotificationHnS");
		if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_success").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "users.details.role")))) {
			if (verifyCreatedDuplicateBaseRoleInList()) {
				flag = true;
			}
		} else {
			LOGGER.error("Error occured,duplicate base role did not add successfully.");
		}
		return flag;
	}
	
	/**
	 * This method validates base role removal.
	 * 
	 * @param LanguageCode - language code to check current language and check for the localization
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyRemoveBaseRole(String LanguageCode) throws Exception {
		boolean flag = false;
		Actions action = new Actions(getDriver());
		
		waitForPageLoaded();
		waitForElementsOfSettingsPage("roleSearchBox");
		if(verifyElementsOfSettingsPage("clearFilter"))
		{
		clearFiltersOfRolesandPermissionListPage("clearFilter");
		}
		Thread.sleep(3000);// after clearing filters it take time to load the search result
		waitForElementsOfSettingsPage("roleSearchBox");
		enterTextForSettingsPage("roleSearchBox", createdBaseRole);
		Thread.sleep(4000);// after entering value in search box it take time to load the search result
		waitForElementsOfSettingsPage("firstRowRole");
		WebElement roleElement = getDriver().findElement(By.linkText(createdBaseRole));
		action.moveToElement(roleElement).build().perform();
		sleeper(5000);
		clickOnElementsOfSettingsPage("editRoleIcon");
		clickOnElementsOfSettingsPage("removeRoleOption");
		clickOnElementsOfSettingsPage("removeButton");
		String successMessage = getTextOfSettingsPage("successNotificationHnS");
		clickOnElementsOfSettingsPage("closeButtonNotification");
		if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_remove_success").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "users.details.role")))) {
			if (verifyBaseRoleRemovalOnList()) {
				flag = true;
			} 
		} else {
			LOGGER.error("Error occured,role did not remove successfully.");
		}
		return flag;
	}
	
	/**
	 * This method is used for clearing any filters applied on role and permission list page
	 * @param clearFilterKey - locator of clear button
	 * @throws Exception
	 */
	public final void clearFiltersOfRolesandPermissionListPage(String clearFilterKey) throws Exception {
		clearFilters(settingsPageProperties.getProperty(clearFilterKey));
	}
	
	/**
	 * This method is used to verify created duplicate base role in list page.
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean verifyCreatedDuplicateBaseRoleInList() throws Exception {
		boolean flag = false;
		try {
			waitForPageLoaded();
			waitForElementsOfSettingsPage("roleSearchBoxAppAdmin");
				if(verifyElementsOfSettingsPage("clearName"))
				{
				clickOnElementsOfSettingsPage("clearName");
				}
			if(verifyElementsOfSettingsPage("clearFilter"))
			{
			clearFiltersOfRolesandPermissionListPage("clearFilter");
			}
			Thread.sleep(2000);
			waitForElementsOfSettingsPage("roleSearchBoxAppAdmin");
			enterTextForSettingsPage("roleSearchBoxAppAdmin", "Copy of " + createdBaseRole);
			Thread.sleep(5000);// after entering value in search box it take time to load the search result
			waitForElementsOfSettingsPage("firstRowRole");
			String expectedName = getTextOfSettingsPage("rolefirstName");
			if (expectedName.equalsIgnoreCase("Copy of " + createdBaseRole)) {
				LOGGER.info("Created duplicate base role got reflected on list.");
				flag = true;
			} else {
				LOGGER.error("Created duplicate base role got reflected on list.");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception in verifying created duplicate base role on list" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	/**
	 * This method is used to verify removed base role in list page.
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean verifyBaseRoleRemovalOnList() throws Exception {
		boolean flag = true;
		try {
			waitForPageLoaded();
			waitForElementsOfSettingsPage("roleSearchBox");
			if(verifyElementsOfSettingsPage("clearFilter"))
			{
			clearFiltersOfRolesandPermissionListPage("clearFilter");
			}
			Thread.sleep(4000);
			waitForElementsOfSettingsPage("roleSearchBox");
			enterTextForSettingsPage("roleSearchBox", createdBaseRole);
			Thread.sleep(4000);// after entering value in search box it take time to load the search result
			waitForElementsOfSettingsPage("firstRowRole");
			String expectedName = getTextOfSettingsPage("rolefirstName");
			if (expectedName.equalsIgnoreCase(createdBaseRole)) {
				LOGGER.error("Removed role is still present in list.");
				flag = false;	
			}
			if (flag) {
				LOGGER.info("Removed role did not reflect on list.");
			}
			else {
					LOGGER.info("No role is present in list.");
			}	
		} catch (Exception e) {
			LOGGER.error("Exception in verifying removed role on list" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	
	/**
	 * This method is used to create base role through UI.
	 * 
	 * @param LanguageCode - language code to check current language and check for the localization
	 * @return
	 * @throws Exception
	 */
	public final boolean createBasePermission(String LanguageCode) throws Exception {
		boolean flag = false;
		clickOnElementsOfSettingsPage("addRolesButton");
		LOGGER.info("Clicked on Add roles button.");
		String generatedString = RandomStringUtils.random(5, true, false);
		if (getTextOfSettingsPage("titleText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "roles.add.information.title"))) {
			enterTextForSettingsPage("nameField", "SeleniumTestBaseRole" + generatedString);
			enterTextForSettingsPage("baseDescriptionField", generatedString);
			clickOnElementsOfSettingsPage("baseLeveldropdown");
			selectValueFromSingleSelectDropDownSettingsPage(1, "listOfLevelElements", "levelBox");
			LOGGER.info("Name and Description and level entered.");
			clickOnElementsOfSettingsPage("nextButton");
			if (getTextOfSettingsPage("titleText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "roles.add.manageability.title"))) {
				Thread.sleep(4000);
				clickOnElementsOfSettingsPage("dropdownButtonManaging");
				selectValueFromSingleSelectDropDownSettingsPage(1, "listOfManagingElements", "managingBox");
					sleeper(2000);
					clickByJavaScriptOnSettingsPage("dropdownButtonManagingAfter");
				clickOnElementsOfSettingsPage("nextButton");
				LOGGER.info("Managing dropdown entered.");
				for (int i = 0; i < getElementsOfSettingsPage("listOfCheckboxes").size(); i++) {
					getElementsOfSettingsPage("listOfCheckboxes").get(i).click();
				}
				clickOnElementsOfSettingsPage("addRoleButton");
				String successMessage = getTextOfSettingsPage("successNotificationHnS");
				if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_success").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "users.details.role")))) {
					LOGGER.info("Base Role added successfully.");
					createdBaseRole = "SeleniumTestBaseRole" + generatedString;
					if (verifyCreatedBaseRoleInList()) {
						flag = true;
					}
				} else {
					LOGGER.error("Error occured,base role did not added successfully.");
				}

			} else {
				LOGGER.error("Manageability tab did not open successfully");
				flag = false;
			}
		} else {
			LOGGER.error("Information tab did not open successfully");
			flag = false;

		}
		return flag;
	}

	/** This is a method to hover mouse on an element
	 * 
	 * @param key - Locator of element
	 */
	public final void mousehoverOnSettingsPage(String key) {
		try {
			mouseHover(settingsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnSettingsPage " + e.getMessage()));
		}
	}

	/**
	 * This method set attribute to block to be used in import
	 * 
	 * @param key - Key of the locator
	 */
	public final void setAttributeOfSettings(String key) {
		setAttributeForImport(settingsPageProperties.getProperty(key));
	}
	
	/**
	 * This method is used to validate the flow before the notification is generated
	 * 
	 * @return - integer value of unread notifications
	 */
	public int preNotificationCheck() {
		try {
			int countUnreadNotification = 0;
			String count = null;

			if (verifyElementsOfSettingsPage("notificationCount")) {
				count = getTextOfSettingsPage("notificationCount");
				countUnreadNotification = Integer.valueOf(count);

				waitForElementsOfSettingsPage("notificationBellIcon");
				clickOnElementsOfSettingsPage("notificationBellIcon");
				LOGGER.info("Clicked on notification bell icon");

				if (verifyElementsOfSettingsPage("unreadNotification")) {
					Actions action = new Actions(getDriver());
					action.moveToElement(getElementOfSettingsPage("unreadNotification")).build().perform();
					sleeper(5000);
					if (verifyElementsOfSettingsPage("notificationCount")) {
						count = getTextOfSettingsPage("notificationCount");
						countUnreadNotification = Integer.valueOf(count);
					}
				} else {
					LOGGER.info("First Notification is already read.");
				}

			} else {
				countUnreadNotification = 0;
			}
			LOGGER.info("Unread notification count is fetched.");
			return countUnreadNotification;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method preNotificationCheck " + e.getMessage()));
			return 0;
		}
	}

	/**
	 * this method is used to verify notification post orders import 
	 * 
	 * @param fileName - name of the order file 
	 * @param countUnread - count of unread notification
	 * @return
	 */
	public boolean postNotificationCheckImport(String fileName, int countUnread) {
		try {
			boolean flag = false;
			String notificationText = null;
			String notificationCountString = null;
			int notificationCount = 0;
			waitForElementsOfSettingsPage("notificationBellIcon");
			clickOnElementsOfSettingsPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			if (waitForElementsOfSettingsPage("unreadNotificationText")) {
				notificationText = getTextOfSettingsPage("unreadNotificationText");
				notificationCountString = getTextOfSettingsPage("notificationCount");
				notificationCount = Integer.valueOf(notificationCountString);
				if(notificationText.equalsIgnoreCase("Your orders from " + fileName + " were imported successfully.") && notificationCount == (countUnread + 1)) {
			flag = true;
				}
				else {
					flag = false;
				}
			
		} 
			return flag;
			}catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckImport " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to validate flow of importing the orders
	 * 
	 * @param getImportOrdersInfo
	 * @param languageCode
	 * @param fileName
	 */
	public void verifyImportOrders(String languageCode, String fileName) {
		try {
			waitForPageLoaded();

			LOGGER.info("Clicked on browse button");
			sleeper(3000);
			WebElement addFile = getElementOfSettingsPage("fileImport");
			addFile.sendKeys(ConstantPath.IMPORT_PATH+fileName);
			sleeper(3000);
			clickOnElementsOfSettingsPage("importOrderButton");
			LOGGER.info("Clicked on submit import button");
			sleeper(10000);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportOrders " + e.getMessage()));
		}
	}
	
	/** This method is used to select all checkboxes in Incident eligibility modal.
	 * @param LanguageCode: Language code should be passed.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyIncidentEligibilityModal(String LanguageCode) throws Exception{
		boolean flag = false;
		clickOnElementsOfSettingsPage("editIncidentEligibility");
		List<WebElement> premierCareCheckboxes= getElementsOfSettingsPage("permierCareCheckboxes");
		List<WebElement> caseCreationCheckboxes= new ArrayList<WebElement>();
		List<WebElement> incidentTypeListModal= getElementsOfSettingsPage("incidentTypeModal");
		ArrayList<String> listOfIncidentsModal = new ArrayList<String>();
		ArrayList<String> listOfIncidentsListText = new ArrayList<String>();
		List<WebElement> listOfIncidentsList= new ArrayList<WebElement>();
		ArrayList<String> listOfCaseCreatedListText = new ArrayList<String>();
		List<WebElement> listOfCaseCreated= new ArrayList<WebElement>();
		for(int i=0;i<incidentTypeListModal.size();i++){
			if(!premierCareCheckboxes.get(i).getAttribute("class").contains("checked")){
				premierCareCheckboxes.get(i).click();
			}
			listOfIncidentsModal.add(incidentTypeListModal.get(i).getText());
		}
		caseCreationCheckboxes=getElementsOfSettingsPage("caseCreationCheckboxes");
		for(int i=0;i<caseCreationCheckboxes.size();i++){
			if(!caseCreationCheckboxes.get(i).getAttribute("class").contains("checked")){
				caseCreationCheckboxes.get(i).click();
			}
		}
		clickOnElementsOfSettingsPage("submitIncidentElibilityButton");
		String successMessage = getTextOfSettingsPage("successNotification");
		clickOnElementsOfSettingsPage("successNotificationClose");
		if(successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.eligibility.toast.success"))){
			listOfIncidentsList = getElementsOfSettingsPage("incidentHeaderList");
			listOfCaseCreated = getElementsOfSettingsPage("caseCreationHeaderList");
			for(int i=0;i<listOfIncidentsList.size();i++){
				listOfIncidentsListText.add(listOfIncidentsList.get(i).getText());
				listOfCaseCreatedListText.add(listOfCaseCreated.get(i).getText());
			}
			if(listOfIncidentsListText.equals(listOfIncidentsModal) && listOfCaseCreatedListText.equals(listOfIncidentsModal) ){
				flag=true;
				LOGGER.info("Incidents got reflected on Incident eligibility list tile.");
			}else{
				LOGGER.error("Incidents did not reflect on Incident eligibility list tile.");
			}
		}else{
			LOGGER.error("Error in setting incident eligibility tile.");
		}
		return flag;
	}
	
	/** This method is used to de select all checkboxes in Incident eligibility modal.
	 * @param LanguageCode: Language code should be passed.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyIncidentEligibilityModalDeselect(String LanguageCode) throws Exception{
		boolean flag = false;
		List<WebElement> listOfIncidentsHeaderList= getElementsOfSettingsPage("incidentHeaderList");
		if(!listOfIncidentsHeaderList.get(0).getText().equals("-")){
		clickOnElementsOfSettingsPage("editIncidentEligibility");
		List<WebElement> premierCareCheckboxes= getElementsOfSettingsPage("permierCareCheckboxes");
		List<WebElement> incidentTypeListModal= getElementsOfSettingsPage("incidentTypeModal");
		List<WebElement> listOfCaseCreated= new ArrayList<WebElement>();
			for (int i = 0; i < incidentTypeListModal.size(); i++) {
					if (premierCareCheckboxes.get(i).getAttribute("aria-checked").contains("mixed")) {
						premierCareCheckboxes.get(i).click();
					}
					if (premierCareCheckboxes.get(i).getAttribute("aria-checked").contains("true")) {
						premierCareCheckboxes.get(i).click();
					}
			}
		clickOnElementsOfSettingsPage("submitIncidentElibilityButton");
		String successMessage = getTextOfSettingsPage("successNotification");
		if(successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.eligibility.toast.success"))){
			listOfIncidentsHeaderList = getElementsOfSettingsPage("incidentHeaderList");
			listOfCaseCreated = getElementsOfSettingsPage("caseCreationHeaderList");
			if(listOfIncidentsHeaderList.get(0).getText().equals("-") && listOfCaseCreated.get(0).getText().equals("-")){
				flag=true;
				LOGGER.info("Incidents got cleared on Incident eligibility list tile.");
			}else{
				LOGGER.error("Incidents did not cleared on Incident eligibility list tile.");
			}
		}else{
			LOGGER.error("Error in clearing incident eligibility tile.");
		}
		
	}else{
		LOGGER.info("Incident eligibility modal is already empty.");
		flag = true;
	}
		return flag;
	}
	
	
	/** This method is used to for checkboxes selection of Incident type in Incident eligibility modal.
	 * @param LanguageCode: Language code should be passed.
	 * @param incidentType: Incident type shoould be passed.
	 * @param action
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyPremierCareCheckBoxSelection(String LanguageCode, String incidentType, String action) throws Exception {
		boolean flag = false;
		ArrayList<String> listOfIncidentsModal = new ArrayList<String>();
		List<WebElement> listOfIncidentsHeaderList = new ArrayList<WebElement>();
		List<WebElement> listOfCaseCreated = new ArrayList<WebElement>();
		clickOnElementsOfSettingsPage("editIncidentEligibility");
		WebElement desiredPremierCare = getDriver().findElement(By.xpath("//*[@id='dui-eligibility-content']//input[contains(@name,'checkbox_" + incidentType + "_premier_care_all')]//ancestor::label/span"));
		WebElement desiredCase = null;
		WebElement incident = null;
		if (action.equalsIgnoreCase("Enable")) {
			if (!desiredPremierCare.getAttribute("class").equalsIgnoreCase("Checked")) {
				desiredPremierCare.click();
				desiredCase = getDriver().findElement(By.xpath("//*[@id='dui-eligibility-content']//input[contains(@name,'checkbox_" + incidentType + "_case_creation_all')]//ancestor::label/span"));
				incident = getDriver().findElement(By.xpath("//*[@id='dui-eligibility-content']//input[contains(@name,'checkbox_" + incidentType + "_case_creation_all')]//ancestor::label/span//ancestor::div[contains(@class,'dui-accordian-title')]/div[contains(@class,'dui-subtype-title')]"));
				desiredCase.click();
			}
		} else if (action.equalsIgnoreCase("Disable")) {
			if (desiredPremierCare.getAttribute("class").equalsIgnoreCase("Checked")) {
				desiredPremierCare.click();
				desiredCase = getDriver().findElement(By.xpath("//*[@id='dui-eligibility-content']//input[contains(@name,'checkbox_" + incidentType + "_case_creation_all')]//ancestor::label/span"));
				incident = getDriver().findElement(By.xpath("//*[@id='dui-eligibility-content']//input[contains(@name,'checkbox_" + incidentType + "_case_creation_all')]//ancestor::label/span//ancestor::div[contains(@class,'dui-accordian-title')]/div[contains(@class,'dui-subtype-title')]"));
				desiredCase.click();
			}
		}
		listOfIncidentsModal.add(incident.getText());
		clickOnElementsOfSettingsPage("submitIncidentElibilityButton");
		String successMessage = getTextOfSettingsPage("successNotification");
		clickOnElementsOfSettingsPage("successNotificationClose");
		if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.eligibility.toast.success"))) {
			listOfIncidentsHeaderList = getElementsOfSettingsPage("incidentHeaderList");
			listOfCaseCreated = getElementsOfSettingsPage("caseCreationHeaderList");
			if (listOfIncidentsHeaderList.get(0).getText().equals(listOfIncidentsModal.get(0)) && listOfCaseCreated.get(0).getText().equals(listOfIncidentsModal.get(0))) {
				flag = true;
				LOGGER.info("Incidents got reflected on Incident eligibility list tile.");
			} else {
				LOGGER.error("Incidents did not cleared on Incident eligibility list tile.");
			}
		} else {
			LOGGER.error("Error in clearing incident eligibility tile.");
		}
		return flag;
	}
	
	/** This method validate create case button on incident details page.
	 * @param CaseButtonVisibleFlag:True/False should be passed.
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public boolean validateCreateCaseButton(boolean CaseButtonVisibleFlag) throws InterruptedException, IOException{
		boolean flag = false;
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		sleeper(2000);
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		if (incidentDetailsPage.verifyElementOfIncidentDetailsPage("incidentDetailsPageTitle")){
		LOGGER.info("Redirected to incident details page");
		if(CaseButtonVisibleFlag){
			if(incidentDetailsPage.verifyElementOfIncidentDetailsPage("createCaseButton")){
				flag = true;
				LOGGER.info("Create Case button is visible when checkbox for premier care is selected from App Admin.");
			}	
		}else{
			if(!incidentDetailsPage.verifyElementOfIncidentDetailsPage("createCaseButton")){
				flag = true;
				LOGGER.info("Create Case button is hidden when checkbox for premier care is de-selected from App Admin.");
			}
		}
		}
		else{
			LOGGER.info("Incident details page did not load successfully.");
		}
		return flag;	
	}

	/**
	 * This method submits an incident using APIs for user submitted incident based on type and subtype given and this is overridden method of the above method and facilitates the user
	 * to pass customized title and description so that same can be validated in the received email and other related sources
	 * 
	 * @param type of the incident
	 * @param subtype of the incident
	 * @param incidentTitle of the incident
	 * @param incidentDescription of the incident
	 * @param host of the environment
	 * @param tenantId of the company
	 * @param userAuthToken of the logged in User
	 * @param companyEmailId of the company under which device is enrolled
	 * @param deviceId of the enrolled device
	 * @return HashMap having information about the created incident
	 * @throws Exception
	 */
	public String submitCaseUsingAPICaseCreation(String type, String subtype, String incidentTitle, String incidentDescription, String tenantId, String deviceId) throws Exception {
		EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
		String host = enrollFakeDevice.getEnvironment(System.getProperty("environment"));
		String userAuthToken= getTokenFromLocalStorage("dui_token_e");
		JSONObject parsedJwtToken = EnrollFakeDevice.jwtTokenParse(userAuthToken);
		String UserID = parsedJwtToken.getString("user");
		String incidentId = "";
		try {
			String serverUrl = "";
			if (Strings.isNullOrEmpty(incidentTitle)) {
				incidentTitle = "Incident for " + type + "";
			}
			if (Strings.isNullOrEmpty(incidentDescription)) {
				incidentDescription = "New incident added for type " + type + " and subtype  " + subtype + " by automation script";
			}

			type = "HW_HEALTH";
			subtype = "HDD_PREDICTIVE_FAILURE";

			if (host.toLowerCase().contains("usdev")) {
				serverUrl = environmentProperties.getProperty("StableUSADDROLEURL");
			} else if (host.toLowerCase().contains("eudev")) {
				serverUrl = environmentProperties.getProperty("StableEUADDROLEURL");
			} else if (host.toLowerCase().contains("usstagingms")) {
				serverUrl = environmentProperties.getProperty("StagingUSADDROLEURL");
			} else if (host.toLowerCase().contains("eustagingms")) {
				serverUrl = environmentProperties.getProperty("StagingEUADDROLEURL");
			} else if (host.toLowerCase().contains("www.hpdaas.com")) {
				serverUrl = environmentProperties.getProperty("ProdUSADDROLEURL");
			} else if (host.toLowerCase().contains("eu.hpdaas.com")) {
				serverUrl = environmentProperties.getProperty("ProdEUADDROLEURL");
			} else {
				LOGGER.error("Incorrect host name given, please check the host name!!." + host + " is not a valid entry.");
				return incidentId;
			}
			
			String apiUrl = serverUrl + "services/ccc-incidents/1.0/tenants/" + tenantId + "/incidents";
			URL url = new URL(apiUrl);
			// Make a device authentication post call
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("Authorization", "Bearer " + userAuthToken);
			String body = "{  \"description\": \"" + incidentDescription + "\",  \"deviceId\": \"" + deviceId + "\",  \"errorcode\": \"string\",  \"sourceId\": \"string\",  \"sourceLocation\": \"string\",  \"status\": \"NEW\",  \"subtype\": \"" + subtype + "\",  \"tenantId\": \"" + tenantId + "\",  \"title\": \"" + incidentTitle + "\",  \"type\": \"" + type + "\",  \"userId\":\""+UserID+"\",\"errorcode\":\"332\",\"cmeasure\":\"PROTECTION_DISABLED\"}";
			httpConnection.setDoOutput(true);
			byte[] outputInBytes = body.getBytes("UTF-8");
			OutputStream os = httpConnection.getOutputStream();
			os.write(outputInBytes);
			os.close();
			// Get the response code for the incident submission call
			int responseCode = httpConnection.getResponseCode();
			LOGGER.info("Response code for incident submission: " + responseCode);
			// Validation for the submit incident API response code
			if (responseCode == 201) {
				String response = getResponse(responseCode, httpConnection);
				JSONObject json = new JSONObject(response.toString());
				incidentId = json.getString("displayId");

			} else {
				LOGGER.error("Incident couldn't be submitted, expected response code: 201, actual response code for API call: " + responseCode);
				return incidentId;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in method submitCaseUsingAPI: " + ex);
			return null;
		}
		return incidentId;
	}
	
	/**
	 * This method will validate the response code and convert the response body into string
	 * 
	 * @param responseCode - The response code for the desired API
	 * @param con - HttpConnection object of the desired API
	 * @return response - response converted into String format
	 */
	public static String getResponse(int responseCode, HttpURLConnection con) throws Exception {
		StringBuffer response = new StringBuffer();
		try {
			if (responseCode == HttpURLConnection.HTTP_CREATED) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				while ((inputLine = bufferedReader.readLine()) != null) {
					response.append(inputLine);
				}
				bufferedReader.close();
				LOGGER.info("Response from desired API: " + response.toString());
			} else {
				LOGGER.error("API for submitting an incident did not work");
			}
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in method getResponse: " + ex.getStackTrace());
		}
		return response.toString();
	}
	
	/**
	 * This methos is to create a case on incident details page
	 * 
	 * @param location - shipping location for the case
	 * @throws Exception
	 */
	public void createCase() throws Exception {
		String FN = generateRandomString(5);
		String LN = generateRandomString(5);
		String addressLine1 = generateRandomString(5);
		String zipCode = generateRandomString(5);
		String state =generateRandomString(5);
		String addressLine2 =generateRandomString(5);
		String city =generateRandomString(5);
		String emailId =generateRandomString(5) + "@gmail.com";
		String phoneNumber = generateRandomNumber();
		clickOnElementsOfSettingsPage("createCaseButton");
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		LOGGER.info("Moved to second step of case creation");
		waitForElementsOfSettingsPage("locationDropdownButton");
		clickOnElementsOfSettingsPage("locationDropdownButton");
		selectFirstOptionFromDropdownOnSettingsPage("locationDropdownOptions");
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		waitForElementsOfSettingsPage("countryDropdownButton");
		clickOnElementsOfSettingsPage("countryDropdownButton");
		selectFirstOptionFromDropdownOnSettingsPage("countryDropdownOptions");
		enterTextForSettingsPage("addressLine1", addressLine1);
		enterTextForSettingsPage("addressLine2", addressLine2);
		enterTextForSettingsPage("city", city);
		enterTextForSettingsPage("state", state);
		enterTextForSettingsPage("postalCode", zipCode);
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		enterTextForSettingsPage("firstName", FN);
		enterTextForSettingsPage("lastName", LN);
		enterTextForSettingsPage("emailId", emailId);
		enterTextForSettingsPage("phoneNumber", phoneNumber);
		LOGGER.info("Selected location for case creation");
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		LOGGER.info("Moved to third step of case creation");
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		LOGGER.info("Case created");
	}

	/** This method is used to subscribe incident type via api.
	 * @param environment: Environment
	 * @param apiSubscription: api url for subscription api
	 * @param body: body of request.
	 * @return
	 */
	public final boolean subscribeFunctionalityApi(String environment, String apiSubscription, String body) {
		try {
			boolean flag = false;
			int code = getStatusCode(apiSubscription, body, "POST", environment);
			if (code != CommonVariables.CODEPASSED) {
				flag = false;
				LOGGER.error("Error occured in Incident subscription.");
			} else {
				flag = true;
				refreshPage();
				waitForPageLoaded();
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in subscribeFunctionalityApi: " + e.getMessage());
			return false;
		}
	}
	
	/** This method validate newly created custom role in role dropdown.
	 * @return
	 * @throws Exception
	 */
	public boolean verifyCustomRoleInUserRoleDropdown() throws Exception {
		try {
			boolean flag = false;
			flag = verifySingleSelectDropdownTextSettingsPage("userListOfRoles", createdRole);
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyCustomRoleInUserRoleDropdown: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * This method is to add a location on incident details page
	 * 
	 * @throws Exception
	 */
	public void createLocation() throws Exception {
		String FN = generateRandomString(5);
		String LN = generateRandomString(5);
		String addressLine1 = generateRandomString(5);
		String zipCode = generateRandomString(5);
		String state =generateRandomString(5);
		String addressLine2 =generateRandomString(5);
		String city =generateRandomString(5);
		String emailId =generateRandomString(5) + "@gmail.com";
		String phoneNumber = generateRandomNumber();
		waitForElementsOfSettingsPage("serviceLocationDropdownButton");
		clickOnElementsOfSettingsPage("serviceLocationDropdownButton");
		selectFirstOptionFromDropdownOnSettingsPage("serviceLocationDropdownOptions");
		clickOnElementsOfSettingsPage("nextButtonAddServiceLocation");
		waitForElementsOfSettingsPage("serviceCountryDropdownButton");
		clickOnElementsOfSettingsPage("serviceCountryDropdownButton");
		selectFirstOptionFromDropdownOnSettingsPage("countryDropdownOptions");
		enterTextForSettingsPage("addressLine1Textbox", addressLine1);
		enterTextForSettingsPage("addressLine2Textbox", addressLine2);
		enterTextForSettingsPage("cityTextbox", city);
		enterTextForSettingsPage("stateTextboxOnServiceLocation", state);
		enterTextForSettingsPage("postalCodeOnServiceLocation", zipCode);
		clickOnElementsOfSettingsPage("nextButtonAddServiceLocation");
		enterTextForSettingsPage("firstNameServiceLocation", FN);
		enterTextForSettingsPage("lastNameServiceLocation", LN);
		enterTextForSettingsPage("emailId", emailId);
		enterTextForSettingsPage("phoneNumber", phoneNumber);
		clickOnElementsOfSettingsPage("nextButtonAddServiceLocation");
		clickOnElementsOfSettingsPage("nextButtonAddServiceLocation");
		LOGGER.info("Location added");
	}
	
	/**
	 * This method is to create a case on incident details page by requesting the location from end user
	 * 
	 * @throws Exception
	 */
	public void createCaseRequestingLocationFromEndUser() throws Exception {
		clickOnElementsOfSettingsPage("createCaseButton");
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		LOGGER.info("Moved to second step of case creation");
		waitForElementsOfSettingsPage("locationDropdownButton");
		clickOnElementsOfSettingsPage("locationDropdownButton");
		enterTextForSettingsPage("searchBox", "Request Location from End User");
		selectFirstOptionFromDropdownOnSettingsPage("locationDropdownOptions");
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		LOGGER.info("Moved to third step of case creation");
		enterTextForSettingsPage("requestMessageTextBox", "Request Location message from End User");
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		LOGGER.info("Moved to last step of case creation");
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		LOGGER.info("Case created");
		waitForElementsOfSettingsPage("successNotification");
		clickOnElementsOfSettingsPage("successNotificationClose");
	}
	
	/**
	 * this method is used to verify notification post orders import for veneer version 3
	 * 
	 * @param fileName - name of the order file 
	 * @return
	 */
	public boolean postNotificationCheckImportInV3(String fileName) {
		try {
			boolean flag = false;
			String notificationText = null;
			waitForElementsOfSettingsPage("notificationBellIcon");
			clickOnElementsOfSettingsPage("notificationBellIcon");
			sleeper(60000);
			LOGGER.info("Clicked on notification bell icon");
			// Get Notification message text in local variable.
			String expectednotificationtext = "Your assets from "+ fileName +" were imported successfully. Please check the logs for more details.";
			String expectednotificationtext_1 = "Your orders from "+ fileName +" were imported successfully.";
			if (waitForElementsOfSettingsPage("unreadNotificationText")) {
				notificationText = getTextOfSettingsPage("unreadNotificationText");
				if(notificationText.equalsIgnoreCase(expectednotificationtext)||notificationText.equalsIgnoreCase(expectednotificationtext_1)) {
			flag = true;
				}
				else {
					flag = false;
				}
			
		} 
			return flag;
			}catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckImport " + e.getMessage()));
			return false;
		}
	}

/** 
 * This method verifies email content to add space
 * @param environment -environment
 * @param inboxEmailAddress -email address of user who receives the mail
 * @param incidentID - subject of the mail
 * @param privateDomain - domain
 * @return
 * @throws Exception
 */
public String verifyEmailContent (String subject, String environment, String inboxEmailAddress, boolean privateDomain) throws Exception {
	String mailContent;
	Mailinator objMailinator = new Mailinator("",inboxEmailAddress.split("@")[0]);
	sleeper(5000);//required wait because script is breaking over here
	MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailForSettingPage(subject, inboxEmailAddress, privateDomain);
	if (objMailinatorEmail != null) {
		mailContent = objMailinatorEmail.getBody();
		return getHtmlParserMailSpaceThree(mailContent);
	} else {
		LOGGER.error("Mail not found");
		return "";
	}
}
/**
 * method to get html parser for email to replace Three spaces
 * @param mailContent - mailinator mail content
 * @return
 * @throws Exception
 */
public final String getHtmlParserMailSpaceThree(String mailContent) throws Exception {

	String actualMailContent;
	InputStream in = org.apache.commons.io.IOUtils.toInputStream(mailContent, "UTF-8");
	BodyContentHandler handler = new BodyContentHandler();
	Metadata metadata = new Metadata();
	ParseContext pcontext = new ParseContext();
	HtmlParser htmlparser = new HtmlParser();
	htmlparser.parse(in, handler, metadata, pcontext);
	actualMailContent = handler.toString().replaceAll("\\s{3,}", " ").trim();
	return actualMailContent;
}

	/**
	 * This method is used to validate flow of importing the orders
	 *
	 * @param fileName
	 */
	public void verifyImportOrdersForMultiPlan(String fileName) {
		try {
			sleeper(3000);
			WebElement addFile = getElementOfSettingsPage("fileImport");
			addFile.sendKeys(fileName);
			sleeper(3000);
			clickOnElementsOfSettingsPage("importOrderButton");
			LOGGER.info("Clicked on submit import button");
			sleeper(10000);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportOrders " + e.getMessage()));
		}
	}
	
	/**
	 * This method is used to verify notification post unsuccessful orders import
	 * for veneer version 3
	 * 
	 * @param fileName - name of the order file
	 * @return
	 */
	public boolean postNotificationCheckUnsuccessfulImportInV3(String fileName) {
		try {
			boolean flag = false;
			String errorMessage = "There was a problem while importing your orders from "+fileName+" file. Please check the errors for more information.";
			String errorMessage1 = "There was a problem while importing your orders from "+fileName+" file.";
			String notificationText = null;
			waitForElementsOfSettingsPage("notificationBellIcon");
			clickOnElementsOfSettingsPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			if (waitForElementsOfSettingsPage("unreadNotificationText")) {
				notificationText = getTextOfSettingsPage("unreadNotificationText");
				if (notificationText.equalsIgnoreCase(errorMessage1)||notificationText.equalsIgnoreCase(errorMessage)) {
					flag = true;
				} else {
					flag = false;
				}

			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckUnsuccessfulImportInV3 " + e.getMessage()));
			return false;
		}
	}
	
	public final void clearSelectionOfSettingPage(String key) throws Exception {
		clearAllFilters(settingsPageProperties.getProperty(key));
	}

	/**
	 * This methos is to create a reactive case
	 * 
	 * @param deviceName - name of the device to be selected from the dropdown
	 * @throws Exception
	 */
	public void createReactiveCase(String deviceName) throws Exception {
		String FN = generateRandomString(5);
		String LN = generateRandomString(5);
		String addressLine1 = generateRandomString(5);
		String zipCode = generateRandomString(5);
		String state =generateRandomString(5);
		String addressLine2 =generateRandomString(5);
		String city =generateRandomString(5);
		String emailId =generateRandomString(5) + "@gmail.com";
		String phoneNumber = generateRandomNumber();
		clickOnElementsOfSettingsPage("createReactiveCaseButton");
		waitForElementsOfSettingsPage("deviceDropdownButton");
		sleeper(3000);
		clickOnElementsOfSettingsPage("deviceDropdownButton");
		String selectDevice = deviceName + " - " + deviceName;
		selectTextValueFromDropdownOfSettingsPage("deviceDropdownOptions",selectDevice,"dropdownBoxAfterSelection");
		pressKey(Keys.ESCAPE);
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		LOGGER.info("Moved to second step of case creation");
		waitForElementsOfSettingsPage("issueDropdownButton");
		clickOnElementsOfSettingsPage("issueDropdownButton");
		selectFirstOptionFromDropdownOnSettingsPage("issueDropdownOptions");
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		waitForElementsOfSettingsPage("locationDropdownButton");
		clickOnElementsOfSettingsPage("locationDropdownButton");
		selectFirstOptionFromDropdownOnSettingsPage("locationDropdownOptions");
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		waitForElementsOfSettingsPage("countryDropdownButton");
		clickOnElementsOfSettingsPage("countryDropdownButton");
		selectFirstOptionFromDropdownOnSettingsPage("countryDropdownOptions");
		enterTextForSettingsPage("addressLine1", addressLine1);
		enterTextForSettingsPage("addressLine2", addressLine2);
		enterTextForSettingsPage("city", city);
		enterTextForSettingsPage("state", state);
		enterTextForSettingsPage("postalCode", zipCode);
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		enterTextForSettingsPage("firstName", FN);
		enterTextForSettingsPage("lastName", LN);
		enterTextForSettingsPage("emailId", emailId);
		enterTextForSettingsPage("phoneNumber", phoneNumber);
		LOGGER.info("Selected location for case creation");
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		LOGGER.info("Moved to third step of case creation");
		clickOnElementsOfSettingsPage("nextButtonCaseCreation");
		LOGGER.info("Reactive Case created");
	}

	/**
	 * This is the method to get a list of all the elements and compare it with a string
	 * 
	 * @param key - locator of the list
	 * @param text - string to compare
	 * @return
	 * @throws Exception
	 */
	public final boolean compareListOfSettingsPage(String key, String text) throws Exception {
		List<WebElement> menuList = getElementsTillAllElementsPresent(settingsPageProperties.getProperty(key));
		boolean flag = false;
		for (WebElement listItem : menuList) {
			String value = listItem.getText();
			if(value.equals(text)) {
				flag = true;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * Method is fetching the Incident Eligibility options for type/subtype and
	 * comparing it with the expected values
	 * 
	 * @param expectedPremierCareTypesSubtypes
	 * @return : boolean true if matching or else boolean false if not matching
	 * @throws Exception
	 */
	public final boolean verifyIncidentEligibilityTileTypesSubTypes(ArrayList<String> expectedTypesSubtypes,
			String incidentsubtye) throws Exception {
		boolean flag = false;
		ArrayList<String> actualListOfTypesSubTypes = new ArrayList<String>();
		try {
			
			List<WebElement> actualPremierCareTypesSubtypes = getElementsOfSettingsPage(incidentsubtye);
			
			for (int i = 0; i < actualPremierCareTypesSubtypes.size(); i++) {
				actualListOfTypesSubTypes.add(actualPremierCareTypesSubtypes.get(i).getText());
			}
			flag = expectedTypesSubtypes.equals(actualListOfTypesSubTypes);
			return flag;
		}

		catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyIncidentEligibilityTileTypesSubTypes " + e.getMessage()));
			return false;
		}

	}

	/**
	 * This method is used to validate working of service support on help and
	 * support page when configuration is done by MSP settings.
	 *
	 * @param serviceRequestInfoHnS : this parameter provides details of service
	 *                              support
	 * @param LanguageCode          : This parameter provides language code for
	 *                              multiple languages.
	 * @param tileName              : This parameter provides tile name
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyHelpAndSupportTile(HashMap<String, String> requestInfoHnS, String LanguageCode,
			String tileName) throws Exception {
		boolean flag = false;
		if (verifyElementsOfSettingsPage(requestInfoHnS.get(tileName + "TileKey"))) {
			System.out.println("Tile verified");
			flag = true;
		} else {
			LOGGER.error("Service Request is not updated on Help & Support");
		}
		return flag;
	}

	/**
	 * This method is used to validate Components of help and support page tile when
	 * configuration is done by MSP settings.
	 *
	 * @param requestInfoHnS : this parameter provides details of tile components
	 * @param tileName       : this parameter provide details of tile name
	 * @param expectedText   : this parameter provide details of expected text
	 * @param url            : this parameter is used to check Navigation url
	 * @return
	 * @throws Exception
	 */
	public final void verifyHelpAndSupportComponents(HashMap<String, String> requestsInfoHnS, String LanguageCode,
			String tileName, String expectedText, String urlText) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(verifyElementsOfSettingsPage(requestsInfoHnS.get(tileName + "IconKey")));
		softAssert.assertTrue(
				matchTextOfSettingsPage(tileName + "Title",
						getTextLanguage(LanguageCode, "daas_ui", "help." + expectedText + ".title")),
				"Track Request support tile title is incorrect");
		softAssert.assertTrue(
				matchTextOfSettingsPage(tileName + "ModalTitle",
						getTextLanguage(LanguageCode, "daas_ui", "help." + expectedText + ".description")),
				"Description on Track Request support tile is incorrect");
		softAssert.assertTrue(
				matchTextOfSettingsPage(tileName + "Button",
						getTextLanguage(LanguageCode, "daas_ui", "help." + expectedText + ".button.label")),
				"Track Request support tile button text is incorrect");
		softAssert.assertTrue(verifyTileNavigation(tileName + "Button", urlText));
		softAssert.assertAll();
		LOGGER.info("Test cases for Track request for IT_ADMIN_CONTRACTUAL passed successfully");
	}

	/**
	 * This method is used to validate working of Requests Navigation on help and
	 * support page
	 *
	 */
	public final boolean verifyTileNavigation(String ButtonKey, String url) throws Exception {
		clickOnElementsOfSettingsPage(ButtonKey);
		switchToDifferentTab();
		if (getUrlOfCurrentPage().contains(url)) {
			LOGGER.info("User lands successfully on " + getUrlOfCurrentPage());
			switchBackToPreviousTab();
			return true;
		} else {
			LOGGER.error("User failed to lands on requested page for " + ButtonKey);
			return false;
		}
	}
	
	
	
	/**
	 * @param fileName
	 * @param ellipseskey
	 * @param duplicateerrorkey
	 * @return
	 */
	public boolean postNotificationCheckUnsuccessfulImportInV3Loop(String fileName, String ellipseskey,
			String duplicateerrorkey) {
		try {
			boolean flag = false;
			String errorMessage = "There was a problem with 1 records from " + fileName
					+ " file. Please click below to view the failed records";
			String successMessage = "Your orders from " + fileName + " were imported successfully.";
			waitForElementsOfSettingsPage("notificationBellIcon");
			clickOnElementsOfSettingsPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			WebElement failureObject = null;
			if (waitForElementsOfSettingsPage("unreadNotificationText")) {

				List<WebElement> ele = getElementsOfSettingsPage("notificationListObjects");
				if (ele.get(0).getText().equalsIgnoreCase(successMessage)
						&& ele.get(1).getText().equalsIgnoreCase(errorMessage)) {
					flag = true;
					failureObject = ele.get(1);
				} else if (ele.get(1).getText().equalsIgnoreCase(successMessage)
						&& ele.get(0).getText().equalsIgnoreCase(errorMessage))
					failureObject = ele.get(0);
			}
			System.out.println();
			mouseHover(failureObject);
			mouseHover(settingsPageProperties.getProperty("notificationEllipseButton"));
			try{
				clickOnElementsOfSettingsPage("notificationEllipseButton");
			}catch(ElementClickInterceptedException ec){
				clickByJavaScriptOnSettingsPage("notificationEllipseButton");
			}
			flag = waitForElementsOfSettingsPage(duplicateerrorkey);
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckUnsuccessfulImportInV3 " + e.getMessage()));
			return false;
		}
	}
	
	
	/**
	 * @param fieldname
	 * @return
	 * @throws Exception
	 */
	public String[] getCSVFieldValue(String fieldname, String filenamekey) throws Exception {
		try {
			
			File f = new File(System.getProperty("user.dir") + "/import/"
					+ getEnvironmentSpecificData(System.getProperty("environment"), filenamekey));

			String[][] arr = CSVFileReader.getInstance().getDataWithHeader(f);
			String[] cellValueFromAllRows = new String[arr.length-1];
			
			int fieldColumnIndex = 0;
			for (int i=1; i<arr.length; i++) {
				for (int j = 0; j < arr[0].length; j++) {
					if (arr[0][j].equalsIgnoreCase(fieldname)) {
						fieldColumnIndex = j;
						break;
					}
				}

				if (fieldname.equals("FCPKServiceStartDate") || fieldname.equals("FCPKServiceEndDate")) {
					cellValueFromAllRows[i-1] = convertDate("dd-MMM-yy", "MM/dd/yyyy", arr[i][fieldColumnIndex]);
				} else {
					cellValueFromAllRows[i-1] = arr[i][fieldColumnIndex];
				}
			}
			return cellValueFromAllRows;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getCSVFieldValue " + e.getMessage()));
			return null;
		}

	}
	
	/**
	 * This is a method to click on all elements in list of web elements
	 * 
	 * @param uiList - List of elements of column on UI and click on 1st element
	 * @throws Exception
	 */

	public final void clickElementsOfSettingsPage(List<WebElement> uiList) throws Exception {

		Iterator<WebElement> uiColumnListIterator = uiList.iterator();
		while (uiColumnListIterator.hasNext()) {
			WebElement element = uiColumnListIterator.next();
			element.click();
		}

	}
	
	/**
	 * This is a method to add maximum number of custom fields for a company
	 * 
	 * @param editKey - Locator of edit button for custom field
	 * @param addMoreFieldsKey - Locator of add more fields link
	 * @param saveKey - Locator of save added custom fields
	 * @param firstTextBoxKey - Locator of first custom field input box
	 * @param secondTextBoxKey - Locator of second custom field input box
	 * @param thirdTextBoxKey - Locator of third custom field input box
	 * @param fourthTextBoxKey - Locator of fourth custom field input box
	 * @param fifthTextBoxKey - Locator of fifth custom field input box
	 */
	public final void addAllCustomFieldsSettingsPage(String editKey, String addMoreFieldsKey, String saveKey, String firstTextBoxKey, String secondTextBoxKey, String thirdTextBoxKey, String fourthTextBoxKey, String fifthTextBoxKey, String customFieldOneKey, String customFieldTwoKey, String customFieldThreeKey, String customFieldFourKey, String customFieldFiveKey) {
		try {
			clickOnElementsOfSettingsPage(editKey);
			enterTextForSettingsPage(firstTextBoxKey, customFieldOneKey);
			clickOnElementsOfSettingsPage(addMoreFieldsKey);
			enterTextForSettingsPage(secondTextBoxKey, customFieldTwoKey);
			clickOnElementsOfSettingsPage(addMoreFieldsKey);
			enterTextForSettingsPage(thirdTextBoxKey, customFieldThreeKey);
			clickOnElementsOfSettingsPage(addMoreFieldsKey);
			enterTextForSettingsPage(fourthTextBoxKey, customFieldFourKey);
			clickOnElementsOfSettingsPage(addMoreFieldsKey);
			enterTextForSettingsPage(fifthTextBoxKey, customFieldFiveKey);
			clickOnElementsOfSettingsPage(saveKey);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method addAllCustomFields " + e.getMessage()));
		}
	}

	/**
	 * @param filenamekey
	 * @return
	 */
	public Map<String, String> getExpectedStartDateExpiryDate(String filenamekey) {
		//String expectedStartDate = null;
		//String expectedExpiryDate = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			String[] startDateCSV = getCSVFieldValue("FCPKServiceStartDate", filenamekey);
			String[] expiryDateCSV = getCSVFieldValue("FCPKServiceEndDate", filenamekey);

			String currentDate = generateDate();
			for(int i=0; i<startDateCSV.length; i++) {
				startDateCSV[i] = convertDate("MM/dd/yyyy", "yyyy-MM-dd", startDateCSV[i]);
				expiryDateCSV[i] = convertDate("MM/dd/yyyy", "yyyy-MM-dd", expiryDateCSV[i]);
				currentDate = convertDate("yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd", currentDate);

				LocalDate startCSVDate = LocalDate.parse(startDateCSV[i]);
				LocalDate expiryCSVDate = LocalDate.parse(expiryDateCSV[i]);
				LocalDate currentCSVDate = LocalDate.parse(currentDate);
			// calculate difference
			long term = Duration.between(startCSVDate.atStartOfDay(), expiryCSVDate.atStartOfDay()).toDays();

			SimpleDateFormat formatTime = new SimpleDateFormat("MM/dd/yyyy");
			formatTime.setTimeZone(TimeZone.getTimeZone("GMT"));

			if (startCSVDate.compareTo(currentCSVDate) > 0) {
				Calendar cal = Calendar.getInstance();
				map.put("expectedStartDate", formatTime.format(cal.getTime()));
				cal.add(Calendar.DAY_OF_MONTH, (int) term);
				map.put("expectedExpiryDate", formatTime.format(cal.getTime()));

			}
			}
			return map;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getExpectedStartDateExpiryDate " + e.getMessage()));
			return null;
		}

	}
	
	
	/**
	 * compares the default values of the subtypes enabled or disabled
	 * 
	 * @param textenabled
	 * @param textdisabled
	 * @param enabledlist
	 * @return boolean
	 * @throws Exception
	 */
	public boolean compareListOfSettingsPage(String textenabled, String textdisabled, List<String> enabledlist)
			throws Exception {
		Map<String, String> selfServiceAlertsStatus = new HashMap<>();
		List<String> actualenabledlist = new ArrayList<>();
		List<WebElement> menuList = getElementsTillAllElementsPresent(
				settingsPageProperties.getProperty("selfservicealertsubtype"));
		boolean flag = false;
		boolean status = true;

		for (int i = 0; i < menuList.size(); i++) {
			selfServiceAlertsStatus.put(menuList.get(i).getAttribute("innerHTML"), menuList.get(i)
					.findElement(getObject(settingsPageProperties.getProperty("selfservicealertsubtypespecificstatus")))
					.getAttribute("innerHTML"));

			if (enabledlist.contains(menuList.get(i).getAttribute("innerHTML"))) {
				actualenabledlist.add(menuList.get(i).getAttribute("innerHTML"));
				if (menuList.get(i)
						.findElement(
								getObject(settingsPageProperties.getProperty("selfservicealertsubtypespecificstatus")))
						.getAttribute("innerHTML").equalsIgnoreCase(textenabled))
					flag = true;
				else
					status = false;
			} else if (menuList.get(i)
					.findElement(getObject(settingsPageProperties.getProperty("selfservicealertsubtypespecificstatus")))
					.getAttribute("innerHTML").equalsIgnoreCase(textdisabled)) {
				flag = true;
			} else {
				flag = false;
				status = false;
			}

		}
		
		return status;

	}

	
	
	
		/**
	 * Revert Self Service Alerts to default status
	 * @throws Exception
	 */
	public void revertSelfServiceAlertsStatusToDefault() throws Exception {
		waitForElementsOfSettingsPage("resetToDefaultAlerts");
		clickOnElementsOfSettingsPage("resetToDefaultAlerts");
		sleeper(2000);
		clickOnElementsOfSettingsPage("resetToDefaultSave");
		sleeper(2000);
		waitForPageLoaded();
		waitForElementsOfSettingsPageForinvisibile("selfservicealertdialogwindow");

	}
	
	
	/**
	 * Enable Status of Activecare--Other selfservice alert
	 * 
	 * @throws Exception
	 */
	public void changeStatusOfActiveCareOtherSubtype() throws Exception {
		clickOnElementsOfSettingsPage("activecareexpandicon");
		clickOnElementsOfSettingsPage("activecareotheredittogglebutton");
		waitForElementsOfSettingsPage("enabletogglebutton");
		clickByJavaScriptOnSettingsPage("enabletogglebutton");
		clickByJavaScriptOnSettingsPage("togglesavebutton");
		waitForPageLoaded();
		sleeper(3000);
		waitForElementsOfSettingsPageForinvisibile("selfservicealertdialogwindow");
	}
	
	/**
	 * update csv fields with random values
	 * 
	 * @throws Exception
	 */
	public void updateActiveCareCSVFieldValue(String csvFile, String[] columnNamesToUpdate) throws Exception {
		try {
			File f = new File(System.getProperty("user.dir") + "/import/"
					+ getEnvironmentSpecificData(System.getProperty("environment"), csvFile));

			String[][] arr = CSVFileReader.getInstance().getDataWithHeader(f);
			for (int i = 0; i < columnNamesToUpdate.length; i++) {
				int fieldColumnIndex = 0;
				for (int j = 0; j < arr[0].length; j++) {
					if (arr[0][j].equalsIgnoreCase(columnNamesToUpdate[i])) {
						fieldColumnIndex = j;
						break;
					}
				}
				for (int k = 1; k < arr.length; k++) {
					if (columnNamesToUpdate[i].equals("EndCustomerPrimaryEmail")) {

						CSVFileReader.getInstance().updateCSV(f,
								RandomStringUtils.randomAlphanumeric(8) + "@mailinator.com", k, fieldColumnIndex);
					}

					else if (columnNamesToUpdate[i].equals("ObjectOfServiceSerialNumber")) {
						CSVFileReader.getInstance().updateCSV(f, "CPAUTO" + RandomStringUtils.randomAlphanumeric(4).toUpperCase(), k,
								fieldColumnIndex);
					}

					else if (columnNamesToUpdate[i].equals("EndCustomerName")) {
						CSVFileReader.getInstance().updateCSV(f, "NEWCOMPANY" + RandomStringUtils.randomAlphanumeric(4).toUpperCase(),
								k, fieldColumnIndex);
					}

				}
			}

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getCSVFieldValue " + e.getMessage()));

		}

	}
	
	/**
	 * Method is fetching the side panel menu options for active care and
	 * comparing it with the expected values
	 * 
	 * @param expectedMenuOptions
	 * @return : boolean true if matching or else boolean false if not matching
	 * @throws Exception
	 */
	public final boolean verifyLeftSideMenuActiveCareService(ArrayList<String> expectedMenuOptions,
			String activeCareSideMenuOption) throws Exception {
		boolean flag = false;
		ArrayList<String> actualListOfSideMenuOptions = new ArrayList<String>();
		try {
			
			List<WebElement> actualActiveCareSideMenu = getElementsOfSettingsPage(activeCareSideMenuOption);
			
			for (int i = 0; i < actualActiveCareSideMenu.size(); i++) {
				actualListOfSideMenuOptions.add(actualActiveCareSideMenu.get(i).getText());
			}
			flag = expectedMenuOptions.equals(actualListOfSideMenuOptions);
			return flag;
		}

		catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyLeftSideMenuActiveCareService " + e.getMessage()));
			return false;
		}

	}
	
	/* Method is checking status of the items listed under Notification tab -- hardware health 
	 * and Active care if enabled or disabled
	 * 
	 * @param enabledText --> Enabled status text
	 * @param disabledText --> Disabled status text
	 * @param  list --> list of all elements under hardware health and Active care
	 * @param  locator2 - to fetch elements as per heading */
	
	public boolean checkStatusOfAlerts(String enabledText, String disabledText , List<String> list, String locator2, boolean checkEnabled)
	        throws Exception {
	    List<WebElement> menuList = getElementsTillAllElementsPresent(
	            settingsPageProperties.getProperty(locator2));
	    boolean flag = true; // Initialize to true

	    if (list.size() == menuList.size()) {
	        for (int i = 0; i < menuList.size(); i++) {
	            String actualText = menuList.get(i)
	                    .findElement(getObject(settingsPageProperties.getProperty("selfservicealertsubtypespecificstatus")))
	                    .getAttribute("innerHTML");

	            if (checkEnabled) {
	                // Check for enabled status
	                if (!actualText.equals(enabledText)) {
	                    flag = false; // If any element does not meet the condition, set flag to false
	                    break; // Exit the loop early since the condition is not met
	                }
	            } else {
	                // Check for disabled status
	                if (actualText.equals(disabledText)) {
	                    flag = false; // If any element does not meet the condition, set flag to false
	                    break; // Exit the loop early since the condition is not met
	                }
	            }
	        }
	    } else {
	        flag = false; // If sizes are different, set flag to false
	    }

	    return flag;
	}
	
	
	/* Method is checking list of the items listed under Notification tab -- hardware health 
	 * and Active care if enabled or disabled
	 * 
	 * @param  list --> list of all elements under hardware health and Active care
	 */
	public boolean verifyListOfSubCategoriesUnderSelfServiceAlerts(List<String> list, String locator)
	        throws Exception {
	    List<String> menuList = getallTextBy(
	            settingsPageProperties.getProperty(locator));
	    boolean flag = true; // Initialize to true

	    if (list.size() == menuList.size()) {
	        for (int i = 0; i < menuList.size(); i++) {
	        	if(!menuList.contains(list.get(i).toUpperCase())){
	        		LOGGER.info(menuList.get(i) + " is not present in Self Service Alert list under Hardware Health sub-categories.");
	        		flag = false;
	        		break;
	        	}
	        	LOGGER.info(menuList.get(i) + " is present in Self Service Alert list under Hardware Health sub-categories.");
	        }
	    } else {
	        flag = false; // If sizes are different, set flag to false
	    }
	    return flag;
	}
	
	
	/* Method opens self service alert HW category modal to get default message & url.
	 * @param  String --> Title of HW Sub-category to open.
	 */
	public boolean editSelfServiceAlertHWToggle(String hw_subcategory)
	        throws Exception {
	    List<String> hw_subcaterogytitle = getallTextBy(
	            settingsPageProperties.getProperty("hardwarehealthcategoriestitle"));
	    Map<String, Integer>hw_subcategorylist = new HashMap<String, Integer>();
	    if (hw_subcaterogytitle.size()!=0) {
	        for (int i = 1; i <= hw_subcaterogytitle.size(); i++) {
	        	hw_subcategorylist.put(hw_subcaterogytitle.get(i-1), i);
	        	}
	        	LOGGER.info(" Fetch list of all HW Subcategory titles.");
	        }
	     else {
	        return false;
	    }
	    if(hw_subcategorylist.containsKey(hw_subcategory.toUpperCase())) {
	    	this.click(String.format(settingsPageProperties.getProperty("eachhardwarehealthtoggleeditbutton"), String.valueOf(hw_subcategorylist.get(hw_subcategory.toUpperCase()))));
	    	LOGGER.info("Open Self Service Alert HW Cateogry modal to get default message & URL.");
	    }
	    return this.waitForElementsOfSettingsPage("selfservicealertmodalmessagearea");
	}
	
	
	/* Method verifies default msg & url provided to self service alert HW categories.
	 * 
	 * @param  list --> list of all elements under hardware health and Active care
	 */
	public boolean verifySelfServiceAlertHWDefaultMessageURL(String default_msg, String default_url)
	        throws Exception {
	    if(this.waitForElementsOfSettingsPage("selfservicealertmodalmessagearea")) {
	    	if(default_msg != null && default_msg.equals(this.getTextOfSettingsPage("selfservicealertmodalmessagearea"))) {
	    		LOGGER.info("Default message for "+ this.getTextOfSettingsPage("selfservicealertmodaltitle")+" matches with UI.");
	    	}else {
	    		LOGGER.info("Default message for "+ this.getTextOfSettingsPage("selfservicealertmodaltitle")+" does not matches with UI.");
	    		return false;
	    	}
	    	if(default_url ==null) {
	    		LOGGER.info("There is no default URL for "+ this.getTextOfSettingsPage("selfservicealertmodaltitle"));
	    	}
	    	else if(default_url.equals(this.getAttributesOfSettingsPage("selfservicealertmodalurl", "value"))) {
	    		LOGGER.info("Default URL for "+ this.getTextOfSettingsPage("selfservicealertmodaltitle")+" matches with UI.");
	    	}else {
	    		LOGGER.info("Default URL for "+ this.getTextOfSettingsPage("selfservicealertmodaltitle")+" does not matches with UI.");
	    		return false;
	    	}
	    	this.clickOnElementsOfSettingsPage("selfservucealertmodalcancelbutton");
	    	return this.waitForElementsOfSettingsPageForinvisibile("selfservicealertmodalmessagearea");
	    }else {
	    	LOGGER.info("Self Service Alert modal is not visible.");
	    	return false;
	    }
	   }

	
}
	







	


