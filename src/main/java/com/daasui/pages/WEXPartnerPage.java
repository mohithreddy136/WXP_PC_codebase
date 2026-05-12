package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WEXPartnerPage extends CommonMethod {
    private ObjectReader WEXPartnerPageReader = new ObjectReader();
    private Properties WEXPartnerPage;
    public static String uiVersion = System.getProperty("uiVersion");
    private WEXPartnerPage instance;

    public WEXPartnerPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEXPartnerPage.class) {
                if (instance == null) {
                    instance = new WEXPartnerPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEXPartnerPage(WebDriver driver) throws IOException {
        WEXPartnerPage = WEXPartnerPageReader.getObjectRepository("WEXPartnerPage");
    }

    /**
     * This method is the method to verify if an element is present on partner page
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is present
     */
    public final boolean verifyElementsOfWEXPartnerPage(String key) {
        try {
            return verifyElementIsPresent(WEXPartnerPage.getProperty(key));

        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }

    public final boolean verifyElementsOfWEXPartnerPageByLocator(String locator) {
        try {
            return verifyElementIsPresent(locator);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEXPartnerPageByLocator " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to verify on element present on partner list page
     *
     * @param key - locator of the element
     * @param name - name of the company or user
     */
    public final boolean verifyOnElementsWithNameOfWEXPartnerPage(String key, String name) {
        try {
            return verifyElementIsPresent(WEXPartnerPage.getProperty(key)+name);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsWithNameOfWEXPartnerPage " + e.getMessage()));
            return false;
        }

    }

    /**
     * This is the method to wait for any element on the partner list page untill it is visible
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is visible
     */
    public final boolean waitForElementsOfWEXPartnerPage(String key) {
        try {
            return verifyElementIsVisible(WEXPartnerPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }

    public final boolean waitForElementsOfWEXPartnerPageByLocator(String locator) {
        try {
            return verifyElementIsVisible(locator);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEXPartnerPageByLocator " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to match text on an element ehich is present on partner list page
     *
     * @param key - locator of the element
     * @param textToMatch - text to be compared
     * @return - boolean value of whether both the texts match
     */
    public final boolean matchTextOfWEXPartnerPage(String key, String textToMatch) {
        try {
            return verifyTextPresentOnElement(WEXPartnerPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }

    public final boolean matchTextOfWEXPartnerPageByLocator(String locator, String textToMatch) {
        try {
            return verifyTextPresentOnElement(locator, textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEXPartnerPageByLocator " + e.getMessage()));
            return false;
        }
    }


    /**
     * This is a method to match text on an element ehich is present on partner list page
     *
     * @param locator - locator of the element
     * @param textToMatch - text to be compared
     * @return - boolean value of whether both the texts match
     */
    public final boolean matchTextOfwexPartnerPage(String locator, String textToMatch) {
        try {
            return verifyTextPresentOnElement(locator, textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }


    /**
     * THis is a method to verify if an element on partner list page is enabled
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is enabled
     */
    public final boolean verifyElementIsEnableOfWEXPartnerPage(String key) {
        try {
            return verifyElementIsEnable(WEXPartnerPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementIsEnableOfWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to verify if an element on partner list page is selected
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is selected
     */
    public final boolean verifyElementIsSelectedOfWEXPartnerPage(String key) {
        try {
            return verifyElementIsSelected(WEXPartnerPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementIsSelectedOfWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to get text present on an element on partner list page
     *
     * @param key - locator of the element
     * @return - string value of the text present on the element
     */
    public final String getTextOfWEXPartnerPage(String key) {
        try {
            return getTextBy(WEXPartnerPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWEXPartnerPage " + e.getMessage()));
            return null;
        }
    }

    public final String getTextOfWEXPartnerPageByLocator(String locator) {
        try {
            return getTextBy(locator);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWEXPartnerPage " + e.getMessage()));
            return null;
        }
    }


    /**
     * This is a metod to get attribute of an element present on partner list page
     *
     * @param key - locator of the element
     * @param desiredValue - desired attribute name
     * @return - value of the attribute as a string
     */
    public final String getAttributesOfWEXPartnerPage(String key, String desiredValue) {
        try {
            return getAttribute(WEXPartnerPage.getProperty(key), desiredValue);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getAttributesOfWEXPartnerPage " + e.getMessage()));
            return null;
        }
    }

    public final void refreshPageOfWEXPartnerPage() {
        try {
            hardRefresh();
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method refreshPageOfWEXPartnerPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to click on element present on partner list page
     *
     * @param key - locator of the element
     */
    public final boolean clickOnElementsOfWEXPartnerPage(String key) {
        try {
            click(WEXPartnerPage.getProperty(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }


    /**
     * This is a method to click on element present on partner list page using java script
     *
     * @param key - locator of the element
     */
    public final boolean clickOnElementsOfwexPartnerPage(String key) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(WEXPartnerPage.getProperty(key))));
            element.click();
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }

    /*
     * This is a method to get list of web elements
     *
     * @param key - locator of the element
     * @return - list of web elements
     * */
    public List<WebElement> getWebelementsOfWEXPartnerPage(String key) {
        try {
            return getAllElements(WEXPartnerPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getWebelementsOfWEXPartnerDashboardPage " + e.getMessage()));
            return null;
        }
    }

    /**
     * This is a method to enter text on a text field present on partner list page
     *
     * @param key - locator of the element
     * @param textToMatch - text to be entered
     */
    public final void enterTextForWEXPartnerPage(String key, String textToMatch) {
        try {
            enterText(WEXPartnerPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method enterTextForWEXPartnerPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to scroll on WorkforceExp Customer Creation Page
     * @param key
     */
    public final void scrollOnWEXPartnerPage(String key) {
        try {
            scrollTillView(WEXPartnerPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method scrollOnWEXPartnerPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to enter text on an element
     * @param key
     * @throws Exception
     */
    public final void actionClickOnWEXPartnerPage(String key) throws Exception {
        actionClick(WEXPartnerPage.getProperty(key));
    }

    public final boolean matchTextOnWEXPartnerPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEXPartnerPage.getProperty(key), Text);
    }

    /**
     * This method is designed to wait element is visible
     * @param key
     * @return
     */
    public final boolean waitForElementsOnWEXPartnerPage(String key) {
        try {
            return verifyElementIsVisibleDynamic(WEXPartnerPage.getProperty(key),120);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOnWEXPartnerPage " + e.getMessage()));
            return false;
        }

    }

    /**
     * This is a method to hover mouse on an element
     *
     * @param key - Locator of element
     */
    public final void mousehoverOnWEXPartnerPage(String key) {
        try {
            mouseHover(WEXPartnerPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method mousehoverOnPartnerPage " + e.getMessage()));
        }
    }

    public final void mousehoverOnWEXPartnerPageByLocator(String locator) {
        try {
            mouseHover(locator);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method mousehoverOnWEXPartnerPageByLocator " + e.getMessage()));
        }
    }
    /**
     * This is a method to click on an element using java script
     *
     * @param key - locator of element
     * @throws Exception
     */
    public final void clickByJavaScriptOnElementsOnPartnerPage(String key) throws Exception {
        clickByJavaScript(WEXPartnerPage.getProperty(key));
    }

    public final void clickByJavaScriptOnElementsOnPartnerPageByLocator(String locator) throws Exception {
        clickByJavaScript(locator);
    }

    public final void doubleClickByActionsOnElementsOnPartnerPage(String key) {
        try {
            doubleclick(WEXPartnerPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method doubleClickByActionsWEPClickDevicelistPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to click on element present on partner list page
     *
     * @param locator - locator of the element
     */
    public final boolean clickOnElementOfWEXPartnerPage(String locator) {
        try {
            click(locator);
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }
    
    public final void openDropDownOfWEXPartnerPage(String key) {
        try {
            verifyElementsOfWEXPartnerPage(key);
            mousehoverOnWEXPartnerPage(key);
            sleeper(1000);
            clickByJavaScriptOnElementsOnPartnerPage(key);
            sleeper(1000);
            clickOnElementsOfwexPartnerPage(key);
            sleeper(1000);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method openDropDownOfWEXPartnerPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to click on element present on partner list page
     *
     * @param locator - locator of the serach box
     * @param ImportCatalogCompany - option to be selected
     */
    public final void  selectDropDownValueOfWEXPartnerPage(String searchBox, String ImportCatalogCompany, String selectCompany) {
        try {
            waitForElementsOfWEXPartnerPage(searchBox);
            enterTextForWEXPartnerPage(searchBox, ImportCatalogCompany);
            sleeper(3000);//to load the company name entered
            clickOnElementsOfWEXPartnerPage(selectCompany);
            sleeper(3000);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method selectDropDownValueOfWEXPartnerPage " + e.getMessage()));
        }
    }
    /**
     * This method is used for file imported using Robot class for veneer version 3
     * @param fileName this is the name of file which was imported
     * @throws Exception
     */
    public void fileImportInV3(String fileName) throws Exception {
        sleeper(2000);
        List<WebElement> addFile = getWebelementsOfWEXPartnerPage("browseInput");
        if (!addFile.isEmpty()) {
            addFile.get(0).sendKeys(fileName);
        }
        sleeper(3000);
    }


    /**
     * This method is used to validate the notification flow after the catalogs have been imported for veneer version 3
     * @return true if the notification text matches else false
     * @throws Exception
     */
    public boolean postNotificationCheckImport(String catalogCustomerId ) throws Exception {
        boolean flag = true;
        mousehoverOnWEXPartnerPage("bellNotification");
        sleeper(1000);

        // verify the Import Bell Notification
        clickOnElementsOfWEXPartnerPage("bellNotification");
        String importedCatalogNotificationTitle = WEXPartnerPage.getProperty("importedCatalogNotificationTitle");
        importedCatalogNotificationTitle = importedCatalogNotificationTitle.replace("TenantId", catalogCustomerId);
        waitForElementsOfWEXPartnerPageByLocator(importedCatalogNotificationTitle);

        String importedCatalogNotificationDate = WEXPartnerPage.getProperty("importedCatalogNotificationDate");
        importedCatalogNotificationDate = importedCatalogNotificationDate.replace("TenantId", catalogCustomerId);
        String catalogTitle = getTextLanguage("en", "daas_ui", "catalog.label").toUpperCase();

        int tries = 0;
        while (tries < 300) {
            String notificationCatalogTitle = getTextOfWEXPartnerPageByLocator(importedCatalogNotificationTitle);
            String notificationTime = getTextOfWEXPartnerPageByLocator(importedCatalogNotificationDate);

            if (notificationCatalogTitle.equals(catalogTitle) &&
                (notificationTime.contains("a few seconds ago") ||
                 notificationTime.contains("a minute ago") ||
                 notificationTime.contains("minutes ago"))) {
                break;
            }

            sleeper(1000);
            tries++;
        }
        LOGGER.info("Notification catalog title and date matches");

        String catalogImportedMessage = "Your 15 products were imported successfully. Please check the logs for more details.";
        String catalogInprogressDescription = "Importing products from catalogValid.csv file.";

        String importedCatalogNotificationMessage = WEXPartnerPage.getProperty("importedCatalogNotificationMessage");
        importedCatalogNotificationMessage = importedCatalogNotificationMessage.replace("TenantId", catalogCustomerId);
        verifyElementsOfWEXPartnerPageByLocator(importedCatalogNotificationMessage);
        String notificationMessage = getTextOfWEXPartnerPageByLocator(importedCatalogNotificationMessage);
        if (notificationMessage.contains("successfully")) {
            if (!notificationMessage.contains(catalogImportedMessage)) {
                flag = false;
                LOGGER.info("Notification catalogImportedMessage does not match");
            } else {
                LOGGER.info("Notification catalogImportedMessage matches");
            }
            String catalogNotification = WEXPartnerPage.getProperty("catalogNotification");
            catalogNotification = catalogNotification.replace("TenantId", catalogCustomerId);

            mousehoverOnWEXPartnerPageByLocator(catalogNotification);
            String notificationHambergerButton = WEXPartnerPage.getProperty("notificationHambergerButton");
            notificationHambergerButton = notificationHambergerButton.replace("TenantId", catalogCustomerId);
//            clickOnElementsOfWEXPartnerPageByLocator(notificationHambergerButton);
            clickByJavaScriptOnElementsOnPartnerPageByLocator(notificationHambergerButton);
            verifyElementsOfWEXPartnerPage("markAllAsReadButton");
            verifyElementsOfWEXPartnerPage("openLogsButton");
            clickOnElementsOfWEXPartnerPage("clearNotificationButton");
            clickOnElementsOfWEXPartnerPage("bellNotification");
        } else if (notificationMessage.contains("problem while importing")) {
            if (notificationMessage.equalsIgnoreCase("There was a problem while importing your products from catalogInvalid.csv. Please check the logs for more information")) {
                flag = true;
            } else {
                LOGGER.error("Notification for import has failed");
            }
        }
        return flag;
    }

    /**
     * This method is used to get the current date in the format "MMMM d, yyyy"
     * @return formattedDate
     * @throws Exception
     */
    public String  getTodaysDate() {
        // Get today's date
        LocalDate today = LocalDate.now();

        // Define the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);

        // Format today's date
        String formattedDate = today.format(formatter);
        return formattedDate;
    }

    /**
     * This method is used to get the current date in the format "MM/dd/yyyy"
     * @return formattedDate
     * @throws Exception
     */
    public String getTodaysDateInMMDDYYYY() {
        // Get today's date
        LocalDate today = LocalDate.now();

        // Define the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // Format today's date
        String formattedDate = today.format(formatter);
        return formattedDate;
    }

    /**
     * This method is used to get the current date in the format "ddMMMyyyy"
     * @return formattedDate
     * @throws Exception
     */
    public static String getddMMMyyyyFormattedDate() {
        // Get today's date
        LocalDate today = LocalDate.now();

        // Define the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMMyyyy");

        // Format today's date
        return today.format(formatter);
    }

    /**
     * This method is used to verify the catalog details after importing the catalog
     * @param catalogType - type of the catalog
     * @param customer1 - name of the customer1
     * @param customer2 - name of the customer2
     * @return true if the details match else false
     * @throws Exception
     */
    public final boolean  verifyImportedCatalog (String catalogType, String customer1, String customer2) throws Exception {
        boolean  flag = true;
        // get the list of catalogs
        List<WebElement> catalogList = getWebelementsOfWEXPartnerPage("catalogList");
        int catalogSize = catalogList.size();
        String importedCatalogName = "catalogValid Product Catalog";
        int index = -1;  // index of the uploaded catalog in the list
        for (int i = 0; i < catalogSize; i++) {
            WebElement catalog = catalogList.get(i);
            String catalogName = getTextBy(catalog);
            if (catalogName.equals(importedCatalogName)) {
                index = i+1;
                break;
            }
        }
        if (index == -1) {
            LOGGER.error("The imported catalog is not present in the list");
            flag = false;
        }

        // validate the customer name of the imported catalog
        Set<String> importedCompanies = new TreeSet<>();
        Set<String> actualCompanies = new TreeSet<>();

        importedCompanies.add(customer1);
        String catalogCustomer = WEXPartnerPage.getProperty("catalogFirstCompany");
        catalogCustomer = catalogCustomer.replace("ind",  ""+index);
        String  customerNamePresent = getTextOfWEXPartnerPageByLocator(catalogCustomer);
        actualCompanies.add(customerNamePresent);

        if (customer2 != "") {
            importedCompanies.add(customer2);
            String catalogSecondCustomer = WEXPartnerPage.getProperty("catalogSecondCompany");
            catalogSecondCustomer = catalogSecondCustomer.replace("ind",  ""+index);
            String  customerNamePresent2 = getTextOfWEXPartnerPageByLocator(catalogSecondCustomer);
            actualCompanies.add(customerNamePresent2);
        }

        if (!actualCompanies.equals(importedCompanies)) {
            LOGGER.error("The customer name of the imported catalog is not matching");
            LOGGER.error("Expected Companies: "+importedCompanies);
            LOGGER.error("Actual Companies: "+actualCompanies);
            flag = false;
        }

        // validate the product count of the imported catalog
        String catalogProductCount = WEXPartnerPage.getProperty("catalogProductCount");
        catalogProductCount = catalogProductCount.replace("ind",  ""+index);
        boolean  productCountPresent = matchTextOfwexPartnerPage(catalogProductCount, "15");

        if (!productCountPresent) {
            LOGGER.error("The product count of the imported catalog is not matching");
            flag = false;
        }

        // validate the updated on date of the imported catalog
        String catalogUpdatedOn = WEXPartnerPage.getProperty("catalogUpdatedOn");
        catalogUpdatedOn = catalogUpdatedOn.replace("ind",  ""+index);
        boolean  updatedOnPresent = matchTextOfwexPartnerPage(catalogUpdatedOn, getTodaysDateInMMDDYYYY());

        if (!updatedOnPresent) {
            LOGGER.error("The updated on date of the imported catalog is not matching");
            flag = false;
        }

        // validate the catalog type of the imported catalog
        String  catalogTypeLocator = WEXPartnerPage.getProperty("catalogType");
        catalogTypeLocator = catalogTypeLocator.replace("ind",  ""+index);
        boolean  catalogTypePresent = matchTextOfwexPartnerPage(catalogTypeLocator, catalogType);

        if (!catalogTypePresent) {
            LOGGER.error("The catalog type of the imported catalog is not matching");
            flag = false;
        }

        if (!flag) {
            LOGGER.error("The imported catalog details are not matching");
        }
        return flag;
    }

    /**
     * This method is used to delete the preuploaded catalog
     * @throws Exception
     */
    public final void deletePreuploadedCatalog() {
        try {
            List<WebElement> catalogList = getWebelementsOfWEXPartnerPage("catalogList");
            int catalogSize = catalogList.size();
            if (catalogSize > 1)
                for (int i = 0; i < catalogSize - 1; i++) {
                    // click on the delete button
                    String deleteButton = WEXPartnerPage.getProperty("catalogThreeDotButton");
                    deleteButton = deleteButton.replace("ind", "" + 1);
                    clickOnElementOfWEXPartnerPage(deleteButton);
                    // click on the delete dropdown option
                    waitForElementsOfWEXPartnerPage("catalogDeleteButtonList");
                    mousehoverOnWEXPartnerPage("catalogDeleteButtonList");
                    clickByJavaScriptOnElementsOnPartnerPage("catalogDeleteButtonList");
                    verifyElementsOfWEXPartnerPage("deleteCatalogTitle");
                    verifyElementsOfWEXPartnerPage("deleteCatalogMessage");
                    clickOnElementsOfWEXPartnerPage("deleteCatalogButton");
                    waitForElementsOfWEXPartnerPage("deleteCatalogtoastNotification");
                    LOGGER.info("Preuploaded Catalog deleted successfully");
                    waitForPageLoaded();
                }

        } catch (Exception e) {
            LOGGER.error("Exception occured in method deletePreuploadedCatalog " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /**
     * This method is used to delete the catalog from the list
     * @return true if the catalog is deleted else false
     * @throws Exception
     */
    public final boolean  deleteCatalogFromList () throws Exception {
        boolean  flag = true;
        // get the list of catalogs
        List<WebElement> catalogList = getWebelementsOfWEXPartnerPage("catalogList");
        int catalogSize = catalogList.size();
        String importedCatalogName = "catalogValid Product Catalog";
        int index = -1;  // index of the uploaded catalog in the list
        for (int i = 0; i < catalogSize; i++) {
            WebElement catalog = catalogList.get(i);
            String catalogNameFromUI = getTextBy(catalog);
            if (catalogNameFromUI.equals(importedCatalogName)) {
                index = i+1;
                break;
            }
        }
        if (index == -1) {
            LOGGER.error("The imported catalog is not present in the list");
            flag = false;
        }
        // click on the delete button
        String deleteButton = WEXPartnerPage.getProperty("catalogThreeDotButton");
        deleteButton = deleteButton.replace("ind",  ""+index);
        clickOnElementOfWEXPartnerPage(deleteButton);
        // click on the delete dropdown option
        waitForElementsOfWEXPartnerPage("catalogDeleteButtonList");
        mousehoverOnWEXPartnerPage("catalogDeleteButtonList");
        clickByJavaScriptOnElementsOnPartnerPage("catalogDeleteButtonList");
        verifyElementsOfWEXPartnerPage("deleteCatalogTitle");
        verifyElementsOfWEXPartnerPage("deleteCatalogMessage");
        clickOnElementsOfWEXPartnerPage("deleteCatalogButton");
        waitForElementsOfWEXPartnerPage("deleteCatalogtoastNotification");

        boolean  toastNotification = matchTextOfWEXPartnerPage("deleteCatalogtoastNotification", getTextLanguage("en", "daas_ui", "catalogs.list.toast.remove.success"));
        if (!toastNotification) {
            LOGGER.error("The toast notification for deleting the catalog is not present");
            flag = false;
        }

        waitForPageLoaded();

        // Again validate weather the catalog is deleted or not
        waitUntilElementIsVisibleDynamic("catalogList", 60);
        catalogList = getWebelementsOfWEXPartnerPage("catalogList");
        if (catalogSize-1 != catalogList.size())
            flag = false;

        return flag;
    }

    /**
     * This method is used to delete the catalog from the catalog details page
     * @return true if the catalog is deleted else false
     * @throws Exception
     */
    public final boolean  deleteCatalogFromCatalogDetailsPage() throws Exception {
        boolean  flag = true;
        // get the list of catalogs
        List<WebElement> catalogList = getWebelementsOfWEXPartnerPage("catalogList");
        int catalogSize = catalogList.size();
        String importedCatalogName = "catalogValid Product Catalog";
        int index = -1;  // index of the uploaded catalog in the list
        for (int i = 0; i < catalogSize; i++) {
            WebElement catalog = catalogList.get(i);
            String catalogNameFromUI = getTextBy(catalog);
            if (catalogNameFromUI.equals(importedCatalogName)) {
                index = i+1;
                catalog.click();
                LOGGER.info("Clicked on the catalog & details page is opened");
                break;
            }
        }
        if (index == -1) {
            LOGGER.error("The imported catalog is not present in the list");
            flag = false;
        }

        waitForElementsOfWEXPartnerPage("removeCatalogButton");
        if (!matchTextOfWEXPartnerPage("removeCatalogButton", "Remove")) {
            LOGGER.error("The remove button label is not present");
            flag = false;
        }
        sleeper(1000);
        clickOnElementsOfWEXPartnerPage("removeCatalogButton");
        verifyElementsOfWEXPartnerPage("deleteCatalogTitle");
        verifyElementsOfWEXPartnerPage("deleteCatalogMessage");
        sleeper(1000);
        clickOnElementsOfWEXPartnerPage("deleteCatalogButton");
        waitForElementsOfWEXPartnerPage("deleteCatalogtoastNotification");

        boolean  toastNotification = matchTextOfWEXPartnerPage("deleteCatalogtoastNotification", getTextLanguage("en", "daas_ui", "catalogs.list.toast.remove.success"));
        if (!toastNotification) {
            LOGGER.error("The toast notification for deleting the catalog is not present");
            flag = false;
        }

        waitUntilElementIsVisibleDynamic("catalogList", 60);
        catalogList = getWebelementsOfWEXPartnerPage("catalogList");
        if (catalogSize-1 != catalogList.size())
            flag = false;

        return flag;
    }
    public final boolean selectTextValueFromDropdownOfCustomerListPageWorkflow(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		return selectTextValueFromDropdownWorkflow(WEXPartnerPage.getProperty(dropdownListKey), elementText, dropdownBox);
	}

    public final void selectCustomerInPartner(String partnerCustomer) {
        try {
            mousehoverOnWEXPartnerPage("searchItem");
            sleeper(2000);
            clickByJavaScriptOnElementsOnPartnerPage("searchItem");
            clickOnElementsOfwexPartnerPage("searchItem");
            waitForElementsOfWEXPartnerPage("searchCompany");
            enterTextForWEXPartnerPage("searchCompany", partnerCustomer);
            sleeper(3000);
            selectTextValueFromDropdownOfCustomerListPageWorkflow("listDropdown", partnerCustomer, "");
            sleeper(3000);
        }
        catch (Exception e) {
            LOGGER.error("Exception occured in method selectCustomerInPartner " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public final void deletePendingInviteCustomerFromPartner(String customers) throws Exception {
        try {
            WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
            List<WebElement> customerList = getWebelementsOfWEXPartnerPage(customers);
            int size = customerList.size();

            for (int customer = 0; customer < size; customer++) {
                clickOnElementsOfWEXPartnerPage("customers");
                // clear all filters of customer page
                wepPartnerCustomersPage.clearAllFiltersOfWEPPartnerCustomersPage();
                mousehoverOnWEXPartnerPage("searchItem");
                Thread.sleep(2000);
                clickByJavaScriptOnElementsOnPartnerPage("searchItem");
                clickOnElementsOfwexPartnerPage("searchItem");
                sleeper(2000);
                if (selectTextValueFromDropdownOfCustomerListPageWorkflow("listDropdown", CommonVariables.COMPANY_NAME, "")){
                    Thread.sleep(2000);
                    boolean isCustomerActive = verifyElementsOfWEXPartnerPage("activeStatus");
                    boolean isCustomerPending = verifyElementsOfWEXPartnerPage("pendingStatus");
                    clickOnElementsOfWEXPartnerPage("optionChoice");
                    verifyElementsOfWEXPartnerPage("resendInvite");
                    verifyElementsOfWEXPartnerPage("viewCustomerProfile");
                    verifyElementsOfWEXPartnerPage("deleteInvite");
                    clickOnElementsOfWEXPartnerPage("deleteInvite");
                    String customerName = getTextOfWEXPartnerPage("deleteCustomerPopupDescription");
                    String deleteCustomerPopupDescriptionXPath;
                    if (isCustomerActive) {
                        deleteCustomerPopupDescriptionXPath = WEXPartnerPage.getProperty("activeCustomerPopupDescription");
                    } else if (isCustomerPending) {
                        deleteCustomerPopupDescriptionXPath = WEXPartnerPage.getProperty("pendingCustomerPopupDescription");
                    } else {
                        throw new RuntimeException("Customer status is neither active nor pending.");
                    }
                    if (customerName.contains(CommonVariables.COMPANY_NAME) || customerName.contains("uiautomationinviteita donotdeletetestusstage")) {
                        clickOnElementsOfWEXPartnerPage("confirmDeleteComp");
                        verifyElementsOfWEXPartnerPage("deleteConfirmToasts");
                    }
                }
                else
                {
                    clickOnElementsOfWEXPartnerPage("home");
                    clickOnElementsOfWEXPartnerPage("customers");
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("Exception occured in method deletePendingInviteCustomerFromPartner " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public final boolean verifyElementsVisibilityOfWEXPartnerPage(String key, int timeout) {
        try {
            return verifyElementIsVisibleDynamic(WEXPartnerPage.getProperty(key),timeout);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEXPartnerPage " + e.getMessage()));
            return false;
        }
    }
    
}
