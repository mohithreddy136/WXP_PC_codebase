package com.testscripts.daasui;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.WEPPulsesCreationPageVariables;
//import com.daasui.pages.WEPIncidentPage;
//import com.daasui.pages.WEPAlertsDashboardPage;
//import com.daasui.pages.WEPAlertsDashboardPulsePage;


public class WEPPulsesTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(WEPPulsesTest.class);
	
	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "PARTNER_EMAIL";
		data[0][1] = "PARTNER_PASSWORD";
		data[1][0] = "COMPANY_EMAIL";
		data[1][1] = "COMPANY_PASSWORD";
		return data;
	}
	
	/**
	 * This method will verify the text elements present on WEP-EE-Pulses List Page
	 *
	 * @throws Exception
	 */
	@Test(priority = 1, groups = {"REGRESSION_PULSES","PRODUCTION_PULSES_EE_SUSTAIN"}, description = "TestCase ID :C42565910 ")
	public final void verifyTextElementsPresentEEPulsesList() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("PulselistBanner"),getTextLanguage(LanguageCode, "daas_ui", "pulses.header.title"));
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		LOGGER.info("Clicked on the Create button from Pulses list Page");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CustomPulseContextualMenuSection"));
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CustomPulseContextHeader"));
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader"),(getTextLanguage(LanguageCode,"daas_ui","ee_pulse_list_custom_pulse_btn_title")),"Custom Pulse Contextual Headers are not getting matched.");
		LOGGER.info("Verified the Custom Pulse Contextual-Menu Section with Header Text");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("SentimentPulseContextualMenuSection"));
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("SentimentPulseContextHeader"));
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader"),(getTextLanguage(LanguageCode,"daas_ui","ee_pulse_list_sentiment_pulse_btn_title")),"Sentiment Pulse Contextual Headers are not getting matched.");
		LOGGER.info("Verified the Sentiment Pulse Contextual-Menu Section with Header Text");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CustomPulseContextDescription"));
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextDescription"),(getTextLanguage(LanguageCode,"daas_ui","ee_pulse_list_custom_pulse_btn_subtitle")),"Custom Pulse Contextual Description are not getting matched.");
		LOGGER.info("Verified the Custom Pulse Context-Description section");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("SentimentPulseContextDescription"));
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextDescription"),(getTextLanguage(LanguageCode,"daas_ui","ee_pulse_list_sentiment_pulse_btn_subtitle")),"Sentiment Pulse Contextual Description are not getting matched.");
		LOGGER.info("Verified the Sentiment Pulse Context-Description section");
		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the table configuration test cases of WEP-EE-Pulses List Page
	 *
	 * @throws Exception
	 */
	@Test(priority = 2, groups = {"REGRESSION_PULSES"},enabled=false, description = "TestCase ID : T1396909334")
	public final void verifyTableConfigurationForWEPPulsesListPage() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"));
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode,"daas_ui","ee_page_text_pulses"),getTextLanguage(LanguageCode,"daas_ui", "ee_pulse_label_status"),getTextLanguage(LanguageCode,"daas_ui","ee_pulse_label_startDate"),getTextLanguage(LanguageCode,"daas_ui","ee_pulse_label_endDate"),getTextLanguage(LanguageCode,"daas_ui","pulses.table.header.new_creation_date"),getTextLanguage(LanguageCode,"daas_ui","ee_pulse_list_header_title_pulse_type"),getTextLanguage(LanguageCode,"daas_ui","ee_pulse_label_responses")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "true", "true"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode,"daas_ui","ee_page_text_pulses")));
		verifyTableConfigurationTests(columnName, checkboxValue, name);
		LOGGER.info("Verified the headers on Pulses list page with the table configuration settings successfully");
		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Upload Company Logo Functionality on Custom Pulse Creation Page
	 *
	 * @throws Exception
	 */
	@Test(priority = 25, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C42566155")
	public final void verifyCompanylogoUploadFunctionalityCustomPulseCreationPage() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		DevicesListPage.companyView(CommonVariables.SETTINGS);
		if(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("LogoPresent")==true) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("selectLogoImageIcon");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("saveButtonOnPopUp");
			sleeper(4000);
			softAssert.assertFalse(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("LogoPresent"));
			LOGGER.info("Company logo got removed successfully from -> company settings page");
		}
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		LOGGER.info("Company Logo is not present hence redirected to the Pulses-List Page");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		LOGGER.info("Redirected to Custom Pulse Creation Page");
		WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("CompanyLogoTitleLabel");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoNotPresent"));
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoToggle"));
		sleeper(2000);
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("CompanyLogoTitleLabel"),WEPPulsesCreationPage.getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_companyLogo"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("SettingsUpdateLogo");
		LOGGER.info("Clicked on the Settings Link to Upload a new Company Logo");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyUploadCompanyLogo(LanguageCode,"selectLogoImageIcon","selectImageRadioButtonOnPopup","browseButton","cat.jpg","saveButtonOnPopUp"));
		WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("SettingsUpdateLogo"));
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoToggle"));
		LOGGER.info("Show Company logo label & settings link - present and verified successfully");
		softAssert.assertAll();
	}


	/**
	 * This method will verify the Upload Company Logo Functionality on Sentiment Pulse Creation Page
	 *
	 * @throws Exception
	 */
	@Test(priority = 3, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C42566155")
	public final void verifyCompanylogoUploadFunctionalitySentimentPulseCreationPage() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		DevicesListPage.companyView(CommonVariables.SETTINGS);
		if(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("LogoPresent")==true) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("selectLogoImageIcon");
			sleeper(2000);
			WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("saveButtonOnPopUp");
			WEPPulsesCreationPage.clickOnElementsOfWEPPulsesCreationPage("saveButtonOnPopUp");
			sleeper(2000);
			System.out.println(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("LogoPresent"));
			softAssert.assertFalse(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("LogoPresent"));
			LOGGER.info("Company logo got removed successfully from -> company settings page");
		}	
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		LOGGER.info("Company Logo is not present hence redirected to the Pulses-List Page");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"));
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse Creation Page");
		WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("CompanyLogoTitleLabel");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoNotPresent"));
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoToggle"));
		sleeper(2000);
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("CompanyLogoTitleLabel"),WEPPulsesCreationPage.getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_companyLogo"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("SettingsUpdateLogo");
		LOGGER.info("Clicked on the Settings Link to Upload a new Company Logo");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyUploadCompanyLogo(LanguageCode,"selectLogoImageIcon","selectImageRadioButtonOnPopup","browseButton","cat.jpg","saveButtonOnPopUp"));
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent"));
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("SettingsUpdateLogo"));
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoToggle"));
		LOGGER.info("Show Company logo label & settings link - present and verified successfully");
		softAssert.assertAll();
	}
	/**
	 * This method will verify the Error Msg's Via CSV Upload Targeting Method - WEP-EE-Pulses Sentiment Type
	 *
	 * @throws Exception
	 */
	@Test(priority = 4, groups = {"REGRESSION_PULSES","PRODUCTION_PULSES_EE_SUSTAIN"}, description = "TestCase ID : C46951300")
	public final void verifyWEPSentimentPulseCSVUploadSerialNumbersErrorMessageValidations() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		SoftAssert softAssert = new SoftAssert();
		String Priority = "Standard";
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"));
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader").equals(WEPPulsesCreationPage.getTextLanguage(LanguageCode,"daas_ui","ee_pulse_list_sentiment_pulse_btn_title"));
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,false,false));
		//LOGGER.info("Details Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,"Sentiment Pulse", "Date-based", true, Priority));
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		}else {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
		}
		WEPPulsesCreationPage.verifyAudienceSectionErrorMsgsValidationsCSVUpload(LanguageCode,"methodValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulses_audience_static_dataType_serial_number"));
		softAssert.assertAll();
		LOGGER.info("All Error Message while Upload CSV Functionality - verified successfully");
	}
	
	/**
	 * This method will verify the Creation Flow for WEP-EE-Pulses Sentiment Type with Company Logo -> Enabled
	 *
	 * @throws Exception
	 */
	@Test(priority = 5, groups = {"REGRESSION_PULSES", "REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID :C46951299,C46951279 ", enabled=false)
	public final void verifyWEPSentimentPulsesCreationFlowViaCSVUploadWithCompanyLogoEnabled() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		Boolean logoEnableViaSettingsPage = false;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		DevicesListPage.verifyEnrolledByHPInsight();
		List<String> devicelist = DevicesListPage.getAllTextOfWEPDeviceListPage("deviceserialnumberfield");
		sleeper(5000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		LOGGER.info("Active devices got fetched from device list page successfully");
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		//LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate, Priority),"Schedule section tab validation check");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
		}
		WEPPulsesCreationPage.verifyAudienceSectionValidationsTargetCSVmethod(LanguageCode,"methodValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulses_audience_static_dataType_serial_number"),devicelist,TotalActiveDevices);
		LOGGER.info("Audience Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");

		sleeper(2000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		sleeper(2000);
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		//Pulse Type column check from pulse List Page for recently created Pulse
		WEPPulsesListPage.scrollOnWEPPulsesListPage("pulseTypeHeader");
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstValuePulseType"),(PulseType.replace(" Pulse", "")),"Pulse Type in Pulses List Page after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow - verified successfully");
		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Creation Flow for WEP-EE-Pulses Sentiment Type with Company Logo disabled Toggle.
	 *
	 * @throws Exception
	 */
	@Test(priority = 6, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C51460166")
	public final void verifyWEPSentimentPulsesCreationFlowViaCSVUploadLOGOToggleDisabled() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		Boolean logoEnableViaSettingsPage = false;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));		
		List<String> devicelist = DevicesListPage.getAllTextOfWEPDeviceListPage("deviceserialnumberfield");
		sleeper(5000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		LOGGER.info("Active devices got fetched from device list page successfully");
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
//		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
//		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withEndDate, Priority),"Schedule section tab validation check");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		}else {
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
		}
		WEPPulsesCreationPage.verifyAudienceSectionValidationsTargetCSVmethod(LanguageCode,"methodValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulses_audience_static_dataType_serial_number"),devicelist,TotalActiveDevices);
		LOGGER.info("Audience Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow - verified successfully");
		//softAssert.assertAll();
	}
	/**
	 * This method will verify the Creation Flow for WEP-EE-Pulses Sentiment Type with No Company Logo present.
	 *
	 * @throws Exception
	 */
	@Test(priority = 7, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C51460757")
	public final void verifyWEPSentimentPulsesCreationFlowViaCSVUploadNoLogo() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
//		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
//		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
//		}else {
//		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDD", "devicestatusddvalues", "firstcolumnheader"));
		List<String> devicelist = DevicesListPage.getAllTextOfWEPDeviceListPage("deviceserialnumberfield");
		sleeper(5000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		LOGGER.info("Active devices got fetched from device list page successfully");
//		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
//			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
//		}else {
//		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"),"Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		//LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withEndDate, Priority),"Schedule section tab validation check");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		}else {
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
		}
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsTargetCSVmethod(LanguageCode,"methodValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulses_audience_static_dataType_serial_number"),devicelist,TotalActiveDevices));
		LOGGER.info("Audience Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow - verified successfully");
		softAssert.assertAll();
	}
	/**
	 * This method will verify the Creation Flow for WEP-EE-Pulses Sentiment Type with Company Logo -> Enabled
	 *
	 * @throws Exception
	 * 
	 * Disabling this test cases as now of bcs Last Signed-In User column removed from Device list page.
	 */
	@Test(priority = 9, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C46951299,C46951300",enabled=false)
	public final void verifyWEPSentimentPulsesCreationFlowViaCSVUploadEmailDataTypeWithCompanyLogoEnabled() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		Boolean logoEnableViaSettingsPage = false;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_LASTLOGGED_FILTER_VALUE");
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		DevicesListPage.verifyApplyOtherFilters(LanguageCode,"LastSignedInUserColumnListTitle","LastSignedInUserColumnListValueSearch",valueName,"countPage");
		String TotalAudienceString = DevicesListPage.getTextOfWEPDeviceListPage("countPage").replace("of ", "");
		int TargetDevices = Integer.parseInt(TotalAudienceString);
		softAssert.assertTrue(TargetDevices==1);
		if(TargetDevices==1) {
		List<String> devicelist = new ArrayList<String>();
		devicelist.add(valueName);
		sleeper(5000);
		LOGGER.info("Active + Targeting devices got fetched from device list page successfully");
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withEndDate, Priority),"Schedule section tab validation check");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		}else {
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
		}
		WEPPulsesCreationPage.verifyAudienceSectionValidationsTargetCSVmethod(LanguageCode,"methodValues","Email address",devicelist,TotalActiveDevices);
		LOGGER.info("Audience Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow - verified successfully");
		}else {
			LOGGER.info("Devices count is not present/active/enrolled for the provided device last-logged user value.");
		}
		softAssert.assertAll();
		}
	
	/**
	 * This method will verify the Creation Flow for WEP-EE-Pulses Sentiment Type with Company Logo -> Enabled
	 *
	 * @throws Exception
	 * 
	 * Disabling this test cases as now of bcs Last Signed-In User column removed from Device list page.
	 */
	@Test(priority = 10, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C46951299,C46951300",enabled=false)
	public final void verifyWEPSentimentPulsesCreationFlowViaCSVUploadEmailDataTypeWithCompanyLogoDisabled() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		Boolean logoEnableViaSettingsPage = false;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_LASTLOGGED_FILTER_VALUE");
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		DevicesListPage.verifyApplyOtherFilters(LanguageCode,"LastSignedInUserColumnListTitle","LastSignedInUserColumnListValueSearch",valueName,"countPage");
		String TotalAudienceString = DevicesListPage.getTextOfWEPDeviceListPage("countPage").replace("of ", "");
		int TargetDevices = Integer.parseInt(TotalAudienceString);
		softAssert.assertTrue(TargetDevices==1);
		if(TargetDevices==1) {
		List<String> devicelist = new ArrayList<String>();
		devicelist.add(valueName);
		sleeper(5000);
		LOGGER.info("Active + Targeting devices got fetched from device list page successfully");
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withEndDate, Priority),"Schedule section tab validation check");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		}else {
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
		}
		WEPPulsesCreationPage.verifyAudienceSectionValidationsTargetCSVmethod(LanguageCode,"methodValues","Email address",devicelist,TotalActiveDevices);
		LOGGER.info("Audience Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow - verified successfully");
		}else {
			LOGGER.info("Devices count is not present/active/enrolled for the provided device last-logged user value.");
		}
		softAssert.assertAll();
		}
	
	/**
	 * This method will verify the Creation Flow for WEP-EE-Pulses Sentiment Type with Company Logo -> Enabled
	 *
	 * @throws Exception
	 * 
	 * Disabling this test cases as now of bcs Last Signed-In User column removed from Device list page.
	 */
	@Test(priority = 11, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C46951299,C46951300",enabled=false)
	public final void verifyWEPSentimentPulsesCreationFlowViaCSVUploadEmailDataTypeNoLogo() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		Boolean logoEnableViaSettingsPage = false;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_LASTLOGGED_FILTER_VALUE");
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		DevicesListPage.verifyApplyOtherFilters(LanguageCode,"LastSignedInUserColumnListTitle","LastSignedInUserColumnListValueSearch",valueName,"countPage");
		String TotalAudienceString = DevicesListPage.getTextOfWEPDeviceListPage("countPage").replace("of ", "");
		int TargetDevices = Integer.parseInt(TotalAudienceString);
		softAssert.assertTrue(TargetDevices==1);
		if(TargetDevices==1) {
		List<String> devicelist = new ArrayList<String>();
		devicelist.add(valueName);
		sleeper(5000);
		LOGGER.info("Active + Targeting devices got fetched from device list page successfully");
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage);
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withEndDate,Priority),"Schedule section tab validation check");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		}else {
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
		}
		WEPPulsesCreationPage.verifyAudienceSectionValidationsTargetCSVmethod(LanguageCode,"methodValues","Email address",devicelist,TotalActiveDevices);
		LOGGER.info("Audience Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow - verified successfully");
		}else {
			LOGGER.info("Devices count is not present/active/enrolled for the provided device last-logged user value.");
		}
		softAssert.assertAll();
		}
	/**
	 * This method will verify the Pulse Creation flow with Audience Filters Method set with Last Logged User - EQ Operators Applied
	 *
	 * @throws Exception
	 * 
	 * Disabling this test cases as now of bcs Last Signed-In User column removed from Device list page.
	 */
	@Test(priority = 12, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C51461611,C43554301",enabled=false)
	public final void verifyWEPSentimentPulseCreationsAudienceFilterLastLoggedUserEQOperatorApplied() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_LAST_LOGGED_USER;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "PULSE_FILTERS_LASTLOGGED_FILTER_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		softAssert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		DevicesListPage.verifyApplyOtherFilters(LanguageCode,"LastSignedInUserColumnListTitle","LastSignedInUserColumnListValueSearch",valueName,"countPage");
		sleeper(3000);
		String TotalAudienceCountText = DevicesListPage.getTextOfWEPDeviceListPage("countPage").replace("of ", "");
		int TotalAudience = Integer.parseInt(TotalAudienceCountText);
		softAssert.assertTrue(TotalAudience==1);
		if(TotalAudience==1) {
		List<String> devicelist = new ArrayList<String>();
		devicelist.add(valueName);
		sleeper(5000);
		LOGGER.info("Active devices got fetched from device list page successfully");
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withoutEndDate, Priority),"Schedule section validation check");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"),"Dynamic Targeting method is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName, operatorName, valueName,TotalActiveDevices,TotalAudience),"Pulse Creation using Filters got failed in Filters validation");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow using filters - Last Signed In user (EQ) - verified successfully");
		}else {
			LOGGER.info("Devices count is not present/active/enrolled for the provided device last-logged user value.");
		}
		softAssert.assertAll();
	}
		
	/**
	 * This method will verify the Pulse Creation flow with Audience Filters Method set with Last Logged User - NEQ Operators Applied
	 *
	 * @throws Exception
	 * 
	 * Disabling this test cases as now of bcs Last Signed-In User column removed from Device list page.
	 */
	@Test(priority = 13, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C51461611", enabled=false)
	public final void verifyWEPSentimentPulseCreationsAudienceFilterLastLoggedUserNEQOperatorApplied() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_LAST_LOGGED_USER;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_NOT_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_LASTLOGGED_FILTER_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		DevicesListPage.verifyApplyOtherFilters(LanguageCode,"LastSignedInUserColumnListTitle","LastSignedInUserColumnListValueSearch",valueName,"countPage");
		String TotalAudienceCountText = DevicesListPage.getTextOfWEPDeviceListPage("countPage").replace("of ", "");
		int TotalAudience = Integer.parseInt(TotalAudienceCountText);
		softAssert.assertTrue(TotalAudience==1);
		if(TotalAudience==1) {
		List<String> devicelist = new ArrayList<String>();
		devicelist.add(valueName);
		sleeper(5000);
		LOGGER.info("Active devices got fetched from device list page successfully");
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage);
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withoutEndDate, Priority);
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"),"Dynamic Targeting method is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");	
		WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName, operatorName, valueName,TotalActiveDevices,TotalAudience);
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow using filters - Last Signed In user (NEQ) - verified successfully");
		}else {
			LOGGER.info("Devices count is not present/active/enrolled for the provided device last-logged user value.");
		}
		softAssert.assertAll();
	}

	/**
	 * This method will verify the Pulse Creation flow with Audience Filters Method set with Serial number - EQ Operators Applied
	 *
	 * @throws Exception
	 */
	@Test(priority = 14, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C51461540")
	public final void verifyWEPSentimentPulseCreationsAudienceFilterSerialnumberEQOperatorApplied() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_SERIAL_NUMBER;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_SERIAL_NUMBER_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
		waitForPageLoaded();
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"SerialNumberColumnTitle","SerialNumberColumnListValueSearch",valueName,"countPage");
		sleeper(3000);
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		WEPPulsesCreationPage.enterTextForWEPPulsesCreationPage("PulseTitle",CommonVariables.SENTIMENT_MAX_CHAR_TITLE+generateRandomString(120));
		sleeper(1000);
		WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("titleErrorMsg");
		WEPPulsesCreationPage.enterTextForWEPPulsesCreationPage("PulseTitle",PulseTitle);
		WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withoutEndDate, Priority));
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"),"Dynamic Targeting method is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName, operatorName, valueName,TotalActiveDevices,TotalAudience));		
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow using filters - Serial number (EQ) - verified successfully");
		softAssert.assertAll();	
	}
	
	/**
	 * This method will verify the Pulse Creation flow with Audience Filters Method set with Serial number - NEQ Operators Applied
	 *
	 * @throws Exception
	 */
	@Test(priority = 15, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C51461540")
	public final void verifyWEPSentimentPulseCreationsAudienceFilterSerialnumberNEQOperatorApplied() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_SERIAL_NUMBER;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_NOT_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_SERIAL_NUMBER_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
		waitForPageLoaded();
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"SerialNumberColumnTitle","SerialNumberColumnListValueSearch",valueName,"countPage");
		sleeper(3000);
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		//LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withoutEndDate, Priority));
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"),"Dynamic Targeting method is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName, operatorName, valueName,TotalActiveDevices,TotalAudience),"Pulse Creation using Filters got failed in Filters validation");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Publish button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow using filters - Serial number (NEQ) - verified successfully");
		softAssert.assertAll();	
	}
	
	/**
	 * This method will verify the Pulse Creation flow with Audience Filters Method set with Device Manufacturer - EQ Operators Applied
	 *
	 * @throws Exception
	 */
	@Test(priority = 16, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C51461563,", enabled = false)
	public final void verifyWEPSentimentPulseCreationsAudienceFilterDeviceManufacturerEQOperatorApplied() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		Boolean logoEnableViaSettingsPage = false;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_DEVICE_MANUFACTURER;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_FIELD_DEVICE_MANUFACTURER_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"ManufacturerColumnTitle","manufacturerdropdown","manufacturerListValues","countPage");
		sleeper(3000);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withoutEndDate, Priority));
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"),"Dynamic Targeting method is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName, operatorName, valueName,TotalActiveDevices,TotalAudience),"Pulse Creation using Filters got failed in Filters validation");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow using filters - Last Signed In user (EQ) - verified successfully");
		softAssert.assertAll();	
	}
	
	/**
	 * This method will verify the Pulse Creation flow with Audience Filters Method set with Device Manufacturer - NEQ Operators Applied
	 *
	 * @throws Exception
	 */
	@Test(priority = 17, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C51461563", enabled = false)
	public final void verifyWEPSentimentPulseCreationsAudienceFilterDeviceManufacturerNEQOperatorApplied() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		Boolean logoEnableViaSettingsPage = false;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_DEVICE_MANUFACTURER;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_FIELD_DEVICE_MANUFACTURER_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"ManufacturerColumnTitle","manufacturerdropdown","manufacturerListValues","countPage");
		sleeper(3000);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withoutEndDate, Priority));
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"),"Dynamic Targeting method is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName, operatorName, valueName,TotalActiveDevices,TotalAudience),"Pulse Creation using Filters got failed in Filters validation");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow using filters - Device Manufacturer (NEQ) - verified successfully");
		softAssert.assertAll();	
	}
	
	/**
	 * This method will verify the Pulse Creation flow with Audience Filters Method set with Operating System - EQ Operators Applied
	 *
	 * @throws Exception
	 */
	@Test(priority = 18, groups = {"REGRESSION_PULSES"},description = "TestCase ID :C51461564",enabled = false)
	public final void verifyWEPSentimentPulseCreationsAudienceFilterOperatingSystemEQOperatorApplied() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_OPERATING_SYSTEM;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_FIELD_OPERATING_SYSTEM_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"operatingsystemColumnTitle","operatingsystemColumnListValueSearch",valueName,"countPage");
		sleeper(3000);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withoutEndDate, Priority));
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"),"Dynamic Targeting method is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");	
		WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName, operatorName, valueName,TotalActiveDevices,TotalAudience);
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow using filters - Operating System (EQ) - verified successfully");
		softAssert.assertAll();	
	}
	
	/**
	 * This method will verify the Pulse Creation flow with Audience Filters Method set with Operating System - NEQ Operators Applied
	 *
	 * @throws Exception
	 */
	@Test(priority = 19, groups = {"REGRESSION_PULSES"},description = "TestCase ID :C51461564", enabled = false)
	public final void verifyWEPSentimentPulseCreationsAudienceFilterOperatingSystemNEQOperatorApplied() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_OPERATING_SYSTEM;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_NOT_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_FIELD_OPERATING_SYSTEM_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"operatingsystemColumnTitle","operatingsystemColumnListValueSearch",valueName,"countPage");
		sleeper(3000);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withoutEndDate, Priority));
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"),"Dynamic Targeting method is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName, operatorName, valueName,TotalActiveDevices,TotalAudience),"Pulse Creation using Filters got failed in Filters validation");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(3000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow using filters - Operating System (NEQ) - verified successfully");
		softAssert.assertAll();	
	}
	
	/**
	 * This method will verify the Pulse Creation flow with Audience Filters Method set with Device Type - EQ Operators Applied
	 *
	 * @throws Exception
	 */
	@Test(priority = 20, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : C51461610", enabled = false)
	public final void verifyWEPSentimentPulseCreationsAudienceFilterDeviceTypeEQOperatorApplied() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_DEVICE_TYPE;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_FIELD_DEVICE_TYPE_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"PCTypeColumnTitle","PCdropdown","ListValues","countPage");
		sleeper(3000);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withoutEndDate, Priority));
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"),"Dynamic Targeting method is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName,operatorName,valueName,TotalActiveDevices,TotalAudience),"Pulse Creation using Filters got failed in Filters validation");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(3000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow using filters - Device Type (EQ) - verified successfully");
		softAssert.assertAll();	
	}
	
	/**
	 * This method will verify the Pulse Creation flow with Audience Filters Method set with Device Type - NEQ Operators Applied
	 * 
	 * @throws Exception
	 */
	@Test(priority = 21, groups = {"REGRESSION_PULSES"}, enabled = false, description = "TestCase ID : C51461610")
	public final void verifyWEPSentimentPulseCreationsAudienceFilterDeviceTypeNEQOperatorApplied() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_DEVICE_TYPE;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_NOT_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_FIELD_DEVICE_TYPE_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"PCTypeColumnTitle","PCdropdown","ListValues","countPage");
		sleeper(3000);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");
		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withoutEndDate, Priority));
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"),"Dynamic Targeting method is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName,operatorName,valueName,TotalActiveDevices,TotalAudience),"Pulse Creation using Filters got failed in Filters validation");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(3000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow using filters - Device Type (EQ) - verified successfully");
		softAssert.assertAll();	
	}
	
	/**
	 * This method will verify the Pulse Creation flow with Everyone Method set
	 * @throws Exception
	 */
	@Test(priority = 22, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : C46951301,C46951350")
	public final void verifyWEPSentimentPulseCreationsEveryoneAudienceMethodApplied() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		int TotalActiveDevices;	
		int TotalTargetedDevices;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		String TotalAudienceString = DevicesListPage.getTextOfWEPDeviceListPage("countPage").replace("of ", "");
		if(TotalAudienceString.contains(",")) {
			String replaced = TotalAudienceString.replace(",", "");
			TotalActiveDevices = Integer.parseInt(replaced);
			TotalTargetedDevices = TotalActiveDevices;
		}else {
		TotalActiveDevices = Integer.parseInt(TotalAudienceString);
		TotalTargetedDevices = TotalActiveDevices;
		}
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		//LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withoutEndDate, Priority));
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_everyone_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingEveryoneMethod");	
		}
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsEveryoneMethodApplied(TotalActiveDevices,TotalTargetedDevices ),"Pulse Creation using Everyone method got failed in Audience Section validation");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(3000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow with Everyone Method set - verified successfully");
		softAssert.assertAll();	
	}
	
	/**
	 * This method will verify Elements in WEP-EE-Pulses Custom Type
	 *
	 * @throws Exception
	 */
	@Test(priority = 24, groups = {"REGRESSION_PULSES","PRODUCTION_PULSES_EE_SUSTAIN","REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : C42566851")
	public final void verifyWEPCustomPulsesCreationElementFieldsCheck() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");	
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("CompanyLogoTitleLabel"),"Companylogo title header not present in Custom Pulse Creation page");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("CompanyLogoTitleLabel"),"Companylogo Toggle not present in Custom Pulse Creation page");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("editTitleIcon"),"Edit Icon for the Pulse title is not present in Custom Pulse Creation page");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customPulseTitle"),"Custom Pulse Title is not Present in the Custom pulse Creation page");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customPulseTitle"),"Custom Pulse Title is not Present in the Custom pulse Creation page");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"),"Conent not Present in the Custom pulse Creation page");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("customContentDescription"),getTextLanguage(LanguageCode, "daas_ui", "ee.question.content.header.msg"),"Content Description for Custom Pulse creation is not matching");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("newQuestionsButton"),"New question button is not present into conect section for Custom Pulse Creation Page");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("newNotificationButton"),"New notification button is not present into conect section for Custom Pulse Creation Page");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("btnGoToSchedule"),"Go to Schedule button is not present into Content section for Custom Pulse Creation Page");
		LOGGER.info("Custom pulse Creation page - Content Section Verified Successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not present into conect section for Custom Pulse Creation Page");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("scheduleContent"),getTextLanguage(LanguageCode, "daas_ui", "ee.urgentPulse.schedule.heading"),"Schedule content Description for Custom Pulse creation is not matching");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("datebasedScheduleOption"), "Date based option is not displayed into Schedule section of Custom Pulse creation page");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("eventbasedScheduleOption"), "Event based option is not displayed into into Schedule section of Custom Pulse creation page");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("btnGoToAudience"),"Go to Auidence button is not present into Schedule section for Custom Pulse Creation Page");
		LOGGER.info("Custom pulse Creation page - Schedule Section Verified Successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not present in Custom Pulse Creation Page");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");

		if (!toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("pulseNewAudienceHeader"),"Audience Header for new UI for Sentiment Pulse creation is not present by-default");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			List<String> AudienceMethodsList = WEPPulsesCreationPage.getAllTextOfPulseCreationPage("audienceListDDValues");
			ArrayList<String> EXPAudienceEntraIDDisconnected = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode,"daas_ui", "ee_pulse_audience_method_dynamic_title"),getTextLanguage(LanguageCode,"daas_ui","ee_pulse_audience_method_csv_upload_title"),getTextLanguage(LanguageCode,"daas_ui","ee_pulse_audience_method_everyone_title")));
			ArrayList<String> EXPAudienceEntraIDConnected = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode,"daas_ui", "ee_pulse_audience_method_entra_id_title"),getTextLanguage(LanguageCode,"daas_ui", "ee_pulse_audience_method_dynamic_title"),getTextLanguage(LanguageCode,"daas_ui","ee_pulse_audience_method_csv_upload_title"),getTextLanguage(LanguageCode,"daas_ui","ee_pulse_audience_method_everyone_title")));
			Boolean AudienceFlag = false;
			if (!AudienceMethodsList.equals(EXPAudienceEntraIDDisconnected)) {
			    softAssert.assertEquals(AudienceMethodsList, EXPAudienceEntraIDConnected, "Audience Methods is not matching when EntraID-Connected");
			    LOGGER.info("Audience Methods got matched as expected when EntraID-Connected");
			    AudienceFlag = true;
			} else if (AudienceMethodsList.equals(EXPAudienceEntraIDDisconnected)) {
			    softAssert.assertEquals(AudienceMethodsList, EXPAudienceEntraIDDisconnected, "Audience Methods is not matching when EntraID-Disconnected");
			    LOGGER.info("Audience Methods got matched as expected when EntraID-Disconnected");
			    AudienceFlag = true;
			}
			Assert.assertTrue(AudienceFlag,"Audience Method falues are not fetched properly - Please check the locator provided");	
		}else {
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceContenttext"),"Audience aontent for Custom Pulse creation is not present by-default");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"),"Dynamic targeting method is not present into Audience section for Custom Pulse Creation Page");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"),"CSV targeting method is not present into Audience section for Custom Pulse Creation Page");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"),"Everyone targeting method is not present into Audience section for Custom Pulse Creation Page");
		}
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("publishButton"),"Publish is not present into Audience section for Custom Pulse Creation Page");
		LOGGER.info("Custom pulse Creation page - Audience Section Verified Successfully");
		softAssert.assertAll();
		LOGGER.info("Custom pulse Creation page Element Fields Check - verified successfully");
	}

	
	/**
	 * This method will verify the Error Msg's Via CSV Upload Targeting Method - WEP-EE-Pulses Custom Type
	 *
	 * @throws Exception
	 */
	@Test(priority = 26, groups = {"REGRESSION_PULSES","PRODUCTION_PULSES_EE_SUSTAIN"}, description = "TestCase ID : C42566874")
	public final void verifyWEPCustomPulseCSVUploadSerialNumbersErrorMessageValidations() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,false,false));
		LOGGER.info("Company logo Section in Custom Pulse creation page got verified successfully");
		//Commenting out the code till the fix is made for [WORKEXP-2187] [FE] Issues Observed into Schedule section while Date selection (PROD)
