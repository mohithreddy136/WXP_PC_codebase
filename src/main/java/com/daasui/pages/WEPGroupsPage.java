package com.daasui.pages;

import static org.testng.Assert.assertEquals;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.amazonaws.auth.policy.Action;
import com.basesource.action.CommonMethod;
import com.basesource.utils.CSVFileReader;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.WEPPGroupsPageVariables;
import com.daasui.constants.WEPPulsesCreationPageVariables;

public class WEPGroupsPage extends CommonMethod {

	private WEPGroupsPage instance;
	private ObjectReader GroupsPagePropertiesReader = new ObjectReader();
	private Properties GroupsPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(WEPGroupsPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");


	public WEPGroupsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPGroupsPage.class) {
				if (instance == null) {
					instance = new WEPGroupsPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public WEPGroupsPage(WebDriver driver) throws IOException {
		GroupsPageProperties = GroupsPagePropertiesReader.getObjectRepository("WEPGroupsPage");
	}
	public final boolean verifyElementIsPresentOnGroupsPage(String key) throws Exception {
		return verifyElementIsPresent(GroupsPageProperties.getProperty(key));
	}

	public void verifyListOfElemetnsOnGroupsPage(ArrayList<String> originalpropertyListOptions,ArrayList<String> propertyListOptions) throws Exception {
		// Compare the two ArrayLists
		if (originalpropertyListOptions.equals(propertyListOptions)) {
			System.out.println("The String array and ArrayList are equal.");
		} else {
			System.out.println("The String array and ArrayList are NOT equal.");
		}
	}


	public final void clickOnElementsOfGroupsPage(String key) throws Exception {
		click(GroupsPageProperties.getProperty(key));
	}
	public final void mouseHoverOfGroupsPage(String key) throws Exception {
		mouseHover(GroupsPageProperties.getProperty(key));
	}
	public final void actionClickOfGroupsPage(String key) throws Exception {
		actionClick(GroupsPageProperties.getProperty(key));
	}
	public  void doubleClickOfGroupsPage(String key) throws Exception {
		doubleclick(GroupsPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsOfGroupsPage(String key) throws Exception {
		return getAllElements(GroupsPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnGroupsPage(String key) throws Exception {
		clickByJavaScript(GroupsPageProperties.getProperty(key));
	}
	public final void listMouseHoverOfGroupsPage(String key) throws Exception {
		listMouseHover(GroupsPageProperties.getProperty(key));
	}
	public final WebElement getElementOfGroupsPage(String key) throws Exception {
		return getElement(GroupsPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleofGroupsPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(GroupsPageProperties.getProperty(key));
	}

	public final void switchToDifferentTabOfGroupsPage() {
		switchToDifferentTab();
	}

	public final void switchToPreviousTabOfGroupsPage() {
		switchBackToPreviousTab();
	}

	public final ArrayList<String> getChartLabelsGroupsPage(String key) throws Exception {
		return getLabelsOfChart(GroupsPageProperties.getProperty(key));
	}

	public void scrollToGroupsPage(String key) throws Exception {
		scrollTillView(GroupsPageProperties.getProperty(key));
	}

	/**
	 * This method designed to get the  Text of WebElement from web page
	 * @param WebElement 
	 */
	public final String getTextOfWEPGroupsPage(String key) throws Exception {
		return getTextBy(GroupsPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfGroupsPage(String key) throws Exception {
		return verifyElementIsVisible(GroupsPageProperties.getProperty(key));
	}

	public final boolean matchTextOfGroupsPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(GroupsPageProperties.getProperty(key), Text);
	}
	public final boolean waitForElementsOfGroupsPageDynamic(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(GroupsPageProperties.getProperty(key), waitTime);
	}

	public final boolean waitUntilElementIsInvisibleOfGroupsPage(String key){
		return verifyElementIsinvisibile(GroupsPageProperties.getProperty(key));
	}

	public final boolean waitUntilAllElementIsInvisibleOfGroupsPage(List<WebElement> key){
		return verifyAllElementIsinvisibile(key);
	}

	/** This method will verify export functionality **/
	public boolean verifyExportFunctionality(String languageCode) {
		{
			boolean flag = false;
			try {
				clickOnElementsOfGroupsPage("exportButton");
				clickOnElementsOfGroupsPage("exportXLSXFileLink");
				sleeper(3000);
				if(getTextOfWEPGroupsPage("toastNotificationExport").equalsIgnoreCase(getTextLanguage(languageCode,"daas_ui","campaign.response.export.data.success"))){
					flag=true;
					LOGGER.info("Toast Notification generated successfully for Export of Sustainability");
				}else{
					flag=false;
					LOGGER.error("Export API is not working correctly.");
				}
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return flag;

		}
	}



	/**
	 * This is the method for sorting the RoomName column in ascending order
	 * 
	 * @param sortingArrowKey - locator of sorting arrow key
	 * @param listKey - locator of list of items
	 * @param nextPaginationLinkKey - locator of next page navigation link present on pagination area
	 * @param firstPageNavigationKey - locator of first page navigation link present on pagination area
	 * @param idKey - locator of element to scroll up on page
	 * @return - boolean value of whether the sorting functionality is working correctly
	 */

	public final boolean sortingOfRoomNameoColumnInAscendingOrder(String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			//Before sorting
			ArrayList<String> unsortedList = getAllDevices(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			//After sorting
			clickOnElementsOfGroupsPage(sortingArrowKey);
			sleeper(4000);
			ArrayList<String> sortedList = getAllDevices(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			return sortingInAscendingOrder(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingOfDeviceNameColumnInAscendingOrder " + e.getMessage()));
			return false;
		}
	}

	public final boolean sortingOfRoomNameColumnInDescendingOrder(String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			ArrayList<String> unsortedList = getAllDevices(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			clickOnElementsOfGroupsPage(sortingArrowKey);
			clickOnElementsOfGroupsPage(sortingArrowKey);
			sleeper(2000);
			ArrayList<String> sortedList = getAllDevices(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			return sortingInDescendingOrder(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingOfDeviceNameColumnInDescendingOrder " + e.getMessage()));
			return false;
		}
	}


	/**
	 * This is a method to get a list of all the devices present on all the pages
	 * 
	 * @param listKey - This is the key for the list of values on column
	 * @param nextPaginationLinkKey - This is the key for the next page link on pagination
	 * @param firstPageNavigationKey - This is the key for the next page link on pagination
	 * @param idKey - This is the key for the element locator to scroll up on page
	 * @return - This method returns a arraylist of all the devices present on all the sustaianbility pages
	 */
	public final ArrayList<String> getAllDevices(String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		ArrayList<String> unsortedList=null;
		try {
			sleeper(2000);
			unsortedList = getTextOfListOfDevicesFromGroupsPage(listKey);
			scrollOnGroupsPage(idKey);
			sleeper(2000);
			return unsortedList;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAllRooms " + e.getMessage()));
			return unsortedList;
		}
	}

	/**
	 * This is a method to scroll on roomListPage page

	 * 
	 * @param key - Locator of element
	 */
	public final void scrollOnGroupsPage(String key) {
		try {
			scrollTillView(GroupsPageProperties.getProperty(key));
		} catch (Exception e) {

			LOGGER.error(("Exception occured in method scrollOnDeviceListPage " + e.getMessage()));
		}
	}

	private ArrayList<String> getTextOfListOfDevicesFromGroupsPage(String listKey) {
		try {
			return getTextOfList(GroupsPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfDeviceListFromGroupsPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify search functionality of search filters on column present on deviceListPage page
	 * 
	 * @param languageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 */
	public final boolean verifySearchValueOfColumnOnGroupList(String languageCode, String textKey, String text, String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionalityofColumn(languageCode, GroupsPageProperties.getProperty(textKey), text, GroupsPageProperties.getProperty(emptyTextKey), GroupsPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOfColumnOnGroupList " + e.getMessage()));
			return false;
		}
	}

	public void enterTextForGroupsPage(String key, String text) {
		try {
			enterText(GroupsPageProperties.getProperty(key), text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOfColumnOnGroupList " + e.getMessage()));
		}
	}


	/**
	 * This is a method for upload csv file while adding the devices  to group 
	 *
	 * @param fileName - list of devices to auto enroll
	 */
	public void addDevicestogroupViaCSV(String locator, String filePath) throws Exception {
		verifyElementIsPresentOnGroupsPage(locator);
		LOGGER.info("Clicked on upload file button");
		sleeper(2000);
		WebElement addFile = getElementOfGroupsPage(locator);
		addFile.sendKeys(filePath);
	}

	/**
	 * This method designed to click on DropDown field on Global Filter page
	 * @param dropdownField 
	 */
	public final void selectdropdownField(String dropdownField) {
		try {
			actionClick(GroupsPageProperties.getProperty(dropdownField));    
		}catch(Exception e) {
			LOGGER.error("Exception occured on selecting the company fromlist box "+dropdownField+" view"+e.getMessage());
		}

	}

	/**
	 * This method designed to verify the tooltip functionality on Sustainability page
	 * @param labelsKey , tooltipTextKey and textKey
	 */
	public final boolean verifyTooltipTextOnPowerConsumptionGraph(String labelsKey, String tooltipTextKey, String textKey)
			throws Exception {
		boolean flag = false;
		String text = null;
		List<WebElement> tooltipTextKeyValue = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfGroupsPage(labelsKey);
		//tooltipTextKeyValue = getElementsOfGroupsPage(tooltipTextKey);
		for (int i = 0; i < listOfLabels.size(); i++) {
			listOfLabels.get(i);
			sleeper(3000);
			action.moveToElement(listOfLabels.get(i)).build().perform();
			//waitForElementsOfGroupsPage(tooltipTextKey);
			//	text = tooltipTextKeyValue.get(i).getText();
			//	System.out.println(text);
			sleeper(2000);
			listOfLabels.get(i).click();
			List<WebElement> monthtext = getElementsOfGroupsPage(textKey);

			if (monthtext.get(0).getText().contains("Total Devices")) {
				flag = true;
			} else {
				LOGGER.error("Report Page did not load in 1 minute");
			}
		}
		return flag;
	}



	public final boolean selectTextValueFromDropdownList(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		return selectTextValueFromDropdown(GroupsPageProperties.getProperty(dropdownListKey), elementText, GroupsPageProperties.getProperty(dropdownBox));
	}

	public void doubleClickOnElementsOfGroupsPage(String locator) throws Exception {
		doubleclick(GroupsPageProperties.getProperty(locator));

	}

	/**
	 * This method is to get the attribute value for the provided tag
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public final String getAttributesOfWEPGroupsPage(String key, String value) throws Exception {
		return getAttribute(GroupsPageProperties.getProperty(key), value);
	}


	public void gotoGroupsTab(String languageCode) throws Exception {
		//	clickOnElementsOfGroupsPage("fleetManagement");
		clickOnElementsOfGroupsPage("groupsMenu");
		LOGGER.info("Groups are Showing in sidemenu bar");
		waitForPageLoaded();
		Assert.assertTrue(getTextOfWEPGroupsPage("groupsHeader")
				.equals(getTextLanguage(languageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups Header is Matched");

	}
	
	public boolean deleteGroups(String groupName) {
	    try {
	        enterTextForGroupsPage("groupNameSearch", groupName);

	        if (verifyElementsOfWEPGroupsPage("noResults")) {
	            LOGGER.info("No records found for the searched group: {}", groupName);
	            return false;
	        }

	        clickOnElementsOfGroupsPage("groupNameCheck");
	        clickOnElementsOfGroupsPage("groupDeleteButton");

	        String securityCode = getTextOfWEPGroupsPage("securityCodeNumber");
	        enterTextForGroupsPage("securityCodeField", securityCode);
	        clickOnElementsOfGroupsPage("deleteButton");

	        if (waitForElementsOfGroupsPage("toastNotificationExport")) {
	            LOGGER.info("Group deleted successfully: {}", groupName);
	            return true;
	        } else {
	            LOGGER.warn("Toast notification not found after deleting group: {}", groupName);
	        }

	    } catch (Exception e) {
	        LOGGER.error("Exception occurred in deleteGroups method for group {}: {}", groupName, e.getMessage(), e);
	    }
	    return false;
	}
	
	/**
	 * This method will get the current page URL and extract room id from there
	 * 
	 * @param NA
	 * @return String
	 * @throws Exception
	 */
	public final String getGroupID() {
		String groupID= getDriver().getCurrentUrl().split("groups/")[1];
		LOGGER.info("GroupId : "+groupID.split("/")[0]);
		return groupID.split("/")[0];
	}
	/**
	 * This method will delete room
	 * 
	 * @param environment
	 * @return
	 * @throws Exception
	 */
	public final boolean deleteGroup(String environment, String body_deleteRoomAPI, String environmentURL) {
		try {
			boolean flag = false;
			int code = getStatusCode(environmentURL + ConstantURL.DELETE_API_ROOM, body_deleteRoomAPI, "DELETE", environment);
			if (code != CommonVariables.CODEOK) {
				flag = false;
				LOGGER.error("Delete API got failed while deleting room.");
			}
			else
			{
				LOGGER.info("Delete room is successful");
				flag = true;
			}
			refreshPage();
			waitForPageLoaded();
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in deleteRoom: " + e.getMessage());
			return false;
		}
	}
  
  	/**
	 * This method returns the text of a list of elements
	 * @throws Exception 
	 */
	public ArrayList<String> getTextOfListOfElementsFromGroupPage(String key) throws Exception {
		return getTextOfList(GroupsPageProperties.getProperty(key));
	}
	
	/**
	 * This is a method to verify if the element is selected
	 * @param key - Locator of element
	 * @return - - boolean value of whether the element is selected or not
	 */
	public final boolean verifyElementsIsSelectedOfGroupsPage(String key) {
	try {
		return verifyElementIsSelected(GroupsPageProperties.getProperty(key));
	} catch (Exception e) {
		LOGGER.error(("Exception occured in method verifyElementsOfGroupsPageProperties " + e.getMessage()));
		return false;
	}
  }
	
	/**
	 * This method will check the Entra ID is connected on Integration page.
	 *
	 * @throws Exception
	 */
	public final boolean verifyEntraIDConnectionOnIntegrationPage(String key) throws Exception {
		waitForElementsOfGroupsPage("integrationTab");
		clickOnElementsOfGroupsPage("integrationTab");
		waitForPageLoaded();
		return verifyElementIsPresentOnGroupsPage(GroupsPageProperties.getProperty(key));
	}
	
	public void clickOnEntraIDNextBtn() throws Exception {
		sleeper(3000);
		waitForElementsOfGroupsPage("addGrpNextBtnEntraID");
		actionClickOfGroupsPage("addGrpNextBtnEntraID");
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEPGroupsPage(String key) {
		try {
			return verifyElementIsPresent(GroupsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEPGroupsPage " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * Helper method to select an item from a drop-down
	 * @param dropdownOptions
	 * @param inputValue
	 * @throws Exception
	 */
	public void selectFromDropdown(String dropdownOptions, String inputValue) throws Exception {
	    List<WebElement> optionsList = getElementsTillAllElementsVisibleofGroupsPage(dropdownOptions);
	    for (WebElement option : optionsList) {
	    	String acttext = option.getText();
	        if (inputValue.equals(acttext)) {
	        	clickWebelement(option);
	            //sleeper(2000);
	            LOGGER.info("Selected required option from " + dropdownOptions);
	            break;
	        }
	    }}
	
	/**
	 * Helper method to verify the group
	 * @param groupType
	 * @param languageCode
	 * @param valueKey
	 * @param descKey
	 * @param radioButton
	 * @param groupName
	 * @param groupDescription
	 * @param enterText
	 * @param valueLangKey
	 * @param descLangKey
	 * @return
	 */
	private boolean verifyGroup(String groupType, String languageCode, String valueKey, String descKey, String radioButton, String groupName, String groupDescription, boolean enterText, String valueLangKey, String descLangKey) {
	    boolean flag = false;
		verifyElementsOfWEPGroupsPage(valueKey);
	    verifyElementsOfWEPGroupsPage(descKey);
	    verifyElementsOfWEPGroupsPage(radioButton);
	    try {
			if (getTextOfWEPGroupsPage(valueKey).equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", valueLangKey)) &&
			    getTextOfWEPGroupsPage(descKey).equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", descLangKey))) {
			    actionClickOfGroupsPage(radioButton);
			    if (enterText) {
			        enterTextForGroupsPage("groupNameField", groupName);
			        LOGGER.info("Group Name is entered");
			        sleeper(2000);
			        enterTextForGroupsPage("groupDescriptionField", groupDescription);
					LOGGER.info("Group Description is entered");
			    }
			    return flag=true;
			} else {
			    LOGGER.error(groupType + " Header / Description is not matching.");
			    return flag;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * Method to verify dynamic group creation with step1
	 * @param groupsType
	 * @param languageCode
	 * @param groupName
	 * @param groupDescription
	 * @return
	 */
	public boolean verifyAddGroupsStep1GroupSelectionFlow(String groupsType, String languageCode, String groupName, String groupDescription) {
	    Map<String, String[]> groupMap = new HashMap<>();
	    groupMap.put("Dynamic Rules", new String[]{"dynamicGroupsValueCreation", "dynamicGroupsDescriptionCreation", "DynamicGroupsRadioButton", "group.dynamic_rules", "group.dynamic_rules_desc"});
	    groupMap.put("Static Assignment", new String[]{"staticGroupsValueCreation", "staticGroupsDescriptionCreation", "StaticGroupsRadioButton", "group.static_assignment", "group.static_assignment_desc"});
	    groupMap.put("Entra ID", new String[]{"EntraGroupsValueCreation", "EntraGroupsDescriptionCreation", "EntraGroupsRadioButton", "group.entra_id", "group.entra_id.desc"});

	    try {
	        if (groupMap.containsKey(groupsType)) {
	            String[] keys = groupMap.get(groupsType);
	            boolean enterText = !groupsType.equals("Entra ID");
	            return verifyGroup(groupsType, languageCode, keys[0], keys[1], keys[2], groupName, groupDescription, enterText, keys[3], keys[4]);
	        } else {
	            LOGGER.info("Provided option does not match any groups type!");
	            return false;
	        }
	    } catch (Exception e) {
	        LOGGER.error("Exception occurred in method verifyAddGroupsStep1GroupSelectionFlow: " + e.getMessage());
	        return false;
	    }
	}
	
	/**
	 * Method to return the specific Index value of groups Page
	 * @param key
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public final String getspecificIndexValueofGroupsPage(String key, int index) throws Exception {
		return getTextBy(GroupsPageProperties.getProperty(key) + "+" + index + "]");
	}
	
	/**
	 * Method to verify the step2 for dynamic group creation page
	 * @param languageCode
	 * @param propertyInputValue
	 * @param operatorInputValue
	 * @param valueInputValue
	 * @return
	 */
	public List<String> verifyStep2DynamicCreationFlow(String languageCode, String propertyInputValue, String operatorInputValue, String valueInputValue) {
	    List<String> secondStep = new ArrayList<>();
	    try {
	        if (getTextOfWEPGroupsPage("headersTextInStepper").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "group.dynamic_rules"))){
	        	
	        	defaultFilterCheck();
	            actionClickOfGroupsPage("propertyValueDropDown");
	            selectFromDropdown("OptionsList", propertyInputValue);
	            sleeper(2000);
	            secondStep.add(getAttributesOfWEPGroupsPage("selectedProperty","value"));

	            actionClickOfGroupsPage("operatorValueDropDown");
	            selectFromDropdown("OptionsList", operatorInputValue);
	            secondStep.add(getAttributesOfWEPGroupsPage("selectedOperator","value"));
	            sleeper(2000);

	            actionClickOfGroupsPage("valueDropDown");
	            enterTextForGroupsPage("searchItems", valueInputValue);
	            sleeper(2000);
	            selectFromDropdown("OptionsList", valueInputValue);
	            sleeper(2000);
	            secondStep.add(getAttributesOfWEPGroupsPage("selectedValue","value"));
	            actionClickOfGroupsPage("outerArea");
	            sleeper(2000);
	           // if ("false".equals(getAttributesOfWEPGroupsPage("calculateButton", "aria-disabled"))) {
	                actionClickOfGroupsPage("calculateButton");
	                sleeper(2000); 
	           // }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return secondStep;
	}
	
	
	public boolean verifyAllElementsinWEPGroupsPage(String[] allElements) {
	    for (String element : allElements) {
	        if (verifyElementsOfWEPGroupsPage(element)==false) {
	            return false; // Return false immediately if any element verification fails
	        }
	    }
	    return true; // All elements verified successfully
	}

	/**
	 * Default filter check
	 */
	public final void defaultFilterCheck () {
		String[] allElements = {"activeDevicesHeader","addRuleButton","undeterminedStateText","reachNoteText","progressBarDynamic","RecalculateButtonEnabled"};
		verifyAllElementsinWEPGroupsPage(allElements);
		LOGGER.info("Reach section got verified with actual reach count & description before applying filters");
		
	}

	
	/**
	 * Method to handle calculate button
	 * @return
	 */
	public Integer handleCalculateButton() {
	    Integer totalDevices = 0;
	    try {
	        scrollToGroupsPage("calculateButton");
	        actionClickOfGroupsPage("calculateButton");
	        Thread.sleep(2000); // Wait for action completion
	        
	        String actualAudience = getTextOfWEPGroupsPage("devicesActive");
	        
	        // Remove " out of XXXX" from the string
	        Pattern pattern = Pattern.compile(" out of [\\d,]+");
	        Matcher matcher = pattern.matcher(actualAudience);
	        String replacedVal = matcher.replaceAll("");

	        // Remove comma if present (e.g., "1,234" to "1234")
	        if (replacedVal.contains(",")) {
	            replacedVal = replacedVal.replace(",", "");
	        }

	        totalDevices = Integer.parseInt(replacedVal);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return totalDevices;
	}

	

	/**
	 * Method to verify the step3 for dynamic group creation page
	 * @param languageCode
	 * @param providedGroupName
	 * @param providedGroupDescription
	 * @param index
	 * @return
	 */
	public List<String> verifyStep3DynamicCreationFlow(String languageCode, String providedGroupName, String providedGroupDescription, int index) {
	    List<String> thirdStep = new ArrayList<>();
	    try {
	        if (getTextOfWEPGroupsPage("reviewMemberShipValue").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "group.dynamic_rules")) &&
	            getTextOfWEPGroupsPage("reviewGroupNameValue").equalsIgnoreCase(providedGroupName) &&
	            getTextOfWEPGroupsPage("reviewGroupDescValue").equalsIgnoreCase(providedGroupDescription)) {
	        	String devicesOfWEPGroupsPage = getTextOfWEPGroupsPage("reviewDeviceCount");
	        	scrollOnGroupsPage("reviewRulesHeader");
	            thirdStep.add(getspecificIndexValueofGroupsPage("reviewPropertyValue", index));
	            thirdStep.add(getspecificIndexValueofGroupsPage("reviewOperatorValue", index));
	            thirdStep.add(getspecificIndexValueofGroupsPage("reviewValue", index));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return thirdStep;
	}
	
	/**
	 * File Import in V3
	 * @param filename
	 * @throws Exception
	 */
	public void fileImportInV3(String fileName) throws Exception {
		StringSelection path = new StringSelection(fileName);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);   
		Robot robot = new Robot();
		robot.setAutoDelay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		sleeper(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	/**
	 * Upload user via CSV
	 * @param inviteUserFirstName
	 * @param inviteUser_email
	 * @throws Exception
	 */
	public void uploadCSVFunctionalityOnWEPGroupsPage(List<String> devicelist) throws Exception {
		CSVFileReader csvFileReader = new CSVFileReader();
		File csvFile = new File(ConstantPath.IMPORT_PATH + WEPPGroupsPageVariables.Import_CSV_UPLOAD);
		csvFileReader.writeDataCSVmultiple(csvFile, devicelist);
		//String Filename = WEPPGroupsPageVariables.Import_CSV_UPLOAD;
		//fileImportInV3(ConstantPath.IMPORT_PATH + WEPPGroupsPageVariables.Import_CSV_UPLOAD);
		addDevicestogroupViaCSV("uploadCSVFileOptionField",ConstantPath.IMPORT_PATH + WEPPGroupsPageVariables.Import_CSV_UPLOAD);
		}
		
	
	/**
	 * This method is to verify the audience section via CSV upload method on Pulse Creation Page
	 * @param LanguageCode
	 * @param methodValues
	 * @param csvMethod
	 * @param devicelist
	 * @return
	 */
		public boolean verifyAudienceSectionValidationsTargetCSVmethod(String LanguageCode, String propertyDropDown, String propertyValue, List<String> devicelist) {
		    boolean flag = false;
		    try {
		    	waitForElementsOfGroupsPage("selectCSVFileOptonin_StaticGroup");
				actionClickOfGroupsPage("selectCSVFileOptonin_StaticGroup");
				sleeper(5000);
				waitForPageLoaded();
				actionClickOfGroupsPage(propertyDropDown);
				actionClickOfGroupsPage(propertyValue);
		    	uploadCSVFunctionalityOnWEPGroupsPage(devicelist);
		} catch (Exception e) {
	        LOGGER.error("Exception in verifyAudienceSectionValidationsTargetCSVmethod: ", e);
	        return false;
	    }
	    return flag;
	}


		public boolean verifyNotificationForGroups(String LanguageCode, String groupName) {
		    int maxretrycnt = 15;
		    try {
		        clickOnElementsOfGroupsPage("notificationIcon");
		        clickOnElementsOfGroupsPage("notificationOption");
		        String expectedHeader = getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title");
		        for (int attempt = 1; attempt <= maxretrycnt; attempt++) {
		        	String actualHeader = getTextOfWEPGroupsPage("topNotificationHeader");
		        	String notificationValue = getTextOfWEPGroupsPage("topNotificationValue");
		        	if (actualHeader.equalsIgnoreCase("Import/Export Actions") && notificationValue.contains(groupName)) {
		        		return true;
		        	} 
		            sleeper(5000); // Wait before retrying
		        }
		    } catch (Exception e) {
		        LOGGER.error("Exception in verifyNotificationAfterExport: ", e);
		        return false;
		    }
		    return false;
		}
		
		 /**
	     * This is a method to clear the filter if present in UI
	     */
	    public void clearFiltersOfGroupsListPage(String clearFilterKey) throws Exception {
	        clearFilters(GroupsPageProperties.getProperty(clearFilterKey));
	    }
	    
	    
	    /**
	     * This method is to sort the provided column of Groups list page.
	     * @param sortHeader
	     * @param headerText
	     * @return
	     */
	    public boolean verifySortingForDifferentHeaderFields(String sortHeader, String headerText) {
	    boolean flag = false;
	    try {
	    	actionClickOfGroupsPage(sortHeader);
	    	System.out.println(getAttributesOfWEPGroupsPage(sortHeader,"aria-label"));
	    	System.out.println("sorted by "+ headerText+ " ascending");
	    	if(getAttributesOfWEPGroupsPage(sortHeader,"aria-label").equals("Ascending sorted")) {
	    		actionClickOfGroupsPage(sortHeader);
	    		if(getAttributesOfWEPGroupsPage(sortHeader,"aria-label").equals("Descending sorted")) {
	    			flag = true;
	    		}
	    	}else if(getAttributesOfWEPGroupsPage(sortHeader,"aria-label").equals("Descending sorted")) {
    			flag = true;
	    	}
	    } catch (Exception e) {
	        LOGGER.error("Exception in verifyLatestCreatedGroupsAfterSorting: ", e);
	        return false;
	    }
			return flag;
	    }
	    
	    public final void clickspecificIndexElementsOfGroupsPage(String key, int index) throws Exception {
	    	actionClick(GroupsPageProperties.getProperty(key) + "[position()=" + index + "]");
	    }

	    
	    /**
	     * This method is to create a static group with device list selction method
	     * @param SG_GroupName
	     * @param SG_GroupDescription
	     * @param maxSelectionCount
	     * @return
	     * @throws Exception
	     */
	    public String staticGroupCreationDeviceList(String SG_GroupName, String SG_GroupDescription, Integer maxSelectionCount) throws Exception {
			enterTextForGroupsPage("groupNameField",SG_GroupName);
			waitForPageLoaded();
			LOGGER.info("Group Name is entered");
			sleeper(2000);
			enterTextForGroupsPage("groupDescriptionField",SG_GroupDescription);
			LOGGER.info("Group Description is entered");
			clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
			waitForPageLoaded();
			sleeper(3000);
			waitForElementsOfGroupsPage("selectDeviceListin_StaticGroup");
			actionClickOfGroupsPage("selectDeviceListin_StaticGroup");
			sleeper(5000);
			waitForPageLoaded();
			clearFiltersOfGroupsListPage("clearListPageGroupCreation");
			for (int DevSec=1;DevSec<maxSelectionCount;DevSec++ )
			{
				clickspecificIndexElementsOfGroupsPage("deviceListCheckBoxes",DevSec);
			}
			clickOnElementsOfGroupsPage("nextBtnOnAddGroupPage");
			waitForPageLoaded();
			sleeper(2000);
			String deviceCount= getTextOfWEPGroupsPage("deviceCountatReviewPage");
			clickOnElementsOfGroupsPage("addGrpBtn");
			return deviceCount;
	    }
	    
	    /**
	     * This method is to extract the text from groups page
	     * @param sentence
	     * @param textExtract
	     * @return
	     */
	    public String textExtractofGroupsPage (String sentence, String textExtract) {
	    	try {
	            // Debugging: Print the input values
	            System.out.println("Sentence: " + sentence);
	            System.out.println("Pattern: " + textExtract);

	            Pattern pattern = Pattern.compile(textExtract);
	            Matcher matcher = pattern.matcher(sentence);
	            if (matcher.find()) {
	                System.out.println("Match Found: " + matcher.group()); // Debugging: Print the matched group
	                return matcher.group(); // Return the matched group
	            } else {
	                System.out.println("No Match Found"); // Debugging: Indicate no match
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); // Print any exceptions for debugging
	        }
	        return null; // Return null if no match is found
	    }public final boolean verifyGroupDetailNavigation(String languageCode) throws Exception {
	        // Verify the serial number search box is present
	        if (!verifyElementIsPresentOnGroupsPage("groupNameColumn")) {
	            LOGGER.error("Group number search box not found.");
	            return false;
	        }

	        // Wait for serial number column values to be visible
	        if (waitForElementsOfGroupsPage("groupNumberColumnValues")) {
	            List<WebElement> serialNumberElements = getElementsOfGroupsPage("groupNumberColumnValues");
	            if (serialNumberElements != null && !serialNumberElements.isEmpty()) {
	                serialNumberElements.get(0).click();
	                LOGGER.info("Clicked on the first Group number element.");
	                return true;
	            } else {
	                LOGGER.error("No Group number elements found.");
	            }
	        } else {
	            LOGGER.error("Group number column values not visible.");
	        }
	        return false;
	    }

	      
}