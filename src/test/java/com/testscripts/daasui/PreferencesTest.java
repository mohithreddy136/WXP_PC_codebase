package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CompanyPin;
import com.basesource.utils.EnrollFakeDevice;
import com.basesource.utils.SNOWInstanceWakeUP;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DeviceVariables;
import com.daasui.constants.PreferenceVariables;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DeviceDetailsPage;
import com.daasui.pages.DeviceListPage;
import com.daasui.pages.EMMToolPage;
import com.daasui.pages.PreferencesPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.UserPage;
import com.google.common.base.Strings;

public class PreferencesTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(PreferencesTest.class);
	public String firstCompany, secondCompany;
	public static String importCompany = getEnvironmentSpecificData(System.getProperty("environment"),
			"DEVICE_IMPORT_COMPANY_SNOW");
	public static String importCompany1 = getEnvironmentSpecificData(System.getProperty("environment"),
			"INCIDENT_COMPANY1");
	public static String deviceName = getEnvironmentSpecificData(System.getProperty("environment"),
			"CHROME_DEVICE_NAME");
	public static String deviceID = getEnvironmentSpecificData(System.getProperty("environment"), "CHROME_DEVICE_ID");
	public static String emmCompanyId = getEnvironmentSpecificData(System.getProperty("environment"),
			"EMM_COMPANY_TENANT_ID");
	public static String airwatchUserName = getEnvironmentSpecificData(System.getProperty("environment"),
			"AIRWATCH_USER_NAME");
	public static String intunehUserName = getEnvironmentSpecificData(System.getProperty("environment"),
			"INTUNE_USER_NAME");
	public static String chromehUserName = getEnvironmentSpecificData(System.getProperty("environment"),
			"CHROME_USER_NAME");
	public static String emmCompanyName = getEnvironmentSpecificData(System.getProperty("environment"),
			"EMM_COMPANY_NAME");
	

	/**
	 * This test verifies Intune EMM configuration as follows : 1. To create a
	 * company 2. To validate EMM string on preferences tab 3. To validate Intune
	 * related strings for Intune toggle 4. To configure Intune EMM 5. To verify
	 * pre-existing intune devices are enrolled on DaaS portal 6. To delete a
	 * company
	 * 
	 * @throws Exception
	 */
	@Test(priority = 0, groups = { "REGRESSION" }, description = "208846, 209093, 209066, 209063, 209067, 208552")
	public final void verifyIntuneConfigurationTest() throws Exception {
		
		try {
			// Login to MSP account
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			SoftAssert softAssert = new SoftAssert();
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			// Remove Intune Configuration
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_INTUNE,
							"intuneIntegrationDeleteButton", emmCompanyId),
					"Error occured while removing microsoft intune on Prefernce Page");
			Assert.assertTrue(preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, intunehUserName,
					getEnvironment(environment)), "Error occured while removing intune user on User List Page");
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(emmCompanyName);
			preferencesPage.goToPreferencesTab();
//			// Test case : 1
			softAssert.assertTrue(preferencesPage.verifyEmmStringOnPreferencesPage(LanguageCode),
					"All Strings are not present for EMM on preferences page");
			LOGGER.info("All Strings are validated for EMM on preferences page");
			// Test Case : 2
			sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