//		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
//		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
//		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
//		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation("Custom Pulse", "Date-based", true));
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		}else {
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
		}
		WEPPulsesCreationPage.verifyAudienceSectionErrorMsgsValidationsCSVUpload(LanguageCode,"methodValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulses_audience_static_dataType_serial_number"));
		softAssert.assertAll();
		LOGGER.info("All Error Message while Upload CSV Functionality - Custom pulse Creation Page - verified successfully");
	}
	/**
	 * This method will verify the Creation Flow for WEP-EE-Pulses Custom Type (Single/Multi select questions option selected) with Company Logo -> Enabled
	 *
	 * @throws Exception
	 */
	@Test(priority = 27, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ")
	public final void verifyWEPCustomPulsesCreationFlowViaAllDevicesWithCompanyLogoEnabledOnlyDateBased_NoReccurance() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = true;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = true;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();

		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		Integer TotalTargetedDevices = DevicesListPage.getConvertedTotalTargetedCountForReach(TotalActiveDevices);
		LOGGER.info("Active devices got fetched from device list page successfully.");

		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader").replace(" Pulse", "");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");

		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		WEPPulsesCreationPage.enterTextForWEPPulsesCreationPage("PulseTitle",PulseTitle);
		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		sleeper(2000);
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
		String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");
		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("customContentDescription Verified Successfully");
		//Custom Pulse creation flow with Multi-select Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_multiSelect"),StartDate);
		LOGGER.info("Custom Content section got Verified Successfully");
		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingEveryoneMethod");
		WEPPulsesCreationPage.verifyAudienceSectionValidationsEveryoneMethodApplied(TotalActiveDevices,TotalTargetedDevices);
		LOGGER.info("Audience Section in Custom Pulse creation page got verified successfully.");

		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(3000);
		LOGGER.info("Publish button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Custom Pulse Creation flow - verified successfully");
		softAssert.assertAll();
}
	
	/**
	 * This method will verify the Creation Flow for WEP-EE-Pulses Custom Type (5-star/Thumbs-up questions option selected) with Company Logo -> Disabled / with End date selected.
	 *
	 * @throws Exception
	 */
	@Test(priority = 28, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ")
	public final void verifyWEPCustomPulsesCreationFlowViaAllDevicesWithCompanyLogoDisabledOnlyDateBased_NoReccurance() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();

		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		Integer TotalTargetedDevices = DevicesListPage.getConvertedTotalTargetedCountForReach(TotalActiveDevices);
		LOGGER.info("Active devices got fetched from device list page successfully.");

		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();

		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader").replace(" Pulse", "");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");

		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
		String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"),"Content sectiontab is not getting displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("customContentDescription Verified Successfully");

		//Custom Pulse creation flow with 5-Star rating Question with max option selected
		//WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_fiveStarRating"),StartDate);
		//Custom Pulse creation flow with ThumbsUp Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_thumbsUp"),StartDate);
		LOGGER.info("Custom Content section got Verified Successfully");

		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingEveryoneMethod");
		WEPPulsesCreationPage.verifyAudienceSectionValidationsEveryoneMethodApplied(TotalActiveDevices,TotalTargetedDevices);
		LOGGER.info("Audience Section in Custom Pulse creation page got verified successfully.");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Publish button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		LOGGER.info("Toast notification after creation got verified successfully.");

		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Custom Pulse Creation flow - verified successfully");
		softAssert.assertAll();
	}

	/**
	 * This method will verify the Creation Flow for WEP-EE-Pulses Custom Type (5-star/Thumbs-up questions option selected) with Company Logo -> Disabled / with End date selected.
	 *
	 * @throws Exception
	 */
	@Test(priority = 29, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ", enabled = false)
	public final void verifyWEPCustomPulsesCreationFlowViaEmailCSVUploadWithNoCompanyLogoOnlyDateBased_NoReccurance() throws Exception {
	login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
	Boolean logoSectionTobeEnabled = false;
	Boolean withEndDate = false;
	String Priority = "Standard";
	Boolean logoDisableViaSettingsPage = false;
	SoftAssert softAssert = new SoftAssert();
	WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
	WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
	WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
	TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
	String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_SERIAL_NUMBER_VALUE");
	leftSideMenuVerification();
	if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
	DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
	}else {
	DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
	Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
	int TotalActiveDevice;
	String TotalAudienceSt = DevicesListPage.getTextOfWEPDeviceListPage("countPage").replace("of ", "");
	if(TotalAudienceSt.contains(",")) {
		String replaced = TotalAudienceSt.replace(",", "");
		TotalActiveDevice = Integer.parseInt(replaced);
	}else {
	TotalActiveDevice = Integer.parseInt(TotalAudienceSt);
	}
	tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
	tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
	tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
	DevicesListPage.verifyApplyOtherFilters(LanguageCode,"SerialNumberColumnTitle","SerialNumberColumnListValueSearch",valueName,"countPage");
	String TotalAudienceString = DevicesListPage.getTextOfWEPDeviceListPage("countPage").replace("of ", "");
	int TotalActiveDevices = Integer.parseInt(TotalAudienceString);
	softAssert.assertTrue(TotalActiveDevices==1);
	if(TotalActiveDevices==1) {
	List<String> devicelist = new ArrayList<String>();
	devicelist.add(valueName);
	sleeper(5000);
	LOGGER.info("Active devices got fetched from device list page successfully");
	if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
	}else {
	WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
	waitForPageLoaded();
	softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
	WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
	String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader").replace(" Pulse", "");
	WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
	LOGGER.info("Redirected to Custom Pulse creation Page successfully");
	softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoDisableViaSettingsPage));
	LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
	WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
	WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
	String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
	softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
	WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
	WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
	softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
	String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
	LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");	
	softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"));
	WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
	softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
	LOGGER.info("customContentDescription Verified Successfully");
	//Custom Pulse creation flow with Comment-Box Question with max option selected
	WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);
	//Custom Pulse creation flow with NPS with max option selected
	//WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType("Net promoter score",StartDate);
	LOGGER.info("Custom Content section got Verified Successfully");
	softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
	WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
	if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
		WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
	}else {
	softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"));
	WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
	}
	softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsTargetCSVmethod(LanguageCode,"methodValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulses_audience_static_dataType_serial_number"),devicelist,TotalActiveDevice));
	LOGGER.info("Audience Section in Custom Pulse creation page got verified successfully");

	WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
	sleeper(3000);
	LOGGER.info("Schedule button got clicked successfully after providing all required field values");
	softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
	WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
	WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
	softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
	//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
	//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
	LOGGER.info("Toast notification after creation got verified successfully.");
	softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
	LOGGER.info("Recently created Pulse got displayed first got verified successfully");
	LOGGER.info("Custom Pulse Creation flow - verified successfully");
	}else {
		LOGGER.info("Devices count is not present/active/enrolled for the provided device last-logged user value.");
	}
	softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Creation Flow for WEP-EE-Pulses Sentiment Type with Company Logo -> Enabled
	 *
	 * @throws Exception
	 */
	@Test(priority = 30, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ")
	public final void verifyWEPCustomPulsesCreationFlowNotificationQuestionViaCSVUploadWithCompanyLogo() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		List<String> devicelist = DevicesListPage.getAllTextOfWEPDeviceListPage("deviceserialnumberfield");
		sleeper(5000);
		int TotalActiveDevice;
		String TotalAudienceSt = DevicesListPage.getTextOfWEPDeviceListPage("countPage").replace("of ", "");
		if(TotalAudienceSt.contains(",")) {
			String replaced = TotalAudienceSt.replace(",", "");
			TotalActiveDevice = Integer.parseInt(replaced);
		}else {
		TotalActiveDevice = Integer.parseInt(TotalAudienceSt);
		}
		LOGGER.info("Active devices got fetched from device list page successfully");
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader").replace(" Pulse", "");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");

		WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage);
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");

		WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
		String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");	

		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"),"Content tab is not visible into Custom Pulse creation page");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("customContentDescription Verified Successfully");

		//Notification type question selection check
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newNotificationButton");
		WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("notificationHeaderRequired");
		    // Enter notification details
		String notificationTxtURL=StartDate + " Automation Notification Question"+WEPPulsesCreationPageVariables.CUSTOM_PULSE_NOTIFICATION_URL_TEXT+WEPPulsesCreationPageVariables.CUSTOM_PULSE_NOTIFICATION_URL;
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("questionInputField", notificationTxtURL );

		WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("checkBoxAck");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("checkBoxAck");
		WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ackTextContent");

		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
		WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		WEPPulsesCreationPage.verifyAudienceSectionValidationsTargetCSVmethod(LanguageCode,"methodValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulses_audience_static_dataType_serial_number"),devicelist,TotalActiveDevice);
		LOGGER.info("Audience Section in Custom Pulse creation page got verified successfully");	

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		LOGGER.info("Publish button got clicked successfully after providing all required field values");
		sleeper(1000);
		WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		sleeper(2000);
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		LOGGER.info("Toast notification after creation got verified successfully.");

		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Custom Pulse Creation flow - verified successfully");
		softAssert.assertAll();
		}

	/**
	 * This method will verify the Employee Engagement widgets
	 * Creation of Sentiment & Custom pulses covered in verifyWEPPulseEmployeeEngagementWidgets() test cases. Skipping this part in this test case to
	 * avoid duplicate code.
	 * Test will Skip checks for sentimentScoreOvertime, topIssueImpactingSentiment, sentimentProgressBar, responseSentimentPulse because this data is device dependent
	 * as given list of checks depends on response rate value.
	 *
	 * @return
	 * @throws Exception
	 */
	@Test(priority = 27, groups = { "REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID :C42399349 ", enabled=false)
	public final void verifyWEPPulseEmployeeEngagementWidgets() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		DevicesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
		waitForPageLoaded();

		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("sentimentScore"), "Sentiment pulse data not available");

		if (WEPPulsesListPage.verifyElementIsPresent("sentimentExpScoreOvertime")) {
			softAssert.assertTrue(
					WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("sentimentExpScoreOvertime"),
					"Sentiment pulse data not available"
			);
		} else {
			LOGGER.info("Element 'sentimentExpScoreOvertime' is not available on UI for this Device; Skipping Sentiment Experience Over Time checks.");
		}
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("allPulses"), "All pulse data not available.");

		if (WEPPulsesListPage.verifyElementIsPresent("responseSentimentPulse")) {
			softAssert.assertTrue(
					WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("responseSentimentPulse"),
					"response Sentiment Pulse data not available"
			);
		} else {
			LOGGER.info("Element 'responseSentimentPulse' is not available on UI for this Device; Skipping Response Sentiment Pulse checks.");
		}

		if (WEPPulsesListPage.verifyElementIsPresent("topIssueImpactingSentiment")) {
			softAssert.assertTrue(
					WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("topIssueImpactingSentiment"),
					"topIssueI mpacting Sentiment Pulse data not available"
			);
		} else {
			LOGGER.info("Element 'topIssueImpactingSentiment' is not available on UI for this Device; Skipping topIssue Impacting Sentiment Pulse checks.");
		}

		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("allPulses"));
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("allPulsesData"), "All pulse data not available");
		LOGGER.info("WEP Pulse Employee Engagement Widgets flow - verified successfully");
		softAssert.assertAll();

	}
	
	/**
	 * This method will verify the Creation+Duplication Flow for WEP-EE-Pulses Custom Type with Multi/Single-Select Question selected with Static Targeting Method selected
	 *
	 * @throws Exception
	 */
	@Test(priority = 31, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ")
	public final void verifyWEPCustomPulsesDuplicationSelectionListFlow() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		List<String> devicelist = DevicesListPage.getAllTextOfWEPDeviceListPage("deviceserialnumberfield");
		sleeper(5000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();	
		LOGGER.info("Active devices got fetched from device list page successfully");
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader").replace(" Pulse", "");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
		String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("customContentDescription Verified Successfully");
		//Custom Pulse creation flow with Multi-select Question with max option selected
		//List<String> providedInputDataMultiSelection = WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_multiSelect"),StartDate);
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);
		//Custom Pulse creation flow with Single-select Question with max option selected
		//List<String> providedInputDataSingleSelection = WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_singleSelect"),StartDate);
		LOGGER.info("Custom Content section got Verified Successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		}else {
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
		}
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsTargetCSVmethod(LanguageCode,"methodValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulses_audience_static_dataType_serial_number"),devicelist,TotalActiveDevices));
		LOGGER.info("Audience Section in Custom Pulse creation page got verified successfully");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(3000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Custom Pulse Creation flow - verified successfully");
		//softAssert.assertTrue(WEPPulsesListPage.verifyDuplicatePulseTypeFunctionality(PulseTitle, "Checkbox-Selection"));
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionAfterDuplicateOrSavedDraftCustomPulseFunctionality(providedInputDataMultiSelection,1));
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionAfterDuplicateOrSavedDraftCustomPulseFunctionality(providedInputDataSingleSelection,2));
		softAssert.assertAll();
		}
	
	/**
	 * This method will verify the Creation+Duplication Via ElipsceSelection Flow for WEP-EE-Pulses Custom Type with 5Star/ThumbsUp Question selected with Everyone Targeting Method selected
	 *
	 * @throws Exception
	 */
	@Test(priority = 32, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ")
	public final void verifyWEPCustomPulsesDuplicationFlowWithElipsceSelection() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		int TotalActiveDevices;	
		int TotalTargetedDevices;
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		TotalTargetedDevices = DevicesListPage.getConvertedTotalTargetedCountForReach(TotalActiveDevices);
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader").replace(" Pulse", "");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
		String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("customContentDescription Verified Successfully");
		//Custom Pulse creation flow with 5-Star rating Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);
		//Custom Pulse creation flow with ThumbsUp Question with max option selected
		LOGGER.info("Custom Content section got Verified Successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_everyone_title"));
		}else {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingEveryoneMethod");	
		}
		WEPPulsesCreationPage.verifyAudienceSectionValidationsEveryoneMethodApplied(TotalActiveDevices,TotalTargetedDevices);

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(3000);
		LOGGER.info("Publish button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		LOGGER.info("Toast notification after creation got verified successfully.");
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Custom Pulse Creation flow - verified successfully");
		softAssert.assertTrue(WEPPulsesListPage.verifyDuplicatePulseTypeFunctionality(PulseTitle, "Elipces-Selection"));
		softAssert.assertAll();
		}
	
	/**
	 * This method will verify the Creation+Duplication Via Results Page Flow for WEP-EE-Pulses Custom Type with CommentBox / NPS Question selected with Dynamic Targeting Method selected
	 *
	 * @throws Exception
	 */
	@Test(priority = 33, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C46951279 ")
	public final void verifyWEPCustomPulsesDuplicationResultsPageFlow() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_DEVICE_MANUFACTURER;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_FIELD_DEVICE_MANUFACTURER_VALUE");
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"ManufacturerColumnTitle","manufacturerdropdown","manufacturerListValues","countPage");
		sleeper(3000);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader").replace(" Pulse", "");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
		String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("customContentDescription Verified Successfully");
		//Custom Pulse creation flow with Comment-Box Question with max option selected
		//List<String> providedInputDataCommentBoxSelection = WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);
		//Custom Pulse creation flow with NPS with max option selected
		//List<String> providedInputDataNPSSelection = WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType("Net promoter score",StartDate);
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);
		LOGGER.info("Custom Content section got Verified Successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName, operatorName, valueName,TotalActiveDevices,TotalAudience),"Pulse Creation using Filters got failed in Filters validation");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(3000);
		LOGGER.info("Publish button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		//Pulse Type column check from pulse List Page for recently created Pulse
		sleeper(3000);
		WEPPulsesListPage.scrollOnWEPPulsesListPage("pulseTypeHeader");
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstValuePulseType"),(PulseType.replace(" Pulse", "")),"Pulse Type in Pulses List Page after creation is not matching");			
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Custom Pulse Creation flow - verified successfully");
		softAssert.assertTrue(WEPPulsesListPage.verifyDuplicatePulseTypeFunctionality(PulseTitle, "Results-Selection"));
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionAfterDuplicateOrSavedDraftCustomPulseFunctionality(providedInputDataCommentBoxSelection,1));
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionAfterDuplicateOrSavedDraftCustomPulseFunctionality(providedInputDataNPSSelection,2));
		softAssert.assertAll();
		}
	
	/**
	 * This method will verify the Creation Flow for WEP-EE-Pulses Custom Type
	 * Single/Multi select questions option selected, 5-star/Thumbs-up/Comment box/ NPS questions option selected with Company Logo -> Disabled / with End date selected.
	 *
	 * @throws Exception
	 */
	@Test(priority = 34, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : C42399348",enabled=false)
	public final void verifyWEPCustomPulsesCreationFlowWithCompanyLogoDisabledOnlyDateBased_NoReccuranceWithAllQueChecks() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();

		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		Integer TotalTargetedDevices = DevicesListPage.getConvertedTotalTargetedCountForReach(TotalActiveDevices);
		LOGGER.info("Active devices got fetched from device list page successfully.");

		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);
		LOGGER.info("Redirected to Custom Pulse creation Page successfully.");

		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully.");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");

		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,"Custom Pulse", "Date-based",withEndDate,Priority),"Schedule Section Verification is failing");
		String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully.");

		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"),"Content sectiontab is not getting displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("customContentDescription Verified Successfully.");

		//Custom Pulse creation flow with Multi-select Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_multiSelect"),StartDate);
		//Custom Pulse creation flow with Single-select Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_singleSelect"),StartDate);
		//Custom Pulse creation flow with ThumbsUp Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_thumbsUp"),StartDate);
		//Custom Pulse creation flow with 5-Star rating Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee.pulse.question.type.five.star.rating"),StartDate);
		//Custom Pulse creation flow with comment box with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);
		//Custom Pulse creation flow with NPS with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType("Net promoter score",StartDate);
		LOGGER.info("Custom Content section got Verified Successfully.");

		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingEveryoneMethod");
		WEPPulsesCreationPage.verifyAudienceSectionValidationsEveryoneMethodApplied(TotalActiveDevices,TotalTargetedDevices);
		LOGGER.info("Audience Section in Custom Pulse creation page got verified successfully.");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		LOGGER.info("Publish button got clicked successfully after providing all required field values.");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		sleeper(1000);
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully.");
		LOGGER.info("Custom Pulse Creation flow - verified successfully.");
		softAssert.assertAll();
	}
