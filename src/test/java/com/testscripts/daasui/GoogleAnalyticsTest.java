package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.basesource.action.PreDefinedActions;
import com.daasui.pages.GoogleAnalyticsPage;

/**
 * This class contains test cases for Google Analytics validation.
 *
 */
public class GoogleAnalyticsTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(GoogleAnalyticsTest.class);

	@Test(priority = 1, groups = { "PRODUCTION_CI", "PRODUCTION_SANITY"}, enabled = false)
	public final void verifyGoogleAnalyticsData() {
		try {
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			GoogleAnalyticsPage googleAnalyticsPage = new GoogleAnalyticsPage(PreDefinedActions.getDriver()).getInstance();
			sleeper(10000);
			Assert.assertTrue(googleAnalyticsPage.getDataLayer(), "Event for sending data to Google Analytics did not generate.");
			LOGGER.info("Validation of Google Analytics got completed. ");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

}
