package com.daasui.constants;

public class PreferenceVariables {
	
	//EMM Strings
	public static final String AIRWATCH = "VMware Workspace ONE";
	public static final String INTUNE = "Microsoft Intune";
	public static final String CHROME = "Google Chrome Enterprise";
	public static final String EMM_AIRWATCH = "Airwatch";
	public static final String EMM_INTUNE = "Intune";
	public static final String EMM_CHROME = "Chrome";
	
	public static final String BASIC_AUTHENTICATION_CONFIGURATION = "BasicAuthentication";
	
	// Intune EMM Configuration
	public static final String STABLE_US_DOMAIN_NAME = "productionorbust.com";
	public static final String STABLE_US_INTUNE_ID = "hptmqa@productionorbust.com";
	public static final String STABLE_US_INTUNE_PASSWORD = "Intune567$";
	public static final String STABLE_EU_DOMAIN_NAME = "productionorbust.com";
	public static final String STABLE_EU_INTUNE_ID = "hptmqa@productionorbust.com";
	public static final String STABLE_EU_INTUNE_PASSWORD = "Intune567$";
	public static final String STAGING_US_DOMAIN_NAME = "productionorbust.com";
	public static final String STAGING_US_INTUNE_ID = "hptmqa@productionorbust.com";
	public static final String STAGING_US_INTUNE_PASSWORD = "Intune567*";
	public static final String STAGING_EU_DOMAIN_NAME = "productionorbust.com";
	public static final String STAGING_EU_INTUNE_ID = "hptmqa@productionorbust.com";
	public static final String STAGING_EU_INTUNE_PASSWORD = "Intune567*";
	public static final String PRODUCTION_US_DOMAIN_NAME = "productionorbust.com";
	public static final String PRODUCTION_US_INTUNE_ID = "hptmqa@productionorbust.com";
	public static final String PRODUCTION_US_INTUNE_PASSWORD = "Intune567$";
	public static final String PRODUCTION_EU_DOMAIN_NAME = "productionorbust.com";
	public static final String PRODUCTION_EU_INTUNE_ID = "hptmqa@productionorbust.com";
	public static final String PRODUCTION_EU_INTUNE_PASSWORD = "Intune567$";

	// Airwatch EMM Configuration
	public static final String AIRWATCH_URL_US = "https://as975.awmdm.com";
	public static final String AIRWATCH_KEY_US = "j6hnTREcz+vi+x2S521JlKWVNK28uEYTtbwypI3jdss=";
	public static final String AIRWATCH_GROUP_ID_US = "uiauto";
	public static final String AIRWATCH_USERNAME_US = "uiauto.admin";
	public static final String AIRWATCH_PASSWORD_US = "Test@1234"; 
	public static final String AIRWATCH_CERTIFICATE_PASSWORD_US = "Test@123";
	public static final String AIRWATCH_CERTIFICATE_FILE_US = "CN=648_uiauto.admin.p12";
	
	public static final String AIRWATCH_URL_EU = "https://as975.awmdm.com";
	public static final String AIRWATCH_KEY_EU = "Fer8byTK0utaymHgQOj5zTXDBMYvcv7hgJeBRY0bOZA=";
	public static final String AIRWATCH_GROUP_ID_EU = "uiautoeu";
	public static final String AIRWATCH_USERNAME_EU = "uiautoeu.admin";
	public static final String AIRWATCH_PASSWORD_EU = "Test@1234";
	public static final String AIRWATCH_CERTIFICATE_PASSWORD_EU = "Test@123";
	public static final String AIRWATCH_CERTIFICATE_FILE_EU = "CN=649_uiautoeu.admin.p12";
	
	//EMM(Airwatch Intune Chromebook) Credentials for EMM Admin Role
	public static final String AIRWATCH_URL_EMMADMIN = "https://awconsole.hpdaas.com";
	public static final String AIRWATCH_KEY_EMMADMIN = "j6hnTREcz+vi+x2S521JlKWVNK28uEYTtbwypI3jdss=";
	public static final String AIRWATCH_GROUP_ID_EMMADMIN = "emmadminautomation";
	public static final String AIRWATCH_USERNAME_EMMADMIN = "emmadmin";
	public static final String AIRWATCH_PASSWORD_EMMADMIN = "N0P3eking@1247";
	