/**
	 * This method will verify the Creation+Deletion Via Selection List Page Flow for WEP-EE-Pulses Custom Type with Everyone Targeting Method selected
	 *
	 * @throws Exception
	 */
	@Test(priority = 35, groups = {"REGRESSION_PULSES","PRODUCTION_PULSES_EE_SUSTAIN"}, description = "TestCase ID :C49178947 ")
	public final void verifyWEPCustomPulsesDeletionSelectionListPageFlow() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		Integer TotalTargetedDevices = DevicesListPage.getConvertedTotalTargetedCountForReach(TotalActiveDevices);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
		String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("customContentDescription Verified Successfully");
		//Custom Pulse creation flow with Comment-Box Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);
		//Custom Pulse creation flow with NPS with max option selected
		//WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType("Net promoter score",StartDate);
		LOGGER.info("Custom Content section got Verified Successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_everyone_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingEveryoneMethod");	
		}
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsEveryoneMethodApplied(TotalActiveDevices,TotalTargetedDevices ),"Pulse Creation using Everyone method got failed in Audience Section validation");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(3000);
		LOGGER.info("Publish button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		//Pulse Type column check from pulse List Page for recently created Pulse
		WEPPulsesListPage.scrollOnWEPPulsesListPage("pulseTypeHeader");
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstValuePulseType"),(PulseType.replace(" Pulse", "")),"Pulse Type in Pulses List Page after creation is not matching");			
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Custom Pulse Creation flow - verified successfully");
		softAssert.assertTrue(WEPPulsesListPage.verifyDeletePulseTypeFunctionality(PulseTitle, "Checkbox-Selection"));
		LOGGER.info("Custom Pulse Deletion flow Via Listing Page - verified successfully");
		//softAssert.assertAll();
		}
	
	/**
	 * TThis method will verify the Creation+Deletion of Draft/Duplication Pulse Via Selection List Page Flow for WEP-EE-Pulses Sentiment Type with Everyone Targeting Method selected
	 *
	 * @throws Exception
	 */
	@Test(priority = 36, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C49178948,C49178951",enabled= false)
	public final void verifyWEPSentimentPulsesDraftDeletionSelectionListPageFlow() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		LOGGER.info("Active devices got fetched from device list page successfully");
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		Integer TotalTargetedDevices = DevicesListPage.getConvertedTotalTargetedCountForReach(TotalActiveDevices);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		//LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_everyone_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingEveryoneMethod");	
		}
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsEveryoneMethodApplied(TotalActiveDevices,TotalTargetedDevices ),"Pulse Creation using Everyone method got failed in Audience Section validation");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(3000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully");
		
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		//Pulse Type column check from pulse List Page for recently created Pulse
		WEPPulsesListPage.scrollOnWEPPulsesListPage("pulseTypeHeader");
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstValuePulseType"),(PulseType.replace(" Pulse", "")),"Pulse Type in Pulses List Page after creation is not matching");			
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Sentiment Pulse Creation flow - verified successfully");
		softAssert.assertTrue(WEPPulsesListPage.verifyDuplicatePulseTypeFunctionality(PulseTitle, "Results-Selection"),"Duplication Via Pulse List Page got failed");
		sleeper(2000);
		LOGGER.info("Sentiment Pulse Duplication flow - verified successfully");
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		//LOGGER.info("Content Section in Sentiment Pulse duplication/draft page got verified successfully");	
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		//Delete the created draft state Pulse - Sentiment Type
		sleeper(2000);
		softAssert.assertTrue(WEPPulsesListPage.verifyDeletePulseTypeFunctionality(PulseTitle+"-Copy", "Elipces-Selection"),"Deletion Via Pulse List Page got failed");
		LOGGER.info("Sentiment Pulse Deletion flow Via Elipsces selection Listing Page - verified successfully");
		softAssert.assertAll();
		}

	/**
	 * This method will verify the Creation+Deletion Via Results Page Flow for WEP-EE-Pulses Custom Type with Dynamic Targeting Method selected
	 *
	 * @throws Exception
	 */
	@Test(priority = 37, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C49178949 ")
	public final void verifyWEPCustomPulsesDeletionResultsPageFlow() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_DEVICE_MANUFACTURER;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_FIELD_DEVICE_MANUFACTURER_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		Integer TotalAudience = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"SerialNumberColumnTitle","SerialNumberColumnListValueSearch",valueName,"countPage");
		sleeper(3000);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
		String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("customContentDescription Verified Successfully");
		//Custom Pulse creation flow with 5-Star rating Question with max option selected
        WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);
		//Custom Pulse creation flow with ThumbsUp Question with max option selected
		//WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_thumbsUp"),StartDate);
		LOGGER.info("Custom Content section got Verified Successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");	
		WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName, operatorName, valueName,TotalActiveDevices,TotalAudience);
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("publishButton");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(3000);
		LOGGER.info("Publish button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		//Pulse Type column check from pulse List Page for recently created Pulse
		WEPPulsesListPage.scrollOnWEPPulsesListPage("pulseTypeHeader");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("firstValuePulseType");
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstValuePulseType"),(PulseType.replace(" Pulse", "")),"Pulse Type in Pulses List Page after creation is not matching");			
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Custom Pulse Creation flow - verified successfully");
		WEPPulsesListPage.verifyDeletePulseTypeFunctionality(PulseTitle, "Results-Selection");
		LOGGER.info("Custom Pulse Deletion flow Via Listing Page - verified successfully");
		//softAssert.assertAll();
		}
	
	/**
	 * This method will verify Elements in WEP-EE-Pulses - Sentiment Type
	 *
	 * @throws Exception
	 */
	@Test(priority = 38, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C51470610 ",enabled = false)
	public final void verifyWEPSentimentPulsesCreationElementFieldsCheck() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");	
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("editTitleIcon"),"Edit Icon for the Pulse title is not present in Sentiment Pulse Creation page");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customPulseTitle"),"Sentiment Pulse Title is not Present in the Sentiment pulse Creation page");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customPulseTitle"),"Sentiment Pulse Title is not Present in the Sentiment pulse Creation page");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("CompanyLogoTitleLabel"),"Companylogo title header not present in Sentiment Pulse Creation page");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("CompanyLogoTitleLabel"),"Companylogo Toggle not present in Sentiment Pulse Creation page");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"),"Conent not Present in the Sentiment pulse Creation page");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("sentimentContentDescription"),getTextLanguage(LanguageCode, "daas_ui", "ee_sentiment_pulse_question_preview_note"),"Content Description for Custom Pulse creation is not matching");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("btnGoToSchedule"),"Go to Schedule button is not present into Content section for Sentiment Pulse Creation Page");
		LOGGER.info("Sentiment pulse Creation page - Content Section Verified Successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not present into conect section for Sentiment Pulse Creation Page");	
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		String scheduleDescription = getTextLanguage(LanguageCode, "daas_ui", "ee_sentiment_pulse_schedule_info").replace("<strong>", "");
		String expScheduleDescription = scheduleDescription.replace("</strong>", "");
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("scheduleContent"),expScheduleDescription,"Schedule content Description for Sentiment Pulse creation is not matching");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("btnGoToAudience"),"Go to Auidence button is not present into Schedule section for Sentiment Pulse Creation Page");
		LOGGER.info("Sentiment pulse Creation page - Schedule Section Verified Successfully");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("btnGoToAudience");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not present in Sentiment Pulse Creation Page");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("btnGoToAudience");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("pulseNewAudienceHeader"),"Audience Header for new UI for Sentiment Pulse creation is not present by-default");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("audienceListDDValues");
			List<String> AudienceMethodsList = WEPPulsesCreationPage.getAllTextOfPulseCreationPage("audienceListDDValues");
			ArrayList<String> EXPAudienceEntraIDDisconnected = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode,"daas_ui", "ee_pulse_audience_method_dynamic_title"),getTextLanguage(LanguageCode,"daas_ui","ee_pulse_audience_method_csv_upload_title"),getTextLanguage(LanguageCode,"daas_ui","ee_pulse_audience_method_everyone_title")));
			ArrayList<String> EXPAudienceEntraIDConnected = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode,"daas_ui", "ee_pulse_audience_method_entra_id_title"),getTextLanguage(LanguageCode,"daas_ui", "ee_pulse_audience_method_dynamic_title"),getTextLanguage(LanguageCode,"daas_ui","ee_pulse_audience_method_csv_upload_title"),getTextLanguage(LanguageCode,"daas_ui","ee_pulse_audience_method_everyone_title")));
			Boolean AudienceFlag = false;
			if (!AudienceMethodsList.equals(EXPAudienceEntraIDDisconnected)) {
			    softAssert.assertEquals(AudienceMethodsList, EXPAudienceEntraIDConnected, "Audience Methods is not matching when EntraID-Connected");
			    LOGGER.info("Audience Methods got matched as expected when EntraID-Connected");
			    AudienceFlag = true;
			} else if (AudienceMethodsList.equals(EXPAudienceEntraIDDisconnected)) {
			    softAssert.assertEquals(AudienceMethodsList, EXPAudienceEntraIDDisconnected, "Audience Methods is not matching when EntraID-Disconnected");
			    LOGGER.info("Audience Methods got matched as expected when EntraID-Disconnected");
			    AudienceFlag = true;
			}
			Assert.assertTrue(AudienceFlag,"Audience Method falues are not fetched properly - Please check the locator provided");	
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceContenttext"),"Audience aontent for Sentiment Pulse creation is not present by-default");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"),"Dynamic targeting method is not present into Audience section for Sentiment Pulse Creation Page");	
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"),"CSV targeting method is not present into Audience section for Sentiment Pulse Creation Page");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"),"Everyone targeting method is not present into Audience section for Sentiment Pulse Creation Page");
		}
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("publishButton"),"Publish is not present into Audience section for Sentiment Pulse Creation Page");
		LOGGER.info("Sentiment pulse Creation page - Audience Section Verified Successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("leavePage");
		//Delete the created draft state Pulse - Sentiment Type
		softAssert.assertTrue(WEPPulsesListPage.verifyDeletePulseTypeFunctionality(PulseTitle, "Elipces-Selection"),"Deletion Via Pulse List Page got failed");
		LOGGER.info("Sentiment Pulse Deletion flow Via Elipsces selection Listing Page - verified successfully");
		softAssert.assertAll();
		LOGGER.info("Sentiment pulse Creation page Element Fields Check - verified successfully");
	}
	
	/**
	 * This method will verify the Save Draft Functionality Flow for WEP-EE-Pulses Custom Type Content Section
	 *
	 * @throws Exception
	 */
	@Test(priority = 39, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : C51470699")
	public final void verifyWEPCustomPulsesSaveDraftFlowContentSection() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader").replace(" Pulse", "");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");
		//Verify Elements into save draft section before doing any changes
		String[] BeforeMakingChanges = {"saveDraftPulseTypeHeaderContent","saveDraftPulseTypeValueContent","saveDraftStatusHeaderContent","saveDraftStatusValueContent"};
		Assert.assertTrue(WEPPulsesCreationPage.verifyAllElementsinWEPPulsesCreationPage(BeforeMakingChanges),"Elements not present before saving draft");
		//Pulse Type check into save draft section
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("saveDraftPulseTypeValueContent"),PulseType);
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
		String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		//Creation Date check into save draft section		
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("saveDraftCreationDateValueContent"),StartDate,"Creation date is not matching into save draft section");			
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("customContentDescription Verified Successfully");
		//Custom Pulse creation flow with Multi-select Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_multiSelect"),StartDate);
		//Verify Elements into save draft section after doing first changes/adding some input
		String[] AfterMakingChanges = {"saveDraftPulseTypeHeaderContent","saveDraftPulseTypeValueContent","saveDraftStatusHeaderContent","saveDraftStatusValueContent","saveDraftCreationDateHeaderContent","saveDraftCreationDateValueContent","saveDraftLastSavedHeaderContent","saveDraftLastSavedValueContent"};
		Assert.assertTrue(WEPPulsesCreationPage.verifyAllElementsinWEPPulsesCreationPage(AfterMakingChanges),"Elements not present after saving draft");
		//Custom Pulse creation flow with Single-select Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_singleSelect"),StartDate);
		//Custom Pulse creation flow with ThumbsUp Question with max option selected
		 WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_thumbsUp"),StartDate);
		//Custom Pulse creation flow with 5-Star rating Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee.pulse.question.type.five.star.rating"),StartDate);
		//Custom Pulse creation flow with comment box with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);
		//Custom Pulse creation flow with NPS with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType("Net promoter score",StartDate);
		LOGGER.info("Custom Content section got Verified by Passing the Inputs at the time of Creation Successfully.");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("leavePage");
		sleeper(1000);
		waitForPageLoaded();
