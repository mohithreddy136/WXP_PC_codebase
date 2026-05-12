package com.daasui.constants;

public class DeviceVariables {
	public static final String ImportDevice_Group_UPLOAD = "Groups.csv";
	public static final String DEVICESWITHCORRECTSERIALNUMBER_UPLOAD = "Devices.csv";
	public static final String DEVICESWITHCORRECTSERIALNUMBER_UPLOAD_COPY = "Devices_2.csv";
	public static final String DEVICES_TITLE = "Devices";
	public static final String DEVICESWITHDUPLICATEASSETTAG_D1_UPLOAD = "DeviceswithSameAssetTag_D1.csv";
	public static final String DEVICESWITHDUPLICATEASSETTAG_D2_UPLOAD = "DeviceswithSameAssetTag_D2.csv";
	public static final String DEVICESWITHDUPLICATESERIALNUMBER_UPLOAD = "DeviceswithSameSerial.csv";
	public static final String DEVICESWITHDUPLICATESERIALNUMBER_ASSET_TAG_UPLOAD = "DuplicateSerialNumberandAssetTag.csv";
	public static final String DEVICESWITHCORRECTSERIALNUMBER_UPLOAD_TO_DELETE = "device_unenroll_remove_file.csv";
	public static final String VIRTUALMACHINE_UPLOAD = "VirtualMachine.csv";
	public static final String DEVICESWITHCORRECTSERIALNUMBERZTE_UPLOAD = "devices_zte.csv";
	public static final String DEVICESWITHCORRECTSERIALNUMBERZTE_UPLOAD_SNR = "devices_zte_snr.csv";
	public static final String DEVICESWITHOUTSERIALNUMBER_UPLOAD = "DevicesWithoutSerialNumber.csv";
	public static final String ASSETWITHOUTSERIAL_NUMBER_UPLOAD = "AssetWithoutSerialNumber.csv";
	public static final String ASSETWITHWRONGHEADER_UPLOAD = "AssetWithIncorrectHeader.csv";
	public static final String ASSETWITHWRONGSERIALNUMBERFORMAT_UPLOAD = "AssetWithWrongSNumFormat.csv";
	public static final String WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE = "wx-navigation-improvements";
	public static final String DEVICEWITHCUSTOMFIELDS_UPLOAD = "DeviceCustomFields.csv";
	public static final String ASSET_UPLOAD = "AssetImport.csv ";
	public static final String ASSETSFORVALIDATIONS_UPLOAD = "AssetsForValidations.csv";
	public static final String ASSETSFORLSVALIDATIONS_UPLOAD = "DeviceForLS.csv";

	public static final String DEVICESWITHINCORRECTSERIALNUMBER_UPLOAD = "DevicesWithIncorrectSerialNumber.csv";
	public static final String DEVICE_UN_ENROLLED_ERROR_TEXT = "Check if the Device has been un-enrolled.";
	public static final String DASHBOARD_CHART_REPORTS_NO_DATA = "No data to display";
	public static final String UILOGURL = "ui/logs";
	public static final String COMPANY_NAME_CONFIGURATION_MANAGEMENT = "test_asdfg@mailinator.com";
	public static final String COMPANY_NAME_SURECLICKUS = "sureclickuscompany@yopmail.com";
	public static final String PAGINATION_COUNT_25 = "25";
	public static final String PAGINATION_COUNT_50 = "50";
	public static final String PAGINATION_COUNT_100 = "100";
	public static final String TEST_DEVICE_SERIAL_NUM_EUSTAGE = "0002770YDL";
	public static final String EUPROD_DEVICE_SERIAL_NUM = "5CD1494GYV";
	public static final String USSTG_DEVICE_NAME = "DESKTOP-95N894E";
	public static final String EUSTG_DEVICE_NAME = "DESKTOP-DSVO62C";
	public static final String USPRD_DEVICE_NAME = "HP-5CG4163QH9";
	public static final String EUPRD_DEVICE_NAME = "DESKTOP-KL89O22";

	// SNOW Admin
	public static final String SNOW_USERNAME = "admin";
	public static final String SNOW_PASSWORD = "serviceNOW@123";

