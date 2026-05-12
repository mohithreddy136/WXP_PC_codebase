package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ScriptVariables;
import com.daasui.pages.WEPAlertsDashboardPage;
import com.daasui.pages.WEPGroupsPage;
import com.daasui.pages.WEPRemediationPage;
import com.daasui.pages.WEPScriptsPage;


public class WEPRemediationTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEPRemediationTest.class);

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
	 * This Test Case TC_C51266642>>[WEXINT][Remediation] Verify Remediate button is showing for ITAdmin
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "REGRESSION_INTEGRATIONS","PRODUCTION_PULSES_EE_SUSTAIN"}, description="TestcaseID: C51266642")
	public final void verifyWEPRemediationLinkOnCustomerView() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEPRemediationPage remediationPage= new WEPRemediationPage(PreDefinedActions.getDriver()).getInstance();
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		remediationPage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),"No Alert Message not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertMessage"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"),"No Alert Message text is  not match");
			LOGGER.info("No Active Alerts found hence skipping the rest of the testcase");
		} else {
			remediationPage.waitForElementsOfRemediationPage("bitLocketActiveAlert");
			remediationPage.clickOnElementsOfRemediationPage("bitLocketActiveAlert");
			waitForPageLoaded();
			sleeper(2000);
			Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("breadcrumbsAlertHeader"),"BreadcrumbsAlertHeader is not present on Active Alert Page");
			//Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("breadcrumbsAlertHeader"),getTextLanguage(LanguageCode, "daas_ui", "alerts.breadcrumbs.title"),"Alert breadcrumb text is  not match");
			Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("breadcrumbsAlertHeader"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.appbar.header.title"),"Alert breadcrumb text is  not match");
			Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("breadcrumbsAlertTitle"),"bread crumb title is not present on Active Alert Page");
			//remediationPage.actionClickOfRemediationPage("firstDeviceInAlertDetails");
			sleeper(2000);
			if(remediationPage.verifyElementIsEnableOnRemediationPage("RemediateBtnInAlertDetails")){
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("RemediateBtnInAlertDetails").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.remediate.action")),"Remediate button doesn't exists");
				LOGGER.info("Remediate button is Available");
			}else {
				LOGGER.info("Remediate button is not Available and no devices are showing in the  list");
			}
		}
		softAssert.assertAll();

	}

	/**
	 * This Test Case TC_C51266681	[WEXINT][Remediation] Verify Help Link on Remediate wizard Modal Window  
	 * @throws Exception
	 */
	@Test(priority = 2, groups = { "REGRESSION_INTEGRATIONS"}, description="Testcase ID: TC_C51266681")
	public final void verifyWEPRemediationHelpLink() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEPRemediationPage remediationPage= new WEPRemediationPage(PreDefinedActions.getDriver()).getInstance();
		WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		remediationPage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),
					"No Alert Message not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertMessage"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"),"No Alert Message text is  not match");
			LOGGER.info("No Active Alerts found hence skipping the rest of the testcase");
		} else {
			remediationPage.waitForElementsOfRemediationPage("bitLocketActiveAlert");
			remediationPage.clickOnElementsOfRemediationPage("bitLocketActiveAlert");
			waitForPageLoaded();
			sleeper(5000);
			//			remediationPage.actionClickOfRemediationPage("firstDeviceInAlertDetails");
			if(remediationPage.verifyElementsOfRemediationPage("RemediateBtnInAlertDetails")) {
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("RemediateBtnInAlertDetails").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.remediate.action")),"Remediate button doesn't exists");
				LOGGER.info("Remediate button is Available");
				remediationPage.actionClickOfRemediationPage("RemediateBtnInAlertDetails");
				waitForPageLoaded();
				sleeper(2000);
				remediationPage.waitForElementsOfRemediationPage("helpLinkOnRemediateWizard");
				remediationPage.verifyElementsOfRemediationPage("helpLinkOnRemediateWizard");
				remediationPage.clickOnElementsOfRemediationPage("helpLinkOnRemediateWizard");
				waitForPageLoaded();
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("remediateHeaderOnHelp").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.remediate.action")),"Remediate Header doesn't exists");
				//System.out.println(remediationPage.getTextOfWEPRemediationPage("helpTextOnremediateHelpWindow"));
				String helpTextMsg = remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.side.bar.p1")+" "+remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.side.bar.p2")+" "+
						remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.side.bar.p3")+" "+remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.side.bar.p4");
				System.out.println(helpTextMsg);
				System.out.println(remediationPage.getTextOfWEPRemediationPage("helpTextOnremediateHelpWindow").replaceAll("\\s+", " ").trim());
				Assert.assertTrue((remediationPage.getTextOfWEPRemediationPage("helpTextOnremediateHelpWindow").replaceAll("\\s+", " ").trim()).contains(helpTextMsg),"Remediate Header doesn't exists");
				LOGGER.info("Help Text on Remediate window is verified");
				remediationPage.clickOnElementsOfRemediationPage("closeBtnOnremediateHelpWindow");	
			}else {
				LOGGER.info("Remediate button is not Available and no devices are showing in the  list");
			}
		}
	}

	/**
	 * This Test Case TC_C51266664	[WEXINT][Remediation] Verify Remediate wizard stepper model(Header and Footer) is as per the figma 	   
	 * @throws Exception
	 */
	@Test(priority = 3, groups = { "REGRESSION_INTEGRATIONS" }, description="Testcase ID: TC_C51266664")
	public final void verifyHeaderAndFooterOnRemediationScreen() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEPRemediationPage remediationPage= new WEPRemediationPage(PreDefinedActions.getDriver()).getInstance();
		WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		remediationPage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),
					"No Alert Message not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertMessage"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"),"No Alert Message text is  not match");
			LOGGER.info("No Active Alerts found hence skipping the rest of the testcase");
		} else {
			remediationPage.waitForElementsOfRemediationPage("bitLocketActiveAlert");
			System.out.println(remediationPage.getTextOfWEPRemediationPage("bitLocketActiveAlert"));
			remediationPage.clickOnElementsOfRemediationPage("bitLocketActiveAlert");
			LOGGER.info("Alerts breadcrumbs are validated");
			waitForPageLoaded();
			sleeper(2000);
			//remediationPage.actionClickOfRemediationPage("firstDeviceInAlertDetails");
			sleeper(3000);
			if(remediationPage.verifyElementsOfRemediationPage("RemediateBtnInAlertDetails")){
				LOGGER.info("Remediate button is Available");
				remediationPage.actionClickOfRemediationPage("RemediateBtnInAlertDetails");
				waitForPageLoaded();
				sleeper(2000);
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("remediateHeader"),"BreadcrumbsAlertHeader is not present on Active Alert Page");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("remediateHeader"),getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.remediate.action"),"Remediation Header text is  not match");
				remediationPage.waitForElementsOfRemediationPage("helpLinkOnRemediateWizard");
				String helpTxt=remediationPage.getTextOfWEPRemediationPage("helpLinkOnRemediateWizard");
				Assert.assertEquals(helpTxt,"Help","Help Link is not exists");

				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("cancelBtnOnRemediateWindow"),getTextLanguage(LanguageCode, "daas_ui", "confirmationModal.closeLabel"),"Cancel button is not exists");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("nextButtonRemediateWizard"),getTextLanguage(LanguageCode, "daas_ui", "global.next"),"Next button is  not exists");
				LOGGER.info("Remediation Header and Footer is done successfully");
			}else {
				LOGGER.info("Remediate button is not Available");
			}
		}
	}

	/**
	 * This Test Case TC_C51600945>>[WEXINT][Remediaion]>>Verify Type and SubType fields are showing as list of dropdown values on Remediate My library Modal popup
	 * @throws Exception
	 */
	@Test(priority = 4, groups = { "REGRESSION_INTEGRATIONS"}, description="Testcase ID: TC_C51600945")
	public final void verifyTypeAndSubTypeFieldsOnRemediationWModalPopup() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEPRemediationPage remediationPage= new WEPRemediationPage(PreDefinedActions.getDriver()).getInstance();
		WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		remediationPage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),
					"No Alert Message not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertMessage"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"),"No Alert Message text is  not match");
			LOGGER.info("No Active Alerts found hence skipping the rest of the testcase");
		} else {
			remediationPage.waitForElementsOfRemediationPage("bitLocketActiveAlert");
			remediationPage.clickOnElementsOfRemediationPage("bitLocketActiveAlert");
			waitForPageLoaded();
			sleeper(2000);
			Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("breadcrumbsAlertHeader"),"BreadcrumbsAlertHeader is not present on Active Alert Page");
			LOGGER.info("Alerts breadcrumbs are validated");
			//remediationPage.actionClickOfRemediationPage("firstDeviceInAlertDetails");
			sleeper(3000);
			if(remediationPage.verifyElementIsEnableOnRemediationPage("RemediateBtnInAlertDetails")) {
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("RemediateBtnInAlertDetails").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.remediate.action")),"Remediate button doesn't exists");
				LOGGER.info("Remediate button is Available");
				remediationPage.actionClickOfRemediationPage("RemediateBtnInAlertDetails");
				waitForPageLoaded();
				sleeper(2000);
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("hpGalleryHeader"),getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.hp.gallery"),"HP Gallery Header text is  not match");
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("scriptNameOnHPGallery"),"ScriptName  Column is not present on HP Gallery section");
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("scriptAuthorName"),"scriptAuthorName Column is  not present on HP Gallery section");	
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("sourceType"),"Type Column is not present on HP Gallery section");
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("subType"),"SubType Column is not present on HP Gallery section");

				remediationPage.waitForElementsOfRemediationPage("hpGalleryTypeListbox");
				remediationPage.actionClickOfRemediationPage("hpGalleryTypeListbox");
				remediationPage.waitForElementsOfRemediationPage("hpGalleryTypeListboxOption1");
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("hpGalleryTypeListboxOption1").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "contentAddForm.option.script")),"Script Type doesn't exists");
				remediationPage.waitForElementsOfRemediationPage("hpGallerySubtypeListbox");
				sleeper(2000);
				remediationPage.waitForElementsOfRemediationPage("hpGallerySubtypeListbox");
				remediationPage.actionClickOfRemediationPage("hpGallerySubtypeListbox");
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("hpGallerySubtypeListboxOption1").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.hardware.label.action")),"Action Subtype doesn't exists");
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("hpGallerySubtypeListboxOption2").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.configuration.options.evaluation")),"Evaluation Subtype doesn't exists");
				LOGGER.info("HPGallery Columns are verified");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("myLibraryHeader"),getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.my.library"),"MY Library Header text is  not match");
				remediationPage.clickOnElementsOfRemediationPage("myLibraryHeader");
				waitForPageLoaded();
				sleeper(2000);
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("scriptNameOnHPGallery"),"ScriptName  Column is not present on HP Library section");
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("scriptAuthorName"),"scriptAuthorName Column is  not present on HP Library section");	
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("scriptVersion"),"scriptVersion Column is  not present on HP Library section");	
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("sourceType"),"Type Column is not present on HP Library section");
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("subTypeOnMyLibraryPage"),"SubType Column is not present on HP Library section");

				remediationPage.waitForElementsOfRemediationPage("hpGalleryTypeListbox");
				remediationPage.actionClickOfRemediationPage("hpGalleryTypeListbox");
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("hpGalleryTypeListboxOption1").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "contentAddForm.option.script")),"Script Type doesn't exists");
				sleeper(2000);
				remediationPage.waitForElementsOfRemediationPage("hpGallerySubtypeListbox");
				remediationPage.actionClickOfRemediationPage("hpGallerySubtypeListbox");
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("hpGallerySubtypeListboxOption1").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.hardware.label.action")),"Action Subtype doesn't exists");
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("hpGallerySubtypeListboxOption2").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.configuration.options.evaluation")),"Evaluation Subtype doesn't exists");

				LOGGER.info("MyLibrary Columns are verified");

			}else {
				LOGGER.info("Remediate button is not Available");	
			}
		}
	}

	/**
	 * These are 3 testcases we are covering as aprt of this single script
	 * TC_C52579233	[WEXINT][Remediaion]>>Verify Remediate flow for any RA  
	 * TC_C51266680>>[WEXINT][Remediation] Verify Library section on Remediate wizard Stepper modal 	
	 * TC_C51266910>>WEXINT][Remediation] Verify Remediate wizard scripts table 
	 * @throws Exception
	 */
	@Test(priority = 5, groups = { "REGRESSION_INTEGRATIONS"}, description="Testcase ID:C52579233")
	public final void verifyApplyRemediationFlowOnCustomer() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEPRemediationPage remediationPage= new WEPRemediationPage(PreDefinedActions.getDriver()).getInstance();
		WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		remediationPage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),
					"No Alert Message not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertMessage"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"),"No Alert Message text is  not match");
			LOGGER.info("No Active Alerts found hence skipping the rest of the testcase");
		} else {
			remediationPage.waitForElementsOfRemediationPage("bitLocketActiveAlert");
			System.out.println(remediationPage.getTextOfWEPRemediationPage("bitLocketActiveAlert"));
			remediationPage.clickOnElementsOfRemediationPage("bitLocketActiveAlert");
			LOGGER.info("Alerts breadcrumbs are validated");
			waitForPageLoaded();
			sleeper(2000);
			Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("breadcrumbsAlertHeader"),"BreadcrumbsAlertHeader is not present on Active Alert Page");
			//Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("breadcrumbsAlertHeader"),getTextLanguage(LanguageCode, "daas_ui", "alerts.breadcrumbs.title"),"Alert breadcrumb text is  not match");
			Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("breadcrumbsAlertHeader"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.appbar.header.title"),"Alert breadcrumb text is  not match");
			Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("breadcrumbsAlertTitle"),"bread crumb title is not present on Active Alert Page");
			LOGGER.info("Alerts breadcrumbs are validated");
			//						remediationPage.actionClickOfRemediationPage("firstDeviceInAlertDetails");
			sleeper(3000);
			if(remediationPage.verifyElementsOfRemediationPage("RemediateBtnInAlertDetails")){
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("RemediateBtnInAlertDetails").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.remediate.action")),"Remediate button doesn't exists");
				LOGGER.info("Remediate button is Available");
				remediationPage.actionClickOfRemediationPage("RemediateBtnInAlertDetails");
				waitForPageLoaded();
				sleeper(2000);
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("remediateHeader"),"BreadcrumbsAlertHeader is not present on Active Alert Page");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("remediateHeader"),getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.remediate.action"),"Remediation Header text is  not match");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("hpGalleryHeader"),getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.hp.gallery"),"HP Gallery Header text is  not match");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("myLibraryHeader"),getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.my.library"),"MY Library Header text is  not match");
				waitForPageLoaded();
				sleeper(2000);
				remediationPage.clickOnElementsOfRemediationPage("selectScriptInRemediateWizard");
				String scriptName=remediationPage.getTextOfWEPRemediationPage("scriptNameInRemediateWizard");
				String synopsisName=remediationPage.getTextOfWEPRemediationPage("synopsisNameInRemediateWizard");
				remediationPage.clickOnElementsOfRemediationPage("nextButtonRemediateWizard");
				waitForPageLoaded();
				sleeper(2000);

				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("reviewHeaderOnRemediateWizard"),"Review Header is not present on Remediation Page");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("reviewHeaderOnRemediateWizard"),getTextLanguage(LanguageCode, "daas_ui", "group.review"),"Review Header text is  not match");

				String synopsisNameOnReiewPage = remediationPage.getTextOfWEPRemediationPage("synopsisName");
				String scriptNameOnReiewPage = remediationPage.getTextOfWEPRemediationPage("scriptName");
				Assert.assertEquals(synopsisNameOnReiewPage,synopsisName,"synopsisName text is not matching");
				Assert.assertEquals(scriptNameOnReiewPage,scriptName,"scriptName text is  not match");	
				//Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("selectedDevicesCount"),"1","selectedDevicesCount text is  not match");

				remediationPage.clickOnElementsOfRemediationPage("remediateButton");
				waitForPageLoaded();
				sleeper(2000);
				System.out.println(remediationPage.getTextOfWEPRemediationPage("toastNotificationExport"));
				Assert.assertTrue(
						remediationPage.getTextOfWEPRemediationPage("toastNotificationExport")
						.contains(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activity.remediation.progress"))," Apply Remediation in failed");
				LOGGER.info("Remediation is done successfully");

				// Navigate to the assignments page		
				//				if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
				//					remediationPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
				//				}else{
				//					remediationPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
				//				}
				remediationPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
				waitForPageLoaded();
				if(wexScriptsPage.verifyElementsOfScriptsPage("clearFilter")){
					wexScriptsPage.clearScriptFilter();
				}
				wexScriptsPage.clickOnElementsOfScriptsPage("assignmentsTab");
				wexScriptsPage.clearScriptFilter();
				wexScriptsPage.clickOnElementsOfScriptsPage("runTypeListboxOnAssignmentListPage");
				sleeper(1000);
				wexScriptsPage.clickOnElementsOfScriptsPage("runTypeOptionSelectionOnAssignmentListPage");
				sleeper(2000);
				wexScriptsPage.clickOnElementsOfScriptsPage("assignmentNameSearchField");
				sleeper(1000);
				wexScriptsPage.enterTextOnScriptsPage("assignmentNameSearchField", scriptNameOnReiewPage);
				sleeper(2000);
				Assert.assertTrue(wexScriptsPage.verifyTextPresentOnElementScriptsPage("assignmentNameRowValue",scriptNameOnReiewPage),"The assignment did not get displayed in the list page.");
				wexScriptsPage.clickOnElementsOfScriptsPage("assignmentNameRowValue");

				wexScriptsPage.scrollTillViewScriptsPage("assignmentDetailScriptInfoHeader");
				Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailScriptInfoHeader","Script Information"), "Label of Script Information header is not matching");
				Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailScriptName","Script Name"), "Label of Script Name is not matching");
				Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailScriptNameVal"), "Value doesn't exist for Script Name");

				Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailVersion","Version"), "Label of Assignment Script Version is not matching");
				Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailVersionVal"), "Value doesn't exist for Script Version");

				Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailOperation","Operation"), "Label of Assignment Script Operation is not matching");
				Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailOperationVal"), "Value doesn't exist for Script Operation");

				Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailScriptID","Script ID"), "Label of Assignment Script ID is not matching");
				Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailScriptIDVal"), "Value doesn't exist for Script ID");

				//        Assignment Information
				wexScriptsPage.scrollTillViewScriptsPage("assignmentDetailAssignmentInfoHeader");
				Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentInfoHeader","Assignment Information"), "Label of Assignment Information header is not matching");

				Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailCreatedByLabel","Created By"), "Label of Assignment Author is not matching");
				Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailCreatedByValue"), "Value doesn't exist for Assignment Created By Author");

				Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentNameLabel","Assignment Name"), "Label of Assignment Name is not matching");
				Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailAssignmentNameValue"), "Value doesn't exist for Assignment Name");

				Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentLastModifiedLabel","Last Modified"), "Label of Assignment Information Last Modified is not matching");
				Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailAssignmentLastModifiedValue"), "Value doesn't exist for Assignment Created By Author");

				Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentDateCreatedLabel","Date Created"), "Label of Assignment Information Created Date is not matching");
				Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailAssignmentDateCreatedValue"), "Value doesn't exist for Assignment Created Date");

				Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentGroupsLabel","Groups"), "Label of Assignment Information Group is not matching");
				Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailAssignmentGroupsValue"), "Value doesn't exist for Assignment Groups");

				Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("assignmentDetailAssignmentAssignmentIDLabel","Assignment ID"), "Label of Assignment Id is not matching");
				Assert.assertTrue(wexScriptsPage.anyValueExists("assignmentDetailAssignmentAssignmentIDValue"), "Value doesn't exist for Assignment Id");

				wexScriptsPage.verifyElementsOfScriptsPage("assignmentDetailScheduleEditBtn");
				LOGGER.info("Assignment Details Verification Test Passed");
				remediationPage.waitForElementsOfRemediationPage("activityTabInScriptDetailPage");
				remediationPage.clickOnElementsOfRemediationPage("activityTabInScriptDetailPage");
				waitForPageLoaded();
				sleeper(2000);
				if(!remediationPage.verifyElementsOfRemediationPage("noDataInActivityTabInAssignmentDetailsPage")) {
					remediationPage.mouseHoverOfRemediationPage("scriptsActivitieSInScriptDetailPage");
					remediationPage.clickOnElementsOfRemediationPage("activityMenuOnScriptActivityPage");
					remediationPage.clickOnElementsOfRemediationPage("detailsActivityMenuOnScriptActivityPage");

					Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailScriptNameLabel","Script Name"), "Label of Script Name is not matching");
					Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailScriptNameVal"), "Value doesn't exist for Script Name");
					Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailTypeLabel","Type"), "Label of Detail type is not matching");
					Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailTypeVal"), "Value doesn't exist for Detail type");
					Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailSubTypeLabel","Subtype"), "Label of Sub Type is not matching");
					Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailSubTypeVal"), "Value doesn't exist for Sub Type");
					Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailInitaitedOnLabel","Initiated On"), "Label of InitaitedOn is not matching");
					Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailInitaitedOnVal"), "Value doesn't exist for Initaited On");
					Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailInitaitedByLabel","Initiated By"), "Label of InitaitedBy is not matching");
					Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailInitaitedByVal"), "Value doesn't exist for Initaited By");
					//Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailCompletedOnLabel","Updated At"), "Label of Updated At is not matching");
					Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailCompletedOnVal"), "Value doesn't exist for Updated At");
					Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailDeviceRemidiatedLabel","Devices Remediated"), "Label of Device Remidiated is not matching");
					Assert.assertTrue(wexScriptsPage.anyValueExists("activityDetailDeviceRemidiatedVal"), "Value doesn't exist for Device Remidiated");
					wexScriptsPage.scrollTillViewScriptsPage("activityDetailDeviceHeader");
					Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailDeviceHeader","Devices"), "Label of Devices header is not matching");
					//		Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailKPIstatus1","Error"), "Label of error kpi is not matching");
					//		Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailKPIstatus2","Success"), "Label of Success kpi is not matching");
					//		Assert.assertTrue(wexScriptsPage.matchTextOfScriptsPage("activityDetailKPIstatus3","Canceled"), "Label of Canceled kpi is not matching");
					List<String> expectedColumnList = new ArrayList<String>(Arrays.asList(
							getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.configuration.options.device.name"),
							getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activitiestable.configuration.options.serial.number"),
							getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.activity.remediation.executionStatus")));
					wexScriptsPage.verifyElementsOfScriptsPage("activityDetailsTable");
					wexScriptsPage.verifyElementsOfScriptsPage("activityDetailsTableRows");
					wexScriptsPage.verifyElementsOfScriptsPage("activityDetailsTableCount");
					Assert.assertTrue(wexScriptsPage.verifyTableColumns(expectedColumnList,"activityDetailsTableColumns"), "Table Columns are not as expected");
					wexScriptsPage.verifyElementsOfScriptsPage("activityListBreadCrumb");
					wexScriptsPage.mouseHoverAndClickOfScriptsPage("activityListBreadCrumb");
					LOGGER.info("Activity Details Verification Test Passed");
				}else {
					LOGGER.info("Activity Tab is not visible for this script hence skipping the activity details verification");
				}
			} else {
				LOGGER.info("Remediation button is not avaiable and no devices are showing on the Device list page");
			}
		}
	}

	/**
	 * These are 2 testcases we are covering as aprt of this single script
	 * TC_C51575015>>[WEXINT][Remediation] Verify new Ui flow for BIOS policy update  
	 * TC_C52579232>>[WEXINT][Remediaion]>>Verify Remediate button should be hidden for BIOS policy and Update Signature-Windows Defender  
	 * @throws Exception
	 */
	@Test(priority = 6, groups = { "REGRESSION_INTEGRATIONS" }, description="TestcaseID: TC_C51575015")
	public final void verifyApplyBiosPolicyFlowOnCustomer() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String SG_GroupName = "Workforce Experience Platform - LatestAll";
		String SG_GroupDescription = "This group is created and managed by Workforce Experience Platform .";
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEPRemediationPage remediationPage= new WEPRemediationPage(PreDefinedActions.getDriver()).getInstance();
		WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		remediationPage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),
					"No Alert Message not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertMessage"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"),"No Alert Message text is  not match");
			LOGGER.info("No Active Alerts found hence skipping the rest of the testcase");
		} else {
			if(remediationPage.verifyElementsOfRemediationPage("missingBiosUpdateActiveAlert")) {
				remediationPage.waitForElementsOfRemediationPage("missingBiosUpdateActiveAlert");
				remediationPage.clickOnElementsOfRemediationPage("missingBiosUpdateActiveAlert");
				LOGGER.info("Navigated to Missing Bios Updated Active Alerts are validated");
				waitForPageLoaded();
				sleeper(3000);
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("breadcrumbsAlertHeader"),"BreadcrumbsAlertHeader is not present on Active Alert Page");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("breadcrumbsAlertHeader"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.appbar.header.title"),"Alert breadcrumb text is  not match");
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("breadcrumbsAlertTitle"),"bread crumb title is not present on Active Alert Page");
				LOGGER.info("Alerts breadcrumbs are validated");
				sleeper(3000);
				String firstDeviceSerialNo = remediationPage.getTextOfWEPRemediationPage("getFirstDeviceSerialNo");
				LOGGER.info("Applying Bios policy on this Serail Number :"+firstDeviceSerialNo);

				if (remediationPage.verifyElementIsEnableOnRemediationPage("RemediateBtnInAlertDetails")) {
					Assert.fail("Remediate button is showing for Bios Policy hence failing the testcase");
					LOGGER.info("Remediate button is showing for Bios Policy hence failing the testcase");
				}
				if (remediationPage.verifyElementIsEnableOnRemediationPage("applyPolicyBtn")) {
					LOGGER.info("Apply Policy button is showing for Bios alert");
				}else {
					LOGGER.info("Apply Policy button is not showing for Bios alert");
				}

				remediationPage.clickOnElementsOfRemediationPage("applyPolicyBtn");
				waitForPageLoaded();
				sleeper(2000);
				LOGGER.info("Apply Policy button is clicked checking the popup fields");
				Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("applyPolicyHeaderOnPopUp"),"applyPolicyHeaderOnPopUp is not present on Active Alert Page");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("applyPolicyHeaderOnPopUp"),getTextLanguage(LanguageCode, "daas_ui", "device_list_apply_policy_label"),"Apply Policy Header text is  not match");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("selectBiosPolicyHeaderOnPopUp"),getTextLanguage(LanguageCode, "daas_ui", "insight.bios_update.apply.policy.popup.select.bios.policy.title"),"selectBiosPolicyHeaderOnPopUp text is  not match");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("keepBiosUptoDateOption").toLowerCase(),getTextLanguage(LanguageCode, "daas_ui", "assets.list.bios.alwaysKeepBiosUpToDate").toLowerCase()," Keep Bios Upto date Header text is  not match");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("updateCriticalBiosUptoDateOption").toLowerCase(),getTextLanguage(LanguageCode, "daas_ui", "device_list_applyPolicyPopup_label_deploy_critical_bios_update").toLowerCase(),"Update Critical Bios Update Header text is  not match");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("saveBtnOnApplyPolicyPopup"),getTextLanguage(LanguageCode, "daas_ui", "table.configuration.save"),"Save Button text is  not match");
				Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("cancelBtnOnApplyPolicyPopup"),getTextLanguage(LanguageCode, "daas_ui", "web.application.config.form.cancel"),"Cancel text is  not match");
				LOGGER.info("Bios Popup verification is done successfully");
				remediationPage.clickOnElementsOfRemediationPage("saveBtnOnApplyPolicyPopup");
				waitForPageLoaded();
				sleeper(2000);
				System.out.println(remediationPage.getTextOfWEPRemediationPage("toastNotificationExport"));
				Assert.assertTrue(
						remediationPage.getTextOfWEPRemediationPage("toastNotificationExport")
						.contains(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.bios.applyPolicy.success.title"))," Apply Bios policy is failed");
				LOGGER.info("Apply Bios Popup is done successfully");
				waitForPageLoaded();
				sleeper(3000);
				groupsPage.companyView(CommonVariables.GROUPS);
				LOGGER.info("Navigated to Groups successfully");
				sleeper(2000);
				Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
						.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
				LOGGER.info("Groups SideMenu is Matched");
	
				Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
						.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"GroupsMenu Breadcrumb doesn't exists");
				LOGGER.info("Groups  Breadcrumb is Matched");
				groupsPage.enterTextForGroupsPage("groupNameSearch",SG_GroupName);
				waitForPageLoaded();
				sleeper(3000);
				Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameCheck").equals(SG_GroupName),"Failed to verify the existing bios Group");		
				LOGGER.info("Bios Policy group creation is verified successfully and its showing on Group List page");		
				groupsPage.clickOnElementsOfGroupsPage("groupNameCheck");
				waitForPageLoaded();
				sleeper(3000);
				Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupNameHeaderTitle").equals(SG_GroupName),"Group Name is not matched");		
				LOGGER.info("Dynamic group Name title is verified");
				Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupDescription").equals(SG_GroupDescription),"Group Description is not matched");		
				LOGGER.info("Dynamic group description is verified");

				groupsPage.clickOnElementsOfGroupsPage("groupMembership");
				waitForPageLoaded();
				remediationPage.enterTextForRemediationPage("deviceSearchOnMembershipTab",firstDeviceSerialNo);
				waitForPageLoaded();
				sleeper(3000);
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("deviceSNOnMembershipTab").equals(firstDeviceSerialNo),"Device Serail No is not matched");
				LOGGER.info("Device is added to bios group");
				remediationPage.clickOnElementsOfRemediationPage("clearFiltersOnMembershipTab");
				waitForPageLoaded();
				LOGGER.info("Filter on Membership tab are cleared");

			}
			else {
				LOGGER.info("Bios Alert is not present on the page");
			}
		}
	}



	/**
	 * This Test Case TC_C51600945>>[WEXINT][Remediaion]>>Verify Type and SubType fields are showing as list of dropdown values on Remediate My library Modal popup
	 * @throws Exception
	 */
	@Test(priority = 4, groups = { "REGRESSION_INTEGRATIONS"}, description="Testcase ID: TC_C51600945")
	public final void verifyCancelButtonFunctionalityOnRemediationWModalPopup() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEPRemediationPage remediationPage= new WEPRemediationPage(PreDefinedActions.getDriver()).getInstance();
		WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		remediationPage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),
					"No Alert Message not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertMessage"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"),"No Alert Message text is  not match");
			LOGGER.info("No Active Alerts found hence skipping the rest of the testcase");
		} else {
			remediationPage.waitForElementsOfRemediationPage("bitLocketActiveAlert");
			remediationPage.clickOnElementsOfRemediationPage("bitLocketActiveAlert");
			waitForPageLoaded();
			sleeper(2000);
			Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("breadcrumbsAlertHeader"),"BreadcrumbsAlertHeader is not present on Active Alert Page");
			LOGGER.info("Alerts breadcrumbs are validated");
			//remediationPage.actionClickOfRemediationPage("firstDeviceInAlertDetails");
			sleeper(3000);
			if(remediationPage.verifyElementIsEnableOnRemediationPage("RemediateBtnInAlertDetails")) {
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("RemediateBtnInAlertDetails").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.remediate.action")),"Remediate button doesn't exists");
				LOGGER.info("Remediate button is Available");
				remediationPage.actionClickOfRemediationPage("RemediateBtnInAlertDetails");
				waitForPageLoaded();
				sleeper(2000);
				remediationPage.clickOnElementsOfRemediationPage("selectScriptInRemediateWizard");
				remediationPage.clickOnElementsOfRemediationPage("nextButtonRemediateWizard");
				waitForPageLoaded();
				sleeper(2000);
				remediationPage.clickOnElementsOfRemediationPage("cancelBtnOnRemediateWindow");
				waitForPageLoaded();
				sleeper(2000);		
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("RemediateBtnInAlertDetails").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.remediate.action")),"Remediate button doesn't exists");
				LOGGER.info("URL is redirected back to Remediation page and Remediate button is Available");

			}else {
				LOGGER.info("Remediate button is not Available");	
			}
		}
	}

	/**
	 * This Test Case TC_C51266642>>[WEXINT][Remediation] Verify Remediate button is showing for ITAdmin
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "REGRESSION_INTEGRATIONS","PRODUCTION_PULSES_EE_SUSTAIN"}, description="TestcaseID: C51266642", enabled=false)
	public final void verifyWEPRemediationLinkOnPartnerView() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPRemediationPage remediationPage= new WEPRemediationPage(PreDefinedActions.getDriver()).getInstance();
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		alertpage.partnerWithCompanyView(getEnvironmentSpecificData(System.getProperty("environment"), "SCRIPTS_COMPANY_NAME"),CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS); 
		sleeper(3000);
		leftSideMenuVerification();
		waitForPageLoaded();
		if (!alertpage.verifyElementsOfAlertPage("ColumnData")) {
			softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"),
					"No Alert Message not present");
			softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertMessage"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"),"No Alert Message text is  not match");
			LOGGER.info("No Active Alerts found hence skipping the rest of the testcase");
		} else {
			remediationPage.waitForElementsOfRemediationPage("bitLocketActiveAlert");
			remediationPage.clickOnElementsOfRemediationPage("bitLocketActiveAlert");
			waitForPageLoaded();
			sleeper(2000);
			Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("breadcrumbsAlertHeader"),"BreadcrumbsAlertHeader is not present on Active Alert Page");
			//Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("breadcrumbsAlertHeader"),getTextLanguage(LanguageCode, "daas_ui", "alerts.breadcrumbs.title"),"Alert breadcrumb text is  not match");
			Assert.assertEquals(remediationPage.getTextOfWEPRemediationPage("breadcrumbsAlertHeader"),getTextLanguage(LanguageCode, "daas_ui", "active_alerts.appbar.header.title"),"Alert breadcrumb text is  not match");
			Assert.assertTrue(remediationPage.verifyElementsOfRemediationPage("breadcrumbsAlertTitle"),"bread crumb title is not present on Active Alert Page");
			//remediationPage.actionClickOfRemediationPage("firstDeviceInAlertDetails");
			sleeper(2000);
			if(remediationPage.verifyElementIsEnableOnRemediationPage("RemediateBtnInAlertDetails")){
				Assert.assertTrue(remediationPage.getTextOfWEPRemediationPage("RemediateBtnInAlertDetails").equals(remediationPage.getTextLanguage(LanguageCode, "daas_ui", "wex.remediate.actions.remediate.action")),"Remediate button doesn't exists");
				LOGGER.info("Remediate button is Available");
			}else {
				LOGGER.info("Remediate button is not Available and no devices are showing in the  list");
			}
		}

	}

}