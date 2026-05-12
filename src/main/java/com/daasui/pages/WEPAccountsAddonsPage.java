       // softAssert.assertTrue(productLinkPresent, String.format("Product link is present for %s", addonName));
package com.daasui.pages;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.daasui.constants.CommonVariables;
import com.daasui.constants.WEPAccountsConstants;

public class WEPAccountsAddonsPage extends CommonMethod {
    private final static Logger LOGGER = LogManager.getLogger(WEPAccountsAddonsPage.class);
    private final Properties props;

    public WEPAccountsAddonsPage() throws IOException {
        var reader = new ObjectReader();
        this.props = reader.getObjectRepository("WEPAccountsAddons");
    }

    public final WebElement getAddonsElement(String key) throws Exception {
        return getElement(props.getProperty(key));
    }

    public final boolean verifyAddonsElementIsPresent(String key) throws Exception {
        return verifyElementIsPresent(props.getProperty(key));
    }

    public final void clickAddonsElement(String key) throws Exception {
        click(props.getProperty(key));
    }

    /**
     * Navigates from the addons tab under accounts to the selected addon
     * details page using the id for the addon listing and finding the learn
     * more button.
     */
    public final boolean navigateToAddonDetails(String key) throws Exception {
        // From the addon listing on the addons page get the learn more link
        var button = getAddonsElement(key).findElement(By.tagName("button"));
        if (button == null) {
            LOGGER.error("No button found for key: " + key);
            return false;
        }
        // The learn more button is the last button in the addon listing
        button.click();
        waitForPageLoaded();
        return verifyAddonsElementIsPresent("productDetailsPage");
    }

    public final boolean navigateToAddonsMenu() {
        leftSideMenuVerification();
        try {
            companyView(CommonVariables.ADDONS);
            waitForPageLoaded();
            LOGGER.info("Redirected Add-ons page");
            return true;
        } catch (Exception e) {
            LOGGER.error("Error while navigating to the addons menu: " + e.getMessage());
            return false;
        }
    }

    /*
     * Validates the addon details page for common UI elements and links.
     */
    private void assertAddonDetails(String addonName, String productLinkUrl, String supportLinkUrl,
                                    String documentationLinkUrl,
                                    String privacyPolicyLinkUrl, String learnMoreLinkUrl, String contactSalesLinkUrl) throws Exception {
        SoftAssert softAssert = new SoftAssert();

        // Validate all common links
        boolean productLinkPresent = verifyAddonsElementIsPresent("productLink");
        softAssert.assertTrue(productLinkPresent, String.format("Product link is present for %s", addonName));
        if (productLinkPresent) {
            sleeper(1000);
            getAddonsElement("productLink").click();
            sleeper(1000);
            switchToDifferentTab();
            new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlToBe(productLinkUrl));
            // Product Link should create a new tab, close the tab and go back to the
            // product page
            softAssert.assertTrue(getDriver().getCurrentUrl().equals(productLinkUrl), String.format(
                    "productLinkUrl matches expected URL for %s. Expected: %s, Actual: %s", addonName,
                    productLinkUrl, getDriver().getCurrentUrl()));
            switchBackToPreviousTab();
        }

        boolean supportLinkPresent = verifyAddonsElementIsPresent("supportSiteLink");
        softAssert.assertTrue(supportLinkPresent, String.format("Support link is present for %s", addonName));
        if (supportLinkPresent) {
            getAddonsElement("supportSiteLink").click();
            sleeper(1000);
            switchToDifferentTab();
            waitForPageLoaded();
            // supportSiteLink should create a new tab, close the tab and go back to the
            // product page
            softAssert.assertTrue(getDriver().getCurrentUrl().equals(supportLinkUrl), String.format(
                    "productLinkUrl matches expected URL for %s. Expected: %s, Actual: %s", addonName,
                    supportLinkUrl, getDriver().getCurrentUrl()));
            switchBackToPreviousTab();
            sleeper(1000);
        }

        boolean documentationLinkPresent = verifyAddonsElementIsPresent("documentationLink");
        softAssert.assertTrue(documentationLinkPresent, String.format("Documentation link is present for %s", addonName));
        if (documentationLinkPresent) {
            getAddonsElement("documentationLink").click();
            sleeper(1000);
            switchToDifferentTab();
            waitForPageLoaded();
            // documentationLink should create a new tab, close the tab and go back to the
            // product page
            softAssert.assertTrue(getDriver().getCurrentUrl().equals(documentationLinkUrl), String.format(
                    "productLinkUrl matches expected URL for %s. Expected: %s, Actual: %s", addonName,
                    documentationLinkUrl, getDriver().getCurrentUrl()));
            switchBackToPreviousTab();
            sleeper(1000);
        }