	// Root account details
	public static final String MSP_NAME = "MSP04";
	public static final String COUNTRY_NAME = "India";
	public static final String REGION_NAME = "US";

	public static final String DEVICE_COMPANY = "UIAutomation_DoNotDelete";
	public static final String DEVICE_IMPORT_COMPANY = "Chromebook Company";
	public static final String DEVICE_COMPANY_ACCESSORIES_PERIPHERALS = "device test";

	// Device page column list
	public static final int Serial_Number = 0;
	public static final int plan = 1;
	public static final int Device_Name = 2;
	public static final int Type = 3;
	public static final int Enrollment_User = 4;
	public static final int Company = 5;
	public static final int Location = 6;
	public static final int Enrolled = 7;
	public static final int Last_Activity = 8;
	public static final int Asset_Tag = 9;
	public static final int HP_Sure_Click_Advanced = 10;
	public static final int BYOD = 11;
	public static final int Cost_Center = 12;
	public static final int Department = 13;
	public static final int Manufacturer = 14;
	public static final int Model = 15;
	public static final int Alias = 16;
	public static final int Operating_System = 17;
	public static final int Operating_System_Language = 18;
	public static final int Product = 19;
	public static final int Store_Number = 20;
	public static final int Last_Signed_In_User	 = 21;
	public static final int Auto_Update_Expiration =22;
	public static final String DEVICE_ADD_SERIAL_NUMBER = "AutomationRemoveDevice1234";
	public static final String DEVICE_ADD_ALIAS_NAME = "AutomationRemoveDevice1234";

	public static final int Serial_NumberV3 = 1;
	public static final int Device_NameV3 = 2;
	public static final int TypeV3 = 3;
	public static final int planV3 = 4;
	public static final int CompanyV3 = 5;
	public static final int EnrolledV3 = 6;
	public static final int Last_ActivityV3 = 7;
	public static final int AliasV3 = 8;
	public static final int LifeCycle_StatusV3 = 10;
	public static final int Device_RoleV3 = 11;
	public static final int Enrollment_userV3 = 12;
	public static final int Last_Signed_In_UserV3= 13;
	public static final int LocationV3 = 14;
	public static final int Asset_TagV3 = 15;
	public static final int HP_Sure_Click_AdvancedV3 = 16;
	public static final int BYODV3 = 17;
	public static final int Cost_CenterV3 = 18;
	public static final int DepartmentV3 = 19;
	public static final int ManufacturerV3 = 20;
	public static final int ModelV3 = 21;
	public static final int Operating_SystemV3 = 22;
	public static final int Operating_System_LanguageV3 = 23;
	public static final int ProductV3 = 24;
	public static final int Store_NumberV3 = 25;
	public static final int Auto_Update_ExpirationV3 =27;


