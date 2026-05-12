package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

import static com.sun.tools.xjc.util.DOMUtils.getElements;

public class WEPPartnerExperienceSnapshotPage extends CommonMethod {

    private WEPPartnerExperienceSnapshotPage instance;
    private Properties WEPPartnerExperienceSnapshotPageProperties;
    private ObjectReader WEPPartnerExperienceSnapshotPagePropertiesReader = new ObjectReader();
    private final static Logger LOGGER = LogManager.getLogger(WEPPartnerExperienceSnapshotPage.class);
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");
    LocalDate today = LocalDate.now();
    int day = today.getDayOfMonth();

    public WEPPartnerExperienceSnapshotPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPPartnerExperienceSnapshotPage.class) {
                if (instance == null) {
                    instance = new WEPPartnerExperienceSnapshotPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEPPartnerExperienceSnapshotPage(WebDriver driver) throws IOException {
        WEPPartnerExperienceSnapshotPageProperties =
                WEPPartnerExperienceSnapshotPagePropertiesReader.getObjectRepository("WEPPartnerExperienceSnapshotPage");
    }

    public final void clickOnElementsOfWEPPartnerExperienceSnapshotPage(String key) throws Exception {
        click(WEPPartnerExperienceSnapshotPageProperties.getProperty(key));
    }

    public final boolean verifyElementsOfWEPPartnerExperienceSnapshotPage(String key) {
        try {
            return verifyElementIsPresent(WEPPartnerExperienceSnapshotPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEPPartnerExperienceSnapshotPage" + e.getMessage()));
            return false;
        }
    }
    public final boolean waitForElementsOnWEPPartnerExperienceSnapshotPage(String key) {
        try {
            return verifyElementIsVisibleDynamic(WEPPartnerExperienceSnapshotPageProperties.getProperty(key),120);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOnWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }

    public final boolean matchTextOfWEPPartnerExperienceSnapshotPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEPPartnerExperienceSnapshotPageProperties.getProperty(key), Text);
    }


    public final String getTextOfWEPPartnerExperienceSnapshotPage(String key) throws Exception {
        return getTextBy(WEPPartnerExperienceSnapshotPageProperties.getProperty(key));
    }

    public final void enterTextOfWEPPartnerExperienceSnapshotPage(String key, String Text) {
        try {
            enterText(WEPPartnerExperienceSnapshotPageProperties.getProperty(key), Text);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method enterTextOfWEPPartnerExperienceSnapshotPageProperties " + e.getMessage()));
        }
    }

    public final void clearTextFromWEPPartnerExperienceSnapshotPageTextField(String key) throws Exception {
        clearText(WEPPartnerExperienceSnapshotPageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnWEPPartnerExperienceSnapshotPage(String key) throws Exception {
        clickByJavaScript(WEPPartnerExperienceSnapshotPageProperties.getProperty(key));
    }

    public int getExperienceCount(String key) throws Exception {
        String countText = getTextOfWEPPartnerExperienceSnapshotPage(key).trim();
        return Integer.parseInt(countText);
    }

    public int getTotalCustomers(String key) throws Exception {
        String text = getTextOfWEPPartnerExperienceSnapshotPage(key);
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }
    public final void hoverOnElementOfWEPPartnerExperienceSnapshotPage(String key) throws Exception {
        mouseHover(WEPPartnerExperienceSnapshotPageProperties.getProperty(key));
    }

    public int getRemainingCustomersCount(String key) throws Exception {
        String text = getTextOfWEPPartnerExperienceSnapshotPage(key);
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

    public final int getExperienceTableRowCount(String key) {
        try {
            return getAllElements(WEPPartnerExperienceSnapshotPageProperties.getProperty(key)).size();
        } catch (Exception e) {
            LOGGER.error("Exception occurred in getExperienceTableRowCount: " + e.getMessage());
            return 0;
        }
    }


}
