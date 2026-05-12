package com.daasui.pages;

import org.apache.commons.lang.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.openqa.selenium.WebDriver;

import com.daasui.constants.CommonVariables;
import org.testng.Assert;

public final class WXPIntegrationsPage extends CommonMethod {
    private static final Logger LOGGER = LogManager.getLogger(WXPIntegrationsPage.class);
    private ObjectReader WXPIntegrationsPageReader = new ObjectReader();
    private static WXPIntegrationsPage instance;
    private Properties  WXPIntegrationsPage;
    public static String uiVersion = System.getProperty("uiVersion");

    public WXPIntegrationsPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEXPartnerDashboardPage.class) {
                if (instance == null) {
                    instance = new WXPIntegrationsPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WXPIntegrationsPage(WebDriver driver) throws IOException {
        WXPIntegrationsPage = WXPIntegrationsPageReader.getObjectRepository("WXPIntegrationsPage");
    }
    /**
     * Navigate to the Integrations page via sidemenu.
     *
     * @return True if navigation succeeds, false otherwise
     */
    public boolean navigateToIntegrationsPage(String role) {
        var success = false;
        leftSideMenuVerification();
        try {
            switch (role) {
                case CommonVariables.IT_ADMIN:
                    companyView(CommonVariables.INTEGRATIONS);
                    break;
                case CommonVariables.CONNECTOR_ADMIN:
                    companyView(CommonVariables.INTEGRATIONS);
                    break;
                case CommonVariables.PARTNER_ADMIN:
                    throw new NotImplementedException();
            }
            waitForPageLoaded();
            success = true;
        } catch (Exception e) {
            final var error = "Failed to navigate to integrations page: " + e.getMessage();
            LOGGER.error(error);
        }
        return success;
    }

    /**
     * Verify if an element is present on integrations page
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is present
     */
    public final boolean verifyElementsOfWXPIntegrationsPage(String key) {
        try {
            return verifyElementIsPresent(WXPIntegrationsPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method verifyElementsOfWXPIntegrationsPage " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify that the connectors are present on the integrations page based on the plan
     *
     * @param connectorId
     * @return
     */
    public final boolean verifyConnectorPresentOnPage(String connectorId) {
        // TODO: if null action ids are passed then ignore actionIds
        // If not null and empty expect no actions to be visible
        // If not null and not empty expect the action buttons passed in actionIds to be visible for the connector
        try {
            var connectorLocator = WXPIntegrationsPage.getProperty("integrationsPageConnectorCard").replace("%CONNECTOR_ID%", connectorId);
            if (!verifyElementIsPresent(connectorLocator)) {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method verifyConnectorsPresentOnPage for " + connectorId + " " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Click on view all connectors button on integrations page to view the list of connectors available for integration
     */
    public final void selectViewAllConnectorsFilters() {
        try {
            verifyElementsOfWXPIntegrationsPage(WXPIntegrationsPage.getProperty("integrationsPageViewAllButton"));
            click(WXPIntegrationsPage.getProperty("integrationsPageViewAllButton"));
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method selectViewAllConnectors " + e.getMessage());
        }
    }

    /**
     * Match text on an element present on integrations page
     *
     * @param key         - locator of the element
     * @param textToMatch - text to be compared
     * @return - boolean value of whether both the texts match
     */
    public final boolean matchTextOfWXPIntegrationsPage(String key, String textToMatch) {
        try {
            return verifyTextPresentOnElement(WXPIntegrationsPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWXPIntegrationsPage " + e.getMessage()));
            return false;
        }
    }

    public void switchToIntegrationsIframe() {
        try {
            switchToIframe(WXPIntegrationsPage.getProperty("integrationsPageIframe"));
        } catch (Exception e) {
            Assert.fail("Failed to switch to integrations iframe: " + e.getMessage());
        }
    }
    public final String getTextOfWXPIntegrationsPage(String key) {
        try {
            return getTextBy(WXPIntegrationsPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWXPIntegrationsPage " + e.getMessage()));
            return null;
        }
    }
    public final boolean clickOnElementsOfWXPIntegrationsPage(String key) {
        try {
            click(WXPIntegrationsPage.getProperty(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    public boolean isConnectButtonVisible(String connectorId) {
        String locator = WXPIntegrationsPage.getProperty("integrationsPageConnectorConnectButton").replace("%CONNECTOR_ID%", connectorId);
        try {
            return verifyElementIsPresent(locator);
        } catch (Exception e) {
            LOGGER.error("Exception in isConnectButtonVisible: " + e.getMessage());
            return false;
        }
    }

    public boolean isConfigureButtonVisible(String connectorId) {
        String locator = WXPIntegrationsPage.getProperty("integrationsPageConnectorConfigureButton").replace("%CONNECTOR_ID%", connectorId);
        try {
            return verifyElementIsPresent(locator);
        } catch (Exception e) {
            LOGGER.error("Exception in isConfigureButtonVisible: " + e.getMessage());
            return false;
        }
    }

    public boolean isDisconnectButtonVisible(String connectorId) {
        String locator = WXPIntegrationsPage.getProperty("integrationsPageConnectorDisconnectButton").replace("%CONNECTOR_ID%", connectorId);
        try {
            return verifyElementIsPresent(locator);
        } catch (Exception e) {
            LOGGER.error("Exception in isDisconnectButtonVisible: " + e.getMessage());
            return false;
        }
    }

    public String getConnectorStatus(String connectorId) {
        String key = WXPIntegrationsPage.getProperty("integrationsPageConnectorStatus").replace("%CONNECTOR_ID%", connectorId);
        try {
            if (verifyElementIsPresent(key)) {
                return verifyTextPresentOnElement(key, "") ? "" : null; // Adjust as per actual text retrieval logic
            }
        } catch (Exception e) {
            LOGGER.error("Exception in getConnectorStatus: " + e.getMessage());
        }
        return null;
    }

    /**
     * Returns the visible action for a connector: "Connect", "Configure", "Disconnect", or status text if no button is visible.
     */
    public String getVisibleConnectorAction(String connectorId) {
        if (isConnectButtonVisible(connectorId)) return "Connect";
        if (isConfigureButtonVisible(connectorId)) return "Configure";
        if (isDisconnectButtonVisible(connectorId)) return "Disconnect";
        String status = getConnectorStatus(connectorId);
        return status != null ? status : "None";

    }
    public final boolean clickOnElementsOfWEXIntegrationPage(String key) {
        try {
            click(WXPIntegrationsPage.getProperty(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    public final boolean waitForElementsOfIntegrationPage(String connectorId) {
        String key = WXPIntegrationsPage.getProperty("integrationsPageConnectorConnectButton").replace("%CONNECTOR_ID%", connectorId);
        try {
            return verifyElementIsVisible(WXPIntegrationsPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * Clicks the action button (Connect, Disconnect) for a connector card based on the action string.
     * Handles the case where the button is not present (e.g.IT admin user)
     * It now reads the actual button text from the UI and acts based on visible text. If the requested
     * action is supplied and present it will be clicked; otherwise the visible action is used with a warning.
     */
    public boolean NoActionButtonShouldVisible(String connectorId, String action) {
        try {
            final String connectLocator = WXPIntegrationsPage.getProperty("integrationsPageConnectorConnectButton").replace("%CONNECTOR_ID%", connectorId);
            final String configureLocator = WXPIntegrationsPage.getProperty("integrationsPageConnectorConfigureButton").replace("%CONNECTOR_ID%", connectorId);
            final String disconnectLocator = WXPIntegrationsPage.getProperty("integrationsPageConnectorDisconnectButton").replace("%CONNECTOR_ID%", connectorId);

            boolean connectVisible = verifyElementIsPresent(connectLocator) && verifyElementIsVisible(connectLocator);
            boolean configureVisible = verifyElementIsPresent(configureLocator) && verifyElementIsVisible(configureLocator);
            boolean disconnectVisible = verifyElementIsPresent(disconnectLocator) && verifyElementIsVisible(disconnectLocator);

            if (!connectVisible && !configureVisible && !disconnectVisible) {
                LOGGER.info("No action button (Connect/Configure/Disconnect) is visible for connector: " + connectorId);
                return true;
            } else {
                LOGGER.warn("At least one action button is visible for connector: " + connectorId);
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred in clickConnectorActionButton for connector: " + connectorId + ", requested action: " + action + ", error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Clicks the connector action based on the visible text in the connector card (no action parameter).
     * - If the visible text is 'Connect': clicks the card's Connect button, waits for the connector modal and
     *   verifies presence of modal Connect/Disconnect actions.
     * - If the visible text is 'Disconnect': clicks Disconnect and waits for the connector to show as disconnected
     *   (Connect button visible again).
     */


    public boolean clickConnectorActionButton(String connectorId) {
        try {
            String textofactionbutton = WXPIntegrationsPage.getProperty("TextofbuttoninConnectorCard").replace("%CONNECTOR_ID%", connectorId);
            LOGGER.info("print locator " + textofactionbutton);

            if (!verifyElementIsPresent(textofactionbutton)) {
                LOGGER.warn("No Connect/Disconnect button present for connector: " + connectorId);
                return false;
            }

            String visibleText = "";
            try {
                visibleText = getTextBy(textofactionbutton);
            } catch (Exception e) {
                LOGGER.warn("Unable to read button text for connector " + connectorId + ": " + e.getMessage());
            }

            if (visibleText.equalsIgnoreCase("Connect")) {
                click(textofactionbutton);
                String titleofopenmodal= getTextOfWXPIntegrationsPage("titleofopenmodal");

                if (titleofopenmodal.equalsIgnoreCase("Power Automate")) {
                    LOGGER.info("Powera automate modal is opened");
                    waitForElementsOfIntegrationPage("Connectbuttoninmodal");
                    clickOnElementsOfWXPIntegrationsPage("Connectbuttoninmodal");
                    waitUntilElementIsVisible(WXPIntegrationsPage.getProperty("connectedstatusofconnector").replace("%CONNECTOR_ID%", connectorId));
                }
            } else if (visibleText.equalsIgnoreCase("Disconnect")) {
                click(textofactionbutton);
                return true;
            } else {
                LOGGER.warn("Unexpected button text '" + visibleText + "' for connector: " + connectorId);
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Exception in clickConnectorActionButton(connectorId) for " + connectorId + ": " + e.getMessage());
            return false;
        }
        return false;
    }

}


