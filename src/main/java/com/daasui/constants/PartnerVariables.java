package com.daasui.constants;

public class PartnerVariables {
	public static final String NAME_SEARCH = "test";
	public static final String EMAIL_SEARCH = "a";
	public static final String PRIMARY_ADMINISTRATOR_SEARCH = "a";
	public static final String MOBILE_PHONE_SEARCH = "1";
	public static final String INCORRECT_SEARCH_STRING = "rrur6r";
	public static final String ADDRESS_SEARCH = "a";
	public static final String NAME = "ui test partner";
	public static final String FIRST_NAME = "ui test";
	public static final String LAST_NAME = "partner";
	public static final String EMAIL = "testpartner@mailinator.com";
	public static final String PHONE_NUMBER = "9876543210";
	public static final String RESELLER_REMOVAL_TEST_USER = "partneradminremovaltestuser";
	public static final String PARTNER_DETAILS_TEST_USER = "partnerdetailstestuser";
	//public static final String PARTNER_DETAILS_TEST_USER = "28JANATEAMNEETUPARTNERUS1";
	public static final String PARTNER_DETAILS_TEST_USER2 = "PartnerAutoReseller DoNotDelete";
	public static final String AUTHORIZED_LEGEND = "Authorized";
	public static final String MSP_DETAILS_TEST_USER = "MSPDetailsTestUser";
	public static final String PARTNER_DETAILS_TEST_MSP_USER = "partnertestcompany1";
	public static final String PARTNER_DETAILS_TEST_PRIMARY_ADMINISTRATOR = "partnerdetails testuser";
	public static final String MSP_DETAILS_TEST_PRIMARY_ADMINISTRATOR = "mspdetails testuser";
	public static final String EDITPROFILE_COUNTRY = "India";
	public static final String SEARCHSERVICEBODY = "{\"query\":\"{\\\"query\\\":{\\\"bool\\\":{\\\"must\\\":[{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"tenantType.keyword\\\":\\\"PARTNER\\\"}}]}},{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"subTypes.keyword\\\":\\\"RESELLER\\\"}},{\\\"match\\\":{\\\"subTypes.keyword\\\":\\\"RESELLERPLUS\\\"}}]}}]}},\\\"from\\\":0,\\\"size\\\":50,\\\"sort\\\":[{\\\"displayName.sort_en\\\":{\\\"order\\\":\\\"ASC\\\",\\\"unmapped_type\\\":\\\"string\\\"}},{\\\"meta.created\\\":{\\\"unmapped_type\\\":\\\"long\\\",\\\"order\\\":\\\"DESC\\\"}}]}\",\"tenant_ids\":[],\"index_list\":[\"idmtenants\"],\"search_type\":\"tenanted\"}";
	public static final String REMOVE_PARTNER_URL = "/tenantType/PARTNER/company";
	public static final String FIRSTNAME = "ui msp";
	public static final String LASTNAME = "user";
	public static final String FIRSTLASTNAME = "ui msp user";
	public static final String MULTIPLE_ACCOUNT_TOOGLE = "one-email-multiple-accounts";
	public static final String PBM_OBM_TOOGLE = "loe4-setup";
	
	public static final String PBM_FIRST_NAME = "ui test";
	public static final String PBM_LAST_NAME = "pbmpartner";
	public static final String OBM_FIRST_NAME = "ui test";
	public static final String OBM_LAST_NAME = "obmpartner";
	public static final String BILLING_USER_FIRST_NAME = "Billing";
	public static final String BILLING_USER_LAST_NAME = "test user";
	
	public static final String PARTNER_DETAILS_AC_TEST_USER = "donotdeleteautomationacpartner";
	
	
}
