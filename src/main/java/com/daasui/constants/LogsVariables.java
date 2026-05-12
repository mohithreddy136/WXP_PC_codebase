package com.daasui.constants;

public class LogsVariables {
	public static final String UNIQUEDEVICETYPEAPI = "/api/2.0/tenants/uniquedevicetype";

	public static final String DEVICE_COMPANY = "UIAutomation_DoNotDelete";
	public static final String DEVICE_IMPORT_COMPANY = "Chromebook Company";
	public static final String DEVICE_COMPANY_ACCESSORIES_PERIPHERALS = "device test";

	public static final String MULTITENENNTSEARCHPRE = "services/ccc-search/1.3/tenants/";
	public static final String MULTITENENNTSEARCHPOST = "/multitenanted/_search";
	public static final String LOGUSERNAMEKEY = "hits.hits._source.";
	public static final String SEARCHSERVICEBODYRESELLER = "{\"query\":\"{\\\"query\\\":{\\\"bool\\\":{\\\"must\\\":[{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"deleted\\\":false}}]}},{\\\"bool\\\":{\\\"must_not\\\":[{\\\"match\\\":{\\\"type.keyword\\\":\\\"notification\\\"}}]}}]}},\\\"_source\\\":[\\\"id\\\",\\\"logType\\\",\\\"description\\\",\\\"logSubtype\\\",\\\"companyName\\\",\\\"tenantId\\\",\\\"createdAt\\\",\\\"createdAt_scr\\\",\\\"level\\\",\\\"deviceType\\\",\\\"deviceName\\\",\\\"deviceSerialNumber\\\",\\\"userName\\\",\\\"userId\\\",\\\"deleted\\\",\\\"type\\\",\\\"deviceId\\\"],\\\"from\\\":0,\\\"size\\\":25,\\\"sort\\\":[{\\\"createdAt\\\":{\\\"order\\\":\\\"DESC\\\",\\\"unmapped_type\\\":\\\"long\\\"}}]}\",\"tenant_ids\":[],\"index_list\":[\"logs_catalog\"],\"search_type\":\"tenanted\"}";
	public static final String SEARCHSERVICEBODYMSP = "{\"query\":\"{\\\"query\\\":{\\\"bool\\\":{\\\"must\\\":[{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"deleted\\\":false}}]}},{\\\"bool\\\":{\\\"must_not\\\":[{\\\"match\\\":{\\\"type.keyword\\\":\\\"notification\\\"}}]}}]}},\\\"_source\\\":[\\\"id\\\",\\\"logType\\\",\\\"description\\\",\\\"logSubtype\\\",\\\"companyName\\\",\\\"tenantId\\\",\\\"createdAt\\\",\\\"createdAt_scr\\\",\\\"level\\\",\\\"deviceType\\\",\\\"deviceName\\\",\\\"deviceSerialNumber\\\",\\\"userName\\\",\\\"userId\\\",\\\"deleted\\\",\\\"type\\\",\\\"deviceId\\\"],\\\"from\\\":0,\\\"size\\\":25,\\\"sort\\\":[{\\\"createdAt\\\":{\\\"order\\\":\\\"DESC\\\",\\\"unmapped_type\\\":\\\"long\\\"}}]}\",\"tenant_ids\":[],\"index_list\":[\"logs\"],\"search_type\":\"tenanted\"}";
	public static final String SUBTYPES_ADD = "add";
    public static final String SUBTYPES_BLOCKED = "blocked";
    public static final String SUBTYPES_BOUNCE = "bounce";
    public static final String SUBTYPES_PARTNER_ASSIGNMENT = "partner assignment";
    public static final String SUBTYPES_UPDATE = "Update";
    public static final String SUBTYPES_CHANGE = "change";
    public static final String SUBTYPES_REMOVE = "remove";
    public static final String SUBTYPES_DELETE = "delete";
    public static final String SUBTYPES_ENROLLMENT = "enrollment";
    public static final String SUBTYPES_AUTO_ENROLLMENT = "auto enrollment";
    public static final String SUBTYPES_EXPORT = "export";
    public static final String SUBTYPES_IMPORT = "import";
    public static final String SUBTYPES_OWNERSHIP_CHANGE = "ownership change";
    public static final String SUBTYPES_BIOS = "bios";
    public static final String SUBTYPES_BATTERY= "battery";
    public static final String SUBTYPES_HARD_DRIVE = "hard drive";
    public static final String SUBTYPES_WARRANTY = "warranty";
    public static final String SUBTYPES_ERASE = "erase";
    public static final String SUBTYPES_LOCK = "lock";
    public static final String SUBTYPES_ENABLED_DISABLE = "enable/disable";
    public static final String SUBTYPES_EDIT = "edit";
    public static final String SUBTYPES_ANTI_THEFT = "anti-theft";
    public static final String SUBTYPES_ANTI_VIRUS = "antivirus";
    public static final String SUBTYPES_FIREWALL = "firewall";
    public static final String SUBTYPES_LOCATION = "location";
    public static final String SUBTYPES_POLICY = "policy";
    public static final String SUBTYPES_SECUREBOOT= "secureboot";
    public static final String SUBTYPES_DOWNGRADE = "downgrade";
    public static final String SUBTYPES_EXPIRATION = "expiration";
    public static final String SUBTYPE_EXTENSION = "extension";
    public static final String SUBTYPES_REACTIVATE = "reactivate";
    public static final String SUBTYPES_TRIAL = "trial";
    public static final String SUBTYPES_UPGRADE = "upgrade";
    public static final String SUBTYPES_LOGIN = "login";
    public static final String SUBTYPES_WARRANTYCRITICALALERT = "warrantycriticalalert";
    public static final String SUBTYPES_WARRANTYEXPIREDALERT = "warrantyexpiredalert";
    public static final String SUBTYPES_WARRANTYINFOALERT = "warrantyinfoalert";
    public static final String SUBTYPES_WARRANTYWARNINGALERT = "warrantywarningalert";
    public static final String SUBTYPES_RECOMMENDATIONS = "recommendations";
    public static final String TYPE_ScriptAssignment = "Script Assignment";
    public static final String lEVEL_INFORMATION = "Information";
    public static final String SUBTYPES_ARCHIVE = "Archive";

    
}
