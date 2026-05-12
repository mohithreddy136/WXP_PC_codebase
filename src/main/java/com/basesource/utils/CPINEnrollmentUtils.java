package com.basesource.utils;

import com.daasui.constants.ConstantPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Utility class for CPIN (Customer Premise Infrastructure Network) enrollment operations
 * Handles device enrollment flow, database validations, and cleanup operations
 */
public class CPINEnrollmentUtils {

    private static final Logger LOGGER = LogManager.getLogger(CPINEnrollmentUtils.class);

    // CPIN Enrollment base path from ConstantPath
    private static final String SCRIPT_BASE_PATH = com.daasui.constants.ConstantPath.CPIN_SCRIPT_BASE_PATH;


    // Properties for configuration
    private Properties config;

    public CPINEnrollmentUtils() {
        loadConfiguration();
    }

    /**
     * Load configuration from properties file
     */
    private void loadConfiguration() {
        config = new Properties();
        try {
            String configPath = com.daasui.constants.ConstantPath.CPIN_CONFIG_PROPERTIES;
            if (Files.exists(Paths.get(configPath))) {
                config.load(new FileInputStream(configPath));
                LOGGER.info("Configuration loaded from: {}", configPath);
            } else {
                // Set default configuration
                setDefaultConfiguration();
                LOGGER.info("Using default configuration");
            }
        } catch (IOException e) {
            LOGGER.error("Error loading configuration: {}", e.getMessage());
            setDefaultConfiguration();
        }
    }

    /**
     * Set default configuration values
     */
    private void setDefaultConfiguration() {
        config.setProperty("enrollment.timeout.minutes", "10");
        config.setProperty("enrollment.retry.count", "3");
        config.setProperty("enrollment.batch.size", "10");
        config.setProperty("database.validation.enabled", "true");
        config.setProperty("cleanup.after.enrollment", "false");
    }

