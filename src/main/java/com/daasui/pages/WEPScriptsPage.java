package com.daasui.pages;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.ScriptVariables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.testng.Assert;
import org.json.JSONObject;

public class WEPScriptsPage extends CommonMethod{
    private WEPScriptsPage instance;
    private ObjectReader WEPScriptsPagePropertiesReader = new ObjectReader();
    private Properties WEPScriptsPageProperties;
    private final static Logger LOGGER = LogManager.getLogger(WEPScriptsPage.class);
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");


    public WEPScriptsPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPScriptsPage.class) {
                if (instance == null) {
                    instance = new WEPScriptsPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEPScriptsPage(WebDriver driver) throws IOException {
        WEPScriptsPageProperties = WEPScriptsPagePropertiesReader.getObjectRepository("WEPScriptsPage");
    }
    public final boolean verifyElementsOfScriptsPage(String key) throws Exception {
        return verifyElementIsPresent(WEPScriptsPageProperties.getProperty(key));
    }
    public final void clickOnElementsOfScriptsPage(String key) throws Exception {
        click(WEPScriptsPageProperties.getProperty(key));
    }
    public final void mouseHoverAndClickOfScriptsPage(String key) throws Exception {
        actionClick(WEPScriptsPageProperties.getProperty(key));
    }
    public final void actionClickOfScriptsPage(String key) throws Exception {
        actionClick(WEPScriptsPageProperties.getProperty(key));
    }
    public final List<WebElement> getElementsOfScriptsPage(String key) throws Exception {
        return getAllElements(WEPScriptsPageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnScriptsPage(String key) throws Exception {
        clickByJavaScript(WEPScriptsPageProperties.getProperty(key));
    }

    public final WebElement getElementOfScriptsPage(String key) throws Exception {
        return getElement(WEPScriptsPageProperties.getProperty(key));
    }

    public void scrollOnScriptsPage(String key) throws Exception {
        scrollTillView(WEPScriptsPageProperties.getProperty(key));
    }

    public final void listMouseHoverOnScriptsPage(String key) throws Exception {
        listMouseHover(WEPScriptsPageProperties.getProperty(key));
    }

    public final void enterTextOnScriptsPage(String key, String Text) throws Exception {
        enterText(WEPScriptsPageProperties.getProperty(key), Text);
    }

    public final void enterTextWithoutClearOnScriptsPage(String key, String Text) throws Exception {
        enterTextwithoutclear(WEPScriptsPageProperties.getProperty(key), Text);
    }

    public final void scrollTillViewScriptsPage(String key) throws Exception {
        scrollTillView(WEPScriptsPageProperties.getProperty(key));
    }

    public final void enterTextByJavaScriptOnScriptsPage(String key, String Text) throws Exception {
        enterTextUsingJavaScript(WEPScriptsPageProperties.getProperty(key), Text);
    }

    public final boolean verifyTextPresentOnElementScriptsPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEPScriptsPageProperties.getProperty(key), Text);
    }

    public final boolean verifyElementIsDisplayedOfScriptsPage(String key) throws Exception {
        return verifyElementIsVisible(WEPScriptsPageProperties.getProperty(key));
    }

    public final void onChangeCalendarEventOfScriptsPage(String key) throws Exception {
        onChangeCalendarEvent(WEPScriptsPageProperties.getProperty(key));
    }


    /**
     * This method is used to browse and upload the script file
     */
    public void importScriptFile(String fileName) throws Exception {
        sleeper(2000);
        WebElement addFile = getElementOfScriptsPage("browseInput");
        addFile.sendKeys(fileName);
        //clickByJavaScriptOnScriptsPage("uploadBulkDeviceBtn");
        sleeper(3000);
    }

    /**
     * This method is used to verify the table columns
     * @param expectedColumnList
     * @return
     * @throws Exception
     */
    public boolean verifyTableColumns(List<String> expectedColumnList, String key) throws Exception {
        boolean flag = false;
        int counter = 0;
        try {
            List<WebElement> actualColumnList = getElementsOfScriptsPage(key);
            for (WebElement we : actualColumnList)

                if(we.getText().contains("sorted")){
                    String[] split = we.getText().split("\n");
                    String columnName = split[0];

                    if (columnName.equalsIgnoreCase(expectedColumnList.get(counter))) {
                        flag = true;
                        counter++;
                    } else {
                        flag = false;
                        LOGGER.error(we.getText() + " Header does not match at list table page.");
                        break;
                    }
                }
                else if (we.getText().equalsIgnoreCase(expectedColumnList.get(counter))) {
                    flag = true;
                    counter++;
                } else {
                    flag = false;
                    LOGGER.error(we.getText() + " Header does not match at list table page.");
                    break;
                }
        } catch (Exception e) {
            LOGGER.error("Error while verifying table columns" + e.getMessage());
        }
        return flag;
    }


    /* This is a method to match text on an element
     *
     * @param key         - Locator of element
     * @param textToMatch - Text to be matched
     * @return - boolean value of whether the text present on element matches or not
     */
    public final boolean matchTextOfScriptsPage(String key, String textToMatch) {
        try {
            return verifyTextPresentOnElement(WEPScriptsPageProperties.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method matchTextOfDeviceListPage {}", e.getMessage());
            return false;
        }

    }
    /**
     * This method used to check is any value exist on the extracted web element
     * @param locator - Web element locator key
     * @return - either true/false if value exist
     * @throws Exception - On exception
     */
    public boolean anyValueExists(String locator) throws Exception {
        return !getElementOfScriptsPage(locator).getText().isEmpty();
    }

    /**
     * This method used to extract the text of a web element
     * @param locator - Web element locator key
     * @return - String webelement
     */
    public String getTextOfScriptsPageElement(String locator) throws Exception {
        return getTextBy(WEPScriptsPageProperties.getProperty(locator));
    }

    /**
     * This method used to get the attribute of the scripts page
     *
     * @param locator - Web element locator key
     * @param attributeName - attribute of web element
     * @return - String attribute of web element
     */
    public String getAttributeOfScriptsPageElement(String locator, String attributeName) throws Exception {
        return getAttribute(WEPScriptsPageProperties.getProperty(locator), attributeName);
    }

    /**
     * This method used to send keys
     *
     * @param key - Web element locator key
     * @param value - value to send to webelement
     */
    public final void sendKeysOnWEPScriptsPage(String key, String value) throws Exception {
        enterTextWithoutClearOnScriptsPage(key, value);
        onChangeCalendarEventOfScriptsPage(key);
    }

    /**
     * This method delete the schedule in assignment details and verify toast message
     *
     */
    public void deleteScheduleAndVerifyToast() throws Exception {
        verifyElementsOfScriptsPage("assignmentDetailScheduleEditBtn");
        mouseHoverAndClickOfScriptsPage("assignmentDetailScheduleEditBtn");
        verifyElementsOfScriptsPage("assignmentDetailsScheduleDeleteBtn");
        clickOnElementsOfScriptsPage("assignmentDetailsScheduleDeleteBtn");
        verifyElementsOfScriptsPage("assignmentDetailsScheduleDeleteConfirmBtn");
        mouseHoverAndClickOfScriptsPage("assignmentDetailsScheduleDeleteConfirmBtn");
        sleeper(2000);
        verifyElementsOfScriptsPage("assignmentDetailsScheduleToast");
        Assert.assertTrue(matchTextOfScriptsPage("assignmentDetailsScheduleToast", ScriptVariables.ASSIGNMENTSCHEDULE_TOAST_DELETEMSG), "Schedule Delete toast message did not appear");
        clickOnElementsOfScriptsPage("assignmentDetailsScheduleUpdateToastClose");
    }

    /**
     * This method save edited schedule in assignment details and verify toast notification message
     *
     */
    public void saveEditedAssignmentAndVerifyToast() throws Exception {
        verifyElementsOfScriptsPage("assignmentDetailsScheduleEditSaveBtn");
        mouseHoverAndClickOfScriptsPage("assignmentDetailsScheduleEditSaveBtn");
        sleeper(2000);
        verifyElementsOfScriptsPage("assignmentDetailsScheduleToast");
        Assert.assertTrue(matchTextOfScriptsPage("assignmentDetailsScheduleToast",ScriptVariables.ASSIGNMENTSCHEDULE_TOAST_UPDATEMSG), "Schedule Update toast message did not appear");
        clickOnElementsOfScriptsPage("assignmentDetailsScheduleUpdateToastClose");
    }

    /**
     * This method clears the filter if any exist
     * @throws Exception
     */
    public void clearScriptFilter() throws Exception {
        if(verifyElementsOfScriptsPage("clearFilter")){
            clickOnElementsOfScriptsPage("clearFilter");
        }
    }

    /**
     *This method deletes the schedule if  it exists
     *
     * @throws Exception
     */
    public void deleteScheduleIfExist() throws Exception {
        if(!verifyElementsOfScriptsPage("assignmentDetailAddScheduleBtn")){
            deleteScheduleAndVerifyToast();
        }
    }

    /**
     * This method extracts the data of schedule and store it in the map to verify against the modified data
     * @param scheduleDetails is a map which carries the data extracted from th ui element
     * @throws Exception
     */
    public void extractScheduleDetailsToVerify(HashMap<String, String> scheduleDetails) throws Exception {
        scheduleDetails.put("Frequency", getTextOfScriptsPageElement("assignmentDetailsScheduleEditFrequencyValue"));
        scheduleDetails.put("RecurringDays", getTextOfScriptsPageElement("assignmentDetailsScheduleEditRecurringDaysValue"));
        scheduleDetails.put("ScheduleStartDate", getAttributeOfScriptsPageElement("assignmentDetailsScheduleEditStartDateValue", "value"));
        scheduleDetails.put("ScheduleStartTime", getAttributeOfScriptsPageElement("assignmentDetailsScheduleEditStartTimeValue", "value"));
    }

    /**
     * This method verifies the data extracted earlier from the ui against the schedule after modification
     * @param scheduleDetails contains all te extracted details form the UI
     * @return true if all assertion is true/passed else it will return false if any fails in validating the content
     */
    public boolean VerifySchedulePostEditing(HashMap<String, String> scheduleDetails) {
        boolean allAssertionPassed = true;
        try {
            Assert.assertTrue(matchTextOfScriptsPage("assignmentDetailsScheduleFrequencyLabel","Frequency"),"Label of Schedule Frequency text not matching");
            Assert.assertTrue(matchTextOfScriptsPage("assignmentDetailsScheduleFrequencyValue", scheduleDetails.get("Frequency")),"Value of Schedule Frequency doesn't match which edited/added");
            Assert.assertTrue(matchTextOfScriptsPage("assignmentDetailsScheduleStartDateLabel","Start Date"),"Label of Schedule Start Date text not matching");
            //Assert.assertTrue(matchTextOfScriptsPage("assignmentDetailsScheduleStartDateValue", scheduleDetails.get("ScheduleStartDate")),"Value of Schedule Start Date doesn't match which edited/added");
            Assert.assertTrue(matchTextOfScriptsPage("assignmentDetailsScheduleStartTimeLabel","Start Time"),"Label of Schedule Start Time text not matching");
            Assert.assertTrue(getTextOfScriptsPageElement("assignmentDetailsScheduleStartTimeValue").equalsIgnoreCase(scheduleDetails.get("ScheduleStartTime")),"Value of Schedule Start Time doesn't match which edited/added");
            Assert.assertTrue(matchTextOfScriptsPage("assignmentDetailsScheduleRecurrenceLabel","Recurring Every Day(s)"),"Label of Schedule Recurrence text not matching");
            Assert.assertTrue(matchTextOfScriptsPage("assignmentDetailsScheduleRecurrenceValue", scheduleDetails.get("RecurringDays")),"Value of Schedule Recurrence doesn't match which edited/added");
            Assert.assertTrue(matchTextOfScriptsPage("assignmentDetailsScheduleEndDateLabel", "End Date"), "Label of Schedule End Date text not matching");
            //Assert.assertTrue(matchTextOfScriptsPage("assignmentDetailsScheduleEndDateValue", scheduleDetails.get("EndDate")), "Value of Schedule End Date doesn't match which edited/added");
            Assert.assertTrue(matchTextOfScriptsPage("assignmentDetailsScheduleEndTimeLabel", "End Time"), "Label of Schedule End Time text not matching");
            Assert.assertTrue(getTextOfScriptsPageElement("assignmentDetailsScheduleEndTimeValue").equalsIgnoreCase(scheduleDetails.get("EndTime")), "Value of Schedule End Time doesn't match which edited/added");
        } catch (Exception e) {
            LOGGER.error("Error while verifying schedule details" + e.getMessage());
            allAssertionPassed = false;
        }
        return allAssertionPassed;
    }

    /**
     * This method extract the start time of a schedule and add 1 hour to make it use while editing the schedule
     * @param scheduleDetails - contains the extract details of a schedule
     * @return new schedule time in the form of string to modify the schedule
     * @throws Exception if it fails to modify the extracted time or fails to extract the existing time
     */
  /*  public String generateNewScheduleStartTime(HashMap<String, String> scheduleDetails) throws Exception {
        String existingScheduleStartTime = scheduleDetails.get("ScheduleStartTime");
        LocalTime startTime = LocalTime.parse(existingScheduleStartTime.trim(), DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH));
        return startTime.plus(1, ChronoUnit.HOURS).format(DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH));
    }*/

    public String generateNewScheduleStartTime(HashMap<String, String> scheduleDetails) throws Exception {
        String existingScheduleStartTime = scheduleDetails.get("ScheduleStartTime").trim();
        System.out.println(existingScheduleStartTime);
        // Validate input format
        if (!existingScheduleStartTime.matches("\\d{1,2}:\\d{2} (am|pm|AM|PM)")) {
            throw new Exception("Invalid time format. Expected format: hh:mm am/pm");
        }

        // Convert to standard format for parsing
        existingScheduleStartTime = existingScheduleStartTime.toLowerCase().replace("am", "AM").replace("pm", "PM");

        // Remove any extra spaces
        existingScheduleStartTime = existingScheduleStartTime.replaceAll("\\s+", " ");

        LocalTime startTime = LocalTime.parse(existingScheduleStartTime, DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH));

        return startTime.plus(1, ChronoUnit.HOURS).format(DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH));
    }

    /**
     * This method extract the start date of a schedule and add 1 day to make it use while editing the schedule
     * @param scheduleDetails - contains the extract details of a schedule
     * @return new schedule time in the form of string to modify the schedule
     * @throws Exception if it fails to modify the extracted data or fails to extract the existing date
     */
    public String generateNewScheduleStartDate(HashMap<String, String> scheduleDetails)  {
        String existingDate = scheduleDetails.get("ScheduleStartDate");
        try {
            LocalDate startDateToUseAsEndDate = LocalDate.parse(existingDate, DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            return startDateToUseAsEndDate.plus(2, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        } catch(Exception e1) {
            try {
                LOGGER.info("checking if date format is MMM d, yyyy as exception occurred in MMM dd, yyyy");
                LocalDate startDateToUseAsEndDate = LocalDate.parse(existingDate, DateTimeFormatter.ofPattern("MMM d, yyyy"));
                return startDateToUseAsEndDate.plus(2, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("MMM d, yyyy"));
            } catch(Exception e2) {
                try {
                    LOGGER.info("checking if date format is dd MMM yyyy as exception occurred in previous formats");
                    LocalDate startDateToUseAsEndDate = LocalDate.parse(existingDate, DateTimeFormatter.ofPattern("dd MMM yyyy"));
                    return startDateToUseAsEndDate.plus(2, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
                } catch(Exception e3) {
                    LOGGER.info("checking if date format is d MMM yyyy as exception occurred in previous formats");
                    LocalDate startDateToUseAsEndDate = LocalDate.parse(existingDate, DateTimeFormatter.ofPattern("d MMM yyyy"));
                    return startDateToUseAsEndDate.plus(2, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("d MMM yyyy"));
                }
            } 
        }
    }

    /**
     * This method takes the new times and modifies the schedule
     *
     * @param scheduleTimeTag -locator of schedule time
     * @param newTime -new time to be modified to
     * @param scheduleDetails- contains the extract details of a schedule
     * @param ScheduleStartTime- start time of a schedule
     * @throws Exception
     */
    public void modifyScheduleTime(String scheduleTimeTag, String newTime, HashMap<String, String> scheduleDetails, String ScheduleStartTime) throws Exception {
        List<WebElement> scheduleTimes = getElementsOfScriptsPage(scheduleTimeTag);
        for (WebElement scheduleTime : scheduleTimes) {
            String actualTime = scheduleTime.getText();
            if (actualTime.matches("^\\d:\\d{2} (AM|PM|am|pm)$")) {
                actualTime = "0" + actualTime;
            }      
            if (actualTime.equalsIgnoreCase(newTime)) {
                clickWebelement(scheduleTime);
                newTime = newTime.startsWith("0") ? newTime.substring(1) : newTime;
                scheduleDetails.replace(ScheduleStartTime, newTime);
                break;
            }
        }
    }

    /**
     * This method is used to select value from a dropdown on Scripts page
     *
     * @param dropdownId - locator of dropdown options
     * @param text - current value on dropdown
     * @return - newly selected value on dropdown
     * @throws Exception
     */
    public final boolean selectValueOnDropDownOfScriptsPage(String dropdownId, String text) throws Exception {
        return selectValueFromDropdown(text,getElementsOfScriptsPage(dropdownId));
    }

    /**
     * This is a method to select all checkboxes of the table options popup
     *
     * @throws Exception
     */
    public void selectAllTableColumns() throws Exception {
        TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
        tableConfigurationPage = tableConfigurationPage.getInstance();
        sleeper(4000);
        clearScriptFilter();
        tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPage("tableConfigurationButton");
        tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
        sleeper(2000);
        tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
        tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
    }

    /**
     * This is a method to get sorting order
     *
     * @cparam columnSortStatus element pointing to the column wanted to be sorted
     * @param orderWanted "asc" or "desc"
     * @return - true if the sort is descending, false otherwise
     */
    public void orderColumnAscOrDesc(String columnSortStatus, String orderWanted) throws Exception {
        clickOnElementsOfScriptsPage(columnSortStatus);
        String[] result =  getTextOfScriptsPageElement(columnSortStatus).split("\n");
        if(result[1].equals("Descending sorted") && orderWanted.equalsIgnoreCase("Asc")){
            clickOnElementsOfScriptsPage(columnSortStatus);
        }
        else if(result[1].equals("Ascending sorted") && orderWanted.equalsIgnoreCase("Desc")){
            clickOnElementsOfScriptsPage(columnSortStatus);
        }
    }

    /**
     * This function is used to validate parameters of IOT job payload
     * @param jsonString
     * @param expectedValue
     * @return
     */
    public static boolean verifyIOTJobPayload(String jsonString, String expectedValue, String key) {
        try {
            // Parse the outer JSON object
            JSONObject jsonObject = new JSONObject(jsonString);

            // Navigate to "parameters" inside "state" -> "desired"
            String parametersStr = jsonObject.getJSONObject("state")
                    .getJSONObject("desired")
                    .getString("parameters");

            // Parse "parameters" as a JSON object
            JSONObject parameters = new JSONObject(parametersStr);

            // Ensure "updateType" key exists and is non-null
            if (!parameters.has(key) || parameters.isNull(key)) {
                return false; // Key is missing or explicitly null
            }
            // Perform comparison with the expected value
            return expectedValue.equals(parameters.getString(key));
        } catch (Exception e) {
            return false; // Handle JSON parsing or key-not-found exceptions
        }
    }

    /**
     * This method deletes the device from system using DRS API
     * @param deviceId
     * @param host
     * @return
     * @throws IOException
     */
    public boolean deleteTheDeviceFromDynamicGroup(String deviceId, String host) throws IOException {
        String bearerToken = getTokenFromLocalStorage("dui_token_e");
        String apiUrl = host + "services/drs/1.0/devices/bulk-delete?isAsset=true";
        URL url = new URL(apiUrl);

        // Make a device delete post call
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("web-proxy.in.hpicorp.net", 8080));
        HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + bearerToken);
        String body = "{\"resources\":[\"" + deviceId + "\"]}";
        con.setDoOutput(true);
        byte[] outputInBytes = body.getBytes("UTF-8");
        OutputStream os = con.getOutputStream();
        os.write(outputInBytes);
        os.close();
        // Get the response code for the device unenrollment call
        int responseCode = con.getResponseCode();
        LOGGER.info("Making a device delete call on the API : " + apiUrl + " Response code for device DELETE: " + responseCode);
        // Validation for the device unenrollment API response code
        if (responseCode == 202) {
            return true;
        }
        return false;

    }
    
    /**
    *This method is used to extract the hour from current time
    **/
    public String getCurrentHour() {
    	Long time = (System.currentTimeMillis()/3600000)*3600000;
    	String epochString = String.valueOf(time);
        String firstThreeDigits = epochString.substring(0, 3); 
        return firstThreeDigits ;
    }

    /**
     * Method to assert that a value is not blank or null
     * @param actualValue
     *
     */
    public void assertValueNotBlankOrNull(String actualValue) {
        Assert.assertNotNull(actualValue,  " value should not be null");
        Assert.assertFalse(actualValue.trim().isEmpty(), " value should not be empty");
    }
    
	public final boolean waitForElementsOfScriptsPage(String key) throws Exception {
		return verifyElementIsVisible(WEPScriptsPageProperties.getProperty(key));
	}
	
	 public final void clearTextOnScriptsPage(String key) throws Exception {
	        clearText(WEPScriptsPageProperties.getProperty(key));
	    }
}