	// variable for device page
	public static final String BLANK_SERIALNUMBER_ERROR_MESSAGE = "Serial Number (mandatory) is required to import devices.";
	public static final String FORBIDDEN_STARTING_STRINGS_ERROR_MESSAGE1 = "Values cannot start with the following characters: (=,+,-,@)";
	public static final String FORBIDDEN_STARTING_STRINGS_ERROR_MESSAGE2 = "Serial Number (mandatory) contains special characters.";
	public static final String WRONG_DATE_FORMAT_ERROR_MESSAGE = "Please make sure Manufacture Date (mm/dd/yyyy) is in the right format";
	public static final String SKIP_MESSAGE = "Skip";
	public static final String DEVICE_NAME_WARRANTY = "HBUW305119";
	public static final String DEVICE_ID_WARRANTY = "cKun_L8EgfMC-xMl6HMGvEndeTcGIUH9DFjtuvw37I4";
	public static final String DEVICE_NAME_STABLE = "0008060039";
	public static final String DEVICE_NAME_STAGING = "5CG737325Q";
	public static final String DEVICE_NAME_PRODUCTION = "5CD8083B35";
	public static final String DEVICE_STABLE_SW_UPDATES = "5CD8072Y10";
	public static final String DEVICE_STAGING_SW_UPDATES = "MJHRWYB";
	public static final String DEVICE_PROD_SW_UPDATES = "5CD8072Y10";
	public static final String INCORRECT_SEARCH_STRING = "rrur6r";
	public static final String SERIAL_NUMBER_SEARCH = "1";
	public static final String NAME_SEARCH = "Fake";
	public static final String USER_SEARCH = "test";
	public static final String COMPANY_SEARCH = "company";
	public static final String LOCATION_SEARCH = "pune";
	public static final String ASSETTAG_SEARCH = "AST";
	public static final String COSTCENTER_SEARCH = "cc";
	public static final String DEPARTMENT_SEARCH = "d";
	public static final String IMEI_SEARCH = "imei";
	public static final String MANUFACTURER_SEARCH = "hp";
	public static final String MODEL_SEARCH = "hp";
	public static final String ALIAS_SEARCH = "a";
	public static final String OPERATING_SYSTEM_SEARCH = "windows";
	public static final String OPERATING_SYSTEM_ARCHITECTURE_SEARCH = "amd";
	public static final String OPERATING_SYSTEM_LANGUAGE_SEARCH = "English";
	public static final String PRODUCT_SEARCH = "lenovo";
	public static final String STORE_NUMBER_SEARCH = "s";

	public static final String UNIQUEDEVICETYPEAPI = "/api/2.0/tenants/uniquedevicetype";

	// Chrome Enterprise Integration
	public static final String EMM_MSP_NAME = "EMMTeamAutomation";
	public static final String EMM_MSP_ADMIN_NAME = "Staging MSP US Partner new";
	public static final String EMM_PARTNER_NAME = "partnerdashboardautomation";
	public static final String CHROME_SERIAL_NUMBER = "Serial number";
	public static final String CHROME_MODEL = "Model";
	public static final String CHROME_VERSION = "Chrome version";
	public static final String CHROME_OWNERSHIP = "Company";
	public static final String CHROME_VERSION_STRING  = "Chrome OS ";
	public static final String CHROME_HW_URLSTRING  = "&flyout=hardware";
	public static final String EMM_COMPANY1_NAME = "EMM CompanyTest1-DO NOT DELETE";
	public static final String EMM_COMPANY2_NAME = "EMM CompanyTest2-DO NOT DELETE";


	//Chrome Enterprise Integration device filter
	public static final String CLIENT_APPLICATION_CHROME_UNKNOWN = "Unknown";
	public static final String CLIENT_APPLICATION_CHROME_VERSION = "Version";
	public static final String CLIENT_APPLICATION_GOOGLE_CHROME = "Google Admin Console";

	// EMM Strings
	public static final String INTUNE = "Intune";

