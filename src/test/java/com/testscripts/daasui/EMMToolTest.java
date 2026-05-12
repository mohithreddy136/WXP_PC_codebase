package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.PreferenceVariables;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DeviceListPage;
import com.daasui.pages.EMMToolPage;
import com.daasui.pages.PreferencesPage;

public class EMMToolTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(EMMToolTest.class);
	
	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[1][2];
		data[0][0] = "MSP_EMM_TOOL_TEAM_USERNAME";
		data[0][1] = "MSP_EMM_TOOL_TEAM_PASSWORD";
		//data[1][0] = "PARTNER_EMM_TEAM_USERNAME";
		//data[1][1] = "PARTNER_EMM_TEAM_PASSWORD";
		return data;
	}

	/**
	 * verify EMM Tool configured for Chrome Enterprise,Airwatch and Intune
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "ALL", "ALL_CI" }, dataProvider = "getLoginData", description = "Testcase 268162 ,268406,268166,268408,268182,269562,268179 268397,268396,268390 ; Roles ~ MSP")
	public final void verifyEMMSideMenuTab(String username, String password) throws Exception {
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
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(companyName, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "EMM_MSP_ID"), "", "", CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			tenantID = getValueFromToken("tenant");
			apiurl = CompaniesTest.cataLystURL + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2;
			tenantIdCompany = getTenantDetails(apiurl, CommonVariables.SEARCH_SERVICE_IDMTENANTS, companyName);
			logout();

			login(username, password);
			deviceListPage.gotoEMMToolBtn();
			softAssert.assertTrue(emmToolPage.getTextOfEMMToolPage("emmToolBtnClick").equals(emmToolPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.emmTools ")), "EMM TOOL tab doesn't match");
			LOGGER.info("EMM TOOL button matched");
			softAssert.assertTrue(emmToolPage.getTextOfEMMToolPage("chromeEnterpriseBtn").equals(emmToolPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.chrome")), "Chrome Enterprise tab doesn't match");
			LOGGER.info("Chrome Enterprise button matched");
			softAssert.assertTrue(emmToolPage.matchTextOfEMMToolPage("intuneBtn", PreferenceVariables.INTUNE), "Intune tab doesn't match");
			LOGGER.info("Intune button matched");
			softAssert.assertTrue(emmToolPage.matchTextOfEMMToolPage("airwatchBtn", PreferenceVariables.AIRWATCH), "Airwatch tab doesn't match");
			LOGGER.info("Airwatch button matched");

			// verify Google Chrome Enterprise not configured error message
			Assert.assertTrue(emmToolPage.EMMToolNonConfigurationMessage(LanguageCode, companyName, PreferenceVariables.CHROME), "Unable to verify Chrome enterprise integration error messages ");
			LOGGER.info("Successfully verified Google Chrome Enterprise Tool button not configured error messages");

			// verify Intune not configured error message
			Assert.assertTrue(emmToolPage.EMMToolNonConfigurationMessage(LanguageCode, companyName, PreferenceVariables.INTUNE), "Unable to verify Intune error messages");
			LOGGER.info("Successfully verified Intune Tool button not configured error messages");

			// verify Airwatch not configured error message
			Assert.assertTrue(emmToolPage.EMMToolNonConfigurationMessage(LanguageCode, companyName, PreferenceVariables.AIRWATCH), "Unable to verify Airwatch error messages");
			LOGGER.info("Successfully verified Airwatch Tool button not configured error messages");

			/* // Configuring the ChromeBook gotoCompaniesTab(); waitForPageLoaded(); impersonateCompanyByCompanyName(companyName); goToPreferencesTab();
			 * 
			 * // Test Case : 1 LOGGER.info("Configuring Chrome enterprise integration"); preferencesPage.waitForElementsOfPreferencesPage("chromebookIntegrationEditButton");
			 * preferencesPage.clickByJavaScriptOnPreferencesPage("chromebookIntegrationEditButton");
			 * softAssert.assertTrue(preferencesPage.enterChromebookIntegrationDetails(PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME,
			 * PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD), "Error occured in configuring chromebook integration");
			 * softAssert.assertTrue(preferencesPage.verifyChromeConfigurationSuccessMessage(), "Error occured while saving chromebook configuration"); gotoDevicesTab(); // verify
			 * chrome enterprise integration punch out link softAssert.assertTrue(emmToolPage.EMMToolTabPunchOutLink(LanguageCode, companyName,
			 * EMMToolVariables.CHROME_ENTERPRISE_TAB), "Unable to verify EMM Tool tab for Chrome");
			 * LOGGER.info("Verified chrome enterprise integration punch out link redirection"); pressKey(Keys.ESCAPE); */

			// Configuring the Intune
			LOGGER.info("Configuring Intune");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(companyName);
			goToPreferencesTab();
			softAssert.assertTrue(preferencesPage.verifyEmmStringOnPreferencesPage(LanguageCode), "All Strings are not present for EMM on preferences page");
			LOGGER.info("All Strings are validated for EMM on preferences page");
			sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			preferencesPage.verifyElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.waitForElementsOfPreferencesPage("intuneRadioButton");
			preferencesPage.verifyElementsOfPreferencesPage("intuneRadioButton");
			softAssert.assertTrue(preferencesPage.verifyElementsOfPreferencesPage("intuneRadioButton"), "Intune Toggle is not present");
			LOGGER.info("Intune Toggle is present");
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			preferencesPage.clickOnElementsOfPreferencesPage("intuneRadioButton");
			softAssert.assertTrue(preferencesPage.verifyEmmStringOnIntunePage(LanguageCode), "All Strings are not present for Intune");
			LOGGER.info("All Strings are validated for Intune");
			Assert.assertTrue(preferencesPage.verifyIntuneConfiguration(PreferencesPage.intuneCredentials.get("DOMAIN_NAME"), PreferencesPage.intuneCredentials.get("INTUNE_ID"), PreferencesPage.intuneCredentials.get("INTUNE_PASSWORD")), "Intune Configuration is failed");
			LOGGER.info("Intune is configured successfully");

			// verify Intune punch out link
			gotoDevicesTab();
			softAssert.assertTrue(emmToolPage.EMMToolTabPunchOutLink(LanguageCode, companyName, PreferenceVariables.INTUNE), "Unable to verify EMM Tool tab for Intune");
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
			Assert.assertTrue(preferencesPage.verifyAirwatchConfiguration("CertificateAuthentication", LanguageCode), "Airwatch Configuration Failed with Certificate Authentication");
			Assert.assertTrue(preferencesPage.verifyAirwatchConfigurationWithSucessMessage(), "Airwatch Configuration Failed with success message");

			// verify Airwatch punch out link
			gotoDevicesTab();
			softAssert.assertTrue(emmToolPage.EMMToolTabPunchOutLink(LanguageCode, companyName, PreferenceVariables.AIRWATCH), "Unable to verify EMM Tool tab for Airwatch");
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
}
