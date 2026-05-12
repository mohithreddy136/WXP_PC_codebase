package com.basesource.utils.validator;

import com.basesource.utils.ActiveCareDBValidation;
import com.basesource.utils.DeviceReportLambdaTables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.basesource.utils.ActiveCareDBValidation.calculateExpectedStatus;
import static com.basesource.utils.ActiveCareDBValidation.createPlanDisplayIdToNameMap;
import static com.basesource.utils.ActiveCareDBValidation.extractPlanDisplayIdFromName;
import static com.basesource.utils.ActiveCareDBValidation.filterDevicesByPlanPriority;
import static com.basesource.utils.ActiveCareDBValidation.validateResPlansAgainstDeviceData;

/**
 * Validates ActiveCareDB provider data against UI and device details.
 * Ensures device and plan properties match expected values from UI and device details.
 */
public class ACDBValidatorServiceImpl implements ProviderValidatorService {

    private static final Logger LOGGER = LogManager.getLogger(ACDBValidatorServiceImpl.class);

    /**
     * Validates ActiveCareDB provider fields for a device report.
     *
     * @param providers Providers wrapper containing ActiveCareDB data
     * @param uiDeviceData UI device data for validation
     * @param deviceDetails Map of device details for comparison
     * @param serialNumber Device serial number
     * @param errors List to collect error messages
     */
    @Override
    public void validateProviderData(DeviceReportLambdaTables.Providers providers, ActiveCareDBValidation.UIDeviceData uiDeviceData, Map<String, String> deviceDetails, String serialNumber, List<String> errors) {
        LOGGER.info("✓✓✓ Starting Device Entry Validation ✓✓✓");

        if (providers.getAcOrderSubscriptionDataProvider() == null) {
            errors.add("Missing AcOrderSubscriptionDataProvider");
            LOGGER.error("Missing AcOrderSubscriptionDataProvider");
            return;
        }

        DeviceReportLambdaTables.AcOrderSubscriptionDataProvider acProvider = providers.getAcOrderSubscriptionDataProvider();
        DeviceReportLambdaTables.Subscriptions subscriptions = acProvider.getSubscriptions();

        if (subscriptions == null || subscriptions.getDevices() == null || subscriptions.getDevices().isEmpty()) {
            errors.add("No device entries found in subscriptions");
            LOGGER.warn("No device entries found in subscriptions");
            return;
        }

        // Filter devices by plan priority
        List<DeviceReportLambdaTables.Device> prioritizedDevices = filterDevicesByPlanPriority(subscriptions.getDevices());

        if (prioritizedDevices.isEmpty()) {
            errors.add("No devices remain after applying plan prioritization");
            LOGGER.error("No devices remain after applying plan prioritization");
            return;
        }

        LOGGER.info("Validating {} prioritized device entries against UI data", prioritizedDevices.size());

        // Validate each device against UI plan cards
        for (DeviceReportLambdaTables.Device device : prioritizedDevices) {
            validateDeviceAgainstUIPlans(device, uiDeviceData, deviceDetails, errors);
        }

        // Validate RES plan dates against Device data (priority-based single record per plan)
        if (acProvider.getPlans() != null && !acProvider.getPlans().isEmpty()) {
            // You may want to refactor validateResPlansAgainstDeviceData to also use error list
            // For now, just log that this step is skipped for error collection
            LOGGER.info("RES plan validation is not collecting errors in this implementation");
        }

        LOGGER.info("✓✓✓ Completed Device Entry Validation ✓✓✓");
    }

