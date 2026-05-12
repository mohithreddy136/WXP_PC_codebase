package com.testscripts.daasui;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.DeviceVariables;
import com.daasui.constants.WPTVariable;
import com.daasui.pages.LogPage;
import com.daasui.pages.WEPDeviceDetailsPage;
import com.daasui.pages.WEPWPTPage;
import com.daasui.pages.WEXDashboardPage;

public class WEPWPTTest extends CommonTest  {

	 private static Logger LOGGER = LogManager.getLogger(WEPWPTTest.class);
	 
		/**
		 * This method will verify DataCollection toggle on preference tab on setting page
		 * 
		 * @throws Exception
		 */
	 @Test(priority = 1, groups = { "REGRESSION_WPT" })	
	    public final void verifyDataCollectionToggle() throws Exception {	 
	        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
	        WEPWPTPage wepDataCollectionWPTPage = new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();		 	 
			SoftAssert softAssert = new SoftAssert();
			leftSideMenuVerification();
			wepDataCollectionWPTPage.companyView(CommonVariables.SETTINGS);
			waitForPageLoaded();
			LOGGER.info("Redirected to setting tab");

			wepDataCollectionWPTPage.verifyElementsOfWEPWPTPage("Preferencestab");
			wepDataCollectionWPTPage.clickOnElementsOfWPTSettingPage("Preferencestab");
		 
			wepDataCollectionWPTPage.verifyElementsOfWEPWPTPage("datacollectionsection");
			wepDataCollectionWPTPage.clickOnElementsOfWPTSettingPage("datacollectionsection");
			
			softAssert.assertTrue(wepDataCollectionWPTPage.verifyElementsOfWEPWPTPage("dataCollectionHeader"),"datacollection header is incorrect");       
	        LOGGER.info("datacollection header is correct");	
	        softAssert.assertTrue(wepDataCollectionWPTPage.verifyElementsOfWEPWPTPage("datacollectionsubheading"),"datacollection sub header is incorrect");       
	        LOGGER.info("datacollection sub header is correct");
			softAssert.assertTrue(
					wepDataCollectionWPTPage.matchTextOfWEXWPTSettingPage("antivirusFirewallLabel",
							getTextLanguage(LanguageCode, "daas_ui", "settings.preference.datacollection.antivirusFirewallLabels")),
					"datacollection antivirusFirewallLabel row validation is incorrect");
			softAssert.assertTrue(
					wepDataCollectionWPTPage.matchTextOfWEXWPTSettingPage("BlueScreenCrashDumpsrowm",
							getTextLanguage(LanguageCode, "daas_ui", "settings.preference.datacollection.blueScreenCrashDumps")),
					"datacollection BlueScreenCrashDumpsr row validation is incorrect");
			softAssert.assertTrue(
					wepDataCollectionWPTPage.matchTextOfWEXWPTSettingPage("devicelogsrow",
							getTextLanguage(LanguageCode, "daas_ui", "settings.preference.datacollection.devicelogs")),
					"datacollection devicelogs row validation is incorrect");
			softAssert.assertTrue(
					wepDataCollectionWPTPage.matchTextOfWEXWPTSettingPage("companylocationrow",
							getTextLanguage(LanguageCode, "daas_ui", "settings.preference.datacollection.companylocation")),
					"datacollection companylocation row validation is incorrect");
			softAssert.assertTrue(
					wepDataCollectionWPTPage.matchTextOfWEXWPTSettingPage("networkidentifire",
							getTextLanguage(LanguageCode, "daas_ui", "settings.preference.datacollection.networkidentifire")),
					"datacollection networkidentifire validation is incorrect");
			softAssert.assertTrue(
					wepDataCollectionWPTPage.matchTextOfWEXWPTSettingPage("softwareinventory",
							getTextLanguage(LanguageCode, "daas_ui", "settings.preference.datacollection.softwareinventory")),
					"datacollection softwareinventory validation is incorrect");				
			softAssert.assertTrue(
					wepDataCollectionWPTPage.matchTextOfWEXWPTSettingPage("webapplicationUsage",
							getTextLanguage(LanguageCode, "daas_ui", "settings.preference.datacollection.webapplicationusage")),
					"datacollection webapplicationusage validation is incorrect");
			LOGGER.info("validation is done for Data Collection section on preferences tab on setting page");
			          softAssert.assertAll();
     }
	 
	
	 
	 /**
	 * Verify Wolf Protect and trace column name on device list page  
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2, groups = {"REGRESSION_WPT"})
	    public void wolfProtectandTraceColumnName() throws Exception {
	        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	        SoftAssert softAssert = new SoftAssert();
	        WEPWPTPage wepWptDeviceListPage = new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();
	        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	        if(wepWptDeviceListPage.verifyElementsOfWEPWPTPage("leftSideMenuCollapseBtn")) {
	    		LOGGER.info("Left side menu is not collapsed as expected");
	    	}else if(wepWptDeviceListPage.verifyElementsOfWEPWPTPage("leftSideMenuExpandBtn")) {
	    		wepWptDeviceListPage.actionClickOfWEPWPTPage("leftSideMenuExpandBtn");
	        	LOGGER.info("Left side menu is expanded successfully");
	    	}
	        if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
	        }
	        else{
	            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
	        }
	        softAssert.assertTrue(wepWptDeviceListPage.verifyElementsOfWEPWPTPage("WolfProtectandTraceColumnName"),"Wolf Protect and trace column is not present");       
	        LOGGER.info("Wolf Protect and trace column is present on the device list page");
	        softAssert.assertAll();
	 }
	 
	 /**
	 * This method will verify WPT Approver Count setting on preference tab on setting page
	 * @throws Exception
	 */
	@Test(priority = 3, groups = { "REGRESSION_WPT" })
		
