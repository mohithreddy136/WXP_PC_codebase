package com.daasui.pages;

import com.amazonaws.services.greengrass.model.Logger;
import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.ConstantPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class WEXPartnerDashboardPage extends CommonMethod {
    private ObjectReader WEXPartnerDashboardPageReader = new ObjectReader();
    private Properties WEXPartnerDashboardPage;
    public static String uiVersion = System.getProperty("uiVersion");
    private WEXPartnerDashboardPage instance;

    public WEXPartnerDashboardPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEXPartnerDashboardPage.class) {
                if (instance == null) {
                    instance = new WEXPartnerDashboardPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEXPartnerDashboardPage(WebDriver driver) throws IOException {
        WEXPartnerDashboardPage = WEXPartnerDashboardPageReader.getObjectRepository("WEXPartnerDashboardPage");
    }

    /**
     * This method is the method to verify if an element is present on partner page
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is present
     */
    public final boolean verifyElementsOfWEXPartnerDashboardPage(String key) {
        try {
            return verifyElementIsPresent(WEXPartnerDashboardPage.getProperty(key));

        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to verify on element present on partner page
     *
     * @param key - locator of the element
     * @param name - name of the company or user
     */
    public final boolean verifyOnElementsWithNameOfWEXPartnerDashboardPage(String key, String name) {
        try {
            return verifyElementIsPresent(WEXPartnerDashboardPage.getProperty(key)+name);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsWithNameOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }

    }

    /**
     * This is the method to wait for any element on the partner page untill it is visible
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is visible
     */
    public final boolean waitForElementsOfWEXPartnerDashboardPage(String key) {
        try {
            return verifyElementIsVisible(WEXPartnerDashboardPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEXPartnerDashboardPage " + e.getMessage()));
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
    public final boolean matchTextOfWEXPartnerDashboardPage(String key, String textToMatch) {
        try {
            return verifyTextPresentOnElement(WEXPartnerDashboardPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to match text on an element which is present on partner page
     *
     * @param key - locator of the element
     * @param textToMatch - text to be compared
     *  @param replaceText - text to be replaced in the locator
     * @return - boolean value of whether both the texts match
     */
    public final boolean matchTextOfWEXPartnerPage(String key, String textToMatch, String replaceText) {
        try {
            return verifyTextPresentOnElement(WEXPartnerDashboardPage.getProperty(key).replace(replaceText, textToMatch.trim()), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to match text on an element which is present on partner page
     *
     * @param key - locator of the element
     * @param textToMatch - text to be compared
     *  @param replaceText - text to be replaced in the locator
     * @param newReplaceText - text to be replaced in the locator
     * @return - boolean value of whether both the texts match
     */
    public final boolean matchTextOfWEXPartnerPage(String key, String textToMatch, String replaceText, String newReplaceText) {
        try {
            return verifyTextPresentOnElement(WEXPartnerDashboardPage.getProperty(key).replace(replaceText, newReplaceText), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * THis is a method to verify if an element on partner page is enabled
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is enabled
     */
    public final boolean verifyElementIsEnableOfWEXPartnerDashboardPage(String key) {
        try {
            return verifyElementIsEnable(WEXPartnerDashboardPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementIsEnableOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to verify if an element on partner page is selected
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is selected
     */
    public final boolean verifyElementIsSelectedOfWEXPartnerDashboardPage(String key) {
        try {
            return verifyElementIsSelected(WEXPartnerDashboardPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementIsSelectedOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to get text present on an element on partner page
     *
     * @param key - locator of the element
     * @return - string value of the text present on the element
     */
    public final String getTextOfWEXPartnerDashboardPage(String key) {
        try {
            return getTextBy(WEXPartnerDashboardPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWEXPartnerDashboardPage " + e.getMessage()));
            return null;
        }
    }

    public final void scrollTillViewOfWEXPartnerDashboardPage(String key) {
        try {
            scrollTillView(WEXPartnerDashboardPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method scrollTillViewOfWEXPartnerDashboardPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to get text present on an element on partner page
     *
     * @param locator - locator of the element
     * @return - string value of the text present on the element
     */
    public final String getTextOfWEXPartnerPage(String locator) {
        try {
            return getTextBy(locator);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWEXPartnerDashboardPage " + e.getMessage()));
            return null;
        }
    }

    /**
     * This is a metod to get attribute of an element present on partner page
     *
     * @param key - locator of the element
     * @param desiredValue - desired attribute name
     * @return - value of the attribute as a string
     */
    public final String getAttributesOfWEXPartnerDashboardPage(String key, String desiredValue) {
        try {
            return getAttribute(WEXPartnerDashboardPage.getProperty(key), desiredValue);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWEXPartnerDashboardPage " + e.getMessage()));
            return null;
        }
    }

    /**
     * This is a method to click on element present on partner page
     *
     * @param key - locator of the element
     */
    public final boolean clickOnElementsOfWEXPartnerDashboardPage(String key) {
        try {
            click(WEXPartnerDashboardPage.getProperty(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to click on element present on partner page
     *
     * @param key - locator of the element
     * @param name - name of the company or user
     */
    public final boolean actionClickOnElementsOfWEXPartnerDashboardPage(String key) {
        try {
            actionClick(WEXPartnerDashboardPage.getProperty(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to click on webelement present on partner page
     *
     * @param locator - locator of the element
     */
    public final boolean clickOnWebelementOfWEXPartnerDashboardPage(String key) {
        try {
//            clickWebelement(locator);
            clickByJavaScript(WEXPartnerDashboardPage.getProperty(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }


    public final void pageRefreshForWEXPartnerDashboardPage() {
        try {
            refreshPage();
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method pageRefreshForWEXPartnerDashboardPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to enter text on a text field present on partner page
     *
     * @param key - locator of the element
     * @param textToMatch - text to be entered
     */
    public final void enterTextForWEXPartnerDashboardPage(String key, String textToMatch) {
        try {
            enterText(WEXPartnerDashboardPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method enterTextForWEXPartnerDashboardPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to verify if an element on partner page is clickable
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is clickable
     */
    public final boolean verifyElementIsClickableOfWEXPartnerDashboardPage(String key) {
        try {
            return verifyElementIsClickable(WEXPartnerDashboardPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementIsClickableOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to get the elements present on partner page
     *
     * @param key - locator of the element
     * @return - list of web elements
     */
    public final WebElement getWebElementOfWEXPartnerDashboardPage(String key) {
        try {
            return getElementsTillAllElementsPresent(WEXPartnerDashboardPage.getProperty(key)).get(0);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getWebElementOfWEXPartnerDashboardPage " + e.getMessage()));
            return null;
        }
    }

    /**
     * This is a method to hover mouse on an element
     *
     * @param key - Locator of element
     */
    public final void mousehoverOnWEXPartnerDashboardPage(String key) {
        try {
            mouseHover(WEXPartnerDashboardPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method mousehoverOnPartnerPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to click on element present on partner page
     *
     * @param key - locator of the element
     * @param name - name of the company or user
     */
    public final boolean clickOnElementsWithNameOfWEXPartnerDashboardPage(String key, String name) {
        try {
            click(WEXPartnerDashboardPage.getProperty(key)+name);
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsWithNameOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to click on element present on partner page
     *
     * @param key - locator of the element
     * @param replaceText - text to be replaced in the locator
     * @param name - name of the company or user
     */
    public final boolean clickOnElementsOfWEXPartnerDashboardPage(String key, String replaceText ,String name) {
        try {
            click(WEXPartnerDashboardPage.getProperty(key).replace(replaceText, name));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsWithNameOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    /*
     * This method is used to switch to the new tab
     *
     * @param custEngmntFirstComp - name of the first company
     * @param CustomerEngagementReportTitle - title of the report
     * @param CustomerEngagementReportName - name of the report
     * */
    public final boolean switchToCustomerEngagementReportTab(String custEngmntFirstComp, String CustomerEngagementReportTitle, String CustomerEngagementReportName) throws Exception {
        boolean flag = true;
        String parentWindowHandle = getDriver().getWindowHandle();
        waitForPageLoaded();
        for (String windowHandle : getDriver().getWindowHandles()) {
            if (!windowHandle.equals(parentWindowHandle)) {
                getDriver().switchTo().window(windowHandle);

                // Verify the title of the report
                if (!matchTextOfWEXPartnerDashboardPage("CustomerEngagementReportTitle", CustomerEngagementReportTitle)) {
                    flag = false;
                    LOGGER.info("Customer Engagement Report Title is not matching");
                    LOGGER.info("Expected Customer Engagement Report Title :" + CustomerEngagementReportTitle);
                    LOGGER.info("Actual Customer Engagement Report Title :"   + getTextOfWEXPartnerDashboardPage("CustomerEngagementReportTitle"));
                }

                // Verify the details button
                if (!matchTextOfWEXPartnerDashboardPage("CustomerEngagementReportDetailButton", "Details")) {
                    flag = false;
                    LOGGER.info("Customer Engagement Report Detail Button is not matching");
                    LOGGER.info("Expected Customer Engagement Report Detail Button :" + "Details");
                    LOGGER.info("Actual Customer Engagement Report Detail Button :"   + getTextOfWEXPartnerDashboardPage("CustomerEngagementReportDetailButton"));
                }

                // Verify the first company name
                if (!matchTextOfWEXPartnerDashboardPage("CustomerEngagementCompanyNameOnReport", custEngmntFirstComp)) {
                    flag = false;
                    LOGGER.info("Customer Engagement First Company is not matching");
                    LOGGER.info("Expected Customer Engagement First Company :" + custEngmntFirstComp);
                    LOGGER.info("Actual Customer Engagement First Company :"   + getTextOfWEXPartnerDashboardPage("CustomerEngagementCompanyNameOnReport"));
                }

                // Verify the download button
                if (verifyElementIsVisible(WEXPartnerDashboardPage.getProperty("CustomerEngagementReportDownloadButton"))) {
                    clickOnElementsOfWEXPartnerDashboardPage("CustomerEngagementReportDownloadButton");
                    LOGGER.info("Customer Engagement Report Download Button is visible");
                    sleeper(2000);
                    // Verify the download PDF
                    if (!verifyElementIsVisible(WEXPartnerDashboardPage.getProperty("CustomerEngagementPDFReport"))) {
                        flag = false;
                        LOGGER.info("Customer Engagement Report Download PDF Button is not visible");
                    } else {
                        clickOnElementsOfWEXPartnerDashboardPage("CustomerEngagementPDFReport");
                        sleeper(5000);
                        waitUntilElementIsVisibleDynamic(WEXPartnerDashboardPage.getProperty("CustomerEngagementReportDetailButton"), 60);
                        LOGGER.info("Customer Engagement Report Download PDF Button is visible");
                    }

                    // Verify the download Excel
                    clickOnElementsOfWEXPartnerDashboardPage("CustomerEngagementReportDownloadButton");
                    if (!verifyElementIsVisible(WEXPartnerDashboardPage.getProperty("CustomerEngagementXLSXReport"))) {
                        flag = false;
                        LOGGER.info("Customer Engagement Report Download Excel Button is not visible");
                    } else {
                        clickOnElementsOfWEXPartnerDashboardPage("CustomerEngagementXLSXReport");
                        sleeper(5000);
                        waitUntilElementIsVisibleDynamic(WEXPartnerDashboardPage.getProperty("CustomerEngagementReportDetailButton"), 60);
                        LOGGER.info("Customer Engagement Report Download Excel Button is visible");
                    }
                } else {
                    flag = false;
                    LOGGER.info("Customer Engagement Report Download Button is not visible");
                }
                getDriver().close();
                getDriver().switchTo().window(parentWindowHandle);
            }
        }

        // Verify the Exported file is downloaded
        final String DOWNLOAD_FOLDER_PATH = ConstantPath.DOWNLOAD_FOLDER_PATH;
        final String EXPECTED_PDF_FILE_NAME = CustomerEngagementReportName.replace(' ', '_') + ".pdf";
        final String EXPECTED_XLSX_FILE_NAME = CustomerEngagementReportName.replace(' ', '_')  + ".xlsx";

        Set<String> expectedFileNames = new TreeSet<>();
        expectedFileNames.add(EXPECTED_PDF_FILE_NAME);
        expectedFileNames.add(EXPECTED_XLSX_FILE_NAME);

        boolean isFileDownloaded = isFileDownloaded(DOWNLOAD_FOLDER_PATH, expectedFileNames);
        if (!isFileDownloaded) {
            flag = false;
            LOGGER.info("File is not downloaded");
        }
        return flag;
    }

    public boolean verifyDownloadUrl(String expectedFileName) throws InterruptedException {
        // Get the current URL
        String currentUrl = getXlsxTabUrlAndClose(getDriver());
        LOGGER.info("Current URL: " + currentUrl);
        // Check if the URL starts with the specified string and contains the expected file name
        return currentUrl.startsWith("https://view.officeapps.live.com/op/view.aspx?src=https") && currentUrl.contains(expectedFileName);
    }

    public String getXlsxTabUrlAndClose(WebDriver driver) throws InterruptedException {
        String parentWindowHandle = driver.getWindowHandle();
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        String xlsxTabUrl = null;

        // Switch to the third tab that opens the XLSX file
        if (windowHandles.size() > 2) {
            driver.switchTo().window(windowHandles.get(2));
            // Get the URL of the currently open tab
            xlsxTabUrl = driver.getCurrentUrl();
            // Close the current tab
            driver.close();
        }

        // Switch back to the parent window
        driver.switchTo().window(parentWindowHandle);

        return xlsxTabUrl;
    }

    /* This method is used to verify exported file is downloaded or not
     * @downloadFolderPath  - path of the download folder
     * @expectedFileName - name of the file to be downloaded
     * @return - boolean value of whether the file is downloaded
     */
    public final  boolean isFileDownloaded(String downloadFolderPath, Set<String> expectedFileName) throws Exception {
        File downloadFolder = new File(downloadFolderPath);
        File[] files = downloadFolder.listFiles();
        Set<String> actualFileNames = new TreeSet<>();
        boolean flag = false;
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    actualFileNames.add(file.getName());
                }
            }
            if (actualFileNames.containsAll(expectedFileName)) {
                flag = true;
            }
        }
        else {
            LOGGER.info("Download folder is empty");
            throw new Exception("Download folder does not exist or is not accessible.");
        }
        return flag;
    }

    public final void actionClickOnWEXPartnerDashboardPage(String key) throws Exception {
        actionClick(WEXPartnerDashboardPage.getProperty(key));
    }

    public final boolean waitForElementsOnWEXPartnerDashboardPage(String key) {
        try {
            return verifyElementIsVisibleDynamic(WEXPartnerDashboardPage.getProperty(key),120);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOnWEXPartnerPage " + e.getMessage()));
            return false;
        }

    }

    /*
     * This is a method to get list of web elements
     *
     * @param key - locator of the element
     * @return - list of web elements
     * */
    public List<WebElement> getWebelementsOfWEXPartnerDashboardPage(String key) {
        try {
            return getAllElements(WEXPartnerDashboardPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getWebelementsOfWEXPartnerDashboardPage " + e.getMessage()));
            return null;
        }
    }

    /*
     * This method is a method to verify the Guideme button on company model page
     *
     * @param key - locator of category heading
     * @return - boolean value of whether the element is present & working
     * */
    public final boolean verifyGuidemeButtonOnCompanyModel(String key) {
        List<WebElement> raCards = getWebelementsOfWEXPartnerDashboardPage(key);
        boolean flag = true;
        int raSize = raCards.size();
        SoftAssert softAssert = new SoftAssert();
        try {
            for (int card = 0; card < raSize; card++) {
                raCards = getWebelementsOfWEXPartnerDashboardPage(key);
                WebElement raCard = raCards.get(card);
                mouseHover(raCard);
                String raCardText = raCard.getText();
                clickOnElementsWithNameOfWEXPartnerDashboardPage("RACardDetailsButton", Integer.toString(card));
                softAssert.assertTrue(matchTextOfWEXPartnerDashboardPage("RACardTittleOnCompModel", raCardText));

                String AVCardName = "Enable Microsoft Defender antivirus";
                String SmartRefreshCardName = "Boost productivity with a Smart Refresh of your devices.";
                if (raCardText.equals(AVCardName) || raCardText.equals(SmartRefreshCardName)) {
                    clickOnElementsOfWEXPartnerDashboardPage("closedButtonOnCompModel");
                    continue;
                }
                if (!switchToNewTab("GuidemeButtonOnCompModel", raCardText)) {
                    flag = false;
                }
            }
        } catch(Exception e){
            LOGGER.error(("Exception occured in method verify GuidemeButtonOnCompanyModel " + e.getMessage()));
            return false;
        }
        return flag;
    }

    /*
     * This method is used to switch to the new tab & verify the troubleshoot page tittle
     * @param key - locator of the element
     * @return - boolean value of whether the element is present & working
     * */
    public final boolean switchToNewTab (String key, String raCardTittle){
        boolean flag = false;
        String parentWindowHandle = getDriver().getWindowHandle();
        clickOnElementsOfWEXPartnerDashboardPage("GuidemeButtonOnCompModel");
        waitForPageLoaded();
        Map<String, String> getTroubleshootPageTitle = getTroubleshootPageTitle();
        for (String windowHandle : getDriver().getWindowHandles()) {
            if (!windowHandle.equals(parentWindowHandle)) {
                getDriver().switchTo().window(windowHandle);
                if (matchTextOfWEXPartnerDashboardPage("troubleshootTittle",  getTroubleshootPageTitle.get(raCardTittle))) {
                    flag = true;
                }
                getDriver().close();
                getDriver().switchTo().window(parentWindowHandle);
                clickOnElementsOfWEXPartnerDashboardPage("closedButtonOnCompModel");
            }
        }
        return flag;
    }

    /*
     * This method is used to get the troubleshoot page title
     * @return - Map of troubleshoot page title where key is the card name and value is the title of the troubleshoot page
     * */
    public final Map<String, String> getTroubleshootPageTitle () {
        Map<String, String> raCardTroubleshootPageTitle = new HashMap<>();
        raCardTroubleshootPageTitle.put("Apply critical Windows updates.", "Windows critical or security updates are missing.");
        raCardTroubleshootPageTitle.put("Investigate critical GPU issue detected", "GPU Health - Attention required.");
        raCardTroubleshootPageTitle.put("Install Windows security updates.", "Windows security updates are missing.");
        raCardTroubleshootPageTitle.put("Enable Windows Defender firewall.", "Firewall is disabled.");
        raCardTroubleshootPageTitle.put("Enable BitLocker encryption.", "BitLocker encryption is not enabled.");
        raCardTroubleshootPageTitle.put("Enable Windows Secure Boot.", "Windows Secure Boot is not enabled.");
        raCardTroubleshootPageTitle.put("Apply critical driver updates.", "Critical driver updates are missing.");
        raCardTroubleshootPageTitle.put("Update Windows Defender firewall signature.", "Firewall signatures are out of date.");
        raCardTroubleshootPageTitle.put("Implement a preventative device reboot.", "The last reboot was more than 21 days ago.");
        raCardTroubleshootPageTitle.put("Enable HP Battery Health Manager.", "HP Battery Health Manager is not enabled");
        raCardTroubleshootPageTitle.put("Investigate surge of collaboration software errors.", "Collaboration software errors are high.");
        raCardTroubleshootPageTitle.put("Free up disk space.", "Low free disk space.");
        raCardTroubleshootPageTitle.put("Optimize OS shutdown time.", "The OS shutdown time is too long.");
        raCardTroubleshootPageTitle.put("Investigate docking station AC adapter failure.", "Docking station AC adapter failed.");
        raCardTroubleshootPageTitle.put("Update docking station firmware.", "Docking station firmware is out of date.");
        raCardTroubleshootPageTitle.put("Investigate device high memory usage.", "Memory utilization is high for an extended time.");
        raCardTroubleshootPageTitle.put("Reduce unexpected OS crashes.", "Windows crash frequency is high.");
        raCardTroubleshootPageTitle.put("Optimize OS startup time.", "The OS startup time is too long.");
        raCardTroubleshootPageTitle.put("Analyze sustained device high fan speed.", "The average fan speed is high for an extended period of time.");
        return raCardTroubleshootPageTitle;
    }

    /**
     * This method is used to verify the category and impact of the RA cards
     * @param key - locator of the element
     * @param index - index of the element
     * @return - boolean value of whether the element is present & working
     */
    public final boolean verifyCategoryImpactCardnameOfRA(String key, String index) throws Exception {
        boolean flag = true;
        Map<String, Map<String, String>> allRACardsDetails = allRACardsDetails();
        List<WebElement> raCards = getWebelementsOfWEXPartnerDashboardPage(key);
        int raSize = raCards.size();

        String raImpact = WEXPartnerDashboardPage.getProperty("RACardImpact");
        String raCategory = WEXPartnerDashboardPage.getProperty("RACardCategory");

        raImpact = raImpact.replace("sections-0", "sections-" + index);
        raCategory = raCategory.replace("sections-0", "sections-" + index);

        for (int card = 0; card < raSize; card++) {

            // Get the card name
            WebElement raCard = raCards.get(card);
            String raCardName = getTextBy(raCard);
            if (raCardName == null || !allRACardsDetails.containsKey(raCardName)) {
                flag = false;
                LOGGER.error("Card name is not matching for the card " + raCardName);
            } else {
                // Get the impact and category of the card
                String raImpactForCard = raImpact.replace("impact-0", "impact-" + card);
                String impactText = getTextOfWEXPartnerPage(raImpactForCard);
                Map<String, String> cardDetails = allRACardsDetails.get(raCardName);
                if (cardDetails == null || !cardDetails.get("Impact").equals(impactText)) {
                    flag = false;
                    LOGGER.error("Impact is not matching for the card " + impactText);
                }

                // Get the category of the card
                String raCategoryForCard = raCategory.replace("tag-0", "tag-" + card);
                String categoryText = getTextOfWEXPartnerPage(raCategoryForCard);
                if (cardDetails == null || !cardDetails.get("Category").equals(categoryText)) {
                    flag = false;
                    LOGGER.error("Category is not matching for the card " + categoryText);
                }
            }

        }
        return flag;
    }
    /*
     * This method is used to get the details of all the RA cards
     * @return - Map of RA cards where key is the card name and value is the details of the card
     */
    public final Map<String, Map<String, String>> allRACardsDetails() {
        Map<String, Map<String, String>> raCardDetails = new HashMap<>();

        addCardDetails(raCardDetails, "Enable Microsoft Defender antivirus", "High impact", "Security");
        addCardDetails(raCardDetails, "Apply critical Windows updates.", "High impact", "Devices");
        addCardDetails(raCardDetails, "Investigate critical GPU issue detected", "High impact", "Devices");
        addCardDetails(raCardDetails, "Install Windows security updates.", "Medium impact", "Security");
        addCardDetails(raCardDetails, "Enable Windows Defender firewall.", "Medium impact", "Security");
        addCardDetails(raCardDetails, "Enable BitLocker encryption.", "Medium impact", "Security");
        addCardDetails(raCardDetails, "Enable Windows Secure Boot.", "Medium impact", "Security");
        addCardDetails(raCardDetails, "Apply critical driver updates.", "Medium impact", "Devices");
        addCardDetails(raCardDetails, "Update Windows Defender firewall signature.", "Low impact", "Security");
        addCardDetails(raCardDetails, "Implement a preventative device reboot.", "Low impact", "Devices");
        addCardDetails(raCardDetails, "Enable HP Battery Health Manager.", "Low impact", "Devices");
        addCardDetails(raCardDetails, "Investigate surge of collaboration software errors.", "Low impact", "Devices");
        addCardDetails(raCardDetails, "Free up disk space.", "Low impact", "Devices");
        addCardDetails(raCardDetails, "Optimize OS shutdown time.", "Low impact", "Devices");
        addCardDetails(raCardDetails, "Investigate docking station AC adapter failure.", "Low impact", "Devices");
        addCardDetails(raCardDetails, "Update docking station firmware.", "Low impact", "Devices");
        addCardDetails(raCardDetails, "Investigate device high memory usage.", "Low impact", "Devices");
        addCardDetails(raCardDetails, "Reduce unexpected OS crashes.", "Low impact", "Devices");
        addCardDetails(raCardDetails, "Optimize OS startup time.", "Low impact", "Devices");
        addCardDetails(raCardDetails, "Analyze sustained device high fan speed.", "Low impact", "Devices");
        addCardDetails(raCardDetails, "Boost productivity with a Smart Refresh of your devices.", "High impact", "Devices");

        return raCardDetails;
    }

    /*
     * This method is used to add the details of the RA cards
     * @param raCardDetails - Map of RA cards where key is the card name and value is the details of the card
     * @param cardName - name of the card
     * @param impact - impact of the card
     * @param category - category of the card
     */
    public final void addCardDetails(Map<String, Map<String, String>> raCardDetails, String cardName, String impact, String category) {
        Map<String, String> cardDetails = new HashMap<>();
        cardDetails.put("Impact", impact);
        cardDetails.put("Category", category);
        raCardDetails.put(cardName, cardDetails);
    }

    public final boolean validateAddress(String addressfield) {
        try {
            if(addressfield == "" || addressfield == null || addressfield.contains("#") || addressfield.contains("&") || addressfield.contains(";")) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method validateAddress " + e.getMessage()));
        }
        return false;
    }

    /**
     * Validates that a customer count string has the expected format:
     * starts with a digit and contains a label after the number.
     * The label can be in any language (e.g. "5 Customers", "1 客戶", "3 Kunden").
     */
    public boolean isValidCustomerCountFormat(String text) {
        if (text == null || text.trim().isEmpty()) {
            LOGGER.warn("Customer count text is null or empty");
            return false;
        }
        String trimmed = text.trim();
        boolean startsWithDigit = Character.isDigit(trimmed.charAt(0));
        boolean hasLabel = trimmed.contains(" ") && trimmed.length() > trimmed.indexOf(" ") + 1;
        if (!startsWithDigit || !hasLabel) {
            LOGGER.warn("Customer count format validation failed for: [" + text + "] "
                    + "(startsWithDigit=" + startsWithDigit + ", hasLabel=" + hasLabel + ")");
        }
        return startsWithDigit && hasLabel;
    }
}