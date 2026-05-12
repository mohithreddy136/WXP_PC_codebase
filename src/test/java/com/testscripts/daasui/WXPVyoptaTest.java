package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.VypotaIntegrationVariables;
import com.daasui.pages.WEXDashboardPage;
import com.daasui.pages.WEXCustomerHomePage;
import com.daasui.pages.WXPCollaborationPage;
import com.daasui.pages.WXPVideoEndpointsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.daasui.constants.ConstantPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WXPVyoptaTest extends CommonTest {
    private static Logger LOGGER = LogManager.getLogger(WXPVyoptaTest.class); 
    
    /**
     * This is to verify the collaboration spaces navigation and menu button
     * Verify the 12 widgets in collaboration spaces navigation
     */
    
    @Test(priority = 1,groups={"REGRESSION_VYOPTA"}, description = "12 widgets in collaboration spaces navigation")
    public final void verifyWorkforceExperienceNavigationToCollaborationspaces() throws Exception{
        login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
        openLeftSidePanel();
        WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        waitForPageLoaded();
		LOGGER.info("Navigated to Analytics tab ");	
        wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationspace");
        waitForPageLoaded();
        Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationspacepagetitle"),
            		"collaboration space page title is not displayed");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DuplicateDashboard");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("EditDashboard");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("SpacesOverviewwidget").equals("Spaces Overview"), "Spaces Overview title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("RoomUtilizationwwidget").equals("Room Utilization by Building"), "Room Utilization by Building title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("OccupiedRoomwidget").equals("Occupied Room Minutes by Location"), "Occupied Room Minutes by Location title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("RoomActivityTypewidget").equals("Room Activity Type - Total Minutes"),"Room Activity Type widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("SpacesbyTypewidget").equals("Spaces by Type"),"Spaces by Type widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("SpacesbyRoomwidget").equals("Spaces by Room System"),"Spaces by Room System widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("BookedMeetingswidget").equals("Booked Meetings by Location"),"Booked Meetings by Location widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("MissedMeetingswidget").equals("Missed Meetings by Location"),"Missed Meetings by Location widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("HighlyUtilizedwidget").equals("Highly Utilized Spaces"),"Highly Utilized Spaces widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("Underutilizedspacewidget") .equals("Underutilized Spaces"),"Underutilized Spaces widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("MissedMettingsbydepartmentwidget").equals("Missed Meetings by Department"),"Missed Meetings by Department widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("Spacesover100%widget").equals("Spaces with over 100% Occupancy"), "Spaces with over 100% Occupancy widget title is not present/matching.");
            
        LOGGER.info("Verified Collaboration Dashboard Widget titles are present");
            
         }
    
    /**
     * Create copy of collaboration spaces navigation
     * Verify the 12 widgets in collaboration spaces navigation
     * Delete the copy of collaboration spaces navigation
     */
    
    @Test(priority = 2,groups={"REGRESSION_VYOPTA"}, description = "Create copy of collaboration spaces navigation")
    public final void Createcopyofcollaborationspacesnavigation() throws Exception{
        login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
        openLeftSidePanel();
        WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationspace");
        waitForPageLoaded();
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DuplicateDashboard");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("CopyCreated").contains("Copy of dashboard created successfully"), "Copy of dashboard created is failed");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("CopyCollaboartionSpacestitle");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("SpacesOverviewwidget").equals("Spaces Overview"), "Spaces Overview title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("RoomUtilizationwwidget").equals("Room Utilization by Building"), "Room Utilization by Building title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("OccupiedRoomwidget").equals("Occupied Room Minutes by Location"), "Occupied Room Minutes by Location title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("RoomActivityTypewidget").equals("Room Activity Type - Total Minutes"),"Room Activity Type widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("SpacesbyTypewidget").equals("Spaces by Type"),"Spaces by Type widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("SpacesbyRoomwidget").equals("Spaces by Room System"),"Spaces by Room System widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("BookedMeetingswidget").equals("Booked Meetings by Location"),"Booked Meetings by Location widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("MissedMeetingswidget").equals("Missed Meetings by Location"),"Missed Meetings by Location widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("HighlyUtilizedwidget").equals("Highly Utilized Spaces"),"Highly Utilized Spaces widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("Underutilizedspacewidget") .equals("Underutilized Spaces"),"Underutilized Spaces widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("MissedMettingsbydepartmentwidget").equals("Missed Meetings by Department"),"Missed Meetings by Department widget title is not present/matching.");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("Spacesover100%widget").equals("Spaces with over 100% Occupancy"), "Spaces with over 100% Occupancy widget title is not present/matching.");
            
        LOGGER.info("Verified Collaboration Dashboard Widget titles are present");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");
        
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully").contains("Dashboard deleted successfully"), "Dashboard deleted is failed");
        
    }
    
    /**
     * Verify the Rename the Custom Dashboard
     * Verify the Remane the Custom Dashboard in Analytics screen
     */
    
    @Test(priority = 3,groups={"REGRESSION_VYOPTA"}, description = "Verify the Rename the Custom Dashboard")
    public final void VerifytheRenametheCustomDashboard() throws Exception{
        login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
        openLeftSidePanel();
        WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationspace");
        waitForPageLoaded();
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DuplicateDashboard");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("CopyCreated").contains("Copy of dashboard created successfully"), "Copy of dashboard created is failed");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("RenameEditbutton");
        wexCollaborationPage.clearTextOfCollaborationPage("Renamefield");
        wexCollaborationPage.enterTextOnCollaborationPage("Editfield",VypotaIntegrationVariables.CustomDashboard);
        wexCollaborationPage.actionClickOfCollaborationPage("EditSavebutton");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("NameUpdate").contains("Dashboard name updated successfully"), "Dashboard name updated is failed");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("UpdatedTitle");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("AnalyticsTab");
        waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("CustomCollaborationSpaces");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully").contains("Dashboard deleted successfully"), "Dashboard deleted is failed");
        
       }
    
    /**
     * Check User Can  Edit the Custom dashboard.
     */
    
    @Test(priority = 4,groups={"REGRESSION_VYOPTA"}, description = "Check user can the Edit the Custom dashboard.")
    public final void VerifytheEdittheCustomdashboard() throws Exception{
        login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
        openLeftSidePanel();
        WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationspace");
        waitForPageLoaded();
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("EditDashboard");
        waitForPageLoaded();
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("SelectWidgets");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("SelectCollaborationspace");
        wexCollaborationPage.actionClickOfCollaborationPage("UncheckSpacesOverviewwidget");
        wexCollaborationPage.actionClickOfCollaborationPage("UncheckRoomUtilizationwwidget");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Savebutton");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("Dashboardupdate").contains("Dashboard updated successfully"), "Dashboard updated is failed");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("UpdatedTitle");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("AnalyticsTab");
        waitForPageLoaded();
        wexCollaborationPage.clickOnElementsOfCollaborationPage("CopyDashboardSpace");
        waitForPageLoaded();
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully").contains("Dashboard deleted successfully"), "Dashboard deleted is failed");
        
     }
    /**
     * Verify user can Remove widget from custom dashboard
     */
    
    @Test(priority = 5,groups={"REGRESSION_VYOPTA"}, description = "Remove widget from custom dashboard")
    public final void VerifyUserCanRemovewidgetfromcustomdashboard() throws Exception{
        login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
        openLeftSidePanel();
        WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationspace");
        waitForPageLoaded();
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DuplicateDashboard");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("CopyCreated").contains("Copy of dashboard created successfully"), "Copy of dashboard created is failed");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("SpacesOverviewwidget");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Widgetsmenu");
        sleeper(2000);
        wexCollaborationPage.actionClickOfCollaborationPage("Removebutton");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("Widgetremovedsuccessfully").contains("Widget removed successfully"), "Widget removed is failed");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully").contains("Dashboard deleted successfully"), "Dashboard deleted is failed");
        
    }
	/**
     * Export widget as a PNG 
     */
    
    @Test(priority = 6,groups={"REGRESSION_VYOPTA"}, description = "Export widget as a PNG")
    public final void VerifyExportwidgetasaPNG () throws Exception{
        login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
        openLeftSidePanel();
        WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationspace");
        waitForPageLoaded();
		wexCollaborationPage.scrollTillViewCollaborationPage("RoomActivityTypewidget");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("RoomActivityTypewidget");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("RoomActivityTypeMenu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("ExportPNGButton");
        sleeper(2000);
        String downloadPath = ConstantPath.DOWNLOAD_PATH;
        wexCollaborationPage.openAndClosePNG(downloadPath);
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("PNGdownload").contains("PNG exported successfully"), "PNG exported is failed");
        
        }
    
    /**
     * Delete the Custom Dasboard
     */
    
    @Test(priority = 7,groups={"REGRESSION_VYOPTA"}, description = "Delete the Custom Dasboard")
    public final void DeletetheCustomDasboard() throws Exception{
        login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
        openLeftSidePanel();
        WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationspace");
        waitForPageLoaded();
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DuplicateDashboard");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("CopyCreated").contains("Copy of dashboard created successfully"), "Copy of dashboard created is failed");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("CopyCollaboartionSpacestitle");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("RenameEditbutton");
        wexCollaborationPage.clearTextOfCollaborationPage("Renamefield");
        wexCollaborationPage.enterTextOnCollaborationPage("Editfield",VypotaIntegrationVariables.CustomDashboard);
        wexCollaborationPage.actionClickOfCollaborationPage("EditSavebutton");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("AnalyticsTab");
        waitForPageLoaded();
        wexCollaborationPage.clickOnElementsOfCollaborationPage("CustomCollaborationSpaces");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully").contains("Dashboard deleted successfully"), "Dashboard deleted is failed");
        
        }
    
    /**
     * Export data from duplicated (custom) dashboard
     */
    
    @Test(priority = 8,groups={"REGRESSION_VYOPTA"}, description = "Export data from duplicated (custom) dashboard")
    public final void Exportdatafromduplicatedcustomdashboard() throws Exception{
        login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
        openLeftSidePanel();
        WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
        wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationspace");
        waitForPageLoaded();
        wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DuplicateDashboard");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("CopyCreated").contains("Copy of dashboard created successfully"), "Copy of dashboard created is failed");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("CopyCollaboartionSpacestitle");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("RenameEditbutton");
        wexCollaborationPage.clearTextOfCollaborationPage("Renamefield");
        wexCollaborationPage.enterTextOnCollaborationPage("Editfield",VypotaIntegrationVariables.CustomDashboard + generateRandomNumber());
        wexCollaborationPage.actionClickOfCollaborationPage("EditSavebutton");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("NameUpdate").contains("Dashboard name updated successfully"), "Dashboard name updated is failed");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("ExportData");
        wexCollaborationPage.actionClickOfCollaborationPage("XLSXFile");
        sleeper(2000);
        wexCollaborationPage.clickOnElementsOfCollaborationPage("NotificationCenter");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("NotificationTab");
        wexCollaborationPage.waitElementsOfCollaborationPage("Report");
		wexCollaborationPage.waitUntilElementIsInvisibleOfCollaborationPage("Report");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DownloadFile");
        String CustomName = "CustomDashboard";
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		String formattedDate = currentDate.format(formatter);
		LOGGER.info("DATE " + formattedDate);
		String updated = formattedDate.replaceFirst("-(?=[^\\-]+$)", "_");
		System.out.println(updated);
		String updatedCustomName = CustomName + "_" + updated + ".xslx";
		LOGGER.info("Updated Custom Name: " + updatedCustomName);
		String filePath  = ConstantPath.DOWNLOAD_PATH + "updatedCustomName";
		File file = new File(filePath);
		LOGGER.info("Checking file path: " + filePath);
		LOGGER.info("File exists: " + file.exists());
		LOGGER.info("File exists: " + wexCollaborationPage.isFileExists(filePath));
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
        wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");
        Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully").contains("Dashboard deleted successfully"), "Dashboard deleted is failed");
       
    }
    
    /**
    * Drilldown functionality should redirect to Respective Vyopta Page
    */
   
   @Test(priority = 9,groups={"REGRESSION_VYOPTA"}, description = "Drilldown functionality should redirect to Respective Vyopta Page")
   public final void DrilldownfunctionalityshouldredirecttoRespectiveVyoptaPage() throws Exception{
       login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
       openLeftSidePanel();
       WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
       WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
       dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
       wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationspace");
       waitForPageLoaded();
       wexCollaborationPage.verifyElementsOfCollaborationPage("SpacesOverviewwidget");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("AverageRoom");
       waitForPageLoaded();
       sleeper(2000);
       Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Workspaces"),"Navigated to Vyopta Page");
       
       LOGGER.info("Successfully Redirect to the  Respective Vyopta Page ");
    }
    
   /**
    * Disable the Toggle button in the Custom Widget
    */
   
   @Test(priority = 10,groups={"REGRESSION_VYOPTA"}, description = "Disable the Toggle button in the Custom Widget")
   public final void DisabletheTogglebuttonintheCustomWidget() throws Exception{
       login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
       openLeftSidePanel();
       WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
       WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
       dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
       wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationspace");
       waitForPageLoaded();
	   wexCollaborationPage.scrollTillViewCollaborationPage("RoomActivityTypewidget");
	   wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("RoomActivityTypewidget");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("RoomActivityTypeMenu");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("VideoButton");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("AudioButton");
       wexCollaborationPage.verifyElementsOfCollaborationPage("NoDataDisplay");
       Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("NoDataDisplay"),"No Data Display is not present");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("VideoButton");
       Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("GraphBar"),"Video Graph is present");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("AudioButton");
       Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("GraphBar"),"Audio garph is present");
       LOGGER.info("Successfully Disabled the Toggle button and displayed as No Data");
   }
   
   /**
    * Create custom dashboards using these widgets.
    */
   
   @Test(priority = 11,groups={"REGRESSION_VYOPTA"}, description = "Create custom dashboards using these widgets.")
   public final void Createcustomdashboardsusingthesewidgets() throws Exception{
       login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
       openLeftSidePanel();
       WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
       WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
       dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
       wexCollaborationPage.clickOnElementsOfCollaborationPage("CreateNew");
       waitForPageLoaded();
       wexCollaborationPage.clickOnElementsOfCollaborationPage("CollaborationSpace");
       wexCollaborationPage.scrollTillViewCollaborationPage("RoomUtilizationwwidget");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("RoomUtilizationwwidget");
       wexCollaborationPage.scrollTillViewCollaborationPage("OccupiedRoomwidget");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("OccupiedRoomwidget");
       wexCollaborationPage.scrollTillViewCollaborationPage("SpacesOverviewwidget");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("SpacesOverviewwidget");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("Create");
       Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("NewDashboardCreated").contains("New dashboard created successfully"), "New Dashboard created is failed");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("RenameEditbutton");
       wexCollaborationPage.clearTextOfCollaborationPage("ClearNewDashboard");
       wexCollaborationPage.enterTextOnCollaborationPage("Editfield",VypotaIntegrationVariables.NewDashboard);
       wexCollaborationPage.actionClickOfCollaborationPage("EditSavebutton");
       Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("NameUpdate").contains("Dashboard name updated successfully"), "Dashboard name updated is failed");
       sleeper(2000);
       wexCollaborationPage.clickOnElementsOfCollaborationPage("AnalyticsTab");
       waitForPageLoaded();
       wexCollaborationPage.clickOnElementsOfCollaborationPage("NewCollaborationSpace");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
       wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");
       Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully").contains("Dashboard deleted successfully"), "Dashboard deleted is failed");
       
   }
   /**
    * Collaboration Spaces should be Available on the Analytics page.
    */
   
   @Test(priority = 12,groups={"REGRESSION_VYOPTA"}, description = "Collaboration Spaces should be Available on the Analytics page.")
   public final void CollaborationSpacesshouldbeAvailableontheAnalyticspage() throws Exception{
       login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
       openLeftSidePanel();
       WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
       WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
       dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);  waitForPageLoaded();
		LOGGER.info("Navigated to Analytics tab ");	
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("collaborationspace");
        wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationspace");
        waitForPageLoaded();
        Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationspacepagetitle"),"collaboration space page title is not displayed");
       
        LOGGER.info("Collaboration page loaded successfully");
       
   }
   
   /**
    * Clicking on View Detail Should Redirect to Vyopta Dashboard
    */
   
   @Test(priority = 13,groups={"REGRESSION_VYOPTA"}, description = "Clicking on View Detail Should Redirect to Vyopta Dashboard")
   public final void ClickingonViewDetailShouldRedirecttovyoptaDashboard() throws Exception{
       login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
       openLeftSidePanel();
       WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
       WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
       dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);  
	   LOGGER.info("Navigated to Analytics tab ");	
	   waitForPageLoaded();
	   wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("collaborationspace");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationspace");
       waitForPageLoaded();
       wexCollaborationPage.clickOnElementsOfCollaborationPage("ViewDetails");
       wexCollaborationPage.switchToNewTab();
       if(wexCollaborationPage.verifyElementsOfCollaborationPage("Loginimage")) {
    	   wexCollaborationPage.enterTextOnCollaborationPage("username",VypotaIntegrationVariables.username);
    	   wexCollaborationPage.clickOnElementsOfCollaborationPage("Nextbutton");
    	   wexCollaborationPage.enterTextOnCollaborationPage("Passwordbutton",VypotaIntegrationVariables.password);
    	   wexCollaborationPage.clickOnElementsOfCollaborationPage("Loginbutton");
    	   waitForPageLoaded();
    	   Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Workspaces"),"Navigated to Vyopta Page");
       } else {
    	   Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Workspaces"),"Navigated to Vyopta Page"); 
       }
	   
   }
   
   /**
    * Verify Collaboration Experience dashboard in Anayltics 
    */
   
   @Test(priority = 14,groups={"REGRESSION_VYOPTA"}, description = "Verify Collaboration Experience dashboard in Anayltics.")
   public final void VerifyCollaborationExperiencedashboardinAnayltics() throws Exception{
       login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
       openLeftSidePanel();
       WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
       WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
       dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);  
	   LOGGER.info("Navigated to Analytics tab ");	
	   waitForPageLoaded();
	   wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperience");
	   wexCollaborationPage.mouseHoverOfCollaborationPage("collaborationExperience");
	   wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover");
       Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover").equals("Created by HP"), "Collaboration Experience Created by HP is not present/matching.");
       wexCollaborationPage.actionClickOfCollaborationPage("collaborationExperience");
       waitForPageLoaded();
       wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperiencePageTitle");
       LOGGER.info("Verified Collaboration Dashboard Expereience naviagtion");
   }
   
   /**
    * Rename the Custom Dasboard in collaboration experience
    */
   
   @Test(priority = 15,groups={"REGRESSION_VYOPTA"}, description = "Rename the Custom Dasboard in collaborationexperience.")
   public final void RenametheCustomDasboardincollaborationexperience() throws Exception{
       login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
       openLeftSidePanel();
       WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver()).getInstance();
       WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
       dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);  
	   LOGGER.info("Navigated to Analytics tab ");	
	   waitForPageLoaded();
	   wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationExperience");
	   waitForPageLoaded();
       wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("DuplicateDashboard");
       Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("CopyCreated").contains("Copy of dashboard created successfully"), "Copy of dashboard created is failed");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("RenameEditbutton");
       wexCollaborationPage.clearTextOfCollaborationPage("RenamefieldExperience");
       wexCollaborationPage.enterTextOnCollaborationPage("Editfield",VypotaIntegrationVariables.CustomDashboardExperience);
       wexCollaborationPage.actionClickOfCollaborationPage("EditSavebutton");
       Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("NameUpdate").contains("Dashboard name updated successfully"), "Dashboard name updated is failed");
       wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("UpdatedTitle");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("AnalyticsTab");
       waitForPageLoaded();
       wexCollaborationPage.clickOnElementsOfCollaborationPage("Customcollaborationexperience");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
       wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
       wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");
       Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully").contains("Dashboard deleted successfully"), "Dashboard deleted is failed");
       
   }
   
   /**
	 * Duplicate the dashboard of collaboartion Experience.
	 */

	@Test(priority = 16, groups = {
			"REGRESSION_VYOPTA" }, description = "Duplicate the dashboard of collaboartion Experience")
	public final void DuplicatethedashboardofcollaboartionExperience() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		LOGGER.info("Navigated to Analytics tab ");
		waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DuplicateDashboard");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("CopyCreated")
				.contains("Copy of dashboard created successfully"), "Copy of dashboard created is failed");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("CopyCollaborationExperiencetitle");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("MeetingSummary").equals("Meetings Summary"),
				"Meetings Summary title is not present/matching.");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("MeetingPlatform")
						.equals("Meetings by Platform over Time"),
				"Meetings by Platform over Time title is not present/matching.");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("MeetingTeams").equals("Meeting Quality - MSFT Teams"),
				"Meeting Quality - MSFT Teams title is not present/matching.");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("MeetingZoom").equals("Meeting Quality - Zoom"),
				"Meeting Quality - Zoom title is not present/matching.");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("MeetingCisco").equals("Meeting Quality - Cisco Webex"),
				"Meeting Quality - Cisco Webex title is not present/matching.");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("RemoteParticipant")
						.equals("Remote Participant Quality of Experience"),
				"Remote Participant Quality title is not present/matching.");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("CorporateNetwork")
						.equals("In-Office/Corporate Network Quality of Experience"),
				"In-Office/Corporate Network Quality of Experience title is not present/matching.");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("BadQuality")
						.equals("Bad Quality of Experience by Network"),
				"Bad Quality of Experience by Network title is not present/matching.");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("VideoEndpointQuality")
						.equals("Video Endpoint Quality of Experience"),
				"Video Endpoint Quality of Experience title is not present/matching.");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("EndUserDeviceQuality")
						.equals("End User Device Quality of Experience"),
				"End User Device Quality of Experience title is not present/matching.");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("CommonEndUser").equals("Common End User Issues"),
				"Common End User Issues title is not present/matching.");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("BadQualityofExperience")
						.equals("Bad Quality of Experience by Device"),
				"Bad Quality of Experience by Device title is not present/matching.");

		LOGGER.info("Verified Collaboration Dashboard Widget titles are present");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");

		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully")
				.contains("Dashboard deleted successfully"), "Dashboard deleted is failed");

	}

	/**
	 * Create custom dashboards using these widgets for collaboration Experience.
	 */

	@Test(priority = 17, groups = {
			"REGRESSION_VYOPTA" }, description = "Create custom dashboards using these widgets for collaboration Experience.")
	public final void CreatecustomdashboardsusingthesewidgetsforcollaborationExperience() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		wexCollaborationPage.clickOnElementsOfCollaborationPage("CreateNew");
		waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Collaboration");
		wexCollaborationPage.scrollTillViewCollaborationPage("MeetingTeamscollaboartionpage");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("MeetingTeamscollaboartionpage");
		wexCollaborationPage.scrollTillViewCollaborationPage("BadQualitycollaboartionpage");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("BadQualitycollaboartionpage");
		wexCollaborationPage.scrollTillViewCollaborationPage("CorporateNetworkcollaboartionpage");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("CorporateNetworkcollaboartionpage");
		wexCollaborationPage.scrollTillViewCollaborationPage("VideoEndpointQualitycollaboartionpage");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("VideoEndpointQualitycollaboartionpage");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Create");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("NewDashboardCreated")
				.contains("New dashboard created successfully"), "New Dashboard created is failed");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("RenameEditbutton");
		wexCollaborationPage.clearTextOfCollaborationPage("ClearNewDashboard");
		wexCollaborationPage.enterTextOnCollaborationPage("Editfield",
				VypotaIntegrationVariables.NewDashboardExperience);
		wexCollaborationPage.actionClickOfCollaborationPage("EditSavebutton");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("NameUpdate")
				.contains("Dashboard name updated successfully"), "Dashboard name updated is failed");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("AnalyticsTab");
		waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("NewCollaborationExperience");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully")
				.contains("Dashboard deleted successfully"), "Dashboard deleted is failed");

	}

	/**
	 * Edit the Custom dashboard for collaboration Experience.
	 */

	@Test(priority = 18, groups = {
			"REGRESSION_VYOPTA" }, description = "Edit the Custom dashboard for collaboration Experience.")
	public final void EdittheCustomdashboardforcollaborationExperience() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("EditDashboard");
		waitForPageLoaded();
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("SelectWidgets");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Collaboration");
		wexCollaborationPage.scrollTillViewCollaborationPage("UncheckSpacesOverviewwidget");
		wexCollaborationPage.actionClickOfCollaborationPage("UncheckSpacesOverviewwidget");
		wexCollaborationPage.scrollTillViewCollaborationPage("UncheckRoomUtilizationwwidget");
		wexCollaborationPage.actionClickOfCollaborationPage("UncheckRoomUtilizationwwidget");
		wexCollaborationPage.scrollTillViewCollaborationPage("UncheckOccupiedRoomwidget");
		wexCollaborationPage.actionClickOfCollaborationPage("UncheckOccupiedRoomwidget");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Savebutton");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("Dashboardupdate")
				.contains("Dashboard updated successfully"), "Dashboard updated is failed");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("UpdatedTitle");
		sleeper(2000);
		wexCollaborationPage.clickOnElementsOfCollaborationPage("AnalyticsTab");
		waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("CopyCollaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully")
				.contains("Dashboard deleted successfully"), "Dashboard deleted is failed");

	}

	/**
	 * Delete the Custom Dasboard for Collaboration Experience
	 */

	@Test(priority = 19, groups = {
			"REGRESSION_VYOPTA" }, description = "Delete the Custom Dasboard for Collaboration Experience")
	public final void DeletetheCustomDasboardforCollaborationExperience() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DuplicateDashboard");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("CopyCreated")
				.contains("Copy of dashboard created successfully"), "Copy of dashboard created is failed");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("CopyCollaboartionSpacestitle");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("RenameEditbutton");
		wexCollaborationPage.clearTextOfCollaborationPage("RenamefieldExperience");
		wexCollaborationPage.enterTextOnCollaborationPage("Editfield",
				VypotaIntegrationVariables.CustomDashboardExperience);
		wexCollaborationPage.actionClickOfCollaborationPage("EditSavebutton");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("NameUpdate")
				.contains("Dashboard name updated successfully"), "Dashboard name updated is failed");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("UpdatedTitle");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("AnalyticsTab");
		waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Customcollaborationexperience");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully")
				.contains("Dashboard deleted successfully"), "Dashboard deleted is failed");

	}

	/**
	 * Verify Default Dashboard and Custom Dashboard export functionality
	 */

	@Test(priority = 20, groups = {
			"REGRESSION_VYOPTA" }, description = "Verify Default Dashboard and Custom Dashboard export functionality")
	public final void VerifyDefaultDashboardandCustomDashboardexportfunctionality() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DuplicateDashboard");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("CopyCreated")
				.contains("Copy of dashboard created successfully"), "Copy of dashboard created is failed");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("AnalyticsTab");
		waitForPageLoaded();
		wexCollaborationPage.mouseHoverOfCollaborationPage("CopyCollaborationExperience");
		wexCollaborationPage.getTextOfCollaborationPage("StageVypota");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("StageVypota").equals("Created by Stage Vyopta"),
				"Collaboration Experience Created by Stage Vyopta is not present/matching.");
		sleeper(2000);
		wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DuplicateDashboard");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("EditDashboard");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("AnalyticsTab");
		waitForPageLoaded();
		wexCollaborationPage.clickOnElementsOfCollaborationPage("CopyCollaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("ExportData");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("Sourceelement");
		wexCollaborationPage.dragAndDropOfCollaborationPage("Sourceelement", "Destinationelement");
		sleeper(2000);
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("MeetingSummary");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Widgetsmenu");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Removebutton");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("Widgetremovedsuccessfully")
				.contains("Widget removed successfully"), "Widget removed is failed");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("ExportData");
		wexCollaborationPage.actionClickOfCollaborationPage("XLSXFile");
		sleeper(2000);
		wexCollaborationPage.clickOnElementsOfCollaborationPage("NotificationCenter");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("NotificationTab");
		sleeper(2000);
		wexCollaborationPage.waitElementsOfCollaborationPage("Report");
		wexCollaborationPage.waitUntilElementIsInvisibleOfCollaborationPage("Report");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DownloadFile");
		String CustomName = "CustomDashboard";
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		String formattedDate = currentDate.format(formatter);
		LOGGER.info("DATE " + formattedDate);
		String updated = formattedDate.replaceFirst("-(?=[^\\-]+$)", "_");
		System.out.println(updated);
		String updatedCustomName = CustomName + "_" + updated + ".xslx";
		LOGGER.info("Updated Custom Name: " + updatedCustomName);
		String filePath = ConstantPath.DOWNLOAD_PATH + "updatedCustomName";
		File file = new File(filePath);
		LOGGER.info("Checking file path: " + filePath);
		LOGGER.info("File exists: " + file.exists());
		LOGGER.info("File exists: " + wexCollaborationPage.isFileExists(filePath));
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Menu");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteMenu");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("DeleteDashboard");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("DeleteButton");
		Assert.assertTrue(wexCollaborationPage.getTextOfCollaborationPage("DeleteSuccessfully")
				.contains("Dashboard deleted successfully"), "Dashboard deleted is failed");

	}
	
	/**
	 * Verify Meetings Summary widget in dashboard
	 */

	@Test(priority = 21, groups = { "REGRESSION_VYOPTA" }, description = "Verify Meetings Summary widget in dashboard")
	public final void VerifyMeetingsSummarywidgetindashboard() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		wexCollaborationPage.clickOnElementsOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("MeetingSummary");
		Assert.assertTrue(
				wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("MeetingSummaryContentUpper"),
				"Content is not present");
		Assert.assertTrue(
				wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("MeetingSummaryContentLower"),
				"Content is not present");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("MeetingSummarymenu");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("ExportPNGButton"),
				"ExportPNGButton is present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("CopyasPNG"), "CopyasPNG is present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Removebutton"),
				"Removebutton is present");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("ExportPNGButton");
		sleeper(2000);
		String Filename = "Meetings Summary"+".png";
		LOGGER.info(Filename);
		String filePath = ConstantPath.DOWNLOAD_PATH + "Filename";
		File file = new File(filePath);
		LOGGER.info("Checking file path: " + filePath);
		LOGGER.info("File exists: " + file.exists());
		LOGGER.info("File exists: " + wexCollaborationPage.isFileExists(filePath));
		wexCollaborationPage.waitElementsOfCollaborationPage("PNGdownload");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("PNGdownload").contains("PNG exported successfully"),
				"PNG exported is failed");

	}

	/**
	 * Verify Meetings by Platform Over Time widget creation in Collaboration
	 * Experience Dashboard
	 */

	@Test(priority = 22, groups = {
			"REGRESSION_VYOPTA" }, description = "Verify Meetings by Platform Over Time widget creation in Collaboration Experience Dashboard")
	public final void VerifyMeetingsbyPlatformOverTimewidgetcreationinCollaborationExperienceDashboard()
			throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.mouseHoverOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover").equals("Created by HP"),
				"Collaboration Experience Created by HP is not present/matching.");
		wexCollaborationPage.actionClickOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperiencePageTitle");
		LOGGER.info("Verified Collaboration Dashboard Expereience naviagtion");
		wexCollaborationPage.verifyElementsOfCollaborationPage("MeetingPlatform");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Grapline"),
				"Grapline is not present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("MSFTTeams"), "MSFTTeams is present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Zoom"), "Zoom is present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("CiscoWebex"),
				"CiscoWebex is present");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("MeetingPlatfrommenu");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("ExportPNGButton"),
				"ExportPNGButton is present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("CopyasPNG"), "CopyasPNG is present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Removebutton"),
				"Removebutton is present");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("ViewDetails");
		wexCollaborationPage.switchToNewTab();
		if (wexCollaborationPage.verifyElementsOfCollaborationPage("Loginimage")) {
			wexCollaborationPage.enterTextOnCollaborationPage("username", VypotaIntegrationVariables.username);
			wexCollaborationPage.clickOnElementsOfCollaborationPage("Nextbutton");
			wexCollaborationPage.enterTextOnCollaborationPage("Passwordbutton", VypotaIntegrationVariables.password);
			wexCollaborationPage.clickOnElementsOfCollaborationPage("Loginbutton");
			waitForPageLoaded();
			Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Meetings"),
					"Navigated to Vyopta Page");
		} else {
			Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Meetings"),
					"Navigated to Vyopta Page");
		}
	}

	/**
	 * Verify Bad Quality of Experience by Network widget
	 */

	@Test(priority = 23, groups = {
			"REGRESSION_VYOPTA" }, description = "Verify Bad Quality of Experience by Network widget in Collaboration Experience Dashboard")
	public final void VerifyBadQualityofExperiencebyNetworkwidgetinCollaborationExperienceDashboard() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.mouseHoverOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover").equals("Created by HP"),
				"Collaboration Experience Created by HP is not present/matching.");
		wexCollaborationPage.actionClickOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.scrollTillViewCollaborationPage("BadQualitycollaboartionpage");
		wexCollaborationPage.verifyElementsOfCollaborationPage("BadQualitycollaboartionpage");
		wexCollaborationPage.verifyElementsOfCollaborationPage("BadQualityViewButton");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("BadQualitymenu");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("ExportPNGButton"),
				"ExportPNGButton is present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("CopyasPNG"), "CopyasPNG is present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Removebutton"),
				"Removebutton is present");
		sleeper(2000);
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("NetworkWigdet");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("BadQualityViewButton");
		wexCollaborationPage.switchToNewTab();
		if (wexCollaborationPage.verifyElementsOfCollaborationPage("Loginimage")) {
			wexCollaborationPage.enterTextOnCollaborationPage("username", VypotaIntegrationVariables.username);
			wexCollaborationPage.clickOnElementsOfCollaborationPage("Nextbutton");
			wexCollaborationPage.enterTextOnCollaborationPage("Passwordbutton", VypotaIntegrationVariables.password);
			wexCollaborationPage.clickOnElementsOfCollaborationPage("Loginbutton");
			waitForPageLoaded();
			Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Meetings"),
					"Navigated to Vyopta Page");
		} else {
			Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Meetings"),
					"Navigated to Vyopta Page");
		}

	}

	/**
	 * Verify Common End User Issues widget
	 */

	@Test(priority = 24, groups = { "REGRESSION_VYOPTA" }, description = "Verify Common End User Issues widget")
	public final void VerifyCommonEndUserIssueswidgetinCollaborationExperienceDashboard() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.mouseHoverOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover").equals("Created by HP"),
				"Collaboration Experience Created by HP is not present/matching.");
		wexCollaborationPage.actionClickOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.scrollTillViewCollaborationPage("CommonEndUser");
		wexCollaborationPage.verifyElementsOfCollaborationPage("CommonEndUser");
		wexCollaborationPage.verifyElementsOfCollaborationPage("CommonEndUserContent");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("CommonEndUsermenu");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("ExportPNGButton"),
				"ExportPNGButton is present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("CopyasPNG"), "CopyasPNG is present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Removebutton"),
				"Removebutton is present");
	}

	/**
	 * Verify Bad Quality of Experience widgets
	 */

	@Test(priority = 25, groups = { "REGRESSION_VYOPTA" }, description = "Verify Bad Quality of Experience widgets")
	public final void VerifyBadQualityofExperiencewidgetsinCollaborationExperienceDashboard() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.mouseHoverOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover").equals("Created by HP"),
				"Collaboration Experience Created by HP is not present/matching.");
		wexCollaborationPage.actionClickOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.scrollTillViewCollaborationPage("BadQualityofExperience");
		wexCollaborationPage.verifyElementsOfCollaborationPage("BadQualityofExperience");
		wexCollaborationPage.verifyElementsOfCollaborationPage("BadQualityExperienceContent");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("BadQualityExperience");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("ExportPNGButton"),
				"ExportPNGButton is present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("CopyasPNG"), "CopyasPNG is present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Removebutton"),
				"Removebutton is present");
	}
	
	/**
	 * Verify Meeting Quality Stacked Bar chart per platform widgets
	 */

	@Test(priority = 26, groups = {
			"REGRESSION_VYOPTA" }, description = "Verify Meeting Quality Stacked Bar chart per platform widgets")
	public final void VerifyMeetingQualityStackedBarchartperplatformwidgetsinCollaborationExperienceDashboard()
			throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.mouseHoverOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover").equals("Created by HP"),
				"Collaboration Experience Created by HP is not present/matching.");
		wexCollaborationPage.actionClickOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.verifyElementsOfCollaborationPage("MeetingTeams");
		wexCollaborationPage.scrollTillViewCollaborationPage("MeetingZoom");
		wexCollaborationPage.verifyElementsOfCollaborationPage("MeetingZoom");
		wexCollaborationPage.scrollTillViewCollaborationPage("MeetingCisco");
		wexCollaborationPage.verifyElementsOfCollaborationPage("MeetingCisco");
		wexCollaborationPage.scrollTillViewCollaborationPage("MeetingTeams");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("GreatButton"),
				"GreatButton is not present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("FairButton"),
				"FairButton is not present");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("PoorButton"),
				"PoorButton is not present");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("MSFTTeamsviewdetails");
		waitForPageLoaded();
		if (wexCollaborationPage.verifyElementsOfCollaborationPage("Loginimage")) {
			wexCollaborationPage.enterTextOnCollaborationPage("username", VypotaIntegrationVariables.username);
			wexCollaborationPage.clickOnElementsOfCollaborationPage("Nextbutton");
			wexCollaborationPage.enterTextOnCollaborationPage("Passwordbutton", VypotaIntegrationVariables.password);
			wexCollaborationPage.clickOnElementsOfCollaborationPage("Loginbutton");
			waitForPageLoaded();
			Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Meetings"),
					"Navigated to Vyopta Page");
		} else {
			Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Meetings"),
					"Navigated to Vyopta Page");
		}

	}

	/**
	 * Verify Quality of Experience Donut chart widgets
	 */

	@Test(priority = 27, groups = {
			"REGRESSION_VYOPTA" }, description = "Verify Quality of Experience Donut chart widgets")
	public final void VerifyQualityofExperienceDonutchartwidgetsinCollaborationExperienceDashboard() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.mouseHoverOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover").equals("Created by HP"),
				"Collaboration Experience Created by HP is not present/matching.");
		wexCollaborationPage.actionClickOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.scrollTillViewCollaborationPage("RemoteParticipant");
		wexCollaborationPage.verifyElementsOfCollaborationPage("RemoteParticipant");
		wexCollaborationPage.scrollTillViewCollaborationPage("CorporateNetwork");
		wexCollaborationPage.verifyElementsOfCollaborationPage("CorporateNetwork");
		wexCollaborationPage.scrollTillViewCollaborationPage("BadQuality");
		wexCollaborationPage.verifyElementsOfCollaborationPage("BadQuality");
		wexCollaborationPage.scrollTillViewCollaborationPage("VideoEndpointQuality");
		wexCollaborationPage.verifyElementsOfCollaborationPage("VideoEndpointQuality");
		wexCollaborationPage.scrollTillViewCollaborationPage("EndUserDeviceQuality");
		wexCollaborationPage.verifyElementsOfCollaborationPage("EndUserDeviceQuality");
		wexCollaborationPage.verifyElementsOfCollaborationPage("DonutGreat");
		wexCollaborationPage.verifyElementsOfCollaborationPage("DonutFair");
		wexCollaborationPage.verifyElementsOfCollaborationPage("DonutPoor");
		wexCollaborationPage.scrollTillViewCollaborationPage("RemoteParticipant");
		wexCollaborationPage.verifyElementsOfCollaborationPage("RemoteParticipantDonut");
		wexCollaborationPage.mouseHoverOfCollaborationPage("Donut");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("RemoteParticipantViewDetails");
		waitForPageLoaded();
		wexCollaborationPage.switchToNewTab();
		if (wexCollaborationPage.verifyElementsOfCollaborationPage("Loginimage")) {
			wexCollaborationPage.enterTextOnCollaborationPage("username", VypotaIntegrationVariables.username);
			wexCollaborationPage.clickOnElementsOfCollaborationPage("Nextbutton");
			wexCollaborationPage.enterTextOnCollaborationPage("Passwordbutton", VypotaIntegrationVariables.password);
			wexCollaborationPage.clickOnElementsOfCollaborationPage("Loginbutton");
			waitForPageLoaded();
			Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Meetings"),
					"Navigated to Vyopta Page");
		} else {
			Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Meetings"),
					"Navigated to Vyopta Page");
		}

	}

	/**
	 * Verify Meeting Quality Score by Platform widget
	 */

	@Test(priority = 28, groups = {
			"REGRESSION_VYOPTA" }, description = "Verify Quality of Experience Donut chart widgets")
	public final void VerifyMeetingQualityScorebyPlatformwidgetinCollaborationExperienceDashboard() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.mouseHoverOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("collaborationExperienceHover").equals("Created by HP"),
				"Collaboration Experience Created by HP is not present/matching.");
		wexCollaborationPage.actionClickOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperiencePageTitle");
		wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperiencetitle");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("MeetingSummary");
		wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("Subtitle");
		Assert.assertTrue(
				wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("MeetingSummaryContentUpper"),
				"Content is not present");
		Assert.assertTrue(
				wexCollaborationPage.verifyElementIsDisplayedOfCollaborationPage("MeetingSummaryContentLower"),
				"Content is not present");
		wexCollaborationPage.mouseHoverOfCollaborationPage("Infobutton");
		wexCollaborationPage.getTextOfCollaborationPage("InfoText");
		Assert.assertTrue(
				wexCollaborationPage.getTextOfCollaborationPage("InfoText").equals("Meeting quality is between 1-3."),
				"Meeting quality is between 1-3. is not present/matching.");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Drilldownbutton");
		wexCollaborationPage.switchToNewTab();
		if (wexCollaborationPage.verifyElementsOfCollaborationPage("Loginimage")) {
			wexCollaborationPage.enterTextOnCollaborationPage("username", VypotaIntegrationVariables.username);
			wexCollaborationPage.clickOnElementsOfCollaborationPage("Nextbutton");
			wexCollaborationPage.enterTextOnCollaborationPage("Passwordbutton", VypotaIntegrationVariables.password);
			wexCollaborationPage.clickOnElementsOfCollaborationPage("Loginbutton");
			waitForPageLoaded();
			Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Meetings"),
					"Navigated to Vyopta Page");
		} else {
			Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Meetings"),
					"Navigated to Vyopta Page");
		}
	}

	/**
	 * Verify View Details on widgets
	 */

	@Test(priority = 29, groups = { "REGRESSION_VYOPTA" }, description = "Verify View Details on widgets")
	public final void VerifyViewDetailsonwidgetsinCollaborationExperienceDashboard() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WXPCollaborationPage wexCollaborationPage = new WXPCollaborationPage(PreDefinedActions.getDriver())
				.getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		wexCollaborationPage.verifyElementsOfCollaborationPage("collaborationExperience");
		wexCollaborationPage.actionClickOfCollaborationPage("collaborationExperience");
		waitForPageLoaded();
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("ViewDetails"),
				"Content is not present");
		wexCollaborationPage.scrollTillViewCollaborationPage("MSFTTeamsviewdetails");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("MSFTTeamsviewdetails"),
				"Content is not present");
		wexCollaborationPage.scrollTillViewCollaborationPage("MeetingTeamsviewdetails");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("MeetingTeamsviewdetails"),
				"Content is not present");
		wexCollaborationPage.scrollTillViewCollaborationPage("MeetingZoomviewdetails");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("MeetingZoomviewdetails"),
				"Content is not present");
		wexCollaborationPage.scrollTillViewCollaborationPage("RemoteParticipantViewDetails");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("RemoteParticipantViewDetails"),
				"Content is not present");
		wexCollaborationPage.scrollTillViewCollaborationPage("MeetingCiscoviewdetails");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("MeetingCiscoviewdetails"),
				"Content is not present");
		wexCollaborationPage.scrollTillViewCollaborationPage("MeetingCiscoviewdetails");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("MeetingCiscoviewdetails"),
				"Content is not present");
		wexCollaborationPage.scrollTillViewCollaborationPage("BadQualityViewButton");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("BadQualityViewButton"),
				"Content is not present");
		wexCollaborationPage.scrollTillViewCollaborationPage("VideoEndpointviewdetails");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("VideoEndpointviewdetails"),
				"Content is not present");
		wexCollaborationPage.scrollTillViewCollaborationPage("Endpointviewdetails");
		Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Endpointviewdetails"),
				"Content is not present");
		wexCollaborationPage.clickOnElementsOfCollaborationPage("Endpointviewdetails");
		waitForPageLoaded();
		wexCollaborationPage.switchToNewTab();
		if (wexCollaborationPage.verifyElementsOfCollaborationPage("Loginimage")) {
			wexCollaborationPage.enterTextOnCollaborationPage("username", VypotaIntegrationVariables.username);
			wexCollaborationPage.clickOnElementsOfCollaborationPage("Nextbutton");
			wexCollaborationPage.enterTextOnCollaborationPage("Passwordbutton", VypotaIntegrationVariables.password);
			wexCollaborationPage.clickOnElementsOfCollaborationPage("Loginbutton");
			waitForPageLoaded();
			Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Endpointvyopta"),
					"Navigated to Vyopta Page");
		} else {
			Assert.assertTrue(wexCollaborationPage.verifyElementsOfCollaborationPage("Endpointvyopta"),
					"Navigated to Vyopta Page");
		}
	}

	/**
	 * View Video Endpoint Details Within WXP
	 */
	@Test(priority = 30, groups = {
			"REGRESSION_VYOPTAINTEGRATION" }, description = "View Video Endpoint Details Within WXP")
	public final void ViewVideoEndpointDetailsWithinWXP() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPVideoEndpointsPage wexVideoEndpointPage = new WXPVideoEndpointsPage(PreDefinedActions.getDriver())
				.getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,
				CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),
				"Video Endpoints Title is not as expected");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("VideoEndpointcontent"),
				"Video content is displayed");
		LOGGER.info("Sucessfully inside video Endpoints  page.");
		LOGGER.info("Workforce Experience Vyopta Video Endpoints Page Redirection Test Passed");
	}

	/**
	 * Verify the redirection to video endpoints page
	 */
	@Test(priority = 31, groups = {
			"REGRESSION_VYOPTAINTEGRATION" }, description = "Verify the redirection to video endpoints page")
	public final void verifyVideoEndpointPageRedirection() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPVideoEndpointsPage wexVideoEndpointPage = new WXPVideoEndpointsPage(PreDefinedActions.getDriver())
				.getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,
				CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),
				"Video Endpoints Title is not as expected");
		LOGGER.info("Sucessfully inside video Endpoints  page.");
		LOGGER.info("Workforce Experience Vyopta Video Endpoints Page Redirection Test Passed");
		List<String> expectedColumnList = new ArrayList<String>(
				Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.endpointname"),
						getTextLanguage(LanguageCode, "daas_ui", "web.application.config.table.column.category"),
						getTextLanguage(LanguageCode, "daas_ui", "asset_details_manufacturer"),
						getTextLanguage(LanguageCode, "daas_ui", "asset_detail.accessories_column.model"),
						getTextLanguage(LanguageCode, "daas_ui", "asset_details_serialNumber"),
						getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.ip_address"),
						getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.mac_address"),
						getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.endpointtags"),
						getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.vyoptamonitored"),
						getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.totalcalls"),
						getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.totalmins"),
						getTextLanguage(LanguageCode, "daas_ui", "poly.raas.devices.date_added")));
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageList");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTableRows");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTableCount");
		resetTableConfiguration();
		Assert.assertTrue(wexVideoEndpointPage.verifyTableColumns(expectedColumnList, "videoEndpointsTableColumns"),
				"Table Columns are not as expected");
		LOGGER.info("Workforce Experience Vyopta Video Endpoints Page Redirection Test Passed");
	}

	/**
	 * Display Profile & System Info as per Figma
	 */
	@Test(priority = 32, groups = {
			"REGRESSION_VYOPTAINTEGRATION" }, description = "Display Profile & System Info as per Figma")
	public final void DisplayProfileSystemInfoasperFigma() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPVideoEndpointsPage wexVideoEndpointPage = new WXPVideoEndpointsPage(PreDefinedActions.getDriver())
				.getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,
				CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),
				"Video Endpoints Title is not as expected");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("Firstrow");
		waitForPageLoaded();
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Firstrowtitle");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Profile"), "Profile is not present");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Description ");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("AltName");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Price");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Devicetag");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Roomname");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Building");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Loaction");
		wexVideoEndpointPage.scrollTillViewOnVideoEndpointsPage("Peripheralstatus");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Peripheralstatus"),
				"Peripheralstatus is not as present");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Peripheralstatusdetails");
		wexVideoEndpointPage.scrollTillViewOnVideoEndpointsPage("Hardware");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Hardware"),
				"Hardware is not as present");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("HArdwaredetails");
		wexVideoEndpointPage.scrollTillViewOnVideoEndpointsPage("Software");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Software"),
				"Softwarae is not as present");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Softwaredetails");
		wexVideoEndpointPage.scrollTillViewOnVideoEndpointsPage("NetworkButton");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("NetworkButton"),
				"Network is not as present");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Networkdetails");
	}

	/**
	 * Endpoint Redirects to Correct Vyopta Page
	 */
	@Test(priority = 33, groups = {
			"REGRESSION_VYOPTAINTEGRATION" }, description = "Endpoint Redirects to Correct Vyopta Page")
	public final void EndpointRedirectstoCorrectVyoptaPage() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPVideoEndpointsPage wexVideoEndpointPage = new WXPVideoEndpointsPage(PreDefinedActions.getDriver())
				.getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,
				CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),
				"Video Endpoints Title is not as expected");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("Firstrow");
		waitForPageLoaded();
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("ViewVyoptaButton");
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Endpointtilepage"),
				"Endpointtilepage is not present");
		wexVideoEndpointPage.switchToIframeVideoEndpointsPage();
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("HardwareDetails"),
				"HardwareDetails is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Network"), "Network is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Manufacturebutton"),
				"Manufacturebutton is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("HPbutton"),
				"HPbutton is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Model"), "Model is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Modelname"),
				"Modelname is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("SerialNumber"),
				"SerialNumber is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Serial"), "Serial is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Categorybutton"),
				"Categorybutton is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Category"),
				"Category is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Configurationdevice"),
				"Configurationdeviceis not present");
	}

	/**
	 * Export Selected Endpoint Details
	 */
	@Test(priority = 34, groups = { "REGRESSION_VYOPTAINTEGRATION" }, description = "Export Selected Endpoint Details")
	public final void ExportSelectedEndpointDetails() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPVideoEndpointsPage wexVideoEndpointPage = new WXPVideoEndpointsPage(PreDefinedActions.getDriver())
				.getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,
				CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),
				"Video Endpoints Title is not as expected");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("Checkbox");
		wexVideoEndpointPage.waitForElementsOfVideoEndpointsPage("ExportButton");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("ExportButton");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("ExportTitle");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("FullDialog");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Cancelbutton");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("CurrentTable");
		waitForPageLoaded();
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("ExportNotification");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("NotificationCenter");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("NotificationTab");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("ImportExportAction");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("DownloadFile");
		String CustomName = "Video_Endpoints";
		String updatedCustomName = CustomName + ".csv";
		LOGGER.info("Updated Custom Name: " + updatedCustomName);
		String filePath = ConstantPath.DOWNLOAD_PATH + "updatedCustomName";
		File file = new File(filePath);
		LOGGER.info("Checking file path: " + filePath);
		LOGGER.info("File exists: " + file.exists());
		LOGGER.info("File exists: " + wexVideoEndpointPage.isFileExists(filePath));
	}

	/**
	 * Verify Filtering and clear filter button
	 */
	@Test(priority = 35, groups = {
			"REGRESSION_VYOPTAINTEGRATION" }, description = "Verify Filtering and clear filter button")
	public final void VerifyFilteringandclearfilterbutton() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPVideoEndpointsPage wexVideoEndpointPage = new WXPVideoEndpointsPage(PreDefinedActions.getDriver())
				.getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,
				CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),
				"Video Endpoints Title is not as expected");
		wexVideoEndpointPage.actionClickOfVideoEndpointsPage("Manufacturerdropdown");
		wexVideoEndpointPage.actionClickOfVideoEndpointsPage("HP");
		waitForPageLoaded();
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("Clearfilterbutton");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageList"),
				"video end point page list  is not display");
	}

	/**
	 * Show KPI Details of Last 30 Days
	 */
	@Test(priority = 36, groups = { "REGRESSION_VYOPTAINTEGRATION" }, description = "Show KPI Details of Last 30 Days")
	public final void ShowKPIDetailsofLast30Days() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPVideoEndpointsPage wexVideoEndpointPage = new WXPVideoEndpointsPage(PreDefinedActions.getDriver())
				.getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,
				CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),
				"Video Endpoints Title is not as expected");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("Firstrow");
		waitForPageLoaded();
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("ViewVyoptaButton");
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Endpointtilepage"),
				"Endpointtilepage is not present");
		wexVideoEndpointPage.switchToIframeVideoEndpointsPage();
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("Calltab");
		wexVideoEndpointPage.actionClickOfVideoEndpointsPage("TimeDisplay");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("LiveValue");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Endpointis"),
				"Endpointis is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Quality"), "Quality is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Calltype"),
				"Calltype is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("CallHeader"),
				"Calltype is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("CallContent"),
				"Calltype is not present");

	}

	/**
	 * Display Data Based on Vyopta Response
	 */
	@Test(priority = 37, groups = {
			"REGRESSION_VYOPTAINTEGRATION" }, description = "Display Data Based on Vyopta Response")
	public final void DisplayDataBasedonVyoptaResponse() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPVideoEndpointsPage wexVideoEndpointPage = new WXPVideoEndpointsPage(PreDefinedActions.getDriver())
				.getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,
				CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),
				"Video Endpoints Title is not as expected");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("Firstrow");
		waitForPageLoaded();
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Firstrowtitle");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("OverView");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("TotalCall");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("TotalMin");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("TotalPoorCall");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Profile"), "Profile is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Peripheralstatus"),
				"Peripheralstatus is not as present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Hardware"),
				"Hardware is not as present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Software"),
				"Softwarae is not as present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("NetworkButton"),
				"Network is not as present");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("CallView");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("CallHistoryTab");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("setting");
		sleeper(2000);
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("Qualitybutton");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("SaveButton");
		sleeper(3000);
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("setting");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("ResetButton");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("SaveButton");
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("DetailscallHistorypage"),
				"History Page is not as present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("ViewVyoptaButton"),
				"ViewVyoptaButton Page is not as present");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("ViewVyoptaButton");
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Endpointtilepage"),
				"Endpointtilepage is not present");
		wexVideoEndpointPage.switchToIframeVideoEndpointsPage();
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("Calltab");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Quality"), "Quality is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("Calltype"),
				"Calltype is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("CallHeader"),
				"Calltype is not present");
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("CallContent"),
				"Calltype is not present");
	}

	/**
	 * Verify Sorting Functionality on Video Endpoints Table
	 */
	@Test(priority = 38, groups = {
			"REGRESSION_VYOPTAINTEGRATION" }, description = "Verify Sorting Functionality on Video Endpoints Table")
	public final void VerifySortingFunctionalityonVideoEndpointsTable() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPVideoEndpointsPage wexVideoEndpointPage = new WXPVideoEndpointsPage(PreDefinedActions.getDriver())
				.getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,
				CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),
				"Video Endpoints Title is not as expected");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("endpointNameCol");
		wexVideoEndpointPage.clickByJavaScriptOnVideoEndpointsPage("endpointNameCol");
		waitForPageLoaded();
		List<String> endPointName = wexVideoEndpointPage.getAvailableEndpoints();
		boolean isDesc = wexVideoEndpointPage.getSortingOrderType("endpointNameCol");
		Assert.assertTrue(wexVideoEndpointPage.IsColumnSorted(endPointName, isDesc),
				"The serial number column is not in sorted format");
		verifyPaginationOnListPage();
	}

	/**
	 * Verify Dark Mode UI Behavior in WXP
	 */
	@Test(priority = 39, groups = {
			"REGRESSION_VYOPTAINTEGRATION" }, description = "Verify Dark Mode UI Behavior in WXP")
	public final void VerifyDarkModeUIBehaviorinWXP() throws Exception {
		login("ITADMIN_EMAIL_VYOPTA", "ITADMIN_PASSWORD_VYOPTA");
		openLeftSidePanel();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WXPVideoEndpointsPage wexVideoEndpointPage = new WXPVideoEndpointsPage(PreDefinedActions.getDriver())
				.getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,
				CommonVariables.CUSTOMER_DEVICES_VIDEOENDPOINTS);
		waitForPageLoaded();
		Assert.assertTrue(wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle"),
				"Video Endpoints Title is not as expected");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("UserSettings");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("DarkMode");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("UserSettings");
		List<String> expectedColumnList = new ArrayList<String>(
				Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.endpointname"),
						getTextLanguage(LanguageCode, "daas_ui", "web.application.config.table.column.category"),
						getTextLanguage(LanguageCode, "daas_ui", "asset_details_manufacturer"),
						getTextLanguage(LanguageCode, "daas_ui", "asset_detail.accessories_column.model"),
						getTextLanguage(LanguageCode, "daas_ui", "asset_details_serialNumber"),
						getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.ip_address"),
						getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.mac_address"),
						getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.endpointtags"),
						getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.vyoptamonitored"),
						getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.totalcalls"),
						getTextLanguage(LanguageCode, "daas_ui", "vyoptalist.header.totalmins"),
						getTextLanguage(LanguageCode, "daas_ui", "poly.raas.devices.date_added")));

		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTitle");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageList");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTableRows");
		wexVideoEndpointPage.verifyElementsOfVideoEndpointsPage("videoEndpointPageTableCount");
		resetTableConfiguration();
		Assert.assertTrue(wexVideoEndpointPage.verifyTableColumns(expectedColumnList, "videoEndpointsTableColumns"),
				"Table Columns are not as expected");
		LOGGER.info("Workforce Experience Vyopta Video Endpoints Page Redirection Test Passed");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("UserSettings");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("LightMode");
		wexVideoEndpointPage.clickOnElementsOfVideoEndpointsPage("UserSettings");
	}
}

