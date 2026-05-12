package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Properties;

public class WEPDiscoveryPage extends CommonMethod {

    private WEPDiscoveryPage instance;
    private ObjectReader WEPDiscoveryPropertiesReader = new ObjectReader();
    private Properties WEPDiscoveryProperties;


    public WEPDiscoveryPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (SettingsPage.class) {
                if (instance == null) {
                    instance = new WEPDiscoveryPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public final String getTextOfWEPDiscoveryPage(String key) {
        try {
            return getTextBy(WEPDiscoveryProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfSystemRequirementsPage " + e.getMessage()));
            return null;
        }
    }

    public WEPDiscoveryPage(WebDriver driver) throws IOException {
        WEPDiscoveryProperties = WEPDiscoveryPropertiesReader.getObjectRepository("discoveryPage");
    }

    public final boolean matchTextOfWEPDiscoveryPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEPDiscoveryProperties.getProperty(key), Text);
    }

    public final boolean verifyElementsOfWEPDiscoveryPage(String key) throws Exception {
        return verifyElementIsPresent(WEPDiscoveryProperties.getProperty(key));
    }

    public final void clickOnElementsOfWEPDiscoveryPage(String key) throws Exception {
        click(WEPDiscoveryProperties.getProperty(key));
    }

    public final void MouseOverOfWEPDiscoveryPage(String key) throws Exception {
        mouseHover(WEPDiscoveryProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnElementsOfWEPDiscoveryPage(String key) throws Exception {
        clickByJavaScript(WEPDiscoveryProperties.getProperty(key));
    }
}
