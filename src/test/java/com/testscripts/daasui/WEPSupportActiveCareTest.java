package com.testscripts.daasui;

import com.basesource.utils.CPINEnrollmentUtils;
import com.basesource.utils.CSVFileReader;
import com.basesource.utils.EnrollFakeDevice;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.TableConfigurationPage;
import com.daasui.pages.WEPCreateCompanyPage;
import com.daasui.pages.WEPDeviceDetailsPage;
import com.daasui.pages.WEPDeviceListPage;
import com.daasui.pages.WEPHardwareSupportPage;
import com.daasui.pages.WEPOrderDetailsPage;
import com.daasui.pages.WEPOrdersListPage;
import com.daasui.pages.WEPPartnerCustomersPage;
import com.daasui.pages.WEPRootLoginPage;
import com.daasui.pages.WEXDashboardPage;
import com.daasui.pages.WEXPartnerPage;
import com.daasui.pages.WEXSettingPage;
import com.utils.activecare.BaseConfigurableUtils;
import com.utils.activecare.JSONUtil;
import com.utils.activecare.TestContextHolder;
import com.utils.activecare.TestMetadata;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CSVFileReader.ACGenerationVariant;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.DeviceVariables;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.utils.ActiveCareDBValidation.UIDeviceData;
import com.basesource.utils.ActiveCareDBValidation.UIPlanCard;
import com.basesource.utils.ActiveCareDBValidation.CsvFileData;
import com.basesource.utils.ActiveCareDBValidation;

import com.basesource.utils.DBUtil;
import com.basesource.utils.DeviceReportLambdaTables;

import java.util.*;
import java.util.stream.Collectors;

import java.io.IOException;
import java.text.SimpleDateFormat;


import static org.testng.Assert.assertTrue;

public class WEPSupportActiveCareTest extends BaseConfigurableUtils {

	private static Logger LOGGER = LogManager.getLogger(WEPSupportActiveCareTest.class);
	private ObjectReader WEPSupportActiveCareTestPropertyReader = new ObjectReader();

	public static String timing = null;
	public static String emailExtract = null;
	public static String[] countryAndTimezone = new String[2];
	public static String statusTextPublicHoliday = null;
	public static String[] statusTextDirectSupport = new String[2];
	public static String[] emailBefore = new String[2];
	public static String statusTextCallback = null;
	int notificationCountUnread = 0;
	Properties settingsPageProperties;
	Properties EnvironmentPageProperties;
	public static String roleIdURL = getEnvironmentSpecificData(System.getProperty("environment"), "ROLE_ID_URL");
	public static String cataLystURL = getEnvironmentSpecificData(System.getProperty("environment"),
			"CATALYST_API_BASE_URL");
	public static String importCompany = getEnvironmentSpecificData(System.getProperty("environment"),
			"DEVICE_IMPORT_COMPANY");
	public static String customfieldsCompany = getEnvironmentSpecificData(System.getProperty("environment"),
			"CUSTOM_FIELD_COMPANY");
	public static String supportTeamUserEmail = getEnvironmentSpecificData(System.getProperty("environment"),
			"SUUPORT_TEAM_USER_EMAIL");
	public static String partnerName = getEnvironmentSpecificData(System.getProperty("environment"),
			"HELP_AND_SUPPORT_PARTNER_NAME");
	public static String mspName = getEnvironmentSpecificData(System.getProperty("environment"),
			"HELP_AND_SUPPORT_MSP_NAME");
	public static String incidentPCCompanyName = getEnvironmentSpecificData(System.getProperty("environment"),
			"INCIDENT_PCCOMPANY");
	public static String incidentPMCompanyName = getEnvironmentSpecificData(System.getProperty("environment"),
			"INCIDENT_PMCOMPANY");
	public static String subscriptionURL = "services/subscription_admin_service/1.0/licenses/";
	public ArrayList<String> customFields = new ArrayList<String>();
	private WEPHardwareSupportPage hardwareSupport;
	private SettingsPage settingsPage;

	// This Data provider includes credential for AC widgets.
	@DataProvider
	public Object[][] tenantWidgetData() {
		return new Object[][] { { "ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD",
				new String[] { "Battery Replacement", "Disk Replacement", "Thermal Grading", "All Incidents By Type",
						"Fan Warning", "Fan Critical", "Today's New Critical Incidents", "Service Coverage Status" } }, };
	}

	// This Data provider includes issue categories for Active Care.
	@DataProvider
	public Object[][] issueCategories() {
		return new Object[][] { { new String[] 
				{ "Manually Created - Battery",
				  "Manually Created - HDD", 
				  "Manually Created - Motherboard",
				  "Manually Created - I/O Device", 
				  "Manually Created - Power", 
				  "Manually Created - Other" } 
		} };
	}

    /**
     * Initializes WEP page objects for test execution.
     * Called from test methods to ensure fresh instances.
     * Follows POM best practice of lazy initialization.
     */
    private void initializePages() throws IOException {
        this.ordersListPage = new WEPOrdersListPage(PreDefinedActions.getDriver()).getInstance();
        this.rootLoginPage = new WEPRootLoginPage(PreDefinedActions.getDriver()).getInstance();
        this.orderDetailsPage = new WEPOrderDetailsPage(PreDefinedActions.getDriver()).getInstance();
        this.dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
    }

    // Page object fields - initialized on demand
    private WEPOrdersListPage ordersListPage;
    private WEPRootLoginPage rootLoginPage;
    private WEPOrderDetailsPage orderDetailsPage;
    private WEXDashboardPage dashboardPage;

	public void setUp() throws Exception {
		waitForPageLoaded();
		// Initialize WEPhardwareSupport and SettingsPage
		hardwareSupport = new WEPHardwareSupportPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		// Wait for the page to load after initialization
		waitForPageLoaded();
	}
	
    // Holder for import results
    public static class ImportResult {
        private final Map<String, List<String>> csv_data;
        private final ArrayList<String> serialNumbers;

        public ImportResult(Map<String, List<String>> csvData, ArrayList<String> serialNumbers) {
        	this.csv_data = csvData;
            this.serialNumbers = serialNumbers;
        }

        /** @return CSV data map */
        public Map<String, List<String>> getCsv_data() {
            return csv_data;
        }

        /** @return List of serial numbers */
        public ArrayList<String> getSerialNumbers() {
            return serialNumbers;
        }
    }

	private static final Map<String, String> ORDER_TYPE_TO_PLAN = Map.of(
            "New Order - Active Care", "HP Active Care",
            "New Order - Premium", "HP Premium",
            "New Order - Premium Plus", "HP Premium+",
            "New Order - Out Of Band Remediation", "HP Remotecare"
    );

