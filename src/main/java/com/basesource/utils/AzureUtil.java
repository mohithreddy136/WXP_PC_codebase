package com.basesource.utils;

import com.basesource.action.CommonMethod;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class AzureUtil extends CommonMethod {

//    private AzureUtil() {}
    // Below Hardcoded configuration should be used for triggerAzurePipelineToProcessOrder only
    
    public AzureUtil instance;
    public static final Logger LOGGER = LogManager.getLogger(AzureUtil.class);
	public AzureUtil getInstance() throws IOException {
		if (instance == null) {
			synchronized (AzureUtil.class) {
				if (instance == null) {
					instance = new AzureUtil();
				}
			}
		}
		return instance;
	}

    public static int getPipeLineID() {
        String rawEnvironmentName = System.getProperty("environment").toUpperCase();
        switch (rawEnvironmentName) {
            case "US-STABLE-WEP":
            case "EU-STABLE-WEP":
                return 32487;
            case "US-STAGE-WEP":
            case "EU-STAGE-WEP":
                return 32498;
            default:
                throw new IllegalArgumentException("Unsupported environment: " + rawEnvironmentName);

        }
    }

    private static final String BASE_URL = "https://dev.azure.com";
    private static final String ORGANIZATION = "hpcodeway";
    private static final String PROJECT = "hpac";
    private static final int PIPELINE_ID = getPipeLineID();
    private static final String API_VERSION = "7.1-preview.1";

    private static final int MAX_ATTEMPTS = 100;

    private static final int POLL_INTERVAL_MS = 10000;

    private static String AUTH_HEADER = "";

    public static void triggerAzurePipelineToProcessOrder(String hostURL, String processOrderEndPoint, String envName) {
        try {
            String apiEndpoint = hostURL + processOrderEndPoint;
            String triggerUrl = buildTriggerUrl();
            String requestBody = buildRequestBody(apiEndpoint, envName);

            Response triggerResponse = RestAssured.given()
                    .header("Authorization", getAuthHeader())
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .when()
                    .post(triggerUrl)
                    .then()
                    .statusCode(200)
                    .log().ifValidationFails()
                    .extract().response();

            int runId = triggerResponse.jsonPath().getInt("id");
            LOGGER.info("Pipeline triggered to process order. Run ID: {}", runId);

            pollPipelineStatus(runId);

        } catch (Exception e) {
            LOGGER.error("Exception occurred while triggering Azure pipeline: ", e);
        }
    }

    public static void triggerAzurePipelineToMakeSNREntry(String hostURL, String processOrderEndPoint, String envName, String serialNo) {

        try {
            String apiEndpoint = "--request POST '"+ hostURL + processOrderEndPoint + "'"
                    + " -H 'content-type: application/json' "
                    + "-d '{"
                    + "\\\"serialNo\\\":\\\"" + serialNo + "\\\","
                    + "\\\"product_no\\\":\\\"1134466#BQQQ\\\","
                    + "\\\"bios_uuid\\\":\\\"66BA178E-7D82-4BB6-9SSS-9335956F8521\\\","
                    + "\\\"deviceid_loc\\\":\\\"\\\","
                    + "\\\"deviceid_non_loc\\\":\\\"\\\","
                    + "\\\"dual_snr_status\\\":\\\"\\\""
                    + "}'";
            
            String triggerUrl = buildTriggerUrl();
            String requestBody = buildRequestBody(apiEndpoint, envName);
            Response triggerResponse = RestAssured.given()
                    .header("Authorization", getAuthHeader())
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .when()
                    .post(triggerUrl)
                    .then()
                    .statusCode(200)
                    .log().ifValidationFails()
                    .extract().response();

            int runId = triggerResponse.jsonPath().getInt("id");
            LOGGER.info("Pipeline triggered to make SNR entry for {}. Run ID: {}", serialNo, runId);

            pollPipelineStatus(runId);

        } catch (Exception e) {
            LOGGER.error("Exception occurred while triggering Azure pipeline: ", e);
        }
    }
//
//    public static void triggerAzurePipelineToDeleteSNREntry(String serialNo) {
//        try {
//            String apiEndpoint = "--request DELETE '" + getHost() + CRON_JOB_END_POINT_TO_DELETE_SNR_ENTRY + serialNo + "'";
//            String triggerUrl = buildTriggerUrl();
//            String requestBody = buildRequestBody(apiEndpoint, getEnvironmentName());
//
//            Response triggerResponse = RestAssured.given()
//                    .header("Authorization", getAuthHeader())
//                    .header("Content-Type", "application/json")
//                    .body(requestBody)
//                    .when()
//                    .post(triggerUrl)
//                    .then()
//                    .statusCode(200)
//                    .log().ifValidationFails()
//                    .extract().response();
//
//            int runId = triggerResponse.jsonPath().getInt("id");
//            LOGGER.info("Pipeline triggered to DELETE SNR entry for {}. Run ID: {}", serialNo, runId);
//
//            pollPipelineStatus(runId);
//
//        } catch (Exception e) {
//            LOGGER.error("Exception occurred while triggering Azure pipeline for DELETE: ", e);
//        }
//    }
//
//    public static void deleteDevice() {
//        String tenantId = getTenantId();
//        String deviceId = TestContextHolder.getDataHolder().getDeviceId();
//
//        try {
//            String apiEndpoint = "curl -i --request POST http://internal.eustaging.hpdaas.com/services/drs/internal/1.0/tenants/" + tenantId + "/devices/bulk-delete " +
//                    "--header 'X-HPTM-Transaction-ID: delete-device001' " +
//                    "--header 'Content-Type: application/json' " +
//                    "--data-raw '{\\\"resources\\\":[\\\"" + deviceId + "\\\"]}'";
//
//            String triggerUrl = buildTriggerUrl();
//            String requestBody = buildRequestBody(apiEndpoint, getEnvironmentName());
//
//            Response triggerResponse = RestAssured.given()
//                    .header("Authorization", getAuthHeader())
//                    .header("Content-Type", "application/json")
//                    .body(requestBody)
//                    .when()
//                    .post(triggerUrl)
//                    .then()
//                    .statusCode(200)
//                    .log().ifValidationFails()
//                    .extract().response();
//
//            int runId = triggerResponse.jsonPath().getInt("id");
//            LOGGER.info("Pipeline triggered to delete device {}. Run ID: {}", getSerialNumber(), runId);
//
//            pollPipelineStatus(runId);
//
//        } catch (Exception e) {
//            LOGGER.error("Exception occurred while triggering Azure pipeline to delete device: ", e);
//        }
//    }
//
    public static String triggerAzurePipelineToFetchDeviceReport(String hostURL, String processOrderEndPoint, String envName, String serialNo, List<String> providersObj) {
        String deviceReportJson;
        int maxAttemptsForDeviceReport = 30;
        final int maxLogAttempts = 15;
        final int logPollIntervalMs = 5000;

        for (int attempt = 1; attempt <= maxAttemptsForDeviceReport; attempt++) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> payload = new HashMap<>();
                payload.put("serialNumber", serialNo);
                payload.put("providersObj", providersObj == null ? Collections.emptyList() : providersObj);
                String jsonBody = mapper.writeValueAsString(payload);

                String apiEndpoint = "-X POST " + hostURL + processOrderEndPoint
                        + " -H 'Content-Type: application/json' "
                        + "-d '" + jsonBody + "'";

                String triggerUrl = buildTriggerUrl();
                String safeApiEndpoint = apiEndpoint.replace("\"", "\\\"");
                String requestBody = buildRequestBody(safeApiEndpoint, envName);

                Response triggerResponse = RestAssured.given()
                        .header("Authorization", getAuthHeader())
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .when()
                        .post(triggerUrl)
                        .then()
                        .statusCode(200)
                        .log().ifValidationFails()
                        .extract().response();

                int runId = triggerResponse.jsonPath().getInt("id");
                LOGGER.info("Pipeline triggered to fetch device report for {}. Run ID: {}", serialNo, runId);
                pollPipelineStatus(runId);

                String logUrl = String.format(
                        "https://dev.azure.com/%s/%s/_apis/build/builds/%d/logs/6",
                        ORGANIZATION, PROJECT, runId
                );

                // Retry fetching logs for the same runId
                for (int logAttempt = 1; logAttempt <= maxLogAttempts; logAttempt++) {
                    Response logResponse = RestAssured.given()
                            .header("Authorization", getAuthHeader())
                            .when()
                            .get(logUrl)
                            .then()
                            .statusCode(200)
                            .extract().response();

                    String logContent = logResponse.getBody().asString();
                    deviceReportJson = extractDeviceReportJson(logContent);

                    if (deviceReportJson != null) {
                        LOGGER.info("Extracted device report JSON on attempt {} (log attempt {}): {}", attempt, logAttempt, deviceReportJson);
                        return deviceReportJson;
                    } else {
                        LOGGER.warn("Log attempt {}: Device report JSON not found in log. Retrying...", logAttempt);
                        if (logAttempt < maxLogAttempts) {
                            Thread.sleep(logPollIntervalMs);
                        }
                    }
                }
                // If not found after log retries, continue to next pipeline trigger attempt
                LOGGER.warn("Attempt {}: Device report JSON not found after {} log retries. Retrying pipeline trigger...", attempt, maxLogAttempts);
                if (attempt < maxAttemptsForDeviceReport) {
                    Thread.sleep(5000);
                }
            } catch (Exception e) {
                LOGGER.error("Exception during attempt {} to fetch device report: ", attempt, e);
                if (attempt < maxAttemptsForDeviceReport) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ie) {
                        LOGGER.error("Sleep interrupted: ", ie);
                    }
                }
            }
        }
        throw new RuntimeException("No data returned for the device report after "
                + maxAttemptsForDeviceReport + " attempts.");
    }

    /**
     Extracts and returns a valid JSON fragment from the provided log content.

     This method handles cases where the JSON object is split across multiple lines
     and may include invalid or non-printable characters.

     It works by iterating over each character of the log content. When the first '{'
     is encountered, it begins accumulating characters into a StringBuilder. The method
     tracks the number of opening and closing braces to ensure that a balanced JSON object
     is assembled.

     Once a balanced JSON fragment is detected (i.e. when the brace count returns to zero),
     the candidate JSON is cleaned by removing non-printable characters. It then tries to
     parse the cleaned candidate using Jackson's ObjectMapper. If the parsed JSON contains the
     required keys ("htmlPath", "jsonPath", and "message"), the method returns the candidate JSON.

     If no valid JSON fragment is found in the log, the method logs a warning and returns null.
     */
    private static String extractDeviceReportJson(String logContent) {
        String result = null;
        if (logContent != null && !logContent.isEmpty()) {
            StringBuilder jsonBuilder = new StringBuilder();
            boolean jsonStarted = false;
            int braceCount = 0;
            for (char ch : logContent.toCharArray()) {
                if (ch == '{') {
                    if (!jsonStarted) {
                        jsonStarted = true;
                    }
                    braceCount++;
                }
                if (jsonStarted) {
                    jsonBuilder.append(ch);
                }
                if (ch == '}') {
                    braceCount--;
                    if (jsonStarted && braceCount == 0) {
                        String candidate = jsonBuilder.toString().trim();
                        candidate = candidate.replaceAll("[^\\x20-\\x7E]", "");
                        try {
                            JsonNode jsonNode = new ObjectMapper().readTree(candidate);
                            if (jsonNode.has("htmlPath") && jsonNode.has("jsonPath") && jsonNode.has("message")) {
                                result = candidate;
                                break;
                            }
                        } catch (Exception ignored) {

                        }
                        jsonBuilder.setLength(0);
                        jsonStarted = false;
                    }
                }
            }
        }
        return result;
    }

    private static void pollPipelineStatus(int runId) {
        String pollUrl = buildPollUrl(runId);

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                Thread.sleep(POLL_INTERVAL_MS);

                Response pollResponse = RestAssured.given()
                        .header("Authorization", getAuthHeader())
                        .get(pollUrl)
                        .then()
                        .statusCode(200)
                        .extract().response();

                String state = pollResponse.jsonPath().getString("state");
                String result = pollResponse.jsonPath().getString("result");

                LOGGER.info("Attempt {} - State: {}, Result: {}", attempt, state, result);

                if ("completed".equalsIgnoreCase(state)) {
                    if ("succeeded".equalsIgnoreCase(result)) {
                        LOGGER.info("Pipeline completed successfully");
                    } else {
                        LOGGER.warn("Pipeline completed with result: {}", result);
                    }
                    return;
                }
            } catch (InterruptedException e) {
                LOGGER.error("Polling interrupted", e);
                return;
            } catch (Exception e) {
                LOGGER.error("Error during polling attempt {}", attempt, e);
            }
        }
        LOGGER.warn("Pipeline did not complete within the expected time");
    }

    private static String buildTriggerUrl() {
        return String.format("%s/%s/%s/_apis/pipelines/%d/runs?api-version=%s",
                BASE_URL, ORGANIZATION, PROJECT, PIPELINE_ID, API_VERSION);
    }

    private static String buildPollUrl(int runId) {
        return String.format("%s/%s/%s/_apis/pipelines/%d/runs/%d?api-version=%s",
                BASE_URL, ORGANIZATION, PROJECT, PIPELINE_ID, runId, API_VERSION);
    }

    private static String buildRequestBody(String apiEndpoint, String environment) {
        return "{\n" +
                "  \"resources\": {\n" +
                "    \"repositories\": {\n" +
                "      \"self\": {\n" +
                "        \"refName\": \"refs/heads/master\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"templateParameters\": {\n" +
                "    \"api_endpoint\": \"" + apiEndpoint + "\",\n" +
                "    \"environment\": \"" + environment + "\"\n" +
                "  }\n" +
                "}";
    }

    private static String getAuthHeader() throws Exception {
        if (AUTH_HEADER.isEmpty()) {
            String pat = getPAT("AzurePAT");
            String auth = ":" + pat;
            String encoded = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            AUTH_HEADER = "Basic " + encoded;
        }
        return AUTH_HEADER;
    }
}