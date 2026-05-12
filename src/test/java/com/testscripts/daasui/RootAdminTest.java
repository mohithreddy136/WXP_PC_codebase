package com.testscripts.daasui;

import java.util.HashMap;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.MailinatorMail;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.LaunchDarkly;
import com.basesource.utils.Mailinator;
import com.daasui.constants.CompaniesVariables;
import com.daasui.pages.CompaniesDetailsPage;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.UserDetailsPage;
import com.daasui.pages.UserPage;

public class RootAdminTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(RootAdminTest.class);

	@DataProvider
	public Object[][] RPLLoginData () {
		Object[][] data = new Object[3][2];
		data[0][0] = "MSP_BLOCKED_EMAIL";
		data[0][1] = "MSP_BLOCKED_PASSWORD";
		data[1][0] = "PARTNER_BLOCKED_EMAIL";
		data[1][1] = "PARTNER_BLOCKED_PASSWORD";
		data[2][0] = "COMPANY_BLOCKED_EMAIL";
		data[2][1] = "COMPANY_BLOCKED_PASSWORD";
		return data;
	}

	@DataProvider
	public Object[] getLoginDataForRPL() {
		Object[][] data = new Object[2][2];
		data[0][0] = "PARTNER_RPL_EMAIL";
		data[0][1] = "Block";
		data[1][0] = "MSP_RPL_EMAIL";
		data[1][1] = "Pending for Approval";
		return data;
	}

	@DataProvider
	public Object[][] getRPLUsers() {
		Object[][]data = new Object[2][2];
		data[0][0] = "PARTNER_RPL_LoginEMAIL";
		data[0][1] = "PARTNER_RPL_PASSWORD";
		data[1][0] = "MSP_RPL_LoginEMAIL";
		data[1][1] = "MSP_RPL_PASSWORD";
		return data;
	}

	/*
	 * Select MSP according to the region
	 */
	@Test(priority = 1, groups = { "REGRESSION" },enabled = false)
	public final void rootAdminCompanyTest() throws Exception {
		Assert.assertTrue(addCompany("MSP03", "", "India", "US"), "Company is not added.");
		LOGGER.info("Company successfully created.");
		Assert.assertTrue(companyPresentInMSP(companyEmail, MSPEmail, MSPPassword), "Company not listed int MSP");
		LOGGER.info("Company successfully listed into MSP");
		logout();
		Assert.assertTrue(companyCleanUp(companyEmail, "US"), "Company is not deleted.");
		LOGGER.info("Company successfully deleted.");
		logout();
		Assert.assertTrue(companyPresentInMSP(companyEmail, MSPEmail, MSPPassword), "Company present in MSP after deletion");
	}

	/*
	 * Verify country dropdown contains Russia options to add MSP for EU region 1) Verify by launch darkly apm-russia is true 2) Verify country as Russia on Company detail Page(User as
	 * MSP USER) for Added company by EU root 3) Verify value in drop down contains Russia (User as MSP USER) on Edit Company detail Page
	 * 
	 */
	@Test(priority = 2, groups = { "REGRESSION" },enabled = false)
	public final void verifyRussiaAsSupportedCountryinDropdown() throws Exception {
		SoftAssert softAssertion = new SoftAssert();

		LaunchDarkly ldinstance = new LaunchDarkly().getInstance();
		Assert.assertTrue((ldinstance.enabled("apm-russia", null, null, true)), "LD toggle is off.");
		ldinstance.destroy();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver());
		companiesPage = companiesPage.getInstance();

		// Test case ID 207887:Verify Added company with country Russian Federation
		Assert.assertTrue(addCompany("MSP06", "", countryNameArray[0], "EU"), "Company is not added.");
		LOGGER.info("Company successfully created.");

		login(MSPEmail, MSPPassword);
		impersonateCompanyByEmail(companyEmail);
		softAssertion.assertTrue(companiesPage.matchTextOfCompaniesPage("company_country_show_byMSP", countryNameArray[0]), "country is diffrent on companydetail page ");
		LOGGER.info("Verified country on Company detail page.");

		companiesPage.clickOnElementsOfCompaniesPage("edit_company_btn");
		// Test case ID 207882:Validate Russia is available for Adding Tenant
		softAssertion.assertTrue(companiesPage.verifyElementPresentIndropdownOfCompaniesPage("company_first_address_countryDD", countryNameArray[0]), "Company not in dropdown");
		LOGGER.info("Verify In company detail Edit page in drop down country Expected " + countryNameArray[0]);

		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver());
		settingsPage = settingsPage.getInstance();

		if (settingsPage.matchTextOfSettingsPage("country_text", countryNameArray[0]) == true) {
			settingsPage.clickOnElementsOfSettingsPage("dui-epr-edit");
			settingsPage.MoveoverElementForSettingsPage("country_timezone_popupid");
			settingsPage.clickOnElementsOfSettingsPage("country_dp_icon");
			settingsPage.selectElementFromDropDownForSettingsPage("country_dp_icon", "countryDD", countryNameArray[2]);
			settingsPage.clickOnElementsOfSettingsPage("submit-country-info");
			LOGGER.info("Drop down selected to  Albania");
		}
		LOGGER.info("On Setting Page Hour of Operation ");
		settingsPage.clickOnElementsOfSettingsPage("dui-epr-edit");
		settingsPage.MoveoverElementForSettingsPage("country_timezone_popupid");
		settingsPage.clickOnElementsOfSettingsPage("country_dp_icon");
		// Test case ID 207937:Russian Federation should be present in the drop down
		softAssertion.assertTrue(settingsPage.selectElementFromDropDownForSettingsPage("country_dp_icon", "countryDD", countryNameArray[0]), "Country is diffrent " + countryNameArray[0]);
		LOGGER.info("Country selected in dropdown as " + countryNameArray[0]);
		settingsPage.clickOnElementsOfSettingsPage("submit-country-info");

		softAssertion.assertTrue(settingsPage.matchTextOfSettingsPage("country_text", countryNameArray[0]), "Country is diffrent in text " + countryNameArray[0]);
		LOGGER.info("Validation Done ");
		logout();

		softAssertion.assertTrue(companyCleanUp(companyEmail, "EU"), "Company is not deleted.");
		LOGGER.info("Company successfully deleted.");
		softAssertion.assertAll();
	}

	/*
	 * Login URL US region by MSP Verifying Russia country is selected in MSP of EU region in company country drop down Verifing URL is redirecing to EU while login by EU region User
	 * and In setting country is Russia Setting tenent back to country countryNameArray[2]
	 * 
	 */
	@Test(priority = 3, groups = { "REGRESSION" }, enabled = false)
	public final void verifyCountryRussiaisSelectedinMSP() throws Exception {
		LaunchDarkly ldinstance = new LaunchDarkly().getInstance();
		Assert.assertTrue((ldinstance.enabled("apm-russia", null, null, true)), "LD toggle is off.");
		ldinstance.destroy();

		// MSP set country as Russia in company detail page
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver());
		companiesPage = companiesPage.getInstance();

		login("MSP_ADMIN_EU_EMAIL", "MSP_ADMIN__EU_PASSWORD");
		impersonateCompanyByEmail(getCredentials(environment, "EU_Russia_User_Email"));
		companiesPage.clickOnElementsOfCompaniesPage("edit_company_btn");
		companiesPage.selectIndropdownOfCompaniesPage("company_first_address_countryDD", countryNameArray[0]);
		LOGGER.info("Country selected" + countryNameArray[0]);
		companiesPage.clickOnElementsOfCompaniesPage("save_company_settings_btn");
		LOGGER.info("Saved change Country to " + countryNameArray[0]);
		Assert.assertTrue(companiesPage.waitForElementsOfCompaniesPage("notifyMsg"), "Notification not displayed");
		Assert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("messageMsp"), "Success notification is not displayed");
		logout();

		// Login in US regoin by EU User to verify url redirection and country as russia
		login("EU_Russia_User_Email", "EU_Russia_User_Password");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver());
		settingsPage = settingsPage.getInstance();

		settingsPage.gotoUserSettingsTab();
		LOGGER.info("click on setting page ");
		Assert.assertTrue(settingsPage.matchTextOfSettingsPage("company_country_show", countryNameArray[0]), "country is diffrent on company detail page ");
		LOGGER.info("Verified country text on setting page ");
		logout();

		// data set up to us country setting back to Afghanistan
		login("MSP_ADMIN_EU_EMAIL", "MSP_ADMIN__EU_PASSWORD");
		impersonateCompanyByEmail(getCredentials(environment, "EU_Russia_User_Email"));
		companiesPage.clickOnElementsOfCompaniesPage("edit_company_btn");
		LOGGER.info("Click on edit btn of company detail ");
		companiesPage.selectIndropdownOfCompaniesPage("company_first_address_countryDD", countryNameArray[2]);
		companiesPage.clickOnElementsOfCompaniesPage("save_company_settings_btn");
		LOGGER.info("Country selected as Albania :" + countryNameArray[2]);
	}

	/*
	 * Verifying Russia country is not present in US region for root user
	 *
	 */
	@Test(priority = 4, groups = { "REGRESSION" },enabled = false)
	public final void verifyRussiaCountry() throws Exception {
		LaunchDarkly ldinstance = new LaunchDarkly().getInstance();
		Assert.assertTrue((ldinstance.enabled("apm-russia", null, null, true)), "LD toggle is off.");
		ldinstance.destroy();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver());
		companiesPage = companiesPage.getInstance();

		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoRootCompaniesTab();
		HashMap<String, String> companyInfo = new HashMap<String, String>();
		companyInfo = companiesPage.getCompanyDetails();
		companiesPage.clickOnElementsOfCompaniesPage("addCompanyButton");
		Assert.assertFalse(companiesPage.addNewCompany(companyInfo, "MSP06", "", "Russian Federation"), "Error while entering data for new company");
	}

	/*
	 * This method will Verify the count of companies listed in the details page and the count specified below the section
	 */
	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI" },enabled = false)
	public final void rootAdminCompanyPageTest() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoRootCompaniesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver());
		companiesPage = companiesPage.getInstance();

		Assert.assertTrue(companiesPage.matchCompaniesCountOnForRoot(), "Count at the bottom on companies page does not match actual companies displayed");
	}

	// RPL fetaure is not deployed on staging yet that is why value of enabled is false
	// This test case validates rpl compliance status of users under root
	@Test(priority = 6, groups = {"REGRESSION"}, dataProvider = "getLoginDataForRPL", description = "Testcase - TestCase-781673,781668,781660, NFR Story 794217 ; User Type ~ MSP, Partner, Company", enabled = false)
	public final void verifyRPLComplianceStsOfUsersUnderRoot(String strUsername, String StrComplianceStatus) {
		try {
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			String expectedMailContent = "", actualMailContent = "";

			if (strUsername.contains("MSP")) {
				//Navigating to MSP Tab
				gotoMSPTab();
				LOGGER.info("Redirected to MSP page");

			} else if (strUsername.contains("PARTNER")) {
				//Navigating to Partner Tab
				gotoPartnersTab();
				LOGGER.info("Redirected to PARTNER page");

			}

			//Go to User detail page
			userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
			resetTableConfiguration();
			userPage.goToSpecificUserDetailsPage(getCredentials(environment, strUsername));
			waitForPageLoaded();
			sleeper(3000);

			//Update and verify compliance status value
			if (StrComplianceStatus.contains("Block")) {
				softAssert.assertTrue(userDetailsPage.getUpdateRPLComplianceStatusMsg("Block").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.compliance.status.title") + getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", "")), "Compliance status updated toast message is not correct");
				softAssert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("txtComplianceStatusOnTile").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.compliance.status.BLOCKED")), "User is not update to  Blocked state");

				expectedMailContent = getTextLanguage(LanguageCode, "rpl-backend-properties", "status.header") + " "
						+ getTextLanguage(LanguageCode, "rpl-backend-properties", "status.title") + ". "
						+ getTextLanguage(LanguageCode, "rpl-backend-properties", "status.message") + " "
						+ getTextLanguage(LanguageCode, "idm", "onboarding.thank.you.with.comma").replace(",", "") + " "
						+ getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear());

				//Get actual blocked email content
				Mailinator objMailinator = new Mailinator("", getCredentials(environment, strUsername).split("@")[0]);
				sleeper(5000);
				MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode("HP TechPulse account has not been approved", getCredentials(environment, strUsername).split("@")[0], true);
				String mailContent = objMailinatorEmail.getBody();
				actualMailContent = getHtmlParser(mailContent);


			} else if (StrComplianceStatus.contains("Pending for Approval")) {
				softAssert.assertTrue(userDetailsPage.getUpdateRPLComplianceStatusMsg("Pending for Approval").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.compliance.status.title") + getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", "")), "Compliance status updated toast message is not correct");
				softAssert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("txtComplianceStatusOnTile").equals(getTextLanguage(LanguageCode, "daas_ui", "global.panding.for.approval")), "User is not updated to  Waiting state");

				expectedMailContent = getTextLanguage(LanguageCode, "rpl-backend-properties", "waiting_status.header") + " "
						+ getTextLanguage(LanguageCode, "rpl-backend-properties", "waiting_status.title") + ". "
						+ getTextLanguage(LanguageCode, "rpl-backend-properties", "waiting_status.message") + " "
						+ getTextLanguage(LanguageCode, "idm", "onboarding.thank.you.with.comma").replace(",", "") + " "
						+ getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear());

				//Get actual waiting email content
				Mailinator objMailinator = new Mailinator("", getCredentials(environment, strUsername).split("@")[0]);
				sleeper(5000);
				MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode("HP TechPulse Account Created", getCredentials(environment, strUsername).split("@")[0], true);
				String mailContent = objMailinatorEmail.getBody();
				actualMailContent = getHtmlParser(mailContent);
			}

			//Verify email content
			softAssert.assertTrue(actualMailContent.equalsIgnoreCase(expectedMailContent), "Mail content does not match");

			softAssert.assertAll();
			LOGGER.info(StrComplianceStatus + " compliance status is verified successfully for user " + strUsername);

		} catch (Exception e) {
			Assert.fail("Exception occurred in method verifyRPLComplianceStsOfUsersUnderRoot " + e.getMessage());
		}
	}

	// RPL fetaure is deployed on staging but toggle is OFF yet that is why value of enabled is false
	// This test case validates screen of Blocked and Waiting user under Root
	@Test(priority = 7, groups = {"REGRESSION"}, dataProvider = "RPLLoginData", description = "Test Case-781747,781751,781749,781660 ,NFR Story 794217 ; User Type ~ MSP, Partner, Company", enabled = false)
	public final void verifyErrorMessageOnLoggedInScreen(String strUserName, String strPassword) {
		try {
			login(strUserName, strPassword);
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();

			if (getUrlOfCurrentPage().contains("blocked")) {
				//Verify Blocked screen header
				softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("txtAccountError").equals(getTextLanguage(LanguageCode, "daas_ui", "global.heading.blocked")), "Account Blocked screen header is not appearing");

				//Verify Blocked screen message
				softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("txtMsgLineOne").equals(getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line1.blocked")), "Blocked screen message line one is not appearing");
				softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("txtMsgLineTwo").equals(getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line2.blocked")), "Blocked screen message line two is not appearing");

			} else if (getUrlOfCurrentPage().contains("pending_approval")) {
				//Verify Waiting  screen header
				softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("txtAccountError").equals(getTextLanguage(LanguageCode, "daas_ui", "global.heading.pending_approval")), "Account waiting screen header is not appearing");

				//Verify Waiting screen message
				softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("txtMsgLineOne").equals(getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line1.pending_approval")), "Account verification screen message line one is not appearing");
				softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("txtMsgLineTwo").equals(getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line2.pending_approval")), "Account Verification screen message line two is not appearing");
			} else {
				LOGGER.info("User is not in blocked or waiting state, Kindly check user");
				softAssert.assertTrue(false);
			}

			softAssert.assertAll();
			LOGGER.info("Messages on screen verified successfully for user " + strUserName);

			//Click on Home Page button
			dashboardPage.clickOnElementsOfDashboardPage("btnError");
			waitForPageLoaded();

		} catch (Exception e) {
			Assert.fail("Exception occurred in method verifyErrorMessageOnLoggedInScreen " + e.getMessage());
		}
	}

	// RPL fetaure is not deployed on staging yet that is why value of enabled is false
	// This test case validates screen of Blocked and Waiting user under Root
	@Test(priority = 8, groups = {"REGRESSION"}, dataProvider = "getRPLUsers", description = "Testcase -781694  , NFR Story 794217 ; User Type ~ MSP, Partner", enabled = false)
	public final void verifyRPLComplianceStsOfCompUnderUsers(String strUserName, String strPassword) {
		try {
			login(strUserName, strPassword);
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

			SoftAssert softAssert = new SoftAssert();

			gotoCompaniesTab();
			userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
			resetTableConfiguration();

			//Go to User detail page
			companiesPage.waitForElementsOfCompaniesPage("firstCompany");
			companiesPage.clickOnElementsOfCompaniesPage("firstCompany");
			waitForPageLoaded();
			sleeper(3000);

			//Verify compliance status
			softAssert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("txtComplianceStatus").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.compliance.status.title")), "compliance status is not available");

		} catch (Exception e) {
			Assert.fail("Exception occurred in method verifyErrorMessageOnLoggedInScreen " + e.getMessage());
		}
	}
	
	//Test validates RPL compliance status when the Tenant Name is updated to Keywords like Nuclear and Osama bin Laden
	@Test(priority = 9, groups = {"REGRESSION"}, description = " 989216, 781683", enabled = false)
	public final void verifyRPLComplianceStsOfCompthroughRootLogin() throws Exception {
		String tenantID = null;
		String tenantID2 = null;

		try {
			login("RPL_Root", "PRL_Root_Password");
			LOGGER.info("Logged in with root user");
			SoftAssert softAssert = new SoftAssert();
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
			
			String subKey1 = generateRandomString(7);
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey1, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "RPL_MSP_ID"), "", "", CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info(subKey1 + " company created");
			
			String subKey2 = generateRandomString(7) + " osama bin laden";
			String compEmail2 = generateRandomString(7)+ "@yopmail.com";
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey2, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "RPL_MSP_ID"), getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID_RPL"), compEmail2, CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info(subKey2 + " company created");
			
			gotoCompaniesTab();
			resetTableConfiguration();

			impersonateCompanyByCompanyName(subKey1);
			LOGGER.info("Redirected to company details page");
			tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
			waitForPageLoaded();
			sleeper(3000);

			companiesDetailsPage.scrollOnCompaniesDetailsPage("txtCompliancelabel");
			sleeper(2000);
			//Verify compliance status
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtCompliancelabel").toLowerCase().equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.compliance.status.title").toLowerCase()), "compliance field is not available");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtComplianceStatusfield").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.compliance.status.APPROVED")), "compliance status is not incorrect");
			
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyName");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("nameSearchBox", subKey1 + " Nuclear");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("nameSave");
			waitForPageLoaded();
			sleeper(3000);	
			refreshPage();
			
			companiesDetailsPage.scrollOnCompaniesDetailsPage("txtCompliancelabel");
			sleeper(3000);
			//Verify compliance status
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtComplianceStatusfield").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.compliance.status.BLOCKED")), "compliance status is not incorrect on blocked");
			
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(subKey2);
			LOGGER.info("Redirected to company details page");
			tenantID2 = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
			waitForPageLoaded();
			sleeper(3000);
			
			companiesDetailsPage.scrollOnCompaniesDetailsPage("txtCompliancelabel");
			//Verify compliance status
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtComplianceStatusfield").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.compliance.status.BLOCKED")), "compliance status is not incorrect on blocked status");
			softAssert.assertAll();
			LOGGER.info("Compliance status is verified successfully");
			
		} catch (Exception e) {
			Assert.fail("Exception occurred in method verifyRPLComplianceStsOfCompthroughRootLogin " + e.getMessage());
		}
		finally {
			// Remove created company
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			companiesPage.removeCompanyUsingAPI(tenantID);
			companiesPage.removeCompanyUsingAPI(tenantID2);
		}
	}
}