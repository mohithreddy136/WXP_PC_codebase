package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.pages.WEPPartnerAndCustomerCreationPage;
import com.daasui.pages.WEPPartnerCustomersPage;
import com.daasui.pages.WEPPartnerSubscriptionContractPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WEPPartnerSubscriptionContractTest extends CommonTest {
    private static Logger LOGGER = LogManager.getLogger(WEPPartnerSubscriptionContractTest.class);
    public static final String Elite_Plan = "SKU-WXP-ELITE-DAAS-TEST - Workforce Experience Elite";
    public static final String Pro_Plan = "SKU-WXP-PRO-DAAS-TEST - Workforce Experience Pro";

    @Test(priority = 1, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0001")
    public final void AddingSubscriptionContractSinglePlan() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
        LOGGER.info("Logged in successfully");
        WEPPartnerSubscriptionContractPage WEPPartnerSubscriptionContractPage = new WEPPartnerSubscriptionContractPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        wepPartnerCustomersPage.sideMenuExpansionForPartner();
        WEPPartnerSubscriptionContractPage.sideMenuSelectionWEPRootLoginPageSubCont(LanguageCode, "PartnerTopMenu", "PartnersInMenu");
        waitForPageLoaded();
        if (WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("ClearFilterbutton")) {
            WEPPartnerSubscriptionContractPage.clickByJavaScriptOnWEPPartnerSubscriptionContractPage("ClearFilterbutton");
        }
        waitForPageLoaded();
        String environment = System.getProperty("environment");
        String partnerEmail;
        if ("EU-Stage-WEP".equalsIgnoreCase(environment) || "EU-VENEER-WEP".equalsIgnoreCase(environment)) {
            partnerEmail = "addonsdashboard_partnercxeu@workforceqa.ext.hp.com";
        } else if ("US-Stage-WEP".equalsIgnoreCase(environment)|| "US-VENEER-WEP".equalsIgnoreCase(environment)) {
            partnerEmail = "addonsdashboard_partnercxus@workforceqa.ext.hp.com";
        } else {
            throw new IllegalArgumentException("Unsupported environment: " + environment);
        }
        WEPPartnerSubscriptionContractPage.enterTextOfWEPPartnerSubscriptionContractPage("EmailSerchBox", partnerEmail);
        sleeper(5000);
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("partnerNameSearch");
        waitForPageLoaded();
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("subscriptionAuthorizedSection"), "Subscription Authorized section is NOT visible");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("subscriptionContract"), "Subscription Contract section is NOT present");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("subscriptionContract");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("AddButtonSub"), "Add Subscription Contract button is NOT present");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("AddButtonSub");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("AddSubscriptionTitle"), "Add Subscription Contract Title is NOT displayed");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("AddSubscriptionSubTitle"), "Add Subscription Contract Sub-Title is NOT displayed");
        String uniqueContractId = "Subscription" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        WEPPartnerSubscriptionContractPage.enterTextOfWEPPartnerSubscriptionContractPage("contractId", uniqueContractId);
        WEPPartnerSubscriptionContractPage.selectCountryByValue("IndOption");
        WEPPartnerSubscriptionContractPage.enterTextOfWEPPartnerSubscriptionContractPage("PaymentTerm","weekly");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("ContractdateTitle"),"Contract Date title is NOT displayed");
        WEPPartnerSubscriptionContractPage.selectStartDate();
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("ContractNextbutton");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("SubTitlesteptwo"), "Subscription Contract sub title in step 2 is NOT displayed");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("PlanInput1"), "Plan input field is NOT displayed in step 2");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("PlanInput1");
        WEPPartnerSubscriptionContractPage.selectTextValueFromDropdownOfWEPPartnerSubscriptionContractPage("Planoption2", Elite_Plan, "PlanInput1");
        WEPPartnerSubscriptionContractPage.enterSeats("seatcostinput", "10");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("BackbuttonContract"), "Subscription Contract back button not present in step 2");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("CancelbuttonContract"), "Subscription Contract cancel button not present in step 2");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("SavebuttonContract");
        LOGGER.info("Subscription Contract added successfully with Contract ID: {}", uniqueContractId);
        softAssert.assertAll();
    }

    @Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0002")
    public final void EditingDeletingSubscriptionContractSinglePlan() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
        LOGGER.info("Logged in successfully");
        WEPPartnerSubscriptionContractPage WEPPartnerSubscriptionContractPage = new WEPPartnerSubscriptionContractPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        wepPartnerCustomersPage.sideMenuExpansionForPartner();
        WEPPartnerSubscriptionContractPage.sideMenuSelectionWEPRootLoginPageSubCont(LanguageCode, "PartnerTopMenu", "PartnersInMenu");
        waitForPageLoaded();
        if (WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("ClearFilterbutton")) {
            WEPPartnerSubscriptionContractPage.clickByJavaScriptOnWEPPartnerSubscriptionContractPage("ClearFilterbutton");
        }
        waitForPageLoaded();
        String environment = System.getProperty("environment");
        String partnerEmail;
        if ("EU-Stage-WEP".equalsIgnoreCase(environment) || "EU-VENEER-WEP".equalsIgnoreCase(environment)) {
            partnerEmail = "addonsdashboard_partnercxeu@workforceqa.ext.hp.com";
        } else if ("US-Stage-WEP".equalsIgnoreCase(environment)|| "US-VENEER-WEP".equalsIgnoreCase(environment)) {
            partnerEmail = "addonsdashboard_partnercxus@workforceqa.ext.hp.com";
        } else {
            throw new IllegalArgumentException("Unsupported environment: " + environment);
        }
        WEPPartnerSubscriptionContractPage.enterTextOfWEPPartnerSubscriptionContractPage("EmailSerchBox", partnerEmail);
        sleeper(5000);
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("partnerNameSearch");
        waitForPageLoaded();
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("subscriptionAuthorizedSection"), "Subscription Authorized section is NOT visible");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("subscriptionContract"), "Subscription Contract section is NOT present");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("subscriptionContract");
        WEPPartnerSubscriptionContractPage.clickByJavaScriptOnWEPPartnerSubscriptionContractPage("Menubutton");
        //Edit Subscription Contract
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("Editbutton"), "Edit option for Subscription Contract is NOT present");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("Editbutton");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("ContractNextbutton");
        WEPPartnerSubscriptionContractPage.clearTextFromWEPPartnerSubscriptionContractPageTextField("SeatsInput");
        WEPPartnerSubscriptionContractPage.enterTextOfWEPPartnerSubscriptionContractPage("SeatsInput","36");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("SavebuttonContract");
        LOGGER.info("Subscription Contract edited successfully to update Seats");
        sleeper(3000);
        WEPPartnerSubscriptionContractPage.clickByJavaScriptOnWEPPartnerSubscriptionContractPage("Menubutton");
        //Delete Subscription Contract
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("Deletebutton");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("deletetitlecontract"), "Delete title for Subscription Contract is NOT present");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("deletesubtitlecontract"), "Delete sub title for Subscription Contract is NOT present");
        String securityCode = WEPPartnerSubscriptionContractPage.getTextOfWEPPartnerSubscriptionContractPage("securitycodecontract");
        WEPPartnerSubscriptionContractPage.enterTextOfWEPPartnerSubscriptionContractPage("securitycodecontractinput", securityCode);
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("cancelbuttoncontract"), "Cancel button for Subscription Contract is NOT present");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("revokeButtoncontract"), "Revoke button for Subscription Contract is NOT present");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("revokeButtoncontract");
        LOGGER.info("Subscription Contract deleted successfully");
        softAssert.assertAll();
    }

    @Test(priority = 3, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0003")
    public final void MultiplePlanAddingSubscriptionContract() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
        LOGGER.info("Logged in successfully");
        WEPPartnerSubscriptionContractPage WEPPartnerSubscriptionContractPage = new WEPPartnerSubscriptionContractPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        wepPartnerCustomersPage.sideMenuExpansionForPartner();
        WEPPartnerSubscriptionContractPage.sideMenuSelectionWEPRootLoginPageSubCont(LanguageCode, "PartnerTopMenu", "PartnersInMenu");
        waitForPageLoaded();
        if (WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("ClearFilterbutton")) {
            WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("ClearFilterbutton");
        }
        waitForPageLoaded();
        String environment = System.getProperty("environment");
        String partnerEmail;
        if ("EU-Stage-WEP".equalsIgnoreCase(environment) || "EU-VENEER-WEP".equalsIgnoreCase(environment)) {
            partnerEmail = "addonsdashboard_partnercxeu@workforceqa.ext.hp.com";
        } else if ("US-Stage-WEP".equalsIgnoreCase(environment)|| "US-VENEER-WEP".equalsIgnoreCase(environment)) {
            partnerEmail = "addonsdashboard_partnercxus@workforceqa.ext.hp.com";
        } else {
            throw new IllegalArgumentException("Unsupported environment: " + environment);
        }
        WEPPartnerSubscriptionContractPage.enterTextOfWEPPartnerSubscriptionContractPage("EmailSerchBox", partnerEmail);
        sleeper(5000);
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("partnerNameSearch");
        waitForPageLoaded();
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("subscriptionAuthorizedSection"), "Subscription Authorized section is NOT visible");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("subscriptionContract"), "Subscription Contract section is NOT present");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("subscriptionContract");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("AddButtonSub"), "Add Subscription Contract button is NOT present");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("AddButtonSub");
        String uniqueContractId = "Subscription" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        WEPPartnerSubscriptionContractPage.enterTextOfWEPPartnerSubscriptionContractPage("contractId", uniqueContractId);
        WEPPartnerSubscriptionContractPage.selectCountryByValue("IndOption");
        WEPPartnerSubscriptionContractPage.enterTextOfWEPPartnerSubscriptionContractPage("PaymentTerm","monthly");
        WEPPartnerSubscriptionContractPage.clickByJavaScriptOnWEPPartnerSubscriptionContractPage("RenueCheckbox");
        sleeper(3000);
        WEPPartnerSubscriptionContractPage.selectStartDate();
        WEPPartnerSubscriptionContractPage.selectEndDate();
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("ContractNextbutton");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("PlanInput1"), "Plan input field is NOT displayed in step 2 for first plan");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("PlanInput1");
        WEPPartnerSubscriptionContractPage.selectTextValueFromDropdownOfWEPPartnerSubscriptionContractPage("Planoption2", Elite_Plan, "PlanInput1");
        sleeper(1000);
        WEPPartnerSubscriptionContractPage.enterSeats("SeatsInput1", "10");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("AddAnotherPlanButton");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("PlanInput2"), "Plan input field is NOT displayed in step 2 for second plan");
        sleeper(1000);
        WEPPartnerSubscriptionContractPage.selectPlanByVisibleText("PlanInput2", "Planoption3");
        WEPPartnerSubscriptionContractPage.enterSeats("SeatsInput2", "12");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("SavebuttonContract");
        LOGGER.info("Subscription Contract added successfully with Contract ID: {}", uniqueContractId);
        softAssert.assertAll();
    }

    @Test(priority = 4, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0004")
    public final void MultiplePlanEditingDeletingSubscriptionContract() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
        LOGGER.info("Logged in successfully");
        WEPPartnerSubscriptionContractPage WEPPartnerSubscriptionContractPage = new WEPPartnerSubscriptionContractPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        wepPartnerCustomersPage.sideMenuExpansionForPartner();
        WEPPartnerSubscriptionContractPage.sideMenuSelectionWEPRootLoginPageSubCont(LanguageCode, "PartnerTopMenu", "PartnersInMenu");
        waitForPageLoaded();
        if (WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("ClearFilterbutton")) {
            WEPPartnerSubscriptionContractPage.clickByJavaScriptOnWEPPartnerSubscriptionContractPage("ClearFilterbutton");

        }
        waitForPageLoaded();
        String environment = System.getProperty("environment");
        String partnerEmail;
        if ("EU-Stage-WEP".equalsIgnoreCase(environment) || "EU-VENEER-WEP".equalsIgnoreCase(environment)) {
            partnerEmail = "addonsdashboard_partnercxeu@workforceqa.ext.hp.com";
        } else if ("US-Stage-WEP".equalsIgnoreCase(environment)|| "US-VENEER-WEP".equalsIgnoreCase(environment)) {
            partnerEmail = "addonsdashboard_partnercxus@workforceqa.ext.hp.com";
        } else {
            throw new IllegalArgumentException("Unsupported environment: " + environment);
        }
        WEPPartnerSubscriptionContractPage.enterTextOfWEPPartnerSubscriptionContractPage("EmailSerchBox", partnerEmail);
        sleeper(5000);
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("partnerNameSearch");
        waitForPageLoaded();
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("subscriptionAuthorizedSection"), "Subscription Authorized section is NOT visible");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("subscriptionContract"), "Subscription Contract section is NOT present");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("subscriptionContract");
        WEPPartnerSubscriptionContractPage.clickByJavaScriptOnWEPPartnerSubscriptionContractPage("Menubutton");
        //Edit Subscription Contract
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("Editbutton");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("ContractNextbutton");
        WEPPartnerSubscriptionContractPage.enterSeats("SeatsInput2", "15");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("SavebuttonContract");
        LOGGER.info("Subscription Contract edited successfully to change costs of second plan");
        sleeper(3000);
        WEPPartnerSubscriptionContractPage.clickByJavaScriptOnWEPPartnerSubscriptionContractPage("Menubutton");
        //Delete Subscription Contract
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("Deletebutton");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("deletetitlecontract"), "Delete title for Subscription Contract is NOT present");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("deletesubtitlecontract"), "Delete sub title for Subscription Contract is NOT present");
        String securityCode = WEPPartnerSubscriptionContractPage.getTextOfWEPPartnerSubscriptionContractPage("securitycodecontract");
        WEPPartnerSubscriptionContractPage.enterTextOfWEPPartnerSubscriptionContractPage("securitycodecontractinput", securityCode);
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("cancelbuttoncontract"), "Cancel button for Subscription Contract is NOT present");
        softAssert.assertTrue(WEPPartnerSubscriptionContractPage.verifyElementsOfWEPPartnerSubscriptionContractPage("revokeButtoncontract"), "Revoke button for Subscription Contract is NOT present");
        WEPPartnerSubscriptionContractPage.clickOnElementsOfWEPPartnerSubscriptionContractPage("revokeButtoncontract");
        LOGGER.info("Subscription Contract deleted successfully");
        softAssert.assertAll();
    }
}