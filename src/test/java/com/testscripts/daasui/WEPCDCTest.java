package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.WEPPulsesCreationPageVariables;
import com.daasui.pages.AnalyticsPage;
import com.daasui.pages.WEPDeviceDetailsPage;
import com.daasui.pages.WEPDeviceListPage;
import com.daasui.pages.WEPPulsesCreationPage;
import com.daasui.pages.WEPPulsesListPage;
import com.daasui.pages.WEXDashboardPage;

import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WEPCDCTest extends CommonTest {
    private static Logger LOGGER = LogManager.getLogger(WEPCDCTest.class);

    @DataProvider
    public Object[][] getLoginData() {
        Object[][] data = new Object[2][2];
        data[0][0] = "PARTNER_EMAIL";
        data[0][1] = "PARTNER_PASSWORD";
        data[1][0] = "COMPANY_EMAIL";
        data[1][1] = "COMPANY_PASSWORD";
        return data;
    }

    @Test(priority = 1, groups = {"REGRESSION_PULSES","REGRESSION_CDC"}, description = "TestCase ID:C66229638")
    public final void verifyCustomDataReportsonAnalyticsdashboard() throws Exception {
        login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
        SoftAssert softAssert = new SoftAssert();
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("Customdatareport");
        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Customdatareport"), "Dashboard does not have custom data report tab");
        LOGGER.info("custom data report tab verified successfully");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Customdatareport");
        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("AddReport"), "Dashboard does not Add report button");
        LOGGER.info("verifyCustomDataReportsTabonAnalyticsdashboard Verified Successfully");
//        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("createdon"), (getTextLanguage(LanguageCode,"daas_ui","wex.scriptname.title_name")), "Script name column name is not matching");
        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("createdon"), "custom report does not have created on column name");
        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("createdBy"), "custom report does not have created By column name");
        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ReportName"), "custom report does not have ReportName column name");
        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ReportStatus"), "custom report does not have ReportStatus column name");
        // WEPPulsesCreationPage.scrollDownCharts();
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("createdon");
        sleeper(3000);
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("ReportName");



    }




    @Test(priority = 2, groups = {"REGRESSION_PULSES","REGRESSION_CDC","PRODUCTION_PULSES_CDC"}, description = "TestCase ID:C65897607")
    public final void verifyCreateCustomReportElements() throws Exception {
        login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("customDataReports");
        sleeper(2000);
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("addCustomDataReport");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("reportTitle"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.create.add.report")), "Report page title not matching");
        LOGGER.info("Redircetd to custom data report page successfully");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("reportSubTitle"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.create.select.script")), "Report page sub title not matching");

        //verify elements on report page
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("col1"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.script.name")), "Script name column name is not matching");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("col2"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.script.version.number")), "Version Number column name is not matching");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Col3"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.script.operation.name")), "Operation name colum name is not matching");

        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("cancelBtn"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.report.Cancel")), "Cancel button not present");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("nextBtn"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.report.Next")), "Next button not present");
//
        //select a script

        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("searchscript");
        WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("searchscript","battery script");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scriptSelection");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("nextBtn");
        //verify elements on step 2 report page
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("reportPageSubheading"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.select.audience")), "Step 2 Report page sub title not matching");
        String actualSubheadingText = WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("reportPageSubheading");
        String expectedSubheadingText = getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.select.audience");
        if (actualSubheadingText.equals(expectedSubheadingText)) {
            // First set of actions when condition is true
            LOGGER.info("Report page subheading matches with selection of Audience. Performing actions for Action type script.");
            softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("col3"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.group.name")), "Group name title not matching");
            softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("col4"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.group.type")), "Group type title not matching");


        }else {
            LOGGER.info("Report page subheading matches with selection of Parameters. Performing actions for Evaluation type script.");
            WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("inputParamLabel");
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("inputParamTextbox");

        }

        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("cancelBtn"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.report.Cancel")), "Cancel button not present");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("nextBtn"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.report.Next")), "Next button not present");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("backBtn"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.report.Back")), "Back button not present");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("group1");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("group2");
//        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("toggleforselectedonly");
//        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("toggleforselectedonly");
        WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("Recalculate");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Recalculate");
        LOGGER.info("Calculated the total audience count successfully");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("nextBtn");
        WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("Enterreportname","New Custom Report"+generateRandomNumber());
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Createreport");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Toastmsgforreportcreation"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.report.create.success.toast")), "report creation toast msg is not received ");
        softAssert.assertAll();
        LOGGER.info("Custom Data report creation flow verified successfully");

    }





    @Test(priority = 4, groups = {"REGRESSION_PULSES","REGRESSION_CDC"}, description = "TestCase ID:C65899258")
    public final void ValidateAlertMessageonAddingReportBeyondMaximumAllowedforCustomDataReports() throws Exception {
        login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
        SoftAssert softAssert = new SoftAssert();
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("Customdatareport");
        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Customdatareport"), "Dashboard does not have custom data report tab");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Customdatareport");
        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("AddReport"), "Dashboard does not have custom data report tab");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("AddReport");
        WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("Toastmsgforreprot");
