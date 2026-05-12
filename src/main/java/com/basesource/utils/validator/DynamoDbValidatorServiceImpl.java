package com.basesource.utils.validator;


import com.basesource.utils.ActiveCareDBValidation.UIDeviceData;
import com.basesource.utils.DeviceReportLambdaTables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.List;

public class DynamoDbValidatorServiceImpl implements ProviderValidatorService {
    private static final Logger LOGGER = LogManager.getLogger(DynamoDbValidatorServiceImpl.class);

    /**
     * Validates DynamoDbProvider data against UI and device details.
     * Ensures device and tenant hash details are present and match expected values.
     *
     * @param wrapper       Providers wrapper containing DynamoDbProvider
     * @param uiDeviceData  UI device data for validation
     * @param deviceDetails Map of device details for comparison
     * @param serialNumber  Device serial number
     * @param errors        List to collect error messages
     */
    @Override
    public void validateProviderData(
            DeviceReportLambdaTables.Providers wrapper,
            UIDeviceData uiDeviceData,
            Map<String, String> deviceDetails,
            String serialNumber,
            List<String> errors) {

        DeviceReportLambdaTables.DynamoDbProvider dynamoDbProvider = wrapper.getDynamoDbProvider();
        LOGGER.info("Validating DynamoDbProvider for Device ID: {}, Serial Number: {}", uiDeviceData.deviceId, serialNumber);

        if (dynamoDbProvider == null) {
            LOGGER.error("DynamoDbProvider is missing in device report for Serial Number: {}", serialNumber);
            errors.add("DynamoDbProvider is missing in device report");
            return;
        }

        validateDeviceHashDetails(dynamoDbProvider, uiDeviceData, deviceDetails, errors);
        validateTenantHashDetails(dynamoDbProvider, deviceDetails, errors);
    }

    private void validateDeviceHashDetails(
            DeviceReportLambdaTables.DynamoDbProvider dynamoDbProvider,
            UIDeviceData uiDeviceData,
            Map<String, String> deviceDetails,
            List<String> errors) {

        DeviceReportLambdaTables.DynamoDbProvider.DeviceHashDetails deviceHash = dynamoDbProvider.getDeviceHashDetails();
        ValidationFunction<String, String> errorCollector = (actual, expected, message, errorList) -> {
            if (!String.valueOf(actual).equals(String.valueOf(expected))) {
                errorList.add(String.format(message, expected, actual));
            }
        };
        if (deviceHash == null) {
            LOGGER.warn("DeviceHashDetails missing in DynamoDbProvider");
            errors.add("DeviceHashDetails missing in DynamoDbProvider");
            return;
        }
        LOGGER.info("Validating DeviceHashDetails for Device ID: {}", uiDeviceData.deviceId);
        errorCollector.apply(deviceHash.getDeviceId(), uiDeviceData.deviceId, "DeviceId mismatch in DeviceHashDetails! Expected: %s, Actual: %s", errors);
        errorCollector.apply(deviceHash.getTenantId(), deviceDetails.get("destinationTenantId"), "TenantId mismatch in DeviceHashDetails! Expected: %s, Actual: %s", errors);
    }

    private void validateTenantHashDetails(
            DeviceReportLambdaTables.DynamoDbProvider dynamoDbProvider,
            Map<String, String> deviceDetails,
            List<String> errors) {

        DeviceReportLambdaTables.DynamoDbProvider.TenantHashDetails tenantHash = dynamoDbProvider.getTenantHashDetails();
        ValidationFunction<String, String> errorCollector = (actual, expected, message, errorList) -> {
            if (!String.valueOf(actual).equals(String.valueOf(expected))) {
                errorList.add(String.format(message, expected, actual));
            }
        };
        if (tenantHash == null) {
            LOGGER.warn("TenantHashDetails missing in DynamoDbProvider");
            errors.add("TenantHashDetails missing in DynamoDbProvider");
            return;
        }
        LOGGER.info("Validating TenantHashDetails for Tenant ID: {}", deviceDetails.get("destinationTenantId"));
        errorCollector.apply(tenantHash.getTenantId(), deviceDetails.get("destinationTenantId"), "TenantId mismatch in TenantHashDetails! Expected: %s, Actual: %s", errors);
        if (tenantHash.getServiceName() == null || tenantHash.getServiceName().isEmpty()) {
            errors.add("ServiceName missing in TenantHashDetails");
        }
        if (tenantHash.getConfig() == null) {
            LOGGER.warn("Config missing in TenantHashDetails");
            errors.add("Config missing in TenantHashDetails");
            return;
        }
        if (tenantHash.getConfig().getRegion() == null || tenantHash.getConfig().getRegion().isEmpty()) {
            errors.add("Region missing in TenantHashDetails.Config");
        }
        if (tenantHash.getConfig().getRegionURL() == null || tenantHash.getConfig().getRegionURL().isEmpty()) {
            errors.add("RegionURL missing in TenantHashDetails.Config");
        }
    }
}