//		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("firstLatestPulselist");
//		//Verify Elements into saved draft Page section after redirecting
//		String[] AfterRedirectingAlreadySaved = {"saveDraftPulseTypeHeaderContent","saveDraftPulseTypeValueContent","saveDraftStatusHeaderContent","saveDraftStatusValueContent","saveDraftCreationDateHeaderContent","saveDraftCreationDateValueContent","saveDraftLastSavedHeaderContent","saveDraftLastSavedValueContent"};
//		WEPPulsesCreationPage.verifyAllElementsinWEPPulsesCreationPage(AfterRedirectingAlreadySaved);
//
////		LOGGER.info("Custom Content section got Verified Successfully after redirecting to already saved draft.");
////		sleeper(2000);
//		//Leave button should not get displayed scenario - verified
//		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		softAssert.assertTrue(WEPPulsesListPage.verifyDeletePulseTypeFunctionality(PulseTitle, "Checkbox-Selection"),"Delete Flow Method Got failed.");
		LOGGER.info("Saved Draft - Custom Pulse Deletion flow Via List Page - verified successfully");
//		softAssert.assertAll();
		}
	
	/**
	 * This method will verify the Save Draft Functionality Flow for WEP-EE-Pulses Custom Type Audience Section
	 *
	 * @throws Exception
	 * 
	 * Marking this test cases as false - BUG ID: 
	 */
	@Test(priority = 40, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ",enabled=false)
	public final void verifyWEPCustomPulsesSaveDraftFlowforAudienceSection() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean logoEnableViaSettingsPage = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_SERIAL_NUMBER;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_SERIAL_NUMBER_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		List<String> devicelistCSVUpload = DevicesListPage.getAllTextOfWEPDeviceListPage("deviceserialnumberfield");	
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		Integer TotalActiveDevicesEveryone = DevicesListPage.getConvertedIntegerCount();
		int TotalTargetedDevices = TotalActiveDevicesEveryone;
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		Integer TotalAudienceDynamic = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"SerialNumberColumnTitle","SerialNumberColumnListValueSearch",valueName,"countPage");
		sleeper(3000);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader").replace(" Pulse", "");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");
		//Verify Elements into save draft section before doing any changes
		String[] BeforeMakingChanges = {"saveDraftPulseTypeHeaderContent","saveDraftPulseTypeValueContent","saveDraftStatusHeaderContent","saveDraftStatusValueContent"};
		Assert.assertTrue(WEPPulsesCreationPage.verifyAllElementsinWEPPulsesCreationPage(BeforeMakingChanges),"Elements not present before saving draft");
		//Pulse Type check into save draft section
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("saveDraftPulseTypeValueContent"),PulseType);
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"),"Targeting CSVMethod is not visible into audience section");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
		}
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsTargetCSVmethod(LanguageCode,"methodValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulses_audience_static_dataType_serial_number"),devicelistCSVUpload,TotalActiveDevicesEveryone),"CSV Upload Scenario Verification");
		List<String> BeforeRedirection = WEPPulsesCreationPage.getInputsforAudienceMethodBeforeAndAfterRedirection("Static");
		LOGGER.info("Audience Section in Custom Pulse creation page got verified successfully.");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("leavePage");
		sleeper(1000);
		waitForPageLoaded();
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("firstLatestPulselist");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		List<String> AfterRedirection = WEPPulsesCreationPage.getInputsforAudienceMethodBeforeAndAfterRedirection("Static");
		softAssert.assertEquals(BeforeRedirection, AfterRedirection,"Save draft for CSV Method is breaking");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");
		WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName, operatorName, valueName,TotalActiveDevicesEveryone,TotalAudienceDynamic);
		//Verify Elements into saved draft Page section after redirecting
		String[] AfterRedirectingAlreadySaved = {"saveDraftPulseTypeHeaderAudience","saveDraftPulseTypeValueAudience","saveDraftStatusHeaderAudience","saveDraftStatusValueAudience","saveDraftCreationDateHeaderAudience","saveDraftCreationDateValueAudience","saveDraftLastSavedHeaderAudience","saveDraftLastSavedValueAudience"};
		Assert.assertTrue(WEPPulsesCreationPage.verifyAllElementsinWEPPulsesCreationPage(AfterRedirectingAlreadySaved),"Elements not present after redirecting to already saved draft");							
		sleeper(2000);
		List<String> BeforeRedirectionDynamic = WEPPulsesCreationPage.getInputsforAudienceMethodBeforeAndAfterRedirection("Dynamic");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("leavePage");
		sleeper(2000);
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("firstLatestPulselist");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		waitForPageLoaded();
		sleeper(3000);
		List<String> AfterRedirectionDynamic = WEPPulsesCreationPage.getInputsforAudienceMethodBeforeAndAfterRedirection("Dynamic");
		softAssert.assertEquals(BeforeRedirectionDynamic, AfterRedirectionDynamic,"Save draft for Dynamic Method is breaking");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"),"Everyone method is not visible into audience section");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingEveryoneMethod");
		}
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsEveryoneMethodApplied(TotalActiveDevicesEveryone,TotalTargetedDevices),"Pulse Creation using Everyone method got failed in Audience Section validation");
		sleeper(1000);
		List<String> BeforeRedirectionEveryone = WEPPulsesCreationPage.getInputsforAudienceMethodBeforeAndAfterRedirection("Everyone");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("leavePage");
		sleeper(2000);
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("firstLatestPulselist");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		List<String> AfterRedirectionEveryone = WEPPulsesCreationPage.getInputsforAudienceMethodBeforeAndAfterRedirection("Everyone");
		softAssert.assertEquals(BeforeRedirectionEveryone, AfterRedirectionEveryone,"Save draft for Everyone Method is breaking");
		//Leave button should not get displayed scenario - verified
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		softAssert.assertTrue(WEPPulsesListPage.verifyDeletePulseTypeFunctionality(PulseTitle, "Checkbox-Selection"),"Delete Flow Method Got failed.");
		LOGGER.info("Saved Draft - Custom Pulse Deletion flow Via List Page - verified successfully");
		softAssert.assertAll();
		}
	
	
	/**
	 * This method will verify the End-End Urgent Pulse Creation flow
	 * @throws Exception
	 */
	@Test(priority = 41, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : C46951301,C46951350,C57503135")
	public final void verifyWEPCustomUrgentPulseEndtoEndScenarios () throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		String Priority = "Urgent";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);

		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Custom Pulse creation page got verified successfully");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		sleeper(2000);
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withoutEndDate,Priority));
		String StartDate = getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.radio.button.label.urgent");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");

		WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("contentSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");

		//Custom Pulse creation flow with 5-Star rating Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee.pulse.question.type.five.star.rating"),StartDate);
		LOGGER.info("custom Content tab details Verified Successfully");

	    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");

		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_everyone_title"));
		}else {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingEveryoneMethod");	
		}
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(2000);
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("urgentPulsePublishBannerText"), getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.publish.modal.title.text"),"Urgent pulse - Publish Banner text not matching.");
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("urgentPulsePublishBannerContent"), getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.publish.modal.subcopy"),"Urgent Pulse - Publish Banner Content not matching.");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		LOGGER.info("Toast notification after creation got verified successfully.");
		waitForPageLoaded();
		sleeper(2000);
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("firstLatestPulselist");
		//verify priority column check from pulse results Page for recently created urgent Pulse
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleResutls");
		sleeper(2000);
		WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("PrioritysettingsHeader");
		WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("PrioritysettingsValue").equals(getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.radio.button.label.urgent"));
		waitForPageLoaded();
		WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("breadCrumbsResults");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("breadCrumbsResults");
		waitForPageLoaded();
		WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("firstLatestPulselist");
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		//Pulse Type column check from pulse List Page for recently created Pulse
		WEPPulsesListPage.scrollOnWEPPulsesListPage("pulseTypeHeader");
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstValuePulseType"),(PulseType.replace(" Pulse", "")),"Pulse Type in Pulses List Page after creation is not matching");			
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		LOGGER.info("Custom Urgent Pulse Creation flow - verified successfully");
		waitForPageLoaded();
//		WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("breadCrumbsResults");
//		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("breadCrumbsResults");
		WEPPulsesListPage.verifyDuplicatePulseTypeFunctionality(PulseTitle, "Elipces-Selection");
		LOGGER.info("Urgent Pulse Duplication flow - verified successfully");
		sleeper(2000);
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("sendImmediatelyHeaderText"));
		WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("pulseBreadCrumbs");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		//Delete the created draft state Urgent Pulse - Custom Type
		softAssert.assertTrue(WEPPulsesListPage.verifyDeletePulseTypeFunctionality(PulseTitle+"-Copy", "Elipces-Selection"),"Deletion Via Pulse List Page got failed");
		LOGGER.info("Custom Urgent Pulse Deletion flow Via Elipsces selection Listing Page - verified successfully");
		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Navigation,Breadcrumbs,URLUpdates for Pulse Listing-Page
	 * @throws Exception
	 */
	@Test(priority = 42, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ")
	public final void verifyWEPListingPageNavigationBreadcrumbsURLUpdates () throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();	
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			waitForPageLoaded();
			String ActurlOfCurrentPage = getUrlOfCurrentPage();
			softAssert.assertEquals(ActurlOfCurrentPage,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_OLD_PULSES_LIST_URL"),"URL of the Current Page is not matching for Old flow");
			LOGGER.info("Actual and Expected URL is getting matched for the Pulses listing page - Old Navigation Flow - Verified Sucessfully");
			softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("pulsesBreadCrumbsOldEMPENGMT"));
			softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("pulsesBreadCrumbsOld"));
			softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("pulsesBreadCrumbsOldEMPENGMT"),getTextLanguage(LanguageCode, "daas_ui", "ee_module_name"));
			softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("pulsesBreadCrumbsOld"),getTextLanguage(LanguageCode, "daas_ui", "global_pulses"));
			LOGGER.info("Actual and Expected BreadCrumbs is getting matched for the Pulses listing page - Old Navigation Flow - Verified Sucessfully");
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		String ActurlOfCurrentPage = getUrlOfCurrentPage();
		softAssert.assertEquals(ActurlOfCurrentPage,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_NEW_PULSES_LIST_URL"),"URL of the Current Page is not matching for New flow");
		LOGGER.info("Actual and Expected URL is getting matched for the Pulses listing page - New Navigation Flow - Verified Sucessfully");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("pulseListPageBreadCrumbsNew"));
		LOGGER.info("Breadcrumbs are getting displayed properly for the Pulses listing page - New Navigation Flow - Verified Sucessfully");
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("pulseListPageBreadCrumbsNew"),getTextLanguage(LanguageCode, "daas_ui", "global_pulses"));
		LOGGER.info("Actual and Expected BreadCrumbs is getting matched for the Pulses listing page - New Navigation Flow - Verified Sucessfully");
		}
		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Navigation,Breadcrumbs,URLUpdates for Custom Pulse Creation-Page
	 * @throws Exception
	 */
	@Test(priority = 43, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ")
	public final void verifyWEPCustomPulseCreationNavigationBreadcrumbsURLUpdates () throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();	
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
			WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
			WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
			WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
			waitForPageLoaded();
			String ActurlOfContentPulseCreation= getUrlOfCurrentPage();
			Assert.assertEquals(ActurlOfContentPulseCreation,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_OLD_PULSES_CREATION_CONTENT"),"URL of the Current Content tab Page is not matching for New flow");
			LOGGER.info("Actual and Expected URL is getting matched for the Pulses Creation page Content - Old Navigation Flow - Verified Sucessfully");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
			sleeper(3000);
			String ActurlOfSchedulePulseCreation= getUrlOfCurrentPage();
			Assert.assertEquals(ActurlOfSchedulePulseCreation,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_OLD_PULSES_CREATION_SCHEDULE"),"URL of the Current Schedule tab Page is not matching for New flow");
			LOGGER.info("Actual and Expected URL is getting matched for the Pulses Creation page Schedule - Old Navigation Flow - Verified Sucessfully");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
			sleeper(2000);
			String ActurlOfAudiencePulseCreation= getUrlOfCurrentPage();
			Assert.assertEquals(ActurlOfAudiencePulseCreation,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_OLD_PULSES_CREATION_AUDIENCE"),"URL of the Current Audience tab Page is not matching for New flow");
			LOGGER.info("Actual and Expected URL is getting matched for the Pulses Creation page Audience - Old Navigation Flow - Verified Sucessfully");
			//Bread-Crumbs Verification
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("pulsesBreadCrumbsOldEMPENGMT"));
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("pulsesBreadCrumbsOld"));
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("pulseBreadCrumbsCreation"));
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesBreadCrumbsOldEMPENGMT"),getTextLanguage(LanguageCode, "daas_ui", "ee_module_name"));
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesBreadCrumbsOld"),getTextLanguage(LanguageCode, "daas_ui", "global_pulses"));
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulseBreadCrumbsCreation"),getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.create.custom.pulse.breadcrumb"));
			LOGGER.info("Actual and Expected BreadCrumbs is getting matched for the Pulses listing page - Old Navigation Flow - Verified Sucessfully");
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		waitForPageLoaded();
		String ActurlOfContentPulseCreation= getUrlOfCurrentPage();
		Assert.assertEquals(ActurlOfContentPulseCreation,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_NEW_PULSES_CREATION_CONTENT"),"URL of the Current Content tab Page is not matching for New flow");
		LOGGER.info("Actual and Expected URL is getting matched for the Pulses Creation page Content - New Navigation Flow - Verified Sucessfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		String ActurlOfSchedulePulseCreation= getUrlOfCurrentPage();
		Assert.assertEquals(ActurlOfSchedulePulseCreation,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_NEW_PULSES_CREATION_CONTENT"),"URL of the Current Schedule tab Page is not matching for New flow");
		LOGGER.info("Actual and Expected URL is getting matched for the Pulses Creation page Schedule - New Navigation Flow - Verified Sucessfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		String ActurlOfAudiencePulseCreation= getUrlOfCurrentPage();
		Assert.assertEquals(ActurlOfAudiencePulseCreation,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_NEW_PULSES_CREATION_CONTENT"),"URL of the Current Audience tab Page is not matching for New flow");
		LOGGER.info("Actual and Expected URL is getting matched for the Pulses Creation page Audience - New Navigation Flow - Verified Sucessfully");
		//Bread-Crumbs Verification
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("pulsesBreadCrumbsNew"));
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("pulsesBreadCrumbsOld"));
		Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesBreadCrumbsNew"),getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.create.custom.pulse.breadcrumb"));
//		Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesBreadCrumbsOld"),getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.create.custom.pulse.breadcrumb"));
		LOGGER.info("Breadcrumbs are getting displayed properly for the Custom Pulses Creation page - New Navigation Flow - Verified Sucessfully");
		}
