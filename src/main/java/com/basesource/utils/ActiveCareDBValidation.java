package com.basesource.utils;

import com.basesource.utils.validator.ACDBValidatorServiceImpl;
import com.basesource.utils.validator.AssetMongoValidatorServiceImpl;
import com.basesource.utils.validator.DynamoDbValidatorServiceImpl;
import com.basesource.utils.validator.ProviderValidatorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import static com.basesource.utils.DBUtil.fetchDeviceReportLambda;

import static com.basesource.action.CommonMethod.getEnvironmentSpecificData;

public class ActiveCareDBValidation {

    public static final class CsvFileData {
        public final String globalFile;
        public final String splitFile;
        public final Map<String, List<String>> csvData;
        public final ArrayList<String> serials;

        public CsvFileData(String globalFile,
                           String splitFile,
                           Map<String, List<String>> csvData,
                           ArrayList<String> serials) {
            this.globalFile = globalFile;
            this.splitFile = splitFile;
            this.csvData = csvData;
            this.serials = serials;
        }
    }

    public static final class UIDeviceData {
        public final String deviceId;
        public final String deviceStatus;
        public final String deviceSrNo;
        public final List<UIPlanCard> planCards;

        public UIDeviceData(String deviceId,
                            String deviceStatus,
                            String deviceSrNo,
                            List<UIPlanCard> planCards) {
            this.deviceId = deviceId;
            this.deviceStatus = deviceStatus;
            this.deviceSrNo = deviceSrNo;
            this.planCards = planCards;
        }

        @Override
        public String toString() {
            return String.format("UIDeviceData{deviceId='%s', status='%s', plans=%d}",
                    deviceId, deviceStatus, planCards.size());
        }
    }

    public static final class UIPlanCard {
        public final String planName;
        public final String status;
        public final String startDate;
        public final String endDate;
        public final String description;

        public UIPlanCard(String planName,
                          String status,
                          String startDate,
                          String endDate,
                          String description) {
            this.planName = planName;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
            this.description = description;
        }

        @Override
        public String toString() {
            return String.format("UIPlanCard{name='%s', status='%s', start='%s', end='%s'}",
                    planName, status, startDate, endDate);
        }
    }

    private static final Logger LOGGER = LogManager.getLogger(ActiveCareDBValidation.class);
    private static final Map<String, Integer> DEVICE_PLAN_PRIORITY = createDevicePlanPriorityMap();

    /**
     * Registry of provider name to validator implementation.
     * Add new providers here for extensible validation.
     */
    private static final Map<String, ProviderValidatorService> VALIDATOR_REGISTRY = Map.of(
        "AssetMongoProvider", new AssetMongoValidatorServiceImpl(),
        "AcOrderSubscriptionDataProvider", new ACDBValidatorServiceImpl(),
        "DynamoDbProvider", new DynamoDbValidatorServiceImpl()
    );

    public static boolean ValidateDBResellerDataPCETest(DeviceReportLambdaTables report,
                                                     Map<String, List<String>> csvData) throws Exception {
        boolean success = true;

        // Step 1: Validate report exists
        if (report == null) {
            LOGGER.error("Device report is null.");
            return false;
        }

        if (report.getProviders() == null || report.getProviders().isEmpty()) {
            LOGGER.error("Device report missing providers");
            return false;
        }

        // Step 2: Extract expected reseller IDs from CSV
        Set<String> expectedIds = Optional.ofNullable(csvData)
                .map(map -> map.getOrDefault("reseller_headquarter_identifier", Collections.emptyList()))
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());

