package com.testscripts.daasui;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.LaunchDarkly;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.WEPPGroupsPageVariables;
import com.daasui.pages.TableConfigurationPage;
import com.daasui.pages.WEPDeviceListPage;
import com.daasui.pages.WEPGroupsPage;
import com.daasui.pages.WEPSustainabilityPage;


public class WEPGroupsTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEPGroupsTest.class);

	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "PARTNER_EMAIL_WEP";
		data[0][1] = "PARTNER_PASSWORD_WEP";
		data[1][0] = "ITADMIN_EMAIL_GROUP";
		data[1][1] = "ITADMIN_PASSWORD_WEP";
		return data;
	}

	/**
	 * WEPINT-52 >>Add Service check from backend E.g. PI, pro plan and Verify the Groups option should be visible on Fleet Management Analytics tab
	 */
	@Test(priority = 1, groups = { "REGRESSION_GROUPS","PRODUCTION_GROUPS"}, description="Testcase ID: C43408570")
	public final void verifyWEPGroupsListPageBreadCrumbsForCustomerView() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
			groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups SideMenu is Matched");

		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"GroupsMenu Breadcrumb doesn't exists");
		LOGGER.info("Groups  Breadcrumb is Matched");
	}



	/**
	 * WEPINT-743 >>https://hp-jira.external.hp.com/browse/WEXINT-743
	 * 	C53527001	[WEP] Verify RBAC Role and Permission on WEP Groups
	 */
	@Test(priority = 2, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: C43408570" ,enabled=false)
	public final void verifyWEPGroupsListPageBreadCrumbsForPartnerView() throws Exception {	
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		//leftSideMenuVerification();
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.partnerWithCompanyView(CommonVariables.CUSTOMER_NAME,CommonVariables.CUSTOMER_FLEET_MANAGEMENT);
		waitForPageLoaded();
			groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		sleeper(2000);	

	}


	/**
	 * https://hp-jira.external.hp.com/browse/WEXINT-425,634
	 * WEPINT-634:[Backend] Align operator for properties in Dynamic Rules.
	 * WEPINT-425:[Grouping][UI][Add Groups] Dynamic Groups API implementation.
	 * TC_C51872293:[WEP] Verify Create Dynamic Group by Manufacturer from UI Implementation
	 */	

	@Test(priority = 3, groups = { "REGRESSION_GROUPS"}, description="Testcase ID: TC_C51872293")
	public final void verifyCreateDynamicGroupManufacturerFilter() throws Exception {
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String dynamicGroupType = getTextLanguage(LanguageCode, "daas_ui", "group.dynamic_rules");
		String DG_GroupName = "Auto_DynamicGroup"+generateRandomNumber();
		String DG_GroupDescription = "Group is created by automation";
		String propertyInputValue = WEPPGroupsPageVariables.GROUPS_FILTERS_FIELD_MANUFACTURER;
		String operatorValue = WEPPGroupsPageVariables.GROUPS_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"Groups_FILTERS_MANUFACTURER_VALUE");
		
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
			DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
			Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active devices filters applied successfully");
			sleeper(2000);
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"ManufacturerColumnTitle","manufacturerdropdown","manufacturerListValues","countPage");	
			groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
		groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
		waitForPageLoaded();
		Assert.assertTrue(groupsPage.verifyAddGroupsStep1GroupSelectionFlow(dynamicGroupType,LanguageCode,DG_GroupName,DG_GroupDescription),"Add Groups Step1 is getting failed");
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		sleeper(2000);
		List<String> verifyStep2DynamicCreationFlow = groupsPage.verifyStep2DynamicCreationFlow(LanguageCode, propertyInputValue, operatorValue, valueName);
		sleeper(2000);
		Integer AudienceCount = groupsPage.handleCalculateButton();
		Assert.assertEquals(TotalAudience, AudienceCount,"Devices count is not getting matched");
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		List<String> verifyStep3DynamicCreationFlow = groupsPage.verifyStep3DynamicCreationFlow(LanguageCode,DG_GroupName,DG_GroupDescription,0);
		Assert.assertEquals(verifyStep2DynamicCreationFlow, verifyStep3DynamicCreationFlow, "Selected Filters/Values are not matching");
		LOGGER.info("Dynamic group >>Device Count is matched between Add group Review apge and Calculate button device count");
		groupsPage.actionClickOfGroupsPage("addGrpBtn");
		waitForPageLoaded();
		groupsPage.waitForElementsOfGroupsPage("toastNotificationExport");
		groupsPage.getTextOfWEPGroupsPage("toastNotificationExport");
		LOGGER.info("Toaster Message for Group creation:"+groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
	//	Assert.assertEquals(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"),groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.dynamic.created.toast.title"),"Dynamic Group Creation is failed");
		LOGGER.info("Dyanmic Group creation is done successfully");
		Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("viewGroups"));
		sleeper(4000);
		groupsPage.enterTextForGroupsPage("groupNameSearch",DG_GroupName);
		sleeper(3000);		
		waitForPageLoaded();
		LOGGER.info("Dynamic group creation is done successfully and its showing on Group List page");	
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
//		sleeper(3000);
		//String overviewTabDeviceCount = groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameHeaderTitle").equals(DG_GroupName),"Device Count is not matched");		
		LOGGER.info("Dynamic group Name title is verified");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupDescription").equals(DG_GroupDescription),"Device Count is not matched");		
		LOGGER.info("Dynamic group description is verified");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameTxt").equals(DG_GroupName),"Device Count is not matched");		
		LOGGER.info("Group Name is verified on overview tab");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("dynamicGroupMemershipType").toUpperCase().contains(getTextLanguage(LanguageCode, "daas_ui", "device.groups.dynamic.label").toUpperCase()),"Groups membershiptype doesn't exists");		
		LOGGER.info("Group membershiptype is verified on overview tab");
//		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount").equals(deviceCount),"Device Count is not matched");		
//		LOGGER.info("Dynamic group >>Device Count is matched between Add group Review apge and Overview page");
	}

	/**
	 * https://hp-jira.external.hp.com/browse/WEXINT-425,634
	 * WEPINT-634:[Backend] Align operator for properties in Dynamic Rules.
	 * WEPINT-425:[Grouping][UI][Add Groups] Dynamic Groups API implementation.
	 * TC_C51872293:[WEP] Verify Create Dynamic Group by Serial-number from UI Implementation
	 */	

	@Test(priority = 4, groups = { "REGRESSION_GROUPS","PRODUCTION_GROUPS","REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description="Testcase ID: TC_C51872293")
	public final void verifyDynamicGroupsCreationSerialNumberFilter() throws Exception {
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String dynamicGroupType = getTextLanguage(LanguageCode, "daas_ui", "group.dynamic_rules");
		String DG_GroupName = "Auto_DynamicGroup"+generateRandomNumber();
		String DG_GroupDescription = "Group is created by automation";
		String propertyInputValue = WEPPGroupsPageVariables.GROUPS_FILTERS_FIELD_SERIAL_NUMBER;
		String operatorValue = WEPPGroupsPageVariables.GROUPS_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"Groups_FILTERS_SERIAL_NUMBER_VALUE");
		
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
			DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
			Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active devices filters applied successfully");
			sleeper(2000);
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"SerialNumberColumnTitle","SerialNumberColumnListValueSearch",valueName,"countPage");
			groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
		groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
		waitForPageLoaded();
		Assert.assertTrue(groupsPage.verifyAddGroupsStep1GroupSelectionFlow(dynamicGroupType,LanguageCode,DG_GroupName,DG_GroupDescription),"Add Groups Step1 is getting failed");
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		sleeper(2000);
		List<String> verifyStep2DynamicCreationFlow = groupsPage.verifyStep2DynamicCreationFlow(LanguageCode, propertyInputValue, operatorValue, valueName);
		sleeper(2000);
		Integer AudienceCount = groupsPage.handleCalculateButton();
		Assert.assertEquals(TotalAudience, AudienceCount,"Devices count is not getting matched");
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		List<String> verifyStep3DynamicCreationFlow = groupsPage.verifyStep3DynamicCreationFlow(LanguageCode,DG_GroupName,DG_GroupDescription,0);
		Assert.assertEquals(verifyStep2DynamicCreationFlow, verifyStep3DynamicCreationFlow, "Selected Filters/Values are not matching");
		LOGGER.info("Dynamic group >>Device Count is matched between Add group Review apge and Calculate button device count");
		groupsPage.actionClickOfGroupsPage("addGrpBtn");
		waitForPageLoaded();
		groupsPage.waitForElementsOfGroupsPage("toastNotificationExport");
		groupsPage.getTextOfWEPGroupsPage("toastNotificationExport");
		LOGGER.info("Toaster Message for Group creation:"+groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
//		Assert.assertEquals(
//				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"),groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.dynamic.created.toast.title"),"Dynamic Group Creation is failed");
		LOGGER.info("Dyanmic Group creation is done successfully");
		Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("viewGroups"));
		sleeper(4000);
		groupsPage.enterTextForGroupsPage("groupNameSearch",DG_GroupName);
		sleeper(4000);
		refreshPage();
		waitForPageLoaded();
		groupsPage.verifyElementIsPresentOnGroupsPage("groupNameCheck");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameCheck").equals(DG_GroupName),"Failed to create Dynamic Group");		
		sleeper(2000);		
		waitForPageLoaded();
		LOGGER.info("Dynamic group creation is done successfully and its showing on Group List page");	
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
//		sleeper(3000);
		//String overviewTabDeviceCount = groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameHeaderTitle").equals(DG_GroupName),"Device Count is not matched");		
		LOGGER.info("Dynamic group Name title is verified");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupDescription").equals(DG_GroupDescription),"Device Count is not matched");		
		LOGGER.info("Dynamic group description is verified");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameTxt").equals(DG_GroupName),"Device Count is not matched");		
		LOGGER.info("Group Name is verified on overview tab");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("dynamicGroupMemershipType").toUpperCase().contains(getTextLanguage(LanguageCode, "daas_ui", "device.groups.dynamic.label").toUpperCase()),"Groups membershiptype doesn't exists");		
		LOGGER.info("Group membershiptype is verified on overview tab");
