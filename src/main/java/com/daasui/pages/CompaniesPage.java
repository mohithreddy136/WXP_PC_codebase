package com.daasui.pages;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class CompaniesPage extends CommonMethod {
	private Properties selectedLanguageProperties;
	private ObjectReader companiesPagePropertiesReader = new ObjectReader();
	private Properties companiesPageProperties;
	private static Logger LOGGER = LogManager.getLogger(CompaniesPage.class);

	private CompaniesPage instance;
	public static String uiVersion = System.getProperty("uiVersion");

	public CompaniesPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (CompaniesPage.class) {
				if (instance == null) {
					instance = new CompaniesPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public CompaniesPage(WebDriver driver) throws IOException {
		companiesPageProperties = companiesPagePropertiesReader.getObjectRepository("CompaniesPageV3");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = companiesPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	public final boolean verifyElementsOfCompaniesPage(String key) throws Exception {
		return verifyElementIsPresent(companiesPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfCompaniesPage(String key) throws Exception {
		return verifyElementIsVisible(companiesPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfCompaniesPageDynamic(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(companiesPageProperties.getProperty(key), waitTime);
	}

	public final boolean matchTextOfCompaniesPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(companiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfCompaniesPage(String key) throws Exception {
		return verifyElementIsEnable(companiesPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfCompaniesPage(String key) throws Exception {
		return verifyElementIsSelected(companiesPageProperties.getProperty(key));
	}

	public final String getTextOfCompaniesPage(String key) throws Exception {
		return getTextBy(companiesPageProperties.getProperty(key));
	}

	public final ArrayList<String> getTextOfListOfCompaniesPage(String key) {
		try {
			return getTextOfList(companiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfListOfCompaniesPage " + e.getMessage()));
			return null;
		}
	}

	public final String getAttributesOfCompaniesPage(String key, String value) throws Exception {
		return getAttribute(companiesPageProperties.getProperty(key), value);
	}

	public final int getCountOfRowsOnCompaniesPageForRoot(String key) throws Exception {
		return getCountOfRows(companiesPageProperties.getProperty(key));
	}

	public final void clickOnElementsOfCompaniesPage(String key) throws Exception {
		click(companiesPageProperties.getProperty(key));
	}
	
	/**
	 * This method will click webElement on companies page
	 * 
	 * @param key - WebElement
	 * @throws Exception
	 */
	public final void clickOnWebElementsOfCompaniesPage(WebElement key) throws Exception {
		clickWebelement(key);
	}

	public final void scrollTillViewCompaniesPage(String locator) throws Exception {
		scrollTillView(companiesPageProperties.getProperty(locator));
	}

	public final void clickByJavaScriptOnCompaniesPage(String key) throws Exception {
		clickByJavaScript(companiesPageProperties.getProperty(key));
	}

	public final void enterTextForCompaniesPage(String key, String Text) throws Exception {
		enterText(companiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfCompaniesPage(String key) throws Exception {
		return verifyElementIsClickable(companiesPageProperties.getProperty(key));
	}

	public final WebElement getElementOfCompaniesPage(String key) throws Exception {
		return getElement(companiesPageProperties.getProperty(key));
	}

	public final boolean waitUntillElementIsClickableOfCompaniesPage(String key) throws Exception {
		return verifyElementIsClickable(companiesPageProperties.getProperty(key));
	}

	public final void clearTextForCompaniesPage(String key) throws Exception {
		clearText(companiesPageProperties.getProperty(key));
	}

	public final void scrollOnCompaniesPage(String key) throws Exception {
		scrollTillView(companiesPageProperties.getProperty(key));
	}

	public final boolean waitForPresenceOfElementsOfCompaniesPage(String key) throws Exception {
		return waitUntillElementIsPresent(companiesPageProperties.getProperty(key));
	}

	// Method to go on the Preferences tab
	public final void gotoPreferencesTab() throws Exception {
		clickOnElementsOfCompaniesPage("preferencesTab");
	}

	// Method checks toggle for submit a help from Preferences tab and if it is
	// disable , enable it
	// Modified this method as earlier method didn't support localization
	public final void checkSubmitIncidentToggle() throws Exception {
		waitForElementsOfCompaniesPage("submitHelpToggleStatus");
		boolean isToggleOn = verifyElementsOfCompaniesPage("submitHelpToggleStatus");
		if (!isToggleOn) {
			clickOnElementsOfCompaniesPage("submitHelpToggle");
		}
	}

	public final void moveToElementOnCompaniesPage(String key) throws Exception {
		moveToElements(companiesPageProperties.getProperty(key));
	}

	public final boolean selectElementFromDropDown(String dropdownId, String key, String text) throws Exception {
		click(companiesPageProperties.getProperty(dropdownId));
		return selectFromDropdown(companiesPageProperties.getProperty(dropdownId),
				companiesPageProperties.getProperty(key), text);
	}

	// This method will enter text to be searched in dropdown
	public final boolean selectElementFromDropDownByText(String dropdownId, String InputText, String key, String text)
			throws Exception {
		click(companiesPageProperties.getProperty(dropdownId));
		enterText(companiesPageProperties.getProperty(InputText), text);
		sleeper(2000);
		return selectFromDropdown(companiesPageProperties.getProperty(dropdownId),
				companiesPageProperties.getProperty(key), text);
	}

	public final void mouseHoverOnCompanyPage(String key) throws Exception {
		mouseHover(companiesPageProperties.getProperty(key));
	}

	public final void mouseHoverOnCompanyPage(WebElement key) throws Exception {
		mouseHover(key);
	}

	public final boolean verifyElementPresentIndropdownOfCompaniesPage(String key, String text) throws Exception {
		click(companiesPageProperties.getProperty(key));
		return verifyTextPresentInDropdown(companiesPageProperties.getProperty(key), text);
	}

	public final void selectIndropdownOfCompaniesPage(String key, String text) throws Exception {
		click(companiesPageProperties.getProperty(key));
		SelectTextPresentInDropdown(companiesPageProperties.getProperty(key), text);
	}
	
	public final ArrayList<String> getTextOfColumns(String key) throws Exception {
		return getTextOfList(companiesPageProperties.getProperty(key));
	}

	/*
	 * This method will: 1.Generate the company details 2.Return the information
	 */
	public final HashMap<String, String> getCompanyDetails() {
		HashMap<String, String> companyInfo = new HashMap<String, String>();
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		companyInfo.put("name", "seleniumCompany" + strDate);
		companyInfo.put("address", "address");
		companyInfo.put("city", "city");
		companyInfo.put("state", "state");
		companyInfo.put("zip", Long.toString(generateRandomNumber(100000, 10000000)));
		companyInfo.put("fName", "fName" + strDate);
		companyInfo.put("lName", "lName" + strDate);
		companyInfo.put("email", "hptpm.multipass+seleniumtest" + String.valueOf(generateRandomNumber(100, 10000000))
				+ "@mailinator.com");
		companyInfo.put("phone", Long.toString(generatePhoneNumber()));
		return companyInfo;
	}

	/*
	 * This method will: 1. Add details on add company page. 2. Click on add button.
	 */
	public final boolean addNewCompany(HashMap<String, String> companyInfo, String MSP, String reseller,
			String country) {
		try {
			this.enterTextForCompaniesPage("companyName", companyInfo.get("name").toString());
			this.clickOnElementsOfCompaniesPage("noOfEmployeesDD");
			this.clickOnElementsOfCompaniesPage("noOfEmployees");
			this.enterTextForCompaniesPage("address", companyInfo.get("address").toString());
			this.enterTextForCompaniesPage("city", companyInfo.get("city").toString());
			this.enterTextForCompaniesPage("state", companyInfo.get("state").toString());
			this.enterTextForCompaniesPage("zip", companyInfo.get("zip").toString());
			if (!this.selectElementFromDropDown("country", "countryDD", country))
				throw new Exception("country:" + country + "is not present");
			if (!this.selectElementFromDropDownByText("mspParent", "mspTextInput", "mspParentDD", MSP))
				throw new Exception("MSP:" + MSP + "is not present");
			if (reseller.length() != 0)
				if (!this.selectElementFromDropDown("resellar", "resellarDD", reseller))
					throw new Exception("MSP:" + reseller + "is not present");
			this.enterTextForCompaniesPage("firstName", companyInfo.get("fName").toString());
			this.enterTextForCompaniesPage("lastName", companyInfo.get("lName").toString());
			this.enterTextForCompaniesPage("email", companyInfo.get("email").toString());
			this.enterTextForCompaniesPage("phone", companyInfo.get("phone").toString());
			this.moveToElementOnCompaniesPage("cancelButton");
			this.clickOnElementsOfCompaniesPage("addButton");
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception : " + e);
			return false;
		}
	}

	// This method enable SNOW configuration using Inherit global service setting
	// toggle
	public final void enableInheritGlobalServiceNowSettings() throws Exception {
		gotoPreferencesTab();
		WebElement element = getElementOfCompaniesPage("inheritGlobalSnowToggle");
		Boolean toggleValue = Boolean.parseBoolean(element.getAttribute("aria-checked"));
		if (!toggleValue)
			clickOnElementsOfCompaniesPage("inheritGlobalSnowToggle");
	}

	// This method disable SNOW configuration using Inherit global service setting
	// toggle
	public final void disableInheritGlobalServiceNowSettings() throws Exception {
		gotoPreferencesTab();
		WebElement element = getElementOfCompaniesPage("inheritGlobalSnowToggle");
		Boolean toggleValue = Boolean.parseBoolean(element.getAttribute("aria-checked"));
		if (toggleValue)
			clickOnElementsOfCompaniesPage("inheritGlobalSnowToggle");
	}

	/**
	 * This method will Validate count of companies on companies page
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean matchCompaniesCountOnForRoot() throws Exception {
		try {
			boolean firstPage = true;
			do {
				if (!firstPage) {
					clickOnElementsOfCompaniesPage("nextButton");
				}
				sleeper(2000);
				String bottomText = getTextOfCompaniesPage("rootBottomText");
				String text[] = bottomText.split(" ");
				int expectedCount = Integer.parseInt(text[2]) - Integer.parseInt(text[0]) + 1;
				int actualCount = getCountOfRowsOnCompaniesPageForRoot("companiesList");
				firstPage = false;
				if (expectedCount == actualCount)
					continue;
				else
					return false;
			} while (verifyElementIsEnableOfCompaniesPage("nextButton"));
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception caught during matchCompaniesCountOnForRoot:" + e.getMessage());
			return false;
		}

	}

	/**
	 * This method set SNOW type(Single-tenant or Multi-tenant) from Root Admin with
	 * SNOW toggle ON..
	 * 
	 * @param configurationValue- Single-tenant or Multi-tenantselected by Root
	 *                            admin
	 * 
	 */

	public final void setConfigurationDetailsWithToggleON(String configurationValue) {
		try {
			waitForElementsOfCompaniesPage("rootSNOWEditIcon");
			clickOnElementsOfCompaniesPage("rootSNOWEditIcon");
			waitForElementsOfCompaniesPage("rootSelectSNOWOption");
			clickOnElementsOfCompaniesPage("rootSelectSNOWOption");
			clickOnElementsOfCompaniesPage(configurationValue);
			clickOnElementsOfCompaniesPage("rootSaveSNOWStatus");
		} catch (Exception e) {
			LOGGER.error("Exception caught during setConfigurationDetailsWithToggleON:" + e.getMessage());
		}
	}

	/**
	 * This method set SNOW type(Single-tenant or Multi-tenant) from Root Admin
	 * without toggle change.
	 * 
	 * @param configurationValue- Single-tenant or Multi-tenantselected by Root
	 *                            admin
	 * 
	 */

	public final void setConfigurationDetailsWithoutToggleOnOff(String configurationValue) {
		try {
			waitForElementsOfCompaniesPage("rootSNOWEditIcon");
			clickOnElementsOfCompaniesPage("rootSNOWEditIcon");
			waitForElementsOfCompaniesPage("rootSNOWEditIcon");
			boolean toggleON= verifyElementIsEnableOfCompaniesPage("serviceNowToggleOn");
			if(toggleON==true) {
				
				clickOnElementsOfCompaniesPage("rootSelectSNOWOption");
				clickOnElementsOfCompaniesPage(configurationValue);
				clickOnElementsOfCompaniesPage("rootSaveSNOWStatus");
			}
			else
			{	
			clickOnElementsOfCompaniesPage("rootSNOWToggleOnOff");
			clickOnElementsOfCompaniesPage("rootSelectSNOWOption");
			clickOnElementsOfCompaniesPage(configurationValue);
			clickOnElementsOfCompaniesPage("rootSaveSNOWStatus");
			}
		} catch (Exception e) {
			LOGGER.error("Exception caught during setConfigurationDetailsWithoutToggleOnOff:" + e.getMessage());
		}
	}

	/**
	 * This method Reset configuration of SNOW at Company level and SNA level.
	 *
	 * @param languageCode - languageCode for the localization testing
	 * @return String - Current SNOW status at tenant level
	 * @throws Exception
	 * 
	 */

	public final String resetConfigurationAtTenantLevel(String languageCode) throws Exception {
		try {
			String snowStaus = getTextOfCompaniesPage("snowStatusAtCompanyLevel");
			waitForElementsOfCompaniesPage("snowEditIconAtCompanyLevel");
			clickOnElementsOfCompaniesPage("snowEditIconAtCompanyLevel");
			if (!(snowStaus.equals(getTextLanguage(languageCode, "daas_ui", "global.enabled")))) {
				waitForElementsOfCompaniesPage("snowToggleOnOffAtCompany");
				clickOnElementsOfCompaniesPage("snowToggleOnOffAtCompany");
			}
			waitForElementsOfCompaniesPage("resetConfigurationAtTenantLevel");
			clickOnElementsOfCompaniesPage("resetConfigurationAtTenantLevel");
			verifyElementsOfCompaniesPage("snowMessagePopupAtTenantLevel");
			String successMessage = getTextOfCompaniesPage("snowMessagePopupAtTenantLevel");
			if (successMessage.equals(getTextLanguage(languageCode, "daas_ui", "companies.details.update.success"))) {
				LOGGER.info("User is able to reset SNOW configuration at Company level.");
			}
			verifyElementsOfCompaniesPage("cancelButtonOfSNOWForm");
			clickOnElementsOfCompaniesPage("cancelButtonOfSNOWForm");
			sleeper(1000); // Need to add wait here
			return getTextOfCompaniesPage("snowStatusAtCompanyLevel");
		} catch (Exception ex) {
			LOGGER.error("Exception caught during resetConfigurationAtTenantLevel:" + ex.getMessage());
			return null;
		}
	}

	/**
	 * This method try to ON Inherit toggle ON when SNOW disabled or single Tenant
	 * selected from Root admin.
	 *
	 * @param languageCode - languageCode for the localization testing
	 * @return boolean - if Inherit toggle error message displayed return true.
	 * 
	 */

	public final boolean tryToEnableInheritToggle(String languageCode) {
		try {
			waitForElementsOfCompaniesPage("inheritGlobalSnowToggle");
			clickOnElementsOfCompaniesPage("inheritGlobalSnowToggle");
			waitForElementsOfCompaniesPage("errorMessagePopup");
			String errorMessage = getTextOfCompaniesPage("errorMessagePopup");
			if (errorMessage.contains(
					getTextLanguage(languageCode, "lhserver", "settings.preferences.service_now.error_423"))) {
				waitForElementsOfCompaniesPage("closeErrorMessage");
				clickOnElementsOfCompaniesPage("closeErrorMessage");
				LOGGER.info(
						"MSP is not able to ON inherit Toggle at Company level when Root selected Disabled/Single-Tenant.");
				return true;
			} else {
				throw new AssertionError(
						"Error in verifying MSP is not able to configured SNOW at company level when Root admin selected Single-Tenant or Multi-Tenant for comapny.");
			}
		} catch (Exception e) {
			LOGGER.error("Exception caught during tryToEnableInheritToggle:" + e.getMessage());
			return false;
		}
	}

	public final int getTotalRecordCount(String key) {
		try {
			int totalRecord = 0;
			String[] allText = getTextBy(companiesPageProperties.getProperty(key)).split(" |/");
			for (int i = allText.length - 1; i > 0; i--) {
				if (isInt(allText[i])) {
					totalRecord = Integer.parseInt(allText[i].trim());
					break;
				}
			}
			return totalRecord;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTotalRecordCount " + e.getMessage()));
			return 0;
		}
	}

	/**
	 * This is a method to wait until an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfCompanyPage(String key) {
		try {
			verifyElementIsinvisibile(companiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfCompanyPage " + e.getMessage()));
		}
	}

	public final void waitUntilElementIsInvisibleOfCompanyPageDynamic(String key,int waittime) {
		try {
			verifyElementIsinvisibileDynamic(companiesPageProperties.getProperty(key),waittime);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfCompanyPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to get a sequence of selected columns
	 * 
	 * @param key - Locator of the list of selected columns
	 * @return - arraylist of the text present on the list of elements
	 */
	public final ArrayList<String> getSequenceOfSelectedColumnsOnCompanyPage(String key) {
		try {
			return getTextOfList(companiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getSequenceOfSelectedColumnsOnCompanyPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify search functionality of search filters present on
	 * incident page
	 * 
	 * @param LanguageCode - Language code
	 * @param textKey      - Locator of searchbox
	 * @param Text         - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey      - Locator for list of items filtered
	 * @return - boolean value of whether the search functionality is working
	 *         correctly
	 */
	public final boolean verifySearchValueOnCompany(String LanguageCode, String textKey, String Text,
			String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionality(LanguageCode, companiesPageProperties.getProperty(textKey), Text,
					companiesPageProperties.getProperty(emptyTextKey), companiesPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOnCompany " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify the filter functionality when a single option is
	 * selected from a static list of options
	 * 
	 * @param LanguageCode     - Language code
	 * @param checkboxKey      - Locator for the checkboxes in dropdown
	 * @param listOfElementKey - Locator for list of items in dropdown
	 * @param columnListKey    - Locator for list of all items in the column
	 * @param emptyTextKey     - Locator for "No items available" message in column
	 * @return - boolean value of whether the filter functionality is working
	 *         correctly
	 */
	public final boolean verifyFilterSingleSelect(String LanguageCode, String checkboxKey, String listOfElementKey,
			String columnListKey, String emptyTextKey) {
		try {
			return verifyFilterFunctionalityForSingleSelect(LanguageCode,
					companiesPageProperties.getProperty(checkboxKey),
					companiesPageProperties.getProperty(listOfElementKey),
					companiesPageProperties.getProperty(columnListKey),
					companiesPageProperties.getProperty(emptyTextKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterSingleSelect " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify the filter functionality when multiple options are
	 * selected from a static list of options
	 * 
	 * @param LanguageCode     - Language code
	 * @param checkboxKey      - Locator for the checkboxes in dropdown
	 * @param listOfElementKey - Locator for list of items in dropdown
	 * @param columnListKey    - Locator for list of all items in the column
	 * @param emptyTextKey     - Locator for "No items available" message in column
	 * @return - boolean value of whether the filter functionality is working
	 *         correctly
	 */
	public final boolean verifyFilterMultiSelect(String LanguageCode, String checkboxKey, String listOfElementKey,
			String columnListKey, String emptyTextKey) {
		try {
			return verifyFilterFunctionalityForMultiSelectForDyanmicList(LanguageCode,
					companiesPageProperties.getProperty(checkboxKey),
					companiesPageProperties.getProperty(listOfElementKey),
					companiesPageProperties.getProperty(columnListKey),
					companiesPageProperties.getProperty(emptyTextKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterMultiSelect " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to select first option from any dropdown
	 * 
	 * @param dropdownListKey - Locator of dropdown elements
	 * @return - String value of the option selecetd from the dropdown
	 */
	public final String selectFirstOptionFromDropdownOnCompaniesPage(String dropdownListKey) {
		try {
			return selectFirstValueFromDropdown(companiesPageProperties.getProperty(dropdownListKey));
		} catch (Exception e) {
			LOGGER.error(
					("Exception occured in method  selectFirstOptionFromDropdownOnCompaniesPage" + e.getMessage()));
			return null;
		}
	}

	public final String addCompany(Boolean reseller, String MSP, String daasReseller, String languageCode) {
		String email = generateRandomString(11) + "@hpmsqa.mailinator.com";
		String companyName = "Test123" + generateRandomString(11);
		try {
			clickOnElementsOfCompaniesPage("addButton");

			enterTextForCompaniesPage("companyNameOnPopup", companyName);
			
			enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);
			sleeper(2000);
			clickOnElementsOfCompaniesPage("countryArrowOnPopup");
			waitForElementsOfCompaniesPage("countrySearchOnPopup");
			enterTextForCompaniesPage("countrySearchOnPopup", CompaniesVariables.COUNTRY);
			clickOnElementsOfCompaniesPage("countrySelectOnPopup");

			waitForElementsOfCompaniesPage("languageArrowOnPopup");
			clickOnElementsOfCompaniesPage("languageArrowOnPopup");
			enterTextForCompaniesPage("languageSearchOnPopup", CommonVariables.LANGUAGE);
			clickOnElementsOfCompaniesPage("languageSelectOnPopup");
			
			clickOnElementsOfCompaniesPage("nextButtonOnPopup");
			
			
			enterTextForCompaniesPage("firstNameOnPopup", CompaniesVariables.FIRST_NAME);
		
			enterTextForCompaniesPage("lastNameOnPopup", CompaniesVariables.COMPANIES_TITLE);
		
			enterTextForCompaniesPage("emailOnPopup", email);

			enterTextForCompaniesPage("phoneNumberOnPopup", CompaniesVariables.NUMBER);

			clickOnElementsOfCompaniesPage("nextButtonOnPopup");
			sleeper(3000);
			waitForElementsOfCompaniesPage("mspDropdown");
			clickOnElementsOfCompaniesPage("mspDropdown");
			sleeper(3000);
			verifySingleSelectDropdownSearchBox(companiesPageProperties.getProperty("dropdownAfterSearchMSP"), MSP,
					companiesPageProperties.getProperty("mspSearchBox"),
					companiesPageProperties.getProperty("mspDropdown"),
					companiesPageProperties.getProperty("noResult"));
			LOGGER.info("MSP added sucessfully");
			sleeper(2000);
			clickOnElementsOfCompaniesPage("nextButtonOnPopup");

			if (reseller == true) {
				sleeper(2000);
				waitForElementsOfCompaniesPage("resellerDropdown");
				clickOnElementsOfCompaniesPage("resellerDropdown");
				verifySingleSelectDropdownSearchBox(companiesPageProperties.getProperty("dropdownAfterSearchReseller"),
						daasReseller, companiesPageProperties.getProperty("recellerSearchBox"),
						companiesPageProperties.getProperty("resellerDropdown"),
						companiesPageProperties.getProperty("noResult"));
				sleeper(2000);
			}
			clickOnElementsOfCompaniesPage("saveButtonOnPopup");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method addCompany " + e.getMessage()));
		}
		return companyName;
	}

	/** This method is used to add company by root admin with Billing model field.
	 * @param reseller
	 * @param MSP
	 * @param daasReseller
	 * @param billingModel
	 * @return
	 */
	public final String addCompanyBillingModel(Boolean reseller, String MSP, String daasReseller,String billingModel) {
		String email = generateRandomString(11) + "@hpmsqa.mailinator.com";
		String companyName = "Test567" + generateRandomString(11);
		try {
			clickOnElementsOfCompaniesPage("addButton");
			enterTextForCompaniesPage("companyNameOnPopup", companyName);
			enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);
			sleeper(2000);
			clickOnElementsOfCompaniesPage("countryArrowOnPopup");
			enterTextForCompaniesPage("countrySearchOnPopup", CompaniesVariables.COUNTRY);
			clickOnElementsOfCompaniesPage("countrySelectOnPopup");

			waitForElementsOfCompaniesPage("languageArrowOnPopup");
			clickOnElementsOfCompaniesPage("languageArrowOnPopup");
			enterTextForCompaniesPage("languageSearchOnPopup", CommonVariables.LANGUAGE);
			clickOnElementsOfCompaniesPage("languageSelectOnPopup");
			clickOnElementsOfCompaniesPage("nextButtonOnPopup");
			Thread.sleep(8000);// Takes time to load the page
			if(billingModel.equalsIgnoreCase("Subscription")){
				Thread.sleep(3000);// Tried all other methods, but sleeper works only
				clickOnElementsOfCompaniesPage("btnArrowBillingModel");
				clickOnElementsOfCompaniesPage("billingModelSubscription");
				clickOnElementsOfCompaniesPage("nextButtonOnPopup");
				
			}else if(billingModel.equalsIgnoreCase("License")){
				clickOnElementsOfCompaniesPage("nextButtonOnPopup");
			}else{
				LOGGER.error("Incorrect Billing Model name, please check.");
				return null;
			}
			
			enterTextForCompaniesPage("firstNameOnPopup", CompaniesVariables.FIRST_NAME);
			enterTextForCompaniesPage("lastNameOnPopup", CompaniesVariables.COMPANIES_TITLE);
			enterTextForCompaniesPage("emailOnPopup", email);
			enterTextForCompaniesPage("phoneNumberOnPopup", CompaniesVariables.NUMBER);
			sleeper(2000);
			clickOnElementsOfCompaniesPage("nextButtonOnPopup");
			
			sleeper(5000);
			clickOnElementsOfCompaniesPage("nextButtonOnPopup");

			if (reseller == true && billingModel.equalsIgnoreCase("Subscription")) {
				sleeper(6000); // The partner dropdown options take time to load when moved to this step
				clickOnElementsOfCompaniesPage("resellerDropdown");
				verifySingleSelectDropdownSearchBox(companiesPageProperties.getProperty("dropdownAfterSearchReseller"),
						daasReseller, companiesPageProperties.getProperty("recellerSearchBox"),
						companiesPageProperties.getProperty("resellerDropdown"),
						companiesPageProperties.getProperty("noResult"));
			}
			clickOnElementsOfCompaniesPage("saveButtonOnPopup");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method addCompany " + e.getMessage()));
		}
		return companyName;
	}
	
	/**
	 * This is a method to add company.
	 * 
	 * @param languageCode - languageCode for the localization testing
	 * @param compName     - Name of the company
	 * @param country      - Name of the country
	 * @param MSPName      - Name of the MSP which is going to select
	 * @param partnerName  - Name of the partner which is going to select
	 * @param partnerFlag  - if flag is true then parter will be selected, if
	 *                     partner flag is false then partner will not be selcted.
	 * @return - boolean value it should be return either true or false
	 * @throws Exception
	 */

	public final boolean addCompanies(String languageCode, String compName, String compEmail, String country,
			String plan, String MSPName, String partnerName, String firstName, String lastName, boolean partnerFlag,
			boolean loginFlag, boolean MSPFlag) throws Exception {
		ArrayList<String> infoLabels = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "daas_ui", "create_company.msp.msp_id").toLowerCase().trim(),
				getTextLanguage(languageCode, "daas_ui", "create_company.msp.street_address").toLowerCase().trim(),
				getTextLanguage(languageCode, "daas_ui", "create_company.msp.city").toLowerCase().trim(),
				getTextLanguage(languageCode, "daas_ui", "create_company.msp.state").toLowerCase().trim(),
				getTextLanguage(languageCode, "daas_ui", "create_company.msp.zip_code").toLowerCase().trim(),
				getTextLanguage(languageCode, "daas_ui", "create_company.msp.country").toLowerCase().trim()));
		ArrayList<String> MSPDetails = new ArrayList<String>(Arrays.asList(
				(getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID")).toLowerCase().trim(),
				(CommonVariables.STREET_ADDRESS).toLowerCase().trim(), (CommonVariables.CITY).toLowerCase().trim(),
				(CommonVariables.STATE).toLowerCase().trim(), CommonVariables.ZIP_CODE, country.toLowerCase().trim()));

		boolean flag = false;
		try {
			clickOnElementsOfCompaniesPage("addButton");
			if (loginFlag == true) {
				clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			}
			LOGGER.info("Add Company Information screen");
			enterTextForCompaniesPage("companyNameOnPopup", compName);
			enterTextForCompaniesPage("addressOnPopup", CommonVariables.STREET_ADDRESS);
			enterTextForCompaniesPage("cityOnPopup", CommonVariables.CITY);
			enterTextForCompaniesPage("stateOnCompPopup", CommonVariables.STATE);
			enterTextForCompaniesPage("zipCodeOnCompPopup", CommonVariables.ZIP_CODE);
			waitForElementsOfCompaniesPage("countryArrowOnPopup");
			sleeper(2000);
			clickOnElementsOfCompaniesPage("countryArrowOnPopup");
			enterTextForCompaniesPage("countrySearchOnPopup", country);
			clickOnElementsOfCompaniesPage("countrySelectOnPopup");
			clickOnElementsOfCompaniesPage("languageArrowOnPopup");
			enterTextForCompaniesPage("languageSearchOnPopup", CommonVariables.LANGUAGE);
			clickOnElementsOfCompaniesPage("languageSelectOnPopup");
			clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			sleeper(1000);
			
			LOGGER.info("Select Plan screen");
			waitForElementsOfCompaniesPage("btnArrowBillingModel");
			if(plan == "HP Active Care") {
				sleeper(200);
				clickOnElementsOfCompaniesPage("btnArrowBillingModel");
				if(!verifyElementsOfCompaniesPage("billingModelCarePack")) {
					LOGGER.info("Care Pack option is not available in Billing model drop down list.");
					return flag;
				}
				clickOnElementsOfCompaniesPage("billingModelCarePack");
				sleeper(1000);
				LOGGER.info("Care Pack option is selected in Billing model drop down list.");
				clickOnElementsOfCompaniesPage("planSelectListCombobox");
				sleeper(500);
			}
			else {
				clickOnElementsOfCompaniesPage("planArrowOnPopup");
				
			}
			if (selectTextValueFromDropdown(companiesPageProperties.getProperty("planSelectListOnPopup"), plan,
					companiesPageProperties.getProperty("planSelectedValue"))) {
				LOGGER.info(plan + " plan option is selected in PLAN drop down list.");
				clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			} else {
				LOGGER.error("Given plan is not present in dropdown/ Please pass the correct plan name.");
				return flag;
			}
			
			LOGGER.info("Add IT Admin Information");
			enterTextForCompaniesPage("typeItAdminFirstName", firstName);
			enterTextForCompaniesPage("typeItAdminSecondName", lastName);
			enterTextForCompaniesPage("typeItAdminEmail", compEmail);
			enterTextForCompaniesPage("typeItAdminPhoneNumber", CommonVariables.PHONE_NUMBER);
			sleeper(3000);
			if (loginFlag == true) {
				clickOnElementsOfCompaniesPage("addCompanyDailogPopupCreateButton");
				sleeper(2000);
				String notificationMessage = getTextOfCompaniesPage("toastNotification");
				if (notificationMessage
						.equals(getTextLanguage(languageCode, "daas_ui", "company.add.savecompany.notification"))) {
					LOGGER.info("Toast notification for company addition got matched successfully.");
				} else {
					LOGGER.info("Toast notification did not match for company addition.");
				}
				flag = true;
				return flag;
			} else {
				clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			}
			sleeper(4000);
			waitForElementsOfCompaniesPage("infomationOfMSP");
			if (loginFlag == false) {

				if (MSPFlag == true) {
					LOGGER.info("Select MSP screen");
					waitForElementsOfCompaniesPage("mspArrowOnPopup");
					sleeper(3000);
					clickOnElementsOfCompaniesPage("mspArrowOnPopup");
					enterTextForCompaniesPage("mspSearchOnPopup", MSPName);
					clickOnElementsOfCompaniesPage("mspSelectOnPopup");
					clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
					flag = true;
				}

				else {
					if (getTextOfCompaniesPage("selectedMSP").equals(MSPName)) {
						if (((getTextOfCompaniesPage("infomationOfMSP").equals(getTextLanguage(languageCode, "lhserver",
								"root_admin.companies.headings.company_information")))
								&& (getTextOfListOfCompaniesPage("infoLabelsMSP").equals(infoLabels))
								&& (getTextOfListOfCompaniesPage("infoValuesMSP").equals(MSPDetails)))) {
							flag = true;
							scrollOnCompaniesPage("addCompanyDialogPopupNextButton");
							clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
						} else {
							flag = false;
							LOGGER.error("MSP is not available for company!!!");
							return flag;
						}
					}
				}
				if (partnerFlag == true) {
					LOGGER.info("Select Partner screen");
					waitForElementsOfCompaniesPage("partnerArrowOnPopup");
					sleeper(2000);
					clickOnElementsOfCompaniesPage("partnerArrowOnPopup");
					enterTextForCompaniesPage("partnerSearchOnPopup", partnerName);
					clickOnElementsOfCompaniesPage("partnerSelectOnPopup");
				}
				LOGGER.info("Click Create button");
				clickOnElementsOfCompaniesPage("addCompanyDailogPopupCreateButton");
				if (!retryWaitUntilInVisible(companiesPageProperties.getProperty("addCompanyDailogPopupCreateButton"), 5000))
				{ //Company creation takes too long.
//				waitUntilElementIsInvisibleOfCompanyPage("addCompanyDailogPopupCreateButton");
				LOGGER.info("Company creation taking too long.");
				return false;
				}
				LOGGER.info("Company created successfully using Root admin login.");
				
				// Notification toaster is removed on Company Creation. Instead user lands on  
//				String notificationMessage = getTextOfCompaniesPage("toastNotification");
//				if (notificationMessage
//						.equals(getTextLanguage(languageCode, "daas_ui", "company.add.savecompany.notification"))) {
//					LOGGER.info("Toast notification for company addition got matched successfully.");
//				} else {
//					LOGGER.info("Toast notification did not match for company addition.");
//				}
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method addCompany " + e.getMessage()));
		}
		return flag;
	}


	
	public final boolean removeCompany(String companyName, String languageCode) {
		boolean flag = false;
		try {
			waitForPageLoaded();
			resetTableConfiguration();
			sleeper(3000);
			int totalCount = getTotalRecordCount("showingPaginationTotalCount");
			enterTextForCompaniesPage("nameSearchBox", companyName);
			waitForElementsOfCompaniesPage("tableOverlay");
			sleeper(5000);// Takes time to search the listed company under filter
			if (verifyElementsOfCompaniesPage("firstRow") == true) {
				mouseHoverOnCompanyPage("firstRowName");
				clickOnElementsOfCompaniesPage("hamburgerIcon");
				clickOnElementsOfCompaniesPage("removeCompany");
				getTextOfCompaniesPage("removePopupTitle")
						.equals(getTextLanguage(languageCode, "daas_ui", "company.remove.popup.title"));
				getTextOfCompaniesPage("revomePopupSubtitle")
						.equals(getTextLanguage(languageCode, "daas_ui", "company.remove.popup.text")
								.replace("{company}", companyName));
				clickOnElementsOfCompaniesPage("removeButton");
				sleeper(2000);
				String notificationMessage = getTextOfCompaniesPage("toastNotification");
				if (notificationMessage
						.equals(getTextLanguage(languageCode, "daas_ui", "company.remove.removed.successfully"))) {
					LOGGER.info("Toast notification for company removal got matched successfully.");
				} else {
					LOGGER.info("Toast notification did not match for company remove.");
				}

				waitForElementsOfCompaniesPage("tableOverlay");
				sleeper(3000);// Takes loader time to fetch rest of the companies after removing
				clickOnElementsOfCompaniesPage("clearFilter");
				waitForElementsOfCompaniesPage("tableOverlay");
				sleeper(2000);// Takes time to fetch whole list of companies after filter clear
				refreshPage();
				waitForElementsOfCompaniesPage("tableOverlay");
				int countAfterAdd = getTotalRecordCount("showingPaginationTotalCount");
				String countAfterAddConvert = Integer.toString(countAfterAdd);
				String beforeTotalCount = Integer.toString(totalCount - 1);
				if (countAfterAddConvert.equals(beforeTotalCount)) {
					enterTextForCompaniesPage("nameSearchBox", companyName);
					sleeper(5000);// Takes time to search the listed company under filter
					if (verifyElementsOfCompaniesPage("noItemAvailable") == true) {
						LOGGER.info("Company deleted successfully");
						flag = true;
					} else {
						LOGGER.error("Company not deleted successfully");
						return flag;
					}
				} else {
					LOGGER.error("After deleting company pagination count is not changed ");
					return flag;
				}
			} else {
				LOGGER.error("Company not present");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method removeCompany " + e.getMessage()));
			return flag;
		}
		return flag;
	}

	/**
	 * This is a method to validate plan list
	 * 
	 * @param billing - billing model 
	 * @param freeTrialToggle - free trial toggle status
	 * @param expectedPlanList - plan list to be compared
	 * @param compName - company name
	 * @param country - company country
	 * @param plan - plan name
	 * @return
	 * @throws Exception
	 */
	public final boolean validatePlansDropDownList(String languageCode,String billing, String freeTrialToggle, ArrayList<String> expectedPlanList, String compName, String country, String plan) throws Exception {

		ArrayList<String> actualPlanList = new ArrayList<String>();
		boolean flag = false;
		try {
			clickOnElementsOfCompaniesPage("addButton");
			enterTextForCompaniesPage("companyNameOnPopup", compName);
			enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);
			waitForElementsOfCompaniesPage("countryArrowOnPopup");
			sleeper(2000);
			clickOnElementsOfCompaniesPage("countryArrowOnPopup");
			enterTextForCompaniesPage("countrySearchOnPopup", country);
			clickOnElementsOfCompaniesPage("countrySelectOnPopup");
			clickOnElementsOfCompaniesPage("languageArrowOnPopup");
			enterTextForCompaniesPage("languageSearchOnPopup", CompaniesVariables.LANGUAGE);
			clickOnElementsOfCompaniesPage("languageSelectOnPopup");
			clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			sleeper(4000);
			clickOnElementsOfCompaniesPage("billingModelDropDown");
			if (billing.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "subscription.list.license"))) {
				clickOnElementsOfCompaniesPage("licenseModel");
				waitForElementsOfCompaniesPage("freeTrialToggle");

				if (freeTrialToggle.equalsIgnoreCase("off")) {
					clickOnElementsOfCompaniesPage("freeTrialToggle");
				} else {
					LOGGER.info("Free trial toggle is on");
				}
			} else if (billing.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "companies.list.subscription"))) {
				clickOnElementsOfCompaniesPage("subscriptionModel");
			} else {
				LOGGER.error("Provide correct billing model");
			}
			
			clickOnElementsOfCompaniesPage("planArrowOnPopup");
			List<WebElement> listOfSubscriptionPlan = getElementsOfCompanyListPage("listOfSubscriptionPlan");
			if (listOfSubscriptionPlan.size() > 0)
				for (int i = 0; i < listOfSubscriptionPlan.size(); i++) {
					actualPlanList.add(listOfSubscriptionPlan.get(i).getText().trim());
				}
			
			if (actualPlanList.equals(expectedPlanList)) {
				flag = true;
			}
			
			List<WebElement> listOfPlan = getElementsOfCompanyListPage("listOfSubscriptionPlan");
			if (listOfPlan.size() > 0) {
				for (int i = 0; i < listOfPlan.size(); i++) {
					if (listOfPlan.get(i).getText().equalsIgnoreCase(plan)) {
						clickOnWebElementsOfCompaniesPage(listOfPlan.get(i));
						break;
					}
				}
			} else {
				LOGGER.info("Planlist is empty");
			}

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method validatePlansDropDownList " + e.getMessage()));
		}
		clickOnElementsOfCompaniesPage("accountSetupCloseBtn");
		return flag;
	}

	/**
	 * This is a method to get a list of elements present on device list page
	 * 
	 * @param key - Locator of element
	 * @return - list of web elements
	 */
	public final List<WebElement> getElementsOfCompanyListPage(String key) {
		try {
			return getElementsTillAllElementsPresent(companiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsOfDeviceListPage " + e.getMessage()));
			return null;
		}
	}

	public final boolean updateValueOfDropdown(String dropdownListKey, String elementText, String dropdownBox)
			throws Exception {
		return selectTextValueFromDropdown(companiesPageProperties.getProperty(dropdownListKey), elementText,
				companiesPageProperties.getProperty(dropdownBox));
	}

	/**
	 * This method set SNOW configuration type (Disabled, Single-Tennat or
	 * Multi-Tenant) from Root admin.
	 * 
	 * @param expectedSNOWStatus- User send which status should be selected by Root
	 *                            admin
	 * @param languageCode        - languageCode for the localization testing
	 * @return String - Current SNOW status at Root level
	 * 
	 */

	public final String setCompanySNOWToggleAtRoot(String expectedSNOWStatus, String languageCode) {
		String snowStatus = null;
		try {

			waitForElementsOfCompaniesPage("rootPreferencesPage");
			clickOnElementsOfCompaniesPage("rootPreferencesPage");
			waitForElementsOfCompaniesPage("rootSNOWStatus");
			String currentSNOWStatus = getTextOfCompaniesPage("rootSNOWStatus");
			snowStatus = snowToggleStatusAtRoot(currentSNOWStatus, expectedSNOWStatus, languageCode);
			sleeper(2000); // Need to use hard code wait
		} catch (Exception e) {
			LOGGER.error("Exception caught during setCompanySNOWToggleAtRoot:" + e.getMessage());
			return null;
		}
		return snowStatus;
	}

	public final String snowToggleStatusAtRoot(String currentSNOWStatus, String expectedSNOWStatus, String languageCode)
			throws Exception {
		// If currentSNOWStatus and expectedSNOWStatus are equal then do nothing.
		LOGGER.info("Current SNOW Toggle Status is: " + expectedSNOWStatus);
		if (currentSNOWStatus.equals(getTextLanguage(languageCode, "daas_ui", "global.not.applicable"))
				|| currentSNOWStatus.equals(getTextLanguage(languageCode, "daas_ui", "global.enabled"))) {
			if (getTextOfCompaniesPage("rootSNOWStatus").equals(expectedSNOWStatus)) {
				LOGGER.info("Expected SNOW status is present.");
				return currentSNOWStatus;
			}
		}
		// This condition will set Multi-Tenant from root admin.
		if (expectedSNOWStatus.equals(getTextLanguage(languageCode, "lhserver",
				"settings.preferences.service_now.tenant_config.options.multi_tenant"))) {
			if (currentSNOWStatus.equals(getTextLanguage(languageCode, "daas_ui", "settings.preferences.service_now.tenant_config.options.multi_tenant"))) {
				setConfigurationDetailsWithToggleON("rootSelectMultiTenant");
				LOGGER.info("SNOW settings saved as Multi-Tenant successfully.");
			} else {
				setConfigurationDetailsWithoutToggleOnOff("rootSelectMultiTenant");
				LOGGER.info("SNOW settings saved as Multi-Tenant successfully.");
			}
		} // This condition will set Single-Tenant from root admin.
		else if (expectedSNOWStatus.equalsIgnoreCase(getTextLanguage(languageCode, "lhserver",
				"settings.preferences.service_now.tenant_config.options.single_tenant"))) {
			if (currentSNOWStatus.equals(getTextLanguage(languageCode, "daas_ui", "settings.preferences.service_now.tenant_config.options.single_tenant"))) {
				setConfigurationDetailsWithToggleON("rootSNOWSingleTenant");
				LOGGER.info("SNOW settings saved as Single-Tenant successfully.");
			} else {
				setConfigurationDetailsWithoutToggleOnOff("rootSelectSingleTenant");
				LOGGER.info("SNOW settings saved as Single-Tenant successfully.");
			}

		} // This condition will set Disabled from root admin.
		else {
			waitForElementsOfCompaniesPage("rootSNOWEditIcon");
			clickOnElementsOfCompaniesPage("rootSNOWEditIcon");
			clickOnElementsOfCompaniesPage("rootSNOWToggleOnOff");
			clickOnElementsOfCompaniesPage("rootSaveSNOWStatus");
			LOGGER.info("SNOW settings saved as Disabled successfully.");
		}
		waitForElementsOfCompaniesPage("rootSNOWStatus");
		waitForElementsOfCompaniesPage("tableOverlay");
		// Return the current SNOW status after expected value set.
		if (expectedSNOWStatus.equals(getTextLanguage(languageCode, "daas_ui", "global.not.applicable")))
			return getTextOfCompaniesPage("rootSNOWStatus");
		else
			return getTextOfCompaniesPage("rootSNOWStatus");
	}

	/**
	 * This method will configured SNOW at Tenant level.
	 * 
	 * @param expectedSNOWStatus - SNOW status like Enabled/Disabled/Not Configured
	 * @param languageCode       -languageCode for the localization testing
	 * @param url                - SNOW configuration url.
	 * @param userName           - SNOW URL username.
	 * @param password           - SNOW URL Password.
	 * @return boolean - Return current SNOW status Enabled/Disabled/Not configured.
	 * @throws InterruptedException
	 */
	public final String tenantLevelSNOWConfiguration(String expectedSNOWStatus, String languageCode, String url,
			String userName, String password) throws InterruptedException {
		String currentSNOWStatus = "";
		try {
			waitForElementsOfCompaniesPage("snowStatusAtCompanyLevel");
			String snowStatus = getTextOfCompaniesPage("snowStatusAtCompanyLevel");
			// This condition will configured SNOW at tenant level, validated success
			// message and return the current status of SNOW.
			if (snowStatus
					.contains(getTextLanguage(languageCode, "daas_ui", "companies.details.section.not_configured"))) {
				sleeper(3000);
				clickOnElementsOfCompaniesPage("snowEditIconAtCompanyLevel");
				waitForElementsOfCompaniesPage("snowToggleOnOffAtCompany");
				sleeper(3000);
				clickOnElementsOfCompaniesPage("snowToggleOnOffAtCompany");
				enterTextForCompaniesPage("urlSnowCompanyLevel", url);
				enterTextForCompaniesPage("adminSnowCompanyLevel", userName);
				enterTextForCompaniesPage("passwordSnowCompanyLevel", password);
				clickOnElementsOfCompaniesPage("saveButtonSnowCompanyLevel");
				// Add error message code
				LOGGER.info("All the fields data are entered successfully.");
				verifyElementsOfCompaniesPage("snowMessagePopupAtTenantLevel");
				String snowPopUpMessage = getTextOfCompaniesPage("snowMessagePopupAtTenantLevel");
				if (expectedSNOWStatus.contains(getTextLanguage(languageCode, "daas_ui", "global.enabled"))
						|| expectedSNOWStatus.equals(getTextLanguage(languageCode, "daas_ui", "global.disabled"))) {
					if (snowPopUpMessage
							.equals(getTextLanguage(languageCode, "daas_ui", "companies.details.update.success")))
						LOGGER.info("SNOW settings successfully configured at Tenant level.");
				} else if (snowPopUpMessage
						.equals(getTextLanguage(languageCode, "daas_ui", "settings.service_now.error_message_423")))
					LOGGER.info("SNOW settings is not configured at Tenant level as expected.");
				sleeper(2000); // Need to use hard code wait here.
				verifyElementsOfCompaniesPage("cancelButtonOfSNOWForm");
				clickOnElementsOfCompaniesPage("cancelButtonOfSNOWForm");
				if (expectedSNOWStatus.equals(getTextLanguage(languageCode, "daas_ui", "global.disabled"))) {
					clickOnElementsOfCompaniesPage("snowEditIconAtCompanyLevel");
					waitForElementsOfCompaniesPage("snowToggleOnOffAtCompany");
					clickOnElementsOfCompaniesPage("snowToggleOnOffAtCompany");
					clickOnElementsOfCompaniesPage("saveButtonSnowCompanyLevel");
					clickOnElementsOfCompaniesPage("cancelButtonOfSNOWForm");
					waitForElementsOfCompaniesPage("tableOverlay");
					currentSNOWStatus = getTextOfCompaniesPage("snowErrorMessageComapnyLevel");
				} else {
					waitForElementsOfCompaniesPage("tableOverlay");
					currentSNOWStatus = getTextOfCompaniesPage("snowStatusAtCompanyLevel");
				}
			} // If SNOW settings is already enabled at Tenant Level then it's return current
				// SNOW Status.
			else if (snowStatus.contains(getTextLanguage(languageCode, "daas_ui", "global.enabled"))) {
				waitForElementsOfCompaniesPage("tableOverlay");
				currentSNOWStatus = getTextOfCompaniesPage("snowStatusAtCompanyLevel");
			} else if (snowStatus.contains(getTextLanguage(languageCode, "daas_ui", "global.disabled"))) {
				waitForElementsOfCompaniesPage("tableOverlay");
				currentSNOWStatus = getTextOfCompaniesPage("snowStatusAtCompanyLevel");
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in tenantLevelSNOWConfiguration.");
		}
		sleeper(2000); // Need to use wait here.
		return currentSNOWStatus;
	}

	/**
	 * This method is create company from root admin.
	 * @param languageCode
	 * 
	 * @return String : It will return Company name.
	 */

	public String createCompany(String languageCode) {
		try {
			waitForPageLoaded();
			resetTableConfiguration();
			sleeper(5000);
			int totalCount = 0;
			totalCount = getTotalRecordCount("showingPaginationTotalCount");
			String companyName = null;
			companyName = addCompany(true, CompaniesVariables.DAAS_MSP, CompaniesVariables.DAAS_RESELLER, languageCode);
			sleeper(25000);// after clicking on save button it take time to navigate to company list page
			refreshPage();
			clickOnElementsOfCompaniesPage("customersTab");
			int countAfterAdd = getTotalRecordCount("showingPaginationTotalCount");
			if (countAfterAdd == (totalCount + 1)) {
				LOGGER.info("After adding company pagination count is Incremented by 1.");
				waitForElementsOfCompaniesPage("nameSearchBox");
				enterTextForCompaniesPage("nameSearchBox",companyName);
				sleeper(5000);// after entering value in search box it take time to load the search result
				String expectedName = getTextOfCompaniesPage("firstName");
				if (expectedName.equalsIgnoreCase(companyName)) {
					return companyName;
				} else {
					return null;
				}
			} else
				LOGGER.error("After adding company pagination count is not changed");

		} catch (Exception e) {
			LOGGER.error("Exception occurs in createCompany method.");
		}
		return null;
	}

	
	/** This method returns the name of newly created company.
	 * @param BillingModel
	 * @param partnerName
	 * @return
	 */
	public String createCompanyBillingModel(String BillingModel,String partnerName) {
		try {
			waitForPageLoaded();
			String companyName = null;
			companyName = addCompanyBillingModel(true, CompaniesVariables.DAAS_MSP, partnerName,BillingModel);
			Thread.sleep(5000);//create company api takes time
			waitUntilElementIsInvisibleOfCompanyPage("btnCompanyCreate");
			Thread.sleep(2000);
			if(getTextOfCompaniesPage("companiesDetailsTitle").equalsIgnoreCase(companyName))
			{
				return companyName;
			} else {
				return null;
			}

		} catch (Exception e) {
			LOGGER.error("Exception occurs in createCompany method.");
		}
		return null;
	}

	
	/**
	 * This is a method to get text present on an element on company list page
	 * 
	 * @param key - locator of the element
	 * @return - string value of the text present on the element
	 */
	public final String getTextOfCompanyPage(String key) {
		try {
			return getTextBy(companiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfCompanyPage " + e.getMessage()));
			return null;
		}
	}

	public final boolean matchTextOfCompanyPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(companiesPageProperties.getProperty(key), Text);
	}

	/**
	 * This method is used to read api data for the company
	 * 
	 * @param api   - URL from you which you want the data
	 * @param body  - request body
	 * @param index - label index
	 * @param id    - event name required
	 */
	public final List<String> getAllCompany(String api, String body, int index, String id) {
		try {
			List<String> listOfIds = new ArrayList<String>();
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON)
					.header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.post(api);
			String expected = response.asString();
			JSONObject jsonObject, obj;
			JSONArray hitsArray;
			jsonObject = new JSONObject(expected);
			obj = (JSONObject) jsonObject.get("hits");
			hitsArray = (JSONArray) obj.get("hits");
			if (hitsArray.length() < 1) {
				LOGGER.info("Company does not exist.");
				return listOfIds;
			}
			for (int i = 0; i < hitsArray.length(); i++) {
				JSONObject jsonObject1 = hitsArray.getJSONObject(i);
				listOfIds.add(jsonObject1.get("_id").toString());
			}
			return listOfIds;
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllCompany: " + e.getMessage());
			return null;
		}
	}

	/**
	 * This method will remove all Company
	 * 
	 * @param environment
	 * @return
	 * @throws Exception
	 */
	public final boolean removeAllCompany(String environment, String tenantID, String body, String environmentURL) {
		try {
			boolean flag = false;
			List<String> listOfIds = getAllCompany(getSearchServiceApi(environment) + ConstantURL.GET_API_COMPANY
					+ tenantID + ConstantURL.GET_API_COMPANY_SECOND, body, 0, "id");
			if (listOfIds.size() > 0) {
				for (int i = 0; i < listOfIds.size(); i++) {
					int code = getStatusCode(environmentURL + ConstantURL.DELETE_API_COMPANY + listOfIds.get(i), "",
							"DELETE", environment);
					if (code == CommonVariables.CODEOK) {
						flag = true;
						LOGGER.info("Delete API got success while removing Company.");
					} else {
						flag = false;
						LOGGER.error("Delete API got failed while removing Company.");
					}
				}
			} else {
				LOGGER.info("There are no Company present.");
				flag = true;
			}
			refreshPage();
			waitForPageLoaded();
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeCompany: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to scroll to Preference Page
	 * 
	 * @param key- Locator of element to scroll.
	 */

	public void scrollToPreferencePage(String key) throws Exception {
		scrollTillView(companiesPageProperties.getProperty(key));
	}

	/**
	 * This method is used to scroll to Coordinates of APage
	 * 
	 * @param xPixels - xPixels Coordinates for scrolling
	 * @param yPixels - yPixels Coordinates for scrolling
	 */

	public void scrollByCoordinatesofPreferencePage(int xPixels, int yPixels) {

		jsDriver().executeScript("window.scrollBy(" + xPixels + "," + yPixels + ")");
	}

	/**
	 * This method is used to edit functionality of Device Recommendation
	 * 
	 * @param languageCode - languageCode for the localization testing
	 */

	public boolean verifyEditFunctionalityofDeviceRecommendation(String LanguageCode) {
		boolean flag = false;
		try {
			scrollToPreferencePage("productCatloagHeading");
			scrollByCoordinatesofAPage(0, -80);
			LOGGER.info("Scrolled down to productCatloagHeading ");
			waitForElementsOfCompaniesPage("productCatloagHeading");
			if (getTextOfCompaniesPage("productCatloagHeading")
					.equals(getTextLanguage(LanguageCode, "daas_ui", "catalogs.label").toUpperCase())) {
				LOGGER.info("Product Catalaog tile title is matched.");
			} else {
				LOGGER.error("Product Catalaog tile title is not matched.");
			}
			verifyElementsOfCompaniesPage("editBtn");
			scrollByCoordinatesofAPage(0, -150);
			clickOnElementsOfCompaniesPage("editBtn");
			waitForElementsOfCompaniesPage("deviceRecommendationHeader");
			verifyElementsOfCompaniesPage("deviceRecommendationHeader");
			verifyElementsOfCompaniesPage("brandPreferenceModalLabel");
			verifyElementsOfCompaniesPage("underutilizedPreferenceModalLabel");

			if ((getTextOfCompaniesPage("brandPreferenceValue")
					.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "brand_preference.option.hp_devices"))
					&& (getTextOfCompaniesPage("underutilizedPreferenceValue").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "under_utilized.option.higher_devices"))))) {
				LOGGER.info("Default Values are correct after edit for device recommendation.");
			} else {
				LOGGER.error("Default Values are not correct after edit for device recommendation.");
			}

			waitForElementsOfCompaniesPage("brandPreferenceDropdown");
			clickOnElementsOfCompaniesPage("brandPreferenceDropdown");
			clickOnElementsOfCompaniesPage("anyBrandDevice");
			waitForElementsOfCompaniesPage("underutilizedPreferenceDropdown");
			clickOnElementsOfCompaniesPage("underutilizedPreferenceDropdown");
			clickOnElementsOfCompaniesPage("lowerPerformanceDevice");
			waitForElementsOfCompaniesPage("deviceRecommendationSubmit");
			clickOnElementsOfCompaniesPage("deviceRecommendationSubmit");
			waitForElementsOfCompaniesPage("deviceRecommendationSuccessNotification");

			// Check Success Message after updating
			if (getTextOfCompaniesPage("deviceRecommendationSuccessNotification")
					.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success")
							.replace("{name}", getTextLanguage(LanguageCode, "daas_ui",
									"companies.details.section.device_recommendation.title")))) {
				LOGGER.info("Success Notification is displayed on device recommendation.");
				sleeper(2000);// Need to use hard code wait here.
				waitForElementsOfCompaniesPage("brandPreferenceDefaultValue");
				// Check after updates, values are updated for device recommendation
				if ((getTextOfCompaniesPage("brandPreferenceDefaultValue").equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "brand_preference.option.other_devices"))
						&& (getTextOfCompaniesPage("underutilizedPreferenceDefaultValue").equalsIgnoreCase(
								getTextLanguage(LanguageCode, "daas_ui", "under_utilized.option.lower_devices"))))) {
					LOGGER.info("Device recommendation values are updated properly.");
					flag = true;

				} else {
					LOGGER.error("Device recommendation values are not updated properly.");
					return flag;
				}
			} else {
				LOGGER.error("Success Notification is not displayed on device recommendation.");
				return flag;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyEditFunctionalityofDeviceRecommendation" + ex.getMessage());
		}
		return flag;
	}

	/**
	 * This method is used to check default values of Device Recommendation
	 * 
	 * @param languageCode - languageCode for the localization testing
	 */

	public boolean verifyDefaultValuesofDeviceRecommendation(String LanguageCode) {
		boolean flag = false;
		try {
			scrollToPreferencePage("productCatloagHeading");
			scrollByCoordinatesofAPage(0, -80);
			LOGGER.info("Scrolled down to productCatloagHeading ");
			waitForElementsOfCompaniesPage("productCatloagHeading");
			if (getTextOfCompaniesPage("productCatloagHeading")
					.equals(getTextLanguage(LanguageCode, "daas_ui", "catalogs.label").toUpperCase())) {
				LOGGER.info("Product Catalaog tile title is matched.");
			} else {
				LOGGER.error("Product Catalaog tile title is not matched.");
			}
			verifyElementsOfCompaniesPage("productCatloagLabel");
			verifyElementsOfCompaniesPage("deviceRecommendationLabel");
			waitForElementsOfCompaniesPage("brandPreferenceDefaultValue");
			waitForElementsOfCompaniesPage("underutilizedPreferenceDefaultValue");

			if ((getTextOfCompaniesPage("brandPreferenceDefaultValue")
					.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "brand_preference.option.hp_devices"))
					&& (getTextOfCompaniesPage("underutilizedPreferenceDefaultValue").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "under_utilized.option.higher_devices"))))) {
				LOGGER.info("Default Values are correct for device recommendation.");
				flag = true;
			} else {
				LOGGER.error("Default Values are not correct for device recommendation.");
				return flag;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyDefaultValuesofDeviceRecommendation" + ex.getMessage());
		}
		return flag;
	}

	/*
	 * This method is used to get unique strings of elements from a companies page
	 * 
	 * @param listLocator - list locator
	 * 
	 * @return - List<String>
	 * 
	 * @throws Exception
	 */
	public final List<String> getUniqueElementsFromCompaniesPageList(String listLocator) throws Exception {
		return getUniqueElementsofStringsFromList(companiesPageProperties.getProperty(listLocator));

	}

	/**
	 * This method is used to cancel functionality of Device Recommendation
	 * 
	 * @param languageCode - languageCode for the localization testing
	 */

	public boolean verifyCancelFunctionalityofDeviceRecommendation(String LanguageCode) {
		boolean flag = false;
		try {
			scrollToPreferencePage("productCatloagHeading");
			scrollByCoordinatesofAPage(0, -80);
			LOGGER.info("Scrolled down to productCatloagHeading ");
			waitForElementsOfCompaniesPage("productCatloagHeading");
			if (getTextOfCompaniesPage("productCatloagHeading")
					.equals(getTextLanguage(LanguageCode, "daas_ui", "catalogs.label").toUpperCase())) {
				LOGGER.info("Product Catalaog tile title is matched.");
			} else {
				LOGGER.error("Product Catalaog tile title is not matched.");
			}
			clickOnElementsOfCompaniesPage("editBtn");
			waitForElementsOfCompaniesPage("deviceRecommendationHeader");
			verifyElementsOfCompaniesPage("deviceRecommendationHeader");
			verifyElementsOfCompaniesPage("brandPreferenceModalLabel");
			verifyElementsOfCompaniesPage("underutilizedPreferenceModalLabel");
			waitForElementsOfCompaniesPage("brandPreferenceDropdown");
			clickOnElementsOfCompaniesPage("brandPreferenceDropdown");
			clickOnElementsOfCompaniesPage("anyBrandDevice");
			waitForElementsOfCompaniesPage("underutilizedPreferenceDropdown");
			clickOnElementsOfCompaniesPage("underutilizedPreferenceDropdown");
			clickOnElementsOfCompaniesPage("lowerPerformanceDevice");
			waitForElementsOfCompaniesPage("deviceRecommendationCancel");
			clickOnElementsOfCompaniesPage("deviceRecommendationCancel");
			waitForElementsOfCompaniesPage("brandPreferenceDefaultValue");

			if ((getTextOfCompaniesPage("brandPreferenceDefaultValue")
					.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "brand_preference.option.other_devices"))
					&& (getTextOfCompaniesPage("underutilizedPreferenceDefaultValue").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "under_utilized.option.lower_devices"))))) {
				LOGGER.info("Device recommendation values are not updated after cancel.");
				flag = true;
			} else {
				LOGGER.error("Device recommendation values are updated after cancel.");
				return flag;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyCancelFunctionalityofDeviceRecommendation" + ex.getMessage());
		}
		return flag;
	}

	/**
	 * This method is used for clearing any filters applied on company list page
	 * 
	 * @param clearFilterKey - locator of clear button
	 * @throws Exception
	 */
	public final void clearFiltersOfCompaniesListPage(String clearFilterKey) throws Exception {
		clearFilters(companiesPageProperties.getProperty(clearFilterKey));
	}

	/**
	 * This method is used fetch text of List
	 * 
	 * @param key - - locator of element to fetch list
	 * @throws Exception
	 */

	public final ArrayList<String> getTextOfListOfCompaniesDetailPage(String key) {
		try {
			return getLabelsOfChart(companiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfListOfCompaniesDetailPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to compare two arraylists
	 * 
	 * @param key      - locator of element to fetch first list
	 * @param subTypes - second list
	 * @return - boolean value of whether both the lists match
	 * @throws Exception
	 */
	public final boolean compareTwoListOfCompanyDetailPage(String key, ArrayList<String> subTypes) throws Exception {
		return compareTwoList(companiesPageProperties.getProperty(key), subTypes);
	}

	public final void clearTextOfCompaniesPage(String key) throws Exception {
		clearTextRefresh(companiesPageProperties.getProperty(key));
	}

	/**
	 * This is the method to get the enability status for link/button
	 * 
	 * @param navigationItemPreviouskey - locator for the button/link to be tested
	 * @return - boolean value of the enability status
	 */
	public final boolean getButtonEnabilityStatus(String navigationItemPreviouskey) {
		try {
			return !getElement(companiesPageProperties.getProperty(navigationItemPreviouskey)).getAttribute("class")
					.contains("disabled");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getButtonEnabilityStatus " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to deselect all checboxes of a popup
	 * 
	 * @param checkbox      - locator of the list of checkboxes
	 * @param checkboxLabel - locator of the checkbox label
	 * @throws Exception
	 */
	public final void deselectAllCheckboxOfPopup(String checkbox, String checkboxLabel) throws Exception {
		List<WebElement> element1 = getElementsTillAllElementsPresent(companiesPageProperties.getProperty(checkbox));
		List<WebElement> element2 = getElementsTillAllElementsPresent(
				companiesPageProperties.getProperty(checkboxLabel));
		for (int i = 0; i < element1.size(); i++) {
			if (element1.get(i).isSelected()) {
				sleeper(500);
				element2.get(i).click();
			}
		}
	}

	/**
	 * This method will create company using API
	 * 
	 * @param companyName - Company name
	 * @param country     - county as per the region US and EU
	 * @param locale      - language
	 * @param msp_id      - msp_id of msp user
	 * @param reseller_id - reseller_id of reseller user
	 * @return - boolean
	 * @throws Exception
	 */
	public final boolean createCompanyUsingAPI(String companyName, String country, String locale, String msp_id, String reseller_id, String email,String companyPlan) throws Exception {
		Response response = null;
		String body = null;

		if (!reseller_id.equals("") && !email.equals("")) {
			body = "{\"company_name\":\"" + companyName + "\",\"city\":\"city\",\"address\":\"address1\",\"state\":\"state1\",\"zip\":\"123456\",\"country\":\"" + country
                    + "\",\"locale\":\"" + locale
                    + "\",\"billing_model\":\"LICENSE\",\"subscription_sym\":\"" + companyPlan + "\",\"primary_contact\":{\"first_name\":\"John\",\"last_name\":\"Wick\",\"email\":\""
                    + email + "\",\"phone_number\":\"987654321\"},\"msp_parent\":{\"id\":\"" + msp_id
                    + "\",\"association\":\"MSP\"},\"reseller_parent\":{\"id\":\"" + reseller_id
                    + "\",\"association\":\"RESELLER\"}}";
		} 	
	
		 else {
			        email = companyName + "@yopmail.com";
			        body = "{\"company_name\":\"" + companyName + "\",\"city\":\"city\",\"address\":\"address1\",\"state\":\"state1\",\"zip\":\"123456\",\"country\":\"" + country
		                    + "\",\"locale\":\"" + locale
		                    + "\",\"billing_model\":\"LICENSE\",\"subscription_sym\":\"" + companyPlan + "\",\"primary_contact\":{\"first_name\":\"John\",\"last_name\":\"Wick\",\"email\":\""
		                    + email + "\",\"phone_number\":\"987654321\"},\"msp_parent\":{\"id\":\"" + msp_id
		                    + "\",\"association\":\"MSP\"},\"reseller_parent\":{}}";
		        }
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON)
				.header("authorization", "Bearer " + mspAuthToken).body(body);
		response = httpRequest.post(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH")
				+ CompaniesVariables.COMPANYRESOURCE);

		if (response.getStatusCode() == 201) {
			return true;
		} else {
			LOGGER.error("Company creation failed due to reason " + response.asString());
			return false;
		}
	}

	/**
	 * This method will create company using API
	 *
	 * @param companyName - Company name
	 * @param country     - county as per the region US and EU
	 * @param locale      - language
	 * @param msp_id      - msp_id of msp user
	 * @param reseller_id - reseller_id of reseller user
	 * @return - boolean
	 * @throws Exception
	 */
	public final boolean createCompanyWithBillingModelUsingAPI(String companyName, String country, String locale, String msp_id, String reseller_id, String email,String companyPlan,String billingModel) throws Exception {
		Response response = null;
		String body = null;

		if (!reseller_id.equals("") && !email.equals("")) {
			body = "{\"company_name\":\"" + companyName + "\",\"city\":\"city\",\"country\":\"" + country
					+ "\",\"locale\":\"" + locale
					+ "\",\"billing_model\":\""+billingModel+"\",\"subscription_sym\":\"" + companyPlan + "\",\"primary_contact\":{\"first_name\":\"Test\",\"last_name\":\"Companies\",\"email\":\""
					+ email + "\",\"phone_number\":\"987654321\"},\"msp_parent\":{\"id\":\"" + msp_id
					+ "\",\"association\":\"MSP\"},\"reseller_parent\":{\"id\":\"" + reseller_id
					+ "\",\"association\":\"RESELLER\"}}";
		}

		else {
			email = companyName + "@yopmail.com";
			body = "{\"company_name\":\"" + companyName + "\",\"city\":\"city\",\"country\":\"" + country
					+ "\",\"locale\":\"" + locale
					+ "\",\"billing_model\":\""+billingModel+"\",\"subscription_sym\":\"" + companyPlan + "\",\"primary_contact\":{\"first_name\":\"Test\",\"last_name\":\"Companies\",\"email\":\""
					+ email + "\",\"phone_number\":\"987654321\"},\"msp_parent\":{\"id\":\"" + msp_id
					+ "\",\"association\":\"MSP\"},\"reseller_parent\":{}}";
		}
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON)
				.header("authorization", "Bearer " + mspAuthToken).body(body);
		response = httpRequest.post(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH")
				+ CompaniesVariables.COMPANYRESOURCE);

		if (response.getStatusCode() == 201) {
			return true;
		} else {
			LOGGER.error("Company creation failed due to reason " + response.asString());
			return false;
		}
	}
	/**
	 * @param tenantID - tenant id of a company which is to be removed
	 * @return - boolean
	 * @throws Exception
	 */
	public final boolean removeCompanyUsingAPI(String tenantID) throws Exception {
		Response response = null;
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON)
				.header("authorization", "Bearer " + mspAuthToken).body("{}");
		response = httpRequest.delete(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH")
				+ CompaniesVariables.REMOVE_COMPANY_URL + tenantID + CompaniesVariables.REMOVE_COMPANY_URL2);

		if (response.getStatusCode() == 204) {
			return true;
		} else {
			LOGGER.error("Company deletion failed due to reason " + response.asString());
			return false;
		}
	}

	/**
	 * @param companiesList - List of companies check boxes needs to be selected
	 * @param key - Locator for companies list page spinner
	 * @throws Exception
	 */
	public final void clickElementsOfCompaniesPage(List<WebElement> companiesList, String key) throws Exception {

		Iterator<WebElement> companiesListIterator = companiesList.iterator();
		while (companiesListIterator.hasNext()) {
			WebElement element = companiesListIterator.next();
			element.click();
			waitUntilElementInvisibleOfCompanyPage(key);
		}
	}

	/**
	 * @param companiesList - List of companies check boxes needs to be selected
	 * @param companyCheckboxList - Locator for companies checkbox
	 * @throws Exception
	 */
	public final void clickElementsOfCompaniesPage(List<WebElement> companiesList, List<WebElement> companyCheckboxList) throws Exception {

		Iterator<WebElement> companiesListIterator = companiesList.iterator();
		Iterator<WebElement> companyCheckboxIterator = companyCheckboxList.iterator();
		while (companiesListIterator.hasNext()) {
			WebElement element = companiesListIterator.next();
			WebElement checkBox = companyCheckboxIterator.next();
			mouseHoverOnCompanyPage(checkBox);
			waitForPageLoaded();
			element.click();
		}
	}

	/**
	 * This is a method to wait until an element is invisible return boolean
	 * 
	 * @param key - Locator of element
	 */
	public final boolean waitUntilElementInvisibleOfCompanyPage(String key) {
		return verifyElementIsinvisibile(companiesPageProperties.getProperty(key));
	}
	
	/**
	 * This is a method enter text in company list page
	 * @param key - Locator of element
	 * @param text- Company name Text
	 */
	public final void enterTextForCompanyPageUsingJavaScript(String key, String text) throws Exception {
			enterTextUsingJavaScript(companiesPageProperties.getProperty(key), text);	
	}
	 /* This method is used to create company.
	 * 
	 * @param languageCode - languageCode for the localization testing
	 * @param compName - company name
	 * @param compEmail - company email
	 * @param country - company country
	 * @param plan - plan name
	 * @param MSPName - MSP
	 * @param partnerName - Partner
	 * @param firstName - first name of ITA
	 * @param lastName - last name of ITA
	 * @param partnerFlag - partner condition
	 * @param billing - billing model
	 * @param freeTrialToggle - freeTrialtoggle
	 * @return
	 * @throws Exception
	 */
	public final boolean addCompany(String languageCode, String compName, String compEmail, String country, String plan, String MSPName, String partnerName, String firstName, String lastName, boolean partnerFlag, String billing, String freeTrialToggle) throws Exception {
		clickOnElementsOfCompaniesPage("addButton");
		enterTextForCompaniesPage("companyNameOnPopup", compName);
		enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);
		enterTextForCompaniesPage("addressOnPopup", CompaniesVariables.ADDRESS);
		enterTextForCompaniesPage("stateOnCompPopup", CompaniesVariables.STATE);
		enterTextForCompaniesPage("zipCodeOnCompPopup", CompaniesVariables.PINCODE);
		waitForElementsOfCompaniesPage("countryArrowOnPopup");
		sleeper(2000);
		clickOnElementsOfCompaniesPage("countryArrowOnPopup");
		enterTextForCompaniesPage("countrySearchOnPopup", country);
		clickOnElementsOfCompaniesPage("countrySelectOnPopup");
		clickOnElementsOfCompaniesPage("languageArrowOnPopup");
		enterTextForCompaniesPage("languageSearchOnPopup", CompaniesVariables.LANGUAGE);
		clickOnElementsOfCompaniesPage("languageSelectOnPopup");
		
		clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
		sleeper(4000);
		
		clickOnElementsOfCompaniesPage("billingModelDropDown");

		if (billing.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "subscription.list.license"))) {
			clickOnElementsOfCompaniesPage("licenseModel");
			waitForElementsOfCompaniesPage("freeTrialToggle");

			if (freeTrialToggle.equalsIgnoreCase("off")) {
				clickOnElementsOfCompaniesPage("freeTrialToggle");
			} else {
				LOGGER.info("Free trial toggle is on");
			}
		} else if (billing.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "companies.list.subscription"))) {
			clickOnElementsOfCompaniesPage("subscriptionModel");
		} else {
			LOGGER.error("Provide correct billing model");
		}

		clickOnElementsOfCompaniesPage("planArrowOnPopup");
		List<WebElement> listOfPlan = getElementsOfCompanyListPage("listOfSubscriptionPlan");
		if (listOfPlan.size() > 0) {
			for (int i = 0; i < listOfPlan.size(); i++) {
				if (listOfPlan.get(i).getText().equalsIgnoreCase(plan)) {
					clickOnWebElementsOfCompaniesPage(listOfPlan.get(i));
					break;
				}
			}
		} else {
			LOGGER.info("Planlist is empty");
		}
		clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");

		// Second step of the company
		enterTextForCompaniesPage("typeItAdminFirstName", firstName);
		enterTextForCompaniesPage("typeItAdminSecondName", lastName);
		enterTextForCompaniesPage("typeItAdminEmail", compEmail);
		enterTextForCompaniesPage("typeItAdminPhoneNumber", CompaniesVariables.NUMBER);
		sleeper(3000);
		clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");

		// Third step of the company
		if (MSPName.equals("")) {
			sleeper(5000);
			clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
		} else {
			waitForElementsOfCompaniesPage("mspArrowOnPopup");
			sleeper(3000);
			clickOnElementsOfCompaniesPage("mspArrowOnPopup");
			enterTextForCompaniesPage("mspSearchOnPopup", MSPName);
			clickOnElementsOfCompaniesPage("mspSelectOnPopup");
			clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");

		}

		// fourth step of the company
		if (partnerFlag == true) {
			waitForElementsOfCompaniesPage("partnerArrowOnPopup");
			sleeper(8000); // wait for partner API to respond
			clickOnElementsOfCompaniesPage("partnerArrowOnPopup");
			enterTextForCompaniesPage("partnerSearchOnPopup", partnerName);
			clickOnElementsOfCompaniesPage("partnerSelectOnPopup");
		}
		clickOnElementsOfCompaniesPage("addCompanyDailogPopupCreateButton");
		waitForPageLoaded();
		sleeper(8000);//Company takes time to create after hitting create button.
		waitForElementsOfCompaniesPage("companyNameIdentity");
	
		if (getTextOfCompaniesPage("companyNameIdentity").equalsIgnoreCase(compName)) {
			LOGGER.info("Company Added successfully through Root admin.");
			return true;
		} else {
			LOGGER.info("Company Creation failed");
			return false;
		}
	}
	public final String getElementSizeOnCompaniesPage(String key) throws Exception {
		return String.valueOf(getAllElements(companiesPageProperties.getProperty(key)).size());
	}
	/**
	 * This is a method to select date from calendar filter
	 * @param date - current date
	 * @param monthKeyCurrent - locator of current month
	 * @param rightArrowKey - locator for right arrow key on calendar
	 * @param daysOnCurrentMonthKey - locator for days on current month
	 */
	public final void selectDateFromCalenderOnCompanyPage(String date, String monthKeyCurrent, String rightArrowKey, String daysOnCurrentMonthKey) {
		try {
			selectDateFromCalenderDatePickerContainer(date, companiesPageProperties.getProperty(monthKeyCurrent), companiesPageProperties.getProperty(rightArrowKey), companiesPageProperties.getProperty(daysOnCurrentMonthKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectDateFromCalenderOnCompanyDetailpage " + e.getMessage()));
		}
	}
	
	/**
	 * Verifies the drop down list of Source Of Customer
	 * 
	 * @param allLocatorKey
	 * @return
	 * @throws Exception
	 */
	public final boolean verifySourceOfCustomerdropdown(String allLocatorKey) throws Exception {
		Boolean flag = false;
		String chartIdsArray[] ={ "AWS" ,"MEM" ,"IW" ,"DSP" ,"TechPulse" ,"HPConnect" ,"WolfPro"};
		ArrayList<String> SourceOfCustomerIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		List<WebElement> allSourceOfCustomerIdsList = getElementsTillAllElementsVisible(companiesPageProperties.getProperty(allLocatorKey));
		if(allSourceOfCustomerIdsList.size() > 0) {
		for (int chartIdsListCounter = 0; chartIdsListCounter < SourceOfCustomerIdsList.size(); chartIdsListCounter++) {
			for (int alllistCounter = chartIdsListCounter; alllistCounter < allSourceOfCustomerIdsList.size() - 1;) {
				if (!SourceOfCustomerIdsList.get(chartIdsListCounter).equalsIgnoreCase(allSourceOfCustomerIdsList.get(alllistCounter).getText())) {
					LOGGER.error("Sequence of " + allSourceOfCustomerIdsList.get(alllistCounter).getText() + " is not correct");
					flag = false;
					break;
				} else {
					flag = true;
					break;
				}
			}
		}
		}else {
			LOGGER.error("Source of Customer list is empty");
		}
		return flag;
	}

	public final String fetchCompanyPinUsingAPI(String tenantID) throws Exception {
		Response response = null;
		String body = "{\"code\":\"4yy6pLTQ\",\"expirationDate\":\"2023-03-22T12:00:00.000Z\"}";
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON)
				.header("authorization", "Bearer " + mspAuthToken).body(body);
		response = httpRequest.post(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH")
				+ CompaniesVariables.COMPANYPINAPI1 + tenantID + CompaniesVariables.COMPANYPINAPI2);
		String expected = response.asString();
		JSONObject jsonObject = new JSONObject(expected);
	    return jsonObject.get("code").toString();
		
	}

	
}

