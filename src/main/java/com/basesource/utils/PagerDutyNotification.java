package com.basesource.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Set;
import org.json.JSONObject;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.basesource.action.CommonMethod;

/**
 *
 *
 * This class contains code to notify via pagerduty whenever there are any failures or resolution of failures.
 *
 */
public class PagerDutyNotification {

	private final static Logger pagerDutyLogger = LogManager.getLogger(PagerDutyNotification.class);
	static HttpURLConnection con;
	static String entityId=null;
	static URL url;
	static String apiUrl = "https://alert.victorops.com/integrations/generic/20131114/alert/f20e4ab3-9470-442d-a6ed-a838965028b5/automation-selenium-alerts";
	static String apiToken = "r_4aoc_A6QCCHCKYvdJe";
	static String serviceKey = "automation-selenium-alerts";
	static String tokenString = "Token token=r_4aoc_A6QCCHCKYvdJe";
	static String serviceKeyV3 = "automation-selenium-alerts";
	public static String environment = System.getProperty("environment");
	private static Properties pagerDutyProperties = new Properties();

	/**
	 * Method to set post request.
	 */
	public static void setPostRequest() {
		try {
			url = new URL(apiUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			//con.setRequestProperty("Authorization", tokenString);
			con.setDoOutput(true);
		} catch (Exception e) {
			System.out.println("inside setPostRequest() exception: " + e.getMessage());
			pagerDutyLogger.error("Exception occured in setting post request : " + e.getMessage());
		}
	}

	/**
	 * Method to get response of notification.
	 *
	 * @param body
	 */
	public static void getResponse(String body) {
		try {
			byte[] outputInBytes = body.getBytes("UTF-8");
			OutputStream os = con.getOutputStream();
			os.write(outputInBytes);
			os.close();
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				InputStream is = con.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String line;
				StringBuilder jsonResponse = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					jsonResponse.append(line);
				}
				reader.close();
				JSONObject jsonObject = new JSONObject(jsonResponse.toString());
				//print json object	print jsonObject
				pagerDutyLogger.info("Response for jsonobject"+jsonObject.toString() );

				entityId = jsonObject.getString("entity_id");
				pagerDutyLogger.info("Response for pagerduty notification is Successful");
			} else {
				pagerDutyLogger.error("Response for pagerduty notification is Failed");
			}

		} catch (Exception e) {
			pagerDutyLogger.error("Exception occured in Test getResponse : " + e.getMessage());
		}

	}
	/**
	 * Publishes a message to an Amazon SNS topic.
	 *
	 * @param topicArn The ARN of the Amazon SNS topic to which the message should be published.
	 * @param message The message to be published to the Amazon SNS topic.
	 */
	public static void publishToSnsTopic(String topicArn, String message) {

		AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
		PublishRequest publishRequest = new PublishRequest(topicArn, message);
		PublishResult publishResult = snsClient.publish(publishRequest);
		pagerDutyLogger.info("MessageId: " + publishResult.getMessageId());
	}
	/**
	 * Method to be called in case of resolution of test case failure.
	 */
	public static void pagerDutyResolvedStatus(String groupName) {
		try {
			String entityId= CommonMethod.getEntityId();
			entityId = entityId + "-" + groupName;
			String body = null;
			setPostRequest();
			if(System.getProperty("uiVersion").equalsIgnoreCase("VENEER3")&&(System.getProperty("environment").equalsIgnoreCase("US-PRODUCTION")||System.getProperty("environment").equalsIgnoreCase("EU-PRODUCTION")||System.getProperty("environment").equalsIgnoreCase("US-PROD-WEP")||System.getProperty("environment").equalsIgnoreCase("EU-PROD-WEP"))){
							body = "{\"entity_id\" : \"" + entityId + "\",\"message_type\" : \"RECOVERY\", " + "\"entity_display_name\" : \"Selenium Production issue resolved(Veneer3) \", \"state_start_time\" : \"" + LocalDateTime.now() + "\"}";
				 			} else{
				body = "{\"service_key\" : \"" + serviceKey + "\",\"incident_key\" : \"https://daas.com \", \"event_type\" : \"resolve\", " + "\"description\" : \"Selenium Production issue resolved \", \"details\" : \"" + LocalDateTime.now() + "\"}";

			}
			getResponse(body);

		} catch (Exception e) {
			pagerDutyLogger.error("Exception occured in Test pagerDutyResolvedStatus : " + e.getMessage());
		}
	}

	/**
	 * Method to be called in case of test case failure.
	 */
	public static void pagerDutyErrorStatus(String testName,String failureReason,Set<String> failedTransactionID,String groupName) {
		try {
			setPostRequest();
			String failureMessage = null;
			String failureMessageFull = failureReason.replaceAll("\n"," ").replaceAll("\\s+"," ");
			String failureMessageClean = failureMessageFull.replaceAll("\"", "");
			String environment = System.getProperty("environment");
			String entityId= CommonMethod.getEntityId();
			entityId = entityId + "-" + groupName;

			if(failureMessageClean.length() > 200){
				failureMessage = failureMessageClean.substring(0,200);
			}
			else{
				failureMessage = failureMessageClean;
			}
			if(System.getProperty("uiVersion").equalsIgnoreCase("VENEER3")&&(System.getProperty("environment").equalsIgnoreCase("US-PRODUCTION")||System.getProperty("environment").equalsIgnoreCase("EU-PRODUCTION")||System.getProperty("environment").equalsIgnoreCase("US-PROD-WEP")||System.getProperty("environment").equalsIgnoreCase("EU-PROD-WEP")))
			{
				Map<String, String> stateMessage = new HashMap<>();
				stateMessage.put("Test Case Name", testName);
				stateMessage.put("Environment", environment);
				stateMessage.put("Failure Reason", failureMessage);
				stateMessage.put("Transaction ID for failed testcase", failedTransactionID.toString());
				stateMessage.put("Group Name", groupName);

				JSONObject jsonObject = new JSONObject(stateMessage);

				Map<String, String> body = new HashMap<>();
				body.put("message_type", "CRITICAL");
				body.put("entity_display_name", "HP Selenium Production Monitoring - Selenium Automation(Veneer3)");
				body.put("entity_id", entityId);
				body.put("state_message", jsonObject.toString());

				JSONObject jsonObjectBody = new JSONObject(body);
				pagerDutyLogger.info(jsonObjectBody.toString());
				getResponse(jsonObjectBody.toString());

			}else{
				String body = "{\r\n" +
						"  \"service_key\": \"2b17342736044047a2267b763f692d25\",\r\n" +
						"  \"event_type\": \"trigger\",\r\n" +
						"  \"incident_key\": \"https://daas.com \",\r\n" +
						"  \"description\": \"HP TechPulse Production Monitoring - Selenium Automation \",\r\n" +
						"  \"details\": {\r\n" +
						"  \"Test Case Name\" : \"" + testName + "\",\r\n" +
						"  \"Environment\" : \"" + environment + "\",\r\n" +
						"  \"Failure Reason\": \"" + failureMessage + "\",\r\n" +
						"  \"Transaction ID for failed testcase\" : \"" + failedTransactionID + "\"\r\n" +
						"  },\r\n" +
						"  \"client_url\": \"https://daas.com \"\r\n" +
						"\r\n" +
						" \r\n" +
						"\r\n" +
						"}";
				getResponse(body);
				pagerDutyLogger.info(body);
			}
		} catch (Exception e) {
			pagerDutyLogger.error("Exception occured in Test pagerDutyErrorStatus : " + e.getMessage());
		}
	}


}
