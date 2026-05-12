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

public class WEXPartnerCertificationsPage extends CommonMethod {
    private ObjectReader WEXPartnerCertificationsPageReader = new ObjectReader();
    private Properties WEXPartnerCertificationsPage;
    public static String uiVersion = System.getProperty("uiVersion");
    private WEXPartnerCertificationsPage instance;

    public WEXPartnerCertificationsPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEXPartnerCertificationsPage.class) {
                if (instance == null) {
                    instance = new WEXPartnerCertificationsPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEXPartnerCertificationsPage(WebDriver driver) throws IOException {
        WEXPartnerCertificationsPage = WEXPartnerCertificationsPageReader.getObjectRepository("WEXPartnerCertificationsPage");
    }

    /**
     * This method is the method to verify if an element is present on partner page
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is present
     */
    public final boolean verifyElementsOfWEXPartnerCertificationsPage(String key) {
        try {
            return verifyElementIsPresent(WEXPartnerCertificationsPage.getProperty(key));
            
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEXPartnerCertificationsPage " + e.getMessage()));
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
    public final boolean matchTextOfWEXPartnerCertificationsPage(String key, String textToMatch) {
        try {
            return verifyTextPresentOnElement(WEXPartnerCertificationsPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEXPartnerCertificationsPage " + e.getMessage()));
            return false;
        }
    }
    public final boolean clickOnElementsOfWEXPartnerCertificationsPage(String key) {
        try {
            click(WEXPartnerCertificationsPage.getProperty(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXPartnerCertificationsPage " + e.getMessage()));
        return false;
        }
    }
    public final void enterTextForWEXPartnerCertificationsPage(String key, String textToMatch) {
        try {
            enterText(WEXPartnerCertificationsPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method enterTextForWEXPartnerCertificationsPage " + e.getMessage()));
        }
    }
    public final void actionClickOnWEXPartnerCertificationsPage(String key) throws Exception {
		actionClick(WEXPartnerCertificationsPage.getProperty(key));
	}

    public final boolean waitForElementsOnWEXPartnerCertificationsPage(String key) {
        try {
            return verifyElementIsVisibleDynamic(WEXPartnerCertificationsPage.getProperty(key),120);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOnWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }
    public final boolean verifyElementIsSelectedOfWEXPartnerCertificationsPage(String key) {
        try {
            return verifyElementIsSelected(WEXPartnerCertificationsPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occurred in method verifyElementIsSelectedOfWEXPartnerCertificationsPage" + e.getMessage()));
            return false;
        }
    }
    public final String getTextOfWEXPartnerCertificationsPage(String key) throws Exception {
        return getTextBy(WEXPartnerCertificationsPage.getProperty(key));
    }
}
