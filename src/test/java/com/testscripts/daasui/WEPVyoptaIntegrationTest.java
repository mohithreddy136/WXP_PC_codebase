package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.VyoptaVariables;
import com.daasui.pages.WEPVyoptaTelephonePage;
import com.daasui.pages.WEPVyoptaVideoEndpointsPage;
import com.daasui.pages.WEXDashboardPage;
import com.daasui.pages.WEXCustomerHomePage;
import com.daasui.pages.WEPCollaborationDashboardPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WEPVyoptaIntegrationTest extends CommonTest{
    private static Logger LOGGER = LogManager.getLogger(WEPVyoptaIntegrationTest.class);

    /**
     * This is to verify the redirection to video endpoints page
     */
    @Test(priority = 1,groups={"REGRESSION_VYOPTAINTEGRATION"},description = "TestCaseID:C52964411",enabled = false)
    public final void verifyWorkforceExperienceVideoEndpointPageRedirection() throws Exception{
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPVyoptaVideoEndpointsPage wepVyoptaVideoEndpointPage = new WEPVyoptaVideoEndpointsPage(PreDefinedActions.getDriver()).getInstance();
        //Navigate to video endpoints
        if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            waitForPageLoaded();
            wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointsTab");
            wepVyoptaVideoEndpointPage.actionClickOfVideoEndpointsPage("videoEndpointsTab");
            waitForPageLoaded();
            Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("devicesPageTitleFleetManagement"),"Fleet Management Title is not as expected");
            Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("devicesPageTitleDevices"),"Devices Title is not as expected");
        }
        else{
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
            waitForPageLoaded();
            Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),"Video Endpoints Title is not as expected");
        }
        LOGGER.info("Sucessfully inside video Endpoints  page.");
        LOGGER.info("Workforce Experience Vyopta Video Endpoints Page Redirection Test Passed");
    }

    /**
     * This is to verify the to video endpoints page
     */
    @Test(priority = 2,groups={"REGRESSION_VYOPTAINTEGRATION"}, description = "TestCaseID:C52964411",enabled = false)
    public final void verifyWorkforceExperienceVideoEndpointPage() throws Exception{
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPVyoptaVideoEndpointsPage wepVyoptaVideoEndpointPage = new WEPVyoptaVideoEndpointsPage(PreDefinedActions.getDriver()).getInstance();
        //Navigate to video endpoints
        if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            waitForPageLoaded();
            wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointsTab");
            wepVyoptaVideoEndpointPage.actionClickOfVideoEndpointsPage("videoEndpointsTab");
            waitForPageLoaded();
            Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("devicesPageTitleFleetManagement"),"Fleet Management Title is not as expected");
            Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("devicesPageTitleDevices"),"Devices Title is not as expected");
        }
        else{
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
            waitForPageLoaded();
            Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),"Video Endpoints Title is not as expected");
        }
        List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.endpointname"),
                getTextLanguage(LanguageCode, "daas_ui", "web.application.config.table.column.category"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_details_manufacturer"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_detail.accessories_column.model"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_details_serialNumber"),
                getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.ip_address"),
                getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.mac_address"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.endpointtags"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.vyoptamonitored"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.totalcalls"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.totalmins"),
                getTextLanguage(LanguageCode, "daas_ui", "poly.raas.devices.date_added")));

        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle");
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageList");
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTableRows");
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTableCount");
        resetTableConfiguration();
        Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyTableColumns(expectedColumnList,"videoEndpointsTableColumns"), "Table Columns are not as expected");
        LOGGER.info("Workforce Experience Vyopta Video Endpoints Page Redirection Test Passed");
    }

    /**
     * This is to verify pagination and sorting of the page
     */
    @Test(priority = 3,groups={"REGRESSION_VYOPTAINTEGRATION"}, description = "TestCaseID:C52964411",enabled = false)
    public final void verifyVideoEndpointPaginationandSort() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        sleeper(2000);
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPVyoptaVideoEndpointsPage wepVyoptaVideoEndpointPage = new WEPVyoptaVideoEndpointsPage(PreDefinedActions.getDriver()).getInstance();
        //Navigate to video endpoints
        if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            waitForPageLoaded();
            wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointsTab");
            wepVyoptaVideoEndpointPage.actionClickOfVideoEndpointsPage("videoEndpointsTab");
            waitForPageLoaded();
            Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("devicesPageTitleFleetManagement"), "Fleet Management Title is not as expected");
            Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("devicesPageTitleDevices"), "Devices Title is not as expected");

        } else {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
            waitForPageLoaded();
            Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"), "Video Endpoints Title is not as expected");

        }
        wepVyoptaVideoEndpointPage.clearFiltersOfVideoEndpointsPage("clearFilters");
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("endpointNameCol");
        wepVyoptaVideoEndpointPage.clickByJavaScriptOnVideoEndpointsPage("endpointNameCol");
        waitForPageLoaded();
        List<String> endPointName = wepVyoptaVideoEndpointPage.getAvailableEndpoints();
        boolean isDesc = wepVyoptaVideoEndpointPage.getSortingOrderType("endpointNameCol");
        Assert.assertTrue(wepVyoptaVideoEndpointPage.IsColumnSorted(endPointName, isDesc), "The serial number column is not in sorted format");
        verifyPaginationOnListPage();

    }
    /**
     * This is to verify telephone page redirection
     */
    @Test(priority = 4,groups={"REGRESSION_VYOPTAINTEGRATION"}, description = "TestCaseID:C52963371",enabled = false)
    public final void verifyWorkforceExperienceTelephonePageRedirection() throws Exception{
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPVyoptaTelephonePage wepVyoptaTelephonePage = new WEPVyoptaTelephonePage(PreDefinedActions.getDriver()).getInstance();

        //Navigate to telephones page
        if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            waitForPageLoaded();
            wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesTab");
            wepVyoptaTelephonePage.actionClickOfTelephonePage("telephonesTab");
            waitForPageLoaded();
            Assert.assertTrue(wepVyoptaTelephonePage.verifyElementsOfTelephonePage("devicesPageTitleFleetManagement"),"Fleet Management Title is not as expected");
            Assert.assertTrue(wepVyoptaTelephonePage.verifyElementsOfTelephonePage("devicesPageTitleDevices"),"Devices Title is not as expected");
        }
        else{
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_TELEPHONES);
            waitForPageLoaded();
            Assert.assertTrue(wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesTitle"),"Telephone Title is not as expected");

        }
        LOGGER.info("Sucessfully inside telephone list page.");
        LOGGER.info("Workforce Experience Vyopta Telephone Page Redirection Test Passed");
    }

    /**
     * This is to verify the telephone list page
     */
    @Test(priority = 5,groups={"REGRESSION_VYOPTAINTEGRATION"}, description = "TestCaseID:C52963371",enabled = false)
    public final void verifyWorkforceExperienceTelephonePage() throws Exception{
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPVyoptaTelephonePage wepVyoptaTelephonePage = new WEPVyoptaTelephonePage(PreDefinedActions.getDriver()).getInstance();
        //Navigate to video endpoints
        if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            waitForPageLoaded();
            wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesTab");
            wepVyoptaTelephonePage.actionClickOfTelephonePage("telephonesTab");
            waitForPageLoaded();
            Assert.assertTrue(wepVyoptaTelephonePage.verifyElementsOfTelephonePage("devicesPageTitleFleetManagement"),"Fleet Management Title is not as expected");
            Assert.assertTrue(wepVyoptaTelephonePage.verifyElementsOfTelephonePage("devicesPageTitleDevices"),"Devices Title is not as expected");
        }
        else{
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_TELEPHONES);
            waitForPageLoaded();
            Assert.assertTrue(wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesTitle"),"Telephone Title is not as expected");

        }
        List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(
                getTextLanguage(LanguageCode, "daas_ui", "partners.list.name"),
                getTextLanguage(LanguageCode, "daas_ui", "web.application.config.table.column.category"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_details_manufacturer"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_detail.accessories_column.model"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_details_serialNumber"),
                getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.ip_address"),
                getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.mac_address"),
                getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.user_email"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.endpointtags"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.vyoptamonitored"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.totalcalls"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.totalmins"),
                getTextLanguage(LanguageCode, "daas_ui", "poly.raas.devices.date_added")));
        wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesPageTitle");
        wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesPageList");
        wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesPageTableRows");
        wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesPageTableCount");
        resetTableConfiguration();
        Assert.assertTrue(wepVyoptaTelephonePage.verifyTableColumns(expectedColumnList,"telephonesPageTableColumns"), "Table Columns are not as expected");
        LOGGER.info("Workforce Experience Vyopta Video Endpoints Page Redirection Test Passed");
    }

    /**
     * This is to verify pagination and sorting of the telephone page
     */
    @Test(priority = 6,groups={"REGRESSION_VYOPTAINTEGRATION"}, description = "TestCaseID:C52963371",enabled = false)
    public final void verifyTelephonesPaginationandSort() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPVyoptaTelephonePage telephonePage = new WEPVyoptaTelephonePage(PreDefinedActions.getDriver()).getInstance();
        //Navigate to telephone page
        if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            waitForPageLoaded();
            telephonePage.verifyElementsOfTelephonePage("telephonesTab");
            telephonePage.actionClickOfTelephonePage("telephonesTab");
            waitForPageLoaded();
            Assert.assertTrue(telephonePage.verifyElementsOfTelephonePage("devicesPageTitleFleetManagement"), "Fleet Management Title is not as expected");
            Assert.assertTrue(telephonePage.verifyElementsOfTelephonePage("devicesPageTitleDevices"), "Devices Title is not as expected");

        } else {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_TELEPHONES);
            waitForPageLoaded();
            Assert.assertTrue(telephonePage.verifyElementsOfTelephonePage("telephonesTitle"),"Telephone Title is not as expected");

        }
        telephonePage.clearFiltersOfTelephonePage("clearFilters");
        telephonePage.verifyElementsOfTelephonePage("nameCol");
        telephonePage.clickByJavaScriptOnTelephonePage("nameCol");
        waitForPageLoaded();
        List<String> endPointName = telephonePage.getAvailableEndpoints();
        boolean isDesc = telephonePage.getSortingOrderType("nameCol");
        Assert.assertTrue(telephonePage.IsColumnSorted(endPointName, isDesc), "The serial number column is not in sorted format");
        verifyPaginationOnListPage();

    }

    /**
     * This is to verify the video endpoint widget on home page
     */
    @Test(priority = 7,groups={"REGRESSION_VYOPTAINTEGRATION"}, description = "TestCaseID:C52965779",enabled = false)
    public final void verifyWorkforceExperienceVideoEndpointWidget() throws Exception{
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEXCustomerHomePage customerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
        WEPVyoptaVideoEndpointsPage wepVyoptaVideoEndpointPage = new WEPVyoptaVideoEndpointsPage(PreDefinedActions.getDriver()).getInstance();
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("fleetInventoryTitle");
        wepVyoptaVideoEndpointPage.scrollTillViewOnVideoEndpointsPage("fleetInventoryTitle");
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointsPill");
        wepVyoptaVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("videoEndpointsPill");
        List<WebElement> fleetInventoriesListElements = wepVyoptaVideoEndpointPage.getElementsOfVideoEndpointsPage("videoEndpointsWidget");
        for (WebElement element : fleetInventoriesListElements) {
            customerHomePage.mouseHoverclickOfCustomerHomePage(element);
        }
        Assert.assertTrue(wepVyoptaVideoEndpointPage.isCountMatching("centerWidgetValue", 0), "Total Count should be 0 after deselecting all legend keys");
        LOGGER.info("Verified widget count after deselecting all options is having count of 0");
        HashMap<String, Integer> differentManuDeviceCount = wepVyoptaVideoEndpointPage.getManufacturerBasedDeviceCount(fleetInventoriesListElements, getTextLanguage(LanguageCode, "daas_ui", "vyoptawidget.heading.title"));
        Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyManufacturerCountFromVideoEndpointList(fleetInventoriesListElements, differentManuDeviceCount,
                wepVyoptaVideoEndpointPage), "Verified failed of redirection count for first manufacturer in device list page");
        wepVyoptaVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("leftPaneHomePageButton");
        waitForPageLoaded();
        wepVyoptaVideoEndpointPage.scrollTillViewOnVideoEndpointsPage("fleetInventoryTitle");
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointsPill");
        wepVyoptaVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("videoEndpointsPill");
        fleetInventoriesListElements.clear();
        fleetInventoriesListElements = wepVyoptaVideoEndpointPage.getElementsOfVideoEndpointsPage("videoEndpointsWidget");
        for (WebElement element : fleetInventoriesListElements) {
            customerHomePage.mouseHoverclickOfCustomerHomePage(element);
        }
        wepVyoptaVideoEndpointPage.mouseHoverclickOfVyoptaPage(fleetInventoriesListElements.get(1));
        wepVyoptaVideoEndpointPage.mouseHoverclickOfVyoptaPage(fleetInventoriesListElements.get(2));
        int countAfterDeselect = wepVyoptaVideoEndpointPage.getManufacturerCount(wepVyoptaVideoEndpointPage.getTextOfWEPVideoEndpointsPage("centerWidgetValue"));
        int countToVerify = differentManuDeviceCount.get(fleetInventoriesListElements.get(1).getText()) + differentManuDeviceCount.get(fleetInventoriesListElements.get(2).getText());
        Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyTheCountWithinRange(countAfterDeselect, countToVerify), "Widget count after deselecting first two legend is not matching as per the expectation.");

    }

    /**
     * This is to verify the telephone endpoint widget on home page
     */
    @Test(priority = 8,groups={"REGRESSION_VYOPTAINTEGRATION"}, description = "TestCaseID:C53064380",enabled = false)
    public final void verifyWorkforceExperienceTelephoneWidget() throws Exception{
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEXCustomerHomePage customerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
        WEPVyoptaTelephonePage telephonePage = new WEPVyoptaTelephonePage(PreDefinedActions.getDriver()).getInstance();
        telephonePage.verifyElementsOfTelephonePage("fleetInventoryTitle");
        telephonePage.scrollTillViewOnTelephonePage("fleetInventoryTitle");
        telephonePage.verifyElementsOfTelephonePage("telephonePill");
        telephonePage.clickOnElementsOfTelephonePage("telephonePill");
        List<WebElement> fleetInventoriesListElements = telephonePage.getElementsOfTelephonePage("telephonesWidget");
        for (WebElement element : fleetInventoriesListElements) {
            customerHomePage.mouseHoverclickOfCustomerHomePage(element);
        }
        Assert.assertTrue(telephonePage.isCountMatching("centerWidgetValue", 0), "Total Count should be 0 after deselecting all legend keys");
        LOGGER.info("Verified widget count after deselecting all options is having count of 0");
        HashMap<String, Integer> differentManuDeviceCount = telephonePage.getManufacturerBasedDeviceCount(fleetInventoriesListElements,getTextLanguage(LanguageCode, "daas_ui", "vyoptawidget.telephones.header.title"));
        Assert.assertTrue(telephonePage.verifyManufacturerCountFromTelephoneList(fleetInventoriesListElements, differentManuDeviceCount,
                telephonePage), "Verified failed of redirection count for first manufacturer in device list page");
        telephonePage.clickOnElementsOfTelephonePage("leftPaneHomePageButton");
        waitForPageLoaded();
        telephonePage.scrollTillViewOnTelephonePage("fleetInventoryTitle");
        telephonePage.verifyElementsOfTelephonePage("telephonePill");
        telephonePage.clickOnElementsOfTelephonePage("telephonePill");
        fleetInventoriesListElements.clear();
        fleetInventoriesListElements = telephonePage.getElementsOfTelephonePage("telephonesWidget");
        for (WebElement element : fleetInventoriesListElements) {
            customerHomePage.mouseHoverclickOfCustomerHomePage(element);
        }
        telephonePage.mouseHoverAndClickOfTelephonePage(fleetInventoriesListElements.get(1));
        telephonePage.mouseHoverAndClickOfTelephonePage(fleetInventoriesListElements.get(2));
        int countAfterDeselect = telephonePage.getManufacturerCount(telephonePage.getTextOfWEPTelephonesPage("centerWidgetValue"));
        int countToVerify = differentManuDeviceCount.get(fleetInventoriesListElements.get(1).getText()) + differentManuDeviceCount.get(fleetInventoriesListElements.get(2).getText());
        Assert.assertTrue(telephonePage.verifyTheCountWithinRange(countAfterDeselect, countToVerify), "Widget count after deselecting first two legend is not matching as per the expectation.");

    }


    /**
     * This is to verify the meeting quality widget on the home page
     */
    @Test(priority = 9,groups={"REGRESSION_VYOPTAINTEGRATION"}, description = "TestCaseID:C52965207",enabled = false)
    public final void verifyWorkforceExperienceMeetingQualityWidget() throws Exception{
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPVyoptaVideoEndpointsPage wepVyoptaVideoEndpointPage = new WEPVyoptaVideoEndpointsPage(PreDefinedActions.getDriver()).getInstance();
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("meetingQualityTitle");
        wepVyoptaVideoEndpointPage.scrollTillViewOnVideoEndpointsPage("meetingQualityTitle");
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("totalMeetingsLabel");
        List<WebElement> meetingsList = wepVyoptaVideoEndpointPage.getElementsOfVideoEndpointsPage("meetingsList");
        HashMap<String, Integer> differentMeetingCount = wepVyoptaVideoEndpointPage.getApplicationWithMeetingCount(meetingsList);
        Assert.assertTrue(wepVyoptaVideoEndpointPage.isCountMatching("totalMeetingsCount", differentMeetingCount.get("Total")), "Total Count should be {} after summing all the values");
        LOGGER.info("Verified widget count after deselecting all options is having count of 0");

    }

    /**
     * This is to verify the collaboration dashboard navigation
     */
    @Test(priority = 10,groups={"REGRESSION_VYOPTAINTEGRATION"}, description = "TestCaseID:C55689330",enabled = false)
    public final void verifyWorkforceExperienceNavigationToCollaborationDashboard() throws Exception{
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPCollaborationDashboardPage wepCollaborationDashboard = new WEPCollaborationDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            LOGGER.info("Vyopta collaboration dashboard not available in this navigation.");
        }
        else{
            dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
            waitForPageLoaded();
            wepCollaborationDashboard.verifyElementsOfCollaborationDashboardPage("collaborationDashboard");
            wepCollaborationDashboard.mouseHoverOnCollaborationDashboardPage("collaborationDashboard");
            String hoverText = wepCollaborationDashboard.getTextOfCollaborationDashboardPage("collaborationDashboardHover");
            Assert.assertTrue(hoverText.equals(getTextLanguage(LanguageCode, "daas_ui", "collaboration.experience.title")+"\n"+
                    getTextLanguage(LanguageCode, "daas_ui", "group.created_by")+" HP"),"The text for Collaboration Experience hover is not matching");
            wepCollaborationDashboard.actionClickOfCollaborationDashboardPage("collaborationDashboard");
            waitForPageLoaded();
            wepCollaborationDashboard.verifyElementsOfCollaborationDashboardPage("collaborationPageBreadcrumb");
            wepCollaborationDashboard.verifyElementsOfCollaborationDashboardPage("collaborationPageTitle");
            LOGGER.info("Verified Collaboration Dashboard Expereience naviagtion");
        }

        LOGGER.info("Verified Collaboration Dashboard Expereience naviagtion");

    }

    /**
     * This is to verify the collaboration dashboard 12 widgets
     */
    @Test(priority = 11,groups={"REGRESSION_VYOPTAINTEGRATION"}, description = "TestCaseID:C53938425",enabled = false)
    public final void verifyCollaborationDashboardWidgets() throws Exception{
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPCollaborationDashboardPage wepCollaborationDashboard = new WEPCollaborationDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            LOGGER.info("Vyopta collaboration dashboard not available in this navigation.");
        }
        else{
            dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
            waitForPageLoaded();
            wepCollaborationDashboard.verifyElementsOfCollaborationDashboardPage("collaborationDashboard");
            wepCollaborationDashboard.mouseHoverOnCollaborationDashboardPage("collaborationDashboard");
            String hoverText = wepCollaborationDashboard.getTextOfCollaborationDashboardPage("collaborationDashboardHover");
            Assert.assertTrue(hoverText.equals(getTextLanguage(LanguageCode, "daas_ui", "collaboration.experience.title")+"\n"+
                    getTextLanguage(LanguageCode, "daas_ui", "group.created_by")+" HP"),"The text for Collaboration Experience hover is not matching");
            wepCollaborationDashboard.actionClickOfCollaborationDashboardPage("collaborationDashboard");
            waitForPageLoaded();
            wepCollaborationDashboard.verifyElementsOfCollaborationDashboardPage("collaborationPageBreadcrumb");
            wepCollaborationDashboard.verifyElementsOfCollaborationDashboardPage("collaborationPageTitle");
            Assert.assertTrue(wepCollaborationDashboard.getTextOfCollaborationDashboardPage("meetingsSummaryTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "vyopta.meetingssummary.widget.header.title")),"Meetings summary title is not present/matching.");
            Assert.assertTrue(wepCollaborationDashboard.getTextOfCollaborationDashboardPage("meetingsPlatformOverTimeTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "vyopta.meetingsByPlatform.widget.header.title")),"Platform over time title is not present/matching.");
            Assert.assertTrue(wepCollaborationDashboard.getTextOfCollaborationDashboardPage("meetingQualityMSTeamsTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "vyopta.meetingsQuality.msTeam.widget.header.title")),"Meeting Quality for MSFT teams title is not present/matching.");
            Assert.assertTrue(wepCollaborationDashboard.getTextOfCollaborationDashboardPage("meetingQualityZoomTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "vyopta.meetingsQuality.zoom.widget.header.title")),"Meeting Quality for zoom title is not present/matching.");
            Assert.assertTrue(wepCollaborationDashboard.getTextOfCollaborationDashboardPage("meetingQualityCiscoTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "vyopta.meetingsQuality.ciscoWebex.widget.header.title")),"Meeting Quality for cisco title is not present/matching.");
            Assert.assertTrue(wepCollaborationDashboard.getTextOfCollaborationDashboardPage("remoteCallQualityTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "vyopta.experience.remote.participant.widget.header.title")),"Remote call Quality for title is not present/matching.");
            Assert.assertTrue(wepCollaborationDashboard.getTextOfCollaborationDashboardPage("inOfficeCorporateTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "vyopta.experience.in.office.corporate.network.widget.header.title")),"Inoffice/Corporate network title is not present/matching.");
            Assert.assertTrue(wepCollaborationDashboard.getTextOfCollaborationDashboardPage("badQualityNetworkTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "vyopta.experience.bad.quality.network.widget.header.title")),"Bad quality of experience by network title is not present/matching.");
            Assert.assertTrue(wepCollaborationDashboard.getTextOfCollaborationDashboardPage("hardwareEndpointTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "vyopta.experience.hardware.endpoint.widget.header.title")),"Hardware endpoint by network title is not present/matching.");
            Assert.assertTrue(wepCollaborationDashboard.getTextOfCollaborationDashboardPage("softwareEndpointTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "vyopta.experience.software.endpoint.widget.header.title")),"Software endpoint  by network title is not present/matching.");
            Assert.assertTrue(wepCollaborationDashboard.getTextOfCollaborationDashboardPage("commonEndUserTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "vyopta.experience.common.endUser.widget.header.title")),"Common end users title is not present/matching.");
            Assert.assertTrue(wepCollaborationDashboard.getTextOfCollaborationDashboardPage("badQualityDeviceTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "vyopta.experience.bad.quality.device.widget.header.title")),"Bad quality by device title is not present/matching.");

            LOGGER.info("Verified Collaboration Dashboard Widget titles are present");
        }
    }
    /**
     * This is to verify the to video endpoints page
     */
    @Test(priority = 12)
    public final void verifyWorkforceExperienceVideoEndpointPageldk() throws Exception{
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        leftSideMenuVerification();
        SoftAssert softAssert = new SoftAssert();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPVyoptaVideoEndpointsPage wepVyoptaVideoEndpointPage = new WEPVyoptaVideoEndpointsPage(PreDefinedActions.getDriver()).getInstance();
        //Navigate to video endpoints
        if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            waitForPageLoaded();
            wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointsTab");
            wepVyoptaVideoEndpointPage.actionClickOfVideoEndpointsPage("videoEndpointsTab");
            waitForPageLoaded();
            softAssert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("devicesPageTitleFleetManagement"),"Fleet Management Title is not as expected");
            softAssert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("devicesPageTitleDevices"),"Devices Title is not as expected");
        }
        else{
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
            waitForPageLoaded();
            softAssert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),"Video Endpoints Title is not as expected");
        }
        List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.endpointname"),
                getTextLanguage(LanguageCode, "daas_ui", "web.application.config.table.column.category"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_details_manufacturer"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_detail.accessories_column.model"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_details_serialNumber"),
                getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.ip_address"),
                getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.mac_address"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.endpointtags"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.vyoptamonitored"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.totalcalls"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.totalmins"),
                getTextLanguage(LanguageCode, "daas_ui", "poly.raas.devices.date_added")));

        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle");
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageList");
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTableRows");
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTableCount");
        resetTableConfiguration();
        Assert.assertTrue(wepVyoptaVideoEndpointPage.verifyTableColumns(expectedColumnList,"videoEndpointsTableColumns"), "Table Columns are not as expected");
        LOGGER.info("Workforce Experience Vyopta Video Endpoints Page Redirection Test Passed");
        wepVyoptaVideoEndpointPage.clearFiltersOfVideoEndpointsPage("clearFilters");
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("endpointNameCol");
        wepVyoptaVideoEndpointPage.clickByJavaScriptOnVideoEndpointsPage("endpointNameCol");
        wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoVyoptaEndpointsLink");
        wepVyoptaVideoEndpointPage.clickByJavaScriptOnVideoEndpointsPage("videoVyoptaEndpointsLink");        
        switchToDifferentTab();
        waitForPageLoaded();
        sleeper(2000);    
        softAssert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("vyoptaEmailInput"),"vyopta Email Input is not present");
        softAssert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("vyoptaEmailNextbtn"),"vyoptaEmail Next button  is not present");
        switchBackToPreviousTab();      
        softAssert.assertAll();
    }
    
    /**
     * This is to verify the telephone list page
     */
    @Test(priority = 13,groups={"PRODUCTION_LDK","INITECH_SOLUTIONS"})
    public final void verifyWorkforceExperienceTelephonePageldk() throws Exception{
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPVyoptaTelephonePage wepVyoptaTelephonePage = new WEPVyoptaTelephonePage(PreDefinedActions.getDriver()).getInstance();
        //Navigate to video endpoints
        if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            waitForPageLoaded();
            wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesTab");
            wepVyoptaTelephonePage.actionClickOfTelephonePage("telephonesTab");
            waitForPageLoaded();
            Assert.assertTrue(wepVyoptaTelephonePage.verifyElementsOfTelephonePage("devicesPageTitleFleetManagement"),"Fleet Management Title is not as expected");
            Assert.assertTrue(wepVyoptaTelephonePage.verifyElementsOfTelephonePage("devicesPageTitleDevices"),"Devices Title is not as expected");
        }
        else{
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_TELEPHONES);
            waitForPageLoaded();
            Assert.assertTrue(wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesTitle"),"Telephone Title is not as expected");

        }
        List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(
                getTextLanguage(LanguageCode, "daas_ui", "partners.list.name"),
                getTextLanguage(LanguageCode, "daas_ui", "web.application.config.table.column.category"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_details_manufacturer"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_detail.accessories_column.model"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_details_serialNumber"),
                getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.ip_address"),
                getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.mac_address"),
                getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.user_email"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.endpointtags"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.vyoptamonitored"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.totalcalls"),
                getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.totalmins"),
                getTextLanguage(LanguageCode, "daas_ui", "poly.raas.devices.date_added")));
        wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesPageTitle");
        wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesPageList");
        wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesPageTableRows");
        wepVyoptaTelephonePage.verifyElementsOfTelephonePage("telephonesPageTableCount");
        resetTableConfiguration();
        Assert.assertTrue(wepVyoptaTelephonePage.verifyTableColumns(expectedColumnList,"telephonesPageTableColumns"), "Table Columns are not as expected");
        LOGGER.info("Workforce Experience Vyopta Video Endpoints Page Redirection Test Passed");
        
    }
    @Test(priority = 12,groups={"PRODUCTION_LDK","INITECH_SOLUTIONS"})
    public final void verifyWXPVideoEndpointPageldk() throws Exception{
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        leftSideMenuVerification();
        SoftAssert softAssert = new SoftAssert();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPVyoptaVideoEndpointsPage wepVyoptaVideoEndpointPage = new WEPVyoptaVideoEndpointsPage(PreDefinedActions.getDriver()).getInstance();
        //Navigate to video endpoints
        if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            waitForPageLoaded();
            wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointsTab");
            wepVyoptaVideoEndpointPage.actionClickOfVideoEndpointsPage("videoEndpointsTab");
            waitForPageLoaded();
        }
        else{
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
            waitForPageLoaded();
            softAssert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),"Video Endpoints Title is not as expected");
            softAssert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndPionttitle"),"videoEndPiont title  is not as expected");
            softAssert.assertTrue(wepVyoptaVideoEndpointPage.verifyElementsOfVideoEndpointsPage("tablelist"),"Table details is not there");
        }          
 }
}
