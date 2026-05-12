# Batch IOT Enrollment - Processing Guide

## Overview
The batch IOT enrollment feature allows you to process **10,000+ devices** in a single execution using parallel processing and SNR-based device ID generation (no UI navigation required).
The pre-requisite is to have all serial numbers inserted into SNR entry
All serial numbers should be imported into tenant.

---

## Key Features

✅ **High Performance**: Process 10k+ devices efficiently with parallel threads  
✅ **No UI Dependency**: Direct API calls using SNR-based device ID generation  
✅ **Progress Tracking**: Real-time logging every N devices  
✅ **Error Handling**: Detailed failure tracking with reasons  
✅ **Resumable**: Export failed devices for retry  
✅ **Configurable**: Adjust thread count and batch size  

---

## CSV File Format

### Required Columns
Your CSV must contain these 3 columns (order matters):
1. **SerialNumber** - Device serial number
2. **BiosUUID** - BIOS UUID (with or without dashes)
3. **ProductNumber** - Product number

### Example CSV (with header)
```csv
SerialNumber,BiosUUID,ProductNumber
CPAUTO100751,66BA178E-7D82-4BB6-9SSS-9335956F8521,1134466#BQQQ
CPAUTO100752,77CB289F-8E93-5CC7-8TTT-8446067G9632,2245577#CRRR
CPAUTO100753,88DC390A-9FA4-6DD8-9UUU-9557178H0743,3356688#DSSS
...
```

### Example CSV (without header)
```csv
CPAUTO100751,66BA178E-7D82-4BB6-9SSS-9335956F8521,1134466#BQQQ
CPAUTO100752,77CB289F-8E93-5CC7-8TTT-8446067G9632,2245577#CRRR
CPAUTO100753,88DC390A-9FA4-6DD8-9UUU-9557178H0743,3356688#DSSS
...
```

---

## Usage Examples

### Option 1: Simple Usage (Default Settings)
Uses 5 parallel threads, logs progress every 100 devices.

```java
BatchIOTEnrollmentPage batchEnrollmentPage = new BatchIOTEnrollmentPage(driver).getInstance();

String csvFilePath = "c:\\path\\to\\device_enrollment_10k.csv";
String tenantId = "your-tenant-id";
String cpin = "your-company-pin";

BatchEnrollmentResult result = batchEnrollmentPage.performBatchIOTEnrollment(
    csvFilePath, 
    true,      // hasHeader = true
    tenantId,
    cpin
);

System.out.println(result.getSummary());
```

### Option 2: Custom Configuration (Advanced)
Adjust threads and batch size for optimal performance.

```java
String csvFilePath = "c:\\path\\to\\device_enrollment_10k.csv";
String tenantId = "your-tenant-id";
String cpin = "your-company-pin";
int parallelThreads = 10;  // Number of parallel threads
int batchSize = 500;       // Log progress every 500 devices

BatchEnrollmentResult result = batchEnrollmentPage.performBatchIOTEnrollment(
    csvFilePath, 
    true,               // hasHeader
    tenantId,
    cpin,
    parallelThreads,    // More threads = faster (max 20 recommended)
    batchSize           // Progress logging frequency
);
```

### Option 3: Validate CSV Before Processing

```java
// Validate CSV format before batch processing
ValidationResult validation = batchEnrollmentPage.validateCSVFile(csvFilePath, true);

if (validation.isValid) {
    LOGGER.info("CSV is valid: {} records found", validation.recordCount);
    // Proceed with batch enrollment
} else {
    LOGGER.error("CSV validation failed: {}", validation.errors);
}
```

---

## Performance Guidelines

### Thread Count Recommendations

| Devices | Recommended Threads | Expected Time* |
|---------|-------------------|----------------|
| 100-1k  | 5                 | 2-10 min       |
| 1k-5k   | 8                 | 10-30 min      |
| 5k-10k  | 10                | 30-60 min      |
| 10k+    | 15-20             | 1-2 hours      |

*Depends on API response time and network conditions

### Optimization Tips

1. **Start Conservative**: Begin with 5 threads, increase if stable
2. **Monitor API Rate Limits**: Too many threads may trigger rate limiting
3. **Batch Size**: Use larger batch sizes (500-1000) for 10k+ records to reduce logging overhead
4. **Network**: Ensure stable network connection for long-running operations

---

## Result Tracking

### BatchEnrollmentResult Object

```java
result.totalRecords        // Total devices in CSV
result.successCount        // Successfully enrolled
result.failureCount        // Failed enrollments
result.successfulSerials   // List of successful serial numbers
result.failedSerials       // List of failed serial numbers
result.failureReasons      // Map of serial -> error message
result.executionTimeMs     // Total execution time in milliseconds
```

### Sample Output

```
========== Batch Enrollment Summary ==========
Total Records: 10000
Successful: 9850 (98.50%)
Failed: 150 (1.50%)
Execution Time: 3256.42 seconds
=============================================

Failed Serial Numbers:
  - CPAUTO105234: Enrollment API returned false
  - CPAUTO106789: Device ID generation failed
  - CPAUTO107890: Connection timeout
```

---

## Error Handling & Retry

### Export Failed Devices

