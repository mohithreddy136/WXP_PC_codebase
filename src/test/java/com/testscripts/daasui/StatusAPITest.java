package com.testscripts.daasui;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.StatusAPIPage;
import com.daasui.pages.WEXCustomerHomePage;

public class StatusAPITest extends CommonTest {
	
	private static Logger LOGGER = LogManager.getLogger(WEPCustomerHomeTest.class);
	public String componentId;
    public String status="operational";
	@Test(priority = 1, description = "TestCaseID:", groups = {"COMPONENT_STATUS_UPDATE"})
	public final void verifyCustomerHomePageWidgets() throws Exception {
		//setting the default status for pass scenario
		StatusAPIPage statusPage = new StatusAPIPage(PreDefinedActions.getDriver()).getInstance();
		Properties props = statusPage.statusAPIPageProperties;
		try { 
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerHomePage customerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		componentId = props.getProperty("component.id.hardware_support");
		customerHomePage.companyView(CommonVariables.PARTNER_Home);
		waitForPageLoaded();
		softAssert.assertTrue(customerHomePage.verifyWexScore("wexScore"));
		softAssert.assertTrue(customerHomePage.verifyWexScoreOverTime("wexScoreOverTime"));
		softAssert.assertTrue(customerHomePage.verifyFleetInventory("fleetInventory"));
		softAssert.assertTrue(customerHomePage.verifyAppsWithMostCrashes("appsWithMostCrashes"));
		softAssert.assertAll();
		} catch(Throwable failure) {
			status = props.getProperty("failure.status.hardware_support");
			throw failure;
		} 
	}

}
