package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ScriptVariables;
import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class WEPPartnerUsersTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEPPartnerUsersTest.class);
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");
	public static String CompanyName = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME");

	/**
	 * This test case is to verify Add/Edit/Delete Customer User in Customer Login
	 * https://hp-jira.external.hp.com/browse/WEXALLI-59
	 * @throws Exception
	 */
	@Test(priority = 1, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : C43504777")
	public final void verifyAddEditDeletePartnerUserInPartnerLogin() throws Exception {
		String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN;
		LOGGER.info("User email id is: " + UserEmail);
		String userID = null , currentUrl, invitationCode, tenantID = null, apiURL;
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerUserListPage WEXPartnerUserListPage = new WEXPartnerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();

		try {
			login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
			leftSideMenuVerification();
			softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
			softAssert.assertAll();
			LOGGER.info("Partner login verified successfully");
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "PARTNER_EMAIL_WEP "))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");

			softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("overview", getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.overview_title")), "Overview is incorrect");

			softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("partnerNameIdentity"), "partnerNameIdentity is not present");
			String PartnerName2 = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("partnerNameIdentity");
			LOGGER.info("Partner Name is : " + PartnerName2);
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("InviteButton");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserTitle");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addManualText");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("upLoadCSVText");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("cancelButtonText");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addManualText");
			
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("InviteTitle", getTextLanguage(LanguageCode, "daas_ui", "invite.users.list.company_selection.title")), "Invite users Title name is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("userInformationText", getTextLanguage(LanguageCode, "daas_ui", "users.add_manually.text")), "User Information text is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("stepInformation", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step1")), "Step 1 of 2 text is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("subHeadingAddUser", getTextLanguage(LanguageCode, "daas_ui", "users.invite_manually.sub_text")), "Sub Heading text is incorrect");
			
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addAnotherUserBtn");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("cancelButtonText");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserBackBtn");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserNextBtn");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("firstNameTextBox");
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("firstNameTextBox", UserFirstname);
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("lastNameTextBox");
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("lastNameTextBox", UserLastname);
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("emailTextBox");
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailTextBox", UserEmail);
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("idpTypeDropDown");
			WEXCustomerUserListPage.selectFirstValueFromDropdownOnWEXCustomerUserListPage("idTypeDropDownList");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
			
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("stepInformation", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step2")), "Step 2 of 2 text is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("roleDropResult",CommonVariables.PARTNER_ADMIN), "Role drop down result is incorrect");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("toastMessage");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("toastMessage");
			LOGGER.info("Partner User added successfully");
			waitForPageLoaded();
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput", UserEmail);
			LOGGER.info("Email of the User entered");
			softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("emailInputValue", UserEmail.toLowerCase()), "User email on list page is not matching");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("roleListValue_partner", CommonVariables.PARTNER_ADMIN), "User role on list page is not matching");
			softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("userNameValue", UserFirstname+" "+UserLastname), "User name on list page is not matching");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("emailInputValue");
			waitForPageLoaded();
			LOGGER.info("User details page opened");
			
			currentUrl = WEXCustomerUserListPage.getUrlOfCurrentPage();
			userID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1,currentUrl.indexOf("?"));
			tenantID = currentUrl.substring(currentUrl.lastIndexOf("=") + 1);
			apiURL = getEnvironment(System.getProperty("environment"))+CommonVariables.DETAILSSEARCHSERVICEAPI+tenantID+CommonVariables.DETAILSSEARCHSERVICEAPI2;
			invitationCode = WEXCustomerUserListPage.getInviationcode(apiURL,"_id", tenantID, UserEmail);
						
			sleeper(5000);
			WEXCustomerUserDetailsPage.clickOnElementsOfWEXCustomerUserDetailsPage("nameEdit");
			String usernameupdate = UserLastname+generateRandomString(3);
			WEXCustomerUserDetailsPage.enterTextOfWEXCustomerUserDetailsPage("editLastName", usernameupdate);
			WEXCustomerUserDetailsPage.clickOnElementsOfWEXCustomerUserDetailsPage("saveButtonUserName");

			WEXCustomerUserDetailsPage.waitForElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			LOGGER.info("Partner User name updated successfully");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.matchTextOnWEXCustomerUserDetailsPage("nameUpdatedValue", UserFirstname + " " +usernameupdate), "User name is not updated");
			LOGGER.info("Partner User name validated on User details page successfully");
			
			WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
			waitForPageLoaded();
			LOGGER.info("Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput", UserEmail);
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("tableOverlay");
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("emailInputValue");
			softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("emailInputValue", UserEmail.toLowerCase()), "User email on list page after updaet is not matching");
			softAssert.assertTrue(WEXPartnerUserListPage.matchTextOnWEXPartnerUserListPage("userNameValue", UserFirstname + " " +usernameupdate), "User name after update on list page is not matching");
			LOGGER.info("Customer User name validated on User List page successfully");


			String actualMailContent = WEXPartnerUserListPage.getActualMailContent(UserEmail);
			if (actualMailContent==null){
				softAssert.assertTrue(false,"Exception occured in method getActualMailContent ");
			}
			String expectedMailContent = WEXPartnerUserListPage.getExpectedMailContent(LanguageCode,PartnerName2, invitationCode,getEnvironment(System.getProperty("environment")));
			if (expectedMailContent==null){
				softAssert.assertTrue(false,"Exception occured in method expectedMailContent ");
			}
			//Assert.assertTrue(actualMailContent.contains(expectedMailContent), "Mail content is not matching");
		
		}catch(Exception e) {
			Assert.fail("Exception occurred in verifyAddEditDeleteCustomerUserInCustomerLogin");
		}finally {
			
//			 Delete Created Customer user
			Assert.assertTrue(WEXCustomerUserListPage.removeInactiveCustomerUser(environment, tenantID, userID, getEnvironment(environment)), "User removal failed");
			
			WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
			waitForPageLoaded();
			LOGGER.info("Account management-Overview tab page");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("usersTab");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("emailInput");
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput", UserEmail);
			waitForPageLoaded();
			Assert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("noResults"));
			LOGGER.info("Customer User Removed successfully");
			softAssert.assertAll();
		}
	}
	
	/**
	 * Test verification of Active user details in Customer Login
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : C51280777",enabled = false)
	public final void verifyActivePartnerUsersInPartnerLogin() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerUserListPage WEXPartnerUserListPage = new WEXPartnerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();
		try {
			login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
			leftSideMenuVerification();
			softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
			softAssert.assertAll();
			LOGGER.info("Partner login verified successfully");
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "PARTNER_EMAIL_WEP "))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
			}else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
			}
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatusdropResult");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("acceptedStatus",getTextLanguage(LanguageCode, "daas_ui", "users.details.status.accepted")));
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("acceptedStatus");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")){	
				LOGGER.info("Data is not available");
			}
			else {
			WEXPartnerUserListPage.clickByJavaScriptOnPartnerUserListPage("emailInputValue");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfile"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagement"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userRoles"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("deleteBtn"));
			}
			softAssert.assertAll();
			LOGGER.info("Customer Active User verified successfully");
			}		
			catch(Exception e) {
				Assert.fail("Exception occured in verifyActiveCustomerUserInCustomerLogin");	
			}
	}

	/**
	 * Test verification of Inactive user details in Customer Login
	 * 
	 * @throws Exception
	 */
	@Test(priority = 3, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : C51280778",enabled = false)
	public final void verifyInactivePartnerUsersInPartnerLogin() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerUserListPage WEXPartnerUserListPage = new WEXPartnerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();
		try {
			login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
			leftSideMenuVerification();
			softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
			softAssert.assertAll();
			LOGGER.info("Partner login verified successfully");
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "PARTNER_EMAIL_WEP "))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
			}else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
			}
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");


			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatusdropResult");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("invitedStatus",getTextLanguage(LanguageCode, "daas_ui", "users.details.status.invited")));
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("invitedStatus");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")){	
				LOGGER.info("Data is not available");
			}
			else {
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("userlistEmailField");
				waitForPageLoaded();
				softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfile"));
				softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagement"));
				softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userRoles"));
			}
			softAssert.assertAll();
			LOGGER.info("Customer InActive User verified successfully");
			}
			
			catch(Exception e) {
				Assert.fail("Exception occured in verifyInactiveCustomerUserInCustomerLogin");	
			}
		
	}

	
	/**
	 * Test verification of Resend Invite for Inactive user in Partner Login
	 * 
	 * @throws Exception
	 */
	@Test(priority = 4, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : C43504781",enabled = false)
	public final void verifyResendInviteInPartnerLoginforPartnerUsers() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();

		WEXPartnerUserListPage WEXPartnerUserListPage = new WEXPartnerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

		try {
			login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
			leftSideMenuVerification();
			softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
			softAssert.assertAll();
			LOGGER.info("Partner login verified successfully");
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "PARTNER_EMAIL_WEP "))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
			} else {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
			}
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");


			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");

			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}

			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatuscol");
			LOGGER.info("Clicked on the User Status column");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("invitedStatus");
			LOGGER.info("Selected Invited status from the dropdown");
			sleeper(1000);
			WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("checkbox");
			sleeper(1000);
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("checkbox");
			waitForPageLoaded();
			LOGGER.info("User checkbox is clicked");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("resendInvite", getTextLanguage(LanguageCode, "daas_ui", "companies.list.resend_invite")), "Resend Invite button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendInvite");
			LOGGER.info("User is able to click Resend button");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendpopup"));
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("popuptext"));
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendpopup"));
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendCancel"));
			LOGGER.info("Resend popup window is displayed");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendsubmit").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.resend")), "Resend button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendsubmit");
			sleeper(2000);
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendMessage");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.resend.success")), "Error occured while sending invite");
			softAssert.assertAll();
			LOGGER.info("User is able to send resend invite in Partner login");

		} catch (Exception e) {
			Assert.fail("Exception occured in verifyResendInviteInPartnerLogin");
		}
	}

	@Test(priority = 5, groups = {"REGRESSION_PARTNERCX","PRODUCTION_PARTNERCX"}, description = "TestCase ID : 44395132")
	public final void verifySalesSpecialistWithBreadcrumbNavigation() throws Exception {
		login("SALES_SPECIALIST_EMAIL", "SALES_SPECIALIST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();

		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		LOGGER.info("Partner login verified successfully");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("HomeTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.home")), "Home tab text is not matching");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("HomeTab"), "Home tab is not clickable");
		waitForLoaderIconToDisappear();
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.home")), "Home tab Breadcrumb is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomersTab", getTextLanguage(LanguageCode, "daas_ui", "whats_new.customers")), "Customers tab text is not matching");

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("RequestsTab", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.requests")), "Requests tab text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FleetAnalyticsTab", getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab text is not matching");
		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("LicenseSubscription", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.tab.LicensesAndSubscriptions")), "LicensesAndSubscriptions text is not matching");

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("ProductCatalogTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.catalogs")), "Product Catalog side menu text is not matching");

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("BillingTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.billing")), "Billing tab text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("IntegrationTab", getTextLanguage(LanguageCode, "daas_ui", "integrations.tab.integrations")), "Integration tab text is not matching");

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SettingsTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.settings")), "Settings tab text is not matching");

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AccountManagementTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.account")), "Account Management tab text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("HelpAndSupportButton"), "Help & Support button is not matching");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("HelpAndSupportButton"), "Help & Support tab is not clickable");
		waitForLoaderIconToDisappear();
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help & Support tab Breadcrumb is not matching");

		softAssert.assertAll();
		LOGGER.info("All the partner tabs verified successfully");
	}

	/**
	 * This method will verify the all the partner tabs and their navigation
	 */
	@Test(priority = 6, groups = {"REGRESSION_PARTNERCX","PRODUCTION_PARTNERCX"}, description = "TestCase ID : 44395131")
	public final void verifyBillingAdminTabsWithBreadcrumbNavigation() throws Exception {
		login("BILLING_ADMIN_EMAIL", "BILLING_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

		leftSideMenuVerification();
		waitForPageLoaded();

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("HomeTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.home")), "Home tab text is not matching");
		softAssert.assertFalse(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("HomeTab"), "Home tab is not clickable");
		waitForLoaderIconToDisappear();
		softAssert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.home")), "Home tab Breadcrumb is not matching");

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomersTab", getTextLanguage(LanguageCode, "daas_ui", "whats_new.customers")), "Customers tab text is not matching");

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("RequestsTab", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.requests")), "Requests tab text is not matching");

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FleetAnalyticsTab", getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AccountManagementTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.account")), "Account Management tab text is not matching");

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("ProductCatalogTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.catalogs")), "Product Catalog side menu text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("BillingTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.billing")), "Billing tab text is not matching");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("BillingTab"), "Billing tab is not clickable");
		waitForLoaderIconToDisappear();

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("IntegrationTab", getTextLanguage(LanguageCode, "daas_ui", "integrations.tab.integrations")), "Integration tab text is not matching");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("IntegrationTab"), "Integration tab is not clickable");
		waitForLoaderIconToDisappear();
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "integrations.tab.integrations")), "Integration tab Breadcrumb is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SettingsTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.settings")), "Settings tab text is not matching");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("SettingsTab"), "Settings tab is not clickable");
		waitForLoaderIconToDisappear();

		Assert.assertTrue(WEXPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("HelpAndSupportButton"), "Help & Support button is not matching");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("HelpAndSupportButton"), "Help & Support tab is not clickable");
		waitForLoaderIconToDisappear();
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help & Support tab Breadcrumb is not matching");

		softAssert.assertAll();
		LOGGER.info("All the partner tabs verified successfully");
	}

	@Test(priority = 7, groups = {"REGRESSION_PARTNERCX","PRODUCTION_PARTNERCX"}, description = "TestCase ID : 4439515876")
	public final void verifyServiceSpecialistWithBreadcrumbNavigation() throws Exception {
		login("SERVICE_SPECIALIST_EMAIL", "SERVICE_SPECIALIST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();

		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		LOGGER.info("Partner login verified successfully");
		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("HomeTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.home")), "Home tab text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomersTab", getTextLanguage(LanguageCode, "daas_ui", "whats_new.customers")), "Customers tab text is not matching");

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("RequestsTab", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.requests")), "Requests tab text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FleetAnalyticsTab", getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")), " Analytics tab text is not matching");
		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("LicenseSubscription", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.tab.LicensesAndSubscriptions")), "LicensesAndSubscriptions text is not matching");

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("ProductCatalogTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.catalogs")), "Product Catalog side menu text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AlertsTab", getTextLanguage(LanguageCode, "daas_ui", "alerts.breadcrumbs.title")), "Alert Management side menu text is not matching");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("AlertsDropDown"), "AlertsTab is not clickable");
		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AlertsManagement", getTextLanguage(LanguageCode, "daas_ui", "global_alertsManagement")), "Alert Management side menu text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("HardwareSupport", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.hardwareSupport")), "Hardware Support text is not matching");

		Assert.assertFalse(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("BillingTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.billing")), "Billing tab text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("IntegrationTab", getTextLanguage(LanguageCode, "daas_ui", "integrations.tab.integrations")), "Integration tab text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SettingsTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.settings")), "Settings tab text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AccountManagementTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.account")), "Account Management tab text is not matching");

		Assert.assertTrue(WEXPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("HelpAndSupportButton"), "Help & Support button is not matching");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("HelpAndSupportButton"), "Help & Support tab is not clickable");
		waitForLoaderIconToDisappear();
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help & Support tab Breadcrumb is not matching");

		softAssert.assertAll();
		LOGGER.info("All the partner tabs verified successfully on service specialist");
	}
}