```java
if (!result.failedSerials.isEmpty()) {
    // Export to CSV for retry
    try (PrintWriter writer = new PrintWriter("failed_enrollments.csv")) {
        writer.println("SerialNumber,FailureReason");
        result.failedSerials.forEach(serial -> {
            String reason = result.failureReasons.get(serial);
            writer.println(serial + "," + reason);
        });
    }
}
```

### Retry Failed Devices

1. Review `failed_enrollments.csv`
2. Investigate common failure patterns
3. Create new CSV with only failed devices
4. Re-run batch enrollment

---

## Complete Test Example

```java
@Test(priority = 1, groups = {"BATCH_ENROLLMENT"})
public void enrollBulkDevices() throws Exception {
    // Initialize batch enrollment page
    BatchIOTEnrollmentPage batchEnrollmentPage = new BatchIOTEnrollmentPage(driver).getInstance();
    
    // Configuration
    String csvFilePath = "c:\\import\\device_enrollment_10k.csv";
    String tenantId = getEnvironmentSpecificData(environment, "TENANT_ID");
    String cpin = getEnvironmentSpecificData(environment, "COMPANY_PIN");
    
    // Optional: Validate CSV before processing
    BatchIOTEnrollmentPage.ValidationResult validation = 
        batchEnrollmentPage.validateCSVFile(csvFilePath, true);
    Assert.assertTrue(validation.isValid, "CSV validation failed");
    
    // Execute batch enrollment
    BatchIOTEnrollmentPage.BatchEnrollmentResult result = batchEnrollmentPage.performBatchIOTEnrollment(
        csvFilePath, 
        true,    // hasHeader
        tenantId,
        cpin,
        10,      // 10 parallel threads
        500      // Log every 500 devices
    );
    
    // Log summary
    LOGGER.info(result.getSummary());
    
    // Assertions
    Assert.assertTrue(result.successCount > 0, "No devices enrolled");
    Assert.assertTrue(result.failureCount < result.totalRecords * 0.05, 
                     "Failure rate > 5%");
    
    // Export failures if any
    if (!result.failedSerials.isEmpty()) {
        result.exportFailedDevicesToCSV("failed_enrollments.csv");
    }
}
```

---

## Monitoring & Logs

### Console Output During Execution

```
[INFO] Successfully read 10000 records from CSV: c:\import\device_enrollment_10k.csv
[INFO] Starting batch enrollment for 10000 devices with 10 parallel threads
[INFO] Generated device ID using SNR method: 0Qk06hWYKkR849vi9MxCqs9VFLg21Qcwyv2J5fMbl_I
[INFO] Progress: 500/10000 devices processed (485 successful, 15 failed)
[INFO] Progress: 1000/10000 devices processed (970 successful, 30 failed)
[INFO] Progress: 1500/10000 devices processed (1455 successful, 45 failed)
...
[INFO] Progress: 10000/10000 devices processed (9850 successful, 150 failed)
```

---

## Troubleshooting

### Common Issues

**Issue**: OutOfMemoryError  
**Solution**: Reduce thread count or increase JVM heap size: `-Xmx4g`

**Issue**: Connection timeouts  
**Solution**: Reduce parallel threads (API rate limiting)

**Issue**: Slow processing  
**Solution**: Increase parallel threads (if API allows)

**Issue**: High failure rate  
**Solution**: Check CSV format, validate tenant ID, review API logs

---

## Best Practices

1. ✅ **Test First**: Run with 100 records before processing 10k
2. ✅ **Backup CSV**: Keep original CSV file safe
3. ✅ **Monitor Progress**: Watch logs for early issues
4. ✅ **Off-Peak Hours**: Run large batches during low-traffic periods
5. ✅ **Export Results**: Save successful/failed serials for tracking

---

## Files Location

- **Batch Enrollment Page**: `src/main/java/com/daasui/pages/BatchIOTEnrollmentPage.java`
- **Device List Page** (single enrollment): `src/main/java/com/daasui/pages/WEPDeviceListPage.java`
- **SNR Device ID Generator**: `src/main/java/com/basesource/utils/TestSnrDeviceId.java`
- **Example Test**: `src/test/java/com/testscripts/daasui/BatchIOTEnrollmentExample.java`
- **CSV Sample**: `import/device_enrollment_sample.csv`
- **Output**: `test-output/failed_enrollments.csv`
- **Reports**: `test-report/enrollment_report.txt`

---

## Class Organization

### BatchIOTEnrollmentPage
Dedicated page class for large-scale batch operations:
- ✅ Handles 10k+ device enrollments
- ✅ Parallel processing with configurable threads
- ✅ CSV reading and validation
- ✅ Progress tracking and error reporting
- ✅ Export failed devices for retry

### WEPDeviceListPage
Handles UI-based and single device operations:
- ✅ UI navigation-based enrollment
- ✅ Single device SNR-based enrollment
- ✅ Device list management
- ✅ References BatchIOTEnrollmentPage for batch operations

---

## Contact & Support

For issues or questions about batch enrollment:
- Check logs in `log/` directory
- Review `test-output/failed_enrollments.csv`
- Verify tenant ID and CSV format
