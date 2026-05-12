package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;
import java.util.Properties;

public class WEPPartnerCustomersPage extends CommonMethod {
    public static String uiVersion = System.getProperty("uiVersion");
    private ObjectReader WEPPartnerCustomersPageReader = new ObjectReader();
    private Properties WEPPartnerCustomersPage;
    private WEPPartnerCustomersPage instance;

    public WEPPartnerCustomersPage(WebDriver driver) throws IOException {
        WEPPartnerCustomersPage = WEPPartnerCustomersPageReader.getObjectRepository("WEPPartnerCustomersPage");
    }

    public WEPPartnerCustomersPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPPartnerCustomersPage.class) {
                if (instance == null) {
                    instance = new WEPPartnerCustomersPage(DRIVER);
                }
            }
        }
        return instance;
    }

    /**
     * This method is the method to verify if an element is present on partner page
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is present
     */
    public final boolean verifyElementsOfWEPPartnerCustomersPage(String key) {
        try {
            return verifyElementIsPresent(WEPPartnerCustomersPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEPPartnerCustomersPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to verify on element present on partner page
     *
     * @param key  - locator of the element
     * @param name - name of the company or user
     */
    public final boolean verifyOnElementsWithNameOfWEPPartnerCustomersPage(String key, String name) {
        try {
            return verifyElementIsPresent(WEPPartnerCustomersPage.getProperty(key) + name);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsWithNameOfWEPPartnerCustomersPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is the method to wait for any element on the partner page untill it is visible
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is visible
     */
    public final boolean waitForElementsOfWEPPartnerCustomersPage(String key) {
        try {
            return verifyElementIsVisible(WEPPartnerCustomersPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEPPartnerCustomersPage " + e.getMessage()));
            return false;
        }
    }

    public final void waitUntillElementIsPresentOnWEPPartnerCustomersPage(String key) {
        try {
            waitUntillElementIsPresent(WEPPartnerCustomersPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForPageLoaded " + e.getMessage()));
        }
    }
    /**
     * This is a method to click on an element using java script
     *
     * @param key - locator of element
     * @throws Exception
     */
    public final void clickByJavaScriptOnWEXPartnerCustomesPage(String key) throws Exception {
        clickByJavaScript(WEPPartnerCustomersPage.getProperty(key));
    }

    /**
     * This is a method to click on element present on partner list page using java script
     *
     * @param key - locator of the element
     */
    public final boolean clickOnElementsOfwexPartnerPage(String key) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(WEPPartnerCustomersPage.getProperty(key))));
            element.click();
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to hover mouse on an element
     *
     * @param key - Locator of element
     */
    public final void mousehoverOnWEPPartnerCustomesPage(String key) {
        try {
            mouseHover(WEPPartnerCustomersPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method mousehoverOnPartnerPage " + e.getMessage()));
        }
    }

    /* This is a method to open the dropdown of the parter page
     *
     * @param key - locator of the element
     */
    public final void openDropDownOfWEPPartnerCustomersPage(String dropDownKey) {
        try {
            verifyElementsOfWEPPartnerCustomersPage(dropDownKey);
            mousehoverOnWEPPartnerCustomesPage(dropDownKey);
            sleeper(1000);
            clickByJavaScriptOnWEXPartnerCustomesPage(dropDownKey);
            sleeper(1000);
            clickOnElementsOfwexPartnerPage(dropDownKey);
            sleeper(1000);
            LOGGER.info("Clicked on dropdown");
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method openDropDownOfWEPPartnerCustomersPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to click on element present on partner list page
     *
     * @param searchBox - locator of the search box
     * @param ImportCatalogCompany - option to be selected
     */
    public final void  selectDropDownValueOfWEPPartnerPage(String searchBox, String ImportCatalogCompany, String selectCompany) {
        try {
            waitForElementsOfWEPPartnerPage(searchBox);

            enterTextForWEPPartnerPage(searchBox, ImportCatalogCompany);
            sleeper(3000);//to load the company name entered
            scrollToBottom();
            openDropDownOfWEPPartnerCustomersPage("customerNameFilterDropdown");
            clickOnElementsOfWEPPartnerCustomersPage(selectCompany);
            sleeper(3000);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method selectDropDownValueOfWEXPartnerPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to enter text on a text field present on partner list page
     *
     * @param key - locator of the element
     * @param textToMatch - text to be entered
     */
    public final void enterTextForWEPPartnerPage(String key, String textToMatch) {
        try {
            enterText(WEPPartnerCustomersPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method enterTextForWEXPartnerPage " + e.getMessage()));
        }
    }

    public final void enterTextWithoutClearForWEPPartnerPage(String key, String textToMatch) {
        try {
            enterTextwithoutclear(WEPPartnerCustomersPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method enterTextWithoutClearForWEPPartnerPage " + e.getMessage()));
        }
    }
    /**
     * This is the method to wait for any element on the partner list page untill it is visible
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is visible
     */
    public final boolean waitForElementsOfWEPPartnerPage(String key) {
        try {
            return verifyElementIsVisible(WEPPartnerCustomersPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }


    /**
     * This is a method to match text on an element which is present on partner page
     *
     * @param key         - locator of the element
     * @param textToMatch - text to be compared
     * @return - boolean value of whether both the texts match
     */
    public final boolean matchTextOfWEPPartnerCustomersPage(String key, String textToMatch) {
        try {
            return verifyTextPresentOnElement(WEPPartnerCustomersPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEPPartnerCustomersPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to match text on an element which is present on partner page
     *
     * @param key         - locator of the element
     * @param textToMatch - text to be compared
     * @param replaceText - text to be replaced in the locator
     * @return - boolean value of whether both the texts match
     */
    public final boolean matchTextOfWEPPartnerCustomersPage(String key, String textToMatch, String replaceText) {
        try {
            return verifyTextPresentOnElement(WEPPartnerCustomersPage.getProperty(key).replace(replaceText, textToMatch.trim()), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEPPartnerCustomersPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to match text on an element which is present on partner page
     *
     * @param key            - locator of the element
     * @param textToMatch    - text to be compared
     * @param replaceText    - text to be replaced in the locator
     * @param newReplaceText - text to be replaced in the locator
     * @return - boolean value of whether both the texts match
     */
    public final boolean matchTextOfWEPPartnerCustomersPage(String key, String textToMatch, String replaceText, String newReplaceText) {
        try {
            return verifyTextPresentOnElement(WEPPartnerCustomersPage.getProperty(key).replace(replaceText, newReplaceText), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEPPartnerCustomersPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * THis is a method to verify if an element on partner page is enabled
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is enabled
     */
    public final boolean verifyElementIsEnableOfWEPPartnerCustomersPage(String key) {
        try {
            return verifyElementIsEnable(WEPPartnerCustomersPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementIsEnableOfWEPPartnerCustomersPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to verify if an element on partner page is selected
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is selected
     */
    public final boolean verifyElementIsSelectedOfWEPPartnerCustomersPage(String key) {
        try {
            return verifyElementIsSelected(WEPPartnerCustomersPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementIsSelectedOfWEPPartnerCustomersPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to get text present on an element on partner page
     *
     * @param key - locator of the element
     * @return - string value of the text present on the element
     */
    public final String getTextOfWEPPartnerCustomersPage(String key) {
        try {
            return getTextBy(WEPPartnerCustomersPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWEPPartnerCustomersPage " + e.getMessage()));
            return null;
        }
    }

    public final void scrollTillViewOfWEPPartnerCustomersPage(String key) {
        try {
            scrollTillView(WEPPartnerCustomersPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method scrollTillViewOfWEPPartnerCustomersPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to get text present on an element on partner page
     *
     * @param locator - locator of the element
     * @return - string value of the text present on the element
     */
    public final String getTextOfWEXPartnerPage(String locator) {
        try {
            return getTextBy(locator);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWEPPartnerCustomersPage " + e.getMessage()));
            return null;
        }
    }

    /**
     * This is a metod to get attribute of an element present on partner page
     *
     * @param key          - locator of the element
     * @param desiredValue - desired attribute name
     * @return - value of the attribute as a string
     */
    public final String getAttributesOfWEPPartnerCustomersPage(String key, String desiredValue) {
        try {
            return getAttribute(WEPPartnerCustomersPage.getProperty(key), desiredValue);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWEPPartnerCustomersPage " + e.getMessage()));
            return null;
        }
    }

    /**
     * This is a method to scroll on WorkforceExp Customer Creation Page
     * @param key
     */
    public final void scrollOnWEPPartnerCustomersPage(String key) {
        try {
            scrollTillView(WEPPartnerCustomersPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method scrollOnWEXPartnerPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to click on element present on partner page
     *
     * @param key - locator of the element
     */
    public final boolean clickOnElementsOfWEPPartnerCustomersPage(String key) {
        try {
            click(WEPPartnerCustomersPage.getProperty(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEPPartnerCustomersPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to click on webelement present on partner page
     *
     * @param locator - locator of the element
     */
    public final boolean clickOnWebelementOfWEPPartnerCustomersPage(String key) {
        try {
            clickByJavaScript(WEPPartnerCustomersPage.getProperty(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEPPartnerCustomersPage " + e.getMessage()));
            return false;
        }
    }

    public final void pageRefreshForWEPPartnerCustomersPage() {
        try {
            refreshPage();
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method pageRefreshForWEPPartnerCustomersPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to enter text on a text field present on partner page
     *
     * @param key         - locator of the element
     * @param textToMatch - text to be entered
     */
    public final void enterTextForWEPPartnerCustomersPage(String key, String textToMatch) {
        try {
            enterText(WEPPartnerCustomersPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method enterTextForWEPPartnerCustomersPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to clear all filter on partner customer page
     */
    public final void clearAllFiltersOfWEPPartnerCustomersPage() {
        try {
            if (verifyElementsOfWEPPartnerCustomersPage("clearAllFilterButton")) {
                mousehoverOnWEPPartnerCustomesPage("clearAllFilterButton");
                clickOnElementsOfWEPPartnerCustomersPage("clearAllFilterButton");
                waitForPageLoaded();
                sleeper(5000);
            }
        } catch (Exception e) {
            LOGGER.error(("Exception occurred in method clearAllFiltersOfWEPPartnerCustomersPage " + e.getMessage()));
        }
    }

    public final Boolean switchToPartnerDashboard(){
        try {
            // If the what's new popup is available, close it
            if(verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
                clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

            sideMenuExpansionForPartner();
            waitForPageLoaded();
            sleeper(5000); // Allow partner dashboard to fully load

            // Check if All Customers is already selected
            String selectedText = getSelectedCustomerText();
            if (!isAllCustomersText(selectedText)) {
                // Not selected - try to select All Customers
                selectAllCustomersAnyLanguage();
                sleeper(3000);
                waitForPageLoaded();
                selectedText = getSelectedCustomerText();
            }

            // Detect and update the actual UI language so tests use correct locale
            if (selectedText != null && !selectedText.trim().isEmpty()) {
                detectAndUpdateUILanguageCode(selectedText);
            }

            boolean result = isAllCustomersText(selectedText);
            LOGGER.info("switchToPartnerDashboard result: " + result + " (selectedText: [" + selectedText + "])");
            return result;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method partnerDashboard " + e.getMessage()));
            return false;
        }
    }

    public final void partnerWithCompanyViewAnyLanguage(String companyName, String modules) {
        if (isAllCustomersLabel(companyName)) {
            selectAllCustomersAnyLanguage();
            partnerView(modules);
            return;
        }
        partnerWithCompanyView(companyName, modules);
    }

    /**
     * Reads the currently selected customer text from the UI.
     * Tries SelectedCustomer element first, falls back to customerFilter.
     * Uses a short dynamic wait (5s) to avoid long timeouts when elements
     * exist in DOM but are not visible at small screen resolution.
     */
    public String getSelectedCustomerText() {
        try {
            if (waitForElementsOfDashboardPageDynamic("SelectedCustomer", 5)) {
                String text = getTextOfWEPPartnerCustomersPage("SelectedCustomer");
                LOGGER.info("SelectedCustomer text from UI: [" + text + "]");
                if (text != null && !text.trim().isEmpty()) {
                    return text.trim();
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Could not read SelectedCustomer element: " + e.getMessage());
        }
        try {
            if (waitForElementsOfDashboardPageDynamic("SelectedCustomerLowResolution", 5)) {
                String text = getTextOfWEPPartnerCustomersPage("SelectedCustomerLowResolution");
                LOGGER.info("SelectedCustomerLowResolution text from UI: [" + text + "]");
                if (text != null && !text.trim().isEmpty()) {
                    return text.trim();
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Could not read SelectedCustomerLowResolution element: " + e.getMessage());
        }
        try {
            if (waitForElementsOfDashboardPageDynamic("customerFilter", 5)) {
                String text = getTextOfWEPPartnerCustomersPage("customerFilter");
                LOGGER.info("customerFilter text from UI: [" + text + "]");
                if (text != null && !text.trim().isEmpty()) {
                    return text.trim();
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Could not read customerFilter element: " + e.getMessage());
        }
        LOGGER.warn("Could not read any customer selection text from UI");
        return null;
    }

    /**
     * Checks if the given text matches any known "All Customers" translation.
     */
    private boolean isAllCustomersText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        String normalized = normalizeLabel(text);
        for (String localizedLabel : getAllCustomersLabelsFromAllLocales()) {
            String normalizedLabel = normalizeLabel(localizedLabel);
            if (normalized.equalsIgnoreCase(normalizedLabel) || normalized.contains(normalizedLabel)) {
                LOGGER.info("Matched [" + normalized + "] with known label [" + normalizedLabel + "]");
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of all known "All Customers" labels from every locale file.
     */
    private java.util.List<String> getAllCustomersLabelsFromAllLocales() {
        java.util.List<String> labels = new java.util.ArrayList<>();
        labels.add(CommonVariables.ALL_CUSTOMER); // English hardcoded fallback
        String[] localeCodes = {"en", "fr", "ja", "de", "es", "pt-BR", "pt-PT", "zh-HANS", "zh-TW",
                "nl", "sv", "it", "da", "fi", "ko", "nb-NO"};
        for (String locale : localeCodes) {
            try {
                String localized = getTextLanguage(locale, "daas_ui", "side.menu.customers.all");
                if (localized != null && !localized.trim().isEmpty()) {
                    labels.add(localized.trim());
                }
            } catch (Exception e) {
                // Skip locales that fail to load
            }
        }
        return labels;
    }

    /**
     * Selects "All Customers" from the company filter dropdown by picking the
     * first option in the list (which is always "All Customers" regardless of
     * language). Retries clicking the dropdown filter up to 3 times if the
     * dropdown options don't appear. Tries the expanded filter first, then
     * falls back to the collapsed filter for small screen resolutions.
     */
    private boolean selectAllCustomersAnyLanguage() {
        try {
            WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
            java.util.List<WebElement> options = null;

            // Retry opening the dropdown up to 3 times
            for (int attempt = 1; attempt <= 3; attempt++) {
                LOGGER.info("Attempting to open company filter dropdown (attempt " + attempt + ")");
                sleeper(2000);
                boolean clicked = false;
                // Try expanded filter first (normal resolution)
                try {
                    dashboardPage.actionClickOfDashboardPage("companyFilter");
                    clicked = true;
                } catch (Exception clickEx) {
                    LOGGER.warn("actionClick on expanded companyFilter failed on attempt " + attempt + ": " + clickEx.getMessage());
                }
                // If expanded filter not available, try collapsed filter (small resolution)
                if (!clicked) {
                    try {
                        clickOnElementsOfWEPPartnerCustomersPage("smallResolutionFilterButton");
                        clicked = true;
                        LOGGER.info("Clicked collapsed company filter (small resolution) on attempt " + attempt);
                    } catch (Exception smallEx) {
                        LOGGER.warn("Click on smallResolutionFilterButton also failed on attempt " + attempt + ": " + smallEx.getMessage());
                        continue;
                    }
                }
                sleeper(3000);
                try {
                    options = dashboardPage.getElementsOfDashboardPage("companySelectionDropdown");
                } catch (Exception e) {
                    LOGGER.warn("getElementsOfDashboardPage threw exception on attempt " + attempt + ": " + e.getMessage());
                    options = null;
                }
                if (options != null && !options.isEmpty()) {
                    break;
                }
                LOGGER.warn("Dropdown options empty on attempt " + attempt + ", retrying...");
            }

            LOGGER.info("Company selection dropdown options count: " + (options != null ? options.size() : 0));

            if (options != null && !options.isEmpty()) {
                for (int i = 0; i < options.size(); i++) {
                    LOGGER.info("Dropdown option [" + i + "]: [" + options.get(i).getText() + "]");
                }
                // The first option in the dropdown is always "All Customers" regardless of language
                WebElement firstOption = options.get(0);
                LOGGER.info("Selecting first dropdown option: [" + firstOption.getText() + "]");
                sleeper(1000);
                clickWebelement(firstOption);
                sleeper(2000);
                waitForPageLoaded();
                return true;
            }

            LOGGER.error("All Customers option not found - company selection dropdown is empty after 3 attempts");
            return false;
        } catch (Exception e) {
            LOGGER.error("Exception occurred while selecting All Customers: " + e.getMessage());
            return false;
        }
    }

    private boolean isAllCustomersLabel(String companyName) {
        return companyName != null && companyName.trim().equalsIgnoreCase(CommonVariables.ALL_CUSTOMER);
    }

    /**
     * Detects the actual UI language from the given selectedText (which should be
     * the "All Customers" label in whatever language the UI is rendering).
     * Updates DashboardPage.LanguageCode and returns the detected locale
     * so that the test class can update its own LanguageCode directly.
     */
    public String detectAndUpdateUILanguageCode(String selectedText) {
        try {
            String normalizedSelected = normalizeLabel(selectedText);
            String[] localeCodes = {"en", "fr", "ja", "de", "es", "pt-BR", "pt-PT", "zh-HANS", "zh-TW",
                    "nl", "sv", "it", "da", "fi", "ko", "nb-NO"};
            for (String locale : localeCodes) {
                try {
                    String localized = getTextLanguage(locale, "daas_ui", "side.menu.customers.all");
                    if (localized != null && normalizeLabel(localized).equalsIgnoreCase(normalizedSelected)) {
                        LOGGER.info("Detected actual UI language: [" + locale + "] (DashboardPage.LanguageCode was [" + DashboardPage.LanguageCode + "])");
                        // Always update DashboardPage.LanguageCode
                        DashboardPage.LanguageCode = locale;
                        return locale;
                    }
                } catch (Exception e) {
                    // Skip locales that fail to load
                }
            }
            LOGGER.warn("Could not detect UI language from text: [" + selectedText + "]");
        } catch (Exception e) {
            LOGGER.warn("Failed to detect actual UI language: " + e.getMessage());
        }
        return null;
    }

    private String normalizeLabel(String value) {
        if (value == null) {
            return "";
        }
        String normalized = value.replace('\u00A0', ' ').trim();
        return normalized.replaceAll("\\s+", " ").toLowerCase();
    }

    public final boolean waitForElementsOfDashboardPageDynamic(String key, int waitTime) throws Exception {
        return verifyElementIsVisibleDynamic(WEPPartnerCustomersPage.getProperty(key), waitTime);
    }
    /**
     * This method is to verify that left side menu is open or close. if left side
     * menu is closed then this method will open it.
     * For small screen resolutions where the side menu toggle button is not present,
     * it dismisses any blocking popups and waits for the company filter to become visible.
     */
    public final void sideMenuExpansionForPartner() {
        try {
            if (waitForElementsOfDashboardPageDynamic("sideMenuToggleButton", 5)) {
                if ((verifyElementsOfWEPPartnerCustomersPage("toggleButtonClose"))) {
                    clickOnElementsOfWEPPartnerCustomersPage("toggleButtonClose");
                    Assert.assertTrue(verifyElementsOfWEPPartnerCustomersPage("toggleButtonOpen"),
                            "toggleButton is not open");
                }
            } else {
                LOGGER.info("Due to small screen resolution the side menu panel expand collapse option is not present");
                clickOnElementsOfWEPPartnerCustomersPage("smallResolutionFilterButton");

            }

        } catch (Exception e) {
            LOGGER.error("Exception occurred on opening Menu button" + e.getMessage());
        }
    }

    public final void  selecttextDropDownValueOfWEPPartnerPage(String searchBox, String ImportCatalogCompany, String listDropdown) {
        try {
            waitForElementsOfWEPPartnerPage(searchBox);

            enterTextForWEPPartnerPage(searchBox, ImportCatalogCompany);
            sleeper(3000);//to load the company name entered
            scrollToBottom();
            openDropDownOfWEPPartnerCustomersPage("customerNameFilterDropdown");
            selectDropdownOptions(WEPPartnerCustomersPage.getProperty(listDropdown),ImportCatalogCompany);
            sleeper(3000);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method selecttextDropDownValueOfWEXPartnerPage " + e.getMessage()));
        }
    }

    public void clickIfPresent(String elementName) {
        try {
            if (verifyElementsOfWEPPartnerCustomersPage(elementName)) {
                clickOnElementsOfWEPPartnerCustomersPage(elementName);
            }
        }catch(Exception e) {
            LOGGER.error(("Exception occured in method clickIfPresent with one agrument " + e.getMessage()));
        }
    }

    public void clickIfPresent(String elementName, String elementClose) {
        try {
            if (verifyElementsOfWEPPartnerCustomersPage(elementName)) {
                clickOnElementsOfWEPPartnerCustomersPage(elementClose);
            }
        }catch(Exception e) {
            LOGGER.error(("Exception occured in method clickIfPresent with two agrument " + e.getMessage()));
        }
    }
}