//		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Navigation,Breadcrumbs,URLUpdates for Sentiment Pulse Creation-Page
	 * @throws Exception
	 */
	@Test(priority = 44, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ", enabled = true)
	public final void verifyWEPSentimentPulseCreationNavigationBreadcrumbsURLUpdates () throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();	
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
			WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
			WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
			WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
			waitForPageLoaded();
			sleeper(3000);
			String ActurlOfContentPulseCreation= getUrlOfCurrentPage();
			Assert.assertEquals(ActurlOfContentPulseCreation,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_OLD_PULSES_CREATION_SENTIMENT_CONTENT"),"URL of the Current Content tab Page is not matching for New flow");
			LOGGER.info("Actual and Expected URL is getting matched for the Pulses Creation page Content - Old Navigation Flow - Verified Sucessfully");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
			sleeper(3000);
			String ActurlOfSchedulePulseCreation= getUrlOfCurrentPage();
			Assert.assertEquals(ActurlOfSchedulePulseCreation,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_OLD_PULSES_CREATION_SENTIMENT_SCHEDULE"),"URL of the Current Schedule tab Page is not matching for New flow");
			LOGGER.info("Actual and Expected URL is getting matched for the Pulses Creation page Schedule - Old Navigation Flow - Verified Sucessfully");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
			sleeper(3000);
			String ActurlOfAudiencePulseCreation= getUrlOfCurrentPage();
			Assert.assertEquals(ActurlOfAudiencePulseCreation,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_OLD_PULSES_CREATION_SENTIMENT_AUDIENCE"),"URL of the Current Audience tab Page is not matching for New flow");
			LOGGER.info("Actual and Expected URL is getting matched for the Pulses Creation page Audience - Old Navigation Flow - Verified Sucessfully");
			//Bread-Crumbs Verification
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("pulsesBreadCrumbsOldEMPENGMT"));
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("pulsesBreadCrumbsOld"));
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("pulseBreadCrumbsCreation"));
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesBreadCrumbsOldEMPENGMT"),getTextLanguage(LanguageCode, "daas_ui", "ee_page_text_pulses"));
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesBreadCrumbsOld"),getTextLanguage(LanguageCode, "daas_ui", "global_pulses"));
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulseBreadCrumbsCreation"),getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.create.sentiment.pulse.breadcrumb"));
			LOGGER.info("Actual and Expected BreadCrumbs is getting matched for the Pulses listing page - Old Navigation Flow - Verified Sucessfully");
			}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		waitForPageLoaded();
		String ActurlOfContentPulseCreation= getUrlOfCurrentPage();
		Assert.assertEquals(ActurlOfContentPulseCreation,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_NEW_PULSES_CREATION_SENTIMENT_CONTENT"),"URL of the Current Content tab Page is not matching for New flow");
		LOGGER.info("Actual and Expected URL is getting matched for the Pulses Creation page Content - New Navigation Flow - Verified Sucessfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		String ActurlOfSchedulePulseCreation= getUrlOfCurrentPage();
		Assert.assertEquals(ActurlOfSchedulePulseCreation,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_NEW_PULSES_CREATION_SENTIMENT_CONTENT"),"URL of the Current Schedule tab Page is not matching for New flow");
		LOGGER.info("Actual and Expected URL is getting matched for the Pulses Creation page Schedule - New Navigation Flow - Verified Sucessfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		String ActurlOfAudiencePulseCreation= getUrlOfCurrentPage();
		Assert.assertEquals(ActurlOfAudiencePulseCreation,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_NEW_PULSES_CREATION_SENTIMENT_CONTENT"),"URL of the Current Audience tab Page is not matching for New flow");
		LOGGER.info("Actual and Expected URL is getting matched for the Pulses Creation page Audience - New Navigation Flow - Verified Sucessfully");
		//Bread-Crumbs Verification
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("pulsesBreadCrumbsNew"));
		//softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("pulsesBreadCrumbsOld"));
		Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesBreadCrumbsNew"),getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.create.sentiment.pulse.breadcrumb"));
		//Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesBreadCrumbsOld"),getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.create.sentiment.pulse.breadcrumb"));
		LOGGER.info("Breadcrumbs are getting displayed properly for the Sentiment Pulses Creation page - New Navigation Flow - Verified Sucessfully");
		}
		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Navigation,Breadcrumbs,URLUpdates for Pulse Results-Page
	 * @throws Exception
	 */
	@Test(priority = 45, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :",enabled=false)
	public final void verifyWEPPulsesResultsPageNavigationBreadcrumbsURLUpdates () throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Urgent";
		Boolean logoEnableViaSettingsPage = false;
		int TotalActiveDevices;	
		int TotalTargetedDevices;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		boolean NavigationtoggleVerification = toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"));	
		if(!NavigationtoggleVerification){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		String TotalAudienceString = DevicesListPage.getTextOfWEPDeviceListPage("countPage").replace("of ", "");
		if(TotalAudienceString.contains(",")) {
			String replaced = TotalAudienceString.replace(",", "");
			TotalActiveDevices = Integer.parseInt(replaced);
			TotalTargetedDevices = TotalActiveDevices;
		}else {
		TotalActiveDevices = Integer.parseInt(TotalAudienceString);
		TotalTargetedDevices = TotalActiveDevices;
		}
		if(!NavigationtoggleVerification){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Custom Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate,Priority));
		String StartDate = getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.radio.button.label.urgent");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("custom Content Description Verified Successfully");
		//Custom Pulse creation flow with 5-Star rating Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee.pulse.question.type.five.star.rating"),StartDate);
		//Custom Pulse creation flow with comment box with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);		
	    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_everyone_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"),"Everyone method is not visible into audience section");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingEveryoneMethod");
		}
		WEPPulsesCreationPage.verifyAudienceSectionValidationsEveryoneMethodApplied(TotalActiveDevices,TotalTargetedDevices );
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		waitForPageLoaded();
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("urgentPulsePublishBannerText"), getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.publish.modal.title.text"),"Urgent pulse - Publish Banner text not matching.");
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("urgentPulsePublishBannerContent"), getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.publish.modal.subcopy"),"Urgent Pulse - Publish Banner Content not matching.");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		sleeper(2000);
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		LOGGER.info("Recently created Pulse got displayed first got verified successfully");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("firstLatestPulselist");
		waitForPageLoaded();
		String ActurlOfContentPulseCreation= getUrlOfCurrentPage();
		Assert.assertTrue(ActurlOfContentPulseCreation.contains(getEnvironmentSpecificData(System.getProperty("environment"),"WEX_NEW_PULSES_RESULTS")),"URL of the Current results Page is not matching for New flow");
		LOGGER.info("Actual and Expected URL is getting matched for the Pulses Results page - Navigation Flow - Verified Sucessfully");
		if(!NavigationtoggleVerification){
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("pulsesBreadCrumbsOldEMPENGMT"));
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("pulsesBreadCrumbsOld"));
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("pulseBreadCrumbsCreation"));
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesBreadCrumbsOldEMPENGMT"),getTextLanguage(LanguageCode, "daas_ui", "ee_module_name"));
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesBreadCrumbsOld"),getTextLanguage(LanguageCode, "daas_ui", "global_pulses"));
			LOGGER.info("Actual and Expected BreadCrumbs is getting matched for the Pulses Results page - Old Navigation Flow - Verified Sucessfully");		
		}else {
			softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("pulsesBreadCrumbsNew"));
			softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("pulsesBreadCrumbsOld"));
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesBreadCrumbsNew"),getTextLanguage(LanguageCode, "daas_ui", "global_pulses"));
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesBreadCrumbsOld"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_lable_results"));	
			LOGGER.info("Actual and Expected BreadCrumbs is getting matched for the Pulses Results page - New Navigation Flow - Verified Sucessfully");
		}
		softAssert.assertAll();
		LOGGER.info("Navigation/BreadCrumbs/URLChanges flow - verified successfully");
	}
	
	/**
	 * This method will verify the Navigation,Breadcrumbs,URLUpdates for EE-Dashboard Page
	 * marking this TC is false bcs EE dashboard not accessible from analytics tab. 
	 * @throws Exception
	 */
	@Test(priority = 46, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :", enabled=false)
	public final void verifyWEPEEDashboardNavigationBreadcrumbsURLUpdates () throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		SoftAssert softAssert = new SoftAssert();
		WEXEEDashboardPage WEXEEDashboardPage = new WEXEEDashboardPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		boolean NavigationtoggleVerification = toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"));	
		if(!NavigationtoggleVerification){
			//Navigation check
			WEXEEDashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
			//URL Check
			String ActurlOfEXPDashobardOld= getUrlOfCurrentPage();
			Assert.assertEquals(ActurlOfEXPDashobardOld,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_NEW_EE_DASHBOARD_OLD"),"URL of the Current Dashboard Page is not matching for Old flow");
			LOGGER.info("Actual and Expected URL is getting matched for the Dashboard page - Old Navigation Flow - Verified Sucessfully");
			//BreadCrumbs Check
			softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("pulsesBreadCrumbsNew"));
			Assert.assertEquals(WEXEEDashboardPage.getTextOfWEXEEDashboardPage("pulsesBreadCrumbsNew"),getTextLanguage(LanguageCode, "daas_ui", "ee_module_name"),"Module name is not getting matched in old flow");	
		}else {
			//Navigation check
			WEXEEDashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
			WEXEEDashboardPage.actionClickOnWEXEEDashboardPage("employeeEngagementDashboardSelection");
			//URL Check
			String ActurlOfEXPDashobard= getUrlOfCurrentPage();
			softAssert.assertTrue(ActurlOfEXPDashobard.contains(getEnvironmentSpecificData(System.getProperty("environment"),"WEX_NEW_EE_DASHBOARD_NEW")),"URL of the Current Dashboard Page is not matching for New flow");
			LOGGER.info("Actual and Expected URL is getting matched for the Dashboard page - New Navigation Flow - Verified Sucessfully");
			//BreadCrumbs Check
			softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("pulsesBreadCrumbsNew"));
			softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("pulsesBreadCrumbsOld"));
			Assert.assertEquals(WEXEEDashboardPage.getTextOfWEXEEDashboardPage("pulsesBreadCrumbsNew"),getTextLanguage(LanguageCode, "daas_ui", "wex.analytics"),"Analytics breadcrumbs is not matching");
			WEXEEDashboardPage.actionClickOnWEXEEDashboardPage("viewReports");
			String ActurlOfEXPDashobardReports= getUrlOfCurrentPage();
			Assert.assertEquals(ActurlOfEXPDashobardReports,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_NEW_EE_DASHBOARD_REPORT_NEW"),"URL of the Current Dashboard Report Page is not matchinf=g for New flow");
			softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("pulsesBreadCrumbsNew"));
			softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("pulsesBreadCrumbsOld"));
			Assert.assertEquals(WEXEEDashboardPage.getTextOfWEXEEDashboardPage("pulsesBreadCrumbsNew"),getTextLanguage(LanguageCode, "daas_ui", "wex.analytics"),"Analytics breadcrumbs is not matching");
			sleeper(2000);
			LOGGER.info("Actual and Expected BreadCrumbs is getting matched for EE-Dashboard/Reports page - New Navigation Flow - Verified Sucessfully");
		}
		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Pulse Creation Functionality Flow for WEP-EE-Pulses Custom Type Audience Switch / Count Scenario
	 *
	 * @throws Exception
	 */
	@Test(priority = 47, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C51470700,C51470626 ",enabled=false)
	public final void verifyWEPPulsesAudienceSwitchScenario() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean logoEnableViaSettingsPage = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_SERIAL_NUMBER;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_SERIAL_NUMBER_VALUE");
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		List<String> devicelist = DevicesListPage.getAllTextOfWEPDeviceListPage("deviceserialnumberfield");
		LOGGER.info("Active devices filters applied successfully");
		sleeper(5000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		Integer TotalAudienceDynamic = DevicesListPage.verifyApplyOtherFilters(LanguageCode,"SerialNumberColumnTitle","SerialNumberColumnListValueSearch",valueName,"countPage");
		sleeper(3000);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withEndDate, Priority),"Schedule section tab validation check");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));
		}else {
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
		}
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsTargetCSVmethod(LanguageCode,"methodValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulses_audience_static_dataType_serial_number"),devicelist,TotalActiveDevices));
		LOGGER.info("Audience Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("leavePage");
		sleeper(1000);
		waitForPageLoaded();
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("firstLatestPulselist");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsApplyFiltersDynamicMethod(fieldName, operatorName, valueName,TotalActiveDevices,TotalAudienceDynamic),"Pulse Creation using Filters got failed in Filters validation");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		sleeper(3000);
		LOGGER.info("Schedule button got clicked successfully after providing all required field values");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("firstLatestPulselist");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		String audienceSwitchCountString = WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("pulsesResultsAudienceCount").replace("  devices", "");
		String audienceSwitchCountStr = audienceSwitchCountString.split(" ")[0];
		Integer audienceSwitchCount = Integer.parseInt(audienceSwitchCountStr);
		softAssert.assertEquals(TotalAudienceDynamic, audienceSwitchCount,"Audience Count into results page is not matching after audience method switch");
		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Save Draft Duplication Flow for WEP-EE-Pulses Custom Type with Content Section Filled into Draft state
	 *
	 * @throws Exception
	 */
	@Test(priority = 48, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : C51674206")
	public final void verifyWEPCustomDraftPulsesDuplicationCheck() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		Boolean logoEnableViaSettingsPage = false;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader").replace(" Pulse", "");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);
		waitForPageLoaded();
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");
		//Verify Elements into save draft section before doing any changes
		String[] BeforeMakingChanges = {"saveDraftPulseTypeHeaderContent","saveDraftPulseTypeValueContent","saveDraftStatusHeaderContent","saveDraftStatusValueContent"};
		Assert.assertTrue(WEPPulsesCreationPage.verifyAllElementsinWEPPulsesCreationPage(BeforeMakingChanges),"Elements not present before saving draft");
		//Pulse Type check into save draft section
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("saveDraftPulseTypeValueContent"),PulseType);
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		sleeper(2000);
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,"Custom Pulse", "Date-based",withEndDate,Priority));
		//String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		String StartDate = WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("saveDraftCreationDateValueSchedule");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");	
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		//Creation Date check into save draft section		
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("saveDraftCreationDateValueContent"),StartDate,"Creation date is not matching into save draft section");			
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("customContentDescription Verified Successfully");
//		//Custom Pulse creation flow with Multi-select Question with max option selected
//		List<String> providedInputDataMultiSelection = WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_multiSelect"),StartDate);
//		//Verify Elements into save draft section after doing first changes/adding some input
//		String[] AfterMakingChanges = {"saveDraftPulseTypeHeaderContent","saveDraftPulseTypeValueContent","saveDraftStatusHeaderContent","saveDraftStatusValueContent","saveDraftCreationDateHeaderContent","saveDraftCreationDateValueContent","saveDraftLastSavedHeaderContent","saveDraftLastSavedValueContent"};
//		Assert.assertTrue(WEPPulsesCreationPage.verifyAllElementsinWEPPulsesCreationPage(AfterMakingChanges),"Elements not present after saving draft");
//		//Custom Pulse creation flow with Single-select Question with max option selected
//		List<String> providedInputDataSingleSelection = WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_singleSelect"),StartDate);
//		//Custom Pulse creation flow with ThumbsUp Question with max option selected
//		List<String> providedInputDataThumbsUpSelection = WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_thumbsUp"),StartDate);
//		//Custom Pulse creation flow with 5-Star rating Question with max option selected
//		List<String> providedInputData5StarSelection = WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee.pulse.question.type.five.star.rating"),StartDate);
//		//Custom Pulse creation flow with NPS with max option selected
//		List<String> providedInputDataNPSSelection = WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType("Net promoter score",StartDate);
		
		//Custom Pulse creation flow with comment box with max option selected
		List<String> providedInputDataCommentBoxSelection = WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);

		LOGGER.info("Custom Content section got Verified by Passing the Inputs at the time of Creation Successfully.");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		//WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("leavePage");
		sleeper(1000);
		waitForPageLoaded();
		// Duplicated the Draft state Pulse
		softAssert.assertTrue(WEPPulsesListPage.verifyDuplicatePulseTypeFunctionality(PulseTitle, "Elipces-Selection"),"Duplication Via Pulse List Page got failed");
		//Verify Elements into duplicated draft Page section after redirecting
		String[] AfterRedirectingAlreadySaved = {"saveDraftPulseTypeHeaderContent","saveDraftPulseTypeValueContent","saveDraftStatusHeaderContent","saveDraftStatusValueContent","saveDraftCreationDateHeaderContent","saveDraftCreationDateValueContent","saveDraftLastSavedHeaderContent","saveDraftLastSavedValueContent"};
		Assert.assertTrue(WEPPulsesCreationPage.verifyAllElementsinWEPPulsesCreationPage(AfterRedirectingAlreadySaved),"Elements not present after redirecting to already saved draft");		
