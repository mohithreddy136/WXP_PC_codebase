package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.daasui.constants.WEPPulsesCreationPageVariables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.LogsVariables;
import com.daasui.constants.ScriptVariables;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.LogPage;
import com.daasui.pages.TableConfigurationPage;
import com.daasui.pages.WEXDashboardPage;
import com.daasui.pages.WEXLogPage;

public class WEXLogTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXLogTest.class);
	
	 /**
     * This method will verify the list page for LOGS
     *
     * @throws Exception
     */
	@Test(priority = 1, groups = {"PRODUCTION_ACCOUNTS"}, description = "TestCaseID:C42198174")
	public final void verifyLogListPage() throws Exception {
	    SoftAssert softAssert = new SoftAssert();
	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			if(wexlogPage.verifyElementsOfWEXLogPage("whatsNewPopupClosedButton"))
	    		wexlogPage.clickOnElementsOfWEXLogPage("whatsNewPopupClosedButton");
	    	wexlogPage.companyView(CommonVariables.SETTINGS);
	    	 sleeper(3000);
		    wexlogPage.verifyElementsOfWEXLogPage("logs");
		    wexlogPage.clickOnElementsOfWEXLogPage("logs");
			LOGGER.info("Redirected to logs list page");
			waitForPageLoaded();
			resetTableConfiguration(); 
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("listTable"), "Table on logs list page is not loaded successfully");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("dateAndTimeHeader"),"Default column is not present on logs list page");
			softAssert.assertAll();
			LOGGER.info("Logs list page is loaded successfully"); 
	    	} else{
	    	if(wexlogPage.verifyElementsOfWEXLogPage("whatsNewPopupClosedButton"))
	    		wexlogPage.clickOnElementsOfWEXLogPage("whatsNewPopupClosedButton");
	    	wexlogPage.companyView(CommonVariables.SETTINGS);
            sleeper(3000);
	    	wexlogPage.verifyElementsOfWEXLogPage("logs");
	    	wexlogPage.clickOnElementsOfWEXLogPage("logs");
	    	LOGGER.info("Redirected to logs list page");
	    	Assert.assertTrue((wexlogPage.getTextOfWEXLogPage("logsSiteHeader").split("\n")[0])
					.equals(wexlogPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.logs")),"Logs Header doesn't match");
			LOGGER.info("Logs Site Header is matched");
	    	waitForPageLoaded();
	    	resetTableConfiguration();
	    	softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("listTable"), "Table on logs list page is not loaded successfully");
	    	softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("dateAndTimeHeader"),"Default column is not present on logs list page");
	    	softAssert.assertAll();
	    	LOGGER.info("Logs list page is loaded successfully"); 

	    	}
 }
		
	 /**
     * This method will verify the list of columns under table configuration which are configurable
     *
     * @throws Exception
     */
    @Test(priority = 2, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:C43905140")
    public final void verifyLogScreenListOfColumnsInTableConfiguration() throws Exception {
    	SoftAssert softAssert = new SoftAssert();
	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
			if(wexlogPage.verifyElementsOfWEXLogPage("whatsNewPopupClosedButton")) {
	    		wexlogPage.clickOnElementsOfWEXLogPage("whatsNewPopupClosedButton");
			}
			wexlogPage.companyView(CommonVariables.SETTINGS);
	    	 sleeper(3000);
		    wexlogPage.verifyElementsOfWEXLogPage("logs");
		    waitForPageLoaded();
		    wexlogPage.clickOnElementsOfWEXLogPage("logs");
			LOGGER.info("Redirected to logs list page");
			if(wexlogPage.verifyElementsOfWEXLogPage("clearFilter")) {
				wexlogPage.scrollOnWEXLogPage("clearFilter");
				wexlogPage.clickByActionsClickWEXLogPage("clearFilter");
			}
			sleeper(3000);
        ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "logs.date_time"), getTextLanguage(LanguageCode, "daas_ui", "logs.type"), getTextLanguage(LanguageCode, "daas_ui", "logs.source"), getTextLanguage(LanguageCode, "daas_ui", "logs.description"), getTextLanguage(LanguageCode, "daas_ui", "logs.level"), getTextLanguage(LanguageCode, "daas_ui", "logs.identifier"),
                getTextLanguage(LanguageCode, "daas_ui", "logs.newValue"), getTextLanguage(LanguageCode, "daas_ui", "logs.oldValue"), getTextLanguage(LanguageCode, "daas_ui", "logs.userInfo")));
        CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
        TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
        companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
        if (tableConfigurationPage.verifyElementIsEnableOftableConfigurationPage("resettodefault")) {
        	wexlogPage.clickByActionsClickWEXLogPage("resettodefault");
        	wexlogPage.clickByActionsClickWEXLogPage("saveButton");
            companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
        }
        Assert.assertTrue(wexlogPage.verifySequenceOfColumnInPopup("logcolumnsLabelList", columnName));
        tableConfigurationPage.clickOnElementsOfTableConfigurationPage("discardButton");
        softAssert.assertAll();
        LOGGER.info("List of columns on table configuration verified successfully...");
    }

    /**
	 * This method will verify the export functionality on log list page
	 * 
	 * @throws Exception 
	 */
	@Test(priority = 3, groups = { "REGRESSION_ACCOUNTS"}, description = "TestCaseID:C43905141",enabled=false)
	public final void verifyExportButtonInLogListPage() throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
        if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            gotoLogTabWEX();
        }else {
            leftSideMenuVerification();
            wexlogPage.companyView(CommonVariables.SETTINGS);
            sleeper(3000);
            wexlogPage.verifyElementsOfWEXLogPage("logstabsnavigation");
            wexlogPage.clickByJavaScriptOnWEXLogPage("logstabsnavigation");
        }
		wexlogPage.waitForElementsOfWEXLogPage("selectAllCheckbox");
		wexlogPage.clickByJavaScriptOnWEXLogPage("selectAllCheckbox");
		softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("exportButton").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.export.btn_export")), "Export button is not available");
		wexlogPage.waitForElementsOfWEXLogPage("exportButton");
		wexlogPage.clickByJavaScriptOnWEXLogPage("exportButton");
		LOGGER.info("User is able to click Export button");
		sleeper(2000);
		softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("exportNotification").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.export.export_in_progress")), "Unable to export logs.");
		wexlogPage.waitUntilElementIsInvisibleOfWEXLogPage("exportNotification");
		wexlogPage.waitForElementsOfWEXLogPage("notificationButton");
		wexlogPage.clickOnElementsOfWEXLogPage("notificationButton");
		wexlogPage.waitForElementsOfWEXLogPage("hamburgerMenuOnNotification");
		wexlogPage.mousehoverOnLogsPage("hamburgerMenuOnNotification");
		wexlogPage.clickOnElementsOfWEXLogPage("hamburgerMenuOnNotification");
		softAssert.assertAll();
		LOGGER.info("User is able to download logs file successfully");
	}
	
	/**
    *This method will verify the log details page along with export functionality 
    *
    * @throws Exception
    */
   @Test(priority = 4, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:C43905142", enabled=false)
    public final void verifyExpandLogsOnLogsPage() throws Exception {
    	SoftAssert softAssert = new SoftAssert();
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
       if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
           gotoLogTabWEX();
       }else {
           leftSideMenuVerification();
           wexlogPage.companyView(CommonVariables.SETTINGS);
           sleeper(3000);
           wexlogPage.verifyElementsOfWEXLogPage("logstabsnavigation");
           wexlogPage.clickByJavaScriptOnWEXLogPage("logstabsnavigation");
       }
		wexlogPage.waitForElementsOfWEXLogPage("selectFirstCheckbox");
		wexlogPage.mousehoverOnLogsPage("selectFirstCheckbox");
		wexlogPage.clickOnElementsOfWEXLogPage("selectFirstCheckbox");
		wexlogPage.waitForElementsOfWEXLogPage("expandedLogDesc");
		LOGGER.info(wexlogPage.getTextOfWEXLogPage("expandedLogDesc"));
		Assert.assertTrue(wexlogPage.verifyElementIsEnableOfWEXLogPage("expandedLogDesc"),"Logs are not getting expanded");
		waitForPageLoaded();
		wexlogPage.waitForElementsOfWEXLogPage("logEntries");
		wexlogPage.waitForElementsOfWEXLogPage("exportButton");
		wexlogPage.clickByJavaScriptOnWEXLogPage("exportButton");
		LOGGER.info("User is able to click Export button");
		sleeper(2000);
		softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("exportNotification").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.export.export_in_progress")), "Unable to export logs.");
		wexlogPage.waitUntilElementIsInvisibleOfWEXLogPage("exportNotification");
		softAssert.assertAll();
		LOGGER.info("User is able to open logs details page and do export");
		
	}
   
   /**
    * Validate Search functionality  and Alphabetical sorting is working for Multi-select columns on Logs List page
    * @throws Exception
    */
   @Test(priority = 5, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:C43905328")
   public final void verifySortingOfMultiSelectColumnOnLogScreen() throws Exception {
	   SoftAssert softAssert = new SoftAssert();
       login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
       WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
       if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
           gotoLogTabWEX();
       }else {
           leftSideMenuVerification();
           wexlogPage.companyView(CommonVariables.SETTINGS);
           sleeper(3000);
           wexlogPage.verifyElementsOfWEXLogPage("logstabsnavigation");
           wexlogPage.clickByJavaScriptOnWEXLogPage("logstabsnavigation");
       }
       CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
       waitForPageLoaded();
       wexlogPage.waitForElementsOfWEXLogPage("tableOverlay");
       companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
       resetTableConfiguration();
       if (wexlogPage.verifyElementsOfWEXLogPage("clearFilter")) {
           wexlogPage.clickByActionsClickWEXLogPage("clearFilter");
       }
       waitForPageLoaded();
       
       //Type column
       wexlogPage.clickByActionsClickWEXLogPage("typeDropdown");
       softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("typeListSearch"), "Search field in type not found");
       wexlogPage.enterTextForWEXLogPage("typeListSearch", "Devices");
       sleeper(2000);
       wexlogPage.clickByActionsClickWEXLogPage("typeList");
       //wexlogPage.waitForElementsOfWEXLogPage("tableOverlay");
       wexlogPage.pressKey(Keys.ESCAPE);
       sleeper(2000);
       wexlogPage.waitForElementsOfWEXLogPage("logTableTypeColumn");
       sleeper(2000);
       softAssert.assertTrue(wexlogPage.verifyFilteredDataOnLogPage("logTableTypeColumn", "Devices"), "Filtered data is incorrect for type column");
       LOGGER.info("Type column shows filtered data correctly...");
       if (wexlogPage.verifyElementsOfWEXLogPage("clearFilter")) {
           wexlogPage.clickByActionsClickWEXLogPage("clearFilter");
       }
       waitForPageLoaded();
       wexlogPage.clickByActionsClickWEXLogPage("typeheader");
       sleeper(3000);
       softAssert.assertTrue(wexlogPage.verifyDropdownOptionOrderOnLogPage("logTableTypeColumn"),"Type field is not sorted");
       LOGGER.info("Type column options are sorted correctly...");
       wexlogPage.pressKey(Keys.ESCAPE);
       waitForPageLoaded();
      
       //Level column
       wexlogPage.scrollOnWEXLogPage("levelDropdown");
       wexlogPage.clickByActionsClickWEXLogPage("levelDropdown");
       softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("levelListSearch"), "Search field in level not found");
       wexlogPage.enterTextForWEXLogPage("levelListSearch", "Information");
       sleeper(2000);
       wexlogPage.clickByActionsClickWEXLogPage("levellist");
       wexlogPage.pressKey(Keys.ESCAPE);
       sleeper(2000);
       wexlogPage.waitForElementsOfWEXLogPage("logTableLevelColumn");
       sleeper(2000);
       softAssert.assertTrue(wexlogPage.verifyFilteredDataOnLogPage("logTableLevelColumn", "Information"), "Filtered data is incorrect for level column");
       LOGGER.info("Level column shows filtered data correctly...");
       if (wexlogPage.verifyElementsOfWEXLogPage("clearFilter")) {
           wexlogPage.clickByActionsClickWEXLogPage("clearFilter");
       }
       wexlogPage.waitForElementsOfWEXLogPage("tableOverlay");
       wexlogPage.clickByActionsClickWEXLogPage("levelheader");
       wexlogPage.waitForElementsOfWEXLogPage("tableOverlay");
       sleeper(3000);
       softAssert.assertTrue(wexlogPage.verifyDropdownOptionOrderOnLogPage("logTableLevelColumn"),"Level field is not sorted");
       LOGGER.info("Level column options are sorted correctly...");
              
       //subtype column
       wexlogPage.scrollOnWEXLogPage("subtypeDropdown");
       wexlogPage.clickByActionsClickWEXLogPage("subtypeDropdown");
       softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("subTypeListSearch"), "Search field in Subtype not found");
       wexlogPage.enterTextForWEXLogPage("subTypeListSearch", "Add");
       sleeper(2000);
       wexlogPage.clickByActionsClickWEXLogPage("subtypelist");
       wexlogPage.pressKey(Keys.ESCAPE);
       sleeper(2000);
       wexlogPage.waitForElementsOfWEXLogPage("logTableSubTypeColumn");
       sleeper(2000);
       softAssert.assertTrue(wexlogPage.verifyFilteredDataOnLogPage("logTableSubTypeColumn", "Add"), "Filtered data is incorrect for Subtype column");
       LOGGER.info("SubType column shows filtered data correctly...");
       if (wexlogPage.verifyElementsOfWEXLogPage("clearFilter")) {
           wexlogPage.clickByActionsClickWEXLogPage("clearFilter");
       }
       wexlogPage.waitForElementsOfWEXLogPage("tableOverlay");
       wexlogPage.clickByActionsClickWEXLogPage("subtypeheader");
       wexlogPage.waitForElementsOfWEXLogPage("tableOverlay");
       sleeper(3000);
       softAssert.assertTrue(wexlogPage.verifyDropdownOptionOrderOnLogPage("logTableSubTypeColumn"),"Subtype field is not sorted");
       LOGGER.info("Subtype column options are sorted correctly...");
       
       wexlogPage.pressKey(Keys.ESCAPE);
       softAssert.assertAll();
       LOGGER.info("Sorting of drop downs are done successfully...");
   }

    /**
    * This method will verify the LOGS functionality for Partner login
    *
    * @throws Exception
    */
    @Test(priority = 6, groups = {""}, description = "TestCaseID:C43905329")
  	public final void verifyPartnerCanSeeLogsPage() throws Exception {
  		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
  		SoftAssert softAssert = new SoftAssert();
  		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
  		dashboardPage.leftSideMenuVerification();
  		// Add the customer name in env spcific data property file based on the partner login you have taken,CommonVariables.LOGS);
  		dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"),CommonVariables.LOGS); 
  		waitForPageLoaded();
  		WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
        if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
            gotoLogTabWEX();
        }else {
            leftSideMenuVerification();
            wexlogPage.companyView(CommonVariables.SETTINGS);
            sleeper(3000);
            wexlogPage.verifyElementsOfWEXLogPage("logstabsnavigation");
            wexlogPage.clickByJavaScriptOnWEXLogPage("logstabsnavigation");
        }
  		wexlogPage.waitForPageLoaded();
  		wexlogPage.resetTableConfiguration();
  		softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("listTable"), "Table on logs list page is not loaded successfully");
  		softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("dateAndTimeHeader"),"Default column is not present on logs list page");
  		LOGGER.info("Logs list page is loaded successfully"); 
  		
  		wexlogPage.waitForElementsOfWEXLogPage("selectAllCheckbox");
  		wexlogPage.clickByJavaScriptOnWEXLogPage("selectAllCheckbox");
  		softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("exportButton").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.export.btn_export")), "Export button is not available");
  		wexlogPage.waitForElementsOfWEXLogPage("selectFirstCheckbox");
  		wexlogPage.mousehoverOnLogsPage("selectFirstCheckbox");
  		wexlogPage.clickOnElementsOfWEXLogPage("selectFirstCheckbox");
  		wexlogPage.waitForElementsOfWEXLogPage("expandedLogDesc");
  		softAssert.assertTrue(wexlogPage.verifyElementIsEnableOfWEXLogPage("expandedLogDesc"),"Logs are not getting expanded");
  		wexlogPage.clickByActionsClickWEXLogPage("expandedLogDesc");
  		wexlogPage.waitForPageLoaded();
  		wexlogPage.waitForElementsOfWEXLogPage("logEntries");
  		
  		softAssert.assertAll();
  		LOGGER.info("All tests for partner passed");
  	} 
    
    @Test(priority = 7, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:")
	public final void verifyApplyFilteronLogListPage() throws Exception {
	    SoftAssert softAssert = new SoftAssert();
	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
			if(wexlogPage.verifyElementsOfWEXLogPage("whatsNewPopupClosedButton")) {
	    		wexlogPage.clickOnElementsOfWEXLogPage("whatsNewPopupClosedButton");
			}
			leftSideMenuVerification();
	    	wexlogPage.companyView(CommonVariables.SETTINGS);
	    	 sleeper(3000);
		    wexlogPage.verifyElementsOfWEXLogPage("logs");
		    waitForPageLoaded();
		    wexlogPage.clickOnElementsOfWEXLogPage("logs");
			LOGGER.info("Redirected to logs list page");
			if(wexlogPage.verifyElementsOfWEXLogPage("clearFilter")) {
				wexlogPage.scrollOnWEXLogPage("clearFilter");
				wexlogPage.clickByActionsClickWEXLogPage("clearFilter");
			}
			sleeper(3000);
			wexlogPage.scrollOnWEXLogPage("typeDropdown");
			wexlogPage.clickByActionsClickWEXLogPage("typeDropdown");
			wexlogPage.enterTextForWEXLogPage("typeListSearch", LogsVariables.TYPE_ScriptAssignment);
			sleeper(1000);
			wexlogPage.clickByActionsClickWEXLogPage("typeList");
			
			wexlogPage.scrollOnWEXLogPage("subtypeDropdown");
			wexlogPage.clickByActionsClickWEXLogPage("subtypeDropdown");
			sleeper(1000);
			wexlogPage.enterTextForWEXLogPage("subTypeListSearch", LogsVariables.SUBTYPES_ARCHIVE);
			sleeper(2000);
			wexlogPage.clickByActionsClickWEXLogPage("subtypelist");
			wexlogPage.scrollOnWEXLogPage("levelDropdown");
			wexlogPage.clickByActionsClickWEXLogPage("levelDropdown");
			sleeper(1000);
			wexlogPage.enterTextForWEXLogPage("levelListSearch",LogsVariables.lEVEL_INFORMATION);
			sleeper(2000);
			wexlogPage.clickByActionsClickWEXLogPage("levellist");
			
			wexlogPage.scrollOnWEXLogPage("dateAndTimeHeader");
			wexlogPage.clickByJavaScriptOnWEXLogPage("firstRowDateAndTime");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("logEntries"), "logs list page is not loaded successfully");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("loginfo"), "logsinfo page is not loaded successfully");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("typeinfo").equals(LogsVariables.TYPE_ScriptAssignment),"Type filter is not matching");
			sleeper(1000);
			LOGGER.info("SubType " + wexlogPage.getTextOfWEXLogPage("subtypeinfo"));
			LOGGER.info("levelinfo " + wexlogPage.getTextOfWEXLogPage("levelinfo"));
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("subtypeinfo").equals(LogsVariables.SUBTYPES_ARCHIVE),"Subtype filter is not matching");
			wexlogPage.scrollOnWEXLogPage("levelDropdown");
			sleeper(2000);
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("levelinfo").equals(LogsVariables.lEVEL_INFORMATION),"level info is not matching");
		    softAssert.assertAll();
    }
    
    @Test(priority = 8, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:")
	public final void verifyExportOptionsonLogListPage() throws Exception {
	    SoftAssert softAssert = new SoftAssert();
	    login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
		WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
			if(wexlogPage.verifyElementsOfWEXLogPage("whatsNewPopupClosedButton"))
	    		wexlogPage.clickOnElementsOfWEXLogPage("whatsNewPopupClosedButton");
			leftSideMenuVerification();
	    	wexlogPage.companyView(CommonVariables.SETTINGS);
	    	 sleeper(3000);
		    wexlogPage.verifyElementsOfWEXLogPage("logs");
		    wexlogPage.clickOnElementsOfWEXLogPage("logs");
			LOGGER.info("Redirected to logs list page");
			if(wexlogPage.verifyElementsOfWEXLogPage("clearFilter")) {
				wexlogPage.scrollOnWEXLogPage("clearFilter");
				wexlogPage.clickByActionsClickWEXLogPage("clearFilter");
			}
			sleeper(3000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("logsHeaderDesc"), "Logs Desc is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("logsHeaderDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "logs.information")), "logs Desc text is not matching.");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("previewAndExport"), "Export and preview button is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("previewAndExport").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.previewExport")), "Preview and export text is not matching.");
			wexlogPage.clickByActionsClickWEXLogPage("selectAllCheckbox");
			sleeper(2000);
			wexlogPage.clickOnElementsOfWEXLogPage("previewAndExport");
			sleeper(6000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("backButton"), "back button is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("backButton").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.back")), "Back text is not matching.");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("previewAndExport"), "Export button is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("previewAndExport").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.export")), "Export button1 text is not matching.");
			wexlogPage.clickByActionsClickWEXLogPage("selectAllCheckbox");
			sleeper(2000);
			wexlogPage.clickOnElementsOfWEXLogPage("previewAndExport");
			wexlogPage.waitForElementsOfWEXLogPage("exportLogsHeader");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("exportLogsHeader"), "Exportlog header is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("exportLogsHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "logs.downloadArchivedLogs.title")), "Export logs text is not matching.");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("currentSelectionOfLogs"), "current selection logs is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("currentSelectionOfLogs").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "logs.export.current.selection")), "Current selection  text is not matching.");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("exportEntireList"), "Export entire list is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("exportEntireList").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "logs.export.entire.list")), "Entire list  text is not matching.");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("exportListDesc"), "Export list desc is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("exportListDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "logs.export.warning_limit").replaceAll("[>\\{}]", "").toString().replaceAll("limit","5000")), "logs limit text is not matching.");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("exportButtonPopup"), "Export button1 is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("exportButtonPopup").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.export")), "Export button text is not matching.");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("cancelButtonPopup"), "Export cancel is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("cancelButtonPopup").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.cancel")), "cancel text is not matching.");
			wexlogPage.clickOnElementsOfWEXLogPage("cancelButtonPopup");
		    sleeper(5000);
			wexlogPage.clickByActionsClickWEXLogPage("selectAllCheckbox");
			wexlogPage.clickOnElementsOfWEXLogPage("backButton");
			sleeper(6000);
			wexlogPage.clickByActionsClickWEXLogPage("selectAllCheckbox");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("logsHeaderDesc"), "Logs Desc is not visible");
			softAssert.assertAll();	
    }
    
    @Test(priority = 9, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:")
	public final void verifyExportOptionsExportentirelist() throws Exception {
	    SoftAssert softAssert = new SoftAssert();
	    login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
		WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
			if(wexlogPage.verifyElementsOfWEXLogPage("whatsNewPopupClosedButton"))
	    		wexlogPage.clickOnElementsOfWEXLogPage("whatsNewPopupClosedButton");
			leftSideMenuVerification();
	    	wexlogPage.companyView(CommonVariables.SETTINGS);
	    	 sleeper(3000);
		    wexlogPage.verifyElementsOfWEXLogPage("logs");
		    wexlogPage.clickOnElementsOfWEXLogPage("logs");
		    sleeper(3000);
			LOGGER.info("Redirected to logs list page");
			if(wexlogPage.verifyElementsOfWEXLogPage("clearFilter")) {
				wexlogPage.scrollOnWEXLogPage("clearFilter");
				wexlogPage.clickByActionsClickWEXLogPage("clearFilter");
			}
			sleeper(3000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("logsHeaderDesc"), "Logs Desc is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("logsHeaderDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "logs.information")), "logs Desc text is not matching.");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("previewAndExport"), "Export and preview button is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("previewAndExport").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.previewExport")), "Preview and export text is not matching.");
			wexlogPage.clickByActionsClickWEXLogPage("selectAllCheckbox");
			wexlogPage.clickOnElementsOfWEXLogPage("previewAndExport");
			sleeper(6000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("backButton"), "back button is not visible");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("previewAndExport"), "Export button is not visible");
			wexlogPage.clickOnElementsOfWEXLogPage("selectAllCheckbox");
			sleeper(2000);
			wexlogPage.clickOnElementsOfWEXLogPage("previewAndExport");
			sleeper(5000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("exportLogsHeader"), "Exportlog header is not visible");
			wexlogPage.clickByActionsClickWEXLogPage("entireListRadioButton");
			wexlogPage.clickOnElementsOfWEXLogPage("exportButtonPopup");
			softAssert.assertTrue(wexlogPage.ElementIsSelectedOfWEXLogPage("currentSelectionRadioButton"), "current list not selected");
			sleeper(3000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("exportNotification"), "Export notification is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("exportNotification").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.export.export_in_progress")), "Unable to export logs.");
			softAssert.assertAll();	
    }

    @Test(priority = 10, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:")
	public final void verifyselectedlogsCountlist() throws Exception {
	    SoftAssert softAssert = new SoftAssert();
	    login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
		WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
			if(wexlogPage.verifyElementsOfWEXLogPage("whatsNewPopupClosedButton")) {
	    		wexlogPage.clickOnElementsOfWEXLogPage("whatsNewPopupClosedButton");
			}
			leftSideMenuVerification();
	    	wexlogPage.companyView(CommonVariables.SETTINGS);
	    	 sleeper(3000);
		    wexlogPage.verifyElementsOfWEXLogPage("logs");
		    wexlogPage.clickOnElementsOfWEXLogPage("logs");
			LOGGER.info("Redirected to logs list page");
			if(wexlogPage.verifyElementsOfWEXLogPage("clearFilter")) {
				wexlogPage.scrollOnWEXLogPage("clearFilter");
				wexlogPage.clickByActionsClickWEXLogPage("clearFilter");
			}
			sleeper(3000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("logsHeaderDesc"), "Logs Desc is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("logsHeaderDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "logs.information")), "logs Desc text is not matching.");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("previewAndExport"), "Export and preview button is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("previewAndExport").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.previewExport")), "Preview and export text is not matching.");
			wexlogPage.clickByActionsClickWEXLogPage("selectAllCheckbox");
			sleeper(2000);
			String selectedItemsCount = wexlogPage.getTextOfWEXLogPage("itemsSelectedLabel").split(" ")[0];
			wexlogPage.clickOnElementsOfWEXLogPage("previewAndExport");
			sleeper(5000);
			wexlogPage.clickByActionsClickWEXLogPage("selectAllCheckbox");
			sleeper(5000);
			wexlogPage.clickByActionsClickWEXLogPage("previewAndExport");
			sleeper(3000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("exportLogsHeader"), "Exportlog header is not visible");
			wexlogPage.clickOnElementsOfWEXLogPage("exportButtonPopup");
			sleeper(3000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("exportNotification"), "Export notification is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("exportNotification").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.export.export_in_progress")), "Unable to export logs.");
			wexlogPage.waitForElementsOfWEXLogPage("notificationButton");
			wexlogPage.clickOnElementsOfWEXLogPage("notificationButton");
			sleeper(2000);
			wexlogPage.clickOnElementsOfWEXLogPage("notification");
			sleeper(5000);
			softAssert.assertEquals(wexlogPage.getTextOfWEXLogPage("notficationTitle").toLowerCase(), getTextLanguage(LanguageCode, "daas_ui", "assets.details.logs").toLowerCase(),"Notification title not matched");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("notificationContent").contains(selectedItemsCount),"Export message not matched with selected count for export");
			softAssert.assertAll();	
			
    }
    @Test(priority = 11, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:")
	public final void verifyAppliedFilterinExportFile() throws Exception {
	    SoftAssert softAssert = new SoftAssert();
	    login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
		WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
			if(wexlogPage.verifyElementsOfWEXLogPage("whatsNewPopupClosedButton")) {
	    		wexlogPage.clickOnElementsOfWEXLogPage("whatsNewPopupClosedButton");
			}
			leftSideMenuVerification();
	    	wexlogPage.companyView(CommonVariables.SETTINGS);
	    	 sleeper(3000);
		    wexlogPage.verifyElementsOfWEXLogPage("logs");
		    wexlogPage.clickOnElementsOfWEXLogPage("logs");
			LOGGER.info("Redirected to logs list page");
			if(wexlogPage.verifyElementsOfWEXLogPage("clearFilter")) {
				wexlogPage.scrollOnWEXLogPage("clearFilter");
				wexlogPage.clickByActionsClickWEXLogPage("clearFilter");
			}
			sleeper(3000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("logsHeaderDesc"), "Logs Desc is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("logsHeaderDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "logs.information")), "logs Desc text is not matching.");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("previewAndExport"), "Export and preview button is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("previewAndExport").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.previewExport")), "Preview and export text is not matching.");
			wexlogPage.scrollOnWEXLogPage("typeDropdown");
			wexlogPage.clickByActionsClickWEXLogPage("typeDropdown");
			wexlogPage.enterTextForWEXLogPage("typeListSearch", LogsVariables.TYPE_ScriptAssignment);
			sleeper(1000);
			wexlogPage.clickByActionsClickWEXLogPage("typeList");
			sleeper(2000);
			wexlogPage.clickByActionsClickWEXLogPage("selectAllCheckbox");
			sleeper(2000);
			wexlogPage.clickOnElementsOfWEXLogPage("previewAndExport");
			sleeper(5000);
			wexlogPage.clickByActionsClickWEXLogPage("selectAllCheckbox");
			sleeper(5000);
			wexlogPage.clickByActionsClickWEXLogPage("previewAndExport");
			sleeper(3000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("exportLogsHeader"), "Exportlog header is not visible");
			wexlogPage.clickOnElementsOfWEXLogPage("exportButtonPopup");
			sleeper(3000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("exportNotification"), "Export notification is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("exportNotification").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.export.export_in_progress")), "Unable to export logs.");
			wexlogPage.waitForElementsOfWEXLogPage("notificationButton");
			wexlogPage.clickOnElementsOfWEXLogPage("notificationButton");
			sleeper(2000);
			wexlogPage.clickOnElementsOfWEXLogPage("notification");
			sleeper(5000);
			softAssert.assertEquals(wexlogPage.getTextOfWEXLogPage("notficationTitle").toLowerCase(), getTextLanguage(LanguageCode, "daas_ui", "assets.details.logs").toLowerCase(),"Notification title not matched");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("notificationContent"), "Export notification content is not visible");
			wexlogPage.clickByActionsClickWEXLogPage("notificationButtonforlogs");
			sleeper(2000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("downloadFileButton"), "Download Fileis not visible");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("dismiss"), "Dismiss  is not visible");
			wexlogPage.clickByActionsClickWEXLogPage("downloadFileButton");
			softAssert.assertAll();		
    }
    
    @Test(priority = 12, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:")
	public final void verifyLogsListinclusionofsubentries() throws Exception {
	    SoftAssert softAssert = new SoftAssert();
	    login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
		WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
			if(wexlogPage.verifyElementsOfWEXLogPage("whatsNewPopupClosedButton"))
	    		wexlogPage.clickOnElementsOfWEXLogPage("whatsNewPopupClosedButton");
			leftSideMenuVerification();
	    	wexlogPage.companyView(CommonVariables.SETTINGS);
	    	 sleeper(3000);
		    wexlogPage.verifyElementsOfWEXLogPage("logs");
		    wexlogPage.clickOnElementsOfWEXLogPage("logs");
		    sleeper(3000);
			LOGGER.info("Redirected to logs list page");
			if(wexlogPage.verifyElementsOfWEXLogPage("clearFilter")) {
				wexlogPage.scrollOnWEXLogPage("clearFilter");
				wexlogPage.clickByActionsClickWEXLogPage("clearFilter");
			}
			sleeper(3000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("logsHeaderDesc"), "Logs Desc is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("logsHeaderDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "logs.information")), "logs Desc text is not matching.");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("previewAndExport"), "Export and preview button is not visible");
			softAssert.assertTrue(wexlogPage.getTextOfWEXLogPage("previewAndExport").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.previewExport")), "Preview and export text is not matching.");
			wexlogPage.clickByActionsClickWEXLogPage("selectAllCheckbox");
			int selectedItemsCount = Integer.parseInt(wexlogPage.getTextOfWEXLogPage("itemsSelectedLabel").split(" ")[0]);
			wexlogPage.clickOnElementsOfWEXLogPage("previewAndExport");
			sleeper(6000);
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("backButton"), "back button is not visible");
			softAssert.assertTrue(wexlogPage.verifyElementsOfWEXLogPage("previewAndExport"), "Export button is not visible");
			int populatedItemsCount = Integer.parseInt(wexlogPage.getTextOfWEXLogPage("pageCount").split(" ")[1]);
			softAssert.assertTrue(selectedItemsCount <= populatedItemsCount, "Items populated on preview page is less selected item");
			softAssert.assertAll();
			
    }
}
