package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.AWSIOTUtils;
import com.basesource.utils.EnrollFakeDevice;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ScriptVariables;
import com.daasui.pages.WEXDashboardPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.daasui.pages.WEPScriptsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WEPScriptsTest extends CommonTest{
    private static Logger LOGGER = LogManager.getLogger(WEPScriptsTest.class);

    @Test(priority = 3, groups = { "REGRESSION_CONFIGSCRIPTS", "REGRESSION_INTEGRATIONQA_CUJ" }, description = "TestCaseID:C43550144")
    public final void verifyWorkforceExperienceScriptsPageRedirection() throws Exception{
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("scriptsPageTitleScripts"),"Scripts Title is not as expected");
        LOGGER.info("Workforce Experience Scripts Page Redirection Test Passed");
    }

    @Test(priority = 4, groups = { "REGRESSION_CONFIGSCRIPTS", "REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_CONFIGSCRIPTS" ,"PRODUCTION_LDK","INITECH_SOLUTIONS","INITECH_SOLUTIONS","INITECH_SOLUTIONS"}, description = "TestCaseID:C41266534")
    public final void verifyWorkforceExperienceScriptsListPage() throws Exception{
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(
                getTextLanguage(LanguageCode, "daas_ui", "wex.script.library.synopsis"),
                getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.scriptname"),
                getTextLanguage(LanguageCode, "daas_ui", "global.version"),
                getTextLanguage(LanguageCode, "daas_ui", "wex.script.assignments.details.operation"),
                getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.remediate.table.column.createdby")));
        wexScriptsPage.verifyElementsOfScriptsPage("scriptsPageTitleScripts");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptTableRows");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptTableCount");

        resetTableConfiguration();
        Assert.assertTrue(wexScriptsPage.verifyTableColumns(expectedColumnList,"scriptLibraryTableColumns"), "Table Columns are not as expected");
        LOGGER.info("Workforce Experience Scripts List Page Test Passed");
    }

    @Test(priority = 3, groups = { "REGRESSION_CONFIGSCRIPTS", "REGRESSION_INTEGRATIONQA_CUJ" }, description = "TestCaseID:C41266534")
    public final void verifyAddScript() throws Exception{
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        String scriptName = ScriptVariables.SCRIPT_NAME + System.currentTimeMillis();
        wexScriptsPage.verifyElementsOfScriptsPage("scriptsPageTitleScripts");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList");
        wexScriptsPage.clickOnElementsOfScriptsPage("addScript");
        wexScriptsPage.scrollTillViewScriptsPage("browseInput");
        wexScriptsPage.importScriptFile(ConstantPath.IMPORT_PATH+ ScriptVariables.SAMPLESCRIPT_UPLOAD);
        wexScriptsPage.verifyElementsOfScriptsPage("nextButton");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("nextButton");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("nextButton");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptName");
        wexScriptsPage.enterTextOnScriptsPage("scriptName", scriptName);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptSynopsis");
        wexScriptsPage.enterTextOnScriptsPage("scriptSynopsis", ScriptVariables.SCRIPT_SYNOPSIS);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptDescription");
        wexScriptsPage.enterTextOnScriptsPage("scriptDescription", ScriptVariables.SCRIPT_DESCRIPTION);
        wexScriptsPage.verifyElementsOfScriptsPage("addScriptFinishButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("addScriptFinishButton");
        waitForPageLoaded();
        wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList");
        sleeper(3000);
        waitForPageLoaded();
        wexScriptsPage.verifyElementsOfScriptsPage("scriptNameSearchFields");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("scriptNameSearchFields");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameSearchFields");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("scriptNameSearchFields", scriptName);
        sleeper(2000);
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptNameRowValue",scriptName),"The script did not get added successfully");
        wexScriptsPage.clearScriptFilter();
        LOGGER.info("Add Script Test Passed");
    }

    @Test(priority = 4, groups = { "REGRESSION_CONFIGSCRIPTS", "REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_CONFIGSCRIPTS" , "PRODUCTION_LDK","INITECH_SOLUTIONS","INITECH_SOLUTIONS"}, description = "TestCaseID:C41941895")
    public final void verifyWorkforceExperienceAssignmentListPage() throws Exception{
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "companies.list.status") ,getTextLanguage(LanguageCode, "daas_ui","wex.script.library.synopsis"), getTextLanguage(LanguageCode, "daas_ui", "wex.script.assignments.details.operation"),
                getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.assignmentname"),
                getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title"),getTextLanguage(LanguageCode, "daas_ui", "exp.mgmt.fleetSummary.totalDevices")));
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageTitleScripts");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableRows");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableCount");
        resetTableConfiguration();
        Assert.assertTrue(wexScriptsPage.verifyTableColumns(expectedColumnList,"assignmentTableColumns"), "Table Columns are not as expected");
        LOGGER.info("Workforce Experience Assignment List Page Test Passed");
    }

    @Test(priority = 5, groups = { "REGRESSION_CONFIGSCRIPTS", "PRODUCTION_CONFIGSCRIPTS","PRODUCTION_LDK","INITECH_SOLUTIONS","INITECH_SOLUTIONS" }, description = "TestCaseID:C41941896")
    public final void verifyWorkforceExperienceActivityListPage() throws Exception{
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
        if (!toggleVerification(ScriptVariables.WEX_REMEDIATION_ACTIVITY_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_ACTIVITY);
            List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(
                    getTextLanguage(LanguageCode, "daas_ui", "campaign.details.status"),
                    getTextLanguage(LanguageCode, "daas_ui", "wex.script.library.synopsis"),
                    getTextLanguage(LanguageCode, "daas_ui", "wex.script.assignments.details.operation"),
                    getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.assignmentname"),
                    getTextLanguage(LanguageCode, "daas_ui", "global.date"),
                    getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title"),
                    getTextLanguage(LanguageCode, "daas_ui", "exp.mgmt.fleetSummary.totalDevices"),getTextLanguage(LanguageCode, "daas_ui", "global.success"),getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.failure"),
                    getTextLanguage(LanguageCode, "daas_ui", "global.error")));
            wexScriptsPage.clickOnElementsOfScriptsPage("activitiesTab");
            wexScriptsPage.verifyElementsOfScriptsPage("activityPageTitleScripts");
            wexScriptsPage.verifyElementsOfScriptsPage("activityPageList");
            wexScriptsPage.verifyElementsOfScriptsPage("activityTableRows");
            wexScriptsPage.verifyElementsOfScriptsPage("activityTableCount");
            resetTableConfiguration();
            Assert.assertTrue(wexScriptsPage.verifyTableColumns(expectedColumnList,"activityTableColumns"), "Table Columns are not as expected");
            LOGGER.info("Workforce Experience Assignment List Page Test Passed");
        }
        else {
//            if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//                dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_ACTIVITY);
//            }else{
//                dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_ACTIVITY);
//            }
            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_ACTIVITY);
            List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(
                    getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.configuration.options.initiated.on"),
                    getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.configuration.options.remediation.name"),
                    getTextLanguage(LanguageCode, "daas_ui", "asset_detail.accessories_column.type"),
                    getTextLanguage(LanguageCode, "daas_ui", "global.subtype"),
                    getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.configuration.options.initiated.by"),
                    getTextLanguage(LanguageCode, "daas_ui", "campaign.details.status"),
                    getTextLanguage(LanguageCode, "daas_ui", "bsod.total.devices"),
                    getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.configuration.options.devices.remediated"),
                    getTextLanguage(LanguageCode, "daas_ui", "global.error"),
                    getTextLanguage(LanguageCode, "daas_ui", "wex.script.library.synopsis")));
            wexScriptsPage.verifyElementsOfScriptsPage("activityPageTitleScripts");
            wexScriptsPage.verifyElementsOfScriptsPage("activityPageList");
            wexScriptsPage.verifyElementsOfScriptsPage("activityTableRows");
            wexScriptsPage.verifyElementsOfScriptsPage("activityTableCount");
            resetTableConfiguration();
            Assert.assertTrue(wexScriptsPage.verifyTableColumns(expectedColumnList,"activityTableColumns"), "Table Columns are not as expected");
            LOGGER.info("Workforce Experience Unified Activity List Page Test Passed");
        }

    }

    @Test(priority = 6, groups = { "REGRESSION_CONFIGSCRIPTS", "REGRESSION_INTEGRATIONQA_CUJ" }, description = "TestCaseID:C41774275")
    public final void verifyWorkforceExperienceAddAssignment() throws Exception{
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        String assignmentName = ScriptVariables.ASSIGNMENT_NAME + System.currentTimeMillis();
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageTitleScripts");
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList"),"Assignment List did not get loaded successfully");
        wexScriptsPage.clearScriptFilter();
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("addAssignment"),"add assignment button is not visible");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignment");

        //Verify Add Assignment modal is opened and select script table is shown
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("scriptSearchField"),"search script screen did not open");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptSearchField");
        wexScriptsPage.enterTextOnScriptsPage("scriptSearchField", ScriptVariables.ADDASSIGNMENTSCRIPT_NAME);
        wexScriptsPage.clickOnElementsOfScriptsPage("selectScriptRadioButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentNextButton");
        waitForPageLoaded();
        sleeper(2000);
        //Verify Add Assignment Group modal is opened and select group table is shown
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("groupSearchField"),"search group screen did not open");
        wexScriptsPage.clickOnElementsOfScriptsPage("groupSearchField");
        wexScriptsPage.enterTextOnScriptsPage("groupSearchField", ScriptVariables.ADDASSIGNMENTGROUP_NAME);
        wexScriptsPage.clickOnElementsOfScriptsPage("selectGroupRadioButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentNextButton");
        waitForPageLoaded();
        
        //Verify Add Assignment modal is opened and assignment name and description fields are shown
        Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("assignmentDescription"),"add assignment modal did not open");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("assignmentName");
        wexScriptsPage.enterTextOnScriptsPage("assignmentName", assignmentName);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("assignmentDescription");
        wexScriptsPage.enterTextOnScriptsPage("assignmentDescription", ScriptVariables.ASSIGNMENT_DESCRIPTION);
        wexScriptsPage.clickOnElementsOfScriptsPage("nextButton");

        //Click on Finish button
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("addAssignmentFinishButton"),"review screen did not open");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentFinishButton");
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList"),"assignment list did not loaded");
        sleeper(3000);
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name column is not visible");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentTableNameColumn");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentTableNameColumn");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("assignmentTableNameColumn", assignmentName);
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name search box is not filled");
        sleeper(2000);
        waitForPageLoaded();
        wexScriptsPage.scrollTillViewScriptsPage("assignmentTabRowValue");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentTabRowValue",assignmentName),"The assignment did not get added successfully when tried to search after entering text in search box");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("runAssignmentEllipsis");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentEllipsisClick");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentEllipsisRun");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentModalConfirm");
        sleeper(1000);
        Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("runAssignmentToastMessageOne"),"The assignment run did not get triggered successfully");
        Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("runAssignmentToastMessageTwo"),"The assignment run did not get triggered successfully");
        waitForPageLoaded();
        sleeper(2000);
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("activityPageTitleScripts"),"User not redirected to Activity tab successfully");
        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
            dashboardPage.clickOnElementsOfDashboardPage("fleetScripts");
            wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
            wexScriptsPage.clickOnElementsOfScriptsPage("clearFilter");
            wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList");
        }
        else {
            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
            wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
            wexScriptsPage.clickOnElementsOfScriptsPage("clearFilter");
            wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList");
        }
    }

    @Test(priority = 7, groups = { "REGRESSION_CONFIGSCRIPTS" }, description = "TestCaseID:C43550144")
    public final void verifyAssignmentListInScriptDetails() throws Exception{
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptsTab");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptNameSearchField");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("scriptNameSearchFields");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameSearchFields");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("scriptNameSearchFields", ScriptVariables.SCRIPTNAME_DETAILS);
        sleeper(2000);
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptNameRowValue",ScriptVariables.SCRIPTNAME_DETAILS),"The script did not get added successfully");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameRowValue");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptDetailsPageAssignmentTab");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptDetailsPageAssignmentTab");
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList"),"Assignment List inside script details did not get open successfully");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignment");
        wexScriptsPage.verifyElementsOfScriptsPage("addAssignmentModalTitle");
        wexScriptsPage.scrollTillViewScriptsPage("addAssignmentCancelButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentCancelButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptDetailsScriptsBreadcrumb");
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList"),"Script List did not loaded successfully");
        wexScriptsPage.clickOnElementsOfScriptsPage("clearFilter");
    }

    @Test(priority = 8, groups = { "REGRESSION_CONFIGSCRIPTS"  ,"INITECH_SOLUTIONS"}, description = "TestCaseID:C43392129")
    public final void verifyWorkforceExperienceAssignmentDetails() throws Exception {
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();

        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        leftSideMenuVerification();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
    	wexScriptsPage.clearScriptFilter();
		wexScriptsPage.clickOnElementsOfScriptsPage("runTypeListboxOnAssignmentListPage");
		sleeper(1000);
		wexScriptsPage.clickOnElementsOfScriptsPage("runTypeOptionSelectionOnAssignmentListPage");
		sleeper(2000);
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentNameSearchField");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("assignmentNameSearchField", ScriptVariables.ASSIGNMENT_NAME);
        sleeper(2000);
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentNameRowValue",ScriptVariables.ASSIGNMENT_NAME),"The assignment did not get displayed in the list page.");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentNameRowValue");

        wexScriptsPage.scrollTillViewScriptsPage("assignmentDetailScriptInfoHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailScriptInfoHeader","Script Information"), "Label of Script Information header is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailScriptName","Script Name"), "Label of Script Name is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailScriptNameVal"), "Value doesn't exist for Script Name");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailVersion","Version"), "Label of Assignment Script Version is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailVersionVal"), "Value doesn't exist for Script Version");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailOperation","Operation"), "Label of Assignment Script Operation is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailOperationVal"), "Value doesn't exist for Script Operation");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailScriptID","Script ID"), "Label of Assignment Script ID is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailScriptIDVal"), "Value doesn't exist for Script ID");

