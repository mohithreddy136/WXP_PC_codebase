package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CSVFileReader;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.DEXPage;
import com.daasui.pages.LogPage;
import com.daasui.pages.PreferencesPage;
import com.daasui.pages.WEPDeviceListPage;
import com.daasui.pages.WEXCustomerHomePage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.daasui.pages.AnalyticsPage;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DEXTest extends CommonTest {

    private static Logger LOGGER = LogManager.getLogger(DEXTest.class);

    @Test(priority = 1,enabled = true, groups = { "REGRESSION_SCORE", "PRODUCTION_LDK","INITECH_SOLUTIONS"},description = "Test Case: 41940207")
    public final void verifySystemhealthSummaryChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("scoreTitleSysHealth").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","exp.mgmt.details.hardwareScore")),"System Health Summary Chart title is incorrect.");
        Assert.assertTrue(dexPage.verifyElementsOfDEXPage("scoreChartData"),"System Health Summary Chart is not loading.");
        Assert.assertTrue(dexPage.verifyDDEXScoreCharts("sysHealthscoreChartBarsLocator"), "System Health Summary Chart is not working.");
        softAssert.assertAll();
    }


    @Test(priority = 2, enabled = true, groups = { "REGRESSION_SCORE" },description = "Test Case: 41940205")
    public final void verifySystemhealthByModelChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        dexPage.scrollToDEXPage("deviceModelChart");
        Assert.assertTrue(dexPage.verifyDDEXDeviceByModelChart("deviceByModelChartBarsLocator","deviceModelNoDataMessage","deviceModelChart",LanguageCode), "System Health Model Chart is not working");
    }

    @Test(priority = 3, enabled = true, groups = { "REGRESSION_SCORE", "REGRESSION_INTEGRATIONQA_CUJ"},description = "Test Case: 41940206")
    public final void verifySystemhealthIssuesChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        Assert.assertTrue(dexPage.verifyDDEXIssuesChart(LanguageCode), "System Health Issues Chart is not working.");
    }

    @Test(priority = 4, enabled = true, groups = { "REGRESSION_SCORE", "PRODUCTION_LDK","INITECH_SOLUTIONS" }, description = "Test Case: 41940207")
    public final void verifyosSystemReliabilitySummaryChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("scoreTitleOSPerf").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","exp.mgmt.details.performanceScore")),"System Health Summary Chart title is incorrect.");
        Assert.assertTrue(dexPage.verifyElementsOfDEXPage("scoreChartData"),"System Health Summary Chart is not loading.");
        Assert.assertTrue(dexPage.verifyDDEXScoreCharts("osPerfscoreChartBarsLocator"), "System Health Summary Chart is not working.");
        softAssert.assertAll();
    }


    @Test(priority = 5, enabled = true, groups = { "REGRESSION_SCORE" },description = "Test Case: 41940205")
    public final void verifyOsSystemReliabilityByModelChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        dexPage.scrollToDEXPage("deviceModelChartPerf");
        Assert.assertTrue(dexPage.verifyDDEXDeviceByModelChart("deviceByModelChartBarsLocator","deviceModelPerfNoDataMessage","deviceModelChartPerf",LanguageCode), "HosSystemReliability Model Chart is not working.");
    }

    @Test(priority = 6, enabled = true, groups = { "REGRESSION_SCORE", "REGRESSION_INTEGRATIONQA_CUJ"},description = "Test Case: 41940206")
    public final void verifyOsSystemReliabilityIssuesChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        Assert.assertTrue(dexPage.verifyDDEXIssuesChart(LanguageCode), "osSystemReliability Top Issues Chart is not working.");
    }

    @Test(priority = 7, enabled = true, groups = { "REGRESSION_SCORE", "PRODUCTION_LDK","INITECH_SOLUTIONS" },description = "Test Case: 41940207")
    public final void verifySecurityhealthSummaryChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("scoreTitleSecurity").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","exp.mgmt.details.securityScore")),"System Health Summary Chart title is incorrect.");
        Assert.assertTrue(dexPage.verifyElementsOfDEXPage("scoreChartData"),"Security Score Chart is not loading.");
        Assert.assertTrue(dexPage.verifyDDEXScoreCharts("securityBarsLocator"), "Security Score Chart is not working.");
        softAssert.assertAll();
    }


    @Test(priority = 8, enabled = true, groups = { "REGRESSION_SCORE" },description = "Test Case: 41940205")
    public final void verifySecurityhealthByModelChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        dexPage.scrollToDEXPage("deviceModelChartSecurity");
        Assert.assertTrue(dexPage.verifyDDEXDeviceByModelChart("deviceByModelChartBarsLocator","deviceModelSecurityNoDataMessage","deviceModelChartSecurity",LanguageCode), "Security Health Model Chart is not working.");
    }

    @Test(priority = 9, enabled = true, groups = { "REGRESSION_SCORE", "REGRESSION_INTEGRATIONQA_CUJ"},description = "Test Case: 41940206")
    public final void verifySecurityhealthIssuesChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        Assert.assertTrue(dexPage.verifyDDEXIssuesChart(LanguageCode), "Security health Issues Chart is not working.");
    }

    @Test(priority = 10, enabled = false, groups = { "REGRESSION_SCORE" },description = "Test Case: 41124385")
    public final void verifyFiltersOnGridPage() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softassert =  new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyDEXDetailsNavigation(), "Navigation to DEX Details page is not working.");
        dexPage.scrollToDEXPage("serialNumberSearchBox");
        softassert.assertTrue(dexPage.verifySearchBoxFilterDDEX("serialNumberColumnValues","serialNumberSearchBox"), "Search box filter is not working.");
//        dexPage.clickOnElementsOfDEXPage("clearGridFilter");
//        dexPage.waitUntilElementIsInvisibleOfDEXPage("reactSkelaton");
//        //sleeper(3000);
//        softassert.assertTrue(dexPage.verifyMultiSelectDropdownFilterDDEX("warrantyColumnValues","warrantyDropdown","warrantyDropdownValues"), "Multidropdown box filter is not working.");
//        dexPage.clickOnElementsOfDEXPage("clearGridFilter");
//        dexPage.waitUntilElementIsInvisibleOfDEXPage("reactSkelaton");
//        softassert.assertTrue(dexPage.verifyComparisonRangeFilter("Less than"), "Comparison range filter is not working.");
//        dexPage.clickOnElementsOfDEXPage("clearGridFilter");
//        dexPage.waitUntilElementIsInvisibleOfDEXPage("reactSkelaton");
//        softassert.assertTrue(dexPage.verifyComparisonRangeFilter("Greater than"), "Comparison range filter is not working.");
//        dexPage.clickOnElementsOfDEXPage("clearGridFilter");
//        dexPage.waitUntilElementIsInvisibleOfDEXPage("reactSkelaton");
//        softassert.assertTrue(dexPage.verifyComparisonRangeFilter("Less than or equal to"), "Comparison range filter is not working.");
//        dexPage.clickOnElementsOfDEXPage("clearGridFilter");
//        dexPage.waitUntilElementIsInvisibleOfDEXPage("reactSkelaton");
//        softassert.assertTrue(dexPage.verifyComparisonRangeFilter("Greater than or equal to"), "Comparison range filter is not working.");
//        dexPage.clickOnElementsOfDEXPage("clearGridFilter");
        softassert.assertAll();
        LOGGER.info("Filter functionality on Experience Management Grid page has been validated successfully without any issues.");
    }


    @Test(priority = 11, enabled = false, groups = { "REGRESSION_SCORE"},description = "Test Case: 41124384")
    public final void verifyFiltersOnExpMgmtPage() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        softAssert.assertTrue(dexPage.verifydexRatingFilter(), "DEX Rating filter is not working.");
