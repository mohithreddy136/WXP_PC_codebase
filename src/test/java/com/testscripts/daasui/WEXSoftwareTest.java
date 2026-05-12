package com.testscripts.daasui;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.WEXSoftwarePage;

public class WEXSoftwareTest extends CommonTest {
	
	@Test(priority = 1, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C43408111")
	public final void verifySOftwarePage() throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		WEXSoftwarePage softwareRequirement = new WEXSoftwarePage(PreDefinedActions.getDriver()).getInstance();				
		getUrl( SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/" + ConstantURL.SOFTWARE_DOWNLOAD + "/" + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode")));		
		softAssert.assertTrue(softwareRequirement.verifyElementsOfSoftwarePage("systemRequirement"), "System Requirement Label is not present on software page");		
		softAssert.assertTrue(softwareRequirement.verifyElementsOfSoftwarePage("androidApplication"), "Android Application is not present on software page");
		softAssert.assertTrue(softwareRequirement.verifyElementsOfSoftwarePage("macApplication"), "Mac Application is not present on software page");
		softAssert.assertTrue(softwareRequirement.verifyElementsOfSoftwarePage("windowsApplication"), "Windows Application is not present on software page");
		softAssert.assertTrue(softwareRequirement.verifyElementsOfSoftwarePage("networkReadinessTool"), "Network Readiness Tool is not present on software page");
		softAssert.assertTrue(softwareRequirement.verifyElementsOfSoftwarePage("wolfConnect"), "Wolf Connect is not present on software page");
		softAssert.assertTrue(softwareRequirement.verifyElementsOfSoftwarePage("downloadButtonMac"), "Download MAC button is not present on software page");
		softAssert.assertTrue(softwareRequirement.verifyElementsOfSoftwarePage("downloadButtonWindows"), "Download Windows button is not present on software page");
		softAssert.assertTrue(softwareRequirement.verifyElementsOfSoftwarePage("downloadButtonAssessmentTools"), "Download Assessment tool button is not present on software page");
		softAssert.assertTrue(softwareRequirement.verifyElementsOfSoftwarePage("downloadButtonWolfConnect"), "Download Wolf Connect button is not present on software page");
	}

}
