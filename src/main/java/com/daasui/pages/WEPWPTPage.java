package com.daasui.pages;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
public class WEPWPTPage extends CommonMethod {

	private ObjectReader WEPWPTPagePropertiesReader = new ObjectReader();
    private Properties WEPWPTPageProperties;
    private final static Logger LOGGER = LogManager.getLogger(DEXPage.class);
    public static String environment;
    private WEPWPTPage instance;
    public static String uiVersion = System.getProperty("uiVersion");

    /**
     * This method is to get the instance of WEPWPTPage
     *
     */
    public WEPWPTPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPWPTPage.class) {
                if (instance == null) {
                    instance = new WEPWPTPage(DRIVER);
                }
            }
        }
        return instance;
    }

    /**
     * This is the constructor of WEPWPTPage class
     * @param driver
     * @throws IOException
     */
    public WEPWPTPage(WebDriver driver) throws IOException {
            WEPWPTPageProperties = WEPWPTPagePropertiesReader.getObjectRepository("WEPWPT");
    }
   
    /**
     * This method is to verify the elements of WEPWPTPage
     * @param key
     * @return
     */
    public final boolean verifyElementsOfWEPWPTPage(String key) {
    try {
        return  verifyElementIsPresent(WEPWPTPageProperties.getProperty(key));
    } catch (Exception e) {
        LOGGER.error("Exception occurred in method verifyElementsOfWEPWPTPage {}", e.getMessage());
        return false;
        }
    }
    
    /**
     * This method is to verify the visibility of elements of WEPWPTPage
     * @param key
     * @return
     */
    public final boolean waitForElementOfWEPWPTPage(String key) { 
    try {
        return verifyElementIsVisible(WEPWPTPageProperties.getProperty(key));
    } catch (Exception e) {
        LOGGER.error("Exception occurred in method waitForElementsOfWEPDeviceListPage {}", e.getMessage());
        return false;
            }
     }
         
     /**
      * This method is to clear the filters of WEPWPTPage
     * @param clearFilterKey
     * @throws Exception
     */
    public void clearFiltersOfWEPWPTPage(String clearFilterKey) throws Exception {
         clearFilters(WEPWPTPageProperties.getProperty(clearFilterKey));
     }
          
    /**
     * This method is to click on the elements of WEPWPTPage
     * @param key
     * @throws Exception
     */
    public final void actionClickOfWEPWPTPage(String key) throws Exception {
         actionClick(WEPWPTPageProperties.getProperty(key));
     }
     
    /**
     * This method is to open the device details page of WEPWPTPage
     * @param serialNumber
     * @throws Exception
     */
    public void openDeviceDetails(String serialNumber) throws Exception {
    	 WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
    	 clearFiltersOfWEPWPTPage("clearfilter");
    	 ArrayList<String> serialNumbers = new ArrayList<>(List.of(serialNumber));
    	    assertTrue(wepDeviceListPage.verifyAddedWEPDevicesOnListPage(serialNumbers), "Devices are not getting reflected back to the list page.");
    	    actionClickOfWEPWPTPage("firstSerialNumberText");
    	    waitForPageLoaded();
    }
                  
    /**
     * This method is to verify the device details tabs of WEPWPTPage
     * @throws Exception
     */
    public void verifyDeviceDetailsTabs() throws Exception {
    	 waitForElementOfWEPWPTPage("deviceDetailsHeader");
    	 waitForElementOfWEPWPTPage("WPTActionButton1");
    	    actionClickOfWEPWPTPage("WPTActionButton1");
    	    List<String> actionElements = List.of(
    	            "ActionexportDataButton",
    	            "ActionRefreshDataButton",
    	            "ActiondeleteDataButton",
    	            "ActionCollectnewlogsButton",
    	            "ActionLockButton",
    	            "ActionEraseButton"
       
    	        );
    	    
    	        for (String key : actionElements) {
    	            assertTrue(verifyElementsOfWEPWPTPage(key), key + " is not present on clicking Actions button.");
    	        }
     }
        	         
    /**
	 * This method is to open the WPT Setting Page
	 * @param driver
	 * @throws IOException
	 */
    public void wptSettingPage(WebDriver driver) throws IOException {
    	 WEPWPTPageProperties = WEPWPTPagePropertiesReader.getObjectRepository("WEXSettingPage");
 	}
        
    /**
     * This method is to open the Preferences tab of WEPWPTPage
     * @throws Exception
     */
    public void openPreferencesTab() throws Exception {
    	    waitForElementOfWEPWPTPage("Preferencestab");
    	    actionClickOfWEPWPTPage("Preferencestab");
     }

    /**
     * This method is to open the Approver Count Setting of WEPWPTPage
     * @throws Exception
     */
    public void openApproverCountSetting() throws Exception {
    	    waitForElementOfWEPWPTPage("WEPWptApproverCountSetting");
    	    actionClickOfWEPWPTPage("WEPWptApproverCountSetting");
    }

    /**
     * This method is to verify the Approver Count Setting of WEPWPTPage
     * @return
     */
    public boolean isApproverCountSettingDisplayed() {
    	    return verifyElementsOfWEPWPTPage("WEPWptApproverCountSetting");
    }
    	
    /**
     * @param key
     * This method is to click on the elements of WPT Setting Page
     * @return
     * @throws Exception
     */
    public final boolean clickOnElementsOfWPTSettingPage(String key) throws Exception {
    		try {
    			click(WEPWPTPageProperties.getProperty(key));
    			return true;
    		 } catch (Exception e) {
    			LOGGER.error(("Exception occured in method clickOnElementsOfWEXSettingPage " + e.getMessage()));
    			return false;
    		}
    }
        	
    /**
     * This method is to match the text of WPT Setting Page
     * @param key
     * @param Text
     * @return
     * @throws Exception
     */
    public final boolean matchTextOfWPTSettingPage(String key, String Text) throws Exception {
    		return verifyTextPresentOnElement(WEPWPTPageProperties.getProperty(key), Text);
    }
    	
    /**
     * This method is to verify the hardware tabs of WEPWPTPage
     * @throws Exception
     */
    public void verifyDeviceDetailshardwareTabs() throws Exception {
    		assertTrue(waitForElementOfWEPWPTPage("hardwareTab"), "Hardware tab is not present on the WEPWPT page.");   
    		actionClickOfWEPWPTPage("hardwareTab");
    		List<String> hardwareTabElements = List.of(
    			    "HPWolfProtectandTraceheader",
    			    "HPWPTServiceActiavationStatus",
    			    "HPWPTServiceStatus",
    			    "HPWPTModuleSupported",
    			    "HPWPTModuleActivationStatus",
    			    "HPWPTMModuleIMEI",
    			    "HPWPTModuleFirmwareVersion",
    			    "HPWPTMModuleeSIMID"
    			    );

    			for (String key : hardwareTabElements) {
    			    assertTrue(waitForElementOfWEPWPTPage(key),key + " is not present on the WEPWPT page.");
    			}
     }
        	
     /**
     * This method is to clear the text on the WPT Settings Page
     * @param key
     * @throws Exception
     */
    public final void clearTextOnWPTSettingsPage(String key) throws Exception {
        		clearText(WEPWPTPageProperties.getProperty(key));
     }
        	
     /**
     * This method is to enter text on the WPT Settings Page
     * @param key
     * @param Text
     * @throws Exception
     */
    public final void enterTextOnWPTSettingsPage(String key, String Text) throws Exception {
        		enterText(WEPWPTPageProperties.getProperty(key), Text);
     }
    	
        	/**
        	 * @param key
        	 * This method is to Action click on the elements of WEPWPTPage
        	 * @return
        	 * @throws Exception
        	 */
     public final boolean actionclickOnElementsOnWPTPage(String key) throws Exception {
        		try {       			
        			actionClick(WEPWPTPageProperties.getProperty(key));
        			return true;
        		} catch (Exception e) {
        			LOGGER.error(("Exception occured in method clickOnElementsOfWEPWPTPage " + e.getMessage()));
        			return false;
        		}
     }
        	
        	/**
        	 * This method is to verify the account overview tabs of WEPWPTPage
        	 * @throws Exception
        	 */
     public void verifyAccountOverviewTabs() throws Exception {
        		//WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
        		List<String> companyProfileElements = List.of(
        			    "wptCompanyProfile",
        			    "wptCustomerID",
        			    "wptCompanysize",
        			    "wptCompanyname",
        			    "wptCreatedon",
        			    "wptPrimaryadministrator",
        			    "wptPrimaryadministratorName",
        			    "wptPrimaryadministratorPhone",
        			    "wptPrimaryadministratorEmail",
        			    "wptCompanyAddress",
        			    "wptCompanyAddressCountry",
        			    "wptCompanyAddressAddressLine1",
        			    "wptCompanyAddressAddressLine2",
        			    "wptCompanyAddressState",
        			    "wptCompanyAddressCity",
        			    "wptCompanyAddressZipCode"
        			);

        			for (String key : companyProfileElements) {
        			    assertTrue(verifyElementsOfWEPWPTPage(key),key + " is not present on the Account Overview page.");
        			}
      }

     /**
     * This method is to match the text of WEPWPTPage
     * @param key
     * @param Text
     * @return
     * @throws Exception
     */
     public final boolean matchTextOfWEXWPTSettingPage(String key, String Text) throws Exception {
        		return verifyTextPresentOnElement(WEPWPTPageProperties.getProperty(key), Text);
     }
        	       	
	/**
	 * This method is to get the text of WEPWPTPage
	 * @param key
	 * @return
	 */
	public final String getTextOfWEPWPTPage(String key) {
		try {
			return getTextBy(WEPWPTPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfSystemRequirementsPage " + e.getMessage()));
			return null;
		}
	}
    	        	
    /**
     * This method is to get all the elements of WEPWPTPage
     * @param key
     * @return
     * @throws Exception
     */
    public final List<WebElement> getAllElementsOfWEPWPTPage(String key) throws Exception {
    		return getAllElements(WEPWPTPageProperties.getProperty(key));
    }
    	
	/**
	 * This method is to verify the WPT License on WEPWPTPage
	 * @param expectedLicenses
	 * @return
	 * @throws Exception
	 */
    public final boolean verifyWPTLicense(List<String> expectedLicenses) throws Exception {
		List<WebElement> licenseElements = getAllElementsOfWEPWPTPage("wptLicense");
		for (WebElement license : licenseElements) {
			String licenseText = getTextOfWEPWPTPage(license);
			for (String expected : expectedLicenses) {
                if (licenseText.equalsIgnoreCase(expected)) {
                    return true; // Found a matching license
                }
            }
		}
		return false; // No matching license found
	}

	 /**
	 * This method is to get the text of WEPWPTPage license
	 * @param license
	 * @return
	 */
	public String getTextOfWEPWPTPage(WebElement license) {
			try {
    			return getTextBy(license);
    		} catch (Exception e) {
    			LOGGER.error(("Exception occured in method getTextOfSystemRequirementsPage " + e.getMessage()));
    			return null;
    		}				
	}
    		
	/**
	 * This method is to edit the WPT Threshold and save
	 * @throws Exception
	 */
	public void editWPTThresholdAndSave() throws Exception {
			verifyElementsOfWEPWPTPage("hpWPTEditThesholdValue");
		    actionclickOnElementsOnWPTPage("hpWPTEditThesholdValue");
		    waitForPageLoaded();
		    clickOnElementsOfWPTSettingPage("wptThresholdToggle");
		    verifyElementsOfWEPWPTPage("hpWPTApproverCountSaveButton");
		    actionclickOnElementsOnWPTPage("hpWPTApproverCountSaveButton");
		    verifyElementsOfWEPWPTPage("wptToastNotification");
    }
						
		/**
		 * This method is to edit the WPT Threshold, set approver count and save
		 * @throws Exception
		 */
	 public void editenableWPTThresholdAndSave() throws Exception {				
			verifyElementsOfWEPWPTPage("hpWPTEditThesholdValue");
		    actionclickOnElementsOnWPTPage("hpWPTEditThesholdValue");
		    waitForPageLoaded();
		    clickOnElementsOfWPTSettingPage("wptThresholdToggle");
		    verifyElementsOfWEPWPTPage("hpWPTApproverCount");
		    clearTextOnWPTSettingsPage("hpWPTApproverCount");
			enterTextOnWPTSettingsPage("hpWPTApproverCount","2");
		    
			verifyElementsOfWEPWPTPage("hpWPTApproverCountSaveButton");
		    actionclickOnElementsOnWPTPage("hpWPTApproverCountSaveButton");
		    verifyElementsOfWEPWPTPage("wptToastNotification");
		}
	 
	 public final void clickByActionsClickWEP(String key) {
			try {
				//waitUntilElementIsVisible(key);
				actionClick(WEPWPTPageProperties.getProperty(key));
			} catch (Exception e) {
				LOGGER.error(("Exception occured in method clickByActionsClickWEP " + e.getMessage()));
			}
		}
		public boolean verifyElementsOfWEPpreferencesPage(String key) {
			try {
				return verifyElementIsPresent(WEPWPTPageProperties.getProperty(key));
			} catch (Exception e) {
				LOGGER.error(("Exception occured in method verifyElementsOfWEPpreferencesPage " + e.getMessage()));
				return false;
			}
		}
		public boolean verifyElementsOfPresence(String key) {
			try {
				return verifyElementIsPresent(WEPWPTPageProperties.getProperty(key));
			} catch (Exception e) {
				LOGGER.error(("Exception occured in method verifyElementsOfWEPpreferencesPage " + e.getMessage()));
				return false;
			}
		}
		public String getTextOfElement(String key) throws Exception {
			 return getTextBy(WEPWPTPageProperties.getProperty(key));

		}
		 public void clearFiltersOfDevicesListPage(String clearFilterKey) throws Exception {
		        clearFilters(WEPWPTPageProperties.getProperty(clearFilterKey));
		    }
		   public final void verifyElementsOfWEPDeviceListPage(String key) {
		        try {
		            verifyElementIsPresent(WEPWPTPageProperties.getProperty(key));
		        } catch (Exception e) {
		            LOGGER.error("Exception occurred in method verifyElementsOfWEPDeviceListPage {}", e.getMessage());
		        }
		    }
		   private WebElement getElementOfWEPDeviceListPage(String key) throws Exception {
		        return getElement(WEPWPTPageProperties.getProperty(key));
		    }
		   public final void enterTextForDeviceListPage(String key, String textToEnter) {
		        try {
		            enterText(WEPWPTPageProperties.getProperty(key), textToEnter);
		        } catch (Exception e) {
		            LOGGER.error("Exception occurred in method enterTextForDeviceListPage {}", e.getMessage());
		        }
		    } 


		public void verifyWPTWEPDevicesOnListPage(String WPTDeviceserialNumber) throws Exception {
			verifyElementsOfWEPDeviceListPage("searchDeviceSerialNumberTextBox");
	        getElementOfWEPDeviceListPage("searchDeviceSerialNumberTextBox").clear();
	        enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", WPTDeviceserialNumber); 
		}




		public final void clickOnElementsOfPreferenceDetailsPage(String key) throws Exception {
			click(WEPWPTPageProperties.getProperty(key));
		}



		   public final void enterTextForUserPage(String key, String textToEnter) {
		        try {
		            enterText(WEPWPTPageProperties.getProperty(key), textToEnter);
		        } catch (Exception e) {
		            LOGGER.error("Exception occurred in method enterTextForDeviceListPage {}", e.getMessage());
		        }
		    }
		public void selectLogsType(String key, String wptSelectLogstype) {

				try {
					selectDropdownOptions(WEPWPTPageProperties.getProperty(key), wptSelectLogstype);
				} catch (Exception e) {
					 LOGGER.error("Exception occurred in method selectLogsType {}", e.getMessage());
				}


		}
		public void selectLogsSubType(String key, String wptSelectLogssubtype) {
			try {
				selectDropdownOptions(WEPWPTPageProperties.getProperty(key), wptSelectLogssubtype);
			} catch (Exception e) {
				 LOGGER.error("Exception occurred in method selectLogsType {}", e.getMessage());
			}

		}
		public void verifydomainNameswithStatus(String key) throws Exception {	
			Map<String, String> domainStatusMap = new HashMap<>();

			try {
			    ArrayList<String> domainList = getTextOfList(WEPWPTPageProperties.getProperty(key));
			    LOGGER.info("Domain names are: " + domainList);

			    for (String entry : domainList) {
			        String[] parts = entry.split(" ");
			        if (parts.length == 2) {
			            domainStatusMap.put(parts[0].trim(), parts[1].trim());
			        }
			    } Map<String, String> expectedDomainStatus = getExpectedDomainStatus(); 

			    for (Map.Entry<String, String> expectedEntry : expectedDomainStatus.entrySet()) {
			        String domain = expectedEntry.getKey();
			        String expectedStatus = expectedEntry.getValue();
			        String actualStatus = domainStatusMap.get(domain);

			        assertEquals(actualStatus, expectedStatus, domain + " should be " + expectedStatus);
			    }

			    LOGGER.info("All domain verifications passed successfully.");

			} catch (Exception e) {
			    LOGGER.error("Exception occurred in method verifyDomainNamesWithStatus {}", e.getMessage());
			}


		}
		private Map<String, String> getExpectedDomainStatus() {
			Map<String, String> expected = new HashMap<>();
		    expected.put("gmail.com", "unverified");
		    expected.put("workforceqa.ext.hp.com", "verified");	    
		    return expected;
		}
		public Boolean verifyDateAndTimeSortingAscending(String key, String attribute)  {
			Boolean isAscending = false;
			String sortByDateAndTime;
			try {
				 sortByDateAndTime= getAttribute(WEPWPTPageProperties.getProperty(key),attribute);
				if(sortByDateAndTime.contains("ascending"))
						{
					isAscending= true;
						}
				else {
					click(WEPWPTPageProperties.getProperty(key));
					sortByDateAndTime= getAttribute(WEPWPTPageProperties.getProperty(key),attribute);
					if(sortByDateAndTime.contains("ascending"))
				{			isAscending= true;
				}

			}} catch (Exception e) {
				LOGGER.error("Exception occurred in method verifydomainNameswithStatus {}", e.getMessage());

			}
			return isAscending;				


		}


		public boolean verifyLogsData(String key,String  logsData) throws Exception {
			String logText=getTextBy(WEPWPTPageProperties.getProperty(key));
			if (logText.contains(logsData)) {
				LOGGER.info("Logs data is present: " + logsData);
				return true;
			} else {
				LOGGER.error("Logs data is not present: " + logsData);
				return false;
			}


		}
		public void selectPlan(String key, String wptSelectPlan) {
			try {
				selectDropdownOptions(WEPWPTPageProperties.getProperty(key), wptSelectPlan);
			} catch (Exception e) {
				 LOGGER.error("Exception occurred in method selectPlan {}", e.getMessage());
			}


		}
		public boolean verifyVisibilityofToggle(String key) {
			int maxretrycnt = 5;
		    try {
				for (int attempt=1; attempt <=maxretrycnt; attempt++) {
					    if(verifyElementsOfPresence(WEPWPTPageProperties.getProperty(key))) { 
						 return true;
					}
					sleeper(2000);
			    }        
		    } catch (Exception e) {
		        LOGGER.error("Exception in verifyvisibilityofToggle: ", e);
		        return false;
		    }
		    return false;
		}
		public boolean waitForElementsOfUserDetailsPage(String key) throws Exception {

			return verifyElementIsVisible(WEPWPTPageProperties.getProperty(key));
		}
		public boolean verifyElementIsSelectedOfPreferencePage(String key) throws Exception {

			return verifyElementIsSelected(WEPWPTPageProperties.getProperty(key));
		}
		public boolean verifyLogsdate(String key) throws Exception {
			LocalDate currentDate = LocalDate.now();
			String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
			LOGGER.info("Current Date: " + formattedDate);		
			waitUntilElementIsVisible(WEPWPTPageProperties.getProperty(key));
			String Logsdate = getTextBy(WEPWPTPageProperties.getProperty(key)) ;
			Logsdate = Logsdate.replaceAll("\\s+", " ").trim();
			String[] parts = Logsdate.split(",");
			String datePart = parts[0] + "," + parts[1];		
			LOGGER.info("datePart is : " + datePart);	
			try {

				if (datePart.equals(formattedDate)) {
					LOGGER.info("Logs date is present: " + formattedDate);
					return true;
				} else {
					LOGGER.error("Logs date is not present: " + formattedDate);
					return false;
				}
			} catch (Exception e) {
				LOGGER.error("Exception occurred in method verifyLogsdate {}", e.getMessage());
			}
			return false;
		}
		public boolean verifylocationSourceValue(String key, String value) {
			try {
				String sourceValue=getTextBy(WEPWPTPageProperties.getProperty(key));
				if (sourceValue.equalsIgnoreCase(value)) {
					LOGGER.info("Location source value is present: " + value);
					return true;
				} else {
					LOGGER.error("Location source value is not present: " + value);
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
 }