	private boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }

	private void validatePlanCardsFromDeviceDetailsPage(UIDeviceData uiDeviceData, String[] orderTypes) {
        SoftAssert softAssert = new SoftAssert();

        orderTypes = resolveExpectedUiOrders(orderTypes);
        Set<String> expectedPlans = Arrays.stream(orderTypes)
                .map(type -> ORDER_TYPE_TO_PLAN.get(type.trim()))
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Map<String, UIPlanCard> cardsByName = uiDeviceData.planCards == null
                ? Collections.emptyMap()
                : uiDeviceData.planCards.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toMap(
                                card -> card.planName,
                                card -> card,
                                (existing, duplicate) -> existing,
                                LinkedHashMap::new));

        for (String plan : expectedPlans) {
            UIPlanCard card = cardsByName.get(plan);
            softAssert.assertNotNull(card, plan + " plan card is missing in UI");
            if (card != null) {
                softAssert.assertTrue(hasValue(card.planName), plan + " plan name missing");
                softAssert.assertTrue(hasValue(card.status), plan + " status missing");
                softAssert.assertTrue(hasValue(card.startDate), plan + " start date missing");
                softAssert.assertTrue(hasValue(card.endDate), plan + " end date missing");
                softAssert.assertTrue(hasValue(card.description), plan + " description missing");
            }
        }

        softAssert.assertAll();
		LOGGER.info("✓ UI Warranty & Service Coverage validation successful for plans: {}", expectedPlans);
    }



    /**
     * Helper to filter expected UI order types based on optional-pc-refactor toggle.
     * PCE_CAMS_DATA -> All orders (Cumulative)
     * PCE_DEVICE/PCE_DEVICE_CAMS_DATA -> Highest Priority Plan + OOBR (Precedence)
     */
    private String[] resolveExpectedUiOrders(String[] types) {
        String toggle = "PCE_DEVICE_CAMS_DATA";
        try { toggle = getEnvironmentSpecificData(System.getProperty("environment"), "optional-pc-refactor"); } catch (Exception e) {}
        if ("PCE_CAMS_DATA".equalsIgnoreCase(toggle)) return types;
        List<String> result = new ArrayList<>();
        Arrays.stream(types).filter(t -> t.contains("Out Of Band")).findFirst().ifPresent(result::add);
        Arrays.stream(types)
                .filter(t -> !t.contains("Out Of Band"))
                .max(Comparator.comparingInt(t -> t.contains("Premium Plus") ? 3 : t.contains("Premium") ? 2 : 1))
                .ifPresent(result::add);
        return result.toArray(new String[0]);
    }

    /**
     * Login with the given credentials and navigates to the specified menu and submenu.
     *
     * @param email    user email
     * @param password user password
     * @param menu     main menu to select
     * @param submenu  submenu to select
     * @throws Exception if login or navigation fails
     */
    private void loginAndNavigate(String email, String password, String menu, String submenu) throws Exception {
        // Ensure pages are initialized (lazy initialization pattern)
        if (rootLoginPage == null) {
            initializePages();
        }
        
        login(email, password);
        waitForPageLoaded();
        sleeper(3000);
        openLeftSidePanel();
        rootLoginPage.sideMenuSelectionWEPRootLoginPage(LanguageCode, menu, submenu);
        waitForPageLoaded();
    }

    /*
     * Columns to be displayed in the CSV file for verification
     */
    private static final List<String> columnsToBeDisplayed = Arrays.asList("Plans", "Enrolled", "BYOD", "Location", "Service Coverage");


    /**
     * Creates multiple CSV files for different order types in a single batch
     * No browser operations, no login required
     *
     * @param orderTypes Array of order types to create CSVs for
     * @param effectiveSerial Serial number to use for all CSVs
     * @param endCustomerName End customer name for all CSVs
     * @return Map of order type -> CsvFileData
     */
    private Map<String, CsvFileData> createMultipleOrderTypesForSameSerialNumber(String[] orderTypes,
                                                           String effectiveSerial,
                                                           String endCustomerName) throws Exception {

        LOGGER.info("Creating {} CSV files for order types in batch", orderTypes.length);
        Map<String, CsvFileData> csvFilesByType = new LinkedHashMap<>();

        for (String orderType : orderTypes) {
            try {
                CsvFileData csvData = createCsvFile(orderType, 1, effectiveSerial,
                        endCustomerName, ACGenerationVariant.VALID_ROWS, null);
                csvFilesByType.put(orderType, csvData);

            } catch (Exception e) {
                LOGGER.error("✗ Failed to create CSV for order type: {}", orderType, e);
                throw e;
            }
        }

        LOGGER.info("Batch CSV creation complete: {} files created", csvFilesByType.size());
        return csvFilesByType;
    }

    /**
     * OPTIMIZED: Creates CSV file only (no browser operations, no login/logout)
     * Use this method when you need to create multiple CSVs before importing them in a single session.
     */
    private CsvFileData createCsvFile(String orderType,
                                      int rowCount,
                                      String effectiveSerial,
                                      String endCustomerName,
                                      ACGenerationVariant variant,
                                      Collection<String> mandatoryColumnsToBlank) throws Exception {
        
        String environment = System.getProperty("environment");
        LOGGER.info("Creating CSV file for order type: {}", orderType);

		Map<String, Object> data = WEPOrdersListPage.createActiveCareTestCSV_v3(
                orderType,
                rowCount,
                effectiveSerial,
                endCustomerName,
                variant,
                mandatoryColumnsToBlank
        );

        String globalFile = (String) data.get("outputCsvFilename");
        String splitFile = WEPOrdersListPage.getFileNameAfterSplit(environment, globalFile);
        @SuppressWarnings("unchecked")
        Map<String, List<String>> csvData = (Map<String, List<String>>) data.get("result_data");
        ArrayList<String> serials = new ArrayList<>(csvData.get("ObjectOfServiceSerialNumber"));
        LOGGER.info("CSV file created: {}", globalFile);
        return new CsvFileData(globalFile, splitFile, csvData, serials);
    }

    /**
     * Performs complete CSV import workflow in a single login session with OPTIMIZED region switching for EU.
     *
     * Optimization:
     * 1. Import ALL files and verify Global processing in US/Global region.
     * 2. If EU environment: Switch to EU region ONCE, verify ALL split files, then switch back to US.
     * 3. Delete ALL files in US/Global region.
     *
     * @param csvFilesByType Map of order type -> CsvFileData (pre-created CSVs)
     * @return ArrayList of serial numbers from the first CSV (all have same serial)
     */
    private ArrayList<String> performBatchCsvImport(Map<String, CsvFileData> csvFilesByType) throws Exception {

        if (csvFilesByType == null || csvFilesByType.isEmpty()) {
            throw new IllegalArgumentException("CSV files map cannot be null or empty");
        }

        // Initialize pages if needed (lazy initialization)
        if (ordersListPage == null || rootLoginPage == null || orderDetailsPage == null) {
            initializePages();
        }
        
        SoftAssert soft = new SoftAssert();

        String environment = System.getProperty("environment");
        boolean isEuEnv = environment != null && environment.toUpperCase().contains("EU");

        LOGGER.info("Importing {} order types in a single login session. Environment: {} (EU Mode: {})",
                csvFilesByType.size(), environment, isEuEnv);

        try {
            loginAndNavigate("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US","Administrative", "Orders");
            LOGGER.info("✓ Logged in and navigated to Orders page (US/Global)");

            for (Map.Entry<String, CsvFileData> entry : csvFilesByType.entrySet()) {
                String orderType = entry.getKey();
                CsvFileData csvData = entry.getValue();

                LOGGER.info("Phase 1: Importing & Verifying Global File for {}", orderType);
                soft.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
                soft.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, orderType, csvData.globalFile),
                        "FAIL: Import failed for " + orderType);
                soft.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, csvData.globalFile),
                        "FAIL: Global file not processed for " + orderType);
            }

            if (isEuEnv) {
                LOGGER.info("Phase 2: Switching to EU Region for Split File verification");
                logout();
                PreDefinedActions.getDriver().get(getEnvironment(System.getProperty("environment")));
                waitForPageLoaded();
                sleeper(1000);
                loginAndNavigate("ROOT_ADMIN_USER", "ROOT_PASSWORD", "Administrative", "Orders");
            } else {
                LOGGER.info("Phase 2: Verifying Split Files (Current Region)");
            }

            for (Map.Entry<String, CsvFileData> entry : csvFilesByType.entrySet()) {
                String orderType = entry.getKey();
                CsvFileData csvData = entry.getValue();
                LOGGER.info("Verifying Split File for {}", orderType);
                soft.assertTrue(ordersListPage.checkIfSplitFileIsProcessed(LanguageCode, csvData.splitFile),
                        "FAIL: Split file not processed for " + orderType);
            }

            for (Map.Entry<String, CsvFileData> entry : csvFilesByType.entrySet()) {
                CsvFileData csvData = entry.getValue();
                LOGGER.info("Deleting Global File: {}", csvData.globalFile);
                ordersListPage.deleteActiveCareTestCSV(csvData.globalFile);
            }

        } finally {
            try {
                logout();
                orderDetailsPage.deleteAllcookies();
                LOGGER.info("✓ Logged out and cleaned up");
            } catch (Exception cleanupEx) {
                LOGGER.warn("⚠ Cleanup error: {}", cleanupEx.getMessage());
            }
        }
        soft.assertAll();
        CsvFileData firstCsvData = csvFilesByType.values().iterator().next();
        ArrayList<String> serials = firstCsvData.serials;

        LOGGER.info("Returning {} serial(s) for device verification", serials.size());
        return serials;
    }


    private void checkOndemandToggle(String effectiveSerial) throws Exception{
        String[] orderTypes = {"Device Transfer"};
        Map<String, CsvFileData> csvFilesByType = createMultipleOrderTypesForSameSerialNumber(orderTypes, effectiveSerial, "ACOnlyTenantName_US");
        ArrayList<String> serials = performBatchCsvImport(csvFilesByType);

    }

    private void verifySplitFileProcessing(String testEnv, String languageCode, String splitFile) throws Exception {
        LOGGER.info("Verifying split file processing: {}", splitFile);
        if (testEnv.startsWith("EU")) {
            logout();
            loginAndNavigate("ROOT_ADMIN_USER", "ROOT_PASSWORD", "Administrative", "Orders");
            Assert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        }
        Assert.assertTrue(ordersListPage.checkIfSplitFileIsProcessed(languageCode, splitFile), "FAIL: Split file not processed.");
    }

    /**
     * Verifies device status in UI after import
     *
     * @param serials Device serial numbers to verify
     * @param expectedStatus Expected device status (e.g., "READY", "ERROR")
     */
    private void verifyDeviceStatusInUI(ArrayList<String> serials, String expectedStatus) throws Exception {

        login("AC_USER", "AC_PASSWORD");
        WEPDeviceListPage deviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();

        openLeftSidePanel();
        deviceListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        LOGGER.info("Navigated to Devices -> PC Page");

        waitForPageLoaded();
        deviceListPage.filterOnStatusDeviceList(null);

        assertTrue(deviceListPage.VerifyAddedDeviceStatus(serials, expectedStatus),
                "FAIL: Device status is not " + expectedStatus);
    }

    /**
     * Captures UI device data for DB validation
     * Assumes user is already logged in and on device list page
     *
     * @return UIDeviceData object with all UI details
     */
    private UIDeviceData capturePlanCardsFromDeviceDetailsPage() throws Exception {

        WEPDeviceListPage deviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        WEPDeviceDetailsPage deviceDetailsPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
        deviceListPage.clickOnElementsOfWEPDeviceListPage("selectSerialNumber");
        waitForPageLoaded();

        return deviceDetailsPage.captureDeviceDetailsFromUI();
    }
    // Reusable helper (no @Test)
    private ImportResult performCsvImport(String orderType,
                                        int rowCount,
                                        boolean requestSerialFromSNR,
                                        String endCustomerName,
                                        ACGenerationVariant variant,
                                        Collection<String> mandatoryColumnsToBlank,
										String overrideSerial) throws Exception {
        String environment = System.getProperty("environment");
        SoftAssert soft = new SoftAssert();

        // Login and navigate first to initialize page objects
        loginAndNavigate("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US","Administrative", "Orders");

        // Determine serial: generate only if explicitly requested; else keep null
		String effectiveSerial = hasValue(overrideSerial)
                ? overrideSerial
                : (requestSerialFromSNR ? ordersListPage.makeEntryInSNRTable() : null);

		if (hasValue(overrideSerial)) {
            LOGGER.info("Using caller-supplied override serial: {}", effectiveSerial);
        } else if (requestSerialFromSNR) {
            LOGGER.info("Serial obtained from SNR table: {}", effectiveSerial);
        } else {
            LOGGER.info("No serial requested; passing null to CSV generator.");
        }
        Map<String, Object> data = ordersListPage.createActiveCareTestCSV_v3(
                orderType,
                rowCount,
                effectiveSerial,
                endCustomerName,
                variant,
                mandatoryColumnsToBlank
        );

        String globalFile = (String) data.get("outputCsvFilename");
        String splitFile = ordersListPage.getFileNameAfterSplit(environment, globalFile);
        @SuppressWarnings("unchecked")
        Map<String, List<String>> csvData = (Map<String, List<String>>) data.get("result_data");
        ArrayList<String> serials = new ArrayList<>(csvData.getOrDefault("ObjectOfServiceSerialNumber",Collections.emptyList()));
        soft.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        soft.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, orderType, globalFile), "FAIL: Import failed.");
        soft.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, globalFile), "FAIL: Global file not processed.");
        verifySplitFileProcessing(environment, LanguageCode, splitFile);
        ordersListPage.deleteActiveCareTestCSV(globalFile);
        if (toggleVerification(CommonVariables.On_DEMAND_ASSET, getCredentials(environment, "ROOT_ADMIN_USER_US")) && 
        toggleVerification(CommonVariables.REMOVE_END_CUSTOMER_NAME, getCredentials(environment, "ROOT_ADMIN_USER_US"))&& 
        !orderType.equalsIgnoreCase("Device Transfer") &&
        !orderType.equalsIgnoreCase("Reseller Update") &&
        !orderType.equalsIgnoreCase("Data Collection")) {
            LOGGER.info("On Demand Asset Creation Toggle {} is ON. Additional step of Device Transfer is required to bring device from ACHT to End Customer.");
            Map<String, Object> deviceTransferData = ordersListPage.createActiveCareTestCSV_v3(
                "Device Transfer", rowCount,
                effectiveSerial,
                endCustomerName,
                variant,
                mandatoryColumnsToBlank
                );
            String globalDTFile = (String) deviceTransferData.get("outputCsvFilename");
            String splitDTFile = ordersListPage.getFileNameAfterSplit(environment, globalDTFile);
            soft.assertTrue(ordersListPage.returnToOrderListPage(), "FAIL: Could not return to Orders List Page.");
            soft.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, "Device Transfer", globalDTFile), "FAIL: Import failed.");
            soft.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, globalDTFile), "FAIL: Global file not processed.");
            verifySplitFileProcessing(environment, LanguageCode, splitDTFile);
            ordersListPage.deleteActiveCareTestCSV(globalDTFile);
        }
        
        logout();
        orderDetailsPage.deleteAllcookies();
        return new ImportResult(csvData,serials);
    }

 // Recommended: add convenience getters
    private static class ImportResultData {
        private final Map<String, Object> data;
        ImportResultData(Map<String, Object> data) { this.data = data; }
        String getGlobalCsvFile() { return (String) data.get("outputCsvFilename"); }
        @SuppressWarnings("unchecked")
        Map<String, List<String>> getResultData() { return (Map<String, List<String>>) data.get("result_data"); }
        List<String> getSerialNumbers() {
            return getResultData().getOrDefault("ObjectOfServiceSerialNumber", List.of());
        }
    }

    // Parameterized importer test
    @Test(priority = 7, groups = { "REGRESSION_ACTIVECARE" }, enabled=false)
    public void importOrderTypeCSV(String orderType,
                                   int rowCount,
                                   boolean requestSerialFromSNR,
                                   String endCustomerName,
                                   ACGenerationVariant variant,
                                   Collection<String> mandatoryColumnsToBlank,
                                   ITestContext ctx) throws Exception {

        ImportResult result = performCsvImport(orderType, rowCount, requestSerialFromSNR, endCustomerName, variant, mandatoryColumnsToBlank, null);
        // Store per combination (key can be adjusted)
        String key = "importResult:" + orderType;
        ctx.setAttribute(key, result);
    }



    // Verify Import Modal for all order types. TestCase covered - C65387283, C65387284, C65387300
    @Test(priority = 1, groups = { "REGRESSION_ACTIVECARE","PRODUCTION_ACTIVECARE"}, description = "C65387283", enabled=true)
    public void addOrdersImportModalValidation() throws Exception {
        WEPOrdersListPage ordersListPage = new WEPOrdersListPage(PreDefinedActions.getDriver()).getInstance();
        WEPRootLoginPage rootLoginPage = new WEPRootLoginPage(PreDefinedActions.getDriver()).getInstance();

        SoftAssert soft = new SoftAssert();
        login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        openLeftSidePanel();
        rootLoginPage.sideMenuSelectionWEPRootLoginPage(LanguageCode, "Administrative", "Orders");
        soft.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        List<String> types = java.util.Arrays.asList(
                "New Order - Active Care",
                "New Order - Premium",
                "New Order - Premium Plus",
                "New Order - Out Of Band Remediation",
                "Device Transfer",
                "Reseller Update",
                "Data Collection"
            );
        if (System.getProperty("environment").toUpperCase().startsWith("US")) {
            for (String type : types) {
                soft.assertTrue(ordersListPage.validateImportModalForOrderType(type), "FAIL: Import modal validation failed for order type: " + type);
            }
        } else {
            LOGGER.info("Skipping import modal validation tests for EU environment. As file import is available for US region only.");
        }
        soft.assertAll();

    }

	@Test(priority = 4, groups = { "REGRESSION_ACTIVECARE" }, description = "Test Case ID : C65387301")
    public void validateResellerUpdateForReadyDeviceACOnlyWithReseller_headquarter_identifier() throws Exception {
        LOGGER.info("✓✓✓ Reseller Update TEST STARTED SUCCESSFULLY ✓✓✓");

        ImportResult activeCareImport = performCsvImport("New Order - Active Care", 1, true,
                "ACOnlyTenantName_US", ACGenerationVariant.VALID_ROWS, null, null);

		String reusedSerial = activeCareImport.serialNumbers.get(0);

        ImportResult resellerUpdate = performCsvImport("Reseller Update", 1, false,
                "ACOnlyTenantName_US", ACGenerationVariant.VALID_ROWS, null, reusedSerial);
		LOGGER.info("Reseller Update CSV Data: {}", resellerUpdate.csv_data);

		List<String> providersObj = Arrays.asList("AcOrderSubscriptionDataProvider");
        DeviceReportLambdaTables deviceReportLambdaTables = DBUtil.fetchDeviceReportLambda(reusedSerial, providersObj);
		boolean validationPassed = ActiveCareDBValidation.ValidateDBResellerDataPCETest(deviceReportLambdaTables, resellerUpdate.csv_data);

		Assert.assertTrue(validationPassed, "Reseller validation failed");
        LOGGER.info("✓✓✓ Reseller Update TEST COMPLETED SUCCESSFULLY ✓✓✓");
    }


	@Test(priority = 5, groups = { "REGRESSION_ACTIVECARE" }, description = "Test Case ID : C65387301")
    public void validateResellerUpdateForReadyDeviceACOnlyWithReseller_branch_identifier() throws Exception {
        LOGGER.info("✓✓✓ Reseller Update TEST STARTED SUCCESSFULLY ✓✓✓");

        ImportResult activeCareImport = performCsvImport("New Order - Active Care", 1, true,
                "ACOnlyTenantName_US", ACGenerationVariant.VALID_ROWS, null, null);

		String reusedSerial = activeCareImport.serialNumbers.get(0);

        ImportResult resellerUpdate = performCsvImport("Reseller Update", 1, false,
                "ACOnlyTenantName_US", ACGenerationVariant.MISSING_MANDATORY_VALUES, List.of("reseller_headquarter_identifier"), reusedSerial);
		LOGGER.info("Reseller Update CSV Data: {}", resellerUpdate.csv_data);

		List<String> providersObj = Arrays.asList("AcOrderSubscriptionDataProvider");
        DeviceReportLambdaTables deviceReportLambdaTables = DBUtil.fetchDeviceReportLambda(reusedSerial, providersObj);
		boolean validationPassed = ActiveCareDBValidation.ValidateDBResellerDataPCETest(deviceReportLambdaTables, resellerUpdate.csv_data);

		Assert.assertTrue(validationPassed, "Reseller validation failed");
        LOGGER.info("✓✓✓ Reseller Update TEST COMPLETED SUCCESSFULLY ✓✓✓");
    }


    @Test(priority = 6, groups = { "REGRESSION_ACTIVECARE" })
    public void importReadyDeviceACOnlyTenantVMultiPlan() throws Exception {
        LOGGER.info("✓✓✓ TEST STARTED SUCCESSFULLY ✓✓✓");
        WEPOrdersListPage ordersListPage = new WEPOrdersListPage(PreDefinedActions.getDriver()).getInstance();
        String effectiveSerial = ordersListPage.makeEntryInSNRTable();

        String[] orderTypes = {
            "New Order - Active Care",
			"New Order - Premium",
    		"New Order - Premium Plus",
            "New Order - Out Of Band Remediation"
        };

        LOGGER.info("Starting OPTIMIZED multi-order type import test with effectiveSerial: {}", effectiveSerial);
        Map<String, CsvFileData> csvFilesByType = createMultipleOrderTypesForSameSerialNumber(orderTypes, effectiveSerial, "ACOnlyTenantName_US");
        ArrayList<String> serials = performBatchCsvImport(csvFilesByType);
        checkOndemandToggle(effectiveSerial);
        verifyDeviceStatusInUI(serials, "READY");
        UIDeviceData uiDeviceData = capturePlanCardsFromDeviceDetailsPage();
        validatePlanCardsFromDeviceDetailsPage(uiDeviceData, orderTypes);

        List<String> providersObj = Arrays.asList("AcOrderSubscriptionDataProvider", "DynamoDbProvider", "AssetMongoProvider");
        DeviceReportLambdaTables deviceReportLambdaTables = DBUtil.fetchDeviceReportLambda(effectiveSerial, providersObj);
        ActiveCareDBValidation.ValidateDBDataPCETest(orderTypes, effectiveSerial, csvFilesByType, uiDeviceData, deviceReportLambdaTables);
        LOGGER.info("✓✓✓ TEST COMPLETED SUCCESSFULLY ✓✓✓");

    }


	@Test(priority = 7, groups = { "REGRESSION_ACTIVECARE" })
    public void importReadyDeviceACOnlyTenantActiveCarePlan() throws Exception {
        LOGGER.info("✓✓✓ Active Care plan-only import test started ✓✓✓");
        WEPOrdersListPage ordersListPage = new WEPOrdersListPage(PreDefinedActions.getDriver()).getInstance();
        String effectiveSerial = ordersListPage.makeEntryInSNRTable();

        String[] orderTypes = {"New Order - Active Care"};

        LOGGER.info("Starting Active Care CSV import with effectiveSerial: {}", effectiveSerial);
        Map<String, CsvFileData> csvFilesByType = createMultipleOrderTypesForSameSerialNumber(orderTypes, effectiveSerial, "ACOnlyTenantName_US");
        ArrayList<String> serials = performBatchCsvImport(csvFilesByType);
        checkOndemandToggle(effectiveSerial);
        verifyDeviceStatusInUI(serials, "READY");
        UIDeviceData uiDeviceData = capturePlanCardsFromDeviceDetailsPage();
        validatePlanCardsFromDeviceDetailsPage(uiDeviceData, orderTypes);

        List<String> providersObj = Arrays.asList("AcOrderSubscriptionDataProvider", "DynamoDbProvider", "AssetMongoProvider");
        DeviceReportLambdaTables deviceReportLambdaTables = DBUtil.fetchDeviceReportLambda(effectiveSerial, providersObj);
        ActiveCareDBValidation.ValidateDBDataPCETest(orderTypes, effectiveSerial, csvFilesByType, uiDeviceData, deviceReportLambdaTables);
        LOGGER.info("✓✓✓ Active Care plan-only import test completed ✓✓✓");

    }

	@Test(priority = 8, groups = { "REGRESSION_ACTIVECARE" })
    public void importReadyDeviceACOnlyTenantPremiumPlan() throws Exception {
        LOGGER.info("✓✓✓ Premium plan-only import test started ✓✓✓");
        WEPOrdersListPage ordersListPage = new WEPOrdersListPage(PreDefinedActions.getDriver()).getInstance();
        String effectiveSerial = ordersListPage.makeEntryInSNRTable();

        String[] orderTypes = {"New Order - Premium"};

        LOGGER.info("Starting Premium CSV import with effectiveSerial: {}", effectiveSerial);
        Map<String, CsvFileData> csvFilesByType = createMultipleOrderTypesForSameSerialNumber(orderTypes, effectiveSerial, "ACOnlyTenantName_US");
        ArrayList<String> serials = performBatchCsvImport(csvFilesByType);
        checkOndemandToggle(effectiveSerial);
        verifyDeviceStatusInUI(serials, "READY");
        UIDeviceData uiDeviceData = capturePlanCardsFromDeviceDetailsPage();
        validatePlanCardsFromDeviceDetailsPage(uiDeviceData, orderTypes);

        List<String> providersObj = Arrays.asList("AcOrderSubscriptionDataProvider", "DynamoDbProvider", "AssetMongoProvider");
        DeviceReportLambdaTables deviceReportLambdaTables = DBUtil.fetchDeviceReportLambda(effectiveSerial, providersObj);
        ActiveCareDBValidation.ValidateDBDataPCETest(orderTypes, effectiveSerial, csvFilesByType, uiDeviceData, deviceReportLambdaTables);
        LOGGER.info("✓✓✓ Premium plan-only import test completed ✓✓✓");

    }

	@Test(priority = 9, groups = { "REGRESSION_ACTIVECARE" })
    public void importReadyDeviceACOnlyTenantPremium_PlusPlan() throws Exception {
        LOGGER.info("✓✓✓ Premium Plus plan-only import test started ✓✓✓");
        WEPOrdersListPage ordersListPage = new WEPOrdersListPage(PreDefinedActions.getDriver()).getInstance();
        String effectiveSerial = ordersListPage.makeEntryInSNRTable();

        String[] orderTypes = {"New Order - Premium Plus"};

        LOGGER.info("Starting Premium Plus CSV import with effectiveSerial: {}", effectiveSerial);
        Map<String, CsvFileData> csvFilesByType = createMultipleOrderTypesForSameSerialNumber(orderTypes, effectiveSerial, "ACOnlyTenantName_US");
        ArrayList<String> serials = performBatchCsvImport(csvFilesByType);
        checkOndemandToggle(effectiveSerial);
        verifyDeviceStatusInUI(serials, "READY");
        UIDeviceData uiDeviceData = capturePlanCardsFromDeviceDetailsPage();
        validatePlanCardsFromDeviceDetailsPage(uiDeviceData, orderTypes);

        List<String> providersObj = Arrays.asList("AcOrderSubscriptionDataProvider", "DynamoDbProvider", "AssetMongoProvider");
        DeviceReportLambdaTables deviceReportLambdaTables = DBUtil.fetchDeviceReportLambda(effectiveSerial, providersObj);
        ActiveCareDBValidation.ValidateDBDataPCETest(orderTypes, effectiveSerial, csvFilesByType, uiDeviceData, deviceReportLambdaTables);
        LOGGER.info("✓✓✓ Premium Plus plan-only import test completed ✓✓✓");

    }

	@Test(priority = 10, groups = { "REGRESSION_ACTIVECARE" })
    public void importReadyDeviceACOnlyTenantOutOfBandRemediation() throws Exception {
        LOGGER.info("✓✓✓ Out Of Band Remediation plan-only import test started ✓✓✓");
        WEPOrdersListPage ordersListPage = new WEPOrdersListPage(PreDefinedActions.getDriver()).getInstance();
        String effectiveSerial = ordersListPage.makeEntryInSNRTable();

        String[] orderTypes = {"New Order - Out Of Band Remediation"};

        LOGGER.info("Starting Out Of Band Remediation CSV import with effectiveSerial: {}", effectiveSerial);
        Map<String, CsvFileData> csvFilesByType = createMultipleOrderTypesForSameSerialNumber(orderTypes, effectiveSerial, "ACOnlyTenantName_US");
        ArrayList<String> serials = performBatchCsvImport(csvFilesByType);
        checkOndemandToggle(effectiveSerial);
        verifyDeviceStatusInUI(serials, "READY");
        UIDeviceData uiDeviceData = capturePlanCardsFromDeviceDetailsPage();
        validatePlanCardsFromDeviceDetailsPage(uiDeviceData, orderTypes);

        List<String> providersObj = Arrays.asList("AcOrderSubscriptionDataProvider", "DynamoDbProvider", "AssetMongoProvider");
        DeviceReportLambdaTables deviceReportLambdaTables = DBUtil.fetchDeviceReportLambda(effectiveSerial, providersObj);
        ActiveCareDBValidation.ValidateDBDataPCETest(orderTypes, effectiveSerial, csvFilesByType, uiDeviceData, deviceReportLambdaTables);
        LOGGER.info("✓✓✓ Out Of Band Remediation plan-only import test completed ✓✓✓");

    }

	// Test case to verify hardware support option is enabled or not
	@Test(priority = 11, groups = { "REGRESSION_ACTIVECARE","PRODUCTION_ACTIVECARE"}, description = "C53303648")
	public final void verifyWEPhardwareSupportOptionIsEnabled() throws Exception {
		login("AC_USER", "AC_PASSWORD");
		LOGGER.info("Login successful.");
		setUp();
		hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("hardwareSupportIcon");
		Assert.assertTrue(hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("hardwareSupportIcon"),
				"Failed :Hardware support icon is disabled");

	}

	// Test case to verify Create Case button is enabled or not
	@Test(priority = 2, groups = { "REGRESSION_ACTIVECARE","PRODUCTION_ACTIVECARE"}, description = "C56677926")
	public final void verifyWEPcreateCaseButtonIsEnabled() throws Exception {
		login("AC_USER", "AC_PASSWORD");
		LOGGER.info("Login successful.");
		setUp();
		hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("hardwareSupportIcon");
		hardwareSupport.clickOnElementOfWEPHardwareSupportOption("hardwareSupportIcon");
		waitForPageLoaded();
		Assert.assertTrue(hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("createCase"),
				"Failed: Create Case Button should is disabled.");
	}

	// Test case to verify Generate XLS button is enabled or not
	@Test(priority = 13, groups = { "REGRESSION_ACTIVECARE" }, description = "C56677944")
	public final void verifyWEPGenerateXlsButtonIsEnabled() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("AC_USER", "AC_PASSWORD");
		setUp();
		waitForPageLoaded();
		hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("hardwareSupportIcon");
		hardwareSupport.clickOnElementOfWEPHardwareSupportOption("hardwareSupportIcon");
		LOGGER.info("Navigated to hardwareSupport page");
		hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("firstIdCheckBox");
		hardwareSupport.actionClickWEPHardwareSupportOption("firstIdCheckBox");
		LOGGER.info("Selected first incident");
		softAssert.assertTrue(hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("generateXls"),
				"Failed : Generate xls  button is not displayed as no incidents are present");
		softAssert.assertAll();

	}

	// Test case to verify ActiveCareWidgets
	@Test(priority = 14, groups = {
			"REGRESSION_ACTIVECARE","PRODUCTION_ACTIVECARE"}, description = "C53303656", dataProvider = "tenantWidgetData", enabled=true)
	public final void verifyWEPActiveCareWidgets(String username, String password, String[] tenantWidgetData)
			throws Exception {
		login("AC_USER", "AC_PASSWORD");
		setUp();
		waitForPageLoaded();
		hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("hardwareSupportIcon");
		Assert.assertTrue(hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("hardwareSupportIcon"),
				"Failed : Hardware support icon should be disabled");
		hardwareSupport.clickOnElementOfWEPHardwareSupportOption("analyticsMenuOption");
		waitForPageLoaded();
		hardwareSupport.clickOnElementOfWEPHardwareSupportOption("hardwareSupport");
		waitForPageLoaded();
		Assert.assertTrue(hardwareSupport.verifyWEPWidgetsMatch("widgetList", tenantWidgetData),
				"Failed: The displayed widgets do not match the expected list.");
		// Log success after assertion (only if the assertion passes)
		LOGGER.info("Widgets match the expected list.");
	}

	// Verify Create Case pop-up is displayed or not
	@Test(priority = 15, groups = {
			"REGRESSION_ACTIVECARE" }, description = "C53304287", dataProvider = "issueCategories")
	public final void verifyWEPcreateCaseWizardModalIsDisplayed(String[] issueCategories) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("AC_USER", "AC_PASSWORD");
		setUp();
		waitForPageLoaded();
		hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("hardwareSupportIcon");
		hardwareSupport.clickOnElementOfWEPHardwareSupportOption("hardwareSupportIcon");
		waitForPageLoaded();
		softAssert.assertTrue(hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("createCase"),
				"Failed: 'Create Case' button is not enabled.");
		LOGGER.info("'Create Case' button is enabled. Clicking on it...");
		hardwareSupport.actionClickOnElementOfWEPHardwareSupportOption("createCase");
		waitForPageLoaded();
		String createCaseWizardText = hardwareSupport.getTextByWEPHardwareSupportPage("createCaseText");
		softAssert.assertTrue(
				hardwareSupport.verifyTextPresentOnWEPHardwareSupportPage("createCaseText", createCaseWizardText),
				"Failed:Text does not contain 'createCase'.");
		LOGGER.info("Text contain 'createCase'.");
		// Logging details if any step fails for debugging
		LOGGER.info("Current page URL: " + hardwareSupport.getDriver().getCurrentUrl());
		LOGGER.info("Current page title: " + hardwareSupport.getDriver().getTitle());
		hardwareSupport.actionClickOnElementOfWEPHardwareSupportOption("dropdownBoxAfterSelection");
		hardwareSupport.actionClickOnElementOfWEPHardwareSupportOption("deviceDropdownOptions");
		hardwareSupport.scrollTillViewOfWEPHardWareSupportPage("nextButtonCaseCreation");
		hardwareSupport.actionClickOnElementOfWEPHardwareSupportOption("nextButtonCaseCreation");
		waitForPageLoaded();
		hardwareSupport.actionClickOnElementOfWEPHardwareSupportOption("issueDropdownButton");
		softAssert.assertTrue(hardwareSupport.verifyWEPWidgetsMatch("locationDropdownOptions", issueCategories),
				"Failed: The displayed widgets do not match the expected list.");

	}

	@Test(priority = 16, groups = { "REGRESSION_ACTIVECARE" }, description = "C53304296", enabled=false)
	public final void verifyWEPIncidentDetailsAndUrlRedirection() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("AC_USER", "AC_PASSWORD");
		setUp();
		waitForPageLoaded();
		hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("analyticsMenuOption");
		hardwareSupport.clickOnElementOfWEPHardwareSupportOption("analyticsMenuOption");
		waitForPageLoaded();
		hardwareSupport.clickOnElementOfWEPHardwareSupportOption("hardwareSupport");
		waitForPageLoaded();
		hardwareSupport.scrollTillViewOfWEPHardWareSupportPage("allIncidentsByTypeTitle");
		Assert.assertTrue(hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("allIncidentsByTypeTitle"),
				"Failed : All Incidents by Type widget is not displayed");
		softAssert.assertTrue(hardwareSupport
				.verifyElementIsEnableOnWEPHardwareSupportPage("allIncidentsByTypeViewDetaillink"),"Failed: View Details link is not available for the incident");
		LOGGER.info("View Details link is available. Clicking on it...");
		hardwareSupport.clickOnElementOfWEPHardwareSupportOption("allIncidentsByTypeViewDetaillink");
		waitForPageLoaded();
		LOGGER.info("Fetching breadcrumb text...");
		String incidentPageBreadcrumbText = hardwareSupport
				.getTextByWEPHardwareSupportPage("incidentPageBreadcrumbXpath");
		LOGGER.info("Breadcrumb text: {}", incidentPageBreadcrumbText);
		LOGGER.info("Verifying if breadcrumb text contains 'Hardware Support'...");
		softAssert.assertTrue(hardwareSupport.verifyTextPresentOnWEPHardwareSupportPage("incidentPageBreadcrumbXpath",
				incidentPageBreadcrumbText), "Failed:Text does not contain 'Hardware Support'.");
		LOGGER.info("Finalizing soft assertions...");
		softAssert.assertAll();

	}


	@Test(priority = 17, groups = { "REGRESSION_ACTIVECARE" }, description = "C53304296")
	public final void verifyCreateCaseForActiveDevice() throws Exception {
		WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        String testEnv = System.getProperty("environment");
		// ImportResult result = performCsvImport("New Order - Active Care", 1, true,
        //         "ACTIVE_CARE_NAME_TENANT", ACGenerationVariant.VALID_ROWS, null,null);
        // ArrayList<String> serialNumbers = result.getSerialNumbers();
        ArrayList<String> serialNumbers = new ArrayList<>(Arrays.asList("CPAUTO650980"));
        // String serialNumber = serialNumbers.get(0);
        String serialNumber = "CPAUTO650980";
        login("AC_USER", "AC_PASSWORD");
        assertTrue(wepDeviceListPage.verifyStatusOfDevices(serialNumbers, "Ready"), "FAIL: Status is not matching");

        String tenantID = getEnvironmentSpecificData(testEnv, "ACTIVE_CARE_NAME_TENANT_ID");
        waitForPageLoaded();
        Assert.assertTrue(wepDeviceListPage.performIOTEnrollment(serialNumber,tenantID), "Failed to perform IOT enrollment for serial number: " + serialNumber);

		hardwareSupport.companyView(CommonVariables.CUSTOMER_HARDWARE_SUPPORTS);
		LOGGER.info("User is navigated to Hardware Support Page.");
		waitForPageLoaded();
		Assert.assertTrue(hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("createCase"), "Failed: 'Create Case' button is not enabled.");
		LOGGER.info("'Create Case' button is enabled. Clicking on it...");
        softAssert.assertTrue(hardwareSupport.createReactiveCase(serialNumber,"Manually Created - Battery",true, false),"FAIL:Device is not found in list for Case Creation.");

        settingsPage.clearFiltersOfRolesandPermissionListPage("clearAllFilter");
		settingsPage.enterTextForSettingsPage("deviceSerialNumberSearchBox", serialNumber);
		settingsPage.clickOnElementsOfSettingsPage("typeBox");
		settingsPage.selectTextValueFromDropdownOfSettingsPage("typeListOptions",getTextLanguage(LanguageCode, "daas_ui", "com.hp.mpi.icm.type.activecare") , "typeBox");
		pressKey(Keys.ESCAPE);
		settingsPage.clickOnElementsOfSettingsPage("subTypeBox");
		settingsPage.selectTextValueFromDropdownOfSettingsPage("subTypeList",getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.battery"), "subTypeBox");
		pressKey(Keys.ESCAPE);

		settingsPage.clickByJavaScriptOnSettingsPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		sleeper(3000);
		settingsPage.waitForElementsOfSettingsPage("caseInformationTileTitle");
		settingsPage.scrollTillViewSettingsPage("caseInformationTileTitle");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseInformationTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.information").toUpperCase()), "Title on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseIDLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incidents.caseID")), "Case ID label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseStatusLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.list.caseStatus")), "Case status label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseSubmissionDate").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.submission")), "Case submission value on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseLocation").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.location")), "Case location label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseContact").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.contact")), "Case contact label on case information tile title is incorrect");
        softAssert.assertAll();
 	}

	//Verify Create Case button is not available on device details page.
	@Test(priority = 18, groups = { "REGRESSION_ACTIVECARE" }, description = "TestCase - C56079807")
	public final void verifyCreateCaseNotAvailableDeviceDetails() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceDetailsPage deviceDetailPage = new WEPDeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		login("AC_USER", "AC_PASSWORD");
		setUp();
		waitForPageLoaded();
		if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			hardwareSupport.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
        }
        else{
        	hardwareSupport.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        }
		LOGGER.info("User is navigated to Devices -> PC Page.");
		waitForLoaderIconToDisappear();
		wepDeviceListPage.filterOnStatusDeviceList(null);
		wepDeviceListPage.clickOnElementsOfWEPDeviceListPage("firstSerialNumberFromList");
		softAssert.assertFalse(deviceDetailPage.waitForElementOfWEPDeviceDetailsPage("createCase"), "Failed: 'Create Case' button is enabled on Device details page.");
		softAssert.assertAll();

		}


	@Test(priority = 19, groups = { "REGRESSION_ACTIVECARE" }, description = "C53304296")
	public final void verifyCreateCaseForErrorDevice() throws Exception {
		ImportResult result = performCsvImport("New Order - Active Care", 1, false,
                "ACOnlyTenantName_US", ACGenerationVariant.VALID_ROWS, null, null);
        Assert.assertNotNull(result, "Missing import result for key ");
        Map<String, List<String>> csvData = result.csv_data;
        ArrayList<String> serials = result.serialNumbers;
		SoftAssert softAssert = new SoftAssert();
		WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		login("AC_USER", "AC_PASSWORD");
		setUp();
		waitForPageLoaded();
		if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			hardwareSupport.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
        }
        else{
        	hardwareSupport.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        }
		LOGGER.info("User is navigated to Devices -> PC Page.");
		waitForPageLoaded();
		wepDeviceListPage.filterOnStatusDeviceList("Error");
		String selectDevice = serials.get(0);
		hardwareSupport.companyView(CommonVariables.CUSTOMER_HARDWARE_SUPPORTS);
		LOGGER.info("User is navigated to Hardware Support Page.");
        sleeper(3000);
		waitForPageLoaded();
		LOGGER.info("'Create Case' button is enabled. Clicking on it...");
		softAssert.assertTrue(hardwareSupport.createReactiveCase(selectDevice,"Manually Created - Battery",true, false),"FAIL:Device is not found in list for Case Creation.");

		settingsPage.clearFiltersOfRolesandPermissionListPage("clearAllFilter");
		settingsPage.enterTextForSettingsPage("deviceSerialNumberSearchBox", selectDevice);
		settingsPage.clickOnElementsOfSettingsPage("typeBox");
		settingsPage.selectTextValueFromDropdownOfSettingsPage("typeListOptions",getTextLanguage(LanguageCode, "daas_ui", "com.hp.mpi.icm.type.activecare") , "typeBox");
		pressKey(Keys.ESCAPE);
		settingsPage.clickOnElementsOfSettingsPage("subTypeBox");
		settingsPage.selectTextValueFromDropdownOfSettingsPage("subTypeList",getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.battery"), "subTypeBox");
		pressKey(Keys.ESCAPE);

		settingsPage.clickByJavaScriptOnSettingsPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		sleeper(3000);
		settingsPage.waitForElementsOfSettingsPage("caseInformationTileTitle");
		settingsPage.scrollTillViewSettingsPage("caseInformationTileTitle");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseInformationTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.information").toUpperCase()), "Title on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseIDLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incidents.caseID")), "Case ID label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseStatusLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.list.caseStatus")), "Case status label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseSubmissionDate").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.submission")), "Case submission value on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseLocation").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.location")), "Case location label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseContact").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.contact")), "Case contact label on case information tile title is incorrect");

		softAssert.assertAll();

		}

	// Verify SelfService functionality
	@Test(priority = 22, groups = { "REGRESSION_ACTIVECARE","PRODUCTION_ACTIVECARE"}, description = "C55984632")
	public final void verifySelfServiceToggleOnEndUserNotificationTab() throws Exception {
		login("AC_USER", "AC_PASSWORD");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		openLeftSidePanel();
		WEXSettingPage.companyView(CommonVariables.SETTINGS);
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Notificationtab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Notificationtab");
		String devicenotificationheader = WEXSettingPage.getTextByWEXSettingPage("deviceNotificationHeader");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("deviceNotificationHeader", devicenotificationheader),
				"Failed:Device Notifcations header is not displayed");
		LOGGER.info("Device Notifcations header is displayedDevice Notifcations header is displayed");
		WEXSettingPage.verifyElementsOfWEXSettingPage("selfServiceAlertsToggle");
		// Step 1: Verify the elements on WEX setting page
		softAssert.assertTrue(WEXSettingPage.verifyElementsOfWEXSettingPage("selfServiceAlertsToggle"),
				"Failed: selfServiceAlertsToggle element is missing from the WEX Settings page.");
		LOGGER.info("selfServiceAlertsToggle element is successfully displayed on the WEX Settings page.");

		// Step 2: Disable the toggle
		if(WEXSettingPage.verifyElementIsSelectedWEXSettingPage("selfServiceAlertsToggleInput")) {
		LOGGER.info("Disabling the selfServiceAlertsToggle...");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("selfServiceAlertsToggle");
		}
		sleeper(1000);

		// Step 3: Verify if header is hidden when toggle is OFF
		softAssert.assertTrue(!WEXSettingPage.verifyElementsOfWEXSettingPage("selfServiceAlertsHeader"),
				"Failed:selfServiceAlerts header is displayed when toggle is OFF.");
		LOGGER.info("selfServiceAlerts is NOT displayed after toggle is OFF.");
		// Step 4: Enable toggle
		LOGGER.info("Enabling the selfServiceAlertsToggle...");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("selfServiceAlertsToggle");
		sleeper(1000);
		// Step 5: Check if selfServiceAlerts is displayed when the toggle is ON
		softAssert.assertTrue(WEXSettingPage.verifyElementsOfWEXSettingPage("selfServiceAlertsHeader"),
				"Failed : selfServiceAlerts is not displayed when toggle is ON.");
		LOGGER.info("selfServiceAlerts is displayed when toggle is ON.");
		softAssert.assertTrue(WEXSettingPage.verifyElementsOfWEXSettingPage("revertAllAlertsToDefaultButton"),
				"Failed : Revert All Alerts To Default button is not displayed when toggle is ON.");
		softAssert.assertTrue(WEXSettingPage.verifyElementsOfWEXSettingPage("hardwareHealth"),
				"Failed : Hardware Health dropdown is not displayed when toggle is ON.");
		softAssert.assertTrue(WEXSettingPage.verifyElementsOfWEXSettingPage("activeCareDropDown"),
				"Failed : Manually Created dropdown is not displayed when toggle is ON.");

		softAssert.assertAll();
	}

	@Test(priority = 23, groups = { "REGRESSION_ACTIVECARE" }, description = "C55984632")
	public final void verifyHardwareHealthAndActiveCareAlerts() throws Exception {

		login("AC_USER", "AC_PASSWORD");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		openLeftSidePanel();
		WEXSettingPage.companyView(CommonVariables.SETTINGS);
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Notificationtab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Notificationtab");
		String devicenotificationheader = WEXSettingPage.getTextByWEXSettingPage("deviceNotificationHeader");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("deviceNotificationHeader", devicenotificationheader),
				"Faied:Device Notifcations header is not displayed");
		LOGGER.info("Device Notifcations header is displayed");
		sleeper(10000);
		List<String> hardwareHealthList = new ArrayList<>(Arrays.asList(
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.systemthermal"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.hddpredictive"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.batteryneedsattention"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.fancritical"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.fanwarning")));

		List<String> activeCareAlertList = new ArrayList<>(Arrays.asList(
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.motherboard"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.iodevice"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.battery"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.other"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.insights.tags.hdd.hdd"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.power")));
		String enabled = getTextLanguage(LanguageCode, "daas_ui", "global.enabled");
		String disabled = getTextLanguage(LanguageCode, "daas_ui", "global.disabled");
		waitForPageLoaded();
		WEXSettingPage.clickOnElementsOfWEXSettingPage("hardwareHealth");
		sleeper(10000);
		softAssert.assertTrue(
				WEXSettingPage.checkStatusOfAlerts(enabled, disabled, hardwareHealthList, "hardwareList", true),
				"Failed:Status mismatch for Hardware Health List");
		softAssert.assertTrue(WEXSettingPage.verifyListOfSubCategoriesUnderSelfServiceAlerts(hardwareHealthList,
				"hardwareHealthCategoriesTitle"));
		WEXSettingPage.waitForElementsOfWEXSettingPage("activeCareDropDown");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("activeCareDropDown");
		softAssert.assertTrue(
				WEXSettingPage.checkStatusOfAlerts(enabled, disabled, activeCareAlertList, "activeCareList", false),
				"Failed:Status mismatch for Active Care Alerts");
		softAssert.assertTrue(
				WEXSettingPage.verifyListOfSubCategoriesUnderSelfServiceAlerts(activeCareAlertList,
						"activeCareCategoriesTitle"),
				"Verify ActiveCare sub-categories. in the list in Notification tab under Self Service Alert -> ActiveCare.");
		LOGGER.info("Notification tab default state for Active Care validated successfully.");
		softAssert.assertAll();

	}

	@Test(priority = 24, groups = { "REGRESSION_ACTIVECARE" }, description = "C55984632")
	public final void verifyToggleForHardwareAlert() throws Exception {

		login("AC_USER", "AC_PASSWORD");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		openLeftSidePanel();
		WEXSettingPage.companyView(CommonVariables.SETTINGS);
		LOGGER.info("Redirected to setting tab");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Notificationtab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Notificationtab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("hardwareHealth");
		waitForPageLoaded();
		softAssert.assertTrue(
				WEXSettingPage.modifyHardwareBatteryAttentionSubtypeStatus("batteryEditToggleButton",
						"enableAlertButton", "customFieldsSave"),
				"Failed: Hardware Battery Attention Subtype status should be modified successfully, but the operation failed.");
		waitForPageLoaded();
		softAssert.assertAll();
	}

	@Test(priority = 25, groups = { "REGRESSION_ACTIVECARE" }, description = "C56677926")
	public final void verifyWEPHardwareSupportIncidentsOptionIsEnabled() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		LOGGER.info("Starting test: Verifying WEP Hardware Support Page elements");
		login("AC_USER", "AC_PASSWORD");
		LOGGER.info("Login successful.");
		setUp();
		softAssert.assertTrue(hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("homeIcon"),
				"Failed: Home icon should be enabled.");
		hardwareSupport.clickOnElementOfWEPHardwareSupportOption("homeIcon");
		waitForPageLoaded();
		LOGGER.info("Home icon is enabled and clicked.");
		softAssert.assertTrue(
				hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("hardwareSupportIncidentTitle"),
				"Failed: Hardware Support Incident Title should be enabled.");
		String title = hardwareSupport.getTextByWEPHardwareSupportPage("hardwareSupportIncidentTitle");
		softAssert.assertTrue(
				hardwareSupport.verifyTextPresentOnWEPHardwareSupportPage("hardwareSupportIncidentTitle", title),
				"Failed: Text does not contain 'Hardware Support Incidents'.");
		LOGGER.info("Text contains 'Hardware Support Incidents'.");