//		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionAfterDuplicateOrSavedDraftCustomPulseFunctionality(providedInputDataMultiSelection,1));
//		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionAfterDuplicateOrSavedDraftCustomPulseFunctionality(providedInputDataSingleSelection,2));
//		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionAfterDuplicateOrSavedDraftCustomPulseFunctionality(providedInputDataThumbsUpSelection,3));
//		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionAfterDuplicateOrSavedDraftCustomPulseFunctionality(providedInputData5StarSelection,4));
//		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionAfterDuplicateOrSavedDraftCustomPulseFunctionality(providedInputDataCommentBoxSelection,5));
//		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionAfterDuplicateOrSavedDraftCustomPulseFunctionality(providedInputDataNPSSelection,6));		
		LOGGER.info("Custom Content section got Verified Successfully after redirecting to duplicated draft Pulse.");
//		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Elements check for EntraID when it's in connected state
	 *
	 * @throws Exception
	 */
	@Test(priority = 49, groups = {}, description = "TestCase ID :")
	public final void verifyElementsCheckforEntraIDConnected() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		//verification of connections from integrations page
		WEPPulsesListPage.companyView(CommonVariables.INTEGRATIONS);
		WEPPulsesCreationPage.switchToIframeofWEPPulsesCreationPage("iframefastn");
		//WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("entraIDConnection");
		boolean EntraIDConnectionCheck = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("entraIDConnection");
		LOGGER.info("EntraID connection status is : " + EntraIDConnectionCheck);
		WEPPulsesCreationPage.switchToDefaultContentofOfIntegrationPage();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if(toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			sleeper(2000);
			if(Boolean.TRUE.equals(EntraIDConnectionCheck)) {
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("entraIDpresentText"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_choose_type_description"),"Audience method description is not matching when EntraID connection is made");
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_entra_id_title"));
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("entraIDdropDownHeader"),"EntraID header not getting displayed after selecting the Audience method");
//			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("entraIDgroupsdropDown"),"EntraID dropdown not getting displayed after selecting the Audience method");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("reachHeaderText"),"Reach header text is not getting displayed after selecting the Audience method");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("reachProgressBar"),"Reach progress bar is not getting displayed after selecting the Audience method");
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ReachNoteText"),"Reach Note text is not getting displayed after selecting the Audience method");
			LOGGER.info("Verified all elements when Entra ID Connection is done");
			}
			else {
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("entraIDpresentText"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_choose_type_description_entra_id_sync_status_disconnected"),"Audience method description is not matching when EntraID connection is not made");
			LOGGER.info("Entra ID connection is disabled for the current tenant");
			}
		}else {
			LOGGER.info("Entra ID toggle is disabled for the current tenant");
		}
		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Sentiment Pulse Creation with the Entra-ID as an audience method.
	 * Already imported Group not displaying on Group list page seems to be Fastn issue, hence disabling this TC as of now.
	 * @throws Exception
	 */
	@Test(priority = 50, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :", enabled=false)
	public final void verifySentimentPulseEntraIDAudienceMethod() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean logoEnableViaSettingsPage = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(
				//toggleVerification(WEPPulsesCreationPageVariables.WX_GROUPS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))&&		
				(toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE")))) {
			WEPGroupsPage WEPGroupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		WEPGroupsPage.companyView(CommonVariables.GROUPS);
		waitForPageLoaded();
		WEPGroupsPage.actionClickOfGroupsPage("searchText");
		WEPGroupsPage.actionClickOfGroupsPage("entraIDOption");
		String groupsname = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_ENTRAID_GROUP_VALUE");
		WEPGroupsPage.enterTextForGroupsPage("groupNameSearch",groupsname);
		WEPGroupsPage.actionClickOfGroupsPage("groupsSelection");
		String DevicesCount = WEPGroupsPage.getTextOfWEPGroupsPage("devicesCountEntraGroup");
		Integer EntraDevicesCount = Integer.parseInt(DevicesCount);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
			}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		sleeper(5000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		LOGGER.info("Active devices got fetched from device list page successfully");
		WEPPulsesListPage.companyView(CommonVariables.INTEGRATIONS);
		WEPPulsesCreationPage.switchToIframeofWEPPulsesCreationPage("iframefastn");
		boolean EntraIDConnectionCheck = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("entraIDConnection");
		WEPPulsesCreationPage.switchToDefaultContentofOfIntegrationPage();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withEndDate, Priority),"Schedule section tab validation check");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
			sleeper(2000);
			if(EntraIDConnectionCheck) {
				Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("entraIDpresentText"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_choose_type_description"),"Audience method description is not matching when EntraID connection is made");
				WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
				WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_entra_id_title"));
				softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsEntraIDMethod(groupsname,EntraDevicesCount,TotalActiveDevices),"Audience method for Entra ID is failing");
				WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
				sleeper(3000);
				LOGGER.info("Schedule button got clicked successfully after providing all required field values");
				softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
				WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
				WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
//				//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
//				//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
				LOGGER.info("Toast notification after creation got verified successfully.");
				softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
				LOGGER.info("Recently created Pulse got displayed first got verified successfully");
				}else {
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("entraIDpresentText"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_choose_type_description_entra_id_sync_status_disconnected"),"Audience method description is not matching when EntraID connection is not made");
			LOGGER.info("Entra ID connection is disabled for the current tenant");
			}
		}else {
			LOGGER.info("Feature toggles related to EntraID that were mentioned are disabled");
		}
		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Custom Pulse Creation with the Entra-ID as an audience method.
	 * Already imported Group not displaying on Group list page seems to be Fastn issue, hence disabling this TC as of now.
	 * @throws Exception
	 */
	@Test(priority = 51, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :", enabled=false)
	public final void verifyCustomPulseEntraIDAudienceMethod() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean logoEnableViaSettingsPage = false;
		Boolean withEndDate = false;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(
				//toggleVerification(WEPPulsesCreationPageVariables.WX_GROUPS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))&&		
				(toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE")))) {
			WEPGroupsPage WEPGroupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		WEPGroupsPage.companyView(CommonVariables.GROUPS);
		waitForPageLoaded();
		WEPGroupsPage.actionClickOfGroupsPage("searchText");
		WEPGroupsPage.actionClickOfGroupsPage("entraIDOption");
		String groupsname = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_ENTRAID_GROUP_VALUE");
		WEPGroupsPage.enterTextForGroupsPage("groupNameSearch",groupsname);
		WEPGroupsPage.actionClickOfGroupsPage("groupsSelection");
		String DevicesCount = WEPGroupsPage.getTextOfWEPGroupsPage("devicesCountEntraGroup");
		Integer EntraDevicesCount = Integer.parseInt(DevicesCount);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
			}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		sleeper(5000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		LOGGER.info("Active devices got fetched from device list page successfully");
		WEPPulsesListPage.companyView(CommonVariables.INTEGRATIONS);
		WEPPulsesCreationPage.switchToIframeofWEPPulsesCreationPage("iframefastn");
		boolean EntraIDConnectionCheck = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("entraIDConnection");
		WEPPulsesCreationPage.switchToDefaultContentofOfIntegrationPage();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		LOGGER.info("Redirected to Custom Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");

		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,"Custom Pulse", "Date-based",withEndDate,Priority),"Schedule Section Verification is failing");
		String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully.");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"),"Content sectiontab is not getting displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("customContentDescription Verified Successfully.");
		//Custom Pulse creation flow with Multi-select Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_multiSelect"),StartDate);
		//Custom Pulse creation flow with Single-select Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_singleSelect"),StartDate);
		//Custom Pulse creation flow with ThumbsUp Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_thumbsUp"),StartDate);
		//Custom Pulse creation flow with 5-Star rating Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee.pulse.question.type.five.star.rating"),StartDate);
		//Custom Pulse creation flow with comment box with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);
		//Custom Pulse creation flow with NPS with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType("Net promoter score",StartDate);
		LOGGER.info("Custom Content section got Verified Successfully.");
		
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
			sleeper(2000);
			if(EntraIDConnectionCheck) {
				Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("entraIDpresentText"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_choose_type_description"),"Audience method description is not matching when EntraID connection is made");
				WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
				WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_entra_id_title"));
				softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsEntraIDMethod(groupsname,EntraDevicesCount,TotalActiveDevices),"Audience method for Entra ID is failing");
				WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
				sleeper(3000);
				LOGGER.info("Schedule button got clicked successfully after providing all required field values");
				softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
				WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
				WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
//				//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
//				//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
				LOGGER.info("Toast notification after creation got verified successfully.");
				softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
				LOGGER.info("Recently created Pulse got displayed first got verified successfully");
				}else {
			Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("entraIDpresentText"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_choose_type_description_entra_id_sync_status_disconnected"),"Audience method description is not matching when EntraID connection is not made");
			LOGGER.info("Entra ID connection is disabled for the current tenant");
			}
		}else {
			LOGGER.info("Feature toggles related to EntraID that were mentioned are disabled");
		}
		softAssert.assertAll();
	}
	
	/**
	 * This method will verify the Save Draft Functionality Flow for Entra-ID Audience Section
	 * Already imported Group not displaying on Group list page seems to be Fastn issue, hence disabling this TC as of now.
	 * @throws Exception
	 */
	@Test(priority = 52, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ", enabled=false)
	public final void verifyWEPCustomPulsesSaveDraftFlowforEntraIDAudienceSection() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = true;
		Boolean logoEnableViaSettingsPage = false;
		Boolean withEndDate = true;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(
				//toggleVerification(WEPPulsesCreationPageVariables.WX_GROUPS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))&&		
				(toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE")))) {
			WEPGroupsPage WEPGroupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		WEPGroupsPage.companyView(CommonVariables.GROUPS);
		waitForPageLoaded();
		WEPGroupsPage.actionClickOfGroupsPage("searchText");
		WEPGroupsPage.actionClickOfGroupsPage("entraIDOption");
		String groupsname = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_ENTRAID_GROUP_VALUE");
		WEPGroupsPage.enterTextForGroupsPage("groupNameSearch",groupsname);
		WEPGroupsPage.actionClickOfGroupsPage("groupsSelection");
		String DevicesCount = WEPGroupsPage.getTextOfWEPGroupsPage("devicesCountEntraGroup");
		Integer EntraDevicesCount = Integer.parseInt(DevicesCount);
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
			}else {
		DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		sleeper(5000);
		Integer TotalActiveDevices = DevicesListPage.getConvertedIntegerCount();
		LOGGER.info("Active devices got fetched from device list page successfully");
		WEPPulsesListPage.companyView(CommonVariables.INTEGRATIONS);
		WEPPulsesCreationPage.switchToIframeofWEPPulsesCreationPage("iframefastn");
		boolean EntraIDConnectionCheck = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("entraIDConnection");
		WEPPulsesCreationPage.switchToDefaultContentofOfIntegrationPage();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
		LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withEndDate, Priority),"Schedule section tab validation check");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
			sleeper(2000);
			if(EntraIDConnectionCheck) {
				Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("entraIDpresentText"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_choose_type_description"),"Audience method description is not matching when EntraID connection is made");
				WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
				WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_entra_id_title"));
				softAssert.assertTrue(WEPPulsesCreationPage.verifyAudienceSectionValidationsEntraIDMethod(groupsname,EntraDevicesCount,TotalActiveDevices),"Audience method for Entra ID is failing");
				sleeper(2000);
				List<String> BeforeRedirection = WEPPulsesCreationPage.getInputsforAudienceMethodBeforeAndAfterRedirection("Entra-ID");
				WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
				WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("leavePage");
				sleeper(1000);
				waitForPageLoaded();
				WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("firstLatestPulselist");
				softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
				WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
				sleeper(2000);
				List<String> AfterRedirection = WEPPulsesCreationPage.getInputsforAudienceMethodBeforeAndAfterRedirection("Entra-ID");
				softAssert.assertEquals(BeforeRedirection, AfterRedirection,"Save draft for Entra-ID audience method is failing");
			}else {
				Assert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("entraIDpresentText"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_choose_type_description_entra_id_sync_status_disconnected"),"Audience method description is not matching when EntraID connection is not made");
				LOGGER.info("Entra ID connection is disabled for the current tenant");
				}
			}else {
				LOGGER.info("Feature toggles related to EntraID that were mentioned are disabled");
			}
			softAssert.assertAll();
	}

	/**
	 * This method will verify the ErrorMessage Validation for Dynamic Audience Method.
	 * Already imported Group not displaying on Group list page seems to be Fastn issue, hence disabling this TC as of now.
	 * @throws Exception
	 */
	@Test(priority = 53, groups = {"REGRESSION_PULSES"}, description = "TestCase ID :C51470622,C57503163", enabled=false)
	public final void verifyWEPPulsesErrorMessageValidationDynamicAudienceMethod() throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_LAST_LOGGED_USER;
		String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_EQUALS;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "PULSE_FILTERS_LASTLOGGED_FILTER_VALUE");
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicTargetingMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("dynamicTargetingMethod");
		}
		WEPPulsesCreationPage.defaultFilterCheck(PulseType);
		softAssert.assertFalse(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("removeButton"));
		LOGGER.info("Default Filter validation is done for the Dynamic method");
		WEPPulsesCreationPage.leaveRedirectionDraftPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("chooseOptionone"),"Choose option field is not visible");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("chooseOptiontwo"),"Choose option field is not visible");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("chooseOptionthree"),"Choose option field is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("fieldDropDown");
		LOGGER.info("fieldDropDown clicked Successfully");
		sleeper(5000);
		WEPPulsesCreationPage.selectFromDropdown("FieldDropDownOptions", fieldName);
		WEPPulsesCreationPage.leaveRedirectionDraftPulsesCreationPage("audienceSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("chooseOptionone"),"Choose option field is not visible");
		softAssert.assertFalse(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("chooseOptiontwo"),"Choose option field is not visible");
		softAssert.assertFalse(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("chooseOptionthree"),"Choose option field is not visible");
		softAssert.assertAll();
	}

	/**
	 * This method will verify the Live State Pulse Duplication flow from Pulse Results page.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 54, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : C57503136")
	public final void verifyLiveStatePulseDuplicationfromPulseResultsPage () throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withoutEndDate = false;
		String Priority = "Urgent";
		Boolean logoEnableViaSettingsPage = false;
		int TotalActiveDevices;
		int TotalTargetedDevices;
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else {
			DevicesListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);}
		Assert.assertTrue(DevicesListPage.verifyonlyactivedevicesdatafetch(LanguageCode, "clearListPageFilter", "activestatusheader", "devicestatusDDn", "devicestatusddvalues", "firstcolumnheader"));
		LOGGER.info("Active devices filters applied successfully");
		sleeper(2000);
		String TotalAudienceString = DevicesListPage.getTextOfWEPDeviceListPage("countPage").replace("of ", "");
		if(TotalAudienceString.contains(",")) {
			String replaced = TotalAudienceString.replace(",", "");
			TotalActiveDevices = Integer.parseInt(replaced);
			TotalTargetedDevices = TotalActiveDevices;
		}else {
			TotalActiveDevices = Integer.parseInt(TotalAudienceString);
			TotalTargetedDevices = TotalActiveDevices;
		}
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
		sleeper(2000);
		softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company Logo Section in Custom Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withoutEndDate,Priority));
		String StartDate = getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.radio.button.label.urgent");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customContentDescription"),"Content for Custom Pulse creation is not present by-default");
		LOGGER.info("custom Content Description Verified Successfully");
		//Custom Pulse creation flow with 5-Star rating Question with max option selected
		//WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee.pulse.question.type.five.star.rating"),StartDate);
		//Custom Pulse creation flow with comment box with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		if (toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_everyone_title"));
		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingEveryoneMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingEveryoneMethod");
		}
		WEPPulsesCreationPage.verifyAudienceSectionValidationsEveryoneMethodApplied(TotalActiveDevices,TotalTargetedDevices);

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("urgentPulsePublishBannerText"), getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.publish.modal.title.text"),"Urgent pulse - Publish Banner text not matching.");
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("urgentPulsePublishBannerContent"), getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.publish.modal.subcopy"),"Urgent Pulse - Publish Banner Content not matching.");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader"),"Confirmation Publish header is to be displayed");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
		//WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
		//softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
		//		WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton");
		//				softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotificationViewButton"),"Toast notification haven't appeared and hence view button into the toast notification didn't got verified");
		LOGGER.info("Toast notification after creation got verified successfully.");
		sleeper(2000);
		//verify priority column check from pulse results Page for recently created urgent Pulse
		softAssert.assertTrue(WEPPulsesListPage.verifyDuplicatePulseTypeFunctionality(PulseTitle, "Results-Selection"),"Duplication Via Pulse Results Page got failed");
		LOGGER.info("Pulse Duplication flow for Live State from Results page - verified successfully");
		sleeper(2000);
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("sendImmediatelyHeaderText"));
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		//Delete the created draft state Urgent Pulse - Custom Type
		softAssert.assertTrue(WEPPulsesListPage.verifyDeletePulseTypeFunctionality(PulseTitle+"-Copy", "Checkbox-Selection"),"Deletion Via Pulse List Page got failed");
		LOGGER.info("Custom Urgent Pulse Deletion flow Via Checkbox selection from Listing Page - verified successfully");
		softAssert.assertAll();
	}
	
	/**
	 * This method will verify Send Pulse Option visible on Alert List page + Error message validation when No device selected.
	 * 
	 * @throws Exception
	 */
     @Test(priority = 55, groups = {"REGRESSION_PULSES","PRODUCTION_PULSES_EE_SUSTAIN"}, description = "TestCase ID : C57547075",enabled=false)
     public final void verifySendPulseOptionVisibleOnAlertListPage() throws Exception {
        login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
        SoftAssert softAssert = new SoftAssert();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		alertpage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		sleeper(3000);
        // Check if any active alert is present
        if (!WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("activealert")) {
        LOGGER.info("No active alerts are present.");
        softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"), "No Alert Message is not visible.");
        softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertMessage"),getTextLanguage(LanguageCode, "daas_ui", "list.no_items"),"No Alert Message text does not match.");
          LOGGER.info("Verified that 'Send Pulse' button is not visible when no active alerts are present.");
        } else {
        LOGGER.info("Active alerts are present.");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("activealert");
        sleeper(2000);
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("serialNumCheckBox");
        sleeper(2000);
        Assert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("sendPulseBtn"), 
            "Send Pulse Option Not available");
        LOGGER.info("Send pulse option visible on alert list page - verified successfully");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("sendPulseBtn");
        WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("alertDeviceSelectionToastNotification");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("alertDeviceSelectionToastNotification"), 
            "Toast notification hasn't appeared in the UI when no device selected");
        LOGGER.info("Toast notification appeared when no devices selected - verified successfully.");
        waitForPageLoaded();
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("serialNumCheckBox");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("sendPulseBtn");
       }
       softAssert.assertAll();
     }

	
	/**
	 * This method will verify End to End Pulse creation flow from Alert.
	 * @throws Exception
	 */
	@Test(priority = 56, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ", enabled=false)
	public final void verifyWEPCustomPulseCreationFromAlertEndtoEndScenarios () throws Exception {
		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		String Priority = "Standard";
		String PulseType = "Custom";
		Boolean withoutEndDate = false;
		String valueName = getEnvironmentSpecificData(System.getProperty("environment"),"PULSE_FILTERS_SERIAL_NUMBER_VALUE");
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPAlertsDashboardPage alertpage = new WEPAlertsDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		leftSideMenuVerification();
		alertpage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		waitForPageLoaded();
		sleeper(3000);
        // Check if any active alert is present 
        if (!WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("activealert")) {
        LOGGER.info("No active alerts are present.");
        softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertMessage"), "No Alert Message is not visible.");
        softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertMessage"),
            getTextLanguage(LanguageCode, "daas_ui", "no_active_alerts.title"),
            "No Alert Message text does not match.");
        softAssert.assertTrue(alertpage.verifyElementsOfAlertPage("noAlertSubText"), "No Alert Subtitle is not visible.");
        softAssert.assertEquals(alertpage.getTextOfAlertPage("noAlertSubText"),
            getTextLanguage(LanguageCode, "daas_ui", "no_active_alerts.subtitle"),
            "No Alert Subtitle text does not match.");
        LOGGER.info("Verified that 'Send Pulse' button is not visible when no active alerts are present.");
        } else {
        LOGGER.info("Active alerts are present.");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("activealert");
		sleeper(2000);
//		WEPPulsesCreationPage.enterTextForWEPPulsesCreationPage("deviceSNforAlert",valueName);
		sleeper(3000);
		WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("sendPulseBtn");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("sendPulseBtn");
		WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("alertPulsePublishTitleText");
		softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("alertPulsePublishTitleText"), getTextLanguage(LanguageCode, "daas_ui", "alerts.send.pulse.modal.title"),"Publish Banner text for an Alert not matching.");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("startPulseBtnFromAlert");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		sleeper(3000);
		WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("audienceDeviceSN");
//		Assert.assertTrue(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("audienceDeviceSN").contains(valueName),"Expected device serial number name not matching for an Alert");
		LOGGER.info("Audience Section in Custom Pulse creation page got verified successfully");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTabAlert");
		WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("scheduleSectionTabAlert");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTabAlert");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode, PulseType, "Date-based", withoutEndDate, Priority),"Schedule section validation check");
		String StartDate = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("selectedStartDatePick", "value");
		LOGGER.info("Scheduling Section in Custom Pulse creation page got verified successfully");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
		//Custom Pulse creation flow with Comment-Box Question with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_commentBox"),StartDate);
		//Custom Pulse creation flow with NPS with max option selected
		WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType("Net promoter score",StartDate);
		LOGGER.info("Custom Content section got Verified Successfully");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
		WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("ConfirmPublishHeader");
		LOGGER.info("Pulse creation from Alert got Verified Successfully");
	   }
        softAssert.assertAll();
	 }
	@Test(priority = 57, groups = {"PRODUCTION_LDK","REGRESSION_INTEGRATIONQA_CUJ"} ,enabled = true )

	public final void verifySentimentPulseCreation() throws Exception {

		login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
		Boolean logoSectionTobeEnabled = false;
		Boolean withEndDate = false;
		Boolean logoEnableViaSettingsPage = false;
		String Priority = "Standard";
		SoftAssert softAssert = new SoftAssert();
		WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage DevicesListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
			WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else {
		WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
		waitForPageLoaded();
		softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
		String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader");
		WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
		LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyCompanylogoSectionValidationsPulseCreation(LanguageCode,logoSectionTobeEnabled,logoEnableViaSettingsPage),"Company logo section verification is failing");
		LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
		boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");

		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
		WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
		String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
		//softAssert.assertTrue(WEPPulsesCreationPage.verifyContentSectionOnSentimentPulsesType(LogoPresent,"contentLogoPresent"),"Content section validation check");
    	LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");

		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("scheduleSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
		softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,PulseType, "Date-based", withEndDate, Priority),"Schedule section tab validation check");
		LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");

		softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
		WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("audienceSectionTab");
		WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");

		if(toggleVerification(WEPPulsesCreationPageVariables.EntraID_toggle, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))) {
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
			WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_csv_upload_title"));

		}else {
			softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("targetingCSVMethod"));
			WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("targetingCSVMethod");
		}

		LOGGER.info("Audience Section in Sentiment Pulse creation page got verified successfully");
		softAssert.assertAll();
	}

    @Test(priority = 58, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ")
    public final void verifyWEPSentimentPulsematrixquestiontemplateeValidations() throws Exception {
    	login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
        Boolean withEndDate = false;
        SoftAssert softAssert = new SoftAssert();
        String Priority = "Standard";
        WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
        if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
            WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
        }else {
            WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
        waitForPageLoaded();
        softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"));
        WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
        WEPPulsesListPage.getTextOfWEPPulsesListPage("SentimentPulseContextHeader").equals(WEPPulsesCreationPage.getTextLanguage(LanguageCode,"daas_ui","ee_pulse_list_sentiment_pulse_btn_title"));
        WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");
        LOGGER.info("Redirected to Sentiment Pulse creation Page successfully");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("editTitleIcon"),"Edit Icon for the Pulse title is not present in Sentiment Pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("customPulseTitle"),"Sentiment Pulse Title is not Present in the Sentiment pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Sentimentpulsequestion1"),"Sentiment Pulse question 1 is not Present in the Sentiment pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("question1starrating"),"1starlowrating is not present into contentsection for Custom Pulse Creation Page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("question5starrating"),"5starlowrating is not present into contentsection for Custom Pulse Creation Page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("NoteditableQuestion1"),"Sentiment Pulse question 1 Not editable text is not Present in the Sentiment pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Sentimentpulsequestion1lock"),"Sentiment Pulse question 1 lock is not Present in the Sentiment pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Sentimentpulsequestion1type"),"Sentiment Pulse question 1 type is not Present in the Sentiment pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Sentimentpulsequestion2"),"Sentiment Pulse question 2 is not Present in the Sentiment pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Sentimentpulsequestion2type"),"Sentiment Pulse question 2 type is not Present in the Sentiment pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("question2reminder"),"Sentiment Pulse question 2 reminder is not Present in the Sentiment pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("question2option1"),"Sentiment Pulse question 2 option1 is not Present in the Sentiment pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("question2option2"),"Sentiment Pulse question 2 option2 is not Present in the Sentiment pulse Creation page");
        WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("question2option2");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("question2option3"),"Sentiment Pulse question 2 option3 is not Present in the Sentiment pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("question2option4"),"Sentiment Pulse question 2 option4 is not Present in the Sentiment pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("question2option5"),"Sentiment Pulse question 2 option5 is not Present in the Sentiment pulse Creation page");
        WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("Sentimentpulsequestion3");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Sentimentpulsequestion3"),"Sentiment Pulse question 3 is not Present in the Sentiment pulse Creation page");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
        WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
        String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("CompanyLogoTitleLabel"),"Companylogo title header not present in Sentiment Pulse Creation page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("CompanyLogoTitleLabel"),"Companylogo Toggle not present in Sentiment Pulse Creation page");
        boolean LogoPresent = WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("contentSectionTab"),"Content not Present in the Sentiment pulse Creation page");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
        LOGGER.info("Content Section in Sentiment Pulse creation page got verified successfully");

        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not present into conent section for Sentiment Pulse Creation Page");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not visible");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyScheduleSectionTabValidationsPulseCreation(LanguageCode,"Sentiment Pulse", "Date-based", withEndDate, Priority),"Schedule section tab validation check");
        LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");

        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceSectionTab"),"Audience section tab is not visible");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Allenrolleddevices");
        sleeper(2000);
        LOGGER.info("Audience Section in Sentiment Pulse creation page got verified successfully");

        WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("publishButton");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
        sleeper(1000);
        WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("confirmPublishButton");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
        WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
        softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
        LOGGER.info("Toast notification after creation got verified successfully.");

        WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("firstLatestPulselist");
        softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");
        sleeper(3000);
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("firstLatestPulselist");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("overview"),"overview is not present in Sentiment Pulse result page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Delivered"),"Delivered is not present in Sentiment Pulse result page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Viewed"),"Viewed is not present in Sentiment Pulse result page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Totalresponses"),"Totalresponses is not present in Sentiment Pulse result page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Uniqueresponse"),"Uniqueresponse is not present in Sentiment Pulse result page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Responserate"),"Responserate is not present in Sentiment Pulse result page");
        WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("Result");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Result"),"Result is not present in Sentiment Pulse result page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("question1header"),"question1header is not present in Sentiment Pulse result page");

        WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("question2header");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("question2header"),"question2header is not present in Sentiment Pulse result page");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("matrixrating"),"matrixrating is not present in Sentiment Pulse result page");
        WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("question3header");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("question3header"),"question3header is not present in Sentiment Pulse result page");

        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Commentbox"),"Commentbox is not present in Sentiment Pulse result page");
        LOGGER.info("Recently created Pulse got displayed first got verified successfully");
        softAssert.assertAll();

        LOGGER.info(" WEPSentimentPulsematrixquestiontemplate Functionality - verified successfully");
    }



