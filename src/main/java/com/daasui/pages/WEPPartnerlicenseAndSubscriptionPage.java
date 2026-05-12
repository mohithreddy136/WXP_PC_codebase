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

public class WEPPartnerlicenseAndSubscriptionPage extends CommonMethod {

    private WEPPartnerlicenseAndSubscriptionPage instance;
    private Properties WEPPartnerlicenseAndSubscriptionProperties;
    private ObjectReader WEPPartnerlicenseAndSubscriptionPagePropertiesReader = new ObjectReader();
    private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");
    LocalDate today = LocalDate.now();
    int day = today.getDayOfMonth();

    public WEPPartnerlicenseAndSubscriptionPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPPartnerlicenseAndSubscriptionPage.class) {
                if (instance == null) {
                    instance = new WEPPartnerlicenseAndSubscriptionPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public final String getTodayDateFormatted() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return currentDate.format(formatter);
    }

    public final String getNextDateFormatted() {
        LocalDate currentDate = LocalDate.now();
        LocalDate nextDate = currentDate.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return nextDate.format(formatter);
    }

    public final void selectStartDate() {
        try {
            String todayDate = getTodayDateFormatted();
            click(WEPPartnerlicenseAndSubscriptionProperties.getProperty("StartDateInput"));
            enterText(WEPPartnerlicenseAndSubscriptionProperties.getProperty("StartDateInput"), todayDate);
            LOGGER.info("Selected Start Date: " + todayDate);
        } catch (Exception e) {
            LOGGER.error("Error selecting Start Date: " + e.getMessage());
        }
    }
    public final void selectEndDate() {
        try {
            String nextDate = getNextDateFormatted();
            click(WEPPartnerlicenseAndSubscriptionProperties.getProperty("EndDateInput"));
            enterText(WEPPartnerlicenseAndSubscriptionProperties.getProperty("EndDateInput"), nextDate);
            LOGGER.info("Selected End Date: " + nextDate);
        } catch (Exception e) {
            LOGGER.error("Error selecting End Date: " + e.getMessage());
        }
    }

    public WEPPartnerlicenseAndSubscriptionPage(WebDriver driver) throws IOException {
        this.DRIVER = driver;
        WEPPartnerlicenseAndSubscriptionProperties =
                WEPPartnerlicenseAndSubscriptionPagePropertiesReader.getObjectRepository("WEPPartnerSubscriptionflow");
    }


    public final void clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage(String key) throws Exception {
        click(WEPPartnerlicenseAndSubscriptionProperties.getProperty(key));
    }

    public final boolean verifyElementsOfWEPPartnerlicenseAndSubscriptionPage(String key) {
        try {
            return verifyElementIsPresent(WEPPartnerlicenseAndSubscriptionProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEPPartnerSubscriptionPage " + e.getMessage()));
            return false;
        }
    }

    public final int generateAndEnterRandomNumber() {
        int randomNumber = 0;

        try {
            Random random = new Random();
            randomNumber = random.nextInt(99) + 1;
            enterTextOfWEPPartnerlicenseAndSubscriptionPage("EndpointInput", String.valueOf(randomNumber));

            LOGGER.info("Random number '" + randomNumber + "' is generated and entered in the textbox.");
        } catch (Exception e) {
            LOGGER.error("Error while generating or entering the random number: ", e);
        }

        return randomNumber;
    }

    public final boolean matchTextOfWEPPartnerlicenseAndSubscriptionPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEPPartnerlicenseAndSubscriptionProperties.getProperty(key), Text);
    }


    public final String getTextOfWEPPartnerlicenseAndSubscriptionPage(String key) throws Exception {
        return getTextBy(WEPPartnerlicenseAndSubscriptionProperties.getProperty(key));
    }

    public final void enterTextOfWEPPartnerlicenseAndSubscriptionPage(String key, String Text) {
        try {
            enterText(WEPPartnerlicenseAndSubscriptionProperties.getProperty(key), Text);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method enterTextOfWEPPartnerlicenseAndSubscriptionProperties " + e.getMessage()));
        }
    }

    public final void clickByJavaScriptOnWEPPartnerlicenseAndSubscriptionPage(String key) throws Exception {
        clickByJavaScript(WEPPartnerlicenseAndSubscriptionProperties.getProperty(key));
    }

    public final void selectCompanyForRevoke(String checkboxKey) {
        try {
            click(WEPPartnerlicenseAndSubscriptionProperties.getProperty(checkboxKey));
            LOGGER.info("Company checkbox selected successfully for revoke.");
        } catch (Exception e) {
            LOGGER.error("Error while selecting company checkbox for revoke: " + e.getMessage());
        }
    }

    public void increaseEndpointValueByOne() {
        try {
            WebElement endpoint = DRIVER.findElement(By.xpath("//input[@placeholder='Enter number']"));

            int current = Integer.parseInt(endpoint.getAttribute("value"));
            int updated = current + 1;

            endpoint.clear();
            endpoint.sendKeys(String.valueOf(updated));

            LOGGER.info("Updated EndpointInput to: " + updated);
        } catch (Exception e) {
            LOGGER.error("Error increasing endpoint value: " + e.getMessage());
        }
    }

        public final boolean matchTextOnWEPPartnerlicenseAndSubscriptionPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEPPartnerlicenseAndSubscriptionProperties.getProperty(key), Text);
    }

    public final boolean waitForElementsOfWEPPartnerlicenseAndSubscriptionPage(String key) {
        try {
            return verifyElementIsVisible(WEPPartnerlicenseAndSubscriptionProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEPPartnerlicenseAndSubscriptionPage " + e.getMessage()));
            return false;
        }
    }

    public void searchCompany(String companyName) {
        try {
            WebDriver driver = getDriver();
            WebElement searchBox = driver.findElement(By.xpath("(//input[@id='table-name-filter'])[2]"));
            ((JavascriptExecutor) DRIVER).executeScript("arguments[0].scrollIntoView({block: 'center'});", searchBox);
            ((JavascriptExecutor) DRIVER).executeScript("arguments[0].click();", searchBox);
            searchBox.clear();
            searchBox.sendKeys(companyName);

            LOGGER.info("Entered company name for search: " + companyName);

        } catch (Exception e) {
            LOGGER.error("Error entering company name: " + e.getMessage());
        }
    }

    public String getSubscriptionStatus(String companyName) {
        try {
            WebDriver driver = getDriver();

            String statusXpath = "//td[text()='" + companyName + "']/following-sibling::td[2]";
            WebElement statusElement = driver.findElement(By.xpath(statusXpath));

            String status = statusElement.getText().trim();
            LOGGER.info("Fetched status for company '" + companyName + "': " + status);
            return status;

        } catch (Exception e) {
            LOGGER.error("Error while fetching subscription status for company '" + companyName + "': " + e.getMessage());
            return null;
        }
    }
    public void waitForElementToBeClickable(String key) throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(getObject(WEPPartnerlicenseAndSubscriptionProperties.getProperty(key))));
    }
}
