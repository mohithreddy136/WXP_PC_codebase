package com.basesource.utils.validator;

import com.basesource.utils.DeviceReportLambdaTables;
import com.basesource.utils.DeviceReportLambdaTables.AssetMongoProvider;
import com.basesource.utils.ActiveCareDBValidation.UIDeviceData;

import java.util.List;
import java.util.Map;

/**
 * Validates AssetMongoProvider data and collects error messages.
 */
public class AssetMongoValidatorServiceImpl implements ProviderValidatorService {

    @Override
    public void validateProviderData(DeviceReportLambdaTables.Providers wrapper,
                                    UIDeviceData uiDeviceData,
                                    Map<String, String> deviceDetails,
                                    String serialNumber,
                                    List<String> errors) {
        AssetMongoProvider assetMongoProvider = wrapper.getAssetMongoProvider();

        ValidationFunction<String, String> errorCollector = (actual, expected, message, errorList) -> {
            if (!String.valueOf(actual).equals(String.valueOf(expected))) {
                errorList.add(String.format(message, expected, actual));
            }
        };

        errorCollector.apply(assetMongoProvider.getUid(), uiDeviceData.deviceId, "Device ID (uid) mismatch! Expected: %s, Actual: %s", errors);
        errorCollector.apply(assetMongoProvider.getSerialNumber(), uiDeviceData.deviceSrNo, "Device Serial Number mismatch! Expected: %s, Actual: %s", errors);

        String expectedStatus = extractStatus(uiDeviceData.deviceStatus);
        String actualStatus = assetMongoProvider.getStatus() != null && assetMongoProvider.getStatus().getEn() != null
                ? assetMongoProvider.getStatus().getEn().getValue()
                : null;
        errorCollector.apply(actualStatus, expectedStatus, "Device Status mismatch! Expected: %s, Actual: %s", errors);

        errorCollector.apply(assetMongoProvider.getCompanyName(), deviceDetails.get("endCustomerName"), "Asset End Customer Name mismatch! Expected: %s, Actual: %s", errors);
        errorCollector.apply(assetMongoProvider.getTenantId(), deviceDetails.get("destinationTenantId"), "Asset Tenant ID mismatch! Expected: %s, Actual: %s", errors);
    }

    private String extractStatus(String uiStatus) {
        if (uiStatus == null) return null;
        int idx = uiStatus.indexOf(' ');
        if (idx > 0) {
            return uiStatus.substring(0, idx).trim();
        }
        return uiStatus.trim();
    }
}
