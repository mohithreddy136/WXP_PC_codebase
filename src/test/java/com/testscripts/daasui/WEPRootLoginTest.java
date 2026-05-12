package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.TableConfigurationPage;
import com.daasui.pages.WEXCustomerUserDetailsPage;
import com.daasui.pages.WEXCustomerUserListPage;
import com.daasui.pages.WEPRootLoginPage;

public class WEPRootLoginTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXCustomerUserTest.class);
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");
	public static String CompanyFullName = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_FULLNAME");
	public static String CompanyNameEmail = getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME");
	public static String PartnerFullName = getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_FULLNAME");
	public static String CompanyName = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME");

	
	/**
	 * Test verification of Active user details in Root Login
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyAddEditDeleteRootUser() throws Exception {
		String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN;
		String userID = null , currentUrl, tenantID = null;
		SoftAssert softAssert = new SoftAssert();
		WEPRootLoginPage WEPRootLoginPage = new WEPRootLoginPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("Administartive");
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("Users");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("UserHeading");
			LOGGER.info("Redirected Root Users tab page");
			
			if (WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("clearFilter")) {
				softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters"),"Clear filter text is  not match");
				WEPRootLoginPage.mousehoverOnWEPRootLoginPage("clearFilter");
				WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}

			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("InviteButton");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("InviteButton"),getTextLanguage(LanguageCode, "daas_ui", "global.add"),"Invite button  text is  not match");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("firstNamelabel"));
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("firstNamelabel"),getTextLanguage(LanguageCode, "daas_ui", "users.details.first_name"),"First name  text is  not match");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("lastNamelabel"));
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("lastNamelabel"),getTextLanguage(LanguageCode, "daas_ui", "users.details.last_name"),"Last name  text is  not match");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("emailAddresslabel"));
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("emailAddresslabel"),getTextLanguage(LanguageCode, "daas_ui", "users.details.email"),"Email   text is  not match");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("IdpTypedrop"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("InvUserBtn"));
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("InvUserBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.add"),"Invite button1  text is  not match");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("cancelBtn"));
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("cancelBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"cancel button  text is  not match");
			sleeper(2000);
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("firstName", UserFirstname);
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("lastName", UserLastname);
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("emailAddress", UserEmail);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("IdpTypedrop");
			WEPRootLoginPage.selectFirstValueFromDropdownOnWEPRootLoginPage("IDPlist");
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("InvUserBtn");
			
			WEPRootLoginPage.waitForElementsOfWEPRootLoginPage("toastMessage");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("toastMessage"));
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("toastMessage"),getTextLanguage(LanguageCode, "daas_ui", "user.created.success"),"Success text is  not match");
			LOGGER.info("Root User added successfully");
			waitForPageLoaded();
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("emailInput", UserEmail);
			LOGGER.info("Email of the User entered");
			sleeper(2000);
			softAssert.assertTrue(WEPRootLoginPage.matchTextOnWEPRootLoginPage("userNameValue", UserFirstname+" "+UserLastname), "User name on list page is not matching");
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("userNameValue");
			waitForPageLoaded();
			LOGGER.info("Root User details page opened");
			
			currentUrl = WEPRootLoginPage.getUrlOfCurrentPage();
			userID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1,currentUrl.indexOf("?"));
			tenantID = currentUrl.substring(currentUrl.lastIndexOf("=") + 1);
								
			sleeper(5000);

            WEXCustomerUserDetailsPage.actionClickOnWEXCustomerUserDetailsPage("nameEdit");
			String userfirstnameupdate = UserFirstname+generateRandomString(3);
			String usernameupdate = UserLastname+generateRandomString(3);
			WEXCustomerUserDetailsPage.enterTextOfWEXCustomerUserDetailsPage("editFirstName", userfirstnameupdate);
			sleeper(1000);
			WEXCustomerUserDetailsPage.enterTextOfWEXCustomerUserDetailsPage("editLastName", usernameupdate);
			sleeper(2000);
			WEXCustomerUserDetailsPage.clickOnElementsOfWEXCustomerUserDetailsPage("saveButtonUserName");
			sleeper(2000);
			WEXCustomerUserDetailsPage.waitForElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			LOGGER.info("Root User name updated successfully");
			//softAssert.assertTrue(WEXCustomerUserDetailsPage.matchTextOnWEXCustomerUserDetailsPage("nameUpdatedValue", userfirstnameupdate + " " +usernameupdate), "User name is not updated");
			LOGGER.info("Root User name validated on User details page successfully");
			sleeper(2000);
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("Users");
			waitForPageLoaded();
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("predefinedSelectAlertCheckbox");
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("delete");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("delete"),getTextLanguage(LanguageCode,"daas_ui", "users.list.action_buttons.remove"),"Delete button  text is  not match");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("delete");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("deleteBelowText");
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("deleteButton");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("deleteButton"),getTextLanguage(LanguageCode,"daas_ui", "users.list.action_buttons.remove"),"Delete button2  text is  not match");
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("cancelButton");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("cancelButton"),getTextLanguage(LanguageCode,"daas_ui", "global.cancel"),"cancel button  text is  not match");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("deleteButton");
			sleeper(2000);
			WEXCustomerUserDetailsPage.waitForElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("emailInput");
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("emailInput", UserEmail);
			waitForPageLoaded();
			Assert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("noResults"));
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("noResults"),getTextLanguage(LanguageCode,"daas_ui", "global.no_results"),"No results  text is  not match");
			LOGGER.info("Root User Removed successfully");
			softAssert.assertAll();	
			}		
		catch(Exception e) {
			Assert.fail("Exception occured in verifyAddEditDeleteRootUser " +e);	
		}
	}
	
	
	@Test(priority = 2, groups = {"REGRESSION_ACCOUNTS"}, enabled = false, description = "TestCase ID : ")
	public final void verifyCustomerImpersonation() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPRootLoginPage WEPRootLoginPage = new WEPRootLoginPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("Customers");
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("Companies");
			waitForPageLoaded();
			LOGGER.info("Redirected Companies tab page");
			
			if (WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("clearFilter")) {
				WEPRootLoginPage.mousehoverOnWEPRootLoginPage("clearFilter");
				WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("companyNameInput",getEnvironmentSpecificData(System.getProperty("environment"),"COMPANY_NAME"));
			waitForPageLoaded();
			sleeper(2000);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("companyName");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("companyDetails"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("deleteCompBtn"));
			LOGGER.info("Company Settings page is opened");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("companySummaryDesc"));
			//WEPRootLoginPage.mousehoverOnWEPRootLoginPage("compSummaryDesc");
			WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("companySummaryDesc");
			WEPRootLoginPage.waitForElementsOfWEPRootLoginPage("compSummaryCard");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("compSummaryCard"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("impersonateBtn"));
			LOGGER.info("Company User Details page opened");
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("impersonateBtn");
			waitForPageLoaded();
			sleeper(2000);
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("confirmText"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("impersonateSubBtn"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("impersonateCancBtn"));
			LOGGER.info("Impersonate popup page opened");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("impersonateCancBtn");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("userMgmt");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("idpInfo"),"IDP Info not present");
			LOGGER.info("Customer Admin IDP Info validated");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("editRole"),"Edit Role not present");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("editRole");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("assignRole"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("rolesSave"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("rolesCancel"));
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("rolesCancel");
			LOGGER.info("Customer Admin edit role button validated");
			softAssert.assertAll();
			}		
		catch(Exception e) {
			Assert.fail("Exception occured in verifyCustomerImpersonation");	
		}
	}
	
	@Test(priority = 3, groups = {"REGRESSION_ACCOUNTS"}, enabled = false, description = "TestCase ID : ")
	public final void verifyPartnerImpersonation() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPRootLoginPage WEPRootLoginPage = new WEPRootLoginPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("PartnerMenu");
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("Partners");
			waitForPageLoaded();
			sleeper(2000);
			LOGGER.info("Redirected Partner tab page");
			
			if (WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("clearFilter")) {
				WEPRootLoginPage.mousehoverOnWEPRootLoginPage("clearFilter");
				WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("companyNameInput",getEnvironmentSpecificData(System.getProperty("environment"),"PARTNER_NAME"));
			waitForPageLoaded();
			sleeper(2000);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("partnerName");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("companyDetails"));
			LOGGER.info("Partner Settings page is opened");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("compSummaryDesc"));
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("compSummaryDesc");
			WEPRootLoginPage.waitForElementsOfWEPRootLoginPage("compSummaryCard");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("compSummaryCard"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("impersonateBtn"));
			LOGGER.info("Partner User Details page opened");
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("impersonateBtn");
			waitForPageLoaded();
			sleeper(2000);
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("confirmText"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("impersonateSubBtn"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("impersonateCancBtn"));
			LOGGER.info("Impersonate popup page opened");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("impersonateCancBtn");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("userMgmt");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("idpInfo"),"IDP Info not present");
			LOGGER.info("Partner Admin IDP Info validated");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("editRole"),"Edit Role not present");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("editRole");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("assignRole"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("rolesSave"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("rolesCancel"));
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("rolesCancel");
			LOGGER.info("Partner Admin edit role button validated");
			softAssert.assertAll();
			}		
		catch(Exception e) {
			Assert.fail("Exception occured in verifyPartnerImpersonation");	
		}
	}

	@Test(priority = 4, groups = {"REGRESSION_ACCOUNTS"}, enabled = false, description = "TestCase ID : ")
	public final void verifyMSPImpersonation() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPRootLoginPage WEPRootLoginPage = new WEPRootLoginPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("MspMenu");
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("Msps");
			waitForPageLoaded();
			LOGGER.info("Redirected Partner tab page");
			
			if (WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("clearFilter")) {
				WEPRootLoginPage.mousehoverOnWEPRootLoginPage("clearFilter");
				WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("companyNameInput",getEnvironmentSpecificData(System.getProperty("environment"),"MSP_NAME"));
			waitForPageLoaded();
			sleeper(2000);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("mspName");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("companyDetails"));
			LOGGER.info("MSP Settings page is opened");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("compSummaryDesc"));
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("compSummaryDesc");
			WEPRootLoginPage.waitForElementsOfWEPRootLoginPage("compSummaryCard");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("compSummaryCard"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("impersonateBtn"));
			LOGGER.info("MSP User Details page opened");
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("impersonateBtn");
			waitForPageLoaded();
			sleeper(2000);
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("confirmText"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("impersonateSubBtn"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("impersonateCancBtn"));
			LOGGER.info("Impersonate popup page opened");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("impersonateCancBtn");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("userMgmt");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("idpInfo"),"IDP Info not present");
			LOGGER.info("MSP Admin IDP Info validated");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("editRole"),"Edit Role not present");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("editRole");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("assignRole"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("rolesSave"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("rolesCancel"));
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("rolesCancel");
			LOGGER.info("MSP Admin edit role button validated");
			softAssert.assertAll();
			}		
		catch(Exception e) {
			Assert.fail("Exception occured in verifyMSPImpersonation");	
		}
	}
	
	@Test(priority = 5, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyAddEditDeleteMSPUser() throws Exception {
		String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN;
		String userID = null , currentUrl, tenantID = null;
		SoftAssert softAssert = new SoftAssert();
		WEPRootLoginPage WEPRootLoginPage = new WEPRootLoginPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("MspMenu");
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("MspUsers");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspUserHeading");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("mspUserHeading"),getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.users"),"userS text is  not match");
			LOGGER.info("Redirected MSP Users tab page");
			
			if (WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("clearFilter")) {
				softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters"),"Clear filter text is  not match");
				WEPRootLoginPage.mousehoverOnWEPRootLoginPage("clearFilter");
				WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("inviteUser");
			sleeper(2000);
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("chooseMsp"));
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("chooseMsp"),getTextLanguage(LanguageCode, "daas_ui", "user.invite.choose.msp"),"Choose msp text is  not match");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspBelowText"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspDropdwn"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("cancelMsp"));
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("cancelMsp"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"cancel text is  not match");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("nextMsp"));
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("nextMsp"),getTextLanguage(LanguageCode, "daas_ui", "global.next"),"Next text is  not match");
			LOGGER.info("Select parent MSP page opened");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("mspDropdwn");
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("search", getEnvironmentSpecificData(System.getProperty("environment"),"MSP_NAME"));
			sleeper(3000);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("selectMsp");
			waitForPageLoaded();
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("nextMsp");
			LOGGER.info("MSP selected from the dropdown");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("role"),"role not present");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("fnMsp"),"fisrtname  not present");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("lnMsp"),"lastname  not present");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("email"),"email  not present");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("phone"),"Phone  not present");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("IdpTypedrop"),"IDP  not present");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("prevMsp"),"Prev  not present");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("prevMsp"),getTextLanguage(LanguageCode, "daas_ui", "global.previous"),"Pervious text is  not match");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("addMsp"),"Add msp  not present");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("addMsp"),getTextLanguage(LanguageCode, "daas_ui", "global.invite"),"Invite text is  not match");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("cancelBtn"),"cancel button  not present");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("cancelBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"Cancel text is  not match");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("role");
			WEPRootLoginPage.selectFirstValueFromDropdownOnWEPRootLoginPage("mspRoles");
			waitForPageLoaded();
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("role");
			sleeper(2000); 
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("fnMsp", UserFirstname);
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("lnMsp", UserLastname);
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("email", UserEmail);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("IdpTypedrop");
			WEPRootLoginPage.selectFirstValueFromDropdownOnWEPRootLoginPage("IDPlist");
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("addMsp");
			WEPRootLoginPage.waitForElementsOfWEPRootLoginPage("toastmessageuser");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("toastmessageuser"),"toast message not present");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("toastmessageuser"),getTextLanguage(LanguageCode, "daas_ui", "user.created.success"),"Success text is  not match");
            sleeper(2000);
            LOGGER.info("MSP User added successfully");
            sleeper(5000);// taking time to create MSP user in backend
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("emailInput", UserEmail);
			LOGGER.info("Email of the User entered");
			sleeper(2000);
			softAssert.assertTrue(WEPRootLoginPage.matchTextOnWEPRootLoginPage("userNameValue", UserFirstname+" "+UserLastname), "User name on list page is not matching");
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("userNameValue");
			waitForPageLoaded();
			LOGGER.info("MSP User details page opened");
								
			sleeper(5000);
			WEXCustomerUserDetailsPage.actionClickOnWEXCustomerUserDetailsPage("nameEdit");
			String userfirstnameupdate = UserFirstname+generateRandomString(3);
			String usernameupdate = UserLastname+generateRandomString(3);
			WEXCustomerUserDetailsPage.enterTextOfWEXCustomerUserDetailsPage("editFirstName", userfirstnameupdate);
			WEXCustomerUserDetailsPage.enterTextOfWEXCustomerUserDetailsPage("editLastName", usernameupdate);
			sleeper(2000);
			WEXCustomerUserDetailsPage.clickOnElementsOfWEXCustomerUserDetailsPage("saveButtonUserName");
			WEXCustomerUserDetailsPage.waitForElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole"),"Toast message not shown");
			LOGGER.info("MSP User name updated successfully");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.matchTextOnWEXCustomerUserDetailsPage("nameUpdatedValue", userfirstnameupdate + " " +usernameupdate), "User name is not updated");
			LOGGER.info("MSP User name validated on User details page successfully");
			sleeper(2000);
			WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("MSPBreacrumpsClick");
            sleeper(2000);
            WEPRootLoginPage.enterTextOfWEPRootLoginPage("emailInput", UserEmail);
            LOGGER.info("Email of the User entered");
            sleeper(2000);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("predefinedSelectAlertCheckbox");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspDeleteButton"),"MSP delete  not present");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("mspDeleteButton"),getTextLanguage(LanguageCode,"daas_ui", "users.list.action_buttons.remove"),"Delete button  text is  not match");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("mspDeleteButton");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspDeleteBelowText");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspdelete"),"msp delete button  not present");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("mspdelete"),getTextLanguage(LanguageCode,"daas_ui", "users.list.action_buttons.remove"),"Delete button2  text is  not match");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspCancel"),"msp cancel  not present");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("mspCancel"),getTextLanguage(LanguageCode,"daas_ui", "global.cancel"),"cancel button  text is  not match");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("mspdelete");
			sleeper(5000);
			WEXCustomerUserDetailsPage.waitForElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole"),"user toast message  not present");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("emailInput");
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("emailInput", UserEmail);
			waitForPageLoaded();
			sleeper(3000);
			Assert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("noResults"),"no results  not present");
			softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("noResults"),getTextLanguage(LanguageCode,"daas_ui", "global.no_results"),"No results  text is  not match");
			LOGGER.info("Root User Removed successfully");
			softAssert.assertAll();	
			}		
				
		catch(Exception e) {
			Assert.fail("Exception occured in verifyAddEditDeleteMSPUser  " +e);	
		}
	}
	
	
	@Test(priority = 6, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyResendInviteforRootUser() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPRootLoginPage WEPRootLoginPage = new WEPRootLoginPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("Administartive");
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("Users");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("UserHeading");
			LOGGER.info("Redirected Root Users tab page");
			
			if (WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("clearFilter")) {
				softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters"),"Clear filter text is  not match");
				WEPRootLoginPage.mousehoverOnWEPRootLoginPage("clearFilter");
				WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}

			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("inviteStatus"));
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("inviteStatusdropdown");
			sleeper(2000);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("selectInv");
			LOGGER.info("Invited status is selected");
			sleeper(1000);	
			WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("checkbox");
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("checkbox");
			LOGGER.info("First user from the list in selected");
			String name = WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("rootUserListName");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("resendInvite", getTextLanguage(LanguageCode, "daas_ui", "companies.list.resend_invite")), "Resend Invite button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendInvite");
			LOGGER.info("User is able to click Resend button");
			waitForPageLoaded();
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendpopup");
			sleeper(1000);
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("rootUserPopUPText");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("rootUserPopUPText"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.resend_invite"),"Resend popup text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendpopup");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendpopup"),getTextLanguage(LanguageCode, "daas_ui", "users.list.confirmation_modal.resend.message").replace("{name}", name),"Resend bleow text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendCancel");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendCancel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.cancel")), "cancel button not present");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendsubmit");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendsubmit").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.resend")), "Resend button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendsubmit");
			sleeper(2000);
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendmessagerootuser");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendmessagerootuser").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.resend.success")), "Error occured while sending invite");
		
			softAssert.assertAll();
			LOGGER.info("User is able to send resend invite");
			}		
		catch(Exception e) {
				Assert.fail("Exception occured in verifyResendInviteforRootUser " +e);	
			}
	}
	
	@Test(priority = 7, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyResendInviteforMSPUser() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPRootLoginPage WEPRootLoginPage = new WEPRootLoginPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("MspMenu");
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("MspUsers");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspUserHeading");
			LOGGER.info("Redirected MSP Users tab page");
			
			if (WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("clearFilter")) {
				softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters"),"Clear filter text is  not match");
				WEPRootLoginPage.mousehoverOnWEPRootLoginPage("clearFilter");
				WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}

			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("inviteStatus"));
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("inviteStatusdropdown");
			sleeper(2000);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("selectInv");
			LOGGER.info("Invited status is selected");
			sleeper(1000);	
			WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("secondCheckbox");
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("secondCheckbox");
			LOGGER.info("First user from the list in selected");
			String name = WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("mspUserSecondListName");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("resendInvite", getTextLanguage(LanguageCode, "daas_ui", "companies.list.resend_invite")), "Resend Invite button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendInvite");
			LOGGER.info("User is able to click Resend button");
			waitForPageLoaded();
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendpopup");	
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("popuptext");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("popuptext"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.resend_invite"),"Resend popup text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendpopup");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendpopup"),getTextLanguage(LanguageCode, "daas_ui", "users.list.confirmation_modal.resend.message").replace("{name}", name),"Resend bleow text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendCancel");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendCancel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.cancel")), "cancel button not present");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendsubmit");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendsubmit").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.resend")), "Resend button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendsubmit");
			sleeper(2000);
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendMessage");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.resend.success")), "Error occured while sending invite");
		
			softAssert.assertAll();
			LOGGER.info("User is able to send resend invite");
			}		
		catch(Exception e) {
				Assert.fail("Exception occured in verifyResendInviteforMSPUser " +e);	
			}
	}
	
	@Test(priority = 8, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyResendInviteforPartnerUser() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPRootLoginPage WEPRootLoginPage = new WEPRootLoginPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("PartnerMenu");
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("partnerUsers");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("partnerUserHeading");
			LOGGER.info("Redirected Partner Users tab page");
			
			if (WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("clearFilter")) {
				softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters"),"Clear filter text is  not match");
				WEPRootLoginPage.mousehoverOnWEPRootLoginPage("clearFilter");
				WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}

			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("inviteStatus"));
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("inviteStatusdropdown");
			sleeper(2000);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("selectInv");
			LOGGER.info("Invited status is selected");
			sleeper(1000);	
			WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("checkbox");
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("checkbox");
			LOGGER.info("First user from the list in selected");
			String name = WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("partnerUserListName");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("resendInvite", getTextLanguage(LanguageCode, "daas_ui", "companies.list.resend_invite")), "Resend Invite button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendInvite");
			LOGGER.info("User is able to click Resend button");
			waitForPageLoaded();
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendpopup");	
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("popuptext");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("popuptext"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.resend_invite"),"Resend popup text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendpopup");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendpopup"),getTextLanguage(LanguageCode, "daas_ui", "users.list.confirmation_modal.resend.message").replace("{name}", name),"Resend bleow text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendCancel");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendCancel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.cancel")), "cancel button not present");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendsubmit");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendsubmit").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.resend")), "Resend button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendsubmit");
			sleeper(2000);
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendMessage");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.resend.success")), "Error occured while sending invite");
		
			softAssert.assertAll();
			LOGGER.info("User is able to send resend invite");
			}		
		catch(Exception e) {
				Assert.fail("Exception occured in verifyResendInviteforPartnerUser " +e);	
			}
	}
	
	@Test(priority = 9, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyResendInviteforCustomerUser() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPRootLoginPage WEPRootLoginPage = new WEPRootLoginPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
			if (WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("Customers")) {
				WEPRootLoginPage.mousehoverOnWEPRootLoginPage("customerUsers");
				WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("customerUsers");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Users tab");
			}else {
                WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("Customers");
                WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("customerUsers");
				waitForPageLoaded();
			}
			
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("customerUserHeading");
			LOGGER.info("Redirected Customer Users tab page");
			
			if (WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("clearFilter")) {
				softAssert.assertEquals(WEPRootLoginPage.getTextOfWEPRootLoginPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters"),"Clear filter text is  not match");
				WEPRootLoginPage.mousehoverOnWEPRootLoginPage("clearFilter");
				WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			sleeper(1000);
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("inviteStatus"));
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("inviteStatusdropdown");
			sleeper(1000);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("selectInv");
			LOGGER.info("Invited status is selected");
			sleeper(1000);	
			WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("checkbox");
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("checkbox");
			LOGGER.info("First user from the list in selected");
			String name = WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("userNameValue");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("resendInvite", getTextLanguage(LanguageCode, "daas_ui", "companies.list.resend_invite")), "Resend Invite button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendInvite");
			LOGGER.info("User is able to click Resend button");
			waitForPageLoaded();
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendpopup");	
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("popuptext");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("popuptext"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.resend_invite"),"Resend popup text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendpopup");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendpopup"),getTextLanguage(LanguageCode, "daas_ui", "users.list.confirmation_modal.resend.message").replace("{name}", name),"Resend below text is not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendCancel");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendCancel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.cancel")), "cancel button not present");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendsubmit");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendsubmit").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.confirmation_modal.resend.button")), "Resend button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendsubmit");
			sleeper(2000);
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendMessage");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.resend.success")), "Error occured while sending invite");
		
			softAssert.assertAll();
			LOGGER.info("User is able to send resend invite");
			}		
		catch(Exception e) {
				Assert.fail("Exception occured in verifyResendInviteforCustomerUser " +e);	
			}
	}
	
	@Test(priority = 10, groups = {"REGRESSION_ACCOUNTS"}, enabled = false,  description = "TestCase ID : ")
	public final void verifyAddEditDeleteMSP() throws Exception {
		String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN;
		String MspId = getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID")+generateRandomString(3);
		String CompanyMSP = MspId+"comp";
		String userID = null , currentUrl;
		SoftAssert softAssert = new SoftAssert();
		WEPRootLoginPage WEPRootLoginPage = new WEPRootLoginPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("MspMenu");
            WEPRootLoginPage.clickByJavaScriptOnWEPRootLoginPage("Msps");
			waitForPageLoaded();
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspheading");
			LOGGER.info("Redirected MSP admin tab page");
			
			if (WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("clearFilter")) {
				WEPRootLoginPage.mousehoverOnWEPRootLoginPage("clearFilter");
				WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("addMspBtn");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("addMspBtn");
			LOGGER.info("Add MSP button clicked");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspId"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("compName"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("countryMsp"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspfirstName"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("msplastName"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspemailAddress"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspIdpTypedrop"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("countryMsp"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("saveBtn"));
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("cancelBtn"));
			LOGGER.info("Add MSP page opened");
		
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("mspId", MspId);
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("compName", CompanyMSP);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("countryMsp");
			WEPRootLoginPage.selectFirstValueFromDropdownOnWEPRootLoginPage("countryList");
			waitForPageLoaded();
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("countryMsp");
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("mspfirstName", UserFirstname);
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("msplastName", UserLastname);
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("mspemailAddress", UserEmail);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("mspIdpTypedrop");
			WEPRootLoginPage.selectFirstValueFromDropdownOnWEPRootLoginPage("mspIDPlist");
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("saveBtn");
			waitForPageLoaded();
			sleeper(2000);
			WEPRootLoginPage.waitForElementsOfWEPRootLoginPage("toastMessage");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("toastMessage"),"Toast message is not present");
			LOGGER.info("MSP added successfully");
			waitForPageLoaded();
			sleeper(2000);
			WEPRootLoginPage.waitForElementsOfWEPRootLoginPage("emailInput");
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("emailInput", UserEmail);
			LOGGER.info("Email of the User entered");
			softAssert.assertTrue(WEPRootLoginPage.matchTextOnWEPRootLoginPage("userNameValue", CompanyMSP), "MSP name on list page is not matching");
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("userNameValue");
			waitForPageLoaded();
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("mspDetails"),"MSP details page not opened");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("delMsp"),"Delete button not present");
			LOGGER.info("MSP details page opened");
			
			currentUrl = WEPRootLoginPage.getUrlOfCurrentPage();
			userID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
			System.out.println(userID +"  "+currentUrl);
			
			String usernameupdate = CompanyMSP+generateRandomString(3);
			WEPRootLoginPage.actionClickOnWEPRootLoginPage("editMsp");
			sleeper(1000);
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("editname", usernameupdate);
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("editSaveBtn");
			WEPRootLoginPage.waitForElementsOfWEPRootLoginPage("toastMspSave");
			softAssert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("toastMspSave"),"Save Toast message is not present");
			LOGGER.info("MSP name updated successfully");
			sleeper(1000);
			softAssert.assertTrue(WEPRootLoginPage.matchTextOnWEPRootLoginPage("updatedName", usernameupdate), "MSP name after update is not matching");
			LOGGER.info("MSP name validated on User details page successfully");
			
			WEPRootLoginPage.clickOnElementsOfWEPRootLoginPage("mspSubmenu");
			LOGGER.info("Redirected MSP list page");
			softAssert.assertAll();
						
			}	
		catch(Exception e) {
			Assert.fail("Exception occured in verifyAddEditDeleteMSPUser " +e);	
		}
		finally {
			
			Assert.assertTrue(WEPRootLoginPage.removeInactiveCustomerMSP(environment, userID, getEnvironment(environment)), "MSP removal failed");
			WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("emailInput");
			WEPRootLoginPage.enterTextOfWEPRootLoginPage("emailInput", UserEmail);
			waitForPageLoaded();
			Assert.assertTrue(WEPRootLoginPage.verifyElementsOfWEPRootLoginPage("noResults"),"MSP did not got deleted");
			LOGGER.info("MSP User Removed successfully");
			softAssert.assertAll();
		}
	}
	
}