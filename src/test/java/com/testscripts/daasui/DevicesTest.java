package com.testscripts.daasui;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import org.json.JSONObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CSVFileReader;
import com.basesource.utils.CompanyPin;
import com.basesource.utils.EnrollFakeDevice;
import com.basesource.utils.LaunchDarkly;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.DeviceVariables;
import com.daasui.constants.PreferenceVariables;
import com.daasui.pages.CompaniesDetailsPage;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.DeviceDetailsPage;
import com.daasui.pages.DeviceListPage;
import com.daasui.pages.ErrorPage;
import com.daasui.pages.IncidentPage;
import com.daasui.pages.LogPage;
import com.daasui.pages.PreferencesPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.TableConfigurationPage;
import com.daasui.pages.UserDetailsPage;
import com.daasui.pages.WelcomePage;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DevicesTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(DevicesTest.class);
	String numberOfDevices = null;
	int notificationCountUnread = 0;
	public static String deviceName = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_NAME");
	public static String staticModel = getEnvironmentSpecificData(System.getProperty("environment"), "STATIC_MODEL_NAME");

	public static String exportDeviceCompany = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_EXPORT_COMPANY");
	public static String deviceName_Manfacturedate = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICENAME_MANUFACTUREDATE");
	public static String device_Name = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_NAME_SUBSCRIPTION");
	public static String staticSerial = getEnvironmentSpecificData(System.getProperty("environment"), "STATIC_DEVICE_NAME");
	public static String deviceNameBios = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_NAME_BIOSDRIVER");
	public static String ldaDeviceName = getEnvironmentSpecificData(System.getProperty("environment"), "LDA_DEVICE_NAME");
	public static String automationDeviceName = getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_DEVICE_NAME");
	public static String deviceID = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_ID");
	public static String tenantID = getEnvironmentSpecificData(System.getProperty("environment"), "TENANT_ID");
	public static String tenantIDBios = getEnvironmentSpecificData(System.getProperty("environment"), "BIOS_TENANT_ID");
	public static String importCompany = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_IMPORT_COMPANY");
	public static String importDeviceCSVCompany = getEnvironmentSpecificData(System.getProperty("environment"), "IMPORT_DEVICE_COMPANY_CSV");
	public static String ztedeviceoa3hashinvalid = getEnvironmentSpecificData(System.getProperty("environment"), "ZTE_DEVICE_OA3_HASH_INVALID");
	public static String ztecompanytenantid = getEnvironmentSpecificData(System.getProperty("environment"), "ZTE_COMPANY_TENANT_ID");
	public static String ztedeviceoa3hashvalid = getEnvironmentSpecificData(System.getProperty("environment"), "ZTE_DEVICE_OA3_HASH_VALID");
	public static String deviceNameLastRestartDate = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_NAME_LAST_RESTART_DATE");
	public static String tenantIDRestartDate = getEnvironmentSpecificData(System.getProperty("environment"), "LAST_RESTART_DATE_ID");
	public static String subCompany = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICES_COUNT_MSPVIEW_COMPANY");
	public static String APP_NAME = getEnvironmentSpecificData(System.getProperty("environment"), "APP_NAME");
	public static final String securityBody = "{" + "\"device_Ids\"" + ":" + "[" + "\"" + deviceID + "\"" + "]" + "," + "\"deviceDetails\"" + ":" + "[" + "{" + "\"deviceId\"" + ":" + "\"" + deviceID + "\"" + "," + "\"eventName\"" + ":" + "[" + "\"AntiVirusStateChanged\"" + "," + "\"FirewallStateChanged\"" + "," + "\"bitlocker-status-event\"" + "," + "\"HDDEvent\"" + "]" + "}" + "]" + "}";
	public static final String HwInvBody = "{" + "\"tenants\"" + ":" + "[" + "\"" + tenantID + "\"" + "]" + "," + "\"filter\"" + ":" + "\"devicesn eq \\" + "\"" + deviceName + "\\" + "\"\"" + "}";
	public static final String BiosDriversBody = "{\"tenants\":[\"" + tenantIDBios + "\"],\"filter\":\"deviceSn eq \\\"" + deviceNameBios + "\\\"\"}";
	public static final String HwInvBios = "services/lighthouse/1.0/devices/" + deviceID + "/asset_details";
	public static final String LastLoggedInUser = "api/2.0/devices/" + deviceID;
	public static final String LastRestartDateBody = "{\"tenants\":[\"" + tenantIDRestartDate + "\"],\"filter\":\"deviceSn eq \\\"" + deviceNameLastRestartDate + "\\\"\"}";
	public static final String driversBody = "{\"tenants\":" + "[" + "\"" + tenantID + "\"" + "]" + "," + "\"filter\":\" (  (deviceSn eq \\" + "\"" + deviceName + "\\\") and ((driverStatus eq \\\"Outdated\\\") or (driverStatus eq \\\"Updated\\\") or (driverStatus eq \\\"Not Supported by HP\\\") or (driverStatus eq \\\"Driver Version Not Supported by HP\\\")) and ((latestDriverCriticality eq \\\"Critical\\\") or (latestDriverCriticality eq \\\"Recommended\\\") or (latestDriverCriticality eq \\\"Routine\\\") or (latestDriverCriticality eq \\\"Unknown\\\")) ) \"}";
	public ArrayList<String> customFields = new ArrayList<String>();
	public static String cataLystURL = getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL");
	public static String preferencesCompany = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_DETAILS_COMPANY");
	public static String snrcompanytenantid = getEnvironmentSpecificData(System.getProperty("environment"), "SNR_COMPANY_TENANT_ID");
	public static String multipleAssetAddComp = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME_DETAIL_PLANSLIST_RESELLER");
	public static String deviceNameBornOnDate = getEnvironmentSpecificData(System.getProperty("environment"), "DeviceName_BornOnDate");
	public static String tenantIDBornOnDate = getEnvironmentSpecificData(System.getProperty("environment"), "TenantID_BornOnDate");
	public static String bornOnDateDeviceId = getEnvironmentSpecificData(System.getProperty("environment"), "BORN_ON_DATE_DEVICEID");
	public static final String BornOnDateBody = "{" + "\"tenants\"" + ":" + "[" + "\"" + tenantIDBornOnDate + "\"" + "]" + "," + "\"filter\"" + ":" + "\"devicesn eq \\" + "\"" + deviceNameBornOnDate + "\\" + "\"\"" + "}";
	public static final String BornOnDeviceDateBody = "{" + "\"deviceId\"" + ":" + " " + "\"" + bornOnDateDeviceId + "\"" + "}";
	public static String deviceNameForNetworkError = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_FOR_NETWORKERROR");

	public static String staticLocation = getEnvironmentSpecificData(System.getProperty("environment"), "STATIC_LOCATION_NAME");
	public static String alias = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_ALIAS_SEARCH_VERIFY");
	public static String costCenter = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_COST_CENTER_SEARCH_VERIFY");

	@DataProvider
	public Object[][] loginDataListPage() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_ADMIN_US_EMAIl";
		data[0][1] = "MSP_ADMIN_US_PASSWORD";
		data[1][0] = "RESELLER_PARTNERS_EMAIL";
		data[1][1] = "RESELLER_PARTNERS_PASSWORD";
		return data;
	}

	@DataProvider
	public Object[][] loginDataForDevicesPendingEnrollment() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_ADMIN_US_EMAIl";
		data[0][1] = "MSP_ADMIN_US_PASSWORD";
		data[1][0] = "SERVICE_SPECIALIST_USER_LIST_EMAIL";
		data[1][1] = "SERVICE_SPECIALIST_USER_LIST_PASSWORD";
		return data;
	}

	@DataProvider
	public Object[][] loginDatarolesForDeviceGrouping() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_ADMIN_US_EMAIl";
		data[0][1] = "MSP_ADMIN_US_PASSWORD";
		data[1][0] = "LD_ADMIN_USERNAME";
		data[1][1] = "LD_ADMIN_PASSWORD";


		return data;
	}

	/**
	 * This method will verify device availability as per the environment
	 *
	 * @return boolean
	 */
	public final boolean VerifyDeviceAvailability() {
		try {
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			sleeper(3000);
			deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", deviceName);
			sleeper(3000);
			deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
			if (deviceListPage.verifyElementsOfDeviceListPage("deviceDetails")) {
				if (deviceName.equals(deviceListPage.getTextOfDeviceListPage("deviceDetails"))) {
					LOGGER.info("Device is available in the list");
					return true;
				} else {
					LOGGER.error("Selected device not present in the list");
					return false;
				}
			} else {
				LOGGER.error("Device List Empty");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception in VerifyDeviceAvailability" + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to delete the devices from device list page
	 * @param serialNumber - serialNumber of device which you want to delete
	 * @return - boolean value of whether the devices are removed successfully
	 * */
	public boolean verifyDeletionOfNewlyAddedDevice(String serialNumber) {
		try {
			boolean flag = false;
			waitForPageLoaded();
			sleeper(5000);
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			deviceListPage.waitForElementsOfDeviceListPage("clearFiltersButton");
			deviceListPage.clickOnElementsOfDeviceListPage("clearFiltersButton");
			deviceListPage.getElementOfDeviceListPage("serialNumberSearchBox").clear();
			deviceListPage.getElementOfDeviceListPage("serialNumberSearchBox").sendKeys(serialNumber);
			sleeper(5000);

			if (!deviceListPage.waitForElementsOfDeviceListPageDynamic("noElementsDisplayText", 5)) {
				deviceListPage.clickOnElementsOfDeviceListPage("serialNumberFirstRecord");
				sleeper(3000);
				String deviceID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
				if (getStatusCode(getEnvironment(System.getProperty("environment")) + DeviceVariables.SINGLE_DEVICE_REMOVE + deviceID, "{}", "DELETE", environment) == 204) {
					LOGGER.info("Removed added Device");
				    flag = true;
				}
				else {
					LOGGER.info("Device removal failed");
                    flag = false;
				}
			} else {
				LOGGER.info("Device list is already empty, There are no newly added devices");
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyDeletionOfNewlyAddedDevice " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method will verify the title of device page
	 *
	 * @throws Exception
	 */
	@Test(priority = 1, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI"}, description = "TC279363")
	public final void verifyDeviceTitle() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		sleeper(3000);
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			deviceListPage.waitForElementsOfDeviceListPage("devicesTitleOnBreadcrumbs");
			Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitleOnBreadcrumbs").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title text is incorrect");
			LOGGER.info("Devices title matched");
		} else {
			Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitle").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title text is incorrect");
			LOGGER.info("Devices title matched");
		}
	}

	/**
	 * This method will verify the table configuration test cases of device list page
	 *
	 * @throws Exception
	 */
	@Test(priority = 2, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "PRODUCTION_SANITY", "STABILIZATION_STAGING"}, description = "test case id:220951")
	public final void verifyTableConfigurationFunctionality() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode), "Error occured in setting dashboard to default mode.");
		gotoDevicesTab();
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, DeviceVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for device list page");
		ArrayList<String> serialNumberId = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.serial_number")));

		if (!toggleVerification(DeviceVariables.FLIP_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			ArrayList<String> columnName1 = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.serial_number"), getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptionPlan"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.alias_name"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.type"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.enrollment"),
					getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.company"), getTextLanguage(LanguageCode, "daas_ui", "assets.location.name"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.enrolled"), getTextLanguage(LanguageCode, "daas_ui", "global.form.status"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.asset_tag"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.bromium"),
					getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.byod"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.cost_center"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.department"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.manufacturer"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.model"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.name"),
					getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.operating_system"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.os_anguage"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.product"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.store_number"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.active_warranty"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.last_signed_user"),
					getTextLanguage(LanguageCode, "daas_ui", "asset_details_auto_update_expiration"), getTextLanguage(LanguageCode, "daas_ui", "devicelist.networkType.label"), getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.lifeCycleStatus.label"), getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.deviceSource.label"), getTextLanguage(LanguageCode, "daas_ui", "asset_details_sure_sense_pro"), getTextLanguage(LanguageCode, "daas_ui", "assets.details.open_incidents")));
			ArrayList<String> checkboxValue1 = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "false", "true", "true", "true", "true", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false"));
			verifyTableConfigurationTests(columnName1, checkboxValue1, serialNumberId);

		} else {
			ArrayList<String> columnName2 = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.serial_number"), getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptionPlan"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.alias_name"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.type"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.enrollment"),
					getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.company"), getTextLanguage(LanguageCode, "daas_ui", "assets.location.name"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.enrolled"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.lockAndErase"), getTextLanguage(LanguageCode, "daas_ui", "global.form.status"), getTextLanguage(LanguageCode, "daas_ui", "assets.details.location.last_seen"),
					getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.asset_tag"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.bromium"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.byod"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.cost_center"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.department"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.manufacturer"),
					getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.model"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.name"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.operating_system"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.os_anguage"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.product"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.store_number"),
					getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.active_warranty"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.last_signed_user"), getTextLanguage(LanguageCode, "daas_ui", "asset_details_auto_update_expiration"), getTextLanguage(LanguageCode, "daas_ui", "devicelist.networkType.label"), getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.lifeCycleStatus.label"), getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.deviceSource.label"),
					getTextLanguage(LanguageCode, "daas_ui", "asset_details_sure_sense_pro"), getTextLanguage(LanguageCode, "daas_ui", "assets.details.open_incidents")));
			ArrayList<String> checkboxValue2 = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "false", "true", "true", "true", "true", "true", "true", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false"));
			//Commenting the table verification code till the open Bug 521940 is resolved
			//verifyTableConfigurationTests(columnName2, checkboxValue2, serialNumberId);
		}
	}

	/**
	 * This method validate manual device add functionality
	 *
	 * @throws Exception
	 */
	@Test(priority = 3, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "PRODUCTION_SANITY", "STABILIZATION_STAGING", "PENTEST"}, description = "TC209658")
	public final void verifyAddDeviceManually() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode), "Error occured in setting dashboard to default mode.");
		if (deviceListPage.verifyElementsOfDeviceListPage("devicesTab"))
			gotoDevicesCompanyUserTab();
		else
			gotoDevicesTab();
		resetTableConfiguration();
		String manual_Device = generateRandomString(7);
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		 HashMap<String, String> mapAdded = DeviceListPage.mapAdded;
		// Assert.assertTrue(deviceListPage.verifyRemovalOfNewlyAddedDevice(LanguageCode), "Devices are not removed from list page.");
		Assert.assertTrue(deviceListPage.selectCompanyOfDevicePage(getEnvironmentSpecificData(System.getProperty("environment"), "NONENGLISHCOMPANY"), LanguageCode), "Company selection failed while adding device.");

		deviceListPage.clickOnElementsOfDeviceListPage("previousButton");
		// Verify Add Devices title on Add device pop up
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("addDevicesPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "assets.add.title")), "Title on Add device popup is incorrect");
		// verify choose company message on Add device pop up
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.select_company.label")), "Message on Add device popup is incorrect");
		deviceListPage.clickOnElementsOfDeviceListPage("nextButton");

		// Add single device
		deviceListPage.addSingleDevice(manual_Device, DeviceVariables.NO_ENROLLMENT);

		// check device-id of added device
		deviceListPage.goToParticularDevice(manual_Device);
		Assert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("serialNumber").equals(manual_Device), "Failed to add device");
		LOGGER.info("Validate serial number on device details page");

		String deviceID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		Assert.assertTrue(getStatusCode(getEnvironment(System.getProperty("environment")) + DeviceVariables.SINGLE_DEVICE_REMOVE + deviceID, "{}", "DELETE", environment) == 204, "Device removal failed");
		LOGGER.info("Removed added Device");

		gotoDevicesTab();
		Assert.assertTrue(deviceListPage.selectCompanyOfDevicePage(getEnvironmentSpecificData(System.getProperty("environment"), "NONENGLISHCOMPANY"), LanguageCode), "Company selection failed while adding device.");

		Assert.assertTrue(deviceListPage.verifyAddManuallyDevices(LanguageCode, 5, DeviceVariables.NO_ENROLLMENT), "Adding device manually has failed.");
		List<String> serialNumber = deviceListPage.getValueFromHashMap(mapAdded);
		Assert.assertTrue(deviceListPage.verifyDevicesOnListPage(serialNumber, 5), "Devices are not getting reflected back to the list page.");

		for (String serialNo : serialNumber) {
			verifyDeletionOfNewlyAddedDevice(serialNo);
			gotoDevicesTab();
		}
		softAssert.assertAll();
		LOGGER.info("Validation of Add device manually completed successfully.");
	}

	/**
	 * This method validate export device functionality
	 *
	 * @throws Exception
	 */
	@Test(priority = 4, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "STABILIZATION_STAGING"}, description = "test case id: 211439,211437")
	public final void verifyExportDevice() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		gotoDevicesTab();
		resetTableConfiguration();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		deviceListPage.selectCompanyOfDevicePage(exportDeviceCompany, LanguageCode);
		deviceListPage.clickOnElementsOfDeviceListPage("previousButton");
		// Verify Add Devices title on Add device pop up
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("addDevicesPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "assets.add.title")), "Title on Add device popup is incorrect");
		// verify choose company message on Add device pop up
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.select_company.label")), "Message on Add device popup is incorrect");
		deviceListPage.clickOnElementsOfDeviceListPage("nextButton");

		deviceListPage.verifyImportDevices(LanguageCode, DeviceVariables.DEVICESWITHCORRECTSERIALNUMBER_UPLOAD, DeviceVariables.NO_ENROLLMENT);
		Assert.assertTrue(deviceListPage.postNotificationCheckImportForSuccessfullImportInV3(DeviceVariables.DEVICESWITHCORRECTSERIALNUMBER_UPLOAD), "Notification for device import did not display/delay in notification for more than 30 seconds");
		LOGGER.info("Notification message verification for import has passed");
		deviceListPage.clickOnElementsOfDeviceListPage("clearListPageFilter");
		sleeper(3000);
		String devSrNo = deviceListPage.getDeviceSerialNo(DeviceVariables.DEVICESWITHCORRECTSERIALNUMBER_UPLOAD);
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", devSrNo);
		sleeper(4000);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.selectCompanyOnDeviceListPage(exportDeviceCompany);
		deviceListPage.selectFirstDeviceFromDeviceListPage(devSrNo);
		waitForPageLoaded();
		String deviceID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		navigateToBack();
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		deviceListPage.clickByJavaScriptOnDeviceListPage("selectAllCheckBox");
		LOGGER.info("Clicked on select all checkbox");
		deviceListPage.waitForElementsOfDeviceListPage("exportDeviceButton");
		deviceListPage.clickByJavaScriptOnDeviceListPage("exportDeviceButton");
		LOGGER.info("Clicked on export button");
		waitForPageLoaded();

		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("exportDevicesHeader", getTextLanguage(LanguageCode, "daas_ui", "assets.export.title")), "Export Device popup header is incorrect");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("exportDevicesInfo", getTextLanguage(LanguageCode, "daas_ui", "assets.export.column.label")), "Export Device popup information is incorrect");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("selectCurrentColumnHeader", getTextLanguage(LanguageCode, "daas_ui", "assets.export.selected.column.label")), "Export Device popup current selection of column header is incorrect");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("selectCurrentColumnHeaderInfo", getTextLanguage(LanguageCode, "daas_ui", "assets.export.selected.column.description")), "Export Device popup current selection of column header information is incorrect");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("selectAllColumnHeader", getTextLanguage(LanguageCode, "daas_ui", "assets.export.all.column.label")), "Export Device popup all selection of column header is incorrect");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("selectAllColumnHeaderInfo", getTextLanguage(LanguageCode, "daas_ui", "assets.export.all.column.description")), "Export Device popup all selection of column header information is incorrect");

		deviceListPage.clickByJavaScriptOnDeviceListPage("cancelExport");
		LOGGER.info("Click on cancel button");
		LOGGER.info("Export device Cancel funactionality completed successfully.");
		waitForPageLoaded();

		softAssert.assertTrue(deviceListPage.verifyExportDevices(LanguageCode, "1", notificationCountUnread), "Export functionality for selected devices got failed.");
		softAssert.assertTrue(deviceListPage.verifyExportAllDevices(LanguageCode, "1", notificationCountUnread + 1), "Export functionality for ALL devices got failed.");
		String deviceDeleteBody = "[{\"id\":\"" + deviceID + "\"}]";
		deviceListPage.deletZTEDeviceUsingAPI(getEnvironment(System.getProperty("environment")) + DeviceVariables.DEVICE_REMOVE, deviceDeleteBody);
		LOGGER.info("Removed added Device");

		softAssert.assertAll();
		LOGGER.info("Validation of Export Devices completed successfully.");
	}

	/*
	 * Test case is disabled as fake device enrollment is not using this functionality
	 */
	@Test(priority = 5, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI"}, description = "test case id:209658", enabled = false)
	public final void verifyFakeDeviceEnrollOnListPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		HashMap<String, String> companyDetailsforDeviceEnrollment = new HashMap<String, String>();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		try {
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "FAKE_DEVICE_ENROLL_COMPANY"));
			CompanyPin companypin = new CompanyPin().getInstance();
			companyDetailsforDeviceEnrollment.put("companyName", companiesPage.getTextOfCompaniesPage("companyNameDetailPage"));
			companyDetailsforDeviceEnrollment.put("companyEmailId", companiesPage.getTextOfCompaniesPage("companyEmailId"));
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
			companyDetailsforDeviceEnrollment.put("companyPin", companypin.generateCompanyPin(LanguageCode));
			softAssert.assertTrue(deviceListPage.verifyFakeDeviceListPage(companyDetailsforDeviceEnrollment, LanguageCode), "Unable to validate the Fake Device on Device list page.");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyFakeDeviceEnrollOnListPage : " + e.getMessage());
		}
		Assert.assertTrue(deviceListPage.selectCompanyOfDevicePage(companyDetailsforDeviceEnrollment.get("companyName"), LanguageCode), "Company selection failed while adding device.");
		Assert.assertTrue(deviceListPage.verifyHpDaaSDownloadClient(), "HP DaaS Client Download got failed.");
		softAssert.assertAll();
		LOGGER.info("Validation of Fake device enrollment completed successfully.");
	}

	/**
	 * This test validates location tile information
	 *
	 * @throws Exception
	 */
	@Test(priority = 6, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "217606")
	public final void verifyLocationTile() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Navigate to device list page");
		resetTableConfiguration();
		waitForPageLoaded();
		ArrayList<String> deviceDetailInfo = deviceListPage.getAllDeviceInfo(deviceName);
		sleeper(2000);

		// Navigate to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "EnrollmentTile_DEVICE_ID"));
		waitForPageLoaded();
		//deviceDetailsPage.scrollOndeviceDetailsPage("locationTab");		
		//deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("locationTab");
		LOGGER.info("Navigate to Location tab");

		// Verify location tile header
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("locationHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details_location")), "Location Header does not match");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("descriptionLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.location.name")), "Location tile asset location field missing");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("descriptionValue")), "Asset location value mismatch on list and details page");
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_ST_EMAIl_01"))) {
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("modalLocationLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details.location.geolocation")), "Location tile Geolocation field missing");
		} else {
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("modalLocationLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.location")), "Location field missing under location tile");
			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("descriptionEditIcon"), "Edit button missing against location tile");
		}
		softAssert.assertAll();
	}

	/**
	 * This test validates preferences tile information under device details page. marking this as false, locally failing assigned autobug to radhika
	 *
	 * @throws Exception
	 */

	@Test(priority = 7, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI", "STABILIZATION_STAGING"}, description = "NFR 163325, TC 341264")
	public final void verifyPreferencesTile() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> lifecycleFields = new ArrayList<String>();
		String LIFECYCLE_STATUS_FIELD_ONE = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_TWO = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_THREE = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_FOUR = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_FIVE = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_SIX = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_SEVEN = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_EIGHT = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_NINE = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_TEN = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_ELEVEN = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_TWELVE = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_THIRTEEN = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_FOURTEEN = generateRandomString(11);
		String LIFECYCLE_STATUS_FIELD_FIFTEEN = generateRandomString(11);
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_ONE.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_TWO.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_THREE.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_FOUR.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_FIVE.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_SIX.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_SEVEN.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_EIGHT.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_NINE.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_TEN.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_ELEVEN.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_TWELVE.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_THIRTEEN.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_FOURTEEN.toLowerCase());
		lifecycleFields.add(LIFECYCLE_STATUS_FIELD_FIFTEEN.toLowerCase());
		ArrayList<String> deviceRoleOptions = new ArrayList<>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.deviceSource.primary").toLowerCase(), getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.deviceSource.secondary").toLowerCase()));
		Collections.sort(lifecycleFields);

		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");

		resetTableConfiguration();

		// Check device is present in the list
		//Assert.assertTrue(VerifyDeviceAvailability(), "Selected device not present in the list, cannot proceed further");
		//LOGGER.info("Selected device is available in the list");

		// Redirected to device details page
		deviceListPage.goToParticularDevice(deviceName);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
		waitForPageLoaded();
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("preferencesTileHeader");
		deviceDetailsPage.scrollOndeviceDetailsPage("preferencesTileHeader");
		// Verify preferences tile header
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("preferencesTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.preferences")), "Preferences tile header did not match with actual one");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("preferencesField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details_byod")), "Preferences tile field title did not match with actual one");
		LOGGER.info("Verified preferences tile headers");
		deviceDetailsPage.scrollOndeviceDetailsPage("preftoggleButton");
		waitForPageLoaded();
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("lastRestartDate");
		LOGGER.info("Before toggle ON :" + deviceDetailsPage.getTextOfDeviceDetailsPage("perferencesStatus"));

		int attempt = 0;
		// Toggle ON
		while (deviceDetailsPage.getTextOfDeviceDetailsPage("perferencesStatus").equals("No") && attempt < 20) {
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("preftoggleButton");
			sleeper(2000);
			LOGGER.info("TOGGLE ON BYOD Attemp: " + attempt);
			LOGGER.info("Current status of toggle :" + deviceDetailsPage.getAttributesOfDeviceDetailsPage("toggleStatus", "aria-checked"));
			attempt++;
		}

		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("perferencesStatus").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details.byod.positive")), "Preferences status is not YES");
		deviceDetailsPage.scrollToTop();
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("softwareTab");
		sleeper(5000);
		Assert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("softwareInventory"), "Inventory tile is visible when toggle is on");
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("deviceInformationTab");
		waitForPageLoaded();
		LOGGER.info("Verified updated Bring Your Own Device(BYOD) toggle status");
		attempt = 0;
		deviceDetailsPage.scrollOndeviceDetailsPage("preftoggleButton");
		waitForPageLoaded();
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("lastRestartDate");
		LOGGER.info("Before toggle off :" + deviceDetailsPage.getTextOfDeviceDetailsPage("perferencesStatus"));
		sleeper(10000);
		// Toggle OFF
		while (deviceDetailsPage.getTextOfDeviceDetailsPage("perferencesStatus").equals("Yes") && attempt < 20) {
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("preftoggleButton");
			sleeper(2000);
			LOGGER.info("TOGGLE OFF BYOD Attempt: " + attempt);
			LOGGER.info("Current status of toggle :" + deviceDetailsPage.getAttributesOfDeviceDetailsPage("toggleStatus", "aria-checked"));

			attempt++;
		}


		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("perferencesStatus").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details.byod.negative")), "Preferences status is not NO");
		deviceDetailsPage.scrollToTop();
		waitForPageLoaded();
		sleeper(2000);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("softwareTab");
		waitForPageLoaded();
		sleeper(5000);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("lastRestartDate");
		//check inventory tile must present
		scrollToBottom();
		Assert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("softwareInventory"), "Inventory tile is not visible when toggle is off");
		LOGGER.info("Verified updated Bring Your Own Device(BYOD) toggle status");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("tableOverlay");
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("deviceInformationTab");
		waitForPageLoaded();
		// Verify device role field
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceRoleLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.deviceSource.label")), "Device Role label under preferences tile is incorrect");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceRoleEditButton"), "Edit button for device role field is not present under preferences tile of device details page");
		deviceDetailsPage.scrollOndeviceDetailsPage("deviceRoleEditButton");
		sleeper(2000);
		attempt = 0;
		String currentDeviceRole = "";
		do {
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("deviceRoleEditButton");
			waitForPageLoaded();
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceRolePopupTitle");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceRolePopupTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.deviceSource.label")), "Title on device role popup is incorrect");
			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceRoleSave"), "Save button for device role field is not present under preferences tile of device details page");
			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceRoleCancel"), "Cancel button for device role field is not present under preferences tile of device details page");
			currentDeviceRole = deviceDetailsPage.getTextOfDeviceDetailsPage("deviceRolePopupDropdown");
			LOGGER.info("Current device role :" + currentDeviceRole);
			if ((currentDeviceRole == null || currentDeviceRole.isEmpty())) {
				deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("");
			} else {
				break;
			}
			sleeper(1000);
			attempt++;
		} while (attempt < 10);

		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceRolePopupDropdown");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("deviceRolePopupDropdown");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceRoleDropdownOptions");
		sleeper(1000);
		softAssert.assertEquals(deviceDetailsPage.getTextOfColumns("deviceRoleDropdownOptions"), deviceRoleOptions, "Options under device role dropdown are incorrect");
		String selectedDeviceRole = deviceDetailsPage.selectValueOnDropDownOfDeviceDetailsPage("deviceRoleDropdownOptions", currentDeviceRole);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("deviceRoleSave");
		sleeper(2000);
		waitForPageLoaded();
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("currentDeviceRole"), selectedDeviceRole, "Edit device role functionality is not working");

		// Verify life cycle status field
		gotoCompaniesTab();
		waitForPageLoaded();
		impersonateCompanyByCompanyName(preferencesCompany);
		LOGGER.info("Redirected to companies details page");
		goToPreferencesTab();
		LOGGER.info("Navigated to preferences tab");
		sleeper(2000);
		companyDetailsPage.scrollOnCompaniesDetailsPage("customFieldsTitle");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("lifecycleStatusLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.lifeCycleStatus.label")), "Title on lifecycle status fields tile is incorrect");
		//companyDetailsPage.scrollOnCompaniesDetailsPage("lifecycleStatusEditButton");
		sleeper(2000);
		if (!companyDetailsPage.getTextOfCompaniesDetailsPage("lifecycleStatusValues").equals(CompaniesVariables.CUSTOM_FIELDS_NOT_CONFIGURED)) {
			companyDetailsPage.scrollOnCompaniesDetailsPage("customFieldsTitle");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("lifecycleStatusEditButton");
			List<WebElement> uiList = companyDetailsPage.getElementsOfCompanyDetailsPage("lifecycleDeleteButton");
			companyDetailsPage.clickElementsOfCompanyDetailsPage(uiList);
			waitForPageLoaded();
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveLifecycleField");
			//companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			Assert.assertTrue(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification"), "Already existing lifecycle status fields were not removed successfully, cannot proceed further : TEST CASE 280877");
			LOGGER.info("Already existing lifecycle fields removed successfully");
		} else {
			LOGGER.info("No existing lifecycle fields fields present");
		}

		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
		companyDetailsPage.scrollOnCompaniesDetailsPage("customFieldsTitle");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("lifecycleStatusEditButton");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("lifecycleTitleOnPopup", getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.lifeCycleStatus.label")), "Title on lifecycle status fields popup is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("lifecycleDescriptionOnPopup", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.lifecycle_status.popup.subtitle")), "Subtitle on lifecycle status fields popup is incorrect");
		companyDetailsPage.enterTextForCompaniesDetailsPage("lifecycleStatusFirstTextbox", LIFECYCLE_STATUS_FIELD_ONE);
		companyDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("addMoreFieldsLinkLifecycle");
		companyDetailsPage.enterTextForCompaniesDetailsPage("lifecycleStatusSecondTextbox", LIFECYCLE_STATUS_FIELD_TWO);
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("cancelLifecycleField");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("cancelLifecycleField");
		waitForPageLoaded();
		LOGGER.info("Clicked on cancel button of lifecycle status fields popup");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("lifecycleStatusValues", CompaniesVariables.CUSTOM_FIELDS_NOT_CONFIGURED), "Cancel functionality on lifecycle status fields popup is not working correctly");

		companyDetailsPage.addAllLifecycleStatusFields("lifecycleStatusEditButton", "addMoreFieldsLinkLifecycle", "saveLifecycleField", "lifecycleStatusFirstTextbox", "lifecycleStatusSecondTextbox", "lifecycleStatusThirdTextbox", "lifecycleStatusFourthTextbox", "lifecycleStatusFifthTextbox", "lifecycleStatusSixthTextbox", "lifecycleStatusSeventhTextbox", "lifecycleStatusEighthTextbox", "lifecycleStatusNinthTextbox", "lifecycleStatusTenthTextbox", "lifecycleStatusEleventhTextbox",
				"lifecycleStatusTwelvethTextbox", "lifecycleStatusThirteenthTextbox", "lifecycleStatusFourteenthTextbox", "lifecycleStatusFifteenthTextbox", LIFECYCLE_STATUS_FIELD_ONE, LIFECYCLE_STATUS_FIELD_TWO, LIFECYCLE_STATUS_FIELD_THREE, LIFECYCLE_STATUS_FIELD_FOUR, LIFECYCLE_STATUS_FIELD_FIVE, LIFECYCLE_STATUS_FIELD_SIX, LIFECYCLE_STATUS_FIELD_SEVEN, LIFECYCLE_STATUS_FIELD_EIGHT, LIFECYCLE_STATUS_FIELD_NINE, LIFECYCLE_STATUS_FIELD_TEN, LIFECYCLE_STATUS_FIELD_ELEVEN,
				LIFECYCLE_STATUS_FIELD_TWELVE, LIFECYCLE_STATUS_FIELD_THIRTEEN, LIFECYCLE_STATUS_FIELD_FOURTEEN, LIFECYCLE_STATUS_FIELD_FIFTEEN);
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Save functionality on custom fields popup is not working correctly : TEST CASE 280877");
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");

		resetTableConfiguration();

		// Redirected to device details page
		deviceListPage.goToParticularDevice(deviceName);
		deviceDetailsPage.scrollOndeviceDetailsPage("viewHistoryButton");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("lifecycleStatusedit"), "Edit button for lifecycle status field is not present under preferences tile of device details page");
		sleeper(2000);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("lifecycleStatusedit");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("lifecyclePopupTitle");
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("lifecyclePopupTitle"), getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.lifeCycleStatus.label"), "Title on lifecycle status popup is incorrect");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("lifecycleSave"), "Save button for lifecycle status field is not present under preferences tile of device details page");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("lifecycleCancel"), "Cancel button for lifecycle status field is not present under preferences tile of device details page");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("lifeCycleStatusDropDown");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("lifeCycleStatusDropDown");
		softAssert.assertEquals(deviceDetailsPage.getTextOfColumns("lifeCycleStatusDropDownOptions"), lifecycleFields, "Options under lifecycle status dropdown are incorrect");
		String selectedLifecycleStatus = deviceDetailsPage.selectValueOnDropDownOfDeviceDetailsPage("lifeCycleStatusDropDownOptions", lifecycleFields.get(2));
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("lifecycleSave");
		waitForPageLoaded();
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("currentLifecycleStatus");
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("currentLifecycleStatus"), selectedLifecycleStatus, "Edit lifecycle status functionality is not working");
		softAssert.assertAll();
		LOGGER.info("Test case to verify Preferences tile passed successfully.");
	}

	/**
	 * This test will verify system tile on device details page
	 * <p>
	 * throws Exception
	 */
	@Test(priority = 8, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "TC217627")
	public final void verifySystemTileOnDeviceDetailsPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");

		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		deviceListPage.selectAllCheckboxOfPopupForDevicelistPage();
		// Check device is present in the list
		ArrayList<String> deviceDetailInfo = deviceListPage.getAllDeviceInfo(deviceName_Manfacturedate);
		sleeper(1000);
		//deviceListPage.goToParticularDevice(deviceName_Manfacturedate);
		// Navigate to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "EnrollmentTile_DEVICE_ID"));

		// Navigate to device details page
		sleeper(2000);
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceBreadCrumbs"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.asset_details"), "Breadcrumb did not match");

		// Click on system tile
		deviceDetailsPage.scrollOndeviceDetailsPage("systemTile");
		sleeper(1000);
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("systemHeader").equalsIgnoreCase((deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.general_informations.system"))), "System tile header did not match");

		// Verify manufacturer,product,model,os value with device list page values
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("manufacturer")), "manufacturer name is different  on detail page than list page");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("product")), "product is different on detail page than list page");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("model")), "model is different on detail page than list page");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("type")), "type is different on detail page than list page");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("os").split("[\\(\\)]")[0].trim()), "Operating system is different on detail page than list page");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("bornOnDateLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details_bornOnDate")), "Born on date field missing on device details page");

		if ((deviceDetailsPage.getTextOfDeviceDetailsPage("os").split("[\\(\\)]")[0].trim()).substring(0, 9).equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.device_os.chromeOS")))
			softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("aue")), "Auto Updation Expiration date is different on detail page than list page");
		else
			softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("aue"), "Auto Update Expiration field is available for non Chrome devices");

		// Verify device type list
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
		RestAssured.baseURI = getEnvironment(System.getProperty("environment"));
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken);
		Response response = httpRequest.get(DeviceVariables.UNIQUEDEVICETYPEAPI);
		Assert.assertEquals(response.statusCode(), CommonVariables.CODEOK, "deviceType Api status code not matched");

		ArrayList<String> deviceTypeListLabelsWiaApi = new ArrayList<>(Arrays.asList(response.jsonPath().getString("").toLowerCase().replaceAll("\\]|\\[|\\s+|-|_", "").trim().split(",")));
		ArrayList<String> arrayList = new ArrayList<String>();
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editButtonType");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("dropDownButton");
		sleeper(2000);//Dropdown is not getting opened due to some UI issue.
		List<WebElement> deviceTypeList = deviceDetailsPage.getElementsOfDeviceDetailsPage("dropDownList");
		for (WebElement ls : deviceTypeList) {
			arrayList.add(ls.getText().toLowerCase().replaceAll("\\]|\\[|\\s+|-|_", "").toString());
		}
		softAssert.assertTrue(deviceTypeListLabelsWiaApi.equals(arrayList), "Device type list did not match with the actual list");
		sleeper(2000);
		deviceTypeList.get(2).click();
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("deviceTypeCancel");
		String typeValue = deviceDetailsPage.dropDownValueCheck(deviceDetailsPage.getTextOfDeviceDetailsPage("dropDownValue"));

		// Verify current device type value with value present in drop down text box
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editButtonType");
		sleeper(2000);
		softAssert.assertEquals(typeValue, deviceDetailsPage.getTextOfDeviceDetailsPage("dropDownBoxText"), "Current device type value did not match with value present in dropdown textbox");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("deviceTypeCancel");
		// Set device type value
		deviceDetailsPage.setDropdownValue(typeValue, "deviceTypeSave", LanguageCode);
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("closeToastNotification");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceTypeDevDetails");
		sleeper(2000);
		deviceDetailsPage.setDropdownValue(typeValue, "deviceTypeCancel", LanguageCode);
		sleeper(3000);
		String deviceType = deviceDetailsPage.getTextOfDeviceDetailsPage("deviceTypeDevDetails");
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceTypeDevDetails"), deviceType, "device type did not match with original value");

		// verify cancel functionality of manufacturing date
		if (deviceDetailsPage.getTextOfDeviceDetailsPage("manufacturingDate").equals("-")) {
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editManufacturingDateButton");
			softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("selectedDate"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "global.select_date"));
			deviceDetailsPage.setDropdownValue(typeValue, "deviceTypeCancel", LanguageCode);
			LOGGER.info("Date not present");
		} else {
			String formattedDate = null;
			String actualDate = deviceDetailsPage.getTextOfDeviceDetailsPage("manufacturingDate");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editManufacturingDateButton");
			String selectedDate = deviceDetailsPage.getAttributesOfDeviceDetailsPage("selectedDate", "value");
			formattedDate = actualDate;
			softAssert.assertEquals(selectedDate, formattedDate, "Actual date and date on pop up does not match");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("dateButton");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("selectDate");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelDateModel");
			sleeper(3000);
			if (deviceDetailsPage.getTextOfDeviceDetailsPage("manufacturingDate").equals(actualDate)) {
				LOGGER.info("Date did not got changed after clicking on cancel button");

			} else {
				softAssert.fail("Date got changed even after clicking on cancel button");
			}
		}

		// verify edit functionality of manufacturing date
		sleeper(2000);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editManufacturingDateButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("dateButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("selectDate");
		String selectedDate = deviceDetailsPage.getAttributesOfDeviceDetailsPage("selectedDate", "value");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("saveDateModel");
		String formattedDate = null;
		formattedDate = selectedDate;
		if (deviceDetailsPage.getTextOfDeviceDetailsPage("manufacturingDate").equals(formattedDate)) {
			LOGGER.info("Edited date on detail page get successfully matched");
		} else {
			softAssert.fail("Edited date on detail page did not matched");
		}

		// Verify OS version
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("os").contains(getDataFromApi("operatingSystem", getEnvironment(System.getProperty("environment")) + LastLoggedInUser)), "Device operating system did not match");
		LOGGER.info("Verified operating system version");

		// Verify device type value with device list page value
		expandMenuIcon();
		gotoDevicesTab();
		deviceListPage.waitForElementsOfDeviceListPage("serialNumberSearchBox");
		sleeper(5000);
		//resetTableConfiguration();
		//deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", deviceName_Manfacturedate);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.scrollTillViewDeviceListPage("deviceTypeValue");
		softAssert.assertEquals(deviceListPage.getTextOfDeviceListPage("deviceTypeValue"), deviceType, "device type did not match with device list page value");
		softAssert.assertAll();
	}

	/**
	 * This test will verify profile tile on device details page
	 * <p>
	 * throws Exception
	 */
	@Test(priority = 9, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI", "STABILIZATION_STAGING"}, description = "279363")
	public final void verifyProfileTileOnDeviceDetailPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage devicePage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		devicePage.selectAllCheckboxOfPopupForDevicelistPage();
		//devicePage.waitForElementsOfDeviceListPage("tableOverlay");
		devicePage.waitForElementsOfDeviceListPage("selectAllCheckBox");

		// Check device is present in the list
		//Assert.assertTrue(VerifyDeviceAvailability(), "Selected device not present in the list");

		ArrayList<String> deviceDetailInfo = devicePage.getAllDeviceInfo(deviceName);
		//devicePage.waitForElementsOfDeviceListPage("tableOverlay");

		// Navigate to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "UPDATED_DEVICE_ID"));
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceBreadCrumbs"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.asset_details"), "Breadcrumb on device detials page does not match");
		deviceDetailsPage.scrollOndeviceDetailsPage("user");
		// Verify all the device info in identification tile on detail page
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("aliasName")), "alias name is different on detail page than list page");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceName")), "device name is different on detail page than list page");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("serialNumber")), "serial number is different on detail page than list page");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("assetTag")), "asset tag is different on detail page than list page");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("costCenter")), "cost center is different on detail page than list page");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("company")), "company name is different on detail page than list page");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("user")), "Enrollment user name is different on detail page than list page");
		// verify company link on details page
		if (deviceDetailsPage.getTextOfDeviceDetailsPage("company").equals("-")) {
			LOGGER.info("Company is not present");
		} else {
			deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("company");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("companiesBreadcrumb2");
			companyDetailsPage.scrollOnCompaniesDetailsPage("companyName");
			sleeper(3000);
			softAssert.assertTrue(deviceDetailInfo.contains(companyDetailsPage.getTextOfCompaniesDetailsPage("companyName")), "not redirected to particular company");
			LOGGER.info("Companies title is matched");
			getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "UPDATED_DEVICE_ID"));
			softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceBreadCrumbs"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.asset_details"), "Breadcrumb on device detials page does not match");
		}

		if (deviceDetailsPage.getTextOfDeviceDetailsPage("user").equals("-")) {
			LOGGER.info("User is not present");
		} else {
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("user");
			sleeper(3000);
			userDetailsPage.scrollOnUserPage("nameValue");
			softAssert.assertTrue(deviceDetailInfo.contains(userDetailsPage.getTextOfUserDetailsPage("nameValue")), "not redirected to particular user");
			LOGGER.info("Users title is matched");
			getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "UPDATED_DEVICE_ID"));
			softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceBreadCrumbs"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.asset_details"), "Breadcrumb on device detials page does not match");
		}

		// Edit alias button functionality
		String aliasName = generateRandomString(11);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editAliasButton");
		deviceDetailsPage.enterTextForDeviceDetailsPage("editBox", generateRandomString(11));
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelButtonOfPopUp");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("aliasName")), "alias name changed even after pressing cancel button");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editAliasButton");
		deviceDetailsPage.enterTextForDeviceDetailsPage("editBox", generateRandomString(11));
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelButtonOfPopUp");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("aliasName")), "alias name changed even after pressing cancel button");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editAliasButton");
		deviceDetailsPage.enterTextForDeviceDetailsPage("editBox", aliasName);
		String aliasNameOnPopup = deviceDetailsPage.getAttributesOfDeviceDetailsPage("aliasNameOnPopup", "value");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("saveButtonOfPopUp");
		sleeper(3000);

		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("aliasName").equalsIgnoreCase(aliasNameOnPopup), "alias name remained unchanged even after pressing save button");
		// Edit asset tag button functionality
		softAssert.assertFalse(deviceDetailsPage.verifyElementIsClickableOfDeviceDetailsPage("saveButtonOfPopUp"));
		String aliasTag = generateRandomString(11);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editTagButton");
		deviceDetailsPage.enterTextForDeviceDetailsPage("editBox", generateRandomString(11));
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelButtonOfPopUp");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("assetTag")), "asset tag changed even after pressing cancel button");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editAliasButton");
		deviceDetailsPage.enterTextForDeviceDetailsPage("editBox", generateRandomString(11));
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelButtonOfPopUp");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("assetTag")), "asset tag changed even after pressing cancel button");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editTagButton");
		deviceDetailsPage.enterTextForDeviceDetailsPage("editBox", aliasTag);
		sleeper(2000);
		String aliasTagOnPopup = deviceDetailsPage.getAttributesOfDeviceDetailsPage("aliasTagOnPopup", "value");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("saveButtonOfPopUp");
		sleeper(3000);
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("assetTag").equalsIgnoreCase(aliasTagOnPopup), "alias tag remained unchanged even after pressing save button");

		// Edit cost center button functionality
		softAssert.assertFalse(deviceDetailsPage.verifyElementIsClickableOfDeviceDetailsPage("saveButtonOfPopUp"));
		String costCentre = generateRandomString(11);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editCostButton");
		deviceDetailsPage.enterTextForDeviceDetailsPage("editBox", generateRandomString(11));
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelButtonOfPopUp");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("costCenter")), "cost center changed even after pressing cancel button");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editAliasButton");
		deviceDetailsPage.enterTextForDeviceDetailsPage("editBox", generateRandomString(11));
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelButtonOfPopUp");
		softAssert.assertTrue(deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("costCenter")), "cost center changed even after pressing cancel button");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editCostButton");
		deviceDetailsPage.enterTextForDeviceDetailsPage("editBox", costCentre);
		String costCenterOnPopup = deviceDetailsPage.getAttributesOfDeviceDetailsPage("costCenterOnPopup", "value");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("saveButtonOfPopUp");
		sleeper(3000);
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("costCenter").equalsIgnoreCase(costCenterOnPopup), "cost center remained unchanged even after pressing save button");

		// Verify last signed in username info
		deviceDetailsPage.scrollOndeviceDetailsPage("LastSignedInUsername");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("LastSignedInUsernameLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.last_signed_user")), "Last signed in usernamelabel text did not match");
		if (!(deviceDetailsPage.getTextOfDeviceDetailsPage("enrollmentName")).equals(getTextLanguage(LanguageCode, "daas_ui", "asset_details_not_enrolled"))) {
			softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("LastSignedInUsername", getDataFromApi("lastLoggedUser", getEnvironment(System.getProperty("environment")) + LastLoggedInUser)), "Last signed in username did not match");
			LOGGER.info("Match last signed in username info with API response");
		} else {
			LOGGER.info("Selected device is not enrolled or device is fake");
		}

		// Navigate back to devices tab
		expandMenuIcon();
		gotoDevicesTab();
		devicePage.selectAllCheckboxOfPopupForDevicelistPage();

		deviceDetailInfo = devicePage.getAllDeviceInfo(deviceName);
		//devicePage.waitForElementsOfDeviceListPage("tableOverlay");

		// Navigate to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "UPDATED_DEVICE_ID"));
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "UPDATED_DEVICE_ID"));
		waitForPageLoaded();
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("aliasName").equals(aliasName), "asset name does not get changed even after editing it from device detail page");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("assetTag").equals(aliasTag), "asset tag does not get changed even after editing it from device detail page");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("costCenter").equals(costCentre), "cost center does not get changed even after editing it from device detail page");
		softAssert.assertAll();
	}

	/**
	 * This test will verify enrollment tile on device details page.
	 *
	 * @throws Exception
	 */
	@Test(priority = 10, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "217618 , UserStory:[472987]")
	public final void verifyEnrollmentTileOnDeviceDetailPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");

		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage devicePage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		resetTableConfiguration();

		// Check device is present in the list
		ArrayList<String> deviceDetailInfo = devicePage.getAllDeviceInfo(deviceName);

		// Navigate to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "EnrollmentTile_DEVICE_ID"));

		// Scroll down to enrollment tile
		deviceDetailsPage.scrollOndeviceDetailsPage("enrollmentTile");
		waitForPageLoaded();
		LOGGER.info("Scrolled down to enrollment tile");

		if (deviceDetailInfo.contains(deviceDetailsPage.getTextOfDeviceDetailsPage("enrollmentName"))) {
			LOGGER.info("List of enroll devices on list page and detail page matches successfully");
		} else {
			softAssert.fail("List of enroll devices on list page and detail page did not match");
		}

		// Click on view history button
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("viewHistoryButton").equals(getTextLanguage(LanguageCode, "daas_ui", "assets.details.general_informations.enrollment.button.view_history")), "View history button text did not match");
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("viewHistoryButton");
		LOGGER.info("Clicked on view history button");
		sleeper(2000);

		// Verify logs page redirection
		waitForPageLoaded();
		Assert.assertTrue(logPage.matchTextOfLogPage("logTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs page navigation failed");
		LOGGER.info("Redirected to logs page");

		// Verify device name,type and sub type filter text
		softAssert.assertTrue(logPage.matchTextOfLogPage("typeBoxText", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Selected Type column filter is not Devices");
		softAssert.assertTrue(logPage.matchTextOfLogPage("subtypeBoxText", getTextLanguage(LanguageCode, "daas_ui", "assets.add.enroll.label")), "Selected Subtype column filter is not Enrollment");
		LOGGER.info("Verified device name,type and sub type filter text on logs page");
		logout();

		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		gotoDevicesTab();
		resetTableConfiguration();
		devicePage.waitForElementsOfDeviceListPage("tableOverlay");

		// Navigate to device details page
		devicePage.goToParticularDevice(device_Name);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
		waitForPageLoaded();

		// Scroll down to enrollment tile
		deviceDetailsPage.scrollOndeviceDetailsPage("enrollmentTile");
		LOGGER.info("Scrolled down to Enrollment Tile");
		ArrayList<String> enrollPlans = deviceDetailsPage.getEnrollPlans();
		int listSize = enrollPlans.size();
		if (listSize > 2) {
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editButtonIcon");
			int planList = deviceDetailsPage.getNumberOfPlans("noOfPlan");
			if (planList < 2) {
				deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("addPlanLink");
				deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("enrollmentDropDown");
				deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("enrollmentDropDownList");
				deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("enrollmentSave");
				deviceDetailsPage.verifyElementsOfDeviceDetailsPage("successMessage");
				softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("successMessage").equals((deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "assets.plan.update.success.single"))), "Plan has not been Updated");
			} else {
				deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("deletePlan");
				deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("enrollmentSave");
				deviceDetailsPage.verifyElementsOfDeviceDetailsPage("successMessage");
				softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("successMessage").equals((deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "assets.plan.update.success.single"))), "Plan has not been Updated");
				waitForPageLoaded();
			}
		} else
			LOGGER.info("Device is attached to only one Plan");

		softAssert.assertAll();

	}

	/**
	 * This test will verify remove functionality on device details page User Story 380464: [DaaS][UI] Implement additional step/process for critical delete functions in TechPulse
	 *
	 * @throws Exception
	 */
	@Test(priority = 11, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "306986", enabled = false)
	public final void verifyRemoveFunctionalityOnDeviceDetailPage() throws Exception {
		login("MSP_DEVICE_LIST_EMAIL", "MSP_DEVICE_LIST_PASSWORD");
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage devicePage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		resetTableConfiguration();

		// Select a company and add a device manually
		Assert.assertTrue(devicePage.selectCompanyOfDevicePage(importCompany, LanguageCode), "Company selection failed while adding device.");
		String serialNumber = generateRandomString(11);
		devicePage.addSingleDevice(serialNumber, DeviceVariables.NO_ENROLLMENT);
		devicePage.waitForElementsOfDeviceListPage("tableOverlay");
		devicePage.goToParticularDevice(serialNumber);

		waitForPageLoaded();

		// Cancel button functionality
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("removeButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelButtonOfPopUp");
		sleeper(2000);// time required to close the pop up

		// Cross button functionality
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("removeButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("XButtonOfPopUp");
		sleeper(2000);// time required to close the pop up

		// Remove button functionality
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("removeButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("removeButtonOfPopUp");
		softAssert.assertFalse(deviceDetailsPage.verifyElementIsClickableOfDeviceDetailsPage("removeButtonOfPopUp"));
		softAssert.assertTrue(devicePage.matchTextOfDeviceListPage("devicesTitle", DeviceVariables.DEVICES_TITLE), "Device list page navigation failed");
		LOGGER.info("Navigated to device page");

		// Search serial number
		devicePage.enterTextForDeviceListPage("serialNumberSearchBox", serialNumber);
		devicePage.waitForElementsOfDeviceListPage("tableOverlay");
		if (devicePage.verifyElementsOfDeviceListPage("deviceName") == true) {
			if (devicePage.getTextOfDeviceListPage("deviceName").equals(serialNumber)) {
				Assert.fail("Device removed on detail page is still visible on device list page");
			} else {
				LOGGER.info("Device removed on detail page is also removed from device list page");
			}
		} else {
			LOGGER.info("Device removed on detail page is also removed from device list page");
		}

		softAssert.assertAll();
	}

	/**
	 * This method verifies the button OPEN IN EMM TOOL and it's redirection
	 *
	 * @throws IOException
	 */
	@Test(priority = 12, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "Testcases 211212,442560", enabled = false)
	public final void verifyDeepLinkingOfEmmEnrolledDevice() throws IOException {
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		try {
			// Login to MSP account
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			// Remove Intune Configuration
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(PreferencesTest.emmCompanyName);
			Assert.assertTrue(preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_INTUNE, "intuneIntegrationDeleteButton", PreferencesTest.emmCompanyId), "Error occured while removing microsoft intune on Prefernce Page");
			Assert.assertTrue(preferencesPage.removeEmmUser(LanguageCode, PreferencesTest.emmCompanyName, PreferencesTest.intunehUserName, getEnvironment(environment)), "Error occured while removing intune user on User List Page");

			gotoCompaniesTab();
			waitForPageLoaded();

			// Impersonating into a company
			impersonateCompanyByCompanyName(PreferencesTest.emmCompanyName);
			goToPreferencesTab();

			// Configure an Intune
			sleeper(6000); // It's required to wait for EMM TOOL button
			preferencesPage.clickOnElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneRadioButton");
			Assert.assertTrue(preferencesPage.verifyIntuneConfiguration(PreferencesPage.intuneCredentials.get("DOMAIN_NAME"), PreferencesPage.intuneCredentials.get("INTUNE_ID"), PreferencesPage.intuneCredentials.get("INTUNE_PASSWORD")), "Intune Configuration is failed");
			LOGGER.info("Intune is configured successfully");

			Assert.assertTrue(deviceListPage.clickOnFirstDeviceBySelectingCompany(PreferencesTest.emmCompanyName, LanguageCode), "Failed to click on first Device");

			Assert.assertTrue(deviceDetailsPage.verifyEmmToolButton(), "Failed to validate OPEN IN EMM TOOL button ");

			Assert.assertTrue(deviceDetailsPage.verifyEmmToolRedirection(PreferencesPage.intuneCredentials.get("INTUNE_ID"), PreferencesPage.intuneCredentials.get("INTUNE_PASSWORD"), DeviceVariables.INTUNE), "EMM Tool redirection failed");
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyDeepLinkingOfEmmEnrolledDevice : " + ex.getMessage());
		} finally {
			try {
				// Remove Intune Configuration
				gotoCompaniesTab();
				waitForPageLoaded();
				impersonateCompanyByCompanyName(PreferencesTest.emmCompanyName);
				Assert.assertTrue(preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_INTUNE, "intuneIntegrationDeleteButton", PreferencesTest.emmCompanyId), "Error occured while removing microsoft intune on Prefernce Page");
				Assert.assertTrue(preferencesPage.removeEmmUser(LanguageCode, PreferencesTest.emmCompanyName, PreferencesTest.intunehUserName, getEnvironment(environment)), "Error occured while removing intune user on User List Page");
			} catch (Exception ex) {
				LOGGER.error("Exception occured in verifyDeepLinkingOfEmmEnrolledDevice : " + ex.getMessage());
			}
		}
	}

	/**
	 * This test will verify warranty tile information for HP devices on device details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 13, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "203372")
	public final void verifyWarrantyTile() throws Exception {
		String startDate, endDate = null;
		int statusCode = 0;
		if (System.getProperty("environment").equals("US-STABLE")) {

			login("MSP_ADMIN_US_ST_EMAIl_01", "MSP_ADMIN_US_ST_PASSWORD_01");
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

			SoftAssert softAssert = new SoftAssert();
			gotoDevicesTab();
			resetTableConfiguration();
			deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
			deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");

			// Check device is present in the list
			Assert.assertTrue(VerifyDeviceAvailability(), "Selected device not present in the list");

			// Navigate to device details page
			deviceListPage.goToParticularDevice(deviceName);
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

			// Scroll down to warranty tile
			deviceDetailsPage.scrollOndeviceDetailsPage("warrantyTile");
			softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("warrantyHeader", getTextLanguage(LanguageCode, "daas_ui", "assets.details.general_informations.warranty").toUpperCase()));

			// Verify warranty text
			softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("warrantyText", getTextLanguage(LanguageCode, "daas_ui", "asset_detail.no_warranty")), "Warranty text did not mathc with the actual one");
			softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("warrantyReason1", getTextLanguage(LanguageCode, "daas_ui", "asset_details.warranty_err.reason1")), "Warranty reason1 did not mathc with the actual one");
			softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("warrantyReason2", getTextLanguage(LanguageCode, "daas_ui", "asset_details.warranty_err.reason2")), "Warranty reason2 did not mathc with the actual one");
			softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("warrantyReason3", getTextLanguage(LanguageCode, "daas_ui", "asset_details.warranty_err.reason3")), "Warranty reason3 did not mathc with the actual one");

			softAssert.assertAll();
		} else if (System.getProperty("environment").equals("US-STAGING")) {
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			SoftAssert softAssert = new SoftAssert();
			gotoDevicesTab();
			resetTableConfiguration();
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

			//deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
			deviceListPage.goToParticularDevice(DeviceVariables.DEVICE_NAME_WARRANTY);
			deviceDetailsPage.getElementDeviceDetailsPage("warrantyTile");

			// Scroll down to warranty tile
			deviceDetailsPage.scrollOndeviceDetailsPage("warrantyTile");
			softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("warrantyHeader", getTextLanguage(LanguageCode, "daas_ui", "assets.details.general_informations.warranty")));
			LOGGER.info("Navigated to warranty tile");

			LOGGER.info("Get warranty API status code");

			statusCode = getStatusCodePost(getEnvironment(System.getProperty("environment")) + ConstantURL.WARRANRY_RESOURCE, "{}");
			softAssert.assertEquals(statusCode, 200, "Selected device is having no warranty");

			// Split date and date text
			String startDate1[] = deviceDetailsPage.getTextOfDeviceDetailsPage("warrantyStatus1StartDate").split(":");
			String endDate1[] = deviceDetailsPage.getTextOfDeviceDetailsPage("warrantyStatus1EndDate").split(":");

			// Verify start and end date text for warranty label 1
			softAssert.assertEquals(startDate1[0], getTextLanguage(LanguageCode, "daas_ui", "asset_details_start_date"), "Start date text did not match with the actual one");
			softAssert.assertEquals(endDate1[0], getTextLanguage(LanguageCode, "daas_ui", "asset_details_end_date"), "End date text did not match with the actual one");
			LOGGER.info("Verified start and end date text for warranty label 1");

			// Get dates for warranty label 1 from API
			startDate = deviceDetailsPage.getWarrantyTileInfoStaging(getEnvironment(System.getProperty("environment")) + ConstantURL.WARRANRY_RESOURCE, "{}", 0, "started_at").replace("-", "/");
			endDate = deviceDetailsPage.getWarrantyTileInfoStaging(getEnvironment(System.getProperty("environment")) + ConstantURL.WARRANRY_RESOURCE, "{}", 0, "ends_at").replace("-", "/");

			// Verify start and end date for warranty label 1 with API response
			softAssert.assertEquals(convertDate("yyyy/MM/dd", "MM/dd/yyyy", startDate), startDate1[1].trim(), "UI start date for warranty label 1 did not match with the api response");
			softAssert.assertEquals(convertDate("yyyy/MM/dd", "MM/dd/yyyy", endDate), endDate1[1].trim(), "UI end date for warranty label 1 did not match with the api response");

			// Verify date format is same as MM/DD/YYYY for warranty label 1
			softAssert.assertTrue(checkDateFormat("([0-9]{2})/([0-9]{2})/([0-9]{4})", endDate1[1].trim()), "End date for warranty label 1 is not in format MM/DD/YYYY");
			softAssert.assertTrue(checkDateFormat("([0-9]{2})/([0-9]{2})/([0-9]{4})", startDate1[1].trim()), "Start date for warranty label 1 is not in format MM/DD/YYYY");
			LOGGER.info("Verified start and end date format for warranty label 1");

			// Split date and date text for warranty label 2
			String startDate2[] = deviceDetailsPage.getTextOfDeviceDetailsPage("warrantyStatus2StartDate").split(":");
			String endDate2[] = deviceDetailsPage.getTextOfDeviceDetailsPage("warrantyStatus2EndDate").split(":");

			// Verify start and end date text for warranty label 2
			softAssert.assertEquals(startDate2[0], getTextLanguage(LanguageCode, "daas_ui", "asset_details_start_date"), "Start date text did not match with the actual one");
			softAssert.assertEquals(endDate2[0], getTextLanguage(LanguageCode, "daas_ui", "asset_details_end_date"), "End date text did not match with the actual one");
			LOGGER.info("Verified start and end date text for warranty label 2");

			// Get dates for warranty label 2 from API
			startDate = deviceDetailsPage.getWarrantyTileInfoStaging(getEnvironment(System.getProperty("environment")) + ConstantURL.WARRANRY_RESOURCE, "{}", 1, "started_at").replace("-", "/");
			endDate = deviceDetailsPage.getWarrantyTileInfoStaging(getEnvironment(System.getProperty("environment")) + ConstantURL.WARRANRY_RESOURCE, "{}", 1, "ends_at").replace("-", "/");

			// Verify start and end date for warranty label 2 with api response
			softAssert.assertEquals(convertDate("yyyy/MM/dd", "MM/dd/yyyy", startDate), startDate2[1].trim(), "UI start date for warranty label 2 did not match with the api response");
			softAssert.assertEquals(convertDate("yyyy/MM/dd", "MM/dd/yyyy", endDate), endDate2[1].trim(), "UI end date for warranty label 2 did not match with the api response");

			// Verify date format is same as MM/DD/YYYY for warranty label 2
			softAssert.assertTrue(checkDateFormat("([0-9]{2})/([0-9]{2})/([0-9]{4})", endDate2[1].trim()), "End date for warranty label 2 is not in format MM/DD/YYYY");
			softAssert.assertTrue(checkDateFormat("([0-9]{2})/([0-9]{2})/([0-9]{4})", startDate2[1].trim()), "Start date for warranty label 2 is not in format MM/DD/YYYY");
			LOGGER.info("Verified start and end date format for warranty label 2");

			softAssert.assertAll();
		}
	}

	/**
	 * This test will verify incident tile
	 *
	 * @throws Exception
	 */
	@Test(priority = 14, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "236724")
	public final void verifyIncidentTile() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage devicelistPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		resetTableConfiguration();

		// Navigate to device details page
		devicelistPage.goToParticularDevice(deviceName);

		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		// Click on incident tab
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");
		if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("noDataIncident") == true) {
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("incidentChartHeading").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset.details.incidentChart.yaxis")), "Incident chart title did not match");
			LOGGER.info("Incident data is not present on selected device");
		} else if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("incidentWithData") == true) {
			LOGGER.info("Incident data is present on selected device");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("incidentsCreatedHeading").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset.details.incidentChart.open")), "Incident Created title did not match");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("incidentThisMonthHeading").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset.details.incidentChart.now")), "Incident Created this month title did not match");

			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("SeeAllIncident");
			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

			// Verifying Bug 257266: [DaaS][Incident] Incident page showing infinite loader when navigated from "See All Incident" link on the device details page
			Assert.assertTrue(incidentPage.waitForElementsOfIncidentPage("generateXlsButton"), "Infinite loader is shown when user is redirected to incidents page after clicking on see all incidents link under incident chart on device details page");
			LOGGER.info("Incident list page is visible");
			incidentPage.waitForElementsOfIncidentPage("selectAllCheckBox");
			incidentPage.scrollOnIncidentPage("statusboxtitle");
			incidentPage.waitForElementsOfIncidentPage("statusboxbefore");
			incidentPage.clickOnElementsOfIncidentPage("statusboxbefore");
			incidentPage.waitForElementsOfIncidentPage("statusDropdownList");
			List<WebElement> elements = incidentPage.getWebElementsOfIncidentPage("statusDropdownList");
			elements.get(0).click();
			sleeper(3000);
			incidentPage.clickOnElementsOfIncidentPage("statusboxbefore");
			incidentPage.waitForElementsOfIncidentPage("selectAllCheckBox");
			List<WebElement> element = incidentPage.getWebElementsOfIncidentPage("statusSearchList");
			int openIncidentCount = element.size();
			navigateToBack();
			waitForPageLoaded();
			sleeper(3000);
			deviceDetailsPage.scrollOndeviceDetailsPage("SeeAllIncident");
			sleeper(3000);
			String incidentCount = deviceDetailsPage.getTextOfDeviceDetailsPage("openIncidentCount");
			int result = Integer.parseInt(incidentCount);
			if (result == openIncidentCount) {
				LOGGER.info("Open incident count matches");
			} else {
				softAssert.fail("Open incident count doesnot match");
			}

		} else {
			softAssert.fail("Incident data is not valid on selected device");
		}
		softAssert.assertAll();

	}


	/**
	 * Disabling the test case as HP Sure click advanced tile is removed as part of
	 * Feature 987100: [WorkF] Disable and Remove Proactive Security analytics in TechPulse
	 * This test will verify HP Sure Click Advanced tile information on device details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 15, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "309626", enabled = false)
	public final void verifyHPSureClickAdvancedTile() throws Exception {
		login("MSP_ADMIN_US_ST_EMAIl_01", "MSP_ADMIN_US_ST_PASSWORD_01");
		SoftAssert softAssert = new SoftAssert();

		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver());
		companyDetailsPage = companyDetailsPage.getInstance();
		gotoCompaniesTab();
		resetTableConfiguration();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_DETAILS_COMPANY"));
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Navigated to Preference Tab");

		// HP sure toggle
		companyDetailsPage.scrollOnCompaniesDetailsPage("reportTileHeader");
		if (!companyDetailsPage.getTextOfCompaniesDetailsPage("bromiumData").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("toggleBromium");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("toggleBromium");
			LOGGER.info("HP Sure toggle is Enabled");
		} else {
			LOGGER.info("HP Sure toggle is already enabled");
		}

		gotoDevicesTab();
		resetTableConfiguration();

		// Navigate to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_ID"));
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		// Click on warranty tile
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");
		deviceDetailsPage.scrollOndeviceDetailsPage("hPSureClickAdvancedHeader");

		// Verify bromium status
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("hPSureClickAdvancedHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.details.health_protection.bromium_integration")), "Header text did not match");
		softAssert.assertEquals(getDataFromApi("bromiumStatus", getEnvironment(System.getProperty("environment")) + ConstantURL.HPSURE_URL + deviceID), deviceDetailsPage.getTextOfDeviceDetailsPage("bromiumStatus"));

		softAssert.assertAll();
	}

	/**
	 * This test will verify hardware inventory information on device details page Commenting data validation part as it is getting difficult to validate all the hardware field
	 * values from API. Hardware values are changing based on the device event and we can add these much check in scripts. Also, Developer also written exact same code, they take
	 * value from API and show in the UI.
	 *
	 * @throws Exception
	 */
	@Test(priority = 16, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "268438")
	public final void verifyHardwareInvTile() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		resetTableConfiguration();

		// Check device is present in the list
		Assert.assertTrue(VerifyDeviceAvailability(), "Selected device not present in the list");

		// Navigate to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "HARDWARE_INV_DEVICE_ID"));
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");

		// Click on hardware tab
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("hardwareTab");
		LOGGER.info("Clicked on hardware tab");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("hardwareList"), "Hardware tile missing on device details page");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("hardwareTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.details.inventory.title").toUpperCase()), "Hardware tile header did not match");
		/*
		 * // If device is in archived state do no verify archived tile if
		 * (!deviceDetailsPage.getTextOfDeviceDetailsPage("statusOnIdentityTile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.archived"))) {
		 *
		 * // Click on hardware tab deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("hardwareTab"); LOGGER.info("Clicked on hardware tab");
		 *
		 * // Verify hardware tile header softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("hardwareTileHeader", getTextLanguage(LanguageCode, "daas_ui",
		 * "assets.details.inventory.title").toUpperCase())
		 *
		 * // Verify battery data if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("batteryLabel")) { if
		 * (!(deviceDetailsPage.getTextOfDeviceDetailsPage("battery_Id").equals("-"))) { deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("batteryMoreDetails");
		 * softAssert.assertEquals(getDataFromApiPost("resources.batterySn", getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") +
		 * ConstantURL.HW_INV_BATTERY, HwInvBody, 1), deviceDetailsPage.getTextOfDeviceDetailsPage("battery_Id"), "Battery id did not match");
		 * LOGGER.info("Battery serial number is verified");
		 *
		 * // Verify CT number String[] cT_Number = deviceDetailsPage.getTextOfDeviceDetailsPage("cT_Number").split("\\:"); softAssert.assertTrue((cT_Number[0] +
		 * ": ").equals(getTextLanguage(LanguageCode, "daas_ui", "asset_details.battery_details.ct")), "CT number text did not match");
		 * softAssert.assertEquals(getDataFromApiPost("resources.ctNumber", getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") +
		 * ConstantURL.HW_INV_BATTERY, HwInvBody, 1), cT_Number[1].trim(), "CT number did not match"); LOGGER.info("CT number is verified");
		 *
		 * // Verify battery warranty status String[] battery_Warranty_Status = deviceDetailsPage.getTextOfDeviceDetailsPage("battery_Warranty_Status").split("\\:");
		 * softAssert.assertTrue((battery_Warranty_Status[0] + ": ").equals(getTextLanguage(LanguageCode, "daas_ui", "asset_details.battery_details.warranty")),
		 * "Battery Warranty text did not match"); softAssert.assertEquals(getDataFromApiPost("resources.warStatus", getEnvironmentSpecificData(System.getProperty("environment"),
		 * "CATALYST_API_BASE_URL") + ConstantURL.HW_INV_BATTERY, HwInvBody, 1), battery_Warranty_Status[1].trim(), "Battery Warranty Status did not match");
		 * LOGGER.info("Battery warranty status is verified"); } else { LOGGER.info("Battery data is null for selected device"); } } else {
		 * LOGGER.info("Battery related data is not present for selected device"); }
		 *
		 * // Verify graphics data if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("graphicsLabel")) { softAssert.assertEquals(getDataFromApiPost("resources.graphics",
		 * getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") + ConstantURL.HW_INV_URL, HwInvBody, 1),
		 * deviceDetailsPage.getTextOfDeviceDetailsPage("graphics"), "graphics did not match with the API response"); LOGGER.info("Graphics details are verified"); } else {
		 * LOGGER.info("Graphics data not present for this device"); }
		 *
		 * // Verify Hard Drive name if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("hardDriveLabel")) { if
		 * (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("hardDriveMoreDetails")) deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("hardDriveMoreDetails");
		 * softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("hardDriveName", getDataFromApiPost("resources.model",
		 * getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") + ConstantURL.HW_INV_DISKREP, HwInvBody, 1)), "Hard drive name did not match");
		 * LOGGER.info("Hard drive name is verified");
		 *
		 * // Verify hard drive serial number String serialNo[] = deviceDetailsPage.getTextOfDeviceDetailsPage("hardDriveSerialNumber").split(":");
		 * softAssert.assertTrue((serialNo[0] + ": ").equals(getTextLanguage(LanguageCode, "daas_ui", "asset_details.hard_disk_details.serial")),
		 * "Serial number text did not match"); softAssert.assertEquals(serialNo[1].trim(), getDataFromApiPost("resources.diskSn",
		 * getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") + ConstantURL.HW_INV_DISKREP, HwInvBody, 1), "Serial number did not match");
		 * LOGGER.info("HD serial number is verified"); } else { LOGGER.info("Hard drive data not present for this device"); }
		 *
		 * // Verify memory data if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("MemoryLabel")) { softAssert.assertEquals(getDataFromApiPost("resources.memory",
		 * getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") + ConstantURL.HW_INV_URL, HwInvBody, 1).replaceAll(" ", ""),
		 * deviceDetailsPage.getTextOfDeviceDetailsPage("memory").replaceAll(" ", ""), "memory did not match with the API response"); LOGGER.info("Memory details are verified"); }
		 * else { LOGGER.info("Memory data not present for this device"); }
		 *
		 * // Verify processor data if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("ProcessorLabel")) { softAssert.assertEquals(getDataFromApiPost("resources.processor",
		 * getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") + ConstantURL.HW_INV_URL, HwInvBody, 1),
		 * deviceDetailsPage.getTextOfDeviceDetailsPage("processor"), "processor did not match with the API response"); LOGGER.info("Processor details are verified"); } else {
		 * LOGGER.info("Processor data not present for this device"); } } else { LOGGER.info("Device is in archived state"); }
		 */
		softAssert.assertAll();
		LOGGER.info("VerifyHardwareInvTile test case is successfully completed");
	}

	/**
	 * This test will verify security tile information on device details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 17, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "230237")
	public final void verifySecurityTile() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		LOGGER.info("Redirected to Devices tab");
		resetTableConfiguration();

		// Check device is present in the list
		Assert.assertTrue(VerifyDeviceAvailability(), "Selected device not present in the list");

		// Redirected to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_ID"));

		// Click on health and protection tab
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");
		LOGGER.info("Clicked on health and protection tab");

		// Verify tile header text
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("SecurityTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.details.health_protection.antivirus_firewall")), "Security tile is not present.");
		LOGGER.info("Tile header text is verified");

		/*
		 * if (!getResponse(getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") + ConstantURL.SECURITY_URL, securityBody,
		 * "POST").contains("Exception occurred in DB. Reason: Requested resource not found")) { // Verify Antivirus and Threat Protection details
		 * softAssert.assertEquals(deviceDetailsPage.getSecurityTileInformation(getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") +
		 * ConstantURL.SECURITY_URL, securityBody, "Antivirus and Threat Protection"), deviceDetailsPage.getTextOfDeviceDetailsPage("antivirusRes"),
		 * "antivirus status did not match"); LOGGER.info("Antivirus and Threat Protection status is verified");
		 *
		 * // Verify Firewall and Network Protection status
		 * softAssert.assertEquals(deviceDetailsPage.getSecurityTileInformation(getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") +
		 * ConstantURL.SECURITY_URL, securityBody, "Firewall and Network Protection"), deviceDetailsPage.getTextOfDeviceDetailsPage("fireWallRes"),
		 * "firewall status did not match"); LOGGER.info("Firewall and Network Protection status is verified");
		 *
		 * // Verify BitLocker Drive Encryption status if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("bitLocker")) {
		 * softAssert.assertEquals(deviceDetailsPage.getSecurityTileInformation(getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") +
		 * ConstantURL.SECURITY_URL, securityBody, "BitLocker Drive Encryption"), deviceDetailsPage.getTextOfDeviceDetailsPage("bitLockerRes"), "bitlock status did not match");
		 * LOGGER.info("BitLocker Drive Encryption status is verified"); } else { LOGGER.info("BitLocker Drive Encryption data is not present for selected device"); } } else {
		 * LOGGER.info("No security related data is present for this device"); }
		 */
		softAssert.assertAll();
		LOGGER.info("verifySecurityTile test case is successfully completed");
	}

	/**
	 * This method verifies the Software Inventory of iOS device from both Intune and DaaS portal
	 */
	@Test(groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "Testcases 213537", enabled = false)
	public final void verifySoftwareInvetoryOfIntuneEnrollediOSDevice() {
		PreferencesPage preferencesPage;
		DeviceDetailsPage deviceDetailsPage;
		CompaniesPage companiesPage = null;
		CompanyPin companypin;
		String companyName = null;
		try {
			preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			companypin = new CompanyPin();

			// Creating a company
			login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
			gotoRootCompaniesTab();
			companyName = companiesPage.addCompany(false, DeviceVariables.EMM_MSP_NAME, null, LanguageCode);
			LOGGER.info("Created a company : " + companyName);
			sleeper(3000); // takes time after adding an company
			logout();

			// Login to MSP account
			login("MSP_EMM_TEAM_USERNAME", "MSP_EMM_TEAM_PASSWORD");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(companyName);
			preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");

			// Creating a company pin
			companypin.generateCompanyPin(LanguageCode);

			// Configuring an Intune
			preferencesPage.clickByJavaScriptOnPreferencesPage("emmEditbutton");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneRadioButton");
			Assert.assertTrue(preferencesPage.verifyIntuneConfiguration(PreferencesPage.intuneCredentials.get("DOMAIN_NAME"), PreferencesPage.intuneCredentials.get("INTUNE_ID"), PreferencesPage.intuneCredentials.get("INTUNE_PASSWORD")), "Intune Configuration is failed");
			LOGGER.info("Intune is configured successfully");

			Assert.assertTrue(deviceDetailsPage.matchSoftwareInvetoryOfiOSDevice(companyName, LanguageCode), "Software Inventory validation is failed");
			LOGGER.info("Software Inventory is validated for Intuned enrolled iOS device");
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifySoftwareInvetoryOfIntuneEnrollediOSDevice" + ex.getMessage());
		} finally {
			try {
				// Logout from MSP account
				logout();
				// Deleting a Company
				login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
				gotoRootCompaniesTab();
				Assert.assertTrue(companiesPage.removeCompany(companyName, LanguageCode), "Unable to delete company");
			} catch (Exception ex) {
				LOGGER.error("Exception occured in verifySoftwareInvetoryOfIntuneEnrollediOSDevice" + ex.getMessage());
			}
		}
	}

	/**
	 * This test case verifies device import using the csv file User Story 380464: [DaaS][UI] Implement additional step/process for critical delete functions in TechPulse
	 *
	 * @throws Exception
	 */
	@Test(priority = 18, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "STABILIZATION_STAGING"}, description = "test case ID: 209672")
	public final void verifyImportDevice() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		resetTableConfiguration();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		if (verifyElementIsVisible("globalFilter") && !deviceListPage.matchTextOfDeviceListPage("globalFilter", getTextLanguage(LanguageCode, "daas_ui", "assets.table.all "))) {
			deviceListPage.clickOnElementsOfDeviceListPage("globalFilter");
			deviceListPage.clickOnElementsOfDeviceListPage("clearGlobalFilter");
			deviceListPage.clickOnElementsOfDeviceListPage("ApplyGlobalFilter");
		}
		Assert.assertTrue(deviceListPage.selectCompanyOfDevicePage(importCompany, LanguageCode), "Company selection failed while adding device");

		deviceListPage.clickOnElementsOfDeviceListPage("previousButton");
		// Verify Add Devices title on Add device pop up
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("addDevicesPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "assets.add.title")), "Title on Add device popup is incorrect");
		// verify choose company message on Add device pop up
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.select_company.label")), "Message on Add device popup is incorrect");
		deviceListPage.clickOnElementsOfDeviceListPage("nextButton");

		deviceListPage.verifyImportDevices(LanguageCode, DeviceVariables.DEVICESWITHCORRECTSERIALNUMBER_UPLOAD, DeviceVariables.NO_ENROLLMENT);
		Assert.assertTrue(deviceListPage.postNotificationCheckImportForSuccessfullImportInV3(DeviceVariables.DEVICESWITHCORRECTSERIALNUMBER_UPLOAD), "Notification for device import did not display/delay in notification for more than 30 seconds");
		LOGGER.info("Notification message verification for import has passed");
		pressKey(Keys.ESCAPE);
		Assert.assertTrue(deviceListPage.verifyImportedDevicesOnListPage(DeviceVariables.DEVICESWITHCORRECTSERIALNUMBER_UPLOAD), "Devices are not getting reflected on the list page.");
		LOGGER.info("Devices which got imported are getting reflected on list page");

		gotoLogTab();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		if (logPage.waitForElementsOfLogPage("clearSelectionButton")) {
			logPage.clickOnElementsOfLogPage("clearSelectionButton");
		}
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		logPage.selectLogTypeOfLogPage("Devices");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		logPage.selectLogSubTypeOfLogPage("Import");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		logPage.selectCompanyOfLogPage(importDeviceCSVCompany);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		Assert.assertTrue(logPage.getTextOfLogPage("descriptionFirstRow").contains("1 devices have been imported and/or updated."), "Log description for add device i not correct");

		//Assert.assertTrue(deviceListPage.verifyDescriptionOnLogsPage(DeviceVariables.DEVICESWITHCORRECTSERIALNUMBER_UPLOAD), "Description of imported devices on logs page is incorrect");
		LOGGER.info("Description on logs page is correct when devices are imported successfully");

		sleeper(2000);
		gotoDevicesTab();
		waitForPageLoaded();
		//resetTableConfiguration();
		deviceListPage.goToParticularDevice("AutomationDeviceSR");
		String deviceID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		String deviceDeleteBody = "[{\"id\":\"" + deviceID + "\"}]";
		deviceListPage.deletZTEDeviceUsingAPI(getEnvironment(System.getProperty("environment")) + DeviceVariables.DEVICE_REMOVE, deviceDeleteBody);
		LOGGER.info("Removed added Device");

		LOGGER.info("Validation of import device completed successfully.");

		softAssert.assertAll();
	}

	/**
	 * This test case verifies device import with csv file containing incorrect serial number
	 *
	 * @throws IOException
	 * @throws Exception
	 */
	@Test(priority = 19, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI"}, description = "test case ID: 209672")
	public final void verifyImportDeviceWithIncorrectSerialNumber() throws IOException {
		SoftAssert softAssert = new SoftAssert();
		deleteAndCreateDir(ConstantPath.DOWNLOAD_PATH);
		try {
			String errorMessage1 = DeviceVariables.FORBIDDEN_STARTING_STRINGS_ERROR_MESSAGE2;
			String errorMessage = DeviceVariables.FORBIDDEN_STARTING_STRINGS_ERROR_MESSAGE1;
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			gotoDevicesTab();
			LOGGER.info("Redirected to device list page");
			resetTableConfiguration();
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(deviceListPage.selectCompanyOfDevicePage(importCompany, LanguageCode), "Company selection failed while adding device.");

			deviceListPage.clickOnElementsOfDeviceListPage("previousButton");
			// Verify Add Devices title on Add device pop up
			softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("addDevicesPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "assets.add.title")), "Title on Add device popup is incorrect");
			// verify choose company message on Add device pop up
			softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.select_company.label")), "Message on Add device popup is incorrect");
			deviceListPage.clickOnElementsOfDeviceListPage("nextButton");

			deviceListPage.verifyImportDevices(LanguageCode, DeviceVariables.DEVICESWITHINCORRECTSERIALNUMBER_UPLOAD, DeviceVariables.NO_ENROLLMENT);
			Assert.assertTrue(deviceListPage.postNotificationCheckImportForUnsuccessfullImportInV3(DeviceVariables.DEVICESWITHINCORRECTSERIALNUMBER_UPLOAD), "Message on notification window is incorrect");
			LOGGER.info("Notification message verification for import has passed");

			pressKey(Keys.ESCAPE);
			resetTableConfiguration();
			Assert.assertTrue(deviceListPage.verifyDescriptionOnLogsPage(DeviceVariables.DEVICESWITHINCORRECTSERIALNUMBER_UPLOAD), "Description of imported devices on logs page is incorrect");
			LOGGER.info("Description on logs page is correct when devices are imported successfully");

			gotoDevicesTab();
			LOGGER.info("Redirected to device list page");
			waitForPageLoaded();
			Assert.assertTrue(deviceListPage.verifyErrorCSVFile(errorMessage, errorMessage1, "2"), "Error message in Error.csv file for device import with incorrect serial number is incorrect");

			LOGGER.info("Validation of import device with incorrect serial number completed successfully.");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyImportDeviceWithIncorrectSerialNumber " + e.getMessage());
		}
		softAssert.assertAll();
	}

	/**
	 * This test case verifies device import with csv file containing no serial number
	 *
	 * @throws IOException
	 */
	@Test(priority = 20, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI"}, description = "test case ID: 209672")
	public final void verifyImportDeviceWithoutSerialNumber() throws IOException {
		SoftAssert softAssert = new SoftAssert();
		deleteAndCreateDir(ConstantPath.DOWNLOAD_PATH);
		try {
			String errorMessageOne = DeviceVariables.BLANK_SERIALNUMBER_ERROR_MESSAGE;
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			gotoDevicesTab();
			LOGGER.info("Redirected to device list page");
			//resetTableConfiguration();
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(deviceListPage.selectCompanyOfDevicePage(importCompany, LanguageCode), "Company selection failed while adding device.");

			deviceListPage.clickOnElementsOfDeviceListPage("previousButton");
			// Verify Add Devices title on Add device pop up
			softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("addDevicesPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "assets.add.title")), "Title on Add device popup is incorrect");
			// verify choose company message on Add device pop up
			softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.select_company.label")), "Message on Add device popup is incorrect");
			deviceListPage.clickOnElementsOfDeviceListPage("nextButton");

			deviceListPage.verifyImportDevices(LanguageCode, DeviceVariables.DEVICESWITHOUTSERIALNUMBER_UPLOAD, DeviceVariables.NO_ENROLLMENT);
			Assert.assertTrue(deviceListPage.postNotificationCheckImportForUnsuccessfullImportInV3(DeviceVariables.DEVICESWITHOUTSERIALNUMBER_UPLOAD), "Message on notification window is incorrect");
			LOGGER.info("Notification message verification for import has passed");
			pressKey(Keys.ESCAPE);
			resetTableConfiguration();
			Assert.assertTrue(deviceListPage.verifyDescriptionOnLogsPage(DeviceVariables.DEVICESWITHOUTSERIALNUMBER_UPLOAD), "Description of imported devices on logs page is incorrect");
			LOGGER.info("Description on logs page is correct when devices are imported successfully");

			gotoDevicesTab();
			LOGGER.info("Redirected to device list page");
			waitForPageLoaded();
			Assert.assertTrue(deviceListPage.verifyErrorCSVFile(errorMessageOne, DeviceVariables.SKIP_MESSAGE, "2"), "Error message in Error.csv file for device import with blank serial number is incorrect");

			LOGGER.info("Validation of import device without serial number completed successfully.");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyImportDeviceWithoutSerialNumber " + e.getMessage());
		}
		softAssert.assertAll();
	}

	/**
	 * This test verify enroll fake Intune Ios device details
	 *
	 * @throws IOException
	 */
	@Test(groups = {"REGRESSIONDEVICES1", "REGRESSION_CI"}, description = "Testcase 208869,208868,208867", enabled = false)
	public final void verifyIntuneFakeEnrollediOSDeviceDetails() throws IOException {
		EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		String companyName = null, companyEmailId = null;
		try {
			login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
			gotoRootCompaniesTab();
			companyName = companiesPage.addCompany(false, DeviceVariables.EMM_MSP_NAME, null, LanguageCode);
			sleeper(3000);// takes time after adding an company
			logout();
			login("MSP_EMM_TEAM_USERNAME", "MSP_EMM_TEAM_PASSWORD");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(companyName);
			companyDetailsPage.scrollOnCompaniesDetailsPage("accountingAssignTileHeader");
			companyEmailId = companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPEmailData");
			Assert.assertTrue(enrollFakeDevice.verifyIntuneFakeDeviceEmmToolRedirection(DeviceVariables.IOS_OS, companyName, companyEmailId, "", LanguageCode), "Failed to validate Intune Fake Device Details");
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyIntuneFakeEnrollediOSDeviceDetails" + e.getMessage());
		} finally {
			// Deleting a company
			try {
				logout();
				login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
				gotoRootCompaniesTab();
				Assert.assertTrue(companiesPage.removeCompany(companyName, LanguageCode), "Unable to delete company " + companyName);
			} catch (Exception e2) {
				LOGGER.error("Exception occured in verifyIntuneFakeEnrollediOSDeviceDetails" + e2.getMessage());
			}
		}
	}

	/**
	 * This test verify enroll fake Intune Android device details
	 *
	 * @throws IOException
	 */
	@Test(groups = {"REGRESSIONDEVICES1", "REGRESSION_CI"}, description = "Testcase 208869,208868,208867", enabled = false)
	public final void verifyIntuneFakeEnrolledAndroidDeviceDetails() throws IOException {
		EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String companyName = null, companyEmailId = null;
		try {
			login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
			gotoRootCompaniesTab();
			companyName = companiesPage.addCompany(false, DeviceVariables.EMM_MSP_NAME, null, LanguageCode);
			sleeper(3000);// takes time after adding an company
			logout();
			login("MSP_EMM_TEAM_USERNAME", "MSP_EMM_TEAM_PASSWORD");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(companyName);
			companyDetailsPage.scrollOnCompaniesDetailsPage("accountingAssignTileHeader");
			companyEmailId = companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPEmailData");
			Assert.assertTrue(enrollFakeDevice.verifyIntuneFakeDeviceEmmToolRedirection(DeviceVariables.ANDROID_OS, companyName, companyEmailId, "", LanguageCode), "Failed to validate Intune Fake Device Details");
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyIntuneFakeEnrolledAndroidDeviceDetails" + e.getMessage());
		} finally {
			// Deleting a company
			try {
				logout();
				login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
				gotoRootCompaniesTab();
				Assert.assertTrue(companiesPage.removeCompany(companyName, LanguageCode), "Unable to delete company " + companyName);
			} catch (Exception e2) {
				LOGGER.error("Exception occured in verifyIntuneFakeEnrolledAndroidDeviceDetails" + e2.getMessage());
			}
		}
	}

	/**
	 * This test verify enroll fake Intune windows device details
	 *
	 * @throws IOException
	 */
	@Test(groups = {"REGRESSIONDEVICES1", "REGRESSION_CI"}, description = "Testcase 208869,208868,208867", enabled = false)
	public final void verifyIntuneFakeEnrolledWindowsDeviceDetails() throws IOException {
		EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String companyName = null, companyEmailId = null;
		try {
			login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
			gotoRootCompaniesTab();
			companyName = companiesPage.addCompany(false, DeviceVariables.EMM_MSP_NAME, null, LanguageCode);
			sleeper(3000);// takes time after adding an company
			logout();
			login("MSP_EMM_TEAM_USERNAME", "MSP_EMM_TEAM_PASSWORD");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(companyName);
			companyDetailsPage.scrollOnCompaniesDetailsPage("accountingAssignTileHeader");
			companyEmailId = companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPEmailData");
			Assert.assertTrue(enrollFakeDevice.verifyIntuneFakeDeviceEmmToolRedirection(DeviceVariables.WINDOWS_OS, companyName, companyEmailId, "", LanguageCode), "Failed to validate Intune Fake Device Details");
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyIntuneFakeEnrollediOSDeviceDetails" + e.getMessage());
		} finally {
			// Deleting a company
			try {
				logout();
				login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
				gotoRootCompaniesTab();
				Assert.assertTrue(companiesPage.removeCompany(companyName, LanguageCode), "Unable to delete company " + companyName);
			} catch (Exception e2) {
				LOGGER.error("Exception occured in verifyIntuneFakeEnrollediOSDeviceDetails" + e2.getMessage());
			}
		}
	}

	/**
	 * This test verify enroll fake Intune MAC device details
	 *
	 * @throws IOException
	 */
	@Test(groups = {"REGRESSIONDEVICES1", "REGRESSION_CI"}, description = "Testcase 208869,208868,208867", enabled = false)
	public final void verifyIntuneFakeEnrolledMacDeviceDetails() throws IOException {
		EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String companyName = null, companyEmailId = null;
		try {
			login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
			gotoRootCompaniesTab();
			companyName = companiesPage.addCompany(false, DeviceVariables.EMM_MSP_NAME, null, LanguageCode);
			sleeper(3000);// takes time after adding an company
			logout();
			login("MSP_EMM_TEAM_USERNAME", "MSP_EMM_TEAM_PASSWORD");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(companyName);
			companyDetailsPage.scrollOnCompaniesDetailsPage("accountingAssignTileHeader");
			companyEmailId = companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPEmailData");
			Assert.assertTrue(enrollFakeDevice.verifyIntuneFakeDeviceEmmToolRedirection(DeviceVariables.MAC_OS, companyName, companyEmailId, "", LanguageCode), "Failed to validate Intune Fake Device Details");
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyIntuneFakeEnrolledMacDeviceDetails" + e.getMessage());
		} finally {
			// Deleting a company
			try {
				logout();
				login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
				gotoRootCompaniesTab();
				Assert.assertTrue(companiesPage.removeCompany(companyName, LanguageCode), "Unable to delete company " + companyName);
			} catch (Exception e2) {
				LOGGER.error("Exception occured in verifyIntuneFakeEnrolledMacDeviceDetails" + e2.getMessage());
			}
		}
	}

	/**
	 * This test verify identity tile information
	 *
	 * @throws Exception
	 */
	@Test(priority = 21, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI"})
	public final void verifyIdentityTileInformation() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		LOGGER.info("Redirected to devices tab.");
		resetTableConfiguration();

		deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");

		// Check device is present in the list
		Assert.assertTrue(VerifyDeviceAvailability(), "Selected device not present in the list");
		ArrayList<String> deviceDetailInfo = deviceListPage.getAllDeviceInfo(deviceName);

		// Navigate to device details page
		deviceListPage.goToParticularDevice(deviceName);

		// Verify identity tile information
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("serialNumberIdentityTile"), "#" + deviceDetailsPage.getTextOfDeviceDetailsPage("serialNumber"), "Serial number on identity tile did not match with numebr on details tile");
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("companyName"), deviceDetailsPage.getTextOfDeviceDetailsPage("company"), "Company name on identity tile did not match with company name on details tile");
		LOGGER.info("Verified alias,company name and serial number on identity tile");

		if (deviceDetailInfo.get(DeviceVariables.Last_Activity).contains(getTextLanguage(LanguageCode, "daas_ui", "asset.last_seen.label"))) {
			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("lastSeen"), "Last seen status is missing on identity tile");
			LOGGER.info("Verified Last seen status on identity tile");
		} else {
			LOGGER.info("Device is not enrolled or archived");
		}
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("removeButton"), "Remove button is missing on identity tile");

		softAssert.assertAll();
	}

	/**
	 * This test will verify Accessories and Peripherals tile information on device details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 22, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI", "STABILIZATION_STAGING"}, description = "TC228391")
	public final void verifyAccessoriesAndPeripheralsTile() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		// Go to Companies tab
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_DETAILS_COMPANY"));
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Navigated to Preference Tab");

		// RPOS toggle enable
		companyDetailsPage.scrollOnCompaniesDetailsPage("reportTileHeader");
		if (!companyDetailsPage.getTextOfCompaniesDetailsPage("rposData").equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("toggleRpos");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("toggleRpos");
			LOGGER.info("RPOS toggle is Enabled");
		} else {
			LOGGER.info("RPOS toggle is already enabled");
		}

		// Go to devices tab
		gotoDevicesTab();
		resetTableConfiguration();
		//deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");

		// Check device is present in the list
		Assert.assertTrue(VerifyDeviceAvailability(), "Selected device not present in the list");

		// Go to particular device
		deviceListPage.goToParticularDevice(deviceName);
		LOGGER.info("Redirected to particular device details page");

		// Click on Accessories and Peripherals tile
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("hardwareTab");
		deviceDetailsPage.scrollOndeviceDetailsPage("AccessoriesAndPeripheralsHeader");
		LOGGER.info("Scroll down to Accessories and Peripherals tile");

		// Verify Accessories and Peripherals tile headar
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("AccessoriesAndPeripheralsHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.details.inventory.accessories_peripherals")), "Accessories and Peripherals tile header did not match with the actual one");
		LOGGER.info("Verified Accessories and Peripherals tile header");

		// Verify first column of accessories and peripherals tile
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("AccessoriesAndPeripheralsFirstCol").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_detail.accessories_column.serial")), "Serial number is not the first column under Accessories and Peripherals table");
		LOGGER.info("Verified first column of accessories and peripherals tile");

		softAssert.assertAll();
	}

	/**
	 * This test case validate software download page Commneting downloadlink verification part for now as changes are required ther as per User Story 345810: [WinClient][TAC][UI]
	 * Add MSI installer for TechPulse and TA on the downloads page
	 *
	 * @throws Exception
	 */
	// Vineer 3 change for direct link pending
	@Test(priority = 23, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI", "PRODUCTION_SANITY", "STABILIZATION_STAGING"}, description = "TC275717, TC350867")
	public final void verifyDownloadSoftwareFromDevicesPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		LaunchDarkly ldinstance_dash = new LaunchDarkly();
		Assert.assertTrue(ldinstance_dash.enabled("previous-software-builds", null, null, false), "Previous Build toggle off for environment");
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		//SoftAssert softAssert = new SoftAssert();
		List<WebElement> availableApplications = new ArrayList<WebElement>();
		List<String> availableApplicationsText = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "download.andriod").replace("{appName}", APP_NAME), getTextLanguage(LanguageCode, "daas_ui", "download.mac").replace("{appName}", APP_NAME), getTextLanguage(LanguageCode, "daas_ui", "download.linuxAnalyticsCore").replace("{appName}", APP_NAME), getTextLanguage(LanguageCode, "daas_ui", "download.linuxTPMCore").replace("{appName}", APP_NAME), getTextLanguage(LanguageCode, "daas_ui", "download.windows").replace("{appName}", APP_NAME),
				getTextLanguage(LanguageCode, "daas_ui", "download.assessment_tools").replace("{appName}", APP_NAME), getTextLanguage(LanguageCode, "daas_ui", "assets.details.iot.title").replace("{appName}", APP_NAME)));
		List<JSONObject> macJsonValues = new ArrayList<JSONObject>(readJsonFromUrl("MACSOFTWAREJSONDATA"));
		gotoDevicesTab();
		resetTableConfiguration();
		sleeper(2000);
		Assert.assertTrue(deviceListPage.selectCompanyOfDevicePage(getEnvironmentSpecificData(System.getProperty("environment"), "NONENGLISHCOMPANY"), LanguageCode), "Company selection failed while adding device.");

		deviceListPage.clickOnElementsOfDeviceListPage("previousButton");
		// Verify Add Devices title on Add device pop up
		Assert.assertTrue(deviceListPage.matchTextOfDeviceListPage("addDevicesPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "assets.add.title")), "Title on Add device popup is incorrect");
		// verify choose company message on Add device pop up
		Assert.assertTrue(deviceListPage.matchTextOfDeviceListPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.select_company.label")), "Message on Add device popup is incorrect");
		deviceListPage.clickOnElementsOfDeviceListPage("nextButton");
		deviceListPage.clickOnElementsOfDeviceListPage("downloadButton");
		deviceListPage.clickOnElementsOfDeviceListPage("nextButton");
		if (deviceListPage.verifyElementsOfDeviceListPage("techpulseSectionCollapsed"))
			deviceListPage.clickOnElementsOfDeviceListPage("techPulseSectionDropDown");
		Assert.assertTrue(getTextLanguage(LanguageCode, "daas_ui", "download.title").replace("{appName}", "HP Insights").equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("downloadText")), "Download title does  not match");
		Assert.assertTrue(getTextLanguage(LanguageCode, "daas_ui", "download.sub_title").replace("{appName}", "HP Insights").replace("  ", " ").equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("downloadDescHeading")), "Download Sub title heading does not match");
		Assert.assertTrue(getTextLanguage(LanguageCode, "daas_ui", "global.application_name.short").replace("{appName}", "HP Insights").equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("techpulseTitle")), "TechPulse title did not match");
		Assert.assertTrue(getTextLanguage(LanguageCode, "daas_ui", "global.download").equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("windowsDownloadButton")), "Windows Download button Text does not match");
		Assert.assertTrue(getTextLanguage(LanguageCode, "daas_ui", "global.download").equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("macDownloadButton")), "MAC Download button Text does not match");

		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("linuxAnalyticsCore"), "linuxAnalyticsCore download button is not present");
		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("linuxTPMCore"), "linuxTPMCore download button is not present");
		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("buttonassessmenttools"), "buttonassessmenttools download button is not present");
		Assert.assertFalse(deviceListPage.verifyElementsOfDeviceListPage("hpSureSenseAdvancedTile"), "HP Sure sense advanced tile not removed from software page");

		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("androidDownloadButton"), "Android download button is not present");
