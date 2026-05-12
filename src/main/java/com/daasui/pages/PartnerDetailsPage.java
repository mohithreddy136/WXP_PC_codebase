package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PartnerDetailsPage extends CommonMethod {

	private ObjectReader partnerDetailsPagePropertiesReader = new ObjectReader();
	private Properties partnerDetailsPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private PartnerDetailsPage instance;

	public PartnerDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (PartnerDetailsPage.class) {
				if (instance == null) {
					instance = new PartnerDetailsPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public PartnerDetailsPage(WebDriver driver) throws IOException {
		partnerDetailsPageProperties = partnerDetailsPagePropertiesReader.getObjectRepository("PartnerDetailsPageV3");
	}

	/**
	 * This method is the method to verify if an element is present on partner details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is present
	 */
	public final boolean verifyElementsOfPartnerDetailsPage(String key) {
		try {
			return verifyElementIsPresent(partnerDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfPartnerDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method to wait for any element on the partner list page untill it is visible
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is visible
	 */
	public final boolean waitForElementsOfPartnerDetailsPage(String key) {
		try {
			return verifyElementIsVisible(partnerDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfPartnerDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to match text on an element ehich is present on partner details page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be compared
	 * @return - boolean value of whether both the texts match
	 */
	public final boolean matchTextOfPartnerDetailsPage(String key, String textToMatch) {
		try {
			return verifyTextPresentOnElement(partnerDetailsPageProperties.getProperty(key), textToMatch);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method matchTextOfPartnerDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * THis is a method to verify if an element on partner details page is enabled
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is enabled
	 */
	public final boolean verifyElementIsEnableOfPartnerDetailsPage(String key) {
		try {
			return verifyElementIsEnable(partnerDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfPartnerDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if an element on partner details page is selected
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is selected
	 */
	public final boolean verifyElementIsSelectedOfPartnerDetailsPage(String key) {
		try {
			return verifyElementIsSelected(partnerDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsSelectedOfPartnerDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to get text present on an element on partner details page
	 * 
	 * @param key - locator of the element
	 * @return - string value of the text present on the element
	 */
	public final String getTextOfPartnerDetailsPage(String key) {
		try {
			return getTextBy(partnerDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfPartnerDetailsPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a metod to get attribute of an element present on partner details page
	 * 
	 * @param key - locator of the element
	 * @param desiredValue - desired attribute name
	 * @return - value of the attribute as a string
	 */
	public final String getAttributesOfPartnerDetailsPage(String key, String desiredValue) {
		try {
			return getAttribute(partnerDetailsPageProperties.getProperty(key), desiredValue);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfPartnerDetailsPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to click on element present on partner details page
	 * 
	 * @param key - locator of the element
	 */
	public final void clickOnElementsOfPartnerDetailsPage(String key) {
		try {
			if(this.verifyElementIsVisible(partnerDetailsPageProperties.getProperty(key))) {
				click(partnerDetailsPageProperties.getProperty(key));
			}
			
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementsOfPartnerDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to enter text on a text field present on partner details page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be entered
	 */
	public final void enterTextForPartnerDetailsPage(String key, String textToMatch) {
		try {
			enterText(partnerDetailsPageProperties.getProperty(key), textToMatch);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForPartnerDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to verify if an element on partner details page is clickable
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is clickable
	 */
	public final boolean verifyElementIsClickableOfPartnerDetailsPage(String key) {
		try {
			return verifyElementIsClickable(partnerDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsClickableOfPartnerDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if an element on partner details page is clickable
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is clickable
	 */
	public final void clearTextFromPartnerDetailsPageTextField(String key) {
		try {
			clearTextRefresh(partnerDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsClickableOfPartnerDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to click on an element present on partner details page using javascript
	 * 
	 * @param key - locator of the element
	 */
	public final void clickByJavaScriptOnPartnerDetailsPage(String key) {
		try {
			clickByJavaScript(partnerDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnPartnerDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This method is used to verify primary administrator of a pertner on administrators tile of partner details page
	 * 
	 * @param administratorsListKey - Locator of list of administrators
	 * @param primaryAdministratorsListKey - Locator of list of primary administrator
	 * @return
	 */
	public final boolean verifyPrimaryAdministratorOfPartnerOnAdministratorsTile(String administratorsNameListKey, String primaryAdministratorsListKey) {
		try {
			HashMap<String, String> primaryAdminsitratorInfo = new HashMap<String, String>();
			ArrayList<String> administratorsNameList = getTextOfListOfPartnerDetailsPage(administratorsNameListKey);
			ArrayList<String> primaryAdministratorsList = getTextOfListOfPartnerDetailsPage(primaryAdministratorsListKey);
			for (int primaryAdministratorsListCounter = 0; primaryAdministratorsListCounter < primaryAdministratorsList.size();) {
				for (int administratorsListCounter = primaryAdministratorsListCounter; administratorsListCounter < administratorsNameList.size();) {
					primaryAdminsitratorInfo.put(primaryAdministratorsList.get(primaryAdministratorsListCounter), administratorsNameList.get(administratorsListCounter));
					primaryAdministratorsListCounter++;
					break;
				}
			}
			return matchTextOfPartnerDetailsPage("primaryAdministratorName", primaryAdminsitratorInfo.get("yes"));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyPrimaryAdminsitratorOfPartnerOnAdministratorsTile " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to get the text of a list as a list itself
	 * 
	 * @param key - locator of element
	 * @return - arraylist of the text of all elements present in the list
	 */

	public final ArrayList<String> getTextOfListOfPartnerDetailsPage(String key) {
		try {
			return getTextOfList(partnerDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfListOfPartnerDetailsPage " + e.getMessage()));
			return null;
		}
	}

	public final String selectAnyValueFromDropdownOfPartnerDetailsPage(String key, int index) {
		try {
			return selectAnyValueFromDropdown(partnerDetailsPageProperties.getProperty(key), index);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfListOfPartnerDetailsPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to wait untill an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfPartnerDetailsPage(String key) {
		try {
			verifyElementIsinvisibile(partnerDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfPartnerDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is the method to select second option from the dropdown if the first one is already selected
	 * 
	 * @param dropdownListKey - Locator of dropdown elements
	 * @param valueOnDropdown - Already present value on the dropdown
	 * @return - String value of the option selecetd from the dropdown
	 */
	public final String selectValueFromDropdownOnPartnerDetailspage(String dropdownListKey, String text) {
		try {
			return selectValueOnPopup(partnerDetailsPageProperties.getProperty(dropdownListKey), text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectValueFromDropdownOnPartnerDetailspage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is the method to select the element based on text from a drop down
	 * 
	 * @param dropdownListKey: List of values in drop down.
	 * @param elementText: Element text which is to be clicked.
	 * @param dropdownBox: Drop down box locator.
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean selectSpecificValueFromDropdownOnPartnerDetailspage(String dropdownListKey, String elementText, String dropdownBox) {
		try {
			return selectTextValueFromDropdown(partnerDetailsPageProperties.getProperty(dropdownListKey), elementText, partnerDetailsPageProperties.getProperty(dropdownBox));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectValueFromDropdownOnPartnerDetailspage " + e.getMessage()));
			return false;
		}
	}
	
	/** 
	 * This is a method to select date from calendar filter
	 * @param date - current date
	 * @param monthKeyCurrent - locator of current month
	 * @param rightArrowKey - locator for right arrow key on calendar
	 * @param daysOnCurrentMonthKey - locator for days on current month 
	 */
	public final void selectDateFromCalenderOnPartnerDetailpage(String date, String monthKeyCurrent, String rightArrowKey, String daysOnCurrentMonthKey) {
		try {
			selectDateFromCalenderDatePickerContainer(date, partnerDetailsPageProperties.getProperty(monthKeyCurrent), partnerDetailsPageProperties.getProperty(rightArrowKey), partnerDetailsPageProperties.getProperty(daysOnCurrentMonthKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectDateFromCalenderOnCompanyDetailpage " + e.getMessage()));
		}
	}
	
	
	/** This method is used to create Subscription Contract.
	 * 
	 * @param LanguageCode
	 * @contractID - ID of Contract
	 * @return
	 * @throws Exception */
	public final boolean createSubscriptionContract(String LanguageCode, String contractID, String country)
			throws Exception {
		boolean flag = false;
		//Commenting the code since "auto-populate-currency" toggle is disabled on staging.
		//Will uncomment the code once toggle is turned ON again on stage.
		//NFR Story 860332: [Subscription] Toggle off feature toggle "auto-populate-currency" for all on Production.
//		ArrayList<String> arrCountry = new ArrayList<String>(
//				Arrays.asList(
//						(getEnvironmentSpecificData(System.getProperty("environment"),"PARTNER_COUNTRY_CONTRACT_CN")), 
//						(getEnvironmentSpecificData(System.getProperty("environment"),"PARTNER_COUNTRY_CONTRACT_IN"))
//						)
//				);	
//		
//		ArrayList<String> arrCurrencyCn = new ArrayList<String>(
//				Arrays.asList(
//						(getEnvironmentSpecificData(System.getProperty("environment"),"CURRENCY_CONTRACT_CAD")), 
//						(getEnvironmentSpecificData(System.getProperty("environment"),"CURRENCY_CONTRACT_USD"))
//						)
//				);	
//		
//		ArrayList<String> arrCurrencyIn = new ArrayList<String>(
//				Arrays.asList(
//						(getEnvironmentSpecificData(System.getProperty("environment"),"CURRENCY_CONTRACT"))
//						)
//				);	
		
		
		clickOnElementsOfPartnerDetailsPage("addNewContractBtn");
		sleeper(5000);
		LOGGER.info("Clicked on Add contract button.");
		if (getTextOfPartnerDetailsPage("contractModelTitle")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.details.subscription.add_title"))) {
			enterTextForPartnerDetailsPage("contractIdInput", contractID);
			scrollToTop();
			sleeper(2000);
			waitForElementsOfPartnerDetailsPage("startDataPicker");
			clickOnElementsOfPartnerDetailsPage("startDataPicker");
			waitForElementsOfPartnerDetailsPage("todayButton");
			clickOnElementsOfPartnerDetailsPage("todayButton");
			sleeper(1000);
			clickOnElementsOfPartnerDetailsPage("todayButton");

			waitForElementsOfPartnerDetailsPage("expiresDatepicker");
			clickOnElementsOfPartnerDetailsPage("expiresDatepicker");
			sleeper(5000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			waitForElementsOfPartnerDetailsPage("monthKeyCurrent");
			selectDateFromCalenderOnPartnerDetailpage(addDaysToCurrentDate(2), "monthKeyCurrent",
					"endDateDialogRightArrow", "daysOnCurrentMonthKey");
			sleeper(2000);
					
			
			// verify currecy dropdown is not editable , if country is not selected.
			if ((getTextOfPartnerDetailsPage("countryDropdownValueLabel").isEmpty() == true)) {

				if (getAttributesOfPartnerDetailsPage("contractCurrencyDropdown", "aria-disabled").equals(CommonVariables.FALSE))
				{
					LOGGER.info("Verification of currency dropwon disable is Successful");
				}
				else
				{
					flag = false;
					LOGGER.error("currency dropdown is editable");
				}
			} 

//			if (country.isEmpty() == true)
//			{
//				ArrayList<String> arrCurrencyList = new ArrayList<String>();
//
//				for (int i = 0; i < arrCountry.size(); i++) {
//				clickOnElementsOfPartnerDetailsPage("countryDropdown");
//    			sleeper(1000);
//					selectSpecificValueFromDropdownOnPartnerDetailspage("contractCountryList", arrCountry.get(i),
//							"countryDropdown");
//					clickOnElementsOfPartnerDetailsPage("currencyDropdownArrow");
//					sleeper(3000);
//
//					if (i == 0) {
//						arrCurrencyList = arrCurrencyCn;
//					} else {
//						arrCurrencyList = arrCurrencyIn;
//					}
//
//					if (compareTwoList(partnerDetailsPageProperties.getProperty("currencyList"),
//							arrCurrencyList) == true) {
//						LOGGER.info("Verification of currency dropwon values are Successfully");
//						clickOnElementsOfPartnerDetailsPage("currencyList");
//						sleeper(1000);
//					} else {
//						LOGGER.error("Verification of currency dropwon values are fail");
//						flag = false;
//					}
//
//				}
//			}
//			
//			else 
//			{
			 	clickOnElementsOfPartnerDetailsPage("countryDropdown");
    			sleeper(1000);
				selectSpecificValueFromDropdownOnPartnerDetailspage("contractCountryList", country, "countryDropdown");
				sleeper(1000);
//			}
			
			enterTextForPartnerDetailsPage("paymentTerms", "Net 30 Days");
			sleeper(1000);
			clickOnElementsOfPartnerDetailsPage("contractSaveBtn");
			waitForElementsOfPartnerDetailsPage("subscriptionDesc");
			clickOnElementsOfPartnerDetailsPage("plandropdownArrow");
			enterTextForPartnerDetailsPage("planSearchOnPopup", CommonVariables.PLAN_PROACTIVE);
			clickOnElementsOfPartnerDetailsPage("planSelectOnPopup");
			enterTextForPartnerDetailsPage("monthlyCostPerSeat", "10");
			clickOnElementsOfPartnerDetailsPage("contractSaveBtn");
			waitForElementsOfPartnerDetailsPage("toastNotification");
			if (getTextOfPartnerDetailsPage("toastNotification").equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_success").replace("{field}",
							getTextLanguage(LanguageCode, "daas_ui", "partner.details.subscription.contract")))) {
				LOGGER.info("Subscription Contract added successfully."); 
				if (verifyCreatedSubscriptionContractInList(contractID)) {
					flag = true;
				}
			} else {
				LOGGER.error("Error occured,Subscription Contract failed to add");
			} 
		} else {
			LOGGER.error("Add Subscription Contract model did not open successfully");
			flag = false;
		}
		return flag;
	}
	
	/**
	 * This method is used to verify created Subscription Contract in list page.
	 * @param contractID- ID of Subscription contract
	 * @return
	 * @throws Exception
	 */
	public boolean verifyCreatedSubscriptionContractInList(String contractID) throws Exception {
		boolean flag = false;
		try {
			refreshPage();
			waitForPageLoaded();
			waitForElementsOfPartnerDetailsPage("subscriptionContractsTitle");
			sleeper(3000);
			scrollByCoordinatesofAPage(0, 250);
			List<WebElement> listOfContracts = getElementsOfPartnerDetailsPage("createdContractList");
			if (listOfContracts.size() > 0) {
				for (int i = 0; i < listOfContracts.size(); i++) {
					if (listOfContracts.get(i).getText().equalsIgnoreCase(contractID)) {
						flag = true;
						LOGGER.info("Created contract got reflected on list.");
						break;
					}
				}
				if (!flag) {
					LOGGER.error("Created contract did not reflect on list.");
				}
			} else {
				LOGGER.error("No contract is present in list.");
			}
		} catch (Exception e) {
			LOGGER.error("Exception in verifying created contract on list" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	/**
	 * @param tenantID - tenant id of a Root
	 * @param contractID - ID of Subscription contract to remove
	 * @return - boolean
	 * @throws Exception
	 */
	public final boolean removeContractUsingAPI(String tenantID, String contractID) throws Exception {
		Response response = null;
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body("{}");
		response = httpRequest.delete(getEnvironmentSpecificData(System.getProperty("environment"), "ROLE_ID_URL") + CompaniesVariables.REMOVE_CONTRACT_ID + tenantID + "/" + contractID);

		if (response.getStatusCode() == 204) {
			return true;
		} else {
			LOGGER.error("Contract deletion failed due to reason " + response.asString());
			return false;
		}
	}
	
	/**
	 * This method verify Subscription Contract toggles on overview page
	 */
	public final void verifySubscriptionContractToggle() {
		try {
			if (getAttributesOfPartnerDetailsPage("onboardPartnerToggle", "aria-checked").equals(CommonVariables.FALSE)) {
				clickOnElementsOfPartnerDetailsPage("onboardPartnerToggle");
				waitForElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
				LOGGER.info("Onboard Authorized Partner Toggle is enabled");
			} else {
				LOGGER.info("Onboard Authorized Partner Toggle is already enabled");
			}
			if (getAttributesOfPartnerDetailsPage("subscriptionAuthorizedToggle", "aria-checked").equals(CommonVariables.FALSE)) {
				clickOnElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
				sleeper(1000);
				clickOnElementsOfPartnerDetailsPage("subscriptionContractAllow");
				sleeper(1000);
				waitForElementsOfPartnerDetailsPage("subscriptionContractsTitle");
				LOGGER.info("Subscription Authorized Toggle is enabled");
			} else {
				LOGGER.info("Subscription Authorized Toggle is already enabled");
				clickOnElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
				waitUntilElementIsInvisibleOfPartnerDetailsPage("subscriptionContractsTitle");
				LOGGER.info("Subscription Authorized Toggle is disabled");
				sleeper(3000);
				clickOnElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
				sleeper(1000);
				clickOnElementsOfPartnerDetailsPage("subscriptionContractAllow");
				sleeper(1000);
				waitForElementsOfPartnerDetailsPage("subscriptionContractsTitle");
				LOGGER.info("Subscription Authorized Toggle is enabled");
			}
		} catch (Exception e) {
			LOGGER.error("Exception thrown in verifySubscriptionContractToggle method :" + e.toString());
		}
	}
	
	public final void mouseHoverOnPartnerDetailPage(String key) throws Exception {
		mouseHover(partnerDetailsPageProperties.getProperty(key));
	}
	
	public final List<WebElement> getElementsOfPartnerDetailsPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(partnerDetailsPageProperties.getProperty(key));
	}

	public final void scrollOnPartnerDetailsPage(String key) throws Exception {
		scrollTillView(partnerDetailsPageProperties.getProperty(key));
	}
	
	/**
	 * This method verify Service Delivery Partner Toggle
	 */
	public final void verifyServiceDeliveryPartnerToggle() {
		try {
			if (getAttributesOfPartnerDetailsPage("serviceDeliveryToggle", "aria-checked").equals(CommonVariables.FALSE)) {
				clickOnElementsOfPartnerDetailsPage("serviceDeliveryToggle");
				sleeper(1000);
				waitForElementsOfPartnerDetailsPage("serviceDeliveryTitle");
				LOGGER.info("Service Delivery Partner Toggle is enabled");
			} else {
				LOGGER.info("Service Delivery Partner Toggle is already enabled");
			}
		} catch (Exception e) {
			LOGGER.error("Exception thrown in verifyServiceDeliveryPartnerToggle method :" + e.toString());
		}
	}
	
	/** This method is used to create Subscription Contract with Auto-Renew Checkbox.
	 * 
	 * @param LanguageCode
	 * @contractID - ID of Contract
	 * @return
	 * @throws Exception */
	public final boolean createSubscriptionContractAutoRenew(String LanguageCode, String contractID, String country)
			throws Exception {
		boolean flag = false;

		clickOnElementsOfPartnerDetailsPage("addNewContractBtn");
		sleeper(5000);
		LOGGER.info("Clicked on Add contract button.");
		if (getTextOfPartnerDetailsPage("contractModelTitle")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.details.subscription.add_title"))) {
			enterTextForPartnerDetailsPage("contractIdInput", contractID);
			scrollToTop();
			sleeper(2000);
			
			if (getTextOfPartnerDetailsPage("autoRenewText").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.details.subscription.auto_renew")))
			{
				LOGGER.info("Auto Renew string verified scuccessfully.");
			}
			else
			{
				flag = false;
				LOGGER.error("Auto Renew string is not present.");
			}
			waitForElementsOfPartnerDetailsPage("startDataPicker");
			clickOnElementsOfPartnerDetailsPage("startDataPicker");
			waitForElementsOfPartnerDetailsPage("endDateDialogRightArrow");
			clickOnElementsOfPartnerDetailsPage("endDateDialogRightArrow");
			sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			selectDateFromCalenderOnPartnerDetailpage(addDaysToCurrentDate(2), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
			sleeper(2000);
			
			//Check if expiration date gets disabled once auto renew checkbox is selected.
			if (getAttributesOfPartnerDetailsPage("expiresDatepicker", "disabled").equals(CommonVariables.TRUE))
			{
				LOGGER.info("Verification of Expiration date field disable is Successful");
			}
			else
			{
				flag = false;
				LOGGER.error("Expiration date is editable");
			}
			String expiryDate= getAttributesOfPartnerDetailsPage("expiresDatepicker", "value");
			if(expiryDate.contains(CommonVariables.FUTURE_YEAR))
			{
				LOGGER.info("Expiry Date is incremneted after selecting Auto renew checkbox.");
			}
			else
			{
				flag = false;
				LOGGER.error("Expiration date is not incremented after selecting Auto renew checkbox. ");
			}

			// verify currecy dropdown is not editable , if country is not selected.
			if ((getTextOfPartnerDetailsPage("countryDropdownValueLabel").isEmpty() == true)) {

				if (getAttributesOfPartnerDetailsPage("contractCurrencyDropdown", "aria-disabled").equals(CommonVariables.FALSE))
				{
					LOGGER.info("Verification of currency dropwon disable is Successful");
				}
				else
				{
					flag = false;
					LOGGER.error("currency dropdown is editable");
				}
			} 
			 	clickOnElementsOfPartnerDetailsPage("countryDropdown");
    			sleeper(1000);
				selectSpecificValueFromDropdownOnPartnerDetailspage("contractCountryList", country, "countryDropdown");
				sleeper(1000);
			
			enterTextForPartnerDetailsPage("paymentTerms", "Net 30 Days");
			sleeper(1000);
			clickOnElementsOfPartnerDetailsPage("contractSaveBtn");
			waitForElementsOfPartnerDetailsPage("subscriptionDesc");
			clickOnElementsOfPartnerDetailsPage("plandropdownArrow");
			enterTextForPartnerDetailsPage("planSearchOnPopup", CommonVariables.PLAN_PROACTIVE);
			clickOnElementsOfPartnerDetailsPage("planSelectOnPopup");
			enterTextForPartnerDetailsPage("monthlyCostPerSeat", "10");
			clickOnElementsOfPartnerDetailsPage("contractSaveBtn");
			waitForElementsOfPartnerDetailsPage("toastNotification");
			if (getTextOfPartnerDetailsPage("toastNotification").equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_success").replace("{field}",
							getTextLanguage(LanguageCode, "daas_ui", "partner.details.subscription.contract")))) {
				LOGGER.info("Subscription Contract added successfully."); 
				if (verifyCreatedSubscriptionContractInList(contractID)) {
					flag = true;
				}
			} else {
				LOGGER.error("Error occured,Subscription Contract failed to add");
			} 
		} else {
			LOGGER.error("Add Subscription Contract model did not open successfully");
			flag = false;
		}
		return flag;
	}
	
	
	/**
	 * @param crsid
	 * @throws Exception
	 */
	public final void enterCrsIdOnPartnerDetailsPage(String crsid)
			throws Exception {
		clickOnElementsOfPartnerDetailsPage("partnerDetailsUpArrowIcon");
		scrollToBottom();
		scrollOnPartnerDetailsPage("crsIdEditIcon");
		
		clickOnElementsOfPartnerDetailsPage("crsIdEditIcon");
		waitForElementsOfPartnerDetailsPage("crsIdAddModalWindowTextbox");
		enterTextForPartnerDetailsPage("crsIdAddModalWindowTextbox", crsid);

		clickByJavaScriptOnPartnerDetailsPage("crsIdAddModalWindowSaveButton");

		waitUntilElementIsInvisibleOfPartnerDetailsPage("crsIdAddModalWindowTextbox");
		waitForElementsOfPartnerDetailsPage("crsIdEditIcon");
		sleeper(2000);
		scrollToBottom();		
	}
}
