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

public class WEPPartnerCustomizeHelpandSupportPage extends CommonMethod {

    private WEPPartnerCustomizeHelpandSupportPage instance;
    private Properties WEPPartnerCustomizeHelpandSupportPageProperties;
    private ObjectReader WEPPartnerCustomizeHelpandSupportPagePropertiesReader = new ObjectReader();
    private final static Logger LOGGER = LogManager.getLogger(WEPPartnerCustomizeHelpandSupportPage.class);
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");
    LocalDate today = LocalDate.now();
    int day = today.getDayOfMonth();

    public WEPPartnerCustomizeHelpandSupportPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPPartnerCustomizeHelpandSupportPage.class) {
                if (instance == null) {
                    instance = new WEPPartnerCustomizeHelpandSupportPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEPPartnerCustomizeHelpandSupportPage(WebDriver driver) throws IOException {
        WEPPartnerCustomizeHelpandSupportPageProperties =
                WEPPartnerCustomizeHelpandSupportPagePropertiesReader.getObjectRepository("WEPPartnerCustomizeHelpandSupportPage");
    }

    public final void clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage(String key) throws Exception {
        click(WEPPartnerCustomizeHelpandSupportPageProperties.getProperty(key));
    }

    public final boolean verifyElementsOfWEPPartnerCustomizeHelpandSupportPage(String key) {
        try {
            return verifyElementIsPresent(WEPPartnerCustomizeHelpandSupportPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEPPartnerSubscriptionContractPage" + e.getMessage()));
            return false;
        }
    }

    public final boolean matchTextOfWEPPartnerCustomizeHelpandSupportPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEPPartnerCustomizeHelpandSupportPageProperties.getProperty(key), Text);
    }


    public final String getTextOfWEPPartnerCustomizeHelpandSupportPage(String key) throws Exception {
        return getTextBy(WEPPartnerCustomizeHelpandSupportPageProperties.getProperty(key));
    }

    public final void enterTextOfWEPPartnerCustomizeHelpandSupportPage(String key, String Text) {
        try {
            enterText(WEPPartnerCustomizeHelpandSupportPageProperties.getProperty(key), Text);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method enterTextOfWEPPartnerCustomizeHelpandSupportPageProperties " + e.getMessage()));
        }
    }

    public final boolean waitForElementsOfWEPPartnerCustomizeHelpandSupportPage(String key) {
        try {
            return verifyElementIsVisible(WEPPartnerCustomizeHelpandSupportPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEPPartnerCustomizeHelpandSupportPage " + e.getMessage()));
            return false;
        }
    }

    public final void selectDropdownOptionByKey(String dropdownKey, String optionKey) throws Exception {
        click(WEPPartnerCustomizeHelpandSupportPageProperties.getProperty(dropdownKey));
        click(WEPPartnerCustomizeHelpandSupportPageProperties.getProperty(optionKey));
    }

    public final void clearTextFromWEPPartnerCustomizeHelpandSupportPageTextField(String key) throws Exception {
        clearText(WEPPartnerCustomizeHelpandSupportPageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnWEPPartnerCustomizeHelpandSupportPage(String key) throws Exception {
        clickByJavaScript(WEPPartnerCustomizeHelpandSupportPageProperties.getProperty(key));
    }

    public final void checkCustomHelpAndSupportToggle(String key) throws Exception {
        boolean isSelected  = verifyElementIsSelected((WEPPartnerCustomizeHelpandSupportPageProperties.getProperty(key)));
        if (!isSelected ) {
            clickByJavaScriptOnWEPPartnerCustomizeHelpandSupportPage(key);
        }
    }

    public final void ensureOptionIsSelected(String key) throws Exception {
        boolean isSelected  = verifyElementIsSelected(WEPPartnerCustomizeHelpandSupportPageProperties.getProperty(key));
        if (!isSelected ) {
            clickByJavaScriptOnWEPPartnerCustomizeHelpandSupportPage(key);
        }
    }
}