//        softAssert.assertTrue(dexPage.verifyfiltersExpMgmtPage("dexRatingFilterDropdown","dexRatingFilterDropdownValues","dexRatingFilterSelectedText"), "DEX Rating filter is not working.");
//        softAssert.assertTrue(dexPage.verifyfiltersExpMgmtPage("dexIssueFilterDropdown","dexIssueFilterDropdownValues","dexIssueFilterSelectedText"), "DEX Issues filter is not working.");
//        softAssert.assertTrue(dexPage.verifyfiltersExpMgmtPage("deviceModelFilterDropdown","deviceModelFilterDropdownValues","deviceModelFilterSelectedText"), "Device Model filter is not working.");
//        softAssert.assertTrue(dexPage.verifyfiltersExpMgmtPage("memoryFilterDropdown","memoryFilterDropdownValues","memoryFilterSelectedText"), "Memory filter is not working.");
//        softAssert.assertTrue(dexPage.verifyfiltersExpMgmtPage("diskTypeFilterDropdown","diskTypeFilterDropdownValues","diskTypeFilterSelectedText"), "Disk Type filter is not working.");
//        softAssert.assertTrue(dexPage.verifyfiltersExpMgmtPage("mfgYearFilterDropdown","mfgYearFilterDropdownValues","mfgYearFilterSelectedText"), "Manufacture Year filter is not working.");
//        softAssert.assertTrue(dexPage.verifyfiltersExpMgmtPage("bornOnYearFilterDropdown","bornOnYearFilterDropdownValues","bornOnYearFilterSelectedText"), "Born On Year filter is not working.");
//        softAssert.assertTrue(dexPage.verifyfiltersExpMgmtPage("warrantyFilterDropdown","warrantyFilterDropdownValues","warrantyFilterSelectedText"), "Warranty filter is not working.");
//        softAssert.assertTrue(dexPage.verifyfiltersExpMgmtPage("deviceOSMajorFilterDropdown","deviceOSMajorFilterDropdownValues","deviceOSMajorFilterSelectedText"), "OS major Version filter is not working.");
//        softAssert.assertTrue(dexPage.verifyfiltersExpMgmtPage("deviceOSMinorFilterDropdown","deviceOSMinorFilterDropdownValues","deviceOSMinorFilterSelectedText"), "OS Minor Version filter is not working.");
        softAssert.assertAll();
        LOGGER.info("Filter functionality validation has been completed successfully.");
    }


    @Test(priority = 12, enabled = true, groups = { "REGRESSION_SCORE"},description = "Test Case: 41940294,52301576")
    public final void verifyRecommendedActionsTile() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessage")) {
            dexPage.scrollToDEXPage("recommendedActionsContent");
            softAssert.assertTrue(dexPage.verifyRecommendedActionstile(LanguageCode), "DEX Rating filter is not working on HW Tab.");
            softAssert.assertTrue(dexPage.verifyViewDevicesButton(LanguageCode), "View Devices is not working on HW Tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in System Health Tab.");
        }
        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessagePerf")) {
            dexPage.scrollToDEXPage("recommendedActionsContent");
            softAssert.assertTrue(dexPage.verifyRecommendedActionstile(LanguageCode), "DEX Rating filter is not working on Perf Tab.");
            softAssert.assertTrue(dexPage.verifyViewDevicesButton(LanguageCode), "View Devices is not working on Perf Tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in OS Performance Tab.");
        }
        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessageSecurity")) {
            dexPage.scrollToDEXPage("recommendedActionsContent");
            softAssert.assertTrue(dexPage.verifyRecommendedActionstile(LanguageCode), "DEX Rating filter is not working on Security Tab.");
            softAssert.assertTrue(dexPage.verifyViewDevicesButton(LanguageCode), "View Devices is not working on HW Tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in Security Tab.");
        }
        softAssert.assertAll();
        LOGGER.info("Recommended Actions validation has been completed successfully.");
    }



    @Test(priority = 13, groups = { "REGRESSION_SCORE"},description = "Test Case: 43549367,41940342,41940255,41940263")
    public final void verifyDeviceExpMgmtDeviceDetails() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        dexPage.scrollToDEXPage("systemHealthOverTimetitle");
        Assert.assertTrue(dexPage.verifyDeviceDetailNavigation(LanguageCode),"Device Details page did not load from Exp Mgmt Page.");
        sleeper(5000);
        dexPage.scrollToDEXPage("healthandProtectionTabDeviceDetails");
        sleeper(3000);
        dexPage.clickOnElementsOfDEXPage("healthandProtectionTabDeviceDetails");
        waitForPageLoaded();
        softAssert.assertTrue((dexPage.getTextOfDEXPage("issueIndicatorsTitle").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","assets_details_issueindicators_issueindicators"))),"Issue Indicators title text is incorrect on Device Details Page.");
        softAssert.assertTrue((dexPage.getTextOfDEXPage("issueIndicatorsSubtitle").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","assets_details_issueindicators_warning"))),"Issue Indicators subtitle text is incorrect on Device Details Page.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("issueIndicatorColumn"),"Issue Indicator column is not present in Issue Indicators table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("valueColumn"),"Value column is not present in Issue Indicators table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("recValueColumn"),"Recommended Value column is not present in Issue Indicators table.");
        dexPage.scrollToDEXPage("issueResolvedLastWeekTitle");
        softAssert.assertTrue((dexPage.getTextOfDEXPage("issueResolvedLastWeekTitle").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","wex.resolved_issues").trim())),"Issue Resolved Since Last week title text is incorrect on Device Details Page.");
        softAssert.assertTrue((dexPage.getTextOfDEXPage("issueResolvedLastWeekSubtitle").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","assets_details_issueresolved_warning"))),"Issue Resolved Since Last week subtitle text is incorrect on Device Details Page.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("issueNameColumn"),"Issue name column is not present in Issue Indicators table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("valueLastWeekColumn"),"Value last week column is not present in Issue Resolved Since Last week table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("valueThisWeekColumn"),"Value this week column is not present in Issue Resolved Since Last week table.");
        dexPage.scrollToDEXPage("leftarrowDeviceDetails");
        dexPage.clickOnElementsOfDEXPage("leftarrowDeviceDetails");
        dexPage.clickOnElementsOfDEXPage("overviewTabDeviceDetails");
        waitForPageLoaded();
        softAssert.assertTrue((dexPage.waitForPresenceOfElementsOfDEXPage("applicationPrgressBarDeviceDetails")),"Application Score is not visible on Device Details Page.");
        softAssert.assertTrue((dexPage.waitForPresenceOfElementsOfDEXPage("overallScoreDeviceDetails")),"Overall Score is not visible on Device Details Page.");
        softAssert.assertTrue((dexPage.waitForPresenceOfElementsOfDEXPage("systemHealthScorePrgressBarDeviceDetails")),"System Health Score is not visible on Device Details Page.");
        softAssert.assertTrue((dexPage.waitForPresenceOfElementsOfDEXPage("osSystemReliabilityPrgressBarDeviceDetails")),"OS & System Reliability Score is not visible on Device Details Page.");
        softAssert.assertTrue((dexPage.waitForPresenceOfElementsOfDEXPage("SecurityPrgressBarDeviceDetails")),"Security Score is not visible on Device Details Page.");
        softAssert.assertTrue((dexPage.waitForPresenceOfElementsOfDEXPage("networkPrgressBarDeviceDetails")),"Network Score is not visible on Device Details Page.");
        softAssert.assertTrue((dexPage.waitForPresenceOfElementsOfDEXPage("webapplicationPrgressBarDeviceDetails")),"Web App Score is not visible on Device Details Page.");
        softAssert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("recommendedActionsDeviceDetails"),"Recommendation tab did not load in Device Details page.");
        if(dexPage.waitForElementsOfDEXPage("viewAllRAButton")) {
            dexPage.clickOnElementsOfDEXPage("viewAllRAButton");
            sleeper(2000);
            softAssert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("devicedetailsRAPage"), "Recommended Actions grid is not loading on Device Details Page.");
        }else{
            LOGGER.info("No Recommended Actions found for the selected device.");
        }
        softAssert.assertAll();
        LOGGER.info("Device Detail validation of DEX charts has been completed successfully.");
    }

    @Test(priority = 14, groups = { "REGRESSION_SCORE"},description = "Test Case: 41940288")
    public final void verifyDeviceExpMgmtExportFeature() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyDEXDetailsNavigation(), "Navigation to DEX Details page is not working.");
        Assert.assertTrue(dexPage.verifyExportFunctionality(LanguageCode,"toastNotificationExport"),"Export functionality is not working.");

        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyDEXDetailsNavigation(), "Navigation to DEX Details page is not working.");
        Assert.assertTrue(dexPage.verifyExportFunctionality(LanguageCode,"toastNotificationExport"),"Export functionality is not working.");

        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyDEXDetailsNavigation(), "Navigation to DEX Details page is not working.");
        Assert.assertTrue(dexPage.verifyExportFunctionality(LanguageCode,"toastNotificationExport"),"Export functionality is not working.");

        softAssert.assertAll();
        LOGGER.info("Export of DDEX functionality validation has been completed successfully.");
    }

    @Test(priority = 15, enabled = true, groups = { "REGRESSION_SCORE"},description = "Test Case: 41940294,52301576")
    public final void verifyViewDevicesRecommendedActionsTile() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessage")) {
            dexPage.scrollToDEXPage("recommendedActionsContent");
            softAssert.assertTrue(dexPage.verifyViewDevicesButton(LanguageCode), "View Devices is not working on HW Tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in System Health Tab.");
        }
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron", "scoreTitleOSPerf"), "Navigation to OS Performance tab failed");
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessagePerf")) {
            dexPage.scrollToDEXPage("recommendedActionsContent");
            softAssert.assertTrue(dexPage.verifyViewDevicesButton(LanguageCode), "View Devices is not working on Perf Tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in OS Performance Tab.");
        }
        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        waitForPageLoaded();
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessageSecurity")) {
            dexPage.scrollToDEXPage("recommendedActionsContent");
            softAssert.assertTrue(dexPage.verifyViewDevicesButton(LanguageCode), "View Devices is not working on Security Tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in Security Tab.");
        }
        Assert.assertTrue(dexPage.verifyHomeNavigation("networkChevron","networkTitle"),"Navigation to Network tab failed");
        waitForPageLoaded();
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssuesNoDataMessageNW")) {
            dexPage.scrollToDEXPage("recommendedActionsContent");
            softAssert.assertTrue(dexPage.verifyViewDevicesButton(LanguageCode), "View Devices is not working on Network Tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in Network Tab.");
        }
        softAssert.assertAll();
        LOGGER.info("View Devices functionality validation has been completed successfully.");
    }

    @Test(priority = 16, groups = { "REGRESSION_SCORE"},description = "Test Case: 41940246",enabled = true)
    //write test case to validate the troubleshoot button
    public final void verifyTroubleshootButton() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessage")) {
            dexPage.scrollToDEXPage("recommendedActionsContent");
            softAssert.assertTrue(dexPage.verifyViewDevicesButton(LanguageCode), "View Devices is not working on System Health Tab.");
            softAssert.assertTrue(dexPage.verifyTroubleshootButton(LanguageCode), "Troubleshoot button is not working on System Health tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in System Health Tab.");
        }
        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        waitForPageLoaded();
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessagePerf")) {
            dexPage.scrollToDEXPage("recommendedActionsContent");
            softAssert.assertTrue(dexPage.verifyViewDevicesButton(LanguageCode), "View Devices is not working on Perf Tab.");
            softAssert.assertTrue(dexPage.verifyTroubleshootButton(LanguageCode), "Troubleshoot button is not working on Perf tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in OS Performance Tab.");
        }
        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        waitForPageLoaded();
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessageSecurity")) {
            dexPage.scrollToDEXPage("recommendedActionsContent");
            softAssert.assertTrue(dexPage.verifyViewDevicesButton(LanguageCode), "View Devices is not working on Security Tab.");
            softAssert.assertTrue(dexPage.verifyTroubleshootButton(LanguageCode), "Troubleshoot button is not working on Security tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in Security Tab.");
        }
        Assert.assertTrue(dexPage.verifyHomeNavigation("networkChevron","networkTitle"),"Navigation to Network tab failed");
        waitForPageLoaded();
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssuesNoDataMessageNW")) {
            dexPage.scrollToDEXPage("recommendedActionsContent");
            softAssert.assertTrue(dexPage.verifyViewDevicesButton(LanguageCode), "View Devices is not working on Network Tab.");
            softAssert.assertTrue(dexPage.verifyTroubleshootButton(LanguageCode), "Troubleshoot button is not working on Network tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in Network Tab.");
        }
        softAssert.assertAll();
        LOGGER.info("Troubleshoot button functionality validation has been completed successfully.");
    }

    @Test(priority = 17, groups = { "REGRESSION_SCORE","PRODUCTION_SCORE", "PRODUCTION_LDK","INITECH_SOLUTIONS" })
    public final void verifyChartsLoadingExperienceManagement() throws Exception {
        login("ITADMIN_EMAIL_SCORE", "ITADMIN_PASSWORD_SCORE");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"","expScoreChartContent","Experience Score Chart"), "Experience Score Chart is not loading on Home page.");
        Assert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"","expScoreOverTimeContent","Experience Score Overtime Chart"), "Experience Score Overtime Chart is not loading on Home page.");
        softAssert.assertTrue(dexPage.verifyTrendChartLastDataPoint("expScoreOverTimeXaxis"), "Exp Score Trend chart last data point is not as expected on Experience Management page.");
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"meterchartNoDataMessage","meterChartData","HW Meter Chart"), "Meter Chart on HW Tab are is not loading on Experience Management page.");
       // softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"summarychartNoDataMessage","donutchartCentreText","HW Summary Chart"), "Summary Chart on HW Tab are is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"topIssueschartNoDataMessage","topIssuesChartData","Top System Health Issues"), "Top Issues Chart on HW Tab are is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyTrendChartLastDataPoint("trendchartXaxisData"), "Trend chart last data point is not as expected on Experience Management page.");
        dexPage.scrollUpCharts();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","totalpointsOnChart","HW Weekly Trend Chart"), "Weekly Trend Chart on HW Tab are is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"modelChartNoDataMessage","deviceByModelChartBarsLocator","HW Device Model Chart"), "Device Model Chart on HW Tab are is not loading on Experience Management page.");
        dexPage.scrollDownCharts();
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessage"))
        {
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode, "recommendedActionsNodata", "recommendedActionsContent", "Recommended Actions"), "Recommended Actions Chart on HW Tab are is not loading on Experience Management page.");
        }
        sleeper(2000);
        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        waitForPageLoaded();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"meterchartNoDataMessage","meterChartData","Perf Meter Chart"), "Meter Chart on Performance Tab is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"topIssueschartNoDataMessagePerf","topIssuesChartData","Top OS Performance Issues"), "Top Issues Chart on Performance Tab is not loading on Experience Management page.");
        dexPage.scrollUpCharts();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","totalpointsOnChart","Perf Weekly Trend Chart"), "Weekly Trend Chart on Performance Tab is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"modelChartNoDataMessageOSPerf","deviceByModelChartBarsLocator","Perf Device Model Chart"), "Device Model Chart on Performance Tab is not loading on Experience Management page.");
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessagePerf"))
        {
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode, "recommendedActionsNodata", "recommendedActionsContent", "Recommended Actions"), "Recommended Actions Chart on Performance Tab is not loading on Experience Management page.");
        }
        sleeper(2000);
        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        waitForPageLoaded();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"meterchartNoDataMessage","meterChartData","Security Meter Chart"), "Meter Chart on Security Tab is not loading on Experience Management page.");
        //softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","donutchartCentreText","Security Summary Chart"), "Summary Chart on Security Tab is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"topIssueschartNoDataMessageSecurity","topIssuesChartData","Top Security Issues"), "Top Issues Chart on Security Tab is not loading on Experience Management page.");
        dexPage.scrollUpCharts();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","totalpointsOnChart","Security Weekly Trend Chart"), "Weekly Trend Chart on Security Tab is not loading on Experience Management page.");
        dexPage.scrollToDEXPage("deviceModelChartSecurity");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"modelChartNoDataMessageSecurity","deviceModelContentSecurity","Security Device Model Chart"), "Device Model Chart on Security Tab is not loading on Experience Management page.");
        //if (!dexPage.getTextOfDEXPage("topIssueschartNoDataMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.bios.action.everything_is_great")))
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessageSecurity"))
        {
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode, "recommendedActionsNodata", "recommendedActionsContent", "Recommended Actions"), "Recommended Actions Chart on Security Tab is not loading on Experience Management page.");
        }
        sleeper(2000);
        Assert.assertTrue(dexPage.verifyHomeNavigation("networkChevron","networkTitle"),"Navigation to Network tab failed");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"","scoreChartContent","Network Meter Chart"), "Meter Chart on Security Tab is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"topIssuesNodataMessageNW","topIssuesChartDataNW","Top Network Issues"), "Top Issues Chart on Network Tab is not loading on Experience Management page.");
        dexPage.scrollUpCharts();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","totalpointsOnChart","Security Weekly Trend Chart"), "Weekly Trend Chart on Network Tab is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"nwAuthTypeNodataMessage","nwAuthTypeBars","NW Auth Type Chart"), "NW Auth Type Chart on Security Tab is not loading on Experience Management page.");
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssuesNodataMessageNW"))
        {
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode, "recommendedActionsNodata", "recommendedActionsContent", "Recommended Actions"), "Recommended Actions Chart on Security Tab is not loading on Experience Management page.");
        }
        //softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("exportButton"), "NW Grid is not loading on Experience Management page.");
        softAssert.assertAll();
        LOGGER.info("Experience management Charts loading functionality validation has been completed successfully.");
    }

    @Test(priority = 18, groups = { "REGRESSION_SCORE","PRODUCTION_SCORE"})
    public final void verifyGridLoadingExperienceManagement() throws Exception {
        login("ITADMIN_EMAIL_SCORE", "ITADMIN_PASSWORD_SCORE");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        dexPage.scrollToDEXPage("systemHealthOverTimetitle");
        Assert.assertTrue(dexPage.verifyExpmgmtGridNavigation(LanguageCode),"Experience Management Grid page did not load successfully.");
        softAssert.assertAll();
        LOGGER.info("Experience management Grid loading functionality validation has been completed successfully.");
    }

    @Test(priority = 19, groups = { "REGRESSION_SCORE"},description = "Test Case: 41124382,41940338")
    public final void verifyWeeklyTrendChartHW() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        dexPage.scrollToDEXPage("systemHealthOverTimetitle");
        softAssert.assertTrue(dexPage.verifyWeeklyTrendChart(),"Weekly Trend Chart is not working for HW Health");
        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        dexPage.scrollToDEXPage("systemHealthOverTimetitle");
        softAssert.assertTrue(dexPage.verifyWeeklyTrendChart(),"Weekly Trend Chart is not working for Performance");
        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        dexPage.scrollToDEXPage("systemHealthOverTimetitle");
        softAssert.assertTrue(dexPage.verifyWeeklyTrendChart(),"Weekly Trend Chart is not working for Security");
        Assert.assertTrue(dexPage.verifyHomeNavigation("networkChevron","networkTitle"),"Navigation to Network tab failed");
        softAssert.assertTrue(dexPage.verifyWeeklyTrendChart(),"Weekly Trend Chart is not working for Network");
        softAssert.assertAll();
        LOGGER.info("Weekly Trend Chart(HW Health) functionality validation has been completed successfully.");
    }

    @Test(priority = 20, groups = { "REGRESSION_SCORE"},description = "Test Case: 52301615")
    public final void verifyColumnHeadersExpHeaders() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        final String[] HW_Health_Coulmn = {"asset_details_serialNumber","assets.table.heading.alias_name","exp.mgmt.details.warrantyState","exp.mgmt.fleetOverTime.health.title.y","exp.mgmt.details.diskHealth",
                "exp.mgmt.details.batteryHealth","exp.mgmt.details.batteryFcc","exp.mgmt.details.fanSpeed","exp.mgmt.details.gpuHealth","exp.mgmt.details.gpuIssues","exp.mgmt.details.lastOnlineDate"};
//                "exp.mgmt.details.mfgYr","exp.mgmt.details.diskreplacementreason","exp.mgmt.details.performanceScore",
//                "exp.mgmt.details.avgCpuUtilization","exp.mgmt.details.avgMemUtilization","exp.mgmt.details.diskStorageUtilization","exp.mgmt.details.osCrashes","exp.mgmt.details.bsodIssues","exp.mgmt.details.avgStartUp","exp.mgmt.details.avgShutDown","exp.mgmt.details.softwareErrors","exp.mgmt.details.dockingStatus","exp.mgmt.details.dockingHealth","exp.mgmt.details.missingDrivers","exp.mgmt.details.biosStatus","exp.mgmt.details.criticalBiosUpdates","exp.mgmt.details.batteryHealthSetting",
//                "exp.mgmt.details.lastRestartDate","exp.mgmt.details.missingUpdates","exp.mgmt.details.securityScore","exp.mgmt.details.missingSecurityUpdate","exp.mgmt.details.secureBootStatus","exp.mgmt.details.antivirusStatus","exp.mgmt.details.antivirusSignStatus","exp.mgmt.details.avPolicyValue","exp.mgmt.details.firewallStatus","exp.mgmt.details.firewallSignStatus","exp.mgmt.details.encryptionStatus","benchmark.details.table.device_model","security.table.heading.deviceManufacturer","asset_detail.hardware_memory",
//                "exp.mgmt.details.cpuProcessor","exp.mgmt.details.gpuProcessor","exp.mgmt.details.deviceOs","exp.mgmt.details.osBuildNumber","exp.mgmt.details.bornOnYear"};
        final String[] perf_Coulmn = {"asset_details_serialNumber","assets.table.heading.alias_name","exp.mgmt.details.warrantyState","exp.mgmt.details.performanceScore","exp.mgmt.details.avgCpuUtilization","exp.mgmt.details.avgMemUtilization","exp.mgmt.details.diskStorageUtilization","exp.mgmt.details.osCrashes","exp.mgmt.details.bsodIssues","exp.mgmt.details.avgStartUp","exp.mgmt.details.avgShutDown","exp.mgmt.details.softwareErrors","exp.mgmt.details.dockingStatus",
                "exp.mgmt.details.dockingHealth","exp.mgmt.details.missingDrivers","exp.mgmt.details.biosStatus","exp.mgmt.details.criticalBiosUpdates","exp.mgmt.details.batteryHealthSetting","exp.mgmt.details.lastRestartDate","exp.mgmt.details.missingUpdates","exp.mgmt.details.lastOnlineDate"};
//        ,"exp.mgmt.details.mfgYr","exp.mgmt.fleetOverTime.health.title.y","exp.mgmt.details.diskHealth","exp.mgmt.details.diskreplacementreason","exp.mgmt.details.batteryHealth","exp.mgmt.details.batteryFcc","exp.mgmt.details.fanSpeed",
//                "exp.mgmt.details.gpuHealth","exp.mgmt.details.gpuIssues","exp.mgmt.details.securityScore","exp.mgmt.details.missingSecurityUpdate","exp.mgmt.details.secureBootStatus","exp.mgmt.details.antivirusStatus","exp.mgmt.details.antivirusSignStatus","exp.mgmt.details.avPolicyValue","exp.mgmt.details.firewallStatus","exp.mgmt.details.firewallSignStatus","exp.mgmt.details.encryptionStatus","benchmark.details.table.device_model","security.table.heading.deviceManufacturer","asset_detail.hardware_memory",
//                "exp.mgmt.details.cpuProcessor","exp.mgmt.details.gpuProcessor","exp.mgmt.details.deviceOs","exp.mgmt.details.osBuildNumber","exp.mgmt.details.bornOnYear"};
        final String[] security_Coulmn = {"asset_details_serialNumber","assets.table.heading.alias_name","exp.mgmt.details.warrantyState","exp.mgmt.details.securityScore","exp.mgmt.details.missingSecurityUpdate","exp.mgmt.details.secureBootStatus","exp.mgmt.details.antivirusStatus","exp.mgmt.details.antivirusSignStatus","exp.mgmt.details.avPolicyValue","exp.mgmt.details.firewallStatus","exp.mgmt.details.firewallSignStatus","exp.mgmt.details.encryptionStatus","exp.mgmt.details.lastOnlineDate"};
