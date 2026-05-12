package com.basesource.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import static com.basesource.action.CommonMethod.getEnvironmentSpecificData;

public class CSVFileReader {
	private static Logger LOGGER = LogManager.getLogger(CSVFileReader.class);
	private static CSVFileReader instance;
	private CSVReader csvReader;
	public static CSVWriter csvWriter;
	File csvFile;

	public CSVFileReader() {
		LOGGER.debug("CSV initializing");
	}

	public static CSVFileReader getInstance() {
		synchronized (CSVFileReader.class) {
			if (instance == null) {
				instance = new CSVFileReader();
			}
		}

		return instance;
	}

	public final String[][] getData(File csvFile) throws IOException {
		this.csvFile = csvFile;

		// Read all
		csvReader = new CSVReader(new FileReader(csvFile));
		List<String[]> list = csvReader.readAll();

		// Convert to 2D array
		String[][] dataArr = new String[list.size()][];
		dataArr = list.toArray(dataArr);

		return dataArr;
	}

	// Get latest Downloaded file form "user.dir/download" folder
	public File getLatestFilefromDir(String dirPath) {

		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	/*
	 * This method fetches all the records(except the headers) from the csv file and converts it to 2D array
	 */
	public final String[][] getDataWithoutHeader(File csvFile) throws IOException {
		try {
			this.csvFile = csvFile;

			// Read all
			csvReader = new CSVReader(new FileReader(csvFile));
			List<String[]> list = csvReader.readAll();
			list.remove(0);

			// Convert to 2D array
			String[][] dataArr = new String[list.size()][];
			dataArr = list.toArray(dataArr);

			return dataArr;
		} catch (Exception e) {
			LOGGER.error("Exception occured in getDataWithoutHeader(): " + e.getMessage());
			return null;
		}
	}

	/*
	 * This method fetches all the records from the csv file and converts it to 2D array
	 */
	public final String[][] getDataWithHeader(File csvFile) throws IOException {
		try {
			this.csvFile = csvFile;

			// Read all
			csvReader = new CSVReader(new FileReader(csvFile));
			List<String[]> list = csvReader.readAll();

			// Convert to 2D array
			String[][] dataArr = new String[list.size()][];
			dataArr = list.toArray(dataArr);
			csvReader.close();
			return dataArr;
		} catch (Exception e) {
			LOGGER.error("Exception occured in getDataWithHeader(): " + e.getMessage());
			return null;
		}
	}

	/*
	 * This method fetches all the records from the csv file and converts it to 2D array
	 */
	public final void writeDataCSV(File csvFile, String text1, String text2, String text3) throws IOException {
		this.csvFile = csvFile;
		csvWriter = new CSVWriter(new FileWriter(csvFile));
		String[] header = { text1, text2, text3 };
		csvWriter.writeNext(header);
		csvWriter.close();
	}
	
	/*
	 * This method fetches all the records from the UI and writes in the csv file in different rows (one single column)
	 */
	   public void writeDataCSVmultiple(File csvFile, List<String> sample) throws IOException {
	        this.csvFile = csvFile;
	        csvWriter = new CSVWriter(new FileWriter(csvFile));
	        try {
	            for (String data : sample) {
	                String[] row = {data}; // Create a new String array for each row
	                csvWriter.writeNext(row); // Write the row to CSV
	            }
	        } finally {
	            if (csvWriter != null) {
	                csvWriter.close(); // Close the CSVWriter after writing all data
	            }
	        }	
}
	
	/** This method fetches all the records from the csv file and converts it to 2D array
	 * @param csvFile: File path
	 * @param text : Text which needs to written.
	 * @throws IOException
	 */
	public final void writeDataCSV2D(File csvFile, String text) throws IOException {
		this.csvFile = csvFile;
		csvWriter = new CSVWriter(new FileWriter(csvFile,true));
		String[] header = { text};
		csvWriter.writeNext(header);
		csvWriter.close();
	}

	/**
	 * This method writes data into csv
	 * 
	 * @param csvFile - File to which data needs to be written into
	 * @param name - Name of user
	 * @param email - Email of user
	 * @param portalRole - Portal role
	 * @param hpFormsRole - HP Forms role
	 * @param hpInstantRole - HP Instant help role
	 * @throws IOException
	 */
	public final void writeDataCSVWorkflow(File csvFile, String name, String email, String portalRole, String hpFormsRole, String hpInstantRole) throws IOException {
		this.csvFile = csvFile;
		csvWriter = new CSVWriter(new FileWriter(csvFile));
		String[] header = { name, email, portalRole, hpFormsRole, hpInstantRole };
		csvWriter.writeNext(header);
		csvWriter.close();
	}

	/**
	 * This method writes data to csv file having a single header column
	 * 
	 * @param csvFile - file
	 * @param header - header
	 * @param data - data in column
	 * @throws IOException
	 */
	public final void writeDataToCSVHavingSingleColumn(File csvFile, String header, String data) throws IOException {
		this.csvFile = csvFile;
		csvWriter = new CSVWriter(new FileWriter(csvFile));
		String[] headerArray = { header };
		String[] dataArray = { data };
		csvWriter.writeNext(headerArray);
		csvWriter.writeNext(dataArray);
		csvWriter.close();
	}
	
	/**
	 *This method writes multiple data records to csv file 
	 * 
	 * @param csvFile - file
	 * @param userData - list of multiple data add to CSV
	 * @throws IOException
	 */
	public final void writeMultipleDataToCSV(File csvFile, List<String[]> userData) throws IOException {
		this.csvFile = csvFile;
		csvWriter = new CSVWriter(new FileWriter(csvFile));
		csvWriter.writeAll(userData);
		csvWriter.close();
	}
	
	/**
	 * This method writes data to csv file having a multiple header column
	 * @param csvFile - csv file which is to be used
	 * @param header - header array
	 * @param data - data array
	 * @throws IOException
	 */
	public final void writeDataToCSVHavingMultipleColumn(File csvFile, String[] header, String[] data) throws IOException {
		this.csvFile = csvFile;
		csvWriter = new CSVWriter(new FileWriter(csvFile));
		csvWriter.writeNext(header);
		csvWriter.writeNext(data);
		csvWriter.close();
	}
	
	/**
	 * This method updates data to csv file for specific row column cell
	 * @param csvFile - csv file which is to be used
	 * @param row - row to be updated
	 * @param col - column to be updated
	 * @throws IOException
	 */
	public final void updateCSV(File csvFile, String replace, int row, int col) throws IOException {
		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8), ',');
		List<String[]> csvBody = reader.readAll();
		csvBody.get(row)[col] = replace;
		reader.close(); 
		OutputStreamWriter outwriter = new OutputStreamWriter(new FileOutputStream(csvFile), StandardCharsets.UTF_8);
		StringBuilder builder = new StringBuilder();
		for(String[] s: csvBody) {
			String listString = (String.join(",", s))+"\n";
			outwriter.write(listString);
			
		}
		outwriter.flush();
		outwriter.close();
	}

	public void writeToCSVLoadtime(File csvFile, HashMap<String, String> data,String date) throws IOException {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile, true));
		    CSVReader reader = new CSVReader(new FileReader(csvFile));
		  String[] header = {"Page Name", "Load Time", "Date"};
		  String[] firstRow = reader.readNext();
		if (firstRow != null) {
			if(Arrays.equals(firstRow, header) == false) {
				csvWriter.writeNext(header);
			}
		}else{
			csvWriter.writeNext(header);
		}
			for (HashMap.Entry<String, String> entry : data.entrySet()) {
				String[] row = {entry.getKey(), entry.getValue(), date};
				csvWriter.writeNext(row);
			}
			csvWriter.close();
		}
	
		
	// New enum (place in same file `CSVFileReader.java`, outside the method but inside the class or as a top-level if preferred)
	public enum ACFileGenerationMode {
	    EMPTY_FILE,            // No header, no rows
	    HEADER_ONLY,           // Header only
	    VALID_ROWS,            // Fully populated rows
	    MISSING_MANDATORY_VALUES // Rows with specified mandatory columns intentionally blank
	}
	
	
	


	// Add (or extend) this enum inside `CSVFileReader` (or adjust existing one)
	public enum ACGenerationVariant {
	    EMPTY_FILE,                // Completely empty file (no header, no rows)
	    VALID_ROWS,                // Header + fully populated rows
	    MISSING_MANDATORY_VALUES,  // Header + rows with mandatory columns blank
	    WITHOUT_MANDATORY_COLUMNS  // Header excludes mandatory columns + rows populated
	}
	
	// Fetch endCustomerName and TenantDisplayId based on environment for ActiveCare testdata generation.
	private static Map<String,String> resolveEnvTenantPair() {
	    String env = Optional.ofNullable(System.getProperty("environment"))
	            .orElseGet(() -> System.getenv("ENVIRONMENT"));
	    if (env == null) env = "";
	    switch (env) {
	        case "US-STAGE-WEP":
	            return Map.of("endCustomerName","Name1","tenantDisplayId","123");
	        case "US-Dev":
	            return Map.of("endCustomerName","Name2","tenantDisplayId","456");
	        case "US-QA":
	            return Map.of("endCustomerName","Name3","tenantDisplayId","789");
	        // Add more exact matches as needed
	        default:
	            return Map.of(
	                "endCustomerName","DefaultName",
	                "tenantDisplayId","TEN_" + UUID.randomUUID().toString().substring(0,7)
	            );
	    }
	}
	
	
	public static Map<String, Object> generateActiveCareCsvByOrderType_v3(
	        String orderType,
	        int rowCount,
	        String serialNumber,
	        String endCustomerNameFromProperties,
	        ACGenerationVariant variant,
	        Collection<String> mandatoryColumnsToBlank // optional override for blanks
	) throws IOException {
        String env = System.getProperty("environment");
        String endCustomerName = getEnvironmentSpecificData(env, endCustomerNameFromProperties);
	    if (orderType == null || orderType.trim().isEmpty()) {
	        String errorMsg = "orderType is required but was null or empty. " +
	                "This typically occurs when TestNG parallel execution or CI/CD pipeline configuration " +
	                "prevents @BeforeMethod from setting ThreadLocal values properly. " +
	                "Ensure that @TestMetadata annotation is present on the test method with currentOrderType set, " +
	                "and verify TestNG configuration (parallel execution, thread-count settings).";
	        throw new IllegalArgumentException(errorMsg);
	    }
	    Objects.requireNonNull(variant, "variant required");
	    if (rowCount < 0) {
	        throw new IllegalArgumentException("rowCount must be >= 0");
	    }
	    
	    String filePath = (System.getProperty("user.dir") + "/import/");
	    // 1. Define per-orderType columns (extend as needed)
	    Map<String, List<String>> ORDER_TYPE_COLUMNS = new HashMap<>();
	    ORDER_TYPE_COLUMNS.put("New Order - Active Care",
	            List.of("ObjectOfServiceSerialNumber","EndCustomerName","FCPKSerialNumber","FCPKProductID","FCPKServiceStartDate","FCPKServiceEndDate","EndCustomerCountryCode","Registered","Deactivated","DeactivatedReason","PackDescription"));
	    ORDER_TYPE_COLUMNS.put("New Order - Premium",
	            List.of("ObjectOfServiceSerialNumber","EndCustomerName","FCPKSerialNumber","FCPKProductID","FCPKServiceStartDate","FCPKServiceEndDate","EndCustomerCountryCode","Registered","Deactivated","DeactivatedReason","PackDescription"));
	    ORDER_TYPE_COLUMNS.put("New Order - Premium Plus",
	            List.of("ObjectOfServiceSerialNumber","EndCustomerName","FCPKSerialNumber","FCPKProductID","FCPKServiceStartDate","FCPKServiceEndDate","EndCustomerCountryCode","Registered","Deactivated","DeactivatedReason","PackDescription"));
	    ORDER_TYPE_COLUMNS.put("New Order - Out Of Band Remediation",
	            List.of("ObjectOfServiceSerialNumber","EndCustomerName","FCPKSerialNumber","FCPKProductID","FCPKServiceStartDate","FCPKServiceEndDate","EndCustomerCountryCode","Registered","Deactivated","DeactivatedReason","PackDescription"));
	    ORDER_TYPE_COLUMNS.put("Device Transfer",
	            List.of("ObjectOfServiceSerialNumber","TenantDisplayId","Region"));
	    ORDER_TYPE_COLUMNS.put("Reseller Update",
	            List.of("serial_number","reseller_branch_identifier","region_code","reseller_headquarter_identifier"));
	    ORDER_TYPE_COLUMNS.put("Remove License",
	            List.of("ObjectOfServiceSerialNumber","Region","PlanDisplayId"));
	    ORDER_TYPE_COLUMNS.put("Data Collection",
	            List.of("SerialNumber"));

	    // 2. Mandatory columns per order type (adjust as business rules evolve)
	    Map<String, Set<String>> ORDER_TYPE_MANDATORY = new HashMap<>();
	    ORDER_TYPE_MANDATORY.put("New Order - Active Care",
	            Set.of("ObjectOfServiceSerialNumber","FCPKSerialNumber","FCPKServiceStartDate","FCPKServiceEndDate","EndCustomerCountryCode","Registered"));
	    ORDER_TYPE_MANDATORY.put("New Order - Premium",
	            Set.of("ObjectOfServiceSerialNumber","FCPKSerialNumber","FCPKServiceStartDate","FCPKServiceEndDate","EndCustomerCountryCode","Registered"));
	    ORDER_TYPE_MANDATORY.put("New Order - Premium Plus",
	            Set.of("ObjectOfServiceSerialNumber","FCPKSerialNumber","FCPKServiceStartDate","FCPKServiceEndDate","EndCustomerCountryCode","Registered"));
	    ORDER_TYPE_MANDATORY.put("New Order - Out Of Band Remediation",
	            Set.of("ObjectOfServiceSerialNumber","FCPKSerialNumber","FCPKServiceStartDate","FCPKServiceEndDate","EndCustomerCountryCode","Registered"));
	    ORDER_TYPE_MANDATORY.put("Device Transfer",
	            Set.of("ObjectOfServiceSerialNumber","EndCustomerName","FCPKServiceStartDate","FCPKServiceEndDate","PreviousContractId"));
	    ORDER_TYPE_MANDATORY.put("Reseller Update",
	            Set.of("serial_number","reseller_branch_identifier","region_code","reseller_headquarter_identifier"));
	    ORDER_TYPE_MANDATORY.put("Remove License",
	            Set.of("ObjectOfServiceSerialNumber","Region","PlanDisplayId"));
	    ORDER_TYPE_MANDATORY.put("Data Collection",
	            Set.of("SerialNumber"));
	
	    List<String> allColumns = ORDER_TYPE_COLUMNS.get(orderType);
	    if (allColumns == null) {
	        throw new IllegalArgumentException("Unsupported orderType: " + orderType);
	    }
	    Set<String> mandatory = ORDER_TYPE_MANDATORY.getOrDefault(orderType, Set.of());
	
	    // 3. Resolve columns based on variant
	    List<String> finalColumns;
	    switch (variant) {
	        case WITHOUT_MANDATORY_COLUMNS:
	            finalColumns = new ArrayList<>();
	            for (String c : allColumns) {
	                if (!mandatory.contains(c)) {
	                    finalColumns.add(c);
	                }
	            }
	            break;
	        case EMPTY_FILE:
	        case VALID_ROWS:
	        case MISSING_MANDATORY_VALUES:
	        default:
	            finalColumns = new ArrayList<>(allColumns);
	    }
	
	    // 4. Filename
	    String sanitizedOrderType = orderType.replaceAll("[^A-Za-z0-9]+", "_");
	    deleteCSVFiles(filePath, sanitizedOrderType);
	    String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	    String fileName = sanitizedOrderType + "_" + timestamp + ".csv";
	    Path outPath = Paths.get(filePath + fileName);
	
	    // 5. Prepare result container
	    Map<String, Object> result = new HashMap<>();
	    result.put("orderType", orderType);
	    result.put("variant", variant.name());
	    result.put("outputCsvFilename", fileName);
	    result.put("columns", finalColumns);
	
	    // Variant: EMPTY_FILE (create zero-byte file)
	    if (variant == ACGenerationVariant.EMPTY_FILE) {
	        Files.deleteIfExists(outPath);
	        Files.createFile(outPath);
	        result.put("rows", List.of());
	        result.put("result_data", Map.of());
	        return result;
	    }
	
	    // Dates defaults
	    SimpleDateFormat dateFmt = new SimpleDateFormat("dd-MM-yyyy");
	    Calendar calStart = Calendar.getInstance();
	    calStart.add(Calendar.YEAR, -1);
	    String defaultStart = dateFmt.format(calStart.getTime());
	    Calendar calEnd = Calendar.getInstance();
	    calEnd.add(Calendar.YEAR, 1);
	    String defaultEnd = dateFmt.format(calEnd.getTime());
	
	    // Set of mandatory columns to blank (if variant demands)
	    Set<String> blankSet;
	    if (variant == ACGenerationVariant.MISSING_MANDATORY_VALUES) {
	        if (mandatoryColumnsToBlank != null && !mandatoryColumnsToBlank.isEmpty()) {
	            blankSet = new HashSet<>(mandatoryColumnsToBlank);
	        } else {
	            blankSet = new HashSet<>(mandatory); // blank all mandatory if not specified
	            }
	    } else {
	        blankSet = Set.of();
	    }
	
	    Random rnd = new Random();
	    // 6. Helper to generate column value
	    Function<String,String> gen = col -> {
	        switch (col) {
	            case "ObjectOfServiceSerialNumber":
	            	return (serialNumber != null && !serialNumber.isBlank())
	                        ? serialNumber
	                        : "SRAUTO" + (100000 + rnd.nextInt(900000));
	            case "SerialNumber":
	            	return (serialNumber != null && !serialNumber.isBlank())
	                        ? serialNumber
	                        : "SRAUTO" + (100000 + rnd.nextInt(900000));
	            case "EndCustomerName":
	            	return (endCustomerName != null && !endCustomerName.isBlank())
	                        ? endCustomerName
	                        : "EC_" + UUID.randomUUID().toString().substring(0, 8);
	            case "FCPKServiceStartDate":
	                return defaultStart;
	            case "FCPKServiceEndDate":
	                return defaultEnd;
	            case "EndCustomerCountryCode":
	                return (env != null && env.toUpperCase().contains("US")) ? "IN" : "FR";
	            case "Registered":
	                return "1";
	            case "Deactivated":
	                return "0";
	            case "DeactivatedReason":
	                return "";
	            case "PackDescription":
                    return "AUTO_PACK_" + (1000 + rnd.nextInt(9000));
                case "TenantDisplayId":
                    String tenantDisplayIdKey = endCustomerNameFromProperties+"_TN_DISPLAY_ID";
                    String tenantDisplayId = getEnvironmentSpecificData(System.getProperty("environment"), tenantDisplayIdKey);
                    return (orderType.equals("Device Transfer") && StringUtils.isNotEmpty(tenantDisplayId)) ? tenantDisplayId : "TEN_" + UUID.randomUUID().toString().substring(0, 7);
                case "Region":
                    String region = env.split("-")[0].trim();
                    return StringUtils.isNotEmpty(region) ? region : "REG_" + (10 + rnd.nextInt(89));
	            case "serial_number":
	            	return (serialNumber != null && !serialNumber.isBlank())
	                        ? serialNumber
	                        : "SRAUTO" + (100000 + rnd.nextInt(900000));
	            case "reseller_branch_identifier":
	                return "HQ-RBI-" + (1000 + rnd.nextInt(9000));
	            case "region_code":
	                return (env != null && env.toUpperCase().contains("US")) ? "US" : "EU";
	            case "reseller_headquarter_identifier":
	                return "HQ-PCE-" + (1000 + rnd.nextInt(9000));
	            case "PlanDisplayId":
	                return "PLAN_" + (1000 + rnd.nextInt(9000));
	            default:
	                return "VAL_" + UUID.randomUUID().toString().substring(0, 6);
	        }
	    };
	
	    List<String[]> generatedRows = new ArrayList<>();
	
	    // 7. Write header + data
	    try (BufferedWriter writer = Files.newBufferedWriter(outPath, StandardCharsets.UTF_8)) {
	        writer.write(String.join(",", finalColumns));
	        writer.newLine();
	
	        for (int r = 0; r < rowCount; r++) {
	            String[] row = new String[finalColumns.size()];
	            for (int i = 0; i < finalColumns.size(); i++) {
	                String col = finalColumns.get(i);
	                if (blankSet.contains(col)) {
	                    row[i] = ""; // force blank
	                } else {
	                    row[i] = gen.apply(col);
	                }
	            }
	            // Ensure mandatory columns populated in VALID_ROWS (if present)
	            if (variant == ACGenerationVariant.VALID_ROWS) {
	                for (int i = 0; i < finalColumns.size(); i++) {
	                    String col = finalColumns.get(i);
	                    if (mandatory.contains(col) && (row[i] == null || row[i].isBlank())) {
	                        row[i] = "FIX_" + UUID.randomUUID().toString().substring(0, 6);
	                    }
	                }
	            }
	            generatedRows.add(row);
	            writer.write(String.join(",", row));
	            writer.newLine();
	        }
	    }
	
	    // 8. Build column-wise map
	    Map<String, List<String>> resultData = new LinkedHashMap<>();
	    for (int c = 0; c < finalColumns.size(); c++) {
	        List<String> colVals = new ArrayList<>();
	        for (String[] row : generatedRows) {
	            colVals.add(row[c]);
	        }
	        resultData.put(finalColumns.get(c), colVals);
	    }
	
	    result.put("rows", generatedRows);
	    result.put("result_data", resultData);
	    result.put("mandatoryColumnsDefined", new ArrayList<>(mandatory));
	    result.put("mandatoryColumnsBlanked", new ArrayList<>(blankSet));
	
	    return result;
	}


    /**
     * This method deletes all CSV files in the specified directory that match the
     * given filename pattern.
     *
     * @param directoryPath   The path to the directory where CSV files are located.
     * @param fileNamePattern The pattern that filenames must contain to be deleted.
     * @return true if any files were deleted, false otherwise.
     */
    public static boolean deleteCSVFiles(String directoryPath, String fileNamePattern) {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles((d, name) -> name.endsWith(".csv") && name.contains(fileNamePattern));
        boolean deleted = false;
        if (files != null) {
            for (File file : files) {
                if (file.delete()) {
                    deleted = true;
                }
            }
        }
        return deleted;
    }
}