//     Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("sureClickDownloadButton"), "Sure click download button is not present");
		availableApplications = deviceListPage.getElementsTillAllElementsVisibleofDeviceListPage("availableApplications");
		Assert.assertTrue(deviceListPage.compareSoftwareList(availableApplications, availableApplicationsText), "Sequence of softwares does not match");

		deviceListPage.clickOnElementsOfDeviceListPage("viewMoreVersions");
		deviceListPage.selectElementFromDropDownOfDeviceListPage("downloadPagination", "downloadPaginationList", Integer.toString(CommonVariables.SELETEDHUNDRED));
		LOGGER.info("Change selected option as " + CommonVariables.SELETEDHUNDRED);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");

		List<WebElement> releaseTypeList = new ArrayList<WebElement>();
		releaseTypeList = deviceListPage.getWebElementsOfDeviceListPage("releaseColumnList");
		Assert.assertTrue(deviceListPage.verifyColumnOfSoftwareList(releaseTypeList, LanguageCode), "Release Type column list does not match");

		// Proactive Security software download section
		if (deviceListPage.verifyElementsOfDeviceListPage("proactiveSecuritysectionCollapsed"))
			deviceListPage.clickOnElementsOfDeviceListPage("proactiveSecuritySectionDropDown");
		Assert.assertTrue(getTextLanguage(LanguageCode, "daas_ui", "assets.details.iot.title").equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("protectTraceWolfConnect")), "Proactive security title did not match");
		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("protectTraceWolfConnectDownloadButton"), "Download button for Pro Security Aplication not present");

		// Test case 2 download page from direct URL
		// *****Vineer 3 change for direct link pending*****
		deviceListPage.getUrl(getEnvironment(System.getProperty("environment")) + ConstantURL.SOFTWARE_DOWNLOAD);
		Assert.assertTrue(deviceListPage.waitForElementsOfDeviceListPage("hpDaas"), "Hp Daas header not visible");
		Assert.assertTrue(deviceListPage.waitForElementsOfDeviceListPage("hpIcon"), "Hp Daas icon not visible");
		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("userProfileButton"), "User Profile Button not available");

		// Verify System Requirements Link is present on software page
		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("systemRequirementsLink"), "System requirements link not present on software page");
		deviceListPage.clickOnElementsOfDeviceListPage("systemRequirementsLink");
		waitForPageLoaded();
		Assert.assertTrue(deviceListPage.getUrlOfCurrentPage().contains(ConstantURL.SYSTEM_REQUIREMENTS), "Page not redirected to System requirements page");
		navigateToBack();

		if (deviceListPage.verifyElementsOfDeviceListPage("techpulseSectionCollapsed"))
			deviceListPage.clickOnElementsOfDeviceListPage("techPulseSectionDropDown");
		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("downloadText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "download.title").replace("{appName}", APP_NAME)), "Download title does  not match");
		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("downloadDescHeading").equalsIgnoreCase(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "download.sub_title").replace("{appName}", APP_NAME).replaceAll("  ", " ")), "Download Sub title heading does not match");
		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("techpulseTitle").equalsIgnoreCase(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "global.application_name.short").replace("{appName}", APP_NAME)), "TechPulse title did not match");
		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("windowsDownloadButton").equalsIgnoreCase(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "global.download")), "Windows Download button Text does not match");
		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("windowsDownloadButton").equalsIgnoreCase(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "global.download")), "Windows Download button Text does not match");
		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("macDownloadButton").equalsIgnoreCase(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "global.download")), "MAC Download button Text does not match");

		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("linuxAnalyticsCore"), "linuxAnalyticsCore download button is not present");
		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("linuxTPMCore"), "linuxTPMCore download button is not present");
		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("buttonassessmenttools"), "buttonassessmenttools download button is not present");
		Assert.assertFalse(deviceListPage.verifyElementsOfDeviceListPage("hpSureSenseAdvancedTile"), "HP Sure sense advanced tile not removed from software page");
		Assert.assertFalse(deviceListPage.verifyElementsOfDeviceListPage("unifiedInstallerTile"), "Unified installer tile not removed from software page");

		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("androidDownloadButton"), "Android download button is not present");
		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("protectTraceWolfConnectDownloadButton"), "Download button for Pro Security Aplication not present");

		availableApplications = deviceListPage.getElementsTillAllElementsVisibleofDeviceListPage("availableApplications");
		Assert.assertTrue(deviceListPage.compareSoftwareList(availableApplications, availableApplicationsText), "Sequence of softwares does not match");
		// Assert.assertTrue(androidData.contains(deviceListPage.getTextOfDeviceListPage("androidVersion").split("\\s")[1]), "Android Version does not match");
		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("macVersion").contains(macJsonValues.get(0).getString("sparkle:version")), "Mac version does not match");

		deviceListPage.clickOnElementsOfDeviceListPage("viewMoreVersions");
		deviceListPage.selectElementFromDropDownOfDeviceListPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDHUNDRED));
		LOGGER.info("Change selected option as " + CommonVariables.SELETEDHUNDRED);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		releaseTypeList = deviceListPage.getWebElementsOfDeviceListPage("releaseColumnListDirectLink");
		Assert.assertTrue(deviceListPage.verifyColumnOfSoftwareList(releaseTypeList, LanguageCode), "Release Type column list does not match");

		// HP TechPulse Network Readiness Tool Tile
		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("networkReadlinessTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "download.assessment_tools").replace("{appName}", APP_NAME)), "HP TechPulse Network Readiness Tool title Text does not match");
		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("networkReadlinessDownloadButton").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.download")), "HP TechPulse Network Readiness Tool Download button Text does not match");
		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("networkReadlinessDescription").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "download.assessment_tools.description")), "HP TechPulse Network Readiness Tool description Text does not match");
		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("networkReadlinessDownloadReadme").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "download.assessment_tools.readMe")), "HP TechPulse Network Readiness Tool download Readme link Text does not match");
		LOGGER.info("HP TechPulse Network Readiness Tool application verification passed");

		// Proactive Security software download section
		if (deviceListPage.verifyElementsOfDeviceListPage("proactiveSecuritysectionCollapsed"))
			deviceListPage.clickOnElementsOfDeviceListPage("proactiveSecuritySectionDropDown");
		Assert.assertTrue(getTextLanguage(LanguageCode, "daas_ui", "assets.details.iot.title").equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("protectTraceWolfConnect")), "Proactive security title did not match");
		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("protectTraceWolfConnectDownloadButton"), "Download button for Pro Security Aplication not present");

		//Service now software download section
		Assert.assertTrue(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.serviceNow.title").equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("serviceNowSection")), "Service Now title did not match");
		Assert.assertTrue(getTextLanguage(LanguageCode, "daas_ui", "download.snow").replace("{appName}", "HP Insights").equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("incidentIntegrationHeader")), "HP TechPulse Incident Integration title did not match");
		Assert.assertTrue(getTextLanguage(LanguageCode, "daas_ui", "software.download.serviceNow.description").equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("incidentIntegrationDescription")), "HP TechPulse Incident Integration description did not match");
		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("snowSoftwareReleases"), "Service now software releases not present");

		deviceListPage.navigateToBack();
		LOGGER.info("All test for download application passed");
	}

	/**
	 * This test case will verify the presence of CPU and Memory utilization graphs.
	 *
	 * @throws Exception
	 */
	@Test(priority = 24, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"})
	public final void verifyCPUAndMemoryGraphsOnDeviceDetailPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		environment = System.getProperty("environment");
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

		LOGGER.info(environment);

		gotoDevicesTab();
		resetTableConfiguration();
		sleeper(3000);
		if (environment.equalsIgnoreCase("US-STABLE")) {
			deviceListPage.goToParticularDevice(DeviceVariables.DEVICE_NAME_STABLE);
		} else if (environment.contains("Staging")) {
			getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_ID_CPU"));
		} else if (environment.equalsIgnoreCase("US-PRODUCTION")) {
			deviceListPage.goToParticularDevice(DeviceVariables.DEVICE_NAME_PRODUCTION);
		} else {
			LOGGER.info("The following env cannot be found please check again" + environment);
		}
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("tableOverlay");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("softwareTab");
		deviceDetailsPage.scrollOndeviceDetailsPage("deviceMemoryPerfView");
		sleeper(4000);
		Assert.assertTrue(((deviceDetailsPage.verifyElementsOfDeviceDetailsPage("cpuMemoryUtilizationGraphData")) && (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("cpuPerformanceGraphData")) && (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("memoryPerformanceGraphData")))
				|| ((deviceDetailsPage.verifyElementsOfDeviceDetailsPage("noCpuUtilizationData")) && (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("noCpuPerformanceData")) && (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("noMemoryUtilizationData"))), "The graphs did not load properly spinner observed");
		LOGGER.info("graphs with data/No data for CPU,Memory utilization and CPU,Memory performance are present");
	}

	/**
	 * Marking this test case as false as we covering this functionality under import device test case (method name: verifyRemovalOfNewlyAddedDevice) This test case validates
	 * remove functionality on device list page User Story 380464: [DaaS][UI] Implement additional step/process for critical delete functions in TechPulse
	 *
	 * @throws Exception
	 */
	@Test(priority = 25, groups = {"REGRESSIONDEVICES1", "SMOKE", "REGRESSION_CI", "ALL_CI", "ALL"}, enabled = false)
	public final void verifyRemoveFunctionalityDeviceList() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_DEVICE_LIST_EMAIL", "MSP_DEVICE_LIST_PASSWORD");
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

		// Select company to add a device
		resetTableConfiguration();
		softAssert.assertTrue(deviceListPage.selectCompanyOfDevicePage(importCompany, LanguageCode), "Company selection failed while adding device.");

		// Add device manually
		String serialNumber = generateRandomString(11);
		deviceListPage.addSingleDevice(serialNumber, DeviceVariables.NO_ENROLLMENT);

		// Test Case 1 - This test case validates if the remove button is present when no device is selected
		softAssert.assertFalse(deviceListPage.verifyElementsOfDeviceListPage("removeDeviceButton"), "Remove button is present even when no device is selected");

		// Test Case 2 - This test case validates if the remove button is not present when all support team members are selected
		deviceListPage.clickByJavaScriptOnDeviceListPage("selectAllCheckBox");
		LOGGER.info("Selected all devices");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("removeDeviceButton"), "Remove button is not present when all checkboxes are selected");

		deviceListPage.clickByJavaScriptOnDeviceListPage("selectAllCheckBox");
		LOGGER.info("Deselected all devices");

		// Test Case 3 - This test case validates the cancel functionality on remove device popup
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", serialNumber);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.mousehoverOnDeviceListPage("firstIdLogo");
		deviceListPage.clickByJavaScriptOnDeviceListPage("firstIdCheckBox");
		LOGGER.info("Selected first device");
		deviceListPage.clickOnElementsOfDeviceListPage("removeDeviceButton");
		LOGGER.info("Clicked on remove device");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("removeTitle", getTextLanguage(LanguageCode, "daas_ui", "asset_details.remove_asset")), "Title on remove device popup is incorrect");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("removeMessage", getTextLanguage(LanguageCode, "daas_ui", "assets.list.modal.remove_asset.warning").replace("{assetName}", serialNumber)), "Message on remove device popup is incorrect");
		deviceListPage.clickOnElementsOfDeviceListPage("cancelRemove");
		LOGGER.info("Clicked on cancel remove");
		softAssert.assertFalse(deviceListPage.verifyElementsOfDeviceListPage("noElementsDisplayText"), "Cancel functionality on remove device popup is not working");

		// Test Case 4 - This test case validates the remove functionality of device using remove button on list page
		deviceListPage.clickOnElementsOfDeviceListPage("removeDeviceButton");
		LOGGER.info("Clicked on remove device");
		deviceListPage.clickOnElementsOfDeviceListPage("submitRemove");
		LOGGER.info("Clicked on submit remove");
		deviceListPage.waitForElementsOfDeviceListPage("toastNotification");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("toastNotification", deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "devicelist.remove.success.single")), "Toast notification message on removing device is incorrect");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", serialNumber);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("noElementsDisplayText"), "Submit functionality on remove device popup is not working");

		// Test Case 5 - This test case validates if remove popup appears on clicking the remove option from hamburger menu
		resetTableConfiguration();
		softAssert.assertTrue(deviceListPage.selectCompanyOfDevicePage(importCompany, LanguageCode), "Company selection failed while adding device.");
		deviceListPage.addSingleDevice(serialNumber, DeviceVariables.NO_ENROLLMENT);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", serialNumber);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.mousehoverOnDeviceListPage("firstIdLogo");
		deviceListPage.clickOnElementsOfDeviceListPage("hamburgerIcon");
		LOGGER.info("Clicked on hamburger icon");
		deviceListPage.clickOnElementsOfDeviceListPage("removeAction");
		LOGGER.info("Clicked on remove action");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("removeTitle"), "Remove device does not appear after clicking on remove from hamburger icon");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.clickOnElementsOfDeviceListPage("submitRemove");
		LOGGER.info("Clicked on submit remove");
		deviceListPage.waitForElementsOfDeviceListPage("toastNotification");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("toastNotification", deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "devicelist.remove.success.single")), "Toast notification message on removing device is incorrect");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("noElementsDisplayText"), "Remove functionality after clicking on hamburger icon on remove device popup is not working");

		softAssert.assertAll();
		LOGGER.info("All test cases of remove functionality for device page have passed");
	}

	/**
	 * This test case validates hyperlinks redirection on device list page
	 *
	 * @throws Exception
	 */
	@Test(priority = 26, groups = {"REGRESSIONDEVICES1", "SMOKE", "REGRESSION_CI", "ALL_CI", "ALL"}, description = "[TASK 244672]")
	public final void verifyHyperlinksOnDeviceListPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		List<WebElement> companyLinksList, userLinksList, serialnumberLinksList;
		String urlOfPage = null;
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		resetTableConfiguration();

		// Test Case 1 - This test case verifies successfull redirection of hyperlinks on serial number column
		serialnumberLinksList = deviceListPage.getWebElementsOfDeviceListPage("serialNumberList");
		deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		if (deviceListPage.clickElementsOfDeviceListPage(serialnumberLinksList)) {
			waitForPageLoaded();
			LOGGER.info("Redirected to device details page");
			urlOfPage = deviceListPage.getUrlOfCurrentPage();
			softAssert.assertTrue(urlOfPage.contains("devices"), "Hyperlinks under serial number column does not redirect to device details page");
			navigateToBack();
			LOGGER.info("Back to device list page");
			waitForPageLoaded();
		} else {
			LOGGER.info("No hyperlinks are present under serial number column on device page");
		}

		// Test Case 2 - This test case verifies successfull redirection of hyperlinks on users column
		deviceListPage.scrollTillViewDeviceListPage("userTitle");
		userLinksList = deviceListPage.getWebElementsOfDeviceListPage("userListForHyperlink");
		deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		if (deviceListPage.clickElementsOfDeviceListPage(userLinksList)) {
			waitForPageLoaded();
			LOGGER.info("Redirected to user details page");
			urlOfPage = deviceListPage.getUrlOfCurrentPage();
			softAssert.assertTrue(urlOfPage.contains("users"), "Hyperlinks under users column does not redirect to user details page");
			navigateToBack();
			LOGGER.info("Back to device list page");
			waitForPageLoaded();
		} else {
			LOGGER.info("No hyperlinks are present under users column on device page");
		}

		// Test Case 3 - This test case verifies successfull redirection of hyperlinks on company column
		deviceListPage.scrollTillViewDeviceListPage("companyTitle");
		companyLinksList = deviceListPage.getWebElementsOfDeviceListPage("companyListForHyperlink");
		deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		if (deviceListPage.clickElementsOfDeviceListPage(companyLinksList)) {
			waitForPageLoaded();
			LOGGER.info("Redirected to company details page");
			urlOfPage = deviceListPage.getUrlOfCurrentPage();
			softAssert.assertTrue(urlOfPage.contains("companies"), "Hyperlinks under companies column does not redirect to company details page");
			navigateToBack();
			LOGGER.info("Back to device list page");
		} else {
			LOGGER.info("No hyperlinks are present under companies column on device page");
		}
		softAssert.assertAll();
		LOGGER.info("All test cases of hyperlink redirection from device page have passed");
	}

	/**
	 * This test is used to validate existing ChromeBook are enrolled on DaaS on configuring the ChromeBook Integration It will also check the redirection of OPEN IN EMM TOOL
	 * Button
	 *
	 * @throws Exception
	 */
	@Test(groups = {"REGRESSIONDEVICES1", "SMOKE", "REGRESSION_CI", "ALL_CI", "ALL"}, description = "Testcases 249101, 240603, 242678", enabled = false)
	public final void verifyPreexistingChromebook() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();

		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		CompanyPin companypin = new CompanyPin();
		String companyName = null;
		try {
			// Creating a company
			login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
			gotoRootCompaniesTab();
			companyName = companiesPage.addCompany(false, DeviceVariables.EMM_MSP_NAME, null, LanguageCode);
			sleeper(3000);// takes time after adding an company
			logout();

			// Login to MSP account
			login("MSP_EMM_TEAM_USERNAME", "MSP_EMM_TEAM_PASSWORD");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(companyName);
			goToPreferencesTab();

			// Creating a company pin
			companypin.generateCompanyPin(LanguageCode);

			// Sign in to the Google Admin Portal
			Assert.assertTrue(deviceListPage.signInTotheGooglePortal(PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME, PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD), "Sign in failed");
			deviceListPage.switchBackToPreviousTab();

			// Configuring the ChromeBook Integration on Preferences tab
			preferencesPage.clickOnElementsOfPreferencesPage("chromebookIntegrationEditButton");
			Assert.assertTrue(preferencesPage.enterChromebookIntegrationDetails(PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME, PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD), "Error occured in configuring chromebook integration");
			Assert.assertTrue(preferencesPage.verifyChromeConfigurationSuccessMessage(), "Error occured while saving chromebook configuration");

			// Test Case : 1
			createAndSwitchToNewTab();
			String device = deviceListPage.getChromeDevicesFromGooglePortal();
			switchBackToPreviousTab();
			gotoDevicesTab();
			Assert.assertTrue(deviceListPage.checkChromeDevicePresentOnDaaSPortal(device), "Pre-existing chromebook is not enrolled on DaaS portal");

			// Test Case : 2
			softAssert.assertTrue(deviceListPage.verifyChromeClientApplicationFilter(LanguageCode, device), "Unable to verify Google Chrome Filter");

			// Test Case : 3
			softAssert.assertTrue(deviceDetailsPage.verifyChromeClientApplicationDeviceDetails(device, LanguageCode), "Unable to verify chrome client application on device details page");

			// Test Case : 4
			Assert.assertTrue(deviceDetailsPage.verifyEmmToolButton(), "Failed to validate OPEN IN EMM TOOL button");
			sleeper(2000);
			deviceDetailsPage.mousehoverOnDeviceDetailsPage("deviceName");

			// Test Test : 5
			Assert.assertTrue(deviceDetailsPage.verifyEmmToolRedirection(PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME, PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD, PreferenceVariables.CHROMEBOOK), "Redirection to the Goole Admin Portal falied");

			softAssert.assertAll();
		} finally {
			// Logout from MSP account
			logout();
			// Deleting a Company
			login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
			gotoRootCompaniesTab();
			Assert.assertTrue(companiesPage.removeCompany(companyName, LanguageCode), "Unable to delete company");
		}
	}

	/**
	 * Verify Chromebook fake device details enrolled via Google Admin Portal
	 */
	@Test(groups = {"REGRESSIONDEVICES1", "REGRESSION_CI"}, description = "Testcase 275975", enabled = false)
	public final void verifyGoogleAdminConsoleFakeChromeDeviceDetails() {
		DeviceDetailsPage deviceDetailsPage = null;
		DeviceListPage deviceListPage = null;
		CompaniesPage companiesPage = null;
		CompaniesDetailsPage companyDetailsPage = null;
		EnrollFakeDevice enrollFakeDevice;
		HashMap<String, String> chromeDeviceData = new HashMap<>();
		String companyName = null, companyEmailId = null;
		try {
			deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			enrollFakeDevice = new EnrollFakeDevice().getInstance();
			companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
			// Creating a company
			login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
			gotoRootCompaniesTab();
			companyName = companiesPage.addCompany(false, DeviceVariables.EMM_MSP_NAME, null, LanguageCode);
			sleeper(3000);// takes time after adding an company
			logout();
			login("MSP_EMM_TEAM_USERNAME", "MSP_EMM_TEAM_PASSWORD");
			// Fetch Chromebook device data from google admin portal
			chromeDeviceData = deviceListPage.fetchGoogleAdminChromeDeviceData();
			if (chromeDeviceData != null) {
				gotoDevicesTab();
				resetTableConfiguration();
				deviceListPage.waitForElementsOfDeviceListPage("serialNumberSearchBox");
				deviceDetailsPage.enterTextForDeviceDetailsPage("serialNumberField", chromeDeviceData.get(DeviceVariables.CHROME_SERIAL_NUMBER));
				deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
				if (!deviceListPage.verifyElementsOfDeviceListPage("noElementsDisplayText")) {
					if (deviceListPage.getTextOfDeviceListPage("deviceDetails").equals(chromeDeviceData.get(DeviceVariables.CHROME_SERIAL_NUMBER).toString())) {
						// If real chrome device is present delete device tenant
						LOGGER.info("Device is present hence deleting device tenant");
						deviceListPage.clickOnElementsOfDeviceListPage("deviceDetails");
						companyName = deviceListPage.getTextOfDeviceListPage("existingDeviceTenant");
						LOGGER.info("Deleting company which conatins existing Chromebook device = " + companyName);
						logout();
						login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
						gotoRootCompaniesTab();
						Assert.assertTrue(companiesPage.removeCompany(companyName, LanguageCode), "Unable to delete company");
						companiesPage.waitForElementsOfCompaniesPage("addButton");
						companyName = companiesPage.addCompany(false, DeviceVariables.EMM_MSP_NAME, null, LanguageCode);
						sleeper(3000);// takes time after adding an company
						logout();
						login("MSP_EMM_TEAM_USERNAME", "MSP_EMM_TEAM_PASSWORD");
					} else {
						LOGGER.error("Device " + chromeDeviceData.get(DeviceVariables.CHROME_SERIAL_NUMBER).toString() + " not found");
					}
				} else {
					LOGGER.info("No Real Chromebook device with serial number " + chromeDeviceData.get(DeviceVariables.CHROME_SERIAL_NUMBER).toString() + " found.. enrolling fake device");
				}
				gotoCompaniesTab();
				waitForPageLoaded();
				impersonateCompanyByCompanyName(companyName);
				companyDetailsPage.scrollOnCompaniesDetailsPage("accountingAssignTileHeader");
				companyEmailId = companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPEmailData");
				Assert.assertTrue(enrollFakeDevice.verifyEMMChromeFakeDaasDevice(DeviceVariables.CHROMEBOOK, companyName, companyEmailId, " ", LanguageCode, chromeDeviceData), "Failed to enroll chrome fake device ");
				Assert.assertTrue(deviceListPage.verifyChromeDaasDeviceDetails(chromeDeviceData), "Failed to validate chrome fake device details");
			} else {
				LOGGER.error("No devices are present under google admin console");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyGoogleAdminConsoleFakeChromeDeviceDetails" + e.getMessage());
		} finally {
			// Deleting a company
			try {
				logout();
				login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
				gotoRootCompaniesTab();
				Assert.assertTrue(companiesPage.removeCompany(companyName, LanguageCode), "Unable to delete company " + companyName);
			} catch (Exception e2) {
				LOGGER.error("Exception occured in verifyIntuneFakeEnrollediOSDeviceDetails" + e2.getMessage());
			}
		}
	}

	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "RESELLER_NEW_UI_EMAIL_US";
		data[0][1] = "RESELLER_NEW_UI_PASSWORD_US";
		data[1][0] = "RESELLER_NEW_UI_EMAIL_US_SS";
		data[1][1] = "RESELLER_NEW_UI_PASSWORD_US_SS";
		return data;
	}

	/**
	 * This method will verify that devices list and details page is visible to both partner admin and partner specialist roles
	 *
	 * @throws Exception
	 */
	@Test(priority = 27, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI"}, dataProvider = "getLoginData", description = "US 274719 ; Roles ~ Reseller, Sales Specialist", enabled = false)
	public final void verifyRolesForDevicesTab(String username, String paasword) throws Exception {
		login(username, paasword);
		String deviceName = null;
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

		Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("devicesTab"), "Device tab is not visible to partner admin/specialist");
		LOGGER.info("Device tab is visible to partner admin/specialist");
		gotoDevicesTab();
		sleeper(3000);
		LOGGER.info("Redirected to device list page");
		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitle").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title is incorrect");
		LOGGER.info("Title on device list page is correct");
		deviceName = deviceListPage.getTextOfDeviceListPage("firstDeviceSerialNumber");
		deviceListPage.clickByJavaScriptOnDeviceListPage("firstDeviceSerialNumber");
		waitForPageLoaded();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("tableOverlay");
		if (deviceDetailsPage.getTextOfDeviceDetailsPage("aliasName").equals("-")) {
			Assert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceDetailsTitle").equals(deviceName), "Title on devices details page is incorrect");
		} else {
			Assert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceDetailsTitle").equals(deviceDetailsPage.getTextOfDeviceDetailsPage("aliasName")), "Title on devices details page is incorrect");
		}
		LOGGER.info("Device details page title matched");
		LOGGER.info("All test cases for roles verification for device tab have passed successfully");
	}

	@Test(priority = 28, groups = {"REGRESSIONDEVICES1", "PRODUCTION_CI", "PRODUCTION_SANITY", "INVENTORYINGESTION_PRODUCTIONCI"}, description = "Test Case ID : 280827")
	public final void verifyDeviceListPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoDevicesTab();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("listTable"), "Table on device list page is not loaded successfully");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("serialNumberHeader"), "Default column is not present on device list page");
		softAssert.assertAll();
		LOGGER.info("Device list page is loaded successfully");
	}

	@Test(priority = 29, groups = {"REGRESSIONDEVICES1", "PRODUCTION_CI", "PRODUCTION_SANITY", "INVENTORYINGESTION_PRODUCTIONCI"}, description = "Test Case ID : 280828")
	public final void verifyDeviceDetailsPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		if (deviceListPage.waitForElementsOfDeviceListPage("clearfilter")) {
			deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
			sleeper(3000);
		}
		deviceListPage.clickOnElementsOfDeviceListPage("selectDropdownButton");
		List<WebElement> dropdownvalues = deviceListPage.getElementsOfDeviceListPage("statusdropdownValues");
		deviceListPage.verifyActiveDevicesSelected(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "asset.last_seen.label"), dropdownvalues);
		deviceListPage.clickByJavaScriptOnDeviceListPage("firstDeviceSerialNumber");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("tableOverlay");
		LOGGER.info("Redirected to device details page");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("identificationTileHeader"), "Profile tile on device details page is not loaded successfully");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("systemTile"), "System tile on device details page is not loaded successfully");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("enrollmentTile"), "Enrollment tile on device details page is not loaded successfully");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("preferencesTileHeader"), "Preferences tile on device details page is not loaded successfully");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("warrantyHeader"), "Warranty tile on device details page is not loaded successfully");
		deviceDetailsPage.scrollToTop();
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("incidentTileTitle"), "Incident tile on device details page is not loaded successfully");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("bsodTileTitle"), "BSOD tile on device details page is not loaded successfully");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("hardwareTab");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("tableOverlay");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("hardwareTileHeader"), "Hardware inventory tile on device details page is not loaded successfully");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("softwareTab");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("cpuMemoryUtilizationHeader"), "CPU and memory utilization tile on device details page is not loaded successfully");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("cpuPerformanceHeader"), "CPU performance tile on device details page is not loaded successfully");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("memoryPerformance"), "Memory performance tile on device details page is not loaded successfully");
		softAssert.assertAll();
		LOGGER.info("Device details page is loaded successfully");
	}

	/**
	 * This test will verify reseller device details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 30, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "275727,275728", enabled = false)
	public final void verifyResellerDeviceDetailsPage() throws Exception {
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		resetTableConfiguration();
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");

		// Check device is present in the list
		Assert.assertTrue(VerifyDeviceAvailability(), "Selected device not present in the list");
		// Navigate to device details page
		deviceListPage.goToParticularDevice(deviceName);

		// Verify breadcrumbs
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("deviceBreadCrumbs1", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.assets")), "BreadCrumb1 did not match on device details page");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("deviceBreadCrumbs", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.asset_details")), "BreadCrumb2 did not match on device details page");
		LOGGER.info("Verified breadcrumb on device details page.");

		// Verify remove button is present
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("daasRemoveDevicebutton"), "Remove button is present for a reseller view");

		// Verify profile tile info
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("aliasLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_alias")), "Alias text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editAliasButton"), "Edit button is present for alias field");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("nameLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_name")), "Name label did not match");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("serialNumberLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_serialNumber")), "Serial number text did not match");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("assetTagLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_modal_assetTag")), "Asset tag text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editTagButton"), "Edit button is present for asset tag field");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("costCenterLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_costCenter")), "Cost center label text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editCostButton"), "Edit button is present for cost center field");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("companyLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_company")), "Company label text did not match");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("userLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_user")), "User label text did not match");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("lastSignedInUsernameLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.details.lastLoggedUser")), "Last logged in user label text did not match");
		LOGGER.info("Verified profile tile info on device details page.");

		// Verify system tile info
		deviceDetailsPage.scrollOndeviceDetailsPage("systemTile");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("manufacturerLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.details.system.manufacturer")), "Manufacturer label text did not match");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("manufactureDateLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_manufactureDate")), "Manufacture date label text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editManufacturingDateButton"), "Edit button is present for alias field");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("productLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_product")), "Product label did not match");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("modelLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_model")), "Model label did not match");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("typeLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_type")), "Type label did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editButtonType"), "Edit button is present for type field");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("operationSystemLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_operatingSystem")), "Operating system label	 text did not match");
		LOGGER.info("Verified system tile info on device details page.");

		// Verify enrollment tile info
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("clientApplicationLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.details.enrollment.client_application")), "Client applcation label text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("viewHistoryButton"), "View history button is present under enrollment tile");
		LOGGER.info("Verified enrollment tile info on device details page.");

		// Verify location tile info
		deviceDetailsPage.scrollOndeviceDetailsPage("enrollmentTile");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("locationLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_location")), "Location label text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editLocation"), "Edit button is present for location field");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("departmentLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_department")), "Department label text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editDepartment"), "Edit button is present for department field");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("storeNumberLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_storeNumber")), "Store number text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editStoreNumber"), "Edit button is present for store number field");
		LOGGER.info("Verified location tile info on device details page.");

		// Verify preferences tile info
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("byodLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_byod")), "Byod label text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("preftoggleButton"), "Toggle is present for byod field");
		LOGGER.info("Verified preferences tile info on device details page.");

		// Click on health and protection tab
		deviceDetailsPage.scrollToTop();
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");

		// Verify incident tile
		if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("noDataIncident") == true) {
			LOGGER.info("Incident data is not present on selected device");
		} else if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("incidentWithData") == true) {
			LOGGER.info("Incident data is present on selected device");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("SeeAllIncident");
			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
			LOGGER.info("Redirected to incident page");
		}

		softAssert.assertAll();
	}

	/**
	 * This test will verify Partner Specialist device details page Marking this as false as we need to update this test case for the new fields in details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 31, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "275727,275728", enabled = false)
	public final void verifyPartnerSpecialistDeviceDetailsPage() throws Exception {
		login("SALES_SPECIALIST_USER_NEW_EMAIL", "SALES_SPECIALIST_USER_NEW_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		resetTableConfiguration();
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");

		// Navigate to device details page
		deviceListPage.goToParticularDevice(getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_DEVICE_ID"));

		// Verify breadcrumbs
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("deviceBreadCrumbs1", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.assets")), "BreadCrumb1 did not match on device details page");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("deviceBreadCrumbs", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.asset_details")), "BreadCrumb2 did not match on device details page");
		LOGGER.info("Verified breadcrumb on device details page.");

		// Verify remove button is present
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("daasRemoveDevicebutton"), "Remove button is present for a partner specialist view");

		// Verify profile tile info
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("aliasLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_alias")), "Alias text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editAliasButton"), "Edit button is present for alias field");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("nameLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_name")), "Name label did not match");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("serialNumberLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_serialNumber")), "Serial number text did not match");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("assetTagLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_modal_assetTag")), "Asset tag text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editTagButton"), "Edit button is present for asset tag field");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("costCenterLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_costCenter")), "Cost center label text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editCostButton"), "Edit button is present for cost center field");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("companyLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_company")), "Company label text did not match");
		LOGGER.info("Verified profile tile info on device details page.");

		// Verify system tile info
		deviceDetailsPage.scrollOndeviceDetailsPage("systemTile");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("manufacturerLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.details.system.manufacturer")), "Manufacturer label text did not match");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("manufactureDateLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_manufactureDate")), "Manufacture date label text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editManufacturingDateButton"), "Edit button is present for alias field");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("productLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_product")), "Product label did not match");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("modelLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_model")), "Model label did not match");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("typeLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_type")), "Type label did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editButtonType"), "Edit button is present for type field");

		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("operationSystemLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_operatingSystem")), "Operating system label	 text did not match");
		LOGGER.info("Verified system tile info on device details page.");

		// Verify enrollment tile info
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("clientApplicationLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.details.enrollment.client_application")), "Client applcation label text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("viewHistoryButton"), "View history button is present under enrollment tile");
		LOGGER.info("Verified enrollment tile info on device details page.");

		// Verify preferences tile info
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("byodLabel", getTextLanguage(LanguageCode, "daas_ui", "asset_details_byod")), "Byod label text did not match");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("preftoggleButton"), "Toggle is present for byod field");
		LOGGER.info("Verified preferences tile info on device details page.");

		// Click on health and protection tab
		deviceDetailsPage.scrollToTop();
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");

		// Verify incident tile
		if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("noDataIncident") == true) {
			LOGGER.info("Incident data is not present on selected device");
		} else if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("incidentWithData") == true) {
			LOGGER.info("Incident data is present on selected device");
			softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("SeeAllIncident"), "See all incident link is present for incident tile");
		}
		softAssert.assertAll();
	}

	@Test(priority = 32, groups = {"REGRESSIONDEVICES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "Test Case ID : 280820 ; Roles ~ MSP, Reseller")
	public final void verifyDeviceListPageData() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, DeviceVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for device list page");
		LOGGER.info("device list page is loaded successfully");
	}

	// Need to change test case as per latest change
	@Test(priority = 33, groups = {"REGRESSIONDEVICES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_SANITY", "STABILIZATION_STAGING"}, description = "User Story ID : 268670, User Story ID : 244764, User Story ID : 268671", enabled = false)
	public final void verifyCustomFieldsDevice() throws Exception {

		login("MSP_DEVICE_LIST_EMAIL_COMPANIES", "MSP_DEVICE_LIST_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		String CUSTOM_FIELD_ONE = generateRandomString(11);
		String CUSTOM_FIELD_TWO = generateRandomString(11);
		String CUSTOM_FIELD_THREE = generateRandomString(11);
		String CUSTOM_FIELD_FOUR = generateRandomString(11);
		String CUSTOM_FIELD_FIVE = generateRandomString(11);
		customFields.add(CUSTOM_FIELD_TWO.toLowerCase());
		customFields.add(CUSTOM_FIELD_THREE.toLowerCase());
		customFields.add(CUSTOM_FIELD_FOUR.toLowerCase());
		customFields.add(CUSTOM_FIELD_FIVE.toLowerCase());
		customFields.add(CUSTOM_FIELD_ONE.toLowerCase());

		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesTab();
		LOGGER.info("Redirected to companies list page");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(importCompany);
		LOGGER.info("Redirected to companies details page");
		goToPreferencesTab();
		LOGGER.info("Navigated to preferences tab");

		// Test Case 275551: Verify elements of Custom fields tile on companies details page
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("customFieldsTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.title")), "Title on custom fields tile is incorrect : TEST CASE 280877");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("customFieldsLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.label")), "Label on custom fields tile is incorrect : TEST CASE 280877");
		// Removing already added custom fields
		if (!companyDetailsPage.getTextOfCompaniesDetailsPage("customFieldsValue").equalsIgnoreCase(CompaniesVariables.CUSTOM_FIELDS_NOT_CONFIGURED)) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("customFieldsEditButton");
			List<WebElement> uiList = companyDetailsPage.getElementsOfCompanyDetailsPage("customFieldsAllDeleteIcons");
			companyDetailsPage.clickElementsOfCompanyDetailsPage(uiList);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("customFieldsSave");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			Assert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Already existing custom fields were not removed successfully, cannot proceed further : TEST CASE 280877");
			LOGGER.info("Already existing custom fields removed successfully, test case started");
		} else {
			LOGGER.info("No existing custom fields present");
		}

		// Test Case 280877: [DaaS][UI][Companies] Validate CRUD operations in custom fields in company details page.
		// Verify cancel functionality on custom field popup
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("customFieldsEditButton");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("customFieldsPopupTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.label")), "Title on custom fields popup is incorrect : TEST CASE 280877");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("customFieldsPopupSubTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.popup.subtitle")), "Subtitle on custom fields popup is incorrect : TEST CASE 280877");
		companyDetailsPage.enterTextForCompaniesDetailsPage("firstTextBox", CUSTOM_FIELD_ONE);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("addMoreFieldsLink");
		companyDetailsPage.enterTextForCompaniesDetailsPage("secondTextBox", CUSTOM_FIELD_TWO);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("customFieldsCancel");
		LOGGER.info("Clicked on cancel button of custom fields popup");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("customFieldsValue", "Not Configured"), "Cancel functionality on custom fields popup is not working correctly : TEST CASE 280877");

		// Test Case 280877: [DaaS][UI][Companies] Validate CRUD operations in custom fields in company details page.
		// Adding all custom fields
		companyDetailsPage.addAllCustomFields("customFieldsEditButton", "addMoreFieldsLink", "customFieldsSave", "firstTextBox", "secondTextBox", "thirdTextBox", "fourthTextBox", "fifthTextBox", CUSTOM_FIELD_ONE, CUSTOM_FIELD_TWO, CUSTOM_FIELD_THREE, CUSTOM_FIELD_FOUR, CUSTOM_FIELD_FIVE);
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Save functionality on custom fields popup is not working correctly : TEST CASE 280877");

		// Test Case 280877: [DaaS][UI][Companies] Validate CRUD operations in custom fields in company details page.
		// verify undo functionality on custom fields popup
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("customFieldsEditButton");
		LOGGER.info("Clicked on edit icon of custom fields.");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("firstDeleteIcon");
		LOGGER.info("Clicked on first delete icon of custom fields popup");
		String deprecatedText = companyDetailsPage.getAttributesOfCompaniesDetailsPage("firstDeprecatedCustomField", "style");
		softAssert.assertTrue((deprecatedText.substring(deprecatedText.indexOf("border:"), deprecatedText.indexOf(";"))).contains("red"), "Color on deprecated text after removing custom field is not proper : TEST CASE 280877");
		softAssert.assertTrue(companyDetailsPage.getAttributesOfCompaniesDetailsPage("firstDeprecatedCustomField", "class").contains("deletedCustomField"), "Removed custom field is not deprecated : TEST CASE 280877");
		softAssert.assertTrue(companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("undoIcon"), "Undo icon is not enabled on custom field deletion : TEST CASE 280877");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("warningTextCustomFieldRemoval").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.popup.delete.warning")), "Incorrect warning message is displayed on custom field popup while deleting custom field : TEST CASE 280877");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("undoIcon");
		LOGGER.info("Clicked on undo icon of custom fields popup");
		softAssert.assertTrue(companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("firstDeleteIcon"), "Delete button is not displayed after clicking on undo button : TEST CASE 280877");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("firstDeleteIcon");
		LOGGER.info("Clicked on first delete icon of custom fields popup");
		softAssert.assertTrue(companyDetailsPage.verifyElementIsEnableOfCompaniesDetailsPage("addMoreFieldsLink"), "Add more fields link is not enabled when 5 fields are added and one of them is deleted : TEST CASE 280877");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("addMoreFieldsLink");
		softAssert.assertFalse(companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("undoIcon"), "Undo icon is enabled after addition of 5 fields : TEST CASE 280877");
		companyDetailsPage.enterTextForCompaniesDetailsPage("sixthTextBox", CUSTOM_FIELD_ONE);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("customFieldsCancel");
		LOGGER.info("Clicked on cancel button of custom fields popup");

		// Test Case 280877: [DaaS][UI][Companies] Validate CRUD operations in custom fields in company details page.
		// Verify delete custom field functionality
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("customFieldsEditButton");
		LOGGER.info("Clicked on edit icon of custom fields.");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("firstDeleteIcon");
		LOGGER.info("Clicked on first delete icon of custom fields popup");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("customFieldsSave");
		LOGGER.info("Clicked on save button of custom fields popup");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		softAssert.assertFalse(companyDetailsPage.getTextOfCompaniesDetailsPage("customFieldsValue").contains(CUSTOM_FIELD_ONE), "Custom field deleted is not reflected on details page : TEST CASE 280877");

		// Test Case 280877: [DaaS][UI][Companies] Validate CRUD operations in custom fields in company details page.
		// Verify error field validation messages on custom field popup
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("customFieldsEditButton");
		LOGGER.info("Clicked on edit icon of custom fields.");
		softAssert.assertFalse(companyDetailsPage.getTextOfCompaniesDetailsPage("firstTextBox").equals(CUSTOM_FIELD_ONE), "Custom field not deleted from popup : TEST CASE 280877");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("addMoreFieldsLink");
		companyDetailsPage.enterTextForCompaniesDetailsPage("fifthTextBox", CUSTOM_FIELD_TWO);
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("errorValidationMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.error.1003")), "Duplicate custom field name error validation message is incorrect : TEST CASE 280877");
		companyDetailsPage.clearTextRefreshFromCompaniesDetailsPageTextField("fifthTextBox");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("customFieldsSave");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("errorValidationMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.error.1001")), "Blank custom field name error validation message is incorrect : TEST CASE 280877");
		companyDetailsPage.enterTextForCompaniesDetailsPage("fifthTextBox", CompaniesVariables.CUSTOM_FIELD_SIX);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("customFieldsSave");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("errorValidationMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.error.1004")), "Custom field name containing special characters error validation message is incorrect : TEST CASE 280877");
		companyDetailsPage.enterTextForCompaniesDetailsPage("fifthTextBox", CUSTOM_FIELD_ONE);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("customFieldsSave");
		LOGGER.info("Clicked on save button of custom fields popup");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");

		// Test Case 280877: [DaaS][UI][Companies] Validate CRUD operations in custom fields in company details page.
		// Verify added custom fields on device details page
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		resetTableConfiguration();
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.clickOnElementsOfDeviceListPage("companyBox");
		LOGGER.info("Clicked on company dropdown");
		deviceListPage.enterTextForDeviceListPage("companySearchBox", importCompany);
		sleeper(2000);
		deviceListPage.selectFirstOptionFromDropdownOnDeviceListPage("companyListOnPopup");
		pressKey(Keys.ESCAPE);
		sleeper(3000);
		deviceListPage.clickByJavaScriptOnDeviceListPage("firstDeviceInList");
		LOGGER.info("Redirected on device details page");
		waitForPageLoaded();
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("tableOverlay");
		softAssert.assertTrue(customFields.equals(deviceDetailsPage.getTextOfColumns("customFieldsLabel")), "Incorrect custom fields are displayed on device details page : TEST CASE 280877");
		// Verify custom fields and values are reflected on device details page
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		resetTableConfiguration();
		deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");
		deviceListPage.clickOnElementsOfDeviceListPage("companyBox");
		softAssert.assertTrue(deviceDetailsPage.verifyCompanyChangeOfDeviceListPage(LanguageCode, "companySearchBox", importCompany, "noElementsDisplayText", "companyList", "companyBox"), "Company Change is not working correctly");
		deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");
		softAssert.assertTrue(deviceDetailsPage.verifyCustomFieldsOnDeviceDetailsPage(customFields), "Custom fields did not reflected back on device details page. Test Case ID: 280957");
		softAssert.assertTrue(deviceDetailsPage.verifyCustomfieldValues(LanguageCode), "Custom fields did not reflected back on device details page. Test Case ID: 280957");
		// Verify custom fields are reflected on device list page
		gotoDevicesTab();
		waitForPageLoaded();
		resetTableConfiguration();
		deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");
		deviceListPage.clickOnElementsOfDeviceListPage("companyBox");
		softAssert.assertTrue(deviceDetailsPage.verifyCompanyChangeOfDeviceListPage(LanguageCode, "companySearchBox", importCompany, "noElementsDisplayText", "companyList", "companyBox"), "Company Change is not working correctly");
		deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");
		softAssert.assertTrue(deviceDetailsPage.verifyCustomfieldDeviceListPage(LanguageCode, customFields), "Custom fields on device list page did not reflected successfully.");

		// Verify custom fields are reflected on Table configuration pop up
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		tableConfigurationPage = tableConfigurationPage.getInstance();
		tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		softAssert.assertTrue(deviceDetailsPage.verifyCustomfieldsOnTableConfigurationPopup(LanguageCode, customFields), "Custome fields are not reflected on table configuration pop up.");

		softAssert.assertAll();
		LOGGER.info("Validation of custom fields on Device details page, list page and table configuration pop up got completed successfully.");
	}

	/**
	 * This test case will verify the device Unenroll functionality on device list and details page for MSP admin
	 *
	 * @throws Exception
	 */

	@Test(priority = 34, groups = {"REGRESSIONDEVICES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "Roles ~ MSP")
	public final void verifyDeviceUnenrollButton() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		resetTableConfiguration();
		dashboardPage.clickByJavaScriptOnDashboardPage("companyDropdownGlobalFilter");
		Assert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", preferencesCompany, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "companyDropdownFilter", "globalFilterSave"), "Company Change is not working correctly");
		sleeper(1000);
		deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		// Test Case 2 - This test case validates if the Unenroll button is not present when device is selected
		deviceListPage.clickByJavaScriptOnDeviceListPage("selectAllCheckBox");
		sleeper(2000);
		LOGGER.info("Selected all devices");
		if (deviceListPage.verifyElementsOfDeviceListPage("morebutton")) {
			deviceListPage.clickByJavaScriptOnDeviceListPage("morebutton");
		}
		// Verify unenroll pop up title and message on device list page.
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("unenrollDeviceButton"), "Unenroll button is not present when device is selected");
		deviceListPage.clickOnElementsOfDeviceListPage("unenrollDeviceButton");
		softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("unenrollPopUpTitle").contains(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.unenroll.title")), "Title on Unenroll device popup is incorrect");
		if (deviceListPage.getTextOfDeviceListPage("cancelBtnPopup").equals(getTextLanguage(LanguageCode, "daas_ui", "global.cancel"))) {
			deviceListPage.clickOnElementsOfDeviceListPage("cancelBtnPopup");
		}
		waitForPageLoaded();
		// Verify unenroll pop up title and message on device details page.
		deviceListPage.goToParticularDevice(getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_UNENROLL"));
		sleeper(2000);

		deviceListPage.clickByJavaScriptOnDeviceListPage("listPageMoreButton");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("unenrollButton");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("unenrollButton", getTextLanguage(LanguageCode, "daas_ui", "devicedetail.unenroll.title")), "Unenroll button not found");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("unenrollButton");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("unEnrollPopupTitleDeviceDetail", getTextLanguage(LanguageCode, "daas_ui", "devicedetail.unenroll.title")), "Title on Unenroll device popup is incorrect");
		if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("cancelUnEnrollButtonDeviceDetailPopup"))
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelUnEnrollButtonDeviceDetailPopup");


