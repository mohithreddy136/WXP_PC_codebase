package com.testscripts.daasui;

import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.WEPPulsesCreationPageVariables;

import org.testng.annotations.Test;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import  java.util.List;
import org.openqa.selenium.WebElement;

public class WEPCustomerHomeTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(WEPCustomerHomeTest.class);

	@Test(priority = 1, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C43390062")
	public final void verifyCustomerHomePageWidgets() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerHomePage customerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		customerHomePage.companyView(CommonVariables.PARTNER_Home);
		waitForPageLoaded();
		softAssert.assertTrue(customerHomePage.verifyWexScore("wexScore"));
		softAssert.assertTrue(customerHomePage.verifyWexScoreOverTime("wexScoreOverTime"));
		softAssert.assertTrue(customerHomePage.verifyFleetInventory("fleetInventory"));
		softAssert.assertTrue(customerHomePage.verifyAppsWithMostCrashes("appsWithMostCrashes"));
		softAssert.assertAll();
	}

	@Test(priority = 2, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_LDK","INITECH_SOLUTIONS"}, description = "TestCaseID: C43522599", enabled=false)
	public final void verifyWEPCustomerDashboard() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerHomePage customerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();

		//step 1,2,3
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("onboarding"), "Onboarding notification is not visible.");
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("onboarding");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("onboardingTitle"), "Onboarding title is missing.");
		customerHomePage.waitForElementsOfWEPCustomerHomePage("firstStep");

		//device added
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("deviceAddedNotification"), "Onboarding deviceAddedNotification is missing.");
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("deviceAddedNotification");
		waitForPageLoaded();
		customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");
		customerHomePage.waitForElementsOfWEPCustomerHomePage("addDevice");

		//Team invited
		Thread.sleep(2000);
		waitForPageLoaded();
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("userAddNotification"), "Onboarding deviceAddedNotification is missing.");
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("userAddNotification");
		waitForPageLoaded();
		customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");
		customerHomePage.waitForElementsOfWEPCustomerHomePage("addUser");

		//profile completed
		Thread.sleep(2000);
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("profileCompNotification"), "Onboarding profileCompNotification is missing.");
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("profileCompNotification");
		waitForPageLoaded();
		customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");
		customerHomePage.waitForElementsOfWEPCustomerHomePage("accountManagement");

		//Explore integrations
		Thread.sleep(2000);
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("setupInteNotification"), "Onboarding setupIntegrationNotification is missing.");
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("setupInteNotification");
		waitForPageLoaded();
		customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");
		customerHomePage.waitForElementsOfWEPCustomerHomePage("integrations");

		customerHomePage.waitForElementsOfWEPCustomerHomePage("discoverMore");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {	
		//improve experience score
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("onboarding");
		customerHomePage.scrollToElementsOfWEPCustomerHomePage("improveExpScore");
		Thread.sleep(2000);
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("improveExpScore"), "Onboarding improveExpScore is missing.");
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("improveExpScore");
		waitForPageLoaded();
		customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");

		//Learn about HP workspace
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("onboarding");
		Thread.sleep(2000);
		customerHomePage.scrollToElementsOfWEPCustomerHomePage("exploreFleetMgmt");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("exploreFleetMgmt"), "Onboarding exploreFleetMgmt is missing.");
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("exploreFleetMgmt");
		waitForPageLoaded();
		customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");

		//Monitor Issue
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("onboarding");
		Thread.sleep(2000);
		customerHomePage.scrollToElementsOfWEPCustomerHomePage("monitorIssue");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("monitorIssue"), "Onboarding monitorIssue is missing.");
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("monitorIssue");
		waitForPageLoaded();
		customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");

		//Ways to improve IT env.
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("onboarding");
		Thread.sleep(2000);
		customerHomePage.scrollToElementsOfWEPCustomerHomePage("recommendationActions");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("recommendationActions"), "Onboarding recommendationActions is missing.");
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("recommendationActions");
		waitForPageLoaded();
		customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");
		refreshPage();
		waitForPageLoaded();
		} else {
			Thread.sleep(2000);
			customerHomePage.scrollToElementsOfWEPCustomerHomePage("customizeAlerts");
			softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("customizeAlerts"), "Onboarding Alerts notification is missing.");
			customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("customizeAlerts");
			waitForPageLoaded();
			customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");
			
			Thread.sleep(2000);
			customerHomePage.scrollToElementsOfWEPCustomerHomePage("deviceIssues");
			softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("deviceIssues"), "Onboarding Troubleshoot devices notification is missing.");
			customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("deviceIssues");
			waitForPageLoaded();
			customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");	
			waitForPageLoaded();
		}

		//workforce sentiment
		Thread.sleep(2000);
		customerHomePage.scrollToElementsOfWEPCustomerHomePage("customePulse");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("customePulse"), "Onboarding pulseInfo is missing.");
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("customePulse");
		waitForPageLoaded();
		customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");

		//Share imp notifications
		Thread.sleep(2000);
		customerHomePage.scrollToElementsOfWEPCustomerHomePage("sentimentPulse");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("sentimentPulse"), "Onboarding pulseInfoMore is missing.");
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("sentimentPulse");
		waitForPageLoaded();
		customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");

		//Share imp feedback
		if (!customerHomePage.waitForElementsOfWEPCustomerHomePage("moreFeedback")) {
			LOGGER.warn("More feedback not available.");
		} else {
			//More feedback
			Thread.sleep(2000);
			customerHomePage.scrollToElementsOfWEPCustomerHomePage("moreFeedback");
			softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("moreFeedback"), "Onboarding moreFeedback is missing.");
			customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("moreFeedback");
			waitForPageLoaded();
			customerHomePage.waitForElementsOfWEPCustomerHomePage("notificationPopUp");
			customerHomePage.mouseHoverOnElementsOfWEPCustomerHomePage("onboarding");
			customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("onboarding");
			//navigateToBack();
		}
		waitForPageLoaded();
		//step 4
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("experienceScore"), "experienceScore tab missing.");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("experienceOverTime"), "experienceOverTime tab missing.");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("recommendedActionTab"), "recommendedActionTab tab missing.");
		customerHomePage.scrollToElementsOfWEPCustomerHomePage("fleetInventoryTab");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("fleetInventoryTab"), "fleetInventoryTab tab missing.");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("appWithMostCrashes"), "appWithMostCrashes tab missing.");

		try {
			waitForPageLoaded();
			if (customerHomePage.verifyElementsOfWEPCustomerHomePage("experienceScoreNoData")) {
				// experienceScore Graph is missing
				LOGGER.warn("No data to display on Experience Score tab.");
			} else {
				//step 6
				customerHomePage.scrollToElementsOfWEPCustomerHomePage("experienceScore");
				try {
					//step 5
					customerHomePage.scrollToElementsOfWEPCustomerHomePage("experienceScore");
					softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("overallScore"), "overallScore tab missing.");
					softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("sentimentScore"), "sentimentScore tab missing.");
					softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("sysHealthScore"), "sysHealthScore tab missing.");
					softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("osPerfScore"), "osPerfScore tab missing.");
					softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("networkHealthScore"), "network health tab missing.");
					softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("securityScore"), "securityScore tab missing.");
					softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("applicationScore"), "applicationScore tab missing.");
					boolean issentimentScoreValid = false;
					if(customerHomePage.verifyElementsOfWEPCustomerHomePage("sentimentScoreVal")) {
						String sentimentScoreValue = customerHomePage.getTextOfWEPCustomerHomePage("sentimentScoreVal");
						issentimentScoreValid = customerHomePage.validateNumericValueWithRange(sentimentScoreValue, 0, 100, "Sentiment Score");
						}else {
							LOGGER.info("Sentiment Score value is not present");
						}

					String overallScoreValue = customerHomePage.getTextOfWEPCustomerHomePage("overallScoreVal");
					String sysHealthScoreValue = customerHomePage.getTextOfWEPCustomerHomePage("sysHealthScoreVal");
					String osPerfScoreValue = customerHomePage.getTextOfWEPCustomerHomePage("osPerfScoreVal");
					String networkHealthScoreValue = customerHomePage.getTextOfWEPCustomerHomePage("networkHealthScoreVal");
					String securityScoreValue = customerHomePage.getTextOfWEPCustomerHomePage("securityScoreVal");
					String applicationScoreValue = customerHomePage.getTextOfWEPCustomerHomePage("applicationScoreVal");

					boolean isOverallScoreValid = customerHomePage.validateNumericValueWithRange(overallScoreValue, 0, 100, "Overall Score");
					boolean issysHealthScoreValue = customerHomePage.validateNumericValueWithRange(sysHealthScoreValue, 0, 100, "System Health Score");
					boolean isosPerfScoreValue = customerHomePage.validateNumericValueWithRange(osPerfScoreValue, 0, 100, "OS Performance Score");
					boolean isnetworkHealthScoreValue = customerHomePage.validateNumericValueWithRange(networkHealthScoreValue, 0, 100, "Network Health Score");
					boolean issecurityScoreValue = customerHomePage.validateNumericValueWithRange(securityScoreValue, 0, 100, "Security Score");
					boolean isapplicationScoreValue = customerHomePage.validateNumericValueWithRange(applicationScoreValue, 0, 100, "Application Score");

					// Check overall validation results
					if (isOverallScoreValid && issysHealthScoreValue && isosPerfScoreValue && issecurityScoreValue && isapplicationScoreValue && issentimentScoreValid) {
						LOGGER.info("All tab scores are valid.");
					} else {
						LOGGER.warn("One or more tab scores are invalid or missing.");
					}
				} catch (Exception e) {
					LOGGER.error("An error occurred while checking Experience Over Time locators.", e);
				}
			}
		} catch (Exception e) {
			LOGGER.error("An error occurred while checking experienceScore action locator.", e);
		}


		//step 7
		customerHomePage.scrollToElementsOfWEPCustomerHomePage("recommendedActionTab");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("recommendedActionTab"), "recommendedActionTab RA info tab missing.");

		//step 8
		customerHomePage.scrollToElementsOfWEPCustomerHomePage("fleetInventoryTab");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("fleetInventoryCount"), "fleetInventoryLabel active device info label missing.");
		softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("viewListOfPCDevices"), "viewListOfPCDevices active device info label missing.");
		String FeeltInventoryDeviceCount = customerHomePage.getTextOfWEXScore("fleetInventoryCount");
		int TotalFeeltInventoryDeviceCount = Integer.parseInt(FeeltInventoryDeviceCount);
		LOGGER.info("Device count: " + FeeltInventoryDeviceCount);
		customerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("viewListOfPCDevices");
		waitForPageLoaded();
		Thread.sleep(5000);
		String totalDevice = wepDeviceListPage.getTextOfWEPDeviceListPage("deviceCount").replace("of ", "");
		int TotalDevicesCount = Integer.parseInt(totalDevice);
		LOGGER.info("Device count total: " + TotalDevicesCount);
		Assert.assertEquals(TotalDevicesCount, TotalFeeltInventoryDeviceCount, "Mismatch found: The total devices and total fleet inventory page devices count are different.");
		navigateToBack();
		waitForPageLoaded();

		//step 9
		customerHomePage.scrollToElementsOfWEPCustomerHomePage("appWithMostCrashes");
		try {
			// Check if the expOvrTimeImp locator exists
			waitForPageLoaded();
			if (customerHomePage.waitForElementsOfWEPCustomerHomePage("appWithCrashNoData")) {
				// Graph is missing
				LOGGER.warn("No data to display on Apps with Most Crashes tab.");
			} else {
				softAssert.assertTrue(customerHomePage.waitForElementsOfWEPCustomerHomePage("appWithCrashData"), "appWithCrashData RA info tab missing.");
			}
		} catch (Exception e) {
			LOGGER.error("An error occurred while checking appWithCrashData action locator.", e);
		}
	}

	/**
	 * This test case is to verify Add user functionality for Enrolled Devices
	 * */
	@Test(priority = 3, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : C43522589")
	public final void verifyWEPAddUserfunctionality() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerHomePage WEPAddUser = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		waitForPageLoaded();
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("userProfiles"), "User Profiles option not available");
		userDetailsPage.clickOnElementsOfUserDetailsPage("userProfiles");
		waitForPageLoaded();

		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("userProfilesTimeZone"), "User Profiles TimeZone option not available");
		userDetailsPage.verifyElementsOfUserDetailsPage("editTimeZone");
		userDetailsPage.clickOnElementsOfUserDetailsPage("editTimeZone");
		waitForPageLoaded();
		userDetailsPage.verifyVisibilityofToggle("timeZoneToggleVisual");
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("timeZoneToggleVisual"), "TimeZone Toggle option not available");
		waitForPageLoaded();
		userDetailsPage.waitForElementsOfUserDetailsPage("timeZoneToggleInput");
		boolean isInitiallyOn = userDetailsPage.verifyElementIsSelectedOfUserDetailsPage("timeZoneToggleInput");
		LOGGER.info("Initial toggle state: " + (isInitiallyOn ? "ON" : "OFF"));
		if(isInitiallyOn) {
		    softAssert.assertFalse(userDetailsPage.verifyElementIsEnableOnUserProfilePage("timeZoneEnabled"), "TimeZone Toggle option is Disabled");
		}else {
			softAssert.assertTrue(userDetailsPage.verifyElementIsEnableOnUserProfilePage("timeZoneEnabled"), "TimeZone Toggle option is Enabled");
		}
		userDetailsPage.clickOnElementsOfUserDetailsPage("timeZoneToggleVisual");
		waitForPageLoaded();
		sleeper(2000);
		boolean isNowOn = userDetailsPage.verifyElementIsSelectedOfUserDetailsPage("timeZoneToggleInput");
		LOGGER.info("Toggle flipped. New state: " + (isNowOn ? "ON" : "OFF"));
		if(isNowOn) {
		    softAssert.assertFalse(userDetailsPage.verifyElementIsEnableOnUserProfilePage("timeZoneEnabled"), "TimeZone Toggle option is Disabled");
		}else {
			softAssert.assertTrue(userDetailsPage.verifyElementIsEnableOnUserProfilePage("timeZoneEnabled"), "TimeZone Toggle option is Enabled");
		}
		if(userDetailsPage.verifyElementIsEnableOnUserProfilePage("timeZoneEnabled")){
		userDetailsPage.clickOnElementsOfUserDetailsPage("timeZoneToggleVisual");
		sleeper(2000);
		}
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("timeZoneValue"), "User Profiles TimeZone value not available");
		String currentTimeZone = userDetailsPage.getTextOfUserDetailsPage("timeZoneValue");
		LOGGER.info("Timezone Text: " + currentTimeZone);

