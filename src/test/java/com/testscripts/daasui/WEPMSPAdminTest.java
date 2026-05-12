package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ProductCatalogVariables;
import com.daasui.constants.ScriptVariables;
import com.daasui.constants.WEPPulsesCreationPageVariables;
import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class WEPMSPAdminTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(WEPMSPAdminTest.class);
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");

	/**
	 * This method will verify the partner login
	 */
	@Test(priority = 1, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyMSPLogin() throws Exception {
		login("MSP_ADMIN_EMAIL_WEP", "MSP_ADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		softAssert.assertAll();
		LOGGER.info("MSP login verified successfully");
	}

	/************************************* Account Management *********************************************/

	/**
	 * This method will verify the update msp name functionality from Account Management Overview tab.
	 */
	@Test(priority = 2, groups = {"REGRESSION_ACCOUNTS" }, description = "TestCase ID: ")
	public final void verifyUpdateOfMSPName() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_EMAIL_WEP", "MSP_ADMIN_PASSWORD_WEP");
		String partnerName = "WEXMSP-" + generateRandomNumber(0, 1000);
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		WEXPartnerDashboardPage.partnerView(CommonVariables.ACCOUNT);
		LOGGER.info("Redirected to AccountManagement Overview page");

		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("overview", getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.overview_title")), "Overview is incorrect");

		//Test Case 1 - This test case validates msp(company) name edit functionality
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("partnerNameIdentity"), "partnerNameIdentity is not present");
		String oldPartnerName = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("partnerNameIdentity");
		LOGGER.info("Old Partner Name is : " + oldPartnerName);
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("companyProfileHeader", getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.company_profile_title")), "Company profile title is incorrect");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("PartnerID", getTextLanguage(LanguageCode, "daas_ui", "partner.id")), "Partner ID is incorrect");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomerReferenceID", getTextLanguage(LanguageCode, "daas_ui", "partners.list.authorization.crs_id")), "CustomerReferenceID is incorrect");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CompanyName", getTextLanguage(LanguageCode, "daas_ui", "global.form.company_name")), "CompanyName is incorrect");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("companycreatedon"), "companycreatedon is not present");
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("companyProfileEdit");
		LOGGER.info("Clicked on edit Company name button");
		sleeper(2000);
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CompanyNameOnPopUp", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.name")), "CompanyNameOnPopUp is incorrect");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CompanyNameLabelOnPopUP", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.name")), "CompanyNameLabelOnPopUP is incorrect");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("CompanyNameTextBox", partnerName);
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("SubmitButton"), "Submit button is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("cancelName"), "Cancel button is not present");
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("cancelName");
		LOGGER.info("Clicked on cancel button");
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("companyProfileEdit");
		LOGGER.info("Clicked on edit name button");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("CompanyNameTextBox", partnerName);
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("SubmitButton");
		LOGGER.info("Clicked on Submit button");
		WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("toastNotification");
		String newPartnerName = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("partnerNameIdentity");
		LOGGER.info("New Partner Name is : " + newPartnerName);
		softAssert.assertFalse(oldPartnerName.equalsIgnoreCase(newPartnerName), "Partner name is not updated");
		softAssert.assertAll();
		LOGGER.info("Validation for Company Name section is completed");
	}

	/**
	 * This test case is to verify Add/Edit/Delete Customer User in Partner Login
	 * https://hp-jira.external.hp.com/browse/WEXALLI-59
	 * @throws Exception
	 */
	@Test(priority = 3, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyAddEditDeleteCustomerUserInMSPLogin() throws Exception {
		String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN;
		String inviteEmailContent, loginLink;
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();

		try {
			login("MSP_ADMIN_EMAIL_WEP", "MSP_ADMIN_PASSWORD_WEP");
			dashboardPage.leftSideMenuVerification();
			
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "MSP_ADMIN_EMAIL_WEP"))) {
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "MSP_USER_NAME"),CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "MSP_USER_NAME"),CommonVariables.ACCOUNT);
		    }	  		
			waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
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
			sleeper(2000);
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("stepInformation", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step2")), "Step 2 of 2 text is incorrect");
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
			WEXCustomerUserDetailsPage.clickOnElementsOfWEXCustomerUserDetailsPage("nameEdit");
			waitForPageLoaded();
			String userfirstnameupdate = UserFirstname+generateRandomString(3);
			WEXCustomerUserDetailsPage.enterTextOfWEXCustomerUserDetailsPage("editFirstName", userfirstnameupdate);
			sleeper(1000);
			String usernameupdate = UserLastname+generateRandomString(3);
			WEXCustomerUserDetailsPage.enterTextOfWEXCustomerUserDetailsPage("editLastName", usernameupdate);
			WEXCustomerUserDetailsPage.clickOnElementsOfWEXCustomerUserDetailsPage("saveButtonUserName");

			WEXCustomerUserDetailsPage.waitForElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("toastMessageonUserRole");
			LOGGER.info("Customer User name updated successfully");
			softAssert.assertTrue(WEXCustomerUserDetailsPage.matchTextOnWEXCustomerUserDetailsPage("nameUpdatedValue", userfirstnameupdate + " " +usernameupdate), "User name is not updated");
			LOGGER.info("Customer User name validated on User details page successfully");
			
			WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		    waitForPageLoaded();
			LOGGER.info("Redirected Account management-Overview tab page");
			
			//Accept the invite
			LOGGER.info("Customer user email in Partner login: "+ UserEmail);
			inviteEmailContent = WEXCustomerUserListPage.getActualMailContent(UserEmail, "You have been invited to the Workforce Experience Platform (WXP)!");
			loginLink = WEXCustomerUserListPage.extractLink(inviteEmailContent);
            if(Objects.equals(inviteEmailContent, "")) {
                LOGGER.info("Email not received in first attempt, clicking checkbox and resending invitation");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("Firstcheckbox");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("resendinvitation");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("resendbutton");
                sleeper(3000); // Wait for resend action to complete

                // Try to get the email content again after resending
                inviteEmailContent = WEXCustomerUserListPage.getActualMailContent(UserEmail, inviteEmailContent);
                if(Objects.equals(inviteEmailContent, "")) {
                    LOGGER.error("Email not received even after resending invitation");
                    // You might want to throw an exception or handle this case as per your test requirements
                }
            }
			LOGGER.info("Invitation email link is: {}", loginLink);
			logout();
			sleeper(2000);
			getDriver().navigate().to(loginLink);
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.onBoardInvitedUser(UserEmail),"User accept failed");
			LOGGER.info("Customer User Invite Accepted successfully");
			logout();
			sleeper(2000);
			
			login("MSP_ADMIN_EMAIL_WEP", "MSP_ADMIN_PASSWORD_WEP");
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
			Assert.fail("Exception occured in verifyAddEditDeleteCustomerUserInMSPLogin " +e);
		}
	}
	
	/**
	 * Test verification of Resend Invite for Inactive user in Partner Login
	 * 
	 * @throws Exception
	 */
	@Test(priority = 4, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyResendInviteInMSPLogin() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();

		try {
			login("MSP_ADMIN_EMAIL_WEP", "MSP_ADMIN_PASSWORD_WEP");
			
			dashboardPage.leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "MSP_ADMIN_EMAIL_WEP"))) {
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "MSP_USER_NAME"),CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "MSP_USER_NAME"),CommonVariables.ACCOUNT);
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
			WEXCustomerUserListPage.scrollOnWEXCustomerUserListPage("invitestatus");			
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("invitestatus"),getTextLanguage(LanguageCode, "daas_ui", "users.list.label.invite.status")," Invite text is  not match");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatuscol");
			LOGGER.info("Clicked on the User Status column");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("invitedStatus");
			LOGGER.info("Selected Invited status from the dropdown");
			sleeper(1000);
            if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")){
                softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("noItemsAvailable"),getTextLanguage(LanguageCode, "daas_ui", "global.no_results")," No results text is  not match");
                LOGGER.info("Data is not available");
            }else {
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
                softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("popuptext"), getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.resend"), " Resend Invite text is  not match");
                softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendpopup"));
                softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("resendCancel"));
                LOGGER.info("Resend popup window is displayed");
                softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendsubmit").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.resend")), "Resend button not present");
                WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("resendsubmit");
                sleeper(2000);
                WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("resendMessage");
                softAssert.assertTrue(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("resendMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.resend.success")), "Error occured while sending invite");
                softAssert.assertAll();
                LOGGER.info("User is able to send resend invite in MSP login");
            }
			}		
			catch(Exception e) {
				Assert.fail("Exception occured in verifyResendInviteInMSPLogin " +e);	
			}
	}
	
	/**
	* Test verification of Active user details in MSP Login 
	* @throws Exception
	*/
	@Test(priority = 5, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyActiveCustomerUserInMSPLogin() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("MSP_ADMIN_EMAIL_WEP", "MSP_ADMIN_PASSWORD_WEP");
			
			dashboardPage.leftSideMenuVerification();
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "MSP_ADMIN_EMAIL_WEP"))) {
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "MSP_USER_NAME"),CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "MSP_USER_NAME"),CommonVariables.ACCOUNT);
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
			WEXCustomerUserListPage.scrollOnWEXCustomerUserListPage("invitestatus");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("invitestatus"),getTextLanguage(LanguageCode, "daas_ui", "users.list.label.invite.status")," Invite text is  not match");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatuscol");
			LOGGER.info("Clicked on the User Status column");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("acceptedStatus");
			LOGGER.info("Selected Accepted status from the dropdown");
			sleeper(2000);
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("acceptedStatus",getTextLanguage(LanguageCode, "daas_ui", "users.details.status.accepted")),"Accepted status not selected");
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")){
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("noItemsAvailable"),getTextLanguage(LanguageCode, "daas_ui", "global.no_results")," No results text is  not match");
				LOGGER.info("Data is not available");
			}else {
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
			softAssert.assertTrue(WEXCustomerUserDetailsPage.verifyElementsOfWEXCustomerUserDetailsPage("editrole"));
			}
			softAssert.assertAll();
			LOGGER.info("Active user verified in Partner login");
			}		
			catch(Exception e) {
				Assert.fail("Exception occured in verifyActiveCustomerUserInMSPLogin " +e);	
			}
		}
	
	/**
	 * Test verification of InActive user details in MSP Login 
	 * @throws Exception
	 */
	@Test(priority = 6, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyInvitedCustomerUserInMSPLogin() throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			login("MSP_ADMIN_EMAIL_WEP", "MSP_ADMIN_PASSWORD_WEP");
			dashboardPage.leftSideMenuVerification();;
			if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "MSP_ADMIN_EMAIL_WEP"))) {
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "MSP_USER_NAME"),CommonVariables.ACCOUNT_MANAGEMENT);
		    }else{
				dashboardPage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "MSP_USER_NAME"),CommonVariables.ACCOUNT);
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
			WEXCustomerUserListPage.scrollOnWEXCustomerUserListPage("invitestatus");
			waitForPageLoaded();
			softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("invitestatus"));
			softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("invitestatus"),getTextLanguage(LanguageCode, "daas_ui", "users.list.label.invite.status")," Invite text is  not match");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("userstatuscol");
			LOGGER.info("Clicked on the User Status column");
			softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("invitedStatus",getTextLanguage(LanguageCode, "daas_ui", "users.details.status.invited")),"Invited status not selected");
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("invitedStatus");
			LOGGER.info("Selected Invited status from the dropdown");
			sleeper(2000);
			if (WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")){
				softAssert.assertEquals(WEXCustomerUserListPage.getTextOfWEXCustomerUserListPage("noItemsAvailable"),getTextLanguage(LanguageCode, "daas_ui", "global.no_results")," No results text is  not match");
				LOGGER.info("Data is not available");
			}else {
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
			LOGGER.info("Invited user verified in MSP login");
		}		
		catch(Exception e) {
			Assert.fail("Exception occured in verifyInvitedCustomerUserInMSPLogin " +e);	
		}
	}

}