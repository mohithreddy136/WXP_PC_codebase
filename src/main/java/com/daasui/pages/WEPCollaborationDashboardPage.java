package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class WEPCollaborationDashboardPage extends CommonMethod {
    private WEPCollaborationDashboardPage instance;
    private ObjectReader WEPCollaborationDashboardPagePropertiesReader = new ObjectReader();
    private Properties WEPCollaborationDashboardPageProperties;
    private final static Logger LOGGER = LogManager.getLogger(WEPCollaborationDashboardPage.class);
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");


    public WEPCollaborationDashboardPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPCollaborationDashboardPage.class) {
                if (instance == null) {
                    instance = new WEPCollaborationDashboardPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEPCollaborationDashboardPage(WebDriver driver) throws IOException {
        WEPCollaborationDashboardPageProperties = WEPCollaborationDashboardPagePropertiesReader.getObjectRepository("WEPCollaborationDashboardPage");
    }

    public final boolean verifyElementsOfCollaborationDashboardPage(String key) throws Exception {
        return verifyElementIsPresent(WEPCollaborationDashboardPageProperties.getProperty(key));
    }

    public final void clickOnElementsOfCollaborationDashboardPage(String key) throws Exception {
        click(WEPCollaborationDashboardPageProperties.getProperty(key));
    }

    public final void mouseHoverAndClickOfCollaborationDashboardPage(String key) throws Exception {
        actionClick(WEPCollaborationDashboardPageProperties.getProperty(key));
    }

    public final void actionClickOfCollaborationDashboardPage(String key) throws Exception {
        actionClick(WEPCollaborationDashboardPageProperties.getProperty(key));
    }

    public final List<WebElement> getElementsOfCollaborationDashboardPage(String key) throws Exception {
        return getAllElements(WEPCollaborationDashboardPageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnCollaborationDashboardPage(String key) throws Exception {
        clickByJavaScript(WEPCollaborationDashboardPageProperties.getProperty(key));
    }

    public final WebElement getElementOfCollaborationDashboardPage(String key) throws Exception {
        return getElement(WEPCollaborationDashboardPageProperties.getProperty(key));
    }

    public void scrollTillViewOnCollaborationDashboardPage(String key) throws Exception {
        scrollTillView(WEPCollaborationDashboardPageProperties.getProperty(key));
    }

    public final void listMouseHoverOnCollaborationDashboardPage(String key) throws Exception {
        listMouseHover(WEPCollaborationDashboardPageProperties.getProperty(key));
    }

    public final void enterTextOnCollaborationDashboardPage(String key, String Text) throws Exception {
        enterText(WEPCollaborationDashboardPageProperties.getProperty(key), Text);
    }

    public final void mouseHoverOnCollaborationDashboardPage(String key) throws Exception {
        mouseHover(WEPCollaborationDashboardPageProperties.getProperty(key));
    }

    public final void enterTextByJavaScriptOnCollaborationDashboardPage(String key, String Text) throws Exception {
        enterTextUsingJavaScript(WEPCollaborationDashboardPageProperties.getProperty(key), Text);
    }

    public final boolean verifyTextPresentOnElementofCollaborationDashboardPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEPCollaborationDashboardPageProperties.getProperty(key), Text);
    }

    /**
     * This method designed to get the  Text of WebElement from web page
     * @param key
     */
    public final String getTextOfCollaborationDashboardPage(String key) throws Exception {
        return getTextBy(WEPCollaborationDashboardPageProperties.getProperty(key));
    }
}
