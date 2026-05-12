package com.testscripts.daasui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.basesource.action.MailinatorMail;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.LaunchDarkly;
import com.basesource.utils.Mailinator;
import com.basesource.utils.ObjectReader;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.UserVariables;
import com.daasui.pages.BenchmarkPage;
import com.daasui.pages.CampaignsListPage;
import com.daasui.pages.CompaniesDetailsPage;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.DeviceDetailsPage;
import com.daasui.pages.DeviceListPage;
import com.daasui.pages.EMMToolPage;
import com.daasui.pages.ExperienceMgmtPage;
import com.daasui.pages.FacebookLoginPage;
import com.daasui.pages.GmailLoginPage;
import com.daasui.pages.HPDexPage;
import com.daasui.pages.IncidentPage;
import com.daasui.pages.LicenseOrdersPage;
import com.daasui.pages.LicensesListPage;
import com.daasui.pages.LogPage;
import com.daasui.pages.LoginPage;
import com.daasui.pages.PaginationPage;
import com.daasui.pages.PartnerDetailsPage;
import com.daasui.pages.PartnerPage;
import com.daasui.pages.PreferencesPage;
import com.daasui.pages.ProductCatalogPage;
import com.daasui.pages.AnalyticsPage;
import com.daasui.pages.RoomsListPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.SupportTeamPage;
import com.daasui.pages.TableConfigurationPage;
import com.daasui.pages.UserDetailsPage;
import com.daasui.pages.UserPage;
import com.daasui.pages.WEPPartnerCustomersPage;
import com.daasui.pages.WEXDashboardPage;
import com.daasui.pages.WEXLogPage;
import com.daasui.pages.WelcomePage;
import com.daasui.pages.WhatsNewPage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.testscripts.daasui.SiteUpTest.buildNumber;

public class CommonTest extends SetTestEnvironments {

	private CommonTest instance;
	private Properties selecteCredentialsProperties;
	private ObjectReader commonTestPropertiesReader = new ObjectReader();
	private static Logger LOGGER = LogManager.getLogger(CommonTest.class);
	public static String environment;
	public static String switchservice;
	public String companyEmail, MSPEmail, MSPPassword;
	public static String[] countryNameArray = new String[] { "Russian Federation", "Afghanistan", "Albania" };
	public static String workflowDashboardUrl = getEnvironmentSpecificData(System.getProperty("environment"), "WORKFLOW_URLCHECK_DASHBOARD");
	public static String workflowSettingsUrl = getEnvironmentSpecificData(System.getProperty("environment"), "WORKFLOW_URLCHECK_SETTINGS");
	public static HashMap<String, String> mapTenantIdDetails = new HashMap<>();
	public int activepageNumber = 0;
	public int firstPageNumber = 0, lastPageNumber = 0, selectedOption = 0, totalCount = 0;
	public boolean previousButtonStatus = false, nextButtonStatus = false;
	public static String uiVersion = System.getProperty("uiVersion");

	public CommonTest getInstance() {
		if (instance == null) {
			synchronized (CommonTest.class) {
				if (instance == null) {
					instance = new CommonTest();

				}

			}
		}
		return instance;
	}

	public final String getCredentials(String credentials, String key) throws Exception {
		selecteCredentialsProperties = commonTestPropertiesReader.getCredentials(credentials);
		return selecteCredentialsProperties.getProperty(key);
	}

	/**
	 * Detects the actual UI language from the partner page and updates
	 * LanguageCode in both SetTestEnvironments and CommonTest directly.
	 */
	protected void detectAndUpdateUILanguageCode(WEPPartnerCustomersPage page) {
		String selectedText = page.getSelectedCustomerText();
		if (selectedText != null && !selectedText.trim().isEmpty()) {
			String detectedLocale = page.detectAndUpdateUILanguageCode(selectedText);
			if (detectedLocale != null) {
				LanguageCode = detectedLocale;
				LOGGER.info("Updated SetTestEnvironments/CommonTest LanguageCode to [" + detectedLocale + "]");
			}
		}
	}


	
	
	/** Login method wihtout Implicit Wait */
	/**
	 * @param email
	 * @param password
	 * @throws Exception
	 */
	public final void loginWithoutImplicitWait(String email, String password) throws Exception {
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAITPAGELOAD));
		environment = System.getProperty("environment");
		LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();

		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		LaunchDarkly ldinstance = new LaunchDarkly();
		//acceptCookiesConsent();
		if (System.getProperty("environment").equalsIgnoreCase("US-STAGE-WEP") ||
				System.getProperty("environment").equalsIgnoreCase("US-STABLE-WEP")) {
			sleeper(2000);
			Assert.assertTrue(welcomePage.verifyElementsOfWelcomePage("signInButton"), "Welcome page did not open successfully");
			welcomePage.clickOnElementsOfWelcomePage("signInButton");
		}
		else if (System.getProperty("environment").equalsIgnoreCase("EU-STAGE-WEP") ||
				System.getProperty("environment").equalsIgnoreCase("EU-STABLE-WEP") ||
		System.getProperty("environment").equalsIgnoreCase("EU-PROD-WEP") || 
		System.getProperty("environment").equalsIgnoreCase("US-PROD-WEP")){
			sleeper(4000); //method does not use implicit wait so adding sleeper to avoid failure
			Assert.assertTrue(loginPage.verifyElementsOfLoginPage("emailInputField"), "Login page did not open successfully");
		}

		if(!isFeatureToggleEnabled(CommonVariables.WX_NEW_LOGIN_PAGE_TOGGLE)) {
			// If the toggle is not enabled, then use the old sign-in page flow
			loginPage.waitForElementsOfLoginPage("emailInputField");
			Assert.assertTrue(loginPage.verifyElementsOfLoginPage("emailInputField"), "Login page did not open successfully");
			loginPage.enterTextForLoginPage("emailInputField", getCredentials(environment, email));
			loginPage.clickOnElementsOfLoginPage("nextbuttonNewUI");
		} else {
			// If the toggle is enabled, then use the new sign-in page flow
			loginPage.waitForElementsOfLoginPage("emailInputField");
			Assert.assertTrue(loginPage.verifyElementsOfLoginPage("emailInputField"), "Login page did not open successfully");
			loginPage.enterTextForLoginPage("emailInputField", getCredentials(environment, email));
			loginPage.clickOnElementsOfLoginPage("Newsigninbutton");
		}

		Assert.assertTrue(loginPage.waitForElementsOfLoginPage("passwordInputField"), "Password page did not open successfully");
		loginPage.enterTextForLoginPage("passwordInputField", getCredentials(environment, password));
		loginPage.clickOnElementsOfLoginPage("submitButtonNewUI");
		sleeper(2000);

		// Screen right after clicking on submit button.
		    Assert.assertTrue(loginPage.waitForElementsOfLoginPage("pleaseWaitTextPage"), "FAIL:Please wait text screen did not appear after clickin Submit button.");
					LOGGER.info("Logged into HP Workforce Experience PORTAL");
	}
	

	/** Login method */
	/**
	 * @param email
	 * @param password
	 * @throws Exception
	 */
	public final void login(String email, String password) throws Exception {
		environment = System.getProperty("environment");
		LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		acceptCookiesConsent();

		if (System.getProperty("environment").equalsIgnoreCase("US-STAGE-WEP") ||
				System.getProperty("environment").equalsIgnoreCase("US-STABLE-WEP")) {
			Assert.assertTrue(welcomePage.verifyElementsOfWelcomePage("signInButton"), "Welcome page did not open successfully");
			welcomePage.clickOnElementsOfWelcomePage("signInButton");
		}
		else if (System.getProperty("environment").equalsIgnoreCase("EU-STAGE-WEP") ||
				System.getProperty("environment").equalsIgnoreCase("EU-STABLE-WEP") ||
		System.getProperty("environment").equalsIgnoreCase("EU-PROD-WEP") || 
		System.getProperty("environment").equalsIgnoreCase("US-PROD-WEP") ||
		System.getProperty("environment").equalsIgnoreCase("US-LDKProd-WEP") ||
        System.getProperty("environment").equalsIgnoreCase("US-VENEER-WEP")){
            if (welcomePage.verifyElementsOfWelcomePage("signInButton")) {
                welcomePage.clickOnElementsOfWelcomePage("signInButton");
            }
			Assert.assertTrue(loginPage.verifyElementsOfLoginPage("emailInputField"), "Login page did not open successfully");
		}

		if(!isFeatureToggleEnabled(CommonVariables.WX_NEW_LOGIN_PAGE_TOGGLE)) {
			// If the toggle is not enabled, then use the old sign-in page flow
			loginPage.waitForElementsOfLoginPage("emailInputField");
			Assert.assertTrue(loginPage.verifyElementsOfLoginPage("emailInputField"), "Login page did not open successfully");
			loginPage.enterTextForLoginPage("emailInputField", getCredentials(environment, email));
			loginPage.clickOnElementsOfLoginPage("nextbuttonNewUI");
		} else {
			// If the toggle is enabled, then use the new sign-in page flow
			loginPage.waitForElementsOfLoginPage("emailInputField");
			Assert.assertTrue(loginPage.verifyElementsOfLoginPage("emailInputField"), "Login page did not open successfully");
			loginPage.enterTextForLoginPage("emailInputField", getCredentials(environment, email));
			loginPage.clickOnElementsOfLoginPage("Newsigninbutton");
		}
		if(System.getProperty("environment").contains("STABLE") || System.getProperty("environment").contains("STAGE")||System.getProperty("environment").contains("Perf")||System.getProperty("environment").contains("VENEER")){
			sleeper(5000);
			loginPage.clickOnElementsOfLoginPage("usePasswordButton");
		}
		sleeper(5000);
		Assert.assertTrue(loginPage.waitForElementsOfLoginPage("passwordInputField"), "Password page did not open successfully");
		sleeper(3000);
		loginPage.enterTextForLoginPage("passwordInputField", getCredentials(environment, password));
		loginPage.clickOnElementsOfLoginPage("submitButtonNewUI");
		sleeper(3000);
        waitForPageLoaded();
		// Wait for the page title dynamically
		Assert.assertTrue(loginPage.waitForElementsOfLoginPageDynamic("pageTitle", CommonVariables.LOGIN_WAIT), "Page title did not load in time");

		waitForPageLoaded();
		LOGGER.info(CommonVariables.PRODUCT_NAME + " login successful");
	}
	

	/**
	 * This method is to switch to required service after login when the logged in user is an Admin-Central migrated.
	 * @throws Exception
	 */
	public final void switchToService() throws Exception {
		boolean flag = false;
		//getting the service name from the POM file
		switchservice = System.getProperty("service");
		String service = getservice(switchservice);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		sleeper(2000);
		if(!dashboardPage.getTextOfDashboardPage("pageheader").equals(service)) {
		if(dashboardPage.verifyElementsOfDashboardPage("pin_Icon")) {
			dashboardPage.clickOnElementsOfDashboardPage("pin_Icon");
			LOGGER.info("App Switcher is unpinned");
		}	
		//To check if already after login the user is on home page or not
		if(dashboardPage.verifyElementsOfDashboardPage("homeTabSelected")) {
			flag = true;
		}
		dashboardPage.clickOnElementsOfDashboardPage("AppSwitcher");
		
		//Condition to check that click on My services only if service value is other than Workforce Experience Platform
		if(!service.equalsIgnoreCase(CommonVariables.WORKFORCE_CENTRAL_SERVICE)) {
			sleeper(2000);
			dashboardPage.clickOnElementsOfDashboardPage("Myservicestab");
			switch (service.toUpperCase()) {

				case "PROACTIVE INSIGHTS":
					Assert.assertTrue(dashboardPage.matchTextOfDashboardPage("proactiveInsights", service), service + " Service is not Present In AppSwitcher");
					dashboardPage.clickOnElementsOfDashboardPage("proactiveInsights");
					break;

				case "WOLF PROTECT & TRACE":
					Assert.assertTrue(dashboardPage.matchTextOfDashboardPage("wolfProtectAndTrace", service), service + " Service is not Present In AppSwitcher");
					dashboardPage.clickOnElementsOfDashboardPage("wolfProtectAndTrace");
					break;

				case "ACTIVE CARE":
					Assert.assertTrue(dashboardPage.matchTextOfDashboardPage("activeCare", service), service + " Service is not Present In AppSwitcher");
					dashboardPage.clickOnElementsOfDashboardPage("activeCare");
					break;
					
				case "CONNECT":
					Assert.assertTrue(dashboardPage.matchTextOfDashboardPage("connect", service), service + " Service is not Present In AppSwitcher");
					dashboardPage.clickOnElementsOfDashboardPage("activeCare");
					break;

				default:
					LOGGER.error("Given : " + service + " service is incorrect");
			}
			Assert.assertTrue(dashboardPage.matchTextOfDashboardPage("applicationLabelPostSwitch", service), "Service Present In AppSwitcher but not Present In My Services");
			sleeper(2000);
			LOGGER.info("Switched to required service :"+service);
		}else{
			if(flag == true){
				dashboardPage.clickByJavaScriptOnDashboardPage("menuCloseIconAppSwitcher");
				LOGGER.info("Navigated to Dashboard page");
			}else {
				dashboardPage.clickWebelement(dashboardPage.getElementOfDashboardPage("hpWorkforceCentral"));
				dashboardPage.waitForElementsOfDashboardPage("hometext");
				Assert.assertTrue(dashboardPage.matchTextOfDashboardPage("hometext", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.home")), "Home tab is not clicked");
				LOGGER.info("Navigated to Home Tab");
			}
		}
		}else {
		LOGGER.info("Already navigated to required service :"+service);
		}
	}
	
	/** This is common method to submit feedback of Veneer 3.
	 * @return
	 * @throws Exception
	 */
	public boolean submitFeedbackVeneer3() throws Exception{
		boolean flag = false;
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("feedbackRating");
		dashboardPage.clickOnElementsOfDashboardPage("submitButtonFeedback");
		if(dashboardPage.waitForElementsOfDashboardPage("switchUIButtonV2")){
			flag = true;
		}else{
			flag = false;
			LOGGER.error("Old UI of techpulse did not load successfully.");
		}
		return flag;
		
	}
	
	/** This method is used to divert execution on required UI version.
	 * @throws Exception
	 */
	public void verifyUIVersion() throws Exception{
		try {
			DashboardPage dashboardPage=new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();
			if(uiVersion.equalsIgnoreCase("VENEER3")){
				if(isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)){
					if(getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM).contains("\"target\":\"ui_v2\"")){
						waitForPageLoaded();
						dashboardPage.waitForElementsOfDashboardPage("switchUIButtonV2");
						dashboardPage.clickOnElementsOfDashboardPage("switchUIButtonV2");
						loginPage.waitForElementsOfLoginPageDynamic("pageTitle",CommonVariables.LOGIN_WAIT);
						Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("switchUIButtonV3"), "New UI of techpulse did not load successfully.");
						LOGGER.info("Navigated to Veneer 3 version successfully.");
					}else if(getItemFromLocalStorage("ui_user_preferences").contains("\"target\":\"ui_v3\"")){
						if(dashboardPage.verifyElementsOfDashboardPage("closeAddCompanyModal"))
							dashboardPage.clickOnElementsOfDashboardPage("closeAddCompanyModal");
						if(dashboardPage.waitForElementsOfDashboardPageDynamic("feedBackModalTitle",2)){
							submitFeedbackVeneer3();
						}
						LOGGER.info("Already in Veneer 3 UI,execution can be continued.");
					}
				}else{
					LOGGER.error("Toggle for Veneer 3 is not enabled.");
				}
			}else if(uiVersion.equalsIgnoreCase("VENEER2")) {
				if(isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)){
					if(getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM).contains("\"target\":\"ui_v3\"")){
						if(dashboardPage.waitForElementsOfDashboardPageDynamic("feedBackModalTitle",2)){
							dashboardPage.clickOnElementsOfDashboardPage("feedbackRating");
							dashboardPage.clickOnElementsOfDashboardPage("submitButtonFeedback");
							sleeper(3000);
						}
						dashboardPage.clickOnElementsOfDashboardPage("switchUIButtonV3");
						Assert.assertTrue(submitFeedbackVeneer3(), "Old UI of techpulse did not load successfully.");
						LOGGER.info("Navigated to Veneer 2 version successfully.");
						sleeper(1000);
					}else if(getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM).contains("\"target\":\"ui_v2\"")){
						LOGGER.info("Already in Veneer 2 UI");
					}
				}else{
					LOGGER.info("Toggle for Veneer 3 is not enabled, execution can be continued.");
				}
			}else{
				LOGGER.error("Pass correct Veneer version in POM, i.e. VENEER2 or VENEER3");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}
	
	/**
	 * Login method for any other customized URL of TechPulse
	 * 
	 */
	public final void loginCustom(String email, String password) throws Exception {
		environment = System.getProperty("environment");
		LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		LicensesListPage subscriptionsListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		LaunchDarkly ldinstance = new LaunchDarkly();
		loginPage.waitForElementsOfLoginPage("emailInputField");
		Assert.assertTrue(loginPage.verifyElementsOfLoginPage("emailInputField"), "login page did not open successfully");
		loginPage.enterTextForLoginPage("emailInputField", getCredentials(environment, email));
		if ((environment.equalsIgnoreCase("US-PRODUCTION") || environment.equalsIgnoreCase("EU-PRODUCTION")) && !(Strings.isNullOrEmpty(buildNumber)))
			sleeper(5000);
		if (ldinstance.enabled("hpid-new-ui-testing", null, null, false) == true) {
			loginPage.clickOnElementsOfLoginPage("nextbuttonNewUI");
			Assert.assertTrue(loginPage.verifyElementsOfLoginPage("passwordTitleNewUI"), "Password page did not open successfully");
		} else {
			loginPage.clickOnElementsOfLoginPage("nextbutton");
			Assert.assertTrue(loginPage.verifyElementsOfLoginPage("passwordTitle"), "Password page did not open successfully");
		}
		loginPage.waitForElementsOfLoginPage("passwordInputField");
		loginPage.enterTextForLoginPage("passwordInputField", getCredentials(environment, password));
		if (ldinstance.enabled("hpid-new-ui-testing", null, null, false) == true) {
			loginPage.clickOnElementsOfLoginPage("submitButtonNewUI");
		} else {
			loginPage.clickOnElementsOfLoginPage("submitButton");
		}

		loginPage.waitForElementsOfLoginPageDynamic("pageTitle",CommonVariables.LOGIN_WAIT);
		sleeper(5000);
		waitForPageLoaded();
		loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		subscriptionsListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		if(isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)){
        if(dashboardPage.waitForPresenceOfElementsOfDashboardPageDynamic("tryNewTechpulseTitle",1)){
        	dashboardPage.clickOnElementsOfDashboardPage("tryNewTechpulseModalClose");
        	sleeper(5000);
		}
        }
