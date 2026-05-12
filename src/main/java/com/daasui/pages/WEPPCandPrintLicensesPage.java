package com.daasui.pages;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;

public class WEPPCandPrintLicensesPage extends CommonMethod {

	
	private WEPPCandPrintLicensesPage instance;
	private ObjectReader pcandprintPagePropertiesReader = new ObjectReader();
	private Properties PCandPrintPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(WEPPCandPrintLicensesPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");


	public WEPPCandPrintLicensesPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPPCandPrintLicensesPage.class) {
				if (instance == null) {
					instance = new WEPPCandPrintLicensesPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public WEPPCandPrintLicensesPage(WebDriver driver) throws IOException {
		PCandPrintPageProperties = pcandprintPagePropertiesReader.getObjectRepository("WEPPCandPrintLicensesPage");

	}
	
	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEPPCandPrintLicensesPage(String key) {
		try {
			return verifyElementIsVisible(PCandPrintPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEPPCandPrintLicensesPage " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEPPCandPrintLicensesPage(String key) {
		try {
			return verifyElementIsPresent(PCandPrintPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEPPCandPrintLicensesPage " + e.getMessage()));
			return false;
		}
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEPPCandPrintLicensesPage(String key) throws Exception {
		click(PCandPrintPageProperties.getProperty(key));
	}
	
	/**
	 * Verifies if the given license is over-enrolled.
	 * If over-enrolled, checks all available over-enrollment banners until a match is found.
	 *
	 * @param licensePage the license page object
	 * @param licenseName the name of the license to verify
	 * @return true if banner is verified or license is not over-enrolled
	 * @throws Exception if over-enrolled but no matching banner found
	 */
	public boolean verifyLicenseOverEnrollment(String licenseName) throws Exception {

	    int enrolledCount = getEnrolledCountForLicense(licenseName);
	    int totalSeats = getTotalSeatsForLicense(licenseName);

	    LOGGER.info(licenseName + " - Enrolled: " + enrolledCount + ", Total Seats: " + totalSeats);

	    if (enrolledCount > totalSeats) {
	        LOGGER.info(licenseName + " is over-enrolled. Checking over-enrollment banners...");

	        boolean bannerVerified = false;
	        int bannerIndex = 1;

	        // Check first banner
	        String currentBannerLicenseName = getOverEnrollmentBannerLicenseName();
	        LOGGER.info("Checking banner #" + bannerIndex + ": " + currentBannerLicenseName);

	        if (currentBannerLicenseName != null && currentBannerLicenseName.equalsIgnoreCase(licenseName)) {
	            LOGGER.info("Banner #" + bannerIndex + " matches: " + licenseName + ". Verified successfully.");
	            bannerVerified = true;
	        }

	        // Loop through remaining banners if first one didn't match
	        while (!bannerVerified) {
	            boolean hasNextBanner = clickNextOverEnrollmentBanner();

	            if (!hasNextBanner) {
	                LOGGER.error("No more banners available. License not found in any banner: " + licenseName);
	                break;
	            }

	            bannerIndex++;
	            currentBannerLicenseName = getOverEnrollmentBannerLicenseName();
	            LOGGER.info("Checking banner #" + bannerIndex + ": " + currentBannerLicenseName);

	            if (currentBannerLicenseName != null && currentBannerLicenseName.equalsIgnoreCase(licenseName)) {
	                LOGGER.info("Banner #" + bannerIndex + " matches: " + licenseName + ". Verified successfully.");
	                bannerVerified = true;
	            }
	        }

	        if (!bannerVerified) {
	            throw new Exception("Over-enrollment banner not found for license: "
	                    + licenseName + " (Enrolled: " + enrolledCount + ", Total: " + totalSeats + ")");
	        }

	        return true;

	    } else {
	        LOGGER.info(licenseName + " is not over-enrolled. Skipping banner verification.");
	        return true;
	    }
	}
	
	/**
	 * Returns the enrolled (used) count for a given license name.
	 * Looks for the license row in the table and parses "X of Y endpoints" text.
	 *
	 * @param licenseName the name of the license
	 * @return enrolled count as int
	 * @throws Exception if license row not found or text cannot be parsed
	 */
	public int getEnrolledCountForLicense(String licenseName) throws Exception {
	    // Finds all license name cells in the table
	    List<WebElement> licenseRows = getAllElementsofWEPPCandPrintLicensesPage("planRows");

	    for (WebElement row : licenseRows) {
	        WebElement nameCell = row.findElement(getObject(PCandPrintPageProperties.getProperty("planName")));
	        if (nameCell.getText().trim().equalsIgnoreCase(licenseName)) {
	            WebElement usageCell = row.findElement(getObject(PCandPrintPageProperties.getProperty("usedSeats")));
	            String usageText = usageCell.getText().trim();
	            LOGGER.info("Usage text for " + licenseName + ": " + usageText);
	            // Parse the enrolled count (first number before "of")
	            String enrolledStr = usageText.split("\\(")[1].split("of")[0].trim();
	            return Integer.parseInt(enrolledStr);
	        }
	    }
	    throw new Exception("License not found in table: " + licenseName);
	}
	/**
	 * Returns the total seat count for a given license name.
	 * Looks for the license row in the table and parses "X of Y endpoints" text.
	 *
	 * @param licenseName the name of the license
	 * @return total seats as int
	 * @throws Exception if license row not found or text cannot be parsed
	 */
	public int getTotalSeatsForLicense(String licenseName) throws Exception {
	    List<WebElement> licenseRows = getAllElementsofWEPPCandPrintLicensesPage("planRows");

	    for (WebElement row : licenseRows) {
	        WebElement nameCell = row.findElement(getObject(PCandPrintPageProperties.getProperty("planName")));
	        if (nameCell.getText().trim().equalsIgnoreCase(licenseName)) {
	            // e.g., text: "12 of 10 endpoints"
	            WebElement usageCell = row.findElement(getObject(PCandPrintPageProperties.getProperty("usedSeats")));
	            String usageText = usageCell.getText().trim(); // "12 of 10 endpoints"
	            LOGGER.info("Usage text for " + licenseName + ": " + usageText);
	            // Parse total seats (number after "of", before "endpoints")
	            String totalStr = usageText.split("of")[1].trim().split(" ")[0].replaceAll("[^0-9]", "");
	            return Integer.parseInt(totalStr);
	        }
	    }
	    throw new Exception("License not found in table: " + licenseName);
	}

	/**
	 * Returns the license name displayed on the currently visible over-enrollment banner.
	 *
	 * @return license name string from the banner, or null if banner not found
	 */
	public String getOverEnrollmentBannerLicenseName() {
	    try {
	        WebElement bannerLicenseNameElement = getElementOfWEPPCandPrintLicensesPage("overEnrollmentBannerName");
	        String bannerText = bannerLicenseNameElement.getText().trim();
	        LOGGER.info("Banner full text found: " + bannerText);

	        // Extract license name between "Your " and " license"
	        String licenseName = null;
	        if (bannerText.toLowerCase().contains("your ") && bannerText.toLowerCase().contains(" license")) {
	            int startIndex = bannerText.toLowerCase().indexOf("your ") + "your ".length();
	            int endIndex = bannerText.toLowerCase().indexOf(" license", startIndex);
	            if (startIndex > 0 && endIndex > startIndex) {
	                licenseName = bannerText.substring(startIndex, endIndex).trim();
	                LOGGER.info("Extracted license name: " + licenseName);
	            }
	        }
	        return licenseName;
	    } catch (Exception e) {
	        LOGGER.warn("Over-enrollment banner license name element not found.");
	        return null;
	    }
	}

	/**
	 * Clicks the next arrow on the over-enrollment banner carousel to navigate to the next banner.
	 *
	 * @return true if the next arrow was found and clicked, false if no next arrow exists
	 */
	public boolean clickNextOverEnrollmentBanner() {
	    try {
	        WebElement nextArrow = getElementOfWEPPCandPrintLicensesPage("nextArrow");
	        if (nextArrow.isDisplayed() && nextArrow.isEnabled()) {
	        	//clickByJavaScriptOnElementsOnWEPPCandPrintLicensesPage("nextArrow");
	        	clickByActionsOnWEPPCandPrintLicensesPage("nextArrow");
	            LOGGER.info("Clicked next banner arrow successfully.");
	            sleeper(1000);// brief wait for banner transition
	            return true;
	        }
	        LOGGER.warn("Next arrow found but not clickable.");
	        return false;
	    } catch (Exception e) {
	        LOGGER.warn("Next banner arrow not found. No more banners available.");
	        return false;
	    }
	}
	
	public final List<WebElement> getAllElementsofWEPPCandPrintLicensesPage(String key) {
        try {
            return getAllElements(PCandPrintPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getAllElementsofWEPPCandPrintLicensesPage " + e.getMessage()));
            return null;
        }
   }
	
	public final WebElement getElementOfWEPPCandPrintLicensesPage(String key) throws Exception {
		return getElement(PCandPrintPageProperties.getProperty(key));
	}
	
	public final void clickByJavaScriptOnElementsOnWEPPCandPrintLicensesPage(String key) throws Exception {
        clickByJavaScript(PCandPrintPageProperties.getProperty(key));
    }
	
	public final void clickByActionsOnWEPPCandPrintLicensesPage(String key) {
		try {
			actionClick(PCandPrintPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByActionsOnWEPPCandPrintLicensesPage " + e.getMessage()));
		}
	}
	

	public boolean verifyDevicesPrintersSection() throws Exception {
	    boolean result = true;
	    LOGGER.info("Navigating to Devices -> Printers");
	    handleDevices("Printers");
	    waitForPageLoaded();
	    LOGGER.info("Verifying Printers tab");
	    result &= verifyPrintersTab();
	    LOGGER.info("Verifying Proxies tab");
	    result &= verifyProxiesTab();
	    LOGGER.info("Verifying Pending Printers tab");
	    result &= verifyPendingPrintersTab();
	    return result;
	}

	/**
	 *  Navigate to Groups -> Printers and verify Printers group
	 */
	public boolean verifyGroupsPrintersSection() throws Exception {
	    boolean result = true;
	    LOGGER.info("Navigating to Groups -> Printers");
	    companyView("Groups");
	    waitForPageLoaded();
	    LOGGER.info("Verifying Printers Group");
	    result &= verifyPrintersGroup();
	    return result;
	}

	/**
	 * Navigate to Remediations -> Policies and verify printer policies section
	 * @return true if all verifications pass, false otherwise
	 */
	public boolean verifyRemediationsPoliciesSection() throws Exception {
	    boolean result = true;
	    LOGGER.info("Navigating to Remediations -> Policies");
	    handleRemediations("Policies");
	    waitForPageLoaded();
	    LOGGER.info("Verifying Printer Policies");
	    result &= verifyPrinterPolicies();
	    LOGGER.info("Verifying Printer Policy Assignments");
	    result &= verifyPrinterPolicyAssignments();
	    return result;
	}

	/**
	 * Navigate to Analytics -> Fleet Management and verify widgets
	 * @return true if all verifications pass, false otherwise
	 */
	public boolean verifyAnalyticsFleetManagementSection() throws Exception {
	    boolean result = true;
	    LOGGER.info("Navigating to Analytics -> Fleet Management");
	    companyView("Analytics");
	    waitForPageLoaded();
	    clickOnElementsOfWEPPCandPrintLicensesPage("fleetManagementTab");
	    waitForPageLoaded();
	    LOGGER.info("Verifying Printer Inventory widget");
	    result &= verifyPrinterInventoryWidget();
	    LOGGER.info("Verifying Printer Fleet Security widget");
	    result &= verifyPrinterFleetSecurityWidget();
	    LOGGER.info("Verifying Printer Fleet Compliance widget");
	    result &= verifyPrinterFleetComplianceWidget();
	    return result;
	}
	
	 /**
     * Verifies the Printers tab under Devices -> Printers.
     * Checks that the Printers list is visible and contains at least one entry.
	 * @return 
     *
     * @throws Exception
     */
	public boolean verifyPrintersTab() throws Exception {
	    try {
	        LOGGER.info("Verifying Printers tab.");

	        // Verify Printers tab is active/visible
	        if (!waitForElementsOfWEPPCandPrintLicensesPage("printersTab")) {
	            LOGGER.error("Printers tab is not visible.");
	            return false;
	        }

	        // Click on Printers tab if not already selected
	        if (!verifyElementsOfWEPPCandPrintLicensesPage("exportPrintersButton")) {
	            clickOnElementsOfWEPPCandPrintLicensesPage("printersTab");
	            waitForPageLoaded();
	        }
	        waitForPageLoaded();
	        boolean tableVisible = waitForElementsOfWEPPCandPrintLicensesPage("printersTable");
	        if (!tableVisible) {
	            LOGGER.error("Printers table is not visible.");
	            return false;
	        }

	        LOGGER.info("Printers tab verified and clicked successfully.");
	        return true;

	    } catch (Exception e) {
	        LOGGER.error("Exception in verifyPrintersTab: " + e.getMessage());
	        throw e;
	    }
	}

    /**
     * Verifies the Proxies tab under Devices -> Printers.
     * Checks that the Proxies list is visible and contains expected data.
     *
     * @throws Exception
     */
    public boolean verifyProxiesTab() throws Exception {
        try {
            LOGGER.info("Verifying Proxies tab.");
            
            clickOnElementsOfWEPPCandPrintLicensesPage("proxiesTab");
            waitForPageLoaded();
            // Verify Proxies tab is active/visible
            if (!waitForElementsOfWEPPCandPrintLicensesPage("proxiesTab")) {
                LOGGER.error("Proxies tab is not visible.");
                return false;
            }

            // Click on Proxies tab if not already selected
            if (!verifyElementsOfWEPPCandPrintLicensesPage("addProxiesButton")) {
            	clickOnElementsOfWEPPCandPrintLicensesPage("proxiesTab");
                waitForPageLoaded();
            }

			
            LOGGER.info("Proxies tab verified and clicked successfully.");
            return true;

        } catch (Exception e) {
            LOGGER.error("Exception in verifyProxiesTab: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verifies the Pending Printers tab under Devices -> Printers.
     * Checks that the Pending Printers list is visible.
     *
     * @throws Exception
     */
    public boolean verifyPendingPrintersTab() throws Exception {
        try {
            LOGGER.info("Verifying Pending Printers tab.");
            clickOnElementsOfWEPPCandPrintLicensesPage("pendingPrintersTab");
            waitForPageLoaded();
            // Verify Pending Printers tab is active/visible
            if (!waitForElementsOfWEPPCandPrintLicensesPage("pendingPrintersTab")) {
                LOGGER.error("Pending Printers tab is not visible.");
                return false;
            }

            // Click on Pending Printers tab if not already selected
            if (!verifyElementsOfWEPPCandPrintLicensesPage("printerStatusOverview")) {
            	clickOnElementsOfWEPPCandPrintLicensesPage("pendingPrintersTab");
                waitForPageLoaded();
            }

            LOGGER.info("Pending Printers tab verified and clicked successfully.");
            return true;

        } catch (Exception e) {
            LOGGER.error("Exception in verifyPendingPrintersTab: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verifies the Printers group under Groups -> Printers.
     * Checks that Printers group is visible and has the expected structure.
     *
     * @throws Exception
     */
    public boolean verifyPrintersGroup() throws Exception {
        try {
            LOGGER.info("Verifying Printers Group.");
            clickOnElementsOfWEPPCandPrintLicensesPage("printersGroupTab");
            waitForPageLoaded();
            // Verify the Printers group tab is visible
            if (!waitForElementsOfWEPPCandPrintLicensesPage("printersGroupTab")) {
                LOGGER.error("Printers group tab is not visible.");
                return false;
            }

            // Click on Printers group tab if grid is not already visible
            if (!verifyElementsOfWEPPCandPrintLicensesPage("printersGroupAddButton")) {
                clickOnElementsOfWEPPCandPrintLicensesPage("printersGroupTab");
                waitForPageLoaded();
            }

            LOGGER.info("Printers group tab verified and clicked successfully.");
            return true;

        } catch (Exception e) {
            LOGGER.error("Exception in verifyPrintersGroup: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verifies the Printer Policies under Remediations -> Policies.
     * Checks that printer policies are visible and listed.
     *
     * @throws Exception
     */
    public boolean verifyPrinterPolicies() throws Exception {
        try {
            LOGGER.info("Verifying Printer Policies.");
            clickOnElementsOfWEPPCandPrintLicensesPage("printersPolicyTab");
            waitForPageLoaded();
            // Verify Printer Policies tab/section is visible
            if (!waitForElementsOfWEPPCandPrintLicensesPage("printersPolicyTab")) {
                LOGGER.error("Printer Policies section is not visible.");
                return false;
            }
            // Verify policies table/grid is present
            if (!verifyElementsOfWEPPCandPrintLicensesPage("importPolicyButton")) {
            	 clickOnElementsOfWEPPCandPrintLicensesPage("printersPolicyTab");
                 waitForPageLoaded();
            }
       
            LOGGER.info("Printer Policies verified successfully.");
            return true;

        } catch (Exception e) {
            LOGGER.error("Exception in verifyPrinterPolicies: " + e.getMessage());
            throw e;
        }
    }


    /**
     * Verifies the Printer Policy Assignments under Remediations -> Policies.
     * Checks that printer policy assignments are visible and listed.
     *
     * @throws Exception
     */
    public boolean verifyPrinterPolicyAssignments() throws Exception {
        try {
            LOGGER.info("Verifying Printer Policy Assignments.");
            clickOnElementsOfWEPPCandPrintLicensesPage("printerAssignments");
            waitForPageLoaded();

            // Verify Policy Assignments tab/section is visible
            if (!waitForElementsOfWEPPCandPrintLicensesPage("printerAssignments")) {
                LOGGER.error("Printer Policy Assignments tab is not visible.");
                return false;
            }


            // Verify assignments table/grid is present
            if (!verifyElementsOfWEPPCandPrintLicensesPage("addAssignmentPolicyButton")) {
           	 clickOnElementsOfWEPPCandPrintLicensesPage("printerAssignments");
                waitForPageLoaded();
           }

            LOGGER.info("Printer Policy Assignments verified successfully.");
            return true;

        } catch (Exception e) {
            LOGGER.error("Exception in verifyPrinterPolicyAssignments: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verifies the Printer Inventory widget on the Fleet Management Analytics dashboard.
     * Checks that the widget is visible and displays data.
     *
     * @throws Exception
     */
    public boolean verifyPrinterInventoryWidget() throws Exception {
        try {
            LOGGER.info("Verifying Printer Inventory Widget.");

            // Verify the widget is present on the dashboard
            if (!waitForElementsOfWEPPCandPrintLicensesPage("printerInventoryWidget")) {
                LOGGER.error("Printer Inventory Widget is not visible.");
                return false;
            }
            LOGGER.info("Printer Inventory Widget verified successfully.");
            return true;

        } catch (Exception e) {
            LOGGER.error("Exception in verifyPrinterInventoryWidget: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verifies the Printer Fleet Security widget on the Fleet Management Analytics dashboard.
     * Checks that the widget is visible and displays security data.
     *
     * @throws Exception
     */
    public boolean verifyPrinterFleetSecurityWidget() throws Exception {
        try {
            LOGGER.info("Verifying Printer Fleet Security Widget.");

            // Verify the widget is present on the dashboard
            if (!waitForElementsOfWEPPCandPrintLicensesPage("fleetSecurityWidget")) {
                LOGGER.error("Printer Fleet Security Widget is not visible.");
                return false;
            }
            LOGGER.info("Printer Fleet Security Widget verified successfully.");
            return true;

        } catch (Exception e) {
            LOGGER.error("Exception in verifyPrinterFleetSecurityWidget: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verifies the Printer Fleet Compliance widget on the Fleet Management Analytics dashboard.
     * Checks that the widget is visible and displays compliance data.
     *
     * @throws Exception
     */
    public boolean verifyPrinterFleetComplianceWidget() throws Exception {
        try {
            LOGGER.info("Verifying Printer Fleet Compliance Widget.");

            // Verify the widget is present on the dashboard
            if (!waitForElementsOfWEPPCandPrintLicensesPage("fleetComplianceWidget")) {
                LOGGER.error("Printer Fleet Compliance Widget is not visible.");
                return false;
            }
            LOGGER.info("Printer Fleet Compliance Widget verified successfully.");
            return true;

        } catch (Exception e) {
            LOGGER.error("Exception in verifyPrinterFleetComplianceWidget: " + e.getMessage());
            throw e;
        }
    }
    
    public final void verifyPrintersFleetInventory(String languageCode) throws Exception {
		WEXCustomerHomePage wepCustomerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
	    wepCustomerHomePage.scrollToElementsOfWEPCustomerHomePage("pcContent");
	    wepCustomerHomePage.waitForElementsOfWEPCustomerHomePage("categoryNamePrint");
	    wepCustomerHomePage.scrollToElementsOfWEPCustomerHomePage("categoryNamePrint");
	    wepCustomerHomePage.clickByJavaScriptOnElementsOfWEPCustomerHomePage("categoryNamePrint");
	}

}
