package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.DashboardVariables;
import com.daasui.pages.BenchmarkPage;
import com.daasui.pages.SettingsPage;

/**
 * @author karpev
 *
 */
public class BenchmarkTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(BenchmarkTest.class);
	public static String environment;

	/**
	 * This Data provider includes MSP and Reseller credentials for Benchmark
	 * testing Admin and IT admin
	 * 
	 * @return
	 */
	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[4][2];
		data[0][0] = "MSP_ADMIN_US_EMAIl_BENCHMARK";
		data[0][1] = "MSP_ADMIN_US_PASSWORD_BENCHMARK";
		data[1][0] = "RESELLER_NEW_UI_EMAIL_US";
		data[1][1] = "RESELLER_NEW_UI_PASSWORD_US";
		data[2][0] = "REPORT_ADMIN_NEW_UI_EMAIL_US";
		data[2][1] = "REPORT_ADMIN_UI_PASSWORD_US";
		data[3][0] = "IT_ADMIN_USER_US";
		data[3][1] = "IT_ADMIN_USER_PASSWORD";
		return data;
	}

	/**
	 * This Data provider includes Partner Admin and Partner Specialist
	 * 
	 * @return Object
	 */
	@DataProvider
	public Object[][] getLoginDataForPartner() {
		Object[][] data = new Object[2][2];
		data[0][0] = "RESELLER_NEW_UI_EMAIL_US";
		data[0][1] = "RESELLER_NEW_UI_PASSWORD_US";
		data[1][0] = "RESELLER_NEW_UI_EMAIL_US_SS";
		data[1][1] = "RESELLER_NEW_UI_PASSWORD_US_SS";
		return data;
	}

	/**
	 * Verify Benchmark Title
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 527429")
	public final void verifyBenchmarkTitle() throws Exception {
		environment = System.getProperty("environment");
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		gotoBenchmarkPage();
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE,
				getCredentials(environment, "MSP_ADMIN_US_EMAIl_BENCHMARK"))) {
			Assert.assertTrue(
					benchmarkPage.getTextOfBenchmarkPage("benchmarkTitleOnBreadcrumbs")
							.equals(benchmarkPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.benchmarks")),
					"Benchmark title doesn't match");
			LOGGER.info("Benchmark title is matched");
		}
	}

	/**
	 * Verify Redirection when Clicked on ADD button to create new benchmark
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 527429")
	public final void checkRedirectionOfPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		benchmarkPage.clickOnElementsOfBenchmarkPage("benchmarkAdd");
		Assert.assertTrue(benchmarkPage.verifyElementsOfBenchmarkPage("addBenchmarkForm"),
				"Redirection to Add benchmark page failed on clicking ADD button on benchmark page");
		LOGGER.info("Redirection to Add benchmark page successfull");
	}

	/**
	 * Verify benchmark creation for SW category 'Operating System', Validate
	 * benchmark created successfully
	 * 
	 * @throws Exception
	 */
	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 527429")
	public final void verifyBenchmarkCreationForOS() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		String operatingSystem = benchmarkPage.getTextLanguage(LanguageCode, "swbenchmark_service",
				"label.swbenchmark.metadata.category.operatingsystem");
		String benchmark = benchmarkPage.createBenchmark(operatingSystem, LanguageCode);
		// When data not present in the company then skip test
		if (benchmark == null) {
			throw new SkipException("Data not present to create benchmark for the selected company");
		}
		Assert.assertTrue(benchmarkPage.validateBenchmarkCreation(benchmark),
				"Failed to create new benchmark for category Operating System");
		LOGGER.info("For category Operating System, benchmark created successfully -" + benchmark);
	}

	/**
	 * Verify benchmark creation for SW category 'BIOS', Validate benchmark created
	 * successfully
	 * 
	 * @throws Exception
	 */
	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 527429")
	public final void verifyBenchmarkCreationForBIOS() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		String bios = benchmarkPage.getTextLanguage(LanguageCode, "swbenchmark_service",
				"label.swbenchmark.metadata.category.bios");
		String benchmark = benchmarkPage.createBenchmark(bios, LanguageCode);
		// When data not present in the company then skip test
		if (benchmark == null) {
			throw new SkipException("Data not present to create benchmark for the selected company");
		}
		Assert.assertTrue(benchmarkPage.validateBenchmarkCreation(benchmark),
				"Failed to create new benchmark for category BIOS");
		LOGGER.info("For category BIOS, benchmark created successfully -" + benchmark);
	}

	/**
	 * Verify benchmark creation for SW category 'Driver', Validate benchmark
	 * created successfully
	 * 
	 * @throws Exception
	 */
	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 527429")
	public final void verifyBenchmarkCreationForDriver() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		String driver = benchmarkPage.getTextLanguage(LanguageCode, "swbenchmark_service",
				"label.swbenchmark.metadata.category.driver");
		String benchmark = benchmarkPage.createBenchmark(driver, LanguageCode);
		// When data not present in the company then skip test
		if (benchmark == null) {
			throw new SkipException("Data not present to create benchmark for the selected company");
		}
		Assert.assertTrue(benchmarkPage.validateBenchmarkCreation(benchmark),
				"Failed to create new benchmark for category Driver");
		LOGGER.info("For category Driver, benchmark created successfully -" + benchmark);
	}

	/**
	 * Verify benchmark creation for SW category 'Software Application', Validate
	 * benchmark created successfully
	 * 
	 * @throws Exception
	 */
	@Test(priority = 6, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 527429")
	public final void verifyBenchmarkCreationForSWApplication() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		String swApplication = benchmarkPage.getTextLanguage(LanguageCode, "swbenchmark_service",
				"label.swbenchmark.metadata.category.softwareapplication");
		String benchmark = benchmarkPage.createBenchmark(swApplication, LanguageCode);
		// When data not present in the company then skip test
		if (benchmark == null) {
			throw new SkipException("Data not present to create benchmark for the selected company");
		}
		Assert.assertTrue(benchmarkPage.validateBenchmarkCreation(benchmark),
				"Failed to create new benchmark for category Software Application");
		LOGGER.info("For category Software Application, benchmark created successfully -" + benchmark);
	}

	/**
	 * Verify create duplicate benchmark, Validate duplicate benchmark created
	 * successfully
	 * 
	 * @throws Exception
	 */
	@Test(priority = 7, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 540071")
	public final void verifyDuplicateBenchmarkCreation() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		String bios = benchmarkPage.getTextLanguage(LanguageCode, "swbenchmark_service",
				"label.swbenchmark.metadata.category.bios");
		String benchmark = benchmarkPage.createBenchmark(bios, LanguageCode);
		// When data not present in the company then skip test
		if (benchmark == null) {
			throw new SkipException("Data not present to create benchmark for the selected company");
		}
		Assert.assertTrue(benchmarkPage.validateBenchmarkCreation(benchmark),
				"Failed to create new benchmark to create its duplicate");
		LOGGER.info("Benchmark created successfully -" + benchmark);
		Assert.assertTrue(benchmarkPage.verifyduplicateBenchmarkCreation(benchmark, LanguageCode),
				"Failed to create duplicate benchmark");
		LOGGER.info("New duplicate benchmark created successfully");
	}

	/**
	 * Verify delete benchmark, Validate deleted benchmark removed from the list
	 * successfully
	 * 
	 * @throws Exception
	 */
	@Test(priority = 8, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 540067")
	public final void verifyBenchmarkDelete() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		String bios = benchmarkPage.getTextLanguage(LanguageCode, "swbenchmark_service",
				"label.swbenchmark.metadata.category.bios");
		String benchmark = benchmarkPage.createBenchmark(bios, LanguageCode);
		// When data not present in the company then skip test
		if (benchmark == null) {
			throw new SkipException("Data not present to create benchmark for the selected company");
		}
		Assert.assertTrue(benchmarkPage.validateBenchmarkCreation(benchmark),
				"Failed to create new benchmark to delete same");
		LOGGER.info("Benchmark created successfully -" + benchmark);
		Assert.assertTrue(benchmarkPage.verifyBenchmarkDelete(benchmark), "Failed to delete benchmark");
		LOGGER.info("Benchmark deleted successfully");
	}

	/**
	 * Verify benchmark list Columns
	 * 
	 * @throws Exception
	 */
	@Test(priority = 9, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 540077", enabled = false)
	public final void verifyBenchmarkList() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		Assert.assertTrue(benchmarkPage.verifyBenchmarkListFields("BenchmarkListColumns", LanguageCode, "daas_ui"),
				"Failed to test benchmark list");
		LOGGER.info("Benchmark list verified successfully");
	}

	/**
	 * Verify Benchmark->BY MODEL tab -
	 * 
	 * @throws Exception
	 */
	@Test(priority = 10, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 539870", enabled = false)
	public final void verifyBenchmarkListByModel() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		// create SW application benchmark to drill down on BY MODEL tab
		String swApplication = benchmarkPage.getTextLanguage(LanguageCode, "swbenchmark_service",
				"label.swbenchmark.metadata.category.softwareapplication");
		String benchmark = benchmarkPage.createBenchmark(swApplication, LanguageCode);
		// When data not present in the company then skip test
		if (benchmark == null) {
			throw new SkipException("Data not present to create benchmark for the selected company");
		}
		Assert.assertTrue(benchmarkPage.validateBenchmarkCreation(benchmark),
				"Failed to create new benchmark for category Software Application");
		LOGGER.info("For category Software Application, benchmark created successfully -" + benchmark);
		String byModel = benchmarkPage.getTextLanguage(LanguageCode, "daas_ui", "benchmark.details.tab.by_model");
		Assert.assertTrue(benchmarkPage.verifyBenchmarkDetails(byModel, "tabByModel"));
		Assert.assertTrue(benchmarkPage.verifyBenchmarkListFields("ByModelTabColumns", LanguageCode, "daas_ui"),
				"Failed to test benchmark BY MODEL");
		LOGGER.info("Benchmark 'BY MODEL' tab verified successfully");
	}

	/**
	 * Verify Benchmark->BY OS tab -
	 * 
	 * @throws Exception
	 */
	@Test(priority = 11, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 540485", enabled = false)
	public final void verifyBenchmarkListByOS() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		// create SW application benchmark to drill down on BY OS tab
		String swApplication = benchmarkPage.getTextLanguage(LanguageCode, "swbenchmark_service",
				"label.swbenchmark.metadata.category.softwareapplication");
		String benchmark = benchmarkPage.createBenchmark(swApplication, LanguageCode);
		// When data not present in the company then skip test
		if (benchmark == null) {
			throw new SkipException("Data not present to create benchmark for the selected company");
		}
		Assert.assertTrue(benchmarkPage.validateBenchmarkCreation(benchmark),
				"Failed to create new benchmark for category Software Application");
		LOGGER.info("For category Software Application, benchmark created successfully -" + benchmark);
		String byOS = benchmarkPage.getTextLanguage(LanguageCode, "daas_ui", "benchmark.details.tab.by_os");
		Assert.assertTrue(benchmarkPage.verifyBenchmarkDetails(byOS, "tabByOS"));
		Assert.assertTrue(benchmarkPage.verifyBenchmarkListFields("ByOSTabColumns", LanguageCode, "daas_ui"),
				"Failed to test benchmark BY OS");
		LOGGER.info("Benchmark 'BY OS' tab verified successfully");
	}

	/**
	 * Verify Benchmark->Details tab -
	 * 
	 * @throws Exception
	 */
	@Test(priority = 12, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 540512", enabled = false)
	public final void verifyBenchmarkDetails() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		// create SW application benchmark to drill down on Details tab
		String swApplication = benchmarkPage.getTextLanguage(LanguageCode, "swbenchmark_service",
				"label.swbenchmark.metadata.category.softwareapplication");
		String benchmark = benchmarkPage.createBenchmark(swApplication, LanguageCode);
		// When data not present in the company then skip test
		if (benchmark == null) {
			throw new SkipException("Data not present to create benchmark for the selected company");
		}
		Assert.assertTrue(benchmarkPage.validateBenchmarkCreation(benchmark),
				"Failed to create new benchmark for category Software Application");
		LOGGER.info("For category Software Application, benchmark created successfully -" + benchmark);
		String details = benchmarkPage.getTextLanguage(LanguageCode, "daas_ui", "benchmark.details.tab.details");
		Assert.assertTrue(benchmarkPage.verifyBenchmarkDetails(details, "tabDetails"));
		Assert.assertTrue(benchmarkPage.verifyBenchmarkListFields("BenchmarkDetailsColumns", LanguageCode, "daas_ui"),
				"Failed to test benchmark Details tab");
		LOGGER.info("Benchmark 'DETAILS' tab verified successfully");
	}

	/**
	 * Verify Benchmark->Overview tab -
	 * 
	 * @throws Exception
	 */
	@Test(priority = 13, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 526289")
	public final void verifyBenchmarkOverview() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		// create SW application benchmark to drill down on Overview tab
		String swApplication = benchmarkPage.getTextLanguage(LanguageCode, "swbenchmark_service",
				"label.swbenchmark.metadata.category.softwareapplication");
		String benchmark = benchmarkPage.createBenchmark(swApplication, LanguageCode);
		// When data not present in the company then skip test
		if (benchmark == null) {
			throw new SkipException("Data not present to create benchmark for the selected company");
		}
		Assert.assertTrue(benchmarkPage.validateBenchmarkCreation(benchmark),
				"Failed to create new benchmark for category Software Application");
		LOGGER.info("For category Software Application, benchmark created successfully -" + benchmark);
		String overview = benchmarkPage.getTextLanguage(LanguageCode, "daas_ui", "benchmark.details.tab.overview");
		Assert.assertTrue(benchmarkPage.verifyBenchmarkDetails(overview, "tabOverview"));
		Assert.assertTrue(benchmarkPage.verifyBenchmarkOverviewtabDetails(benchmark, LanguageCode, "daas_ui"),
				"Failed to test benchmark Overview tab");
		LOGGER.info("Benchmark 'Overview' tab verified successfully");
	}

	/**
	 * Verify Benchmark->TOP 10 tab -
	 * 
	 * @throws Exception
	 */
	@Test(priority = 14, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "Test Case ID: 540510")
	public final void verifyBenchmarkTOP10() throws Exception {
		login("MSP_ADMIN_US_EMAIl_BENCHMARK", "MSP_ADMIN_US_PASSWORD_BENCHMARK");
		impersonateCompanyByNameBenchmark("COMPANY_NAME_BENCHMARK", "MSP_ADMIN_US_EMAIl_BENCHMARK");
		BenchmarkPage benchmarkPage = new BenchmarkPage(PreDefinedActions.getDriver()).getInstance();
		// create SW application benchmark to drill down on 'TOP 10' tab
		String swApplication = benchmarkPage.getTextLanguage(LanguageCode, "swbenchmark_service",
				"label.swbenchmark.metadata.category.softwareapplication");
		String benchmark = benchmarkPage.createBenchmark(swApplication, LanguageCode);
		// When data not present in the company then skip test
		if (benchmark == null) {
			throw new SkipException("Data not present to create benchmark for the selected company");
		}
		Assert.assertTrue(benchmarkPage.validateBenchmarkCreation(benchmark),
				"Failed to create new benchmark for category Software Application");
		LOGGER.info("For category Software Application, benchmark created successfully -" + benchmark);
		String top10 = benchmarkPage.getTextLanguage(LanguageCode, "daas_ui", "benchmark.details.tab.top_10");
		Assert.assertTrue(benchmarkPage.verifyBenchmarkDetails(top10, "tabTop10"));
		Assert.assertTrue(benchmarkPage.verifyBenchmarkTOP10Details(benchmark, LanguageCode, "daas_ui"),
				"Failed to test benchmark 'TOP 10' tab");
		LOGGER.info("Benchmark 'TOP 10' tab verified successfully");
	}
	
	/**
	 * This test case validates the context sensitive help links on Benchmark screen
	 *
	 * @throws Exception
	 */
	@Test(priority = 15, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
	"SMOKE_CI" }, description = "Test Case ID : 891080")
	public final void verifyOnlineHelpLinksOnBenchmark() throws Exception {
		login("IT_ADMIN_HELP_AND_SUPPORT_EMAIL", "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		// Verify help and support link for Benchmark tab
		waitForPageLoaded();
		gotoBenchmarkPage();
		LOGGER.info("Redirected to Benchmark tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("help&SupportLink",
						getTextLanguage(LanguageCode, "daas_ui", "settings.help_and_support.title")),
				"Help & Support link text did not match on Benchmark tab");
		settingsPage.clickOnElementsOfSettingsPage("help&SupportLink");
		LOGGER.info("Clicked on Help & Support link from Benchmark tab");
		waitForPageLoaded();
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")),
				"User not redirected to Help & Support tab after clicking on Help & Support link from Benchmark tab");
		gotoBenchmarkPage();

		softAssert.assertAll();
		LOGGER.info("Test case to verify context sensitive links on Benchmark passed successfully.");
	}
}