//		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount").equals(deviceCount),"Device Count is not matched");		
//		LOGGER.info("Dynamic group >>Device Count is matched between Add group Review apge and Overview page");
		groupsPage.actionClickOfGroupsPage("listRedirection");
		sleeper(2000);		
		waitForPageLoaded();
		Assert.assertTrue(groupsPage.deleteGroups(DG_GroupName));
	}
		
	/**
	 * https://hp-jira.external.hp.com/browse/WEXINT-425,634
	 * WEPINT-634:[Backend] Align operator for properties in Dynamic Rules.
	 * WEPINT-425:[Grouping][UI][Add Groups] Dynamic Groups API implementation.
	 * TC_C51872293:[WEP] Verify Create Dynamic Group by Device Name from UI Implementation
	 */	

	@Test(priority = 26, groups = { "REGRESSION_GROUPS"}, description="Testcase ID: TC_C51872293")
	public final void verifyDynamicGroupsCreationDeviceNameFilter() throws Exception {
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String dynamicGroupType = getTextLanguage(LanguageCode, "daas_ui", "group.dynamic_rules");
		String DG_GroupName = "Auto_DynamicGroup"+generateRandomNumber();
		String DG_GroupDescription = "Group is created by automation";
		String propertyInputValue = WEPPGroupsPageVariables.GROUPS_FILTERS_FIELD_DEVICE_NAME;
		String operatorValue = WEPPGroupsPageVariables.GROUPS_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"Groups_FILTERS_DEVICE_NAME_VALUE");
		
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
			DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
			Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active devices filters applied successfully");
			sleeper(2000);
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"deviceNameColumnTitle","deviceNameColumnListValueSearch",valueName,"countPage");
			groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
		groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
		waitForPageLoaded();
		Assert.assertTrue(groupsPage.verifyAddGroupsStep1GroupSelectionFlow(dynamicGroupType,LanguageCode,DG_GroupName,DG_GroupDescription),"Add Groups Step1 is getting failed");
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		sleeper(2000);
		List<String> verifyStep2DynamicCreationFlow = groupsPage.verifyStep2DynamicCreationFlow(LanguageCode, propertyInputValue, operatorValue, valueName);
		sleeper(2000);
		Integer AudienceCount = groupsPage.handleCalculateButton();
		Assert.assertEquals(TotalAudience, AudienceCount,"Devices count is not getting matched");
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		List<String> verifyStep3DynamicCreationFlow = groupsPage.verifyStep3DynamicCreationFlow(LanguageCode,DG_GroupName,DG_GroupDescription,0);
		Assert.assertEquals(verifyStep2DynamicCreationFlow, verifyStep3DynamicCreationFlow, "Selected Filters/Values are not matching");
		LOGGER.info("Dynamic group >>Device Count is matched between Add group Review apge and Calculate button device count");
		groupsPage.actionClickOfGroupsPage("addGrpBtn");
		waitForPageLoaded();
		groupsPage.waitForElementsOfGroupsPage("toastNotificationExport");
		groupsPage.getTextOfWEPGroupsPage("toastNotificationExport");
		LOGGER.info("Toaster Message for Group creation:"+groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
//		Assert.assertEquals(
//				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"),groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.dynamic.created.toast.title"),"Dynamic Group Creation is failed");
		LOGGER.info("Dyanmic Group creation is done successfully");
		Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("viewGroups"));
		sleeper(4000);
		groupsPage.enterTextForGroupsPage("groupNameSearch",DG_GroupName);
		sleeper(3000);		
		waitForPageLoaded();
		groupsPage.verifyElementIsPresentOnGroupsPage("groupNameCheck");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameCheck").equals(DG_GroupName),"Failed to create Dynamic Group");		
		sleeper(2000);		
		waitForPageLoaded();
		LOGGER.info("Dynamic group creation is done successfully and its showing on Group List page");	
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
//		sleeper(3000);
		//String overviewTabDeviceCount = groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameHeaderTitle").equals(DG_GroupName),"Device Count is not matched");		
		LOGGER.info("Dynamic group Name title is verified");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupDescription").equals(DG_GroupDescription),"Device Count is not matched");		
		LOGGER.info("Dynamic group description is verified");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameTxt").equals(DG_GroupName),"Device Count is not matched");		
		LOGGER.info("Group Name is verified on overview tab");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("dynamicGroupMemershipType").toUpperCase().contains(getTextLanguage(LanguageCode, "daas_ui", "device.groups.dynamic.label").toUpperCase()),"Groups membershiptype doesn't exists");		
		LOGGER.info("Group membershiptype is verified on overview tab");
//		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount").equals(deviceCount),"Device Count is not matched");		
//		LOGGER.info("Dynamic group >>Device Count is matched between Add group Review apge and Overview page");
		groupsPage.actionClickOfGroupsPage("listRedirection");
		sleeper(2000);		
		waitForPageLoaded();
		Assert.assertTrue(groupsPage.deleteGroups(DG_GroupName));
	}

	/**
	 * https://hp-jira.external.hp.com/browse/WEXINT-425,634
	 * WEPINT-634:[Backend] Align operator for properties in Dynamic Rules.
	 * WEPINT-425:[Grouping][UI][Add Groups] Dynamic Groups API implementation.
	 * TC_C51872293:[WEP] Verify Create Dynamic Group by Device Model from UI Implementation
	 */	

	@Test(priority = 6, groups = { "REGRESSION_GROUPS"}, description="Testcase ID: TC_C51872293,TC_C58768954")
	public final void verifyDynamicGroupsCreationDeviceModelFilter() throws Exception {
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String dynamicGroupType = getTextLanguage(LanguageCode, "daas_ui", "group.dynamic_rules");
		String DG_GroupName = "Auto_DynamicGroup"+generateRandomNumber();
		String DG_GroupDescription = "Group is created by automation";
		String propertyInputValue = WEPPGroupsPageVariables.GROUPS_FILTERS_FIELD_DEVICE_MODEL;
		String operatorValue = WEPPGroupsPageVariables.GROUPS_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"Groups_FILTERS_DEVICE_MODEL_VALUE");
		
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
			DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
			Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active devices filters applied successfully");
			sleeper(2000);
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"deviceModelColumnTitle","deviceModelColumnListValueSearch",valueName,"countPage");
			groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
		
		groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
		waitForPageLoaded();
		Assert.assertTrue(groupsPage.verifyAddGroupsStep1GroupSelectionFlow(dynamicGroupType,LanguageCode,DG_GroupName,DG_GroupDescription),"Add Groups Step1 is getting failed");
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		sleeper(2000);
		List<String> verifyStep2DynamicCreationFlow = groupsPage.verifyStep2DynamicCreationFlow(LanguageCode, propertyInputValue, operatorValue, valueName);
		sleeper(2000);
		Integer AudienceCount = groupsPage.handleCalculateButton();
		Assert.assertEquals(TotalAudience, AudienceCount,"Devices count is not getting matched");
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		List<String> verifyStep3DynamicCreationFlow = groupsPage.verifyStep3DynamicCreationFlow(LanguageCode,DG_GroupName,DG_GroupDescription,0);
		Assert.assertEquals(verifyStep2DynamicCreationFlow, verifyStep3DynamicCreationFlow, "Selected Filters/Values are not matching");
		LOGGER.info("Dynamic group >>Device Count is matched between Add group Review apge and Calculate button device count");
		groupsPage.actionClickOfGroupsPage("addGrpBtn");
		waitForPageLoaded();
		groupsPage.waitForElementsOfGroupsPage("toastNotificationExport");
		groupsPage.getTextOfWEPGroupsPage("toastNotificationExport");
		LOGGER.info("Toaster Message for Group creation:"+groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
//		Assert.assertEquals(
//				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"),groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.dynamic.created.toast.title"),"Dynamic Group Creation is failed");
		LOGGER.info("Dyanmic Group creation is done successfully");
		Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("viewGroups"));
		sleeper(4000);
		groupsPage.enterTextForGroupsPage("groupNameSearch",DG_GroupName);
		sleeper(3000);		
		waitForPageLoaded();;
		groupsPage.verifyElementIsPresentOnGroupsPage("groupNameCheck");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameCheck").equals(DG_GroupName),"Failed to create Dynamic Group");		
		sleeper(2000);		
		waitForPageLoaded();
		LOGGER.info("Dynamic group creation is done successfully and its showing on Group List page");	
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
//		sleeper(3000);
		//String overviewTabDeviceCount = groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameHeaderTitle").equals(DG_GroupName),"Device Count is not matched");		
		LOGGER.info("Dynamic group Name title is verified");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupDescription").equals(DG_GroupDescription),"Device Count is not matched");		
		LOGGER.info("Dynamic group description is verified");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameTxt").equals(DG_GroupName),"Device Count is not matched");		
		LOGGER.info("Group Name is verified on overview tab");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("dynamicGroupMemershipType").toUpperCase().contains(getTextLanguage(LanguageCode, "daas_ui", "device.groups.dynamic.label").toUpperCase()),"Groups membershiptype doesn't exists");		
		LOGGER.info("Group membershiptype is verified on overview tab");
//		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount").equals(deviceCount),"Device Count is not matched");		
//		LOGGER.info("Dynamic group >>Device Count is matched between Add group Review apge and Overview page");
		groupsPage.actionClickOfGroupsPage("listRedirection");
		sleeper(2000);		
		waitForPageLoaded();
		Assert.assertTrue(groupsPage.deleteGroups(DG_GroupName));
	}

	/**
	 * https://hp-jira.external.hp.com/browse/WEXINT-425,634
	 * WEPINT-634:[Backend] Align operator for properties in Dynamic Rules.
	 * WEPINT-425:[Grouping][UI][Add Groups] Dynamic Groups API implementation.
	 * TC_C51872293:[WEP] Verify Create Dynamic Group by Operating System from UI Implementation
	 */	

	@Test(priority = 7, groups = { "REGRESSION_GROUPS"}, description="Testcase ID: TC_C59809089")
	public final void verifyDynamicGroupsCreationOperatingSystemFilter() throws Exception {
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String dynamicGroupType = getTextLanguage(LanguageCode, "daas_ui", "group.dynamic_rules");
		String DG_GroupName = "Auto_DynamicGroup"+generateRandomNumber();
		String DG_GroupDescription = "Group is created by automation";
		String propertyInputValue = WEPPGroupsPageVariables.GROUPS_FILTERS_FIELD_OPERATING_SYSTEM;
		String operatorValue = WEPPGroupsPageVariables.GROUPS_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"Groups_FILTERS_OPERATING_SYSTEM_VALUE");
		
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
			DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
			Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active devices filters applied successfully");
			sleeper(2000);
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"operatingsystemColumnTitle","operatingsystemColumnListValueSearch",valueName,"countPage");
			groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
		groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
		waitForPageLoaded();
		Assert.assertTrue(groupsPage.verifyAddGroupsStep1GroupSelectionFlow(dynamicGroupType,LanguageCode,DG_GroupName,DG_GroupDescription),"Add Groups Step1 is getting failed");
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		sleeper(2000);
		List<String> verifyStep2DynamicCreationFlow = groupsPage.verifyStep2DynamicCreationFlow(LanguageCode, propertyInputValue, operatorValue, valueName);
		sleeper(2000);
		Integer AudienceCount = groupsPage.handleCalculateButton();
		Assert.assertEquals(TotalAudience, AudienceCount,"Devices count is not getting matched");
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		List<String> verifyStep3DynamicCreationFlow = groupsPage.verifyStep3DynamicCreationFlow(LanguageCode,DG_GroupName,DG_GroupDescription,0);
		Assert.assertEquals(verifyStep2DynamicCreationFlow, verifyStep3DynamicCreationFlow, "Selected Filters/Values are not matching");
		LOGGER.info("Dynamic group >>Device Count is matched between Add group Review apge and Calculate button device count");
		groupsPage.actionClickOfGroupsPage("addGrpBtn");
		waitForPageLoaded();
		groupsPage.waitForElementsOfGroupsPage("toastNotificationExport");
		groupsPage.getTextOfWEPGroupsPage("toastNotificationExport");
		LOGGER.info("Toaster Message for Group creation:"+groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
//		Assert.assertEquals(
//				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"),groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.dynamic.created.toast.title"),"Dynamic Group Creation is failed");
		LOGGER.info("Dyanmic Group creation is done successfully");
		Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("viewGroups"));
		sleeper(4000);
		groupsPage.enterTextForGroupsPage("groupNameSearch",DG_GroupName);
		sleeper(3000);		
		waitForPageLoaded();
		groupsPage.verifyElementIsPresentOnGroupsPage("groupNameCheck");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameCheck").equals(DG_GroupName),"Failed to create Dynamic Group");		
		sleeper(2000);		
		waitForPageLoaded();
		LOGGER.info("Dynamic group creation is done successfully and its showing on Group List page");	
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
//		sleeper(3000);
		//String overviewTabDeviceCount = groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameHeaderTitle").equals(DG_GroupName),"Device Count is not matched");		
		LOGGER.info("Dynamic group Name title is verified");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupDescription").equals(DG_GroupDescription),"Device Count is not matched");		
		LOGGER.info("Dynamic group description is verified");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameTxt").equals(DG_GroupName),"Device Count is not matched");		
		LOGGER.info("Group Name is verified on overview tab");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("dynamicGroupMemershipType").toUpperCase().contains(getTextLanguage(LanguageCode, "daas_ui", "device.groups.dynamic.label").toUpperCase()),"Groups membershiptype doesn't exists");		
		LOGGER.info("Group membershiptype is verified on overview tab");
