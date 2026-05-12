package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.utils.activecare.BaseConfigurableUtils;
import com.daasui.pages.BatchIOTEnrollmentPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Example test class demonstrating batch IOT enrollment processing
 * for large-scale device enrollment operations (10k+ devices).
 * 
 * Uses dedicated BatchIOTEnrollmentPage for clean separation of concerns.
 */
public class BatchIOTEnrollmentExample extends BaseConfigurableUtils {

    private static Logger LOGGER = LogManager.getLogger(BatchIOTEnrollmentExample.class);

    /**
     * Example: Process 10k devices from CSV with parallel processing
     * 
     * CSV Format (with header):
     * SerialNumber,BiosUUID,ProductNumber
     * CPAUTO100751,66BA178E-7D82-4BB6-9SSS-9335956F8521,1134466#BQQQ
     * CPAUTO100752,77CB289F-8E93-5CC7-8TTT-8446067G9632,2245577#CRRR
     * ...
     */
    @Test(priority = 1, groups = {"BATCH_ENROLLMENT"}, description = "Batch IOT Enrollment - 10K Devices")
    public void testBatchIOTEnrollment() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        
        // Initialize batch enrollment page
        BatchIOTEnrollmentPage batchEnrollmentPage = new BatchIOTEnrollmentPage(PreDefinedActions.getDriver()).getInstance();
        
        // Configuration
        String csvFilePath = "c:\\Automation\\selenium-wex-automation\\import\\device_enrollment_10k.csv";
        boolean hasHeader = true; // Set to false if CSV has no header row
        // String tenantId = getEnvironmentSpecificData(System.getProperty("environment"), "AC_PLUS_PI_NAME_TENANT_ID");
        // String cpin = getEnvironmentSpecificData(System.getProperty("environment"), "AC_PLUS_PI_NAME_TENANT_CPIN");
        String tenantId = "01a5f1b9-e111-46fb-a186-c262706ea5cc";
        String cpin = "kb4Yv5SY";

        // Optional: Validate CSV before processing
        BatchIOTEnrollmentPage.ValidationResult validation = 
            batchEnrollmentPage.validateCSVFile(csvFilePath, hasHeader);
        LOGGER.info("CSV Validation: {}", validation);
        softAssert.assertTrue(validation.isValid, "CSV validation failed: " + validation.errors);
        
        // Option 1: Use default settings (5 threads, progress every 100 devices)
        BatchIOTEnrollmentPage.BatchEnrollmentResult result = 
            batchEnrollmentPage.performBatchIOTEnrollment(csvFilePath, hasHeader, tenantId, cpin);
        
        // Option 2: Custom settings for better control
        // int parallelThreads = 10; // Increase for faster processing (max 20 recommended)
        // int batchSize = 500; // Log progress every 500 devices
        // BatchIOTEnrollmentPage.BatchEnrollmentResult result = 
        //     batchEnrollmentPage.performBatchIOTEnrollment(csvFilePath, hasHeader, tenantId, cpin, parallelThreads, batchSize);
        
        // Verify results
        LOGGER.info(result.getSummary());
        
        // Assertions
        softAssert.assertTrue(result.successCount > 0, "No devices were enrolled successfully");
        softAssert.assertTrue(result.failureCount < result.totalRecords * 0.05, 
                             "More than 5% failure rate: " + result.failureCount + "/" + result.totalRecords);
        
        // Export failed devices to CSV for retry/investigation
        if (!result.failedSerials.isEmpty()) {
            result.exportFailedDevicesToCSV("c:\\Automation\\selenium-wex-automation\\test-output\\failed_enrollments.csv");
        }
        
        softAssert.assertAll();
    }

    /**
     * Example: Process smaller batch with custom thread count
     */
    @Test(priority = 2, groups = {"BATCH_ENROLLMENT"}, description = "Batch IOT Enrollment - Custom Settings")
    public void testCustomBatchEnrollment() throws Exception {
        BatchIOTEnrollmentPage batchEnrollmentPage = new BatchIOTEnrollmentPage(PreDefinedActions.getDriver()).getInstance();
        
        String csvFilePath = "c:\\Automation\\selenium-wex-automation\\import\\device_enrollment_sample.csv";
        String tenantId = getEnvironmentSpecificData(System.getProperty("environment"), "ACTIVE_CARE_NAME_TENANT_ID");
        String cpin = getEnvironmentSpecificData(System.getProperty("environment"), "ACTIVE_CARE_NAME_TENANT_CPIN");
        
        // Process with 8 parallel threads, log every 50 devices
        BatchIOTEnrollmentPage.BatchEnrollmentResult result = 
            batchEnrollmentPage.performBatchIOTEnrollment(csvFilePath, true, tenantId, cpin, 8, 50);
        
        LOGGER.info("Enrollment completed: {} successful, {} failed out of {} total", 
                   result.successCount, result.failureCount, result.totalRecords);
        
        // Write detailed report
        writeDetailedReport(result, "c:\\Automation\\selenium-wex-automation\\test-report\\enrollment_report.txt");
    }

    /**
     * Example: Single device enrollment using SNR method (without CSV)
     */
    @Test(priority = 3, groups = {"BATCH_ENROLLMENT"}, description = "Single Device IOT Enrollment")
    public void testSingleDeviceEnrollment() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        BatchIOTEnrollmentPage batchEnrollmentPage = new BatchIOTEnrollmentPage(PreDefinedActions.getDriver()).getInstance();
        
        String serialNumber = "A002R9TDLSOED";
        String biosUUID = "aecff2ef-705d-469e-8b83-e583732ff8a0";
        String productNumber = "626DC305552A";
        // String tenantId = getEnvironmentSpecificData(System.getProperty("environment"), "AC_PLUS_PI_NAME_TENANT_ID");
        // String cpin = getEnvironmentSpecificData(System.getProperty("environment"), "AC_PLUS_PI_NAME_TENANT_CPIN");
        String tenantId = "44250eaf-e1bf-484f-9744-982930ac988f";
        String cpin = null;

        boolean enrolled = batchEnrollmentPage.performIOTEnrollmentWithSNR(
            serialNumber, biosUUID, productNumber, tenantId, cpin
        );
        
        softAssert.assertTrue(enrolled, "Device enrollment failed for: " + serialNumber);
        LOGGER.info("Device {} enrolled successfully", serialNumber);
        softAssert.assertAll();
    }

    /**
     * Helper method to write detailed enrollment report
     */
    private void writeDetailedReport(BatchIOTEnrollmentPage.BatchEnrollmentResult result, String outputPath) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(outputPath)) {
            writer.println(result.getSummary());
            
            writer.println("\nSuccessful Enrollments:");
            result.successfulSerials.forEach(serial -> writer.println("  ✓ " + serial));
            
            if (!result.failedSerials.isEmpty()) {
                writer.println("\nFailed Enrollments:");
                result.failedSerials.forEach(serial -> 
                    writer.println("  ✗ " + serial + " - " + result.failureReasons.get(serial))
                );
            }
            
            LOGGER.info("Detailed report written to: {}", outputPath);
        } catch (Exception e) {
            LOGGER.error("Failed to write detailed report: {}", e.getMessage());
        }
    }
}
