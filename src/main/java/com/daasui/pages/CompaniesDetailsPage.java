package com.daasui.pages;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CompaniesDetailsPage extends CommonMethod {


	private Properties selectedLanguageProperties;
	private ObjectReader companiesDetailsPagePropertiesReader = new ObjectReader();
	private Properties companiesDetailsPageProperties;
	private ObjectReader environmentPropertiesReader = new ObjectReader();
	public Properties environmentProperties;
	public static String uiVersion = System.getProperty("uiVersion");

	private CompaniesDetailsPage instance;

	public CompaniesDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (CompaniesDetailsPage.class) {
				if (instance == null) {
					instance = new CompaniesDetailsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public CompaniesDetailsPage(WebDriver driver) throws IOException {
		
		environmentProperties = environmentPropertiesReader.getObjectRepository("Environment");
		companiesDetailsPageProperties = companiesDetailsPagePropertiesReader.getObjectRepository("CompaniesDetailsPageV3");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = companiesDetailsPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * This method is the method to verify if an element is present on support team details page
	 * 
	 * @param key - locator of the element
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyElementsOfCompaniesDetailsPage(String key) throws Exception {
		return verifyElementIsPresent(companiesDetailsPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfCompaniesDetailsPage(String key) throws Exception {
		return verifyElementIsVisible(companiesDetailsPageProperties.getProperty(key));
	}

	public final boolean matchTextOfCompaniesDetailsPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(companiesDetailsPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfCompaniesDetailsPage(String key) throws Exception {
		return verifyElementIsEnable(companiesDetailsPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfCompaniesDetailsPage(String key) throws Exception {
		return verifyElementIsSelected(companiesDetailsPageProperties.getProperty(key));
	}

	public final String getTextOfCompaniesDetailsPage(String key) throws Exception {
		return getTextBy(companiesDetailsPageProperties.getProperty(key));
	}

	public final String getAttributesOfCompaniesDetailsPage(String key, String value) throws Exception {
		return getAttribute(companiesDetailsPageProperties.getProperty(key), value);
	}

	public final int getCountOfRowsOnCompaniesDetailsPageForRoot(String key) throws Exception {
		return getCountOfRows(companiesDetailsPageProperties.getProperty(key));
	}
	
	/**
	 * @param key - locator of the element
	 * @return - boolean
	 * @throws Exception
	 */
	public final boolean waitForElementsOfCompaniesDetailsPageForinvisibile(String key) throws Exception {
		return verifyElementIsinvisibile(companiesDetailsPageProperties.getProperty(key));
	}

	public final void clickOnElementsOfCompaniesDetailsPage(String key) throws Exception {
		click(companiesDetailsPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnCompaniesDetailsPage(String key) throws Exception {
		clickByJavaScript(companiesDetailsPageProperties.getProperty(key));
	}

	public final void enterTextForCompaniesDetailsPage(String key, String Text) throws Exception {
		enterText(companiesDetailsPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfCompaniesDetailsPage(String key) throws Exception {
		return verifyElementIsClickable(companiesDetailsPageProperties.getProperty(key));
	}

	public final WebElement getElementOfCompaniesDetailsPage(String key) throws Exception {
		return getElement(companiesDetailsPageProperties.getProperty(key));
	}

	public final void clearTextForCompaniesDetailsPage(String key) throws Exception {
		clearText(companiesDetailsPageProperties.getProperty(key));
	}

	// Method to go on the Preferences tab
	public final void gotoPreferencesTab() throws Exception {
		clickOnElementsOfCompaniesDetailsPage("preferenceTab");
	}
	
	// Method to go on the Overview tab
	public final void gotoOverviewTab() throws Exception {
		if(waitForElementsOfCompaniesDetailsPage("overviewTab")) {
		clickOnElementsOfCompaniesDetailsPage("overviewTab");
		}else {
			LOGGER.info("Overview Tab is not visible. Hence unable to navigate to Overview tab.");
		}
	}
	
	// Method to go on the Plans tab
		public final boolean gotoPlansTab() throws Exception {
			clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			return waitForElementsOfCompaniesDetailsPage("subscriptionTitle");
		}

	// Method checks toggle for submit a help from Preferences tab and if it is disable , enable it
	// Modified this method as earlier method didn't support localization
	public final void checkSubmitIncidentToggle() throws Exception {
		waitForElementsOfCompaniesDetailsPage("submitHelpToggleStatus");
		boolean isToggleOn = verifyElementsOfCompaniesDetailsPage("submitHelpToggleStatus");
		if (!isToggleOn) {
			clickOnElementsOfCompaniesDetailsPage("submitHelpToggle");
		}
	}

	public final void moveToElementOnCompaniesDetailsPage(String key) throws Exception {
		moveToElements(companiesDetailsPageProperties.getProperty(key));
	}

	public final boolean selectElementFromDropDown(String dropdownId, String key, String text) throws Exception {
		click(companiesDetailsPageProperties.getProperty(dropdownId));
		return selectFromDropdown(companiesDetailsPageProperties.getProperty(dropdownId), companiesDetailsPageProperties.getProperty(key), text);
	}

	/**
	 * This is a method to wait until an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfCompanyDetailsPage(String key) throws Exception {
		verifyElementIsinvisibile(companiesDetailsPageProperties.getProperty(key));

	}

	/**
	 * This is a method to select first option from any dropdown
	 * 
	 * @param dropdownListKey - Locator of dropdown elements
	 * @return - String value of the option selecetd from the dropdown
	 */
	public final String selectFirstOptionFromDropdownOnCompaniesDetailsPage(String dropdownListKey, String text) {
		try {
			return selectValueOnPopup(companiesDetailsPageProperties.getProperty(dropdownListKey), text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectFirstOptionFromDropdownOnCompaniesDetailsPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to select first option from any dropdown
	 * 
	 * @param dropdownListKey - Locator of dropdown elements
	 * @return - String value of the option selecetd from the dropdown
	 */
	public final String selectFirstOptionFromDropdown(String dropdownListKey) {
		try {
			return selectFirstValueFromDropdown(companiesDetailsPageProperties.getProperty(dropdownListKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectFirstOptionFromDropdownOnCompaniesDetailsPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is the method to select MSP on companies details page
	 * 
	 * @param key - locator of msp dropdown
	 * @param text - already selected MSP
	 * @return
	 */
	public final String selectMSP(String searchBox, String key, String text) {
		try {
			if (text.contains(CompaniesVariables.MSP_COMPANY_DETAILS_TESTUSERONE)) {

				enterTextForCompaniesDetailsPage(searchBox, CompaniesVariables.MSP_COMPANY_DETAILS_TESTUSERTWO);
				waitForElementsOfCompaniesDetailsPage("tableOverlay");
				selectFirstOptionFromDropdown(key);
				return CompaniesVariables.MSP_COMPANY_DETAILS_TESTUSERTWO;
			} else {

				enterTextForCompaniesDetailsPage(searchBox, CompaniesVariables.MSP_COMPANY_DETAILS_TESTUSERONE);
				waitForElementsOfCompaniesDetailsPage("tableOverlay");
				selectFirstOptionFromDropdown(key);
				return CompaniesVariables.MSP_COMPANY_DETAILS_TESTUSERONE;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectMSP " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to select partner on companies details page
	 * 
	 * @param key - locator of partner dropdown
	 * @param text - already selected partner
	 * @return
	 */
	public final String selectPartner(String searchBox, String key, String text) {
		try {
			if (text.equals(CompaniesVariables.RESELLER_COMPANY_DETAILS_TESTUSERONE)) {

				enterTextForCompaniesDetailsPage(searchBox, CompaniesVariables.RESELLER_COMPANY_DETAILS_TESTUSERTWO);
				waitForElementsOfCompaniesDetailsPage("tableOverlay");
				selectFirstOptionFromDropdown(key);
				return CompaniesVariables.RESELLER_COMPANY_DETAILS_TESTUSERTWO;
			} else {

				enterTextForCompaniesDetailsPage(searchBox, CompaniesVariables.RESELLER_COMPANY_DETAILS_TESTUSERONE);
				waitForElementsOfCompaniesDetailsPage("tableOverlay");
				selectFirstOptionFromDropdown(key);
				return CompaniesVariables.RESELLER_COMPANY_DETAILS_TESTUSERONE;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectPartner " + e.getMessage()));
			return null;
		}
	}

	// This method will enter text to be searched in dropdown
	public final boolean selectElementFromDropDownByText(String dropdownId, String InputText, String key, String text) throws Exception {
		click(companiesDetailsPageProperties.getProperty(dropdownId));
		enterText(companiesDetailsPageProperties.getProperty(InputText), text);
		sleeper(2000);
		return selectFromDropdown(companiesDetailsPageProperties.getProperty(dropdownId), companiesDetailsPageProperties.getProperty(key), text);
	}

	public final void mouseHoverOnCompanyPage(String key) throws Exception {
		mouseHover(companiesDetailsPageProperties.getProperty(key));
	}

	public final boolean verifyElementPresentIndropdownOfCompaniesDetailsPage(String key, String text) throws Exception {
		click(companiesDetailsPageProperties.getProperty(key));
		return verifyTextPresentInDropdown(companiesDetailsPageProperties.getProperty(key), text);
	}

	public final void selectIndropdownOfCompaniesDetailsPage(String key, String text) throws Exception {
		SelectTextPresentInDropdown(companiesDetailsPageProperties.getProperty(key), text);
	}

	/**
	 * This is a method to get the text of a list as a list itself
	 * 
	 * @param key - locator of element
	 * @return - arraylist of the text of all elements present in the list
	 */

	public final ArrayList<String> getTextOfListOfCompanyDetailsPage(String key) {
		try {
			return getTextOfList(companiesDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfListOfCompanyDetailsPage " + e.getMessage()));
			return null;
		}
	}

	public final void scrollOnCompaniesDetailsPage(String key) throws Exception {
		scrollTillView(companiesDetailsPageProperties.getProperty(key));
	}

	/**
	 * This method is used to verify the description logs page
	 * 
	 * @param logText - expected text on logs page
	 * @return boolean if description matches on logs page returns true else false
	 * @throws Exception
	 */
	public boolean verifyDescriptionOnLogsPage(String logText) throws Exception {

		boolean flag = true;
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		resetTableConfiguration();
		waitForPageLoaded();
			logPage.clickOnElementsOfLogPage("firstCheckbox");
		if((logPage.getTextOfLogPage("logsPageDescription").toLowerCase()).contains(logText.toLowerCase()))
			flag = true;
		else {
			LOGGER.error("Description on logs page is incorrect");
			flag = false;
		}
		return flag;
	}

	/**
	 * This is a method to select from calendar filter
	 * 
	 * @param date - date need to be selected
	 * @param calenderKey - locator for calendar filter
	 * @param monthKey - locator to go to months
	 * @param rightArrowKey - locator for right arrow key on calendar
	 * @param daysKey - locator for days
	 * @return flag if date selected returns true else false
	 */
	public final boolean selectDateFromCalenderForCompanyPin(String date, String calenderKey, String monthKey, String rightArrowKey, String daysKey) {
		boolean flag = false;
		try {
			String[] strArray = date.split(" ");
			String month = strArray[0].replaceAll(",", "");
			sleeper(1000);
			while (!(getTextOfCompaniesDetailsPage(monthKey).contains(month))) {
				clickOnElementsOfCompaniesDetailsPage(rightArrowKey);
			}
			List<WebElement> totalDates = getElementsTillAllElementsPresent(companiesDetailsPageProperties.getProperty(daysKey));
			int countOfDays = totalDates.size();
			for (int daysCounter = 0; daysCounter < countOfDays; daysCounter++) {
				String text = totalDates.get(daysCounter).getText();
				if (text.equalsIgnoreCase(strArray[1].replaceAll(",", ""))) {
					totalDates.get(daysCounter).click();
					flag = true;
					break;
				}
			}
		} catch (Exception e) {
			flag = false;
			LOGGER.error(("Exception occured in method selectDateFromCalenderForCompanyPin " + e.getMessage()));
			return flag;
		}
		return flag;
	}

	/**
	 * This is a method to click on all elements in list of web elements
	 * 
	 * @param uiList - List of elements of column on UI and click on 1st element
	 * @throws Exception
	 */

	public final void clickElementsOfCompanyDetailsPage(List<WebElement> uiList) throws Exception {
		for(WebElement element:uiList) {
			element.click();
		}

	}

	/**
	 * This is a method to get a list of elements present on device list page
	 * 
	 * @param key - Locator of element
	 * @return - list of web elements
	 */
	public final List<WebElement> getElementsOfCompanyDetailsPage(String key) {
		try {
			return getElementsTillAllElementsPresent(companiesDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsOfCompanyDetailsPage " + e.getMessage()));
			return null;
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
	public final void addAllCustomFields(String editKey, String addMoreFieldsKey, String saveKey, String firstTextBoxKey, String secondTextBoxKey, String thirdTextBoxKey, String fourthTextBoxKey, String fifthTextBoxKey, String customFieldOneKey, String customFieldTwoKey, String customFieldThreeKey, String customFieldFourKey, String customFieldFiveKey) {
		try {
			clickOnElementsOfCompaniesDetailsPage(editKey);
			enterTextForCompaniesDetailsPage(firstTextBoxKey, customFieldOneKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(secondTextBoxKey, customFieldTwoKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(thirdTextBoxKey, customFieldThreeKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(fourthTextBoxKey, customFieldFourKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(fifthTextBoxKey, customFieldFiveKey);
			clickOnElementsOfCompaniesDetailsPage(saveKey);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method addAllCustomFields " + e.getMessage()));
		}
	}

	/**
	 * This is the method to clear text refresh from an element present on user details page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void clearTextRefreshFromCompaniesDetailsPageTextField(String key) throws Exception {
		clearTextRefresh(companiesDetailsPageProperties.getProperty(key));
	}

	
	/**
	 * This method is used to select element from dropdown based on text.
	 * @param dropdownListKey
	 * @param elementText
	 * @param dropdownBox
	 * @throws Exception
	 */
	public final void selectDropdownSingleValueTextFromCompaniesDetailsPage(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		selectTextValueFromDropdown(companiesDetailsPageProperties.getProperty(dropdownListKey),elementText,companiesDetailsPageProperties.getProperty(dropdownBox));
	}


	/**This method is used to validate working of Authentication(save) on company details page.
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyAuthenticationMethod(String LanguageCode,String operationType) throws Exception {
		boolean flag = false;
		String defaultSelectedMethod = null;
		String selectedMethodText = null;
		sleeper(5000); //need this to slowdown the flow, without this sleeper click is not happening.
		waitForPageLoaded();
		clickOnElementsOfCompaniesDetailsPage("editAuthenticationIcon");
		clickOnElementsOfCompaniesDetailsPage("authenticationDropdownButton");
		defaultSelectedMethod = getTextOfCompaniesDetailsPage("authenticationDropdownBox");
		if(defaultSelectedMethod.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.sign_in.options.azure_and_hp"))){
			defaultSelectedMethod = getTextLanguage(LanguageCode, "daas_ui", "companies.details.sign_in.options.azure_only");
		}else if(defaultSelectedMethod.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.sign_in.options.azure_only"))){
			defaultSelectedMethod = getTextLanguage(LanguageCode, "daas_ui", "companies.details.sign_in.options.azure_and_hp");
		}else{
			LOGGER.error("Invalid value is selected in drop down of authentication methods.");
		}
		selectDropdownSingleValueTextFromCompaniesDetailsPage( "authenticationList",defaultSelectedMethod, "authenticationDropdownBox");
		selectedMethodText = getTextOfCompaniesDetailsPage("authenticationDropdownBox");
		if(operationType.equalsIgnoreCase("Save")){
			clickOnElementsOfCompaniesDetailsPage("saveButtonAuthentication");
			LOGGER.info("Clicked on SAVE button.");
			String successMessage = getTextOfCompaniesDetailsPage("toastNotification");
			clickOnElementsOfCompaniesDetailsPage("closeToastNotification");
			if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.sign_in.auth_methods")))) {
				if(getTextOfCompaniesDetailsPage("authenticationMethodSelected").equalsIgnoreCase(selectedMethodText)){
					flag = true;
					LOGGER.info("Authentication method: '"+defaultSelectedMethod+"' changes got reflected in details page.");
				}else{
					LOGGER.error("Selected authentication method: '"+defaultSelectedMethod+"' is not getting reflected in details page.");
				}
			}else{
				LOGGER.error("Error occured while saving details of authentication methods.");
			}
		} else if(operationType.equalsIgnoreCase("Cancel")){
			clickOnElementsOfCompaniesDetailsPage("cancelButtonAuthentication");
			LOGGER.info("Clicked on CANCEL button.");
			if(!getTextOfCompaniesDetailsPage("authenticationMethodSelected").equalsIgnoreCase(selectedMethodText)){
				flag = true;
				LOGGER.info("Authentication method:'"+defaultSelectedMethod+"' changes did not get reflected in details page by CANCEL operation.");
			}else{
				LOGGER.error("Selected authentication method:'"+defaultSelectedMethod+"' is getting changed even by CANCEL operation.");
			}
		}else{
			LOGGER.error("Incorrect Operation Type! Please pass either SAVE or CANCEL.");
		}
		return flag;
	}
	
	/**
	 * This is a method to add maximum number of lifecycle status fields for a company
	 * 
	 * @param editKey
	 * @param addMoreFieldsKey
	 * @param saveKey
	 * @param firstTextBoxKey
	 * @param secondTextBoxKey
	 * @param thirdTextBoxKey
	 * @param fourthTextBoxKey
	 * @param fifthTextBoxKey
	 * @param sixthTextBoxKey
	 * @param seventhTextBoxKey
	 * @param eighthTextBoxKey
	 * @param ninthTextBoxKey
	 * @param tenthTextBoxKey
	 * @param eleventhTextBoxKey
	 * @param twelvethTextBoxKey
	 * @param thirteenthTextBoxKey
	 * @param fourteenthTextBoxKey
	 * @param fifthteenthTextBoxKey
	 * @param customFieldOneKey
	 * @param customFieldTwoKey
	 * @param customFieldThreeKey
	 * @param customFieldFourKey
	 * @param customFieldFiveKey
	 * @param customFieldSixKey
	 * @param customFieldSevenKey
	 * @param customFieldEightKey
	 * @param customFieldNineKey
	 * @param customFieldTenKey
	 * @param customFieldElevenKey
	 * @param customFieldTwelveKey
	 * @param customFieldThirteenKey
	 * @param customFieldFourteenKey
	 * @param customFieldFifteenKey
	 */
	public final void addAllLifecycleStatusFields(String editKey, String addMoreFieldsKey, String saveKey, String firstTextBoxKey, String secondTextBoxKey, String thirdTextBoxKey, String fourthTextBoxKey, String fifthTextBoxKey, String sixthTextBoxKey,String seventhTextBoxKey,String eighthTextBoxKey,String ninthTextBoxKey,String tenthTextBoxKey,String eleventhTextBoxKey,String twelvethTextBoxKey,String thirteenthTextBoxKey,String fourteenthTextBoxKey,String fifthteenthTextBoxKey, String customFieldOneKey, String customFieldTwoKey, String customFieldThreeKey, String customFieldFourKey, String customFieldFiveKey, String customFieldSixKey, String customFieldSevenKey, String customFieldEightKey, String customFieldNineKey, String customFieldTenKey, String customFieldElevenKey, String customFieldTwelveKey, String customFieldThirteenKey, String customFieldFourteenKey, String customFieldFifteenKey) {
		try {
			clickOnElementsOfCompaniesDetailsPage(editKey);
			enterTextForCompaniesDetailsPage(firstTextBoxKey, customFieldOneKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(secondTextBoxKey, customFieldTwoKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(thirdTextBoxKey, customFieldThreeKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(fourthTextBoxKey, customFieldFourKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(fifthTextBoxKey, customFieldFiveKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(sixthTextBoxKey, customFieldSixKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(seventhTextBoxKey, customFieldSevenKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(eighthTextBoxKey, customFieldEightKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(ninthTextBoxKey, customFieldNineKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(tenthTextBoxKey, customFieldTenKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(eleventhTextBoxKey, customFieldElevenKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(twelvethTextBoxKey, customFieldTwelveKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(thirteenthTextBoxKey, customFieldThirteenKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(fourteenthTextBoxKey, customFieldFourteenKey);
			clickOnElementsOfCompaniesDetailsPage(addMoreFieldsKey);
			enterTextForCompaniesDetailsPage(fifthteenthTextBoxKey, customFieldFifteenKey);
			scrollOnCompaniesDetailsPage(saveKey);
			clickOnElementsOfCompaniesDetailsPage(saveKey);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method addAllCustomFields " + e.getMessage()));
		}
	}
	
	/** 
	 * This is a method to select date from calendar filter
	 * @param date - current date
	 * @param monthKeyCurrent - locator of current month
	 * @param rightArrowKey - locator for right arrow key on calendar
	 * @param daysOnCurrentMonthKey - locator for days on current month 
	 */
	public final void selectDateFromCalenderOnCompanyDetailpage(String date, String monthKeyCurrent, String rightArrowKey, String daysOnCurrentMonthKey) {
		try {
			selectDateFromCalenderDatePicker(date, companiesDetailsPageProperties.getProperty(monthKeyCurrent), companiesDetailsPageProperties.getProperty(rightArrowKey), companiesDetailsPageProperties.getProperty(daysOnCurrentMonthKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectDateFromCalenderOnCompanyDetailpage " + e.getMessage()));
		}
	}
	
	/**
	 * This method is used to verify the description of extend trial period which appears on logs page
	 * @param LanguageCode
	 * @param CompanyName- Name of company whose trial period extended.
	 * @param endDate -end date of subscription license.
	 * @return boolean if description matches on logs page returns true else false
	 * @throws Exception
	 */
	public boolean verifyTrialDescriptionOnLogsPage(String LanguageCode, String CompanyName, String endDate) throws Exception {
		boolean flag = true;
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		resetTableConfiguration();
		waitForPageLoaded();
		logPage.waitForElementsOfLogPage("firstRecordOnLogsPage");
		logPage.clickOnElementsOfLogPage("firstRecordOnLogsPage");
		if (logPage.getTextOfLogPage("logsPageDescription").equals(getTextLanguage(LanguageCode, "daas_entitlement_notifier_service", "daas.notifier.subscription.trial.end_date_changed.description").replace("{0}", CompanyName).replace("{1}", endDate))) {
			LOGGER.info("Description on logs page when trial period extended verified successfully");
			flag = true;
		} else {
			LOGGER.error("Description on logs page is incorrect when trial period extended.");
			flag = false;
		}
		return flag;
	}
	
	/**
	 * This method will convert license model to subscription model
	 * 
	 * @param tenantId - tenantId of a company
	 * @param partnerUuid - partnerUuid
	 * @param totalSeats - number of seats
	 * @param endDate - end date of sub
	 * @return - boolean
	 * @throws Exception
	 */
	public final boolean convertToSubscriptionModel(String tenantId, String partnerUuid, String totalSeats, String endDate) throws Exception {
		Response response = null;
		String body = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = formatTime.format(cal.getTime());
		
		if (endDate.equals("")) {
			body = "{\"tenantId\":\"" + tenantId + "\",\"partnerUuid\":\"" + partnerUuid + "\"," + "\"startDate\":\"" + startDate + "\",\"endDate\":\"\",\"planId\":\"" + CompaniesVariables.PLAN_ID_INSIGHTS + "\",\"totalSeats\":\"" + totalSeats + "\",\"isAutoRenew\":true}";
		} else {
			body = "{\"tenantId\":\"" + tenantId + "\",\"partnerUuid\":\"" + partnerUuid + "\"," + "\"startDate\":\"" + startDate + "\",\"endDate\":\"" + endDate + "\",\"planId\":\"" + CompaniesVariables.PLAN_ID_INSIGHTS + "\",\"totalSeats\":\"" + totalSeats + "\",\"isAutoRenew\":false}";
		}
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
		response = httpRequest.post(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + CompaniesVariables.RESOURCE_ENTITLEMENT_SERVICE + tenantId);

		if (response.getStatusCode() == 201) {
			return true;
		} else {
			LOGGER.error("Convert to subscription model failed due to " + response.asString());
			return false;
		}
	}

	/**
	 * This method is used to verify the description of company logs which appears on logs page
	 * @param logText
	 * @return
	 */
	public boolean verifyDescriptionOnLoggingPage(String logText) {
		try {
			LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
			logPage.waitForElementsOfLogPage("hamburgerMenuOnNotification");
			logPage.clickOnElementsOfLogPage("hamburgerMenuOnNotification");
			logPage.clickOnElementsOfLogPage("openLogsHyperlink");
			LOGGER.info("Clicked on open logs hyperlink");
			logPage.switchToDifferentTabOfLogsPage();
			sleeper(3000);
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
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
			LOGGER.info("Clicked on reset to default");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			LOGGER.info("Clicked on save button");
			logPage.waitForElementsOfLogPage("tableOverlay");

			// logPage.waitForElementsOfLogPage("firstRecordOnLogsPage");
			logPage.clickByJavaScriptOnLogPage("firstRecordOnLogsPage");
			LOGGER.info("Clicked on first record on logs page");
			if (logText.contains("seat has been added")) {
				if (logPage.getTextOfLogPage("logsPageDescription").contains("seat has been added")) {
					logPage.switchToDifferentTabOfLogsPage();
					return true;
				} else {
					LOGGER.error("Description on logs page is incorrect when devices are imported successfully");
					return false;
				}
			} 
			else {
					LOGGER.error("Description on logs page is incorrect when devices are not imported successfully");
					return false;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyDescriptionOnLoggingPage " + e.getMessage()));
			return false;
		}
}

	
	/**
	 * This method gets the plan name and seats used from plan overview tab
	 * @param LanguageCode
	 * @param companyDetails- Hashmap to enter plan info in
	 * @throws Exception
	 */
	public HashMap<String, String> getPlanNameAndUsedSeats(String LanguageCode, HashMap<String, String> companyDetails) throws Exception {
		String planName="";String usedSeats="";
		List<WebElement> plans=getElementsOfCompanyDetailsPage("planNameOnAccountTile");
		List<WebElement> seats=getElementsOfCompanyDetailsPage("seatsInfoOnPlanOverview");
		for(int i=0;i<plans.size();i++) {
			String totalPlanName = plans.get(i).getText();
			planName= totalPlanName.split(" \\(")[0];
			String totalSeatsText= seats.get(i).getText();
			usedSeats = totalSeatsText.split(" ")[0];
			companyDetails.put(planName, usedSeats);
		}
		return companyDetails;
	}
	
	/**
	 * This method verifies the plan seats consumption after enrollment of 1 device
	 * @param companyDetails- Hashmap containing plan info
	 * @throws Exception
	 */
	public Object[][] verifySeatConsumption(HashMap<String, String> companyDetails) throws Exception {
		Object[][] flagAndString=new Object[1][2];
		String usedSeats="";String planName="";
		List<WebElement> plans=getElementsOfCompanyDetailsPage("planNameOnAccountTile");
		List<WebElement> seats=getElementsOfCompanyDetailsPage("seatsInfoOnPlanOverview");
		for(int i=0;i<plans.size();i++) {
			String totalPlanName = plans.get(i).getText();
			planName= totalPlanName.split(" \\(")[0];
			String totalSeatsText= seats.get(i).getText();
			usedSeats = totalSeatsText.split(" ")[0];
			if(Integer.parseInt(companyDetails.get(planName))+1 == Integer.parseInt(usedSeats)) {
				flagAndString[0][0]=true;
				flagAndString[0][1]=planName;
			}
		}
		return flagAndString;
	}
	
	/**
	 * This method verifies the plan seats consumption after unenrollment of 1 device
	 * @param companyDetails- Hashmap containing plan info
	 * @throws Exception
	 */
	public boolean verifySeatConsumptionAfterUnenrollment(HashMap<String, String> companyDetails, String seatConsumedPlan) throws Exception {
		String usedSeats="";String planName="";
		boolean flag=false;
		List<WebElement> plans=getElementsOfCompanyDetailsPage("planNameOnAccountTile");
		List<WebElement> seats=getElementsOfCompanyDetailsPage("seatsInfoOnPlanOverview");
		for(int i=0;i<plans.size();i++) {
			String totalPlanName = plans.get(i).getText();
			planName= totalPlanName.split(" \\(")[0];
			String totalSeatsText= seats.get(i).getText();
			usedSeats = totalSeatsText.split(" ")[0];
			if(planName.toLowerCase().contains(seatConsumedPlan.toLowerCase()))
				if(Integer.parseInt(companyDetails.get(planName)) == Integer.parseInt(usedSeats))
					flag=true;
		}
		return flag;

	}
	
	/**
	 * This method will disable device lock and erase toggle
	 * @return boolean
	 * @throws Exception
	 */
	public boolean disableDeviceLockAndEraseToggle(String languageCode) throws Exception {
		clickOnElementsOfCompaniesDetailsPage("lockAndEraseEdit");
		if (getAttributesOfCompaniesDetailsPage("lockAndEraseToggle", "aria-checked").equals(CommonVariables.TRUE)) {
			Thread.sleep(2000);
			clickOnElementsOfCompaniesDetailsPage("lockAndEraseToggle");
			waitForElementsOfCompaniesDetailsPage("tableOverlay");
			clickOnElementsOfCompaniesDetailsPage("saveapprover");
			waitForElementsOfCompaniesDetailsPage("toastNotification");
			return getTextOfCompaniesDetailsPage("toastNotification").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "companies.details.lockanderase.success.update.no_of_approvals"));
		} else {
			LOGGER.info("Device lock and erase toggle is already off");
			//clickOnElementsOfCompaniesDetailsPage("cancelDeclineButton");
			waitForElementsOfCompaniesDetailsPage("cancelsettingsbutton");
			clickByJavaScriptOnCompaniesDetailsPage("cancelsettingsbutton");
			return true;
		}

	}
	
	/**
	 * This method will enable device lock and erase toggle
	 * @return boolean
	 * @throws Exception
	 */
	public boolean enableDeviceLockAndEraseToggle(String languageCode) throws Exception {
		clickOnElementsOfCompaniesDetailsPage("lockAndEraseEdit");
		if (getAttributesOfCompaniesDetailsPage("lockAndEraseToggle", "aria-checked").equals(CommonVariables.FALSE)) {
			clickOnElementsOfCompaniesDetailsPage("lockAndEraseToggle");
			waitForElementsOfCompaniesDetailsPage("tableOverlay");
			enterTextForCompaniesDetailsPage("approverAdd", "4");
			clickOnElementsOfCompaniesDetailsPage("saveapprover");
			waitForElementsOfCompaniesDetailsPage("toastNotification");
			return getTextOfCompaniesDetailsPage("toastNotification").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "companies.details.lockanderase.success.update.no_of_approvals"));
		} else {
			LOGGER.info("Device lock and erase toggle is already ON");
			enterTextForCompaniesDetailsPage("approverAdd", "4");
			clickOnElementsOfCompaniesDetailsPage("saveButton");
			return true;
		}
	}
	
	/**
	 * This is a method to wait until an element is invisible and return boolean
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public final boolean waitUntilElementInvisibleOfCompanyDetailsPage(String key) throws Exception {
		return verifyElementIsinvisibile(companiesDetailsPageProperties.getProperty(key));

	}
	
	
	/**
	 * PlanList compare with array of plans list
	 * @param key
	 * @param subTypes
	 * @return
	 * @throws Exception
	 */
	public final boolean comparePlansListOfCompanyDetailPage(String key, ArrayList<String> subTypes) throws Exception {
		return comparePlansList(companiesDetailsPageProperties.getProperty(key), subTypes);
	}
	
	
	/*
	 * PlanList compare with  company details add subscription
	 */
	public final boolean comparePlansList(String key, ArrayList<String> array) throws Exception {
		ArrayList<String> menuTabs = new ArrayList<String>();
		List<WebElement> menuList = getElementsTillAllElementsPresent(key);		
		for (WebElement listItem : menuList) {
			String value = listItem.getText();
			menuTabs.add(value);
		}
		if (array.containsAll(menuTabs)) {
			return true;
		}

		return false;
	}
	
	
	/**
	 * HP-Dex plans compare with array of plans list
	 * @param key
	 * @param subTypes
	 * @return
	 * @throws Exception
	 */
	public final boolean comparePlansOfHPDexInCompanyDetailPage(String key, ArrayList<String> subTypes) throws Exception {
		return compareHPDexPlansList(companiesDetailsPageProperties.getProperty(key), subTypes);
	}
	
	/*
	 * HP-Dex plans compare with  company details plans list
	 */
	public final boolean compareHPDexPlansList(String key, ArrayList<String> array) throws Exception {
		ArrayList<String> menuTabs = new ArrayList<String>();
		List<WebElement> menuList = getElementsTillAllElementsPresent(key);		
		for (WebElement listItem : menuList) {
			String value = listItem.getText();
			if (value.contains("(")) {
				String[] parts = value.split("\\(");
				menuTabs.add(parts[0].trim());
			}				
		}
		if (array.containsAll(menuTabs)) {
			return true;
		}

		return false;
	}
	
	/**
	 * This is a method to verify if the selected value from searchbox
	 * 
	 * @param LanguageCode - language code
	 * @param textKey - locator of search box
	 * @param text - text to be entered
	 * @param emptyTextKey - locator for "no items available" message on popup
	 * @param listKey - locator of list of items filtered on popup
	 * @param assignToPopupKey - locator to get assign to value on popup
	 * @param dropdownkey - locator for drop down arrow on popup
	 * @param cancelButtonKey - locator for cancel button on popup
	 * @return - String value of the selected option
	 */
	public final boolean selectedValueFromSearchBoxInsidePopup(String LanguageCode, String textKey, String text, String emptyTextKey, String listKey, String assignToPopupKey, String dropdownkey, String cancelButtonKey) {
		try {
			enterText(companiesDetailsPageProperties.getProperty(textKey), text);
			sleeper(5000);
			String empty_text = null;
			String entered_text = null;
			boolean str = false;

			if (verifyElementIsPresent(companiesDetailsPageProperties.getProperty(emptyTextKey))) {
				empty_text = getTextBy(companiesDetailsPageProperties.getProperty(emptyTextKey));
				String[] emptytext = getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").split("%");
				if (empty_text.contains(emptytext[0].concat(text))) {
					clickOnElementsOfCompaniesDetailsPage(dropdownkey);
					sleeper(2000);
					clickOnElementsOfCompaniesDetailsPage(cancelButtonKey);

				}
			} else {
				List<WebElement> elements = getElementsTillAllElementsPresent(companiesDetailsPageProperties.getProperty(listKey));
				for (WebElement webElement : elements) {
					if (webElement.getText().toLowerCase().contains(text.toLowerCase())) {
						elements.get(0).click();
						entered_text = getTextBy(companiesDetailsPageProperties.getProperty(assignToPopupKey));
						if(entered_text.contains(text))
							str=true;
						else
						str = false;
						break;
					}
				}
			}
			return str;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectedValueFromSearchBoxInsidePopup " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This method is for setting Support contacts display table configuration to default.
	 * 
	 * @throws Exception
	 */

	public final void resetSupportContactsTableConfiguration() throws Exception {
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		tableConfigurationPage = tableConfigurationPage.getInstance();
		sleeper(1000);
		tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPage("Settingsbutton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("Settingsbutton");
		sleeper(3000);
		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("resettodefaultoption")){
			if(tableConfigurationPage.verifyElementIsEnableOftableConfigurationPage("resettodefaultoption")){
				tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefaultoption");
			}
		}
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(4000);
		
	}
	
	public final boolean verifyDropdownIsOpenOfCompaniesDetailsPage(String key) throws Exception {
		return waitUntillElementIsPresent(companiesDetailsPageProperties.getProperty(key));
	}
	public final void enterTextwithoutclearOnCompanyPage(String key,String text) throws Exception {
		enterTextwithoutclear(companiesDetailsPageProperties.getProperty(key), text);
	}
	
	/**
	 * This method verifies Tenant details created. 
	 * @param LanguageCode
	 * @param companyDetails - Test data for company creation.
	 * @return true if details match.
	 */
	public final boolean verifyTenantDetailsCreated(String LanguageCode, HashMap<String, String> companyDetails){
		HashMap<String, String>fetchCompanyDetails = new HashMap<String, String>();
		try {
			gotoOverviewTab();
			sleeper(1000);
			if(waitForElementsOfCompaniesDetailsPage("companyName")) {
			fetchCompanyDetails.put("CompanyName", getTextOfCompaniesDetailsPage("companyName"));
			fetchCompanyDetails.put("ITAdminEmail", getTextOfCompaniesDetailsPage("companyPrimaryAdministratorEmail"));
			fetchCompanyDetails.put("CompanyCountry", getTextOfCompaniesDetailsPage("companyCountry"));
			fetchCompanyDetails.put("CompanyAddressLine1", getTextOfCompaniesDetailsPage("companyAddressLine1"));
			fetchCompanyDetails.put("CompanyAddressState", getTextOfCompaniesDetailsPage("companyAddressState"));
			fetchCompanyDetails.put("CompanyAddressCityZip", getTextOfCompaniesDetailsPage("companyAddressCityZip"));
			}else {
				LOGGER.info("Company name is not visible.");
				return false;
			}
			sleeper(300);
			gotoPlansTab();
			fetchCompanyDetails = getPlanNameAndUsedSeats(LanguageCode, fetchCompanyDetails);
			
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyTenantDetailsCreated " + e.getMessage()));
			return false;
		}
				
		return companyDetails.equals(fetchCompanyDetails);
	}
	
}

