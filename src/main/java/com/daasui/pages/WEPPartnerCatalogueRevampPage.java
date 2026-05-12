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

public class WEPPartnerCatalogueRevampPage extends CommonMethod {
    private ObjectReader WEPPartnerCatalogueRevampPageReader = new ObjectReader();
    private Properties WEPPartnerCatalogueRevampPage;
    public static String uiVersion = System.getProperty("uiVersion");
    private WEPPartnerCatalogueRevampPage instance;

    public WEPPartnerCatalogueRevampPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPPartnerCatalogueRevampPage.class) {
                if (instance == null) {
                    instance = new WEPPartnerCatalogueRevampPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEPPartnerCatalogueRevampPage(WebDriver driver) throws IOException {
        WEPPartnerCatalogueRevampPage = WEPPartnerCatalogueRevampPageReader.getObjectRepository("WEPPartnerCatalogueRevampPage");
    }

    /**
     * This method is the method to verify if an element is present on partner page
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is present
     */
    public final boolean verifyElementsOfWEPPartnerCatalogueRevampPage(String key) {
        try {
            return verifyElementIsPresent(WEPPartnerCatalogueRevampPage.getProperty(key));

        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
            return false;
        }
    }

    public final boolean verifyElementsOfWEPPartnerCatalogueRevampPageByLocator(String locator) {
        try {
            return verifyElementIsPresent(locator);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEPPartnerCatalogueRevampPageByLocator " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to verify on element present on partner list page
     *
     * @param key - locator of the element
     * @param name - name of the company or user
     */
    public final boolean verifyOnElementsWithNameOfWEPPartnerCatalogueRevampPage(String key, String name) {
        try {
            return verifyElementIsPresent(WEPPartnerCatalogueRevampPage.getProperty(key)+name);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsWithNameOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
            return false;
        }

    }

    /**
     * This is the method to wait for any element on the partner list page untill it is visible
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is visible
     */
    public final boolean waitForElementsOfWEPPartnerCatalogueRevampPage(String key) {
        try {
            return verifyElementIsVisible(WEPPartnerCatalogueRevampPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
            return false;
        }
    }

    public final boolean waitForElementsOfWEPPartnerCatalogueRevampPageByLocator(String locator) {
        try {
            return verifyElementIsVisible(locator);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEPPartnerCatalogueRevampPageByLocator " + e.getMessage()));
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
    public final boolean matchTextOfWEPPartnerCatalogueRevampPage(String key, String textToMatch) {
        try {
            return verifyTextPresentOnElement(WEPPartnerCatalogueRevampPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
            return false;
        }
    }

    public final boolean matchTextOfWEPPartnerCatalogueRevampPageByLocator(String locator, String textToMatch) {
        try {
            return verifyTextPresentOnElement(locator, textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEPPartnerCatalogueRevampPageByLocator " + e.getMessage()));
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
    public final boolean verifyElementIsEnableOfWEPPartnerCatalogueRevampPage(String key) {
        try {
            return verifyElementIsEnable(WEPPartnerCatalogueRevampPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementIsEnableOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to verify if an element on partner list page is selected
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is selected
     */
    public final boolean verifyElementIsSelectedOfWEPPartnerCatalogueRevampPage(String key) {
        try {
            return verifyElementIsSelected(WEPPartnerCatalogueRevampPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementIsSelectedOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to get text present on an element on partner list page
     *
     * @param key - locator of the element
     * @return - string value of the text present on the element
     */
    public final String getTextOfWEPPartnerCatalogueRevampPage(String key) {
        try {
            return getTextBy(WEPPartnerCatalogueRevampPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
            return null;
        }
    }

    public final String getTextOfWEPPartnerCatalogueRevampPageByLocator(String locator) {
        try {
            return getTextBy(locator);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
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
    public final String getAttributesOfWEPPartnerCatalogueRevampPage(String key, String desiredValue) {
        try {
            return getAttribute(WEPPartnerCatalogueRevampPage.getProperty(key), desiredValue);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getAttributesOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
            return null;
        }
    }

    public final void refreshPageOfWEPPartnerCatalogueRevampPage() {
        try {
            hardRefresh();
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method refreshPageOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to click on element present on partner list page
     *
     * @param key - locator of the element
     */
    public final boolean clickOnElementsOfWEPPartnerCatalogueRevampPage(String key) {
        try {
            click(WEPPartnerCatalogueRevampPage.getProperty(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
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
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(WEPPartnerCatalogueRevampPage.getProperty(key))));
            element.click();
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
            return false;
        }
    }

    /*
     * This is a method to get list of web elements
     *
     * @param key - locator of the element
     * @return - list of web elements
     * */
    public List<WebElement> getWebelementsOfWEPPartnerCatalogueRevampPage(String key) {
        try {
            return getAllElements(WEPPartnerCatalogueRevampPage.getProperty(key));
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
    public final void enterTextForWEPPartnerCatalogueRevampPage(String key, String textToMatch) {
        try {
            enterText(WEPPartnerCatalogueRevampPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method enterTextForWEPPartnerCatalogueRevampPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to scroll on WorkforceExp Customer Creation Page
     * @param key
     */
    public final void scrollOnWEPPartnerCatalogueRevampPage(String key) {
        try {
            scrollTillView(WEPPartnerCatalogueRevampPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method scrollOnWEPPartnerCatalogueRevampPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to enter text on an element
     * @param key
     * @throws Exception
     */
    public final void actionClickOnWEPPartnerCatalogueRevampPage(String key) throws Exception {
        actionClick(WEPPartnerCatalogueRevampPage.getProperty(key));
    }

    public final boolean matchTextOnWEPPartnerCatalogueRevampPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEPPartnerCatalogueRevampPage.getProperty(key), Text);
    }

    /**
     * This method is designed to wait element is visible
     * @param key
     * @return
     */
    public final boolean waitForElementsOnWEPPartnerCatalogueRevampPage(String key) {
        try {
            return verifyElementIsVisibleDynamic(WEPPartnerCatalogueRevampPage.getProperty(key),120);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOnWEPPartnerCatalogueRevampPage " + e.getMessage()));
            return false;
        }

    }

    /**
     * This is a method to hover mouse on an element
     *
     * @param key - Locator of element
     */
    public final void mousehoverOnWEPPartnerCatalogueRevampPage(String key) {
        try {
            mouseHover(WEPPartnerCatalogueRevampPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method mousehoverOnPartnerPage " + e.getMessage()));
        }
    }

    public final void mousehoverOnWEPPartnerCatalogueRevampPageByLocator(String locator) {
        try {
            mouseHover(locator);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method mousehoverOnWEPPartnerCatalogueRevampPageByLocator " + e.getMessage()));
        }
    }
    /**
     * This is a method to click on an element using java script
     *
     * @param key - locator of element
     * @throws Exception
     */
    public final void clickByJavaScriptOnElementsOnPartnerPage(String key) throws Exception {
        clickByJavaScript(WEPPartnerCatalogueRevampPage.getProperty(key));
    }

    public final void clickByJavaScriptOnElementsOnPartnerPageByLocator(String locator) throws Exception {
        clickByJavaScript(locator);
    }

    public final void doubleClickByActionsOnElementsOnPartnerPage(String key) {
        try {
            doubleclick(WEPPartnerCatalogueRevampPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method doubleClickByActionsWEPClickDevicelistPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to click on element present on partner list page
     *
     * @param locator - locator of the element
     */
    public final boolean clickOnElementOfWEPPartnerCatalogueRevampPage(String locator) {
        try {
            click(locator);
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    public final void openDropDownOfWEPPartnerCatalogueRevampPage(String key) {
        try {
            verifyElementsOfWEPPartnerCatalogueRevampPage(key);
            mousehoverOnWEPPartnerCatalogueRevampPage(key);
            sleeper(1000);
            clickByJavaScriptOnElementsOnPartnerPage(key);
            sleeper(1000);
            clickOnElementsOfwexPartnerPage(key);
            sleeper(1000);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method openDropDownOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to click on element present on partner list page
     *
     * @param locator - locator of the serach box
     * @param ImportCatalogCompany - option to be selected
     */
    public final void  selectDropDownValueOfWEPPartnerCatalogueRevampPage(String searchBox, String ImportCatalogCompany, String selectCompany) {
        try {
            waitForElementsOfWEPPartnerCatalogueRevampPage(searchBox);
            enterTextForWEPPartnerCatalogueRevampPage(searchBox, ImportCatalogCompany);
            sleeper(3000);//to load the company name entered
            clickOnElementsOfWEPPartnerCatalogueRevampPage(selectCompany);
            sleeper(3000);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method selectDropDownValueOfWEPPartnerCatalogueRevampPage " + e.getMessage()));
        }
    }
    /**
     * This method is used for file imported using Robot class for veneer version 3
     * @param fileName this is the name of file which was imported
     * @throws Exception
     */
    public void fileImportInV3(String fileName) throws Exception {
        sleeper(2000);
        List<WebElement> addFile = getWebelementsOfWEPPartnerCatalogueRevampPage("browseInput");
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
        mousehoverOnWEPPartnerCatalogueRevampPage("bellNotification");
        sleeper(1000);

        // verify the Import Bell Notification
        clickOnElementsOfWEPPartnerCatalogueRevampPage("bellNotification");
        String importedCatalogNotificationTitle = WEPPartnerCatalogueRevampPage.getProperty("importedCatalogNotificationTitle");
        importedCatalogNotificationTitle = importedCatalogNotificationTitle.replace("TenantId", catalogCustomerId);
        waitForElementsOfWEPPartnerCatalogueRevampPageByLocator(importedCatalogNotificationTitle);

        String importedCatalogNotificationDate = WEPPartnerCatalogueRevampPage.getProperty("importedCatalogNotificationDate");
        importedCatalogNotificationDate = importedCatalogNotificationDate.replace("TenantId", catalogCustomerId);
        String catalogTitle = getTextLanguage("en", "daas_ui", "catalog.label").toUpperCase();

        int tries = 0;
        while (tries < 300) {
            String notificationCatalogTitle = getTextOfWEPPartnerCatalogueRevampPageByLocator(importedCatalogNotificationTitle);
            String notificationTime = getTextOfWEPPartnerCatalogueRevampPageByLocator(importedCatalogNotificationDate);

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

        String importedCatalogNotificationMessage = WEPPartnerCatalogueRevampPage.getProperty("importedCatalogNotificationMessage");
        importedCatalogNotificationMessage = importedCatalogNotificationMessage.replace("TenantId", catalogCustomerId);
        verifyElementsOfWEPPartnerCatalogueRevampPageByLocator(importedCatalogNotificationMessage);
        String notificationMessage = getTextOfWEPPartnerCatalogueRevampPageByLocator(importedCatalogNotificationMessage);
        if (notificationMessage.contains("successfully")) {
            if (!notificationMessage.contains(catalogImportedMessage)) {
                flag = false;
                LOGGER.info("Notification catalogImportedMessage does not match");
            } else {
                LOGGER.info("Notification catalogImportedMessage matches");
            }
            String catalogNotification = WEPPartnerCatalogueRevampPage.getProperty("catalogNotification");
            catalogNotification = catalogNotification.replace("TenantId", catalogCustomerId);

            mousehoverOnWEPPartnerCatalogueRevampPageByLocator(catalogNotification);
            String notificationHambergerButton = WEPPartnerCatalogueRevampPage.getProperty("notificationHambergerButton");
            notificationHambergerButton = notificationHambergerButton.replace("TenantId", catalogCustomerId);
//            clickOnElementsOfWEPPartnerCatalogueRevampPageByLocator(notificationHambergerButton);
            clickByJavaScriptOnElementsOnPartnerPageByLocator(notificationHambergerButton);
            verifyElementsOfWEPPartnerCatalogueRevampPage("markAllAsReadButton");
            verifyElementsOfWEPPartnerCatalogueRevampPage("openLogsButton");
            clickOnElementsOfWEPPartnerCatalogueRevampPage("clearNotificationButton");
            clickOnElementsOfWEPPartnerCatalogueRevampPage("bellNotification");
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
     * @return true if the details match else false
     * @throws Exception
     */
    public final boolean  verifyImportedCatalog (String catalogType) throws Exception {
        boolean  flag = true;
        // get the list of catalogs
        List<WebElement> catalogList = getWebelementsOfWEPPartnerCatalogueRevampPage("catalogList");
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

        // validate the product count of the imported catalog
        String catalogProductCount = WEPPartnerCatalogueRevampPage.getProperty("catalogProductCount");
        catalogProductCount = catalogProductCount.replace("ind",  ""+index);
        boolean  productCountPresent = matchTextOfwexPartnerPage(catalogProductCount, "15");

        if (!productCountPresent) {
            LOGGER.error("The product count of the imported catalog is not matching");
            flag = false;
        }

        // validate the updated on date of the imported catalog
        String catalogUpdatedOn = WEPPartnerCatalogueRevampPage.getProperty("catalogUpdatedOn");
        catalogUpdatedOn = catalogUpdatedOn.replace("ind",  ""+index);
        boolean  updatedOnPresent = matchTextOfwexPartnerPage(catalogUpdatedOn, getTodaysDateInMMDDYYYY());

        if (!updatedOnPresent) {
            LOGGER.error("The updated on date of the imported catalog is not matching");
            flag = false;
        }

        // validate the catalog type of the imported catalog
        boolean  catalogTypePresent = matchTextOfwexPartnerPage(WEPPartnerCatalogueRevampPage.getProperty("catalogType"), catalogType);

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
            List<WebElement> catalogList = getWebelementsOfWEPPartnerCatalogueRevampPage("catalogList");
            int catalogSize = catalogList.size();
            if (catalogSize > 1)
                for (int i = 0; i < catalogSize - 1; i++) {
                    // click on the delete button
                    String deleteButton = WEPPartnerCatalogueRevampPage.getProperty("catalogThreeDotButton");
                    deleteButton = deleteButton.replace("ind","1");
                    clickOnElementOfWEPPartnerCatalogueRevampPage(deleteButton);
                    // click on the delete dropdown option
                    waitForElementsOfWEPPartnerCatalogueRevampPage("catalogDeleteButtonList");
                    mousehoverOnWEPPartnerCatalogueRevampPage("catalogDeleteButtonList");
                    clickByJavaScriptOnElementsOnPartnerPage("catalogDeleteButtonList");
                    verifyElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogTitle");
                    verifyElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogMessage");
                    clickOnElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogButton");
                    waitForElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogtoastNotification");
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
        List<WebElement> catalogList = getWebelementsOfWEPPartnerCatalogueRevampPage("catalogList");
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
        if (index != -1) {
            String deleteButton = WEPPartnerCatalogueRevampPage.getProperty("catalogThreeDotButton");
            deleteButton = deleteButton.replace("ind", "" + index);
            clickOnElementOfWEPPartnerCatalogueRevampPage(deleteButton);
            // click on the delete dropdown option
            waitForElementsOfWEPPartnerCatalogueRevampPage("catalogDeleteButtonList");
            mousehoverOnWEPPartnerCatalogueRevampPage("catalogDeleteButtonList");
            clickByJavaScriptOnElementsOnPartnerPage("catalogDeleteButtonList");
            verifyElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogTitle");
            verifyElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogMessage");
            clickOnElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogButton");
            waitForElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogtoastNotification");

            boolean toastNotification = matchTextOfWEPPartnerCatalogueRevampPage("deleteCatalogtoastNotification", getTextLanguage("en", "daas_ui", "catalogs.list.toast.remove.success"));
            if (!toastNotification) {
                LOGGER.error("The toast notification for deleting the catalog is not present");
                flag = false;
            }

            waitForPageLoaded();
        }
        // Again validate weather the catalog is deleted or not
        waitUntilElementIsVisibleDynamic("catalogList", 60);
        catalogList = getWebelementsOfWEPPartnerCatalogueRevampPage("catalogList");
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
        List<WebElement> catalogList = getWebelementsOfWEPPartnerCatalogueRevampPage("catalogList");
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

        waitForElementsOfWEPPartnerCatalogueRevampPage("removeCatalogButton");
        if (!matchTextOfWEPPartnerCatalogueRevampPage("removeCatalogButton", "Remove")) {
            LOGGER.error("The remove button label is not present");
            flag = false;
        }
        sleeper(1000);
        clickOnElementsOfWEPPartnerCatalogueRevampPage("removeCatalogButton");
        verifyElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogTitle");
        verifyElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogMessage");
        sleeper(1000);
        clickOnElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogButton");
        waitForElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogtoastNotification");

        boolean  toastNotification = matchTextOfWEPPartnerCatalogueRevampPage("deleteCatalogtoastNotification", getTextLanguage("en", "daas_ui", "catalogs.list.toast.remove.success"));
        if (!toastNotification) {
            LOGGER.error("The toast notification for deleting the catalog is not present");
            flag = false;
        }

        waitUntilElementIsVisibleDynamic("catalogList", 60);
        catalogList = getWebelementsOfWEPPartnerCatalogueRevampPage("catalogList");
        if (catalogSize-1 != catalogList.size())
            flag = false;

        return flag;
    }
    public final boolean selectTextValueFromDropdownOfCustomerListPageWorkflow(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
        return selectTextValueFromDropdownWorkflow(WEPPartnerCatalogueRevampPage.getProperty(dropdownListKey), elementText, dropdownBox);
    }

    public final void selectCustomerInPartner(String partnerCustomer) {
        try {
            mousehoverOnWEPPartnerCatalogueRevampPage("searchItem");
            sleeper(2000);
            clickByJavaScriptOnElementsOnPartnerPage("searchItem");
            clickOnElementsOfwexPartnerPage("searchItem");
            waitForElementsOfWEPPartnerCatalogueRevampPage("searchCompany");
            enterTextForWEPPartnerCatalogueRevampPage("searchCompany", partnerCustomer);
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
            List<WebElement> customerList = getWebelementsOfWEPPartnerCatalogueRevampPage(customers);
            int size = customerList.size();

            for (int customer = 0; customer < size; customer++) {
                clickOnElementsOfWEPPartnerCatalogueRevampPage("customers");
                // clear all filters of customer page
                wepPartnerCustomersPage.clearAllFiltersOfWEPPartnerCustomersPage();
                mousehoverOnWEPPartnerCatalogueRevampPage("searchItem");
                Thread.sleep(2000);
                clickByJavaScriptOnElementsOnPartnerPage("searchItem");
                clickOnElementsOfwexPartnerPage("searchItem");
                sleeper(2000);
                if (selectTextValueFromDropdownOfCustomerListPageWorkflow("listDropdown", CommonVariables.COMPANY_NAME, "")){
                    Thread.sleep(2000);
                    verifyElementsOfWEPPartnerCatalogueRevampPage("pendingStatus");
                    clickOnElementsOfWEPPartnerCatalogueRevampPage("optionChoice");
                    verifyElementsOfWEPPartnerCatalogueRevampPage("resendInvite");
                    verifyElementsOfWEPPartnerCatalogueRevampPage("viewCustomerProfile");
                    verifyElementsOfWEPPartnerCatalogueRevampPage("deleteInvite");
                    clickOnElementsOfWEPPartnerCatalogueRevampPage("deleteInvite");
                    String customerName = getTextOfWEPPartnerCatalogueRevampPage("deleteCustomerPopupDescription");
                    if (customerName.contains(CommonVariables.COMPANY_NAME) || customerName.contains("uiautomationinviteita donotdeletetestusstage")) {
                        clickOnElementsOfWEPPartnerCatalogueRevampPage("confirmDeleteComp");
                        verifyElementsOfWEPPartnerCatalogueRevampPage("deleteConfirmToasts");
                    }
                }
                else
                {
                    clickOnElementsOfWEPPartnerCatalogueRevampPage("home");
                    clickOnElementsOfWEPPartnerCatalogueRevampPage("customers");
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("Exception occured in method deletePendingInviteCustomerFromPartner " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public final void openManageAssignmentModal() {
        try {
            waitForElementsOfWEPPartnerCatalogueRevampPage("catalogThreeDotButton2");
            mousehoverOnWEPPartnerCatalogueRevampPage("catalogThreeDotButton2");
            clickByJavaScriptOnElementsOnPartnerPage("catalogThreeDotButton2");
            waitForElementsOfWEPPartnerCatalogueRevampPage("catalogSetAsDefaultButton");
            mousehoverOnWEPPartnerCatalogueRevampPage("catalogSetAsDefaultButton");
            clickByJavaScriptOnElementsOnPartnerPage("catalogSetAsDefaultButton");
            LOGGER.info("Clicked on Set as Default Catalog button");
        }
        catch (Exception e) {
            LOGGER.error("Exception occurred in method setAsDefaultCatalog " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to assign the preuploaded catalog to a customer
     * @throws Exception
     */
    public final boolean setAsDefaultCatalog() {
        boolean toastNotification;
        try {
            List<WebElement> companylist = getWebelementsOfWEPPartnerCatalogueRevampPage("catalogSetAsDefaultToggle");
            int noOfCompanies = companylist.size();
            LOGGER.info("Total companies are : " + noOfCompanies);

            // select random customer from the list of customers to assign the default catalog
            Random random = new Random();
            int randomIndex = random.nextInt(noOfCompanies);
            String selectedCompanyToggleLocator = WEPPartnerCatalogueRevampPage.getProperty("selectCatalogSetAsDefaultToggle");
            selectedCompanyToggleLocator = selectedCompanyToggleLocator.replace("ind", "" + (randomIndex));
            clickByJavaScriptOnElementsOnPartnerPageByLocator(selectedCompanyToggleLocator);
            sleeper(2000);
            LOGGER.info("Selected company number" + randomIndex + " to assign the default catalog");
            // Get Company name
            String defaultCompanyNameLocator = WEPPartnerCatalogueRevampPage.getProperty("catalogSetAsDefaultCustomerName");
            defaultCompanyNameLocator = defaultCompanyNameLocator.replace("ind", "" + (randomIndex));
            String companyName = getTextOfWEPPartnerCatalogueRevampPageByLocator(defaultCompanyNameLocator);
            // click on the save button
            clickOnElementsOfWEPPartnerCatalogueRevampPage("setAsDefaultSaveButton");
            waitForElementsOfWEPPartnerCatalogueRevampPage("catalogSetAsDefaultToastNotification");
            toastNotification = matchTextOfWEPPartnerCatalogueRevampPage("catalogSetAsDefaultToastNotification", getTextLanguage("en", "daas_ui", "catalog.default_catalog.set"));
            LOGGER.info("Catalog set as default for a customer successfully");
            waitForPageLoaded();
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method setAsDefaultCatalog " + e.getMessage());
            throw new RuntimeException(e);
        }
        return toastNotification;
    }

    /**
     * This method is used to assign the preuploaded catalog to a customer
     * @throws Exception
     */
    public final String getCompanyNameCatalogDefaultFor() {
        String companyName = "";
        try {
            List<WebElement> companylist = getWebelementsOfWEPPartnerCatalogueRevampPage("catalogSetAsDefaultToggle");
            int noOfCompanies = companylist.size();
            LOGGER.info("Total companies are : " + noOfCompanies);

            // select random customer from the list of customers to assign the default catalog
            Random random = new Random();
            int randomIndex = random.nextInt(noOfCompanies);
            String selectedCompanyToggleLocator = WEPPartnerCatalogueRevampPage.getProperty("selectCatalogSetAsDefaultToggle");
            selectedCompanyToggleLocator = selectedCompanyToggleLocator.replace("ind", "" + (randomIndex));
            clickByJavaScriptOnElementsOnPartnerPageByLocator(selectedCompanyToggleLocator);
            sleeper(2000);
            LOGGER.info("Selected company number" + randomIndex + " to assign the default catalog");
            // Get Company name
            String defaultCompanyNameLocator = WEPPartnerCatalogueRevampPage.getProperty("catalogSetAsDefaultCustomerName");
            defaultCompanyNameLocator = defaultCompanyNameLocator.replace("ind", "" + (randomIndex));
            companyName = getTextOfWEPPartnerCatalogueRevampPageByLocator(defaultCompanyNameLocator);
            // click on the save button
            clickOnElementsOfWEPPartnerCatalogueRevampPage("setAsDefaultSaveButton");
            waitForElementsOfWEPPartnerCatalogueRevampPage("catalogSetAsDefaultToastNotification");
            LOGGER.info("Catalog set as default for a customer successfully");
            waitForPageLoaded();
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method setAsDefaultCatalog " + e.getMessage());
            throw new RuntimeException(e);
        }
        return companyName;
    }

    public final boolean openCatalogDetailsPage () throws Exception {
        // get the list of catalogs
        boolean  flag = true;
        List<WebElement> catalogList = getWebelementsOfWEPPartnerCatalogueRevampPage("catalogList");
        int catalogSize = catalogList.size();
        String importedCatalogName = "catalogValid Product Catalog";
        int index = -1;  // index of the uploaded catalog in the list
        for (int i = 0; i < catalogSize; i++) {
            WebElement catalog = catalogList.get(i);
            String catalogName = getTextBy(catalog);
            if (catalogName.equals(importedCatalogName)) {
                index = i + 1;
                break;
            }
        }
        if (index == -1) {
            LOGGER.error("The imported catalog is not present in the list");
            flag = false;
        }

        if (index != -1) {
            String customCatalog = WEPPartnerCatalogueRevampPage.getProperty("customCatalog");
            customCatalog = customCatalog.replace("ind", "" + index);
            mousehoverOnWEPPartnerCatalogueRevampPage(customCatalog);
            clickByJavaScriptOnElementsOnPartnerPageByLocator(customCatalog);
            waitForElementsOfWEPPartnerCatalogueRevampPage("catalogDetailsTitle");
        }
        return flag;
    }
}