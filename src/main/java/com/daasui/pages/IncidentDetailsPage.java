package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CompanyPin;
import com.basesource.utils.EnrollFakeDevice;
import com.basesource.utils.ObjectReader;

/**
 * This class includes all the methods related to incident details page test cases
 */
public class IncidentDetailsPage extends CommonMethod {
	private Properties selectedLanguageProperties;
	private ObjectReader incidentDetailsPagePropertiesReader = new ObjectReader();
	private Properties incidentDetailsPagePropertiesPageProperties;

	private IncidentDetailsPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	public IncidentDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (IncidentDetailsPage.class) {
				if (instance == null) {
					instance = new IncidentDetailsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public IncidentDetailsPage(WebDriver driver) throws IOException {
		incidentDetailsPagePropertiesPageProperties = incidentDetailsPagePropertiesReader.getObjectRepository("IncidentDetailsPageV3");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = incidentDetailsPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * This is a method to match text present on element
	 * 
	 * @param key - locator of element
	 * @param Text - text to be matched
	 * @return - boolean value of whether the text is matched or not
	 */
	public final boolean matchTextOfIncidentDetailsPage(String key, String Text) {
		try {
			return verifyTextPresentOnElement(incidentDetailsPagePropertiesPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method matchTextOfIncidentDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if element is present
	 * 
	 * @param key - locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementOfIncidentDetailsPage(String key) {
		try {
			return verifyElementIsPresent(incidentDetailsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementOfIncidentDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to click on any element
	 * 
	 * @param key - locator of element
	 */
	public final void clickOnElementOfIncidentDetailsPage(String key) {
		try {
			click(incidentDetailsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementOfIncidentDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to click on any element using javascript
	 * 
	 * @param key - locator of element
	 */
	public final void clickByJavaScriptOnIncidentDetailsPage(String key) {
		try {
			clickByJavaScript(incidentDetailsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnIncidentDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to enter text in a search box
	 * 
	 * @param key - locator of search box
	 * @param Text - text to be element
	 */
	public final void enterTextForIncidentDetailsPage(String key, String Text) {
		try {
			enterText(incidentDetailsPagePropertiesPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForIncidentDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to get Text present on an element
	 * 
	 * @param key - locator of element
	 * @return - String value of the text
	 */
	public final String getTextOfElementOnIncidentDetailsPage(String key) {
		try {
			return getTextBy(incidentDetailsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfElementOnIncidentDetailsPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify if an element is enable
	 * 
	 * @param key - locator of element
	 * @return - boolean value of whether the element is enabled or not
	 */
	public final boolean verifyElementIsEnableOfIncidentDetailsPage(String key) {
		try {
			return verifyElementIsEnable(incidentDetailsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfIncidentDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method to verify if element is clickable
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is clickable or not
	 */
	public final boolean verifyElementIsClickableOfIncidentDetailsPage(String key) {
		try {
			return verifyElementIsClickable(incidentDetailsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsClickableOfIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to wait for an element to be visible
	 * 
	 * @param key - locator of element
	 * @return - boolean value of whether the element is visible after the wait
	 */
	public final boolean waitForElementOfIncidentDetailsPage(String key) {
		try {
			return verifyElementIsVisible(incidentDetailsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementOfIncidentDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to get webelement on incident details page
	 * 
	 * @param key - locator of element
	 * @return - web element present in the locator
	 */
	public final WebElement getElementOfIncidentDetailsPage(String key) {
		try {
			return getElement(incidentDetailsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementOfIncidentDetailsPage " + e.getMessage()));
			return null;
		}

	}

	/**
	 * This is a method to get details of an incident from incident details page
	 * 
	 * @return - arraylist of the details of incident
	 */
	public final ArrayList<String> getDetailsOfIncident() {
		try {
			ArrayList<String> incidentDetailsPageInfo = new ArrayList<>();
			incidentDetailsPageInfo.add(getTextBy(incidentDetailsPagePropertiesPageProperties.getProperty("incidentId")));
			incidentDetailsPageInfo.add(getTextBy(incidentDetailsPagePropertiesPageProperties.getProperty("currentIncidentType")));
			incidentDetailsPageInfo.add(getTextBy(incidentDetailsPagePropertiesPageProperties.getProperty("currentIncidentSubType")));
			incidentDetailsPageInfo.add(getTextBy(incidentDetailsPagePropertiesPageProperties.getProperty("currentIncidentPriority")));
			incidentDetailsPageInfo.add(getTextBy(incidentDetailsPagePropertiesPageProperties.getProperty("currentIncidentStatus")));
//			incidentDetailsPageInfo.add(getTextBy(incidentDetailsPagePropertiesPageProperties.getProperty("incidentDetailsPageTitle")));
			incidentDetailsPageInfo.add(getTextBy(incidentDetailsPagePropertiesPageProperties.getProperty("currentCreatedOn")));
			return incidentDetailsPageInfo;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getDetailsOfIncident " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to select value from a dropdown
	 * 
	 * @param editButtonKey - locator of edit button
	 * @param dropdownButtonKey - locator of dropdown arrow
	 * @param dropdownListKey - locator of list of options on dropdown
	 * @param submitButtonKey - locator of submit button
	 * @return - String value of the option selected
	 */
	public final String selectValueFromDropdownInPopup(String editButtonKey, String dropdownButtonKey, String dropdownListKey, String valueOnDropdown, String submitButtonKey) {
		try {
			clickOnElementOfIncidentDetailsPage(editButtonKey);
			clickOnElementOfIncidentDetailsPage(dropdownButtonKey);
			String text = selectValueOnPopup(incidentDetailsPagePropertiesPageProperties.getProperty(dropdownListKey), valueOnDropdown);
			clickOnElementOfIncidentDetailsPage(submitButtonKey);
			return text;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectValueFromDropdownInPopup " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to select value from type dropdown
	 * 
	 * @param editButtonKey - locator of edit button
	 * @param dropdownTypeButtonKey - locator of type dropdown arrow
	 * @param dropdownSubTypeButtonKey - llocator of subtype dropdown arrow
	 * @param dropdownListKey - locator of list of options on dropdown
	 * @param submitButtonKey - locator of submit button
	 * @return - list of the values of type and subtype selected from dropdown
	 */
	public final ArrayList<String> selectValueFromDropdownInTypePopup(String editButtonKey, String dropdownTypeButtonKey, String dropdownSubTypeButtonKey, String dropdownListKey, String typeValueOnPopup, String subtypeValueOnPopup, String submitButtonKey) {
		try {
			ArrayList<String> incidentTypeAndSubtypeInfo = new ArrayList<String>();
			clickOnElementOfIncidentDetailsPage(editButtonKey);
			clickOnElementOfIncidentDetailsPage(dropdownTypeButtonKey);
			incidentTypeAndSubtypeInfo.add(selectValueOnPopup(incidentDetailsPagePropertiesPageProperties.getProperty(dropdownListKey), typeValueOnPopup));
			clickOnElementOfIncidentDetailsPage(dropdownSubTypeButtonKey);
			incidentTypeAndSubtypeInfo.add(selectValueOnPopup(incidentDetailsPagePropertiesPageProperties.getProperty(dropdownListKey), subtypeValueOnPopup));
			clickOnElementOfIncidentDetailsPage(submitButtonKey);
			return incidentTypeAndSubtypeInfo;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectValueFromDropdownInTypePopup " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify the options present on dropdown
	 * 
	 * @param key - the locator of the list of options
	 * @param optionsForStatusOnDropdown - arraylist of the options expected
	 * @return - boolean value of whether both the arraylists match
	 */
	public final boolean verifyOptionsOnDropdownForIncidentDetailsPage(String key, ArrayList<String> optionsForStatusOnDropdown) {
		try {
			return compareTwoList(incidentDetailsPagePropertiesPageProperties.getProperty(key), optionsForStatusOnDropdown);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyOptionsOnDropdownForIncidentDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to select first option from dropdown
	 * 
	 * @param dropdownListKey - locator of list of options available in dropdown
	 * @return - String value of the selected option from dropdown
	 */
	public final String selectFirstOptionFromDropdownOnIncidentDetailsPage(String dropdownListKey) {
		try {
			return selectFirstValueFromDropdown(incidentDetailsPagePropertiesPageProperties.getProperty(dropdownListKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectFirstOptionFromDropdownOnIncidentDetailsPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is the method to select second option from the dropdown if the first one is already selected
	 * 
	 * @param dropdownListKey - Locator of dropdown elements
	 * @param valueOnDropdown - Already present value on the dropdown
	 * @return - String value of the option selecetd from the dropdown
	 */
	public final String selectOptionFromDropdownOnIncidentDetailsPage(String dropdownListKey, String valueOnDropdown) {
		try {
			return selectValueOnPopup(incidentDetailsPagePropertiesPageProperties.getProperty(dropdownListKey), valueOnDropdown);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectOptionFromDropdownOnIncidentDetailsPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify the value selected after filtering from searchbox present inside a popup
	 * 
	 * @param LanguageCode - language code
	 * @param textKey - locator of search box
	 * @param text - text to be entered
	 * @param emptyTextKey - locator of "no items available" message
	 * @param listKey - locator of the list of items after filtering
	 * @param popupKey - locator for the selected value on popup
	 * @param dropdownkey - locator of dropdown arrow key
	 * @param cancelButtonKey - locator for cancel button
	 * @return - String value of selected option
	 */
	public final String selectedValueFromSearchBoxInsidePopup(String LanguageCode, String textKey, String text, String emptyTextKey, String listKey, String popupKey, String dropdownkey, String cancelButtonKey) {
		try {

			enterText(incidentDetailsPagePropertiesPageProperties.getProperty(textKey), text);
			sleeper(5000);
			String empty_text = null;
			String entered_text = null;
			String str = null;

			if (verifyElementIsPresent(incidentDetailsPagePropertiesPageProperties.getProperty(emptyTextKey))) {
				empty_text = getTextBy(incidentDetailsPagePropertiesPageProperties.getProperty(emptyTextKey));
				if (empty_text.contains(getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults"))) {
					LOGGER.info("Entered text doesn't match");
					clickOnElementOfIncidentDetailsPage(dropdownkey);
					clickOnElementOfIncidentDetailsPage(cancelButtonKey);
				}
			} else {
				List<WebElement> elements = getElementsTillAllElementsPresent(incidentDetailsPagePropertiesPageProperties.getProperty(listKey));
				for (WebElement webElement : elements) {

					System.out.println(webElement.getText());
					if (webElement.getText().contains(text)) {
						entered_text = webElement.getText();
						webElement.click();
						str = entered_text.substring(0, entered_text.indexOf("<")).trim();
					}
				}
			}
			return str;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectedValueFromSearchBoxInsidePopup " + e.getMessage()));
			return null;
		}
	}
	
	/**
	 * This is a method to verify the value selected after filtering from searchbox present inside change device popup
	 * 
	 * @param LanguageCode - language code
	 * @param textKey - locator of search box
	 * @param text - text to be entered
	 * @param emptyTextKey - locator of "no items available" message
	 * @param listKey - locator of the list of items after filtering
	 * @param popupKey - locator for the selected value on popup
	 * @param dropdownkey - locator of dropdown arrow key
	 * @param cancelButtonKey - locator for cancel button
	 * @return - String value of selected option
	 */
	public final String selectedValueFromSearchBoxInsideDevicePopup(String LanguageCode, String textKey, String text, String emptyTextKey, String listKey, String popupKey, String dropdownkey, String cancelButtonKey) {
		try {

			enterText(incidentDetailsPagePropertiesPageProperties.getProperty(textKey), text);
			sleeper(5000);
			String empty_text = null;
			String entered_text = null;
			String str = null;

			if (verifyElementIsPresent(incidentDetailsPagePropertiesPageProperties.getProperty(emptyTextKey))) {
				empty_text = getTextBy(incidentDetailsPagePropertiesPageProperties.getProperty(emptyTextKey));
				if (empty_text.contains(getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults"))) {
					LOGGER.info("Entered text doesn't match");
					clickOnElementOfIncidentDetailsPage(dropdownkey);
					clickOnElementOfIncidentDetailsPage(cancelButtonKey);
				}
			} else {
				List<WebElement> elements = getElementsTillAllElementsPresent(incidentDetailsPagePropertiesPageProperties.getProperty(listKey));
				for (WebElement webElement : elements) {

					if (webElement.getText().contains(text)) {
						entered_text = webElement.getText();
						webElement.click();
						str = entered_text.substring(0, entered_text.indexOf("-")).trim();
						break;
					}
				}
			}
			return str;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectedValueFromSearchBoxInsideDevicePopup " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify the search value on incident page inside popup
	 * 
	 * @param LanguageCode - language code
	 * @param textKey - locator of search box
	 * @param emptyTextKey - locator of "no items available" message
	 * @param listKey - locator of the list of items after filtering
	 * @return - boolean value of whether the search was successfull
	 */
	public final boolean verifySearchValueOnIncidentInsidePopup(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionalityInsidePopup(LanguageCode, incidentDetailsPagePropertiesPageProperties.getProperty(textKey), Text, incidentDetailsPagePropertiesPageProperties.getProperty(emptyTextKey), incidentDetailsPagePropertiesPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOnIncidentInsidePopup " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to wait for an element untill it is visible
	 * 
	 * @param key - locator of element
	 * @return - boolean value of whether the element is clickable
	 */
	public final boolean waitForElementToBeClickableOfIncidentDetailsPage(String key) {
		try {
			return verifyElementIsClickable(incidentDetailsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementToBeClickableOfIncidentDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to get text of all elements present in a list
	 * 
	 * @param key - locator of element
	 * @return - arraylist of the text present on the list of elements
	 */
	public final ArrayList<String> getTextsOfIncidentDetailsPage(String key) {
		try {
			return getTextOfList(incidentDetailsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfIncidentDetailsPage " + e.getMessage()));
			return null;
		}
	}

	/** This is a method to get text of element.
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public final String getTextOfIncidentDetailsPage(String key) throws Exception {
		return getTextBy(incidentDetailsPagePropertiesPageProperties.getProperty(key));
	}
	
	/**
	 * This method enrolls a fake device
	 * 
	 * @return - return hashmap which contains enrolled device details
	 */
	public final HashMap<String, String> enrollDevice(String languageCode) {
		try {
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			String companyName = companiesPage.getTextOfCompaniesPage("companyNameShow");
			String companyEmailId = companiesPage.getTextOfCompaniesPage("companyEmailId");
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			preferencesPage.clickOnElementsOfPreferencesPage("preferencesTab");
			CompanyPin companypin = new CompanyPin();
			String companyPin = companypin.generateCompanyPin(languageCode);
			HashMap<String, String> deviceDetails = EnrollFakeDevice.enrollFakeDeviceForIncident(companyName, companyPin, companyEmailId);
			return deviceDetails;
		} catch (Exception e) {
			LOGGER.error("exception caught in enrollDevice method: " + e.getMessage());
			return null;
		}

	}

	/**
	 * This method submits an incident using API for the device provide
	 * 
	 * @param languageCode- LanguageCode for localization testing
	 * @param deviceDetails - details of the device for which incident going to submit
	 * @return - Incident Id which is submitted using API
	 */
	public String submitIncident(String languageCode, HashMap<String, String> deviceDetails) {
		try {
			DeviceDetailsPage devicepage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			String incidentTitle = "Test Incident for enrolled device " + deviceDetails.get("deviceSerialNumber");
			String type = getTextLanguage(languageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account");
			String subtype = getTextLanguage(languageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.unknown");
			String incidentId = devicepage.submitCaseUsingAPI(type, subtype, incidentTitle, "", deviceDetails.get("host"), deviceDetails.get("tenantId"), deviceDetails.get("userAuthToken"), deviceDetails.get("deviceId"), deviceDetails.get("UserID"));
			return incidentId;
		} catch (Exception e) {
			LOGGER.error("exception caught in submitIncident method: " + e.getMessage());
			return null;
		}
	}

	/**
	 * This method validates device name neither hyperlink nor editable for the sales admin
	 * 
	 * @param incidentId - Incident display id whose device field going to validate
	 * @return - return true if device field is hyperlink and editable else return false
	 */
	public boolean validateDeviceNameHyperLink(String incidentId, String languageCode) {
		try {
			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

			incidentPage.clickOnElementsOfIncidentPage("tableConfigurationButton");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			incidentPage.clickOnElementsOfIncidentPage("tableConfigurationButton");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			incidentPage.verifySearchValueOnIncident(languageCode, "idSearchBox", incidentId, "noelementsdisplaytext", "idlist");
			incidentPage.clickOnElementsOfIncidentPage("incidentIdLink");
			WebElement element = getElementOfIncidentDetailsPage("deviceName");
			if (element.getAttribute("a") == null && !verifyElementOfIncidentDetailsPage("editDevieIcon")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("exception caught in validateDeviceNameHyperLink method: " + e.getMessage());
			return false;
		}

	}

	/*
	 * This method change assignee of the given incident and return comment added after asignee is changed throws Exception
	 */
	public String changeAssigneeOfIncident() throws Exception {
		try {
			verifyElementOfIncidentDetailsPage("editAssignedToPencilIcon");
			waitForElementToBeClickableOfIncidentDetailsPage("editAssignedToPencilIcon");
			sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
			clickOnElementOfIncidentDetailsPage("editAssignedToPencilIcon");
			verifyElementIsClickableOfIncidentDetailsPage("expandAssigntoDropdown");
			sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
			clickOnElementOfIncidentDetailsPage("expandAssigntoDropdown");
			verifyElementIsClickableOfIncidentDetailsPage("assignToPopupDropDownFirstOption");
			clickOnElementOfIncidentDetailsPage("assignToPopupDropDownFirstOption");
			verifyElementIsClickableOfIncidentDetailsPage("submitAssigntoButton");
			clickOnElementOfIncidentDetailsPage("submitAssigntoButton");
			waitForPageLoaded();
			sleeper(2000);// Need this sleeper as part if page is loading
			clickOnElementOfIncidentDetailsPage("commentTab");
			verifyElementOfIncidentDetailsPage("firstComment");
			waitForElementOfIncidentDetailsPage("firstComment");
			return getTextOfElementOnIncidentDetailsPage("firstComment");
		} catch (Exception ex) {
			LOGGER.error(("Exception occured in method changeAssigneeOfIncident " + ex.getMessage()));
			throw ex;
		}
	}

	/*
	 * This method change status of the given incident and return comment added after status is changed throws Exception
	 */
	public String changeStatusOfIncident() throws Exception {
		try {
			verifyElementOfIncidentDetailsPage("editStatus");
			waitForElementToBeClickableOfIncidentDetailsPage("editStatus");
			verifyElementIsClickableOfIncidentDetailsPage("editStatus");
			sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
			clickOnElementOfIncidentDetailsPage("editStatus");
			verifyElementIsClickableOfIncidentDetailsPage("editStatusPopup");
			sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
			clickOnElementOfIncidentDetailsPage("editStatusPopup");
			verifyElementIsClickableOfIncidentDetailsPage("selectInverstigationStatus");
			clickOnElementOfIncidentDetailsPage("selectInverstigationStatus");
			verifyElementIsClickableOfIncidentDetailsPage("submitStatusButton");
			clickOnElementOfIncidentDetailsPage("submitStatusButton");
			waitForPageLoaded();
			sleeper(2000);// Need this sleeper as part if page is loading
			clickOnElementOfIncidentDetailsPage("commentTab");
			verifyElementOfIncidentDetailsPage("secondComment");
			waitForElementOfIncidentDetailsPage("secondComment");
			return getTextOfElementOnIncidentDetailsPage("firstComment");
		} catch (Exception ex) {
			LOGGER.error(("Exception occured in method changeStatusOfIncident " + ex.getMessage()));
			throw ex;
		}
	}

	/*
	 * This method change priority of the given incident and return comment added after priority is changed throws Exception
	 */
	public String changePriorityOfIncident() throws Exception {
		try {
			verifyElementOfIncidentDetailsPage("editPriorityButton");
			waitForElementToBeClickableOfIncidentDetailsPage("editPriorityButton");
			verifyElementIsClickableOfIncidentDetailsPage("editPriorityButton");
			sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
			clickOnElementOfIncidentDetailsPage("editPriorityButton");
			verifyElementIsClickableOfIncidentDetailsPage("changePriorityExpandDropdown");
			sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
			clickOnElementOfIncidentDetailsPage("changePriorityExpandDropdown");
			verifyElementIsClickableOfIncidentDetailsPage("selectCriticalPriority");
			clickOnElementOfIncidentDetailsPage("selectCriticalPriority");
			verifyElementIsClickableOfIncidentDetailsPage("submitPriorityButton");
			clickOnElementOfIncidentDetailsPage("submitPriorityButton");
			waitForPageLoaded();
			sleeper(2000);// Need this sleeper as part if page is loading
			clickOnElementOfIncidentDetailsPage("commentTab");
			verifyElementOfIncidentDetailsPage("thirdComment");
			waitForElementOfIncidentDetailsPage("thirdComment");
			return getTextOfElementOnIncidentDetailsPage("firstComment");
		} catch (Exception ex) {
			LOGGER.error(("Exception occured in method changePriorityOfIncident " + ex.getMessage()));
			throw ex;
		}
	}

	/*
	 * This method type/subtype assignee of the given incident and return comment added after type/subtype is changed throws Exception
	 */
	public HashMap<String, String> changeTypeSubtypeOfIncident() throws Exception {
		try {
			verifyElementOfIncidentDetailsPage("editTypeButton");
			waitForElementToBeClickableOfIncidentDetailsPage("editTypeButton");
			verifyElementIsClickableOfIncidentDetailsPage("editTypeButton");
			sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
			clickOnElementOfIncidentDetailsPage("editTypeButton");
			verifyElementIsClickableOfIncidentDetailsPage("typeButtonDropdownArrow");
			sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
			clickOnElementOfIncidentDetailsPage("typeButtonDropdownArrow");
			clickOnElementOfIncidentDetailsPage("selectType");
			verifyElementIsClickableOfIncidentDetailsPage("subtypeExpand");
			sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
			clickOnElementOfIncidentDetailsPage("subtypeExpand");
			verifyElementIsClickableOfIncidentDetailsPage("selectSubType");
			clickOnElementOfIncidentDetailsPage("selectSubType");
			verifyElementIsClickableOfIncidentDetailsPage("submitTypeButton");
			clickOnElementOfIncidentDetailsPage("submitTypeButton");
			waitForPageLoaded();
			verifyElementOfIncidentDetailsPage("fourthComment");
			waitForElementOfIncidentDetailsPage("fourthComment");
			sleeper(2000);// Need this sleeper as part if page is loading
			clickOnElementOfIncidentDetailsPage("commentTab");
			HashMap<String, String> typeSubtype = new HashMap<>();
			typeSubtype.put("type", getTextOfElementOnIncidentDetailsPage("secondComment"));
			typeSubtype.put("subType", getTextOfElementOnIncidentDetailsPage("firstComment"));
			return typeSubtype;
		} catch (Exception ex) {
			LOGGER.error(("Exception occured in method changeTypeOfIncident " + ex.getMessage()));
			throw ex;
		}
	}
	
	/**
	 * This is a method to wait untill an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfIncidentDetailsPage(String key) {
		try {
			verifyElementIsinvisibile(incidentDetailsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfIncidentDetailsPage " + e.getMessage()));
		}
	}
	/**
	 * This is a method to get attribute of an element
	 * 
	 * @param key - Locator of element
	 * @param value - the name of attribute
	 * @return - String value of the attribute
	 */
	public final String getAttributesOfIncidentDetailPage(String key, String value) {
		try {
			return getAttribute(incidentDetailsPagePropertiesPageProperties.getProperty(key), value);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAttributesOfIncidentPage " + e.getMessage()));
			return null;
		}
	}
	/**
	 * This is the method to select second option from the dropdown if the first one is already selected
	 * 
	 * @param dropdownListKey - Locator of dropdown elements
	 * @param index - Already present index on the dropdown
	 * @return - String value of the option selecetd from the dropdown
	 */
	public final String selectAnyValueFromDropdownOnIncidentDetailPage(String dropdownListKey, int index) {
		try {
			return selectAnyValueFromDropdown(incidentDetailsPagePropertiesPageProperties.getProperty(dropdownListKey), index);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectAnyValueFromDropdownOnIncidentDetailPage " + e.getMessage()));
			return null;
		}
	}
	
	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnIncidentDetailsPage(String key) {
		try {
			mouseHover(incidentDetailsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnIncidentDetailsPage " + e.getMessage()));
		}
	}
	
	
}