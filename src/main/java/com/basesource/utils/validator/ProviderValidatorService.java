package com.basesource.utils.validator;

import com.basesource.utils.DeviceReportLambdaTables;
import com.basesource.utils.ActiveCareDBValidation.UIDeviceData;

import java.util.List;
import java.util.Map;

/**
 * Interface for provider data validation.
 */
public interface ProviderValidatorService {
    /**
     * Validates provider data and appends error messages.
     *
     * @param wrapper Providers wrapper
     * @param uiDeviceData UI device data
     * @param deviceDetails Device details map
     * @param serialNumber Device serial number
     * @param errors Error message list
     */
    void validateProviderData(DeviceReportLambdaTables.Providers wrapper,
                             UIDeviceData uiDeviceData,
                             Map<String, String> deviceDetails,
                             String serialNumber,
                             List<String> errors);
}

