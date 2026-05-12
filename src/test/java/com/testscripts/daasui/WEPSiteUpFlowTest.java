package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WEPSiteUpFlowTest extends CommonTest {

		private static Logger LOGGER = LogManager.getLogger(WEXDashboardTest.class);
		public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
		public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");
		
		@DataProvider
		public Object[][] getLoginData() {
			Object[][] data = new Object[2][2];
			data[1][0] = "ITADMIN_EMAIL_SITEUP";
			data[1][1] = "ITADMIN_PASSWORD_SITEUP";
			return data;
		}
		
		@Test(priority = 1, groups = { "PRODUCTION_ACCOUNTS_SITEUP"})
		public final void verifySiteUpTestcaseWithItAdmin() throws Exception {
			login("ITADMIN_EMAIL_SITEUP", "ITADMIN_PASSWORD_SITEUP");
			leftSideMenuVerification();
			waitForPageLoaded();
			LOGGER.info("SiteUP is working fine");
		}
		
}