        boolean privacyPolicyLinkPresent = verifyAddonsElementIsPresent("privacyPolicyLink");
        softAssert.assertTrue(privacyPolicyLinkPresent,
                String.format("Privacy Policy link is present for %s", addonName));
        if (privacyPolicyLinkPresent) {
            getAddonsElement("privacyPolicyLink").click();
            sleeper(1000);
            switchToDifferentTab();
            waitForPageLoaded();
            // privacyPolicyLink should create a new tab, close the tab and go back to the
            // product page
            softAssert.assertTrue(getDriver().getCurrentUrl().equals(privacyPolicyLinkUrl), String.format(
                    "productLinkUrl matches expected URL for %s. Expected: %s, Actual: %s", addonName,
                    privacyPolicyLinkUrl, getDriver().getCurrentUrl()));
            switchBackToPreviousTab();
            sleeper(1000);
        }

        boolean learnMoreLinkPresent = verifyAddonsElementIsPresent("learnMoreLink");
        softAssert.assertTrue(learnMoreLinkPresent,
                String.format("Learn More link is present for %s", addonName));
        if (learnMoreLinkPresent) {
            getAddonsElement("learnMoreLink").click();
            sleeper(1000);
            switchToDifferentTab();
            waitForPageLoaded();
            // learnMoreLink should create a new tab, close the tab and go back to the
            // product page
            softAssert.assertTrue(getDriver().getCurrentUrl().equals(learnMoreLinkUrl), String.format(
                    "productLinkUrl matches expected URL for %s. Expected: %s, Actual: %s", addonName,
                    learnMoreLinkUrl, getDriver().getCurrentUrl()));
            switchBackToPreviousTab();
            sleeper(1000);
        }

        var contactSalesLinkPresent = verifyAddonsElementIsPresent("contactSalesButton");
        softAssert.assertTrue(contactSalesLinkPresent,
                String.format("Contact Sales link is present for %s", addonName));
        if (contactSalesLinkPresent) {
            getAddonsElement("contactSalesButton").click();
            sleeper(1000);
            switchToDifferentTab();
            waitForPageLoaded();
            // Contact sales should create a new tab, close the tab and go back to the
            // product page
            softAssert.assertTrue(getDriver().getCurrentUrl().equals(contactSalesLinkUrl), String.format(
                    "Contact Sales link matches expected URL for %s. Expected: %s, Actual: %s", addonName,
                    contactSalesLinkUrl, getDriver().getCurrentUrl()));
            switchBackToPreviousTab();
            sleeper(1000);
        }

        softAssert.assertTrue(verifyAddonsElementIsPresent("productDetailsBodyContent"),
                String.format("Product details body is present for %s", addonName));
        softAssert.assertTrue(verifyAddonsElementIsPresent("productDetailsSideContent"),
                String.format("Product details side content is present for %s", addonName));

        // Validate media photos on page
        var mediaGalleryPresent = verifyAddonsElementIsPresent("productDetailsMediaGallery");
        softAssert.assertTrue(mediaGalleryPresent,
                String.format("Media gallery is present on page for %s", addonName));

