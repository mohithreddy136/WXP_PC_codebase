package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.DashboardVariables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;

public class FMDashboardPage extends CommonMethod {

    private ObjectReader FMDashboardPagePropertiesReader = new ObjectReader();
    private Properties FMDashboardPageProperties;
    private final static Logger LOGGER = LogManager.getLogger(FMDashboardPage.class);
    private Properties selectedLanguageProperties;
    public static String environment;
    private FMDashboardPage instance;
    private Properties selecteCredentialsProperties;
    private ObjectReader environmentPropertiesReader = new ObjectReader();
    private Properties environmentProperties;
    public static String uiVersion = System.getProperty("uiVersion");

    public FMDashboardPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (FMDashboardPage.class) {
                if (instance == null) {
                    instance = new FMDashboardPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public FMDashboardPage(WebDriver driver) throws IOException {

        if(isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)){

            FMDashboardPageProperties = FMDashboardPagePropertiesReader.getObjectRepository("FMDashboardPage");
        }

    }

    /**
     * @param language: Language code for localization testing
     * @param localefolder: To specify the folder where the key is present
     * @param key: Contains the string which is localized
     * @return String which is localized
     * @throws Exception
     */
    public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
        selectedLanguageProperties = FMDashboardPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
        return selectedLanguageProperties.getProperty(key);
    }

    public final void waitUntilElementIsVisibleOfFMDashboardPage(String key) throws Exception {
        waitUntilElementIsVisible(FMDashboardPageProperties.getProperty(key));
    }
    public final boolean verifyElementsOfFMDashboardPage(String key) throws Exception {
        return verifyElementIsPresent(FMDashboardPageProperties.getProperty(key));
    }

    public final boolean waitForElementsOfFMDashboardPage(String key) throws Exception {
        return verifyElementIsVisible(FMDashboardPageProperties.getProperty(key));
    }
    public final boolean waitForElementsOfFMDashboardPage1(String key) throws Exception {
        return verifyElementIsVisible(FMDashboardPageProperties.getProperty(key));
    }
    public final boolean waitForElementsOfFMDashboardPageDynamic(String key,int waitTime) throws Exception {
        return verifyElementIsVisibleDynamic(FMDashboardPageProperties.getProperty(key),waitTime);
    }

    public final boolean waitForPresenceOfElementsOfFMDashboardPageDynamic(String key,int waitTime) throws Exception {
        return waitUntillElementIsPresentDynamic(FMDashboardPageProperties.getProperty(key),waitTime);
    }

    public final boolean waitForPresenceOfElementsOfFMDashboardPage(String key) throws Exception {
        return waitUntillElementIsPresent(FMDashboardPageProperties.getProperty(key));
    }

    public final boolean matchTextOfFMDashboardPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(FMDashboardPageProperties.getProperty(key), Text);
    }

    public final boolean verifyElementIsEnableOfFMDashboardPage(String key) throws Exception {
        return verifyElementIsEnable(FMDashboardPageProperties.getProperty(key));
    }

    public final boolean verifyElementIsSelectedOfFMDashboardPage(String key) throws Exception {
        return verifyElementIsSelected(FMDashboardPageProperties.getProperty(key));
    }

    public final String getTextOfFMDashboardPage(String key) throws Exception {
        return getTextBy(FMDashboardPageProperties.getProperty(key));
    }

    public final String getAttributesOfFMDashboardPage(String key, String value) throws Exception {
        return getAttribute(FMDashboardPageProperties.getProperty(key), value);
    }

    public final void clickOnElementsOfFMDashboardPage(String key) throws Exception {
        click(FMDashboardPageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnFMDashboardPage(String key) throws Exception {
        clickByJavaScript(FMDashboardPageProperties.getProperty(key));
    }

    public final void enterTextForFMDashboardPage(String key, String Text) throws Exception {
        enterText(FMDashboardPageProperties.getProperty(key), Text);
    }

    public final boolean verifyElementIsClickableOfFMDashboardPage(String key) throws Exception {
        return verifyElementIsClickable(FMDashboardPageProperties.getProperty(key));
    }

    public void switchToIframeofFMDashboardPage(String key) throws Exception {
        switchToIframe(FMDashboardPageProperties.getProperty(key));
    }

    public final boolean verifyCompanyChangeOfFMDashboardPage(String LanguageCode, String textKey, String companySearchText, String emptyTextKey, String listKey, String dropdownBoxKey) throws Exception {
        return verifySearchFunctionalityUsingSingleSelectDropdown(LanguageCode, FMDashboardPageProperties.getProperty(textKey), companySearchText, FMDashboardPageProperties.getProperty(emptyTextKey), FMDashboardPageProperties.getProperty(listKey), FMDashboardPageProperties.getProperty(dropdownBoxKey));
    }

    public final boolean verifyCompanyChangeOfFMDashboardPageGlobalFilter(String LanguageCode, String textKey, String companySearchText, String emptyTextKey, String listKey, String dropdownBoxKey, String saveButton) throws Exception {
        return verifySearchFunctionalityUsingSingleSelectDropdownRadioButton(LanguageCode, FMDashboardPageProperties.getProperty(textKey), companySearchText, FMDashboardPageProperties.getProperty(emptyTextKey), FMDashboardPageProperties.getProperty(listKey), FMDashboardPageProperties.getProperty(dropdownBoxKey),FMDashboardPageProperties.getProperty(saveButton));
    }


    public final void mouseHoverOfFMDashboardPage(String key) throws Exception {
        mouseHover(FMDashboardPageProperties.getProperty(key));
    }

    public final WebElement getElementOfFMDashboardPage(String key) throws Exception {
        return getElement(FMDashboardPageProperties.getProperty(key));
    }

    public final List<String> mouseHoverOfElementsFMDashboardPage(String key, String key1) throws Exception {
        return gettextmouseHoverelements(FMDashboardPageProperties.getProperty(key), FMDashboardPageProperties.getProperty(key1));
    }

    public final List<WebElement> getElementsOfFMDashboardPage(String key) throws Exception {
        return getAllElements(FMDashboardPageProperties.getProperty(key));
    }

    public final List<WebElement> getElementsTillAllElementsVisibleofFMDashboardPage(String key) throws Exception {
        return getElementsTillAllElementsVisible(FMDashboardPageProperties.getProperty(key));
    }

    public final void switchToDifferentTabOfFMDashboardPage() {
        switchToDifferentTab();
    }

    public final void switchToPreviousTabOfFMDashboardPage() {
        switchBackToPreviousTab();
    }

    public final ArrayList<String> getChartLabelsFMDashboardPage(String key) throws Exception {
        return getLabelsOfChart(FMDashboardPageProperties.getProperty(key));
    }

    public void scrollToFMDashboardPage(String key) throws Exception {
        scrollTillView(FMDashboardPageProperties.getProperty(key));
    }

    public final void scrollUpCharts() {
        jsDriver().executeScript("scroll(0, -250);");
    }

    public final void scrollDownCharts() {
        jsDriver().executeScript("scroll(0, 750);");
    }
    public final String getCredentials(String credentials, String key) throws Exception {
        selecteCredentialsProperties = FMDashboardPagePropertiesReader.getCredentials(credentials);
        return selecteCredentialsProperties.getProperty(key);
    }
    public final List<String> getallTextOfFMDashboardPage(String key) throws Exception {
        return getallTextBy(FMDashboardPageProperties.getProperty(key));
    }

    public final void selectfromDropdownFMDashboardPage(String Locator,String text) throws Exception {
        selecValueFromDropdown(FMDashboardPageProperties.getProperty(Locator),FMDashboardPageProperties.getProperty(text));
    }

    public final void waitUntilElementIsInvisibleOfFMDashboardPage(String key) {
        try {
            verifyElementIsinvisibile(FMDashboardPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfCompanyPage " + e.getMessage()));
        }
    }

    public final boolean isAttributePresentFMDashboardPage(String key, String attribute) throws Exception {
        return isAttributePresent(FMDashboardPageProperties.getProperty(key), attribute);
    }

}