//        softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("Toastmsgforreprot"),"Toast notification haven't appeared in the UI after clicking on Add for maximum limit reach ");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Toastmsgforreprot"),
                getTextLanguage(LanguageCode, "daas_ui", "analytics.custom.data.max.report.limit.reached")," Maximum report limit reached toast message is not matching");



        LOGGER.info("Verification of the toast notification displayed upon reaching the maximum limit for adding reports was completed successfully.");
        softAssert.assertAll();
        LOGGER.info("ValidateAlertMessageonAddingReportBeyondMaximumAllowedforCustomDataReports Verified Successfully");


    }

    @Test(priority = 5, groups = {"REGRESSION_PULSES","REGRESSION_CDC","PRODUCTION_PULSES_CDC"}, description = "TestCase ID: C67894131")
    public final void verifyCustomDataReportExportFunctionality() throws Exception {
        login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();

        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();

        // Navigate to custom data reports
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("customDataReports");
        LOGGER.info("Navigated to Custom Data Reports page successfully");
        WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("customDataReports");
        sleeper(2000);

        // Verify export button is present
        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Selectfirstreport"), "First report is not available to select");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Selectfirstreport");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("exportReportBtn"), "Export Report button is not present on Custom Data Reports page");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("exportReportBtn");
        LOGGER.info("Clicked on Export Report button");
        // Verify export notification message
        sleeper(2000);
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Toastmsgforexportreprot"),
                getTextLanguage(LanguageCode, "daas_ui", "customDataReports.export.export_in_progress"),"Export success message not matching");
        softAssert.assertAll();
        LOGGER.info("CSV export functionality verified successfully");
    }

    /**
     * This method will verify the Export Functionality for Custom Data Reports
     * @throws Exception
     */
    @Test(priority = 6, groups = {"REGRESSION_PULSES","REGRESSION_CDC","PRODUCTION_PULSES_CDC"}, description = "TestCase ID: C67894131")
    public final void verifyCustomDataReportExportFRomDetailspage() throws Exception {
        login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();

        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();

        // Navigate to custom data reports
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("customDataReports");
        LOGGER.info("Navigated to Custom Data Reports page successfully");
        WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("customDataReports");

        sleeper(2000);
        //check export from report details page
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Selectoffirstreportbyname");
        waitForPageLoaded();
        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("exportRDetailsPage"), "Export button not visible on Report Details Page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("exportRDetailsPage"), "Export Report button is not present on Custom Data Reports page");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("exportRDetailsPage");
        LOGGER.info("Clicked on Export from Report Details Page");

        softAssert.assertAll();
        LOGGER.info("CSV export functionality verified successfully");
    }


    @Test(priority = 7, groups = {"REGRESSION_PULSES","REGRESSION_CDC","PRODUCTION_PULSES_CDC"}, description = "TestCase ID:C67193186")
    public final void verifycancelCustomReportFunctionlity() throws Exception {
        login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("customDataReports");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Selectfirstreport");
        String ReportStatus = WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("getreportstatus");
        LOGGER.info("Report status is : "+ ReportStatus);
        if ("In Progress".equalsIgnoreCase(ReportStatus)) {
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Selectoffirstreportbyname");
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("CancelReport");
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmationcancelreport");
            softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Toastmsgforreprot"), (getTextLanguage(LanguageCode,"daas_ui","customDataReports.toastNotification.cancelDataCollectionTitle")), "report Cancel toast msg is not received ");
            WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Cancelreportdetails");
            String CancelReportdetails = WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Cancelreportdetails");
            LOGGER.info("Report status is : "+ CancelReportdetails);
            System.out.println("Report is cancelled  successfully");
        } else if ("Cancelled".equalsIgnoreCase(ReportStatus)
                || "expire".equalsIgnoreCase(ReportStatus)
                || "Completed".equalsIgnoreCase(ReportStatus)) {
            LOGGER.info(("Report can't be cancelled as report is in "+ ReportStatus + " status"));
        }
        softAssert.assertAll();

        LOGGER.info("Custom Data report Cancelation flow verified successfully");

    }

    @Test(priority = 8, groups = {"REGRESSION_PULSES","REGRESSION_CDC","PRODUCTION_PULSES_CDC"}, description = "TestCase ID:C67194137")
    public final void verifyRestartCustomReportFunctionlity() throws Exception {
        login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("customDataReports");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Selectfirstreport");
        String ReportStatus = WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("getreportstatus");
        LOGGER.info("Report status is : "+ ReportStatus);
        if ("In Progress".equalsIgnoreCase(ReportStatus)) {
            LOGGER.info("Report can't be restarted as report is in In progress");
        } else if ("Cancelled".equalsIgnoreCase(ReportStatus)
                || "expire".equalsIgnoreCase(ReportStatus)
                || "Completed".equalsIgnoreCase(ReportStatus)) {
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Selectoffirstreportbyname");
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("RestartReport");
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmationrestartreport");
            softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Toastmsgforreprot"), (getTextLanguage(LanguageCode,"daas_ui","customDataReports.toastNotification.restartDataCollectionTitle")), "report Restart toast msg is not received ");
            LOGGER.info(("Report Restarted successfully"));
        }
        softAssert.assertAll();

        LOGGER.info("Custom Data report Restart flow verified successfully");

    }

    @Test(priority = 9, groups = {"REGRESSION_PULSES","REGRESSION_CDC","PRODUCTION_PULSES_CDC"}, description = "TestCase ID:C67187614")
    public final void verifyDeleteandcancelCustomReportFunctionlity() throws Exception {
        login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("customDataReports");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Selectfirstreport");
        String ReportStatus = WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("getreportstatus");
        LOGGER.info("Report status is : "+ ReportStatus);
        if ("In Progress".equalsIgnoreCase(ReportStatus)) {
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Selectoffirstreportbyname");
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("CancelReport");
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmationcancelreport");
            softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Toastmsgforreprot"), (getTextLanguage(LanguageCode,"daas_ui","customDataReports.toastNotification.cancelDataCollectionTitle")), "report Cancel toast msg is not received ");
            WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Cancelreportdetails");
            String CancelReportdetails = WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Cancelreportdetails");
            LOGGER.info("Report status is : "+ CancelReportdetails);
//            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("canceltoastmessage");
            sleeper(2000);
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("clicktogetdeleteoption");
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("deletereportfromreportview");
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmationdeletereport");
//            softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Toastmsgforreprot"), (getTextLanguage(LanguageCode,"daas_ui","customDataReports.toastNotification.reportDeletedTitle")), "report Delete toast msg is not received ");
            System.out.println("Report is cancelled and then deleted successfully");
        } else if ("Cancelled".equalsIgnoreCase(ReportStatus)
                || "expire".equalsIgnoreCase(ReportStatus)
                || "Completed".equalsIgnoreCase(ReportStatus)) {
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Selectfirstreport");
            sleeper(2000);
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("DeleteReport");
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmationdeletereport");
//            softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Toastmsgforreprot"), (getTextLanguage(LanguageCode,"daas_ui","customDataReports.toastNotification.reportDeletedTitle")), "report Delete toast msg is not received ");
            LOGGER.info(("Report deleted successfully"));
        }
        softAssert.assertAll();

        LOGGER.info("Custom Data report Deletion flow verified successfully");

    }



    @Test(priority = 10, groups = {"REGRESSION_PULSES","REGRESSION_CDC"}, description = "TestCase ID:C67894383,C67194197")
    public final void verifyAuditlogsforCustomReportFunctionlity() throws Exception {
        login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("SettingTab");
        sleeper(2000);
        WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("LogsTab");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("LogsTab");
        sleeper(2000);
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Datepicker");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("currentDateOnCalender");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("currentDateOnCalender");

        softAssert.assertAll();

        LOGGER.info("Custom Data report Deletion flow verified successfully");
    }

    /**
     * This method will verify the Export Functionality for Custom Data Reports
     * @throws Exception
     */



    /**
     * This method will verify the DCustom Data tab on device details page
     * @throws Exception
     */
    @Test(priority = 11, groups = {"REGRESSION_PULSES","REGRESSION_CDC"}, description = "TestCase ID:C68101318", enabled=true)
    public final void verifyCustomDataTabDeviceDetailspage() throws Exception {
        login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        //String serialNumber = getEnvironmentSpecificData(System.getProperty("environment"), "TEST_DEVICE_SERIAL_NUM");
        JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
//        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
//            LOGGER.info("Left side menu is not collapsed as expected");
//        }else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
//            wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
//            LOGGER.info("Left side menu is expanded successfully");
//        }
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyCustomDataTabDeviceDetailspage()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        //wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", serialNumber);
        sleeper(2000);
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberText");
        waitForPageLoaded();
        js.executeScript("document.body.style.zoom='67%'");
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("deviceDetailsHeader");
        if(WEPPulsesCreationPage.verifyElementIsEnableonWEPPulsesCreationPage("CDCtabDEviceDetails")) {
	        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("CDCtabDEviceDetails"), "CDC tab not visible on Report Details Page");
	        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("CDCtabDEviceDetails");
	        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("CDCtabTitle"), "Custom Data title is not visible on Report Details Page");
	        LOGGER.info("Verified CDC tab is present on Device Details page");
        }else {
            LOGGER.info("Verified CDC tab is not present on Device Details page");

        }
    }

    @Test(priority = 12, groups = {"REGRESSION_PULSES","REGRESSION_CDC"}, description = "TestCase ID:",enabled = false)
    public final void verifyerrormessageforCreateCustomReportElements() throws Exception {
        login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
        AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("customDataReports");
        sleeper(2000);
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("addCustomDataReport");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("reportTitle"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.create.add.report")), "Report page title not matching");
        LOGGER.info("Redircetd to custom data report page successfully");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("reportSubTitle"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.create.select.script")), "Report page sub title not matching");

        //verify elements on report page
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("col1"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.script.name")), "Script name column name is not matching");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("col2"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.script.version.number")), "Version Number column name is not matching");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Col3"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.script.operation.name")), "Operation name colum name is not matching");

        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("cancelBtn"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.report.Cancel")), "Cancel button not present");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("nextBtn"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.report.Next")), "Next button not present");