//                "exp.mgmt.details.mfgYr","exp.mgmt.fleetOverTime.health.title.y","exp.mgmt.details.diskHealth","exp.mgmt.detailsplacementreason","exp.mgmt.details.batteryHealth","exp.mgmt.details.batteryFcc","exp.mgmt.details.fanSpeed","exp.mgmt.details.gpuHealth","exp.mgmt.details.gpuIssues","exp.mgmt.details.performanceScore","exp.mgmt.details.avgCpuUtilization","exp.mgmt.details.avgMemUtilization","exp.mgmt.details.diskStorageUtilization","exp.mgmt.details.osCrashes","exp.mgmt.details.bsodIssues",
//                "exp.mgmt.details.avgStartUp","exp.mgmt.details.avgShutDown","exp.mgmt.details.softwareErrors","exp.mgmt.details.dockingStatus","exp.mgmt.details.dockingHealth","exp.mgmt.details.missingDrivers","exp.mgmt.details.biosStatus","exp.mgmt.details.criticalBiosUpdates","exp.mgmt.details.batteryHealthSetting","exp.mgmt.details.lastRestartDate","exp.mgmt.details.missingUpdates","benchmark.details.table.device_model","security.table.heading.deviceManufacturer","asset_detail.hardware_memory","exp.mgmt.details.cpuProcessor",
//                "exp.mgmt.details.gpuProcessor","exp.mgmt.details.deviceOs","exp.mgmt.details.osBuildNumber","exp.mgmt.deta"};
        final String[] network_Coulmn = {"asset_details_serialNumber","assets.table.heading.alias_name","applications.filter.dashboard.model","exp.mgmt.details.warrantyState","exp.mgmt.details.NetworkScore","exp.mgmt.details.avgNominalSpeed","exp.mgmt.details.minNominalSpeed","exp.mgmt.details.badNominalSpeedDuration","exp.mgmt.details.avgSignalStrength","exp.mgmt.details.minSignalStrength","exp.mgmt.details.badSignalStrengthDuration","exp.mgmt.details.totalDisconnectionCount","exp.mgmt.details.unsecureNetworkCount"};

        final String[] application_Coulmn = {"application.filter.applicationName","application.grid.deviceAppScore","application.grid.column.Crashes","application.grid.column.Freezes","application.grid.column.RecommendedVersion","applications.least_stable_version.title","applications.crashesandfreezes.legends.2","application.filter.applicationType","application.grid.column.majorOS"};
        final String[] application_details_Coulmn = {"applicationDetails.grid.column.deviceSn","application.grid.column.Crashes","applicationDetails.grid.column.lastAppCrashOccurrenceDate","application.grid.column.Freezes","applicationDetails.grid.column.lastAppFreezeOccurrenceDate","applicationDetails.grid.column.binaryFileWithErrors","applicationDetails.grid.column.appVersion","applicationDetails.grid.column.deviceName","applicationDetails.grid.column.deviceModel","applications.filter.dashboard.mfg","exp.mgmt.details.mfgYr","application.grid.column.majorOS","applicationInv.grid.column.osVersion","exp.mgmt.details.osBuildNumber","asset_details_location"};

        final String[] installed_application_coulmn = {"applicationDetails.grid.column.deviceSn","applicationDetails.grid.column.deviceName","application.grid.deviceAppScore","application.grid.column.Crashes","application.grid.column.Freezes","applicationDetails.grid.column.deviceModel","applications.filter.dashboard.mfg","exp.mgmt.details.mfgYr","application.grid.column.majorOS","applicationInv.grid.column.osVersion","asset_details_location","exp.mgmt.details.osBuildNumber"};
        final String[] topAppCrashFreeze_Coulmn = {"application.filter.applicationName","application.grid.column.Crashes","application.grid.column.Freezes","application.grid.column.RecommendedVersion","applications.least_stable_version.title","applications.crashesandfreezes.legends.2","application.filter.applicationType","application.grid.column.majorOS"};


        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        dexPage.scrollToDEXPage("systemHealthOverTimetitle");
        Assert.assertTrue(dexPage.verifyExpmgmtGridNavigation(LanguageCode),"Experience Management Grid page did not load successfully.");
        resettoDefaultTableConfiguration();
        softAssert.assertTrue(dexPage.getTextOfDEXPage("lastSignedInUserColumn").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui","assets.table.heading.last_signed_user" )),"Last Signed In User column header is not correct.");
        softAssert.assertTrue(dexPage.verifyHeaderExpMgmtGridPage(LanguageCode,"columnListGridPage",HW_Health_Coulmn),"Column headers are not correct for HW Health");
        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        dexPage.scrollToDEXPage("systemHealthOverTimetitle");
        Assert.assertTrue(dexPage.verifyExpmgmtGridNavigation(LanguageCode),"Experience Management Grid page did not load successfully.");
        resettoDefaultTableConfiguration();
        softAssert.assertTrue(dexPage.getTextOfDEXPage("lastSignedInUserColumn").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui","assets.table.heading.last_signed_user" )),"Last Signed In User column header is not correct.");
        softAssert.assertTrue(dexPage.verifyHeaderExpMgmtGridPage(LanguageCode,"columnListGridPage",perf_Coulmn),"Column headers are not correct for Performance");
        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        dexPage.scrollToDEXPage("systemHealthOverTimetitle");
        Assert.assertTrue(dexPage.verifyExpmgmtGridNavigation(LanguageCode),"Experience Management Grid page did not load successfully.");
        resettoDefaultTableConfiguration();
        softAssert.assertTrue(dexPage.getTextOfDEXPage("lastSignedInUserColumn").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui","assets.table.heading.last_signed_user" )),"Last Signed In User column header is not correct.");
        softAssert.assertTrue(dexPage.verifyHeaderExpMgmtGridPage(LanguageCode,"columnListGridPage",security_Coulmn),"Column headers are not correct for Security");
        Assert.assertTrue(dexPage.verifyHomeNavigation("networkChevron","networkTitle"),"Navigation to Network tab failed");
        resettoDefaultTableConfiguration();
        softAssert.assertTrue(dexPage.getTextOfDEXPage("lastSignedInUserColumn").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui","assets.table.heading.last_signed_user" )),"Last Signed In User column header is not correct.");
        softAssert.assertTrue(dexPage.verifyHeaderExpMgmtGridPage(LanguageCode,"columnListGridPage",network_Coulmn),"Column headers are not correct for Network");
        Assert.assertTrue(dexPage.verifyHomeNavigation("applicationChevron","installedAppTitle"),"Navigation to Application tab failed");
        if(!toggleVerification(CommonVariables.WX_APPLICATION_BREAKDOWN, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            softAssert.assertTrue(dexPage.verifyHeaderExpMgmtGridPage(LanguageCode, "installedAppGrid", application_Coulmn), "Column headers are not correct for Application grid");
            dexPage.clickOnElementsOfDEXPage("firstAppNameLink");
            dexPage.waitForElementsOfDEXPage("appNameDetailsPage");
            sleeper(3000);
            softAssert.assertTrue(dexPage.verifyHeaderExpMgmtGridPage(LanguageCode, "installedAppGrid", application_details_Coulmn), "Column headers are not correct for Application Details grid");

        }else
        {
            softAssert.assertTrue(dexPage.verifyHeaderExpMgmtGridPage(LanguageCode, "installedAppGrid", installed_application_coulmn), "Column headers are not correct for Application grid");
            dexPage.clickOnElementsOfDEXPage("viewDetailsButtonCrash");
            Assert.assertTrue(dexPage.waitForElementsOfDEXPage("topAppCrashesSectionDetails"), "Top App Crashes section is not present on details page.");
            softAssert.assertTrue(dexPage.verifyHeaderExpMgmtGridPage(LanguageCode, "installedAppGrid", topAppCrashFreeze_Coulmn), "Column headers are not correct for Application grid");
            dexPage.clickOnElementsOfDEXPage("firstappbarlink");
            softAssert.assertTrue(dexPage.verifyHeaderExpMgmtGridPage(LanguageCode, "installedAppGrid", application_details_Coulmn), "Column headers are not correct for Application grid");

        }
        softAssert.assertAll();
        LOGGER.info("Experience management Grid Header validation has been completed successfully.");
    }

    @Test(priority = 21, groups = { "REGRESSION_SCORE" },description = "Test Case: 41940348,52300077")
    public final void verifyChangesSinceLastWeek() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        dexPage.scrollToDEXPage("changesSinceLastWeekChartTitleSystemHealth");
        softAssert.assertTrue(dexPage.verifyChangesSinceLastWeek(LanguageCode),"Changes Since Last Week is not working for HW Health");
        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        dexPage.scrollToDEXPage("changesSinceLastWeekChartTitleOSandSystemReliabilty");
        softAssert.assertTrue(dexPage.verifyChangesSinceLastWeek(LanguageCode),"Changes Since Last Week is not working for Performance");
        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        dexPage.scrollToDEXPage("changesSinceLastWeekChartTitleSecurity");
        softAssert.assertTrue(dexPage.verifyChangesSinceLastWeek(LanguageCode),"Changes Since Last Week is not working for Security");
        Assert.assertTrue(dexPage.verifyHomeNavigation("networkChevron","networkTitle"),"Navigation to Network tab failed");
        dexPage.scrollToDEXPage("changesSincelastWeekNW");
        softAssert.assertTrue(dexPage.verifyChangesSinceLastWeek(LanguageCode),"Changes Since Last Week is not working for Network");

        softAssert.assertAll();
        LOGGER.info("Changes Since Last Week functionality validation has been completed successfully.");
    }

    @Test(priority = 22, groups = { "REGRESSION_SCORE" },description = "Test Case: 43137719",enabled = false)
    public final void verifyDashboardExpMgmt() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
        Assert.assertTrue(dexPage.waitForElementsOfDEXPage("devicesChartDashboard")," Hardware Health Score Card did not load on Dashboard.");
        Assert.assertTrue(dexPage.waitForElementsOfDEXPage("secChartDashboard")," Security Health Score Card did not load on Dashboard.");
        Assert.assertTrue(dexPage.verifydexChartDashboardNavigation("devicesChartDashboard","fleetHardwareTab")," Experience Management page (HW Tab) did not load successfully when navigated from Dashboard.");
        dexPage.companyView(CommonVariables.WORKFORCE_CENTRAL_SERVICE);
        dexPage.waitUntilElementIsInvisibleOfDEXPage("reactSkelaton");
        Assert.assertTrue(dexPage.verifydexChartDashboardNavigation("devicesChartDashboard","fleetPerformanceTab")," Experience Management page (Performance Tab) did not load successfully when navigated from Dashboard.");
        dexPage.companyView(CommonVariables.WORKFORCE_CENTRAL_SERVICE);
        dexPage.waitUntilElementIsInvisibleOfDEXPage("reactSkelaton");
        Assert.assertTrue(dexPage.verifydexChartDashboardNavigation("secChartDashboard","fleetSecurityTab")," Experience Management page (Security Tab) did not load successfully when navigated from Dashboard.");
        dexPage.companyView(CommonVariables.WORKFORCE_CENTRAL_SERVICE);
        dexPage.waitUntilElementIsInvisibleOfDEXPage("reactSkelaton");
        Assert.assertTrue(dexPage.verifydexChartDashboardNavigation("secChartDashboard","fleetSecurityTab")," Experience Management page (Network Tab) did not load successfully when navigated from Dashboard.");
        LOGGER.info("Validation of Experience Management page from Dashboard has been completed successfully.");
    }

    @Test(priority = 23, groups = { "REGRESSION_SCORE" }, description = "Test Case 41940350")
    public final void verifyFleetScorePreferenceTileHWHealth() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
        LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
        dexPage.companyView(CommonVariables.SETTINGS);
        if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            preferencesPage.verifyElementsOfPreferencesPage("preferenceTabold");
            preferencesPage.clickOnElementsOfPreferencesPage("preferenceTabold");
        }
        else {
            preferencesPage.verifyElementsOfPreferencesPage("preferenceTabNew");
            preferencesPage.clickOnElementsOfPreferencesPage("preferenceTabNew");
        }
        softAssert.assertTrue(dexPage.getTextOfDEXPage("fleetScoreTitle").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.fleetscorepreferences")),"Fleet Score Preference tile title is not correct.");
        softAssert.assertTrue(dexPage.verifyFleetScorePreferenceTile(LanguageCode,"editIconHwHealthTile"),"Fleet Score Preference tile is not working correctly for Hardware Health.");
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        sleeper(3000);
        softAssert.assertTrue(dexPage.getTextOfDEXPage("bannerNotification1").contains("System Health's scoring preference was updated"),"Notification message is not coming on Exp mgmt page after Fleet Score Preference update.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("gearIcon"),"Settings gear icon is not present on Experience management page.");
        softAssert.assertTrue(dexPage.verifyClickAndValidateElement("gearIcon","fleetScorePreferences"),"Settings page did not load successfully from Experience Management page.");
//        dexPage.companyView(CommonVariables.LOGS);
//        waitForPageLoaded();
//        Assert.assertTrue(logPage.matchTextOfLogPage("logTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs page navigation failed");
//        LOGGER.info("Redirected to logs page");
//        resetTableConfiguration();
//        // Verify device name,type and sub type filter text
//        softAssert.assertTrue(logPage.matchTextOfLogPage("typeBoxText", "Experience Management"), "Selected Type column filter is not Experience Management in Logs");
//        softAssert.assertTrue(logPage.matchTextOfLogPage("subtypeBoxText", "DEX Score Modification"), "Selected Subtype column filter is not DEX Score Modification in Logs");
        softAssert.assertAll();
        LOGGER.info("Fleet Score Preference tile title validation has been completed successfully.");
    }

    @Test(priority = 24, groups = { "REGRESSION_SCORE" }, description = "Test Case 41940350")
    public final void verifyFleetScorePreferenceTileHPerf() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
        LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
        dexPage.companyView(CommonVariables.SETTINGS);
        if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            preferencesPage.verifyElementsOfPreferencesPage("preferenceTabold");
            preferencesPage.clickOnElementsOfPreferencesPage("preferenceTabold");
        }
        else {
            preferencesPage.verifyElementsOfPreferencesPage("preferenceTabNew");
            preferencesPage.clickOnElementsOfPreferencesPage("preferenceTabNew");
        }
        softAssert.assertTrue(dexPage.getTextOfDEXPage("fleetScoreTitle").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.fleetscorepreferences")),"Fleet Score Preference tile title is not correct.");
        softAssert.assertTrue(dexPage.verifyFleetScorePreferenceTile(LanguageCode,"editIconPerfTile"),"Fleet Score Preference tile is not working correctly for Hardware Health.");
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        sleeper(3000);
        softAssert.assertTrue(dexPage.getTextOfDEXPage("bannerNotification2").contains("OS Performance's scoring preference was updated"),"Notification message is not coming on Exp mgmt page after Fleet Score Preference update.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("gearIcon"),"Settings gear icon is not present on Experience management page.");
        softAssert.assertTrue(dexPage.verifyClickAndValidateElement("gearIcon","fleetScorePreferences"),"Settings page did not load successfully from Experience Management page.");
//        dexPage.companyView(CommonVariables.LOGS);
//        waitForPageLoaded();
//        Assert.assertTrue(logPage.matchTextOfLogPage("logTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs page navigation failed");
//        LOGGER.info("Redirected to logs page");
//        resetTableConfiguration();
//        // Verify device name,type and sub type filter text
//        softAssert.assertTrue(logPage.matchTextOfLogPage("typeBoxText", "Experience Management"), "Selected Type column filter is not Experience Management in Logs");
//        softAssert.assertTrue(logPage.matchTextOfLogPage("subtypeBoxText", "DEX Score Modification"), "Selected Subtype column filter is not DEX Score Modification in Logs");
        softAssert.assertAll();
        LOGGER.info("Fleet Score Preference tile title validation has been completed successfully.");
    }

    @Test(priority = 25, groups = { "REGRESSION_SCORE" }, description = "Test Case 41940350")
    public final void verifyFleetScorePreferenceTileHSecurity() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
        LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
        dexPage.companyView(CommonVariables.SETTINGS);
        if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            preferencesPage.verifyElementsOfPreferencesPage("preferenceTabold");
            preferencesPage.clickOnElementsOfPreferencesPage("preferenceTabold");
        }
        else {
            preferencesPage.verifyElementsOfPreferencesPage("preferenceTabNew");
            preferencesPage.clickOnElementsOfPreferencesPage("preferenceTabNew");
        }
        softAssert.assertTrue(dexPage.getTextOfDEXPage("fleetScoreTitle").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.fleetscorepreferences")),"Fleet Score Preference tile title is not correct.");
        softAssert.assertTrue(dexPage.verifyFleetScorePreferenceTile(LanguageCode,"editIconSecTile"),"Fleet Score Preference tile is not working correctly for Hardware Health.");
        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        sleeper(3000);
        softAssert.assertTrue(dexPage.getTextOfDEXPage("bannerNotification3").contains("Security's scoring preference was updated"),"Notification message is not coming on Exp mgmt page after Fleet Score Preference update.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("gearIcon"),"Settings gear icon is not present on Experience management page.");
        softAssert.assertTrue(dexPage.verifyClickAndValidateElement("gearIcon","fleetScorePreferences"),"Settings page did not load successfully from Experience Management page.");
//        dexPage.companyView(CommonVariables.LOGS);
//        waitForPageLoaded();
//        Assert.assertTrue(logPage.matchTextOfLogPage("logTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs page navigation failed");
//        LOGGER.info("Redirected to logs page");
//        resetTableConfiguration();
//        // Verify device name,type and sub type filter text
//        softAssert.assertTrue(logPage.matchTextOfLogPage("typeBoxText", "Experience Management"), "Selected Type column filter is not Experience Management in Logs");
//        softAssert.assertTrue(logPage.matchTextOfLogPage("subtypeBoxText", "DEX Score Modification"), "Selected Subtype column filter is not DEX Score Modification in Logs");
        softAssert.assertAll();
        LOGGER.info("Fleet Score Preference tile title validation has been completed successfully.");
    }

    @Test(priority = 26, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ" }, description = "Test Case:C57504016")
    public final void verifyApplicationTopCrashChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("applicationChevron","installedAppTitle"),"Navigation to Application tab failed");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("installedappsWithMostCrashesTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "applications.applications.with.most.crashes.title")),"Top Crashes title is not matching.");
        softAssert.assertTrue(dexPage.verifyNoOfCrashesFreezes("installedfreezesProgressBars"),"Number of crashes and freezes are not visible on Application page.");

        if(toggleVerification(CommonVariables.WX_APPLICATION_BREAKDOWN, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            //dexPage.applyRangeFilter("crashHeader", "rangePickerAppCrash");

            // 1. Store Top App Crashes data from main page
            List<String> mainAppNames = dexPage.getTopCrashAppNamesMain("topCrashAppNamesMain");
            List<String> mainDeviceCounts = dexPage.getTopCrashImpactedDevicesMain("topCrashImpactedDevicesMain");

            // 2. Click on View Details button
            dexPage.clickOnElementsOfDEXPage("viewDetailsButtonCrash");

            // 3. Verify Top App Crashes section is present on details page
            softAssert.assertTrue(dexPage.isTopAppCrashesSectionPresent("topAppCrashesSectionDetails"), "Top App Crashes section is not present on details page.");

            // 4. Retrieve Top App Crashes data from details page
            List<String> detailsAppNames = dexPage.getTopCrashAppNamesDetails("topCrashAppNamesMain");
            List<String> detailsDeviceCounts = dexPage.getTopCrashImpactedDevicesDetails("topCrashImpactedDevicesMain");

            // 5. Validate that app names and impacted device counts match
            softAssert.assertEquals(detailsAppNames, mainAppNames, "App names in Top App Crashes do not match between main and details page.");
            System.out.println("details" + detailsAppNames);
            System.out.println("main" + mainAppNames);
            softAssert.assertEquals(detailsDeviceCounts, mainDeviceCounts, "Impacted device counts in Top App Crashes do not match between main and details page.");
        }
        else{
            softAssert.assertTrue(dexPage.sortApplicationColumnDesc("sortedNoOfDevices","noOfDevicesHeader"),"Number of crashes column did not sort successfully.");
            softAssert.assertTrue(dexPage.verifyTopCrashesFreezesChart("appCrashNameList","appCrashCountList","listofCrashCountGrid"),"Top Crashes chart is not working properly on Application page.");

        }
        softAssert.assertAll();
        LOGGER.info("Application Top Crash chart functionality validation has been completed successfully.");
    }

    @Test(priority = 27, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"  }, description = "Test Case:C57504017")
    public final void verifyApplicationTopFreezeChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("applicationChevron","installedAppTitle"),"Navigation to Application tab failed");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("installedappsWithMostFreezesTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "applications.applications.with.most.freezes.title")),"Top Freezes title is not matching.");
        softAssert.assertTrue(dexPage.verifyNoOfCrashesFreezes("freezesProgressBars"),"Number of crashes and freezes are not visible on Application page.");

        if(toggleVerification(CommonVariables.WX_APPLICATION_BREAKDOWN, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            // 1. Store Top App Crashes data from main page
            List<String> mainAppNames = dexPage.getTopCrashAppNamesMain("topFreezeAppNamesMain");
            List<String> mainDeviceCounts = dexPage.getTopCrashImpactedDevicesMain("topFreezeImpactedDevicesMain");

            // 2. Click on View Details button
            dexPage.clickOnElementsOfDEXPage("viewDetailsButtonFreeze");

            // 3. Verify Top App Crashes section is present on details page
            softAssert.assertTrue(dexPage.isTopAppCrashesSectionPresent("topAppFreezeSectionDetails"), "Top App Crashes section is not present on details page.");

            // 4. Retrieve Top App Crashes data from details page
            List<String> detailsAppNames = dexPage.getTopCrashAppNamesDetails("topFreezeAppNamesMain");
            List<String> detailsDeviceCounts = dexPage.getTopCrashImpactedDevicesDetails("topFreezeImpactedDevicesMain");

            // 5. Validate that app names and impacted device counts match
            softAssert.assertEquals(detailsAppNames, mainAppNames, "App names in Top App Crashes do not match between main and details page.");
            softAssert.assertEquals(detailsDeviceCounts, mainDeviceCounts, "Impacted device counts in Top App Crashes do not match between main and details page.");
        }else{
            softAssert.assertTrue(dexPage.sortApplicationColumnDesc("sortedNoOfDevices","noOfDevicesHeader"),"Number of freeze column did not sort successfully.");
            softAssert.assertTrue(dexPage.verifyTopCrashesFreezesChart("appFreezeNameList","appFreezeCountList","listofFreezeCountGrid"),"Top Freezes chart is not working properly on Application page.");

        }
        softAssert.assertAll();
        LOGGER.info("Application Top Freeze chart functionality validation has been completed successfully.");
    }

    @Test(priority = 28, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ" }, description = "Test Case:C57504018")
    public final void verifyApplicationScoreChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        waitForPageLoaded();
        
        if (dexPage.waitForElementsOfDEXPage("applicationScoreValue")) {
            String appScoreValueDashboard = dexPage.getTextOfDEXPage("applicationScoreValue");
            Assert.assertTrue(dexPage.verifyHomeNavigation("applicationChevron","installedAppTitle"),"Navigation to Application tab failed");
            //softAssert.assertTrue(dexPage.getTextOfDEXPage("applicationScoreTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "applications.application_score.title")),"Top Freezes title is not matching.");
            //softAssert.assertTrue(dexPage.getTextOfDEXPage("applicationScoreSubtitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "exp.mgmt.fleetOverTime.warning")),"Top Freezes title is not matching.");
            String appScoreValueAppPage = dexPage.getTextOfDEXPage("applicationScoreValueAppPage");
            softAssert.assertTrue(appScoreValueDashboard.equalsIgnoreCase(appScoreValueAppPage),"Application score is not matching on dashboard and application page.");
            softAssert.assertAll();
            LOGGER.info("Application Score chart functionality validation has been completed successfully.");
        } else {
            LOGGER.info("Devices with no reported issues are not currently included");
        }
        
    }

    @Test(priority = 29, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ" }, description = "Test Case:C57504033",enabled = true)
    public final void verifyApplicationCrashesFreezesChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("applicationChevron","installedAppTitle"),"Navigation to Application tab failed");
        softAssert.assertTrue(dexPage.verifyCrashesAndFreezesChart(), "Incorrect dates are being displayed in Application crashes freezes overtime chart.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("crashesFreezesOvertimeTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "applications.crashesandfreezes.title")), "Freezes Crashes overtime title does not match.");
        softAssert.assertAll();
        LOGGER.info("Freezes and Crashes chart functionality validation has been completed successfully.");
    }

    @Test(priority = 30, groups = { "REGRESSION_APPLICATIONS_INSIGHTS" },description = "Test Case: C57504015")
    public final void verifyApplicationExportFeature() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyHomeNavigation("applicationChevron","installedAppTitle"),"Navigation to Application tab failed");
        waitForPageLoaded();
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        if(!toggleVerification(CommonVariables.WX_APPLICATION_BREAKDOWN, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            if (dexPage.verifyElementsOfDEXPage("listOfAppNames")) {
                Assert.assertTrue(analyticsPage.verifyNewExportFunctionality(LanguageCode, "toastNotificationExportApplicationInventory"), "Export functionality is not working on Application table..");
                String firstAppName = dexPage.getTextOfDEXPage("firstAppNameLink");
                dexPage.clickOnElementsOfDEXPage("firstAppNameLink");
                dexPage.waitForElementsOfDEXPage("appNameDetailsPage");
                sleeper(3000);
                Assert.assertTrue(firstAppName.equalsIgnoreCase(dexPage.getTextOfDEXPage("appNameDetailsPage")), "Application name is not matching on Application page.");
                Assert.assertTrue(dexPage.verifyAppExportFunctionality(LanguageCode, "toastNotificationExportApplication","exportAppDetails"), "Export functionality is not working on Application details table..");
            } else {
                Assert.assertTrue(dexPage.verifyElementsOfDEXPage("appScoreGridNoData"));
                LOGGER.info("No data is present in Application grid.");
            }
        }else
        {
            Assert.assertTrue(analyticsPage.verifyNewExportFunctionality(LanguageCode, "toastNotificationExportApplicationInventory"), "Export functionality is not working on Application table..");
            dexPage.clickOnElementsOfDEXPage("viewDetailsButtonCrash");
            Assert.assertTrue(dexPage.verifyAppExportFunctionality(LanguageCode, "toastNotificationExportApplication","exportTopAppCrashFreeze"), "Export functionality is not working on Application details table..");
        }
        softAssert.assertAll();
        LOGGER.info("Export of Application score page functionality validation has been completed successfully.");
    }

    @Test(priority = 31, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"},description = "Test Case: C57503972")
    public final void verifyApplicationCrashesAndFreezesTableOnDeviceDetailsPage() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        dexPage.scrollToDEXPage("systemHealthOverTimetitle");
        Assert.assertTrue(dexPage.verifyDeviceDetailNavigation(LanguageCode),"Device Details page did not load from Exp Mgmt Page.");
        sleeper(5000);
        if(!toggleVerification(CommonVariables.WX_APPLICATION_BREAKDOWN, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            dexPage.clickOnElementsOfDEXPage("healthandProtectionTabDeviceDetails");
            dexPage.scrollToDEXPage("applicationCrashes&FreezesTitle");
            softAssert.assertTrue((dexPage.getTextOfDEXPage("applicationCrashes&FreezesTitle").equalsIgnoreCase("Application Crashes & Freezes")),"application Crashes & Freezes title text is incorrect on Device Details Page.");
            softAssert.assertTrue((dexPage.getTextOfDEXPage("applicationCrashes&FreezesSubtitle").equalsIgnoreCase("These app errors occurred within the last 30 days.")),"application Crashes & Freezes subtitle text is incorrect on Device Details Page.");
        }else
        {
            dexPage.clickOnElementsOfDEXPage("softwareTabDeviceDetails");
        }
        waitForPageLoaded();
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("dateofErrorColumn"),"date of error column is not present in application Crashes & Freezes table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("ErrorTypeColumn"),"Error Type column is not present in application Crashes & Freezes table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("appNameColumn"),"app Name column is not present in application Crashes & Freezes table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("appVersionColumn"),"app Version column is not present in application Crashes & Freezes table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("binaryFileNameColumn"),"binary File Name column is not present in application Crashes & Freezes table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("binaryFileVersionColumn"),"binary File Version column is not present in application Crashes & Freezes table.");
        softAssert.assertAll();
        LOGGER.info("Application Crashes And Freezes Table On Device Details Page validation has been completed successfully.");
    }

    @Test(priority = 32, groups = { "REGRESSION_SCORE", "REGRESSION_INTEGRATIONQA_CUJ"},description = "Test Case: 41940294, C42397648",enabled = false)
    public final void verifyRecommendedActionsFromHomeDashboard() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        dexPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DASHBOARD);
        waitForPageLoaded();
        softAssert.assertTrue(dexPage.verifyRecommendedActionstile(LanguageCode),"Recommended Actions is not working");
        softAssert.assertTrue(dexPage.verifyViewDevicesButton(LanguageCode),"View Devices is not working");
        softAssert.assertTrue(dexPage.verifyTroubleshootButton(LanguageCode),"Troubleshoot button is not working.");
        softAssert.assertAll();
        LOGGER.info("Recommended Actions From Home Dashboard functionality validation has been completed successfully.");
    }

    @Test(priority = 33, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"},description = "Test Case: C56673710")
    public final void verifyApplicationInventoryTableOnDeviceDetailsPage() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        dexPage.scrollToDEXPage("systemHealthOverTimetitle");
        Assert.assertTrue(dexPage.verifyDeviceDetailNavigation(LanguageCode),"Device Details page did not load from Exp Mgmt Page.");
        sleeper(5000);
        dexPage.clickOnElementsOfDEXPage("healthandProtectionTabDeviceDetails");
        dexPage.clickOnElementsOfDEXPage("softwareTabDeviceDetails");
        waitForPageLoaded();
        dexPage.scrollToDEXPage("applicationInventoryTitle");
        softAssert.assertTrue((dexPage.getTextOfDEXPage("applicationInventoryTitle").equalsIgnoreCase("Application Inventory")),"Application Inventory title text is incorrect on Device Details Page.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("appNameColumn"),"app Name column is not present in Application Inventory table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("appVersionColumn"),"app Version column is not present in Application Inventory table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("appPublisherColumn"),"app Publisher Column column is not present in Application Inventory table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("appInstallDateColumn"),"app Install Date Column column is not present in Application Inventory table.");
        softAssert.assertAll();
        LOGGER.info("Application Inventory Table On Device Details Page validation has been completed successfully.");
    }

    @Test(priority = 34, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"},description = "Test Case:C57503944")
    public final void verifyApplicationInventoryExportFeature() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        sleeper(3000);
        analyticsPage.clickOnElementsOfAnalyticsPage("applicationInventoryButton");
        waitForPageLoaded();
        if (analyticsPage.verifyElementsOfAnalyticsPage("exportButton")) {
            Assert.assertTrue(analyticsPage.verifyNewExportFunctionality(LanguageCode, "toastNotificationExportApplicationInventory"), "Export functionality is not working on Application inventory Dashboard table.");
            String firstAppName1 = analyticsPage.getTextOfAnalyticsPage("firstAppNameLinkApplicationInventoryDashboard");
            analyticsPage.clickOnElementsOfAnalyticsPage("firstAppNameLinkApplicationInventoryDashboard");
            analyticsPage.verifyElementsOfAnalyticsPage("appInventoryDetailsPage");
            sleeper(3000);
            //Assert.assertTrue(firstAppName1.equalsIgnoreCase(analyticsPage.getTextOfAnalyticsPage("appInventoryDetailsPage")), "Application name is not matching on Application inventory Details page.");
            Assert.assertTrue(analyticsPage.verifyExportFunctionalityforApplicationInventory(LanguageCode, "toastNotificationExportApplicationInventory"), "Export functionality is not working on Application inventory Details table.");
        }
        else{
            Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appInventoryGridNoData"));
            LOGGER.info("No data is present in Application inventory grid.");
        }
        softAssert.assertAll();
        LOGGER.info("Export of Application Inventory functionality validation has been completed successfully.");
    }

    @Test(priority = 35, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ" }, description = "Test Case: C56673712")
    public final void verifyTopInstalledAppsChartOnAppInventoryDashboard() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        sleeper(3000);
        analyticsPage.clickOnElementsOfAnalyticsPage("applicationInventoryButton");
        waitForPageLoaded();
        softAssert.assertTrue(analyticsPage.getTextOfAnalyticsPage("topInstalledAppsTitle").equalsIgnoreCase("Top Installed Apps"),"Top Installed Apps title is not matching.");
        softAssert.assertTrue(analyticsPage.verifyNoInstalledAppsAndVersions("topAppNameList"),"top 5 installed apps are not visible on Application inventory page.");
        softAssert.assertTrue(analyticsPage.verifyNoInstalledAppsAndVersions("topInstalledAppsProgressBars"),"Number of top installed apps are not visible on Application inventory page.");
        softAssert.assertTrue(analyticsPage.verifyNoInstalledAppsAndVersions("topAppCountList"),"top 5 installed apps devices counts are not visible on Application inventory page.");
        softAssert.assertAll();
        LOGGER.info("top Installed Apps chart functionality validation has been completed successfully.");
    }

    @Test(priority = 36, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ" }, description = "Test Case C57503931")
    public final void verifyAppsWithMostInstalledVersionsChartOnAppInventoryDashboard() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        sleeper(3000);
        analyticsPage.clickOnElementsOfAnalyticsPage("applicationInventoryButton");
        waitForPageLoaded();
        softAssert.assertTrue(analyticsPage.getTextOfAnalyticsPage("appsWithMostInstalledVersionsTitle").equalsIgnoreCase("Apps with Most Installed Versions"),"Apps with Most Installed Versions title is not matching.");
        softAssert.assertTrue(analyticsPage.verifyNoInstalledAppsAndVersions("appsWithMostInstalledVersionsList"),"top 5 Apps with Most Installed Versions are not visible on Application inventory page.");
        softAssert.assertTrue(analyticsPage.verifyNoInstalledAppsAndVersions("appsWithMostInstalledVersionssProgressBars"),"Number of Apps with Most Installed Versions are not visible on Application inventory page.");
        softAssert.assertTrue(analyticsPage.verifyNoInstalledAppsAndVersions("appsWithMostInstalledVersionssCountList"),"top 5 Apps with Most Installed Versions counts are not visible on Application inventory page.");
        softAssert.assertAll();
        LOGGER.info("Apps with Most Installed Versions chart functionality validation has been completed successfully.");
    }

    @Test(priority = 37, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ" }, description = "Test Case: C56673713")
    public final void verifyAppInventoryDashboard() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        sleeper(3000);
        analyticsPage.clickOnElementsOfAnalyticsPage("applicationInventoryButton");
        waitForPageLoaded();
        softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("appNameColumn"),"app Name column is not present in Application Inventory table.");
        softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("noOfDevicesHeader"),"noOfDevices column is not present in Application Inventory table.");
        softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("latestVersionColumn"),"latestVersion Column column is not present in Application Inventory table.");
        softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("totalVersionsInstalledColumn"),"totalVersionsInstalled Column column is not present in Application Inventory table.");
        softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("appPublisherColumn"),"appPublisher Column column is not present in Application Inventory table.");
        softAssert.assertAll();
        LOGGER.info("App Inventory Dashboard table functionality validation has been completed successfully.");
    }

    @Test(priority = 38, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ" }, description = "Test Case: C57503930")
    public final void verifyAppInventoryDetailsDashboard() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        sleeper(3000);
        analyticsPage.clickOnElementsOfAnalyticsPage("applicationInventoryButton");
        waitForPageLoaded();
        analyticsPage.clickOnElementsOfAnalyticsPage("firstAppNameLinkApplicationInventoryDashboard");
        analyticsPage.verifyElementsOfAnalyticsPage("appInventoryDetailsPage");
        softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("lastSignedInUser"),"lastSignedInUser column is not present in Application Inventory details table.");
        softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("serialNumber"),"serialNumber column is not present in Application Inventory details table.");
        softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("appVersion"),"Installed Version Column column is not present in Application Inventory details table.");
        softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("appInstallDate"),"Installed On Column column is not present in Application Inventory details table.");
        softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("deviceName"),"deviceName Column column is not present in Application Inventory details table.");
        softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("deviceModels"),"deviceModel Column column is not present in Application Inventory details table.");
        softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("deviceManufacturer"),"deviceManufacturer Column column is not present in Application Inventory details table.");
        softAssert.assertAll();
        LOGGER.info("App Inventory Details Dashboard table functionality validation has been completed successfully.");
    }

    @Test(priority = 39, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ" }, description = "Test Case: C57503929", enabled=false)
    public final void verifyAppWithMostCrashesChartOnHomeDashboard() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        dexPage.companyView(CommonVariables.PARTNER_Home);
        waitForPageLoaded();
        softAssert.assertTrue(dexPage.getTextOfDEXPage("appsWithMostCrashesTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "applications.applications.with.most.crashes.title")),"Top Crashes title is not matching.");

        softAssert.assertTrue(dexPage.verifyNoOfCrashesFreezes("crashesProgressBars"),"Number of crashes are not visible on Home page.");
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("applicationMostCrashesNoDataMessage")) {
            dexPage.scrollToDEXPage("appsWithMostCrashesTitle");
            dexPage.clickByJavaScriptOnDEXPage("appMostCrashesBar");
            dexPage.waitForElementsOfDEXPage("versionsWithMostCrashes");
            //softAssert.assertTrue(dexPage.getTextOfDEXPage("installedappsWithMostCrashesTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "applications.installedAppScore.title")), "Top Crashes title is not matching on Application Experience Page.");
        }
        else{
            LOGGER.info("No data is present in Apps with most crashes chart.");
        }
        softAssert.assertAll();
        LOGGER.info("Application Top Crash chart on Home Dashboard functionality validation has been completed successfully.");
    }

    @Test(priority = 40, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ" }, description = "Test Case: C56673714 ")
    public final void verifyDevicesCountOnAppInventoryDetailsDashboard() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        sleeper(3000);
        analyticsPage.clickOnElementsOfAnalyticsPage("applicationInventoryButton");
        waitForPageLoaded();
        analyticsPage.verifyElementsOfAnalyticsPage("firstAppNameLinkApplicationInventoryDashboard");
        analyticsPage.verifyElementsOfAnalyticsPage("firstDeviceCounttextApplicationInventoryGrid");
        String firstAppDeviceCounttext = analyticsPage.getTextOfAnalyticsPage("firstDeviceCounttextApplicationInventoryGrid");
        int firstAppDeviceCount = Integer.parseInt(firstAppDeviceCounttext);
        String firstappVersionCount = analyticsPage.getTextOfAnalyticsPage("firstappVersionCount");
        analyticsPage.clickOnElementsOfAnalyticsPage("firstAppNameLinkApplicationInventoryDashboard");
        analyticsPage.verifyElementsOfAnalyticsPage("appInventoryDetailsPage");
        analyticsPage.verifyElementsOfAnalyticsPage("installedVersiontitle");
        sleeper(3000);
        softAssert.assertTrue(analyticsPage.getTextOfAnalyticsPage("installedVersiontitle").contains(firstappVersionCount),"text is not matching");
        softAssert.assertTrue(analyticsPage.verifyCountOfDevices(firstAppDeviceCount),"first count is not matching");
        String firstinstalledVersionDeviceCountText = analyticsPage.getTextOfAnalyticsPage("firstinstalledVersionDeviceCount");
        int firstinstalledVersionDeviceCount = Integer.parseInt(firstinstalledVersionDeviceCountText);
        analyticsPage.clickOnElementsOfAnalyticsPage("firstinstalledVersionDeviceCount");
        softAssert.assertTrue(analyticsPage.verifyCountOfDevices(firstinstalledVersionDeviceCount),"count is not matching");
        softAssert.assertAll();
        LOGGER.info("App Inventory Details Dashboard Devices counts functionality validation has been completed successfully.");
    }

    @Test(priority = 41, groups = { "REGRESSION_SCORE" }, description = "Test Case: 52149524")
    public final void verifyNetworkScoreChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        Assert.assertTrue(dexPage.verifyHomeNavigation("networkChevron","networkTitle"),"Navigation to Network tab failed");
        softAssert.assertTrue(dexPage.verifyNetworkScoreChart("nwGreatLegends","nwGreatLegendsCount"),"Network Score chart is not working for Great category.");
        softAssert.assertTrue(dexPage.verifyNetworkScoreChart("nwFairLegends","nwFairLegendsCount"),"Network Score chart is not working for Fair category.");
        softAssert.assertTrue(dexPage.verifyNetworkScoreChart("nwPoorLegends","nwPoorLegendsCount"),"Network Score chart is not working for Poor category.");
        softAssert.assertAll();
        LOGGER.info("Network Score chart functionality validation has been completed successfully.");
    }

    @Test(priority = 42, groups = { "REGRESSION_SCORE", "REGRESSION_INTEGRATIONQA_CUJ"},description = "Test Case:52300064,52301571 ")
    public final void verifyNetworkBarChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("networkChevron","networkTitle"),"Navigation to Network tab failed");
        softAssert.assertTrue(dexPage.verifyNetworkBarChart("topIssuesChartDataNW","topIssuesTooltip",LanguageCode), "NW Issues Chart is not working.");
        softAssert.assertAll();
        LOGGER.info("Network Bar Chart functionality validation has been completed successfully.");
    }

    @Test(priority = 43, groups = { "REGRESSION_SCORE" }, description = "Test Case ")
    public final void verifyFleetScorePreferenceTileHNetwork() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
        LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
        dexPage.companyView(CommonVariables.SETTINGS);
        if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            preferencesPage.verifyElementsOfPreferencesPage("preferenceTabold");
            preferencesPage.clickOnElementsOfPreferencesPage("preferenceTabold");
        }
        else {
            preferencesPage.verifyElementsOfPreferencesPage("preferenceTabNew");
            preferencesPage.clickOnElementsOfPreferencesPage("preferenceTabNew");
        }
        softAssert.assertTrue(dexPage.getTextOfDEXPage("fleetScoreTitle").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.fleetscorepreferences")),"Fleet Score Preference tile title is not correct.");
        softAssert.assertTrue(dexPage.verifyFleetScorePreferenceTile(LanguageCode,"editIconNWTile"),"Fleet Score Preference tile is not working correctly for Network.");
        Assert.assertTrue(dexPage.verifyHomeNavigation("networkChevron","networkTitle"),"Navigation to Network tab failed");
        sleeper(3000);
        softAssert.assertTrue(dexPage.getTextOfDEXPage("bannerNotification4").contains("Network's scoring preference was updated"),"Notification message is not coming on Exp mgmt page after Fleet Score Preference update.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("gearIcon"),"Settings gear icon is not present on Experience management page.");
        softAssert.assertTrue(dexPage.verifyClickAndValidateElement("gearIcon","fleetScorePreferences"),"Settings page did not load successfully from Experience Management page.");
//        dexPage.companyView(CommonVariables.LOGS);
//        waitForPageLoaded();
//        Assert.assertTrue(logPage.matchTextOfLogPage("logTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs page navigation failed");
//        LOGGER.info("Redirected to logs page");
//        resetTableConfiguration();
//        // Verify device name,type and sub type filter text
//        softAssert.assertTrue(logPage.matchTextOfLogPage("typeBoxText", "Experience Management"), "Selected Type column filter is not Experience Management in Logs");
//        softAssert.assertTrue(logPage.matchTextOfLogPage("subtypeBoxText", "DEX Score Modification"), "Selected Subtype column filter is not DEX Score Modification in Logs");
        softAssert.assertAll();
        LOGGER.info("Fleet Score Preference tile title validation has been completed successfully.");
    }

    @Test(priority = 44, enabled = true, groups = { "REGRESSION_APPLICATIONS_INSIGHTS" }, description = "Test Case:C57503900")
    public final void verifyWebAppScoreChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        Assert.assertTrue(dexPage.verifyHomeNavigationforApps("applicationChevron","webTab","webappTitle"),"Navigation to Web App tab failed");
        if(dexPage.waitForElementsOfDEXPage("webappScoreChartContent")) {
            Assert.assertTrue(dexPage.verifyElementsOfDEXPage("webappScoreChartContent"), "Web App Score chart is not loaded correctly");
            dexPage.clickOnElementsOfDEXPage("filtersButton");
            dexPage.mouseHoverclickOfDEXPage("timeFrame");
            dexPage.clickOnElementsOfDEXPage("timeFrame30days");
            dexPage.clickOnElementsOfDEXPage("applyButtonFilters");
            softAssert.assertTrue(dexPage.compareCategoryCounts("webappGreatLegendsCount", "webappFairLegendsCount", "webappPoorLegendsCount"), "Web App Score chart category counts is not working.");
        }
        else {
            Assert.assertTrue(dexPage.waitForElementsOfDEXPage("webAppScoreChartNoData"));
        }
        softAssert.assertAll();
        LOGGER.info("Web App Score chart functionality validation has been completed successfully.");
    }

    @Test(priority = 45,enabled = true, groups = { "REGRESSION_APPLICATIONS_INSIGHTS" }, description = "Test Case:C57503743")
    public final void verifyAppswithLongestPageLoadTimeChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        softAssert.assertTrue(dexPage.verifyHomeNavigationforApps("applicationChevron","webTab","appswithLongestPageLoadTimeTitle"),"Navigation to Web App tab failed");
        if(dexPage.waitForElementsOfDEXPage("listOfAppsInappswithLongestPageLoadTimeChart")) {
            softAssert.assertTrue(dexPage.verifyNoOfAppsInCharts("listOfAppsInappswithLongestPageLoadTimeChart"), "Apps with Longest Page Load Time chart is not having data");
            softAssert.assertTrue(dexPage.verifyCorrectOrderofAppsinCharts("listOfAppsInappswithLongestPageLoadTimeChart", "listOfLoadTimeInappswithLongestPageLoadTimeChart"), "Apps with Longest Page Load Time chart is not having data in correct order");
        }
        else{
            dexPage.waitForElementsOfDEXPage("webAppswithLongestPageLoadTimeNoData");
        }
        softAssert.assertAll();
        LOGGER.info("Apps with Longest Page Load Time chart functionality validation has been completed successfully.");
    }

    @Test(priority = 46,enabled = true, groups = { "REGRESSION_APPLICATIONS_INSIGHTS" }, description = "Test Case:C57503742")
    public final void verifyAppswithMostErrorsChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        softAssert.assertTrue(dexPage.verifyHomeNavigationforApps("applicationChevron","webTab","appswithMostErrorsTitle"),"Navigation to Web App tab failed");
        if(dexPage.waitForElementsOfDEXPage("listOfAppsInappswithMostErrorsChart")){
            softAssert.assertTrue(dexPage.verifyNoOfAppsInCharts("listOfAppsInappswithMostErrorsChart"), "Apps with Most Errors chart is not having data");
            softAssert.assertTrue(dexPage.verifyCorrectOrderofAppsinCharts("listOfAppsInappswithMostErrorsChart", "listOfErrorsOccurencesInappswithMostErrorsChart"), "Apps with Most Errors chart is not having data in correct order");
        }
        else{
            dexPage.waitForElementsOfDEXPage("webAppswithMostErrorsChartNoData");
        }
        softAssert.assertAll();
        LOGGER.info("Apps with MostErrors chart functionality validation has been completed successfully.");
    }

    @Test(priority = 47,enabled = true, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"},description = "Test Case:C57503737")
    public final void verifyWebApplicationExportFeature() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyHomeNavigationforApps("applicationChevron","webTab","webappTitle"),"Navigation to Web App tab failed");
        waitForPageLoaded();
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        if (analyticsPage.verifyElementsOfAnalyticsPage("exportButton")) {
            Assert.assertTrue(analyticsPage.verifyNewExportFunctionality(LanguageCode, "toastNotificationExportApplicationInventory"), "Export functionality is not working on Web Application table..");
            if(dexPage.verifyElementsOfDEXPage("firstwebAppNameLink")) {
                String firstAppName = dexPage.getTextOfDEXPage("firstwebAppNameLink");
                dexPage.clickOnElementsOfDEXPage("firstwebAppNameLink");
                dexPage.waitForElementsOfDEXPage("webappNameDetailsPage");
                sleeper(3000);
                Assert.assertTrue(firstAppName.equalsIgnoreCase(dexPage.getTextOfDEXPage("webappNameDetailsPage")), "Application name is not matching on Web Application page.");
                Assert.assertTrue(analyticsPage.verifyExportFunctionalityforApplicationInventory(LanguageCode, "toastNotificationExport"), "Export functionality is not working on Web Application details table..");
            }
        }
        else{
            Assert.assertTrue(dexPage.verifyElementsOfDEXPage("webAppGridNoData"));
            LOGGER.info("No data is present in Web Application grid.");
        }
        softAssert.assertAll();
        LOGGER.info("Export of Web Application score page functionality validation has been completed successfully.");
    }

    @Test(priority = 47,enabled = true, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"},description = "Test Case:C56673716")
    public final void verifyNavigationToWebAppConfig() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyHomeNavigationforApps("applicationChevron","webTab","webappTitle"),"Navigation to Web App tab failed");
        waitForPageLoaded();
        dexPage.verifyElementsOfDEXPage("ellipsesButton");
        dexPage.mouseHoverclickOfDEXPage("dashboardellipis");
        dexPage.clickOnElementsOfDEXPage("manageConfigButton");
        softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("webAppConfigTitle"),"Web App Config Page is not loaded correctly");
        sleeper(3000);
        dexPage.scrollToDEXPage("webAppConfigToggle");
        softAssert.assertTrue(dexPage.verifyElementIsSelectedOfDEXPage("webAppConfigToggle"),"Web App Config toggle is not enabled");
        softAssert.assertAll();
    }

    @Test(priority = 48,enabled = true, groups = { "REGRESSION_APPLICATIONS_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"},description = "Test Case:C57503703")
    public final void verifyTop5AppsinAppsWithMostErrorsOverTimeChart() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyHomeNavigationforApps("applicationChevron","webTab","appswithMostErrorsOverTimeTitle"),"Navigation to apps with Most Errors Over Time Title failed");
        if(dexPage.waitForElementsOfDEXPage("listofAppsInappswithMostErrorsOverTimeChart")) {
            softAssert.assertTrue(dexPage.validateAppsWithMostErrorsOvertimeChart("listOfAppsInappswithMostErrorsChart", "listofAppsInappswithMostErrorsOverTimeChart"), "top 5 apps in apps with Most Errors Over Time chart are not matching");
        }
        else{
            Assert.assertTrue(dexPage.waitForElementsOfDEXPage("webAppswithMostErrorsOverTimeNoData"));
            LOGGER.info("No data is present in Apps With Most Errors Over Time Chart.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 49, groups = { "REGRESSION_APPLICATIONS_INSIGHTS","PRODUCTION_SCORE" },description = "Test Case: C57503370")
    public final void verifyChartsLoadingApplicationsDashboard() throws Exception {
        login("ITADMIN_EMAIL_SCORE", "ITADMIN_PASSWORD_SCORE");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        if(!toggleVerification(CommonVariables.WX_INSTALLED_APP_NEW_UX, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            Assert.assertTrue(dexPage.verifyHomeNavigation("applicationChevron", "applicationTitle"), "Navigation to Application tab failed");
            waitForPageLoaded();
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"appCrashNoDataMessageOld","appCrashDataOld","Application Top Crashes"), "Application Top Crashes Chart is not loading on Experience Management page.");
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"appFreezeNoDataMessageOld","appFreezeDataOld","Application Top Freezes"), "Application Top Freezes Chart is not loading on Experience Management page.");
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"appCrashNoDataMessageOld","applicationScoreChartData","Application Score"), "Application Score Chart is not loading on Experience Management page.");
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"appCrashesAndFreezesOverTimeNoDataMessage","crashesFreezesOverTimeData","Crashes and Freezes Over Time"), "Crashes and Freezes Over Time Chart is not loading on Experience Management page.");
        }else {
            Assert.assertTrue(dexPage.verifyHomeNavigation("applicationChevron", "installedAppTitle"), "Navigation to Application tab failed");
            waitForPageLoaded();
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"installedappCrashNoDataMessage","installedappCrashData","Application Top Crashes"), "Application Top Crashes Chart is not loading on Experience Management page.");
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"installedappFreezeNoDataMessage","installedappFreezeData","Application Top Freezes"), "Application Top Freezes Chart is not loading on Experience Management page.");
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"","insAppScoreLegends","Application Score"), "Application Score Chart is not loading on Experience Management page.");
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"appCrashesAndFreezesOverTimeNoDataMessage","insappcrashesFreezesOverTimeData","Crashes and Freezes Over Time"), "Crashes and Freezes Over Time Chart is not loading on Experience Management page.");
            softAssert.assertTrue(dexPage.verifyDevicesAppScore(),"Devices are zero in App Score chart.");
        }
        softAssert.assertAll();
        LOGGER.info("Applications Dashboard Charts loading functionality validation has been completed successfully.");
    }

    @Test(priority = 50, groups = { "REGRESSION_APPLICATIONS_INSIGHTS","PRODUCTION_SCORE"},description = "Test Case: C56673700")
    public final void verifyGridLoadingApplicationsDashboard() throws Exception {
        login("ITADMIN_EMAIL_SCORE", "ITADMIN_PASSWORD_SCORE");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        if(!toggleVerification(CommonVariables.WX_INSTALLED_APP_NEW_UX, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            Assert.assertTrue(dexPage.verifyHomeNavigation("applicationChevron", "applicationTitle"), "Navigation to Application tab failed");
        }else {
            Assert.assertTrue(dexPage.verifyHomeNavigation("applicationChevron", "installedAppTitle"), "Navigation to Installed Application tab failed");
        }
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyAppsDashboardGridNavigation(LanguageCode),"Applications Dashboard Grid page did not load successfully.");
        softAssert.assertAll();
        LOGGER.info("Applications Dashboard Grid loading functionality validation has been completed successfully.");
    }
    @Test(priority = 51, enabled = true, groups = { "REGRESSION_SCORE", "REGRESSION_INTEGRATIONQA_CUJ"},description = "Test Case: 57241111")
    public final void verifyDEXAnalyticsNavigations() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        softAssert.assertTrue(dexPage.verifyClickAndValidateElement("healthLink","scoreTitleSysHealth"),"System Health page did not load successfully from Analytics page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"meterchartNoDataMessage","meterChartData","HW Meter Chart"), "Meter Chart on HW Tab are is not loading on Experience Management page.");
        // softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"summarychartNoDataMessage","donutchartCentreText","HW Summary Chart"), "Summary Chart on HW Tab are is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"topIssueschartNoDataMessage","topIssuesChartData","Top System Health Issues"), "Top Issues Chart on HW Tab are is not loading on Experience Management page.");
        dexPage.scrollUpCharts();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","totalpointsOnChart","HW Weekly Trend Chart"), "Weekly Trend Chart on HW Tab are is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"modelChartNoDataMessage","deviceByModelChartBarsLocator","HW Device Model Chart"), "Device Model Chart on HW Tab are is not loading on Experience Management page.");
        dexPage.scrollDownCharts();
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessage"))
        {
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode, "recommendedActionsNodata", "recommendedActionsContent", "Recommended Actions"), "Recommended Actions Chart on HW Tab are is not loading on Experience Management page.");
        }
        sleeper(2000);
        dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        softAssert.assertTrue(dexPage.verifyClickAndValidateElement("performanceLink","scoreTitleOSPerf"),"OS Performance page did not load successfully from Analytics page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"meterchartNoDataMessage","meterChartData","Perf Meter Chart"), "Meter Chart on Performance Tab is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"topIssueschartNoDataMessagePerf","topIssuesChartData","Top OS Performance Issues"), "Top Issues Chart on Performance Tab is not loading on Experience Management page.");
        dexPage.scrollUpCharts();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","totalpointsOnChart","Perf Weekly Trend Chart"), "Weekly Trend Chart on Performance Tab is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"modelChartNoDataMessageOSPerf","deviceByModelChartBarsLocator","Perf Device Model Chart"), "Device Model Chart on Performance Tab is not loading on Experience Management page.");
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessagePerf"))
        {
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode, "recommendedActionsNodata", "recommendedActionsContent", "Recommended Actions"), "Recommended Actions Chart on Performance Tab is not loading on Experience Management page.");
        }
        sleeper(2000);
        dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        softAssert.assertTrue(dexPage.verifyClickAndValidateElement("securityLink","scoreTitleSecurity"),"Security page did not load successfully from Analytics page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"meterchartNoDataMessage","meterChartData","Security Meter Chart"), "Meter Chart on Security Tab is not loading on Experience Management page.");
        //softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","donutchartCentreText","Security Summary Chart"), "Summary Chart on Security Tab is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"topIssueschartNoDataMessageSecurity","topIssuesChartData","Top Security Issues"), "Top Issues Chart on Security Tab is not loading on Experience Management page.");
        dexPage.scrollUpCharts();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","totalpointsOnChart","Security Weekly Trend Chart"), "Weekly Trend Chart on Security Tab is not loading on Experience Management page.");
        dexPage.scrollToDEXPage("deviceModelChartSecurity");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"modelChartNoDataMessageSecurity","deviceModelContentSecurity","Security Device Model Chart"), "Device Model Chart on Security Tab is not loading on Experience Management page.");
        //if (!dexPage.getTextOfDEXPage("topIssueschartNoDataMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.bios.action.everything_is_great")))
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssueschartNoDataMessageSecurity"))
        {
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode, "recommendedActionsNodata", "recommendedActionsContent", "Recommended Actions"), "Recommended Actions Chart on Security Tab is not loading on Experience Management page.");
        }
        sleeper(2000);
        dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        softAssert.assertTrue(dexPage.verifyClickAndValidateElement("networkLink","networkTitle"),"Network page did not load successfully from Analytics page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"","scoreChartContent","Network Meter Chart"), "Meter Chart on Security Tab is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"topIssuesNodataMessageNW","topIssuesChartDataNW","Top Network Issues"), "Top Issues Chart on Network Tab is not loading on Experience Management page.");
        dexPage.scrollUpCharts();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","totalpointsOnChart","Security Weekly Trend Chart"), "Weekly Trend Chart on Network Tab is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"nwAuthTypeNodataMessage","nwAuthTypeBars","NW Auth Type Chart"), "NW Auth Type Chart on Security Tab is not loading on Experience Management page.");
        if(!dexPage.waitForPresenceOfElementsOfDEXPage("topIssuesNodataMessageNW"))
        {
            softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode, "recommendedActionsNodata", "recommendedActionsContent", "Recommended Actions"), "Recommended Actions Chart on Security Tab is not loading on Experience Management page.");
        }
        softAssert.assertTrue(!dexPage.waitForElementsOfDEXPage("dexGridNoDataMessage"), "NW Grid is not loading on Experience Management page.");
        softAssert.assertAll();

    }

    @Test(priority = 52, enabled = true, groups = { "REGRESSION_SCORE"},description = "Test Case: 57241365")
    public final void verifyDEXExpScoreNavigations() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"","expScoreContent","Exp Score Chart"), "Exp Score Chart on Exp Score Page is not loading.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","expScoreTrendChartContent","Exp Score Trend Chart"), "Weekly Trend Chart on HW Tab are is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"meterchartNoDataMessage","meterChartData","HW Meter Chart"), "Meter Chart on HW Tab are is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","totalpointsOnChart","HW Weekly Trend Chart"), "Weekly Trend Chart on HW Tab are is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"meterchartNoDataMessage","meterChartData","Perf Meter Chart"), "Meter Chart on Performance Tab is not loading on Experience Management page.");
        dexPage.scrollUpCharts();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","totalpointsOnChart","Perf Weekly Trend Chart"), "Weekly Trend Chart on Performance Tab is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"meterchartNoDataMessage","meterChartData","Security Meter Chart"), "Meter Chart on Security Tab is not loading on Experience Management page.");
        dexPage.scrollUpCharts();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","totalpointsOnChart","Security Weekly Trend Chart"), "Weekly Trend Chart on Security Tab is not loading on Experience Management page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"","scoreChartContent","Network Meter Chart"), "Meter Chart on Network Tab is not loading on Experience Management page.");
        softAssert.assertAll();
        LOGGER.info("Navigation from Analytics to Exp Score page validation has been completed successfully.");

    }

    @Test(priority = 53, groups = { "REGRESSION_APPLICATIONS_INSIGHTS"},description = "Test Case:C58305314 ")
    public final void verifyWebAppsErrorsTableOnDeviceDetailsPage() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        dexPage.scrollToDEXPage("systemHealthOverTimetitle");
        Assert.assertTrue(dexPage.verifyDeviceDetailNavigation(LanguageCode),"Device Details page did not load from Exp Mgmt Page.");
        sleeper(5000);
        dexPage.clickOnElementsOfDEXPage("healthandProtectionTabDeviceDetails");
        waitForPageLoaded();
        dexPage.scrollToDEXPage("webApplicationErrorsTitle");
        softAssert.assertTrue((dexPage.getTextOfDEXPage("webApplicationErrorsTitle").equals(dexPage.getTextLanguage(LanguageCode, "daas_ui", "web.application.device.details.web_application_error.title"))),"Web Application Errors title text is incorrect on Device Details Page.");
        //softAssert.assertTrue((dexPage.getTextOfDEXPage("webApplicationErrorsSubtitle").equals(dexPage.getTextLanguage(LanguageCode, "daas_ui", "web.application.device.details.web_application_error.description"))),"Web Application Errors subtitle text is incorrect on Device Details Page.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("dateofErrorColumn"),"date of error column is not present in Web Apps Errors table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("ErrorTypeColumn"),"Error Type column is not present in Web Apps Errors table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("appNameColumn"),"app Name column is not present in Web Apps Errors table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("domainURLColumn"),"domain URL Column is not present in Web Apps Errors table.");
        softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("browserColumn"),"browser Column is not present in Web Apps Errors table.");
        softAssert.assertAll();
        LOGGER.info("Web Apps Errors Table On Device Details Page validation has been completed successfully.");
    }

    @Test(priority = 54, groups = { "REGRESSION_SCORE"},description = "Test Case: ")
    public final void verifyDeviceExpMgmtExportAllDEX() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyExportFunctionalityAllDEX(LanguageCode,"toastNotificationExport","exportButtonDexPage"),"Export all DEX functionality is not working.");

        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyExportFunctionalityAllDEX(LanguageCode,"toastNotificationExport","exportButtonDexPage"),"Export functionality is not working.");

        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyExportFunctionalityAllDEX(LanguageCode,"toastNotificationExport","exportButtonDexPage"),"Export functionality is not working.");

        Assert.assertTrue(dexPage.verifyHomeNavigation("networkChevron","networkTitle"),"Navigation to Network tab failed");
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyExportFunctionalityAllDEX(LanguageCode,"toastNotificationExport","exportButtonNWPage"),"Export functionality is not working.");

        softAssert.assertAll();
        LOGGER.info("Export of All DDEX functionality validation has been completed successfully.");
    }
    @Test(priority = 55, groups = { "REGRESSION_SCORE"},description = "Test Case: 58737872")
    public final void verifyExpScoreStandardPlan() throws Exception {
        login("WXP_STANDARD_COMPANY_EMAIL", "WXP_STANDARD_COMPANY_PASSWORD");
        final String[] network_Coulmn = {"exp.mgmt.details.NetworkScore","exp.mgmt.details.avgNominalSpeed","exp.mgmt.details.minNominalSpeed","exp.mgmt.details.badNominalSpeedDuration","exp.mgmt.details.avgSignalStrength","exp.mgmt.details.minSignalStrength","exp.mgmt.details.badSignalStrengthDuration","exp.mgmt.details.totalDisconnectionCount","exp.mgmt.details.unsecureNetworkCount"};
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        sleeper(3000);
        dexPage.companyView(CommonVariables.CUSTOMER_HOME);
        sleeper(2000);
        dexPage.clickOnElementsOfDEXPage("infoIconExpScore");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText1").equalsIgnoreCase("System Health (40%):"+" "+dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_wex_score_para1_description5")),"About Score text 1 is not correct.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText2").equalsIgnoreCase("OS Performance (40%):"+" "+dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_wex_score_para1_description")),"About Score text 2 is not correct.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText3").equalsIgnoreCase("Security (20%):"+" "+dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_para1_description5")),"About Score text 3 is not correct.");
        pressKey(Keys.ESCAPE);
        Assert.assertTrue(dexPage.getTextOfDEXPage("sentimentUpgrade").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "global.upgrade")),"Sentiment Upgrade title is not correct.");
        Assert.assertTrue(dexPage.getTextOfDEXPage("networkUpgrade").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "global.upgrade")),"Network Upgrade title is not correct.");
        Assert.assertTrue(dexPage.getTextOfDEXPage("applicationUpgrade").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "global.upgrade")),"Application Upgrade title is not correct.");
        Assert.assertTrue(dexPage.getTextOfDEXPage("collaborationAddOn").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "global.addOn")),"Collaboration Upgrade title is not correct.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("systemHealthScore"),"System Health Score is not present on Exp Score Chart for Standard plan tenant.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("osPerformanceScore"),"OS Performance Score is not present on Exp Score Chart for Standard plan tenant.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("securityScore"),"Security Score is not present on Exp Score Chart for Standard plan tenant.");
        Assert.assertTrue(dexPage.verifyUpgradeButton("sentimentUpgrade"), "Sentiment Upgrade button is not present for Standard plan tenant.");
        Assert.assertTrue(dexPage.verifyUpgradeButton("networkUpgrade"), "Network Upgrade button is not present for Standard plan tenant.");
        Assert.assertTrue(dexPage.verifyUpgradeButton("applicationUpgrade"), "Application Upgrade button is not present for Standard plan tenant.");
        Assert.assertTrue(dexPage.verifyAddOnButton("collaborationAddOn"), "Collaboration Add On button is not present for Standard plan tenant.");

        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        dexPage.scrollToDEXPage("systemHealthOverTimetitle");
        Assert.assertTrue(dexPage.verifyExpmgmtGridNavigation(LanguageCode),"Experience Management Grid page did not load successfully.");
        resetTableConfiguration();
        softAssert.assertTrue(dexPage.verifyHeaderAsPerPlanExpMgmtGridPage(LanguageCode,"columnListGridPage",network_Coulmn),"Network columns found for Standard plan tenant are not correct.");
        dexPage.companyView(CommonVariables.SETTINGS);
        if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            preferencesPage.verifyElementsOfPreferencesPage("preferenceTabold");
            preferencesPage.clickOnElementsOfPreferencesPage("preferenceTabold");
        }
        else {
            preferencesPage.verifyElementsOfPreferencesPage("preferenceTabNew");
            preferencesPage.clickOnElementsOfPreferencesPage("preferenceTabNew");
        }
        softAssert.assertTrue(dexPage.getTextOfDEXPage("fleetScoreTitle").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.fleetscorepreferences")),"Fleet Score Preference tile title is not correct.");
        softAssert.assertTrue(!dexPage.waitForPresenceOfElementsOfDEXPage("editIconNWTile"),"Fleet Score Preference tile for NW is present for Standard plan tenant.");

        softAssert.assertAll();
        LOGGER.info("Standard tenant DEX functionality validation has been completed successfully.");
    }

    @Test(priority = 56, groups = { "REGRESSION_SCORE"},description = "Test Case: 58737873,58737874")
    public final void verifyExpScoreProPlan() throws Exception {
        login("WXP_PRO_COMPANY_EMAIL", "WXP_PRO_COMPANY_PASSWORD");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue((dexPage.waitForPresenceOfElementsOfDEXPage("sentimentScore") || dexPage.waitForPresenceOfElementsOfDEXPage("sentimentWaiting")),"Sentiment Score is not present on Exp Score Chart for Pro plan tenant.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("networkScoreExp"),"Network Health Score is not present on Exp Score Chart for Pro plan tenant.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("applicationScoreExp"),"Application Upgrade title is not correct.");
        Assert.assertTrue(dexPage.getTextOfDEXPage("collaborationAddOn").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "global.addOn")),"Collaboration Upgrade title is not correct.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("systemHealthScore"),"System Health Score is not present on Exp Score Chart for Pro plan tenant.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("osPerformanceScore"),"OS Performance Score is not present on Exp Score Chart for Pro plan tenant.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("securityScore"),"Security Score is not present on Exp Score Chart for Pro plan tenant.");
        dexPage.clickOnElementsOfDEXPage("infoIconExpScore");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText1").contains(dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_wex_score_para1_description5")),"About Score text 1 is not correct.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText2").contains(dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_wex_score_para1_description")),"About Score text 2 is not correct.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText3").contains(dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_para1_network_health_description1")),"About Score text 3 is not correct.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText4").contains(dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_para1_description5")),"About Score text 4 is not correct.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText5").contains(dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_para1_description6")),"About Score text 5 is not correct.");
        pressKey(Keys.ESCAPE);
        softAssert.assertAll();
        LOGGER.info("Pro tenant DEX functionality validation has been completed successfully.");
    }

    @Test(priority = 57, groups = { "REGRESSION_SCORE"},description = "Test Case: 58737873,58737874")
    public final void verifyExpScoreElitePlan() throws Exception {
        login("WXP_ELITE_COMPANY_EMAIL", "WXP_ELITE_COMPANY_PASSWORD");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue((dexPage.waitForPresenceOfElementsOfDEXPage("sentimentScore") || dexPage.waitForPresenceOfElementsOfDEXPage("sentimentWaiting")),"Sentiment Score is not present on Exp Score Chart for Elite plan tenant.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("networkScoreExp"),"Network Health Score is not present on Exp Score Chart for Elite plan tenant.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("applicationScoreExp"),"Application Upgrade title is not correct.");
        Assert.assertTrue(dexPage.getTextOfDEXPage("collaborationAddOn").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "global.addOn")),"Collaboration Upgrade title is not correct.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("systemHealthScore"),"System Health Score is not present on Exp Score Chart for Elite plan tenant.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("osPerformanceScore"),"OS Performance Score is not present on Exp Score Chart for Elite plan tenant.");
        Assert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("securityScore"),"Security Score is not present on Exp Score Chart for Elite plan tenant.");
        dexPage.clickOnElementsOfDEXPage("infoIconExpScore");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText1").contains(dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_wex_score_para1_description5")),"About Score text 1 is not correct.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText2").contains(dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_wex_score_para1_description")),"About Score text 2 is not correct.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText3").contains(dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_para1_network_health_description1")),"About Score text 3 is not correct.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText4").contains(dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_para1_description5")),"About Score text 4 is not correct.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("expScoreText5").contains(dexPage.getTextLanguage(LanguageCode, "daas_ui", "ee_experienceScore_para1_description6")),"About Score text 5 is not correct.");
        pressKey(Keys.ESCAPE);
        softAssert.assertAll();
        LOGGER.info("Elite tenant DEX functionality validation has been completed successfully.");
    }

    @Test(priority = 58, groups = { "REGRESSION_SCORE"},description = "Test Case: 58737869")
    public final void verifyExpScoreStarterPlan() throws Exception {
        login("WXP_STARTER_COMPANY_EMAIL", "WXP_STARTER_COMPANY_PASSWORD");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
//        softAssert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPageDynamic("disabledExpScoreContent",1),"Experience Score Chart being displayed for a Starter plan tenant.");
//        softAssert.assertFalse(dexPage.waitForPresenceOfElementsOfDEXPageDynamic("expScoreOverTimeContent",1),"Experience Score Overtime Chart being displayed for a Starter plan tenant.");
        dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        softAssert.assertFalse(dexPage.waitForPresenceOfElementsOfDEXPageDynamic("healthLink",1),"System health being displayed for a Starter plan tenant.");
        softAssert.assertFalse(dexPage.waitForPresenceOfElementsOfDEXPageDynamic("networkLink",1),"Network being displayed for a Starter plan tenant.");
        softAssert.assertFalse(dexPage.waitForPresenceOfElementsOfDEXPageDynamic("performanceLink",1),"OS Performance being displayed for a Starter plan tenant.");
        softAssert.assertFalse(dexPage.waitForPresenceOfElementsOfDEXPageDynamic("securityLink",1),"Security being displayed for a Starter plan tenant.");
        softAssert.assertFalse(dexPage.waitForPresenceOfElementsOfDEXPageDynamic("applicationLink",1),"Application exp being displayed for a Starter plan tenant.");
        softAssert.assertAll();
        LOGGER.info("Starter tenant DEX functionality validation has been completed successfully.");
    }

    @Test(priority = 59, groups = { "REGRESSION_SCORE"},description = "Test Case: ")
    public final void verifySubScoreCustomizeUI() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        sleeper(2000);
        String overallScore = dexPage.getTextOfDEXPage("overallScoreExpScoreChart");
        LOGGER.info("Overall Score on Exp Score Chart is: " + overallScore);
        dexPage.clickOnElementsOfDEXPage("customizeButton");
        Assert.assertTrue(dexPage.getTextOfDEXPage("customizeTitle").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "exp.mgmt.score_customize.title")),"Customize Sub Score title is not matching with expected.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("customizeSubtitle").trim().equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "exp.mgmt.score_customize.instruction").trim()),"Customize Sub Score subtitle is not matching with expected.");
        sleeper(4000);
        softAssert.assertTrue(overallScore.equalsIgnoreCase(dexPage.getTextOfDEXPage("currentScore")),"Overall Score on Exp Score Chart is not matching with Current Score on Sub Score Score Chart.");
        LOGGER.info("Current Score on Sub Score Chart is: " + dexPage.getTextOfDEXPage("currentScore"));

        softAssert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("systemHealthScoreSub"),"System Health Score is not present under Sub Score Chart.");
        softAssert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("osPerformanceScoreSub"),"OS Performance Score is not present under Sub Score Chart.");
        softAssert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("securityScoreSub"),"Security Score is not present under Sub Score Chart.");
        softAssert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("networkScoreSub"),"Network Score is not present under Sub Score Chart.");
        softAssert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("applicationScoreSub"),"Application Score is not present under Sub Score Chart.");
        softAssert.assertTrue(dexPage.waitForPresenceOfElementsOfDEXPage("sentimentScoreSub"),"Sentiment Score is not present under Sub Score Chart.");

        dexPage.clickOnElementsOfDEXPage("helpButtonSub");
        String actualText = dexPage.getTextOfDEXPage("helpText");
        String paraa1 = dexPage.getTextLanguage(LanguageCode, "daas_ui", "wex.sidebar.customization_description");
        String paraa2 = dexPage.getTextLanguage(LanguageCode, "daas_ui", "wex.sidebar.sub_score_zero_description");
        String line1 = dexPage.getTextLanguage(LanguageCode, "daas_ui", "wex.sidebar.exclude_from_overall");
        String line2 = dexPage.getTextLanguage(LanguageCode, "daas_ui", "wex.sidebar.remove_from_recommended");
        String line3 = dexPage.getTextLanguage(LanguageCode, "daas_ui", "wex.sidebar.prevent_benchmark");
        String paraa3 =dexPage.getTextLanguage(LanguageCode, "daas_ui", "wex.sidebar.allow_changes_effect");
        String expectedText = paraa1 + "\n\n" + paraa2 + "\n" + line1 + "\n" + line2 + "\n" + line3 + "\n\n" + paraa3;
        softAssert.assertTrue(actualText.equalsIgnoreCase(expectedText),"Help text is not matching with expected.");

        dexPage.clickOnElementsOfDEXPage("expScorePrefLink");
        dexPage.switchToDifferentTabOfDEXPage();
        softAssert.assertTrue(dexPage.getTextOfDEXPage("fleetScoreTitle").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.fleetscorepreferences")),"Fleet Score Preference tile title is not correct.");
        dexPage.clickOnElementsOfDEXPage("editIconScoreWeightTile");
        Assert.assertTrue(dexPage.getTextOfDEXPage("customizeTitle").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "exp.mgmt.score_customize.title")),"Customize Sub Score title is not matching with expected.");
        dexPage.switchToPreviousTabOfDEXPage();

        dexPage.clickOnElementsOfDEXPage("closeButtonHelp");
        dexPage.clickOnElementsOfDEXPage("cancelButtonSub");

        LOGGER.info("UI validation of Customize Experience Score has been completed successfully.");
        softAssert.assertAll();
    }

    @Test(priority = 60, groups = { "REGRESSION_SCORE"},description = "Test Case: ")
    public final void verifySubScoreCustomizeFunctionality() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        sleeper(2000);
        String overallScore = dexPage.getTextOfDEXPage("overallScoreExpScoreChart");
        LOGGER.info("Overall Score on Exp Score Chart is: " + overallScore);
        dexPage.clickOnElementsOfDEXPage("customizeButton");
        Assert.assertTrue(dexPage.getTextOfDEXPage("customizeTitle").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "exp.mgmt.score_customize.title")),"Customize Sub Score title is not matching with expected.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("customizeSubtitle").trim().equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "exp.mgmt.score_customize.instruction").trim()),"Customize Sub Score subtitle is not matching with expected.");
        sleeper(4000);
        softAssert.assertTrue(overallScore.equalsIgnoreCase(dexPage.getTextOfDEXPage("currentScore")),"Overall Score on Exp Score Chart is not matching with Current Score on Sub Score Score Chart.");
        LOGGER.info("Current Score on Sub Score Chart is: " + dexPage.getTextOfDEXPage("currentScore"));
        softAssert.assertTrue(dexPage.verifyCustomizeExpScore("Pro/Elite"),"Customize Experience Score functionality is not working as expected.");
        LOGGER.info("UI validation of Customize Experience Score has been completed successfully.");
        softAssert.assertAll();
    }

    @Test(priority = 61, groups = { "REGRESSION_SCORE"},description = "Test Case: ")
    public final void verifySubScoreCustomizeFunctionalityStdPlan() throws Exception {
        login("WXP_STANDARD_COMPANY_EMAIL", "WXP_STANDARD_COMPANY_PASSWORD");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        sleeper(2000);
        String overallScore = dexPage.getTextOfDEXPage("overallScoreExpScoreChart");
        LOGGER.info("Overall Score on Exp Score Chart is: " + overallScore);
        dexPage.clickOnElementsOfDEXPage("customizeButton");
        Assert.assertTrue(dexPage.getTextOfDEXPage("customizeTitle").equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "exp.mgmt.score_customize.title")),"Customize Sub Score title is not matching with expected.");
        softAssert.assertTrue(dexPage.getTextOfDEXPage("customizeSubtitle").trim().equalsIgnoreCase(dexPage.getTextLanguage(LanguageCode, "daas_ui", "exp.mgmt.score_customize.instruction").trim()),"Customize Sub Score subtitle is not matching with expected.");
        sleeper(4000);
        softAssert.assertTrue(overallScore.equalsIgnoreCase(dexPage.getTextOfDEXPage("currentScore")),"Overall Score on Exp Score Chart is not matching with Current Score on Sub Score Score Chart.");
        LOGGER.info("Current Score on Sub Score Chart is: " + dexPage.getTextOfDEXPage("currentScore"));
        softAssert.assertTrue(dexPage.verifyCustomizeExpScore("Standard"),"Customize Experience Score functionality is not working as expected.");
        LOGGER.info("UI validation of Customize Experience Score has been completed successfully.");
        softAssert.assertAll();
    }

    @Test(priority =62, enabled = true, groups = { "REGRESSION_SCORE"},description = "Test Case: ")
    public final void verifyViewAllRecommendedActionsTile() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("systemHealthChevron","scoreTitleSysHealth"),"Navigation to System Health tab failed");
        waitForPageLoaded();
        if(dexPage.waitForPresenceOfElementsOfDEXPage("viewAllRAsButton")) {
            dexPage.clickOnElementsOfDEXPage("viewAllRAsButton");
            softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("recommendedActionsBreadCrumb"), "DEX Rating filter is not working on HW Tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in System Health Tab.");
        }
        Assert.assertTrue(dexPage.verifyHomeNavigation("osPerformanceChevron","scoreTitleOSPerf"),"Navigation to OS Performance tab failed");
        if(dexPage.waitForPresenceOfElementsOfDEXPage("viewAllRAsButton")) {
            dexPage.clickOnElementsOfDEXPage("viewAllRAsButton");
            softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("recommendedActionsBreadCrumb"), "DEX Rating filter is not working on HW Tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in OS Perf Tab.");
        }
        Assert.assertTrue(dexPage.verifyHomeNavigation("securityChevron","scoreTitleSecurity"),"Navigation to Security tab failed");
        if(dexPage.waitForPresenceOfElementsOfDEXPage("viewAllRAsButton")) {
            dexPage.clickOnElementsOfDEXPage("viewAllRAsButton");
            softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("recommendedActionsBreadCrumb"), "DEX Rating filter is not working on HW Tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in Security Tab.");
        }
        softAssert.assertTrue(dexPage.verifyHomeNavigation("networkChevron","networkTitle"),"Navigation to Network tab failed");
        if(dexPage.waitForPresenceOfElementsOfDEXPage("viewAllRAsButton")) {
            dexPage.clickOnElementsOfDEXPage("viewAllRAsButton");
            softAssert.assertTrue(dexPage.waitForElementsOfDEXPage("recommendedActionsBreadCrumb"), "DEX Rating filter is not working on HW Tab.");
        } else{
            LOGGER.info("No data found in Recommended Actions tile in Network Tab.");
        }
        softAssert.assertAll();
        LOGGER.info("View all Recommended Actions validation has been completed successfully.");
    }

    @Test(priority = 63, groups = { "REGRESSION_APPLICATIONS_INSIGHTS","PRODUCTION_SCORE" },description = "Test Case: ")
    public final void verifyChartsLoadingWEBApplicationsDashboard() throws Exception {
        login("ITADMIN_EMAIL_SCORE", "ITADMIN_PASSWORD_SCORE");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyHomeNavigation("applicationChevron", "webTab"), "Navigation to Application tab failed");
        waitForPageLoaded();
        dexPage.clickOnElementsOfDEXPage("webTab");
        waitForPageLoaded();
        Assert.assertTrue(dexPage.verifyElementsOfDEXPage("webappTitle"), "Navigation to Web Application tab failed");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"webAppScoreChartNoData","webappScoreChartContent","Web Applications Score"), "Web Applications Score Chart is not loading on Application dashboard page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"webAppswithLongestPageLoadTimeNoData","listOfAppsInappswithLongestPageLoadTimeChart","Apps with Longest Page Load Time"), "Apps with Longest Page Load Time Chart is not loading on Application dashboard page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"webAppswithMostErrorsChartNoData","listOfAppsInappswithMostErrorsChart","Apps with Most Errors"), "Apps with Most Errors Chart is not loading on Application dashboard page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"webAppswithMostErrorsOverTimeNoData","listofAppsInappswithMostErrorsOverTimeChart","Apps with Most Errors Over Time"), "Apps with Most Errors Over Time Chart is not loading on Application dashboard page.");
        softAssert.assertAll();
        LOGGER.info("Web Applications Dashboard Charts loading functionality validation has been completed successfully.");
    }

    @Test(priority = 64, groups = { "REGRESSION_APPLICATIONS_INSIGHTS","PRODUCTION_SCORE"},description = "Test Case: ")
    public final void verifyGridLoadingWebApplicationsDashboard() throws Exception {
        login("ITADMIN_EMAIL_SCORE", "ITADMIN_PASSWORD_SCORE");
        SoftAssert softAssert = new SoftAssert();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        Assert.assertTrue(dexPage.verifyHomeNavigation("applicationChevron", "webTab"), "Navigation to Installed Application tab failed");
        waitForPageLoaded();
        dexPage.clickOnElementsOfDEXPage("webTab");
        Assert.assertTrue(dexPage.verifyElementsOfDEXPage("webappTitle"), "Navigation to Web Application tab failed");
        if(dexPage.waitForElementsOfDEXPage("firstwebAppNameLink")) {
            Assert.assertTrue(dexPage.verifyElementsOfDEXPage("firstwebAppNameLink"), "WEB Applications Dashboard Grid page did not load successfully.");
        }
        else {
            Assert.assertTrue(dexPage.verifyElementsOfDEXPage("webAppGridNoData"));
            LOGGER.info("No data is present in Web Application grid.");
        }
            softAssert.assertAll();
        LOGGER.info("Web Applications Dashboard Grid loading functionality validation has been completed successfully.");
    }

    @Test(priority = 65, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"})
   	public final void verifytsystemHealthReportAnalyticsListPage() throws Exception {
   		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   		SoftAssert softAssert = new SoftAssert();
   		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
   		DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
   		leftSideMenuVerification();
   		dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   		waitForPageLoaded();
   		Assert.assertTrue(dexPage.getTextOfDEXPage("analytics").equals(dexPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
   				"Analytics title doesn't match");
   		LOGGER.info("Analytics listing page opened correctly");
   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("systemHealthScore1"),"System health score report missing.");
   		Thread.sleep(2000);
   		dexPage.clickOnElementsOfDEXPage("systemHealthScore1");
   		waitForPageLoaded();
        dexPage.validateSystemHealthSummaryChart(softAssert, LanguageCode);
        waitForPageLoaded();

        softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("systemHealthScore1"),"System health score report missing.");
   		Thread.sleep(2000);
   		dexPage.clickOnElementsOfDEXPage("systemHealthScore1");
   		waitForPageLoaded();
        LOGGER.info("Clicked on System health score widget");
   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("topSystemHealthIssue"),"top System HealthIssue report missing.");
	   	waitForPageLoaded();
	   	Thread.sleep(2000);
   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("systemHealthByModel"),"system Health By Model report missing.");
   		waitForPageLoaded();
   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("changesSinceLastWeekChartTitleSystemHealth"),"changes Since Last Week Chart Title SystemHealth chart is missing.");
   		waitForPageLoaded();
   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("systemHealthOverTimetitle"),"systemHealthOverTime Summary report missing.");
   		waitForPageLoaded();
   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("sysRecommendationAction"),"sysRecommendationAction Summary report missing.");
       	LOGGER.info("System health score validation completed");
   		softAssert.assertAll();
   	}



    /**
	 * This test case verifies the os Performance score widget functionality .
	 */
    @Test(priority = 67, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"})
   	public final void verifytOSPerformanceReportAnalyticsListPage() throws Exception {
	   		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	   		SoftAssert softAssert = new SoftAssert();
	   		String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
	   		DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
	   		leftSideMenuVerification();
	   		dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
	   		waitForPageLoaded();
	   		Assert.assertTrue(dexPage.getTextOfDEXPage("analytics").equals(dexPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
	   				"Analytics title doesn't match");
	   		LOGGER.info("Analytics listing page opened correctly");

	   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("osPerformanceScore1"),"System health score report missing.");
	   		Thread.sleep(2000);
	   		dexPage.clickOnElementsOfDEXPage("osPerformanceScore1");
	   		waitForPageLoaded();
	   		dexPage.validateOSPerformanceSummaryChart(softAssert, LanguageCode);
	        waitForPageLoaded();
	        softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("osPerformanceScore1"),"System health score report missing.");
	   		Thread.sleep(2000);
	   		dexPage.clickOnElementsOfDEXPage("osPerformanceScore1");
	   		waitForPageLoaded();
	   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("topOSPerformanceIssues"),"top OS Performance Issues report missing.");
		   	waitForPageLoaded();
		   	Thread.sleep(2000);
	   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("osPerformanceByModel"),"os Performance By Model report missing.");
	   		waitForPageLoaded();
	   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("oSPerformanceOverTime"),"os Performance Over Time report missing.");
	   		waitForPageLoaded();
	   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("sysRecommendationAction"),"os Performance Recommendation Action missing.");
	   		waitForPageLoaded();
	   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("changesSinceLastWeekChartTitleOSandSystemReliabilty"),"changes Since Last Week Chart Title OS chart is missing.");
	   		LOGGER.info("os Performance score validation completed");
	   		softAssert.assertAll();
   	}

     /**
   	 * This test case verifies the security score widget functionality .
   	 */
    @Test(priority = 68, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"})
    public final void verifytSecurityScoreReportAnalyticsListPage() throws Exception {
      		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
      		SoftAssert softAssert = new SoftAssert();
      		String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
      		DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
      		leftSideMenuVerification();
      		dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
      		waitForPageLoaded();
      		Assert.assertTrue(dexPage.getTextOfDEXPage("analytics").equals(dexPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
      				"Analytics title doesn't match");
      		LOGGER.info("Analytics listing page opened correctly");
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("securityScore1"),"Security Score report missing.");
      		Thread.sleep(2000);
      		dexPage.clickOnElementsOfDEXPage("securityScore1");
      		waitForPageLoaded();
     		dexPage.validateSecurityScoreChart(softAssert, LanguageCode);
            waitForPageLoaded();
            softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("securityScore1"),"Security Score report missing.");
      		Thread.sleep(2000);
      		dexPage.clickOnElementsOfDEXPage("securityScore1");
      		waitForPageLoaded();
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("topSecurityIssues"),"top Security Issues chart missing.");
    		waitForPageLoaded();
    		Thread.sleep(2000);
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("securityByModel"),"Security By Model report missing.");
      		waitForPageLoaded();
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("securityOverTime"),"security Over Time Summary report missing.");
      		waitForPageLoaded();
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("changesSinceLastWeekChartTitleSecurity"),"changes Since Last Week Chart Title Security is missing.");
      		waitForPageLoaded();
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("sequrityRecommendationAction"),"sysRecommendationAction Summary report missing.");
      		softAssert.assertAll();
     }

    /**
   	 * This test case verifies the Network Health Score Report widget functionality .
   	 */
     @Test(priority = 69, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"})
     public final void verifytNetworkHealthScoreReportAnalyticsListPage() throws Exception {
      		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
      		SoftAssert softAssert = new SoftAssert();
      		String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
      		DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
      		leftSideMenuVerification();
      		dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
      		waitForPageLoaded();
      		Assert.assertTrue(dexPage.getTextOfDEXPage("analytics").equals(dexPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
      				"Analytics title doesn't match");
      		LOGGER.info("Analytics listing page opened correctly");
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("networkScore"),"network Health score report missing.");
      		Thread.sleep(2000);
      		dexPage.clickOnElementsOfDEXPage("networkScore");
      		waitForPageLoaded();
      		Thread.sleep(2000);
      		softAssert.assertTrue(dexPage.verifyAllNetworkScoreCharts(LanguageCode), "Network Score charts validation failed.");
      		waitForPageLoaded();
	   		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("networkAuthenticationTypes"),"Network Authentication Types report missing.");
	   		waitForPageLoaded();
	   		LOGGER.info("Network Health Score Report validation completed");
	   		softAssert.assertAll();

     }

     /**
      * This test case verifies the Exp Score Dashboard functionality .
      */
     @Test(priority = 70, enabled = true, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
     public final void verifyDEXExpScoreDashboard() throws Exception {
           login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
           String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
           SoftAssert softAssert = new SoftAssert();
           DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
           dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
           waitForPageLoaded();

           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           waitForPageLoaded();
           dexPage.scrollToDEXPage("expscoredbSystemhealthScore");
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbSystemhealthScore"),"systemHealthScores Summary report missing.");
           Assert.assertTrue(dexPage.verifyElementsOfDEXPage("scoreChartData"),"System Health Summary Chart is not loading.");
           Assert.assertTrue(dexPage.verifyLDKDDEXScoreCharts("sysHealthscoreChartBarsLocatordb"), "System Health Summary Chart is not working.");
           Thread.sleep(2000);
           LOGGER.info("System Health Summary Chart is working fine.");
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           waitForPageLoaded();
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbOSPerfScore"),"os Performance Summary report missing.");
           Assert.assertTrue(dexPage.verifyElementsOfDEXPage("scoreChartData"),"os Performance Summary Chart is not loading.");
           Assert.assertTrue(dexPage.verifyLDKDDEXScoreCharts("osPerfscoreChartBarsLocatordb"), "os Performance Summary Chart is not working.");
           waitForPageLoaded();
           LOGGER.info("OS Performance Summary Chart is working fine.");
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           waitForPageLoaded();
           dexPage.scrollToDEXPage("expscoredbSceurityOScore");
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbSceurityOScore"), "Security Score report missing.");
           softAssert.assertTrue(dexPage.getTextOfDEXPage("expscoredbSecurityScore").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "exp.mgmt.details.securityScore")),
               "Security Score Chart title is incorrect.");
           Assert.assertTrue(dexPage.verifyElementsOfDEXPage("scoreChartData"), "Security Score Chart is not loading.");
           Assert.assertTrue(dexPage.verifyLDKDDEXScoreCharts("securityBarsLocatordb"), "Security Score Chart is not working.");
           waitForPageLoaded();
	   	   LOGGER.info("Exp Score Dashboard page validation completed successfully.");
	   	   softAssert.assertAll();
	  	   LOGGER.info("Completed test case: verifyDEXExpScoreDashboard" );
    }


     /**
	  * This test case verifies the Exp Score Over Time widgets functionality .
	  */
   	@Test(priority = 71, enabled = true, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
    public final void verifyDEXExpOverTimeScoreWidgets() throws Exception {
           login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
           SoftAssert softAssert = new SoftAssert();
           String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
           DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
           dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
           waitForPageLoaded();
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbSystemhealthOverTime"),"System health Over Time report missing.");
           dexPage.clickByJavaScriptOnDEXPage("expscoredbSystemhealthOverTimeViewReport");
           LOGGER.info(" System Health Over Time report open successfully.");
           waitForPageLoaded();
           dexPage.validateSystemHealthSummaryChart(softAssert, LanguageCode);
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbSystemhealthOverTime"),"System health Over Time report missing.");
           dexPage.clickByJavaScriptOnDEXPage("expscoredbSystemhealthOverTimeViewReport");
           LOGGER.info(" System Health Over Time report open successfully.");
           waitForPageLoaded();
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("topSystemHealthIssue"),"top System HealthIssue report missing.");
	   	   waitForPageLoaded();
	   	   Thread.sleep(2000);
	   	   softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("systemHealthByModel"),"system Health By Model report missing.");
	   	   waitForPageLoaded();
	   	   LOGGER.info("system Health By Model report open successfully.");
	   	   softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("systemHealthOverTimetitle"),"systemHealthOverTime Summary report missing.");
	   	   waitForPageLoaded();
	   	   LOGGER.info("systemHealthOverTime Summary report open successfully.");
	   	   softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("sysRecommendationAction"),"sysRecommendationAction Summary report missing.");
	       LOGGER.info("System health score validation completed");
       	   dexPage.verifyElementsOfDEXPage("breadCrumSystemHealthAnylatics");
           dexPage.clickOnElementsOfDEXPage("breadCrumSystemHealthAnylatics");


          //OS performance
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           waitForPageLoaded();
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbOSPerfOverTime"),"OS Performance Over Time report missing.");
           dexPage.clickByJavaScriptOnDEXPage("expscoredbOSPerformanceOverTimeViewReport");
           LOGGER.info(" System Health Over Time report open successfully.");
           waitForPageLoaded();
           dexPage.validateOSPerformanceSummaryChart(softAssert, LanguageCode);
           waitForPageLoaded();
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           waitForPageLoaded();
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbOSPerfOverTime"),"OS Performance Over Time report missing.");
           dexPage.clickByJavaScriptOnDEXPage("expscoredbOSPerformanceOverTimeViewReport");
           LOGGER.info(" System Health Over Time report open successfully.");
           waitForPageLoaded();
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("topOSPerformanceIssues"),"top OS Performance Issues report missing.");
	   	   waitForPageLoaded();
	   	   Thread.sleep(2000);
   		   softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("osPerformanceByModel"),"os Performance By Model report missing.");
   		   waitForPageLoaded();
   		   LOGGER.info("os Performance By Model report open successfully.");
   		   softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("oSPerformanceOverTime"),"os Performance Over Time report missing.");
   		   waitForPageLoaded();
   		   LOGGER.info("os Performance Over Time report open successfully.");
   		   softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("sysRecommendationAction"),"os Performance Recommendation Action missing.");
   		   LOGGER.info("os Performance score validation completed");
   		   dexPage.verifyElementsOfDEXPage("breadCrumSystemHealthAnylatics");
           dexPage.clickOnElementsOfDEXPage("breadCrumSystemHealthAnylatics");

           //Scecurity
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           waitForPageLoaded();           
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbSceurityOverTime"),"Security Over Time report missing.");
           dexPage.clickByJavaScriptOnDEXPage("expscoredbSceurityOverTimeViewReport");
           LOGGER.info(" System Health Over Time report open successfully.");
           waitForPageLoaded();
           dexPage.validateSecurityScoreChart(softAssert, LanguageCode);
           waitForPageLoaded();
           Thread.sleep(2000);
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           waitForPageLoaded();          
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbSceurityOverTime"),"Security Over Time report missing.");
           dexPage.clickByJavaScriptOnDEXPage("expscoredbSceurityOverTimeViewReport");
           LOGGER.info(" System Health Over Time report open successfully.");
           waitForPageLoaded();
            softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("topSecurityIssues"),"top Security Issues chart missing.");
    		waitForPageLoaded();
    		Thread.sleep(2000);
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("securityByModel"),"Security By Model report missing.");
      		waitForPageLoaded();
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("securityOverTime"),"security Over Time Summary report missing.");
      		waitForPageLoaded();
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("sequrityRecommendationAction"),"sysRecommendationAction Summary report missing.");      	     		
      		softAssert.assertAll();
      		LOGGER.info("Completed test case: verifyDEXExpOverTimeScoreWidgets");

     }

   	 /**
   	 * This test case verifies the Exp Score Dashboard widgets functionality .
	 */
	 @Test(priority = 72, enabled = true, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
	 public final void verifyDEXExpScoreWidgets() throws Exception {
           login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
           SoftAssert softAssert = new SoftAssert();
           String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
           DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
           dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
           waitForPageLoaded();
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");

           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbSystemhealthScore"),"System health Over Time report missing.");
           dexPage.clickByJavaScriptOnDEXPage("expscoredbSystemhealthScoreViewReport");
           LOGGER.info(" System Health Over Time report open successfully.");
           waitForPageLoaded();
           dexPage.validateSystemHealthSummaryChart(softAssert, LanguageCode);
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbSystemhealthScore"),"System health Over Time report missing.");
           Thread.sleep(2000);
           dexPage.clickByJavaScriptOnDEXPage("expscoredbSystemhealthScoreViewReport");
           LOGGER.info(" System Health Over Time report open successfully.");
           waitForPageLoaded();
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("topSystemHealthIssue"),"top System HealthIssue report missing.");
	   	   waitForPageLoaded();
	   	   Thread.sleep(2000);
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("systemHealthByModel"),"system Health By Model report missing.");
	   	   waitForPageLoaded();
	   	   Thread.sleep(2000);
	   	   softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("systemHealthOverTimetitle"),"system Health OverTime Summary report missing.");
	   	   waitForPageLoaded();
	   	   Thread.sleep(2000);
	   	   softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("sysRecommendationAction"),"sysRecommendationAction Summary report missing.");
	       LOGGER.info("System health score validation completed");
	       Thread.sleep(2000);
	       dexPage.verifyElementsOfDEXPage("breadCrumSystemHealthAnylatics");
           dexPage.clickOnElementsOfDEXPage("breadCrumSystemHealthAnylatics");

          //OS performance
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           waitForPageLoaded();

           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbOSPerfScore"),"OS Performance report missing.");
           dexPage.clickByJavaScriptOnDEXPage("expscoredbosPerformanceScoreViewReport");
           LOGGER.info(" System Health Over Time report open successfully.");
           waitForPageLoaded();
           dexPage.validateOSPerformanceSummaryChart(softAssert, LanguageCode);
           waitForPageLoaded();
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           waitForPageLoaded();
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbOSPerfScore"),"OS Performance report missing.");
           Thread.sleep(2000);
           dexPage.clickByJavaScriptOnDEXPage("expscoredbosPerformanceScoreViewReport");
           LOGGER.info(" System Health Over Time report open successfully.");
           waitForPageLoaded();

           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("topOSPerformanceIssues"),"top OS Performance Issues report missing.");
	   	   waitForPageLoaded();
	   	   Thread.sleep(2000);
	   	   softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("osPerformanceByModel"),"os Performance By Model report missing.");
	   	   waitForPageLoaded();
	   	   Thread.sleep(2000);
	   	   LOGGER.info("Verified OS Performance By Model chart");
	   	   softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("oSPerformanceOverTime"),"os Performance  report missing.");
	   	   waitForPageLoaded();
	   	   Thread.sleep(2000);
	   	   LOGGER.info("Verified OS Performance Over Time chart");
	   	   softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("sysRecommendationAction"),"os Performance Recommendation Action missing.");
	   	   LOGGER.info("os Performance score validation completed");
	   	   Thread.sleep(2000);
	   	   LOGGER.info("Verified os Performance Recommendation Action Summary report");
	   	   dexPage.verifyElementsOfDEXPage("breadCrumSystemHealthAnylatics");
           dexPage.clickOnElementsOfDEXPage("breadCrumSystemHealthAnylatics");

           //Scecurity
           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           waitForPageLoaded();
           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbSceurityOScore"),"OS Performance report missing.");
           dexPage.clickByJavaScriptOnDEXPage("expscoredbSceurityViewReport");
           LOGGER.info(" System Health Over Time report open successfully.");
           waitForPageLoaded();

           dexPage.validateSecurityScoreChart(softAssert, LanguageCode);
           waitForPageLoaded();

           softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
           waitForPageLoaded();

           softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbSceurityOScore"),"Sceurity Score  report missing.");
           Thread.sleep(2000);
           dexPage.clickByJavaScriptOnDEXPage("expscoredbSceurityViewReport");
           LOGGER.info("Sceurity Score report open successfully.");
           waitForPageLoaded();
            softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("topSecurityIssues"),"top Security Issues chart missing.");
     		waitForPageLoaded();
     		Thread.sleep(2000);
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("securityByModel"),"Security By Model chart missing.");
      		waitForPageLoaded();
      		Thread.sleep(2000);
      		LOGGER.info("Verified Security By Model chart");
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("securityOverTime")," Security Over Time chart missing.");
      		waitForPageLoaded();
      		Thread.sleep(2000);
      		LOGGER.info("Verified Security Over Time chart");
      		softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("sequrityRecommendationAction"),"sysRecommendation Action Summary report missing.");
		    LOGGER.info("Verified sysRecommendation Action Summary report");
      		softAssert.assertAll();
      		LOGGER.info("Completed test case: verifyDEXExpScoreWidgets");

     }
	 /**
	 * This test case verifies the Installed Application Score Report widget functionality .
     */

	 @Test(priority = 73, enabled = true, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
	 public final void verifyInstalledApplicationScoreNavigations() throws Exception {
   	        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   	        SoftAssert softAssert = new SoftAssert();
   	     String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
   	        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
   	        dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   	        waitForPageLoaded();
   	        LOGGER.info("Navigated to Analytics Page");
   	        softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Exp Score Dashboard page did not load successfully from Analytics page.");
   	        softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbInstalledAppScore"),"OS Performance report missing.");
   	        dexPage.clickByJavaScriptOnDEXPage("expscoredbInstalledAppViewReport");
   	        LOGGER.info("Installed Application Score report open successfully.");
   	        waitForPageLoaded();
   	        Thread.sleep(2000);
   	        softAssert.assertTrue(dexPage.validateInstalledApplicationScoreSection(), "Installed Application Score section validation failed.");
   	        LOGGER.info("Application score validation completed");
   			waitForPageLoaded();
   			dexPage.verifyElementsOfDEXPage("webButton");
   			dexPage.clickOnElementsOfDEXPage("webButton");
   			LOGGER.info("navigate to web tab");
   			waitForPageLoaded();
   			softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("webApplicationsScore"),"Application score report missing.");
   			softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("appswithLongestPageLoadTime"),"Application with most crashes report missing.");
   			softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("appswithMostErrors"),"Application with most freezes report missing.");
   			softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("appswithMostErrorsOverTime"),"crashes And Freezes OverTime freezes report missing.");
   			LOGGER.info("Application web validation completed");

   			dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   			waitForPageLoaded();
            softAssert.assertAll();
            LOGGER.info("Completed test case: verifyInstalledApplicationScoreNavigations");

    }
 	/**
 	 * This test case verifies the Network Health Score Report widget functionality .
 	 */

    @Test(priority = 74, enabled = true, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
   	public final void verifyNetworkHealthScoreNavigations() throws Exception {
   	        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   	        SoftAssert softAssert = new SoftAssert();
   	     String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
   	        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
   	        dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   	        waitForPageLoaded();
   	        LOGGER.info("Navigated to Analytics Page");
   	        softAssert.assertTrue(dexPage.verifyClickAndValidateElement("expScoreLink","expScoreContent"),"Experience Score Dashboard page did not load successfully from Analytics page.");
            LOGGER.info("Navigated to Experience Score Dashboard Page");
   	        softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("expscoredbNetworkScorepScore"),"Network Score report missing.");
   	        LOGGER.info("Network Score report present on Experience Score Dashboard Page");
   	        dexPage.clickByJavaScriptOnDEXPage("expscoredbNetworkScoreViewReport");
   	        LOGGER.info("Installed Application Score report open successfully.");
   	        waitForPageLoaded();

   	        softAssert.assertTrue(dexPage.verifyAllNetworkScoreCharts(LanguageCode), "Network Score charts validation failed.");
   	   		waitForPageLoaded();
            LOGGER.info("Network Score charts validation completed");
   			softAssert.assertTrue(dexPage.verifyElementsOfDEXPage("nwHealthRecommendationAction"),"nwRecommendationAction Summary report missing.");
   			LOGGER.info("Network Health Recommendation Action  summary report is present");
   	   		softAssert.assertAll();

    }

    @Test(priority = 75, groups = { "REGRESSION_APPLICATIONS_INSIGHTS","PRODUCTION_SCORE" },description = "Test Case: ",enabled = false)
    public final void verifyChartsLoadingKernelPanicErrorsDashboard() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        analyticsPage.clickOnElementsOfAnalyticsPage("kernelPanicErrorReport");
        waitForPageLoaded();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"topModelsAffectedChartNodata","topModelsAffectedChart","Top Models Affected"), "Top Models Affected Chart is not loading on kernel panic dashboard page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"TopModulesCausingKernelPanicChartNodata","TopModulesCausingKernelPanicChart","Top Modules Causing Kernel Panic"), "Top Modules Causing Kernel Panic Chart is not loading on kernel panic dashboard page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"TopHardwareErrorCodesChartNodata","TopHardwareErrorCodesChart","Top Hardware Error Codes"), "Top Hardware Error Codes Chart is not loading on kernel panic dashboard page.");
        softAssert.assertTrue(dexPage.verifyExperienceManagementChartLoading(LanguageCode,"KernelPanicovertimeChartNodata","KernelPanicovertimeChart","Kernel Panic over time"), "Kernel Panic over time Chart is not loading on  kernel panic dashboard page.");
        softAssert.assertAll();
        LOGGER.info("kernel Panic Dashboard Charts loading functionality validation has been completed successfully.");
    }

    @Test(priority = 76, groups = { "REGRESSION_APPLICATIONS_INSIGHTS","PRODUCTION_SCORE"},description = "Test Case: ",enabled = false)
    public final void verifyGridLoadingKernelPanicErrorsDashboard() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        analyticsPage.clickOnElementsOfAnalyticsPage("kernelPanicErrorReport");
        waitForPageLoaded();
        DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
        if(dexPage.waitForElementsOfDEXPage("firstwebAppNameLink")) {
            Assert.assertTrue(dexPage.verifyElementsOfDEXPage("firstwebAppNameLink"), "Kernel panic Dashboard Grid page did not load successfully.");
        }
        else {
            Assert.assertTrue(dexPage.verifyElementsOfDEXPage("kernelpanicgridnodata"));
            LOGGER.info("No data is present in kernel panic grid.");
        }
        softAssert.assertAll();
        LOGGER.info("kernel panic Dashboard Grid loading functionality validation has been completed successfully.");
    }
 }



