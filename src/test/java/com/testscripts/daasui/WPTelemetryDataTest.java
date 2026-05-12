package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.LogsVariables;
import com.daasui.constants.ProductCatalogVariables;
import com.daasui.constants.WEPPulsesCreationPageVariables;
import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.assertTrue;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WPTelemetryDataTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(WPTelemetryDataTest.class);
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");

	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "ITADMIN_EMAIL_WEX";
		data[0][1] = "ITADMIN_PASSWORD_WEX";

		return data;
	}

	/**
	 * This method will verify the Title and Description of Telemetry Data
	 */
	@Test(priority = 1)
	public final void verifyTitleAndDescriptionOfTelemetryData() throws Exception {
		login("ITADMIN_EMAIL_WEX", "ITADMIN_PASSWORD_WEX");
		SoftAssert softAssert = new SoftAssert();
		WEPTelemetryDataPage wepTelemetryDataPage = new WEPTelemetryDataPage(PreDefinedActions.getDriver()).getInstance();
		// If the what's new popup is available, close it
		if(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton"))
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton");
		wepTelemetryDataPage.companyView(CommonVariables.ACCOUNT);
		softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("tabTelemetryData", getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data")),"Telemetry data is not available on Telemetry Data page");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("tabTelemetryData"), "Telemetry Data is not clickable");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementIsEnableOnTelemetryDataPage("dataCollectionLink"), "Data collection link is not available");
		LOGGER.info("Data Collection Documentation link is available on Telemetry data page");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("dataCollectionLink"),"Data collection link not clickable");
		sleeper(2000);
		wepTelemetryDataPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load
		String Actual_URL = wepTelemetryDataPage.getUrlOfCurrentPage();
		String Expected_URL = ConstantURL.DATA_COLLECTION_LINK;
		//softAssert.assertTrue((Actual_URL.contains(Expected_URL)), "URL for Data collection document  is not matching");

        Assert.assertEquals(Actual_URL, ConstantURL.DATA_COLLECTION_LINK, "URL for Data collection document is not matching");

		String ActualPage_Title = wepTelemetryDataPage.getBrowserTabName();
		String Expected_Title = "HP WXP - Security and Privacy Technical Document";
		softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for Data collection document link is not matching");
		
		LOGGER.info("Actual Title is :" + ActualPage_Title);
		LOGGER.info("Actual URL is :" + Actual_URL);
		LOGGER.info("Expected URL is :" + Expected_URL);
		wepTelemetryDataPage.switchBackToPreviousTab();
		wepTelemetryDataPage.scrollToTelemetryDataPage("requestData");
		softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("telemetryTitle", getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data")),"Telemetry data is not available on Telemetry Data page");
		//softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("telemetrySubTitle", getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data.desc")),"Telemetry decription is not available on Telemetry Data page");
		softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("requestData", getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data.button_request_data")),"Request Data is not available on Telemetry Data page");
		softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("requestId", getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data.button_request_id")),"Request Id column is not available on Telemetry Data page");
		softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("status", getTextLanguage(LanguageCode, "daas_ui", "alert_management.status.column")),"Status column is not available on Telemetry Data page");
		//softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("createdOn", getTextLanguage(LanguageCode, "daas_ui", "device-level-alerts.grid.column.createdOn")),"Created On column is not available on Telemetry Data page");
		LOGGER.info("Telemetry data description verified successfully");

		softAssert.assertAll();
	}

	/**
	 * This method will verify the Request Data Functionality
	 */
	@Test(priority = 2)
	public final void verifyRequestDataFuncionalityOfTelemetryData() throws Exception {
		login("ITADMIN_EMAIL_WEX", "ITADMIN_PASSWORD_WEX");
		SoftAssert softAssert = new SoftAssert();
		WEPTelemetryDataPage wepTelemetryDataPage = new WEPTelemetryDataPage(PreDefinedActions.getDriver()).getInstance();

		// If the what's new popup is available, close it
		if(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton"))
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton");

		wepTelemetryDataPage.companyView(CommonVariables.ACCOUNT);
		softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("tabTelemetryData", getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data")),"Telemetry data is not available on Telemetry Data page");

		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("tabTelemetryData"), "Telemetry Data is not clickable");
		softAssert.assertTrue(wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("requestData").equals(wepTelemetryDataPage.getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data.button_request_data")), "Request Data title does not match");
		sleeper(2000);
		if( wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("clearFilter")) {
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("clearFilter");
			LOGGER.info("Filter cleared successfully");
		}
		wepTelemetryDataPage.scrollToTelemetryDataPage("requestData");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("requestData"),"Request Data is not clickable");
		wepTelemetryDataPage.waitUntilElementIsPresentOnWEPTelemetryDataPage("downloadToastNotification");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("requestIdColumn"),"RequestID column is not visible");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("requestIdStatus"),"Requet Status column is not visible");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("requestIdCreatedtedOn"),"Request Created on column is not visible");
		wepTelemetryDataPage.scrollToTelemetryDataPage("pagination");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("requestIdSearch"),"Search Input is not present on Telemetry data Page");
		wepTelemetryDataPage.actionClickOfTelemetryDataPage("requestIdSearch");
		sleeper(2000);
		String requestIdColumnValue = wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("requestIdColumn");
		LOGGER.info("Got the value of Request id "+requestIdColumnValue);
		wepTelemetryDataPage.enterTextOfTelemetryDataPage("requestIdSearch",requestIdColumnValue);

		String requestIdColumnSearchValue = wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("requestIdColumn");
		LOGGER.info("Searched record is "+requestIdColumnSearchValue);
		softAssert.assertTrue(requestIdColumnValue.equals(wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("requestIdColumn")), 
				"Record with '" + requestIdColumnValue + "' was not found.");
		softAssert.assertAll();
	}
	/**
	 * This method will verify the Sorting On RequestId Column Functionality
	 */
	@Test(priority = 3)
	public final void verifySortingOfRequestIdColumn() throws Exception {

		login("ITADMIN_EMAIL_WEX", "ITADMIN_PASSWORD_WEX");
		SoftAssert softAssert = new SoftAssert();
		WEPTelemetryDataPage wepTelemetryDataPage = new WEPTelemetryDataPage(PreDefinedActions.getDriver()).getInstance();

		// If the what's new popup is available, close it
		if (wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton"))
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton");

		wepTelemetryDataPage.companyView(CommonVariables.ACCOUNT);
		softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("tabTelemetryData", getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data")), "Telemetry data is not available on Telemetry Data page");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("tabTelemetryData"), "Telemetry Data is not clickable");
		wepTelemetryDataPage.scrollToTelemetryDataPage("pagination");
		if (wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("clearFilter")) {
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("clearFilter");
			LOGGER.info("Filter cleared successfully");
		}
		sleeper(2000);
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("requestIdSortBtn"), "Request Id Sort button is not present on Telemetry Data Page");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("requestIdSortBtn"), "Request Id Sort button is not clickable on Telemetry Data Page");
		LOGGER.info("Clicked on Request Id Sort button");
		List<String> requestIdList = wepTelemetryDataPage.getAvailableRequestId();
		boolean isDesc = wepTelemetryDataPage.getSortingOrderType();
		isDesc = wepTelemetryDataPage.getSortingOrderType();
		sleeper(2000);
		requestIdList = wepTelemetryDataPage.getAvailableRequestId();
		softAssert.assertTrue(wepTelemetryDataPage.isRequestIdSorted(requestIdList, isDesc), "The request Id's are not in sorted format");
		LOGGER.info("Sorting Of Request Id is Verified successfully");

	}

	/**
	 * This method will verify the filter on Status Column
	 */
	@Test(priority = 4)
	public final void verifyStatusColumnOption() throws Exception {
		login("ITADMIN_EMAIL_WEX", "ITADMIN_PASSWORD_WEX");
		SoftAssert softAssert = new SoftAssert();
		WEPTelemetryDataPage wepTelemetryDataPage = new WEPTelemetryDataPage(PreDefinedActions.getDriver()).getInstance();

		// If the what's new popup is available, close it
		if (wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton"))
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton");

		wepTelemetryDataPage.companyView(CommonVariables.ACCOUNT);
		softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("tabTelemetryData", getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data")), "Telemetry data is not available on Telemetry Data page");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("tabTelemetryData"), "Telemetry Data is not clickable");
		if (wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("clearFilter")) {
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("clearFilter");
			LOGGER.info("Filter cleared successfully");
		}
		sleeper(3000);
		wepTelemetryDataPage.scrollToTelemetryDataPage("pagination");

		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusDropdown"),"Status dropdown not there");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("statusDropdown"),"Status dropdown is not clickable");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusInitiated"),"Initiated Status dropdown not there");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusInprogress"),"Inprogress Status dropdown not there");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusCompleted"),"Completed Status dropdown not there");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusExpired"),"Expired Status dropdown not there");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusFailed"),"Failed Status dropdown not there");
		String Option1 = wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("statusCompleted");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("statusCompleted"),"Completed status is not clickable");
		waitForPageLoaded();
		wepTelemetryDataPage.waitForElementsOfWEPTelemetryDataPage("statuscolumndropdown");
		softAssert.assertTrue(wepTelemetryDataPage.verifyFilteredDataOnTelemetryDataPage("statuscolumndropdown", Option1), "Filtered data is incorrect for status column");
		LOGGER.info("Completed Status column filter applied successfully");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("clearFilter"),"Failed to clear filter");
		LOGGER.info("Filter cleared successfully");

		wepTelemetryDataPage.waitForElementsOfWEPTelemetryDataPage("statusDropdown");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("statusDropdown"),"Status dropdown is not clickable");
		String Option2 = wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("statusInprogress");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("statusInprogress"),"Inprogress status is not clickable");
		sleeper(3000);
		wepTelemetryDataPage.waitForElementsOfWEPTelemetryDataPage("statuscolumndropdown");
		softAssert.assertTrue(wepTelemetryDataPage.verifyFilteredDataOnTelemetryDataPage("statuscolumndropdown", Option2), "Filtered data is incorrect for status column");
		LOGGER.info("Inprogress Status column filter applied successfully");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("clearFilter"),"Failed to clear filter");
		//wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("clearFilter");
		LOGGER.info("Filter cleared successfully");
		softAssert.assertAll();
	}

	@Test(priority = 5)
	public final void verifyStatusColumnFilterWithNoResult() throws Exception {
		login("ITADMIN_EMAIL_WEX", "ITADMIN_PASSWORD_WEX");
		SoftAssert softAssert = new SoftAssert();
		WEPTelemetryDataPage wepTelemetryDataPage = new WEPTelemetryDataPage(PreDefinedActions.getDriver()).getInstance();

		// Close popup if present
		if (wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton"))
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton");

		wepTelemetryDataPage.companyView(CommonVariables.ACCOUNT);
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("tabTelemetryData"), "Telemetry Data is not clickable");

		// Clear any existing filters
		if (wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("clearFilter"))
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("clearFilter");
		LOGGER.info("Filter cleared successfully");
		sleeper(3000);
		// Verify the Status dropdown is present and clickable

		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("statusDropdown"), "Status dropdown is not clickable");
		String InitiatedOption = wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("statusInitiated");

		// Apply "Initiated" filter
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("statusInitiated"), "Initiated status is not clickable");
		sleeper(3000);

		wepTelemetryDataPage.waitUntilElementIsPresentOnWEPTelemetryDataPage("statuscolumndropdown");
		// wepTelemetryDataPage.waitForElementsOfWEPTelemetryDataPage("statuscolumndropdown");

		// Check for no result
		if (wepTelemetryDataPage.isDataTableEmpty()) {
			softAssert.assertTrue(
					wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("noRecordText"),
					"No results message is not displayed when no data is available for Initiated status"
					);
			softAssert.assertEquals(
					wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("noRecordText"),
					"No results",
					"No results message text is incorrect"
					);
		} else {
			// If data is present, verify it matches the filter
			softAssert.assertTrue(
					wepTelemetryDataPage.verifyFilteredDataOnTelemetryDataPage("statuscolumndropdown", InitiatedOption),
					"Filtered data is incorrect for status column"
					);
		}
		softAssert.assertAll();
	}
	@Test(priority = 6)
	public final void verifyDownloadFileFunctionality() throws Exception {

		login("ITADMIN_EMAIL_WEX", "ITADMIN_PASSWORD_WEX");
		SoftAssert softAssert = new SoftAssert();
		WEPTelemetryDataPage wepTelemetryDataPage = new WEPTelemetryDataPage(PreDefinedActions.getDriver()).getInstance();

		// If the what's new popup is available, close it
		if (wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton"))
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton");

		wepTelemetryDataPage.companyView(CommonVariables.ACCOUNT);
		softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("tabTelemetryData", getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data")), "Telemetry data is not available on Telemetry Data page");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("tabTelemetryData"), "Telemetry Data is not clickable");
		if (wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("clearFilter")) {
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("clearFilter");
			LOGGER.info("Filter cleared successfully");
		}
		sleeper(3000);
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusDropdown"),"Status dropdown not there");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("statusDropdown"),"Status dropdown is not clickable");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusInitiated"),"Initiated Status dropdown not there");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusInprogress"),"Inprogress Status dropdown not there");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusCompleted"),"Completed Status dropdown not there");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusFailed"),"Failed Status dropdown not there");
		String Option1 = wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("statusCompleted");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("statusCompleted"),"Completed status is not clickable");
		LOGGER.info("Selected Completed status");
		wepTelemetryDataPage.waitForElementsOfWEPTelemetryDataPage("threeDot");

		wepTelemetryDataPage.mousehoverOnTelemetryDataPage("threeDot");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("threeDot"),"threeDots not there for completed status.");

		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("threeDot"),"Completed status is not clickable");
		sleeper(1000);
		LOGGER.info("Clicked on threeDot");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("downloadButton"),"Download Button is not present for completed status");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("downloadButton"),"Download Button is not clickable");
		softAssert.assertAll();
		LOGGER.info("File downloaded successfully");

	}

	@Test(priority = 7)
	public final void verifyNoTelemetryDataTabForPartner() throws Exception {
		login("PARTNER_EMAIL_WEX", "PARTNER_PASSWORD_WEX");
		SoftAssert softAssert = new SoftAssert();
		WEPTelemetryDataPage wepTelemetryDataPage = new WEPTelemetryDataPage(PreDefinedActions.getDriver()).getInstance();
		// If the what's new popup is available, close it
		if (wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton"))
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton");
		leftSideMenuVerification();
		waitForPageLoaded();
		wepTelemetryDataPage.waitForElementsOfWEPTelemetryDataPage("SelectCustomer");
 
		// If the All customer is not selected, select the All customer
		if (!wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
			wepTelemetryDataPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
		}
		wepTelemetryDataPage.companyView(CommonVariables.ACCOUNT);
		//Verify no Telemetry tab is available
		Assert.assertFalse(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("tabTelemetryData", getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data")),"No Telemetry Data tab is available");
		LOGGER.info("Telemetry Data tab is not available");
	}

	/**
	 * This method will verify the Request Data Functionality for Partner Admin
	 */
	
	@Test(priority = 8)
	public final void verifyRequestDataForPartnerAdmin() throws Exception {
		login("PARTNER_EMAIL_WEX", "PARTNER_PASSWORD_WEX");
		SoftAssert softAssert = new SoftAssert();
		WEPTelemetryDataPage wepTelemetryDataPage = new WEPTelemetryDataPage(PreDefinedActions.getDriver()).getInstance();
		// If the what's new popup is available, close it
		if (wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton"))
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton");
		leftSideMenuVerification();
		
		waitForPageLoaded();
	    softAssert.assertTrue(wepTelemetryDataPage.waitForElementsOfWEPTelemetryDataPage("SelectCustomer"),"All Customer is not present");
		
	    // If All customer is not selected, then select All customer
	    if (!wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("SelectCustomer", CommonVariables.ALL_CUSTOMER)) {
			wepTelemetryDataPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
		}
		wepTelemetryDataPage.partnerWithCompanyView(CommonVariables.TDCUSTOMER_NAME,CommonVariables.ACCOUNT);
		LOGGER.info("Redirected to Account tab");
        softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("tabTelemetryData", getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data")),"Telemetry data is not available on Telemetry Data page");
        softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("tabTelemetryData"), "Telemetry Data is not clickable");
		softAssert.assertTrue(wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("requestData").equals(wepTelemetryDataPage.getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data.button_request_data")), "Request Data title does not match");
		sleeper(2000);
		
		if( wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("clearFilter")) {
			wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("clearFilter");
			LOGGER.info("Filter cleared successfully");
		}
		
		wepTelemetryDataPage.scrollToTelemetryDataPage("requestData");
		softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("requestData"),"Request Data is not clickable");
		wepTelemetryDataPage.waitUntilElementIsPresentOnWEPTelemetryDataPage("downloadToastNotification");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("requestIdColumn"),"RequestID column is not visible");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("requestIdStatus"),"Requet Status column is not visible");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("requestIdCreatedtedOn"),"Request Created on column is not visible");
		wepTelemetryDataPage.scrollToTelemetryDataPage("pagination");
		softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("requestIdSearch"),"Search Input is not present on Telemetry data Page");
		wepTelemetryDataPage.actionClickOfTelemetryDataPage("requestIdSearch");
		sleeper(2000);
		String requestIdColumnValue = wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("requestIdColumn");
		LOGGER.info("Got the value of Request id "+requestIdColumnValue);
		wepTelemetryDataPage.enterTextOfTelemetryDataPage("requestIdSearch",requestIdColumnValue);

		String requestIdColumnSearchValue = wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("requestIdColumn");
		LOGGER.info("Searched record is "+requestIdColumnSearchValue);
		softAssert.assertTrue(requestIdColumnValue.equals(wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("requestIdColumn")), 
				"Record with '" + requestIdColumnValue + "' was not found.");
		
		softAssert.assertAll();
		LOGGER.info("Request data for Partner Admin verified successfully");
	}
	
	/**
	 * This method will verify the Download File Functionality for Partner Admin
	 */
	
	@Test(priority = 9)
	public final void verifyDownloadFileFunctionalityForPartnerAdmin() throws Exception {
	    login("PARTNER_EMAIL_WEX", "PARTNER_PASSWORD_WEX");
	    SoftAssert softAssert = new SoftAssert();
	    WEPTelemetryDataPage wepTelemetryDataPage = new WEPTelemetryDataPage(PreDefinedActions.getDriver()).getInstance();
	    // If the what's new popup is available, close it
	    if (wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton"))
	        wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton");
	    leftSideMenuVerification();
	    waitForPageLoaded();
	    softAssert.assertTrue(wepTelemetryDataPage.waitForElementsOfWEPTelemetryDataPage("SelectCustomer"),"All Customer is not present");
	    // If All customer is not selected, then select All customer
	    if (!wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("SelectCustomer", CommonVariables.ALL_CUSTOMER)) {
	        wepTelemetryDataPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
	    }
	    wepTelemetryDataPage.partnerWithCompanyView(CommonVariables.TDCUSTOMER_NAME,CommonVariables.ACCOUNT);
	    LOGGER.info("Redirected to Account tab");
	    softAssert.assertTrue(wepTelemetryDataPage.matchTextOfWEPTelemetryDataPage("tabTelemetryData", getTextLanguage(LanguageCode, "daas_ui", "account_management.tab.telemetry_data")),"Telemetry data is not available on Telemetry Data page");
	    softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("tabTelemetryData"), "Telemetry Data is not clickable");
	    if (wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("clearFilter")) {
	        wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("clearFilter");
	        LOGGER.info("Filter cleared successfully");
	    }
	    sleeper(3000);
	    softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusDropdown"),"Status dropdown not there");
	    softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("statusDropdown"),"Status dropdown is not clickable");
	    softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("statusCompleted"),"Completed Status dropdown not there");
	    String Option1 = wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("statusCompleted");
	    softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("statusCompleted"),"Completed status is not clickable");
	    LOGGER.info("Selected Completed status");
		wepTelemetryDataPage.scrollToTelemetryDataPage("pagination");
	    wepTelemetryDataPage.waitForElementsOfWEPTelemetryDataPage("threeDot");
	    wepTelemetryDataPage.mousehoverOnTelemetryDataPage("threeDot");
	    softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("threeDot"),"threeDots not there for completed status.");
	    softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("threeDot"),"Completed status is not clickable");
	    sleeper(1000);
	    LOGGER.info("Clicked on threeDot");
	    softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("downloadButton"),"Download Button is not present for completed status");
	    softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("downloadButton"),"Download Button is not clickable");
	    softAssert.assertAll();
	    LOGGER.info("File downloaded successfully for Partner Admin");
	}
	
	@Test(priority = 10)
	public final void verifyApplyFilteronLogListPage() throws Exception {
	    SoftAssert softAssert = new SoftAssert();
	    login("ITADMIN_EMAIL_WEX", "ITADMIN_PASSWORD_WEX");
	    WEPTelemetryDataPage wepTelemetryDataPage = new WEPTelemetryDataPage(PreDefinedActions.getDriver()).getInstance();
			if(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton")) {
				wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("whatsNewPopupClosedButton");
			}
			leftSideMenuVerification();
			wepTelemetryDataPage.companyView(CommonVariables.SETTINGS);
	    	 sleeper(3000);
	 	    softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("logs"),"Logs tab not present on Settings page");
	 	    softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("logs"),"Logs tab is not clickable on Settings page");
				LOGGER.info("Redirected to logs list page");
				
				if(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("clearFilter")) {
					wepTelemetryDataPage.scrollToTelemetryDataPage("clearFilter");
					wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("clearFilter");
				}
				sleeper(3000);
				wepTelemetryDataPage.scrollToTelemetryDataPage("typeDropdown");
		 	    softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("typeDropdown"),"Type dropdown is not clickable on Logs page");
				wepTelemetryDataPage.enterTextOfTelemetryDataPage("typeListSearch", CommonVariables.TYPE_EU_DATA_DOWNLOAD);
				sleeper(1000);
				wepTelemetryDataPage.actionClickOfTelemetryDataPage("typeList");
		 	    //softAssert.assertTrue(wepTelemetryDataPage.actionClickOfTelemetryDataPage("typeList"),"Type List is not clickable on Logs page");
				wepTelemetryDataPage.scrollToTelemetryDataPage("subtypeDropdown");
		 	    softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("subtypeDropdown"),"SubType  dropdown is not clickable on Logs page");
		 	    sleeper(1000);
				wepTelemetryDataPage.enterTextOfTelemetryDataPage("subtypeDropdown", CommonVariables.SUBTYPE_REQUEST);
				sleeper(2000);
				wepTelemetryDataPage.actionClickOfTelemetryDataPage("subtypelist");

		 	    //softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("subtypelist"),"SubTypeList is not clickable on Logs page");
				wepTelemetryDataPage.scrollToTelemetryDataPage("levelDropdown");
				
		 	    softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("levelDropdown"),"Level dropdown is not clickable on Logs page");
				sleeper(1000);
				wepTelemetryDataPage.enterTextOfTelemetryDataPage("levelDropdown",CommonVariables.lEVEL_INFORMATION);
				sleeper(2000);
				wepTelemetryDataPage.actionClickOfTelemetryDataPage("levellist");

		 	    //softAssert.assertTrue(wepTelemetryDataPage.actionClickOfTelemetryDataPage("levellist"),"Level list is not clickable on Logs page");
				wepTelemetryDataPage.scrollToTelemetryDataPage("dateAndTimeHeader");
		 	    softAssert.assertTrue(wepTelemetryDataPage.clickOnElementsOfWEPTelemetryDataPage("firstRowDateAndTime"),"Date And Time is not clickable on Logs page");
				softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("logEntries"), "logs entries page is not loaded successfully");
				softAssert.assertTrue(wepTelemetryDataPage.verifyElementsOfWEPTelemetryDataPage("loginfo"), "logsinfo page is not loaded successfully");
				softAssert.assertTrue(wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("typeinfo").equals(CommonVariables.TYPE_EU_DATA_DOWNLOAD),"Type filter is not matching");
				sleeper(1000);
				LOGGER.info("SubType " + wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("subtypeinfo"));
				LOGGER.info("levelinfo " + wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("levelinfo"));
				softAssert.assertTrue(wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("subtypeinfo").equals(CommonVariables.SUBTYPE_REQUEST),"Subtype filter is not matching");
				wepTelemetryDataPage.scrollToTelemetryDataPage("levelDropdown");
				sleeper(2000);
				softAssert.assertTrue(wepTelemetryDataPage.getTextOfWEPTelemetryDataPage("levelinfo").equals(CommonVariables.lEVEL_INFORMATION),"level info is not matching");
			    softAssert.assertAll();
				}
}










