package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class WXPPartnerAnalyticsTest extends CommonTest {

    private static final Logger LOGGER = LogManager.getLogger(com.testscripts.daasui.WEPPartnerCustomersTest.class);
    public static String environment;


    /**
     * This test case verifies the analytics list page for partner admin.
     * It logs in with the given credentials, verifies the left side menu,
     * and checks the analytics page title and tabs.
     */
    @Test(priority = 1, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0001")
    public final void verifyPartnerAnalyticsPageTabs() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        wexPartnerPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW, CommonVariables.PARTNER_CUSTOMERS);
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FleetAnalyticsTab", getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab text is not matching");
        waitForPageLoaded();
        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("FleetAnalyticsTab"), "Fleet Analytics tab is not clickable");
        waitForLoaderIconToDisappear();
        LOGGER.info("Analytics page opened correctly");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("modernReports", getTextLanguage(LanguageCode, "daas_ui", "analytics.reports.wxp")), " Modern Reports tab text is not matching");
        waitForPageLoaded();
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("classicReports", getTextLanguage(LanguageCode, "daas_ui", "analytics.reports.classic")), " Classic Reports tab text is not matching");
        waitForPageLoaded();
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("businessInsights", getTextLanguage(LanguageCode, "daas_ui", "partner.analytics.bir.label")), " Business Insights tab text is not matching");
        waitForPageLoaded();
        LOGGER.info("Reports tabs verified correctly");
    }

    /**
     * Win11 report
     *
     * @throws Exception
     */
    @Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0002")
    public final void verifyWin11ReadinessReportV2() throws Exception {
        SoftAssert sa = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        WXPPartnerAnalyticsPage partnerAnalyticsPage = new WXPPartnerAnalyticsPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        wexPartnerPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW, CommonVariables.PARTNER_CUSTOMERS);
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FleetAnalyticsTab", getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab text is not matching");
        waitForPageLoaded();
        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("FleetAnalyticsTab"), "Fleet Analytics tab is not clickable");
        waitForLoaderIconToDisappear();
        partnerAnalyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
        waitForPageLoaded();

        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("selectPageSize");
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("size100");
        waitForPageLoaded();
        //Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("totalReports", "of 22"), " totalReports count  is not matching");

        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("win11");
        partnerAnalyticsPage.waitForElementsOfAnalyticsPage("modalCompanyName");
        if (partnerAnalyticsPage.verifyElementsOfPartnerAnalyticsPage("modalViewButton")) {
            partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("modalViewButton");
            boolean isExportButtonPresent = partnerAnalyticsPage.verifyElementIsVisibleOfAnalyticsPage("exportButton");
            if (isExportButtonPresent) {
                Assert.assertTrue(partnerAnalyticsPage.verifyExportFunctionality(LanguageCode, "toastNotificationExport"), "Export functionality is not working on Win11 Readiness Report details page.");
                LOGGER.info("Export functionality validation for Win11 Readiness Report has been completed successfully.");
            } else {
                LOGGER.warn("Export button is not present on Win11 Readiness Report.");
            }
            String[] columnNameKeys = {"HWWin11ReadinessDetails"};
            String[] reportTabs = new String[]{"HWWin11ReadinessDetails.Summary", "HWWin11ReadinessDetails.Details"};
            sa.assertTrue(
                    partnerAnalyticsPage.reportDataValidation(partnerAnalyticsPage.getReportTabDetails(reportTabs), columnNameKeys,
                            LanguageCode, "daas_reports_ui"),
                    "The values in Grid or graph did not matched or did not get load");
        } else {
            LOGGER.warn("No data available for Win11 Readiness Report");
        }
        sa.assertAll();
    }

    /**
     * Hardware Inventory Report
     *
     * @throws Exception
     */
    @Test(priority = 3, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0003")
    public final void createHardwareInventoryDetailsReport() throws Exception {
        SoftAssert sa = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WXPPartnerAnalyticsPage partnerAnalyticsPage = new WXPPartnerAnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        sa.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        wexPartnerPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW, CommonVariables.PARTNER_CUSTOMERS);
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FleetAnalyticsTab", getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab text is not matching");
        waitForPageLoaded();
        sa.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("FleetAnalyticsTab"), "Fleet Analytics tab is not clickable");
        waitForLoaderIconToDisappear();
        waitForPageLoaded();
        partnerAnalyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
        waitForPageLoaded();
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("selectPageSize");
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("size100");
        waitForPageLoaded();
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("hwInventory");
        partnerAnalyticsPage.waitForElementsOfAnalyticsPage("modalCompanyName");
        if (partnerAnalyticsPage.verifyElementsOfPartnerAnalyticsPage("modalViewButton")) {
            partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("modalViewButton");
            boolean isExportButtonPresent = partnerAnalyticsPage.verifyElementIsVisibleOfAnalyticsPage("exportButton");
            if (isExportButtonPresent) {
                Assert.assertTrue(partnerAnalyticsPage.verifyExportFunctionality(LanguageCode, "toastNotificationExport"), "Export functionality is not working on Hardware Inventory Details Page.");
                LOGGER.info("Export functionality validation for Hardware Inventory Details Page has been completed successfully.");
            } else {
                LOGGER.warn("Export button is not present on Hardware Inventory Details Page.");
            }
            String[] columnNameKeys = {"HWInventoryDetails"};
            String[] reportTabs = new String[]{"HWInventoryDetails.ByLocation",
                    "HWInventoryDetails.ByDeviceCongiguration", "HWInventoryDetails.ByDeviceModel", "HWInventoryDetails.ByOperatingSystem",
                    "HWInventoryDetails.Details"};
            sa.assertTrue(
                    partnerAnalyticsPage.reportDataValidation(partnerAnalyticsPage.getReportTabDetails(reportTabs), columnNameKeys,
                            LanguageCode, "daas_reports_ui"),
                    "The values in Grid or graph did not matched or did not get load");
        } else {
            LOGGER.warn("No data available for Hardware Inventory Report");
        }
        sa.assertAll();
    }

    /**
     * BIOS Inventory Report
     *
     * @throws Exception
     */
    @Test(priority = 4, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0004")
    public final void createHardwareBIOSInventoryReport() throws Exception {
        SoftAssert sa = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WXPPartnerAnalyticsPage partnerAnalyticsPage = new WXPPartnerAnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        sa.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        wexPartnerPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW, CommonVariables.PARTNER_CUSTOMERS);
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FleetAnalyticsTab", getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab text is not matching");
        waitForPageLoaded();
        sa.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("FleetAnalyticsTab"), "Fleet Analytics tab is not clickable");
        waitForLoaderIconToDisappear();
        waitForPageLoaded();
        partnerAnalyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
        waitForPageLoaded();
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("selectPageSize");
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("size100");
        waitForPageLoaded();
        // Click on BIOS Inventory
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("biosInventory");
        partnerAnalyticsPage.waitForElementsOfAnalyticsPage("modalCompanyName");
        if (partnerAnalyticsPage.verifyElementsOfPartnerAnalyticsPage("modalViewButton")) {
            partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("modalViewButton");
            boolean isExportButtonPresent = partnerAnalyticsPage.verifyElementIsVisibleOfAnalyticsPage("exportButton");
            if (isExportButtonPresent) {
                Assert.assertTrue(partnerAnalyticsPage.verifyExportFunctionality(LanguageCode, "toastNotificationExport"), "Export functionality is not working on BSOD Insights Page.");
                LOGGER.info("Export functionality validation for Hardware Inventory Details Page has been completed successfully.");
            } else {
                LOGGER.warn("Export button is not present on Hardware Inventory Details Page.");
            }
            // Define column names and report tabs
            String[] columnNameKeys = {"HWbiosInventoryDetails"};
            String[] reportTabs = new String[]{"HWbiosInventoryDetails.Summary", "HWbiosInventoryDetails.UniqueBIOS",
                    "HWbiosInventoryDetails.ByWeek", "HWbiosInventoryDetails.Details"};
            // Validate report data
            sa.assertTrue(
                    partnerAnalyticsPage.reportDataValidation(partnerAnalyticsPage.getReportTabDetails(reportTabs), columnNameKeys,
                            LanguageCode, "daas_reports_ui"),
                    "The values in Grid or graph did not match or did not get loaded");
        } else {
            LOGGER.warn("No data available for Win11 Readiness Report");
        }
        LOGGER.info("Report data validation completed");
        // Assert all
        sa.assertAll();
    }

    /**
     * Test method to create a hardware blue screen error details report.
     */
    @Test(priority = 5, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0005")
    public final void createHardwareBlueScreenErrorDetailsReport() throws Exception {
        SoftAssert sa = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WXPPartnerAnalyticsPage partnerAnalyticsPage = new WXPPartnerAnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        sa.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        wexPartnerPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW, CommonVariables.PARTNER_CUSTOMERS);
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FleetAnalyticsTab", getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab text is not matching");
        waitForPageLoaded();
        sa.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("FleetAnalyticsTab"), "Fleet Analytics tab is not clickable");
        waitForLoaderIconToDisappear();
        waitForPageLoaded();
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("selectPageSize");
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("size100");
        waitForPageLoaded();
        // Click on Blue Screen Errors
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("blueScreenErrors");
        LOGGER.info("Clicked on Blue Screen Errors");
        partnerAnalyticsPage.waitForElementsOfAnalyticsPage("modalCompanyName");
        if (partnerAnalyticsPage.verifyElementsOfPartnerAnalyticsPage("modalViewButton")) {
            partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("modalViewButton");
            boolean isExportButtonPresent = partnerAnalyticsPage.verifyElementIsVisibleOfAnalyticsPage("exportButton");
            if (isExportButtonPresent) {
                Assert.assertTrue(partnerAnalyticsPage.verifyExportFunctionality(LanguageCode, "toastNotificationExport"), "Export functionality is not working on BSOD Insights Page.");
                LOGGER.info("Export functionality validation for Hardware Inventory Details Page has been completed successfully.");
            } else {
                LOGGER.warn("Export button is not present on Hardware Inventory Details Page.");
            }
            // Verify Blue Screen Insights Page
            sa.assertTrue(partnerAnalyticsPage.verifyElementsOfAnalyticsPage("insightsPageBSOD"), "Blue Screen Insights Page doesn't open");
            LOGGER.info("Verified Blue Screen Insights Page");
        } else {
            LOGGER.warn("No data available for Win11 Readiness Report");
        }
        // Assert all
        sa.assertAll();
    }

    /**
     * Device Utilization Report
     *
     * @throws Exception
     */

    @Test(priority = 6, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0006")
    public final void createHardwareDeviceutilDetailsReport() throws Exception {
        SoftAssert sa = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WXPPartnerAnalyticsPage partnerAnalyticsPage = new WXPPartnerAnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        sa.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        wexPartnerPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW, CommonVariables.PARTNER_CUSTOMERS);
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FleetAnalyticsTab", getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab text is not matching");
        waitForPageLoaded();
        sa.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("FleetAnalyticsTab"), "Fleet Analytics tab is not clickable");
        waitForLoaderIconToDisappear();
        waitForPageLoaded();
        partnerAnalyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
        waitForPageLoaded();
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("selectPageSize");
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("size100");
        waitForPageLoaded();
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("deviceUtilization");
        LOGGER.info("Clicked on Device Utilization report");
        partnerAnalyticsPage.waitForElementsOfAnalyticsPage("modalCompanyName");
        if (partnerAnalyticsPage.verifyElementsOfPartnerAnalyticsPage("modalViewButton")) {
            partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("modalViewButton");
            boolean isExportButtonPresent = partnerAnalyticsPage.verifyElementIsVisibleOfAnalyticsPage("exportButton");
            if (isExportButtonPresent) {
                Assert.assertTrue(partnerAnalyticsPage.verifyExportFunctionality(LanguageCode, "toastNotificationExport"), "Export functionality is not working on BSOD Insights Page.");
                LOGGER.info("Export functionality validation for Deviceutil Details Page has been completed successfully.");
            } else {
                LOGGER.warn("Export button is not present on Deviceutil Details Page.");
            }
            String[] columnNameKeys = {"HWDeviceutilDetails"};
            String[] reportTabs = new String[]{"HWDeviceutilDetails.Summary", "HWDeviceutilDetails.ManufactureYear",
                "HWDeviceutilDetails.SoftPerf", "HWDeviceutilDetails.SoftPerfMac", "HWDeviceutilDetails.Details"};
            sa.assertTrue(partnerAnalyticsPage.reportDataValidation(partnerAnalyticsPage.getReportTabDetails(reportTabs), columnNameKeys, LanguageCode, "daas_reports_ui"),
                "The values in Grid or graph did not matched or did not get load");
            LOGGER.info("Verified Deviceutil Details Report Page");
        } else {
            LOGGER.warn("No data available for Win11 Readiness Report");
        }
        sa.assertAll();
    }

    /**
     * Driver Inventory report
     * * report with outdated drivers information. This test creating new Driver
     * Inventory report on the reports page, Verifying data in the header section on
     * drill down report page, Verifying presence of data under the respective tabs,
     * Also Verifying grid columns displaying correctly
     *
     * @throws Exception
     */

    @Test(priority = 7, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0007")
    public final void createDriverInventoryReport() throws Exception {
        SoftAssert sa = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WXPPartnerAnalyticsPage partnerAnalyticsPage = new WXPPartnerAnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        sa.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        wexPartnerPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW, CommonVariables.PARTNER_CUSTOMERS);
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FleetAnalyticsTab", getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab text is not matching");
        waitForPageLoaded();
        sa.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("FleetAnalyticsTab"), "Fleet Analytics tab is not clickable");
        waitForLoaderIconToDisappear();
        waitForPageLoaded();
        partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("driverInventory");
        LOGGER.info("Clicked on Device Utilization report");
        partnerAnalyticsPage.waitForElementsOfAnalyticsPage("modalCompanyName");
        if (partnerAnalyticsPage.verifyElementsOfPartnerAnalyticsPage("modalViewButton")) {
            partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("modalViewButton");
            boolean isExportButtonPresent = partnerAnalyticsPage.verifyElementIsVisibleOfAnalyticsPage("exportButton");
            if (isExportButtonPresent) {
                Assert.assertTrue(partnerAnalyticsPage.verifyExportFunctionality(LanguageCode, "toastNotificationExport"), "Export functionality is not working on BSOD Insights Page.");
                LOGGER.info("Export functionality validation for DriverInventory Details Page has been completed successfully.");
            } else {
                LOGGER.warn("Export button is not present on DriverInventory Details Page.");
            }
            String[] columnNameKeys = {"SWDriverInventoryDriversTable", "SWDriverInventoryDetails"};
            String[] reportTabs = new String[]{"SWDriverInventory.Summary", "SWDriverInventory.Drivers",
                    "SWDriverInventory.DriversTable", "SWDriverInventory.Details"};

            //sa.assertTrue(
                    //partnerAnalyticsPage.reportDataValidation(partnerAnalyticsPage.getReportTabDetails(reportTabs), columnNameKeys,
                            //LanguageCode, "daas_reports_ui"),
                   // "The values in Grid or graph did not match or was not get loaded");
           // LOGGER.info("Verified Deviceutil Details Report Page");
        } else {
            LOGGER.warn("No data available for Win11 Readiness Report");
        }
        sa.assertAll();
    }

    /**
     * This test case verifies the Application error Report (Analytics)
     */
    @Test(priority = 8, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0008")
    public final void verifyApplicationExperienceReportAnalyticsListPage() throws Exception {
        SoftAssert sa = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WXPPartnerAnalyticsPage partnerAnalyticsPage = new WXPPartnerAnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        sa.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        wexPartnerPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW, CommonVariables.PARTNER_CUSTOMERS);
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FleetAnalyticsTab", getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab text is not matching");
        waitForPageLoaded();
        sa.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("FleetAnalyticsTab"), "Fleet Analytics tab is not clickable");
        waitForLoaderIconToDisappear();
        waitForPageLoaded();
        LOGGER.info("Analytics listing page opened correctly");
        sa.assertTrue(partnerAnalyticsPage.verifyElementsOfAnalyticsPage("ApplicationExperience"), "Application Error report missing.");
        partnerAnalyticsPage.clickOnElementsOfAnalyticsPage("ApplicationExperience");
        waitForPageLoaded();
        Thread.sleep(2000);
        partnerAnalyticsPage.waitForElementsOfAnalyticsPage("modalCompanyName");
        if (partnerAnalyticsPage.verifyElementsOfPartnerAnalyticsPage("modalViewButton")) {
            partnerAnalyticsPage.clickByJavaScriptOnAnalyticsPage("modalViewButton");
            boolean isExportButtonPresent = partnerAnalyticsPage.verifyElementIsVisibleOfAnalyticsPage("exportButton");
            if (isExportButtonPresent) {
                Assert.assertTrue(partnerAnalyticsPage.verifyExportFunctionality(LanguageCode, "toastNotificationExport"), "Export functionality is not working on BSOD Insights Page.");
                LOGGER.info("Export functionality validation for DriverInventory Details Page has been completed successfully.");
            } else {
                LOGGER.warn("Export button is not present on DriverInventory Details Page.");
            }
            sa.assertTrue(partnerAnalyticsPage.verifyElementsOfAnalyticsPage("applicationScore"), "Application score report missing.");
            sa.assertTrue(partnerAnalyticsPage.verifyElementsOfAnalyticsPage("appWithMostCrashes"), "Application with most crashes report missing.");
            sa.assertTrue(partnerAnalyticsPage.verifyElementsOfAnalyticsPage("appWithMostFreezes"), "Application with most freezes report missing.");
            sa.assertTrue(partnerAnalyticsPage.verifyElementsOfAnalyticsPage("crashesAndFreezesOverTime"), "crashes And Freezes OverTime freezes report missing.");
            LOGGER.info("Application Experience Report validation completed");
            partnerAnalyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
            waitForPageLoaded();
        } else {
            LOGGER.warn("No data available for Win11 Readiness Report");
        }
        sa.assertAll();

    }
}