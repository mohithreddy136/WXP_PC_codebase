package com.testscripts.daasui;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.WEXCompanyOverviewPage;
import com.daasui.pages.WEXDashboardPage;

public class WEXCompanyOverviewTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXHelpAndSupportTest.class);
	String description = generateRandomString(11);
	private static final String Company_size_from_dropdown = "50-99";
	private static final String CUSTOMER_NAME_STAGING = "wex company staging";
	
	@Test(priority = 2, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C42197272")
	public final void verifyCompanyOverviewdetails() throws Exception {
		
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXCompanyOverviewPage WEXCompanyOverviewPage = new WEXCompanyOverviewPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEXCompanyOverviewPage.companyView(CommonVariables.ACCOUNT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account-Overview tab page");
		
		softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("customerid", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerid")), "company id is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyname",getTextLanguage(LanguageCode, "daas_ui", "hAccountManagement.overviewtab.customername")), "customer name is incorrect");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companynameeditbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("companynameeditbutton");
     	sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companynamelabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companynamelabel")), "company name label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companynametextboxlabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companynametextboxlabel")), "company name text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companynametextbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companynamebox")), "company name text box on pop up is incorrect");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companynamesubmitbutton");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companynamecancelbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("companynamesubmitbutton");
     	LOGGER.info("Validation for company name is completed");
     	
     	sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companysize", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companysize")), "company size is incorrect");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companysizeeditbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("companysizeeditbutton");
     	sleeper(2000);
     	WEXCompanyOverviewPage.mouseHoverclickOfWEXCompanyOverviewPage(WEXCompanyOverviewPage.getElementOfWEXCompanyOverviewPage("Companysizelistbox"));
     	WEXCompanyOverviewPage.selectTextValueFromDropdownOfWEXCompanyOverviewPage("companysizedropdown", Company_size_from_dropdown,"Companysizelistbox");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companysizesavebutton");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companysizecancelbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("companysizesavebutton");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.toastNotificationforaddress")), "toaste notification for company size is incorrect");
     	LOGGER.info("Validation for company is completed");
     	
     	LOGGER.info("Validation for company profile section is completed");
     	
     	
		sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("primaryadministratorheader", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.primaryadministratorheader")), "primary adminstrator name header on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("adminname", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.primaryadminstratorname")), "primary adminstrator name/ on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("Adminemail", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.primaryadminstratoremailid")), "primary adminstrator email on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("amdincontactnumber", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.primaryadminstratorphonenumber")), "primary adminstrator phone number on label is incorrect");     	
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("primaryadministratoreditbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("primaryadministratoreditbutton");
     	sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("primaryadministratorheaderpopup", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companynametextboxlabel")), "company time zone label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("primaryadministratorselectbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companynamebox")), "company time select box label on pop up is incorrect");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("primaryadministratorsavebutton");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("primaryadministratorcancelbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("primaryadministratorsavebutton");
     	LOGGER.info("Validation for primary adminstrator section is completed");
     	
     	
     	sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddressheader", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddressheader")), "Company address header on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companycountry", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companycountry")), "company country name/ on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companystate", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companystate")), "company state label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddress1", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddress1")), "Company address line 1 on label is incorrect");     	
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddress2", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddress2")), "Company address line 2 on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companycity", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companycity")), "Company city on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyzipcode", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyzipcode")), "company zip code label is incorrect");     	
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("Companyaddresseditbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("Companyaddresseditbutton");
     	sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddresslabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companyaddresslabel")), "Company address header on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companycountrylabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companycountrylabel")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companycountrytextbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companycountrytextbox")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddressline1label", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companyaddressline1textbox")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddressline1textbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddressline1textbox")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddressline2label", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddressline2label")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddressline2textbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddressline2textbox")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companycitylabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companycitylabel")), "company time text box label on pop up is incorrect");     	
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companycitytextbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companycitytextbox")), "company time text box label on pop up is incorrectt");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companystatelabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companystatelabel")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companystatetextbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companystatetextbox")), "company time text box label on pop up is incorrect");     	
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyzipcodelabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyzipcodelabel")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyzipcodetextbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyzipcodetextbox")), "company time text box label on pop up is incorrect");
     	WEXCompanyOverviewPage.enterTextForWEXCompanyOverviewPage("companycitytextbox",description);
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companysavebutton");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companycancelbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("companysavebutton");
     	
     	//softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.toastNotificationforaddress")), "toaste notification for address is incorrect");
     	LOGGER.info("Validation for Address section is completed");	
	}
	
	@Test(priority = 1, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C42197272")
	public final void verifyCompanyOverviewdetailsThroughPartner() throws Exception {
		
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEXCompanyOverviewPage WEXCompanyOverviewPage = new WEXCompanyOverviewPage(PreDefinedActions.getDriver()).getInstance();
  		dashboardPage.leftSideMenuVerification();
		String environment = System.getProperty("environment");
		String companyOrCustomerName;
		if ("US-Stage-WEP".equalsIgnoreCase(environment) || "US-VENEER-WEP".equalsIgnoreCase(environment)) {
			companyOrCustomerName = getEnvironmentSpecificData(environment, "COMPANY_NAME");
		} else {
			companyOrCustomerName = getEnvironmentSpecificData(environment, "CUSTOMER_NAME");
		}
		dashboardPage.partnerWithCompanyView(companyOrCustomerName, CommonVariables.ACCOUNT);
		waitForPageLoaded();
        LOGGER.info("Redirected Account -Overview tab page");
		softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("Overviewcompanyname", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyname")), "company name is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("PlanName",getTextLanguage(LanguageCode, "daas_ui", "hAccountManagement.overviewtab.planname")), "plan name is incorrect");
		softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companystatus",getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companystatus")), "company status is incorrect");
		softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("Overviewtab",getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab")), " overview tab name is incorrect");
		LOGGER.info("Validation for company overview header is completed");
		
		
		softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyprofileheader", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyprofileheader")), "company profile header is incorrect");
		softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("customerid", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerid")), "company id is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyname",getTextLanguage(LanguageCode, "daas_ui", "hAccountManagement.overviewtab.customername")), "customer name is incorrect");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companynameeditbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("companynameeditbutton");
     	sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companynamelabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companynamelabel")), "company name label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companynametextboxlabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companynametextboxlabel")), "company name text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companynametextbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companynamebox")), "company name text box on pop up is incorrect");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companynamesubmitbutton");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companynamecancelbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("companynamesubmitbutton");
     	LOGGER.info("Validation for company name is completed");
     	
     	sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companysize", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companysize")), "company size is incorrect");
     	LOGGER.info("Validation for company is completed");
     	
     	LOGGER.info("Validation for company profile section is completed");
     	
     	
		sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("primaryadministratorheader", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.primaryadministratorheader")), "primary adminstrator name header on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("adminname", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.primaryadminstratorname")), "primary adminstrator name/ on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("Adminemail", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.primaryadminstratoremailid")), "primary adminstrator email on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("amdincontactnumber", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.primaryadminstratorphonenumber")), "primary adminstrator phone number on label is incorrect");     	
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("primaryadministratoreditbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("primaryadministratoreditbutton");
     	sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("primaryadministratorheaderpopup", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companynametextboxlabel")), "company time zone label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("primaryadministratorselectbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companynamebox")), "company time select box label on pop up is incorrect");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("primaryadministratorsavebutton");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("primaryadministratorcancelbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("primaryadministratorsavebutton");
     	LOGGER.info("Validation for primary adminstrator section is completed");
     	
     	
     	sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddressheader", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddressheader")), "Company address header on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companycountry", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companycountry")), "company country name/ on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companystate", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companystate")), "company state label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddress1", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddress1")), "Company address line 1 on label is incorrect");     	
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddress2", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddress2")), "Company address line 2 on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companycity", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companycity")), "Company city on label is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyzipcode", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyzipcode")), "company zip code label is incorrect");     	
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("Companyaddresseditbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("Companyaddresseditbutton");
     	sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddresslabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companyaddresslabel")), "Company address header on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companycountrylabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companycountrylabel")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companycountrytextbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companycountrytextbox")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddressline1label", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.customerName.companyaddressline1textbox")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddressline1textbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddressline1textbox")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddressline2label", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddressline2label")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddressline2textbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddressline2textbox")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companycitylabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companycitylabel")), "company time text box label on pop up is incorrect");     	
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companycitytextbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companycitytextbox")), "company time text box label on pop up is incorrectt");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companystatelabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companystatelabel")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companystatetextbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companystatetextbox")), "company time text box label on pop up is incorrect");     	
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyzipcodelabel", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyzipcodelabel")), "company time text box label on pop up is incorrect");
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyzipcodetextbox", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyzipcodetextbox")), "company time text box label on pop up is incorrect");
     	WEXCompanyOverviewPage.enterTextForWEXCompanyOverviewPage("companycitytextbox",description);
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companysavebutton");
     	WEXCompanyOverviewPage.verifyElementsOfWEXCompanyOverviewPage("companycancelbutton");
     	WEXCompanyOverviewPage.clickOnElementsOfWEXCompanyOverviewPage("companysavebutton");
     	sleeper(2000);
     	softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.toastNotificationforaddress")), "toaste notification for address is incorrect");
     	LOGGER.info("Validation for Address section is completed");		
	}
	
	@Test(priority = 3, groups = {"PRODUCTION_PLATFORMCX"}, description = "TestCaseID:C42197272")
	public final void verifyCompanyOverviewTabVerification() throws Exception {
		
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXCompanyOverviewPage WEXCompanyOverviewPage = new WEXCompanyOverviewPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEXCompanyOverviewPage.companyView(CommonVariables.ACCOUNT);
		waitForPageLoaded();
		LOGGER.info("Redirected to Account -Overview tab page");
		
		softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("Overviewcompanyname", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyname")), "company name is incorrect");
		softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("primaryadministratorheader", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.primaryadministratorheader")), "primary adminstrator name header on label is incorrect");
		softAssert.assertTrue(WEXCompanyOverviewPage.matchTextOfWEXCompanyOverviewPage("companyaddressheader", getTextLanguage(LanguageCode, "daas_ui", "AccountManagement.overviewtab.companyaddressheader")), "Company address header on label is incorrect");
		
		LOGGER.info("Validation Account -Overview tab Verification");
	
	}
	
}