	 public final void WEPWptApproverCountSettingPage() throws Exception {		 
	 login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	    WEPWPTPage WEPWptApproverCountSetting = new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();
	    SoftAssert softAssert = new SoftAssert();
	    if(WEPWptApproverCountSetting.verifyElementsOfWEPWPTPage("leftSideMenuCollapseBtn")) {
  		LOGGER.info("Left side menu is not collapsed as expected");
  	    }else if(WEPWptApproverCountSetting.verifyElementsOfWEPWPTPage("leftSideMenuExpandBtn")) {
  		WEPWptApproverCountSetting.actionClickOfWEPWPTPage("leftSideMenuExpandBtn");
      	LOGGER.info("Left side menu is expanded successfully");
  	    }
	    WEPWptApproverCountSetting.companyView(CommonVariables.SETTINGS);
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");
		WEPWptApproverCountSetting.verifyElementsOfWEPWPTPage("Preferencestab");
		WEPWptApproverCountSetting.clickOnElementsOfWPTSettingPage("Preferencestab");
		
		WEPWptApproverCountSetting.verifyElementsOfWEPWPTPage("DeviceHPWolfProtectandTracePrefSettings");
		WEPWptApproverCountSetting.clickOnElementsOfWPTSettingPage("DeviceHPWolfProtectandTracePrefSettings");
		softAssert.assertTrue(WEPWptApproverCountSetting.matchTextOfWPTSettingPage("DeviceHPWolfProtectandTracesettingsheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.WPT.header")),
				"WPT header is incorrect");
		
		softAssert.assertTrue(WEPWptApproverCountSetting.matchTextOfWPTSettingPage("DeviceHPWolfProtectandTracesettingsSubheader",
								getTextLanguage(LanguageCode, "daas_ui","Setting.preference.WPT.subheader")),
						"WPT sub header is incorrect");
		
