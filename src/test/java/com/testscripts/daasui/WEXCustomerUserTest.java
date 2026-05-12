package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.Objects;

import com.daasui.constants.*;
import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;


public class WEXCustomerUserTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXCustomerUserTest.class);
	public String UserFirstname = CompaniesVariables.USER_FN + generateRandomString(4).toLowerCase();
	public String UserLastname = CompaniesVariables.USER_LN + generateRandomString(4).toLowerCase();
	public static String CompanyFullName = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_FULLNAME");
	public static String CompanyNameEmail = getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME");
	public static String PartnerFullName = getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_FULLNAME");
	public static String CompanyName = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME");
	public static String itadmin = getEnvironmentSpecificData(System.getProperty("environment"), "ROLE1");
	public static String connectoradmin = getEnvironmentSpecificData(System.getProperty("environment"), "ROLE2");
	public static String lostadmin = getEnvironmentSpecificData(System.getProperty("environment"), "ROLE3");
    public String company_email;
	/**
	 * This test case is to verify Add/Edit/Delete Customer User in Customer Login
	 * https://hp-jira.external.hp.com/browse/WEXALLI-59
	 * @throws Exception
	 */
	@Test(priority = 1, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : C43504777")
	public final void verifyAddEditDeleteCustomerUserInCustomerLogin() throws Exception {
		String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN;
		String userID = null , currentUrl, invitationCode, tenantID = null, apiURL, subjectInvite, inviteEmailContent, loginLink;
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			waitForPageLoaded();
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("InviteButton");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("InviteButton"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.invite_user")," Invite user text is  not match");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("InviteButton");
			
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserTitle");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addUserTitle"),getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.title"),"Add user text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addManualText");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addManualText"),getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.first.add.manually"),"Manual Add text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("upLoadCSVText");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("upLoadCSVText"),getTextLanguage(LanguageCode, "daas_ui", "global.uploadcsv"),"Upload csv text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("cancelButtonText");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("cancelButtonText"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"cancel text is  not match");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addManualText");
			
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("InviteTitle", getTextLanguage(LanguageCode, "daas_ui", "invite.users.list.company_selection.title")), "Invite users Title name is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("userInformationText", getTextLanguage(LanguageCode, "daas_ui", "users.add_manually.text")), "User Information text is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("stepInformation", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step1")), "Step 1 of 2 text is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("subHeadingAddUser", getTextLanguage(LanguageCode, "daas_ui", "admin.invite_manually.sub_text")), "Sub Heading text is incorrect");
			
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
			softAssert.assertTrue(WEXCustomerUserListPage.matchValueOnWEXCustomerUserListPage("roleDropResult",CommonVariables.REPORT_ADMIN), "Role drop down result is incorrect");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("toastMessage");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("toastMessage");
			LOGGER.info("Customer User added successfully");
			waitForPageLoaded();
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput", UserEmail);
			LOGGER.info("Email of the User entered");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("emailInputValue", UserEmail.toLowerCase()), "User email on list page is not matching");
            WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("roleListValue");
            softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("userNameValue", UserFirstname+" "+UserLastname), "User name on list page is not matching");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("emailInputValue");
			waitForPageLoaded();
			LOGGER.info("User details page opened");

			sleeper(5000);
			WEXCustomerUserDetailsPage.actionClickOnWEXCustomerUserDetailsPage("nameEdit");
			String usernamefirstupdate = UserFirstname+generateRandomString(3);
			String usernameupdate = UserLastname+generateRandomString(3);
			WEXCustomerUserDetailsPage.enterTextOfWEXCustomerUserDetailsPage("editFirstName", usernamefirstupdate);
			WEXCustomerUserDetailsPage.enterTextOfWEXCustomerUserDetailsPage("editLastName", usernameupdate);
			WEXCustomerUserDetailsPage.clickOnElementsOfWEXCustomerUserDetailsPage("saveButtonUserName");

			WEXCustomerUserDetailsPage.waitForElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			LOGGER.info("Customer User name updated successfully");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.matchTextOnWEXCustomerUserDetailsPage("nameUpdatedValue", usernamefirstupdate + " " +usernameupdate), "User name is not updated");
			LOGGER.info("Customer User name validated on User details page successfully");
			
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			sleeper(2000);
			//Accept the invite
			
			subjectInvite = getEnvironmentSpecificData(System.getProperty("environment"), "TENANT_INIT") + " invites you to the HP Workforce Experience Platform (WXP)!";
            LOGGER.info("subjectInvite "+ subjectInvite);
            LOGGER.info("Customer User Email: "+ UserEmail);
			
			inviteEmailContent = WEXCustomerUserListPage.getActualMailContent(UserEmail, subjectInvite);
            if(Objects.equals(inviteEmailContent, "")) {
                LOGGER.info("Email not received in first attempt, clicking checkbox and resending invitation");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("Firstcheckbox");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("resendinvitation");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("resendbutton");
                sleeper(3000); // Wait for resend action to complete
                
                // Try to get the email content again after resending
                inviteEmailContent = WEXCustomerUserListPage.getActualMailContent(UserEmail, subjectInvite);
                if(Objects.equals(inviteEmailContent, "")) {
                    LOGGER.error("Email not received even after resending invitation");
                    // You might want to throw an exception or handle this case as per your test requirements
                }
            }
			loginLink = WEXCustomerUserListPage.extractLink(inviteEmailContent);
			LOGGER.info("Invitation email link is: {}", loginLink);
			logout();
			sleeper(2000);
			getDriver().navigate().to(loginLink);
			waitForPageLoaded();
            sleeper(7000);
            LOGGER.info("Verified email address!");
            waitForPageLoaded();
            // Onboarding Page
            sleeper(7000);
            WEXCustomerUserListPage.onBoardInvitedUser(UserEmail);
			LOGGER.info("Customer User Invite Accepted successfully");
			logout();
			sleeper(2000);
			
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			sleeper(2000);
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");		
			sleeper(2000);
			
			softAssert.assertEquals(WEXCustomerUserListPage.verifyDeleteUser(UserEmail),getTextLanguage(LanguageCode, "daas_ui", "global.no_results")," No results text is  not match");
			LOGGER.info("Customer User Removed successfully"); 
			softAssert.assertAll();
			/*
			 * String actualMailContent =
			 * WEXCustomerUserListPage.verifyEmailContent("INVITED_USER_EMAIL_SUBJECT",
			 * environment, UserEmail,true); int count = 0; while (count < 5 &&
			 * actualMailContent == "") { sleeper(1000); count++; System.out.println(count +
			 * " : Email not received"); actualMailContent =
			 * WEXCustomerUserListPage.verifyEmailContent("INVITED_USER_EMAIL_SUBJECT",
			 * environment, UserEmail,true); } String subactualMail =
			 * actualMailContent.substring(0 , actualMailContent.lastIndexOf("Welcome"));
			 * String expectedMailContent = (getTextLanguage(LanguageCode,
			 * "notification_service", "com.hp.ns.admin.central.invite.hi.there")+ " " +
			 * getTextLanguage(LanguageCode, "daas_ui",
			 * "com.hp.ns.welcome.user.subject").replace("{0}",CommonVariables.APP_NAME)+
			 * " " + getTextLanguage(LanguageCode, "notification_service",
			 * "com.hp.ns.welcome.user.hello.admin.central").replace("{0}", UserFirstname)+
			 * "," + " " + getTextLanguage(LanguageCode, "notification_service",
			 * "com.hp.ns.invite.user.mail.body1.one.cloud").replace("{0}",CompanyFullName).
			 * replace("{1}",CompanyName).replace("{2}" , CommonVariables.APP_NAME)+ " " +
			 * getTextLanguage(LanguageCode, "notification_service",
			 * "com.hp.ns.invite.user.mail.body3.one.cloud") + " " + CommonVariables.SIGN_IN
			 * + " " + getTextLanguage(LanguageCode, "notification_service",
			 * "com.hp.ns.admin.central.link.paste") + " " +
			 * getEnvironment(System.getProperty("environment"))+
			 * CommonVariables.INVITATION_LINK +invitationCode + " " +
			 * getTextLanguage(LanguageCode, "daas_ui", "adminx.signature_thanks") ); //+
			 * " " //+ CommonVariables.APP_NAME + " " +"Team"+ " " +"Â" //+
			 * getTextLanguage(LanguageCode, "lhserver",
			 * "global.copyright").replace("%{current_year}", getCurrentYear()));
			 * LOGGER.info(actualMailContent); LOGGER.info(subactualMail);
			 * LOGGER.info(expectedMailContent);
			 * Assert.assertTrue(subactualMail.contains(expectedMailContent),
			 * "Mail content is not matching");
			 */
		
		}catch(Exception e) {
			Assert.fail("Exception occured in verifyAddEditDeleteCustomerUserInCustomerLogin"+ e.getMessage());
		}
	}
	
	/**
	 * Test verification of Active user details in Customer Login
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2, groups = {"REGRESSION_ACCOUNTS", "PRODUCTION_ACCOUNTS"}, description = "TestCase ID : C51280777")
	public final void verifyActiveCustomerUserInCustomerLogin() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			
		    if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters")," clear filter text is  not match");
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			softAssert.assertEquals((WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("invitestatus").split("\n")[0]),getTextLanguage(LanguageCode, "daas_ui", "users.list.label.invite.status")," Invite text is  not match");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatusdropResult");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("acceptedStatus",getTextLanguage(LanguageCode, "daas_ui", "users.details.status.accepted")));
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("acceptedStatus");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")){
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("noItemsAvailable"),getTextLanguage(LanguageCode, "daas_ui", "global.no_results")," No results text is  not match");
				LOGGER.info("Data is not available");
			}
			else {
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("userlistEmailField");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfileHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userProfileHeader"),getTextLanguage(LanguageCode, "daas_ui", "users.profile")," user profile text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfile"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagementHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userManagementHeader"),getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_management")," user management text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagement"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userRolesHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userRolesHeader"),getTextLanguage(LanguageCode, "daas_ui", "global.form.roles")," roles text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userRoles"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("deleteBtn"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("deleteBtn"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.delete")," Delete text is  not match");
			}
			softAssert.assertAll();
			LOGGER.info("Customer Active User verified successfully");
			}		
			catch(Exception e) {
				Assert.fail("Exception occured in verifyActiveCustomerUserInCustomerLogin " +e);	
			}
	}

	/**
	 * Test verification of Inactive user details in Customer Login
	 * 
	 * @throws Exception
	 */
	@Test(priority = 3, groups = {"REGRESSION_ACCOUNTS", "PRODUCTION_ACCOUNTS"}, description = "TestCase ID : C51280778")
	public final void verifyInactiveCustomerUserInCustomerLogin() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters")," clear filter text is  not match");
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			softAssert.assertEquals((WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("invitestatus").split("\n")[0]),getTextLanguage(LanguageCode, "daas_ui", "users.list.label.invite.status")," Invite text is  not match");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatusdropResult");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("invitedStatus",getTextLanguage(LanguageCode, "daas_ui", "users.details.status.invited")));
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("invitedStatus");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")){
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("noItemsAvailable"),getTextLanguage(LanguageCode, "daas_ui", "global.no_results")," No results text is  not match");
				LOGGER.info("Data is not available");
			}
			else {
			    WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("userlistEmailField");
				waitForPageLoaded();
				softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfileHeader"));
				softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userProfileHeader"),getTextLanguage(LanguageCode, "daas_ui", "users.profile")," user profile text is  not match");
				softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfile"));
				softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagementHeader"));
				softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userManagementHeader"),getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_management")," user management text is  not match");
				softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagement"));
				softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userRolesHeader"));
				softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userRolesHeader"),getTextLanguage(LanguageCode, "daas_ui", "global.form.roles")," roles text is  not match");
				softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userRoles"));
			}
			softAssert.assertAll();
			LOGGER.info("Customer InActive User verified successfully");
			}
			
			catch(Exception e) {
				Assert.fail("Exception occured in verifyInactiveCustomerUserInCustomerLogin  " +e);	
			}
		
	}	
	
	/**
	 * Test verification of Resend Invite for Inactive user in Customer Login
	 * 
	 * @throws Exception
	 */
	@Test(priority = 4, groups = {"REGRESSION_ACCOUNTS", "PRODUCTION_ACCOUNTS"}, description = "TestCase ID : C43504780")
	public final void verifyResendInviteInCustomerLogin() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			sleeper(2000);
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters")," clear filter text is  not match");
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			softAssert.assertEquals((WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("invitestatus").split("\n")[0]),getTextLanguage(LanguageCode, "daas_ui", "users.list.label.invite.status")," Invite text is  not match");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatusdropResult");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("invitedStatus");
			LOGGER.info("Invited status is selected");
			WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("checkbox");
			sleeper(1000);
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("checkbox");
			LOGGER.info("First user from the list in selected");

			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("resendInvite", getTextLanguage(LanguageCode, "daas_ui", "companies.list.resend_invite")), "Resend Invite button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendInvite");
			LOGGER.info("User is able to click Resend button");
			waitForPageLoaded();
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendpopup");	
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("popuptext");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("popuptext"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.resend")," Resend Invite text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendpopup");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendCancel");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendCancel"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"cancel text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendsubmit").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.resend")), "Resend button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendsubmit");
			sleeper(2000);
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendMessage");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.resend.success")), "Error occured while sending invite");
		
			softAssert.assertAll();
			LOGGER.info("User is able to send resend invite");
			}		
		catch(Exception e) {
				Assert.fail("Exception occured in verifyResendInviteInCustomerLogin " +e);	
			}
	}
	
	/**
	 * Test verification of Resend Invite for Inactive user in Partner Login
	 * 
	 * @throws Exception
	 */
	@Test(priority = 5, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : C43504781")
	public final void verifyResendInviteInPartnerLogin() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();

		try {
			login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("partnerPopup")) {
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("closePopup");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Close Customer Exp view popup");
			}
			dashboardPage.leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "PARTNER_EMAIL_WEP"))) {
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME"),CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME"),CommonVariables.ACCOUNT);
                sleeper(3000);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			sleeper(2000);
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters")," clear filter text is  not match");
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
						
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("invitestatus"),getTextLanguage(LanguageCode, "daas_ui", "users.list.label.invite.status")," Invite text is  not match");
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
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("popuptext"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.resend")," Resend Invite text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendpopup"));
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendCancel"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendCancel"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"cancel text is  not match");
			LOGGER.info("Resend popup window is displayed");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendsubmit").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.resend")), "Resend button not present");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendsubmit");
			sleeper(2000);
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendMessage");
			softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.resend.success")), "Error occured while sending invite");
			softAssert.assertAll();
			LOGGER.info("User is able to send resend invite in Partner login");
				
			}		
			catch(Exception e) {
				Assert.fail("Exception occured in verifyResendInviteInPartnerLogin" + e);	
			}
	}
	
    /**
	 * This test case is to verify Add/Edit/Delete Customer User in Partner Login
	 * https://hp-jira.external.hp.com/browse/WEXALLI-59
	 * @throws Exception
	 */
	@Test(priority = 6, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : C43504777")
	public final void verifyAddEditDeleteCustomerUserInPartnerLogin() throws Exception {
		String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN;
		String inviteEmailContent, loginLink;
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();

		try {
			login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("partnerPopup")) {
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("closePopup");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Close Customer Exp view popup");
			}
			dashboardPage.leftSideMenuVerification();
			
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "PARTNER_EMAIL_WEP"))) {
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"),CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"),CommonVariables.ACCOUNT);
		    }	  		
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("usersTab");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("InviteButton");
			waitForPageLoaded();
			LOGGER.info("User Invite popup is shown");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserTitle"));
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addAADText"));
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addManualText"));
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("upLoadCSVText"));
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("cancelButtonText"));
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addManualText");
			LOGGER.info("Invite Users popup window is shown");
			
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("InviteTitle", getTextLanguage(LanguageCode, "daas_ui", "invite.users.list.company_selection.title")), "Invite users Title name is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("userInformationText", getTextLanguage(LanguageCode, "daas_ui", "users.add_manually.text")), "User Information text is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("stepInformation", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step1")), "Step 1 of 2 text is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("subHeadingAddUser", getTextLanguage(LanguageCode, "daas_ui", "admin.invite_manually.sub_text")), "Sub Heading text is incorrect");
			
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addAnotherUserBtn");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserCancelBtn");
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
			LOGGER.info("Assign Users roles window is shown");
			
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("stepInformation", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step2")), "Step 2 of 2 text is incorrect");
			sleeper(2000);
			softAssert.assertTrue(WEXCustomerUserListPage.matchValueOnWEXCustomerUserListPage("roleDropResult",CommonVariables.REPORT_ADMIN), "Role drop down result is incorrect");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
			LOGGER.info("New User info is submitted");
			
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendMessage");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendMessage"));
			LOGGER.info("Customer User added successfully");
						
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput", UserEmail);
			LOGGER.info("Customer User email is enterd in email column searchbox");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("emailInputValue", UserEmail.toLowerCase()), "User email on list page is not matching");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("roleListValue", CommonVariables.REPORT_ADMIN), "User role on list page is not matching");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("userNameValue", UserFirstname+" "+UserLastname), "User name on list page is not matching");
			LOGGER.info("Found the newly added user");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("emailInputValue");
			waitForPageLoaded();
			LOGGER.info("User detail page is opened");
							
			sleeper(5000);
			String usernamefirstupdate = UserFirstname+generateRandomString(3);
			String usernameupdate = UserLastname+generateRandomString(3);
			WEXCustomerUserDetailsPage.actionClickOnWEXCustomerUserDetailsPage("nameEdit");
			WEXCustomerUserDetailsPage.enterTextOfWEXCustomerUserDetailsPage("editFirstName", usernamefirstupdate);
			WEXCustomerUserDetailsPage.enterTextOfWEXCustomerUserDetailsPage("editLastName", usernameupdate);
			sleeper(2000);
			WEXCustomerUserDetailsPage.clickOnElementsOfWEXCustomerUserDetailsPage("saveButtonUserName");
			sleeper(2000);
			WEXCustomerUserDetailsPage.waitForElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			LOGGER.info("Customer User name updated successfully");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.matchTextOnWEXCustomerUserDetailsPage("nameUpdatedValue", usernamefirstupdate + " " +usernameupdate), "User name is not updated");
			LOGGER.info("Customer User name validated on User details page successfully");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("partnerPopup")) {
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("closePopup");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Close Customer Exp view popup");
			}
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");

            WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
            sleeper(2000);

			//Accept the invite
			LOGGER.info("Customer user email in Partner login: "+ UserEmail);

            String subjectInvite = "You have been invited to the Workforce Experience Platform (WXP)!";
            LOGGER.info("subjectInvite "+ subjectInvite);
            LOGGER.info("Customer User Email: "+ UserEmail);

            inviteEmailContent = WEXCustomerUserListPage.getActualMailContent(UserEmail, subjectInvite);
            if(Objects.equals(inviteEmailContent, "")) {
                LOGGER.info("Email not received in first attempt, clicking checkbox and resending invitation");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("Firstcheckbox");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("resendinvitation");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("resendbutton");
                sleeper(3000); // Wait for resend action to complete

                // Try to get the email content again after resending
                inviteEmailContent = WEXCustomerUserListPage.getActualMailContent(UserEmail, subjectInvite);
                if(Objects.equals(inviteEmailContent, "")) {
                    LOGGER.error("Email not received even after resending invitation");
                    // You might want to throw an exception or handle this case as per your test requirements
                }
            }
            loginLink = WEXCustomerUserListPage.extractLink(inviteEmailContent);
            LOGGER.info("Invitation email link is: {}", loginLink);
            logout();
            sleeper(2000);
            getDriver().navigate().to(loginLink);
            waitForPageLoaded();
            // Onboarding Page
            sleeper(7000);
            WEXCustomerUserListPage.onBoardInvitedUser(UserEmail);
            LOGGER.info("Customer User Invite Accepted successfully");
            logout();
            sleeper(3000);
			
			login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("partnerPopup")) {
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("closePopup");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Close Customer Exp view popup");
			}
			dashboardPage.leftSideMenuVerification();
			dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"),CommonVariables.ACCOUNT);
		  	
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");		
			sleeper(2000);
			
			softAssert.assertEquals(WEXCustomerUserListPage.verifyDeleteUser(UserEmail),getTextLanguage(LanguageCode, "daas_ui", "global.no_results")," No results text is  not match");
			LOGGER.info("Customer User Removed successfully"); 
			
			softAssert.assertAll();	
		}catch(Exception e) {
				Assert.fail("Exception occured in verifyAddEditDeleteCustomerUserInPartnerLogin");
						
			/* String expectedMailContent = (getTextLanguage(LanguageCode, "notification_service", "com.hp.ns.admin.central.invite.hi.there")+ " " 
					+ getTextLanguage(LanguageCode, "daas_ui", "com.hp.ns.welcome.user.subject").replace("{0}",CommonVariables.APP_NAME)+ " " 
					+ getTextLanguage(LanguageCode, "notification_service", "com.hp.ns.welcome.user.hello.admin.central").replace("{0}", UserFirstname)+ "," + " "
					+ getTextLanguage(LanguageCode, "notification_service", "com.hp.ns.invite.user.mail.body1.one.cloud").replace("{0}",PartnerFullName).replace("{1}",CompanyNameEmail).replace("{2}" , CommonVariables.APP_NAME)+ " "
					+ getTextLanguage(LanguageCode, "notification_service", "com.hp.ns.invite.user.mail.body3.one.cloud") + " "
					+ CommonVariables.SIGN_IN + " "
					+ getTextLanguage(LanguageCode, "notification_service", "com.hp.ns.admin.central.link.paste") + " "
					+ getEnvironment(System.getProperty("environment"))+ CommonVariables.INVITATION_LINK +invitationCode + " "
					+ getTextLanguage(LanguageCode, "daas_ui", "adminx.signature_thanks") + " " 
					+ CommonVariables.Partner_Name + " " + "Â"
					+ getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear()));
			Assert.assertTrue(actualMailContent.contains(expectedMailContent), "Mail content is not matching");
			LOGGER.info("Mail content verified");	*/	
		}
	}
	
	/**
	* Test verification of Active user details in Partner Login 
	* @throws Exception
	*/
	@Test(priority = 7, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : C43504777")
	public final void verifyActiveCustomerUserInPartnerLogin() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("partnerPopup")) {
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("closePopup");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Close Customer Exp view popup");
			}
			dashboardPage.leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "PARTNER_EMAIL_WEP"))) {
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"),CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"),CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			//sleeper(5000);
			waitForPageLoaded();
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters")," clear filter text is  not match");
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
				
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("invitestatus"),getTextLanguage(LanguageCode, "daas_ui", "users.list.label.invite.status")," Invite text is  not match");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatuscol");
			LOGGER.info("Clicked on the User Status column");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("acceptedStatus");
			LOGGER.info("Selected Accepted status from the dropdown");
			sleeper(1000);
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("acceptedStatus",getTextLanguage(LanguageCode, "daas_ui", "users.details.status.accepted")),"Accepted status not selected");
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("userlistEmailField");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfileHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userProfileHeader"),getTextLanguage(LanguageCode, "daas_ui", "users.profile")," user profile text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfile"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagementHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userManagementHeader"),getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_management")," user management text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagement"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userRolesHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userRolesHeader"),getTextLanguage(LanguageCode, "daas_ui", "global.form.roles")," roles text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userRoles"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("deleteBtn"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("deleteBtn"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.delete")," Delete text is  not match");
			softAssert.assertAll();
			LOGGER.info("Active user verified in Partner login");
			}		
			catch(Exception e) {
				Assert.fail("Exception occured in verifyActiveCustomerUserInPartnerLogin " +e);	
			}
		}
		
		
	/**
	 * Test verification of InActive user details in Partner Login 
	 * @throws Exception
	 */
	@Test(priority = 8, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : C51280779")
	public final void verifyInactiveCustomerUserInPartnerLogin() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("partnerPopup")) {
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("closePopup");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Close Customer Exp view popup");
			}
			dashboardPage.leftSideMenuVerification();;
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "PARTNER_EMAIL_WEP"))) {
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME"),CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME"),CommonVariables.ACCOUNT);
                sleeper(3000);
		    }
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters")," clear filter text is  not match");
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("invitestatus"),getTextLanguage(LanguageCode, "daas_ui", "users.list.label.invite.status")," Invite text is  not match");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatuscol");
			LOGGER.info("Clicked on the User Status column");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("invitedStatus",getTextLanguage(LanguageCode, "daas_ui", "users.details.status.invited")),"Invited status not selected");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("invitedStatus");
			LOGGER.info("Selected Invited status from the dropdown");
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("userlistEmailField");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfileHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userProfileHeader"),getTextLanguage(LanguageCode, "daas_ui", "users.profile")," user profile text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfile"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagementHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userManagementHeader"),getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_management")," user management text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagement"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userRolesHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userRolesHeader"),getTextLanguage(LanguageCode, "daas_ui", "global.form.roles")," roles text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userRoles"));
			softAssert.assertAll();
			LOGGER.info("InActive user verified in Partner login");
		}		
		catch(Exception e) {
			Assert.fail("Exception occured in verifyInActiveCustomerUserInPartnerLogin " +e);	
		}
	}

	/**
	 * This test case is to verify Add User through Customer flow
	 * @throws Exception
	 */
	@Test(priority = 9, groups = {"REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : C43522614")
	public final void verifyAddUserThroughCustomer() throws Exception {
		String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN;
		String userID = null , tenantID = null, currentUrl;
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
 
		try {
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			leftSideMenuVerification();
			if(toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
			} else {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
			}
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page.");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("usersTab");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("InviteButton");
			waitForPageLoaded();
			sleeper(2000);
			LOGGER.info("Invite Users popup window is shown.");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("importFromAAD");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addManually");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addCSV");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("cancelButtonText");
 
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addManually");
			sleeper(1000);
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addAnotherUserBtn"), "Add user button missing.");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserCancelBtn");
 
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserBackBtn"), "Add user back button missing.");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserNextBtn"), "Add user next button missing.");
 
			for (int i = 0; i < 4; i++) {
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addAnotherUserBtn");
				LOGGER.info("Maximum 5 role we are able to add.");
			}
 
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("enterFnameErr");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("enterLnameErr");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("enterEmailErr");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("enterIDPErr");
			LOGGER.info("Validated error if not added any mandatory data field");
 
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserBackBtn");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addManually");
 
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
			WEXCustomerUserListPage.selectCustomRoleFromDropdown(CommonVariables.IT_ADMIN);
			softAssert.assertTrue(WEXCustomerUserListPage.matchValueOnWEXCustomerUserListPage("roleDropResult",CommonVariables.IT_ADMIN), "Role drop down result is incorrect");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("toastMessage");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("toastMessage");
			LOGGER.info("Customer User added successfully.");
 
			if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter")) {
				tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
				waitForPageLoaded();
			}
 
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput", UserEmail);
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("emailInputValue", UserEmail.toLowerCase()), "User email on list page is not matching");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("userNameValue", UserFirstname+" "+UserLastname), "User name on list page is not matching");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("emailInputValue");
			
            LOGGER.info("User details page opened");
			
			currentUrl = WEXCustomerUserListPage.getUrlOfCurrentPage();
			userID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1,currentUrl.indexOf("?"));
			tenantID = currentUrl.substring(currentUrl.lastIndexOf("=") + 1);
			
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("emailPanel"), "Email panel not available.");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("role"), "Role button not available.");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("inviteTab"), "Invite state not available.");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("roleValue"), "Role value mismatch.");
			waitForPageLoaded();
			if(toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
			} else {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
			}
			waitForPageLoaded();
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			softAssert.assertEquals(WEXCustomerUserListPage.verifyDeleteUser(UserEmail),getTextLanguage(LanguageCode, "daas_ui", "global.no_results")," No results text is  not match");
			LOGGER.info("Customer User Removed successfully");
			softAssert.assertAll();
		}catch(Exception e) {
			Assert.fail("Exception occurred in verifyAddUserInCustomerLogin");
	   }
	} 
 
	/**
	 * This test case is to verify Add User through Partner flow
	 * @throws Exception
	 */
	@Test(priority = 10, groups = {"REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : C43522614")
	public final void verifyAddUserThroughPartner() throws Exception {
		String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN;
		String userID = null , tenantID = null, currentUrl;
		SoftAssert softAssert = new SoftAssert();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
 
		try {
			login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
			leftSideMenuVerification();
			softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
			if(toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "PARTNER_EMAIL_WEP"))) {
				Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AccountManagementTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.account")), "Account tab text is not matching");
				WEXCustomerUserListPage.partnerView(CommonVariables.ACCOUNT);
				softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.account")), "Account tab Breadcrumb is not matching");
			} else {
				Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AccountManagementTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.accountManagement")), "Account Management tab text is not matching");
				WEXCustomerUserListPage.partnerView(CommonVariables.ACCOUNT_MANAGEMENT);
				softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.accountManagement")), "Account Management tab Breadcrumb is not matching");
			}	
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab");
 
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("InviteButton");
			waitForPageLoaded();
			sleeper(2000);
			//WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("importFromAAD");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addManually");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addCSV");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("cancelButtonText");
 
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addManually");
			sleeper(1000);
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addAnotherUserBtn"), "Add user button missing.");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserCancelBtn");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserBackBtn"), "Add user back button missing.");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserNextBtn"), "Add user next button missing.");
 
			for (int i = 0; i < 4; i++) {
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addAnotherUserBtn");
				LOGGER.info("Maximum 5 role we are able to add");
			}
 
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("enterFnameErr");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("enterLnameErr");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("enterEmailErr");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("enterIDPErr");
			LOGGER.info("Validated error if not added any mandatory data field.");
 
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserBackBtn");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addManually");
 
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
			softAssert.assertTrue(WEXCustomerUserListPage.matchValueOnWEXCustomerUserListPage("roleDropResult",CommonVariables.PARTNER_ADMIN), "Role drop down result is incorrect");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("toastMessage");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("toastMessage");
			LOGGER.info("Partner Customer User added successfully");
 
			if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter")) {
				tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
				waitForPageLoaded();
			}
 
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput", UserEmail);
			Thread.sleep(2000);
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("emailInputValue");
			waitForPageLoaded();
			currentUrl = WEXCustomerUserListPage.getUrlOfCurrentPage();
			userID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1,currentUrl.indexOf("?"));
			tenantID = currentUrl.substring(currentUrl.lastIndexOf("=") + 1);
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("emailPanel"), "Email panel not available.");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("role"), "Role button not available.");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("inviteTab"), "Invite state not available.");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("partnerRoleValue"), "Role value mismatch.");
			waitForPageLoaded();
 
			if(toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "PARTNER_EMAIL_WEP"))) {
				Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AccountManagementTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.account")), "Account tab text is not matching");
				WEXCustomerUserListPage.partnerView(CommonVariables.ACCOUNT);
				softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.account")), "Account tab Breadcrumb is not matching");
			} else {
				Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AccountManagementTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.accountManagement")), "Account Management tab text is not matching");
				WEXCustomerUserListPage.partnerView(CommonVariables.ACCOUNT_MANAGEMENT);
				softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.accountManagement")), "Account Management tab Breadcrumb is not matching");
			}
			waitForPageLoaded();
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			softAssert.assertEquals(WEXCustomerUserListPage.verifyDeleteUser(UserEmail),getTextLanguage(LanguageCode, "daas_ui", "global.no_results")," No results text is  not match");
			LOGGER.info("Partner User Removed successfully.");
			softAssert.assertAll();
		}catch(Exception e) {
			Assert.fail("Exception occurred in verifyAddUserInPartnerLogin");
	  }
	}
	
	
	@Test(priority = 11, groups = {"REGRESSION_ACCOUNTS","PRODUCTION_ACCOUNTS"}, description = "TestCase ID : C51478600")
	public final void verifyElementsinUserDetailsPage() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("ITADMIN_EMAIL_ACCOUNT","ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters")," clear filter text is  not match");
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("InviteButton"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("InviteButton"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.invite_user")," Invite user text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			softAssert.assertEquals((WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("invitestatus").split("\n")[0]),getTextLanguage(LanguageCode, "daas_ui", "users.list.label.invite.status")," Invite text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("nameText"));
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("emailText"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("emailText"),getTextLanguage(LanguageCode, "daas_ui", "global.form.email"),"email text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("roleText"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("roleText"),getTextLanguage(LanguageCode, "daas_ui", "global.roles"),"Role text is  not match");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatuscol");
			sleeper(1000);
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("invitedStatus");
			sleeper(2000);
			LOGGER.info("User status INVITED selected");
			WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("checkbox");
			sleeper(1000);
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("checkbox");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("deleteText"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("deleteText"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.remove"),"Delete text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("assignRoles"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("assignRoles"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.assign_roles"),"Assign Role text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendInvite"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendInvite"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.resend"),"Resnd invite text is  not match");
			LOGGER.info("Delete, AssignRoles, Resend Invite buttons are verified");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("deleteText");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("popuptext");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("popuptext"),getTextLanguage(LanguageCode, "daas_ui", "users.remove.title"),"Delete text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("deleteparaText");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("deleteparaText"),getTextLanguage(LanguageCode, "daas_ui", "users.remove.detail_message"),"Delete para text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendsubmit");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendsubmit"),getTextLanguage(LanguageCode, "daas_ui", "alert_management.delete"),"Delete text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendCancel");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendCancel"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"Cancel text is  not match");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendCancel");
			LOGGER.info("Delete button pop-up verified");

			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			
			WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("userstatuscol");
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("userstatuscol");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("userstatuscol");
			sleeper(1000);
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("acceptedStatus");	
			sleeper(2000);
			LOGGER.info("User status ACCEPTED selected");
			WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("checkbox");
			sleeper(1000);
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("checkbox");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("deleteText"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("deleteText"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.remove"),"Delete text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("assignRoles"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("assignRoles"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.assign_roles"),"Assign Role text is  not match");
			LOGGER.info("Delete, AssignRoles buttons are verified");
			softAssert.assertAll();
			}
		catch(Exception e) {
				Assert.fail("Exception occured in verifyelementsinuserdetailpage "+ e.getMessage());	
			}
	}
	
	@Test(priority = 12, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : C51478603")
	public final void verifyAssignRole() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("ITADMIN_EMAIL_ACCOUNT","ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters")," clear filter text is  not match");
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("InviteButton"));
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("roleText"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("roleText"),getTextLanguage(LanguageCode, "daas_ui", "global.roles")," Roles text is  not match");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("roleColumn");
			sleeper(1000);
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("roleinput");
			sleeper(1000);
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("roleinput", connectoradmin);
			sleeper(4000);
			LOGGER.info("Connector Admin role is selected");
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("checkbox");
			waitForPageLoaded();
			LOGGER.info("User is selected from the user list page");

			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("assignRoles"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("assignRoles"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.assign_roles"),"Assign  Roles text is  not match");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("assignRoles");
			waitForPageLoaded();
			sleeper(2000);
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("assignRolesText"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("assignRolesText"),getTextLanguage(LanguageCode, "daas_ui", "workflow_users.assign_roles.title"),"Assign  Roles2 text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("assignbutton"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("assignbutton"),getTextLanguage(LanguageCode, "daas_ui", "global.assign"),"Assign button text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("rolesCancelButton"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("rolesCancelButton"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"cancel button text is  not match");
			LOGGER.info("Assign role pop-up is shown");
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("roleSearch", itadmin);
			sleeper(1000);
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("checkrole");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("assignbutton");
			sleeper(5000);
			waitForPageLoaded();
			WEXCustomerUserDetailsPage.waitForElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole"),"Toast message not present");
			softAssert.assertAll();
			LOGGER.info("New Role is assigned to the user");
		}
		catch(Exception e) {
			Assert.fail("Exception occured inAssign Roles inuserdetailpage " +e.getMessage());	
		}
	}
	
	@Test(priority = 13, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : C51478601")
	public final void verifyEditRole() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("ITADMIN_EMAIL_ACCOUNT","ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.waitUntillElementIsPresentonWEXCustomerUserListPage("usersTab");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters")," clear filter text is  not match");
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("roleText"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("roleText"),getTextLanguage(LanguageCode, "daas_ui", "global.roles")," Roles text is  not match");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("roleColumn");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("roleinput");
			
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("roleinput", connectoradmin);
			sleeper(3000);
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("ITRole");
			LOGGER.info("IT Admin role is selected");
			WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("emailInputValue");
			sleeper(1000);
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("emailInputValue");
			waitForPageLoaded();
			LOGGER.info("User details page is shown");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userRolesHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userRolesHeader"),getTextLanguage(LanguageCode, "daas_ui", "global.form.roles")," roles text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userRoles"));
			WEXCustomerUserDetailsPage.clickOnElementsOfWEXCustomerUserDetailsPage("roleEdit");
			sleeper(2000);
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("assignRolesText"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("assignRolesText"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.assign_roles"),"Assign roles text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("assignbutton"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("assignbutton"),getTextLanguage(LanguageCode, "daas_ui", "global.assign"),"Assign button  text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("assigncancelbutton"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("assigncancelbutton"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"cancel button  text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("roleSearch"));
			LOGGER.info("Assign Role page is shown");
			WEXCustomerUserDetailsPage.enterTextOfWEXCustomerUserDetailsPage("roleSearch", lostadmin);
			sleeper(1000);
			WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("checkbox");
			sleeper(2000);
			WEXCustomerUserDetailsPage.mousehoverOnWEXCustomerUserDetailsPage("assignbutton");
			sleeper(1000);
			WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("assignbutton");
			sleeper(2000);
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("toastMessageonUserRole"),CommonVariables.MODIFY_USERS_ROLE);
			LOGGER.info("New Role added to the user");
			softAssert.assertAll();
			LOGGER.info("Edit role functionality verified successfully");
						
		}
		catch(Exception e) {
				Assert.fail("Exception occured in Edit rolesuserdetailpage " + e.getMessage());	

		}
	}
	
	/**
	 * This test case is to verify Add Customer User page in Customer Login
	 * https://hp-jira.external.hp.com/browse/WEXALLI-59
	 * @throws Exception
	 */
	@Test(priority = 14, groups = {"REGRESSION_ACCOUNTS","PRODUCTION_ACCOUNTS"}, description = "TestCase ID : C43504777")
	public final void verifyAddUserManuallypage() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Redirected to Users tab page");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("InviteButton");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("InviteButton"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.invite_user")," Invite user text is  not match");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("InviteButton");
			LOGGER.info("Invite user page screen shown");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserTitle");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addUserTitle"),getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.title"),"Add user text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addManualText");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addManualText"),getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.first.add.manually"),"Manual Add text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("upLoadCSVText");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("upLoadCSVText"),getTextLanguage(LanguageCode, "daas_ui", "global.uploadcsv"),"Upload csv text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("cancelButtonText");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("cancelButtonText"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"cancel text is  not match");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addManualText");
			
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("InviteTitle", getTextLanguage(LanguageCode, "daas_ui", "invite.users.list.company_selection.title")), "Invite users Title name is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("userInformationText", getTextLanguage(LanguageCode, "daas_ui", "users.add_manually.text")), "User Information text is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("stepInformation", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step1")), "Step 1 of 2 text is incorrect");
	//		softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("subHeadingAddUser", getTextLanguage(LanguageCode, "daas_ui", "users.invite_manually.sub_text")), "Sub Heading text is incorrect");
			
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addAnotherUserBtn"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addAnotherUserBtn"),getTextLanguage(LanguageCode, "daas_ui", "users.invite.invite_another"),"Another user text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserCancelBtn"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addUserCancelBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"cancel text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserBackBtn"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addUserBackBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.back_button"),"Back button text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserNextBtn"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addUserNextBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.next"),"Next text is  not match");
			softAssert.assertAll();
			LOGGER.info("Invite user manually page verified");
			
		}catch(Exception e) {
			Assert.fail("Exception occured in verifyAddUserManuallypage " + e.getMessage());
		}
	}	
	
	@Test(priority = 15, groups = {"REGRESSION_ACCOUNTS","PRODUCTION_ACCOUNTS"}, description = "TestCase ID : C51280785")
	public final void verifyAddUserthroughCSVpage() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Redirected to Users tab page");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("InviteButton");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("InviteButton"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.invite_user")," Invite user text is  not match");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("InviteButton");
			
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserTitle");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addUserTitle"),getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.title"),"Add user text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addManualText");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addManualText"),getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.first.add.manually"),"Manual Add text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("upLoadCSVText");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("upLoadCSVText"),getTextLanguage(LanguageCode, "daas_ui", "global.uploadcsv"),"Upload csv text is  not match");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("cancelButtonText");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("cancelButtonText"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"cancel text is  not match");
			LOGGER.info("Invite user page screen shown");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("upLoadCSVText");
			LOGGER.info("Add users through Upload CSV screen shown");

			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("addUsers", getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.title")), "Add Users Title name is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("uploadCSV", getTextLanguage(LanguageCode, "daas_ui", "ee_pulses_csv_upload_label")), "Upload CSV test is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserBackBtn"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addUserBackBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.back_button"),"back button  text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("downloadBtn"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("downloadBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.download"),"Downlaod button  text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserCancelBtn"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addUserCancelBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"cancel button  text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("uploadFile"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("uploadFile"),getTextLanguage(LanguageCode, "daas_ui", "assets.add.upload_file.label"),"Upload file  text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("importBtn"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("importBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.import"),"Import button  text is  not match");
			softAssert.assertAll();
			LOGGER.info("Upload CSV user page verified");
			
		}catch(Exception e) {
			Assert.fail("Exception occured in verifyAddUserthroughCSVpage " + e.getMessage());
		}
	}	
	
	@Test(priority = 16, groups = {"REGRESSION_ACCOUNTS","PRODUCTION_ACCOUNTS"}, description = "TestCase ID : C51280787", enabled=false)
	public final void verifyAddUserthroughAADimportpage() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Redirected to Users tab page");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("InviteButton");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("InviteButton"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.invite_user")," Invite button text is  not match");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("InviteButton");
			LOGGER.info("Invite user page screen shown");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserTitle"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addUserTitle"),getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.title")," Add user text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addManualText"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addManualText"),getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.first.add.manually")," Add manual  text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addAADText"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addAADText"),getTextLanguage(LanguageCode, "daas_ui", "global.importfromAAD")," AAD importtext is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("cancelButtonText"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("cancelButtonText"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel")," cancel button text is  not match");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addAADText");
			LOGGER.info("Add users through AAD import screen shown");

			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("addUsers", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.workflow.users.add")), "Add Users Title name is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("selectTyp"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("selectTyp"),getTextLanguage(LanguageCode, "daas_ui", "wex.importAAD.step2.subtitle")," select type text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("users"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("users"),getTextLanguage(LanguageCode, "daas_ui", "assets.import.users.title"),"users text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("groups"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("groups"),getTextLanguage(LanguageCode, "daas_ui", "wex.importAAD.step2.content.groupTab.title"),"group text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserCancelBtn"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addUserCancelBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel")," cancel button text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserBackBtn"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("addUserBackBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.back_button"),"Back button text is  not match");
			softAssert.assertAll();
			LOGGER.info("AAD import user page verified");
			
		}catch(Exception e) {
			Assert.fail("Exception occured in verifyAddUserthroughAADimportpage " + e.getMessage());
		}
	}	
	
	@Test(priority = 17, groups = {"REGRESSION_ACCOUNTS", "PRODUCTION_ACCOUNTS"}, description = "TestCase ID : C51280778")
	public final void verifyUserOverviewPage() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("overviewTab");
			LOGGER.info("Clicked on the Overview tab");
			
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("compProfileheader"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("compProfileheader"),getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.company_profile_title"),"Company profile text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("customId"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("customId"),getTextLanguage(LanguageCode, "daas_ui", "create_company.send.invitation.label"),"Customer ID text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("compNm"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("compNm"),getTextLanguage(LanguageCode, "daas_ui", "global.form.company_name")," Company name text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("compSz"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("compSz"),getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.number_of_employees"),"Company size text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("createdOn"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("createdOn"),getTextLanguage(LanguageCode, "daas_ui", "group.created_on"),"Created on text is  not match");
			LOGGER.info("Company profile section verified");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("primAdmin"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("primAdmin"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.primary_account_admin"),"Primary Admini text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("adminNm"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("adminNm"),getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.name"),"Admin name text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("mailId"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("mailId"),getTextLanguage(LanguageCode, "daas_ui", "global.email"),"Email text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("phoneNo"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("phoneNo"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.phone_number"),"Phone number text is  not match");
			LOGGER.info("Primary administrator section verified");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("compAddre"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("compAddre"),getTextLanguage(LanguageCode, "daas_ui", "company.details.company_address"),"Company address text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("countryNm"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("countryNm"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.country"),"Country text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("stateNm"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("stateNm"),getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.state"),"State text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("cityNm"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("cityNm"),getTextLanguage(LanguageCode, "daas_ui", "global.city"),"City text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("zipCode"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("zipCode"),getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.zip-code"),"Zip code text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("address1"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("address1"),getTextLanguage(LanguageCode, "daas_ui", "global.address1"),"Adress line1 text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("address2"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("address2"),getTextLanguage(LanguageCode, "daas_ui", "global.address2"),"Address line2 text is  not match");
			LOGGER.info("Company Address section verified");		
			softAssert.assertAll();
			LOGGER.info("User Overview Page verified successfully");
			}
			catch(Exception e) {
				Assert.fail("Exception occured in verifyUserOverviewPage " + e.getMessage());	
			}
		
	}	

	@Test(priority = 18, groups = {"REGRESSION_ACCOUNTS", "PRODUCTION_ACCOUNTS"}, description = "TestCase ID : C51280778")
	public final void verifyUserProfilePage() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("profile");
			sleeper(3000);
			LOGGER.info("Clicked on the Profile button");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userProfileOption");
			waitForPageLoaded();
			LOGGER.info("Clicked on the User Profile option");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("profileTitle"));
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("compProfile"));
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("userProfile"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("userProfile"),getTextLanguage(LanguageCode, "daas_ui", "users.profile"),"User profile text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("namePanel"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("namePanel"),getTextLanguage(LanguageCode, "daas_ui", "users.details.name"),"Name  text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("mobilePanel"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("mobilePanel"),getTextLanguage(LanguageCode, "daas_ui", "users.details.mobile_phone"),"mobile text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("languagePanel"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("languagePanel"),getTextLanguage(LanguageCode, "daas_ui", "users.details.language"),"langauge text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("TimezonePanel"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("TimezonePanel"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_timezone"),"Timezone text text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("emailPanel"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("emailPanel"),getTextLanguage(LanguageCode, "daas_ui", "global.form.email_address"),"Email text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("officePanel"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("officePanel"),getTextLanguage(LanguageCode, "daas_ui", "users.details.office_phone"),"office  text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("title"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("title"),getTextLanguage(LanguageCode, "daas_ui", "users.list.label.title"),"Title text is  not match");
			LOGGER.info("User Profile section validated");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("userManagementHeader"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("userManagementHeader"),getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_management")," user management text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("idp"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("idp"),getTextLanguage(LanguageCode, "daas_ui", "global.idp.label"),"IDP text is  not match");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("passwordPanel"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("passwordPanel"),getTextLanguage(LanguageCode, "daas_ui", "global.password"),"Password text is  not match");
			LOGGER.info("User Management section validated");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("userRolesHeader"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("userRolesHeader"),getTextLanguage(LanguageCode, "daas_ui", "global.form.roles"),"Roles header text is  not match");
			LOGGER.info("Roles section validated");
			softAssert.assertAll();
			LOGGER.info("User Overview Page verified successfully");
			}
			catch(Exception e) {
				Assert.fail("Exception occured in verifyUserProfilePage " + e.getMessage());	
			}
	}
	
	@Test(priority = 19, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : C51280777")
	public final void verifyIDPTypeOfUser() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			
		    if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Clicked on the User tab");
			
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters")," clear filter text is  not match");
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("invitestatus"),getTextLanguage(LanguageCode, "daas_ui", "users.list.label.invite.status")," Invite text is  not match");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatusdropResult");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("acceptedStatus",getTextLanguage(LanguageCode, "daas_ui", "users.details.status.accepted")));
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("acceptedStatus");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")){	
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("noItemsAvailable"),getTextLanguage(LanguageCode, "daas_ui", "global.no_results"),"No results text is  not match");
				LOGGER.info("Data is not available");
			}
			else {
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("userlistEmailField");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagementHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userManagementHeader"),getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_management")," user management text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagement"));
			sleeper(5000);	//IDP API value takes time to load
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("idpValue"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("idpValue"),getTextLanguage(LanguageCode, "daas_ui", "global.HPID_label")," HPID text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.checkIDPTypeIsPresent("idpValue"));
			LOGGER.info("IDP value verified for Accepted user");
			}
			
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("clearFilter"),getTextLanguage(LanguageCode, "daas_ui", "global.clear.filters")," clear filter text is  not match");
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatusdropResult");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("invitedStatus",getTextLanguage(LanguageCode, "daas_ui", "users.details.status.invited")));
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("invitedStatus");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")){
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("noItemsAvailable"),getTextLanguage(LanguageCode, "daas_ui", "global.no_results"),"No results text is  not match");
				LOGGER.info("Data is not available");
			}
			else {
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("userlistEmailField");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagementHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userManagementHeader"),getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_management")," user management text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userManagement"));
			sleeper(5000);	//IDP API value takes time to load
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("idpValue"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.checkIDPTypeIsPresent("idpValue"));
			LOGGER.info("IDP value verified for Invited user");
			}
			softAssert.assertAll();
			LOGGER.info("IDPType of User verified successfully");
			}		
			catch(Exception e) {
				Assert.fail("Exception occured in verifyIDPTypeOfUser" + e.getMessage());	
			}
	}


	@Test(priority = 20, groups = {"REGRESSION_ACCOUNTS", "PRODUCTION_ACCOUNTS"}, description = "TestCase ID : C51280778")
	public final void verifyIDPTypeOnUserProfilePage() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
					
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("profile");
			sleeper(3000);
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userProfileOption");
			waitForPageLoaded();
			LOGGER.info("Clicked on the User Profile option");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfilePageManagementHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userProfilePageManagementHeader"),getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_management")," user management text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfilePageManagement"));
			sleeper(5000);	//IDP API value takes time to load
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("idpSection"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("passwordSection"));
			sleeper(7000); //IDP API value takes time to load
			WEXCustomerUserDetailsPage.waitForElementsOfWEXCustomerUserDetailsPage("idpValue");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("idpValue"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("idpValue"),getTextLanguage(LanguageCode, "daas_ui", "global.HPID_label")," HPID text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.checkIDPTypeIsPresent("idpValue"));
					
			softAssert.assertAll();
			LOGGER.info("User Overview Page IDPType verified successfully");
		}
		catch(Exception e) {
			Assert.fail("Exception occured in verifyIDPTypeOnUserProfilePage " + e.getMessage());	
		}
	}
	
	@Test(priority = 21, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : C51280778")
	public final void verifyIDPTypeOnPartnerProfilePage() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("partnerPopup")) {
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("closePopup");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Close Customer Exp view popup");
			}
					
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("profile");
			sleeper(3000);
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userProfileOption");
			waitForPageLoaded();
			LOGGER.info("Clicked on the User Profile option");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfilePageManagementHeader"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("userProfilePageManagementHeader"),getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_management")," user management text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("userProfilePageManagement"));
			sleeper(5000);	//IDP API value takes time to load
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("idpSection"));
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("passwordSection"));
			sleeper(7000);
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("idpValue"));
			softAssert.assertEquals(WEXCustomerUserDetailsPage.getTextOfWEXCustomerUserDetailsPage("idpValue"),getTextLanguage(LanguageCode, "daas_ui", "global.HPID_label")," HPID text is  not match");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.checkIDPTypeIsPresent("idpValue"));
					
			softAssert.assertAll();
			LOGGER.info("Partner Overview Page IDPType verified successfully");
		}
		catch(Exception e) {
			Assert.fail("Exception occured in verifyIDPTypeOnPartnerProfilePage " + e.getMessage());	
		}
	}
	
	@Test(priority = 22, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifySwitchAccount() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("SWITCH_EMAIL_WEP", "SWITCH_PASSWORD_WEP");
            waitForPageLoaded();
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("profile");
			sleeper(3000);
            if(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("switchUserName")) {
                String switchUserFirst = WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("switchUserName");
                WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("switchAccount");
                sleeper(2000);
                waitForPageLoaded();
                LOGGER.info("Clicked on the Switch Account option");
                softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("switchTitle"));
                softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("switchButton"));
                softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("cancelSwitch"));
                softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("switchSelectdropDown"));
                softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("currentUserName"));
                sleeper(1000);
                WEXCustomerUserListPage.switchUser("listOfSwitchUser", "switchSelectdropDown");
                WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("switchButton");
                WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("profile");
                sleeper(6000);
                WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("profile");
                sleeper(5000);
                WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("switchUserName");
                String switchUserSecond = WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("switchUserName");
                sleeper(3000);
                softAssert.assertFalse(switchUserFirst.equals(switchUserSecond), "Switch does not worked");
                sleeper(3000);
                LOGGER.info("\nUser First:" + switchUserFirst + "\nUser Second:" + switchUserSecond);
                softAssert.assertAll();
                LOGGER.info("Switch User functionality verified successfully");
            }
		}
		catch(Exception e) {
			Assert.fail("Exception occured in verifySwitchAccount " + e.getMessage());	
		}
	}
	

	@Test(priority = 23, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : C43504777")
	public final void verifyinviteuserthroughcustomroleInCustomerLogin() throws Exception {
		String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN, subjectInvite, inviteEmailContent, loginLink;
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
        WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");

			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("InviteButton");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("InviteButton"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.invite_user")," Invite user text is  not match");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("InviteButton");
			sleeper(2000);
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addManualText");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("InviteTitle", getTextLanguage(LanguageCode, "daas_ui", "invite.users.list.company_selection.title")), "Invite users Title name is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("userInformationText", getTextLanguage(LanguageCode, "daas_ui", "users.add_manually.text")), "User Information text is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("stepInformation", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step1")), "Step 1 of 2 text is incorrect");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("subHeadingAddUser", getTextLanguage(LanguageCode, "daas_ui", "admin.invite_manually.sub_text")), "Sub Heading text is incorrect");
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
			sleeper(3000);
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("stepInformation", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step2")), "Step 2 of 2 text is incorrect");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("userRoleDropDown");
			sleeper(1000);
			WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("roleClearButton");
			sleeper(1000);
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userRoleDropDown");
			sleeper(1000);
			WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("selecRole");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("selecRole");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("toastMessage");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("toastMessage");
			LOGGER.info("Customer User added successfully");
			waitForPageLoaded();

			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}

			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput", UserEmail);
			LOGGER.info("Email of the User entered");
			sleeper(2000);
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("emailInputValue", UserEmail.toLowerCase()), "User email on list page is not matching");
            WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("roleListValue");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("userNameValue", UserFirstname+" "+UserLastname), "User name on list page is not matching");
			LOGGER.info("Customer User Email with customrole: "+ UserEmail);

			//Accepting the invite
            subjectInvite = "wex A invites you to the HP Workforce Experience Platform (WXP)!";
            LOGGER.debug(subjectInvite);
            inviteEmailContent = WEXCustomerUserListPage.getActualMailContent(UserEmail, subjectInvite);
            if(Objects.equals(inviteEmailContent, "")) {
                LOGGER.info("Email not received in first attempt, clicking checkbox and resending invitation");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("Firstcheckbox");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("resendinvitation");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("resendbutton");
                sleeper(3000); // Wait for resend action to complete

                // Try to get the email content again after resending
                inviteEmailContent = WEXCustomerUserListPage.getActualMailContent(UserEmail, subjectInvite);
                if(Objects.equals(inviteEmailContent, "")) {
                    LOGGER.error("Email not received even after resending invitation");
                    // You might want to throw an exception or handle this case as per your test requirements
                }
            }
			loginLink = WEXCustomerUserListPage.extractLink(inviteEmailContent);
			LOGGER.info("Invitation email link is: {}", loginLink);
			logout();
			sleeper(2000);
			getDriver().navigate().to(loginLink);
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.onBoardInvitedUser(UserEmail),"User accept failed");
			LOGGER.info("Customer User Invite Accepted successfully");
			logout();
			sleeper(2000);
			
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			sleeper(2000);
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");		
			sleeper(2000);
			
			softAssert.assertEquals(WEXCustomerUserListPage.verifyDeleteUser(UserEmail),getTextLanguage(LanguageCode, "daas_ui", "global.no_results")," No results text is  not match");
			LOGGER.info("Customer User Removed successfully"); 
		 	softAssert.assertAll();	
		 	
		}catch(Exception e) {
			Assert.fail("Exception occured in verifyinviteuserthroughcustomroleInCustomerLogin"+ e.getMessage());
		}
	}
	
	@Test(priority = 24, groups = {"REGRESSION_ACCOUNTS",}, description = "TestCase ID : C51280778")
	public final void verifyAssignedpartnerPage() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("assignedPartner");
			LOGGER.info("Clicked on the assigned tab");
			
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("assignmentHeader"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("assignmentHeader"),getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings"),"Assignemnt text is  not match");
            if(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitationsHeader")) {
                softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitationsHeader"));
                softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("invitationsHeader"), getTextLanguage(LanguageCode, "daas_ui", "partner.pending.invitations"), "Invitation text is  not match");
                softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("deleteIcon"));
            }
		}catch(Exception e) {
			Assert.fail("Exception occured in verifyAssignedpartnerPage"+ e.getMessage());
		}
	}
	
	@Test(priority = 25, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : C51280785")
	public final void verifyCSVUserUpload() throws Exception {
		
		String subjectInvite, inviteEmailContent, loginLink;
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
        WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
			leftSideMenuVerification();

			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_ACCOUNT"))) {
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    }
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");

			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
			LOGGER.info("Redirected to Users tab page");

			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}

			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatusdropResult");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("invitedStatus");
			waitForPageLoaded();
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("emailInput");
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput",WEPCustomerVariables.USER_NAME);
			sleeper(2000);
			if (!WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")) {
				softAssert.assertTrue(WEXCustomerUserListPage.verifyDeleteOnCSVuploadedUsers(),"User delete failed");
			}

			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatusdropResult");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("acceptedStatus");
			waitForPageLoaded();
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("emailInput");
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput",WEPCustomerVariables.USER_NAME);
			sleeper(2000);
			if (!WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")) {
				softAssert.assertTrue(WEXCustomerUserListPage.verifyDeleteOnCSVuploadedUsers(),"User delete failed");
			}

			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("InviteButton");
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("InviteButton"),getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.invite_user")," Invite user text is  not match");
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("InviteButton");
			WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("upLoadCSVText");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("upLoadCSVText");
			LOGGER.info("Add users through Upload CSV screen shown");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("uploadFileBtn"),"Upload File button not present");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("uploadFileBtn");
			WEXCustomerUserListPage.uploadCSVOnWEXUserPage();
			sleeper(2000);
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("importBtn");
			sleeper(1000);
			WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("CSVUploadToastMsg");
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("CSVUploadToastMsg"),"Toast message not shown for CSV upload");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("clearFilter")) {
				WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("clearFilter");
				WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			sleeper(10000); // User upload takes time
			refreshPage();

			softAssert.assertTrue(WEXCustomerUserListPage.verifyCSVuploadOnUserlistPage(),"User upload failed");
			LOGGER.info("CSV users are imported successfully");

			WEXCustomerUserListPage.scrollOnWEXCustomerUserListPage("emailInput");
			WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput",WEPCustomerVariables.USER_NAME);
			sleeper(2000);
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatusdropResult");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("invitedStatus",getTextLanguage(LanguageCode, "daas_ui", "users.details.status.invited")));
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("invitedStatus");
			sleeper(2000);
			ArrayList<String> userList = WEXCustomerUserListPage.getTextOfListOfElementsWEXUsersListPage("csvInvitedUsers");
			LOGGER.info(userList);

			//Accepting the invite
			subjectInvite = "wex A invites you to the HP Workforce Experience Platform (WXP)!";
    		LOGGER.debug(subjectInvite);
            inviteEmailContent = WEXCustomerUserListPage.getActualMailContent(userList.get(0), subjectInvite);
            if(Objects.equals(inviteEmailContent, "")) {
                LOGGER.info("Email not received in first attempt, clicking checkbox and resending invitation");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("Firstcheckbox");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("resendinvitation");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("resendbutton");
                sleeper(3000); // Wait for resend action to complete

                // Try to get the email content again after resending
                inviteEmailContent = WEXCustomerUserListPage.getActualMailContent(userList.get(0), subjectInvite);
                if(Objects.equals(inviteEmailContent, "")) {
                    LOGGER.error("Email not received even after resending invitation");
                    // You might want to throw an exception or handle this case as per your test requirements
                }
            }
            loginLink = WEXCustomerUserListPage.extractLink(inviteEmailContent);
            LOGGER.info("Invitation email link is: {}", loginLink);
            logout();
            sleeper(2000);
    		getDriver().navigate().to(loginLink);
    		waitForPageLoaded();
            WEXCustomerUserListPage.onBoardInvitedUser(userList.get(0));
    	
			softAssert.assertAll();
			LOGGER.info("Upload CSV user flow verified");
			
		}catch(Exception e) {
			Assert.fail("Exception occured in verifyCSVUserUpload " + e.getMessage());
		}
	}	
	
}
