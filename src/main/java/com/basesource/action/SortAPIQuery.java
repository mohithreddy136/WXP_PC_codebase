package com.basesource.action;

public class SortAPIQuery {
	
	
	public static final String SIZEKEY="size";	
	public static final String ORDERKEY="order"; 
	
	public static final String JSONQALlLOGS = "{\"query\":\"{\\\"query\\\":{\\\"bool\\\":{\\\"must\\\":[{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"deleted\\\":false}}]}},{\\\"bool\\\":{\\\"must_not\\\":[{\\\"match\\\":{\\\"type.keyword\\\":\\\"notification\\\"}}]}}]}},\\\"_source\\\":[\\\"id\\\",\\\"logType\\\",\\\"description\\\",\\\"logSubtype\\\",\\\"companyName\\\",\\\"tenantId\\\",\\\"createdAt\\\",\\\"createdAt_scr\\\",\\\"level\\\",\\\"deviceType\\\",\\\"deviceName\\\",\\\"deviceSerialNumber\\\",\\\"userName\\\",\\\"userId\\\",\\\"deleted\\\",\\\"type\\\",\\\"deviceId\\\"],\\\"from\\\":0,\\\"size\\\":25,\\\"sort\\\":[{\\\"logType.sort_en\\\":{\\\"order\\\":\\\"ASC\\\",\\\"unmapped_type\\\":\\\"string\\\"}}]}\",\"tenant_ids\":[],\"index_list\":[\"logs\"],\"search_type\":\"tenanted\"}";
	public static final String JSONQCREATEDLOG = "{\"query\":\"{\\\"query\\\":{\\\"bool\\\":{\\\"must\\\":[{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"deleted\\\":false}}]}},{\\\"bool\\\":{\\\"must_not\\\":[{\\\"match\\\":{\\\"type.keyword\\\":\\\"notification\\\"}}]}}]}},\\\"_source\\\":[\\\"id\\\",\\\"logType\\\",\\\"description\\\",\\\"logSubtype\\\",\\\"companyName\\\",\\\"tenantId\\\",\\\"createdAt\\\",\\\"createdAt_scr\\\",\\\"level\\\",\\\"deviceType\\\",\\\"deviceName\\\",\\\"deviceSerialNumber\\\",\\\"userName\\\",\\\"userId\\\",\\\"deleted\\\",\\\"type\\\",\\\"deviceId\\\"],\\\"from\\\":0,\\\"size\\\":25,\\\"sort\\\":[{\\\"createdAt\\\":{\\\"order\\\":\\\"ASC\\\",\\\"unmapped_type\\\":\\\"long\\\"}}]}\",\"tenant_ids\":[],\"index_list\":[\"logs\"],\"search_type\":\"tenanted\"}";

	public static final String[] ORDERSORT = new String[] { "ASC", "DESC" };
	public static final String[] COLUMNNAMESONLOG = new String[] { "createdAt", "logType", "logSubtype", "description", "companyName", "level", "deviceName", "deviceSerialNumber", "deviceType", "userName" };
	public static final String[] COLUMNNSORTLOCATORSONLOG = new String[] { "dateAndTimeSortArrow", "typeSortArrow", "subTypeSortArrow", "descriptionSortArrow", "companySortArrow", "severitySortArrow", "assetNameSortArrow", "assetSerialNumberSortArrow", "assetTypeSortArrow", "userNameSortArrow" };
	public static final String[] COLUMNNLISTLOCATORSONLOG = new String[] { "dateAndTimeList", "typeList", "subTypeList", "descriptionList", "companyList", "severityList", "assetNameSearchList", "assetSerialNumberSearchList", "assetTypeList", "userNameList" };
}