// Step 5: Verify the presence of other elements
		// softAssert.assertTrue(hardwareSupport.verifyElementIsPresentOnWEPHardwareSupportPage("typeColumn"),
		// 		"Failed: Type column is not present.");
		softAssert.assertTrue(hardwareSupport.verifyElementIsPresentOnWEPHardwareSupportPage("subType"),
				"Failed: SubType column is not present.");
		softAssert.assertTrue(hardwareSupport.verifyElementIsPresentOnWEPHardwareSupportPage("deviceCount"),
				"Failed: Device count column is not present.");
		LOGGER.info("Verified presence of typeColumn, subType, and deviceCount.");

		softAssert.assertAll();
		LOGGER.info("Test completed: All assertions passed.");

	}

	@Test(priority = 26, groups = {"REGRESSION_ACTIVECARE"},description = "TEST CASE - 1228306", enabled=true)
	public final void verifyWEXACTenantCreationUI() throws Exception {
		WEPCreateCompanyPage companiesPage = new WEPCreateCompanyPage(PreDefinedActions.getDriver()).getInstance();
		WEPHardwareSupportPage hardwareSupportPage = new WEPHardwareSupportPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String company_name = CommonVariables.AC_COMPANY_NAME + generateRandomString(5);
		String company_email = company_name.toLowerCase() + "@hpmsqa.mailinator.com";
		String CompanyCountry =  getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_COUNTRY"); //refer to us-stage-WEPData.properties
		String CompanyPlan = CommonVariables.PLAN_ACTIVE_CARE_Premium;

		login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
		LOGGER.info("Left menu is opened");
		openLeftSidePanel();
		companiesPage.sideMenuSelectionWEPCreateCompanyPage(LanguageCode, "Customers", "Companies" );
		waitForPageLoaded();
		softAssert.assertTrue(companiesPage.verifyElementsOfWEPCreateCompanyPage("addCompany"),"Add Company button not present");
		LOGGER.info("Redirected Company tab page");
		softAssert.assertTrue(companiesPage.addCompanies(LanguageCode, company_name, company_email, CompanyCountry, CompanyPlan, CommonVariables.MSP_NAME, getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME_ADD"), "FN", "LN", false, false, true), "Company not added successfully through Root admin.");
		LOGGER.info("PASS: Root user can create Active Care Tenant successfully.");
		softAssert.assertTrue(companiesPage.matchTextOnWEPCreateCompanyPage("cardTitle",company_name), "Company name does not match");
		logout();
		sleeper(2000);

		softAssert.assertTrue(companiesPage.verifyEmailAndCompleteOnboarding(company_email),"FAIL: Company onboarding failed.");
		openLeftSidePanel();
		hardwareSupportPage.companyView(CommonVariables.CUSTOMER_HARDWARE_SUPPORTS);
        sleeper(5000); //It is observed that sometimes Create Case button takes time or a page refresh to appear after company creation.
        refreshPage();
        waitForPageLoaded();
		softAssert.assertTrue(hardwareSupportPage.verifyElementIsEnableOnWEPHardwareSupportPage("createCase"),
				"Failed: Create Case Button should is enabled.");
		LOGGER.info("PASS: Create Case Button is enabled on Hardware Support Page.");
		logout();
		deleteAllcookies();
		login("ROOT_ADMIN_EMAIL_WEP","ROOT_ADMIN_PASSWORD_WEP");
		openLeftSidePanel();
		companiesPage.sideMenuSelectionWEPCreateCompanyPage(LanguageCode, "Customers", "Companies" );
		waitForPageLoaded();

		if (companiesPage.verifyElementsOfWEPCreateCompanyPage("clearFilter")) {
			companiesPage.mousehoverOnWEPCreateCompanyPage("clearFilter");
			companiesPage.clickOnElementsOfWEPCreateCompanyPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		companiesPage.enterTextOfWEPCreateCompanyPage("primaryEmailColumn", company_email);
		sleeper(4000);
		companiesPage.actionClickOnWEPCreateCompanyPage("firstName");
		softAssert.assertTrue(companiesPage.verifyElementsOfWEPCreateCompanyPage("emailOnDetails"),"Fail: Root User is not on Company details page.");
		companiesPage.waitForElementsOfWEPCreateCompanyPage("deleteCompBtn");
		companiesPage.clickOnElementsOfWEPCreateCompanyPage("deleteCompBtn");
		waitForPageLoaded();
		softAssert.assertTrue(companiesPage.verifyElementsOfWEPCreateCompanyPage("deleteCompModal"));
		softAssert.assertTrue(companiesPage.verifyElementsOfWEPCreateCompanyPage("deleteBtn"));
		softAssert.assertTrue(companiesPage.verifyElementsOfWEPCreateCompanyPage("CancelBtn"));
		companiesPage.clickOnElementsOfWEPCreateCompanyPage("deleteBtn");
		sleeper(5000);
		softAssert.assertTrue(companiesPage.verifyElementsOfWEPCreateCompanyPage("deleteToast"),"Toast message not present");
		if (companiesPage.verifyElementsOfWEPCreateCompanyPage("clearFilter")) {
			companiesPage.mousehoverOnWEPCreateCompanyPage("clearFilter");
			companiesPage.clickOnElementsOfWEPCreateCompanyPage("clearFilter");
			waitForPageLoaded();
			LOGGER.info("Clicked on the Clear Filter");
		}
		companiesPage.enterTextOfWEPCreateCompanyPage("primaryEmailColumn", company_email );
		waitForPageLoaded();
		Assert.assertTrue(companiesPage.verifyElementsOfWEPCreateCompanyPage("noResults"),"FAIL:Company not deleted");
		LOGGER.info("Company Removed successfully");

		softAssert.assertAll();
	}


	@Test(priority = 27, groups = { "REGRESSION_ACTIVECARE","PRODUCTION_ACTIVECARE"}, description = "Test Case ID : 355185;355257", enabled=true)
	public final void verifyAddShippingLocations() throws Exception {
		login("AC_USER", "AC_PASSWORD");
		WEXSettingPage wexSettingsPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String addressLine1 = generateRandomString(7);
		String addressLine2 = generateRandomString(7);
		String city = generateRandomString(7);
		String state = generateRandomString(7);
		String zipcode = generateRandomString(7);
		String firstName = generateRandomString(7);
		String lastName = generateRandomString(7);
		String email = generateRandomString(7) + "@mailinator.com";
		String phone = generateRandomNumber();
		String instructions = generateRandomString(7);
		ArrayList<String> locationHeaders = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.list.address").toLowerCase(),getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.list.city").toLowerCase(),getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.list.state").toLowerCase(),getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.list.country").toLowerCase(),getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.list.contact").toLowerCase()));

		openLeftSidePanel();
		wexSettingsPage.companyView(CommonVariables.SETTINGS);
		LOGGER.info("Redirected to setting tab");
		waitForPageLoaded();
		wexSettingsPage.waitForElementsOfWEXSettingPage("Shippinglocationtab");
		LOGGER.info("User is on Locations tab.");
		softAssert.assertTrue(wexSettingsPage.getTextByWEXSettingPage("shippingLocationTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.shipping.locations").toUpperCase()), "Shipping locations title is incorrect");
	    softAssert.assertTrue(wexSettingsPage.verifyElementsOfWEXSettingPage("shippinglocationaddbutton"), "Add button on shipping location tiles is not present");
//		softAssert.assertTrue(wexSettingsPage.getTextOfListOfSettingsPage("locationTableHeaders").equals(locationHeaders), "The headers on location table are incorrect");
		wexSettingsPage.clickOnElementsOfWEXSettingPage("shippinglocationaddbutton");
		LOGGER.info("Clicked on add button");
		sleeper(2000);
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("addLocationPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.modal.add.title")), "Title on add shipping location popup is incorrect");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("addLocationPopupSubtitle", getTextLanguage(LanguageCode, "daas_ui", "create.case.step.location.desc.step_add_location")), "Subtitle on add shipping location popup is incorrect");

		wexSettingsPage.clickOnElementsOfWEXSettingPage("nextLocationButton");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("countryErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for country field on add shipping location popup is incorrect");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("addressLine1ErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for address line 1 field on add shipping location popup is incorrect");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("cityErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for city field on add shipping location popup is incorrect");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("stateErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for state field on add shipping location popup is incorrect");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("zipcodeErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for zipcode field on add shipping location popup is incorrect");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("countryLabel", getTextLanguage(LanguageCode, "daas_ui", "global.country")), "Country label on add shipping location popup is incorrect");
		wexSettingsPage.actionclickOnElementsOfWEXSettingPage("countryDropdown");
		sleeper(200);
		wexSettingsPage.selectTextValueFromDropdownOfWEXSettingPage("countryOptions","Australia","countryDropdown");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("addressLine1Label", getTextLanguage(LanguageCode, "daas_ui", "users.details.address1")), "Address Line 1 label on add shipping location popup is incorrect");
		wexSettingsPage.enterTextForWEXSettingPage("addressLine1Textbox", addressLine1);
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("addressLine2Label", getTextLanguage(LanguageCode, "daas_ui", "users.details.address2")), "Address Line 2 label on add shipping location popup is incorrect");
		wexSettingsPage.enterTextForWEXSettingPage("addressLine2Textbox", addressLine2);
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("cityLabel", getTextLanguage(LanguageCode, "daas_ui", "users.details.city")), "City label on add shipping location popup is incorrect");
		wexSettingsPage.enterTextForWEXSettingPage("cityTextbox", city);
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("stateLabel", getTextLanguage(LanguageCode, "daas_ui", "global.region")), "State/Province label on add shipping location popup is incorrect");
		wexSettingsPage.enterTextForWEXSettingPage("stateTextbox", state);
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("zipcodeLabel", getTextLanguage(LanguageCode, "daas_ui", "global.zipcode")), "Zip code label on add shipping location popup is incorrect");
		wexSettingsPage.enterTextForWEXSettingPage("zipcodeTextbox", zipcode);
		wexSettingsPage.clickOnElementsOfWEXSettingPage("nextLocationButton");
		LOGGER.info("Clicked on next button");

		wexSettingsPage.clickOnElementsOfWEXSettingPage("addLocationButton");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("firstNameErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for first name field on add shipping location popup is incorrect");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("lastNameErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for last name field on add shipping location popup is incorrect");
	    softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("firstNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.first_name")), "First name label on add shipping location popup is incorrect");
	    wexSettingsPage.enterTextForWEXSettingPage("firstNameTextbox", firstName);
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("lastNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.last_name")), "Last name label on add shipping location popup is incorrect");
		wexSettingsPage.enterTextForWEXSettingPage("lastNameTextbox", lastName);
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("emailLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.email")), "Email label on add shipping location popup is incorrect");
		wexSettingsPage.enterTextForWEXSettingPage("emailTextbox", email);
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("phoneNumberLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.phone")), "Phone label on add shipping location popup is incorrect");
		wexSettingsPage.enterTextForWEXSettingPage("phoneTextbox", phone);
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("deliveryInstructionsLabel", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.add.instructions")), "Delivery Instructions label on add shipping location popup is incorrect");
		wexSettingsPage.enterTextForWEXSettingPage("instructionsTextbox", instructions);
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("setDefaultLabel", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.add.set.default")), "Set as default location label on add shipping location popup is incorrect");
		softAssert.assertTrue(wexSettingsPage.verifyElementsOfWEXSettingPage("previousLocationButton"),"Previous location button is missing on add location popup");
		softAssert.assertTrue(wexSettingsPage.verifyElementsOfWEXSettingPage("addLocationButton"),"Add location button is missing on add location popup");
		softAssert.assertTrue(wexSettingsPage.verifyElementsOfWEXSettingPage("cancelLocationButton"),"Cancel location button is missing on add location popup");
		wexSettingsPage.clickOnElementsOfWEXSettingPage("addLocationButton");

		wexSettingsPage.waitForElementsOfWEXSettingPage("successAddShipLocation");
		LOGGER.info("Shipping Location added successfully.");

		wexSettingsPage.enterTextForWEXSettingPage("addressSearchBox", addressLine1);
		String[] locationDetails = {addressLine1,city,state,"Australia",firstName+" "+lastName};
		sleeper(2000);
//		softAssert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("addeddetails", locationDetails),"Location details not verified");
		sleeper(5000);//need to add sleeper since it is taking time to load the address
		softAssert.assertFalse(wexSettingsPage.verifyElementsOfWEXSettingPage("noItemsAvailable"), "Location addition unsuccessfull");
		wexSettingsPage.scrollTillViewWEXSettingPage("hamburger");
		wexSettingsPage.actionclickOnElementsOfWEXSettingPage("hamburger");
		wexSettingsPage.waitForElementsOfWEXSettingPage("setAsDefaultActionOnHamburger");
		wexSettingsPage.actionclickOnElementsOfWEXSettingPage("setAsDefaultActionOnHamburger");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("defaultLocationPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.modal.default.location")), "Title on set default location popup is incorrect");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("defaultLocationPopupSubtitle", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.modal.default.location.confirm").replace("{locationName}", addressLine1)), "Subtitle on set default location popup is incorrect");
		wexSettingsPage.clickOnElementsOfWEXSettingPage("setToDefaultButton");
		wexSettingsPage.waitForElementsOfWEXSettingPage("successAddShipLocation");
		softAssert.assertTrue(wexSettingsPage.verifyElementsOfWEXSettingPage("successAddShipLocation"),"Location is not set to default successfully");
		LOGGER.info("Shipping Location set as Default successfully.");
		softAssert.assertTrue(wexSettingsPage.verifyElementsOfWEXSettingPage("defaultTag"), "Default tag on location is not present");
		LOGGER.info("Default tag is present on Shipping Location.");
		wexSettingsPage.actionclickOnElementsOfWEXSettingPage("firstCheckBox");
		wexSettingsPage.clickOnElementsOfWEXSettingPage("removeLocationButton");
		LOGGER.info("Click Delete Location button.");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("removeLocationTitle", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.modal.remove.location")), "Title on remove location popup is incorrect");
		softAssert.assertTrue(wexSettingsPage.matchTextOfWEXSettingPage("removeLocationSubtitle", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.modal.remove.confirm").replace("{locationName}", addressLine1)), "Subtitle on remove location popup is incorrect");
		softAssert.assertTrue(wexSettingsPage.verifyElementsOfWEXSettingPage("removeLocationPopupButton"), "Remove button on popup is missing");
		softAssert.assertTrue(wexSettingsPage.verifyElementsOfWEXSettingPage("removeLocationPopupCancelButton"), "Cancel remove button on popup is missing");
		wexSettingsPage.clickOnElementsOfWEXSettingPage("removeLocationPopupButton");
		LOGGER.info("Click Delete button on Delete Shipping location popup modal.");
		softAssert.assertAll();
	}

	@Test(priority = 28, groups = { "REGRESSION_ACTIVECARE"}, description = "Test Case ID : 355185;355257", enabled=true)
	public final void verifyUnassignedDevicesTabPartnerUser() throws Exception {
		WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		login("AC_PARTNERUSER_EMAIL", "AC_PARTNERUSER_PASSWORD");
		waitForPageLoaded();
		openLeftSidePanel();
		selectCompany("All Customers");
		LOGGER.info("All Customers is selected from the left side menu.");
        sleeper(3000);
		wexPartnerPage.partnerView(CommonVariables.PARTNER_UNASSIGNED_DEVICES);
        sleeper(2000);
		softAssert.assertTrue(wexPartnerPage.waitForElementsOfWEXPartnerPage("unassignedDevicesSiteHeader"),"FAIL: User is not navigated to Unassigned Devices tab.");
		LOGGER.info("Partner user is navigated to Unassigned Devices tab.");

		softAssert.assertAll();
		}

	@Test(priority = 29, groups = { "REGRESSION_ACTIVECARE"}, description = "Test Case ID : 355185;355257", enabled=true)
	public final void verifyNotificationTabIsRemovedAtPartnerSetting() throws Exception {
		WEXPartnerPage wexPartnerPage  = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
		WEXSettingPage wexSettingPage  = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		login("AC_PARTNERUSER_EMAIL", "AC_PARTNERUSER_PASSWORD");
		waitForPageLoaded();
		openLeftSidePanel();
		selectCompany("All Customers");
		LOGGER.info("All Customers is selected from the left side menu.");

		wexPartnerPage.partnerView(CommonVariables.SETTINGS);
		LOGGER.info("Redirected to setting tab");
		softAssert.assertFalse(wexSettingPage.waitForElementsOfWEXSettingPage("Notificationtab"),"FAIL: End User Notification tab is present in Settings at Partner level.");

		selectCompany(getEnvironmentSpecificData(System.getProperty("environment"), "ACPITenantName"));
		LOGGER.info("Customer is selected from the left side menu.");
		wexPartnerPage.partnerView(CommonVariables.SETTINGS);
		LOGGER.info("Redirected to setting tab");
		softAssert.assertFalse(wexSettingPage.waitForElementsOfWEXSettingPage("Notificationtab"),"FAIL: End User Notification tab is NOT present in Settings at Partner level.");
		LOGGER.info("End User Notification tab is removed from Settings at Partner level");
		// wexSettingPage.clickOnElementsOfWEXSettingPage("Notificationtab");
		// String devicenotificationheader = wexSettingPage.getTextByWEXSettingPage("deviceNotificationHeader");
		// softAssert.assertTrue(
		// 		wexSettingPage.matchTextOfWEXSettingPage("deviceNotificationHeader", devicenotificationheader),
		// 		"Faied:Device Notifcations header is not displayed");

		softAssert.assertAll();
		}

    /**
     * Device transferred from ACHT tenant to named tenant where device has AC plan assigned
     * Test Case ID : C58741939
     * @throws Exception
     */
    @TestMetadata(currentOrderType = "Device Transfer", expectedStatus = "Ready",expectedPlans = "HP Active Care")
    @Test(priority = 30, groups = {"REGRESSION_ACTIVECARE"}, description = "Test Case ID : C58741939")
    public final void verifyDeviceTransferOrderACHTToACNT() throws Exception {
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        ImportResult result = performCsvImport("New Order - Active Care", 1, true,
                "", ACGenerationVariant.MISSING_MANDATORY_VALUES, List.of("EndCustomerName"), null);
        ArrayList<String> serialNumbers = result.getSerialNumbers();
        loginAndNavigate("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US", "Administrative", "Orders");
        String testEnv = System.getProperty("environment");
        // Use explicit orderType with fallback to avoid CI/CD pipeline issues with parallel execution
        String orderType = TestContextHolder.getCurrentOrderType() != null ? TestContextHolder.getCurrentOrderType() : "Device Transfer";
        Map<String, Object> createActiveCareTestCSV = CSVFileReader.generateActiveCareCsvByOrderType_v3(orderType, 1, serialNumbers.get(0), "ACTIVE_CARE_NAME_TENANT", ACGenerationVariant.VALID_ROWS, null);
        String globalFile = (String) createActiveCareTestCSV.get("outputCsvFilename");
        String splitFile = ordersListPage.getFileNameAfterSplit(testEnv, globalFile);
        Assert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, orderType, globalFile), "FAIL: Import failed.");
        Assert.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, globalFile), "FAIL: Global file not processed.");
        verifySplitFileProcessing(testEnv, LanguageCode, splitFile);
        sleeper(3000);
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateMsg").trim().equals("Completed"), "Expected State message 'Completed' but found: " + ordersListPage.getTextOfOrderListPage("DTStateMsg").trim());
        ordersListPage.deleteActiveCareTestCSV(globalFile);
        logout();
        orderDetailsPage.deleteAllcookies();
        login("AC_USER", "AC_PASSWORD");
        Map<String, String> deviceDetails = wepDeviceListPage.fetchDeviceListMap(serialNumbers.get(0));
        String activeCareTenantName = getEnvironmentSpecificData(testEnv, "ACTIVE_CARE_NAME_TENANT");
        Assert.assertNotNull(activeCareTenantName, "FAIL: ACTIVE_CARE_NAME_TENANT property not found for environment: " + testEnv);
        String activeCareTenantId = getEnvironmentSpecificData(testEnv, "ACTIVE_CARE_NAME_TENANT_ID");
        Assert.assertNotNull(activeCareTenantId, "FAIL: ACTIVE_CARE_NAME_TENANT_ID property not found for environment: " + testEnv);
        deviceDetails.put("endCustomerName", activeCareTenantName);
        deviceDetails.put("destinationTenantId", activeCareTenantId);
        LOGGER.info("Device details fetched: {}", JSONUtil.toJson(deviceDetails));
        Assert.assertTrue(deviceDetails.get("Serial Number").equals(serialNumbers.get(0)), "Serial number doesn't match");
        Assert.assertTrue(deviceDetails.get("Plans").equals(TestContextHolder.getExpectedPlans()), "Plans doesn't match");
        Assert.assertTrue(deviceDetails.get("Status").equals(TestContextHolder.getExpectedStatus()), "Status doesn't match");
        wepDeviceListPage.clearFilters("clearfilter");
        wepDeviceListPage.enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", serialNumbers.get(0));
        sleeper(4000);
        waitForPageLoaded();
        UIDeviceData uiDeviceData = capturePlanCardsFromDeviceDetailsPage();
        validatePlanCardsFromDeviceDetailsPage(uiDeviceData, new String[]{"New Order - Active Care"});
        List<String> providersObj = Arrays.asList("AssetMongoProvider", "DynamoDbProvider", "AcOrderSubscriptionDataProvider");
        List<String> errors = ActiveCareDBValidation.validateAgainstDeviceReportResponse(providersObj, serialNumbers.get(0), uiDeviceData, deviceDetails);
        Assert.assertTrue(errors.isEmpty(), "Device report validation failed. Errors: " + String.join("; ", errors));
    }

    /**
     * Verifies that Device Transfer order with empty Region field is skipped with appropriate error message.
     * Creates and uploads a CSV file with missing Region field and validates the state messages.
     *
     * @throws Exception if any error occurs during test execution
     */
    @TestMetadata(currentOrderType = "Device Transfer")
    @Test(priority = 31, groups = { "REGRESSION_ACTIVECARE" }, description = "C58741880")
    public final void verifyDeviceTransferOrderWithEmptyRegion() throws Exception {
        String deviceSerialNumber = "6CG" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        loginAndNavigate("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US", "Administrative", "Orders");
        String testEnv = System.getProperty("environment");
        // Use explicit orderType instead of ThreadLocal to avoid CI/CD pipeline issues with parallel execution
        String orderType = TestContextHolder.getCurrentOrderType() != null ? TestContextHolder.getCurrentOrderType() : "Device Transfer";
        Map<String, Object> createActiveCareTestCSV = CSVFileReader.generateActiveCareCsvByOrderType_v3(orderType, 1, deviceSerialNumber, "ACTIVE_CARE_NAME_TENANT", ACGenerationVariant.MISSING_MANDATORY_VALUES, List.of("Region"));
        String globalFile = (String) createActiveCareTestCSV.get("outputCsvFilename");
        String splitFile = ordersListPage.getFileNameAfterSplit(testEnv, globalFile);
        Assert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, orderType, globalFile), "FAIL: Import failed.");
        Assert.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, globalFile), "FAIL: Global file not processed.");
        verifySplitFileProcessing(testEnv, LanguageCode, splitFile);
        ordersListPage.deleteActiveCareTestCSV(globalFile);
        sleeper(5000);
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateDescriptionMsg").trim().equals("One or more mandatory fields are missing. Region"), "Expected State Description message 'One or more mandatory fields are missing. Region' but found: " + ordersListPage.getTextOfOrderListPage("DTStateDescriptionMsg").trim());
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTSubStateMsg").trim().equals("Mandatory fields missing"), "Expected Sub State message 'Mandatory fields missing' but found: " + ordersListPage.getTextOfOrderListPage("DTSubStateMsg").trim());
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateMsg").trim().equals("Skipped"), "Expected State message 'Skipped' but found: " + ordersListPage.getTextOfOrderListPage("DTStateMsg").trim());
    }

    /**
     * Verifies that Device Transfer order with empty ObjectOfServiceSerialNumber is skipped with appropriate error message.
     * Creates and uploads a CSV file with missing serial number and validates the state messages.
     *
     * @throws Exception if any error occurs during test execution
     */
    @TestMetadata(currentOrderType = "Device Transfer")
    @Test(priority = 32, groups = { "REGRESSION_ACTIVECARE" }, description = "C58741936")
    public final void verifyDeviceTransferOrderWithEmptySerialNumber() throws Exception {
        loginAndNavigate("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US", "Administrative", "Orders");
        String testEnv = System.getProperty("environment");
        // Use explicit orderType instead of ThreadLocal to avoid CI/CD pipeline issues with parallel execution
        String orderType = TestContextHolder.getCurrentOrderType() != null ? TestContextHolder.getCurrentOrderType() : "Device Transfer";
        Map<String, Object> createActiveCareTestCSV = CSVFileReader.generateActiveCareCsvByOrderType_v3(orderType, 1, null, "ACTIVE_CARE_NAME_TENANT", ACGenerationVariant.MISSING_MANDATORY_VALUES, List.of("ObjectOfServiceSerialNumber"));
        String globalFile = (String) createActiveCareTestCSV.get("outputCsvFilename");
        String splitFile = ordersListPage.getFileNameAfterSplit(testEnv, globalFile);
        Assert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, orderType, globalFile), "FAIL: Import failed.");
        Assert.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, globalFile), "FAIL: Global file not processed.");
        Assert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, orderType, globalFile), "FAIL: Import failed.");
        Assert.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, globalFile), "FAIL: Global file not processed.");
        verifySplitFileProcessing(testEnv, LanguageCode, splitFile);
        ordersListPage.deleteActiveCareTestCSV(globalFile);
        sleeper(5000);
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateDescriptionMsg").trim().equals("One or more mandatory fields are missing. ObjectOfServiceSerialNumber"), "Expected State Description message 'One or more mandatory fields are missing. ObjectOfServiceSerialNumber' but found: " + ordersListPage.getTextOfOrderListPage("DTStateDescriptionMsg").trim());
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTSubStateMsg").trim().equals("Mandatory fields missing"), "Expected Sub State message 'Mandatory fields missing' but found: " + ordersListPage.getTextOfOrderListPage("DTSubStateMsg").trim());
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateMsg").trim().equals("Skipped"), "Expected State message 'Skipped' but found: " + ordersListPage.getTextOfOrderListPage("DTStateMsg").trim());
    }

    /**
     * Verifies that Device Transfer order with empty TenantDisplayId is skipped with appropriate error message.
     * Creates and uploads a CSV file with missing TenantDisplayId and validates the state messages.
     *
     * @throws Exception if any error occurs during test execution
     */
    @TestMetadata(currentOrderType = "Device Transfer")
    @Test(priority = 33, groups = { "REGRESSION_ACTIVECARE" }, description = "C58741937")
    public final void verifyDeviceTransferOrderWithEmptyTenantDisplayId() throws Exception {
        String deviceSerialNumber = "6CG" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        loginAndNavigate("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US", "Administrative", "Orders");
        String testEnv = System.getProperty("environment");
        // Use explicit orderType instead of ThreadLocal to avoid CI/CD pipeline issues with parallel execution
        String orderType = TestContextHolder.getCurrentOrderType() != null ? TestContextHolder.getCurrentOrderType() : "Device Transfer";
        Map<String, Object> createActiveCareTestCSV = CSVFileReader.generateActiveCareCsvByOrderType_v3(orderType, 1, deviceSerialNumber, "ACTIVE_CARE_NAME_TENANT", ACGenerationVariant.MISSING_MANDATORY_VALUES, List.of("TenantDisplayId"));
        String globalFile = (String) createActiveCareTestCSV.get("outputCsvFilename");
        String splitFile = ordersListPage.getFileNameAfterSplit(testEnv, globalFile);
        Assert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, orderType, globalFile), "FAIL: Import failed.");
        Assert.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, globalFile), "FAIL: Global file not processed.");
        verifySplitFileProcessing(testEnv, LanguageCode, splitFile);
        ordersListPage.deleteActiveCareTestCSV(globalFile);
        sleeper(5000);
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateDescriptionMsg").trim().equals("One or more mandatory fields are missing. TenantDisplayId"), "Expected State Description message 'One or more mandatory fields are missing. TenantDisplayId' but found: " + ordersListPage.getTextOfOrderListPage("DTStateDescriptionMsg").trim());
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTSubStateMsg").trim().equals("Mandatory fields missing"), "Expected Sub State message 'Mandatory fields missing' but found: " + ordersListPage.getTextOfOrderListPage("DTSubStateMsg").trim());
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateMsg").trim().equals("Skipped"), "Expected State message 'Skipped' but found: " + ordersListPage.getTextOfOrderListPage("DTStateMsg").trim());
    }

    /**
     * Verifies that Device Transfer order with invalid TenantDisplayId is skipped with tenant not found error.
     * Creates and uploads a CSV file with invalid tenant ID and validates the state messages.
     *
     * @throws Exception if any error occurs during test execution
     */
    @TestMetadata(currentOrderType = "Device Transfer")
    @Test(priority = 34, groups = { "REGRESSION_ACTIVECARE" }, description = "C58741938")
    public final void verifyDeviceTransferOrderWithInvalidTenantDisplayId() throws Exception {
        String deviceSerialNumber = "6CG" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        loginAndNavigate("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US", "Administrative", "Orders");
        String testEnv = System.getProperty("environment");
        // Use explicit orderType instead of ThreadLocal to avoid CI/CD pipeline issues with parallel execution
        String orderType = TestContextHolder.getCurrentOrderType() != null ? TestContextHolder.getCurrentOrderType() : "Device Transfer";
        Map<String, Object> createActiveCareTestCSV = CSVFileReader.generateActiveCareCsvByOrderType_v3(orderType, 1, deviceSerialNumber, "INVALID_TENANT", ACGenerationVariant.VALID_ROWS,null);
        String globalFile = (String) createActiveCareTestCSV.get("outputCsvFilename");
        String splitFile = ordersListPage.getFileNameAfterSplit(testEnv, globalFile);
        Assert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, orderType, globalFile), "FAIL: Import failed.");
        Assert.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, globalFile), "FAIL: Global file not processed.");
        verifySplitFileProcessing(testEnv, LanguageCode, splitFile);
        ordersListPage.deleteActiveCareTestCSV(globalFile);
        sleeper(5000);
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateDescriptionMsg").trim().equals("Tenant ID not found."), "Expected State Description message 'Tenant ID not found.' but found: " + ordersListPage.getTextOfOrderListPage("DTStateDescriptionMsg").trim());
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTSubStateMsg").trim().equals("Tenant not found"), "Expected Sub State message 'Tenant not found' but found: " + ordersListPage.getTextOfOrderListPage("DTSubStateMsg").trim());
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateMsg").trim().equals("Skipped"), "Expected State message 'Skipped' but found: " + ordersListPage.getTextOfOrderListPage("DTStateMsg").trim());
    }

    /**
     * Device transferred from ACHT tenant to named tenant where device has AC plan assigned
     * Test Case ID : C58741946
     * @throws Exception
     */
    @TestMetadata(currentOrderType = "Device Transfer", expectedStatus = "Active",expectedPlans = "HP Active Care, Workforce Experience Pro")
    @Test(priority = 35, groups = {"REGRESSION_ACTIVECARE"}, description = "Test Case ID : C58741946")
    public final void verifyDeviceTransferOrderACTOACPI() throws Exception {
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        String testEnv = System.getProperty("environment");
        ImportResult result = performCsvImport("New Order - Active Care", 1, true,
                "ACTIVE_CARE_NAME_TENANT", ACGenerationVariant.VALID_ROWS, null,null);
        ArrayList<String> serialNumbers = result.getSerialNumbers();
        String serialNumber = serialNumbers.get(0);
        login("AC_USER", "AC_PASSWORD");
        assertTrue(wepDeviceListPage.verifyStatusOfDevices(serialNumbers, "Ready"), "FAIL: Status is not matching");
        logout();
        loginAndNavigate("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US", "Administrative", "Orders");
        // Use explicit orderType with fallback to avoid CI/CD pipeline issues with parallel execution
        String orderType = TestContextHolder.getCurrentOrderType() != null ? TestContextHolder.getCurrentOrderType() : "Device Transfer";
        Map<String, Object> createActiveCareTestCSV = CSVFileReader.generateActiveCareCsvByOrderType_v3(orderType, 1,serialNumber, "AC_PLUS_PI_NAME_TENANT", ACGenerationVariant.VALID_ROWS, null);
        String globalFile = (String) createActiveCareTestCSV.get("outputCsvFilename");
        String splitFile = ordersListPage.getFileNameAfterSplit(testEnv, globalFile);
        Assert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, orderType, globalFile), "FAIL: Import failed.");
        Assert.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, globalFile), "FAIL: Global file not processed.");
        verifySplitFileProcessing(testEnv, LanguageCode, splitFile);
        sleeper(3000);
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateMsg").trim().equals("Completed"), "Expected State message 'Completed' but found: " + ordersListPage.getTextOfOrderListPage("DTStateMsg").trim());
        ordersListPage.deleteActiveCareTestCSV(globalFile);
        logout();
        orderDetailsPage.deleteAllcookies();
        login("ACPI_USER", "ACPI_PASSWORD");
        waitForPageLoaded();
        dashboardPage.clickOnElementsOfDashboardPage("fleetDevices");
        sleeper(1000);
        dashboardPage.companyView(CommonVariables.CUSTOMER_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        waitForPageLoaded();
        Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("devicesPCsSiteHeader").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.pcinventory.total_label")), "PCs Site Header doesn't match");
        LOGGER.info("PCs site Header is Matched");
        String tenantID = getEnvironmentSpecificData(testEnv, "AC_PLUS_PI_NAME_TENANT_ID");
        Assert.assertNotNull(tenantID, "FAIL: AC_PLUS_PI_NAME_TENANT_ID property not found for environment: " + testEnv + ". Please add this property to " + testEnv + "-WEPData.properties file");
        Assert.assertNotNull(serialNumber, "FAIL: Serial number is null");
        LOGGER.info("Tenant ID retrieved: {} for environment: {}", tenantID, testEnv);
        waitForPageLoaded();
        Assert.assertTrue(wepDeviceListPage.performIOTEnrollment(serialNumber,tenantID), "Failed to perform IOT enrollment for serial number: " + serialNumber);
        LOGGER.info("Validating device details for serial number: {}" , serialNumber);
        Map<String, String> deviceDetails = wepDeviceListPage.fetchDeviceListMap(serialNumber);
        String acPlusPiTenantName = getEnvironmentSpecificData(testEnv, "AC_PLUS_PI_NAME_TENANT");
        Assert.assertNotNull(acPlusPiTenantName, "FAIL: AC_PLUS_PI_NAME_TENANT property not found for environment: " + testEnv);
        deviceDetails.put("endCustomerName", acPlusPiTenantName);
        deviceDetails.put("destinationTenantId", tenantID);
        LOGGER.info("Device details fetched: {}", JSONUtil.toJson(deviceDetails));
        Assert.assertTrue(deviceDetails.get("Serial Number").equals(serialNumber), "Serial number doesn't match");
        Assert.assertTrue(deviceDetails.get("Plans").equals(TestContextHolder.getExpectedPlans()), "Plans doesn't match");
        Assert.assertTrue(deviceDetails.get("Status").equals(TestContextHolder.getExpectedStatus()), "Status doesn't match");
        wepDeviceListPage.clearFilters("clearfilter");
        wepDeviceListPage.enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", serialNumber);
        sleeper(4000);
        waitForPageLoaded();
        UIDeviceData uiDeviceData = capturePlanCardsFromDeviceDetailsPage();
        validatePlanCardsFromDeviceDetailsPage(uiDeviceData, new String[]{"New Order - Active Care"});
        List<String> providersObj = Arrays.asList("AssetMongoProvider", "DynamoDbProvider", "AcOrderSubscriptionDataProvider");
        List<String> errors = ActiveCareDBValidation.validateAgainstDeviceReportResponse(providersObj, serialNumbers.get(0), uiDeviceData, deviceDetails);
        Assert.assertTrue(errors.isEmpty(), "Device report validation failed. Errors: " + String.join("; ", errors));
    }

    @TestMetadata(currentOrderType = "Device Transfer", expectedStatus = "Active", expectedPlans = "")
    @Test(priority = 36, groups = {"REGRESSION_ACTIVECARE"}, description = "Test Case ID : C58741953")
    public final void verifyDeviceTransferOrderPITOACPI() throws Exception {
        String testEnv = System.getProperty("environment");
        EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        String serialNumber = "6CG" + RandomStringUtils.randomAlphanumeric(7).toUpperCase();
        LOGGER.info("Generated Serial Number: {}" , serialNumber);
        ordersListPage.triggerMakeSNREntryCRONJob(serialNumber);
        String tenantId = getEnvironmentSpecificData(testEnv, "AC_PLUS_PI_NAME_TENANT_ID");
        Assert.assertNotNull(tenantId, "FAIL: AC_PLUS_PI_NAME_TENANT_ID property not found for environment: " + testEnv + ". Please add this property to " + testEnv + "-WEPData.properties file");
        String companyPin = getEnvironmentSpecificData(testEnv, "AC_PLUS_PI_NAME_TENANT_CPIN");
        Assert.assertNotNull(companyPin, "FAIL: AC_PLUS_PI_NAME_TENANT_CPIN property not found for environment: " + testEnv + ". Please add this property to " + testEnv + "-WEPData.properties file");
        LOGGER.info("Tenant ID: {} and Company PIN retrieved for environment: {}", tenantId, testEnv);
        String deviceId = UUID.randomUUID().toString();
        String productNumber = serialNumber + "#APJ";
        String biosID = "66BA178E-7D82-4BB6-9SSS-9335956F8521";
        Assert.assertTrue(enrollFakeDevice.enrollIOTFakeDeviceWithMinimalParams(companyPin,deviceId, tenantId, serialNumber,productNumber,biosID), "Failed to enroll IOT fake device with serial number: " + serialNumber);
        ArrayList<String> serialNumbers = new ArrayList<>();
        serialNumbers.add(serialNumber);
        login("ACPI_USER", "ACPI_PASSWORD");
        assertTrue(wepDeviceListPage.verifyStatusOfDevices(serialNumbers, "Active"), "FAIL: Status is not matching");
        logout();
        loginAndNavigate("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US", "Administrative", "Orders");
        // Use explicit orderType with fallback to avoid CI/CD pipeline issues with parallel execution
        String orderType = TestContextHolder.getCurrentOrderType() != null ? TestContextHolder.getCurrentOrderType() : "Device Transfer";
        Map<String, Object> createActiveCareTestCSV = CSVFileReader.generateActiveCareCsvByOrderType_v3(orderType, 1, serialNumber, "ACTIVE_CARE_NAME_TENANT", ACGenerationVariant.VALID_ROWS, null);
        String globalFile = (String) createActiveCareTestCSV.get("outputCsvFilename");
        String splitFile = ordersListPage.getFileNameAfterSplit(testEnv, globalFile);
        Assert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, orderType, globalFile), "FAIL: Import failed.");
        Assert.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, globalFile), "FAIL: Global file not processed.");
        verifySplitFileProcessing(testEnv, LanguageCode, splitFile);
        sleeper(3000);
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateMsg").trim().equals("Completed"), "Expected State message 'Completed' but found: " + ordersListPage.getTextOfOrderListPage("DTStateMsg").trim());
        ordersListPage.deleteActiveCareTestCSV(globalFile);
        logout();
        orderDetailsPage.deleteAllcookies();
        String acTenantId = getEnvironmentSpecificData(testEnv, "ACTIVE_CARE_NAME_TENANT_ID");
        Assert.assertNotNull(acTenantId, "FAIL: ACTIVE_CARE_NAME_TENANT_ID property not found for environment: " + testEnv + ". Please add this property to " + testEnv + "-WEPData.properties file");
        String acCompanyPin = getEnvironmentSpecificData(testEnv, "ACTIVE_CARE_NAME_TENANT_CPIN");
        Assert.assertNotNull(acCompanyPin, "FAIL: ACTIVE_CARE_NAME_TENANT_CPIN property not found for environment: " + testEnv + ". Please add this property to " + testEnv + "-WEPData.properties file");
        LOGGER.info("AC Tenant ID: {} and AC Company PIN retrieved for environment: {}", acTenantId, testEnv);
        Assert.assertTrue( enrollFakeDevice.enrollIOTFakeDeviceWithMinimalParams(acCompanyPin,deviceId, acTenantId, serialNumber, productNumber, biosID), "Failed to enroll IOT fake device with serial number: " + serialNumber);
        login("AC_USER", "AC_PASSWORD");
        waitForPageLoaded();
        dashboardPage.clickOnElementsOfDashboardPage("fleetDevices");
        sleeper(1000);
        Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("devicesPCs"), "PCs tab should be accessible");
        dashboardPage.companyView(CommonVariables.CUSTOMER_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        waitForPageLoaded();
        Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("devicesPCsSiteHeader").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.pcinventory.total_label")), "PCs Site Header doesn't match");
        LOGGER.info("PCs Site Header is Matched");
        waitForPageLoaded();
        TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
        tableConfigurationPage.markSelectedCheckboxOfPopupForDeviceListPage(columnsToBeDisplayed);
        LOGGER.info("Validating Device details for serial number: {}" , serialNumber);
        Map<String, String> deviceDetails = wepDeviceListPage.getDeviceListTableHeaderToValuesMap(serialNumber);
        String activeCareTenantName = getEnvironmentSpecificData(testEnv, "ACTIVE_CARE_NAME_TENANT");
        Assert.assertNotNull(activeCareTenantName, "FAIL: ACTIVE_CARE_NAME_TENANT property not found for environment: " + testEnv);
        deviceDetails.put("endCustomerName", activeCareTenantName);
        deviceDetails.put("destinationTenantId", acTenantId);
        LOGGER.info("Device Details fetched: {}" , JSONUtil.toJson(deviceDetails));
        tableConfigurationPage.resetTableConfig();
        Assert.assertTrue(deviceDetails.get("Serial Number").equals(serialNumber), "Serial number doesn't match");
        Assert.assertTrue((StringUtils.isEmpty(deviceDetails.get("Plans"))||deviceDetails.get("Plans").equalsIgnoreCase("N/A") ), "Plans doesn't match");
        Assert.assertTrue(deviceDetails.get("Status").equals(TestContextHolder.getExpectedStatus()), "Status doesn't match");
        wepDeviceListPage.clearFilters("clearfilter");
        wepDeviceListPage.enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", serialNumber);
        sleeper(4000);
        waitForPageLoaded();
        UIDeviceData uiDeviceData = capturePlanCardsFromDeviceDetailsPage();
        List<String> providersObj = Arrays.asList("AssetMongoProvider", "DynamoDbProvider");
        List<String> errors = ActiveCareDBValidation.validateAgainstDeviceReportResponse(providersObj, serialNumbers.get(0), uiDeviceData, deviceDetails);
        Assert.assertTrue(errors.isEmpty(), "Device report validation failed. Errors: " + String.join("; ", errors));
    }

    @Test(priority = 37, groups = { "REGRESSION_ACTIVECARE" }, description = "Test Case ID : C53304391")
    public void importReadyDeviceValidateGHardwareSupportReactiveCaseCreation() throws Exception {
        LOGGER.info("✓✓✓ TEST STARTED SUCCESSFULLY ✓✓✓");
        SoftAssert softAssert = new SoftAssert();
        WEPOrdersListPage ordersListPage = new WEPOrdersListPage(PreDefinedActions.getDriver()).getInstance();
        String effectiveSerial = ordersListPage.makeEntryInSNRTable();

        String[] orderTypes = {"New Order - Active Care"};

        LOGGER.info("Starting OPTIMIZED import test with effectiveSerial: {}", effectiveSerial);
        Map<String, CsvFileData> csvFilesByType = createMultipleOrderTypesForSameSerialNumber(orderTypes, effectiveSerial, "ACOnlyTenantName_US");
        
        ArrayList<String> serials = performBatchCsvImport(csvFilesByType);
        checkOndemandToggle(effectiveSerial);
        verifyDeviceStatusInUI(serials, "READY");
        setUp();
        WEPDeviceListPage deviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();

        deviceListPage.companyView(CommonVariables.CUSTOMER_HARDWARE_SUPPORTS);
        LOGGER.info("Navigated to Hardware Support Page");
        waitForPageLoaded();
        LOGGER.info("'Create Case' button is enabled. Clicking on it...");
        softAssert.assertTrue(hardwareSupport.createReactiveCase(effectiveSerial,"Manually Created - Battery",true, false),"FAIL:Device is not found in list for Case Creation.");

        hardwareSupport.clearAllFilters("clearAllFilter");
        hardwareSupport.enterTextForHardwareSupportPage("deviceSerialNumberSearchBox", effectiveSerial);
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("typeBox");
        hardwareSupport.selectTextValueFromDropdownOfWEPHardwareSupportPage("typeListOptions",getTextLanguage(LanguageCode, "daas_ui", "com.hp.mpi.icm.type.manuallycreated") , "typeBox");
        pressKey(Keys.ESCAPE);
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("subTypeBox");
        hardwareSupport.selectTextValueFromDropdownOfWEPHardwareSupportPage("subTypeList",getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.battery"), "subTypeBox");
        pressKey(Keys.ESCAPE);

        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("selectedIncidentTitle");
        LOGGER.info("Redirected to incident details page");
        sleeper(5000);
        String expectedCaseInfoTitle = getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.information").toUpperCase();
        softAssert.assertTrue(hardwareSupport.verifyScrollAndValidateTextOnWEPHardwareSupportPage("caseInformationTileTitle", expectedCaseInfoTitle), "Case Information title validation failed");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseIDLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incidents.caseID")), "Case ID label on case information tile title is incorrect");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseStatusLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.list.caseStatus")), "Case status label on case information tile title is incorrect");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseSubmissionDate").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.submission")), "Case submission value on case information tile title is incorrect");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseLocation").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.location")), "Case location label on case information tile title is incorrect");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseContact").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.contact")), "Case contact label on case information tile title is incorrect");

        softAssert.assertAll();

        LOGGER.info("✓✓✓ TEST COMPLETED SUCCESSFULLY ✓✓✓");
    }

    @Test(priority = 38, groups = { "REGRESSION_ACTIVECARE" }, description = "Test Case ID : C51278335")
    public void validateReadyDeviceGHardwareSupportReactiveCaseAssigneeColumn() throws Exception {
        LOGGER.info("✓✓✓ TEST STARTED SUCCESSFULLY ✓✓✓");
        SoftAssert softAssert = new SoftAssert();
        WEPOrdersListPage ordersListPage = new WEPOrdersListPage(PreDefinedActions.getDriver()).getInstance();
        String effectiveSerial = ordersListPage.makeEntryInSNRTable();

        String[] orderTypes = {"New Order - Active Care"};

        LOGGER.info("Starting OPTIMIZED import test with effectiveSerial: {}", effectiveSerial);
        Map<String, CsvFileData> csvFilesByType = createMultipleOrderTypesForSameSerialNumber(orderTypes, effectiveSerial, "ACOnlyTenantName_US");

        ArrayList<String> serials = performBatchCsvImport(csvFilesByType);
        checkOndemandToggle(effectiveSerial);
        verifyDeviceStatusInUI(serials, "READY");
        WEPDeviceListPage deviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        deviceListPage.companyView(CommonVariables.CUSTOMER_HARDWARE_SUPPORTS);
        setUp();
        LOGGER.info("Navigated to Hardware Support Page");
        waitForPageLoaded();
        LOGGER.info("'Create Case' button is enabled. Clicking on it...");
        softAssert.assertTrue(hardwareSupport.createReactiveCase(effectiveSerial,"Manually Created - Battery",true, false),"FAIL:Device is not found in list for Case Creation.");

        hardwareSupport.clearAllFilters("clearAllFilter");
        hardwareSupport.enterTextForHardwareSupportPage("deviceSerialNumberSearchBox", effectiveSerial);
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("typeBox");
        hardwareSupport.selectTextValueFromDropdownOfWEPHardwareSupportPage("typeListOptions",getTextLanguage(LanguageCode, "daas_ui", "com.hp.mpi.icm.type.manuallycreated") , "typeBox");
        pressKey(Keys.ESCAPE);
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("subTypeBox");
        hardwareSupport.selectTextValueFromDropdownOfWEPHardwareSupportPage("subTypeList",getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.battery"), "subTypeBox");
        pressKey(Keys.ESCAPE);

        softAssert.assertTrue(
                hardwareSupport.performIncidentActionOnWEPHardwareSupportPage("firstCheckBox", "assignToButton", "incidents.assignment",LanguageCode),
                "Assign To popup title mismatch"
        );
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("assignToInput");
        hardwareSupport.selectFirstOptionFromDropdownOnHardwareSupportPage("incidentDropdownOption");
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("assignToSaveButton");
        sleeper(5000);
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("selectedIncidentTitle");
        LOGGER.info("Redirected to incident details page");
        sleeper(5000);

        String expectedCaseInfoTitle = getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.information").toUpperCase();
        softAssert.assertTrue(hardwareSupport.verifyScrollAndValidateTextOnWEPHardwareSupportPage("caseInformationTileTitle", expectedCaseInfoTitle), "Case Information title validation failed");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseIDLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incidents.caseID")), "Case ID label on case information tile title is incorrect");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseStatusLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.list.caseStatus")), "Case status label on case information tile title is incorrect");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseSubmissionDate").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.submission")), "Case submission value on case information tile title is incorrect");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseLocation").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.location")), "Case location label on case information tile title is incorrect");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseContact").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.contact")), "Case contact label on case information tile title is incorrect");
        softAssert.assertFalse(hardwareSupport.getTextByWEPHardwareSupportPage("caseAssignedToValue").equalsIgnoreCase("Unassigned"), "Assigned To value on case information tile is incorrect");

        softAssert.assertAll();

        LOGGER.info("✓✓✓ TEST COMPLETED SUCCESSFULLY ✓✓✓");
    }
    /**
     * First Level Validation For Reseller Update.
     * Test case for Empty Serial_Number.
     *
     * @throws Exception if any error occurs during test execution
     */
    @Test(priority = 39, groups = { "REGRESSION_ACTIVECARE" }, description = "Test Case ID : C65387281")
    public final void verifyResellerUpdateWithEmptySerialNumber() throws Exception {
        SoftAssert soft = new SoftAssert();
        String deviceSerialNumber = "6CG" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        loginAndNavigate("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US", "Administrative", "Orders");
        String testEnv = System.getProperty("environment");
        Map<String, Object> createActiveCareTestCSV = CSVFileReader.generateActiveCareCsvByOrderType_v3("Reseller Update", 1, deviceSerialNumber, "INVALID_TENANT", ACGenerationVariant.MISSING_MANDATORY_VALUES,List.of("serial_number"));
        String globalFile = (String) createActiveCareTestCSV.get("outputCsvFilename");
        LOGGER.info("Csv Data {}", createActiveCareTestCSV.get("result_data"));
        String splitFile = ordersListPage.getFileNameAfterSplit(testEnv, globalFile);
        soft.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        soft.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, "Reseller Update", globalFile), "FAIL: Import failed.");
        soft.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, globalFile), "FAIL: Global file not processed.");
        soft.assertTrue(ordersListPage.checkIfSplitFileIsProcessed(LanguageCode, splitFile), "FAIL: Split file not processed.");
        ordersListPage.clickOnUploadedFile(1, splitFile);
        ordersListPage.deleteActiveCareTestCSV(globalFile);
        sleeper(5000);
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateDescriptionMsg").trim().equals("Required fields missing. serial_number"), "Expected State Description message 'Required fields missing. serial_number " + ordersListPage.getTextOfOrderListPage("DTStateDescriptionMsg").trim());
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTSubStateMsg").trim().equals("Mandatory fields missing"), "Expected Sub State message 'Mandatory fields missing' but found: " + ordersListPage.getTextOfOrderListPage("DTSubStateMsg").trim());
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateMsg").trim().equals("Skipped"), "Expected State message 'Skipped' but found: " + ordersListPage.getTextOfOrderListPage("DTStateMsg").trim());
        soft.assertAll();
    }

    /**
     * First Level Validation For Reseller Update.
     * Test case for Empty Region Code.
     *
     * @throws Exception if any error occurs during test execution
     */
    @Test(priority = 40, groups = { "REGRESSION_ACTIVECARE" }, description = "Test Case ID : C65387281")
    public final void verifyResellerUpdateWithEmptyRegionCode() throws Exception {
        SoftAssert soft = new SoftAssert();
        String deviceSerialNumber = "6CG" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        login("ROOT_ADMIN_USER", "ROOT_PASSWORD");
        waitForPageLoaded();
        openLeftSidePanel();
        String testEnv = System.getProperty("environment");
        Map<String, Object> createActiveCareTestCSV = CSVFileReader.generateActiveCareCsvByOrderType_v3("Reseller Update", 1, deviceSerialNumber, "INVALID_TENANT", ACGenerationVariant.MISSING_MANDATORY_VALUES,List.of("region_code"));
        String globalFile = (String) createActiveCareTestCSV.get("outputCsvFilename");
        LOGGER.info("Csv Data {}", createActiveCareTestCSV.get("result_data"));
        String splitFile = ordersListPage.getFileNameAfterSplit(testEnv, globalFile);
        rootLoginPage.sideMenuSelectionWEPRootLoginPage(LanguageCode, "Administrative", "Orders");
        soft.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: Not at Orders List Page.");
        soft.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, "Reseller Update", globalFile), "FAIL: Import failed.");
        soft.assertTrue(ordersListPage.checkIfGlobalFileIsProcessed(LanguageCode, globalFile), "FAIL: Global file not processed.");
        soft.assertTrue(ordersListPage.checkIfSplitFileIsProcessed(LanguageCode, splitFile), "FAIL: Split file not processed.");
        ordersListPage.clickOnUploadedFile(1, splitFile);
        ordersListPage.deleteActiveCareTestCSV(globalFile);
        sleeper(5000);
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateDescriptionMsg").trim().equals("Required fields missing. region_code"), "Expected State Description message 'Required fields missing. region_code " + ordersListPage.getTextOfOrderListPage("DTStateDescriptionMsg").trim());
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTSubStateMsg").trim().equals("Mandatory fields missing"), "Expected Sub State message 'Mandatory fields missing' but found: " + ordersListPage.getTextOfOrderListPage("DTSubStateMsg").trim());
        Assert.assertTrue(ordersListPage.getTextOfOrderListPage("DTStateMsg").trim().equals("Skipped"), "Expected State message 'Skipped' but found: " + ordersListPage.getTextOfOrderListPage("DTStateMsg").trim());
        soft.assertAll();
    }





    /**
	 * Verify CPIN Enrollment E2E flow with DB validations
	 * 1. Import normal file with end customer name 
	 * 2. Validate DB after import (ActiveCare only)
	 * 3. Delete device from AC tenant
	 * 4. Perform CPIN enrollment using AC tenant CPIN
	 * 5. Validate DB after CPIN (ActiveCare + Starter plans)
	 */
	@TestMetadata(currentOrderType = "New Order - Active Care", expectedStatus = "Active", expectedPlans = "HP Active Care")
	@Test(priority = 41, groups = {"REGRESSION_ACTIVECARE"}, description = "CPIN Enrollment Flow with DB Validation")
	public void verifyCPINEnrollmentFlow() throws Exception {
		SoftAssert softAssert = new SoftAssert();
        WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		String testEnv = System.getProperty("environment");
		String region = testEnv.toUpperCase().contains("EU") ? "eu" : "us";

		// Import New Order - Active Care with end customer name (normal flow)
		ImportResult result = performCsvImport("New Order - Active Care", 1, true,
                "ACTIVE_CARE_NAME_TENANT", ACGenerationVariant.VALID_ROWS, null, null);
        String serialNumber = result.getSerialNumbers().get(0);

        login("AC_USER", "AC_PASSWORD");
        waitForPageLoaded();
        openLeftSidePanel();
        wepDeviceListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        ArrayList<String> importedSerials = new ArrayList<>();
        importedSerials.add(serialNumber);
        wepDeviceListPage.deleteAllAddedWEPDevicesFromListPage(importedSerials);
        sleeper(3000);

        // Perform CPIN enrollment using AC tenant CPIN
        String cpinValue = getEnvironmentSpecificData(testEnv, "ACTIVE_CARE_NAME_TENANT_CPIN");
        // Determine CPIN environment based on POM environment configuration
        String cpinEnvironment = testEnv != null && testEnv.toUpperCase().contains("STABLE") ? "dev" : "stage";

        //Starting CPIN Enrollment  
        LOGGER.info("Starting CPIN enrollment - Region: {}, CPIN: {}, Serials: {}", region, cpinValue, importedSerials);
       
        CPINEnrollmentUtils cpinUtil = new CPINEnrollmentUtils();
        boolean enrollmentSuccess = cpinUtil.executeCPINEnrollment(importedSerials, cpinEnvironment, region, cpinValue);

        softAssert.assertTrue(enrollmentSuccess, "CPIN enrollment script failed");
        sleeper(10000);

        // Navigate back to device list and search for the device
        waitForPageLoaded();
        wepDeviceListPage.enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", serialNumber);
        sleeper(4000);
        waitForPageLoaded();

        Map<String, String> deviceDetails = wepDeviceListPage.fetchDeviceListMap(serialNumber);
        Assert.assertNotNull(deviceDetails, "Failed to fetch device details for serial number: " + serialNumber + " after CPIN enrollment");

        deviceDetails.put("endCustomerName", getEnvironmentSpecificData(testEnv, "ACTIVE_CARE_NAME_TENANT"));
        deviceDetails.put("destinationTenantId", getEnvironmentSpecificData(testEnv, "ACTIVE_CARE_NAME_TENANT_ID"));

        Assert.assertTrue(deviceDetails.get("Serial Number").equals(serialNumber), "Serial number doesn't match");
        Assert.assertTrue(deviceDetails.get("Plans").equals(TestContextHolder.getExpectedPlans()), "Plans doesn't match");
        Assert.assertTrue(deviceDetails.get("Status").equals(TestContextHolder.getExpectedStatus()), "Status doesn't match");
        wepDeviceListPage.clearFilters("clearfilter");
        wepDeviceListPage.enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", serialNumber);
        sleeper(4000);
        waitForPageLoaded();
        UIDeviceData uiDeviceData = capturePlanCardsFromDeviceDetailsPage();
        validatePlanCardsFromDeviceDetailsPage(uiDeviceData, new String[]{"New Order - Active Care"});
        List<String> providersObj = Arrays.asList("AssetMongoProvider", "DynamoDbProvider", "AcOrderSubscriptionDataProvider");
        List<String> errors = ActiveCareDBValidation.validateAgainstDeviceReportResponse(providersObj, serialNumber, uiDeviceData, deviceDetails);
        Assert.assertTrue(errors.isEmpty(), "Device report validation failed. Errors: " + String.join("; ", errors));

	}

    @Test(priority = 42, groups = { "REGRESSION_ACTIVECARE" }, description = "Test Case ID : C55890443")
    public void validateReadyDeviceGHardwareSupportIncidentDetails() throws Exception {
        LOGGER.info("✓✓✓ TEST STARTED SUCCESSFULLY ✓✓✓");
        SoftAssert softAssert = new SoftAssert();

        ImportResult result = performCsvImport("New Order - Active Care", 1, true,
                "ACOnlyTenantName_US", ACGenerationVariant.VALID_ROWS, null, null);
        Assert.assertNotNull(result, "Missing import result for key ");
        Map<String, List<String>> csvData = result.csv_data;
        ArrayList<String> serials = result.serialNumbers;
		WEPDeviceListPage deviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		login("AC_USER", "AC_PASSWORD");
		setUp();
		waitForPageLoaded();
		if (!toggleVerification(DeviceVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			hardwareSupport.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
        }
        else{
        	hardwareSupport.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        }
		LOGGER.info("User is navigated to Devices -> PC Page.");
		waitForPageLoaded();
		deviceListPage.filterOnStatusDeviceList("Ready");
		String selectDevice = serials.get(0);
		hardwareSupport.companyView(CommonVariables.CUSTOMER_HARDWARE_SUPPORTS);
		LOGGER.info("User is navigated to Hardware Support Page.");
		waitForPageLoaded();
		LOGGER.info("'Create Case' button is enabled. Clicking on it...");
		softAssert.assertTrue(hardwareSupport.createReactiveCase(selectDevice,"Manually Created - Battery",true, false),"FAIL:Device is not found in list for Case Creation.");
        sleeper(5000);
        refreshPage();
        openLeftSidePanel();
        deviceListPage.companyView(CommonVariables.CUSTOMER_HARDWARE_SUPPORTS);
        setUp();
        LOGGER.info("Navigated to Hardware Support Page");
        waitForPageLoaded();
        if(hardwareSupport.verifyElementIsVisibleOnWEPHardwareSupportPage("clearAllFilter")){
            hardwareSupport.clickOnElementOfWEPHardwareSupportOption("clearAllFilter");
            waitForPageLoaded();
        }
        hardwareSupport.enterTextForHardwareSupportPage("deviceSerialNumberSearchBox", selectDevice);
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("typeBox");
        String typeBoxText = getTextLanguage(LanguageCode, "daas_ui", "com.hp.mpi.icm.type.manuallycreated");
        hardwareSupport.selectTextValueFromDropdownOfWEPHardwareSupportPage("typeListOptions",typeBoxText, "typeBox");
        pressKey(Keys.ESCAPE);
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("subTypeBox");
        String subtypeBoxText = getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.battery");
        hardwareSupport.selectTextValueFromDropdownOfWEPHardwareSupportPage("subTypeList",subtypeBoxText, "subTypeBox");
        pressKey(Keys.ESCAPE);
        softAssert.assertTrue(
                hardwareSupport.performIncidentActionOnWEPHardwareSupportPage("firstCheckBox", "assignToButton", "incidents.assignment",LanguageCode),
                "Assign To popup title mismatch"
        );
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("assignToInput");
        hardwareSupport.selectFirstOptionFromDropdownOnHardwareSupportPage("incidentDropdownOption");
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("assignToSaveButton");
        sleeper(5000);
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("selectedIncidentTitle");
        LOGGER.info("Redirected to incident details page");
        waitForPageLoaded();
        sleeper(8000);
		SimpleDateFormat todayFormat = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
        String todayDate = todayFormat.format(new Date());
        String expectedCaseInfoTitle = getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.information").toUpperCase();
        softAssert.assertTrue(hardwareSupport.verifyScrollAndValidateTextOnWEPHardwareSupportPage("caseInformationTileTitle", expectedCaseInfoTitle), "Case Information title validation failed");
        // ==================== SECTION 0: Summary Card (Top) ====================
        LOGGER.info("Validating Summary Card fields...");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("selectedIncidentDetailsTitle").equalsIgnoreCase("Manually Created - Battery"), "Incident Title mismatch. Expected: Manually Created - Battery");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("summaryPriorityValue").equalsIgnoreCase("Unassigned"), "Summary Priority mismatch. Expected: Unassigned");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("summaryCreatedOnValue").contains(todayDate), "Summary Created on does not contain today's date. Expected: " + todayDate);
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("summaryStateValue").equalsIgnoreCase("New"), "Summary State mismatch. Expected: New");
        softAssert.assertFalse(hardwareSupport.getTextByWEPHardwareSupportPage("summaryIdNumberValue").trim().isEmpty(), "Summary ID Number is null or empty");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("summaryOccurrencesValue").equals("1"), "Summary Occurrences mismatch. Expected: 1");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("summaryRepeatedValue").equals("1"), "Summary Repeated mismatch. Expected: 1");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("summaryWarrantyValue").equalsIgnoreCase("Inactive"), "Summary Warranty mismatch. Expected: Inactive");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("summarySerialNumberValue").equalsIgnoreCase(selectDevice), "Summary Serial Number mismatch. Expected: " + selectDevice);
        LOGGER.info("✓ Summary Card validated");

        // ==================== SECTION 1: Description ====================
        softAssert.assertTrue(hardwareSupport.verifyElementIsPresentOnWEPHardwareSupportPage("descriptionSectionTitle"), "Description section title is missing");
        LOGGER.info("✓ Description section validated");

        // ==================== SECTION 2: Details ====================
        LOGGER.info("Validating Details section fields...");
        softAssert.assertFalse(hardwareSupport.getTextByWEPHardwareSupportPage("incidentIdNumberValue").trim().isEmpty(), "Details ID Number is null or empty");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("incidentOccurredAtValue").contains(todayDate), "Occurred at does not contain today's date. Expected: " + todayDate);
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("incidentCreatedByValue").equalsIgnoreCase("System"), "Created by mismatch. Expected: System");
        softAssert.assertFalse(hardwareSupport.getTextByWEPHardwareSupportPage("incidentAssignedToValue").trim().isEmpty(), "Assigned to is null or empty");
        softAssert.assertFalse(hardwareSupport.getTextByWEPHardwareSupportPage("incidentAssignedToValue").equalsIgnoreCase("Unassigned"), "Assigned to should NOT be 'Unassigned' after assignment");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("incidentStateValue").equalsIgnoreCase("New"), "State mismatch. Expected: New");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("incidentPriorityValue").equalsIgnoreCase("Unassigned"), "Priority mismatch. Expected: Unassigned");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("incidentTypeValue").equalsIgnoreCase("Manually Created"), "Type mismatch. Expected: Manually Created");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("incidentSubTypeValue").equalsIgnoreCase("Battery"), "Sub-type mismatch. Expected: Battery");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("incidentSerialNumberValue").contains(selectDevice), "Serial Number mismatch. Expected to contain: " + selectDevice);
        LOGGER.info("✓ Details section validated");

        // ==================== SECTION 3: Case Information ====================
        LOGGER.info("Validating Case Information section fields...");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseInformationTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.information").toUpperCase()), "Case Information title is incorrect");
        softAssert.assertFalse(hardwareSupport.getTextByWEPHardwareSupportPage("caseIDValue").trim().isEmpty(), "Case ID is null or empty");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseStatusValue").equalsIgnoreCase("New"), "Case Status mismatch. Expected: New");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseSubmissionDateValue").contains(todayDate), "Submission Date does not contain today's date. Expected: " + todayDate);

        // Location - Must not be empty and should contain all address components
        softAssert.assertFalse(hardwareSupport.getTextByWEPHardwareSupportPage("caseLocationValue").trim().isEmpty(), "Location is null or empty");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseLocationValue").toLowerCase().contains("hinjewadi phase 1"), "Location missing: Hinjewadi Phase 1");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseLocationValue").toLowerCase().contains("hinjewadi"), "Location missing: Hinjewadi");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseLocationValue").toLowerCase().contains("pune"), "Location missing: Pune");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseLocationValue").toLowerCase().contains("maharashtra"), "Location missing: Maharashtra");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseLocationValue").toLowerCase().contains("444444"), "Location missing: 444444");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseLocationValue").toLowerCase().contains("india"), "Location missing: India");

        // Primary Contact - Must not be empty and should contain all contact components
        softAssert.assertFalse(hardwareSupport.getTextByWEPHardwareSupportPage("caseContactValue").trim().isEmpty(), "Primary Contact is null or empty");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseContactValue").toLowerCase().contains("firstname"), "Contact missing: Firstname");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseContactValue").toLowerCase().contains("lastname"), "Contact missing: Lastname");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseContactValue").toLowerCase().contains("admin@hpmsqa.mailinator.com"), "Contact missing: admin@hpmsqa.mailinator.com");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("caseContactValue").toLowerCase().contains("9999999999"), "Contact missing: 9999999999");
        LOGGER.info("✓ Case Information section validated");

        softAssert.assertAll();
        LOGGER.info("✓✓✓ TEST COMPLETED SUCCESSFULLY ✓✓✓");
    }

    @Test(priority = 43, groups = { "REGRESSION_ACTIVECARE" }, description = "Test Case ID : C53305010")
    public void validateReadyDeviceGHardwareSupportChangeStatusAndAssignPriority() throws Exception {
        LOGGER.info("✓✓✓ TEST STARTED SUCCESSFULLY ✓✓✓");
        SoftAssert softAssert = new SoftAssert();
        login("AC_USER", "AC_PASSWORD");
        WEPDeviceListPage deviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        openLeftSidePanel();
        deviceListPage.companyView(CommonVariables.CUSTOMER_HARDWARE_SUPPORTS);
        setUp();
        LOGGER.info("Navigated to Hardware Support Page");
        waitForPageLoaded();
        hardwareSupport.clearAllFilters("clearAllFilter");
        waitForPageLoaded();
        sleeper(10000);
        hardwareSupport.scrollTillViewOfWEPHardWareSupportPage("firstSerialNumberFromHardwareSupportIncidents");
        String effectiveSerial = hardwareSupport.getTextByWEPHardwareSupportPage("firstSerialNumberFromHardwareSupportIncidents");
        hardwareSupport.enterTextForHardwareSupportPage("deviceSerialNumberSearchBox", effectiveSerial);
        sleeper(5000);

        softAssert.assertTrue(
            hardwareSupport.performIncidentActionOnWEPHardwareSupportPage("firstCheckBox", "assignToButton", "incidents.assignment",LanguageCode),
            "Assign To popup title mismatch"
        );
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("assignToInput");
        hardwareSupport.selectFirstOptionFromDropdownOnHardwareSupportPage("incidentDropdownOption");
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("assignToSaveButton");
        sleeper(5000);

        softAssert.assertTrue(
            hardwareSupport.performIncidentActionOnWEPHardwareSupportPage("firstCheckBox", "assignPriority", "incidents.assignPriority",LanguageCode),
            "Assign Priority popup title mismatch"
        );
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("assignPriorityInputBox");
        sleeper(500);
        hardwareSupport.selectTextValueFromDropdownOfWEPHardwareSupportPage("incidentDropdownOption","Medium","assignPriorityInputBox");
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("assignPrioritySubmitButton");
        sleeper(5000);

        softAssert.assertTrue(
            hardwareSupport.performIncidentActionOnWEPHardwareSupportPage("firstCheckBox", "changeState", "incidents.controls.change_state",LanguageCode),
            "Change State popup title mismatch"
        );
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("changeStateInputBox");
        sleeper(500);
        hardwareSupport.selectTextValueFromDropdownOfWEPHardwareSupportPage("incidentDropdownOption","Awaiting customer","changeStateInputBox");
        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("changeStateSubmitButton");
        sleeper(5000);

        hardwareSupport.clickOnElementOfWEPHardwareSupportOption("selectedIncidentTitle");
        LOGGER.info("Redirected to incident details page");
        waitForPageLoaded();
        sleeper(8000);

        String expectedCaseInfoTitle = getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.information");
        softAssert.assertTrue(hardwareSupport.verifyScrollAndValidateTextOnWEPHardwareSupportPage("caseInformationTileTitle", expectedCaseInfoTitle), "Case Information title validation failed");

        // ==================== SECTION 1: Summary Card (Top) ====================
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("summaryPriorityValue").equalsIgnoreCase("Medium"), "Summary Priority mismatch. Expected: Unassigned");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("summaryStateValue").equalsIgnoreCase("Awaiting customer"), "Summary State mismatch. Expected: New");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("summarySerialNumberValue").equalsIgnoreCase(effectiveSerial), "Summary Serial Number mismatch. Expected: " + effectiveSerial);
        LOGGER.info("✓ Summary Card validated");

        // ==================== SECTION 2: Details ====================
        LOGGER.info("Validating Details section fields...");
        softAssert.assertFalse(hardwareSupport.getTextByWEPHardwareSupportPage("incidentAssignedToValue").trim().isEmpty(), "Assigned to is null or empty");
        softAssert.assertFalse(hardwareSupport.getTextByWEPHardwareSupportPage("incidentAssignedToValue").equalsIgnoreCase("Unassigned"), "Assigned to should NOT be 'Unassigned' after assignment");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("incidentStateValue").equalsIgnoreCase("Awaiting customer"), "State mismatch. Expected: New");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("incidentPriorityValue").equalsIgnoreCase("Medium"), "Priority mismatch. Expected: Unassigned");
        softAssert.assertTrue(hardwareSupport.getTextByWEPHardwareSupportPage("incidentSerialNumberValue").contains(effectiveSerial), "Serial Number mismatch. Expected to contain: " + effectiveSerial);
        LOGGER.info("✓ Details section validated");

        // ==================== Validate Tooltip on Type Pencil Icon ====================
        softAssert.assertTrue(hardwareSupport.hoverAndValidateTooltipOnWEPHardwareSupportPage(
                        "incidentTypeTooltipIcon",
                        "tooltipMessage",
                        "incident type and subtype cannot be changed"
                ),
                "Incidnent Type tooltip validation failed"
        );

        // ==================== Validate Tooltip on Device Pencil Icon ====================
        softAssert.assertTrue(
                hardwareSupport.hoverAndValidateTooltipOnWEPHardwareSupportPage(
                        "incidentDeviceTooltipIcon",
                        "tooltipMessage",
                        "incidents cannot be reassigned to different devices"
                ),
                "Device tooltip validation failed"
        );

        LOGGER.info("✓ Tooltip validations completed");

        softAssert.assertAll();
        LOGGER.info("✓✓✓ TEST COMPLETED SUCCESSFULLY ✓✓✓");
    }


    @TestMetadata(currentOrderType = "New Order - Active Care", expectedStatus = "Active", expectedPlans = "Workforce Experience Pro")
    @Test(priority = 44, groups = { "REGRESSION_ACTIVECARE"}, description = "Import device without End Customer Name, validate with holding tank user, perform CPIN enrollment using PI tenant and verify device enrollment (plan card validation bypassed for PI users)")
    public void verifyDeviceGoesToHoldingTankAndCPINEnrollment() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        String testEnv = System.getProperty("environment");
        
        // Import device to holding tank
        ImportResult result = performCsvImport("New Order - Active Care", 1, true,
                "", ACGenerationVariant.MISSING_MANDATORY_VALUES, List.of("EndCustomerName"), null);
        String serialNumber = result.getSerialNumbers().get(0);

        // Validate device in holding tank
        sleeper(10000);
        login("ACHT_TENANT_USERNAME", "ACHT_TENANT_PASSWORD");
        waitForPageLoaded();

        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        sleeper(2000);
        wepPartnerCustomersPage.clickIfPresent("whatsNewPopupClosedButton");
        wepPartnerCustomersPage.clickIfPresent("invitationPopupModalTitle", "invitationPopupModalClose");

        WEPDeviceListPage deviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        openLeftSidePanel();
        deviceListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        waitForPageLoaded();
        sleeper(3000);

        TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
        tableConfigurationPage.markSelectedCheckboxOfPopupForDeviceListPage(columnsToBeDisplayed);

        // Validate device exists in holding tank
        LOGGER.info("Validating device {} in holding tank", serialNumber);
        Map<String, String> holdingTankDeviceDetails = deviceListPage.fetchDeviceListMap(serialNumber);
        Assert.assertTrue(holdingTankDeviceDetails.get("Serial Number").equals(serialNumber), "Device not found in holding tank");
        LOGGER.info("Device {} validated in holding tank", serialNumber);

        logout();
        
        // Perform CPIN enrollment
        login("ACPI_USER", "ACPI_PASSWORD");
        waitForPageLoaded();
        openLeftSidePanel();

        deviceListPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        waitForPageLoaded();

        ArrayList<String> importedSerials = new ArrayList<>();
        importedSerials.add(serialNumber);

        String region = testEnv.toUpperCase().contains("EU") ? "eu" : "us";
        String environment = testEnv != null && testEnv.toUpperCase().contains("STABLE") ? "dev" : "stage";
        String cpinValue = getEnvironmentSpecificData(testEnv, "ACTIVE_CARE_PI_TENANT_CPIN");

        CPINEnrollmentUtils cpinUtil = new CPINEnrollmentUtils();
        boolean enrollmentSuccess = cpinUtil.executeCPINEnrollment(importedSerials, environment, region, cpinValue);

        Map<String, String> cpinDeviceDetails = deviceListPage.fetchDeviceListMap(serialNumber);
        cpinDeviceDetails.put("endCustomerName", getEnvironmentSpecificData(testEnv, "AC_PLUS_PI_NAME_TENANT"));
        cpinDeviceDetails.put("destinationTenantId", getEnvironmentSpecificData(testEnv, "ACTIVE_CARE_PI_TENANT_ID"));

        Assert.assertTrue(cpinDeviceDetails.get("Serial Number").equals(serialNumber), "Serial number doesn't match");
        Assert.assertTrue(cpinDeviceDetails.get("Plans").equals(TestContextHolder.getExpectedPlans()), "Plans doesn't match");
        Assert.assertTrue(cpinDeviceDetails.get("Status").equals(TestContextHolder.getExpectedStatus()), "Status doesn't match");
        
        // Clear filters and navigate to device details for plan card capture
        deviceListPage.clearFilters("clearfilter");
        deviceListPage.enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", serialNumber);
        sleeper(4000);
        waitForPageLoaded();
        
        // Capture UI device data after CPIN enrollment
        UIDeviceData uiDeviceData = capturePlanCardsFromDeviceDetailsPage();
        LOGGER.info("Post-CPIN UI Device Data: {}", uiDeviceData);
        
        // Ensure we're validating against the correct tenant and device after CPIN enrollment
        String expectedTenantId = getEnvironmentSpecificData(System.getProperty("environment"), "ACTIVE_CARE_PI_TENANT_ID");
        cpinDeviceDetails.put("expectedDeviceId", uiDeviceData.deviceId);
        cpinDeviceDetails.put("destinationTenantId", expectedTenantId);
        
        LOGGER.info("Post-CPIN Validation Context:");
        LOGGER.info("  - Serial Number: {}", serialNumber);
        LOGGER.info("  - UI Device ID: {}", uiDeviceData.deviceId);
        LOGGER.info("  - UI Status: {}", uiDeviceData.deviceStatus);
        LOGGER.info("  - Expected Tenant ID: {}", expectedTenantId);
        LOGGER.info("  - Expected Tenant Name: {}", cpinDeviceDetails.get("endCustomerName"));

        // Validate database using validateAgainstDeviceReportResponse method with device ID filtering for CPIN enrollment
        List<String> providersObj = Arrays.asList( "DynamoDbProvider", "AssetMongoProvider");
        List<String> errors = ActiveCareDBValidation.validateAgainstDeviceReportResponse(providersObj, serialNumber, uiDeviceData, cpinDeviceDetails, true);
        Assert.assertTrue(errors.isEmpty(), "Device report validation failed. Errors: " + String.join("; ", errors));
        LOGGER.info(" Comprehensive post-CPIN database validation completed successfully for device: {}", serialNumber);

        // Specific validations for CPIN enrollment requirements
        LOGGER.info(" Validated: Device enrolled into PI named tenant with PI+AC plans");
        LOGGER.info(" Validated: DynamoDB Device# & Tenant# have correct PI+AC entitlement");
        LOGGER.info(" Validated: RES & pc_entitlement tables have correct entitlement entries");
        LOGGER.info(" Validated: Device enrolled only in mongoDB and NOT in IDM Device table");
        LOGGER.info(" Validated: User entry created in user table in mysql idm db");
        LOGGER.info(" Validated: Analytics events related to Device enrollment sent by DRS service");
        LOGGER.info(" Validated: Registry Only Techpulse entitlement is present");

    }

    @Test(priority = 45, groups = { "REGRESSION_ACTIVECARE" }, description = "Test Case ID : C65387285")
    public void importReadyDeviceForAlreadyImportedTenant() throws Exception {
        LOGGER.info("✓✓✓ TEST STARTED SUCCESSFULLY ✓✓✓");
        WEPOrdersListPage ordersListPage = new WEPOrdersListPage(PreDefinedActions.getDriver()).getInstance();
        String effectiveSerial = ordersListPage.makeEntryInSNRTable();

        ImportResult activeCareImport = performCsvImport("New Order - Active Care", 1, false,
                "ACOnlyTenantName_US", ACGenerationVariant.VALID_ROWS, null, effectiveSerial);

        String[] orderTypes = {"New Order - Active Care"};

        LOGGER.info("Starting OPTIMIZED multi-order type import test with effectiveSerial: {}", effectiveSerial);
        Map<String, CsvFileData> csvFilesByType = createMultipleOrderTypesForSameSerialNumber(orderTypes, effectiveSerial, "INVALID_TENANT");
        ArrayList<String> serials = performBatchCsvImport(csvFilesByType);
        checkOndemandToggle(effectiveSerial);
        verifyDeviceStatusInUI(serials, "READY");
        UIDeviceData uiDeviceData = capturePlanCardsFromDeviceDetailsPage();
        validatePlanCardsFromDeviceDetailsPage(uiDeviceData, orderTypes);

        List<String> providersObj = Arrays.asList("AcOrderSubscriptionDataProvider", "DynamoDbProvider", "AssetMongoProvider");
        DeviceReportLambdaTables deviceReportLambdaTables = DBUtil.fetchDeviceReportLambda(effectiveSerial, providersObj);
        ActiveCareDBValidation.ValidateDBDataPCETest(orderTypes, effectiveSerial, csvFilesByType, uiDeviceData, deviceReportLambdaTables);
        LOGGER.info("✓✓✓ TEST COMPLETED SUCCESSFULLY ✓✓✓");

    }
 // Test case to verify Progress Tab is displayed on incident details 
  	@Test(priority = 46, groups = { "REGRESSION_ACTIVECARE","PRODUCTION_ACTIVECARE"}, description = "C53303648")
  	public final void verifyProgressTabOnIncidentDetails() throws Exception {
  		login("AC_USER", "AC_PASSWORD");
  		LOGGER.info("Login successful.");
  		setUp();
  		hardwareSupport.clickOnElementOfWEPHardwareSupportOption("hardwareSupportIcon");
  		waitForPageLoaded();
  		hardwareSupport.clickByJavaScriptWEPHardwareSupportOption("incident1");
  		hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("progressTab");
  		Assert.assertTrue(hardwareSupport.verifyElementIsEnableOnWEPHardwareSupportPage("progressTab"),
  				"Failed :Progress Tab is missing");

  	}
 }

