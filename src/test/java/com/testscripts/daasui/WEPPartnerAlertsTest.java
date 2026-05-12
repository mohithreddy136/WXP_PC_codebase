package com.testscripts.daasui;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.TableConfigurationPage;
import com.daasui.pages.WEPPartnerAlertsPage;
import com.daasui.pages.WEXSignUpPage;



public class WEPPartnerAlertsTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEPPartnerAlertsPage.class);
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");
	public static String UserEmail = getEnvironmentSpecificData(System.getProperty("environment"), "WEP_USER_EMAIL_SEARCH");
	

	
	@DataProvider
	public Object[][] loginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "RESELLER_PLUS_ADMIN_EMAIL";
		data[0][1] = "RESELLER_PLUS_ADMIN_PASSWORD";
		data[1][0] = "RESELLER_PLUS_SERVICE_SPECIALIST_EMAIL";
		data[1][1] = "RESELLER_PLUS_SERVICE_SPECIALIST_PASSWORD";
		return data;
	}
	
	/**
	 * This test case is to verify Partner Alert management tab in Partner Admin and Service Specialist  Login
	 * @throws Exception
	 */
   @Test(priority = 1, groups = {"PRODUCTION_FRAMEWORKCORE","REGRESSION_FRAMEWORKCORE" }, description = "TestCaseID:C51620788", dataProvider = "loginData", enabled = false)
	public final void verifyPartnerAlertManagement(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login(username, password);
		WEPPartnerAlertsPage WEPPartnerAlertsPage = new WEPPartnerAlertsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		WEPPartnerAlertsPage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);
		WEPPartnerAlertsPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME"),CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ALERTS_MANAGEMENT); 
		
		waitForPageLoaded();
		sleeper(3000);
		if(!WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("predefinedSelectAlertCheckbox"))
		{	
			softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emptyAlertList"),"No Alert Message not present");
			softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("emptyAlertList"),getTextLanguage(LanguageCode, "daas_ui", "global.no_results"));	
			LOGGER.info("Predefined alerts are not present");
		}else {
		LOGGER.info("Redirected to the alert management page.");
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("alertManagementPage"),"Alerts management page is not match.");
		softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("alertManagementPage"),getTextLanguage(LanguageCode, "daas_ui", "global_alertsManagement"));
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("manageAlertNotification"),"Alerts management notification is not match.");
	    softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("manageAlertNotification"),getTextLanguage(LanguageCode, "daas_ui", "alert_management_alerts_notifications_header"));
	  
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("manageAlertNotificationtext"),"Alerts management text is not match.");
		softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("manageAlertNotificationtext"),getTextLanguage(LanguageCode, "daas_ui", "alert_management_alerts_notifications_sub_header"));
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("inProductNotificationLabel"),"Product notification label text is not match.");
		softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("inProductNotificationLabel"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.in.product.notification"));
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("inProductNotificationDropdown"),"Product notification dropdown text is not match");
			
		WEPPartnerAlertsPage.mousehoverOnWEPPartnerAlertsPage("inProductNotificationDropdown");
		if(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("clearDropdown"))
		{
			WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("clearDropdown");
		}
		WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("inProductNotificationDropdown");
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("itAdminSelect"),"IT admin is not visible on dropdown");
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("reportAdminSelect"),"Report admin is not visible on dropdown");
		WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("itAdminSelect");
		WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("inProductNotificationDropdown");
		LOGGER.info("Verified the In Product Notification.");
		
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emailNotificationaLabel"),"Email Notification label is not match.");
		softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("emailNotificationaLabel"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.email.notification"));
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emailNotificationDropdown"),"Email Notification dropdown is not verified.");
		WEPPartnerAlertsPage.mousehoverOnWEPPartnerAlertsPage("emailNotificationDropdown");
		if(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emailNotificationClearDropdown"))
		{
			WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("emailNotificationClearDropdown");
		}
		WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("emailNotificationDropdown");
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("itAdminEmailSelect"),"It admin is not selected in email notification.");
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("reportAdminEmailSelect"),"Report admin is not selected in email notification.");
		
		WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("itAdminEmailSelect");
		WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("emailNotificationDropdown");
		LOGGER.info("Verified the Email Notification.");
		WEPPartnerAlertsPage.ClearAndResetAlertManagementListTable();
		LOGGER.info("Verified clear and Reset functionality.");
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("titleColumn"),"Title  Column is not present on Alert mgmt Page");
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("statusColumn"),"Status Column is not present on Alert mgmt Page");		
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("severityColumn"),"Severity Column is not present on Alert mgmt Page");
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("lastEditedColumn"),"Last Edited Column is not present on Alert mgmt Page");
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("lastTriggeredColumn"),"Last Triggered Column is not present on Alert mgmt Page");
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("currentPageNumber"),	"Current Page Number is not present on Active Alert Page");
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("previousButton"),"Previous Button is not present on Active Alert Page");
		softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("nextButton"),"Next Button is not present on Active Alert Page");	
		}
        LOGGER.info("Alert management page columns are verified and all the available controls successfully");
        softAssert.assertAll();
	}
   
   /**
	 * This test case is to verify All alerts Enabled And Disabled Status for Partner Admin and Service Specialist  Login
	 * @throws Exception
	 */
   
   @Test(priority = 2, groups = {"REGRESSION_FRAMEWORKCORE","PRODUCTION_FRAMEWORKCORE" }, description = "TestCaseID:C51620788", dataProvider = "loginData",enabled = false)
   
	public final void verifyPartnerlogin_AllalertsEnabledAndDisabledStatus(String username, String password) throws Exception {
  		SoftAssert softAssert = new SoftAssert();
  		login(username, password);
  		WEPPartnerAlertsPage WEPPartnerAlertsPage = new WEPPartnerAlertsPage(PreDefinedActions.getDriver());
		WEPPartnerAlertsPage.companyView(CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);
		waitForPageLoaded();
		WEPPartnerAlertsPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME"),CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ALERTS_MANAGEMENT); 
		waitForPageLoaded();
		sleeper(3000);
		if (!WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("predefinedSelectAlertCheckbox")) {
			softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emptyAlertList"),
					"No Alert Message not present");
			softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("emptyAlertList"),
					getTextLanguage(LanguageCode, "daas_ui", "global.no_results"));
		LOGGER.info("Predefined alerts are not present");
		}else {
			int alertStatus = WEPPartnerAlertsPage.getSizeOfAllElementsVisibleOnWEPPartnerAlertsPage("allAlertStatus");
			WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("selectAllCheckbox");
			if(WEPPartnerAlertsPage.verifyElementIsClickableOfAlertManagementPage("enableAlert"))
			{
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("statusDropdown");
				String Option = WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("enableOption");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("statusDropdown");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("enableAlert");
				sleeper(1000);
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("enableAlertToastMessageText"),
						getTextLanguage(LanguageCode, "daas_ui", "bulk.enabled.alert.success.toast.message").replace("{selectedUser}", Integer.toString(alertStatus)), "Bulk enabled toast not matching");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("closeButton");
				sleeper(2000);
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyFilteredDataOnAlertManagementPage("allAlertStatus", Option), "All data should be Enabled for status column");
				
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("selectAllCheckbox");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("statusDropdown");
				Option = WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("disableOption");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("statusDropdown");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("disableAlert");
				sleeper(1000);
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("disableAlertToastMessageText"),
						getTextLanguage(LanguageCode, "daas_ui", "bulk.disabled.alert.success.toast.message").replace("{selectedUser}", Integer.toString(alertStatus)), "Bulk disabled toast not matching");
				sleeper(2000);
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyFilteredDataOnAlertManagementPage("allAlertStatus", Option), "All data should be Disabled for status column");
				
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("selectAllCheckbox");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("enableAlert");
			}else {
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("statusDropdown");
				String Option = WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("disableOption");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("statusDropdown");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("disableAlert");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("disableAlertToastMessageText"),
						getTextLanguage(LanguageCode, "daas_ui", "bulk.disabled.alert.success.toast.message").replace("{selectedUser}", Integer.toString(alertStatus)), "Bulk disabled toast not matching");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("closeButton");
				sleeper(2000);
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyFilteredDataOnAlertManagementPage("allAlertStatus", Option), "All data should be Disabled for status column");
			
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("selectAllCheckbox");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("statusDropdown");
				Option = WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("enableOption");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("statusDropdown");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("enableAlert");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("enableAlertToastMessageText"),
						getTextLanguage(LanguageCode, "daas_ui", "bulk.enabled.alert.success.toast.message").replace("{selectedUser}", Integer.toString(alertStatus)), "Bulk enabled toast not matching");
				sleeper(2000);
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyFilteredDataOnAlertManagementPage("allAlertStatus", Option), "All data should be Enabled for status column");
			}
			
			softAssert.assertAll();
			
		}
 }
   
   /**
	 * This test case is to verify Partner Alert management tab in Partner Admin and Service Specialist  Login
	 * @throws Exception
	 */
	@Test(priority = 3, groups = {"REGRESSION_FRAMEWORKCORE"}, description = "TestCase ID :",dataProvider = "loginData",enabled = false)
	public final void verifypartnerWEPPartnerAlertsPage(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login(username, password);
		WEPPartnerAlertsPage WEPPartnerAlertsPage = new WEPPartnerAlertsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		WEPPartnerAlertsPage.companyView(CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);
		if (!WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("predefinedSelectAlertCheckbox")) {
			softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emptyAlertList"),"No Alert Message not present");
			softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("emptyAlertList"),getTextLanguage(LanguageCode, "daas_ui", "global.no_results"));
		    LOGGER.info("Predefined alerts are not present");			
		}
		else {
			WEPPartnerAlertsPage.ClearAndResetAlertManagementListTable();
			LOGGER.info("Verified clear and Reset functionality.");
			softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("titleColumn"),"Title  Column is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("statusColumn"),"Status Column is not present on Alert mgmt Page");		
			softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("severityColumn"),"Severity Column is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("lastEditedColumn"),"Last Edited Column is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("lastTriggeredColumn"),"Last Triggered Column is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("currentPageNumber"),	"Current Page Number is not present on Active Alert Page");
			softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("previousButton"),"Previous Button is not present on Active Alert Page");
			softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("nextButton"),"Next Button is not present on Active Alert Page");	
			}
	        LOGGER.info("Alert management page columns are verified and all the available controls successfully");
	        softAssert.assertAll();
		}

	 /**
		 * This test case is to verify Partner Edit Alerts in Partner Admin and Service Specialist  Login
		 * @throws Exception
		 */
		@Test(priority = 4, groups = {"REGRESSION_FRAMEWORKCORE"}, description = "TestCase ID :",dataProvider = "loginData",enabled = false)
		public final void verifyPartnerEditAlertsPage(String username, String password) throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login(username, password);
			WEPPartnerAlertsPage WEPPartnerAlertsPage = new WEPPartnerAlertsPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			WEPPartnerAlertsPage.companyView(CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);
			if (!WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("predefinedSelectAlertCheckbox")) {
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emptyAlertList"),"No Alert Message not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("emptyAlertList"),getTextLanguage(LanguageCode, "daas_ui", "global.no_results"));
			    LOGGER.info("Predefined alerts are not present");			
			}
			else {
								
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("predefinedSelectAlertCheckbox");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("alertClick"),"Predefined alerts are not present");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("alertClick");
				sleeper(2000);
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("editAlert"),"Edit alert text is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("editAlert"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.edit.alert.heading"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("lastModified"),"Last Modified by Text is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("lastModified"),getTextLanguage(LanguageCode, "daas_ui", "alert.edit.last.modified.by"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("lastModifiedPartnerName"),"Last Modified partner name is not present");				
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("editAlertSubtitle"),"Edit alert sub text is not Present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("editAlertSubtitle"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.edit.screen.severity.description"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("editAlertSeverity"),"Severity label is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("editAlertSeverity"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.severity.column"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("currentSeverity"),"severity is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("editAlertTitle"),"Alert Title is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("editAlertTitle"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.title.column"));
				
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("editAlertTitleInput"),"Alert Title Input is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("editAlertDescription"),"Description field title is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("editAlertDescription"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.description.column"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("editAlertDescriptionInput"),"Description Input is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("alertWhenText"),"Alert when text is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("alertWhenText2"),"% of devices is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("alertWhenText2"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.edit.alert.devices.text"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("editAlertThreshold"),"Threshold is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("customerWarningMessage"),"Customer warning msg is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("customerWarningMessage"),getTextLanguage(LanguageCode, "daas_ui", "alerts.edit.customer.list.warning"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("selectCustomerEditAlert"),"Select customer alerts text  is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("selectCustomerEditAlert"),getTextLanguage(LanguageCode, "daas_ui", "alerts.select.customer.label"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("customerSelected"),"All customer text is not match");
				
				
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("inProductNotificationLabel"),"Product notification label text is not match.");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("inProductNotificationLabel"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.in.product.notification"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("inProductNotificationDropdown"),"Product notification dropdown text is not match");
					
				WEPPartnerAlertsPage.mousehoverOnWEPPartnerAlertsPage("inProductNotificationDropdown");
				if(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("clearDropdown"))
				{
					WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("clearDropdown");
				}
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("inProductNotificationDropdown");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("partnerAdminSelect"),"Partner admin is not visible on dropdown");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("partnerServiceSpecialistSelect"),"Partner Service Specialist is not visible on dropdown");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("partnerSalesSpecialistSelect"),"Partner Sales Specialist is not visible on dropdown");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("partnerAdminSelect");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("inProductNotificationDropdown");
				LOGGER.info("Verified the In Product Notification.");
				
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emailNotificationaLabel"),"Email Notification label is not match.");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("emailNotificationaLabel"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.email.notification"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emailNotificationDropdown"),"Email Notification dropdown is not verified.");
				WEPPartnerAlertsPage.mousehoverOnWEPPartnerAlertsPage("emailNotificationDropdown");
				if(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emailNotificationClearDropdown"))
				{
					WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("emailNotificationClearDropdown");
				}
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("emailNotificationDropdown");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("partnerAdminEmailSelect"),"Partner admin email is not visible on dropdown");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("partnerServiceSpecialistEmailSelect"),"Partner Service Specialist email is not visible on dropdown");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("partnerSalesSpecialistEmailSelect"),"Partner Sales Specialist email is not visible on dropdown");
				
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("partnerAdminEmailSelect");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("emailNotificationDropdown");
				LOGGER.info("Verified the Email Notification.");
				
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("editAlertCard"),"edit Alert Card is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("severityTag"),"Severity tag is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("alertCardTitle")," Alert Card Title is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("alertCardDescription")," Alert Card Description is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("percentageTag"),"Percentage tag is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("saveEditAlert"),"edit Alert Save is not present");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("saveEditAlert");
				WEPPartnerAlertsPage.waitForElementsOfWEPPartnerAlertsPageDynamic("editAlertToastMessageText", 2000);
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("editAlertToastMessageText"),"Toast messages is not present");
				}
				softAssert.assertAll();
		}

		 /**
		 * This test case is to verify Partner Edit Alerts in Partner Admin and Service Specialist  Login
		 * @throws Exception
		 */
		@Test(priority = 5, groups = {"REGRESSION_FRAMEWORKCORE"}, description = "TestCase ID :",dataProvider = "loginData",enabled = false)
		public final void verifyPartnerDuplicateAlertsPage(String username, String password) throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login(username, password);
		
			WEPPartnerAlertsPage WEPPartnerAlertsPage = new WEPPartnerAlertsPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			WEPPartnerAlertsPage.companyView(CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);
			if (!WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("predefinedSelectAlertCheckbox")) {
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emptyAlertList"),"No Alert Message not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("emptyAlertList"),getTextLanguage(LanguageCode, "daas_ui", "global.no_results"));
			    LOGGER.info("Predefined alerts are not present");			
			}
			else {
								
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("predefinedSelectAlertCheckbox");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("alertClick"),"Predefined alerts are not present");
				WEPPartnerAlertsPage.mousehoverOnWEPPartnerAlertsPage("mouseHoverAlertOption");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("mouseHoverAlertOption"),"Alert option is not present");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("mouseHoverAlertOption");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("mouseHoverDuplicate"),"Duplicate Alert option is not present");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("mouseHoverDuplicate");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("duplicateAlert"),"Duplicate alert text is not present");
				sleeper(2000);
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("duplicateAlert"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.duplicate.alert.heading"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("lastModified"),"Last Modified by Text is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("lastModified"),getTextLanguage(LanguageCode, "daas_ui", "alert.edit.last.modified.by"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("lastModifiedPartnerName"),"Last Modified partner name is not present");				
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("duplicateAlertSubtitle"),"Duplicate alert sub text is not Present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("duplicateAlertSubtitle"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.edit.screen.severity.description"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("duplicateAlertSeverity"),"Severity label is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("duplicateAlertSeverity"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.severity.column"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("currentSeverity"),"severity is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("duplicateAlertTitle"),"Alert Title is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("duplicateAlertTitle"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.title.column"));
				
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("duplicateAlertTitleInput"),"Alert Title Input is not present");
				WEPPartnerAlertsPage.enterTextwithoutclearForWEPPartnerAlertsPage("typeSearch", " - Duplicate");				
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("duplicateAlertDescription"),"Description field title is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("duplicateAlertDescription"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.description.column"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("duplicateAlertDescriptionInput"),"Description Input is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("alertWhenText"),"Alert when text is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("alertWhenText2"),"% of devices is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("alertWhenText2"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.edit.alert.devices.text"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("duplicateAlertThreshold"),"Threshold is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("customerWarningMessage"),"Customer warning msg is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("customerWarningMessage"),getTextLanguage(LanguageCode, "daas_ui", "alerts.edit.customer.list.warning"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("selectCustomerEditAlert"),"Select customer alerts text  is not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("selectCustomerEditAlert"),getTextLanguage(LanguageCode, "daas_ui", "alerts.select.customer.label"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("customerSelected"),"All customer text is not match");

															
				}
							
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("inProductNotificationLabel"),"Product notification label text is not match.");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("inProductNotificationLabel"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.in.product.notification"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("inProductNotificationDropdown"),"Product notification dropdown text is not match");
					
				WEPPartnerAlertsPage.mousehoverOnWEPPartnerAlertsPage("inProductNotificationDropdown");
				if(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("clearDropdown"))
				{
					WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("clearDropdown");
				}
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("inProductNotificationDropdown");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("partnerAdminSelect"),"Partner admin is not visible on dropdown");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("partnerServiceSpecialistSelect"),"Partner Service Specialist is not visible on dropdown");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("partnerSalesSpecialistSelect"),"Partner Sales Specialist is not visible on dropdown");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("partnerAdminSelect");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("inProductNotificationDropdown");
				LOGGER.info("Verified the In Product Notification.");
				
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emailNotificationaLabel"),"Email Notification label is not match.");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("emailNotificationaLabel"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.email.notification"));
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emailNotificationDropdown"),"Email Notification dropdown is not verified.");
				WEPPartnerAlertsPage.mousehoverOnWEPPartnerAlertsPage("emailNotificationDropdown");
				if(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("emailNotificationClearDropdown"))
				{
					WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("emailNotificationClearDropdown");
				}
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("emailNotificationDropdown");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("partnerAdminEmailSelect"),"Partner admin email is not visible on dropdown");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("partnerServiceSpecialistEmailSelect"),"Partner Service Specialist email is not visible on dropdown");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("partnerSalesSpecialistEmailSelect"),"Partner Sales Specialist email is not visible on dropdown");
				
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("partnerAdminEmailSelect");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("emailNotificationDropdown");
				LOGGER.info("Verified the Email Notification.");
				
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("duplicateAlertCard"),"Duplicate  Alert Card is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("severityTag"),"Severity tag is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("alertCardTitle")," Alert Card Title is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("alertCardDescription")," Alert Card Description is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("percentageTag"),"Percentage tag is not present");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("saveDuplicateAlert"),"Duplicate  Alert Save is not present");
				WEPPartnerAlertsPage.actionClickOnWEPPartnerAlertsPage("saveDuplicateAlert");
				//WEPPartnerAlertsPage.waitForElementsOfWEPPartnerAlertsPageDynamic("duplicateAlertToastMessageText", 2000);
				sleeper(1000);
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("duplicateAlertToastMessageText"),"Toast messages is not present");
				softAssert.assertAll();
				
				}
		
		
		/**
		 * This test case is to verify Partner Alert management tab in Sales Specialist  Login
		 * @throws Exception
		 */

	   @Test(priority = 6, groups = {"PRODUCTION_FRAMEWORKCORE","REGRESSION_FRAMEWORKCORE" }, description = "TestCaseID:C51620788", enabled = false)

		public final void verifyPartnerAlertsSalesSpecialist() throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login("RESELLER_PLUS_SALES_SPECIALIST_EMAIL", "RESELLER_PLUS_SALES_SPECIALIST_PASSWORD");
			WEPPartnerAlertsPage WEPPartnerAlertsPage = new WEPPartnerAlertsPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			leftSideMenuVerification();
			WEPPartnerAlertsPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME"),CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS); 
			waitForPageLoaded();
			sleeper(3000);
			if (!WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("ColumnData")) {
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("noAlertMessage"),
						"No Alert Message not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("noAlertMessage"),getTextLanguage(LanguageCode, "daas_ui", "no_active_alerts.title"),"No Alert Message text is  not match");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("noAlertSubText"),
						"No Alert Message subtext not present");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("noAlertSubText"),getTextLanguage(LanguageCode, "daas_ui", "no_active_alerts.subtitle")," sub Alert Message text is  not match");
			} else {
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("breadcrumbsAlertHeader"),
						"BreadcrumbsAlertHeader is not present on Active Alert Page");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("breadcrumbsAlertHeader"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.appbar.header.title"),"Alert breadcrumb text is  not match");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("helpButton"),
						"Help Button is not present on Active Alert Page");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("notificationButton"),
						"Notification Button is not present on Active Alert Page");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("profileButton"),
						"Profile Button is not present on Active Alert Page");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("searchInput"),
						"Search Input is not present on Active Alert Page");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("columnSettingIcon"),
						"Column Setting Icon is not present on Active Alert Page");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("severityColumn"),
						"Severity Column is not present on Active Alert Page");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("alertColumn"),
						"Alert Column is not present on Active Alert Page");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("alertColumn"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.grid.column.title"),"Alert column text is  not match");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("createdColumn"),
						"created Column is not present on Active Alert Page");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("updatedColumn"),
						"updated Column is not present on Active Alert Page");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("deviceImpactedColumn"),
						"Device Impacted Column is not present on Active Alert Page");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("deviceImpactedColumn"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.grid.column.devicesImpacted"),"Device impacted column text is  not match");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("fleetColumn"),
						"Fleet Column is not present on Active Alert Page");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("fleetColumn"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.grid.column.fleetPercentage"),"fleet column text is  not match");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("currentPageNumber"),
						"Current Page Number is not present on Active Alert Page");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("previousButton"),
						"Previous Button is not present on Active Alert Page");
				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("nextButton"),
						"Next Button is not present on Active Alert Page");
				String alertname = WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("ColumnData");
				WEPPartnerAlertsPage.clickOnElementsOfWEPPartnerAlertsPage("ColumnData");
				waitForPageLoaded();

				softAssert.assertTrue(WEPPartnerAlertsPage.verifyElementsOfWEPPartnerAlertsPage("breadcrumbsAlertTitle"),
						"bread crumb title is not present on Active Alert Page");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("alertImpactedDevicesText"),getTextLanguage(LanguageCode, "daas_ui", "applications.crashesandfreezes.legends.2"),"Impacted devices text is  not match");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("alertFleetText"),getTextLanguage(LanguageCode, "daas_ui", "wex.fleet.mgmt.ra.percentage_of_fleet"),"Fleet text is  not match");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("remediationGuide"),getTextLanguage(LanguageCode, "daas_ui", "wex.widget.recommended.actions.remediation.guide"),"remediation text is  not match");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("viewMoreButton"),getTextLanguage(LanguageCode, "daas_ui", "wex.widget.recommended.actions.view.more"),"view more text is  not match");
				WEPPartnerAlertsPage.clickOnElementsOfWEPPartnerAlertsPage("selectAllCheckbox");
				WEPPartnerAlertsPage.clickOnElementsOfWEPPartnerAlertsPage("exportButton");
				softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("exportButton"),getTextLanguage(LanguageCode, "daas_ui", "assets.export.btn_export"),"Export button text is  not match");
				sleeper(2000);
				if (alertname.contains(CommonVariables.Bios_Alert)) {
					softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("toastNotificationExport"),
							getTextLanguage(LanguageCode, "daas_ui", "assets.export.export_in_progress"),
							"Export notification text is  not match");
				} else {
					softAssert.assertEquals(WEPPartnerAlertsPage.getTextOfWEPPartnerAlertsPage("toastNotificationExport"),
							getTextLanguage(LanguageCode, "daas_ui", "device.details.export.device.data.success"),
							"Export notification text is  not match");
				}
				softAssert.assertAll();
			}
		}		
				
		
}

   