//
        //select a script

        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("searchscript");
        WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("searchscript","battery script");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scriptSelection");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("nextBtn");
        //verify elements on step 2 report page
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("reportPageSubheading"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.select.audience")), "Step 2 Report page sub title not matching");
        String actualSubheadingText = WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("reportPageSubheading");
        String expectedSubheadingText = getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.select.audience");
        if (actualSubheadingText.equals(expectedSubheadingText)) {
            // First set of actions when condition is true
            LOGGER.info("Report page subheading matches with selection of Audience. Performing actions for Action type script.");
            softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("col3"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.group.name")), "Group name title not matching");
            softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("col4"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.group.type")), "Group type title not matching");


        }else {
            LOGGER.info("Report page subheading matches with selection of Parameters. Performing actions for Evaluation type script.");
            WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("inputParamLabel");
            WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("inputParamTextbox");

        }

        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("cancelBtn"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.report.Cancel")), "Cancel button not present");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("nextBtn"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.report.Next")), "Next button not present");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("backBtn"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.report.Back")), "Back button not present");
//        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("selectallgroup"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.select.all")), "Select All option is not present");
//        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("selectallgroup");
//        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Deselectallgroup"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.deselect.all")), "DeSelect All option is not present");
//        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Deselectallgroup");
//        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Grouptypesearch");
//        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Entraidgrouptype");
//        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("ClearFilter"), (getTextLanguage(LanguageCode,"daas_ui","global.clear.filters")), "Clear filter option is not present");
//        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("ClearFilter");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("group1");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("group2");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("toggleforselectedonly");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("toggleforselectedonly");
        WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("Recalculate");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Recalculate");
//        String Audiencecount = WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("AudienceCount");
//        LOGGER.info("Selected group audience count is "+ Audiencecount);
        LOGGER.info("Calculated the total audience count successfully");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("nextBtn");
//        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Createreport");
//        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Errormsgforreportname"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.error.report.name")), "Add report name error message is not matching ");

//        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("termandconditionmsg"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.error.terms.description")), "terms and condition msg is not matching ");
//        WEPPulsesCreationPage.enterTextForWEPPulsesCreationPage("Enterreportname","New Custom Report");
        WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("Enterreportname","New Custom Report"+generateRandomNumber());
//        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("termandconditiocheckbox");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Createreport");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("Toastmsgforreportcreation"), (getTextLanguage(LanguageCode,"daas_ui","analytics.custom.data.report.create.success.toast")), "report creation toast msg is not received ");
        softAssert.assertAll();
        LOGGER.info("Custom Data report creation flow verified successfully");

    }

}