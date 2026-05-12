package com.daasui.pages;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WEXCustomerHomePage extends CommonMethod{
	
	private WEXCustomerHomePage instance;
	private ObjectReader WEXCustomerHomePropertiesReader = new ObjectReader();
	private Properties WEXCustomerHomeProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	
	public WEXCustomerHomePage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXDashboardPage.class) {
				if (instance == null) {
					instance = new WEXCustomerHomePage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public WEXCustomerHomePage(WebDriver driver) throws IOException {
		WEXCustomerHomeProperties = WEXCustomerHomePropertiesReader.getObjectRepository("WEXCustomerHomePage");
	}

	/**
	 * This is a method to get text of an element on user details page
	 *
	 * @param key - locator of the element
	 * @return - string value of text on the element
	 * @throws Exception
	 */
	public final String getTextOfWEXScore(String key) throws Exception {
		return getTextBy(WEXCustomerHomeProperties.getProperty(key));
	}
	
	public final boolean verifyWexScore(String key) throws Exception {
		boolean flag = false;
		String title = getTextBy(WEXCustomerHomeProperties.getProperty(key));
		String string = WEXCustomerHomeProperties.getProperty("wexScoreString");
		try {
 
			if (string.equals(title)) {
				LOGGER.info("Wex Score widget title is a expected that is =" + string);
				flag = true;
 
			}
 
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyWexScore " + e.getMessage()));
		}
		return flag;
	}
	
	public final boolean verifyWexScoreOverTime(String key) throws Exception {
		boolean flag = false;
		String title = getTextBy(WEXCustomerHomeProperties.getProperty(key));
		String string = WEXCustomerHomeProperties.getProperty("wexScoreOverTimeString");
		try {
 
			if (string.equals(title)) {
				LOGGER.info("Wex Score over time widget title is a expected that is =" + string);
				flag = true;
 
			}
 
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyWexScoreOverTime " + e.getMessage()));
		}
		return flag;
	}
	
	public final boolean verifyWEXRecommendedAction(String key) throws Exception {
		boolean flag = false;
		String title = getTextBy(WEXCustomerHomeProperties.getProperty(key));
		String string = WEXCustomerHomeProperties.getProperty("recommendedActionsString");
		try {
 
			if (string.equals(title)) {
				LOGGER.info("Wex recommended action widget title is a expected that is =" + string);
				flag = true;
 
			}
 
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyWEXRecommendedAction " + e.getMessage()));
		}
		return flag;
	}
	
	
	public final boolean verifyFleetInventory(String key) throws Exception {
		boolean flag = false;
		String title = getTextBy(WEXCustomerHomeProperties.getProperty(key));
		String string = WEXCustomerHomeProperties.getProperty("fleetInventoryString");
		try {
 
			if (string.equals(title)) {
				LOGGER.info("Wex fleet inventory widget title is a expected that is =" + string);
				flag = true;
 
			}
 
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFleetInventory " + e.getMessage()));
		}
		return flag;
	}
	
	public final boolean verifyAppsWithMostCrashes(String key) throws Exception {
		boolean flag = false;
		String title = getTextBy(WEXCustomerHomeProperties.getProperty(key));
		String string = WEXCustomerHomeProperties.getProperty("appsWithMostCrashesString");
		try {
 
			if (string.equals(title)) {
				LOGGER.info("Wex apps With Most Crashes widget title is a expected that is =" + string);
				flag = true;
 
			}
 
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyAppsWithMostCrashes " + e.getMessage()));
		}
		return flag;
	}
	
	public final boolean verifySentimentPersona(String key) throws Exception {
		boolean flag = false;
		String title = getTextBy(WEXCustomerHomeProperties.getProperty(key));
		String string = WEXCustomerHomeProperties.getProperty("sentimentPersonaString");
		try {
 
			if (string.equals(title)) {
				LOGGER.info("Wex sentiment Persona widget title is a expected that is =" + string);
				flag = true;
 
			}
 
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySentimentPersona " + e.getMessage()));
		}
		return flag;
	}
	
	/**
	 * This method designed to wait for element of Customer Home page.
	 * 
	 * @param WebElement
	 */
	public final boolean waitForElementsOfWEPCustomerHomePage(String key) throws Exception {
		return verifyElementIsVisible(WEXCustomerHomeProperties.getProperty(key));
	}

	/**
	 * This method designed to scroll till element is visible
	 *
	 * @param WebElement
	 */
	public void scrollToElementsOfWEPCustomerHomePage(String key) throws Exception {
		scrollTillView(WEXCustomerHomeProperties.getProperty(key));
	}

	/**
	 * This method designed to get the  Text of WebElement from web page
	 * @param key
	 */
	public final String getTextOfWEPCustomerHomePage(String key) throws Exception {
		return getTextBy(WEXCustomerHomeProperties.getProperty(key));
	}

	public final void clickOnElementsOfWEPCustomerHomePage(String key) throws Exception {
		click(WEXCustomerHomeProperties.getProperty(key));
	}

	/**
	 * This method used for click on left side of total count.
	 * @param key:it is center point from that we start moving.
	 * @param left:It is value for moving left from center point
	 * @param right:It is value for moving Right from center point
	 * @throws Exception
	 */
	public final void mouseHoverbyoffsettClick(String key,int left,int right) throws Exception {
		mouseHoverbyoffsetClick(WEXCustomerHomeProperties.getProperty(key),left,right);
	}

	/**
	 * This is a method to return elements of device list page
	 *
	 * @param key - Locator of element
	 * @return WebElement list that are present in that locator
	 */
	public final List<WebElement> getElementsOfWEPCustomerHomePage(String key) throws Exception {
		return getElementsTillAllElementsPresent(WEXCustomerHomeProperties.getProperty(key));
	}

	/**
	 * Validates if the given string is a numeric value within the specified range.
	 * Handles null values gracefully OR non-digit values and logs a message.
	 *
	 * @param value        the string value to validate (can be null)
	 * @param minRange     the minimum acceptable value
	 * @param maxRange     the maximum acceptable value
	 * @param valueName    the name of the value (for logging purposes)
	 * @return true if the value is valid or null (logs a warning for null), false otherwise
	 */
	public static boolean validateNumericValueWithRange(String value, int minRange, int maxRange, String valueName) {
		if (value == null || !value.matches("\\d+")) {
			LOGGER.warn(valueName + " has no value. Marked as 'No value for given tab'.");
			return false;
		}

		try {
			int numericValue = Integer.parseInt(value);

			if (numericValue >= minRange && numericValue <= maxRange) {
				LOGGER.info(valueName + " is valid and within the range: " + numericValue);
				return true;
			} else {
				LOGGER.error(valueName + " is out of range (" + minRange + "-" + maxRange + "): " + numericValue);
				return false;
			}
		} catch (NumberFormatException e) {
			LOGGER.error(valueName + " is not a numeric value: " + value, e);
			return false;
		}
	}

	/**
	 * Click element using java script
	 */
	public final void clickByJavaScriptOnElementsOfWEPCustomerHomePage(String key) throws Exception {
		clickByJavaScript(WEXCustomerHomeProperties.getProperty(key));
	}
	/**
	 * This method is to mouse hover and click through javascript for a webelement
	 * @param key - webelement
	 * @throws Exception
	 */
	public final void mouseHoverclickOfCustomerHomePage(WebElement key) throws Exception {
		mouseHoverclick(key);
	}
	/**
	 * Mouse Hover for reports
	 * @param key
	 * @throws Exception
	 */
	public final void mouseHoverOnElementsOfWEPCustomerHomePage(String key) throws Exception {
		mouseHover(WEXCustomerHomeProperties.getProperty(key));
	}

	public final void waitUntilElementIsInvisibleOfWEPCustomerHomePage(String key) {
		try {
			verifyElementIsinvisibile(WEXCustomerHomeProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfWEPCustomerHomePage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to verify if the element is present
	 *
	 * @param key - Locator of element
	 * @return 
	 */

	public final boolean verifyElementsOfWEPCustomerHomePage(String key) {
		try {
			return verifyElementIsPresent(WEXCustomerHomeProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error("Exception occurred in method verifyElementsOfWEPCustomerHomePage {}", e.getMessage());
		}
		return false;
	}

	// Method to validate time zone
	public static void validateTimeZone(String timeZone, Pattern pattern, String label) {
		Matcher matcher = pattern.matcher(timeZone);
		if (matcher.matches()) {
			LOGGER.info("Timezone format is valid: " + label + " - " + timeZone);
		} else {
			LOGGER.info("Timezone format is invalid: " + label + " - " + timeZone);
		}
	}

	public boolean verifyWEPChartDashboardNavigation(String expandButton,String expmgmtTab)
	{
		boolean flag = false;
		try {
			if (waitForElementsOfWEPCustomerHomePage(expandButton)) {
				clickOnElementsOfWEPCustomerHomePage(expandButton);
				waitUntilElementIsInvisibleOfWEPCustomerHomePage("reactSkelaton");
				if (waitForElementsOfWEPCustomerHomePage(expmgmtTab)) {
					flag = true;
					LOGGER.info("Navigated to Experience Management page "+expmgmtTab+" from Dashboard successfully.");
				} else {
					LOGGER.error("Experience Management page did not load successfully when navigated from Dashboard.");
				}
			} else {
				LOGGER.error("Dex Charts on Dashboard page did not load successfully.");
				flag = false;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param Text - The text to be entered
	 */
	public final void enterTextForUserListPage(String key, String Text) {
		try {
			enterText(WEXCustomerHomeProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error("Exception occurred in method enterTextForUserListPage " + e.getMessage());
		}
	}

	/**
	 * This method returns the manufacturer from hashmap based on the device count
	 * @param fleetInventoriesListElements list of widget elements
	 * @return hashmap containing manufacturer with the count
	 * @throws Exception
	 */
	public HashMap<String, Integer> getManufacturerBasedDeviceCount() throws Exception {
		int totalCountOfDevices = Integer.parseInt(getTextOfWEPCustomerHomePage("valueExtractor").split("PCs")[0]);
		int latestCountOfRemainPcs = totalCountOfDevices;
		List<WebElement> refreshedList = getElementsOfWEPCustomerHomePage("fleetInventoryWidget");
		HashMap<String, Integer> differentManufacturerPcs = new HashMap<>();
		for (int i = 0; i < refreshedList.size(); i++) {
			try {
				refreshedList = getElementsOfWEPCustomerHomePage("fleetInventoryWidget");
		        waitForElementsOfWEPCustomerHomePage("valueExtractor");
				totalCountOfDevices = Integer.parseInt(getTextOfWEPCustomerHomePage("valueExtractor").split("PCs")[0]);
				LOGGER.info("Total count of PCs before clicking manufacturer: " + totalCountOfDevices);
				if (!refreshedList.get(i).getText().isEmpty() && !refreshedList.get(i).getText().equals("PCs by Manufacturer")) {
					try {
						mouseHoverclickOfCustomerHomePage(refreshedList.get(i));
					} catch (Exception e) {
						LOGGER.info("Exception handelled while clicking on current manufacturer element");
					}
					LOGGER.info("Clicked on current element");
					latestCountOfRemainPcs = Integer.parseInt(getTextOfWEPCustomerHomePage("valueExtractor").split("PCs")[0]);
					LOGGER.info("Latest count of remaining PCs after clicking manufacturer: " + latestCountOfRemainPcs);
					refreshedList = getElementsOfWEPCustomerHomePage("fleetInventoryWidget");
					String manName = refreshedList.get(i).getText();
					System.out.println("Manufacturer Name: " + manName);
					int currentMfgCount = totalCountOfDevices - latestCountOfRemainPcs;
					System.out.println("Manufacturer Count: " + currentMfgCount);
					differentManufacturerPcs.put(manName,currentMfgCount);
				}
			}catch (Exception e) {
				LOGGER.error("Exception handelled in method getManufacturerBasedDeviceCount");
	        }
		}
		return differentManufacturerPcs;
	}

	/**
	 * This method returns if the count is matching.
	 * @param locator - the count in UI
	 * @param finalCountToVerify - count to verify against
	 * @return true if count is matching, false otherwise
	 * @throws Exception
	 */
	public boolean isCountMatching(String locator, int finalCountToVerify) throws Exception {
		int finalCount = Integer.parseInt(getTextOfWEPCustomerHomePage(locator));
		return finalCountToVerify == finalCount;
	}

	/**
	 * This method gets the total pagination number
	 * @param wepDeviceListPage device list page object
	 * @param locator - locator
	 * @return total count displayed
	 * @throws Exception
	 */
	public int getTotalNumbersFromPagination(WEPDeviceListPage wepDeviceListPage, String locator) throws Exception {
		String totalDeviceText = wepDeviceListPage.getTextOfWEPDeviceListPage(locator);
		return Integer.parseInt(totalDeviceText.replace(",","").split("of ")[1]);
	}

	/**
	 * This method verifies if the count is matching once redirected to device list page
	 * @param fleetInventoriesListElements - elements of fleet widget
	 * @param differentManuDeviceCount - count for the different manufacturers
	 * @param customerHomePage -obj of supportive class
	 * @param wepDeviceListPage -obj of supportive class
	 * @return true if count is matching
	 * @throws Exception
	 */
	public boolean verifyManufacturerDeviceCountFromDeviceList(HashMap<String, Integer> differentManuDeviceCount, WEXCustomerHomePage customerHomePage, WEPDeviceListPage wepDeviceListPage) throws Exception {
		try {
			List<WebElement> refreshedList = getElementsOfWEPCustomerHomePage("fleetInventoryWidget");
			int latestCountOfRemainPcs = Integer.parseInt(customerHomePage.getTextOfWEPCustomerHomePage("valueExtractor").split("PCs")[0]);
			String pcInventoryName = refreshedList.get(1).getText();
	        LOGGER.info("Manufacturer name captured as: " + pcInventoryName);
	        try {
	            mouseHoverclickOfCustomerHomePage(refreshedList.get(1));
	        } catch (Exception e) {
				LOGGER.info("Exception handelled while clicking on first manufacturer");
	        }
			LOGGER.info("clicked on first manufacturer from fleet inventory widget");
			latestCountOfRemainPcs = Integer.parseInt(customerHomePage.getTextOfWEPCustomerHomePage("valueExtractor").split("PCs")[0]);
			LOGGER.info("Latest count of remaining PCs after clicking first manufacturer: " + latestCountOfRemainPcs);
			boolean flag = false;
			if(differentManuDeviceCount.containsKey(pcInventoryName) && differentManuDeviceCount.get(pcInventoryName) == latestCountOfRemainPcs){
				LOGGER.info("Trying to click on widget");
				int yOffset = 00;
				while(customerHomePage.verifyElementIsVisible(WEXCustomerHomeProperties.getProperty("chartCircle"))) {
					customerHomePage.mouseHoverbyoffsettClick("chartCircle", 00, yOffset);
					yOffset++;
				}
				waitForPageLoaded();
				int numberOfTotalDevices = customerHomePage.getTotalNumbersFromPagination(wepDeviceListPage,"showingPaginationTotalCount");
				LOGGER.info("Number of total devices present in list page: "+ numberOfTotalDevices);
				flag =  differentManuDeviceCount.get(pcInventoryName) == numberOfTotalDevices;
				LOGGER.info("Verified redirection count of first manufacturer is matching device list page count");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception handelled in method verifyManufacturerDeviceCountFromDeviceList");
			return false;
		}
	}
	
	public boolean verifyIntegrationConnectors(String key) {
		try {
		return verifyElementIsPresent(WEXCustomerHomeProperties.getProperty(key));
	} catch (Exception e) {
		LOGGER.error("Exception occurred in method verifyElementsOfWEPCustomerHomePage {}", e.getMessage());
	}
	return false;

	}

	public void verifySwitchToFrame(String key) throws Exception {
	switchToIframe(WEXCustomerHomeProperties.getProperty(key));
	}
	
	public void verifySwitchToDefaultContent() {
		switchToDefaultContent();
	}
	
	public final boolean matchTextOfCustomerHomePage(String key, String textToMatch) {
        try {
		           return verifyTextPresentOnElement(WEXCustomerHomeProperties.getProperty(key), textToMatch);
       } catch (Exception e) {
		 LOGGER.error(("Exception occured in method matchTextOfCustomerHomePage " + e.getMessage()));
         return false;
	   }
     }
			
	 public final boolean actionClickOnElementsOfCustomerHomePage(String key) {
        try {
           actionClick(WEXCustomerHomeProperties.getProperty(key));
            return true;
       } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXPartnerDashboardPage " + e.getMessage()));
            return false;
       }
   }
	 
	public final List<WebElement> getAllElementsofWEPCustomerHomePage(String key) {
	        try {
	            return getAllElements(WEXCustomerHomeProperties.getProperty(key));
	        } catch (Exception e) {
	            LOGGER.error(("Exception occured in method getWebelementsOfWEPCustomerHomePage " + e.getMessage()));
	            return null;
	        }
	}
	
	/* ---------- Helpers ---------- */

	private boolean hoverChartSlice(WebElement bar) {
	    try {

	        // Final fallback: JS events
	        ((JavascriptExecutor) getDriver()).executeScript(
	                "['mousemove','mouseenter','mouseover'].forEach(ev=>" +
	                        "arguments[0].dispatchEvent(new MouseEvent(ev,{bubbles:true,view:window})));", bar);
	        sleeper(300);
	        return true;
	    } catch (Exception e) {
	        LOGGER.error("Hover logic failed: " + e.getMessage());
	        return false;
	    }
	}
	
	private boolean hoverChartSlice(WebElement slice, boolean useActions) {
	    if (slice == null) {
	        LOGGER.error("hoverChartSlice called with null slice. useActions=" + useActions);
	        return false;
	    }

	    try {
	        // Always try to bring element into view first
	        try {
	            ((JavascriptExecutor) getDriver())
	                    .executeScript("arguments[0].scrollIntoView({block:'center',inline:'center'});", slice);
	        } catch (Exception e) {
	            LOGGER.warn("scrollIntoView failed before hover: " + e.getMessage());
	        }

	        boolean hoverSucceeded = false;

	        if (useActions) {
	            // PRINTERS path: use Actions with multiple offsets and validate tooltip
	            Actions actions = new Actions(PreDefinedActions.getDriver());

	            int[][] offsets = new int[][]{
	                    {0, 0},    // center
	                    {2, 2},
	                    {-2, -2},
	                    {4, 0},
	                    {0, 4},
	                    {-4, 0},
	                    {0, -4}
	            };

	            for (int[] off : offsets) {
	                try {
	                    actions.moveToElement(slice, off[0], off[1])
	                           .pause(Duration.ofMillis(250))
	                           .perform();
	                    sleeper(300);

	                    LOGGER.info("Hover attempted with Actions at offset (" + off[0] + "," + off[1] + ")");

	                    // check tooltip after every move
	                    if (verifyElementsOfWEPCustomerHomePage("chartToolTip")) {
	                        hoverSucceeded = true;
	                        LOGGER.info("Tooltip appeared after Actions hover at offset (" +
	                                off[0] + "," + off[1] + ")");
	                        break;
	                    }
	                } catch (Exception e) {
	                    LOGGER.warn("Actions hover failed at offset (" + off[0] + "," + off[1] + "): " + e.getMessage());
	                }
	            }

	            // If Actions did not produce tooltip, fall back to JS hover on same slice
	            if (!hoverSucceeded) {
	                LOGGER.warn("All Actions hover attempts failed to show tooltip. Falling back to JS hover.");
	                try {
	                    JavascriptExecutor js = (JavascriptExecutor) getDriver();
	                    js.executeScript(
	    		                "['mousemove','mouseenter','mouseover'].forEach(ev=>" +
	    		                        "arguments[0].dispatchEvent(new MouseEvent(ev,{bubbles:true,view:window})));", slice);
	    	            sleeper(500);
	    	            LOGGER.info("Hover on chart slice attempted with JavaScript.");
	                    hoverSucceeded = verifyElementsOfWEPCustomerHomePage("chartToolTip");
	                    if (hoverSucceeded) {
	                        LOGGER.info("Tooltip appeared after JS hover fallback.");
	                    }
	                } catch (Exception e) {
	                    LOGGER.error("JS hover fallback failed: " + e.getMessage(), e);
	                }
	            }
	        } else {
	            // Non-printers path: JS hover only
	            JavascriptExecutor js = (JavascriptExecutor) getDriver();
	            js.executeScript(
		                "['mousemove','mouseenter','mouseover'].forEach(ev=>" +
		                        "arguments[0].dispatchEvent(new MouseEvent(ev,{bubbles:true,view:window})));", slice);
	            sleeper(500);
	            hoverSucceeded = verifyElementsOfWEPCustomerHomePage("chartToolTip");
	            LOGGER.info("Hover on chart slice attempted with JavaScript. tooltipPresent=" + hoverSucceeded);
	        }

	        if (hoverSucceeded) {
	            LOGGER.info("Hover on chart slice succeeded. useActions=" + useActions);
	            return true;
	        } else {
	            LOGGER.error("Hover on chart slice failed to show tooltip. useActions=" + useActions);
	            return false;
	        }

	    } catch (Exception ex) {
	        LOGGER.error("Hover logic failed. useActions=" + useActions + " error=" + ex.getMessage(), ex);
	        return false;
	    }
	}
	

	private String fetchTooltipText(String tooltipLocatorKey, long timeoutMs) throws InterruptedException {
	    long end = System.currentTimeMillis() + timeoutMs;
	    while (System.currentTimeMillis() < end) {
	        try {
	            String txt = getTextOfWEPCustomerHomePage(tooltipLocatorKey);
	            if (txt != null && !txt.trim().isEmpty()) {
	                return txt.trim();
	            }
	        } catch (Exception ignored) {}
	        sleeper(150);
	    }
	    return null;
	}

	private int extractCountFromTooltip(String tooltip) {
	    // Expected format: Vendor : 301 (85%)
	    try {
	        String afterColon = tooltip.split(":", 2)[1].trim();
	        String countToken = afterColon.split("\\s+")[0].replace(",", "");
	        return Integer.parseInt(countToken);
	    } catch (Exception e) {
	        throw new RuntimeException("Unexpected tooltip format: " + tooltip, e);
	    }
	}

	private boolean simpleSvgSliceClick(WebElement el) {
	    // Bring into view (ignore failures)
		try {
	        ((JavascriptExecutor)getDriver()).executeScript(
	            "arguments[0].dispatchEvent(new MouseEvent('click',{bubbles:true}));", el);
	        sleeper(120);
	    } catch (Exception ignored) {
	    	return false;
	    }
		return true;    
	}
	
	public boolean verifyFleetInventoryPieChart(String barLocator, String tooltipLocator, String categoryTileLocator, String languageCode) throws Exception {
	    DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
	    
	    String categoryText = "";
	    try {
	        categoryText = getTextOfWEPCustomerHomePage(categoryTileLocator);
	        LOGGER.info("Category tile text: " + categoryText);
	    } catch (Exception e) {
	        LOGGER.error("Failed to read category tile text for locator: " + categoryTileLocator, e);
	    }

	    // \*If it is Printers -> use Actions, otherwise use JavaScript\*
	    boolean useActions = categoryText != null && categoryText.toLowerCase().contains("printers");
	    LOGGER.info("Hover strategy decided from category: " +
	                categoryText + " -> useActions=" + useActions);

	    if (!verifyElementsOfWEPCustomerHomePage(barLocator)) {
	        LOGGER.warn("No pie segments found for locator: " + barLocator);
	        return true;
	    }

	    int totalSlices = getElementsOfWEPCustomerHomePage(barLocator).size();
	    if (totalSlices == 0) {
	        LOGGER.warn("Pie slice list empty.");
	        return true;
	    }
	    LOGGER.info("Processing " + totalSlices + " pie slices.");

	    boolean allOk = true;

	    for (int index = 0; index < totalSlices; index++) {
	        // Re-fetch current slice list (DOM may rebuild)
	        List<WebElement> slices = getElementsOfWEPCustomerHomePage(barLocator);
	        if (index >= slices.size()) {
	            LOGGER.warn("Slice index " + index + " no longer present after DOM refresh.");
	            break;
	        }

	        WebElement slice = slices.get(index);
	        String labelForLog = "Slice[" + index + "]";
	        boolean sliceClicked = false;

	        try {
	            // Defensive scroll (ignores failures)
	            try {
	                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", slice);
	            } catch (Exception ignored) {}

	            if (!hoverChartSlice(slice, useActions)) {
	                LOGGER.error(labelForLog + " hover failed.");
	                allOk = false;
	                continue;
	            }

	            String tooltipText = fetchTooltipText(tooltipLocator, 2500);
	            if (tooltipText == null) {
	                LOGGER.error(labelForLog + " tooltip not found.");
	                allOk = false;
	                continue;
	            }

	            int expectedCount;
	            try {
	                expectedCount = extractCountFromTooltip(tooltipText);
	            } catch (Exception e) {
	                LOGGER.error(labelForLog + " tooltip parse failed: " + tooltipText, e);
	                allOk = false;
	                continue;
	            }

	            LOGGER.info(labelForLog + " expectedCount=" + expectedCount + " tooltip='" + tooltipText + "'");

	            if (!simpleSvgSliceClick(slice)) {
	                LOGGER.error(labelForLog + " click failed.");
	                allOk = false;
	                continue;
	            }

	            // Light stabilization (replace with explicit wait for target page if available)
	            sliceClicked = true;
	            sleeper(400);

	            boolean pageOk;
	            try {
	                pageOk = dexPage.verifyCountOfDevices(expectedCount, languageCode);
	            } catch (Exception e) {
	                LOGGER.error(labelForLog + " verifyCountOfDevices threw exception.", e);
	                pageOk = false;
	            }

	            if (!pageOk) {
	                LOGGER.error(labelForLog + " count verification FAILED. Expected=" + expectedCount);
	                allOk = false;
	            } else {
	                LOGGER.info(labelForLog + " count verification passed.");
	            }
	        } catch (StaleElementReferenceException sere) {
	            LOGGER.warn(labelForLog + " stale element encountered. Skipping this slice.");
	            allOk = false;
	        } catch (Exception e) {
	            LOGGER.error(labelForLog + " unexpected exception.", e);
	            allOk = false;
	        } finally {
	            // Always attempt to return to chart for next slice (unless already on chart)
	        	if (sliceClicked) {
	            try {
	                getDriver().navigate().back();
	                // Wait for chart root presence (reuse existing wait)
	                waitForElementsOfWEPCustomerHomePage(barLocator);
	                sleeper(2000);
	                scrollToElementsOfWEPCustomerHomePage(categoryTileLocator);
	                clickByJavaScriptOnElementsOfWEPCustomerHomePage(categoryTileLocator);
	                sleeper(2000);
	                waitForElementsOfWEPCustomerHomePage(barLocator);
	                
	            } catch (Exception e) {
	                LOGGER.error("Navigation back failed after " + labelForLog + ". Aborting further processing.", e);
	                return false;
	            }
	         }
	      }
	        
	    }

	    return allOk;
	}
		
}
