package com.testscripts.daasui;
import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.DeviceVariables;
import com.daasui.pages.DEXPage;
import com.daasui.pages.WEXCustomerHomePage;
import com.daasui.pages.WEPDeviceDetailsPage;
import com.daasui.pages.WEPDeviceListPage;
import com.daasui.pages.WEXDashboardPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.daasui.constants.ConstantPath.*;
import static com.daasui.constants.DeviceVariables.*;
import static org.testng.Assert.*;

public class WEPDeviceListTest extends CommonTest {

    private static final Logger LOGGER = LogManager.getLogger(WEPDeviceListTest.class);


    /**
     * This will verify the PC's Device List page has all the expected properties and is loading fine
     */
    @Test(priority = 1, groups = {"REGRESSION_INVENTORYINGESTION","PRODUCTION_INVENTORYINGESTION","PRODUCTION_LDK","INITECH_SOLUTIONS"}, description = "TestCase ID :42525167")
    public final void verifyPCDeviceListPage() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyPCDeviceListPage()");
        waitForPageLoaded();
        LOGGER.info("Redirected into customer device list page successfully");
        List<String> expectedColumnList = new ArrayList<>(Arrays.asList(
        		getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.serial_number"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_detail.accessories_column.model"),
                getTextLanguage(LanguageCode, "daas_ui", "unassigned_devices.list.status"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_modal_pctype"),
                getTextLanguage(LanguageCode, "daas_ui", "assets.details.general_informations.location")
                ));
        js.executeScript("document.body.style.zoom='50%'");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("columnOptionBtn");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("columnOptionBtn");
        sleeper(2000);
        wepDeviceListPage.verifyElementIsClickableOfDeviceListPage("resetColumnFilter");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("resetColumnFilter");
        sleeper(2000);
        wepDeviceListPage.verifyElementIsClickableOfDeviceListPage("saveColumnFilter");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("saveColumnFilter");
        waitForPageLoaded();
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("addDeviceBtn");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("clearfilter");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("columnOptionBtn");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("filterInListPage");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("showingPagination");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("showingPaginationTotalCount");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("navigationItemNext");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("navigationItemPrevious");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("navigationItemActivePage");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("tablePagination");
        Assert.assertTrue(wepDeviceListPage.verifyTableColumns(expectedColumnList,"deviceListPageTableColumns"), "Table Columns are not as expected");
        LOGGER.info("Fleet Management Devices Page verified and all the available controls successfully");
    }

    /**
     * This will verify the addition of device via upload
     */
    @Test(priority = 2, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :43120466")
    public final void verify_UploadCSV_AddsNewDevices_FromPCTab_For_Customer() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        String csvPath = String.format("%s%s", IMPORT_PATH, DEVICESWITHCORRECTSERIALNUMBER_UPLOAD);
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verify_UploadCSV_AddsNewDevices_FromPCTab_For_Customer()");
        waitForPageLoaded();
        wepDeviceListPage.uploadAssetsViaPhysicalAssetsListPage(csvPath);
        ArrayList<String> serialNumberListToVerifyAddAndDelete = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvPath);
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToVerifyAddAndDelete, false), "Some Device added are not exist in the Device List page");
        LOGGER.info("verifyWEPAddPhysicalAsset_FromCSV_Customer() - Passed: Verified Notification after adding device in bulk and verified added device");
        wepDeviceListPage.DeleteDeviceAndVerifyExistence(serialNumberListToVerifyAddAndDelete);
        LOGGER.info("verifyWEPAddPhysicalAsset_FromCSV_Customer() - Passed : Deleting devices after verifying");
    }

    /**
     * This will verify that the add functionality of multiple devices via csv upload having duplicate serial number should update the device
     */
    @Test(priority = 3, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :43919483")
    public final void verifyAddingDevicesWith_DuplicateSerialNumber() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        String csvFilePath = IMPORT_PATH + DEVICESWITHDUPLICATESERIALNUMBER_UPLOAD;
        ArrayList<String> serialNumberListToUse = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvFilePath);
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyAddingDevicesWith_DuplicateSerialNumber()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.deleteDeviceIfExistBeforeAddDevicesFromListPage(serialNumberListToUse);
        wepDeviceListPage.uploadAssetsViaPhysicalAssetsListPage(csvFilePath);
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToUse, false), "Device added is not exist in the Device List page");
        LOGGER.info("Verified device with duplicate Serial Number is added & updated the same");
    }

    /**
     * This will verify that the add functionality of multiple devices via csv upload having duplicate asset tag should not added
     */
    @Test(priority = 4, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :43919483")
    public final void verifyAddingDevicesWith_DuplicateAssetTag() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        wepDeviceListPage.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
        String csvFilePath_D1 = IMPORT_PATH + DEVICESWITHDUPLICATEASSETTAG_D1_UPLOAD;
        String csvFilePath_D2 = IMPORT_PATH + DEVICESWITHDUPLICATEASSETTAG_D2_UPLOAD;
        ArrayList<String> serialNumberListToUse_D1 = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvFilePath_D1);
        ArrayList<String> serialNumberListToUse_D2 = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvFilePath_D2);
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyAddingDevicesWith_DuplicateAssetTag()");
        waitForPageLoaded();
        js.executeScript("document.body.style.zoom='70%'");
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.deleteDeviceIfExistBeforeAddDevicesFromListPage(serialNumberListToUse_D1);
        wepDeviceListPage.deleteDeviceIfExistBeforeAddDevicesFromListPage(serialNumberListToUse_D2);
        wepDeviceListPage.uploadAssetsViaPhysicalAssetsListPage(csvFilePath_D1);
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToUse_D1, false), "Device added is not exist in the Device List page");
        LOGGER.info("Verified device is added successfully");
        wepDeviceListPage.uploadAssetsViaPhysicalAssetsListPage(csvFilePath_D2);
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToUse_D2, true), "Device added is exist in the Device List page");
        LOGGER.info("Verified device with duplicate asset tag is not added");

    }

    /**
     * This will verify that the add functionality of multiple devices via csv upload having duplicate serial number and asset tag should not happen
     */
    @Test(priority = 5, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :43919482")
    public final void verifyAddingDevicesWith_DuplicateSerialNumberAndAssetTag() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        String csvFilePath = IMPORT_PATH + DEVICESWITHDUPLICATESERIALNUMBER_ASSET_TAG_UPLOAD;
        ArrayList<String> serialNumberListToUse = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvFilePath);
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyAddingDevicesWith_DuplicateSerialNumberAndAssetTag()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.deleteDeviceIfExistBeforeAddDevicesFromListPage(serialNumberListToUse);
        wepDeviceListPage.uploadAssetsViaPhysicalAssetsListPage(csvFilePath);
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToUse, false), "Device added are not exist in the Device List page");
        LOGGER.info("Verified device is getting updated when try to add two devices with duplicate serial number & asset tag");
    }

    /**
     * This will verify that the sorting functionality of the device list page
     */
    @Test(priority = 6, groups = {"REGRESSION_INVENTORYINGESTION","PRODUCTION_LDK","INITECH_SOLUTIONS"}, description = "TestCase ID :42525157")
    public final void verifySortingOfDevices() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifySortingOfDevices()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("serialNumberSortBtn");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("serialNumberSortBtn");
        List<String> deviceSerialNumberList = wepDeviceListPage.getAvailableDevices();
        boolean isDesc = wepDeviceListPage.getSortingOrderType();
        assertTrue(wepDeviceListPage.IsDeviceSorted(deviceSerialNumberList, isDesc), "The devices are not in sorted format");
        isDesc = wepDeviceListPage.getSortingOrderType();
        sleeper(5000);
        deviceSerialNumberList = wepDeviceListPage.getAvailableDevices();
        assertTrue(wepDeviceListPage.IsDeviceSorted(deviceSerialNumberList, isDesc), "The devices are not in sorted format");
        LOGGER.info("Sorting Of Devices in list page is Verified successfully");
    }

    /**
     * This will verify that the export functionality of via editable fields option
     */
    @Test(priority = 7, groups = {"REGRESSION_INVENTORYINGESTION","PRODUCTION_LDK","INITECH_SOLUTIONS"}, description = "TestCase ID :42525145")
    public final void verifyExportDevices_ByEditableFields() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyExportDevices_ByEditableFields()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("addDeviceBtn");
        ArrayList<String> deviceSerialNumberList = new ArrayList<>();
        wepDeviceListPage.GetSerialNumberListFromDeviceListPage(deviceSerialNumberList);
        assertTrue(wepDeviceListPage.verifyEditableFieldsCSVFile(deviceSerialNumberList, VALID_ASSET_EXPORT_MSG), "The csv file is not matching the editable fields from UI");
        LOGGER.info("Export functionality by editable fields in device list page is verified successfully");
    }

    /**
     * This will verify that the export functionality of via current fields option
     */
    @Test(priority = 8, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :42525145")
    public final void verifyExportDevices_ByCurrentField() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyExportDevices_ByCurrentField()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("addDeviceBtn");
        ArrayList<String> deviceSerialNumberList = new ArrayList<>();
        wepDeviceListPage.GetSerialNumberListFromDeviceListPage(deviceSerialNumberList);
        wepDeviceListPage.verifyCurrentFieldExportCSV(deviceSerialNumberList, VALID_ASSET_EXPORT_MSG);
        LOGGER.info("Export functionality By Current Field fields in device list page is verified successfully");
    }


    /**
     * This will verify that the add functionality of multiple devices via csv upload having no serial number should not be uploaded
     */
    @Test(priority = 9, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :43919484")
    public final void verifyWEXBulkDeviceImportByCSV_WITHOUTSERIALNUMBER() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        String csvFilePath = IMPORT_PATH + ASSETWITHOUTSERIAL_NUMBER_UPLOAD;
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyWEXBulkDeviceImportByCSV_WITHOUTSERIALNUMBER()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.Verify_AddPhysicalAssetUpload_And_DisplayNotification_Message(csvFilePath,DUPLICATE_ASSET_UPLOAD_LOG_FIRST_PART_MSG + 
        		" "+ ASSETWITHOUTSERIAL_NUMBER_UPLOAD+ "."+ DUPLICATE_ASSET_UPLOAD_LOG_LAST_PART_MSG);
        wepDeviceListPage.downloadNotificationFile("errorFileLinkBellIcon",ASSETWITHOUTSERIAL_NUMBER_UPLOAD);
        assertTrue(wepDeviceListPage.extractColumnValueAndVerifyMessage(IMPORT_EMPTY_SERIAL_NO_ASSET_CSV_ERROR,"Description"),"Error in CSV is not same as the message provided to verify");
        LOGGER.info("Notification message verification for import has passed while processing csv without header");
        pressKey(Keys.ESCAPE);
        assertTrue(wepDeviceListPage.verifyDescriptionOnLogsPage(ASSETWITHOUTSERIAL_NUMBER_LOG_ERR_MSG, ASSETWITHOUTSERIAL_NUMBER_UPLOAD), "Description of imported devices on logs page is incorrect");
        LOGGER.info("Description on logs page is correct when devices are not imported successfully while processing csv without header");
    }

    /**
     * This will verify that the add functionality of multiple devices via csv upload having incorrect header should not be uploaded
     */
    @Test(priority = 10, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :43919485")
    public final void verifyWEXBulkDeviceImportByCSV_WITHINCORRECTHEADER() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        String csvFilePath = IMPORT_PATH + ASSETWITHWRONGHEADER_UPLOAD;
        ArrayList<String> serialNumberListToUse = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvFilePath);
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyWEXBulkDeviceImportByCSV_WITHINCORRECTHEADER()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.deleteDeviceIfExistBeforeAddDevicesFromListPage(serialNumberListToUse);
        wepDeviceListPage.uploadAssetsViaPhysicalAssetsListPage(csvFilePath);
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToUse, true), "Some Device added are exist in the Device List page");
        LOGGER.info("Bulk Device Import By csv with incorrect header is verified successfully");
    }

    /**
     * This will verify that the add functionality of multiple devices via csv upload having wrong data format should not be uploaded
     */
    @Test(priority = 11, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :44017281")
    public final void verifyWEXBulkDeviceImportByCSV_WITHWRONGFORMAT() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        String csvFilePath = IMPORT_PATH + ASSETWITHWRONGSERIALNUMBERFORMAT_UPLOAD;
        ArrayList<String> serialNumberListToUse = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvFilePath);
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);        
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyWEXBulkDeviceImportByCSV_WITHWRONGFORMAT()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.deleteDeviceIfExistBeforeAddDevicesFromListPage(serialNumberListToUse);
        wepDeviceListPage.uploadAssetsViaPhysicalAssetsListPage(csvFilePath);
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToUse, true), "Some Device added are exist in the Device List page");
        LOGGER.info("Bulk Device Import By csv with wrong format is verified successfully");
    }

    /**
     * This will verify that the pagination in the list page is working correctly
     */
    @Test(priority = 12, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :43919486")
    public final void verifyDeviceList_Pagination() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyDeviceList_Pagination()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        verifyPaginationOnListPage();
        LOGGER.info("Pagination in list page is verified successfully");
    }

    /**
     * This will verify that the CSV upload via ZTE is working correctly and that it should have the correct status in UI
     */
    @Test(priority = 13, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :43120463")
    public final void verifyWEXBulkDeviceImportByCSV_ZTE() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        String csvFilePath = IMPORT_PATH + DEVICESWITHCORRECTSERIALNUMBERZTE_UPLOAD;
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        String companyToSelect = getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME_ASSET");
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.partnerWithCompanyView(companyToSelect,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyWEXBulkDeviceImportByCSV_ZTE()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        ArrayList<String> serialNumberListToUse = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvFilePath);
        wepDeviceListPage.deleteDeviceIfExistBeforeAddDevicesFromListPage(serialNumberListToUse);
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("addIndividualDeviceBtn");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("addIndividualDeviceBtn");
        wepDeviceListPage.AutoEnrollDevices(csvFilePath);
		js.executeScript("document.body.style.zoom='50%'");
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToUse, false), "Some Device added are not exist in the Device List page");
        assertTrue(wepDeviceListPage.VerifyAddedDeviceStatus(serialNumberListToUse, "Error"), "Status is not matching");
        LOGGER.info("Notification message verification for CSV_ZTE import has passed");
    }

    /**
     * This is to validate that the CSV upload having serial numbers that are in the SNR database is coming with Ready state
     */
    @Test(priority = 14, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :43120463")
    public final void verifyWEXBulkDeviceImportByCSV_ZTE_WithSNR() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        String csvFilePath = IMPORT_PATH + DEVICESWITHCORRECTSERIALNUMBERZTE_UPLOAD_SNR;
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        String companyToSelect = getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME_ASSET");
        JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.partnerWithCompanyView(companyToSelect,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyWEXBulkDeviceImportByCSV_ZTE_WithSNR()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        ArrayList<String> serialNumberListToUse = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvFilePath);
        wepDeviceListPage.deleteDeviceIfExistBeforeAddDevicesFromListPage(serialNumberListToUse);
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("addIndividualDeviceBtn");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("addIndividualDeviceBtn");
        wepDeviceListPage.AutoEnrollDevices(csvFilePath);
        ArrayList<String> serialNumberListToVerify = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvFilePath);
		js.executeScript("document.body.style.zoom='50%'");
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToVerify, false), "Some Device added are not exist in the Device List page");
        assertTrue(wepDeviceListPage.VerifyAddedDeviceStatus(serialNumberListToVerify, "Ready"), "Status is not matching");
        LOGGER.info("Notification message verification for CSV ZTE_WithSNR import has passed");
    }

    /**
     * This is to validate that the device details page have correct header tabs and that it is loading correctly
     */
    @Test(priority = 15, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :44551420")
    public final void verifyWEPDeviceDetails_Header() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        String serialNumberToUse  = getEnvironmentSpecificData(System.getProperty("environment"), "TEST_DEVICE_SERIAL_NUM");
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyWEPDeviceDetails_Header()");
        waitForPageLoaded();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", serialNumberToUse);
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberText");
        waitForPageLoaded();
        WEPDeviceDetailsPage.verifyElementIsVisible("deviceDetailsHeader");
        LOGGER.info("Verification of device details header successfully");
    }

    /**
     * This is to validate that all the tabs in the device details are loading correctly
     */
    @Test(priority = 16, groups = {"REGRESSION_INVENTORYINGESTION", "REGRESSION_INTEGRATIONQA_CUJ","PRODUCTION_INVENTORYINGESTION"}, description = "TestCase ID :44551420, C43733057")
    public final void verifyWEPDeviceDetailsTabs() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        String serialNumber = getEnvironmentSpecificData(System.getProperty("environment"), "ASSET_DEVICE_SERIAL_NUM");
        JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyWEPDeviceDetailsTabs()");
        js.executeScript("document.body.style.zoom='67%'");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", serialNumber);
        sleeper(2000);
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberText");
        waitForPageLoaded();
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("deviceDetailsHeader");
        assertTrue(WEPDeviceDetailsPage.VerifyOverviewTabsContents(serialNumber), "Overview tab data is not matching the expected data");  
        //assertTrue(WEPDeviceDetailsPage.VerifyNewTimelineTabContents(), "TimeLine tab sections are not matching the expected header sections");
        assertTrue(WEPDeviceDetailsPage.VerifyHealthAndProtectionTabContents(), "Health and Protection tab sections are not matching the expected header sections");
        assertTrue(WEPDeviceDetailsPage.VerifyHardwareTabContents(), "Hardware tab header sections are not matching the expected header sections");
        assertTrue(WEPDeviceDetailsPage.VerifyNetworkTabContents(), "Network tab header sections are not matching the expected header sections");
        assertTrue(WEPDeviceDetailsPage.VerifyBIOSTabContents(), "BIOS tab sections are not matching the expected header sections");
        assertTrue(WEPDeviceDetailsPage.VerifySoftwareTabContents(), "Software tab sections are not matching the expected header sections");
        assertTrue(WEPDeviceDetailsPage.VerifyLocationTabContents(), "Location tab sections are not matching the expected header sections");
        LOGGER.info("Verified each tab of device details successfully");
    }
    
    /**
     * This will verify the addition of device via multiple csv upload
     */
    @Test(priority = 17, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :48595474")
    public final void verifyWEPAddPhysicalAsset_From_Multiple_CSV_Customer() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        wepDeviceListPage.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
        String csvFilePath_D1 = IMPORT_PATH + DEVICESWITHCORRECTSERIALNUMBER_UPLOAD;
        String csvFilePath_D2 = IMPORT_PATH + DEVICESWITHCORRECTSERIALNUMBER_UPLOAD_COPY;
        ArrayList<String> serialNumberListToUse_D1 = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvFilePath_D1);
        ArrayList<String> serialNumberListToUse_D2 = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvFilePath_D2);
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyWEPAddPhysicalAsset_From_Multiple_CSV_Customer()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.deleteDeviceIfExistBeforeAddDevicesFromListPage(serialNumberListToUse_D1);
        wepDeviceListPage.deleteDeviceIfExistBeforeAddDevicesFromListPage(serialNumberListToUse_D2);
        wepDeviceListPage.uploadAssetsViaPhysicalAssetsListPage(csvFilePath_D1);
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToUse_D1, false), "Device added from Devices.csv is not exist in the Device List page");
        LOGGER.info("Verified Device added from Devices.csv is exist in the Device List pag successfully");
        wepDeviceListPage.uploadAssetsViaPhysicalAssetsListPage(csvFilePath_D2);
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToUse_D2, false), "Device added from Devices_2.csv is not exist in the Device List page");
        LOGGER.info("Verified Device added from Devices_2.csv is exist in the Device List pag successfully");
    }

    /**
     * This is to validate that Recommended Action
     * This test script referring internal Squad team test case verifyTroubleshootButton(),
     * verifyViewDevicesRecommendedActionsTile(), verifyRecommendedActionsFromHomeDashboard()
     */
    @Test(priority = 18, groups = {"REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID :C42397648", enabled = false)
    public final void verifyWEPDeviceRecommendedActions() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyWEPAddPhysicalAsset_From_Multiple_CSV_Customer()");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("recommendedActionContent");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("highRATile");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("mediumRATile");
        Thread.sleep(2000);
        softAssert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDevicePage("recommendedActionMoreContent"), "Recommendation action tiles not available.");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("recommendedActionMoreContent");
        softAssert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDevicePage("rafilters"), "Recommendation action filter not available.");
        softAssert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDevicePage("clearFilters"), "Recommendation action clear filer option not available.");
        softAssert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDevicePage("highFilter"), "Recommendation action High filer option not available.");
        softAssert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDevicePage("mediumFilter"), "Recommendation action Medium filer option not available.");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("mediumFilter");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("lowFilter");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("highOnRAFilter");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("mediumOnRAFilter");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("lowOnRAFilter");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("lowOnRAFilter");
        Thread.sleep(2000);
        wepDeviceListPage.clickByJavaScriptOnDevicePage("clearFilters");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("highFilterCheckBox");
        waitForPageLoaded();       
        wepDeviceListPage.clickByJavaScriptOnDevicePage("clearFilters");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("mediumFilterCheckBox");
        waitForPageLoaded();
        wepDeviceListPage.clickByJavaScriptOnDevicePage("clearFilters");
        softAssert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDevicePage("sysHealthCheckBox"), "Recommendation action System health filer option not available.");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("sysHealthCheckBox");
        waitForPageLoaded();
        wepDeviceListPage.clickByJavaScriptOnDevicePage("clearFilters");
        softAssert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDevicePage("osPerfCheckBoxs"), "Recommendation action OS Performance check filer option not available.");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("osPerfCheckBoxs");
        waitForPageLoaded();
        wepDeviceListPage.clickByJavaScriptOnDevicePage("clearFilters");
        softAssert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDevicePage("securityCheckBox"), "Recommendation action Security check filer option not available.");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("securityCheckBox");
        waitForPageLoaded();
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("bitLockerIssues");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("bitLockerIssues");
        Thread.sleep(2000);
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("enableBitLockerEncryption");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("securityImpactedDevices");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("perOfFleetImpactedDevices");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("exportDevices");
        String impactedDevices = wepDeviceListPage.getTextOfWEPDeviceListPage("impactedDevices");
        String totalImpactedDevices = wepDeviceListPage.getTextOfWEPDeviceListPage("totalImpactedDevices").replace("of ", "");
        int impactedDevicesCount = Integer.parseInt(impactedDevices);
        int totalImpactedCount = Integer.parseInt(totalImpactedDevices);
        Assert.assertEquals(impactedDevicesCount, totalImpactedCount, "Mismatch found: The impacted devices and total devices count are different.");
        softAssert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDevicePage("viewMore"), "View More option not available.");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("viewMore");
        softAssert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDevicePage("remediationGuide"), "Remediation Guide option not available.");
        softAssert.assertAll();
        LOGGER.info("Recommendation action functionality validation has been completed successfully.");
    }

    /**
     * This test script is to Verify device details page.
     * Reference verifyWEPDeviceDetailsTabs()
     */
    @Test(priority = 19, groups = {"REGRESSION_INTEGRATIONQA_CUJ","REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :C43733057")
    public final void verifyWEPDeviceDetailsAllTabs() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        String serialNumberToUse  = getEnvironmentSpecificData(System.getProperty("environment"), "TEST_DEVICE_SERIAL_NUM");
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyWEPDeviceDetailsAllTabs()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", serialNumberToUse);
        sleeper(2000);
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberTextInVirtualMachines");
        waitForPageLoaded();
        js.executeScript("document.body.style.zoom='67%'");
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("deviceDetailsHeader");
        //assertTrue(WEPDeviceDetailsPage.VerifyNewTimelineTabContents(), "TimeLine tab sections are not matching the expected header sections");
        Thread.sleep(2000);
        assertTrue(WEPDeviceDetailsPage.VerifyHealthAndProtectionTabContents(), "Health and Protection tab sections are not matching the expected header sections");
        Thread.sleep(2000);
    }


    /**
     * This test will Verify devices tab on Fleet management
     */
    @Test(priority = 20, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID :C42397647", enabled = false)
    public final void verifyFeetManagementGrouping() throws  Exception{
        WEPDeviceListPage WEPDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        verifyPCDeviceListPage();
        LOGGER.info("WEXDeviceListPage verification completed.");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("selectAllCheckBox");
        WEPDeviceListPage.clickByJavaScriptOnDevicePage("selectAllCheckBox");
        LOGGER.info("Clicked on select all checkbox.");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("exportDeviceBtn");
        softAssert.assertTrue(WEPDeviceListPage.verifyElementsOfWEPDeviceLstPage("exportDeviceBtn"),"exportDeviceButton not Present");
        LOGGER.info("Verified  export button");
        softAssert.assertTrue(WEPDeviceListPage.verifyElementsOfWEPDeviceLstPage("deleteDeviceBtn"),"deleteDeviceButton not Present");
        LOGGER.info("Verified  delete button");
        WEPDeviceListPage.clickByJavaScriptOnDevicePage("exportDeviceBtn");
        WEPDeviceListPage.doubleClickByActionsWEPClickDevicelistPage("editableFiields");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("exportToast");
        wepDeviceListPage.ClearAndResetDeviceListTable();
        waitForPageLoaded();
        String totalDevice = wepDeviceListPage.getTextOfWEPDeviceListPage("deviceCount").replace("of ", "");
        int totalDevices = Integer.parseInt(totalDevice);
        System.out.println("Count from device details page" + totalDevices);
        sleeper(2000);
        wepDeviceListPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        hardRefresh();
        wepDeviceListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DASHBOARD);
        waitForPageLoaded();
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("devicePC");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("deviceCountPC");
        WEPDeviceListPage.scrollOnWEPDeviceListPage("deviceCountPC");
        String totalFMDevice = wepDeviceListPage.getTextOfWEPDeviceListPage("deviceCountPC");
        int totalFMDevices = Integer.parseInt(totalFMDevice);
        System.out.println("Count FM device details page" + totalFMDevices);
        // Verify device list count should be matching to "PC Inventory" count which is present on FM dashboard.
        Assert.assertEquals(totalFMDevices, totalDevices, "Mismatch found: The devices details device count and FM dashboard devices count are different.");
        hardRefresh();
        wepDeviceListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
        String csvPath = String.format("%s%s", IMPORT_PATH, DEVICESWITHCORRECTSERIALNUMBER_UPLOAD);
        wepDeviceListPage.Verify_AddPhysicalAssetUpload_And_DisplayNotification_Message( csvPath, VALID_ASSET_UPLOAD_LOG_MSG);
        ArrayList<String> serialNumberListToVerifyAddAndDelete = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvPath);
        //Clear's the filter if any and reset's the column options to default
        wepDeviceListPage.ClearAndResetDeviceListTable();
        //Verify the device by searching one after the other by extracting from theCSV
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToVerifyAddAndDelete, false), "Some Device added are not exist in the Device List page");
        LOGGER.info("verifyWEPAddPhysicalAsset_FromCSV_Customer() - Passed: Verified Notification after adding device in bulk and verified added device.");
        wepDeviceListPage.ClearAndResetDeviceListTable();
        waitForPageLoaded();
        String totalNewDevice = wepDeviceListPage.getTextOfWEPDeviceListPage("deviceCount").replace("of ", "");
        int totalNewDevices = Integer.parseInt(totalNewDevice);
        System.out.println("Count from device2 details page" + totalNewDevices);
        sleeper(2000);
        wepDeviceListPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        hardRefresh();
        wepDeviceListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DASHBOARD);
        waitForPageLoaded();
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("devicePC");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("deviceCountPC");
        WEPDeviceListPage.scrollOnWEPDeviceListPage("deviceCountPC");
        String totalNewFMDevice = wepDeviceListPage.getTextOfWEPDeviceListPage("deviceCountPC");
        int totalNewFMDevices = Integer.parseInt(totalNewFMDevice);
        System.out.println("Count FM2 device details page" + totalNewFMDevices);
        // Verify device list count should be matching to "PC Inventory" count which is present on FM dashboard.
        Assert.assertEquals(totalNewFMDevices, totalNewDevices, "Mismatch found: The devices details device count and FM dashboard devices count are different.");
        hardRefresh();
        wepDeviceListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
        Thread.sleep(3000);
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("clrFilter");
        WEPDeviceListPage.clickByJavaScriptOnDevicePage("clrFilter");
        //Deleting the devices added one by one
        wepDeviceListPage.DeleteDeviceAndVerifyExistence(serialNumberListToVerifyAddAndDelete);
        LOGGER.info("verifyWEPAddPhysicalAsset_FromCSV_Customer() - Passed : Deleting devices after verifying.");
        // Opening Group panel
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("grouppanel");
        WEPDeviceListPage.clickByJavaScriptOnDevicePage("grouppanel");
        LOGGER.info("Clicked on Group panel");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("groupbtn");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("publicgroupbtn");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("createstaticgroupbtn");
        Thread.sleep(2000);
        WEPDeviceListPage.actionClickOfDeviceListPage("createstaticgroupbtn");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("groupheader");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("nameboxinput");
        WEPDeviceListPage.clickOnElementsOfWEPDeviceListPage("nameboxinput");
        WEPDeviceListPage.sendkeysOnWEPDeviceListPage("nameboxinput");
        WEPDeviceListPage.scrollOnWEPDeviceListPage("selectGrpMethod");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("selectGrpMethod");
        WEPDeviceListPage.mousehoverOnWEPDeviceListPage("groupMethod");
        WEPDeviceListPage.actionClickOfDeviceListPage("groupMethod");
        WEPDeviceListPage.clickOnElementsOfWEPDeviceListPage("groupMethodManual");
        WEPDeviceListPage.mousehoverOnWEPDeviceListPage("groupProperty");
        WEPDeviceListPage.actionClickOfDeviceListPage("groupProperty");
        WEPDeviceListPage.clickOnElementsOfWEPDeviceListPage("groupPropertyDevice");
        WEPDeviceListPage.clickOnElementsOfWEPDeviceListPage("groupValue");
        WEPDeviceListPage.sendKeysOnWEPDeviceListPage("groupValue", CommonVariables.TEST_DEVICE_SERIAL_NUM );
        Thread.sleep(2000);
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("staticGrpToast");
        WEPDeviceListPage.clickOnElementsOfWEPDeviceListPage("savebutton");
        LOGGER.info("Static group creation completed successfully.");
        Thread.sleep(3000);
        WEPDeviceListPage.clickByJavaScriptOnDevicePage("clrFilter");
        WEPDeviceListPage.clickByJavaScriptOnDevicePage("grouppanel");
        WEPDeviceListPage.mousehoverOnWEPDeviceListPage("staticGrpList");
        WEPDeviceListPage.clickByJavaScriptOnDevicePage("staticGrpList");
        ArrayList<String> serialNumber = new ArrayList<>(List.of(CommonVariables.TEST_DEVICE_SERIAL_NUM));
        WEPDeviceListPage.ClearAndResetDeviceListTable();
        assertTrue(WEPDeviceListPage.verifyAddedWEPDevicesOnListPage(serialNumber), "Devices are not getting reflected back to the list page.");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("staticGrpDeviceCount");
        // Creating dynamic group
        hardRefresh();
        WEPDeviceListPage.clickByJavaScriptOnDevicePage("grouppanel");
        WEPDeviceListPage.clickByJavaScriptOnDevicePage("staticdropdownpane");
        Thread.sleep(2000);
        WEPDeviceListPage.clickByJavaScriptOnDevicePage("dynamicdropdown");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("createdynamicgroupbtn");
        WEPDeviceListPage.actionClickOfDeviceListPage("createdynamicgroupbtn");
        Thread.sleep(2000);
        WEPDeviceListPage.doubleClickByActionsWEPClickDevicelistPage("createdynamicgroupbtn");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("groupheader");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("dynamicanamebox");
        WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("expirationpolicy");
        WEPDeviceListPage.clickOnElementsOfWEPDeviceListPage("nameboxinput");
        WEPDeviceListPage.sendkeysOnWEPDeviceListPage("nameboxinput");
        WEPDeviceListPage.clickOnElementsOfWEPDeviceListPage("savebuttondynamic");
        softAssert.assertTrue(WEPDeviceListPage.verifyElementsOfWEPDeviceLstPage("savebuttondynamic"),"Dynamic group create button missing");
        softAssert.assertTrue(WEPDeviceListPage.verifyElementsOfWEPDeviceLstPage("staticGrpToast"),"Dynamic group creation failed.");
        LOGGER.info("Dynamic group creation completed successfully.");
        wepDeviceListPage.ClearAndResetDeviceListTable();
        WEPDeviceListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
        waitForPageLoaded();
        ArrayList<String> serialNumbers = new ArrayList<>(List.of(CommonVariables.TEST_DEVICE_SERIAL_NUM));
        WEPDeviceListPage.ClearAndResetDeviceListTable();
        assertTrue(WEPDeviceListPage.verifyAddedWEPDevicesOnListPage(serialNumbers), "Devices are not getting reflected back to the list page.");
        WEPDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberText");
        waitForPageLoaded();
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("deviceDetailsHeader");
        assertTrue(WEPDeviceDetailsPage.VerifyHealthAndProtectionTabContents(), "Health and Protection tab sections are not matching the expected header sections");
        LOGGER.info("Verified Health and Protection of device details page successfully");
        softAssert.assertAll();
    }

	/**
	 * This will verify physical assets tab and column headers , search and
	 * pagination
	 */
	@Test(priority = 21, groups = { "REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :48992692")
	public final void verifyPhysicalAssetsTabNavigationandColumnHeaders() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.actionClickOfDeviceListPage("physicalAssetMenu");
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyPhysicalAssetsTabNavigationandColumnHeaders()");
        waitForPageLoaded();
        wepDeviceListPage.ClearAndResetDeviceListTable();
        verifyPaginationOnListPage();
        LOGGER.info("verifyDeviceList_Pagination() - Passed : Pagination functionality verified successfully");
        List<String> expectedColumnList = new ArrayList<>(Arrays.asList("Serial Number", "Asset Tag", "Asset Type", "Asset Name", "Manufacturer", "Model", "Asset Location", "Lifecycle Status"));
        Assert.assertTrue(wepDeviceListPage.verifyTableColumns(expectedColumnList, "physicalAssetsTableColumns"), "Table Columns are not as expected");
	}

	/**
	 * This is to validate Physical Assets details page
	 */
	@Test(priority = 22, groups = { "REGRESSION_INVENTORYINGESTION","PRODUCTION_INVENTORYINGESTION"}, description = "TestCase ID :C50930186")
	public final void verifyPhysicalAssetsDetailsTabs() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        String csvPath = IMPORT_PATH + ASSET_UPLOAD;
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PHYSICALASSETS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyPhysicalAssetsDetailsTabs()");
        waitForPageLoaded();
        ArrayList<String> serialNumberListToVerifyAddAndDelete = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvPath);
        wepDeviceListPage.importAssetViaCsvAndDisplayNotification(csvPath);
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", serialNumberListToVerifyAddAndDelete.get(0));
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberTextInPhysicalAssets");
        waitForPageLoaded();
        assertTrue(WEPDeviceDetailsPage.verifyPhysicalAssetsOverviewTabContents(serialNumberListToVerifyAddAndDelete.get(0)), "Overview tab data is not matching the expected data");
        LOGGER.info("Verified each tab of device details successfully");
	}

	/**
	 * This will verify the csv upload modal for Physical Assets
	 */
	@Test(priority = 23, groups = { "REGRESSION_INVENTORYINGESTION" }, description = "TestCase ID :C50930630")
	public final void verify_CSVModalVerificationForPhysicalAssets() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PHYSICALASSETS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verify_CSVModalVerificationForPhysicalAssets()");
        waitForPageLoaded();
		wepDeviceListPage.ClearAndResetDeviceListTable();
		WEPDeviceListPage WEPDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage.verifyElementsOfWEPDeviceListPage("addDeviceBtn");
		wepDeviceListPage.clickByJavaScriptOnDevicePage("addDeviceInPhysicalAssets");
		wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("addPhysicalAssetModalTitle");
		wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("addPhysicalAssetModalSubTitle");
		wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("addPhysicalAssetModalDesc");
		wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("DownloadCSVPhysAsset");
		wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("UploadCSVPhyAsset");
	}

	/**
	 * This is to validate Add Warranty and Update Warranty from UI for Physical
	 * Assets
	 */
	@Test(priority = 24, groups = { "REGRESSION_INVENTORYINGESTION" }, description = "TestCase ID :50930084 ,51159616")
	public final void verifyAddandUpdateWarrantyFromUIForPhysicalAssets() throws Exception {
		login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_DEVICELIST_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            wepDeviceListPage.showTemporaryBannerInUI("Running - verifyAddandUpdateWarrantyFromUIForPhysicalAssets()");
            waitForPageLoaded();
            // Clear's the filter if any and reset's the column options to default
            wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("PhysicalAssetsBtn");
            wepDeviceListPage.clickByJavaScriptOnDevicePage("PhysicalAssetsBtn");
            wepDeviceListPage.ClearAndResetDeviceListTable();
            String serialNumberToUse = CommonVariables.TEST_PhysicalAsset_SERIAL_NUM;
            if (!getUrlOfCurrentPage().contains("staging"))
                serialNumberToUse = CommonVariables.PROD_DEVICE_SERIAL_NUM;
            ArrayList<String> serialNumbers = new ArrayList<>(List.of(serialNumberToUse));
            wepDeviceListPage.verifyAddedWEPDevicesOnListPage(serialNumbers);
            wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberTextInPhysicalAssets");
            waitForPageLoaded();
            WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
            waitForPageLoaded();
            // Enter Details For Warranty Tab
            WEPDeviceDetailsPage.clickByJavaScriptOnDevicePage("addWarranty");
            WEPDeviceDetailsPage.clickByJavaScriptOnDevicePage("warrantyTitle");
            WEPDeviceDetailsPage.enterTextForDeviceDetailPage("warrantyTitle", "Add Device Warranty");
            LocalDate date = LocalDate.now();
            LocalDate startDate = date.minusDays(15);
            LocalDate endDate = date.plusYears(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            WEPDeviceDetailsPage.clickByJavaScriptOnDevicePage("warrantyStartDate");
            WEPDeviceDetailsPage.enterTextForDeviceDetailPage("warrantyStartDate", startDate.format(formatter));
            WEPDeviceDetailsPage.clickByJavaScriptOnDevicePage("warrantyEndDate");
            WEPDeviceDetailsPage.enterTextForDeviceDetailPage("warrantyEndDate", endDate.format(formatter));
            WEPDeviceDetailsPage.clickByJavaScriptOnDevicePage("warrantyDesc");
            WEPDeviceDetailsPage.enterTextForDeviceDetailPage("warrantyDesc", "Adding Device Warranty");
            WEPDeviceDetailsPage.clickOnElementsOfDevicePage("saveWarranty");
            assertTrue(WEPDeviceDetailsPage.matchTextOfDeviceDetailsPage("addWarrantyStatus", "Active"));
            // Edit Warranty
            WEPDeviceDetailsPage.clickOnElementsOfDevicePage("editWarranty");
            WEPDeviceDetailsPage.enterTextForDeviceDetailPage("warrantyTitle", "Update Device Warranty");
            WEPDeviceDetailsPage.clickOnElementsOfDevicePage("saveWarranty");
            assertTrue(WEPDeviceDetailsPage.matchTextOfDeviceDetailsPage("labelWarrantyTitle", "Update Device Warranty"));
            // Delete Warranty
            WEPDeviceDetailsPage.clickOnElementsOfDevicePage("deleteWarranty");
            WEPDeviceDetailsPage.clickOnElementsOfDevicePage("deleteWarrantyConfirmation");
            WEPDeviceDetailsPage.verifyElementsOfWEPDeviceDetailsPage("addWarranty");
            assertTrue(WEPDeviceDetailsPage.matchTextOfDeviceDetailsPage("addWarrantyText", "Add Warranty"));
        }
        else{
            LOGGER.info("No Physical Asset tab available for toggle.");
      }

	}

	/**
	 * This will verify that the sorting functionality of the Physical Assets page
	 */
	@Test(priority = 25, groups = { "REGRESSION_INVENTORYINGESTION" }, description = "TestCase ID :48992692")
	public final void verifySortingOfPhysicalAssets() throws Exception {
		login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_DEVICELIST_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            wepDeviceListPage.showTemporaryBannerInUI("Running - verifySortingOfPhysicalAssets()");
            waitForPageLoaded();
            wepDeviceListPage.ClearAndResetDeviceListTable();
            wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("PhysicalAssetsBtn");
            wepDeviceListPage.clickByJavaScriptOnDevicePage("PhysicalAssetsBtn");
            wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("serialNumberSortBtn");
            wepDeviceListPage.clickByJavaScriptOnDevicePage("serialNumberSortBtn");
            List<String> PhysicalAssetsList = wepDeviceListPage.getAvailablePhysicalAssets();
            boolean isDesc = wepDeviceListPage.getSortingOrderType();
            assertTrue(wepDeviceListPage.IsDeviceSorted(PhysicalAssetsList, isDesc), "The devices are not in sorted format");
            isDesc = wepDeviceListPage.getSortingOrderType();
            sleeper(5000);
            PhysicalAssetsList = wepDeviceListPage.getAvailablePhysicalAssets();
            assertTrue(wepDeviceListPage.IsDeviceSorted(PhysicalAssetsList, isDesc), "The devices are not in sorted format");
        }

        else{
            LOGGER.info("Physical Asset Tab is not present");
        }
	}

	/**
	 * This is to validate Non-Editable fields in Device Details page
	 */
	@Test(priority = 26, groups = { "REGRESSION_INVENTORYINGESTION" }, description = "TestCase ID :51144256")
	public final void verifyWEPDeviceNameAliasNotEditableInDeviceDetails() throws Exception {
		login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIRTUALMACHINES);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyWEPDeviceNameAliasNotEditableInDeviceDetails()");
        waitForPageLoaded();
		WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", getEnvironmentSpecificData(System.getProperty("environment"), "VIRTUAL_MACHINE_SERIAL_NUMBER"));
		sleeper(2000);
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberText");
		waitForPageLoaded();
		WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("deviceDetailsHeader");
		WEPDeviceDetailsPage.verifyElementIsClickable("overViewTabDeviceNameLabel");
		assertFalse(WEPDeviceDetailsPage.verifyElementIsClickable("overViewTabDeviceValue"),"Device Name field is not editable");
		assertFalse(WEPDeviceDetailsPage.verifyElementIsClickable("overViewTabAliasName"),"Alias field is not editable");
		LOGGER.info("Verified Non Editable fields in device details successfully");
	}
    
	/**
     * This will verify that sorting is not available for Enrolled Column in the list page for Virtual Machines
     */
    @Test(priority = 27, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :C44646678")
    public final void verifyEnrollColumnDoesNotHaveSortingForVirtualMachinesTab() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIRTUALMACHINES);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyEnrollColumnDoesNotHaveSortingForVirtualMachinesTab()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("enrolledSortIcon");
        wepDeviceListPage.waitForElementsOfDeviceListPage("firstSerialNumberText");
        String before_firstSerialNumberText = wepDeviceListPage.getTextOfWEPDeviceListPage("firstSerialNumberText");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("enrolledSortIcon");
        wepDeviceListPage.waitForElementsOfDeviceListPage("firstSerialNumberText");
        String after_firstSerialNumberText = wepDeviceListPage.getTextOfWEPDeviceListPage("firstSerialNumberText");
        LOGGER.info(before_firstSerialNumberText + " : " + after_firstSerialNumberText);
    	Assert.assertEquals(before_firstSerialNumberText, after_firstSerialNumberText, "failed - enroll column is clickable in the list page");
    	LOGGER.info("passed - enroll column is not clickable in the list page as expected");
    }   
    
    /**
     * This will verify Hardware tab has processor details
     */
    @Test(priority = 28, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :48135050")
    public final void verifyVirtualMachineHardwareTabHasProcessorDetails() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIRTUALMACHINES);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyVirtualMachineHardwareTabHasProcessorDetails()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", getEnvironmentSpecificData(System.getProperty("environment"), "VIRTUAL_MACHINE_SERIAL_NUMBER"));
        sleeper(2000);
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberTextInVirtualMachines");
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
        js.executeScript("document.body.style.zoom='67%'");
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("deviceDetailsHeader");
		WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("healthAndProtectionTab");
		WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("hardwareTab");
		WEPDeviceDetailsPage.actionClickOfDeviceDetailsPage("hardwareTab");
		WEPDeviceDetailsPage.waitUntilElementIsPresentOnDeviceDetailsPage("hardwareTabElements");
		WEPDeviceDetailsPage.scrollOnDeviceDetailsListPage("hardwareTabProcessorHeaderSection");
		assertTrue(WEPDeviceDetailsPage.matchTextOfDeviceDetailsPage("hardwareTabProcessorHeaderSection", CommonVariables.VIRTUAL_MACHINE_PROCESSOR_HEADER));
		assertTrue(WEPDeviceDetailsPage.verifyDataIsPresetOrNot("labelProcessorNameValue"),"Processor Name is not present");
		assertTrue(WEPDeviceDetailsPage.verifyDataIsPresetOrNot("labelNoOfCoresValue"),"No of Cores is not present");
		assertTrue(WEPDeviceDetailsPage.verifyDataIsPresetOrNot("labelNoOfLogicalProcessorsValue"),"No of Logical Processors is not present");
		assertTrue(WEPDeviceDetailsPage.verifyDataIsPresetOrNot("labelNoOfEnabledCoresValue"),"No of Enabled Cores is not present");
    }

    /**
     * This will verify HP Anyware is present under Enrolled Apps in OverviewTab
     */
    @Test(priority = 29, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :48135049")
    public final void verifyHPAnywhereIsPresentInEnrolledAppsInOverviewTab() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIRTUALMACHINES);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyHPAnywhereIsPresentInEnrolledAppsInOverviewTab()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", getEnvironmentSpecificData(System.getProperty("environment"), "VIRTUAL_MACHINE_SERIAL_NUMBER"));
        sleeper(2000);
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberTextInVirtualMachines");
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        waitForPageLoaded();
        sleeper(3000);
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("enrollApp");
        WEPDeviceDetailsPage.scrollOnDeviceDetailsListPage("enrollApp");
        assertTrue(WEPDeviceDetailsPage.checkTextOnDeviceDetailsPage("enrolledApplicationsList", CommonVariables.VIRTUAL_MACHINE_APP_NAME));
        LOGGER.info("Verified HP Anywhere is present in enrolled apps in Overview Tab successfully");
    }   
        
    /**
	 * This will verify physical assets do not have Product field
	 */
	@Test(priority = 30, groups = { "REGRESSION_INVENTORYINGESTION" }, description = "TestCase ID :C49181996")
	public final void verifyPhysicalAssetsDetailsTabDonotHaveProduct() throws Exception {		
    	login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_DEVICELIST_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            wepDeviceListPage.showTemporaryBannerInUI("Running - verifyPhysicalAssetsDetailsTabDonotHaveProduct()");
            waitForPageLoaded();
            // Clear's the filter if any and reset's the column options to default
            wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("PhysicalAssetsBtn");
            wepDeviceListPage.clickByJavaScriptOnDevicePage("PhysicalAssetsBtn");
            wepDeviceListPage.ClearAndResetDeviceListTable();
            wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberTextInPhysicalAssets");
            waitForPageLoaded();
            WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
            waitForPageLoaded();
            //verify product is not present
            WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("deviceDetailsHeader");
            assertFalse(WEPDeviceDetailsPage.verifyElementIsPresent("overViewTabProductValue"));
            LOGGER.info("Verified Product field is not present in Device Details");
        }
        else{
            LOGGER.info("Physical Asset not available");
        }
	}

    /**
     * This will verify fleet inventory widget count and redirection in customer home page
     */
    @Test(priority = 31, groups = { "REGRESSION_INVENTORYINGESTION" }, description = "TestCase ID :C48135051")
    public final void verifyHomeFleetInventoryWidget() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXCustomerHomePage customerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyHomeFleetInventoryWidget()");
        customerHomePage.scrollToElementsOfWEPCustomerHomePage("fleetInventoryTitle");
        customerHomePage.verifyElementsOfWEPCustomerHomePage("fleetInventoryTitle");
        HashMap<String, Integer> differentManuDeviceCount = customerHomePage.getManufacturerBasedDeviceCount();
        int totalCountAfterDeselectingKeys = Integer.parseInt(customerHomePage.getTextOfWEPCustomerHomePage("valueExtractor").split("PCs")[0]);
        Assert.assertEquals(totalCountAfterDeselectingKeys, 0, "Total Count should be 0 after deselecting all legend keys");
        LOGGER.info("Verified widget count after deselecting all options is having count of 0");
        System.out.println("Different manufacturer pcs with count: " + differentManuDeviceCount);
        Assert.assertTrue(customerHomePage.verifyManufacturerDeviceCountFromDeviceList(differentManuDeviceCount,
                customerHomePage, wepDeviceListPage), "Verification failed for redirection count of first manufacturer in device list page");
        wepDeviceListPage.clickOnElementsOfDevicePage("leftPaneHomePageButton");
        LOGGER.info("Navigated back to home page successfully");
        customerHomePage.scrollToElementsOfWEPCustomerHomePage("fleetInventoryTitle");
        sleeper(3000);
        Integer totalCount = Integer.parseInt(customerHomePage.getTextOfWEPCustomerHomePage("valueExtractor").split("PCs")[0]);
        customerHomePage.clickOnElementsOfWEPCustomerHomePage("firstManufacturerLegendIcon");
        LOGGER.info("Clicked on first manufacturer legend icon to deselect the option");
        customerHomePage.clickOnElementsOfWEPCustomerHomePage("secondManufacturerLegendIcon");
        sleeper(3000);
        LOGGER.info("Clicked on second manufacturer legend icon to deselect the option");
        int countAfterDeselect = Integer.parseInt(customerHomePage.getTextOfWEPCustomerHomePage("valueExtractor").split("PCs")[0]);
        int verifySplitCount = totalCount-differentManuDeviceCount.get(customerHomePage.getTextOfWEPCustomerHomePage("firstManufacturerLegendIcon"))-differentManuDeviceCount.get(customerHomePage.getTextOfWEPCustomerHomePage("secondManufacturerLegendIcon"));
        Assert.assertEquals(verifySplitCount, countAfterDeselect, "Widget count after deselecting first two legend is not matching as per the expectation.");
    }
    /**
     * This will verify inventory widget in dashboardpage.
     */
    @Test(priority = 32, groups = { "REGRESSION_INVENTORYINGESTION" }, description = "TestCase ID :C51872027")
    public final void verifyDashboardInventoryWidget() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyDashboardInventoryWidget()");
        dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        wepDeviceListPage.actionClickOfDeviceListPage("FleetInventoryDashboard");
        waitForPageLoaded();
        List<WebElement> pcStatusInventoriesListElements = dashboardPage.getElementsOfDashboardPage("pcInventoryWidget");
        HashMap<String, Integer> differentPcsStatusCount = dashboardPage.getStatusBasedPCCount(pcStatusInventoriesListElements);
        Assert.assertTrue(dashboardPage.isCountMatching("pcWidgetCenterVal", 0), "Device Final count not matching with the expected");
        String activePcInventoryName = pcStatusInventoriesListElements.get(0).getText();
        dashboardPage.mouseHoverclickOfDashboardPage(pcStatusInventoriesListElements.get(0));
        sleeper(2000);
        WebElement svgElement = dashboardPage.getElementOfDashboardPage( "pcCircleWidget");
        dashboardPage.navigateToDeviceListPagefromWidget(svgElement);
        waitForPageLoaded();
        int numberOfTotalActiveDevices = dashboardPage.getTotalNumbersFromPagination(wepDeviceListPage,"showingPaginationTotalCount");
        int numberOfActiveDeviceFromHashMap = differentPcsStatusCount.get(activePcInventoryName);
        Assert.assertEquals(numberOfTotalActiveDevices, numberOfActiveDeviceFromHashMap, "Total device from pagination and widget extracted count are not matching");
        dashboardPage.navigateToBack();
        waitForPageLoaded();
        pcStatusInventoriesListElements = dashboardPage.getElementsOfDashboardPage("pcInventoryWidget");
        sleeper(2000);
        String inActivePcInventoryName =pcStatusInventoriesListElements.get(1).getText();
        dashboardPage.mouseHoverclickOfDashboardPage(pcStatusInventoriesListElements.get(0));
        int finalInactiveCount = Integer.parseInt(dashboardPage.getTextOfWexDashboardPage("pcWidgetCenterVal"));
        int numberOfInactiveDeviceFromHashMap = differentPcsStatusCount.get(inActivePcInventoryName);
        Assert.assertTrue(numberOfInactiveDeviceFromHashMap ==finalInactiveCount,"Device list page count is not matching the expected widget count for inactive devices");
    }
	
	/**
	 * This will Virtual Machine Column Headers and Pagination
	 * pagination
	 */
	@Test(priority = 33, groups = { "REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :51988478")
	public final void verifyVirtualMachineTabNavigationandColumnHeaders() throws Exception {
		login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIRTUALMACHINES);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyVirtualMachineTabNavigationandColumnHeaders()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
		// Verify Pagination
		verifyPaginationOnListPage();
		LOGGER.info("verifyDeviceList_Pagination() - Passed : Pagination functionality verified successfully");
		// Verify Default Table Columns
		List<String> expectedColumnList = new ArrayList<>(Arrays.asList("Serial Number", "Model", "Status","PC Type","Location"));
		Assert.assertTrue(wepDeviceListPage.verifyTableColumns(expectedColumnList, "virtualMachineTableColumns"),"Table Columns are not as expected");
	}
	
	/**
     * This will verify Virtual Machine Details
     */
    @Test(priority = 34, groups = {"REGRESSION_INVENTORYINGESTION","PRODUCTION_INVENTORYINGESTION"}, description = "TestCase ID :51988479")
    public final void verifyVirtualMachineDetailsPage() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIRTUALMACHINES);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyVirtualMachineDetailsPage()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", getEnvironmentSpecificData(System.getProperty("environment"), "VIRTUAL_MACHINE_SERIAL_NUMBER"));
        sleeper(2000);
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberTextInVirtualMachines");
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        waitForPageLoaded();
        js.executeScript("document.body.style.zoom='67%'");
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("deviceDetailsHeader");
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("overViewTab");
        WEPDeviceDetailsPage.actionClickOfDeviceDetailsPage("overViewTab");
        waitForPageLoaded();
        //assertTrue(WEPDeviceDetailsPage.VerifyTimelineTabContents(), "TimeLine tab sections are not matching the expected header sections");
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("healthAndProtectionTab");
        assertTrue(WEPDeviceDetailsPage.VerifyHardwareTabContentsforVM(), "Hardware tab header sections are not matching the expected header sections");
        assertTrue(WEPDeviceDetailsPage.VerifyNetworkTabContents(), "Network tab header sections are not matching the expected header sections");
        assertTrue(WEPDeviceDetailsPage.VerifySoftwareTabContents(), "Software tab sections are not matching the expected header sections");
        assertTrue(WEPDeviceDetailsPage.VerifyLocationTabContents(), "Location tab sections are not matching the expected header sections");
        LOGGER.info("Verified each tab of device details successfully");
    }

    /**
     * This is to validate the export of the device details page
     */
    @Test(priority = 35, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :42544874")
    public final void verifyExportOfDetailsPage() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        wepDeviceListPage.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyExportOfDetailsPage()");
        waitForPageLoaded();
        String serialNumberToUse = getEnvironmentSpecificData(System.getProperty("environment"), "TEST_DEVICE_SERIAL_NUM");
        ArrayList<String> serialNumbers = new ArrayList<>(List.of(serialNumberToUse));
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        assertTrue(wepDeviceListPage.verifyAddedWEPDevicesOnListPage(serialNumbers), "Devices are not getting reflected back to the list page.");
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberText");
        waitForPageLoaded();
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("deviceDetailsHeader");
        if(WEPDeviceDetailsPage.verifyElementsOfWEPDeviceDetailsPage("actionsButton")) {
			WEPDeviceDetailsPage.clickByJavaScriptOnDevicePage("actionsButton");
			LOGGER.info("Clicked on actions button successfully");
		}
		WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("exportDataButton");
		LOGGER.info("Export button is visible successfully");
		WEPDeviceDetailsPage.actionClickOfDeviceDetailsPage("exportDataButton");
		LOGGER.info("Clicked on export button successfully");
        LOGGER.info("started waiting for export msg");
        sleeper(2000);
        WEPDeviceDetailsPage.clickByJavaScriptOnDevicePage("closeExportToastMsg");
        LOGGER.info("export msg verified");
        String deviceName = WEPDeviceDetailsPage.getTextOfWEPDeviceDetailsPage("deviceNameValue");
        String deviceFileExportName = deviceName + "_"+WEPDeviceDetailsPage.getTextOfWEPDeviceDetailsPage("overViewTabSerialNumberValue")+"__device_details.xls";
        LOGGER.info("deviceFileExportName is- "+ deviceFileExportName);
        Assert.assertTrue(WEPDeviceDetailsPage.isExportSuccess(deviceName),"Device details did not export correctly");
        sleeper(5000);
        Assert.assertTrue(WEPDeviceDetailsPage.verifyExportIsPresent(deviceFileExportName),"CSV file is not present in path");
        LOGGER.info("Verified export of device details successfully");
    }

	/**
     * This will Verify Company global Filter for partner
     */
    @Test(priority = 36, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :42525166")
    public final void VerifyCompanyGlobalFilterForPartner() throws Exception {
    	login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        String companyToSelect = getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_COMPANY_TO_SELECT");
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        wepDeviceListPage.showTemporaryBannerInUI("Running - VerifyCompanyGlobalFilterForPartner()");
        waitForPageLoaded();
        sleeper(5000);
        Assert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("allCustomersGlobalFilter"),"allCustomersGlobalFilter is not displayed");
        selectCompany("All Customers");
        sleeper(5000);
        wepDeviceListPage.waitForElementsOfDeviceListPage("unassignedDevicesOption");
        LOGGER.info("All Customers option is selected from Company global Filter");
        waitForPageLoaded();
        Assert.assertFalse(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("devicesDropdown"),"Devices dropdown is displayed before selecting CompanyGlobalFilterForPartner");
        LOGGER.info("Devices dropdown is not displayed as expected before selecting CompanyGlobalFilterForPartner");
        selectCompany(companyToSelect);
        wepDeviceListPage.waitForElementsOfDeviceListPage("devicesDropdown");
        LOGGER.info(companyToSelect+ "Company is selected successfully from Company global Filter");
        Assert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("devicesDropdown"),"Devices dropdown is not displayed after selecting CompanyGlobalFilterForPartner");
        LOGGER.info("Verified Company global Filter for partner successfully");
    }
    
    /**
     * This will Verify custom fields in settings for Partner 
     */
    @Test(priority = 37, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID: C56976628, C56976629")
    public final void VerifyCusomFieldsInSettingsForPartner() throws Exception {
    	login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        String csvPath = IMPORT_PATH + DEVICEWITHCUSTOMFIELDS_UPLOAD;
        ArrayList<String> serialNumberListToVerifyAddAndDelete = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvPath);
        String companyToSelect = getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_COMPANY_TO_SELECT");
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.partnerWithCompanyView(companyToSelect,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - VerifyCusomFieldsInSettingsForPartner()");
        waitForPageLoaded();
        wepDeviceListPage.clickByActionsWEPClickDevicelistPage("userProfileSettings");
        waitForPageLoaded();
        wepDeviceListPage.clickByActionsWEPClickDevicelistPage("preferancesSettings");
        sleeper(3000);
        wepDeviceListPage.mousehoverOnWEPDeviceListPage("customFieldsSettings");
        wepDeviceListPage.ConfirmAndAddRequiredCustomFields();
        wepDeviceListPage.ConfirmAnddeleteAnyExistingField();
        wepDeviceListPage.addCustomFields(5);
        wepDeviceListPage.deleteCustomFields(2);
        wepDeviceListPage.checkLimitMessage();
        selectCompany("All Customers");
        dashboardPage.partnerWithCompanyView(companyToSelect,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        waitForPageLoaded();
        Assert.assertTrue(wepDeviceListPage.verifyCustomFieldsColumnsInListPage(),"Custom fields columns are not present in list page");
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.deleteDeviceIfExistBeforeAddDevicesFromListPage(serialNumberListToVerifyAddAndDelete);
        wepDeviceListPage.uploadAssetsViaPhysicalAssetsListPage(csvPath);
        assertTrue(wepDeviceListPage.VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToVerifyAddAndDelete, false), "Some Device added are not exist in the Device List page");
        LOGGER.info("Devices are successfully Imported By csv");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", serialNumberListToVerifyAddAndDelete.get(0));
        sleeper(2000);
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberFromList");
		waitForPageLoaded();
        assertTrue(WEPDeviceDetailsPage.verifyCustomFieldsInAssetDetailsPage(), "Overview tab data is not matching the expected data");
        LOGGER.info("Verified custom fields successfully");
    }
    
    /**
     * This is to validate the export of the device details page
     */
    @Test(priority = 38, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :C51258856")
    public final void verifyExportDataOfAssetDetailsPage() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        String csvPath = IMPORT_PATH + ASSET_UPLOAD;
        wepDeviceListPage.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
        ArrayList<String> serialNumberListToVerifyAddAndDelete = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvPath);
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PHYSICALASSETS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyExportDataOfAssetDetailsPage()");
        waitForPageLoaded();
        wepDeviceListPage.importAssetViaCsvAndDisplayNotification(csvPath);
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", serialNumberListToVerifyAddAndDelete.get(0));
        sleeper(2000);
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberFromList");
		waitForPageLoaded();
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("deviceDetailsHeader");
		if(WEPDeviceDetailsPage.verifyElementsOfWEPDeviceDetailsPage("actionsButton")) {
			WEPDeviceDetailsPage.clickByJavaScriptOnDevicePage("actionsButton");
			LOGGER.info("Clicked on actions button successfully");
		}
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("exportDataButton_asset");
        WEPDeviceDetailsPage.actionClickOfDeviceDetailsPage("exportDataButton_asset");
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("exportToast");
        LOGGER.info("started waiting for export msg");
        sleeper(2000);
        Assert.assertTrue(WEPDeviceDetailsPage.verifyElementsOfWEPDeviceDetailsPage("exportToast"),"The export did not get triggered successfully");
        WEPDeviceDetailsPage.clickByJavaScriptOnDevicePage("closeExportToastMsg");
        LOGGER.info("export msg verified");
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("assetNameValue");
        String assetName = WEPDeviceDetailsPage.getTextOfWEPDeviceDetailsPage("assetNameValue");
        String deviceFileExportName = assetName + "_"+WEPDeviceDetailsPage.getTextOfWEPDeviceDetailsPage("overViewTabSerialNumberValue")+"__device_details.xls";
        LOGGER.info("deviceName is- "+ assetName);
        LOGGER.info("deviceFileExportName is- "+ deviceFileExportName);
        if(WEPDeviceDetailsPage.isExportSuccess(assetName)) {
        	sleeper(5000);
        	LOGGER.info("Export is completed successfully");
        	WEPDeviceDetailsPage.actionClickOfDeviceDetailsPage("notificationBellIcon");
            WEPDeviceDetailsPage.verifyExportedCSVOfAssetDetailsPage(deviceFileExportName);
            LOGGER.info("Verified export of device details successfully");
        }else {
        	LOGGER.info("Export file didn't appear within 60 seconds of waiting, so finishing the test case.");
        }        
    }
    
    /**
     * This is to validate the Physical Assets Delete, Export and Advance Filter functionality
     */
    @Test(priority = 39, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :C51258888, C42561195")
    public final void verifyExportDeleteAndFilterInAssetListPage() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        String csvPath = IMPORT_PATH + ASSETSFORVALIDATIONS_UPLOAD;
        wepDeviceListPage.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
        ArrayList<String> serialNumberListToVerifyAddAndDelete = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvPath);
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PHYSICALASSETS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyExportDeleteAndFilterInAssetListPage()");
        waitForPageLoaded();
        sleeper(3000);
        wepDeviceListPage.importAssetViaCsvAndDisplayNotification(csvPath);
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.createAndVerifyAdvFilter(serialNumberListToVerifyAddAndDelete);
        wepDeviceListPage.verifyDeleteDeviceFromThreeDots();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", "PHYASSETAUTO");
        sleeper(2000);
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberFromList");
		waitForPageLoaded();
        WEPDeviceDetailsPage.verifyDeleteDeviceFromActionsDD();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", "PHYASSETAUTO");
        sleeper(2000);
        wepDeviceListPage.deleteAllAddedWEPDevicesFromListPage(serialNumberListToVerifyAddAndDelete);
        Assert.assertFalse(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("oldDeviceNav"),"Old navigation menu is appeared in list page in new navigation");
    }
    
	/**
     * This will Verify left side menu animation
     */
    @Test(priority = 40, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :C58454606")
    public final void VerifyLeftSideMenuAnimation() throws Exception {
    	login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - VerifyLeftSideMenuAnimation()");
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
    	wepDeviceListPage.verifyNewNavMenuDisplayed();
        LOGGER.info("Left side panel animation is verified successfully");
    }
    
    /**
     * This will Verify Lifecycle status in device list & details page
     */
    @Test(priority = 41, groups = {"REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID: C58454638")
    public final void VerifyLifecycleStatusInListAndDetailsPage() throws Exception {
    	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        String serialNumber  = getEnvironmentSpecificData(System.getProperty("environment"), "TEST_DEVICE_SERIAL_NUM");
        wepDeviceListPage.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - VerifyLifecycleStatusInListAndDetailsPage()");
        waitForPageLoaded();
        wepDeviceListPage.waitForElementsOfDeviceListPage("clearfilter");
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        waitForPageLoaded();
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", serialNumber);
        waitForPageLoaded();
        wepDeviceListPage.clickByJavaScriptOnDevicePage("firstSerialNumberFromList");
		waitForPageLoaded();
		String lifecycleStatusValue = WEPDeviceDetailsPage.verifyLifecycleStatusInDetailsPage();
		dashboardPage.navigateToBack();
		js.executeScript("document.body.style.zoom='50%'");
        waitForPageLoaded();
        ArrayList<String> listToSelectColumns = new ArrayList<>(List.of("HP Wolf Protect and Trace", "HP Protect & Trace with Wolf Connect", "Lifecycle Status"));
        wepDeviceListPage.verifyDeviceColumnDataInListPage("Lifecycle Status",lifecycleStatusValue, serialNumber, listToSelectColumns);
        LOGGER.info("Verified Lifecycle status in device list & details page successfully");
        wepDeviceListPage.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
        String csvFilePath = IMPORT_PATH + ASSETSFORLSVALIDATIONS_UPLOAD;
        ArrayList<String> serialNumberListToUse = wepDeviceListPage.ExtractSerialNumberFromCSVFileToUse(csvFilePath);
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        wepDeviceListPage.deleteDeviceIfExistBeforeAddDevicesFromListPage(serialNumberListToUse);
        js.executeScript("document.body.style.zoom='50%'");
        wepDeviceListPage.uploadAssetsViaPhysicalAssetsListPage(csvFilePath);
        listToSelectColumns.remove("Lifecycle Status");
        wepDeviceListPage.verifyDeviceColumnDataInListPage("Lifecycle Status","Delivered", serialNumberListToUse.get(0), listToSelectColumns);
        LOGGER.info("Verified Lifecycle status in device list page & imported device successfully");
    }
    
    /**
     * This will verify the VM Device List page has all the expected properties and is loading fine
     */
    @Test(priority = 42, groups = {"REGRESSION_INVENTORYINGESTION","PRODUCTION_INVENTORYINGESTION"}, description = "TestCase ID :42525167")
    public final void verifyVMDeviceListPage() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIRTUALMACHINES);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyVMDeviceListPage()");
        waitForPageLoaded();
        LOGGER.info("Redirected into customer device list page successfully");
        List<String> expectedColumnList = new ArrayList<>(Arrays.asList(
                getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.serial_number"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_detail.accessories_column.model"),
                getTextLanguage(LanguageCode, "daas_ui", "unassigned_devices.list.status"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_modal_pctype"),
                getTextLanguage(LanguageCode, "daas_ui", "assets.details.general_informations.location"),
                getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.service_coverage"),
                getTextLanguage(LanguageCode, "daas_ui", "companies.details.lockanderase.model.title")
                ));
        js.executeScript("document.body.style.zoom='50%'");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("columnOptionBtn");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("columnOptionBtn");
        sleeper(2000);
        wepDeviceListPage.verifyElementIsClickableOfDeviceListPage("resetColumnFilter");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("resetColumnFilter");
        sleeper(2000);
        wepDeviceListPage.verifyElementIsClickableOfDeviceListPage("saveColumnFilter");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("saveColumnFilter");
        waitForPageLoaded();
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("addDeviceBtn");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("clearfilter");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("columnOptionBtn");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("showingPagination");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("showingPaginationTotalCount");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("navigationItemNext");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("navigationItemPrevious");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("navigationItemActivePage");
        Assert.assertTrue(wepDeviceListPage.verifyTableColumns(expectedColumnList,"deviceListPageTableColumns"), "Table Columns are not as expected");
        LOGGER.info("Fleet Management Devices Page verified and all the available controls successfully");
    }
    
    /**
     * This will verify the Physical Assets Device List page has all the expected properties and is loading fine
     */
    @Test(priority = 43, groups = {"REGRESSION_INVENTORYINGESTION","PRODUCTION_INVENTORYINGESTION"}, description = "TestCase ID :42525167")
    public final void verifyPhysicalAssetsDeviceListPage() throws Exception {
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PHYSICALASSETS);
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyPhysicalAssetsDeviceListPage()");
        waitForPageLoaded();
        LOGGER.info("Redirected into customer device list page successfully");
        List<String> expectedColumnList = new ArrayList<>(Arrays.asList(
                getTextLanguage(LanguageCode, "daas_ui", "groups.property.option.serial_number"),
                getTextLanguage(LanguageCode, "daas_ui", "unassigned_device.list.asset_tag"),
                getTextLanguage(LanguageCode, "daas_ui", "asset.inventory.type"),
                getTextLanguage(LanguageCode, "daas_ui", "asset.inventory.name"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_detail.accessories_column.manufacturer"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_detail.accessories_column.model"),
                getTextLanguage(LanguageCode, "daas_ui", "asset_modal_location"),
                getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.lifeCycleStatus.label")
                ));
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("columnOptionBtn");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("columnOptionBtn");
        sleeper(2000);
        wepDeviceListPage.verifyElementIsClickableOfDeviceListPage("resetColumnFilter");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("resetColumnFilter");
        sleeper(2000);
        wepDeviceListPage.verifyElementIsClickableOfDeviceListPage("saveColumnFilter");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("saveColumnFilter");
        waitForPageLoaded();
        js.executeScript("document.body.style.zoom='67%'");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("addDeviceBtn");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("clearfilter");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("columnOptionBtn");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("showingPagination");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("showingPaginationTotalCount");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("navigationItemNext");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("navigationItemPrevious");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("navigationItemActivePage");
        Assert.assertTrue(wepDeviceListPage.verifyTableColumns(expectedColumnList,"physicalAssetsTableColumns"), "Table Columns are not as expected");
        LOGGER.info("Fleet Management Devices Page verified and all the available controls successfully");
    }
    
    /**
     * This will verify redirection to PC and VM list page from fleet inventory widget in home page
     */
    @Test(priority = 44, groups = { "REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :C48135051")
    public final void verifyRedirectToPCFromHomeFleetInventoryWidget() throws Exception {
        login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXCustomerHomePage customerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
        JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyRedirectToPCFromHomeFleetInventoryWidget()");
        customerHomePage.verifyElementsOfWEPCustomerHomePage("fleetInventoryTitle");
        customerHomePage.scrollToElementsOfWEPCustomerHomePage("fleetInventoryTitle");
        wepDeviceListPage.waitForElementsOfWEPDeviceListPage("clickViewlistOfPCs");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("clickViewlistOfPCs");
        wepDeviceListPage.clickOnElementsOfDevicePage("clickViewlistOfPCs");
        wepDeviceListPage.waitForElementsOfWEPDeviceListPage("deviceListPageType");
        Assert.assertTrue(wepDeviceListPage.verifyTextPresentOnElementOfDeviceListPage("deviceListPageType", "PCs"), "Home fleet inventory widget is not redirected to PCs list page");
        LOGGER.info("Home fleet inventory widget is successfully redirected to PCs list page");
        js.executeScript("document.body.style.zoom='67%'");
        wepDeviceListPage.waitForElementsOfWEPDeviceListPage("clickHome");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("clickHome");
        wepDeviceListPage.clickByJavaScriptOnDevicePage("clickHome");
        customerHomePage.verifyElementsOfWEPCustomerHomePage("fleetInventoryTitle");
        customerHomePage.scrollToElementsOfWEPCustomerHomePage("fleetInventoryTitle");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("virtualMachineWidget");
        wepDeviceListPage.clickOnElementsOfDevicePage("virtualMachineWidget");
        wepDeviceListPage.verifyElementsOfWEPDeviceListPage("clickViewlistOfPCs");
        wepDeviceListPage.clickOnElementsOfDevicePage("clickViewlistOfPCs");
        wepDeviceListPage.waitForElementsOfWEPDeviceListPage("deviceListPageType");
        Assert.assertTrue(wepDeviceListPage.verifyTextPresentOnElementOfDeviceListPage("deviceListPageType", "Virtual Machines"), "Home fleet inventory widget is not redirected to PCs list page");
        LOGGER.info("Home fleet inventory widget is successfully redirected to Virtual Machines list page");
    }
    
    /**
     * This will verify redirection to pc list page from PC inventory widget in dashboard page
     */
    @Test(priority = 45, groups = { "REGRESSION_INVENTORYINGESTION"}, description = "TestCase ID :C51872027")
    public final void verifyRedirectToPCFromDashboardInventoryWidget() throws Exception {
    	 login("ITADMIN_DEVICELIST_EMAIL_WEP", "ITADMIN_DEVICELIST_PASSWORD_WEP");
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyRedirectToPCFromDashboardInventoryWidget()");
        dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
        wepDeviceListPage.actionClickOfDeviceListPage("FleetInventoryDashboard");
        waitForPageLoaded();
        dashboardPage.waitForElementsOfDashboardPage("pcInventoryWidget");
        List<WebElement> pcStatusInventoriesListElements = dashboardPage.getElementsOfDashboardPage("pcInventoryWidget");
        dashboardPage.mouseHoverclickOfDashboardPage(pcStatusInventoriesListElements.get(1));
        sleeper(2000);
        WebElement svgElement = dashboardPage.getElementOfDashboardPage("pcCircleWidget");
        dashboardPage.navigateToDeviceListPagefromWidget(svgElement);
        wepDeviceListPage.waitForElementsOfWEPDeviceListPage("deviceListPageType");
        Assert.assertTrue(wepDeviceListPage.verifyTextPresentOnElementOfDeviceListPage("deviceListPageType", "PCs"), "Home fleet inventory widget is not redirected to PCs list page");
        LOGGER.info("Home fleet inventory widget is successfully redirected to PCs list page");
    }
    @Test(priority = 46, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"})
    public final void verifyVirtualMachineDetailsPageLdk() throws Exception {
    	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
    	 String testSuiteName = SetTestEnvironments.suiteName;
 		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
 			switchUserBasedOnSuite(testSuiteName);
 		}
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        SoftAssert softAssert = new SoftAssert();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        JavascriptExecutor js = (JavascriptExecutor) PreDefinedActions.getDriver();
        if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn")) {
    		LOGGER.info("Left side menu is not collapsed as expected");
    	}else if(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn")) {
    		wepDeviceListPage.actionClickOfDeviceListPage("leftSideMenuExpandBtn");
        	LOGGER.info("Left side menu is expanded successfully");
    	}
        if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_DEVICELIST_EMAIL_WEP"))) {
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
            wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("linkVirtualMachine");
            wepDeviceListPage.clickByJavaScriptOnDevicePage("linkVirtualMachine");
        }
        else{
            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_VIRTUALMACHINES);
        }
        wepDeviceListPage.showTemporaryBannerInUI("Running - verifyVirtualMachineDetailsPage()");
        waitForPageLoaded();
        wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
        String serilNumber= getEnvironmentSpecificData(System.getProperty("environment"), "VIRTUAL_MACHINE_SERIAL_NUMBER");
        wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", getEnvironmentSpecificData(System.getProperty("environment"), "VIRTUAL_MACHINE_SERIAL_NUMBER"));
        waitForPageLoaded();
        wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberTextInVirtualMachines");
        WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        waitForPageLoaded();
        js.executeScript("document.body.style.zoom='67%'");
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("deviceDetailsHeader");
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("overViewTab");
        WEPDeviceDetailsPage.actionClickOfDeviceDetailsPage("overViewTab");
        softAssert.assertTrue(WEPDeviceDetailsPage.VerifyOverviewTabsContents(serilNumber), "overview tab sections are not matching the expected header sections");
        waitForPageLoaded();
        WEPDeviceDetailsPage.waitForElementOfWEPDeviceDetailsPage("healthAndProtectionTab");
        softAssert.assertTrue(WEPDeviceDetailsPage.VerifyHardwareTabContentsforVM(), "Hardware tab header sections are not matching the expected header sections");
        softAssert.assertTrue(WEPDeviceDetailsPage.VerifyLocationTabContents(), "Location tab sections are not matching the expected header sections");
        softAssert.assertTrue(WEPDeviceDetailsPage.VerifySoftwareTabContents(), "Software tab sections are not matching the expected header sections");        
        LOGGER.info("Verified each tab of device details successfully");
        softAssert.assertAll();
    }
    
    @Test(priority = 47, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"})
   	public final void verifyPhysicalAssetsDetailsTabsonLDK() throws Exception {
   		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   	 String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
           WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
           WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
           WEPDeviceDetailsPage WEPDeviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
           if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
               dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_DEVICES_PHYSICALASSETS);
           }
           else{
               dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PHYSICALASSETS);
           }
           
           wepDeviceListPage.showTemporaryBannerInUI("Running - verifyPhysicalAssetsDetailsTabs()");
           waitForPageLoaded();
           String serialNumberToUse = CommonVariables.TEST_PhysicalAsset_SERIAL_NUMLDK;
           wepDeviceListPage.clearFiltersOfDevicesListPage("clearfilter");
           wepDeviceListPage.sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", serialNumberToUse);
           wepDeviceListPage.actionClickOfDeviceListPage("firstSerialNumberTextInPhysicalAssets");
           waitForPageLoaded();
           assertTrue(WEPDeviceDetailsPage.verifyPhysicalAssetsOverviewTabContents(serialNumberToUse), "Overview tab data is not matching the expected data");
           LOGGER.info("Verified each tab of device details successfully");
   	    } 
    
   }
