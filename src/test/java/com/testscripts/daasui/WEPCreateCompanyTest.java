package com.testscripts.daasui;

import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.WEPCustomerVariables;

import java.util.Objects;


public class WEPCreateCompanyTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXCustomerUserTest.class);
	private String companyFullName = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_NAME");
	private String companyFN = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_FN");
	private String companyLN = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_LN");
	public String companyEmail = companyFN + companyLN +CommonVariables.EMAIL_DOMAIN;
	private String companyAddress = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_ADDRESS");
	private String city = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_CITY");
	private String zipCode = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_ZIPCODE");
	private String state = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_STATE");
	private String phone = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_PHONE_NO");
	public String loginLink;
	public String company_email;
	
	
	@Test(priority = 1, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyAddCompany() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPCreateCompanyPage WEPCreateCompanyPage = new WEPCreateCompanyPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
			WEPCreateCompanyPage.sideMenuSelectionWEPCreateCompanyPage(LanguageCode, "Customers", "Companies" );
			waitForPageLoaded();
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addCompany"),"Add Company button not present");
			LOGGER.info("Redirected Company tab page");
						
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("addCompany");
			LOGGER.info("Add Company button clicked");
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("addCustomerHeader", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.title")), "Add Customer Title name is incorrect");
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("custmerSubtitle", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.sub.title")), "SubTitle on add customer is incorrect");
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("custmerAddStep1", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step1")), "Step1 string is incorrect");
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("custmerAdddesc", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.desc")), "Add Customer description is incorrect");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("CompanyName"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("CompanyName"),getTextLanguage(LanguageCode, "daas_ui", "global.form.company_name"),"Company name text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("Address1"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("Address1"),getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.address-line1"),"Address Line1 text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("Address2"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("Address2"),getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.address-line2"),"Address Line2 text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("cityLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("cityLabel"),getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.city"),"City text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("region"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("ZipCodeLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("ZipCodeLabel"),getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.zip-code"),"Zip code text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("country"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("country"),getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.country"),"Country text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addNxtBtn"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("addNxtBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.next"),"Next text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("cancelBtn"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("cancelBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"Cancel text is  not match");
			LOGGER.info("Add Company page opened");
		
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("CompanyName", companyFullName);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("Address1", companyAddress);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("city", city);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("region", state);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("zipcode", zipCode);
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("country");
			sleeper(2000);
			WEPCreateCompanyPage.selectFirstValueFromDropdownOnWEPCreateCompanyPage("countrylt");
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("addNxtBtn");
			waitForPageLoaded();
			LOGGER.info("Company address details added");
			
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("custmerAddStep2", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step2")), "Step2 is incorrect");
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("custmerITdesc", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.itadmin.desc")), "Add IT admin description is incorrect");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("custmerSubtitle"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("firstNameLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("firstNameLabel"),getTextLanguage(LanguageCode, "daas_ui", "itadmin.form.first_name"),"Fisrt name text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("lastNameLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("lastNameLabel"),getTextLanguage(LanguageCode, "daas_ui", "itadmin.form.last_name"),"Last Name text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("emailLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("emailLabel"),getTextLanguage(LanguageCode, "daas_ui", "itadmin.form.email_address"),"Email text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("phoneNumberLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("phoneNumberLabel"),getTextLanguage(LanguageCode, "daas_ui", "itadmin.form.phone_number"),"Phone Number text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("IdpTypedrop"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addPrevBtn"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("addPrevBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.back_button"),"Back text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("cancelBtn"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("cancelBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"Cancel text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addNxtBtn"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("addNxtBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.next"),"Next text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("SelectPlan"));
			LOGGER.info("Add IT Admin page opened");
			
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("firstItadmn", companyFN);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("lastItadmn", companyLN);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("emailItadmin", companyEmail);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("phoneNumber", phone);
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("IdpTypedrop");
			
			WEPCreateCompanyPage.selectFirstValueFromDropdownOnWEPCreateCompanyPage("IDPlist");
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("SelectPlan");
			WEPCreateCompanyPage.selectFirstValueFromDropdownOnWEPCreateCompanyPage("ProPlanOption");
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("addNxtBtn");
			waitForPageLoaded();
			sleeper(2000);
			LOGGER.info("IT Admin deatails added");
			
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("mspInfo"),"Choose MSP pop-up not present");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("mspDesc"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("mspAssigned"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addPrevBtn"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("cancelBtn"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addNxtBtn"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addCompInfo"));
			LOGGER.info("Choose MSP page opened");
			waitForPageLoaded();
			sleeper(2000);
			//WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("mspAssigned");
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("addNxtBtn");
			waitForPageLoaded();
			LOGGER.info("MSP selected from the list");
			
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("partnerInfo"),"Choose Partner pop-up not present");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("partnerDesc"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("partnerAssigned"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addPrevBtn"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("cancelBtn"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("createBtn"));
			LOGGER.info("Choose Partner page opened");
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("createBtn");
			waitForPageLoaded();				
			sleeper(5000); //Company addition takes time to SAVE the details
			WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("compDetails");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("compDetails"),"Company Settings tab not present");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deleteCompBtn"),"Delete button not present");
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("cardTitle",companyFullName), "Company name do not match");
			LOGGER.info("Company got created successfully");
			
			WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("overviewTab");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("overviewTab"),"Overview tab not present");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("compProfile"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("bussiness"));
			LOGGER.info("Overview tab verified");
			
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("prefTab"),"Preference tab not present");
			WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("prefTab");
			sleeper(1000);
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("authentication"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deviceGroup"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("comNetwork"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("thirdParty"));
			LOGGER.info("Preference tab verified");
			
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("mspTab"),"MSP tab not present");
			WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("mspTab");
			sleeper(1000);
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("assignmentSetting"));
			LOGGER.info("Assigned MSP tab verified");
			
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("partnerTab"),"Partner tab not present");
			WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("partnerTab");
			sleeper(1000);
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("assignmentSetting"));
			LOGGER.info("Assigned Partner tab verified");
			
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("commnetsTab"),"Comments tab not present");
			WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("commnetsTab");
			sleeper(1000);
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addcomment"));
			LOGGER.info("Comments tab verified");
			
			LOGGER.info("Company page validated");
						
			}		
		catch(Exception e) {
			Assert.fail("Exception occured in verifyAddCompany " +e);	
		}
	}
	
	@Test(priority = 2, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyAssignPartner() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPCreateCompanyPage WEPCreateCompanyPage = new WEPCreateCompanyPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
			WEPCreateCompanyPage.sideMenuSelectionWEPCreateCompanyPage(LanguageCode, "Customers", "Companies" );
			waitForPageLoaded();
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addCompany"),"Add Company button not present");
			LOGGER.info("Redirected Company tab page");
			
			if (WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("clearFilter")) {
				WEPCreateCompanyPage.mousehoverOnWEPCreateCompanyPage("clearFilter");
				WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("primaryEmailColumn", companyEmail);
			sleeper(3000);
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("firstName");
			waitForPageLoaded();
			WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("partnerTab");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("partnerTab"),"Partner tab not present");
			WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("partnerTab");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("assignmentSetting"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addPart"));
			LOGGER.info("Assigned Partner tab verified");
			WEPCreateCompanyPage.mousehoverOnWEPCreateCompanyPage("addPart");
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("addPart");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("invitePartnerLabel"),"Invite Partner popup not present");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("searchPartner"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("invButton"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("canButton"));
			sleeper(2000);
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("searchPartner");
			WEPCreateCompanyPage.selectFirstValueFromDropdownOnWEPCreateCompanyPage("partnerList");
			WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("partnerInfo");
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("invButton");
			sleeper(5000); //assign partner takes time
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deleteToast"),"Toast message not present");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("pendingInvite"),"Partner invitaion not present");
			LOGGER.info("Partner assigned to company");
						
			}		
		catch(Exception e) {
			Assert.fail("Exception occured in verifyAssignPartner " +e);	
		}
	}

	@Test(priority = 3, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyDeleteCompany() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPCreateCompanyPage WEPCreateCompanyPage = new WEPCreateCompanyPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
			WEPCreateCompanyPage.sideMenuSelectionWEPCreateCompanyPage(LanguageCode, "Customers", "Companies" );
			waitForPageLoaded();
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addCompany"),"Add Company button not present");
			LOGGER.info("Redirected Company tab page");
			
			if (WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("clearFilter")) {
				WEPCreateCompanyPage.mousehoverOnWEPCreateCompanyPage("clearFilter");
				WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("primaryEmailColumn", companyEmail);
			sleeper(4000);
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("firstName");
			waitForPageLoaded();
			WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("emailOnDetails");
			String EmailId = 	WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("emailOnDetails");
			WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("deleteCompBtn");
			WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("deleteCompBtn");
			waitForPageLoaded();
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deleteCompModal"),"deleteCompModal not present");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deleteBtn"),"deleteBtn not present");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("CancelBtn"),"CancelBtn not present");
			WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("deleteBtn");
			sleeper(5000);
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deleteToast"),"Toast message not present");
			if (WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("clearFilter")) {
				WEPCreateCompanyPage.mousehoverOnWEPCreateCompanyPage("clearFilter");
				WEPCreateCompanyPage.clickOnElementsOfWEPCreateCompanyPage("clearFilter");
				waitForPageLoaded();
				LOGGER.info("Clicked on the Clear Filter");
			}
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("primaryEmailColumn", EmailId );
			waitForPageLoaded();
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("noResults"),"Company not deleted");
			LOGGER.info("Company Removed successfully");
			softAssert.assertAll();
		}
		catch(Exception e) {
			Assert.fail("Exception occured in verifyDeleteCompany " +e);	
		}
	}
	
	@Test(priority = 4, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCase ID : ")
	public final void verifyCompanyCreated() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPCreateCompanyPage WEPCreateCompanyPage = new WEPCreateCompanyPage(PreDefinedActions.getDriver()).getInstance();
        WEXCustomerUserDetailsPage WEXCustomerUserDetailsPage = new WEXCustomerUserDetailsPage(PreDefinedActions.getDriver()).getInstance();
        WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		try {
			login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
			LOGGER.info("Left menu is opened");
			leftSideMenuVerification();
			WEPCreateCompanyPage.sideMenuSelectionWEPCreateCompanyPage(LanguageCode, "Customers", "Companies" );
			waitForPageLoaded();
			company_email = WEPCustomerVariables.CUSTOMER_NAME + generateRandomString(4).toLowerCase() + WEXPartnerPage.getddMMMyyyyFormattedDate().toLowerCase() + "@hpmsqa.mailinator.com";
	        LOGGER.info("Company email id is: {}", company_email);
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addCompany"),"Add Company button not present");
			LOGGER.info("Redirected Company tab page");
						
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("addCompany");
			LOGGER.info("Add Company button clicked");
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("addCustomerHeader", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.title")), "Add Customer Title name is incorrect");
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("custmerSubtitle", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.sub.title")), "SubTitle on add customer is incorrect");
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("custmerAddStep1", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step1")), "Step1 string is incorrect");
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("custmerAdddesc", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.desc")), "Add Customer description is incorrect");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("CompanyNameLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("CompanyNameLabel"),getTextLanguage(LanguageCode, "daas_ui", "global.form.company_name"),"Company name text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("Address1abel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("Address1abel"),getTextLanguage(LanguageCode, "daas_ui", "global.address1"),"Address Line1 text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("Address1abelSecond"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("Address1abelSecond"),getTextLanguage(LanguageCode, "daas_ui", "global.address2"),"Address Line2 text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("cityLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("cityLabel"),getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.city"),"City text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("region-label"));
            softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("region-label"),getTextLanguage(LanguageCode, "daas_ui", "global.region"),"State text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("ZipCodeLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("ZipCodeLabel"),getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.zip-code"),"Zip code text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("countryLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("countryLabel"),getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.country"),"Country text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addNxtBtn"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("addNxtBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.next"),"Next text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("cancelBtn"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("cancelBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"Cancel text is  not match");
			LOGGER.info("Add Company page opened");
		
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("CompanyName", WEPCustomerVariables.CUSTOMER_NAME);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("Address1", companyAddress);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("city", city);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("region", state);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("zipcode", zipCode);
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("country");
			WEPCreateCompanyPage.selectFirstValueFromDropdownOnWEPCreateCompanyPage("countrylt");
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("addNxtBtn");
			waitForPageLoaded();
			LOGGER.info("Company address details added");
			
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("custmerAddStep2", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.step2")), "Step2 is incorrect");
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("custmerITdesc", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.itadmin.desc")), "Add IT admin description is incorrect");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("custmerSubtitle"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("firstNameLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("firstNameLabel"),getTextLanguage(LanguageCode, "daas_ui", "itadmin.form.first_name"),"Fisrt name text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("lastNameLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("lastNameLabel"),getTextLanguage(LanguageCode, "daas_ui", "itadmin.form.last_name"),"Last Name text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("emailLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("emailLabel"),getTextLanguage(LanguageCode, "daas_ui", "itadmin.form.email_address"),"Email text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("phoneNumberLabel"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("phoneNumberLabel"),getTextLanguage(LanguageCode, "daas_ui", "itadmin.form.phone_number"),"Phone Number text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("IdpTypedrop"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addPrevBtn"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("addPrevBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.back_button"),"Back text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("cancelBtn"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("cancelBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.cancel"),"Cancel text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addNxtBtn"));
			softAssert.assertEquals(WEPCreateCompanyPage.getTextOfWEPCreateCompanyPage("addNxtBtn"),getTextLanguage(LanguageCode, "daas_ui", "global.next"),"Next text is  not match");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("SelectPlan"));
			LOGGER.info("Add IT Admin page opened");
			
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("firstItadmn", companyFN);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("lastItadmn", companyLN);
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("emailItadmin", company_email);
			//WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("phoneNumber", phone);
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("IdpTypedrop");
			
			WEPCreateCompanyPage.selectFirstValueFromDropdownOnWEPCreateCompanyPage("IDPlist");
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("SelectPlan");
			WEPCreateCompanyPage.selectPlanDropdownOnWEPCreateCompanyPage("ProPlanOption",WEPCustomerVariables.CUSTOMER_PRO_PLAN);
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("addNxtBtn");
			waitForPageLoaded();
			sleeper(2000);
			LOGGER.info("IT Admin deatails added");
			
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("mspInfo"),"Choose MSP pop-up not present");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("mspDesc"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("mspAssigned"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addPrevBtn"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("cancelBtn"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addNxtBtn"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addCompInfo"));
			LOGGER.info("Choose MSP page opened");
			waitForPageLoaded();
			sleeper(2000);
			//WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("mspAssigned");
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("addNxtBtn");
			waitForPageLoaded();
			sleeper(6000);
			LOGGER.info("MSP selected from the list");
			
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("partnerInfoLabel"),"Choose Partner pop-up not present");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("partnerDesc"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("partnerAssigned"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("addPrevBtn"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("cancelBtn"));
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("createBtn"));
			LOGGER.info("Choose Partner page opened");
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("createBtn");
			waitForPageLoaded();				
			sleeper(7000); //Company addition takes time to SAVE the details
			WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("compDetails");
			//softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("compDetails"),"Company Settings tab not present");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("deleteCompBtn"),"Delete button not present");
			softAssert.assertTrue(WEPCreateCompanyPage.matchTextOnWEPCreateCompanyPage("cardTitle",WEPCustomerVariables.CUSTOMER_NAME), "Company name do not match");
			LOGGER.info("Company got created successfully");
			
			//Validating customer admin email
			String subjectInvitation = "You have been invited to the HP Workforce Experience Platform (WXP)!";
            LOGGER.info("subjectInvite "+ subjectInvitation);
            LOGGER.info("Customer User Email: "+ company_email);
			String invitationEmailContent = WEPCreateCompanyPage.getActualMailContent(company_email, subjectInvitation);
            if(Objects.equals(invitationEmailContent, "")) {
                LOGGER.info("Email not received in first attempt, clicking checkbox and resending invitation");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("Firstcheckbox");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("resendinvitation");
                WEXCustomerUserDetailsPage.clickByJavaScriptOnWEXCustomerUserDetailsPage("resendbutton");
                sleeper(3000); // Wait for resend action to complete

                // Try to get the email content again after resending
                invitationEmailContent = WEPCreateCompanyPage.getActualMailContent(company_email, subjectInvitation);
                if(Objects.equals(invitationEmailContent, "")) {
                    LOGGER.error("Email not received even after resending invitation");
                    // You might want to throw an exception or handle this case as per your test requirements
                }
            }
			loginLink = WEPCreateCompanyPage.extractLink(invitationEmailContent);
			LOGGER.info("Invitation email link is: {}", loginLink);
			softAssert.assertAll();
			logout();
			sleeper(2000);
			getDriver().navigate().to(loginLink);
			waitForPageLoaded();
            //WEXCustomerUserListPage.onBoardInvitedUser(company_email);
			WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("usePasswordButton");
			sleeper(3000);
			WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("passowrdTextBox");
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("passowrdTextBox", WEPCustomerVariables.CUSTOMER_PWD);
			sleeper(2000);
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("createButton");
			LOGGER.info("partner login password created!");
			sleeper(2000);

			//Verifying email address
			String subjectVerificationCode = "Verify your email address";
			String verificationEmailContent = WEPCreateCompanyPage.getVerificationEmail(company_email, subjectVerificationCode);
			String code = WEPCreateCompanyPage.extractVerificationCode(verificationEmailContent);
			waitForPageLoaded();
			WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("codeTextBox");
			WEPCreateCompanyPage.enterTextOfWEPCreateCompanyPage("codeTextBox", code);
			WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("verifyButton");
			sleeper(7000);
			LOGGER.info("Verified email address!");
			waitForPageLoaded();
			// Onboarding Page
			sleeper(7000);

			WEPCreateCompanyPage.waitForElementsOfWEPCreateCompanyPage("verifyOnboardingLogo");
			softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("verifyOnboardingLogo"),"Logo is not present");
			//softAssert.assertTrue(WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("verifySteps"),"Onboarding Steps icon is not present");
			//WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("verifyOnboardingNextButton");
			waitForPageLoaded();

			WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("finshAccountCreationTitle");
			WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("companyInformationTitle");
			WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("primaryContactTitle");

			WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("clickOnCompanySizeButton");
			WEPCreateCompanyPage.actionClickOnWEPCreateCompanyPage("clickOnCompanySizeButton");
			WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("clickOnCompanyActualSize");
			WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("termAndConditionCheckbox");
			WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("termAndConditionCheckbox");
			WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("createaccountbuttononpreview");
			waitForPageLoaded();
			//WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("skipButton");
			//WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("skipButton");
			//waitForPageLoaded();
			WEPCreateCompanyPage.verifyElementsOfWEPCreateCompanyPage("welcomePopupSkipButton");
			WEPCreateCompanyPage.clickByJavaScriptOnWEPCreateCompanyPage("welcomePopupSkipButton");
			softAssert.assertAll();
			LOGGER.info("Partner dashboard tour viewed!");
			
			}		
		catch(Exception e) {
			Assert.fail("Exception occured in verifyCompanyCreated " +e);	
		}
	}

}