//							 this is the final solution which works as expected.)
			preferencesPage.verifyElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.waitForElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.waitForElementsOfPreferencesPage("intuneRadioButton");	
			softAssert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("intuneRadioButton"),
					"Intune Toggle is not present");
			LOGGER.info("Intune Toggle is present");
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			preferencesPage.clickOnElementsOfPreferencesPage("intuneRadioButton");
			softAssert.assertTrue(preferencesPage.verifyEmmStringOnIntunePage(LanguageCode),
					"All Strings are not present for Intune");
			LOGGER.info("All Strings are validated for Intune");
			// Test case : 3
			Assert.assertTrue(
					preferencesPage.verifyIntuneConfiguration(PreferencesPage.intuneCredentials.get("DOMAIN_NAME"),
							PreferencesPage.intuneCredentials.get("INTUNE_ID"),
							PreferencesPage.intuneCredentials.get("INTUNE_PASSWORD")),
					"Intune Configuration is failed");
			LOGGER.info("Intune is configured successfully");
			// Test case : 4 Verify Linking from EMM tool button
			softAssert.assertTrue(preferencesPage.verifyExistingIntuneIosDeviceEnrollemnt(emmCompanyName),
					"Pre-existing devices enrollemnt failed");
			LOGGER.info(
					"After configuring an Intune on DaaS portal, existing Intune enrolled iOS devices are enrolled on DaaS portal");

			softAssert.assertAll();
			LOGGER.info("All test cases of Intune configuarion passed");
		} finally {
			// Remove Intune Configuration
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_INTUNE,
							"intuneIntegrationDeleteButton", emmCompanyId),
					"Error occured while removing microsoft intune on Prefernce Page");
			gotoUserTab();
			Assert.assertTrue(preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, intunehUserName,
					getEnvironment(environment)), "Error occured while removing intune user on User List Page");
		}
	}

	/**
	 * This Test verify Airwatch existing linking with creating two companies
	 * configuration in one tenant will display success message and same configuring
	 * in other tenant will display existing linking error message & deletes both
	 * the company
	 */
	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI" }, description = "218199 , 218189")
	public final void verifyPreventExistingAirwatchLinkingConfiguration() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		try {
			// Login in to the MSP in which both above companies are present
			
			// Remove Airwatch Configuration with checkobox
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_AIRWATCH,
							"airwatchIntegrationDeleteButton", emmCompanyId),
					"Error occured while removing airwatch on Prefernce Page");
			Assert.assertTrue(
					preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, airwatchUserName,
							getEnvironment(environment)),
					"Error occured while removing airwatch user on User List Page");

			// Impersonating into a first company
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);

			// Configuring the Airwatch Integration on Preferences tab for first company
			preferencesPage.goToPreferencesTab();
			preferencesPage.scrollOnPreferencesPage("airwatchEditbutton");
			preferencesPage.waitForElementsOfPreferencesPage("airwatchEditbutton");
			preferencesPage.clickOnElementsOfPreferencesPage("airwatchEditbutton");
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			Assert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("aiwtachRadioButton"),
					"Airwatch Toggle is not present");
			LOGGER.info("Airwatch Toggle is present");
			
			preferencesPage.waitForElementsOfPreferencesPage("aiwtachRadioButton");
			preferencesPage.clickOnElementsOfPreferencesPage("aiwtachRadioButton");
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			Assert.assertTrue(preferencesPage.verifyAirwatchConfiguration("CertificateAuthentication", LanguageCode),
					"Airwatch Configuration Failed with Certificate Authentication");
			Assert.assertTrue(preferencesPage.verifyAirwatchConfigurationWithSucessMessage(),
					"Airwatch failed to configured with success message");
			LOGGER.info("Airwatch Configured Successfully");
			waitForPageLoaded();
			gotoCompaniesTab();
			waitForPageLoaded();

			// Configure Airwatch for existing configuration error message
			impersonateCompanyByCompanyName(DeviceVariables.EMM_COMPANY2_NAME);
			preferencesPage.goToPreferencesTab();
			preferencesPage.clickOnElementsOfPreferencesPage("airwatchEditbutton");
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			Assert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("aiwtachRadioButton"),
					"Airwatch Toggle is not present");
			LOGGER.info("Airwatch Toggle is present");
			preferencesPage.waitForElementsOfPreferencesPage("aiwtachRadioButton");
			preferencesPage.clickOnElementsOfPreferencesPage("aiwtachRadioButton");
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			Assert.assertTrue(preferencesPage.verifyAirwatchConfiguration("CertificateAuthentication", LanguageCode),
					"Airwatch Configuration Failed with Basic Authentication");
			Assert.assertTrue(preferencesPage.verifyAlreadyLinkErrorMessage(LanguageCode),
					"Already Link error message in not displayed");
			LOGGER.info("Airwatch Existing Tenant Configuration Proceed successfully ");
		} finally {
			// Remove Airwatch Configuration with checkobox
			waitForPageLoaded();
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_AIRWATCH,
							"airwatchIntegrationDeleteButton", emmCompanyId),
					"Error occured while removing airwatch on Prefernce Page");
			Assert.assertTrue(
					preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, airwatchUserName,
							getEnvironment(environment)),
					"Error occured while removing airwatch user on User List Page");
		}
	}

	/**
	 * This Test verify Intune existing linking with creating two companies
	 * configuration in one tenant will display success message and same configuring
	 * in other tenant will display existing linking error message & deletes both
	 * the company
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI" }, description = "218196")
	public final void verifyPreventExistingIntuneLinkingConfiguration() throws Exception {
		try {
			// Login in to the MSP in which both above companies are present
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			// Remove Intune Configuration
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_INTUNE,
							"intuneIntegrationDeleteButton", emmCompanyId),
					"Error occured while removing microsoft intune on Prefernce Page");
			Assert.assertTrue(preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, intunehUserName,
					getEnvironment(environment)), "Error occured while removing intune user on User List Page");

			// Impersonating into a first company
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);

			// Configuring the Intune Integration on Preferences tab for first company
			sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			preferencesPage.goToPreferencesTab();
			preferencesPage.verifyElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.waitForElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.waitForElementsOfPreferencesPage("intuneRadioButton");	
			softAssert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("intuneRadioButton"),
					"Intune Toggle is not present");
			LOGGER.info("Intune Toggle is present");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneRadioButton");
			sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			softAssert.assertTrue(preferencesPage.verifyEmmStringOnIntunePage(LanguageCode),
					"All Strings are not present for Intune");
			LOGGER.info("All Strings are validated for Intune");
			Assert.assertTrue(
					preferencesPage.verifyIntuneConfiguration(PreferencesPage.intuneCredentials.get("DOMAIN_NAME"),
							PreferencesPage.intuneCredentials.get("INTUNE_ID"),
							PreferencesPage.intuneCredentials.get("INTUNE_PASSWORD")),
					"Intune Configuration is failed");
			LOGGER.info("Intune is configured successfully");
			gotoCompaniesTab();
			waitForPageLoaded();

			// Configure Intune for existing configuration error message
			sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			impersonateCompanyByCompanyName(DeviceVariables.EMM_COMPANY2_NAME);
			preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.verifyElementsOfPreferencesPage("intuneRadioButton");
			softAssert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("intuneRadioButton"),
					"Intune Toggle is not present");
			LOGGER.info("Intune Toggle is present");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneRadioButton");
			sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			softAssert.assertTrue(preferencesPage.verifyEmmStringOnIntunePage(LanguageCode),
					"All Strings are not present for Intune");
			LOGGER.info("All Strings are validated for Intune");
			Assert.assertTrue(
					preferencesPage
							.verifyExistingIntuneConfiguration(PreferencesPage.intuneCredentials.get("DOMAIN_NAME")),
					"Intune Configuration is failed for existing configuration.");
			Assert.assertTrue(preferencesPage.verifyAlreadyLinkErrorMessage(LanguageCode),
					"Already Link error message in not displayed");

			softAssert.assertAll();
			LOGGER.info("Existing Intune Configuration test passed.");

		} finally {
			// Remove Intune Configuration
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_INTUNE,
							"intuneIntegrationDeleteButton", emmCompanyId),
					"Error occured while removing microsoft intune on Prefernce Page");
			Assert.assertTrue(preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, intunehUserName,
					getEnvironment(environment)), "Error occured while removing intune user on User List Page");
		}
	}

	/**
	 * This test case verifies SNOW and NON SNOW settings are present for the sales
	 * admin
	 * 
	 * @throws Exception
	 */
	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI" }, description = "235323")
	public final void verifySnowNonSnowForSalesAdmin() throws Exception {
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_MSP_COMPANY_NAME);
		Assert.assertTrue(preferencesPage.verifySnowAndNonSnowSettingPresent(LanguageCode),
				"Error in verifying SNOW and Non SNOW settings present for the sales admin");
		LOGGER.info("Successfully verified SNOW and Non SNOW settings present for the sales admin");
	}

	/**
	 * This test case verifies SNOW and NON SNOW settings are not present for the
	 * sales specialist
	 * 
	 * @throws Exception
	 */
	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI" }, description = "235326")
	public final void verifySnowNonSnowForPartnerSpecialist() throws Exception {
		login("RESELLER_NEW_UI_US_SS_EMAIL", "RESELLER_NEW_UI_US_SS_PASSWORD");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.COMPANY_EMAIL_REPORTS_SS);
		Assert.assertTrue(!preferencesPage.verifySnowAndNonSnowSettingPresent(LanguageCode),
				"Error in verifying SNOW and Non SNOW settings present for the sales admin");
	}

	/*
	 * This test case validates SNOW configuration not allowed for Company at MSP
	 * level when Root admin selected Disabled for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 5, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "227580, 227613, 227619",enabled = false)
	public final void verifyMSPBehaviourWhenCompanySnowDisabled() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String expectedStatus = getTextLanguage(LanguageCode, "daas_ui", "global.disabled");
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(expectedStatus.toLowerCase(), LanguageCode);
		Assert.assertTrue(currentSNOWStatus.contains(getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable")),
				"Root Admin is not able to selected SNOW status as per given inputs.");
		LOGGER.info("Toggle successfully Disabled by Root Admin.");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_MSP_COMPANY_NAME);
		goToPreferencesTab();
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(companiesPage.tryToEnableInheritToggle(LanguageCode),
				"Error in verifying MSP is able to ON inherit service now toggle even if root admin selected Single Tenant.");
		LOGGER.info("MSP is not able to ON inherit service now toggle when Root admin selected Single Tenant.");
		String popupMessage = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(
				popupMessage
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"MSP is able to configured SNOW at company level when Root admin selected Disabled for Company");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Disabled for a Company then MSP is not able to configured SNOW at Company level.");
	}

	/*
	 * This test case validates SNOW configuration not allowed for Company at SNA
	 * level when Root admin selected Not Applicable for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 6, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "227580, 227613, 227619",enabled = false)
	public final void verifySNABehaviourWhenRootCompanySelectedNotApplicable() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoRootCompaniesTab();
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable"), LanguageCode);
		Assert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable")),
				"Root Admin is not able to selected SNOW status as per given inputs.");
		logout();
		login("SNOW_ADMIN_USER_EMAIL", "SNOW_ADMIN_USER_PASSWORD");
		goToPreferencesTab();
		String snowStatusAtSNA = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(
				snowStatusAtSNA
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"SNOW admin is able to configured SNOW when Root admin selected Not Applicable for Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Not Applicable for a Company then SNA is not able to configured SNOW.");
	}

	/*
	 * This test case validates SNOW configuration allowed for Company at MSP level
	 * when Root admin selected Single-Tenant for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 7, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "227837, 228106, 227248, 227245", enabled = false)
	public final void verifyMSPBehaviourWhenCompanySnowIsSingleTenant() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String expectedSNOWStatus = getTextLanguage(LanguageCode, "lhserver",
				"settings.preferences.service_now.tenant_config.options.single_tenant");
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(expectedSNOWStatus.toLowerCase(),
				LanguageCode);
		Assert.assertTrue(currentSNOWStatus.contains(getTextLanguage(LanguageCode, "daas_ui", "global.enabled")),
				"Error in verifying Root Admin is not able to selected SNOW status as per given inputs.");
		LOGGER.info("Toggle successfully selected Single-Tenant by Root Admin.");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_MSP_COMPANY_NAME);
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		preferencesPage.goToPreferencesTab();
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(companiesPage.tryToEnableInheritToggle(LanguageCode),
				"MSP is able to ON inherit service now toggle.");
		LOGGER.info("MSP is not able to ON inherit service now toggle when Root admin selected Single Tenant.");
		String snowConfigurationMessage = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "lhserver",
						"settings.preferences.service_now.tenant_config.options.single_tenant"),
				LanguageCode, ConstantURL.SNOW_URL1, ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(snowConfigurationMessage.contains(getTextLanguage(LanguageCode, "daas_ui", "global.enabled")),
				"Error in verifying MSP is not able to configured SNOW at company level when Root admin choosen Single-Tenant for Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Single-tenant for a Company then MSP is able to configured SNOW at Company level.");
		Assert.assertTrue(
				(companiesPage.resetConfigurationAtTenantLevel(LanguageCode))
						.contains(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured")),
				"Error in verifying MSP is not able to Reset configuration.");
		LOGGER.info("MSP is able to Reset configuration successfully.");
	}

	/*
	 * This test case validates SNOW configuration allowed for SNA at Company level
	 * when Root admin selected Single-Tenant for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 8, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "227837, 228106, 227248, 227245",enabled = false)
	public final void verifySNABehaviourWhenCompanySnowIsSingleTenant() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				(getTextLanguage(LanguageCode, "lhserver",
						"settings.preferences.service_now.tenant_config.options.single_tenant")).toLowerCase(),
				LanguageCode);
		Assert.assertTrue(currentSNOWStatus.contains(getTextLanguage(LanguageCode, "daas_ui", "global.enabled")),
				"Error in verifying Root Admin is not able to selected SNOW status as per given inputs.");
		logout();
		login("SNOW_ADMIN_EMAIL", "SNOW_ADMIN_PASSWORD");
		goToPreferencesTab();
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		String snowConfigurationMessage = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "lhserver",
						"settings.preferences.service_now.tenant_config.options.single_tenant"),
				LanguageCode, ConstantURL.SNOW_URL1, ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(
				snowConfigurationMessage.contains((getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))),
				"Error in verifying SNA is not able to configured SNOW.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Single-Tenant for a Company then SNA should be able to configured SNOW.");
		Assert.assertTrue(
				(companiesPage.resetConfigurationAtTenantLevel(LanguageCode))
						.contains(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured")),
				"Error in verifying SNA is not able to Reset configuration.");
		LOGGER.info("SNA is able to Reset configuration successfully.");
	}

	/**
	 * This test case verifies NON SNOW settings is present for the Report Admin
	 * 
	 * @throws Exception
	 */
	@Test(priority = 9, groups = { "REGRESSION", "REGRESSION_CI" }, description = "248607")
	public final void verifyNonSnowForReportAdmin() {
		try {
			login("COMPANY_REPORT_ADMIN_EMAIL", "COMPANY_REPORT_ADMIN_PASSWORD");
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			preferencesPage = preferencesPage.getInstance();
			gotoSettingsTabOfReportAdmin();
			Assert.assertTrue(preferencesPage.verifyNonSnowSettingPresent(),
					"Error in verifying SNOW and Non SNOW settings present for the sales admin");
			LOGGER.info("Successfully verified Non SNOW settings is present for the Report Admin user");
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in test case verifyNonSnowForReportAdmin " + ex.getStackTrace());
		}
	}

	/**
	 * This test case verifies Premium company should have visibility of Required
	 * app not installed incident.
	 */
	@Test(priority = 10, groups = { "REGRESSION", "REGRESSION_CI" }, description = "247513")
	public final void verifyRequiredAppNotInstalledSubTypeVisibilityForPremiumCompany() {
		try {
			login("MSP_ADMIN_US_EMAIL_03", "MSP_ADMIN_US_PASSWORD_03");
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			preferencesPage = preferencesPage.getInstance();
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(CompaniesVariables.PREMIUM_COMPANY_USER);
			Assert.assertTrue(preferencesPage.verifyIsRequiredAppNotInstalledSubtypePresent(),
					"Required app not installed incident subtype is not visible to premium subscription company");
			LOGGER.info(
					"Successfully validated 'Required app not installed' subtype is present to premium subscription company.");
		} catch (Exception e) {
			LOGGER.error(
					"Exception thrown in test case verifyRequiredAppNotInstalledIncidentAccess " + e.getStackTrace());
		}
	}

	/**
	 * This test case verifies Enhanced company should not have visibility of
	 * Required app not installed incident.
	 */
	@Test(priority = 11, groups = { "REGRESSION", "REGRESSION_CI" }, description = "247514")
	public final void verifyRequiredAppNotInstalledSubTypeVisibilityForEnhancedCompany() {
		try {
			login("MSP_ADMIN_US_EMAIL_03", "MSP_ADMIN_US_PASSWORD_03");
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			preferencesPage = preferencesPage.getInstance();
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(CompaniesVariables.ENHANCED_COMPANY_USER);
			Assert.assertFalse(preferencesPage.verifyIsRequiredAppNotInstalledSubtypePresent(),
					"Required app not installed incident subtype is visible to Enhanced subscription company");
			LOGGER.info(
					"Successfully validated 'Required app not installed' subtype is not visible to Enhanced subscription company.");
		} catch (Exception e) {
			LOGGER.error(
					"Exception thrown in test case verifyRequiredAppNotInstalledIncidentAccess " + e.getStackTrace());
		}
	}

	/**
	 * This test case verifies Standard company should not have visibility of
	 * Required app not installed incident.
	 */
	@Test(priority = 12, groups = { "REGRESSION", "REGRESSION_CI" }, description = "247514")
	public final void verifyRequiredAppNotInstalledSubTypeVisibilityForStandardCompany() {
		try {
			login("MSP_ADMIN_US_EMAIL_03", "MSP_ADMIN_US_PASSWORD_03");
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			preferencesPage = preferencesPage.getInstance();
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(CompaniesVariables.STANDARD_COMPANY_USER);
			Assert.assertFalse(preferencesPage.verifyIsRequiredAppNotInstalledSubtypePresent(),
					"Required app not installed incident subtype is visible to Standard subscription company");
			LOGGER.info(
					"Successfully validated 'Required app not installed' subtype is not visible to Standard subscription company.");
		} catch (Exception e) {
			LOGGER.error(
					"Exception thrown in test case verifyRequiredAppNotInstalledIncidentAccess " + e.getStackTrace());
		}
	}

	/**
	 * This test verifies chromebook validation error messages are displayed and
	 * verifies toggle behaviour by configuring and disabling the chromebook
	 * integration toggles. It checks if firware tab is displayed for device with
	 * client type Chromebook.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 13, groups = { "REGRESSION",
			"REGRESSION_CI" }, description = "253160, 240566, 248639, 248642, 248607, 253162,313341, 327799", enabled = false)
	public final void verifyChromebookIntegration() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String aUEValue;
		try {
			// Login to MSP account
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			// Remove Chromebook Configuration
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_CHROME,
							"chromeDeleteButton", emmCompanyId),
					"Error occured while removing chromebook on Prefernce Page");
			Assert.assertTrue(
					preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, chromehUserName,
							getEnvironment(environment)),
					"Error occured while removing chromebook user on User List Page");

			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			goToPreferencesTab();

			// Test Case1: Configuring Chrome enterprise
			LOGGER.info("Configuring Chrome enterprise integration");
			preferencesPage.scrollOnPreferencesPage("chromebookIntegrationEditButton");
			preferencesPage.waitForElementsOfPreferencesPage("chromebookIntegrationEditButton");
			preferencesPage.clickOnElementsOfPreferencesPage("chromebookIntegrationEditButton");
			preferencesPage.enterChromebookIntegrationDetails(
					PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME,
					PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD);
			preferencesPage.verifyChromeConfigurationSuccessMessage();
			LOGGER.info("Successfully verified chromebook integration");

			// Test case2: Verify Disabled String
			LOGGER.info("Configuring Chrome enterprise integration for disbaled string");
			refreshPage();
			waitForPageLoaded();
			preferencesPage.scrollOnPreferencesPage("chromebookIntegrationEditButton");
			preferencesPage.waitForElementsOfPreferencesPage("chromebookIntegrationEditButton");
			preferencesPage.clickOnElementsOfPreferencesPage("chromebookIntegrationEditButton");
			Assert.assertTrue(preferencesPage.verifyChrombookIntegrationDisabledStrings(LanguageCode),
					"Error occured while verifying disabled string for chromebook on Prefernce Page");
			LOGGER.info("Successfully verified chromebook integration for disbaled string");

			// Test case3: Verify AUE field for chrome device on device list and details
			// page
			gotoDevicesTab();
			refreshPage();
			waitForPageLoaded();
			resetTableConfiguration();
			deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", deviceName);
			deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
			aUEValue = deviceListPage.getTextOfDeviceListPage("aueColumnValue");
			deviceListPage.clickOnElementsOfDeviceListPage("deviceDetails");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("aue").equals(aUEValue),
					"Auto Updation Expiration date is different on detail page than list page");

			// Test case4: Verify Firmware tab for Chrome client type device.
			deviceListPage.waitForElementsOfDeviceListPage("firmwareTab");
			deviceListPage.clickOnElementsOfDeviceListPage("firmwareTab");
			softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("firmwareTab"),
					"Firmware Tab is not present for Chrome device");
			softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("firmwareTitle"),
					"Firmware Title is not present for Chrome device");
			softAssert.assertEquals(
					getDataFromApi("firmwareVersion", getEnvironment(environment) + ConstantURL.HPSURE_URL + deviceID),
					deviceListPage.getTextOfDeviceListPage("firmwareVersion"), "Firmware Version did not match");
			LOGGER.info("Successfully verified firmware tab for chromebook devices on device detail page.");
			softAssert.assertAll();
			LOGGER.info(
					"Successfully verified chromebook enterprise integration and firmware tab for chromebook devices on device detail page.");

		} finally {
			// Remove Chromebook Configuration
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_CHROME,
							"chromeDeleteButton", emmCompanyId),
					"Error occured while removing chromebook on Prefernce Page");
			Assert.assertTrue(
					preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, chromehUserName,
							getEnvironment(environment)),
					"Error occured while removing chromebook user on User List Page");
		}
	}

	/**
	 * This test validated the configuration AIrwatch & ChromeBook OR Intune &
	 * ChromeBook
	 * 
	 * @throws Exception
	 */
	@Test(priority = 14, groups = { "REGRESSION", "REGRESSION_CI" }, description = "Testcases 267971, 267972", enabled = false)
	public final void verifyCombineEMMConfiguration() throws Exception {
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String body;
		try {
			// Login to MSP account
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			gotoCompaniesTab();
			waitForPageLoaded();

			// Impersonating into a company
			impersonateCompanyByCompanyName(emmCompanyName);
			goToPreferencesTab();

			// Configuring the ChromeBook
			preferencesPage.clickOnElementsOfPreferencesPage("chromebookIntegrationEditButton");
			Assert.assertTrue(
					preferencesPage.enterChromebookIntegrationDetails(
							PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME,
							PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD),
					"Error occured in configuring chromebook integration");
			softAssert.assertTrue(preferencesPage.verifyChromeConfigurationSuccessMessageStrings(LanguageCode),
					"Error occured while saving chromebook configuration");
			LOGGER.info("Verified Chromebook Configuration");

			// Configuring the AirWatch
			preferencesPage.clickByJavaScriptOnPreferencesPage("airwatchEditbutton");
			Assert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("aiwtachRadioButton"),
					"Airwatch Toggle is not present");
			LOGGER.info("Airwatch Toggle is present");
			preferencesPage.clickOnElementsOfPreferencesPage("aiwtachRadioButton");
			Assert.assertTrue(preferencesPage.verifyAirwatchConfiguration("CertificateAuthentication", LanguageCode),
					"Airwatch Configuration Failed with Certificate Authentication");
			Assert.assertTrue(preferencesPage.verifyAirwatchConfigurationWithSucessMessage(),
					"Airwatch failed to configured with success message");
			LOGGER.info("Airwatch Configured Successfully");

			// Configuring the Intune
			preferencesPage.clickByJavaScriptOnPreferencesPage("intuneEditButton");
			preferencesPage.verifyElementsOfPreferencesPage("intuneRadioButton");
			softAssert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("intuneRadioButton"),
					"Intune Toggle is not present");
			LOGGER.info("Intune Toggle is present");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneRadioButton");
			softAssert.assertTrue(preferencesPage.verifyEmmStringOnIntunePage(LanguageCode),
					"All Strings are not present for Intune");
			LOGGER.info("All Strings are validated for Intune");

			// Test case : 3
			Assert.assertTrue(
					preferencesPage.verifyIntuneConfiguration(PreferencesPage.intuneCredentials.get("DOMAIN_NAME"),
							PreferencesPage.intuneCredentials.get("INTUNE_ID"),
							PreferencesPage.intuneCredentials.get("INTUNE_PASSWORD")),
					"Intune Configuration is failed");
			LOGGER.info("Intune is configured successfully");
		} finally {

			// Remove Chromebook Configuration
			body = "{\"tenant_Ids\":[\"" + emmCompanyId
					+ "\"],\"emm_provider\":\"Chrome\",\"is_cleanup_from_daas\":true}";
			Assert.assertTrue(
					getStatusCode(getSearchServiceApi(environment) + ConstantURL.DELETE_EMM, body, "DELETE",
							getEnvironment(environment)) == CommonVariables.CODESUCCESS,
					"Delete api not working for remving Chromebook configuration");
			LOGGER.info("Chromebook Configuration removed successfully through API ");

			// Remove Intune Configuration
			body = "{\"tenant_Ids\":[\"" + emmCompanyId
					+ "\"],\"emm_provider\":\"Intune\",\"is_cleanup_from_daas\":true}";
			Assert.assertTrue(
					getStatusCode(getSearchServiceApi(environment) + ConstantURL.DELETE_EMM, body, "DELETE",
							getEnvironment(environment)) == CommonVariables.CODESUCCESS,
					"Delete api not working for remving Intune configuration");
			LOGGER.info("Intune Configuration removed successfully through API ");

			// Remove Airwatch Configuration
			body = "{\"tenant_Ids\":[\"" + emmCompanyId
					+ "\"],\"emm_provider\":\"Airwatch\",\"is_cleanup_from_daas\":true}";
			Assert.assertTrue(
					getStatusCode(getSearchServiceApi(environment) + ConstantURL.DELETE_EMM, body, "DELETE",
							getEnvironment(environment)) == CommonVariables.CODESUCCESS,
					"Delete api not working for remving Airwatch configuration");
			LOGGER.info("Airwatch Configuration removed successfully through API ");
		}
	}

	@DataProvider(name = "supportSepcialistAndPartnerAdminUsers")
	private Object[][] createDataProvider() {
		Object[][] users = { { "SUPPORT_SPECIALIST_USER_EMAIL", "SUPPORT_SPECIALIST_USER_PASSWORD" },
				/* {"REPORT_ADMIN_USER_EMAIL", "REPORT_ADMIN_USER_PASSWORD" }, */
				{ "PARTNER_ADMIN_USER_EMAIL", "PARTNER_ADMIN_USER_PASSWORD" } };
		return users;
	}

	@DataProvider(name = "supportUsers")
	private Object[][] supportUsers() {
		Object[][] users = { { "SUPPORT_SPECIALIST_USER_EMAIL", "SUPPORT_SPECIALIST_USER_PASSWORD" } };
		return users;
	}

	@DataProvider(name = "reportAndITAdminUsers_OMSURLNotConfigured")
	private Object[][] reportAndITAdminUsers() {
		Object[][] users = { { "REPORT_ADMIN_USER_EMAIL1", "REPORT_ADMIN_USER_PASSWORD1" },
				{ "IT_ADMIN_USER_EMAIL1", "IT_ADMIN_USER_PASSWORD1" } };
		return users;
	}

	@DataProvider(name = "reportAndITAdminUsers_OMSURLConfigured")
	private Object[][] reportAndITAdminUsers2() {
		Object[][] users = { { "REPORT_ADMIN_USER_EMAIL", "REPORT_ADMIN_USER_PASSWORD" },
				{ "IT_ADMIN_USER_EMAIL", "IT_ADMIN_USER_PASSWORD" } };
		return users;
	}

	/**
	 * Test to verify error message when user tries to configure Microsoft Telemetry
	 * details with incorrect workspace id
	 * 
	 * @throws Exception
	 */
	@Test(priority = 15, groups = { "REGRESSION" }, description = "243643",enabled=false)
	public final void verifyMessageForIncorrectWorksapceId() throws Exception {
		login("SUPPORT_SPECIALIST_USER_EMAIL", "SUPPORT_SPECIALIST_USER_PASSWORD");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(PreferenceVariables.MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_CONFIGURED);
		preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
		preferencesPage.enterOMSdetailsAndLoginToAzure(PreferenceVariables.MICROSOFT_TELEMETRY_DOMAIN_NAME,
				PreferenceVariables.MICROSOFT_TELEMETRY_URL,
				PreferenceVariables.MICROSOFT_TELEMETRY_INCORRECT_WORKSPACE_ID);
		sleeper(3000);
		preferencesPage.verifyIncorrectWorkspaceIdMessage();
		boolean areDetailsSecured = preferencesPage.verifyPopUpWithSecuredDetails();
		Assert.assertTrue(areDetailsSecured, "Configured details must remain secured in the pop-up");
	}

	/**
	 * Test to verify support admin/specialist users are able to configure Microsoft
	 * Telemetry details
	 * 
	 * @throws Exception
	 */
	@Test(priority = 16, groups = { "REGRESSION" }, description = "211493, 224944, 238892, 239278, 255272",enabled=false)
	public final void verifySupportSpecialistCanCreateOMSURL() throws Exception {
		login("SUPPORT_SPECIALIST_USER_EMAIL", "SUPPORT_SPECIALIST_USER_PASSWORD");
		gotoCompaniesTab();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		impersonateCompanyByCompanyName(PreferenceVariables.MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_CONFIGURED);
		preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
		preferencesPage.enterOMSdetailsAndLoginToAzure(PreferenceVariables.MICROSOFT_TELEMETRY_DOMAIN_NAME,
				PreferenceVariables.MICROSOFT_TELEMETRY_URL, PreferenceVariables.MICROSOFT_TELEMETRY_WORKSPACE_ID);
		boolean areOMSDetailsReflected = preferencesPage
				.verifyOMSUrlConfigured(PreferenceVariables.MICROSOFT_TELEMETRY_URL);
		Assert.assertTrue(areOMSDetailsReflected, "Configured OMS details should be reflected on screen");
	}

	/**
	 * Test to verify configures Microsoft Telemetry details are displayed to other
	 * users
	 * 
	 * @throws Exception
	 */

	@Test(priority = 17, dataProvider = "supportSepcialistAndPartnerAdminUsers", groups = {
			"REGRESSION" }, description = "226026, 238887 ; Roles ~ Support Specialist, Reseller",enabled=false)
	public final void verifyConfiguredURLIsDisplayedToUsers(String username, String password) throws Exception {
		login(username, password);
		gotoCompaniesTab();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		impersonateCompanyByCompanyName(PreferenceVariables.MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_CONFIGURED);
		preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
		boolean areOMSDetailsReflected = preferencesPage
				.verifyOMSUrlConfigured(PreferenceVariables.MICROSOFT_TELEMETRY_URL);
		Assert.assertTrue(areOMSDetailsReflected, "Configured OMS details should be reflected on screen");
	}

	/**
	 * Test to verify microsoft telemetry user is able to view configured Microsoft
	 * Telemetry details
	 * 
	 * @throws Exception
	 */
	@Test(priority = 18, groups = { "REGRESSION" }, description = "211648, 211866, 211867, 211868, 224935",enabled=false)
	public final void verifyConfiguredURLIsDisplayedToMSTAUser() throws Exception {
		login("MICROSOFT_TELEMETRY_ADMIN_USER_EMAIL","MICROSOFT_TELEMETRY_ADMIN_USER_PASSWORD");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		sleeper(2000);
		gotoSettingsTabOfSnowAdmin();
		goToPreferencesTab();
		boolean areOMSDetailsReflected = preferencesPage
				.verifyOMSUrlConfigured(PreferenceVariables.MICROSOFT_TELEMETRY_URL);
		Assert.assertTrue(areOMSDetailsReflected, "Configured OMS details should be reflected on screen");
	}

	/**
	 * Test to verify primary admin user is able to view configured Microsoft
	 * Telemetry details
	 * 
	 * @throws Exception
	 */
	@Test(priority = 19, groups = { "REGRESSION" }, description = "238887",enabled=false)
	public final void verifyPrimaryAdminCanViewConfiguredOMSDetails() throws Exception {
		login("PRIMARY_ADMIN_USER_EMAIL", "PRIMARY_ADMIN_USER_PASSWORD");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		preferencesPage.clickOnElementsOfPreferencesPage("settingTab");
		preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
		boolean areOMSDetailsReflected = preferencesPage
				.verifyOMSUrlConfigured(PreferenceVariables.MICROSOFT_TELEMETRY_URL);
		Assert.assertTrue(areOMSDetailsReflected, "Configured OMS details should be reflected on screen");

	}

	/**
	 * Test to verify error message when user tries to configure Microsoft Telemetry
	 * details with somain name that is already configured
	 * 
	 * @throws Exception
	 */
	@Test(priority = 20, groups = { "REGRESSION" }, description = "240631",enabled=false)
	public final void verifyDuplicateDomainErrorWhileConfiguringOMSDetails() throws Exception {
		login("SUPPORT_SPECIALIST_USER_EMAIL", "SUPPORT_SPECIALIST_USER_PASSWORD");
		gotoCompaniesTab();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		impersonateCompanyByCompanyName(PreferenceVariables.MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_NOT_CONFIGURED);
		preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
		preferencesPage.enterOMSdetailsAndLoginToAzure(PreferenceVariables.MICROSOFT_TELEMETRY_DOMAIN_NAME,
				PreferenceVariables.MICROSOFT_TELEMETRY_EDIT_URL, PreferenceVariables.MICROSOFT_TELEMETRY_WORKSPACE_ID);
		boolean areDetailsSecured = preferencesPage.verifyPopUpWithSecuredDetails();
		preferencesPage.verifyDuplicateDomainMessage();
		Assert.assertTrue(areDetailsSecured, "Configured details must remain secured in the pop-up");
	}

	/*
	 * TESTS RELATED TO MICROSOFT TELEMETRY FOR SUPPORT SPECIALIST ROLE AND SUPPORT
	 * ADMIN ROLE
	 */

	/**
	 * Test to validate Microsoft Telemetry page title (User type: Support
	 * Specialist)
	 *
	 * @param username Username to be user for signIn
	 * @param password Password to be used for signIn
	 * @throws Exception
	 */
	@Test(priority = 21, dataProvider = "supportUsers", groups = {
			"REGRESSION" }, description = "205558 ; Roles ~ Support Specialist",enabled=false)
	public final void verifyMicrosoftTelemetryHeader(String username, String password) throws Exception {
		login(username, password);
		gotoCompaniesTab();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		impersonateCompanyByCompanyName(PreferenceVariables.MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_CONFIGURED);
		preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
		Assert.assertTrue(PreferenceVariables.MICROSOFT_TELEMETRY_TITLE.equalsIgnoreCase(preferencesPage.getTextOfPreferencesPage("microsoftTelemetryHeader")), " Microsoft Telemetry title doesn't match");
		LOGGER.info("Microsoft Telemetry title is matched");
	}

	/**
	 * Test to validate Select a Company label on Microsoft Telemetry page (User
	 * type: Support Specialist)
	 *
	 * @param username Username to be user for signIn
	 * @param password Password to be used for signIn
	 * @throws Exception
	 * 
	 *                   MS Telemetry page is not available in UI, so enabling false
	 */
	@Test(priority = 22, dataProvider = "supportUsers", groups = {
			"REGRESSION" }, description = "205558 ; Roles ~ Support Specialist", enabled = false)
	public final void verifySelectACompanyLabelAndCancelButton(String username, String password) throws Exception {
		login(username, password);
		gotoMicrosoftTelemetryTab();
		String selectACompanyText = getTextLanguage(LanguageCode, "daas_ui", "ms_telemetry.select_company.placeholder");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		Assert.assertTrue(preferencesPage.matchTextOfPreferencesPage("selectACompanyLabel", selectACompanyText),
				"Label on Microsoft Telemetry tab doesn't match");

		Assert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("cancelButton"),
				"Cancel button is not visible on pop up");

		LOGGER.info("Label on Microsoft Telemetry page is matched");
	}

	/**
	 * MS Telemetry page is not available in UI, so enabling false
	 * 
	 * Test to validate list of companies in listbox on Microsoft Telemetry page
	 * (User type: Support Specialist)
	 *
	 * @param username Username to be user for signIn
	 * @param password Password to be used for signIn
	 * @throws Exception
	 */
	@Test(priority = 23, dataProvider = "supportUsers", groups = {
			"REGRESSION" }, description = "225738, 225741 ; Roles ~ Support Specialist", enabled = false)
	public final void verifyCompaniesInSelectACompanyListbox(String username, String password) throws Exception {
		login(username, password);
		gotoMicrosoftTelemetryTab();

		String[] MS_TELEMETRY_COMPANY_URL_CONFIGURED = {
				PreferenceVariables.MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_CONFIGURED,
				PreferenceVariables.MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_NOT_CONFIGURED };

		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		boolean ispresent = preferencesPage.verifyCompaniesInListbox(MS_TELEMETRY_COMPANY_URL_CONFIGURED);
		Assert.assertTrue(ispresent, "Companies listed are not same as expected");
	}

	/**
	 * MS Telemetry page is not available in UI, so enabling false
	 * 
	 * Test to validate message displayed on Microsoft Telemetry page when OMS URL
	 * is not configured (User type: Support Specialist)
	 *
	 * @param username Username to be user for signIn
	 * @param password Password to be used for signIn
	 * @throws Exception
	 */
	@Test(priority = 24, dataProvider = "supportUsers", groups = {
			"REGRESSION" }, description = "205558 ; Roles ~ Support Specialist", enabled = false)
	public final void verifyMessageDisplayedOnSelectCompanyWithoutOMSURL(String username, String password)
			throws Exception {
		login(username, password);
		gotoMicrosoftTelemetryTab();

		String companyName = PreferenceVariables.MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_CONFIGURED;
		String urlNotConfiguredText = getTextLanguage(LanguageCode, "daas_ui", "ms_telemetry.no_config.title");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();

		boolean isPresent = preferencesPage.verifyMessageDisplayedWhenOMSURLNotConfigured(companyName,
				urlNotConfiguredText);
		Assert.assertTrue(isPresent, urlNotConfiguredText + " is not beng displayed.");
		LOGGER.info(urlNotConfiguredText + "Message when OMS URL not configured is matched");
	}

	/**
	 * MS Telemetry page is not available in UI, so enabling false
	 * 
	 * Test to validate user is being redirected to the correct URL after selecting
	 * company with OMS URL configured (User type: Support Specialist)
	 *
	 * @param username Username to be user for signIn
	 * @param password Password to be used for signIn
	 * @throws Exception
	 */
	@Test(priority = 25, dataProvider = "supportUsers", groups = {
			"REGRESSION" }, description = "205558 ; Roles ~ Support Specialist", enabled = false)
	public final void verifyUserRedirectsToConfiguredOMSURL(String username, String password) throws Exception {
		String configuredURL = "";
		String companyName = PreferenceVariables.MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_CONFIGURED;
		login(username, password);
		gotoMicrosoftTelemetryTab();

		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		configuredURL = preferencesPage.getConfiguredOMSURL(companyName);

		Assert.assertTrue(configuredURL.contains("login.microsoftonline.com"),
				"Current URL does not contain Azure URL");
		LOGGER.info("User is being navigated to the correct URL is verified");
	}

	/*
	 * TESTS RELATED TO MICROSOFT TELEMETRY FOR REPORT ADMIN AND IT ADMIN ROLES
	 */

	/**
	 * MS Telemetry tab is not available in UI, enabling false
	 * 
	 * Test to validate title and message on Microsoft Telemetry tab when OMS URL is
	 * not configured
	 *
	 * @param username Username to be user for signIn
	 * @param password Password to be used for signIn
	 * @throws Exception
	 */
	@Test(priority = 26, dataProvider = "reportAndITAdminUsers_OMSURLNotConfigured", groups = {
			"REGRESSION" }, description = "205558 ; Roles ~ Report Admin, IT Admin", enabled = false)
	public final void verifyMicrosoftTelemetryTitle_RA_ITA(String username, String password) throws Exception {
		login(username, password);

		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		Assert.assertTrue(preferencesPage.verifyElementIsNotPresent("microsoftTelemetryTab"));

		LOGGER.info("Verified that Microsoft Telemetry cconfigurations are not visible as its not configured");
	}

	/**
	 * MS Telemetry tab is not available in UI, enabling false
	 * 
	 * Test to validate URL and title on Microsoft Telemetry tab when OMS URL is
	 * configured
	 *
	 * @param username Username to be user for signIn
	 * @param password Password to be used for signIn
	 * @throws Exception
	 */

	@Test(priority = 27, dataProvider = "reportAndITAdminUsers_OMSURLConfigured", groups = {
			"REGRESSION" }, description = "205558 ; Roles ~ Report Admin, IT Admin", enabled = false)
	public final void verifyUserRedirectsToConfiguredOMSURL_RA_ITA(String username, String password) throws Exception {
		login(username, password);
		gotoMicrosoftTelemetryTab();
		gotoMicrosoftTelemetryTab();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();

		switchToDifferentTab();
		preferencesPage.waitForElementsOfPreferencesPage("microsoftAzureLogo");
		switchBackToParentWithoutCloseTab();
		switchToDifferentTab();
		Assert.assertTrue(getUrlOfCurrentPage().contains("login.microsoftonline.com"),
				"Current URL does not contain Azure URL");
		switchBackToParentWithoutCloseTab();
	}

	/**
	 * Test to verify support admin/specialist users are able to delete configured
	 * Microsoft Telemetry details
	 * 
	 * @throws Exception
	 */
	@Test(priority = 28, groups = { "REGRESSION" }, description = "211644, 238892, 249517",enabled=false)
	public final void verifyMSTACanDeleteOMSURL() throws Exception {
		login("SUPPORT_SPECIALIST_USER_EMAIL", "SUPPORT_SPECIALIST_USER_PASSWORD");
		gotoCompaniesTab();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		impersonateCompanyByCompanyName(PreferenceVariables.MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_CONFIGURED);
		preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
		preferencesPage.moveToElementOnPreferencesPage("3rdPartyAppTileHeader");
		preferencesPage.clickOnElementsOfPreferencesPage("microsoftTelemetryOMSURLEnabled");
		preferencesPage.clickOnElementsOfPreferencesPage("microsoftTelemetrySaveButton");
		Assert.assertTrue(preferencesPage.deleteOMSURL(PreferenceVariables.MSTUSER_TYPE_SUPPORT_SPECIALIST), "OMS URL could not be deleted");
	}

	/**
	 * Test to verify support admin/specialist users are able to edit configured
	 * Microsoft Telemetry details
	 * 
	 * @throws Exception
	 */
	@Test(priority = 29, groups = { "REGRESSION" }, dependsOnMethods = {
			"verifySupportSpecialistCanCreateOMSURL" }, description = "211500, 238892, 249517",enabled=false)
	public final void verifyMSTACanEditOMSURL() throws Exception {
		login("SUPPORT_SPECIALIST_USER_EMAIL", "SUPPORT_SPECIALIST_USER_PASSWORD");
		gotoCompaniesTab();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		impersonateCompanyByCompanyName(PreferenceVariables.MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_CONFIGURED);
		preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
		preferencesPage.enterOMSdetailsAndLoginToAzure(PreferenceVariables.MICROSOFT_TELEMETRY_DOMAIN_NAME,
				PreferenceVariables.MICROSOFT_TELEMETRY_EDIT_URL, PreferenceVariables.MICROSOFT_TELEMETRY_WORKSPACE_ID);
		sleeper(3000);
		boolean isOMSURLConfigured = preferencesPage
				.verifyOMSUrlConfigured(PreferenceVariables.MICROSOFT_TELEMETRY_EDIT_URL);
		Assert.assertTrue(isOMSURLConfigured, "OMS URL must have been configured");
		Assert.assertTrue(preferencesPage.deleteOMSURL(PreferenceVariables.MSTUSER_TYPE_SUPPORT_SPECIALIST), "OMS URL could not be deleted");
	}

	/*
	 * This test case validates SNOW configuration not allowed for SNA level when
	 * Root admin selected Not Applicable for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 30, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "227580, 227613, 227619", enabled = false)
	public final void verifySNABehaviourWhenRootCompanySelectedSingleTenant() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant"),
				LanguageCode);
		Assert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled")),
				"Root Admin is not able to selected SNOW status as Single-Tenant.");
		logout();
		login("SNOW_ADMIN_USER_EMAIL", "SNOW_ADMIN_USER_PASSWORD");
		goToPreferencesTab();
		String snowStatusAtSNALevel = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.enabled"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(snowStatusAtSNALevel.equals((getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))),
				"SNOW admin is able to configured SNOW at company level when Root admin selected Disabled for Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Disabled for a Company then SNA is not able to configured SNOW at Company level.");
		String SNOWStatus = companiesPage.resetConfigurationAtTenantLevel(LanguageCode);
		Assert.assertTrue(
				SNOWStatus
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"SNA is not able to reset configuration.");
		LOGGER.info("Company is successfully reset configuration.");
	}

	/*
	 * This test case validates SNOW configuration not allowed for SNA level when
	 * Root admin selected Not Applicable for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 31, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "227580, 227613, 227619", enabled = false)
	public final void verifySNABehaviourWhenRootCompanySelectedMultiTenant() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.multi_tenant"),
				LanguageCode);
		Assert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled")),
				"Root Admin is not able to selected SNOW status as Multi-Tenant.");
		logout();
		login("SNOW_ADMIN_USER_EMAIL", "SNOW_ADMIN_USER_PASSWORD");
		goToPreferencesTab();
		String snowStatusAtSNALevel = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.enabled"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(snowStatusAtSNALevel.equals((getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))),
				"SNOW admin is able to configured SNOW at company level when Root admin selected Disabled for Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Disabled for a SNA then SNA is not able to configured SNOW at Company level.");
		String SNOWStatus = companiesPage.resetConfigurationAtTenantLevel(LanguageCode);
		Assert.assertTrue(
				SNOWStatus
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"SNA is not able to reset configuration.");
		LOGGER.info("Company is successfully reset configuration.");
	}

	/*
	 * This test case validates SNOW configuration not allowed for Company level
	 * when Root admin selected Not Applicable for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 32, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "227580, 227613, 227619,239654",enabled = false)
	public final void verifyCompanyBehaviourWhenRootCompanySelectedNotApplicable() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable"), LanguageCode);
		Assert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable")),
				"Root Admin is not able to selected SNOW status as per given inputs.");
		logout();
		login("SNOW_ENABLE_COMPANY_REPORT_ADMIN_EMAIL", "SNOW_ENABLE_COMPANY_REPORT_ADMIN_PASSWORD");
		gotoSettingsTabWorkflow();
		goToPreferencesTab();
		String snowStatus = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(
				snowStatus
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"SNOW admin is able to configured SNOW when Root admin selected Not Applicable for Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Not Applicable for a Company then SNA is not able to configured SNOW.");
	}

	/*
	 * This test case validates SNOW configuration is allowed for Reseller-Company
	 * level when Root admin selected Single_Tenant for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 33, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "227580, 227613, 227619,239654", enabled = false)
	public final void verifyCompanyBehaviourWhenRootCompanySelectedSingleTenant() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant"),
				LanguageCode);
		Assert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled")),
				"Root Admin is not able to selected SNOW status as Single-Tenant.");
		logout();
		login("SNOW_ENABLE_COMPANY_REPORT_ADMIN_EMAIL", "SNOW_ENABLE_COMPANY_REPORT_ADMIN_PASSWORD");
		gotoSettingsTabWorkflow();
		goToPreferencesTab();
		String snowStatus = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.enabled"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(snowStatus.equals((getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))),
				"SNOW admin is able to configured SNOW at company level when Root admin selected Disabled for Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Single-Tenant for a Company then company is able to configured SNOW at Company level.");
		String companySNOWStatus = companiesPage.resetConfigurationAtTenantLevel(LanguageCode);
		Assert.assertTrue(
				companySNOWStatus
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"Company is not able to reset configuration.");
		LOGGER.info("Company is successfully reset configuration.");
	}

	/*
	 * This test case validates SNOW configuration is allowed for Reseller-Company
	 * level when Root admin selected Multi_Tenant for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 34, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "227580, 227613, 227619,239654",enabled = false)
	public final void verifyCompanyBehaviourWhenRootCompanySelectedMultiTenant() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoRootCompaniesTab();
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.multi_tenant"),
				LanguageCode);
		Assert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled")),
				"Root Admin is not able to selected SNOW status as Single-Tenant.");
		logout();
		login("SNOW_ENABLE_COMPANY_REPORT_ADMIN_EMAIL", "SNOW_ENABLE_COMPANY_REPORT_ADMIN_PASSWORD");
		gotoSettingsTabWorkflow();
		goToPreferencesTab();
		String snowStatus = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.enabled"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(snowStatus.equals((getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))),
				"SNOW admin is able to configured SNOW at company level when Root admin selected Disabled for Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Multi Tenat for a Company then company is able to configured SNOW at Company level.");
		String companySNOWStatus = companiesPage.resetConfigurationAtTenantLevel(LanguageCode);
		Assert.assertTrue(
				companySNOWStatus
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"Company is not able to reset configuration.");
		LOGGER.info("Company is successfully reset configuration.");
	}

	/*
	 * This test case validates SNOW configuration is not allowed for MSP-Company
	 * when Root admin selected Not Applicable for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 35, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "227580, 227613, 227619,239654",enabled = false)
	public final void verifyMSPUnderCompanyBehaviourWhenRootCompanySelectedNotApplicable() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable"), LanguageCode);
		Assert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable")),
				"Root Admin is not able to selected SNOW status as per given inputs.");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
		goToPreferencesTab();
		String snowStatus = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(
				snowStatus
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"SNOW admin is able to configured SNOW when Root admin selected Not Applicable for Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Not Applicable for a Company then MSP-Company is not able to configured SNOW.");
	}

	/*
	 * This test case validates SNOW configuration is allowed for MSP-Company level
	 * when Root admin selected Single_Tenant for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 36, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "227580, 227613, 227619,239654", enabled = false)
	public final void verifyMSPUnderCompanyBehaviourWhenRootCompanySelectedSingleTenant() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant"),
				LanguageCode);
		Assert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled")),
				"Root Admin is not able to selected SNOW status as Single-Tenant.");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
		goToPreferencesTab();
		String snowStatus = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.enabled"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(snowStatus.equals((getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))),
				"Company is able to configured SNOW at company level when Root admin selected SIngle-Tenant for Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Single-Tenat for a Company then MSP-company is able to configured SNOW at Company level.");
		String companySNOWStatus = companiesPage.resetConfigurationAtTenantLevel(LanguageCode);
		Assert.assertTrue(
				companySNOWStatus
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"Company is not able to reset configuration.");
		LOGGER.info("Company is successfully reset configuration.");
	}

	/*
	 * This test case validates SNOW configuration is allowed for MSP-Company level
	 * when Root admin selected Multi_Tenant for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 37, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "227580, 227613, 227619,239654,211652,211649",enabled = false)
	public final void verifyMSPAndCompanyBehaviourWhenRootCompanySelectedMultiTenant() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.multi_tenant"),
				LanguageCode);
		softAssert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled")),
				"Root Admin is not able to selected SNOW status as Multi-Tenant.");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
		goToPreferencesTab();
		String snowStatus = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.enabled"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		softAssert.assertTrue(snowStatus.equals((getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))),
				"Error MSP-Company is not able to configured SNOW at company level when Root admin selected Multi-Tenant for a Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Multi-Tenat for a Company then MSP-company is able to configured SNOW at Company level.");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		gotoSettingsTab();
		// Test Case 211652: [SNOW] Verify if SNOW url is configured at Tenant level
		// then any MSP will not allowed to use same url for SNOW setting.
		softAssert.assertTrue(
				settingsPage.verifySNOWFormAtGlobalLevel(LanguageCode, "right URL", ConstantURL.SNOW_URL1),
				"'The ServiceNow URL is not in use by another company.' is displayed successfully.Test cases failed : 227580, 227613, 227619,239654,211652,211649");
		LOGGER.info(
				"'The ServiceNow URL is in use by another company.' is displayed successfully.Passed test cases : 227580, 227613, 227619,239654,211652,211649");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
		goToPreferencesTab();
		String companySNOWStatus = companiesPage.resetConfigurationAtTenantLevel(LanguageCode);
		softAssert.assertTrue(
				companySNOWStatus
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"Company is not able to reset configuration.");
		LOGGER.info("Company is successfully reset configuration.");
		softAssert.assertAll();
	}

	/*
	 * This test case validates SNOW configuration is not allowed for
	 * Reseller-Company at SNA level when Root admin selected Not Applicable for a
	 * Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 39, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "227580, 227613, 227619,239654",enabled = false)
	public final void verifyResellerUnderCompanyBehaviourWhenRootCompanySelectedNotApplicable() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable"), LanguageCode);
		Assert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable")),
				"Root Admin is not able to selected SNOW status as per given inputs.");
		logout();
		login("RESELLER_SNOW_EMAIL", "RESELLER_SNOW_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
		goToPreferencesTab();
		String snowStatus = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.not.applicable"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(
				snowStatus
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"Company is not able to configured SNOW when Root admin selected Not Applicable for Comapny.");
		LOGGER.info(
				"Validated SNOW configuration is not allowed for Reseller-Company level when Root admin selected Not Applicable for a Company.");
	}

	/*
	 * This test case validates SNOW configuration is allowed for Reseller-Company
	 * level when Root admin selected Single_Tenant for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 40, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "227580, 227613, 227619,239654", enabled = false)
	public final void verifyResellerUnderCompanyBehaviourWhenRootCompanySelectedSingleTenant() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant"),
				LanguageCode);
		Assert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled")),
				"Root Admin is not able to selected SNOW status as Single-Tenant.");
		logout();
		login("RESELLER_SNOW_EMAIL", "RESELLER_SNOW_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
		goToPreferencesTab();
		String snowStatus = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.enabled"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(snowStatus.equals((getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))),
				"Company is able to configured SNOW at Reseller-company level when Root admin selected Single-Tenant for Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Single-Tenant for a Company then Reseller company is able to configured SNOW at Company level.");
		String companySNOWStatus = companiesPage.resetConfigurationAtTenantLevel(LanguageCode);
		Assert.assertTrue(
				companySNOWStatus
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"Company is not able to reset configuration.");
		LOGGER.info("Company is successfully reset configuration.");
	}

	/*
	 * This test case validates SNOW configuration is allowed for Reseller-Company
	 * level when Root admin selected Multi_Tenant for a Company.
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 41, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "227580, 227613, 227619,239654",enabled = false)
	public final void verifyResellerUnderCompanyBehaviourWhenRootCompanySelectedMultiTenant() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = companiesPage.getInstance();
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.multi_tenant"),
				LanguageCode);
		Assert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled")),
				"Root Admin is not able to selected SNOW status as Multi-Tenant.");
		logout();
		login("RESELLER_SNOW_EMAIL", "RESELLER_SNOW_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
		goToPreferencesTab();
		String snowStatus = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.enabled"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		Assert.assertTrue(snowStatus.equals((getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))),
				"Company is able to configured SNOW at company level when Root admin selected Multi-Tenant for Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Multi Tenat for a Company then Reseller-company is able to configured SNOW at Company level.");
		String companySNOWStatus = companiesPage.resetConfigurationAtTenantLevel(LanguageCode);
		Assert.assertTrue(
				companySNOWStatus
						.equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))),
				"Company is not able to reset configuration.");
		LOGGER.info("Company is successfully reset configuration.");
	}

	/*
	 * This test case validates Incident heading, Incident message, Incident Title
	 * and edit icon on Preferences tab.
	 */
	@Test(priority = 42, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "241828")
	public final void validateIncidentTilesOnPreferencesTab() throws Exception {
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
		goToPreferencesTab();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(
				preferencesPage.verifyIncidentHeading(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")),
				"Issue in validating Incident Heading on Preferences page.");
		LOGGER.info("Validated Incident heading is successfully displayed on Preferences tab.");
		softAssert.assertTrue(
				preferencesPage.verifyIncidentChooseTypeSubTypeMessage(
						getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.incidents")),
				"Issue in validating Incident Type-SubType message on Preferences page.");
		LOGGER.info("Validated Incident choose type subtye message is successfully displayed on Preferences tab.");
		softAssert.assertTrue(preferencesPage
				.verifyIncidentTitle(getTextLanguage(LanguageCode, "daas_ui", "incidents.label"), LanguageCode),
				"Issue in validating Incident Title on Preferences page.");
		LOGGER.info("Validated Incident Title is successfully displayed on Preferences tab.");
		softAssert.assertAll();
		Assert.assertTrue(preferencesPage.verifyIncidentEditIcon(),
				"Issue in validating Incident Edit icon on Preferences page.");
		LOGGER.info("Validated Incident Edit icon is successfully displayed on Preferences tab.");
	}

	/*
	 * This test case validates if all the subtypes is checked than on Preferences
	 * page show incidentTypeSubType status is displayed with every Type with All
	 * text on Incident tile of the preferences page.
	 */
	@Test(priority = 43, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "241828, 224930 NFR 178911 ")
	public final void validateIncidentTypeSubTypeSelectionStatusOnPreferencesTab() throws Exception {
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
		goToPreferencesTab();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		preferencesPage = preferencesPage.getInstance();
		preferencesPage.verifyAllTypeSubtypeIsSelected();
		String finalTypeSubTypeStatus = preferencesPage.verifyIncidentTypeSubtypeStatusIsUpdatedToAll();
		String all = getTextLanguage(LanguageCode, "daas_ui", "incidents.filters.assigned_to.all");
		String expectedString = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account") + " ("
				+ all + "):" + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hwchange") + " ("
				+ all + "):" + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hardwarehealth")
				+ " (" + all + "):" + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.oshealth")
				+ " (" + all + "):" + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security")
				+ " (" + all + "):"
				+ getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.softwarehealth") + " (" + all
				+ "):" + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned") + " ("
				+ all + "):";
		Assert.assertTrue(finalTypeSubTypeStatus.equals(expectedString),
				"Issue in validating validateIncidentTypeSubTypeSelectionStatusOnPreferencesTab method, Type Subtype is not matched as per ");
		LOGGER.info(
				"Validated All Incident type subype is displayed on incident tile of the Preferences tab successfully.");
	}

	/**
	 * This method validate when Root admin set any SNOW status for company, after
	 * logout Root admin is able to see same SNOW status for the same company. This
	 * method is validated all three status of SNOW : Not Applicable, Single-Tenant,
	 * Multi-Tenant using DataProvider.
	 * 
	 * @param snowConfigurationKey: Key of SNOW status from the daas_ui locals.
	 * @throws Exception
	 */

	@Test(priority = 44, description = "Test Case ID: 227838 ; Roles ~ N/A, Single Tenant, Multi Tenant", dataProvider = "snowStatusToBeSet", groups = {
			"REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyRootIsAbleToSeeOldConfigurationOnCompDetailPage(String snowConfigurationKey)
			throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		String snowStatusCurrent = preferencesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", snowConfigurationKey), LanguageCode);
		sleeper(2000);
		Assert.assertTrue(snowStatusCurrent.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", snowConfigurationKey)),
				"Root Admin is not able to selected given SNOW status.");
		logout();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoRootCompaniesTab();
		impersonateCompanyByRootAdmin("SNOW_ENABLE_COMPANY_REPORT_ADMIN");
		goToPreferencesTab();
		sleeper(2000);
		String snowStatusAfterLogout = preferencesPage.getSNOWStatusFromRoot(snowConfigurationKey);
		Assert.assertTrue(snowStatusAfterLogout.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", snowConfigurationKey)),
				"Root admin is not able to see same SNOW status after logout.");
		LOGGER.info("Validated successfully that Root admin is able to see same SNOW status after logout.");

	}

	/**
	 * This test case verifies MSP should have visibility of Required app not
	 * installed incident.
	 */
	@Test(priority = 45, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI",
			"ALL" }, description = "Test Case: 247506,NFR Story 247006")
	public final void verifyRequiredAppNotInstalledSubTypeVisibilityForMSP() {
		try {
			login("MSP_ADMIN_US_EMAIL_03", "MSP_ADMIN_US_PASSWORD_03");
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(CompaniesVariables.PREMIUM_COMPANY_USERNAME);
			Assert.assertTrue(preferencesPage.verifyIsRequiredAppNotInstalledSubtypePresent(),
					"Required app not installed incident subtype is not visible refer test case 247506");
			Assert.assertTrue(
					preferencesPage.verifyElementIsPresentOnPreferencesPage("requiredAppNotInstalledSubtype"),
					"Required app not installed incident subtype is selected refer test case 247509");
			LOGGER.info(
					"Successfully validated 'Required app not installed' subtype is present to premium subscription company.");
		} catch (Exception e) {
			LOGGER.error(
					"Exception thrown in test case verifyRequiredAppNotInstalledIncidentAccess " + e.getStackTrace());
		}
		LOGGER.info(
				"Test Case 247506/247509 verifyRequiredAppNotInstalledSubTypeVisibilityForMSP executed successfully");

	}

	/**
	 * This Data provider includes different SNOW status key.
	 * 
	 * @return Array of Keys
	 * @throws Exception
	 */
	@DataProvider
	public Object[] snowStatusToBeSet() throws Exception {
		Object[] data = new Object[3];
		data[0] = "global.not.applicable";
		data[1] = "settings.service_now.config.options.single_tenant";
		data[2] = "settings.service_now.config.options.multi_tenant";
		return data;
	}
//  We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 46, description = "TestCaseID: 240722,240711,240721,240802,240801,240803,221574,221575,221576,221578 ; Roles ~ MSP, Reseller, Snow Admin", dataProvider = "snowRoleToBeSet", groups = {
			"REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" },enabled = false)
	public final void verifySNOWUIAtCompanyPreferences(String email, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByEmailForRootAdmin(CompaniesVariables.SNOW_ENABLE_COMPANY_REPORT_ADMIN_EMAIL);
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		String snowStatusCurrent = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant"),
				LanguageCode);
		softAssert.assertTrue(
				snowStatusCurrent.equals(
						getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant")),
				"Root Admin is not able to selected given SNOW status.");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		if (!(email.equals("SNOW_ADMIN_USER_EMAIL"))) {
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
			goToPreferencesTab();
		}
		softAssert.assertTrue(
				preferencesPage.tenantLevelSNOWConfiguration(LanguageCode, ConstantURL.SNOW_URL1,
						ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1),
				"Error MSP is not able to configured SNOW at company level when Root admin selected single-Tenant for Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Multi Tenat for a Company then is able to configured SNOW at Company level. Covered TestCases: 240711, 240802, 240803, 221574, 221576.");
		softAssert.assertTrue(
				companiesPage.resetConfigurationAtTenantLevel(LanguageCode)
						.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured")),
				"Error MSP is not able to reset SNOW configuration at Company level.");
		LOGGER.info("Validated MSP is successfully reset SNOW configuration at Company level.");
		softAssert.assertAll();
	}

	/**
	 * This Data provider includes different SNOW status key.
	 * 
	 * @return Array of Keys
	 * @throws Exception
	 */
	@DataProvider
	public Object[][] snowRoleToBeSet() throws Exception {
		Object[][] data = new Object[3][2];
		data[0][0] = "MSP_SNOW_ADMIN_EMAIL";
		data[0][1] = "MSP_SNOW_ADMIN_PASSWORD";
		data[1][0] = "RESELLER_SNOW_EMAIL";
		data[1][1] = "RESELLER_SNOW_PASSWORD";
		data[2][0] = "SNOW_ADMIN_USER_EMAIL";
		data[2][1] = "SNOW_ADMIN_USER_PASSWORD";
		return data;
	}

	/**
	 * Verify Chromebook integration with non super admin user displays an error
	 * message
	 * 
	 * @throws Exception
	 */
	@Test(priority = 47, description = "Testcase 278593,278594", enabled = false)
	public final void verifyNonSuperAdminChromebookIntegration() throws Exception {
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		String body;
		try {
			// Configure DaaS tenant with non super admin role
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			goToPreferencesTab();
			LOGGER.info("Configuring Chrome enterprise integration");
			preferencesPage.scrollOnPreferencesPage("chromebookIntegrationEditButton");
			preferencesPage.clickOnElementsOfPreferencesPage("chromebookIntegrationEditButton");
			LOGGER.info("Configuring daas tenant with non super admin google user");
			Assert.assertTrue(
					preferencesPage.enterChromebookIntegrationDetails(
							PreferenceVariables.CHROME_GOOGLE_ADMIN_NON_SUPERADMIN_USER,
							PreferenceVariables.CHROME_GOOGLE_ADMIN_NON_SUPERADMIN_USER_PASSWORD),
					"Unable to configure chromebook integration");
			// Verify Error message for non super admin role
			Assert.assertTrue(preferencesPage.verifyChromeNonSuperAdminErrorMessage(LanguageCode),
					"Unable to verify non super admin error message");
			LOGGER.info("Successfully verified chromebook integration with non super admin user error message");
		} finally {

			// Remove Chromebook Configuration
			body = "{\"tenant_Ids\":[\"" + emmCompanyId
					+ "\"],\"emm_provider\":\"Chrome\",\"is_cleanup_from_daas\":true}";
			Assert.assertTrue(
					getStatusCode(getSearchServiceApi(environment) + ConstantURL.DELETE_EMM, body, "DELETE",
							getEnvironment(environment)) == CommonVariables.CODESUCCESS,
					"Delete api not working for removing Chromebook configuration");
			LOGGER.info("Chromebook Configuration removed successfully through API ");
		}
	}
	
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 48, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "216978,216962,216971,216960,216979,221764", enabled = false)
	public final void verifySSAndCompanyBehaviourForSNOW() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByEmailForRootAdmin(CompaniesVariables.SNOW_ENABLE_COMPANY_RA_EMAIL1);
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		// Test Case 216962: [SNOW] Verify SS is able to configured SNOW settings for
		// company.
		String currentSNOWStatus = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant"),
				LanguageCode);
		softAssert.assertTrue(
				currentSNOWStatus.equals(
						getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant")),
				"Root Admin is not able to selected SNOW status as Single-Tenant.");
		logout();
		login("MSP_SS_EMAIL", "MSP_SS_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME1);
		goToPreferencesTab();
		String snowStatus = companiesPage.tenantLevelSNOWConfiguration(
				getTextLanguage(LanguageCode, "daas_ui", "global.enabled"), LanguageCode, ConstantURL.SNOW_URL1,
				ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		softAssert.assertTrue(snowStatus.equals((getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))),
				"Error Company is not able to configured SNOW at company level when Root admin selected Single-Tenant for a Comapny.");
		LOGGER.info(
				"Validated When Root Admin selected SNOW Single-Tenat for a Company then MSP-company is able to configured SNOW at Company level.");
		logout();
		login("SNOW_ADMIN_USER_EMAIL1", "SNOW_ADMIN_USER_PASSWORD1");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(
				preferencesPage.verifyExistingSNOWStatusAtCompanyOrSNALevel(LanguageCode, ConstantURL.SNOW_URL1),
				"SNA is not able to see Company configured SNOW. Failed test cases:216978");
		LOGGER.info("Validated SNA is able to see Company configured SNOW.Passed Test cases: 216978");
		logout();
		login("MSP_SS_EMAIL", "MSP_SS_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME1);
		goToPreferencesTab();
		softAssert.assertTrue(
				preferencesPage.verifyExistingSNOWStatusAtCompanyOrSNALevel(LanguageCode, ConstantURL.SNOW_URL1),
				"Company is not able to see existing configured SNOW.Failed test case: 216960");
		LOGGER.info("Validated Company is able to see existing configured SNOW. Passed test case : 216960");
		// Test Case 216971: [SNOW] Verify SS is able to Delete already configured SNOW
		// setting for company.
		Assert.assertTrue(
				(companiesPage.resetConfigurationAtTenantLevel(LanguageCode))
						.contains(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured")),
				"Error in verifying MSP is not able to Reset configuration.Failed test case: 216971");
		LOGGER.info("SS is able to Reset configuration successfully. Passed test case : 216971");
		softAssert.assertAll();
	}

	/**
	 * This method validate when Root admin set any SNOW status for company, MSP is
	 * able to ON Inherit SNOW toggle for that company. This method is validated two
	 * status of SNOW : Single-Tenant, Multi-Tenant using DataProvider.
	 * 
	 * @param keyName: Key of SNOW status from the daas_ui locals.
	 * @throws Exception
	 */

	@Test(priority = 49, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, dataProvider = "snowStatusToBeSetRootLevel", description = "227250,187877,227245,187877 ; Roles ~ Single Tenant, Multi Tenant")
	public final void verifyInheritToggleValidationAtCompanyLevel(String keyName) throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoRootCompaniesTab();
		impersonateCompanyByEmailForRootAdmin(CompaniesVariables.SNOW_ENABLE_COMPANY_REPORT_ADMIN_EMAIL);
		goToPreferencesTab();
		sleeper(2000);
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		String currentSNOWStatus = companiesPage
				.setCompanySNOWToggleAtRoot(getTextLanguage(LanguageCode, "daas_ui", keyName), LanguageCode);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(currentSNOWStatus.equals(getTextLanguage(LanguageCode, "daas_ui", keyName)),
				"Error Root Admin is not able to selected SNOW status as " + keyName + ".");
		LOGGER.info("Validated Root Admin is able to selected SNOW status as " + keyName + ".");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
		goToPreferencesTab();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		if (keyName.contains("multi_tenant")) {
			preferencesPage.verifySNOWToggleOnOffValidationComapnyLevel("on", LanguageCode);
			LOGGER.info(
					"Validated when 'Inherit global ServiceNow' settings is enabled, the pencil button next to 'ServiceNow Integration' is disabled");
		} else
			softAssert.assertTrue(preferencesPage.verifySNOWToggleOnOffValidationComapnyLevel("off", LanguageCode),
					"Error in InheritToggle message of Company SNOW is not displayed properly, Failed Test case: 227250,187877,227245, 187877");
		LOGGER.info("Validated Company Preference SNOW toggle message dispalyed as expected.");
		softAssert.assertAll();
	}

	/**
	 * This Data provider includes different SNOW status key.
	 * 
	 * @return Array of Keys
	 * @throws Exception
	 */
	@DataProvider
	public Object[] snowStatusToBeSetRootLevel() throws Exception {
		Object[] data = new Object[2];
		data[0] = "settings.service_now.config.options.single_tenant";
		data[1] = "settings.service_now.config.options.multi_tenant";
		return data;
	}

	/**
	 * This method validate when Root admin set any SNOW status for company, MSP
	 * configured SNOW url is visible for company login preference.
	 * 
	 * @throws Exception
	 */
	/*
	 * We can not automate since SNOW is third party and UI elements control is not
	 * in our hand
	 */
	@Test(priority = 50, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "221758,221747,218321,221756,216979", enabled = false)
	public final void verifySNOWUrlVisibilityAtCompanyLevel() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByEmailForRootAdmin(CompaniesVariables.SNOW_ENABLE_COMPANY_REPORT_ADMIN_EMAIL);
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		String snowStatusCurrent = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant"),
				LanguageCode);
		softAssert.assertTrue(
				snowStatusCurrent.equals(
						getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant")),
				"Root Admin is not able to selected given SNOW status.");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
		goToPreferencesTab();
		softAssert.assertTrue(
				preferencesPage.tenantLevelSNOWConfiguration(LanguageCode, ConstantURL.SNOW_URL1,
						ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1),
				"Error MSP is not able to configured SNOW at company level when Root admin selected single-Tenant for Comapny. Failed Test case: 221747,218321");
		LOGGER.info(
				"Validated When Root Admin selected SNOW single Tenant for a Company then is able to configured SNOW at Company level. Covered TestCases: 221747, 218321");
		logout();
		login("SNOW_ENABLE_COMPANY_REPORT_ADMIN_EMAIL", "SNOW_ENABLE_COMPANY_REPORT_ADMIN_PASSWORD");
		gotoCompanySettingsTab();
		goToPreferencesTab();
		softAssert.assertTrue(preferencesPage.verifyURLIsVisibleAtCompanyLevel(LanguageCode, ConstantURL.SNOW_URL1),
				"Error MSP configured SNOW url is not visible at company level. Test case Failed: 221758");
		LOGGER.info(
				"Validated Company is able to see SNOW url at Preferences page when SNOW Configured by MSP. Test case passed: 221758");
		softAssert.assertTrue(
				companiesPage.resetConfigurationAtTenantLevel(LanguageCode)
						.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured")),
				"Error MSP is not able to reset SNOW configuration at Company level.");
		LOGGER.info("Validated MSP is successfully reset SNOW configuration at Company level.");
		softAssert.assertAll();
	}

	/**
	 * This method validate when SNA configured SNOW than SA/SS under Company is
	 * able to visible SNOW url configured by SNA.
	 * 
	 * @throws Exception
	 */
//	We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 51, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "217040,217041,221754,221754,187057,170639",enabled = false)
	public final void verifySNAConfiguredSNOWVisibilityForMSP() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByEmailForRootAdmin(CompaniesVariables.SNOW_ENABLE_COMPANY_RA_EMAIL1);
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		String snowStatusCurrent = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant"),
				LanguageCode);
		softAssert.assertTrue(
				snowStatusCurrent.equals(
						getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant")),
				"Root Admin is not able to selected given SNOW status.");
		logout();
		login("SNOW_ADMIN_USER_EMAIL1", "SNOW_ADMIN_USER_PASSWORD1");
		softAssert.assertTrue(
				preferencesPage.SNALevelSNOWConfiguration(LanguageCode, ConstantURL.SNOW_URL1,
						ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1),
				"Error SNA is not able to configured SNOW when Root admin selected single-Tenant for Comapny.");
		LOGGER.info("Validated SNA configured SNOW successfully.");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL1", "MSP_SNOW_ADMIN_PASSWORD1");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME1);
		goToPreferencesTab();
		softAssert.assertTrue(preferencesPage.verifyURLIsVisibleAtCompanyLevel(LanguageCode, ConstantURL.SNOW_URL1),
				"Error SNA configured SNOW url is not visible at company level.Test case fail: 217040");
		LOGGER.info(
				"Validated SA > Company is able to see SNOW url at Preferences page when SNOW Configured by SNA. Test case passed: 217040");
		logout();
		login("MSP_SS_EMAIL", "MSP_SS_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME1);
		goToPreferencesTab();
		softAssert.assertTrue(preferencesPage.verifyURLIsVisibleAtCompanyLevel(LanguageCode, ConstantURL.SNOW_URL1),
				"Error SNA configured SNOW url is not visible at company level. Test case Failed: 217041");
		LOGGER.info(
				"Validated SS > Company is able to see SNOW url at Preferences page when SNOW Configured by SNA. Test case passed: 217041");
		softAssert.assertTrue(
				companiesPage.resetConfigurationAtTenantLevel(LanguageCode)
						.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured")),
				"Error SS is not able to reset SNOW configuration at Company level.Failed Test Case: 221754, 187057");
		LOGGER.info(
				"Validated SS is successfully reset SNOW configuration at Company level.Pass test Case: 221754,187057");
		softAssert.assertAll();
	}

	@Test(priority = 52, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "187876")
	public final void verifySNOWInheritToggleForNewCompany() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert(); 
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance(); PreferencesPage
		preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		gotoRootCompaniesTab();
		String companyName = "SNOWComp"+generateRandomString(7);
		Assert.assertTrue(companiesPage.createCompanyUsingAPI(companyName, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID"), "", "", CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
		LOGGER.info(companyName + " company created");
		
		LOGGER.info("Company is created successfully from the Root.");
		logout();
		login("MSP_COMPANY_DETAILS_EMAIL_SNOW", "MSP_COMPANY_DETAILS_PASSWORD_SNOW");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(companyName);
		goToPreferencesTab();
		softAssert.assertTrue(preferencesPage.verifySNOWInheritToggleIsOffByDefault(),
				"Error for new company 'Inherit SNOW Toggle' is not visible in disable mode. Test Case Fail: 187876");
		LOGGER.info(
				"Validated for New Company 'Inherit SNOW Toggle' is visible in disable mode.Test case Pass: 187876");
		logout();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		gotoRootCompaniesTab();
		impersonateCompanyByCompanyName(companyName);
		String tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		companiesPage.removeCompanyUsingAPI(tenantID);
		logout();
		softAssert.assertAll();
	}
//We can not automate since SNOW is third party and UI elements control is not in our hand
	@Test(priority = 53, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "219613,219604", enabled = false)
	public final void verifyIncidentStatusOnSNOWConfiguration() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME,
				ConstantURL.SNOW_PASSWORD1);
		gotoRootCompaniesTab();
		impersonateCompanyByEmailForRootAdmin(CompaniesVariables.SNOW_ENABLE_COMPANY_REPORT_ADMIN_EMAIL);
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		String snowStatusCurrent = companiesPage.setCompanySNOWToggleAtRoot(
				getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant"),
				LanguageCode);
		softAssert.assertTrue(
				snowStatusCurrent.equals(
						getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant")),
				"Root Admin is not able to selected given SNOW status.");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL1", "MSP_SNOW_ADMIN_PASSWORD1");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME1);
		LOGGER.info("Step 2: Capture company details");
		String companyName = companiesPage.getTextOfCompaniesPage("companyNameShow");
		LOGGER.info("CompanyName is: " + companyName);
		String companyEmailId = companiesPage.getTextOfCompaniesPage("companyEmailId");
		LOGGER.info("Company EmailId is: " + companyEmailId);
		CompanyPin companypin = new CompanyPin();
		goToPreferencesTab();
		String companyPin = companypin.generateCompanyPin(LanguageCode);
		LOGGER.info("companyPin is : " + companyPin);
		LOGGER.info("Step 3: Enroll a fake device for this company");
		HashMap<String, String> deviceDetails = new HashMap<>();
		deviceDetails = EnrollFakeDevice.enrollFakeDeviceForIncident(companyName, companyPin, companyEmailId);
		Assert.assertTrue(!deviceDetails.isEmpty(), "Fake device couldnt be enrolled");
		String deviceSerialNumber = deviceDetails.get("deviceSerialNumber");
		LOGGER.info(deviceSerialNumber + " fake Device is Enrolled Successfully");
		LOGGER.info("Step 4: Submit an incident for this enrolled fake device");
		DeviceDetailsPage devicepage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String incidentTitle = "Test Incident for enrolled device " + deviceSerialNumber;
		String type = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account");
		String subtype = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.licensing");
		String incidentId = devicepage.submitCaseUsingAPI(type, subtype, incidentTitle, "", deviceDetails.get("host"),
				deviceDetails.get("tenantId"), deviceDetails.get("userAuthToken"), deviceDetails.get("deviceId"),
				deviceDetails.get("UserID"));
		Assert.assertFalse(Strings.isNullOrEmpty(incidentId),
				"No incident id was created, hence failing the test case");
		LOGGER.info("Incident ID: " + incidentId);
		Assert.assertTrue(!incidentId.isEmpty(), "Error occured while submitting an incident using API");
		goToPreferencesTab();
		softAssert.assertTrue(
				preferencesPage.tenantLevelSNOWConfiguration(LanguageCode, ConstantURL.SNOW_URL1,
						ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1),
				"Error MSP is not able to configured SNOW at company level when Root admin selected single-Tenant for Comapny. Failed Test case: 221747,218321");
		LOGGER.info(
				"Validated When Root Admin selected SNOW single Tenant for a Company then is able to configured SNOW at Company level. Covered TestCases: 221747, 218321");
		logout();
		softAssert.assertAll();
	}

	// This test case verifies if aggregation incident subtype is unsubscribed from
	// all incident types by default
	@Test(priority = 54, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "291586")
	public final void verifyAggregationIncidentSubtype() throws Exception {
		login("MSP_DEVICE_LIST_EMAIL_COMPANIES", "MSP_DEVICE_LIST_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesTab();
		LOGGER.info("Clicked on companies tab");
		impersonateCompanyByCompanyName(importCompany1);
		LOGGER.info("Redirected to companies details page");
		goToPreferencesTab();
		LOGGER.info("Clicked on preferences tab");
		preferencesPage.clickOnElementsOfPreferencesPage("incidentEditIcon");
		LOGGER.info("Clicked on incident notification edit icon");
		preferencesPage.clickOnElementsOfPreferencesPage("accountTypeEdit");
		LOGGER.info("Clicked on account type edit button");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("accountAggregationSubtype"),
				"Account aggregation incident subtype is selected by default");
		preferencesPage.clickOnElementsOfPreferencesPage("hardwareChangeTypeEdit");
		LOGGER.info("Clicked on hardware change type edit button");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("hardwareChangeAggregationSubtype"),
				"Hardware change aggregation incident subtype is selected by default");
		preferencesPage.clickOnElementsOfPreferencesPage("hardwareHealthEdit");
		LOGGER.info("Clicked on hardware health type edit button");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("hardwareHealthAggregationSubtype"),
				"Hardware health aggregation incident subtype is selected by default");
		preferencesPage.clickOnElementsOfPreferencesPage("osHealthTypeEdit");
		LOGGER.info("Clicked on os health type edit button");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("osHealthAggregationSubtype"),
				"OS health aggregation incident subtype is selected by default");
		preferencesPage.clickOnElementsOfPreferencesPage("securityTypeEdit");
		LOGGER.info("Clicked on security type edit button");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("securityAggregationSubtype"),
				"Security aggregation incident subtype is selected by default");
		preferencesPage.clickOnElementsOfPreferencesPage("softwareHealthTypeEdit");
		LOGGER.info("Clicked on software health type edit button");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("softwareHealthAggregationSubtype"),
				"Software health aggregation incident subtype is selected by default");
		softAssert.assertAll();
		LOGGER.info("All test cases of aggregation incident subtype passed successfully");
	}

	// This test case verifies if subtypes under predictive incident type is
	// unsubscribed by default
	// Predictive type is removed from UI
	@Test(priority = 55, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "TEST CASE 291756", enabled = false)
	public final void verifyPredictiveIncidentSubtype() throws Exception {
		login("MSP_DEVICE_LIST_EMAIL_COMPANIES", "MSP_DEVICE_LIST_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesTab();
		LOGGER.info("Clicked on companies tab");
		waitForPageLoaded();
		resetTableConfiguration();
		impersonateCompanyByCompanyName(importCompany);
		LOGGER.info("Redirected to companies details page");
		goToPreferencesTab();
		LOGGER.info("Clicked on preferences tab");
		preferencesPage.clickByJavaScriptOnPreferencesPage("incidentEditIcon");
		LOGGER.info("Clicked on incident notification edit icon");
		preferencesPage.clickOnElementsOfPreferencesPage("predictiveTypeEdit");
		LOGGER.info("Clicked on predictive incident type edit icon");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveAllSubtype"),
				"Predictive all incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveAntivirusSubtype"),
				"Predictive antivirus incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveBatteryNotDetectedSubtype"),
				"Predictive battery not detected incident subtype is selected by default");
		softAssert.assertFalse(
				preferencesPage.verifyElementsOfPreferencesPage("predictiveBatteryPredictiveFailureSubtype"),
				"Predictive battery predictive failure incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveACPUHighUtilisationSubtype"),
				"Predictive CPU High utilisation incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveFirewallSubtype"),
				"Predictive firewall incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveHDDSubtype"),
				"Predictive HDD incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveHDDPredictiveFailureSubtype"),
				"Predictive HDD predictive failure incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveHDDSmartEventFailureSubtype"),
				"Predictive HDD smart event failure incident subtype is selected by default");
		softAssert.assertFalse(
				preferencesPage.verifyElementsOfPreferencesPage("predictiveHDDStoargeCapacityFullSubtype"),
				"Predictive HDD storage capacity full incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveHeartBeatFailureSubtype"),
				"Predictive heart beat failure incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveMemorySubtype"),
				"Predictive memory incident subtype is selected by default");
		softAssert.assertFalse(
				preferencesPage.verifyElementsOfPreferencesPage("predictiveMemoryHighUtilisationSubtype"),
				"Predictive memory high utilisation incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveOSBSODSubtype"),
				"Predictive BSOD incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveOSUnexpectedCrashSubtype"),
				"Predictive OS unexpected crash incident subtype is selected by default");
		softAssert.assertFalse(
				preferencesPage.verifyElementsOfPreferencesPage("predictiveRequiredAppsNotInstalledSubtype"),
				"Predictive required apps not installed incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveSureClickSubtype"),
				"Predictive sure click incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveSystemErrorThermalSubtype"),
				"Predictive system error thermal incident subtype is selected by default");
		softAssert.assertFalse(preferencesPage.verifyElementsOfPreferencesPage("predictiveUnassignedSubtype"),
				"Predictive unassigned incident subtype is selected by default");
		softAssert.assertAll();
		LOGGER.info("All test cases of predictive incident subtype passed successfully");
	}

