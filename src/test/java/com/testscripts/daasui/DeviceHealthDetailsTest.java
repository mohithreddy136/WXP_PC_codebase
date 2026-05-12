package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.DeviceHealthVariables;
import com.daasui.pages.DeviceHealthDetailsPage;

public class DeviceHealthDetailsTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(DeviceHealthDetailsTest.class);

	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "PRODUCTION_SANITY" })
	public final void verifyBatteryReplacementChart() throws Exception {
		DeviceHealthDetailsPage deviceHealthDetailsPage = new DeviceHealthDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		deviceHealthDetailsPage.scrollToDeviceHealthDetailsPage("batteryReplacementNoData");
		if (deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("batteryReplacementNoDataTitle")) {
			LOGGER.info("Data not present for Battery Replacement");
			softAssert.assertTrue(deviceHealthDetailsPage.matchTextOfDeviceHealthDetailsPage("batteryReplacementNoDataTitle", getTextLanguage(LanguageCode, "daas_ui", "dashboard.dhealth.action.brep.nodata.title")), "Battery Replacement No data title does not match");
			softAssert.assertTrue(deviceHealthDetailsPage.matchTextOfDeviceHealthDetailsPage("batteryReplacementNoDataDesc", getTextLanguage(LanguageCode, "daas_reports_ui", "flexible.dashboard.dhealth.action.brep.desc")), "Battery Replacement No data Description does not match");
			LOGGER.info("Verified no data elements");
		} else if (deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("batteryReplacementDataTileTitle")) {
			LOGGER.info("Data present for Battery Replacement");
			softAssert.assertTrue(deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("batteryReplacementDataTileCount"), "Battery Replacement Count not visible");
			softAssert.assertTrue(deviceHealthDetailsPage.matchTextOfDeviceHealthDetailsPage("batteryReplacementDataTileTitle", getTextLanguage(LanguageCode, "daas_ui", "dashboard.dhealth.action.brep")), "Battery Replacement data Title does not match");
			softAssert.assertTrue(deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("batteryReplacementViewDetailButton"), "Battery Replacement View details button not visible");
			softAssert.assertTrue(deviceHealthDetailsPage.columnHeadersTest(LanguageCode), "Report columns does not match");
			deviceHealthDetailsPage.switchBackToPreviousTab();
			LOGGER.info("Switched to Previous tab");
		}
		softAssert.assertAll();
		LOGGER.info("Validation of Battery Replacement completed");
	}

	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI", "PRODUCTION_SANITY" })
	public final void verifyHealthyDiskChart() throws Exception {
		DeviceHealthDetailsPage deviceHealthDetailsPage = new DeviceHealthDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		if (deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("healthyDiskNoDataTitle")) {
			LOGGER.info("Data not present for Healthy Disk");
			softAssert.assertTrue(deviceHealthDetailsPage.matchTextOfDeviceHealthDetailsPage("healthyDiskNoDataTitle", getTextLanguage(LanguageCode, "daas_reports_ui", "flexible.dashboard.dhealth.action.drep.nodata.title")), "Healthy Disk No data title does not match");
			softAssert.assertTrue(deviceHealthDetailsPage.matchTextOfDeviceHealthDetailsPage("healthyDiskNoDataDesc", getTextLanguage(LanguageCode, "daas_reports_ui", "flexible.dashboard.dhealth.action.drep.desc")), "Healthy Disk No data Description does not match");
			LOGGER.info("Verified no data elements");
		} else if (deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("healthyDiskDataTileTitle")) {
			LOGGER.info("Data present for Healthy Disk");
			softAssert.assertTrue(deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("healthyDiskDataTileCount"), "Disk Capacity Count not visible");
			softAssert.assertTrue(deviceHealthDetailsPage.matchTextOfDeviceHealthDetailsPage("healthyDiskDataTileTitle", getTextLanguage(LanguageCode, "daas_ui", "dashboard.dhealth.action.drep")), "Healthy Disk data Title does not match");
			softAssert.assertTrue(deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("healthyDiskViewDetailButton"), "Disk Capacity View details button not visible");
			softAssert.assertTrue(deviceHealthDetailsPage.columnHeadersTest(LanguageCode), "Report columns does not match");
			deviceHealthDetailsPage.switchBackToPreviousTab();
			LOGGER.info("Switched to Previous tab");
		}
		softAssert.assertAll();
		LOGGER.info("Validation of Healthy Disk completed");
	}

	@Test(priority = 3, groups = { "REGRESSION_CI", "REGRESSION" }, description = "TC:[906006]")
	public final void verifyDiskCapacityChart() throws Exception {
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
		DeviceHealthDetailsPage deviceHealthDetailsPage = new DeviceHealthDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		Assert.assertTrue(toggleVerification(DeviceHealthVariables.DEVICEHEALTHTOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS")), "Toggle disabled for given user");
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		deviceHealthDetailsPage.mousehoverOnDeviceHealthDetailsPage("diskCpacityWidget");
		if (deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("diskCapacityNoDataTitle")) {
			LOGGER.info("Data not present for Disk Capacity");
			softAssert.assertTrue(deviceHealthDetailsPage.matchTextOfDeviceHealthDetailsPage("diskCapacityNoDataTitle", getTextLanguage(LanguageCode, "daas_ui", "dashboard.dhealth.action.dcap.nodata.title")), "Disk Capacity No data title does not match");
			softAssert.assertTrue(deviceHealthDetailsPage.matchTextOfDeviceHealthDetailsPage("diskCapacityNoDataDesc", getTextLanguage(LanguageCode, "daas_reports_ui", "flexible.dashboard.dhealth.action.dcap.desc")), "Disk Capacity No data Description does not match");
			LOGGER.info("Verified no data elements");
		} else if (deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("diskCapacityDataTileTitle")) {
			LOGGER.info("Data present for Disk Capacity");
			softAssert.assertTrue(deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("diskCapacityDataTileCount"), "Disk Capacity Count not visible");
			softAssert.assertTrue(deviceHealthDetailsPage.matchTextOfDeviceHealthDetailsPage("diskCapacityDataTileTitle", getTextLanguage(LanguageCode, "daas_ui", "dashboard.dhealth.action.dcap")), "Disk Capacity data Title does not match");
			Assert.assertTrue(deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("diskCapacityViewDetailButton"), "Disk Capacity View details button not visible");
           //Check for toggle of Match Summary and Details for Incident KPI widget Feature.
			if (!toggleVerification(DashboardVariables.MATCH_WIDGET_DATA_TO_DETAILS, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS"))){
				softAssert.assertTrue(deviceHealthDetailsPage.columnHeadersTest(LanguageCode), "Report columns does not match");
			}else {
				softAssert.assertTrue(deviceHealthDetailsPage.columnHeadersTestNew(LanguageCode,"diskCapacityDataTileCount","diskCapacityViewDetailButton"), "Report count in details / columns does not match");
			}
			deviceHealthDetailsPage.switchBackToPreviousTab();
			LOGGER.info("Switched to Previous tab");
		}
		softAssert.assertAll();
		LOGGER.info("Validation of Disk Capacity completed");
	}

	@Test(priority = 4, groups = { "REGRESSION_CI", "REGRESSION" }, description = "TC:[906006]")
	public final void verifyThermalGradingChart() throws Exception {
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
		DeviceHealthDetailsPage deviceHealthDetailsPage = new DeviceHealthDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		Assert.assertTrue(toggleVerification(DeviceHealthVariables.DEVICEHEALTHTOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS")), "Toggle disabled for given user");
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		deviceHealthDetailsPage.mousehoverOnDeviceHealthDetailsPage("thermalGradeWidget");
		if (deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("thermalGradingNoDataTitle")){
			softAssert.assertTrue(deviceHealthDetailsPage.matchTextOfDeviceHealthDetailsPage("thermalGradingNoDataTitle", getTextLanguage(LanguageCode, "daas_ui", "dashboard.dhealth.action.tgra.nodata.title")));
			LOGGER.info("Data not present for Thermal Grading");
			softAssert.assertTrue(deviceHealthDetailsPage.matchTextOfDeviceHealthDetailsPage("thermalGradingNoDataDesc", getTextLanguage(LanguageCode, "daas_ui", "dashboard.dhealth.action.tgra.desc")), "Thermal Grading No data Description does not match");
			LOGGER.info("Verified no data elements");
		} else if (deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("thermalGradingDataTileTitle")){
			LOGGER.info("Data present for Thermal Grading");
			softAssert.assertTrue(deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("thermalGradingDataTileCount"), "Thermal Grading Count not visible");
			softAssert.assertTrue(deviceHealthDetailsPage.matchTextOfDeviceHealthDetailsPage("thermalGradingDataTileTitle", getTextLanguage(LanguageCode, "daas_ui", "dashboard.dhealth.action.tgra")), "Thermal Grading data Title does not match");
			Assert.assertTrue(deviceHealthDetailsPage.verifyElementsOfDeviceHealthDetailsPage("thermalGradingViewDetailButton"), "Thermal Grading View details button not visible");
			//Check for toggle of Match Summary and Details for Incident KPI widget Feature.
			if (!toggleVerification(DashboardVariables.MATCH_WIDGET_DATA_TO_DETAILS, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS"))){
				softAssert.assertTrue(deviceHealthDetailsPage.columnHeadersTest(LanguageCode), "Report columns does not match");
			}else {
				softAssert.assertTrue(deviceHealthDetailsPage.columnHeadersTestNew(LanguageCode,"thermalGradingDataTileCount","thermalGradingViewDetailButton"), "Report count in details / columns does not match");
			}
			deviceHealthDetailsPage.switchBackToPreviousTab();
			LOGGER.info("Switched to Previous tab");
		}
		softAssert.assertAll();
		LOGGER.info("Validation of Thermal Grading Chart completed");
	}
}
