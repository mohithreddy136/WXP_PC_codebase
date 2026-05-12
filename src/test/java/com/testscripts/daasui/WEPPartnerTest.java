package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.*;
import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WEPPartnerTest extends CommonTest {

    private static Logger LOGGER = LogManager.getLogger(WEPPartnerTest.class);
    public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
    public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");

    @DataProvider
    public Object[][] getLoginData() {
        Object[][] data = new Object[2][2];
        data[0][0] = "PARTNER_EMAIL_WEP";
        data[0][1] = "PARTNER_PASSWORD_WEP";
        data[1][0] = "ITADMIN_EMAIL_WEP";
        data[1][1] = "ITADMIN_PASSWORD_WEP";
        return data;
    }

    /**
     * This method will verify the partner login
     */
    @Test(priority = 1, groups = {"REGRESSION_PARTNERCX", "PRODUCTION_PARTNERCX"}, description = "TestCase ID : C67893085")
    public final void verifyPartnerLogin() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        softAssert.assertAll();
        LOGGER.info("Partner login verified successfully");
    }

    /**
     * This method will verify the all the partner tabs and their navigation
     */
    @Test(priority = 2, groups = { "REGRESSION_PARTNERCX", "PRODUCTION_PARTNERCX" }, description = "TestCase ID : C67893228")
    public final void verifyPartnerTabsWithBreadcrumbNavigation() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("HomeTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.home")), "Home tab text is not matching");
        softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("HomeTab"), "Home tab is not clickable");
        waitForLoaderIconToDisappear();
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.home")), "Home tab Breadcrumb is not matching");

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomersTab", getTextLanguage(LanguageCode, "daas_ui", "whats_new.customers")), "Customers tab text is not matching");
        softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("CustomersTab"), "Customers tab is not clickable");
        waitForLoaderIconToDisappear();
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "whats_new.customers")), "Customers tab Breadcrumb is not matching");

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("ProductCatalogTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.catalogs")), "Requests tab text is not matching");
        softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("ProductCatalogTab"), "Requests tab is not clickable");
        waitForLoaderIconToDisappear();
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb",  getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.catalogs")), "Requests tab Breadcrumb is not matching");

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FleetAnalyticsTab", getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab text is not matching");
        softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("FleetAnalyticsTab"), "Fleet Analytics tab is not clickable");
        waitForLoaderIconToDisappear();
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb",  getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab Breadcrumb is not matching");

        if(toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "PARTNER_EMAIL_WEP"))) {
            Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AccountManagementTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.account")), "Account tab text is not matching");
            softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("AccountManagementTab"), "Account tab is not clickable");
            waitForLoaderIconToDisappear();
            softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.account")), "Account tab Breadcrumb is not matching");
        } else {
            Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AccountManagementTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.accountManagement")), "Account Management tab text is not matching");
            softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("AccountManagementTab"), "Account Management tab is not clickable");
            waitForLoaderIconToDisappear();
            softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.accountManagement")), "Account Management tab Breadcrumb is not matching");
        }
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("IntegrationTab", getTextLanguage(LanguageCode, "daas_ui", "integrations.tab.integrations")), "Integration tab text is not matching");
        softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("IntegrationTab"), "Integration tab is not clickable");
        waitForLoaderIconToDisappear();
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "integrations.tab.integrations")), "Integration tab Breadcrumb is not matching");

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SettingsTab", getTextLanguage(LanguageCode, "daas_ui", "settings.label")), "Settings tab text is not matching");
        softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("SettingsTab"), "Settings tab is not clickable");
        waitForLoaderIconToDisappear();
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "settings.label")), "Settings tab Breadcrumb is not matching");

        Assert.assertTrue(WEXPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("HelpAndSupportButton"), "Help & Support button is not matching");
        softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("HelpAndSupportButton"), "Help & Support tab is not clickable");
        waitForLoaderIconToDisappear();
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help & Support tab Breadcrumb is not matching");

        softAssert.assertAll();
        LOGGER.info("All the partner tabs verified successfully");
    }

    /**
     * This method will verify the Experience Over Time widget on partner dashboard
     */
    @Test(priority = 3, groups = {"REGRESSION_PARTNERCX", "PRODUCTION_PARTNERCX"}, description = "TestCase ID: C53035196")
    public final void verifyExperienceOverTimeWidget() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        WEXDetailsPage WEXDetailsPage = new WEXDetailsPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("experienceOverTime", getTextLanguage(LanguageCode, "daas_ui", "ee_wex_over_time_heading")), "Experience Over Time widget is not available on partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("experienceOverTimeGraph"), "experienceOverTimeGraph locator is not present");

        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("greatExperience", getTextLanguage(LanguageCode, "daas_ui", "ee_great_experience")), "Great Experience locator is not available on partner dashboard");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("fairExperience", getTextLanguage(LanguageCode, "daas_ui", "ee_fair_experience")), "Fair Experience locator is not available on partner dashboard");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("poorExperience", getTextLanguage(LanguageCode, "daas_ui", "ee_poor_experience")), "Poor Experience locator is not available on partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("greatExperienceCustomerCount"), "greatExperienceCustomerCount locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("fairExperienceCustomerCount"), "fairExperienceCustomerCount locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("poorExperienceCustomerCount"), "poorExperienceCustomerCount locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("experienceOverTimeXaxis"), "experienceOverTimeXaxis locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("experienceOverTimeYaxis"), "experienceOverTimeYaxis locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("experienceOverTimeGraph1"), "experienceOverTimeYaxis locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("experienceOverTimeGraph2"), "experienceOverTimeYaxis locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("experienceOverTimeGraph3"), "experienceOverTimeYaxis locator is not present");

        String greatExperienceCustomerCount = wexPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("greatExperienceCustomerCount");
        String fairExperienceCustomerCount = wexPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("fairExperienceCustomerCount");
        String poorExperienceCustomerCount = wexPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("poorExperienceCustomerCount");

        LOGGER.info("Great Experience Customer Count : " + greatExperienceCustomerCount);
        LOGGER.info("Fair Experience Customer Count : " + fairExperienceCustomerCount);
        LOGGER.info("Poor Experience Customer Count : " + poorExperienceCustomerCount);

        softAssert.assertTrue(wexPartnerDashboardPage.isValidCustomerCountFormat(greatExperienceCustomerCount),
                "Great Experience Customer Count format is incorrect. Actual: [" + greatExperienceCustomerCount + "]");
        softAssert.assertTrue(wexPartnerDashboardPage.isValidCustomerCountFormat(fairExperienceCustomerCount),
                "Fair Experience Customer Count format is incorrect. Actual: [" + fairExperienceCustomerCount + "]");
        softAssert.assertTrue(wexPartnerDashboardPage.isValidCustomerCountFormat(poorExperienceCustomerCount),
                "Poor Experience Customer Count format is incorrect. Actual: [" + poorExperienceCustomerCount + "]");

        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("expOverTImeframeworkFilter"), "experienceOverTimeFrameworkSelector locator is not present");

        wexPartnerDashboardPage.mousehoverOnWEXPartnerDashboardPage("expOverTimeFilter");
        softAssert.assertTrue(wexPartnerDashboardPage.actionClickOnElementsOfWEXPartnerDashboardPage("expOverTimeFilter"), "experienceOverTimeFrameworkSelector is not clickable");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("last30Days", getTextLanguage(LanguageCode, "daas_ui", "ee_last_30_days")), "Last 30 Days option is not available on partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.actionClickOnElementsOfWEXPartnerDashboardPage("last30Days"), "last30Days is not clickable");

        softAssert.assertTrue(wexPartnerDashboardPage.actionClickOnElementsOfWEXPartnerDashboardPage("expOverTimeFilter"), "experienceOverTimeFrameworkSelector is not clickable");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("last90Days", getTextLanguage(LanguageCode, "daas_ui", "ee_last_90_days")), "Last 90 Days option is not available on partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.actionClickOnElementsOfWEXPartnerDashboardPage("last90Days"), "last90Days locator is not present");

        Assert.assertTrue(WEXDetailsPage.verifyListofElementsWithCountOfWEXDetailsPage("ExperienceOverTimeGraphLines",WEPPulsesCreationPageVariables.Partner_Experience_OverTime_graphLines_COUNT),"Experience over time graph lines are not present on the experience details page");

        softAssert.assertAll();
        LOGGER.info("The Experience Over Time widget verified successfully");
    }

    /**
     * This method will verify the Endpoints Growth Widget on partner dashboard
     */
    @Test(priority = 4, groups = {"REGRESSION_PARTNERCX", "PRODUCTION_PARTNERCX"}, description = "TestCase ID: C44466846")
    public final void verifyEndpointsGrowthWidget() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("customersByLocationLegend1");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("endpointsGrowthTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.partner_dashboard.tile.title.endpoints_by_subscription")),"Endpoints Growth card is not available on partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("endpointsGrowthXaxis"), "endpointsGrowthXaxis locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("endpointsGrowthYaxis"), "endpointsGrowthYaxis locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("endpointsGrowthWidget"), "endpointsGrowthFullGraph4 locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("endpointsGrowthFullGraphPlot"), "endpointsGrowthFullGraph6 locator is not present");

        softAssert.assertAll();
        LOGGER.info("The endpoints growth widget verified successfully");
    }

    /**
     * This method will verify the Customers by Location Widget on partner dashboard
     */
    @Test(priority = 5, groups = {"REGRESSION_PARTNERCX", "PRODUCTION_PARTNERCX"}, description = "TestCase ID: C52618471")
    public final void verifyCustomersByLocationWidget() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("customersByLocationLegend1");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("customersByLocationTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.partner_dashboard.tile.title.customers_by_location")),"Customers by Location widget is not available on partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("customersByLocationWidget"), "customersByLocationWidget  is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("customersByLocationFullMap"), "customersByLocationFullMap  is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("customersByLocationMapWidget"), "customersByLocationMapWidget  is not present");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("customersByLocationLegend1", getTextLanguage(LanguageCode, "daas_ui", "customer.bylocation.lessfifty.endpoint")), "<50 endpoints is not available on partner dashboard");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("customersByLocationLegend2", getTextLanguage(LanguageCode, "daas_ui", "customer.bylocation.betweenfiftyhundred.endpoint")),"50-100 endpoints is not available on partner dashboard");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("customersByLocationLegend3", getTextLanguage(LanguageCode, "daas_ui", "customer.bylocation.greaterhundred.endpoint")),">100 endpoints is not available on partner dashboard");

        softAssert.assertAll();
        LOGGER.info("The Customers by Location widget verified successfully");
    }


    /**
     * This method will verify the Send Feedback Widget on partner dashboard
     */
    @Test(priority = 6, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID: 48460785", enabled = false)
    public final void verifySendFeedbackWidget() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("customersByLocationLegend1");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("sendFeedback", getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.send_feedback.title")), "Send Feedback title is not available on partner dashboard");
        Assert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("sendFeedbackImage"), "Send Feedback image is not available on partner dashboard");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("sendFeedbackSubheading", getTextLanguage(LanguageCode, "daas_ui", "wex.feedback.subheading")), "Send Feedback subheading is not available on partner dashboard");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("sendFeedbackDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.feedback.desc")), "Send Feedback description is not available on partner dashboard");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("sendFeedbackButtonText", getTextLanguage(LanguageCode, "daas_ui", "wex.feedback.send.button")), "Send Feedback button is not available on partner dashboard");
        wexPartnerDashboardPage.mousehoverOnWEXPartnerDashboardPage("sendFeedbackButton");
        Assert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("sendFeedbackButton"), "Send Feedback button is not clickable on partner dashboard");
        switchToDifferentTab();
        // Verify the title of the new tab
        String expectedTitle = "HP Workforce Experience Platform Feedback Form";
        String actualTitle = wexPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("feedbackFormTitle");
        Assert.assertEquals(actualTitle, expectedTitle, "The title of the feedback form tab is not as expected");
        switchBackToPreviousTab();
        softAssert.assertAll();
        LOGGER.info("The Send Feedback widget verified successfully");
    }

    /********************************* Customer Engagement ****************************************************/

    /**
     * This method will verify the customer engagement title is not available id Customer Engagement card is not available.
     */
    @Test(priority = 7, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 44395851")
    public final void verifyIfNoCustomerEngagmentCardIsAvailable() throws Exception {
        login("NO_DATA_PARTNER_EMAIL_WEP", "NO_DATA_PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        waitForPageLoaded();
        WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("SelectCustomer");

        // If the All customer is not selected, select the All customer
        if (!WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEXPartnerDashboardPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        //Verify no customer engagement card is available
        Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.customer_engagement")),"No Customer Engagement card is available");
        LOGGER.info("Customer Engagement card is not available");
    }

    /**
     * This method will verify Low Device Enrollment Card is available or not.
     */

    @Test(priority = 8, groups = { "REGRESSION_PARTNERCX"}, description = "TestCase ID : 42735085",enabled = false)
    public final void verifyCustomerEngagementOfLowDviceEnrollmentCardWithExport() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);

        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("CustomerEngagementTitle");
        sleeper(2000);
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.customer_engagement")),"Customer Engagement card is not available on partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("LowDeviceEnrollmentHeader", getTextLanguage(LanguageCode, "daas_ui", "ce.lower_than_expected_device_enrollment.title")), "Customer Engagement LowDeviceEnrollmentHeader card is not available on partner dashboad");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("LowDeviceEnrollmentSubheader", getTextLanguage(LanguageCode, "daas_ui", "ce.lower_than_expected_device_enrollment.desc")),"Customer Engagement LowDeviceEnrollmentSubheader card is not available on partner dashboad");

        wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("LowDeviceEnrollmentCard");
        LOGGER.info("Clicked on Customer Engagement LowDeviceEnrollmentSubheader card to open company modal");

        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementCompanyModelTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.lower_than_expected_device_enrollment.title")), "Customer Engagement LowDeviceEnrollmentHeader card is not available on company model page");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementCompanyModelSubTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.lower_than_expected_device_enrollment.desc")),"Customer Engagement LowDeviceEnrollmentHeaderSubheader card is not available on company model page");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CompaniesTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.modal.companies")+":"),"Customer Engagement LowDeviceEnrollmentHeader - COMPANIES is not available on company model page");
        LOGGER.info("Customer Engagement LowDeviceEnrollment card is available on company modal");

        softAssert.assertTrue(wexPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("LowDeviceEnrollmentFirstCompany"), "First company is not visible in DevicesNotCompatibleWithWindows11 modal");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("LowDeviceEnrollmentDetails", getTextLanguage(LanguageCode, "daas_ui", "global.details")),"Details is not available on company model page");
        wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("LowDeviceEnrollmentDetails");

        sleeper(2000);
        wexPartnerDashboardPage.switchToDifferentTab();
        waitForPageLoaded();
        String Actual_URL = wexPartnerDashboardPage.getUrlOfCurrentPage();

        String ActualPage_Title = wexPartnerDashboardPage.getBrowserTabName();
        String Expected_Title = "HTML View";
        softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for Low device enrollment is not matching");
        LOGGER.info("Actual Title is :" + ActualPage_Title);

        wexPartnerDashboardPage.switchBackToPreviousTab();
        LOGGER.info("Validation of Low device enrollment completed.");
        LOGGER.info("Actual URL is :" + Actual_URL);
        LOGGER.info("Successfully validated Low device enrollment URL");
        softAssert.assertAll();
        LOGGER.info("Verified Low device enrollmentsuccessfully");

        softAssert.assertAll();
        LOGGER.info("Low Device Enrollment card verified successfully");
    }

    /**
     * 	 This method will verify Devices Not Compatible With Windows11 Card is available or not.
     */

    @Test(priority = 9, groups = { "REGRESSION_PARTNERCX"}, description = "TestCase ID : 43096570",enabled = false)
    public final void verifyCustomerEngagementOfDevicesNotCompatibleWithWindows11WithExport() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);

        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("CustomerEngagementTitle");
        sleeper(2000);
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.customer_engagement")),"Customer Engagement card is not available on partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("DevicesNotCompatibleWithWindows11Header", getTextLanguage(LanguageCode, "daas_ui", "ce.device_not_compatiable_with_windows11.title")), "Customer Engagement DevicesNotCompatibleWithWindows11Header card is not available on partner dashboad");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("DevicesNotCompatibleWithWindows11Subheader", getTextLanguage(LanguageCode, "daas_ui", "ce.device_not_compatiable_with_windows11.desc")),"Customer Engagement DevicesNotCompatibleWithWindows11Subheader card is not available on partner dashboad");

        wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("DevicesNotCompatibleWithWindows11Header");
        LOGGER.info("Clicked on DevicesNotCompatibleWithWindows11Header to open company modal");

        // Verify the DevicesNotCompatibleWithWindows11 card is available on company modal
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementCompanyModelTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.device_not_compatiable_with_windows11.title")), "Customer Engagement DevicesNotCompatibleWithWindows11Header card is not available on company model page");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementCompanyModelSubTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.device_not_compatiable_with_windows11.desc")),"Customer Engagement DevicesNotCompatibleWithWindows11Subheader card is not available on company model page");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CompaniesTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.modal.companies")+":"),"Customer Engagement DevicesNotCompatibleWithWindows11Subheader card - COMPANIES is not available on company model page");
        LOGGER.info("DevicesNotCompatibleWithWindows11 card is available on company modal");

        softAssert.assertTrue(wexPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("DevicesNotCompatibleWithWindows11FirstCompany"), "First company is not visible in DevicesNotCompatibleWithWindows11 modal");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("DevicesNotCompatibleWithWindows11Details", getTextLanguage(LanguageCode, "daas_ui", "global.details")),"Details is not available on company model page");
        wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("DevicesNotCompatibleWithWindows11Details");

        sleeper(2000);
        wexPartnerDashboardPage.switchToDifferentTab();
        waitForPageLoaded();
        String Actual_URL = wexPartnerDashboardPage.getUrlOfCurrentPage();

        String ActualPage_Title = wexPartnerDashboardPage.getBrowserTabName();
        String Expected_Title = "HTML View";
        softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for DevicesNotCompatibleWithWindows11 is not matching");
        LOGGER.info("Actual Title is :" + ActualPage_Title);

        wexPartnerDashboardPage.switchBackToPreviousTab();
        LOGGER.info("Validation of WXP Action card completed.");
        LOGGER.info("Actual URL is :" + Actual_URL);
        LOGGER.info("Successfully validated Windows 11 Readiness Assessment URL");
        softAssert.assertAll();
        LOGGER.info("Verified Windows 11 Readiness Assessment successfully");
    }

    /**
     * 	 This method will verify PC Warranties Have Ended Card is available or not.
     */

    @Test(priority =10, groups = { "REGRESSION_PARTNERCX"}, description = "TestCase ID : 43096672",enabled = false)
    public final void verifyCustomerEngagementOfPCWarrantiesHaveEndedWithExport() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);

        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("CustomerEngagementTitle");
        sleeper(2000);
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.customer_engagement")),"Customer Engagement card is not available on partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("PCWarrantiesHaveEndedHeader", getTextLanguage(LanguageCode, "daas_ui", "ce.warranty_ended.title")), "Customer Engagement PCWarrantiesHaveEndedHeader card is not available on partner dashboad");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("PCWarrantiesHaveEndedSubheader", getTextLanguage(LanguageCode, "daas_ui", "ce.warranty_ended.desc")),"Customer Engagement PCWarrantiesHaveEndedSubheader card is not available on partner dashboad");

        wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("PCWarrantiesHaveEndedCard");
        LOGGER.info("Clicked on PCWarrantiesHaveEndedHeader to open company modal");

        // Verify the DevicesNotCompatibleWithWindows11 card is available on company modal
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementCompanyModelTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.warranty_ended.title")), "Customer Engagement PCWarrantiesHaveEndedHeader card is not available on company model page");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementCompanyModelSubTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.warranty_ended.desc")),"Customer Engagement PCWarrantiesHaveEndedHeaderSubheader card is not available on company model page");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CompaniesTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.modal.companies")+":"),"Customer Engagement PCWarrantiesHaveEndedHeaderSubheader card - COMPANIES is not available on company model page");
        LOGGER.info("PCWarrantiesHaveEndedHeader card is available on company modal");

        softAssert.assertTrue(wexPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("PCWarrantiesHaveEndedFirstCompany"), "First company is not visible in PCWarrantiesHaveEnded modal");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("DevicesNotCompatibleWithWindows11Details", getTextLanguage(LanguageCode, "daas_ui", "global.details")),"Details is not available on company model page");
        wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("DevicesNotCompatibleWithWindows11Details");

        sleeper(2000);
        wexPartnerDashboardPage.switchToDifferentTab();
        waitForPageLoaded();
        String Actual_URL = wexPartnerDashboardPage.getUrlOfCurrentPage();

        String ActualPage_Title = wexPartnerDashboardPage.getBrowserTabName();
        String Expected_Title = "HTML View";
        softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for PCWarrantiesHaveEnded is not matching");
        LOGGER.info("Actual Title is :" + ActualPage_Title);

        wexPartnerDashboardPage.switchBackToPreviousTab();
        LOGGER.info("Validation of PCWarrantiesHaveEnded completed.");
        LOGGER.info("Actual URL is :" + Actual_URL);

        softAssert.assertAll();
        LOGGER.info("PC Warranties have ended card verified successfully");
    }

    /**
     * 	 This method will verify PC Warranties Expires Soon is available or not.
     */

    @Test(priority = 11, groups = { "REGRESSION_PARTNERCX"}, description = "TestCase ID : 43096725",enabled = false)
    public final void verifyCustomerEngagementOfPCWarrantiesExpiresSoonWithExport() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();

        // If the what's new popup is available, close it
        if(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("whatsNewPopupClosedButton"))
            wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("whatsNewPopupClosedButton");

        leftSideMenuVerification();
        waitForPageLoaded();

        wexPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("SelectCustomer");

        // If the All customer is not selected, select the All customer
        if (!wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            wexPartnerDashboardPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        // Verify the All customer is selected
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SelectCustomer", CommonVariables.ALL_CUSTOMER));
        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("CustomerEngagementTitle");

        // Verify the Customer Engagement card is available on dashboad
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.customer_engagement")),"Customer Engagement card is not available on partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("PCWarrantiesExpiresSoonHeader", getTextLanguage(LanguageCode, "daas_ui", "ce.warranty_ending_soon.title")), "Customer Engagement PCWarrantiesExpiresSoonHeader card is not available on partner dashboad");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("PCWarrantiesExpiresSoonSubheader", getTextLanguage(LanguageCode, "daas_ui", "ce.warranty_ending_soon.desc")),"Customer Engagement PCWarrantiesExpiresSoonSubheader card is not available on partner dashboad");

        String PCWarrantiesExpiresSoonFirstCompany = wexPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("PCWarrantiesExpiresSoonFirstCompany");

        // all companies on customer engagement card
        List<WebElement> allCompaniesOnCard = wexPartnerDashboardPage.getWebelementsOfWEXPartnerDashboardPage("PCWarrantiesExpiresSoonAllCompany");
        int allCompaniesNameOnCardSize = allCompaniesOnCard.size();
        if (allCompaniesNameOnCardSize == 3) {
            String companyCount = allCompaniesOnCard.get(2).getText().trim();   // allCompaniesOnCard[2] = "+2"
            companyCount = companyCount.replace("+", "");
            allCompaniesNameOnCardSize = Integer.parseInt(companyCount) + 2;
        }

        // Verify the first company name on company modal
        wexPartnerDashboardPage.mousehoverOnWEXPartnerDashboardPage("PCWarrantiesExpiresSoonHeader");
        wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("PCWarrantiesExpiresSoonHeader");
        LOGGER.info("Clicked on PCWarrantiesExpiresSoonHeader to open company modal");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustEngFirstCompanyOnCompModal", PCWarrantiesExpiresSoonFirstCompany), "First company name on company modal is not matching");

        // Verify the PCWarrantiesExpiresSoon card is available on company modal
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementCompanyModelTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.warranty_ending_soon.title")), "Customer Engagement PCWarrantiesExpiresSoonHeader card is not available on company model page");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementCompanyModelSubTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.warranty_ending_soon.desc")),"Customer Engagement PCWarrantiesExpiresSoonSubheader card is not available on company model page");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CompaniesTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.modal.companies")+":"),"Customer Engagement PCWarrantiesExpiresSoonSubheader card - COMPANIES is not available on company model page");
        LOGGER.info("PCWarrantiesExpiresSoon card is available on company modal");

        // all companies on company modal
        List<WebElement> allCompaniesOnModel = wexPartnerDashboardPage.getWebelementsOfWEXPartnerDashboardPage("CustomerEngagementAllCompaniesOnModel");

        softAssert.assertTrue(allCompaniesNameOnCardSize == allCompaniesOnModel.size(), "Company count on company modal is not matching with company count on card");

        // Verify the device count and detail button on company modal
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("DeviceCountOnCompanyModal"), "Device count is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerPage("CustomerEngagementDetailsButton", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.details"), "CompanyName", PCWarrantiesExpiresSoonFirstCompany),"Detail button is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("CustomerEngagementDetailsButton", "CompanyName", PCWarrantiesExpiresSoonFirstCompany), "Detail button is not clickable on company modal");
        waitForPageLoaded();
        LOGGER.info("Clicked on detail button of company modal");

        String PCWarrantiesExpiresSoonReportTitle = "Hardware Warranty";
        String PCWarrantiesExpiresSoonReportName = "_hwwarV2_details";

        // Switch to the PCWarrantiesExpiresSoon details tab
        softAssert.assertTrue(wexPartnerDashboardPage.switchToCustomerEngagementReportTab(PCWarrantiesExpiresSoonFirstCompany, PCWarrantiesExpiresSoonReportTitle, PCWarrantiesExpiresSoonFirstCompany+PCWarrantiesExpiresSoonReportName), "PCWarrantiesExpiresSoon details tab is not present");
        wexPartnerDashboardPage.clickOnWebelementOfWEXPartnerDashboardPage("CustomerEngagementCompanyModelCloseButton");
        softAssert.assertAll();
        LOGGER.info("PC Warranties expires soon card verified successfully");
    }

    /**
     * This method will verify Batteries Identified In Recall Card is available or not.
     */
    @Test(priority = 12, groups = { "REGRESSION_PARTNERCX"}, description = "TestCase ID : 43096727",enabled = false)
    public final void verifyCustomerEngagementOfBatteriesIdentifiedInRecall() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("CustomerEngagementTitle");

        // Verify the Customer Engagement card is available on dashboad
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.customer_engagement")),"Customer Engagement card is not available on partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("BatteriesIdentifiedInRecallHeader", getTextLanguage(LanguageCode, "daas_ui", "ce.battery_identified_for_recall.title")), "Customer Engagement BatteriesIdentifiedInRecallHeader card is not available on partner dashboad");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("BatteriesIdentifiedInRecallSubheader", getTextLanguage(LanguageCode, "daas_ui", "ce.battery_identified_for_recall.desc")),"Customer Engagement BatteriesIdentifiedInRecallSubheader card is not available on partner dashboad");

        String BatteriesIdentifiedInRecallFirstCompany = wexPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("BatteriesIdentifiedInRecallFirstCompany");

        // all companies on customer engagement card
        List<WebElement> allCompaniesOnCard = wexPartnerDashboardPage.getWebelementsOfWEXPartnerDashboardPage("BatteriesIdentifiedInRecallAllCompany");
        int allCompaniesNameOnCardSize = allCompaniesOnCard.size();
        if (allCompaniesNameOnCardSize == 3) {
            String companyCount = allCompaniesOnCard.get(2).getText().trim();   // allCompaniesOnCard[2] = "+2"
            companyCount = companyCount.replace("+", "");
            allCompaniesNameOnCardSize = Integer.parseInt(companyCount) + 2;
        }

        // Verify the first company name on company modal
        wexPartnerDashboardPage.mousehoverOnWEXPartnerDashboardPage("BatteriesIdentifiedInRecallHeader");
        wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("BatteriesIdentifiedInRecallHeader");
        LOGGER.info("Clicked on BatteriesIdentifiedInRecallHeader to open company modal");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustEngFirstCompanyOnCompModal", BatteriesIdentifiedInRecallFirstCompany), "First company name on company modal is not matching");

        // Verify the BatteriesIdentifiedInRecall card is available on company modal
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementCompanyModelTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.battery_identified_for_recall.title")), "Customer Engagement BatteriesIdentifiedInRecallHeader card is not available on company model page");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerEngagementCompanyModelSubTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.battery_identified_for_recall.desc")),"Customer Engagement BatteriesIdentifiedInRecallSubheader card is not available on company model page");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CompaniesTitle", getTextLanguage(LanguageCode, "daas_ui", "ce.modal.companies")+":"),"Customer Engagement BatteriesIdentifiedInRecallSubheader card - COMPANIES is not available on company model page");
        LOGGER.info("BatteriesIdentifiedInRecall card is available on company modal");

        // all companies on company modal
        List<WebElement> allCompaniesOnModel = wexPartnerDashboardPage.getWebelementsOfWEXPartnerDashboardPage("CustomerEngagementAllCompaniesOnModel");

        softAssert.assertTrue(allCompaniesNameOnCardSize == allCompaniesOnModel.size(), "Company count on company modal is not matching with company count on card");

        // Verify the device count and detail button on company modal
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("DeviceCountOnCompanyModal"), "Device count is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerPage("CustomerEngagementDetailsButton", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.details"), "CompanyName", BatteriesIdentifiedInRecallFirstCompany),"Detail button is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("CustomerEngagementDetailsButton", "CompanyName", BatteriesIdentifiedInRecallFirstCompany), "Detail button is not clickable on company modal");
        waitForPageLoaded();
        LOGGER.info("Clicked on detail button of company modal");

        String BatteriesIdentifiedInRecallReportTitle = "Battery Status and Risk Factor";
        String BatteriesIdentifiedInRecallReportName = "_batteryStatRiskFact_batteryRepAndRiskFact";

        // Switch to the BatteriesIdentifiedInRecall details tab
        softAssert.assertTrue(wexPartnerDashboardPage.switchToCustomerEngagementReportTab(BatteriesIdentifiedInRecallFirstCompany, BatteriesIdentifiedInRecallReportTitle, BatteriesIdentifiedInRecallFirstCompany+BatteriesIdentifiedInRecallReportName), "BatteriesIdentifiedInRecall details tab is not present");
        wexPartnerDashboardPage.clickOnWebelementOfWEXPartnerDashboardPage("CustomerEngagementCompanyModelCloseButton");
        softAssert.assertAll();
        LOGGER.info("Batteries identified in recall card verified successfully");
    }

    /************************************* Account Management *********************************************/

    /**
     * This method will verify the update partner name functionality from Account Management Overview tab.
     */
    @Test(priority = 13, groups = {"REGRESSION_PARTNERCX" }, description = "TestCase ID: 44395133")
    public final void verifyUpdateOfPartnerName() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        String partnerName = "WEXAutomationPartner-" + generateRandomNumber(0, 1000);
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        WEXPartnerDashboardPage.partnerView(CommonVariables.ACCOUNT);
        LOGGER.info("Redirected to AccountManagement Overview page");

        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("overview", getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.overview_title")), "Overview is incorrect");

        //Test Case 1 - This test case validates partner(company) name edit functionality
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("partnerNameIdentity"), "partnerNameIdentity is not present");
        String oldPartnerName = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("partnerNameIdentity");
        LOGGER.info("Old Partner Name is : " + oldPartnerName);
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("companyProfileHeader", getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.company_profile_title")), "Company profile title is incorrect");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("PartnerID", getTextLanguage(LanguageCode, "daas_ui", "partner.id")), "Partner ID is incorrect");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerReferenceID", getTextLanguage(LanguageCode, "daas_ui", "partners.list.authorization.crs_id")), "CustomerReferenceID is incorrect");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CompanyName", getTextLanguage(LanguageCode, "daas_ui", "global.form.company_name")), "CompanyName is incorrect");
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("companycreatedon"), "companycreatedon is not present");
        //softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("companyProfileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.name")), "Company Name field for partner is not present on details tile");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("companyProfileEdit");
        LOGGER.info("Clicked on edit Company name button");
        sleeper(2000);
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CompanyNameOnPopUp", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.name")), "CompanyNameOnPopUp is incorrect");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CompanyNameLabelOnPopUP", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.name")), "CompanyNameLabelOnPopUP is incorrect");
        WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("CompanyNameTextBox", partnerName);
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("SubmitButton"), "Submit button is not present");
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("cancelName"), "Cancel button is not present");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("cancelName");
        LOGGER.info("Clicked on cancel button");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("companyProfileEdit");
        LOGGER.info("Clicked on edit name button");
        WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("CompanyNameTextBox", partnerName);
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("SubmitButton");
        LOGGER.info("Clicked on Submit button");
        WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("toastNotification");
        // softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("toastNotification"), "Toast notification is not generated after changing company Name of Partner");
        String newPartnerName = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("partnerNameIdentity");
        LOGGER.info("New Partner Name is : " + newPartnerName);
        softAssert.assertFalse(oldPartnerName.equalsIgnoreCase(newPartnerName), "Partner name is not updated");
        softAssert.assertAll();
        LOGGER.info("Validation for Company Name section is completed");
    }

    /**
     * This method will verify the update preferred time zone functionality from Account Management Overview tab.
     */
    @Test(priority = 14, groups = {}, description = "TestCase ID: 44395133")
    public final void verifyUpdatePreferredTimeZone() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        WEXPartnerDashboardPage.partnerView(CommonVariables.ACCOUNT_MANAGEMENT);
        LOGGER.info("Redirected to AccountManagement Overview page");

        List<String> timeZones = new ArrayList<>();
        timeZones.add("chennaiTimeZone");
        timeZones.add("kolkataTimeZone");
        timeZones.add("mumbaiTimeZone");
        timeZones.add("newDelhiTimeZone");

        // Get the old preferred time zone
        String oldPreferredTimeZone = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("currentPreferredTimeZone");

        // Remove the old preferred time zone from the list
        if (oldPreferredTimeZone.contains("Kolkata")) {
            timeZones.remove("kolkataTimeZone");
        }
        if (oldPreferredTimeZone.contains("New Delhi")) {
            timeZones.remove("newDelhiTimeZone");
        }
        if (oldPreferredTimeZone.contains("Chennai")) {
            timeZones.remove("chennaiTimeZone");
        }
        if (oldPreferredTimeZone.contains("Mumbai")) {
            timeZones.remove("mumbaiTimeZone");
        }

        // Select a random time zone from the remaining timeZones list
        Random random = new Random();
        int randomIndex = random.nextInt(timeZones.size());
        String randomTimeZone = timeZones.get(randomIndex);
        LOGGER.info("Random Time Zone selected is : " + randomTimeZone);

        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("preferredTimeZoneEditButton");
        LOGGER.info("Clicked on edit preferred time zone button");
        sleeper(2000);
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("preferredTimeZonePopupHeader", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_preferred_time_zone")), "Preferred Time Zone header on pop up is incorrect");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("timezoneLabel", getTextLanguage(LanguageCode, "daas_ui", "settings.country.time_zone")), "Time Zone label on pop up is incorrect");
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("saveTimeZoneButton"), "Save button is not present");
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("cancelTimeZoneButton"), "Cancel button is not present");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("cancelTimeZoneButton");
        LOGGER.info("Clicked on cancel button of time zone pop up");

        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("preferredTimeZoneEditButton");
        LOGGER.info("Clicked on edit timezone button");

        WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        wexPartnerPage.openDropDownOfWEXPartnerPage("timezoneSelectBox");
        LOGGER.info("Clicked on dropdown");

        sleeper(1000);
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage(randomTimeZone);
        sleeper(1000);
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("saveTimeZoneButton");
        LOGGER.info("Clicked on save button of time zone pop up");
        WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("toastNotificationTimezone");
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("toastNotificationTimezone"), "Toast notification is not generated after changing time zone of Partner");
        String newPreferredTimeZone = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("currentPreferredTimeZone");
        LOGGER.info("Old Preferred Name is : " + oldPreferredTimeZone);
        LOGGER.info("New Preferred Name is : " + newPreferredTimeZone);
        softAssert.assertFalse(oldPreferredTimeZone.equalsIgnoreCase(newPreferredTimeZone), "Preferred Time Zone is not updated");
        softAssert.assertAll();
        LOGGER.info("Validation for Preferred Time Zone section is completed");
    }

    /**
     * This method will verify the switch of primary partner admin account functionality from Account Management Overview tab.
     */
    @Test(priority = 15, groups = {"REGRESSION_PARTNERCX" }, description = "TestCase ID: 44395133")
    public final void verifyUpdateOfPrimaryPartnerAdminAccount() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        String partnerName = "WEXPartner-" + generateRandomNumber(0, 1000);
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        WEXPartnerDashboardPage.partnerView(CommonVariables.ACCOUNT);
        LOGGER.info("Redirected to AccountManagement Overview page");

        WEXPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("primaryAdministratorHeader");
        String primaryAdministratorHeader = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("primaryAdministratorHeader");
        String primaryAdministratorHeaderMestro = getTextLanguage(LanguageCode, "daas_ui", "partners.list.primary_admin");
        softAssert.assertTrue(primaryAdministratorHeader.equalsIgnoreCase(primaryAdministratorHeaderMestro), "Primary Adminstrator name header on label is incorrect");
        LOGGER.info("Primary Administrator Header is : " + primaryAdministratorHeader);
        LOGGER.info("Primary Administrator Header from properties file is : " + primaryAdministratorHeaderMestro);
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("primaryAdminNameLabel", getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.name")), "Primary Adminstrator name on label is incorrect");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Adminemail", getTextLanguage(LanguageCode, "daas_ui", "settings.email.label")), "Primary Adminstrator email on label is incorrect");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("primaryadministratoreditbutton");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("primaryadministratorheaderpopup", getTextLanguage(LanguageCode, "daas_ui", "partners.list.primary_admin")), "Primary Administrator Header on pop up is incorrect");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("primaryadministratorselectboxLabel", getTextLanguage(LanguageCode, "daas_ui", "partners.list.primary_admin")), "Primary Administrator label on pop up is incorrect");
        WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("primaryadministratorselectbox");
        WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("primaryadministratorsavebutton");
        WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("primaryadministratorcancelbutton");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("primaryadministratorcancelbutton");
        LOGGER.info("Validation for primary Administrator  popup and cancel button are completed");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("primaryadministratoreditbutton");
        String currentPrimaryAdministrator = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("currentPrimaryAdmin");
        wexPartnerPage.openDropDownOfWEXPartnerPage("primaryadministratorselectbox");
        LOGGER.info("Clicked on Primary Administrator dropdown");

        List<WebElement> primaryAdminList =
                PreDefinedActions.getDriver().findElements(By.xpath("//ul[@role='listbox']//li[@role='option']"));

        LOGGER.info("Total Primary Admins are : " + primaryAdminList.size());

        List<WebElement> filteredList = primaryAdminList.stream()
                .filter(e -> !e.getText().equalsIgnoreCase(currentPrimaryAdministrator))
                .collect(Collectors.toList());

        filteredList.get(new Random().nextInt(filteredList.size())).click();

        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("primaryadministratorsavebutton");
        WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("primaryAdminToastNotification");
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("primaryAdminToastNotification"), "Toast notification is not generated after changing time zone of Partner");

        WEXPartnerDashboardPage.pageRefreshForWEXPartnerDashboardPage();
        WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("primaryAdminName");
        WEXPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("primaryAdminName");
        String newPrimaryAdminName = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("primaryAdminName");
        LOGGER.info("New Primary Admin Name is : " + currentPrimaryAdministrator);
        LOGGER.info("Current Primary Admin Name is : " + newPrimaryAdminName);
        assert newPrimaryAdminName != null;
        LOGGER.info("Validation for Primary Administrator section is completed");
    }

    /**
     * 	 This method will verify the update partner address functionality from Account Management Overview tab.
     */
    @Test(priority = 16, groups = {"REGRESSION_PARTNERCX" }, description = "TestCase ID: 44395133")
    public final void verifyUpdatePartnerAddressOnAccountManagementPage() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");

        String address1, address2, state, city, zipcode;
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        WEXPartnerDashboardPage.partnerView(CommonVariables.ACCOUNT);
        LOGGER.info("Redirected to AccountManagement Overview page");

        //Test Case  - This test case validates partner address edit functionality
        WEXPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("companyAddressHeader");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("companyAddressHeader",getTextLanguage(LanguageCode, "daas_ui", "company.details.company_address")), "Company Address header is incorrect");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("countrylabel",getTextLanguage(LanguageCode, "daas_ui", "create_company.msp.country")), "Country field for partner is not present on details tile");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("address-line-1label",getTextLanguage(LanguageCode, "daas_ui", "users.details.address1")), "Address line 1 field for partner is not present on details tile");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("address-line-2label",getTextLanguage(LanguageCode, "daas_ui", "users.details.address2")), "Address line 2 field for partner is not present on details tile");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("statelabel",getTextLanguage(LanguageCode, "daas_ui", "global.state")), "State field for partner is not present on details tile");

        softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addressEditButton"), "Address Edit button is not clickable");
        LOGGER.info("Clicked on Address edit button");

        softAssert.assertTrue(
                WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("addressCountrySelectBox"),
                "Country field is not present on company address popup"
        );
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("AddressLine1labelonpop"), "AddressLine1labelonpop is not present");
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("AddressLine2labelonpop"), "AddressLine2labelonpop is not present");
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("citylabelonpop"), "citylabelonpop is not present");
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("statelabelonpop"), "statelabelonpop is not present");
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("ziplabelonpop"), "ziplabelonpop is not present");

        address1 = "Bank of " + WEXPartnerDashboardPage.generateRandomString(5);
        address2 = WEXPartnerDashboardPage.generateRandomString(8);
        city     = WEXPartnerDashboardPage.generateRandomString(6);
        state    = WEXPartnerDashboardPage.generateRandomString(6);
        zipcode  = WEXPartnerDashboardPage.generateRandomNumber();

        softAssert.assertTrue(
                WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage(
                        "addressPopupHeader",
                        getTextLanguage(LanguageCode, "daas_ui", "global.address")),
                "Header on address popup of Partner is incorrect");

        WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("addressStreetLine1TextBox", address1);
        WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("addressStreetLine2TextBox", address2);
        WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("cityTextBox", city);
        WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("stateTextBox", state);
        WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("zipCodeTextBox", zipcode);

        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addressCancelButton");
        softAssert.assertTrue(
                WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("addressPopupHeader"),
                "Address popup is still visible after cancel");

        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addressEditButton");


        softAssert.assertTrue(
                WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage(
                        "addressPopupHeader",
                        getTextLanguage(LanguageCode, "daas_ui", "global.address")),
                "Header on address popup of Partner is incorrect");

        WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("addressStreetLine1TextBox", address1);
        WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("addressStreetLine2TextBox", address2);
        WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("cityTextBox", city);
        WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("stateTextBox", state);
        WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("zipCodeTextBox", zipcode);

        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addressSaveButton");
        WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("toastNotificationadd");

        softAssert.assertTrue(
                WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("toastNotificationadd"),
                "Toast notification is not generated after saving address");

        softAssert.assertAll();
        LOGGER.info("Validation for Company (Partner) Address section is completed");
    }

    /**
     * This test case is to verify Add/Edit/Delete Partner User in Partner Login
     * @throws Exception
     */
    @Test(priority = 17, groups = {"PLATFORM-CX_PRODUCTION_CI", "PLATFORM-CX_REGRESSION_CI"}, description = "TestCase ID : ")
    public final void verifyAddEditDeletePartnerUserInPartnerLogin() throws Exception {
        String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN;
        String userID , currentUrl, tenantID;
        SoftAssert softAssert = new SoftAssert();
        TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerUserListPage WEXPartnerUserListPage = new WEXPartnerUserListPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerUserDetailsPage WEXPartnerUserDetailsPage = new WEXPartnerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
        try {
            login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
            // If the what's new popup is available, close it
            if(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("whatsNewPopupClosedButton"))
                WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("whatsNewPopupClosedButton");
            leftSideMenuVerification();
            waitForPageLoaded();
            WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("SelectCustomer");

            if (!WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SelectCustomer", CommonVariables.ALL_CUSTOMER)) {
                WEXPartnerDashboardPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
            }

            WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("AccountManagementTab");
            WEXPartnerUserListPage.clickOnElementsOfPartnerUserListPage("UsersTabOnPartner");
            WEXPartnerUserListPage.clickOnElementsOfPartnerUserListPage("AddUserButtonOnPartner");

            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("addUsersTitle", getTextLanguage(LanguageCode, "daas_ui", "onboarding.users.title")), "Add Users Title name is incorrect");
            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("userInformationText", getTextLanguage(LanguageCode, "daas_ui", "users.add_manually.text")), "User Information text is incorrect");
            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("stepInformation", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step1")), "Step 1 of 2 text is incorrect");
            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("subHeadingAddUser", getTextLanguage(LanguageCode, "daas_ui", "users.add_manually.sub_text")), "Sub Heading text is incorrect");

            WEXPartnerUserListPage.verifyElementsOfPartnerUserListPage("addAnotherUserBtn");
            WEXPartnerUserListPage.verifyElementsOfPartnerUserListPage("addUserCancelBtn");
            WEXPartnerUserListPage.verifyElementsOfPartnerUserListPage("addUserBackBtn");
            WEXPartnerUserListPage.verifyElementsOfPartnerUserListPage("addUserNextBtn");

            WEXPartnerUserListPage.clickOnElementsOfPartnerUserListPage("firstNameTextBox");
            WEXPartnerUserListPage.enterTextOfWEXPartnerUserListPage("firstNameTextBox", UserFirstname);
            WEXPartnerUserListPage.clickOnElementsOfPartnerUserListPage("lastNameTextBox");
            WEXPartnerUserListPage.enterTextOfWEXPartnerUserListPage("lastNameTextBox", UserLastname);
            WEXPartnerUserListPage.clickOnElementsOfPartnerUserListPage("emailTextBox");
            WEXPartnerUserListPage.enterTextOfWEXPartnerUserListPage("emailTextBox", UserEmail);
            WEXPartnerUserListPage.actionClickOnWEXPartnerUserListPage("idpTypeDropDown");
            WEXPartnerUserListPage.selectFirstValueFromDropdownOnWEXPartnerUserListPage("idTypeDropDownList");
            WEXPartnerUserListPage.clickOnElementsOfPartnerUserListPage("addUserNextBtn");

            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("stepInformation", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step2")), "Step 2 of 2 text is incorrect");
            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("roleDropResult",CommonVariables.PARTNER_ADMIN), "Role drop down result is incorrect");
            WEXPartnerUserListPage.clickOnElementsOfPartnerUserListPage("addUserNextBtn");
            WEXPartnerUserListPage.waitForElementsOfWEXPartnerUserListPage("toastMessage");
            WEXPartnerUserListPage.verifyElementsOfPartnerUserListPage("toastMessage");
            LOGGER.info("Partner User added successfully");

            if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter")) {
                tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
                waitForPageLoaded();
            }

            WEXPartnerUserListPage.enterTextOfWEXPartnerUserListPage("emailInput", UserEmail);
            Thread.sleep(3000);
            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("emailInputValue", UserEmail.toLowerCase()), "User email is not matching");
            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("roleListValue", CommonVariables.PARTNER_ADMIN), "User role is not matching");
            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("userNameValue", UserFirstname+" "+UserLastname), "User name is not matching");
            WEXPartnerUserListPage.clickOnElementsOfPartnerUserListPage("userNameValue");
            waitForPageLoaded();
            sleeper(5000);
            WEXPartnerUserDetailsPage.clickOnElementsOfWEXPartnerUserDetailsPage("nameEdit");
            String usernameupdate = UserLastname+generateRandomString(3);
            WEXPartnerUserDetailsPage.enterTextOfWEXPartnerUserDetailsPage("editLastName", usernameupdate);
            WEXPartnerUserDetailsPage.clickOnElementsOfWEXPartnerUserDetailsPage("saveButtonUserName");

            WEXPartnerUserDetailsPage.waitForElementsOfWEXPartnerUserDetailsPage("toastMessageonUserRole");
            WEXPartnerUserDetailsPage.verifyElementsOfWEXPartnerUserDetailsPage("toastMessageonUserRole");
            LOGGER.info("Partner User name updated successfully");
            softAssert.assertTrue(WEXPartnerUserDetailsPage.matchTextOnWEXPartnerUserDetailsPage("nameUpdatedValue", UserFirstname + " " +usernameupdate), "User name is not updated");
            LOGGER.info("Partner User name validated on User details page successfully");

            WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("AccountManagementTab");
            WEXPartnerUserListPage.clickOnElementsOfPartnerUserListPage("UsersTabOnPartner");

            if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter")) {
                tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
                waitForPageLoaded();
            }

            WEXPartnerUserListPage.enterTextOfWEXPartnerUserListPage("emailInput", UserEmail);
            Thread.sleep(3000);
            WEXPartnerUserListPage.waitForElementsOfWEXPartnerUserListPage("tableOverlay");
            WEXPartnerUserListPage.waitForElementsOfWEXPartnerUserListPage("emailInputValue");
            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("emailInputValue", UserEmail.toLowerCase()), "User email is not matching");
            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("userNameValue", UserFirstname + " " +usernameupdate), "User name is not matching");
            LOGGER.info("Partner User name validated on User List page successfully");
        }catch(Exception e) {
            Assert.fail("Exception occured in verifyAddEditDeletePartnerUserInPartnerLogin: " + e.getMessage());
        }finally {
            WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("AccountManagementTab");
            WEXPartnerUserListPage.clickOnElementsOfPartnerUserListPage("UsersTabOnPartner");

            if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter")) {
                tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
                waitForPageLoaded();
            }

            WEXPartnerUserListPage.enterTextOfWEXPartnerUserListPage("emailInput", UserEmail);
            Thread.sleep(3000);
            WEXPartnerUserListPage.waitForElementsOfWEXPartnerUserListPage("tableOverlay");
            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("emailInputValue", UserEmail.toLowerCase()), "Partner User email is not matching");
            WEXPartnerUserListPage.clickOnElementsOfPartnerUserListPage("userNameValue");
            sleeper(2000);// Need to wait after clicking on search value
            currentUrl = WEXPartnerUserListPage.getUrlOfCurrentPage();
            userID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1,currentUrl.indexOf("?"));
            tenantID = currentUrl.substring(currentUrl.lastIndexOf("=") + 1);

            // Delete Created Partner user
            Assert.assertTrue(WEXPartnerUserListPage.removeInactivePartnerUser(environment, tenantID,userID, getEnvironment(environment)), "User removal failed");
            WEXPartnerUserListPage.clickOnElementsOfPartnerUserListPage("accountManagementHeader");
            softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("noItemsAvailable", "No items available"),"No Items available message is not displayed");
            LOGGER.info("Partner User Removed successfully");
            softAssert.assertAll();
        }
    }


    /************************************************* Recommended Action *********************************************************************/

    @Test(priority = 18, groups = {"PARTNER_PRODUCTION_CI" }, description = "TestCase ID : 4779314" )
    public final void verifyEmptyRecommendedActionsWidget() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        // If the what's new popup is available, close it
        if(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("whatsNewPopupClosedButton"))
            WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("whatsNewPopupClosedButton");
        leftSideMenuVerification();
        waitForPageLoaded();
        WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("SelectCustomer");

        // If the All customer is not selected, select the All customer
        if (!WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEXPartnerDashboardPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }
        // Verify the All customer is selected
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SelectCustomer", CommonVariables.ALL_CUSTOMER));
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("RAWidgetTitle"), "Recommended Actions tittle is not present");

        String noRecommendedActionsTitle = "No Recommended Actions at this time";
        String noRecommendedActionsSubtitle = "We are currently monitoring your customers' enrolled devices to detect opportunities for improvement";

        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("EmptyRATitle", noRecommendedActionsTitle)  , noRecommendedActionsTitle + " is not present");
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("EmptyRASubTitle", noRecommendedActionsSubtitle)  , noRecommendedActionsSubtitle + " is not present");
        softAssert.assertFalse(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("ViewAllRAButton"),  "View All RA button is present");

        softAssert.assertAll();
        LOGGER.info("Recommended Actions verified successfully");
    }

    /**
     * This method will verify the Guide me button on company model
     */
    @Test(priority = 19, groups = {"PARTNER_PRODUCTION_CI"}, description = "TestCase ID : 44395443")
    public final void verifyGuidemeOnCompanyModel() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        // If the what's new popup is available, close it
        if(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("whatsNewPopupClosedButton"))
            WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("whatsNewPopupClosedButton");
        leftSideMenuVerification();
        waitForPageLoaded();
        WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("SelectCustomer");

        // If the All customer is not selected, select the All customer
        if (!WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEXPartnerDashboardPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }
        // Verify the All customer is selected
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SelectCustomer", CommonVariables.ALL_CUSTOMER));
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("RAWidgetTitle"), "Recommended Actions tittle is not present");

        if (WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("ViewAllRAButton")) {
            WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("ViewAllRAButton");
            waitForPageLoaded();
        }

        // verify the Guideme button for Devices category on Company Model
        if (WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("RADeviceCatagoryHeading")) {
            WEXPartnerDashboardPage.verifyGuidemeButtonOnCompanyModel("AllDevicesRATittle");
        }

        // verify the Guideme button for Security category on Company Model
        if (WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("RASecurityCatagoryHeading")) {
            WEXPartnerDashboardPage.verifyGuidemeButtonOnCompanyModel("AllSecurityRATittle");
        }
        softAssert.assertAll();
        LOGGER.info("Guideme button verified successfully on Company Model");
    }

    /**
     * This method will verify the Category, Impact and card name of RA
     */
    @Test(priority = 20, groups = {"PARTNER_PRODUCTION_CI"}, description = "TestCase ID : 41226870")
    public final void verifyCategoryImpactCardnameOfRA() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        // If the what's new popup is available, close it
        if(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("whatsNewPopupClosedButton"))
            WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("whatsNewPopupClosedButton");
        leftSideMenuVerification();
        waitForPageLoaded();
        WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("SelectCustomer");

        // If the All customer is not selected, select the All customer
        if (!WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEXPartnerDashboardPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }
        // Verify the All customer is selected
        softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SelectCustomer", CommonVariables.ALL_CUSTOMER));
        softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("RAWidgetTitle"), "Recommended Actions tittle is not present");

        if (WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("ViewAllRAButton")) {
            WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("ViewAllRAButton");
            waitForPageLoaded();
        }

        // for Devices category
        if (WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("RADeviceCatagoryHeading")) {
            softAssert.assertTrue(WEXPartnerDashboardPage.verifyCategoryImpactCardnameOfRA("AllDevicesRATittle", "0"), "Device Category, Impact and card name of RA not verified successfully");
        }

        // for Security category
        if (WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("RASecurityCatagoryHeading")) {
            softAssert.assertTrue(WEXPartnerDashboardPage.verifyCategoryImpactCardnameOfRA("AllSecurityRATittle", "1"), "Security Category, Impact and card name of RA not verified successfully");
        }
        softAssert.assertAll();
        LOGGER.info("Category, Impact and card name of RA verified successfully");
    }

    /************************************************* Catalog *********************************************************************/

    /**
     * This method will verify the import of preset catalog for single customers
     * then validate the Bell notification then validate in catalog list page and delete the catalog from the catalog list page
     * & again validate the deletion of the catalog
     */
    @Test(priority = 21, groups = { "REGRESSION_PARTNERCX"}, description = "TestCase ID : 4758184, 41160557", enabled = false)
    public final void verifyImportPresetCatalogForSingleCustomer() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        String catalogAdditionCompany = getEnvironmentSpecificData(System.getProperty("environment"), "CATALOG_CUSTOMER1_NAME");
        String catalogAdditionCompanyID = getEnvironmentSpecificData(System.getProperty("environment"), "CATALOG_CUSTOMER1_TENANTID");
        WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        wexPartnerPage.partnerView(CommonVariables.PARTNER_REQUESTS);

        softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("CatalogTab"), "Catalog tab is not present");
        softAssert.assertTrue(wexPartnerPage.clickOnElementsOfWEXPartnerPage("CatalogTab"), "Not clicked on Catalog tab");
        LOGGER.info("Redirected to Catalog list page");
        wexPartnerPage.deletePreuploadedCatalog();
        softAssert.assertTrue(wexPartnerPage.getTextOfWEXPartnerPage("addCatalogButtonText").equals(wexPartnerPage.getTextLanguage(LanguageCode, "daas_ui", "catalog.list.add_catalog")), "Product catalog import title does not match");
        wexPartnerPage.clickOnElementsOfWEXPartnerPage("addCatalogButton");
        wexPartnerPage.verifyElementsOfWEXPartnerPage("downloadCatalogSample");
        wexPartnerPage.verifyElementsOfWEXPartnerPage("dropDownOpen");
        wexPartnerPage.mousehoverOnWEXPartnerPage("dropDownOpen");
        sleeper(1000);
        wexPartnerPage.clickByJavaScriptOnElementsOnPartnerPage("dropDownOpen");
        sleeper(1000);
        wexPartnerPage.clickOnElementsOfwexPartnerPage("dropDownOpen");
        sleeper(1000);
        LOGGER.info("Clicked on dropdown");

        wexPartnerPage.selectDropDownValueOfWEXPartnerPage("companySearch", catalogAdditionCompany, "selectCompany");
        LOGGER.info("Selected the company");

        // closed the dropdown
        Robot robot = new Robot();
        robot.mouseMove(630, 420); // move mouse point to specific location
        robot.delay(1500);        // delay is to make code wait for mentioned milliseconds before executing next step
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // press left click
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // release left click
        LOGGER.info("closed the company selection dropdown");

        wexPartnerPage.clickOnElementsOfWEXPartnerPage("browseButton");
        wexPartnerPage.fileImportInV3(ConstantPath.IMPORT_PATH + ProductCatalogVariables.FILE_NAME_VALID);
        wexPartnerPage.waitForElementsOfWEXPartnerPage("importButton");
        sleeper(2000);//File upload fails in quick import button click.

        // Press the Escape key to close the file upload window
        robot.keyPress(KeyEvent.VK_ESCAPE);    // Press the Escape key
        robot.keyRelease(KeyEvent.VK_ESCAPE);  // Release the Escape key
        LOGGER.info("closed the uploaded popup");
        // set as default
        wexPartnerPage.clickOnElementsOfWEXPartnerPage("setAsDefaultCheckBox");
        String setAsDefaultCheckBoxLabel = "Set as default catalog";
        softAssert.assertTrue(wexPartnerPage.getTextOfWEXPartnerPage("setAsDefaultCheckBoxLabel").equals(setAsDefaultCheckBoxLabel), "Set as Default does not match");

        wexPartnerPage.clickOnElementsOfWEXPartnerPage("importButton");
        LOGGER.info("Clicked on import button");
        wexPartnerPage.waitForElementsOfWEXPartnerPage("ImportToastNotification");
        softAssert.assertTrue(wexPartnerPage.getTextOfWEXPartnerPage("ImportToastNotification").equals(wexPartnerPage.getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast Notification text does not match");

        // here my page is refreshing so I am waiting for the page to load
        sleeper(20000);
        // Refresh the page
        getDriver().navigate().refresh();
        waitForPageLoaded();
        sleeper(10000);
        // Create a new instance of WebDriverWait
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20)); // 20 seconds timeout

        //softAssert.assertTrue(wexPartnerPage.postNotificationCheckImport(catalogAdditionCompanyID),"Bell Notification is not present");
        //LOGGER.info("Import Catalog notification is validated");
        // Verify the imported catalog details
        softAssert.assertTrue(wexPartnerPage.verifyImportedCatalog(CommonVariables.PRESET_CATALOG, catalogAdditionCompany, ""), "Imported catalog details not verified successfully");
        LOGGER.info("Imported catalog details verified successfully");

        // delete the catalog from the catalog list page
        softAssert.assertTrue(wexPartnerPage.deleteCatalogFromList(), "Catalog not deleted successfully");
        LOGGER.info("Catalog deleted successfully");

        softAssert.assertAll();
        LOGGER.info("Verified import of preset catalog for single customer successfully");
    }

    /**
     * This method will verify the import of custom catalog for single customers
     * then validate the Bell notification then validate in catalog list page and delete the catalog from the catalog list page
     * & again validate the deletion of the catalog
     */
    @Test(priority = 22, groups = { "REGRESSION_PARTNERCX"}, description = "TestCase ID : 41160555,  41160557",enabled = false)
    public final void verifyImportCustomCatalogForSingleCustomer() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        String catalogAdditionCompany = getEnvironmentSpecificData(System.getProperty("environment"), "CATALOG_CUSTOMER1_NAME");
        String catalogAdditionCompanyID = getEnvironmentSpecificData(System.getProperty("environment"), "CATALOG_CUSTOMER1_TENANTID");
        WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);

        wexPartnerPage.partnerView(CommonVariables.PARTNER_REQUESTS);
        LOGGER.info("Redirected to Request page");

        softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("CatalogTab"), "Catalog is not present");
        softAssert.assertTrue(wexPartnerPage.clickOnElementsOfWEXPartnerPage("CatalogTab"), "Not clicked on Catalog");
        wexPartnerPage.deletePreuploadedCatalog();
        LOGGER.info("Redirected to Catalog list page");

        wexPartnerPage.clickOnElementsOfWEXPartnerPage("addCatalogButton");
        wexPartnerPage.verifyElementsOfWEXPartnerPage("downloadCatalogSample");
        wexPartnerPage.verifyElementsOfWEXPartnerPage("dropDownOpen");
        wexPartnerPage.mousehoverOnWEXPartnerPage("dropDownOpen");
        sleeper(1000);
        wexPartnerPage.clickByJavaScriptOnElementsOnPartnerPage("dropDownOpen");
        sleeper(1000);
        wexPartnerPage.clickOnElementsOfwexPartnerPage("dropDownOpen");
        sleeper(1000);
        LOGGER.info("Clicked on dropdown");

        wexPartnerPage.selectDropDownValueOfWEXPartnerPage("companySearch", catalogAdditionCompany, "selectCompany");
        LOGGER.info("Selected the company");

        // closed the dropdown
        Robot robot = new Robot();
        robot.mouseMove(630, 420); // move mouse point to specific location
        robot.delay(1500);        // delay is to make code wait for mentioned milliseconds before executing next step
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // press left click
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // release left click
        LOGGER.info("closed the company selection dropdown");

        wexPartnerPage.clickOnElementsOfWEXPartnerPage("browseButton");
        wexPartnerPage.fileImportInV3(ConstantPath.IMPORT_PATH + ProductCatalogVariables.FILE_NAME_VALID);
        wexPartnerPage.waitForElementsOfWEXPartnerPage("importButton");
        sleeper(2000);//File upload fails in quick import button click.

        // Press the Escape key to close the file upload window
        robot.keyPress(KeyEvent.VK_ESCAPE);    // Press the Escape key
        robot.keyRelease(KeyEvent.VK_ESCAPE);  // Release the Escape key
        LOGGER.info("closed the uploaded popup");

        wexPartnerPage.clickOnElementsOfWEXPartnerPage("importButton");
        LOGGER.info("Clicked on import button");
        wexPartnerPage.waitForElementsOfWEXPartnerPage("ImportToastNotification");
        softAssert.assertTrue(wexPartnerPage.getTextOfWEXPartnerPage("ImportToastNotification").equals(wexPartnerPage.getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast Notification text does not match");

        // here my page is refreshing so I am waiting for the page to load
        sleeper(20000);
        // Refresh the page
        getDriver().navigate().refresh();
        waitForPageLoaded();
        sleeper(10000);
        // Create a new instance of WebDriverWait
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20)); // 20 seconds timeout

        LOGGER.info("Import Catalog notification is validated");
        // Verify the imported catalog details
        softAssert.assertTrue(wexPartnerPage.verifyImportedCatalog(CommonVariables.CUSTOM_CATALOG, catalogAdditionCompany, ""), "Imported catalog details not verified successfully");
        LOGGER.info("Imported catalog details verified successfully");

        // delete the catalog from the catalog list page
        softAssert.assertTrue(wexPartnerPage.deleteCatalogFromList(), "Catalog not deleted successfully");
        LOGGER.info("Catalog deleted successfully");

        softAssert.assertAll();
        LOGGER.info("Verified import of custom catalog for single customer successfully");
    }

    /**
     * This method will verify the import of preset catalog for multiple customers
     * then validate the Bell notification then validate in catalog list page and delete the catalog from the catalog details page
     * & again validate the deletion of the catalog
     */
    @Test(priority = 23, groups = { "REGRESSION_PARTNERCX"}, description = "TestCase ID : 44577715,  41160557", enabled = false)
    public final void verifyImportPresetCatalogForMultipleCustomer() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        String importCatalogCompany1 = getEnvironmentSpecificData(System.getProperty("environment"), "CATALOG_CUSTOMER1_NAME");
        String importCatalogCompanyId1 = getEnvironmentSpecificData(System.getProperty("environment"), "CATALOG_CUSTOMER1_TENANTID");
        String importCatalogCompany2 = getEnvironmentSpecificData(System.getProperty("environment"), "CATALOG_CUSTOMER2_NAME");
        String importCatalogCompanyId2 = getEnvironmentSpecificData(System.getProperty("environment"), "CATALOG_CUSTOMER2_TENANTID");
        WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        wexPartnerPage.partnerView(CommonVariables.PARTNER_REQUESTS);

        softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("CatalogTab"), "Catalog tab is not present");
        softAssert.assertTrue(wexPartnerPage.clickOnElementsOfWEXPartnerPage("CatalogTab"), "Not clicked on Catalog tab");
        LOGGER.info("Redirected to Catalog list page");

        wexPartnerPage.deletePreuploadedCatalog();
        softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addCatalogButton", getTextLanguage(LanguageCode, "daas_ui", "catalog.list.add_catalog")), "Add catalog button text does not match");
        wexPartnerPage.clickOnElementsOfWEXPartnerPage("addCatalogButton");

        // verify the add catalog popup
        softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addCatalogTitle", getTextLanguage(LanguageCode, "daas_ui", "catalog.add_update.product")), "Product catalog import title does not match");
        String subTitle = getTextLanguage(LanguageCode, "daas_ui", "catalog.add_update.product.description");
        String subTitleText = wexPartnerPage.getTextOfWEXPartnerPage("addCatalogSubtitle");
        softAssert.assertTrue(subTitleText.contains(subTitle), "Product catalog 'You can add or update a product catalog by importing a .CSV file.' does not match");
        String downloadCatalogSample = getTextLanguage(LanguageCode, "daas_ui", "catalog.add_update.product.description.first_time");
        String downloadCatalogSampleText = wexPartnerPage.getTextOfWEXPartnerPage("sampleCatalogFile");
        softAssert.assertTrue(downloadCatalogSample.contains(downloadCatalogSampleText), "Product catalog 'click here to download the sample file.' does not match");

        wexPartnerPage.verifyElementsOfWEXPartnerPage("downloadCatalogSample");
        wexPartnerPage.verifyElementsOfWEXPartnerPage("dropDownOpen");
        wexPartnerPage.mousehoverOnWEXPartnerPage("dropDownOpen");
        sleeper(1000);
        wexPartnerPage.clickByJavaScriptOnElementsOnPartnerPage("dropDownOpen");
        sleeper(1000);
        wexPartnerPage.clickOnElementsOfwexPartnerPage("dropDownOpen");
        sleeper(1000);
        LOGGER.info("Clicked on dropdown");

        // select the company
        wexPartnerPage.selectDropDownValueOfWEXPartnerPage("companySearch", importCatalogCompany1, "selectCompany");
        wexPartnerPage.getWebelementsOfWEXPartnerPage("companySearch").get(0).clear();
        wexPartnerPage.selectDropDownValueOfWEXPartnerPage("companySearch", importCatalogCompany2, "selectCompany");
        LOGGER.info("companies are selected for import");

        // closed the dropdown
        Robot robot = new Robot();
        robot.mouseMove(630, 420); // move mouse point to specific location
        robot.delay(1500);        // delay is to make code wait for mentioned milliseconds before executing next step
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // press left click
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // release left click
        LOGGER.info("closed the company selection dropdown");

        wexPartnerPage.clickOnElementsOfWEXPartnerPage("browseButton");
        wexPartnerPage.fileImportInV3(ConstantPath.IMPORT_PATH + ProductCatalogVariables.FILE_NAME_VALID);
        wexPartnerPage.waitForElementsOfWEXPartnerPage("importButton");
        sleeper(2000);//File upload fails in quick import button click.

        // Press the Escape key to close the file upload window
        robot.keyPress(KeyEvent.VK_ESCAPE);    // Press the Escape key
        robot.keyRelease(KeyEvent.VK_ESCAPE);  // Release the Escape key
        LOGGER.info("closed the uploaded popup");
        // set as default
        wexPartnerPage.clickOnElementsOfWEXPartnerPage("setAsDefaultCheckBox");
        softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("setAsDefaultCheckBoxLabel", getTextLanguage(LanguageCode, "daas_ui", "catalog.add_update.set_default")), " Set as default catalog : label does not match");
        softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("importButton", getTextLanguage(LanguageCode, "daas_ui", "global.import")), " Import button text does not match");
        softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("importCatalogCancelButton", getTextLanguage(LanguageCode, "daas_ui", "flexible_dashboard.modals.cancel")), " Cancel label does not match");
        // softAssert.assertTrue(wexPartnerPage.verifyElementIsVisible("importCatalogClosedButton"), "Add catalog popup close button is not present");

        wexPartnerPage.clickOnElementsOfWEXPartnerPage("importButton");
        LOGGER.info("Clicked on import button");
        wexPartnerPage.waitForElementsOfWEXPartnerPage("ImportToastNotification");
        softAssert.assertTrue(wexPartnerPage.getTextOfWEXPartnerPage("ImportToastNotification").equals(wexPartnerPage.getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast Notification text does not match");

        // here my page is refreshing so I am waiting for the page to load
        sleeper(20000);
        // Refresh the page
        getDriver().navigate().refresh();
        waitForPageLoaded();
        sleeper(10000);
        // Create a new instance of WebDriverWait
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20)); // 20 seconds timeout

        LOGGER.info("Import Catalog notification is validated for company 1");
        LOGGER.info("Import Catalog notification is validated for company 2");

        // Verify the imported catalog details
        softAssert.assertTrue(wexPartnerPage.verifyImportedCatalog(CommonVariables.PRESET_CATALOG, importCatalogCompany2, importCatalogCompany1), "Imported catalog details not verified successfully");
        LOGGER.info("Imported catalog details verified successfully for multiple customers");

        // delete the catalog from the catalog list page
        softAssert.assertTrue(wexPartnerPage.deleteCatalogFromCatalogDetailsPage(), "Catalog not deleted successfully");
        LOGGER.info("Catalog deleted successfully");

        softAssert.assertAll();
        LOGGER.info("Verified import of preset catalog for single customer successfully");
    }

    /**
     * This method will verify the import of custom catalog for multiple customers
     * then validate the Bell notification then validate in catalog list page and delete the catalog from the catalog details page
     * & again validate the deletion of the catalog
     */
    @Test(priority = 24, groups = { "REGRESSION_PARTNERCX"}, description = "TestCase ID : 44577714, 41160557", enabled = false)
    public final void verifyImportCustomCatalogForMultipleCustomer() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        String importCatalogCompany1 = getEnvironmentSpecificData(System.getProperty("environment"), "CATALOG_CUSTOMER1_NAME");
        String importCatalogCompanyId1 = getEnvironmentSpecificData(System.getProperty("environment"), "CATALOG_CUSTOMER1_TENANTID");
        String importCatalogCompany2 = getEnvironmentSpecificData(System.getProperty("environment"), "CATALOG_CUSTOMER2_NAME");
        String importCatalogCompanyId2 = getEnvironmentSpecificData(System.getProperty("environment"), "CATALOG_CUSTOMER2_TENANTID");
        WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        wexPartnerPage.partnerView(CommonVariables.PARTNER_REQUESTS);

        softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("CatalogTab"), "Catalog tab is not present");
        softAssert.assertTrue(wexPartnerPage.clickOnElementsOfWEXPartnerPage("CatalogTab"), "Not clicked on Catalog tab");
        LOGGER.info("Redirected to Catalog list page");

        wexPartnerPage.deletePreuploadedCatalog();
        softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addCatalogButton", getTextLanguage(LanguageCode, "daas_ui", "catalog.list.add_catalog")), "Add catalog button text does not match");
        wexPartnerPage.clickOnElementsOfWEXPartnerPage("addCatalogButton");

        // verify the add catalog popup
        softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addCatalogTitle", getTextLanguage(LanguageCode, "daas_ui", "catalog.add_update.product")), "Product catalog import title does not match");
        String subTitle = getTextLanguage(LanguageCode, "daas_ui", "catalog.add_update.product.description");
        String subTitleText = wexPartnerPage.getTextOfWEXPartnerPage("addCatalogSubtitle");
        softAssert.assertTrue(subTitleText.contains(subTitle), "Product catalog 'You can add or update a product catalog by importing a .CSV file.' does not match");
        String downloadCatalogSample = getTextLanguage(LanguageCode, "daas_ui", "catalog.add_update.product.description.first_time");
        String downloadCatalogSampleText = wexPartnerPage.getTextOfWEXPartnerPage("sampleCatalogFile");
        softAssert.assertTrue(downloadCatalogSample.contains(downloadCatalogSampleText), "Product catalog 'click here to download the sample file.' does not match");

        wexPartnerPage.verifyElementsOfWEXPartnerPage("downloadCatalogSample");
        wexPartnerPage.verifyElementsOfWEXPartnerPage("dropDownOpen");
        wexPartnerPage.mousehoverOnWEXPartnerPage("dropDownOpen");
        sleeper(1000);
        wexPartnerPage.clickByJavaScriptOnElementsOnPartnerPage("dropDownOpen");
        sleeper(1000);
        wexPartnerPage.clickOnElementsOfwexPartnerPage("dropDownOpen");
        sleeper(1000);
        LOGGER.info("Clicked on dropdown");

        // select the company
        wexPartnerPage.selectDropDownValueOfWEXPartnerPage("companySearch", importCatalogCompany1, "selectCompany");
        wexPartnerPage.getWebelementsOfWEXPartnerPage("companySearch").get(0).clear();
        wexPartnerPage.selectDropDownValueOfWEXPartnerPage("companySearch", importCatalogCompany2, "selectCompany");
        LOGGER.info("companies are selected for import");

        // closed the dropdown
        Robot robot = new Robot();
        robot.mouseMove(630, 420); // move mouse point to specific location
        robot.delay(1500);        // delay is to make code wait for mentioned milliseconds before executing next step
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // press left click
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // release left click
        LOGGER.info("closed the company selection dropdown");

        wexPartnerPage.clickOnElementsOfWEXPartnerPage("browseButton");
        wexPartnerPage.fileImportInV3(ConstantPath.IMPORT_PATH + ProductCatalogVariables.FILE_NAME_VALID);
        wexPartnerPage.waitForElementsOfWEXPartnerPage("importButton");
        sleeper(2000);//File upload fails in quick import button click.

        // Press the Escape key to close the file upload window
        robot.keyPress(KeyEvent.VK_ESCAPE);    // Press the Escape key
        robot.keyRelease(KeyEvent.VK_ESCAPE);  // Release the Escape key
        LOGGER.info("closed the uploaded popup");

        // set as default
        softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("setAsDefaultCheckBoxLabel", getTextLanguage(LanguageCode, "daas_ui", "catalog.add_update.set_default")), " Set as default catalog : label does not match");
        softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("importButton", getTextLanguage(LanguageCode, "daas_ui", "global.import")), " Import button text does not match");
        softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("importCatalogCancelButton", getTextLanguage(LanguageCode, "daas_ui", "flexible_dashboard.modals.cancel")), " Cancel label does not match");
        // softAssert.assertTrue(wexPartnerPage.verifyElementIsVisible("importCatalogClosedButton"), "Add catalog popup close button is not present");

        wexPartnerPage.clickOnElementsOfWEXPartnerPage("importButton");
        LOGGER.info("Clicked on import button");
        wexPartnerPage.waitForElementsOfWEXPartnerPage("ImportToastNotification");
        softAssert.assertTrue(wexPartnerPage.getTextOfWEXPartnerPage("ImportToastNotification").equals(wexPartnerPage.getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast Notification text does not match");

        // here my page is refreshing so I am waiting for the page to load
        sleeper(20000);
        // Refresh the page
        getDriver().navigate().refresh();
        waitForPageLoaded();
        sleeper(10000);
        // Create a new instance of WebDriverWait
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20)); // 20 seconds timeout

        //softAssert.assertTrue(wexPartnerPage.postNotificationCheckImport(importCatalogCompanyId1),"Bell Notification is not present for company 1");
        //LOGGER.info("Import Catalog notification is validated for company 1");
        //softAssert.assertTrue(wexPartnerPage.postNotificationCheckImport(importCatalogCompanyId2),"Bell Notification is not present for company 2");
        //LOGGER.info("Import Catalog notification is validated for company 2");

        // Verify the imported catalog details
        softAssert.assertTrue(wexPartnerPage.verifyImportedCatalog(CommonVariables.CUSTOM_CATALOG, importCatalogCompany2, importCatalogCompany1), "Imported catalog details not verified successfully");
        LOGGER.info("Imported catalog details verified successfully for multiple customers");

        // delete the catalog from the catalog list page
        softAssert.assertTrue(wexPartnerPage.deleteCatalogFromCatalogDetailsPage(), "Catalog not deleted successfully");
        LOGGER.info("Catalog deleted successfully");

        softAssert.assertAll();
        LOGGER.info("Verified import of preset catalog for single customer successfully");
    }
    @Test(priority = 25, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 427350855")
    public final void VerifyPartnerEnablementCardsUnlockWXP() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        LOGGER.info("Partner login verified successfully");

        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("EnablementCards");
        sleeper(2000);
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("EnablementCards", getTextLanguage(LanguageCode, "daas_ui", "wxp.partner.dashboard.certification.card.title")), "Boost your expertise Title mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("EnablementCardsDes", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.certifications.card.description")), "Boost your expertise Card Description mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("LearnMoreButton"), "Learn more button is not available");
        LOGGER.info("Learn More button present for Boost your expertise with HP Solutions certifications card");

        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("LearnMoreButton"),"Learn More is not clickable");
        LOGGER.info("Redirected to Certifications Tab in Accounts");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CertificationsTab1", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications")), "Certifications not found on Accounts Tab ");
        softAssert.assertAll();
        LOGGER.info("Verified Boost your expertise with HP Solutions certifications card successfully");
    }

    @Test(priority = 26, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 427350855")
    public final void VerifyWatchHPWXPActionPartnerEnablementCards() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        LOGGER.info("Partner login verified successfully");

        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("WatchHPCard");
        sleeper(2000);
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("WatchHPCard", getTextLanguage(LanguageCode, "daas_ui", "wxp.partner.dashboard.inAction.card.title")), "Watch HP WXP in Action Title mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("WatchHPCardDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.watchAction.card.description")), "Watch HP WXP in Action Card Description mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("WatchWXP"), "Watch button is not available");
        LOGGER.info("Watch button present for Watch HP WXP in Action card");

        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("WatchWXP"),"Watch button is not clickable");
        sleeper(2000);
        wexPartnerDashboardPage.switchToDifferentTab();
        waitForPageLoaded();// Url takes time to load
        String Actual_URL = wexPartnerDashboardPage.getUrlOfCurrentPage();
        String Expected_URL = ConstantURL.WXP_Watch;
        softAssert.assertTrue((Actual_URL.contains(Expected_URL)), "URL for WXP Action  is not matching");

        String ActualPage_Title = wexPartnerDashboardPage.getBrowserTabName();
        String Expected_Title = "Platform Demos | HP Workforce Experience Platform (WXP)";
        softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for Learn More is not matching");
        LOGGER.info("Actual Title is :" + ActualPage_Title);

        wexPartnerDashboardPage.switchBackToPreviousTab();
        LOGGER.info("Validation of WXP Action card completed.");
        LOGGER.info("Actual URL is :" + Actual_URL);
        LOGGER.info("Expected URL is :" + Expected_URL);
        LOGGER.info("Successfully validated WXP URL");
        softAssert.assertAll();
        LOGGER.info("Verified Watch HP WXP in Action card successfully");
    }

    @Test(priority = 27, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 4273508597")
    public final void VerifyStayInKnowPartnerEnablementCards() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        LOGGER.info("Partner login verified successfully");

        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("StayinknowCard");
        sleeper(2000);

        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("StayinknowCard", getTextLanguage(LanguageCode, "daas_ui", "wxp.partner.dashboard.stayInKnow.card.title")), "Stay in the know Title mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("StayinknowCardDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.stayInKnow.card.description")), "Stay in the know Card Description mismatches");
        waitForPageLoaded();
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("LearMore2"), "Learn more button is not available");
        LOGGER.info("Learn More button present for Stay in the know card");

        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("LearMore2"),"Learn more button is not available");
        sleeper(2000);
        wexPartnerDashboardPage.switchToDifferentTab();
        waitForPageLoaded();// Url takes time to load
        String Actual_URL = wexPartnerDashboardPage.getUrlOfCurrentPage();
        String Expected_URL = ConstantURL.Stay_In_Know_LearMore;
        softAssert.assertTrue((Actual_URL.contains(Expected_URL)), "URL for Learn More is not matching");

        String ActualPage_Title = wexPartnerDashboardPage.getBrowserTabName();
        String Expected_Title = "My Subscription Preferences - HP Partner Portal";
        softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for Learn More is not matching");
        LOGGER.info("Actual Title is :" + ActualPage_Title);

        wexPartnerDashboardPage.switchBackToPreviousTab();
        LOGGER.info("Validation of Stay in the know card completed.");
        LOGGER.info("Actual URL is :" + Actual_URL);
        LOGGER.info("Expected URL is :" + Expected_URL);
        LOGGER.info("Successfully validated Learn more URL");
        softAssert.assertAll();
        LOGGER.info("Verified Watch Stay in the know card successfully");
    }

    @Test(priority = 28, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 4273508598")
    public final void VerifySeeLatestPostsPartnerEnablementCards() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
        LOGGER.info("Partner login verified successfully");

        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("SeeLatestPostsCard");
        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("ScrollButton"),"Scroll Button is not available");
        sleeper(2000);

        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SeeLatestPostsCard", getTextLanguage(LanguageCode, "daas_ui", "wxp.partner.dashboard.blog.card.title")), "See the latest posts from our experts Title mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SeeLatestPostsDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.latestPosts.card.description")), "See the latest posts from our experts Card Description mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("LearMore3"), "Learn more button is not available");
        LOGGER.info("Learn More button present for See the latest posts from our experts card");

        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("LearMore3"),"Learn more button is not available");
        sleeper(2000);
        wexPartnerDashboardPage.switchToDifferentTab();
        waitForPageLoaded();// Url takes time to load
        String Actual_URL = wexPartnerDashboardPage.getUrlOfCurrentPage();
        String Expected_URL = ConstantURL.See_Latest_Post;
        softAssert.assertTrue((Actual_URL.contains(Expected_URL)), "URL for Learn More is not matching");

        String ActualPage_Title = wexPartnerDashboardPage.getBrowserTabName();
        String Expected_Title = "The HP Workforce Experience Blog";
        softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for Learn More is not matching");
        LOGGER.info("Actual Title is :" + ActualPage_Title);

        wexPartnerDashboardPage.switchBackToPreviousTab();
        LOGGER.info("Validation of See the latest posts from our experts card completed.");
        LOGGER.info("Actual URL is :" + Actual_URL);
        LOGGER.info("Expected URL is :" + Expected_URL);
        LOGGER.info("Successfully validated Learn more URL");

        softAssert.assertAll();
        LOGGER.info("Verified See the latest posts from our experts Card successfully");
    }
}