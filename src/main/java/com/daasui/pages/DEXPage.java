package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.DashboardVariables;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static com.daasui.pages.DashboardPage.LanguageCode;

public class DEXPage extends CommonMethod {

    private ObjectReader DEXPagePropertiesReader = new ObjectReader();
    private Properties DEXPageProperties;
    private final static Logger LOGGER = LogManager.getLogger(DEXPage.class);
    private Properties selectedLanguageProperties;
    public static String environment;
    private DEXPage instance;
    private Properties selecteCredentialsProperties;
    private ObjectReader environmentPropertiesReader = new ObjectReader();
    private Properties environmentProperties;
    public static String uiVersion = System.getProperty("uiVersion");

    public DEXPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (DEXPage.class) {
                if (instance == null) {
                    instance = new DEXPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public DEXPage(WebDriver driver) throws IOException {
            DEXPageProperties = DEXPagePropertiesReader.getObjectRepository("DEXPage");
    }

    /**
     * @param language: Language code for localization testing
     * @param localefolder: To specify the folder where the key is present
     * @param key: Contains the string which is localized
     * @return String which is localized
     * @throws Exception
     */
    public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
        selectedLanguageProperties = DEXPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
        return selectedLanguageProperties.getProperty(key);
    }

    public final void waitUntilElementIsVisibleOfDEXPage(String key) throws Exception {
        waitUntilElementIsVisible(DEXPageProperties.getProperty(key));
    }
    public final boolean verifyElementsOfDEXPage(String key) throws Exception {
        return verifyElementIsPresent(DEXPageProperties.getProperty(key));
    }

    public final boolean waitForElementsOfDEXPage(String key) throws Exception {
        return verifyElementIsVisible(DEXPageProperties.getProperty(key));
    }

    public final boolean waitForElementsOfDEXPageDynamic(String key,int waitTime) throws Exception {
        return verifyElementIsVisibleDynamic(DEXPageProperties.getProperty(key),waitTime);
    }

    public final boolean waitForPresenceOfElementsOfDEXPageDynamic(String key,int waitTime) throws Exception {
        return waitUntillElementIsPresentDynamic(DEXPageProperties.getProperty(key),waitTime);
    }

    public final boolean waitForPresenceOfElementsOfDEXPage(String key) throws Exception {
        return waitUntillElementIsPresent(DEXPageProperties.getProperty(key));
    }

    public final boolean matchTextOfDEXPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(DEXPageProperties.getProperty(key), Text);
    }

    public final boolean verifyElementIsEnableOfDEXPage(String key) throws Exception {
        return verifyElementIsEnable(DEXPageProperties.getProperty(key));
    }

    public final boolean verifyElementIsSelectedOfDEXPage(String key) throws Exception {
        return verifyElementIsSelected(DEXPageProperties.getProperty(key));
    }

    public final String getTextOfDEXPage(String key) throws Exception {
        return getTextBy(DEXPageProperties.getProperty(key));
    }

    public final String getAttributesOfDEXPage(String key, String value) throws Exception {
        return getAttribute(DEXPageProperties.getProperty(key), value);
    }

    public final void clickOnElementsOfDEXPage(String key) throws Exception {
       // click(DEXPageProperties.getProperty(key));
        try {
            click(DEXPageProperties.getProperty(key));
            LOGGER.info("Clicked on element with key '" + key + "' successfully.");
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            LOGGER.warn("ElementClickInterceptedException occurred. Attempting JavaScript click.");
            clickByJavaScript(DEXPageProperties.getProperty(key));
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            LOGGER.warn("StaleElementReferenceException occurred. Re-locating the element and retrying.");
            WebElement element = getElementOfDEXPage(key);
            element.click();
        }
        catch (Exception e) {
            LOGGER.error("Exception occurred while clicking on element with key '" + key + "': " + e.getMessage());
            throw e;
        }
    }

    public final void clickByJavaScriptOnDEXPage(String key) throws Exception {
        clickByJavaScript(DEXPageProperties.getProperty(key));
    }

    public final void enterTextForDEXPage(String key, String Text) throws Exception {
        enterText(DEXPageProperties.getProperty(key), Text);
    }

    public final boolean verifyElementIsClickableOfDEXPage(String key) throws Exception {
        return verifyElementIsClickable(DEXPageProperties.getProperty(key));
    }

    public void switchToIframeofDEXPage(String key) throws Exception {
        switchToIframe(DEXPageProperties.getProperty(key));
    }

    public final boolean verifyCompanyChangeOfDEXPage(String LanguageCode, String textKey, String companySearchText, String emptyTextKey, String listKey, String dropdownBoxKey) throws Exception {
        return verifySearchFunctionalityUsingSingleSelectDropdown(LanguageCode, DEXPageProperties.getProperty(textKey), companySearchText, DEXPageProperties.getProperty(emptyTextKey), DEXPageProperties.getProperty(listKey), DEXPageProperties.getProperty(dropdownBoxKey));
    }

    public final boolean verifyCompanyChangeOfDEXPageGlobalFilter(String LanguageCode, String textKey, String companySearchText, String emptyTextKey, String listKey, String dropdownBoxKey, String saveButton) throws Exception {
        return verifySearchFunctionalityUsingSingleSelectDropdownRadioButton(LanguageCode, DEXPageProperties.getProperty(textKey), companySearchText, DEXPageProperties.getProperty(emptyTextKey), DEXPageProperties.getProperty(listKey), DEXPageProperties.getProperty(dropdownBoxKey),DEXPageProperties.getProperty(saveButton));
    }


    public final void mouseHoverOfDEXPage(String key) throws Exception {
        mouseHover(DEXPageProperties.getProperty(key));
    }

    public final WebElement getElementOfDEXPage(String key) throws Exception {
        return getElement(DEXPageProperties.getProperty(key));
    }

    public final List<String> mouseHoverOfElementsDEXPage(String key, String key1) throws Exception {
        return gettextmouseHoverelements(DEXPageProperties.getProperty(key), DEXPageProperties.getProperty(key1));
    }

    public final List<WebElement> getElementsOfDEXPage(String key) throws Exception {
        return getAllElements(DEXPageProperties.getProperty(key));
    }

    public final List<WebElement> getElementsTillAllElementsVisibleofDEXPage(String key) throws Exception {
        return getElementsTillAllElementsVisible(DEXPageProperties.getProperty(key));
    }

    public final void switchToDifferentTabOfDEXPage() {
        switchToDifferentTab();
    }

    public final void switchToPreviousTabOfDEXPage() {
        switchBackToPreviousTab();
    }

    public final ArrayList<String> getChartLabelsDEXPage(String key) throws Exception {
        return getLabelsOfChart(DEXPageProperties.getProperty(key));
    }

    public void scrollToDEXPage(String key) throws Exception {
        scrollTillView(DEXPageProperties.getProperty(key));
    }

    public final void scrollUpCharts() {
        jsDriver().executeScript("scroll(0, -250);");
    }

    public final void scrollDownCharts() {
        jsDriver().executeScript("scroll(0, 750);");
    }
    public final String getCredentials(String credentials, String key) throws Exception {
        selecteCredentialsProperties = DEXPagePropertiesReader.getCredentials(credentials);
        return selecteCredentialsProperties.getProperty(key);
    }
    public final List<String> getallTextOfDEXPage(String key) throws Exception {
        return getallTextBy(DEXPageProperties.getProperty(key));
    }

    public final void selectfromDropdownDEXPage(String Locator,String text) throws Exception {
        selecValueFromDropdown(DEXPageProperties.getProperty(Locator),DEXPageProperties.getProperty(text));
    }

