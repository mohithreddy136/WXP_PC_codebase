package com.daasui.pages;
import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;


public class WEPPartnerSubscriptionContractPage extends CommonMethod {

    private WEPPartnerSubscriptionContractPage instance;
    private Properties WEPPartnerSubscriptionContractPageProperties;
    private ObjectReader WEPPartnerSubscriptionContractPagePropertiesReader = new ObjectReader();
    private final static Logger LOGGER = LogManager.getLogger(WEPPartnerSubscriptionContractPage.class);
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");
    LocalDate today = LocalDate.now();
    int day = today.getDayOfMonth();

    public WEPPartnerSubscriptionContractPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPPartnerSubscriptionContractPage.class) {
                if (instance == null) {
                    instance = new WEPPartnerSubscriptionContractPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEPPartnerSubscriptionContractPage(WebDriver driver) throws IOException {
        WEPPartnerSubscriptionContractPageProperties =
                WEPPartnerSubscriptionContractPagePropertiesReader.getObjectRepository("WEPPartnerSubscriptionContract");
    }

    public final void clickOnElementsOfWEPPartnerSubscriptionContractPage(String key) throws Exception {
        click(WEPPartnerSubscriptionContractPageProperties.getProperty(key));
    }

    public final boolean verifyElementsOfWEPPartnerSubscriptionContractPage(String key) {
        try {
            return verifyElementIsPresent(WEPPartnerSubscriptionContractPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEPPartnerSubscriptionContractPage" + e.getMessage()));
            return false;
        }
    }

    public final boolean selectTextValueFromDropdownOfWEPPartnerSubscriptionContractPage(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
        return selectTextValueFromDropdown(WEPPartnerSubscriptionContractPageProperties.getProperty(dropdownListKey), elementText, WEPPartnerSubscriptionContractPageProperties.getProperty(dropdownBox));
    }

    public final boolean matchTextOfWEPPartnerSubscriptionContractPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEPPartnerSubscriptionContractPageProperties.getProperty(key), Text);
    }


    public final String getTextOfWEPPartnerSubscriptionContractPage(String key) throws Exception {
        return getTextBy(WEPPartnerSubscriptionContractPageProperties.getProperty(key));
    }

    public final void enterTextOfWEPPartnerSubscriptionContractPage(String key, String Text) {
        try {
            enterText(WEPPartnerSubscriptionContractPageProperties.getProperty(key), Text);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method enterTextOfWEPPartnerSubscriptionContractPageProperties " + e.getMessage()));
        }
    }

    public final void clearTextFromWEPPartnerSubscriptionContractPageTextField(String key) throws Exception {
        clearText(WEPPartnerSubscriptionContractPageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnWEPPartnerSubscriptionContractPage(String key) throws Exception {
        clickByJavaScript(WEPPartnerSubscriptionContractPageProperties.getProperty(key));
    }

    public final void selectCountryByValue(String value) throws Exception {
        click(WEPPartnerSubscriptionContractPageProperties.getProperty("CountryIDDropdown"));
        click(WEPPartnerSubscriptionContractPageProperties.getProperty(value));
    }
    public final String getTodayDateFormatted() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return currentDate.format(formatter);
    }

    public final String getNextDateFormatted() {
        LocalDate currentDate = LocalDate.now();
        LocalDate nextDate = currentDate.plusDays(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return nextDate.format(formatter);
    }

    public final void selectStartDate() {
        try {
            String todayDate = getTodayDateFormatted();
            click(WEPPartnerSubscriptionContractPageProperties.getProperty("StartDate"));
            enterText(WEPPartnerSubscriptionContractPageProperties.getProperty("StartDate"), todayDate);
            LOGGER.info("Selected Start Date: " + todayDate);
        } catch (Exception e) {
            LOGGER.error("Error selecting Start Date: " + e.getMessage());
        }
    }

    public final void selectEndDate() {
        try {
            String nextDate = getNextDateFormatted();
            click(WEPPartnerSubscriptionContractPageProperties.getProperty("EndDateInput"));
            enterText(WEPPartnerSubscriptionContractPageProperties.getProperty("EndDateInput"), nextDate);
            LOGGER.info("Selected End Date: " + nextDate);
        } catch (Exception e) {
            LOGGER.error("Error selecting End Date: " + e.getMessage());
        }
    }

    public final void selectPlanByVisibleText(String planInputKey, String key) throws Exception {
        verifyElementIsVisible(WEPPartnerSubscriptionContractPageProperties.getProperty(planInputKey));
        click(WEPPartnerSubscriptionContractPageProperties.getProperty(planInputKey));
        Thread.sleep(1000);
        verifyElementIsVisible(WEPPartnerSubscriptionContractPageProperties.getProperty(key));
        click(WEPPartnerSubscriptionContractPageProperties.getProperty(key));
    }

    public final void enterSeats(String seatsKey, String value) throws Exception {
        clearText(WEPPartnerSubscriptionContractPageProperties.getProperty(seatsKey));
        enterText(WEPPartnerSubscriptionContractPageProperties.getProperty(seatsKey), value);
    }

    public final void sideMenuSelectionWEPRootLoginPageSubCont(String lang, String parentmenu, String childmenu) throws Exception {
        sideMenuSelection(lang, WEPPartnerSubscriptionContractPageProperties.getProperty(parentmenu), WEPPartnerSubscriptionContractPageProperties.getProperty(childmenu));
        System.out.println("childmenu : " + childmenu);
    }


}