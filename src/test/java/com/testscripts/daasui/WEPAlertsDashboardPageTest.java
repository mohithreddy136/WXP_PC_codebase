package com.testscripts.daasui;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.WEPAlertsDashboardPage;

public class WEPAlertsDashboardPageTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXLogTest.class);

	/**
	 * This test case is to verify Element in Active Alert tab
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "REGRESSION_FRAMEWORKCORE", "PRODUCTION_FRAMEWORKCORE" }, description = "TestCaseID:C51577858", enabled = false)
	public final void verifyElementsOnActiveAlertsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		alertpage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		sleeper(3000);
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),
					"No Alert Message not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertMessage"),getTextLanguage(LanguageCode, "daas_ui", "no_active_alerts.title"),"No Alert Message text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertSubText"),
					"No Alert Message subtext not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertSubText"),getTextLanguage(LanguageCode, "daas_ui", "no_active_alerts.subtitle")," sub Alert Message text is  not match");
		} else {
			alertpage.clickOnElementsOfAlertPage("columnSettingIcon");
			if (alertpage.verifyElementIsClickableOfAlertPage("resetToDefaultButton")) {
				alertpage.clickOnElementsOfAlertPage("resetToDefaultButton");
			}
			alertpage.clickOnElementsOfAlertPage("saveButton");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("breadcrumbsAlertHeader"),
					"BreadcrumbsAlertHeader is not present on Active Alert Page");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("breadcrumbsAlertHeader"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.appbar.header.title"),"Alert breadcrumb text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("onboardingButton"),
					"Onboarding Button is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("helpButton"),
					"Help Button is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("notificationButton"),
					"Notification Button is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("profileButton"),
					"Profile Button is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("searchInput"),
					"Search Input is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("columnSettingIcon"),
					"Column Setting Icon is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("severityColumn"),
					"Severity Column is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("alertColumn"),
					"Alert Column is not present on Active Alert Page");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("alertColumn"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.grid.column.title"),"Alert column text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("createdColumn"),
					"created Column is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("updatedColumn"),
					"updated Column is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("deviceImpactedColumn"),
					"Device Impacted Column is not present on Active Alert Page");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("deviceImpactedColumn"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.grid.column.devicesImpacted"),"Device impacted column text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("fleetColumn"),
					"Fleet Column is not present on Active Alert Page");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("fleetColumn"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.grid.column.fleetPercentage"),"fleet column text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("currentPageNumber"),
					"Current Page Number is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("previousButton"),
					"Previous Button is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("nextButton"),
					"Next Button is not present on Active Alert Page");
			String alertname = alertpage.getTextOfAlertPage("ColumnData");
			alertpage.clickOnElementsOfAlertPage("ColumnData");
			waitForPageLoaded();
			
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("breadcrumbsAlertTitle"),
					"bread crumb title is not present on Active Alert Page");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("alertImpactedDevicesText"),getTextLanguage(LanguageCode, "daas_ui", "applications.crashesandfreezes.legends.2"),"Impacted devices text is  not match");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("alertFleetText"),getTextLanguage(LanguageCode, "daas_ui", "wex.fleet.mgmt.ra.percentage_of_fleet"),"Fleet text is  not match");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("remediationGuide"),getTextLanguage(LanguageCode, "daas_ui", "wex.widget.recommended.actions.remediation.guide"),"remediation text is  not match");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("viewMoreButton"),getTextLanguage(LanguageCode, "daas_ui", "wex.widget.recommended.actions.view.more"),"view more text is  not match");
			alertpage.clickOnElementsOfAlertPage("exportButton");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("exportButton"),getTextLanguage(LanguageCode, "daas_ui", "assets.export.btn_export"),"Export button text is  not match");
			sleeper(2000);
			if (alertname.contains(CommonVariables.Bios_Alert)) {
				softAssert.assertEquals(alertpage.getTextOfAlertPage("toastNotificationExport"),
						getTextLanguage(LanguageCode, "daas_ui", "assets.export.export_in_progress"),
						"Export notification text is  not match");
			} else {
				softAssert.assertEquals(alertpage.getTextOfAlertPage("toastNotificationExport"),
						getTextLanguage(LanguageCode, "daas_ui", "device.details.export.device.data.success"),
						"Export notification text is  not match");
			}
			softAssert.assertAll();
		}
	}

	/**
	 * This test case is to verify ColumnSetting popup in Active Alert tab
	 * @throws Exception
	 */
	@Test(priority = 2, groups = { "REGRESSION_FRAMEWORKCORE"}, description = "TestCaseID:C51577858", enabled = false)
	public final void verifyColumnSettingPopup() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		alertpage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		sleeper(3000);
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),
					"No Alert Message not present");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertSubText"),
					"No Alert Message subtext not present");
		} else {
			alertpage.clickOnElementsOfAlertPage("columnSettingIcon");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("popupHeader"),
					"Popup Header is not present on Column Setting Popup");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("popupHeader"),getTextLanguage(LanguageCode, "daas_ui", "table.configuration.columnOptions"),"Popup header  text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("optionLabel"),
					"option Label is not present on Column Setting Popup");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("optionCheckbox"),
					"Option Checkbox is not present on Column Setting Popup");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("resetToDefaultButton"),
					"Reset To Default Button is not present on Column Setting Popup");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("resetToDefaultButton"),getTextLanguage(LanguageCode, "daas_ui", "table.configuration.reset"),"reset default text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("saveButton"),
					"Save Button is not present on Column Setting Popup");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("saveButton"),getTextLanguage(LanguageCode, "daas_ui", "table.configuration.save"),"save text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("cancelButton"),
					"Cancel Button is not present on Column Setting Popup");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("cancelButton"),getTextLanguage(LanguageCode, "daas_ui", "table.configuration.cancel"),"cancel text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("columnUpButton"),
					"Column Up Button is not present on Column Setting Popup");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("columnDownButton"),
					"Column down Button is not present on Column Setting Popup");
		}
		softAssert.assertAll();
	}

	/**
	 * This test case is to verify ColumnSetting functionality in Active Alert tab
	 * @throws Exception
	 */
	@Test(priority = 3, groups = { "REGRESSION_FRAMEWORKCORE"}, description = "TestCaseID:C51577858", enabled = false)
	public final void verifyColumnSettingFunctionality() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		alertpage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		sleeper(3000);
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),
					"No Alert Message not present");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertSubText"),
					"No Alert Message subtext not present");
		} else {
			alertpage.clickOnElementsOfAlertPage("columnSettingIcon");
			alertpage.clickOnElementsOfAlertPage("optionLabel2");
			alertpage.clickOnElementsOfAlertPage("optionLabel3");
			alertpage.clickOnElementsOfAlertPage("optionLabel4");
			alertpage.clickOnElementsOfAlertPage("optionLabel5");
			alertpage.clickOnElementsOfAlertPage("optionLabel6");
			alertpage.clickOnElementsOfAlertPage("saveButton");
			Thread.sleep(2000);
			alertpage.verifyElementIsinvisibile("severityColumn");
			alertpage.verifyElementIsinvisibile("createdColumn");
			alertpage.verifyElementIsinvisibile("updatedColumn");
			alertpage.verifyElementIsinvisibile("deviceImpactedColumn");
			alertpage.verifyElementIsinvisibile("fleetColumn");

			alertpage.clickOnElementsOfAlertPage("columnSettingIcon");
			alertpage.clickOnElementsOfAlertPage("optionLabel2");
			alertpage.clickOnElementsOfAlertPage("saveButton");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("severityColumn"),
					"Severity Column is not present on Active Alert Page");
			Thread.sleep(2000);

			alertpage.clickOnElementsOfAlertPage("columnSettingIcon");
			alertpage.clickOnElementsOfAlertPage("optionLabel2");
			alertpage.clickOnElementsOfAlertPage("saveButton");
			alertpage.clickOnElementsOfAlertPage("columnSettingIcon");
			Thread.sleep(2000);
			alertpage.clickOnElementsOfAlertPage("resetToDefaultButton");
			alertpage.clickOnElementsOfAlertPage("saveButton");
			Thread.sleep(2000);
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("severityColumn"),
					"Severity Column is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("createdColumn"),
					"Severity Column is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("updatedColumn"),
					"Severity Column is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("deviceImpactedColumn"),
					"device Impacted Column is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("fleetColumn"),
					"fleet Column is not present on Active Alert Page");
		}
		softAssert.assertAll();
	}

	/**
	 * This test case is to verify Search in Active Alert tab 
	 * @throws Exception
	 */
	@Test(priority = 4, groups = { "REGRESSION_FRAMEWORKCORE", "PRODUCTION_FRAMEWORKCORE" }, description = "TestCaseID:C51600503", enabled = false)
	public final void verifySearchFunctionality() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		alertpage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		sleeper(3000);
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),
					"No Alert Message not present");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertSubText"),
					"No Alert Message subtext not present");
		} else {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("searchInput"),
					"Search Input is not present on Active Alert Page");
			alertpage.clickOnElementsOfAlertPage("searchInput");
			String input = alertpage.getTextOfAlertPage("alertTitleDescription");
			alertpage.enterTextOfAlertPage("searchInput", input);
			alertpage.clickOnElementsOfAlertPage("searchButton");
			sleeper(3000);
			List<WebElement> records = alertpage.getllAllElementsVisibleofAlertPage("recordsInTable");
			softAssert.assertEquals(records.size(), 1, "search not working correctly");
			softAssert.assertEquals(input, alertpage.getTextOfAlertPage("alertTitleDescription"), "invalidsearch");
			alertpage.enterTextOfAlertPage("searchInput", "invalidTextEntered#");
			alertpage.clickOnElementsOfAlertPage("searchButton");
			sleeper(3000);
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noRecordText"),"No result not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noRecordText"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"),"no result text is  not match");
		}
		softAssert.assertAll();
	}

	/**
	 * This test case is to verify Alerts Data in Active Alert tab and Active Alert Details tab
	 * @throws Exception
	 */
	@Test(priority = 5,  groups = { "REGRESSION_FRAMEWORKCORE"}, description = "TestCaseID:C53190216",enabled = false)
	public final void verifyActiveAlertData() throws Exception {
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		alertpage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		sleeper(5000);
		Assert.assertTrue(alertpage.validateActiveAlertPageData());
	}

	/**
	 * This test case is to verify Alerts Data in Home tab and Active Alert Details tab
	 * @throws Exception
	 */
	@Test(priority = 6,  groups = { "REGRESSION_FRAMEWORKCORE"}, description = "TestCaseID:C51600382", enabled = false)
	public final void verifyActiveAlertDataFromHomePage() throws Exception {
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		Assert.assertTrue(alertpage.validateActiveAlertDataFromHomePage());
	}

	/**
	 * This test case is to verify Alerts Data in Notification-Alert tab and Active Alert Details tab
	 * @throws Exception
	 */
	@Test(priority = 7, groups = { "REGRESSION_FRAMEWORKCORE", "PRODUCTION_FRAMEWORKCORE" }, description = "TestCaseID:C52584914",enabled = false)
	public final void verifyAlertInNotificationCenter() throws Exception {
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		alertpage.companyView(CommonVariables.CUSTOMER_ALERTS, CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		sleeper(5000);
		waitForPageLoaded();
		Assert.assertTrue(alertpage.validateAlertTabInNotificationCenter());
	}

	/**
	 * This test case is to verify Bulk Actions Bell Icon in Notification tab
	 * @throws Exception
	 */
	@Test(priority = 8, groups = { "REGRESSION_FRAMEWORKCORE"}, description = "TestCaseID:C52584914", enabled = false)
	public final void verifyBulkActionsinBellicon() throws Exception {
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		SoftAssert softAssert = new SoftAssert();
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("notificationButton"),
				"bell Icon is not present on Home  Page");
		alertpage.clickOnElementsOfAlertPage("notificationButton");
		alertpage.clickOnElementsOfAlertPage("notificationThreeDots");
		softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("markAllAsRead"),
				"mark all as read  is not present on notification center");
		softAssert.assertEquals(alertpage.getTextOfAlertPage("markAllAsRead"),getTextLanguage(LanguageCode, "daas_ui", "global.mark_all_as_read"),"marl all as read  text is  not match");
		softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("dismissAll"),
				"dismissall  is not present on notification center");
		softAssert.assertEquals(alertpage.getTextOfAlertPage("dismissAll"),getTextLanguage(LanguageCode, "daas_ui", "global.dismiss_all"),"Dismiss all  text is  not match");
		softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("communicationPrefernces"),
				"communication preferences is not present on notification bell");
		softAssert.assertEquals(alertpage.getTextOfAlertPage("communicationPrefernces"),getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences"),"communication preference text is  not match");
		alertpage.actionClickOfAlertPage("communicationPrefernces");
		waitForPageLoaded();
		sleeper(5000);
		softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("alertNotificationHeader"),
				" alert header is not present on commnunication preference");
		softAssert.assertEquals(alertpage.getTextOfAlertPage("alertNotificationHeader"),getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.alert_notify"),"Alert notification header text is  not match");
		softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("alertPara"),
				" alert Para is not present on commnunication preference");
		softAssert.assertEquals(alertpage.getTextOfAlertPage("alertPara"),getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.alert_notify.desc"),"Alert descrption text is  not match");
		sleeper(2000);
		softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("email"),
				"email is not present on commnunication preference");
		softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("product"),
				"Product is not present on commnunication preference");
		softAssert.assertTrue(alertpage.verifyElementIsClickableOfAlertPage("emailToggle"),
				"email toggle is not present on commnunication preference");
		softAssert.assertTrue(alertpage.verifyElementIsClickableOfAlertPage("productToggle"),
				"Product toggle is not present on commnunication preference");
		softAssert.assertAll();
	}
	
	@DataProvider
	public Object[][] loginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "RESELLER_PLUS_SERVICE_SPECIALIST_EMAIL";  
		data[0][1] = "RESELLER_PLUS_SERVICE_SPECIALIST_PASSWORD";
		data[1][0] = "RESELLER_PLUS_ADMIN_EMAIL"; 
		data[1][1] = "RESELLER_PLUS_ADMIN_PASSWORD";
		return data;
	}
	
	@Test(priority = 9,  groups = {"PRODUCTION_FRAMEWORKCORE" }, description = "TestCaseID:C57447539 , C57447534", dataProvider = "loginData",enabled= false)
	public final void verifyPartnerlogin_ActiveAlerts(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login(username, password);
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		if(alertpage.verifyElementsOfAlertPage("popupCloseBtn"))
		{
			alertpage.actionClickOfAlertPage("popupCloseBtn");
		}
		leftSideMenuVerification();
		alertpage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME"),CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS); 
		leftSideMenuVerification();
		waitForPageLoaded();
		sleeper(3000);
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),
					"No Alert Message not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertMessage"),getTextLanguage(LanguageCode, "daas_ui", "no_active_alerts.title"),"No Alert Message text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertSubText"),
					"No Alert Message subtext not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertSubText"),getTextLanguage(LanguageCode, "daas_ui", "no_active_alerts.subtitle")," sub Alert Message text is  not match");
		} else {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("breadcrumbsAlertHeader"),
					"BreadcrumbsAlertHeader is not present on Active Alert Page");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("breadcrumbsAlertHeader"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.appbar.header.title"),"Alert breadcrumb text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("helpButton"),
					"Help Button is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("notificationButton"),
					"Notification Button is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("profileButton"),
					"Profile Button is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("searchInput"),
					"Search Input is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("columnSettingIcon"),
					"Column Setting Icon is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("severityColumn"),
					"Severity Column is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("alertColumn"),
					"Alert Column is not present on Active Alert Page");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("alertColumn"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.grid.column.title"),"Alert column text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("createdColumn"),
					"created Column is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("updatedColumn"),
					"updated Column is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("deviceImpactedColumn"),
					"Device Impacted Column is not present on Active Alert Page");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("deviceImpactedColumn"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.grid.column.devicesImpacted"),"Device impacted column text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("fleetColumn"),
					"Fleet Column is not present on Active Alert Page");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("fleetColumn"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.grid.column.fleetPercentage"),"fleet column text is  not match");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("currentPageNumber"),
					"Current Page Number is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("previousButton"),
					"Previous Button is not present on Active Alert Page");
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("nextButton"),
					"Next Button is not present on Active Alert Page");
			String alertname = alertpage.getTextOfAlertPage("ColumnData");
			alertpage.clickOnElementsOfAlertPage("ColumnData");
			waitForPageLoaded();
			
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("breadcrumbsAlertTitle"),
					"bread crumb title is not present on Active Alert Page");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("alertImpactedDevicesText"),getTextLanguage(LanguageCode, "daas_ui", "applications.crashesandfreezes.legends.2"),"Impacted devices text is  not match");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("alertFleetText"),getTextLanguage(LanguageCode, "daas_ui", "wex.fleet.mgmt.ra.percentage_of_fleet"),"Fleet text is  not match");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("remediationGuide"),getTextLanguage(LanguageCode, "daas_ui", "wex.widget.recommended.actions.remediation.guide"),"remediation text is  not match");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("viewMoreButton"),getTextLanguage(LanguageCode, "daas_ui", "wex.widget.recommended.actions.view.more"),"view more text is  not match");
			alertpage.clickOnElementsOfAlertPage("exportButton");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("exportButton"),getTextLanguage(LanguageCode, "daas_ui", "assets.export.btn_export"),"Export button text is  not match");
			sleeper(2000);
			if (alertname.contains(CommonVariables.Bios_Alert)) {
				softAssert.assertEquals(alertpage.getTextOfAlertPage("toastNotificationExport"),
						getTextLanguage(LanguageCode, "daas_ui", "assets.export.export_in_progress"),
						"Export notification text is  not match");
			} else {
				softAssert.assertEquals(alertpage.getTextOfAlertPage("toastNotificationExport"),
						getTextLanguage(LanguageCode, "daas_ui", "device.details.export.device.data.success"),
						"Export notification text is  not match");
			}
			softAssert.assertAll();
		}
	}
	
	@Test(priority = 10,  groups = {"PRODUCTION_FRAMEWORKCORE" }, description = "TestCaseID:C57447539 , C57447534" , enabled= false)
	public final void verifyPartnerlogin_HomeActiveAlerts() throws Exception {
		login("RESELLER_PLUS_SALES_SPECIALIST_EMAIL", "RESELLER_PLUS_SALES_SPECIALIST_PASSWORD");
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		if(alertpage.verifyElementsOfAlertPage("popupCloseBtn"))
		{
			alertpage.actionClickOfAlertPage("popupCloseBtn");
		}
		leftSideMenuVerification();
		alertpage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME"),CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS); 
		leftSideMenuVerification();
		waitForPageLoaded();
		alertpage.actionClickOfAlertPage("homeButton");
		waitForPageLoaded();
		Assert.assertTrue(alertpage.validateActiveAlertDataFromHomePage());
	}
	
	@Test(priority = 11, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS", "STAGE_LDK"})
	public final void verifyActiveAlertDataFromHomePageLDK() throws Exception {
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		 String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
	    Assert.assertTrue(alertpage.validateActiveAlertDataFromHomePage());
		alertpage.clickOnElementsOfAlertPage("viewFullOfList");
		Assert.assertTrue(alertpage.validateActiveAlertPageData());
	}
}
