package com.testscripts.daasui;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.WorkflowVariables;
import com.daasui.pages.WEXDashboardPage;
import com.daasui.pages.WXPWorkflowsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WXPWorkflowsTest extends CommonTest {
    private static Logger LOGGER = LogManager.getLogger(WXPWorkflowsTest.class);

        /**
     * This test case verifies the Workflow Builder list page
     * 1. Login and navigate to Workflows page
     * 2. Verify page title and breadcrumb
     * 3. Verify table columns
     * 4. Verify search functionality
     * 5. Verify workflow name is displayed in the table
     */
    @Test(priority = 1, groups = {"REGRESSION_WORKFLOWS"}, description = "TestCaseID:C43550144")
    public void verifyWorkflowBuilderListPage() throws Exception {
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        leftSideMenuVerification();
        
        // Navigate to Workflows page
        dashboardPage.companyView(CommonVariables.LABS);
        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("workflowTryNow");
        waitForPageLoaded();
        
        // Verify page title
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowBuilderPageTitle"),
                "Workflow Builder page title is not displayed");
        
        // Verify table columns
        List<String> expectedColumns = Arrays.asList( getTextLanguage(LanguageCode, "daas_ui", "asset_modal_name"),
                getTextLanguage(LanguageCode, "daas_ui", "alert_management.status.column"),
                getTextLanguage(LanguageCode, "daas_ui", "wex.labs.workflow-automation.Table.coulmn.triggerType"),
                getTextLanguage(LanguageCode, "daas_ui", "alert_management.lastEdited.column"),
                getTextLanguage(LanguageCode, "daas_ui", "contents.tableColumns.createdBy"));
        Assert.assertTrue(wexWorkflowsPage.verifyTableColumns(expectedColumns, "workflowBuilderTableColumns"),
                "Workflow Builder table columns do not match");
        
        // Verify page list is present
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowPageList"),
                "Workflow page list is not displayed");
        
        // Verify add workflow button is present
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("addWorkflowButton"),
                "Add workflow button is not displayed");
        
        // Verify workflow table has rows
        if (wexWorkflowsPage.getElementsOfWorkflowsPage("workflowTableRows").size() > 0) {
            LOGGER.info("Workflow table has " + wexWorkflowsPage.getElementsOfWorkflowsPage("workflowTableRows").size() + " rows");
            Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowTableCount"),
                    "Workflow table count is not displayed");
        }
        
        LOGGER.info("Verify Workflow Builder List Page test completed successfully");
    }

    /**
     * This test case verifies the Workflow Details Overview Tab
     * 1. Login and navigate to Workflows page
     * 2. Click on first workflow name to open details
     * 3. Verify overview tab is displayed and active
     * 4. Verify workflow information section
     * 5. Verify workflow name, type, description, status fields
     * 6. Verify created by, date created, last modified fields
     * 7. Verify workflow steps section
     */
    @Test(priority = 2, groups = {"REGRESSION_WORKFLOWS"}, description = "Verify Workflow Details Overview Tab")
    public void verifyWorkflowDetailsOverviewTab() throws Exception {
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        leftSideMenuVerification();       
        // Navigate to Workflows page
        dashboardPage.companyView(CommonVariables.LABS);
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
        waitForPageLoaded();
        if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
            wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
            LOGGER.info("Clear button clicked");
    	    } else {
    	        LOGGER.info("Clear button not present — skipping");
    	    }
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
	    sleeper(3000);
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("completedstatus");
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown"); 
        // Verify table has rows before clicking
        Assert.assertTrue(wexWorkflowsPage.getElementsOfWorkflowsPage("workflowTableRows").size() > 0,
                "No workflows found in the table");  
        // Click on first workflow name
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowNameRowValue"); 
        // Verify Overview tab is displayed
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailsOverviewTab"),
                "Workflow details overview tab is not displayed");
        // Verify workflow information header
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailInfoHeader"),
                "Workflow information header is not displayed");
        // Verify workflow name field
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailName"),
                "Workflow name label is not displayed");
        // Verify profile section
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailProfile"),
                "Workflow profile section is not displayed");
        // Verify Time & Audience section
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailTimeAudience"),
                "Workflow Time & Audience section is not displayed");       
        // Verify Editing History section
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailEditingHistory"),
                "Workflow Editing History section is not displayed");
        LOGGER.info("Verify Workflow Details Overview Tab test completed successfully");
    }

    /**
     * This test case verifies the Workflow Details Activity Tab
     * 1. Login and navigate to Workflows page
     * 2. Click on first workflow name to open details
     * 3. Click on Activity tab
     * 4. Verify activity table columns
     * 5. Verify activity search field
     * 6. Verify activity status dropdown
     * 7. Verify activity records are displayed
     */
    @Test(priority = 3, groups = {"REGRESSION_WORKFLOWS"}, description = "Verify Workflow Details Activity Tab")
    public void verifyWorkflowDetailsActivityTab() throws Exception {
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        leftSideMenuVerification();   
        // Navigate to Workflows page
        dashboardPage.companyView(CommonVariables.LABS);
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
        waitForPageLoaded();
        if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
            wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
            LOGGER.info("Clear button clicked");
    	    } else {
    	        LOGGER.info("Clear button not present — skipping");
    	    }
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
	    sleeper(3000);
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("completedstatus");
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
        // Verify table has rows before clicking
        Assert.assertTrue(wexWorkflowsPage.getElementsOfWorkflowsPage("workflowTableRows").size() > 0,
                "No workflows found in the table");        
        // Click on first workflow name
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowNameRowValue");              
        // Click on Activity tab
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowDetailsActivityTab");       
        // Verify activity page title
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowActivityPageTitle"),
                "Workflow activity page title is not displayed");   
        // Verify activity name search field is present
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowActivityNameSearchBox"),
                "Workflow activity name search field is not displayed");        
        // Verify activity status dropdown is present
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowActivityStatusDropdown"),
                "Workflow activity status dropdown is not displayed");        
        // Verify activity table
        if (wexWorkflowsPage.getElementsOfWorkflowsPage("workflowActivityTableRows").size() > 0) {
            LOGGER.info("Workflow activity table has " + wexWorkflowsPage.getElementsOfWorkflowsPage("workflowActivityTableRows").size() + " rows");
            Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowActivityNameValue"),
                    "Workflow activity name value is not displayed");
        } else {
            LOGGER.info("No workflow activities found for this workflow");
        }  
        // Verify breadcrumb to navigate back
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowActivityListBreadCrumb"),
                "Workflow activity breadcrumb is not displayed");
        LOGGER.info("Verify Workflow Details Activity Tab test completed successfully");
    }
	
	/**
	 * This test case verifies the Workflow Details Activity Tab 
	 * 1. Login and navigate to Workflows page 
	 * 2. Click on first workflow name to open details
	 * 3.Click on Activity tab 4. Verify activity table columns 
	 * 5. Verify activity search field 
	 * 6. Verify activity status dropdown 
	 * 7. Verify activity record are displayed
	 */
	@Test(priority = 4, groups = {"REGRESSION_WORKFLOWS" }, description = "Verify Workflow Details Activity Tab")
	public void verifyWorkflowDetailsInActivityTab() throws Exception {
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();

		// Navigate to Workflows page
		dashboardPage.companyView(CommonVariables.LABS);
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
		waitForPageLoaded();
		if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
        LOGGER.info("Clear button clicked");
	    } else {
	        LOGGER.info("Clear button not present — skipping");
	    }
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
		sleeper(3000);
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("completedstatus");
		waitForPageLoaded();
		sleeper(3000);
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
		wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow",WorkflowVariables.CompletedWorkflowname);
		sleeper(3000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("WorkflowSearchItem");
		// Click on Activity tab
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowDetailsActivityTab");
		waitForPageLoaded();
		// Verify table columns       
		List<String> expectedColumns = Arrays.asList(
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "execution_id")).orElse("Execution ID"),
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "execution.status.column")).orElse("Execution Status"),
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "execution.started.column")).orElse("Execution Started"),
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "devices.gathered.column")).orElse("Devices Gathered"));
		resetTableConfiguration();
		wexWorkflowsPage.verifyElementsOfWorkflowsPage("ExecutionId");
		wexWorkflowsPage.verifyElementsOfWorkflowsPage("ExecutionStatus");
		wexWorkflowsPage.verifyElementsOfWorkflowsPage("ExecutionStarted");
		wexWorkflowsPage.verifyElementsOfWorkflowsPage("Devicesgathered");
        Assert.assertTrue(wexWorkflowsPage.verifyColumnsinActivityPage(expectedColumns,"Execution"), "Table Columns are not as expected");	
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Executionidfirstrow");
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Selectdevice");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("DeviceExecutionTimeLine");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("DeviceName");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("TimeElapsed");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("started");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("completed");
		Assert.assertTrue(wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("SelectTimeAndAudience"),
				"SelectTimeAndAudience is not displayed");
		Assert.assertTrue(wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("WindowsFirewallCompliance"),
				"Windowsfirewallcompliance is not displayed");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("StepId");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Frequency");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Trigger");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Group");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("WindowsStepID");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("StandardOutput");
		Assert.assertTrue(wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Exitcode"),"Exitcode is not zero");
		Assert.assertTrue(wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("IfThenElse"),
				"IfthenElse tab is not displayed");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("ConditionSource");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("If");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Then");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Else");
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("End");
	}
	
	/**
	 * TC_C53352045:[WEP]>>verify User Can Create workflow for webhook
	 */
	@Test(priority = 05, groups = {"REGRESSION_WORKFLOWS" }, description="verify User Can Create workflow for webhook")
	public final void verifyUserCanCreateworkflowforwebhook() throws Exception {

	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	    leftSideMenuVerification();
	    waitForPageLoaded();
	    // Navigate to Workflows page
	    dashboardPage.companyView(CommonVariables.LABS);
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
	    waitForPageLoaded();
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
	    waitForPageLoaded();
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
	    wexWorkflowsPage.switchToNewTab();
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
		wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
		wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow",WorkflowVariables.WORKFLOWNAME+generateRandomNumber());
	    wexWorkflowsPage.actionClickOfWorkflowsPage("secheduleDropdown");
	    waitForPageLoaded();
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandAudience","DestinationSelectandAudience");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("groupsClick");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch","BS_Workflow_Static_Group");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.PowerMode);
	    sleeper(3000);
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DragFastboot");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    boolean ScriptOutputDetailsVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputDetails");
	    if (ScriptOutputDetailsVisible) {
	        LOGGER.info("ScriptOutputDetails already visible — last used device pre-filled, skipping device selection");
	    } else {
	        LOGGER.info("ScriptOutputDetails not visible — proceeding with device selection and test");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("SelcetTestDeviceButton");
	        String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_serial_number");
	        LOGGER.info(valueName);
	        Assert.assertNotNull(valueName, "Groups_Device_serial_number not found for environment: " + System.getProperty("environment"));
	        Assert.assertFalse(valueName.isEmpty(), "Groups_Device_serial_number is empty for environment: " + System.getProperty("environment"));
	        wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
	        sleeper(2000);
	        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
	        waitForPageLoaded();
	        wexWorkflowsPage.getElementOfWorkflowsPage("TextExitcode");
	        wexWorkflowsPage.getElementOfWorkflowsPage("Exitcode");
	    }
	    wexWorkflowsPage.clickonblankpage();
	    // Device Action Section
	    wexWorkflowsPage.actionClickOfWorkflowsPage("DeviceAction");
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceDriverupdate","DestinationDriverupdate");
	    // Webhook Section
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ConnectorDropdown");
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceWebhook","DestinationWebhook");
	    waitForPageLoaded();
	    wexWorkflowsPage.enterTextOnWorkflowsPage("webhookName",WorkflowVariables.WEBHOOKNAME+generateRandomNumber());
		waitForPageLoaded();
		wexWorkflowsPage.enterTextOnWorkflowsPage("webhookDescription","Regression_Webhook_IST");
		wexWorkflowsPage.actionClickOfWorkflowsPage("importSwagger");
		wexWorkflowsPage.enterTextOnWorkflowsPage("SwaggerURL",WorkflowVariables.SwaggerURL);
		sleeper(3000);
		wexWorkflowsPage.actionClickOfWorkflowsPage("ImportButton");
		wexWorkflowsPage.actionClickOfWorkflowsPage("SelectAPIEndpoint");
		sleeper(3000);
		wexWorkflowsPage.enterTextOnWorkflowsPage("APIsearchitem",WorkflowVariables.PostMethod);
		wexWorkflowsPage.actionClickOfWorkflowsPage("SelectPost");
		wexWorkflowsPage.clearTextOfWorkflowsPage("SwaggerrTargetURL");
		wexWorkflowsPage.enterTextOnWorkflowsPage("SwaggerrTargetURL",WorkflowVariables.TargetURL);
		sleeper(3000);
		wexWorkflowsPage.actionClickOfWorkflowsPage("SelectAuthorizationTab");
		wexWorkflowsPage.enterTextOnWorkflowsPage("UsenameInput",WorkflowVariables.Username);
		sleeper(3000);
		wexWorkflowsPage.enterTextOnWorkflowsPage("PasswordInput",WorkflowVariables.Password);
		wexWorkflowsPage.actionClickOfWorkflowsPage("PayloadTab");
		waitForPageLoaded();
		sleeper(3000);
		wexWorkflowsPage.clearTextOfWorkflowsPage("PaylaodInput");
		String jsonpath = ConstantPath.JSON_PATH;
		String payload = wexWorkflowsPage.getJsonData(jsonpath);
		LOGGER.info("Payload:\n" + payload);
		wexWorkflowsPage.enterJsonPayloadToTextarea(payload);
		sleeper(3000);
		wexWorkflowsPage.actionClickOfWorkflowsPage("Fixformat");
		sleeper(3000);
		wexWorkflowsPage.actionClickOfWorkflowsPage("TestButton");
		waitForPageLoaded();
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Statuswebhook");
		waitForPageLoaded();
		wexWorkflowsPage.actionClickOfWorkflowsPage("publishButton");
	    // Verify Test Success Status
		Assert.assertTrue(wexWorkflowsPage.getTextOfWorkflowsPage("PublishWorflow").contains("Workflow published"),
				"workflow published is failed");  
	}
	
	
	/**
     * Verify the workflow timestamp and  complete status in Activity tab
     */
     
    @Test(priority = 6, groups = {"REGRESSION_WORKFLOWS"}, description = "Verify Workflow time stamp and complete status Details Activity Tab")
    public void VerifytheworkflowtimestampandcompletestatusinActivitytab() throws Exception {
    		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();

	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	    leftSideMenuVerification();
	    waitForPageLoaded();

	    // Navigate to Workflows page
	    dashboardPage.companyView(CommonVariables.LABS);

	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
	    waitForPageLoaded();
	    if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
	        LOGGER.info("Clear button clicked");
	    } else {
	        LOGGER.info("Clear button not present — skipping");
	    }
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("completedstatus");
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
        wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow",WorkflowVariables.workflowname);
        sleeper(3000);
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("WorkflowSearchItem");
        wexWorkflowsPage.verifyElementsOfWorkflowsPage("Completed");
     // Click on Activity tab
     	wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowDetailsActivityTab");
     		// Verify table columns 
     	List<String> expectedColumns = Arrays.asList(
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "execution_id"))
		                .orElse("Execution ID"),
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "execution.status.column"))
		                .orElse("Execution Status"),
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "execution.started.column"))
		                .orElse("Execution Started"),
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "devices.gathered.column"))
		                .orElse("Devices Gathered"));
		resetTableConfiguration();
 		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Executionidfirstrow");
 		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Selectdevice");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("DeviceExecutionTimeLine");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("DeviceName");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("TimeElapsed");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("started");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("completed");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("timecompleted");
 		Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("SelectTimeAndAudience"),
 				"SelectTimeAndAudience is not displayed");
 		Assert.assertTrue(wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("fastbootEnable"),
 				"Enablefastboot is not displayed");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("fastbootstepid");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("fastbootstandardop");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("fastbootexitcode");
 		Assert.assertTrue(wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Exitcode"),"Exitcode is not zero");
 		Assert.assertTrue(wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Driverupdate"),
 				"Driverupdate is not displayed");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Driverupdatestepid");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Driversupdated");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Deploy");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("output");
 		Assert.assertTrue(wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Exitcode"),"Exitcode is not zero");
 		Assert.assertTrue(wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Newwebook"),
 				"Newwebook is not displayed");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Newwebbokstepid");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Newwebboktype");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("HttpStatuscode");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Httpmethod");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("output");
 		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("End");  
    }

    /**
     * Verify Workflow execution device status
     */
    
    @Test(priority = 7, groups = {"REGRESSION_WORKFLOWS"}, description = "Verify Workflow execution device status")
    public void Verifythestatusexecutiondevicedetailsoftheworkflow() throws Exception {
    		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	    leftSideMenuVerification();
	    waitForPageLoaded();
	    // Navigate to Workflows page
	    dashboardPage.companyView(CommonVariables.LABS);
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
	    waitForPageLoaded();
	    if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
	        LOGGER.info("Clear button clicked");
	    } else {
	        LOGGER.info("Clear button not present — skipping");
	    }
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("completedstatus");
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
        wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow",WorkflowVariables.workflowname);
        sleeper(2000);
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("WorkflowSearchItem");
        wexWorkflowsPage.verifyElementsOfWorkflowsPage("Completed");
     // Click on Activity tab
     	wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowDetailsActivityTab");
 		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Executionidfirstrow");
 		waitForPageLoaded();
 		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("StatusExecution");
 		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Completeddevice");		
 		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearfilters");
 		wexWorkflowsPage.verifyElementsOfWorkflowsPage("completestatus");
 		wexWorkflowsPage.doubleClickOnWorkflowsPage("StatusExecution");
 		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Inprogresdevice");
 		wexWorkflowsPage.verifyElementsOfWorkflowsPage("Noresults");	
 		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearfilters");
 		wexWorkflowsPage.doubleClickOnWorkflowsPage("StatusExecution");
 		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Erroreddevice");
 		wexWorkflowsPage.verifyElementsOfWorkflowsPage("Noresults");
 		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearfilters");
 		wexWorkflowsPage.doubleClickOnWorkflowsPage("StatusExecution");
 		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Cancelleddevice");
 		wexWorkflowsPage.verifyElementsOfWorkflowsPage("Noresults");
		Assert.assertTrue(wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Noresults"),"No results is not displayed");
    }
    
    /**
     * Verify edit button for the audience in overview tab
     */
    @Test(priority = 8, groups = {"REGRESSION_WORKFLOWS"}, description = "Verify edit button for the audience in overview tab")
    public void Verifyeditbuttonfortheaudienceinoverviewtab() throws Exception {
    		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	    leftSideMenuVerification();
	    waitForPageLoaded();
	    // Navigate to Workflows page
	    dashboardPage.companyView(CommonVariables.LABS);
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("workflowTryNow");
	    waitForPageLoaded();
	    sleeper(3000);
	    if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
	        LOGGER.info("Clear button clicked");
	    } else {
	        LOGGER.info("Clear button not present — skipping");
	    }
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("completedstatus");
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("TriggerType");
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("completedtype");
	    sleeper(3000);
	    Assert.assertTrue(wexWorkflowsPage.getElementsOfWorkflowsPage("workflowTableRows").size() > 0,
                "No workflows found in the table");  
        // Click on first workflow name
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowNameRowValue");
        if (wexWorkflowsPage.waitUntilElementIsInvisibleOfWorkflowsPage("EditButton")) {
			LOGGER.info("edit button is not visible, proceeding...");
		} else {
			LOGGER.info("edit Button is still visible.");
		}
    }
    
    /**
     * Verify ative button for the audience in overview tab in active workflow
     */
    @Test(priority = 9, groups = {"REGRESSION_WORKFLOWS"}, description = "Verify ative button for the audience in overview tab in active workflow")
    public void VerifyeditbuttonfortheaudienceinoverviewtabforActiveworkflow() throws Exception {
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
    leftSideMenuVerification();
    waitForPageLoaded();
    // Navigate to Workflows page
    dashboardPage.companyView(CommonVariables.LABS);
    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("workflowTryNow");
    waitForPageLoaded();
    sleeper(3000);
    if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
        LOGGER.info("Clear button clicked");
    } else {
        LOGGER.info("Clear button not present — skipping");
    } 
    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
    sleeper(2000);
    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("Active");
    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
    wexWorkflowsPage.verifyElementsOfWorkflowsPage("Addworkflow");   
    wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow",WorkflowVariables.ActiveWorkflowname);
    sleeper(3000);
    wexWorkflowsPage.actionClickOfWorkflowsPage("WorkflowSearchItem");
    waitForPageLoaded();
    sleeper(3000);
    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("ActiveStatus");
    if (wexWorkflowsPage.waitUntilElementIsInvisibleOfWorkflowsPage("EditButton")) {
		LOGGER.info("edit Button is still visible.");
	} else {
		LOGGER.info("edit button is not visible, proceeding...");
	}
    wexWorkflowsPage.matchTextOfWorkflowPage("Audiencegroupname",WorkflowVariables.ExpectedResults);
    wexWorkflowsPage.actionClickOfWorkflowsPage("EditButton");
    wexWorkflowsPage.actionClickOfWorkflowsPage("toogleButton");
    wexWorkflowsPage.verifyElementsOfWorkflowsPage("groups");
    wexWorkflowsPage.verifyElementsOfWorkflowsPage("devices");
    wexWorkflowsPage.actionClickOfWorkflowsPage("toogleButton");
    waitForPageLoaded();
    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch",WorkflowVariables.latestGroup_Name);
    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch",WorkflowVariables.GroupName);
    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch",WorkflowVariables.Group_Name);
    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("updateButton");
    Assert.assertTrue(wexWorkflowsPage.getTextOfWorkflowsPage("updatedgroups").contains("Update Successful"),
			"update sucessful is failed");
    dashboardPage.companyView(CommonVariables.LABS);
    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("workflowTryNow");
    waitForPageLoaded();
    sleeper(3000);
    wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow",WorkflowVariables.ActiveWorkflowname);
    wexWorkflowsPage.actionClickOfWorkflowsPage("WorkflowSearchItem");
    waitForPageLoaded();
    sleeper(3000);
    wexWorkflowsPage.matchTextOfWorkflowPage("Audiencegroupname",WorkflowVariables.updatedExpectedResults);
    sleeper(3000);
    wexWorkflowsPage.actionClickOfWorkflowsPage("EditButton");
    wexWorkflowsPage.actionClickOfWorkflowsPage("toogleButton");
    wexWorkflowsPage.verifyElementsOfWorkflowsPage("updatedgroups");
    wexWorkflowsPage.verifyElementsOfWorkflowsPage("updateddevices");
    wexWorkflowsPage.actionClickOfWorkflowsPage("toogleButton");
    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch",WorkflowVariables.latestGroup_Name);
    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch",WorkflowVariables.GroupName);
    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch",WorkflowVariables.Group_Name);
    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("updateButton");
    Assert.assertTrue(wexWorkflowsPage.getTextOfWorkflowsPage("updatedgroups").contains("Update Successful"),
			"update sucessful is failed");
    waitForPageLoaded();
    sleeper(3000);
    wexWorkflowsPage.matchTextOfWorkflowPage("Audiencegroupname",WorkflowVariables.ExpectedResults);   
    }
    
    /**
     * Cretae duplicate form existing workflow and verify the details
     */
    
    @Test(priority = 10, groups = {"REGRESSION_WORKFLOWS"}, description = "Cretae duplicate form existing workflow and verify the details")
    public void Createduplicatewththeexistingworkflow() throws Exception {
    	WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	    leftSideMenuVerification();
	    waitForPageLoaded();
	    // Navigate to Workflows page
	    dashboardPage.companyView(CommonVariables.LABS);
	    sleeper(3000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("workflowTryNow");
	    waitForPageLoaded();
	    if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
	        LOGGER.info("Clear button clicked");
	    } else {
	        LOGGER.info("Clear button not present — skipping");
	    }
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
	    sleeper(2000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("Active");
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow",WorkflowVariables.ActiveWorkflowname);
	    sleeper(3000);
	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("WorkflowSearchItem");
	    sleeper(2000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("metaball");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("duplicate");
	    waitForPageLoaded();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("duplicatebutton");
	    Assert.assertTrue(wexWorkflowsPage.getTextOfWorkflowsPage("DuplicatedWorkflow").contains("Workflow duplicated"),
				"workflow duplicated is failed");
	    sleeper(2000);
	    dashboardPage.companyView(CommonVariables.LABS);
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("workflowTryNow");
	    sleeper(5000);
	    if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
	        LOGGER.info("Clear button clicked");
	    } else {
	        LOGGER.info("Clear button not present — skipping");
	    }
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("draftDropdown");
	    sleeper(3000);
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("WorkflowSearchItem");
	    waitForPageLoaded();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("SelectScriptNode");
	    boolean ScriptOutputDetailsVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputDetails");
	    if (ScriptOutputDetailsVisible) {
	        LOGGER.info("ScriptOutputDetails already visible — last used device pre-filled, skipping device selection");
	    } else {
	        LOGGER.info("ScriptOutputDetails not visible — proceeding with device selection and test");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("SelcetTestDeviceButton");
	        String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_serial_number");
	        LOGGER.info(valueName);
	        Assert.assertNotNull(valueName, "Groups_Device_serial_number not found for environment: " + System.getProperty("environment"));
	        Assert.assertFalse(valueName.isEmpty(), "Groups_Device_serial_number is empty for environment: " + System.getProperty("environment"));
	        wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
	        sleeper(2000);
	        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
	        waitForPageLoaded();
	        sleeper(8000);
	        wexWorkflowsPage.getElementOfWorkflowsPage("TextExitcode");
	        wexWorkflowsPage.getElementOfWorkflowsPage("Exitcode");
	    }
	    wexWorkflowsPage.clickonblankpage();
		wexWorkflowsPage.actionClickOfWorkflowsPage("ClickSelctAudience");
		waitForPageLoaded();
		wexWorkflowsPage.actionClickOfWorkflowsPage("SelectDropdown");
		waitForPageLoaded();
		wexWorkflowsPage.actionClickOfWorkflowsPage("runatsechedule");
		wexWorkflowsPage.actionClickOfWorkflowsPage("freqquencybutton");
		wexWorkflowsPage.actionClickOfWorkflowsPage("dailybutton");
		wexWorkflowsPage.selectNextDateFromCalendar();
		sleeper(2000);
		wexWorkflowsPage.actionClickOfWorkflowsPage("StartTimeDropdown");
		wexWorkflowsPage.actionClickOfWorkflowsPage("SelectStartTime");
		sleeper(3000);
		if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("includeenddate")) {
		    wexWorkflowsPage.actionClickOfWorkflowsPage("includeenddate");
		    LOGGER.info("Include End Date clicked");
		} else {
		    LOGGER.info("Include End Date not present — skipping");
		}
		wexWorkflowsPage.actionClickOfWorkflowsPage("enddate");
		wexWorkflowsPage.actionClickOfWorkflowsPage("nextButton");
		wexWorkflowsPage.actionClickOfWorkflowsPage("EndTimedropdown");
		wexWorkflowsPage.actionClickOfWorkflowsPage("SelectStartTime");
		wexWorkflowsPage.clickonblankpage();
		wexWorkflowsPage.actionClickOfWorkflowsPage("WebhookClick");
		wexWorkflowsPage.getTextOfWorkflowsPage("WebhooknameModified");
		sleeper(3000);
		wexWorkflowsPage.actionClickOfWorkflowsPage("TestButton");;
		waitForPageLoaded();
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Statuswebhook");
		sleeper(2000);
		wexWorkflowsPage.actionClickOfWorkflowsPage("publishButton");
	    // Verify Test Success Status
		Assert.assertTrue(wexWorkflowsPage.getTextOfWorkflowsPage("PublishWorflow").contains("Workflow published"),
				"workflow published is failed");
		dashboardPage.companyView(CommonVariables.LABS);
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("workflowTryNow");
	    waitForPageLoaded();
	    sleeper(3000);
	    if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
	        LOGGER.info("Clear button clicked");
	    } else {
	        LOGGER.info("Clear button not present — skipping");
	    }
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
	    sleeper(3000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("Active");
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow",WorkflowVariables.ActiveWorkflownamecopy);
	    sleeper(3000);
	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("WorkflowSearchItem");
	    sleeper(2000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("metaball");
	    sleeper(2000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("CancelButton");
	    waitForPageLoaded();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("CancelWorkflow");
	    Assert.assertTrue( wexWorkflowsPage.getTextOfWorkflowsPage("CanceledWorkflow").contains("Workflow canceled"),"Workflow canceled message not displayed");	
    }
    
    /**
   	 * TC_C53352045:[WEP]>>verify User Can Create Event workflow for webhook
   	 */
   	@Test(priority = 11, groups = {"REGRESSION_WORKFLOWS" }, description="verify User Can Create Event workflow for webhook")
   	public final void verifyUserCanCreateEventworkflowforwebhook() throws Exception {

   	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
   	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
   	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   	    leftSideMenuVerification();
   	    waitForPageLoaded();
   	    // Navigate to Workflows page
   	    dashboardPage.companyView(CommonVariables.LABS);
   	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
   	    waitForPageLoaded();
   	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
   	    waitForPageLoaded();
   	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
   	    wexWorkflowsPage.switchToNewTab();
   	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
   		wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
   		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
   		wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow",WorkflowVariables.AlertWorkflow+generateRandomNumber());
   	    wexWorkflowsPage.actionClickOfWorkflowsPage("EventDropdown");
   	    sleeper(2000);
   	    wexWorkflowsPage.enterTextByJavaScriptOnWorkflowsPage("EventSearch",WorkflowVariables.windowsSecurity);
   	    sleeper(3000);
   	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceMultipleBSOD","DestinationEvent");
   	    wexWorkflowsPage.clickonblankpage();
   	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
   	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.DiskCleanupcript);
   	    sleeper(3000);
   	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DragFastboot");
   	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    boolean ScriptOutputDetailsVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputDetails");
	    if (ScriptOutputDetailsVisible) {
	        LOGGER.info("ScriptOutputDetails already visible — last used device pre-filled, skipping device selection");
	    } else {
	        LOGGER.info("ScriptOutputDetails not visible — proceeding with device selection and test");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("SelcetTestDeviceButton");
	        String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_serial_number");
	        LOGGER.info(valueName);
	        Assert.assertNotNull(valueName, "Groups_Device_serial_number not found for environment: " + System.getProperty("environment"));
	        Assert.assertFalse(valueName.isEmpty(), "Groups_Device_serial_number is empty for environment: " + System.getProperty("environment"));
	        wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
	        sleeper(2000);
	        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
	        waitForPageLoaded();
	        sleeper(8000);
	        wexWorkflowsPage.getElementOfWorkflowsPage("TextExitcode");
	        wexWorkflowsPage.getElementOfWorkflowsPage("Exitcode");
	    }
   	    wexWorkflowsPage.clickonblankpage();
   	    // Device Action Section
   	    wexWorkflowsPage.actionClickOfWorkflowsPage("DeviceAction");
   	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceDriverupdate","DestinationDriverupdate");
   	    // Webhook Section
   	    wexWorkflowsPage.clickonblankpage();
   	    wexWorkflowsPage.actionClickOfWorkflowsPage("ConnectorDropdown");
   	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceWebhook","DestinationWebhook");
   	    waitForPageLoaded();
   	    wexWorkflowsPage.enterTextOnWorkflowsPage("webhookName",WorkflowVariables.WEBHOOKNAME+generateRandomNumber());
   		waitForPageLoaded();
   		wexWorkflowsPage.actionClickOfWorkflowsPage("importSwagger");
		wexWorkflowsPage.enterTextOnWorkflowsPage("SwaggerURL",WorkflowVariables.SwaggerURL);
		sleeper(3000);
		wexWorkflowsPage.actionClickOfWorkflowsPage("ImportButton");
		wexWorkflowsPage.actionClickOfWorkflowsPage("SelectAPIEndpoint");
		sleeper(3000);
		wexWorkflowsPage.enterTextOnWorkflowsPage("APIsearchitem",WorkflowVariables.PostMethod);
		wexWorkflowsPage.actionClickOfWorkflowsPage("SelectPost");
		wexWorkflowsPage.clearTextOfWorkflowsPage("SwaggerrTargetURL");
		wexWorkflowsPage.enterTextOnWorkflowsPage("SwaggerrTargetURL",WorkflowVariables.TargetURL);
		sleeper(3000);
		wexWorkflowsPage.actionClickOfWorkflowsPage("SelectAuthorizationTab");
		wexWorkflowsPage.enterTextOnWorkflowsPage("UsenameInput",WorkflowVariables.Username);
		sleeper(3000);
		wexWorkflowsPage.enterTextOnWorkflowsPage("PasswordInput",WorkflowVariables.Password);
		wexWorkflowsPage.actionClickOfWorkflowsPage("PayloadTab");
		waitForPageLoaded();
		sleeper(3000);
		wexWorkflowsPage.clearTextOfWorkflowsPage("PaylaodInput");
		String jsonpath = ConstantPath.JSON_PATH;
		String payload = wexWorkflowsPage.getJsonData(jsonpath);
		LOGGER.info("Payload:\n" + payload);
		wexWorkflowsPage.enterJsonPayloadToTextarea(payload);
		sleeper(3000);
		wexWorkflowsPage.actionClickOfWorkflowsPage("Fixformat");
		sleeper(3000);
		wexWorkflowsPage.actionClickOfWorkflowsPage("TestButton");
		waitForPageLoaded();
		wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Statuswebhook");
		waitForPageLoaded();
		wexWorkflowsPage.actionClickOfWorkflowsPage("publishButton");
	    // Verify Test Success Status
		Assert.assertTrue(wexWorkflowsPage.getTextOfWorkflowsPage("PublishWorflow").contains("Workflow published"),
				"workflow published is failed");    
   	}
   	
    /**
   	 * C84918081:[WEP]>>Verify initial warning when Scrip tOutput expanded no prior test
   	 */
   	@Test(priority = 12, groups = {"REGRESSION_WORKFLOWS" }, description="Verify initial warning when Script Output expanded no prior test")
   	public final void VerifyinitialwarningwhenScriptOutputexpanded() throws Exception {

   	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
   	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
   	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   	    leftSideMenuVerification();
   	    waitForPageLoaded();
   	    // Navigate to Workflows page
   	    dashboardPage.companyView(CommonVariables.LABS);
   	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
   	    waitForPageLoaded();
   	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
   	    waitForPageLoaded();
   	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
   	    wexWorkflowsPage.switchToNewTab();
   	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
   		wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
   		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
   		wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow",WorkflowVariables.ScriptWorkflow+generateRandomNumber());
   	    wexWorkflowsPage.actionClickOfWorkflowsPage("secheduleDropdown");
	    waitForPageLoaded();
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandAudience","DestinationSelectandAudience");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("groupsClick");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch","BS_Workflow_Static_Group");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.ImageAssistant);
	    sleeper(3000);
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DragFastboot");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    sleeper(2000);
	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
	    boolean tooltipOnErrorVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
  	    Assert.assertTrue(tooltipOnErrorVisible,
  	            "Tooltip message should be visible after hovering on Script Output in error state");
	    boolean scripttestingVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("Scripttesting");
	    Assert.assertTrue(scripttestingVisible,
	            "Tooltip message should be visible after hovering on Script Output in error state");
	    if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("ErrorSymbol")) {
	        LOGGER.info("Error symbol present");
	    } else {
	    	 LOGGER.info("Error count remains 0 in panel");
	    }
   	}
   	
   	/**
   	 * C84918081:[WEP]>>Verify initial warning when Scrip tOutput expanded no prior test
   	 */
   	@Test(priority = 13, groups = {"REGRESSION_WORKFLOWS" }, description="Verify error escalation after closing panel without testing")
   	public final void Verifyerrorescalationafterclosingpanelwithouttesting() throws Exception {

   	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
   	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
   	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   	    leftSideMenuVerification();
   	    waitForPageLoaded();
   	    // Navigate to Workflows page
   	    dashboardPage.companyView(CommonVariables.LABS);
   	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
   	    waitForPageLoaded();
   	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
   	    waitForPageLoaded();
   	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
   	    wexWorkflowsPage.switchToNewTab();
   	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
   		wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
   		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
   		wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow",WorkflowVariables.ScriptOutputWorkflow+generateRandomNumber());
   	    wexWorkflowsPage.actionClickOfWorkflowsPage("secheduleDropdown");
	    waitForPageLoaded();
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandAudience","DestinationSelectandAudience");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("groupsClick");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch","BS_Workflow_Static_Group");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.SecureBootscript);
	    sleeper(3000);
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DragFastboot");
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("Closesidepanel");
	    sleeper(3000);
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("Closesidepanel");
	    sleeper(2000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ClickSelctAudience");
	    
	    wexWorkflowsPage.actionClickOfWorkflowsPage("SelectScriptNode");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    sleeper(2000);
	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
	    boolean tooltipOnErrorVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
  	    Assert.assertTrue(tooltipOnErrorVisible,
  	            "Tooltip message should be visible after hovering on Script Output in error state");
	    boolean scripttestingVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("Scripttesting");
	    Assert.assertTrue(scripttestingVisible,
	            "Tooltip message should be visible after hovering on Script Output in error state");
	    boolean errorSymbolVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ErrorSymbol");
  	    Assert.assertTrue(errorSymbolVisible,
  	            "Error symbol should be displayed on the script node when execution fails");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("workflowbutton");
	    sleeper(3000);
	    wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow",WorkflowVariables.ScriptOutputWorkflow);
	    sleeper(3000);
	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("WorkflowSearchItem");
	    sleeper(2000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("metaball");
	    sleeper(2000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("workflowDelete");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("DeleteButton");
	    Assert.assertTrue(wexWorkflowsPage.getTextOfWorkflowsPage("deleteworkflow").contains("Workflow deleted"),
				"workflow deleted is failed");
   	}
   	
   	/**
  	 * C84918084:[WEP]>>Verify offline/deleted device error during test
  	 */
  	@Test(priority = 14, groups = {"REGRESSION_WORKFLOWS" }, description="Verify offline/deleted device error during test")
  	public final void Verifyofflinedeleteddeviceerrorduringtest() throws Exception {

  	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
  	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
  	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
  	    leftSideMenuVerification();
  	    waitForPageLoaded();
  	    // Navigate to Workflows page
  	    dashboardPage.companyView(CommonVariables.LABS);
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
  	    wexWorkflowsPage.switchToNewTab();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  		wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
  		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  		wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow",WorkflowVariables.ScriptOutputWorkflow+generateRandomNumber());
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("secheduleDropdown");
	    waitForPageLoaded();
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandAudience","DestinationSelectandAudience");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("groupsClick");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch","BS_Workflow_Static_Group");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.AppDeploy);
	    sleeper(3000);
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DragFastboot");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    boolean ScriptOutputDetailsVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputDetails"); 
	    if (ScriptOutputDetailsVisible) {
	        LOGGER.info("ScriptOutputDetails already visible — last used device pre-filled, skipping device selection");
	    } else {
	        LOGGER.info("ScriptOutputDetails not visible — proceeding with device selection and test");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("ChangeDevice");
	        String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_Inactive_serial_number");
	        LOGGER.info(valueName);
	        Assert.assertNotNull(valueName, "Groups_Device_Inactive_serial_number not found for environment: " + System.getProperty("environment"));
	        Assert.assertFalse(valueName.isEmpty(), "Groups_Device_Inactive_serial_number is empty for environment: " + System.getProperty("environment"));
	        wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
	        sleeper(2000);
	        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
	    }
	    waitForPageLoaded();
	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptTestFailedMsg");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ClickSelctAudience");
	    sleeper(2000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("SelectScriptNode");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    waitForPageLoaded();
	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("offlinemsg");
	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("ErrorMSg");
	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("ChangeDevice");
	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
	    boolean tooltipScriptoutputVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipScriptoutput");
  	    Assert.assertTrue(tooltipScriptoutputVisible,
  	            "Tooltip message should be visible after hovering on Script Output in error state");
	    boolean tooltipOnErrorVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
  	    Assert.assertTrue(tooltipOnErrorVisible,
  	            "Tooltip message should be visible after hovering on Script Output in error state");
	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("Scripttesting");
  	  boolean scripttestingVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("Scripttesting");
	    Assert.assertTrue(scripttestingVisible,
	            "Tooltip message should be visible after hovering on Script Output in error state");
	    boolean errorSymbolVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ErrorSymbol");
  	    Assert.assertTrue(errorSymbolVisible,
  	            "Error symbol should be displayed on the script node when execution fails");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("publishButton");
	    boolean publishErrorMsgVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("PublishErrorMsg");
  	    Assert.assertTrue(publishErrorMsgVisible,
  	            "Publish error message should be displayed when publishing with a failed script");
	    boolean scriptErrorMsgVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("scriptErrormsg");
  	    Assert.assertTrue(scriptErrorMsgVisible,
  	            "Script error message should be displayed in publish error details");
  	 }
  	
  	/**
  	 * C84918086:[WEP]>>Verify successful test clears errors
  	 */
  	@Test(priority = 15, groups = {"REGRESSION_WORKFLOWS" }, description="Verify successful test clears errors")
  	public final void Verifysuccessfultestclearserrors() throws Exception {

  	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
  	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
  	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
  	    leftSideMenuVerification();
  	    waitForPageLoaded();
  	    // Navigate to Workflows page
  	    dashboardPage.companyView(CommonVariables.LABS);
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
  	    wexWorkflowsPage.switchToNewTab();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  		wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
  		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  		wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow",WorkflowVariables.ScriptWorkflow+generateRandomNumber());
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("secheduleDropdown");
	    waitForPageLoaded();
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandAudience","DestinationSelectandAudience");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("groupsClick");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch","BS_Workflow_Static_Group");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.InvokeRestartScript);
	    sleeper(3000);
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DragFastboot");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    boolean ScriptOutputDetailsVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputDetails");
	    if (ScriptOutputDetailsVisible) {
	        LOGGER.info("ScriptOutputDetails already visible — last used device pre-filled, skipping device selection");
	    } else {
	        LOGGER.info("ScriptOutputDetails not visible — proceeding with device selection and test");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("ChangeDevice");
	        String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_serial_number");
	        LOGGER.info(valueName);
	        Assert.assertNotNull(valueName, "Groups_Device_serial_number not found for environment: " + System.getProperty("environment"));
	        Assert.assertFalse(valueName.isEmpty(), "Groups_Device_serial_number is empty for environment: " + System.getProperty("environment"));
	        wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
	        sleeper(2000);
	        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
	        waitForPageLoaded();
	        sleeper(8000);
	        wexWorkflowsPage.getElementOfWorkflowsPage("TextExitcode");
	        wexWorkflowsPage.getElementOfWorkflowsPage("Exitcode");
	    }
	    wexWorkflowsPage.getElementOfWorkflowsPage("TestAgainButton");
	    boolean ScriptOutputSucessMsgVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputSucessMsg");
  	    Assert.assertTrue(ScriptOutputSucessMsgVisible,
  	            "ScriptOutputSucessMsgVisible message should be visible after hovering on Script Output in error state");
	    boolean changeDeviceVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ChangeDevice");
  	    Assert.assertTrue(changeDeviceVisible,
  	            "'Change Device' option should be visible when script execution fails");
		if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("ErrorSymbol")) {
		    System.out.println("Error symbol is displayed");
		} else {
		    System.out.println("Error symbol is not displayed, continuing execution");
		}
	    wexWorkflowsPage.actionClickOfWorkflowsPage("publishButton");
	 // Verify Test Success Status
 		Assert.assertTrue(wexWorkflowsPage.getTextOfWorkflowsPage("PublishWorflow").contains("Workflow published"),
 				"workflow published is failed");  
  	 }
  	
  	/**
  	 * C84918085:[WEP]>>Verify online device but script execution fails
  	 */
  	@Test(priority = 16, groups = {"REGRESSION_WORKFLOWS" }, description="Verify online device but script execution fails")	
  	public final void Verifyonlinedevicebutscriptexecutionfails() throws Exception {

  	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
  	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();

  	    // Step 1: Login and verify
  	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
  	    leftSideMenuVerification();
  	    waitForPageLoaded();

  	    // Step 2: Navigate to Workflows page
  	    dashboardPage.companyView(CommonVariables.LABS);
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
  	    wexWorkflowsPage.switchToNewTab();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  	    wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  	    String workflowName = WorkflowVariables.ScriptOutputWorkflow + generateRandomNumber();
  	    wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow", workflowName);
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("secheduleDropdown");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandAudience", "DestinationSelectandAudience");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("groupsClick");
  	    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch", "BS_Workflow_Static_Group");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
  	    wexWorkflowsPage.clickonblankpage();
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
  	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch", WorkflowVariables.Repairzoom);
  	    sleeper(3000);
  	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot", "DragFastboot");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
  	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
  	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("Scripttesting");
  	    boolean ScriptOutputDetailsVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptTestFailedMsg");
	    if (ScriptOutputDetailsVisible) {
	        LOGGER.info("ScriptTestFailedMsg already visible — last used device pre-filled, skipping device selection");
	    } else {
	        LOGGER.info("ScriptOutputDetails not visible — proceeding with device selection and test");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("ChangeDevice");
	        String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_serial_number");
	        LOGGER.info(valueName);
	        Assert.assertNotNull(valueName, "Groups_Device_serial_number not found for environment: " + System.getProperty("environment"));
	        Assert.assertFalse(valueName.isEmpty(), "Groups_Device_serial_number is empty for environment: " + System.getProperty("environment"));
	        wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
	        sleeper(2000);
	        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
	        waitForPageLoaded();
	    }
  	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptTestFailedMsg");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("ClickSelctAudience");
  	    sleeper(2000);
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("SelectScriptNode");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("offlinemsg");
  	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("ErrorMSg");
  	    boolean changeDeviceVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ChangeDevice");
  	    Assert.assertTrue(changeDeviceVisible,
  	            "'Change Device' option should be visible when script execution fails");
  	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
  	    boolean tooltipOnErrorVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
  	    Assert.assertTrue(tooltipOnErrorVisible,
  	            "Tooltip message should be visible after hovering on Script Output in error state");
  	    boolean scriptTestingVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("Scripttesting");
  	    Assert.assertTrue(scriptTestingVisible,
  	            "Script testing element should be visible in error state");
  	    boolean errorSymbolVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ErrorSymbol");
  	    Assert.assertTrue(errorSymbolVisible,
  	            "Error symbol should be displayed on the script node when execution fails");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("publishButton");
  	    boolean publishErrorMsgVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("PublishErrorMsg");
  	    Assert.assertTrue(publishErrorMsgVisible,
  	            "Publish error message should be displayed when publishing with a failed script");
  	    boolean scriptErrorMsgVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("scriptErrormsg");
  	    Assert.assertTrue(scriptErrorMsgVisible,
  	            "Script error message should be displayed in publish error details");
  	}

  	/**
  	 * C84918086:[WEP]>>Verify Select Test Device table sorting and search
  	 */
  	@Test(priority = 17, groups = {"REGRESSION_WORKFLOWS" }, description="Verify Select Test Device table sorting and search") 	
  	public final void VerifySelectTestDevicetablesortingandsearch() throws Exception {

  	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
  	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();

  	    // Step 1: Login and verify
  	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
  	    leftSideMenuVerification();
  	    waitForPageLoaded();

  	    // Step 2: Navigate to Workflows page
  	    dashboardPage.companyView(CommonVariables.LABS);
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow"); 	    
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");   
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
  	    wexWorkflowsPage.switchToNewTab();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  	    wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  	    String workflowName = WorkflowVariables.ScriptWorkflow + generateRandomNumber();
  	    wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow", workflowName);
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("secheduleDropdown");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandAudience", "DestinationSelectandAudience");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("groupsClick");
  	    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch", "BS_Workflow_Static_Group");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
  	    wexWorkflowsPage.clickonblankpage();
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
  	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch", WorkflowVariables.FaststartupScript);
  	    sleeper(3000);
  	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot", "DragFastboot");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
  	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
  	    boolean tooltipVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
  	    Assert.assertTrue(tooltipVisible, "Tooltip message should be visible after hovering on Script Output");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("Scripttesting");
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("SelcetTestDeviceButton");
  	    String expectedDeviceName = Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "device_name"))
  	            .orElse("Device Name");
  	    String expectedSerialNumber = Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "serial.number.column"))
  	            .orElse("Serial Number");
  	    String expectedLastSignedIn = Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "last.signed.in.user.column"))
  	            .orElse("Last Signed-In User");
  	    List<String> expectedColumns = Arrays.asList(expectedDeviceName, expectedSerialNumber, expectedLastSignedIn);
  	    LOGGER.info("Expected columns: {}", expectedColumns);
  	    boolean deviceNameHeaderVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("DeviceNameHeader");
  	    Assert.assertTrue(deviceNameHeaderVisible,
  	            "Device Name column header should be visible. Expected: " + expectedDeviceName);
  	    boolean serialNumberHeaderVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("SerialNumberHeader");
  	    Assert.assertTrue(serialNumberHeaderVisible,
  	            "Serial Number column header should be visible. Expected: " + expectedSerialNumber);
  	    boolean lastSignInUserVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("LastSigninUser");
  	    Assert.assertTrue(lastSignInUserVisible,
  	            "Last Signed-In User column header should be visible. Expected: " + expectedLastSignedIn);
  	    String actualDeviceNameHeader = wexWorkflowsPage.getTextOfWorkflowsPage("DeviceNameHeader");
  	    Assert.assertEquals(actualDeviceNameHeader, expectedDeviceName,
  	            "Device Name header text should match expected language value");
  	    String actualSerialNumberHeader = wexWorkflowsPage.getTextOfWorkflowsPage("SerialNumberHeader");
  	    Assert.assertEquals(actualSerialNumberHeader, expectedSerialNumber,
  	            "Serial Number header text should match expected language value");
  	    String actualLastSignInHeader = wexWorkflowsPage.getTextOfWorkflowsPage("LastSigninUser");
  	    Assert.assertEquals(actualLastSignInHeader, expectedLastSignedIn,
  	            "Last Signed-In User header text should match expected language value");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("devicetable");
  	    List<String> sortedDeviceNames = wexWorkflowsPage.getSortedDeviceNames();
  	    Assert.assertNotNull(sortedDeviceNames, "Sorted device names list should not be null");
  	    Assert.assertFalse(sortedDeviceNames.isEmpty(), "Device table should have at least one device after sorting");
  	    List<String> expectedSortedList = sortedDeviceNames.stream()
  	            .sorted(String.CASE_INSENSITIVE_ORDER)
  	            .collect(java.util.stream.Collectors.toList());
  	    Assert.assertEquals(sortedDeviceNames, expectedSortedList,
  	            "Device names should be sorted in ascending order after clicking column header");
  	    boolean deviceNameSearchVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("DeviceNameSearch");
  	    Assert.assertTrue(deviceNameSearchVisible, "Device Name search field should be visible in the table");
  	}

	/**
  	 * C84918088:[WEP]>>Verify dual error and warning when parameters changed and script untested
  	 */
  	@Test(priority = 18, groups = {"REGRESSION_WORKFLOWS" }, description="Verify dual error and warning when parameters changed and script untested")
  	public final void Verifydualerrorandwarningwhenparameterschangedandscriptuntested() throws Exception {

        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        leftSideMenuVerification();
        waitForPageLoaded();
        // Navigate to Workflows page
        dashboardPage.companyView(CommonVariables.LABS);
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
        waitForPageLoaded();
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
        waitForPageLoaded();
        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
        wexWorkflowsPage.switchToNewTab();
        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
        wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
        wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow", WorkflowVariables.ScriptWorkflow + generateRandomNumber());
        wexWorkflowsPage.actionClickOfWorkflowsPage("secheduleDropdown");
        waitForPageLoaded();
        wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandAudience", "DestinationSelectandAudience");
        wexWorkflowsPage.actionClickOfWorkflowsPage("groupsClick");
        wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch", "BS_Workflow_Static_Group");
        wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
        wexWorkflowsPage.clickonblankpage();
        wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
        wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch", WorkflowVariables.ScriptSearch);
        sleeper(3000);
        wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot", "DragFastboot");
        wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
        wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
        wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
        wexWorkflowsPage.verifyElementsOfWorkflowsPage("Scripttesting");
        sleeper(3000);
        wexWorkflowsPage.actionClickOfWorkflowsPage("ChangeDevice");
        String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_Inactive_serial_number");
	    LOGGER.info(valueName);
	    Assert.assertNotNull(valueName, "Groups_Device_Inactive_serial_number not found for environment: " + System.getProperty("environment"));
	    Assert.assertFalse(valueName.isEmpty(), "Groups_Device_Inactive_serial_number is empty for environment: " + System.getProperty("environment"));
	    wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
        sleeper(2000);
        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
        waitForPageLoaded();
        wexWorkflowsPage.verifyElementsOfWorkflowsPage("offlinemsg");
        wexWorkflowsPage.verifyElementsOfWorkflowsPage("ErrorMSg");
        wexWorkflowsPage.verifyElementsOfWorkflowsPage("ChangeDevice");        
        wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
        wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
        wexWorkflowsPage.verifyElementsOfWorkflowsPage("Scripttesting");
        wexWorkflowsPage.verifyElementsOfWorkflowsPage("ErrorSymbol");
        wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptTestFailedMsg");
        wexWorkflowsPage.actionClickOfWorkflowsPage("ClickSelctAudience");
        sleeper(2000);
        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectScriptNode");
        wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
        waitForPageLoaded();
        wexWorkflowsPage.actionClickOfWorkflowsPage("parameterDropdown");
        sleeper(2000);
        wexWorkflowsPage.clearTextOfWorkflowsPage("parameterString");
        wexWorkflowsPage.enterTextOnWorkflowsPage("QuerySearch", "test");
        // Assert parameter error symbol is displayed when parameter value is changed
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("ParameterErrorSymbol"),
                "FAILED: Parameter error symbol should appear when parameter is modified");
        // Assert script output error symbol is displayed
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptoutputErrorSymbol"),
                "FAILED: Script output error symbol should appear when parameter is changed and script is untested");
        // Assert script node error indicator is displayed
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptNodeerror"),
                "FAILED: Script node error should be displayed when parameters are changed and script is untested");
    }
  		
  	/**
  	 * C84918087:[WEP]>>Verify reuse of tested parameters across workflows
  	 */
  	@Test(priority = 19, groups = {"REGRESSION_WORKFLOWS" }, description="Verify reuse of tested parameters across workflows")
  	public final void Verifyreuseoftestedparametersacrossworkflows() throws Exception {

  		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
   	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
   	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   	    leftSideMenuVerification();
   	    waitForPageLoaded();
   	    // Navigate to Workflows page
   	    dashboardPage.companyView(CommonVariables.LABS);
   	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
   	    waitForPageLoaded();
   	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
   	    waitForPageLoaded();
   	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
   	    wexWorkflowsPage.switchToNewTab();
   	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
   		wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
   		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
   		wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow",WorkflowVariables.ReuseWorkflow + generateRandomNumber());
   	    wexWorkflowsPage.actionClickOfWorkflowsPage("secheduleDropdown");
	    waitForPageLoaded();
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandAudience","DestinationSelectandAudience");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("groupsClick");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch","BS_Workflow_Static_Group");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.Dellcommand);
	    sleeper(3000);
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DragFastboot");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    sleeper(2000);
	    boolean ScriptOutputSucessMsgVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputSucessMsg");
  	    Assert.assertTrue(ScriptOutputSucessMsgVisible,
  	            "ScriptOutputSucessMsgVisible message should be visible after hovering on Script Output in error state");
    }
  	
  	/**
  	 * C84918090:[WEP]>>Verify re-running test after success and device offline triggers test-required error
  	 */
  	@Test(priority = 20, groups = {"REGRESSION_WORKFLOWS" }, description="Verify re-running test after success and device offline triggers test-required error")
  	public void Verifyrerunningtestaftersuccessanddeviceofflinetriggerstestrequirederror() throws Exception {
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();

		// Navigate to Workflows page
		dashboardPage.companyView(CommonVariables.LABS);
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
		waitForPageLoaded();
		if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
        LOGGER.info("Clear button clicked");
	    } else {
	        LOGGER.info("Clear button not present — skipping");
	    }
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
		sleeper(3000);
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("draftDropdown");
		waitForPageLoaded();
		sleeper(3000);
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
		wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow",WorkflowVariables.ReuseWorkflow);
		sleeper(3000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("WorkflowSearchItem");
	    waitForPageLoaded();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("SelectScriptNode");
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("ScriptOutputButton");
	    waitForPageLoaded();
	    sleeper(2000);
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("ChangeDevice");
	    String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_Inactive_serial_number");
	    LOGGER.info(valueName);
	    Assert.assertNotNull(valueName, "Groups_Device_Inactive_serial_number not found for environment: " + System.getProperty("environment"));
	    Assert.assertFalse(valueName.isEmpty(), "Groups_Device_Inactive_serial_number is empty for environment: " + System.getProperty("environment"));
	    wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
        sleeper(2000);
        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");  
		waitForPageLoaded();
  	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
  	    boolean tooltipOnErrorVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
  	    Assert.assertTrue(tooltipOnErrorVisible,
  	            "Tooltip message should be visible after hovering on Script Output in error state");
  	    boolean scriptTestingVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("Scripttesting");
  	    Assert.assertTrue(scriptTestingVisible,
  	            "Script testing element should be visible in error state");
  	    boolean errorSymbolVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ErrorSymbol");
  	    Assert.assertTrue(errorSymbolVisible,
  	            "Error symbol should be displayed on the script node when execution fails");
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("SelectScriptNode");
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("ScriptOutputButton");
	    waitForPageLoaded();
	    sleeper(2000);
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("ChangeDevice");
	    String valueName1 = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_serial_number");
	    LOGGER.info(valueName1);
	    Assert.assertNotNull(valueName1, "Groups_Device_serial_number not found for environment: " + System.getProperty("environment"));
	    Assert.assertFalse(valueName1.isEmpty(), "Groups_Device_serial_number is empty for environment: " + System.getProperty("environment"));
	    wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName1);
	    sleeper(2000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
  	}

  	/**
  	 * C84918092:[WEP]>>Verify pre-workflow test device ownership (no cross-workflow impact)
  	 */
  	@Test(priority = 21, groups = {"REGRESSION_WORKFLOWS" }, description="Verify pre-workflow test device ownership (no cross-workflow impact)")
  	public final void Verifypreworkflowtestdeviceownershipnocrossworkflowimpact() throws Exception {

  	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
  	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
  	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
  	    leftSideMenuVerification();
  	    waitForPageLoaded();
  	    // Navigate to Workflows page
  	    dashboardPage.companyView(CommonVariables.LABS);
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
  	    wexWorkflowsPage.switchToNewTab();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  		wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
  		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  		wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow",WorkflowVariables.ScriptWorkflow+generateRandomNumber());
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("secheduleDropdown");
	    waitForPageLoaded();
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandAudience","DestinationSelectandAudience");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("groupsClick");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch","BS_Workflow_Static_Group");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.HealthManager);
	    sleeper(3000);
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DragFastboot");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    boolean ScriptOutputDetailsVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputDetails");
	    if (ScriptOutputDetailsVisible) {
	        LOGGER.info("ScriptOutputDetails already visible — last used device pre-filled, skipping device selection");
	    } else {
	        LOGGER.info("ScriptOutputDetails not visible — proceeding with device selection and test");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("ChangeDevice");
	        String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_serial_number");
	        LOGGER.info(valueName);
	        Assert.assertNotNull(valueName, "Groups_Device_serial_number not found for environment: " + System.getProperty("environment"));
	        Assert.assertFalse(valueName.isEmpty(), "Groups_Device_serial_number is empty for environment: " + System.getProperty("environment"));
	        wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
	        sleeper(2000);
	        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
	        waitForPageLoaded();
	        sleeper(8000);
	        wexWorkflowsPage.getElementOfWorkflowsPage("TextExitcode");
	        wexWorkflowsPage.getElementOfWorkflowsPage("Exitcode");
	    }
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.RepairTask);
	    sleeper(3000);
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DestinationDriverupdate");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
	    boolean ScriptOutputDetailsVisibled = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputDetails");
	    if (ScriptOutputDetailsVisibled) {
	        LOGGER.info("ScriptOutputDetails already visible — last used device pre-filled, skipping device selection");
	    } else {
	        LOGGER.info("ScriptOutputDetails not visible — proceeding with device selection and test");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("ChangeDevice");
	        String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_serial_number");
	        LOGGER.info(valueName);
	        Assert.assertNotNull(valueName, "Groups_Device_serial_number not found for environment: " + System.getProperty("environment"));
	        Assert.assertFalse(valueName.isEmpty(), "Groups_Device_serial_number is empty for environment: " + System.getProperty("environment"));
	        wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
	        sleeper(2000);
	        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
	        waitForPageLoaded();
	        sleeper(8000);
	        wexWorkflowsPage.getElementOfWorkflowsPage("TextExitcode");
	        wexWorkflowsPage.getElementOfWorkflowsPage("Exitcode");
	    }
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("SelectScriptNode");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    waitForPageLoaded();
	    boolean ScriptOutputDetails = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputDetails");
	    if (ScriptOutputDetails) {
	        LOGGER.info("ScriptOutputDetails already visible — last used device pre-filled, skipping device selection");
	    } else {
	        LOGGER.info("ScriptOutputDetails not visible — proceeding with device selection and test");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("ChangeDevice");
	        String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_serial_number");
	        LOGGER.info(valueName);
	        Assert.assertNotNull(valueName, "Groups_Device_serial_number not found for environment: " + System.getProperty("environment"));
	        Assert.assertFalse(valueName.isEmpty(), "Groups_Device_serial_number is empty for environment: " + System.getProperty("environment"));
	        wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
	        sleeper(2000);
	        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
	        waitForPageLoaded();
	        sleeper(8000);
	        wexWorkflowsPage.getElementOfWorkflowsPage("TextExitcode");
	        wexWorkflowsPage.getElementOfWorkflowsPage("Exitcode");
	    }
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.InvokeMgr);
	    sleeper(3000);
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DestinationWebhook");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
	    boolean ScriptOutput = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputDetails");
	    if (ScriptOutput) {
	        LOGGER.info("ScriptOutputDetails already visible — last used device pre-filled, skipping device selection");
	    } else {
	        LOGGER.info("ScriptOutputDetails not visible — proceeding with device selection and test");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("ChangeDevice");
	        String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_serial_number");
	        LOGGER.info(valueName);
	        Assert.assertNotNull(valueName, "Groups_Device_serial_number not found for environment: " + System.getProperty("environment"));
	        Assert.assertFalse(valueName.isEmpty(), "Groups_Device_serial_number is empty for environment: " + System.getProperty("environment"));
	        wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
	        sleeper(2000);
	        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
	        waitForPageLoaded();
	        sleeper(8000);
	        wexWorkflowsPage.getElementOfWorkflowsPage("TextExitcode");
	        wexWorkflowsPage.getElementOfWorkflowsPage("Exitcode");
	    }
	    boolean ScriptOutputSucessMsgVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("outputsucessmsg");
	    Assert.assertTrue(ScriptOutputSucessMsgVisible,
  	            "ScriptOutputSucessMsgVisible message should be visible after hovering on Script Output in error state");
	    
  	}
  	
	/**
  	 * C84918093:[WEP]>>Verify “last used device” defaults when creating a new workflow (script-specific)
  	 */
  	@Test(priority = 22, groups = {"REGRESSION_WORKFLOWS" }, description="Verify “last used device” defaults when creating a new workflow (script-specific)")
  	public final void Verifylastuseddevicedefaultswhencreatinganewworkflowscriptspecifict() throws Exception {

  	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
  	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
  	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
  	    leftSideMenuVerification();
  	    waitForPageLoaded();
  	    // Navigate to Workflows page
  	    dashboardPage.companyView(CommonVariables.LABS);
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
  	    wexWorkflowsPage.switchToNewTab();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  		wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
  		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  		wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow",WorkflowVariables.ScriptWorkflow+generateRandomNumber());
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("secheduleDropdown");
	    waitForPageLoaded();
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandAudience","DestinationSelectandAudience");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("groupsClick");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch","BS_Workflow_Static_Group");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.MSEdge);
	    sleeper(3000);
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DragFastboot");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    boolean ScriptOutputDetailsVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputDetails");
	    if (ScriptOutputDetailsVisible) {
	        LOGGER.info("ScriptOutputDetails already visible — last used device pre-filled, skipping device selection");
	    } else {
	        LOGGER.info("ScriptOutputDetails not visible — proceeding with device selection and test");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("ChangeDevice");
	        String valueName = getEnvironmentSpecificData(System.getProperty("environment"), "Groups_Device_serial_number");
	        LOGGER.info(valueName);
	        Assert.assertNotNull(valueName, "Groups_Device_serial_number not found for environment: " + System.getProperty("environment"));
	        Assert.assertFalse(valueName.isEmpty(), "Groups_Device_serial_number is empty for environment: " + System.getProperty("environment"));
	        wexWorkflowsPage.enterTextOnWorkflowsPage("SerialNumberSearch", valueName);
	        sleeper(2000);
	        wexWorkflowsPage.actionClickOfWorkflowsPage("SelectRadiobutton");
	        wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("TestScriptButton");
	        waitForPageLoaded();
	        sleeper(8000);
	        wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptTestSuccessMsg");
	        wexWorkflowsPage.getElementOfWorkflowsPage("TextExitcode");
	        wexWorkflowsPage.getElementOfWorkflowsPage("Exitcode");
	    }
	    waitForPageLoaded(); 
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("workflowbutton");
  	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
  	    waitForPageLoaded();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("CreateScratch");
  	    wexWorkflowsPage.switchToDifferentTab();
  	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  		wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
  		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  		wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow",WorkflowVariables.ScriptWorkflow+generateRandomNumber());
  	    wexWorkflowsPage.actionClickOfWorkflowsPage("secheduleDropdown");
	    waitForPageLoaded();
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandAudience","DestinationSelectandAudience");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("groupsClick");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("groupSearch","BS_Workflow_Static_Group");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("selectCheckbox");
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.MSEdge);
	    sleeper(3000);
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DragFastboot");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
	    boolean ScriptOutputSucessMsgVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputDetails");
  	    Assert.assertTrue(ScriptOutputSucessMsgVisible,
  	            "ScriptOutputSucessMsgVisible message should be visible after hovering on Script Output in error state");
	    
  	}
  		
  	/**
  	 * C84918094:[WEP]>>Verify test device priority/order rules (node -script - workflow)
  	 */
  	@Test(priority = 23, groups = {"REGRESSION_WORKFLOWS" }, description="Verify test device priority/order rules (node -script - workflow)")
  	public void Verifytestdevicepriorityorderrulesnodescriptworkflow() throws Exception {
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();

		// Navigate to Workflows page
		dashboardPage.companyView(CommonVariables.LABS);
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
		waitForPageLoaded();
		if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
        LOGGER.info("Clear button clicked");
	    } else {
	        LOGGER.info("Clear button not present — skipping");
	    }
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
		sleeper(3000);
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("draftDropdown");
		waitForPageLoaded();
		sleeper(3000);
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("statusDropdown");
		wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow",WorkflowVariables.ScriptWorkflow);
		sleeper(3000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("WorkflowSearchItem");
	    waitForPageLoaded();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("SelectScriptNode");
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("NodedeleteButton");
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("confirmdeleteNodeButton");
	    wexWorkflowsPage.clickonblankpage();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
	    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch",WorkflowVariables.MSEdge);
	    sleeper(3000);
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DragFastboot");
	    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptOutputButton");
	    wexWorkflowsPage.mouseHoverOfWorkflowsPage("tooltipScriptoutput");
	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("tooltipmessage");
	    boolean ScriptOutputSucessMsgVisible = wexWorkflowsPage.verifyElementsOfWorkflowsPage("ScriptOutputDetails");
  	    Assert.assertTrue(ScriptOutputSucessMsgVisible,
  	            "ScriptOutputSucessMsgVisible message should be visible after hovering on Script Output in error state");
  	    wexWorkflowsPage.verifyElementsOfWorkflowsPage("ChangeDevice");
  	}

    /**
     * This test case verifies the Workflow Details Overview Tab
     * 1. Login and navigate to Workflows page
     * 2. Filter on TriggerType = alert
     * 3. Click on first workflow name to open details
     * 4. Verify overview tab is displayed and active
     * 5. Verify workflow information section
     * 6. Verify workflow name, type, description, status fields
     * 7. Verify created by, date created, last modified fields
     * 8. Verify alert configuration section
     */
    @Test(priority = 1, groups = {"REGRESSION_ALERTWORKFLOWS"}, description = "Verify Workflow Details Overview Tab for Alerts")
    public void verifyWorkflowDetailsOverviewTabExtended() throws Exception {
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        leftSideMenuVerification();

        // Navigate to Workflows page
        dashboardPage.companyView(CommonVariables.LABS);
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
        waitForPageLoaded();
        
        sleeper(3000);
	    if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
	        LOGGER.info("Clear button clicked");
	    } else {
	        LOGGER.info("Clear button not present — skipping");
	    }

        // Filter on TriggerType = alert
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("SearchWorkflow");
        wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow", WorkflowVariables.ExecutionValidationAlert);
        sleeper(3000);
        
        // Verify table has rows before clicking
        Assert.assertTrue(wexWorkflowsPage.getElementsOfWorkflowsPage("workflowTableRows").size() > 0,
                "No workflows found in the table");

        // Click on first workflow name
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowNameRowValue");

        // Verify Overview tab is displayed
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailsOverviewTab"),
                "Workflow details overview tab is not displayed");

        // Verify workflow information section
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailInfoHeader"),
                "Workflow information header is not displayed");

        // Verify profile section
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailProfile"),
                "Workflow profile section is not displayed");

       // Verify Alert Configuration Section
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailsAlertName"),
                "Workflow Alert Name is not displayed");

        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailsAlertConfig"),
                "Workflow Alert Configuration is not displayed");
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailsAlertConfigVal"),
                "Alert Config value is not displayed");
        
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailsAlertType"),
                "Workflow Alert Type is not displayed");
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailsAlertTypeVal"),
                "Alert type value is not displayed");
        
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowDetailsAlertCreatedBy"),
                "Workflow Alert Created By is not displayed");
        
        LOGGER.info("Verify Workflow Details Overview Tab Extended test completed successfully");
    }


	/**
     * Verify the fleet level alert triggered workflow details in Activity tab
     */
     
    @Test(priority = 2, groups = {"REGRESSION_ALERTWORKFLOWS"}, description = "Verify Activity tab for Fleet level Alert associated with Workflow")
    public void VerifyFleetLevelAlertTriggeredWorkflowinActivitytab() throws Exception {
    		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();

	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	    leftSideMenuVerification();
	    waitForPageLoaded();

	    // Navigate to Workflows page
	    dashboardPage.companyView(CommonVariables.LABS);

	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
	    waitForPageLoaded();
	    if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
	        LOGGER.info("Clear button clicked");
	    } else {
	        LOGGER.info("Clear button not present — skipping");
	    }
	   
	    wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow", WorkflowVariables.ExecutionValidationAlert);
        sleeper(3000);
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("WorkflowSearchItem");

       // wexWorkflowsPage.verifyElementsOfWorkflowsPage("Completed");
     // Click on Activity tab
     	wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowDetailsActivityTab");

     	// Verify table columns 

     	List<String> expectedColumns = Arrays.asList(
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "execution_id"))
		                .orElse("Execution ID"),
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "execution.status.column"))
		                .orElse("Execution Status"),
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "execution.started.column"))
		                .orElse("Execution Started"),
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "devices.gathered.column"))
		                .orElse("Devices Gathered"));
		resetTableConfiguration();
		
		// Verify activity table
        if (wexWorkflowsPage.getElementsOfWorkflowsPage("workflowActivityTableRows").size() > 0) {
            LOGGER.info("Workflow activity table has " + wexWorkflowsPage.getElementsOfWorkflowsPage("workflowActivityTableRows").size() + " rows");
            Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowActivityNameValue"),
                    "Workflow activity name value is not displayed");
        } else {
            LOGGER.info("No workflow activities found for this workflow");
        }
        
        // Verify bread crumb to navigate back
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("workflowActivityListBreadCrumb"),
                "Workflow activity breadcrumb is not displayed");
        
        LOGGER.info("Verify Fleet AlertWorkflow Activity Tab test completed successfully");
    } 
    
	/**
     * Verify the fleet level alert triggered workflow details in Activity tab
     */
     
    @Test(priority = 3, groups = {"REGRESSION_ALERTWORKFLOWS"}, description = "Verify Activity tab for Fleet level Alert associated with Workflow")
    public void VerifyAlertSwitchTrigger() throws Exception {
    		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();

	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	    leftSideMenuVerification();
	    waitForPageLoaded();

	    // Navigate to Workflows page
	    dashboardPage.companyView(CommonVariables.LABS);

	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
	    waitForPageLoaded();   
    
	    //select a draft workflow
	    wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow", WorkflowVariables.DraftWorkflowname);
        sleeper(3000);
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("WorkflowSearchItem");
        waitForPageLoaded();
        
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("defaultNode");
        
        //verify switch alert options
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("switchAlert"),
                "Switch Alert trigger is not displayed");
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("changeTriggerType"),
                "Change Trigger Type button is not displayed");
        
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("switchAlert");
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("alertsSelectionBox"),
                "Alert selection search filter is not displayed");
       
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("changeTriggerType");
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("changeTriggerConfirmationModal"),
                "confirmation modal is not displayed");
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("changeTriggerButton");
        
        //now switch back to alert as trigger for next run
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("changeTriggerType");
        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("changeTriggerConfirmationModal"),
                "confirmation modal is not displayed");
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("changeTriggerButton");
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("firstAlertinTable");
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("seeDetailsAlert");
        
        //navigate back to workflow list page
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowBreadcrumb");
        
       }
    
	/**
	 * This test case verifies the Workflow Details Activity Tab 
	 * 1. Login and navigate to Workflows page 
	 * 2. Click on first workflow name to open details
	 * 3.Click on Activity tab 4. Verify activity table columns 
	 * 5. Verify activity search field 
	 * 6. Verify activity status dropdown 
	 * 7. Verify activity record are displayed
	 */
	@Test(priority = 4, groups = { "REGRESSION_ALERTWORKFLOWS" }, description = "Verify Fleet Activity Execution step details")
	public void verifyFleetAlertWorkflowStepsOutputsInActivityTab() throws Exception {
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();

		// Navigate to Workflows page
		dashboardPage.companyView(CommonVariables.LABS);
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
		waitForPageLoaded();
		if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
        LOGGER.info("Clear button clicked");
	    } else {
	        LOGGER.info("Clear button not present — skipping");
	    }
		
		//search for a fleet level alert workflow
		wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow", WorkflowVariables.ExecutionValidationAlert);
        sleeper(3000);
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("WorkflowSearchItem");
        waitForPageLoaded();
	
		// Click on Activity tab
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowDetailsActivityTab");
		waitForPageLoaded();
		// Verify table columns       
		List<String> expectedColumns = Arrays.asList(
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "execution_id")).orElse("Execution ID"),
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "execution.status.column")).orElse("Execution Status"),
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "execution.started.column")).orElse("Execution Started"),
		        Optional.ofNullable(getTextLanguage(LanguageCode, "daas_ui", "devices.gathered.column")).orElse("Devices Gathered"));
		resetTableConfiguration();
		wexWorkflowsPage.verifyElementsOfWorkflowsPage("ExecutionId");
		wexWorkflowsPage.verifyElementsOfWorkflowsPage("ExecutionStatus");
		wexWorkflowsPage.verifyElementsOfWorkflowsPage("ExecutionStarted");
		wexWorkflowsPage.verifyElementsOfWorkflowsPage("Devicesgathered");
        Assert.assertTrue(wexWorkflowsPage.verifyColumnsinActivityPage(expectedColumns,"Execution"), "Table Columns are not as expected");	
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Executionidfirstrow");
		
		String ExecutionStatus = wexWorkflowsPage.getTextOfWorkflowsPage("DeviceExcutionStatus");
		if (ExecutionStatus.equalsIgnoreCase("In Progress")) {
			LOGGER.info("Workflow execution is still in progress, verifying minimal step detailsn");
			wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Selectdevice");
			wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("DeviceExecutionTimeLine");
			wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("DeviceName");
			return;
		}else {
			LOGGER.info("Workflow execution is completed, verifying all step details");
			wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Selectdevice");
			wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("DeviceExecutionTimeLine");
			wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("DeviceName");
			wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("TimeElapsed");
			wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("started");
			wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("completed");
			wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("StepId");
			wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Frequency");
			wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("Trigger");
			wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("WindowsStepID");
			wexWorkflowsPage.verifyElementIsDisplayedOfWorkflowsPage("StandardOutput");
	}
	}
	
	@Test(priority = 5, groups = {"REGRESSION_ALERTWORKFLOWS"}, description="verify User Can Create Alert based workflow")
	public final void verifyUserCanCreateAlertBasedworkflow() throws Exception {

	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	    leftSideMenuVerification();
	    waitForPageLoaded();
	    // Navigate to Workflows page
	    dashboardPage.companyView(CommonVariables.LABS);
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
	    waitForPageLoaded();
	    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
	    waitForPageLoaded();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("CreateScratch");
	    wexWorkflowsPage.switchToNewTab();
	    waitForPageLoaded();
	    sleeper(3000);
	    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  		wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
  		sleeper(1000);
  		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
  		String workflowName = WorkflowVariables.FleetAlertWORKFLOWNAME + generateRandomNumber();
		wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow",workflowName);
		
		//select event trigger as alert
		wexWorkflowsPage.actionClickOfWorkflowsPage("EventDropdown");
		waitForPageLoaded();
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandEvent","DestinationSelectandAudience");
	    	    
	    wexWorkflowsPage.clickonblankpage();
	    // Device Action Section
	    wexWorkflowsPage.actionClickOfWorkflowsPage("DeviceAction");
	    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceDriverupdate","DestinationSelectandAudience");
	    wexWorkflowsPage.clickonblankpage();
	    waitForPageLoaded();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("publishButton");
	    // Verify Test Success Status
		Assert.assertTrue(wexWorkflowsPage.getTextOfWorkflowsPage("PublishWorflow").contains("Workflow published"),
				"workflow published is failed");
		waitForPageLoaded();
		
		//navigate back to workflow list page
        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowBreadcrumb");
        waitForPageLoaded();
        
		
		//cancel this workflow after creation to avoid any impact on other test cases
		if (wexWorkflowsPage.verifyElementsOfWorkflowsPage("clearbutton")) {
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("clearbutton");
	        LOGGER.info("Clear button clicked");
		    } else {
		        LOGGER.info("Clear button not present — skipping");
		    }
			
		//search for a fleet level alert workflow
		wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow", workflowName);
		sleeper(3000);
		wexWorkflowsPage.mouseHoverOfWorkflowsPage("WorkflowSearchItem");
		sleeper(1000);
		wexWorkflowsPage.clickOnElementsOfWorkflowsPage("metaball");
	    sleeper(2000);
	    wexWorkflowsPage.actionClickOfWorkflowsPage("CancelButton");
	    waitForPageLoaded();
	    wexWorkflowsPage.actionClickOfWorkflowsPage("CancelWorkflow");
	    Assert.assertTrue( wexWorkflowsPage.getTextOfWorkflowsPage("CanceledWorkflow").contains("Workflow canceled"),"Workflow canceled message not displayed");	
	}
	
	@Test(priority = 6, groups = {"REGRESSION_ALERTWORKFLOWS"}, description = "Verify user cannot click publish without selecting trigger")
	public void VerifyPublishButtonwithTrigger() throws Exception {
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPWorkflowsPage wexWorkflowsPage = new WXPWorkflowsPage(PreDefinedActions.getDriver()).getInstance();

		    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		    leftSideMenuVerification();
		    waitForPageLoaded();
		    // Navigate to Workflows page
		    dashboardPage.companyView(CommonVariables.LABS);
		    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowTryNow");
		    waitForPageLoaded();
		    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("Addworkflow");
		    waitForPageLoaded();
		    wexWorkflowsPage.actionClickOfWorkflowsPage("CreateScratch");
		    wexWorkflowsPage.switchToNewTab();
		    waitForPageLoaded();
		    sleeper(3000);
		    wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
	   		wexWorkflowsPage.clearTextOfWorkflowsPage("untitledworkflow");
	   		wexWorkflowsPage.clickByJavaScriptOnWorkflowsPage("WorkfloweditIcon");
			String workflowName = "AutoGenerated"+WorkflowVariables.FleetAlertWORKFLOWNAME + generateRandomNumber();
			wexWorkflowsPage.enterTextOnWorkflowsPage("editworkflow",workflowName);
			
			//select event trigger as alert
			wexWorkflowsPage.actionClickOfWorkflowsPage("EventDropdown");
			waitForPageLoaded();
		    wexWorkflowsPage.dragAndDropOfWorkflowpage("SourceSelectandEvent","DestinationSelectandAudience");
		    
		    //select a script
		    wexWorkflowsPage.clickonblankpage();
		    wexWorkflowsPage.actionClickOfWorkflowsPage("ScriptDropdown");
		    wexWorkflowsPage.enterTextOnWorkflowsPage("ScriptSearch","Enable Fast Boot");
		    sleeper(3000);
		    wexWorkflowsPage.dragAndDropOfWorkflowpage("EnablefastBoot","DragFastboot");
		    wexWorkflowsPage.clickonblankpage();
		    waitForPageLoaded();
		    
		    
		    // switch triggers
		    
		    wexWorkflowsPage.clickOnElementsOfWorkflowsPage("defaultNode");
	        
	        //change triggers to schedule
	        	        
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("changeTriggerType");
	        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("changeTriggerConfirmationModal"),
	                "confirmation modal is not displayed");
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("changeTriggerButton");
	        sleeper(2000);
	        
	        // change trigger back to alert event
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("changeTriggerType");
	        Assert.assertTrue(wexWorkflowsPage.verifyElementsOfWorkflowsPage("changeTriggerConfirmationModal"),
	                "confirmation modal is not displayed");
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("changeTriggerButton");
	        sleeper(5000);
		
	        //Verify publishButton is disabled as no alert is selected at this point
	        WebElement publishBtnStatus = wexWorkflowsPage.getElementOfWorkflowsPage("publishButtonDisabld");

	        Assert.assertFalse(publishBtnStatus.isEnabled(), "Publish button is enabled - NOT EXPECTED");

	        //navigate back to workflow list page
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("workflowBreadcrumb");


	        //search for a fleet level alert workflow
	        wexWorkflowsPage.enterTextOnWorkflowsPage("SearchWorkflow", workflowName);
	        sleeper(3000);
	        wexWorkflowsPage.mouseHoverOfWorkflowsPage("WorkflowSearchItem");
	        sleeper(2000);
	        wexWorkflowsPage.clickOnElementsOfWorkflowsPage("metaball");
	        sleeper(2000);
	        wexWorkflowsPage.actionClickOfWorkflowsPage("CancelButton");
	        waitForPageLoaded();
	        wexWorkflowsPage.actionClickOfWorkflowsPage("CancelWorkflow");
	        Assert.assertTrue( wexWorkflowsPage.getTextOfWorkflowsPage("CanceledWorkflow").contains("Workflow canceled"),"Workflow canceled message not displayed");	
		
	}
}

   	