// Generic type is removed from UI
	@Test(priority = 56, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "TEST CASE 291616", enabled = false)
	public final void verifyGenericIncidentTypeOnIncidentPage() throws Exception {
		login("MSP_DEVICE_LIST_EMAIL_COMPANIES", "MSP_DEVICE_LIST_PASSWORD_COMPANIES");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		gotoIncidentTab();
		LOGGER.info("Clicked on incidents tab");
		resetTableConfiguration();
		waitForPageLoaded();
		preferencesPage.clickOnElementsOfPreferencesPage("typeDropdown");
		LOGGER.info("Clicked on type dropdown");
		Assert.assertTrue(
				preferencesPage.getTextOfListOfPreferencesPage("typeOptionsList")
						.contains(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
								"com.hp.mpi.icm.type.generic").toLowerCase()),
				"Generic incident type is not displayed under type dropdown");
		LOGGER.info("Test case of generic incident type passed successfully");
	}

	/**
	 * This test verifies the new tenant is not configured with CromeBook
	 * Integration, if already configured tenant is present
	 * 
	 * @throws Exception
	 */
	@Test(priority = 57, groups = { "REGRESSION", "REGRESSION_CI" }, description = "Testcase 248643", enabled = false)
	public final void verifyPreventLinkingOfChromeBookConfiguration() throws Exception {

		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		try {
			// Login in to the MSP in which both above companies are present
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");

			// Impersonating into a first company
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);

			// Configuring the ChromeBook Integration on Preferences tab for first company
			LOGGER.info("Configuring a Chromebook Integration in a first company..");
			goToPreferencesTab();
			preferencesPage.clickOnElementsOfPreferencesPage("chromebookIntegrationEditButton");
			Assert.assertTrue(
					preferencesPage.enterChromebookIntegrationDetails(
							PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME,
							PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD),
					"Error occured in configuring chromebook integration");
			Assert.assertTrue(preferencesPage.verifyChromeConfigurationSuccessMessage(),
					"Error occured while saving chromebook configuration");
			LOGGER.info("Configured Chromebook Integration for first company");

			// Impersonating into a second company
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(DeviceVariables.EMM_COMPANY2_NAME);

			// Configuring the ChromeBook Integration on Preferences tab for second company
			LOGGER.info("Configuring a Chromebook Integration in a second company..");
			goToPreferencesTab();
			preferencesPage.clickOnElementsOfPreferencesPage("chromebookIntegrationEditButton");
			Assert.assertTrue(
					preferencesPage.enterChromebookIntegrationDetails(
							PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME,
							PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD),
					"Error occured in configuring chromebook integration");
			Assert.assertTrue(preferencesPage.verifyAlreadyLinkErrorMessage(LanguageCode),
					"Already Link error message in not displayed");
			LOGGER.info(
					"Verified that new tenant is not configured with CromeBook Integration if already configured tenant is present");
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in test case verifyPreventLinkingOfChromeBookConfiguration "
					+ ex.getStackTrace());
		} finally {

			// Remove Chromebook Configuration
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_CHROME,
							"chromeDeleteButton", emmCompanyId),
					"Error occured while removing chromebook on Prefernce Page");
			Assert.assertTrue(
					preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, chromehUserName,
							getEnvironment(environment)),
					"Error occured while removing chromebook user on User List Page");
		}
	}

	/**
	 * This test verifies remove chromebook integration and verify unlinking in
	 * devices tab
	 * 
	 * @throws Exception
	 */
	@Test(priority = 59, groups = { "REGRESSION",
			"REGRESSION_CI" }, description = "300832 Failing due to Bug 275101: [Core][EMM][DaaS] Unable to get client application \"Google admin Console\" on device list and details page for chromebook devices\" Test case id:314268", enabled=false)
	public final void verifyRemoveChromebookIntegration() throws Exception {
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();

		try {
			// Login to MSP account
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);

			// Test case 1: Verify chromebook integration removal in devices tab without
			// checkbox
			// Configuring the ChromeBook Integration on Preferences tab for company
			LOGGER.info("Configuring a Chromebook Integration in a first company..");
			goToPreferencesTab();
			preferencesPage.clickOnElementsOfPreferencesPage("chromebookIntegrationEditButton");
			Assert.assertTrue(
					preferencesPage.enterChromebookIntegrationDetails(
							PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME,
							PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD),
					"Error occured in configuring chromebook integration");
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			Assert.assertTrue(preferencesPage.verifyChromeConfigurationSuccessMessage(),
					"Error occured while saving chromebook configuration");
			LOGGER.info("Configured Chromebook Integration in a first company");

			/*
			 * //Get devices Serial number for verification sleeper(3000);// Due to
			 * inconsistent UI response we have to put wait here.(Tried many ways but this
			 * is the final solution which works as expected.) gotoDevicesTab();
			 * List<String> enrolledSerialNoList = new ArrayList<String>();
			 * enrolledSerialNoList=preferencesPage.getEnrolledDevicesSerialNo(
			 * emmCompanyName,"Google Chrome Enterprise");
			 */

			// Remove chromebook integration without checkbox
			LOGGER.info("Configuring a Chromebook Integration in a first company..");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			// Assert.assertTrue(preferencesPage.removeEmmIntegration(LanguageCode,PreferenceVariables.CHROME,"chromebookIntegrationDeleteButton",false),
			// "Error occured while removing chromebook configuration on Prefernce Page");
			Assert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("chromebookIntegrationDeleteButton"),
					"Delete button is not removed after removing configuration without checkbox");

			// Verification pending due to Bug 275101: [Core][EMM][DaaS] Unable to get
			// client application \"Google admin Console\" on device list and details page
			// for chromebook devices\
			/*
			 * //Verify chromebook integration without checkbox in devices tab
			 * sleeper(3000);// Due to inconsistent UI response we have to put wait
			 * here.(Tried many ways but this is the final solution which works as
			 * expected.) gotoDevicesTab(); Assert.assertTrue(preferencesPage.
			 * verifyEMMDeviceClientApplicationRemovalOnDevicesTab(LanguageCode,
			 * emmCompanyName,enrolledSerialNoList),
			 * "Verifcation failed for chromebook integration removal without checkbox on devices tab."
			 * );
			 */

			// Test Case2: Verify chromebook integration removal with device checkbox
			// Configuring the ChromeBook Integration on Preferences tab for first company
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			LOGGER.info("Configuring a Chromebook Integration in a first company..");
			goToPreferencesTab();
			preferencesPage.clickOnElementsOfPreferencesPage("chromebookIntegrationEditButton");
			Assert.assertTrue(
					preferencesPage.enterChromebookIntegrationDetails(
							PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME,
							PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD),
					"Error occured in configuring chromebook integration");
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			Assert.assertTrue(preferencesPage.verifyChromeConfigurationSuccessMessage(),
					"Error occured while saving chromebook configuration");
			LOGGER.info("Configured Chromebook Integration in a first company");

			// Remove chromebook integration with checkbox
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			// Assert.assertTrue(preferencesPage.removeEmmIntegration(LanguageCode,PreferenceVariables.CHROME,"chromebookIntegrationDeleteButton",true),
			// "Error occured while removing chromebook configuration on Prefernce Page");
			Assert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("chromebookIntegrationDeleteButton"),
					"Delete button is not removed after removing configuration with checkbox");

			// Verification pending due to Bug 275101: [Core][EMM][DaaS] Unable to get
			// client application \"Google admin Console\" on device list and details page
			// for chromebook devices\
			/*
			 * //Verify chromebook integration with checkbox in devices tab sleeper(3000);//
			 * Due to inconsistent UI response we have to put wait here.(Tried many ways but
			 * this is the final solution which works as expected.) gotoDevicesTab();
			 * Assert.assertTrue(preferencesPage.verifyEMMConfigurationRemovalWithCheckbox(
			 * LanguageCode, emmCompanyName,PreferenceVariables.CHROME),
			 * "Error occured while removing chromebook configuration on devices tab");
			 */

		} finally {

			// Remove Chromebook Configuration with checkobox
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_CHROME,
							"chromeDeleteButton", emmCompanyId),
					"Error occured while removing chromebook on Prefernce Page");
			Assert.assertTrue(
					preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, chromehUserName,
							getEnvironment(environment)),
					"Error occured while removing chromebook user on User List Page");
		}
	}

	/**
	 * This test verifies remove Intune integration and verify unlinking in devices
	 * tab
	 * 
	 * @throws Exception
	 */
	@Test(priority = 60, groups = { "REGRESSION", "REGRESSION_CI" }, description = "300832")
	public final void verifyRemoveIntuneIntegration() throws Exception {
		// Login to MSP account
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver());
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		try {
			
			// Remove Intune Configuration
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_INTUNE,
							"intuneIntegrationDeleteButton", emmCompanyId),
					"Error occured while removing microsoft intune on Prefernce Page");
			
			userPage.clickOnElementsOfUserPage("userTab");
			Assert.assertTrue(preferencesPage.removeEmmUser(LanguageCode, emmCompanyName,intunehUserName,getEnvironmentSpecificData(System.getProperty("environment"), "ROLE_ID_URL")),
			  "Error occured while removing intune user on User List Page");
			 
			companiesPage.clickOnElementsOfCompaniesPage("companiesTab");
			impersonateCompanyByCompanyName(emmCompanyName);

			// Test case 1: Verify Intune integration removal in devices tab without
			// checkbox
			goToPreferencesTab();
			sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			preferencesPage.scrollOnPreferencesPage("intuneEditButton");
			preferencesPage.waitForElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.waitForElementsOfPreferencesPage("intuneRadioButton");
			preferencesPage.verifyElementsOfPreferencesPage("intuneRadioButton");
			LOGGER.info("Intune Toggle is present");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneRadioButton");
			Assert.assertTrue(
					preferencesPage.verifyIntuneConfiguration(PreferencesPage.intuneCredentials.get("DOMAIN_NAME"),
							PreferencesPage.intuneCredentials.get("INTUNE_ID"),
							PreferencesPage.intuneCredentials.get("INTUNE_PASSWORD")),
					"Intune Configuration is failed");
			LOGGER.info("Intune is configured successfully");

			// Get devices Serial number for verification
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			LOGGER.info("Configuring a Intune Integration in a first company..");
			companiesPage.clickOnElementsOfCompaniesPage("companiesTab");
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEmmIntegration(LanguageCode, PreferenceVariables.INTUNE,
							"intuneIntegrationDeleteButton", false),
					"Error occured while removing microsoft intune on Prefernce Page");

			// Verify Intune integration with checkbox in devices tab
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			gotoDevicesTab();
			Assert.assertTrue(
					preferencesPage.verifyEMMConfigurationRemovalWithCheckbox(LanguageCode, emmCompanyName,
							PreferenceVariables.INTUNE),
					"Error occured while removing microsoft intune configuration on devices tab");

		} finally {

			// Remove Intune Configuration with checkobox
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_INTUNE,
							"intuneIntegrationDeleteButton", emmCompanyId),
					"Error occured while removing microsoft intune on Prefernce Page");
			userPage.clickOnElementsOfUserPage("userTab");
			Assert.assertTrue(preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, intunehUserName,
					getEnvironment(environment)), "Error occured while removing intune user on User List Page");
		}
	}


	/**
	 * This test verifies remove Airwatch integration and verify unlinking in
	 * devices tab
	 * 
	 * @throws Exception
	 */
	@Test(priority = 61, groups = { "REGRESSION", "REGRESSION_CI" }, description = "300832")
	public final void verifyRemoveAirwatchIntegration() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();	
		try {
			// Login to MSP account
			// Remove Airwatch Configuration with checkobox
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_AIRWATCH,
							"airwatchIntegrationDeleteButton", emmCompanyId),
					"Error occured while removing airwatch on Prefernce Page");
			gotoUserTab();
			Assert.assertTrue(
					preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, airwatchUserName,
							getEnvironment(environment)),
					"Error occured while removing airwatch user on User List Page");

			// Disabled since airwatch device is not available on airwatch portal
			/*
			 * gotoCompaniesTab(); waitForPageLoaded();
			 * impersonateCompanyByCompanyName(emmCompanyName);
			 * 
			 * // Test case 1: Verify Airwatch integration removal in devices tab without
			 * checkbox // Configuring the Airwatch Integration on Preferences tab for first
			 * company preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
			 * preferencesPage.clickByJavaScriptOnPreferencesPage("airwatchEditbutton");
			 * Assert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage(
			 * "aiwtachRadioButton"), "Airwatch Toggle is not present");
			 * LOGGER.info("Airwatch Toggle is present");
			 * preferencesPage.clickOnElementsOfPreferencesPage("aiwtachRadioButton");
			 * Assert.assertTrue(preferencesPage.verifyAirwatchConfiguration(
			 * "CertificateAuthentication", LanguageCode),
			 * "Airwatch Configuration Failed with Certificate Authentication");
			 * Assert.assertTrue(preferencesPage.
			 * verifyAirwatchConfigurationWithSucessMessage(),
			 * "Airwatch failed to configured with success message");
			 * LOGGER.info("Airwatch Configured Successfully");
			 * 
			 * // Get devices Serial number for verification sleeper(3000);// Due to
			 * inconsistent UI response we have to put wait here.(Tried many ways but this
			 * is the final solution which works as expected.) gotoDevicesTab();
			 * List<String> enrolledSerialNoList = new ArrayList<String>();
			 * enrolledSerialNoList =
			 * preferencesPage.getEnrolledDevicesSerialNo(emmCompanyName,
			 * "VMware Workspace ONE");
			 * 
			 * // Remove Airwatch integration without checkbox
			 * LOGGER.info("Configuring a Airwatch Integration in a first company..");
			 * gotoCompaniesTab(); waitForPageLoaded();
			 * impersonateCompanyByCompanyName(emmCompanyName);
			 * Assert.assertTrue(preferencesPage.removeEmmIntegration(LanguageCode,
			 * PreferenceVariables.AIRWATCH, "airwatchIntegrationDeleteButton", false),
			 * "Error occured while removing airwatch configuration on Prefernce Page");
			 * 
			 * // Verify Intune integration without checkbox in devices tab sleeper(3000);//
			 * Due to inconsistent UI response we have to put wait here.(Tried many ways but
			 * this is the final solution which works as expected.) gotoDevicesTab();
			 * waitForPageLoaded(); //Assert.assertTrue(preferencesPage.
			 * verifyEMMDeviceClientApplicationRemovalOnDevicesTab(LanguageCode,
			 * emmCompanyName, enrolledSerialNoList),
			 * "Verifcation failed for intune integration removal without checkbox on devices tab."
			 * );
			 * Assert.assertTrue(preferencesPage.verifyEMMConfigurationRemovalWithCheckbox(
			 * LanguageCode, emmCompanyName,PreferenceVariables.AIRWATCH),
			 * "Error occured while removing microsoft intune configuration on devices tab"
			 * );
			 */

			// Test Case2: Verify Airwatch integration removal with device checkbox
			// Configuring the Airwatch Integration on Preferences tab for first company
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			LOGGER.info("Configuring a Airwatch Integration in a first company..");
			preferencesPage.goToPreferencesTab();
			preferencesPage.scrollOnPreferencesPage("airwatchEditbutton");
			preferencesPage.waitForElementsOfPreferencesPage("airwatchEditbutton");
			preferencesPage.clickOnElementsOfPreferencesPage("airwatchEditbutton");
			Assert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("aiwtachRadioButton"),
					"Airwatch Toggle is not present");
			LOGGER.info("Airwatch Toggle is present");
			preferencesPage.clickOnElementsOfPreferencesPage("aiwtachRadioButton");
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			Assert.assertTrue(preferencesPage.verifyAirwatchConfiguration("CertificateAuthentication", LanguageCode),
					"Airwatch Configuration Failed with Certificate Authentication");
			Assert.assertTrue(preferencesPage.verifyAirwatchConfigurationWithSucessMessage(),
					"Airwatch failed to configured with success message");
			LOGGER.info("Airwatch Configured Successfully");

			// Remove chromebook integration with checkbox
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_AIRWATCH,
							"airwatchIntegrationDeleteButton", emmCompanyId),
					"Error occured while removing airwatch on Prefernce Page");

			// Verify chromebook integration with checkbox in devices tab
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			gotoDevicesTab();
			Assert.assertTrue(
					preferencesPage.verifyEMMConfigurationRemovalWithCheckbox(LanguageCode, emmCompanyName,
							PreferenceVariables.AIRWATCH),
					"Error occured while removing microsoft intune configuration on devices tab");

		} finally {

			// Remove Airwatch Configuration with checkobox
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(emmCompanyName);
			Assert.assertTrue(
					preferencesPage.removeEMMIntegrationUsingAPI(environment, PreferenceVariables.EMM_AIRWATCH,
							"airwatchIntegrationDeleteButton", emmCompanyId),
					"Error occured while removing airwatch on Prefernce Page");
			gotoUserTab();
			Assert.assertTrue(
					preferencesPage.removeEmmUser(LanguageCode, emmCompanyName, airwatchUserName,
							getEnvironment(environment)),
					"Error occured while removing airwatch user on User List Page");
		}
	}

	/**
	 * Test to verify microsoft telemetry user is able to configured Microsoft
	 * Telemetry
	 * 
	 * @throws Exception
	 */
	@Test(priority = 62, groups = { "REGRESSION" }, description = "211648, 211866, 211867, 211868, 224935",enabled=false)
	public final void verifyMSTAUserConfiguredOMSURL() throws Exception {
		login("MICROSOFT_TELEMETRY_ADMIN_USER_EMAIL","MICROSOFT_TELEMETRY_ADMIN_USER_PASSWORD");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver());
		preferencesPage = preferencesPage.getInstance();
		gotoSettingsTabOfSnowAdmin();
		goToPreferencesTab();
		preferencesPage.enterOMSdetailsAndLoginToAzure(PreferenceVariables.MICROSOFT_TELEMETRY_DOMAIN_NAME,
				PreferenceVariables.MICROSOFT_TELEMETRY_URL, PreferenceVariables.MICROSOFT_TELEMETRY_WORKSPACE_ID);
		boolean areOMSDetailsReflected = preferencesPage
				.verifyOMSUrlConfigured(PreferenceVariables.MICROSOFT_TELEMETRY_URL);
		Assert.assertTrue(areOMSDetailsReflected, "Configured OMS details should be reflected on screen");
		sleeper(2000);
		Assert.assertTrue(preferencesPage.deleteOMSURL(PreferenceVariables.MSTUSER_TYPE_MST), "OMS URL could not be deleted");
	}

	/**
	 * verify EMM Tool configured for Chrome Enterprise,Airwatch and Intune
	 * 
	 * @throws Exception
	 */

	@Test(priority = 63, groups = { "REGRESSION", "REGRESSION_CI", "ALL", "ALL_CI" },description = "Testcase 268162 ,268406,268166,268408,268182,269562,268179 268397,268396,268390,442974 ; Roles ~ MSP")

	public final void verifyEMMSideMenuTab() throws Exception {
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();

		EMMToolPage emmToolPage = new EMMToolPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String companyName = "AutoTest" + generateRandomString(7), tenantID, apiurl, tenantIdCompany = null;
		try {
			// Creating a company
			login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
			gotoCompaniesTab();
			Assert.assertTrue(
					companiesPage.createCompanyUsingAPI(companyName,
							getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode,
							getEnvironmentSpecificData(System.getProperty("environment"), "EMM_MSP_ID"), "", "", CompaniesVariables.COMPANY_STANDARDTRIAL),
					"Company Creation failed");
			tenantID = getValueFromToken("tenant");
			apiurl = CompaniesTest.cataLystURL + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID
					+ CommonVariables.DETAILSSEARCHSERVICEAPI2;
			tenantIdCompany = getTenantDetails(apiurl, CommonVariables.SEARCH_SERVICE_IDMTENANTS, companyName);
			logout();

			login("MSP_EMM_TOOL_TEAM_USERNAME", "MSP_EMM_TOOL_TEAM_PASSWORD");
			deviceListPage.gotoEMMToolBtn();
			softAssert.assertTrue(
					emmToolPage.getTextOfEMMToolPage("emmToolBtnClick")
							.equals(emmToolPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.emmTools ")),
					"EMM TOOL tab doesn't match");
			LOGGER.info("EMM TOOL button matched");
			softAssert.assertTrue(
					emmToolPage.getTextOfEMMToolPage("chromeEnterpriseBtn")
							.equals(emmToolPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.chrome")),
					"Chrome Enterprise tab doesn't match");
			LOGGER.info("Chrome Enterprise button matched");
			softAssert.assertTrue(emmToolPage.matchTextOfEMMToolPage("intuneBtn", PreferenceVariables.INTUNE),
					"Intune tab doesn't match");
			LOGGER.info("Intune button matched");
			softAssert.assertTrue(emmToolPage.matchTextOfEMMToolPage("airwatchBtn", PreferenceVariables.AIRWATCH),
					"Airwatch tab doesn't match");
			LOGGER.info("Airwatch button matched");

			// verify Google Chrome Enterprise not configured error message
			Assert.assertTrue(
					emmToolPage.EMMToolNonConfigurationMessage(LanguageCode, companyName, PreferenceVariables.CHROME),
					"Unable to verify Chrome enterprise integration error messages ");
			LOGGER.info("Successfully verified Google Chrome Enterprise Tool button not configured error messages");

			// verify Intune not configured error message
			Assert.assertTrue(
					emmToolPage.EMMToolNonConfigurationMessage(LanguageCode, companyName, PreferenceVariables.INTUNE),
					"Unable to verify Intune error messages");
			LOGGER.info("Successfully verified Intune Tool button not configured error messages");

			// verify Airwatch not configured error message
			Assert.assertTrue(
					emmToolPage.EMMToolNonConfigurationMessage(LanguageCode, companyName, PreferenceVariables.AIRWATCH),
					"Unable to verify Airwatch error messages");
			LOGGER.info("Successfully verified Airwatch Tool button not configured error messages");

			/*
			 * // Configuring the ChromeBook gotoCompaniesTab(); waitForPageLoaded();
			 * impersonateCompanyByCompanyName(companyName); goToPreferencesTab();
			 * 
			 * // Test Case : 1 LOGGER.info("Configuring Chrome enterprise integration");
			 * preferencesPage.waitForElementsOfPreferencesPage(
			 * "chromebookIntegrationEditButton");
			 * preferencesPage.clickByJavaScriptOnPreferencesPage(
			 * "chromebookIntegrationEditButton");
			 * softAssert.assertTrue(preferencesPage.enterChromebookIntegrationDetails(
			 * PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME,
			 * PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD),
			 * "Error occured in configuring chromebook integration");
			 * softAssert.assertTrue(preferencesPage.verifyChromeConfigurationSuccessMessage
			 * (), "Error occured while saving chromebook configuration"); gotoDevicesTab();
			 * // verify chrome enterprise integration punch out link
			 * softAssert.assertTrue(emmToolPage.EMMToolTabPunchOutLink(LanguageCode,
			 * companyName, EMMToolVariables.CHROME_ENTERPRISE_TAB),
			 * "Unable to verify EMM Tool tab for Chrome"); LOGGER.
			 * info("Verified chrome enterprise integration punch out link redirection");
			 * pressKey(Keys.ESCAPE);
			 */

			// Configuring the Intune
			LOGGER.info("Configuring Intune");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(companyName);
			goToPreferencesTab();
			softAssert.assertTrue(preferencesPage.verifyEmmStringOnPreferencesPage(LanguageCode),
					"All Strings are not present for EMM on preferences page");
			LOGGER.info("All Strings are validated for EMM on preferences page");
			sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			preferencesPage.verifyElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.waitForElementsOfPreferencesPage("intuneRadioButton");
			preferencesPage.verifyElementsOfPreferencesPage("intuneRadioButton");
			softAssert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("intuneRadioButton"),
					"Intune Toggle is not present");
			LOGGER.info("Intune Toggle is present");
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but
							// this is the final solution which works as expected.)
			preferencesPage.clickOnElementsOfPreferencesPage("intuneRadioButton");
			softAssert.assertTrue(preferencesPage.verifyEmmStringOnIntunePage(LanguageCode),
					"All Strings are not present for Intune");
			LOGGER.info("All Strings are validated for Intune");
			Assert.assertTrue(
					preferencesPage.verifyIntuneConfiguration(PreferencesPage.intuneCredentials.get("DOMAIN_NAME"),
							PreferencesPage.intuneCredentials.get("INTUNE_ID"),
							PreferencesPage.intuneCredentials.get("INTUNE_PASSWORD")),
					"Intune Configuration is failed");
			LOGGER.info("Intune is configured successfully");

			// verify Intune punch out link
			gotoDevicesTab();
			softAssert.assertTrue(
					emmToolPage.EMMToolTabPunchOutLink(LanguageCode, companyName, PreferenceVariables.INTUNE),
					"Unable to verify EMM Tool tab for Intune");
			LOGGER.info("Verified Intune punch out link redirection");
			pressKey(Keys.ESCAPE);

			// Configuring the AirWatch
			LOGGER.info("Configuring Airwatch");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(companyName);
			goToPreferencesTab();
			sleeper(2000);
			preferencesPage.scrollOnPreferencesPage("airwatchEditbutton");
			Assert.assertTrue(preferencesPage.verifyEmmToggle("Airwatch"), "Failed to verify Emm Airwatch Toggle");
			Assert.assertTrue(preferencesPage.verifyAirwatchConfiguration("CertificateAuthentication", LanguageCode),
					"Airwatch Configuration Failed with Certificate Authentication");
			Assert.assertTrue(preferencesPage.verifyAirwatchConfigurationWithSucessMessage(),
					"Airwatch Configuration Failed with success message");

			// verify Airwatch punch out link
			gotoDevicesTab();
			softAssert.assertTrue(
					emmToolPage.EMMToolTabPunchOutLink(LanguageCode, companyName, PreferenceVariables.AIRWATCH),
					"Unable to verify EMM Tool tab for Airwatch");
			LOGGER.info("Verified Airwatch punch out link redirection");
			pressKey(Keys.ESCAPE);

		} catch (Exception e) {
			LOGGER.error("Exception occurred in Test verifyEMMSideMenuTab : " + e.getMessage());
		} finally {
			// Deleting a company
			logout();
			login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
			companiesPage.removeCompanyUsingAPI(tenantIdCompany);
		}
	}
	/**
	 * This test case verify log of enable/disable lock and erase setting tile in company preference page
	 * @throws Exception
	 */
	@Test(priority = 64, groups = {"REGRESSION"}, description = "NFR STORY 343963, TC 582682", enabled=false)
	public final void verifyEnableAndDisableFlipSetTenatConfiguration() throws Exception
	{
		login("ITA_DEVICE_EMAIL", "ITA_DEVICE_PASSWORD");
		PreferencesPage preferencePage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert =new SoftAssert();
		gotoSettingsTabOfReportAdmin();
		sleeper(2000);
		preferencePage.clickOnElementsOfPreferencesPage("preferencesTab");
		if (preferencePage.getTextOfPreferencesPage("tenantStatus").equals("Disabled")) 
		{
			softAssert.assertTrue(preferencePage.enableTenantConfiguration(),"Unable to enable tenant configuration");
			gotoSettingsTabOfReportAdmin();
			preferencePage.waitForElementsOfPreferencesPage("preferencesTab");
			preferencePage.clickOnElementsOfPreferencesPage("preferencesTab");
			softAssert.assertTrue(preferencePage.disableTenantConfiguration(),"Unable to disable tenant configuration");
		
		} 
		else {
			softAssert.assertTrue(preferencePage.disableTenantConfiguration(),"Unable to disable tenant configuration");
			gotoSettingsTabOfReportAdmin();
			preferencePage.waitForElementsOfPreferencesPage("preferenceTab");
			preferencePage.clickOnElementsOfPreferencesPage("preferenceTab");
			softAssert.assertTrue(preferencePage.enableTenantConfiguration(),"Unable to enable tenant configuration");
		}
		LOGGER.info("Verified set and enable/Disable tenant configuration and logs successfully.");
	}
	
	/**
	 * This test case verify add, remove and verify Domain Name 
	 * @throws Exception
	 */
	@Test(priority = 64, groups = {"REGRESSION"}, description ="TEST CASE 819511", enabled=false)
	public final void verifyDomainNameVerification() throws Exception
	{
		login("ITA_DOMAIN_NAME_EMAIL", "ITA_DOMAIN_NAME_PASSWORD");
		PreferencesPage preferencePage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert =new SoftAssert();
		gotoSettingsTabOfReportAdmin();
		goToPreferencesTab();
		preferencePage.clickOnElementsOfPreferencesPage("domainName");
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainName").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.domain_name")), "Domain name tile is not present");
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainPanelWarning").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.domain_name")), "Domain Warning text is not present ");
		
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainNameText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.domain_name")), "Domain name text is not present");
		preferencePage.clickOnElementsOfPreferencesPage("domainEditButton");
		sleeper(2000);
		//invalid domain
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainNanmetextPopuplabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.domain_name")), "Domain Name TextPop-up is not present");
		preferencePage.enterTextForPreferencesPage("domainNanmetextPopuptext", "hppipeline");
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainContenttext").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.domain.sub.message")), "");
		preferencePage.verifyElementIsClickableOfPreferencesPage("domainCancelButton");
		preferencePage.clickOnElementsOfPreferencesPage("domainSaveButton");	
		
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("errorMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.errors.domain_name")),"Error message is not present");
		//Valid domain 
		preferencePage.clearTextOfPreferencePage("domainNanmetextPopuptext");
		preferencePage.enterTextForPreferencesPage("domainNanmetextPopuptext", "hppipeline.com");
		preferencePage.clickOnElementsOfPreferencesPage("domainSaveButton");
		
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("verifyDomainOwnershipTextButton").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.domain.verify")), "verify domain ownership text is not present");
		sleeper(2000);
		preferencePage.clickOnElementsOfPreferencesPage("verifyDomainOwnershipButton");
		
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("verifyDomainOwnershipPopupTextButton").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.domain.verify")), "verify domain ownership text is not present");
		ArrayList<String> domainProceduresUI= preferencePage.getTextOfListOfPreferencesPage("domainProcedure");
		String procedureTextMaestro= getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.domain.verify.procedure").toLowerCase().replaceAll("\"", "");
			boolean missingProcedure=false;
		for (String domainProcedure : domainProceduresUI) {
			if (!procedureTextMaestro.contains(domainProcedure)) {
				missingProcedure=true;
			}
		}
		softAssert.assertFalse(missingProcedure, "Domain Procedure is not matching");
		
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainVerifyNote").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.domain.verify.note")), "Note is not present");
		preferencePage.clickOnElementsOfPreferencesPage("verifyNowButton");
		
		//domain get verified tag
		preferencePage.verifyElementIsPresentOnPreferencesPage("domainverifytext");
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainverifytext").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.domain.verified")), "Domain verificationis not done");
		sleeper(2000);
		//Add Multiple domain
		preferencePage.waitForElementsOfPreferencesPage("domainEditButton");
		preferencePage.clickOnElementsOfPreferencesPage("domainEditButton");
		preferencePage.clearTextOfPreferencePage("domainNanmetextPopuptext");
		preferencePage.enterTextForPreferencesPage("domainNanmetextPopuptext", "hppipeline.com; hp.com; yopmail.com");
		preferencePage.clickOnElementsOfPreferencesPage("domainSaveButton");
		// Delete domain
		sleeper(2000);
		preferencePage.waitForElementsOfPreferencesPage("domainEditButton");
		preferencePage.clickOnElementsOfPreferencesPage("domainEditButton");
		preferencePage.clearTextOfPreferencePage("domainNanmetextPopuptext");
		preferencePage.clickOnElementsOfPreferencesPage("domainSaveButton");
		
		softAssert.assertAll();
		LOGGER.info("Verified Domain Name successfully");
	}
	@Test(priority = 66, groups = {"REGRESSION"}, description ="TEST CASE 819511",enabled = false)
	public final void verifynewDomainVerificationTile() throws Exception
	{
		login("FLIP_REPORT_ADMIN_EMAIL", "FLIP_REPORT_ADMIN_PASSWORD");
		PreferencesPage preferencePage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoSettingsTabOfReportAdmin();
		waitForPageLoaded();
		goToPreferencesTab();
		waitForPageLoaded();
		preferencePage.clickOnElementsOfPreferencesPage("domainName");
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainName").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","companies.list.domain_name")), " Domain name is not present");
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainPanelWarning").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui","companies.details.section_description.new_domain_name")),"Warning text is not present");
		//Validate New UI
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainInstruction").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui", "companies.details.section.domain.instructions")),"verify instruction text not present");
		preferencePage.waitForElementsOfPreferencesPage("accordion");
		preferencePage.clickOnElementsOfPreferencesPage("accordion");
		LOGGER.info("Accordion is expanded");
		//Verify Option 1 text		
		String[] Option1 = getTextLanguage(LanguageCode, "daas_ui","companies.details.section.domain.verify.email_procedure").split("\"");
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("option1").equalsIgnoreCase(Option1[1]),"Option 1 text is not present");
		//Verify Option 2 Text	
		ArrayList<String> domainProceduresUI= preferencePage.getTextOfListOfPreferencesPage("Option2");
		String procedureTextMaestro= getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.domain.verify.procedure").toLowerCase().replaceAll("\"", "");
		boolean missingProcedure=false;
		for (String domainProcedure : domainProceduresUI) 
			{
				if (!procedureTextMaestro.contains(domainProcedure))
					{
							missingProcedure=true;
									}
								}
		softAssert.assertFalse(missingProcedure, "Domain Procedure is not matching");				
		preferencePage.clickOnElementsOfPreferencesPage("accordion");
		sleeper(3000);
		//Invalid domain 
		preferencePage.waitForElementsOfPreferencesPage("domainEditButton");
		preferencePage.clickOnElementsOfPreferencesPage("domainEditButton");
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainNanmetextPopuplabel").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","companies.list.domain_name")), "Domain name is present");
		preferencePage.clearTextOfPreferencePage("domainNanmetextPopuptext");
		preferencePage.enterTextForPreferencesPage("domainNanmetextPopuptext","hppipeline");
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainContenttext").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","companies.details.domain.sub.message")),"Instruction is not present");
		preferencePage.clickOnElementsOfPreferencesPage("domainSaveButton");
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("errorMessage").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","companies.details.errors.domain_name")),"Error message is not displayed");
		preferencePage.clickOnElementsOfPreferencesPage("domainCancelButton");
		
		
		//Valid Domain
		preferencePage.waitForElementsOfPreferencesPage("domainEditButton");
		preferencePage.clickOnElementsOfPreferencesPage("domainEditButton");
		preferencePage.clearTextOfPreferencePage("domainNanmetextPopuptext");
		preferencePage.enterTextForPreferencesPage("domainNanmetextPopuptext","yopmail.com");
		preferencePage.clickOnElementsOfPreferencesPage("domainSaveButton");
		preferencePage.verifyElementIsinvisibile("domainSaveButton");
		preferencePage.waitForElementsOfPreferencesPage("verifyNow");
		sleeper(2000);
		preferencePage.clickOnElementsOfPreferencesPage("verifyNow");
		
		//Get Verified tag
		preferencePage.verifyElementIsinvisibile("verifyNow");
		preferencePage.verifyElementIsPresentOnPreferencesPage("domainverifytext");
		softAssert.assertTrue(preferencePage.getTextOfPreferencesPage("domainverifytext").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui","companies.details.section.domain.verified")), "Verified tag is not visible");
		
		//Add Multiple Domain
		preferencePage.waitForElementsOfPreferencesPage("domainEditButton");
		preferencePage.clickOnElementsOfPreferencesPage("domainEditButton");
		preferencePage.clearTextOfPreferencePage("domainNanmetextPopuptext");
		preferencePage.enterTextForPreferencesPage("domainNanmetextPopuptext", "hppipeline.com; hp.com; yopmail.com");
		preferencePage.clickOnElementsOfPreferencesPage("domainSaveButton");
		
		
		softAssert.assertAll();
		LOGGER.info("Verified Domain Name successfully");		
		
	}
	

	/**
	 * Test to verify Microsoft Teams Integration Toggle
	 * Test Case 911526: [CaaS][MTR] Verify user can enable the toggle for MTR integration
	 * 
	 * @throws Exception
	 */
	@Test(priority = 67, groups = { "CAAS_REGRESSION" }, description = "911526, 1011172",enabled=false)
	public final void verifyMSTeamsToggle() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver());
		preferencesPage = preferencesPage.getInstance();
		gotoSettingsTabOfSnowAdmin();
		goToPreferencesTab();
		preferencesPage.verifyMSTeamsToggleIntegartion();
		Assert.assertTrue(PreferenceVariables.MICROSOFT_TEAMS_TITLE.equalsIgnoreCase(preferencesPage.getTextOfPreferencesPage("microsoftTeamsToggleTitle")), " Microsoft Teams Integration title doesn't match");
		LOGGER.info("Microsoft Teams Integration title is matched");
		Assert.assertTrue(PreferenceVariables.MICROSOFT_TEAMS_TITLE_STATUS.equalsIgnoreCase(preferencesPage.getTextOfPreferencesPage("microsoftTeamsToggleStatus")), " Microsoft Teams Integration Status is not Enabled");
		LOGGER.info("Microsoft Teams Integration Status is : "+preferencesPage.getTextOfPreferencesPage("microsoftTeamsToggleStatus"));
		preferencesPage.clickOnElementsOfPreferencesPage("microsoftTeamsToggleBtn");
		switchToDifferentTab();
		preferencesPage.waitForElementsOfPreferencesPage("microsoftLogo");
		Assert.assertTrue(getUrlOfCurrentPage().contains("https://login.microsoftonline.com/"),"Current URL does not contain Microsft URL");
		switchBackToParentWithoutCloseTab();
	}
	
	

	/**
	 * Test to verify Zoom Integration Toggle
	 * Test Case 1029803: [CaaS][Subscription][Zoom] >> Verify User can Enable the Zoom Integration from UI
	 * 
	 * @throws Exception
	 */
	@Test(priority = 68, groups = { "CAAS_REGRESSION" }, description = "1029803, 1011230, 1029805")
	public final void verifyZoomToggle() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver());
		preferencesPage = preferencesPage.getInstance();
		gotoSettingsTabOfSnowAdmin();
		goToPreferencesTab();
		preferencesPage.verifyZoomToggleIntegartion();
		Assert.assertTrue(PreferenceVariables.ZOOM_INTEGRATION_TITLE.equalsIgnoreCase(preferencesPage.getTextOfPreferencesPage("zoomIntegrationToggleTitle")), " Zoom Integration title doesn't match");
		LOGGER.info("Zoom Integration title is matched");
		Assert.assertTrue(PreferenceVariables.ZOOM_INTEGRATION_TITLE_STATUS.equalsIgnoreCase(preferencesPage.getTextOfPreferencesPage("zoomIntegrationToggleStatus")), " Zoom Integration Status is not Enabled");
		LOGGER.info("Zoom Integration Status is : "+preferencesPage.getTextOfPreferencesPage("zoomIntegrationToggleStatus"));
		preferencesPage.clickOnElementsOfPreferencesPage("zoomIntegrationToggleBtn");
		switchToDifferentTab();
		preferencesPage.waitForElementsOfPreferencesPage("zoomLogo");
		Assert.assertTrue(getUrlOfCurrentPage().contains("https://zoom.us/signin"),"Current URL does not contain Zoom URL");
		switchBackToParentWithoutCloseTab();
	}
	
}