	// Intune Fake device parameters
	public static final String INTUNE_DEVICE_NAME = "Device name";
	public static final String INTUNE_OWNERSHIP = "Ownership";
	public static final String INTUNE_SERIAL_NUMBER = "Serial number";
	public static final String INTUNE_MANUFACTURE = "Device manufacturer";
	public static final String INTUNE_MODEL = "Device model";
	public static final String IOS_OS = "IOS";
	public static final String ANDROID_OS = "ANDROID";
	public static final String WINDOWS_OS = "WINDOWS";
	public static final String MAC_OS = "MACOS";
	public static final String CHROMEBOOK = "CHROMEBOOK";
	public static final String SEARCHSERVICEBODY = "{\"query\":\"{\\\"query\\\":{\\\"match_all\\\":{}},\\\"_source\\\":[\\\"uid\\\",\\\"alias\\\",\\\"deviceType.en\\\",\\\"deviceType.en.name\\\",\\\"deviceType.en.value\\\",\\\"deviceOwner\\\",\\\"deviceOwnerName\\\",\\\"companyName\\\",\\\"tenantId\\\",\\\"location\\\",\\\"deviceClientApplication.name\\\",\\\"deviceClientApplicationName\\\",\\\"deviceClientApplicationNames\\\",\\\"lastSeen\\\",\\\"assetTag\\\",\\\"bromiumStatus.en\\\",\\\"bromiumStatus.en.name\\\",\\\"bromiumStatus.en.value\\\",\\\"byod\\\",\\\"costCenter\\\",\\\"department\\\",\\\"imei\\\",\\\"manufacturer\\\",\\\"deviceModel\\\",\\\"name\\\",\\\"operatingSystem\\\",\\\"osArchitecture\\\",\\\"osLanguage\\\",\\\"productNumber\\\",\\\"serialNumber\\\",\\\"storeNumber\\\",\\\"customFields\\\"],\\\"from\\\":0,\\\"size\\\":25,\\\"sort\\\":[]}\",\"tenant_ids\":[],\"index_list\":[\"idmdevices\"],\"search_type\":\"tenanted\"}";
	public static final String SEARCHSERVICEBODYPENINGTAB = "{\"query\":\"{\\\"query\\\":{\\\"bool\\\":{\\\"must\\\":[{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"status.en.name.keyword\\\":\\\"Pre_enrolled\\\"}},{\\\"match\\\":{\\\"status.en.name.keyword\\\":\\\"Ready\\\"}},{\\\"match\\\":{\\\"status.en.name.keyword\\\":\\\"Error\\\"}}]}}]}},\\\"_source\\\":[\\\"uid\\\",\\\"alias\\\",\\\"assetTag\\\",\\\"name\\\",\\\"status.en\\\",\\\"status.en.name\\\",\\\"status.en.value\\\",\\\"deviceType.en\\\",\\\"deviceType.en.name\\\",\\\"deviceType.en.value\\\",\\\"timeReadyTs\\\",\\\"createdTs\\\",\\\"companyName\\\",\\\"tenantId\\\",\\\"lastSeen\\\",\\\"serialNumber\\\",\\\"customFields\\\"],\\\"from\\\":0,\\\"size\\\":100,\\\"sort\\\":[{\\\"serialNumber.sort_en\\\":{\\\"order\\\":\\\"ASC\\\",\\\"unmapped_type\\\":\\\"string\\\"}},{\\\"meta.created\\\":{\\\"unmapped_type\\\":\\\"long\\\",\\\"order\\\":\\\"DESC\\\"}}]}\",\"tenant_ids\":[],\"index_list\":[\"idmdevices\"],\"search_type\":\"tenanted\"}";
	public static final String DEVICE_UNENROLL_CSV = "device_unenroll_remove_file.csv";
	public static final String DEVICE_UNENROLL_REMOVE_CSV_HEADER = "Serial Number (mandatory)";
	public static final String DEVICE_REMOVE_CSV = "device_remove_file.csv";
	public static final String PRODUCT_NAME = "HP TechPulse";
	public static final String WEX_PRODUCT_NAME = "HP Workforce Experience Platform";

	// ZTE Parameters
	public static final String NO_ENROLLMENT = "no_enroll";
	public static final String AUTO_ENROLLEMNT = "auto_enroll";
	public static final String TOKEN_URL = "api/v1/";
	public static final String TOKEN_URL_RESOURCE = "oauth2/token";
	public static final String ZTE_SNOW_URL = "services/ccc-hpsn/1.0/devices";
	public static final String ZTE_DEVICE_URL = "api/2.0/tenants/";
	public static final String ZTE_DEVICE_RESOURCE = "/assets?type=zte";
	public static final String SINGLE_DEVICE_REMOVE = "api/2.0/devices/";
	public static final String DEVICE_REMOVE = "api/2.0/devices/bulk-delete";
	public static final String LAST_RESTART_DATE = "api/v1/reports/1.0/instance/magpi/reports/winperformance/details/type/grid";

	public static final String FLIP_TOGGLE = "flip-lock-and-erase";
	public static final String BORNONDATE_TOGGLE = "born-on-date-from-idm";
	public static final String Network_Error_Toggle = "network-networkassmtv2-networkerrors";

	public static final String BSOD_Toggle = "device-details-bsod-list";
	public static final String DEVICETIMELINE_Toggle = "device-timeline-api";