	public static final String INTUNE_DOMAIN_NAME_EMMADMIN = "vividemmdev.onmicrosoft.com";
	public static final String INTUNE_USERNAME_EMMADMIN = "nitin.kote@vividemmdev.onmicrosoft.com";
	public static final String INTUNE_PASSWORD_EMMADMIN = "bid.far-97";
	
	public static final String CHROME_ENTERPRISE_INTEGRATION_USERNAME_EMMADMIN = "siddhi@daas3.ext.hp.com";	
	public static final String CHROME_ENTERPRISE_INTEGRATION_PASSWORD_EMMADMIN = "Google@97";

	// Chromebook Integration
	public static final String CHROME_ENTERPRISE_INTEGRATION_USERNAME = "admin.qamanual@qa.manual.daas.ext.hp.com";	
	public static final String CHROME_ENTERPRISE_INTEGRATION_PASSWORD = "Test@1234";
	public static final String CHROMEBOOK = "ChromeBook";
    public static final String CHROME_PORTAL_URL = "https://admin.google.com";
    public static final String CHROME_ACCOUNT_PORTAL_URL = "https://accounts.google.com";
    public static final String CHROME_DEVICE_URL = "https://admin.google.com/AdminHome?fral=1#Devices:";
    public static final String CHROME_DEVICE_LIST_URL = "&dFullSearch=true";
    public static final String CHROME_DEVICE_DETAILS = "https://admin.google.com/AdminHome#DeviceDetails:deviceType=CHROME";
    public static final String CHROME_GOOGLE_ADMIN_NON_SUPERADMIN_USER = "user03@qa.auto.daas.ext.hp.com";
    public static final String CHROME_GOOGLE_ADMIN_NON_SUPERADMIN_USER_PASSWORD = "Test@1234";
    
	//Microsoft Telemetry variable
	public static final String MICROSOFT_TELEMETRY_TITLE = "Microsoft Telemetry";
	public static final String MICROSOFT_TEAMS_TITLE = "Microsoft Teams Integration";
	public static final String MICROSOFT_TEAMS_TITLE_STATUS = "Disabled";
	public static final String ZOOM_INTEGRATION_TITLE = "Zoom Integration";
	public static final String ZOOM_INTEGRATION_TITLE_STATUS = "Disabled";
	public static final String MICROSOFT_TELEMETRY_DOMAIN_NAME = "vividemmdev.onmicrosoft.com";
	public static final String MICROSOFT_TELEMETRY_URL = "https://portal.azure.com/#@lightaria.net/resource/subscriptions/82f3ba8d-dd04-4556-8602-b01099214533/resourcegroups/mms-eus/providers/microsoft.operationalinsights/workspaces/lightaria/solutionOverview";
	public static final String MICROSOFT_TELEMETRY_EDIT_URL = "https://portal.azure.com";
	public static final String MICROSOFT_TELEMETRY_WORKSPACE_ID = "9d5fc3c2-4c2b-4191-ad96-d222a15923f9";
	public static final String MICROSOFT_TELEMETRY_INCORRECT_WORKSPACE_ID = "123";
	public static final String AZURE_ID_TO_CONFIGURE_MICROSOFT_TELEMETRY = "neeraj.gangapurkar@vividemmdev.onmicrosoft.com";
	public static final String PASSOWRD_FOR_AZURE_ID = "N0P3eking!";
	public static final String MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_CONFIGURED = "AutoTest Telemtery Configured";
	public static final String MICROSOFT_TELEMETRY_COMPANY_OMS_DETAILS_NOT_CONFIGURED = "AutoTest Telemtery Not Configured";
	public static final String MSTUSER_TYPE_MST = "MST_USER";
	public static final String MSTUSER_TYPE_SUPPORT_SPECIALIST = "MST_USER_SUPPORT_SPECIALIST";

	public static final String NUMBER_OF_APPROVERS = "3";
}
