package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.ConstantURL;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WEPHardwareSupportPage extends CommonMethod {

	private Properties selectedLanguageProperties;
	private ObjectReader WEXHardwareSupportPagePropertiesReader = new ObjectReader();
	private Properties WEXHardwareSupportPageProperties;
	// private ObjectReader dashboardPagePropertiesReader = new ObjectReader();
//    private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String LanguageCode;
	private Properties selectedCredentialsProperties;
	private ObjectReader environmentPropertiesReader = new ObjectReader();
	private Properties environmentProperties;
	private WEPHardwareSupportPage instance;
	private WEPHardwareSupportPage hardwaresupport;
	private SettingsPage settingsPage;

	public static String uiVersion = System.getProperty("uiVersion");

	// Constructor to initialize properties
	public WEPHardwareSupportPage(WebDriver dRIVER) throws IOException {
		WEXHardwareSupportPageProperties = WEXHardwareSupportPagePropertiesReader
				.getObjectRepository("WEXHardwareSupportPage");
	}

	// Singleton pattern for creating an instance of the page object
	public WEPHardwareSupportPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPHardwareSupportPage.class) {
				if (instance == null) {
					instance = new WEPHardwareSupportPage(DRIVER);
				}
			}
		}
		return instance;
	}

	/**
	 * This is a method to click on element present on hardware support page
	 *
	 * @param key - locator of the element
	 */
	public final boolean clickOnElementOfWEPHardwareSupportOption(String key) {
		try {
			click(WEXHardwareSupportPageProperties.getProperty(key));
			return true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementOfWEPHardwareSupportOption " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to click on element present on hardware support page
	 *
	 * @param key - locator of the element
	 */
	public final boolean clickByJavaScriptWEPHardwareSupportOption(String key) {
		try {
			clickByJavaScript(WEXHardwareSupportPageProperties.getProperty(key));
			return true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementOfWEPHardwareSupportOption " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to click on element present on hardware support page
	 *
	 * @param key - locator of the element
	 */
	public final void actionClickWEPHardwareSupportOption(String key) {
		try {
			actionClick(WEXHardwareSupportPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementOfWEPHardwareSupportOption " + e.getMessage()));
		}
	}

	/**
	 * This is the method to wait for any element on the hardwaresupport page until
	 * it is visible
	 *
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is visible
	 */
	public final boolean verifyElementIsVisibleOnWEPHardwareSupportPage(String key) {
		try {
			return verifyElementIsVisible(WEXHardwareSupportPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(
					("Exception occured in method verifyElementIsVisibleofWEPHardwareSupportPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * THis is a method to verify if an element is enabled on hardwaresupport page
	 *
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is enabled
	 */
	public final boolean verifyElementIsEnableOnWEPHardwareSupportPage(String key) {
		try {
			return verifyElementIsEnable(WEXHardwareSupportPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(
					("Exception occured in method verifyElementIsEnableOfWEPHardwareSupportPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * THis is a method to verify to get list of elements on hardware support page
	 *
	 * @param key - locator of the element
	 */
	public final List<WebElement> getElementsOfWEPHardwareSupportPage(String key) throws Exception {
		return getAllElements(WEXHardwareSupportPageProperties.getProperty(key));
	}

	/**
	 * THis is a method to verify the list of the widgets displayed for Active Care
	 * on hardware support page
	 *
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is enabled
	 */
	public boolean verifyWEPWidgetsMatch(String WidgetList, String[] expectedWidgetNames) throws Exception {
		// Convert the expected widget names array to an ArrayList
		ArrayList<String> actualList = new ArrayList<>(Arrays.asList(expectedWidgetNames));
		LOGGER.info("Expected Widgets: {}", actualList);
		LOGGER.info("Expected Widgets Count: {}", actualList.size());

		// Fetch the elements from the webpage using the provided widgetLocator
		List<WebElement> elementsFromWebpage = getElementsOfWEPHardwareSupportPage(WidgetList);
		// List<WebElement> elementsFromWebpage =
		// getAllElements(WEXHardwareSupportPageProperties.getProperty(WidgetList));
		LOGGER.info("Elements from Webpage: {}", elementsFromWebpage);
		LOGGER.info("Webpage Elements Count: {}", elementsFromWebpage.size());

		// Create a list to store text values of the webpage elements
		List<String> chartNamesFromWebpage = new ArrayList<>();

		// Extract the text from each element on the webpage and store in the list
		for (WebElement element : elementsFromWebpage) {
			String text = element.getText();
			LOGGER.info("Text from element: {}", text);
			if (text != null) {
				chartNamesFromWebpage.add(text.trim()); // Trim any unnecessary whitespace
			}
		}

		// Print the extracted chart names from the webpage
		LOGGER.info("Chart Names from Webpage: {}", chartNamesFromWebpage);
		LOGGER.info("Chart Names Count: {}", chartNamesFromWebpage.size());

		// Normalize the lists by trimming whitespaces and converting to lowercase for
		// case-insensitive comparison
		List<String> cleanedActualList = actualList.stream().map(String::trim) // Trim leading/trailing spaces
				.map(String::toLowerCase) // Convert to lowercase for case-insensitivity
				.collect(Collectors.toList());

		List<String> cleanedWebPageList = chartNamesFromWebpage.stream().map(String::trim) // Trim leading/trailing
				// spaces
				.map(String::toLowerCase) // Convert to lowercase for case-insensitivity
				.collect(Collectors.toList());

		// Sort both lists to compare them in the same order
		Collections.sort(cleanedActualList);
		Collections.sort(cleanedWebPageList);

		// Debugging: Print both cleaned lists for comparison
		LOGGER.info("Cleaned Actual List: {}", cleanedActualList);
		LOGGER.info("Cleaned Webpage List: {}", cleanedWebPageList);

		// Compare the two lists element-by-element to find any mismatches
		for (int i = 0; i < cleanedActualList.size(); i++) {
			String expectedElement = cleanedActualList.get(i);
			String actualElement = cleanedWebPageList.get(i);
			if (!expectedElement.equals(actualElement)) {
				LOGGER.info("Mismatch found at index {}:", i);
				LOGGER.info("Expected: '{}'", expectedElement);
				LOGGER.info("Actual: '{}'", actualElement);
			}
		}

		// Check if the webpage contains all expected elements
		boolean containsAll = cleanedWebPageList.containsAll(cleanedActualList);
		LOGGER.info("Does the webpage list contain all expected elements? {}", containsAll);

		// Check if both lists are equal
		boolean allElementsPresent = cleanedActualList.equals(cleanedWebPageList);
		LOGGER.info("Lists are equal: {}", allElementsPresent);

		if (!allElementsPresent) {
			LOGGER.info("Some elements are missing or the lists do not match.");
		} else {
			LOGGER.info("All elements match.");
		}

		// Print counts of both lists after comparison
		LOGGER.info("Cleaned Actual List Count: {}", cleanedActualList.size());
		LOGGER.info("Cleaned Webpage List Count: {}", cleanedWebPageList.size());

		return allElementsPresent;
	}

	/**
	 * THis is a method to get the view incident url
	 *
	 * @param key - locator of the element
	 */
//    public String getViewIncidentUrl() {
//        return ConstantURL.ViewIncidentUrl;
//    }

	/**
	 * THis is a method to get the text on hardware support page
	 *
	 * @param key - locator of the element
	 */
	public final String getTextByWEPHardwareSupportPage(String key) {
		try {
			return getTextBy(WEXHardwareSupportPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfWEPPartnerCustomersPage " + e.getMessage()));
			return null;
		}

	}

	/**
	 * This is a method to verify text is present on the hardware support page
	 *
	 * @param key - locator of the element
	 */
	public final boolean verifyTextPresentOnWEPHardwareSupportPage(String key, String Text) {
		try {
			return verifyTextPresentOnElement(WEXHardwareSupportPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyTextPresentOnWEPHardwareSupportPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to scroll to an element on hardwaresupport page
	 *
	 * @param key - locator of the element
	 */
	public final void scrollTillViewOfWEPHardWareSupportPage(String key) {
		try {
			scrollTillView(WEXHardwareSupportPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollTillViewOfWEPHardWareSupportPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to click on element present on hardware support page using action class
	 *
	 * @param key - locator of the element
	 */
	public final boolean actionClickOnElementOfWEPHardwareSupportOption(String key) {
		try {
			actionClick(WEXHardwareSupportPageProperties.getProperty(key));
			return true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementOfWEPHardwareSupportOption " + e.getMessage()));
			return false;
		}
	}
	


    /**
     * This is a method to wait for an element till it is invisible
     *
     * @param key - Locator of element
     * @return - boolean value of whether the element is invisible or not
     */
    public final boolean waitUntilElementIsInvisibleOfHardwareSupportPage(String key) {
		try {
			return verifyElementIsinvisibile(WEXHardwareSupportPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error("Exception occurred in method waitUntilElementIsInvisibleOfHardwareSupportPage {}",e.getMessage());
			return false;
		}
    }
    
    
    public final boolean selectTextValueFromDropdownOfWEPHardwareSupportPage(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		return selectTextValueFromDropdownWorkflow(WEXHardwareSupportPageProperties.getProperty(dropdownListKey), elementText, WEXHardwareSupportPageProperties.getProperty(dropdownBox));
	}
    
    
    /**
	 * This is a method to select first option from any dropdown
	 * 
	 * @param dropdownListKey - Locator of dropdown elements
	 * @return - String value of the option selecetd from the dropdown
	 */
	public final String selectFirstOptionFromDropdownOnHardwareSupportPage(String dropdownListKey) {
		try {
			return selectFirstValueFromDropdown(WEXHardwareSupportPageProperties.getProperty(dropdownListKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectFirstOptionFromDropdownOnHardwareSupportPage " + e.getMessage()));
			return null;
		}
	}
	
	
	public final void enterTextForHardwareSupportPage(String key, String Text) throws Exception {
		enterText(WEXHardwareSupportPageProperties.getProperty(key), Text);
	}
	
	
	/**
	 * This methos is to create a reactive case
	 * 
	 * @param deviceName - name of the device to be selected from the dropdown
	 * @throws Exception
	 */
	public boolean createReactiveCase(String deviceName, String issueCategory, Boolean newLocation, Boolean requestEndUserLocation) throws Exception {
		if(waitUntilElementIsInvisibleOfHardwareSupportPage("createCaseModule")){
			LOGGER.info("Create Case modal is not opened.");
			clickOnElementOfWEPHardwareSupportOption("createCase");
		}
		sleeper(1000);
		if(!this.verifyElementIsVisibleOnWEPHardwareSupportPage("dropdownBoxAfterSelection")){
			LOGGER.info("Create Case modal is not opened. Device dropdown is not visible.");
			return false;
		}
		sleeper(1000);
		clickByJavaScriptWEPHardwareSupportOption("dropdownBoxAfterSelection");
//		actionClickWEPHardwareSupportOption("dropdownBoxAfterSelection");
		sleeper(500);
		enterTextForHardwareSupportPage("deviceSearchTextField", deviceName);
		if(!selectTextValueFromDropdownOfWEPHardwareSupportPage("deviceDropdownOptions",deviceName,"dropdownBoxAfterSelection")) {
			LOGGER.info("Device is not found in dropdown list.");
			return false;
		}
		pressKey(Keys.ESCAPE);
		
		clickOnElementOfWEPHardwareSupportOption("nextButtonCaseCreation");
		LOGGER.info("Moved to second step of case creation");
		
		if(this.verifyElementIsVisibleOnWEPHardwareSupportPage("issueDropdownButton")){
			clickOnElementOfWEPHardwareSupportOption("issueDropdownButton");
			sleeper(500);
			enterTextForHardwareSupportPage("deviceSearchTextField", issueCategory);
			if(!selectTextValueFromDropdownOfWEPHardwareSupportPage("deviceDropdownOptions",issueCategory,"issueDropdownButton")) {
				LOGGER.info("Issue type is not found in dropdown list.");
				return false;
			}
//			selectTextValueFromDropdownOfWEPHardwareSupportPage("deviceDropdownOptions",issueCategory,"issueDropdownButton");
//			pressKey(Keys.ESCAPE);
			clickOnElementOfWEPHardwareSupportOption("nextButtonIssueCaseCreation");	
		}
		
		
		if(verifyElementIsVisibleOnWEPHardwareSupportPage("locationDropdownButton")){
			clickByJavaScriptWEPHardwareSupportOption("locationDropdownButton");
			sleeper(500);
		}
		
		if(newLocation){
			selectFirstOptionFromDropdownOnHardwareSupportPage("deviceDropdownOptions");
			clickOnElementOfWEPHardwareSupportOption("nextButtonLocationCaseCreation");
			enterNewShipLocationCreateCase();
		}
		else if(requestEndUserLocation){
			clickOnElementOfWEPHardwareSupportOption("requestFromEndUser");
			clickOnElementOfWEPHardwareSupportOption("nextButtonCaseCreation");
			requestFromEndUser();
		}
		else {
			clickOnElementOfWEPHardwareSupportOption("addressOption");
			clickOnElementOfWEPHardwareSupportOption("nextButtonLocationCaseCreation");
		}
		if(verifyElementIsVisibleOnWEPHardwareSupportPage("reviewSubmitScreen")){
			clickOnElementOfWEPHardwareSupportOption("reviewSubmitButton");
			LOGGER.info("Reactive Case created");
		}
		
		return verifyElementIsVisibleOnWEPHardwareSupportPage("caseCreatedSuccessToastMessage");
	}
	
	
//	/**
//	 * This methos is to validate incident details info after case creation
//	 * 
//	 * @param deviceName - name of the device to be selected from the dropdown
//	 * @throws Exception
//	 */
//	public boolean validateIncidentDetails(String serialNumber, String issueCategory, Boolean newLocation, Boolean requestEndUserLocation) throws Exception {
//		if(waitUntilElementIsInvisibleOfHardwareSupportPage("createCaseModule")){
//			LOGGER.info("Create Case modal is not opened.");
//			clickOnElementOfWEPHardwareSupportOption("createCase");
//		}
//		sleeper(1000);
//		if(!this.verifyElementIsVisibleOnWEPHardwareSupportPage("dropdownBoxAfterSelection")){
//			LOGGER.info("Create Case modal is not opened. Device dropdown is not visible.");
//			return false;
//		}
//		sleeper(1000);
//		clickByJavaScriptWEPHardwareSupportOption("dropdownBoxAfterSelection");
////		actionClickWEPHardwareSupportOption("dropdownBoxAfterSelection");
//		sleeper(500);
//		enterTextForHardwareSupportPage("deviceSearchTextField", deviceName);
//		if(!selectTextValueFromDropdownOfWEPHardwareSupportPage("deviceDropdownOptions",deviceName,"dropdownBoxAfterSelection")) {
//			LOGGER.info("Device is not found in dropdown list.");
//			return false;
//		}
//		pressKey(Keys.ESCAPE);
//		
//		clickOnElementOfWEPHardwareSupportOption("nextButtonCaseCreation");
//		LOGGER.info("Moved to second step of case creation");
//		
//		if(this.verifyElementIsVisibleOnWEPHardwareSupportPage("issueDropdownButton")){
//			clickOnElementOfWEPHardwareSupportOption("issueDropdownButton");
//			sleeper(500);
//			enterTextForHardwareSupportPage("deviceSearchTextField", issueCategory);
//			if(!selectTextValueFromDropdownOfWEPHardwareSupportPage("deviceDropdownOptions",issueCategory,"issueDropdownButton")) {
//				LOGGER.info("Issue type is not found in dropdown list.");
//				return false;
//			}
////			selectTextValueFromDropdownOfWEPHardwareSupportPage("deviceDropdownOptions",issueCategory,"issueDropdownButton");
////			pressKey(Keys.ESCAPE);
//			clickOnElementOfWEPHardwareSupportOption("nextButtonIssueCaseCreation");	
//		}
//		
//		
//		if(verifyElementIsVisibleOnWEPHardwareSupportPage("locationDropdownButton")){
//			clickByJavaScriptWEPHardwareSupportOption("locationDropdownButton");
//			sleeper(500);
//		}
//		
//		if(newLocation){
//			selectFirstOptionFromDropdownOnHardwareSupportPage("deviceDropdownOptions");
//			clickOnElementOfWEPHardwareSupportOption("nextButtonLocationCaseCreation");
//			enterNewShipLocationCreateCase();
//		}
//		else if(requestEndUserLocation){
//			clickOnElementOfWEPHardwareSupportOption("requestFromEndUser");
//			clickOnElementOfWEPHardwareSupportOption("nextButtonCaseCreation");
//			requestFromEndUser();
//		}
//		else {
//			clickOnElementOfWEPHardwareSupportOption("addressOption");
//			clickOnElementOfWEPHardwareSupportOption("nextButtonLocationCaseCreation");
//		}
//		if(verifyElementIsVisibleOnWEPHardwareSupportPage("reviewSubmitScreen")){
//			clickOnElementOfWEPHardwareSupportOption("reviewSubmitButton");
//			LOGGER.info("Reactive Case created");
//		}
//		
//		return verifyElementIsVisibleOnWEPHardwareSupportPage("caseCreatedSuccessToastMessage");
//	}
	
	/**
	 * This methos is to create a case on Hardware Support page
	 * 
	 * @param location - shipping location for the case
	 * @throws Exception
	 */
	public final void enterNewShipLocationCreateCase() throws Exception {
		String FN = "Firstname";
		String LN = "Lastname";
		String addressLine1 = "Hinjewadi Phase 1";
		String zipCode = "444444";
		String state = "Maharashtra";
		String addressLine2 = "Hinjewadi";
		String city = "Pune";
		String emailId = "admin@hpmsqa.mailinator.com";
		String phoneNumber = "9999999999";
        String country = "India";
		
		sleeper(500);
		clickOnElementOfWEPHardwareSupportOption("countryDropdownbutton");
		sleeper(500);
        selectTextValueFromDropdownOfWEPHardwareSupportPage("countryDropdownOptions",country,"countryDropdownbutton");
		LOGGER.info("Select Country");
		pressKey(Keys.ESCAPE);
		
		enterTextForHardwareSupportPage("addressLine1", addressLine1);
		enterTextForHardwareSupportPage("addressLine2", addressLine2);
		enterTextForHardwareSupportPage("city", city);
		enterTextForHardwareSupportPage("state", state);
		enterTextForHardwareSupportPage("postalCode", zipCode);
		clickOnElementOfWEPHardwareSupportOption("nextButtonNewLocationCaseCreation");
		enterTextForHardwareSupportPage("firstName", FN);
		enterTextForHardwareSupportPage("lastName", LN);
		enterTextForHardwareSupportPage("emailId", emailId);
		enterTextForHardwareSupportPage("phoneNumber", phoneNumber);
		LOGGER.info("Selected location for case creation");
		clickOnElementOfWEPHardwareSupportOption("nextButtonPersonalInfoCaseCreation");
		LOGGER.info("Moved to Review and Submit step of case creation");
	}
	
	
	/**
	 * This methos is to create a case on Hardware Support page
	 * 
	 * @param location - shipping location for the case
	 * @throws Exception
	 */
	public void requestFromEndUser() throws Exception {
		String comment = "Please Deliver At My Home.";
		if(verifyElementIsVisibleOnWEPHardwareSupportPage("requestLocationMessage")){
			enterTextForHardwareSupportPage("requestLocationMessage", comment);
			clickOnElementOfWEPHardwareSupportOption("nextButtonCaseCreation");
			LOGGER.info("Moved to third step of case creation");
		}
	}
	
	/**
	 * This is a method to verify text is present on the hardware support page
	 *
	 * @param key - locator of the element
	 */
	public final boolean verifyElementIsPresentOnWEPHardwareSupportPage(String key) {
		try {
			return verifyElementIsPresent(WEXHardwareSupportPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsPresentOnWEPHardwareSupportPage " + e.getMessage()));
			return false;
		}
	}

    /** This is a method to hover mouse on an element
     *
     * @param key - Locator of element
     */
    public final void mouseHoverOnWEPHardwareSupportPage(String key) {
        try {
            mouseHover(WEXHardwareSupportPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method mousehoverOnWEPHardwareSupportPage " + e.getMessage()));
        }
    }

    /**
     * Verifies element visibility, scrolls to it, and validates its text matches expected value.
     *
     * @param elementKey - locator key for the element
     * @param expectedText - expected text to match (case-insensitive)
     * @return true if element is visible and text matches, false otherwise
     */
    public boolean verifyScrollAndValidateTextOnWEPHardwareSupportPage(String elementKey, String expectedText) {
        try {

            if (!verifyElementIsVisibleOnWEPHardwareSupportPage(elementKey)) {
                LOGGER.warn("Element '{}' is not visible on the page", elementKey);
                return false;
            }

            scrollTillViewOfWEPHardWareSupportPage(elementKey);
            String actualText = getTextByWEPHardwareSupportPage(elementKey);
            boolean matches = actualText.equalsIgnoreCase(expectedText);

            if (matches) {
                LOGGER.info("✓ Validation passed for '{}': Expected='{}', Actual='{}'", elementKey, expectedText, actualText);
            } else {
                LOGGER.error("✗ Validation failed for '{}': Expected='{}', Actual='{}'", elementKey, expectedText, actualText);
            }

            return matches;
        } catch (Exception e) {
            LOGGER.error("Error in verifyScrollAndValidateText for '{}': {}", elementKey, e.getMessage());
            return false;
        }
    }


	/**
	 * This is a method to perform incident action on hardware support page
	 *
	 * @param checkboxKey    - locator of the checkbox element
	 * @param actionButtonKey - locator of the action button element
	 * @param popupTitleKey  - locator of the popup title element
	 * @param languageCode   - language code for localization
	 * @return true if popup title matches expected text, false otherwise
	 */
	public boolean performIncidentActionOnWEPHardwareSupportPage(String checkboxKey, String actionButtonKey,String popupTitleKey, String languageCode) throws Exception {
		clickByJavaScriptWEPHardwareSupportOption(checkboxKey);
		LOGGER.info("Checked incident checkbox '{}'", checkboxKey);

		if (!verifyElementIsPresentOnWEPHardwareSupportPage(actionButtonKey)) {
			LOGGER.error("Action button '{}' not present", actionButtonKey);
			return false;
		}

		clickByJavaScriptWEPHardwareSupportOption(actionButtonKey);
		LOGGER.info("Clicked action button '{}'", actionButtonKey);

		verifyElementIsVisibleOnWEPHardwareSupportPage("incidentPopupHeading");
		String title = getTextByWEPHardwareSupportPage("incidentPopupHeading");
		return title.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", popupTitleKey));
	}


    /**
     * Hovers on an element and validates the tooltip message contains expected text.
     *
     * @param hoverElementKey - locator key for the element to hover on
     * @param tooltipElementKey - locator key for the tooltip element
     * @param expectedTooltipText - expected text that tooltip should contain
     * @return true if tooltip is visible and contains expected text, false otherwise
     */
    public boolean hoverAndValidateTooltipOnWEPHardwareSupportPage(String hoverElementKey, String tooltipElementKey, String expectedTooltipText) {
        try {
            LOGGER.info("Validating tooltip on '{}'...", hoverElementKey);

            mouseHoverOnWEPHardwareSupportPage(hoverElementKey);
            Thread.sleep(1000);

            if (!verifyElementIsVisibleOnWEPHardwareSupportPage(tooltipElementKey)) {
                LOGGER.error("✗ Tooltip is not displayed on '{}' hover", hoverElementKey);
                return false;
            }

            String actualTooltipText = getTextByWEPHardwareSupportPage(tooltipElementKey);
            boolean matches = actualTooltipText.toLowerCase().contains(expectedTooltipText.toLowerCase());

            if (matches) {
                LOGGER.info("✓ Tooltip validated on '{}': '{}'", hoverElementKey, actualTooltipText);
            } else {
                LOGGER.error("✗ Tooltip mismatch on '{}'. Expected to contain: '{}', Actual: '{}'",
                        hoverElementKey, expectedTooltipText, actualTooltipText);
            }

            return matches;
        } catch (Exception e) {
            LOGGER.error("Error validating tooltip on '{}': {}", hoverElementKey, e.getMessage());
            return false;
        }
    }



}
