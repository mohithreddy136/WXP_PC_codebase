package com.daasui.constants;

import java.io.File;
import com.basesource.action.CommonMethod;

public class ConstantPath {	
	public static final String EXCEL_PATH = System.getProperty("user.dir") + File.separator + "testdata" + File.separator + "testdata.xlsx";
	public static final String DOWNLOAD_PATH = System.getProperty("user.dir") + File.separator + "download" + File.separator;
	public static final String PREAPPROVED_DEVICE_PATH = System.getProperty("user.dir") + File.separator + "BulkUser" + File.separator + "Preapproved_Devices_IMEI_MAC_List.csv";
	public static final String LOGGER_FILE_PATH = System.getProperty("user.dir") + File.separator + "properties" + File.separator + CommonMethod.passFileName("logfile");
	public static final String SCREENSHOOT_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "test-screenshots" + File.separator;
	public static final String PROPERTIES_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "properties" + File.separator;
	public static final String CHROME_EXE_PATH = System.getProperty("user.dir") + File.separator + "lib" + File.separator + CommonMethod.passFileName("chromedriverfile");
	public static final String FIREFOX_EXE_PATH = System.getProperty("user.dir") + File.separator + "lib" + File.separator + "geckodriver.exe";
	public static final String IE_EXE_PATH = System.getProperty("user.dir") + File.separator + "lib" + File.separator + "IEDriverServer.exe";
	public static final String EDGE_PATH = System.getProperty("user.dir") + File.separator + "lib" + File.separator + "MicrosoftWebDriver.exe";
	public static final String DOWNLOAD_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "download";
	public static final String DESKTOP_VIDEO_PATH = System.getProperty("user.dir") + File.separator + "test-videos";
	public static final String REPORT_PATH = System.getProperty("user.dir") + File.separator + "test-report" + File.separator;
	public static final String FIREFOX_LOG_PATH = System.getProperty("user.dir") + File.separator + "log/FF.log";
	public static final String LOCALISATION_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "locales" + File.separator;
	public static final String CREDENTIALS_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "credentials" + File.separator;
	public static final String LOG_PATH = System.getProperty("user.dir") + File.separator + "log" + File.separator;
	public static final String IMPORT_PATH = System.getProperty("user.dir") + File.separator + "import" + File.separator;
	public static final String FILE_UPLOAD_PATH = System.getProperty("user.dir") + File.separator + "uploadFile" + File.separator;
	public static final String EMAIL_FILE_PATH = System.getProperty("user.dir") + File.separator + "test-Email" + File.separator;
	public static final String PAGECOUNT_PATH = System.getProperty("user.dir") + File.separator + "pageCount" + File.separator;
	public static final String UNIQUEPAGECOUNT_PATH = System.getProperty("user.dir") + File.separator + "uniquePageCount" + File.separator;
	public static final String LATENCY_PATH = System.getProperty("user.dir") + File.separator + "latencyTime" + File.separator;
	public static final String CAMPAIGN_COMPANY_LOGO = System.getProperty("user.dir") + File.separator + "uploadFile" + File.separator;
	public static final String SELF_REPORTING_CSV = System.getProperty("user.dir") + File.separator + "uploadFile" + File.separator;
	public static final String PULSE_COMPANY_LOGO = System.getProperty("user.dir") + File.separator + "uploadFile" + File.separator;
	public static final String SCRIPTS_PATH = System.getProperty("user.dir") + File.separator + "scripts" + File.separator;
	// CPIN Enrollment Paths
	public static final String CPIN_SCRIPT_BASE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "basesource" + File.separator + "utils" + File.separator + "cpin_enrollment" + File.separator;
	public static final String CPIN_INPUT_JSON_FILE = CPIN_SCRIPT_BASE_PATH + "input.json";
	public static final String CPIN_CONFIG_PROPERTIES = CPIN_SCRIPT_BASE_PATH + "cpin_test_config.properties";
	public static final String CPIN_SERIAL_NUMBERS_CSV = CPIN_SCRIPT_BASE_PATH + "serial_numbers.csv";
	public static final String CPIN_ENROLLMENT_METRICS_CSV = CPIN_SCRIPT_BASE_PATH + "enrolmentMetrics.csv";
	public static final String JSON_PATH = System.getProperty("user.dir") + File.separator + "testdata" + File.separator + "payload.json";

}