//		verifyUIVersion();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, email))) {
			switch (getAttributeDynamic("[css]:.dui-app-bar", "id",CommonVariables.LOGIN_WAIT).toUpperCase()) {
			case "DASHBOARD":
				Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard page did not load successfully after login. Error occurred in login, please check.");
				break;

			case "COMPANIESLIST":
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Root Companies list page did not load successfully after login. Error occurred in login, please check.");
				break;

			case "COMPANYDETAILS":
				Assert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("companiesDetailsTitle"), "Settings page did not load successfully after login. Error occurred in login, please check.");
				break;

			case "DEVICELIST":
				Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("devicesTitle"), "Devices page did not load successfully after login. Error occurred in login, please check.");
				break;

			case "ROLELIST":
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("rolesAndPermissionTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.rolesPermission")), "Roles and permissions page did not load successfully after login. Error occurred in login, please check.");
				break;

			case "ADD-NEW-SUBSCRIPTION-KEY":
				Assert.assertTrue(subscriptionsListPage.getTextOfSubscriptionsListPage("subscriptionsPageTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "Subscription page did not load successfully after login. Error occurred in login, please check.");
				break;

			default:
				throw new Exception("Failed to get ID of the page as page took more than 20 seconds to load");
			}
		} else {
			switch (getAttributeDynamic("[css]:.dui-app-bar", "id",CommonVariables.LOGIN_WAIT).toUpperCase()) {

			case "COMPANIESLIST":
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Root Companies list page did not load successfully after login. Error occurred in login, please check.");
				break;

			case "ROLELIST":
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("rolesAndPermissionTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.rolesPermission")), "Roles and permissions page did not load successfully after login. Error occurred in login, please check.");
				break;

			case "DASHBOARD":
				if (dashboardPage.verifyElementsOfDashboardPage("globalFilter")) {
					LOGGER.info("Login is successfull.");
				} else if (dashboardPage.getTextOfDashboardPage("dashboardTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard"))) {
					LOGGER.info("Login is successfull.");
				} else {
					LOGGER.error("Dashboard page did not load successfully.");
				}
				break;

			case "COMPANYDETAILS":
				Assert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("companiesBreadcrumb2"), "Settings page did not load successfully after login. Error occurred in login, please check.");
				break;
				
			case "DEVICELIST":
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalFilter"), "Devices page did not load successfully after login. Error occurred in login, please check.");
				break;

			case "DEVICEDETAILS":
				Assert.assertTrue(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("deviceBreadCrumbs"), "Devices details page did not load successfully after login. Error occurred in login, please check.");
				break;

			default:

				throw new Exception("Failed to get ID of the page as page took more than 20 seconds to load");
			}
		}

		changeLanguage(LanguageCode);
	}
	
	/** Login using SignInWithMicrosoft button */
	
	public final void loginAsMicrosoftUser(String email, String password) throws Exception {
		environment = System.getProperty("environment");
		LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		LicensesListPage subscriptionsListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		LaunchDarkly ldinstance = new LaunchDarkly();
		acceptCookiesConsent();
		if (ldinstance.enabled("daas-landing-page", null, null, false) == true) {
			Assert.assertTrue(welcomePage.verifyElementsOfWelcomePage("welcomePageTitle"), "welcome page did not open successfully");
			if (ldinstance.enabled("self-onboarding", null, null, false) == true)
				welcomePage.clickOnElementsOfWelcomePage("signInButton_selfOnboarding");
			else
				welcomePage.clickOnElementsOfWelcomePage("signInMicrosoftButton");
		}else
			welcomePage.clickOnElementsOfWelcomePage("signInMicrosoftButton");
		
		loginPage.waitForElementsOfLoginPage("emailInputFieldAzureID");
		loginPage.enterTextForLoginPage("emailInputFieldAzureID", getCredentials(environment, email));
		loginPage.clickOnElementsOfLoginPage("nextButtonMicrosoft");
		loginPage.waitForElementsOfLoginPageDynamic("passwordTitleMicrosoftAccount",CommonVariables.EXPLICITWAIT);
		sleeper(5000);
		Assert.assertTrue(loginPage.verifyElementsOfLoginPage("passwordTitleMicrosoftAccount"), "Password page did not open successfully");
		loginPage.waitForElementsOfLoginPage("passwordInputFieldAzureAccount");
		loginPage.enterTextForLoginPage("passwordInputFieldAzureAccount", getCredentials(environment, password));
		loginPage.clickOnElementsOfLoginPage("nextButtonMicrosoft");
		loginPage.waitForElementsOfLoginPage("staySignedInYesButton");
		loginPage.clickOnElementsOfLoginPage("staySignedInYesButton");
		sleeper(1000);
		waitForPageLoaded();
		if(loginPage.waitForElementsOfLoginPage("acceptbtn")) {
			loginPage.clickByJavaScriptOfLoginPage("termsCondCheckbox");
			loginPage.clickByJavaScriptOfLoginPage("acceptbtn");
		}
		loginPage.waitForElementsOfLoginPageDynamic("pageTitle",CommonVariables.LOGIN_WAIT);
		sleeper(5000);
		refreshPage();
		waitForPageLoaded();
		loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		subscriptionsListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();

		if (email.contains("WAITING")) {
			if (getUrlOfCurrentPage().contains("/error/pending_approval")) {
				dashboardPage.pressKey(Keys.ESCAPE);
			}
		} else if (email.contains("BLOCKED")) {
			if (getUrlOfCurrentPage().contains("/error/blocked")) {
				dashboardPage.pressKey(Keys.ESCAPE);
			}
		} else {
			if (toggleVerification(DashboardVariables.WHATS_NEW_ALERT_TOGGLE, getCredentials(environment, email))) {			
				dashboardPage.pressEscapeKeysForDashboardPage();
			}

			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, email))) {
				switch (getAttributeDynamic("[css]:.dui-app-bar", "id", CommonVariables.LOGIN_WAIT).toUpperCase()) {
					case "DASHBOARD":
						Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard page did not load successfully after login. Error occurred in login, please check.");
						break;

					case "COMPANIESLIST":
						Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Root Companies list page did not load successfully after login. Error occurred in login, please check.");
						break;

					case "COMPANYDETAILS":
						Assert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("companiesDetailsTitle"), "Settings page did not load successfully after login. Error occurred in login, please check.");
						break;

					case "DEVICELIST":
						Assert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("devicesTitle"), "Devices page did not load successfully after login. Error occurred in login, please check.");
						break;

					case "ROLELIST":
						Assert.assertTrue(companiesPage.getTextOfCompaniesPage("rolesAndPermissionTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.rolesPermission")), "Roles and permissions page did not load successfully after login. Error occurred in login, please check.");
						break;

					case "ADD-NEW-SUBSCRIPTION-KEY":
						Assert.assertTrue(subscriptionsListPage.getTextOfSubscriptionsListPage("subscriptionsPageTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "Subscription page did not load successfully after login. Error occurred in login, please check.");
						break;

					case "BILLINGREPORT":
						Assert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("billingAdminReportHeader"), "Billing Page did not load successfully after login. Error occurred in login, please check.");
						break;

					default:
						throw new Exception("Failed to get ID of the page as page took more than 20 seconds to load");
				}
			} else  {
					switch (getAttributeDynamic("[css]:.dui-header-nav", "id", CommonVariables.LOGIN_WAIT).toUpperCase()) {

						case "COMPANIESLIST":
							Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Root Companies list page did not load successfully after login. Error occurred in login, please check.");
							break;

						case "ROLELIST":
							Assert.assertTrue(companiesPage.getTextOfCompaniesPage("rolesAndPermissionTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.rolesPermission")), "Roles and permissions page did not load successfully after login. Error occurred in login, please check.");
							break;

						case "DASHBOARD":
							if (dashboardPage.verifyElementsOfDashboardPage("globalFilter")) {
								LOGGER.info("Login is successfull.");
							} else if (dashboardPage.getTextOfDashboardPage("dashboardTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard"))) {
								LOGGER.info("Login is successfull.");
							} else {
								LOGGER.error("Dashboard page did not load successfully.");
							}
							break;

						case "COMPANYDETAILS":
							Assert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("companiesDetailsTitle"), "Settings page did not load successfully after login. Error occurred in login, please check.");
							break;

						case "ADD-NEW-SUBSCRIPTION-KEY":
							Assert.assertTrue(subscriptionsListPage.getTextOfSubscriptionsListPage("subscriptionsPageTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "Subscription page did not load successfully after login. Error occurred in login, please check.");
							break;

						case "DEVICELIST":
							Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalFilter"), "Devices page did not load successfully after login. Error occurred in login, please check.");
							break;

						case "BILLINGREPORT":
							Assert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("billingAdminReportHeader"), "Billing Page did not load successfully after login. Error occurred in login, please check.");
							break;

						default:

							throw new Exception("Failed to get ID of the page as page took more than 20 seconds to load");
					}
				}
		}
		LOGGER.info(CommonVariables.PRODUCT_NAME + " login successfull");
		changeLanguage(LanguageCode);
	}
	
	/** This method is used to Select the Preferred language from the edit profile page
	 * @throws Exception
	 */
	public final String preferredlanguage(String preferredlanguage) throws Exception{
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		dashboardPage.waitForElementsOfDashboardPage("logoutButtonWithoutImage");
		dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		sleeper(2000);
		userDetailsPage.scrollOnUserPage("jobTitleLabel");
		sleeper(2000);
			softAssert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("languageLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.language")), "Model Language text is not matched on details tile popup");
			userDetailsPage.clickOnElementsOfUserDetailsPage("languageEditButton");
			softAssert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("languageModelHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.language")), "Language text for user is not present on details tile popup");
			userDetailsPage.clickOnElementsOfUserDetailsPage("selectArrowKey");
			userDetailsPage.waitForElementsOfUserDetailsPage(preferredlanguage);
			userDetailsPage.clickOnElementsOfUserDetailsPage(preferredlanguage);
			sleeper(3000);
			userDetailsPage.clickOnElementsOfUserDetailsPage("SaveButtonOnPopup");
			userDetailsPage.waitUntilElementIsInvisibleOfUserDetailsPage("toastNotification");
		return preferredlanguage;
	}

	/**
	 * Login method
	 * 
	 */
	public final void loginRoot(String email, String password) throws Exception {
		environment = System.getProperty("environment");
		LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver());
		loginPage = loginPage.getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
		dashboardPage = dashboardPage.getInstance();
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver());
		whatsNewPage = whatsNewPage.getInstance();
		loginPage.waitForElementsOfLoginPage("emailInputField");
		loginPage.enterTextForLoginPage("emailInputField", getCredentials(environment, email));
		if ((environment.equalsIgnoreCase("US-PRODUCTION") || environment.equalsIgnoreCase("EU-PRODUCTION")) && !(Strings.isNullOrEmpty(buildNumber)))
			sleeper(5000);
		loginPage.clickOnElementsOfLoginPage("nextbutton");
		Assert.assertTrue(loginPage.verifyElementsOfLoginPage("passwordTitle"), "Password page did not open successfully");
		loginPage.waitForElementsOfLoginPage("passwordInputField");
		loginPage.enterTextForLoginPage("passwordInputField", getCredentials(environment, password));
		loginPage.clickOnElementsOfLoginPage("submitButton");
		dashboardPage.waitForPageLoaded();
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("dashboardTitle"), "HP WebApp not login successfully");
		dashboardPage.waitForElementsOfDashboardPage("dashboardTitle");
		Assert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("partnerTitle"), "HP WebApp not login successfully");
		changeLanguage(LanguageCode);
	}

	/**
	 * Logout method
	 * 
	 */
	public final void logout() throws Exception {
		//sleeper(2000);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (dashboardPage.verifyElementsOfDashboardPage("logoutButton")) {
			sleeper(2000);
			dashboardPage.clickByJavaScriptOnDashboardPage("logoutButton");
			sleeper(2000);
			if(!getDriver().getCurrentUrl().contains("admin")) {
				dashboardPage.waitForPresenceOfElementsOfDashboardPage("signoutButton");
				dashboardPage.clickByJavaScriptOnDashboardPage("signoutButton");
			}else{
				dashboardPage.waitForPresenceOfElementsOfDashboardPage("signOutAdminCentral");
				dashboardPage.clickByJavaScriptOnDashboardPage("signOutAdminCentral");
			}
		} else if (dashboardPage.verifyElementsOfDashboardPage("logoutButtonWithoutImage")) {
			sleeper(2000);
			dashboardPage.clickByJavaScriptOnDashboardPage("logoutButtonWithoutImage");
			sleeper(2000);
			if(!getDriver().getCurrentUrl().contains("admin")) {
				dashboardPage.waitForPresenceOfElementsOfDashboardPage("signoutButton");
				dashboardPage.clickByJavaScriptOnDashboardPage("signoutButton");
			}else{
				dashboardPage.waitForPresenceOfElementsOfDashboardPage("signOutAdminCentral");
				dashboardPage.clickByJavaScriptOnDashboardPage("signOutAdminCentral");
			}
		} else {
			LOGGER.error("Logout button not present");
		}
	}

	/**
	 * Method to go to dashboard page
	 * 
	 */
	public final void gotoDashboardTab() throws Exception {
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
		dashboardPage = dashboardPage.getInstance();
		expandMenuIcon();
		// dashboardPage.clickByJavaScriptOnDashboardPage("dashboardTab");
		dashboardPage.clickOnElementsOfDashboardPage("dashboardTab");
		
	}

	/**
	 * Method to go to experience Management page
	 * 
	*/ 
	public final void gotoExperienceMgmtPageTab() throws Exception {
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver());
		experienceMgmtPage = experienceMgmtPage.getInstance();
		//expandMenuIcon();
		// dashboardPage.clickByJavaScriptOnDashboardPage("dashboardTab");
		experienceMgmtPage.clickByJavaScriptOnExperienceMgmtPage("experienceMgmtTab");
		//clickOnElementsOfExperienceMgmtPage("experienceMgmtTab");
		
	}
	
	/**Method to verify the Recommended Actions section in Experience Management page
	 * @throws Exception
	 */
	public final void VerifyRecommendedActions() throws Exception {
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver());
		experienceMgmtPage = experienceMgmtPage.getInstance();
		expandMenuIcon();
		experienceMgmtPage.getTextOfExperienceMgmtPage("recommendedActions");
		if(experienceMgmtPage.verifyElementsOfExperienceMgmtPage("noDataRecommendedAction")) {
			LOGGER.info("The msg Nothing going on here validated successfully");
		}
		else {
	        experienceMgmtPage.getTextOfExperienceMgmtPage("recommendedActionsec");
		String Recommendedactionssec = experienceMgmtPage.getTextOfExperienceMgmtPage("recommendedActionssection");
		Character rc1 = Recommendedactionssec.charAt(0);
		if(verifyElementIsinvisibile("recommendedActionssidebar")) {
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("Sidebararrow");
		}String Recommendedactionssidebar = experienceMgmtPage.getTextOfExperienceMgmtPage("recommendedActionssidebar");
		Character rc2 = Recommendedactionssidebar.charAt(0);
		Assert.assertEquals(rc1, rc2);
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("Sidebararrow"); 
       		waitForPageLoaded();
		gotoExperienceMgmtPageTab();
		}
	}
	
	/**
	 * Method to go to HPDex Tab
	 * 
	 */
	public final void gotoHPDexTab() throws Exception {
		HPDexPage hpdexPage = new HPDexPage(PreDefinedActions.getDriver());
		hpdexPage = hpdexPage.getInstance();
		expandMenuIcon();
		hpdexPage.clickOnElementsOfHPDexPage("hpDexTab");
	}
	
	/**
	 * Method to go to incident page
	 * 
	 */
	public final void gotoIncidentTab() throws Exception {
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver());
		incidentPage = incidentPage.getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"));
		// incidentPage.clickByJavaScriptOnIncidentPage("incidentTab");
		incidentPage.clickOnElementsOfIncidentPage("incidentTab");
	}

	/**
	 * Method to go to device page from old UI
	 * 
	 */
	public final void gotoDeviceTab() throws Exception {
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver());
		deviceListPage = deviceListPage.getInstance();
		expandMenuIcon();
		// deviceListPage.clickByJavaScriptOnDeviceListPage("deviceTab");
		deviceListPage.clickOnElementsOfDeviceListPage("deviceTab");
	}

	/**
	 * Method to go to devices page
	 * 
	 */
	public final void gotoDevicesTab() throws Exception {
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver());
		deviceListPage = deviceListPage.getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"));
		deviceListPage.clickByJavaScriptOnDeviceListPage("devicesTab");
		sleeper(2000);
	}

	/**
	 * Method to go to devices pending enrollment tab
	 * 
	 */
	public final void gotoPendingEnrollmentTab() throws Exception {
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver());
		deviceListPage = deviceListPage.getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"));
		deviceListPage.clickByJavaScriptOnDeviceListPage("devicesTab");
		deviceListPage.clickByJavaScriptOnDeviceListPage("pendingEnrollmentTab");
	}

	/**
	 * Method to go to companies page
	 * 
	 */
	public final void gotoCompaniesTab() throws Exception {

		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver());
		companiesPage = companiesPage.getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"));
		companiesPage.clickOnElementsOfCompaniesPage("companiesTab");
		
	}

	/**
	 * Method to go to support team page
	 * 
	 */
	public final void gotoSupportTeamTab() throws Exception {
		SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver());
		supportTeamPage = supportTeamPage.getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.myOrganization"));
		// supportTeamPage.clickByJavaScriptOnSupportTeamPage("supportTeamTab");
		supportTeamPage.clickOnElementsOfSupportTeamPage("supportTeamTab");
	}

	/**
	 * Method to go to partner team page
	 * 
	 */
	public final void gotoPartnerTeamTab() {
		try {
			SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver());
			supportTeamPage = supportTeamPage.getInstance();
			expandMenuIcon();
			expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.myOrganization"));
			supportTeamPage.clickOnElementsOfSupportTeamPage("partnerTeamTab");
			LOGGER.info("Clicked on Partner team tab");
		} catch (Exception e) {
			LOGGER.error("Partner Team tab is not present");
			e.printStackTrace();
		}
	}

	/**
	 * Method to go to subscription orders page
	 * 
	 */
	public final void gotosubscriptionOrdersTab() {
		try {
			LicenseOrdersPage subscriptionOrdersPage = new LicenseOrdersPage(PreDefinedActions.getDriver());
			subscriptionOrdersPage = subscriptionOrdersPage.getInstance();
			expandMenuIcon();
			expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.myOrganization"));
			subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("subscriptionOrdersTab");
			LOGGER.info("Clicked on subscription orders tab");
		} catch (Exception e) {
			LOGGER.error("Subscription orders tab is not present");
			e.printStackTrace();
		}
	}

	/**
	 * Method to go to user page
	 * 
	 */
	public final void gotoUserTab() throws Exception {
		UserPage userPage = new UserPage(PreDefinedActions.getDriver());
		userPage = userPage.getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"));
		// userPage.clickByJavaScriptOnUserPage("userTab");
		userPage.clickOnElementsOfUserPage("userTab");
	}

	/**
	 * Method to go to report page for report admin
	 */
	public final void gotoReportTabForAdmin() throws Exception {
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver());
		analyticsPage = analyticsPage.getInstance();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportTab");
	}

	/**
	 * Method to go to report page
	 * 
	 */
	public final void gotoReportTab() throws Exception {
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver());
		analyticsPage = analyticsPage.getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"));
		// expandMenuIconOldReports();
		// AnalyticsPage.clickByJavaScriptOnAnalyticsPage("reportTab");
		analyticsPage.clickOnElementsOfAnalyticsPage("reportTab");
	}

	/**
	 * Method to go to report page
	 * 
	 */
	public final void gotoReportTabFromOldUI() throws Exception {
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver());
		analyticsPage = analyticsPage.getInstance();
		expandMenuIconOldReports();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportTab");
	}

	/**
	 * Method to go to log page
	 * 
	 */
	public final void gotoLogTab() throws Exception {
		LogPage logPage = new LogPage(PreDefinedActions.getDriver());
		logPage = logPage.getInstance();
		expandMenuIcon();
		// logPage.clickByJavaScriptOnLogPage("logTab");
		logPage.scrollOnLogPage("logTab");
		logPage.clickOnElementsOfLogPage("logTab");
	}

	/**
	 * Method to go to setting page
	 * 
	 */
	public final void gotoSettingsTab() throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver());
		settingsPage = settingsPage.getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.myOrganization"));
		// settingsPage.clickByJavaScriptOnSettingsPage("settingsTab");
		settingsPage.scrollTillViewSettingsPage("dashboardTab");
		settingsPage.clickOnElementsOfSettingsPage("settingsTab");
	}

	/**
	 * Method to go to non msp setting page
	 * 
	 */
	public final void gotoNonMSPSettingsTab() throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver());
		settingsPage = settingsPage.getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.myOrganization"));
		// settingsPage.clickByJavaScriptOnSettingsPage("settingsTabNonMSP");
		settingsPage.clickOnElementsOfSettingsPage("settingsTabNonMSP");
	}

	/**
	 * Method to go to non msp setting page
	 * 
	 */
	public final void gotoNonMSPSettingsTabAppAdmin() throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver());
		settingsPage = settingsPage.getInstance();
		expandMenuIcon();
		settingsPage.clickOnElementsOfSettingsPage("settingTabApp");
	}

	/**
	 * Method to go to setting page for workflow
	 * 
	 */
	public final void gotoSettingsTabWorkflow() throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver());
		settingsPage = settingsPage.getInstance();
		expandMenuIcon();
		settingsPage.clickByJavaScriptOnSettingsPage("settingsTabWorkflow");
	}

	/**
	 * Method to go to Product Catalog page
	 * 
	 */
	public final void gotoProductCatalogTab() throws Exception {

		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.myOrganization"));
		productCatalogPage.clickOnElementsOfProductCatalogPage("productCatalogTab");
	}

	/**
	 * Method to go to help & support page
	 * 
	 */
	public final void gotoHelpAndSupportTab() throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver());
		settingsPage = settingsPage.getInstance();
		expandMenuIcon();
		settingsPage.scrollTillViewSettingsPage("helpAndSupportTab");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTab");
	}

	/**
	 * Method to go to EMM Tool page
	 * 
	 * @throws Exception
	 */
	public final void gotoEMMToolTab() throws Exception {
		EMMToolPage emmToolPage = new EMMToolPage(PreDefinedActions.getDriver());
		emmToolPage = emmToolPage.getInstance();
		expandMenuIcon();
		// emmToolPage.clickByJavaScriptOnEMMToolPage("emmToolClick");
		emmToolPage.clickOnElementsOfEMMToolPage("emmToolClick");
	}

	/**
	 * Method to go to setting page of the Report admin
	 * 
	 */
	public final void gotoSettingsTabOfReportAdmin() throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver());
		settingsPage = settingsPage.getInstance();
		expandMenuIcon();
		settingsPage.clickOnElementsOfSettingsPage("nonMSPSettingsTab");
	}

	/**
	 * Method to go to setting page of the SNOW Admin
	 * 
	 */
	public final void gotoSettingsTabOfSnowAdmin() throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver());
		settingsPage = settingsPage.getInstance();
		if (!settingsPage.matchTextOfSettingsPage("nonMSPSettingsTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.settings")))
			settingsPage.clickOnElementsOfSettingsPage("toggleSideMenu");
		settingsPage.clickOnElementsOfSettingsPage("nonMSPSettingsTab");
	}

	public final void goToPreferencesTab() throws Exception {

		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		// expandMenuIcon();
		preferencesPage.clickOnElementsOfPreferencesPage("preferencesTab");
	}

	/**
	 * Method to go to What's New page of Root Admin.
	 * 
	 */
	public final void gotoWhatsNewTabOfRootAdmin() throws Exception {
		try {
			WhatsNewPage whatNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIconRoot();
			if (!whatNewPage.getAttributesOfWhatsNewPage("administratorTab", "class").contains("expanded")) {
				whatNewPage.clickOnElementsOfWhatsNewPage("administratorTab");
				LOGGER.info("Clicked on administrators tab");
			} else {
				LOGGER.info("Administrators menu already expanded");
			}
			whatNewPage.clickOnElementsOfWhatsNewPage("whatsNewTabRoot");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoWhatsNewTabOfRootAdmin " + e.getMessage()));
		}
	}

	/**
	 * Method to go to What's New page of MSP/Customer.
	 * 
	 */
	public final void gotoWhatsNewTabOfMspRA() throws Exception {
		WhatsNewPage whatNewPage = new WhatsNewPage(PreDefinedActions.getDriver());
		whatNewPage = whatNewPage.getInstance();
		expandMenuIcon();
		whatNewPage.clickOnElementsOfWhatsNewPage("whatsNewTabMspRA");
	}

	/**
	 * Method to go to Microsoft Telemetry page
	 * 
	 */
	public final void gotoMicrosoftTelemetryTab() throws Exception {
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver());
		preferencesPage = preferencesPage.getInstance();
		expandMenuIcon();
		preferencesPage.clickOnElementsOfPreferencesPage("microsoftTelemetryTab");
	}

	/**
	 * Method to expand menu tab when screen resolution is small
	 * 
	 */
	public final void expandMenuIcon() throws Exception {
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
		dashboardPage = dashboardPage.getInstance();
		if (!dashboardPage.waitForElementsOfDashboardPage("dashboardTab")) {
			dashboardPage.clickOnElementsOfDashboardPage("menuIcon");
		}
	}

	/**
	 * Method to expand menu tab when screen resolution is small
	 * 
	 */
	public final void expandMenuIconOldReports() throws Exception {
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
		dashboardPage = dashboardPage.getInstance();
		if (!dashboardPage.waitForElementsOfDashboardPage("dashboardTab")) {
			dashboardPage.clickOnElementsOfDashboardPage("menuIconOldReports");
		}
	}

	/**
	 * Method to expand menu tab when screen resolution is small
	 * 
	 */
	public final void expandMenuIconRoot() throws Exception {
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver());
		whatsNewPage = whatsNewPage.getInstance();
		if (!whatsNewPage.waitForElementsOfWhatsNewPage("partnerTab")) {
			whatsNewPage.clickOnElementsOfWhatsNewPage("menuIcon");
		}
	}

	/**
	 * Method to go to companies page
	 *
	 */
	public final void gotoRootCompaniesTab() throws Exception {
		try {
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIconRoot();
			if (!companiesPage.getAttributesOfCompaniesPage("customersTab", "class").contains("expanded")) {
				companiesPage.clickOnElementsOfCompaniesPage("customersTab");
				LOGGER.info("Clicked on customers tab");
			} else {
				LOGGER.info("Customers menu already expanded");
			}
			companiesPage.clickOnElementsOfCompaniesPage("customersListTab");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoRootCompaniesTab " + e.getMessage()));
		}

	}

	/**
	 * method to go to partners list page
	 */
	public final void gotoPartnersTab() throws Exception {
		try {
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIconRoot();
			if (!partnerPage.getAttributesOfPartnerPage("partnersTab", "class").contains("expanded")) {
				partnerPage.clickOnElementsOfPartnerPage("partnersTab");
				LOGGER.info("Clicked on Partners tab");
			} else {
				LOGGER.info("Partners menu already expanded");
			}
			partnerPage.clickOnElementsOfPartnerPage("partnersListTab");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoPartnersTab " + e.getMessage()));
		}
	}

	/**
	 * method to go to MSP list page
	 */
	public final void gotoMSPTab() throws Exception {
		try {
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIconRoot();
			if (!partnerPage.getAttributesOfPartnerPage("mspTab", "class").contains("expanded")) {
				partnerPage.clickOnElementsOfPartnerPage("mspTab");
				LOGGER.info("Clicked on MSP tab");
			} else {
				LOGGER.info("MSPs menu already expanded");
			}
			partnerPage.clickOnElementsOfPartnerPage("mspListTab");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoMSPTab " + e.getMessage()));
		}
	}

	public final void expandMenuIconForRoot() throws Exception {
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver());
		partnerPage = partnerPage.getInstance();
		if (!partnerPage.waitForElementsOfPartnerPage("partnersTab")) {
			partnerPage.clickOnElementsOfPartnerPage("menuIcon");
		}
	}

	/**
	 * Method to go to user management page for workflow user
	 * 
	 */
	public final void gotoUserMgmtTab() throws Exception {
		UserPage userPage = new UserPage(PreDefinedActions.getDriver());
		userPage = userPage.getInstance();
		expandMenuIcon();
		userPage.clickByJavaScriptOnUserPage("userTabWorkflow");
	}

	/**
	 * Method to go to user page for application user
	 * 
	 */
	public final void gotoAppAdminUsersTab() throws Exception {
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		userPage.clickOnElementsOfUserPage("appUsersTab");
	}

	/**
	 * This method is used for login to DaaS portal by gmail login
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public final boolean gmailLogin(String email, String password) throws Exception {
		environment = System.getProperty("environment");
		LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver());
		loginPage = loginPage.getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
		dashboardPage = dashboardPage.getInstance();
		getDriver().get(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/home");
		GmailLoginPage gmailLoginPage = new GmailLoginPage(PreDefinedActions.getDriver());
		gmailLoginPage = gmailLoginPage.getInstance();
		loginPage.clickOnElementsOfLoginPage("gmailIcon");
		gmailLoginPage.waitForElementsOfGmailLoginPage("gmailEmail");
		Assert.assertTrue(gmailLoginPage.verifyElementIsEnableOfGmailLoginPage("gmailEmail"), "email on gmail page not found");
		gmailLoginPage.enterTextForGmailLoginPage("gmailEmail", getCredentials(environment, email));
		gmailLoginPage.clickOnElementsOfGmailLoginPage("gmailNext");
		gmailLoginPage.enterTextForGmailLoginPage("gmailPassword", getCredentials(environment, password));
		gmailLoginPage.clickOnElementsOfGmailLoginPage("GmailPassNext");
		dashboardPage.waitForElementsOfDashboardPage("dashboardTitle");
		if (dashboardPage.verifyElementsOfDashboardPage("dashboardTitle")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is used for login to DaaS portal by facebook login
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public final boolean facebookLogin(String email, String password) throws Exception {
		environment = System.getProperty("environment");
		LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver());
		loginPage = loginPage.getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
		dashboardPage = dashboardPage.getInstance();
		getDriver().get(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/home");
		FacebookLoginPage facebookLoginPage = new FacebookLoginPage(PreDefinedActions.getDriver());
		facebookLoginPage = facebookLoginPage.getInstance();
		loginPage.clickOnElementsOfLoginPage("facebookIcon");
		facebookLoginPage.waitForElementsOfFacebookLoginPage("fbEmail");
		facebookLoginPage.enterTextForFacebookLoginPage("fbEmail", getCredentials(environment, email));
		facebookLoginPage.enterTextForFacebookLoginPage("fbPassword", getCredentials(environment, password));
		facebookLoginPage.clickOnElementsOfFacebookLoginPage("fbLogin");
		dashboardPage.waitForElementsOfDashboardPage("dashboardTitle");
		if (dashboardPage.verifyElementsOfDashboardPage("dashboardTitle")) {
			return true;
		} else {
			return false;
		}
	}

	public final void loginAsMicrosoftTelemetryAdmin(String email, String password) throws Exception {
		environment = System.getProperty("environment");
		LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver());
		loginPage = loginPage.getInstance();
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver());
		welcomePage = welcomePage.getInstance();
		LaunchDarkly ldinstance = new LaunchDarkly();
		if (ldinstance.enabled("daas-landing-page", null, null, false) == true) {
			Assert.assertTrue(welcomePage.verifyElementsOfWelcomePage("welcomePageTitle"), "welcome page did not open successfully");
			welcomePage.clickOnElementsOfWelcomePage("signInButton");
		}
		Assert.assertTrue(loginPage.verifyElementsOfLoginPage("emailInputField"), "login page did not open successfully");
		PreferencesPage preferencepage = new PreferencesPage(PreDefinedActions.getDriver());
		preferencepage = preferencepage.getInstance();
		loginPage.waitForElementsOfLoginPage("emailInputField");
		loginPage.enterTextForLoginPage("emailInputField", getCredentials(environment, email));
		loginPage.clickOnElementsOfLoginPage("nextbutton");
		Assert.assertTrue(loginPage.verifyElementsOfLoginPage("passwordTitle"), "Password page did not open successfully");
		loginPage.waitForElementsOfLoginPage("passwordInputField");
		loginPage.enterTextForLoginPage("passwordInputField", getCredentials(environment, password));
		loginPage.clickOnElementsOfLoginPage("submitButton");
		sleeper(2000);
		preferencepage.clickOnElementsOfPreferencesPage("preferenceTab");
		preferencepage.waitForElementsOfPreferencesPage("microsoftTelemetryEditURLButton");
		preferencepage.waitForElementsOfPreferencesPage("microsoftTelemetrySettingName");
		Assert.assertTrue(preferencepage.verifyElementsOfPreferencesPage("microsoftTelemetryEditURLButton"));
		Assert.assertTrue(preferencepage.verifyElementsOfPreferencesPage("microsoftTelemetrySettingName"));
	}

	/**
	 * 
	 * @param data - data is which particular parameter you want from json file
	 * @param api - the api from you which you want the data
	 * @return data in string format
	 */
	public final String getDataFromApi(String data, String api) {
		String jsonResponse = null;
		try {
			String mspAuthToken =getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken);
			Response response = httpRequest.get(api);
			LOGGER.info(response.statusCode());
			jsonResponse = response.jsonPath().getString(data);
			LOGGER.info(jsonResponse);
		} catch (Exception e) {
			LOGGER.error("Exception in getDataFromApi" + e.getMessage());
		}
		return jsonResponse;
	}

	/**
	 * 
	 * @param key - parameter you want read from json file in . format
	 * @param api - URL from you which you want the data
	 * @param body - request body
	 * @param index - array index value
	 */
	public final String getDataFromApiPost(String key, String api, String body, int index) {
		String jsonResponse = null;
		try {
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.post(api);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(response.body().asString());
			if (rootNode.get("totalResults").intValue() > 0) {
//				if (!key.contains(".")) {
//					jsonResponse = key;
//					return jsonResponse;
//				}
				String[] data = key.split("\\.");
				List<JsonNode> jsonNodes = rootNode.findValues(data[index]);
				jsonResponse = jsonNodes.get(0).asText().replaceAll("\\\\", "");
			} else {
				jsonResponse = "";
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getDataFromApiPost" + e.getMessage());
		}
		return jsonResponse;
	}

	/**
	 * This method is used to get status code from given API URL
	 * 
	 * @param api - api from you which you want the status code
	 * @param body - request body
	 */
	public final int getStatusCodePost(String api, String body) {
		int statusCode = 0;
		try {
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RestAssured.baseURI = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "api";
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.post(api);
			statusCode = response.statusCode();
		} catch (Exception e) {
			LOGGER.error("Exception in getStatusCode" + e.getMessage());
		}
		return statusCode;
	}

	/**
	 * This method is used to check format for the given date.
	 * 
	 * @param format - regular expression format e.g.([0-9]{2})/([0-9]{2})/([0-9]{4})
	 * @param date - date
	 */
	public final boolean checkDateFormat(String format, String date) {
		if (date != null && date.matches(format)) {
			return true;
		} else {
			LOGGER.error("Date is not valid");
			return false;
		}

	}

	public final void verifyTableConfigurationTests(ArrayList<String> columnName, ArrayList<String> checkboxValue, ArrayList<String> uniqueColumnName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		resetTableConfiguration();
		waitForPageLoaded();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		ArrayList<String> columnNameOnPopup = null;
		ArrayList<String> columnNamesOnPage = null;
		uniqueColumnName = (ArrayList<String>) uniqueColumnName.stream().map(String::toLowerCase).collect(Collectors.toList());
		columnName = (ArrayList<String>) columnName.stream().map(String::toLowerCase).collect(Collectors.toList());
		// Test Case 2 - This test case validates if the table configuration opens when we click on the table configuration icon
		companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
		LOGGER.info("Opened table configuration popup");
		companiesPage.waitForElementsOfCompaniesPage("tableConfigurationTitle");
		softAssert.assertTrue(tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationTitle"), "table configuration pop-up does not open after clicking on table Configuration Button");

		// Test Case 3 - This test case validates if the sequence of available column is correct on the popup
//       I will remove below commented code when existing bug gets resolved.
//		softAssert.assertTrue(tableConfigurationPage.verifySequenceOfColumnInPopup("listOfColumnsOnPopup", columnName), "Sequence of available column names in configuration pop-up doesn't match ");
//		LOGGER.info("Verified sequence of columns on table configuration popup");

		// Test Case 4 - This test case validates if by default the values of all the checkboxes on the popup is correct
		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("resettodefault")) {
			if(tableConfigurationPage.verifyElementIsEnableOftableConfigurationPage("resettodefault")){
				tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
			}
			LOGGER.info("Clicked on reset to default");
			LOGGER.info("Set table configuration to default state");
		}
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
		sleeper(2000);
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		
		// Test Case 5 - This test case validates if by default the save button is disabled on the table confguration popup
		companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
		sleeper(2000);
		softAssert.assertTrue(tableConfigurationPage.verifyElementIsEnableOftableConfigurationPage("saveButton"), "By default save button should be enable on table configuration pop-up");

		// Test Case 6 - This test case validates if by default the discard button is enabled on the table configuration popup
		softAssert.assertTrue(tableConfigurationPage.verifyElementIsEnableOftableConfigurationPage("discardButton"), "By default discard button should be enable on table configuration pop-up");

		// Test Case 7 - This test case validates if the sequence of columns on the popup and the table on the company page is same
		columnNameOnPopup = tableConfigurationPage.getSequenceOfSelectedColumns("selectedCheckBoxesOnPopup", "listOfcheckedColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		columnNamesOnPage = companiesPage.getSequenceOfSelectedColumnsOnCompanyPage("listOfColumnsOnTable");
		softAssert.assertTrue(columnNameOnPopup.equals(columnNamesOnPage), "Sequence of column names on company page and table configuration doesn't match");

		// Test Case 8 - This test case validates the discard functionality for the table configuration popup
		companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
		int actualCount = tableConfigurationPage.getCountOfSelectedCheckBoxOnPopup("selectedCheckBoxesOnPopup");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		LOGGER.info("All columns on table configuration selected");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("discardButton");
		companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
		int expectedCount = tableConfigurationPage.getCountOfSelectedCheckBoxOnPopup("selectedCheckBoxesOnPopup");
		softAssert.assertEquals(actualCount, expectedCount, "Discard functionality not working properly");
		LOGGER.info("Verified discard functionality on table configuration popup");

		// Test Case 9 - This test case validates the sequence of columns on company page when all the checkboxes of the table configuration popup are checked
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
		columnNameOnPopup = tableConfigurationPage.getSequenceOfSelectedColumns("selectedCheckBoxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("discardButton");
		columnNamesOnPage = companiesPage.getSequenceOfSelectedColumnsOnCompanyPage("listOfColumnsOnTable");
		//I will remove below commented code when existing bug gets resolved.
		//softAssert.assertTrue(columnNameOnPopup.equals(columnNamesOnPage), "When we select all checkboxes of available colums in configuration pop-up ,Sequence of column on company page and table configuration doesn't match");

		// Test Case 10 - This test case validates if after reordering the columns on the table configuration the sequence matches with that of the columns of table on company
		// page
		companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("idCheckboxOfPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("downArrow");
		LOGGER.info("Changed order of columns on table configuration");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
		columnNameOnPopup = tableConfigurationPage.getSequenceOfSelectedColumns("selectedCheckBoxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("discardButton");
		columnNamesOnPage = companiesPage.getSequenceOfSelectedColumnsOnCompanyPage("listOfColumnsOnTable");
		softAssert.assertTrue(columnNameOnPopup.equals(columnNamesOnPage), "When we arrange a sequence of columns by up-down arrow from configuration pop-up ,Sequence of column on company page and table configuration doesn't match");

		// Test Case 11 - This test case validates the presence of the columns on the table when all the checkboxes are deselected from the popup
		companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("idCheckboxOfPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("downArrow");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
		sleeper(3000);
		tableConfigurationPage.deselectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		LOGGER.info("Deselected all columns on table configuration");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(2000);
		softAssert.assertTrue(companiesPage.getSequenceOfSelectedColumnsOnCompanyPage("listOfColumnsOnTable").equals(uniqueColumnName), "table columns except ID column are present even after we deselect all the checkbox from table configuration pop-up");

		// Test Case 12 - This test case validates if the reset to default link functionality is working properly
		companiesPage.clickOnElementsOfCompaniesPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
		// I will remove below commented code when existing bug gets resolved.
//		softAssert.assertTrue(tableConfigurationPage.verifySequenceOfColumnInPopup("listOfColumnsOnPopup", columnName), "Reset to default functionality not working properly");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");

		softAssert.assertAll();
		LOGGER.info("All test cases of table configuration passed");

	}

	////////////// Need to add following code in company page when we will write new
	////////////// code for company/////////////

	/**
	 * Method to impersonate company using email
	 * 
	 */
	public final void impersonateCompanyByEmail(String email) throws Exception {
		CompaniesPage companiespage = new CompaniesPage(PreDefinedActions.getDriver());
		companiespage = companiespage.getInstance();
		gotoCompaniesTab();
		companiespage.clickOnElementsOfCompaniesPage("companysearchbox");
		companiespage.waitForElementsOfCompaniesPage("emailInCompanyTable");
		companiespage.enterTextForCompaniesPage("companysearchbox", email);
		Assert.assertFalse(companiespage.matchTextOfCompaniesPage("companyEmptySearch", CompaniesVariables.COMPANY_SEARCH_EMPTY), "company not found in the search result");
		Assert.assertTrue(companiespage.matchTextOfCompaniesPage("emailInCompanyTable", email), "company search is not correct");
		companiespage.clickOnElementsOfCompaniesPage("companyInSearchList");
		companiespage.waitForElementsOfCompaniesPage("companyDetailsForm");
	}

	/**
	 * 
	 * Method to impersonate company using company name for new Company UI. It will remove all the filters and search on company list page, and select given company name and
	 * redirected on company detail page.
	 * 
	 * @param companyName (While calling this method give parameter from ConstantURL class.)
	 * @throws Exception
	 */

	public final void impersonateCompanyByCompanyName(String companyName) throws Exception {
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		resetTableConfiguration();
		waitForPageLoaded();
		companiesPage.enterTextForCompaniesPage("companyNameSearch",companyName);
		sleeper(4000);//result takes time to come
		//companiesPage.waitForElementsOfCompaniesPage("companySearchResult");
		//Assert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("companyEmptySearchResult"), "Company not found");
		companiesPage.waitForElementsOfCompaniesPage("companySearchResult");
		Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companySearchResult").equalsIgnoreCase(companyName), "company is not found on company search list.");
		companiesPage.clickByJavaScriptOnCompaniesPage("companySearchResult");
		companiesPage.waitForElementsOfCompaniesPage("companyNameOnDetailsPage");
		Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companyNameOnDetailsPage").equalsIgnoreCase(companyName), "Company name is not present on company details page.");
		LOGGER.info("Valid company name is present on company detail page.");
	}

	/*
	 * Checks if the company is present.
	 *
	 */
	public final boolean companyAvailable(String email) throws Exception {
		CompaniesPage companiespage = new CompaniesPage(PreDefinedActions.getDriver());
		companiespage = companiespage.getInstance();
		gotoCompaniesTab();
		companiespage.clickOnElementsOfCompaniesPage("companysearchbox");
		companiespage.enterTextForCompaniesPage("companysearchbox", email);
		Thread.sleep(3000);
		if (!companiespage.verifyElementsOfCompaniesPage("noCompany"))
			if (companiespage.matchTextOfCompaniesPage("emailInCompanyTable", email))
				return true;
			else {
				LOGGER.info("Company not found in the search result.");
				return false;
			}
		return false;
	}

	/*
	 * Checks if the company is present for root admin.
	 *
	 */
	public final boolean companyAvailableForRoot(String email) throws Exception {
		CompaniesPage companiespage = new CompaniesPage(PreDefinedActions.getDriver());
		companiespage = companiespage.getInstance();
		gotoRootCompaniesTab();
		if (companiespage.waitForElementsOfCompaniesPage("removeAllSearchFilters")) {
			sleeper(3000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			companiespage.clickOnElementsOfCompaniesPage("removeAllSearchFilters");
			companiespage.waitForElementsOfCompaniesPage("tableOverlay");
			LOGGER.info("All the filters of company list page has been removed successfully.");
			sleeper(4000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
		}
		companiespage.clickOnElementsOfCompaniesPage("companysearchboxForRoot");
		companiespage.enterTextForCompaniesPage("companysearchboxForRoot", email);
		Thread.sleep(3000);
		if (!companiespage.verifyElementsOfCompaniesPage("noCompany")) {
			if (companiespage.matchTextOfCompaniesPage("emailInCompanyTableForRoot", email))
				return true;
		} else {
			LOGGER.info("Company not found in the search result.");
			return false;
		}
		return false;
	}

	/*
	 * This method will: 1. Login via root admin. 2. Create a company. 3. Login via MSP under which tenant is created and validate if the company is listed properly,\.
	 *
	 * NOTE: Use MSP04 for US and MSP06 for EU
	 */
	public final boolean addCompany(String MSP, String reseller, String country, String region) throws Exception {
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver());
		companiesPage = companiesPage.getInstance();
		if (region.equals("US")) {
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			MSPEmail = "MSP_ADMIN_US_EMAIl";
			MSPPassword = "MSP_ADMIN_US_PASSWORD";
		} else {
			login("ROOT_ADMIN_USER_EU", "ROOT_PASSWORD_EU");
			MSPEmail = "MSP_ADMIN_EU_EMAIL";
			MSPPassword = "MSP_ADMIN__EU_PASSWORD";
		}
		gotoRootCompaniesTab();
		HashMap<String, String> companyInfo = new HashMap<String, String>();
		companyInfo = companiesPage.getCompanyDetails();
		companiesPage.clickOnElementsOfCompaniesPage("addCompanyButton");
		Assert.assertTrue(companiesPage.addNewCompany(companyInfo, MSP, reseller, country), "Error while entering data for new company");
		Assert.assertTrue(companiesPage.waitForElementsOfCompaniesPage("notification"), "Notification not displayed");
		Assert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("successNotification"), "Success notification is not displayed");
		LOGGER.info("Company added successfully");
		companyEmail = companyInfo.get("email").toString();
		logout();
		return true;
	}

	/*
	 * This method will check if the company is listed in MSP
	 */
	public final boolean companyPresentInMSP(String companyEmail, String MSPEmail, String MSPPassword) throws Exception {
		LOGGER.info("Logging in via MSP admin");
		login(MSPEmail, MSPPassword);
		return companyAvailable(companyEmail) ? true : false;
	}

	/*
	 * This method will: 1. Login via root admin 2. Delete a company.
	 */
	public final boolean companyCleanUp(String companyEmail, String region) throws Exception {
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver());
		companiesPage = companiesPage.getInstance();
		if (region.equals("US"))
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		else
			login("ROOT_ADMIN_USER_EU", "ROOT_PASSWORD_EU");
		LOGGER.info("Logging in via Root Admin");
		Assert.assertTrue(companyAvailableForRoot(companyEmail), "Company not present");
		companiesPage.mouseHoverOnCompanyPage("emailInCompanyTableForRoot");
		LOGGER.info("Deleting company for " + companyEmail);
		companiesPage.clickOnElementsOfCompaniesPage("hamburgerIcon");
		companiesPage.clickOnElementsOfCompaniesPage("removeCompany");
		companiesPage.clickOnElementsOfCompaniesPage("removeButton");
		Assert.assertTrue(companiesPage.waitForElementsOfCompaniesPage("toastNotification"), "Notification not displayed");
		return companiesPage.verifyElementsOfCompaniesPage("toastNotification") ? true : false;
	}

	/**
	 * This method is to pass the expected company selected as per env in reports.
	 * 
	 * @param companyname
	 * @return
	 * @throws Exception
	 */
	public final String getCompanyValueReports(String companyname) throws Exception {
		environment = System.getProperty("environment");
		String companynameReports = getCredentials(environment, companyname);
		return companynameReports;
	}

	/**
	 * This method is used to impersonate company by navigating on reports page.
	 * 
	 * @param name
	 * @throws Exception
	 */
	public final void impersonateCompanyByEmailReports(String name) throws Exception {
		environment = System.getProperty("environment");
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver());
		analyticsPage = analyticsPage.getInstance();
		gotoReportTab();
		analyticsPage.verifyElementIsVisibleOfAnalyticsPage("companyDropdown");
		analyticsPage.clickOnElementsOfAnalyticsPage("companyDropdown");
		analyticsPage.verifyElementIsVisibleOfAnalyticsPage("companySelectionBar");
		if (analyticsPage.verifyElementsOfAnalyticsPage("seemore") == true) {
			analyticsPage.clickOnElementsOfAnalyticsPage("seemore");
			analyticsPage.verifyElementIsVisibleOfAnalyticsPage("companysearchboxreports");
			analyticsPage.clickOnElementsOfAnalyticsPage("companysearchboxreports");
			analyticsPage.enterTextForAnalyticsPage("companysearchboxreports", getCredentials(environment, name));
			analyticsPage.verifyElementIsVisibleOfAnalyticsPage("companySelectRadioButtonSeemore");
			sleeper(3000);
			analyticsPage.clickOnElementsOfAnalyticsPage("companySelectRadioButtonSeemore");
			analyticsPage.clickOnElementsOfAnalyticsPage("applyButton");
			LOGGER.info("Company selection is successfull");
		} else {
			List<WebElement> element = analyticsPage.getElementsTillAllElementsPresentAnalyticsPage("companylist");
			if (element.size() > 0) {
				for (WebElement we : element) {
					if (we.getText().equalsIgnoreCase(getCredentials(environment, name)) == true) {
						we.click();
						LOGGER.info("Company selection is successfull");
						break;
					}
				}
			}
		}
		analyticsPage.verifyElementIsVisibleOfAnalyticsPage("AnalyticsPageAllReportsList");
		LOGGER.info("Company impersonation is successfull");
	}


	/**
	 * Impersonate company by Name
	 * 
	 * @param name - This is the Name of the company.
	 * @throws Exception
	 */
	public final void impersonateCompanyByNameReports(String name, String email) throws Exception {
		environment = System.getProperty("environment");
		boolean flag = false;
		if (!(environment.equalsIgnoreCase("PENTEST"))) {
			SoftAssert softAssert = new SoftAssert();
			AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver());
			analyticsPage = analyticsPage.getInstance();
			gotoReportTab();
			LOGGER.info("Verifying global filter and selecting company");
			Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("globalLocationFilter"),
					"Global location filter option is not available on Reports page");
			Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("globalLocationFilterDropdownReports"),
					"Selection of global location filter is not available on Reports page");

			if (uiVersion.equalsIgnoreCase("VENEER2")) {
				analyticsPage.clickOnElementsOfAnalyticsPage("globalLocationFilterClearIconReports");
				waitForPageLoaded();
				flag = analyticsPage.getTextOfAnalyticsPage("locatFilterCompanyName")
						.equalsIgnoreCase(getCredentials(environment, name));
			}
			else if (uiVersion.equalsIgnoreCase("VENEER3")) {
				flag = analyticsPage.getTextOfAnalyticsPage("globalLocationFilterDropdownReports")
						.equalsIgnoreCase(getCredentials(environment, name));
			}
			if (flag == true) {
				LOGGER.info("Company is already selected");
				analyticsPage.verifyElementIsVisibleOfAnalyticsPage("customReportsCreatedList");
			} else {
				analyticsPage.waitUntilElementIsInvisibleOfAnalyticsPage("reportListPageSpinner");
				analyticsPage.clickOnElementsOfAnalyticsPage("globalLocationFilterDropdownReports");
				waitForPageLoaded();
				sleeper(1000);
				softAssert.assertTrue(
						analyticsPage.getTextOfAnalyticsPage("filterMenuCompaniesHeader")
								.equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui",
										"dashboard.actions.filter.company.label")),
						"Location filter header does not match");
				analyticsPage.clickOnElementsOfAnalyticsPage("filterMenuCompaniesSearch");
				analyticsPage.enterTextForAnalyticsPage("filterMenuCompaniesSearch", getCredentials(environment, name));
				LOGGER.info("Search company to set location filter");
				waitForPageLoaded();
				sleeper(3000);
				analyticsPage.waitForElementsOfAnalyticsPage("selectCompanyOnSearch");
				softAssert.assertTrue(
						analyticsPage.matchTextOfAnalyticsPage("selectCompanyOnSearch", getCredentials(environment, name)),
						"Company name and search result company name does not match");
				analyticsPage.clickOnElementsOfAnalyticsPage("selectCompanyOnSearch");
				analyticsPage.clickOnElementsOfAnalyticsPage("applyCompanyLocationFilter");
				// To click on blank area
				if (uiVersion.equalsIgnoreCase("VENEER3")) {
					analyticsPage.clickOnElementsOfAnalyticsPage("blankArea");
				}
				sleeper(6000);
				LOGGER.info("Company selected successfully");
				analyticsPage.waitForElementsOfAnalyticsPage("customReportsCreatedList");
				analyticsPage.verifyElementIsVisibleOfAnalyticsPage("customReportsCreatedList");
			}
		} else {
			gotoReportTab();
			LOGGER.info("Pentest Env No need for impersonation");
		}
	}
	
	/**
	 * Impersonate company by Name on the Benchmark page
	 * 
	 * @param name  - This is the Name of the company.
	 * @param email - predefined email id used for log in
	 * @throws Exception
	 */
	public final void impersonateCompanyByNameBenchmark(String name, String email) throws Exception {
		environment = System.getProperty("environment");
		SoftAssert softAssert = new SoftAssert();
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		gotoBenchmarkPage();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, email))) {
			benchmarkPage.verifyElementIsVisibleOfBenchmarkPage("benchmarkTable");
			benchmarkPage.verifyElementIsVisibleOfBenchmarkPage("companyDropdown");
			benchmarkPage.clickOnElementsOfBenchmarkPage("companyDropdown");
			benchmarkPage.clickOnElementsOfBenchmarkPage("companySearchBox");
			benchmarkPage.enterTextForBenchmarkPage("companySearchBox", getCredentials(environment, name));
			sleeper(5000);
			List<WebElement> element = benchmarkPage.getElementsTillAllElementsPresentBenchmarkPage("companyNameList");
			if (element.size() > 0) {
				for (WebElement we : element) {
					if (we.getText().equalsIgnoreCase(getCredentials(environment, name)) == true) {
						we.click();
						sleeper(2000);
						LOGGER.info("Company selection is successfull");
						break;
					}
				}
			}
			benchmarkPage.verifyElementIsVisibleOfBenchmarkPage("benchmarkTable");
		} else {
			LOGGER.info("Verifying global filter and selecting company");
			
			if (uiVersion.equalsIgnoreCase("VENEER2")) {
				Assert.assertTrue(benchmarkPage.verifyElementsOfBenchmarkPage("globalLocationFilter"),
						"Global location filter is not available on benchmark page");
				Assert.assertTrue(benchmarkPage.verifyElementsOfBenchmarkPage("globalLocationFilterDropdownBenchmark"),
						"Global location filter dropown is not available on benchmark page");

				// Clear global company filter if any
				if (benchmarkPage.waitForPresenceOfElementsOfBenchmarkPage("globalLocationFilterClearIconBenchmark")) {
					benchmarkPage.clickOnElementsOfBenchmarkPage("globalLocationFilterClearIconBenchmark");
					waitForPageLoaded();
					sleeper(2000);
				}

			}
			
			benchmarkPage.clickOnElementsOfBenchmarkPage("globalLocationFilterDropdownBenchmark");
			waitForPageLoaded();
			softAssert.assertTrue(
					benchmarkPage.getTextOfBenchmarkPage("filterMenuCompaniesHeader")
							.equals(benchmarkPage.getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.actions.filter.company.label")),
					"Location filter header does not match");
			sleeper(2000);
			benchmarkPage.clickOnElementsOfBenchmarkPage("filterMenuCompaniesSearch");
			benchmarkPage.enterTextForBenchmarkPage("filterMenuCompaniesSearch", getCredentials(environment, name));
			LOGGER.info("Search company to set location filter");
			waitForPageLoaded();
			sleeper(2000);
			benchmarkPage.waitForElementsOfBenchmarkPage("selectCompanyOnSearch");
			softAssert.assertTrue(
					benchmarkPage.matchTextOfBenchmarkPage("selectCompanyOnSearch", getCredentials(environment, name)),
					"Company name and search result company name does not match");
			benchmarkPage.clickOnElementsOfBenchmarkPage("selectCompanyOnSearch");
			benchmarkPage.clickOnElementsOfBenchmarkPage("applyCompanyLocationFilter");
			LOGGER.info("Company selected successfully");
			sleeper(2000);
			benchmarkPage.clickOnElementsOfBenchmarkPage("benchmarkTab");
			benchmarkPage.verifyElementIsVisibleOfBenchmarkPage("benchmarkTable");
		}
	}

	/**
	 * Method to impersonate company using email for Root Admin.
	 * 
	 */

	public final void impersonateCompanyByRootAdmin(String email) throws Exception {
		environment = System.getProperty("environment");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver());
		companiesPage = companiesPage.getInstance();
		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
		sleeper(3000);// wait is needed here
		resetTableConfiguration();
		sleeper(3000);// wait is needed here
		LOGGER.info("All the serach has been removed sucessfully.");
		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
		companiesPage.waitForElementsOfCompaniesPage("emailInCompanyTableForRoot1");
		companiesPage.clickOnElementsOfCompaniesPage("companysearchboxForRoot");
		companiesPage.enterTextForCompaniesPage("companysearchboxForRoot", getCredentials(environment, email));
		sleeper(3000);// wait is needed here
		companiesPage.waitForElementsOfCompaniesPage("emailInCompanyTableForRoot1");
		sleeper(3000);// wait is needed here
		Assert.assertTrue(companiesPage.matchTextOfCompaniesPage("emailInCompanyTableForRoot1", getCredentials(environment, email)), "company search is not correct");
		sleeper(3000);// wait is needed here
		companiesPage.clickOnElementsOfCompaniesPage("companySearchListForRoot1");
		sleeper(3000);// wait is needed here
		companiesPage.waitForElementsOfCompaniesPage("companyDetailsFormForRoot");
	}

	/**
	 * Method to impersonate company using company name for new Company UI for Root Admin. It will remove all the filters and search on company list page, and select given company
	 * name and redirected on company detail page.
	 * 
	 * @param email (While calling this method give parameter from ConstantURL class.)
	 * @throws Exception
	 */
	public final void impersonateCompanyByEmailForRootAdmin(String email) throws Exception {
		environment = System.getProperty("environment");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver());
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver());
		companiesPage = companiesPage.getInstance();
		sleeper(3000);
		resetTableConfiguration();
		LOGGER.info("All the serach has been removed successfully.");
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
		companiesPage.enterTextForCompaniesPage("companyEmailSearchForRoot", email);
		Assert.assertTrue(companiesPage.matchTextOfCompaniesPage("emailInCompanyTableForRoot", email), "company search is not correct");
		String companyNmae = companiesPage.getTextOfCompaniesPage("companySearchListForRootCompany");
		sleeper(1000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
		companiesPage.clickOnElementsOfCompaniesPage("companySearchListForRootClick");
		companiesPage.waitForElementsOfCompaniesPage("companyDetailsFormForRoot");
		Assert.assertTrue(companyNmae.equals(companiesDetailsPage.getTextOfCompaniesDetailsPage("companyDetailsFormForRoot")), "Company name is not matched on company details page.");
		LOGGER.info("Valid company name is present on company detail page.");
	}

	/**
	 * Method to impersonate company using name for workflow
	 * 
	 * @param name : name of the company to be impersonated
	 * @throws Exception
	 */
	public final void impersonateCompanyByNameWorkflow(String name) throws Exception {
		CompaniesPage companiespage = new CompaniesPage(PreDefinedActions.getDriver());
		companiespage = companiespage.getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		tableConfigurationPage = tableConfigurationPage.getInstance();
		gotoCompaniesTab();
		companiespage.waitForPresenceOfElementsOfCompaniesPage("searchedCompanyWorkflow");
		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter")) {
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
			sleeper(5000);// Needed else script fails
			companiespage.waitForPresenceOfElementsOfCompaniesPage("searchedCompanyWorkflow");
		}
		companiespage.enterTextForCompaniesPage("nameColumn", name);
		sleeper(5000);// Needed else script fails
		Assert.assertTrue(companiespage.matchTextOfCompaniesPage("searchedCompanyWorkflow", name), "Company search is not correct");
		companiespage.clickOnElementsOfCompaniesPage("searchedCompanyWorkflow");
		companiespage.waitForElementsOfCompaniesPage("companyDetailsFormWorkflow");
	}

	/**
	 * Method to impersonate workflow company using email for Root Admin.
	 * 
	 */

	public final void impersonateWorkflowCompanyByRootAdmin(String email) throws Exception {
		environment = System.getProperty("environment");
		CompaniesPage companiespage = new CompaniesPage(PreDefinedActions.getDriver());
		companiespage = companiespage.getInstance();
		gotoRootCompaniesTab();
		companiespage.waitForElementsOfCompaniesPage("nameColumn");
		resetTableConfiguration();
		companiespage.waitForElementsOfCompaniesPage("nameColumn");
		companiespage.enterTextForCompaniesPage("companyEmailSearchForRoot", getCredentials(environment, email));
		sleeper(5000);// For elastic search to take effect
		Assert.assertTrue(companiespage.matchTextOfCompaniesPage("primaryAdminEmailInCompanyTableForRoot", getCredentials(environment, email)), "company search is not correct");
		companiespage.clickOnElementsOfCompaniesPage("companySearchListForRootClickByName");
		companiespage.waitForElementsOfCompaniesPage("companyDetailsFormForRoot");
	}


	/*
	 * This method reads data from json
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public final List<JSONObject> readJsonFromUrl(String key) throws Exception {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		String jsonText = getJson(httpClient, key);
		JSONObject json;
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		if (key.equals("MACSOFTWAREJSONDATA")) {
			json = XML.toJSONObject(jsonText);
			jsonValues.add(json.getJSONObject("rss").getJSONObject("channel").getJSONObject("item").getJSONObject("enclosure"));
		} else if (key.equals("WINDOWSSOFTWAREJSONDATA")) {
			json = new JSONObject(jsonText);
			JSONArray softwareArray = (JSONArray) json.get("data");
			for (int i = 0; i < softwareArray.length(); i++) {
				JSONObject softwareData = softwareArray.getJSONObject(i);
				if (softwareData.get("appName").equals("TM-Client"))
					jsonValues.add(softwareArray.getJSONObject(i));

			}
			Collections.sort(jsonValues, new Comparator<JSONObject>() {
				// You can change "Name" with "ID" if you want to sort by ID
				private static final String KEY_NAME = "appVersion";

				@Override
				public int compare(JSONObject a, JSONObject b) {
					String valA = new String();
					String valB = new String();

					valA = (String) a.get(KEY_NAME);
					valB = (String) b.get(KEY_NAME);

					return -valA.compareTo(valB);
					// if you want to change the sort order, simply use the following:
					// return -valA.compareTo(valB);
				}
			});

		}

		return jsonValues;
	}

	public final String getJson(CloseableHttpClient httpClient, String key) throws Exception {
		StringBuilder sb = new StringBuilder();
		try {

			HttpGet getRequest = new HttpGet(getEnvironmentSpecificData(System.getProperty("environment"), key));
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		httpClient.close();
		return sb.toString();
	}

	/**
	 * Method to go setting page of report admin user
	 * 
	 */
	public final void gotoCompanySettingsTab() throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver());
		settingsPage = settingsPage.getInstance();
		expandMenuIcon();
		// settingsPage.clickByJavaScriptOnSettingsPage("reportAdminSettingsTab");
		settingsPage.clickOnElementsOfSettingsPage("reportAdminSettingsTab");
	}

	/**
	 * This method returns the child company names from details api of report page for workflow
	 * 
	 * @param key - parameter you want read from json file in . format
	 * @param api - URL from you which you want the data
	 * @param body - request body
	 * @param mspAuth - authentication token
	 * @param label - value to access
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final List<String> getDataFromApiPostwf(String key, String api, String body, String mspAuth, String label) {
		try {
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuth).body(body);
			Response response = httpRequest.post(api);
			String expected = response.asString();
			JSONObject jsonObject;
			jsonObject = new JSONObject(expected);
			List<Map<String, Object>> list = new Gson().fromJson(jsonObject.get(key).toString(), List.class);
			Object s = list.get(0).get("filters");
			List<Map<String, Object>> list1 = (List<Map<String, Object>>) s;
			Optional<Map<String, Object>> mapOption = list1.stream().filter(obj -> {
				return label.equals(obj.get("label"));
			}).findFirst();
			Map map = mapOption.orElse(null);
			if (map != null) {
				return (List) map.get("values");
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getDataFromApiWF " + e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * method to go to Administrator User page
	 */
	public final void gotoAdministratorUserTab() throws Exception {
		try {
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIcon2();
			if (!userPage.getAttributesOfUserPage("administratorTab", "class").contains("expanded")) {
				userPage.clickOnElementsOfUserPage("administratorTab");
				LOGGER.info("Clicked on administrators tab");
			} else {
				LOGGER.info("Administrators menu already expanded");
			}
			userPage.clickOnElementsOfUserPage("userRootTab");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoAdministratorUserTab " + e.getMessage()));
		}
	}

	/**
	 * 
	 * method to go to Administrator order page
	 */
	public final void gotoOrderTab() throws Exception {
		try {
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
//			expandMenuIcon2();
			if(!userPage.waitForElementsOfUserPage("orderTab")){
				userPage.clickByJavaScriptOnUserPage("administratorTab");
				LOGGER.info("Clicked on administrators tab");
			}
//			if (!userPage.getAttributesOfUserPage("administratorTab", "class").contains("expanded")) {
//				userPage.clickOnElementsOfUserPage("administratorTab");
//				LOGGER.info("Clicked on administrators tab");
//			} else {
//				LOGGER.info("Administrators menu already expanded");
//			}
			userPage.clickOnElementsOfUserPage("orderTab");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoOrderTab " + e.getMessage()));
		}
	}

	/**
	 * method to go to Administrator Subscription Page page
	 */
	public final void gotoAdministratorSubscriptionTab() throws Exception {
		try {
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIconRoot();
			if (!userPage.getAttributesOfUserPage("administratorTab", "class").contains("expanded")) {
				userPage.clickOnElementsOfUserPage("administratorTab");
				LOGGER.info("Clicked on administrators tab");
			} else {
				LOGGER.info("Administrators menu already expanded");
			}
			userPage.clickOnElementsOfUserPage("subscriptionManagementTab");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoAdministratorSubscriptionTab " + e.getMessage()));
		}
	}

	/**
	 * method to get html parser for email
	 * 
	 * @return
	 */
	public final String getHtmlParser(String mailContent) throws Exception {

		String actualMailContent;
		InputStream in = org.apache.commons.io.IOUtils.toInputStream(mailContent, "UTF-8");
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext pcontext = new ParseContext();
		HtmlParser htmlparser = new HtmlParser();
		htmlparser.parse(in, handler, metadata, pcontext);
		actualMailContent = handler.toString().replaceAll("\\s{2,}", " ").trim();
		return actualMailContent;
	}

	/**
	 * This is the method to verify successful user impersonation
	 * 
	 * @param userRole - Role of user which is impersonated
	 *
	 */
	public final void verifyUserImpersonation(String userRole, String userEmail, String languageCode) {
		try {
			SiteUpTest siteUpTest = new SiteUpTest();
			switch (userRole) {
			case UserVariables.MANAGED_SERVICE_PROVIDER_ADMINISTRATOR:
				siteUpTest.managedServiceProviderSiteUpTest(languageCode,userEmail);
				break;

			case UserVariables.SUPPORT_SPECIALIST:
				siteUpTest.commonSiteUp(languageCode,userEmail);
				siteUpTest.supportSpecialistSiteUpTest(languageCode,userEmail);
				break;

			case UserVariables.PARTNER_ADMINISTRATOR:
				siteUpTest.commonSiteUp(languageCode,userEmail);
				siteUpTest.partnerAdministratorSiteUpTest(languageCode,userEmail);
				break;

			case UserVariables.SALES_SPECIALIST:
				siteUpTest.commonSiteUp(languageCode,userEmail);
				siteUpTest.salesSpecialistSiteUpTest(languageCode,userEmail);
				break;

			case UserVariables.REPORT_ADMINISTRATOR:
				siteUpTest.commonSiteUp(languageCode,userEmail);
				siteUpTest.reportAdministratorSiteUpTest(languageCode,userEmail);
				break;

			case UserVariables.IT_ADMINISTRATOR:
				siteUpTest.commonSiteUp(languageCode,userEmail);
				siteUpTest.itAdministratorSiteUpTest(languageCode,userEmail);
				break;

			case UserVariables.EMM_ADMINISTRATOR:
				siteUpTest.snowAdministratorAndMicrosoftTelemetryAdministratorSiteUpTest(languageCode,userEmail);
				break;

			case UserVariables.MICROSOFT_TELEMETRY_ADMINISTRATOR:
				siteUpTest.snowAdministratorAndMicrosoftTelemetryAdministratorSiteUpTest(languageCode,userEmail);
				break;

			case UserVariables.SERVICE_NOW_ADMINISTRATOR:
				siteUpTest.snowAdministratorAndMicrosoftTelemetryAdministratorSiteUpTest(languageCode,userEmail);

			default:
				LOGGER.info("Provided role is incorrect");

			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyUserImpersonation " + e.getMessage()));
		}
	}

	public final void expandSideGroupMenu(String menuGroup) throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver());
		settingsPage = settingsPage.getInstance();
		if (settingsPage.waitForElementsOfSettingsPageDynamic("myOrganizationGroupTab", 1) || settingsPage.waitForElementsOfSettingsPageDynamic("customersGroupTab", 1)) {

			if (menuGroup.equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.myOrganization"))) {
				if(!settingsPage.verifyElementsOfSettingsPage("myOrganizationGroupTabExpanded")){
					settingsPage.clickByJavaScriptOnSettingsPage("myOrganizationGroupTabLink");
				}else {
					LOGGER.info("'" + menuGroup + "' Tab menu already expanded");
				}
			} else if (menuGroup.equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"))) {
				if(!settingsPage.verifyElementsOfSettingsPage("customersGroupTabExpanded")){
					settingsPage.clickByJavaScriptOnSettingsPage("customersGroupTabLink");
				}else {
					LOGGER.info("'" + menuGroup + "' Tab menu already expanded");
				}
			} else {
				LOGGER.error("'" + menuGroup + "' is Invalid.Enter valid Side Menu Group.");
			}
		} else {
			LOGGER.info("No grouping of side menu is present for existing role.");
		}
	}

	/**
	 * Method to go to incident page of Company User
	 * 
	 */
	public final void gotoIncidentCompanyUserTab() throws Exception {
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		// incidentPage.clickByJavaScriptOnIncidentPage("incidentTab");
		incidentPage.clickOnElementsOfIncidentPage("incidentTab");
	}

	/**
	 * Method to go to user page of Company User
	 * 
	 */
	public final void gotoUserCompanyUserTab() throws Exception {
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		// userPage.clickByJavaScriptOnUserPage("userTab");
		userPage.clickOnElementsOfUserPage("userTab");
	}

	/**
	 * Method to go to log page of Company User
	 * 
	 */
	public final void gotoLogCompanyUserTab() throws Exception {
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		// logPage.clickByJavaScriptOnLogPage("logTab");
		logPage.clickOnElementsOfLogPage("logTab");
	}

	/**
	 * Method to go to non msp setting page of Company User
	 * 
	 */
	public final void gotoNonMSPSettingsCompanyUserTab() throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		// settingsPage.clickByJavaScriptOnSettingsPage("settingsTabNonMSP");
		settingsPage.clickOnElementsOfSettingsPage("settingsTabNonMSP");
	}

	/**
	 * Method to go to devices page of Company User
	 * 
	 */
	public final void gotoDevicesCompanyUserTab() throws Exception {
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		// deviceListPage.clickByJavaScriptOnDeviceListPage("devicesTab");
		deviceListPage.clickOnElementsOfDeviceListPage("devicesTab");
	}

	/**
	 * Method to go to report page of Company User
	 * 
	 */
	public final void gotoReportCompanyUserTab() throws Exception {
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		// AnalyticsPage.clickByJavaScriptOnAnalyticsPage("reportTab");
		analyticsPage.clickOnElementsOfAnalyticsPage("reportTab");
	}

	/**
	 * This method will remove all pending invitation
	 * 
	 * @param environment
	 * @return
	 * @throws Exception
	 */
	public final boolean removeAllPendingInvitations(String environment, String tenantID, String environmentURL) {
		try {
			boolean flag = false;
			List<String> listOfUUIDs = getAllInvitations(environmentURL + ConstantURL.GET_API_INVITATION + tenantID + ConstantURL.GET_API_INVITATION_SECOND, "SENT");
			if (listOfUUIDs.size() > 0) {
				for (int i = 0; i < listOfUUIDs.size(); i++) {
					int code = getStatusCode(environmentURL + ConstantURL.DELETE_API_INVITATION + listOfUUIDs.get(i), "", "DELETE", environment);
					if (code != CommonVariables.CODESUCCESS) {
						flag = false;
						LOGGER.error("Delete API got failed while removing invitation.");
						break;
					}
					flag = true;
				}
			} else {
				LOGGER.info("There are no pending invitations present.");
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeAllPendingInvitations: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method will remove all pending invitation recieved from partner
	 * @param environment
	 * @return
	 * @throws Exception
	 */
	public final boolean removeAllReceievedPendingInvitations(String environment, String tenantID, String environmentURL) {
		try {
			boolean flag = false;
			List<String> listOfUUIDs = getAllInvitations(environmentURL + ConstantURL.GET_API_INVITATION + tenantID + ConstantURL.GET_API_INVITATION_SECOND , "RECEIVED");
			if (listOfUUIDs.size() > 0) {
				for (int i = 0; i < listOfUUIDs.size(); i++) {
					int code = getStatusCode(environmentURL + ConstantURL.DELETE_API_INVITATION + listOfUUIDs.get(i) + ConstantURL.DELETE_API_RECIEVED_INVITATION, "", "PATCH", environment);
					if (code != CommonVariables.CODEOK) {
						flag = false;
						LOGGER.error("Delete API got failed while removing receivedinvitation.");
						break;
					}
					flag = true;
				}
			} else {
				LOGGER.info("There are no pending invitations present.");
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeAllPendingInvitations: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to read api data for the invitations
	 * 
	 * @param api - URL from you which you want the data
	 * @param associationReqType - Invitation request type which you want to delete
	 */
	public final List<String> getAllInvitations(String api, String associationReqType) {
		try {
			List<String> listOfUUIDs = new ArrayList<String>();
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken);
			Response response = httpRequest.get(api);
			String expected = response.asString();
			JSONObject obj;
			JSONArray hitsArray;
			hitsArray = new JSONArray(expected);
			for (int i = 0; i < hitsArray.length(); i++) {
				obj = hitsArray.getJSONObject(i);
				if (obj.getString("status").equals("PENDING") && obj.getString("assoReqType").equals(associationReqType)) {
					listOfUUIDs.add(obj.getString("uuid").toString());
				}
			}
			return listOfUUIDs;
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllInvitations: " + e.getMessage());
			return null;
		}
	}

	/**
	 * This method verifies email content
	 * 
	 *
	 * @param environment - environment
	 * @param inboxEmailAddress - email address of user who receives the mail
	 * @param incidentID - subject of the mail
	 * @param invitationString - subject of the mail
	 * @param privateDomain - domain
	 * @return - actual mail content
	 * @throws Exception
	 */
	public final String verifyEmailContent(String environment, String inboxEmailAddress, String incidentID, String invitationString, boolean privateDomain) throws Exception {
		String mailContent;
		Mailinator objMailinator = new Mailinator("", getCredentials(environment, inboxEmailAddress).split("@")[0]);
		sleeper(5000);
		MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(getEnvironmentSpecificData(System.getProperty("environment"), incidentID) + invitationString, getCredentials(environment, inboxEmailAddress).split("@")[0], privateDomain);
		if (objMailinatorEmail != null) {
			mailContent = objMailinatorEmail.getBody();
			return getHtmlParser(mailContent);
		} else {
			LOGGER.error("Mail not found");
			return "";

		}

	}

	/**
	 * This method unassigns partner
	 * 
	 *
	 */
	public final boolean unassignPartner(String environment) throws Exception {
		boolean flag = false;
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		if (!companyDetailsPage.getTextOfCompaniesDetailsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editAssignedpartnerButton");
			companyDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("unassignPartnerButtonCompanyDetailsPage");
			LOGGER.info("Partner unassigned");
			companyDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
			flag = true;
			return flag;
		} else {
			LOGGER.info("Partner not assigned");
			flag = true;
			return true;
		}

	}

	/**
	 * Method to go to report page for workflow
	 * 
	 */
	public final void gotoReportTabWorkflow() throws Exception {
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver());
		analyticsPage = analyticsPage.getInstance();
		expandMenuIcon();
		analyticsPage.clickByJavaScriptOnAnalyticsPage("reportTabWorkflow");
	}

	/**
	 * Method to go to log page for workflow
	 * 
	 */
	public final void gotoLogTabWorkflow() throws Exception {
		LogPage logPage = new LogPage(PreDefinedActions.getDriver());
		logPage = logPage.getInstance();
		expandMenuIcon();
		logPage.clickByJavaScriptOnLogPage("logTabWorkflow");
	}

	/**
	 * Method to go to devices page for workflow
	 * 
	 */
	public final void gotoDevicesTabWorkflow() throws Exception {
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver());
		deviceListPage = deviceListPage.getInstance();
		expandMenuIcon();
		deviceListPage.clickByJavaScriptOnDeviceListPage("devicesTabWorkflow");
		sleeper(3000);
	}

	/**
	 * Method to go to dashboard page for workflow
	 * 
	 */
	public final void gotoDashboardTabWorkflow() throws Exception {
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
		dashboardPage = dashboardPage.getInstance();
		expandMenuIcon();
		dashboardPage.clickByJavaScriptOnDashboardPage("dashboardTabWorkflow");
	}

	/**
	 * This method is used to get tenant ID of company.
	 * 
	 * @param api : API url
	 * @param body : body
	 * @param companyName: name of company
	 * @return
	 */
	public final String getTenantDetails(String api, String body, String companyName) {
		try {
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.post(api);
			String expected = response.asString();
			JSONObject jsonObject1, jsonObject2, jsonObject3, jsonObject4;
			jsonObject1 = new JSONObject(expected);
			jsonObject2 = jsonObject1.getJSONObject("hits");
			JSONArray jsonArray = jsonObject2.getJSONArray("hits");
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject3 = new JSONObject(jsonArray.get(i).toString());
				jsonObject4 = (JSONObject) new JSONObject(jsonObject3.toString()).get("_source");
				mapTenantIdDetails.put(new JSONObject(jsonObject4.toString()).get("displayName").toString(), new JSONObject(jsonObject4.toString()).get("tenantId").toString());
			}
			LOGGER.info("Tenant ID of " + companyName + " is fetched:" + mapTenantIdDetails.get(companyName));
			return mapTenantIdDetails.get(companyName);
		} catch (Exception e) {
			LOGGER.error("Exception occured in getTenantDetails: " + e.getMessage());
			return null;
		}
	}

	/**
	 * This is the method to get necessary pagination information for any page
	 * 
	 * @throws Exception
	 */
	public void getPaginationInfo() throws Exception {
		PaginationPage paginationPage = new PaginationPage(PreDefinedActions.getDriver()).getInstance();
		try {
			activepageNumber = Integer.parseInt(paginationPage.getTextOfPaginationPage("navigationItemActivePage"));
			previousButtonStatus = paginationPage.getButtonEnabilityStatus("navigationItemPrevious");
			nextButtonStatus = paginationPage.getButtonEnabilityStatus("navigationItemNext");
			totalCount = paginationPage.getTotalRecordCount("showingPaginationTotalCount");
			
			// This condition basically check till perform click action until disable pagination next button 
			if(paginationPage.getButtonEnabilityStatus("navigationItemNext")){
				do {
					paginationPage.clickOnElementsOfPaginationPage("navigationItemNext");
				}while(paginationPage.getButtonEnabilityStatus("navigationItemNext"));
				
			}
			lastPageNumber = Integer.parseInt(paginationPage.getTextOfPaginationPage("navigationItemLast"));
			firstPageNumber = Integer.parseInt(paginationPage.getTextOfPaginationPage("navigationItemFirst"));
			selectedOption = paginationPage.getSelectedOptionTextofPaginationCompanyPage("paginationDropdownMenu", "paginationDropdownOptionListText");
		} catch (Exception e) {
			Assert.fail("Exception occured in method getPaginationInfo " + e.getMessage());
		}
	}

	/**
	 * This is the method to verify pagination on list pages
	 *
	 * @throws Exception
	 */
	public final void verifyPaginationOnListPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		//resetTableConfiguration();
		PaginationPage paginationPage = new PaginationPage(PreDefinedActions.getDriver()).getInstance();
		paginationPage.waitUntilElementIsInvisibleOfPaginationPage("tableOverlay");
		Assert.assertFalse(paginationPage.verifyElementsOfPaginationPage("noElementsDisplayText"), "No records are present on this page, unable to proceed further");
		LOGGER.info("Atleast one record is present, test case started");
		// Verified if the pagination dropdown menu is enabled and clickable
		softAssert.assertTrue(paginationPage.verifyElementIsEnableOfPaginationPage("paginationDropdownMenu"), "Pagination Dropdown not available");
		softAssert.assertTrue(paginationPage.verifyElementIsClickableOfPaginationPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
		// Verified if the next link, previous link and navigation links are available
		softAssert.assertTrue(paginationPage.waitForElementsOfPaginationPage("navigationItemDiv"), "Navigation Items are not available");
		// Get the information related to current pagination count
		getPaginationInfo();
		LOGGER.info("get Pagination Information ");
		softAssert.assertTrue(paginationPage.verifyElementIsEnableOfPaginationPage("navigationItemActivePage"), "The active page navigation link is not disabled");
		// Verifying if the total page count is as per selected option
		//softAssert.assertTrue(paginationPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
		// Verified enability status of navigation links w.r.t current total page count
		softAssert.assertTrue(paginationPage.verifyButtonStatusofPagination(activepageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
		// Change the current page count to 50
		if (changeSelectedOption(totalCount, 50)) {
			paginationPage.selectElementFromDropDownOfPaginationPage("paginationDropdownMenu", "paginationDropdownOptionList", "50");
			LOGGER.info("Change Selected option as 50");
			// Verifying if the pagination count is changed to 50
			//softAssert.assertTrue(paginationPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
			// Verified enability status of navigation links w.r.t current pagination count(i.e 50)
			softAssert.assertTrue(paginationPage.verifyButtonStatusofPagination(activepageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
		} else {
			LOGGER.info("Selected user has less than 50 records");
		}
		paginationPage.waitUntilElementIsInvisibleOfPaginationPage("tableOverlay");
		// Get the information related to current pagination count
		getPaginationInfo();
		// Verified if the next link is clickable
		if (paginationPage.canClickNext(activepageNumber, lastPageNumber, nextButtonStatus)) {
			softAssert.assertTrue(paginationPage.getButtonEnabilityStatus("navigationItemNext"), "Navigation next button is not enable");
			// Click on the next link
			paginationPage.clickOnElementsOfPaginationPage("navigationItemNext");
			// Get the information related to current pagination count
			paginationPage.waitUntilElementIsInvisibleOfPaginationPage("tableOverlay");
			getPaginationInfo();
			// Verified if the previous link is clickable
			if (paginationPage.canClickPrevious(activepageNumber, firstPageNumber, previousButtonStatus)) {
				softAssert.assertTrue(paginationPage.getButtonEnabilityStatus("navigationItemPrevious"), "Navigation previous button is not enable");
				sleeper(2000);
				// Click on the previous link
				paginationPage.clickOnElementsOfPaginationPage("navigationItemPrevious");
			} else {
				LOGGER.info("Previous button is disabled");
			}

			// Verified if the previous link is clickable
		} else if (paginationPage.canClickPrevious(activepageNumber, firstPageNumber, previousButtonStatus)) {
			softAssert.assertTrue(paginationPage.getButtonEnabilityStatus("navigationItemPrevious"), "Navigation previous button is not enable");
			// Click on the previous link
			paginationPage.clickOnElementsOfPaginationPage("navigationItemPrevious");
			paginationPage.waitUntilElementIsInvisibleOfPaginationPage("tableOverlay");
			// Get the information related to current pagination count
			getPaginationInfo();
			activepageNumber = Integer.parseInt(paginationPage.getTextOfPaginationPage("navigationItemActivePage"));
			// Verified if the next link is clickable
			if (paginationPage.canClickNext(activepageNumber, lastPageNumber, nextButtonStatus)) {
				softAssert.assertTrue(paginationPage.getButtonEnabilityStatus("navigationItemNext"), "Navigation next button is not enable");
				// Click on the next link
				paginationPage.clickOnElementsOfPaginationPage("navigationItemNext");
				paginationPage.waitUntilElementIsInvisibleOfPaginationPage("tableOverlay");
			} else {
				LOGGER.info("Next button is disabled");
			}
		} else {
			LOGGER.info("Previous and Next button both are disabled");
		}
		softAssert.assertAll();
		LOGGER.info("Verification of pagination functionality done successfully");
	}

	/**
	 * Method to go to Root setting page
	 * 
	 */
	public final void gotoRootSettingsTab() throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver());
		settingsPage = settingsPage.getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.myOrganization"));
		settingsPage.clickOnElementsOfSettingsPage("settingsTab");
	}

	/**
	 * Method to go to Admin Root setting page
	 * 
	 */
	public final void gotoAdminSettingsTab() throws Exception {
		try {
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIconRoot();
			if (!userPage.getAttributesOfUserPage("administratorTab", "class").contains("expanded")) {
				userPage.clickOnElementsOfUserPage("administratorTab");
				LOGGER.info("Clicked on administrators tab");
			} else {
				LOGGER.info("Administrators menu already expanded");
			}
			userPage.clickOnElementsOfUserPage("settingsRootTab");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoAdminSettingsTab " + e.getMessage()));
		}
	}

	/**
	 * Method to go to Subscriptions page
	 * 
	 * @throws Exception
	 */
	public final void gotoSubscriptionsTab() throws Exception {
		LicensesListPage subscriptionsListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		subscriptionsListPage.clickOnElementsOfSubscriptionsListPage("subscriptionsTab");
	}

	/**
	 * Method to go to Campaigns page
	 *
	 * @throws Exception
	 */
	public final void gotoCampaignsTab() throws Exception {
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"));
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignsTab");
	}
	
	/**
	 * This method is to get value of Partner/MSP/Company ID
	 * @param tenantID - ID of tenant
	 * @param env -environment
	 * @return String value for given key
	 * @throws Exception
	 */
	public final String getIDOverviewTab(String tenantID, String env) throws Exception {
		String apiURL = null, value = null;
		try {
			apiURL = env + ConstantURL.ADD_API_USER + tenantID;
			int code = getStatusCode(apiURL, "{}", "GET", env);
			if (code != CommonVariables.UNAUTHORISEDERROR) {
				value = getDataFromApi("displayId", apiURL);
				if(value == null)
					value = "-";
			} else {
				apiURL = env + ConstantURL.DELETE_API_INVITATION + tenantID;
				value = getDataFromApi("displayId", apiURL);
			}
			return value;
		} catch (Exception e) {
			LOGGER.error("Exception occured in getValueOfCustomerID: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Select Multi-level locations hierarchy for Multi-Tenancy Feature
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public final boolean selectLocationHierarchyForRPOS(String email) throws Exception {
		String globalFilterLocation1 = getEnvironmentSpecificData(System.getProperty("environment"), "MultiTenancy_LOCATION1_DO_NOT_DELETE");
		String globalFilterLocation2 = getEnvironmentSpecificData(System.getProperty("environment"), "MultiTenancy_LOCATION2_DO_NOT_DELETE");
		String globalFilterLocation3 = getEnvironmentSpecificData(System.getProperty("environment"), "MultiTenancy_LOCATION3_DO_NOT_DELETE");
		String globalFilterLocation4 = getEnvironmentSpecificData(System.getProperty("environment"), "MultiTenancy_LOCATION4_DO_NOT_DELETE");
		boolean multiLevelLocation = false;
		SoftAssert softAssert = new SoftAssert();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver());
		analyticsPage = analyticsPage.getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, email))) {
			LOGGER.info("Verifying Global Multi-Level locations hierarchy Filter");
			analyticsPage.clickOnElementsOfAnalyticsPage("globalLocationFilterDropdownReports");
			waitForPageLoaded();

			softAssert.assertTrue(analyticsPage.getTextOfAnalyticsPage("FilterMenuLocationLevel1Header").equals((analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.location") + " 1")), "Location filter header does not match");

			if (analyticsPage.verifyElementIsVisibleOfAnalyticsPage("FilterMenuLocationLevel1Header")) {
				analyticsPage.enterTextForAnalyticsPage("filterLocation1Search", globalFilterLocation1);
				LOGGER.info("Search location1  to set Location Level 1 filter");
				waitForPageLoaded();
				sleeper(3000);
				if (analyticsPage.verifyElementsOfAnalyticsPage("location1OnListSearch")) {
					analyticsPage.clickOnElementsOfAnalyticsPage("location1OnListSearch");
					LOGGER.info("Location-1 is selected for Location Level-1");
					sleeper(1000);
				} else {
					LOGGER.info("Location is not present in search list of Location Level-1");
				}
			}else {LOGGER.info("Location-1 filter is not available ");
				analyticsPage.clickOnElementsOfAnalyticsPage("clearGlobalFilter");
			}
			if (analyticsPage.verifyElementIsVisibleOfAnalyticsPage("FilterMenuLocationLevel2Header")) {
				analyticsPage.enterTextForAnalyticsPage("filterLocation2Search", globalFilterLocation2);
				LOGGER.info("Search location2  to set Location Level 2 filter");
				waitForPageLoaded();
				sleeper(3000);
				if (analyticsPage.verifyElementsOfAnalyticsPage("location2OnListSearch")) {
					analyticsPage.clickOnElementsOfAnalyticsPage("location2OnListSearch");
					LOGGER.info("Location-2 is selected for Location Level-2");
					sleeper(1000);
				} else {
					LOGGER.info("Location is not present in search list of Location Level-2");
				}
			}
			if (analyticsPage.verifyElementIsVisibleOfAnalyticsPage("FilterMenuLocationLevel3Header")) {
				analyticsPage.enterTextForAnalyticsPage("filterLocation3Search", globalFilterLocation3);
				LOGGER.info("Search location3  to set Location Level 3 filter");
				waitForPageLoaded();
				sleeper(3000);
				if (analyticsPage.verifyElementsOfAnalyticsPage("location3OnListSearch")){
					analyticsPage.clickOnElementsOfAnalyticsPage("location3OnListSearch");
					LOGGER.info("Location-3 is selected for Location Level-3");
					sleeper(1000);
				} else {
					LOGGER.info("Location is not present in search list of Location Level-3");
				}
			}
			if (analyticsPage.verifyElementIsVisibleOfAnalyticsPage("FilterMenuLocationLevel4Header")) {
				analyticsPage.enterTextForAnalyticsPage("filterLocation4Search", globalFilterLocation4);
				LOGGER.info("Search location4  to set Location Level 4 filter");
				waitForPageLoaded();
				sleeper(3000);
				if (analyticsPage.verifyElementsOfAnalyticsPage("location4OnListSearch")){
					analyticsPage.clickOnElementsOfAnalyticsPage("location4OnListSearch");
					LOGGER.info("Location-4 is selected for Location Level-4");
				} else {
					LOGGER.info("Location is not present in search list of Location Level-4");
				}
			}
			analyticsPage.clickOnElementsOfAnalyticsPage("applyCompanyLocationFilter");
			LOGGER.info("Clicked on Apply button");
			LOGGER.info("Multi-Level Hierarchy location  selected successfully");
			sleeper(3000);
			multiLevelLocation = true;
		} else {
			LOGGER.info("Launch Darkly Toggle is off for the user ");
			multiLevelLocation = false;
		}
		return  multiLevelLocation;
	}
	
	/** This method is used to accept pop up before starting of each and every test case.
	 * 
	 */
	public boolean acceptCookiesConsent() {
		try {
			WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Entering acceptCookiesConsent method");
			if (attemptToAcceptCookies()) {
				return true;
			}
			deleteAllcookies();
			refreshPage();
			return attemptToAcceptCookies();
		} catch (Exception e) {
			LOGGER.error("Error while accepting cookies consent: {}", e.getMessage());
			return false;
		}
	}

	private boolean attemptToAcceptCookies() throws Exception{
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Attempting to accept cookies consent");
		if (welcomePage.waitForElementsOfWelcomePageDynamic("cookieAccept", 5)) {
			welcomePage.clickOnElementsOfWelcomePage("cookieAccept");
			LOGGER.info("Clicked on accept cookies consent");
			return true;
		}
		LOGGER.error("Cookies pop-up is not displayed.");
		return false;
	}
	
	/**
	 * Method to go to Benchmark page
	 * 
	 * @throws Exception
	 */
	public final void gotoBenchmarkPage() throws Exception {
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"));
		benchmarkPage.clickOnElementsOfBenchmarkPage("benchmarkTab");
		if (benchmarkPage.verifyElementIsVisibleOfBenchmarkPage("benchmarkPage"))
			LOGGER.info("Navigated to page " + benchmarkPage.getTextOfBenchmarkPage("benchmarkTab"));
		else
			LOGGER.info("Failed navigate to page " + benchmarkPage.getTextOfBenchmarkPage("benchmarkTab"));
	}
	
	/**
	 * method to go to MSP User list page 
	 * @throws Exception
	 */
	public final void gotoMSPUserTab() throws Exception {
		try {
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIconRoot();
			if (!partnerPage.getAttributesOfPartnerPage("mspTab", "class").contains("expanded")) {
				partnerPage.clickOnElementsOfPartnerPage("mspTab");
				LOGGER.info("Clicked on MSP tab");
			} else {
				LOGGER.info("MSPs menu already expanded");
			}
			partnerPage.clickOnElementsOfPartnerPage("mspUsersTab");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoMSPUserTab " + e.getMessage()));
		}
	}
	
	/**
	 * @param tenantID - tenant id of a company which is to be removed
	 * @return - boolean
	 * @throws Exception
	 */
	public final boolean removeCompanyUsingAPI(String tenantID) throws Exception {
		Response response = null;
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");

		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON)
				.header("authorization", "Bearer " + mspAuthToken).body("{}");
		response = httpRequest.delete(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH")
				+ CompaniesVariables.REMOVE_COMPANY_URL + tenantID + CompaniesVariables.REMOVE_COMPANY_URL2);

		if (response.getStatusCode() == 204) {
			return true;
		} else {
			LOGGER.error("Company deletion failed due to reason " + response.asString());
			return false;
		}
	}

		
	/**
	 * This method is used for file import for veneer version 3
	 * @param fileName this is the name of file which was imported
	 * @throws Exception 
	 */
	public void fileImportInV3(String fileName) throws Exception {
		SettingsPage settingPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();;
		sleeper(3000);
		WebElement addFile = settingPage.getElementOfSettingsPage("uploadBrowseInput");
	    addFile.sendKeys(fileName);
	    sleeper(3000);
	}
	
	/**
	 * Method to go to self service page
	 * 
	 */
	public final void gotoSelfServiceTab() throws Exception {
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver());
		analyticsPage = analyticsPage.getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"));
		analyticsPage.clickOnElementsOfAnalyticsPage("selfServiceTab");
	}
	
	public final void impersonateCompanyByNameDashboard(String name, String email) throws Exception {
		environment = System.getProperty("environment");
		boolean flag = false;
		if (!(environment.equalsIgnoreCase("PENTEST"))) {
			SoftAssert softAssert = new SoftAssert();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			gotoDashboardTab();
			LOGGER.info("Verifying global filter and selecting company");
			Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"),
					"Global location filter option is not available on Dashboard page");
			Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"),
					"Selection of global location filter is not available on Dashboard page");

				flag = dashboardPage.getTextOfDashboardPage("companyDropdownGlobalFilter")
						.equalsIgnoreCase(getCredentials(environment, name));
			
			if (flag == true) {
				LOGGER.info("Company is already selected");
				//dashboardPage.verifyElementIsVisible("customReportsCreatedList");
			} else {
				dashboardPage.waitUntilElementIsInvisibleOfDashboardPage("reportListPageSpinner");
				dashboardPage.clickOnElementsOfDashboardPage("globalLocationFilter");
				waitForPageLoaded();
				sleeper(1000);
				softAssert.assertTrue(
						dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader")
								.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui",
										"dashboard.actions.filter.company.label")),
						"Location filter header does not match");
				dashboardPage.clickOnElementsOfDashboardPage("filterMenuCompaniesSearch");
				sleeper(2000);
				dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", getCredentials(environment, name));
				LOGGER.info("Search company to set location filter");
				waitForPageLoaded();
				dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
				softAssert.assertTrue(
						dashboardPage.matchTextOfDashboardPage("companyOnListSearch", getCredentials(environment, name)),
						"Company name and search result company name does not match");
				dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
				dashboardPage.clickOnElementsOfDashboardPage("applyCompanyLocationFilter");
				// To click on blank area
//				if (uiVersion.equalsIgnoreCase("VENEER3")) {
//					dashboardPage.clickOnElementsOfDashboardPage("blankArea");
//				}
				sleeper(6000);
				LOGGER.info("Company selected successfully");
			}
		} else {
			gotoReportTab();
			LOGGER.info("Pentest Env No need for impersonation");
		}
	}
	
	/**
	 * Method to go to Rooms page
	 * 
	 */
	public final void gotoRoomsTab() throws Exception {
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver());
		RoomsListPage = RoomsListPage.getInstance();
		//expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"));
		RoomsListPage.clickByJavaScriptOnRoomsListPage("roomsTab");
		sleeper(2000);
	}
	
	/**
	 * Method to go to Rooms page
	 * 
	 */
	public final void gotoTasksTab() throws Exception {
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver());
		RoomsListPage = RoomsListPage.getInstance();
		//expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"));
		RoomsListPage.clickByJavaScriptOnRoomsListPage("tasksTab");
		sleeper(2000);
	}
	
	/**Method to verify the AV Recommended Actions section in Experience Management page
	 * @throws Exception
	 */
	public final void VerifyAVRecommendedActions() throws Exception {
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver());
		experienceMgmtPage = experienceMgmtPage.getInstance();
		experienceMgmtPage.getTextOfExperienceMgmtPage("recommendedActionTitle");
		
		if(!(experienceMgmtPage.verifyElementsOfExperienceMgmtPage("noDataOnRecommandationSection")))
		{
			List<String> RecommandedAction = experienceMgmtPage.getallTextOfExperienceMgmtPage("openRecommendedAction");
			for (String actionList:RecommandedAction) {
				if (actionList.equals(experienceMgmtPage.getTextLanguage(LanguageCode,"daas_ui","enable-antivirus-windows-defender"))) 
				{
					experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("detailsButtonAvEnable");
					sleeper(4000);
					LOGGER.info("Enable AV text is verified successfully.");
					gotoExperienceMgmtPageTab();
					experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetSecurityTab");
					waitForPageLoaded();
					experienceMgmtPage.scrollToExperienceMgmtPage("benchMarkSent");
				}			
				else if (actionList.equals (experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "security.update_signature_wind_defender")))
				{
					
					experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("detailsButtonAVSignature");
					sleeper(4000);
					LOGGER.info("Update Signature text is verified successfully.");
					gotoExperienceMgmtPageTab();
					experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetSecurityTab");
				}
				}
			
				}
	}
	
	/**
	 * Method to return system date
	 * 
	 */
	public final String getSystemDate() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:");
		Date date = new Date();
        String date1= dateFormat.format(date);
		return date1;
}
	/**
	 * Method to go to log page
	 * 
	 */
	public final void gotoLogTabWEX() throws Exception {
		WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver());
		wexlogPage = wexlogPage.getInstance();
		wexlogPage.scrollOnWEXLogPage("logstabsnavigation");
		wexlogPage.clickByActionsClickWEXLogPage("logstabsnavigation");
	}
	
	
	/**
	 * This method waits for spinner icon to disappear
	 * @return - returns true if spinner icon disappears
	 */
	public final boolean waitForLoaderIconToAppear() throws Exception
	{	
		WEXDashboardPage wexDashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		if(!wexDashboardPage.waitForElementsOfDashboardPage("loaderIcon")) {
			LOGGER.info("Loader icon still not appeared!!!");
            return false;
		}
		return true;
				        
	}
	
	/**
	 * This method waits for spinner icon to disappear
	 * @return - returns true if spinner icon disappears
	 */
	public final boolean waitForLoaderIconToDisappear() throws Exception
	{	
		WEXDashboardPage wexDashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (wexDashboardPage.waitForElementsOfDashboardPageDynamic("loaderIcon",2)) {
			if(!wexDashboardPage.waitUntilElementIsInvisibleOfDashboardPage("loaderIcon")) {
				LOGGER.info("Loader icon is still visible!!!");
	            return false;
			}
		}
		return true;
	}

}