//		else {
//			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("unenrollButtonOnTile");
//			deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("unenrollButtonOnTile");
//		}
		//softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("unenrollPopUpTitle", getTextLanguage(LanguageCode, "daas_ui", "asset_details.remove_asset")), "Title on Unenroll device popup is incorrect");
		softAssert.assertAll();
		LOGGER.info("All the test cases for device unenroll button have passed successfully.");
	}

	@Test(priority = 36, groups = {"REGRESSIONDEVICES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING"}, description = "Test Case ID : 298967,371630, 379661")
	public final void verifyBIOSTileOnDeviceDetailsPage() throws Exception {

		deleteAndCreateDir(ConstantPath.DOWNLOAD_PATH);
		login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		resetTableConfiguration();
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_NAME_BIOSDRIVER_ID"));
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");

		// Click on BIOS and Drivers tab
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("biosAndDriversTab");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("biosAndDriversTab");
		sleeper(1000);
		LOGGER.info("Clicked on BIOS and Drivers tab");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("biosTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details_bios")), "BIOS tile header text did not match");

		sleeper(3000);// time to load BIOS API

		if (!deviceDetailsPage.verifyElementsOfDeviceDetailsPage("noBIOSDataFound")) {

			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("versionNumber").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.version_number")), "Version Number column text did not match");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("BIOSStatus").equalsIgnoreCase(getTextLanguage(LanguageCode, "lhserver", "global.status")), "BIOS Status column text did not match");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("installationDate").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.installation_date")), "Installation date column text did not match");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("releaseDate").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.release_date")), "Release date column text did not match");
			sleeper(1000);
			deviceDetailsPage.verifyElementIsEnableOfDeviceDetailsPage("statusDropDown");
			deviceDetailsPage.scrollOndeviceDetailsPage("installedVersionsLink");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("installedVersionsLink").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.link.show_previous_version")), "Previous installed versions text did not match");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("installedVersionsLink");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("installedVersionsLink").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.link.hide_previous_version")), "Hide Previous installed versions text did not match");

			deviceDetailsPage.mousehoverOnDeviceDetailsPage("expandBIOSDetailsbutton");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("expandBIOSDetailsbutton");
			deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("expandbuttonBIOS");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("viewDetailsButton");

		} else {
			LOGGER.info("No BIOS history updates were found");
		}
		if (!(getDataFromApiPost("resources", getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.BIOS_INVENTORY, BiosDriversBody, 0).equals(""))) {
			softAssert.assertTrue(getDataFromApiPost("resources.inBiosVersion", getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.BIOS_INVENTORY, BiosDriversBody, 1).equals((deviceDetailsPage.getTextOfDeviceDetailsPage("biosVersionVal").equals("-")) ? "null" : deviceDetailsPage.getTextOfDeviceDetailsPage("biosVersionVal")), "Bios version did not match with the API response");
			LOGGER.info("Verified Bios version");

			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("biosInstalledManufacturer").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.installed_manufacturer")), "BIOS installed manufacturer text did not match");
			softAssert.assertTrue(getDataFromApiPost("resources.deviceMfg", getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.BIOS_INVENTORY, BiosDriversBody, 1).equals((deviceDetailsPage.getTextOfDeviceDetailsPage("biosInstalledManufacturerVal").equals("-")) ? "null" : deviceDetailsPage.getTextOfDeviceDetailsPage("biosInstalledManufacturerVal")), "BIOS installed manufacturer did not match with the API response");
			LOGGER.info("Verified Bios installed manufacturer");

			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("biosInstalledStatus").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.installed_status")), "BIOS installed status text did not match");
			softAssert.assertTrue(getDataFromApiPost("resources.inBiosStatus", getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.BIOS_INVENTORY, BiosDriversBody, 1).equals((deviceDetailsPage.getTextOfDeviceDetailsPage("biosInstalledStatusVal").equals("-")) ? "null" : deviceDetailsPage.getTextOfDeviceDetailsPage("biosInstalledStatusVal")), "BIOS installed status did not match with the API response");
			LOGGER.info("Verified Bios installed status");

			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestVersion").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.latest_version")), "BIOS latest version text did not match");
			softAssert.assertTrue(getDataFromApiPost("resources.latBiosVersion", getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.BIOS_INVENTORY, BiosDriversBody, 1).equals((deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestVersionVal").equals("-")) ? "null" : deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestVersionVal")), "Bios latest version did not match with the API response");
			LOGGER.info("Verified Bios latest version");

			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("biosSettingChanged").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.settings_changed")), "BIOS Setting changed version text did not match");
			if (deviceDetailsPage.getTextOfDeviceDetailsPage("biosSettingChangedVersionVal").equals("-")) {
				softAssert.assertTrue(getDataFromApiPost("resources.biosSettingValue", getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.BIOS_INVENTORY, BiosDriversBody, 1) == null, "Bios Setting changed version did not match with the API response");
			} else {
				softAssert.assertTrue(getDataFromApiPost("resources.biosSettingValue", getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.BIOS_INVENTORY, BiosDriversBody, 1).equals(deviceDetailsPage.getTextOfDeviceDetailsPage("biosSettingChangedVersionVal")), "Bios Setting changed version did not match with the API response");
			}
			LOGGER.info("Verified Bios Setting Changed version");

			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("biosAllSettings").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.bios.all_settings")), "BIOS All Settings version text did not match");
			LOGGER.info("Verified Bios All Setting Version");

			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestVersionCriticality").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.latest_version_criticality")), "BIOS latest version criticality text did not match");
			softAssert.assertTrue(getDataFromApiPost("resources.latBiosCriticality", getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.BIOS_INVENTORY, BiosDriversBody, 1).equals((deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestVersionCriticalityVal").equals("-")) ? "null" : deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestVersionCriticalityVal")), "BIOS latest version criticality did not match with the API response");
			LOGGER.info("Verified Bios latest version criticality");

			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestVersionReleaseNotes").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.latest_release_notes")), "BIOS latest release notes text did not match");
			if (!deviceDetailsPage.verifyElementsOfDeviceDetailsPage("biosLatestVersionReleaseNotesValDash"))
				softAssert.assertTrue(getDataFromApiPost("resources.latestCriticalSpNumber", getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.BIOS_INVENTORY, BiosDriversBody, 1).equals((deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestVersionReleaseNotesVal").equals("-")) ? "null" : deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestVersionReleaseNotesVal")), "BIOS latest release notes url did not match with the API response");
			LOGGER.info("Verified Bios latest release notes");

			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("biosSoftpaqNumber").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.softpaq_number")), "BIOS Softpaq Number text did not match");
			if (!deviceDetailsPage.verifyElementsOfDeviceDetailsPage("biosSoftpaqNumberValDash"))
				softAssert.assertTrue(getDataFromApiPost("resources.spNumber", getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.BIOS_INVENTORY, BiosDriversBody, 1).equalsIgnoreCase((deviceDetailsPage.getTextOfDeviceDetailsPage("biosSoftpaqNumberVal").equalsIgnoreCase("-")) ? "null" : deviceDetailsPage.getTextOfDeviceDetailsPage("biosSoftpaqNumberVal")), "BIOS Softpaq Number url did not match with the API response");
			LOGGER.info("Verified Bios Softpaq Number");

			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestCriticalAvailableVersion").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.latest_critical_version")), "BIOS Latest Critical Available Version text did not match");
			if (!deviceDetailsPage.verifyElementsOfDeviceDetailsPage("biosLatestCriticalAvailableVersionValDash"))
				softAssert.assertTrue(getDataFromApiPost("resources.latCriticalVersion", getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.BIOS_INVENTORY, BiosDriversBody, 1).equalsIgnoreCase((deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestCriticalAvailableVersionVal").equalsIgnoreCase("-")) ? "null" : deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestCriticalAvailableVersionVal")),
						"BIOS Latest Critical Available Version did not match with the API response");
			LOGGER.info("Verified BIOS Latest Critical Available Version");

			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestCriticalAvailableVersionReleaseNotes").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.latest_critical_release_notes")), "BIOS Latest Critical Available Version Release Notes text did not match");
			if (!deviceDetailsPage.verifyElementsOfDeviceDetailsPage("biosLatestCriticalAvailableVersionReleaseNotesValDash"))
				softAssert.assertTrue(getDataFromApiPost("resources.latestCriticalSpNumber", getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.BIOS_INVENTORY, BiosDriversBody, 1).equalsIgnoreCase((deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestCriticalAvailableVersionReleaseNotesVal").equalsIgnoreCase("-")) ? "null" : deviceDetailsPage.getTextOfDeviceDetailsPage("biosLatestCriticalAvailableVersionReleaseNotesVal")),
						"BIOS Latest Critical Available Version release notes url did not match with the API response");
			LOGGER.info("Verified Bios Latest Critical Available Version Release Notes");
			deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("closeExpandButton");
			sleeper(1000);
			refreshPage();
			deviceDetailsPage.verifyElementsOfDeviceDetailsPage("exportButton");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("exportButton");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("toastNotification").equalsIgnoreCase(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "device.details.export.device.data.success")), "Toast Notification text does not match");
			sleeper(20000);// wait for the notification

			deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("notificationBellIcon");
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("firstNotification");
			deviceDetailsPage.mousehoverOnDeviceDetailsPage("hamburgerMenuOnNotification");
			deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("hamburgerMenuOnNotification");
			deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("downloadDeviceDetails");

			sleeper(10000);// wait till file gets downloaded.

			waitForPageLoaded();
			softAssert.assertTrue(deviceDetailsPage.verifyExpectedFieldsAvailableInExportedDeviceDetailsFile(getTextLanguage(LanguageCode, "daas_ui", "asset_details_bios"), getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.label.settings_changed")), "Export functionality on device details got failed for Bios Tabs and Setting changed columns.");
		}
		softAssert.assertAll();
	}

	@Test(priority = 37, groups = {"REGRESSIONDEVICES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "Test Case ID : 298967")
	public final void verifyDriversTileOnBiosAndDriversTab() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		resetTableConfiguration();
		deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");

		// Navigate to device details page
		deviceListPage.goToParticularDevice(deviceName);
		waitForPageLoaded();
		// Click on bios and driver tab
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("biosAndDriversTab");
		LOGGER.info("Clicked on BIOS and Drivers tab");
		waitForPageLoaded();
		sleeper(2000);
		// Verify drivers tile
		Assert.assertTrue(deviceDetailsPage.verifyDriversStatus(LanguageCode, driversBody), "Drivers tile is not present on Bios and Driver tab of device details page");

		if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("driverNameColumn")) {

			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("softpaqNumber"), "SOFTPAQ NUMBER column of the driver is not present");
			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("driverStatus"), "DRIVER STATUS Install version of the driver is not present");
			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("latestDriverStatus"), "LATEST DRIVER STATUS column of the driver is not present");
			LOGGER.info("Driver list is present on the Driver tile");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("driverStatusDropdown");
			ArrayList<String> driverStatusValues = new ArrayList<>();
			driverStatusValues.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.label.outdated"));
			driverStatusValues.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.label.updated"));
			driverStatusValues.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.label.updateAvailable.notSupported"));
			driverStatusValues.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.label.driverversionnotsupportedbyhp"));

			ArrayList<String> DriverStatusdata = new ArrayList<>();
			List<WebElement> elements = deviceDetailsPage.getElementsOfDeviceDetailsPage("driverStatusDropdownValues");

			for (WebElement element : elements) {
				DriverStatusdata.add(element.getText());
			}
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("driverStatusDropdown");
			softAssert.assertTrue(DriverStatusdata.equals(driverStatusValues), "Available driver Status details values are not same as expected driver Status details values.");

			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("latestDriverStatusDropdown");
			ArrayList<String> latestDriverStatusValues = new ArrayList<>();
			latestDriverStatusValues.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.windows_updates.label.critical"));
			latestDriverStatusValues.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "global.recommended"));
			latestDriverStatusValues.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "driver.driver_inventory.title.routine"));
			latestDriverStatusValues.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "global.device_type.unknown"));

			ArrayList<String> latestDriverStatusdata = new ArrayList<>();
			List<WebElement> elements1 = deviceDetailsPage.getElementsOfDeviceDetailsPage("latestDriverStatusDropdownValues");

			for (WebElement element : elements1) {
				latestDriverStatusdata.add(element.getText());
			}
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("latestDriverStatusDropdown");
			softAssert.assertTrue(latestDriverStatusdata.equals(latestDriverStatusValues), "Available latest driver Status details values are not same as expected driver Status details values.");

			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("expandButtonOnDriverTile"), "Driver details information expand button is not available.");
			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("driverName"), "Driver name information is not available.");

			LOGGER.info("Click on driver details expand icon");

			deviceDetailsPage.mousehoverOnDeviceDetailsPage("expandButtonOnDriverTileClick");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("expandButtonOnDriverTileClick");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("viewDetailsButtonOnDriverTile");
			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("installedVersion"), "Install version of the driver is not present");
			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("latestVersion"), "Latest version of the driver is not present");
		} else
			LOGGER.info("Drivers list is not present for the device on the driver tile of Bios and Driver tab");
		softAssert.assertAll();
		LOGGER.info("Verification of Driver tile on Bios and Drivers tab done successfully.");
	}

	/**
	 * This test case verifies Wolf pro security functionality
	 * verifyBromiumFunctionality changes to verifyWolfProSecurityFunctionality
	 *
	 * @throws Exception
	 */
	//MAerked as false Proactive security toggle is hidden and will be removed in future as per User Story 987738: [WorkF][UI] Disable UI for Proactive security reports
	@Test(priority = 38, groups = {"REGRESSIONDEVICES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING"}, description = "Test Case ID : 309626, 315017", enabled = false)
	public final void verifyWolfProSecurityFunctionality() throws Exception {
		login("MSP_ADMIN_US_ST_EMAIl_01", "MSP_ADMIN_US_ST_PASSWORD_01");
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashBoardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		gotoCompaniesTab();
		LOGGER.info("Redirected to company list page");
		waitForPageLoaded();
		resetTableConfiguration();
		impersonateCompanyByCompanyName(preferencesCompany);
		goToPreferencesTab();
		LOGGER.info("Redirected to preferences tab");
		companyDetailsPage.scrollOnCompaniesDetailsPage("reportTileHeader");
		if (companyDetailsPage.getAttributesOfCompaniesDetailsPage("toggleBromium", "aria-checked").equals("false"))
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("toggleBromium");
		else

			LOGGER.info("Bromium toggle is already switched on");

		// Verify HP sure dashboard widgets visibilty
		gotoDashboardTab();
		softAssert.assertTrue(dashBoardPage.verifyElementsOfDashboardPage("hpSureChart"), "HP Sure Click Advanced tile is invisible");
		logout();
		LOGGER.info("Logged out successfully");

		login("ITA_COMPANY_PROACTIVE_SECURITY_REPORTS_EMAIL", "ITA_COMPANY_PROACTIVE_SECURITY_REPORTS_PASSWORD");
		// Verify HP sure dashboard widgets visibilty
		softAssert.assertTrue(dashBoardPage.verifyElementsOfDashboardPage("hpSureChart"), "HP Sure Click Advanced tile is invisible even if toggle is on");
		gotoDevicesCompanyUserTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		//deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "WOLFPRO_DEVICE_ID"));
		LOGGER.info("Redirected to device details page");
		waitForPageLoaded();
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");
		LOGGER.info("Redirected to health and protection tab");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("HPWOlfProSecurityTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.details.health_protection.bromium.pro")), "Title on HP Wolf Pro Security tile is incorrect");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("HPThreatIsolation").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.details.health_protection.bromium.state")), "Label under HP Wolf pro Security tile is incorrect");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("SureSenseProAntimalwareLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.details.health_protection.bromium.sureSenseProAntimalware")), "Label under HP Wolf pro Security tile is incorrect");
		LOGGER.info("Verified bromium functionality with proactive security reports toggled ON");
		logout();

		// LOGGER.info("Logged out successfully");
		login("MSP_ADMIN_US_ST_EMAIl_01", "MSP_ADMIN_US_ST_PASSWORD_01");
		gotoCompaniesTab();
		LOGGER.info("Redirected to company list page");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(preferencesCompany);
		goToPreferencesTab();
		LOGGER.info("Redirected to preferences tab");
		companyDetailsPage.scrollOnCompaniesDetailsPage("reportTileHeader");
		if (companyDetailsPage.getAttributesOfCompaniesDetailsPage("toggleBromium", "aria-checked").equals("true"))
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("toggleBromium");
		else
			LOGGER.info("Bromium toggle is already switched off");

		// Verify HP sure dashboard widgets visibilty
		gotoDashboardTab();
		sleeper(2000);
		softAssert.assertTrue(dashBoardPage.verifyElementsOfDashboardPage("hpSureChart"), "HP Sure Click Advanced tile is invisible");
		logout();
		LOGGER.info("Logged out successfully");

		login("ITA_COMPANY_PROACTIVE_SECURITY_REPORTS_EMAIL", "ITA_COMPANY_PROACTIVE_SECURITY_REPORTS_PASSWORD");

		// Verify HP sure dashboard widgets visibilty
		softAssert.assertFalse(dashBoardPage.verifyElementsOfDashboardPage("hpSureChart"), "HP Sure Click Advanced tile is visible even if toggle is off");

		gotoDevicesCompanyUserTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		resetTableConfiguration();
		sleeper(2000);
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "WOLFPRO_DEVICE_ID"));
		LOGGER.info("Redirected to device details page");
		sleeper(2000);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");
		LOGGER.info("Redirected to health and protection tab");
		sleeper(2000);
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("HPWOlfProSecurityTitle"), "HP Sure Click Advanced tile is present under health and protection tab even when proactive security is toggled off");

		LOGGER.info("Verified bromium functionality with proactive security reports toggled OFF");
		softAssert.assertAll();
		LOGGER.info("Test cases for verifyWolfProSecurityFunctionality passed");
	}

	/**
	 * This test case verifies fields under retail fields tile
	 *
	 * @throws Exception
	 */
	@Test(priority = 39, groups = {"REGRESSIONDEVICES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "Test Case ID : 309622")
	public final void verifyRetailFieldsTile() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String storeNumber, department;
		gotoDevicesTab();
		LOGGER.info("Navigated to device list page");
		resetTableConfiguration();
		deviceListPage.goToParticularDevice(deviceName);
		sleeper(2000);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("locationTab");
		LOGGER.info("Navigated to location tab");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("retailFieldHeader");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("retailFieldHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.details.retail.fields.title")), "Header on retails field tile is incorrect");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("departmentLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details_department")), "Department label on retails field tile is not present");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("storeNumberLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details_storeNumber")), "Store Number label on retails field tile is not present");
		storeNumber = generateRandomString(11);
		department = generateRandomString(11);

		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editDepartment");
		LOGGER.info("Clicked on edit department button");
		sleeper(3000);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("modalHeader");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("modalHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_modal_department")), "Header on department modal of retails field tile is incorrect");
		deviceDetailsPage.enterTextForDeviceDetailsPage("modalTextBox", department);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("modalCancel");
		LOGGER.info("Clicked on cancel button");
		softAssert.assertFalse(deviceDetailsPage.getTextOfDeviceDetailsPage("departmentValue").equals(department), "Cancel functionality for department field is not working");

		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editDepartment");
		LOGGER.info("Clicked on edit department button");
		deviceDetailsPage.enterTextForDeviceDetailsPage("modalTextBox", department);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("modalSubmit");
		LOGGER.info("Clicked on submit button");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("toastNotification");
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("closeToastNotification");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("departmentValue", department), "Save functionality for department field is not working");

		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editStoreNumber");
		LOGGER.info("Clicked on edit store number button");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("modalHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details_storeNumber")), "Header on storeNumber modal of retails field tile is incorrect");
		deviceDetailsPage.enterTextForDeviceDetailsPage("modalTextBox", storeNumber);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("modalCancel");
		LOGGER.info("Clicked on cancel button");
		softAssert.assertFalse(deviceDetailsPage.getTextOfDeviceDetailsPage("storeNumberValue").equals(storeNumber), "Cancel functionality for storeNumber field is not working");

		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("editStoreNumber");
		LOGGER.info("Clicked on edit store number button");
		deviceDetailsPage.enterTextForDeviceDetailsPage("modalTextBox", storeNumber);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("modalSubmit");
		LOGGER.info("Clicked on submit button");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("toastNotification");
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("closeToastNotification");
		softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("storeNumberValue", storeNumber), "Save functionality for storeNumber field is not working");

		gotoDevicesTab();
		deviceListPage.selectAllCheckboxOfPopupForDevicelistPage();
		waitForPageLoaded();
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", deviceName);
		sleeper(2000);
		LOGGER.info("Navigated to device list page");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.scrollOnDeviceListPage("departmentTitle");
		waitForPageLoaded();
		softAssert.assertTrue(deviceListPage.verifyDeviceListTableData("DeviceListHeader", getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.department"), "partialDeviceListDataLocator", department), "Device list table data for column->" + department + " is incorrect");
		softAssert.assertTrue(department.equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("deviceDepartment")), "Updated department value is not reflected on device list page");
		deviceListPage.scrollOnDeviceListPage("storeNumberTitle");
		softAssert.assertTrue(storeNumber.equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("deviceStoreNumber")), "Updated store number value is not reflected on device list page");

		softAssert.assertAll();
		LOGGER.info("Verification of fields under retail fields tile is done successfully");

	}

	/**
	 * This test case validates removal of Devices via CSV file upload. User Story 380464: [DaaS][UI] Implement additional step/process for critical delete functions in TechPulse
	 *
	 * @throws Exception
	 */
	@Test(priority = 40, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI"}, description = "Test Case: 306986", enabled = false)
	public final void verifyRemoveDeviceCSV() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		login("MSP_DEVICE_LIST_EMAIL", "MSP_DEVICE_LIST_PASSWORD");
		gotoDevicesTab();
		resetTableConfiguration();
		String tenantID = getValueFromToken("tenant");
		try {
			notificationCountUnread = deviceListPage.preNotificationCheck();
		} catch (Exception e) {
			LOGGER.error("Exception occured in getting count of unread notification, number of devices : " + e.getMessage());
		}

		ArrayList<String> deviceDetails = new ArrayList<String>();
		String apiurl = cataLystURL + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2;
		String tenantIdCompany = getTenantDetails(apiurl, CommonVariables.SEARCH_SERVICE_IDMTENANTS, importCompany);
		deviceDetails = deviceListPage.addDeviceApi(environment, getEnvironment(environment), tenantIdCompany);
		Assert.assertNotNull(deviceDetails, "Device did not get added via API.");
		resetTableConfiguration();
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", deviceDetails.get(0));
		CSVFileReader csvFileReader = new CSVFileReader();
		File csvFile = new File(ConstantPath.IMPORT_PATH + DeviceVariables.DEVICE_REMOVE_CSV);
		csvFileReader.writeDataToCSVHavingSingleColumn(csvFile, DeviceVariables.DEVICE_UNENROLL_REMOVE_CSV_HEADER, deviceDetails.get(0));
		sleeper(3000);
		Assert.assertFalse(deviceListPage.waitForElementsOfDeviceListPage("noElementsDisplayText"), "Device added via API did not get reflected back on list page.");
		deviceListPage.clickOnElementsOfDeviceListPage("removeDeviceButton");
		deviceListPage.clickOnElementsOfDeviceListPage("allDevicesExport");
		sleeper(2000);
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("removeDevicesTitleText", getTextLanguage(LanguageCode, "daas_ui", "assets.list.modal.remove_asset.title.plural")), "Remove devices title did not match.");
		sleeper(2000);
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("removeDevicesModelLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.remove.header")), "Remove devices label did not match");
		sleeper(2000);
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("removeDevicesModelDescription", getTextLanguage(LanguageCode, "daas_ui", "assets.remove.description")), "Remove devices model description did not match");
		deviceListPage.clickOnElementsOfDeviceListPage("continueButton");
		LOGGER.info("Clicked on continue button");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("removeDevicesModelLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.remove.select.label")), "Remove devices label did not match on CSV import modal.");
		deviceListPage.clickOnElementsOfDeviceListPage("previousButton");
		LOGGER.info("Clicked on previous button");
		deviceListPage.clickOnElementsOfDeviceListPage("allDevicesExport");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("removeDevicesTitleText", getTextLanguage(LanguageCode, "daas_ui", "assets.list.modal.remove_asset.title.plural")), "Remove devices title did not match when came back from CSV import model.");
		deviceListPage.clickOnElementsOfDeviceListPage("continueButton");
		LOGGER.info("Clicked on continue button");
		deviceListPage.setAttributeForDevice("fileImport");
		deviceListPage.enterTextForDeviceListPage("fileImport", ConstantPath.IMPORT_PATH + DeviceVariables.DEVICE_REMOVE_CSV);
		sleeper(2000);
		deviceListPage.clickOnElementsOfDeviceListPage("submitRemove");
		LOGGER.info("Clicked remove button");
		String toastNotification = deviceListPage.getTextOfDeviceListPage("toastNotification");
		LOGGER.info("Generated toast notification after remove CSV import " + toastNotification);
		softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast notification message is incorrect");
		sleeper(13000); // Time required for notification in bell icon.
		softAssert.assertTrue(deviceListPage.postNotificationCheckImportForSuccessfullImport(DeviceVariables.DEVICE_REMOVE_CSV, notificationCountUnread), "Message on notification window is incorrect");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		Assert.assertTrue(deviceListPage.waitForElementsOfDeviceListPage("noElementsDisplayText"), "Device added via API did not get removed from list page.");

		softAssert.assertAll();
		LOGGER.info("Validation of Remove device by CSV completed successfully.");

	}

	/**
	 * This test case validates Network Type tab under devices page
	 *
	 * @throws Exception
	 */
	@Test(priority = 41, groups = {"REGRESSIONDEVICES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING"}, description = "Test Case ID : 288242", enabled = true)
	public final void verifyNetworkTile() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
		Assert.assertTrue(VerifyDeviceAvailability(), "Selected device not present in the list");
		deviceListPage.goToParticularDevice(deviceName);
		LOGGER.info("Redirected to device details page");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("networkTab");
		LOGGER.info("Switched to Network tile");
		if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("noDataIcon")) {
			LOGGER.info("Network data unavailable for the device");
		} else if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("wirelessLANAdapter") || deviceDetailsPage.verifyElementsOfDeviceDetailsPage("ethernetAdapterBluetoothNetwork") || deviceDetailsPage.verifyElementsOfDeviceDetailsPage("ethernet") || deviceDetailsPage.verifyElementsOfDeviceDetailsPage("wi-fi")) {
			LOGGER.info("Network data  available for selected device");
		}
		softAssert.assertAll();
		LOGGER.info("Test case to verify Network Type tile passed successfully");
	}

	/**
	 * This test case verifies device unenroll functionality through import User Story 380464: [DaaS][UI] Implement additional step/process for critical delete functions in
	 * TechPulse
	 *
	 * @throws Exception
	 */
	@Test(priority = 42, groups = {"REGRESSIONDEVICES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "Test Case ID : 306492, 306625", enabled = false)
	public final void verifyUnenrollDeviceUsingImport() throws Exception {
		login("MSP_DEVICE_LIST_EMAIL", "MSP_DEVICE_LIST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		HashMap<String, String> companyDetailsforDeviceEnrollment = new HashMap<String, String>();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesTab();
		resetTableConfiguration();
		LOGGER.info("Redirected to companies tab");
		impersonateCompanyByCompanyName(importCompany);
		CompanyPin companypin = new CompanyPin().getInstance();
		companyDetailsforDeviceEnrollment.put("companyName", companiesPage.getTextOfCompaniesPage("companyNameShow"));
		companyDetailsforDeviceEnrollment.put("companyEmailId", companiesPage.getTextOfCompaniesPage("companyEmailId"));
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		companyDetailsforDeviceEnrollment.put("companyPin", companypin.generateCompanyPin(LanguageCode));
		softAssert.assertTrue(deviceListPage.verifyFakeDeviceListPage(companyDetailsforDeviceEnrollment, LanguageCode), "Unable to validate the Fake Device on Device list page.");
		String deviceSerialNo = DeviceListPage.deviceSerialNo;
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		CSVFileReader csvFileReader = new CSVFileReader();
		File csvFile = new File(ConstantPath.IMPORT_PATH + DeviceVariables.DEVICE_UNENROLL_CSV);
		csvFileReader.writeDataToCSVHavingSingleColumn(csvFile, DeviceVariables.DEVICE_UNENROLL_REMOVE_CSV_HEADER, deviceSerialNo);

		resetTableConfiguration();
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.clickOnElementsOfDeviceListPage("unenrollDeviceButton");
		LOGGER.info("Clicked on unenroll button");
		deviceListPage.clickOnElementsOfDeviceListPage("allDevicesExport");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("unenrollTitle", getTextLanguage(LanguageCode, "daas_ui", "assets.unenroll.title")), "Title on unenroll devices popup is incorrect");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("unenrollHeader", getTextLanguage(LanguageCode, "daas_ui", "assets.unenroll.header")), "Header on unenroll devices popup is incorrect");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("unenrollSubHeader", getTextLanguage(LanguageCode, "daas_ui", "assets.unenroll.description")), "Sub header on unenroll devices popup is incorrect");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("currentSelectionHeader", getTextLanguage(LanguageCode, "daas_ui", "assets.export.selected.label")), "Header under current selection option on unenroll devices popup is incorrect");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("currentSelectionSubHeader", getTextLanguage(LanguageCode, "daas_ui", "assets.unenroll.selected.list")), "Sub header under current selection option on unenroll devices popup is incorrect");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("listOfDevicesHeader", getTextLanguage(LanguageCode, "daas_ui", "assets.unenroll.selected.import.label")), "Header under list of devices option on unenroll devices popup is incorrect");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("listOfDevicesSubHeader", getTextLanguage(LanguageCode, "daas_ui", "assets.unenroll.selected.import")), "Sub header under list of devices option on unenroll devices popup is incorrect");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("cancelButton"), "Cancel button is not present on unenroll devices popup");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("continueButton"), "Continue button is not present on unenroll devices popup");
		deviceListPage.clickOnElementsOfDeviceListPage("continueButton");
		LOGGER.info("Clicked on continue button");
		deviceListPage.setAttributeForDevice("fileImport");
		deviceListPage.enterTextForDeviceListPage("fileImport", ConstantPath.IMPORT_PATH + DeviceVariables.DEVICE_UNENROLL_CSV);
		sleeper(2000);
		deviceListPage.clickOnElementsOfDeviceListPage("unenrollButton");
		LOGGER.info("Clicked unenroll button");
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast notification message is incorrect");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", deviceSerialNo);
		sleeper(10000); // time required to import devices
		deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");
		deviceListPage.scrollOnDeviceListPage("statusColumn");
		LOGGER.info("status of the device after import device CSV unenroll: " + deviceListPage.getTextOfDeviceListPage("statusOfFirstDevice"));
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("statusOfFirstDevice", getTextLanguage(LanguageCode, "daas_ui", "global.inactive")), "Devices unenrolled are not reflected on list page");

		gotoCompaniesTab();
		resetTableConfiguration();
		LOGGER.info("Redirected to companies tab");
		impersonateCompanyByCompanyName(importCompany);
		companypin = new CompanyPin().getInstance();
		companyDetailsforDeviceEnrollment.put("companyName", companiesPage.getTextOfCompaniesPage("companyNameShow"));
		companyDetailsforDeviceEnrollment.put("companyEmailId", companiesPage.getTextOfCompaniesPage("companyEmailId"));
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		companyDetailsforDeviceEnrollment.put("companyPin", companypin.generateCompanyPin(LanguageCode));
		softAssert.assertTrue(deviceListPage.verifyFakeDeviceListPage(companyDetailsforDeviceEnrollment, LanguageCode), "Unable to validate the Fake Device on Device list page.");
		deviceSerialNo = DeviceListPage.deviceSerialNo;
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");

		resetTableConfiguration();
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", deviceSerialNo);
		sleeper(3000);
		deviceListPage.clickByJavaScriptOnDeviceListPage("selectAllCheckBox");
		deviceListPage.clickOnElementsOfDeviceListPage("unenrollDeviceButton");
		LOGGER.info("Clicked on unenroll button");
		deviceListPage.clickOnElementsOfDeviceListPage("continueButton");
		LOGGER.info("Clicked on continue button");
		softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("unenrollDevicePopupHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.unenroll.title")), "Title on unenroll device popup is incorrect");
		softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("unenrollDevicePopupDescription").equals(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.unenroll.confirm_message").replace("{name}", deviceSerialNo)), "Description on unenroll device popup is incorrect");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("unenrollCancelButton"), "Cancel button is not present on unenroll device popup");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("unenrollConfirmButton"), "Confirm button is not present on unenroll device popup");
		deviceListPage.clickOnElementsOfDeviceListPage("unenrollConfirmButton");
		LOGGER.info("Clicked on unenroll button");

		softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "devicelist.unenroll.success.single")), "Toast notification message is incorrect");

		sleeper(5000); // wait for device to get unenrolled
		deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");
		deviceListPage.scrollOnDeviceListPage("statusColumn");
		LOGGER.info("status of the device after single device unenroll: " + deviceListPage.getTextOfDeviceListPage("statusOfFirstDevice"));
		softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("statusOfFirstDevice").equals(getTextLanguage(LanguageCode, "daas_ui", "global.inactive")), "Devices unenrolled are not reflected on list page");
		softAssert.assertAll();
		LOGGER.info("All test cases of verify device unenroll functionality through import passed successfully");
	}

	/**
	 * This method will verify the table configuration for Pending Enrollment Tab
	 *
	 * @throws Exception
	 */
	@Test(priority = 43, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI"}, description = "test case id:332553")
	public final void verifyTableConfigurationFunctionalityPendingEnrollmentTab() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoPendingEnrollmentTab();
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, DeviceVariables.SEARCHSERVICEBODYPENINGTAB, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for pending enrollment tab");
		ArrayList<String> serialNumberId = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.serial_number")));
		ArrayList<String> columnName = new ArrayList<String>(
				Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.serial_number"), getTextLanguage(LanguageCode, "daas_ui", "subscription.section.plans"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.company"), getTextLanguage(LanguageCode, "daas_ui", "assets.tab.pending.time.added"), getTextLanguage(LanguageCode, "daas_ui", "assets.tab.pending.time.ready"), getTextLanguage(LanguageCode, "daas_ui", "global.form.status")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "true"));
		//Commenting the table verification code till the open Bug 521940 is resolved
		//verifyTableConfigurationTests(columnName, checkboxValue, serialNumberId);
	}

	/**
	 * This method validate manual device add functionality under pending enrollment tab
	 *
	 * @throws Exception
	 */
	@Test(priority = 44, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI"}, description = "TC209658")
	public final void verifyAddDeviceManuallyPendingEnrollmentTab() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode), "Error occured in setting dashboard to default mode.");
		gotoPendingEnrollmentTab();
		String zte_Device = generateRandomString(7);
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		deviceListPage.clickOnElementsOfDeviceListPage("allTab");
		Assert.assertTrue(deviceListPage.selectCompanyOfDevicePage(getEnvironmentSpecificData(System.getProperty("environment"), "NONENGLISHCOMPANY"), LanguageCode), "Company selection failed while adding device.");

		deviceListPage.clickOnElementsOfDeviceListPage("previousButton");
		// Verify Add Devices title on Add device pop up
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("addDevicesPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "assets.add.title")), "Title on Add device popup is incorrect");
		// verify choose company message on Add device pop up
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.select_company.label")), "Message on Add device popup is incorrect");
		deviceListPage.clickOnElementsOfDeviceListPage("nextButton");

		// Add single device
		deviceListPage.addSingleDevice(zte_Device, DeviceVariables.AUTO_ENROLLEMNT);
		if (deviceListPage.verifyElementsOfDeviceListPage("clearfilter") == true) {
			deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
		}
		deviceListPage.clickOnElementsOfDeviceListPage("pendingEnrollmentTab");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		// check device-id of added device
		deviceListPage.goToParticularDevice(zte_Device);
		waitForPageLoaded();
		Assert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("serialNumber").equals(zte_Device), "Failed to add device");
		LOGGER.info("Validate serial number on device details page");

		String deviceID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		//verify log details for added device
		gotoLogTab();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		if (logPage.waitForElementsOfLogPage("clearSelectionButton")) {
			logPage.clickOnElementsOfLogPage("clearSelectionButton");
		}
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		logPage.selectLogTypeOfLogPage("Devices");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		logPage.selectLogSubTypeOfLogPage("Add");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		logPage.scrollOnLogPage("objectIdentifier");
		logPage.enterTextForLogPage("objectIdentifier", zte_Device);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		Assert.assertTrue(logPage.getTextOfLogPage("descriptionFirstRow").contains("The device " + zte_Device + " has been successfully added."), "Log description for add device i not correct");

		String deviceDeleteBody = "[{\"id\":\"" + deviceID + "\"}]";
		deviceListPage.deletZTEDeviceUsingAPI(getEnvironment(System.getProperty("environment")) + DeviceVariables.DEVICE_REMOVE, deviceDeleteBody);
		LOGGER.info("Removed added Device");
		//verify device deleted or  not
		gotoDevicesTab();
		waitForPageLoaded();
		gotoPendingEnrollmentTab();
		deviceListPage.goToParticularDevice(zte_Device);
		Assert.assertFalse(deviceDetailsPage.waitForElementsOfDeviceDetailsPage("serialNumber"), "Device is not deleted");
		LOGGER.info("Device serial number on device details page not found");
		softAssert.assertAll();
		LOGGER.info("Validation of Add device manually completed successfully.");
	}

	/**
	 * This test case verifies device import using the csv file for pending tab
	 *
	 * @throws Exception
	 */
	@Test(priority = 45, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI"}, description = "test case ID: 332553", enabled = false)
	public final void verifyImportDevicePendingTab() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_DEVICE_LIST_EMAIL_ZTE", "MSP_DEVICE_LIST_PASSWORD_ZTE");
		gotoDevicesTab();
		gotoPendingEnrollmentTab();
		resetTableConfiguration();
		LOGGER.info("Redirected to pending devices list page");
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

		try {
			notificationCountUnread = deviceListPage.preNotificationCheck();
		} catch (Exception e) {
			LOGGER.error("Exception occured in getting count of unread notification, number of devices : " + e.getMessage());
		}
		Assert.assertTrue(deviceListPage.verifyRemovalOfNewlyAddedDevice(LanguageCode, getEnvironment(System.getProperty("environment"))), "Devices are not removed from list page.");
		Assert.assertTrue(deviceListPage.selectCompanyOfDevicePage(getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_IMPORT_COMPANY_ZTE"), LanguageCode), "Company selection failed while adding device");

		deviceListPage.clickOnElementsOfDeviceListPage("previousButton");
		// Verify Add Devices title on Add device pop up
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("addDevicesPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "assets.add.title")), "Title on Add device popup is incorrect");
		// verify choose company message on Add device pop up
		softAssert.assertTrue(deviceListPage.matchTextOfDeviceListPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "assets.select_company.label")), "Message on Add device popup is incorrect");
		deviceListPage.clickOnElementsOfDeviceListPage("nextButton");

		deviceListPage.verifyImportDevices(LanguageCode, DeviceVariables.DEVICESWITHCORRECTSERIALNUMBERZTE_UPLOAD, DeviceVariables.AUTO_ENROLLEMNT);
		resetTableConfiguration();
		Assert.assertTrue(deviceListPage.postNotificationCheckImportForSuccessfullImport(DeviceVariables.DEVICESWITHCORRECTSERIALNUMBERZTE_UPLOAD, notificationCountUnread), "Notification for device import did not display/delay in notification for more than 30 seconds");
		LOGGER.info("Notification message verification for import has passed");
		pressKey(Keys.ESCAPE);
		Assert.assertTrue(deviceListPage.verifyImportedDevicesOnPendingDeviceListPage(DeviceVariables.DEVICESWITHCORRECTSERIALNUMBERZTE_UPLOAD, LanguageCode), "Devices are not getting reflected on the list page.");
		LOGGER.info("Devices which got imported are getting reflected on list page");

		Assert.assertTrue(deviceListPage.verifyRemovalOfNewlyAddedDevice(LanguageCode, getEnvironment(System.getProperty("environment"))), "Newly added devices are not removed from list page.");
		Assert.assertTrue(deviceListPage.verifyDescriptionOnLogsPage(DeviceVariables.DEVICESWITHCORRECTSERIALNUMBERZTE_UPLOAD), "Description of imported devices on logs page is incorrect");
		LOGGER.info("Description on logs page is correct when devices are imported successfully");

		LOGGER.info("Validation of import device completed successfully.");
		softAssert.assertAll();
	}

	/**
	 * This method validates export device details functionality on device details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 48, groups = {"REGRESSIONDEVICES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING"}, description = "340454")
	public final void verifyExportDeviceDetailsButton() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		deleteAndCreateDir(ConstantPath.DOWNLOAD_PATH);
		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "EXPORT_DEVICE_ID"));
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
		LOGGER.info("Redirected to device details page");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("exportButton");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("toastNotification").equals(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "device.details.export.device.data.success")), "Toast Notification text does not match");
		sleeper(7000);// wait for the notification
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("notificationBellIcon");
		deviceDetailsPage.mousehoverOnDeviceDetailsPage("firstNotification");
		deviceDetailsPage.mousehoverOnDeviceDetailsPage("hamburgerMenuOnNotification");
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("hamburgerMenuOnNotification");
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("downloadDeviceDetails");
		sleeper(5000);// wait till file gets downloaded

		Assert.assertEquals(deviceListPage.getFileCount(ConstantPath.DOWNLOAD_PATH), 1, "Failed to download file");
		softAssert.assertAll();
		LOGGER.info("Test case to verify Export device details button passed successfully");
	}

	/**
	 * This method validates devices in customer and support admin view NFR Story 291623: [QA] Automation- Customer view shows only unenrolled devices, support admin login show
	 * only enrolled devices.
	 *
	 * @throws Exception
	 */
	@Test(priority = 49, groups = {"REGRESSIONDEVICES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "NFR291623")
	public final void verifyDevicesUnderCustomerAndMSPView() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		resetTableConfiguration();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
		Assert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", subCompany, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "allCompanyTextFlexiGlobalFilter", "globalFilterSave"), "Company Change is not working correctly");
		int mSPDeviceCount = Integer.parseInt(deviceListPage.getCountOfDevices());
		logout();

		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		resetTableConfiguration();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
		dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", subCompany, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "allCompanyTextFlexiGlobalFilter", "globalFilterSave");

		waitForPageLoaded();

		int itADeviceCount = Integer.parseInt(deviceListPage.getCountOfDevices());
		Assert.assertTrue(mSPDeviceCount == itADeviceCount, "Devices does not match in Customer and support admin view");
		LOGGER.info("Test case to verifyDevicesUnderCustomerAndMSPView passed successfully");
	}

	/**
	 * This test case will verify the SW Updates Tables on device details page.
	 * Sequence of charts Presence of data Columns in the both SW Updates
	 * tables-Windows and Office updates User Story 354437: [DaaS][API] Missing
	 * software updates tables on device details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 50, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI", "ALL", "ALL_CI",
			"STABILIZATION_STAGING"}, description = "Test Case ID : 343930")
	public final void verifySWUpdatesTablesOnDeviceDetailPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		environment = System.getProperty("environment");
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		resetTableConfiguration();
		sleeper(2000);
		// Navigate to Device Details page-software tab
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_ID_SOFTWARETAB"));
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("softwareTab");
		LOGGER.info("Navigated to Software tab");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
		sleeper(2000);
		// Verify presence of data in MISSING WINDOWS UPDATES table
		softAssert.assertTrue(
				deviceDetailsPage.verifyPresenceOfSWDataOnDeviceDetailsPage("Missing Windows Updates",
						"SWWindowsUpdatesData", "SWWindowsUpdatesNoData"),
				"Grid is not loaded due to some error in MISSING WINDOWS UPDATES table");
		// Verify presence of data in MISSING OFFICE UPDATES table
		softAssert.assertTrue(
				deviceDetailsPage.verifyPresenceOfSWDataOnDeviceDetailsPage("Missing Office Updates",
						"SWOfficeUpdatesData", "SWOfficeUpdatesNoData"),
				"Grid tab is not loaded due to some error in MISSING OFFICE UPDATES table");
		// Verify columns in the SW Updates Missing Windows Updates table
		if (!(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("SWWindowsUpdatesNoData")) && (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("SWOfficeUpdatesNoData"))) {
			String[] columnNameKeys = {"SWDeviceDetails"};
			softAssert.assertTrue(deviceDetailsPage.verifyColumnNamesInSWTable("device-winupdate-list", columnNameKeys,
					LanguageCode, "daas_ui"), "Failed to test columns of Missing Windows Updates table");
			// Verify columns in the SW Updates Missing Office Updates table
			softAssert.assertTrue(deviceDetailsPage.verifyColumnNamesInSWTable("device-ofcupdate-list", columnNameKeys,
					LanguageCode, "daas_ui"), "Failed to test columns of Missing Office Updates table");
		} else {
			LOGGER.info("No data on Missing Windows Updates and Missing Office Updates");
		}
		softAssert.assertAll();
		LOGGER.info("SW Updates tables on software tab of device details page tested successfully");
	}

	/**
	 * This test case will validate warranty tile on non-hp devices
	 *
	 * @throws Exception
	 */
	@Test(priority = 51, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "TCID233239")
	public final void verifyWarrantyTileForNonHPDevice() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		gotoDevicesTab();
		resetTableConfiguration();
		String warrantyName = generateRandomString(5);
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_ID_NONHP"));
		waitForPageLoaded();

		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		sleeper(2000);
		// Scroll down to warranty tile
		deviceDetailsPage.scrollOndeviceDetailsPage("warrantyTile");
		Assert.assertTrue(deviceDetailsPage.validateWarrantyRemoval("warrantyDeleteButton"), "Warranty removal failed");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("addWarrantyButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("addWarrantyButton");
		deviceDetailsPage.enterTextForDeviceDetailsPage("warrantyTextbox", warrantyName);
		deviceDetailsPage.enterTextForDeviceDetailsPage("warrantyDescription", warrantyName);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("warrantyStartDate");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("warrantyStartDate");
		deviceDetailsPage.selectDateFromCalenderOnDeviceDetailpage(addDaysToCurrentDate(0), "monthKeyCurrent", "endDateDialogRightArrow", "daysOnCurrentMonthKey");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("warrantyEndDate");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("warrantyEndDate");
		deviceDetailsPage.selectDateFromCalenderOnDeviceDetailpage(addDaysToCurrentDate(5), "monthKeyCurrent", "endDateDialogRightArrow", "daysOnCurrentMonthKey");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("saveWarrantyButton");
		LOGGER.info("Added warranty using current date");

		String toastMessage = deviceDetailsPage.getTextOfDeviceDetailsPage("toastNotificationKey");
		Assert.assertTrue(toastMessage.equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_success").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "incident.details.device_warranty"))), "Failed to add warranty");
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("closeToastNotification");

		deviceDetailsPage.scrollOndeviceDetailsPage("moreDetailsButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("moreDetailsButton");
		sleeper(1000);
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("warrantyLabel").equalsIgnoreCase(warrantyName), "Warranty name did not match on details page");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("newWarrantyStatus").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Warranty status did not match on details page");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("moreDetailsButton").equals(warrantyName), "Warranty desc did not match on details page");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("removeWarranty"), "Remove button missing on details page");
		LOGGER.info("Validate warranty data on details page");

		gotoDevicesTab();
		sleeper(4000);
		tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");

		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_NONHP"));
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.scrollOnDeviceListPage("activeWarrantyColumn");
		sleeper(1000);
		if (environment.equalsIgnoreCase("US-STAGING")) {
			softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("activeWarrantyupdated").equalsIgnoreCase(warrantyName), "Warranty name did not match on list page");
		} else {
			softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("activeWarranty").equalsIgnoreCase(warrantyName), "Warranty name did not match on list page");
		}
		LOGGER.info("Validate warranty data on list page");

		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_ID_NONHP"));
		waitForPageLoaded();
		Assert.assertTrue(deviceDetailsPage.validateWarrantyRemoval("warrantyDeleteButton"), "Warranty removal failed");

		softAssert.assertAll();
	}

	/**
	 * This test case validates last restart date field on device details page. User Story 386845: [DaaS][Analytics] Add last restart date to the Device Details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 52, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI", "ALL", "ALL_CI", "STABILIZATION_STAGING"}, description = "Test Case ID : 408654")
	public final void verifyLastRestartDateFieldOnDeviceDetailsPAge() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		resetTableConfiguration();
		deviceListPage.goToParticularDevice(getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_NAME_LAST_RESTART_DATE"));
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
		if (deviceListPage.verifyElementsOfDeviceListPage("lasteRestartDateTitle")) {
			softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("lasteRestartDateTitle", getTextLanguage(LanguageCode, "daas_ui", "assets.details.location.last_restart")), "Last restart date title did not match");
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd, yyyy");
			softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("lasteRestartDateValue", sdf2.format(sdf.parse(getDataFromApiPost("resources.lastRestartDate", getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") + DeviceVariables.LAST_RESTART_DATE, LastRestartDateBody, 1)))), "Last restart date value did not match");
		} else {
			LOGGER.info("Last restart date field is not available for selected device");
		}
		softAssert.assertAll();
		LOGGER.info("Test case to verify Last restart date field on device details page passed successfully.");
	}

	/**
	 * This test case validates devices tabs under customer view
	 *
	 * @throws Exception
	 */
	@Test(priority = 53, groups = {"REGRESSIONDEVICES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "Test Case ID : 332553")
	public final void verifyDeviceTabITAdminView() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoDevicesTab();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("allTab"), "All tab is not present under customer view");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("pendingEnrollmentTab"), "Pending enrollment tab is not present under customer view");
		softAssert.assertAll();
	}

	/**
	 * This test case validates the device list and details page for suspended company User Story 367359: [Core][UI] Limit the view of devices for suspended company.
	 *
	 * @throws Exception
	 */
	@Test(priority = 54, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI", "ALL", "ALL_CI", "STABILIZATION_STAGING"}, description = "Test Case ID : 415832")
	public final void verifyDeviceListAndDetailsPageForSuspendedCompany() throws Exception {
		login("SUSPENDED_COMPANY_EMAIL", "SUSPENDED_COMPANY_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String currentUrl, tenantID;
		gotoDevicesTab();
		resetTableConfiguration();
		waitForPageLoaded();

		// verify add device functionality for suspended company
		String serialNumber = generateRandomString(11);
		deviceListPage.clickOnElementsOfDeviceListPage("addButton");
		waitForPageLoaded();
		deviceListPage.addSingleDevice(serialNumber, DeviceVariables.NO_ENROLLMENT);
		LOGGER.info("Verified add device functionality for suspended company");

		ArrayList<String> serialNumberId = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.serial_number")));
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.serial_number"), getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptionPlan"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.alias_name"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.type"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.enrollment"),
				getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.enrolled"), getTextLanguage(LanguageCode, "daas_ui", "global.form.status"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.asset_tag"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.bromium"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.cost_center"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.name"),
				getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.active_warranty"), getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.last_signed_user")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "false", "true", "true", "false", "false", "false", "false", "false", "false"));
		//Commenting the table verification code till the open Bug 521940 is resolved