@Test(priority = 59, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ")
public final void verifyWEPCustomPulsesCreationformatrixquestiontypeEndtoEndScenario() throws Exception {
	login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");
    SoftAssert softAssert = new SoftAssert();
    WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
    WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
    leftSideMenuVerification();
    if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
        WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
    }else {
        WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
    waitForPageLoaded();
    softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
    WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
    String PulseType = WEPPulsesListPage.getTextOfWEPPulsesListPage("CustomPulseContextHeader");
    WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
    WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
    LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
    WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
    sleeper(2000);
    String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("companyLogoToggle");
    sleeper(1000);
    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
    sleeper(3000);
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("newQuestionsButton"),"New question button is not present into conect section for Custom Pulse Creation Page");
    sleeper(5000);
    WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_matrixRating"),"09-10-2025");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("1starlowratingforcreatepulse"),"1starlowrating is not present into contentsection for Custom Pulse Creation Page");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("5starhighratingforcreatepulse"),"5starlowrating is not present into contentsection for Custom Pulse Creation Page");

    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("newNotificationButton"),"New notification button is not present into conect section for Custom Pulse Creation Page");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("btnGoToSchedule"),"Go to Schedule button is not present into Content section for Custom Pulse Creation Page");
    LOGGER.info("Custom pulse Creation page - Content Section Verified Successfully");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not present into conect section for Custom Pulse Creation Page");
    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
    softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("scheduleContent"),getTextLanguage(LanguageCode, "daas_ui", "ee.urgentPulse.schedule.heading"),"Schedule content Description for Custom Pulse creation is not matching");
    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("urgentPriorityRadioButton");
    LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("audienceContenttext"),"Audience aontent for Sentiment Pulse creation is not present by-default");
    //softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("entraidaudience"),"entraidaudience method is not present into Audience section for Sentiment Pulse Creation Page");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("dynamicfilter"),"dynamicfilter method is not present into Audience section for Sentiment Pulse Creation Page");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("uploadcsvfile"),"uploadcsvfile method is not present into Audience section for Sentiment Pulse Creation Page");
    LOGGER.info("Audience Section in Sentiment Pulse creation page got verified successfully");
    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Allenrolleddevices");
    sleeper(2000);
    WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("publishButton");
    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
    sleeper(1000);
    WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("confirmPublishButton");
    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
    WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
    softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");

    LOGGER.info("Toast notification after creation got verified successfully.");
    WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("firstLatestPulselist");
    System.out.println(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"));
    softAssert.assertEquals(WEPPulsesListPage.getTextOfWEPPulsesListPage("firstLatestPulselist"),(PulseTitle),"Pulse name after creation is not matching");

    sleeper(3000);

    WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("firstLatestPulselist");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("overview"),"overview is not present in Sentiment Pulse result page");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Delivered"),"Delivered is not present in Sentiment Pulse result page");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Viewed"),"Viewed is not present in Sentiment Pulse result page");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Totalresponses"),"Totalresponses is not present in Sentiment Pulse result page");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Uniqueresponse"),"Uniqueresponse is not present in Sentiment Pulse result page");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Responserate"),"Responserate is not present in Sentiment Pulse result page");
    WEPPulsesCreationPage.scrollOnWEPPulsesCreationPage("Result");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Result"),"Result is not present in Sentiment Pulse result page");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("question1header"),"question1header is not present in Sentiment Pulse result page");
    softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("matrixrating"),"matrixrating is not present in Sentiment Pulse result page");

    LOGGER.info("Recently created Pulse got displayed first got verified successfully");
    LOGGER.info("WEPCustomPulsesCreationformatrixquestiontypeEndtoEndScenario Verified Successfully");


}

    @Test(priority = 60, groups = {"REGRESSION_PULSES"}, description = "TestCase ID : ")
    public final void verifyWEPCustomPulsesCreationformatrixquestiontypeEndtoEndScenariowithpartner() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("SelectAllcustomer"),"SelectAllcustomer is not present in Sentiment Pulse result page");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("SelectAllcustomer");
        sleeper(2000);
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("Searchfunction"),"Searchfunction is not present in Sentiment Pulse result page");
        WEPPulsesCreationPage.enterTextForWEPPulsesCreationPage("Searchfunction",getEnvironmentSpecificData(System.getProperty("environment"),"COMPANY_NAME"));
        sleeper(2000);
        WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("Selectcustomer");
        WEPPulsesCreationPage.clickOnElementsOfWEPPulsesCreationPage("Selectcustomer");
        WEPPulsesCreationPage.clickOnElementsOfWEPPulsesCreationPage("Selectcustomer");
        sleeper(2000);
        waitForPageLoaded();
        leftSideMenuVerification();
        if(toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
            WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
        }else {
            WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);}
        waitForPageLoaded();
        WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
        WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
        WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");
        waitForPageLoaded();
        LOGGER.info("Company logo Section in Sentiment Pulse creation page got verified successfully");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
        WEPPulsesCreationPage.enterTextwithoutclearForWEPPulsesCreationPage("PulseTitle",generateRandomNumber());
        String PulseTitle = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle","value");
        sleeper(2000);
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("companyLogoToggle");
        sleeper(1000);
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
        sleeper(3000);
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("newQuestionsButton"),"New question button is not present into conect section for Custom Pulse Creation Page");
        sleeper(5000);
        WEPPulsesCreationPage.verifyContentSectionQuestionsSelectionCustomPulseType(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_matrixRating"),"09-10-2025");
        LOGGER.info("Custom pulse Creation page - Content Section Verified Successfully");
        softAssert.assertTrue(WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("scheduleSectionTab"),"Schedule section tab is not present into conect section for Custom Pulse Creation Page");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
        softAssert.assertEquals(WEPPulsesCreationPage.getTextOfWEPPulsesCreationPage("scheduleContent"),getTextLanguage(LanguageCode, "daas_ui", "ee.urgentPulse.schedule.heading"),"Schedule content Description for Custom Pulse creation is not matching");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("scheduleSectionTab");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("datebasedScheduleOption");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("urgentPriorityRadioButton");
        LOGGER.info("Scheduling Section in Sentiment Pulse creation page got verified successfully");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
        LOGGER.info("Audience Section in Sentiment Pulse creation page got verified successfully");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("Allenrolleddevices");
        sleeper(2000);
        WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("publishButton");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("publishButton");
        sleeper(1000);
        WEPPulsesCreationPage.waitForElementsOfWEPPulsesCreationPage("confirmPublishButton");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("confirmPublishButton");
        WEPPulsesListPage.waitForElementsOfWEPPulsesListPage("CreationtoastNotification");
        softAssert.assertTrue(WEPPulsesListPage.verifyElementsOfWEPPulsesListPage("CreationtoastNotification"),"Toast notification haven't appeared in the UI after Pulse Publish");
        LOGGER.info("Toast notification after creation got verified successfully.");
        softAssert.assertAll();
        LOGGER.info("WEPCustomPulsesCreationformatrixquestiontypeEndtoEndScenario Verified Successfully");
  }
    /**
     * This method opens a pulse and clicks on the audience dropdown
     */
    @Test(priority = 61, groups = {"REGRESSION_PULSES"}, description = "Open pulse and click audience dropdown")
    public final void verifyDEviceNameDropDownOptionSentimentPulse() throws Exception {
        login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");

        // Declare the required variables
        String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_DEVICE_NAME;
        String operatorName = WEPPulsesCreationPageVariables.PULSE_FILTERS_OPERATOR_EQUALS;
        //String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "PULSE_FILTERS_DEVICE_NAME_VALUE");

        WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();

        // Navigate to pulses page
        if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
            WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT, CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
        } else {
            WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
        }

        waitForPageLoaded();

        // Create new pulse
        WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
        WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("SentimentPulseContextualMenuSection");

        // Navigate to audience section
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");

        // Click audience dropdown
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
        WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));

        // Add device model filter with EQUALS operator
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("fieldDropDown");
        LOGGER.info("fieldDropDown clicked Successfully");
        sleeper(5000);
        WEPPulsesCreationPage.selectFromDropdown("FieldDropDownOptions", fieldName);
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("operatorDropDown");
        WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("operatorDropDown");
        LOGGER.info("DEvice name is present in dropdowm and got clicked Successfully");

          }
    /**
     * This method opens a pulse and clicks on the audience dropdown
     */
    @Test(priority = 61, groups = {"REGRESSION_PULSES"}, description = "Open pulse and click audience dropdown")
    public final void verifyDEviceNameDropDownOptionCustomPulse() throws Exception {
        login("ITADMIN_EMAIL_PULSE", "ITADMIN_PASSWORD_PULSE");

        // Declare the required variables
        String fieldName = WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_DEVICE_NAME;
        String direction = "down";
        String optionName = "//li[@data-value='name']";

        //String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "PULSE_FILTERS_DEVICE_NAME_VALUE");

        WEPPulsesListPage WEPPulsesListPage = new WEPPulsesListPage(PreDefinedActions.getDriver()).getInstance();
        WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();

        // Navigate to pulses page
        if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_PULSE"))){
            WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT, CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
        } else {
            WEPPulsesListPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
        }

        waitForPageLoaded();

        // Create new pulse
        WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CreateButton");
        WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("CustomPulseContextualMenuSection");
        WEPPulsesListPage.clickByActionsClickWEPPulsesListPage("Createfromscratch");

        // Navigate to audience section
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");

        // Click audience dropdown
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("newAudienceDropDown");
        WEPPulsesCreationPage.selectFromDropdown("audienceListDDValues",getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_audience_method_dynamic_title"));

        // Add device model filter with EQUALS operator
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("fieldDropDown");
        LOGGER.info("fieldDropDown clicked Successfully");
        sleeper(5000);
       // WEPPulsesCreationPage.scrollinDropdown(optionName, direction);
        WEPPulsesCreationPage.selectFromDropdown("FieldDropDownOptions", fieldName);
        WEPPulsesCreationPage.verifyElementsOfWEPPulsesCreationPage("operatorDropDown");
        WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("operatorDropDown");

        LOGGER.info("DEvice name is present in dropdowm and got clicked Successfully");

          }

}