//		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount").equals(deviceCount),"Device Count is not matched");		
//		LOGGER.info("Dynamic group >>Device Count is matched between Add group Review apge and Overview page");
		groupsPage.actionClickOfGroupsPage("listRedirection");
		sleeper(2000);		
		waitForPageLoaded();
		Assert.assertTrue(groupsPage.deleteGroups(DG_GroupName));
	}
	
	/**
	 * WEPINT-434 >>[Grouping][UI][Dynamic Group Detail] Create Group Detail membership tab with Edit functionality.
	 * TC_C44639874:Verify 'Edit Dynamic Rules for a Group' functionality on WEP Platform
	 */

	@Test(priority = 3, groups = { "REGRESSION_GROUPS" },  description="Testcase ID: TC_C59809089")
	public final void verifyEditDynamicGroup() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		String createdOn = getTextLanguage(LanguageCode, "daas_ui", "group.created_on");
		Assert.assertTrue(groupsPage.verifySortingForDifferentHeaderFields("createdOnHeaderSort",createdOn));
		sleeper(3000);
		if(!groupsPage.verifyElementsOfWEPGroupsPage("noResults")) {
			waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("groupMembership");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("editGroupBtn");
		sleeper(2000);
		groupsPage.clickOnElementsOfGroupsPage("editAddRuleLink");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("delete1stRuleBtn");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("editAddRuleLink");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("delete1stRuleBtn");
		waitForPageLoaded();
		sleeper(5000);
		LOGGER.info("Current Rule1 and Rule2 are deleted");
		groupsPage.waitForElementsOfGroupsPage("editPropertyListbox1");
		groupsPage.actionClickOfGroupsPage("editPropertyListbox1");
		groupsPage.clickOnElementsOfGroupsPage("editPropertyListItem1");
		waitForPageLoaded();
		sleeper(2000);
		groupsPage.actionClickOfGroupsPage("OperatorListbox1");
		groupsPage.clickOnElementsOfGroupsPage("OperatorListItem1");
		waitForPageLoaded();
		groupsPage.waitForElementsOfGroupsPage("valueListboxInput");
		groupsPage.enterTextForGroupsPage("valueListboxInput","HP");
		waitForPageLoaded();
		sleeper(2000);
		groupsPage.actionClickOfGroupsPage("addRuleButton");
		LOGGER.info("Edit Membership >> Rule1 added");
		groupsPage.waitForElementsOfGroupsPage("editPropertyListbox2");
		groupsPage.actionClickOfGroupsPage("editPropertyListbox2");
		groupsPage.clickOnElementsOfGroupsPage("editPropertyListItem2");
		waitForPageLoaded();
		sleeper(2000);
		groupsPage.waitForElementsOfGroupsPage("editValueListbox2");
		groupsPage.actionClickOfGroupsPage("editValueListbox2");
		groupsPage.clickOnElementsOfGroupsPage("editValueListItem2");
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Edit Membership >> Rule2 added");
		groupsPage.clickOnElementsOfGroupsPage("saveGroupBtn");
		waitForPageLoaded();
		sleeper(2000);
		Assert.assertTrue(
				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport").contains("Group modified"),"Dynamic Group Rules Updation is failed");
		//.equals(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.dynamic.updated.toast.title")),"Dynamic Group Rules Updation is failed");
		LOGGER.info("Group Rule are updated successfully");
		}LOGGER.info("There are no records found for the searched group value");
	}

	/**
	 * WEPINT-427 >>[Grouping][UI][Dynamic Group Detail] Create Edit functionality under Group Detail overview tab
	 * TC_C50905744:[WEP]Verify 'Edit Dynamic Group Name and Description' overview page functionality
	 */

	@Test(priority = 9, groups = { "REGRESSION_GROUPS","PRODUCTION_GROUPS" },enabled=false, description="Testcase ID: TC_C58768936")
	public final void verifyEditDynamicGroupNameDescription() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String DG_GroupName = "Auto_DynamicGroup";
		String Edited_DG_GroupName = "Auto_Updated_DynamicGroup"+generateRandomNumber();
		String DG_GroupDescription = "Group description is updated by automation";
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		groupsPage.enterTextForGroupsPage("groupNameSearch",DG_GroupName);
		sleeper(2000);
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("editGroupBtn");
		waitForPageLoaded();
		groupsPage.enterTextForGroupsPage("editGroupNameField",Edited_DG_GroupName);
		waitForPageLoaded();
		LOGGER.info("Updated Group Name is entered in Edit field");
		sleeper(2000);
		groupsPage.enterTextForGroupsPage("editGroupDescriptionField",DG_GroupDescription);
		LOGGER.info("Updated Group Description is entered");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("saveGroupBtn");
		//	System.out.println(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
		Assert.assertTrue(
				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
				.equals(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.dynamic.updated.toast.title")),"Dynamic Group Updation is failed");
		LOGGER.info("Dyanmic Group Name and Description updation is done successfully");

	}

	/**
	 * WEPINT-525 >> [Grouping][UI][ Dynamic Group Detail] Add export button with functionality(Export Group) on Group Details page.
	 * TC_C51872526:[WEP] Verify Global export functionality on Dynamic group
	 */

	@Test(priority = 10, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C59809092,TC_C58768939")
	public final void verifyExportDynamicGroup() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String Edited_DG_GroupName = "Auto_Updated_DynamicGroup";
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		sleeper(2000);
		//groupsPage.gotoGroupsTab(LanguageCode);
		LOGGER.info("Groups Header is Matched");
		groupsPage.enterTextForGroupsPage("groupNameSearch",Edited_DG_GroupName);
		waitForPageLoaded();
		if(!groupsPage.verifyElementsOfWEPGroupsPage("noResults")) {
			waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("exportButton");
		waitForPageLoaded();
		//	System.out.println(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
		Assert.assertTrue(
				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
				.equals(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "device.details.export.device.data.success")),"Dynamic Group Export is failed");
		LOGGER.info("Dyanmic Group global Export is done successfully");

	}LOGGER.info("There are no records found for the searched group value");
	}

	/**
	 * WEPINT-613 >>[Grouping][UI][ Group Detail] API Implemention for Export button for membership tab for static and dynamic flow
	 * TC_C50766138:[WEP] Verify Membership tab export functionality on Dynamic group
	 */
	@Test(priority = 11, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C50766138")
	public final void verifyMembershipExportOnDynamicGroup() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String Edited_DG_GroupName = "Auto_Updated_DynamicGroup";
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		sleeper(2000);
		groupsPage.enterTextForGroupsPage("groupNameSearch",Edited_DG_GroupName);
		waitForPageLoaded();
		if(!groupsPage.verifyElementsOfWEPGroupsPage("noResults")) {
			waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("groupMembership");
		waitForPageLoaded();
		if(groupsPage.verifyElementIsPresentOnGroupsPage("select1stDevice")) {
			groupsPage.clickOnElementsOfGroupsPage("select1stDevice");
			waitForPageLoaded();
			sleeper(3000);
			groupsPage.actionClickOfGroupsPage("membershipExportButton");
			waitForPageLoaded();
			Assert.assertTrue(
					groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
					.equals(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "device.details.export.device.data.success")),"Dynamic Group Export is failed");
			LOGGER.info("Membership Export is done successfully with selected devices");
		}else {
			LOGGER.info("No Devices are showing on Device List page on the Dyanmic Group of Membership tab");
		}

	}LOGGER.info("There are no records found for the searched group value");
	}


	/**
	 * [WEXINT-434]>>[Grouping][UI][ Dynamic Group Detail] Add Delete button with functionality(Delete Group) on Group Details page.
	 * TC_C44639873:[WEP]Verify Dynamic group deletion functionality
	 * https://hp-jira.external.hp.com/browse/WEXINT-432
	 */
	@Test(priority = 3, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C44639873")
	public final void verifyDeleteDynamicGroup() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		String createdOn = getTextLanguage(LanguageCode, "daas_ui", "group.created_on");
		Assert.assertTrue(groupsPage.verifySortingForDifferentHeaderFields("createdOnHeaderSort",createdOn));
		sleeper(3000);
		waitForPageLoaded();
		if(!groupsPage.verifyElementsOfWEPGroupsPage("noResults")) {
			groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
			LOGGER.info("Verified the imported device count present under Overview tab");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("groupDeleteButton");
		if(verifyElementIsPresent("deletePopupassignmentTxt")) {
		Assert.assertTrue(
				groupsPage.getTextOfWEPGroupsPage("deletePopupassignmentTxt")
				.contains("group is used by other items in this platform"),"Delete assignment pop-up is not matching");		
		LOGGER.info(" Delete button is showing due to policy assigned to the group");
		groupsPage.clickOnElementsOfGroupsPage("closeModal");
		}else {
		waitForPageLoaded();
		String secCode = groupsPage.getTextOfWEPGroupsPage("securityCodeNumber");
		groupsPage.enterTextForGroupsPage("securityCodeField",secCode);
		sleeper(2000);
		groupsPage.clickOnElementsOfGroupsPage("deleteButton");
		waitForPageLoaded();
		Assert.assertTrue(
				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
				.contains(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.toast.delete.group.success")),"Dynamic Group Deletion is failed");
		LOGGER.info("Dyanmic Group deletion is done successfully");
		}}LOGGER.info("There are no records found for the searched group value / Connection not present into the current automation tenant.");
		
	}


	@Test(priority = 13, groups = { "REGRESSION_GROUPS","PRODUCTION_GROUPS"}, description="Testcase ID: TC_C58768935")
	public final void verifyDynamicRulesProperties() throws Exception {
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String DG_GroupName = "Auto_DynamicGroup";
		String DG_GroupDescription = "Group is created by automation";
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		sleeper(2000);
		groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("dynamicGroupRadioBtn");
		waitForPageLoaded();
		groupsPage.enterTextForGroupsPage("groupNameField",DG_GroupName);
		waitForPageLoaded();
		LOGGER.info("Group Name is entered");
		sleeper(2000);
		groupsPage.enterTextForGroupsPage("groupDescriptionField",DG_GroupDescription);
		LOGGER.info("Group Description is entered");
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.waitForElementsOfGroupsPage("propertyValueDropDown");
		groupsPage.actionClickOfGroupsPage("propertyValueDropDown");
		sleeper(3000);
		/*******Verify for Dynamic group 5 Properties should  show those are  "manufacturer", "device model", "device name", "operating system", "serial number" *********/
		ArrayList<String> propertyListOptions = groupsPage.getTextOfListOfElementsFromGroupPage("propertyListOptions");
		String[] originaOptionslList = {"manufacturer", "device model", "device name", "operating system", "serial number"};
		ArrayList<String> originaOptionslListStringArray = new ArrayList<>(Arrays.asList(originaOptionslList));
		groupsPage.verifyListOfElemetnsOnGroupsPage(originaOptionslListStringArray,propertyListOptions);
		LOGGER.info("Dynamic Property Rules are verified");
	}
	
	/**
	 * WEPINT-743 >>[Grouping][UI][Add Groups] Add Static Groups API implementation.
	 * https://hp-jira.external.hp.com/browse/WEXINT-426
	 * TC_C51872292:[WEP]>>Verify Create Static Group with selection of Devices from Device List UI Implementation	
	 */

	@Test(priority = 14, groups = { "REGRESSION_GROUPS","PRODUCTION_GROUPS","REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ" },enabled=false, description="Testcase ID: TC_C59809088,TC_C58768946")
	public final void verifyCreationofStaticGroupsWithDeviceList() throws Exception {
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String SG_GroupName = "Auto_StaticGroup"+generateRandomNumber();
		String SG_GroupDescription = "Static Group is created for"+SG_GroupName;
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb").equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		sleeper(2000);
		groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("staticGroupadioBtn");
		waitForPageLoaded();
		String deviceCount = groupsPage.staticGroupCreationDeviceList(SG_GroupName, SG_GroupDescription, 3);
		waitForPageLoaded();
		//Assert.assertTrue(groupsPage.verifyNotificationForGroups(LanguageCode,SG_GroupName),"Device mapping notification did not appear after 15 retries with 5-second intervals");
//		Assert.assertTrue(
//				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
//				.contains(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.dynamic.created.toast.title")),"Static Group Creation is failed");
		LOGGER.info("Static Group creation is done successfully");
		sleeper(3000);	
		groupsPage.enterTextForGroupsPage("groupNameSearch",SG_GroupName);
		waitForPageLoaded();
		sleeper(3000);
		System.out.println(groupsPage.getTextOfWEPGroupsPage("groupNameCheck"));
		groupsPage.verifyElementIsPresentOnGroupsPage("groupNameCheck");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameCheck").equals(SG_GroupName),"Failed to create Static Group with Device List");		
		LOGGER.info("Static group creation is done successfully and its showing on Group List page");	
		sleeper(5000);
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameHeaderTitle").equals(SG_GroupName),"Device Count is not matched");		
		LOGGER.info("Group Name title is verified");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupDescription").equals(SG_GroupDescription),"Device Count is not matched");		
		LOGGER.info("Group description is verified");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameTxt").equals(SG_GroupName),"Device Count is not matched");		
		LOGGER.info("Group Name is verified on overview tab");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("staticGroupMemershipType").toUpperCase().equals(getTextLanguage(LanguageCode, "daas_ui", "group.static_assignment").toUpperCase()),"Groups membershiptype doesn't exists");		
		LOGGER.info("Group membershiptype is verified on overview tab");
		refreshPage();
		waitForPageLoaded();
		sleeper(3000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount").equals(deviceCount),"Device Count is not matched");		
		LOGGER.info("Group >>Device Count is matched between Add group Review apge and Overview page");
		groupsPage.actionClickOfGroupsPage("listRedirection");
		sleeper(2000);		
		waitForPageLoaded();
		Assert.assertTrue(groupsPage.deleteGroups(SG_GroupName));
	}


	/**
	 * WEPINT-743 >>[Grouping][UI][Add Groups] Add Static Groups API implementation.
	 * https://hp-jira.external.hp.com/browse/WEXINT-426
	 * TC_C51872292:[WEP]>>Verify Create Static Group with selection of Devices from Device List UI Implementation	
	 */
	@Test(priority = 15, groups = { "REGRESSION_GROUPS","PRODUCTION_GROUPS" }, description="Testcase ID: TC_C59809090")
	public final void verifyCreateStaticGroupsWithCSVFileUploadSerialNumber() throws Exception {
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String SG_GroupName = "Auto_StaticGroup_CSVUpload"+generateRandomNumber();
		String SG_GroupDescription = "Static Group with CSv file is created by automation";
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
			Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
			LOGGER.info("Active devices filters applied successfully");
			List<String> devicelist = DevicesListPage.getAllTextOfWEPDeviceListPage("deviceserialnumberfield");
			sleeper(5000);
			int TotalActiveDevice;
			String TotalAudienceSt = DevicesListPage.getTextOfWEPDeviceListPage("countPage").replace("of ", "");
			if(TotalAudienceSt.contains(",")) {
				String replaced = TotalAudienceSt.replace(",", "");
				TotalActiveDevice = Integer.parseInt(replaced);
			}else {
			TotalActiveDevice = Integer.parseInt(TotalAudienceSt);
			}
			LOGGER.info("Active devices got fetched from device list page successfully");
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		sleeper(2000);
		groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("staticGroupadioBtn");
		waitForPageLoaded();
		groupsPage.enterTextForGroupsPage("groupNameField",SG_GroupName);
		waitForPageLoaded();
		LOGGER.info("Group Name is entered");
		sleeper(2000);
		groupsPage.enterTextForGroupsPage("groupDescriptionField",SG_GroupDescription);
		LOGGER.info("Group Description is entered");
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.verifyAudienceSectionValidationsTargetCSVmethod(LanguageCode, "selectGroupProperty", "selectGroupPropertyAsSerialNumber",devicelist);
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
		waitForPageLoaded();
		sleeper(2000);
		groupsPage.clickOnElementsOfGroupsPage("addGrpBtn");
		waitForPageLoaded();
		System.out.println(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
//		Assert.assertTrue(
//				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
//				.contains(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.dynamic.created.toast.title")),"Static Group Creation is failed");		
		LOGGER.info("Static Group creation with CSv file upload is done successfully");
		sleeper(3000);		
		groupsPage.enterTextForGroupsPage("groupNameSearch",SG_GroupName);
		sleeper(2000);
		waitForPageLoaded();
		System.out.println(groupsPage.getTextOfWEPGroupsPage("groupNameCheck"));
		groupsPage.verifyElementIsPresentOnGroupsPage("groupNameCheck");
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameCheck").equals(SG_GroupName),"Failed to create Static Group");		
		LOGGER.info("Static group creation is done successfully and its showing on Group List page");		
	}


	/**
	 * WEPINT-743 >>[Grouping][UI][Add Groups] Add Static Groups API implementation.
	 * https://hp-jira.external.hp.com/browse/WEXINT-426
	 * TC_C51872292:[WEP]>>Verify Create Static Group with selection of Devices from Device List UI Implementation	
	 */
	@Test(priority = 16, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C58768952")
	public final void verifyEditStaticGroupsWithCSVFileUpload() throws Exception {
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String SG_GroupName = "Auto_StaticGroup";
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		groupsPage.enterTextForGroupsPage("groupNameSearch",SG_GroupName);
		sleeper(3000);
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		LOGGER.info("Assigning devices to Static Group with CSV  File");
		groupsPage.clickOnElementsOfGroupsPage("groupMembership");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("assignDevicesToGrpBtn");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.waitForElementsOfGroupsPage("selectCSVFileOptonin_StaticGroup");
		groupsPage.actionClickOfGroupsPage("selectCSVFileOptonin_StaticGroup");
		sleeper(5000);
		waitForPageLoaded();
		groupsPage.actionClickOfGroupsPage("selectGroupProperty");
		groupsPage.actionClickOfGroupsPage("selectGroupPropertyAsDeviceName");
		String csvFile = ConstantPath.IMPORT_PATH + CommonVariables.DEVICE_NAMES_CSV;
		System.out.println(csvFile);
		groupsPage.addDevicestogroupViaCSV("uploadCSVFileOptionField",csvFile);
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("addDevicesToStaticGrpBtn");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		System.out.println(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
		Assert.assertTrue(
				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
				.contains("CSV is being processed."),"Static Group Creation is failed");		
		LOGGER.info("Assigning devices to Static Group  is done successfully");
		sleeper(2000);		
	}

	/**
	 * WEPINT-434 >>[Grouping][UI][Dynamic Group Detail] Create Group Detail membership tab with Edit functionality.
	 * TC_C44639874:	Verify 'Edit Dynamic Rules for a Group' functionality on WEP Platform
	 */

	@Test(priority = 17, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C44639874,TC_C58768951")
	public final void verifyEditStaticGroupWithDeviceList() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String SG_GroupName = "Edit_StaticGroup_DevicesList";
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		groupsPage.enterTextForGroupsPage("groupNameSearch",SG_GroupName);
		sleeper(2000);
		waitForPageLoaded();
		if(!groupsPage.verifyElementsOfWEPGroupsPage("noResults")) {
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		LOGGER.info("Assigning devices to Static Group");
		groupsPage.clickOnElementsOfGroupsPage("groupMembership");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("assignDevicesToGrpBtn");
		sleeper(2000);		
		groupsPage.waitForElementsOfGroupsPage("selectDeviceListin_StaticGroup");
		groupsPage.actionClickOfGroupsPage("selectDeviceListin_StaticGroup");
		sleeper(5000);
		LOGGER.info("Selecting the devices from Assigning devices to Static Group Page");
		waitForPageLoaded();
		groupsPage.clearFiltersOfGroupsListPage("clearListPageGroupCreation");
		groupsPage.clickOnElementsOfGroupsPage("assignDevice1_StaticGroup");
		sleeper(2000);
		groupsPage.clickOnElementsOfGroupsPage("assignDevice2_StaticGroup");
		sleeper(2000);
		LOGGER.info("Devices are selected");
		groupsPage.clickOnElementsOfGroupsPage("addDevicesToStaticGrpBtn");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(
				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport").contains("Group modified"),"Static Group Update with Assign is failed");
		//groupsPage.getTextLanguage(LanguageCode, "daas_ui", "group.assign_devices")),"Static Group Update with Assign is failed");
		LOGGER.info("Assigning devices to Static Group  is done successfully");
	}
	LOGGER.info("There are no records found for the searched group value");
	}


	/**
	 * WEPINT-427 >>[Grouping][UI][Dynamic Group Detail] Create Edit functionality under Group Detail overview tab
	 * TC_C50905744:[WEP]Verify 'Edit Dynamic Group Name and Description' overview page functionality
	 */

	@Test(priority = 18, groups = { "REGRESSION_GROUPS","PRODUCTION_GROUPS" },enabled=false,description="Testcase ID: TC_50905744,TC_C58768953")
	public final void verifyEdiStaticGroupName() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String SG_GroupName= "Auto_StaticGroup_CSVUpload";
		String Edited_SG_GroupName = "Auto_Updated_StaticGroup"+generateRandomNumber();
		String DG_GroupDescription = "Group description is updated for "+Edited_SG_GroupName;
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		groupsPage.enterTextForGroupsPage("groupNameSearch",SG_GroupName);
		sleeper(2000);
		if(!groupsPage.verifyElementsOfWEPGroupsPage("noResults")) {
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("editGroupBtn");
		waitForPageLoaded();
		groupsPage.enterTextForGroupsPage("editGroupNameField",Edited_SG_GroupName);
		waitForPageLoaded();
		LOGGER.info("Updated Group Name is entered in Edit field");
		sleeper(2000);
		groupsPage.enterTextForGroupsPage("editGroupDescriptionField",DG_GroupDescription);
		LOGGER.info("Updated Group Description is entered");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("saveGroupBtn");
		//	System.out.println(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
		Assert.assertTrue(
				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
				.equals(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.dynamic.updated.toast.title")),"Static Group Updation is failed");
		LOGGER.info("Static Group Name and Description updation is done successfully");
		
	}
	LOGGER.info("There are no records found for the searched group value");
	}
		

	/**
	 * WEPINT-525 >> [Grouping][UI][ Dynamic Group Detail] Add export button with functionality(Export Group) on Group Details page.
	 * TC_C51872526:[WEP] Verify Global export functionality on Dynamic group
	 */

	@Test(priority = 19, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C51872526")
	public final void verifyGlobalExportOnStaticGroup() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String Edited_SG_GroupName = "Auto_Updated_StaticGroup";
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		groupsPage.enterTextForGroupsPage("groupNameSearch",Edited_SG_GroupName);
		waitForPageLoaded();
		if(!groupsPage.verifyElementsOfWEPGroupsPage("noResults")) {
			waitForPageLoaded();
		sleeper(2000);
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("exportButton");
		waitForPageLoaded();
		//	System.out.println(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
		Assert.assertTrue(
				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
				.equals(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "device.details.export.device.data.success")),"Static Group Export is failed");
		LOGGER.info("Static Group global Export is done successfully");
		
		
	}LOGGER.info("There are no records found for the searched group value");
	}

	/**
	 * WEPINT-613 >>[Grouping][UI][ Group Detail] API Implemention for Export button for membership tab for static and dynamic flow
	 * TC_C50766138:[WEP] Verify Membership tab export functionality on Static group
	 */
	@Test(priority = 20, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C59809091")
	public final void verifyDevicesExportOnStaticGroupFromMembershipTab() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String Edited_SG_GroupName = "DevicesExport_Static - GRP";
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		sleeper(2000);
		groupsPage.enterTextForGroupsPage("enterGroupName", Edited_SG_GroupName);
		sleeper(2000);
		if(!groupsPage.verifyElementsOfWEPGroupsPage("noResults")) {
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("groupMembership");
		waitForPageLoaded();
		if(groupsPage.verifyElementIsPresentOnGroupsPage("select1stDevice")) {
			groupsPage.clickOnElementsOfGroupsPage("select1stDevice");
			waitForPageLoaded();
//			groupsPage.clickOnElementsOfGroupsPage("select1stPageDevices");
//			waitForPageLoaded();
			sleeper(3000);
			groupsPage.clickOnElementsOfGroupsPage("membershipExportButton");
			waitForPageLoaded();
			Assert.assertTrue(
					groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
					.equals(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "device.details.export.device.data.success")),"Static Group Export is failed");
			LOGGER.info("Membership Export is done successfully with selected devices");
		}else {
			LOGGER.info("No Devices are showing on Device List page on the static Group of Membership tab");
		}
		sleeper(3000);
		Assert.assertTrue(groupsPage.verifyNotificationForGroups(LanguageCode,Edited_SG_GroupName));
		}
		LOGGER.info("There are no records found for the searched group value");
		}

	/**
	 * WEPINT-612 >>[Grouping][UI][ Static Group Detail] Remove the devices from membership table under static group
	 * https://hp-jira.external.hp.com/browse/WEXINT-612
	 * TC_C50766138:[WEP] Verify Membership tab export functionality on Dynamic group
	 */
	@Test(priority = 21, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C58768944")
	public final void verifyRemoveDevicesOnStaticGroupFromMembershipTab() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String Edited_SG_GroupName = "Devices Removal group";
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		groupsPage.enterTextForGroupsPage("groupNameSearch",Edited_SG_GroupName);
		if(!groupsPage.verifyElementsOfWEPGroupsPage("noResults")) {
		waitForPageLoaded();
		sleeper(2000);
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("groupMembership");
		waitForPageLoaded();
		if(groupsPage.verifyElementIsPresentOnGroupsPage("select1stDevice")) {
			groupsPage.clickOnElementsOfGroupsPage("select1stDevice");
			waitForPageLoaded();
			LOGGER.info("1st Device is selected");
			sleeper(2000);
			groupsPage.clickOnElementsOfGroupsPage("membershipRemoveButton");
			waitForPageLoaded();
			Assert.assertTrue(
					groupsPage.getTextOfWEPGroupsPage("toastNotificationExport").contains("Device removal in progress"),"Devices are removed from Static Group is failed");
					//.equals(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "assets.list.toast.remove.assets.success")),"Devices are removed from Static Group is failed");
			LOGGER.info("Membership Remove device  is done successfully with selected devices");
		}else {
			LOGGER.info("No Devices are showing on Device List page on the Static Group of Membership tab");
		}
		}LOGGER.info("There are no records found for the searched group value");
	}

	/**
	 * [WEXINT-434]>>[Grouping][UI][ Dynamic Group Detail] Add Delete button with functionality(Delete Group) on Group Details page.
	 * TC_C44639873:[WEP]Verify Dynamic group deletion functionality
	 * https://hp-jira.external.hp.com/browse/WEXINT-432
	 */
	@Test(priority = 15, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C44639873")
	public final void verifyDeleteStaticGroup() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		sleeper(2000);
		String createdOn = getTextLanguage(LanguageCode, "daas_ui", "group.created_on");
		Assert.assertTrue(groupsPage.verifySortingForDifferentHeaderFields("createdOnHeaderSort",createdOn));
		sleeper(3000);
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("groupDeleteButton");
		waitForPageLoaded();
		String secCode = groupsPage.getTextOfWEPGroupsPage("securityCodeNumber");
		groupsPage.enterTextForGroupsPage("securityCodeField",secCode);
		sleeper(2000);
		groupsPage.clickOnElementsOfGroupsPage("deleteButton");
		waitForPageLoaded();
		System.out.println(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
		Assert.assertTrue(
				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
				.contains(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.toast.delete.group.success")),"Static Group Deletion is failed");
		LOGGER.info("Static Group deletion is done successfully");

	}

	/**
	 * This TC_C57058261>>[WEP] Verify Sorting Functionality on WEP Groups list page
	 * @throws Exception
	 */
	@Test(priority = 23, groups = { "REGRESSION_GROUPS","PRODUCTION_GROUPS" }, description="Testcase ID: C57058261")
	public final void verifySortingOfGroupNameColumnInSortingOrder() throws Exception {
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		sleeper(2000);
		waitForPageLoaded();
		groupsPage.clearFiltersOfGroupsListPage("clearListPageFilter");
		groupsPage.sortingOfRoomNameoColumnInAscendingOrder("groupNameColumn","groupNamelistkey","navigationItemNext","navigationItemFrist","groupNamelistkey","selectAllCheckBox");
		LOGGER.info("Group Names are in Ascending Order");
		groupsPage.sortingOfRoomNameColumnInDescendingOrder("groupNameColumn","groupNamelistkey","navigationItemNext","navigationItemFrist","groupNamelistkey","selectAllCheckBox");
		LOGGER.info("Group Names are in Descending Order");
	} 

	/**
	 * This TC_C53527002>>[WEP] Verify Search Functionality on Groups List table
	 * @throws Exception
	 */
	@Test(priority = 24, groups = { "REGRESSION_GROUPS","PRODUCTION_GROUPS" }, description="Testcase ID: TC_C58768965")
	public final void verifyPartialSearchFunctionalityOnGroupListPage() throws Exception {
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String groupNameSearchKey = "group";
		String searchNoItemsIntheList = "Book";
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		sleeper(2000);
		waitForPageLoaded();
		groupsPage.waitForElementsOfGroupsPage("deviceNameSearchBox");
		Assert.assertEquals(groupsPage.verifySearchValueOfColumnOnGroupList(LanguageCode,"groupNameSearch",groupNameSearchKey,"noItemsAvailable","listkey"),true);
		LOGGER.info("Search Results are showing as expected");
		Assert.assertEquals(groupsPage.verifySearchValueOfColumnOnGroupList(LanguageCode,"groupNameSearch",searchNoItemsIntheList,"noItemsAvailable","listkey"),true);
		LOGGER.info("No Results Message is showing as expected");

	} 



	/**
	 * TC_C57059720:[WEP]>>Verify User is able to assign the specific Static group to any policy from Policies tab
	 */
	@Test(priority = 25, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C58768957,TC_C58768962")
	public final void verifyDeleteButtonShouldDisablewhenPolicyisAssignedStaticGroup() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String SG_GroupName = "Edit_DELETE_Check_On_StaticGroup1"+generateRandomNumber();
		String SG_GroupDescription = "Auto_DELETE_StaticGroup with Scripts and policy";
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("staticGroupadioBtn");
		waitForPageLoaded();
		groupsPage.staticGroupCreationDeviceList(SG_GroupName, SG_GroupDescription, 3);		
		waitForPageLoaded();
		sleeper(2000);
		groupsPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		groupsPage.clickOnElementsOfGroupsPage("addPolicyButton");
		waitForPageLoaded();
		String policyname = "SG_Group With Bios Policy"+generateRandomNumber();
		groupsPage.enterTextForGroupsPage("createPolicyNameTextBox",policyname);
		waitForPageLoaded();
		groupsPage.waitForElementsOfGroupsPage("selectBiosOptionDropdown");
		groupsPage.actionClickOfGroupsPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		groupsPage.clickOnElementsOfGroupsPage("biosSettingsOption");
		sleeper(3000);
		
		groupsPage.enterTextForGroupsPage("createPolicyDescriptionTextBox",policyname);
		groupsPage.clickOnElementsOfGroupsPage("policyScopeGlobalOption");
		groupsPage.clickOnElementsOfGroupsPage("nextbuttonOnPolicyCreationPage");
		sleeper(3000);
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		groupsPage.clickOnElementsOfGroupsPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("addPolicyButton");
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.enterTextForGroupsPage("policySearch","policyname");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("policySearchItem1");
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("policiesAssignmentTab");
		waitForPageLoaded();
		groupsPage.clickOnElementsOfGroupsPage("addPolicyButton");
		groupsPage.enterTextForGroupsPage("policySearch",SG_GroupName);
		sleeper(3000);
		groupsPage.waitForElementsOfGroupsPage("selectGroupItem");
		groupsPage.clickOnElementsOfGroupsPage("selectGroupItem");
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("nextbuttonOnPolicyCreationPage");
		sleeper(2000);
		groupsPage.clickOnElementsOfGroupsPage("assignGroupButton");
		waitForPageLoaded();
		System.out.println(groupsPage.getTextOfWEPGroupsPage("policyTastNotificationExport"));
		
//		Assert.assertTrue(
//				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
//				.contains("Group assignment pending")," Policies assigned to Static Group is failed");
		//waitForPageLoaded();
		//sleeper(5000);
		//System.out.println(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
//		Assert.assertTrue(
//				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
//				.contains("Group assigned")," Policies assigned to Static Group is failed");

		LOGGER.info("Policies assigned to Static Group is done successfully");
		groupsPage.companyView(CommonVariables.GROUPS);
		waitForPageLoaded();
		groupsPage.enterTextForGroupsPage("groupNameSearch",SG_GroupName);
		waitForPageLoaded();
		sleeper(3000);
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		refreshPage();
		waitForPageLoaded();
		sleeper(3000);
		Assert.assertTrue(
				groupsPage.getTextOfWEPGroupsPage("groupPoliciesAssigned")
				.equals("1")," Policies assigned to Static Group is failed");
		groupsPage.clickOnElementsOfGroupsPage("groupMembership");
		sleeper(3000);
		if(groupsPage.verifyElementsOfWEPGroupsPage("deleteGroupButtonChk")==true) {
			groupsPage.clickOnElementsOfGroupsPage("deleteGroupButtonChk");
			Assert.assertTrue(
					groupsPage.getTextOfWEPGroupsPage("deletePopupassignmentTxt")
					.contains("group is used by other items in this platform"),"Delete pop-up is not matching");
			Assert.assertTrue(
					groupsPage.getTextOfWEPGroupsPage("deleteAssignment")
					.contains("Policy"),"Delete pop-up is not matching");		
			LOGGER.info(" Delete button is showing due to policy assigned to the group");
			groupsPage.clickOnElementsOfGroupsPage("closeModal");
			Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("assignmentTab"),"Assignment tab is not getting displayed for newly created group");
			groupsPage.clickOnElementsOfGroupsPage("assignmentTab");
			LOGGER.info("Redirected to Assignment list page successfully");
			Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("assignmentEntry"),"Assignment Entry for assigned polcytab is not getting displayed for newly created group");
			Assert.assertFalse(groupsPage.verifyElementIsPresentOnGroupsPage("noAssignments"),"No Assignments texts are getting displayed after even active assignmtns being displayed");
			Assert.assertFalse(groupsPage.verifyElementIsPresentOnGroupsPage("noAssignmentsInnerSection"),"No Assignments Inner section texts are getting displayed after even active assignmtns being displayed");
			LOGGER.info("Verified Assignment list page successfully");
		}else {
			LOGGER.info("Delete button is not showing and policy is not correctly assigned to the group");
		}
	}

	/**
	 * TC_C57059720:[WEP]>>Verify User is able to assign the specific Static group to any policy from Policies tab
	 */
	@Test(priority = 27, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C57059720")
	public final void verifyDeleteButtonShouldDisplayedwhenPolicyisUnAssignedStaticGroup() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		
	//Unassign the  from Dyanmic Group
	groupsPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from Remediation tab successfully");
		sleeper(2000);
	waitForPageLoaded();
	groupsPage.waitForElementsOfGroupsPage("policySearch");
	groupsPage.enterTextForGroupsPage("policySearch","SG_Group With Bios Policy");
	sleeper(3000);
	waitForPageLoaded();
	groupsPage.clickOnElementsOfGroupsPage("policySearchItem1");
	groupsPage.waitForElementsOfGroupsPage("policiesAssignmentTab");
	groupsPage.clickOnElementsOfGroupsPage("policiesAssignmentTab");
	waitForPageLoaded();
	groupsPage.waitForElementsOfGroupsPage("groupItem1");
	groupsPage.mouseHoverOfGroupsPage("groupItem1");
	sleeper(2000);
	groupsPage.actionClickOfGroupsPage("actionmenuItemOnPolicy");
	groupsPage.waitForElementsOfGroupsPageDynamic("unAssignedPolicyMenuOption",5);
	groupsPage.actionClickOfGroupsPage("unAssignedPolicyMenuOption");
	String SG_GroupName = groupsPage.textExtractofGroupsPage("textGroupNameTobeFetched","Edit_DELETE_Check_On_StaticGroup\\d+");
	sleeper(2000);
	groupsPage.clickOnElementsOfGroupsPage("unAssignedBtn");
	waitForPageLoaded();
	System.out.println(groupsPage.getTextOfWEPGroupsPage("policyTastNotificationExport"));
	Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("policyTastNotificationExport").contains("Groups unassignment pending"), "Policies Unassignement from Sttaic Group is failed");
	//Delete the group
	groupsPage.companyView(CommonVariables.GROUPS);
	groupsPage.waitForElementsOfGroupsPage("groupNameSearch");
	groupsPage.enterTextForGroupsPage("groupNameSearch",SG_GroupName);
	waitForPageLoaded();
	groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
	refreshPage();
	waitForPageLoaded();
	sleeper(3000);
	groupsPage.clickOnElementsOfGroupsPage("groupDeleteButton");
	waitForPageLoaded();
	String secCode = groupsPage.getTextOfWEPGroupsPage("securityCodeNumber");
	groupsPage.enterTextForGroupsPage("securityCodeField",secCode);
	groupsPage.waitForElementsOfGroupsPage("deleteButton");
	groupsPage.clickOnElementsOfGroupsPage("deleteButton");
	waitForPageLoaded();
	System.out.println(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
	Assert.assertTrue(
			groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
			.contains(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.toast.delete.group.success")),"Static Group Deletion is failed");
	LOGGER.info("Static Group delete button is allowed to be clieked and verification is done successfully");

}

/**
 * [WEXINT-763]>>[Grouping][UI] Apply check for not allowing the dynamic group rules edit (under membership tab) if any policy or script is associated
 * TC_C53526998:[WEP]>>Verify User is able to assign the specific static/Dynamic group to any policy from Policies tab
 * https://hp-jira.external.hp.com/browse/WEXINT-762
 */
@Test(priority = 26, groups = { "REGRESSION_GROUPS" }, description="Testcase IDs:TC_CC58768956, TC_C53526953")
public final void verifyEditRules_DeleteButtonShouldDisableWhenPolicyisAssigned() throws Exception {	
	login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
	WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
	String DG_GroupName = "Donot-Delete_UI_Automation-EditDynamic-Rules_Check";
	groupsPage.companyView(CommonVariables.GROUPS);
	waitForPageLoaded();
	sleeper(3000);
	groupsPage.enterTextForGroupsPage("groupNameSearch",DG_GroupName);
	waitForPageLoaded();
	sleeper(2000);
	groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
	waitForPageLoaded();
	sleeper(3000);
	int minAssignedCount = 1;
	String policesCountStr = groupsPage.getTextOfWEPGroupsPage("groupPoliciesAssigned");
	int policiesCount = Integer.parseInt(policesCountStr);
	Assert.assertTrue(
			policiesCount>=minAssignedCount," Policy is assigned to Dynamic Group is failed");
	groupsPage.clickOnElementsOfGroupsPage("groupMembership");
	waitForPageLoaded();
	Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("editGroupButtonMemChk"));
	sleeper(2000);
	Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("deleteGroupButtonChk"));
	LOGGER.info("Edit & Delete button is not getting displayed as policy is assigned");
}

/**
 * [WEXINT-763]>>[Grouping][UI] Apply check for not allowing the dynamic group rules edit (under membership tab) if any policy or script is associated
 * TC_C53526998:[WEP]>>Verify User is able to Un-assign the specific static/Dynamic group to any policy from Policies tab
 * https://hp-jira.external.hp.com/browse/WEXINT-762
 */
	@Test(priority = 28, groups = { "REGRESSION_GROUPS" },enabled=false, description="Testcase IDs:TC_C53526998, TC_C53526953")
	public final void verifyEditRules_DeleteButtonShouldEnabledWhenPolicyisUnAssigned() throws Exception {	
	login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
	WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
	//Unassign the  from Dyanmic Group
	groupsPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from Remediation tab successfully");
		sleeper(2000);
	waitForPageLoaded();
	groupsPage.enterTextForGroupsPage("policySearch","DG_Group With Bios Policy");
	sleeper(2000);
	waitForPageLoaded();
	groupsPage.clickOnElementsOfGroupsPage("policySearchItem1");
	sleeper(2000);
	groupsPage.clickOnElementsOfGroupsPage("policiesAssignmentTab");
	waitForPageLoaded();
	sleeper(2000);
	groupsPage.mouseHoverOfGroupsPage("groupItem1");
	sleeper(2000);
	groupsPage.actionClickOfGroupsPage("actionmenuItemOnPolicy");
	groupsPage.waitForElementsOfGroupsPageDynamic("unAssignedPolicyMenuOption",5);
	groupsPage.actionClickOfGroupsPage("unAssignedPolicyMenuOption");
	sleeper(2000);
	String GroupNameSentence = groupsPage.getTextOfWEPGroupsPage("textGroupNameTobeFetched");
	String DG_GroupName = groupsPage.textExtractofGroupsPage(GroupNameSentence,"Auto_DynamicGroup\\d+");
	System.out.println(DG_GroupName);
	groupsPage.clickOnElementsOfGroupsPage("unAssignedBtn");
	waitForPageLoaded();
//	System.out.println(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
//	Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport").contains("Groups unassignment pending"), "Policies Unassignement from Dynamic Group is failed");
	sleeper(2000);
	//Delete the group
	groupsPage.companyView(CommonVariables.GROUPS);
	groupsPage.enterTextForGroupsPage("groupNameSearch",DG_GroupName);
	sleeper(5000);
	waitForPageLoaded();
	groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
	waitForPageLoaded();
	sleeper(3000);
	groupsPage.clickOnElementsOfGroupsPage("groupDeleteButton");
	waitForPageLoaded();
	String secCode = groupsPage.getTextOfWEPGroupsPage("securityCodeNumber");
	groupsPage.enterTextForGroupsPage("securityCodeField",secCode);
	sleeper(2000);
	groupsPage.clickOnElementsOfGroupsPage("deleteButton");
	waitForPageLoaded();
	System.out.println(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
	Assert.assertTrue(
			groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
			.contains(groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.toast.delete.group.success")),"Dynamic Group Deletion is failed");
	LOGGER.info("Dynamic Group deletion is done successfully");
		
}
	
	/**
	 * This TC_C53526999>>[WEP] Verify User is able to assign the specific static/Dynamic group to any script from Script section
	 * TC_C53526953:[WEP]>>Verify if any script or policy is assigned Edit and Delete button should disable for Static group 
	 * @throws Exception
	 */
	@Test(priority = 27, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C53526953")
	public final void verifyDeleteButtonShouldDisablewhenScriptisAssignedGroup() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		String SG_GroupName = "Remediatie";
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		sleeper(2000);
		groupsPage.enterTextForGroupsPage("groupNameSearch",SG_GroupName);
		waitForPageLoaded();
		sleeper(3000);
		if(!groupsPage.verifyElementsOfWEPGroupsPage("noResults")) {
		groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
		waitForPageLoaded();
		sleeper(3000);
		int minAssignedCount = 1;
		String scriptsCountStr = groupsPage.getTextOfWEPGroupsPage("groupScriptsAssigned");
		int scriptsCount = Integer.parseInt(scriptsCountStr);
		Assert.assertTrue(
				scriptsCount>=minAssignedCount," Script is assigned to Static Group is failed");
		groupsPage.clickOnElementsOfGroupsPage("groupMembership");
		sleeper(2000);
		if(groupsPage.verifyElementIsPresentOnGroupsPage("deleteGroupButtonChk")==true) {
			Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("deleteGroupButtonChk"));
			groupsPage.clickOnElementsOfGroupsPage("deleteGroupButtonChk");
			Assert.assertTrue(
					groupsPage.getTextOfWEPGroupsPage("toastNotificationExport")
					.contains("Unable to delete group"),"Dynamic Group Deletion is failed");
			LOGGER.info("Delete button is getting displayed but deletion of group is not allowed as there is script assigned");
		}
		Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("assignmentTab"),"Assignment tab is not getting displayed for newly created group");
		groupsPage.clickOnElementsOfGroupsPage("assignmentTab");
		LOGGER.info("Redirected to Assignment list page successfully");
		Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("assignmentEntry"),"Assignment Entry for assigned polcytab is not getting displayed for newly created group");
		Assert.assertFalse(groupsPage.verifyElementIsPresentOnGroupsPage("noAssignments"),"No Assignments texts are getting displayed after even active assignmtns being displayed");
		Assert.assertFalse(groupsPage.verifyElementIsPresentOnGroupsPage("noAssignmentsInnerSection"),"No Assignments Inner section texts are getting displayed after even active assignmtns being displayed");
		LOGGER.info("Verified Assignment list page successfully");
		}else {
		LOGGER.info("There are no records found for the searched group value");
		}
	}

	/**
	 * This TC_C53527012>>[WEP] Verify Column Option setting from Group List Table Configuration page
	 * @throws Exception
	 */

	@Test(priority = 28, groups = { "REGRESSION_GROUPS" }, description="Testcase ID: TC_C58768955",enabled=false)
	public final void verifyDeviceListTableConfigurationFunctionalityOnGroupListPage() throws Exception {
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		sleeper(2000);
		waitForPageLoaded();
		//		sleeper(5000);// Url takes time to load
		ArrayList<String> groupName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "asset_details_name")));
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(
				getTextLanguage(LanguageCode, "daas_ui", "asset_details_name"),
				getTextLanguage(LanguageCode, "daas_ui", "group.membership_type"),
				getTextLanguage(LanguageCode, "daas_ui", "group.created_on"),
				getTextLanguage(LanguageCode, "daas_ui", "group.created_by"),
				getTextLanguage(LanguageCode, "daas_ui", "group.modified_on"),
				getTextLanguage(LanguageCode, "daas_ui", "group.modified_by")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "true"));
		verifyTableConfigurationTests(columnName, checkboxValue, groupName);
	}

	
		/**
		 * This method will verify Entra ID Group option available on Add Group Page.
		 * 
		 * @throws Exception
		 */
		@Test(priority = 29, groups = {"REGRESSION_GROUPS","PRODUCTION_GROUPS"}, description="Testcase ID: C57036427")
		public final void verifyEntraIDOptionAvailableOnAddGroupPage() throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
			WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			groupsPage.companyView(CommonVariables.GROUPS);
				LOGGER.info("Navigated to Groups successfully");
				sleeper(2000);
			groupsPage.waitForElementsOfGroupsPage("addGroupBtn");
			groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
			waitForPageLoaded();
			groupsPage.waitForElementsOfGroupsPage("entraIDRadioBtn");
			groupsPage.actionClickOfGroupsPage("entraIDRadioBtn");
			softAssert.assertEquals(
					groupsPage.getTextOfWEPGroupsPage("entraIDHeaderText"),getTextLanguage(LanguageCode, "daas_ui", "group.entra_id"),"Entra ID option is not available");
			LOGGER.info("Verified the Entra ID Option is available and user is able to select it to import the groups");
			softAssert.assertAll();
		}
	
		/**
		 * This method will verify Review page after group selection.
		 * 
		 * @throws Exception
		 */
		@Test(priority = 30, groups = { "REGRESSION_GROUPS"},enabled=false, description="Testcase ID: C57036431")
		public final void verifyReviewPageAfterGroupSelection() throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
			String EntraID_GroupName = getEnvironmentSpecificData(System.getProperty("environment"),"EntraID_GroupName");
			String Membership_Type = getEnvironmentSpecificData(System.getProperty("environment"),"Membership_Type");
			String Selected_GroupName = getEnvironmentSpecificData(System.getProperty("environment"),"Selected_GroupName");
			String Selected_Member_Devices = getEnvironmentSpecificData(System.getProperty("environment"),"Selected_Member_Devices");
			WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			groupsPage.companyView(CommonVariables.GROUPS);
				LOGGER.info("Navigated to Groups successfully");
				sleeper(2000);
			groupsPage.waitForElementsOfGroupsPage("addGroupBtn");
			groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
			waitForPageLoaded();
			groupsPage.waitForElementsOfGroupsPage("entraIDRadioBtn");
			groupsPage.clickOnElementsOfGroupsPage("entraIDRadioBtn");
			groupsPage.clickOnEntraIDNextBtn();
			waitForPageLoaded();
			sleeper(3000);
			groupsPage.waitForElementsOfGroupsPage("dropdownOpen");
			groupsPage.actionClickOfGroupsPage("dropdownOpen");
			sleeper(2000);
			groupsPage.waitForElementsOfGroupsPage("groupSearch");
			groupsPage.actionClickOfGroupsPage("groupSearch");
			sleeper(3000);
			groupsPage.enterTextForGroupsPage("groupSearch",EntraID_GroupName);
			sleeper(2000);
			groupsPage.waitForElementsOfGroupsPage("groupNameDropdown");
			groupsPage.actionClickOfGroupsPage("groupNameDropdown");
			groupsPage.clickOnEntraIDNextBtn();
			waitForPageLoaded();
			groupsPage.waitForElementsOfGroupsPage("membershipTypeCheck");
			softAssert.assertEquals(
					groupsPage.getTextOfWEPGroupsPage("membershipTypeCheck"), Membership_Type, "Fail the Test case if Membership type is not Entra ID");
			groupsPage.waitForElementsOfGroupsPage("selectedMemberDevicesCheck");
			softAssert.assertEquals(
					groupsPage.getTextOfWEPGroupsPage("selectedMemberDevicesCheck"), Selected_Member_Devices, "Fail the Test case if Device count is different");
			groupsPage.waitForElementsOfGroupsPage("selectedGroupCheck");
			softAssert.assertEquals(
					groupsPage.getTextOfWEPGroupsPage("selectedGroupCheck"), Selected_GroupName, "Fail the Test case if different Group is selected");
			LOGGER.info("Verified the Membership Type, Selected Member Device, Selected Group Name while Importing the Group");
			softAssert.assertAll();
		}
	
		/**
		 * This method will verify the devices Count in the Entra ID Group Overview Tab.
		 * 
		 * @throws Exception
		 */
		@Test(priority = 31, groups = {"REGRESSION_GROUPS"}, description="Testcase ID: C57082460")
		public final void verifyDevicesCountInEntraIDGroupOverviewTab() throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
			String ImportedGroupName = getEnvironmentSpecificData(System.getProperty("environment"),"ImportedGroupName");
			String Device_Count_OverviewTab = getEnvironmentSpecificData(System.getProperty("environment"),"Device_Count_OverviewTab");
			WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			groupsPage.companyView(CommonVariables.GROUPS);
				LOGGER.info("Navigated to Groups successfully");
				sleeper(2000);
			groupsPage.waitForElementsOfGroupsPage("enterGroupName");
			sleeper(2000);
			groupsPage.enterTextForGroupsPage("enterGroupName",ImportedGroupName);
			sleeper(2000);
			if(!groupsPage.verifyElementsOfWEPGroupsPage("noResults")) {
			groupsPage.clickOnElementsOfGroupsPage("selectGroupName");
			groupsPage.waitForElementsOfGroupsPage("groupCount");
			softAssert.assertEquals(
					groupsPage.getTextOfWEPGroupsPage("groupCount"),Device_Count_OverviewTab);
			LOGGER.info("Verified the imported device count present under Overview tab");
			softAssert.assertAll();
		}LOGGER.info("There are no records found for the searched group value / Connection not present into the current automation tenant.");
		}
	
	
		/**
		 * This method will verify the devices present in the Entra ID Group Membership Tab.
		 * 
		 * @throws Exception
		 */
		@Test(priority = 32, groups = {"REGRESSION_GROUPS"}, description="Testcase ID: C57082461")
		public final void verifyDevicePresentInEntraIDGroupMembershipTab() throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
			String ImportedGroupName = getEnvironmentSpecificData(System.getProperty("environment"),"ImportedGroupName");
			String Device_Name_MembershipTab = getEnvironmentSpecificData(System.getProperty("environment"),"Device_Name_MembershipTab");
			WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			groupsPage.companyView(CommonVariables.GROUPS);
				LOGGER.info("Navigated to Groups successfully");
				sleeper(2000);
			groupsPage.waitForElementsOfGroupsPage("enterGroupName");
			sleeper(2000);
			groupsPage.enterTextForGroupsPage("enterGroupName",ImportedGroupName);
			sleeper(2000);
			if(!groupsPage.verifyElementsOfWEPGroupsPage("noResults")) {
			groupsPage.clickOnElementsOfGroupsPage("selectGroupName");
			groupsPage.clickOnElementsOfGroupsPage("membershipTab");
			groupsPage.waitForElementsOfGroupsPage("getDeviceSN");
			softAssert.assertEquals(
					groupsPage.getTextOfWEPGroupsPage("getDevice"),Device_Name_MembershipTab);
			LOGGER.info("Verified the imported device name present under Membership tab");
			softAssert.assertAll();
		}
		LOGGER.info("There are no records found for the searched group value / Connection not present into the current automation tenant.");
		}
	
		/**
		 * This method will verify the error message when user not select Entra ID option.
		 * 
		 * @throws Exception
		 */
		@Test(priority = 33, groups = {"REGRESSION_GROUPS","PRODUCTION_GROUPS"}, description="Testcase ID: C57036429")
		public final void verifyErrorMessageWhenEntraIDNotSelected() throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
			String EntraID_ErrorMessage = getEnvironmentSpecificData(System.getProperty("environment"),"EntraID_ErrorMessage");
			WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
			groupsPage.companyView(CommonVariables.GROUPS);
				LOGGER.info("Navigated to Groups successfully");
				sleeper(2000);
			groupsPage.waitForElementsOfGroupsPage("addGroupBtn");
			groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
			groupsPage.clickOnEntraIDNextBtn();
			softAssert.assertEquals(
					groupsPage.getTextOfWEPGroupsPage("entraIDErrorMessage"),EntraID_ErrorMessage);
			LOGGER.info("Verified the Error Message when User not select the Entra ID option");
			softAssert.assertAll();
		}

		/**
		 * This method will verify the Assignment tab after Static Group creation for both toggle ON/OFF
		 * 
		 * @throws Exception
		 */
		@Test(priority = 34, groups = {"REGRESSION_GROUPS"}, description="Testcase ID:C57082321")
		public final void verifyNoAssignmentsForStaticGroupDetailsPage() throws Exception {
			login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
			String SG_GroupName = "Auto_StaticGroup"+generateRandomNumber();
			String SG_GroupDescription = "Static Group is created for"+SG_GroupName;
			WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
			groupsPage.companyView(CommonVariables.GROUPS);
				LOGGER.info("Navigated to Groups successfully");
				sleeper(2000);
			Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb").equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
			LOGGER.info("Groups Header is Matched");
			sleeper(2000);
			groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
			waitForPageLoaded();
			groupsPage.clickOnElementsOfGroupsPage("staticGroupadioBtn");
			waitForPageLoaded();
			String deviceCount = groupsPage.staticGroupCreationDeviceList(SG_GroupName, SG_GroupDescription, 3);
			waitForPageLoaded();
			sleeper(3000);	
			groupsPage.enterTextForGroupsPage("groupNameSearch",SG_GroupName);
			waitForPageLoaded();
			sleeper(3000);
			groupsPage.verifyElementIsPresentOnGroupsPage("groupNameCheck");
			Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameCheck").equals(SG_GroupName),"Failed to create Static Group with Device List");		
			LOGGER.info("Static group creation is done successfully and its showing on Group List page");	
			sleeper(5000);
			groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
			waitForPageLoaded();
			sleeper(3000);
			Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameHeaderTitle").equals(SG_GroupName),"Device Count is not matched");		
			LOGGER.info("Group Name title is verified");
			Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupDescription").equals(SG_GroupDescription),"Device Count is not matched");		
			LOGGER.info("Group description is verified");
			Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameTxt").equals(SG_GroupName),"Device Count is not matched");		
			LOGGER.info("Group Name is verified on overview tab");
			Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("staticGroupMemershipType").toUpperCase().equals(getTextLanguage(LanguageCode, "daas_ui", "group.static_assignment").toUpperCase()),"Groups membershiptype doesn't exists");		
			LOGGER.info("Group membershiptype is verified on overview tab");
			sleeper(3000);
			Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount").equals(deviceCount),"Device Count is not matched");		
			LOGGER.info("Group >>Device Count is matched between Add group Review apge and Overview page");
			//Assignment Tab Verification
			Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("assignmentTab"),"Assignment tab is not getting displayed for newly created group");
			groupsPage.clickOnElementsOfGroupsPage("assignmentTab");
			LOGGER.info("Redirected to Assignment list page successfully");
			Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("noAssignments"),"No Assignments texts are not getting displayed");
			groupsPage.scrollOnGroupsPage("noAssignmentsInnerSection");
			Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("noAssignmentsInnerSection"),"No Assignments Inner section texts are not getting displayed");
			//URL Check after redirection from Assignment List page
			groupsPage.clickOnElementsOfGroupsPage("assignButton");
			groupsPage.waitForElementsOfGroupsPage("policyOption");
			groupsPage.clickOnElementsOfGroupsPage("policyOption");
			waitForPageLoaded();
			String ActurlOfCurrentPage = getUrlOfCurrentPage();
			LOGGER.info("Redirected to Policy list page successfully");
			Assert.assertEquals(ActurlOfCurrentPage,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_ASSIGNMTN_LIST_POLICY_URL"),"URL of the Policy List Page is not matching for New flow");
			LOGGER.info("Scripts list page URL Matched successfully");
			navigateToBack();
			
			groupsPage.clickOnElementsOfGroupsPage("assignmentTab");
			groupsPage.scrollOnGroupsPage("noAssignmentsInnerSection");
			groupsPage.actionClickOfGroupsPage("assignButton");
			groupsPage.actionClickOfGroupsPage("scriptsOption");
			waitForPageLoaded();
			String ActurlScriptsCurrentPage = getUrlOfCurrentPage();
			LOGGER.info("Redirected to Scripts list page successfully");
			Assert.assertEquals(ActurlScriptsCurrentPage,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_ASSIGNMTN_LIST_SCRIPTS_URL"),"URL of the Scripts List Page is not matching for New flow");
			LOGGER.info("Scripts list page URL Matched successfully");
			navigateToBack();
			
		}
		
		/**
		 * This method will verify the Assignment tab after Dynamic Group creation for both toggle ON/OFF
		 * 
		 * @throws Exception
		 */
		@Test(priority = 35, groups = {"REGRESSION_GROUPS","PRODUCTION_GROUPS"},enabled = false, description="Testcase ID:C57082567 ")
		public final void verifyNoAssignmentsForDynamicGroupDetailsPage() throws Exception {
			login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
			WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
			String dynamicGroupType = getTextLanguage(LanguageCode, "daas_ui", "group.dynamic_rules");
			String DG_GroupName = "Auto_DynamicGroup"+generateRandomNumber();
			String DG_GroupDescription = "Group is created by automation";
			String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"Groups_FILTERS_MANUFACTURER_VALUE");
			String propertyInputValue = WEPPGroupsPageVariables.GROUPS_FILTERS_FIELD_MANUFACTURER;
			String operatorValue = WEPPGroupsPageVariables.GROUPS_FILTERS_OPERATOR_EQUALS;
			TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
			WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
			DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
					Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
					LOGGER.info("Active devices filters applied successfully");
					sleeper(2000);
					tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
					tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
					tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
					Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"ManufacturerColumnTitle","manufacturerdropdown","manufacturerListValues","countPage");	
				groupsPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.GROUPS);
					LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
					waitForPageLoaded();
				groupsPage.companyView(CommonVariables.GROUPS);
					LOGGER.info("Navigated to Groups successfully");
				groupsPage.clickOnElementsOfGroupsPage("addGroupBtn");
				waitForPageLoaded();
				Assert.assertTrue(groupsPage.verifyAddGroupsStep1GroupSelectionFlow(dynamicGroupType,LanguageCode,DG_GroupName,DG_GroupDescription),"Add Groups Step1 is getting failed");
				groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
				sleeper(2000);
				List<String> verifyStep2DynamicCreationFlow = groupsPage.verifyStep2DynamicCreationFlow(LanguageCode, propertyInputValue, operatorValue, valueName);
				sleeper(2000);
				Integer AudienceCount = groupsPage.handleCalculateButton();
				Assert.assertEquals(TotalAudience, AudienceCount,"Devices count is not getting matched");
				groupsPage.clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
				List<String> verifyStep3DynamicCreationFlow = groupsPage.verifyStep3DynamicCreationFlow(LanguageCode,DG_GroupName,DG_GroupDescription,0);
				Assert.assertEquals(verifyStep2DynamicCreationFlow, verifyStep3DynamicCreationFlow, "Selected Filters/Values are not matching");
				LOGGER.info("Dynamic group >>Device Count is matched between Add group Review apge and Calculate button device count");
				groupsPage.actionClickOfGroupsPage("addGrpBtn");
				waitForPageLoaded();
				groupsPage.waitForElementsOfGroupsPage("toastNotificationExport");
				groupsPage.getTextOfWEPGroupsPage("toastNotificationExport");
				LOGGER.info("Toaster Message for Group creation:"+groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"));
			    //Assert.assertEquals(groupsPage.getTextOfWEPGroupsPage("toastNotificationExport"),groupsPage.getTextLanguage(LanguageCode, "daas_ui", "groups.dynamic.created.toast.title"),"Dynamic Group Creation is failed");
				LOGGER.info("Dyanmic Group creation is done successfully");
				Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("viewGroups"));
				groupsPage.enterTextForGroupsPage("groupNameSearch",DG_GroupName);
				sleeper(2000);
				groupsPage.verifyElementIsPresentOnGroupsPage("groupNameCheck");
				Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameCheck").equals(DG_GroupName),"Failed to create Dynamic Group");		
				sleeper(2000);		
				waitForPageLoaded();
				LOGGER.info("Dynamic group creation is done successfully and its showing on Group List page");	
				groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
				waitForPageLoaded();
				//sleeper(3000);
				//String overviewTabDeviceCount = groupsPage.getTextOfWEPGroupsPage("overviewTabDeviceCount");
				Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameHeaderTitle").equals(DG_GroupName),"Device Count is not matched");		
				LOGGER.info("Dynamic group Name title is verified");
				Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupDescription").equals(DG_GroupDescription),"Device Count is not matched");		
				LOGGER.info("Dynamic group description is verified");
				Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameTxt").equals(DG_GroupName),"Device Count is not matched");		
				LOGGER.info("Group Name is verified on overview tab");
				Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("dynamicGroupMemershipType").toUpperCase().contains(getTextLanguage(LanguageCode, "daas_ui", "device.groups.dynamic.label").toUpperCase()),"Groups membershiptype doesn't exists");		
				LOGGER.info("Group membershiptype is verified on overview tab");	
			//Assignment Tab Verification
			Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("assignmentTab"),"Assignment tab is not getting displayed for newly created group");
			groupsPage.clickOnElementsOfGroupsPage("assignmentTab");
			LOGGER.info("Redirected to Assignment list page successfully");
			Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("noAssignments"),"No Assignments texts are not getting displayed");
			Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("noAssignmentsInnerSection"),"No Assignments Inner section texts are not getting displayed");
			groupsPage.scrollOnGroupsPage("noAssignmentsInnerSection");
			Assert.assertTrue(groupsPage.verifyElementIsPresentOnGroupsPage("assignButton"),"Assign button is not getting displayed");
			//URL Check after redirection from Assignment List page
			groupsPage.clickOnElementsOfGroupsPage("assignButton");
			groupsPage.clickOnElementsOfGroupsPage("policyOption");
			waitForPageLoaded();
			String ActurlOfCurrentPage = getUrlOfCurrentPage();
			LOGGER.info("Redirected to Policy list page successfully");
			Assert.assertEquals(ActurlOfCurrentPage,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_ASSIGNMTN_LIST_POLICY_URL"),"URL of the Policy List Page is not matching for New flow");
			LOGGER.info("Scripts list page URL Matched successfully");
			navigateToBack();
			groupsPage.scrollOnGroupsPage("noAssignmentsInnerSection");
			groupsPage.clickOnElementsOfGroupsPage("assignButton");
			groupsPage.clickOnElementsOfGroupsPage("scriptsOption");
			waitForPageLoaded();
			String ActurlScriptsCurrentPage = getUrlOfCurrentPage();
			LOGGER.info("Redirected to Scripts list page successfully");
			Assert.assertEquals(ActurlScriptsCurrentPage,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_ASSIGNMTN_LIST_SCRIPTS_URL"),"URL of the Scripts List Page is not matching for New flow");
			LOGGER.info("Scripts list page URL Matched successfully");
			navigateToBack();
		}
		
		/**
		 * This method will verify Groups Page UI elements
		 * @throws Exception
		 */

		  @Test(priority = 46, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
		    public final void verifyGroupsPage() throws Exception {
				login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
				SoftAssert softAssert = new SoftAssert();
				 String testSuiteName = SetTestEnvironments.suiteName;
					if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
						switchUserBasedOnSuite(testSuiteName);
					}
				WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
				waitForPageLoaded();
				if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
					groupsPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.GROUPS);
					LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
					waitForPageLoaded();
					sleeper(2000);
				}else {
					groupsPage.companyView(CommonVariables.GROUPS);
					LOGGER.info("Navigated to Groups successfully");
					sleeper(2000);
				}
				
				groupsPage.verifyElementIsPresentOnGroupsPage("groupAddTitle");	
				groupsPage.clickOnElementsOfGroupsPage("groupAddTitle");
				waitForPageLoaded();
				sleeper(2000);
				softAssert.assertTrue(groupsPage.waitForElementsOfGroupsPage("groupNameTitle"), "Group Name Title is missing");
				sleeper(2000);	
				softAssert.assertTrue(groupsPage.waitForElementsOfGroupsPage("dynamicRules"), "Dynamic Rules option is missing");
				softAssert.assertTrue(groupsPage.waitForElementsOfGroupsPage("staticAssignment"), "Static Assignment option is missing");
				softAssert.assertTrue(groupsPage.waitForElementsOfGroupsPage("entraID"), "Entra ID option is missing");
				softAssert.assertTrue(groupsPage.waitForElementsOfGroupsPage("groupNextButton"), "Next button is missing");								
				softAssert.assertTrue(groupsPage.waitForElementsOfGroupsPage("groupcancelButton"), "Next button is missing");
				sleeper(2000);	
				groupsPage.clickOnElementsOfGroupsPage("groupcancelButton"); 
				LOGGER.info("user should be able to click on cancel button and navigate to groups page");
				sleeper(2000);		
				Assert.assertTrue(groupsPage.verifyGroupDetailNavigation(LanguageCode),"Policy Details page did not load ");
		        sleeper(5000);
		        softAssert.assertTrue(groupsPage.waitForElementsOfGroupsPage("groupDetailsName"), "group Details Name is missing.");      
		        softAssert.assertTrue(groupsPage.waitForElementsOfGroupsPage("groupinformation"), "group information tab missing.");
		        groupsPage.clickOnElementsOfGroupsPage("groupinformation");
		        waitForPageLoaded();
		        sleeper(2000);	
		        softAssert.assertTrue(groupsPage.waitForElementsOfGroupsPage("groupmembership"), "group membership tab missing.");
		        groupsPage.clickOnElementsOfGroupsPage("groupmembership");
		        waitForPageLoaded();
		        sleeper(2000);	
		        softAssert.assertTrue(groupsPage.waitForElementsOfGroupsPage("groupAssignments"), "group Assignments tab missing.");
		        groupsPage.clickOnElementsOfGroupsPage("groupAssignments");
		        waitForPageLoaded();
		        sleeper(2000);	
		        softAssert.assertTrue(groupsPage.waitForElementsOfGroupsPage("groupDelete"), "group Delete button missing.");
		        softAssert.assertTrue(groupsPage.waitForElementsOfGroupsPage("groupExport"), "group export button missing.");			
				softAssert.assertAll();
			
		}
}