//		verifyTableConfigurationTests(columnName, checkboxValue, serialNumberId);

		deviceListPage.goToParticularDevice(serialNumber);
		waitForPageLoaded();

		// verify device details page for suspended company
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deleteDeviceButton"), "Remove button not present on Identity tile");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("profileTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.profile_title").toUpperCase()), "Profile tile not present on device details page");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editAliasButton"), "Edit Alias button not present on device details page");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editTagButton"), "Edit Asset tag button not present on device details page");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editCostButton"), "Edit Cost center button not present on device details page");
		deviceDetailsPage.scrollOndeviceDetailsPage("systemTile");

		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("systemTile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.details.general_informations.system").toUpperCase()), "System tile not present on device details page");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editManufacturingDateButton"), "Edit Manufacture date button not present on device details page");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("editTypeButton"), "Edit Type button not present on device details page");

		deviceDetailsPage.scrollOndeviceDetailsPage("enrollmentTile");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("enrollmentTile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.add.enroll.label").toUpperCase()), "Enrollment tile not present on device details page");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("warrantyTile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.details.general_informations.warranty").toUpperCase()), "Warranty tile not present on device details page");

		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("healthAndProtectionTab"), "Health and Protection tab is available for Suspended company");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("hardwareTab"), "Hardware tab is available for Suspended company");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("networkTab"), "Network tab is available for Suspended company");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("biosAndDriversTab"), "Bios and Drivers tab is available for Suspended company");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("locationTab"), "Location tab is available for Suspended company");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("softwareTab"), "Software tab is available for Suspended company");

		// Delete newly added device
		currentUrl = deviceDetailsPage.getUrlOfCurrentPage();
		tenantID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
		String deviceDeleteBody = "[{\"id\":\"" + tenantID + "\"}]";
		deviceListPage.deletZTEDeviceUsingAPI(getEnvironment(System.getProperty("environment")) + DeviceVariables.DEVICE_REMOVE, deviceDeleteBody);
		LOGGER.info("Removed newly added device");

		softAssert.assertAll();
		LOGGER.info("Test case to verify Device list and details page for suspended company passed successfully.");

	}

	/**
	 * This test case validates devices BIOS and drivers tile for a PC company
	 *
	 * @throws Exception
	 */
	@Test(priority = 55, groups = {"REGRESSIONDEVICES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "Test Case ID : 436429")
	public final void verifyBIOSTileOnDeviceDetailsPageForPCCompany() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoDevicesTab();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		resetTableConfiguration();
		//deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceInList");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_NAME"));
		sleeper(2000);
		deviceListPage.clickOnElementsOfDeviceListPage("seclectSerialNumber");
		waitForPageLoaded();
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("biosAndDriversTab");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("biosAndDriversTab");
		LOGGER.info("Clicked on BIOS and Drivers tab");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("biosTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details_bios")), "BIOS tile header text did not match");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("driversHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.driver.table.title").toUpperCase()), "Drivers tile header text did not match");
		softAssert.assertAll();
	}

	/**
	 * This method validate ZTE device status through SNR
	 *
	 * @throws Exception
	 */
	@Test(priority = 56, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI", "STABILIZATION_STAGING"}, description = "NFR STORY 441987,TEST CASE 440192")
	public final void verifyPendingDevicesStatusUsingSNR() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		gotoPendingEnrollmentTab();
		LOGGER.info("Redirected to pending enrollment tab");
		String zTEDeviceBody = "[{\"alias\":\"\",\"serialNumber\"" + ":" + "\"" + getEnvironmentSpecificData(System.getProperty("environment"), "ZTE_DEVICE_SNR") + "\"}]";
		deviceListPage.addZTEDeviceUsingAPI(getEnvironment(System.getProperty("environment")) + DeviceVariables.ZTE_DEVICE_URL + snrcompanytenantid + DeviceVariables.ZTE_DEVICE_RESOURCE, zTEDeviceBody);
		LOGGER.info("Added ZTE type device using API");
		refreshPage();
		deviceListPage.waitForElementsOfDeviceListPage("serialNumberSearchBox");
		deviceListPage.getElementOfDeviceListPage("serialNumberSearchBox").clear();
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "ZTE_DEVICE_SNR"));
		sleeper(2000);// wait till device reflect on device list
		deviceListPage.scrollOnDeviceListPage("statusColumn");
		refreshPage();
		sleeper(4000);// wait till device status reflect on device list
		deviceListPage.scrollOnDeviceListPage("statusValue");
		softAssert.assertTrue((deviceListPage.getTextOfDeviceListPage("statusValue").equals(getTextLanguage(LanguageCode, "daas_ui", "global.error"))), "Newly added ZTE device is not in error state");
		deviceListPage.goToParticularDevice(getEnvironmentSpecificData(System.getProperty("environment"), "ZTE_DEVICE_SNR"));
		String tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 43);
		String deviceDeleteBody = "[{\"id\":\"" + tenantID + "\"}]";
		deviceListPage.deletZTEDeviceUsingAPI(getEnvironment(System.getProperty("environment")) + DeviceVariables.DEVICE_REMOVE, deviceDeleteBody);
		LOGGER.info("Removed added device");
		softAssert.assertAll();
	}

	/**
	 * This test case validates URL specific keywords for each tab present in device details page. Validation by hitting required URL and landing on specific page. Validation by
	 * hitting invalid URL.
	 *
	 * @throws Exception
	 */
	@Test(priority = 57, groups = {"REGRESSIONDEVICES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "Test Case ID : 312492")
	public final void verifyDeviceDetailsTabUrls() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		String currentUrl = null;
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		ErrorPage errorPage = new ErrorPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_DETAILS_ID"));
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("tableOverlay");
		LOGGER.info("Redirected to device details page");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");
		currentUrl = getUrlOfCurrentPage();
		softAssert.assertTrue(currentUrl.contains("device-health-protection"), "Health And Protection tab does not have expected keyword.");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("hardwareTab");
		currentUrl = getUrlOfCurrentPage();
		softAssert.assertTrue(currentUrl.contains("device-hardware"), "Hardware tab does not have expected keyword.");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("biosAndDriversTab");
		currentUrl = getUrlOfCurrentPage();
		softAssert.assertTrue(currentUrl.contains("deviceBiosAndDriversTab"), "Bios and Drivers tab does not have expected keyword.");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("softwareTab");
		currentUrl = getUrlOfCurrentPage();
		softAssert.assertTrue(currentUrl.contains("device-software"), "Software tab does not have expected keyword.");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("locationTab");
		currentUrl = getUrlOfCurrentPage();
		int attempts = 0;
		while (currentUrl != null && !currentUrl.contains("device-location-tab") && attempts < 10) {
			currentUrl = getUrlOfCurrentPage();
			attempts++;
			sleeper(1000);
		}
		softAssert.assertTrue(currentUrl.contains("device-location-tab"), "Location tab does not have expected keyword.");
		logout();
		waitForPageLoaded();
		getUrl(currentUrl);
		waitForPageLoaded();
		welcomePage.waitForElementsOfWelcomePage("signInButton");
		welcomePage.clickOnElementsOfWelcomePage("signInButton");
		loginCustom("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		currentUrl = getUrlOfCurrentPage();
		softAssert.assertTrue(currentUrl.contains("device-location-tab"), "Location tab does not have expected keyword after logout and login with custom URL.");
		getUrl(currentUrl + "/randomTextinURL");
		errorPage.waitForElementsOfErrorPage("errorIcon");
		softAssert.assertTrue(errorPage.verifyElementsOfErrorPage("errorIcon"), "Hp icon is not present on the 404 error page");
		softAssert.assertTrue(errorPage.matchTextOfErrorPage("errorMessage", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.heading.404")), "Error message on 404 error page is incorrect");
		softAssert.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line1.404")), "No permission title 1 on 404 error page is incorrect");
		softAssert.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle2", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line2.404")), "No permission title 2 on 404 error page is incorrect");
		errorPage.clickOnElementsOfErrorPage("goBackSignIn");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyDropdown");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "After clicking on GO TO HOME PAGE button, user is not redirected to dashboard page");
		softAssert.assertAll();
		LOGGER.info("Validation of Device details tab URLs completed successfully");
	}

	/**
	 * This method validate same asset addition under multiple tenants and their warranty being different for both.
	 *
	 * @throws Exception
	 */
	@Test(priority = 58, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI"}, description = "Feature:442756,User Story: 476151")
	public final void verifyMultipleAssetAddition() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode), "Error occured in setting dashboard to default mode.");
		gotoDevicesTab();
		resetTableConfiguration();
		String manual_Device = generateRandomString(7);
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		Assert.assertTrue(deviceListPage.selectCompanyOfDevicePage(getEnvironmentSpecificData(System.getProperty("environment"), "NONENGLISHCOMPANY"), LanguageCode), "Company selection failed while adding device.");
		// Add 1st device
		deviceListPage.addSingleDevice(manual_Device, DeviceVariables.NO_ENROLLMENT);
		deviceListPage.goToParticularDevice(manual_Device);
		Assert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("serialNumber").equalsIgnoreCase(manual_Device), "Serial number not matching on details page for 1st device");
		Assert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceStatus").equalsIgnoreCase(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "global.inactive")), "Status not inactive on details page");

		// Add warranty section pending for vineer3 changes due to bug 603063
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("addWarrantyButton");
		deviceDetailsPage.enterTextForDeviceDetailsPage("warrantyText", "Test warranty");
		deviceDetailsPage.enterTextForDeviceDetailsPage("warrantyDescription", "Warranty test");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("warrantyStartDate");
		deviceDetailsPage.selectDateFromCalenderOnDeviceDetailpage(addDaysToCurrentDate(0), "monthKeyCurrent", "endDateDialogRightArrow", "daysOnCurrentMonthKey");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("warrantyEndDate");
		deviceDetailsPage.selectDateFromCalenderOnDeviceDetailpage(addDaysToCurrentDate(5), "monthKeyCurrent", "endDateDialogRightArrow", "daysOnCurrentMonthKey");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("saveWarrantyButton");
		LOGGER.info("Added warranty using current date");

		String toastMessage = deviceDetailsPage.getTextOfDeviceDetailsPage("toastNotificationwarrenty");
		Assert.assertTrue(toastMessage.equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_success").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "incident.details.device_warranty"))), "Failed to add warranty");
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("closeToastNotification");
		gotoDevicesTab();

		Assert.assertTrue(deviceListPage.selectCompanyOfDevicePage(multipleAssetAddComp, LanguageCode), "Company selection failed while adding device.");
		// Add 2nd device
		deviceListPage.addSingleDevice(manual_Device, DeviceVariables.NO_ENROLLMENT);

		dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
		Assert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", multipleAssetAddComp, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "allCompanyTextFlexiGlobalFilter", "globalFilterSave"), "Company Change is not working correctly");
		sleeper(5000);
		deviceListPage.clickByJavaScriptOnDeviceListPage("firstDeviceInList");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
		Assert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("serialNumber").equalsIgnoreCase(manual_Device), "Serial number not matching on details page for 2nd device");
		Assert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceStatus").equalsIgnoreCase(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "global.inactive")), "Status not inactive on details page");
		// Add warranty section pending for vineer3 changes due to bug 603063
		Assert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("warrantyLabel"), "Warranty added for 1st device visible to 2nd device as well.");
		gotoDevicesTab();

		sleeper(3000);
		dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
		Assert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", getEnvironmentSpecificData(System.getProperty("environment"), "NONENGLISHCOMPANY"), "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "allCompanyTextFlexiGlobalFilter", "globalFilterSave"), "Company Change is not working correctly");

		sleeper(5000);
		deviceListPage.waitForElementsOfDeviceListPage("firstDeviceInList");
		deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceInList");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
		String deviceID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		Assert.assertTrue(getStatusCode(getEnvironment(System.getProperty("environment")) + DeviceVariables.SINGLE_DEVICE_REMOVE + deviceID, "{}", "DELETE", environment) == 204, "Device removal failed");
		LOGGER.info("Removed 1st Device");

		gotoDevicesTab();
		deviceListPage.waitForElementsOfDeviceListPage("firstDeviceInList");
		dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
		Assert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", multipleAssetAddComp, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "allCompanyTextFlexiGlobalFilter", "globalFilterSave"), "Company Change is not working correctly");
		sleeper(5000);
		deviceListPage.waitForElementsOfDeviceListPage("firstDeviceInList");
		deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceInList");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
		deviceID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		Assert.assertTrue(getStatusCode(getEnvironment(System.getProperty("environment")) + DeviceVariables.SINGLE_DEVICE_REMOVE + deviceID, "{}", "DELETE", environment) == 204, "Device removal failed");
		LOGGER.info("Removed 2nd Device");

		softAssert.assertAll();
		LOGGER.info("Validation of Adding same asset under multiple tenants completed successfully.");
	}

	/**
	 * This method validate manual device add functionality under pending enrollment tab and then unsuccessful addition of same asset under another tenant.
	 *
	 * @throws Exception
	 */
	@Test(priority = 59, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI"}, description = "Feature:442756")
	public final void verifySameDeviceAdditionUnderDifferentTenant() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode), "Error occured in setting dashboard to default mode.");
		gotoPendingEnrollmentTab();
		String zte_Device = generateRandomString(9);
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		deviceListPage.clickOnElementsOfDeviceListPage("allTab");
		sleeper(2000);
		softAssert.assertTrue(deviceListPage.selectCompanyOfDevicePage(getEnvironmentSpecificData(System.getProperty("environment"), "MULTIPLE_ASSET_ADDITION_COMPANY"), LanguageCode), "Company selection failed while adding device.");
		// Add single device
		deviceListPage.addSingleDevice(zte_Device, DeviceVariables.AUTO_ENROLLEMNT);
		// check device-id of added device
		deviceListPage.clickOnElementsOfDeviceListPage("pendingEnrollmentTab");
		waitForPageLoaded();
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", zte_Device);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		waitForPageLoaded();
		sleeper(2000);
		deviceListPage.clickOnElementsOfDeviceListPage("seclectSerialNumber");
		waitForPageLoaded();
		Assert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("serialNumber").equals(zte_Device), "Failed to add device");
		LOGGER.info("Validate serial number on device details page");
		gotoDevicesTab();
		//Assert.assertTrue(deviceListPage.selectCompanyOfDevicePage(multipleAssetAddComp, LanguageCode), "Company selection failed while adding device.");
		softAssert.assertTrue(deviceListPage.selectCompanyOfDevicePage(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_FOR_DEVICE_LOG"), LanguageCode), "Company selection failed while adding device.");

		// Add 2nd device
		deviceListPage.verifyFailureInDeviceAddition(zte_Device, DeviceVariables.AUTO_ENROLLEMNT, LanguageCode);
		deviceListPage.clickOnElementsOfDeviceListPage("downloadClose");
		deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
		deviceListPage.clickOnElementsOfDeviceListPage("pendingEnrollmentTab");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", zte_Device);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		sleeper(2000);
		deviceListPage.clickOnElementsOfDeviceListPage("seclectSerialNumber");
		String deviceID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		String deviceDeleteBody = "[{\"id\":\"" + deviceID + "\"}]";
		deviceListPage.deletZTEDeviceUsingAPI(getEnvironment(System.getProperty("environment")) + DeviceVariables.DEVICE_REMOVE, deviceDeleteBody);
		//verify device deleted
		LOGGER.info("Validate serial number on device details page");
		gotoDevicesTab();
		deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
		deviceListPage.clickOnElementsOfDeviceListPage("pendingEnrollmentTab");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", zte_Device);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		sleeper(2000);
		softAssert.assertFalse(deviceListPage.matchTextOfDeviceListPage("seclectSerialNumber", zte_Device), "After delete, device still found in device list");
		LOGGER.info("Removed added Device");
		softAssert.assertAll();
		LOGGER.info("Validation of Add device manually completed successfully.");
	}

	/**
	 * This test case is used to verify HPSureRunTile
	 *
	 * @throws Exception
	 */
	@Test(priority = 60, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "230237", enabled = false)
	public final void verifyHPSureRunTile() throws Exception {
		login("MSP_ADMIN_US_ST_EMAIl_01", "MSP_ADMIN_US_ST_PASSWORD_01");
		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		LOGGER.info("Redirected to Devices tab");

		// Redirected to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_ID"));

		// Click on health and protection tab
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");
		LOGGER.info("Clicked on health and protection tab");

		// Verify tile header text
		Assert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("hpSureRunTile"), "HP Sure Run tile missing");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("hpSureRunTileTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "assets.details.automatic_health_diagnostics")), "HP Sure Run tile header does not match");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("hpSureRunTileWarning").equals(getTextLanguage(LanguageCode, "daas_ui", "assets.details.automatic_health_diagnostics.warning")), "HP Sure Run warning text does not match");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("automaticHealthDiagnostics").equals(getTextLanguage(LanguageCode, "daas_ui", "assets.details.automatic_health_diagnostics.label")), "HP Sure Run label does not match");

		// Verify Automatic Health Diagnostics
		Assert.assertTrue(deviceDetailsPage.verifyAutomaticHealthDiagnosticsonDeviceDetails(deviceDetailsPage.getTextOfDeviceDetailsPage("automaticHealthDiagnosticsVal")), "HP Sure Run status is incorrect on device details page");

		softAssert.assertAll();
		LOGGER.info("verifyHPSureRunTile test case is successfully completed");
	}

	/**
	 * Verifies lock and erase menu icon
	 *
	 * @throws Exception Marking the test as enabled = false as this can be automated with help of real device .. Fake device this will not work
	 */
	@Test(priority = 61, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI"}, description = "NFR STORY 429187,USER STORY 350806,TEST CASE 591588", enabled = false)
	public final void verifyLockAndEraseMenuIcon() throws Exception {
		login("ITA_DEVICE_EMAIL_US", "ITA_DEVICE_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		PreferencesPage preferencePage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoSettingsTabOfReportAdmin();
		preferencePage.clickOnElementsOfPreferencesPage("preferencesTab");

		if (preferencePage.getTextOfPreferencesPage("tenantStatus").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			sleeper(2000);
			preferencePage.waitForElementsOfPreferencesPage("editPencilIcon");
			preferencePage.clickOnElementsOfPreferencesPage("editPencilIcon");
			preferencePage.clickOnElementsOfPreferencesPage("flipEnableToggle");
			preferencePage.verifyElementIsClickableOfPreferencesPage("buttonCancel");
			preferencePage.clickOnElementsOfPreferencesPage("buttonSave");

			gotoDevicesTab();
			resetTableConfiguration();
			deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "FLIP_DEVICE"));
			sleeper(2000);
			deviceListPage.clickOnElementsOfDeviceListPage("seclectSerialNumber");

			sleeper(2000);
			sleeper(2000);
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("moreButton");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("moreButton");
			gotoSettingsTabOfReportAdmin();
			preferencePage.clickOnElementsOfPreferencesPage("preferencesTab");
			preferencePage.scrollOnPreferencesPage("manageLockAndErase");
			preferencePage.waitForElementsOfPreferencesPage("editPencilIcon");
			preferencePage.clickOnElementsOfPreferencesPage("editPencilIcon");
			preferencePage.clickOnElementsOfPreferencesPage("flipEnableToggle");
			preferencePage.waitForElementsOfPreferencesPage("numberOfApprovals");
			preferencePage.enterTextForPreferencesPage("numberOfApprovals", PreferenceVariables.NUMBER_OF_APPROVERS);
			preferencePage.verifyElementIsClickableOfPreferencesPage("buttonCancel");
			preferencePage.clickOnElementsOfPreferencesPage("buttonSave");

			gotoDevicesTab();
			resetTableConfiguration();
			deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "FLIP_DEVICE"));
			sleeper(2000);
			deviceListPage.clickOnElementsOfDeviceListPage("seclectSerialNumber");
			sleeper(2000);
			sleeper(2000);
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("moreButton");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("moreButton");

			softAssert.assertTrue(deviceDetailsPage.verifyElementIsClickableOfDeviceDetailsPage("lockRequest"), "Lock menu icon is not present");
			softAssert.assertTrue(deviceDetailsPage.verifyElementIsClickableOfDeviceDetailsPage("eraseRequest"), "Erase menu icon is not present");

		}
		softAssert.assertAll();
		LOGGER.info("Verified lock and erase menu icon successfully.");
	}

	/**
	 * This test case verify lock device request, cancel request,resend request and there logs.
	 *
	 * @throws Exception
	 */
	//Lock and Erase buttons can be seen on real time devices only. Fake devices doesn't have the provision to 
	//display these buttons. Hence disabled the test case
	@Test(priority = 62, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI"}, description = "NFR STORY 429187,USER STORY 350806,TEST CASE 582654", enabled = false)
	public final void verifyLockRequest() throws Exception {
		login("ITA_DEVICE_EMAIL_US", "ITA_DEVICE_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		LogPage logsPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		resetTableConfiguration();
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "FLIP_DEVICE"));
		sleeper(3000);
//		String deviceListPlan= deviceListPage.getTextOfDeviceListPage("devicePlan");
		deviceListPage.clickOnElementsOfDeviceListPage("seclectSerialNumber");
		sleeper(2000);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("moreButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("moreButton");

		//Commenting the code as this part of functionality is not yet in stage
		//softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("flipEnrolledPlans").equalsIgnoreCase(deviceListPlan), "HP Protect and Trace plan present in not devicedetail page");
		deviceDetailsPage.scrollOndeviceDetailsPage("lockRequest");
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("lockRequest"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.lockDevice.model.title"));
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("eraseRequest"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.eraseDevice.model.title"));

		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("lockRequest");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("approverDropdown");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("scelectApprovers");
		sleeper(2000);
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("approverDropdownAter");
		deviceDetailsPage.enterTextForDeviceDetailsPage("messageText", "Lock request");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("requestButton");
		sleeper(5000);// wait is required because before clicking on save button it taking flip status
		//Commenting the code as this part of functionality is not yet in stage
//		String devicedetilflipStatus = deviceDetailsPage.getTextOfDeviceDetailsPage("deviceDetailFlipStatus");
//		gotoDevicesTab();
//		LOGGER.info("Redirect to device list page");
//		deviceListPage.scrollTillViewDeviceListPage("deviceListFlipStatus");
//		softAssert.assertTrue(devicedetilflipStatus.equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("deviceListFlipStatus")), "Flip status is not matching");
		gotoLogTab();
		sleeper(2000);
		refreshPage();
		logsPage.mousehoverOnLogsPage("checkBox");
		logsPage.clickByJavaScriptOnLogPage("checkBox");
		softAssert.assertTrue(logsPage.verifyElementsOfLogPage("lockeraseenableStatus"), "Log is not present in audit logs");
		refreshPage();
		gotoDevicesTab();
		deviceListPage.waitForElementsOfDeviceListPage("seclectSerialNumber");
		deviceListPage.clickByJavaScriptOnDeviceListPage("seclectSerialNumber");
		sleeper(2000);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("moreButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("moreButton");

		deviceDetailsPage.scrollOndeviceDetailsPage("resendLockRequest");
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("resendLockRequest"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.lockDevice.resend.label"));
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("cancelLockRequest"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.lockDevice.cancel.label"));

		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("resendLockRequest");
		deviceDetailsPage.verifyElementIsClickableOfDeviceDetailsPage("cancelButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("submitButton");
		sleeper(2000);
		sleeper(2000);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("moreButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("moreButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelLockRequest");
		deviceDetailsPage.verifyElementIsClickableOfDeviceDetailsPage("cancelButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("submitButton");
		gotoLogTab();
		sleeper(2000);
		refreshPage();
		logsPage.mousehoverOnLogsPage("checkBox");
		logsPage.clickByJavaScriptOnLogPage("checkBox");
		softAssert.assertTrue(logsPage.verifyElementsOfLogPage("lockeraseenableStatus"), "Log is not present in audit logs");
		softAssert.assertAll();
		LOGGER.info("Validated of lock request and logs successfully.");
	}

	/**
	 * This test case verify erase device request, cancel request, resend request and there logs.
	 *
	 * @throws Exception
	 */
	//Lock and Erase buttons can be seen on real time devices only. Fake devices doesn't have the provision to 
	//display these buttons. Hence disabled the test case
	@Test(priority = 63, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI"}, description = "NFR STORY 429187, TEST CASE 582647", enabled = false)
	public final void verifyEraseRequest() throws Exception {
		login("ITA_DEVICE_EMAIL_US", "ITA_DEVICE_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		LogPage logsPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		resetTableConfiguration();
		sleeper(2000);
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "FLIP_DEVICE"));
		sleeper(2000);
//		String deviceListPlan= deviceListPage.getTextOfDeviceListPage("devicePlan");
		sleeper(3000);
		deviceListPage.clickOnElementsOfDeviceListPage("seclectSerialNumber");
		//Commenting the code as this part of functionality is not yet in stage
//		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("flipEnrolledPlans").equalsIgnoreCase(deviceListPlan), "HP Protect and Trace plan present in not devicedetail page");
		sleeper(2000);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("moreButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("moreButton");
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("lockRequest"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.lockDevice.model.title"));
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("eraseRequest"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.eraseDevice.model.title"));

		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("eraseRequest");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("approverDropdown");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("scelectApprovers");
		sleeper(2000);
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("approverDropdownAter");
		deviceDetailsPage.enterTextForDeviceDetailsPage("messageText", "Erase request");
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("textOfLockErase"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.eraseDevice.model.checkbox.label"));
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("checkBoxLockErase");

		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("requestButton");
		sleeper(10000);// wait is required because before clicking on save button it click on flip status
		//Commenting the code as this part of functionality is not yet in stage
//		String devicedetilflipStatus = deviceDetailsPage.getTextOfDeviceDetailsPage("deviceDetailFlipStatus");
//		gotoDevicesTab();
//		LOGGER.info("Redirect to device list page");
//		deviceListPage.scrollTillViewDeviceListPage("deviceListFlipStatus");
//		softAssert.assertTrue(devicedetilflipStatus.equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("deviceListFlipStatus")), "Flip status is not matching");
		gotoLogTab();
		sleeper(2000);
		refreshPage();
		logsPage.mousehoverOnLogsPage("checkBox");
		logsPage.clickByJavaScriptOnLogPage("checkBox");
		softAssert.assertTrue(logsPage.verifyElementsOfLogPage("lockeraseenableStatus"), "Log is not present in audit logs");
		refreshPage();
		gotoDevicesTab();

		deviceListPage.waitForElementsOfDeviceListPage("seclectSerialNumber");
		deviceListPage.clickByJavaScriptOnDeviceListPage("seclectSerialNumber");
		sleeper(2000);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("moreButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("moreButton");
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("resendEraseRequest"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.eraseDevice.resend.label"));
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("cancelEraseRequest"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.eraseDevice.cancel.label"));
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("resendEraseRequest");
		deviceDetailsPage.verifyElementIsClickableOfDeviceDetailsPage("cancelButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("submitButton");
		sleeper(2000);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("moreButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("moreButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelEraseRequest");
		deviceDetailsPage.verifyElementIsClickableOfDeviceDetailsPage("cancelButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("submitButton");

		gotoLogTab();
		sleeper(2000);
		refreshPage();
		logsPage.mousehoverOnLogsPage("checkBox");
		logsPage.clickByJavaScriptOnLogPage("checkBox");
		softAssert.assertTrue(logsPage.verifyElementsOfLogPage("lockeraseenableStatus"), "Log is not present in audit logs");
		softAssert.assertAll();
		LOGGER.info("Validated of erase requested and logs successfully.");
	}

	/* This test case verify lock and erase device request, cancel request, resend request and there logs.
	 * @throws Exception
	 */
	//Lock and Erase buttons can be seen on real time devices only. Fake devices doesn't have the provision to 
	//display these buttons. Hence disabled the test case
	@Test(priority = 64, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI"}, description = "NFR STORY 429187,TEST CASE 582658", enabled = false)
	public final void verifyLockAndEraseRequest() throws Exception {
		login("ITA_DEVICE_EMAIL_US", "ITA_DEVICE_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		LogPage logsPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		resetTableConfiguration();
		sleeper(2000);
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "FLIP_DEVICE"));
		sleeper(3000);
//		String deviceListPlan= deviceListPage.getTextOfDeviceListPage("devicePlan");
		sleeper(3000);
		deviceListPage.clickByJavaScriptOnDeviceListPage("seclectSerialNumber");
		//Commenting the code as this part of functionality is not yet in stage
//		softAssert.assertTrue(deviceListPlan.equalsIgnoreCase(deviceDetailsPage.getTextOfDeviceDetailsPage("flipEnrolledPlans")), "Device Plan in device list page is matching with device detail page");
		sleeper(2000);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("moreButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("moreButton");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("lockRequest").equalsIgnoreCase(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.lockDevice.model.title")), "lock request button is not present");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("eraseRequest").equalsIgnoreCase(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.eraseDevice.model.title")), "Erase Request button is not present");

		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("eraseRequest");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("approverDropdown");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("scelectApprovers");
		sleeper(2000);
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("approverDropdownAter");
		deviceDetailsPage.enterTextForDeviceDetailsPage("messageText", "Erase request");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("requestButton");

		sleeper(10000);// wait is required because before clicking on save button it click on flip status
//		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailFlipStatus");
//		String devicedetilflipStatus = deviceDetailsPage.getTextOfDeviceDetailsPage("deviceDetailFlipStatus");
//		gotoDevicesTab();
//		LOGGER.info("Redirect to device list page");
//		deviceListPage.scrollTillViewDeviceListPage("deviceListFlipStatus");
//		softAssert.assertTrue(devicedetilflipStatus.equalsIgnoreCase(deviceListPage.getTextOfDeviceListPage("deviceListFlipStatus")), "Flip status is not matching");
		gotoLogTab();
		sleeper(2000);
		refreshPage();
		logsPage.mousehoverOnLogsPage("checkBox");
		logsPage.clickByJavaScriptOnLogPage("checkBox");
		softAssert.assertTrue(logsPage.verifyElementsOfLogPage("lockeraseenableStatus"), "Log is not present in audit logs");
		refreshPage();
		gotoDevicesTab();
		deviceListPage.waitForElementsOfDeviceListPage("seclectSerialNumber");
		deviceListPage.clickByJavaScriptOnDeviceListPage("seclectSerialNumber");
		sleeper(2000);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("moreButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("moreButton");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("resendEraseRequest").equalsIgnoreCase(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.eraseDevice.resend.label")), "Reset lock request button is not present");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("cancelEraseRequest").equalsIgnoreCase(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flip.eraseDevice.cancel.label")), "Cancel lock request button is not present");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("resendEraseRequest");
		deviceDetailsPage.verifyElementIsClickableOfDeviceDetailsPage("cancelButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("submitButton");
		sleeper(2000);
		sleeper(2000);
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("moreButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("moreButton");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("cancelEraseRequest");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelEraseRequest");
		deviceDetailsPage.verifyElementIsClickableOfDeviceDetailsPage("cancelButton");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("submitButton");

		gotoLogTab();
		sleeper(2000);
		refreshPage();
		logsPage.mousehoverOnLogsPage("checkBox");
		logsPage.clickByJavaScriptOnLogPage("checkBox");
		softAssert.assertTrue(logsPage.verifyElementsOfLogPage("lockeraseenableStatus"), "Log is not present in audit logs");
		softAssert.assertAll();
		LOGGER.info("Validated of lock and erase request and logs successfully.");

	}

	/**
	 * This method validate device addition under multiple subscriptions, change in subscription and its unenrollment.
	 *
	 * @throws Exception
	 */
	@Test(priority = 65, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI"}, description = "UserStory:[472984,568521,472985,472986]", enabled = false)
	public final void verifyFakeDeviceEnrollUnderMultiSubscription() throws Exception {
		login("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		boolean planNameCheck = false;
		Object[][] booleanAndStringFlag = new Object[1][2];
		List<WebElement> dropDownElements = new ArrayList<WebElement>();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		String multiSubsCompany = getEnvironmentSpecificData(System.getProperty("environment"), "MULTI_SUBS_COMP");
		SoftAssert softAssert = new SoftAssert();
		HashMap<String, String> companyDetailsforDeviceEnrollment = new HashMap<String, String>();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String changedPlanName = "";
		try {
			gotoCompaniesTab();
			resetTableConfiguration();
			impersonateCompanyByCompanyName(multiSubsCompany);
			CompanyPin companypin = new CompanyPin().getInstance();
			companyDetailsforDeviceEnrollment.put("companyName", companiesPage.getTextOfCompaniesPage("companyNameShow"));
			companyDetailsforDeviceEnrollment.put("companyEmailId", companiesPage.getTextOfCompaniesPage("companyEmailId"));
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
			companyDetailsforDeviceEnrollment.put("companyPin", companypin.generateCompanyPin(LanguageCode));
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			waitForPageLoaded();
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("seatsInfoOnPlanOverview");
			companiesDetailsPage.getPlanNameAndUsedSeats(LanguageCode, companyDetailsforDeviceEnrollment);
			softAssert.assertTrue(deviceListPage.verifyFakeDeviceListPage(companyDetailsforDeviceEnrollment, LanguageCode), "Unable to validate the Fake Device on Device list page.");
			waitForPageLoaded();
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(multiSubsCompany);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("seatsInfoOnPlanOverview");
			booleanAndStringFlag = companiesDetailsPage.verifySeatConsumption(companyDetailsforDeviceEnrollment);
			softAssert.assertTrue((boolean) booleanAndStringFlag[0][0], "Seat consumption is not correct");
			gotoDevicesTab();
			dashboardPage.selectCompanyInGlobalFilter(multiSubsCompany);

			deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
			softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("planColumnDropDown"), "Plans column is not present on all tab");
			deviceListPage.clickOnElementsOfDeviceListPage("pendingEnrollmentTab");
			softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("planColumnDropDown"), "Plans column is not present on pending enrollment tab");
			deviceListPage.clickOnElementsOfDeviceListPage("allTab");
			deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");

			deviceListPage.clickOnElementsOfDeviceListPage("addButton");
			deviceListPage.clickOnElementsOfDeviceListPage("autoEnrollmentOption");
			deviceListPage.clickOnElementsOfDeviceListPage("nextButton");
			deviceListPage.waitForElementsOfDeviceListPage("addDevicesPlanSelectionModalTitle");
			softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("addDevicesPlanSelectionModalTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.add.title.auto_enroll")), "Plan selection modal title does not match in add devices window.");
			softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("addDevicesPlanSelectionModalLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.add_new_plan.label")), "Plan selection modal label does not match in add devices window.");
			softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("addDevicesPlanSelectionModalDescription").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.changeplan.description")), "Plan selection modal description does not match in add devices window.");

			// Pending for vineer 3 due to bug 603559
			// Plan selection modal verification while adding device
			deviceListPage.clickOnElementsOfDeviceListPage("planSelectionDropdown");
			deviceListPage.waitForElementsOfDeviceListPage("planSelectionDropdownList");
			dropDownElements = deviceListPage.getElementsOfDeviceListPage("planSelectionDropdownList");
			for (Map.Entry<String, String> m : companyDetailsforDeviceEnrollment.entrySet()) {
				for (int i = 0; i < dropDownElements.size(); i++) {
					if (m.getKey().contains(dropDownElements.get(i).getText())) {
						planNameCheck = true;
						break;
					}
				}
			}
			softAssert.assertTrue(planNameCheck, "Expected Plan not present in dropdown");

			dropDownElements.get(0).click();
			dropDownElements.clear();
			softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("cancelRemove"), "Previous button is not present");
			deviceListPage.waitForElementsOfDeviceListPage("nextButton");
			deviceListPage.clickOnElementsOfDeviceListPage("nextButton");
			softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("enterManually"), "Previous button is not present");
			deviceListPage.clickOnElementsOfDeviceListPage("downloadClose");

			// Change plans verification
			deviceListPage.waitForElementsOfDeviceListPage("firstDeviceInList");
			sleeper(5000);// element present yet click intercepts till full loading of table
			deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceInList");
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
			deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("enrolledPlans");
			String enrolledPlanName = deviceDetailsPage.getTextOfDeviceDetailsPage("enrolledPlans");

			gotoDevicesTab();
			deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");
			deviceListPage.clickByJavaScriptOnDeviceListPage("selectAllCheckBox");
			if (deviceListPage.verifyElementsOfDeviceListPage("morebutton")) {
				deviceListPage.clickByJavaScriptOnDeviceListPage("morebutton");
			}
			deviceListPage.waitForElementsOfDeviceListPage("changePlanButton");
			sleeper(3000);// Stale element exception
			// Pending for vineer 3 due to bug 603599
			deviceListPage.clickOnElementsOfDeviceListPage("changePlanButton");
			deviceListPage.waitForElementtobeClickableOfDeviceListPage("saveChangedPlansButton");

			// ***Need to change maestro values after maestro file updates
			// softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("").equals(getTextLanguage(LanguageCode, "daas_ui", "device.button.change_plans").substring(0,
			// getTextLanguage(LanguageCode, "daas_ui", "device.button.change_plans").length()-2)), "Change plans window title is incorrect");
			// softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("").equals(getTextLanguage(LanguageCode, "daas_ui", "assets.changeplan.label")), "Plan selection modal label
			// does not match in add devices window.");

			softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("addAnother").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.details.contracts.add_plan")), "Add another plan link not present");
			softAssert.assertTrue(deviceListPage.verifyElementIsClickableOfDeviceListPage("addAnother"), "Add another plan link not working");
			// Pending for vineer 3 due to bug 603599
			deviceListPage.clickOnElementsOfDeviceListPage("planSelectionDropdownOnChangePlans");
			deviceListPage.waitForElementsOfDeviceListPage("planSelectionDropdownListOnChangePlans");
			dropDownElements = deviceListPage.getElementsOfDeviceListPage("planSelectionDropdownListOnChangePlans");

			for (int i = 0; i < dropDownElements.size(); i++) {
				sleeper(500);
				if (!enrolledPlanName.contains(dropDownElements.get(i).getText())) {
					changedPlanName = dropDownElements.get(i).getText();
					dropDownElements.get(i).click();
					break;
				}
			}
			sleeper(1000);
			deviceListPage.waitForElementtobeClickableOfDeviceListPage("saveChangedPlansButton");
			deviceListPage.clickOnElementsOfDeviceListPage("saveChangedPlansButton");
		} catch (Exception e) {
			LOGGER.info("Validation of Fake device enrollment is unsuccessful.");
		} finally {
			deviceListPage.waitForElementsOfDeviceListPage("firstDeviceInList");
			sleeper(5000);// element present yet click intercepts till full loading of table
			deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceInList");
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
			deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("enrolledPlans");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("enrolledPlans").contains(changedPlanName), "Plan not changed");
			gotoDevicesTab();
			deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");

			deviceListPage.waitForElementsOfDeviceListPage("firstDeviceInList");
			deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceInList");
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
			deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
			String deviceID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
			Assert.assertTrue(getStatusCode(getEnvironment(System.getProperty("environment")) + DeviceVariables.SINGLE_DEVICE_REMOVE + deviceID, "{}", "DELETE", environment) == 204, "Device removal failed");
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "MULTI_SUBS_COMP"));
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("seatsInfoOnPlanOverview");
			softAssert.assertTrue(companiesDetailsPage.verifySeatConsumptionAfterUnenrollment(companyDetailsforDeviceEnrollment, changedPlanName), "Seat consumption incorrect after unenrollment");
			LOGGER.info("Removed Device");
		}
		softAssert.assertAll();
		LOGGER.info("Validation of Fake device enrollment completed successfully.");
	}

	/**
	 * This method validate report admin delete device functionality
	 *
	 * @throws Exception
	 */
	@Test(priority = 66, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI"}, description = "Test case id 283982")
	public final void verifyReportAdminDeviceDeleteFunctionality() throws Exception {
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		login("REPORT_ADMIN_STAGING_EMAIL", "REPORT_ADMIN_STAGING_PASSWORD");
		gotoDevicesTab();

		Assert.assertFalse(deviceListPage.verifyElementsOfDeviceListPage("removeDeviceButton"), "Delete device button visible to report admin on list page");
		LOGGER.info("Validated visibility of delete device button on device list page");

		// Navigate to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_ID"));

		Assert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deleteDeviceButton"), "Delete device button visible to report admin on details page");
		LOGGER.info("Validated visibility of delete device button on device details page");

		LOGGER.info("Validation of report admin delete device functionality completed successfully.");
	}


	/*
	 * Verify Device detail plans
	 */
	@Test(priority = 67, groups = {"REGRESSIONDEVICES2"}, description = "Test case id 624984")
	public final void verifyDeviceDetailPlans() throws Exception {
		login("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		waitForPageLoaded();
		deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.goToParticularDevice(getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_DEVICE_NAME"));
		// Scroll down to enrollment tile
		deviceDetailsPage.scrollOndeviceDetailsPage("enrollmentTile");
		LOGGER.info("Scrolled down to enrollment tile");
		sleeper(3000);
		ArrayList<String> arrPlansValues = new ArrayList<String>(
				Arrays.asList(
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.enhanced_package.short")),
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.premium_package.short")),
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.standard_package.short")),
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.proactive_standard_management_package.short")),
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.hp_proactive_endpoint_management")),
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.hp_proactive_insights"))
				)
		);
		sleeper(1000);
		Assert.assertTrue(deviceDetailsPage.compareDeviceDetailPlans("enrollmentPlan", arrPlansValues), "All plans are not displayed in device overview");
	}

	@Test(priority = 68, groups = {"REGRESSIONDEVICES2"}, description = "Test case id 624957")
	public final void verifyNAPlanVisibleInDeviceListDropdown() throws Exception {
		login("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		waitForPageLoaded();
		deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.clickOnElementsOfDeviceListPage("plancolumndropdownbtn");
		deviceListPage.enterTextForDeviceListPage("planColumnDropDownSearchText", (deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "global.list.column.na")));
		sleeper(3000);
		Assert.assertTrue(deviceListPage.matchTextOfDeviceListPage("searchPlanDropdownValue", deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "global.list.column.na")), "N/A option not visible in plan's dropdown");


	}


	/*
	 * Verify that after login redirect to device detail page using serial number.
	 */
	@Test(priority = 69, groups = {"REGRESSIONDEVICES2"}, description = "Testcases are 762683,762685")
	public final void verifyDirectLinkURLtoDeviceDetails() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		getUrl(getEnvironment(System.getProperty("environment")) + "device/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_SERIAL_DETAIL_PLANS"));
		sleeper(1000);
		welcomePage.waitForElementsOfWelcomePage("signInButton");
		welcomePage.clickOnElementsOfWelcomePage("signInButton");
		loginCustom("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		waitForPageLoaded();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_DIRECTLINK"));
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("serialNumber");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("serialNumber").equals(getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_SERIAL_DETAIL_PLANS")), "Device serial number is not valid");
		LOGGER.info("Valid device serial visible in device detail page");

		getUrl(getEnvironment(System.getProperty("environment")) + "device/" + getEnvironmentSpecificData(System.getProperty("environment"), "INVALID_DEVICE_DETAIL_PAGE"));
		LOGGER.info("Redirect to invalid device url");
		waitForPageLoaded();
		sleeper(3000);
		softAssert.assertTrue(getUrlOfCurrentPage().contains("404"), "404 not visible in url");
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_DIRECTLINK"));
		softAssert.assertAll();
		logout();
		LOGGER.info("Validation direct link to device detail page completed successfully.");
	}

	/*
	 * This test case verifies logs tile on Device details page after enabling/disabling device logs toggle from company details page
	 * User Story 653360: [CloudPlatform][UI] Post Enrollment Client Log Collection
	 */
	@Test(priority = 70, groups = {"REGRESSIONDEVICES2"}, description = "Test case: 770148")
	public final void verifLogsTileOnDeviceDetailsPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		//Navigate to company details page
		gotoCompaniesTab();
		waitForPageLoaded();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_FOR_DEVICE_LOG"));
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Navigated to Preference Tab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("dataCaptureTileHeader");
		companyDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");
		companyDetailsPage.scrollOnCompaniesDetailsPage("deviceLogsData");
		sleeper(3000);

		//Disable device logs toggle from company details page
		if (companyDetailsPage.getTextOfCompaniesDetailsPage("deviceLogsData").equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("deviceLogsToggle");
			sleeper(4000);
			Assert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("deviceLogsData", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Antivirus status does not match after toggle change");
			LOGGER.info("Device logs toggle Disabled");
		}
		//Navigate to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "LOGS_COLLECTION_DEVICE_ID"));
		LOGGER.info("Redirected to device details page");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");
		waitForPageLoaded();
		Assert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceLogsTile"), "LogsCollection tile is present on device details page even if device logs toggle is off");

		//Enable device logs toggle from company details page
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_FOR_DEVICE_LOG"));
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Navigated to Preference Tab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("dataCaptureTileHeader");
		companyDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");
		sleeper(4000);
		if (!companyDetailsPage.verifyElementIsSelectedOfCompaniesDetailsPage("deviceLogsToggle")) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("deviceLogsToggle");
		}
		sleeper(4000);
		Assert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("deviceLogsData", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Device logs status does not match after toggle change");
		LOGGER.info("Device logs toggle Enabled");

		//Navigate to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "LOGS_COLLECTION_DEVICE_ID"));
		LOGGER.info("Redirected to device details page");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");
		sleeper(4000);
		Assert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceLogsTile"), "Logs Collection tile is not present on device details page even if device logs toggle is on");
		Assert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("collectLogsButton"), "Collect Logs button not present on logs tile");

		softAssert.assertAll();
		LOGGER.info("Test case verifLogsTileOnDeviceDetailsPage passed successfully.");
	}

	/*
	 * This test case verifies Collect new logs popup on device details page.
	 * User Story 653360: [CloudPlatform][UI] Post Enrollment Client Log Collection
	 */
	@Test(priority = 71, groups = {"REGRESSIONDEVICES2"}, description = "Test case: 770153")
	public final void verifyCollectNewLogsPopup() throws Exception {
		login("MSP_ADMIN_US_CL_EMAIl", "MSP_ADMIN_US_PASSWORD");
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> logCollectionOptions = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "assets.details.logs.allLogs").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "assets.details.logs.logForSpecificDate").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "assets.details.logs.customDays").toLowerCase().trim()));
		String invalidCustomDays = generateRandomNumber();

		// Navigate to company details page
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_COLLECTLOGS_COMPANY"));
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Navigated to Preference Tab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("dataCaptureTileHeader");
		companyDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");

		// Verify device logs toggle is enabled from company details page
		if (companyDetailsPage.getTextOfCompaniesDetailsPage("deviceLogsData").equals(getTextLanguage(LanguageCode, "daas_ui", "global.disabled"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("deviceLogsToggle");
			sleeper(3000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("deviceLogsData", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Device logs status does not match after toggle change");
			LOGGER.info("Device logs toggle Enabled");
		}

		gotoDevicesTab();
		deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
		// Navigate to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "LOGS_COLLECTION_DEVICE_ID"));
		deviceListPage.waitForPageLoaded();
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");
		sleeper(4000);
		// Verify Collect logs tile on device details page
		Assert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceLogsTile"), "Logs Collection tile is not present on device details page even if device logs toggle is on");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("collectLogsButton"), "Collect logs button not present on logs tile");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("collectLogsButton");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("collectNewLogPopup");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("collectNewLogPopup"), "Collect logs pop up did not opened after clicking on collect logs");

		// Verify Collect new log popup
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("collectLogLabel").equals(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.logs.collectNewLog")), "Collect new log title did not match");
		softAssert.assertTrue(deviceDetailsPage.getTextOfColumns("logsCollectionOptions").equals(logCollectionOptions), "Logs collection options present on collect logs popup are incorrect");
		LOGGER.info("Verified logs collection options on collect logs popup");

		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("cancelLogsButton");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("collectNewLogPopup"), "Collect logs pop up did not closed after clicking on ancel button");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("collectLogsButton");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("collectNewLogPopup");

		// Validate logs for custom days option
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("customDaysButton");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("customDaysTextbox");
		deviceDetailsPage.enterTextForDeviceDetailsPage("customDaysTextbox", invalidCustomDays);
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("collectLogsButtonOnPopup");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("customDaysErrorMessage"), "Error message did not appear after entering invalid days");
		// Commenting this line since string is not yet translated; will uncomment this once required maestro string gets translated
		// softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("customDaysErrorMessage").equals(deviceDetailsPage.getTextLanguage(LanguageCode,"daas_ui","assets.details.logs.enteredCustomDaysExceedEnrollmentDate")),"Error message for invalid custom days did not match");
		deviceDetailsPage.clearTextRefreshFromDeviceDetailsTextField("customDaysTextbox");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("collectLogsButtonOnPopup");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("customDaysErrorMessage"), "Error message did not appear for empty custom days");
		// Commenting this line since string is not yet translated; will uncomment this once required maestro string gets translated
		// softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("customDaysErrorMessage").equals(deviceDetailsPage.getTextLanguage(LanguageCode,"daas_ui", "assets.details.logs.customDaysInvalid")), "Errormessage for empty custom days did not match");

		// Validate logs for specific date range option
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("specificDateRangeButton");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("specificDateRangeCalendar");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("startDateTextBox"), "Start date field did not appear on logs collection popup even after selecting specific date option");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("startDateLabel").equals(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "global.start_date")), "Start date label did not match on specifc days field");
		softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("endDateTextBox"), "Start date field did not appear on logs collection popup even after selecting specific date option");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("endDateLabel").equals(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "global.end_date")), "End date label did not match on specifc days field");

		softAssert.assertAll();
		LOGGER.info("Test case verifyCollectNewLogsPopup passed successfully.");
	}

	/**
	 * This Test case verifies protect and Trace column dropdown states
	 */
	@Test(priority = 72, groups = {}, description = "Test case: 801998", enabled = false)
	public final void verifyStateOfProtectTraceColumn() throws Exception {
		login("ITADMIN_DEVICE_PROTECTTRACE_STATUS_EMAIL", "ITADMIN_DEVICE_PROTECTTRACE_STATUS_PASSWORD");
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoDevicesTab();
		softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("ProtectTraceColumn").equalsIgnoreCase(getTextLanguage(LanguageCode, "idm", "onboarding.protectandtrace.service_name")), "Protect and Trace column text is not matching");

		deviceListPage.waitForElementsOfDeviceListPage("ProtectTraceDropDown");
		deviceListPage.clickOnElementsOfDeviceListPage("ProtectTraceDropDown");
		sleeper(10000);//wait is required to open protecttrace column dropdown option
		ArrayList<String> ProtectTraceStatusUI = deviceListPage.getTextOfListOfDeviceListPage("ProtectTraceStatus");

		ArrayList<String> ProtectTraceStatusMaestro = new ArrayList<>(Arrays.asList(
				getTextLanguage(LanguageCode, "daas_ui", "assets.list.lockAndErase.available"),
				getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned"),
				getTextLanguage(LanguageCode, "daas_ui", "global.not_supported"),
				getTextLanguage(LanguageCode, "daas_ui", "global.pending"),
				getTextLanguage(LanguageCode, "lhserver", "custom_apps.fields.status.error"),
				getTextLanguage(LanguageCode, "daas_ui", "assets.list.lockAndErase.user_rejected")));
		for (String StatusUI : ProtectTraceStatusUI) {
			boolean protectTraceStatusMatched = false;
			for (String statusMaestro : ProtectTraceStatusMaestro) {
				if (StatusUI.equalsIgnoreCase(statusMaestro)) {
					protectTraceStatusMatched = true;
				}
			}
			softAssert.assertTrue(protectTraceStatusMatched, "ProtectTrace column status are not matching");
		}
		resetTableConfiguration();
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", getEnvironmentSpecificData(System.getProperty(environment), "FlipDevicesStatus"));
		String HPWolfProtectTraceValue = deviceListPage.getTextOfDeviceListPage("HPWolfProtectTraceValue");
		deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceInList");
		deviceDetailsPage.getTextOfDeviceDetailsPage("protectTraceTile");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("protectTraceStatus").equalsIgnoreCase(HPWolfProtectTraceValue), "HP Wolf Protect and Trace status is not matching with device list and device detail page");
		softAssert.assertAll();
		LOGGER.info("Verified ProtectTrace column status values on device list page.");
	}

	/**
	 * This test case validates the context sensitive help links on Devices screen
	 *
	 * @throws Exception
	 */
	@Test(priority = 73, groups = {"REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI"}, description = "Test Case ID : 891080")
	public final void verifyOnlineHelpLinksOnDevices() throws Exception {
		login("IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES", "IT_ADMIN_COMPANY_USER_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		// Verify help and support link for Devices tab
		waitForPageLoaded();
		gotoDevicesCompanyUserTab();
		LOGGER.info("Redirected to Devices tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("help&SupportLink",
						getTextLanguage(LanguageCode, "daas_ui", "settings.help_and_support.title")),
				"Help & Support link text did not match on Devices tab");
		settingsPage.clickOnElementsOfSettingsPage("help&SupportLink");
		LOGGER.info("Clicked on Help & Support link from Devices tab");
		waitForPageLoaded();
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")),
				"User not redirected to Help & Support tab after clicking on Help & Support link from Devices");
		gotoDevicesCompanyUserTab();

		// Verify overview link for Devices tab
		waitForPageLoaded();
		LOGGER.info("Redirected to Devices tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("devicesOverviewLink",
						getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices.overview")),
				"Devices overview link text did not match on Devices tab");
		settingsPage.clickOnElementsOfSettingsPage("devicesOverviewLink");
		LOGGER.info("Clicked on overview link from Devices tab");
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.KNOWLEDGE_BASE),
				"User not redirected to Online help section after clicking on overview link from Devices tab");
		settingsPage.switchBackToPreviousTab();

		softAssert.assertAll();
		LOGGER.info("Test case to verify context sensitive links on Devices passed successfully.");
	}

	@Test(priority = 74, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "885508", enabled = true)
	public final void verifyNetworkHealthOutageTile() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");

		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		resetTableConfiguration();

		// Navigate to device details page
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
		deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceSerialNumber");
		LOGGER.info("Redirected to device details page");

		// Click on network tab
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("networkTab");
		Assert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("networkHealthTile"), "Network Health Tile is not present on Device Details page.");
		Assert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("networkOutageTile"), "Network Outage Tile is not present on Device Details page.");

	}

	/**
	 * User Story 758992: [Core] [HPDX][UI] Add "digital experience" tab on device detail page
	 *
	 * @throws Exception
	 */
	@Test(priority = 75, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "Test Case ID : 913711", enabled = false)
	public final void verifyHPDXTileOnDeviceDetailsPageITadmin() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		deviceListPage.clickByJavaScriptOnDeviceListPage("devicesTab");
		LOGGER.info("Redirected to device list page");
		deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceInList");
		waitForPageLoaded();
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("DigitalExperienceTab");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("DigitalExperienceTab");
		LOGGER.info("Clicked on Digital Experience tab");
		waitForPageLoaded();
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("DigitalExpOverview").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "hpdx.kpi.overview_card")), "Experience Overview text did not match");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("DigitalExpTimeline").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "hpdx.kpi.digital_experience_time")), "Experience Overview Over time text did not match");
		softAssert.assertAll();
	}

	/**
	 * This test case verifies the Experience management  tab in device details page for IT admin login.
	 *
	 * @throws Exception
	 */
	@Test(priority = 76, groups = {"REGRESSIONDEVICES2", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "Test Case ID : 913711", enabled = false)
	public final void verifyHPDXTileOnDeviceDetailsPageReportadmin() throws Exception {
		login("IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES", "IT_ADMIN_COMPANY_USER_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		deviceListPage.clickByJavaScriptOnDeviceListPage("devicesTab");
		LOGGER.info("Redirected to device list page");
		deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceInList");
		waitForPageLoaded();
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("DigitalExperienceTab");
		deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("DigitalExperienceTab");
		LOGGER.info("Clicked on Digital Experience tab");
		waitForPageLoaded();
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("DigitalExpOverview").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "hpdx.kpi.overview_card")), "Experience Overview text did not match");
		softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("DigitalExpTimeline").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "hpdx.kpi.digital_experience_time")), "Experience Overview Over time text did not match");
		softAssert.assertAll();
	}

	/**
	 * This test verifies Born on date field on Device detail page
	 * If born-on-date-from-idm toggle is OFF, Born on date field picks from reports API and
	 * if the toggle if ON field picks from device details API
	 */
	@Test(priority = 77, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "956610")
	public final void verifyBornOnDate() throws Exception {
		login("IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES", "IT_ADMIN_COMPANY_USER_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to Devices tab");
		resetTableConfiguration();

		// Redirected to device details page
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "BORN_ON_DATE_DEVICEID"));
		waitForPageLoaded();
		deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("deviceInformationTab");
		deviceDetailsPage.scrollOndeviceDetailsPage("systemTile");

		if (!toggleVerification(DeviceVariables.BORNONDATE_TOGGLE, getCredentials(environment, "COMPANY_BORNONDATE_EMAIL"))) {
			softAssert.assertEquals(deviceDetailsPage.getSystemTileInformation(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.HW_INV_URL, BornOnDateBody), deviceDetailsPage.getTextOfDeviceDetailsPage("bornOnDatevalue"), "Born on date did not match");
		} else {
			softAssert.assertEquals(deviceDetailsPage.getSystemTileAPIInformation(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.Device_Details_API, BornOnDeviceDateBody), deviceDetailsPage.getTextOfDeviceDetailsPage("bornOnDatevalue"), "Born on date did not match");
		}
		softAssert.assertAll();
		LOGGER.info("Verified Born On Date on UI is successful");
	}

	/**
	 * This test verifies Create Group Functionality for devices with Serial number Property
	 * If techpulse-grouping-service toggle is OFF, then it skips the verification of this scenario
	 * if the toggle if ON field picks from device details API
	 */
	@Test(priority = 78, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"})
	public final void VerifyCreatGroupfunctionality() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to Devices tab");
		if (toggleVerification(DeviceListPage.Device_Grouping_toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			//To Verify whether any company is selected from the global location by default
			if (!dashboardPage.getTextOfDashboardPage("alllocationtext").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
				LOGGER.info("Verifying global filter functionality");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
				clearCompanyFilterDashboard(LanguageCode);
			}
			dashboardPage.getTextOfDashboardPage("grouppanelalltext1");
			dashboardPage.getTextOfDashboardPage("grouppanelalltext2");
			//Before Group Creation Ensure only Active Devices list is selected
			softAssert.assertTrue(deviceListPage.verifyonlyactivedevicesdatafetchforstaticgroups(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDD", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active Device list filtered successfully");
			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			LOGGER.info("Search company to set location filter");
			dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", CommonVariables.Company_Device_Grouping);
			sleeper(5000);
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"));
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter Saved successfully.");
			waitForPageLoaded();

			sleeper(3000);
			List<String> devicelist = deviceListPage.getAllTextOfDeviceListPage("deviceserialnumberfield");
			//Verification of group creation functionality
			String property = deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.serial_number");
			softAssert.assertTrue(deviceDetailsPage.verifyCreateParentGroup("methodpropertyvalue", property, "propertylist", devicelist, LanguageCode));
			//Verification of device count and list after group creation
			softAssert.assertTrue(deviceDetailsPage.verifyDevicecountandlistaftergroupcreation(devicelist));
			//verification of success notification once after creation
			softAssert.assertTrue(deviceDetailsPage.postNotificationCheckDevicegroupsForSuccessfull());
			gotoDevicesTab();
			//verification of delete group functionality
			softAssert.assertTrue(deviceDetailsPage.deletegroup());
			softAssert.assertAll();
		} else {
			LOGGER.info("Toggle is set to off for the provided credentials");
		}
	}

	/**
	 * This test verifies Create Hierarchy Group Functionality for devices
	 * If techpulse-grouping-service toggle is OFF, then it skips the verification of this scenario
	 * if the toggle if ON field picks from device details API
	 */
	@Test(priority = 79, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"})
	public final void VerifyHierarchyGroupCreation() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to Devices tab");
		if (toggleVerification(DeviceListPage.Device_Grouping_toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			//To Verify whether any company is selected from the global location by default
			if (!dashboardPage.getTextOfDashboardPage("alllocationtext").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
				LOGGER.info("Verifying global filter functionality");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
				clearCompanyFilterDashboard(LanguageCode);
			}
			dashboardPage.getTextOfDashboardPage("grouppanelalltext1");
			dashboardPage.getTextOfDashboardPage("grouppanelalltext2");
			//Before Group Creation Ensure only Active Devices list is selected
			softAssert.assertTrue(deviceListPage.verifyonlyactivedevicesdatafetchforstaticgroups(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDD", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active Device list filtered successfully");
			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			LOGGER.info("Search company to set location filter");
			dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", CommonVariables.Company_Device_Grouping);
			sleeper(5000);
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"));
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter Saved successfully.");
			waitForPageLoaded();
			String property = deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.serial_number");
			String firstdevicedetail = deviceListPage.getTextOfDeviceListPage("firstdevicetext");
			//Verify Hierarchy group creation functionality
			softAssert.assertTrue(deviceDetailsPage.verifyHierarchyGroupCreation(firstdevicedetail, property, "propertylist", LanguageCode));
			softAssert.assertAll();
		} else {
			LOGGER.info("Toggle is set to off for the provided credentials");
		}
	}

	/**
	 * This test verifies Edit Group Functionality for devices
	 * If techpulse-grouping-service toggle is OFF, then it skips the verification of this scenario
	 * if the toggle if ON field picks from device details API
	 */
	@Test(priority = 80, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"})
	public final void VerifyEditGroupFunctionality() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to Devices tab");
		if (toggleVerification(DeviceListPage.Device_Grouping_toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			//To Verify whether any company is selected from the global location by default
			if (!dashboardPage.getTextOfDashboardPage("alllocationtext").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
				LOGGER.info("Verifying global filter functionality");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
				clearCompanyFilterDashboard(LanguageCode);
			}
			dashboardPage.getTextOfDashboardPage("grouppanelalltext1");
			dashboardPage.getTextOfDashboardPage("grouppanelalltext2");
			//Before Group Creation Ensure only Active Devices list is selected
			softAssert.assertTrue(deviceListPage.verifyonlyactivedevicesdatafetchforstaticgroups(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDD", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active Device list filtered successfully");
			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			LOGGER.info("Search company to set location filter");
			dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", CommonVariables.Company_Device_Grouping);
			sleeper(5000);
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"));
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter Saved successfully.");
			waitForPageLoaded();
			String property = deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.serial_number");
			List<String> devicelist = deviceListPage.getAllTextOfDeviceListPage("deviceserialnumberfield");
			//Verification of group creation functionality
			softAssert.assertTrue(deviceDetailsPage.verifyCreateParentGroup("methodpropertyvalue", property, "propertylist", devicelist, LanguageCode));
			// To Verify the EditGroupFunctionality for existing group
			softAssert.assertTrue(deviceDetailsPage.verifyeditGroupfunctionality(LanguageCode));
			//verification of delete group functionality
			softAssert.assertTrue(deviceDetailsPage.deletegroup());
			softAssert.assertAll();
		} else {
			LOGGER.info("Toggle is set to off for the provided credentials");
		}
	}

	/**
	 * This test verifies Add Devices to Group Functionality for devices
	 * If techpulse-grouping-service toggle is OFF, then it skips the verification of this scenario
	 * if the toggle if ON field picks from device details API
	 */
	@Test(priority = 81, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"})
	public final void VerifyAddDevicestoGroupFunctionality() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to Devices tab");
		if (toggleVerification(DeviceListPage.Device_Grouping_toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			//To Verify whether any company is selected from the global location by default
			if (!dashboardPage.getTextOfDashboardPage("alllocationtext").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
				LOGGER.info("Verifying global filter functionality");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
				clearCompanyFilterDashboard(LanguageCode);
			}
			dashboardPage.getTextOfDashboardPage("grouppanelalltext1");
			dashboardPage.getTextOfDashboardPage("grouppanelalltext2");
			//Before Group Creation Ensure only Active Devices list is selected
			softAssert.assertTrue(deviceListPage.verifyonlyactivedevicesdatafetchforstaticgroups(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDD", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active Device list filtered successfully");
			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			LOGGER.info("Search company to set location filter");
			dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", CommonVariables.Company_Device_Grouping);
			sleeper(5000);
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"));
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter Saved successfully.");
			waitForPageLoaded();
			String property = deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.serial_number");
			List<String> devicelist = deviceListPage.getAllTextOfDeviceListPage("deviceserialnumberfield");
			// To Verify the AddDevicetoGroup for existing group
			softAssert.assertTrue(deviceDetailsPage.verifyadddevicestogroupfunctionality(devicelist, property, "propertylist", LanguageCode));
			softAssert.assertTrue(deviceDetailsPage.verifyDevicecountandlistaftergroupcreation(devicelist));
			//verification of success notification once after creation
			softAssert.assertTrue(deviceDetailsPage.postNotificationCheckDevicegroupsForSuccessfull());
			softAssert.assertAll();
		} else {
			LOGGER.info("Toggle is set to off for the provided credentials");
		}
	}

	/**
	 * This test verifies Device List and details page after creation with Device Namer Property
	 * If techpulse-grouping-service toggle is OFF, then it skips the verification of this scenario
	 * if the toggle if ON field picks from device details API
	 */
	@Test(priority = 82, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"})
	public final void VerifyDeviceListanddetailsaftercreation() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to Devices tab");
		if (toggleVerification(DeviceListPage.Device_Grouping_toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			//To Verify whether any company is selected from the global location by default
			if (!dashboardPage.getTextOfDashboardPage("alllocationtext").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
				LOGGER.info("Verifying global filter functionality");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
				clearCompanyFilterDashboard(LanguageCode);
			}
			dashboardPage.getTextOfDashboardPage("grouppanelalltext1");
			dashboardPage.getTextOfDashboardPage("grouppanelalltext2");
			//Before Group Creation Ensure only Active Devices list is selected
			softAssert.assertTrue(deviceListPage.verifyonlyactivedevicesdatafetchforstaticgroups(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDD", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active Device list filtered successfully");
			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			LOGGER.info("Search company to set location filter");
			dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", CommonVariables.Company_Device_Grouping);
			sleeper(5000);
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"));
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter Saved successfully.");
			waitForPageLoaded();
			String property = deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.device_name");
			List<String> devicelist = deviceListPage.getAllTextOfDeviceListPage("devicenamefieldlist");
			//Verification of create group functionality
			softAssert.assertTrue(deviceDetailsPage.verifyCreateParentGroup("methodpropertyvalue", property, "propertylist", devicelist, LanguageCode));
			//Verify group DeviceList and details after creation functionality
			softAssert.assertTrue(deviceDetailsPage.verifyDevicecountandlistaftergroupcreation(devicelist));
			//verification of success notification once after creation
			softAssert.assertTrue(deviceDetailsPage.postNotificationCheckDevicegroupsForSuccessfull());
			softAssert.assertAll();
		} else {
			LOGGER.info("Toggle is set to off for the provided credentials");
		}
	}

	/**
	 * This test verifies remove device from group functionality & details page after removal
	 * If techpulse-grouping-service toggle is OFF, then it skips the verification of this scenario
	 * if the toggle if ON field picks from device details API
	 */
	@Test(priority = 83, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"})
	public final void VerifyremovedevicefromGroupfunctionality() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to Devices tab");
		if (toggleVerification(DeviceListPage.Device_Grouping_toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			//To Verify whether any company is selected from the global location by default
			if (!dashboardPage.getTextOfDashboardPage("alllocationtext").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
				LOGGER.info("Verifying global filter functionality");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
				clearCompanyFilterDashboard(LanguageCode);
			}
			dashboardPage.getTextOfDashboardPage("grouppanelalltext1");
			dashboardPage.getTextOfDashboardPage("grouppanelalltext2");
			//Before Group Creation Ensure only Active Devices list is selected
			softAssert.assertTrue(deviceListPage.verifyonlyactivedevicesdatafetchforstaticgroups(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDD", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active Device list filtered successfully");
			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			LOGGER.info("Search company to set location filter");
			dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", CommonVariables.Company_Device_Grouping);
			sleeper(5000);
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"));
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter Saved successfully.");
			waitForPageLoaded();
			sleeper(2000);
			String property = deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.device_name");
			List<String> devicelist = deviceListPage.getAllTextOfDeviceListPage("devicenamefieldlist");
			//Verify group creation functionality

			softAssert.assertTrue(deviceDetailsPage.verifyCreateParentGroup("methodpropertyvalue", property, "propertylist", devicelist, LanguageCode));
			sleeper(8000);
			//Verify remove devices from group functionality after group creation
			softAssert.assertTrue(deviceDetailsPage.verifyremovefromdevicefromgroup(LanguageCode));
			softAssert.assertAll();
		} else {
			LOGGER.info("Toggle is set to off for the provided credentials");
		}
	}

	/**
	 * This test verifies the managing groups with read group roles
	 * If techpulse-grouping-service toggle is OFF, then it skips the verification of this scenario
	 * if the toggle if ON field picks from device details API
	 * <p>
	 * Disabling the test case as there is a open bug related to roles - bug ID: Bug 1165906
	 */
	@Test(priority = 84, dataProvider = "loginDatarolesForDeviceGrouping", groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, enabled = true)
	public final void verifymanaginggroupswithreadgrouproles(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		if (username.equals("MSP_ADMIN_US_EMAIl") == true) {
			login(username, password);
			gotoDevicesTab();
			LOGGER.info("Redirected to Devices tab");
			if (toggleVerification(DeviceListPage.Device_Grouping_toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
				//To Verify whether any company is selected from the global location by default
				if (!dashboardPage.getTextOfDashboardPage("alllocationtext").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
					LOGGER.info("Verifying global filter functionality");
					Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
					Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
					clearCompanyFilterDashboard(LanguageCode);
				}
				dashboardPage.getTextOfDashboardPage("grouppanelalltext1");
				dashboardPage.getTextOfDashboardPage("grouppanelalltext2");
				//Before Group Creation Ensure only Active Devices list is selected
				softAssert.assertTrue(deviceListPage.verifyonlyactivedevicesdatafetchforstaticgroups(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDD", "devicestatusddvalues", "firstcolumnheader"));
				LOGGER.info("Active Device list filtered successfully");
				dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
				waitForPageLoaded();
				LOGGER.info("Search company to set location filter");
				dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", CommonVariables.Company_Device_Grouping);
				sleeper(5000);
				dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
				softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"));
				dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
				dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
				LOGGER.info("Global Location Filter Saved successfully.");
				waitForPageLoaded();
				sleeper(2000);
				String property = deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.device_name");
				List<String> devicelist = deviceListPage.getAllTextOfDeviceListPage("devicenamefieldlist");
				//Verify group creation functionality through Management group roles
				softAssert.assertTrue(deviceDetailsPage.verifyCreateParentGroup("methodpropertyvalue", property, "propertylist", devicelist, LanguageCode));
				softAssert.assertAll();
				logout();
				//Verify read group functionality for the same company for various roles
			}
		} else {
			login(username, password);
			sleeper(2000);
			if (deviceListPage.getTextOfDeviceListPage("tabmenu").equals(getTextLanguage(LanguageCode, "daas_ui", "patches.details.label.devices"))) {
				LOGGER.info("Already in Devices List Page");
			} else {
				gotoDevicesTab();
				waitForPageLoaded();
				//Verification of groups panel & panel texts for the roles provided
				Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("groupspanel"));
				Assert.assertEquals(deviceListPage.getTextOfDeviceListPage("groupspanel"), deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.hpcGroups"), "Groups Tile on the Sidepanel is not getting matched");
				Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("publictitle"));
				Assert.assertEquals(deviceListPage.getTextOfDeviceListPage("publictitle"), deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "device.groups.tab.public.label"), "Public Tile on the Sidepanel is not getting matched");
				Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("statictitle"));
				Assert.assertEquals(deviceListPage.getTextOfDeviceListPage("statictitle"), deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "device.groups.static.group.label"), "Static Tile on the Sidepanel is not getting matched");
				Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("searchgroupfield"));
				Assert.assertFalse(deviceListPage.verifyElementsOfDeviceListPage("creategroupButton"));
				deviceListPage.verifyElementsOfDeviceListPage("grouplist");
				Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("readrolepanelmessage"));
				//Verification of recently created group from the Management role
				Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("newlycreatedgroup"));
				deviceListPage.clickOnElementsOfDeviceListPage("newlycreatedgroup");
				LOGGER.info("Groups list verified successfully for read group roles");
			}
		}
	}

	/**
	 * This test verifies Create group functionality with Invalid Property value
	 * If techpulse-grouping-service toggle is OFF, then it skips the verification of this scenario
	 * if the toggle if ON field picks from device details API
	 */
	@Test(priority = 85, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"})
	public final void verifycreategroupfuntionalitywithinvalidproperty() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to Devices tab");
		if (toggleVerification(DeviceListPage.Device_Grouping_toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			//To Verify whether any company is selected from the global location by default
			if (!dashboardPage.getTextOfDashboardPage("alllocationtext").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
				LOGGER.info("Verifying global filter functionality");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
				clearCompanyFilterDashboard(LanguageCode);
			}
			dashboardPage.getTextOfDashboardPage("grouppanelalltext1");
			dashboardPage.getTextOfDashboardPage("grouppanelalltext2");
			//Before Group Creation Ensure only Active Devices list is selected
			Assert.assertTrue(deviceListPage.verifyonlyactivedevicesdatafetchforstaticgroups(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDD", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active Device list filtered successfully");
			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			LOGGER.info("Search company to set location filter");
			dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", CommonVariables.Company_Device_Grouping);
			sleeper(5000);
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"));
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter Saved successfully.");
			waitForPageLoaded();
			String property = deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.device_name");
			List<String> devicelist = deviceListPage.getAllTextOfDeviceListPage("deviceserialnumberfield");
			//Verification of group creation functionality by providing Invalid property value (ie., by selecting a property value as device name and trying to give serial number as value)
			Assert.assertTrue(deviceDetailsPage.verifyCreateParentGroup("methodpropertyvalue", property, "propertylist", devicelist, LanguageCode));
			//Verification of device count and list after group creation
			deviceListPage.scrollOnDeviceListPage("lastgroup");
			String groupname = deviceListPage.getTextOfDeviceListPage("lastgroup");
			sleeper(8000);
			deviceListPage.clickOnElementsOfDeviceListPage("lastgroup");
			String Tile = deviceListPage.getTextOfDeviceListPage("groupnametile");
			//Verification of created group name tile after clicking on group
			Assert.assertEquals(groupname, Tile, "Group name tile not getting matched on the devices list for the selected group");
			//verification of No Items available Text Verification after group creation with Invalid property value
			deviceListPage.getTextOfDeviceListPage("Nodevicefoundtext").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "list.no_items"));
			sleeper(20000);// wait for the notification
			deviceListPage.clickByJavaScriptOnDeviceListPage("notificationBellIcon");
			//verification of success notification once after creation
			Assert.assertTrue(deviceDetailsPage.postNotificationCheckDevicegroupsForFailuredownload());
			sleeper(2000);
			gotoDevicesTab();
			//verification of delete group functionality
			Assert.assertTrue(deviceDetailsPage.deletegroup());
		} else {
			LOGGER.info("Toggle is set to off for the provided credentials");
		}
	}

	/**
	 * This test verifies create group functionality CSVUpload
	 * If techpulse-grouping-service toggle is OFF, then it skips the verification of this scenario
	 * if the toggle if ON field picks from device details API
	 */
	@Test(priority = 86, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"})
	public final void verifycreategroupfuntionalityCSVUpload() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		LOGGER.info("Redirected to Devices tab");
		if (toggleVerification(DeviceListPage.Device_Grouping_toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			//To Verify whether any company is selected from the global location by default
			if (!dashboardPage.getTextOfDashboardPage("alllocationtext").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
				LOGGER.info("Verifying global filter functionality");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
				clearCompanyFilterDashboard(LanguageCode);
			}
			dashboardPage.getTextOfDashboardPage("grouppanelalltext1");
			dashboardPage.getTextOfDashboardPage("grouppanelalltext2");
			//Before Group Creation Ensure only Active Devices list is selected
			Assert.assertTrue(deviceListPage.verifyonlyactivedevicesdatafetchforstaticgroups(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDD", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active Device list filtered successfully");
			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			LOGGER.info("Search company to set location filter");
			dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", CommonVariables.Company_Device_Grouping);
			sleeper(5000);
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"));
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter Saved successfully.");
			waitForPageLoaded();
			List<String> devicelist = deviceListPage.getAllTextOfDeviceListPage("deviceserialnumberfield");
			String property = deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.serial_number");
			Assert.assertTrue(deviceDetailsPage.verifyCreateParentGroup("importCSV", property, "propertylist", devicelist, LanguageCode));
			sleeper(30000);
			//Verify group DeviceList and details after creation functionality
			Assert.assertTrue(deviceDetailsPage.verifyDevicecountandlistaftergroupcreation(devicelist));
			//verification of success notification once after creation
			Assert.assertTrue(deviceDetailsPage.postNotificationCheckDevicegroupsForSuccessfull());
			gotoDevicesTab();
			//verification of delete group functionality
			Assert.assertTrue(deviceDetailsPage.deletegroup());
		} else {
			LOGGER.info("Toggle is set to off for the provided credentials");
		}
	}

	/**
	 * This test verifies the Network Errors Summary card.
	 */
	@Test(priority = 87, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"})
	public final void verifyNetworkErrorSummaryCard() throws Exception {
		login("MSP_ADMIN_US_EMAIl_REPORTS_ALPHA", "MSP_ADMIN_US_PASSWORD_REPORTS");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		resetTableConfiguration();

		deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceSerialNumber");
		if (toggleVerification(DeviceVariables.Network_Error_Toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS_ALPHA"))) {
			//Validation of the Network error section is present or not and navigating to Network Error tab.
			softAssert.assertTrue(deviceDetailsPage.verifynavigationToNetworkErrortab("networkTab", "networkError", "networkerrorsheader", "summary"), "Navigation to Network error Tab Failed");
			//Validation of Network summary card is present or not
			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("summary"), "Network error Summary card is not present in Network Tab");

			ArrayList<String> currentCustomFields = new ArrayList<String>();
			currentCustomFields.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.network.errors.column.winEventId"));
			currentCustomFields.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "contentDetailsEdit.contentDescription"));
			currentCustomFields.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.network.errors.column.networkErrorCount"));
			//Validation of the Column Headers
			softAssert.assertTrue(deviceDetailsPage.verifyheadersofDeviceDetailspage(LanguageCode, currentCustomFields, "summaryheaders", "hyperlinksummary"), "Verification of Column Headers for Network Error Summary card Failed");
			softAssert.assertAll();
			LOGGER.info("Verified verifyNetworkErrorSummaryCard Testcase successfully.");
		} else {
			LOGGER.info("Toggle is set to off for the provided credentials");
		}
	}

	/**
	 * This test verifies the Network Errors Details card.
	 */
	@Test(priority = 88, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"})
	public final void verifyNetworkErrorDetailsCard() throws Exception {
		login("MSP_ADMIN_US_EMAIl_REPORTS_ALPHA", "MSP_ADMIN_US_PASSWORD_REPORTS");
		SoftAssert softAssert = new SoftAssert();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();
		resetTableConfiguration();
		deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceSerialNumber");
		if (toggleVerification(DeviceVariables.Network_Error_Toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS_ALPHA"))) {
			//Validation of the Network error section is present or not and navigating to Network Error tab.
			softAssert.assertTrue(deviceDetailsPage.verifynavigationToNetworkErrortab("networkTab", "networkError", "networkerrorsheader", "details"), "Navigation to Network error Tab Failed");
			//Validation of Network Details card is present or not
			softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("details"), "Network error Details card is not present in Network Tab");
			ArrayList<String> currentCustomFields = new ArrayList<String>();
			currentCustomFields.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "logs.date_time"));
			currentCustomFields.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.network.errors.column.winEventId"));
			currentCustomFields.add(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "asset_details_locationDescription"));
			softAssert.assertTrue(deviceDetailsPage.verifyheadersofDeviceDetailspage(LanguageCode, currentCustomFields, "detailsCardHeader", "hyperlinkDetails"), "Verification of Column Headers for Network Error Details Card Failed.");
			softAssert.assertAll();
			LOGGER.info("Verified verifyNetworkErrorDetailsCard Testcase successfully.");
		} else {
			LOGGER.info("Toggle is set to off for the provided credentials");
		}
	}


	@Test(priority = 89, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "1278007")
	public final void verifyBSODTile() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		if (toggleVerification(DeviceVariables.BSOD_Toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			gotoDevicesTab();
			if (deviceListPage.waitForElementsOfDeviceListPage("clearfilter")) {
				deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
				sleeper(3000);
			}
			deviceListPage.clickOnElementsOfDeviceListPage("selectDropdownButton");
			List<WebElement> dropdownvalues = deviceListPage.getElementsOfDeviceListPage("statusdropdownValues");
			deviceListPage.verifyActiveDevicesSelected(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "asset.last_seen.label"), dropdownvalues);
			deviceListPage.clickByJavaScriptOnDeviceListPage("firstDeviceSerialNumber");
			waitForPageLoaded();
			if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceBreadCrumbs1")) {
				deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("healthAndProtectionTab");
				//Validation of the BSOD section is present or not and navigating to BSOD tab.
				softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("bsodTileTitle").equalsIgnoreCase("BSOD Crashes (last 30 days)"), "Navigation to BSOD Tab Failed");
				//Validation of Columns in BSOD Table
				softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("dateOfOccurenace"), "dateOfOccurenace column is not present in BSOD Table");
				softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("bugCheckCode"), "bugCheckCode column is not present in BSOD Table");
				softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("bugCheckCodeDescription"), "bugCheckCodeDescription column is not present in BSOD Table");
				softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("driverCrash"), "driverCrash column is not present in BSOD Table");
				softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("driverVersion"), "driverVersion column is not present in BSOD Table");
				softAssert.assertAll();
				LOGGER.info("Verified verifyBSODTile Testcase successfully.");
			} else {
				LOGGER.error("Device Details page is not loaded");
			}
		} else {
			LOGGER.info("Toggle is set to off for the provided credentials");
		}

	}

	/**
	 * Validating that the "Not Enrolled" is added as an option in the drop-down for Enrolled column in the Asset list page
	 *
	 * @throws Exception
	 */
	@Test(priority = 90, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "230498")
	public final void verifyNotEnrolledOptionInEnrolledColumn() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage devicePage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

		gotoDevicesTab();
		resetTableConfiguration();
		//verify not enrolled option in enrolled dropdown list
		devicePage.waitForElementsOfDeviceListPage("enrollColumnDropdown");
		devicePage.scrollOnDeviceListPage("enrollColumnDropdown");
		devicePage.clickOnElementsOfDeviceListPage("enrollColumnDropdown");
		Assert.assertTrue(devicePage.getAllTextOfDeviceListPage("enrollColumnList").contains("Not enrolled"));
		devicePage.pressKey(Keys.ESCAPE);
		LOGGER.info("Not enrolled option verification is done successfully");
	}


	@Test(priority = 91, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "1317528")
	public final void verifyDeviceTimeline() throws Exception {
		login("COMPANY_EMAIL_DEVICESECURE_WIDGET", "COMPANY_PWS_DEVICESECURE_WIDGET");
		SoftAssert softAssert = new SoftAssert();
		if (toggleVerification(DeviceVariables.DEVICETIMELINE_Toggle, getCredentials(environment, "DEVICE_TIMELINE_TENANT_ID"))) {
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			gotoDevicesTab();
			if (deviceListPage.waitForElementsOfDeviceListPage("clearfilter")) {
				deviceListPage.clearFiltersOfDevicesListPage("clearfilter");
				sleeper(3000);
			}
			deviceListPage.clickOnElementsOfDeviceListPage("selectDropdownButton");
			List<WebElement> dropdownvalues = deviceListPage.getElementsOfDeviceListPage("statusdropdownValues");
			deviceListPage.verifyActiveDevicesSelected(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "asset.last_seen.label"), dropdownvalues);
			deviceListPage.clickByJavaScriptOnDeviceListPage("firstDeviceSerialNumber");
			waitForPageLoaded();
			if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceBreadCrumbs1")) {
				deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("deviceTimelineTab");
				// Validation of the Device Timeline section is present or not and navigating to Device Timeline tab.
				softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceTimelineTitle").equals(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "device_details_timeline")), "Device Timeline Tab text is incorrect");
				softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceTimelineMessage").equals(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "device.details.device.timeline.title")), "Device Timeline Tab messgae is incorrect");
				// Validation of Contents in Device Timeline Table
				softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceTimelineUpdatedDate"), "Updated Date is not present in Device Timeline tab");
				softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceTimelineGraph"), "Graph is not present in Device Timeline tab");
				softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("devicetimelineXaxis"), "X-axis is not present in Device Timeline tab");
				softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceTimelineEvents"), "Y-axis is not present in Device Timeline tab");
				deviceDetailsPage.mousehoverOnDeviceDetailsPage("deviceTimelineEvents");
				softAssert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceTimelineEventsTooltip"), "Tooltip is not present in Device Timeline tab");
				//Validation of the Error Event section is present or not and navigating to Error Event tab.
				deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("errorEventLink");
				softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("errorEventPageTitle").equalsIgnoreCase("Error Events"), "Navigation to Error Event Tab Failed");
				switchBackToPreviousTab();

				softAssert.assertAll();
				LOGGER.info("Verified verifyDeviceTimeline Testcase successfully.");
			}

		}
	}

	@Test(priority = 92, groups = {"REGRESSIONDEVICES1", "REGRESSION_CI", "ALL", "ALL_CI"}, description = "test case ID: 209822")
	public final void verifyFilterFunctionalityOnEachColumn () throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert=new SoftAssert();
		gotoDevicesTab();
		resetTableConfiguration();
		deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		Assert.assertFalse(deviceListPage.verifyElementsOfDeviceListPage("noElementDisplayText"), "No device is present on device page, unable to proceed further");
		LOGGER.info("Atleast one device is present, test case started");
		deviceListPage.waitForElementsOfDeviceListPage("firstDevice");
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		tableConfigurationPage = tableConfigurationPage.getInstance();
		tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for the searchbox on Serial column
		softAssert.assertTrue(deviceListPage.verifySearchValueOnDevice(LanguageCode, "deviceSerialSearchBox", DeviceVariables.INCORRECT_SEARCH_STRING, "noElementDisplayText", "SerialNoList"), "Search functionality with incorrect search string for device column on company list page is not working");
		softAssert.assertTrue(deviceListPage.verifySearchValueOnDevice(LanguageCode, "deviceSerialSearchBox", deviceName, "noElementDisplayText", "SerialNoList"), "Search functionality for device column on company list page is not working");
		LOGGER.info("Verified filter functionality for serial column");


		// This test case validates if the filter functionality is working
		// properly for
		// the searchbox on name column
		deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		softAssert.assertTrue(deviceListPage.verifySearchValueOnDevice(LanguageCode, "deviceSearchBox", DeviceVariables.INCORRECT_SEARCH_STRING, "noElementDisplayText", "DeviceList"), "Search functionality with incorrect search string for name column on company list page is not working");
		softAssert.assertTrue(deviceListPage.verifySearchValueOnDevice(LanguageCode, "deviceSearchBox", staticSerial, "noElementDisplayText", "DeviceList"), "Search functionality for name column on company list page is not working");
		LOGGER.info("Verified filter functionality for name column");
		// This test case validates if the filter functionality is working
		// properly for- the searchbox on model column
		deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		softAssert.assertTrue(deviceListPage.verifySearchValueOnDevice(LanguageCode, "modelSearchBox", DeviceVariables.INCORRECT_SEARCH_STRING, "noElementDisplayText", "ModelList"), "Search functionality with incorrect search string for model column on company list page is not working");
		softAssert.assertTrue(deviceListPage.verifySearchValueOnDevice(LanguageCode, "modelSearchBox", staticModel, "noElementDisplayText", "ModelList"), "Search functionality for model column on company list page is not working");
		LOGGER.info("Verified filter functionality for plan column");

		// verify filter functionality is working properly for the dropdown on Subscription(Plan) column
		softAssert.assertTrue(deviceListPage.verifySingleMultiSelect("planBoxBefore", "subscriptionList", LanguageCode),"Single/Multi select on subscription column fails");
		// verify filter functionality is working properly for the dropdown on Status column
		softAssert.assertTrue(deviceListPage.verifySingleMultiSelect("deviceStatusBoxBefore", "deviceStatusList", LanguageCode),"Single/Multi select on status column fails");
		// verify filter functionality is working properly for the dropdown on Type column
		softAssert.assertTrue(deviceListPage.verifySingleMultiSelect("deviceTypeBoxBefore", "deviceTypeList", LanguageCode),"Single/Multi select on type column fails");

		// verify filter functionality is working properly for the dropdown on Enrolled column
		softAssert.assertTrue(deviceListPage.verifySingleMultiSelect("deviceEnrolledBoxBefore", "deviceEnrolledList", LanguageCode),"Single/Multi select on Enroll column fails");

		// verify search works properly for the searchbox on Location column
		deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		softAssert.assertTrue(deviceListPage.verifySearchValueOnDevice(LanguageCode, "deviceLocationSearchBox", DeviceVariables.INCORRECT_SEARCH_STRING, "noElementDisplayText", "deviceLocationList"), "Search functionality with incorrect search string for name column on company list page is not working");
		softAssert.assertTrue(deviceListPage.verifySearchValueOnDevice(LanguageCode, "deviceLocationSearchBox", staticLocation, "noElementDisplayText", "deviceLocationList"), "Search functionality for name column on company list page is not working");

		// verify select works properly for Company column
		LOGGER.info("Verified filter functionality for Location column");
		softAssert.assertTrue(deviceListPage.verifySingleSelect("deviceCompanyBoxBefore", "deviceCompanyList", LanguageCode),"Single select on company fails");
		LOGGER.info("Verified filter functionality for company column");

		// verify search works properly for the alias column
		softAssert.assertTrue(deviceListPage.verifySearchColumn("deviceAliasSearchBox", DeviceVariables.INCORRECT_SEARCH_STRING, "deviceAliasFirstRow", alias, LanguageCode),"alias colummn search fails");
		LOGGER.info("Alias search verified successfully...");
		// verify search works properly for the asset tag column
		softAssert.assertTrue(deviceListPage.verifySearchColumn("assetTagSearch", DeviceVariables.INCORRECT_SEARCH_STRING, "assetTagFirstRow", alias, LanguageCode),"Asset tag colummn search fails");
		LOGGER.info("Tag search verified successfully...");
		// verify search works properly for the asset cost center column
		softAssert.assertTrue(deviceListPage.verifySearchColumn("costCenterSearch", DeviceVariables.INCORRECT_SEARCH_STRING, "costCenterFirstRow", costCenter, LanguageCode),"Cost center colummn search fails");
		LOGGER.info("Cost center search verified successfully...");
		// verify search works properly for the dept column
		softAssert.assertTrue(deviceListPage.verifySearchColumn("departmentSearch", DeviceVariables.INCORRECT_SEARCH_STRING, "departmentFirstRow", costCenter, LanguageCode),"Dept colummn search fails");
		LOGGER.info("Dept search verified successfully...");
		softAssert.assertAll();

	}
}
