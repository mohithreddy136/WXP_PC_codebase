package com.daasui.constants;

import java.util.ArrayList;
import java.util.Arrays;

public class DashboardVariables {
	public static final String DASHBOARD_DEVICE_BY_OS_ANDROID = "Android";
	public static final String DASHBOARD_DEVICE_BY_OS_IOS = "iOS";
	public static final String DASHBOARD_CHART_CPU_UTILIZATION_TITLE = "CPU Utilization";
	public static final String DASHBOARD_CHART_MEMORY_UTILIZATION_TITLE = "Memory Utilization";
	public static final String DASHBOARD_CHART_HARDWARE_INVENTORY_TITLE = "Hardware Inventory";
	public static final String DASHBOARD_CHART_SOFTWARE_INVENTORY_TITLE = "SOFTWARE INVENTORY (TOP 10)";
	public static final String DASHBOARD_CHART_OPEN_INCIDENTS_TITLE = "OPEN INCIDENTS";
	public static final String DASHBOARD_CHART_ALL_INCIDENTS_TITLE = "ALL INCIDENTS";
	public static final String DASHBOARD_CHART_TOP_10_INCIDENTS_TITLE = "TOP 10 INCIDENTS";
	public static final String DASHBOARD_CHART_WARRANTY_EXPIRATION_TITLE = "Warranty Expiration";
	public static final String DASHBOARD_CHART_SUBSCRIPTION_EXPIRATION_TITLE = "Subscription Expiration";
	public static final String DASHBOARD_CHART_BATTERY_REPLACEMENT_SUMMARY_TITLE = "Battery Replacement Summary";
	public static final String DASHBOARD_CHART_DISK_REPLACEMENT_SUMMARY_TITLE = "Disk Replacement Summary";
	public static final String DASHBOARD_CHART_COMPANY_SEARCH_BOX_NAME = "All companies";
	public static final String DASHBOARD_CHART_COMPANY_NAME_MSP_US = "NoDeviceCompany";
	public static final String DASHBOARD_CHART_COMPANY_NAME_RESELLER_US = "NoDeviceCompany";
	public static final String DASHBOARD_CHART_SUBSCRIPTION_AGGREGATE = "Automation Company - Do Not Delete";
	public static final String DASHBOARD_CHART_COMPANY_NAME_ALL_INCIDENTS_FIXED = "Automation company 2-do_not_delete_modify";
	public static final String DASHBOARD_CHART_COMPANY_NAME_NO_DATA = "Automation com1-do_not";
	public static final String DESFLEETSCORECHART= "dashboard-digital_experience-reports";
	
	// Dashboard chart toggles
    public static final String DHBATTERYREPLACEMENT = "dashboard-device-health-widget-v2";
    public static final String DHDISKREPLACEMENT = "dashboard-device-health-widget-v2";
    public static final String BUSINESSREVIEWCARD = "dashboard-business-review-tile-v2";
    public static final String DHDISKCAPACITY = "dashboard-device-health-widget-v2";
    public static final String DHTHERMALGRADE = "dashboard-device-health-widget-v2";
    public static final String DEVICEHEALTH = "dashboard-device-health-widget";
    public static final String NEWBUSINESSREVIEWCARD = "dashboard-business-review-tile";
    public static final String OSVERSIONSUPPORT = "dashboard-os-version-support";
    public static final String DEVICESBYTYPE = "mpi-dashboard-devices-chart";
    public static final String DEVICESBYOS = "mpi-dashboard-devices-chart";
    public static final String WARRANTYEXPIRATION = "dashboard-warranty-expiration-chart";
    public static final String SUBSCRIPTIONEXPIRATION = "dashboard-subscription-expiration-chart";
    public static final String CPUUTILIZATION = "dashboard-device-utilization-chart";
    public static final String MEMORYUTILIZATION = "dashboard-device-utilization-chart";
    public static final String HARDWAREINVENTORY = "dashboard-hardware-inventory-chart";
    public static final String BATTERYREPLACEMENTSUMMARY = "dashboard-battery-replacement-chart";
    public static final String DISKREPLACEMENTSUMMARY = "dashboard-disk-replacement-chart";
    public static final String INCIDENTSWITHOPENSTATUS = "mpi-dashboard-incidents-chart";
    public static final String INCIDENTSBYTYPE = "mpi-dashboard-incidents-chart";
    public static final String INCIDENTSTOPBYSUBTYPE = "mpi-dashboard-incidents-chart";
    public static final String ALLINCIDENTSBYTYPE = "dashboard-all-incidents-chart";
    public static final String INCIDENTBURNDOWNSUMMARY = "incidents-burndown-chart";
    public static final String SOFTWAREINVENTORY = "dashboard-software-inventory-chart";
    public static final String TODAYSCRITICALINCIDENTS = "dashboard-critical-incidents-chart";
    public static final String BATTERYSWELLPROB = "batterySwellProb";
    public static final String REDESIGN= "daas-ui-dashboard-redesign";
    public static final String NETWORKHEALTHANDOUTAGE = "networkassmtV2";
    public static final String JAVASWITCHER= "jarvisMFESwitcher";
    public static final String SOFTWAREUPDATEV2= "softwareUpdatesV2";
    public static final String BIOSTEMPLATE1= "Deploy bios update HP BIOS and System Firmware (S21) 02.10.01";
    public static final String BIOSTEMPLATE2= "Deploy bios update HP BIOS and System Firmware (S21) 02.10.00";
    public static final String BIOSTEMPLATE3= "Deploy bios update HP BIOS and System Firmware (S21) 02.09.02";

