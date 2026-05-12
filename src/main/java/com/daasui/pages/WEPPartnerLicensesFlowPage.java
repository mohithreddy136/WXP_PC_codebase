package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.ConstantPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WEPPartnerLicensesFlowPage extends CommonMethod {
    private ObjectReader WEPPartnerLicensesFlowPageReader = new ObjectReader();
    private Properties WEPPartnerLicensesFlowPage;
    public static String uiVersion = System.getProperty("uiVersion");
    private WEPPartnerLicensesFlowPage instance;

    public WEPPartnerLicensesFlowPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPPartnerLicensesFlowPage.class) {
                if (instance == null) {
                    instance = new WEPPartnerLicensesFlowPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEPPartnerLicensesFlowPage(WebDriver driver) throws IOException {
        WEPPartnerLicensesFlowPage = WEPPartnerLicensesFlowPageReader.getObjectRepository("WEPPartnerLicensesFlowPage");
    }
    
    /**
     * This method is the method to verify if an element is present on partner page
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is present
     */
    public final boolean verifyElementsOfWEPPartnerLicensesFlowPage(String key) {
        try {
            return verifyElementIsPresent(WEPPartnerLicensesFlowPage.getProperty(key));

        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEPPartnerLicensesFlowPage " + e.getMessage()));
            return false;
        }
    }
    /**
     * This is a method to match text on an element which is present on partner page
     *
     * @param key - locator of the element
     * @param textToMatch - text to be compared
     * @return - boolean value of whether both the texts match
     */
    public final boolean matchTextOfWEPPartnerLicensesFlowPage(String key, String textToMatch) {
        try {
            return verifyTextPresentOnElement(WEPPartnerLicensesFlowPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEPPartnerLicensesFlowPage " + e.getMessage()));
            return false;
        }
    }
    public final boolean clickOnElementsOfWEPPartnerLicensesFlowPage(String key) {
        try {
            click(WEPPartnerLicensesFlowPage.getProperty(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEPPartnerLicensesFlowPage " + e.getMessage()));
            return false;
        }
    }
    public final void enterTextForWEPPartnerLicensesFlowPage(String key, String textToMatch) {
        try {
            enterText(WEPPartnerLicensesFlowPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method enterTextForWEPPartnerLicensesFlowPage " + e.getMessage()));
        }
    }
    public final void actionClickOnWEPPartnerLicensesFlowPage(String key) throws Exception {
        actionClick(WEPPartnerLicensesFlowPage.getProperty(key));
    }

    public final boolean waitForElementsOnWEPPartnerLicensesFlowPage(String key) {
        try {
            return verifyElementIsVisibleDynamic(WEPPartnerLicensesFlowPage.getProperty(key),120);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOnWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }
    public final boolean verifyElementIsSelectedOfWEPPartnerCustomersPage(String key) {
        try {
            return verifyElementIsSelected(WEPPartnerLicensesFlowPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occurred in method verifyElementIsSelectedOfWEPPartnerCustomersPage " + e.getMessage()));
            return false;
        }
    }

}