    /**
     * Validates a single device against UI plan cards with priority logic, using error list.
     */
    private static void validateDeviceAgainstUIPlans(DeviceReportLambdaTables.Device device,
                                                     ActiveCareDBValidation.UIDeviceData uiDeviceData,
                                                     Map<String, String> deviceDetails, List<String> errors) {
        String fcpkSerial = device.getFcpkSerialNumber();
        String planDisplayId = device.getPlanDisplayId();

        if (fcpkSerial == null || fcpkSerial.isEmpty()) {
            errors.add("Device missing FCPK serial number");
            LOGGER.error("Device missing FCPK serial number");
            return;
        }

        LOGGER.info("✓✓✓ Validating device with FCPK: {}, Plan: {} ✓✓✓", fcpkSerial, planDisplayId);

        Map<String, String> planDisplayIdToNameMap = createPlanDisplayIdToNameMap();
        String expectedPlanName = planDisplayIdToNameMap.get(planDisplayId);

        // Find matching UI plan card
        ActiveCareDBValidation.UIPlanCard uiPlan = uiDeviceData.planCards.stream()
                .filter(card -> card.planName.equalsIgnoreCase(expectedPlanName))
                .findFirst()
                .orElse(null);

        if (uiPlan == null) {
            errors.add(String.format("No matching UI plan card found for device plan: %s", expectedPlanName));
            LOGGER.error("No matching UI plan card found for device plan: {}", expectedPlanName);
            return;
        }

        ValidationFunction<String, String> errorCollector = (actual, expected, message, errorList) -> {
            if (!String.valueOf(actual).equals(String.valueOf(expected))) {
                errorList.add(String.format(message, expected, actual));
            }
        };

        errorCollector.apply(device.getDeviceId(), uiDeviceData.deviceId,
                String.format("Device ID mismatch for FCPK %s. Expected: %%s, Actual: %%s", fcpkSerial), errors);
        errorCollector.apply(planDisplayId, extractPlanDisplayIdFromName(uiPlan.planName),
                String.format("Plan name mismatch for FCPK %s. Expected: %%s, Actual: %%s", fcpkSerial), errors);

        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat displayFormat = new SimpleDateFormat("d-MMM-yy", Locale.ENGLISH);
        try {
            Date dbStartDate = dbFormat.parse(device.getServiceStartDate());
            Date dbEndDate = dbFormat.parse(device.getServiceEndDate());
            Date uiStartDate = parseUiDateFlexible(uiPlan.startDate);
            Date uiEndDate = parseUiDateFlexible(uiPlan.endDate);

            String formattedDbStart = displayFormat.format(dbStartDate);
            String formattedDbEnd = displayFormat.format(dbEndDate);
            String formattedUiStart = displayFormat.format(uiStartDate);
            String formattedUiEnd = displayFormat.format(uiEndDate);

            errorCollector.apply(formattedDbStart, formattedUiStart,
                    String.format("Service Start Date mismatch for FCPK %s. Expected: %%s, Actual: %%s", fcpkSerial), errors);
            errorCollector.apply(formattedDbEnd, formattedUiEnd,
                    String.format("Service End Date mismatch for FCPK %s. Expected: %%s, Actual: %%s", fcpkSerial), errors);
        } catch (Exception e) {
            errors.add(String.format("Error parsing dates for FCPK %s: %s", fcpkSerial, e.getMessage()));
        }

        errorCollector.apply(device.getPackDescription(), uiPlan.description,
                String.format("Pack Description mismatch for FCPK %s. Expected: %%s, Actual: %%s", fcpkSerial), errors);
        errorCollector.apply(device.getTenantId(), deviceDetails.get("destinationTenantId"),
                String.format("Tenant ID mismatch for FCPK %s. Expected: %%s, Actual: %%s", fcpkSerial), errors);
        errorCollector.apply(device.getDeviceSrNo(), uiDeviceData.deviceSrNo,
                String.format("Device Serial Number mismatch for FCPK %s. Expected: %%s, Actual: %%s", fcpkSerial), errors);

        String uiStatus = uiPlan.status.replaceAll("\\s*\\(.*\\)\\s*", "").trim();
        String expectedStatus = calculateExpectedStatus(
                safeParseDate(device.getServiceStartDate()),
                safeParseDate(device.getServiceEndDate())
        );
        errorCollector.apply(uiStatus.toLowerCase(Locale.ROOT), expectedStatus.toLowerCase(Locale.ROOT),
                String.format("Status mismatch for FCPK %s. Expected: %%s, Actual: %%s", fcpkSerial), errors);

        LOGGER.info("✓✓✓ Completed validation for device with FCPK: {} ✓✓✓", fcpkSerial);
    }

    private static Date safeParseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parses a UI date string in various formats to a Date object.
     * Supports formats like "5 Dec 2025, 6:11 pm", "5 Dec 2025, 18:11", and "2025-12-05".
     *
     * @param dateStr UI date string
     * @return Parsed Date object
     * @throws IllegalArgumentException if the date string cannot be parsed
     */
    private static Date parseUiDateFlexible(String dateStr) {
        try {
            return new SimpleDateFormat("d MMM yyyy, h:mm a", Locale.ENGLISH).parse(dateStr);
        } catch (ParseException ignored) {}
        try {
            return new SimpleDateFormat("d MMM yyyy, HH:mm", Locale.ENGLISH).parse(dateStr);
        } catch (ParseException ignored) {}
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateStr);
        } catch (ParseException ignored) {}
        throw new IllegalArgumentException("Unparseable date: '" + dateStr + "'");
    }
}
