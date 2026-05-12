package com.testscripts.daasui;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ScriptVariables;
import com.daasui.pages.WEPPartnerCustomersPage;
import com.daasui.pages.WXPRolesandPermissionPage;

public class WXPRolesandPermissionTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WXPRolesandPermissionTest.class);

	@Test(priority = 1,  groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifyElementsOnRolesandPermissionPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			rolesPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		} else {
			rolesPage.companyView(CommonVariables.ACCOUNT);
		}
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleName"), "role column not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("description"),
				"Description column not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleType"),
				"Role type column not present");
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customType"),
				"custom type not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("moduleType"),
				"module type not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("basicType"), "basic type not present");
		softAssert.assertAll();
	}

	@Test(priority = 2, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifyTestRoleOnRolesandPermissionPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			rolesPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		} else {
			rolesPage.companyView(CommonVariables.ACCOUNT);
		}
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		rolesPage.mouseHoverOnRolesAndPermissionPage("mouseHoverOptions");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
        softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverOptions"),
                "mouser hover option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouserHoverDetails"),
				"details option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDuplicate"),
				"duplicate option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouserHoverTestRole"),
				"Test role not present");
		rolesPage.actionClickOfRolesAndPermissionPage("mouserHoverTestRole");
		waitForPageLoaded();
		sleeper(7000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("revertTestingRole"),
				"Revert testing role option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("revertTestingRole");
		waitForPageLoaded();
		sleeper(10000); //reverting takes time
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("account"),
				"Account header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rolesAndPermissionTab"),
				"Roles tab not present");
		LOGGER.info("Test Role verified on Role list page");
		
		rolesPage.actionClickOfRolesAndPermissionPage("roleOption");
		sleeper(5000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("createCustomeRoleHeader"),
				"Edit custome role header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "test role not present");
		rolesPage.actionClickOfRolesAndPermissionPage("testRole");
		waitForPageLoaded();
		sleeper(7000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("revertTestingRole"),
				"Revert testing role option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("revertTestingRole");
		waitForPageLoaded();
		sleeper(10000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "test role not present");
		LOGGER.info("Test Role verified on Edit Role page");
		
		rolesPage.actionClickOfRolesAndPermissionPage("accountBreadcrumb");
        sleeper(10000); //taking time to load the page after clicking on breadcrumb
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("account"),
				"Account header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rolesAndPermissionTab"),
				"Roles tab not present");
		softAssert.assertAll();
	}

	@Test(priority = 3, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifyDuplicatefunctionalityOnRolesandPermissionPage() throws Exception {
		String rolename = CompaniesVariables.DuplicateRole + generateRandomString(4).toLowerCase();
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			rolesPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		} else {
			rolesPage.companyView(CommonVariables.ACCOUNT);
		}
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		rolesPage.mouseHoverOnRolesAndPermissionPage("mouseHoverOptions");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
        softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverOptions"),
                "mouser hover option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDuplicate"),
				"duplicate option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverDuplicate");
		waitForPageLoaded();
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("createCustomeRoleHeader"),
				"Edit custome role header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "test role not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customRoleName"),
				"customer role name not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roledecription"),
				"role description not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesearch");
		rolesPage.enterTextOfRolesAndPermissionPage("rolesearch", rolename);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("selectPermissionHeader"),
				"select header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("belowPara"), "below para not present");
		rolesPage.actionClickOfRolesAndPermissionPage("templatescard1");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("saveAndContinueButton");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"),
				"Toast message not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("saveAndContinueButton");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleName"), "role column not present");
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", rolename);
		sleeper(3000);
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDelete"),
				"delete option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverDelete");
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deletePopupHeader"),
				"delete header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deletemessage"),
				"popup message not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deleteButton"),
				"delete button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleCancelButton"),
				"cancel button not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("deleteButton");
		sleeper(5000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"),
				"Delete Toast message not present");
		sleeper(2000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", rolename);
		sleeper(4000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("noResults"), "Role removal failed");
		softAssert.assertAll();
	}

	@Test(priority = 4, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifyADDfunctionalityOnRolesandPermissionPage() throws Exception {
		String testrolename = CompaniesVariables.ADDCUSTOMRole + generateRandomString(4).toLowerCase();
		String descriptionName = CompaniesVariables.DESCRIPTIOName;
		String UsersRoles = CompaniesVariables.UserRoleAdd;
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			rolesPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		} else {
			rolesPage.companyView(CommonVariables.ACCOUNT);
		}
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("addButton");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("accountBreadcrumb"),
				"Account breadcrumn not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rolesBreadcrumb"),
				"Role Breadcrumb not present");
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("createCustomeRoleHeader"),
				"Create custom role not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "Test Role header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customRoleName"),
				"CustomRole name field not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roledecription"),
				"Role description field not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesearch");
		rolesPage.enterTextOfRolesAndPermissionPage("rolesearch", testrolename);
		sleeper(1000);
		rolesPage.clickOnElementsOfRolesAndPermissionPage("descriptionsearch");
		rolesPage.enterTextOfRolesAndPermissionPage("descriptionsearch", descriptionName);
		sleeper(1000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("selectPermissionHeader"),
				"Select permission header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("belowPara"), "Below para not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "Test Role not present");
		
		//Verify all template cards visible
		softAssert.assertTrue(rolesPage.verifyTemplateCardsOnPage(), "Failed while verifying template cards");
		
		// Verify all permission tag present
		softAssert.assertTrue(rolesPage.verifyallPermissionAndTogglePresentOnPage(), "Failed while verifying permission and toggle cards");

		//Verify search functionality
		rolesPage.scrollOnRolesAndPermissionPage("permissionsHeader", "[1]");
		rolesPage.clickOnSpecificElementsOfRolesAndPermissionPage("permissionsHeader", "[1]");
		String label = rolesPage.getTextOfSpecificElementOnRolesAndPermissionPage("permissionToggleHeader", "[1]", "[1]");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rbacSearch"), "Rbac Search not present on page");
		rolesPage.enterTextOfRolesAndPermissionPage("rbacSearch", label);
		softAssert.assertTrue(rolesPage.getTextOfRolesAndPermissionPage("rbacSearchResult").contains("1"), "Search not work to find valid label");
		rolesPage.enterTextOfRolesAndPermissionPage("rbacSearch", "Invalid Input Provided");
		sleeper(3000);
		System.out.println(rolesPage.getTextOfRolesAndPermissionPage("rbacSearchResult"));
		softAssert.assertTrue(rolesPage.getTextOfRolesAndPermissionPage("rbacSearchResult").contains("0"), "Search not work to find Invalid label");

		
		//Click on first template card
		rolesPage.clickOnSpecificElementsOfRolesAndPermissionPage("templatesCardsHeader", "[1]");
		//Select first toggle of first permission
		rolesPage.clickOnSpecificElementsOfRolesAndPermissionPage("permissionToggleHeader", "[1]", "[1]");
		//Click on Save Button
		rolesPage.clickOnElementsOfRolesAndPermissionPage("saveAndContinueButton");
		sleeper(3000);
		
		//Verify RBAC details Page
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"), "toast message not present on page");
		waitForPageLoaded();
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rbacDetailsPageHeader"), "Rbac Details Page header not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rbacDetailsPageLabel"), "Rbac Details Page Label not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rbacDetailsPageExistingUserTitle"), "Rbac Details Page Existing User Title not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("userNameColumn"), "Username column label not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("emailColumn"), "Email column label not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("userNameSearch"), "UserName Search not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("emailSearch"), "EmailSearch not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addUserButton"), "AddUser Button not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("removeButton"), "Remove Button not present on page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("addUserButton");
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("assignUsersLabel"),
				"Assign users not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("userSearch"),
				"User search  not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("userSearch");
		rolesPage.enterTextOfRolesAndPermissionPage("userSearch", UsersRoles);
		sleeper(3000);
		rolesPage.actionClickOfRolesAndPermissionPage("checkbox");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("usersConfirm"), "User confirm Button not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("usersCancel"), "User cancel Button not present on page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("usersConfirm");
		sleeper(4000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"), " User role toast message not present on page");
		sleeper(4000);
		rolesPage.enterTextOfRolesAndPermissionPage("userRoleSearch", UsersRoles);
		sleeper(4000);
		rolesPage.actionClickOfRolesAndPermissionPage("firstCheckbox");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("removeButton");
		sleeper(4000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"), " User role  removal toast message not present on page");
		sleeper(4000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("noResults"), "User removal failed");
		sleeper(2000);
		rolesPage.clickOnElementsOfRolesAndPermissionPage("finishButton");
		waitForPageLoaded();
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", testrolename);
		sleeper(3000);
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDelete"),
				"delete option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverDelete");
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deletePopupHeader"),
				"delete header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deletemessage"),
				"popup message not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deleteButton"),
				"delete button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleCancelButton"),
				"cancel button not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("deleteButton");
		sleeper(4000);
		rolesPage.waitForElementsOffRolesAndPermissionPage("toastmessage");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"),
				"Delete Toast message not present");
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", testrolename);
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("noResults"), "Role removal failed");
		softAssert.assertAll();
	}
	
	@Test(priority = 5, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifymousehoverOnRolesandPermissionPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			rolesPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		} else {
			rolesPage.companyView(CommonVariables.ACCOUNT);
		}
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {
			
			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleType"),
				"Role type column not present");
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customType"),
				"custom type not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("moduleType"),
				"module type not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("basicType"), "basic type not present");
		rolesPage.actionClickOfRolesAndPermissionPage("customType");
		sleeper(2000);
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("noResults")){
			LOGGER.info("Data is not available");
		}
		else {
		rolesPage.mouseHoverOnRolesAndPermissionPage("mouseHoverOptions");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
            softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverOptions"),
                    "mouser hover option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouserHoverDetails"),
				"details option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDuplicate"),
				"duplicate option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDelete"),
				"Delete option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouserHoverTestRole"),
				"Test role not present");
		}
		rolesPage.actionClickOfRolesAndPermissionPage("clearButton");
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		sleeper(2000);
		rolesPage.actionClickOfRolesAndPermissionPage("basicType");
		sleeper(2000);
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("noResults")){
			LOGGER.info("Data is not available");
		}
		else {
		rolesPage.mouseHoverOnRolesAndPermissionPage("mouseHoverOptions");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
        softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverOptions"),
                    "mouser hover option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouserHoverDetails"),
				"details option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDuplicate"),
				"duplicate option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouserHoverTestRole"),
				"Test role not present");
		softAssert.assertTrue(rolesPage.verifyElementIsinvisibileOfRolesAndPermissionPage("mouseHoverDelete"),
				"Delete option is present for basic type");
		}
		
		softAssert.assertAll();
	}
	
	@Test(priority = 6,  groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifyElementsOnRolesandPermissionPagewithPartnerLogin() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		rolesPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.ACCOUNT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleName"), "role column not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("description"),
				"Description column not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleType"),
				"Role type column not present");
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customType"),
				"custom type not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("basicType"), "basic type not present");
		softAssert.assertAll();
	}
	
	@Test(priority = 7, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifyTestRoleOnRolesandPermissionPagewithPartnerLogin() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		rolesPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.ACCOUNT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		rolesPage.mouseHoverOnRolesAndPermissionPage("mouseHoverOptions");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
        softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverOptions"),
                "mouser hover option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouserHoverDetails"),
				"details option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDuplicate"),
				"duplicate option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouserHoverTestRole"),
				"Test role not present");
		rolesPage.actionClickOfRolesAndPermissionPage("mouserHoverTestRole");
		waitForPageLoaded();
		sleeper(7000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("revertTestingRole"),
				"Revert testing role option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("revertTestingRole");
		waitForPageLoaded();
		sleeper(7000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("account"),
				"Account header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rolesAndPermissionTab"),
				"Roles tab not present");
		rolesPage.actionClickOfRolesAndPermissionPage("roleOption");
		sleeper(5000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("createCustomeRoleHeader"),
				"Edit custome role header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "test role not present");
		rolesPage.actionClickOfRolesAndPermissionPage("testRole");
		waitForPageLoaded();
		sleeper(7000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("revertTestingRole"),
				"Revert testing role option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("revertTestingRole");
		waitForPageLoaded();
		sleeper(7000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "test role not present");
		rolesPage.actionClickOfRolesAndPermissionPage("accountBreadcrumb");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("account"),
				"Account header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rolesAndPermissionTab"),
				"Roles tab not present");
		softAssert.assertAll();
	}
	

	@Test(priority = 8, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifyDuplicatefunctionalityOnRolesandPermissionPagewithPartnerLogin() throws Exception {
		String rolename = CompaniesVariables.DuplicateRole + generateRandomString(4).toLowerCase();
		SoftAssert softAssert = new SoftAssert();
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");
		leftSideMenuVerification();
		rolesPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.ACCOUNT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		rolesPage.mouseHoverOnRolesAndPermissionPage("mouseHoverOptions");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
        sleeper(1000);
        softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverOptions"),
                "mouser hover option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDuplicate"),
				"duplicate option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverDuplicate");
		waitForPageLoaded();
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("createCustomeRoleHeader"),
				"Edit custome role header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "test role not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customRoleName"),
				"customer role name not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roledecription"),
				"role description not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesearch");
		rolesPage.enterTextOfRolesAndPermissionPage("rolesearch", rolename);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("selectPermissionHeader"),
				"select header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("belowPara"), "below para not present");
		rolesPage.actionClickOfRolesAndPermissionPage("templatescard1");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("saveAndContinueButton");
		sleeper(4000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"),
				"Toast message not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("saveAndContinueButton");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleName"), "role column not present");
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", rolename);
		sleeper(3000);
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDelete"),
				"delete option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverDelete");
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deletePopupHeader"),
				"delete header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deletemessage"),
				"popup message not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deleteButton"),
				"delete button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleCancelButton"),
				"cancel button not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("deleteButton");
		sleeper(5000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"),
				"Delete Toast message not present");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", rolename);
		sleeper(4000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("noResults"), "Role removal failed");
		softAssert.assertAll();
	}
	
	@Test(priority = 9, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifyADDfunctionalityOnRolesandPermissionPagewithPartnerLogin() throws Exception {
		String testrolename = CompaniesVariables.ADDCUSTOMRole + generateRandomString(4).toLowerCase();
		String descriptionName = CompaniesVariables.DESCRIPTIOName;
		String UsersRoles = CompaniesVariables.UserRoleAdd;
		SoftAssert softAssert = new SoftAssert();
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");
		waitForPageLoaded();
		leftSideMenuVerification();
		rolesPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.ACCOUNT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("addButton");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("accountBreadcrumb"),
				"Add button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rolesBreadcrumb"),
				"Add button not present");
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("createCustomeRoleHeader"),
				"Add button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "Add button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customRoleName"),
				"Add button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roledecription"),
				"Add button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "Add button not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesearch");
		rolesPage.enterTextOfRolesAndPermissionPage("rolesearch", testrolename);
		sleeper(1000);
		rolesPage.clickOnElementsOfRolesAndPermissionPage("descriptionsearch");
		rolesPage.enterTextOfRolesAndPermissionPage("descriptionsearch", descriptionName);
		sleeper(1000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("selectPermissionHeader"),
				"Add button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("belowPara"), "Add button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "Add button not present");
		
		//Verify all template cards visible
		softAssert.assertTrue(rolesPage.verifyTemplateCardsOnPage(), "Failed while verifying template cards");
		
		// Verify all permission tag present
		softAssert.assertTrue(rolesPage.verifyallPermissionAndTogglePresentOnPage(), "Failed while verifying permission and toggle cards");

		//Verify search functionality
		rolesPage.scrollOnRolesAndPermissionPage("permissionsHeader", "[1]");
		rolesPage.clickOnSpecificElementsOfRolesAndPermissionPage("permissionsHeader", "[1]");
		String label = rolesPage.getTextOfSpecificElementOnRolesAndPermissionPage("permissionToggleHeader", "[1]", "[1]");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rbacSearch"), "Rbac Search not present on page");
		rolesPage.enterTextOfRolesAndPermissionPage("rbacSearch", label);
		softAssert.assertTrue(rolesPage.getTextOfRolesAndPermissionPage("rbacSearchResult").contains("1"), "Search not work to find valid label");
		rolesPage.enterTextOfRolesAndPermissionPage("rbacSearch", "Invalid Input Provided");
		sleeper(3000);
		System.out.println(rolesPage.getTextOfRolesAndPermissionPage("rbacSearchResult"));
		softAssert.assertTrue(rolesPage.getTextOfRolesAndPermissionPage("rbacSearchResult").contains("0"), "Search not work to find Invalid label");

		
		//Click on first template card
		rolesPage.clickOnSpecificElementsOfRolesAndPermissionPage("templatesCardsHeader", "[1]");
		//Select first toggle of first permission
		rolesPage.clickOnSpecificElementsOfRolesAndPermissionPage("permissionToggleHeader", "[1]", "[1]");
		//Click on Save Button
		rolesPage.clickOnElementsOfRolesAndPermissionPage("saveAndContinueButton");
		sleeper(4000);
		
		//Verify RBAC details Page
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"), "toast message not present on page");
		waitForPageLoaded();
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rbacDetailsPageHeader"), "Rbac Details Page header not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rbacDetailsPageLabel"), "Rbac Details Page Label not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rbacDetailsPageExistingUserTitle"), "Rbac Details Page Existing User Title not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("userNameColumn"), "Username column label not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("emailColumn"), "Email column label not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("userNameSearch"), "UserName Search not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("emailSearch"), "EmailSearch not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addUserButton"), "AddUser Button not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("removeButton"), "Remove Button not present on page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("addUserButton");
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("assignUsersLabel"),
				"Assign users not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("userSearch"),
				"User search  not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("userSearch");
		rolesPage.enterTextOfRolesAndPermissionPage("userSearch", UsersRoles);
		sleeper(3000);
		rolesPage.actionClickOfRolesAndPermissionPage("checkbox");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("usersConfirm"), "User confirm Button not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("usersCancel"), "User cancel Button not present on page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("usersConfirm");
		sleeper(5000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"), " User role toast message not present on page");
		sleeper(4000);
		rolesPage.enterTextOfRolesAndPermissionPage("userRoleSearch", UsersRoles);
		sleeper(4000);
		rolesPage.actionClickOfRolesAndPermissionPage("firstCheckbox");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("removeButton");
		sleeper(4000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"), " User role  removal toast message not present on page");
		sleeper(4000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("noResults"), "User removal failed");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("finishButton");
		waitForPageLoaded();
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", testrolename);
		sleeper(3000);
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDelete"),
				"delete option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverDelete");
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deletePopupHeader"),
				"delete header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deletemessage"),
				"popup message not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deleteButton"),
				"delete button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleCancelButton"),
				"cancel button not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("deleteButton");
		sleeper(5000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"),
				"Delete Toast message not present");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", testrolename);
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("noResults"), "Role removal failed");
		softAssert.assertAll();
	}

	@Test(priority = 10,  groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifySearchandsortOnRolesandPermissionPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE,
				getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			rolesPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		} else {
			rolesPage.companyView(CommonVariables.ACCOUNT);
		}
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleName"), "role column not present");
		String input = rolesPage.getTextOfRolesAndPermissionPage("roleOption");
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", input);
		rolesPage.clickOnElementsOfRolesAndPermissionPage("roleNameSearchButton");
		sleeper(3000);
		List<WebElement> records = rolesPage.getllAllElementsVisibleofRolesAndPermissionPage("recordsInTable");
		softAssert.assertTrue(records.size()> 0, "search not working correctly");
		softAssert.assertEquals(input, rolesPage.getTextOfRolesAndPermissionPage("roleOption"), "invalidsearch");
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", "invalidTextEntered#");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("roleNameSearchButton");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("noRecordText"),"No result not present");
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleType"),
				"Role type column not present");
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customType"),
				"custom type not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("moduleType"),
				"module type not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("basicType"), "basic type not present");
		String Option = rolesPage.getTextOfRolesAndPermissionPage("customType");
		rolesPage.actionClickOfRolesAndPermissionPage("customType");
		sleeper(2000);
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		sleeper(3000);
		rolesPage.waitForElementsOffRolesAndPermissionPage("typeColumn");
		softAssert.assertTrue(rolesPage.verifyFilteredDataOnOnRolesAndPermissionPage("typeColumn", Option), "Filtered data is incorrect for status column");
		softAssert.assertAll();
	}
		
	@Test(priority = 11,  groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifySearchandsortOnRolesandPermissionPagewithPartnerLogin() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		rolesPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.ACCOUNT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleName"), "role column not present");
		String input = rolesPage.getTextOfRolesAndPermissionPage("roleOption");
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", input);
		rolesPage.clickOnElementsOfRolesAndPermissionPage("roleNameSearchButton");
		sleeper(3000);
		List<WebElement> records = rolesPage.getllAllElementsVisibleofRolesAndPermissionPage("recordsInTable");
		softAssert.assertTrue(records.size()> 0, "search not working correctly");
		softAssert.assertEquals(input, rolesPage.getTextOfRolesAndPermissionPage("roleOption"), "invalidsearch");
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", "invalidTextEntered#");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("roleNameSearchButton");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("noRecordText"),"No result not present");
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleType"),
				"Role type column not present");
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customType"),
				"custom type not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("basicType"), "basic type not present");
		String Option = rolesPage.getTextOfRolesAndPermissionPage("customType");
		rolesPage.actionClickOfRolesAndPermissionPage("customType");
		sleeper(2000);
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		sleeper(3000);
		rolesPage.waitForElementsOffRolesAndPermissionPage("typeColumn");
		softAssert.assertTrue(rolesPage.verifyFilteredDataOnOnRolesAndPermissionPage("typeColumn", Option), "Filtered data is incorrect for status column");
		softAssert.assertAll();
	}
	
	@Test(priority = 12,  groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifyElementsOnRolesandPermissionPagewithMSPLogin() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_EMAIL_WEP", "MSP_ADMIN_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		rolesPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.ACCOUNT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleName"), "role column not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("description"),
				"Description column not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleType"),
				"Role type column not present");
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customType"),
				"custom type not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("basicType"), "basic type not present");
		softAssert.assertAll();
	}
	
	@Test(priority = 13,  groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifySearchandsortOnRolesandPermissionPagewithMSPLogin() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_EMAIL_WEP", "MSP_ADMIN_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		rolesPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.ACCOUNT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleName"), "role column not present");
		String input = rolesPage.getTextOfRolesAndPermissionPage("roleOption");
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", input);
		rolesPage.clickOnElementsOfRolesAndPermissionPage("roleNameSearchButton");
		sleeper(3000);
		List<WebElement> records = rolesPage.getllAllElementsVisibleofRolesAndPermissionPage("recordsInTable");
		softAssert.assertTrue(records.size()> 0, "search not working correctly");
		softAssert.assertEquals(input, rolesPage.getTextOfRolesAndPermissionPage("roleOption"), "invalidsearch");
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", "invalidTextEntered#");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("roleNameSearchButton");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("noRecordText"),"No result not present");
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleType"),
				"Role type column not present");
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customType"),
				"custom type not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("basicType"), "basic type not present");
		String Option = rolesPage.getTextOfRolesAndPermissionPage("customType");
		rolesPage.actionClickOfRolesAndPermissionPage("customType");
		sleeper(2000);
		rolesPage.actionClickOfRolesAndPermissionPage("typeSelect");
		sleeper(3000);
		rolesPage.waitForElementsOffRolesAndPermissionPage("typeColumn");
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("noRecordText")) {

		}
		else {
		softAssert.assertTrue(rolesPage.verifyFilteredDataOnOnRolesAndPermissionPage("typeColumn", Option), "Filtered data is incorrect for status column");
		}
		softAssert.assertAll();
		
	}
	
	@Test(priority = 14, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifyTestRoleOnRolesandPermissionPagewithmspLogin() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_EMAIL_WEP", "MSP_ADMIN_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		rolesPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.ACCOUNT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		rolesPage.mouseHoverOnRolesAndPermissionPage("mouseHoverOptions");
        rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverOptions"),
				"mouser hover option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouserHoverDetails"),
				"details option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDuplicate"),
				"duplicate option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouserHoverTestRole"),
				"Test role not present");
		rolesPage.actionClickOfRolesAndPermissionPage("mouserHoverTestRole");
		waitForPageLoaded();
		sleeper(7000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("revertTestingRole"),
				"Revert testing role option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("revertTestingRole");
		waitForPageLoaded();
		sleeper(7000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("account"),
				"Account header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rolesAndPermissionTab"),
				"Roles tab not present");
		rolesPage.actionClickOfRolesAndPermissionPage("roleOption");
		sleeper(5000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("createCustomeRoleHeader"),
				"Edit custome role header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "test role not present");
		rolesPage.actionClickOfRolesAndPermissionPage("testRole");
		waitForPageLoaded();
		sleeper(7000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("revertTestingRole"),
				"Revert testing role option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("revertTestingRole");
		waitForPageLoaded();
		sleeper(7000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "test role not present");
		rolesPage.actionClickOfRolesAndPermissionPage("accountBreadcrumb");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("account"),
				"Account header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rolesAndPermissionTab"),
				"Roles tab not present");
		softAssert.assertAll();
	}
	
	@Test(priority = 15, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifyDuplicatefunctionalityOnRolesandPermissionPagewithMSPLogin() throws Exception {
		String rolename = CompaniesVariables.DuplicateRole + generateRandomString(4).toLowerCase();
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_EMAIL_WEP", "MSP_ADMIN_PASSWORD_WEP");
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		rolesPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.ACCOUNT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		rolesPage.mouseHoverOnRolesAndPermissionPage("mouseHoverOptions");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
        softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverOptions"),
                "mouser hover option not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDuplicate"),
				"duplicate option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverDuplicate");
		waitForPageLoaded();
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("createCustomeRoleHeader"),
				"Edit custome role header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "test role not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customRoleName"),
				"customer role name not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roledecription"),
				"role description not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesearch");
		rolesPage.enterTextOfRolesAndPermissionPage("rolesearch", rolename);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("selectPermissionHeader"),
				"select header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("belowPara"), "below para not present");
		rolesPage.actionClickOfRolesAndPermissionPage("templatescard1");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("saveAndContinueButton");
		sleeper(5000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"),
				"Toast message not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("saveAndContinueButton");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleName"), "role column not present");
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", rolename);
		sleeper(3000);
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDelete"),
				"delete option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverDelete");
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deletePopupHeader"),
				"delete header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deletemessage"),
				"popup message not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deleteButton"),
				"delete button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleCancelButton"),
				"cancel button not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("deleteButton");
		sleeper(5000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"),
				"Delete Toast message not present");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", rolename);
		sleeper(4000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("noResults"), "Role removal failed");
		softAssert.assertAll();
	}
	
	@Test(priority = 16, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID= :")
	public final void verifyADDfunctionalityOnRolesandPermissionPagewithMSPLogin() throws Exception {
		String testrolename = CompaniesVariables.ADDCUSTOMRole + generateRandomString(4).toLowerCase();
		String descriptionName = CompaniesVariables.DESCRIPTIOName;
		String UsersRoles = CompaniesVariables.UserRoleAdd;
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_EMAIL_WEP", "MSP_ADMIN_PASSWORD_WEP");
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WXPRolesandPermissionPage rolesPage = new WXPRolesandPermissionPage(PreDefinedActions.getDriver())
				.getInstance();
		waitForPageLoaded();
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");
		
		leftSideMenuVerification();
		rolesPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.ACCOUNT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesAndPermissionTab");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		rolesPage.waitForElementsOffRolesAndPermissionPage("addButton");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addButton"), "Add button not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("addButton");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("accountBreadcrumb"),
				"accountBreadcrumb button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rolesBreadcrumb"),
				"rolesBreadcrumb button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("createCustomeRoleHeader"),
				"createCustomeRoleHeader button not present");
		rolesPage.waitForElementsOffRolesAndPermissionPage("testRole");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "testRole button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("customRoleName"),
				"customRoleName button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roledecription"),
				"roledecription button not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("rolesearch");
		rolesPage.enterTextOfRolesAndPermissionPage("rolesearch", testrolename);
		sleeper(1000);
		rolesPage.clickOnElementsOfRolesAndPermissionPage("descriptionsearch");
		rolesPage.enterTextOfRolesAndPermissionPage("descriptionsearch", descriptionName);
		sleeper(1000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("selectPermissionHeader"),
				"selectPermissionHeader button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("belowPara"), "belowPara button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("testRole"), "test Role button not present");
		
		//Verify all template cards visible
		softAssert.assertTrue(rolesPage.verifyTemplateCardsOnPage(), "Failed while verifying template cards");
		
		// Verify all permission tag present
		softAssert.assertTrue(rolesPage.verifyallPermissionAndTogglePresentOnPage(), "Failed while verifying permission and toggle cards");

		//Verify search functionality
		rolesPage.scrollOnRolesAndPermissionPage("permissionsHeader", "[1]");
		rolesPage.clickOnSpecificElementsOfRolesAndPermissionPage("permissionsHeader", "[1]");
		String label = rolesPage.getTextOfSpecificElementOnRolesAndPermissionPage("permissionToggleHeader", "[1]", "[1]");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rbacSearch"), "Rbac Search not present on page");
		rolesPage.enterTextOfRolesAndPermissionPage("rbacSearch", label);
		softAssert.assertTrue(rolesPage.getTextOfRolesAndPermissionPage("rbacSearchResult").contains("1"), "Search not work to find valid label");
		rolesPage.enterTextOfRolesAndPermissionPage("rbacSearch", "Invalid Input Provided");
		sleeper(3000);
		softAssert.assertTrue(rolesPage.getTextOfRolesAndPermissionPage("rbacSearchResult").contains("0"), "Search not work to find Invalid label");

		//Click on first template card
		rolesPage.clickOnSpecificElementsOfRolesAndPermissionPage("templatesCardsHeader", "[1]");
		//Select first toggle of first permission
		rolesPage.clickOnSpecificElementsOfRolesAndPermissionPage("permissionToggleHeader", "[1]", "[1]");
		//Click on Save Button
		rolesPage.clickOnElementsOfRolesAndPermissionPage("saveAndContinueButton");
		sleeper(4000);
		
		//Verify RBAC details Page
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"), "toast message not present on page");
		waitForPageLoaded();
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rbacDetailsPageHeader"), "Rbac Details Page header not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rbacDetailsPageLabel"), "Rbac Details Page Label not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("rbacDetailsPageExistingUserTitle"), "Rbac Details Page Existing User Title not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("userNameColumn"), "Username column label not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("emailColumn"), "Email column label not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("userNameSearch"), "UserName Search not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("emailSearch"), "EmailSearch not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("addUserButton"), "AddUser Button not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("removeButton"), "Remove Button not present on page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("addUserButton");
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("assignUsersLabel"),
				"Assign users not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("userRoleSearch"),
				"User search  not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("userSearch");
		rolesPage.enterTextOfRolesAndPermissionPage("userSearch", UsersRoles);
		sleeper(3000);
		rolesPage.actionClickOfRolesAndPermissionPage("checkbox");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("usersConfirm"), "User confirm Button not present on page");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("usersCancel"), "User cancel Button not present on page");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("usersConfirm");
		sleeper(5000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"), " User role toast message not present on page");
		sleeper(4000);
		rolesPage.enterTextOfRolesAndPermissionPage("userRoleSearch", UsersRoles);
		sleeper(4000);
		rolesPage.actionClickOfRolesAndPermissionPage("firstCheckbox");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("removeButton");
		sleeper(5000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"), " User role  removal toast message not present on page");
		sleeper(4000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("noResults"), "User removal failed");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("finishButton");
		waitForPageLoaded();
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", testrolename);
		sleeper(3000);
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverOptions");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("mouseHoverDelete"),
				"delete option not present");
		rolesPage.actionClickOfRolesAndPermissionPage("mouseHoverDelete");
		sleeper(2000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deletePopupHeader"),
				"delete header not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deletemessage"),
				"popup message not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("deleteButton"),
				"delete button not present");
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("roleCancelButton"),
				"cancel button not present");
		rolesPage.clickOnElementsOfRolesAndPermissionPage("deleteButton");
		sleeper(5000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("toastmessage"),
				"Delete Toast message not present");
		sleeper(3000);
		if (rolesPage.verifyElementsOfRolesAndPermissionPage("clearFilter")) {

			rolesPage.mouseHoverOnRolesAndPermissionPage("clearFilter");
			rolesPage.clickOnElementsOfRolesAndPermissionPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		rolesPage.enterTextOfRolesAndPermissionPage("rolenameSearch", testrolename);
		sleeper(3000);
		softAssert.assertTrue(rolesPage.verifyElementsOfRolesAndPermissionPage("noResults"), "Role removal failed");
		softAssert.assertAll();
	}
}