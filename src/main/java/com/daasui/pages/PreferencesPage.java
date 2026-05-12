package com.daasui.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.PreferenceVariables;

public class PreferencesPage extends CommonMethod {
	
	private Properties selectedLanguageProperties;
	private ObjectReader preferencesPagePropertiesReader = new ObjectReader();
	private Properties preferencesPagePropertiesPage;
	private static Logger LOGGER = LogManager.getLogger(PreferencesPage.class);
	private PreferencesPage instance;
	public static HashMap<String, String> intuneCredentials = fetchIntuneCredentilas();
	public static HashMap<String, String> airwatchCredentials = fetchAirwatchCredentials();
	public static String intuneEnrolledDevices = getEnvironmentSpecificData(System.getProperty("environment"), "INTUNE_DEVICE_NAME");
	public static String emmCompanyId = getEnvironmentSpecificData(System.getProperty("environment"), "EMM_COMPANY_TENANT_ID");
	String authcode =null;
	public static String uiVersion = System.getProperty("uiVersion");
	public PreferencesPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (PreferencesPage.class) {
				if (instance == null) {
					instance = new PreferencesPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public PreferencesPage(WebDriver driver) throws IOException {
		preferencesPagePropertiesPage = preferencesPagePropertiesReader.getObjectRepository("PreferencesPageV3");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = preferencesPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	public final boolean verifyElementsOfPreferencesPage(String key) throws Exception {
		return verifyElementIsPresent(preferencesPagePropertiesPage.getProperty(key));
	}

	public final boolean waitForElementsOfPreferencesPage(String key) throws Exception {
		return verifyElementIsVisible(preferencesPagePropertiesPage.getProperty(key));
	}

	public final boolean matchTextOfPreferencesPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(preferencesPagePropertiesPage.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfPreferencesPage(String key) throws Exception {
		return verifyElementIsEnable(preferencesPagePropertiesPage.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfPreferencesPage(String key) throws Exception {
		return verifyElementIsSelected(preferencesPagePropertiesPage.getProperty(key));
	}

	public final String getTextOfPreferencesPage(String key) throws Exception {
		return getTextBy(preferencesPagePropertiesPage.getProperty(key));
	}

	public final String getAttributesOfPreferencesPage(String key, String value) throws Exception {
		return getAttribute(preferencesPagePropertiesPage.getProperty(key), value);
	}

	public final void clickOnElementsOfPreferencesPage(String key) throws Exception {
		click(preferencesPagePropertiesPage.getProperty(key));
	}
	
	public final void enterTextForPreferencesPage(String key, String Text) throws Exception {
		enterText(preferencesPagePropertiesPage.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfPreferencesPage(String key) throws Exception {
		return verifyElementIsClickable(preferencesPagePropertiesPage.getProperty(key));
	}

	public final List<WebElement> getElementsOfPreferencesPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(preferencesPagePropertiesPage.getProperty(key));
	}

	public final void selectElementFromListbox(String key, String text) throws Exception {
		click(preferencesPagePropertiesPage.getProperty(key));
		SelectTextPresentInDropdown(preferencesPagePropertiesPage.getProperty(key), text);
	}

	public final void goToPreferencesTab() throws Exception {
		clickOnElementsOfPreferencesPage("preferencesTab");
	}

	public final void moveToElementOnPreferencesPage(String key) throws Exception {
		moveToElements(preferencesPagePropertiesPage.getProperty(key));
	}

	public final boolean verifyElementIsNotPresent(String key) throws Exception {
		return verifyElementIsinvisibile(preferencesPagePropertiesPage.getProperty(key));
	}
	public final WebElement getElementOfPreferencePage(String key) throws Exception{
		return getElement(preferencesPagePropertiesPage.getProperty(key));
	}

	public final void clickByJavaScriptOnPreferencesPage(String key) throws Exception {
		clickByJavaScript(preferencesPagePropertiesPage.getProperty(key));
	}
	public final void closeNewWindow(String newWindow) throws Exception {
		Set<String> allWindows = getDriver().getWindowHandles();
		if (allWindows.contains(newWindow)) {
			getDriver().switchTo().window(newWindow).close();
			getDriver().switchTo().defaultContent();
		}
	}

	public final boolean verifyElementIsPresentOnPreferencesPage(String key) {
		try {
			return verifyElementIsVisible(preferencesPagePropertiesPage.getProperty(key));
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyElementIsPresentOnPreferencesPage" + ex.getMessage());
			return false;
		}		
	}

	public final void scrollOnPreferencesPage(String key) {
		try {
			scrollTillView(preferencesPagePropertiesPage.getProperty(key));
		} catch (Exception ex) {
			LOGGER.error("Exception occured in scrollOnPreferencesPage" + ex.getMessage());
		}	
	}

	/**
	 * This method mouse hovers on device details elements
	 * @param key
	 */
	public final void mousehoverOnPreferencesPage(String key) {
		try {
			mouseHover(preferencesPagePropertiesPage.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnPreferencesPage " + e.getMessage()));
		}
	}

	/**
	 * This method set attribute to block to be used in import
	 * @param key -Key of the locator
	 */
	public final void setAttributeOfPreferencesPage(String key) {
		setAttributeForImport(preferencesPagePropertiesPage.getProperty(key));
	}

	/**
	 * This method configures Intune EMM on preferences tab
	 * 
	 * @return
	 */
	public final boolean verifyIntuneConfiguration(String domainName, String username, String password) {
		boolean flag = false;
		try {
			enterTextForPreferencesPage("domainNameIntuneText", domainName);
			if (verifyElementIsEnableOfPreferencesPage("intuneSaveButton")) {
				clickOnElementsOfPreferencesPage("intuneSaveButton");
				if (loginToIntunePortal(username, password)) {
					waitForElementsOfPreferencesPage("successNotification");
					if (verifyElementsOfPreferencesPage("successNotification")) {
						sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
						refreshPage();
						waitForPageLoaded();
						waitForElementsOfPreferencesPage("domainText");
						goToPreferencesTab();
						waitForElementsOfPreferencesPage("domainText");
						verifyElementsOfPreferencesPage("domainText");
						scrollOnPreferencesPage("domainText");
						if ((getTextOfPreferencesPage("domainText").equals(domainName))) {
							flag = true;
						} else {
							LOGGER.error("Error : Intune credentils are not matched for EMM on Preferences tab");
							return flag;
						}
					} else {
						LOGGER.error("Error : Success Notification is not displayed on configuring an EMM");
						return flag;
					}
				} else {
					return flag;
				}
			} else {
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyIntuneConfiguration" + e.getMessage());
		}
		return flag;
	}

	/**
	 * This method verify match the EMM related strings
	 * 
	 * @param hashMap HashMap of the expected EMM strings
	 * @return boolean value true if validated emm strings are displayed ,false if emm strings fails to validate with actual one
	 * @throws Exception
	 */
	public final boolean verifyEmmStrings(HashMap<String, String> hashMap, String languageCode) throws Exception {
		boolean check = true;
		Set<Entry<String, String>> set = hashMap.entrySet();
		Iterator<Entry<String, String>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> mentry = (Map.Entry<String, String>) iterator.next();
			if (getTextOfPreferencesPage(mentry.getKey().toString()).equalsIgnoreCase(getTextLanguage(languageCode, "lhserver", mentry.getValue().toString()))) {
				LOGGER.info(getTextLanguage(languageCode, "lhserver", mentry.getValue().toString()) + " is Displyed");
			} else {
				LOGGER.error("Error : " + mentry.getValue() + " is not Displyed");
				check = false;
			}
		}
		return check;
	}

	/**
	 * This method logins to Intune portal
	 * 
	 * @param id
	 * @param password
	 * @return boolean value true If Intune is configured , false If Portal login fails
	 */
	public final boolean loginToIntunePortal(String id, String password) {
		boolean flag = false;
		try {
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			if (deviceDetailsPage.waitForElementsOfDeviceDetailsPage("intuneSignInPanel")) {
				if (getUrlOfCurrentPage().contains(ConstantURL.INTUNE_URL) || getUrlOfCurrentPage().contains(ConstantURL.AZURE_PORTAL_URL)) {
					LOGGER.info("Redirected to the Intune url : " + getDriver().getCurrentUrl());
					enterTextForPreferencesPage("intuneId", id);
					clickOnElementsOfPreferencesPage("intuneNextButton");
					enterTextForPreferencesPage("intunePassword", password);
					clickOnElementsOfPreferencesPage("intuneSignButton");
					clickOnElementsOfPreferencesPage("intuneYesButton");
					sleeper(3000);// Takes time to redirect back on daas portal from microsoft consent page
					return flag = true;
				} else {
					LOGGER.error("Intune Portal login failed");
					return flag;
				}
			} else if (deviceDetailsPage.waitForElementsOfDeviceDetailsPage("microsoftDashborad")) {
				LOGGER.info("Already logged in to Intune Portal redirected to the Intune url : " + getUrlOfCurrentPage());
				waitForPageLoaded();
				deviceDetailsPage.verifyElementsOfDeviceDetailsPage("microsoftDashborad");
				flag = true;
			} else {
				LOGGER.error("Error : Intune redirection failed redirected to : " + getDriver().getCurrentUrl());
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in loginToIntunePortal" + e.getMessage());
		}
		return flag;
	}

	/**
	 * 
	 * @param languageCode
	 * @return boolean value true If Intune strings are validated,false If Intune strings fails to validate with actual one
	 * @throws Exception
	 */

	public final boolean verifyEmmStringOnPreferencesPage(String languageCode) throws Exception {
		HashMap<String, String> emmNotconfiguredMap = new HashMap<String, String>();
		emmNotconfiguredMap.put("emmLabel", "settings.preferences.emm.intune");
		if (verifyEmmStrings(emmNotconfiguredMap, languageCode)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method check EMM related strings for Intune toggle
	 * 
	 * @param languageCode
	 * @return boolean value true If Intune Strings are validated ,false if Intune strings fails to validate with actual one
	 * @throws Exception
	 */
	public final boolean verifyEmmStringOnIntunePage(String languageCode) throws Exception {
		HashMap<String, String> intuneConfigurationStrings = new HashMap<String, String>();
		intuneConfigurationStrings.put("domainNameLabel", "settings.preferences.emm.domain_name");
		if (verifyEmmStrings(intuneConfigurationStrings, languageCode)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method fetch all iOS devices from Intune portal
	 * 
	 * @return ArrayList of iOS devices
	 * @throws Exception
	 */
	public final ArrayList<String> fetchIntuneiosDevices() throws Exception {
		ArrayList<String> intuneDevices = new ArrayList<String>();
		createAndSwitchToNewTab();
		if (setIntuneDeviceFilter("iOS")) {
			List<WebElement> list = getElementsOfPreferencesPage("intuneDeviceTable");
			for (WebElement webElement : list) {
				webElement.click();
				String serialName = getTextOfPreferencesPage("deviceSerialNumber");
				intuneDevices.add(serialName);
			}
		} else {
			return intuneDevices;
		}
		if (signOutFromIntunePortal()) {
			LOGGER.info("Successfully signed out from Intune portal");
		} else {
			LOGGER.info("Error : signed out from Intune portal failed");
		}
		return intuneDevices;
	}

	/**
	 * This method fetch all iOS device from DaaS portal
	 * 
	 * @return ArrayList of iOS devices
	 * @throws Exception
	 */
	public final ArrayList<String> fetchDaaSiosDevices(String companyName) throws Exception {
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver());
		deviceListPage = deviceListPage.getInstance();
		
		if (companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters")) {
			sleeper(4000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			companiesPage.clickOnElementsOfCompaniesPage("removeAllSearchFilters");
			companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
			LOGGER.info("All the filters of company list page has been removed successfully.");
			sleeper(5000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
		}		
		deviceListPage.clickOnElementsOfDeviceListPage("companyBox");
		deviceListPage.enterTextForDeviceListPage("companyBoxSearch", companyName);
		sleeper(2000);
		deviceListPage.verifyElementIsClickable("clickOnCompanyName");
		deviceListPage.clickOnElementsOfDeviceListPage("clickOnCompanyName");
		sleeper(1000);
		deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("SpinnerList");
		
		// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
//		deviceListPage.clickOnElementsOfDeviceListPage("companyResult");
		sleeper(6000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
		
		//getlist
		ArrayList<String> response = deviceListPage.getTextOfListOfDeviceListPage("deviceList");
		ArrayList<String> daasDevices = new ArrayList<String>();
		for (int deviceCount = 0; deviceCount < response.size(); deviceCount++) {
				daasDevices.add(response.get(deviceCount).toUpperCase());	
		}
		return daasDevices;
	}

	/**
	 * This method verify pre-existing iOS devices form Intune portal are enrolled on DaaS portal
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean verifyExistingIntuneIosDeviceEnrollemnt(String companyName) throws Exception {
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		List<String> intuneDevices = new ArrayList<String>();
		intuneDevices = Arrays.asList(intuneEnrolledDevices);
		deviceListPage.waitForElementsOfDeviceListPage("devicesTab");
		deviceListPage.clickOnElementsOfDeviceListPage("devicesTab");
		waitForPageLoaded();
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		ArrayList<String> daasDevices = fetchDaaSiosDevices(companyName);
		LOGGER.info("Devices present on Intune portal : " + intuneDevices);
		LOGGER.info("Devices present on DaaS portal : " + daasDevices);
		if (intuneDevices.size() > 0) {
			if (daasDevices.size() > 0) {
				for (int counter = 0; counter < intuneDevices.size(); counter++) {
					if (daasDevices.contains(intuneDevices.get(counter))) {
						LOGGER.info(intuneDevices.get(counter) + " present on DaaS portal");
					} else {
						LOGGER.error("Error : " + intuneDevices.get(counter) + " is not present on DaaS portal");
						return false;
					}
				}
			} else {
				LOGGER.error("Error : Existing intune iOS devices are not enrolled on DaaS portal");
				return false;
			}
		} else {
			LOGGER.error("Error : There is no iOS device enrolled on Intune portal. To execute this test there should be atleast one iOS device enrolled on Intune portal");
			return false;
		}
		return true;
	}

	/**
	 * This method sign out from Intune portal
	 * 
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean signOutFromIntunePortal() throws Exception {
		clickOnElementsOfPreferencesPage("accountMenu");
		clickOnElementsOfPreferencesPage("intuneSignOutButton");
		sleeper(1000);
		if (verifyElementsOfPreferencesPage("intuneLogOut")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method returns HashMap of Intune credentials
	 * 
	 * @return
	 */
	public final static HashMap<String, String> fetchIntuneCredentilas() {
		HashMap<String, String> hashMap = new HashMap<>();
		String env = System.getProperty("environment").toUpperCase();
		
		// Support flexible matching for environment names
		// Handles variations like: US-STABLE, US-Stable-WEX, US-STAGE-WEP, US-Perf-WEP, etc.
		if (env.contains("US") && env.contains("STABLE")) {
			hashMap.put("DOMAIN_NAME", PreferenceVariables.STABLE_US_DOMAIN_NAME);
			hashMap.put("INTUNE_ID", PreferenceVariables.STABLE_US_INTUNE_ID);
			hashMap.put("INTUNE_PASSWORD", PreferenceVariables.STABLE_US_INTUNE_PASSWORD);
		} else if (env.contains("EU") && env.contains("STABLE")) {
			hashMap.put("DOMAIN_NAME", PreferenceVariables.STABLE_EU_DOMAIN_NAME);
			hashMap.put("INTUNE_ID", PreferenceVariables.STABLE_EU_INTUNE_ID);
			hashMap.put("INTUNE_PASSWORD", PreferenceVariables.STABLE_EU_INTUNE_PASSWORD);
		} else if (env.contains("US") && (env.contains("STAGING") || env.contains("STAGE") || env.contains("PERF"))) {
			// Matches: US-STAGING, US-STAGE-WEP, US-Perf-WEP, etc.
			hashMap.put("DOMAIN_NAME", PreferenceVariables.STAGING_US_DOMAIN_NAME);
			hashMap.put("INTUNE_ID", PreferenceVariables.STAGING_US_INTUNE_ID);
			hashMap.put("INTUNE_PASSWORD", PreferenceVariables.STAGING_US_INTUNE_PASSWORD);
		} else if (env.contains("EU") && (env.contains("STAGING") || env.contains("STAGE"))) {
			hashMap.put("DOMAIN_NAME", PreferenceVariables.STAGING_EU_DOMAIN_NAME);
			hashMap.put("INTUNE_ID", PreferenceVariables.STAGING_EU_INTUNE_ID);
			hashMap.put("INTUNE_PASSWORD", PreferenceVariables.STAGING_EU_INTUNE_PASSWORD);
		} else if (env.contains("US") && (env.contains("PRODUCTION") || env.contains("PROD"))) {
			hashMap.put("DOMAIN_NAME", PreferenceVariables.PRODUCTION_US_DOMAIN_NAME);
			hashMap.put("INTUNE_ID", PreferenceVariables.PRODUCTION_US_INTUNE_ID);
			hashMap.put("INTUNE_PASSWORD", PreferenceVariables.PRODUCTION_US_INTUNE_PASSWORD);
		} else if (env.contains("EU") && (env.contains("PRODUCTION") || env.contains("PROD"))) {
			hashMap.put("DOMAIN_NAME", PreferenceVariables.PRODUCTION_EU_DOMAIN_NAME);
			hashMap.put("INTUNE_ID", PreferenceVariables.PRODUCTION_EU_INTUNE_ID);
			hashMap.put("INTUNE_PASSWORD", PreferenceVariables.PRODUCTION_EU_INTUNE_PASSWORD);
		} else {
			LOGGER.error("Error : Incorrect home environment name given: '" + env + "'. Please check environment name is a valid entry (e.g., US-STAGING, US-STAGE-WEP, US-Perf-WEP, EU-STAGING, US-PRODUCTION, EU-PROD-WEX)");
		}
		return hashMap;
	}

	/**
	 * This method returns HashMap of Airwatch credentials
	 * 
	 * @return
	 */
	public final static HashMap<String, String> fetchAirwatchCredentials() {
		HashMap<String, String> airwatchCredentialsMap = new HashMap<>();
		try {
			if (System.getProperty("environment").contains("US")) {
				airwatchCredentialsMap.put("URL", PreferenceVariables.AIRWATCH_URL_US);
				airwatchCredentialsMap.put("KEY", PreferenceVariables.AIRWATCH_KEY_US);
				airwatchCredentialsMap.put("GROUP_ID", PreferenceVariables.AIRWATCH_GROUP_ID_US);
				airwatchCredentialsMap.put("USERNAME", PreferenceVariables.AIRWATCH_USERNAME_US);
				airwatchCredentialsMap.put("PASSWORD", PreferenceVariables.AIRWATCH_PASSWORD_US);
				airwatchCredentialsMap.put("CERTIFICATE_PASSWORD", PreferenceVariables.AIRWATCH_CERTIFICATE_PASSWORD_US);
				airwatchCredentialsMap.put("CERTIFICATE_FILE", PreferenceVariables.AIRWATCH_CERTIFICATE_FILE_US);
			} else if (System.getProperty("environment").contains("EU")) {
				airwatchCredentialsMap.put("URL", PreferenceVariables.AIRWATCH_URL_US);
				airwatchCredentialsMap.put("KEY", PreferenceVariables.AIRWATCH_KEY_US);
				airwatchCredentialsMap.put("GROUP_ID", PreferenceVariables.AIRWATCH_GROUP_ID_US);
				airwatchCredentialsMap.put("USERNAME", PreferenceVariables.AIRWATCH_USERNAME_US);
				airwatchCredentialsMap.put("PASSWORD", PreferenceVariables.AIRWATCH_PASSWORD_US);
				airwatchCredentialsMap.put("CERTIFICATE_PASSWORD", PreferenceVariables.AIRWATCH_CERTIFICATE_PASSWORD_US);
				airwatchCredentialsMap.put("CERTIFICATE_FILE", PreferenceVariables.AIRWATCH_CERTIFICATE_FILE_US);
			} else {
				LOGGER.error("Error :Incorrect region given in home environment...Please check environment region given is a valid entry");
			}

		} catch (Exception e) {
			LOGGER.error("Exception occurred in Test fetchAirwatchCredentials : " + e.getStackTrace());
			return null;
		}
		return airwatchCredentialsMap;
	}

	/**
	 * This method will set Intune Device Operating System filter on intune portal
	 * @param intuneOSType - Intune Device Os type filter(Expected values - iOS,Andriod,macOS,Windows)
	 * @return true - Boolean value return either true or false
	 */
	public final boolean setIntuneDeviceFilter(String intuneOSType) {
		boolean flag = false;
		try {
			List<String> intuneDataValuesList = new ArrayList<>();
			getUrl(ConstantURL.AZURE_PORTAL_DEVICE_LIST_URL);
			waitForElementsOfPreferencesPage("intuneFilter");
			waitForPageLoaded();
			clickOnElementsOfPreferencesPage("intuneFilter");
			waitForPageLoaded();
			scrollOnPreferencesPage("osFilter");
			sleeper(5000);// Intune(Third Party Portal) takes time to load filter
			clickOnElementsOfPreferencesPage("osFilter");
			waitForPageLoaded();
			clickOnElementsOfPreferencesPage("selectAllFilter");
			clickOnElementsOfPreferencesPage("selectAllFilter");
			List<WebElement> intuneDeviceDetails = getElementsOfPreferencesPage("intuneSelectOSFilter");
			for (WebElement intuneDeviceValues : intuneDeviceDetails) {
				intuneDataValuesList.add(intuneDeviceValues.getText());
				if (intuneDeviceValues.getText().contains(intuneOSType)) {
					intuneDeviceValues.click();
					break;
				}
			}
			clickOnElementsOfPreferencesPage("intuneApplyButton");
			flag = true;
			if (verifyElementsOfPreferencesPage("intuneNoDeviceFound")) {
				LOGGER.error("Error : No devices found on Intune portal");
				switchBackToPreviousTab();
				return flag;
			} else {
				LOGGER.info(intuneOSType + " device is present");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred in Test setIntuneDeviceFilter : " + e.getStackTrace());
		}
		return flag;
	}

	/**
	 * This method verify Emm(Airwatch or Intune) toggles on preferences page
	 * 
	 * @param emmType - Airwatch or Intune 
	 * @return boolean value true if Airwatch/Intune toggle is present ,false if Intune/Airwatch strings fails to validate with actual one
	 */
	public final boolean verifyEmmToggle(String emmType) {
		try {
			clickByJavaScriptOnPreferencesPage("emmEditbutton");
			if (emmType.equalsIgnoreCase("Airwatch")) {
				if (verifyElementsOfPreferencesPage("airwatchRadioButton")) {
					LOGGER.info("Airwatch Toggle is present");
					clickOnElementsOfPreferencesPage("airwatchRadioButton");
					return true;
				} else {
					LOGGER.info("Airwatch Toggle is not present");
					return false;
				}
			} else if (emmType.equalsIgnoreCase("Intune")) {
				if (verifyElementsOfPreferencesPage("intuneRadioButton")) {
					LOGGER.info("Intune Toggle is present");
					clickOnElementsOfPreferencesPage("intuneRadioButton");
					return true;
				} else {
					LOGGER.info("Intune Toggle is not present");
					return false;
				}
			} else {
				LOGGER.info("Default Emm type can be : 1.Airwatch or 2.Intune");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception thrown in verifyEmmToggle method :" + e.toString());
			return false;
		}
	}

	/**
	 * This method check EMM related strings for Airwatch basic authentication toggle
	 * 
	 * @param languageCode
	 * @return boolean value true if username and password are validated,false if username and password fails to validate with actual one
	 * @throws Exception
	 */
	public final boolean verifyAirwatchUsernameAndPassword(String languageCode) throws Exception {
		HashMap<String, String> basicAuthStrings = new HashMap<String, String>();
		clickOnElementsOfPreferencesPage("airwatchBasicAuthRadiobutton");
		LOGGER.info("Switching with Basic Authentication toggle");
		waitForElementsOfPreferencesPage("airwatchUsernameLabel");
		verifyElementIsVisible("airwatchUsernameLabel");
		waitForElementsOfPreferencesPage("airwatchPasswordLabel");
		verifyElementIsVisible("airwatchPasswordLabel");
		basicAuthStrings.put("airwatchUsernameLabel", "settings.preferences.emm.username");
		basicAuthStrings.put("airwatchPasswordLabel", "settings.preferences.emm.password");
		if (verifyEmmStrings(basicAuthStrings, languageCode)) {
			LOGGER.info("Verifed Username & Password strings on Airwatch Page");
			return true;
		} else {
			LOGGER.info("Error: Unable to Verify Username & Password Emm Strings on Airwatch Page");
			return false;
		}
	}

	/**
	 * This method check EMM related strings for Airwatch certificate authentication toggle
	 * 
	 * @param languageCode
	 * @return boolean value true if certificate and password are validated,false if certificate and password fails to validate with actual one
	 * @throws Exception
	 */
	public final boolean verifyAirwatchCertificateAndPassword(String languageCode) throws Exception {
		HashMap<String, String> certificateAuthStrings = new HashMap<String, String>();
		clickOnElementsOfPreferencesPage("airwatchCertificateAuthRadiobutton");
		LOGGER.info("Switching with Certificate Authentication toggle");
		certificateAuthStrings.put("airwatchCertificateLabel", "settings.certificates.title.one");
		certificateAuthStrings.put("airwatchCertificatePasswordLabel", "settings.preferences.emm.password");
		if (verifyEmmStrings(certificateAuthStrings, languageCode)) {
			LOGGER.info("Verifed Certificate & Password strings on Airwatch Page");
			return true;
		} else {
			LOGGER.error("Error: Unable to Verify Emm Strings on Airwatch Page");
			return false;
		}
	}

	/**
	 * This method check EMM related strings for Airwatch toggle
	 * 
	 * @param languageCode
	 * @return boolean value true if emmurl,key,groupid are validated,false if emm_url,key,group_id fails to validate with actual one
	 */
	public final boolean verifyEmmStringOnAirwatchPage(String languageCode) {
		try {
			HashMap<String, String> airwatchStrings = new HashMap<String, String>();
			waitForElementsOfPreferencesPage("airwatchEmmUrlLabel");
			verifyElementIsVisible("airwatchUsernameLabel");
			waitForElementsOfPreferencesPage("airwatchEmmKeyLabel");
			verifyElementIsVisible("airwatchEmmKeyLabel");
			waitForElementsOfPreferencesPage("airwatchEmmGroupidLabel");
			verifyElementIsVisible("airwatchEmmGroupidLabel");
			airwatchStrings.put("airwatchEmmUrlLabel", "settings.preferences.emm.url");
			airwatchStrings.put("airwatchEmmKeyLabel", "settings.preferences.emm.key");
			airwatchStrings.put("airwatchEmmGroupidLabel", "settings.preferences.emm.group_id");
			verifyAirwatchUsernameAndPassword(languageCode);
			verifyAirwatchCertificateAndPassword(languageCode);
			if (verifyEmmStrings(airwatchStrings, languageCode)) {
				LOGGER.info("Verifed Emm Strings on Airwatch Page");
				return true;
			} else {
				LOGGER.error("Error: Unable to Verify Emm Strings on Airwatch Page");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in method verifyEmmStringOnAirwatchPage : " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method inputs Url , Key , GroupId while configuring Airwatch
	 * @param url
	 * @param key
	 * @param groupId
	 */
	public final void enterValuesOfUrlKeyGroupId(String url, String key, String groupId) {
		try {
			waitForElementsOfPreferencesPage("airwatchEmmUrl");
			verifyElementIsPresentOnPreferencesPage("airwatchEmmUrl");
			waitForElementsOfPreferencesPage("airwatchEmmKey");
			verifyElementIsPresentOnPreferencesPage("airwatchEmmKey");
			waitForElementsOfPreferencesPage("airwatchEmmGroupid");
			verifyElementIsPresentOnPreferencesPage("airwatchEmmGroupid");
			enterTextForPreferencesPage("airwatchEmmUrl", url);
			enterTextForPreferencesPage("airwatchEmmKey", key);
			enterTextForPreferencesPage("airwatchEmmGroupid", groupId);
		} catch (Exception e) {
			LOGGER.error("Exception occured in method enterValuesOfUrlKeyGroupId : " + e.getMessage());
		}
	}

	/**
	 * This method Configures EMM Airwatch tenant Using Basic Authentication
	 * 
	 * @return boolean value true if airwatch configuration is saved ,false if airwatch configuration fails to save
	 */
	public final boolean configureAirwatchUsingBasicAuthentication(String username, String password) {
		boolean flag = false;
		try {
			clickOnElementsOfPreferencesPage("airwatchBasicAuthRadiobutton");
			enterTextForPreferencesPage("airwatchUsername", username);
			enterTextForPreferencesPage("airwatchPassword", password);
			if (verifyElementsOfPreferencesPage("airwatchSaveButton")) {
				clickOnElementsOfPreferencesPage("airwatchSaveButton");
				LOGGER.info("Saved Airwatch Configuration Successfully");
				flag = true;
			} else {
				LOGGER.info("Save button for Airwatch toggle is disabled");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in method configureAirwatchUsingBasicAuthentication : " + e.getMessage());
		}
		return flag;
	}

	/**
	 * This method browse file explorer and uploads Airwatch certificate
	 */
	public final void verifyUploadAirwatchCertificate() {
		String userDirectory = System.getProperty("user.dir");
		StringSelection certificateLoaction = new StringSelection(userDirectory + "\\emmAirwatchCertificate\\" + airwatchCredentials.get("CERTIFICATE"));
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(certificateLoaction, null);
		try {
			Robot robot = new Robot();
			robot.setAutoDelay(3000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			robot.setAutoDelay(3000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			LOGGER.error("Exception occured while Initializing Robot class to Upload certificate file : " + e.getMessage());
		}
	}

	/**
	 * This method validates uploaded airwatch certificate
	 * 
	 * @return boolean value true if certificate is validated with actual one ,false if certificate validation fails
	 */
	public final boolean certificateValidate() {
		boolean flag = false;
		try {
			sleeper(5000); // Takes time to upload certificate hence 5 secs wait is required
			if (getTextOfPreferencesPage("airwatchCertificateUploadedFileName").equalsIgnoreCase(airwatchCredentials.get("CERTIFICATE_FILE"))) {
				LOGGER.info("Airwatch Certificate Validated Successfully");
				flag = true;
			} else {
				LOGGER.error("Error Validating Airwatch Certificate");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in method certificateValidate : " + e.getMessage());
			flag = false;
		}
		return flag;
	}

	/**
	 * This method Configures EMM Airwatch tenant Using Certificate Authentication
	 * 
	 * @return boolean value boolean value true if airwatch configuration is saved using certificate authentication ,false if airwatch configuration fails to save using certificate
	 *         authentication
	 */
	public final boolean configureAirwatchUsingCertificateAuthentication(String certificateFile,String certificatePassword) {
		boolean flag = false;
		try {
			clickOnElementsOfPreferencesPage("airwatchCertificateAuthRadiobutton");
			if (verifyElementsOfPreferencesPage("airwatchBrowseButton")) {
				mousehoverOnPreferencesPage("airwatchBrowseButton");
				fileImportInV3(ConstantPath.IMPORT_PATH + certificateFile);
				sleeper(2000);
				enterTextForPreferencesPage("airwatchCertificationPassword", certificatePassword);
				if (certificateValidate()) {
					if (verifyElementsOfPreferencesPage("airwatchSaveButton")) {
						waitForElementsOfPreferencesPage("airwatchSaveButton");
						clickOnElementsOfPreferencesPage("airwatchSaveButton");
						LOGGER.info("Saved Airwatch Configuration Successfully");
						flag = true;
					} else {
						LOGGER.info("Save button is disabled ");
						return flag;
					}
				} else {
					return flag;
				}
			} else {
				LOGGER.info("Browse button is disabled ");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in configureAirwatchUsingCertificateAuthentication" + e.getMessage());
		}
		return flag;
	}

	/**
	 * This method import file for Veneer3 UI
	 * @param filename
	 * @throws Exception 
	 */
	public final void importFileV3PreferencePage(String filename) throws Exception {
		
		    clickOnElementsOfPreferencesPage("airwatchBrowseButton");
	        sleeper(2000);
	        StringSelection path = new StringSelection(ConstantPath.IMPORT_PATH + filename);
	        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);                                    
	        Robot robot = new Robot();
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        sleeper(500);
	        robot.keyPress(KeyEvent.VK_ENTER);
	        robot.keyRelease(KeyEvent.VK_ENTER);
	}
	/**
	 * This method configures Airwatch using Basic or Certificate Authentication
	 * 
	 * @param authenticationType
	 * @param languageCode
	 * @return boolean value true if airwatch configures using basic or certificate authentication depending on arguments passed false if airwatch configuration fails using basic or
	 *         certificate authentication depending on arguments passed
	 * 
	 */
	public final boolean verifyAirwatchConfiguration(String authenticationType, String languageCode) {
		boolean flag = false;
		try {
			if (verifyEmmStringOnAirwatchPage(languageCode)) {
				enterValuesOfUrlKeyGroupId(airwatchCredentials.get("URL"), airwatchCredentials.get("KEY"), airwatchCredentials.get("GROUP_ID"));
				if (authenticationType.equalsIgnoreCase("BasicAuthentication")) {
					if (configureAirwatchUsingBasicAuthentication(airwatchCredentials.get("USERNAME"), airwatchCredentials.get("PASSWORD"))) {
						flag = true;
					} else {
						return flag;
					}
				} else if (authenticationType.equalsIgnoreCase("CertificateAuthentication")) {
					if (configureAirwatchUsingCertificateAuthentication(airwatchCredentials.get("CERTIFICATE_FILE"), airwatchCredentials.get("CERTIFICATE_PASSWORD"))) {
						LOGGER.info("Airwatch Configured Successfully Using Certificate Authentication");
						flag = true;
					} else {
						LOGGER.info("Airwatch configuration Failed Using Certificate Authentication");
						return flag;
					}
				} else {
					LOGGER.info("Default Authetication type can be : 1.Basic or 2.Certificate");
					return flag;
				}
			} else {
				LOGGER.error("Failed to validate all Airwatch strings");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyAirwatchConfiguration" + e.getMessage());
			flag = false;
		}
		return flag;
	}

	/**
	 * This method Configures Airwatch tenant with success message
	 * 
	 * @return boolean value true if existing success message is displayed ,false if success notification popup is not displayed
	 * @throws Exception
	 */
	public final boolean verifyAirwatchConfigurationWithSucessMessage() throws Exception {
		boolean flag = false;
		try {
			waitForElementsOfPreferencesPage("successNotification");
			if (verifyElementsOfPreferencesPage("successNotification")) {
				LOGGER.info("Success Notification is displayed on configuring EMM Airwatch");
				flag = true;
			} else {
				LOGGER.info("Success Notification is not displayed on configuring EMM Airwatch");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyAirwatchConfigurationWithSucessMessage" + e.getMessage());
			flag = false;
		}
		return flag;
	}

	/**
	 * This method verify Existing Emm tenant Error message
	 * 
	 * @param languageCode
	 * @return boolean value true if existing error message is displayed ,false if error existing popup is not displayed
	 */
	public final boolean verifyAlreadyLinkErrorMessage(String languageCode) {
		boolean flag = false;
		try {
			waitForElementsOfPreferencesPage("emmIntegrationNotification");
			if (getTextOfPreferencesPage("emmIntegrationNotification").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "companies.details.section.emm.error_409"))) {
				pressKey(Keys.ESCAPE);
				LOGGER.info("Error message is found");
				flag = true;
			} else {
				LOGGER.error("Error popup is not dipalyed");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeExistingDevicePresentOnDaas" + e.getMessage());
			flag = false;
		}
		return flag;
	}

	/**
	 * This method verify Intune strings and configures Intune
	 * 
	 * @param languageCode
	 * @return boolean value true if Intune strings are verified and configured successfully and ,false if Intune configuration fails and Intune strings validation fails
	 */
	public final boolean verifyEmmIntuneConfiguration(String languageCode) {
		boolean flag = false;
		try {
			if (verifyEmmStringOnIntunePage(languageCode)) {
				if (configureIntuneOnPreferencesTab(intuneCredentials.get("DOMAIN_NAME"))) {
					LOGGER.info("Successfully Configured Intune");
					flag = true;
				} else {
					LOGGER.info("Failed to configure Intune on prefereneces page");
					flag = false;
				}
			} else {
				LOGGER.info("Failed to validate all Intune strings");
				flag = false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyEmmIntuneConfiguration" + e.getMessage());
			flag = false;
		}
		return flag;
	}

	/**
	 * This method verify success notification on configuring Intune on preferenecs page
	 * 
	 * @return boolean value true if Intune configures successfully ,false Intune configuration fails to notify successfully
	 * @throws Exception
	 */
	public final boolean verifySuccessMessageOfIntuneConfiguration() {
		boolean flag = false;
		try {
			if (loginToIntunePortal(intuneCredentials.get("INTUNE_ID"), intuneCredentials.get("INTUNE_PASSWORD"))) {
				waitForElementsOfPreferencesPage("successNotification");
				if (verifyElementsOfPreferencesPage("successNotification")) {
					if ((getTextOfPreferencesPage("domainText").equals(intuneCredentials.get("DOMAIN_NAME")))) {
						flag = true;
					} else {
						LOGGER.info("Error : Intune credentils are not matched for EMM on Preferences tab");
						return flag;
					}
				} else {
					LOGGER.error("Error : Success Notification is not displayed on configuring an EMM");
					return flag;
				}
			} else {
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception in method verifySuccessMessageOfIntuneConfiguration" + e.getMessage());
			flag = false;
		}
		return flag;
	}

	/**
	 * This method Configures EMM Intune tenant
	 * 
	 * @return boolean value
	 */
	public final boolean configureIntuneOnPreferencesTab(String domainName) {
		boolean flag = false;
		try {
			enterTextForPreferencesPage("domainName", domainName);
			if (verifyElementIsEnableOfPreferencesPage("intuneSignInButton")) {
				clickOnElementsOfPreferencesPage("intuneSignInButton");
			} else {
				LOGGER.error("Error : Sign In button for Intune toggle is disabled");
				return flag;
			}
			flag = true;
		} catch (Exception e) {
			LOGGER.error("Exception in method configureIntuneOnPreferencesTab" + e.getMessage());
			flag = false;
		}
		return flag;
	}

	/**
	 * @param languageCode -languageCode for the localization testing
	 * @return - true if SNOW and non SNOW setting present for the user else return false
	 * @throws Exception
	 */
	public boolean verifySnowAndNonSnowSettingPresent(String languageCode) throws Exception {
		try {
			goToPreferencesTab();

			if (verifyElementIsPresentOnPreferencesPage("inheritGlobalToggleText")) {
				if (getTextOfPreferencesPage("inheritGlobalToggleText").equalsIgnoreCase(
						getTextLanguage(languageCode, "lhserver", "support_admin.service_now.inherit_global_snow"))
						&& matchTextOfPreferencesPage("enableServiceNowIntegration",
								getTextLanguage(languageCode, "lhserver", "adep.adep_custom_modal.enable"))) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		} catch (Exception ex) {
			LOGGER.error("Exception thrown in method verifySnowAndNonSnowSettingPresent" + ex.getMessage());
			throw ex;
		}
	}

	/**
	 * 
	 * @return - true if non SNOW setting present for the user else return false
	 * @throws Exception
	 */
	public boolean verifyNonSnowSettingPresent() throws Exception {
		try {
			goToPreferencesTab();
			if (verifyElementsOfPreferencesPage("incidentSetting")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in method verifyNonSnowSettingPresent" + ex.getMessage());
			throw ex;
		}
	}

	/**
	 * verify chromebook integration toggle behaviour
	 * @return
	 */
	public final boolean verifyToggleCheck() {
		boolean flag = false;
		try {
			if (verifyElementsOfPreferencesPage("daasChromebookIntegrationConfigLabel")) {
				LOGGER.info("Chromebook integration pop-up opened");
				if (verifyElementsOfPreferencesPage("chromebookToggleStateOff")) {
					LOGGER.info("Verified by-default chromebook integration toggle state will be always off");
					enterChromebookIntegrationDetails(PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME, PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD);
					sleeper(4000);
					if(verifyChromeConfigurationSuccessMessage()== true)
					{
						refreshPage();
						clickOnElementsOfPreferencesPage("preferenceTab");
						mousehoverOnPreferencesPage("chromebookIntegrationEnabledEditButton");
						clickOnElementsOfPreferencesPage("chromebookIntegrationEnabledEditButton");
						LOGGER.info("Editing chromebook integration details");
						if (verifyElementsOfPreferencesPage("chromebookToggleStateOn")) {
							LOGGER.info("Verified toggle is On");
							if (verifyChrombookIntegrationDisabled()) {
								sleeper(3000);
								mousehoverOnPreferencesPage("chromebookIntegrationEnabledEditButton");
								clickOnElementsOfPreferencesPage("chromebookIntegrationEnabledEditButton");
								LOGGER.info("Clicked on edit chromebook details");
								if (verifyElementsOfPreferencesPage("chromebookToggleStateOff")) {
									LOGGER.info("Verified toggle is Off");
									clickOnElementsOfPreferencesPage("chromeIntegrationCancelButton");
									refreshPage();
									clickOnElementsOfPreferencesPage("preferenceTab");
									flag = true;
								} else {
									flag = false;
								}
							} else {
								flag = false;
							}
						} else {
							flag = false;
						}
					} else {
						flag = false;
					}
				} else {
					LOGGER.error("Error while verifying by-default toggle state");
					flag = false;
				}
			} else {
				LOGGER.error("Error while opening chromebook integration pop-up window");
				flag = false;
			}
		}catch (Exception ex) {
			LOGGER.error("Exception occured in verifyToggleCheck" + ex.getMessage());
		}
		return flag;

	}

	/**
	 * This method configures chromebook integration into daas tenant 
	 * @return
	 */
	public final boolean enterChromebookIntegrationDetails(String googleUsername,String googlePassword) {
		boolean flag = false;
		try {
			if (verifyElementsOfPreferencesPage("chromebookToggleStateOff")) {
				chromeIntegrationToggleCheck("ON");
			}
			enterTextForPreferencesPage("googleOrganizationPathInput", googleUsername);
			if (verifyElementsOfPreferencesPage("daasGetAuthCodeLable")) {
				String authorizationCode = getAuthorizationCode(googleUsername,googlePassword);
				clickOnElementsOfPreferencesPage("googleAuthCodeInput");
				enterTextForPreferencesPage("googleAuthCodeInput", authorizationCode);
				if (verifyElementsOfPreferencesPage("chromebookIntegrationSaveButton")) {
					clickOnElementsOfPreferencesPage("chromebookIntegrationSaveButton");
					flag = true;
				} else {
					LOGGER.error("Error while saving chromebook integration details");
					flag = false;
				}
			} else {
				LOGGER.error("Unable to verify string : " + getTextOfPreferencesPage("daasGetAuthCodeLable"));
				flag = false;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyChromeConfigurationSuccessMessageStrings" + ex.getMessage());
		}
		return flag;
	}

	/**
	 * This method generates authorization code
	 * @return
	 */
	public final String generateChromeConfigurationGoogleAuthCode() {
		try {
			clickOnElementsOfPreferencesPage("googleHpDaasPermissionAllowButton");
			sleeper(2000);//Takes time to open google permission pop-pup
			clickOnElementsOfPreferencesPage("googleHpDaasPermissionAllowButton");
			sleeper(2000);//Takes time to open google permission pop-pup
			clickOnElementsOfPreferencesPage("googleHpDaasPermissionAllowButton");
			sleeper(2000);//Takes time to open google permission pop-pup
			scrollOnPreferencesPage("googleApproveHpdaasChoices");
			clickOnElementsOfPreferencesPage("googleApproveHpdaasChoices");
			sleeper(5000);
			String str = getDriver().getTitle();
			switchBackToPreviousTab();
			sleeper(2000);
			String[] authCodeSplitArray = str.split("=");
			String[] authCodeGenerateArray = authCodeSplitArray[1].split("&");
			authcode = authCodeGenerateArray[0];
		} catch (Exception ex) {
			LOGGER.error("Exception occured in getAuthorizationCode" + ex.getMessage());
		}
		return authcode;
	}

	/**
	 * This method verifies existing google login accounts and generates authcode
	 * @return
	 */
	public final String getAuthorizationCode(String googleUsername, String googlePassword) {
		
		try {
			clickOnElementsOfPreferencesPage("daasGetAuthCodeLable");
			switchToDifferentTab();
			waitForElementsOfPreferencesPage("googleSignInLabel");
			if (verifyElementsOfPreferencesPage("googleSignInLabel")) {
				enterTextForPreferencesPage("googleUsername", googleUsername);
				clickOnElementsOfPreferencesPage("googleNextButton");
				enterTextForPreferencesPage("googlePassword", googlePassword);
				clickOnElementsOfPreferencesPage("googlePasswordNextButton");
				verifyGooglePermission();	
				authcode = generateChromeConfigurationGoogleAuthCode();
				
			} else if (getTextOfPreferencesPage("googleExistingLoginCheck").equalsIgnoreCase(googleUsername)) {
				clickOnElementsOfPreferencesPage("googleExistingLoginCheck");
				verifyGooglePermission();
				authcode = generateChromeConfigurationGoogleAuthCode();
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in getAuthorizationCode" + ex.getMessage());
		}
		return authcode;
	}

	/**
	 * This method verify chromebook integration validation error messages
	 * @param languageCode
	 * @return
	 */
	public final boolean verifyChromeIntegrationValidationErrorMessageStrings(String languageCode) {
		boolean flag = false;
		try {
			clickOnElementsOfPreferencesPage("chromebookIntegrationEditButton");
			chromeIntegrationToggleCheck("ON");
			clickOnElementsOfPreferencesPage("chromebookIntegrationSaveButton");
			if (matchTextOfPreferencesPage("chromeValidUsernameErrorLabel", getTextLanguage(languageCode, "lhserver", "form.validate.required")) && matchTextOfPreferencesPage("chromeValidAuthCodeErrorLabel", getTextLanguage(languageCode, "lhserver", "form.validate.required"))) {
				LOGGER.info("Validated chromebook integration validation error messages strings");
				flag = true;
			} else {
				LOGGER.error("Unable to validate chromebook integration error messages strings");
				flag = false;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyChromeIntegrationValidationErrorMessageStrings" + ex.getMessage());
		}
		return flag;
	}

	/**
	 * This method verify toggle On/Off
	 * @param toggleState
	 * @return
	 */
	public final boolean chromeIntegrationToggleCheck(String toggleState) {
		boolean toggleCheck = false;
		try {
			switch (toggleState) {
			case "ON":
				clickOnElementsOfPreferencesPage("chromebookToggleStateOff");
				if (verifyElementsOfPreferencesPage("chromebookToggleStateOn")) {
					LOGGER.info("Enabled chromebook integration toggle");
					toggleCheck = true;
				} else {
					LOGGER.error("unable to verify toggle state as On");
				}
				break;
			case "OFF":
				clickOnElementsOfPreferencesPage("chromebookToggleStateOn");
				waitForElementsOfPreferencesPage("chromebookToggleStateOn");
				if (verifyElementsOfPreferencesPage("chromebookToggleStateOff")) {
					LOGGER.info("Disabled chromebook integration toggle");
					toggleCheck = true;
				} else {
					LOGGER.error("unable to verify toggle state as Off");
				}
				break;
			default:
				LOGGER.error("Invalid toggle state : " + toggleState);
			}
		}catch (Exception ex) {
			LOGGER.error("Exception occured in chromeIntegrationToggleCheck" + ex.getMessage());
		}
		return toggleCheck;

	}

	/**
	 * This method verify chromebook integration configured success message strings 
	 * @param languageCode
	 * @return
	 */
	public final boolean verifyChromeConfigurationSuccessMessageStrings(String languageCode) {
		boolean flag = false;
		try {
			verifyElementsOfPreferencesPage("emmIntegrationNotification");
			waitForElementsOfPreferencesPage("chromeIntegrationEnabledDisabledString");
			if (matchTextOfPreferencesPage("chromeIntegrationUsernameString", PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME) && matchTextOfPreferencesPage("chromeIntegrationEnabledDisabledString", getTextLanguage(languageCode, "daas_ui", "settings.data.enabled"))) {
				clickOnElementsOfPreferencesPage("closeToastNotification");
				LOGGER.info("Chrome Integration " + getTextOfPreferencesPage("emmIntegrationNotification"));
				LOGGER.info("Validated String :  " + getTextOfPreferencesPage("chromeIntegrationEnabledDisabledString"));
				flag = true;
			} else {
				LOGGER.error("Unable to match chromebook integration configuration details");
				flag = false;
			}
		}catch (Exception ex) {
			LOGGER.error("Exception occured in verifyChromeConfigurationSuccessMessageStrings" + ex.getMessage());
		}
		return flag;

	}

	/**
	 * This method verify chrome enterprise integration is saved successfully
	 * @return
	 */
	public final boolean verifyChromeConfigurationSuccessMessage() {
		boolean flag = false;
		try {
			if (verifyElementsOfPreferencesPage("emmIntegrationNotification") && verifyElementsOfPreferencesPage("chromeIntegrationEnabledDisabledString")) {
				LOGGER.info("Validated String :  " + getTextOfPreferencesPage("chromeIntegrationEnabledDisabledString"));
				refreshPage();
				clickOnElementsOfPreferencesPage("preferenceTab");
				flag = true;
			} else {
				LOGGER.error("Unable to verify chromebook integration configuration details");
				flag = false;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyChromeConfigurationSuccessMessage" + ex.getMessage());
		}
		return flag;
	}

	/**
	 * This method verify chrome integration disabled strings
	 * @param languageCode
	 * @return
	 */
	public final boolean verifyChrombookIntegrationDisabledStrings(String languageCode) {
		boolean flag = false;
		try {
			LOGGER.info("Verifying Chrome Integration disabled strings");
			authcode = getAuthorizationCode(PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME,PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD);
			clickOnElementsOfPreferencesPage("googleAuthCodeInput");
			enterTextForPreferencesPage("googleAuthCodeInput", authcode);
			chromeIntegrationToggleCheck("OFF");
			clickOnElementsOfPreferencesPage("chromebookIntegrationSaveButton");
			verifyElementsOfPreferencesPage("emmIntegrationNotification");
			waitForElementsOfPreferencesPage("chromeIntegrationEnabledDisabledString");
			if (matchTextOfPreferencesPage("chromeIntegrationEnabledDisabledString", getTextLanguage(languageCode, "daas_ui", "settings.data.disabled"))) {
				LOGGER.info("Chrome Integration " + getTextOfPreferencesPage("emmIntegrationNotification"));
				clickOnElementsOfPreferencesPage("closeToastNotification");
				refreshPage();
				clickOnElementsOfPreferencesPage("preferenceTab");
				LOGGER.info("Validated String :  " + getTextOfPreferencesPage("chromeIntegrationEnabledDisabledString"));
				flag = true;
			} else {
				LOGGER.error("Unable to verify chromebook integration disabled strings");
				flag = false;
			}

		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyChrombookIntegrationDisabledStrings" + ex.getMessage());
		}
		return flag;
	}

	/**
	 * verify chromebook integration is disabled
	 * @return
	 */
	public final boolean verifyChrombookIntegrationDisabled() {
		boolean flag = false;
		try {
			authcode = getAuthorizationCode(PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME,PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD);
			clickOnElementsOfPreferencesPage("googleAuthCodeInput");
			enterTextForPreferencesPage("googleAuthCodeInput", authcode);
			chromeIntegrationToggleCheck("OFF");
			clickOnElementsOfPreferencesPage("chromebookIntegrationSaveButton");
			if (verifyElementsOfPreferencesPage("emmIntegrationNotification") && verifyElementsOfPreferencesPage("chromeIntegrationEnabledDisabledString")) {
				clickOnElementsOfPreferencesPage("closeToastNotification");
				LOGGER.info("Chrome Integration " + getTextOfPreferencesPage("emmIntegrationNotification"));
				LOGGER.info("Validated String :  " + getTextOfPreferencesPage("chromeIntegrationEnabledDisabledString"));
				flag = true;
			} else {
				LOGGER.error("Unable to validate chromebook integration is disable");
				flag = false;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyChrombookIntegrationDisabled" + ex.getMessage());
		}
		return flag;
	}

	/**
	 * verify chromebook strings on preferences page 
	 * @param languageCode
	 * @return
	 */
	public final boolean verifyChromebookIntegrationPreferencePageStrings(String languageCode){
		boolean flag = false;
		try {
			if (matchTextOfPreferencesPage("daasChromebookIntegrationLabel", getTextLanguage(languageCode, "daas_ui", "devicelist.emmtools.chrome_enterprise")) && matchTextOfPreferencesPage("daasNotConfiguredLabel", getTextLanguage(languageCode, "lhserver", "settings.preferences.emm.not_configured"))) {
				LOGGER.info("Validated chromebook integration prefreneces page strings");
				flag = true;
			} else {
				LOGGER.error("Error while validating chromebook integration strings on preferences page");
				flag = false;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyChromebookIntegrationPreferencePageStrings" + ex.getMessage());
		}
		return flag;
	}

	/**
	 * verify chrome configuration strings
	 * @param languageCode
	 * @return
	 */
	public final boolean verifyChromebookConfigurationStrings(String languageCode) {
		boolean flag = false;
		try {
			LOGGER.info("Validating chrome enterprise integration strings");
			HashMap<String, String> chromebookConfiguration = new HashMap<String, String>();
			chromeIntegrationToggleCheck("ON");
			chromebookConfiguration.put("daasChromebookIntegrationConfigLabel", "companies.details.chromebook.title");
			chromebookConfiguration.put("daasEnableChromebookIntegrationLabel", "companies.details.chromebook.toggle.label");
			chromebookConfiguration.put("daasChromeUsernameLabel", "global.username");
			chromebookConfiguration.put("daasAuthenticationCodeLabel", "companies.details.chromebook.auth_code.label");
			chromebookConfiguration.put("daasGetAuthCodeLable", "companies.details.chromebook.get_auth_code");
			if (verifyChromebookIntegrationStrings(chromebookConfiguration, languageCode)) {
				LOGGER.info("Validated chrome enterprise integration strings");
				flag = true;
			} else {
				LOGGER.error("Error: Unable to validated chromebook integration strings");
				flag = false;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyChromebookConfigurationStrings" + ex.getMessage());
		}
		return flag;
	}

	/**
	 * verify match chromebook configuration related strings 
	 * @param chromeMap
	 * @param languageCode
	 * @return
	 */
	public final boolean verifyChromebookIntegrationStrings(HashMap<String, String> chromeMap, String languageCode) {
		boolean chromeCheck = true;
		try {
			Set<Entry<String, String>> set = chromeMap.entrySet();
			Iterator<Entry<String, String>> iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> mentry = (Map.Entry<String, String>) iterator.next();
				if (getTextOfPreferencesPage(mentry.getKey().toString()).equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", mentry.getValue().toString()))) {
					LOGGER.info(getTextLanguage(languageCode, "daas_ui", mentry.getValue().toString()) + " is Displyed");
				} else {
					LOGGER.error("Error : " + mentry.getValue() + " is not Displyed");
					chromeCheck = false;
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in test case verifyChromebookIntegrationStrings " + ex.getMessage());
		}
		return chromeCheck;
	}

	/**
	 * @return - true if Required App not Install subtype is present for the company else return false
	 * @throws Exception
	 */
	public boolean verifyIsRequiredAppNotInstalledSubtypePresent() throws Exception {
		try {
			goToPreferencesTab();
			waitForElementsOfPreferencesPage("preferencesTitle");
			clickOnElementsOfPreferencesPage("incidentTypeSubtypeEditIcon");
			clickOnElementsOfPreferencesPage("softwareHealthType");	
			scrollOnPreferencesPage("requiredAppNotInstalledSubtype");
			waitForElementsOfPreferencesPage("requiredAppNotInstalledSubtype");
			return verifyElementIsPresentOnPreferencesPage("requiredAppNotInstalledSubtype");
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyIsRequiredAppNotInstalledSubtypePresent" + ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Method to enter Microsoft Telemetry configuration and log into Microsoft
	 * azure portal
	 *
	 * @param domainName
	 *            Microsoft Telemetry domain name
	 * @param omsUrl
	 *            Microsoft Telemetry URL
	 * @param workspaceId
	 *            Microsoft Telemetry workspace Id
	 * @throws Exception
	 */
	public final void enterOMSdetailsAndLoginToAzure(String domainName, String omsUrl, String workspaceId) throws Exception {
		moveToElementOnPreferencesPage("3rdPartyAppTileHeader");

//		verifyElementsOfPreferencesPage("microsoftTelemetryNotConfigured");
		verifyElementsOfPreferencesPage("microsoftTelemetryEditURLButton");
		clickOnElementsOfPreferencesPage("microsoftTelemetryEditURLButton");

		LOGGER.info("Verified and clicked on Edit icon to enter OMS URL details");

		verifyElementsOfPreferencesPage("microsoftTelemetryToggleOnPopUp");
		LOGGER.info("Verified and clicked on OMS URL configuration toggle");

		verifyElementsOfPreferencesPage("microsoftTelemetryDomainNameLabel");
		verifyElementsOfPreferencesPage("microsoftTelemetryOMSURLLabel");
		verifyElementsOfPreferencesPage("microsoftTelemetryWorkspaceIdLabel");
		sleeper(2000);
		LOGGER.info("Verified Domain Name, OMS URL and Workspace Id fields on pop-up");
		boolean isEdit= verifyElementsOfPreferencesPage("microsoftTelemetryToggleOn");
		if (isEdit==true) {
			clickOnElementsOfPreferencesPage("microsoftTelemetryToggleOn");
			sleeper(4000);
			LOGGER.info("Turned on the toggle to configure MS Telemetry");
			enterTextForPreferencesPage("microsoftTelemetryDomainNameInput", domainName);
			enterTextForPreferencesPage("microsoftTelemetryOMSURLInput", omsUrl);
			enterTextForPreferencesPage("microsoftTelemetryWorkspaceIdInput", workspaceId);
			LOGGER.info("Entered details for Domain Name, OMS URL and Workspace Id fields on pop-up");

			verifyElementsOfPreferencesPage("microsoftTelemetrySaveButton");
			verifyElementsOfPreferencesPage("microsoftTelemetryCancelButton");
			LOGGER.info("Verified Save and Cancel buttons on pop-up");

			clickOnElementsOfPreferencesPage("microsoftTelemetrySaveButton");
			LOGGER.info("Clicked on Save button");

			waitForElementsOfPreferencesPage("microsoftAzureSignInButton");
			enterTextForPreferencesPage("microsoftAzureSignInButton",
					PreferenceVariables.AZURE_ID_TO_CONFIGURE_MICROSOFT_TELEMETRY);
			LOGGER.info("Entered username in azure portal");

			clickOnElementsOfPreferencesPage("microsoftAzureSubmitButton");
			LOGGER.info("Clicked on submit button after entering username");

			waitForElementsOfPreferencesPage("microsoftAzurePasswordTextbox");
			enterTextForPreferencesPage("microsoftAzurePasswordTextbox", PreferenceVariables.PASSOWRD_FOR_AZURE_ID);
			LOGGER.info("Entered password in azure portal");

			clickOnElementsOfPreferencesPage("microsoftAzureSubmitButton");
			LOGGER.info("Clicked on submit button after entering password");
			
			clickOnElementsOfPreferencesPage("microsoftAzureSubmitButton");
			LOGGER.info("Clicked on yes button after signin");
			sleeper(5000);
			waitForElementsOfPreferencesPage("successNotification");
		}
		else {
			
			clickOnElementsOfPreferencesPage("microsoftTelemetrySaveButton");
			LOGGER.info("Clicked on Save button");
		}
	}

	/**
	 * This method verifies configured OMS URL
	 *
	 * @param omsUrl
	 * @throws Exception
	 */
	public final boolean verifyOMSUrlConfigured(String omsUrl) throws Exception {
		sleeper(5000);
		waitForElementsOfPreferencesPage("microsoftTelemetryOMSURLEnabled");
		clickOnElementsOfPreferencesPage("microsoftTelemetryOMSURLEnabled");
		LOGGER.info("Verified 'Enabled' text for OMS URL");
		String locator = preferencesPagePropertiesPage.getProperty("microsoftTelemetryConfiguredURL");
		locator = locator.replace("{parameter}", omsUrl);
		if (waitUntillElementIsPresent(locator)) {
			LOGGER.info("Verified configured OMS URL");
			clickOnElementsOfPreferencesPage("microsoftTelemetrySaveButton");
			return true;	
		} else {
			LOGGER.error("Dispalyed OMS URL is different from the entered URL");
		}
		return false;
	}

	/**
	 * This method verifies error message when user tries to configure a domain that
	 * is already configured
	 *
	 * @throws Exception
	 */
	public final void verifyDuplicateDomainMessage() throws Exception {
		waitForElementsOfPreferencesPage("microsoftTelemetryDuplicateDomainErrorMessage");
		verifyElementsOfPreferencesPage("microsoftTelemetryDuplicateDomainErrorMessage");
		LOGGER.info("Verified Duplicate Domain error message on portal");
	}

	/**
	 * This method verified error message when user tries to save Microsoft
	 * Telemetry details with incorrect Workspace Id
	 *
	 * @throws Exception
	 */
	public final boolean verifyIncorrectWorkspaceIdMessage() throws Exception {
		waitForElementsOfPreferencesPage("microsoftTelemetryIncorrectWorkspaceIdMessage");
		boolean isMessageDisplayed = verifyElementsOfPreferencesPage("microsoftTelemetryIncorrectWorkspaceIdMessage");
		LOGGER.info("Verified Incorrect Workspace Id error message on portal");
		return isMessageDisplayed;
	}

	/**
	 * This method deletes already configured Microsoft Telemetry details
	 *
	 * @throws Exception
	 */
	public final boolean deleteOMSURL(String userType) throws Exception {
		sleeper(2000);
		waitForElementsOfPreferencesPage("microsoftTelemetryNotConfigured");
		verifyElementIsClickableOfPreferencesPage("microsoftTelemetryDeleteButton");
		clickOnElementsOfPreferencesPage("microsoftTelemetryDeleteButton");
		if(userType.equals(PreferenceVariables.MSTUSER_TYPE_MST)) {
		waitForElementsOfPreferencesPage("microsoftTelemetryDelete_MSTUSER");
		clickByJavaScriptOnPreferencesPage("microsoftTelemetryDelete_MSTUSER");
		}
		waitForElementsOfPreferencesPage("microsoftTelemetryDelete");
		clickByJavaScriptOnPreferencesPage("microsoftTelemetryDelete");
		waitForElementsOfPreferencesPage("microsoftTelemetryNotConfigured");
		return verifyElementsOfPreferencesPage("microsoftTelemetryNotConfigured");
	}

	/**
	 * This method verifies that microsoft telemetry pop-up remains on screen in case of error
	 *
	 * @throws Exception
	 */
	public final boolean verifyPopUpWithSecuredDetails() throws Exception {

		if (verifyElementsOfPreferencesPage("microsoftTelemetryDomainNameLabel")
				&& verifyElementsOfPreferencesPage("microsoftTelemetryOMSURLLabel")
				&& verifyElementsOfPreferencesPage("microsoftTelemetryWorkspaceIdLabel")) {
			return true;
		} else
			return false;
	}

	/**
	 * This method verifies the companies listed in select a company listbox after clicking Microsoft Telemetry tab
	 *
	 * @throws Exception
	 */
	public boolean verifyCompaniesInListbox(String[] arr) {
		try {
			waitForPageLoaded();
			clickOnElementsOfPreferencesPage("selectACompanyLabel");
			List<WebElement> listOfCompanies = getElementsOfPreferencesPage("listOfCompaniesInListBox");

			if (listOfCompanies.size() >= 1) {
				for (int i = 0; i < listOfCompanies.size(); i++) {
					if (!(listOfCompanies.get(i).getText()).equals(arr[i])) {
						return false;
					}
				}
			} else {
				LOGGER.error("There are no companies present in the list of companies");
				return false;
			}
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception occured: " + e.getMessage());
			return false;
		}
	}

	public boolean verifyMessageDisplayedWhenOMSURLNotConfigured(String companyName, String expectedText) {
		boolean isPresent = false;
		try {
			waitForPageLoaded();
			clickOnElementsOfPreferencesPage("selectACompanyLabel");
			List<WebElement> listOfCompanies = getElementsOfPreferencesPage("listOfCompaniesInListBox");

			if (listOfCompanies.size() >= 1) {
				for (int i = 0; i < listOfCompanies.size(); i++) {
					if (listOfCompanies.get(i).getText().equals(companyName)) {
						listOfCompanies.get(i).click();
						Assert.assertTrue(matchTextOfPreferencesPage("URLNotConfiguredLabel", expectedText),
								"Message when URL not configured doesn't match");
						isPresent = true;
					}
					if (isPresent)
						break;
				}
			} else
				LOGGER.error("There are no companies present in the dropdown");
		} catch (Exception e) {
			LOGGER.error("Exception occured: " + e.getMessage());
			return false;
		}
		return isPresent;
	}

	public String getConfiguredOMSURL(String companyName) {
		String configuredURL = "";
		try {
			waitForPageLoaded();
			clickOnElementsOfPreferencesPage("selectACompanyLabel");
			List<WebElement> listOfCompanies = getElementsOfPreferencesPage("listOfCompaniesInListBox");
			if (listOfCompanies.size() >= 1) {
				for (int i = 0; i < listOfCompanies.size();) {
					if (listOfCompanies.get(i).getText().equals(companyName)) {
						listOfCompanies.get(i).click();
						switchToDifferentTab();
						waitForElementsOfPreferencesPage("microsoftAzureLogo");
						switchBackToParentWithoutCloseTab();
						switchToDifferentTab();
						configuredURL = getUrlOfCurrentPage();
						switchBackToParentWithoutCloseTab();
					}
					break;
				}
			} else
				LOGGER.error("There are no companies present in the dropdown");
		} catch (Exception e) {
			LOGGER.error("Exception occured: " + e.getMessage());
			return configuredURL;
		}
		return configuredURL;
	}
	/*
	 * This method set given status of the serviceNOW configuration type from company detail page of the Root Admin
	 * @param expectedSNOWStatus : Need to give parameter Which type of status user want to configured for SNOW
	 * @param languageCode : languageCode for localization
	 */
	public String setCompanySNOWToggleAtRoot(String expectedSNOWStatus, String languageCode) {
		try {
			String currentSNOWStatusIfEnable=null;
			waitForPageLoaded();
			waitForElementsOfPreferencesPage("rootSNOWStatus");
			String currentSNOWStatus = getTextOfPreferencesPage("rootSNOWStatus");
			//Getting current status of the SNOW on company detail page of root
			
			if(verifyElementIsPresentOnPreferencesPage("getStatusOfRootIfEnable"))
				currentSNOWStatusIfEnable = getTextOfPreferencesPage("getStatusOfRootIfEnable");			

			//If current status is equal to expected status then returning status
			if(expectedSNOWStatus.equals(currentSNOWStatus)|| expectedSNOWStatus.equals(currentSNOWStatusIfEnable))
				return expectedSNOWStatus;	

			//If expected status is equal to "Not applicable"
			if(expectedSNOWStatus.equals(getTextLanguage(languageCode,"daas_ui","global.not.applicable"))) {
				waitForElementsOfPreferencesPage("rootSNOWEditIcon");
				clickOnElementsOfPreferencesPage("rootSNOWEditIcon");
				WebElement element=	getElementOfPreferencePage("snowToggleAtRoot");
				boolean toggleValue = Boolean.parseBoolean(element.getAttribute("aria-checked"));
				if(toggleValue)
				clickOnElementsOfPreferencesPage("snowToggleAtRoot");
				clickOnElementsOfPreferencesPage("rootSaveSNOWStatus");
				refreshPage();
				waitForElementsOfPreferencesPage("rootSNOWStatus");
				currentSNOWStatus = getTextOfPreferencesPage("rootSNOWStatus");
				return currentSNOWStatus;
			}
			//If expected status is equal to "Multi tenant"
			else if(expectedSNOWStatus.equals(getTextLanguage(languageCode,"daas_ui","settings.service_now.config.options.multi_tenant"))) {
				waitForElementsOfPreferencesPage("rootSNOWEditIcon");
				clickOnElementsOfPreferencesPage("rootSNOWEditIcon");
				WebElement element=	getElementOfPreferencePage("snowToggleAtRoot");
				boolean toggleValue = Boolean.parseBoolean(element.getAttribute("aria-checked"));
				if(!toggleValue)
					clickOnElementsOfPreferencesPage("snowToggleAtRoot");
				clickOnElementsOfPreferencesPage("expandSNOWDropdown");
				sleeper(2000);
				clickOnElementsOfPreferencesPage("rootSelectMultiTenant");
				clickOnElementsOfPreferencesPage("rootSaveSNOWStatus");	
				waitForElementsOfPreferencesPage("getStatusOfRootIfEnable");
				refreshPage();
				currentSNOWStatus = getTextOfPreferencesPage("getStatusOfRootIfEnable");	
				return currentSNOWStatus;
			}		
			//If expected status is equal to "Single tenant"
			else if(expectedSNOWStatus.equals(getTextLanguage(languageCode,"daas_ui","settings.service_now.config.options.single_tenant"))) {
				waitForElementsOfPreferencesPage("rootSNOWEditIcon");
				clickOnElementsOfPreferencesPage("rootSNOWEditIcon");
				WebElement element=	getElementOfPreferencePage("snowToggleAtRoot");
				boolean toggleValue = Boolean.parseBoolean(element.getAttribute("aria-checked"));
				if(!toggleValue)
					clickOnElementsOfPreferencesPage("snowToggleAtRoot");
				clickOnElementsOfPreferencesPage("expandSNOWDropdown");
				sleeper(2000);
				clickOnElementsOfPreferencesPage("rootSelectSingleTenant");
				clickOnElementsOfPreferencesPage("rootSaveSNOWStatus");
				waitForElementsOfPreferencesPage("getStatusOfRootIfEnable");//Need sleeper to change status
				refreshPage();
				currentSNOWStatus = getTextOfPreferencesPage("getStatusOfRootIfEnable");	
				return currentSNOWStatus;
			}
			return currentSNOWStatus;
		} catch (Exception ex) {
			LOGGER.error("Exception occured in setCompanySNOWToggleAtRoot ");
			return null;
		}
	}
	/**
	 * This method verify Incident Heading on Preferences tab.
	 * @param expectedStatus : The Incident Heading which you expect on Preference tab.
	 * @return boolean : Return true if Incident Heading is expected or return false
	 */
	public boolean verifyIncidentHeading(String expectedStatus) {
		try {
			waitForElementsOfPreferencesPage("incidentHeading");
			if (getTextOfPreferencesPage("incidentHeading").equalsIgnoreCase(expectedStatus.toUpperCase()))
				return true;
		} catch (Exception ex) {
			LOGGER.info("Exception occured in verifyIncidentHeading method " + ex.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * This method verify Incident ChooseTypeSUbType message on Preferences tab.
	 * @param expectedStatus : The Incident Choose Type SubType message which you expect on Preference tab.
	 * @return boolean : Return true if Incident Choose Type SubType message is expected or return false
	 */
	public boolean verifyIncidentChooseTypeSubTypeMessage(String expectedStatus) {
		try {
			waitForElementsOfPreferencesPage("incidentChooseTypeSubTypeMessage");
			if (getTextOfPreferencesPage("incidentChooseTypeSubTypeMessage").equals(expectedStatus))
				return true;
		} catch (Exception ex) {
			LOGGER.info("Exception occured in verifyIncidentHeading method " + ex.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * This method verify Incident Title on Preferences tab.
	 * @param expectedStatus : The Incident Title which you expect on Preference tab.
	 * @return boolean : Return true if Incident Title is expected or return false
	 */
	public boolean verifyIncidentTitle(String expectedStatus, String languageCode) {
		try {
			waitForElementsOfPreferencesPage("incidentTitle");
			if (getTextOfPreferencesPage("incidentTitle").equalsIgnoreCase(expectedStatus))
				return true;
		} catch (Exception ex) {
			LOGGER.info("Exception occured in verifyIncidentHeading method " + ex.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * This method verify Incident Edit icon on Preferences tab.
	 * @param expectedStatus : The Incident Edit icon which you expect on Preference tab.
	 * @return boolean : Return true if Incident edit icon is present or  return false
	 */
	public boolean verifyIncidentEditIcon() {
		try {
			if (waitForElementsOfPreferencesPage("incidentEditIcon"))
				return true;
		} catch (Exception ex) {
			LOGGER.info("Exception occured in verifyIncidentHeading method " + ex.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * This method verify that "All" Subtype checkbox is selected and if not selected than select it on Type-SubType selection UI.
	 * @param typeLocator : The dynamic locator which can represent all the type on TypeSubtype selection UI.
	 * @param subtypeAllCheckboxLocator : The dynamic locator which can represent "All" checkbox of all the Type on TypeSubtype selection UI.
	 * @param numOfIteration : The size of the total Type is present on the TypeSubtype selection UI..
	 */
	public void verifyAllSubtypeCheckBoxIsSelected(String typeLocator, String subtypeAllCheckboxLocator, int numOfIteration) {
		try {
			for (int i = 1; i <= numOfIteration; i++) {
				WebElement typeElement = getElement(typeLocator.split("%i%")[0] + i + typeLocator.split("%i%")[1]);
				verifyElementIsVisible(typeElement);
				typeElement.click();
				WebElement allSubTypeCheckBox = getElement(subtypeAllCheckboxLocator.split("%i%")[0] + i + subtypeAllCheckboxLocator.split("%i%")[1]);
				verifyElementIsVisible(allSubTypeCheckBox);
				String checkboxClassValue = allSubTypeCheckBox.getAttribute("class");
				System.out.println("checkboxClassValue: "+checkboxClassValue);
				if (!(checkboxClassValue.contains("checked"))) {
					allSubTypeCheckBox.click();
					typeElement.click();
				}
				else
					typeElement.click();
			}
			waitForElementsOfPreferencesPage("saveIncidentTypeSubType");
			clickOnElementsOfPreferencesPage("saveIncidentTypeSubType");
		} catch (Exception ex) {
			LOGGER.error("Exception occure in checkAllSubtypeCheckBoxIsSelected method " + ex.getMessage());
		}
	}

	/**
	 * This method verify all the type sybtype is selected on Preferences tab.
	 */
	public void verifyAllTypeSubtypeIsSelected() {
		try {
			waitForElementsOfPreferencesPage("incidentEditIcon");
			clickOnElementsOfPreferencesPage("incidentEditIcon");
			int totalRow = getElementsOfPreferencesPage("totalType").size();
			String typeLocator = preferencesPagePropertiesPage.getProperty("totalTypeRows");
			String subtypeAllCheckboxLocator = preferencesPagePropertiesPage.getProperty("checkBoxofAll");
			verifyAllSubtypeCheckBoxIsSelected(typeLocator, subtypeAllCheckboxLocator, totalRow);
			clickOnElementsOfPreferencesPage("saveIncidentTypeSubType");
			LOGGER.info("All the Subtype checkbox is checked successfully.");
		} catch (Exception ex) {
			LOGGER.error("Exception occure in verifyAllTypeSubtypeIsSelected method " + ex.getMessage());
		}
	}

	/**
	 * This method verify all the Type Subtype is updated on Preferences tab with their respective Type name and All keyword.
	 * @return finalTypeSubTypeStatus : Return the final Type Subtype status in the form of user expected string on preferences tab for Incident tile.
	 */
	public String verifyIncidentTypeSubtypeStatusIsUpdatedToAll() {
		String finalTypeSubTypeStatus = "";
		try {
			
			if (System.getProperty("uiVersion").equalsIgnoreCase("VENEER2")) {
				String incidentTypeSubTypeStatus = preferencesPagePropertiesPage.getProperty("incidentTypeSubTypeStatus");
				int totalRow = getElementsOfPreferencesPage("totalIncidentTypeSubtypeStatus").size();
				if (waitForElementsOfPreferencesPage("incidentTitle")) {
					for (int i = 1; i <= totalRow; i++) {
						WebElement allSubTypeCheckBox = getElement(incidentTypeSubTypeStatus.split("%i%")[0] + i + incidentTypeSubTypeStatus.split("%i%")[1]);
								
						String typeString = allSubTypeCheckBox.getText();
						finalTypeSubTypeStatus = finalTypeSubTypeStatus + typeString + ":";
					}
				}
			} else if (System.getProperty("uiVersion").equalsIgnoreCase("VENEER3")) {
				String incidentTypeSubTypeStatus = preferencesPagePropertiesPage.getProperty("incidentTypeSubTypeStatus");
				int totalRow = getElementsOfPreferencesPage("totalIncidentTypeSubtypeStatus").size();
				if (waitForElementsOfPreferencesPage("incidentTitle")) {
					for (int i = 1; i <= totalRow; i++) {
						WebElement allSubTypeCheckBox = getElement(incidentTypeSubTypeStatus.split("%i%")[0] + i + incidentTypeSubTypeStatus.split("%i%")[1]);
						String typeString = allSubTypeCheckBox.getText();
						finalTypeSubTypeStatus = finalTypeSubTypeStatus + typeString + ":";
					}
			}
				
			}
			return finalTypeSubTypeStatus;
		} catch (Exception ex) {
			LOGGER.error("Exception occure in verifyIncidentTypeSubtypeStatusIsUpdatedToAll method " + ex.getMessage());
			return null;
		}
	}

	/**
	 * This method return the current SNOW status from Root as per given SNOW key.
	 * 
	 * @param snowStatusKey:Key of SNOW status from the daas_ui locals.
	 */
	public final String getSNOWStatusFromRoot(String snowStatusKey) {
		String snowStatus = null;
		boolean value = true;
		if (snowStatusKey.equals("global.not.applicable"))
			value = false;
		try {
			waitForElementsOfPreferencesPage("rootSNOWEditIcon");
			if (waitForElementsOfPreferencesPage("rootSNOWStatus")) {
				if (value) {
					snowStatus = getTextOfPreferencesPage("getStatusOfRootIfEnable");
					return snowStatus;
				} else {
					snowStatus = getTextOfPreferencesPage("rootSNOWStatus");
					return snowStatus;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception caught during getSNOWStatusFromRoot:" + e.getMessage());
			return null;
		}
		return snowStatus;
	}

	/**
	 * This method will configured SNOW at Company>Preferences level and validate multiple scenario of SNOW form like URL, UserName, ClientID, ClientSecret.
	 * @param languageCode- languageCode for the localization testing      
	 * @param url -SNOW configuration url.        
	 * @param userName -SNOW username for SNOW login.     
	 * @param password -SNOW password for SNOW login.       
	 * @return boolean -It will return SNOW configuration status(true/false) from SNOW Integration page.
	 */

	public final boolean tenantLevelSNOWConfiguration(String languageCode, String url, String userName, String password) {
		try {
			if (getAttributesOfPreferencesPage("companyInheritToggleOfSNOW", "class").equals("vn-toggle") && !(getAttributesOfPreferencesPage("companyInheritToggleOfSNOW", "class").contains("toggle--on"))) {
				waitForElementsOfPreferencesPage("snowEditIconAtCompanyLevel");
				clickOnElementsOfPreferencesPage("snowEditIconAtCompanyLevel");
				sleeper(1000);
				waitForElementsOfPreferencesPage("snowToggleAtCompanyLevel");
				if (!(getAttributesOfPreferencesPage("snowToggleAtCompanyLevel", "class").contains("toggle--on"))) {
					clickOnElementsOfPreferencesPage("snowToggleAtCompanyLevel");
					LOGGER.info("SNOW toggle is ON at Global level");
				} else if (waitForElementsOfPreferencesPage("resetConfigurationAtTenantLevel")) {
					clickOnElementsOfPreferencesPage("resetConfigurationAtTenantLevel");
					LOGGER.info("SNOW configuration Reset Successfully.");
					sleeper(1000); // Here wait is needed
					if (verifySNOWToastNotificationOnSNOWForm(languageCode))
						LOGGER.info("SNOW settings successfully Reset at Comapny level.");
					else
						LOGGER.error("Error SNOW settings is not Reset at Company level as expected.");
					clickOnElementsOfPreferencesPage("snowToggleAtCompanyLevel");
					LOGGER.info("Now SNOW toggle is ON at Global level");
				} else
					LOGGER.error("Error Something went wrong with SNOW Enable/Disable Toggle.");
				// Test Case 240711: [DaaS][SNOW] Verify URL and Username fields are accept blank space before or after entering a URL and Username for MSP configuration.
				enterTextForPreferencesPage("urlSnowCompanyLevel", " " + url + " ");
				enterTextForPreferencesPage("adminSnowCompanyLevel", " " + userName + " ");
				enterTextForPreferencesPage("passwordSnowCompanyLevel", password);
				LOGGER.info("All the fields data are entered successfully.");
				verifyElementsOfPreferencesPage("saveButtonSnowCompanyLevel");
				clickOnElementsOfPreferencesPage("saveButtonSnowCompanyLevel");
				LOGGER.info("Cliked on SAVE button successfully.");
				if (verifySNOWToastNotificationOnSNOWForm(languageCode)) {
					// Test Case 221574: [SNOW] Verify after SNOW configuration ClientID and ClientSecret is generated with copy icon button at Preferences page.
					if (verifyElementsOfPreferencesPage("clientIDLabelTenant") && verifyElementsOfPreferencesPage("clientSecretLabelTenant") && verifyElementsOfPreferencesPage("clientIDCopyIcon") && verifyElementsOfPreferencesPage("clientSecretCopyIcon") && verifyElementsOfPreferencesPage("clientIDValue") && verifyElementsOfPreferencesPage("clientSecretValue")) {
						LOGGER.info("ClientId and ClientSecret and respective Copy icon and values is displayed on SNOW form of Company Preferences.");
					} else
						LOGGER.error("Error ClientID or CLientSecret or respective Copy icons and values are not dispalyed at Company Preferences.");
				}
				verifyElementsOfPreferencesPage("cancelIconOfSNOWForm");
				clickOnElementsOfPreferencesPage("cancelIconOfSNOWForm");
				LOGGER.info("SNOW form successfully closed.");
				sleeper(1000); // Need to use wait here.
				if (getTextOfPreferencesPage("snowStatusAtTenant").equals(getTextLanguage(languageCode, "daas_ui", "global.enabled")) && getTextOfPreferencesPage("snowURLStatusAtTenant").equals(url)) {
					return true;
				} else
					LOGGER.error("Error in method verifySNOWToastNotificationOnSNOWForm returns false.");
			} else
				LOGGER.error("Erro Company Preference Inherit SNOW toggle is ON, So Not able to Configured SNOW at Prefernces.");
		} catch (Exception ex) {
			LOGGER.error("Exception occured in tenantLevelSNOWConfiguration.");
			return false;
		}
		return false;
	}

	/**
	 * This method is verifying SNOW success or error message after SNOW Configuration.
	 * 
	 * @param languageCode-languageCode for the localization testing
	 * @return boolean -It will return SNOW Success message status(true/false) from SNOW Integration page.
	 */
	public boolean verifySNOWToastNotificationOnSNOWForm(String languageCode) {
		try {
			verifyElementsOfPreferencesPage("snowMessagePopupAtTenantLevel");
			String snowPopUpMessage = getTextOfPreferencesPage("snowMessagePopupAtTenantLevel");
			verifyElementsOfPreferencesPage("snowMessagePopupCloseAtTenantLevel");
			clickOnElementsOfPreferencesPage("snowMessagePopupCloseAtTenantLevel");
			String successMessage = getTextLanguage(languageCode, "daas_ui", "companies.details.update.success");
			String snowLabel = getTextLanguage(languageCode, "daas_ui", "settings.service_now.read_only.label");
			successMessage = successMessage.replace("{name}", snowLabel);
			if (snowPopUpMessage.equals(successMessage)) {
				LOGGER.info("SNOW configuration success messege is displayed Successfully.");
				return true;
			} else if (snowPopUpMessage
					.equals(getTextLanguage(languageCode, "daas_ui", "settings.service_now.error_message_500"))) {
				LOGGER.error("Error SNOW configuration Error messege is displayed.");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifySNOWToastNotificationOnSNOWForm method. " + e.getMessage());
		}
		return false;
	}

	/**
	 * This method verify localization of error message displayed when chrome
	 * integration is configured with non super admin user
	 * @param languageCode
	 * @return
	 */
	public final boolean verifyChromeNonSuperAdminErrorMessage(String languageCode) {
		boolean flag = false;
		try {
			if (getTextOfPreferencesPage("emmIntegrationNotification").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "companies.details.chromebook.update.error_403"))) {
				flag = true;
			} else {
				LOGGER.error("Unable to verify chromebook integration configuration details");
				return flag;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyChromeNonSuperAdminErrorMessage" + ex.getMessage());
		}
		return flag;
	}

	/**
	 * This method is verifying given SNOW status is available at company / SNA level or not.
	 * @param languageCode -languageCode for the localization testing.
	 * @param url -SNOW configuration url.
	 * @return boolean - Return true/false value as per given SNOW status comparison.
	 */
	public boolean verifyExistingSNOWStatusAtCompanyOrSNALevel(String languageCode, String url) {
		try {
			waitForElementsOfPreferencesPage("snowStatus");
			if((getTextOfPreferencesPage("snowStatus").equals(getTextLanguage(languageCode, "daas_ui", "global.enabled")) && (getTextOfPreferencesPage("snowURLStatus").equals(url))))
				return true;
		}catch(Exception e) {
			LOGGER.error("Exception occured in verifySNOWStatusAtSNALevel method."+e.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * This method set attribute to block to be used in import
	 * 
	 * @param key - Key of the locator
	 */
	public final void setAttributeOfProductCatalog(String key) {
		setAttributeForImport(preferencesPagePropertiesPage.getProperty(key));
	}

	/**
	 * This method is check Company Preference- Inherit SNOW toggle validation.
	 * @param toggleStatus : Status will be "on" or "off".
	 * @param languageCode :  language code to check current language and check for the localization
	 * @return boolean : That value should be "true" or "false"
	 */
	public boolean verifySNOWToggleOnOffValidationComapnyLevel(String toggleStatus, String languageCode) {
		try {
			waitForElementsOfPreferencesPage("companyInheritToggleOfSNOW");
			if (toggleStatus.equals("on")) {
				if (getAttributesOfPreferencesPage("companyInheritToggleOfSNOW", "class").contains("toggle--on") && verifyElementIsNotPresent("snowEditIconAtCompanyLevel")) {
					return true;
				} else if (getAttributesOfPreferencesPage("companyInheritToggleOfSNOW", "class").contains("vn-toggle")) {
					clickOnElementsOfPreferencesPage("companyInheritToggleOfSNOW");
					if (getAttributesOfPreferencesPage("companyInheritToggleOfSNOW", "class").contains("toggle--on") && verifyElementIsNotPresent("snowEditIconAtCompanyLevel")) {
						return true;
					}
				}
			} else {
				if (getAttributesOfPreferencesPage("companyInheritToggleOfSNOW", "class").contains("toggle--on") && verifyElementIsNotPresent("snowEditIconAtCompanyLevel")) {
					return true;
				} else if (getAttributesOfPreferencesPage("companyInheritToggleOfSNOW", "class").equals("vn-toggle")) {
					clickOnElementsOfPreferencesPage("companyInheritToggleOfSNOW");
					if (getTextOfPreferencesPage("errorMessageOfInheritToggleOfSNOW").equals(getTextLanguage(languageCode, "daas_ui", "settings.service_now.error_message_423"))) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception ocuured in verifySNOWToggleOnOffValidationComapnyLevel method due to " + e.getMessage());
		}
		return false;
	}

	/**
	 * This method is check SNOW URL is present at Company Preference.
	 * @param languageCode :  language code to check current language and check for the localization
	 * @param exceptedURL : SNOW configured URL.
	 * @return boolean : That value should be "true" or "false"
	 */
	public boolean verifyURLIsVisibleAtCompanyLevel(String languageCode, String exceptedURL) {
		try {
			waitForElementsOfPreferencesPage("snowURLStatus");
			sleeper(1000);
			if (getTextOfPreferencesPage("snowURLStatus").equals(exceptedURL))
				return true;
			else {
				LOGGER.error("Error Something went wrong while validating SNOW url at company level..");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyURLIsVisibleAtCompanyLevel method.");
		}
		return false;
	}

	/**
	 * This method will configured already used SNOW url at Company>Preferences level and validate Error message for URL field.
	 * @param languageCode -languageCode for the localization testing
	 * @param url -SNOW configuration url.
	 * @param userName -SNOW username for SNOW login.
	 * @param password -SNOW password for SNOW login.
	 * @return boolean -It will return SNOW URL error message status(true/false) from SNOW Integration page.
	 */
	public final boolean alreadyConfiguredURLtenantLevelSNOWConfiguration(String languageCode, String url, String userName, String password) {
		try {
			if (getAttributesOfPreferencesPage("companyInheritToggleOfSNOW", "class").equals("vn-toggle")) {
				waitForElementsOfPreferencesPage("snowEditIconAtCompanyLevel");
				clickOnElementsOfPreferencesPage("snowEditIconAtCompanyLevel");
				sleeper(1000);
				waitForElementsOfPreferencesPage("snowToggleAtCompanyLevel");
				if (!(getAttributesOfPreferencesPage("snowToggleAtCompanyLevel", "class").contains("toggle--on"))) {
					clickOnElementsOfPreferencesPage("snowToggleAtCompanyLevel");
					LOGGER.info("SNOW toggle is ON at Global level");
				} else if (waitForElementsOfPreferencesPage("resetConfigurationAtTenantLevel")) {
					clickOnElementsOfPreferencesPage("resetConfigurationAtTenantLevel");
					LOGGER.info("SNOW configuration Reset Successfully.");
					sleeper(1000); // Here wait is needed
					if (verifySNOWToastNotificationOnSNOWForm(languageCode))
						LOGGER.info("SNOW settings successfully Reset at Comapny level.");
					else
						LOGGER.error("Error SNOW settings is not Reset at Company level as expected.");
					clickOnElementsOfPreferencesPage("snowToggleAtCompanyLevel");
					LOGGER.info("Now SNOW toggle is ON at Global level");
				} else
					LOGGER.error("Error Something went wrong with SNOW Enable/Disable Toggle.");
				// Test Case 240711: [DaaS][SNOW] Verify URL and Username fields are accept blank space before or after entering a URL and Username for MSP configuration.
				enterTextForPreferencesPage("urlSnowCompanyLevel", " " + url + " ");
				enterTextForPreferencesPage("adminSnowCompanyLevel", " " + userName + " ");
				enterTextForPreferencesPage("passwordSnowCompanyLevel", password);
				LOGGER.info("All the fields data are entered successfully.");
				verifyElementsOfPreferencesPage("saveButtonSnowCompanyLevel");
				clickOnElementsOfPreferencesPage("saveButtonSnowCompanyLevel");
				LOGGER.info("Cliked on SAVE button successfully.");
				verifyElementsOfPreferencesPage("snowURLErrorMessageStatus");
				if (getTextOfPreferencesPage("snowURLErrorMessageStatus").equals(getTextLanguage(languageCode, "daas_ui", "settings.service_now.error_message_409"))) {
					LOGGER.info("'The ServiceNow URL is in use by another company.' message is displayed successfully.");
					verifyElementsOfPreferencesPage("cancelIconOfSNOWForm");
					clickOnElementsOfPreferencesPage("cancelIconOfSNOWForm");
					LOGGER.info("SNOW form successfully closed.");
					sleeper(1000); // Need to use wait here.
					if (getTextOfPreferencesPage("snowStatusAtTenant").equals(getTextLanguage(languageCode, "daas_ui", "companies.details.section.not_configured")))
						LOGGER.info("On Preferences 'Not Configured' status dispalyed");
					return true;
				} else {
					LOGGER.error("Error in displaying 'The ServiceNow URL is in use by another company.' this message.");
					return false;
				}
			} else
				LOGGER.error("Erro Company Preference Inherit SNOW toggle is ON, So Not able to Configured SNOW at Prefernces.");
		} catch (Exception ex) {
			LOGGER.error("Exception occured in alreadyConfiguredURLtenantLevelSNOWConfiguration.");
			return false;
		}
		return false;
	}

	/**
	 * This method will configured SNOW at SNA level and validate multiple scenario of SNOW form like URL, UserName, ClientID, ClientSecret.
	 * @param languageCode- languageCode for the localization testing      
	 * @param url -SNOW configuration url.        
	 * @param userName -SNOW username for SNOW login.     
	 * @param password -SNOW password for SNOW login.       
	 * @return boolean -It will return SNOW configuration status(true/false) from SNOW Integration page.
	 */

	public final boolean SNALevelSNOWConfiguration(String languageCode, String url, String userName, String password) {
		try {
			waitForElementsOfPreferencesPage("snowEditIconAtCompanyLevel");
			clickOnElementsOfPreferencesPage("snowEditIconAtCompanyLevel");
			sleeper(1000);
			waitForElementsOfPreferencesPage("snowToggleAtCompanyLevel");
			if (!(getAttributesOfPreferencesPage("snowToggleAtCompanyLevel", "class").contains("toggle--on"))) {
				clickOnElementsOfPreferencesPage("snowToggleAtCompanyLevel");
				LOGGER.info("SNOW toggle is ON at Global level");
			} else if (waitForElementsOfPreferencesPage("resetConfigurationAtTenantLevel")) {
				clickOnElementsOfPreferencesPage("resetConfigurationAtTenantLevel");
				LOGGER.info("SNOW configuration Reset Successfully.");
				sleeper(1000); // Here wait is needed
				if (verifySNOWToastNotificationOnSNOWForm(languageCode))
					LOGGER.info("SNOW settings successfully Reset at Comapny level.");
				else
					LOGGER.error("Error SNOW settings is not Reset at Company level as expected.");
				clickOnElementsOfPreferencesPage("snowToggleAtCompanyLevel");
				LOGGER.info("Now SNOW toggle is ON at Global level");
			} else
				LOGGER.error("Error Something went wrong with SNOW Enable/Disable Toggle.");
			enterTextForPreferencesPage("urlSnowCompanyLevel", " " + url + " ");
			enterTextForPreferencesPage("adminSnowCompanyLevel", " " + userName + " ");
			enterTextForPreferencesPage("passwordSnowCompanyLevel", password);
			LOGGER.info("All the fields data are entered successfully.");
			verifyElementsOfPreferencesPage("saveButtonSnowCompanyLevel");
			clickOnElementsOfPreferencesPage("saveButtonSnowCompanyLevel");
			LOGGER.info("Cliked on SAVE button successfully.");
			if (verifySNOWToastNotificationOnSNOWForm(languageCode)) {
				if (verifyElementsOfPreferencesPage("clientIDLabelTenant") && verifyElementsOfPreferencesPage("clientSecretLabelTenant") && verifyElementsOfPreferencesPage("clientIDCopyIcon") && verifyElementsOfPreferencesPage("clientSecretCopyIcon") && verifyElementsOfPreferencesPage("clientIDValue") && verifyElementsOfPreferencesPage("clientSecretValue")) {
					LOGGER.info("ClientId and ClientSecret and respective Copy icon and values is displayed on SNOW form of Company Preferences.");
				} else
					LOGGER.error("Error ClientID or CLientSecret or respective Copy icons and values are not dispalyed at Company Preferences.");
			}
			verifyElementsOfPreferencesPage("cancelIconOfSNOWForm");
			clickOnElementsOfPreferencesPage("cancelIconOfSNOWForm");
			LOGGER.info("SNOW form successfully closed.");
			sleeper(1000); // Need to use wait here.
			if (getTextOfPreferencesPage("snowStatusAtTenant").equals(getTextLanguage(languageCode, "daas_ui", "global.enabled")) && getTextOfPreferencesPage("snowURLStatusAtTenant").equals(url) && verifyElementIsNotPresent("companyInheritToggleOfSNOW")) {
				return true;
			} else
				LOGGER.error("Error in method verifySNOWToastNotificationOnSNOWForm returns false.");
		} catch (Exception ex) {
			LOGGER.error("Exception occured in tenantLevelSNOWConfiguration.");
			return false;
		}
		return false;
	}

	/**
	 * This method is validate SNOW toggle is by default visible in disable mode.
	 * 
	 * @return boolean : Return true / false as per validation.
	 */
	public boolean verifySNOWInheritToggleIsOffByDefault() {
		try {
			scrollOnPreferencesPage("companyInheritToggleOfSNOW");
			verifyElementsOfPreferencesPage("companyInheritToggleOfSNOW");
			sleeper(2000);
			if (getAttributesOfPreferencesPage("companyInheritToggleOfSNOW", "class").contains("toggle"))
				return true;
			else
				return false;
		} catch (Exception e) {
			LOGGER.error("Exception occurs in verifySNOWInheritToggleIsOffByDefault method.");
		}
		return false;
	}

	/**
	 * This is a method to get text of a list of elements
	 * 
	 * @param key - Locator of the list of selected columns
	 * @return - arraylist of the text present on the list of elements
	 */
	public final ArrayList<String> getTextOfListOfPreferencesPage(String key) {
		try {
			return getTextOfList(preferencesPagePropertiesPage.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfListOfPreferencesPage " + e.getMessage()));
			return null;
		}
	}
	
	public final void switchToIframe1(String key) throws Exception {
		getDriver().switchTo().frame(this.getElement(preferencesPagePropertiesPage.getProperty(key)));
	}
	
	
	/**
	 * This method generates authorization code
	 * @return
	 */
	public final void verifyGooglePermission() {
		try {
			if (verifyElementsOfPreferencesPage("googleAdvancedLink")) {
				clickOnElementsOfPreferencesPage("googleAdvancedLink");
				waitForElementsOfPreferencesPage("googleHPDaasUnsafe");
				clickOnElementsOfPreferencesPage("googleHPDaasUnsafe");
			}
			else {
				LOGGER.info("Permission already present for account");
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyGooglePermission" + ex.getMessage());
		}
	}
	
	/** Remove EMM integration 
	 * @param  languageCode
	 * @emmType- Type of EMM
	 * @EMMDeleteBtn- Locator of emm type delete button
	 * @deleteDeviceCheckbox - To check/Uncheck device checkbox
	 * @return */
	public final boolean removeEmmIntegration(String languageCode, String emmType, String EMMDeleteBtn, Boolean deleteDeviceCheckbox) {
		boolean flag = false;
		try {
			goToPreferencesTab();

			if (verifyElementsOfPreferencesPage(EMMDeleteBtn)) {
				scrollOnPreferencesPage(EMMDeleteBtn);
				sleeper(4000);
				clickOnElementsOfPreferencesPage(EMMDeleteBtn);

				if (verifyElementsOfPreferencesPage("removeChromebookIntegrationLabel"))
				{
					LOGGER.info("Remove Chromebook integration pop-up opened");
				}
				if (emmType.equalsIgnoreCase(PreferenceVariables.CHROME)) {
					if (getTextOfPreferencesPage("removeChromebookIntegrationConfirmation").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "devicelist.emmtools.remove.confirmation").replace("{name}", getTextLanguage(languageCode, "daas_ui", "devicelist.emmtools.chrome_enterprise")))) {
						LOGGER.info("Remove Confirmation message for Google Chrome Enterprise  is found");
					} else {
						LOGGER.error("Remove Confirmation message for Google Chrome Enterprise is not found");
					}
				}
				else if (emmType.equalsIgnoreCase(PreferenceVariables.INTUNE)) {
					if (getTextOfPreferencesPage("removeChromebookIntegrationConfirmation").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "devicelist.emmtools.remove.confirmation").replace("{name}", getTextLanguage(languageCode, "daas_ui", "devicelist.emmtools.microsoft_intune")))) {
						LOGGER.info("Remove Confirmation message for Microsoft Intune is found");
					} else {
						LOGGER.error("Remove Confirmation message for Microsoft Intune is not found");
					}

				} else {
					if (getTextOfPreferencesPage("removeChromebookIntegrationConfirmation").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "devicelist.emmtools.remove.confirmation").replace("{name}", getTextLanguage(languageCode, "daas_ui", "sidemenu.airwatch")))) {
						LOGGER.info("Remove Confirmation message for Airwatch is found");
					} else {
						LOGGER.error("Remove Confirmation message for Airwatch is not found");
					}
				}
				//Unenroll device message string is out for translation
				/*if (getTextOfPreferencesPage("removeChromebookDevicesConfirmation").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "devicelist.emmtools.unenroll_remove_device.title"))) {
					LOGGER.info("Remove devices Confirmation message is found");
				} else {
					LOGGER.error("Remove devices Confirmation message is not found");
				}*/
				if (!deleteDeviceCheckbox == true) {
					LOGGER.info("Remove only google chrome configuration");
					clickOnElementsOfPreferencesPage("removeChromebookButton");
					sleeper(2000);
					verifyRemoveChromeConfigurationSuccessMessageStrings(languageCode,emmType);
					flag = true;
				}
			} else {
				LOGGER.info("EMM configuration is already removed.");
				flag = true;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in removeEmmIntegration" + ex.getMessage());
		}
		return flag;
	}
	
	/** This method verify chromebook integration configured success message strings
	 * @param languageCode */
	public final void verifyRemoveChromeConfigurationSuccessMessageStrings(String languageCode,String emmType) {
		try {
			verifyElementsOfPreferencesPage("emmIntegrationNotification");
		
			if (emmType.equalsIgnoreCase(PreferenceVariables.CHROME)) {
				if (getTextOfPreferencesPage("emmIntegrationNotification").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "companies.details.remove.success").replace("{name}", getTextLanguage(languageCode, "daas_ui", "devicelist.emmtools.chrome_enterprise")))) {
					pressKey(Keys.ESCAPE);
					waitForElementsOfPreferencesPage("chromeIntegrationEnabledDisabledString");
					if (matchTextOfPreferencesPage("chromeIntegrationEnabledDisabledString", getTextLanguage(languageCode, "daas_ui", "companies.details.section.not_configured"))) {
						LOGGER.info("Validated String :  " + getTextOfPreferencesPage("chromeIntegrationEnabledDisabledString"));

					} else {
						LOGGER.error("Unable to match remove chromebook integration configuration details");
					}
					LOGGER.info("Chrome Integration Removed successfully");
						
				} else {
					LOGGER.error("Chrome Integration not Removed successfully");
				}
			}
			else if (emmType.equalsIgnoreCase(PreferenceVariables.INTUNE)) {
				if (getTextOfPreferencesPage("emmIntegrationNotification").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "companies.details.remove.success").replace("{name}", getTextLanguage(languageCode, "daas_ui", "devicelist.emmtools.microsoft_intune")))) {
					pressKey(Keys.ESCAPE);
					waitForElementsOfPreferencesPage("intuneIntegrationEnabledDisabledString");
					if (matchTextOfPreferencesPage("intuneIntegrationEnabledDisabledString", getTextLanguage(languageCode, "daas_ui", "companies.details.section.not_configured"))) {
						LOGGER.info("Validated String :  " + getTextOfPreferencesPage("intuneIntegrationEnabledDisabledString"));

					} else {
						LOGGER.error("Unable to match remove chromebook integration configuration details");
					}
					
					LOGGER.info("Intune Integration Removed successfully");
					
				} else {
					LOGGER.error("Intune Integration not Removed successfully");
				}

			} else {
				if (getTextOfPreferencesPage("emmIntegrationNotification").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "companies.details.remove.success").replace("{name}", getTextLanguage(languageCode, "daas_ui", "sidemenu.airwatch")))) {
					pressKey(Keys.ESCAPE);
					waitForElementsOfPreferencesPage("airwatchIntegrationEnabledDisabledString");
					if (matchTextOfPreferencesPage("airwatchIntegrationEnabledDisabledString", getTextLanguage(languageCode, "daas_ui", "companies.details.section.not_configured"))) {
						LOGGER.info("Validated String :  " + getTextOfPreferencesPage("airwatchIntegrationEnabledDisabledString"));
					} else {
						LOGGER.error("Unable to match remove airwatch integration configuration details");
					}
					LOGGER.info("Airwatch Integration Removed successfully");
				} else {
					LOGGER.error("Airwatch Integration not Removed successfully");
				}
			}
			
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyRemoveChromeConfigurationSuccessMessageStrings" + ex.getMessage());
		}
	}

	/** This method verify EMM(Chrome,Intune,Airwatch) integration removal from device list page and device detail page
	 * @param enrolledSerialNoList - List of enrolled devices with serial no
	 * @param CompanyName Company name to remove EMM Configuration */
	
	public boolean verifyEMMDeviceClientApplicationRemovalOnDevicesTab(String languageCode,String CompanyName, List<String> enrolledSerialNoList) {
		boolean flag = false;
		try {
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

			for (String enrolledIterator : enrolledSerialNoList) {

				if (companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters")) {
					sleeper(3000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
					companiesPage.clickOnElementsOfCompaniesPage("removeAllSearchFilters");
					sleeper(3000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
					companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
					LOGGER.info("All the filters of company list page has been removed successfully.");
					sleeper(4000);
				}
				deviceListPage.clickOnElementsOfDeviceListPage("serialNumberBox");
				deviceListPage.enterTextForDeviceListPage("serialNumberBox", enrolledIterator);
				sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)

				if (deviceListPage.getTextOfDeviceListPage("enrolledColumn").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "global.application_name"))) {
					LOGGER.info("EMM Configuration for " + enrolledIterator + " has been removed successfully from device list page.");
				} else {
					LOGGER.error("EMM Configuration for " + enrolledIterator + " has not been removed successfully from device list page.");
				}
				deviceListPage.clickOnElementsOfDeviceListPage("deviceFirstRow");
				waitForPageLoaded();

				if ((!deviceListPage.verifyElementsOfDeviceListPage("deviceEMMTool") && (!deviceListPage.verifyElementsOfDeviceListPage("unerollClientIntegration")))) {
					LOGGER.info("EMM Tool button and " + enrolledIterator + " Integration is removed from device detail page.");
					flag = true;
				} else {
					LOGGER.error("EMM Tool button and  " + enrolledIterator + " Integration is not removed from device detail page.");
					return flag;
				}
				deviceListPage.clickOnElementsOfDeviceListPage("devicesTab");
				waitForPageLoaded();
			}
			LOGGER.info("EMM Integration is removed successfully from device detail page.");

		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyChromeConfigurationRemovalInDevicesTab" + ex.getMessage());
		}
		return flag;
	}

	/** This method verify EMM(Chrome,Intune,Airwatch) integration removal with Checkbox
	 * @param languageCode
	 * @throws InterruptedException 
	 * @CompanyName Company name to remove EMM Configuration */
	public boolean verifyEMMConfigurationRemovalWithCheckbox(String languageCode, String CompanyName,String emmType) throws InterruptedException {
		boolean flag = false;
	//	List<String> enrolledListItems = new ArrayList<String>();
		try {
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

			if (companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters")) {
				sleeper(3000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				companiesPage.clickOnElementsOfCompaniesPage("removeAllSearchFilters");
				sleeper(3000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
				LOGGER.info("All the filters of company list page has been removed successfully.");
				sleeper(4000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			}
			deviceListPage.clickOnElementsOfDeviceListPage("companyBox");
			deviceListPage.waitForElementsOfDeviceListPage("companySearch");
			deviceListPage.enterTextForDeviceListPage("companySearch", CompanyName);
			sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			deviceListPage.clickOnElementsOfDeviceListPage("companyResult");
			pressKey(Keys.ESCAPE);
			sleeper(8000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)

			// Select Enrolled Type
			deviceListPage.waitForElementsOfDeviceListPage("enrolledBox");
			sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			deviceListPage.clickOnElementsOfDeviceListPage("enrolledBox");
				if (emmType.equals((PreferenceVariables.CHROME))) {
					LOGGER.info("Google Chrome Enterprise is present");
					deviceListPage.clickOnElementsOfDeviceListPage("googleChromeDevice");
					pressKey(Keys.ESCAPE);
					sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				} else if (emmType.equals((PreferenceVariables.INTUNE))) {
					LOGGER.info("Microsoft Intune is present");
					deviceListPage.clickOnElementsOfDeviceListPage("microsoftIntuneDevice");
					pressKey(Keys.ESCAPE);
					sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				} else if (emmType.equals((PreferenceVariables.AIRWATCH))) {
					LOGGER.info("VMware Workspace ONE is present");
					deviceListPage.clickOnElementsOfDeviceListPage("airwatchDevice");
					pressKey(Keys.ESCAPE);
					sleeper(4000);	// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				}
		
			deviceListPage.waitForElementsOfDeviceListPage("NoDeviceData");
			waitForPageLoaded();

			if (deviceListPage.getTextOfDeviceListPage("NoDeviceData").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "list.no_items"))) {

				LOGGER.info("Device enrollment is removed from device when checkbox is selected while removing integration.");
				flag = true;
			} else {
				LOGGER.error("Device enrollment is not removed from device when checkbox is selected while removing integration.");
				return flag;
			}

			LOGGER.info("Device enrolled is removed successfully from devices tab.");

		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyChromeConfigurationRemovalWithCheckbox" + ex.getMessage());
		}
		sleeper(2000);
		return flag;
	}

	/** This method get serial no of devices which are enrolled by EMM
	 * @param emmType -type of EMM (Chrome,Intune,Airwatch)
	 * @CompanyName Company name to remove EMM Configuration */
	public List<String> getEnrolledDevicesSerialNo(String CompanyName, String emmType) {

		List<String> enrolledSerialNoItems = new ArrayList<String>();
		try {
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			//List<String> enrolledListItems = new ArrayList<String>();

			if (companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters")) {
				sleeper(3000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				companiesPage.clickOnElementsOfCompaniesPage("removeAllSearchFilters");
				sleeper(3000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
				LOGGER.info("All the filters of company list page has been removed successfully.");
				sleeper(4000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			}
			deviceListPage.clickOnElementsOfDeviceListPage("companyBox");
			deviceListPage.enterTextForDeviceListPage("companySearch", CompanyName);
			sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			deviceListPage.clickOnElementsOfDeviceListPage("companyResult");
			pressKey(Keys.ESCAPE);
			sleeper(5000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			// Select Enrolled Type
			deviceListPage.waitForElementsOfDeviceListPage("enrolledBox");
			sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			deviceListPage.clickOnElementsOfDeviceListPage("enrolledBox");
			deviceListPage.getTextOfListOfDeviceListPage("enrolledListElements");
			if (emmType.equals((PreferenceVariables.CHROME))) {
				LOGGER.info("Google Chrome Enterprise is present");
				deviceListPage.clickOnElementsOfDeviceListPage("googleChromeDevice");
				deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("SpinnerList");
				pressKey(Keys.ESCAPE);
				sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			} else if (emmType.equals((PreferenceVariables.INTUNE))) {
				LOGGER.info("Microsoft Intune is present");
				deviceListPage.clickOnElementsOfDeviceListPage("microsoftIntuneDevice");
				pressKey(Keys.ESCAPE);
				deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("SpinnerList");
				sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)

			} else if (emmType.equals((PreferenceVariables.AIRWATCH))) {
				LOGGER.info("VMware Workspace ONE is present");
				deviceListPage.clickOnElementsOfDeviceListPage("airwatchDevice");
				pressKey(Keys.ESCAPE);
				deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("SpinnerList");
				sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			}
			enrolledSerialNoItems = deviceListPage.getTextOfListOfDeviceListPage("serialNoListElements");
		} catch (Exception ex) {
			LOGGER.error("Exception occured in getEnrolledDevicesSerialNo" + ex.getMessage());
		}
		return enrolledSerialNoItems;
	}

	/** This method configures Airwatch for existing EMM on preferences tab
	 * domainName - domainName to configure
	 * @return */
	public final boolean verifyExistingIntuneConfiguration(String domainName) {
		boolean flag = false;
		try {
			enterTextForPreferencesPage("domainNameIntuneText", domainName);
			if (verifyElementIsEnableOfPreferencesPage("intuneSaveButton")) {
				clickOnElementsOfPreferencesPage("intuneSaveButton");
				flag=true;
			}
			else {
				LOGGER.info("Element Not Present");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyExistingIntuneConfiguration" + e.getMessage());
		}
		return flag;
	}
	
	/** Remove EMM User
	 * @param languageCode 
	 * CompanyName- EMM Company Name
	 * @emmUserName- EMM USer Name
	 * @return */
	public final boolean removeEmmUser(String LanguageCode, String CompanyName, String emmUserName,String environment) {
		boolean flag = false;
		String currentUrl,userID;;
		try {
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			sleeper(3000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			clickOnElementsOfPreferencesPage("userTab");
			resetTableConfiguration();
			userPage.clickOnElementsOfUserPage("userCompanyBox");
			userPage.enterTextForUserPage("companySearchBox", CompanyName);
			userPage.waitUntilElementIsInvisibleOfUserPage("SpinnerList");
			sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			userPage.clickOnElementsOfUserPage("userCompanyResult");
			pressKey(Keys.ESCAPE);
			sleeper(5000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			userPage.waitForElementsOfUserPage("userNameSearchBox");
			userPage.clickOnElementsOfUserPage("userNameSearchBox");
			userPage.enterTextForUserPage("userNameSearchBox", emmUserName);
			userPage.waitForElementsOfUserPageForinvisibile("SpinnerList");
			sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)

			if (userPage.verifyElementsOfUserPage("firstRowUser")) {
				waitForPageLoaded();
				userPage.waitForElementsOfUserPage("selectCheckbox");
				userPage.clickOnElementsOfUserPage("firstRowUser");
				currentUrl = userPage.getUrlOfCurrentPage();
				userID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
				removeEMMUserWithAPI(environment,userID,environment);
				flag = true;
			} else {
				LOGGER.info("EMM User is already removed.");
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeEmmUser" + e.getMessage());
		}
		return flag;
	}
	
	
	/** This method will remove EMM integration with devices using API
	 * @param environmentURL - url of the environment
	 * @param emmType - Chrome/Airwatch/Intune 
	 * @prama EMMDeleteBtn - Locator of emm type delete button
	 * @parma emmCompanyId - ID of EMM company
	 * @return Boolean value return either true or false
	 * @throws Exception */
	public final boolean removeEMMIntegrationUsingAPI(String environment, String EMMType, String EMMDeleteBtn, String emmCompanyId) {
		boolean flag = false;
		try {
			goToPreferencesTab();
			if (verifyElementsOfPreferencesPage(EMMDeleteBtn)) {
				String body = "{\"tenant_Ids\":[\"" + emmCompanyId + "\"],\"emm_provider\":\"" + EMMType + "\",\"is_cleanup_from_daas\":true}";
				int code = getStatusCode(getEnvironmentSpecificData(System.getProperty("environment"), "ROLE_ID_URL") + ConstantURL.DELETE_EMM, body, "DELETE", environment);
				if (code != CommonVariables.CODESUCCESS && code != CommonVariables.CODEDELETE) {
					flag = false;
					LOGGER.error("Delete API got failed while removing EMM Integration.");
				} else
					flag = true;
			} else {
				LOGGER.info("EMM configuration is already removed.");
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeEMMIntegrationUsingAPI: " + e.getMessage());
			return false;
		}
	}
	
	/** This method will remove emm user
	 * @param environmentURL - url of the environment
	 * @param userID - Id of the user to remove
	 * @return Boolean value return either true or false
	 * @throws Exception */
	public final boolean removeEMMUserWithAPI(String environment, String userID, String environmentURL) {
		try {
			boolean flag = false;
			int code = getStatusCode(environment + ConstantURL.DELETE_API_USER, "[{\"id\":\"" + userID + "\"}]", "POST", environment);
			if (code != CommonVariables.CODESUCCESS && code != CommonVariables.CODEDELETE) {
				flag = false;
				LOGGER.error("Delete API got failed while removing emm user.");
			} else
				flag = true;
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeEMMUserWithAPI: " + e.getMessage());
			return false;
		}
	}
	
	public final boolean enableTenantConfiguration() throws Exception {
		boolean successfulRun = false;
		try {
			LogPage logsPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
			waitForElementsOfPreferencesPage("editPencilIcon");
			clickOnElementsOfPreferencesPage("editPencilIcon");
			sleeper(2000);
			clickOnElementsOfPreferencesPage("flipEnableToggle");
			sleeper(2000);
			waitForElementsOfPreferencesPage("numberOfApprovals");
			enterTextForPreferencesPage("numberOfApprovals", PreferenceVariables.NUMBER_OF_APPROVERS);
			verifyElementIsClickableOfPreferencesPage("buttonCancel");
			clickOnElementsOfPreferencesPage("buttonSave");
			LOGGER.info("Enabled Lock and erase setting");
			logsPage.clickOnElementsOfLogPage("logTab");
				sleeper(2000);
				refreshPage();
				logsPage.mousehoverOnLogsPage("checkBox");
				logsPage.clickByJavaScriptOnLogPage("checkBox");
				sleeper(3000);
				Assert.assertTrue(logsPage.getTextOfLogPage("lockeraseenableStatus").contains("Protect and Trace Enabled"),
						"Enable log is not present in logs tab");
			successfulRun = true;
		}
		catch(Exception e){
			successfulRun=false;
			LOGGER.info("Error in enabling tenant configuration method");
			}
		
		return successfulRun;
	}
	public final boolean disableTenantConfiguration() throws Exception {
		boolean successfulRun = false;
		try {
			LogPage logsPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

			waitForElementsOfPreferencesPage("editPencilIcon");
			clickOnElementsOfPreferencesPage("editPencilIcon");
			clickOnElementsOfPreferencesPage("flipEnableToggle");
			verifyElementIsClickableOfPreferencesPage("buttonCancel");
			clickOnElementsOfPreferencesPage("buttonSave");
			LOGGER.info("Disabled lock and erase setting tile in company preference page");

			logsPage.clickOnElementsOfLogPage("logTab");
				sleeper(2000);
				refreshPage();
				logsPage.mousehoverOnLogsPage("checkBox");
				logsPage.clickByJavaScriptOnLogPage("checkBox");
				Assert.assertTrue(logsPage.getTextOfLogPage("lockeraseenableStatus").contains("Protect and Trace Disabled"),
						"Disable log is not present in logs tab");

		} catch (Exception e) {
			successfulRun = false;
			LOGGER.info("Error in enabling tenant configuration method");
		}
		return successfulRun;
	}
	public final void clearTextOfPreferencePage(String key) throws Exception {
		clearText(preferencesPagePropertiesPage.getProperty(key));
	}
	
	/**
	 * This method is used for file import for veneer version 3
	 * @param fileName this is the name of file which was imported
	 * @throws Exception 
	 */
	public void fileImportInV3(String fileName) throws Exception {
		sleeper(3000);
		WebElement addFile = getElementOfPreferencePage("airwatchuploadBrowseInput");
	    addFile.sendKeys(fileName);
	    sleeper(3000);
	}

	public void clickOn3rdPartySoftwareTileHeaderOnPreferencesPage(String key) throws Exception {
		click(preferencesPagePropertiesPage.getProperty(key));
		
	}

	
	
	/**
	 * Method to Enable the Microsoft Teams Toggle
	 * @throws Exception
	 */
	public final void verifyMSTeamsToggleIntegartion() throws Exception {
		clickOnElementsOfPreferencesPage("companyPreferncesSummaryCardTitle");
		waitForElementsOfPreferencesPage("3rdPartySoftwareTileHeader");
		moveToElementOnPreferencesPage("3rdPartySoftwareTileHeader");
		waitForElementsOfPreferencesPage("microsoftTeamsToggleTitle");
		verifyElementsOfPreferencesPage("microsoftTeamsToggleTitle");
		verifyElementsOfPreferencesPage("microsoftTeamsToggleBtn");		
	}
	
	

	/**
	 * Method to Enable the Zoom Integration Toggle
	 * @throws Exception
	 */
	public final void verifyZoomToggleIntegartion() throws Exception {
		clickOnElementsOfPreferencesPage("companyPreferncesSummaryCardTitle");
		waitForElementsOfPreferencesPage("3rdPartySoftwareTileHeader");
		moveToElementOnPreferencesPage("3rdPartySoftwareTileHeader");
		waitForElementsOfPreferencesPage("zoomIntegrationToggleTitle");
		verifyElementsOfPreferencesPage("zoomIntegrationToggleTitle");
		verifyElementsOfPreferencesPage("zoomIntegrationToggleBtn");		
	}


}