	//Import wrong data error messages
	public static final String ERROR_FRST_PART= "There was an error during the import process of file ";
	public static final String ERROR_LST_PART= ". Please try again.";
	public static final String ASSETWITHWRONGHEADER_UPLOAD_ERR_MSG = "Please add Serial Number (Serial Number or Asset Tag mandatory) the headers in the file AssetWithIncorrectHeader.csv.";
	public static final String ASSETWITHOUTSERIAL_NUMBER_UPLOAD_ERR_MSG = "There was a problem processing assets. Please check the logs for more details";
	public static final String ASSETWITHWRONGHEADER_LOG_ERR_MSG = "Imported CSV file AssetWithIncorrectHeader.csv is missing headers for required fields. Please add correct headers and try again.";
	public static final String ASSETWITHOUTSERIAL_NUMBER_LOG_ERR_MSG = "There was an error during the import process of file AssetWithoutSerialNumber.csv. Please try again.";
	public static final String 	ASSETWITHWRONGSERIALNUMBERFORMAT_UPLOAD_ERR_MSG= "There was a problem processing assets. Please check the logs for more details.";
	public static final String 	ASSETWITHWRONGSERIALNUMBERFORMAT_LOG_ERR_MSG= "There was an error during the import process of file AssetWithWrongSNumFormat.csv. Please try again.";
	public static final String DUPLICATE_ASSET_SERIAL_UPLOAD_LOG_MSG = "Your assets from DuplicateSerialNumberandAssetTag.csv were imported successfully. Please check the logs for more details.";

	public static final String VALID_ASSET_UPLOAD_LOG_MSG = "Your assets from Devices.csv were imported successfully. Please check the logs for more details.";
	public static final String VALID_ASSET_UPLOAD_LOG_MSG_FIRST_PART_MSG = "Your assets from ";
	public static final String VALID_ASSET_UPLOAD_LOG_MSG_LAST_PART_MSG ="were imported successfully. Please check the logs for more details.";
	public static final String VALID_MULTI_ASSET_UPLOAD_LOG_MSG = "Your assets from Devices_2.csv were imported successfully. Please check the logs for more details.";
	public static final String DUPLICATE_ASSET_UPLOAD_LOG_MSG = "There was a problem processing assets. Please check the logs for more details.";
	public static final String DUPLICATE_ASSET_UPLOAD_LOG_FIRST_PART_MSG ="An error occurred while processing";
	public static final String DUPLICATE_ASSET_UPLOAD_LOG_LAST_PART_MSG =" You can download the file from the More menu (three dots) on the right to view the errors.";
	public static final String DUPLICATE_ASSET_UPLOAD_LOG_LAST_PART_MSG_CLMN_HEADER ="Column headers do not match the template.";
	public static final String DUPLICATE_DEVICE_ADD_SUCCESS_LOG_MSG = "2 assets have been imported and/or updated.";
	public static final String VALID_ASSET_UPLOADZTE_LOG_MSG = "Your devices from devices_zte.csv were imported successfully. Please check the logs for more details.";
	public static final String VALID_ASSET_UPLOADZTE_LOG_SNR_MSG = "Your devices from devices_zte_snr.csv were imported successfully. Please check the logs for more details.";
	public static final String VALID_ASSET_EXPORT_MSG = "Device list exported successfully.";
	//Import for ZTE
	public static final String IMPORT_SUCCESS_MANUAL_SINGLE = "Device has been added and its enrollment is pending.";
	public static final String MANUAL_ENROL_MESSAGE_MULTIPLE = "Devices have been added, and their enrollment is pending.";

	public static final String IMPORT_DUPLICATE_ASSET_CSV_ERROR = "asset already exists";
	public static final String IMPORT_EMPTY_SERIAL_NO_ASSET_CSV_ERROR = "Asset type PC requires Serial Number";
	public static final String VALID_CUSTOMFIELD_ASSET_UPLOAD_LOG_MSG = "Your assets from DeviceCustomFields.csv were imported successfully. Please check the logs for more details.";
	public static final String CUSTOM_FIELDS_UPDATE = "Custom field was updated.";
}