        if (mediaGalleryPresent) {
            sleeper(1000);
            var mediaPhotos = getAddonsElement("productDetailsMediaGallery").findElements(By.tagName("img"));
            softAssert.assertTrue(!mediaPhotos.isEmpty(),
                    String.format("Media gallery photos are present on page for %s", addonName));

            for (var mediaPhoto : mediaPhotos) {
                var src = mediaPhoto.getAttribute("src");
                softAssert.assertTrue(!src.isEmpty(),
                        String.format("Media gallery image source defined for %s", addonName));
                mediaPhoto.click();
                sleeper(1000);
                softAssert.assertTrue(verifyAddonsElementIsPresent("productDetailsMediaGalleryModal"),
                        String.format("Media gallery modal is present on page for %s", addonName));
                softAssert.assertTrue(verifyAddonsElementIsPresent("productDetailsMediaGalleryModalImg"),
                        String.format("Media gallery modal image is present on page for %s", addonName));
                var modalCurrentImgSelected = getAddonsElement("productDetailsMediaGalleryModalImg");
                softAssert.assertTrue(src.equals(modalCurrentImgSelected.getAttribute("src")),
                        String.format("Selected image in media gallery matches image displayed in media gallery modal for %s",
                                addonName));
                getAddonsElement("closeModalButton").click();
                sleeper(1000);
            }
        }
        softAssert.assertAll();
    }

    public final void assertAnywareAddonDetails() throws Exception {
        assertAddonDetails("Anyware", WEPAccountsConstants.AnywareProductLink,
                WEPAccountsConstants.AnywareSupportLink,
                WEPAccountsConstants.AnywareDocumentationLink,
                WEPAccountsConstants.PrivacyPolicyLink,
                WEPAccountsConstants.AnywareLearnMoreLink,
                WEPAccountsConstants.AnywareContactSalesLink);
        // Verify all unique things for anyware addon below
        SoftAssert softAssert = new SoftAssert();
        var purchaseBtnPresent = verifyAddonsElementIsPresent("purchaseButton");
        softAssert.assertTrue(purchaseBtnPresent,
                "Purchase button is present for Anyware addon");
        if (purchaseBtnPresent) {
            var purchaseBtn = getAddonsElement("purchaseButton");
            purchaseBtn.click();
            waitForPageLoaded();

            // Purchase button should create a new tab, close the tab and go back to the
            // product page
            switchToDifferentTab();
            softAssert.assertTrue(getDriver().getCurrentUrl().equals(WEPAccountsConstants.AnywarePurchaseLink),
                    "Purchase button redirects to the correct URL");
            switchBackToPreviousTab();
        }
        softAssert.assertAll();
    }

    public final void assertVyoptaAddonDetails() throws Exception {
        assertAddonDetails("Vyopta", WEPAccountsConstants.VyoptaProductLink,
                WEPAccountsConstants.VyoptaSupportLink,
                WEPAccountsConstants.VyoptaDocumentationLink,
                WEPAccountsConstants.PrivacyPolicyLink,
                WEPAccountsConstants.VyoptaLearnMoreLink,
                WEPAccountsConstants.VyoptaContactSalesLink);
        // Verify all unique things for vyopta addon below
        SoftAssert softAssert = new SoftAssert();

        var connectBtnPresent = verifyAddonsElementIsPresent("connectButton");
        softAssert.assertTrue(connectBtnPresent,
                "Connect button is present for Vyopta addon");
        softAssert.assertAll();
    }

    public final void assertWPTAddonDetails() throws Exception {
        assertAddonDetails("WPT", WEPAccountsConstants.WPTProductLink,
                WEPAccountsConstants.WPTSupportLink,
                WEPAccountsConstants.WPTDocumentationLink,
                WEPAccountsConstants.PrivacyPolicyLink,
                WEPAccountsConstants.WPTLearnMoreLink,
                WEPAccountsConstants.WPTContactSalesLink);
        // Verify all unique things for WPT addon below
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertAll();
    }

    /**
     * Checks if the Vyopta modal content is present and visible. Does not open
     * the modal. That must be done by clicking the connect button. Does not
     * validate full flow. Only checks if the tenant is able to see the connect
     * button.
     *
     * @throws Exception
     */
    public final void assertVyoptaModal() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(verifyAddonsElementIsPresent("vyoptaModalContent"),
                "Vyopta modal content is present and visible");
        // When the modal appears on-screen it will call an API and determine if
        // the tenant can connect or not. If they can connect, the first button
        // will be the connect button and the second will be a cancel button.
        // If they cannot connect, it will be an OK button to close out of the modal.
        var modalButtons = getAddonsElement("vyoptaModalFooter").findElements(By.tagName("button"));
        softAssert.assertTrue(!modalButtons.isEmpty(),
                "Vyopta modal buttons are present and visible");
        softAssert.assertTrue(modalButtons.size() == 2,
                "Vyopta modal has two buttons indicating organization found state");

        // Close out of the modal as to not block the rest of the test
        if (modalButtons.size() == 2) {
            var cancelBtn = modalButtons.get(1);
            cancelBtn.click();
        } else {
            var okBtn = modalButtons.get(0);
            okBtn.click();
        }
        softAssert.assertAll();
    }
}
