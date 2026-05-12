package com.testscripts.daasui;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.WEPAlertManagementPage;
import com.daasui.pages.WEPAlertsDashboardPage;
import com.daasui.pages.WEPDeviceLevelAlertsPage;
public class WEPDeviceLevelAlertsPageTest extends CommonTest {
	
	@Test(priority = 1, groups = { "REGRESSION_FRAMEWORKCORE", "PRODUCTION_FRAMEWORKCORE" }, enabled = false )
	public final void verifyDeviceLevelAlertPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEPDeviceLevelAlertsPage DeviceAlerts = new WEPDeviceLevelAlertsPage(PreDefinedActions.getDriver())
				.getInstance();
		leftSideMenuVerification();
		DeviceAlerts.actionClickOfDeviceAlertPage("deviceAlertOption");
		waitForPageLoaded();
		DeviceAlerts.verifyElementsOfDeviceAlertPage("deviceLevelTab");
		softAssert.assertEquals(DeviceAlerts.getTextOfDeviceAlertPage("deviceLevelTab"),getTextLanguage(LanguageCode, "daas_ui", "device_level_alerts_tab"),"Device level text is  not match");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("deviceLevelTab");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("gearIcon");
		if (DeviceAlerts.verifyElementIsClickable("resetToDefaultButton")) {
			DeviceAlerts.clickOnElementsOfDeviceAlertPage("resetToDefaultButton");
		}
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("saveButton");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("gearIcon"),
				"Column Setting Icon is not present on device Alert Page");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("severityColumn"),
				"severity column is not present on device Alert Page");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("deviceNamecolumn"),
				"device name column is not present on device Alert Page");
		softAssert.assertEquals(DeviceAlerts.getTextOfDeviceAlertPage("deviceNamecolumn"),getTextLanguage(LanguageCode, "daas_ui", "device-level-alerts.grid.column.deviceName"),"Device name column text is  not match");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("kpiColumn"),
				"KPI is not present on device Alert Page");
		softAssert.assertEquals(DeviceAlerts.getTextOfDeviceAlertPage("kpiColumn"),getTextLanguage(LanguageCode, "daas_ui", "device-level-alerts.grid.column.KPI"),"KPI column text is  not match");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("occurencesColumn"),
				"occurences is not present on device Alert Page");
		softAssert.assertEquals(DeviceAlerts.getTextOfDeviceAlertPage("occurencesColumn"),getTextLanguage(LanguageCode, "daas_ui", "device-level-alerts.grid.column.occurrences"),"Occurences column text is  not match");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("serialNumberColumn"),
				"serial number column is not present on device Alert Page");
		softAssert.assertEquals(DeviceAlerts.getTextOfDeviceAlertPage("serialNumberColumn"),getTextLanguage(LanguageCode, "daas_ui", "device-level-alerts.grid.column.serialNumber"),"Serial number  column text is  not match");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("modelColumn"),
				" Model Column  is not present on device Alert Page");
		softAssert.assertEquals(DeviceAlerts.getTextOfDeviceAlertPage("modelColumn"),getTextLanguage(LanguageCode, "daas_ui", "device-level-alerts.grid.column.model"),"Model  column text is  not match");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("createdONColumn"),
				"created Column is not present on device Alert Page");
		softAssert.assertEquals(DeviceAlerts.getTextOfDeviceAlertPage("createdONColumn"),getTextLanguage(LanguageCode, "daas_ui", "device-level-alerts.grid.column.createdOn"),"Created column text is  not match");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("lastUpdatedColumn"),
				"last updated Column is not present on device Alert Page");
		softAssert.assertEquals(DeviceAlerts.getTextOfDeviceAlertPage("lastUpdatedColumn"),getTextLanguage(LanguageCode, "daas_ui", "device-level-alerts.grid.column.lastUpdated"),"Last updated column text is  not match");
		softAssert.assertAll();
	}

	@Test(priority = 2, groups = { "REGRESSION_FRAMEWORKCORE", "PRODUCTION_FRAMEWORKCORE" } , enabled = false)
	public final void verifyColumnSettingFunctionalityofDeviceAlerts() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEPDeviceLevelAlertsPage DeviceAlerts = new WEPDeviceLevelAlertsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		DeviceAlerts.actionClickOfDeviceAlertPage("deviceAlertOption");
		waitForPageLoaded();
		DeviceAlerts.verifyElementsOfDeviceAlertPage("deviceLevelTab");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("deviceLevelTab");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("gearIcon");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("optionLabel2");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("optionLabel3");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("optionLabel4");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("optionLabel5");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("optionLabel6");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("optionLabel7");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("optionLabel8");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("saveButton");
		Thread.sleep(2000);
		DeviceAlerts.verifyElementIsinvisibile("severityColumn");
		DeviceAlerts.verifyElementIsinvisibile("kpiColumn");
		DeviceAlerts.verifyElementIsinvisibile("occurencesColumn");
		DeviceAlerts.verifyElementIsinvisibile("serialNumberColumn");
		DeviceAlerts.verifyElementIsinvisibile("modelColumn");
		DeviceAlerts.verifyElementIsinvisibile("createdONColumn");
		DeviceAlerts.verifyElementIsinvisibile("lastUpdatedColumn");

		DeviceAlerts.clickOnElementsOfDeviceAlertPage("gearIcon");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("optionLabel2");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("saveButton");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("severityColumn"),
				"Severity Column is not present on Active Alert Page");
		Thread.sleep(2000);

		DeviceAlerts.clickOnElementsOfDeviceAlertPage("gearIcon");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("optionLabel2");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("saveButton");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("gearIcon");
		Thread.sleep(2000);
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("resetToDefaultButton");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("saveButton");
		Thread.sleep(2000);
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("severityColumn"),
				"Severity Column is not present on Active Alert Page");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("kpiColumn"),
				"KPI Column is not present on Active Alert Page");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("occurencesColumn"),
				"Occcurence Column is not present on Active Alert Page");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("serialNumberColumn"),
				"serial number Column is not present on Active Alert Page");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("modelColumn"),
				"model Column is not present on Active Alert Page");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("createdONColumn"),
				"created Column is not present on Active Alert Page");
		softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("lastUpdatedColumn"),
				"last updated Column is not present on Active Alert Page");
		softAssert.assertAll();
	}

	@Test(priority = 3, groups = { "REGRESSION_FRAMEWORKCORE", "PRODUCTION_FRAMEWORKCORE" } , enabled = false)
	public final void verifysearchFunctionalityofDeviceAlerts() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEPDeviceLevelAlertsPage DeviceAlerts = new WEPDeviceLevelAlertsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		DeviceAlerts.actionClickOfDeviceAlertPage("deviceAlertOption");
		waitForPageLoaded();
		DeviceAlerts.verifyElementsOfDeviceAlertPage("deviceLevelTab");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("deviceLevelTab");
		if(!DeviceAlerts.verifyElementsOfDeviceAlertPage("firstCheckbox"))
		{	
			softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("emptyAlertList"),"No Alert Message not present");
			softAssert.assertEquals(DeviceAlerts. getTextOfDeviceAlertPage("emptyAlertList"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"));	
		}else {
			softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("deviceSearch"),
					"Search Input is not present on device Alert Page");
			DeviceAlerts.clickOnElementsOfDeviceAlertPage("deviceSearch");
			String input = DeviceAlerts.getTextOfDeviceAlertPage("deviceDescription");
			DeviceAlerts.enterTextOfDeviceAlertPage("deviceSearch", input);
			DeviceAlerts.clickOnElementsOfDeviceAlertPage("deviceSearchButton");
			sleeper(3000);
			List<WebElement> records = DeviceAlerts.getllAllElementsVisibleofDeviceAlertPage("recordTable");
			softAssert.assertEquals(records.size(), 1, "search not working correctly");
			softAssert.assertEquals(input, DeviceAlerts.getTextOfDeviceAlertPage("deviceDescription"), "invalidsearch");
			DeviceAlerts.enterTextOfDeviceAlertPage("deviceSearch", "invalidTextEntered#");
			DeviceAlerts.clickOnElementsOfDeviceAlertPage("deviceSearchButton");
			sleeper(3000);
			softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("emptyAlertList"),"No result not present");
			softAssert.assertEquals(DeviceAlerts.getTextOfDeviceAlertPage("emptyAlertList"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"),"no result text is  not match");
		}
		softAssert.assertAll();
	}

	@Test(priority = 4, groups = { "REGRESSION_FRAMEWORKCORE", "PRODUCTION_FRAMEWORKCORE" }, enabled = false)
	public final void verifyDevicePopUpandKPIdropdownFunctionalityofDeviceAlerts() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEPDeviceLevelAlertsPage DeviceAlerts = new WEPDeviceLevelAlertsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		DeviceAlerts.actionClickOfDeviceAlertPage("deviceAlertOption");
		waitForPageLoaded();
		DeviceAlerts.verifyElementsOfDeviceAlertPage("deviceLevelTab");
		DeviceAlerts.clickOnElementsOfDeviceAlertPage("deviceLevelTab");
		if(!DeviceAlerts.verifyElementsOfDeviceAlertPage("firstCheckbox"))
		{	
			softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("emptyAlertList"),"No Alert Message not present");
			softAssert.assertEquals(DeviceAlerts. getTextOfDeviceAlertPage("emptyAlertList"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"));	
		}else {
			DeviceAlerts.clickOnElementsOfDeviceAlertPage("deviceDescription");
			waitForPageLoaded();
			DeviceAlerts.verifyElementsOfDeviceAlertPage("printerPaperHeader");
			DeviceAlerts.verifyElementsOfDeviceAlertPage("closeButton");
			DeviceAlerts.verifyElementsOfDeviceAlertPage("deviceID");
			DeviceAlerts.verifyElementsOfDeviceAlertPage("osInfo");
			DeviceAlerts.verifyElementsOfDeviceAlertPage("occurencesInfo");
			DeviceAlerts.verifyElementsOfDeviceAlertPage("viewDeviceDetails");
			DeviceAlerts.clickOnElementsOfDeviceAlertPage("closeButton");
			waitForPageLoaded();
			softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("kpiSearch"),"Status dropdown not there");
			DeviceAlerts.actionClickOfDeviceAlertPage("kpiSearch");
			sleeper(2000);
			softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("kpiOption1"),"KPI option1 Status dropdown not there");
			softAssert.assertTrue(DeviceAlerts.verifyElementsOfDeviceAlertPage("kpiOption2")," KPI option2 Status dropdown not there");
			String Option = DeviceAlerts.getTextOfDeviceAlertPage("kpiOption2");
			DeviceAlerts.actionClickOfDeviceAlertPage("kpiOption2");
			DeviceAlerts.actionClickOfDeviceAlertPage("kpiSearch");
			waitForPageLoaded();
			DeviceAlerts.waitForElementsOfDeviceAlertPage("kpiDropDownOption");
			softAssert.assertTrue(DeviceAlerts.verifyFilteredDataOnDeviceAlertPage("kpiDropDownOption", Option), "Filtered data is incorrect for KPI column");
			if (DeviceAlerts.verifyElementsOfDeviceAlertPage("kpiClear")) {
				DeviceAlerts.clickByJavaScriptOnDeviceAlertPage("kpiClear");
		       }

			DeviceAlerts.actionClickOfDeviceAlertPage("kpiColumn");
			waitForPageLoaded();
			softAssert.assertTrue(DeviceAlerts.verifyDropdownOptionOrderOnDeviceAlertPage("kpiDropDownOption"),"KPI field is not sorted");
			waitForPageLoaded();

		}
		softAssert.assertAll();
	}
}








