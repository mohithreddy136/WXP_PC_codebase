package com.basesource.utils;

import com.basesource.action.CommonMethod;
import com.daasui.constants.ConstantURL;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public final class DBUtil extends CommonMethod {

    private DBUtil() {}
    private static final Logger LOGGER = LogManager.getLogger(DBUtil.class);

    public static DeviceReportLambdaTables fetchDeviceReportLambda(String serialNumber, List<String> providersObj) throws Exception {
        final int maxAttempts = 3;
        int attempt = 1;
        Throwable lastError = null;
        DeviceReportLambdaTables report = null;
        List<String> allFailures = new ArrayList<>();

        while (attempt <= maxAttempts && report == null) {
            try {
                LOGGER.info("Fetching device report (Attempt {}/{})", attempt, maxAttempts);

                String deviceReportJson = triggerFetchDeviceReportCRONJob(serialNumber, providersObj);
                ObjectMapper mapper = new ObjectMapper();
                report = mapper.readValue(deviceReportJson, DeviceReportLambdaTables.class);
                break;

            } catch (Throwable e) {
                lastError = e;
                String type = (e instanceof AssertionError) ? "AssertionError" : "Exception";
                allFailures.add("Attempt " + attempt + " failed with " + type + ": " + e.getMessage());
                LOGGER.warn("✗ Device report fetch failed on attempt {}/{}: {}", attempt, maxAttempts, e.getMessage());
                attempt++;
                // Loop continues - report is still null
            }
        }

        if (report == null) {
            StringBuilder sb = new StringBuilder("Failed to fetch device report after " + maxAttempts + " attempts.\n");
            for (String failure : allFailures) {
                sb.append(failure).append("\n");
            }
            throw new AssertionError(sb.toString(), lastError);
        }
        LOGGER.info("✓ Device report fetched successfully");
        return report;
    }

    /**
     * Trigger AZURE pipeline to Fetch Device Report
     *
     * @throws Exception
     */
    public static String triggerFetchDeviceReportCRONJob(String serialnumber, List<String> providersObj) throws Exception {
        String hostURL=getEnvironmentSpecificData(System.getProperty("environment"), "deviceReportLambdaEndPointUrl");
        String processOrderEndPoint= ConstantURL.CRONJOBENDPOINT_DEVICEREPORTLAMBDA;
        String envName=getEnvironmentSpecificData(System.getProperty("environment"), "hostEnviroment");
        LOGGER.error("Trigger Fetch Device Report CRON Job." );
        return AzureUtil.triggerAzurePipelineToFetchDeviceReport(hostURL, processOrderEndPoint, envName, serialnumber, providersObj);
    }
}