    public static final int DASHBOARD_REPORTS_WAIT = 60;
    
    //Flexi Dashboard toggle
    public static final String FLEXIDASHBOARD = "flexible-dashboards";

	public static final ArrayList<String> TOP_TEN_INCIDENTS_CHART_LABELS_LIST = new ArrayList<>(
			Arrays.asList("com.hp.mpi.icm.type.short.account - com.hp.mpi.icm.type.short.other", "com.hp.mpi.icm.type.short.hwchange - com.hp.mpi.icm.type.short.other", "com.hp.mpi.icm.type.short.hardwarehealth - com.hp.mpi.icm.type.short.other", "com.hp.mpi.icm.type.short.hwinventory - com.hp.mpi.icm.type.short.other", "com.hp.mpi.icm.type.short.network - com.hp.mpi.icm.type.short.other", "com.hp.mpi.icm.type.short.oshealth - com.hp.mpi.icm.type.short.other",
					"com.hp.mpi.icm.type.short.security - com.hp.mpi.icm.type.short.other", "com.hp.mpi.icm.type.short.softwarechange - com.hp.mpi.icm.type.short.other", "com.hp.mpi.icm.type.short.softwarehealth - com.hp.mpi.icm.type.short.other", "com.hp.mpi.icm.type.short.other - com.hp.mpi.icm.type.short.other", "com.hp.mpi.icm.type.short.account - com.hp.mpi.icm.type.short.unknown", "com.hp.mpi.icm.type.short.hwchange  - com.hp.mpi.icm.type.short.unknown",
					"com.hp.mpi.icm.type.short.hardwarehealth - com.hp.mpi.icm.type.short.unknown", "com.hp.mpi.icm.type.short.hwinventory - com.hp.mpi.icm.type.short.unknown", "com.hp.mpi.icm.type.short.network - com.hp.mpi.icm.type.short.unknown", "com.hp.mpi.icm.type.short.oshealth - com.hp.mpi.icm.type.short.unknown", "com.hp.mpi.icm.type.short.security - com.hp.mpi.icm.type.short.unknown", "com.hp.mpi.icm.type.short.softwarechange - com.hp.mpi.icm.type.short.unknown",
					"com.hp.mpi.icm.type.short.softwarehealth - com.hp.mpi.icm.type.short.unknown", "com.hp.mpi.icm.type.short.unknown - com.hp.mpi.icm.type.short.unknown", "com.hp.mpi.icm.subtype.short.account", "com.hp.mpi.icm.subtype.short.apns", "com.hp.mpi.icm.subtype.short.licensing", "com.hp.mpi.icm.subtype.short.hdd", "com.hp.mpi.icm.subtype.short.memory", "com.hp.mpi.icm.subtype.short.pnp", "com.hp.mpi.icm.type.short.hwchange", "com.hp.mpi.icm.type.short.hardwarehealth",
					"com.hp.mpi.icm.subtype.short.hddstore", "com.hp.mpi.icm.subtype.short.hddevent", "com.hp.mpi.icm.subtype.short.hddssd", "com.hp.mpi.icm.subtype.short.batterycapacity", "com.hp.mpi.icm.subtype.short.batterydetected", "com.hp.mpi.icm.subtype.short.batterypredictive", "com.hp.mpi.icm.subtype.short.systemthermal", "com.hp.mpi.icm.subtype.short.systemcpu", "com.hp.mpi.icm.subtype.short.systemfan", "com.hp.mpi.icm.subtype.short.systempower",
					"com.hp.mpi.icm.subtype.short.systemmemory", "com.hp.mpi.icm.subtype.short.biosstart", "com.hp.mpi.icm.subtype.short.biosversion", "com.hp.mpi.icm.subtype.short.ntp", "com.hp.mpi.icm.subtype.short.cmos", "com.hp.mpi.icm.type.short.hwinventory", "com.hp.mpi.icm.subtype.short.warrent", "com.hp.mpi.icm.type.short.network", "com.hp.mpi.icm.subtype.short.highnetwork", "com.hp.mpi.icm.type.short.oshealth", "com.hp.mpi.icm.subtype.short.driverversion",
					"com.hp.mpi.icm.subtype.short.drivermissing", "com.hp.mpi.icm.subtype.short.osbsod", "com.hp.mpi.icm.subtype.short.oscrash", "com.hp.mpi.icm.subtype.short.cpuhigh", "com.hp.mpi.icm.subtype.short.memoryhigh", "com.hp.mpi.icm.subtype.short.hddthrashing", "com.hp.mpi.icm.subtype.short.slowboot", "com.hp.mpi.icm.type.short.security", "com.hp.mpi.icm.subtype.short.encryption", "com.hp.mpi.icm.subtype.short.antivirus", "com.hp.mpi.icm.subtype.short.firewall",
					"com.hp.mpi.icm.subtype.short.heartbeat", "com.hp.mpi.icm.subtype.short.online", "com.hp.mpi.icm.subtype.short.password", "com.hp.mpi.icm.type.short.softwarechange", "com.hp.mpi.icm.subtype.short.modification", "com.hp.mpi.icm.subtype.short.uninstall", "com.hp.mpi.icm.subtype.short.install", "com.hp.mpi.icm.type.short.softwarehealth", "com.hp.mpi.icm.subtype.short.crash", "com.hp.mpi.icm.subtype.short.requiredapps", "com.hp.mpi.icm.subtype.short.patch"));