//		This pattern will match any valid (GMT +00.00) format followed by any text OR specifically Etc/UTC
		Pattern pattern = Pattern.compile("\\(GMT ?[+-]\\d{2}:\\d{2}\\) (.+|Etc/UTC)");
		WEPAddUser.validateTimeZone(currentTimeZone, pattern, "currentTimeZone");

		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("timeZoneCancelBtn"), "Cancel button not available");
		userDetailsPage.clickOnElementsOfUserDetailsPage("timeZoneCancelBtn");

		softAssert.assertAll();
		LOGGER.info("Validation of verify Add user functionality for Enrolled Devices completed successfully.");

	}
	/** 	    
	 *  This test case is to verify Connectors under Integrations tab in WEP Customer Home page
	 * @throws Exception
	 */
	@Test(priority = 4, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS", "STAGE_LDK"})
	public final void verifyIntegrationsTab() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		WEXCustomerHomePage customerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		customerHomePage.companyView(CommonVariables.PARTNER_Home);
		waitForPageLoaded();
		customerHomePage.scrollToElementsOfWEPCustomerHomePage("integrationTab");
		customerHomePage.clickOnElementsOfWEPCustomerHomePage("integrationTab");
		waitForPageLoaded();
		customerHomePage.verifySwitchToFrame("connectorFrame");
		sleeper(2000);
		softAssert.assertTrue(customerHomePage.verifyIntegrationConnectors("serviceNow"),"serviceNow Connector is not present");
		softAssert.assertTrue(customerHomePage.verifyIntegrationConnectors("tablue"),"tablue Connector is not present");
		softAssert.assertTrue(customerHomePage.verifyIntegrationConnectors("entraIDAccount"),"entraIDAccount Connector is not present");				
		softAssert.assertTrue(customerHomePage.verifyIntegrationConnectors("powerAutomate"),"powerAutomate Connector is not present");
		softAssert.assertTrue(customerHomePage.verifyIntegrationConnectors("entraIdGroup"),"entraIdGroup Connector is not present");
		softAssert.assertTrue(customerHomePage.verifyIntegrationConnectors("splunk"),"splunk Connector is not present");
		softAssert.assertTrue(customerHomePage.verifyIntegrationConnectors("powerBi"),"powerBi Connector is not present");
		customerHomePage.verifySwitchToDefaultContent();
		softAssert.assertAll();
	}
	
	@Test(priority = 5, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS", "STAGE_LDK"})
	public final void verifyExperienceOverTimeWidgetonCustomerDashboard() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		 String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
		WEXCustomerHomePage customerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXDetailsPage WEXDetailsPage = new WEXDetailsPage(PreDefinedActions.getDriver()).getInstance();

		Assert.assertTrue(customerHomePage.matchTextOfCustomerHomePage("experienceOverTime", getTextLanguage(LanguageCode, "daas_ui", "ee_wex_over_time_heading")), "Experience Over Time widget is not available on customer dashboard");
		softAssert.assertTrue(customerHomePage.verifyElementsOfWEPCustomerHomePage("experienceOverTimeGraph"), "experienceOverTimeGraph locator is not present");

		Assert.assertTrue(customerHomePage.verifyElementsOfWEPCustomerHomePage("great"), "Great Experience locator is not available on customer dashboard");
		Assert.assertTrue(customerHomePage.verifyElementsOfWEPCustomerHomePage("fair"), "Fair Experience locator is not available on customer dashboard");
		Assert.assertTrue(customerHomePage.verifyElementsOfWEPCustomerHomePage("poor"), "Poor Experience locator is not available on customer dashboard");
		softAssert.assertTrue(customerHomePage.verifyElementsOfWEPCustomerHomePage("experienceOverTimeXaxis"), "experienceOverTimeXaxis locator is not present");
		softAssert.assertTrue(customerHomePage.verifyElementsOfWEPCustomerHomePage("experienceOverTimeYaxis"), "experienceOverTimeYaxis locator is not present");
		softAssert.assertTrue(customerHomePage.verifyElementsOfWEPCustomerHomePage("experienceOverTimeGraph1"), "experienceOverTimeYaxis locator is not present");
		softAssert.assertTrue(customerHomePage.verifyElementsOfWEPCustomerHomePage("experienceOverTimeGraph2"), "experienceOverTimeYaxis locator is not present");
		softAssert.assertTrue(customerHomePage.verifyElementsOfWEPCustomerHomePage("experienceOverTimeGraph3"), "experienceOverTimeYaxis locator is not present");

		softAssert.assertTrue(customerHomePage.verifyElementsOfWEPCustomerHomePage("expOverTImeframeworkFilter"), "experienceOverTimeFrameworkSelector locator is not present");

		customerHomePage.mouseHoverOnElementsOfWEPCustomerHomePage("expOverTimeFilter");
		softAssert.assertTrue(customerHomePage.actionClickOnElementsOfCustomerHomePage("expOverTimeFilter"), "experienceOverTimeFrameworkSelector is not clickable");
		Assert.assertTrue(customerHomePage.matchTextOfCustomerHomePage("last30Days", getTextLanguage(LanguageCode, "daas_ui", "ee_last_30_days")), "Last 30 Days option is not available on partner dashboard");
		softAssert.assertTrue(customerHomePage.actionClickOnElementsOfCustomerHomePage("last30Days"), "last30Days is not clickable");

		softAssert.assertTrue(customerHomePage.actionClickOnElementsOfCustomerHomePage("expOverTimeFilter"), "experienceOverTimeFrameworkSelector is not clickable");
		Assert.assertTrue(customerHomePage.matchTextOfCustomerHomePage("last90Days", getTextLanguage(LanguageCode, "daas_ui", "ee_last_90_days")), "Last 90 Days option is not available on partner dashboard");
		softAssert.assertTrue(customerHomePage.actionClickOnElementsOfCustomerHomePage("last90Days"), "last90Days locator is not present");

		Assert.assertTrue(WEXDetailsPage.verifyListofElementsWithCountOfWEXDetailsPage("ExperienceOverTimeGraphLines",WEPPulsesCreationPageVariables.Partner_Experience_OverTime_graphLines_COUNT),"Experience over time graph lines are not present on the experience details page");

		softAssert.assertAll();
		LOGGER.info("The Experience Over Time widget on Customer Dashboard verified successfully");
	}
	
	@Test(priority = 6, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS", "STAGE_LDK"})
	public final void verifyPCsFleetInventory() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		 String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
		WEXCustomerHomePage wepCustomerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
	    wepCustomerHomePage.scrollToElementsOfWEPCustomerHomePage("pcContent");
	    Assert.assertTrue(wepCustomerHomePage.waitForElementsOfWEPCustomerHomePage("categoryNamePC"), "PCs category tile not found.");
	    wepCustomerHomePage.scrollToElementsOfWEPCustomerHomePage("categoryNamePC");
	    wepCustomerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("categoryNamePC");
	    sleeper(2000);
	    List<WebElement> slices = wepCustomerHomePage.getAllElementsofWEPCustomerHomePage("chartColorBarsPC");
	    Assert.assertNotNull(slices, "Pie slices list is null.");
	    Assert.assertFalse(slices.isEmpty(), "No pie slices displayed for PCs.");
	    softAssert.assertTrue(wepCustomerHomePage.verifyFleetInventoryPieChart("chartColorBarsPC","chartToolTip","categoryNamePC",LanguageCode), "End-to-end PCs pie workflow failed (verifyFleetInventoryPieChart=false).");
	    softAssert.assertAll();
	}
	
	@Test(priority = 7, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS", "STAGE_LDK"})
	public final void verifyVMFleetInventory() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		 String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
		WEXCustomerHomePage wepCustomerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
	    wepCustomerHomePage.scrollToElementsOfWEPCustomerHomePage("pcContent");
	    Assert.assertTrue(wepCustomerHomePage.waitForElementsOfWEPCustomerHomePage("categoryNameVM"), "VM category tile not found.");
	    wepCustomerHomePage.scrollToElementsOfWEPCustomerHomePage("categoryNameVM");
	    wepCustomerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("categoryNameVM");
	    sleeper(3500);
	    List<WebElement> slices = wepCustomerHomePage.getAllElementsofWEPCustomerHomePage("chartColorBarsVM");
	    Assert.assertNotNull(slices, "Pie slices list is null.");
	    Assert.assertFalse(slices.isEmpty(), "No pie slices displayed for Virtual Machine.");
	    softAssert.assertTrue(wepCustomerHomePage.verifyFleetInventoryPieChart("chartColorBarsVM","chartToolTip","categoryNameVM",LanguageCode), "End-to-end Virtual Machine pie workflow failed (verifyFleetInventoryPieChart=false).");
	    softAssert.assertAll();
	}
	
	@Test(priority = 8, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS", "STAGE_LDK"})
	public final void verifyVEFleetInventory() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		WEXCustomerHomePage wepCustomerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
	    wepCustomerHomePage.scrollToElementsOfWEPCustomerHomePage("pcContent");
	    Assert.assertTrue(wepCustomerHomePage.waitForElementsOfWEPCustomerHomePage("categoryNameVE"), "VE category tile not found.");
	    wepCustomerHomePage.scrollToElementsOfWEPCustomerHomePage("categoryNameVE");
	    wepCustomerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("categoryNameVE");
	    sleeper(2000);
	    List<WebElement> slices = wepCustomerHomePage.getAllElementsofWEPCustomerHomePage("chartColorBarsVE");
	    Assert.assertNotNull(slices, "Pie slices list is null.");
	    Assert.assertFalse(slices.isEmpty(), "No pie slices displayed for Video Endpoints.");
	    softAssert.assertTrue(wepCustomerHomePage.verifyFleetInventoryPieChart("chartColorBarsVE","chartToolTip","categoryNameVE",LanguageCode), "End-to-end Video Endpoints pie workflow failed (verifyFleetInventoryPieChart=false).");
	    softAssert.assertAll();
	}
	
	@Test(priority = 9, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS", "STAGE_LDK"})
	public final void verifyTEFleetInventory() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		WEXCustomerHomePage wepCustomerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
	    wepCustomerHomePage.scrollToElementsOfWEPCustomerHomePage("pcContent");
	    Assert.assertTrue(wepCustomerHomePage.waitForElementsOfWEPCustomerHomePage("categoryNameTP"), "PCs category tile not found.");
	    wepCustomerHomePage.scrollToElementsOfWEPCustomerHomePage("categoryNameTP");
	    wepCustomerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("categoryNameTP");
	    sleeper(2000);
	    List<WebElement> slices = wepCustomerHomePage.getAllElementsofWEPCustomerHomePage("chartColorBarsTP");
	    Assert.assertNotNull(slices, "Pie slices list is null.");
	    Assert.assertFalse(slices.isEmpty(), "No pie slices displayed for Telephones.");
	    softAssert.assertTrue(wepCustomerHomePage.verifyFleetInventoryPieChart("chartColorBarsTP","chartToolTip","categoryNameTP",LanguageCode), "End-to-end Telephones pie workflow failed (verifyFleetInventoryPieChart=false).");
	    softAssert.assertAll();
	}
	
	@Test(priority = 10, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS", "STAGE_LDK"})
	public final void verifyPrintersFleetInventory() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		WEXCustomerHomePage wepCustomerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
	    wepCustomerHomePage.scrollToElementsOfWEPCustomerHomePage("pcContent");
	    Assert.assertTrue(wepCustomerHomePage.waitForElementsOfWEPCustomerHomePage("categoryNamePrint"), "PCs category tile not found.");
	    wepCustomerHomePage.scrollToElementsOfWEPCustomerHomePage("categoryNamePrint");
	    wepCustomerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("categoryNamePrint");
	    sleeper(2000);
	    List<WebElement> slices = wepCustomerHomePage.getAllElementsofWEPCustomerHomePage("chartColorBarsPrint");
	    Assert.assertNotNull(slices, "Pie slices list is null.");
	    Assert.assertFalse(slices.isEmpty(), "No pie slices displayed for Printers.");
	    softAssert.assertTrue(wepCustomerHomePage.verifyFleetInventoryPieChart("chartColorBarsPrint","chartToolTip","categoryNamePrint",LanguageCode), "End-to-end Printers pie workflow failed (verifyFleetInventoryPieChart=false).");
	    softAssert.assertAll();
	}
	
	@Test(priority = 11, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS", "STAGE_LDK" })
	public final void verifyhelpandsupportsection() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		WEXHelpAndSupportPage helpAndSupportPage = new WEXHelpAndSupportPage(PreDefinedActions.getDriver())
				.getInstance();
		SoftAssert softAssert = new SoftAssert();
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("HelpAndSupportTab");
		waitForPageLoaded();
		LOGGER.info("Redirected to help and support page");

		// Validation for the getting started
		
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("GettingStartedLabel"),
				"Getting Started Title is incorrect");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("GettingStartedLabel");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load
		String expectedUrl = getEnvironmentSpecificData(System.getProperty("environment"),"WXP_GETTING_STARTED_GUIDE").replace("/docs/", "/docs/" + LanguageCode + "/");
		String URL = helpAndSupportPage.getUrlOfCurrentPage();
		softAssert.assertTrue(
				(helpAndSupportPage.getUrlOfCurrentPage().contains(expectedUrl)),
				"URL for Getting Started  is not matching");
		helpAndSupportPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Getting Started Tile completed.");

		// Validation for software download tile
		
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("SoftwareDownloadLabel"),
				"Software download Title is incorrect");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("SoftwareDownloadLabel");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load
		String expectedSoftwareDownloadUrl = getEnvironmentSpecificData(System.getProperty("environment"),"WXP_SOFTWARE_DOWNLOAD") + "/"
				+ LanguageCode;
		softAssert.assertTrue(
				(expectedSoftwareDownloadUrl.equals(helpAndSupportPage.getUrlOfCurrentPage())),
				"URL for software download link is not matching");
		helpAndSupportPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Software Download Tile completed.");

		// Validation for knowledge base tile
		
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("KnowledgeBaseLabel"),
				"Knowledge Base Title is incorrect");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("KnowledgeBaseLabel");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load
		softAssert.assertTrue(
				(helpAndSupportPage.getUrlOfCurrentPage().contains( getEnvironmentSpecificData(System.getProperty("environment"),"WXP_KNOWLEDGE_BASE"))),"URL for Knowledge Base  is not matching");
		helpAndSupportPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Getting Started Tile completed.");

		// Validation for software requirement tile
	 
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("SystemRequirementsLabel"),
				"System Requirements Title is incorrect");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("SystemRequirementsLabel");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load
		String expectedSystemRequirementUrl =  getEnvironmentSpecificData(System.getProperty("environment"),"WXP_SYSTEM_REQUIREMENTS") + "/" + LanguageCode;
		softAssert.assertTrue(
				expectedSystemRequirementUrl.equals(helpAndSupportPage.getUrlOfCurrentPage()),
				"URL for System Requirements link is not matching");
		helpAndSupportPage.switchBackToPreviousTab();
		LOGGER.info("Validation of System Requirements Tile completed.");

		// Legal Information Tile
		// Privacy link
		helpAndSupportPage.scrollTillViewWEXHelpAndSupportPage("PrivacyLink");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("PrivacyLink"),
				"Privacy link is not present on Help and Support Page");
		helpAndSupportPage.waitForElementsOfWEXHelpAndSupportPage("PrivacyLink");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("PrivacyLink");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded(); // Url takes time to load
		softAssert.assertTrue(helpAndSupportPage.getUrlOfCurrentPage().contains(getEnvironmentSpecificData(System.getProperty("environment"),"WXP_PRIVACY_LINK")),"Privacy Title matching failed");
		helpAndSupportPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Privacy completed.");

		// Terms and Condition link
		helpAndSupportPage.scrollTillViewWEXHelpAndSupportPage("TermsandConditionsLink");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("TermsandConditionsLink"),
				"Terms and condition link is not present on Help and Support Page");
		helpAndSupportPage.waitForElementsOfWEXHelpAndSupportPage("TermsandConditionsLink");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("TermsandConditionsLink");
		helpAndSupportPage.switchToDifferentTab();
		sleeper(2000);// Url takes time to load
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("printButton"),
				"Print Button not present");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("HpWEXLogo"),
				"HP WEX con not present");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("HPheaderOn"),
				"HP WEX header is not present");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("userProfileIcon"),
				"User profile icon not present");
		waitForPageLoaded();
		String ConstantURL_TermsAndConditions = getEnvironmentSpecificData(System.getProperty("environment"),"WXP_TERMS_AND_CONDITIONS") + "/" + LanguageCode;
		softAssert.assertTrue(ConstantURL_TermsAndConditions.equals(helpAndSupportPage.getUrlOfCurrentPage()),
						"Terms and Condition URL not matching");
		helpAndSupportPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Terms and Condition completed.");

		// Service Level Agreement
		helpAndSupportPage.scrollTillViewWEXHelpAndSupportPage("ServiceLevelAgreementLink");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("ServiceLevelAgreementLink"),
				"Terms and condition link is not present on Help and Support Page");
		helpAndSupportPage.waitForElementsOfWEXHelpAndSupportPage("ServiceLevelAgreementLink");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("ServiceLevelAgreementLink");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("printButton"),
				"Print Button not present");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("HpWEXLogo"),
				"HP icon not present");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("SLAHeader"),
				"HP Daas not present");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("userProfileIcon"),
				"User profile icon not present");
		softAssert
				.assertTrue(
						(getEnvironmentSpecificData(System.getProperty("environment"),"WXP_SLA") + "/" + LanguageCode)
								.equals(helpAndSupportPage.getUrlOfCurrentPage()),
						"Service Level Agreement URL not matching");
		helpAndSupportPage.switchBackToPreviousTab();
		waitForPageLoaded();
		LOGGER.info("Validation of Service Level Agreement completed.");

		// Use Of Cookies
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("UseofCookiesLink"),
				"Use of cookies is not present on Help and Support Page");
		helpAndSupportPage.waitForElementsOfWEXHelpAndSupportPage("UseofCookiesLink");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("UseofCookiesLink");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load
		softAssert.assertTrue(
				helpAndSupportPage.getUrlOfCurrentPage().contains(
						helpAndSupportPage.getTextLanguage(LanguageCode, "daas_ui", "welcome.privacy").toLowerCase()),
				"Use of Cookies Title matching failed");
		helpAndSupportPage.switchBackToPreviousTab();
		softAssert.assertAll();
		LOGGER.info("Validation of Use of Cookies completed.");

		LOGGER.info("Validation of Looking for something new section on help and support page completed");
	}
}

