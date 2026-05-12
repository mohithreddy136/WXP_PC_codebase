package com.basetest.environments;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v124.network.Network;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class NetworkLogsCapture {
    private static WebDriver driver;
    private DevTools devTools;

    private final Map<String, Map<String, Object>> requestStore = new HashMap<>();
    private final List<Map<String, Object>> harEntries = new ArrayList<>();
    private final List<String> consoleLogs = new ArrayList<>();

    // Enable performance logging for network capture
    public void setupNetworkLogging(WebDriver driver) {
        this.driver = driver;

        // Primary approach: Use performance logs which work with any Chrome/Edge version
        // This approach doesn't require version-specific DevTools imports
        try {
            // Verify that performance logs are available
            driver.manage().logs().get(LogType.PERFORMANCE);
            System.out.println("Performance logging is enabled and ready for network capture");
        } catch (Exception e) {
            System.err.println("Performance logging not available: " + e.getMessage());
        }

        // Optional: Try DevTools if available (fallback approach)
        if (driver instanceof HasDevTools) {
            try {
                devTools = ((HasDevTools) driver).getDevTools();
                devTools.createSession();
                System.out.println("DevTools session created successfully");
            } catch (Exception e) {
                System.err.println("DevTools not available: " + e.getMessage());
                devTools = null;
            }
        }
    }

    // Capture network logs using performance logs (works with any Chrome/Edge version)
    public void captureNetworkLogs() {
        if (driver == null) return;

        try {
            List<LogEntry> logs = driver.manage().logs().get(LogType.PERFORMANCE).getAll();

            for (LogEntry log : logs) {
                try {
                    JSONObject logJson = new JSONObject(log.getMessage());
                    JSONObject message = logJson.getJSONObject("message");
                    String method = message.getString("method");

                    if ("Network.requestWillBeSent".equals(method)) {
                        handleNetworkRequest(message.getJSONObject("params"));
                    } else if ("Network.responseReceived".equals(method)) {
                        handleNetworkResponse(message.getJSONObject("params"));
                    } else if ("Runtime.consoleAPICalled".equals(method)) {
                        handleConsoleLog(message.getJSONObject("params"));
                    }
                } catch (Exception e) {
                    // Skip malformed log entries and continue processing
                }
            }
        } catch (Exception e) {
            System.err.println("Error capturing network logs: " + e.getMessage());
        }
    }
    

private static final String timestamp = java.time.LocalDateTime.now()
    .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
private static final String logFilePath = "network_logs_" + timestamp + ".log";
private static final String errorLogFilePath = "network_error_logs_" + timestamp + ".log";

public static void CaptureNetworkLogsWithResponseBody() {
    if (driver == null) return;

    DevTools devTools = ((HasDevTools) driver).getDevTools();
    devTools.createSession();
    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

    devTools.addListener(Network.requestWillBeSent(), event -> {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write("Request: " + event.getRequest().getUrl());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing request log: " + e.getMessage());
       }
    });

    devTools.addListener(Network.responseReceived(), event -> {
        String url = event.getResponse().getUrl();
        int status = event.getResponse().getStatus();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
           writer.write("Response: " + status + " for " + url);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing response log: " + e.getMessage());
        }
        if (status >= 400) {
            try {
                Network.GetResponseBodyResponse bodyResponse =
                   devTools.send(Network.getResponseBody(event.getRequestId()));
                String responseBody = bodyResponse.getBody();

               try (BufferedWriter errorWriter = new BufferedWriter(new FileWriter(errorLogFilePath, true))) {
                    errorWriter.write("Error Response: " + status + " for Request: " + url);
                   errorWriter.newLine();
                    errorWriter.write("Response Body: " + responseBody);
                   errorWriter.newLine();
               }
            } catch (Exception e) {
                System.err.println("Error writing error log or fetching response body: " + e.getMessage());
            }
        }
    });

    System.out.println("Network logs are saved to: " + logFilePath);
    System.out.println("Error logs are saved to: " + errorLogFilePath);
}



    private void handleNetworkRequest(JSONObject params) {
        try {
            String requestId = params.getString("requestId");
            JSONObject request = params.getJSONObject("request");

            Map<String, Object> requestData = new HashMap<>();
            requestData.put("url", request.getString("url"));
            requestData.put("method", request.getString("method"));
            requestData.put("headers", convertJSONObjectToMap(request.getJSONObject("headers")));
            requestData.put("timestamp", Instant.now().toEpochMilli());

            if (request.has("postData")) {
                requestData.put("postData", request.getString("postData"));
            }

            requestStore.put(requestId, requestData);
        } catch (Exception e) {
            // Skip malformed requests
        }
    }

    private void handleNetworkResponse(JSONObject params) {
        try {
            String requestId = params.getString("requestId");
            JSONObject response = params.getJSONObject("response");

            if (!requestStore.containsKey(requestId)) return;

            Map<String, Object> requestData = requestStore.get(requestId);
            Map<String, Object> harEntry = createHarEntry(requestData, response);

            if (harEntry != null) {
                harEntries.add(harEntry);
            }

        } catch (Exception e) {
            // Skip malformed responses
        }
    }

    private void handleConsoleLog(JSONObject params) {
        try {
            if (params.has("args")) {
                JSONArray args = params.getJSONArray("args");
                for (int i = 0; i < args.length(); i++) {
                    JSONObject arg = args.getJSONObject(i);
                    if (arg.has("value")) {
                        consoleLogs.add(arg.getString("value"));
                    }
                }
            }
        } catch (Exception e) {
            // Skip malformed console logs
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> createHarEntry(Map<String, Object> requestData, JSONObject response) {
        try {
            Map<String, Object> entry = new LinkedHashMap<>();

            // Get current timestamp for proper time calculation
            long currentTime = System.currentTimeMillis();
            long startTime = (Long) requestData.get("timestamp");

            // Basic timing info - ensure valid numeric values
            entry.put("startedDateTime", new Date(startTime).toInstant().toString());
            entry.put("time", Math.max(0, currentTime - startTime)); // Ensure non-negative

            // Request section
            Map<String, Object> requestSection = new HashMap<>();
            requestSection.put("method", requestData.get("method"));
            requestSection.put("url", requestData.get("url"));
            requestSection.put("httpVersion", "HTTP/1.1");
            requestSection.put("headers", convertHeadersToHarFormat((Map<String, Object>) requestData.get("headers")));
            requestSection.put("queryString", new ArrayList<>());
            requestSection.put("cookies", new ArrayList<>());
            requestSection.put("headersSize", 0); // Use 0 instead of -1 to avoid parsing issues

            // Calculate proper body size
            int bodySize = 0;
            if (requestData.containsKey("postData") && requestData.get("postData") != null) {
                bodySize = requestData.get("postData").toString().length();
            }
            requestSection.put("bodySize", bodySize);

            if (requestData.containsKey("postData") && requestData.get("postData") != null) {
                Map<String, Object> postData = new HashMap<>();
                postData.put("mimeType", "application/json");
                postData.put("text", requestData.get("postData"));
                requestSection.put("postData", postData);
            }

            entry.put("request", requestSection);

            // Response section
            Map<String, Object> responseSection = new HashMap<>();

            // Ensure status is a valid integer
            int status = 200; // Default status
            try {
                status = response.getInt("status");
            } catch (Exception e) {
                // Use default if parsing fails
            }
            responseSection.put("status", status);

            String statusText = "OK"; // Default status text
            try {
                statusText = response.getString("statusText");
            } catch (Exception e) {
                // Use default if parsing fails
            }
            responseSection.put("statusText", statusText);

            responseSection.put("httpVersion", "HTTP/1.1");

            // Handle response headers safely
            Map<String, Object> responseHeaders = new HashMap<>();
            try {
                responseHeaders = convertJSONObjectToMap(response.getJSONObject("headers"));
            } catch (Exception e) {
                // Use empty map if parsing fails
            }
            responseSection.put("headers", convertHeadersToHarFormat(responseHeaders));

            responseSection.put("cookies", new ArrayList<>());

            // Content section with proper structure
            Map<String, Object> content = new HashMap<>();
            content.put("size", 0);
            content.put("mimeType", "text/html");
            content.put("text", "");
            responseSection.put("content", content);

            responseSection.put("redirectURL", "");
            responseSection.put("headersSize", 0); // Use 0 instead of -1
            responseSection.put("bodySize", 0); // Use 0 instead of -1

            entry.put("response", responseSection);

            // Cache and timing info with valid numeric values
            entry.put("cache", new HashMap<>());

            Map<String, Object> timings = new HashMap<>();
            timings.put("send", 0);
            timings.put("wait", 0);
            timings.put("receive", 0);
            timings.put("blocked", 0);
            timings.put("dns", 0);
            timings.put("connect", 0);
            entry.put("timings", timings);

            return entry;

        } catch (Exception e) {
            System.err.println("Error creating HAR entry: " + e.getMessage());
            return null;
        }
    }

    private Map<String, Object> convertJSONObjectToMap(JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        try {
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                map.put(key, jsonObject.get(key));
            }
        } catch (Exception e) {
            // Return empty map on error
        }
        return map;
    }

    private List<Map<String, Object>> convertHeadersToHarFormat(Map<String, Object> headers) {
        List<Map<String, Object>> harHeaders = new ArrayList<>();
        for (Map.Entry<String, Object> header : headers.entrySet()) {
            Map<String, Object> harHeader = new HashMap<>();
            harHeader.put("name", header.getKey());
            harHeader.put("value", header.getValue().toString());
            harHeaders.add(harHeader);
        }
        return harHeaders;
    }

    // Get response body using DevTools if available, otherwise skip
    public void attachResponseBodies() {
        if (devTools == null) {
            System.out.println("DevTools not available - response bodies will not be captured");
            return;
        }

        // Response body capture would require version-specific CDP commands
        // For now, we'll skip this to maintain version compatibility
        System.out.println("Response body capture skipped for version compatibility");
    }

    public void exportHar(String filePath) throws IOException {
        Map<String, Object> har = new LinkedHashMap<>();
        har.put("log", Map.of(
                "version", "1.2",
                "creator", Map.of("name", "Selenium Network Capture", "version", "1.0"),
                "entries", harEntries
        ));

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), har);
    }

    public List<String> getConsoleLogs() {
        return new ArrayList<>(consoleLogs);
    }

    public List<Map<String, Object>> getHarEntries() {
        return new ArrayList<>(harEntries);
    }

    public void clearLogs() {
        harEntries.clear();
        consoleLogs.clear();
        requestStore.clear();
    }
}
