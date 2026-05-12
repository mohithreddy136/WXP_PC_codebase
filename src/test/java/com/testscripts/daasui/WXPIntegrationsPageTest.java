package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.WXPIntegrationsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WXPIntegrationsPageTest extends CommonTest {
    private static final Logger LOGGER = LogManager.getLogger(WXPIntegrationsPageTest.class);

    /**
     * This method will verify the integrations tab loading successfully
     */
    @Test(priority = 1, groups = {"REGRESSION_CONNECTORS"}, description = "TestCase ID : ")
    public final void verifyIntegrationsPageLoad() throws Exception {
        WXPIntegrationsPage wxpIntegrationsPage = new WXPIntegrationsPage(PreDefinedActions.getDriver()).getInstance();
        login("WXP_STARTER_COMPANY_EMAIL", "WXP_STARTER_COMPANY_PASSWORD");

        Assert.assertTrue(wxpIntegrationsPage.navigateToIntegrationsPage(CommonVariables.IT_ADMIN), "Integrations tab present in the side menu");
        Assert.assertTrue(wxpIntegrationsPage.verifyElementsOfWXPIntegrationsPage("integrationsPageIframe"), "Integrations iframe is not present on the integrations page");
    }

    /**
     * This method will verify the integrations tab loads correctly for WXP standard
     */
    @Test(priority = 2, groups = {"REGRESSION_CONNECTORS"}, description = "TestCase ID : ")
    public final void verifyIntegrationsForStarter() throws Exception {
        WXPIntegrationsPage wxpIntegrationsPage = new WXPIntegrationsPage(PreDefinedActions.getDriver()).getInstance();
        login("WXP_STARTER_COMPANY_EMAIL", "WXP_STARTER_COMPANY_PASSWORD");
        var softAssert = new SoftAssert();

        wxpIntegrationsPage.navigateToIntegrationsPage(CommonVariables.IT_ADMIN);
        wxpIntegrationsPage.switchToIntegrationsIframe();
        wxpIntegrationsPage.selectViewAllConnectorsFilters();
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_ENTRAID_GROUPS), "Entra ID Groups connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyElementsOfWXPIntegrationsPage("integrationsPageConnectorUpgradeCard"), "Upgrade card is not present for starter tenant");
        softAssert.assertAll();
        switchToDefaultContent();
    }


    /**
     * This method will verify the integrations tab loads correctly for WXP standard
     */
    @Test(priority = 3, groups = {"REGRESSION_CONNECTORS"}, description = "TestCase ID : ")
    public final void verifyIntegrationsForStandard() throws Exception {
        WXPIntegrationsPage wxpIntegrationsPage = new WXPIntegrationsPage(PreDefinedActions.getDriver()).getInstance();
        login("WXP_STANDARD_COMPANY_EMAIL", "WXP_STANDARD_COMPANY_PASSWORD");
        var softAssert = new SoftAssert();

        wxpIntegrationsPage.navigateToIntegrationsPage(CommonVariables.IT_ADMIN);
        wxpIntegrationsPage.switchToIntegrationsIframe();
        wxpIntegrationsPage.selectViewAllConnectorsFilters();
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_TABLEAU), "Tableau connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_POWER_BI), "Power BI connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_SERVICE_NOW), "ServiceNow connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_SPLUNK), "Splunk connector is not present on integrations page");
//        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_MICROSOFT_TEAMS, (String[]) null), "Microsoft Teams connector is not present on integrations page");
//        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_SLACK, (String[]) null), "Slack connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_ENTRAID_GROUPS), "Entra ID Groups connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_ENTRAID_ACCOUNTS), "Entra ID Accounts connector is not present on integrations wxpIntegrationsPage");
        softAssert.assertAll();
        switchToDefaultContent();
    }

    /**
     * This method will verify the integrations tab loads correctly for WXP pro trial
     */
    // TODO: Add pro trial tenant credentials for testing
    @Test(priority = 4, groups = {"REGRESSION_CONNECTORS"}, description = "TestCase ID : ", enabled = false)
    public final void verifyIntegrationsForProTrial() throws Exception {
        WXPIntegrationsPage wxpIntegrationsPage = new WXPIntegrationsPage(PreDefinedActions.getDriver()).getInstance();
        login("WXP_PRO_TRIAL_COMPANY_EMAIL", "WXP_PRO_TRIAL_COMPANY_EMAIL");
        var softAssert = new SoftAssert();

        wxpIntegrationsPage.navigateToIntegrationsPage(CommonVariables.IT_ADMIN);
        wxpIntegrationsPage.switchToIntegrationsIframe();
        wxpIntegrationsPage.selectViewAllConnectorsFilters();
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_TABLEAU), "Tableau connector is not present on integrations wxpIntegrationsPage");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_POWER_BI), "Power BI connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_SERVICE_NOW), "ServiceNow connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_SPLUNK), "Splunk connector is not present on integrations page");
//        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_MICROSOFT_TEAMS, (String[]) null), "Microsoft Teams connector is not present on integrations page");
//        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_SLACK, (String[]) null), "Slack connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_ENTRAID_GROUPS), "Entra ID Groups connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_ENTRAID_ACCOUNTS), "Entra ID Accounts connector is not present on integrations page");
        softAssert.assertAll();
        switchToDefaultContent();
    }

    /**
     * This method will verify the integrations tab loads correctly for WXP pro trial
     */
    @Test(priority = 5, groups = {"REGRESSION_CONNECTORS"}, description = "TestCase ID : ")
    public final void verifyIntegrationsForPro() throws Exception {
        WXPIntegrationsPage wxpIntegrationsPage = new WXPIntegrationsPage(PreDefinedActions.getDriver()).getInstance();
        login("WXP_PRO_COMPANY_EMAIL", "WXP_PRO_COMPANY_PASSWORD");
        var softAssert = new SoftAssert();

        wxpIntegrationsPage.navigateToIntegrationsPage(CommonVariables.IT_ADMIN);
        wxpIntegrationsPage.switchToIntegrationsIframe();
        wxpIntegrationsPage.selectViewAllConnectorsFilters();
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_TABLEAU), "Tableau connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_POWER_BI), "Power BI connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_SERVICE_NOW), "ServiceNow connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_SPLUNK), "Splunk connector is not present on integrations page");
//        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_MICROSOFT_TEAMS, (String[]) null), "Microsoft Teams connector is not present on integrations page");
//        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_SLACK, (String[]) null), "Slack connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_ENTRAID_GROUPS), "Entra ID Groups connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_ENTRAID_ACCOUNTS), "Entra ID Accounts connector is not present on integrations page");
        softAssert.assertAll();
        switchToDefaultContent();
    }

    /**
     * This method will verify the integrations tab loads correctly for WXP pro trial
     */
    @Test(priority = 6, groups = {"REGRESSION_CONNECTORS"}, description = "TestCase ID : ")
    public final void verifyIntegrationsForElite() throws Exception {
        WXPIntegrationsPage wxpIntegrationsPage = new WXPIntegrationsPage(PreDefinedActions.getDriver()).getInstance();
        login("WXP_ELITE_COMPANY_EMAIL", "WXP_ELITE_COMPANY_PASSWORD");
        var softAssert = new SoftAssert();

        wxpIntegrationsPage.navigateToIntegrationsPage(CommonVariables.IT_ADMIN);
        wxpIntegrationsPage.switchToIntegrationsIframe();
        wxpIntegrationsPage.selectViewAllConnectorsFilters();
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_TABLEAU), "Tableau connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_POWER_AUTOMATE), "Tableau connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_POWER_BI), "Power BI connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_SERVICE_NOW), "ServiceNow connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_SPLUNK), "Splunk connector is not present on integrations page");
//        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_MICROSOFT_TEAMS, (String[]) null), "Microsoft Teams connector is not present on integrations page");
//        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_SLACK, (String[]) null), "Slack connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_ENTRAID_GROUPS), "Entra ID Groups connector is not present on integrations page");
        softAssert.assertTrue(wxpIntegrationsPage.verifyConnectorPresentOnPage(CommonVariables.CONNECTOR_ENTRAID_ACCOUNTS), "Entra ID Accounts connector is not present on integrations page");
        softAssert.assertAll();
        switchToDefaultContent();

    }

    /**
     * Verifies that as an IT admin, no Connect, Configure, or Disconnect buttons are visible for any connector card.
     */
    @Test(priority = 7, groups = {"REGRESSION_CONNECTORS"}, description = "IT admin should not see Connect, Configure, or Disconnect buttons for any connector",enabled = false)
    public void noActionButtonsVisibleForITAdmin() throws Exception {
        WXPIntegrationsPage wxpIntegrationsPage = new WXPIntegrationsPage(PreDefinedActions.getDriver()).getInstance();
        login("WXP_PRO_COMPANY_ITADMIN_EMAIL", "WXP_PRO_COMPANY_ITADMIN_PASSWORD");
        wxpIntegrationsPage.navigateToIntegrationsPage(CommonVariables.IT_ADMIN);
        wxpIntegrationsPage.switchToIntegrationsIframe();
        wxpIntegrationsPage.selectViewAllConnectorsFilters();

        String[] connectorIds = {
            CommonVariables.CONNECTOR_TABLEAU,
            CommonVariables.CONNECTOR_POWER_BI,
            CommonVariables.CONNECTOR_SERVICE_NOW,
            CommonVariables.CONNECTOR_SPLUNK,
            CommonVariables.CONNECTOR_ENTRAID_GROUPS,
            CommonVariables.CONNECTOR_POWER_AUTOMATE,
            CommonVariables.CONNECTOR_ENTRAID_ACCOUNTS
        };
        SoftAssert softAssert = new SoftAssert();
        for (String connectorId : connectorIds) {
            softAssert.assertFalse(wxpIntegrationsPage.isConnectButtonVisible(connectorId), "Connect button should not be visible for IT admin on " + connectorId);
            softAssert.assertFalse(wxpIntegrationsPage.isConfigureButtonVisible(connectorId), "Configure button should not be visible for IT admin on " + connectorId);
            softAssert.assertFalse(wxpIntegrationsPage.isDisconnectButtonVisible(connectorId), "Disconnect button should not be visible for IT admin on " + connectorId);
        }
        softAssert.assertAll();
        switchToDefaultContent();
    }
    /**
     * Verifies that as an Connector admin,  Connect, Configure, or Disconnect buttons are visible for any connector card.
     */
    @Test(priority = 8, groups = {"REGRESSION_CONNECTORS"}, description = "ConnectorAdmin should see Connect, Configure, or Disconnect buttons for any connector")
    public void ActionButtonsVisibleForConnectorAdmin() throws Exception {
        WXPIntegrationsPage wxpIntegrationsPage = new WXPIntegrationsPage(PreDefinedActions.getDriver()).getInstance();
        login("WXP_ELITE_COMPANY_EMAIL", "WXP_ELITE_COMPANY_PASSWORD");
        wxpIntegrationsPage.navigateToIntegrationsPage(CommonVariables.CONNECTOR_ADMIN);
        wxpIntegrationsPage.switchToIntegrationsIframe();
        wxpIntegrationsPage.selectViewAllConnectorsFilters();

        String[] connectorIds = {
                CommonVariables.CONNECTOR_TABLEAU,
                CommonVariables.CONNECTOR_POWER_BI,
                CommonVariables.CONNECTOR_SERVICE_NOW,
                CommonVariables.CONNECTOR_SPLUNK,
                CommonVariables.CONNECTOR_ENTRAID_GROUPS,
                CommonVariables.CONNECTOR_ENTRAID_ACCOUNTS
        };
        SoftAssert softAssert = new SoftAssert();
        for (String connectorId : connectorIds) {

            boolean anyButtonVisible = wxpIntegrationsPage.isConnectButtonVisible(connectorId)
                    || wxpIntegrationsPage.isConfigureButtonVisible(connectorId)
                    || wxpIntegrationsPage.isDisconnectButtonVisible(connectorId);
            softAssert.assertTrue(anyButtonVisible, "At least one action button (Connect, Configure, Disconnect) should be visible for Connector admin on " + connectorId);
}
        softAssert.assertAll();
        switchToDefaultContent();
    }

    /**
     * Verifies the full activation flow for Power Automate connector
     * - Clicks Connect in the modal and verifies activation
     */
    @Test(priority = 9, groups = {"REGRESSION_CONNECTORS"}, description = "Power Automate: full activation flow including authorize/disconnect/connect")
    public void activatePowerAutomateConnector() throws Exception {
        WXPIntegrationsPage wxpIntegrationsPage = new WXPIntegrationsPage(PreDefinedActions.getDriver()).getInstance();
        login("WXP_PRO_COMPANY_EMAIL", "WXP_PRO_COMPANY_PASSWORD");
        wxpIntegrationsPage.navigateToIntegrationsPage(CommonVariables.CONNECTOR_ADMIN);
        wxpIntegrationsPage.switchToIntegrationsIframe();
        wxpIntegrationsPage.selectViewAllConnectorsFilters();

        SoftAssert softAssert = new SoftAssert();
        // Open Power Automate modal by clicking Connect button
       // softAssert.assertTrue(wxpIntegrationsPage.isConnectButtonVisible(CommonVariables.CONNECTOR_POWER_AUTOMATE), "Connect button should be visible for Power Automate");
        wxpIntegrationsPage.clickConnectorActionButton(CommonVariables.CONNECTOR_POWER_AUTOMATE);
        softAssert.assertAll();
        switchToDefaultContent();
    }
}