//        Assignment Information
        wexScriptsPage.scrollTillViewScriptsPage("assignmentDetailAssignmentInfoHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentInfoHeader","Assignment Information"), "Label of Assignment Information header is not matching");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailCreatedByLabel","Created By"), "Label of Assignment Author is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailCreatedByValue"), "Value doesn't exist for Assignment Created By Author");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentNameLabel","Assignment Name"), "Label of Assignment Name is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailAssignmentNameValue"), "Value doesn't exist for Assignment Name");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentLastModifiedLabel","Last Modified"), "Label of Assignment Information Last Modified is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailAssignmentLastModifiedValue"), "Value doesn't exist for Assignment Created By Author");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentDateCreatedLabel","Date Created"), "Label of Assignment Information Created Date is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailAssignmentDateCreatedValue"), "Value doesn't exist for Assignment Created Date");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentGroupsLabel","Groups"), "Label of Assignment Information Group is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailAssignmentGroupsValue"), "Value doesn't exist for Assignment Groups");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentAssignmentIDLabel","Assignment ID"), "Label of Assignment Id is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailAssignmentAssignmentIDValue"), "Value doesn't exist for Assignment Id");

        //        Schedule Section
        wexScriptsPage.scrollTillViewScriptsPage("assignmentDetailScheduleInfoHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailScheduleInfoHeader","Schedule"), "Label of Schedule header is not matching");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentDetailScheduleEditBtn");
        LOGGER.info("Assignment Details Verification Test Passed");

    }

    @Test(priority = 9, groups = { "REGRESSION_CONFIGSCRIPTS" }, description = "TestCaseID:C48892886")
    public final void verifyWorkforceExperienceAssignmentScheduleEdit() throws Exception {
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }

        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        HashMap<String, String> scheduleDetails = new HashMap<>(){{put("EndDate","-");put("EndTime","-");}} ;
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
        wexScriptsPage.clearScriptFilter();
		wexScriptsPage.clickOnElementsOfScriptsPage("runTypeListboxOnAssignmentListPage");
		sleeper(1000);
		wexScriptsPage.clickOnElementsOfScriptsPage("runTypeOptionSelectionOnAssignmentListPage");
		sleeper(2000);
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentNameSearchField");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("assignmentNameSearchField", ScriptVariables.ASSIGNMENT_NAME);
        sleeper(2000);
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentNameRowValue",ScriptVariables.ASSIGNMENT_NAME),"The assignment did not get displayed in the list page.");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentNameRowValue");

        //Schedule Section
        wexScriptsPage.scrollTillViewScriptsPage("assignmentDetailScheduleInfoHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailScheduleInfoHeader","Schedule"), "Label of Schedule header is not matching");
        //Verify the schedule if exist delete and proceed
        wexScriptsPage.deleteScheduleIfExist();

        wexScriptsPage.verifyElementsOfScriptsPage("assignmentDetailAddScheduleBtn");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("assignmentDetailAddScheduleBtn");

        //Schedule Sidebar edit section
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailsScheduleSideBarLabel", "Schedule"), "None");

        //Extracting the schedule details from UI and placing it into the map to verify later
        wexScriptsPage.extractScheduleDetailsToVerify(scheduleDetails);
        //Save edited schedule and verify toast message
        wexScriptsPage.saveEditedAssignmentAndVerifyToast();
        //Verification of edited data of schedule in the schedule section of Assignment details page
        Assert.assertTrue(wexScriptsPage.VerifySchedulePostEditing(scheduleDetails),"Verification of edited data of schedule in the schedule section of Assignment details page failed");

        //Getting the Start time of the schedule edited and Modify the scheduled start time (add 1 hour)
        String newTime = wexScriptsPage.generateNewScheduleStartTime(scheduleDetails);
        //Modify the Start time of a schedule
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentDetailScheduleEditBtn");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentDetailScheduleEditBtn");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentDetailsScheduleEditStartTimeValue");
        wexScriptsPage.modifyScheduleTime("assignmentDetailsScheduleStartTimes", newTime, scheduleDetails, "ScheduleStartTime");

        //Form new end date from start date and add end time and end date
        String newEndDate = wexScriptsPage.generateNewScheduleStartDate(scheduleDetails);
        newTime = newTime.matches("^\\d{1}:\\d{2}.*") ? "0" + newTime : newTime;
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentDetailsIncludeEndDateTimeChkBox");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentDetailsIncludeEndDateTimeChkBox");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentDetailsEndDate");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentDetailsEndDate");
        wexScriptsPage.sendKeysOnWEPScriptsPage("assignmentDetailsEndDate",newEndDate);
        scheduleDetails.put("EndDate",newEndDate);

        wexScriptsPage.verifyElementsOfScriptsPage("assignmentDetailsEndTimeValue");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentDetailsEndTimeValue");
        wexScriptsPage.modifyScheduleTime("assignmentDetailsEndTime", newTime, scheduleDetails, "EndTime");
        //Save the modified schedule and verify the toast message
        wexScriptsPage.saveEditedAssignmentAndVerifyToast();

        //Verification of edited data of schedule in the schedule section of Assignment details page(post editing the start time)
        Assert.assertTrue(wexScriptsPage.VerifySchedulePostEditing(scheduleDetails), "Verification failed, UI schedule details not matching with the extracted data");

        //Get inside the schedule and deleting
        wexScriptsPage.deleteScheduleAndVerifyToast();
        //Verify the schedule deleted is not exist
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAddScheduleBtn", "Add Schedule"), "There is a schedule exist for this Script");
    }

    @Test(priority = 10, groups = { "REGRESSION_CONFIGSCRIPTS", "REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_CONFIGSCRIPTS" , "PRODUCTION_LDK","INITECH_SOLUTIONS","INITECH_SOLUTIONS" }, description = "TestCaseID:C50830890")
    public final void verifyWorkforceExperienceGalleryListPage() throws Exception{
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "companies.list.status") ,getTextLanguage(LanguageCode, "daas_ui","benchmark.table.header.last_updated"), getTextLanguage(LanguageCode, "daas_ui","wex.script.library.synopsis"),
                getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.scriptname"),
                getTextLanguage(LanguageCode, "daas_ui", "global.version"),getTextLanguage(LanguageCode, "daas_ui", "benchmark.table.header.created_by")
                ,getTextLanguage(LanguageCode, "daas_ui", "wex.script.assignments.details.operation")));
        wexScriptsPage.clickOnElementsOfScriptsPage("galleryTab");
        wexScriptsPage.verifyElementsOfScriptsPage("galleryPageTitleScripts");
        wexScriptsPage.verifyElementsOfScriptsPage("galleryPageList");
        wexScriptsPage.verifyElementsOfScriptsPage("galleryTableRows");
        wexScriptsPage.verifyElementsOfScriptsPage("galleryTableCount");
        resetTableConfiguration();
        Assert.assertTrue(wexScriptsPage.verifyTableColumns(expectedColumnList,"galleryTableColumns"), "Table Columns are not as expected");
        LOGGER.info("Workforce Experience Assignment List Page Test Passed");
    }

    @Test(priority = 11, groups = { "REGRESSION_CONFIGSCRIPTS" }, description = "TestCaseID:C42526069")
    public final void verifyWorkforceExperienceScriptDetails() throws Exception {
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();

        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        wexScriptsPage.clearScriptFilter();
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameSearchFields");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("scriptNameSearchFields", ScriptVariables.SCRIPT_NAME);
        sleeper(2000);
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptNameRowValue",ScriptVariables.SCRIPT_NAME),"The script did not get displayed in the list page.");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameRowValue");

        wexScriptsPage.scrollTillViewScriptsPage("scriptDetailScriptInfoHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailScriptInfoHeader","Script Information"), "Label of Script Information header is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailScriptName","Name"), "Label of Script Name is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailScriptNameVal"), "Value doesn't exist for Script Name");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailVersion","Version"), "Label of Script Version is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailVersionVal"), "Value doesn't exist for Script Version");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailDateCreated","Date Created"), "Label of Date Created is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailDateCreatedVal"), "Value doesn't exist for Date Created");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailCreatedBy","Created By"), "Label of Created By is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailCreatedByVal"), "Value doesn't exist for Created By");

        //Execution Properties
        wexScriptsPage.scrollTillViewScriptsPage("scriptDetailsExecutionPropertiesHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailsExecutionPropertiesHeader","Execution Properties"), "Label of Execution Properties header is not matching");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailOperation","Operation"), "Label of Operation is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailOperationVal"), "Value doesn't exist for Operation");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailHostEnv","Host Environment"), "Label of Host Environment is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailHostEnvVal"), "Value doesn't exist for Host Environmen");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailRunAs","Run as"), "Label of Run as is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailRunAsVal"), "Value doesn't exist for Run as");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailExecPolicy","Execution Policy"), "Label of Execution Policy is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailExecPolicyVal"), "Value doesn't exist for Execution Policy");
        
        //Script Content
        wexScriptsPage.scrollTillViewScriptsPage("scriptDetailsScriptContentHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailsScriptContentHeader","Script Content"), "Label of Script Content is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailsScriptContentVal"), "Value doesn't exist for Script Content");
        LOGGER.info("Script Details Verification Test Passed");

    }

    @Test(priority = 12, groups = { "REGRESSION_CONFIGSCRIPTS", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCaseID:C42526069")
    public final void verifyWorkforceExperienceGalleryScriptDetails() throws Exception {
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        wexScriptsPage.clearScriptFilter();
        wexScriptsPage.verifyElementsOfScriptsPage("galleryTab");
        wexScriptsPage.clickOnElementsOfScriptsPage("galleryTab");
        wexScriptsPage.verifyElementsOfScriptsPage("galleryPageTitleScripts");
        wexScriptsPage.verifyElementsOfScriptsPage("galleryStatusDropdown");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("galleryStatusDropdown");
        wexScriptsPage.selectValueOnDropDownOfScriptsPage("galleryStatusOptions", getTextLanguage(LanguageCode, "daas_ui", "wex.script.gallery.status.inLibrary"));
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("galleryStatusDropdown");
        wexScriptsPage.verifyElementsOfScriptsPage("galleryScriptNameValue");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("galleryScriptNameValue");
        waitForPageLoaded();
        wexScriptsPage.scrollTillViewScriptsPage("scriptDetailScriptInfoHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailScriptInfoHeader","Script Information"), "Label of Script Information header is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailScriptName","Name"), "Label of Script Name is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailScriptNameVal"), "Value doesn't exist for Script Name");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("galleryDetailCreatedByLabel","Created By"), "Label of Created By is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("galleryDetailCreatedByVal"), "Value doesn't exist for Created By");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailVersion","Version"), "Label of Script Version is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailVersionVal"), "Value doesn't exist for Script Version");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailDateCreated","Date Created"), "Label of Date Created is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailDateCreatedVal"), "Value doesn't exist for Date Created");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("galleryDetailDateModifiedLabel","Last Modified"), "Label of Last Modified is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("galleryDetailDateModifiedVal"), "Value doesn't exist for Last Modified");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("galleryDetailScriptIDLabel","Script ID"), "Label of Script ID is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("galleryDetailScriptIDVal"), "Value doesn't exist for Script ID");

        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailCreatedBy","Created By"), "Label of Created By is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailCreatedByVal"), "Value doesn't exist for Created By");

        //Execution Properties
        wexScriptsPage.scrollTillViewScriptsPage("scriptDetailsExecutionPropertiesHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailsExecutionPropertiesHeader","Execution Properties"), "Label of Execution Properties header is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailOperation","Operation"), "Label of Operation is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailOperationVal"), "Value doesn't exist for Operation");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailHostEnv","Host Environment"), "Label of Host Environment is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailHostEnvVal"), "Value doesn't exist for Host Environmen");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailRunAs","Run as"), "Label of Run as is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailRunAsVal"), "Value doesn't exist for Run as");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailExecPolicy","Execution Policy"), "Label of Execution Policy is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailExecPolicyVal"), "Value doesn't exist for Execution Policy");

        //Script Content
        wexScriptsPage.scrollTillViewScriptsPage("scriptDetailsScriptContentHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailsScriptContentHeader","Script Content"), "Label of Script Content is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("scriptDetailsScriptContentVal"), "Value doesn't exist for Script Content");
        LOGGER.info("Gallery Script Details Verification Test Passed");

    }

    @Test(priority = 13, groups = { "REGRESSION_CONFIGSCRIPTS", "PRODUCTION_LDK","INITECH_SOLUTIONS","INITECH_SOLUTIONS"}, description = "TestCaseID:C52399943")
    public final void verifyWorkforceExperienceActivityDetails() throws Exception {
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_ACTIVITY);
        //select all columns from table
        wexScriptsPage.selectAllTableColumns();
        //wexScriptsPage.orderColumnAscOrDesc("activityListInitiatedOrder","Desc");
        wexScriptsPage.verifyElementsOfScriptsPage("activityStatusDropdown");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("activityStatusDropdown");

        //completed
        wexScriptsPage.selectValueOnDropDownOfScriptsPage("activityStatusOptions", getTextLanguage(LanguageCode, "daas_ui", "benchmark.status.completed"));
        sleeper(2000);
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("activityStatusDropdown");
        wexScriptsPage.verifyElementsOfScriptsPage("activityNameValue");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("activityNameValue");
        waitForPageLoaded();
        wexScriptsPage.scrollTillViewScriptsPage("activityDetailScriptInfoHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailScriptNameLabel","Script Name"), "Label of Script Name is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailScriptNameVal"), "Value doesn't exist for Script Name");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailTypeLabel","Type"), "Label of Detail type is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailTypeVal"), "Value doesn't exist for Detail type");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailSubTypeLabel","Subtype"), "Label of Sub Type is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailSubTypeVal"), "Value doesn't exist for Sub Type");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailInitaitedOnLabel","Initiated On"), "Label of InitaitedOn is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailInitaitedOnVal"), "Value doesn't exist for Initaited On");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailInitaitedByLabel","Initiated By"), "Label of InitaitedBy is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailInitaitedByVal"), "Value doesn't exist for Initaited By");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailCompletedOnLabel","Completed On"), "Label of Completed On is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailCompletedOnVal"), "Value doesn't exist for Completed On");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailDeviceRemidiatedLabel","Devices Remediated"), "Label of Device Remidiated is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailDeviceRemidiatedVal"), "Value doesn't exist for Device Remidiated");

        wexScriptsPage.scrollTillViewScriptsPage("activityDetailDeviceHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailDeviceHeader","Devices"), "Label of Devices header is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailDeviceHeader","Devices"), "Label of Devices header is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailDeviceHeader","Devices"), "Label of Devices header is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailKPIstatus1","Error"), "Label of error kpi is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailKPIstatus2","Success"), "Label of Success kpi is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailKPIstatus3","Not Processed"), "Label of Not Processed kpi is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailKPIstatus4","In Progress"), "Label of In progress kpi is not matching");
        List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(
                getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.configuration.options.device.name"),
                getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.configuration.options.serial.number"),
                getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activity.remediation.executionStatus"),
                getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activity.remediation.exitcode")));
        wexScriptsPage.verifyElementsOfScriptsPage("activityDetailsTable");
        wexScriptsPage.verifyElementsOfScriptsPage("activityDetailsTableRows");
        wexScriptsPage.verifyElementsOfScriptsPage("activityDetailsTableCount");
        Assert.assertTrue(wexScriptsPage.verifyTableColumns(expectedColumnList,"activityDetailsTableColumns"), "Table Columns are not as expected");
        wexScriptsPage.verifyElementsOfScriptsPage("activityListBreadCrumb");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("activityListBreadCrumb");
        wexScriptsPage.clearScriptFilter();
        //cancelled
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("activityStatusDropdown");
        wexScriptsPage.selectValueOnDropDownOfScriptsPage("activityStatusOptions", getTextLanguage(LanguageCode, "daas_ui", "reports.history.status.canceled"));
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("activityStatusDropdown");
        wexScriptsPage.verifyElementsOfScriptsPage("activityNameValue");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("activityNameValue");
        wexScriptsPage.scrollTillViewScriptsPage("activityDetailScriptInfoHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailScriptNameLabel","Script Name"), "Label of Script Name is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailScriptNameVal"), "Value doesn't exist for Script Name");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailTypeLabel","Type"), "Label of Detail type is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailTypeVal"), "Value doesn't exist for Detail type");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailSubTypeLabel","Subtype"), "Label of Sub Type is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailSubTypeVal"), "Value doesn't exist for Sub Type");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailInitaitedOnLabel","Initiated On"), "Label of InitaitedOn is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailInitaitedOnVal"), "Value doesn't exist for Initaited On");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailInitaitedByLabel","Initiated By"), "Label of InitaitedBy is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailInitaitedByVal"), "Value doesn't exist for Initaited By");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailCompletedOnLabel","Updated At"), "Label of Updated At is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailCompletedOnVal"), "Value doesn't exist for Updated At");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailDeviceRemidiatedLabel","Devices Remediated"), "Label of Device Remidiated is not matching");
        Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailDeviceRemidiatedVal"), "Value doesn't exist for Device Remidiated");
        wexScriptsPage.scrollTillViewScriptsPage("activityDetailDeviceHeader");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailDeviceHeader","Devices"), "Label of Devices header is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailDeviceHeader","Devices"), "Label of Devices header is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailDeviceHeader","Devices"), "Label of Devices header is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailKPIstatus1","Error"), "Label of error kpi is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailKPIstatus2","Success"), "Label of Success kpi is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailKPIstatus3","Not Processed"), "Label of Not Processed kpi is not matching");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailKPIstatus4","Canceled"), "Label of Canceled kpi is not matching");
        expectedColumnList = new ArrayList<String>(Arrays.asList(
                getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.configuration.options.device.name"),
                getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.configuration.options.serial.number"),
                getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activity.remediation.executionStatus"),
                getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activity.remediation.exitcode")));
        wexScriptsPage.verifyElementsOfScriptsPage("activityDetailsTable");
        wexScriptsPage.verifyElementsOfScriptsPage("activityDetailsTableRows");
        wexScriptsPage.verifyElementsOfScriptsPage("activityDetailsTableCount");
        Assert.assertTrue(wexScriptsPage.verifyTableColumns(expectedColumnList,"activityDetailsTableColumns"), "Table Columns are not as expected");
        wexScriptsPage.verifyElementsOfScriptsPage("activityListBreadCrumb");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("activityListBreadCrumb");
        LOGGER.info("Activity Details Verification Test Passed");
    }

    @Test(priority = 14, groups = { "REGRESSION_CONFIGSCRIPTS" }, description = "TestCaseID:CXXXXX", enabled = false)
    public final void verifyCancelAssignmentInActivityDetails() throws Exception {
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();

        // Navigate to the assignments page
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
        waitForPageLoaded();
        if(wexScriptsPage.verifyElementsOfScriptsPage("clearFilter")){
            wexScriptsPage.clearScriptFilter();
        }
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name column is not visible");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentTableNameColumn");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentTableNameColumn");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("assignmentTableNameColumn", ScriptVariables.CANCEL_ASSIGNMENT_NAME);
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name search box is not filled");
        sleeper(2000);
        waitForPageLoaded();
        wexScriptsPage.scrollTillViewScriptsPage("assignmentTabRowValue");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentTabRowValue",ScriptVariables.CANCEL_ASSIGNMENT_NAME),"The assignment did not get added successfully when tried to search after entering text in search box");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("runAssignmentEllipsis");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentEllipsisClick");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentEllipsisRun");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentModalConfirm");
        sleeper(1000);
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("runAssignmentToastMessageOne"),"The assignment run did not get triggered successfully");
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("runAssignmentToastMessageTwo"),"The assignment run did not get triggered successfully");
        waitForPageLoaded();

        // Redirect automatically to activity page and verify
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("activityNameSearchField");
        wexScriptsPage.clickOnElementsOfScriptsPage("activityNameSearchField");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("activityNameSearchField", ScriptVariables.ACTIVITY_NAME);
        sleeper(2000);
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("activityNameValue",ScriptVariables.ACTIVITY_NAME),"The activity did not get displayed in the list page.");
        wexScriptsPage.clickOnElementsOfScriptsPage("activityNameValue");

        // Verify activity details page and Click on the cancel button
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("activityDetailScriptInfoHeader"),"Activity details page did not loaded successfully");
        wexScriptsPage.clickOnElementsOfScriptsPage("cancelButton");

        // Confirm the cancellation
        wexScriptsPage.verifyElementsOfScriptsPage("confirmCancelButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("confirmCancelButton");

        // Verify the cancellation
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("activityStatusDetailsPage", "Canceled"), "The activity status did not change to Canceled");
        navigateToBack();
        waitForPageLoaded();
        wexScriptsPage.clearScriptFilter();
        wexScriptsPage.verifyElementsOfScriptsPage("activityNameSearchField");
        LOGGER.info("Cancel Assignment in Activity Details Test Passed");
    }

    @Test(priority = 15, groups = { "REGRESSION_CONFIGSCRIPTS" }, description = "TestCaseID:C53019911", enabled = false)
    public final void verifyCancelAssignmentInActivityDetailsForScheduledActivity() throws Exception {
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();

        // Navigate to the assignments page
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
        waitForPageLoaded();
        if(wexScriptsPage.verifyElementsOfScriptsPage("clearFilter")){
            wexScriptsPage.clearScriptFilter();
        }
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name column is not visible");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentTableNameColumn");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentTableNameColumn");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("assignmentTableNameColumn", ScriptVariables.CANCEL_ASSIGNMENT_NAME);
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name search box is not filled");
        sleeper(2000);
        waitForPageLoaded();
        wexScriptsPage.scrollTillViewScriptsPage("assignmentTabRowValue");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentTabRowValue",ScriptVariables.CANCEL_ASSIGNMENT_NAME),"The assignment did not get added successfully when tried to search after entering text in search box");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("runAssignmentEllipsis");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentEllipsisClick");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentEllipsisRun");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentModalConfirm");
        sleeper(1000);
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("runAssignmentToastMessageOne"),"The assignment run did not get triggered successfully");
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("runAssignmentToastMessageTwo"),"The assignment run did not get triggered successfully");
        waitForPageLoaded();

        // Redirect automatically to activity page and verify
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("activityNameSearchField");
        wexScriptsPage.clickOnElementsOfScriptsPage("activityNameSearchField");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("activityNameSearchField", ScriptVariables.ACTIVITY_NAME);
        sleeper(2000);
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("activityNameValue",ScriptVariables.ACTIVITY_NAME),"The activity did not get displayed in the list page.");
        wexScriptsPage.clickOnElementsOfScriptsPage("activityNameValue");

        // Verify activity details page and Click on the cancel button
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("activityDetailScriptInfoHeader"),"Activity details page did not loaded successfully");
        wexScriptsPage.clickOnElementsOfScriptsPage("cancelButton");

        // Confirm the cancellation
        wexScriptsPage.verifyElementsOfScriptsPage("confirmCancelButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("confirmCancelButton");

        // Verify the cancellation
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("activityStatusDetailsPage", "Canceled"), "The activity status did not change to Canceled");
        navigateToBack();
        waitForPageLoaded();
        wexScriptsPage.clearScriptFilter();
        wexScriptsPage.verifyElementsOfScriptsPage("activityNameSearchField");
        LOGGER.info("Cancel Assignment in Activity Details Test Passed");
    }

    @Test(priority = 1, groups = { "REGRESSION_CONFIGSCRIPTS" }, description = "TestCaseID:C53188352")
    public final void verifyDynamicGroupAssignmentDeviceAddedAndIOT() throws Exception {
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
        EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice();
        AWSIOTUtils awsIOTUtils = new AWSIOTUtils();

        // Navigate to the assignments page
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        waitForPageLoaded();
        //Go to assignments tab and validate count
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
        waitForPageLoaded();
        if(wexScriptsPage.verifyElementsOfScriptsPage("clearFilter")){
            wexScriptsPage.clearScriptFilter();
        }
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name column is not visible");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentTableNameColumn");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentTableNameColumn");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("assignmentTableNameColumn", ScriptVariables.DYNAMIC_GROUP_ASSIGNMENT_NAME);
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name search box is not filled");
        sleeper(2000);
        waitForPageLoaded();
        wexScriptsPage.scrollTillViewScriptsPage("assignmentTabRowValue");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentTabRowValue",ScriptVariables.DYNAMIC_GROUP_ASSIGNMENT_NAME),"The assignment did not get added successfully when tried to search after entering text in search box");
        wexScriptsPage.scrollTillViewScriptsPage("assignmentTabDeviceCountColumnValue");
        String deviceCountOfGroup = wexScriptsPage.getTextOfScriptsPageElement("assignmentTabDeviceCountColumnValue");
        int expectedCountOfDevice = (Integer.parseInt(deviceCountOfGroup)+1);
        LOGGER.info("Expected Device Count is: {}", expectedCountOfDevice);
        //Enroll Fake Device
        enrollFakeDevice.enrollIOTFakeDevice(
        		getEnvironmentSpecificData(System.getProperty("environment"),"SCRIPTS_COMPANY_NAME"),
                getEnvironmentSpecificData(System.getProperty("environment"),"SCRIPTS_CPIN"),
                getEnvironmentSpecificData(System.getProperty("environment"),"SCRIPTS_COMPANY_EMAIL"),
                getEnvironmentSpecificData(System.getProperty("environment"),"SCRIPTS_SERIAL_NO"),
                getEnvironmentSpecificData(System.getProperty("environment"),"SCRIPTS_DEVICE_ID"),
                getEnvironmentSpecificData(System.getProperty("environment"),"SCRIPTS_TENANT_ID"));
        sleeper(5000);
        refreshPage();
        waitForPageLoaded();
        sleeper(2000);
        LOGGER.info("Device Enrolled Successfully");
        wexScriptsPage.scrollTillViewScriptsPage("assignmentTabDeviceCountColumnValue");
        int actualDeviceCount =  Integer.parseInt(wexScriptsPage.getTextOfScriptsPageElement("assignmentTabDeviceCountColumnValue"));
        LOGGER.info("Actual Device Count is: {}", actualDeviceCount);
        Assert.assertTrue(actualDeviceCount == expectedCountOfDevice, "Device count is not matching with the expected count");

        //Verify the IOT job is sent or not to the device
        awsIOTUtils.getIotDataClient();
        String shadowName = "scheduled-job-cmd-config-script-remediation-"+getEnvironmentSpecificData(System.getProperty("environment"),"IOT_JOB_ID");
        String jsonShadowResponse = awsIOTUtils.getDeviceShadow(getEnvironmentSpecificData(System.getProperty("environment"),"SCRIPTS_DEVICE_ID"), shadowName);
        Assert.assertTrue(wexScriptsPage.verifyIOTJobPayload(jsonShadowResponse, "CREATE", "updateType"),"IOT JOB not created for the device");
        wexScriptsPage.clearScriptFilter();
        LOGGER.info("IOT Job payload verification successfully: {}","device count increase verified");
    }

    @Test(priority = 2, groups = { "REGRESSION_CONFIGSCRIPTS" }, description = "TestCaseID:C53188353")
    public final void verifyDynamicGroupAssignmentUpdateIOTEvent() throws Exception {
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
        EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice();
        AWSIOTUtils awsIOTUtils = new AWSIOTUtils();
        SoftAssert softAssert = new SoftAssert();
        String host;
        host = enrollFakeDevice.getEnvironment(System.getProperty("environment"));
        // Navigate to the assignments page
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        waitForPageLoaded();
        //Go to assignments tab and validate count
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
        waitForPageLoaded();
        if(wexScriptsPage.verifyElementsOfScriptsPage("clearFilter")){
            wexScriptsPage.clearScriptFilter();
        }
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name column is not visible");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentTableNameColumn");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentTableNameColumn");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("assignmentTableNameColumn", ScriptVariables.DYNAMIC_GROUP_ASSIGNMENT_NAME);
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name search box is not filled");
        sleeper(2000);
        waitForPageLoaded();
        wexScriptsPage.scrollTillViewScriptsPage("assignmentTabRowValue");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentTabRowValue",ScriptVariables.DYNAMIC_GROUP_ASSIGNMENT_NAME),"The assignment did not get added successfully when tried to search after entering text in search box");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentTabRowValue");
        //Schedule Section
        wexScriptsPage.scrollTillViewScriptsPage("assignmentDetailScheduleInfoHeader");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentDetailScheduleEditBtn");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentDetailScheduleEditBtn");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentDetailsScheduleSideBarLabel");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentDetailsScheduleEditRecurringDaysValue");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentDetailsEditScheduleRecurringTwoDays");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentDetailsScheduleEditSaveBtn");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentDetailsScheduleToast");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentDetailsScheduleUpdateToastClose");
        waitForPageLoaded();

        //Verify the IOT job is sent or not to the device
        awsIOTUtils.getIotDataClient();
        String shadowName = "scheduled-job-cmd-config-script-remediation-"+getEnvironmentSpecificData(System.getProperty("environment"),"IOT_JOB_ID");
        String jsonShadowResponse = awsIOTUtils.getDeviceShadow(getEnvironmentSpecificData(System.getProperty("environment"),"SCRIPTS_DEVICE_ID"), shadowName);
        softAssert.assertTrue(wexScriptsPage.verifyIOTJobPayload(jsonShadowResponse, "UPDATE", "updateType"),"IOT JOB not updated for the device");
        LOGGER.info("IOT Job payload verified successfully: {}",jsonShadowResponse);

        //Resetting the schedule for recurring One days
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentDetailScheduleEditBtn");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentDetailsScheduleSideBarLabel");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentDetailsScheduleEditRecurringDaysValue");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentDetailsEditScheduleRecurringOneDays");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentDetailsScheduleEditSaveBtn");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentDetailsScheduleToast");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentDetailsScheduleUpdateToastClose");
        Assert.assertTrue(wexScriptsPage.deleteTheDeviceFromDynamicGroup(getEnvironmentSpecificData(System.getProperty("environment"),"SCRIPTS_DEVICE_ID"),host), "Device delete failed");
        softAssert.assertAll();
        LOGGER.info("Test case to modify the Schedule and verify Update IOT job for dynamic group validated successfully");
    }

    @Test(priority = 18, groups = { "REGRESSION_CONFIGSCRIPTS"}, description = "TestCaseID:C51254515")
    public final void verifyArchiveAssignment() throws Exception {
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
        // Navigate to the assignments page
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        waitForPageLoaded();
        //Go to assignments tab and validate count
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
        waitForPageLoaded();
        if(wexScriptsPage.verifyElementsOfScriptsPage("clearFilter")){
            wexScriptsPage.clearScriptFilter();
        }
        String assignmentName = ScriptVariables.ASSIGNMENT_NAME + System.currentTimeMillis();
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageTitleScripts");
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList"),"Assignment List did not get loaded successfully");
        wexScriptsPage.clearScriptFilter();
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("addAssignment"),"add assignment button is not visible");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignment");

        //Verify Add Assignment modal is opened and select script table is shown
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("scriptSearchField"),"search script screen did not open");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptSearchField");
        wexScriptsPage.enterTextOnScriptsPage("scriptSearchField", ScriptVariables.ADDASSIGNMENTSCRIPT_NAME);
        wexScriptsPage.clickOnElementsOfScriptsPage("selectScriptRadioButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentNextButton");

        //Verify Add Assignment Group modal is opened and select group table is shown
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("groupSearchField"),"search group screen did not open");
        wexScriptsPage.clickOnElementsOfScriptsPage("groupSearchField");
        wexScriptsPage.enterTextOnScriptsPage("groupSearchField", ScriptVariables.ADDASSIGNMENTGROUP_NAME);
        wexScriptsPage.clickOnElementsOfScriptsPage("selectGroupRadioButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentNextButton");

        //Verify Add Assignment modal is opened and assignment name and description fields are shown
        Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("assignmentDescription"),"add assignment modal did not open");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("assignmentName");
        wexScriptsPage.enterTextOnScriptsPage("assignmentName", assignmentName);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("assignmentDescription");
        wexScriptsPage.enterTextOnScriptsPage("assignmentDescription", ScriptVariables.ASSIGNMENT_DESCRIPTION);
        wexScriptsPage.clickOnElementsOfScriptsPage("nextButton");

        //Click on Finish button
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("addAssignmentFinishButton"),"review screen did not open");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentFinishButton");
        waitForPageLoaded();

        //Verify the assignment is added successfully
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList"),"assignment list did not loaded");
        sleeper(3000);
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name column is not visible");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentTableNameColumn");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentTableNameColumn");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("assignmentTableNameColumn", assignmentName);
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name search box is not filled");
        sleeper(2000);
        waitForPageLoaded();
        wexScriptsPage.scrollTillViewScriptsPage("assignmentTabRowValue");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentTabRowValue",assignmentName),"The assignment did not get added successfully when tried to search after entering text in search box");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("runAssignmentEllipsis");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentEllipsisClick");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentEllipsisArchive");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentModalConfirm");
        sleeper(1000);
        waitForPageLoaded();
        sleeper(2000);
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentListPageAssignmentStatus","Archived"),"The assignment status did not change to Archived");
    }

    @Test(priority = 19, groups = { "REGRESSION_CONFIGSCRIPTS"}, description = "TestCaseID:C53378585")
    public final void verifyAddScriptPartner() throws Exception{
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"),"COMPANY_NAME"),CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"),"COMPANY_NAME"),CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"),"COMPANY_NAME"),CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        String scriptName = ScriptVariables.SCRIPT_NAME + System.currentTimeMillis();
        wexScriptsPage.verifyElementsOfScriptsPage("scriptsPageTitleScripts");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList");
        wexScriptsPage.clickOnElementsOfScriptsPage("addScript");
        wexScriptsPage.scrollTillViewScriptsPage("browseInput");
        wexScriptsPage.importScriptFile(ConstantPath.IMPORT_PATH+ ScriptVariables.SAMPLESCRIPT_UPLOAD);
        wexScriptsPage.verifyElementsOfScriptsPage("nextButton");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("nextButton");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("nextButton");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptName");
        wexScriptsPage.enterTextOnScriptsPage("scriptName", scriptName);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptSynopsis");
        wexScriptsPage.enterTextOnScriptsPage("scriptSynopsis", ScriptVariables.SCRIPT_SYNOPSIS);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptDescription");
        wexScriptsPage.enterTextOnScriptsPage("scriptDescription", ScriptVariables.SCRIPT_DESCRIPTION);
        wexScriptsPage.verifyElementsOfScriptsPage("nextButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("addScriptFinishButton");
        waitForPageLoaded();
        wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList");
        sleeper(3000);
        waitForPageLoaded();
        wexScriptsPage.verifyElementsOfScriptsPage("scriptNameSearchField");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("scriptNameSearchFields");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameSearchFields");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("scriptNameSearchFields", scriptName);
        sleeper(2000);
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptNameRowValue",scriptName),"The script did not get added successfully");
        wexScriptsPage.clearScriptFilter();
        LOGGER.info("Add Script Test Passed");
    }

    @Test(priority = 24, groups = { "REGRESSION_CONFIGSCRIPTS"}, description = "TestCaseID:C59816464, C59816465, C59816466")
    public final void verifyAddInputParameterForScripts() throws Exception {
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        String scriptName = ScriptVariables.INPUTPARAMETER_SCRIPT_NAME;
        wexScriptsPage.verifyElementsOfScriptsPage("scriptsPageTitleScripts");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList");
        sleeper(3000);
        wexScriptsPage.clickOnElementsOfScriptsPage("addScript");
        wexScriptsPage.scrollTillViewScriptsPage("browseInput");
        wexScriptsPage.importScriptFile(ConstantPath.IMPORT_PATH+ ScriptVariables.SAMPLESCRIPT_UPLOAD);
        wexScriptsPage.verifyElementsOfScriptsPage("nextButton");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("nextButton");
        sleeper(1000);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("nextButton");
        sleeper(1000);
        wexScriptsPage.waitForElementsOfScriptsPage("scriptName");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptName");
        wexScriptsPage.enterTextOnScriptsPage("scriptName", scriptName);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptSynopsis");
        wexScriptsPage.enterTextOnScriptsPage("scriptSynopsis", ScriptVariables.SCRIPT_SYNOPSIS);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptDescription");
        wexScriptsPage.enterTextOnScriptsPage("scriptDescription", ScriptVariables.SCRIPT_DESCRIPTION);
        wexScriptsPage.verifyElementsOfScriptsPage("nextButton");
        sleeper(2000);
        wexScriptsPage.clickOnElementsOfScriptsPage("addScriptFinishButton");
        waitForPageLoaded();
       sleeper(2000);
        wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList");
        waitForPageLoaded();
        sleeper(2000);
        wexScriptsPage.waitForElementsOfScriptsPage("scriptNameSearchField");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("scriptNameSearchFields", scriptName);
        sleeper(2000);
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptNameRowValue", scriptName), "The script did not get added successfully");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameRowValue");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptDetailsPageTitle");

        wexScriptsPage.scrollTillViewScriptsPage("addParameter");
        wexScriptsPage.verifyElementsOfScriptsPage("addParameter");
        wexScriptsPage.clickOnElementsOfScriptsPage("addParameter");

        //Parameter 1
        wexScriptsPage.verifyElementsOfScriptsPage("sidePanelAddParameterHeading");
        wexScriptsPage.verifyElementsOfScriptsPage("Parameter1");
        wexScriptsPage.clickOnElementsOfScriptsPage("Name1");
        wexScriptsPage.enterTextOnScriptsPage("Name1", "DataType");
        wexScriptsPage.waitForElementsOfScriptsPage("Value1");
        wexScriptsPage.clickOnElementsOfScriptsPage("Value1");
        wexScriptsPage.enterTextOnScriptsPage("Value1", "HealthReport");

        //Parameter 2
        wexScriptsPage.clickOnElementsOfScriptsPage("addParameterButton");
        wexScriptsPage.verifyElementsOfScriptsPage("Parameter2");
        wexScriptsPage.verifyElementsOfScriptsPage("Name2");
        wexScriptsPage.clickOnElementsOfScriptsPage("Name2");
        wexScriptsPage.enterTextOnScriptsPage("Name2", "Tags");
        wexScriptsPage.waitForElementsOfScriptsPage("Value2");
        wexScriptsPage.clickOnElementsOfScriptsPage("Value2");
        wexScriptsPage.enterTextOnScriptsPage("Value2", "dev,debug,urgent");

        //Parameter 3
        wexScriptsPage.clickOnElementsOfScriptsPage("addParameterButton");
        wexScriptsPage.verifyElementsOfScriptsPage("Parameter3");
        wexScriptsPage.verifyElementsOfScriptsPage("Name3");
        wexScriptsPage.clickOnElementsOfScriptsPage("Name3");
        wexScriptsPage.enterTextOnScriptsPage("Name3", "RetryCount");
        wexScriptsPage.clickOnElementsOfScriptsPage("Type3");
        wexScriptsPage.clickOnElementsOfScriptsPage("Type3Select");
        wexScriptsPage.waitForElementsOfScriptsPage("Value3");
        wexScriptsPage.clickOnElementsOfScriptsPage("Value3");
        wexScriptsPage.enterTextOnScriptsPage("Value3", "3");

        //Parameter 4
        wexScriptsPage.clickOnElementsOfScriptsPage("addParameterButton");
        wexScriptsPage.verifyElementsOfScriptsPage("Parameter4");
        wexScriptsPage.verifyElementsOfScriptsPage("Name4");
        wexScriptsPage.clickOnElementsOfScriptsPage("Name4");
        wexScriptsPage.enterTextOnScriptsPage("Name4", "VerboseMode");
        wexScriptsPage.scrollTillViewScriptsPage("Type4");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("Type4");
        wexScriptsPage.clickOnElementsOfScriptsPage("Type4Select");
        wexScriptsPage.waitForElementsOfScriptsPage("Value4");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("Value4");
        wexScriptsPage.clickOnElementsOfScriptsPage("Type4ValueSelect");

        //Parameter 5
        wexScriptsPage.clickOnElementsOfScriptsPage("addParameterButton");
        wexScriptsPage.verifyElementsOfScriptsPage("Parameter5");
        wexScriptsPage.verifyElementsOfScriptsPage("Name5");
        wexScriptsPage.clickOnElementsOfScriptsPage("Name5");
        wexScriptsPage.enterTextOnScriptsPage("Name5", "ForceExecution");
        wexScriptsPage.scrollTillViewScriptsPage("Type5");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("Type5");
        wexScriptsPage.clickOnElementsOfScriptsPage("Type5Select");
        wexScriptsPage.waitForElementsOfScriptsPage("Value5");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("Value5");
        wexScriptsPage.clickOnElementsOfScriptsPage("Type4ValueSelect");

        wexScriptsPage.clickOnElementsOfScriptsPage("saveParameterButton");
        Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("editParameterToastMessageOne"),"Script Updated");
        waitForPageLoaded();

        // Verify the parameters are added successfully and displayed on script details page
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptParameterName1", "DataType"), "The parameter DataType did not get added successfully");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptParameterValue1", "HealthReport"), "The parameter HealthReport did not get added successfully");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptParameterName2", "Tags"), "The parameter Tags did not get added successfully");
        //Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptParameterValue2", "dev,debug,urgent"), "The parameter dev,debug,urgent did not get added successfully");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptParameterName3", "RetryCount"), "The parameter RetryCount did not get added successfully");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptParameterValue3", "3"), "The parameter 3 did not get added successfully");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptParameterName4", "VerboseMode"), "The parameter VerboseMode did not get added successfully");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptParameterValue4", "False"), "The parameter True did not get added successfully");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptParameterName5", "ForceExecution"), "The parameter ForceExecution did not get added successfully");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptParameterValue5", "False"), "The parameter False did not get added successfully");

        //Delete Script
        wexScriptsPage.clickOnElementsOfScriptsPage("deleteScriptButtonScriptDetails");
        wexScriptsPage.clickOnElementsOfScriptsPage("deleteScriptButtonConfirm");
    }
    
    @Test(priority = 25, groups = { "REGRESSION_CONFIGSCRIPTS"}, description = "TestCaseID:C59816478")
    public final void verifyAddAssignmentWithInputParameters() throws Exception {
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        String assignmentName = ScriptVariables.ASSIGNMENT_NAME_INPUTPARAMETER + System.currentTimeMillis();
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageTitleScripts");
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList"),"Assignment List did not get loaded successfully");
        wexScriptsPage.clearScriptFilter();
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("addAssignment"),"add assignment button is not visible");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignment");

        //Select Script from List
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("scriptSearchField"),"search script screen did not open");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptSearchField");
        wexScriptsPage.enterTextOnScriptsPage("scriptSearchField", ScriptVariables.ADDASSIGNMENTSCRIPT_NAME_INPUTPARAMETER);
        sleeper(2000);
        wexScriptsPage.clickOnElementsOfScriptsPage("selectScriptRadioButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentNextButton");
        sleeper(2000);
        // input parameters section
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("addAssignmentInputParametersHeading"),"Input parameter section did not open");
        wexScriptsPage.verifyTextPresentOnElementScriptsPage("inputParameterName1","DataType (String)");
        wexScriptsPage.verifyTextPresentOnElementScriptsPage("inputParameterName2","Tags (String)");
        wexScriptsPage.verifyTextPresentOnElementScriptsPage("inputParameterName3","RetryCount (Integer)");
        wexScriptsPage.verifyTextPresentOnElementScriptsPage("inputParameterName4","VerboseMode (Boolean)");
        wexScriptsPage.verifyTextPresentOnElementScriptsPage("inputParameterName5","ForceExecution (Switch)");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentNextButton");

        // Select Group Section
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("groupSearchField"),"search group screen did not open");
        wexScriptsPage.clickOnElementsOfScriptsPage("groupSearchField");
        wexScriptsPage.enterTextOnScriptsPage("groupSearchField", ScriptVariables.ADDASSIGNMENTGROUP_NAME);
        wexScriptsPage.clickOnElementsOfScriptsPage("selectGroupRadioButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentNextButton");

        // Add assignment Name and description
        Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("assignmentDescription"),"add assignment modal did not open");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("assignmentName");
        wexScriptsPage.enterTextOnScriptsPage("assignmentName", assignmentName);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("assignmentDescription");
        wexScriptsPage.enterTextOnScriptsPage("assignmentDescription", ScriptVariables.ASSIGNMENT_DESCRIPTION);
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentNextButton");
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("addAssignmentFinishButton"),"review screen did not open");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentFinishButton");
        waitForPageLoaded();

        //Verify if Assignment is added successfully
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList"),"assignment list did not loaded");
        sleeper(3000);
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name column is not visible");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentTableNameColumn");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentTableNameColumn");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("assignmentTableNameColumn", assignmentName);
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name search box is not filled");
        sleeper(2000);
        waitForPageLoaded();
        wexScriptsPage.scrollTillViewScriptsPage("assignmentTabRowValue");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentTabRowValue",assignmentName),"The assignment did not get added successfully when tried to search after entering text in search box");

        //Archive the assignment created
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("runAssignmentEllipsis");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentEllipsisClick");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentEllipsisArchive");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentModalConfirm");
        sleeper(1000);
        waitForPageLoaded();
        sleeper(2000);
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentListPageAssignmentStatus","Archived"),"The assignment status did not change to Archived");
    }

    @Test(priority = 20 , groups = { "REGRESSION_CONFIGSCRIPTS"}, description = "TestCaseID:C59857397")
    public final void verifyAddScriptDuplicateScriptNameError() throws Exception{
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        
        String scriptName = ScriptVariables.DUPLICATE_SCRIPT_NAME;
        wexScriptsPage.verifyElementsOfScriptsPage("scriptsPageTitleScripts");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList");
        wexScriptsPage.clickOnElementsOfScriptsPage("addScript");
        wexScriptsPage.scrollTillViewScriptsPage("browseInput");
        wexScriptsPage.importScriptFile(ConstantPath.IMPORT_PATH+ ScriptVariables.SAMPLESCRIPT_UPLOAD);
        wexScriptsPage.verifyElementsOfScriptsPage("nextButton");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("nextButton");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("nextButton");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptName");
        wexScriptsPage.enterTextOnScriptsPage("scriptName", scriptName);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptSynopsis");
        wexScriptsPage.enterTextOnScriptsPage("scriptSynopsis", ScriptVariables.SCRIPT_SYNOPSIS);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptDescription");
        wexScriptsPage.enterTextOnScriptsPage("scriptDescription", ScriptVariables.SCRIPT_DESCRIPTION);
        wexScriptsPage.verifyElementsOfScriptsPage("nextButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("addScriptFinishButton");
        Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("errorrMessageDuplicateScript","Script name and version already exist. Change the version to proceed."), "Error message was not triggered");
		LOGGER.info("Duplicate script name Test passed");
		wexScriptsPage.clickOnElementsOfScriptsPage("cancelBtn");
		
    }
       
    @Test(priority = 21 , groups = { "REGRESSION_CONFIGSCRIPTS" }, description = "TestCaseID:C59861114")
    public final void verifyAddScriptDuplicateScriptNameDiffVersion() throws Exception{
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        String scriptName = ScriptVariables.DUPLICATE_SCRIPT_NAME;
        String version = "v" + System.currentTimeMillis();
        wexScriptsPage.verifyElementsOfScriptsPage("scriptsPageTitleScripts");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList");
        wexScriptsPage.clickOnElementsOfScriptsPage("addScript");
        wexScriptsPage.scrollTillViewScriptsPage("browseInput");
        wexScriptsPage.importScriptFile(ConstantPath.IMPORT_PATH+ ScriptVariables.SAMPLESCRIPT_UPLOAD);
        wexScriptsPage.verifyElementsOfScriptsPage("nextButton");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("nextButton");
        wexScriptsPage.enterTextOnScriptsPage("maxRunTimeCreate", ScriptVariables.MAX_RUN_TIME);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("nextButton");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptName");
        wexScriptsPage.enterTextOnScriptsPage("scriptName", scriptName);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptVersion");
        wexScriptsPage.enterTextOnScriptsPage("scriptVersion",version);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptSynopsis");
        wexScriptsPage.enterTextOnScriptsPage("scriptSynopsis", ScriptVariables.SCRIPT_SYNOPSIS);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptDescription");
        wexScriptsPage.enterTextOnScriptsPage("scriptDescription", ScriptVariables.SCRIPT_DESCRIPTION);
        wexScriptsPage.clickOnElementsOfScriptsPage("addScriptFinishButton");
        waitForPageLoaded();
        wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList");
        sleeper(3000);
        waitForPageLoaded();
        wexScriptsPage.verifyElementsOfScriptsPage("scriptVersionSearchField");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("scriptVersionSearchField");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptVersionSearchField");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("scriptVersionSearchField", version);
        sleeper(2000);
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptNameRowValue",scriptName),"The script did not get added successfully");
        wexScriptsPage.clearScriptFilter();
        LOGGER.info("Add Duplicate Script anme but with different version Test Passed");
		
    }
    
    @Test(priority = 22 , groups = { "REGRESSION_CONFIGSCRIPTS"}, description = "TestCaseID:C59857262")
    public final void verifyScriptNameEditFunctionality() throws Exception{
    	 WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
         WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();

         login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
         leftSideMenuVerification();
//         if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//             dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//         }else{
//             dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//         }
         dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
         String searchScriptName = ScriptVariables.SCRIPT_NAME + wexScriptsPage.getCurrentHour();
         String scriptName = ScriptVariables.SCRIPT_NAME + System.currentTimeMillis();
         String version = "v" + System.currentTimeMillis();
         wexScriptsPage.clearScriptFilter();
         wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameSearchFields");
         sleeper(1000);
         wexScriptsPage.enterTextOnScriptsPage("scriptNameSearchFields", searchScriptName);
         sleeper(2000);         
         Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptNameRowValue",ScriptVariables.SCRIPT_NAME),"The script did not get displayed in the list page.");
         wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameRowValue");
         waitForPageLoaded();
         wexScriptsPage.clickOnElementsOfScriptsPage("scriptDetailsOverviewEditBtn");
         wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptDetailsOverviewEditName");
         wexScriptsPage.enterTextOnScriptsPage("scriptDetailsOverviewEditName",scriptName);
         wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptDetailsOverviewEditVersion");
         wexScriptsPage.enterTextOnScriptsPage("scriptDetailsOverviewEditVersion",version);
         wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptDeatilsOverviewEditApply");
         
         Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("scriptRenameToastMessage"),"The Script could not be renamed");
         sleeper(3000);
         Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailScriptNameVal", scriptName), "Script was not renamed");
         Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailVersionVal",version), "Version was not updated");
    }  
    
    @Test(priority = 23 , groups = { "REGRESSION_CONFIGSCRIPTS"}, description = "TestCaseID:C60071187")
    public final void verifyUpdateScrpitDuplicateScriptName() throws Exception{
    	 WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
         WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();

         login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
         leftSideMenuVerification();
//         if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//             dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//         }else{
//             dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//         }
         dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
         String searchScriptName = ScriptVariables.SCRIPT_NAME + wexScriptsPage.getCurrentHour();
         String scriptName = ScriptVariables.DUPLICATE_SCRIPT_NAME;
         String version = ScriptVariables.BASIC_VERSION;
         wexScriptsPage.clearScriptFilter();
         wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameSearchFields");
         sleeper(1000);
         wexScriptsPage.enterTextOnScriptsPage("scriptNameSearchFields", searchScriptName);
         sleeper(2000);         
         Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptNameRowValue",ScriptVariables.SCRIPT_NAME),"The script did not get displayed in the list page.");
         wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameRowValue");
         waitForPageLoaded();
         wexScriptsPage.clickOnElementsOfScriptsPage("scriptDetailsOverviewEditBtn");
         wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptDetailsOverviewEditName");
         wexScriptsPage.enterTextOnScriptsPage("scriptDetailsOverviewEditName",scriptName);
         wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptDetailsOverviewEditVersion");
         wexScriptsPage.enterTextOnScriptsPage("scriptDetailsOverviewEditVersion",version);
         wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptDeatilsOverviewEditApply");
         sleeper(2000);  
         Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("errorrMessageDuplicateScript","Script name and version already exist. Change the version to proceed."), "Error message was not triggered");
 		 LOGGER.info("Update Script with Duplicate name test passed");
 		 wexScriptsPage.clickOnElementsOfScriptsPage("btnSidebarCancel");
    }
  
    @Test(priority = 24 , groups = { "REGRESSION_CONFIGSCRIPTS"}, description = "TestCaseID:C60501741")
    public final void verifyMaxRunTimeEdit() throws Exception{
		 WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		 WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();

		 login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
		 leftSideMenuVerification();
//		 if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//			 dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//		 }else{
//			 dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//		 }
		 dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
		 String searchScriptName = ScriptVariables.SCRIPT_NAME + wexScriptsPage.getCurrentHour();
		 wexScriptsPage.clearScriptFilter();
		 wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameSearchFields");
		 sleeper(1000);
		 wexScriptsPage.enterTextOnScriptsPage("scriptNameSearchFields", searchScriptName);
		 sleeper(2000);         
		 Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptNameRowValue",ScriptVariables.SCRIPT_NAME),"The script did not get displayed in the list page.");
		 wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameRowValue");
		 waitForPageLoaded();
		 
		 wexScriptsPage.clickOnElementsOfScriptsPage("scriptDetailsExecutionPropEditBtn");
		 sleeper(3000);
		 wexScriptsPage.clearTextOnScriptsPage("scriptDetailsMaxRuntimeEdit");
		 Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("scriptDetailsMAxRunTimeErrorMsg"),"Error meassge was not displayed");
		 LOGGER.info("Max Runtime value file dis tested withempty value and verified the error messgae");
		 
		 wexScriptsPage.enterTextOnScriptsPage("scriptDetailsMaxRuntimeEdit", "1020");
		 Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("scriptDetailsMAxRunTimeErrorMsg"),"Error meassge was not displayed");
		 LOGGER.info("Max Runtime value given more than expected range and verified the error messgae");

		 wexScriptsPage.enterTextOnScriptsPage("scriptDetailsMaxRuntimeEdit", ScriptVariables.MAX_RUN_TIME);
		 LOGGER.info("Max Run Time Edit Test Passed");
		 wexScriptsPage.clickByJavaScriptOnScriptsPage("scriptDeatilsOverviewEditApply");
		 
		 Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("scriptRenameToastMessage"),"The Script could not be updated");
		 Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("scriptDetailMaxRunTimeVal", ScriptVariables.MAX_RUN_TIME), "MaxRunTime was not updated");
		 
    }
    
    @Test(priority = 26 , groups = { "REGRESSION_CONFIGSCRIPTS"}, description = "TestCaseID:C60548791")
    public final void verifyMaxRunTimeAssignmentDetails() throws Exception{
    	 WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
         WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();

         login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
         leftSideMenuVerification();
//         if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
//             dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//         }else{
//             dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//         }
         dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
         wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
         wexScriptsPage.clearScriptFilter();
         wexScriptsPage.clickOnElementsOfScriptsPage("assignmentNameSearchField");
         sleeper(1000);
         wexScriptsPage.enterTextOnScriptsPage("assignmentNameSearchField", ScriptVariables.ASSIGNMENT_NAME);
         sleeper(2000);
         Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentNameRowValue",ScriptVariables.ASSIGNMENT_NAME),"The assignment did not get displayed in the list page.");
         wexScriptsPage.clickOnElementsOfScriptsPage("assignmentNameRowValue");

         wexScriptsPage.scrollTillViewScriptsPage("assignmentDetailScriptInfoHeader");
         Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailVersion","Version"), "Label of Assignment Script Version is not matching");
         Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailVersionVal"), "Value doesn't exist for Script Version");

         wexScriptsPage.scrollTillViewScriptsPage("assignmentDetailAssignmentInfoHeader");
         Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentInfoHeader","Assignment Information"), "Label of Assignment Information header is not matching");

         Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailmaxRunTime","Maximum Runtime in Minutes"), "Label of maxRunTime field is not matching");
         Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailmaxRunTimeValue"), "Value doesn't exist for maxRunTime");

    }

    @Test(priority = 27, groups = { "REGRESSION_CONFIGSCRIPTS"}, description = "TestCaseID:C59816464")
    public final void verifyEditInputParameterForScripts() throws Exception {
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        String scriptName = ScriptVariables.SCRIPT_NAME_EDITINPUTPARAMETER;

        // Navigate to script details page
        wexScriptsPage.verifyElementsOfScriptsPage("scriptsPageTitleScripts");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptPageList");
        sleeper(3000);
        waitForPageLoaded();
        wexScriptsPage.verifyElementsOfScriptsPage("scriptNameSearchField");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("scriptNameSearchFields");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameSearchFields");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("scriptNameSearchFields", scriptName);
        sleeper(2000);
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("scriptNameRowValue", scriptName), "The script was not found");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptNameRowValue");
        wexScriptsPage.verifyElementsOfScriptsPage("scriptDetailsPageTitle");

        // Verify existing parameters are present before editing
        wexScriptsPage.scrollTillViewScriptsPage("editInputParameter");
        String dataTypeValue = wexScriptsPage.getTextOfScriptsPageElement("scriptParameterName1");
        wexScriptsPage.assertValueNotBlankOrNull(dataTypeValue);

        String tagsValue = wexScriptsPage.getTextOfScriptsPageElement("scriptParameterName2");
        wexScriptsPage.assertValueNotBlankOrNull(tagsValue);

        String retryCountValue = wexScriptsPage.getTextOfScriptsPageElement("scriptParameterName3");
        wexScriptsPage.assertValueNotBlankOrNull(retryCountValue);


        // Edit Parameter 1 - DataType
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("editInputParameter");

        wexScriptsPage.clickOnElementsOfScriptsPage("Name1");
        wexScriptsPage.enterTextOnScriptsPage("Name1", "ReportType" + ThreadLocalRandom.current().nextInt(10000, 99999));
        wexScriptsPage.clickOnElementsOfScriptsPage("Value1");
        wexScriptsPage.enterTextOnScriptsPage("Value1", "SecurityReport" + ThreadLocalRandom.current().nextInt(10000, 99999));

        // Edit Parameter 2 - Tags

        wexScriptsPage.clickOnElementsOfScriptsPage("Name2");
        wexScriptsPage.enterTextOnScriptsPage("Name2", "Environment" + ThreadLocalRandom.current().nextInt(10000, 99999));
        wexScriptsPage.scrollTillViewScriptsPage("Value2");
        wexScriptsPage.clickOnElementsOfScriptsPage("Value2");
        wexScriptsPage.enterTextOnScriptsPage("Value2", "production,staging,testing" + ThreadLocalRandom.current().nextInt(10000, 99999));

        // Edit Parameter 3 - RetryCount (change type and value)

        wexScriptsPage.clickOnElementsOfScriptsPage("Name3");
        wexScriptsPage.enterTextOnScriptsPage("Name3", "MaxRetries" + ThreadLocalRandom.current().nextInt(10000, 99999));
        wexScriptsPage.scrollTillViewScriptsPage("Type3");
        
        wexScriptsPage.clickOnElementsOfScriptsPage("Type3");
        wexScriptsPage.clickOnElementsOfScriptsPage("Type3Select");
        wexScriptsPage.clickOnElementsOfScriptsPage("Value3");
        wexScriptsPage.enterTextOnScriptsPage("Value3", String.valueOf(ThreadLocalRandom.current().nextInt(1, 21)));

        // Save the edited parameters
        wexScriptsPage.clickOnElementsOfScriptsPage("saveParameterButton");
        Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("editParameterToastMessageOne"),"Script Updated");
        waitForPageLoaded();

        // Verify the edited parameters are displayed correctly on script details page
        Assert.assertNotEquals(wexScriptsPage.getTextOfScriptsPageElement("scriptParameterName1"),dataTypeValue, "The parameter ReportType did not get updated successfully");
        Assert.assertNotEquals(wexScriptsPage.getTextOfScriptsPageElement("scriptParameterName2"),tagsValue, "The parameter tagsValue did not get updated successfully");
        Assert.assertNotEquals(wexScriptsPage.getTextOfScriptsPageElement("scriptParameterName3"),retryCountValue, "The parameter retryCountValue did not get updated successfully");
      }

    @Test(priority = 28, groups = { "REGRESSION_CONFIGSCRIPTS" }, description = "TestCaseID:C43550144",enabled=false)
    public final void verifyWorkforceExperienceAddScriptAssignmentForEntraIDGroup() throws Exception{
        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
//        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
//            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
//        }else{
//            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
        String assignmentName = ScriptVariables.SCRIPT_ASSIGNMENT_WITH_ENTRAID_GROUP_NAME + System.currentTimeMillis();
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
        wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageTitleScripts");
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList"),"Assignment List did not get loaded successfully");
        wexScriptsPage.clearScriptFilter();
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("addAssignment"),"add assignment button is not visible");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignment");

        //Verify Add Assignment modal is opened and select script table is shown
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("scriptSearchField"),"search script screen did not open");
        wexScriptsPage.clickOnElementsOfScriptsPage("scriptSearchField");
        wexScriptsPage.enterTextOnScriptsPage("scriptSearchField", ScriptVariables.ADDASSIGNMENTSCRIPT_NAME);
        sleeper(2000);
        wexScriptsPage.clickOnElementsOfScriptsPage("selectScriptRadioButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentNextButton");

        //Verify EntraID option is showing on Dropdown which is showing on Assign Group to Script page 
        sleeper(3000);
        wexScriptsPage.waitForElementsOfScriptsPage("selectGroupdropDownId");
        wexScriptsPage.clickOnElementsOfScriptsPage("selectGroupdropDownId");
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("selectEntraIDGroup"),"Entra ID group option is not showing on dropdwon in Assignment creation page");
        wexScriptsPage.clickOnElementsOfScriptsPage("selectEntraIDGroup");
        
        //Verify Add Assignment Group modal is opened and select group table is shown
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("groupSearchField"),"search group screen did not open");
        wexScriptsPage.clickOnElementsOfScriptsPage("groupSearchField");
        wexScriptsPage.enterTextOnScriptsPage("groupSearchField", ScriptVariables.ADDASSIGNMENT_ENTRAID_GROUP_NAME);
        wexScriptsPage.clickOnElementsOfScriptsPage("selectGroupRadioButton");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentNextButton");

        //Verify Add Assignment modal is opened and assignment name and description fields are shown
        Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("assignmentDescription"),"add assignment modal did not open");
        wexScriptsPage.clickByJavaScriptOnScriptsPage("assignmentName");
        wexScriptsPage.enterTextOnScriptsPage("assignmentName", assignmentName);
        wexScriptsPage.clickByJavaScriptOnScriptsPage("assignmentDescription");
        wexScriptsPage.enterTextOnScriptsPage("assignmentDescription", ScriptVariables.ASSIGNMENT_DESCRIPTION);
        wexScriptsPage.clickOnElementsOfScriptsPage("nextButton");

        //Click on Finish button
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("addAssignmentFinishButton"),"review screen did not open");
        wexScriptsPage.clickOnElementsOfScriptsPage("addAssignmentFinishButton");
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList"),"assignment list did not loaded");
        sleeper(3000);
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name column is not visible");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("assignmentTableNameColumn");
        wexScriptsPage.clickOnElementsOfScriptsPage("assignmentTableNameColumn");
        sleeper(1000);
        wexScriptsPage.enterTextOnScriptsPage("assignmentTableNameColumn", assignmentName);
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("assignmentTableNameColumn"),"assignment name search box is not filled");
        sleeper(2000);
        waitForPageLoaded();
        wexScriptsPage.scrollTillViewScriptsPage("assignmentTabRowValue");
        Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentTabRowValue",assignmentName),"The assignment did not get added successfully when tried to search after entering text in search box");
        wexScriptsPage.mouseHoverAndClickOfScriptsPage("runAssignmentEllipsis");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentEllipsisClick");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentEllipsisRun");
        wexScriptsPage.clickOnElementsOfScriptsPage("runAssignmentModalConfirm");
        sleeper(1000);
        Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("runAssignmentToastMessageOne"),"The assignment run did not get triggered successfully");
        Assert.assertTrue(wexScriptsPage.verifyElementIsDisplayedOfScriptsPage("runAssignmentToastMessageTwo"),"The assignment run did not get triggered successfully");
        waitForPageLoaded();
        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("activityPageTitleScripts"),"User not redirected to Activity tab successfully");
        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            dashboardPage.clickOnElementsOfDashboardPage("fleetScripts");
            wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
            wexScriptsPage.clickOnElementsOfScriptsPage("clearFilter");
            wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList");
        }
        else {
            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
            wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
            wexScriptsPage.clickOnElementsOfScriptsPage("clearFilter");
            wexScriptsPage.verifyElementsOfScriptsPage("assignmentPageList");
        }
        
    }
}