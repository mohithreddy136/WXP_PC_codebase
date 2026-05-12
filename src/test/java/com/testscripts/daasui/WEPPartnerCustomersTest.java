package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ProductCatalogVariables;
import com.daasui.constants.WEPPartnerVariables;
import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class WEPPartnerCustomersTest extends CommonTest {

	private static final Logger LOGGER = LogManager.getLogger(WEPPartnerCustomersTest.class);

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
	 * This method will verify the add customer through partner & delete the customer invitaiton
	 *
	 * @throws Exception
	 */
	@Test(priority = 1, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID :T1460669384, C43522601 ", enabled = false)
	public final void AddDeleteInviteCustomerThroughPartner() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WorkforceExpPartnerPage partnerPage = new WorkforceExpPartnerPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		wexPartnerPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW, CommonVariables.PARTNER_CUSTOMERS);

		wexPartnerPage.clickOnElementsOfWEXPartnerPage("customers");
		waitForPageLoaded();
		// delete the old invited customers
		wepPartnerCustomersPage.clearAllFiltersOfWEPPartnerCustomersPage();

		wexPartnerPage.verifyElementsOfWEXPartnerPage("addCustomer");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("addCustomer"), "addCustomer button is not present");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("invite"), "invite button is not present");
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
		wexPartnerPage.enterTextForWEXPartnerPage("companyName", CommonVariables.COMPANY_NAME);
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
		String emailAddress = "autoinvitecustomer" + wexPartnerPage.getddMMMyyyyFormattedDate().toLowerCase() + CommonVariables.EMAIL_DOMAIN;
		wexPartnerPage.enterTextForWEXPartnerPage("emailAddress", emailAddress);
		wexPartnerPage.scrollOnWEXPartnerPage("selectIdp");
		wexPartnerPage.actionClickOnWEXPartnerPage("selectIdp");
		wexPartnerPage.actionClickOnWEXPartnerPage("HPID");
		LOGGER.info("HPID selected");

		wexPartnerPage.scrollOnWEXPartnerPage("selectPlan");
		wexPartnerPage.actionClickOnWEXPartnerPage("selectPlan");
		partnerPage.selectPlan(CommonVariables.PLAN_WXP_PRO_TRIAL, "planList");
		LOGGER.info("Plan selected");
		waitForPageLoaded();
		wexPartnerPage.clickOnElementsOfwexPartnerPage("addButton");
		LOGGER.info("Add Button clicked");
		waitForPageLoaded();
		if (wexPartnerPage.verifyElementsOfWEXPartnerPage("addButton")) {
			wexPartnerPage.clickOnElementsOfwexPartnerPage("addButton");
			LOGGER.info("Add Button clicked second time");
			waitForPageLoaded();
		}
		softAssert.assertTrue(wexPartnerPage.waitForElementsOnWEXPartnerPage("companyProfile"), "Company Profile not Present");
		softAssert.assertTrue(wexPartnerPage.matchTextOnWEXPartnerPage("tenantTitle", CommonVariables.COMPANY_NAME), "Customer is not added");
		LOGGER.info("Title verified");
		wexPartnerPage.scrollOnWEXPartnerPage("emailId");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("emailId");
		wexPartnerPage.scrollOnWEXPartnerPage("emailIdValue");
		String actualEmailId = wexPartnerPage.getTextOfWEXPartnerPage("emailIdValue").trim();
		softAssert.assertTrue(actualEmailId.equals(emailAddress), "Customer email is not matching");
		LOGGER.info("Email id verified correctly");
		LOGGER.info("Get the customer email:(Actual)" + actualEmailId);
		LOGGER.info("Get the customer email:(Expected)" + emailAddress);

		wexPartnerPage.clickOnElementsOfWEXPartnerPage("customers");

		// clear all filters of customer page
		wepPartnerCustomersPage.clearAllFiltersOfWEPPartnerCustomersPage();
		wexPartnerPage.mousehoverOnWEXPartnerPage("searchItem");
		Thread.sleep(2000);
		wexPartnerPage.clickByJavaScriptOnElementsOnPartnerPage("searchItem");
		wexPartnerPage.clickOnElementsOfwexPartnerPage("searchItem");
		sleeper(2000);
		wexPartnerPage.selectTextValueFromDropdownOfCustomerListPageWorkflow("listDropdown", CommonVariables.COMPANY_NAME, "");
		Thread.sleep(2000);
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("pendingStatus"), "Company not in pending state.");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("optionChoice");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("resendInvite"), "Resend Invite option not available");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("viewCustomerProfile"), "view Customer Profile option not available");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("deleteInvite"), "Delete Invite option not available");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("deleteInvite");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("confirmDeleteComp");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("deleteConfirmToasts");

		softAssert.assertAll();
		LOGGER.info("AddDeleteInviteCustomerThroughPartner test has been completed successfully");
	}

	/**
	 * This method will verify the delete customer through root admin
	 *
	 * @throws Exception
	 */
	@Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : ", enabled = false)
	public final void verifyDeleteCompany() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPCreateCompanyPage WEPCreateCompanyPage = new WEPCreateCompanyPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
			WEPCreateCompanyPage.sideMenuSelectionWEPCreateCompanyPage(LanguageCode, "Customers", "Companies");
			waitForPageLoaded();
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addCompany"), "Add Company button not present");
			LOGGER.info("Redirected Company tab page");

			if (WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("clearFilter")) {
				WEPCreateCompanyPage.mousehoverOnWEPCreateCompanyPage("clearFilter");
				WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			int totalCompanies = WEPCreateCompanyPage.countOfAllElementOnWEPCreateCompanyPage("totalCompanies");
			LOGGER.info("Total Companies: " + totalCompanies);
			String companyEmailAddress = WEPPartnerVariables.CUSTOMER_EMAIL + wexPartnerPage.getddMMMyyyyFormattedDate().toLowerCase() + CommonVariables.EMAIL_DOMAIN;
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("compName", CommonVariables.COMPANY_NAME);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("primaryEmailColumn", companyEmailAddress);
			waitForPageLoaded();
			sleeper(2000);
			WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("companyNameNew");
			WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("companyNameNew");
			sleeper(2000);
			if (WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("invitedCustomer", CommonVariables.COMPANY_NAME)) {
				WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("deleteCompBtn");
				WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("deleteCompBtn");
				waitForPageLoaded();
				softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deleteCompModal"));
				softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deleteBtn"));
				softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("CancelBtn"));
				String deleteModelDesc = WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("deleteModelDesc");
				if (deleteModelDesc.contains(CommonVariables.COMPANY_NAME)) {
					WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("deleteBtn");
					sleeper(5000);
					softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deleteToast"), "Toast message not present");
				} else {
					LOGGER.info("Company not found!!");
					WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("CancelBtn");
					WEPCreateCompanyPage.sideMenuSelectionWEPCreateCompanyPage(LanguageCode, "Customers", "Companies");
					waitForPageLoaded();
				}
			} else {
				LOGGER.info("Company not found!!");
				WEPCreateCompanyPage.sideMenuSelectionWEPCreateCompanyPage(LanguageCode, "Customers", "Companies");
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
			Assert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("noResults"), "Company not deleted");
			LOGGER.info("Company Removed successfully");
		} catch (Exception e) {
			Assert.fail("Exception occurred in verifyDeleteCompany: " + e);
		}
	}

	/**
	 * This method will Verify partner to customer invitation acceptance
	 */
	@Test(priority = 3, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : C43522604", enabled = false)
	public final void verifyPartnerToCustomerInvitaitonAcceptance() throws Exception {
		login("INVITE_CUSTOMER_EMAIL_WEP", "INVITE_CUSTOMER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

		// If the what's new popup is available, close it
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

		leftSideMenuVerification();
		waitForPageLoaded();
		LOGGER.info("Sign in with customer to remove the partner mapping");
		wepPartnerCustomersPage.clickIfPresent("invitationPopupModalTitle", "invitationPopupModalClose");
		WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("AccountManagementTab");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("overview");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("customerID");
		String customerID = wexPartnerPage.getTextOfWEXPartnerPage("customerID");
		String CompanyName = wexPartnerPage.getTextOfWEXPartnerPage("companyValue");
		Thread.sleep(2000);
		wexPartnerPage.verifyElementsOfWEXPartnerPage("assignPartner");
		WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("assignPartner");

		if (WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("unassignPartnrBtn")) {
			WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("unassignSettingsPartnrBtn");
			WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("unassignPartnrBtn");
			WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("confirmUnassignPartnr");
			WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("confirmUnassignPartnr");
			waitForPageLoaded();
			softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("unassignedPartnerToast"), "Partner was not updated");
			LOGGER.info("Available partner deleted.");
		} else {
			LOGGER.info("Already partner not available. Proceed with standard invite partner procedure");
		}
		if (WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("declinePartnerInvite")) {
			WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("declinePartnerInvite");
			WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("confirmDeclineRequest");
			WEXPartnerDashboardPage.waitForElementsOnWEXPartnerDashboardPage("invitationSuccessToast");
			softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("invitationSuccessToast"), "Invitation Decline process failed");
			LOGGER.info("Available Request Declined.");
		} else {
			LOGGER.info("Already Request not available. Proceed with standard invite partner procedure");
		}

		logout();
		wexPartnerPage.deleteAllcookies();

		login("NO_DATA_PARTNER_EMAIL_WEP", "NO_DATA_PARTNER_PASSWORD_WEP");
		LOGGER.info("Sign in with partner to send invite to the customer");
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");
		wepPartnerCustomersPage.switchToPartnerDashboard();
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("customers");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("invite");

		// verify invite popup cancel button
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("invite");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("sendInviteTitle", getTextLanguage(LanguageCode, "daas_ui", "customer.add.invitepopup.title")), "send Invite Title is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("sendInviteSubtitle", getTextLanguage(LanguageCode, "daas_ui", "customer.add.invitepopup.desc")), "send Invite Subtitle is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("customerIdLabel", getTextLanguage(LanguageCode, "daas_ui", "workflowOnboarding.company.id")), "customer ID Label is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("buttonInvite", getTextLanguage(LanguageCode, "daas_ui", "adminx.Onboard.invite")), "button Invite is not matching.");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("sendInviteCancelButton", getTextLanguage(LanguageCode, "daas_ui", "confirmationModal.closeLabel")), "send Invite popup Cancel Button is not matching.");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("sendInviteCancelButton");
		softAssert.assertFalse(wexPartnerPage.matchTextOfWEXPartnerPage("sendInviteTitle", getTextLanguage(LanguageCode, "daas_ui", "customer.add.invitepopup.title")), "send Invite Title is not matching.");
		LOGGER.info("Invite popup cancel button verified successfully");

		// verify invite popup closed button
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("invite");
		softAssert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("sendInviteTitle", getTextLanguage(LanguageCode, "daas_ui", "customer.add.invitepopup.title")), "send Invite Title is not matching.");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("invitePopupCloseButton");
		softAssert.assertFalse(wexPartnerPage.matchTextOfWEXPartnerPage("sendInviteTitle", getTextLanguage(LanguageCode, "daas_ui", "customer.add.invitepopup.title")), "send Invite Title is not matching.");
		LOGGER.info("Invite popup close button verified successfully");

		// verify invite popup invite button
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("invite");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("sendInviteText", customerID);
		Thread.sleep(3000);
		wexPartnerPage.verifyElementsOfWEXPartnerPage("buttonInvite");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("buttonInvite");
		wexPartnerPage.waitForElementsOnWEXPartnerPage("invitationSuccessToast");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("invitationSuccessToast"), "Invitation sent process failed");
		Thread.sleep(2000);

		wexPartnerPage.refreshPageOfWEXPartnerPage();
		waitForPageLoaded();
		// clear all filters of customer page
		wepPartnerCustomersPage.clearAllFiltersOfWEPPartnerCustomersPage();
		wexPartnerPage.mousehoverOnWEXPartnerPage("searchItem");
		Thread.sleep(2000);
		wexPartnerPage.clickByJavaScriptOnElementsOnPartnerPage("searchItem");
		wexPartnerPage.clickOnElementsOfwexPartnerPage("searchItem");
		sleeper(2000);
		wepPartnerCustomersPage.selecttextDropDownValueOfWEPPartnerPage("searchCompany", CompanyName, "listDropdown");
		sleeper(2000);
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameValue", CompanyName), "Invited Customer Name is not matching with the filter customer name in the list");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("pendingStatus"), "Company pending state verification failed.");

		logout();
		wexPartnerPage.deleteAllcookies();

		login("INVITE_CUSTOMER_EMAIL_WEP", "INVITE_CUSTOMER_PASSWORD_WEP");
		LOGGER.info("Sign in with customer to accept the partner invitation");
		leftSideMenuVerification();
		WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("AccountManagementTab");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("assignPartner");
		WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("assignPartner");
		WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("partnerInvitationAccept");
		WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("partnerInvitationAccept");
		WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("confirmUnassignPartnr");
		WEXPartnerDashboardPage.waitForElementsOnWEXPartnerDashboardPage("invitationSuccessToast");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("invitationSuccessToast"), "Invitation approve process failed");
		WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("AccountManagementTab");
		logout();
		wexPartnerPage.deleteAllcookies();

		login("NO_DATA_PARTNER_EMAIL_WEP", "NO_DATA_PARTNER_PASSWORD_WEP");
		LOGGER.info("Sign in with partner to verify the customer acceptance");
		wepPartnerCustomersPage.switchToPartnerDashboard();
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("customers");

		// clear all filters of customer page
		wepPartnerCustomersPage.clearAllFiltersOfWEPPartnerCustomersPage();
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("searchItem"), "Company search bar not available on UI.");
		;
		wexPartnerPage.mousehoverOnWEXPartnerPage("searchItem");
		Thread.sleep(2000);
		wexPartnerPage.clickByJavaScriptOnElementsOnPartnerPage("searchItem");
		wexPartnerPage.clickOnElementsOfwexPartnerPage("searchItem");
		wepPartnerCustomersPage.selecttextDropDownValueOfWEPPartnerPage("searchCompany", CompanyName, "listDropdown");
		Thread.sleep(3000);
		waitForPageLoaded();
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameValue", CompanyName), "Invited Customer Name is not matching with the filter customer name in the list");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("activeStatus"), "Company not in active state.");

		wexPartnerPage.clickOnElementsOfWEXPartnerPage("optionChoice");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("viewCustomerDashboad"), "Resend Invite option not available");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("viewCustomerProfile"), "view Customer Profile option not available");
		softAssert.assertTrue(wexPartnerPage.verifyElementsOfWEXPartnerPage("deleteCustomer"), "Delete Customer option not available");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("deleteCustomer");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("confirmDeleteComp");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("deleteConfirmToasts");

		softAssert.assertAll();
		LOGGER.info("Partner to customer invitation acceptance flow verified successfully");
	}

	/**
	 * This method will verify the active customer on the customer page
	 */
	@Test(priority = 4, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 44395131")
	public final void verifyActiveCustomerOnList() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
		LOGGER.info("Partner login verified successfully");
		waitForPageLoaded();

		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CustomerSideMenu", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers")), "Customers tab text is not matching");
		LOGGER.info("Customers tab text has been successfully verified.");

		softAssert.assertTrue(wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("CustomerSideMenu"), "Customers tab is not clickable");
		LOGGER.info("Navigated successfully to Customers page");

		// Verify the  customers table
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CustomerColumn", getTextLanguage(LanguageCode, "daas_ui", "partner.customerlist.table.header.customer")), "Customer Name column name is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("PlanColumn", getTextLanguage(LanguageCode, "daas_ui", "partner.customerlist.table.header.plan")), "Plan column name is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("StatusColumn", getTextLanguage(LanguageCode, "daas_ui", "partners.list.status")), "Status column name is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("ExperienceScoreColumn", getTextLanguage(LanguageCode, "daas_ui", "partner.customerlist.table.header.experiencescore")), "EndPoints column name is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("LocationColumn", getTextLanguage(LanguageCode, "daas_ui", "global.location")), "Location column name is not matching");

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);

		String[] ActiveCustomer = {"DNDELITE"};

		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("ActiveStatus", getTextLanguage(LanguageCode, "daas_ui", "partner.customerlist.table.status.account_activated")), "Active status text is not matching");
		softAssert.assertTrue(wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("CustomerSearch1"), "Customers search box is not clickable");
		LOGGER.info("Customers search box clicked");

		for (String isActive : ActiveCustomer) {
			wepPartnerCustomersPage.enterTextForWEPPartnerCustomersPage("CustomerSearch1", isActive);
			LOGGER.info("Entered search term: " + isActive);
			waitForPageLoaded();

			softAssert.assertTrue(wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("EliteCustomers"), "Cannot Find DNDElite Customer");
			LOGGER.info("Elite customer identified on the Customer List page.");
			waitForPageLoaded();
			softAssert.assertAll();
		}
	}
	/** This will test something that will test Customers tab details
	 *
	 */
	@Test(priority = 5, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 4439513")
	public final void verifyCustomerAndInviteesTabOnPartnerPage() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");

        detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
		LOGGER.info("Partner login verified successfully");
		waitForPageLoaded();

		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CustomerSideMenu", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers")), "Customers tab text is not matching");
		LOGGER.info("Customers tab text has been successfully verified.");

		softAssert.assertTrue(wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("CustomerSideMenu"), "Customers tab is not clickable");
		LOGGER.info("Navigated successfully to Customers page");

		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerTextOnCustomerTab", getTextLanguage(LanguageCode, "daas_ui", "partner.customerlist.tabname.customers")), "Customers tab text is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CustomerSubject", getTextLanguage(LanguageCode, "daas_ui", "partner.customerlist.tabname.customers")), "Customers tab text is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("Invitees", getTextLanguage(LanguageCode, "daas_ui", "partner.customerlist.tabname.invites")), "Invitees Subtab text is not matching");
		softAssert.assertTrue(wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("CustomerSubject"), "Customers tab is not clickable");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("Invitees", getTextLanguage(LanguageCode, "daas_ui", "partner.customerlist.tabname.invites")), "Invitees Subtab text is not matching");

		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("AddButtonCustomers", getTextLanguage(LanguageCode, "daas_ui", "contentAddForm.add.label")), "Add text is not matching");
		softAssert.assertTrue(wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("AddButtonCustomers"), "Customers tab is not clickable");

		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CompanyName", getTextLanguage(LanguageCode, "daas_ui", "global.form.company_name")), "Company Name text is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("AddCustomerModal", getTextLanguage(LanguageCode, "daas_ui", "partner.customerlist.activecustomers.empty.state.buttontext")), "Add Customer text is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("Step1", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step1")), "step1 text is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("Addressline1", getTextLanguage(LanguageCode, "daas_ui", "global.address1")), "address 1 text is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("ZipCodee", getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.zip-code")), "Zip Code text is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("RegionLabel", getTextLanguage(LanguageCode, "daas_ui", "subscription.list.state")), "Region Label text is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CityLabel", getTextLanguage(LanguageCode, "daas_ui", "registration.partners.details.information.label.city")), "City Label text is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("Addressline2", getTextLanguage(LanguageCode, "daas_ui", "users.details.address2")), "address 2 text is not matching");

		softAssert.assertTrue(wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("CancelPopup"), "Cancel button is not clickable");
		softAssert.assertTrue(wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("Invitees"), "Invitees tab is not clickable");

		softAssert.assertTrue(wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("InviteButton"), "Invitees Button is not clickable");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SendInviteLabel", getTextLanguage(LanguageCode, "daas_ui", "customer.add.invitepopup.title")), "Invite text is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("InviteesDescription", getTextLanguage(LanguageCode, "daas_ui", "customer.add.invitepopup.desc")), "Invitee Description text is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CustomerIDInput", getTextLanguage(LanguageCode, "daas_ui", "create_company.send.invitation.label")), "Customer ID Input text is not matching");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("ModalInviteButton", getTextLanguage(LanguageCode, "daas_ui", "global.invite")), "Modal Invite Button text is not matching");

		softAssert.assertTrue(wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("ModalCancelInvite"), "Cancel button is not clickable");

		softAssert.assertAll();

		LOGGER.info("verifyCustomerTabOnPartnerPage test has been completed successfully");
	}

	/**
	 * This method will verify 'view customer profile' button on the customer page
	 */
	@Test(priority = 6, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 44395131", enabled = false)
	public final void verifyViewCustomerProfileButtonforActiveCustomer() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();

		// If the what's new popup is available, close it
		if(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

		leftSideMenuVerification();
		waitForPageLoaded();
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerCustomersPage("customerFilter");

		// If the All customer is not selected, select the All customer
		if (!wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
			wepPartnerCustomersPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
		}

		// Verify the All customer is selected
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerFilter", CommonVariables.ALL_CUSTOMER));
		wepPartnerCustomersPage.partnerView(CommonVariables.PARTNER_CUSTOMERS);
		waitForPageLoaded();

		// clear all filters
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("clearAllFilterButton")) {
			wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("clearAllFilterButton");
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("clearAllFilterButton");
			waitForPageLoaded();
			sleeper(5000);
		}
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage("experienceScoreColumn");
		String partnerCustomer = getEnvironmentSpecificData(System.getProperty("environment"), "UPDATE_LOCATION_CUSTOMER");
		wexPartnerPage.selectCustomerInPartner(partnerCustomer);
		waitForPageLoaded();
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage(partnerCustomer.toLowerCase().replace(" ", "-"));

		// Verify the customer is available in the list
		String customerNameOnList = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerNameValue");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameValue", partnerCustomer), "Search Customer Name is not matching with the filter customer name in the list");
		//Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerPlanValue", "Workforce Experience Pro"),"Workforce Experience Pro plan not matching " );
		String experienceScoreStr = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerExperienceScoreValue");
		int experienceScore = 0;
		if (experienceScoreStr != null) {
			experienceScore = experienceScoreStr.matches("\\d+") ? Integer.parseInt(experienceScoreStr) : 0;
		}
        Assert.assertTrue(experienceScore >= 0 && experienceScore <= 100, "Experience Score is not between 0 and 100");
		//String proEndpoints = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerProEndpointsValue");
		//if (proEndpoints == null || proEndpoints.isEmpty()) {
			//Assert.fail("Pro Endpoints is not available");
		//}
		String locationOnList = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerLocationValue");

		// click on active customer ellipsis button
		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("ellipsisButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("ellipsisButton");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("viewCustomerDashboardButton","View customer dashboard"),"View customer dashboard button is not present on ellipsis button" );
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("viewCustomerProfileButton", "View customer profile"),"View customer profile  is not present on ellipsis button " );
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("updateLocationButton", "Update Location"),"Update Location  is not present on ellipsis button " );
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("deleteCustomerButton", "Remove Customer"),"Delete Customer  is not present on ellipsis button " );

		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("viewCustomerProfileButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("viewCustomerProfileButton");
		waitForPageLoaded();

		// Verify the customer on customer details page
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameOnDetailsPage", customerNameOnList), "Customer Name is not matching on details page");
		softAssert.assertAll();
		LOGGER.info("verifyViewCustomerProfileButtonforActiveCustomer test has been completed successfully");
	}

	/**
	 * This method will  verify switch to customers dashboad from 'View customer dashboad' action button on the customer list page
	 */
	@Test(priority = 7, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 44395131", enabled = false)
	public final void verifyViewCustomerDashboadButtonforActiveCustomer() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
		wepPartnerCustomersPage.partnerView(CommonVariables.PARTNER_CUSTOMERS);
		waitForPageLoaded();

		// clear all filters
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("clearAllFilterButton")) {
			wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("clearAllFilterButton");
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("clearAllFilterButton");
			waitForPageLoaded();
			sleeper(5000);
		}
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage("experienceScoreColumn");
		String partnerCustomer = getEnvironmentSpecificData(System.getProperty("environment"), "HAS_LOCATION_CUSTOMER_NAME");
		wexPartnerPage.selectCustomerInPartner(partnerCustomer);
		waitForPageLoaded();

		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage(partnerCustomer.toLowerCase().replace(" ", "-"));

		// Verify the customer is available in the list
		String customerNameOnList = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerNameValue");
		LOGGER.info("customer name on list: "+ customerNameOnList);
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameValue", partnerCustomer), "Search Customer Name is not matching with the filter customer name in the list");
		//String planNameOnList = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerPlanValue");
		String customerStatusOnList = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("statusValue");
		String locationOnList = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerLocationValue");

		// click on active customer ellipsis button
		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("ellipsisButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("ellipsisButton");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("viewCustomerDashboardButton","View customer dashboard"),"View customer dashboad button is not present on ellipsis button" );
		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("viewCustomerDashboardButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("viewCustomerDashboardButton");
		waitForPageLoaded();
		dashboardPage.waitUntilElementIsInvisibleOfDashboardPage("accountManagement");


		// Verify the customer on customer details page
		dashboardPage.clickOnElementsOfDashboardPage("accountManagement");
		waitForPageLoaded();
		// Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameOnAccountManagement", customerNameOnList, "wex_automation_customer1", customerNameOnList.toLowerCase() ), "Customer Name is not matching on customer details page");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameOnAccountManagementPage", customerNameOnList), "Customer Name is not matching on customer Account Management page");
		// Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerStatusOnCustomerPage", customerStatusOnList), "Status is not active");
		wepPartnerCustomersPage.scrollOnWEPPartnerCustomersPage("cityOnCustomerPage");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("cityOnCustomerPage", locationOnList), "Location is not matching on details page");

		softAssert.assertAll();
		LOGGER.info("verify ViewCustomerDashboad Button for Active Customer test has been completed successfully");
	}

	/**
	 * This method will  verify switch to customers dashboad from customer filter dropdown option from the partner dashboard page
	 */
	@Test(priority = 8, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 44395131", enabled = false)
	public final void verifySwitchToActiveCustomerFilterFromDashboad() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
		wepPartnerCustomersPage.partnerView(CommonVariables.PARTNER_CUSTOMERS);
		waitForPageLoaded();

		// clear all filters
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("clearAllFilterButton")) {
			wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("clearAllFilterButton");
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("clearAllFilterButton");
			waitForPageLoaded();
			sleeper(5000);
		}
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage("experienceScoreColumn");
		String partnerCustomer = getEnvironmentSpecificData(System.getProperty("environment"), "HAS_LOCATION_CUSTOMER_NAME");
		wexPartnerPage.selectCustomerInPartner(partnerCustomer);
		waitForPageLoaded();

		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage(partnerCustomer.toLowerCase().replace(" ", "-"));
		// Verify the customer is available in the list
		leftSideMenuVerification();
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameValue", partnerCustomer), "Search Customer Name is not matching with the filter customer name in the list");
		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("customerFilter");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("customerFilter");
		wepPartnerCustomersPage.enterTextWithoutClearForWEPPartnerPage("searchBox", partnerCustomer);
		sleeper(2000);
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("CustomerSelected");
		sleeper(2000);
		waitForPageLoaded();

		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("AccountManagementTab");
		waitForPageLoaded();
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameOnAccountManagement", partnerCustomer, "wex_automation_customer1", partnerCustomer.toLowerCase().replace(" ", "-") ), "Customer Name is not matching on customer Account Management page");

		softAssert.assertAll();
		LOGGER.info("verify Switch To Active Customer Filter From Dashboad  test has been completed successfully");


	}

	/**
	 * This method will verify the update customer name on customer details page
	 */
	@Test(priority = 9, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 44395131", enabled = false)
	public final void verifyUpdateCustomerNameFromActiveCustomerDetailPage() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();

		// If the what's new popup is available, close it
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

		leftSideMenuVerification();
		waitForPageLoaded();
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerCustomersPage("customerFilter");

		// If the All customer is not selected, select the All customer
		if (!wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
			wepPartnerCustomersPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
		}

		// Verify the All customer is selected
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerFilter", CommonVariables.ALL_CUSTOMER));
		wepPartnerCustomersPage.partnerView(CommonVariables.PARTNER_CUSTOMERS);
		waitForPageLoaded();

		// Clear all filters
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("clearAllFilterButton")) {
			wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("clearAllFilterButton");
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("clearAllFilterButton");
			waitForPageLoaded();
			sleeper(5000);
		}
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage("experienceScoreColumn");

		String partnerCustomer = getEnvironmentSpecificData(System.getProperty("environment"), "WEXCOMPANY_ACTIVE");
		wepPartnerCustomersPage.openDropDownOfWEPPartnerCustomersPage("customerNameFilterDropdown");
		wepPartnerCustomersPage.selectDropDownValueOfWEPPartnerPage("customerNameSearchBox", partnerCustomer, "SelectCustomerName");
		waitForPageLoaded();
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage(partnerCustomer.toLowerCase().replace(" ", "-"));

		// Verify the customer is available in the list
		String customerNameOnList = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerNameValue");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameValue", partnerCustomer), "Search Customer Name is not matching with the filter customer name in the list");

		// Click on active customer ellipsis button
		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("ellipsisButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("ellipsisButton");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("viewCustomerDashboardButton", "View customer dashboard"), "View customer dashboard button is not present on ellipsis button");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("viewCustomerProfileButton", "View customer profile"), "View customer profile is not present on ellipsis button");

		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("viewCustomerProfileButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("viewCustomerProfileButton");
		waitForPageLoaded();
		LOGGER.info("Redirected to customer details Overview page");

		// Verify the customer on customer details page
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameOnDetailsPage", customerNameOnList), "Customer Name is not matching on details page");
		//softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("deleteCustomerButtonOnDetailsPage", getTextLanguage(LanguageCode, "daas_ui", "global.remove")), "Delete button is not present on customer details page");

		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("overviewTab", getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.overview_title")), "Overview is incorrect");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("stateValueOnDetailsPage", getTextLanguage(LanguageCode, "daas_ui", "premier.care.case.status.active")), "Active status is not showing");

		//Test Case 1 - This test case validates company name edit functionality
		String oldCustomerName = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerNameVal");
		LOGGER.info("Old Partner Name is : " + oldCustomerName);
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("companyProfileheader", getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.company_profile_title")), "Company profile title is incorrect");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerId", getTextLanguage(LanguageCode, "daas_ui", "workflowOnboarding.company.id")), "Customer ID is incorrect");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("companySize", getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.number_of_employees")), "CustomerReferenceID is incorrect");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CustomerName", getTextLanguage(LanguageCode, "daas_ui", "global.form.company_name")), "CompanyName is not present");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("createdOn", getTextLanguage(LanguageCode, "daas_ui", "incidents.createdOn")), "companycreatedon is not present");

		softAssert.assertTrue(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("customerIdValue"), "Customer ID value is not present");
		softAssert.assertTrue(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("companySizeVal"), "Company Size value is not present");
		softAssert.assertTrue(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("customerIdValue"), "Customer Name value is not present");
		softAssert.assertTrue(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("createdOnVal"), "Created On value is not present");

		String wexCustomer = "WEPAutomationNewActiveCustomer-" + generateRandomNumber(1,100);

		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("editComanyNameButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("editComanyNameButton");
		wepPartnerCustomersPage.clickByJavaScriptOnWEXPartnerCustomesPage("editComanyNameButton");
		LOGGER.info("Clicked on edit Company name button");
		sleeper(2000);
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CompanyNameOnPopUp", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.name")), "CompanyNameOnPopUp is incorrect");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CompanyNameLabelOnPopUP", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.name")), "CompanyNameLabelOnPopUP is incorrect");
		wepPartnerCustomersPage.enterTextForWEPPartnerCustomersPage("CompanyNameTextBox", wexCustomer);
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("SubmitButton"), "Submit button is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("cancelName"), "Cancel button is not present");
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("cancelName");
		LOGGER.info("Clicked on cancel button");
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("companyProfileEdit");
		LOGGER.info("Clicked on edit name button");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("CompanyNameTextBox", wexCustomer);
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("SubmitButton");
		LOGGER.info("Clicked on Submit button");
		WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("toastNotification");
		//softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("toastNotification"), "Toast notification is not generated after changing company Name of Partner");
		String newCustomerName = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("partnerNameIdentity");
		LOGGER.info("New Partner Name is : " + newCustomerName);
		softAssert.assertFalse(oldCustomerName.equalsIgnoreCase(newCustomerName), "Customer name is not updated");

		softAssert.assertAll();
		LOGGER.info("Validation of update Company Name is completed successfully");
	}

	/**
	 * This method will verify the update of Primary ITAdmin of Active Customer On Detail Page
	 */
	@Test(priority = 10, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 44395131", enabled = false)
	public final void verifyUpdateOfPrimaryITAdminofActiveCustomerOnDetailPage() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
		wepPartnerCustomersPage.partnerView(CommonVariables.PARTNER_CUSTOMERS);
		waitForPageLoaded();

		// Clear all filters
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("clearAllFilterButton")) {
			wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("clearAllFilterButton");
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("clearAllFilterButton");
			waitForPageLoaded();
			sleeper(5000);
		}
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage("experienceScoreColumn");

		String partnerCustomer = getEnvironmentSpecificData(System.getProperty("environment"), "WEXCOMPANY_ACTIVE");
		wepPartnerCustomersPage.openDropDownOfWEPPartnerCustomersPage("customerNameFilterDropdown");
		wepPartnerCustomersPage.selectDropDownValueOfWEPPartnerPage("customerNameSearchBox", partnerCustomer, "SelectCustomerName");
		waitForPageLoaded();
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage(partnerCustomer.toLowerCase().replace(" ", "-"));

		// Verify the customer is available in the list
		String customerNameOnList = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerNameValue");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameValue", partnerCustomer), "Search Customer Name is not matching with the filter customer name in the list");

		// Click on active customer ellipsis button
		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("ellipsisButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("ellipsisButton");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("viewCustomerProfileButton", "View customer profile"), "View customer profile is not present on ellipsis button");

		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("viewCustomerProfileButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("viewCustomerProfileButton");
		waitForPageLoaded();
		LOGGER.info("Redirected to customer details Overview page");

		// Verify the customer on customer details page
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameOnDetailsPage", customerNameOnList), "Customer Name is not matching on details page");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("removeCustomerButtonOnDetailsPage", getTextLanguage(LanguageCode, "daas_ui", "global.remove_label")), "Remove button is not present on customer details page");

		WEXPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("primaryAdministratorHeader");
		String primaryAdministratorHeader = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("primaryAdministratorHeader");
		String primaryAdministratorHeaderMestro = getTextLanguage(LanguageCode, "daas_ui", "partners.list.primary_admin");
		softAssert.assertTrue(primaryAdministratorHeader.equalsIgnoreCase(primaryAdministratorHeaderMestro), "Primary Adminstrator name header on label is incorrect");
		LOGGER.info("Primary Administrator Header is : " + primaryAdministratorHeader);
		LOGGER.info("Primary Administrator Header from properties file is : " + primaryAdministratorHeaderMestro);
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("primaryAdminNameLabel", getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.name")), "Primary Adminstrator name on label is incorrect");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Adminemail", getTextLanguage(LanguageCode, "daas_ui", "settings.email.label")), "Primary Adminstrator email on label is incorrect");
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("primaryadministratoreditbutton");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("primaryadministratorheaderpopup", getTextLanguage(LanguageCode, "daas_ui", "partners.list.primary_admin")), "Primary Administrator Header on pop up is incorrect");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("primaryadministratorselectboxLabel", getTextLanguage(LanguageCode, "daas_ui", "partners.list.primary_admin")), "Primary Administrator label on pop up is incorrect");
		WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("primaryadministratorselectbox");
		WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("primaryadministratorsavebutton");
		WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("primaryadministratorcancelbutton");
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("primaryadministratorcancelbutton");
		LOGGER.info("Validation for primary Administrator  popup and cancel button are completed");

		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("primaryadministratoreditbutton");
		String currentPrimaryAdministrator = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("currentPrimaryAdmin");
		WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
		wexPartnerPage.openDropDownOfWEXPartnerPage("primaryadministratorselectbox");
		LOGGER.info("Clicked on Primary Administrator dropdown");

		List<WebElement> primaryAdminList = WEXPartnerDashboardPage.getWebelementsOfWEXPartnerDashboardPage("partnerAdminUserlist");
		LOGGER.info("Total Primary Admins are : " + primaryAdminList.size());

		// select new primary admin from the list of primary admins
		String newPrimaryAdmin = null;
		for(WebElement primaryAdmin : primaryAdminList) {
			newPrimaryAdmin = primaryAdmin.getText();
			LOGGER.info("New Primary Admin Name is :: " + newPrimaryAdmin);
			if(!newPrimaryAdmin.equalsIgnoreCase(currentPrimaryAdministrator)) {
				LOGGER.info("Primary Admin Name is : " + newPrimaryAdmin);
				primaryAdmin.click();
				break;
			}
		}
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("primaryadministratorsavebutton");
		WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("primaryAdminToastNotification");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("primaryAdminToastNotification"), "Toast notification is not generated after changing time zone of Partner");

		WEXPartnerDashboardPage.pageRefreshForWEXPartnerDashboardPage();
		WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("primaryAdminName");
		WEXPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("primaryAdminName");
		String newPrimaryAdminName = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("primaryAdminName");
		LOGGER.info("New Primary Admin Name is : " + newPrimaryAdmin);
		LOGGER.info("Current Primary Admin Name is : " + newPrimaryAdminName);
		assert newPrimaryAdminName != null;
		softAssert.assertTrue(newPrimaryAdminName.trim().equalsIgnoreCase(newPrimaryAdmin.trim()), "Primary Admin Name is not updated");
		softAssert.assertAll();
		LOGGER.info("Validation for Primary Administrator section is completed");
	}

	/**
	 * This method will verify the update customer address of active customer on customer details page
	 */
	@Test(priority = 11, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 44395131", enabled = false)
	public final void verifyUpdateCustomerAddressOfActiveCustomerOnDetailPage() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		String country, address1, address2, state, city, zipcode;
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
		wepPartnerCustomersPage.partnerView(CommonVariables.PARTNER_CUSTOMERS);
		waitForPageLoaded();

		// Clear all filters
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("clearAllFilterButton")) {
			wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("clearAllFilterButton");
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("clearAllFilterButton");
			waitForPageLoaded();
			sleeper(5000);
		}
		wepPartnerCustomersPage.waitUntillElementIsPresentOnWEPPartnerCustomersPage("experienceScoreColumn");

		String partnerCustomer = getEnvironmentSpecificData(System.getProperty("environment"), "WEXCOMPANY_ACTIVE");
		wepPartnerCustomersPage.openDropDownOfWEPPartnerCustomersPage("customerNameFilterDropdown");
		wepPartnerCustomersPage.selectDropDownValueOfWEPPartnerPage("customerNameSearchBox", partnerCustomer, "SelectCustomerName");
		waitForPageLoaded();
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage(partnerCustomer.toLowerCase().replace(" ", "-"));

		// Verify the customer is available in the list
		String customerNameOnList = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerNameValue");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameValue", partnerCustomer), "Search Customer Name is not matching with the filter customer name in the list");

		// Click on active customer ellipsis button
		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("ellipsisButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("ellipsisButton");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("viewCustomerProfileButton", "View customer profile"), "View customer profile is not present on ellipsis button");

		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("viewCustomerProfileButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("viewCustomerProfileButton");
		waitForPageLoaded();
		LOGGER.info("Redirected to customer details Overview page");

		//Test Case  - This test case validates partner address edit functionality
		WEXPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("companyAddressHeader");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("companyAddressHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "company.details.company_address")), "Company Address header is incorrect");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("countrylabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "create_company.msp.country")), "Country field for partner is not present on details tile");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("address-line-1label").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.address1")), "Address line 1 field for partner is not present on details tile");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("address-line-2label").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.address2")), "Address line 2 field for partner is not present on details tile");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("statelabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.state")), "State field for partner is not present on details tile");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("citylabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.city")), "City field for partner is not present on details tile");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("zipcodelabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "create_company.msp.zip_code")), "Zip code field for partner is not present on details tile");

		country = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("currentAddressCountry");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("currentAddressCountry"), "countrylabel is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addressEditButton"), "Address Edit button is not clickable");
		LOGGER.info("Clicked on Address edit button");

		// Verify the address fields are disabled
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("addressCountrySelectBox"), "Country field is not present on company address pop up");
		String countryIsDisabled = WEXPartnerDashboardPage.getAttributesOfWEXPartnerDashboardPage("addressCountrySelectBox", "aria-disabled");
		softAssert.assertTrue(countryIsDisabled.equalsIgnoreCase("true"), "Country field is not disabled on company address pop up");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("countrylabelonpop"), "countrylabel is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("AddressLine1labelonpop"), "AddressLine1labelonpop is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("AddressLine2labelonpop"), "AddressLine2labelonpop is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("citylabelonpop"), "citylabelonpop is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("statelabelonpop"), "statelabelonpop is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("ziplabelonpop"), "ziplabelonpop is not present");

		address1 = "Bank of ";
		address2 = WEXPartnerDashboardPage.generateRandomString(11);
		state = WEXPartnerDashboardPage.generateRandomString(11);
		city = WEXPartnerDashboardPage.generateRandomString(11);
		zipcode = WEXPartnerDashboardPage.generateRandomNumber();
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("addressPopupHeader", getTextLanguage(LanguageCode, "daas_ui", "global.address")), "Header on address popup of Partner is incorrect");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("addressStreetLine1TextBox", address1);
		LOGGER.info("Entered Address 1");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("addressStreetLine2TextBox", address2);
		LOGGER.info("Entered Address 2");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("cityTextBox", city);
		LOGGER.info("Entered City");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("stateTextBox", state);
		LOGGER.info("Entered State");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("zipCodeTextBox", zipcode);
		LOGGER.info("Entered Zip Code");
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addressCancelButton");
		LOGGER.info("Clicked cancel button");
		// verify popup is closed once cancel button is clicked
		//softAssert.assertFalse(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("addressPopupHeader"), "Address pop up header is not present!!");
		softAssert.assertFalse(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("companyAddressHeader").equals("Address"), "Cancel functionality on address popup is not working");
		sleeper(2000);
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addressEditButton");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("addressStreetLine1TextBox", address1);

		List<WebElement> addressList = WEXPartnerDashboardPage.getWebelementsOfWEXPartnerDashboardPage("partnerAddressStreetLine1Dropdown");
		LOGGER.info("Total Address are : " + addressList.size());

		// select ramdom new address from the list of address
		Random random = new Random();
		int randomIndex = random.nextInt(addressList.size());
		//addressList.get(randomIndex).click();
		sleeper(2000);
		LOGGER.info("Clicked on new address");

		address1 = WEXPartnerDashboardPage.getAttributesOfWEXPartnerDashboardPage("addressStreetLine1TextBox", "value");
		if(address1 == "" || address1 == null || address1.contains("#") || address1.contains("&") || address1.contains(";")) {
			address1 = "address1";
			WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("addressStreetLine1TextBox", address1);
		}
		LOGGER.info("New Address 1 is : " + address1);
		address2 = WEXPartnerDashboardPage.getAttributesOfWEXPartnerDashboardPage("addressStreetLine2TextBox", "value");
		if(address2 == "" || address2 == null) {
			address2 = "-";
		}
		LOGGER.info("New Address 2 is : " + address2);
		state = WEXPartnerDashboardPage.getAttributesOfWEXPartnerDashboardPage("stateTextBox", "value");
		if (state == "" || state == null || state.contains("#") || state.contains("&") || state.contains(";")) {
			state = "state";
			WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("stateTextBox", state);
		}
		LOGGER.info("New State is : " + state);
		city = WEXPartnerDashboardPage.getAttributesOfWEXPartnerDashboardPage("cityTextBox", "value");
		if (city == "" || city == null || city.contains("#") || city.contains("&") || city.contains(";")) {
			city = "city";
			WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("cityTextBox", city);
		}
		LOGGER.info("New City is : " + city);
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("zipCodeTextBox", zipcode);
		zipcode = WEXPartnerDashboardPage.getAttributesOfWEXPartnerDashboardPage("zipCodeTextBox", "value");
		LOGGER.info("New Zip Code is : " + zipcode);

		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addressSaveButton");
		LOGGER.info("Clicked save button of address pop up to update address");
		WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("toastNotificationadd");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("toastNotificationadd"), "Toast notification is not generated after changing address of Partner");

		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerPage("newAddressCountry", country, "new"), "New Address 1 is not updated");
		//softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerPage("newAddressStreetLine1", address1, "new"), "New Address 1 is not updated");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerPage("newAddressStreetLine2", address2, "new"), "New Address 2 is not updated");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerPage("newCity", city, "new"), "New City is not updated");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerPage("newState", state, "new"), "New State is not updated");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerPage("newZipCode", zipcode, "new"), "New Zip Code is not updated");

		softAssert.assertAll();
		LOGGER.info("Validation for Company(Partner) Address section is completed");
	}


	/************************************** PENDING INVITE CUSTOMER ************************************************/

	/**
	 * This method will verify the update customer name of pending invite customer on customer details page
	 */
	@Test(priority = 12, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 44395131", enabled = false)
	public final void verifyUpdatePendingInviteCustomerNameFromCustomerDetailPage() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
		wepPartnerCustomersPage.partnerView(CommonVariables.PARTNER_CUSTOMERS);
		waitForPageLoaded();

		// Clear all filters
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("clearAllFilterButton")) {
			wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("clearAllFilterButton");
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("clearAllFilterButton");
			waitForPageLoaded();
			sleeper(5000);
		}
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage("experienceScoreColumn");

		String partnerCustomer = getEnvironmentSpecificData(System.getProperty("environment"), "PENDING_INVITE_CUSTOMER_NAME");
		wepPartnerCustomersPage.openDropDownOfWEPPartnerCustomersPage("customerNameFilterDropdown");
		wepPartnerCustomersPage.selectDropDownValueOfWEPPartnerPage("customerNameSearchBox", partnerCustomer, "SelectCustomerName");
		waitForPageLoaded();
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage(partnerCustomer.toLowerCase().replace(" ", "-"));

		// Verify the customer is available in the list
		String customerNameOnList = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerNameValue");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameValue", partnerCustomer), "Search Customer Name is not matching with the filter customer name in the list");

		// Click on active customer ellipsis button
		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("ellipsisButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("ellipsisButton");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("viewCustomerProfileButton", "View customer profile"), "View customer profile is not present on ellipsis button");

		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("viewCustomerProfileButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("viewCustomerProfileButton");
		waitForPageLoaded();
		LOGGER.info("Redirected to customer details Overview page");

		// Verify the customer on customer details page
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameOnDetailsPage", customerNameOnList), "Customer Name is not matching on details page");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("resendInviteButtonOnDetailsPage", getTextLanguage(LanguageCode, "daas_ui", "partner.resend.invite.label")), "Re-send invite button is not present on customer details page");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("stateValueOnDetailsPage", getTextLanguage(LanguageCode, "daas_ui", "premier.care.case.status.pending")), "Pending status is not showing");

		//Test Case 1 - This test case validates company name edit functionality
		String oldCustomerName = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerNameVal");
		LOGGER.info("Old Pending Invite Customer Name is : " + oldCustomerName);
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("companyProfileheader", getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.company_profile_title")), "Company profile title is incorrect");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerId", getTextLanguage(LanguageCode, "daas_ui", "workflowOnboarding.company.id")), "Customer ID is incorrect");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("companySize", getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.number_of_employees")), "CustomerReferenceID is incorrect");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CustomerName", getTextLanguage(LanguageCode, "daas_ui", "global.form.company_name")), "CompanyName is not present");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("createdOn", getTextLanguage(LanguageCode, "daas_ui", "incidents.createdOn")), "companycreatedon is not present");

		softAssert.assertTrue(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("customerIdValue"), "Customer ID value is not present");
		softAssert.assertTrue(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("companySizeVal"), "Company Size value is not present");
		softAssert.assertTrue(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("customerIdValue"), "Customer Name value is not present");
		softAssert.assertTrue(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("createdOnVal"), "Created On value is not present");

		String wexCustomer = "WEPAutomationPendingInviteCustomer-" + generateRandomNumber(1,100);

		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("editComanyNameButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("editComanyNameButton");
		wepPartnerCustomersPage.clickByJavaScriptOnWEXPartnerCustomesPage("editComanyNameButton");
		LOGGER.info("Clicked on edit Company name button");
		sleeper(2000);
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CompanyNameOnPopUp", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.name")), "CompanyNameOnPopUp is incorrect");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CompanyNameLabelOnPopUP", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.name")), "CompanyNameLabelOnPopUP is incorrect");
		wepPartnerCustomersPage.enterTextForWEPPartnerCustomersPage("CompanyNameTextBox", wexCustomer);
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("SubmitButton"), "Submit button is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("cancelName"), "Cancel button is not present");
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("cancelName");
		LOGGER.info("Clicked on cancel button");
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("companyProfileEdit");
		LOGGER.info("Clicked on edit name button");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("CompanyNameTextBox", wexCustomer);
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("SubmitButton");
		LOGGER.info("Clicked on Submit button");
		WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("toastNotification");
		//softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("toastNotification"), "Toast notification is not generated after changing company Name of Partner");
		String newCustomerName = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("partnerNameIdentity");
		LOGGER.info("New Partner Name is : " + newCustomerName);
		softAssert.assertFalse(oldCustomerName.equalsIgnoreCase(newCustomerName), "Customer name is not updated");

		softAssert.assertAll();
		LOGGER.info("Validation of update pending invite  customer Name is completed successfully");
	}

	/**
	 * This method will verify the update customer address of pending invite customer on customer details page
	 */
	@Test(priority = 13, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 44395131", enabled = false)
	public final void verifyUpdatePendingInviteCustomerAddressFromCustomerDetailPage() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		String country, address1, address2, state, city, zipcode;
		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
		wepPartnerCustomersPage.partnerView(CommonVariables.PARTNER_CUSTOMERS);
		waitForPageLoaded();

		// Clear all filters
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("clearAllFilterButton")) {
			wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("clearAllFilterButton");
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("clearAllFilterButton");
			waitForPageLoaded();
			sleeper(5000);
		}
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage("experienceScoreColumn");

		String partnerCustomer = getEnvironmentSpecificData(System.getProperty("environment"), "PENDING_INVITE_CUSTOMER_NAME");
		wepPartnerCustomersPage.openDropDownOfWEPPartnerCustomersPage("customerNameFilterDropdown");
		wepPartnerCustomersPage.selectDropDownValueOfWEPPartnerPage("customerNameSearchBox", partnerCustomer, "SelectCustomerName");
		waitForPageLoaded();
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage(partnerCustomer.toLowerCase().replace(" ", "-"));

		// Verify the customer is available in the list
		String customerNameOnList = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerNameValue");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameValue", partnerCustomer), "Search Customer Name is not matching with the filter customer name in the list");

		// Click on active customer ellipsis button
		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("ellipsisButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("ellipsisButton");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("viewCustomerProfileButton", "View customer profile"), "View customer profile is not present on ellipsis button");

		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("viewCustomerProfileButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("viewCustomerProfileButton");
		waitForPageLoaded();
		LOGGER.info("Redirected to customer details Overview page");

		//Test Case  - This test case validates partner address edit functionality
		WEXPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("companyAddressHeader");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("companyAddressHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "company.details.company_address")), "Company Address header is incorrect");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("countrylabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "create_company.msp.country")), "Country field for partner is not present on details tile");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("address-line-1label").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.address1")), "Address line 1 field for partner is not present on details tile");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("address-line-2label").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.address2")), "Address line 2 field for partner is not present on details tile");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("statelabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.state")), "State field for partner is not present on details tile");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("citylabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.city")), "City field for partner is not present on details tile");
		softAssert.assertTrue(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("zipcodelabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "create_company.msp.zip_code")), "Zip code field for partner is not present on details tile");

		country = WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("currentAddressCountry");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("currentAddressCountry"), "countrylabel is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addressEditButton"), "Address Edit button is not clickable");
		LOGGER.info("Clicked on Address edit button");

		// Verify the address fields are disabled
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("addressCountrySelectBox"), "Country field is not present on company address pop up");
		String countryIsDisabled = WEXPartnerDashboardPage.getAttributesOfWEXPartnerDashboardPage("addressCountrySelectBox", "aria-disabled");
		softAssert.assertTrue(countryIsDisabled.equalsIgnoreCase("true"), "Country field is not disabled on company address pop up");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("countrylabelonpop"), "countrylabel is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("AddressLine1labelonpop"), "AddressLine1labelonpop is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("AddressLine2labelonpop"), "AddressLine2labelonpop is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("citylabelonpop"), "citylabelonpop is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("statelabelonpop"), "statelabelonpop is not present");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("ziplabelonpop"), "ziplabelonpop is not present");

		address1 = "Bank of ";
		address2 = WEXPartnerDashboardPage.generateRandomString(11);
		state = WEXPartnerDashboardPage.generateRandomString(11);
		city = WEXPartnerDashboardPage.generateRandomString(11);
		zipcode = WEXPartnerDashboardPage.generateRandomNumber();
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("addressPopupHeader", getTextLanguage(LanguageCode, "daas_ui", "global.address")), "Header on address popup of Partner is incorrect");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("addressStreetLine1TextBox", address1);
		LOGGER.info("Entered Address 1");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("addressStreetLine2TextBox", address2);
		LOGGER.info("Entered Address 2");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("cityTextBox", city);
		LOGGER.info("Entered City");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("stateTextBox", state);
		LOGGER.info("Entered State");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("zipCodeTextBox", zipcode);
		LOGGER.info("Entered Zip Code");
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addressCancelButton");
		LOGGER.info("Clicked cancel button");
		// verify popup is closed once cancel button is clicked
		//softAssert.assertFalse(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("addressPopupHeader"), "Address pop up header is not present!!");
		softAssert.assertFalse(WEXPartnerDashboardPage.getTextOfWEXPartnerDashboardPage("companyAddressHeader").equals("Address"), "Cancel functionality on address popup is not working");
		sleeper(2000);
		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addressEditButton");
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("addressStreetLine1TextBox", address1);

		List<WebElement> addressList = WEXPartnerDashboardPage.getWebelementsOfWEXPartnerDashboardPage("partnerAddressStreetLine1Dropdown");
		LOGGER.info("Total Address are : " + addressList.size());

		// select ramdom new address from the list of address
		Random random = new Random();
		int randomIndex = random.nextInt(addressList.size());
		//addressList.get(randomIndex).click();
		sleeper(2000);
		LOGGER.info("Clicked on new address");

		address1 = WEXPartnerDashboardPage.getAttributesOfWEXPartnerDashboardPage("addressStreetLine1TextBox", "value");
		if(WEXPartnerDashboardPage.validateAddress(address1)) {
			address1 = "address1";
			WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("addressStreetLine1TextBox", address1);
		}
		LOGGER.info("New Address 1 is : " + address1);
		address2 = WEXPartnerDashboardPage.getAttributesOfWEXPartnerDashboardPage("addressStreetLine2TextBox", "value");
		if(WEXPartnerDashboardPage.validateAddress(address2)) {
			address2 = "-";
		}
		LOGGER.info("New Address 2 is : " + address2);
		state = WEXPartnerDashboardPage.getAttributesOfWEXPartnerDashboardPage("stateTextBox", "value");
		if (WEXPartnerDashboardPage.validateAddress(state)) {
			state = "state";
			WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("stateTextBox", state);
		}
		LOGGER.info("New State is : " + state);
		city = WEXPartnerDashboardPage.getAttributesOfWEXPartnerDashboardPage("cityTextBox", "value");
		if (WEXPartnerDashboardPage.validateAddress(city)) {
			city = "city";
			WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("cityTextBox", city);
		}
		LOGGER.info("New City is : " + city);
		WEXPartnerDashboardPage.enterTextForWEXPartnerDashboardPage("zipCodeTextBox", zipcode);
		zipcode = WEXPartnerDashboardPage.getAttributesOfWEXPartnerDashboardPage("zipCodeTextBox", "value");
		LOGGER.info("New Zip Code is : " + zipcode);

		WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addressSaveButton");
		LOGGER.info("Clicked save button of address pop up to update address");
		WEXPartnerDashboardPage.waitForElementsOfWEXPartnerDashboardPage("toastNotificationadd");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("toastNotificationadd"), "Toast notification is not generated after changing address of Partner");

		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerPage("newAddressCountry", country, "new"), "country 1 is not updated");
		//softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerPage("newAddressStreetLine1", address1, "new"), "New Address 1 is not updated");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerPage("newAddressStreetLine2", address2, "new"), "New Address 2 is not updated");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerPage("newCity", city, "new"), "New City is not updated");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerPage("newState", state, "new"), "New State is not updated");
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerPage("newZipCode", zipcode, "new"), "New Zip Code is not updated");

		softAssert.assertAll();
		LOGGER.info("Validation for Company(Partner) Address section is completed");
	}

	/************************************** PENDING REQUEST CUSTOMER ************************************************/
	/**
	 * This method will verify the update customer name of pending request customer on customer details page
	 */
	@Test(priority = 14, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 44395131", enabled = false)
	public final void verifyUpdatePendingRequestCustomerProfileFromCustomerDetailPage() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
		wepPartnerCustomersPage.partnerView(CommonVariables.PARTNER_CUSTOMERS);
		waitForPageLoaded();

		// Clear all filters
		if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("clearAllFilterButton")) {
			wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("clearAllFilterButton");
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("clearAllFilterButton");
			waitForPageLoaded();
			sleeper(5000);
		}
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage("experienceScoreColumn");

		String partnerCustomer = getEnvironmentSpecificData(System.getProperty("environment"), "PENDING_REQUEST_CUSTOMER");
		wepPartnerCustomersPage.openDropDownOfWEPPartnerCustomersPage("customerNameFilterDropdown");
		wepPartnerCustomersPage.selectDropDownValueOfWEPPartnerPage("customerNameSearchBox", partnerCustomer, "SelectCustomerName");
		waitForPageLoaded();
		wepPartnerCustomersPage.waitForElementsOfWEPPartnerPage(partnerCustomer.toLowerCase().replace(" ", "-"));

		// Verify the customer is available in the list
		String customerNameOnList = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerNameValue");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameValue", partnerCustomer), "Search Customer Name is not matching with the filter customer name in the list");

		// Click on active customer ellipsis button
		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("ellipsisButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("ellipsisButton");
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("viewCustomerProfileButton", "View customer profile"), "View customer profile is not present on ellipsis button");

		wepPartnerCustomersPage.mousehoverOnWEPPartnerCustomesPage("viewCustomerProfileButton");
		wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("viewCustomerProfileButton");
		waitForPageLoaded();
		LOGGER.info("Redirected to customer details Overview page");

		// Verify the customer on customer details page
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameOnDetailsPage", customerNameOnList), "Customer Name is not matching on details page");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("stateValueOnDetailsPage", getTextLanguage(LanguageCode, "daas_ui", "premier.care.case.status.pending")), "Active status is not showing");

		//Test Case 1 - This test case validates company name edit functionality
		String oldCustomerName = wepPartnerCustomersPage.getTextOfWEPPartnerCustomersPage("customerNameVal");
		LOGGER.info("Old Pending Request Customer Name is : " + oldCustomerName);
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("companyProfileheader", getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.company_profile_title")), "Company profile title is incorrect");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerId", getTextLanguage(LanguageCode, "daas_ui", "workflowOnboarding.company.id")), "Customer ID is incorrect");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("companySize", getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.number_of_employees")), "CustomerReferenceID is incorrect");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("CustomerName", getTextLanguage(LanguageCode, "daas_ui", "global.form.company_name")), "CompanyName is not present");
		softAssert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("createdOn", getTextLanguage(LanguageCode, "daas_ui", "incidents.createdOn")), "companycreatedon is not present");

		softAssert.assertTrue(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("customerIdValue"), "Customer ID value is not present");
		softAssert.assertTrue(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("companySizeVal"), "Company Size value is not present");
		softAssert.assertTrue(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("customerIdValue"), "Customer Name value is not present");
		softAssert.assertTrue(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("createdOnVal"), "Created On value is not present");
		softAssert.assertAll();
		LOGGER.info("Validation pending request customer profile is completed successfully");
	}
	
	/**
	 * This method will Verify customer to partner invitation acceptance
	 */

	@Test(priority = 15, groups = {"REGRESSION_PARTNERCX","REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : C43522604", enabled = true)
	public final void verifyCustomerToPartnerInvitationAcceptanceFirst() throws Exception {
		login("NO_DATA_PARTNER_EMAIL_WEP", "NO_DATA_PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();	
		// If the what's new popup is available, close it
		wepPartnerCustomersPage.clickIfPresent("whatsNewPopupClosedButton");
		LOGGER.info("Sign in with partner to get Partner Id and Partner Name for the Invite");
		leftSideMenuVerification();
		WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("AccountManagementTab");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("overview");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("customerID");
		String partnerID = wexPartnerPage.getTextOfWEXPartnerPage("customerID");
		String partnerName = wexPartnerPage.getTextOfWEXPartnerPage("companyValue");
		logout();
		wexPartnerPage.deleteAllcookies();
		
		login("INVITE_CUSTOMER_EMAIL_WEP", "INVITE_CUSTOMER_PASSWORD_WEP");
		wepPartnerCustomersPage.clickIfPresent("whatsNewPopupClosedButton");
		wepPartnerCustomersPage.clickIfPresent("invitationPopupModalTitle", "invitationPopupModalClose");
		leftSideMenuVerification();
		waitForPageLoaded();
		LOGGER.info("Sign in with customer to remove the partner mapping and invite partner through customer");
		WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("AccountManagementTab");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("overview");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("customerID");
		String customerID = wexPartnerPage.getTextOfWEXPartnerPage("customerID");
		String CompanyName = wexPartnerPage.getTextOfWEXPartnerPage("companyValue");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("assignPartner");
		WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("assignPartner");

		if(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("unassignPartnrBtn")) {
			WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("unassignSettingsPartnrBtn");
			WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("unassignPartnrBtn");
			WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("confirmUnassignPartnr");
			WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("confirmUnassignPartnr");
			waitForPageLoaded();
			sleeper(2000);
			softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("unassignedPartnerToast"), "Partner was not updated");
			LOGGER.info("Available partner deleted.");
		} else {
			LOGGER.info("Already partner not available. Proceed with standard invite partner procedure");
		}
		waitForPageLoaded();
		if(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("declinePartnerInvite")) {
			WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("declinePartnerInvite");
			WEXPartnerDashboardPage.actionClickOnWEXPartnerDashboardPage("confirmDeclineRequest");
			WEXPartnerDashboardPage.waitForElementsOnWEXPartnerDashboardPage("invitationSuccessToast");
			softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("invitationSuccessToast"), "Invitation Decline process failed");
			LOGGER.info("Available Request Declined.");
		} else {
			LOGGER.info("Already Request not available. Proceed with standard invite partner procedure");
		}
		sleeper(3000);
		wexPartnerPage.refreshPageOfWEXPartnerPage();
		wexPartnerPage.verifyElementsOfWEXPartnerPage("invitePartner");
		wexPartnerPage.actionClickOnWEXPartnerPage("invitePartner");
		sleeper(2000);
		wexPartnerPage.mousehoverOnWEXPartnerPage("searchPartner");
		wexPartnerPage.clickOnElementsOfwexPartnerPage("searchPartner");
		wexPartnerPage.clickOnElementsOfwexPartnerPage("searchPartnerDropdown");
		sleeper(2000);
		wexPartnerPage.enterTextForWEXPartnerPage("searchPartnerDropdown", partnerName);
		sleeper(2000);
		wexPartnerPage.clickOnElementsOfwexPartnerPage("valuePartner");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("submitInvitePartner");
		wexPartnerPage.clickOnElementsOfwexPartnerPage("submitInvitePartner");
		waitForPageLoaded();
		sleeper(10000);
		WEXPartnerDashboardPage.waitForElementsOnWEXPartnerDashboardPage("invitationSuccessToast");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("invitationSuccessToast"), "Invitation sent process failed");
		WEXPartnerDashboardPage.waitForElementsOnWEXPartnerDashboardPage("declinePartnerInvite");
		softAssert.assertTrue(WEXPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("declinePartnerInvite"), "Invitation sent process failed");
		LOGGER.info("Invitation sent successfully");
		wexPartnerPage.refreshPageOfWEXPartnerPage();
		waitForPageLoaded();
		
		softAssert.assertAll();
		LOGGER.info("Customer to partner invitation acceptance flow verified successfully");
	}
	/**
	 * This method will Verify invite acceptance by Partner
	 */

	@Test(priority = 16, groups = {"REGRESSION_PARTNERCX","REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : C43522604", enabled = false)
	public final void verifyCustomerToPartnerInvitationAcceptanceSecond() throws Exception {
		login("NO_DATA_PARTNER_EMAIL_WEP", "NO_DATA_PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();	
		
		// If the what's new popup is available, close it
		if(wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
			wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");
		
		LOGGER.info("Signed in with partner to accept invite of the customer");
		String CompanyName = getEnvironmentSpecificData(System.getProperty("environment"), "INVITE_CUSTOMER_EMAIL_WEP");
		wepPartnerCustomersPage.switchToPartnerDashboard();
		detectAndUpdateUILanguageCode(wepPartnerCustomersPage);
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("customers");
		sleeper(2000);
        wexPartnerPage.clickOnElementsOfWEXPartnerPage("invitesTab");
        waitForPageLoaded();
        sleeper(2000);
        wexPartnerPage.enterTextForWEXPartnerPage("inviteSearchBox", CompanyName);
        sleeper(2000);
        wexPartnerPage.mousehoverOnWEXPartnerPage("inviteSearchElement");
        wexPartnerPage.clickOnElementsOfWEXPartnerPage("threeDotElementInvites");
        waitForPageLoaded();
        sleeper(2000);
		Assert.assertTrue(wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("customerNameValue", CompanyName), "Invited Customer Name is not matching with the filtered customer name in the list");
		softAssert.assertTrue(wexPartnerPage.verifyElementsVisibilityOfWEXPartnerPage("pendingRequestStatus",50), "Company pending request state verification failed.");

		softAssert.assertTrue(wexPartnerPage.verifyElementsVisibilityOfWEXPartnerPage("acceptRequest",50), "Accept Request option not available");
		softAssert.assertTrue(wexPartnerPage.verifyElementsVisibilityOfWEXPartnerPage("viewCustomerProfile",50), "view Customer Profile option not available");
		softAssert.assertTrue(wexPartnerPage.verifyElementsVisibilityOfWEXPartnerPage("declineCustomer",50), "Decline request of Customer option not available");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("acceptRequest");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("confirmAcceptRequest");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("confirmAcceptRequest");
		sleeper(3000);
		waitForPageLoaded();
		wexPartnerPage.waitForElementsOnWEXPartnerPage("toastNotification");
		softAssert.assertTrue(wexPartnerPage.waitForElementsOnWEXPartnerPage("toastNotification"), "Invitation approve process failed");
		LOGGER.info("Invitation Request accepted by Partner");
		wexPartnerPage.refreshPageOfWEXPartnerPage();
		waitForPageLoaded();
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("customersTab");
		waitForPageLoaded();
		sleeper(2000);
		wexPartnerPage.enterTextForWEXPartnerPage("inviteSearchBox", CompanyName);
		sleeper(2000);
		Assert.assertTrue(wexPartnerPage.matchTextOfWEXPartnerPage("activeStatusNew", "Active"), "Company not in active state.");
		wexPartnerPage.mousehoverOnWEXPartnerPage("customersSearchElement");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("threeDotElementCustomers");
		waitForPageLoaded();
		sleeper(2000);
		softAssert.assertTrue(wexPartnerPage.verifyElementsVisibilityOfWEXPartnerPage("viewProfile",50), "View Profile option not available");
		softAssert.assertTrue(wexPartnerPage.verifyElementsVisibilityOfWEXPartnerPage("navToDashboard",50), "Navigate to Dashboard option not available");
		softAssert.assertTrue(wexPartnerPage.verifyElementsVisibilityOfWEXPartnerPage("removeCustomer",50), "Remove Customer option not available");
		sleeper(3000);
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("removeCustomer");
		wexPartnerPage.clickOnElementsOfWEXPartnerPage("confirmDeleteComp");
		wexPartnerPage.verifyElementsOfWEXPartnerPage("toastNotification");
		sleeper(3000);
		LOGGER.info("Active Customer Deleted");
		softAssert.assertAll();
		LOGGER.info("Customer to partner invitation acceptance flow verified successfully");
	}
}