    public final void waitUntilElementIsInvisibleOfDEXPage(String key) {
        try {
            verifyElementIsinvisibile(DEXPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfCompanyPage " + e.getMessage()));
        }
    }

    public final boolean isAttributePresentDEXPage(String key, String attribute) throws Exception {
        return isAttributePresent(DEXPageProperties.getProperty(key), attribute);
    }

    public final void clearTextForDEXPage(String key,String text) throws Exception {
        clearAndReplaceText(DEXPageProperties.getProperty(key),text);
    }

    /** This method will validate Health Summary chart.
     * @return
     * @throws Exception
     */
    public final boolean verifyDDEXDonutChart() throws Exception {
        boolean flag= false;
        String status,noOfDevices= null;
        Actions action = new Actions(getDriver());
        sleeper(3000);
        mouseHoverbyoffsett("donutchartCentreText", 00, 75);
        sleeper(3000);
        status = getTextOfDEXPage("donutChartTootlipText1");
        //sleeper(2000);
        String[] statusSubstring = status.split(" ");
        String scoreCategory = statusSubstring[0];
        noOfDevices = getTextOfDEXPage("donutChartTootlipText2");
        String[] noOfDevicesSubString = noOfDevices.split("\\(");
        int noOfDevicesInt = Integer.parseInt(noOfDevicesSubString[0].trim());
        mouseHoverbyoffsettClick("donutchartCentreText", 00, 75);
        if(verifyScoreCategory(scoreCategory,"scoreColumnValues")) {
            if(verifyCountOfDevices(noOfDevicesInt,LanguageCode)) {
                flag=true;
                LOGGER.info("DDEX Donut chart's validation passed successfully.");
            }
            else
            {
                flag=false;
                LOGGER.error("Count of devices validation in DDEX Donut chart got failed.");
            }
        }
        else
        {
            flag= false;
            LOGGER.error("Score validation in DDEX Bar chart got failed.");
        }
        return flag;
    }

    public final boolean verifyDDEXScoreCharts(String scoreChartBarsLocator) {
        boolean flag = false;
        try {
            if (waitForElementsOfDEXPage(scoreChartBarsLocator)) {
                List<WebElement> chartBars = getElementsOfDEXPage(scoreChartBarsLocator);
                Actions action = new Actions(getDriver());
                action.moveToElement(chartBars.get(0)).build().perform();
                sleeper(3000);
                String status = getTextOfDEXPage("scoreChartTootTipText1");
                sleeper(2000);
                String[] statusParts = status.split(":");
                String scoreCategory = statusParts[0].split(" ")[0];
                int noOfDevices = Integer.parseInt(statusParts[1].replace(" ", "").trim());
                chartBars.get(0).click();
                if (!waitForElementsOfDEXPage("dexGridNoDataMessage")) {
                    if (verifyScoreCategory(scoreCategory, "scoreColumnValues") && verifyCountOfDevices(noOfDevices,LanguageCode)) {
                        flag = true;
                        LOGGER.info("DDEX Score chart's validation passed successfully.");
                    } else {
                        LOGGER.error("Validation in DDEX Score chart failed.");
                    }
                } else {
                    LOGGER.error("DEX grid did not load successfully.");
                }
            } else {
                LOGGER.info("No data is present in Score chart.");
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * This method used for move the mouse left side of total count.
     *  @param key:it is center point from that we start moving.
     * @param left:It is value for moving left from center point
     * @param right:It is value for moving Right from center point
     * @throws Exception
     */
    public final void mouseHoverbyoffsett(String key,int left,int right) throws Exception {
        mouseHoverbyoffset(DEXPageProperties.getProperty(key),left,right);
    }

    /**
     * This method used for click on left side of total count.
     * @param key:it is center point from that we start moving.
     * @param left:It is value for moving left from center point
     * @param right:It is value for moving Right from center point
     * @throws Exception
     */
    public final void mouseHoverbyoffsettClick(String key,int left,int right) throws Exception {
        mouseHoverbyoffsetClick(DEXPageProperties.getProperty(key),left,right);
    }


    /** This method will validate score category on drilldown.
     * @param category
     * @param columnValuesLocator
     * @return
     * @throws Exception
     */
    public final boolean verifyScoreCategory(String category, String columnValuesLocator) throws Exception {
        try {
            boolean flag = false;
            sleeper(3000);
            scrollToDEXPage("dexGridTable");
            List<WebElement> columnValues = getElementsOfDEXPage(columnValuesLocator);
            Set<WebElement> uniqueColumnValues = new HashSet<>(columnValues);

            for (WebElement element : uniqueColumnValues) {
                String value = element.getText().trim();

                // Clean and validate the input string
                if (value.isEmpty() || !value.matches("-?\\d+(\\.\\d+)?")) {
                    LOGGER.error("Invalid numeric value: " + value);
                    return false;
                }

                float numericValue = Float.parseFloat(value);

                switch (category.trim().toLowerCase()) {
                    case "great":
                        if (numericValue < 85) {
                            flag = false;
                            break;
                        } else {
                            flag = true;
                        }
                        break;

                    case "fair":
                        if (numericValue <= 55 || numericValue >= 85) {
                            flag = false;
                            break;
                        } else {
                            flag = true;
                        }
                        break;

                    case "poor":
                        if (numericValue > 55) {
                            flag = false;
                            break;
                        } else {
                            flag = true;
                        }
                        break;

                    default:
                        LOGGER.error("Invalid category: " + category);
                        throw new InputMismatchException("You can use: Great, Fair, Poor only");
                }
            }
            return flag;

        } catch (NumberFormatException e) {
            LOGGER.error("NumberFormatException occurred while parsing numeric value: " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** This method will validate count of devices from chart to Grid.
     * @param noOfDevices
     * @return
     * @throws Exception
     */
    public final boolean verifyCountOfDevices(int noOfDevices,String LanguageCode) throws Exception {
        boolean flag = false;
        sleeper(5000);
        String biosCount = null;
        String paginationText = getTextOfDEXPage("paginationCount");
        String [] paginationsubString = paginationText.split(" ");
        LOGGER.info("Noofdevices: "+noOfDevices);
        LOGGER.info("SubString: "+paginationsubString[0]+" "+paginationsubString[1]);
        String paginationClean = paginationsubString[1].replace(",","");
        LOGGER.info("parsint: "+Integer.parseInt(paginationClean));
        if(waitForPresenceOfElementsOfDEXPage("biosTile")){
        if(getTextOfDEXPage("biosTile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "wex.fleet.mgmt.recommended.missing_critical_bios_updates"))){
            biosCount = getTextOfDEXPage("raTileImpactCount");
            if (biosCount != null && noOfDevices==(Integer.parseInt(biosCount))) {
                LOGGER.info("Filter from View Devices button is working correctly.");
                flag = true;
            }else
            {
                LOGGER.error("Filter from View Devices button is not working correctly. Count from Charts page did not match in Grid page.");
            }
        } }else if(noOfDevices==Integer.parseInt(paginationClean)) {
            flag = true;
        }
        else {
            flag = false;
            LOGGER.error("Count did not match from chart to Grid.");
        }

        return flag;
    }

    public final boolean verifyDDEXDeviceByModelChart(String barchartLocator,String noDataMessage,String chartData,String LanguageCode) throws Exception {
        boolean flag= false;
        String status,noOfDevices= null;
        Actions action = new Actions(getDriver());
        if(verifyElementsOfDEXPage(chartData)) {
            sleeper(3000);
            List<WebElement> chartBars = getElementsOfDEXPage(barchartLocator);
            sleeper(3000);
            action.moveToElement(chartBars.get(0)).build().perform();
            sleeper(3000);
            //scrollToDEXPage("deviceModelChartSecurity");
            status = getTextOfDEXPage("barChartTootTipText2");
            sleeper(2000);
            String[] statusSubstring = status.split(" ");
            String scoreCategory = statusSubstring[0];
            noOfDevices = getTextOfDEXPage("barChartTootTipText1");
            String noOfDevicesClean = noOfDevices.replace(" ","");
            int noOfDevicesInt = Integer.parseInt(noOfDevicesClean.trim());
            chartBars.get(0).click();
            if (verifyScoreCategory(scoreCategory, "scoreColumnValues")) {
                if (verifyCountOfDevices(noOfDevicesInt,LanguageCode)) {
                    flag = true;
                    LOGGER.info("DDEX Bar chart's validation passed successfully.");
                } else {
                    flag = false;
                    LOGGER.error("Count of devices validation in DDEX Bar chart got failed.");
                }
            } else {
                flag = false;
                LOGGER.error("Score validation in DDEX Bar chart got failed.");
            }
        }else if(verifyElementsOfDEXPage(noDataMessage)){
            flag = true;
            LOGGER.info("No data is present in Device Model chart.");
        }else{
            flag = false;
            LOGGER.error("Device Model chart did not load successfully.");
        }
        return flag;
    }

    /** This method will validate Top issues chart on DDEX page.
     * @return
     * @throws Exception
     */
    public final boolean verifyDDEXIssuesChart(String LanguageCode) throws Exception {
        boolean flag= false;
        String noOfDevices,text= null;
        int noOfDevicesInt=0;
        Actions action = new Actions(getDriver());
        if(verifyElementsOfDEXPage("issuesChartBarLocator")) {
            List<WebElement> chartBars = getElementsOfDEXPage("issuesChartBarLocator");
            sleeper(2000);
            action.moveToElement(chartBars.get(0)).build().perform();
            sleeper(5000);
            text= getTextOfDEXPage("barChartTootTipText0");
            if(text.contains("BSOD")){
                action.moveToElement(chartBars.get(1)).build().perform();
                sleeper(5000);
                noOfDevices = getTextOfDEXPage("barChartTootTipText1");
                noOfDevicesInt = Integer.parseInt(noOfDevices.trim());
                chartBars.get(1).click();
            }else {
                noOfDevices = getTextOfDEXPage("barChartTootTipText1");
                noOfDevicesInt = Integer.parseInt(noOfDevices.trim());
                chartBars.get(0).click();
            }
            scrollToDEXPage("paginationCount");
            if (verifyCountOfDevices(noOfDevicesInt,LanguageCode)) {
                flag = true;
                LOGGER.info("DDEX Issues Bar chart's validation passed successfully.");
            } else {
                flag = false;
                LOGGER.error("Count of devices validation in DDEX Issues Bar chart got failed.");
            }
        }
        else{
            LOGGER.info("No data is present in Top Digital Issues chart.");
            flag=true;
        }
        return flag;
    }

    /** This method will validate search box filter.
     * @param columnValuesLocator
     * @param searchBoxLocator
     * @return
     * @throws Exception
     */
    public boolean verifySearchBoxFilterDDEX(String columnValuesLocator, String searchBoxLocator) throws Exception {
        try {
            // Wait for elements to load
            sleeper(3000);

            // Get initial column values
            List<WebElement> columnValues = getElementsOfDEXPage(columnValuesLocator);
            if (columnValues == null || columnValues.isEmpty()) {
                LOGGER.error("No column values found for locator: " + columnValuesLocator);
                return false;
            }

            // Get the text of the first column value
            String searchText = columnValues.get(0).getText();
            enterTextForDEXPage(searchBoxLocator, searchText);

            // Wait for the filter to apply
            sleeper(5000);

            // Get column values after applying the filter
            List<WebElement> columnValuesPostFilter = getElementsOfDEXPage(columnValuesLocator);
            if (columnValuesPostFilter == null || columnValuesPostFilter.isEmpty()) {
                LOGGER.error("No column values found after applying the filter.");
                return false;
            }

            // Verify if all filtered values contain the search text
            for (WebElement element : columnValuesPostFilter) {
                if (!element.getText().contains(searchText)) {
                    LOGGER.error("Search filter is not working. Mismatch found: " + element.getText());
                    return false;
                }
            }

            LOGGER.info("Search filter is working correctly.");
            return true;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in verifySearchBoxFilterDDEX: ", e);
            return false;
        }
    }

    /** This method will validate multi select dropdown.
     * @param columnValuesLocator
     * @param searchBoxLocator
     * @param dropdownValues
     * @return
     * @throws Exception
     */
    public boolean verifyMultiSelectDropdownFilterDDEX(String columnValuesLocator,String searchBoxLocator,String dropdownValues) throws Exception {
        try {
            boolean flag = false;
            List<WebElement> columnValues = getElementsOfDEXPage(columnValuesLocator);
            String s = columnValues.get(0).getText();
            mouseHoverclickOfDEXPage(searchBoxLocator);
            sleeper(3000);
            List<WebElement> dropdownColumnValues = getElementsOfDEXPage(dropdownValues);
            //sleeper(1500);
            for (int i = 0; i < dropdownColumnValues.size(); i++) {
                if (dropdownColumnValues.get(i).getText().equalsIgnoreCase(s)) {
                    sleeper(1500);
                    dropdownColumnValues.get(i).click();
                    sleeper(1500);
                    break;
                }
            }
            waitUntilElementIsInvisibleOfDEXPage("reactSkelaton");
            //sleeper(3000);
            pressEscapeKeysForDEXPage();
            List<WebElement> columnValuesPostFilter = getElementsOfDEXPage(columnValuesLocator);
            System.out.println("post: "+columnValuesPostFilter.get(0).getText());
            System.out.println("pre:"+s);
            for (int i = 0; i < columnValuesPostFilter.size(); i++) {
                if (!columnValuesPostFilter.get(i).getText().contains(s)) {
                    flag = false;
                    LOGGER.error("Multi select filter is not working.");
                    break;
                } else {
                    flag = true;
                }
            }
            return flag;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /** This method will validate comparison range filter.
     * @param category
     * @return
     * @throws Exception
     */
    public boolean verifyComparisonRangeFilter(String category) throws Exception {
        boolean flag = false;
        List<WebElement> columnValues = getElementsOfDEXPage("hardwareScoreValues");
        String s = columnValues.get(0).getText();
        LOGGER.info("pre:"+s);
        mouseHoverclickOfDEXPage("hardwareScoreSearchBox");
        sleeper(2000);
        mouseHoverclickOfDEXPage("compareRangeDropdownBox");
        sleeper(2000);
        List<WebElement> dropdownValues = getElementsOfDEXPage("compareRangeDropdownValues");
        sleeper(2000);
        List<WebElement> columnValuesPostFilter =null;
        switch(category) {
            case "Less than":
                for(int i=0;i<dropdownValues.size();i++) {
                    if(dropdownValues.get(i).getText().equalsIgnoreCase(category)) {
                        dropdownValues.get(i).click();
                        sleeper(2000);
                        enterTextForDEXPage("compareRangeValueInputBox", s);
                        sleeper(2000);
                        clickOnElementsOfDEXPage("submitButtonHWCompareRange");
                        break;
                    }
                }
                if(waitForPresenceOfElementsOfDEXPage("nodataGridPageIcon"))
                {
                    LOGGER.info("No data is present in Grid after applying"+category+" filter.");
                    flag=true;
                    return flag;
                }
                else {
                    columnValuesPostFilter = getElementsOfDEXPage("hardwareScoreValues");
                    LOGGER.info("post:" + columnValuesPostFilter.get(0).getText());
                    for (int i = 0; i < columnValuesPostFilter.size(); i++) {
                        if (!(Float.parseFloat(columnValuesPostFilter.get(i).getText()) < Float.parseFloat(s))) {
                            flag = false;
                            LOGGER.error("Compare Range for" + category + " filter is not working.");
                            break;
                        } else {
                            flag = true;
                        }

                    }
                    return flag;
                }
            case "Greater than":
                for(int i=0;i<dropdownValues.size();i++) {
                    if(dropdownValues.get(i).getText().equalsIgnoreCase(category)) {
                        dropdownValues.get(i).click();
                        sleeper(2000);
                        enterTextForDEXPage("compareRangeValueInputBox", s);
                        sleeper(2000);
                        clickOnElementsOfDEXPage("submitButtonHWCompareRange");
                        break;
                    }
                }

                if(waitForPresenceOfElementsOfDEXPage("nodataGridPageIcon"))
                {
                    LOGGER.info("No data is present in Grid after applying"+category+" filter.");
                    flag=true;
                    return flag;
                }
                else
                {
                    columnValuesPostFilter = getElementsOfDEXPage("hardwareScoreValues");
                    for(int i=0;i<columnValuesPostFilter.size();i++)
                    {
                        if(!(Float.parseFloat(columnValuesPostFilter.get(i).getText())>Float.parseFloat(s)))
                        {
                            flag = false;
                            LOGGER.error("Compare Range for"+category+" filter is not working.");
                            break;
                        }
                        else
                        {
                            flag=true;
                        }
                    }
                }return flag;

            case "Less than or equal to":
                for(int i=0;i<dropdownValues.size();i++) {
                    if(dropdownValues.get(i).getText().equalsIgnoreCase(category)) {
                        dropdownValues.get(i).click();
                        sleeper(2000);
                        enterTextForDEXPage("compareRangeValueInputBox", s);
                        sleeper(2000);
                        clickOnElementsOfDEXPage("submitButtonHWCompareRange");
                        break;
                    }
                }

                if(waitForPresenceOfElementsOfDEXPage("nodataGridPageIcon"))
                {
                    LOGGER.info("No data is present in Grid after applying"+category+" filter.");
                    flag=true;
                    return flag;
                }
                else
                {
                    columnValuesPostFilter = getElementsOfDEXPage("hardwareScoreValues");
                    for(int i=0;i<columnValuesPostFilter.size();i++)
                    {
                        if(!(Float.parseFloat(columnValuesPostFilter.get(i).getText())<=Float.parseFloat(s)))
                        {
                            flag = false;
                            LOGGER.error("Compare Range for"+category+" filter is not working.");
                            break;
                        }
                        else
                        {
                            flag=true;
                        }
                    }
                }return flag;

            case "Greater than or equal to":
                for(int i=0;i<dropdownValues.size();i++) {
                    if(dropdownValues.get(i).getText().equalsIgnoreCase(category)) {
                        dropdownValues.get(i).click();
                        sleeper(2000);
                        enterTextForDEXPage("compareRangeValueInputBox", s);
                        sleeper(2000);
                        clickOnElementsOfDEXPage("submitButtonHWCompareRange");
                        break;
                    }
                }

                if(waitForPresenceOfElementsOfDEXPage("nodataGridPageIcon"))
                {
                    LOGGER.info("No data is present in Grid after applying"+category+" filter.");
                    flag=true;
                    return flag;
                }
                else
                {
                    columnValuesPostFilter = getElementsOfDEXPage("hardwareScoreValues");
                    for(int i=0;i<columnValuesPostFilter.size();i++)
                    {
                        if(!(Float.parseFloat(columnValuesPostFilter.get(i).getText())>=Float.parseFloat(s)))
                        {
                            flag = false;
                            LOGGER.error("Compare Range for"+category+" filter is not working.");
                            break;
                        }
                        else
                        {
                            flag=true;
                        }
                    }
                }return flag;

            default:
                LOGGER.error("Given : " + category + " category is incorrect");
                throw new InputMismatchException("You can use : Good,Fair,Poor only ");
        }
    }

    /**
     * Escape key for dashboard Page
     */
    public final void pressEscapeKeysForDEXPage() throws Exception {
        pressKey(Keys.ESCAPE);
    }

    public final boolean verifyfiltersExpMgmtPage(String filterDropdown,String drodownValues,String selectedFilterText) throws Exception {
        boolean flag = false;
        List<WebElement> dropdownValues =null;
        if(waitForElementsOfDEXPage("filterheaderTab"))
        {
            clickOnElementsOfDEXPage("filterheaderTab");
        }
        else
        {
            LOGGER.error("Filter header tab is not present.");
            return false;
        }
        if(getAttributesOfDEXPage("clearFilterButton", "aria-disabled").equalsIgnoreCase("false")){
            clickOnElementsOfDEXPage("clearFilterButton");
        }
        clickOnElementsOfDEXPage(filterDropdown);
        dropdownValues = getElementsOfExperienceMgmtListPage(drodownValues);
        dropdownValues.get(0).click();
        pressEscapeKeysForDEXPage();
        if(getTextOfDEXPage("appliedFilterText").contains(getTextOfDEXPage(selectedFilterText))) {
            if(waitForElementsOfDEXPage("donutchartCentreText")) {
                mouseHoverbyoffsettClick("donutchartCentreText", 00, 75);
                if(waitForElementsOfDEXPage("appliedFilterTextGridPage")) {
                    if(getTextOfDEXPage("appliedFilterTextGridPage").equalsIgnoreCase(getTextOfDEXPage("appliedFilterText")))
                    {
                        clickOnElementsOfDEXPage("backButtonArrowGridPage");
                        clickOnElementsOfDEXPage("clearFilterButton");
                        flag=true;
                    }
                }else {
                    flag = false;
                    LOGGER.error("Filter did not apply in Grid Page.");
                }
            }
            else if(waitForElementsOfDEXPage("deviceByModelChartBarsLocator")){
                List<WebElement> bars = getElementsOfDEXPage("deviceByModelChartBarsLocator");
                bars.get(0).click();
                if(waitForElementsOfDEXPage("appliedFilterTextGridPage")) {
                    if(getTextOfDEXPage("appliedFilterTextGridPage").equalsIgnoreCase(getTextOfDEXPage("appliedFilterText")))
                    {
                        clickOnElementsOfDEXPage("backButtonArrowGridPage");
                        clickOnElementsOfDEXPage("clearFilterButton");
                        flag=true;
                    }
                }
                else
                {
                    flag = false;
                    LOGGER.error("Filter did not apply in Grid Page.");
                }
            }else if(waitForElementsOfDEXPage("issuesChartBarLocator"))
            {
                List<WebElement> bars = getElementsOfDEXPage("issuesChartBarLocator");
                bars.get(0).click();
                if(waitForElementsOfDEXPage("appliedFilterTextGridPage")) {
                    if(getTextOfDEXPage("appliedFilterTextGridPage").equalsIgnoreCase(getTextOfDEXPage("appliedFilterText")))
                    {
                        clickOnElementsOfDEXPage("backButtonArrowGridPage");
                        clickOnElementsOfDEXPage("clearFilterButton");
                        flag=true;
                    }
                }
                else
                {
                    flag = false;
                    LOGGER.error("Filter did not apply in Grid Page.");
                }
            }
            else
            {
                List<WebElement> errorMessageLocatorList = getElementsOfDEXPage("errorMessageLocator");
                for(int i=0;i<errorMessageLocatorList.size();i++)
                {
                    if(errorMessageLocatorList.get(i).getText().contains("Oops")) {
                        flag = false;
                        LOGGER.error("API got failed after applying filters.");
                        return flag;
                    }
                    flag = true;
                    LOGGER.info("No data is present in charts after applying the filter.");
                }
            }
        }else {
            flag = false;
            LOGGER.error("Filter did not apply on charts.");
            return flag;
        }
        return flag;
    }


    public final boolean verifyFilterApplied(String filterDropdown, String dropdownValues) {
        try {
            boolean flag = false;
            // Check if the filter header tab is present
            if (!waitForElementsOfDEXPage("filterheaderTab")) {
                LOGGER.error("Filter header tab is not present.");
                return false;
            }
            clickOnElementsOfDEXPage("filterheaderTab");

            // Clear existing filters if the clear button is enabled
            if ("false".equalsIgnoreCase(getAttributesOfDEXPage("clearFilterButton", "aria-disabled"))) {
                clickOnElementsOfDEXPage("clearFilterButton");
            }

            // Open the dropdown and select the first value
            clickOnElementsOfDEXPage(filterDropdown);
            clickByJavaScriptOnDEXPage(filterDropdown);
            List<WebElement> dropdownValuesList = getElementsOfDEXPage(dropdownValues);
            if (dropdownValuesList == null || dropdownValuesList.isEmpty()) {
                LOGGER.error("No values found in the dropdown.");
                return false;
            }
            String elementText = dropdownValuesList.get(0).getText();
            dropdownValuesList.get(0).click();
            pressEscapeKeysForDEXPage();
            if(getTextOfDEXPage("appliedFilterText").contains(elementText)) {
                LOGGER.info("Filter applied successfully.");
                flag=true;
            } else {
                LOGGER.error("Filter did not apply successfully.");
                flag=false;
            }

            // Apply the filter

            clickOnElementsOfDEXPage("filterApplyButton");
            return flag;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method verifyFilterApplied: " + e.getMessage());
            return false;
        }
    }

    public boolean verifydexRatingFilter() throws Exception {
        boolean flag = false;
        if(verifyFilterApplied("dexRatingFilterDropdown","dexRatingFilterDropdownValues"))
        {
            if(verifyGridNavigation()) {
                String scoreCategory = getTextOfDEXPage("appliedFilterTextGridPage").split(":")[1].trim();
               // String scoreCategory = getTextOfDEXPage("scoreCategory").substring(getTextOfDEXPage("scoreCategory").indexOf(":") + 1).trim() ;
                if(verifyScoreCategory(scoreCategory,"scoreColumnValues")){
                     flag=true;
                 }
                }else {
                    flag = false;
                    LOGGER.error("Grid did not load successfully after applying filter.");
                }
        }
        return flag;
    }

    public boolean verifyGridNavigation() throws Exception {
        boolean flag = false;
        clickOnElementsOfDEXPage("scoreBarChartData");
        if(!waitForElementsOfDEXPage("dexGridNoDataMessage"))
        {
            resetTableConfiguration();
            flag=true;
            LOGGER.info("Grid page loaded successfully.");
        } else
            {
                flag=false;
                LOGGER.error("Grid page did not load successfully.");
            }
        return flag;
        }



    /**
     * This is a method to get a list of elements present on Incident list page
     * @param key - Locator of element
     * @return - list of web elements
     */
    public final List<WebElement> getElementsOfExperienceMgmtListPage(String key) {
        try {
            return getElementsTillAllElementsPresent(DEXPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getElementOfExperienceMgmtPage " + e.getMessage()));
            return null;
        }
    }
    public final boolean verifyRecommendedActionstile1(String languageCode){
        try{
            boolean flag = false;
            if(waitForElementsOfDEXPage("recommendedActionsContent")){
            if(waitForElementsOfDEXPage("recommendedActionsContent")){
                flag=true;
                LOGGER.info("Recommended Actions tile loaded successfully.");
            }else{
                LOGGER.error("Recommended Actions tile did not load, it has errors.");
            }}else if(waitForElementsOfDEXPage("recommendedActionsNodata")){
                if(getTextOfDEXPage("recommendedActionsDescription").equalsIgnoreCase("We are currently monitoring your enrolled devices to detect opportunities for improvement"))
                {
                    flag = true;
                    LOGGER.info("No data is present in Recommended Actions tile.");
                }else{
                    LOGGER.error("Recommended Actions tile did not load, it has errors.");
                }
            }
            return flag;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public final boolean verifyRecommendedActionstile(String languageCode) {
        boolean flag = false;
        try {
            if (waitForElementsOfDEXPage("recommendedActionsContent")) {
                flag = true;
                LOGGER.info("Recommended Actions tile loaded successfully.");
            } else if (waitForElementsOfDEXPage("recommendedActionsNodata")) {
                if (getTextOfDEXPage("recommendedActionsDescription").equalsIgnoreCase("We are currently monitoring your enrolled devices to detect opportunities for improvement")) {
                    flag = true;
                    LOGGER.info("No data is present in Recommended Actions tile.");
                } else {
                    LOGGER.error("Recommended Actions tile did not load, it has errors.");
                }
            } else {
                LOGGER.error("Recommended Actions tile did not load, it has errors.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public final boolean verifyViewDevicesButton(String Languagecode)
    {
        boolean flag = false;
        String numberOfDevicesString,numberOfDevices,paginationString,paginationCount,biosCount = null;
        try{
            if(waitForElementsOfDEXPage("numberOfDevicePill")){
                numberOfDevicesString = getTextOfDEXPage("numberOfDevicePill");
                String [] numberOfDevicesArray = numberOfDevicesString.split("\n");
                numberOfDevices = numberOfDevicesArray[0];
                String categoryname = getTextOfDEXPage("raCategoryName");
                clickOnElementsOfDEXPage("numberOfDevicePill");
                waitForPageLoaded();
                if(getTextOfDEXPage("raTileCategory").contains(categoryname))
                {
                    sleeper(3000);
                    paginationString = getTextOfDEXPage("paginationCount");
                    String [] paginationStringArray = paginationString.split(" ");
                    paginationCount = paginationStringArray[1];
                    System.out.println("numberofdevices "+numberOfDevices);
                    System.out.println("pagination "+paginationCount);
                    if(getTextOfDEXPage("biosTile").equalsIgnoreCase(getTextLanguage(Languagecode, "daas_ui", "wex.fleet.mgmt.recommended.missing_critical_bios_updates")) ||
                            getTextOfDEXPage("biosTile").contains("BSOD")){
                        biosCount = getTextOfDEXPage("raTileImpactCount");
                        if(numberOfDevices.equalsIgnoreCase(biosCount)){
                            LOGGER.info("Filter from View Devices button is working correctly.");
                            flag = true;
                        }else
                        {
                            LOGGER.error("Filter from View Devices button is not working correctly. Count from Charts page did not match in Grid page.");
                        }
                    }
                    else if(numberOfDevices.contains(paginationCount)){
                        LOGGER.info("Filter from View Devices button is working correctly.");
                        flag = true;
                    }else
                    {
                        LOGGER.error("Filter from View Devices button is not working correctly. Count from Charts page did not match in Grid page.");
                    }
                }else
                {
                    LOGGER.error("Exp mgmt page Grid page did not load successfully from View Devices button.");
                }
                return flag;
            }
            else if(waitForElementsOfDEXPage("recommendedActionsNodata")){
                LOGGER.info("No data is present in Recommended Actions tile.");
                return true;
            }else{
                LOGGER.error("Recommended Actions tile did not load, it has errors.");
                return false;
            }
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public final boolean verifyDEXDetailsNavigation(){
        boolean flag = false;
        Actions action = new Actions(getDriver());
        try {
            if(waitForElementsOfDEXPage("trendChartDatainChart")) {
                scrollToDEXPage("trendChartDatainChart");
                List<WebElement> chartBars = getElementsOfDEXPage("totalpointsOnChart");
                sleeper(3000);
                action.moveToElement(chartBars.get(chartBars.size()-1)).build().perform();
                chartBars.get(chartBars.size()-1).click();
                sleeper(3000);
                if(!waitForElementsOfDEXPage("dexGridNoDataMessage")){
                    flag = true;
                    LOGGER.info(" Experience Management Grid page loaded successfully.");
                }
                else
                {
                    LOGGER.error("Experience Management Grid page did not load successfully.");
                }
            }else if(waitForElementsOfDEXPage("nodataOnTrendChart")){
                LOGGER.error("No data is present in Weekly Trend Chart.");
                flag=false;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean verifyDeviceDetailNavigation(String Languagecode){
        boolean flag = false;
        Actions action = new Actions(getDriver());
        try {
            if(waitForElementsOfDEXPage("trendChartDatainChart")) {
                List<WebElement> chartBars = getElementsOfDEXPage("totalpointsOnChart");
                sleeper(3000);
                action.moveToElement(chartBars.get(chartBars.size()-1)).build().perform();

                chartBars.get(chartBars.size()-1).click();
                sleeper(3000);
                scrollToDEXPage("serialNumberSearchBox");
                if(waitForElementsOfDEXPage("serialNumberColumnValues")){
                    wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
                    sleeper(3000);
                    List<WebElement> serialNumberList = getElementsOfDEXPage("serialNumberColumnValues");

                    serialNumberList.get(0).click();
                    // Loop through the list
//                    for (int i = 0; i < serialNumberList.size(); i++) {
//                        try {
//                            // Re-locate the element just before clicking
//                            WebElement item = serialNumberList.get(0);
//                            // Wait for the element to be clickable
//                            WebElement clickableItem = wait.until(ExpectedConditions.elementToBeClickable(item));
//                            clickableItem.click();
//                        } catch (org.openqa.selenium.StaleElementReferenceException e) {
//                            // Handle StaleElementReferenceException by re-locating the element
//                            serialNumberList = getElementsOfDEXPage("serialNumberColumnValues");  // Re-fetch the list
//                        }
//                    }
                    sleeper(5000);
                    if(waitForElementsOfDEXPage("deviceDetailIcon")) {
                       // if (getTextOfDEXPage("devicedetailsBreadCrums").equalsIgnoreCase(getTextLanguage(Languagecode, "daas_ui", "breadcrumbs.asset_details"))) {
                            flag = true;
                            LOGGER.info("Device details page loaded successfully from Experience Management tab.");
//                        } else {
//                            LOGGER.error("Device Details Page did not load successfully from Exp Mgmt Page.");
//                        }
                    }else{
                        LOGGER.error("Device Details Page did not load successfully from Exp Mgmt Page.");
                        flag = false;
                    }
                }else{
                    LOGGER.error("No data is present in Grid table.");
                    flag = false;
                }
            }else{
                LOGGER.error("No data is present in Weekly Trend Chart.");
                flag=false;
            }
            return flag;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean verifyExpmgmtGridNavigation(String Languagecode){
        boolean flag = false;
        Actions action = new Actions(getDriver());
        try {
            //if(!verifyElementsOfDEXPage("nodataOnTrendChart")) {
            if(verifyElementsOfDEXPage("totalpointsOnChart")) {
                List<WebElement> chartBars = getElementsOfDEXPage("trendchartXaxisData");
                sleeper(3000);
                action.moveToElement(chartBars.get(chartBars.size()-1)).build().perform();
                chartBars.get(chartBars.size()-1).click();
                if(!waitForElementsOfDEXPage("dexGridNoDataMessage")){
                    flag = true;
                    LOGGER.info(" Experience Management Grid page loaded successfully.");
                }
                else
                {
                    LOGGER.error("Experience Management Grid page did not load successfully.");
                }
            }else if(verifyElementsOfDEXPage("nodataOnTrendChart")){
                LOGGER.error("No data is present in Weekly Trend Chart.");
                flag=false;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean verifyExportFunctionality(String languageCode, String notificationMessage) {
        try {
            // Select the first checkbox
            if (!waitForElementsOfDEXPage("firstCheckbox")) {
                LOGGER.error("First checkbox is not present on the page.");
                return false;
            }
          //  clickOnElementsOfDEXPage("firstCheckbox");
           // LOGGER.info("First checkbox selected successfully.");

            // Click on the export button
            if (!waitForElementsOfDEXPage("exportButton")) {
                LOGGER.error("Export button is not present on the page.");
                return false;
            }
            clickOnElementsOfDEXPage("exportButton");
            sleeper(3000);

            // Validate the toast notification
            String actualMessage = getTextOfDEXPage(notificationMessage);
            String expectedMessage = getTextLanguage(languageCode, "daas_ui", "campaign.response.export.data.success");
            if (actualMessage.equalsIgnoreCase(expectedMessage)) {
                LOGGER.info("Toast Notification generated successfully for Export of DDEX Devices.");
                return true;
            } else {
                LOGGER.error("Export API is not working correctly. Expected: " + expectedMessage + ", but got: " + actualMessage);
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred during export functionality verification: ", e);
            return false;
        }
    }

    public final boolean verifyAppExportFunctionality(String languageCode, String notificationMessage, String exportButton) {
        try {

            // Click on the export button
            if (!waitForElementsOfDEXPage(exportButton)) {
                LOGGER.error("Export button is not present on the page.");
                return false;
            }
            clickOnElementsOfDEXPage(exportButton);
            sleeper(3000);

            // Validate the toast notification
            String actualMessage = getTextOfDEXPage(notificationMessage);
            String expectedMessage = getTextLanguage(languageCode, "daas_ui", "campaign.response.export.data.success");
            if (actualMessage.equalsIgnoreCase(expectedMessage)) {
                LOGGER.info("Toast Notification generated successfully for Export of DDEX Devices.");
                return true;
            } else {
                LOGGER.error("Export API is not working correctly. Expected: " + expectedMessage + ", but got: " + actualMessage);
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred during export functionality verification: ", e);
            return false;
        }
    }

    public final boolean verifyExportFunctionalityAllDEX(String languageCode, String notificationMessage,String exportButton) {
        try {
            // Click on the export button
            if (!waitForElementsOfDEXPage(exportButton)) {
                LOGGER.error("Export button is not present on the page.");
                return false;
            }
            clickOnElementsOfDEXPage(exportButton);
            clickOnElementsOfDEXPage("exportXls");
            sleeper(3000);

            // Validate the toast notification
            String actualMessage = getTextOfDEXPage(notificationMessage);
            String expectedMessage = getTextLanguage(languageCode, "daas_ui", "campaign.response.export.data.success");
            if (actualMessage.equalsIgnoreCase(expectedMessage)) {
                LOGGER.info("Toast Notification generated successfully for Export of DDEX Devices.");
                return true;
            } else {
                LOGGER.error("Export API is not working correctly. Expected: " + expectedMessage + ", but got: " + actualMessage);
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred during export functionality verification: ", e);
            return false;
        }
    }

    public final boolean verifyTroubleshootButton(String Languagecode)
    {
        boolean flag = false;
        try {
            if (waitForElementsOfDEXPage("remediationGuideViewMoreButton")) {
                clickOnElementsOfDEXPage("remediationGuideViewMoreButton");
                scrollToDEXPage("troubleshootButton");
                clickOnElementsOfDEXPage("troubleshootButton");
                switchToDifferentTabOfDEXPage();
                if (waitForElementsOfDEXPage("troubleshootGuideTitle")) {
                    LOGGER.info("Troubleshoot button is working correctly.");
                    flag = true;
                    switchToParentTab();
                } else {
                    LOGGER.error("Troubleshoot button is not working correctly.");
                    return false;
                }
                return flag;
            } else if (waitForElementsOfDEXPage("recommendedActionsNodata")) {
                LOGGER.info("No data is present in Recommended Actions tile.");
                return true;
            } else {
                LOGGER.error("Troubleshoot button is not working correctly.");
                return false;
            }
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    /**
     * This method is used to verify the loading of charts on Experience Management page.
     * @param Languagecode
     * @param nodataMessage
     * @param chartData
     * @param chartName
     * @return
     */

    public final boolean verifyExperienceManagementChartLoading(String Languagecode, String nodataMessage, String chartData,String chartName) {
        boolean flag = false;
        try {
           switch (chartName){
                case "Top System Health Issues":
                case "Top OS Performance Issues":
                case "Top Security Issues":
               case  "Top Network Issues":
                    if (waitForElementsOfDEXPage(chartData)) {
                        LOGGER.info(chartName+ " Chart is loading successfully.");
                        flag = true;
                    } else if (getTextOfDEXPage(nodataMessage).equalsIgnoreCase(getTextLanguage(Languagecode, "daas_ui", "digital_exp_score_nodata_title"))) {
                        LOGGER.info("No data is present in " +chartName+"Chart.");
                        flag =  true;
                    } else {
                        LOGGER.error(chartName+ " Chart is not loading.");
                        flag= false;
                    }
                    break;
                case "Recommended Actions":
                    if (waitForElementsOfDEXPage(chartData)) {
                        LOGGER.info(chartName+ " Chart is loading successfully.");
                        flag = true;
                    } else if (getTextOfDEXPage(nodataMessage).equalsIgnoreCase(getTextLanguage(Languagecode, "daas_ui", "wex.widget.recommended.actions.nodata.title"))) {
                        LOGGER.info("No data is present in " +chartName+"Chart.");
                        flag =  true;
                    } else {
                        LOGGER.error(chartName+ " Chart is not loading.");
                        flag= false;
                    }
                    break;
               case "Application Score":
               case "Network Meter Chart":
               case "Experience Score Chart":
               case "Experience Score Overtime Chart":
                   if (waitForElementsOfDEXPage(chartData)) {
                       LOGGER.info(chartName+ " Chart is loading successfully.");
                       flag = true;
                   }  else {
                       LOGGER.error(chartName+ " Chart is not loading.");
                       flag= false;
                   }
                   break;
                default:
                    if (waitForElementsOfDEXPage(chartData)) {
                        LOGGER.info(chartName+ " Chart is loading successfully.");
                        flag = true;
                    } else if (getTextOfDEXPage(nodataMessage).equalsIgnoreCase(getTextLanguage(Languagecode, "daas_ui", "global.no_data_to_display"))) {
                        LOGGER.info("No data is present in " +chartName+"Chart.");
                        flag =  true;
                    } else if(getTextOfDEXPage(nodataMessage).equalsIgnoreCase(getTextLanguage(Languagecode, "daas_ui", "dashboard.bios.action.everything_is_great"))){
                        LOGGER.info("No data is present in " +chartName+"Chart.");
                        flag =  true;
                    }
                    else {
                        LOGGER.error(chartName+ " Chart is not loading.");
                        flag= false;
                    }
                    break;
            }
            return flag;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public final boolean verifyWeeklyTrendChart(){

        boolean flag = false;
        try {
            if(verifyElementsOfDEXPage("totalpointsOnChart")) {
                List<WebElement> chartBars = getElementsOfDEXPage("totalpointsOnChart");
                sleeper(3000);
                if(chartBars.size()<=39) {
                    flag=true;
                    LOGGER.info("13 Datelist weeks are visible in Weekly Trend Chart.");
                }else{
                    LOGGER.error("13 Datelist weeks are not visible in Weekly Trend Chart.");
                    flag=false;
                }
            }else if(verifyElementsOfDEXPage("nodataOnTrendChart")){
                LOGGER.error("No data is present in Weekly Trend Chart.");
                flag=false;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * This method is used to verify header column on Experience Management Grid page.
     *
     * @param languageCode: This is language code used for multiple languages.
     * @param keyHeaderOnReportPage: Header on the drill down page
     * @param headerNamesOnReportPage: Values in the header section on the drill down page
     * @return
     * @throws Exception
     */
    public final boolean verifyHeaderExpMgmtGridPage(String languageCode, String keyHeaderOnReportPage, String[] headerNamesOnReportPage)
    {
        boolean flag = false;
        try{
            if(waitForElementsOfDEXPageDynamic(keyHeaderOnReportPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)){
                List<WebElement> listOfOptions = getElementsOfDEXPage(keyHeaderOnReportPage);
                for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
                    String [] cleanText = listOfOptions.get(listOfOptionsCounter).getText().split("\n");
                    if (cleanText[0].equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", headerNamesOnReportPage[listOfOptionsCounter]).trim())) {
                        flag = true;
                    } else {
                        LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText() + " Header does not match at Exp Mgmt Grid page.");
                        flag = false;
                        break;

                    }
                }
            }else{
                LOGGER.error("Exp Mgmt Grid page did not load in 1 minute.");
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return flag;
    }

    public final boolean verifyHeaderAsPerPlanExpMgmtGridPage(String languageCode, String keyHeaderOnReportPage, String[] headerNamesOnReportPage) {
        try {
            if (waitForElementsOfDEXPageDynamic(keyHeaderOnReportPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
                List<WebElement> listOfOptions = getElementsOfDEXPage(keyHeaderOnReportPage);
                for (String header : headerNamesOnReportPage) {
                    for (WebElement option : listOfOptions) {
                        String[] cleanText = option.getText().split("\n");
                        if (cleanText[0].equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", header).trim())) {
                            LOGGER.error("Header '" + header + "' is found at Exp Mgmt Grid page. Failing the check.");
                            return false;
                        }
                    }
                }
            } else {
                LOGGER.error("Exp Mgmt Grid page did not load in 1 minute.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public final boolean verifyChangesSinceLastWeek(String LanguageCode)
    {
        boolean flag = false;
        try{
            if(waitForElementsOfDEXPage("itemFirst")){
                String	noOfDevices = getTextOfDEXPage("itemFirst");
                int noOfDevicesInt = Integer.parseInt(noOfDevices.trim());
                if(!(noOfDevicesInt==0)){
                    clickOnElementsOfDEXPage("itemFirst");
                    if(waitForElementsOfDEXPageDynamic("columnListGridPage", DashboardVariables.DASHBOARD_REPORTS_WAIT)){
                        if (verifyCountOfDevices(noOfDevicesInt,LanguageCode)) {
                            flag = true;
                            LOGGER.info("Count of devices validation in Changes Since Last Week chart got passed.");
                        } else {
                            flag = false;
                            LOGGER.error("Count of devices validation in Changes Since Last Week chart got failed.");
                        }
                    }}else{
                    LOGGER.info("No data is present in Changes Since Last Week chart.");
                    flag=true;
                }
            }else if(waitForElementsOfDEXPage("nodataIconChangesSinceLastWeek")){
                LOGGER.info("No data is present in Changes Since Last Week chart.");
                flag=true;
            }
            return flag;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifydexChartDashboardNavigation(String expandButton,String expmgmtTab)
    {
        boolean flag = false;
        try {
            //scrollToDEXPage(expandButton);
            if (waitForElementsOfDEXPage(expandButton)) {
                clickOnElementsOfDEXPage(expandButton);
                waitUntilElementIsInvisibleOfDEXPage("reactSkelaton");
                if (waitForElementsOfDEXPage(expmgmtTab)) {
                    flag = true;
                    LOGGER.info("Navigated to Experience Management page "+expmgmtTab+" from Dashboard successfully.");
                } else {
                    LOGGER.error("Experience Management page did not load successfully when navigated from Dashboard.");
                }
            } else {
                LOGGER.error("Dex Charts on Dashboard page did not load successfully.");
                flag = false;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean verifyFleetScorePreferenceTile(String Languagecode,String editIcon){
        boolean flag = false;
        try{
            if(waitForElementsOfDEXPage(editIcon))
            {
                clickOnElementsOfDEXPage(editIcon);
                List<WebElement> listOfOptions = getElementsOfDEXPage("listofCheckBoxes");
                sleeper(3000);
                if(isAttributePresentDEXPage("resettoDefaultButton","disabled"))
                {
                    for(int i=0;i<listOfOptions.size()-2;i++){
                        listOfOptions.get(i).click();
                    }
                    LOGGER.info("Changed two KPIs in Fleet Score Preference tile.");
                    clickOnElementsOfDEXPage("saveButtonFleetScorePref");
                }else
                {
                    clickOnElementsOfDEXPage("resettoDefaultButton");
                    LOGGER.info("Reset to Default button is working correctly.");
                    clickOnElementsOfDEXPage("saveButtonFleetScorePref");
                }
                if(getTextOfDEXPage("toastNotificationFleetScorePref").contains("Fleet Score Preferences Were Modified at"))
                {
                    flag = true;
                    LOGGER.info("Fleet Score Preference tile is working correctly.");
                }else
                {
                    LOGGER.error("Fleet Score Preference tile is not working correctly.Toast Notification is not coming.");
                    flag= false;
                }
            }
            else
            {
                LOGGER.error("Edit icon is not present on Fleet Score Preference tile.");
                flag = false;
            }
            return flag;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public final boolean verifyClickAndValidateElement(String elementToClick,String elementToValidate){
        boolean flag = false;
        try{
            if(waitForElementsOfDEXPage(elementToClick)){
                clickOnElementsOfDEXPage(elementToClick);
                if(waitForElementsOfDEXPage(elementToValidate)){
                    flag = true;
                    LOGGER.info("Element is clicked and validated successfully.");
                }else{
                    LOGGER.error("Element is not validated successfully.");
                    flag = false;
                }
            }else{
                LOGGER.error("Element is not present on the page.");
                flag = false;
            }
            return flag;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public final void mouseHoverclickOfDEXPage(String key) throws Exception {
        mouseHover(DEXPageProperties.getProperty(key));
        actionClick(DEXPageProperties.getProperty(key));
    }

    /**
     * This method is used to verify the sorting of columns on Experience Management Grid page.
     * @param columnName: This is the column name on which sorting is to be done.
     * @param columnHeader: This is the header of the column.
     * @return
     */
    public final boolean sortApplicationColumnDesc(String columnName, String columnHeader) {
        try {
            if (waitForElementsOfDEXPage(columnName)) {
                LOGGER.info("Application crash Column is not sorted.");
                clickOnElementsOfDEXPage(columnHeader);
            }

            if (!getAttributesOfDEXPage(columnName,"aria-sort").equalsIgnoreCase("descending")) {
                clickOnElementsOfDEXPage(columnHeader);
                if (!getAttributesOfDEXPage(columnName,"aria-sort").equalsIgnoreCase("descending")) {
                    LOGGER.error("Application Column is not sorted in Descending order.");
                    return false;
                }
            }

            LOGGER.info("Application Column is sorted in Descending order.");
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to verify the number of Crashes/Freezes in chart.
     * @param listOfCrashesFreezes: This is the column name on which sorting is to be done.
     * @return
     */
    public final boolean verifyNoOfCrashesFreezes(String listOfCrashesFreezes) {
        try {
            List<WebElement> listOfItems = getElementsOfDEXPage(listOfCrashesFreezes);
            if (listOfItems.size() <= 10) {
                LOGGER.info("Correct number of Crashes and Freezes are present in chart.");
                return true;
            } else {
                LOGGER.error("Incorrect number of Crashes and Freezes are present in chart.");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred while validating number of Crashes and Freezes.", e);
            return false;
        }
    }

//    /**
//     * This method is used to verify the Top Crashes and Freezes chart.
//     * @param appCrashNameList
//     * @param appCrashCountList
//     * @param appcountGrid
//     * @return
//     */
//    public final boolean verifyTopCrashesFreezesChart(String appCrashNameList, String appCrashCountList, String appcountGrid) {
//        try {
//            List<WebElement> listofAppname = getElementsOfDEXPage(appCrashNameList);
//            List<WebElement> listofAppCount = getElementsOfDEXPage(appCrashCountList);
//            List<WebElement> listofAppnameGrid = getElementsOfDEXPage("listOfAppNames");
//            List<WebElement> listofAppCountGrid = getElementsOfDEXPage(appcountGrid);
//
//            Map<String, String> mapChartData = new HashMap<>();
//            Map<String, String> mapGridData = new HashMap<>();
//
//            // Normalize and populate mapChartData
//            for (int i = 0; i < listofAppname.size(); i++) {
//                String appName = listofAppname.get(i).getText().trim().toLowerCase();
//                String appCount = listofAppCount.get(i).getText().trim();
//                mapChartData.put(appName, appCount);
//            }
//
//            // Normalize and populate mapGridData
//            for (int i = 0; i < listofAppnameGrid.size(); i++) {
//                String appName = listofAppnameGrid.get(i).getText().trim().toLowerCase();
//                String appCount = listofAppCountGrid.get(i).getText().trim();
//                mapGridData.put(appName, appCount);
//            }
//
//            LOGGER.info("mapChartData: " + mapChartData);
//            LOGGER.info("mapGridData: " + mapGridData);
//            // Compare the maps
//            if (mapChartData.equals(mapGridData)) {
//                LOGGER.info("Top Crashes and Freezes chart is validated successfully.");
//                return true;
//            } else {
//                LOGGER.error("Top Crashes and Freezes chart is not validated successfully.");
//                return false;
//            }
//        } catch (Exception e) {
//            LOGGER.error("Exception occurred while validating Top Crashes and Freezes chart.", e);
//            return false;
//        }
//    }

    /**
     * This method is used to verify the Top Crashes and Freezes chart.
     * @param appCrashNameList
     * @param appCrashCountList
     * @param appcountGrid
     * @return
     */
    public final boolean verifyTopCrashesFreezesChart(String appCrashNameList, String appCrashCountList, String appcountGrid) {
        try {
            List<WebElement> listofAppname = getElementsOfDEXPage(appCrashNameList);
            List<WebElement> listofAppCount = getElementsOfDEXPage(appCrashCountList);
            List<WebElement> listofAppnameGrid = getElementsOfDEXPage("listOfAppNames");
            List<WebElement> listofAppCountGrid = getElementsOfDEXPage(appcountGrid);

            Map<String, String> mapChartData = new HashMap<>();
            Map<String, String> mapGridData = new HashMap<>();

            // Normalize and populate mapChartData
            for (int i = 0; i < listofAppname.size(); i++) {
                String appName = listofAppname.get(i).getText().trim().toLowerCase();
                String appCount = listofAppCount.get(i).getText().trim();
                mapChartData.put(appName, appCount);
            }

            // Normalize and populate mapGridData
            for (int i = 0; i < listofAppnameGrid.size(); i++) {
                String appName = listofAppnameGrid.get(i).getText().trim().toLowerCase();
                String appCount = listofAppCountGrid.get(i).getText().trim();
                mapGridData.put(appName, appCount);
            }

            LOGGER.info("mapChartData: " + mapChartData);
            LOGGER.info("mapGridData: " + mapGridData);

            // Check if all entries in mapChartData exist in mapGridData with matching values
            for (Map.Entry<String, String> entry : mapChartData.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (!mapGridData.containsKey(key) || !mapGridData.get(key).equals(value)) {
                    LOGGER.error("Top Crashes and Freezes chart validation failed. Missing or mismatched entry: " + key + " = " + value);
                    return false;
                }
            }

            LOGGER.info("Top Crashes and Freezes chart is validated successfully.");
            return true;
        } catch (Exception e) {
            LOGGER.error("Exception occurred while validating Top Crashes and Freezes chart.", e);
            return false;
        }
    }


    /**
     * This method is used to verify the Crashes and Freezes chart.
     * @return
     */
    public final boolean verifyCrashesAndFreezesChart()
    {
        boolean flag;
        try {
            if(verifyElementsOfDEXPage("expandButton")) {
                mouseHoverclickOfDEXPage("expandButton");
                List<WebElement> chartBars = getElementsOfDEXPage("listofDates");
                sleeper(3000);
                if(chartBars.size()<=13) {
                    flag=true;
                    LOGGER.info("13 Datelist weeks are visible in Crashes and Freezes Chart.");
                }else{
                    LOGGER.error("13 Datelist weeks are not visible in Crashes and Freezes Chart.");
                    flag=false;
                }
            }else {
                LOGGER.error("No data is present in Crashes and Freezes Chart.");
                flag=false;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyHomeNavigation(String dexChevron,String dexTab){
        boolean flag = false;
        try{
            if(verifyElementsOfDEXPage("homeButton")){
                clickOnElementsOfDEXPage("homeButton");
                if(waitForElementsOfDEXPage(dexChevron)){
                    clickOnElementsOfDEXPage(dexChevron);
                    if(waitForElementsOfDEXPage(dexTab))
                    {
                        flag = true;
                        LOGGER.info("Navigated to " + dexTab + " successfully.");
                    }
                }else{
                    LOGGER.error(dexChevron+ "did not load successfully.");
                    flag = false;
                }
            }else{
                LOGGER.error("Home button is not present on the page.");
                flag = false;
            }
            return flag;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyNetworkScoreChart(String nwCategory,String nwCount) throws Exception {
        boolean flag ;
        String countText = null;
        if(waitForElementsOfDEXPage("scoreChartContent")){
            clickOnElementsOfDEXPage(nwCategory);
            countText = getTextOfDEXPage(nwCount);
            String count = countText.split(" ")[0];
            sleeper(3000);
            String paginationText = getTextOfDEXPage("paginationCount");
            String [] paginationsubString = paginationText.split(" ");
            LOGGER.info("Noofdevices: "+count);
            LOGGER.info("SubString: "+paginationsubString[0]+" "+paginationsubString[1]);
            String paginationClean = paginationsubString[1].replace(",","");
            LOGGER.info("parsint: "+Integer.parseInt(paginationClean));
            if(Integer.parseInt(count)==Integer.parseInt(paginationClean)) {
                flag = true;
            }
            else {
                flag = false;
                LOGGER.error("Count did not match from chart to Grid.");
            }
            return flag;
        }
        return false;
    }

    public boolean verifyNetworkBarChart(String barLocator,String tooltipLocator,String LanguageCode) throws Exception {
        boolean flag;
        String noOfDevices= null;
        Actions action = new Actions(getDriver());
        if(verifyElementsOfDEXPage(barLocator)) {
            List<WebElement> chartBars = getElementsOfDEXPage(barLocator);
            sleeper(2000);
            action.moveToElement(chartBars.get(0)).build().perform();
            sleeper(5000);
            noOfDevices = getTextOfDEXPage(tooltipLocator);
            int noOfDevicesInt = Integer.parseInt(noOfDevices.trim());
            chartBars.get(0).click();
            scrollToDEXPage("paginationCount");
            if (verifyCountOfDevices(noOfDevicesInt,LanguageCode)) {
                flag = true;
                LOGGER.info("DDEX NW Issues Bar chart's validation passed successfully.");
            } else {
                flag = false;
                LOGGER.error("Count of devices validation in NW Issues Bar chart got failed.");
            }
        }
        else{
            LOGGER.info("No data is present in Top NW Issues chart.");
            flag=true;
        }
        return flag;

    }

    public boolean verifyHomeNavigationforApps(String dexChevron, String dexTab, String appCharts) {
        boolean flag = false;
        try {
            if (verifyElementsOfDEXPage("homeButton")) {
                clickOnElementsOfDEXPage("homeButton");
                if (waitForElementsOfDEXPage(dexChevron)) {
                    clickOnElementsOfDEXPage(dexChevron);
                    if (waitForElementsOfDEXPage(dexTab)) {
                        flag = true;
                        LOGGER.info("Navigated to " + dexTab + " successfully.");
                        clickOnElementsOfDEXPage(dexTab);
                        if (waitForElementsOfDEXPage(appCharts)) {
                            flag = true;
                            LOGGER.info("Navigated to " + appCharts + " successfully.");
                        }
                    }

                } else {
                    LOGGER.error(dexChevron + "did not load successfully.");
                    flag = false;
                }
            } else {
                LOGGER.error("Home button is not present on the page.");
                flag = false;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean compareCategoryCounts(String greatChartCount, String fairChartCount, String poorChartCount) throws Exception {
        boolean flag = false;
        String greatcountText, faircountText, poorcountText = null;
        greatcountText = getTextOfDEXPage(greatChartCount);
        String greatcount = greatcountText.split(" ")[0];
        faircountText = getTextOfDEXPage(fairChartCount);
        String faircount = faircountText.split(" ")[0];
        poorcountText = getTextOfDEXPage(poorChartCount);
        String poorcount = poorcountText.split(" ")[0];
        int totalChartCount = Integer.parseInt(greatcount + faircount + poorcount);

        String paginationText = getTextOfDEXPage("paginationCount");
        String[] paginationsubString = paginationText.split(" ");
        LOGGER.info("Noofapps: " + totalChartCount);
        LOGGER.info("SubString: " + paginationsubString[0] + " " + paginationsubString[1]);
        String paginationClean = paginationsubString[1].replace(",", "");
        LOGGER.info("parsint: " + Integer.parseInt(paginationClean));
        if (totalChartCount == Integer.parseInt(paginationClean)) {
            flag = true;
        } else {
            flag = false;
            LOGGER.error("Count did not match from chart to Grid.");
        }
        return flag;
    }

    public final boolean verifyNoOfAppsInCharts(String listOfApps) {
        try {
            if (waitForElementsOfDEXPage(listOfApps)) {
                List<WebElement> listOfItems = getElementsOfDEXPage(listOfApps);

                if (listOfItems.size() <= 10) {
                    LOGGER.info("Correct number of Apps are present in chart.");
                    return true;
                } else {
                    LOGGER.error("Incorrect number of Apps are present in chart.");
                    return false;
                }
            } else {
                LOGGER.info("No data is present in charts.");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred while validating number of Apps with Most Errors.", e);
            return false;
        }
    }

    public boolean verifyCorrectOrderofAppsinCharts(String listappNames, String counts) {
        try {
            if (waitForElementsOfDEXPage(listappNames)) {
                List<WebElement> appNames = getElementsOfDEXPage(listappNames);
                List<WebElement> Counts = getElementsOfDEXPage(counts);

                for (int i = 0; i < appNames.size() - 1; i++) {
                    double currentCount = Double.parseDouble(Counts.get(i).getText().replace(",", ""));
                    double nextCount = Double.parseDouble(Counts.get(i + 1).getText().replace(",", ""));

                    if (currentCount < nextCount) {
                        LOGGER.info("Error: The chart is not in the correct order.");
                        return false;
                    }
                }

                LOGGER.info("Chart is showing apps in the correct order.");
                return true;

            } else {
                LOGGER.info("No data is present in chart.");
                return false;
            }

        } catch (Exception e) {
            LOGGER.error("Exception occurred while validating correct order", e);
            return false;
        }
    }

    public boolean validateAppsWithMostErrorsOvertimeChart(String listofAppnamesfromAppsWithMostErrorsChart, String listofAppnamesfromAppsWithMostErrorsOverTimeChart) {
        try {
            List<WebElement> firstChartApps = getElementsOfDEXPage(listofAppnamesfromAppsWithMostErrorsChart);
//            if (firstChartApps.size() < 5) {
//                LOGGER.info("Apps with most errors chart has less than 5 apps.");
//                return false;
//            }
            for (int i = 0; i < firstChartApps.size(); i++) {
                String appName = firstChartApps.get(i).getText();
                boolean isPresent = false;
                List<WebElement> secondChartApps = getElementsOfDEXPage(listofAppnamesfromAppsWithMostErrorsOverTimeChart);
                for (WebElement app : secondChartApps) {
                    if (app.getText().equalsIgnoreCase(appName)) {
                        isPresent = true;
                        break;
                    }
                }
                if (!isPresent) {
                    LOGGER.info("App not found in Apps with most errors over time chart: " + appName);
                    return false;
                }
            }
            LOGGER.info("All top 5 apps from first chart are present in Apps with most errors over time chart.");
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final boolean verifyAppsDashboardGridNavigation(String Languagecode){
        boolean flag = false;
        try {
            if(verifyElementsOfDEXPage("listOfAppNames")) {
                if(waitForElementsOfDEXPage("listOfAppNames")){
                    flag = true;
                    LOGGER.info(" Apps Dashboard Grid page loaded successfully.");
                }
                else
                {
                    LOGGER.error("Apps Dashboard Grid page did not load successfully.");
                }
            }else if(verifyElementsOfDEXPage("appDashboardGridNoData")){
                LOGGER.error("No data is present in Apps Dashboard.");
                flag=true;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean verifyUpgradeButton(String upgradeButton) {
        boolean flag = false;
        try {
            if (waitForElementsOfDEXPage(upgradeButton)) {
                clickOnElementsOfDEXPage(upgradeButton);
                switchToDifferentTabOfDEXPage();
                if (waitForPresenceOfElementsOfDEXPage("pricePlanHeader")) {
                    LOGGER.info("Upgrade button is working correctly for " + upgradeButton + " .");
                    flag = true;
                    switchToPreviousTabOfDEXPage();
                } else {
                    LOGGER.error("Upgrade button is not working correctly.");
                    return false;
                }
            }
            return flag;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

        public final boolean verifyAddOnButton(String collaborationAddOn) {
            boolean flag = false;
            try {
                if (waitForElementsOfDEXPage(collaborationAddOn)) {
                    clickOnElementsOfDEXPage(collaborationAddOn);
                    if (waitForPresenceOfElementsOfDEXPage("addOnHeader")) {
                        LOGGER.info("Add on button is working correctly.");
                        flag = true;
                    } else {
                        LOGGER.error("Add On button is not working correctly.");
                        return false;
                    }
                }
                return flag;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                return false;
            }}


    public final boolean verifyCustomizeExpScore(String plan) throws Exception {
        try {
            boolean flag = false;
            Map<String, Integer> inputValues = new LinkedHashMap<>();

            // Generate random values that sum to 100
            if(!plan.equalsIgnoreCase("Standard")) {
                inputValues = generateRandomValues();
            }else{
               inputValues = generateRandomValuesStd();
            }

            // Clear and set input fields dynamically
            for (Map.Entry<String, Integer> entry : inputValues.entrySet()) {
                clearTextForDEXPage(entry.getKey(), String.valueOf(entry.getValue()));
            }
            String newScore = getTextOfDEXPage("newScore");
            clickOnElementsOfDEXPage("saveButtonSub");
            // Validate new score and toast notification
            if (validateToastNotification("toastNotificationFleetScorePref", "Overall Experience Score was customized")) {

                String overallScore = getTextOfDEXPage("overallScoreExpScoreChart");
                if (newScore.equalsIgnoreCase(overallScore)) {
                    flag = true;
                    LOGGER.info("Customize Experience Score is verified successfully.");
                } else {
                    LOGGER.error("Customize Experience Score verification failed.");
                }
            } else {
                LOGGER.error("Toast Notification is not displayed for Customize Experience Score.");
            }

            return flag;

        } catch (Exception e) {
            LOGGER.error("Exception occurred while verifying Customize Experience Score: ", e);
            return false;
        } finally {
            resetCustomizeExpScore();
        }
    }

    private Map<String, Integer> generateRandomValuesStd() {
        Random random = new Random();
        int remainingSum = 100;

        Map<String, Integer> values = new LinkedHashMap<>();

        // Generate random values for each key
        values.put("sentimentInputText", random.nextInt(remainingSum + 1));
        remainingSum -= values.get("sentimentInputText");

        values.put("systemHealthInputText", random.nextInt(remainingSum + 1));
        remainingSum -= values.get("systemHealthInputText");

        // Assign the remaining sum to the last key
        values.put("osPerformanceInputText", remainingSum);

        return values;
    }

    private Map<String, Integer> generateRandomValues() {
        Random random = new Random();
        int remainingSum = 100;

        Map<String, Integer> values = new LinkedHashMap<>();
        values.put("sentimentInputText", random.nextInt(remainingSum + 1));
        remainingSum -= values.get("sentimentInputText");

        values.put("systemHealthInputText", random.nextInt(remainingSum + 1));
        remainingSum -= values.get("systemHealthInputText");

        values.put("osPerformanceInputText", random.nextInt(remainingSum + 1));
        remainingSum -= values.get("osPerformanceInputText");

        values.put("networkInputText", random.nextInt(remainingSum + 1));
        remainingSum -= values.get("networkInputText");

        values.put("securityInputText", random.nextInt(remainingSum + 1));
        remainingSum -= values.get("securityInputText");

        values.put("appInputText", remainingSum);

        return values;
    }

    private boolean validateToastNotification(String toastLocator, String expectedMessage) throws Exception {
        String toastMessage = getTextOfDEXPage(toastLocator);
        return toastMessage.contains(expectedMessage);
    }

    private void resetCustomizeExpScore() throws Exception {
        clickOnElementsOfDEXPage("customizeButton");
        scrollToDEXPage("resetToDefaultButtonSub");
        sleeper(2000);
        clickOnElementsOfDEXPage("resetToDefaultButtonSub");
        sleeper(2000);
        scrollToDEXPage("saveButtonSub");
        clickOnElementsOfDEXPage("saveButtonSub");

        if (validateToastNotification("toastNotificationFleetScorePref", "The weight of 1 or more sub-scores was adjusted")) {
            LOGGER.info("Customize Experience Score has been reset successfully.");
        } else {
            LOGGER.error("Toast Notification is not displayed after resetting Customize Experience Score.");
        }
    }

    public boolean verifyDevicesAppScore(){
        boolean flag =  false;
        try {
            if (waitForPresenceOfElementsOfDEXPage("insAppScoreLegends")) {
                List<WebElement> appScoreLegends = getElementsOfDEXPage("insAppDeviceCounts");
                scrollDownCharts();
                String paginationCount = getTextOfDEXPage("paginationCount");
                String[] paginationsubString = paginationCount.split(" ");
                String paginationClean = paginationsubString[1].replace(",", "");
                sleeper(3000);
                if(Integer.parseInt(paginationClean)!=0){
                    // Check if all appScoreLegends are 0
                    boolean allZero = true;
                    for (WebElement legend : appScoreLegends) {
                        String legendText = legend.getText().trim();
                        String[] parts = legendText.split(" ");
                        if (!parts[0].equals("0")) {
                            allZero = false;
                            break;
                        }
                    }

                    if (!allZero) {
                        flag = true;
                        LOGGER.info("App Score legends have non-zero values.");
                    } else {
                        LOGGER.error("All App Score legends are 0.");
                    }
                }else{
                    LOGGER.info("Pagination count is 0, hence skipping App Score legends validation.");
                    flag=true;
                }

            } else {
                LOGGER.error("App Score legends are not present on Experience Management page.");
                flag = false;
            }
        }catch(Exception e){
            LOGGER.error(e.getMessage());
        }
        return flag;
    }
    
    /** This method will validate Click on DEXPage.
     * @return
     * @throws Exception
     */
    public final void actionClickOfDEXPage(String key) throws Exception {
	   	actionClick(DEXPageProperties.getProperty(key));
	   }
    
    	/**
    	 * This method will verify the Experience Score Dashboard.
    	 * @param languageCode
    	 * @return
    	 * @throws Exception
    	 */

	public boolean verifyDEXExpScoreDashboard(String languageCode) throws Exception {
	    boolean result = true;
	    result &= verifyElementsOfDEXPage("expScoreLink");
	    clickOnElementsOfDEXPage("expScoreLink");
	    result &= verifyElementsOfDEXPage("expscoredbSystemhealthScore");
	    result &= verifyElementsOfDEXPage("expscoredbSystemhealthOverTime");
	    result &= verifyElementsOfDEXPage("expscoredbOSPerfScore");
	    result &= verifyElementsOfDEXPage("expscoredbOSPerfOverTime");
	    result &= verifyElementsOfDEXPage("expscoredbSecurityScore");
	    result &= verifyElementsOfDEXPage("expscoredbSceurityOverTime");
	    result &= verifyElementsOfDEXPage("expscoredbNWHeathScore");
	    result &= verifyElementsOfDEXPage("expscoredbInstalledApplicationsScore");
	    return result;
	}
	
	/**
	 * This method will verify the Changes Since Last Week report.
	 * @param LanguageCode
	 * @return
	 */
	public final boolean verifyChangesSinceLastWeekReport(String LanguageCode) {
	    boolean flag = false;
	    try {
	        if (waitForElementsOfDEXPage("itemFirst")) {
	            String noOfDevices = getTextOfDEXPage("itemFirst");
	            if (noOfDevices == null || noOfDevices.trim().isEmpty()) {
	                LOGGER.error("No device count found in itemFirst.");
	                return false;
	            }
	            int noOfDevicesInt;
	            try {
	                noOfDevicesInt = Integer.parseInt(noOfDevices.trim());
	            } catch (NumberFormatException e) {
	                LOGGER.error("Failed to parse device count: " + noOfDevices);
	                return false;
	            }
	            if (noOfDevicesInt != 0) {
	                clickOnElementsOfDEXPage("itemFirst");
	                if (waitForElementsOfDEXPageDynamic("columnListGridPage", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
	                    if (verifyCountOfDevices(noOfDevicesInt, LanguageCode)) {
	                        flag = true;
	                        LOGGER.info("Count of devices validation in Changes Since Last Week chart got passed.");
	                    } else {
	                        flag = false;
	                        LOGGER.error("Count of devices validation in Changes Since Last Week chart got failed.");
	                    }
	                    clickOnElementsOfDEXPage("anylaticsDoubleBreadcrum");
	                    sleeper(2000);
	                    clickOnElementsOfDEXPage("analyticsvreadcrumb");
	                }
	            } else {
	                LOGGER.info("No data is present in Changes Since Last Week chart.");
	                flag = true;
	            }
	        } else if (waitForElementsOfDEXPage("nodataIconChangesSinceLastWeek")) {
	            LOGGER.info("No data is present in Changes Since Last Week chart.");
	            flag = true;
	        }
	        return flag;
	    } catch (Exception e) {
	        LOGGER.error("Exception in verifyChangesSinceLastWeekReport: ", e);
	        return false;
	    }
	}
	
	/**
	 * This method will verify the DDEX Issues Chart.
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyLDKDDEXIssuesChart(String LanguageCode) throws Exception {
	    boolean flag= false;
	    String noOfDevices,text= null;
	    int noOfDevicesInt=0;
	    Actions action = new Actions(getDriver());
	    if(verifyElementsOfDEXPage("issuesChartBarLocator")) {
	        List<WebElement> chartBars = getElementsOfDEXPage("issuesChartBarLocator");
	        sleeper(2000);
	        action.moveToElement(chartBars.get(0)).build().perform();
	        sleeper(5000);
	        text= getTextOfDEXPage("barChartTootTipText0");
	        if(text.contains("BSOD")){
	            action.moveToElement(chartBars.get(1)).build().perform();
	            sleeper(5000);
	            noOfDevices = getTextOfDEXPage("barChartTootTipText1");
	            noOfDevicesInt = Integer.parseInt(noOfDevices.trim());
	            chartBars.get(1).click();
	        }else {
	            noOfDevices = getTextOfDEXPage("barChartTootTipText1");
	            noOfDevicesInt = Integer.parseInt(noOfDevices.trim());
	            chartBars.get(0).click();
	        }
	        scrollToDEXPage("paginationCount");
	        if (verifyCountOfDevices(noOfDevicesInt,LanguageCode)) {
	            flag = true;
	            LOGGER.info("DDEX Issues Bar chart's validation passed successfully.");
	        } else {
	            flag = false;
	            LOGGER.error("Count of devices validation in DDEX Issues Bar chart got failed.");
	        }
	        verifyElementsOfDEXPage("breadCrumSystemHealth");
	        actionClickOfDEXPage("breadCrumSystemHealth");
	    }
	    else{
	        LOGGER.info("No data is present in Top Digital Issues chart.");
	        flag=true;
	        verifyElementsOfDEXPage("breadCrumSystemHealth");
	        actionClickOfDEXPage("breadCrumSystemHealth");
	    }
	    return flag;
	}
	
	/**
	 * This method will verify the DDEX Score Chart.
	 * @param scoreChartBarsLocator
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyLDKDDEXScoreCharts(String scoreChartBarsLocator) {
	    boolean flag = false;
	    try {
	        if (waitForElementsOfDEXPage(scoreChartBarsLocator)) {
	            List<WebElement> chartBars = getElementsOfDEXPage(scoreChartBarsLocator);
	            Actions action = new Actions(getDriver());
	            action.moveToElement(chartBars.get(0)).build().perform();
	            sleeper(3000);
	            String status = getTextOfDEXPage("scoreChartTootTipText1");
	            sleeper(2000);
	            String[] statusParts = status.split(":");
	            String scoreCategory = statusParts[0].split(" ")[0];
	            int noOfDevices = Integer.parseInt(statusParts[1].replace(" ", "").trim());
	            chartBars.get(0).click();
	            if (!waitForElementsOfDEXPage("dexGridNoDataMessage")) {
	                if (verifyScoreCategory(scoreCategory, "scoreColumnValues") && verifyCountOfDevices(noOfDevices,LanguageCode)) {
	                    flag = true;
	                    LOGGER.info("DDEX Score chart's validation passed successfully.");
	                } else {
	                    LOGGER.error("Validation in DDEX Score chart failed.");
	                }
	                verifyElementsOfDEXPage("breadCrumSystemHealth");
	                actionClickOfDEXPage("breadCrumSystemHealth");
	            } else {
	                LOGGER.error("DEX grid did not load successfully.");
	                verifyElementsOfDEXPage("breadCrumSystemHealth");
	                actionClickOfDEXPage("breadCrumSystemHealth");
	            }
	        } else {
	            LOGGER.info("No data is present in Score chart.");
	            flag = true;
	            verifyElementsOfDEXPage("breadCrumSystemHealth");
	            actionClickOfDEXPage("breadCrumSystemHealth");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return flag;
	}
	
	/**
	 * This method will validate System Health Summary Chart.
	 * @param softAssert
	 * @param languageCode
	 * @throws Exception
	 */
	public void validateSystemHealthSummaryChart(SoftAssert softAssert, String languageCode) throws Exception {
	    softAssert.assertTrue(verifyElementsOfDEXPage("systemHealthScores"), "systemHealthScores Summary report missing.");
	    sleeper(2000);
	    softAssert.assertTrue(getTextOfDEXPage("scoreTitleSysHealth").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "exp.mgmt.details.hardwareScore")), "System Health Summary Chart title is incorrect.");
	    sleeper(2000);
	    Assert.assertTrue(verifyElementsOfDEXPage("scoreChartData"), "System Health Summary Chart is not loading.");
	    sleeper(2000);
	    Assert.assertTrue(verifyLDKDDEXScoreCharts("sysHealthscoreChartBarsLocator"), "System Health Summary Chart is not working.");
	    Thread.sleep(2000);
	}
	
	/**
	 * This method will validate Top System Health Issues Chart.
	 * @param softAssert
	 * @param languageCode
	 * @throws Exception
	 */
	public void validateTopSystemHealthIssues(SoftAssert softAssert, String languageCode) throws Exception {
	    softAssert.assertTrue(verifyElementsOfDEXPage("topSystemHealthIssue"), "topSystemHealthIssue Summary report missing.");
	    sleeper(2000);
	    softAssert.assertTrue(getTextOfDEXPage("topSystemHealthIssue").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "exp.mgmt.details.topSystemHealthIssues")), "Top System Health Issues Chart title is incorrect.");
	    sleeper(2000);
	    Assert.assertTrue(verifyLDKDDEXIssuesChart(languageCode), "System Health Issues Chart is not working.");
	    sleeper(2000);
	}
	
	/**
	 * This method will validate OS Performance Summary Chart.
	 * @param softAssert
	 * @param languageCode
	 * @throws Exception
	 */
	public void validateOSPerformanceSummaryChart(SoftAssert softAssert, String languageCode) throws Exception {
	    softAssert.assertTrue(verifyElementsOfDEXPage("osPerformanceScores"), "OS Performance Summary report missing.");
	    softAssert.assertTrue(getTextOfDEXPage("scoreTitleOSPerf")
	        .equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "exp.mgmt.details.performanceScore")),
	        "OS Performance Summary Chart title is incorrect.");
	    Assert.assertTrue(verifyElementsOfDEXPage("scoreChartData"), "OS Performance Summary Chart is not loading.");
	    Assert.assertTrue(verifyLDKDDEXScoreCharts("osPerfscoreChartBarsLocator"), "OS Performance Summary Chart is not working.");
	}
	
	/**
	 * This method will validate Top OS Performance Issues Chart.
	 * @param softAssert
	 * @param languageCode
	 * @throws Exception
	 */
	public void validateTopOSPerformanceIssues(SoftAssert softAssert, String languageCode) throws Exception {
	    softAssert.assertTrue(verifyElementsOfDEXPage("topOSPerformanceIssues"), "top OS Performance Issues report missing.");
	    softAssert.assertTrue(
	        getTextOfDEXPage("topOSPerformanceIssues").equalsIgnoreCase(
	            getTextLanguage(languageCode, "daas_ui", "exp.mgmt.details.topOSPerformanceIssues")
	        ),
	        "Top OS Performance Issues Chart title is incorrect.");
	    Assert.assertTrue(verifyLDKDDEXIssuesChart(languageCode), "osSystemReliability Top Issues Chart is not working.");
	    waitForPageLoaded();
	}
	
	/**
	 * This method will validate Network Score Chart.
	 * @param softAssert
	 * @param languageCode
	 * @throws Exception
	 */
	public void validateSecurityScoreChart(SoftAssert softAssert, String languageCode) throws Exception {
	    softAssert.assertTrue(verifyElementsOfDEXPage("securityScoreExp"), "Security Score report missing.");
	    softAssert.assertTrue( getTextOfDEXPage("scoreTitleSecurity").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "exp.mgmt.details.securityScore")),
	        "Security Score Chart title is incorrect."
	    );
	    Assert.assertTrue(verifyElementsOfDEXPage("scoreChartData"), "Security Score Chart is not loading.");
	    Assert.assertTrue(verifyLDKDDEXScoreCharts("securityBarsLocator"), "Security Score Chart is not working.");
	}
	
	/**
	 * This method will validate Top Security Issues Chart.
	 * @param softAssert
	 * @param languageCode
	 * @throws Exception
	 */
	public void validateTopSecurityIssues(SoftAssert softAssert, String languageCode) throws Exception {
	    softAssert.assertTrue(verifyElementsOfDEXPage("topSecurityIssues"), "Top Security Issues Summary report missing.");
	    softAssert.assertTrue(
	        getTextOfDEXPage("topSecurityIssues").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "exp.mgmt.details.topSecurityIssues")),
	        "Top Security Issues Chart title is incorrect.");
	    sleeper(2000);
	    Assert.assertTrue(verifyLDKDDEXIssuesChart(languageCode), "Security Issues Chart is not working.");
	    waitForPageLoaded();
	}
	/**
	 * This method will validate All Network Score Charts.
	 * @param languageCode
	 * @return
	 * @throws Exception
	 */
	
	public boolean verifyAllNetworkScoreCharts(String languageCode) throws Exception {
	    boolean great = verifyNetworkScoreChart("nwGreatLegends", "nwGreatLegendsCount");
	    waitForPageLoaded();
	    boolean fair = verifyNetworkScoreChart("nwFairLegends", "nwFairLegendsCount");
	    waitForPageLoaded();
	    boolean poor = verifyNetworkScoreChart("nwPoorLegends", "nwPoorLegendsCount");
	    waitForPageLoaded();
	    sleeper(2000);
//	    boolean issues = verifyNetworkBarChart("topIssuesChartDataNW", "topIssuesTooltip", languageCode);
//	    waitForPageLoaded();
//	    boolean authType = verifyNetworkBarChart("nwAuthTypeBars", "nwAuthTypeTooltip", languageCode);
//	    waitForPageLoaded();
	    return great && fair && poor ;
	}
	
	/**
	 * This method will validate Installed Application Score Section.
	 * @return
	 * @throws Exception
	 */
	public boolean validateInstalledApplicationScoreSection() throws Exception {
	    return verifyElementsOfDEXPage("installedApplicationScore") &&
	           verifyElementsOfDEXPage("appWithMostCrashes1") &&
	           verifyElementsOfDEXPage("appWithMostFreezes1") &&
	           verifyElementsOfDEXPage("crashesAndFreezesOverTime1");
	} 
	
	/**
	 * This method will get the text of WEX Score based on key.
	 * @param key
	 * @return
	 * @throws Exception
	 */
    public final String getTextOfWEXScore(String key) throws Exception {
	return getTextBy(DEXPageProperties.getProperty(key));
	}


    /**
     * Clicks the 'View Details' button on the Application Top Crashes chart.
     */
    public void clickViewDetailsButton() throws Exception {
        clickOnElementsOfDEXPage("viewDetailsButton");
    }

    /**
     * Retrieves the list of app names from the Top App Crashes chart on the main page.
     */
    public List<String> getTopCrashAppNamesMain(String topAppsMain) throws Exception {
        List<WebElement> appNameElements = getElementsOfDEXPage(topAppsMain);
        List<String> appNames = new ArrayList<>();
        for (WebElement el : appNameElements) {
            appNames.add(el.getText().trim());
        }
        return appNames;
    }

    /**
     * Retrieves the list of impacted device counts from the Top App Crashes chart on the main page.
     */
    public List<String> getTopCrashImpactedDevicesMain(String topAppsDevices) throws Exception {
        List<WebElement> deviceCountElements = getElementsOfDEXPage(topAppsDevices);
        List<String> deviceCounts = new ArrayList<>();
        for (WebElement el : deviceCountElements) {
            deviceCounts.add(el.getText().trim());
        }
        return deviceCounts;
    }

    /**
     * Retrieves the list of app names (max 10) from the Top App Crashes chart on the details page.
     *
     * @param appNames the locator key for app name elements
     * @return list of up to 10 app names
     * @throws Exception if element retrieval fails
     */
    public List<String> getTopCrashAppNamesDetails(String appNames) throws Exception {
        List<WebElement> appNameElements = getElementsOfDEXPage(appNames);
        List<String> appNamesList = new ArrayList<>();

        int limit = Math.min(appNameElements.size(), 10);
        for (int i = 0; i < limit; i++) {
            appNamesList.add(appNameElements.get(i).getText().trim());
        }

        return appNamesList;
    }

    /**
     * Retrieves the list of impacted device counts (max 10) from the Top App Crashes chart on the details page.
     *
     * @param impactedDevices the locator key for impacted device count elements
     * @return list of up to 10 impacted device counts
     * @throws Exception if element retrieval fails
     */
    public List<String> getTopCrashImpactedDevicesDetails(String impactedDevices) throws Exception {
        List<WebElement> deviceCountElements = getElementsOfDEXPage(impactedDevices);
        List<String> deviceCounts = new ArrayList<>();

        int limit = Math.min(deviceCountElements.size(), 10);
        for (int i = 0; i < limit; i++) {
            deviceCounts.add(deviceCountElements.get(i).getText().trim());
        }

        return deviceCounts;
    }


    /**
     * Checks if the Top App Crashes section is present on the details page.
     */
    public boolean isTopAppCrashesSectionPresent(String appData) throws Exception {
        return !getElementsOfDEXPage(appData).isEmpty();
    }

    public void applyRangeFilter1() throws Exception {
        clickOnElementsOfDEXPage("freezeHeader");
        clickOnElementsOfDEXPage("rangePickerAppFreeze");
        clickOnElementsOfDEXPage("greaterThanOption");
        enterTextForDEXPage("inputBoxAppFreeze","0");
        clickOnElementsOfDEXPage("submitButtonRangePicker");
    }

    /**
     * Applies a range filter on the freeze column with "greater than 0" condition.
     *
     * @throws Exception if any element interaction fails
     */
    public void applyRangeFilter(String columnHeader,String rangePicker) throws Exception {
        try {
            // Wait for freeze header to be clickable before proceeding
            if (!waitForElementsOfDEXPage(columnHeader)) {
                LOGGER.error("Freeze header not found on page");
                throw new Exception("Unable to locate freeze header element");
            }

            scrollToDEXPage(columnHeader);
            sleeper(3000);
            clickOnElementsOfDEXPage(columnHeader);
            sleeper(3000); // Brief pause for dropdown to appear

            clickOnElementsOfDEXPage(rangePicker);
            sleeper(3000);
            clickOnElementsOfDEXPage("greaterThanOption");
            sleeper(3000);

            // Clear existing text before entering new value
            clearTextForDEXPage("inputBoxAppFreeze", "0");

            clickOnElementsOfDEXPage("submitButtonRangePicker");

            // Wait for filter to be applied
            waitUntilElementIsInvisibleOfDEXPage("reactSkelaton");

            LOGGER.info("Range filter applied successfully: Freeze count > 0");
        } catch (Exception e) {
            LOGGER.error("Failed to apply range filter: " + e.getMessage());
            throw e;
        }
    }


//    public boolean verifyTrendChartLastDataPoint(String trendchartXaxisData) {
//        try {
//            // Get the last data point date from the trend chart
//            String lastDataPointDate = getLastTrendChartDataPoint(trendchartXaxisData);
//
//            if (lastDataPointDate == null || lastDataPointDate.isEmpty()) {
//                LOGGER.error("Unable to retrieve last data point date from trend chart");
//                return false;
//            }
//
//            // Normalize the date format by removing the period if present
//            String normalizedDate = lastDataPointDate.replace(".", "").trim();
//
//            // Try multiple date formats to handle both "19 Feb" and "Feb 19" cases
//            List<SimpleDateFormat> dateFormats = Arrays.asList(
//                    new SimpleDateFormat("dd MMM", Locale.ENGLISH),
//                    new SimpleDateFormat("MMM dd", Locale.ENGLISH)
//            );
//
//            Calendar chartDate = null;
//            for (SimpleDateFormat dateFormat : dateFormats) {
//                try {
//                    dateFormat.setLenient(false);
//                    chartDate = Calendar.getInstance();
//                    chartDate.setTime(dateFormat.parse(normalizedDate));
//                    chartDate.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
//                    break; // Parsing succeeded, exit loop
//                } catch (Exception ex) {
//                    chartDate = null; // Reset and try next format
//                }
//            }
//
//            if (chartDate == null) {
//                LOGGER.error("Unable to parse date '{}' with any known format", lastDataPointDate);
//                return false;
//            }
//
//            // Get today and yesterday dates
//            Calendar today = Calendar.getInstance();
//            Calendar yesterday = Calendar.getInstance();
//            yesterday.add(Calendar.DATE, -1);
//
//            // Compare dates (ignore time)
//            chartDate.set(Calendar.HOUR_OF_DAY, 0);
//            chartDate.set(Calendar.MINUTE, 0);
//            chartDate.set(Calendar.SECOND, 0);
//            chartDate.set(Calendar.MILLISECOND, 0);
//
//            today.set(Calendar.HOUR_OF_DAY, 0);
//            today.set(Calendar.MINUTE, 0);
//            today.set(Calendar.SECOND, 0);
//            today.set(Calendar.MILLISECOND, 0);
//
//            yesterday.set(Calendar.HOUR_OF_DAY, 0);
//            yesterday.set(Calendar.MINUTE, 0);
//            yesterday.set(Calendar.SECOND, 0);
//            yesterday.set(Calendar.MILLISECOND, 0);
//
//            boolean isValid = chartDate.equals(today) || chartDate.equals(yesterday);
//
//            if (!isValid) {
//                LOGGER.error("Last data point date '{}' is older than yesterday", lastDataPointDate);
//            } else {
//                LOGGER.info("Last data point date '{}' is valid (today or yesterday)", lastDataPointDate);
//            }
//
//            return isValid;
//
//        } catch (Exception e) {
//            LOGGER.error("Error validating trend chart last data point: {}", e.getMessage());
//            return false;
//        }
//    }

    public boolean verifyTrendChartLastDataPoint(String trendchartXaxisData) {
        try {
            // Get the last data point date from the trend chart
            String lastDataPointDate = getLastTrendChartDataPoint(trendchartXaxisData);

            if (lastDataPointDate == null || lastDataPointDate.isEmpty()) {
                LOGGER.error("Unable to retrieve last data point date from trend chart");
                return false;
            }

            // Normalize the date format by removing the period if present
            String normalizedDate = lastDataPointDate.replace(".", "").trim();

            // Try multiple date formats to handle both "19 Feb" and "Feb 19" cases
            List<SimpleDateFormat> dateFormats = Arrays.asList(
                    new SimpleDateFormat("dd MMM", Locale.ENGLISH),
                    new SimpleDateFormat("MMM dd", Locale.ENGLISH)
            );

            Calendar chartDate = null;
            for (SimpleDateFormat dateFormat : dateFormats) {
                try {
                    dateFormat.setLenient(false);
                    chartDate = Calendar.getInstance();
                    chartDate.setTime(dateFormat.parse(normalizedDate));
                    chartDate.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                    break; // Parsing succeeded, exit loop
                } catch (Exception ex) {
                    chartDate = null; // Reset and try next format
                }
            }

            if (chartDate == null) {
                LOGGER.error("Unable to parse date '{}' with any known format", lastDataPointDate);
                return false;
            }

            // Get today, yesterday and day before yesterday dates
            Calendar today = Calendar.getInstance();
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DATE, -1);
            Calendar dayBeforeYesterday = Calendar.getInstance();
            dayBeforeYesterday.add(Calendar.DATE, -2);

            // Compare dates (ignore time)
            chartDate.set(Calendar.HOUR_OF_DAY, 0);
            chartDate.set(Calendar.MINUTE, 0);
            chartDate.set(Calendar.SECOND, 0);
            chartDate.set(Calendar.MILLISECOND, 0);

            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            yesterday.set(Calendar.HOUR_OF_DAY, 0);
            yesterday.set(Calendar.MINUTE, 0);
            yesterday.set(Calendar.SECOND, 0);
            yesterday.set(Calendar.MILLISECOND, 0);

            dayBeforeYesterday.set(Calendar.HOUR_OF_DAY, 0);
            dayBeforeYesterday.set(Calendar.MINUTE, 0);
            dayBeforeYesterday.set(Calendar.SECOND, 0);
            dayBeforeYesterday.set(Calendar.MILLISECOND, 0);

            boolean isValid = chartDate.equals(today) || chartDate.equals(yesterday) || chartDate.equals(dayBeforeYesterday);

            if (!isValid) {
                LOGGER.error("Last data point date '{}' is older than day before yesterday", lastDataPointDate);
            } else {
                LOGGER.info("Last data point date '{}' is valid (today, yesterday or day before yesterday)", lastDataPointDate);
            }

            return isValid;

        } catch (Exception e) {
            LOGGER.error("Error validating trend chart last data point: {}", e.getMessage());
            return false;
        }
    }





    private String getLastTrendChartDataPoint(String XaxisDataLocator) {
        try {
            // Locate the last data point on the trend chart (adjust selector as needed)
            List<WebElement> lastPoint = getElementsOfDEXPage(XaxisDataLocator);
            return lastPoint.get(lastPoint.size()-1).getText().trim();
        } catch (Exception e) {
            LOGGER.error("Unable to locate last data point: {}", e.getMessage());
            return null;
        }
    }


}
