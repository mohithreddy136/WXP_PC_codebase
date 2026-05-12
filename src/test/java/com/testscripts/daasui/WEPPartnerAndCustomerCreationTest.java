package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.*;
import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.Objects;
import static com.daasui.pages.WEXPartnerPage.getddMMMyyyyFormattedDate;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class WEPPartnerAndCustomerCreationTest extends CommonTest {

	@DataProvider
	public Object[][] companyEmailProvider() {
		String companyEmailAddress1 = "autocustomerdnd_protrial" + getddMMMyyyyFormattedDate().toLowerCase() + CommonVariables.EMAIL_DOMAIN;
		String companyEmailAddress2 = "autocustomerdnd_premiumpremiumplus" + getddMMMyyyyFormattedDate().toLowerCase() + CommonVariables.EMAIL_DOMAIN;
		return new Object[][] {
				{ companyEmailAddress1, CommonVariables.PLAN_WXP_PRO_TRIAL },
				//{ companyEmailAddress2, CommonVariables.PLAN_ACTIVE_CARE_Premium }
		};
	}

	private String company_name = CommonVariables.FIRST_NAME+CommonVariables.LAST_NAME;

	private static Logger LOGGER = LogManager.getLogger(SupportTeamTest.class);
	public int activePageNumber = 0, firstPageNumber = 0, lastPageNumber = 0, selectedOption = 0, totalCount = 0;
	public boolean previousButtonStatus = false, nextButtonStatus = false;
	public static String cataLystURL = getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL");
	public String loginLink;
	private String partner_email;

	/*
	 * This test case validates add partner functionality on partner list page
	 */
	@Test(priority = 1, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : C57560040")
	public final void verifyAddPartnerFunctionality() throws Exception {
		login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		String partnerID = generateRandomString(11);
		LOGGER.info("Redirected to companies list page");
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerAndCustomerCreationPage wEPPartnerAndCustomerCreationPage = new WEPPartnerAndCustomerCreationPage(PreDefinedActions.getDriver()).getInstance();
		partner_email = WEPPartnerVariables.PARTNER_EMAIL + generateRandomString(4).toLowerCase() + WEXPartnerPage.getddMMMyyyyFormattedDate().toLowerCase() + "@hpmsqa.mailinator.com";
        LOGGER.info("partner email id is: {}", partner_email);
		String pbm_email = "pbm" + generateRandomString(11).toLowerCase() + "@hpmsqa.mailinator.com";
		String obm_email = "obm" + generateRandomString(11).toLowerCase() + "@hpmsqa.mailinator.com";

		try {
            wepPartnerCustomersPage.sideMenuExpansionForPartner();
            wEPPartnerAndCustomerCreationPage.sideMenuSelectionWEPRootLoginPage(LanguageCode, "PartnerMenu", "Partners");
            waitForPageLoaded();
            sleeper(2000);
            LOGGER.info("Redirected Partner tab page");

			//Add partner
			partnerPage.clickOnElementsOfPartnerPage("addPartnerButton");
			LOGGER.info("Clicked on add partner button");
			partnerPage.enterTextForPartnerPage("addPartnerID", partnerID);
			partnerPage.enterTextForPartnerPage("compPartnerNameSearch", PartnerVariables.NAME);
			wEPPartnerAndCustomerCreationPage.actionClickOnWEPCreateCompanyPage("countryDropdown");
			wEPPartnerAndCustomerCreationPage.selectFirstValueFromDropdownOnWEPCreateCompanyPage("countryList");
			partnerPage.enterTextForPartnerPage("partnerFirstNameSearchBox", PartnerVariables.FIRST_NAME);
			partnerPage.enterTextForPartnerPage("partnerLastNameSearchBox", PartnerVariables.LAST_NAME);
			partnerPage.enterTextForPartnerPage("partnerEmailSearchBox", partner_email);
			partnerPage.enterTextForPartnerPage("partnerPhoneSearchBox", PartnerVariables.PHONE_NUMBER);
			wEPPartnerAndCustomerCreationPage.actionClickOnWEPCreateCompanyPage("idpDropdown");
			sleeper(2000);
			wEPPartnerAndCustomerCreationPage.selectFirstValueFromDropdownOnWEPCreateCompanyPage("idpList");
			LOGGER.info("Filled all form fields");

			partnerPage.clickOnElementsOfPartnerPage("next");
			sleeper(2000);

			//Verify Partner Business Manager Labels 
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("pbmTitleText", getTextLanguage(LanguageCode, "daas_ui", "partners.list.partner_business_manager")), "Partner Business Manager Title on add partner popup is incorrect");
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("pbmFirstNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.first_name")), "Partner Business Manager First Name Label on add partner popup is incorrect");
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("pbmLastNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.last_name")), "Partner Business Managet Last Name Label on add partner popup is incorrect");
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("pbmEmailLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.email_address")), "Partner Business Managet Email Address Label on add partner popup is incorrect");

			//Enter data on Partner Business Manager fields
			partnerPage.enterTextForPartnerPage("pbmFirstNameSearchBox", PartnerVariables.PBM_FIRST_NAME);
			partnerPage.enterTextForPartnerPage("pbmLastNameSearchBox", PartnerVariables.PBM_LAST_NAME);
			partnerPage.enterTextForPartnerPage("pbmEmailSearchBox", pbm_email);

			//Verify Onboarding Business Manager Labels
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("obmTitleText", getTextLanguage(LanguageCode, "daas_ui", "partners.list.onboarding_business_manager")), "Onboarding Business Manager Text on add partner popup is incorrect");
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("obmFirstNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.first_name")), "Onboarding Business Manager First Name Label on add partner popup is incorrect");
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("obmLastNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.last_name")), "Onboarding Business Manager Last Name Label on add partner popup is incorrect");
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("obmEmailLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.email_address")), "Onboarding Business Manager Email Address Label on add partner popup is incorrect");

			//Enter data on Onboarding Business Manager fields
			partnerPage.enterTextForPartnerPage("obmFirstNameSearchBox", PartnerVariables.OBM_FIRST_NAME);
			partnerPage.enterTextForPartnerPage("obmLastNameSearchBox", PartnerVariables.OBM_LAST_NAME);
			partnerPage.enterTextForPartnerPage("obmEmailSearchBox", obm_email);
			partnerPage.clickByJavaScriptOnPartnerPage("pbmobmCheckBox");
			softAssert.assertTrue(Objects.requireNonNull(partnerPage.getTextOfPartnerPage("notifyUserText")).equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.business_manager_send_email")), "Send notification to users text is not present on popup");

			//Verify Cancel, Previous and Save buttons are available
			softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("previous"), "Previous button is not available");
			softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("save"), "Save button is not available");
			softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("cancel"), "Cancel button is not available");
			sleeper(2000);
			partnerPage.clickOnElementsOfPartnerPage("save");
			LOGGER.info("Clicked on save button");
            partnerPage.waitForElementsOfPartnerPageDynamic("partnerAddedSuccessfullyToastNotification",10);
            softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("partnerAddedSuccessfullyToastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner.add.successful")), "Partner added successfully toast notification is not displayed or text is incorrect");
            LOGGER.info("Partner Added Successfully");
            sleeper(2000);

            //Validating partner admin email
			String subjectInvitation = "You have been invited to the HP Workforce Experience Platform (WXP)!";
			String invitationEmailContent = wEPPartnerAndCustomerCreationPage.getActualMailContent(partner_email, subjectInvitation);
			loginLink = wEPPartnerAndCustomerCreationPage.extractLink(invitationEmailContent);
            assertNotNull(loginLink, "Login link is not available");
			softAssert.assertAll();
		} catch (Exception e) {
			Assert.fail("Exception occurred in method verifyAddPartnerFunctionality " + e.getMessage());
		}
	}

	@Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : C51807708", dependsOnMethods = {"verifyAddPartnerFunctionality"})
	public final void newPartnerLoginFunctionality() throws Exception {
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerAndCustomerCreationPage wEPPartnerAndCustomerCreationPage = new WEPPartnerAndCustomerCreationPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		//login to partner account using invitation link
		getDriver().navigate().to(loginLink);
		waitForPageLoaded();
		partnerPage.clickByJavaScriptOnPartnerPage("passowrdTextBox");
		partnerPage.enterTextForPartnerPage("passowrdTextBox", getCredentials(environment, "NO_DATA_PARTNER_PASSWORD_WEP"));
		sleeper(2000);
		wEPPartnerAndCustomerCreationPage.actionClickOnWEPCreateCompanyPage("createButton");
		LOGGER.info("partner login password created!");
		sleeper(2000);

		//Verifying email address
		String subjectVerificationCode = "Verify your email address";
		String verificationEmailContent = wEPPartnerAndCustomerCreationPage.getVerificationEmail(partner_email, subjectVerificationCode);
		String code = wEPPartnerAndCustomerCreationPage.extractVerificationCode(verificationEmailContent);
		waitForPageLoaded();
		partnerPage.clickByJavaScriptOnPartnerPage("codeTextBox");
		partnerPage.enterTextForPartnerPage("codeTextBox", code);
		partnerPage.clickByJavaScriptOnPartnerPage("verifyButton");
		LOGGER.info("Verified email address!");

		// Verify and accept terms and conditions
		if(partnerPage.verifyElementsOfPartnerPage("termsAndConditionsCheckboxTnC")){
			partnerPage.clickOnElementsOfPartnerPage("termsAndConditionsCheckboxTnC");
			softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("printButtonTnC"), "Print button is not available");
			softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("cancelButtonTnC"), "Cancel button is not available");
			partnerPage.clickOnElementsOfPartnerPage("acceptButtonTnC");
			LOGGER.info("Terms and Conditions accepted!");}

		//view dashboard tour popup
		if(partnerPage.matchTextOfPartnerPage("partnerDashboardTourTitle", getTextLanguage(LanguageCode, "daas_ui", "partner-welcome-popup-heading"))) {
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("partnerDashboardTourSubTitle", getTextLanguage(LanguageCode, "daas_ui", "partner-welcome-popup-subheading")), "Partner welcome popup subheading is incorrect");
			softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("cancelTourButton"), "Cancel tour button is not available");
			partnerPage.clickOnElementsOfPartnerPage("viewGuidedTourButton");
			partnerPage.clickOnElementsOfPartnerPage("nextButton");
			partnerPage.clickOnElementsOfPartnerPage("nextButton");
			partnerPage.clickOnElementsOfPartnerPage("nextButton");
			partnerPage.clickOnElementsOfPartnerPage("tooltipViewedButton");
		}
		softAssert.assertAll();
		LOGGER.info("Partner dashboard tour viewed!");
	}

	@Test(priority = 3, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : C51807708")
	public final void verifyEmptyStateDashboard() throws Exception {
		login("NO_DATA_PARTNER_EMAIL_WEP", "NO_DATA_PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		if(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

		LOGGER.info("Sign in with partner to get Partner Id and Partner Name for the Invite");
		wepPartnerCustomersPage.sideMenuExpansionForPartner();
		//Verify empty state dashboard
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardBannerTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.bannerTitle")), "Empty state dashboard banner title is incorrect");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardBannerDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.bannerdescription")), "Empty state dashboard banner description is incorrect");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardGuidedTourButton", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.guided.tourBtn")), "Empty state dashboard guided tour button text is incorrect");
		softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("emptyStateDashboardGuidedTourButton"), "Guided tour button is not available");

		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardCustomizeCardTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.customize.card.title")), "Empty state dashboard guided tour button text is incorrect");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardCustomizeCardDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.customize.card.description")), "Empty state dashboard customize card description is incorrect");
		softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("CustomizeButton"), "Customize button is not available");

		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardAddMemberTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.addCompanyUsers.card.title")), "Empty state dashboard guided tour button text is incorrect");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardAddMemberDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.addCompanyUsers.card.description")), "Empty state dashboard customize card description is incorrect");
		softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("AddMemberButton"), "Add member button is not available");

		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardCertificationsTitle","Boost your expertise with HP Solutions certifications"), "Empty state dashboard certifications card title is incorrect");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardCertificationsDescription","Become an HP certified expert. Enhance your skills, validate your knowledge, and unlock new opportunities with our comprehensive certification program."), "Empty state dashboard certifications card description is incorrect");
		softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("LearnMoreButton_1"), "Learn more button 1 is not available");

		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardWatchActionCardTitle_1", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.watchAction.card.title")), "Empty state dashboard Watch Action card title is incorrect");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardWatchActionCardDescription", "Explore our demo video library to see how the HP Workforce Experience Platform delivers seamless and secure work experiences."), "Empty state dashboard Watch Action card description is incorrect");
		softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("WatchButton"), "Watch button is not available");

		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardStayInKnowTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.stayInKnow.card.title")), "Empty state dashboard stayInKnow card title is incorrect");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardStayInKnowCardDescription", "Subscribe today to the HP Partner News and get monthly updates on market news, operational updates, and solution information."), "Empty state dashboard stayInKnow card description is incorrect");
		softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("LearnMoreButton_2"), "Learn more button 2 is not available");

		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardLatestPostsTitle", "See the latest posts from our experts"), "Empty state dashboard Latest Posts card title is incorrect");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("emptyStateDashboardLatestPostsCardDescription", "Ensure you're up to date with the latest WXP information from our product experts, thought leaders and editorial team."), "Empty state dashboard Latest Posts card description is incorrect");
		softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("LearnMoreButton_3"), "Learn more button 3 is not available");
		softAssert.assertAll();
		LOGGER.info("Partner empty state dashboard validated!");
	}


	/**
	 * This method will verify the add customer through partner, login with new customer and accept partner invitaiton functionality
	 *
	 * @throws Exception
	 */
	@Test(priority = 4, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID :T1460669384, C43522601 ",dataProvider = "companyEmailProvider")
	public final void AddDCustomerThroughPartner(String companyEmailAddress, String plan) throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WorkforceExpPartnerPage partnerPage = new WorkforceExpPartnerPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEPCreateCompanyPage WEPCreateCompanyPage = new WEPCreateCompanyPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
		wexPartnerPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW, CommonVariables.PARTNER_CUSTOMERS);
		WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("customers");
		waitForPageLoaded();
		// delete the old invited customers
		wepPartnerCustomersPage.clearAllFiltersOfWEPPartnerCustomersPage();

		wexPartnerPage.verifyElementsOfWEXPartnerPage("addCustomer");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("addCustomer"), "addCustomer button is not present");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("addCustomer");

		// Verify the add customer popup labels
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addCustomerTitle", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.title")), "add Customer Title is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addCustomerSubtitle", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.sub.title")), "add Customer Subtitle is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addCustomerStep1", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step1")), "add Customer Step 1 is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addCustomerPopupDescription", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.desc")), "add Customer Popup Description is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("companyNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.company_name")), "company Name Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addressLine1Label", getTextLanguage(LanguageCode, "daas_ui", "users.details.address1")), "address Line 1 Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addressLine2Label", getTextLanguage(LanguageCode, "daas_ui", "users.details.address2")), "address Line 2 Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("cityLabel", getTextLanguage(LanguageCode, "daas_ui", "users.details.city")), "city Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("stateLabel", getTextLanguage(LanguageCode, "daas_ui", "users.details.region")), "state Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("zipCodeLabel", getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.zip-code")), "zip Code Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("countryLabel", getTextLanguage(LanguageCode, "daas_ui", "create_company.msp.country")), "country Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("cancelPopupButton", getTextLanguage(LanguageCode, "daas_ui", "flexible_dashboard.modals.cancel")), "cancel Popup Button is not matching.");

		// verify cancel button of popup
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("cancelPopupButton");
		softAssert.assertFalse(wexPartnerPage.verifyElementsOfWEXPartnerPage("addCustomerTitle"), "add Customer Title is matching after cancel.");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("addCustomer");

		// fill the details of step 1
		wexPartnerPage.enterTextForWEXPartnerPage("companyName", company_name );
		wexPartnerPage.enterTextForWEXPartnerPage("addressLine", CommonVariables.STREET_ADDRESS);
		wexPartnerPage.enterTextForWEXPartnerPage("city", CommonVariables.CITY);
		wexPartnerPage.enterTextForWEXPartnerPage("state", CommonVariables.STATE);
		wexPartnerPage.enterTextForWEXPartnerPage("zipCode", CommonVariables.ZIP_CODE);
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("nextButton", getTextLanguage(LanguageCode, "daas_ui", "global.next")), "Next Button is not matching.");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("nextButton");

		waitForPageLoaded();
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addCustomerStep2Subtitle", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.itadmin.sub.title")), "add Customer Step 2 Subtitle is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addCustomerStep2", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step2")), "add Customer Step 2 is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addCustomerStep2Description", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.itadmin.desc")), "add Customer Step 2 Description is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("firstNameLabel", getTextLanguage(LanguageCode, "daas_ui", "itadmin.form.first_name")), "first Name Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("lastNameLabel", getTextLanguage(LanguageCode, "daas_ui", "itadmin.form.last_name")), "last Name Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("emailLabel", getTextLanguage(LanguageCode, "daas_ui", "ee_pulses_audience_static_dataType_email")), "email Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("phoneLabel", getTextLanguage(LanguageCode, "daas_ui", "Incidents.phone_number")), "phone Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("idpLabel", getTextLanguage(LanguageCode, "daas_ui", "users.add.select_idp")), "idp Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("backButton", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.button.back")), "back Button is not matching.");

		// verify back button of popup
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("backButton");
		waitForPageLoaded();
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("addCustomerSubtitle", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.sub.title")), "add Customer SubTitle is not matching.");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("nextButton");
		waitForPageLoaded();

		// fill the details of step 2
		wexPartnerPage.enterTextForWEXPartnerPage("firstname", CommonVariables.FIRST_NAME);
		wexPartnerPage.enterTextForWEXPartnerPage("lastname", CommonVariables.LAST_NAME);
		wexPartnerPage.enterTextForWEXPartnerPage("emailAddress", companyEmailAddress);
		wexPartnerPage.scrollOnWEXPartnerPage("selectIdp");
		wexPartnerPage.actionClickOnWEXPartnerPage("selectIdp");
		wexPartnerPage.actionClickOnWEXPartnerPage("HPID");
		LOGGER.info("HPID selected");

		wexPartnerPage.scrollOnWEXPartnerPage("selectPlan");
		wexPartnerPage.actionClickOnWEXPartnerPage("selectPlan");
        wexPartnerPage.actionClickOnWEXPartnerPage("proTrialPlanOption");
		LOGGER.info("Plan selected");
		waitForPageLoaded();
		wexPartnerPage.clickOnElementsOfwexPartnerPage("addButton");
		LOGGER.info("Clicked on Add Button");

		waitForPageLoaded();
		softAssert.assertTrue(wexPartnerPage.waitForElementsOnWEXPartnerPage("companyProfile"), "Company Profile not Present");
		wexPartnerPage.scrollOnWEXPartnerPage("emailId");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("emailId");
		wexPartnerPage.scrollOnWEXPartnerPage("emailIdValueNew");
		String actualEmailId = Objects.requireNonNull(wexPartnerPage.getTextOfWEXPartnerPage("emailIdValueNew")).trim();
		softAssert.assertTrue(actualEmailId.equals(companyEmailAddress), "Customer email is not matching");
		LOGGER.info("Email id verified correctly");
		LOGGER.info("Get the customer email:(Actual)" + actualEmailId);
		LOGGER.info("Get the customer email:(Expected)" + companyEmailAddress);

		wexPartnerPage.clickOnElementsOfWEXPartnerPage("customers");

		// clear all filters of customer page
		wepPartnerCustomersPage.clearAllFiltersOfWEPPartnerCustomersPage();
        wexPartnerPage.clickOnElementsOfwexPartnerPage("InvitesTab");
		wexPartnerPage.mousehoverOnWEXPartnerPage("searchItem");
		Thread.sleep(2000);
		wexPartnerPage.clickByJavaScriptOnElementsOnPartnerPage("searchItem");
		wexPartnerPage.clickOnElementsOfwexPartnerPage("searchItem");
		sleeper(2000);
		wexPartnerPage.enterTextForWEXPartnerPage("searchItem", company_name);
		Thread.sleep(2000);
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("pendingStatus"), "Company not in pending state.");
		String subjectInvitation = "You have been invited to the Workforce Experience Platform (WXP)!";

		String invitationEmailContent = WEPCreateCompanyPage.getActualMailContent(companyEmailAddress, subjectInvitation);
		loginLink = WEPCreateCompanyPage.extractLink(invitationEmailContent);
		LOGGER.info("Invitation email link is: {}", loginLink);
		LOGGER.info("AddCustomerThroughPartner test has been completed successfully");
		logout();

		wexPartnerPage.deleteAllcookies();

		//login to partner account using invitation link
		getDriver().navigate().to(loginLink);
		waitForPageLoaded();
        WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("passowrdTextBox");
		WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("passowrdTextBox");
		WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("passowrdTextBox", WEPCustomerVariables.CUSTOMER_PWD);
		sleeper(2000);
		WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("createButton");
		LOGGER.info("customer login password created!");
		sleeper(2000);

		//Verifying email address
        String subjectVerificationCode = "Verify your email address";
        String subjectVerificationCodeAlt = "HP account confirmation";
        String verificationEmailContent = WEPCreateCompanyPage.getVerificationEmail(companyEmailAddress, subjectVerificationCode);
        if (verificationEmailContent == null || verificationEmailContent.isEmpty()) {
            verificationEmailContent = WEPCreateCompanyPage.getVerificationEmail(companyEmailAddress, subjectVerificationCodeAlt);
        }
        String code = WEPCreateCompanyPage.extractVerificationCode(verificationEmailContent);
		waitForPageLoaded();
		WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("codeTextBox");
		sleeper(3000);
		WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("codeTextBox", code);
		WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("verifyButton");
		sleeper(7000);
		LOGGER.info("Verified email address!");
		waitForPageLoaded();
		// Onboarding Page
		sleeper(7000);

		WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("verifyOnboardingLogo");
		softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("verifyOnboardingLogo"), "Logo is not present");
		waitForPageLoaded();
		WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("clickOnCompanySizeButton");
		WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("clickOnCompanySizeButton");
        WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("companySize-option-0");
        WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("termsCheckbox");
		WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("dataCollectingCheckbox");
		waitForPageLoaded();
		WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("createAccountButton");
		waitForPageLoaded();
        Thread.sleep(5000);
		if(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("skipButton")){
			WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("skipButton");}
		waitForPageLoaded();
		Thread.sleep(2000);
		if(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("welcomePopupSkipButton")){
			WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("welcomePopupSkipButton");}
		LOGGER.info("Customer dashboard tour viewed!");

		leftSideMenuVerification();
        //*[@aria-label="Close modal"]
        wexPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("closeInviteModal");
        Thread.sleep(2000);
        wexPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("AccountManagementTab");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("assignPartner"),"assignPartner is not visible");
		wexPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("assignPartner");
		softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("partnerInvitationAccept"),"partnerInvitationAccept button is not visible");
		wexPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("partnerInvitationAccept");
		wexPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("confirmUnassignPartnr");
		wexPartnerDashboardPage.waitForElementsOnWEXPartnerDashboardPage("invitationSuccessToast");
		softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("invitationSuccessToast"), "Invitation approve process failed");
		wexPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("AccountManagementTab");
		logout();
		softAssert.assertAll();
		wexPartnerPage.deleteAllcookies();
		LOGGER.info("Partner invitation accepted!");
	}

	/**
	 * This method will verify the delete customer through root admin
	 * @throws Exception
	 */
	@Test(priority = 5, groups =  {"REGRESSION_PARTNERCX"}, description = "TestCase ID : ",dataProvider = "companyEmailProvider")
	public final void verifyDeleteCompany(String companyEmailAddress, String plan) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPCreateCompanyPage WEPCreateCompanyPage = new WEPCreateCompanyPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerAndCustomerCreationPage wepPartnerAndCustomerCreationPage=  new WEPPartnerAndCustomerCreationPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			wepPartnerCustomersPage.sideMenuExpansionForPartner();
			wepPartnerAndCustomerCreationPage.sideMenuSelectionWEPRootLoginPage(LanguageCode, "Customers", "Companies");
			waitForPageLoaded();
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addCompany"),"Add Company button not present");
			LOGGER.info("Redirected Company tab page");

			if (WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("clearFilter")) {
				WEPCreateCompanyPage.mousehoverOnWEPCreateCompanyPage("clearFilter");
				WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			int totalCompanies =WEPCreateCompanyPage. countOfAllElementOnWEPCreateCompanyPage("totalCompanies");
			LOGGER.info("Total Companies: " + totalCompanies);

			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("compName", company_name);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("primaryEmailColumn", companyEmailAddress);
			waitForPageLoaded();
			sleeper(2000);
			WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("partnerInvitedCompanyName");
			WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("partnerInvitedCompanyName");
			sleeper(2000);
			if (WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("partnerInvitedCustomer", company_name)) {
				WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("deleteCompBtn");
				WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("deleteCompBtn");
				waitForPageLoaded();
				softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deleteCompModal"));
				softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deleteBtn"));
				softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("CancelBtn"));
				String deleteModelDesc = WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("deleteModelDesc");
				if (deleteModelDesc.contains(company_name)) {
					WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("deleteBtn");
					sleeper(5000);
					softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deleteToast"), "Toast message not present");
				} else {
					LOGGER.info("Company not found!!");
					WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("CancelBtn");
					WEPCreateCompanyPage.sideMenuSelectionWEPCreateCompanyPage(LanguageCode, "Customers", "Companies" );
					waitForPageLoaded();
				}
			}  else {
				LOGGER.info("Company not found!!");
				WEPCreateCompanyPage.sideMenuSelectionWEPCreateCompanyPage(LanguageCode, "Customers", "Companies" );
				waitForPageLoaded();
			}
			if (WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("clearFilter")) {
				WEPCreateCompanyPage.mousehoverOnWEPCreateCompanyPage("clearFilter");
				WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}

			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("primaryEmailColumn", companyEmailAddress);
			waitForPageLoaded();
			assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("noResults"),"Company not deleted");
			LOGGER.info("Company Removed successfully");
		}
		catch(Exception e) {
			Assert.fail("Exception occurred in verifyDeleteCompany: "+ e);
		}
	}
}