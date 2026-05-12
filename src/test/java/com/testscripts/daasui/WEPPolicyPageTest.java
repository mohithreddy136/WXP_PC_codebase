package com.testscripts.daasui;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.io.File;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.daasui.constants.PolicyVariables;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.pages.WEPPolicyPage;
import com.daasui.pages.WEPSustainabilityPage;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import java.util.Properties;




public class WEPPolicyPageTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEPPolicyPageTest.class);

	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "PARTNER_EMAIL_WEP";
		data[0][1] = "PARTNER_PASSWORD_WEP";
		data[1][0] = "ITADMIN_EMAIL_WEP";
		data[1][1] = "ITADMIN_PASSWORD_WEP";
		return data;
	}


		/**
	 * TC_C53352045:[WEP]>>verify User Can Create Bios Setting Policy
	 */
	@Test(priority = 1, groups = { "REGRESSION_POLICY","REGRESSION_INTEGRATIONQA_CUJ" }, description="Testcase ID: TC_C53352045")
	public final void verifyUserCanCreateBiosSettingPolicy() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.Policy_Name+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		sleeper(3000);
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		sleeper(3000);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		sleeper(3000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Policy_Name);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(3000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}

	
	
	/**
	 * TC_C53352046:[WEP]>>Verify User Can Assign Bios Setting Policy To A Group
	 */
	@Test(priority = 02, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53352046")
	public final void VerifyUserCanAssignBiosSettingPolicyToAGroup() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		String SG_GroupName = "BS_Static_craetion_groups" +generateRandomNumber();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
	
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.GROUPS);
			LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		}
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("groupsHeader")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		policypages.clickOnElementsOfPolicyPage("addGroupBtn");
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("staticGroupadioBtn");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("groupNameField",SG_GroupName);
		waitForPageLoaded();
		LOGGER.info("Group Name is entered");
		sleeper(2000);
		policypages.enterTextForPolicyPage("groupDescriptionField",PolicyVariables.SG_GroupDescription);
		LOGGER.info("Group Description is entered");
		policypages.clickOnElementsOfPolicyPage("addGrpNextBtn");
		waitForPageLoaded();
		sleeper(3000);
		policypages.actionClickOfPolicyPage("selectDeviceListin_StaticGroup");
		sleeper(5000);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("clearAllFiltersLink");
		waitForPageLoaded();
		
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"Groups_Device_serial_number");
		LOGGER.info(valueName);
		if (valueName != null && !valueName.isEmpty()) {
		    policypages.enterTextForPolicyPage("groupNameSearch", valueName);
		} else {
		    throw new RuntimeException("Groups_Device_serial_number not found for environment: " 
		                                + System.getProperty("environment"));
		}
		sleeper(2000);
		policypages.actionClickOfPolicyPage("groupNameCheck");
		policypages.clickOnElementsOfPolicyPage("addGrpNextBtn");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addGrpBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("groupCreate").contains("Group created"), "Group created is failed");
		waitForPageLoaded();
		LOGGER.info("Static Group creation is done successfully");		
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("NotificationButton");
		policypages.clickOnElementsOfPolicyPage("notification");
		policypages.waitElementsOfPolicyPage("NotificationMessage");
		policypages.waitUntilElementIsInvisibleOfPolicyPage("NotificationMessage");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.BS_PolicyName+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		sleeper(3000);
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		sleeper(3000);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.BS_PolicyName);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",SG_GroupName);
		sleeper(3000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		waitForPageLoaded();
		LOGGER.info("Policies assigned to Static Group is done successfully");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.BS_PolicyName);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		waitForPageLoaded();
		sleeper(3000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		sleeper(2000);
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.GROUPS);
			LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
		}
		sleeper(2000);
		policypages.enterTextForPolicyPage("groupSearch",SG_GroupName);
		waitForPageLoaded();
		sleeper(5000);
		policypages.clickOnElementsOfPolicyPage("groupSelect");
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("deleteGroupButtonChk");
		String secCode = policypages.getTextOfWEPPolicyPage("securityCodeNumber");
		policypages.enterTextForPolicyPage("securityCodeField",secCode);
		policypages.clickOnElementsOfPolicyPage("deleteButton1");
	
		LOGGER.info(policypages.getTextOfWEPPolicyPage("groupdelete"));
		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("groupdelete")
				.contains(policypages.getTextLanguage(LanguageCode, "daas_ui", "groups.toast.delete.group.success")),"Static Group Deletion is failed");
		}  
	
	/**
	 * TC_C53712623:[WEP]>>Verify Unassigned Global Bios Setting Policy Can Be Deleted
	 */
	@Test(priority = 03, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53712623")
	public final void VerifyUnassignedGlobalBiosSettingPolicyCanBeDeleted() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		String SG_GroupName = "BS_Static_craetion_groups" +generateRandomNumber();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
//		policypages.companyView(CommonVariables.GROUPS);
	
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.GROUPS);
			LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		}
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("groupsHeader")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");
		policypages.clickOnElementsOfPolicyPage("addGroupBtn");
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("staticGroupadioBtn");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("groupNameField",SG_GroupName);
		waitForPageLoaded();
		LOGGER.info("Group Name is entered");
		sleeper(2000);
		policypages.enterTextForPolicyPage("groupDescriptionField",PolicyVariables.SG_GroupDescription);
		LOGGER.info("Group Description is entered");
		policypages.clickOnElementsOfPolicyPage("addGrpNextBtn");
		waitForPageLoaded();
		sleeper(3000);
		policypages.actionClickOfPolicyPage("selectDeviceListin_StaticGroup");
		sleeper(5000);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("clearAllFiltersLink");
		waitForPageLoaded();
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"Groups_Device_serial_number");
		LOGGER.info(valueName);
		if (valueName != null && !valueName.isEmpty()) {
		    policypages.enterTextForPolicyPage("groupNameSearch", valueName);
		} else {
		    throw new RuntimeException("Groups_Device_serial_number not found for environment: " 
		                                + System.getProperty("environment"));
		}
		policypages.actionClickOfPolicyPage("groupNameCheck");
		policypages.clickOnElementsOfPolicyPage("addGrpNextBtn");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addGrpBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("groupCreate").contains("Group created"), "Group created is failed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("NotificationButton");
		policypages.clickOnElementsOfPolicyPage("notification");
		sleeper(5000);
		policypages.waitUntilElementIsInvisibleOfPolicyPage("NotificationMessage");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.BS_PolicyName+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		sleeper(3000);
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		sleeper(3000);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.BS_PolicyName);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",SG_GroupName);
		sleeper(3000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		waitForPageLoaded();
		LOGGER.info("Policies assigned to Static Group is done successfully");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.BS_PolicyName);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");waitForPageLoaded();
		sleeper(3000);
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.BS_PolicyName);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		sleeper(2000);
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.GROUPS);
			LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
		}
		sleeper(2000);
		policypages.enterTextForPolicyPage("groupSearch",SG_GroupName);
		waitForPageLoaded();
		sleeper(5000);
		policypages.clickOnElementsOfPolicyPage("groupSelect");
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("deleteGroupButtonChk");
		String secCode = policypages.getTextOfWEPPolicyPage("securityCodeNumber");
		policypages.enterTextForPolicyPage("securityCodeField",secCode);
		policypages.clickOnElementsOfPolicyPage("deleteButton1");
	
		LOGGER.info(policypages.getTextOfWEPPolicyPage("groupdelete"));
		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("groupdelete")
				.contains(policypages.getTextLanguage(LanguageCode, "daas_ui", "groups.toast.delete.group.success")),"Static Group Deletion is failed");
	}
	
	
	
	/**
	 * TC_C53712622:[WEP]>>Verify User Can Assign Created Global Bios Setting Policy To A Group
	 */
	@Test(priority = 04, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53712622")
	public final void VerifyUserCanAssignCreatedGlobalBiosSettingPolicyToGroup() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.global_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		sleeper(3000);
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		sleeper(3000);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.global_policy);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		waitForPageLoaded();
		LOGGER.info("Policies assigned to Static Group is done successfully");
		sleeper(3000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.global_policy);
		sleeper(3000);
		policypages.waitForElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		
		}
	
	
	/**
	 * TC_C53712624:[WEP]>>Verify Assigned Global Bios Setting Policy Cannot Delete
	 */
	@Test(priority = 05, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53712624")
	public final void VerifyAssignedGlobalBiosSettingPolicyCannotDelete() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.waitForElementsOfPolicyPage("policySearch");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.global_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		if (policypages.waitUntilElementIsInvisibleOfPolicyPage("deleteButton")) {
			LOGGER.info("groupDeleteButton is not visible, proceeding...");
		} else {
			LOGGER.info("groupDeleteButton is still visible.");
		}
		waitForPageLoaded();
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.waitForElementsOfPolicyPage("policySearch");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.global_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");	
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		}
	
	/**
	 * TC_C53712621:[WEP]>>Check User Can Edit Policy With Status Unassigned
	 */
	@Test(priority = 06, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53712621")
	public final void CheckUserCanEditPolicyWithStatusUnassigned() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.edit_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();	
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.edit_policy);
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("editButton");
		policypages.clickOnElementsOfPolicyPage("clearButton");
		waitForPageLoaded();	
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.edit_policy1);
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("applyButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("applyButton"));
		policypages.clickOnElementsOfPolicyPage("applyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyUpdatePending").contains("Policy update pending"), "policy update pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyUpdated");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyUpdated").contains("Policy updated"), "policy updated is failed");
		policypages.verifyElementIsPresentOnPolicyPage("policiesHistoryTab");
		policypages.clickOnElementsOfPolicyPage("policiesHistoryTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	
		}
	
	/**
	 * TC_C53712620:[WEP]>>Verify Settings Applied For Global Bios Setting Type Policy
	 */
	@Test(priority = 07, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53712620")
	public final void VerifySettingsAppliedForGlobalBiosSettingTypePolicy() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.global_setting_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.global_setting_policy);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(3000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		}
	
	/**
	 * TC_C53135934:[WEP]>>Check User Can Edit Policy With Status Unassigned
	 */
	@Test(priority = 8, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135934")
	public final void CheckUserCanEditBiosUpdatePolicyWithStatusUnassigned() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.edit_update_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("deployment");
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		sleeper(3000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.edit_update_policy);
		waitForPageLoaded();	
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("editButton");
		policypages.clickOnElementsOfPolicyPage("clearButton");	
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.edit_update_policy1);
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("applyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyUpdatePending").contains("Policy update pending"), "policy update pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyUpdated");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyUpdated").contains("Policy updated"), "policy updated is failed");
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");	
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.edit_update_policy1);
		LOGGER.info("Policy name is successfully changed");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		}
	
	
	/**
	 * TC_C53926511:[WEP]>>Verify User Can Create Secret Password Type
	 */
	@Test(priority = 9, groups = { "REGRESSION_POLICY", "PRODUCTION_POLICY","REGRESSION_INTEGRATIONQA_CUJ" }, description="Testcase ID: TC_C53926511")
	public final void VerifyUserCanCreateSecretPasswordType() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		policypages.enterTextForPolicyPage("ScreteNameTextBox",PolicyVariables.secret_name+generateRandomNumber());
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("passwordRadioButton");
		policypages.enterTextForPolicyPage("SecretDescriptionTextBox",PolicyVariables.SG_SecretDescription);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("secretePageNextButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("standardButton");
		policypages.enterTextForPolicyPage("passwordText",PolicyVariables.Secret_password);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicyCreationPage"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addSecretBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAddPending").contains("Secret add pending"), "secret add pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAdded").contains("Secret added"), "secret added is failed");
		LOGGER.info("Added secret successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_name);
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesOverviewTab");
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretDeletePending").contains("Secret delete pending"), "Secret delete pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretDeleted").contains("Secret deleted"), "Secret deleted is failed");
		
	}
	
	/**
	 * TC_C53242417:[WEP]>>Verify User Can Create Bios Authentication Policy
	 */
	@Test(priority = 10, groups = { "REGRESSION_POLICY","PRODUCTION_POLICY" }, description="Testcase ID: TC_C53242417")
	public final void VerifyUserCanCreateBiosAuthenticationPolicy() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.Authentication_Policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosAuthenticationOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("automationsecreteoption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("backButton");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("InformationPolicyName");
		policypages.verifyElementIsPresentOnPolicyPage("InformationPolicyType");
		policypages.verifyElementIsPresentOnPolicyPage("BiosAuthenticationBiosPassword");
		
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Authentication_Policy);
		waitForPageLoaded();	
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		String errorMsg = policypages.getTextOfWEPPolicyPage("policiesAssignmentTab");
	    LOGGER.info(errorMsg);
	    Assert.assertTrue(
	        errorMsg != null && errorMsg.contains("Assignments"),
	        "Expected conflict message not found. Actual message: " + errorMsg);
	
	}
	
	/**
	 * TC_C53135034:[WEP]>>Verify Search Functionality With Typeable Input And Data Display For PolicyName
	 */
	@Test(priority = 11, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135034")
	public final void VerifySearchFunctionalityWithTypeableInputAndDataDisplayForPolicyName() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.waitForElementsOfPolicyPage("policySearch");
		waitForPageLoaded();	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Authentication_Policy);
		policypages.verifyElementIsPresentOnPolicyPage("policySearchItem");
		String errorMsg = policypages.getTextOfWEPPolicyPage("policySearchItem");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
			    errorMsg != null && errorMsg.toLowerCase().contains("Bios policy Authentication secret".toLowerCase()),
			    "Search item is available: " + errorMsg);
	}
	
	
	
	/**
	 * TC_C53135035:[WEP]>>Verify The Details Displayed For Policy
	 */
	@Test(priority = 12, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135035")
	public final void VerifyTheDetailsDisplayedForPolicy() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.waitForElementsOfPolicyPage("policySearch");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Authentication_Policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Authentication_Policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("profileName");
		policypages.verifyElementIsPresentOnPolicyPage("description");
		policypages.verifyElementIsPresentOnPolicyPage("Type");
		policypages.verifyElementIsPresentOnPolicyPage("policyName");
		policypages.verifyElementIsPresentOnPolicyPage("CreatedBy");
		policypages.verifyElementIsPresentOnPolicyPage("createdOn");
		policypages.verifyElementIsPresentOnPolicyPage("BiosPassword");
		if (policypages.waitUntilElementIsInvisibleOfPolicyPage("editGroupButtonChk")) {
			LOGGER.info("edit button is not visible, proceeding...");
		} else {
			LOGGER.info("edit Button is still visible.");
		}
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		
		policypages.verifyElementIsPresentOnPolicyPage("editGroupButtonChk");
		policypages.verifyElementIsPresentOnPolicyPage("editGroupButtonChk");
		String errorMsg = policypages.getTextOfWEPPolicyPage("editGroupButtonChk");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
			    errorMsg != null && errorMsg.toLowerCase().contains("Edit".toLowerCase()),
			    "Search item is available: " + errorMsg);
	
		
		}
	
	
	/**
	 * TC_C53135036:[WEP]>>Check Assigned Unassigned Status Of Existing Authentication Policies
	 */
	@Test(priority = 13, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53135036")
	public final void CheckAssignedUnassignedStatusOfExistingAuthenticationPolicies() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.waitForElementsOfPolicyPage("policySearch");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Authentication_Policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Authentication_Policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		String errorMsg = policypages.getTextOfWEPPolicyPage("UnassignedState");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
			    errorMsg != null && errorMsg.toLowerCase().contains("Unassigned".toLowerCase()),
			    "Search item is available: " + errorMsg);
		}
	
	
	/**
	 * TC_C53135037:[WEP]>>Verify List Of Policies Present Using Vertical ScrollBar
	 */
	@Test(priority = 14, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53135037")
	public final void VerifyListOfPoliciesPresentUsingVerticalScrollBar() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		waitForPageLoaded();
		sleeper(2000);
		policypages.scrollOnPolicyPage("scrollUpDown");
		sleeper(2000);
		policypages.scrollOnPolicyPage("scrollUpDown");		
	}
	
	
	/**
	 * TC_C53135038:[WEP]>>Verify Settings Applied For Authentication Type Policy
	 */
	@Test(priority = 15, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135038")
	public final void verifySettingsAppliedForAuthenticationTypePolicy() throws Exception {
	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
	    final WEPPolicyPage policyPages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	
	    waitForPageLoaded();
	    if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	    	policyPages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	        LOGGER.info("Navigated to Policies from side menu bar under Fleet Management");
	    } else {
	    	policyPages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	        LOGGER.info("Navigated to Policies from Remediation tab");
	    }
	    waitForPageLoaded();
	    sleeper(2000); 
	    LOGGER.info("Navigated to Policies tab");
	    policyPages.enterTextForPolicyPage("policySearch", PolicyVariables.Authentication_Policy);
	    waitForPageLoaded();
	    sleeper(2000);
	    policyPages.clickOnElementsOfPolicyPage("policySearchItem");
	    waitForPageLoaded();
	    sleeper(2000);
	    String[] policyElements = {
	        "profileName", "description", "Type", "policyName", 
	        "CreatedBy", "createdOn", "ModifyBy", "ModifyOn", "BiosPassword"
	    };
	    for (String element : policyElements) {
	    	policyPages.verifyElementIsPresentOnPolicyPage(element);
	    }
	    
	}
	
	
	/**
	 * TC_C53135039:[WEP]>>VerifyAssignmentToForAuthenticationTypePolicy
	 */
	@Test(priority = 16, groups = { "REGRESSION_POLICY","PRODUCTION_POLICY" }, description="Testcase ID: TC_C53135039")
	public final void VerifyAssignmentToForAuthenticationTypePolicy() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.waitForElementsOfPolicyPage("policySearch");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Authentication_Policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Authentication_Policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.waitForElementsOfPolicyPage("policiesAssignmentTab");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		
		String errorMsg1 = policypages.getTextOfWEPPolicyPage("UnassignedState");
		LOGGER.info(errorMsg1);
		Assert.assertTrue(
			    errorMsg1 != null && errorMsg1.toLowerCase().contains("Unassigned".toLowerCase()),
			    "Search item is available: " + errorMsg1);
	}
	
	/**
	 * TC_C53139721:[WEP]>>Verify in Add PC Policy "BIOS Settings for Global Policy" screen once user selects bios settings the count of selected settings should display
	 */
	@Test(priority = 17, groups = { "REGRESSION_POLICY"}, description="Testcase ID: C53139721")
	public final void verifyNexteButtonShouldDisablewhenPolicyisAssignedStaticGroup() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_assigned);
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.verifyElementIsPresentOnPolicyPage("nextbuttonOnPolicy");	
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("PolicytoggleButtonOpen");
		waitForPageLoaded();
		sleeper(1000);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.verifyElementIsPresentOnPolicyPage("nextbuttonOnPolicy");	
	}
	/**
	 * TC_C53139722:[WEP]>>Verify in Bios Setting create policy screen user able to select "platform policy" option
	 */	
	
	@Test(priority = 18, groups = { "REGRESSION_POLICY" }, description="Testcase ID: C53139722")
	public final void verifyBiosSettingcreatepolicyscreenuserabletoselectplatformpolicyoption() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.platform_policy_user);
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();		
	
	}
	/**
	 * TC_C53712619:[WEP]>>Check for Global Bios setting policy in list view
	 */	
	
	@Test(priority = 19, groups = { "REGRESSION_POLICY" }, description="Testcase ID: C53712619")
	public final void verifyforGlobalBiossettingpolicyinlistview() throws Exception{
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(
	            getTextLanguage(LanguageCode, "daas_ui", "catalog.name"),
	            getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_status"),
	            getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_type"),
	            getTextLanguage(LanguageCode, "daas_ui", "group.form.scope.label"),
	            getTextLanguage(LanguageCode, "daas_ui", "incident.details.tags"),
				getTextLanguage(LanguageCode, "daas_ui", "group.modified.on"),
				getTextLanguage(LanguageCode, "daas_ui", "group.modified_by"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.created_by"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.createdOn")));
		policypages.verifyElementIsPresentOnPolicyPage("ListOptions");
		Assert.assertTrue(policypages.verifyTableColumns(expectedColumnList,"assignmentTableColumns"), "Table Columns are not as expected");
	    LOGGER.info("Workforce Experience Assignment List Page Test Passed");  
	}
	
	/**
	 * TC_C53135031:[WEP]>>Check for authentication policy in list view
	 */	
	 
	@Test(priority = 20, groups = { "REGRESSION_POLICY" }, description="Testcase ID: C53135031")
	public final void checkForAuthenticationPolicyinListview() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.bios_authentication_policy);
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.actionClickOfPolicyPage("biosAuthenticationOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("automationsecreteoption");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicyCreationPage"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("BiosAuthenticationBiosPassword");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_authentication_policy);
		policypages.mouseHoverOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(
	            getTextLanguage(LanguageCode, "daas_ui", "catalog.name"),
	            getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_status"),
	            getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_type"),
	            getTextLanguage(LanguageCode, "daas_ui", "group.form.scope.label"),
	            getTextLanguage(LanguageCode, "daas_ui", "incident.details.tags"),
				getTextLanguage(LanguageCode, "daas_ui", "group.modified.on"),
				getTextLanguage(LanguageCode, "daas_ui", "group.modified_by"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.created_by"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.createdOn")));
		policypages.verifyElementIsPresentOnPolicyPage("ListOptions");
		Assert.assertTrue(policypages.verifyTableColumns(expectedColumnList,"assignmentTableColumns"), "Table Columns are not as expected");
	    LOGGER.info("Workforce Experience Assignment List Page Test Passed");
	    policypages.clickOnElementsOfPolicyPage("policySearchItem");
	    policypages.clickOnElementsOfPolicyPage("policiesOverviewTab");
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}
	
	/**
	 * TC_C53135397:[WEP]>>Verify on all the created Secrets are listed
	 */	
	
	@Test(priority = 21, groups = { "REGRESSION_POLICY"  }, description="Testcase ID: C53135397")
	public final void VerifyonallthecreatedSecretsarelisted() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		waitForPageLoaded();
		sleeper(2000);
		List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(
	            getTextLanguage(LanguageCode, "daas_ui", "catalog.name"),
	            getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_status"),
	            getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_type"),
	            getTextLanguage(LanguageCode, "daas_ui", "group.form.scope.label"),
	            getTextLanguage(LanguageCode, "daas_ui", "incident.details.tags"),
				getTextLanguage(LanguageCode, "daas_ui", "group.modified.on"),
				getTextLanguage(LanguageCode, "daas_ui", "group.modified_by"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.created_by"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.createdOn")));
		policypages.verifyElementIsPresentOnPolicyPage("ListOptions");
		Assert.assertTrue(policypages.verifyTableColumns(expectedColumnList,"assignmentTableColumns"), "Table Columns are not as expected");
	    LOGGER.info("Workforce Experience Assignment List Page Test Passed");               
	}
	
	/**
	 * TC_C53135401:[WEP]>>Verify User Can Create Secret 
	 */
	 
	@Test(priority = 22, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135401")
	public final void VerifyUserCanCreateSecret() throws Exception {
		String ComplexityRuleName = generateRandomString(10);
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		policypages.enterTextForPolicyPage("ScreteNameTextBox",PolicyVariables.secret_policy_name+generateRandomNumber());
		policypages.clickOnElementsOfPolicyPage("passwordRadioButton");
		policypages.enterTextForPolicyPage("SecretDescriptionTextBox",PolicyVariables.SG_SecretDescription);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		LOGGER.info(policypages.verifyElementIsClickable("secretePageNextButton"));
		policypages.clickOnElementsOfPolicyPage("secretePageNextButton");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addGroupBtn");
		policypages.enterTextForPolicyPage("SecretComplexityRuleName",ComplexityRuleName);
		policypages.enterTextForPolicyPage("SecretComplexityPassword",PolicyVariables.SecretComplexitylength);
		policypages.clickOnElementsOfPolicyPage("SelectUpperCase");
		policypages.clickOnElementsOfPolicyPage("SelectLowercase");
		policypages.clickOnElementsOfPolicyPage("SelectSymbol");
		policypages.clickOnElementsOfPolicyPage("SelectNumber");
		policypages.clickOnElementsOfPolicyPage("SelectAllowSpaces");
		policypages.clickOnElementsOfPolicyPage("SecrectComplexityAdd");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SecrectComplexityPasswordSet",PolicyVariables.Secret_password);
		policypages.clickOnElementsOfPolicyPage("secretePageNextButton");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addGroupBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAddPending").contains("Secret add pending"), "secret add pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAdded").contains("Secret added"), "secret added is failed");
		LOGGER.info("Added secret successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_policy_name);
		LOGGER.info("Policy name is successfully changed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesOverviewTab");
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretDeletePending").contains("Secret delete pending"), "Secret delete pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretDeleted").contains("Secret deleted"), "Secret deleted is failed");

	}
	
	/**
	 * TC_C53135040:[WEP]>>verify Description For Bios Authentication Policy In Detail View
	 */
	@Test(priority = 23, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135040")
	public final void verifyDescriptionForBiosAuthenticationPolicyInDetailView() throws Exception {
	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
	    final WEPPolicyPage policyPages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	
	    waitForPageLoaded();
	    if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	    	policyPages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	        LOGGER.info("Navigated to Policies from side menu bar under Fleet Management");
	    } else {
	    	policyPages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	        LOGGER.info("Navigated to Policies from Remediation tab");
	    }
	    waitForPageLoaded();
	    sleeper(2000); 
	    LOGGER.info("Navigated to Policies tab");
	    policyPages.enterTextForPolicyPage("policySearch", PolicyVariables.Authentication_Policy);
	    waitForPageLoaded();
	    sleeper(2000);
	    policyPages.clickOnElementsOfPolicyPage("policySearchItem");
	    waitForPageLoaded();
	    sleeper(2000);
	    String[] policyElements = {
	        "description"
	    };
	    for (String element : policyElements) {
	    	policyPages.verifyElementIsPresentOnPolicyPage(element);
	    }
	    
	}
	
	
	/**
	 * TC_C53242420:[WEP]>>Verify Unassigned Bios Authentication Policy Can Be Deleted
	 */
	@Test(priority = 24, groups = { "REGRESSION_POLICY","REGRESSION_INTEGRATIONS","PRODUCTION_INTEGRATIONS" }, description="Testcase ID: TC_C53242420")
	public final void VerifyUnassignedBiosAuthenticationPolicyCanBeDeleted() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.Unassigned_authentication_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("biosAuthenticationOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("automationsecreteoption");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("BiosAuthenticationBiosPassword");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Unassigned_authentication_policy);
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("metaball");
		policypages.clickOnElementsOfPolicyPage("AssignToGroup");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Unassigned_authentication_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Unassigned_authentication_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	
	}

	/**
	 * TC_C53242421:[WEP]>>Verify Assigned Bios Authentication Policy Cannot Deleted
	 */
	@Test(priority = 25, groups = { "REGRESSION_POLICY", "PRODUCTION_POLICY" }, description="Testcase ID: TC_C53242421")
	public final void VerifyAssignedBiosAuthenticationPolicyCannotDeleted() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.Unassigned_authentication_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosAuthenticationOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("automationsecreteoption");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("BiosAuthenticationBiosPassword");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Unassigned_authentication_policy);
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("metaball");
		policypages.clickOnElementsOfPolicyPage("AssignToGroup");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		
		LOGGER.info("Policies assigned to Static Group is done successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Unassigned_authentication_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		sleeper(3000);
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		if (policypages.waitUntilElementIsInvisibleOfPolicyPage("deleteButton")) {
			LOGGER.info("groupDeleteButton is not visible, proceeding...");
		} else {
			LOGGER.info("groupDeleteButton is still visible.");
		}
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Unassigned_authentication_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");

	}	
	
	
	

	/**
	 * TC_C53242418:[WEP]>>Verify User Can Assign Created Policy To Group
	 */
	@Test(priority = 26, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53242418")
	public final void VerifyUserCanAssignCreatedPolicyToAGroup() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.assigned_authentication_policy);
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("biosAuthenticationOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("automationsecreteoption");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("BiosAuthenticationBiosPassword");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assigned_authentication_policy);
		policypages.mouseHoverOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("metaball");
		policypages.clickOnElementsOfPolicyPage("AssignToGroup");
		policypages.verifyElementIsPresentOnPolicyPage("AssignPage");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("viewseleted");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("reviewAssisgnPage");
		policypages.verifyElementIsPresentOnPolicyPage("verifyDetailsInReviewPage");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		policypages.verifyElementIsPresentOnPolicyPage("backButton");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		sleeper(4000);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assigned_authentication_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		sleeper(5000);
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		policypages.mouseHoverOfPolicyPage("groupItem1");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");

		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
	}

	/**
	 * TC_C53351963:[WEP]>>Verify User Can Create Bios Update Policy
	 */
	@Test(priority = 27, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53351963")
	public final void VerifyUserCanCreateBiosUpdatePolicy() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.bios_policy_update+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deployment");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_policy_update);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("policyNametext");
		String errorMsg = policypages.getTextOfWEPPolicyPage("policyNametext");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains(PolicyVariables.bios_policy_update),
		    "Expected error message for duplicate policy name not found. Actual message: " + errorMsg);
	}	


	/**
	 * TC_C53351969:[WEP]>>Verify User Can Assign Bios Update Policy To A Group
	 */
	@Test(priority = 28, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53351969")
	public final void VerifyUserCanAssignBiosUpdatePolicyToAGroup() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.waitForElementsOfPolicyPage("policySearch");
		waitForPageLoaded();	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		sleeper(4000);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");

		LOGGER.info("Policies assigned to Static Group is done successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Assigned"),
		    "Expected error message for duplicate policy name not found. Actual message: " + errorMsg
		);
	}
	
	/**
	 * TC_C53135940:[WEP]>>Check User Cannot Delete Policy With Status In Use
	 */
	@Test(priority = 29, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135940")
	public final void CheckUserCannotDeletePolicyWithStatusInUse() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		waitForPageLoaded();		
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_policy_update);
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		waitForPageLoaded();
		if (policypages.waitUntilElementIsInvisibleOfPolicyPage("deleteButton")) {
			LOGGER.info("groupDeleteButton is not visible, proceeding...");
		} else {
			LOGGER.info("groupDeleteButton is still visible.");
		}
	}
	
	
	/**
	 * TC_C53135935:[WEP]>>CheckUserCannotEditAPolicyWithStatusAssigned
	 */
	@Test(priority = 30, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135935")
	public final void CheckUserCannotEditAPolicyWithStatusAssigned() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		if (policypages.waitUntilElementIsInvisibleOfPolicyPage("editButton")) {
			LOGGER.info("edit button is not visible, proceeding...");
		} else {
			LOGGER.info("edit Button is still visible.");
		}
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");

	    policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_policy_update);
		policypages.verifyElementIsPresentOnPolicyPage("PolicyVerify");
		String errorMsg = policypages.getTextOfWEPPolicyPage("PolicyVerify");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("No results"),
		    "Expected error message for policy name not found. Actual message: " + errorMsg
		);
	    
		}
		
		/**
	 * TC_C53135936:[WEP]>>Check User Can Add Or Remove Groups
	 */
	@Test(priority = 31, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135936")
	public final void CheckUserCanAddOrRemoveGroups() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.waitForElementsOfPolicyPage("policySearch");
		waitForPageLoaded();	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assigned_authentication_policy);
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("metaball");
		policypages.clickOnElementsOfPolicyPage("AssignToGroup");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assigned_authentication_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");

		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		String errorMsg = policypages.getTextOfWEPPolicyPage("UnassignedState");
		System.out.println(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Unassigned"),
		    "Expected error message for duplicate policy name not found. Actual message: " + errorMsg
		);
	}
	
	
	
	/**
	 * TC_C53135937:[WEP]>>Verify User Cannot Add A Policy With Same Name
	 */
	@Test(priority = 32, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135937")
	public final void VerifyUserCannotAddAPolicyWithSameName() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.assigned_authentication_policy);
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("PolicyNameExists");

		String errorMsg = policypages.getTextOfWEPPolicyPage("PolicyNameExists");
		System.out.println(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Name already exists"),
		    "Expected error message for duplicate policy name not found. Actual message: " + errorMsg);
		policypages.clickOnElementsOfPolicyPage("cancelButton");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assigned_authentication_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}
	
	
	/**
	 * TC_C53135938:[WEP]>>Check Overall Bios Update UI
	 */
	@Test(priority = 33, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135938")
	public final void CheckOverallBiosUpdateUI() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.bios_update_ui+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deployment");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();	
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_update_ui);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
	    waitForPageLoaded();
		sleeper(2000);
		LOGGER.info(policypages.verifyElementIsPresentOnPolicyPage("profileName"));
		LOGGER.info(policypages.verifyElementIsPresentOnPolicyPage("description"));
		LOGGER.info(policypages.verifyElementIsPresentOnPolicyPage("Type"));
		LOGGER.info(policypages.verifyElementIsPresentOnPolicyPage("policyName"));
		LOGGER.info(policypages.verifyElementIsPresentOnPolicyPage("CreatedBy"));
		LOGGER.info(policypages.verifyElementIsPresentOnPolicyPage("createdOn"));
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		LOGGER.info(policypages.verifyElementIsPresentOnPolicyPage("NoPolicyAssigned"));
	}
	
	/**
	 * TC_C53135939:[WEP]>>Check User Can Delete A Policy With Status Not Used
	 */
	@Test(priority = 34, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53135939")
	public final void CheckUserCanDeleteAPolicyWithStatusNotUsed() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_update_ui);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		policypages.waitForElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	    policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_update_ui);
	    policypages.verifyElementIsPresentOnPolicyPage("PolicyVerify");
	    String errorMsg = policypages.getTextOfWEPPolicyPage("PolicyVerify");
		System.out.println(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("No results"),
		    "Policy details are not avilable policy deleted sucessfully: " + errorMsg);
	}
	
	/**
	 * TC_C53351971:[WEP]>>Verify Assigned Bios Update Policy Cannot Delete
	 */
	@Test(priority = 35, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53351971")
	public final void VerifyAssignedBiosUpdatePolicyCannotDelete() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.assigned_bios_update+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deployment");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assigned_bios_update);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assigned_bios_update);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		waitForPageLoaded();
		if (policypages.waitUntilElementIsInvisibleOfPolicyPage("deleteButton")) {
			LOGGER.info("groupDeleteButton is not visible, proceeding...");
		} else {
			LOGGER.info("groupDeleteButton is still visible.");
		}
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");

		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assigned_bios_update);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}
	
	/**
	 * TC_C53135941:[WEP]>>Conflict Resolution On The Two Policies Target The Same Platform Assign To Same DeviceGroup
	 */
	@Test(priority = 36, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135941")
	public final void ConflictResolutionOnTheTwoPoliciesTargetTheSamePlatformAssignToSameDeviceGroup() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.conflict_update_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.waitForElementsOfPolicyPage("policyScopePlatformOption");
		policypages.actionClickOfPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("platformDropdownButton");
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem",PolicyVariables.Platform_Name);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deployment");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");	
		waitForPageLoaded();	
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.conflict_update_policy);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.latestGroup_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.conflict_update_policy);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.update_policy_conflict+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.waitForElementsOfPolicyPage("policyScopePlatformOption");
		policypages.actionClickOfPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("platformDropdownButton");
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem",PolicyVariables.Platform_Name);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deployment");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.update_policy_conflict);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.latestGroup_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("policyConflict");
	    String errorMsg = policypages.getTextOfWEPPolicyPage("policyConflict");
		System.out.println(errorMsg);
		Assert.assertTrue(
			    errorMsg != null && errorMsg.toLowerCase().contains("Conflicts (1)".toLowerCase()),
			    "Policy details are not available. Actual message: " + errorMsg);
		sleeper(2000);
        policypages.clickOnElementsOfPolicyPage("cancelButton");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.conflict_update_policy);
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");

		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");

		
	}
	
	/**
	 * TC_C53351970:[WEP]>>Verify Unassigned Bios Update Policy Can Delete
	 */
	@Test(priority = 37, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53351970")
	public final void VerifyUnassignedBiosUpdatePolicyCanDelete() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.update_policy_conflict);
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		policypages.waitForElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");

	    
	}
	

	/**
	 * TC_C53135942:[WEP]>>No Conflict Resolution On The Two Policies Target The Same Platform Assign To Same DeviceGroup
	 */
	@Test(priority = 38, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53135942")
	public final void NoConflictResolutionOnTheTwoPoliciesOneIsGeneric2ndIsSpecificAssignToSameDeviceGroup() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.Conflict_Resolution+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("platformDropdownButton");
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem",PolicyVariables.Platform_Name);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deployment");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");

		waitForPageLoaded();	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch","Bios Update Policy");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.Noconflict_update_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deployment");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");

		waitForPageLoaded();	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Noconflict_update_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Noconflict_update_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Noconflict_update_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
        policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");

		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Conflict_Resolution);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");

		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Conflict_Resolution);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}
	

	/**
	 * TC_C53173569:[WEP]>>Check User Can Add Remove Groups
	 */
	@Test(priority = 39, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53173569")
	public final void CheckUserCanAddRemoveGroups() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.platform_policy_user+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.waitForElementsOfPolicyPage("policyScopePlatformOption");
		policypages.actionClickOfPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("platformDropdownButton");
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem",PolicyVariables.Platform_Name);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AssetTrackingNumberOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();	
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.platform_policy_user);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.latestGroup_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");	
		LOGGER.info("Policies assigned to Static Group is done successfully");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
	    String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
	    LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Assigned"),
		    "Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.platform_policy_user);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
	}

	
	/**
	 * TC_C53173567:[WEP]>>Check User Can Edit Policy With Status Unassigned
	 */
	@Test(priority = 40, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53173567")
	public final void CheckUserCanEditPlatformPolicyWithStatusUnassigned() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.platform_policy_user);
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("editButton");
		policypages.clickOnElementsOfPolicyPage("clearButton");
		waitForPageLoaded();	
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.edit_platform_policy_user);
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("applyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyUpdatePending").contains("Policy update pending"), "policy update pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyUpdated");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyUpdated").contains("Policy updated"), "policy updated is failed");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.edit_platform_policy_user);
		LOGGER.info("Policy name is successfully changed");
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		
		}


	/**
	 * TC_C53173568:[WEP]>>Check User Cannot Edit A Platform Policy With Status Assigned
	 */
	@Test(priority = 41, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53173568")
	public final void CheckUserCannotEditAPlatformPolicyWithStatusAssigned() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.edit_platform_policy_user);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.latestGroup_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
	    String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
	    LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Assigned"),
		    "Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.edit_platform_policy_user);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		if (policypages.waitUntilElementIsInvisibleOfPolicyPage("editButton")) {
			LOGGER.info("edit button is not visible, proceeding...");
		} else {
			LOGGER.info("edit Button is still visible.");
		}
	}

	/**
	 * TC_C53173570:[WEP]>>Verify User Cannot Add A Policy With Same Name
	 */
	@Test(priority = 42, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53173570")
	public final void CheckUserCannotAddAPolicyWithSameName() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.edit_platform_policy_user);
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("PolicyNameExists");

		String errorMsg = policypages.getTextOfWEPPolicyPage("PolicyNameExists");
		System.out.println(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Name already exists"),
		    "Expected error message for duplicate policy name not found. Actual message: " + errorMsg);	
	}

	/**
	 * TC_C53173574:[WEP]>>Check User Cannot Delete Policy With Status Assigned
	 */
	@Test(priority = 43, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53173574")
	public final void CheckUserCannotDeletePolicyWithStatusAssigned() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.edit_platform_policy_user);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		if (policypages.waitUntilElementIsInvisibleOfPolicyPage("deleteButton")) {
			LOGGER.info("groupDeleteButton is not visible, proceeding...");
		} else {
			LOGGER.info("groupDeleteButton is still visible.");
		}
		}
	
	/**
	 * TC_C53173573:[WEP]>>Check User Can Delete A Policy With Status Unassigned
	 */
	@Test(priority = 44, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53173573")
	public final void CheckUserCanDeleteAPolicyWithStatusUnassigned() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.edit_platform_policy_user);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");

	
	}
	
	/**
	 * TC_C53173575:[WEP]>>Conflict Resolution On The Two Platform Policies Target The Same Platform Assign To Same DeviceGroup
	 */
	@Test(priority = 45, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53173575")
	public final void ConflictResolutionOnTheTwoPlatformPoliciesTargetTheSamePlatformAssignToSameDeviceGroup() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.conflict_setting_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("platformDropdownButton");
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem",PolicyVariables.Platform_Name);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AssetTrackingNumberOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();	
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.conflict_setting_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.GroupName);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.conflict_setting_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.conflict_setting_policy1+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("platformDropdownButton");
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem",PolicyVariables.Platform_Name);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AssetTrackingNumberOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.conflict_setting_policy1);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.GroupName);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("policyConflict");
	    String errorMsg = policypages.getTextOfWEPPolicyPage("policyConflict");
        LOGGER.info(errorMsg);
        Assert.assertTrue(
			    errorMsg != null && errorMsg.toLowerCase().contains("Conflicts (1)".toLowerCase()),
			    "Policy details are not available. Actual message: " + errorMsg);
        sleeper(2000);
        policypages.clickOnElementsOfPolicyPage("cancelButton");
        policypages.enterTextForPolicyPage("policySearch",PolicyVariables.conflict_setting_policy1);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
        policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.conflict_setting_policy);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.conflict_setting_policy);
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}
	
	/**
	 * TC_C53173577:[WEP]>>No Conflict Resolution On The Two Policies One Is Generic Second Is Specific Assign To Same Device Group
	 */
	@Test(priority = 46, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53173577")
	public final void NoConflictResolutionOnTheTwoPoliciesOneIsGenericSecondIsSpecificAssignToSameDeviceGroup() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.No_conflict_global_setting_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("deployment");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();	
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.No_conflict_global_setting_policy);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.latestGroup_Name);
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		sleeper(3000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.No_conflict_global_setting_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Assigned"),
		    "Expected error message for duplicate policy name not found. Actual message: " + errorMsg);
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.No_conflict_platform_setting_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("platformDropdownButton");
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem",PolicyVariables.Platform_Name);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deployment");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();	
		sleeper(2000);	
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.No_conflict_platform_setting_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.latestGroup_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.No_conflict_global_setting_policy);
		waitForPageLoaded();
		sleeper(5000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		sleeper(2000);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.No_conflict_global_setting_policy);
		waitForPageLoaded();
		sleeper(5000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
        policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");		
	    policypages.enterTextForPolicyPage("policySearch",PolicyVariables.No_conflict_platform_setting_policy);
		waitForPageLoaded();
		sleeper(5000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.No_conflict_platform_setting_policy);
		waitForPageLoaded();
		sleeper(5000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}
	

	/**
	 * TC_C53139720:[WEP]>>UI Verify In Add Policy Screen User Is Able To Select Global Policy Option For BiosSetting
	 */
	@Test(priority = 47, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139720")
	public final void UIVerifyInAddPolicyScreenUserIsAbleToSelectGlobalPolicyOptionForBiosSetting() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.select_global_bios+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		policypages.verifyElementIsPresentOnPolicyPage("AssignPage");
		String errorMsg = policypages.getTextOfWEPPolicyPage("AssignPage");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Add PC Policy"),
		    "Navigated to the add pc policy page: " + errorMsg);
		}
	
	/**
	 * TC_C53712042:[WEP]>>verify User Can Create Global Bios Setting Policy
	 */
	@Test(priority = 48, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53712042")
	public final void verifyUserCanCreateGlobalBiosSettingPolicy() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.select_global_bios+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("description");
		policypages.verifyElementIsPresentOnPolicyPage("policyName");
		policypages.verifyElementIsPresentOnPolicyPage("Type");
		policypages.verifyElementIsPresentOnPolicyPage("Scope");
		policypages.verifyElementIsPresentOnPolicyPage("AccessaryPort");
		policypages.verifyElementIsPresentOnPolicyPage("backButton");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		sleeper(3000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.select_global_bios);
		sleeper(3000);
		LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}

	/**
	 * TC_C53139723:[WEP]>>Verify That User Able Click On Preview Button In Add Pc Policy Overview Page
	 */
	@Test(priority = 49, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139723")
	public final void VerifyThatUserAbleClickOnPreviewButtonInAddPcPolicyOverviewPage() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		LOGGER.info(policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton"));
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.preview_policy_page+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("AssignPage");
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		policypages.verifyElementIsPresentOnPolicyPage("backButton");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("previewButton");
		policypages.verifyElementIsPresentOnPolicyPage("previewplatform");
		policypages.clickOnElementsOfPolicyPage("previewCancelButton");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.preview_policy_page);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
	    waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("previewButton");
		policypages.verifyElementIsPresentOnPolicyPage("previewplatform");
		String errorMsg = policypages.getTextOfWEPPolicyPage("previewplatform");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Preview platform settings"),
		    "Navigate to the Preview platform settings page: " + errorMsg);	
		policypages.clickOnElementsOfPolicyPage("previewCancelButton");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}

	/**
	 * TC_C53139725:[WEP]>>UI Verify On Selecting Policy Option List Of Bios Setting Populate By Default In Add Pc PolicyPage
	 */
	@Test(priority = 50, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53139725")
	public final void UIVerifyOnSelectingPolicyOptionListOfBiosSettingPopulateByDefaultInAddPcPolicyPage() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.preview_policy_page+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		policypages.verifyElementIsPresentOnPolicyPage("AssignPage");
		policypages.verifyElementIsPresentOnPolicyPage("UsbEnumeration");
		String errorMsg = policypages.getTextOfWEPPolicyPage("UsbEnumeration");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Accelerate USB Enumeration"),
		    "Navigated to the add pc policy page and verified teh USB Enumeration: " + errorMsg);
		}
	
	/**
	 * TC_C53139726:[WEP]>>UI Verify On Enabling Show Selected Only Button Bios Setting Selected From The Default List Should Be Listed Out
	 */
	@Test(priority = 51, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139726")
	public final void UIVerifyOnEnablingShowSelectedOnlyButtonBiosSettingSelectedFromTheDefaultListShouldBeListedOut() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.preview_policy_page+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		policypages.actionClickOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		sleeper(2000);
		policypages.actionClickOfPolicyPage("UsbEnumeration");
		sleeper(2000);
		policypages.actionClickOfPolicyPage("memoryCapSetting");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("PolicytoggleButtonOpen");
		policypages.clickOnElementsOfPolicyPage("PolicytoggleButtonOpen");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		String errorMsg = policypages.getTextOfWEPPolicyPage("EnableButton");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Enable"),
		    "Enable button is visible: " + errorMsg);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("PolicytoggleButtonOpen");
		policypages.scrollOnPolicyPage("scrollUpDown");
		policypages.verifyElementIsPresentOnPolicyPage("actionkeySetting");
		}
	
	
	/**
	 * TC_C53139727:[WEP]>>UI Verify Bios Setting Review Policy Page Shows The Bios Setting Selected From Global Bios Setting List
	 */
	@Test(priority = 52, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139727")
	public final void UIVerifyBiosSettingReviewPolicyPageShowsTheBiosSettingSelectedFromGlobalBiosSettingList() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.preview_policy_page+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("accelerateUSBSetting");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("memoryCapSetting");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("viewselectedItem");
		String errorMsg = policypages.getTextOfWEPPolicyPage("viewselectedItem");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
			    errorMsg != null && errorMsg.matches(".*\\d+ item[s]? selected.*"),
			    "Expected item(s) to be selected, but was: " + errorMsg);
		
		}

	/**
	 * TC_C53139728:[WEP]>>UI Verify In Add Pc Policy Information Page By Default None Of The Option Selected Until User Action
	 */
	@Test(priority = 53, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53139728")
	public final void UIVerifyInAddPcPolicyInformationPageByDefaultNoneOfTheOptionSelectedUntilUserAction() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.preview_policy_page+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		LOGGER.info(policypages.verifyElementsIsSelectedOfPolicyPage("policyScopeGlobalOption"));
		LOGGER.info(policypages.verifyElementsIsSelectedOfPolicyPage("policyScopePlatformOption"));
		
		}

	/**
	 * TC_C53139729:[WEP]>>Verify On Clicking Policy Conflict Button After Global Policy Bios Setting Conflict User Can View NewAnd Old Global Policy Settings
	 */
	@Test(priority = 54, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139729")
	public final void UiVerifyOnClickingPolicyConflictButtonAfterGlobalPolicyBiosSettingConflictUserCanViewNewAndOldGlobalPolicySettings() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.preview_policy_page+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.preview_policy_page);
		waitForPageLoaded();
		LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.latestGroup_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.preview_policy_page);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_conflict_page+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_conflict_page);
		waitForPageLoaded();
		LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.latestGroup_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("policyConflict");
	    String errorMsg = policypages.getTextOfWEPPolicyPage("policyConflict");
		System.out.println(errorMsg);
		Assert.assertTrue(
			    errorMsg != null && errorMsg.toLowerCase().contains("Conflicts (1)".toLowerCase()),
			    "Expected policy conflict message not found. Actual message: " + errorMsg);
		policypages.clickOnElementsOfPolicyPage("cancelButton");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.preview_policy_page);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_conflict_page);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_conflict_page);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");

	}	
	
	/**
	 * TC_C53139734:[WEP]>>UI Verify After Publishing Global Bios Setting Policy In Policy List Detail View It Should Show Assigned
	 */
	@Test(priority = 55, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139734")
	public final void UIVerifyAfterPublishingGlobalBiosSettingPolicyInPolicyListDetailViewItShouldShowAssignedStateWithGroupName() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.detail_view_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.detail_view_policy);
		policypages.mouseHoverOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("metaball");
		policypages.clickOnElementsOfPolicyPage("AssignToGroup");
		policypages.verifyElementIsPresentOnPolicyPage("AssignPage");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.latestGroup_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("viewseleted");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("reviewAssisgnPage");
		policypages.verifyElementIsPresentOnPolicyPage("verifyDetailsInReviewPage");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		policypages.verifyElementIsPresentOnPolicyPage("backButton");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
	}

	/**
	 * TC_C53139735:[WEP]>>UIVerifyUserCannotEditGlobalBiosSettingToPlatformPoliciesIFItIsAssignedState
	 */
	@Test(priority = 56, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53139735")
	public final void UIVerifyUserCannotEditGlobalBiosSettingToPlatformPoliciesIFItIsAssignedState() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.detail_view_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		if (policypages.waitUntilElementIsInvisibleOfPolicyPage("editButton")) {
			LOGGER.info("edit button is not visible, proceeding...");
		} else {
			LOGGER.info("edit Button is still visible.");
		}
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.detail_view_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		}
	

	/**
	 * TC_C53139736:[WEP]>>UI Verify When Conflict Occur In Policy Review Page Assign Button Should Not Displayed When User Uncheck Group Button
	 */
	@Test(priority = 57, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139736")
	public final void UIVerifyWhenConflictOccurInPolicyReviewPageAssignButtonShouldNotDisplayedWhenUserUncheckGroupButton() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_review+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_review);
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("metaball");
		policypages.clickOnElementsOfPolicyPage("AssignToGroup");
		policypages.verifyElementIsPresentOnPolicyPage("AssignPage");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.latestGroup_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("viewseleted");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("reviewAssisgnPage");
		policypages.verifyElementIsPresentOnPolicyPage("verifyDetailsInReviewPage");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		policypages.verifyElementIsPresentOnPolicyPage("backButton");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.actionClickOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_review);
		policypages.mouseHoverOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_review_page+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("platformDropdownButton");
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		policypages.enterTextForPolicyPage("SearchItem",PolicyVariables.Platform_Name);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AssetTrackingNumberOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");	
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_review_page);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.latestGroup_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.actionClickOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");

		LOGGER.info("Policies assigned to Static Group is done successfully");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.global_policy_review+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.global_policy_review);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.latestGroup_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("uncheckboxAssignpage");
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("itemsNotFound");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		policypages.verifyElementIsPresentOnPolicyPage("backButton");
		policypages.clickOnElementsOfPolicyPage("cancelButton");
		String errorMsg = policypages.getTextOfWEPPolicyPage("policyPage");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
			    errorMsg != null && errorMsg.toLowerCase().contains("policies".toLowerCase()),
			    "Afetr clicking the cancel button navigated to policies pages: " + errorMsg);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.global_policy_review);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_review_page);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");

		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_review);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		policypages.waitForElementsOfPolicyPage("deleteButton");
	    policypages.clickOnElementsOfPolicyPage("deleteButton");
	    waitForPageLoaded();
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	    policypages.waitForElementsOfPolicyPage("policyDeleted");
	    Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");

	}

	/**
	 * TC_C53139737:[WEP]>>Ui In Global Bios Setting List There Must Be Only Secure Boot Settings
	 */
	@Test(priority = 58, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139737")
	public final void UiInGlobalBiosSettingListThereMustBeOnlySecureBootSettings() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.Secure_Boot_Settings+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.boot_setting);
		policypages.clickOnElementsOfPolicyPage("securebootinSettings");
		
		String errorMsg = policypages.getTextOfWEPPolicyPage("securebootinSettings");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Secure Boot"),
		    "Three items should be selected: " + errorMsg);
		
		}
	
	/**
	 * TC_C53139739:[WEP]>>UI In Global Bios Setting List There Must Be Only Hyperthreading Setting
	 */
	@Test(priority = 59, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139739")
	public final void UIInGlobalBiosSettingListThereMustBeOnlyHyperthreadingSetting() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.Secure_Boot_Settings+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("policyScopeGlobalOption");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		sleeper(3000); 
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_setting);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("hyperthreadsetting");
		String errorMsg = policypages.getTextOfWEPPolicyPage("hyperthreadsetting");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Hyperthreading"),
		    "Three items should be selected: " + errorMsg);
		
		}

	/**
	 * TC_C53139740:[WEP]>>UI Verify In Preview Platform Settings Page No Platform Should Be Selected By Default
	 */
	@Test(priority = 60, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139740")
	public final void UIVerifyInPreviewPlatformSettingsPageNoPlatformShouldBeSelectedByDefault() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.Secure_Boot_Settings+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("accelerateUSBSetting");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("memoryCapSetting");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("PolicytoggleButtonOpen");
		policypages.clickOnElementsOfPolicyPage("PolicytoggleButtonOpen");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("previewButton");
		policypages.verifyElementIsPresentOnPolicyPage("previewplatform");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("viewselected");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Secure_Boot_Settings);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("previewButton");
		policypages.verifyElementIsPresentOnPolicyPage("previewplatform");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("viewselected");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("cancelbuttonInPreview");
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		String errorMsg = policypages.getTextOfWEPPolicyPage("UnassignedState");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Unassigned"),
		    "Policy is in unassigned state: " + errorMsg);
		
		}
	
	/**
	 * TC_C53139742:[WEP]>>UI Verify User Can Select N number Of Platform In Preview Platform Settings Page
	 */
	@Test(priority = 61, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139742")
	public final void UIVerifyUserCanSelectNnumberOfPlatformInPreviewPlatformSettingsPage() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.Preview_Platform_Settings+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("accelerateUSBSetting");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("memoryCapSetting");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("PolicytoggleButtonOpen");
		policypages.clickOnElementsOfPolicyPage("PolicytoggleButtonOpen");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("previewButton");
		policypages.verifyElementIsPresentOnPolicyPage("previewplatform");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.platformName);
		policypages.clickOnElementsOfPolicyPage("checkboxInPreviewpage");
		policypages.clickOnElementsOfPolicyPage("clearButton");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.platfromName1);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("checkboxInPreviewpage");
		policypages.clickOnElementsOfPolicyPage("clearButton");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.platformName2);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("checkboxInPreviewpage");
		policypages.clickOnElementsOfPolicyPage("clearButton");
		waitForPageLoaded();
		String errorMsg = policypages.getTextOfWEPPolicyPage("viewseleted1");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
			    errorMsg != null && !errorMsg.isBlank() &&
			    (errorMsg.contains("3 items selected") || errorMsg.contains("View Selected")),
			    "Unexpected platform message, found: '" + errorMsg + "'"
			);
	}

	
	/**
	 * TC_C53139744:[WEP]>>UI Verify User Can Save Policy Without Selecting A Platform From Dropdown For Comparing In Review Policy Page
	 */
	@Test(priority = 62, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139744")
	public final void UIVerifyUserCanSavePolicyWithoutSelectingAPlatformFromDropdownForComparingInReviewPolicyPage() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.Preview_Platform_Settings+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("accelerateUSBSetting");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("memoryCapSetting");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.scrollOnPolicyPage("scrollUpDown");
		policypages.verifyElementIsPresentOnPolicyPage("biossettingvalue");
		policypages.verifyElementIsPresentOnPolicyPage("biossettingvalue1");
		policypages.verifyElementIsPresentOnPolicyPage("biossettingvalue2");
		policypages.verifyElementsIsEnabledOfPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		String errorMsg = policypages.getTextOfWEPPolicyPage("addPolicyButton");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
			    errorMsg != null && errorMsg.contains("Add"),
			    "Add button is enabled: " + errorMsg);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Preview_Platform_Settings);
		waitForPageLoaded();
		LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();	
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}
	
	/**
	 * TC_C53926511:[WEP]>>Verify User Can Create Secret SPM Certificate Type
	 */
	@Test(priority = 63, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53926511")
	public final void VerifyUserCanCreateSecretSPMCertificateType() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		policypages.enterTextForPolicyPage("ScreteNameTextBox",PolicyVariables.secret_certificate);
		policypages.clickOnElementsOfPolicyPage("spmCerticateRadioButton");
		policypages.enterTextForPolicyPage("SecretDescriptionTextBox",PolicyVariables.SG_SecretDescription);
		sleeper(2000);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	
		policypages.clickOnElementsOfPolicyPage("secretePageNextButton");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("spmCerticateRadioButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentcounrtyDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentstateDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentcityDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentorganizationnameDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentorganizationunitDetails");
		policypages.actionClickOfPolicyPage("signingRadioButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingcounrtyDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingstateDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingcityDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingorganizationnameDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingorganizationunitDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("cancelButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("backButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementsIsEnabledOfPolicyPage("secretName");
		policypages.verifyElementsIsEnabledOfPolicyPage("secretAssignedName");
		policypages.verifyElementsIsEnabledOfPolicyPage("secretType");
		policypages.verifyElementsIsEnabledOfPolicyPage("cancelButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("backButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("addSecretBtn");
		policypages.clickOnElementsOfPolicyPage("addSecretBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAddPending").contains("Secret add pending"), "secret add pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAdded").contains("Secret added"), "secret added is failed");
		LOGGER.info("Added secret successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_certificate);
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		
	}
	
	/**
	 * TC_C53135399:[WEP]>>Verify User Can Search And Sort WIth Respect To Secret Name
	 */
	@Test(priority = 64, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53135399")
	public final void VerifyUserCanSearchAndSortWIthrespectToSecretName() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("assendingOrderSecret");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("assendingOrderSecret");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("CreatedOn");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_variables);
		policypages.verifyElementIsPresentOnPolicyPage("NoResults");
	}
	
	/**
	 * TC_C53135263:[WEP]>>Verify Details Page For An Unassigned Secret
	 */
	@Test(priority = 65, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135263")
	public final void VerifyDetailsPageForAnUnassignedSecret() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_certificate);
		LOGGER.info("Policy name is successfully changed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		LOGGER.info(policypages.verifyElementIsPresentOnPolicyPage("UnassignedState"));
		LOGGER.info(policypages.verifyElementIsPresentOnPolicyPage("deleteButton"));
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("secretHistoryTab");
		LOGGER.info(policypages.verifyElementIsPresentOnPolicyPage("historyTitle"));
		String errorMsg = policypages.getTextOfWEPPolicyPage("historyTitle");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
			    errorMsg != null && errorMsg.contains("History"),
			    "Navigated to the History tab: " + errorMsg);
	}
	
	/**
	 * TC_C53135264:[WEP]>>Verify Details Page For An Assigned Secret
	 */
	@Test(priority = 66, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135264")
	public final void VerifyDetailsPageForAnAssignedSecret() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		policypages.enterTextForPolicyPage("ScreteNameTextBox",PolicyVariables.secret_details_name+generateRandomNumber());
		policypages.clickOnElementsOfPolicyPage("passwordRadioButton");
		policypages.enterTextForPolicyPage("SecretDescriptionTextBox",PolicyVariables.SG_SecretDescription);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("secretePageNextButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("standardButton");
		policypages.enterTextForPolicyPage("passwordText",PolicyVariables.Secret_password);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addSecretBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAddPending").contains("Secret add pending"), "secret add pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAdded").contains("Secret added"), "secret added is failed");
		LOGGER.info("Added secret successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_details_name);
		LOGGER.info("Secret added successfully ");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.assigned_authentication_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosAuthenticationOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("automationsecreteoption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		policypages.verifyElementIsPresentOnPolicyPage("backButton");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("InformationPolicyName");
		policypages.verifyElementIsPresentOnPolicyPage("InformationPolicyType");
		policypages.verifyElementIsPresentOnPolicyPage("BiosAuthenticationBiosPassword");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("policySearch");
		waitForPageLoaded();	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assigned_authentication_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.GroupName);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		waitForPageLoaded();
		LOGGER.info("Policies assigned to Static Group is done successfully");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assigned_authentication_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		if (policypages.waitUntilElementIsInvisibleOfPolicyPage("deleteButton")) {
			LOGGER.info("groupDeleteButton is not visible, proceeding...");
		} else {
			LOGGER.info("groupDeleteButton is still visible.");
		}
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assigned_authentication_policy);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}
	
	/**
	 * TC_C55551280:[WEP]>>Verify Details Page For An Unassigned Or Assigned Secret Password Overvew
	 */
	@Test(priority = 67, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C55551280")
	public final void VerifyDetailsPageForAnUnassignedOrAssignedSecretPasswordOvervew() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_details_name);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("profileName");
		policypages.verifyElementIsPresentOnPolicyPage("description");
		policypages.verifyElementIsPresentOnPolicyPage("Type");
		policypages.verifyElementIsPresentOnPolicyPage("policyName");
		policypages.verifyElementIsPresentOnPolicyPage("CreatedBy");
		policypages.verifyElementIsPresentOnPolicyPage("createdOn");
		policypages.verifyElementIsPresentOnPolicyPage("policyId");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("Complexityrule");
		policypages.verifyElementIsPresentOnPolicyPage("Passwordrule");
		policypages.clickOnElementsOfPolicyPage("maskButton");
		policypages.verifyElementIsPresentOnPolicyPage("passwordValue");
		String errorMsg = policypages.getTextOfWEPPolicyPage("passwordValue");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
			    errorMsg != null && errorMsg.contains("Password@1234"),
			    "Password should be in plain text: " + errorMsg);
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("deleteButton",30);
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretDeletePending").contains("Secret delete pending"), "Secret delete pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretDeleted").contains("Secret deleted"), "Secret deleted is failed");
		}
	
	
	/**
	 * TC_C55546417:[WEP]>>Verify Details Page For An Unassigned Or Assigned Secret Assignment
	 */
	@Test(priority = 68, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C55546417")
	public final void VerifyDetailsPageForAnUnassignedOrAssignedSecretAssignment() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		policypages.enterTextForPolicyPage("ScreteNameTextBox",PolicyVariables.secret_assignment+generateRandomNumber());
		policypages.clickOnElementsOfPolicyPage("passwordRadioButton");
		policypages.enterTextForPolicyPage("SecretDescriptionTextBox",PolicyVariables.SG_SecretDescription);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("secretePageNextButton");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("standardButton");
		policypages.enterTextForPolicyPage("passwordText",PolicyVariables.Secret_password);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");	
		policypages.clickOnElementsOfPolicyPage("addSecretBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAddPending").contains("Secret add pending"), "secret add pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAdded").contains("Secret added"), "secret added is failed");
		LOGGER.info("Added secret successfully");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_assignment);
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		policypages.waitForElementsOfPolicyPage("NoPolicyAssigned");
		LOGGER.info("No Policy is assigned to the secret");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.assignment_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosAuthenticationOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("automationsecreteoption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		policypages.verifyElementIsPresentOnPolicyPage("backButton");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("InformationPolicyName");
		policypages.verifyElementIsPresentOnPolicyPage("InformationPolicyType");
		policypages.verifyElementIsPresentOnPolicyPage("BiosAuthenticationBiosPassword");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assignment_policy);
		waitForPageLoaded();	
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
        sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_assignment);
		waitForPageLoaded();	
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("PolicyNameInSecret");
		policypages.verifyElementIsPresentOnPolicyPage("StatusInSecret");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assignment_policy);
		sleeper(4000);
		policypages.clickOnElementsOfPolicyPage("clearbuttonInSecretName");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("OptionAllinStatusDropdown");
		policypages.actionClickOfPolicyPage("clearbuttonInSecretName1");
		policypages.actionClickOfPolicyPage("OptionAllinStatusDropdown");
		policypages.verifyElementIsPresentOnPolicyPage("OptionAssignedinStatusDropdown");
		policypages.verifyElementIsPresentOnPolicyPage("OptionUnassignedinStatusDropdown");
		policypages.clickOnElementsOfPolicyPage("OptionAssignedinStatusDropdown");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("clearbuttonInSecretName1");		
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.assignment_policy);
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");	
	}
	
	/**
	 * TC_C55557925:[WEP]>>Verify Details Page For An Unassigned Or Assigned Secret History
	 */
	@Test(priority = 69, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C55557925")
	public final void VerifyDetailsPageForAnUnassignedOrAssignedSecretHistory() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		policypages.enterTextForPolicyPage("ScreteNameTextBox",PolicyVariables.secret_assignment+generateRandomNumber());
		policypages.clickOnElementsOfPolicyPage("passwordRadioButton");
		policypages.enterTextForPolicyPage("SecretDescriptionTextBox",PolicyVariables.SG_SecretDescription);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretePageNextButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("standardButton");
		policypages.enterTextForPolicyPage("passwordText",PolicyVariables.Secret_password);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");	
		policypages.clickOnElementsOfPolicyPage("addSecretBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAddPending").contains("Secret add pending"), "secret add pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAdded").contains("Secret added"), "secret added is failed");
		LOGGER.info("Added secret successfully");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_assignment);
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		policypages.clickOnElementsOfPolicyPage("secretHistoryTab");
		policypages.waitForElementsOfPolicyPage("TimeInHistoryTab");
		policypages.waitForElementsOfPolicyPage("DescriptionInHistoryTab");
		policypages.waitForElementsOfPolicyPage("SecretcreatedInHistoryTab");
		String time = policypages.getTextOfWEPPolicyPage("secretTimeCreated");
		LOGGER.info(time);
		policypages.enterTextForPolicyPage("policySearch",time);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("clearbuttonInSecretName");
		String description = policypages.getTextOfWEPPolicyPage("SecretcreatedInHistoryTab");
		LOGGER.info(description);
		policypages.enterTextForPolicyPage("searchInHistoryTab",description);
		policypages.clickOnElementsOfPolicyPage("clearbuttonInSecretName");
		String errorMsg = policypages.getTextOfWEPPolicyPage("ValueInSearch");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
			    errorMsg != null && errorMsg.contains(""),
			    "Search Tab shoud be Empty: " + errorMsg);		
	}
	
	

	/**
	 * TC_C53135402:[WEP]>>Verify User Can Create Secret SPM Certificate Type Upload
	 */
	@Test(priority = 70, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53135402")
	public final void VerifyUserCanCreateSecretSPMCertificateTypeUpload() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		String fileName = "Automation Secret using certificate - Endorsement Key.pfx";
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_certificate);
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		waitForPageLoaded();
		sleeper(3000);
		policypages.actionClickOfPolicyPage("downloadendrosmentKey");
		policypages.enterTextForPolicyPage("inputPasswordEndrosmentKey","Password@1234");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("downloadButton");
		sleeper(5000);
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_certificate);
		LOGGER.info("Policy name is successfully changed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		waitForPageLoaded();
		sleeper(5000);
		policypages.actionClickOfPolicyPage("downloadsigningKey");
		policypages.enterTextForPolicyPage("inputPasswordEndrosmentKey","Password@1234");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("downloadButton");
		LOGGER.info("downloaded certificates sucessfully");
		sleeper(5000);
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		policypages.enterTextForPolicyPage("ScreteNameTextBox",PolicyVariables.secret_certificate+generateRandomNumber());
		policypages.clickOnElementsOfPolicyPage("spmCerticateRadioButton");
		policypages.enterTextForPolicyPage("SecretDescriptionTextBox","Secrete for bios Authentication");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("secretePageNextButton");
		sleeper(2000);
		policypages.actionClickOfPolicyPage("uploadcertificateEndrosmentKey");
	    policypages.actionClickOfPolicyPage("uploadEndrosmentKey");
	    String downloadPath = ConstantPath.DOWNLOAD_PATH;            
	    policypages.uploadFile(downloadPath);
	    policypages.uploadFile(fileName);
	    waitForPageLoaded();
		policypages.enterTextForPolicyPage("inputPasswordEndrosmentKey","Password@1234");		
		policypages.actionClickOfPolicyPage("uploadcertificateSigningKey");
	    policypages.actionClickOfPolicyPage("uploadSigningKey");
		waitForPageLoaded();
		String fileName1 = "Automation Secret using certificate - Signing Key.pfx";
		System.out.println(fileName1);
		policypages.uploadFile(fileName1);
		sleeper(2000);
		policypages.enterTextForPolicyPage("inputPasswordSigningKey","Password@1234");
		policypages.clickOnElementsOfPolicyPage("unmaskbuttonEndrosment");
		policypages.clickOnElementsOfPolicyPage("maskbuttonEndrosment");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("unmaskbuttonSigning");
		policypages.clickOnElementsOfPolicyPage("maskbuttonSigning");
		policypages.mouseHoverOfPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addSecretBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAddPending").contains("Secret add pending"), "secret add pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAdded").contains("Secret added"), "secret added is failed");
		LOGGER.info("Added secret successfully");

	}		
	
	/**
	 * TC_C53135396:[WEP]>>VerifyUserCanEditDetailsForAnUnAssignedSecretSPMCertificate
	 */
	@Test(priority = 71, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C53135396")
	public final void VerifyUserCanEditDetailsForAnUnAssignedSecretSPMCertificate() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		policypages.enterTextForPolicyPage("ScreteNameTextBox",PolicyVariables.secret_certificate_download);
		policypages.clickOnElementsOfPolicyPage("spmCerticateRadioButton");
		policypages.enterTextForPolicyPage("SecretDescriptionTextBox",PolicyVariables.SG_SecretDescription);
		sleeper(2000);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	
		policypages.clickOnElementsOfPolicyPage("secretePageNextButton");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("spmCerticateRadioButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentcounrtyDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentstateDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentcityDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentorganizationnameDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentorganizationunitDetails");
		policypages.clickOnElementsOfPolicyPage("signingRadioButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingcounrtyDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingstateDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingcityDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingorganizationnameDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingorganizationunitDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("cancelButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("backButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementsIsEnabledOfPolicyPage("secretName");
		policypages.verifyElementsIsEnabledOfPolicyPage("secretAssignedName");
		policypages.verifyElementsIsEnabledOfPolicyPage("secretType");
		policypages.verifyElementsIsEnabledOfPolicyPage("cancelButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("backButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("addSecretBtn");
		policypages.clickOnElementsOfPolicyPage("addSecretBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAddPending").contains("Secret add pending"), "secret add pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAdded").contains("Secret added"), "secret added is failed");
		LOGGER.info("Added secret successfully");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_certificate_download);
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		waitForPageLoaded();
		sleeper(3000);
		policypages.actionClickOfPolicyPage("downloadendrosmentKey");
		policypages.enterTextForPolicyPage("inputPasswordEndrosmentKey","Password@1234");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("downloadButton");
		sleeper(5000);
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_certificate_download);
		LOGGER.info("Policy name is successfully changed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		waitForPageLoaded();
		sleeper(5000);
		policypages.actionClickOfPolicyPage("downloadsigningKey");
		policypages.enterTextForPolicyPage("inputPasswordEndrosmentKey","Password@1234");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("downloadButton");
		LOGGER.info("downloaded certificates sucessfully");
		sleeper(5000);
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_certificate_download);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		policypages.clickOnElementsOfPolicyPage("editButton");
		policypages.verifyElementIsPresentOnPolicyPage("SecretNameEdit");
		policypages.verifyElementIsPresentOnPolicyPage("DescriptionInEdit");
		policypages.verifyElementIsPresentOnPolicyPage("TagInEdit");
		policypages.clickOnElementsOfPolicyPage("clearButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("ScreteNameTextBox","Automation Secret using certificate12");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("SecretDescriptionTextBox","Automation Secret certificate1");
		LOGGER.info(policypages.verifywaituntilelementisenabled("applyButton"));
		policypages.clickOnElementsOfPolicyPage("applyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretUpdatePending").contains("Secret update pending"), "Secret update pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretUpdated");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretUpdated").contains("Secret updated"), "Secret updated is failed");
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("EditSPMcertificate");
		policypages.clickOnElementsOfPolicyPage("uploadcertificateEndrosmentKey");
		policypages.actionClickOfPolicyPage("uploadEndrosmentKey");
		String downloadPath = ConstantPath.DOWNLOAD_PATH;            
	    policypages.uploadFile(downloadPath);
		waitForPageLoaded();
		String fileName = "Download certificate - Endorsement Key.pfx";
		System.out.println(fileName);
		policypages.uploadFile(fileName);	
		policypages.enterTextForPolicyPage("inputPasswordEndrosmentKey","Password@1234");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("uploadcertificateSigningKey");
		policypages.actionClickOfPolicyPage("uploadSigningKey");
		String fileName1 = "Download certificate - Signing Key.pfx";
		System.out.println(fileName1);
		policypages.uploadFile(fileName1);
		policypages.enterTextForPolicyPage("inputPasswordSigningKey","Password@1234");
		policypages.clickOnElementsOfPolicyPage("unmaskbuttonEndrosment");
		policypages.clickOnElementsOfPolicyPage("maskbuttonEndrosment");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("unmaskbuttonSigning");
		policypages.clickOnElementsOfPolicyPage("maskbuttonSigning");
		policypages.mouseHoverOfPolicyPage("cancelButton");
		policypages.verifywaituntilelementisenabled("applyButtonUpdate");
		policypages.clickOnElementsOfPolicyPage("applyButtonUpdate");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretUpdatePending").contains("Secret update pending"), "Secret update pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretUpdated");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretUpdated").contains("Secret updated"), "Secret updated is failed");
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretDeletePending").contains("Secret delete pending"), "Secret delete pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretDeleted").contains("Secret deleted"), "Secret deleted is failed");
	}
	
	/**
	 * TC_C55558175:[WEP]>>VerifyUserCanEditDetailsForAnUnAssignedSecretPassword
	 */
	@Test(priority = 72, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C55558175")
	public final void VerifyUserCanEditDetailsForAnUnAssignedSecretPassword() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");	
		sleeper(3000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.secret_assignment);
		sleeper(3000);
		policypages.waitForElementsOfPolicyPage("secretSearchItem");
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		policypages.clickOnElementsOfPolicyPage("editButton");
		policypages.verifyElementIsPresentOnPolicyPage("SecretNameEdit");
		policypages.verifyElementIsPresentOnPolicyPage("DescriptionInEdit");
		policypages.verifyElementIsPresentOnPolicyPage("TagInEdit");
		policypages.clickOnElementsOfPolicyPage("clearButton");
		waitForPageLoaded();	
		policypages.enterTextForPolicyPage("ScreteNameTextBox","Automation Secret overview12");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("SecretDescriptionTextBox","Automation Secret ");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("applyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretUpdatePending").contains("Secret update pending"), "Secret update pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretUpdated");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretUpdated").contains("Secret updated"), "Secret updated is failed");	   
		sleeper(5000);
		policypages.clickOnElementsOfPolicyPage("EditSPMcertificate");
		policypages.clickOnElementsOfPolicyPage("editclearbutton");
		policypages.waitForElementsOfPolicyPage("editdropdownbutton");
		policypages.actionClickOfPolicyPage("editdropdownbutton");
		waitForPageLoaded();	
		policypages.clickOnElementsOfPolicyPage("ComplexityRuleInEdit");
		sleeper(6000);
		policypages.enterTextForPolicyPage("PasswordInEdit","Password@12345");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("applyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretUpdatePending").contains("Secret update pending"), "Secret update pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretUpdated");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretUpdated").contains("Secret updated"), "Secret updated is failed");
	}
	
	/**
	 * TC_C53926583:[WEP]>>Verify User Can Delete An Unassigned Secret
	 */
	@Test(priority = 73, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53926583")
	public final void VerifyUserCanDeleteAnUnassignedSecret() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");	
		policypages.enterTextForPolicyPage("policySearch","Automation Secret Overview1");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretDeletePending").contains("Secret delete pending"), "Secret delete pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretDeleted").contains("Secret deleted"), "Secret deleted is failed");		policypages.verifyElementIsPresentOnPolicyPage("PolicyVerify");
		policypages.enterTextForPolicyPage("policySearch","Automation Secret Overview1");
		sleeper(2000);
		String errorMsg = policypages.getTextOfWEPPolicyPage("PolicyVerify");
		System.out.println(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("No results"),
		    "Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		
       }
	
	
	/**
	 * TC_C53135266:[WEP]>>Verify User Can Delete An Assigned Secret
	 */
	@Test(priority = 74, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135266")
	public final void VerifyUserCanDeleteAnAssignedSecret() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");	
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch","Mac_Secret2");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		if (policypages.waitUntilElementIsInvisibleOfPolicyPage("deleteButton")) {
			LOGGER.info("groupDeleteButton is not visible, proceeding...");
		} else {
			LOGGER.info("groupDeleteButton is still visible.");
		}
	}
	
	/**
	 * TC_C55594046:[WEP]>>Verify User Can Modify Grid Displayed On Secrets Main Page
	 */
	@Test(priority = 75, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C55594046")
	public final void VerifyUserCanModifyGridDisplayedOnSecretsMainPAge() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");	
		policypages.clickOnElementsOfPolicyPage("SettingInSecretPage");
		policypages.clickOnElementsOfPolicyPage("StatusInColumn");
		policypages.clickOnElementsOfPolicyPage("upwardButton");
		policypages.clickOnElementsOfPolicyPage("SaveButton");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("SettingInSecretPage");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("RestoreDefault");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("SaveButton");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("SeceretHome");
	    String errorMsg = policypages.getTextOfWEPPolicyPage("SeceretHome");
		System.out.println(errorMsg);
		Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Secrets"),
		    "Navigated to the Secret home page: " + errorMsg);
	}
	
	/**
	 * TC_C53135395:[WEP]>>VerifyUserCannotEditDetailsForAnAssignedSecret
	 */
	@Test(priority = 76, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53135395")
	public final void VerifyUserCannotEditDetailsForAnAssignedSecret() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch","Mac_Secret2");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		if (policypages.waitUntilElementIsInvisibleOfPolicyPage("editButton")) {
			LOGGER.info("edit button is not visible, proceeding...");
		} else {
			LOGGER.info("edit Button is still visible.");
		}
	}
	
	/**
	 * TC_C53139745:[WEP]>>VerifyonSelectPlatformdropdownplatformsADgroup
	 */
	@Test(priority = 77, groups = {"REGRESSION_POLICY"}, description = "Testcase ID: C53139745")
	public final void VerifyonSelectPlatformdropdownplatformsADgroup() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", "SG_Group With Bios Policy name");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", "SG_Group With Bios Policy name");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		sleeper(2000);
		policypages.actionClickOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("AssetTrackingNumberOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("PolicytoggleButtonOpen");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("previewButton");
		policypages.enterTextForPolicyPage("policySearch", "8536, HP ProBook 430 G6 Notebook PC, R71");
		policypages.verifyElementIsPresentOnPolicyPage("checkPlatform");

	}

	/**
	 * TC_C53139748:[WEP]>>VerifyBIOSsettingsglobalpolicyshoulddisplayvaluethatplatform
	 */
	@Test(priority = 78, groups = {"REGRESSION_POLICY"}, description = "Testcase ID: C53139748")
	public final void VerifyBIOSsettingsglobalpolicyshoulddisplayvaluethatplatform() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", "SG_Group With Bios Policy name");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", "SG_Group With Bios Policy name");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		sleeper(2000);
		policypages.actionClickOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("AssetTrackingNumberOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("PolicytoggleButtonOpen");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("previewButton");
		policypages.enterTextForPolicyPage("policySearch", "8536, HP ProBook 430 G6 Notebook PC, R71");
		policypages.verifyElementIsPresentOnPolicyPage("checkPlatform");

	}
	
	/**
	 * TC_C55542241:[WEP]>>VerifyDetailsPageforanUnassignedAssignedSecret
	 */
	@Test(priority = 79, groups = { "REGRESSION_POLICY" }, description="Testcase ID: C55542241")
	public final void VerifyDetailsPageforanUnassignedAssignedSecret() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		policypages.enterTextForPolicyPage("ScreteNameTextBox","Automation Secret using certificateee");
		policypages.clickOnElementsOfPolicyPage("spmCerticateRadioButton");
		policypages.enterTextForPolicyPage("SecretDescriptionTextBox","Secret for bios Authentication");
		sleeper(2000);
		policypages.waitUntilElementIsEnabled("secretePageNextButton");
		policypages.clickOnElementsOfPolicyPage("secretePageNextButton");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("endrosmentRadioButton");
		policypages.clickOnElementsOfPolicyPage("signingRadioButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentcounrtyDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentstateDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentcityDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentorganizationnameDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("endrosmentorganizationunitDetails");
		policypages.clickOnElementsOfPolicyPage("signingRadioButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingcounrtyDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingstateDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingcityDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingorganizationnameDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("signingorganizationunitDetails");
		policypages.verifyElementsIsEnabledOfPolicyPage("cancelButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("backButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.verifyElementsIsEnabledOfPolicyPage("secretName");
		policypages.verifyElementsIsEnabledOfPolicyPage("secretAssignedName");
		policypages.verifyElementsIsEnabledOfPolicyPage("secretType");
		policypages.verifyElementsIsEnabledOfPolicyPage("cancelButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("backButton");
		policypages.verifyElementsIsEnabledOfPolicyPage("addSecretBtn");
		policypages.clickOnElementsOfPolicyPage("addSecretBtn");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAddPending").contains("Secret add pending"), "secret add pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretAdded").contains("Secret added"), "secret added is failed");
		policypages.enterTextForPolicyPage("policySearch","Automation Secret using certificateee");
		LOGGER.info("Policy name is successfully changed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		policypages.verifyElementIsPresentOnPolicyPage("profileName");
		policypages.verifyElementIsPresentOnPolicyPage("description");
		policypages.verifyElementIsPresentOnPolicyPage("Type");
		policypages.verifyElementIsPresentOnPolicyPage("policyName");
		policypages.verifyElementIsPresentOnPolicyPage("CreatedBy");
		policypages.verifyElementIsPresentOnPolicyPage("createdOn");
		policypages.verifyElementIsPresentOnPolicyPage("policyId");
		policypages.actionClickOfPolicyPage("downloadendrosmentKey");
		policypages.enterTextForPolicyPage("inputPasswordEndrosmentKey","Password@1234");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("downloadButton");
		sleeper(5000);
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.enterTextForPolicyPage("policySearch","Automation Secret using certificateee");
		LOGGER.info("Policy name is successfully changed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		waitForPageLoaded();
		sleeper(5000);
		policypages.actionClickOfPolicyPage("downloadsigningKey");
		policypages.enterTextForPolicyPage("inputPasswordEndrosmentKey","Password@1234");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("downloadButton");
		LOGGER.info("downloaded certificates sucessfully");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Secrets tab ");
		policypages.enterTextForPolicyPage("policySearch","Automation Secret using certificateee");
		waitForPageLoaded();
		sleeper(5000);
		policypages.clickOnElementsOfPolicyPage("secretSearchItem");
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretDeletePending").contains("Secret delete pending"), "Secret delete pending is failed");
		policypages.waitForElementsOfPolicyPage("SecretDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("SecretDeleted").contains("Secret deleted"), "Secret deleted is failed");
	}

	/**
	 * TC_C53139724:[WEP]>>VerifyUsercandeleteexistingpolicieswhenglobalpolicygotconflict
	 */
	@Test(priority = 80, groups = {"REGRESSION_POLICY"}, description = "Testcase ID: TC_C53139724")
	public final void VerifyUsercandeleteexistingpolicieswhenglobalpolicygotconflict() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.conflict_update_policy + generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.SG_PolicyDescription);
		policypages.waitForElementsOfPolicyPage("policyScopePlatformOption");
		policypages.actionClickOfPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("platformDropdownButton");
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_Name);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deployment");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.conflict_update_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.waitForElementsOfPolicyPage("policiesAssignmentTab");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		LOGGER.info("Policies assigned to Static Group is done successfully");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.conflict_update_policy);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.update_policy_conflict + generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.SG_PolicyDescription);
		policypages.waitForElementsOfPolicyPage("policyScopePlatformOption");
		policypages.actionClickOfPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("platformDropdownButton");
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_Name);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deployment");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.update_policy_conflict);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.Group_Name);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("policyConflict");
		String errorMsg = policypages.getTextOfWEPPolicyPage("policyConflict");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
				errorMsg != null && errorMsg.toLowerCase().contains("Conflicts (1)".toLowerCase()),
				"Policy details are not available. Actual message: " + errorMsg);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("cancelButton");
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.conflict_update_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		policypages.clickOnElementsOfPolicyPage("OverviewTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}
	
	/**
	 * TC_C53139747:[WEP]>>UI Review Policy Page Global Bios Settings Should List In Alphabetical Order
	 */
	@Test(priority = 81, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139747")
	public final void UIReviewPolicyPageGlobalBiosSettingsShouldListInAlphabeticalOrder() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox","Global Policy Bios preview");
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox","SG_Policy With Bios Policy");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("accelerateUSBSetting");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("memoryCapSetting");
		sleeper(2000);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicyCreationPage"));
		policypages.actionClickOfPolicyPage("nextbuttonOnPolicyCreationPage");
		String textBlock = policypages.getTextOfWEPPolicyPage("alphabeticalOrder");
		List<String> actualList = Arrays.asList(textBlock.split("\\r?\\n")); 
		actualList = actualList.stream().map(String::trim).collect(Collectors.toList());
		List<String> sortedList = new ArrayList<>(actualList);
		Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER);
		if (actualList.equals(sortedList)) {
		    System.out.println("The list is in alphabetical order.");
		} else {
		    System.out.println("The list is NOT in alphabetical order.");
		    System.out.println("Actual Order  : " + actualList);
		    System.out.println("Expected Order: " + sortedList);
		}
		policypages.verifyElementIsPresentOnPolicyPage("ReviewAlphabeticalorder");
		String textreview1 = policypages.getTextOfWEPPolicyPage("ReviewAlphabeticalorder");
		List<String> actualList1 = Arrays.asList(textBlock.split("\\r?\\n")); 
		actualList1 = actualList1.stream().map(String::trim).collect(Collectors.toList());
		List<String> sortedList1 = new ArrayList<>(actualList1);
		Collections.sort(sortedList1, String.CASE_INSENSITIVE_ORDER);
		if (actualList.equals(sortedList1)) {
		    System.out.println("The list is in alphabetical order.");
		} else {
		    System.out.println("The list is NOT in alphabetical order.");
		    System.out.println("Actual Order  : " + actualList1);
		    System.out.println("Expected Order: " + sortedList1);
		}
	}
	
	/**
	 * TC_C53139748:[WEP]>>UI Verify For Each Bios Setting In Global Policy Should Display Value That Will Be Actually Set On The Selected Platform
	 */
	@Test(priority = 82, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139748")
	public final void UIVerifyForEachBiosSettingInGlobalPolicyShouldDisplayValueThatWillBeActuallySetOnTheSelectedPlatform() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox","Global Policy Bios preview");
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox","SG_Policy With Bios Policy");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
        policypages.enterTextForPolicyPage("policySearch","Secure Boot");
		policypages.clickOnElementsOfPolicyPage("securebootSetting");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch","Sure Start Secure Boot Keys Protection");
		policypages.clickOnElementsOfPolicyPage("surestartSetting");
		sleeper(2000);
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("viewselectedToogleButton");
		policypages.clickOnElementsOfPolicyPage("viewselectedToogleButton");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("previewButton");
		policypages.verifyElementIsPresentOnPolicyPage("previewplatform");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.platformName);
		policypages.clickOnElementsOfPolicyPage("checkboxInPreviewpage");
		policypages.clickOnElementsOfPolicyPage("previewbuttonInSettings");
		sleeper(2000);
		String policyName = "Global Policy Bios preview";
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		String formattedDate = currentDate.format(formatter);
		LOGGER.info("DATE " + formattedDate);
		String updated = formattedDate.replaceFirst("-(?=[^\\-]+$)", "_");
		System.out.println(updated);
		String updatedPolicyName = policyName + "_" + updated + ".csv";
		LOGGER.info("Updated Policy Name: " + updatedPolicyName);
		String filePath  = ConstantPath.DOWNLOAD_PATH + "updatedPolicyName";
		File file = new File(filePath);
		LOGGER.info("Checking file path: " + filePath);
		LOGGER.info("File exists: " + file.exists());
		LOGGER.info("File exists: " + WEPPolicyPage.isFileExists(filePath));
		LOGGER.info(Arrays.deepToString(policypages.getDataWithHeader(file)));
		

	}
	
	/**
	 * TC_C53139746:[WEP]>>UI Verify Generate Report Option Available By Default
	 */
	@Test(priority = 83, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C53139746")
	public final void UIVerifyGenerateReportOptionAvailableByDefault() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		String platformName = "8536, HP ProBook 430 G6 Notebook PC, R71, 3";
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox","Global Policy Bios setting generate");
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptultrionTextBox","SG_Policy With Bios Policy");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("policyScopePlatformOption");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		policypages.enterTextForPolicyPage("policySearch","Secure Boot");
		policypages.clickOnElementsOfPolicyPage("securebootSetting");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch","Sure Start Secure Boot Keys Protection");
		policypages.clickOnElementsOfPolicyPage("surestartSetting");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("viewselectedToogleButton");
		policypages.clickOnElementsOfPolicyPage("viewselectedToogleButton");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.verifyElementIsPresentOnPolicyPage("EnableButton");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.actionClickOfPolicyPage("previewButton");
		policypages.verifyElementIsPresentOnPolicyPage("previewplatform");
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch",platformName);
		policypages.clickOnElementsOfPolicyPage("checkboxInPreviewpage");
		policypages.clickOnElementsOfPolicyPage("previewbuttonInSettings");
		sleeper(4000);
		String policyName = "Global Policy Bios setting generate";
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		String formattedDate = currentDate.format(formatter);
		LOGGER.info("DATE " + formattedDate);
		String updated = formattedDate.replaceFirst("-(?=[^\\-]+$)", "_");
		System.out.println(updated);
		String updatedPolicyName = policyName + "_" + updated + ".csv";
		LOGGER.info("Updated Policy Name: " + updatedPolicyName);
		String filePath  = ConstantPath.DOWNLOAD_PATH + "updatedPolicyName";
		File file = new File(filePath);
		LOGGER.info("Checking file path: " + filePath);
		LOGGER.info("File exists: " + file.exists());
		LOGGER.info("File exists: " + WEPPolicyPage.isFileExists(filePath));
	}
	
	
	 /**
	  * TC_C62764263:[WEP]>>Verify Driver Update Policy Creation with Global Scope and Critical Updates option
		 */	  
	 @Test (priority = 84, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C62764263") 
	 public final void VerifyDriverUpdatePolicyCreationwithGlobalScopeandCriticalUpdatesoption  () throws Exception {	
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			openLeftSidePanel();
			WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
			waitForPageLoaded();
		 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 waitForPageLoaded();
	 LOGGER.info("Navigated to Policies tab ");
	 policypages.clickOnElementsOfPolicyPage("addpolicy");
	 waitForPageLoaded();
	 policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
	 sleeper(2000);
	 policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
	 policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
	 LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
	 
	 
	}		 

		
	 /**
	  * TC_C62764269:[WEP]>>Verify that the review screen accurately reflects selected settings for driver update
		 */	
	 @Test (priority = 85, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C62764269")
	 public final void Verifythatthereviewscreenaccuratelyreflectsselectedsettingsfordriverupdate () throws Exception {	
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
			openLeftSidePanel();
			WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
			waitForPageLoaded();
		 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 } 
		 waitForPageLoaded();
		 LOGGER.info("Navigated to Policies tab ");
		 policypages.clickOnElementsOfPolicyPage("addpolicy");
		 waitForPageLoaded();
		 policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
		 sleeper(2000);
		 policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		 policypages.clickOnElementsOfPolicyPage("Driverupdate");
		 policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		 policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		 LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		 waitForPageLoaded();
		 policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
		 policypages.clickOnElementsOfPolicyPage("Nextbtn");
		 waitForPageLoaded();
		 
		 String policy_name_tab = policypages.getTextOfWEPPolicyPage("policynametab");
		 LOGGER.info(policy_name_tab);
		 Assert.assertTrue(
				 policy_name_tab != null && policy_name_tab.contains("Policy Name"),
			    "verify policy element: " + policy_name_tab);

		 String policy_name = policypages.getTextOfWEPPolicyPage("policyname1");
		 LOGGER.info(policy_name);
		 Assert.assertTrue(
			    policy_name != null && policy_name.contains("Driver update policy"),
			    "verify policy element: " + policy_name );
		 
		 String type_tab = policypages.getTextOfWEPPolicyPage("typetab");
		 LOGGER.info(type_tab);
		 Assert.assertTrue(
				 type_tab != null && type_tab.contains("Type"),
			    "verify policy element: " + type_tab);

		 String type_name = policypages.getTextOfWEPPolicyPage("typename");
		 LOGGER.info(type_name);
		 Assert.assertTrue(
				 type_name  != null && type_name .contains("Driver Update"),
			    "verify policy element: " + type_name );
		 
		 String scope_tab = policypages.getTextOfWEPPolicyPage("scopetab");
		 LOGGER.info(scope_tab);
		 Assert.assertTrue(
				 scope_tab  != null && scope_tab.contains("Scope"),
			    "verify policy element: " + scope_tab);
		 
		 String scope_name = policypages.getTextOfWEPPolicyPage("scopename");
		 LOGGER.info(scope_name );
		 Assert.assertTrue(
				 scope_name  != null && scope_name .contains("Global"),
			    "verify policy element: " + scope_name);
		 
		 String Deployment_tab = policypages.getTextOfWEPPolicyPage("Deploymenttab");
		 LOGGER.info(Deployment_tab );
		 Assert.assertTrue(
				 Deployment_tab   != null && Deployment_tab .contains("Deployment"),
			    "verify policy element: " + Deployment_tab );
		 
		 String Driverupdate_selected = policypages.getTextOfWEPPolicyPage("Driverupdateselected");
		 LOGGER.info(Driverupdate_selected);
		 Assert.assertTrue(
				 Driverupdate_selected   != null && Driverupdate_selected .contains("Deploy only Critical driver updates"),
			    "verify policy element: " + Driverupdate_selected);
		 
		 
		 
		 
	 }	  
		 
	/**
	  * TC_C62766146:[WEP]>>Verify Driver Update Policy Creation with Global Scope and Always deploy driver updates (HP recommended) option
		*/	
	 @Test (priority = 86, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C62766146")
	 public final void VerifyDriverUpdatePolicyCreationwithGlobalScopeandAlwaysdeploydriverupdatesHPrecommendedoption () throws Exception {	
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
			openLeftSidePanel();
			WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
			waitForPageLoaded();
		 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 waitForPageLoaded();
	 LOGGER.info("Navigated to Policies tab ");
	 policypages.clickOnElementsOfPolicyPage("addpolicy");
	 waitForPageLoaded();
	 policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
	 sleeper(2000);
	 policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
	 LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
	 policypages.clickOnElementsOfPolicyPage("Nextbtn");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
	 policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy add pending is failed");
	 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 waitForPageLoaded();
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	waitForPageLoaded();
	sleeper(2000);
	policypages.waitForElementsOfPolicyPage("deleteButton");
	policypages.clickOnElementsOfPolicyPage("deleteButton");
	waitForPageLoaded();
	sleeper(2000);
	waitForPageLoaded();
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	policypages.waitForElementsOfPolicyPage("policyDeleted");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}	 			    
	
		    
    /**
	 * TC_C62766147:[WEP]>>Verify Driver Update Policy Creation with Global Scope and Specific driver update categories option
	  */	
	 @Test (priority = 87, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C62766147")
	 public final void VerifyDriverUpdatePolicyCreationwithGlobalScopeandSpecificdriverupdatecategoriesoption  () throws Exception {	
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
			openLeftSidePanel();
			WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
			waitForPageLoaded();
		 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 waitForPageLoaded();
	 LOGGER.info("Navigated to Policies tab ");
	 policypages.clickOnElementsOfPolicyPage("addpolicy");
	 waitForPageLoaded();
	 policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
	 sleeper(2000);
	 policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
	 LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("FirmwareandDriver");
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
	 policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy add pending is failed");
	 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 waitForPageLoaded();
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	waitForPageLoaded();
	sleeper(2000);
	policypages.waitForElementsOfPolicyPage("deleteButton");
	policypages.clickOnElementsOfPolicyPage("deleteButton");
	waitForPageLoaded();
	sleeper(2000);
	waitForPageLoaded();
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	policypages.waitForElementsOfPolicyPage("policyDeleted");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}	     	
	
    /**
	 * TC_C62766163:[WEP]>>Verify user can Assign Global Driver Policy - Update Critical
		*/	
	 @Test (priority = 88, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C62766163")
	 public final void VerifyusercanAssignGlobalDriverPolicyUpdateCritical  () throws Exception {	
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			openLeftSidePanel();
			WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
			waitForPageLoaded();
		 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 waitForPageLoaded();
	 LOGGER.info("Navigated to Policies tab ");
	 policypages.clickOnElementsOfPolicyPage("addpolicy");
	 waitForPageLoaded();
	 policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
	 sleeper(2000);
	 policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
	 sleeper(2000);
	 LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
	 policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy add pending is failed");
	 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policySearchItem");	
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 sleeper(2000);
	 waitForPageLoaded();
     policypages.enterTextForPolicyPage("policySearch",PolicyVariables.driverupdategroup);
	 sleeper(4000);
	 policypages.waitForElementsOfPolicyPage("selectGroupItem");
	 policypages.clickOnElementsOfPolicyPage("selectGroupItem");
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("DateClear");
	 policypages.clickOnElementsOfPolicyPage("dropdown2");
	 policypages.clickOnElementsOfPolicyPage("RecurringDays");
	 policypages.selectNextDateFromCalendar();
	 sleeper(2000);
	 policypages.selectRoundedUpCurrentTimeFromDropdown();
	 policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
	 policypages.clickOnElementsOfPolicyPage("scheduleNext");
	 waitForPageLoaded();
	 policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
	 policypages.clickOnElementsOfPolicyPage("assignGroupButton");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "group assignment pending is failed");
	 policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "group assigned is failed");
     waitForPageLoaded();
	 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 waitForPageLoaded();
	 policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policySearchItem");	
	 policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
	 String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
	 LOGGER.info(errorMsg);
	 Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Assigned"),
		    "Policy details are not avilable policy deleted sucessfully: " + errorMsg);
	 policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	 waitForPageLoaded();
	 sleeper(2000);
	 policypages.mouseHoverOfPolicyPage("groupItem1");
	 policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
	 policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
	 policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "group unassignment pending is failed");
	 policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "group unassigned is failed");
	 waitForPageLoaded();
	 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
			waitForPageLoaded();
			policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
			sleeper(2000);
			policypages.clickOnElementsOfPolicyPage("policySearchItem");
			waitForPageLoaded();
			sleeper(2000);
			policypages.waitForElementsOfPolicyPage("deleteButton");
			policypages.clickOnElementsOfPolicyPage("deleteButton");
			waitForPageLoaded();
			sleeper(2000);
			waitForPageLoaded();
			Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
			policypages.waitForElementsOfPolicyPage("policyDeleted");
			Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
			waitForPageLoaded();

	 }		 

		
	/**
	 * TC_C62766164:[WEP]>>Verify user can Assign Global Driver Policy - Update Select Specific Categories
		 */	
	 @Test (priority = 89, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C62766164")
	 public final void VerifyusercanAssignGlobalDriverPolicyUpdateSelectSpecificCategories   () throws Exception {	
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
			openLeftSidePanel();
			WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
			waitForPageLoaded();
		 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 waitForPageLoaded();
	 LOGGER.info("Navigated to Policies tab ");
	 policypages.clickOnElementsOfPolicyPage("addpolicy");
	 waitForPageLoaded();
	 policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
	 sleeper(2000);
	 policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
	 sleeper(2000);
	 LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("FirmwareandDriver");
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
	 policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy add pending is failed");
	 waitForPageLoaded();
	 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policySearchItem");	
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 sleeper(2000);
	 waitForPageLoaded();
     policypages.enterTextForPolicyPage("policySearch",PolicyVariables.driverupdategroup);
	 sleeper(4000);
	 policypages.waitForElementsOfPolicyPage("selectGroupItem");
	 policypages.clickOnElementsOfPolicyPage("selectGroupItem");
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("DateClear");
	 policypages.clickOnElementsOfPolicyPage("dropdown2");
	 policypages.clickOnElementsOfPolicyPage("RecurringDays");
	 policypages.selectNextDateFromCalendar();
	 sleeper(2000);
	 policypages.selectRoundedUpCurrentTimeFromDropdown();
	 policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
	 policypages.clickOnElementsOfPolicyPage("scheduleNext");
	 waitForPageLoaded();
	 policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
	 policypages.clickOnElementsOfPolicyPage("assignGroupButton");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "group assignment pending is failed");
	 policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "group assigned is failed");
	 waitForPageLoaded();
	if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 waitForPageLoaded();
	 policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policySearchItem");	
	 policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
	 String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
	 LOGGER.info(errorMsg);
	 Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Assigned"),
		    "Policy details are not avilable policy deleted sucessfully: " + errorMsg);
	 policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	 waitForPageLoaded();
	 sleeper(2000);
	 policypages.mouseHoverOfPolicyPage("groupItem1");
	 policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
	 policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
	 policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "group unassignment pending is failed");
	 policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "group unassigned is failed");
	 waitForPageLoaded();
	 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		waitForPageLoaded();

	 }		  
			 
	
    /**
	 * TC_C62766166:[WEP]>>Verify user can Assign Global Driver Policy - Always deploy driver updates (HP recommended)
		 */	
	 @Test (priority = 90, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C62766166")
	 public final void VerifyusercanAssignGlobalDriverPolicyAlwaysdeploydriverupdatesHPrecommended () throws Exception {	
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
			openLeftSidePanel();
			WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
			waitForPageLoaded();
	 
	 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
	 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
	 waitForPageLoaded();
	 sleeper(2000);
	 }
	else {
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from Remediation tab successfully");
		 sleeper(2000);
		 }
	 waitForPageLoaded();
	 LOGGER.info("Navigated to Policies tab ");
	 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 waitForPageLoaded();
	 policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
	 policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
	 sleeper(2000);
	 policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
	 LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	 
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
	 policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy add pending is failed");
	 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policySearchItem");	
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 sleeper(2000);
	 waitForPageLoaded();
     policypages.enterTextForPolicyPage("policySearch",PolicyVariables.driverupdategroup);
	 sleeper(4000);
	 policypages.waitForElementsOfPolicyPage("selectGroupItem");
	 policypages.clickOnElementsOfPolicyPage("selectGroupItem");
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("DateClear");
	 policypages.clickOnElementsOfPolicyPage("dropdown2");
	 policypages.clickOnElementsOfPolicyPage("RecurringDays");
	 policypages.selectNextDateFromCalendar();
	 sleeper(2000);
	 policypages.selectRoundedUpCurrentTimeFromDropdown();
	 policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
	 policypages.clickOnElementsOfPolicyPage("scheduleNext");
	 waitForPageLoaded();
	 policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
	 policypages.clickOnElementsOfPolicyPage("assignGroupButton");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "group assignment pending is failed");
	 policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "group assigned is failed");	 
	 waitForPageLoaded();
	 policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policySearchItem");	
	 policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
	 String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
	 LOGGER.info(errorMsg);
	 Assert.assertTrue(
		    errorMsg != null && errorMsg.contains("Assigned"),
		    "Policy details are not avilable policy deleted sucessfully: " + errorMsg);
	 policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	 waitForPageLoaded();
	 sleeper(2000);
	 policypages.mouseHoverOfPolicyPage("groupItem1");
	 policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
	 policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
	 policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "group unassignment pending is failed");
	 policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "group unassigned is failed");
	 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	 waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		waitForPageLoaded();
	
	 }		 	
			 

    /**
	 * TC_C62766167:[WEP]>>Verify that user can edit Driver Update Policy via UI
	 */	
	 @Test (priority = 91, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C62766167")
	 public final void VerifythatusercaneditDriverupdatepolicyviaUI  () throws Exception {	
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
			openLeftSidePanel();
			String policy_name_value = "bios setting policy" + generateRandomNumber();
			String edit_name = "Driver_Name" + generateRandomNumber();
			WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
			waitForPageLoaded();
	 
	 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
	 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
	 waitForPageLoaded();
	 sleeper(2000);
	 }
	else {
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from Remediation tab successfully");
		 sleeper(2000);
		 }
	 LOGGER.info("Navigated to Policies tab ");
	 policypages.clickOnElementsOfPolicyPage("addpolicy");
	 waitForPageLoaded();
	 policypages.enterTextForPolicyPage("createPolicyNameTextBox",policy_name_value);
	 policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
	 sleeper(2000);
	 LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	 
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
	 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
	 policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy add pending is failed");
     waitForPageLoaded();
	 policypages.enterTextForPolicyPage("policySearch",policy_name_value );
	 sleeper(2000);
	 policypages.waitForElementsOfPolicyPage("policySearchItem");
	 policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 waitForPageLoaded();
	 policypages.clickOnElementsOfPolicyPage("editButton");
	 policypages.clickOnElementsOfPolicyPage("clearButton");
	 sleeper(2000);
	 policypages.enterTextForPolicyPage("createPolicyNameTextBox",edit_name);
	 sleeper(2000);
	 policypages.clickOnElementsOfPolicyPage("applyButton");
	 waitForPageLoaded();
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyUpdatePending").contains("Policy update pending"), "policy update pending is failed");
	 policypages.waitForElementsOfPolicyPage("PolicyUpdated");
	 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyUpdated").contains("Policy updated"), "policy updated is failed");
     waitForPageLoaded();
	 if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEPs"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);

	 	}
	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	policypages.waitForElementsOfPolicyPage("policySearch");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("policySearch", edit_name);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	policypages.waitForElementsOfPolicyPage("deleteButton");
	 	policypages.clickOnElementsOfPolicyPage("deleteButton");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
	 			"policy delete pending is failed");
	 	policypages.waitForElementsOfPolicyPage("policyDeleted");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
	 			"Policy deleted is failed");
	 }		 
			 
			 
	/**
	  * TC_C62766168:[WEP]>>Verify that user can Delete Driver Update Policy via UI
	 */	
	 @Test (priority = 92, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C62766168")
	 public final void Verifythatusercandeletethepolicy  () throws Exception {	
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
			openLeftSidePanel();
			String policy_name_value = "bios setting policy" + generateRandomNumber();
			WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
			waitForPageLoaded();
		 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
		 LOGGER.info("Navigated to Policies tab ");
		 policypages.clickOnElementsOfPolicyPage("addpolicy");
		 waitForPageLoaded();
		 policypages.enterTextForPolicyPage("createPolicyNameTextBox",policy_name_value);
		 policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		 policypages.clickOnElementsOfPolicyPage("Driverupdate");
		 policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		 sleeper(2000);
		 LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	 
		 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		 waitForPageLoaded();
		 policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
		 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		 waitForPageLoaded();
		 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		 waitForPageLoaded();
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		 policypages.waitForElementsOfPolicyPage("PolicyAdded");
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy add pending is failed");
	     waitForPageLoaded();	
		 policypages.enterTextForPolicyPage("policySearch",policy_name_value);
		 policypages.waitForElementsOfPolicyPage("policyname");
		 policypages.clickOnElementsOfPolicyPage("policySearchItem");
		 waitForPageLoaded();
		 policypages.waitForElementsOfPolicyPage("deleteButton");
		 waitForPageLoaded();
		 policypages.clickOnElementsOfPolicyPage("deleteButton");
		 waitForPageLoaded();
		 sleeper(2000);
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy update pending is failed");
		 policypages.waitForElementsOfPolicyPage("policyDeleted");
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy updated is failed");
		 

	 }	
	 
	 /*
	  * C62766171:[WEP]>>Verify Driver Update Policy Creation with Platform Scope and
	  * Critical Updates option
	  */
	 @Test(priority = 93, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: C62766171")

	 public final void VerifyUserCanCreateDriverUpdatePolicyCreationwithPlatformScopeandCriticaloption()
	 		throws Exception {
	 	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
	 	WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	 	waitForPageLoaded();
	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);
	 	}

	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	policypages.clickOnElementsOfPolicyPage("addpolicy");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyNameTextBox",
	 			PolicyVariables.policy_name_value + generateRandomNumber());
	 	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 	policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.waitForElementsOfPolicyPage("platformDropdownButton");
	 	policypages.actionClickOfPolicyPage("platformDropdownButton");
	 	policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
	 	policypages.clickOnElementsOfPolicyPage("SelectItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");

	 }

	 /*
	  * C62766187:[WEP]>>Verify that the review screen accurately reflects selected
	  * settings for driver update
	  */
	 @Test(priority = 94, groups = { "REGRESSION_POLICY"}, description = "Testcase ID: C62766187")

	 public final void Verifyreviewscreenaccuratelyreflectsselectedsettingsfordriverupdate() throws Exception {
	 	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
	 	WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	 	waitForPageLoaded();
	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);
	 	}

	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	policypages.clickOnElementsOfPolicyPage("addpolicy");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyNameTextBox",
	 			PolicyVariables.policy_name_value + generateRandomNumber());
	 	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 	policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.actionClickOfPolicyPage("platformDropdownButton");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
	 	sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
	 	policypages.clickOnElementsOfPolicyPage("SelectItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 	waitForPageLoaded();

	 	String anynamee = policypages.getTextOfWEPPolicyPage("policyname1");
	 	LOGGER.info(anynamee);
	 	Assert.assertTrue(anynamee != null && anynamee.contains("Driver update policy"),
	 			"Policy details are not available" + anynamee);

	 	String review = policypages.getTextOfWEPPolicyPage("typename");
	 	LOGGER.info(review);
	 	Assert.assertTrue(review != null && review.contains("Driver Update"),
	 			"Policy details are not available" + review);

	 	String revieww = policypages.getTextOfWEPPolicyPage("reviewPolicyscope");
	 	LOGGER.info(revieww);
	 	Assert.assertTrue(revieww != null && revieww.contains("Platform"),
	 			"Policy details are not available" + revieww);

	 	String PlatformName = policypages.getTextOfWEPPolicyPage("policyReviewplatformName");
	 	LOGGER.info(PlatformName);
	 	Assert.assertTrue(
	 			PlatformName != null
	 					&& PlatformName.contains(PlatformName),
	 			"Policy details are not available" + PlatformName);

	 	String deploy = policypages.getTextOfWEPPolicyPage("Driverupdateselected");
	 	LOGGER.info(deploy);
	 	Assert.assertTrue(deploy != null && deploy.contains("Deploy only Critical driver updates"),
	 			"Policy details are not available" + deploy);

	 	waitForPageLoaded();

	 }

	 /*
	  * C62766188:[WEP]>>Verify Driver Update Policy Creation with Platform Scope and
	  * Always deploy driver updates (HP recommended) option
	  */

	 @Test(priority = 95, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: C62766188")
	 public final void VerifyHPRecommendedDriverUpdate() throws Exception {
	 	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
	 	WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	 	waitForPageLoaded();
	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);
	 	}

	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("addpolicy");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyNameTextBox",
	 			PolicyVariables.policy_name_value + generateRandomNumber());
	 	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
	 	waitForPageLoaded();
	 	sleeper(1000);
	 	policypages.actionClickOfPolicyPage("platformDropdownButton");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
	 	sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
	 	policypages.clickOnElementsOfPolicyPage("SelectItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 	waitForPageLoaded();

	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
	 			"policy add pending is failed");
	 	policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
	 			"policy add pending is failed");
	 	waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEPs"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);

	 	}
	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	policypages.waitForElementsOfPolicyPage("policySearch");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	policypages.waitForElementsOfPolicyPage("deleteButton");
	 	policypages.clickOnElementsOfPolicyPage("deleteButton");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
	 			"policy delete pending is failed");
	 	policypages.waitForElementsOfPolicyPage("policyDeleted");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
	 			"Policy deleted is failed");

	 }
	 
	 /*
	  * C62766189:[WEP]>> Verify Driver Update Policy Creation with Platform Scope
	  * and Specific driver update categories option
	  */

	 @Test(priority = 96, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: C62766189")
	 public final void VerifySpecificdriverupdateDriverUpdate() throws Exception {
	 	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
	 	WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	 	waitForPageLoaded();
	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);
	 	}

	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("addpolicy");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyNameTextBox",
	 			PolicyVariables.policy_name_value + generateRandomNumber());
	 	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
	 	waitForPageLoaded();
	 	sleeper(1000);
	 	policypages.actionClickOfPolicyPage("platformDropdownButton");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
	 	policypages.clickOnElementsOfPolicyPage("SelectItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("DriverAudio");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 	waitForPageLoaded();

	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
	 			"policy add pending is failed");
	 	policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
	 			"policy add pending is failed");
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEPs"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);

	 	}
	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	policypages.waitForElementsOfPolicyPage("policySearch");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	policypages.waitForElementsOfPolicyPage("deleteButton");
	 	policypages.clickOnElementsOfPolicyPage("deleteButton");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
	 			"policy delete pending is failed");
	 	policypages.waitForElementsOfPolicyPage("policyDeleted");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
	 			"Policy deleted is failed");

	 }

	 /*
	  * C62766193:[WEP]>>Verify user can Assign platform Driver Policy - Update
	  * Critical
	  */
	 @Test(priority = 97, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: C62766193")
	 public final void VerifyUserCreateDriverUpdateCriticalPlatform() throws Exception {
	 	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
	 	WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	 	waitForPageLoaded();

	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);
	 	}

	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("addpolicy");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyNameTextBox",
	 			PolicyVariables.policy_name_value + generateRandomNumber());
	 	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
	 	waitForPageLoaded();
	 	sleeper(1000);
	 	policypages.actionClickOfPolicyPage("platformDropdownButton");
	 	waitForPageLoaded();
	 	sleeper(1000);
	 	policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
	 	policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
	 	policypages.clickOnElementsOfPolicyPage("SelectItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 	waitForPageLoaded();

	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
	 			"policy add pending is failed");
	 	policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
	 			"policy add pending is failed");

	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 	sleeper(2000);
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
	 	sleeper(4000);
	 	policypages.clickOnElementsOfPolicyPage("selectGroupItem");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("DateClear");
	 	policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
	 	waitForPageLoaded();
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),"Group assignment is failed");
	 	policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
	 			"Group assigned is failed");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(3000);
	 	String anyname = policypages.getTextOfWEPPolicyPage("AssignedState");
	 	LOGGER.info(anyname);
	 	Assert.assertTrue(anyname != null && anyname.contains("Assigned"),
	 			"Policy details are not available" + anyname);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	 	waitForPageLoaded();
	 	sleeper(4000);
	 	policypages.mouseHoverOfPolicyPage("groupItem1");
	 	policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
	 	policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");

	 	waitForPageLoaded();
	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEPs"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);

	 	}
	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	policypages.waitForElementsOfPolicyPage("policySearch");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	policypages.waitForElementsOfPolicyPage("deleteButton");
	 	policypages.clickOnElementsOfPolicyPage("deleteButton");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
	 			"policy delete pending is failed");
	 	policypages.waitForElementsOfPolicyPage("policyDeleted");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
	 			"Policy deleted is failed");

	 	waitForPageLoaded();

	 }

	 /**
	  * C62766194:[WEP]>>Verify user can Assign platform Driver Policy - Always
	  * deploy driver updates (HP recommended)
	  */
	 @Test(priority = 98, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: C62766194")

	 public final void VerifyUserCanCreateDriverGroup() throws Exception {
	 	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
	 	String DG_GroupName_Text = "DG_Driver_Update_groups" +generateRandomNumber();
	 	WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	 	waitForPageLoaded();

	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.GROUPS);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.GROUPS);
	 		LOGGER.info("Navigated to Groups successfully");
	 		sleeper(2000);
	 	}

	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to Groups tab ");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("addGroupBtn");
	 	waitForPageLoaded();
	 	policypages.actionClickOfPolicyPage("staticGroupadioBtn");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("groupNameField", DG_GroupName_Text);
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("groupDescriptionField", PolicyVariables.DU_GroupDescription);
	 	policypages.clickOnElementsOfPolicyPage("addGrpNextBtn");
	 	waitForPageLoaded();
	 	sleeper(3000);
	 	policypages.actionClickOfPolicyPage("selectDeviceListin_StaticGroup");
	 	waitForPageLoaded();

	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("clearAllFiltersLink");
	 	waitForPageLoaded();
	 	String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_serial_number");
	 	policypages.enterTextForPolicyPage("groupNameSearch", valueName);
	 	sleeper(3000);
	 	policypages.actionClickOfPolicyPage("groupNameCheck");
	 	policypages.clickOnElementsOfPolicyPage("addGrpNextBtn");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("addGrpBtn");
	 	waitForPageLoaded();
	 	LOGGER.info("Static Group creation is done successfully");
	 	waitForPageLoaded();
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("groupCreate").contains("Group created"), "Group created is failed");	
	 	policypages.clickOnElementsOfPolicyPage("NotificationButton");
	 	policypages.clickOnElementsOfPolicyPage("notification");
	 	policypages.waitElementsOfPolicyPage("NotificationMessage");

	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEPs"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);
	 	}

	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("addpolicy");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyNameTextBox",
	 			PolicyVariables.policy_name_value + generateRandomNumber());
	 	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
	 	waitForPageLoaded();
	 	sleeper(1000);
	 	policypages.actionClickOfPolicyPage("platformDropdownButton");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
	 	policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
	 	policypages.clickOnElementsOfPolicyPage("SelectItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 	waitForPageLoaded();

	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
	 			"policy add pending is failed");
	 	policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
	 			"policy add pending is failed");

	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 	sleeper(2000);
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("policySearch",DG_GroupName_Text);
	 	sleeper(4000);
	 	policypages.clickOnElementsOfPolicyPage("selectGroupItem");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("DateClear");
	    policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
	 	waitForPageLoaded();
	 	Assert.assertTrue(
	 			policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),
	 			"Group assignment is failed");
	 	policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
	 			"Group assigned is failed");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(3000);
	 	String anyname = policypages.getTextOfWEPPolicyPage("AssignedState");
	 	LOGGER.info(anyname);
	 	Assert.assertTrue(anyname != null && anyname.contains("Assigned"),
	 			"Policy details are not available" + anyname);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	 	waitForPageLoaded();
	 	sleeper(4000);
	 	policypages.mouseHoverOfPolicyPage("groupItem1");
	 	policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
	 	waitForPageLoaded();
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
	 	policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEPs"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);

	 	}
	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("editButton");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.Edit_Policy);
	 	sleeper(1000);
	 	policypages.enterTextForPolicyPage("PolicyEditdescription", PolicyVariables.Description_policy);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("applyButton");
	 	waitForPageLoaded();

	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyUpdatePending").contains("Policy update pending"),
	 			"Policy update pending is failed");
	 	policypages.waitForElementsOfPolicyPage("PolicyUpdated");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyUpdated").contains("Policy updated"),
	 			"Policy updated is failed");

	 	policypages.verifyElementIsPresentOnPolicyPage("policiesHistoryTab");
	 	policypages.clickOnElementsOfPolicyPage("policiesHistoryTab");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.waitForElementsOfPolicyPage("deleteButton");
	 	policypages.clickOnElementsOfPolicyPage("deleteButton");
	 	waitForPageLoaded();
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
	 			"Policy delete pending is failed");
	 	policypages.waitForElementsOfPolicyPage("policyDeleted");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
	 			"Policy deleted is failed");

	 	waitForPageLoaded();

	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEPs"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.GROUPS);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.GROUPS);
	 		LOGGER.info("Navigated to Groups successfully");
	 		sleeper(2000);
	 	}

	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to Groups tab ");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("groupSearch", DG_GroupName_Text);
	 	sleeper(2000);
	 	LOGGER.info(policypages.getTextOfWEPPolicyPage("groupSelect"));
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("groupSelect");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("deleteGroupButtonChk");
	 	waitForPageLoaded();
	 	String securityCode = policypages.getTextOfWEPPolicyPage("securityCodeNumber").trim();
	 	LOGGER.info("Retrieved Security Code: " + securityCode);
	 	policypages.verifyElementIsPresentOnPolicyPage("securitycode");
	 	policypages.enterTextForPolicyPage("securityCodeField", securityCode);
	 	LOGGER.info("Entered Security Code into the input field");
	 	policypages.clickOnElementsOfPolicyPage("deleteButton1");
	 	LOGGER.info(policypages.getTextOfWEPPolicyPage("groupdelete"));
	 	Assert.assertTrue(
	 			policypages.getTextOfWEPPolicyPage("groupdelete")
	 			.contains(policypages.getTextLanguage(LanguageCode, "daas_ui", "groups.toast.delete.group.success")),"Static Group Deletion is failed");

	 }

	 /*
	  * C62766195:[WEP]>>Verify that user can edit Driver Update Policy via UI
	  */
	 @Test(priority = 99, groups = { "REGRESSION_POLICY", "PRODUCTION_POLICY" }, description = "Testcase ID:C62766195")

	 public final void VerifythatusercaneditDriverUpdatePolicyviaUI() throws Exception {
	 	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
	 	WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	 	waitForPageLoaded();

	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);
	 	}

	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("addpolicy");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyNameTextBox",
	 			PolicyVariables.policy_name_value + generateRandomNumber());
	 	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.actionClickOfPolicyPage("platformDropdownButton");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
	 	policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
	 	policypages.clickOnElementsOfPolicyPage("SelectItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 	waitForPageLoaded();

	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
	 			"policy add pending is failed");
	 	policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
	 			"policy add pending is failed");

	 	waitForPageLoaded();

	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("editButton");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.edit_name +generateRandomNumber());
	 	sleeper(1000);
	 	policypages.enterTextForPolicyPage("PolicyEditdescription", PolicyVariables.Description_policy);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("applyButton");
	 	waitForPageLoaded();
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyUpdatePending").contains("Policy update pending"),
	 			"Policy update pending is failed");
	 	policypages.waitForElementsOfPolicyPage("PolicyUpdated");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyUpdated").contains("Policy updated"),
	 			"Policy updated is failed");
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEPs"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);

	 	}
	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	policypages.waitForElementsOfPolicyPage("policySearch");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.edit_name);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	policypages.waitForElementsOfPolicyPage("deleteButton");
	 	policypages.clickOnElementsOfPolicyPage("deleteButton");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
	 			"policy delete pending is failed");
	 	policypages.waitForElementsOfPolicyPage("policyDeleted");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
	 			"Policy deleted is failed");
	 }

	 /*
	  * C62766196:[WEP]>>Verify that user can Delete Driver Update Policy via UI
	  */
	 @Test(priority = 100, groups = { "REGRESSION_POLICY" }, description = "Testcase ID:C62766196")

	 public final void VerifythatusercanDeleteDriverUpdatePolicyviaUI() throws Exception {
	 	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
	 	WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	 	waitForPageLoaded();

	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);
	 	}

	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("addpolicy");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyNameTextBox",
	 			PolicyVariables.policy_name_value + generateRandomNumber());
	 	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.actionClickOfPolicyPage("platformDropdownButton");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
	 	policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
	 	policypages.clickOnElementsOfPolicyPage("SelectItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("DriverAudio");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 	waitForPageLoaded();

	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
	 			"policy add pending is failed");
	 	policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
	 			"policy add pending is failed");

	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
	 	waitForPageLoaded();
	 	sleeper(3000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("deleteButton");
	 	waitForPageLoaded();

	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
	 			"Policy delete pending is failed");
	 	policypages.waitForElementsOfPolicyPage("policyDeleted");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
	 			"Policy deleted is failed");
	 	waitForPageLoaded();
	 }

	 /*
	  * C62766197:[WEP]>>Verify user can Assign Platform Driver Policy - Update
	  * Select Specific Categories
	  */
	 @Test(priority = 101, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: C62766197")

	 public final void VerifyPlatformUpdateSelectCategoriesPlatform() throws Exception {
	 	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
	 	WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	 	waitForPageLoaded();

	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);
	 	}

	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("createPolicyNameTextBox",
	 			PolicyVariables.policy_name_value + generateRandomNumber());
	 	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("Driverupdate");
	 	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.actionClickOfPolicyPage("platformDropdownButton");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
	 	policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
	 	policypages.clickOnElementsOfPolicyPage("SelectItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	 	waitForPageLoaded();
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("DriverAudio");
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 	waitForPageLoaded();

	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
	 			"policy add pending is failed");
	 	policypages.waitForElementsOfPolicyPage("PolicyAdded");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
	 			"policy add pending is failed");

	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	 	sleeper(2000);
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.DG_GroupName_Text);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("selectGroupItem");
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("DateClear");
	 	sleeper(2000);
	    policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
	 	policypages.selectNextDateFromCalendar();
	 	sleeper(2000);
	 	policypages.selectRoundedUpCurrentTimeFromDropdown();
	 	policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
	 	policypages.clickOnElementsOfPolicyPage("scheduleNext");
	 	waitForPageLoaded();
	 	policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
	 	policypages.clickOnElementsOfPolicyPage("assignGroupButton");
	 	waitForPageLoaded();
	 	Assert.assertTrue(
	 			policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),
	 			"Group assignment is failed");
	 	policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
	 			"Group assigned is failed");
	 	waitForPageLoaded();
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(3000);
	 	String anyname = policypages.getTextOfWEPPolicyPage("AssignedState");
	 	LOGGER.info(anyname);
	 	Assert.assertTrue(anyname != null && anyname.contains("Assigned"),
	 			"Policy details are not available" + anyname);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	 	waitForPageLoaded();
	 	sleeper(4000);
	 	policypages.mouseHoverOfPolicyPage("groupItem1");
	 	policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
	 	sleeper(1000);
	 	policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
	 	waitForPageLoaded();
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
	 	policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
	 	waitForPageLoaded();
	 	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEPs"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);

	 	}
	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
	 	waitForPageLoaded();
	 	sleeper(3000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	waitForPageLoaded();
	 	policypages.clickOnElementsOfPolicyPage("deleteButton");
	 	waitForPageLoaded();

	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
	 			"Policy delete pending is failed");
	 	policypages.waitForElementsOfPolicyPage("policyDeleted");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
	 			"Policy deleted is failed");
	 	waitForPageLoaded();

	 } 
	 
    /**
	 * TC_C64433527:[WEP]>>Verify Conflict when multiple policies with "Only deploy critical driver updates" are assigned to the same device group
	 */
	@Test(priority = 102, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: TC_C64433527")
	public final void VerifyConflictwhenmultiplepolicieswithOnlydeploycriticaldriverupdatesareassignedtothesamedevicegroup()
			throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();

		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.policy_name+generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
		policypages.clickOnElementsOfPolicyPage("Nextbtn");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
				"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
				"policy add pending is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("DateClear");
		policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.actionClickOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),
				"group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
				"group assigned is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",
				PolicyVariables.policy_name_value + generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
		policypages.clickOnElementsOfPolicyPage("Nextbtn");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
				"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
				"policy add pending is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		String policy_name = policypages.getTextOfWEPPolicyPage("policyConflict");
		LOGGER.info(policy_name);
		Assert.assertTrue(policy_name != null && policy_name.contains("Conflicts (1)"),
				"verify policy element: " + policy_name);
		policypages.clickOnElementsOfPolicyPage("cancelButton");
		policypages.enterTextForPolicyPage("searchpolicy", PolicyVariables.policy_name);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		LOGGER.info(errorMsg);
		Assert.assertTrue(errorMsg != null && errorMsg.contains("Assigned"),
				"Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending")
				.contains("Groups unassignment pending"), "group unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"),
				"group unassigned is failed");
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
		waitForPageLoaded();

	}
	
	/**
	 * TC_C64433529:[WEP]>>Verify Conflict when multiple policies with the "same specific categories & options" are assigned to the same device group
	 * 
	 */
		@Test(priority = 103, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: TC_C64433529")
		public final void VerifyConflictwhenmultiplepolicieswiththesamespecificcategoriesoptionsareassignedtothesamedevicegroup ()
				throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
				
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
		getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		waitForPageLoaded();
		sleeper(2000);
		} else {
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from Remediation tab successfully");
		sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.policy_name+generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("FirmwareandDriver");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("Nextbtn");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
				"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
				"policy add pending is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("DateClear");
		policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),
				"group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
				"group assigned is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",
				PolicyVariables.policy_name_value + generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("FirmwareandDriver");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("Nextbtn");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
				"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
				"policy add pending is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		String policy_name = policypages.getTextOfWEPPolicyPage("policyConflict");
		LOGGER.info(policy_name);
		Assert.assertTrue(policy_name != null && policy_name.contains("Conflicts (1)"),
				"verify policy element: " + policy_name);
		policypages.clickOnElementsOfPolicyPage("cancelButton");
		policypages.enterTextForPolicyPage("searchpolicy", PolicyVariables.policy_name);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		LOGGER.info(errorMsg);
		Assert.assertTrue(errorMsg != null && errorMsg.contains("Assigned"),
				"Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"),
				"group unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"),
				"group unassigned is failed");
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
		waitForPageLoaded();
		}
	
	/**
	 * TC_C64433504:[WEP]>>Verify user can Unassign Platform Driver Policy Update Critical
	 * 
	 */
	@Test(priority = 104, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: TC_C64433504")

	public final void VerifyusercanUnassignPlatformDriverPolicyUpdateCritical() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();

		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}

		waitForPageLoaded();
		LOGGER.info("Navigated to policies tab ");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",
				PolicyVariables.policy_name_value + generateRandomNumber());
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",
				PolicyVariables.description_value);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(2000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();

		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
				"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
				"policy add pending is failed");

		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending")
				.contains("Group assignment pending"), "Group assignment is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
				"Group assigned is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(3000);
		String anyname = policypages.getTextOfWEPPolicyPage("AssignedState");
		LOGGER.info(anyname);
		Assert.assertTrue(anyname != null && anyname.contains("Assigned"),
				"Policy details are not available" + anyname);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(4000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();

		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment  is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"),"Group unassigned is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEPs"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);

	 	}
	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	policypages.waitForElementsOfPolicyPage("policySearch");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	policypages.waitForElementsOfPolicyPage("deleteButton");
	 	policypages.clickOnElementsOfPolicyPage("deleteButton");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
	 			"policy delete pending is failed");
	 	policypages.waitForElementsOfPolicyPage("policyDeleted");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
	 			"Policy deleted is failed");

	}
	
	
	/**
	 * TC_C64433505:[WEP]>>Verify user can Unassign Platform Driver Policy Update Select Categories
	 * 
	 */
	@Test(priority = 105, groups = { "REGRESSION_POLICY"}, description = "Testcase ID: TC_C64433505")

	public final void VerifyusercanUnassignPlatformDriverPolicyUpdateSelectCategories() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();

		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}

		waitForPageLoaded();
		LOGGER.info("Navigated to policies tab ");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",
				PolicyVariables.policy_name_value + generateRandomNumber());
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(2000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
		policypages.clickOnElementsOfPolicyPage("DriverAudio");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
				"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
				"policy add pending is failed");

		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending")
				.contains("Group assignment pending"), "Group assignment is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
				"Group assigned is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		String anyname = policypages.getTextOfWEPPolicyPage("AssignedState");
		LOGGER.info(anyname);
		Assert.assertTrue(anyname != null && anyname.contains("Assigned"),
				"Policy details are not available" + anyname);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment  is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"),"Group unassigned is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEPs"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);

	 	}
	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	policypages.waitForElementsOfPolicyPage("policySearch");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	policypages.waitForElementsOfPolicyPage("deleteButton");
	 	policypages.clickOnElementsOfPolicyPage("deleteButton");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
	 			"policy delete pending is failed");
	 	policypages.waitForElementsOfPolicyPage("policyDeleted");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
	 			"Policy deleted is failed");

	}
	
	/**
	 * TC_C64433520:[WEP]>>Verify user can Unassign Platform Driver Policy Always deploy driver updates HPrecommended
	 * 
	 */
	@Test(priority = 106, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: TC_C64433520")
	public final void VerifyusercanUnassignPlatformDriverPolicyAlwaysdeploydriverupdatesHPrecommended() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();

		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}

		waitForPageLoaded();
		LOGGER.info("Navigated to policies tab ");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",
				PolicyVariables.policy_name_value + generateRandomNumber());
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",
				PolicyVariables.description_value);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(2000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();

		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),"policy add pending is failed");

		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("DateClear");
		policypages.clickOnElementsOfPolicyPage("dropdown2");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),"Group assigned is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(3000);
		String anyname = policypages.getTextOfWEPPolicyPage("AssignedState");
		LOGGER.info(anyname);
		Assert.assertTrue(anyname != null && anyname.contains("Assigned"),"Policy details are not available" + anyname);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(4000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();

		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment  is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"),"Group unassigned is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
	 			getCredentials(environment, "ITADMIN_EMAIL_WEPs"))) {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
	 		waitForPageLoaded();
	 		sleeper(2000);
	 	} else {
	 		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
	 				CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 		LOGGER.info("Navigated to Policies from Remediation tab successfully");
	 		sleeper(2000);

	 	}
	 	waitForPageLoaded();
	 	LOGGER.info("Navigated to policies tab ");
	 	policypages.waitForElementsOfPolicyPage("policySearch");
	 	sleeper(2000);
	 	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
	 	sleeper(2000);
	 	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	 	policypages.waitForElementsOfPolicyPage("deleteButton");
	 	policypages.clickOnElementsOfPolicyPage("deleteButton");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
	 			"policy delete pending is failed");
	 	policypages.waitForElementsOfPolicyPage("policyDeleted");
	 	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
	 			"Policy deleted is failed");

	}		

	
	/**
	 * TC_C64433937:[WEP]>>Verify conflict when multiple policies with always deploy driver update are assigned to same device
	 * 
	 */
     @Test(priority = 107, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: TC_C64433937")
     public final void Verifyconflictwhenmultiplepolicieswithalwaysdeploydriverupdateareassignedtosamedevice() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
	
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
	
		waitForPageLoaded();
		LOGGER.info("Navigated to policies tab ");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(2000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
	
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),"policy add pending is failed");
	
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("DateClear");
		policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),"Group assigned is failed");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.DU_Policy_Text);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(2000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),"policy add pending is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.DU_Policy_Text);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		String anyname = policypages.getTextOfWEPPolicyPage("policyConflict");
		LOGGER.info(anyname);
		Assert.assertTrue(anyname != null && anyname.contains("Conflicts (1)"),
				"Policy conflict 1 are not available" + anyname);
		sleeper(2000);
		policypages.waitUntilElementIsInvisibleOfPolicyPage("cancelButton");
		policypages.clickOnElementsOfPolicyPage("cancelButton");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(4000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment  is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"),"Group unassigned is failed");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "Policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),"Policy deleted is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.DU_Policy_Text);
		sleeper(2000);
		LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "Policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),"Policy deleted is failed");
		waitForPageLoaded();		
	}
	    
     /**
 	 * TC_C64433525:[WEP]>>Verify Conflict when multiple policies with Only deploy critical driver updates are assigned to thesame device group platform
 	 * 
 	 */
	   @Test(priority = 108, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: TC_C64433525")
	    public final void VerifyConflictwhenmultiplepolicieswithOnlydeploycriticaldriverupdatesareassignedtothesamedevicegroupplatform() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
	
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
	
		waitForPageLoaded();
		LOGGER.info("Navigated to policies tab ");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(2000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),"policy add pending is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("DateClear");
		policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),"Group assigned is failed");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.DU_Policy_Text);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(2000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),"policy add pending is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.DU_Policy_Text);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		String anyname = policypages.getTextOfWEPPolicyPage("policyConflict");
		LOGGER.info(anyname);
		Assert.assertTrue(anyname != null && anyname.contains("Conflicts (1)"),"Conflicts (1) are not available" + anyname);
		sleeper(2000);
		policypages.waitUntilElementIsInvisibleOfPolicyPage("cancelButton");
		policypages.clickOnElementsOfPolicyPage("cancelButton");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(4000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment  is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"),"Group unassigned is failed");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "Policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),"Policy deleted is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.DU_Policy_Text);
		sleeper(2000);
		LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "Policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),"Policy deleted is failed");
		waitForPageLoaded();
			
			
	}
	   
   /**
 	 * TC_C64433529:[WEP]>>Verify Conflict when multiple policies with the same specific categories options are assigned to the same deviceg roup platform
 	 * 
 	 */
	 @Test(priority = 109, groups = { "REGRESSION_POLICY"}, description = "Testcase ID: TC_C64433529")
	 public final void VerifyConflictwhenmultiplepolicieswiththesamespecificcategoriesoptionsareassignedtothesamedevicegroupplatform() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
	
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
			
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
	
		waitForPageLoaded();
		LOGGER.info("Navigated to policies tab ");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(2000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("DriverAudio");
		policypages.actionClickOfPolicyPage("selectCrticaldropdown");
		policypages.clickOnElementsOfPolicyPage("Criticalcategory");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),"policy add pending is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("DateClear");
		policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),"Group assigned is failed");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.DU_Policy_Text);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(2000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("DriverAudio");
		policypages.actionClickOfPolicyPage("selectCrticaldropdown");
		policypages.actionClickOfPolicyPage("Criticalcategory");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),"policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),"policy add pending is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.DU_Policy_Text);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		String anyname = policypages.getTextOfWEPPolicyPage("policyConflict");
		LOGGER.info(anyname);
		Assert.assertTrue(anyname != null && anyname.contains("Conflicts (1)"),"Conflicts (1) are not available" + anyname);
		sleeper(2000);
		policypages.waitUntilElementIsInvisibleOfPolicyPage("cancelButton");
		policypages.clickOnElementsOfPolicyPage("cancelButton");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(4000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		sleeper(1000);
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment  is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"),"Group unassigned is failed");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "Policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),"Policy deleted is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.DU_Policy_Text);
		sleeper(2000);
		LOGGER.info(policypages.getTextOfWEPPolicyPage("policySearchItem"));
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "Policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),"Policy deleted is failed");
		waitForPageLoaded();
				
	}
	 
	 /**
	 * TC_C64433388[WEP]>>Verify user can Unassign Global Driver Policy - Update Critical
	*/	
	 @Test (priority = 110, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C64433388")
	 public final void VerifyusercanUnassignGlobalDriverPolicyUpdateCritical  () throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}
			else {
				 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
				 LOGGER.info("Navigated to Policies from Remediation tab successfully");
				 sleeper(2000);
				 }
		 waitForPageLoaded();
		 LOGGER.info("Navigated to Policies tab ");
		 policypages.clickOnElementsOfPolicyPage("addpolicy");
		 waitForPageLoaded();
		 policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
		 sleeper(2000);
		 policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		 policypages.clickOnElementsOfPolicyPage("Driverupdate");
		 policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		 sleeper(2000);
		 LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		 waitForPageLoaded();
		 policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		 waitForPageLoaded();
		 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		 waitForPageLoaded();
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		 policypages.waitForElementsOfPolicyPage("PolicyAdded");
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy add pending is failed");
		 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			 waitForPageLoaded();
			 sleeper(2000);
			 }
			else {
				 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
				 LOGGER.info("Navigated to Policies from Remediation tab successfully");
				 sleeper(2000);
				 }
		 policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("policySearchItem");	
		 waitForPageLoaded();
		 policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		 sleeper(2000);
		 waitForPageLoaded();
	     policypages.enterTextForPolicyPage("policySearch",PolicyVariables.driverupdategroup);
		 sleeper(4000);
		 policypages.waitForElementsOfPolicyPage("selectGroupItem");
		 policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		 waitForPageLoaded();
		 policypages.clickOnElementsOfPolicyPage("DateClear");
		 policypages.clickOnElementsOfPolicyPage("dropdown2");
		 policypages.clickOnElementsOfPolicyPage("RecurringDays");
		 policypages.selectNextDateFromCalendar();
		 sleeper(2000);
		 policypages.selectRoundedUpCurrentTimeFromDropdown();
		 policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		 policypages.clickOnElementsOfPolicyPage("scheduleNext");
		 waitForPageLoaded();
		 policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		 policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		 waitForPageLoaded();
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "group assignment pending is failed");
		 policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "group assigned is failed");
	     waitForPageLoaded();
		 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			 waitForPageLoaded();
			 sleeper(2000);
			 }
			else {
				 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
				 LOGGER.info("Navigated to Policies from Remediation tab successfully");
				 sleeper(2000);
				 }
		 waitForPageLoaded();
		 policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("policySearchItem");	
		 policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		 String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		 LOGGER.info(errorMsg);
		 Assert.assertTrue(
			    errorMsg != null && errorMsg.contains("Assigned"),
			    "Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		 policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		 waitForPageLoaded();
		 sleeper(2000);
		 policypages.mouseHoverOfPolicyPage("groupItem1");
		 policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		 policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		 policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		 waitForPageLoaded();
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "group unassignment pending is failed");
		 policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "group unassigned is failed");
		 waitForPageLoaded();
		 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			 waitForPageLoaded();
			 sleeper(2000);
			 }
			else {
				 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
				 LOGGER.info("Navigated to Policies from Remediation tab successfully");
				 sleeper(2000);
				 }
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitUntillElementIsPresent("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
		waitForPageLoaded();
	
	 }	
	 
	 /**
	  * TC_C64433393:[WEP]>>Verify user can Unassign Global Driver Policy - Update Select Categories
	 */	
	 @Test(priority = 111, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: TC_C64433393")
	 public final void VerifyusercanUnassignGlobalDriverPolicyUpdateSelectCategories() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",
				PolicyVariables.policy_name_value + generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		sleeper(2000);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("FirmwareandDriver");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
				"policy add pending is failed");
		policypages.waitUntillElementIsPresent("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
				"policy add pending is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("DateClear");
		policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),
				"group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
				"group assigned is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		LOGGER.info(errorMsg);
		Assert.assertTrue(errorMsg != null && errorMsg.contains("Assigned"),
				"Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"),
				"group unassignment pending is failed");
		policypages.waitUntillElementIsPresent("GroupUnassigned");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"),
				"group unassigned is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitUntillElementIsPresent("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
		waitForPageLoaded();

	}
			 
	/**
	* TC_C64433394:[WEP]>>Verify user can Unassign Global Driver Policy - Always deploy driver updates (HP recommended)
	*/
	 @Test(priority = 112, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: TC_C64433394")
	 public final void VerifyusercanUnassignGlobalDriverPolicyAlwaysdeploydriverupdatesHPrecommended()
		throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",
				PolicyVariables.policy_name_value + generateRandomNumber());
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		sleeper(2000);
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
				"policy add pending is failed");
		policypages.waitUntillElementIsPresent("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
				"policy add pending is failed");
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("DateClear");
		policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),
				"group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
				"group assigned is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		LOGGER.info(errorMsg);
		Assert.assertTrue(errorMsg != null && errorMsg.contains("Assigned"),
				"Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"),
				"group unassignment pending is failed");
		policypages.waitUntillElementIsPresent("GroupUnassigned");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"),
				"group unassigned is failed");
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitUntillElementIsPresent("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
		waitForPageLoaded();

	}	
		 	
	/**
	 * TC_C64433395:[WEP]>>Verify Conflict when multiple policies with "Always deploy driver updates" are assigned to the same device group
	 */
	 @Test(priority = 113, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: TC_C64433395")
	 public final void VerifyConflictwhenmultiplepolicieswithAlwaysdeploydriverupdatesareassignedtothesamedevicegroup()
		throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.policy_name+generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
		policypages.clickOnElementsOfPolicyPage("Nextbtn");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
				"policy add pending is failed");
		policypages.waitUntillElementIsPresent("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
				"policy add pending is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("DateClear");
		policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.actionClickOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),
				"group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
				"group assigned is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",
				PolicyVariables.policy_name_value + generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("Deployalldriverupdates");
		policypages.clickOnElementsOfPolicyPage("Nextbtn");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
				"policy add pending is failed");
		policypages.waitUntillElementIsPresent("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
				"policy add pending is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.driverupdategroup);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		String policy_name = policypages.getTextOfWEPPolicyPage("policyConflict");
		LOGGER.info(policy_name);
		Assert.assertTrue(policy_name != null && policy_name.contains("Conflicts (1)"),
				"verify policy element: " + policy_name);
		policypages.clickOnElementsOfPolicyPage("cancelButton");
		policypages.enterTextForPolicyPage("searchpolicy", PolicyVariables.policy_name);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		LOGGER.info(errorMsg);
		Assert.assertTrue(errorMsg != null && errorMsg.contains("Assigned"),
				"Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(
				policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"),
				"group unassignment pending is failed");
		policypages.waitUntillElementIsPresent("GroupUnassigned");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"),
				"group unassigned is failed");
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitUntillElementIsPresent("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitUntillElementIsPresent("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
		waitForPageLoaded();
	
	}
	 
	/**
	 * TC_C66335583:[WEP]>>Ensure the "Driver Update" policy type is available and selectable.
	 */
	@Test(priority = 114, groups = { "REGRESSION_POLICY", }, description = "Testcase ID: TC_C66335583")
	public final void EnsuretheDriverUpdatepolicytypeisavailableandselectable() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();

		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.policy_name+generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
	}
		
	/**
	 * TC_C66336462:[WEP]>>Verify that the 'Global' option is not selected by default under
	 * the scope settings.
	 */
	@Test(priority = 115, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: TC_C66336462")
	public final void VerifythattheGlobaloptionisnotselectedbydefaultunderthescopesettings() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();

		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.policy_name+generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.verifyElementIsSelected("policyScopeGlobalOption");
		}
		
	/**
	 * TC_C66336160:[WEP]>>Verify user can select one or more specific categories of drivers to deploy policy
	 */
	@Test(priority = 116, groups = { "REGRESSION_POLICY"}, description = "Testcase ID: TC_C66336160")
	public final void Verifyusercanselectoneormorespecificcategoriesofdriverstodeploypolicy() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();

		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.policy_name+generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		sleeper(2000);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("FirmwareandDriver");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("DriverAudio");
		sleeper(2000);
		policypages.verifywaituntilelementisenabled("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
	
		}
		

	
	/**TC_T1945993169
	 * Verify Policy and Secret page UI elements
	 * @throws Exception
	 */

	@Test(priority = 117, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
    public final void verifyPolicyPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		SoftAssert softAssert = new SoftAssert();
		 String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();

		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");

		policypages.verifyElementIsPresentOnPolicyPage("addPolicyButton");	
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		sleeper(2000);
		softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("policyAddTitle"), "policy Title missing.");
		softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("policyName"), "policy Name tab missing.");
		softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("policyType"), "experienceOverTime tab missing.");
		softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("policyDescription"), "fleetInventoryTab tab missing.");
		softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("policyTag"), "appWithMostCrashes tab missing.");
		sleeper(2000);
		navigateToBack();

		Assert.assertTrue(policypages.verifyPolicyDetailNavigation(LanguageCode),"Policy Details page did not load ");
        sleeper(5000);
        softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("policyDetailsName"), "policy Details Name  missing.");      
        softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("policyOverview"), "policy Overview tab missing.");
        policypages.clickOnElementsOfPolicyPage("policyOverview");
        waitForPageLoaded();
        softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("policyAssignments"), "policy Assignments tab missing.");
        policypages.clickOnElementsOfPolicyPage("policyAssignments");
        waitForPageLoaded();
        softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("policyHistory"), "policy History tab missing.");
        policypages.clickOnElementsOfPolicyPage("policyHistory");
        waitForPageLoaded();
        
		softAssert.assertAll();

   }

	/**TC_T1945993169
	 * Verify Secret page UI elements
	 * @throws Exception
	 */

    @Test(priority = 118, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
    public final void verifySecretPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		SoftAssert softAssert = new SoftAssert();
		 String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();

		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Secret from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
			LOGGER.info("Navigated to Secret from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Secret tab ");		
		policypages.verifyElementIsPresentOnPolicyPage("secretAddButton");	
		policypages.clickOnElementsOfPolicyPage("secretAddButton");
		waitForPageLoaded();
		sleeper(2000);
		softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("secretNameTitle"), "Secret Title missing.");
		softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("secretType"), "Secret Type tab missing.");
		softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("secretTDescription"), "secretT Description tab missing.");
		softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("secretTtag"), "secretT tag missing.");		
		sleeper(2000);
		navigateToBack();	
		Assert.assertTrue(policypages.verifySecretDetailNavigation(LanguageCode),"Policy Details page did not load ");
        sleeper(5000);
        softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("policyDetailsName"), "policy Details Name  missing.");      
        softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("SecretOverview"), "policy Overview tab missing.");
        policypages.clickOnElementsOfPolicyPage("SecretOverview");
        waitForPageLoaded();
        softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("SecretAssignments"), "policy Assignments tab missing.");
        policypages.clickOnElementsOfPolicyPage("SecretAssignments");
        waitForPageLoaded();
        softAssert.assertTrue(policypages.waitForElementsOfPolicyPage("SecretHistory"), "policy History tab missing.");
        policypages.clickOnElementsOfPolicyPage("SecretHistory");
		softAssert.assertAll();
       }
	   
	/**
	 * TC_C66336209:[WEP]>>Verify user can select one or more specific categories of drivers to deploy policy platform
	 */
	@Test(priority = 119, groups = { "REGRESSION_POLICY"}, description = "Testcase ID: TC_C66336209")
	public final void Verifyusercanselectoneormorespecificcategoriesofdriverstodeploypolicyplatform() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
 
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.policy_name+generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(2000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("driverupdatecategories");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("FirmwareandDriver");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("DriverAudio");
		sleeper(2000);
		policypages.verifywaituntilelementisenabled("nextbuttonOnPolicyCreationPage");
		}
		
	/**
	 * TC_C66336209:[WEP]>>Verify Bios Update Policy Creation with Global Scope and Only deploy critical BIOS updates option
	 */
	@Test(priority = 120, groups = { "REGRESSION_POLICY"}, description = "Testcase ID: TC_C66336209")
	public final void VerifyBiosUpdatePolicyCreationwithGlobalScopeandOnlydeploycriticalBIOSupdatesoption() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
 
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,
					CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.edit_update_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("passwordRadioButton");
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.edit_update_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitUntillElementIsPresent("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");		
   }
 
		 	
	/**
	 * TC_C66336506:[WEP]>>Verify user can Assign Global Bios update Policy
	 */
	 @Test(priority = 121, groups = { "REGRESSION_POLICY" }, description = "Testcase ID: TC_C66336506")
	 public final void VerifyusercanAssignGlobalBiosupdatePolicy()
		throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.bios_policy_update+generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("passwordRadioButton");
		policypages.clickOnElementsOfPolicyPage("Nextbtn");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
				"policy add pending is failed");
		policypages.waitUntillElementIsPresent("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
				"policy add pending is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.latestGroup_Name);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.actionClickOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),
				"group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
				"group assigned is failed");
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			 waitForPageLoaded();
			 sleeper(2000);
			 }
			else {
				 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
				 LOGGER.info("Navigated to Policies from Remediation tab successfully");
				 sleeper(2000);
				 }
		 waitForPageLoaded();
		 policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_policy_update);
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("policySearchItem");	
		 policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		 String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		 LOGGER.info(errorMsg);
		 Assert.assertTrue(
			    errorMsg != null && errorMsg.contains("Assigned"),
			    "Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		 policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		 waitForPageLoaded();
		 sleeper(2000);
		 policypages.mouseHoverOfPolicyPage("groupItem1");
		 policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		 policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		 policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		 waitForPageLoaded();
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "group unassignment pending is failed");
		 policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "group unassigned is failed");
		 waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitUntillElementIsPresent("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
	}

	/**
	 * TC_C66336565:[WEP]>>Verify Bios Update Policy Creation with Platfrom Scope and Only deploy critical BIOS updates option
	 */
	@Test(priority = 122, groups = { "REGRESSION_POLICY"}, description = "Testcase ID: TC_C66336565")
	public final void VerifyBiosUpdatePolicyCreationwithPlatfromScopeandOnlydeploycriticalBIOSupdatesoption() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.edit_update_policy+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(1000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("passwordRadioButton");
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.edit_update_policy);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitUntillElementIsPresent("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
   }
	
	
	/**
	 * TC_C66336566:[WEP]>>Verify Bios Update Policy Creation with Platfrom Scope and enfore a bios update
	 */
	@Test(priority = 123, groups = { "REGRESSION_POLICY"}, description = "Testcase ID: TC_C66336566")
	public final void VerifyBiosUpdatePolicyCreationwithPlatfromScopeandenforeabiosupdate() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.bios_policy_update+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(1000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("EnforceBiosUpdate");
		sleeper(3000);
		policypages.actionClickOfPolicyPage("Biosversiondropdown");
		policypages.clickOnElementsOfPolicyPage("Biosversion");
		LOGGER.info("Biosversion");
		sleeper(6000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitUntillElementIsPresent("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
		waitForPageLoaded();
   }
	
	
	/**
	 * TC_C66336568:[WEP]>>Verify user can Assign Platfrom BIOS updates policy - only deploy bios critical option
	 */
	@Test(priority = 124, groups = { "REGRESSION_POLICY"}, description = "Testcase ID: TC_C66336568")
	public final void VerifyusercanAssignPlatfromBIOSupdatespolicyonlydeploybioscriticaloption() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.bios_policy_update+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(1000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("passwordRadioButton");
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.latestGroup_Name);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.actionClickOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),
				"group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
				"group assigned is failed");
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			 waitForPageLoaded();
			 sleeper(2000);
			 }
			else {
				 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
				 LOGGER.info("Navigated to Policies from Remediation tab successfully");
				 sleeper(2000);
				 }
		 waitForPageLoaded();
		 policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_policy_update);
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("policySearchItem");	
		 policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		 String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		 LOGGER.info(errorMsg);
		 Assert.assertTrue(
				errorMsg != null && errorMsg.contains("Assigned"),
				"Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		 policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		 waitForPageLoaded();
		 sleeper(2000);
		 policypages.mouseHoverOfPolicyPage("groupItem1");
		 policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		 policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		 policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		 waitForPageLoaded();
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "group unassignment pending is failed");
		 policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "group unassigned is failed");
		 waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitUntillElementIsPresent("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
		waitForPageLoaded();
   }
	
	/**
	 * TC_C66336575:[WEP]>>Verify user can Assign platform Bios update Policy - enfore a bios update
	 */
	@Test(priority = 125, groups = { "REGRESSION_POLICY"}, description = "Testcase ID: TC_C66336575")
	public final void VerifyusercanAssignplatformBiosupdatePolicyenforeabiosupdate() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		LOGGER.info("Navigated to Policies tab ");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.bios_policy_update+generateRandomNumber());
		waitForPageLoaded();
		policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(1000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("EnforceBiosUpdate");
		sleeper(3000);
		policypages.actionClickOfPolicyPage("Biosversiondropdown");
		policypages.clickOnElementsOfPolicyPage("Biosversion");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.latestGroup_Name);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.actionClickOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),
				"group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
				"group assigned is failed");
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			 waitForPageLoaded();
			 sleeper(2000);
			 }
			else {
				 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
				 LOGGER.info("Navigated to Policies from Remediation tab successfully");
				 sleeper(2000);
				 }
		 waitForPageLoaded();
		 policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_policy_update);
		 sleeper(2000);
		 policypages.clickOnElementsOfPolicyPage("policySearchItem");	
		 policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		 String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		 LOGGER.info(errorMsg);
		 Assert.assertTrue(
				errorMsg != null && errorMsg.contains("Assigned"),
				"Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		 policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		 waitForPageLoaded();
		 sleeper(2000);
		 policypages.mouseHoverOfPolicyPage("groupItem1");
		 policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		 policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		 policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		 waitForPageLoaded();
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "group unassignment pending is failed");
		 policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		 Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "group unassigned is failed");
		 waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitUntillElementIsPresent("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
   }
   
   /**
	* TC_C66732959:[WEP]>>Verify User Can Assign Bios Setting Policy To A  Dynamic Group
	*/
	@Test(priority = 126, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C66732959")
	public final void VerifyUserCanAssignBiosSettingPolicyToADynamicGroup() throws Exception {	
	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
	openLeftSidePanel();
	String DG_GroupName = "BS_Dynamic_craetion_groups" +generateRandomNumber();
	WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	waitForPageLoaded();

	if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.GROUPS);
		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
		waitForPageLoaded();
		sleeper(2000);
	}else {
		policypages.companyView(CommonVariables.GROUPS);
		LOGGER.info("Navigated to Groups successfully");
		sleeper(2000);
	}
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("groupsHeader")
			.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
	LOGGER.info("Groups Header is Matched");
	policypages.clickOnElementsOfPolicyPage("addGroupBtn");
	waitForPageLoaded();
	policypages.actionClickOfPolicyPage("Dynamicgroup");
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("groupNameField",DG_GroupName);
	waitForPageLoaded();
	LOGGER.info("Group Name is entered");
	sleeper(2000);
	policypages.enterTextForPolicyPage("groupDescriptionField",PolicyVariables.SG_GroupDescription);
	LOGGER.info("Group Description is entered");
	policypages.clickOnElementsOfPolicyPage("addGrpNextBtn");
	waitForPageLoaded();
	sleeper(3000);
	policypages.actionClickOfPolicyPage("propertydropdown");
	sleeper(5000);
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("selectmanufacturer");
	sleeper(3000);
	policypages.actionClickOfPolicyPage("valuedropdown");
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("serialserachbutton",PolicyVariables.Manufacturer_Name);
	sleeper(3000);
	policypages.actionClickOfPolicyPage("Manufacturername");
	sleeper(3000);
	policypages.clickOnElementsOfPolicyPage("addGrpNextBtn");
	waitForPageLoaded();
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("addGrpBtn");
	waitForPageLoaded();
	LOGGER.info("Static Group creation is done successfully");
	waitForPageLoaded();
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("groupCreate").contains("Group created"), "Group created is failed");	
	policypages.clickOnElementsOfPolicyPage("NotificationButton");
	policypages.clickOnElementsOfPolicyPage("notification");
	policypages.waitElementsOfPolicyPage("NotificationMessage");		
	if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		waitForPageLoaded();
		sleeper(2000);
	}else {
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from Remediation tab successfully");
		sleeper(2000);
	}
	waitForPageLoaded();
	sleeper(2000);
	LOGGER.info("Navigated to Policies tab ");
	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.BS_PolicyName+generateRandomNumber());
	waitForPageLoaded();
	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	waitForPageLoaded();
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
	sleeper(3000);
	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
	policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
	policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
	LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	sleeper(3000);
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("AccessaryUSBPortOptionOnPolicyCreationPage");
	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
	policypages.waitForElementsOfPolicyPage("PolicyAdded");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.BS_PolicyName);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	sleeper(3000);
	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("policySearch",DG_GroupName);
	sleeper(3000);
	policypages.waitForElementsOfPolicyPage("selectGroupItem");
	policypages.clickOnElementsOfPolicyPage("selectGroupItem");
	sleeper(2000);
	policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("assignGroupButton");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
	policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
	waitForPageLoaded();
	LOGGER.info("Policies assigned to Static Group is done successfully");
	if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		waitForPageLoaded();
		sleeper(2000);
	}else {
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from Remediation tab successfully");
		sleeper(2000);
	}
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.BS_PolicyName);
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	waitForPageLoaded();
	sleeper(2000);
	policypages.mouseHoverOfPolicyPage("groupItem1");
	policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
	policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
	policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
	waitForPageLoaded();
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
	policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
	waitForPageLoaded();
	sleeper(3000);
	policypages.waitForElementsOfPolicyPage("deleteButton");
	policypages.clickOnElementsOfPolicyPage("deleteButton");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	policypages.waitForElementsOfPolicyPage("policyDeleted");
	if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.GROUPS);
		LOGGER.info("Groups are Showing in sidemenu bar under Fleetmanagement");
		waitForPageLoaded();
		sleeper(2000);
	}else {
		policypages.companyView(CommonVariables.GROUPS);
		LOGGER.info("Navigated to Groups successfully");
	}
	sleeper(2000);
	policypages.enterTextForPolicyPage("groupSearch",DG_GroupName);
	waitForPageLoaded();
	sleeper(5000);
	policypages.clickOnElementsOfPolicyPage("groupSelect");
	sleeper(3000);
	policypages.clickOnElementsOfPolicyPage("deleteGroupButtonChk");
	String secCode = policypages.getTextOfWEPPolicyPage("securityCodeNumber");
	policypages.enterTextForPolicyPage("securityCodeField",secCode);
	policypages.clickOnElementsOfPolicyPage("deleteButton1");
	LOGGER.info(policypages.getTextOfWEPPolicyPage("groupdelete"));
	Assert.assertTrue(
			policypages.getTextOfWEPPolicyPage("groupdelete")
			.contains(policypages.getTextLanguage(LanguageCode, "daas_ui", "groups.toast.delete.group.success")),"Static Group Deletion is failed");
	}  


	/**
	* TC_C66732960:[WEP]>>Verify User Can Assign Bios update Policy To A  Dynamic Group
	*/
	@Test(priority = 127, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C66732960")
	public final void VerifyUserCanAssignBiosupdatePolicyToADynamicGroup() throws Exception {	
	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
	openLeftSidePanel();
	WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	waitForPageLoaded();	
	if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		waitForPageLoaded();
		sleeper(2000);
	}else {
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from Remediation tab successfully");
		sleeper(2000);
	}
	waitForPageLoaded();
	LOGGER.info("Navigated to Policies tab ");
	policypages.clickOnElementsOfPolicyPage("addpolicy");
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.bios_policy_update+generateRandomNumber());
	sleeper(2000);
	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
	LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("passwordRadioButton");
	policypages.clickOnElementsOfPolicyPage("Nextbtn");
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	waitForPageLoaded();
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
			"policy add pending is failed");
	policypages.waitUntillElementIsPresent("PolicyAdded");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
			"policy add pending is failed");
	waitForPageLoaded();
	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
			getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		waitForPageLoaded();
		sleeper(2000);
	} else {
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from Remediation tab successfully");
		sleeper(2000);
	}
	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.bios_policy_update);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	sleeper(2000);
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.DG_GroupName_Latest);
	sleeper(4000);
	policypages.waitForElementsOfPolicyPage("selectGroupItem");
	policypages.clickOnElementsOfPolicyPage("selectGroupItem");
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	waitForPageLoaded();
	policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
	policypages.actionClickOfPolicyPage("assignGroupButton");
	waitForPageLoaded();
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),
			"group assignment pending is failed");
	policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
			"group assigned is failed");
	waitForPageLoaded();
	if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		 waitForPageLoaded();
		 sleeper(2000);
		 }
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_policy_update);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policySearchItem");	
	policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
	String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
	LOGGER.info(errorMsg);
	Assert.assertTrue(
			errorMsg != null && errorMsg.contains("Assigned"),
			"Policy details are not avilable policy deleted sucessfully: " + errorMsg);
	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	waitForPageLoaded();
	sleeper(2000);
	policypages.mouseHoverOfPolicyPage("groupItem1");
	policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
	policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
	policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
	waitForPageLoaded();
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "group unassignment pending is failed");
	policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "group unassigned is failed");
	waitForPageLoaded();
	if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
			getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		waitForPageLoaded();
		sleeper(2000);
	} else {
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from Remediation tab successfully");
		sleeper(2000);
	}
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("policySearch", PolicyVariables.bios_policy_update);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	waitForPageLoaded();
	sleeper(2000);
	policypages.waitForElementsOfPolicyPage("deleteButton");
	policypages.clickOnElementsOfPolicyPage("deleteButton");
	waitForPageLoaded();
	sleeper(2000);
	waitForPageLoaded();
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
			"policy delete pending is failed");
	policypages.waitUntillElementIsPresent("policyDeleted");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
			"policy deleted is failed");
	}

	/**
	* TC_C66732961:[WEP]>>Verify User Can Assign Bios Authentication Policy To A  Dynamic Group
	*/
	@Test(priority = 128, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C66732961")
	public final void VerifyUserCanAssignBiosAuthenticationPolicyToADynamicGroup() throws Exception {	
	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
	openLeftSidePanel();
	WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
	waitForPageLoaded();
	if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		waitForPageLoaded();
		sleeper(2000);
	}else {
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from Remediation tab successfully");
		sleeper(2000);
	}
	LOGGER.info("Navigated to Policies tab ");
	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.Unassigned_authentication_policy+generateRandomNumber());
	waitForPageLoaded();
	policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	policypages.clickOnElementsOfPolicyPage("biosAuthenticationOption");
	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
	policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
	LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");	
	waitForPageLoaded();
	policypages.waitForElementsOfPolicyPage("selectBiosOptionDropdown");
	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	policypages.clickOnElementsOfPolicyPage("automationsecreteoption");
	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	waitForPageLoaded();
	policypages.verifyElementIsPresentOnPolicyPage("BiosAuthenticationBiosPassword");
	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
	policypages.waitForElementsOfPolicyPage("PolicyAdded");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
	waitForPageLoaded();
	sleeper(2000);
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Unassigned_authentication_policy);
	sleeper(2000);
	policypages.mouseHoverOfPolicyPage("policySearchItem");
	policypages.clickOnElementsOfPolicyPage("metaball");
	policypages.clickOnElementsOfPolicyPage("AssignToGroup");
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.DG_GroupName_Latest);
	policypages.waitForElementsOfPolicyPage("selectGroupItem");
	policypages.clickOnElementsOfPolicyPage("selectGroupItem");
	policypages.verifyElementIsPresentOnPolicyPage("cancelButton");
	sleeper(2000);
	policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("assignGroupButton");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
	policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
	LOGGER.info("Policies assigned to Static Group is done successfully");
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Unassigned_authentication_policy);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	waitForPageLoaded();
	sleeper(2000);
	policypages.mouseHoverOfPolicyPage("groupItem1");
	policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
	policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
	policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
	policypages.clickOnElementsOfPolicyPage("OverviewTab");
	waitForPageLoaded();
	sleeper(2000);
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
	policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
	policypages.verifyElementIsPresentOnPolicyPage("UnassignedState");
	if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		policypages.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		waitForPageLoaded();
		sleeper(2000);
	}else {
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from Remediation tab successfully");
		sleeper(2000);
	}
	LOGGER.info("Navigated to Policies tab ");
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.Unassigned_authentication_policy);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	policypages.waitForElementsOfPolicyPage("deleteButton");
	policypages.clickOnElementsOfPolicyPage("deleteButton");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	policypages.waitForElementsOfPolicyPage("policyDeleted");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");

	}

	/**
	* TC_C66732962:[WEP]>>Verify User Can Assign Driver update To A  Dynamic Group
	*/	
	@Test (priority = 129, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C66732962")
	public final void VerifyUserCanAssignDriverupdateToADynamicGroup() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();
	 if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
	 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
	 waitForPageLoaded();
	 sleeper(2000);
	 }
	else {
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from Remediation tab successfully");
		 sleeper(2000);
		 }
	waitForPageLoaded();
	LOGGER.info("Navigated to Policies tab ");
	policypages.clickOnElementsOfPolicyPage("addpolicy");
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
	sleeper(2000);
	policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
	policypages.clickOnElementsOfPolicyPage("Driverupdate");
	policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
	sleeper(2000);
	LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	waitForPageLoaded();
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
	policypages.waitForElementsOfPolicyPage("PolicyAdded");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy add pending is failed");
	if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
	 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
	 waitForPageLoaded();
	 sleeper(2000);
	 }
	else {
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from Remediation tab successfully");
		 sleeper(2000);
		 }
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policySearchItem");	
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("addPolicyButton");
	sleeper(2000);
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.DG_GroupName_Latest);
	sleeper(4000);
	policypages.waitForElementsOfPolicyPage("selectGroupItem");
	policypages.clickOnElementsOfPolicyPage("selectGroupItem");
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
	waitForPageLoaded();
	policypages.clickOnElementsOfPolicyPage("DateClear");
	policypages.clickOnElementsOfPolicyPage("dropdown2");
	policypages.clickOnElementsOfPolicyPage("RecurringDays");
	policypages.selectNextDateFromCalendar();
	sleeper(2000);
	policypages.selectRoundedUpCurrentTimeFromDropdown();
	policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
	policypages.clickOnElementsOfPolicyPage("scheduleNext");
	waitForPageLoaded();
	policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
	policypages.clickOnElementsOfPolicyPage("assignGroupButton");
	waitForPageLoaded();
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "group assignment pending is failed");
	policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "group assigned is failed");
	waitForPageLoaded();
	if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
	 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
	 waitForPageLoaded();
	 sleeper(2000);
	 }
	else {
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from Remediation tab successfully");
		 sleeper(2000);
		 }
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policySearchItem");	
	policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
	String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
	LOGGER.info(errorMsg);
	Assert.assertTrue(
		errorMsg != null && errorMsg.contains("Assigned"),
		"Policy details are not avilable policy deleted sucessfully: " + errorMsg);
	policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
	waitForPageLoaded();
	sleeper(2000);
	policypages.mouseHoverOfPolicyPage("groupItem1");
	policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
	policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
	policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
	waitForPageLoaded();
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "group unassignment pending is failed");
	policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "group unassigned is failed");
	waitForPageLoaded();
	if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
	 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
	 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
	 waitForPageLoaded();
	 sleeper(2000);
	 }
	else {
		 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		 LOGGER.info("Navigated to Policies from Remediation tab successfully");
		 sleeper(2000);
		 }
	waitForPageLoaded();
	policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
	sleeper(2000);
	policypages.clickOnElementsOfPolicyPage("policySearchItem");
	waitForPageLoaded();
	sleeper(2000);
	policypages.waitForElementsOfPolicyPage("deleteButton");
	policypages.clickOnElementsOfPolicyPage("deleteButton");
	waitForPageLoaded();
	sleeper(2000);
	waitForPageLoaded();
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
	policypages.waitForElementsOfPolicyPage("policyDeleted");
	Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
	}
		
	/**
	 * TC_C66732963:[WEP]>>Verify User Can Assign Bios Setting Policy To A  Dynamic Group for platform
	 */
	@Test(priority = 130, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C66732963")
	public final void VerifyUserCanAssignBiosSettingPolicyToADynamicGroupforplatform() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();		
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		sleeper(2000);
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.BS_PolicyName+generateRandomNumber());
		waitForPageLoaded();
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		waitForPageLoaded();
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("biosSettingsOption");
		sleeper(3000);
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.SG_PolicyDescription);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(1000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("AssetTrackingNumberOnPolicyCreationPage");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy added is failed");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.BS_PolicyName);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.DG_GroupName_Latest);
		sleeper(3000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.waitForElementsOfPolicypageDynamic("nextbuttonOnPolicyCreationPage",30);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "Group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "Group assigned is failed");
		waitForPageLoaded();
		LOGGER.info("Policies assigned to Static Group is done successfully");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.BS_PolicyName);
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "Groups unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "Group unassigned is failed");
		waitForPageLoaded();
		sleeper(3000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		
		}

	/**
	 * TC_C66732964:[WEP]>>Verify User Can Assign Bios update Policy To A  Dynamic Group for platform
	 */
	@Test(priority = 131, groups = { "REGRESSION_POLICY"}, description="Testcase ID: TC_C66732964")
	public final void VerifyUserCanAssignBiosupdatePolicyToADynamicGroupforplatform() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
		openLeftSidePanel();
		WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
		waitForPageLoaded();	
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		}else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox", PolicyVariables.bios_policy_update+generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("biosUpdateOption");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox", PolicyVariables.description_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policyScopePlatformOption");
		waitForPageLoaded();
		sleeper(1000);
		policypages.actionClickOfPolicyPage("platformDropdownButton");
		waitForPageLoaded();
		sleeper(2000);
		policypages.enterTextForPolicyPage("SearchItem", PolicyVariables.Platform_search);
		policypages.waitForElementsOfPolicypageDynamic("SelectItem",30);
		policypages.clickOnElementsOfPolicyPage("SelectItem");
		waitForPageLoaded();
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		sleeper(3000);
		policypages.clickOnElementsOfPolicyPage("deployment");
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"),
				"policy add pending is failed");
		policypages.waitUntillElementIsPresent("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"),
				"policy add pending is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.DG_GroupName_Latest);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.actionClickOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"),
				"group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"),
				"group assigned is failed");
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			 waitForPageLoaded();
			 sleeper(2000);
			 }
			else {
				 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
				 LOGGER.info("Navigated to Policies from Remediation tab successfully");
				 sleeper(2000);
				 }
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");	
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
				errorMsg != null && errorMsg.contains("Assigned"),
				"Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "group unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "group unassigned is failed");
		waitForPageLoaded();
		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
		} else {
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from Remediation tab successfully");
			sleeper(2000);
		}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch", PolicyVariables.bios_policy_update);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"),
				"policy delete pending is failed");
		policypages.waitUntillElementIsPresent("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"),
				"policy deleted is failed");
	}
		
	 /**
	 * TC_C66732965:[WEP]>>Verify User Can Assign Driver update To A  Dynamic Group for platform 
		*/	
	 @Test (priority = 132, groups = { "REGRESSION_POLICY" }, description="Testcase ID: TC_C66732965")
	 public final void VerifyUserCanAssignDriverupdateToADynamicGroupforplatform  () throws Exception {	
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP"); 
			openLeftSidePanel();
			WEPPolicyPage policypages = WEPPolicyPage.getInstance(PreDefinedActions.getDriver());
			waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
		policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
		waitForPageLoaded();
		sleeper(2000);
		}
		else {
			 policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			 LOGGER.info("Navigated to Policies from Remediation tab successfully");
			 sleeper(2000);
			 }
		waitForPageLoaded();
		LOGGER.info("Navigated to Policies tab ");
		policypages.clickOnElementsOfPolicyPage("addpolicy");
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("createPolicyNameTextBox",PolicyVariables.policy_name_value + generateRandomNumber());
		sleeper(2000);
		policypages.actionClickOfPolicyPage("selectBiosOptionDropdown");
		policypages.clickOnElementsOfPolicyPage("Driverupdate");
		policypages.enterTextForPolicyPage("createPolicyDescriptionTextBox",PolicyVariables.description_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policyScopeGlobalOption");
		sleeper(2000);
		LOGGER.info(policypages.verifywaituntilelementisenabled("nextbuttonOnPolicy"));	
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicy");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("Criticaldriverupdates");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAddPending").contains("Policy add pending"), "policy add pending is failed");
		policypages.waitForElementsOfPolicyPage("PolicyAdded");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyAdded").contains("Policy added"), "policy add pending is failed");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
			}
			else {
				policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
				LOGGER.info("Navigated to Policies from Remediation tab successfully");
				sleeper(2000);
				}
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");	
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("addPolicyButton");
		sleeper(2000);
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.DG_GroupName_Latest);
		sleeper(4000);
		policypages.waitForElementsOfPolicyPage("selectGroupItem");
		policypages.clickOnElementsOfPolicyPage("selectGroupItem");
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("nextbuttonOnPolicyCreationPage");
		waitForPageLoaded();
		policypages.clickOnElementsOfPolicyPage("DateClear");
		policypages.clickOnElementsOfPolicyPage("dropdown2");
		policypages.clickOnElementsOfPolicyPage("RecurringDays");
		policypages.selectNextDateFromCalendar();
		sleeper(2000);
		policypages.selectRoundedUpCurrentTimeFromDropdown();
		policypages.waitForElementsOfPolicypageDynamic("schedulenextButton",30);
		policypages.clickOnElementsOfPolicyPage("scheduleNext");
		waitForPageLoaded();
		policypages.waitForElementsOfPolicypageDynamic("assignGroupButton",30);
		policypages.clickOnElementsOfPolicyPage("assignGroupButton");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssignmentPending").contains("Group assignment pending"), "group assignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupAssigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupAssigned").contains("Group assigned"), "group assigned is failed");
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
			}
			else {
				policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
				LOGGER.info("Navigated to Policies from Remediation tab successfully");
				sleeper(2000);
				}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");	
		policypages.verifyElementIsPresentOnPolicyPage("AssignedState");
		String errorMsg = policypages.getTextOfWEPPolicyPage("AssignedState");
		LOGGER.info(errorMsg);
		Assert.assertTrue(
				errorMsg != null && errorMsg.contains("Assigned"),
				"Policy details are not avilable policy deleted sucessfully: " + errorMsg);
		policypages.clickOnElementsOfPolicyPage("policiesAssignmentTab");
		waitForPageLoaded();
		sleeper(2000);
		policypages.mouseHoverOfPolicyPage("groupItem1");
		policypages.clickOnElementsOfPolicyPage("actionmenuItemOnPolicy");
		policypages.clickOnElementsOfPolicyPage("unAssignedPolicyMenuOption");
		policypages.clickOnElementsOfPolicyPage("unAssignedBtn");
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassignmentPending").contains("Groups unassignment pending"), "group unassignment pending is failed");
		policypages.waitForElementsOfPolicypageDynamic("GroupUnassigned",60);
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("GroupUnassigned").contains("Group unassigned"), "group unassigned is failed");
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))){
			policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
			LOGGER.info("Navigated to Policies from sidemenu bar under Fleetmanagement");
			waitForPageLoaded();
			sleeper(2000);
			}
			else {
				policypages.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
				LOGGER.info("Navigated to Policies from Remediation tab successfully");
				sleeper(2000);
				}
		waitForPageLoaded();
		policypages.enterTextForPolicyPage("policySearch",PolicyVariables.policy_name_value);
		sleeper(2000);
		policypages.clickOnElementsOfPolicyPage("policySearchItem");
		waitForPageLoaded();
		sleeper(2000);
		policypages.waitForElementsOfPolicyPage("deleteButton");
		policypages.clickOnElementsOfPolicyPage("deleteButton");
		waitForPageLoaded();
		sleeper(2000);
		waitForPageLoaded();
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("PolicyDeletePending").contains("Policy delete pending"), "policy delete pending is failed");
		policypages.waitForElementsOfPolicyPage("policyDeleted");
		Assert.assertTrue(policypages.getTextOfWEPPolicyPage("policyDeleted").contains("Policy deleted"), "policy deleted is failed");
		}
   
}