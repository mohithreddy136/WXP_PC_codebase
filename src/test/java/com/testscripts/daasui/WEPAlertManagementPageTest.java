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
import com.daasui.pages.WEPAlertManagementPage;
import com.daasui.pages.WEXSignUpPage;

public class WEPAlertManagementPageTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEPAlertManagementPageTest.class);
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");
	public static String UserEmail = getEnvironmentSpecificData(System.getProperty("environment"), "WEP_USER_EMAIL_SEARCH");

	/**
	 * This test case is to verify Alert management tab in IT admin Login
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "REGRESSION_FRAMEWORKCORE",
			"PRODUCTION_FRAMEWORKCORE" }, description = "TestCase ID :C51620788", enabled = true)
	public final void verifyWEPAlertManagementPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertManagementPage WEPAlertManagementPage = new WEPAlertManagementPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		WEPAlertManagementPage.companyView(CommonVariables.CUSTOMER_ALERTS, CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);

		waitForPageLoaded();
		if (!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("predefinedSelectAlertCheckbox")) {
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList"),
					"No Alert Message not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("emptyAlertList"),
					getTextLanguage(LanguageCode, "daas_ui", "list.no_items"));
			LOGGER.info("Predefined alerts are not present");
		} else {
			LOGGER.info("Redirected to the alert management page.");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertManagementPage"),
					"Alerts management page is not match.");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("alertManagementPage"),
					getTextLanguage(LanguageCode, "daas_ui", "global_alertsManagement"));
			LOGGER.info("Verified clear and Reset functionality.");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("createButton"),
					"Create button is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("columnSettingIcon"),
					"ColumnSetting button Search is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("TitleHeader"),
					"Title  Column is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("TitleHeaderSearch"),
					"Title  Column Search is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("statusheader"),
					"Status Column is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("statusDropdown"),
					"Status Column dropdown is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("dataPointsHeader"),
					"Data Point Column is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("dataPointDropdown"),
					"Data Point Column dropdown is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("configurationHeader"),
					"Configuration Column is not present on Alert mgmt Page");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("configurationDropdown"),
					"Configuration Column dropdown is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertTypeHeader"),
					"Alert type Column is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertTypeDropdown"),
					"Alert type Column dropdown is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("severityheader"),
					"Severity type Column is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("severityDropdown"),
					"Severity type Column dropdown is not present on Alert mgmt Page");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("monitoringScopeHeader"),
					"Monitoring scope Column is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("createdByHeader"),
					"CreatedBy Column is not present on Alert mgmt Page");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("createdByHeaderSearch"),
					"CreatedBy Column search is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("lastEditedHeader"),
					"Last Edited Column is not present on Alert mgmt Page");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("lastEditedHeaderSearch"),
					"Last Edited Column search is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("lastTriggeredHeader"),
					"Last Triggered Column is not present on Alert mgmt Page");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("lastTriggeredHeaderSearch"),
					"Last Triggered Column search is not present on Alert mgmt Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("currentPageNumber"),
					"Current Page Number is not present on Active Alert Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("previousButton"),
					"Previous Button is not present on Active Alert Page");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("nextButton"),
					"Next Button is not present on Active Alert Page");
		}
		LOGGER.info("Alert management page columns are verified and all the available controls successfully");
		softAssert.assertAll();
	}

	/**
	 * This test case is to verify Alert management tab in IT admin Login Enable and
	 * Disable Functionality
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2, groups = { "REGRESSION_FRAMEWORKCORE",
			"PRODUCTION_FRAMEWORKCORE" }, description = "TestCase ID :C51636401,C52584514 ", enabled = true)
	public final void verifyEnableAlert() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertManagementPage WEPAlertManagementPage = new WEPAlertManagementPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		WEPAlertManagementPage.companyView(CommonVariables.CUSTOMER_ALERTS, CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);

		if (!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList")) {
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("predefinedSelectAlertCheckbox"),
					"CheckBox not selected");
			WEPAlertManagementPage.clickByJavaScriptOnWEPAlertManagementPage("predefinedSelectAlertCheckbox");
			LOGGER.info("Selected the 1st checkbox");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("enableAlert"),
					"Enable alert button is not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("enableAlert"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.enable"));
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("disableAlert"),
					"Disable alert button is not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("disableAlert"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.disable"));
			WEPAlertManagementPage.clickByJavaScriptOnWEPAlertManagementPage("predefinedSelectAlertCheckbox");
			
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("disableOption");
			if (WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("predefinedSelectAlertCheckbox")) {
				WEPAlertManagementPage.clickByJavaScriptOnWEPAlertManagementPage("predefinedSelectAlertCheckbox");
				softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertStatus"),
						"Alert status is not present");
				WEPAlertManagementPage.clickOnElementsOfWEPAlertManagementPage("enableAlert");
				LOGGER.info("Clicked on enable Alert");
				WEPAlertManagementPage.waitForElementsOfWEPAlertManagementPageDynamic("editToastMessage", 2000);
				softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("editToastMessage"),
						"Enable alert toast messages is not present");
				sleeper(2000);
				softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("editToastMessage"),
						getTextLanguage(LanguageCode, "daas_ui", "single.enable.alert.success.toast.message"));
				LOGGER.info("Enable Alert functionality verified successfully");

			} else {
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("enableOption");
				WEPAlertManagementPage.findEligibleAlertForDisabling();
				softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("editToastMessage"),
						"Disable alert toast messages is not present");
				softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("editToastMessage"),
						getTextLanguage(LanguageCode, "daas_ui", "single.disabled.alert.success.toast.message"));
				LOGGER.info("Disable Alert functionality verified successfully");
			}

		} else {
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList"),
					"No Alert Message not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("emptyAlertList"),
					getTextLanguage(LanguageCode, "daas_ui", "list.no_items"));
			LOGGER.info("Predefined alerts are not present");
		}
		LOGGER.info("Enable/Disable Alert functinality verified successfully");
		softAssert.assertAll();
	}

	/**
	 * This test case is to verify Alert management tab in IT admin Login Delete
	 * alert functionality
	 * 
	 * @throws Exception
	 */
	@Test(priority = 3, groups = {
			"REGRESSION_FRAMEWORKCORE", }, description = "TestCase ID :C51635119", enabled = false)
	public final void verifyDeleteAlert() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertManagementPage WEPAlertManagementPage = new WEPAlertManagementPage(PreDefinedActions.getDriver())
				.getInstance();
		WEPAlertManagementPage.companyView(CommonVariables.CUSTOMER_ALERTS, CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);
		waitForPageLoaded();

		if (!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("predefinedSelectAlertCheckbox")) {
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList"),
					"No Alert Message not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("emptyAlertList"),
					getTextLanguage(LanguageCode, "daas_ui", "list.no_items"));
			LOGGER.info("Predefined alerts are not present");
		} else {
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("predefinedSelectAlertCheckbox"),
					"CheckBox not selected");
			WEPAlertManagementPage.clickByJavaScriptOnWEPAlertManagementPage("predefinedSelectAlertCheckbox");
			LOGGER.info("Selected the 1st checkbox");

			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("deleteAlert"),
					"Delete alert option is not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("deleteAlert"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.delete"));
			WEPAlertManagementPage.clickByJavaScriptOnWEPAlertManagementPage("deleteAlert");
			LOGGER.info("Clicked on Delete alert");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("deleteConfirmation"),
					"Delete Confirmation text is not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("deleteConfirmation"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.delete_confirmation"));
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("deleteText"),
					"Delete Text is not Present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("deleteText"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.delete.popup.sub.heading"));

			WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("deleteButton");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("cancelButton"),
					"Cancel button is not present");
			WEPAlertManagementPage.clickByJavaScriptOnWEPAlertManagementPage("deleteButton");
			LOGGER.info("Clicked on Delete Button");
			sleeper(2000);
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("toastMessage"),
					"Toast messages is not present");
			sleeper(2000);
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("deleteAlertToastMessageText"),
					"Delete alert toast messages is not present");
			softAssert.assertEquals(
					WEPAlertManagementPage.getTextOfWEPAlertManagementPage("deleteAlertToastMessageText"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.delete.success.toast"));
			LOGGER.info("Alert Deletion functionality verified successfully");
		}
		softAssert.assertAll();

	}

	/**
	 * This test case is to verify Alert management tab in IT admin Login Edit alert
	 * functionality
	 * 
	 * @throws Exception
	 */
	@Test(priority = 4, groups = { "REGRESSION_FRAMEWORKCORE",
			"PRODUCTION_FRAMEWORKCORE" }, description = "TestCase ID :C51635119", enabled = true)
	public final void verifyEditAlert() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertManagementPage WEPAlertManagementPage = new WEPAlertManagementPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		WEPAlertManagementPage.companyView(CommonVariables.CUSTOMER_ALERTS, CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);

		waitForPageLoaded();
		if (!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("predefinedSelectAlertCheckbox")) {
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList"),
					"No Alert Message not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("emptyAlertList"),
					getTextLanguage(LanguageCode, "daas_ui", "list.no_items"));
			LOGGER.info("Predefined alerts are not present");
		} else {
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("predefinedSelectAlertCheckbox");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertClick"),
					"Predefined alerts are not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("alertClick");
			sleeper(2000);
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("editAlert"),
					"Edit alert text is not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("editAlert"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.edit.alert.heading"));
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("editAlertSubtitle"),
					"Edit alert sub text is not Present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("editAlertSubtitle"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.edit.screen.severity.description"));
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("editAlertSeverity"),
					"Severity label is not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("editAlertSeverity"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.severity.column"));
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("currentSeverity"),
					"severity is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("editAlertTitle"),
					"Alert Title is not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("editAlertTitle"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.title.column"));

			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("editAlertTitleInput"),
					"Alert Title Input is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("editAlertDescription"),
					"Description field title is not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("editAlertDescription"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.description.column"));
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("editAlertDescriptionInput"),
					"Description Input is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertWhenText"),
					"Alert when text is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertWhenText2"),
					"% of devices is not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("alertWhenText2"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.edit.alert.devices.text"));
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("editAlertThreshold"),
					"Threshold is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("editAlertCard"),
					"edit Alert Card is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("severityTag"),
					"Severity tag is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertCardTitle"),
					" Alert Card Title is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertCardDescription"),
					" Alert Card Description is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("percentageTag"),
					"Percentage tag is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("inProductNotificationLabel"),
					"Product notification label text is not match.");
			softAssert.assertEquals(
					WEPAlertManagementPage.getTextOfWEPAlertManagementPage("inProductNotificationLabel"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.in.product.notification"));
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("inProductNotificationDropdown"),
					"Product notification dropdown text is not match");
			WEPAlertManagementPage.mousehoverOnWEPAlertManagementPage("inProductNotificationDropdown");
			if (WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("clearDropdown")) {
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("clearDropdown");
			}
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("inProductNotificationDropdown");
			sleeper(2000);
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("itAdminSelect"),
					"IT admin is not visible on dropdown");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reportAdminSelect"),
					"Report admin is not visible on dropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("itAdminSelect");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("inProductNotificationDropdown");
			LOGGER.info("Verified the In Product Notification.");

			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emailNotificationaLabel"),
					"Email Notification label is not match.");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("emailNotificationaLabel"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.email.notification"));
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emailNotificationDropdown"),
					"Email Notification dropdown is not verified.");
			WEPAlertManagementPage.mousehoverOnWEPAlertManagementPage("emailNotificationDropdown");
			if (WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emailNotificationClearDropdown")) {
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("emailNotificationClearDropdown");
			}
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("emailNotificationDropdown");
			sleeper(2000);
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("itAdminSelect"),
					"It admin is not selected in email notification.");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reportAdminSelect"),
					"Report admin is not selected in email notification.");

			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("reportAdminSelect");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("emailNotificationDropdown");
			LOGGER.info("Verified the Email Notification.");

			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("saveEditAlert"),
					"edit Alert Save is not present");

			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("saveEditAlert");
			sleeper(2000);
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("editToastMessage"),
					"Edit alert toast messages is not present");
		}
		softAssert.assertAll();

	}

	@Test(priority = 5, groups = { "REGRESSION_FRAMEWORKCORE" }, description = "TestCase ID :C51635119", enabled = true)
	public final void verifyMouseHoverAlertOptions() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertManagementPage WEPAlertManagementPage = new WEPAlertManagementPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		WEPAlertManagementPage.companyView(CommonVariables.CUSTOMER_ALERTS, CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);

		if (!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("predefinedSelectAlertCheckbox")) {
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList"),
					"No Alert Message not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("emptyAlertList"),
					getTextLanguage(LanguageCode, "daas_ui", "list.no_items"));
			LOGGER.info("Predefined alerts are not present");
		} else {
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("configurationDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("standardOption");
			LOGGER.info("Clicked Standard configuration option");
			sleeper(2000);
			if(!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList")) {
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("enableOption");
				LOGGER.info("Clicked enable option");
				sleeper(2000);
				if(!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList")) {
					WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("mouseHoverAlertOption");
					LOGGER.info("Clicked MouseHoverAlertOption for standard configuration option with enable status");
					softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverDisable"),"Mouse hover disable option is not present for standard configuration option");
					softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverEdit"),"Mouse hover edit option is not present for standard configuration option");
					softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverDelete"),"Mouse hover delete option is present for standard configuration option");
					softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverEnable"),"Mouse hover enable option is present for standard configuration option");
				}
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("disableOption");
				LOGGER.info("Clicked enable option");
				sleeper(2000);
				if(!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList")) {
					WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("mouseHoverAlertOption");
					LOGGER.info("Clicked MouseHoverAlertOption for standard configuration option with disable status");
					softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverEnable"),"Mouse hover enable option is not present for standard config option");
					softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverEdit"),"Mouse hover edit option is not present for standard config option");
					softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverDelete"),"Mouse hover delete option is present for standard config option");
					softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverDisable"),"Mouse hover disable option is present for standard config option");
				}
			}
			
			WEPAlertManagementPage.clickByJavaScriptOnWEPAlertManagementPage("clearFilter");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("configurationDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("enhancedOption");
			LOGGER.info("Clicked enhanced configuration option");
			sleeper(3000);
			if(!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList")) {
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("enableOption");
				LOGGER.info("Clicked enable option");
				sleeper(2000);
				if(!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList")) {
					WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("mouseHoverAlertOption");
					LOGGER.info("Clicked MouseHoverAlertOption for enhanced configuration option with enable status");
					softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverDisable"),"Mouse hover disable option is not present for enhanced configuration option");
					softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverEdit"),"Mouse hover edit option is not present for enhanced configuration option");
					softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverDelete"),"Mouse hover delete option is not present for enhanced configuration option");
					softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverEnable"),"Mouse hover enable option is present for enhanced configuration option");
				}
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("disableOption");
				LOGGER.info("Clicked disable option");
				sleeper(2000);
				if(!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList")) {
					WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("mouseHoverAlertOption");
					LOGGER.info("Clicked MouseHoverAlertOption for enhanced configuration option with disable status");
					softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverEnable"),"Mouse hover enable option is not present for enhanced config option");
					softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverEdit"),"Mouse hover edit option is not present for enhanced config option");
					softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverDelete"),"Mouse hover delete option is not present for enhanced config option");
					softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("mousehoverDisable"),"Mouse hover disable option is present for enhanced config option");
				}
			}
		}
			softAssert.assertAll();
	}

	@Test(priority = 6, groups = { "REGRESSION_FRAMEWORKCORE" }, description = "TestCase ID :C51620788", enabled = true)
	public final void verifyMulticolumnOptions() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertManagementPage WEPAlertManagementPage = new WEPAlertManagementPage(PreDefinedActions.getDriver())
				.getInstance();

		waitForPageLoaded();
		WEPAlertManagementPage.companyView(CommonVariables.CUSTOMER_ALERTS, CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);
		if (!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("predefinedSelectAlertCheckbox")) {
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList"),
					"No Alert Message not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("emptyAlertList"),
					getTextLanguage(LanguageCode, "daas_ui", "list.no_items"));
			LOGGER.info("Predefined alerts are not present");
		} else {
			// title search
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("typeSearch"),
					"type search not present");
			WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("typeSearch", "invalidTextEntered#");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("searchClearButton");
			sleeper(3000);
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("noRecordText"),
					"No result not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("noRecordText"),
					getTextLanguage(LanguageCode, "daas_ui", "list.no_items"), "No items available is not match");
			WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("clearFilter");
			WEPAlertManagementPage.clickByJavaScriptOnWEPAlertManagementPage("clearFilter");
			waitForPageLoaded();

			// status column
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("statusDropdown"),
					"Status dropdown not there");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("enableOption"),
					"enable Status dropdown not there");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("disableOption"),
					" disable Status dropdown not there");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("draftOption"),
					" draft Status dropdown not there");
			String Option = WEPAlertManagementPage.getTextOfWEPAlertManagementPage("enableOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("enableOption");
			waitForPageLoaded();
			if (WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("statuscolumnData")) {
				WEPAlertManagementPage.waitForElementsOfAlertManagementPage("statuscolumnData");
				softAssert.assertTrue(
						WEPAlertManagementPage.verifyFilteredDataOnAlertManagementPage("statuscolumnData", Option),
						"Filtered data is incorrect for status column");
			}
			if (WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("clearFilter")) {
				WEPAlertManagementPage.clickByJavaScriptOnWEPAlertManagementPage("clearFilter");
			}

			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusheader");
			waitForPageLoaded();
			sleeper(3000);
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyDropdownOptionOrderOnAlertManagementPage("statuscolumnData"),
					"status field is not sorted");
			waitForPageLoaded();

			// data point column
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("dataPointDropdown"),
					"Data point dropdown not there");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("dataPointDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("dataPointDropdown");

			// Configuration
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("configurationDropdown"),
					"Configuration dropdown not there");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("configurationDropdown");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("enhancedOption"),
					"Enhanced option not there");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("standardOption"),
					" Standard option not there");
			Option = WEPAlertManagementPage.getTextOfWEPAlertManagementPage("enhancedOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("enhancedOption");
			waitForPageLoaded();
			sleeper(2000);
			if (WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("configurationColumnData")) {
				WEPAlertManagementPage.waitForElementsOfAlertManagementPage("configurationColumnData");
				softAssert.assertTrue(WEPAlertManagementPage.verifyFilteredDataOnAlertManagementPage(
						"configurationColumnData", Option), "Filtered data is incorrect for configuration column");
			}
			if (WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("clearFilter")) {
				WEPAlertManagementPage.clickByJavaScriptOnWEPAlertManagementPage("clearFilter");
			}

			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("configurationHeader");
			waitForPageLoaded();

			// Alert Type
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertTypeDropdown"),
					"Alert type dropdown not there");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("alertTypeDropdown");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("fleetOption"),
					"Fleet option not there");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("deviceOption"),
					"Device option not there");
			Option = WEPAlertManagementPage.getTextOfWEPAlertManagementPage("fleetOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");
			waitForPageLoaded();
			sleeper(2000);
			if (WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertTypeColumnData")) {
				softAssert.assertTrue(
						WEPAlertManagementPage.verifyFilteredDataOnAlertManagementPage("alertTypeColumnData", Option),
						"Filtered data is incorrect for alert column");
			}
			if (WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("clearFilter")) {
				WEPAlertManagementPage.clickByJavaScriptOnWEPAlertManagementPage("clearFilter");
			}

			// severity column
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("severitydropdown"),
					"Severity dropdown not there");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severitydropdown");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("severityCriticalOption"),
					"critical option dropdown not there");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("severityCriticalOption"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.column.critical.severity"));
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("severityHighOption"),
					"high option dropdown not there");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("severityHighOption"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.column.high.severity"));
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("severityMediumOption"),
					"medium option dropdown not there");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("severityMediumOption"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.column.medium.severity"));
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("severityLowOption"),
					"low option  dropdown not there");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("severityLowOption"),
					getTextLanguage(LanguageCode, "daas_ui", "alert_management.column.low.severity"));
			String SevrityOption = WEPAlertManagementPage.getTextOfWEPAlertManagementPage("severityHighOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityHighOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severitydropdown");
			waitForPageLoaded();
			sleeper(2000);
			if (WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("severitycoulmndropdown")) {
				WEPAlertManagementPage.waitForElementsOfAlertManagementPage("severitycoulmndropdown");
				softAssert.assertTrue(WEPAlertManagementPage.verifyFilteredDataOnAlertManagementPage(
						"severitycoulmndropdown", SevrityOption), "Filtered data is incorrect for severity column");
			}
			if (WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("clearFilter")) {
				WEPAlertManagementPage.clickByJavaScriptOnWEPAlertManagementPage("clearFilter");
			}
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityheader");
			waitForPageLoaded();

			// CreatedBy column
			WEPAlertManagementPage.scrollOnWEPAlertManagementPage("createdByHeaderSearch");
			if (WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("createdByColumnData")) {
				String text = WEPAlertManagementPage.getTextOfWEPAlertManagementPage("createdByColumnData");
				WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("createdByHeaderSearch", text);
				waitForPageLoaded();
				sleeper(3000);
				softAssert.assertTrue(
						WEPAlertManagementPage.verifyFilteredDataOnAlertManagementPage("createdByColumnData", text),
						"Filtered data is incorrect for Created By column");
				waitForPageLoaded();
			}

		}
		softAssert.assertAll();
	}

	/*
	 * Marked as disabled as its applicable partner alerts now and added the TC in
	 * Partner alerts page. Keeping it for future reference.
	 */

	@Test(priority = 7, groups = { "REGRESSION_FRAMEWORKCORE",
			"PRODUCTION_FRAMEWORKCORE" }, description = "TestCase ID :C51620788", enabled = true)
	public final void verifyAllalertsEnabledAndDisabledStatus() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertManagementPage WEPAlertManagementPage = new WEPAlertManagementPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		WEPAlertManagementPage.companyView(CommonVariables.CUSTOMER_ALERTS, CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);
		if (!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("predefinedSelectAlertCheckbox")) {
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList"),
					"No Alert Message not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("emptyAlertList"),
					getTextLanguage(LanguageCode, "daas_ui", "list.no_items"));
			LOGGER.info("Predefined alerts are not present");
		} else {
			int alertStatus = WEPAlertManagementPage
					.getSizeOfAllElementsVisibleOnWEPAlertManagementPage("allAlertStatus");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("selectAllCheckbox");
			if (WEPAlertManagementPage.verifyElementIsClickableOfAlertManagementPage("enableAlert")) {
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				String Option = WEPAlertManagementPage.getTextOfWEPAlertManagementPage("enableOption");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("enableAlert");
				// sleeper(1000);
				softAssert
						.assertEquals(
								WEPAlertManagementPage.getTextOfWEPAlertManagementPage("enableAlertToastMessageText"),
								getTextLanguage(LanguageCode, "daas_ui", "bulk.enabled.alert.success.toast.message")
										.replace("{selectedUser}", Integer.toString(alertStatus)),
								"Bulk enabled toast not matching");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("closeButton");
				sleeper(2000);
				softAssert.assertTrue(
						WEPAlertManagementPage.verifyFilteredDataOnAlertManagementPage("allAlertStatus", Option),
						"All data should be Enabled for status column");

				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("selectAllCheckbox");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				Option = WEPAlertManagementPage.getTextOfWEPAlertManagementPage("disableOption");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("disableAlert");
				sleeper(1000);
				softAssert
						.assertEquals(
								WEPAlertManagementPage.getTextOfWEPAlertManagementPage("disableAlertToastMessageText"),
								getTextLanguage(LanguageCode, "daas_ui", "bulk.disabled.alert.success.toast.message")
										.replace("{selectedUser}", Integer.toString(alertStatus)),
								"Bulk disabled toast not matching");
				sleeper(2000);
				softAssert.assertTrue(
						WEPAlertManagementPage.verifyFilteredDataOnAlertManagementPage("allAlertStatus", Option),
						"All data should be Disabled for status column");

				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("selectAllCheckbox");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("enableAlert");
			} else {
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				String Option = WEPAlertManagementPage.getTextOfWEPAlertManagementPage("disableOption");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("disableAlert");
				softAssert
						.assertEquals(
								WEPAlertManagementPage.getTextOfWEPAlertManagementPage("disableAlertToastMessageText"),
								getTextLanguage(LanguageCode, "daas_ui", "bulk.disabled.alert.success.toast.message")
										.replace("{selectedUser}", Integer.toString(alertStatus)),
								"Bulk disabled toast not matching");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("closeButton");
				sleeper(2000);
				softAssert.assertTrue(
						WEPAlertManagementPage.verifyFilteredDataOnAlertManagementPage("allAlertStatus", Option),
						"All data should be Disabled for status column");

				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("selectAllCheckbox");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				Option = WEPAlertManagementPage.getTextOfWEPAlertManagementPage("enableOption");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("statusDropdown");
				WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("enableAlert");
				softAssert
						.assertEquals(
								WEPAlertManagementPage.getTextOfWEPAlertManagementPage("enableAlertToastMessageText"),
								getTextLanguage(LanguageCode, "daas_ui", "bulk.enabled.alert.success.toast.message")
										.replace("{selectedUser}", Integer.toString(alertStatus)),
								"Bulk enabled toast not matching");
				sleeper(2000);
				softAssert.assertTrue(
						WEPAlertManagementPage.verifyFilteredDataOnAlertManagementPage("allAlertStatus", Option),
						"All data should be Enabled for status column");
			}

			softAssert.assertAll();

		}

	}

	@Test(priority = 8, groups = { "REGRESSION_FRAMEWORKCORE",
			"PRODUCTION_FRAMEWORKCORE" }, description = "TestCase ID :", enabled = true)
	public final void verifyNewAlertCreationFlow() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertManagementPage WEPAlertManagementPage = new WEPAlertManagementPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		WEPAlertManagementPage.companyView(CommonVariables.CUSTOMER_ALERTS, CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);

		waitForPageLoaded();
		if (!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("predefinedSelectAlertCheckbox")) {
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList"),
					"No Alert Message not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("emptyAlertList"),
					getTextLanguage(LanguageCode, "daas_ui", "list.no_items"));
			LOGGER.info("Predefined alerts are not present");
		} else {
			LOGGER.info("Redirected to the alert management page.");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("createButton");
			waitForPageLoaded();
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("createNewAlertTitle"),
					"Create New Alert Title is not present");
			LOGGER.info("Create New Alert Page is opened successfully.");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("createNewAlertSubtitle"),
					"Create New Alert Subtitle is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("progressBarText"),
					"Progress Bar Text is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("sectionTitle"),
					"Section Title is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("sectionDescription"),
					"Section Description is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("newAlertTypeHelperText"),
					"New AlertType Helper Text is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("newAlertPageCancelBtn"),
					"New AlertPage Cancel Button is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("newAlertPageNextBtn"),
					"New AlertPage Next Button is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("saveAsDraftBtn"),
					"SaveAsDraft button is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("newAlertTypeDropdown"),
					"New AlertType Dropdown is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("newAlertTypeDropdown");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("fleetOption"),
					"Fleet option is not present in dropdown");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("deviceOption"),
					"Device option is not present in dropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("dataPointToMonitorLabel"),
					"DataPoint To Monitor Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("dataPointToMonitorDropdown"),
					"DataPoint To Monitor Dropdown is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("dataPointToMonitorDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("appCrashesOption");
			waitForPageLoaded();
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("onThisAppLabel"),
					"On this App Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("onThisAppHelperText"),
					"On this App Helper Text is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("onThisAppDropdown"),
					"On this App Dropdown is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("onThisAppDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");

			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertDetailsTitle"),
					"AlertDetails Title Text is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertDetailsDescription"),
					"AlertDetails Description Text is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertTitleLabel"),
					"Alert Title Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertTitleHelperText"),
					"Alert Title Helper Text is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertTitleInput"),
					"Alert Title InputBox is not present");
			String alertTitle = "Automation Test Alert Title" + generateRandomNumber();
			WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("alertTitleInput", alertTitle);
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("severityLabel"),
					"severityLabel Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("newAlertSeverityDropdown"),
					"severityDropdown Dropdown is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("newAlertSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityCriticalOption");

			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("triggerConditionTitle"),
					"Trigger Condition Title is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("triggerConditionDescription"),
					"Trigger Condition Description is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("eventThresholdLabel"),
					"Event Threshold Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("eventThresholdHelperText"),
					"Event Threshold Helper Text is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("eventThresholdInput"),
					"Event Threshold InputBox is not present");
			WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("eventThresholdInput", "2");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("timeWindowLabel"),
					"Time Window Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("timeWindowDropdown"),
					"Time Window Dropdown is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("timeWindowDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("fleetThresholdLabel"),
					"Fleet Threshold Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("fleetThresholdHelperText"),
					"Fleet Threshold Helper Text is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("fleetThresholdInput"),
					"Fleet Threshold InputBox is not present");
			WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("fleetThresholdInput", "2");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("unitTypeLabel"),
					"UnitType Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("unitTypeDropdown"),
					"UnitType Dropdown is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("unitTypeDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("recoveryPeriodLabel"),
					"Recovery Period Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("recoveryPeriodHelperText"),
					"Recovery Period Helper Text is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("recoveryPeriodDropdown"),
					"Recovery Period Dropdown is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("recoveryPeriodDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");

			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationThresholdTitle"),
					"Escalation Threshold Title is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationThresholdDescription"),
					"Escalation Threshold Description is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityLabel"),
					"Escalation Severity Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityDropdown"),
					"Escalation Severity Dropdown is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationThresholdLabel"),
					"Escalation Threshold Label is not present");
			sleeper(3000);
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationThresholdHelperText"),
					"Escalation Threshold Helper text is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationThresholdInput"),
					"Escalation Threshold InputBox is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationUnitTypeLabel"),
					"Escalation Unit Type Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationUnitTypeDropdown"),
					"Escalation Unit Type Dropdown is not present");

			LOGGER.info("All elements verified on Alert Details section of New Alert Creation Page.");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("newAlertPageNextBtn");
			waitForPageLoaded();

			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("moniterAllDeviceLabel"),
					"MoniterAll Device Label is not present");
			LOGGER.info("Navigated to the Set monitoring scope Page.");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("moniterAllDeviceLabel");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("moniterSpecificDeviceLabel"),
					"Moniter Specific Device Label is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("moniterSpecificDeviceLabel");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("newGroupCreationHelperText"),
					"New Group Creation Helper Text is not present");
			waitForPageLoaded();

			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("viewSelectedGroupsLabel"),
					"View Selected Groups Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("specificGroupTableTitle"),
					"Specific Group Table Title is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage
							.verifyElementsOfWEPAlertManagementPage("specificGroupTableSelectAllCheckbox"),
					"Specific Group Table SelectAll Checkbox is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("specificGroupTableTitleSearch"),
					"Specific Group Table Title Search is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("specificGroupTableDataCheckbox"),
					"Specific Group Table Data Checkbox is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("specificGroupTableDataCheckbox");
			String selectedGroupName = WEPAlertManagementPage
					.getTextOfWEPAlertManagementPage("specificGroupTableDataName");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("selectedGroupCount"),
					"Selected Group Count is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("viewSelectedGroupsLabel");
			LOGGER.info("Validated Set monitoring scope section of New Alert Creation Page.");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("setScopePageNextBtn");
			waitForPageLoaded();
			sleeper(3000);

			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("notificationTitle"),
					"Notification Title is not present");
			LOGGER.info("Navigated to the Configure Notifications Page.");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("notificationTitleDescription"),
					"Notification Title Description is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("ProductNotificationLabel"),
					"InProduct Notification Label is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("ProductNotificationLabel");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("ProductNotificationDropdown");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emailNoficationLabel"),
					"Email Notification Label is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("emailNotificationDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("emailNotificationDropdown");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("teamsNotificationLabel"),
					"Teams Notification Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("teamsNotificationDropdown"),
					"Teams Notification Dropdown is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("repeatNotificationLabel"),
					"Repeat Notification Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("repeatNotificationHelperText"),
					"Repeat Notification Helper Text is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("repeatNotificationDropdown"),
					"Repeat Notification Dropdown is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("repeatNotificationDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("repeatNotificationDropdown");
			
			LOGGER.info("Validated Configure Notifications section of New Alert Creation Page.");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("configurePageNextBtn");
			waitForPageLoaded();

			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("overviewTitle"),
					"Overview Title is not present");
			LOGGER.info("Navigated to the Review and Create Alert Page.");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("overviewDescription"),
					"Overview Description is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewAlertTitleLabel"),
					"Overview Alert Title Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewAlertTitleValue"),
					"Overview Alert Title Value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewTriggerConditionTitle"),
					"Trigger Condition Title is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertTypeLabel"),
					"Overview Alert Type Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("alertTypeValue"),
					"Overview Alert Type Value is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewSeverityLabel"),
					"Severity Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewSeverityValue"),
					"Severity Value is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("dataPointLabel"),
					"Data Point Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("dataPointValue"),
					"Data Point Value is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("appsMoniteredLabel"),
					"Apps Monitered Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("appsMoniteredValue"),
					"Apps Monitered Value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewFleetThresholdLabel"),
					"Review Fleet Threshold Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewFleetThresholdValue"),
					"Review Fleet Threshold Value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewEventThresholdLabel"),
					"Review Event Threshold Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewEventThresholdValue"),
					"Review Event Threshold Value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewTimeWindowLabel"),
					"Review Time Window Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewTimeWindowValue"),
					"Review Time Window Value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewRecoveryPeriodLabel"),
					"Recovery Period Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewRecoveryPeriodValue"),
					"Recovery Period Value is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("deviceGroupsLabel"),
					"Device Groups Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("deviceGroupsValue"),
					"Device Groups Value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationThresholdTitleLabel"),
					"Review Escalation Threshold Title is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewEscalationSeverityLabel"),
					"Review Escalation Severity Label is not present");
			sleeper(2000);
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewEscalationSeverityValue"),
					"Review Escalation Severity value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewEscalationThresholdLabel"),
					"Review Escalation Threshold Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewEscalationThresholdValue"),
					"Review Escalation Threshold value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("notificationTitleLabel"),
					"Notification Title Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewInProductNotificationLabel"),
					"InProduct Notification Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewInProductNotificationValue"),
					"InProduct Notification Value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emailNotificationLabel"),
					"Email Notification Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emailNotificationValue"),
					"Email Notification Value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewRepeatNotificationLabel"),
					"Repeat Notification Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewRepeatNotificationValue"),
					"Repeat Notification Value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewTeamsNotificationLabel"),
					"Teams Notification Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("reviewTeamsNotificationValue"),
					"Teams Notification Value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage
							.verifyElementsOfWEPAlertManagementPage("escalationInProductNotificationLabel"),
					"Escalation InProduct Notification Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage
							.verifyElementsOfWEPAlertManagementPage("escalationInProductNotificationValue"),
					"Escalation InProduct Notification value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationTeamsNotificationLabel"),
					"Escalation Teams Notification Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationTeamsNotificationValue"),
					"Escalation Teams Notification value is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationEmailNotificationLabel"),
					"Escalation Email Notification Label is not present");
			softAssert.assertTrue(
					WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationEmailNotificationValue"),
					"Escalation Email Notification value is not present");

			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("publishButton");
			waitForPageLoaded();
			sleeper(10000);

			WEPAlertManagementPage.waitForElementsOfAlertManagementPage("toastMessage");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("toastMessage"),
					"Alert Published toast message is not present");
			LOGGER.info("New Alert created and published successfully.");

			WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("TitleHeaderSearch", alertTitle);
			sleeper(3000);
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("selectRowCheckbox");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("deleteAlert");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("deleteButton");
			sleeper(3000);
			WEPAlertManagementPage.waitForElementsOfAlertManagementPage("toastMessage");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("toastMessage"),
					"Alert deleted toast message is not present");
			LOGGER.info("New Alert deleted successfully.");
		}
		softAssert.assertAll();
	}

	@Test(priority = 9, groups = { "REGRESSION_FRAMEWORKCORE",
			"PRODUCTION_FRAMEWORKCORE" }, description = "TestCase ID :", enabled = true)
	public final void verifyNewAlertCreationValidation() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_ALERTS", "ITADMIN_PASSWORD_ALERTS");
		WEPAlertManagementPage WEPAlertManagementPage = new WEPAlertManagementPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		WEPAlertManagementPage.companyView(CommonVariables.CUSTOMER_ALERTS, CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);

		waitForPageLoaded();
		if (!WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("predefinedSelectAlertCheckbox")) {
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("emptyAlertList"),
					"No Alert Message not present");
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("emptyAlertList"),
					getTextLanguage(LanguageCode, "daas_ui", "list.no_items"));
			LOGGER.info("Predefined alerts are not present");
		} else {
			LOGGER.info("Redirected to the alert management page.");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("createButton");
			waitForPageLoaded();
			sleeper(2000);
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("createNewAlertTitle"),
					"Create New Alert Title is not present");
			LOGGER.info("Create New Alert Page is opened successfully");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("newAlertTypeDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");
			
			//Checking AppCrashes Alert Validations
			LOGGER.info("Checking App Crashes Alert Validations");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("dataPointToMonitorDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("appCrashesOption");
			
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("onThisAppLabel"),
					"On This App Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("onThisAppDropdown"),
					"On This App Dropdown is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("onThisAppHelperText"),
					"On This App helper text is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("onThisAppDropdown");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("dropdownSearch"),
					"Dropdown Search in OnThisApp Dropdown is not present");
			String option = WEPAlertManagementPage.getTextOfWEPAlertManagementPage("fleetOption");
			WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("dropdownSearch", option);
			sleeper(2000);
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("fleetOption"),option, "Dropdown Search not working properly.");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");
			LOGGER.info("AppCrashes Alert Validations Completed.");
			
			//Checking BSOD Alert Validations
			LOGGER.info("Checking BSOD Alert Validations");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("dataPointToMonitorDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("bsodOption");
			
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("bugCheckCodeLabel"),
					"On This App Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("bugCheckCodeDropdown"),
					"On This App Dropdown is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("bugCheckCodeDropdown");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("dropdownSearch"),
					"Dropdown Search in bugCheckCode Dropdown is not present");
			option = WEPAlertManagementPage.getTextOfWEPAlertManagementPage("fleetOption");
			WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("dropdownSearch", option);
			sleeper(2000);
			softAssert.assertEquals(WEPAlertManagementPage.getTextOfWEPAlertManagementPage("fleetOption"),option, "Dropdown Search not working properly.");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("bugCheckCodeDropdown");
			LOGGER.info("BSOD Alert Validations Completed.");
			
			//Checking Missing Windows Update Alert Validations
			LOGGER.info("Checking Missing Windows Update Alert Validations");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("dataPointToMonitorDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("missingWindowsUpddateOption");
			
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("updateTypeLabel"),
					"Update Type Label is not present");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("updateTypeDropdown"),
					"Update Type Dropdown is not present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("updateTypeDropdown");
			softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("dropdownSearch"),
					"Dropdown Search in updateType Dropdown is present");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("fleetOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("updateTypeDropdown");
			LOGGER.info("Missing Windows Update Alert Validations Completed.");
			
			//Checking Missing Windows Update Alert Validations
			LOGGER.info("Checking Missing Windows Update Alert Validations");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("dataPointToMonitorDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("osCrashesOption");
			LOGGER.info("Missing Windows Update Alert Validations Completed");
			
			//Checking Event, Fleet Threshold and Severity validation
			LOGGER.info("Checking Event and Fleet Threshold Validations");
			WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("eventThresholdInput", "1");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("eventThresholdHelperText"),
					"Event Threshold Error Message is not present");
			WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("eventThresholdInput", "2");
			WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("fleetThresholdInput", "1");
			WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("escalationThresholdInput", "1");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationThresholdHelperText"),
					"Escalation Threshold Error Message is not present");
			WEPAlertManagementPage.enterTextOfWEPAlertManagementPage("escalationThresholdInput", "2");
			LOGGER.info("Event and Fleet Threshold Validations Completed");
		
			LOGGER.info("Checking Severity Validations for High severity");
			//Checking When Severity selected as High
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("newAlertSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityHighOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityHighOption");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is not present for High severity and High escalation severity");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityCriticalOption");
			softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is present High severity and Critical escalation severity");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityMediumOption");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is not present High severity and Medium escalation severity");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityCriticalOption");
			softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is present High severity and Critical escalation severity");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityLowOption");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is not present High severity and Low escalation severity");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityCriticalOption");
			softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is present High severity and Critical escalation severity");
			
			LOGGER.info("Checking Severity Validations for Medium severity");
			//Checking When Severity selected as Medium
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("newAlertSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityMediumOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityHighOption");
			softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is present Medium severity and High escalation severity");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityMediumOption");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is not present Medium severity and Medium escalation severity");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityCriticalOption");
			softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is present Medium severity and Critical escalation severity");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityLowOption");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is not present Medium severity and Low escalation severity");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityCriticalOption");
			softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is present Medium severity and Critical escalation severity");
			
			LOGGER.info("Checking Severity Validations for Low severity");
			//Checking When Severity selected as Low
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("newAlertSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityLowOption");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityHighOption");
			softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is present Low severity and High escalation severity");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityMediumOption");
			softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is present Low severity and Medium escalation severity");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityLowOption");
			softAssert.assertTrue(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is not present Low severity and Low escalation severity");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("escalationSeverityDropdown");
			WEPAlertManagementPage.actionClickOnWEPAlertManagementPage("severityCriticalOption");
			softAssert.assertFalse(WEPAlertManagementPage.verifyElementsOfWEPAlertManagementPage("escalationSeverityHelperText"),
					"Escalation Severity Error Message is present Low severity and Critical escalation severity");
			
			LOGGER.info("Severity Validations Completed");
		}
		softAssert.assertAll();
	}
}
