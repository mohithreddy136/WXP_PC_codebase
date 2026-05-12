package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.EnrollFakeDevice;
import com.basesource.utils.TestSnrDeviceId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Page class for batch IOT device enrollment operations.
 * Handles large-scale device enrollment (10k+) using parallel processing
 * and SNR-based device ID generation without UI navigation.
 */
public class BatchIOTEnrollmentPage extends CommonMethod {
    
    private static final Logger LOGGER = LogManager.getLogger(BatchIOTEnrollmentPage.class);
    private BatchIOTEnrollmentPage instance;
    
    public BatchIOTEnrollmentPage(WebDriver driver) {
        // Constructor - no super call needed as CommonMethod doesn't take WebDriver
    }
    
    public BatchIOTEnrollmentPage getInstance() {
        if (instance == null) {
            synchronized (BatchIOTEnrollmentPage.class) {
                if (instance == null) {
                    instance = new BatchIOTEnrollmentPage(DRIVER);
                }
            }
        }
        return instance;
    }

    /**
     * Data class to hold device enrollment record from CSV
     */
    public static class DeviceEnrollmentRecord {
        public String serialNumber;
        public String biosUUID;
        public String productNumber;
        
        public DeviceEnrollmentRecord(String serialNumber, String biosUUID, String productNumber) {
            this.serialNumber = serialNumber;
            this.biosUUID = biosUUID;
            this.productNumber = productNumber;
        }
        
        @Override
        public String toString() {
            return String.format("DeviceEnrollmentRecord[serial=%s, bios=%s, product=%s]", 
                               serialNumber, biosUUID, productNumber);
        }
    }

    /**
     * Result class to track batch enrollment results
     */
    public static class BatchEnrollmentResult {
        public int totalRecords;
        public int successCount;
        public int failureCount;
        public List<String> successfulSerials = new ArrayList<>();
        public List<String> failedSerials = new ArrayList<>();
        public Map<String, String> failureReasons = new HashMap<>();
        public long executionTimeMs;
        
        /**
         * Generates a formatted summary of the batch enrollment results
         */
        public String getSummary() {
            StringBuilder summary = new StringBuilder();
            summary.append("\n========== Batch Enrollment Summary ==========\n");
            summary.append(String.format("Total Records: %d\n", totalRecords));
            
            if (totalRecords > 0) {
                summary.append(String.format("Successful: %d (%.2f%%)\n", 
                                           successCount, (successCount * 100.0 / totalRecords)));
                summary.append(String.format("Failed: %d (%.2f%%)\n", 
                                           failureCount, (failureCount * 100.0 / totalRecords)));
            } else {
                summary.append("Successful: 0\nFailed: 0\n");
            }
            
            summary.append(String.format("Execution Time: %.2f seconds\n", executionTimeMs / 1000.0));
            summary.append("=============================================\n");
            
            if (!failedSerials.isEmpty()) {
                summary.append("\nFailed Serial Numbers:\n");
                failedSerials.forEach(serial -> 
                    summary.append(String.format("  - %s: %s\n", 
                                                serial, 
                                                failureReasons.getOrDefault(serial, "Unknown error")))
                );
            }
            return summary.toString();
        }
        
        /**
         * Exports failed devices to CSV file for retry
         */
        public void exportFailedDevicesToCSV(String outputPath) {
            try (java.io.PrintWriter writer = new java.io.PrintWriter(outputPath)) {
                writer.println("SerialNumber,FailureReason");
                failedSerials.forEach(serial -> {
                    String reason = failureReasons.getOrDefault(serial, "Unknown");
                    // Escape commas in reason
                    writer.println(serial + ",\"" + reason.replace("\"", "\"\"") + "\"");
                });
                LOGGER.info("Failed devices exported to: {}", outputPath);
            } catch (Exception e) {
                LOGGER.error("Failed to export failed devices: {}", e.getMessage());
            }
        }
    }