	// Share Dashboard variables
	 public static final String SHARE_DASHBOARD_NAME = "Auto Share Dashboard";
	 
	 // Global filter togglename
	 public static final String GLOBAL_FILTER_TOGGLE = "hierarchical-multi-tenancy";

	 // List page pagination updated rows count
	 public static final String LISTPAGETABLE_UPDATED_ROW_COUNT = "extra-page-size-options";
	 
	// Sure sense pro chart togglename
    public static final String SURE_SENSE_PRO = "suresensepro";
    public static final String WHATS_NEW_ALERT_TOGGLE = "whats-new-alerts";
    
    //Actionable insights right panel togglename
    public static final String ACTIONABLE_INSIGHTS = "actionable-insights-widget";

    //Remove timestamp toggle name
    public static final String REMOVE_TIMESTAMP_TOGGLE = "remove-timestamp-from-dashboard-widget";
    
    //Match Dashboard Widget Data with "Details" of widget toggle name
    public static final String MATCH_WIDGET_DATA_TO_DETAILS = "match-dashboard-widget-data-with-details";
    
    //Pre_configured Dashboard toggle name
    public static final String PRE_CONFIGURED_DASHBOARD = "pre-configured-dashboards-V1";

    public static final String FLEET_OVERVIEW_DASHBOARD = "fleetoverview";

    public static final String Win11V2 = "win11ReadinessV2";
}
