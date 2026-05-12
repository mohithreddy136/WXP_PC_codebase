package com.testscripts.daasui;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.LaunchDarkly;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.MEMVariables;
import com.daasui.pages.CompaniesDetailsPage;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.MEMDetailsPage;

public class MEMDetailsTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(MEMDetailsTest.class);	
	public static String azureEmail = getEnvironmentSpecificData(System.getProperty("environment"), "AZURE_EMAIL_ID");
	public static String azurePassword = getEnvironmentSpecificData(System.getProperty("environment"), "AZURE_PASSWORD");
	public static String companyusername = getEnvironmentSpecificData(System.getProperty("environment"), "USER_NAME");
	public static String envURL = getEnvironmentSpecificData(System.getProperty("environment"), "MEM_URL");
	public static String memCountry = getEnvironmentSpecificData(System.getProperty("environment"), "MEM_COUNTRY");
	public static String memCountryCrt = getEnvironmentSpecificData(System.getProperty("environment"), "MEM_COUNTRY_CRT");
	public static String memCity = getEnvironmentSpecificData(System.getProperty("environment"), "MEM_CITY");
	LaunchDarkly ldinstance = new LaunchDarkly();	
	/**This test verifies Microsoft Endpoint Manager flow Terms and Conditions page 
	 * if Azure user didn't accept the terms and conditions before for different conditions
	 * Test Case 1: User not Logged in with azure and Registered with HP TechPulse
	 * Test case 2: User already Logged in with azure already and Registered with HP TechPulse
	 * Test case 3: User already Logged in with azure and not Registered with HP TechPulse
	**/
	@Test(priority = 1 , groups = { "REGRESSION" } , description = "Test Case ID :795759, 849646, 887269")
	public final void verifyMEMWorkflow() throws Exception {
		login("ROOT_ADMIN_NEW_USER_US", "ROOT_ADMIN_NEW_PASSWORD_US");
		MEMDetailsPage memPage = new MEMDetailsPage(PreDefinedActions.getDriver()).getInstance();	
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		List<WebElement> widgetsList;
		List<WebElement> widgetCheckboxList;
		String tenantID;
		String currentUrl;
		
		// Test case 1		
		LOGGER.info("Test case 1: User not Logged in with azure and not Registered with HP Tech Pulse");
		
		//----------LOGIN WITH ROOT USER TO CHECK IF THE COMPANY EXISTS------------
		//----------IF NOT CREATE A COMPANY---------------------------------------	
		
		try {
			Assert.assertTrue(companyAvailableForRoot(azureEmail), "Company not present");	
			
			Assert.assertTrue(companiesPage.matchTextOfCompaniesPage("emailList", azureEmail), "Company didn't match");
			companiesPage.clickByJavaScriptOnCompaniesPage("companySearchListForRootClick");
			waitForPageLoaded();
			LOGGER.info("Redirected to details page");	
			currentUrl = companyDetailsPage.getUrlOfCurrentPage();
			tenantID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
			LOGGER.info("Captured tenantID");
			
			// Remove created company
			Assert.assertTrue(removeCompanyUsingAPI(tenantID), "Company removal failed");	
			logout();
		}catch(Throwable error) {
			LOGGER.info("Company didn't exist");
		}
		
		//Navigating to Azure Registration Page
		getUrl(getEnvironment(System.getProperty("environment"))+"azure");	
		String parentWinHandleAfter = getDriver().getWindowHandle();
		LOGGER.info("Azure Registration Page opened successfully");	
		memPage.waitForPageLoaded();
		
		if (ldinstance.enabled("azureonboard-marketingpage", null, null, false) == true) {
			softAssert.assertTrue(memPage.getTextOfMEMPage("bannerTile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "azure.onboard.BannerHeading")), "Banner Heading is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("signUpCard", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.signupcard.heading")), "Sign Up Banner Heading is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("tryitNowButton", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.signupcard.tryitnow")), "Try it Now button is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("learnmoreTitle", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.learnmoresection.heading")), "Learn more title text is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("learmoreButton", getTextLanguage(LanguageCode, "daas_ui", "azure.onboard.button.learn")), "Learn more button is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.getTextOfMEMPage("hardwareText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.hardwaresection.heading")), "Hardware section is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.getTextOfMEMPage("applicationInsightsText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.insightsection.heading")), "Application Insights section is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.getTextOfMEMPage("employeeInsightsText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.employeesection.heading")), "Employee Insights section is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("supportedPlatformsText", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.platforms.heading")), "Supported Platforms is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("windowsheadingText", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.platforms.windows.title")), "Windows heading text is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("macOSheadingText", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.platforms.macos.title")), "macOS heading text is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("chromeOSheadingText", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.platforms.chromeos.title")), "chrome OS heading text is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("mobileheadingText", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.platforms.mobile.title")), "mobile heading text is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("macOSText",getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.platforms.macos.content")), "macOS text is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("chromeOSText", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.platforms.chromeos.content")), "chrome OS text is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("privacyLinkNew", getTextLanguage(LanguageCode, "daas_ui", "help.info.legal.privacy.text")), "Privacy text is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("bannerTextNew1", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.signupcard.description")), "Sign up description is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("bannerTextNew2", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.learnmoresection.content")), "Learn more section content is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("bannerTextNew3", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.signupsection.description")), "Sign up section description content is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("signUpNowText", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.signupsection.heading")), "Sign up New! text is not matching on Azure marketing page");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("tryItNowbtn", getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.signupcard.tryitnow")), "Try it Now button is not matching on Azure marketing page");
		} else {
			// Validates the text on Azure Registration Screen
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("bannerSubHeading", getTextLanguage(LanguageCode, "daas_ui", "azure.onboard.BannerSubHeading")), "Text on Banner is incorrect");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("bannerHeading", getTextLanguage(LanguageCode, "daas_ui", "azure.onboard.BannerHeading")), "Heading on Banner is incorrect");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("bannerDescription", getTextLanguage(LanguageCode, "daas_ui", "azure.onboard.BannerDescription")), "Text on BannerDescription is incorrect");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("message", getTextLanguage(LanguageCode, "daas_ui", "azure.onboard.message")), "Text on message is incorrect");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("learnMoreText", getTextLanguage(LanguageCode, "daas_ui", "azure.onboard.button.learn")), "Text on message is incorrect");
			softAssert.assertTrue(memPage.verifyLinksoOfAzurePage("videolink"),"Video is not available");
		}
		LOGGER.info("Validated text on Azure Registration Page successfully");
		
		memPage.waitForElementsOfMEMPage("termslink");
		memPage.clickOnElementsOfMEMPage("termslink");
		memPage.switchToDifferentTab();
		
		// Validates the Terms URL on Azure Registration Screen
		String url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.TERMS_AND_CONDITIONS;
		softAssert.assertTrue(url.equals(getUrlOfCurrentPage()+"/"), "URL not matching");
		sleeper(500);
		memPage.scrollOnMEMPage("termsCondRussiaText");
		
		//Validate Russia Heading and Text in Terms and Conditions page
		softAssert.assertTrue(memPage.getTextOfMEMPage("termsCondRussiaText").equals(MEMVariables.TERMSANDCOND_RUSSIA_TEXT),"Terms and Conditions Russia Text not matched in Terms and Condtions page");
		softAssert.assertTrue(memPage.getTextOfMEMPage("russiaHeading").equals(MEMVariables.RUSSIA_HEADING),"Russia Heading not matched in Terms and Condtions page");
		softAssert.assertTrue(memPage.getTextOfMEMPage("russiaText").equals(MEMVariables.RUSSIA_TEXT),"Russia Text not matched in Terms and Condtions page");
		
		memPage.scrollOnMEMPage("russiaLinkText");
		memPage.clickOnElementsOfMEMPage("russiaLinkText");
		memPage.switchToDifferentTab();
		sleeper(10000); // Sleeper cannot be avoided as Page is taking 7 to 8 seconds to load
		softAssert.assertTrue(MEMVariables.RUSSIA_DATAPRIVACYLINK.equals(getUrlOfCurrentPage()), "RUSSIA URL not matching");
		memPage.switchBackToPreviousTab();
		LOGGER.info("Russian link verified and switched back to main URL");
		
		getDriver().switchTo().window(parentWinHandleAfter);
		memPage.waitForElementsOfMEMPage("privacyLink");
		memPage.clickOnElementsOfMEMPage("privacyLink");
		memPage.switchToDifferentTab();
		sleeper(10000); // Sleeper cannot be avoided as Page is taking 7 to 8 seconds to load
		
		// Validates the Privacy URL on Azure Registration Screen
		softAssert.assertTrue(getUrlOfCurrentPage().contains(getTextLanguage(LanguageCode, "daas_ui", "help.info.legal.privacy.text").toLowerCase()), "Page not redirected to Privacy page");
		memPage.switchBackToPreviousTab();
		
		if (ldinstance.enabled("azureonboard-marketingpage", null, null, false) == true) {
			memPage.clickByJavaScriptOnMEMPage("tryitNowButton");
		}else {
			memPage.clickByJavaScriptOnMEMPage("selectCheckBox");
			memPage.clickByJavaScriptOnMEMPage("tryItFree");		
		}
	
		LOGGER.info("Redirected to Azure login Page");		
		memPage.azureLogin("AZURE_EMAIL_ID", "AZURE_PASSWORD");	
		memPage.waitForPageLoaded();
		if(memPage.verifyElementsOfMEMPage("consentCheckBox")) {
			memPage.clickOnMEMPage("consentCheckBox");
		}
		memPage.clickByJavaScriptOnMEMPage("acceptButton");	
		memPage.waitForPageLoaded();
		if(memPage.verifyElementsOfMEMPage("companyNameLabel")) {
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("companyScreenDescription", getTextLanguage(LanguageCode, "daas_ui", "azure.onboard.companyinfo.description")), "Description is incorrect on Company Screen");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("companyNameLabel", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_name")), "Company Name Label is incorrect on Company Screen");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("companySizeLabel", getTextLanguage(LanguageCode, "daas_ui", "companies.list.no_of_employees")), "Company Size Label is incorrect on Company Screen");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("industryLabel", getTextLanguage(LanguageCode, "daas_ui", "companies.list.industry")), "Industry Label is incorrect on Company Screen");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("countryLabel", getTextLanguage(LanguageCode, "daas_ui", "global.country")), "Country Label is incorrect on Company Screen");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("companyCityLabel", getTextLanguage(LanguageCode, "daas_ui", "global.city")), "Country Label is incorrect on Company Screen");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("companyNextbtn", getTextLanguage(LanguageCode, "daas_ui", "global.next")), "Back button is incorrect on Company Screen");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("companyBackbtn", getTextLanguage(LanguageCode, "daas_ui", "global.back")), "Next button is incorrect on Company Screen");
			softAssert.assertTrue(memPage.getAttributesOfMEMPage("companyNameText","value").equalsIgnoreCase(MEMVariables.COMPANY_NAME), "Company Name didn't match");

			memPage.clickOnElementsOfMEMPage("companySizedropdown");
			memPage.selectFirstValueFromDropdownMEMPage("companySizelistvalues");
			memPage.clickOnElementsOfMEMPage("industrydropdown");
			memPage.selectFirstValueFromDropdownMEMPage("industrylistValues");
			memPage.enterTextForMEMPage("companyCity", memCity);
			memPage.clickOnElementsOfMEMPage("countrydropdown");
			memPage.selectSpecificValueFromDropdownOnMEMPage("countrylistValues", memCountryCrt , "countrydropdown");
			memPage.clickByJavaScriptOnMEMPage("companyNextbtn");
			softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("hangonMessage", getTextLanguage(LanguageCode, "daas_ui", "azure.onboard.company.create.placeholder")), "Hang on while we are setting up your account... is incorrect");
			memPage.waitForPageLoaded();	
			LOGGER.info("Widgets Page opened and labels are verified successfully ");	
			widgetsList = memPage.getElementsOfWidgetListPage("checkboxesOnWidget");		
			widgetCheckboxList = memPage.getElementsOfWidgetListPage("labelsOfCheckboxesinWidget");
			memPage.clickCheckboxInWidget(widgetsList, widgetCheckboxList);	
			memPage.clickOnMEMPage("nextButton");
		}
		memPage.waitForPageLoaded();
		LOGGER.info("Logged into HP Tech Pulse Successfully");
		gotoDevicesTab();
		memPage.waitForPageLoaded();		
		logout();		
		LOGGER.info("Test case 1: Logged out from HP Tech Pulse Successfully");		
		
		//Test case 2
		LOGGER.info("Test case 2: User Logged in with azure already and Registered with HP Tech Pulse");
		getUrl(getEnvironment(System.getProperty("environment"))+"azure");	
		memPage.waitForPageLoaded(); 
		LOGGER.info("Logged into HP Tech Pulse Successfully");
		memPage.waitForPageLoaded();
		gotoDevicesTab();
		memPage.waitForPageLoaded();
		logout();
		LOGGER.info("Test case 2: Logged out from HP Tech Pulse Successfully");		
		softAssert.assertAll();
		
		LOGGER.info("Validation of MEM flow completed successfully");		
	}
	
	/**This test verifies SignInWithMicrosoft button on WelcomePage**/
	@Test(priority = 2 , dependsOnMethods = "verifyMEMWorkflow" , groups = { "REGRESSION" } , description = "Test Case ID : 837038")
	public final void verifySigninwithMicrosoftbtn() throws Exception {
		loginAsMicrosoftUser("AZURE_EMAIL_ID", "AZURE_PASSWORD");		
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
					
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "AZURE_EMAIL_ID"))) {
			dashboardPage.waitForElementsOfDashboardPage("dashboardTitleOnBreadcrumbs");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard title text is incorrect");
			LOGGER.info("Dashboard title is matched");
		}else{
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitle").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard title text is incorrect");
			LOGGER.info("Dashboard title is matched");
		}
	}
	
	/**	This test verifies MEM portal redirection to appropriate data center if user exists
	**/
	@Test(priority = 3 , groups = { "REGRESSION" } , description = "Test Case ID :849629")
	public final void verifyMEMURLNavigation() throws Exception{
		MEMDetailsPage memPage = new MEMDetailsPage(PreDefinedActions.getDriver()).getInstance();	
		SoftAssert softAssert = new SoftAssert();
				
		getUrl(getEnvironment(System.getProperty("environment"))+"azure");	
		memPage.waitForPageLoaded();		
		if (ldinstance.enabled("azureonboard-marketingpage", null, null, false) == true) {
			memPage.clickByJavaScriptOnMEMPage("tryitNowButton");
		}else {
			memPage.clickByJavaScriptOnMEMPage("selectCheckBox");
			memPage.clickByJavaScriptOnMEMPage("tryItFree");		
		}
		
		LOGGER.info("Redirected to Azure login Page");	
		memPage.azureLogin("MEM_AZURE_EMAIL", "MEM_AZURE_PASSWORD");
		memPage.waitForPageLoaded();		
		if(memPage.verifyElementsOfMEMPage("consentCheckBox")) {
			memPage.clickOnMEMPage("consentCheckBox");
		}
		memPage.clickByJavaScriptOnMEMPage("acceptButton");	

		memPage.waitForPageLoaded();	
		sleeper(2000);
		LOGGER.info("Logged into HP Tech Pulse Successfully");
		gotoDevicesTab();
		// Validates the URL on HP Tech Pulse
		softAssert.assertTrue(envURL.equals(getUrlOfCurrentPage()), "URL not matching");
		memPage.waitForPageLoaded();
		softAssert.assertAll();
		LOGGER.info("MEM portal redirection verified successfully");
	}
	
	//There is a open bug related to the test case, so disabling the test case. Will be enabled once the bug is resolved
	/**	This test verifies Company page and Terms and Conditions page 
	**	if Microsoft Azure user didn't accept the terms and conditions before 
	**/
	@Test(priority = 4 , groups = { "REGRESSION" } , description = "Test Case ID : 843768, 849629" , enabled = false)
	public final void verifyCompnanypageForAzureUser() throws Exception{
		//----------LOGIN WITH ROOT USER TO DELETE THE COMPANY IF EXISTS------------	
		login("ROOT_ADMIN_NEW_USER_US", "ROOT_ADMIN_NEW_PASSWORD_US");
		MEMDetailsPage memPage = new MEMDetailsPage(PreDefinedActions.getDriver()).getInstance();	
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String currentUrl, tenantID;
		LOGGER.info("User Logged in with azure and not Registered with HP Tech Pulse");
			
		try {
			Assert.assertTrue(companyAvailableForRoot(azureEmail), "Company not present");	
			
			Assert.assertTrue(companiesPage.matchTextOfCompaniesPage("emailList", azureEmail), "Company didn't match");
			companiesPage.clickByJavaScriptOnCompaniesPage("companySearchListForRootClick");
			waitForPageLoaded();
			LOGGER.info("Redirected to details page");	
			currentUrl = companyDetailsPage.getUrlOfCurrentPage();
			tenantID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
			LOGGER.info("Captured tenantID");
			
			// Remove created company
			Assert.assertTrue(removeCompanyUsingAPI(tenantID), "Company removal failed");	
			logout();
		}catch(Throwable error) {
			LOGGER.info("Company didn't exist");
		}
				
		getUrl(getEnvironment(System.getProperty("environment"))+"azure");	
		memPage.waitForPageLoaded();		
				
		memPage.clickByJavaScriptOnMEMPage("selectCheckBox");
		memPage.clickByJavaScriptOnMEMPage("tryItFree");		
		LOGGER.info("Redirected to Azure login Page");		
		
		memPage.azureLogin("MEM_AZURE_EMAIL_ID", "MEM_AZURE_PASSWORD");		
		memPage.waitForPageLoaded();		
		if(memPage.verifyElementsOfMEMPage("consentCheckBox")) {
			memPage.clickOnMEMPage("consentCheckBox");
		}
		memPage.clickByJavaScriptOnMEMPage("acceptButton");	

		//Verify Labels on Company Registration page
		softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("companyTitle", getTextLanguage(LanguageCode, "daas_ui", "aws.onboard.companyinfo.title").replace("{userName}!", companyusername)),"Title  is incorrect on Company Screen");
		memPage.clickOnElementsOfMEMPage("companySizedropdown");
		//Verify Company size drop down list
		softAssert.assertTrue(memPage.verifyCompanySizedropdownOfMEMPage(LanguageCode), "Device Recommnedation was not updated.");
	
		//Enter the details on Company Screen
		memPage.selectFirstValueFromDropdownMEMPage("companySizelistvalues");
		memPage.clickOnElementsOfMEMPage("industrydropdown");
			
		//Verify Industry drop down list
		softAssert.assertTrue(memPage.verifydropdownOrderOfMEMPage("industrylistValues"), "Industry drop down list values are not correct");
		memPage.selectFirstValueFromDropdownMEMPage("industrylistValues");
		memPage.enterTextForMEMPage("companyCity", memCity);
		memPage.clickOnElementsOfMEMPage("countrydropdown");
		memPage.selectSpecificValueFromDropdownOnMEMPage("countrylistValues", memCountry , "countrydropdown");
		memPage.clickByJavaScriptOnMEMPage("companyNextbtn");
			
		softAssert.assertTrue(memPage.matchTextOnAzureDetailsPage("hangonMessage", getTextLanguage(LanguageCode, "daas_ui", "azure.onboard.company.create.placeholder")), "Hang on while we are setting up your account... is incorrect");
		memPage.waitForPageLoaded();
			
		// Validate the Sequence of check boxes on Widget Screen
		softAssert.assertTrue(memPage.verifyCheckboxesOrderOnWidgetPage("labelsOfCheckboxesinWidget"), "Checkboxes Sequence on Widget screen is not correct");
		LOGGER.info("Widgets Page opened and checkboxes validated successfully");		
					
		// Clicks the check boxes on Widget Screen
		List<WebElement> widgetsList = memPage.getElementsOfWidgetListPage("checkboxesOnWidget");		
		List<WebElement> widgetCheckboxList = memPage.getElementsOfWidgetListPage("labelsOfCheckboxesinWidget");
		memPage.clickCheckboxInWidget(widgetsList, widgetCheckboxList);		
		memPage.clickOnMEMPage("nextButton");
		memPage.waitForPageLoaded();	
		LOGGER.info("Logged into HP Tech Pulse Successfully");
		gotoDevicesTab();
		softAssert.assertTrue(envURL.equals(getUrlOfCurrentPage()), "URL not matching");
		memPage.waitForPageLoaded();		
		
		softAssert.assertAll();
		LOGGER.info("Company page and navigation to correct URL validated successfully");
	}
	
	/**This test verifies HPID ACCOUNT and MICROSOFT ACCOUNT buttons text on WelcomePage**/
	@Test(priority = 5 , groups = { "REGRESSION" } , description = "Test Case ID : 881473")
	public final void verifySignInbtnsOnHomePage() throws Exception {
		environment = System.getProperty("environment");
		acceptCookiesConsent();
		
		MEMDetailsPage memPage = new MEMDetailsPage(PreDefinedActions.getDriver()).getInstance();	
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertTrue(memPage.getTextOfMEMPage("signInButton").equals(MEMVariables.HPID_ACCOUNT),"HPID Account text is not correct");
		softAssert.assertTrue(memPage.getTextOfMEMPage("signInMicrosoftButton").equals(MEMVariables.MICROSOFT_ACCOUNT),"Microsoft Account text is not correct");
		softAssert.assertAll();
		LOGGER.info("HPID ACCOUNT and MICROSOFT ACCOUNT buttons text validated successfully");
	}
	
}