		if (WEPWptApproverCountSetting.verifyElementsOfWEPWPTPage("wptThresholdStateEnabled")) {
		    LOGGER.info("WPT Threshold state is enabled");
		    WEPWptApproverCountSetting.editWPTThresholdAndSave();
		    String toastMessage = WEPWptApproverCountSetting.getTextOfWEPWPTPage("wptToastNotification");
		    if (toastMessage.contains(getTextLanguage(LanguageCode, "daas_ui", "setting.preference.WPT.successtoastMessage"))) { 
		        LOGGER.info("Toaster message is displayed as expected: " + toastMessage);
		    }
		    WEPWptApproverCountSetting.editenableWPTThresholdAndSave();
		    toastMessage = WEPWptApproverCountSetting.getTextOfWEPWPTPage("wptToastNotification");
		    if (toastMessage.contains(getTextLanguage(LanguageCode, "daas_ui", "setting.preference.WPT.successtoastMessage"))) {
		        LOGGER.info("Toaster message is displayed as expected: " + toastMessage);		       
		    } else if (toastMessage.contains(getTextLanguage(LanguageCode, "daas_ui", "setting.preference.WPT.inprogresstoastMessage"))) {
		        LOGGER.info("Toaster message is displayed as expected: " + toastMessage);
		        WEPWptApproverCountSetting.editenableWPTThresholdAndSave();
		        
		        Assert.assertEquals(WEPWptApproverCountSetting.getTextOfWEPWPTPage("wptToastNotification"),
		                getTextLanguage(LanguageCode, "daas_ui", "setting.preference.WPT.successtoastMessage"),
		                "Toaster message is not as expected after enabling WPT Threshold");
		        // Additional in-progress logic if needed
		    } else {
		        LOGGER.error("Toaster message is not displayed as expected: " + toastMessage);
		        softAssert.fail("Toaster message is not displayed as expected");
		    }
		} else if (WEPWptApproverCountSetting.verifyElementsOfWEPWPTPage("wptThresholdStateDisabled")) {
		    LOGGER.info("WPT Threshold state is disabled");
		    WEPWptApproverCountSetting.editenableWPTThresholdAndSave();
		    String toastMessageDisabled = WEPWptApproverCountSetting.getTextOfWEPWPTPage("wptToastNotification");

		    if (toastMessageDisabled.contains(getTextLanguage(LanguageCode, "daas_ui", "setting.preference.WPT.inprogresstoastMessage"))) {
		        LOGGER.info("Toaster message is displayed as expected: " + toastMessageDisabled);
		        WEPWptApproverCountSetting.editenableWPTThresholdAndSave();
		        Assert.assertEquals(WEPWptApproverCountSetting.getTextOfWEPWPTPage("wptToastNotification"),
		                getTextLanguage(LanguageCode, "daas_ui", "setting.preference.WPT.successtoastMessage"),
		                "Toaster message is not as expected after enabling WPT Threshold");
		    } else if (toastMessageDisabled.contains(getTextLanguage(LanguageCode, "daas_ui", "setting.preference.WPT.successtoastMessage"))) {
		        LOGGER.info("Toaster message is displayed as expected: " + toastMessageDisabled);
		   
		    } else {
		        LOGGER.error("Toaster message is not displayed as expected: " + toastMessageDisabled);
		        softAssert.fail("Toaster message is not displayed as expected");
		    }
		}	 
	 }

	 /**
	 * This method will verify WPT Device details tabs
	 * @throws Exception
	 */
	@Test(priority = 4, groups = {"REGRESSION_WPT" }) 
	    public final void verifyWEPDeviceDetailsTabs() throws Exception {
	        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	        WEPWPTPage wepWPTPage = new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();
	        if(wepWPTPage.verifyElementsOfWEPWPTPage("leftSideMenuCollapseBtn")) {
	    		LOGGER.info("Left side menu is not collapsed as expected");
	    	}else if(wepWPTPage.verifyElementsOfWEPWPTPage("leftSideMenuExpandBtn")) {
	    		wepWPTPage.actionClickOfWEPWPTPage("leftSideMenuExpandBtn");
	        	LOGGER.info("Left side menu is expanded successfully");
	    	}
	        if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) 
	        {
	        	wepWPTPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
	        }else{
	        	wepWPTPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
	        }	        
	        String serialNumber = getEnvironmentSpecificData(System.getProperty("environment"), "WPT_TEST_DEVICE_SERIAL_NUM");        
	        wepWPTPage.openDeviceDetails(serialNumber);
	        wepWPTPage.verifyDeviceDetailsTabs();
	        LOGGER.info("Device details tabs verified successfully");
	        
	 }     
	 
	 /**
	 * This method will verify WPT user column in customer login
	 * @throws Exception
	 */
	@Test(priority = 5, groups = {"REGRESSION_WPT" })
		public final void verifyWPTuserColumnInCustomerLogin() throws Exception {
			WEPWPTPage wepWPTPage = new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			leftSideMenuVerification();
			if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
				wepWPTPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
		    	wepWPTPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");				
			Assert.assertTrue(wepWPTPage.clickOnElementsOfWPTSettingPage("usersTab"), "Unable to click on the User tab");
			LOGGER.info("Clicked on the User tab");							
			Assert.assertTrue(wepWPTPage.verifyElementsOfWEPWPTPage("WolfProtectandTraceColumnNameonuserpage"),"Wolf Protect and trace column is not present");       		        
			LOGGER.info("WPT Column is present on the user list page");
			softAssert.assertAll();
     }
	 	 
	 /**
	 * This method will verify WPT Device details hardware tabs
	 * @throws Exception
	 */
	@Test(priority = 6, groups = {"REGRESSION_WPT" })
	    public final void verifyWEPDeviceDetailsHardwareTabs() throws Exception {
	        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	        WEPWPTPage wepWPTPage = new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();
	        SoftAssert softAssert = new SoftAssert();
	        if(wepWPTPage.verifyElementsOfWEPWPTPage("leftSideMenuCollapseBtn")) {
	    		LOGGER.info("Left side menu is not collapsed as expected");
	    	}else if(wepWPTPage.verifyElementsOfWEPWPTPage("leftSideMenuExpandBtn")) {
	    		wepWPTPage.actionClickOfWEPWPTPage("leftSideMenuExpandBtn");
	        	LOGGER.info("Left side menu is expanded successfully");
	    	}
	        if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) 
	        {
	        	wepWPTPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
	        }else{
	        	wepWPTPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
	        }	     
	        String serialNumber = getEnvironmentSpecificData(System.getProperty("environment"), "WPT_TEST_DEVICE_SERIAL_NUM");	        
	        wepWPTPage.openDeviceDetails(serialNumber);
	        wepWPTPage.verifyDeviceDetailshardwareTabs();
	        LOGGER.info("Device header tabs verified successfully");
	        softAssert.assertAll();
     }
				
	 /**
	  * This method will verify WPT Account overview page
	 * @throws Exception
	 */
	@Test(priority = 7, groups = {"REGRESSION_WPT" })		 
		public final void verifyWPTAccountOverviewPagw() throws Exception {		
		 	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			WEPWPTPage wepWPTAccountOverview = new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			leftSideMenuVerification();
			if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
				wepWPTAccountOverview.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    } else{
		    	wepWPTAccountOverview.companyView(CommonVariables.ACCOUNT);
		    }
	    	waitForPageLoaded();		    
			LOGGER.info("Redirected Account management-Overview tab page");				
			wepWPTAccountOverview.actionClickOfWEPWPTPage("wptAccountOverviewPage");
			LOGGER.info("Clicked on the overview tab");
			wepWPTAccountOverview.verifyAccountOverviewTabs();
	        LOGGER.info("Overview tab verified successfully");
	        softAssert.assertAll();
      }
	 
	 /**
	 * This method will verify WPT License on account management page
	 * @throws Exception
	 */
	@Test(priority = 8, groups = {"REGRESSION_WPT" })
		 public final void verifyWPTLicense() throws Exception {
		    WEPWPTPage WEXWPTLicense = new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();
		    SoftAssert softAssert = new SoftAssert();
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			leftSideMenuVerification();
			if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
				WEXWPTLicense.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    } else{
		    	WEXWPTLicense.companyView(CommonVariables.ACCOUNT);
		    }
	    	waitForPageLoaded();		    	
			LOGGER.info("Redirected Account management-Overview tab page");				
			WEXWPTLicense.actionClickOfWEPWPTPage("licenseTab");
			LOGGER.info("Clicked on the license tab");
			ArrayList<String> expectedLicenses = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode,"daas_ui", "account_management.subscriptions.wpt.header"),getTextLanguage(LanguageCode,"daas_ui","account_management.subscriptions.addon.wpt.header")));		    
	    	softAssert.assertTrue(WEXWPTLicense.verifyWPTLicense(expectedLicenses),"WPT License is not displayed as expected");
	    	softAssert.assertAll();
	  }
				 
	 /**
	 * This method will verify WPT AddOn on account management page
	 * @throws Exception
	 */
	@Test(priority = 9, groups = {"REGRESSION_WPT" })
		 public final void verifyWPTAddOn() throws Exception {
			WEPWPTPage WEXWPTAddon = new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			leftSideMenuVerification();
			if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
				WEXWPTAddon.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    } else{
	    	    WEXWPTAddon.companyView(CommonVariables.ACCOUNT);
		    }
	    	waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");			
			WEXWPTAddon.verifyElementsOfWEPWPTPage("addOnTab");
			WEXWPTAddon.actionClickOfWEPWPTPage("addOnTab");
			LOGGER.info("Clicked on the AddOns tab");		
			softAssert.assertTrue(WEXWPTAddon.verifyElementsOfWEPWPTPage("wepWPTaddOnlable"),"HP Wolf Protect and Trace with Wolf Connect AddOn is not available as expected");				
			softAssert.assertAll();
	 }
	@Test(priority = 10, groups = { "REGRESSION_WPT" })
	public final void verifyHelpIcon() throws Exception {	
		WEXHelpAndSupportTest wexhelpandsupporttest=new WEXHelpAndSupportTest();
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();
		wexhelpandsupporttest.verifyNeedAssistanceWithWEX();
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("mySupportCase"),"mySupportCase is not avaialable");		
		LOGGER.info("Help Icon Verified successfuly");

	}

	@Test(priority = 11, groups = { "REGRESSION_WPT" })
	public final void verifyCompanyPin() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();			
		leftSideMenuVerification();			
		WEPWPTPage.clickByActionsClickWEP("settingsMenu");
		waitForPageLoaded();
		WEPWPTPage.clickByActionsClickWEP("preferencesTab");
		LOGGER.info("Redirected to preferences Page");
		WEPWPTPage.clickByActionsClickWEP("CompanyPinLink");		
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("CompanyPinHeading"),"Company Heading is not present");
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("CompanyPinSubHeading"),"Company Sub-Heading is not present");
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("CompanyPinHideAndShow"),"Company Sub-Heading is not present");
		LOGGER.info("Now click on the Show Pin and verify the CPin");
		sleeper(3000);
		WEPWPTPage.clickByActionsClickWEP("CompanyPinHideAndShow");	
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("CompanyPinShow"),"Company Pin is not avaialable");
		sleeper(3000);
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("CompanyPinExpiryDate"),"Company Pin Expiration date is not available");
		softAssert.assertAll();
		String CPin=WEPWPTPage.getTextOfElement("companyPin");	    		
		LOGGER.info("Company Pin is " + CPin);

	}


	@Test(priority = 12, groups = { "REGRESSION_WPT" })
	public final void verifyDomainName() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();			
		leftSideMenuVerification();			
		WEPWPTPage.clickByActionsClickWEP("settingsMenu");
		waitForPageLoaded();
		WEPWPTPage.clickByActionsClickWEP("preferencesTab");
		LOGGER.info("Redirected to preferences Page");
		sleeper(3000);
		WEPWPTPage.clickByActionsClickWEP("domainNameLink");
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("domainNameHeading"),"Domain name Heading is not present");
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("domainNameSubHeading"),"Domain name Sub-Heading is not present");
		sleeper(3000);
		WEPWPTPage.clickByActionsClickWEP("domainNameEdit");
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("domainNameEditModal"),"Domain name dui-text-modal is not present");
		sleeper(3000);	
		String domainNames = getEnvironmentSpecificData(System.getProperty("environment"), "WPT_SELECT_DOMAINS");
		WEPWPTPage.enterTextForUserPage("domainNameEditModalInputText", domainNames);
		WEPWPTPage.clickByActionsClickWEP("domainNameEditModalSaveButton");
		LOGGER.info("Now Get the Domain name");
		sleeper(3000);	
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("domainNames"),"domainNames are not present");
		WEPWPTPage.verifydomainNameswithStatus("domainNames");
		LOGGER.info("Outside the Domain name");					
		softAssert.assertAll();

	}	

	@Test(priority = 13, groups = { "REGRESSION_WPT" })
	public final void verifyBIOSAndDriver() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage = new WEPWPTPage(PreDefinedActions.getDriver()).getInstance(); 		
        if(WEPWPTPage.verifyElementsOfWEPpreferencesPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(WEPWPTPage.verifyElementsOfWEPpreferencesPage("leftSideMenuExpandBtn")) {
    		WEPWPTPage.clickByActionsClickWEP("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        if (!toggleVerification(WPTVariable.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
        	WEPWPTPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
        }
        else{
        	WEPWPTPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        }
        waitForPageLoaded();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        String serialNumber = getEnvironmentSpecificData(System.getProperty("environment"), "TEST_DEVICE_SERIAL_NUM");
        ArrayList<String> serialNumbers = new ArrayList<>(List.of(serialNumber));
        WEPWPTPage.clearFiltersOfDevicesListPage("clearfilter");
        String Device_serialNumber = getEnvironmentSpecificData(System.getProperty("environment"),"WPT_DEVICE_SERIALNUMBER");
        WEPWPTPage.verifyWPTWEPDevicesOnListPage("Device_serialNumber");
        WEPWPTPage.clickByActionsClickWEP("firstSerialNumberText");
        waitForPageLoaded();
        assertTrue(WEPDeviceDetailsPage.VerifyBIOSTabContents(), "BIOS tab sections are not matching the expected header sections");
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("biosDataTable"),"BIOS Data Table is not present");
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("biosDataTableFirstRow"),"biosDataTableFirstRow Table is not present");
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("driverDataTable"),"driverDataTable  Table is not present");
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("driverDataTableFirstRow"),"driverDataTableFirstRow  Table is not present");
        softAssert.assertAll();
	}


	@Test(priority = 14, groups = { "REGRESSION_WPT" },enabled=false)
	public final void verifyPlanChange() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage = new WEPWPTPage(PreDefinedActions.getDriver()).getInstance(); 		
        if(WEPWPTPage.verifyElementsOfWEPpreferencesPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(WEPWPTPage.verifyElementsOfWEPpreferencesPage("leftSideMenuExpandBtn")) {
    		WEPWPTPage.clickByActionsClickWEP("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        if (!toggleVerification(WPTVariable.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
        	WEPWPTPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
        }
        else{
        	WEPWPTPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        }
        waitForPageLoaded();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        String serialNumber = getEnvironmentSpecificData(System.getProperty("environment"), "TEST_DEVICE_SERIAL_NUM");
        ArrayList<String> serialNumbers = new ArrayList<>(List.of(serialNumber));
        WEPWPTPage.clearFiltersOfDevicesListPage("clearfilter");
        String Device_serialNumber = getEnvironmentSpecificData(System.getProperty("environment"),"WPT_DEVICE_SERIALNUMBER");
        WEPWPTPage.verifyWPTWEPDevicesOnListPage("Device_serialNumber");
        WEPWPTPage.clickByActionsClickWEP("selectAllDevice");
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("changePlanButton"),"change Plan Button is not present");
        WEPWPTPage.clickByActionsClickWEP("changePlanButton");
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("changePlanTitle"),"change Plan Title is not present");
        WEPWPTPage.clickByActionsClickWEP("changePlanSelect");
        WEPWPTPage.selectPlan("changePlanSelectOptions", WPTVariable.WPT_Select_Plan);
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("changePlanBtn"),"change Plan Button in model is not present");
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("cancelPlanBtn"),"cancel Plan Button in model is not present");
        WEPWPTPage.clickByActionsClickWEP("changePlanBtn");
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("changePlanAlert"),"change Plan Alert is not present");
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("planColumnHeader"),"planColumn Header is not present");
        String planColumnHeaderTextValue = WEPWPTPage.getTextOfElement("planColumnHeaderTextValue");
        softAssert.assertEquals(planColumnHeaderTextValue, WPTVariable.WPT_Select_Plan1, "Plan column header text value is not matching with expected value");
        sleeper(3000);
        WEPWPTPage.clickByActionsClickWEP("firstSerialNumberText"); 
        waitForPageLoaded();
        WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();       
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("planEnrollmentHeader"),"plan Enrollment Header is not present");	        
        WEPWPTPage.clickByActionsClickWEP("editPlanEnrollment"); 
        sleeper(3000);
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("editPlanTitle"),"edit Plan Enrollment Title is not present");
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("editPlanText"),"edit Plan Enrollment Title is not present");
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("editPlanDelete"),"edit Plan Delete is not present");   
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("changePlanBtn"),"change Plan Button in model is not present");
        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("cancelPlanBtn"),"cancel Plan Button in model is not present");
        softAssert.assertAll();
	}

	@Test(priority = 15, groups = { "REGRESSION_WPT" })
	public final void verifyLogs() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();	
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();	
		WEPWPTPage.clickByActionsClickWEP("settingsMenu");
		waitForPageLoaded();
		sleeper(3000);
		WEPWPTPage.clickByActionsClickWEP("logsTab");
		sleeper(3000);
		LOGGER.info("Redirected to logs list page");
		WEPWPTPage.clickByActionsClickWEP("logsTabLink");		
		softAssert.assertEquals(WEPWPTPage.getTextOfElement("logsHeader"),"Logs");		
		WEPWPTPage.verifyElementsOfPresence("logsWarning");
		softAssert.assertTrue(logPage.verifyElementsOfLogPage("listTable"), "Table on logs list page is not loaded successfully");
		softAssert.assertTrue(logPage.verifyElementsOfLogPage("dateAndTimeHeader"), "Default column is not present on logs list page");
		WEPWPTPage.clickByActionsClickWEP("clearfilter");			
		LOGGER.info("Logs list page is loaded successfully");
		softAssert.assertTrue(WEPWPTPage.verifyDateAndTimeSortingAscending("dateAndTimeSorting",WPTVariable.WPT_Select_dateAndTimeAttribute),"Date and Time sorting is not working as expected");
		WEPWPTPage.clickByActionsClickWEP("logsType");		
		WEPWPTPage.selectLogsType("logsTypeInputOptions",WPTVariable.WPT_Select_LogsType);
		WEPWPTPage.clickByActionsClickWEP("logsType");
		sleeper(3000);
		WEPWPTPage.clickByActionsClickWEP("logsSubType");	
		WEPWPTPage.selectLogsSubType("logsSubTypeInputOptions",WPTVariable.WPT_Select_LogsSubType);	
		WEPWPTPage.clickByActionsClickWEP("logsSubType");		
		WEPWPTPage.enterTextForUserPage("inputeDescription", WPTVariable.WPT_Logs_Description);
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("logTableFirstRow"),"logs Table FirstRow is not present");
		sleeper(3000);
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("logTableFirstRow"),"logs Table FirstRow is not present");
		softAssert.assertTrue(WEPWPTPage.verifyLogsdate("logTableFirstRowDateAndTime"),"logs Date and Time is not Present");;
		softAssert.assertTrue(WEPWPTPage.verifyLogsData("logTableFirstRowDescription", WPTVariable.WPT_Logs_Description),"logs Table Description is not present");;		 
		softAssert.assertAll();

	}


	@Test(priority = 16, groups = { "REGRESSION_WPT" })
	public final void verifyLocation() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();			
		leftSideMenuVerification();			
		WEPWPTPage.clickByActionsClickWEP("settingsMenu");
		waitForPageLoaded();
		WEPWPTPage.clickByActionsClickWEP("preferencesTab");
		LOGGER.info("Redirected to preferences Page");
		sleeper(3000);
		WEPWPTPage.clickByActionsClickWEP("dataCollectionLink");
		WEPWPTPage.verifyElementsOfWEPpreferencesPage("companyLableLocation");	
		WEPWPTPage.verifyElementsOfWEPpreferencesPage("locationToggle");			
		WEPWPTPage.verifyVisibilityofToggle("locationToggle");
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("locationToggle"), "location Toggle option is not available");
		sleeper(2000);
		WEPWPTPage.waitForElementsOfUserDetailsPage("locationToggleInput");
		boolean isInitiallyOn = WEPWPTPage.verifyElementIsSelectedOfPreferencePage("locationToggleInput");
		LOGGER.info("Initial toggle state: " + (isInitiallyOn ? "ON" : "OFF"));	
		if (!isInitiallyOn) {
			softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("locationToggle"), "location Toggle option is not available");
			WEPWPTPage.clickOnElementsOfPreferenceDetailsPage("locationToggle");
			sleeper(2000);
			softAssert.assertTrue(WEPWPTPage.verifyElementIsSelectedOfPreferencePage("locationToggleInput"), "Location toggle is not ON");
		} 	
		   if(WEPWPTPage.verifyElementsOfWEPpreferencesPage("leftSideMenuCollapseBtn")) {
	    		LOGGER.info("Left side menu is not collapsed as expected");
	    	}else if(WEPWPTPage.verifyElementsOfWEPpreferencesPage("leftSideMenuExpandBtn")) {
	    		WEPWPTPage.clickByActionsClickWEP("leftSideMenuExpandBtn");
	        	LOGGER.info("Left side menu is expanded successfully");
	    	}
	        if (!toggleVerification(WPTVariable.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	        	WEPWPTPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
	        }
	        else{
	        	WEPWPTPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
	        }
	        waitForPageLoaded();
	        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
	        String serialNumber = getEnvironmentSpecificData(System.getProperty("environment"), "TEST_DEVICE_SERIAL_NUM");
	        ArrayList<String> serialNumbers = new ArrayList<>(List.of(serialNumber));
	        WEPWPTPage.clearFiltersOfDevicesListPage("clearfilter");
	        String Device_serialNumber = getEnvironmentSpecificData(System.getProperty("environment"),"WPT_DEVICE_SERIALNUMBER");
	        WEPWPTPage.verifyWPTWEPDevicesOnListPage("Device_serialNumber");
	        WEPWPTPage.clickByActionsClickWEP("firstSerialNumberText");
	        waitForPageLoaded();
	        assertTrue(WEPDeviceDetailsPage.VerifyLocationTabContents(), "Location tab sections are not matching the expected header sections");
	        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("locationDetailsDiv"),"Locationdetails are not present");
	        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("locationSource"),"locationSource is not present");
	        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("locationSourceValue"),"locationSource Value is not present");
	        softAssert.assertEquals(WEPWPTPage.verifylocationSourceValue("locationAddress","Windows Location"),"location source value  is not matched");
	        softAssert.assertAll();
		}


	@Test(priority = 17, groups = { "REGRESSION_WPT" },enabled=false)
	public final void verifyLocationWiFiDevice() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();			
		leftSideMenuVerification();			
		WEPWPTPage.clickByActionsClickWEP("settingsMenu");
		waitForPageLoaded();
		WEPWPTPage.clickByActionsClickWEP("preferencesTab");
		LOGGER.info("Redirected to preferences Page");
		sleeper(3000);
		WEPWPTPage.clickByActionsClickWEP("dataCollectionLink");
		WEPWPTPage.verifyElementsOfWEPpreferencesPage("companyLableLocation");	
		WEPWPTPage.verifyElementsOfWEPpreferencesPage("locationToggle");			
		WEPWPTPage.verifyVisibilityofToggle("locationToggle");
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("locationToggle"), "location Toggle option is not available");
		sleeper(2000);
		WEPWPTPage.waitForElementsOfUserDetailsPage("locationToggleInput");
		boolean isInitiallyOn = WEPWPTPage.verifyElementIsSelectedOfPreferencePage("locationToggleInput");
		LOGGER.info("Initial toggle state: " + (isInitiallyOn ? "ON" : "OFF"));	
		if (!isInitiallyOn) {
			softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("locationToggle"), "location Toggle option is not available");
			WEPWPTPage.clickOnElementsOfPreferenceDetailsPage("locationToggle");
			sleeper(2000);
			softAssert.assertTrue(WEPWPTPage.verifyElementIsSelectedOfPreferencePage("locationToggleInput"), "Location toggle is not ON");
		} 	
		   if(WEPWPTPage.verifyElementsOfWEPpreferencesPage("leftSideMenuCollapseBtn")) {
	    		LOGGER.info("Left side menu is not collapsed as expected");
	    	}else if(WEPWPTPage.verifyElementsOfWEPpreferencesPage("leftSideMenuExpandBtn")) {
	    		WEPWPTPage.clickByActionsClickWEP("leftSideMenuExpandBtn");
	        	LOGGER.info("Left side menu is expanded successfully");
	    	}
	        if (!toggleVerification(WPTVariable.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	        	WEPWPTPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
	        }
	        else{
	        	WEPWPTPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
	        }
	        waitForPageLoaded();
	        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
	        String serialNumber = getEnvironmentSpecificData(System.getProperty("environment"), "TEST_DEVICE_SERIAL_NUM");
	        ArrayList<String> serialNumbers = new ArrayList<>(List.of(serialNumber));
	        WEPWPTPage.clearFiltersOfDevicesListPage("clearfilter");
	        String Device_serialNumber = getEnvironmentSpecificData(System.getProperty("environment"),"WPT_DEVICE_SERIALNUMBER");
	        WEPWPTPage.verifyWPTWEPDevicesOnListPage("Device_serialNumber");
	        WEPWPTPage.clickByActionsClickWEP("firstSerialNumberText");
	        waitForPageLoaded();
	        assertTrue(WEPDeviceDetailsPage.VerifyLocationTabContents(), "Location tab sections are not matching the expected header sections");
	        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("locationDetailsDiv"),"Locationdetails are not present");
	        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("locationReloadBtn"),"locationReload button is not present");
	        WEPWPTPage.clickByActionsClickWEP("locationReloadBtn");
	        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("locationSource"),"locationSource is not present");
	        softAssert.assertTrue(WEPWPTPage.verifyElementsOfPresence("locationSourceValue"),"locationSource Value is not present");
	        softAssert.assertEquals(WEPWPTPage.verifylocationSourceValue("locationAddress","Cellular Location"),"location source value  is not matched");
	        softAssert.assertAll();
		}


	@Test(priority = 18, groups = { "REGRESSION_WPT" })
	public final void verifyUserInvite() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();			
		leftSideMenuVerification();	
		 WEPWPTPage.clickByActionsClickWEP("accountMenu");
		 waitForPageLoaded();
		 WEPWPTPage.clickByActionsClickWEP("userTab");	
		 waitForPageLoaded();
		 WEPWPTPage.clickByActionsClickWEP("inviteUserButton");	
		 sleeper(3000);
		 WEPWPTPage.clickByActionsClickWEP("addUser_addManually");
		// WEPWPTPage.waitUntilElementIsVisible("addManually");	
		 String userFirstName = getEnvironmentSpecificData(System.getProperty("environment"), "USER_INVITE_FIRSTNAME");
		 WEPWPTPage.enterTextForUserPage("newUserFirstName", userFirstName);
		 String userLastName = getEnvironmentSpecificData(System.getProperty("environment"), "USER_INVITE_LASTNAME");
		 WEPWPTPage.enterTextForUserPage("newUserLastName", userLastName);
		 String userEmail = getEnvironmentSpecificData(System.getProperty("environment"), "USER_INVITE_EMAIL");
		 WEPWPTPage.enterTextForUserPage("newUserEmail", userEmail);		 
		 WEPWPTPage.clickByActionsClickWEP("newUserSelectIDP");
		 WEPWPTPage.clickByActionsClickWEP("newUserSelectHPID");
		 WEPWPTPage.clickByActionsClickWEP("newUserSelectHPIDNextbtn");
		 WEPWPTPage.clickByActionsClickWEP("newUserAssignRole");
		 WEPWPTPage.clickByActionsClickWEP("newUserAssignRoleSearch");
		 WEPWPTPage.enterTextForUserPage("newUserAssignRoleSearch", WPTVariable.WPT_InviteUser_AssignRole);
		 WEPWPTPage.clickByActionsClickWEP("newUserLDARole");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("inviteUserBackBtn"),"inviteUser BackBtn is not present");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("inviteUserFinishBtn"),"inviteUser FinishBtn is not present");
		 sleeper(3000);
		 WEPWPTPage.clickByActionsClickWEP("inviteUserFinishBtn");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("inviteUserAlert"),"inviteUser Alert is not present");
		 sleeper(3000);
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("createdUserSearch"),"createdUser search is not present");
    	 WEPWPTPage.enterTextForUserPage("createdUserSearch", userEmail);		 
    	 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("createdUserTableRow"),"createdUser search is not present");    			 	 
		 softAssert.assertAll();

	}

	@Test(priority = 19, groups = { "REGRESSION_WPT" },enabled=false)
	public final void verifyUserDelete() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();			
		leftSideMenuVerification();	
		 WEPWPTPage.clickByActionsClickWEP("accountMenu");
		 waitForPageLoaded();
		 WEPWPTPage.clickByActionsClickWEP("userTab");	
		 waitForPageLoaded();	
		 WEPWPTPage.clickByActionsClickWEP("newcreatedUserEmail");
		 String userEmail = getEnvironmentSpecificData(System.getProperty("environment"), "USER_INVITE_EMAIL");
		 WEPWPTPage.enterTextForUserPage("newcreatedUserEmail", userEmail);
		 sleeper(3000);
		 WEPWPTPage.clickByActionsClickWEP("newcreatedUserCheckbox");
		 WEPWPTPage.clickByActionsClickWEP("deleteUserLink");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("deleteUserPopup"),"Delete Popup is not present");
		 String securityCode=WEPWPTPage.getTextOfElement("deleteUserSecurityCode");
		 WEPWPTPage.enterTextForUserPage("deleteUserSecurityCodeinput",securityCode);
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("deleteUserPopupCancel"),"Delete Popup Cancel is not present");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("deleteUserPopupSubmit"),"Delete Popup Cancel is not present");
		 WEPWPTPage.clickByActionsClickWEP("deleteUserPopupSubmit");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("deleteUserAlert"),"Delete Popup Cancel is not present");
		 softAssert.assertAll();

	}

	
	@Test(priority = 20, groups = { "REGRESSION_WPT" },enabled=false)
	public final void verifyAssignRoleToUser() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();			
		leftSideMenuVerification();	
		 WEPWPTPage.clickByActionsClickWEP("accountMenu");
		 waitForPageLoaded();
		 WEPWPTPage.clickByActionsClickWEP("userTab");	
		 waitForPageLoaded();	
		 WEPWPTPage.clickByActionsClickWEP("newcreatedUserEmail");
		 String userEmail = getEnvironmentSpecificData(System.getProperty("environment"), "USER_INVITE_EMAIL");
		 WEPWPTPage.enterTextForUserPage("newcreatedUserEmail", userEmail);
		 sleeper(3000);
		 WEPWPTPage.clickByActionsClickWEP("newcreatedUserCheckbox");	 
		 WEPWPTPage.clickByActionsClickWEP("assignRoleLink");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("rolesModal-label"),"Assign role Popup is not present");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("rolesModal"),"rolesModal is not present");
		 WEPWPTPage.enterTextForUserPage("assignRoleInput", "IT");
		 WEPWPTPage.clickByActionsClickWEP("itAdminCheckbox");		
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("rolesCancelBtn"),"Delete Popup Cancel is not present");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("rolesSaveBtn"),"Delete Popup Cancel is not present");
		 WEPWPTPage.clickByActionsClickWEP("rolesSaveBtn");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("roleAssignAlert"),"Delete Popup Cancel is not present");
		 softAssert.assertAll();

	}

	@Test(priority = 21, groups = { "REGRESSION_WPT" },enabled=false)
	public final void verifyRemindApproverToEnroll() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();			
		leftSideMenuVerification();	
		 WEPWPTPage.clickByActionsClickWEP("accountMenu");
		 waitForPageLoaded();
		 WEPWPTPage.clickByActionsClickWEP("userTab");	
		 waitForPageLoaded();	
		 WEPWPTPage.clickByActionsClickWEP("newcreatedUserEmail");
		 String userEmail = getEnvironmentSpecificData(System.getProperty("environment"), "USER_INVITE_EMAIL");
		 WEPWPTPage.enterTextForUserPage("newcreatedUserEmail", userEmail);
		 sleeper(3000);
		 WEPWPTPage.clickByActionsClickWEP("newcreatedUserCheckbox");	 
		 WEPWPTPage.clickByActionsClickWEP("remindApproverLink");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("remindApproverSubTitle"),"remind Approver SubTitle is not present");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("remindApproverCancel"),"remind Approver Cancel is not present");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("remindApproverSubmit"),"remind Approver Submit is not present");
		 WEPWPTPage.clickByActionsClickWEP("remindApproverSubmit");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("remindApproverAlert"),"remind Approver Alert is not present");
		 softAssert.assertAll();


	}

	@Test(priority = 22, groups = { "REGRESSION_WPT" },enabled=false)
	public final void verifyResendInvitation() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();			
		leftSideMenuVerification();	
		 WEPWPTPage.clickByActionsClickWEP("accountMenu");
		 waitForPageLoaded();
		 WEPWPTPage.clickByActionsClickWEP("userTab");	
		 waitForPageLoaded();
		 WEPWPTPage.clickByActionsClickWEP("newcreatedUserEmail");
		 String resendInviteMail = getEnvironmentSpecificData(System.getProperty("environment"), "USER_RESEND_INVITE_EMAIL");
		 WEPWPTPage.enterTextForUserPage("newcreatedUserEmail", resendInviteMail);
		 sleeper(3000);
		 WEPWPTPage.clickByActionsClickWEP("newcreatedUserCheckbox");	 
		 WEPWPTPage.clickByActionsClickWEP("resendInvitaionLink");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("resendInvitaionPopup"),"resend Invitaion Popup is not present");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("resendInvitaionCancelButton"),"resend Invitaion CancelButton is not present");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("resendInvitaionButton"),"resendInvitaionButton is not present");
		 WEPWPTPage.clickByActionsClickWEP("resendInvitaionButton");
		 softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("resendInvitaionAlert"),"resendInvitaion Alert is not present");
		 softAssert.assertAll();

	}
}