    /**
     * Reads device enrollment records from CSV file.
     * Expected CSV format: SerialNumber, BiosUUID, ProductNumber (with or without header)
     * 
     * @param csvFilePath Absolute path to CSV file
     * @param hasHeader true if CSV has header row, false otherwise
     * @return List of DeviceEnrollmentRecord objects
     */
    public List<DeviceEnrollmentRecord> readDeviceEnrollmentCSV(String csvFilePath, boolean hasHeader) {
        List<DeviceEnrollmentRecord> records = new ArrayList<>();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(csvFilePath))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // Skip header if present
                if (hasHeader && lineNumber == 1) {
                    continue;
                }
                
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] fields = line.split(",");
                if (fields.length >= 3) {
                    String serialNumber = fields[0].trim();
                    String biosUUID = fields[1].trim();
                    String productNumber = fields[2].trim();
                    
                    if (!serialNumber.isEmpty() && !biosUUID.isEmpty() && !productNumber.isEmpty()) {
                        records.add(new DeviceEnrollmentRecord(serialNumber, biosUUID, productNumber));
                    } else {
                        LOGGER.warn("Skipping line {} - missing required fields: {}", lineNumber, line);
                    }
                } else {
                    LOGGER.warn("Skipping line {} - insufficient columns: {}", lineNumber, line);
                }
            }
            
            LOGGER.info("Successfully read {} records from CSV: {}", records.size(), csvFilePath);
        } catch (Exception e) {
            LOGGER.error("Error reading CSV file {}: {}", csvFilePath, e.getMessage());
        }
        return records;
    }

    /**
     * Performs single device IOT enrollment using SNR-based device ID generation.
     * This is the core enrollment method used by batch processing.
     * 
     * @param serialNumber Serial number of the device
     * @param biosUUID BIOS UUID of the device
     * @param productNumber Product number of the device
     * @param tenantId Tenant ID where device should be enrolled
     * @param cpin Company PIN for enrollment
     * @return true if enrollment succeeds, false otherwise
     */
    public boolean performIOTEnrollmentWithSNR(String serialNumber, String biosUUID, 
                                               String productNumber, String tenantId, String cpin) {
        try {
            EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
            String deviceID = TestSnrDeviceId.generateDeviceId(serialNumber, biosUUID, productNumber);
            LOGGER.info("Generated device ID using SNR method for {}: {}", serialNumber, deviceID);
            
            return enrollFakeDevice.enrollIOTFakeDeviceWithMinimalParams(cpin, deviceID, tenantId, serialNumber, productNumber, biosUUID);
        } catch (Exception e) {
            LOGGER.error("Exception during IOT enrollment with SNR for {}: {}", serialNumber, e.getMessage());
            return false;
        }
    }

    /**
     * Performs batch IOT enrollment with parallel processing for large-scale operations.
     * Optimized for processing 10k+ devices efficiently with progress tracking and error handling.
     * 
     * @param csvFilePath Absolute path to CSV file containing device records
     * @param hasHeader true if CSV has header row, false otherwise
     * @param tenantId Tenant ID where devices should be enrolled
     * @param cpin Company PIN for enrollment
     * @param parallelThreads Number of parallel threads (recommended: 5-10 for API stability)
     * @param batchSize Number of devices to process before logging progress (default: 100)
     * @return BatchEnrollmentResult containing detailed execution summary
     */
    public BatchEnrollmentResult performBatchIOTEnrollment(String csvFilePath, boolean hasHeader, 
                                                           String tenantId, String cpin,
                                                           int parallelThreads, int batchSize) {
        BatchEnrollmentResult result = new BatchEnrollmentResult();
        long startTime = System.currentTimeMillis();
        
        try {
            // Read all records from CSV
            List<DeviceEnrollmentRecord> records = readDeviceEnrollmentCSV(csvFilePath, hasHeader);
            result.totalRecords = records.size();
            
            if (records.isEmpty()) {
                LOGGER.error("No valid records found in CSV file: {}", csvFilePath);
                result.executionTimeMs = System.currentTimeMillis() - startTime;
                return result;
            }
            
            LOGGER.info("Starting batch enrollment for {} devices with {} parallel threads", 
                       result.totalRecords, parallelThreads);
            
            // Process records in parallel using ExecutorService
            java.util.concurrent.ExecutorService executor = 
                java.util.concurrent.Executors.newFixedThreadPool(parallelThreads);
            java.util.concurrent.atomic.AtomicInteger processedCount = 
                new java.util.concurrent.atomic.AtomicInteger(0);
            
            List<java.util.concurrent.Future<Boolean>> futures = new ArrayList<>();
            
            for (DeviceEnrollmentRecord record : records) {
                java.util.concurrent.Future<Boolean> future = executor.submit(() -> {
                    try {
                        boolean enrolled = performIOTEnrollmentWithSNR(
                            record.serialNumber, 
                            record.biosUUID, 
                            record.productNumber, 
                            tenantId,
                            cpin
                        );
                        
                        // Thread-safe result tracking
                        synchronized (result) {
                            if (enrolled) {
                                result.successCount++;
                                result.successfulSerials.add(record.serialNumber);
                            } else {
                                result.failureCount++;
                                result.failedSerials.add(record.serialNumber);
                                result.failureReasons.put(record.serialNumber, "Enrollment API returned false");
                            }
                        }
                        
                        // Progress logging
                        int processed = processedCount.incrementAndGet();
                        if (processed % batchSize == 0) {
                            LOGGER.info("Progress: {}/{} devices processed ({} successful, {} failed)", 
                                       processed, result.totalRecords, result.successCount, result.failureCount);
                        }
                        
                        return enrolled;
                    } catch (Exception e) {
                        synchronized (result) {
                            result.failureCount++;
                            result.failedSerials.add(record.serialNumber);
                            result.failureReasons.put(record.serialNumber, e.getMessage());
                        }
                        LOGGER.error("Exception enrolling device {}: {}", record.serialNumber, e.getMessage());
                        return false;
                    }
                });
                futures.add(future);
            }
            
            // Wait for all tasks to complete
            executor.shutdown();
            try {
                if (!executor.awaitTermination(2, java.util.concurrent.TimeUnit.HOURS)) {
                    LOGGER.warn("Batch enrollment exceeded 2 hour timeout. Forcing shutdown.");
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                LOGGER.error("Batch enrollment interrupted: {}", e.getMessage());
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
            
            result.executionTimeMs = System.currentTimeMillis() - startTime;
            LOGGER.info(result.getSummary());
            
        } catch (Exception e) {
            LOGGER.error("Fatal error during batch enrollment: {}", e.getMessage());
            result.executionTimeMs = System.currentTimeMillis() - startTime;
        }
        
        return result;
    }

    /**
     * Convenience method for batch enrollment with default settings.
     * Uses 5 parallel threads and logs progress every 100 devices.
     * 
     * @param csvFilePath Path to CSV file
     * @param hasHeader true if CSV has header
     * @param tenantId Tenant ID
     * @param cpin Company PIN
     * @return BatchEnrollmentResult
     */
    public BatchEnrollmentResult performBatchIOTEnrollment(String csvFilePath, boolean hasHeader, 
                                                           String tenantId, String cpin) {
        return performBatchIOTEnrollment(csvFilePath, hasHeader, tenantId, cpin, 5, 100);
    }
    
    /**
     * Validates CSV file format before processing
     * 
     * @param csvFilePath Path to CSV file
     * @param hasHeader Whether CSV has header
     * @return Validation result with any errors found
     */
    public ValidationResult validateCSVFile(String csvFilePath, boolean hasHeader) {
        ValidationResult validation = new ValidationResult();
        
        try {
            java.io.File file = new java.io.File(csvFilePath);
            if (!file.exists()) {
                validation.isValid = false;
                validation.errors.add("File does not exist: " + csvFilePath);
                return validation;
            }
            
            if (!file.canRead()) {
                validation.isValid = false;
                validation.errors.add("File is not readable: " + csvFilePath);
                return validation;
            }
            
            List<DeviceEnrollmentRecord> records = readDeviceEnrollmentCSV(csvFilePath, hasHeader);
            validation.recordCount = records.size();
            
            if (records.isEmpty()) {
                validation.isValid = false;
                validation.errors.add("No valid records found in CSV file");
            } else {
                validation.isValid = true;
                LOGGER.info("CSV validation passed: {} valid records found", records.size());
            }
            
        } catch (Exception e) {
            validation.isValid = false;
            validation.errors.add("Error validating CSV: " + e.getMessage());
        }
        
        return validation;
    }
    
    /**
     * Result class for CSV validation
     */
    public static class ValidationResult {
        public boolean isValid = true;
        public int recordCount = 0;
        public List<String> errors = new ArrayList<>();
        
        @Override
        public String toString() {
            if (isValid) {
                return String.format("Validation passed: %d records found", recordCount);
            } else {
                return "Validation failed: " + String.join(", ", errors);
            }
        }
    }
}