    /**
     * Execute CPIN enrollment for the provided serial numbers and configuration
     *
     * @param serialNumbers List of device serial numbers to enroll
     * @param environment   Environment name (e.g., "stage", "prod")
     * @param region        Region name (e.g., "us", "eu")
     * @param pin           CPIN value for enrollment
     * @return true if enrollment is successful, false otherwise
     */
    public boolean executeCPINEnrollment(List<String> serialNumbers, String environment, String region, String pin) {
        LOGGER.info("Starting CPIN enrollment for {} devices in env: {}, region: {}", serialNumbers.size(), environment, region);

        try {
            // Step 1: Create serial numbers CSV file
            if (!createSerialNumbersCsv(serialNumbers)) {
                LOGGER.error("Failed to create serial numbers CSV");
                return false;
            }

            // Step 2: Create input JSON configuration
            if (!createInputJsonConfiguration(environment, region, pin)) {
                LOGGER.error("Failed to create input JSON configuration");
                return false;
            }

            // Step 3: Execute CPIN enrollment script
            if (!executeEnrollmentScript()) {
                LOGGER.error("Failed to execute CPIN enrollment script");
                return false;
            }

            // Step 4: Validate enrollment results
            if (!validateEnrollmentResults(serialNumbers.size())) {
                LOGGER.error("Enrollment validation failed");
                return false;
            }

            LOGGER.info("CPIN enrollment completed successfully for all {} devices", serialNumbers.size());
            return true;

        } catch (Exception e) {
            LOGGER.error("Error during CPIN enrollment: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Create CSV file with serial numbers for enrollment
     */
    private boolean createSerialNumbersCsv(List<String> serialNumbers) {
        try {
            Path csvPath = Paths.get(com.daasui.constants.ConstantPath.CPIN_SERIAL_NUMBERS_CSV);
            // Ensure directory exists
            Files.createDirectories(csvPath.getParent());

            List<String> csvLines = new ArrayList<>();
            csvLines.addAll(serialNumbers);

            Files.write(csvPath, csvLines);
            LOGGER.info("Created serial numbers CSV with {} entries", serialNumbers.size());
            return true;

        } catch (IOException e) {
            LOGGER.error("Error creating serial numbers CSV: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Create input JSON configuration for enrollment script
     */
    private boolean createInputJsonConfiguration(String environment, String region, String pin) {
        try {
            Path jsonPath = Paths.get(com.daasui.constants.ConstantPath.CPIN_INPUT_JSON_FILE);

            String json = String.format("{\n  \"environment\": \"%s\",\n  \"region\": \"%s\",\n  \"enroll_type\": \"CPIN\",\n  \"pin\": \"%s\"\n}", environment, region, pin);

            Files.write(jsonPath, json.getBytes());
            LOGGER.info("Created input JSON configuration for env: {}, region: {}", environment, region);
            return true;

        } catch (IOException e) {
            LOGGER.error("Error creating input JSON: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Execute the CPIN enrollment shell script
     */
    private boolean executeEnrollmentScript() {
        try {
            String scriptDirAbs = new File(SCRIPT_BASE_PATH).getAbsolutePath().replace("\\", "/");
            File scriptDirFile = new File(scriptDirAbs);
            String scriptName = "run.sh";

            File scriptFile = new File(scriptDirFile, scriptName);
            if (!scriptFile.exists()) {
                LOGGER.error("Enrollment script not found: {}", scriptFile.getAbsolutePath());
                return false;
            }

            List<String> command;
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                String gitBashPath = findGitBash();
                if (gitBashPath == null) {
                    LOGGER.error("Git Bash not found. Please install Git for Windows.");
                    return false;
                }
                command = List.of(
                        gitBashPath, "-c",
                        "export HTTP_PROXY=http://web-proxy.corp.hp.com:8080 && " +
                                "export HTTPS_PROXY=http://web-proxy.corp.hp.com:8080 && " +
                                "./run.sh"
                );
            } else {
                command = List.of(
                        "/bin/bash", "-c",
                        "export HTTP_PROXY=http://web-proxy.corp.hp.com:8080 && " +
                                "export HTTPS_PROXY=http://web-proxy.corp.hp.com:8080 && " +
                                "./run.sh"
                );
            }

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(scriptDirFile);
            processBuilder.redirectErrorStream(true);

            LOGGER.info("Executing CPIN enrollment script: {}", scriptFile.getAbsolutePath());

            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    LOGGER.info("Script output: {}", line);
                }
            }

            int exitCode = process.waitFor();
            LOGGER.info("Shell script finished with exit code: {}", exitCode);

            if (exitCode != 0) {
                LOGGER.error("Shell script execution failed with exit code: " + exitCode);
                return false;
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error executing enrollment script: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Find Git Bash installation on Windows
     */
    private String findGitBash() {
        String[] possiblePaths = {
                "C:\\Program Files\\Git\\bin\\bash.exe",
                "C:\\Program Files (x86)\\Git\\bin\\bash.exe",
                "C:\\Git\\bin\\bash.exe"
        };

        for (String path : possiblePaths) {
            if (Files.exists(Paths.get(path))) {
                return path;
            }
        }

        return null;
    }

    /**
     * Validate enrollment results from metrics CSV
     */
    private boolean validateEnrollmentResults(int expectedDeviceCount) {
        try {
            Path metricsPath = Paths.get(com.daasui.constants.ConstantPath.CPIN_ENROLLMENT_METRICS_CSV);

            if (!Files.exists(metricsPath)) {
                LOGGER.error("Enrollment metrics file not found: {}", metricsPath);
                return false;
            }

            List<String> lines = Files.readAllLines(metricsPath);
            if (lines.size() < 2) { // At least header + 1 data line
                LOGGER.error("Invalid metrics file format");
                return false;
            }

            int successCount = 0;
            int failureCount = 0;

            // Skip header line
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] columns = line.split(",");
                if (columns.length < 2) continue;
                String serial = columns[1].trim();
                if (serial.equalsIgnoreCase("SerialNumber")) continue; // skip header row if present
                if (line.contains(",true")) {
                    successCount++;
                } else if (line.contains(",false")) {
                    failureCount++;
                }
            }

            LOGGER.info("Enrollment results - Success: {}, Failure: {}", successCount, failureCount);

            if (successCount == expectedDeviceCount && failureCount == 0) {
                LOGGER.info("All {} devices enrolled successfully", expectedDeviceCount);
                return true;
            } else {
                LOGGER.error("Enrollment validation failed. Expected: {}, Success: {}, Failure: {}",
                        expectedDeviceCount, successCount, failureCount);
                return false;
            }

        } catch (IOException e) {
            LOGGER.error("Error validating enrollment results: {}", e.getMessage());
            return false;
        }

    }
}