        if (expectedIds.isEmpty()) {
            LOGGER.warn("reseller_headquarter_identifier list from CSV is empty, falling back to reseller_branch_identifier");
            expectedIds = Optional.ofNullable(csvData)
                    .map(map -> map.getOrDefault("reseller_branch_identifier", Collections.emptyList()))
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());
        }

        if (expectedIds.isEmpty()) {
            LOGGER.error("reseller_branch_identifier list from CSV is empty");
            return false;
        }

        // Step 3: Get toggle to determine which table to validate
        String toggle = getToggleValue();
        LOGGER.info("Validating reseller IDs (toggle: {}) against expected values {}", toggle, expectedIds);

        // Step 4: Validate the single provider
        String providerKey = report.getProviders().keySet().iterator().next();
        DeviceReportLambdaTables.ProviderWrapper wrapper = report.getProviders().get(providerKey);
        DeviceReportLambdaTables.Providers providers = wrapper != null ? wrapper.getProviders() : null;

        if (providers == null) {
            LOGGER.error("Provider {} has null providers object", providerKey);
            return false;
        }

        DeviceReportLambdaTables.AcOrderSubscriptionDataProvider acProvider = providers.getAcOrderSubscriptionDataProvider();
        if (acProvider == null || acProvider.getSubscriptions() == null) {
            LOGGER.error("Provider {} missing AcOrderSubscriptionDataProvider subscriptions", providerKey);
            return false;
        }

        switch (toggle.toUpperCase(Locale.ROOT)) {
            case "PCE_DEVICE":
                Set<String> deviceIds = getResellerIdsFromDevices(acProvider);
                boolean deviceMatch = expectedIds.stream().anyMatch(deviceIds::contains);

                if (deviceIds.isEmpty()) {
                    LOGGER.error("Provider {} missing reseller IDs in Device data (toggle=PCE_DEVICE)", providerKey);
                    success = false;
                } else if (!deviceMatch) {
                    LOGGER.error("Expected reseller IDs {} not found in Device list for provider {}", expectedIds, providerKey);
                    success = false;
                } else {
                    LOGGER.info("Reseller ID validation (Device): expected={}, actual={}", expectedIds, deviceIds);
                    expectedIds.stream()
                            .filter(deviceIds::contains)
                            .forEach(id -> LOGGER.info("✓ Provider {} matched Device reseller id {}", providerKey, id));
                }
                break;
            case "PCE_CAMS_DATA":
                Set<String> camsIds = getResellerIdsFromCams(acProvider);
                boolean camsMatch = expectedIds.stream().anyMatch(camsIds::contains);

                if (camsIds.isEmpty()) {
                    LOGGER.error("Provider {} missing reseller IDs in CAMS data (toggle=PCE_CAMS_DATA)", providerKey);
                    success = false;
                } else if (!camsMatch) {
                    LOGGER.error("Expected reseller IDs {} not found in CAMS list for provider {}", expectedIds, providerKey);
                    success = false;
                } else {
                    LOGGER.info("Reseller ID validation (CAMS): expected={}, actual={}", expectedIds, camsIds);
                    expectedIds.stream()
                            .filter(camsIds::contains)
                            .forEach(id -> LOGGER.info("✓ Provider {} matched CAMS reseller id {}", providerKey, id));
                }
                break;
            case "PCE_DEVICE_CAMS_DATA":
                Set<String> deviceIds2 = getResellerIdsFromDevices(acProvider);
                Set<String> camsIds2 = getResellerIdsFromCams(acProvider);
                boolean deviceMatch2 = expectedIds.stream().anyMatch(deviceIds2::contains);
                boolean camsMatch2 = expectedIds.stream().anyMatch(camsIds2::contains);

                if (deviceIds2.isEmpty() || camsIds2.isEmpty()) {
                        if (deviceIds2.isEmpty()) {
                            LOGGER.error("Provider {} missing reseller IDs in Device data (toggle=PCE_DEVICE_CAMS_DATA)", providerKey);
                        }
                        if (camsIds2.isEmpty()) {
                            LOGGER.error("Provider {} missing reseller IDs in CAMS data (toggle=PCE_DEVICE_CAMS_DATA)", providerKey);
                        }
                        success = false;
                    } else if (!deviceMatch2 || !camsMatch2) {
                        if (!deviceMatch2) {
                            LOGGER.error("Expected reseller IDs {} not found in Device list for provider {}. Actual: {}", 
                                    expectedIds, providerKey, deviceIds2);
                        }
                        if (!camsMatch2) {
                            LOGGER.error("Expected reseller IDs {} not found in CAMS list for provider {}. Actual: {}", 
                                    expectedIds, providerKey, camsIds2);
                        }
                        success = false;
                    } else {
                        LOGGER.info("Reseller ID validation (Device & CAMS): device={}, cams={}", deviceIds2, camsIds2);
                        expectedIds.stream()
                                .filter(deviceIds2::contains)
                                .forEach(id -> LOGGER.info("✓ Provider {} matched Device reseller id {}", providerKey, id));
                        expectedIds.stream()
                                .filter(camsIds2::contains)
                                .forEach(id -> LOGGER.info("✓ Provider {} matched CAMS reseller id {}", providerKey, id));
                    }
                break;
            default:
                LOGGER.error("Unknown toggle value: {}", toggle);
                success = false;
        }

        if (success) {
            LOGGER.info("✓ Reseller validation passed");
        } else {
            LOGGER.error("✗ Reseller validation failed");
        }

        return success;
    }

    private static Set<String> getResellerIdsFromDevices(DeviceReportLambdaTables.AcOrderSubscriptionDataProvider acProvider) {
        if (acProvider.getSubscriptions() == null || acProvider.getSubscriptions().getDevices() == null) {
            return Collections.emptySet();
        }
        return acProvider.getSubscriptions().getDevices().stream()
                .map(DeviceReportLambdaTables.Device::getResellerId)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static Set<String> getResellerIdsFromCams(DeviceReportLambdaTables.AcOrderSubscriptionDataProvider acProvider) {
        if (acProvider.getSubscriptions() == null ||
                acProvider.getSubscriptions().getCamsAndAssetData() == null ||
                acProvider.getSubscriptions().getCamsAndAssetData().getReseller() == null) {
            return Collections.emptySet();
        }
        return acProvider.getSubscriptions().getCamsAndAssetData().getReseller().stream()
                .map(DeviceReportLambdaTables.Reseller::getPartnerResellerId)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    public static void ValidateDBDataPCETest(String[] orderTypes , String serialNumber, Map<String, CsvFileData> csvFilesByType, UIDeviceData uiDeviceData, DeviceReportLambdaTables report) throws Exception {
        SoftAssert softly = new SoftAssert();
        boolean success = false;

        if (report == null) {
            softly.fail("Device report is null.");
            LOGGER.error("Device report is null.");
            return;
        }

        try {
            if (report.getProviders() == null) {
                softly.fail("No providers found in device report.");
                LOGGER.error("No providers found in device report.");
            } else {
                for (String providerKey : report.getProviders().keySet()) {
                    DeviceReportLambdaTables.ProviderWrapper wrapper = report.getProviders().get(providerKey);
                    if (wrapper != null && wrapper.getProviders() != null) {
                        DeviceReportLambdaTables.Providers providers = wrapper.getProviders();

                        String toggle = getToggleValue();
                        LOGGER.info("Using toggle value: {}", toggle);

                        if ("PCE_DEVICE".equalsIgnoreCase(toggle)) {
                            validateDeviceEntriesOnly(providers, csvFilesByType, uiDeviceData, softly);
                        } else if ("PCE_CAMS_DATA".equalsIgnoreCase(toggle)) {
                            validateCamsDataOnly(orderTypes , serialNumber, csvFilesByType, uiDeviceData, providers, softly);
                        } else if ("PCE_DEVICE_CAMS_DATA".equalsIgnoreCase(toggle)) {
                            validateDeviceEntriesOnly(providers, csvFilesByType, uiDeviceData, softly);
                            validateCamsDataOnly(orderTypes , serialNumber, csvFilesByType, uiDeviceData, providers, softly);
                        } else {
                            LOGGER.error("Unknown toggle value: {}", toggle);
                        }

                        if (providers.getAssetMongoProvider() != null) {
                            validateAssetMongoProvider(orderTypes,serialNumber,providers.getAssetMongoProvider(), uiDeviceData, softly);
                        } else {
                            softly.fail("Provider " + providerKey + " missing AssetMongoProvider");
                            LOGGER.error("Provider {} missing AssetMongoProvider", providerKey);
                        }

                        if (providers.getDynamoDbProvider() != null) {
                            validateDynamoDbProvider(orderTypes,providers.getDynamoDbProvider(), uiDeviceData, softly);
                        } else {
                            softly.fail("Provider " + providerKey + " missing DynamoDbProvider");
                            LOGGER.error("Provider {} missing DynamoDbProvider", providerKey);
                        }
                        softly.assertAll();
                    } else {
                        softly.fail("Provider wrapper or inner providers is null for key: " + providerKey);
                        LOGGER.error("Provider wrapper or inner providers is null for key: {}", providerKey);
                    }
                }
            }
            softly.assertAll();
            success = true;
        } catch (Throwable e) {
            softly.fail("Exception during DB validation: " + e.getMessage());
            LOGGER.error("Exception during DB validation: {}", e.getMessage());
        }
        if (!success) {
            throw new AssertionError("DB validation failed.\n");
        }
    }

    // -------------------- Device Validation --------------------

    /**
     * Validates device entries against UI data with plan priority logic
     */
    private static void validateDeviceEntriesOnly(DeviceReportLambdaTables.Providers providers,
                                                  Map<String, CsvFileData> csvFilesByType,
                                                  UIDeviceData uiDeviceData, SoftAssert softly) throws Exception {
        LOGGER.info("✓✓✓ Starting Device Entry Validation ✓✓✓");

        if (providers.getAcOrderSubscriptionDataProvider() == null) {
            softly.fail("Missing AcOrderSubscriptionDataProvider");
            LOGGER.error("Missing AcOrderSubscriptionDataProvider");
            return;
        }

        DeviceReportLambdaTables.AcOrderSubscriptionDataProvider acProvider = providers.getAcOrderSubscriptionDataProvider();
        DeviceReportLambdaTables.Subscriptions subscriptions = acProvider.getSubscriptions();

        if (subscriptions == null || subscriptions.getDevices() == null || subscriptions.getDevices().isEmpty()) {
            softly.fail("No device entries found in subscriptions");
            LOGGER.warn("No device entries found in subscriptions");
            return;
        }

        // Filter devices by plan priority
        List<DeviceReportLambdaTables.Device> prioritizedDevices = filterDevicesByPlanPriority(subscriptions.getDevices());

        if (prioritizedDevices.isEmpty()) {
            softly.fail("No devices remain after applying plan prioritization");
            LOGGER.error("No devices remain after applying plan prioritization");
            return;
        }

        LOGGER.info("Validating {} prioritized device entries against UI data", prioritizedDevices.size());

        // Validate each device against UI plan cards
        for (DeviceReportLambdaTables.Device device : prioritizedDevices) {
            validateDeviceAgainstUIPlans(device, uiDeviceData, csvFilesByType, softly);
        }

        // Validate RES plan dates against Device data (priority-based single record per plan)
        if (acProvider.getPlans() != null && !acProvider.getPlans().isEmpty()) {
            validateResPlansAgainstDeviceData(acProvider.getPlans(), prioritizedDevices, softly);
        }

        LOGGER.info("✓✓✓ Completed Device Entry Validation ✓✓✓");
    }

    /**
     * Validates a single device against UI plan cards with priority logic
     */
    private static void validateDeviceAgainstUIPlans(DeviceReportLambdaTables.Device device,
                                                     UIDeviceData uiDeviceData,
                                                     Map<String, CsvFileData> csvFilesByType, SoftAssert softly) {
        String fcpkSerial = device.getFcpkSerialNumber();
        String planDisplayId = device.getPlanDisplayId();

        if (fcpkSerial == null || fcpkSerial.isEmpty()) {
            softly.fail("Device missing FCPK serial number");
            LOGGER.error("Device missing FCPK serial number");
            return;
        }

        LOGGER.info("✓✓✓ Validating device with FCPK: {}, Plan: {} ✓✓✓", fcpkSerial, planDisplayId);

        Map<String, String> planDisplayIdToNameMap = createPlanDisplayIdToNameMap();
        String expectedPlanName = planDisplayIdToNameMap.get(planDisplayId);

        // Find matching UI plan card
        UIPlanCard uiPlan = uiDeviceData.planCards.stream()
                .filter(card -> card.planName.equalsIgnoreCase(expectedPlanName))
                .findFirst()
                .orElse(null);

        if (uiPlan == null) {
            softly.fail(String.format("No matching UI plan card found for device plan: %s", expectedPlanName));
            LOGGER.error("No matching UI plan card found for device plan: {}", expectedPlanName);
            return;
        }

        try {

            softly.assertEquals(device.getDeviceId(),
                    uiDeviceData.deviceId,
                    String.format("Device ID mismatch for FCPK %s. Expected: %s, Actual: %s",
                            fcpkSerial, uiDeviceData.deviceId, device.getDeviceId())
            );
            LOGGER.info("Device ID: expected={}, actual={}", uiDeviceData.deviceId, device.getDeviceId());

            softly.assertEquals(planDisplayId,
                    extractPlanDisplayIdFromName(uiPlan.planName),
                    String.format("Plan name mismatch for FCPK %s. Expected: %s, Actual: %s",
                            fcpkSerial, extractPlanDisplayIdFromName(uiPlan.planName), planDisplayId)
            );
            LOGGER.info("Plan Name: expected={}, actual={}", extractPlanDisplayIdFromName(uiPlan.planName), planDisplayId);

            // Database stores dates in UTC, so parse them as UTC
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dbFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            
            SimpleDateFormat displayFormat = new SimpleDateFormat("d-MMM-yy", Locale.ENGLISH);
            // Use UTC timezone for formatting to ensure consistent date comparison
            SimpleDateFormat utcDisplayFormat = new SimpleDateFormat("d-MMM-yy", Locale.ENGLISH);
            utcDisplayFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date dbStartDate = dbFormat.parse(device.getServiceStartDate());
            Date dbEndDate = dbFormat.parse(device.getServiceEndDate());
            Date uiStartDate = parseUIDateFormat(uiPlan.startDate);
            Date uiEndDate = parseUIDateFormat(uiPlan.endDate);
            
            // Debug logging for date parsing
            LOGGER.info("[DATE DEBUG] FCPK: {}", fcpkSerial);
            LOGGER.info("[DATE DEBUG] DB Start Raw: {}, Parsed: {}", device.getServiceStartDate(), dbStartDate);
            LOGGER.info("[DATE DEBUG] DB End Raw: {}, Parsed: {}", device.getServiceEndDate(), dbEndDate);
            LOGGER.info("[DATE DEBUG] UI Start Raw: '{}', Parsed: {}", uiPlan.startDate, uiStartDate);
            LOGGER.info("[DATE DEBUG] UI End Raw: '{}', Parsed: {}", uiPlan.endDate, uiEndDate);

            // Format using UTC to get the actual date being represented
            String formattedDbStart = utcDisplayFormat.format(dbStartDate);
            String formattedDbEnd = utcDisplayFormat.format(dbEndDate);
            String formattedUiStart = utcDisplayFormat.format(uiStartDate);
            String formattedUiEnd = utcDisplayFormat.format(uiEndDate);
            
            LOGGER.info("[DATE DEBUG] Formatted (UTC) - DB Start: {}, UI Start: {}", formattedDbStart, formattedUiStart);
            LOGGER.info("[DATE DEBUG] Formatted (UTC) - DB End: {}, UI End: {}", formattedDbEnd, formattedUiEnd);

            softly.assertEquals(formattedDbStart, 
                    formattedUiStart,
                    String.format("Service Start Date mismatch for FCPK %s. Expected: %s, Actual: %s",
                            fcpkSerial, formattedUiStart, formattedDbStart));
            LOGGER.info("Service Start Date: expected={}, actual={}", formattedUiStart, formattedDbStart);

            softly.assertEquals(formattedDbEnd,
                    formattedUiEnd,
                    String.format("Service End Date mismatch for FCPK %s. Expected: %s, Actual: %s",
                            fcpkSerial, formattedUiEnd, formattedDbEnd));
            LOGGER.info("Service End Date: expected={}, actual={}", formattedUiEnd, formattedDbEnd);

            softly.assertEquals(device.getPackDescription(),
                    uiPlan.description,
                    String.format("Pack Description mismatch for FCPK %s. Expected: %s, Actual: %s",
                            fcpkSerial, uiPlan.description, device.getPackDescription()));
            LOGGER.info("Pack Description: expected={}, actual={}", uiPlan.description, device.getPackDescription());

            softly.assertEquals(device.getTenantId(),
                    getTenantId(),
                    String.format("Tenant ID mismatch for FCPK %s. Expected: %s, Actual: %s",
                            fcpkSerial, getTenantId(), device.getTenantId()));
            LOGGER.info("Tenant ID: expected={}, actual={}", getTenantId(), device.getTenantId());

            softly.assertEquals(device.getDeviceSrNo(),
                    uiDeviceData.deviceSrNo,
                    String.format("Device Serial Number mismatch for FCPK %s. Expected: %s, Actual: %s",
                            fcpkSerial, uiDeviceData.deviceSrNo, device.getDeviceSrNo()));
            LOGGER.info("Device Serial Number: expected={}, actual={}", uiDeviceData.deviceSrNo, device.getDeviceSrNo());

            String uiStatus = uiPlan.status.replaceAll("\\s*\\(.*\\)\\s*", "").trim();
            String expectedStatus = calculateExpectedStatus(dbStartDate, dbEndDate);
            softly.assertEquals(uiStatus.toLowerCase(Locale.ROOT),
                    expectedStatus.toLowerCase(Locale.ROOT),
                    String.format("Status mismatch for FCPK %s. Expected: %s, Actual: %s",
                            fcpkSerial, expectedStatus.toLowerCase(Locale.ROOT), uiStatus.toLowerCase(Locale.ROOT)));
            LOGGER.info("Status: expected={}, actual={}", expectedStatus, uiStatus);

        } catch (Exception e) {
            softly.fail(String.format("Error validating device with FCPK %s: %s", fcpkSerial, e.getMessage()));
            LOGGER.error("Error validating device with FCPK {}: {}", fcpkSerial, e.getMessage());
        }
        LOGGER.info("✓✓✓ Completed validation for device with FCPK: {} ✓✓✓", fcpkSerial);
    }

    /**
     * Validates RES plan dates against Device data (single priority record per plan)
     * Used in validateDeviceEntriesOnly flow
     */
    public static void validateResPlansAgainstDeviceData(
            List<DeviceReportLambdaTables.Plan> resPlans,
            List<DeviceReportLambdaTables.Device> prioritizedDevices, SoftAssert softly) {

        LOGGER.info("✓✓✓ Starting RES Plan Validation Against Device Data ✓✓✓");

        // Create map of devices by planId (already prioritized)
        Map<String, DeviceReportLambdaTables.Device> devicesByPlanId = prioritizedDevices.stream()
                .filter(device -> device != null && device.getPlanId() != null)
                .collect(Collectors.toMap(
                        DeviceReportLambdaTables.Device::getPlanId,
                        device -> device,
                        (existing, replacement) -> existing  // Keep first (already prioritized)
                ));

        for (DeviceReportLambdaTables.Plan resPlan : resPlans) {
            if (resPlan == null || resPlan.getPlan() == null) {
                continue;
            }

            String planId = resPlan.getPlan().getPlanId();
            String resStartDate = resPlan.getStartDate();
            String resEndDate = resPlan.getEndDate();
            String planDisplayName = resPlan.getPlan().getDisplayName();

            LOGGER.info("Validating RES Plan: {} (planId: {})", planDisplayName, planId);

            if (!devicesByPlanId.containsKey(planId)) {
                LOGGER.warn("⚠ No Device data found for planId: {} ({})", planId, planDisplayName);
                continue;
            }

            DeviceReportLambdaTables.Device device = devicesByPlanId.get(planId);
            String formattedDeviceStart = extractDatePart(device.getServiceStartDate());
            String formattedDeviceEnd = extractDatePart(device.getServiceEndDate());

            LOGGER.info("  RES Plan: Start={}, End={}", resStartDate, resEndDate);
            LOGGER.info("  Device (FCPK {}): Start={}, End={}", device.getFcpkSerialNumber(), formattedDeviceStart, formattedDeviceEnd);

            softly.assertEquals(formattedDeviceStart,
                    resStartDate,
                    String.format("Device start date mismatch for planId %s (%s). " +
                                    "Expected (RES): %s, Actual (Device): %s",
                            planId, planDisplayName, resStartDate, formattedDeviceStart));
            LOGGER.info("Start Date: expected={}, actual={}", resStartDate, formattedDeviceStart);

            softly.assertEquals(formattedDeviceEnd,
                    resEndDate,
                    String.format("Device end date mismatch for planId %s (%s). " +
                                    "Expected (RES): %s, Actual (Device): %s",
                            planId, planDisplayName, resEndDate, formattedDeviceEnd));
            LOGGER.info("End Date: expected={}, actual={}", resEndDate, formattedDeviceEnd);

            String expectedPlanId = getExpectedPlanId(planDisplayName);
            softly.assertTrue(planId != null && !planId.isBlank(),
                    String.format("Plan ID missing for %s", planDisplayName));

            if (expectedPlanId != null) {
                softly.assertEquals(planId,
                        expectedPlanId,
                        String.format("Plan ID mismatch for %s. Expected: %s, Actual: %s",
                                planDisplayName, expectedPlanId, planId));
            }
            LOGGER.info("Plan ID: expected={}, actual={}", expectedPlanId, planId);
        }
        LOGGER.info("✓✓✓ RES Plan Validation Against Device Data COMPLETED SUCCESSFULLY ✓✓✓");
    }

    // -------------------- CAMS Validation --------------------

    /**
     * Validates CAMS data entries against UI plan data
     */
    private static void validateCamsDataOnly(String[] orderTypes , String serialNumber, Map<String, CsvFileData> csvFilesByType, UIDeviceData uiDeviceData, DeviceReportLambdaTables.Providers providers, SoftAssert softly ) throws Exception {
        LOGGER.info("✓✓✓ Starting CAMS Data Validation ✓✓✓");

        if (providers.getAcOrderSubscriptionDataProvider() == null) {
            softly.fail("Missing AcOrderSubscriptionDataProvider");
            LOGGER.error("Missing AcOrderSubscriptionDataProvider");
            return;
        }

        DeviceReportLambdaTables.AcOrderSubscriptionDataProvider acProvider = providers.getAcOrderSubscriptionDataProvider();
        DeviceReportLambdaTables.Subscriptions subscriptions = acProvider.getSubscriptions();

        if (subscriptions == null || subscriptions.getCamsAndAssetData() == null) {
            softly.fail("Missing CAMS and Asset data in subscriptions");
            LOGGER.warn("Missing CAMS and Asset data in subscriptions");
            return;
        }

        DeviceReportLambdaTables.CamsAndAssetData camsAndAssetData = subscriptions.getCamsAndAssetData();

        validateCsvDataAgainstCamsData(camsAndAssetData, csvFilesByType, softly);
        validateCamsDataEntries(camsAndAssetData,  uiDeviceData, softly);
        validateAssetDataEntries(camsAndAssetData, uiDeviceData, softly);
        validateResellerData(camsAndAssetData, softly);

        if (acProvider.getPlans() != null && !acProvider.getPlans().isEmpty()) {
            String toggle = getToggleValue();
            if ("PCE_CAMS_DATA".equalsIgnoreCase(toggle)) {
                // Cumulative validation for CAMS-only flow
                LOGGER.info("Using cumulative RES plan validation (toggle: PCE_CAMS_DATA)");
                validateResPlansAgainstCumulativeCamsData(acProvider.getPlans(), camsAndAssetData, softly);

            } else if ("PCE_DEVICE_CAMS_DATA".equalsIgnoreCase(toggle)) {
                // Priority-based validation for CAMS data (filtered by plan priority)
                LOGGER.info("Using priority-based RES plan validation against CAMS data (toggle: PCE_DEVICE_CAMS_DATA)");
                List<DeviceReportLambdaTables.CamsData> prioritizedCamsData = filterCamsByPlanPriority(camsAndAssetData.getCamsData());
                validateResPlansAgainstPrioritizedCamsData(acProvider.getPlans(), prioritizedCamsData, softly);
            }
        }

        LOGGER.info("✓✓✓ CAMS Data validation COMPLETED SUCCESSFULLY ✓✓✓");
    }

    /**
     * Validates CSV imported data against CAMS data by matching FCPK serial numbers
     */
    private static void validateCsvDataAgainstCamsData(DeviceReportLambdaTables.CamsAndAssetData camsAndAssetData,
                                                       Map<String, CsvFileData> csvFilesByType, SoftAssert softly) {

        LOGGER.info("Starting CSV Data validation against CAMS data");

        if (camsAndAssetData.getCamsData() == null || camsAndAssetData.getCamsData().isEmpty()) {
            softly.fail("No CAMS data found for CSV validation");
            LOGGER.warn("No CAMS data found");
            return;
        }

        // Create map of CAMS data by FCPK for quick lookup
        Map<String, DeviceReportLambdaTables.CamsData> camsByFcpk = camsAndAssetData.getCamsData().stream()
                .filter(cams -> cams != null && cams.getFcpkSerialNumber() != null)
                .collect(Collectors.toMap(
                        DeviceReportLambdaTables.CamsData::getFcpkSerialNumber,
                        cams -> cams,
                        (existing, replacement) -> existing  // Keep first record
                ));

        for (Map.Entry<String, CsvFileData> entry : csvFilesByType.entrySet()) {
            String orderType = entry.getKey();
            CsvFileData csvData = entry.getValue();

            // Extract FCPK serials from CSV
            List<String> fcpkSerials = csvData.csvData.getOrDefault("FCPKSerialNumber", new ArrayList<>());
            List<String> objectOfServiceSerials = csvData.csvData.getOrDefault("ObjectOfServiceSerialNumber", new ArrayList<>());
//            List<String> endCustomerNames = csvData.csvData.getOrDefault("EndCustomerName", new ArrayList<>());
            List<String> fcpkProductIds = csvData.csvData.getOrDefault("FCPKProductID", new ArrayList<>());
            List<String> startDates = csvData.csvData.getOrDefault("FCPKServiceStartDate", new ArrayList<>());
            List<String> endDates = csvData.csvData.getOrDefault("FCPKServiceEndDate", new ArrayList<>());
            List<String> packDescriptions = csvData.csvData.getOrDefault("PackDescription", new ArrayList<>());

            LOGGER.info("Validating {} CSV entries for order type: {}", fcpkSerials.size(), orderType);

            for (int i = 0; i < fcpkSerials.size(); i++) {
                String fcpkSerial = fcpkSerials.get(i);

                if (fcpkSerial == null || fcpkSerial.trim().isEmpty()) {
                    softly.fail("Empty FCPK serial found in CSV for order type: " + orderType);
                    continue;
                }

                // Find matching CAMS data by FCPK
                DeviceReportLambdaTables.CamsData camsData = camsByFcpk.get(fcpkSerial);

                if (camsData == null) {
                    softly.fail(String.format(
                            "No CAMS data found for CSV FCPK serial: %s (Order Type: %s)",
                            fcpkSerial, orderType));
                    LOGGER.error("FCPK {} not found in CAMS data", fcpkSerial);
                    continue;
                }

                try {
                    // ========== Validate FCPK Serial ==========
                    softly.assertEquals(camsData.getFcpkSerialNumber(),
                            fcpkSerial,
                            String.format("FCPK Serial mismatch for order type %s. Expected: %s, Actual: %s",
                                    orderType, fcpkSerial, camsData.getFcpkSerialNumber()));
                    LOGGER.info("✓ FCPK Serial: {}", fcpkSerial);

                    // ========== Validate Object of Service Serial Number ==========
                    if (i < objectOfServiceSerials.size()) {
                        String csvObjectSerial = objectOfServiceSerials.get(i);
                        softly.assertEquals(camsData.getDeviceSrNo(),
                                csvObjectSerial,
                                String.format("Object of Service Serial mismatch for FCPK %s. Expected: %s, Actual: %s",
                                        fcpkSerial, csvObjectSerial, camsData.getDeviceSrNo()));
                        LOGGER.info("✓ Object of Service Serial: {}", csvObjectSerial);
                    }

                    // ========== Validate FCPK Product ID ==========
                    if (i < fcpkProductIds.size()) {
                        String csvProductId = fcpkProductIds.get(i);
                        softly.assertEquals(camsData.getFcpkProductId(),
                                csvProductId,
                                String.format("FCPK Product ID mismatch for FCPK %s. Expected: %s, Actual: %s",
                                        fcpkSerial, csvProductId, camsData.getFcpkProductId()));
                        LOGGER.info("✓ FCPK Product ID: {}", csvProductId);
                    }

                    // ========== Validate Pack Description ==========
                    if (i < packDescriptions.size()) {
                        String csvPackDesc = packDescriptions.get(i);
                        softly.assertEquals(camsData.getPackDescription(),
                                csvPackDesc,
                                String.format("Pack Description mismatch for FCPK %s. Expected: %s, Actual: %s",
                                        fcpkSerial, csvPackDesc, camsData.getPackDescription()));
                        LOGGER.info("✓ Pack Description: {}", csvPackDesc);
                    }

                    // ========== Validate Service Dates ==========
                    softly.assertTrue(camsData.getServiceStartDate() != null && !camsData.getServiceStartDate().isBlank(),
                            String.format("Missing service start date in CAMS for FCPK %s", fcpkSerial));

                    softly.assertTrue(camsData.getServiceEndDate() != null && !camsData.getServiceEndDate().isBlank(),
                            String.format("Missing service end date in CAMS for FCPK %s", fcpkSerial));

                    // Validate dates format and end date >= start date
                    try {
                        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(camsData.getServiceStartDate());
                        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(camsData.getServiceEndDate());

                        softly.assertTrue(endDate.getTime() >= startDate.getTime(),
                                String.format("Service end date before start date for FCPK %s. Start: %s, End: %s",
                                        fcpkSerial, camsData.getServiceStartDate(), camsData.getServiceEndDate()));

                        // Validate CSV dates match CAMS dates (format: dd-MM-yyyy)
                        if (i < startDates.size() && i < endDates.size()) {
                            String csvStartDate = startDates.get(i);
                            String csvEndDate = endDates.get(i);

                            SimpleDateFormat csvDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            Date csvStart = csvDateFormat.parse(csvStartDate);
                            Date csvEnd = csvDateFormat.parse(csvEndDate);

                            SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String camsStartDateStr = displayFormat.format(startDate);
                            String camsEndDateStr = displayFormat.format(endDate);
                            String csvStartDateStr = displayFormat.format(csvStart);
                            String csvEndDateStr = displayFormat.format(csvEnd);

                            softly.assertEquals(camsStartDateStr,
                                    csvStartDateStr,
                                    String.format("Start Date mismatch for FCPK %s. CSV: %s, CAMS: %s",
                                            fcpkSerial, csvStartDateStr, camsStartDateStr));
                            LOGGER.info("✓ Service Start Date - CSV: {}, CAMS: {}", csvStartDateStr, camsStartDateStr);

                            softly.assertEquals(camsEndDateStr,
                                    csvEndDateStr,
                                    String.format("End Date mismatch for FCPK %s. CSV: %s, CAMS: %s",
                                            fcpkSerial, csvEndDateStr, camsEndDateStr));
                            LOGGER.info("✓ Service End Date - CSV: {}, CAMS: {}", csvEndDateStr, camsEndDateStr);

                        }
                    } catch (Exception e) {
                        softly.fail(String.format("Invalid date format for FCPK %s: %s", fcpkSerial, e.getMessage()));
                    }

                    // ========== Validate License Status ==========
                    softly.assertEquals(camsData.getLicenseStatus(),
                            "VALID",
                            String.format("Invalid license status for FCPK %s. Expected: VALID, Actual: %s",
                                    fcpkSerial, camsData.getLicenseStatus()));
                    LOGGER.info("✓ License Status: VALID");
                    LOGGER.info("✓ CSV FCPK {} validated successfully against CAMS data", fcpkSerial);

                } catch (Exception e) {
                    softly.fail(String.format("Error validating CSV FCPK %s against CAMS: %s", fcpkSerial, e.getMessage()));
                    LOGGER.error("Validation error for FCPK {}: {}", fcpkSerial, e.getMessage(), e);
                }
            }
        }

        LOGGER.info("Completed CSV Data validation against CAMS data");
    }

    /**
     * Validates individual CAMS data entries against UI plan cards
     */
    private static void validateCamsDataEntries(DeviceReportLambdaTables.CamsAndAssetData camsAndAssetData, UIDeviceData uiDeviceData, SoftAssert softly) {
        LOGGER.info("✓✓✓ CAMS Data Entries validation STARTED ✓✓✓");

        if (camsAndAssetData.getCamsData() == null || camsAndAssetData.getCamsData().isEmpty()) {
            softly.fail("No CAMS data found");
            LOGGER.warn("No CAMS data found");
            return;
        }

        Map<String, String> planDisplayIdToNameMap = createPlanDisplayIdToNameMap();
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat displayFormat = new SimpleDateFormat("d-MMM-yy", Locale.ENGLISH);

        for (DeviceReportLambdaTables.CamsData camsData : camsAndAssetData.getCamsData()) {
            String planDisplayId = camsData.getPlanDisplayId();
            String expectedPlanName = planDisplayIdToNameMap.get(planDisplayId);

            LOGGER.info("✓ Validating CAMS data for plan: {}", planDisplayId);

            // Find matching UI plan card
            UIPlanCard uiPlan = uiDeviceData.planCards.stream()
                    .filter(card -> card.planName.equalsIgnoreCase(expectedPlanName))
                    .findFirst()
                    .orElse(null);

            if (uiPlan == null) {
                softly.fail(String.format("No matching UI plan card found for CAMS plan: %s", expectedPlanName));
                LOGGER.error("No matching UI plan card found for CAMS plan: {}", expectedPlanName);
                continue;
            }

            try {
                // Database stores dates in UTC, so parse them as UTC
                SimpleDateFormat camsDbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                camsDbFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                
                // Parse dates
                Date camsStartDate = camsDbFormat.parse(camsData.getServiceStartDate());
                Date camsEndDate = camsDbFormat.parse(camsData.getServiceEndDate());
                Date uiStartDate = parseUIDateFormat(uiPlan.startDate);
                Date uiEndDate = parseUIDateFormat(uiPlan.endDate);

                // Use UTC timezone for formatting to ensure consistent date comparison
                SimpleDateFormat utcDisplayFormat = new SimpleDateFormat("d-MMM-yy", Locale.ENGLISH);
                utcDisplayFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                
                String formattedCamsStart = utcDisplayFormat.format(camsStartDate);
                String formattedCamsEnd = utcDisplayFormat.format(camsEndDate);
                String formattedUiStart = utcDisplayFormat.format(uiStartDate);
                String formattedUiEnd = utcDisplayFormat.format(uiEndDate);

                softly.assertEquals(
                        formattedCamsStart,
                        formattedUiStart,
                        String.format("CamsData Fcpk Service Start Date mismatch! Expected: %s, Actual: %s",
                                formattedUiStart, formattedCamsStart));
                LOGGER.info("CamsData Service Start Date: expected={}, actual={}", formattedUiStart, formattedCamsStart);

                softly.assertEquals(
                        formattedCamsEnd,
                        formattedUiEnd,
                        String.format("CamsData Fcpk Service End Date mismatch! Expected: %s, Actual: %s",
                                formattedUiEnd, formattedCamsEnd));
                LOGGER.info("CamsData Service End Date: expected={}, actual={}", formattedUiEnd, formattedCamsEnd);

                String uiStatus = uiPlan.status.replaceAll("\\s*\\(.*\\)\\s*", "").trim();
                String expectedStatus = calculateExpectedStatus(camsStartDate, camsEndDate);
                softly.assertEquals(
                        uiStatus.toLowerCase(Locale.ROOT),
                        expectedStatus.toLowerCase(Locale.ROOT),
                        String.format("Status mismatch for FCPK %s. Expected: %s, Actual: %s",
                                camsData.getFcpkSerialNumber(), expectedStatus.toLowerCase(Locale.ROOT), uiStatus.toLowerCase(Locale.ROOT)));
                LOGGER.info("Status: expected={}, actual={}", expectedStatus, uiStatus);

                softly.assertEquals(
                        camsData.getDeviceSrNo(),
                        uiDeviceData.deviceSrNo,
                        String.format("Device Serial Number mismatch for plan %s", expectedPlanName));
                LOGGER.info("Device Serial Number: expected={}, actual={}", uiDeviceData.deviceSrNo, camsData.getDeviceSrNo());

                softly.assertEquals(
                        camsData.getLicenseStatus(),
                        "VALID",
                        String.format("License Status mismatch for plan %s", expectedPlanName));
                LOGGER.info("CAMS validation passed for plan {}: Start={}, End={}, Desc={}",
                        expectedPlanName, formattedCamsStart, formattedCamsEnd, camsData.getPackDescription());

            } catch (Exception e) {
                softly.fail(String.format("Error validating CAMS data for plan %s: %s", expectedPlanName, e.getMessage()));
                LOGGER.error("Error validating CAMS data for plan {}: {}", expectedPlanName, e.getMessage());
            }
        }
        LOGGER.info("✓✓✓ CAMS Data Entries validation COMPLETED SUCCESSFULLY ✓✓✓");
    }

    /**
     * Validates asset data entries against UI device data
     */
    private static void validateAssetDataEntries(DeviceReportLambdaTables.CamsAndAssetData camsAndAssetData, UIDeviceData uiDeviceData, SoftAssert softly) {
        LOGGER.info("✓✓✓ AssetMongoProvider validation STARTED ✓✓✓");

        if (camsAndAssetData.getAssets() == null || camsAndAssetData.getAssets().isEmpty()) {
            softly.fail("No asset data found");
            LOGGER.warn("No asset data found");
            return;
        }

        for (DeviceReportLambdaTables.AssetData asset : camsAndAssetData.getAssets()) {
            softly.assertEquals(
                    asset.getDeviceId(),
                    uiDeviceData.deviceId,
                    String.format("Asset Device ID mismatch! Expected: %s, Actual: %s",
                            uiDeviceData.deviceId, asset.getDeviceId()));
            LOGGER.info("Asset Device ID: expected={}, actual={}", uiDeviceData.deviceId, asset.getDeviceId());

            softly.assertEquals(
                    asset.getWxp_state(),
                    uiDeviceData.deviceStatus,
                    String.format("Asset wxp_state mismatch! Expected: %s, Actual: %s",
                            uiDeviceData.deviceStatus, asset.getWxp_state()));
            LOGGER.info("Asset wxp_state: expected={}, actual={}", uiDeviceData.deviceStatus, asset.getWxp_state());

            String effectiveTenantId = getTenantId();
            softly.assertEquals(
                    asset.getTenantId(),
                    effectiveTenantId,
                    String.format("Asset Tenant ID mismatch! Expected: %s, Actual: %s",
                            effectiveTenantId, asset.getTenantId()));
            LOGGER.info("Asset Tenant ID: expected={}, actual={}", effectiveTenantId, asset.getTenantId());
        }
        LOGGER.info("✓✓✓ AssetMongoProvider validation COMPLETED SUCCESSFULLY ✓✓✓");
    }

    /**
     * Validates reseller data if present
     */
    private static void validateResellerData(DeviceReportLambdaTables.CamsAndAssetData camsAndAssetData, SoftAssert softly) {
        LOGGER.info("Validating reseller data");

        if (camsAndAssetData.getReseller() == null || camsAndAssetData.getReseller().isEmpty()) {
            LOGGER.warn("No reseller data found in CAMS and Asset data");
            return;
        }

        LOGGER.info("Validating reseller in camsAndAssetData");
        for (DeviceReportLambdaTables.Reseller reseller : camsAndAssetData.getReseller()) {
            softly.assertTrue(reseller.getPartnerResellerId() != null && !reseller.getPartnerResellerId().isEmpty(),
                    "Reseller partnerResellerId should not be null or empty");
            LOGGER.info("Reseller partnerResellerId: actual={}", reseller.getPartnerResellerId());
        }

        LOGGER.info("Reseller data validation completed");
    }

    /**
     * Validates RES plan dates against prioritized CAMS data (single priority record per planId)
     * Used in validateCamsDataOnly flow with PCE_DEVICE_CAMS_DATA toggle
     */
    private static void validateResPlansAgainstPrioritizedCamsData(
            List<DeviceReportLambdaTables.Plan> resPlans,
            List<DeviceReportLambdaTables.CamsData> prioritizedCamsData, SoftAssert softly) {

        LOGGER.info("✓✓✓ Starting RES Plan Validation Against Prioritized CAMS Data ✓✓✓");

        // Create map of prioritized CAMS data by planId
        Map<String, DeviceReportLambdaTables.CamsData> camsByPlanId = prioritizedCamsData.stream()
                .filter(cams -> cams != null && cams.getPlanId() != null)
                .collect(Collectors.toMap(
                        DeviceReportLambdaTables.CamsData::getPlanId,
                        cams -> cams,
                        (existing, replacement) -> existing  // Keep first (already prioritized)
                ));

        for (DeviceReportLambdaTables.Plan resPlan : resPlans) {
            if (resPlan == null || resPlan.getPlan() == null) {
                continue;
            }

            String planId = resPlan.getPlan().getPlanId();
            String resStartDate = resPlan.getStartDate();
            String resEndDate = resPlan.getEndDate();
            String planDisplayName = resPlan.getPlan().getDisplayName();

            LOGGER.info("Validating RES Plan: {} (planId: {})", planDisplayName, planId);

            if (!camsByPlanId.containsKey(planId)) {
                LOGGER.warn("⚠ No prioritized CAMS data found for planId: {} ({})", planId, planDisplayName);
                continue;
            }

            DeviceReportLambdaTables.CamsData camsData = camsByPlanId.get(planId);
            String formattedCamsStart = extractDatePart(camsData.getServiceStartDate());
            String formattedCamsEnd = extractDatePart(camsData.getServiceEndDate());

            LOGGER.info("  RES Plan: Start={}, End={}", resStartDate, resEndDate);
            LOGGER.info("  CAMS (Priority-based FCPK {}): Start={}, End={}",
                    camsData.getFcpkSerialNumber(), formattedCamsStart, formattedCamsEnd);

            softly.assertEquals(
                    formattedCamsStart,
                    resStartDate,
                    String.format("CAMS start date mismatch for planId %s (%s). " +
                                    "Expected (RES): %s, Actual (CAMS FCPK %s): %s",
                            planId, planDisplayName, resStartDate, camsData.getFcpkSerialNumber(), formattedCamsStart));
            LOGGER.info("Start Date: expected={}, actual={}", resStartDate, formattedCamsStart);

            softly.assertEquals(
                    formattedCamsEnd,
                    resEndDate,
                    String.format("CAMS end date mismatch for planId %s (%s). " +
                                    "Expected (RES): %s, Actual (CAMS FCPK %s): %s",
                            planId, planDisplayName, resEndDate, camsData.getFcpkSerialNumber(), formattedCamsEnd));
            LOGGER.info("End Date: expected={}, actual={}", resEndDate, formattedCamsEnd);

            String expectedPlanId = getExpectedPlanId(planDisplayName);
            softly.assertTrue(
                    planId != null && !planId.isBlank(),
                    String.format("Plan ID missing for %s", planDisplayName));

            if (expectedPlanId != null) {
                softly.assertEquals(
                        planId,
                        expectedPlanId,
                        String.format("Plan ID mismatch for %s. Expected: %s, Actual: %s",
                                planDisplayName, expectedPlanId, planId));
            }
            LOGGER.info("Plan ID: expected={}, actual={}", expectedPlanId, planId);
        }
        LOGGER.info("✓✓✓ RES Plan Validation Against Prioritized CAMS Data COMPLETED SUCCESSFULLY ✓✓✓");
    }

    /**
     * Validates RES plan dates against cumulative CAMS data (multiple records per plan)
     * Used in validateCamsDataOnly flow
     */
    private static void validateResPlansAgainstCumulativeCamsData(
            List<DeviceReportLambdaTables.Plan> resPlans,
            DeviceReportLambdaTables.CamsAndAssetData camsAndAssetData, SoftAssert softly) {

        LOGGER.info("✓✓✓ Starting RES Plan Validation Against Cumulative CAMS Data ✓✓✓");

        Map<String, CumulativeDateRange> camsCumulativeByPlanId = calculateCumulativeCamsDateRanges(camsAndAssetData);

        for (DeviceReportLambdaTables.Plan resPlan : resPlans) {
            if (resPlan == null || resPlan.getPlan() == null) {
                continue;
            }

            String planId = resPlan.getPlan().getPlanId();
            String resStartDate = resPlan.getStartDate();
            String resEndDate = resPlan.getEndDate();
            String planDisplayName = resPlan.getPlan().getDisplayName();

            LOGGER.info("Validating RES Plan: {} (planId: {})", planDisplayName, planId);

            if (!camsCumulativeByPlanId.containsKey(planId)) {
                LOGGER.warn("⚠ No CAMS data found for planId: {} ({})", planId, planDisplayName);
                continue;
            }

            CumulativeDateRange camsRange = camsCumulativeByPlanId.get(planId);
            String formattedCamsStart = extractDatePart(camsRange.minStartDate);
            String formattedCamsEnd = extractDatePart(camsRange.maxEndDate);

            LOGGER.info("  RES Plan: Start={}, End={}", resStartDate, resEndDate);
            LOGGER.info("  CAMS Cumulative ({} records): Start={}, End={}", camsRange.recordCount, formattedCamsStart, formattedCamsEnd);

            softly.assertEquals(
                    formattedCamsStart,
                    resStartDate,
                    String.format("CAMS cumulative start date mismatch for planId %s (%s). " +
                                    "Expected (RES): %s, Actual (min from %d CAMS records): %s",
                            planId, planDisplayName, resStartDate, camsRange.recordCount, formattedCamsStart));
            LOGGER.info("Start Date: expected={}, actual={}", resStartDate, formattedCamsStart);

            softly.assertEquals(
                    formattedCamsEnd,
                    resEndDate,
                    String.format("CAMS cumulative end date mismatch for planId %s (%s). " +
                                    "Expected (RES): %s, Actual (max from %d CAMS records): %s",
                            planId, planDisplayName, resEndDate, camsRange.recordCount, formattedCamsEnd));
            LOGGER.info("End Date: expected={}, actual={}", resEndDate, formattedCamsEnd);

            String expectedPlanId = getExpectedPlanId(planDisplayName);
            softly.assertTrue(
                    planId != null && !planId.isBlank(),
                    String.format("Plan ID missing for %s", planDisplayName));

            if (expectedPlanId != null) {
                softly.assertEquals(
                        planId,
                        expectedPlanId,
                        String.format("Plan ID mismatch for %s. Expected: %s, Actual: %s",
                                planDisplayName, expectedPlanId, planId));
            }
            LOGGER.info("Plan ID: expected={}, actual={}", expectedPlanId, planId);
        }

        LOGGER.info("✓✓✓ RES Plan Validation Against Cumulative CAMS Data COMPLETED SUCCESSFULLY ✓✓✓");
    }

    /**
     * Calculates cumulative date ranges from all CAMS data grouped by planId
     * Returns earliest startDate and latest endDate for each planId
     */
    private static Map<String, CumulativeDateRange> calculateCumulativeCamsDateRanges(
            DeviceReportLambdaTables.CamsAndAssetData camsAndAssetData) {

        Map<String, CumulativeDateRange> cumulativeRanges = new HashMap<>();

        if (camsAndAssetData == null || camsAndAssetData.getCamsData() == null) {
            return cumulativeRanges;
        }

        // Group CAMS data by planId
        Map<String, List<DeviceReportLambdaTables.CamsData>> camsByPlanId =
                camsAndAssetData.getCamsData().stream()
                        .filter(cams -> cams != null && cams.getPlanId() != null)
                        .collect(Collectors.groupingBy(DeviceReportLambdaTables.CamsData::getPlanId));

        // Calculate cumulative dates for each planId
        for (Map.Entry<String, List<DeviceReportLambdaTables.CamsData>> entry : camsByPlanId.entrySet()) {
            String planId = entry.getKey();
            List<DeviceReportLambdaTables.CamsData> camsRecords = entry.getValue();

            // Get the earliest start date (min)
            String minStartDate = camsRecords.stream()
                    .map(DeviceReportLambdaTables.CamsData::getServiceStartDate)
                    .filter(Objects::nonNull)
                    .sorted()
                    .findFirst()
                    .orElse(null);

            // Get latest end date (max)
            String maxEndDate = camsRecords.stream()
                    .map(DeviceReportLambdaTables.CamsData::getServiceEndDate)
                    .filter(Objects::nonNull)
                    .sorted(Collections.reverseOrder())
                    .findFirst()
                    .orElse(null);

            CumulativeDateRange range = new CumulativeDateRange(
                    planId, minStartDate, maxEndDate, camsRecords.size()
            );
            cumulativeRanges.put(planId, range);

            LOGGER.debug("CAMS Cumulative for planId {}: {} records, {} to {}",
                    planId, camsRecords.size(), minStartDate, maxEndDate);
        }

        return cumulativeRanges;
    }

    /**
     * Data class to hold cumulative date range for a plan
     */
    private static class CumulativeDateRange {
        final String planId;
        final String minStartDate;
        final String maxEndDate;
        final int recordCount;

        CumulativeDateRange(String planId, String minStartDate, String maxEndDate, int recordCount) {
            this.planId = planId;
            this.minStartDate = minStartDate;
            this.maxEndDate = maxEndDate;
            this.recordCount = recordCount;
        }

        @Override
        public String toString() {
            return String.format("CumulativeDateRange{planId='%s', start='%s', end='%s', count=%d}",
                    planId, minStartDate, maxEndDate, recordCount);
        }
    }


    // -------------------- Provider Validation --------------------
    /**
     * Validates AssetMongoProvider against UI device data
     */
    private static void validateAssetMongoProvider( String[] orderTypes ,String serialNumber, DeviceReportLambdaTables.AssetMongoProvider assetMongoProvider,
                                                    UIDeviceData uiDeviceData, SoftAssert softly) {
        LOGGER.info("✓✓✓ AssetMongoProvider validation STARTED ✓✓✓");

        softly.assertEquals(
                assetMongoProvider.getUid(),
                uiDeviceData.deviceId,
                String.format("Device ID (uid) mismatch! Expected: %s, Actual: %s",
                        uiDeviceData.deviceId, assetMongoProvider.getUid()));
        LOGGER.info("AssetMongo Expected Device ID: {}, Actual Device ID: {}", uiDeviceData.deviceId, assetMongoProvider.getUid());

        softly.assertEquals(
                assetMongoProvider.getSerialNumber(),
                uiDeviceData.deviceSrNo,
                String.format("Device Serial Number mismatch! Expected: %s, Actual: %s",
                        uiDeviceData.deviceSrNo, assetMongoProvider.getSerialNumber()));
        LOGGER.info("AssetMongo Expected Serial Number: {}, Actual Serial Number: {}", uiDeviceData.deviceSrNo, assetMongoProvider.getSerialNumber());

        if (assetMongoProvider.getStatus() != null && assetMongoProvider.getStatus().getEn() != null) {
            String dbStatus = assetMongoProvider.getStatus().getEn().getValue();
            softly.assertEquals(
                    dbStatus,
                    uiDeviceData.deviceStatus,
                    String.format("Device Status mismatch! Expected: %s, Actual: %s",
                            uiDeviceData.deviceStatus, dbStatus)
            );
            LOGGER.info("AssetMongo Expected Device Status: {}, Actual Device Status: {}", uiDeviceData.deviceStatus, dbStatus);
        } else {
            softly.fail("AssetMongoProvider missing status.en object");
            LOGGER.error("AssetMongoProvider missing status.en object");
        }

        if (assetMongoProvider.getPlans() != null && assetMongoProvider.getPlans().getEn() != null
                && assetMongoProvider.getPlans().getEn().getValue() != null) {
            Set<String> expectedServiceNames = getExpectedServiceNamesFromOrderTypes(orderTypes,"AssetMongo");
            Set<String> actualServiceNames = new LinkedHashSet<>(
                    Arrays.asList(assetMongoProvider.getPlans().getEn().getValue().split(","))
            );
            actualServiceNames = actualServiceNames.stream()
                    .map(String::trim)
                    .collect(Collectors.toSet());

            softly.assertEquals(
                    actualServiceNames,
                    expectedServiceNames,
                    String.format("Service Names mismatch! Expected: %s, Actual: %s",
                            expectedServiceNames, actualServiceNames));
            LOGGER.info("AssetMongo Expected Service Names: {}, Actual Service Names: {}", expectedServiceNames, actualServiceNames);
        } else {
            softly.fail("AssetMongoProvider missing plans.en.value object");
            LOGGER.error("AssetMongoProvider missing plans.en.value object");
        }

        String effectiveEndCustomerName = getTenantName();
        softly.assertEquals(
                assetMongoProvider.getCompanyName(),
                effectiveEndCustomerName,
                String.format("Asset End Customer Name mismatch! Expected: %s, Actual: %s",
                        effectiveEndCustomerName, assetMongoProvider.getCompanyName()));
        LOGGER.info("AssetMongo End Customer Name: expected={}, actual={}", effectiveEndCustomerName, assetMongoProvider.getCompanyName());

        String effectiveTenantId = getTenantId();
        softly.assertEquals(
                assetMongoProvider.getTenantId(),
                effectiveTenantId,
                String.format("Asset Tenant ID mismatch! Expected: %s, Actual: %s",
                        effectiveTenantId, assetMongoProvider.getTenantId()));
        LOGGER.info("AssetMongo Tenant ID: expected={}, actual={}", effectiveTenantId, assetMongoProvider.getTenantId());
        LOGGER.info("✓✓✓ AssetMongoProvider validation COMPLETED SUCCESSFULLY ✓✓✓");
    }

    private static void validateDynamoDbProvider(String[] orderTypes, DeviceReportLambdaTables.DynamoDbProvider dynamoDbProvider,
                                                 UIDeviceData uiDeviceData, SoftAssert softly) {
        LOGGER.info("✓✓✓ DynamoDbProvider validation STARTED ✓✓✓");

        if (dynamoDbProvider.getDeviceHashDetails() == null) {
            softly.fail("DynamoDbProvider missing DeviceHashDetails");
            LOGGER.error("DynamoDbProvider missing DeviceHashDetails");
            return;
        }

        DeviceReportLambdaTables.DynamoDbProvider.DeviceHashDetails deviceHashDetails = dynamoDbProvider.getDeviceHashDetails();

        softly.assertEquals(
                deviceHashDetails.getDeviceId(),
                uiDeviceData.deviceId,
                String.format("Device ID mismatch! Expected: %s, Actual: %s",
                        uiDeviceData.deviceId, deviceHashDetails.getDeviceId()));
        LOGGER.info("DynamoDb Expected Device ID: {}, Actual Device ID: {}", uiDeviceData.deviceId, deviceHashDetails.getDeviceId());

        String effectiveTenantId = getTenantId();
        softly.assertEquals(
                deviceHashDetails.getTenantId(),
                effectiveTenantId,
                String.format("Asset Tenant ID mismatch! Expected: %s, Actual: %s",
                        effectiveTenantId, deviceHashDetails.getTenantId()));
        LOGGER.info("DynamoDb Expected Tenant ID: {}, Actual Tenant ID: {}", effectiveTenantId, deviceHashDetails.getTenantId());

        softly.assertEquals(
                deviceHashDetails.getEnrollmentType(),
                "ZTE",
                String.format("EnrollmentType mismatch! Expected: %s, Actual: %s",
                        "ZTE", deviceHashDetails.getEnrollmentType()));
        LOGGER.info("DynamoDb Expected EnrollmentType: {}, Actual EnrollmentType: {}", "ZTE", deviceHashDetails.getEnrollmentType());

        Set<String> expectedServiceNames = getExpectedServiceNamesFromOrderTypes(orderTypes,"DynamoDb");
        Set<String> actualServiceNames = new LinkedHashSet<>(Arrays.asList(deviceHashDetails.getServiceName().split(",")));

        actualServiceNames = actualServiceNames.stream()
                .map(String::trim)
                .collect(Collectors.toSet());

        softly.assertEquals(
                actualServiceNames,
                expectedServiceNames,
                String.format("Service Names mismatch! Expected: %s, Actual: %s",
                        expectedServiceNames, actualServiceNames));
        LOGGER.info("DynamoDb Expected Service Names: {}, Actual Service Names: {}", expectedServiceNames, actualServiceNames);

        LOGGER.info("✓✓✓ DynamoDbProvider validation COMPLETED SUCCESSFULLY ✓✓✓");
    }

    // -------------------- Helper Sections --------------------

    public static Map<String, String> createPlanDisplayIdToNameMap() {
        Map<String, String> map = new HashMap<>();
        map.put("ACTIVECARE", "HP Active Care");
        map.put("PREMIUM", "HP Premium");
        map.put("PREMIUMPLUS", "HP Premium+");
        map.put("OUT_OF_BAND_REMEDIATION", "HP Remotecare");
        return map;
    }

    public static String extractPlanDisplayIdFromName(String uiPlanName) {
        if (uiPlanName.contains("Active Care")) {
            return "ACTIVECARE";
        } else if (uiPlanName.contains("Premium+")) {
            return "PREMIUMPLUS";
        } else if (uiPlanName.contains("Premium")) {
            return "PREMIUM";
        } else if (uiPlanName.contains("Remotecare")) {
            return "OUT_OF_BAND_REMEDIATION";
        }
        return uiPlanName;
    }

    /**
     * GENERIC: Single method to prioritize ANY collection by care plan priority
     * Works for Device, CAMS, or any future object implementing PrioritizableByPlan
     */
    private static <T extends PrioritizableByPlan> List<T> prioritizeByCarePlan(List<T> items) {
        if (items == null || items.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, T> bestCarePlanByKey = new LinkedHashMap<>();
        Map<String, Integer> bestPriorityByKey = new HashMap<>();
        Map<String, T> oobrByKey = new LinkedHashMap<>();

        for (T item : items) {
            if (item == null || item.getUniqueKey() == null || item.getUniqueKey().isEmpty()) {
                continue;
            }

            String planDisplayId = item.getPlanDisplayId();
            String normalizedPlan = planDisplayId != null ? planDisplayId.trim().toUpperCase(Locale.ROOT) : "";

            // Separate OOBR (lower priority)
            if ("OUT_OF_BAND_REMEDIATION".equals(normalizedPlan)) {
                oobrByKey.putIfAbsent(item.getUniqueKey(), item);
                continue;
            }

            // Apply priority for care plans
            Integer priority = DEVICE_PLAN_PRIORITY.get(normalizedPlan);
            if (priority != null) {
                Integer currentBestPriority = bestPriorityByKey.get(item.getUniqueKey());
                if (currentBestPriority == null || priority > currentBestPriority) {
                    bestCarePlanByKey.put(item.getUniqueKey(), item);
                    bestPriorityByKey.put(item.getUniqueKey(), priority);
                }
            }
        }

        List<T> prioritized = new ArrayList<>(bestCarePlanByKey.values());
        prioritized.addAll(oobrByKey.values());

        LOGGER.info("Prioritization: Care Plans={}, OOBR={}, Total={}",
                bestCarePlanByKey.size(), oobrByKey.size(), prioritized.size());

        return prioritized;
    }

    /**
     * Adapter: Makes Device objects compatible with generic prioritization
     */
    private static class DeviceAdapter implements PrioritizableByPlan {
        private final DeviceReportLambdaTables.Device device;

        DeviceAdapter(DeviceReportLambdaTables.Device device) {
            this.device = device;
        }

        @Override
        public String getPlanDisplayId() {
            return device.getPlanDisplayId();
        }

        @Override
        public String getUniqueKey() {
            return device.getFcpkSerialNumber();
        }
    }

    /**
     * Adapter: Makes CAMS objects compatible with generic prioritization
     */
    private static class CamsAdapter implements PrioritizableByPlan {
        private final DeviceReportLambdaTables.CamsData camsData;

        CamsAdapter(DeviceReportLambdaTables.CamsData camsData) {
            this.camsData = camsData;
        }

        @Override
        public String getPlanDisplayId() {
            return camsData.getPlanDisplayId();
        }

        @Override
        public String getUniqueKey() {
            return camsData.getPlanId();
        }
    }

    /**
     * SIMPLIFIED WRAPPER: Filter devices by priority
     * (Replaces old 50-line method)
     */
    public static List<DeviceReportLambdaTables.Device> filterDevicesByPlanPriority(List<DeviceReportLambdaTables.Device> devices) {
        if (devices == null || devices.isEmpty()) return new ArrayList<>();

        List<DeviceAdapter> adapters = devices.stream()
                .map(DeviceAdapter::new)
                .collect(Collectors.toList());

        return prioritizeByCarePlan(adapters).stream()
                .map(adapter -> adapter.device)
                .collect(Collectors.toList());
    }

    /**
     * SIMPLIFIED WRAPPER: Filter CAMS by priority
     * (Replaces old 50-line method)
     */
    private static List<DeviceReportLambdaTables.CamsData> filterCamsByPlanPriority(List<DeviceReportLambdaTables.CamsData> camsDataList) {
        if (camsDataList == null || camsDataList.isEmpty()) return new ArrayList<>();

        List<CamsAdapter> adapters = camsDataList.stream()
                .map(CamsAdapter::new)
                .collect(Collectors.toList());

        return prioritizeByCarePlan(adapters).stream()
                .map(adapter -> adapter.camsData)
                .collect(Collectors.toList());
    }

    /**
     * Universal interface for objects prioritizable by care plan
     */
    private interface PrioritizableByPlan {
        String getPlanDisplayId();
        String getUniqueKey();  // FCPK serial for Device, PlanId for CAMS
    }

    private static Map<String, Integer> createDevicePlanPriorityMap() {
        Map<String, Integer> priority = new HashMap<>();
        priority.put("ACTIVECARE", 1);
        priority.put("PREMIUM", 2);
        priority.put("PREMIUMPLUS", 3);
        return priority;
    }

    /**
     * SIMPLE: Maps imported order types to expected service names
     * Order type → Service name mapping:
     * - "New Order - Active Care" → "ActiveCare" / "HP Active Care"
     * - "New Order - Premium" → "ActiveCare" / "HP Active Care"
     * - "New Order - Premium Plus" → "ActiveCare" / "HP Active Care"
     * - "New Order - Out Of Band Remediation" → "RemoteCare" / "HP Remotecare"
     *
     * @param orderTypes Array of imported order types
     * @param dataProvider Provider type ("AssetMongo" or "DynamoDb")
     * @return Set of expected service names
     */
    private static Set<String> getExpectedServiceNamesFromOrderTypes(String[] orderTypes, String dataProvider) {
        Set<String> serviceNames = new LinkedHashSet<>();

        if (orderTypes == null || orderTypes.length == 0) {
            LOGGER.warn("⚠ No order types provided");
            return serviceNames;
        }
        LOGGER.debug("Deriving service names from {} order types", orderTypes.length);
        for (String orderType : orderTypes) {
            if (orderType == null || orderType.trim().isEmpty()) {
                continue;
            }
            String normalized = orderType.toLowerCase(Locale.ENGLISH).trim();
            String canonicalName = mapOrderTypeToCanonicalService(normalized);
            if (canonicalName != null) {
                serviceNames.add(applyProviderFormat(canonicalName, dataProvider));
            } else {
                LOGGER.warn("⚠ Unsupported order type: {}", orderType);
            }
        }
        LOGGER.info("Expected service names: {}", serviceNames);
        return serviceNames;
    }

    private static String applyProviderFormat(String canonicalName, String dataProvider) {
        if (!"AssetMongo".equalsIgnoreCase(dataProvider)) {
            return canonicalName; // DynamoDb, etc.
        }
        switch (canonicalName) {
            case "ActiveCare":
                return "HP Active Care";
            case "RemoteCare":
                return "HP Remotecare";
            default:
                return "HP " + canonicalName;
        }
    }

    private static String mapOrderTypeToCanonicalService(String normalized) {
        if (normalized.contains("premium plus") || normalized.contains("premium+")
                || normalized.contains("premium") || normalized.contains("active care")) {
            return "ActiveCare";
        }
        if (normalized.contains("out of band") || normalized.contains("remediation")) {
            return "RemoteCare";
        }
        return null;
    }

    public static String calculateExpectedStatus(Date startDate, Date endDate) {
        Date now = new Date();
        if (startDate.before(now) && endDate.after(now)) {
            return "Active";
        } else if (startDate.after(now) && endDate.after(now)) {
            return "Inactive";
        } else if (startDate.before(now) && endDate.before(now)) {
            return "Expired";
        }
        return "Unknown";
    }

    /**
     * CRITICAL: The UI displays UTC timestamps converted to local timezone.
     * Example: DB stores "2025-01-21 00:00:00" UTC, UI shows "Jan 20, 2025, 5:00 PM" in MST (UTC-7)
     * This method parses the displayed time and returns the Date object representing that moment.
     * The caller should format using UTC timezone to get the correct date: "21-Jan-25"
     * 
     * Always uses Locale.ENGLISH for consistent parsing across environments.
     */
    private static Date parseUIDateFormat(String dateString) throws Exception {
        if (dateString == null || dateString.isBlank()) {
            throw new Exception("Date string is null or blank");
        }
        
        // Try different date formats commonly used in UI
        String[] patterns = {
            "MMM d, yyyy, h:mm a",   // Jan 16, 2025, 4:00 PM (US format)
            "MMM dd, yyyy, h:mm a",  // Jan 16, 2025, 4:00 PM (padded day)
            "d MMM yyyy, h:mm a",    // 16 Jan 2025, 4:00 PM (EU format)
            "dd MMM yyyy, h:mm a"    // 16 Jan 2025, 4:00 PM (padded day)
        };
        
        Exception lastException = null;
        for (String pattern : patterns) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
                format.setLenient(false); // Strict parsing
                // Parse in local timezone (as displayed in UI)
                return format.parse(dateString);
            } catch (Exception e) {
                lastException = e;
            }
        }
        
        // If all formats fail, throw detailed exception
        throw new Exception(String.format("Unparseable date: \"%s\". Tried formats: %s. Last error: %s", 
            dateString, String.join(", ", patterns), lastException.getMessage()));
    }

    private static String extractDatePart(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.length() < 10) {
            return dateTimeString;
        }
        return dateTimeString.substring(0, 10);
    }

    private static final Map<String, String> PLAN_DISPLAY_TO_PROPERTY_KEY = Map.of(
            "HP Remotecare", "PlanId_RemoteCare",
            "Hp Care Pack 2", "PlanId_ActiveCare"
    );

    private static String getExpectedPlanId(String planDisplayName) {
        if (planDisplayName == null) {
            return null;
        }
        String propertyKey = PLAN_DISPLAY_TO_PROPERTY_KEY.get(planDisplayName);
        if (propertyKey == null) {
            LOGGER.warn("No planId property key mapped for {}", planDisplayName);
            return null;
        }
        return getEnvironmentSpecificData(System.getProperty("environment"), propertyKey);
    }

    private static String getToggleValue() {
        try {
            return getEnvironmentSpecificData(System.getProperty("environment"), "optional-pc-refactor");
        } catch (Exception e) {
            LOGGER.warn("Unable to fetch toggle value, using default: PCE_CAMS_DATA");
            return "PCE_CAMS_DATA";
        }
    }

    private static String getTenantId() {
        return getEnvironmentSpecificData(System.getProperty("environment"), "ACOnlyTenantId_US");
    }

    private static String getTenantName() {
        return getEnvironmentSpecificData(System.getProperty("environment"), "ACOnlyTenantName_US");
    }

    /**
     * Validates device report response for the given providers and returns error messages.
     * Uses the provider validator registry for extensibility.
     *
     * @param listOfProvidersToVerify List of provider names to validate
     * @param serialNumber Device serial number
     * @param uiDeviceData UI device data
     * @param deviceDetails Map of device details
     * @return List of error messages (empty if all validations pass)
     */
    public static List<String> validateAgainstDeviceReportResponse(
            List<String> listOfProvidersToVerify,
            String serialNumber,
            UIDeviceData uiDeviceData,
            Map<String, String> deviceDetails) throws Exception {
        return validateAgainstDeviceReportResponse(listOfProvidersToVerify, serialNumber, uiDeviceData, deviceDetails, false);
    }

    /**
     * Validates device report response for the given providers and returns error messages.
     * Uses the provider validator registry for extensibility.
     *
     * @param listOfProvidersToVerify List of provider names to validate
     * @param serialNumber Device serial number
     * @param uiDeviceData UI device data
     * @param deviceDetails Map of device details
     * @param filterByDeviceId If true, filters device records by UI device ID (for CPIN enrollment scenarios)
     * @return List of error messages (empty if all validations pass)
     */
    public static List<String> validateAgainstDeviceReportResponse(
            List<String> listOfProvidersToVerify,
            String serialNumber,
            UIDeviceData uiDeviceData,
            Map<String, String> deviceDetails,
            boolean filterByDeviceId) throws Exception {
        LOGGER.info("Starting validation against device report response for serialNumber: {}", serialNumber);
        DeviceReportLambdaTables deviceReport = DBUtil.fetchDeviceReportLambda(serialNumber, listOfProvidersToVerify);
        List<String> errors = new ArrayList<>();
        
        if (filterByDeviceId) {
            // CPIN enrollment scenario: Filter device records by UI device ID to use only the correct post-CPIN record
            DeviceReportLambdaTables.ProviderWrapper correctProviderWrapper = null;
            LOGGER.info("Filtering device records by UI Device ID: {} (CPIN enrollment mode)", uiDeviceData.deviceId);
            
            for (Map.Entry<String, DeviceReportLambdaTables.ProviderWrapper> entry : deviceReport.getProviders().entrySet()) {
                DeviceReportLambdaTables.ProviderWrapper wrapper = entry.getValue();
                if (wrapper != null && wrapper.getProviders() != null && wrapper.getProviders().getDynamoDbProvider() != null) {
                    DeviceReportLambdaTables.DynamoDbProvider dynamoProvider = wrapper.getProviders().getDynamoDbProvider();
                    if (dynamoProvider.getDeviceHashDetails() != null) {
                        String deviceId = dynamoProvider.getDeviceHashDetails().getDeviceId();
                        if (uiDeviceData.deviceId.equals(deviceId)) {
                            correctProviderWrapper = wrapper;
                            LOGGER.info("Found matching device record with Device ID: {}", deviceId);
                            break;
                        } else {
                            LOGGER.info("Skipping device record with non-matching Device ID: {}", deviceId);
                        }
                    }
                }
            }
            
            if (correctProviderWrapper == null) {
                errors.add("No device record found matching UI Device ID: " + uiDeviceData.deviceId);
                LOGGER.error("No device record found matching UI Device ID: {}", uiDeviceData.deviceId);
                return errors;
            }
            
            // Validate only the correct provider wrapper that matches UI device ID
            DeviceReportLambdaTables.Providers correctProviders = correctProviderWrapper.getProviders();
            validateProvidersData(listOfProvidersToVerify, correctProviders, uiDeviceData, deviceDetails, serialNumber, errors);
        } else {
            // Standard validation: Use first available provider wrapper (existing behavior)
            LOGGER.info("Using standard validation mode (no device ID filtering)");
            DeviceReportLambdaTables.ProviderWrapper wrapper = deviceReport.getProviders().values().iterator().next();
            if (wrapper == null || wrapper.getProviders() == null) {
                errors.add("Provider data is null");
                return errors;
            }
            
            DeviceReportLambdaTables.Providers providers = wrapper.getProviders();
            validateProvidersData(listOfProvidersToVerify, providers, uiDeviceData, deviceDetails, serialNumber, errors);
        }
        LOGGER.info("Validation against device report response completed for serialNumber: {}", serialNumber);
        return errors;
    }

    /**
     * Helper method to validate providers data using the validator registry
     */
    private static void validateProvidersData(
            List<String> listOfProvidersToVerify,
            DeviceReportLambdaTables.Providers providers,
            UIDeviceData uiDeviceData,
            Map<String, String> deviceDetails,
            String serialNumber,
            List<String> errors) {
        
        for (String providerName : listOfProvidersToVerify) {
            boolean found = false;
            LOGGER.info("Validating provider: {}", providerName);
            
            ProviderValidatorService validator = VALIDATOR_REGISTRY.get(providerName);
            if (validator != null) {
                // Check if the provider exists in the Providers object
                boolean providerExists = false;
                switch (providerName) {
                    case "AssetMongoProvider":
                        providerExists = providers.getAssetMongoProvider() != null;
                        break;
                    case "AcOrderSubscriptionDataProvider":
                        providerExists = providers.getAcOrderSubscriptionDataProvider() != null;
                        break;
                    case "DynamoDbProvider":
                        providerExists = providers.getDynamoDbProvider() != null;
                        break;
                    default:
                        providerExists = false;
                }
                if (providerExists) {
                    validator.validateProviderData(providers, uiDeviceData, deviceDetails, serialNumber, errors);
                    found = true;
                    LOGGER.info("{} validation completed", providerName);
                }
            } else {
                LOGGER.warn("No validator registered for provider: {}", providerName);
            }
            if (!found) {
                LOGGER.error("Provider {} not found or validation failed", providerName);
                errors.add("Provider " + providerName + " not found or validation failed");
            }
        }
    }


}
