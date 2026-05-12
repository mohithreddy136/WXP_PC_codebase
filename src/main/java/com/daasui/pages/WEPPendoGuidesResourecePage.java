package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Properties;

public class WEPPendoGuidesResourecePage extends CommonMethod {

    private WEPPendoGuidesResourecePage instance;
    private ObjectReader WEPPendoGuidesResourecePageReader = new ObjectReader();
    private Properties WEPPendoGuidesResourecePageProperties;
    private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");

    public WEPPendoGuidesResourecePage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPPendoGuidesResourecePage.class) {
                if (instance == null) {
                    instance = new WEPPendoGuidesResourecePage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEPPendoGuidesResourecePage(WebDriver driver) throws IOException {
        WEPPendoGuidesResourecePageProperties = WEPPendoGuidesResourecePageReader.getObjectRepository("PendoGuidesResource");
    }

    public final boolean verifyElementsOfWEPPendoGuidesResourecePage(String key) throws Exception {
        return verifyElementIsPresent(WEPPendoGuidesResourecePageProperties.getProperty(key));
    }

    public final boolean verifyElementIsClickableOfWEPPendoGuidesResourecePage(String key) throws Exception {
        return verifyElementIsClickable(WEPPendoGuidesResourecePageProperties.getProperty(key));
    }

    public final void scrollOnWEPPendoGuidesResourecePage(String key) throws Exception {
        scrollTillView(WEPPendoGuidesResourecePageProperties.getProperty(key));
    }

    public final String getTextOfWEPPendoGuidesResourecePage(String key) throws Exception {
        return getTextBy(WEPPendoGuidesResourecePageProperties.getProperty(key));

    }

    public final void clickOnElementsOfWEPPendoGuidesResourecePage(String key) throws Exception {
        click(WEPPendoGuidesResourecePageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnElementsOfWEPPendoGuidesResourecePage(String key) throws Exception {
        clickByJavaScript(WEPPendoGuidesResourecePageProperties.getProperty(key));
    }

    public final boolean waitForElementsOfWEPPendoGuidesResourecePage(String key,int waitTime) throws Exception {
        return verifyElementIsVisibleDynamic(WEPPendoGuidesResourecePageProperties.getProperty(key),waitTime);
    }

    public final boolean matchTextOfWEPPendoGuidesResourecePage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEPPendoGuidesResourecePageProperties.getProperty(key), Text);
    }
}
