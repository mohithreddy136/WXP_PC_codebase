package com.basesource.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.basesource.action.MailinatorInboxFormat;
import com.basesource.action.MailinatorMail;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;

/**
 * This class basically a wrapper for MailinatorMail and MailinatorInboxFormat This has the reusable APIs to perform mailinator operation for testing this includes 1. Getting the
 * email of an inbox(inboxEmailAddress) 2. Getting any specific email out of those email in the inbox All the mailinator functionality can be put here in future
 * 
 * @author kumargup
 *
 */
public class Mailinator {
	private Properties selectedLanguageProperties;
	private ObjectReader emailPropertiesReader = new ObjectReader();
	private static Logger log = LogManager.getLogger(Mailinator.class);
	private String mailinatorToken;
	/**
	 * Constructor requires
	 *
	 * @param mailinatorToken mailinator token for accessing mailinator rest APIs(Required and mandatory) In case you have it then use it otherwise pass "" string as argument it will
	 *            use the value defined in the CommonVariable.java
	 * @param inboxEmailAddress user whose email need to be accessed for performing APIs operations
	 */
	public Mailinator(String mailinatorToken, String inboxEmailAddress) {
		if (!mailinatorToken.trim().equals("")) {
			this.mailinatorToken = mailinatorToken;
		} else {
			this.mailinatorToken = CommonVariables.MAILINATOR_TOKEN;
		}
	}

	/**
	 * Support method to be used for getting localized text strings
	 *
	 * @param language
	 * @param localefolder
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = emailPropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * Method is used for getting all email of an inbox
	 * @return arraylist of email
	 * @param mailId this is mail id of which we need to fetch mails
	 * @param privateDomain flag which will define if we want to hit mailinator private inbox or public inbox
	 * @throws Exception
	 */

	public ArrayList<MailinatorInboxFormat> getEmailsOfInbox(String mailId, boolean privatedomain) {
		ArrayList<MailinatorInboxFormat> objEmailInbox = new ArrayList<MailinatorInboxFormat>();
		try {
			String mailinatorInboxURL = ConstantURL.MAILINATOR_BASE_URL + "/inbox?token=%s&to=%s&private_domain=%b";

			String emailUrl = String.format(mailinatorInboxURL, mailinatorToken, mailId, privatedomain);
			URL url = new URL(emailUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("accept", "application/json");
			int responseCode = con.getResponseCode();
			log.info("Sending 'GET' request to URL : " + url + " And Responce code is: " + responseCode);
			String response = getResponse(responseCode, con);
			JSONObject json = new JSONObject(response);
			JSONArray jsonObj2 = json.getJSONArray("messages");
			if (jsonObj2.length() > 0) {
				for (int i = 0; i < jsonObj2.length(); i++) {
					MailinatorInboxFormat objTempMailinatorInboxFormat = new MailinatorInboxFormat();
					objTempMailinatorInboxFormat.setToOfEmail(jsonObj2.getJSONObject(i).get("to").toString());
					objTempMailinatorInboxFormat.setSecondsAgoOfEmail(Long.valueOf(jsonObj2.getJSONObject(i).get("seconds_ago").toString()));
					if (objTempMailinatorInboxFormat.getToOfEmail().equalsIgnoreCase(mailId) && objTempMailinatorInboxFormat.getSecondsAgoOfEmail() > 100) {
						objTempMailinatorInboxFormat.setIdOfEmail(jsonObj2.getJSONObject(i).get("id").toString());
						deleteSpecificEmail(objTempMailinatorInboxFormat.getIdOfEmail(), privatedomain);
					} else {
						MailinatorInboxFormat objMailinatorInboxFormat = new MailinatorInboxFormat();
						if (privatedomain == true) {
							objMailinatorInboxFormat.setSubjectOfEmail(jsonObj2.getJSONObject(i).get("subject").toString());
							objMailinatorInboxFormat.setDomainOfEmail(jsonObj2.getJSONObject(i).get("domain").toString());
							objMailinatorInboxFormat.setFromNameOfEmail(jsonObj2.getJSONObject(i).get("from").toString());
							objMailinatorInboxFormat.setIdOfEmail(jsonObj2.getJSONObject(i).get("id").toString());
							objMailinatorInboxFormat.setToOfEmail(jsonObj2.getJSONObject(i).get("to").toString());
							objMailinatorInboxFormat.setSecondsAgoOfEmail(Long.valueOf(jsonObj2.getJSONObject(i).get("seconds_ago").toString()));
							objEmailInbox.add(objMailinatorInboxFormat);
							Collections.reverse(objEmailInbox);
//							break;
						} else if (privatedomain == false) {
							objMailinatorInboxFormat.setFromFullEmailAddressOfEmail(jsonObj2.getJSONObject(i).get("fromfull").toString());
							objMailinatorInboxFormat.setSubjectOfEmail(jsonObj2.getJSONObject(i).get("subject").toString());
							objMailinatorInboxFormat.setFromNameOfEmail(jsonObj2.getJSONObject(i).get("from").toString());
							objMailinatorInboxFormat.setOrigFromOfEmail(jsonObj2.getJSONObject(i).get("origfrom").toString());
							objMailinatorInboxFormat.setToOfEmail(jsonObj2.getJSONObject(i).get("to").toString());
							objMailinatorInboxFormat.setIdOfEmail(jsonObj2.getJSONObject(i).get("id").toString());
							objMailinatorInboxFormat.setTimeOfEmail(Long.valueOf(jsonObj2.getJSONObject(i).get("time").toString()));
							objMailinatorInboxFormat.setSecondsAgoOfEmail(Long.valueOf(jsonObj2.getJSONObject(i).get("seconds_ago").toString()));
							objEmailInbox.add(objMailinatorInboxFormat);
							Collections.reverse(objEmailInbox);
//							break;
						}
					}
				}
			} else {
				log.info("No email received in the response of the provided Mailinator inbox.");
			}

		} catch (Exception ex) {
			log.error("Exception thrown in getEmailsOfInbox method: " + ex.toString());
		}
		return objEmailInbox;
	}

	/**
	 * Method is used for getting all email of an inbox
	 * @return arraylist of email
	 * @param mailId this is mail id of which we need to fetch mails
	 * @param privateDomain flag which will define if we want to hit mailinator private inbox or public inbox
	 * @throws Exception
	 */

	public ArrayList<MailinatorInboxFormat> getEmailsOfInboxForPartner(String mailId, boolean privatedomain) {
		ArrayList<MailinatorInboxFormat> objEmailInbox = new ArrayList<MailinatorInboxFormat>();
		try {
			String mailinatorInboxURL = ConstantURL.MAILINATOR_BASE_URL + "/inbox?token=%s&to=%s&private_domain=%b";

			String emailUrl = String.format(mailinatorInboxURL, mailinatorToken, mailId, privatedomain);
			URL url = new URL(emailUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("accept", "application/json");
			int responseCode = con.getResponseCode();
			log.info("Sending 'GET' request to URL : " + url + " And Responce code is: " + responseCode);
			String response = getResponse(responseCode, con);
			JSONObject json = new JSONObject(response);
			JSONArray jsonObj2 = json.getJSONArray("messages");
			if (jsonObj2.length() > 0) {
				for (int i = 0; i < jsonObj2.length(); i++) {
					MailinatorInboxFormat objTempMailinatorInboxFormat = new MailinatorInboxFormat();
					objTempMailinatorInboxFormat.setToOfEmail(jsonObj2.getJSONObject(i).get("to").toString());
					objTempMailinatorInboxFormat.setSecondsAgoOfEmail(Long.valueOf(jsonObj2.getJSONObject(i).get("seconds_ago").toString()));
					if (objTempMailinatorInboxFormat.getToOfEmail().equalsIgnoreCase(mailId) && objTempMailinatorInboxFormat.getSecondsAgoOfEmail() > 40) {
						objTempMailinatorInboxFormat.setIdOfEmail(jsonObj2.getJSONObject(i).get("id").toString());

					} else {
						MailinatorInboxFormat objMailinatorInboxFormat = new MailinatorInboxFormat();
						if (privatedomain == true) {
							objMailinatorInboxFormat.setSubjectOfEmail(jsonObj2.getJSONObject(i).get("subject").toString());
							objMailinatorInboxFormat.setDomainOfEmail(jsonObj2.getJSONObject(i).get("domain").toString());
							objMailinatorInboxFormat.setFromNameOfEmail(jsonObj2.getJSONObject(i).get("from").toString());
							objMailinatorInboxFormat.setIdOfEmail(jsonObj2.getJSONObject(i).get("id").toString());
							objMailinatorInboxFormat.setToOfEmail(jsonObj2.getJSONObject(i).get("to").toString());
							objMailinatorInboxFormat.setSecondsAgoOfEmail(Long.valueOf(jsonObj2.getJSONObject(i).get("seconds_ago").toString()));
							objEmailInbox.add(objMailinatorInboxFormat);
							Collections.reverse(objEmailInbox);
							break;
						} else if (privatedomain == false) {
							objMailinatorInboxFormat.setFromFullEmailAddressOfEmail(jsonObj2.getJSONObject(i).get("fromfull").toString());
							objMailinatorInboxFormat.setSubjectOfEmail(jsonObj2.getJSONObject(i).get("subject").toString());
							objMailinatorInboxFormat.setFromNameOfEmail(jsonObj2.getJSONObject(i).get("from").toString());
							objMailinatorInboxFormat.setOrigFromOfEmail(jsonObj2.getJSONObject(i).get("origfrom").toString());
							objMailinatorInboxFormat.setToOfEmail(jsonObj2.getJSONObject(i).get("to").toString());
							objMailinatorInboxFormat.setIdOfEmail(jsonObj2.getJSONObject(i).get("id").toString());
							objMailinatorInboxFormat.setTimeOfEmail(Long.valueOf(jsonObj2.getJSONObject(i).get("time").toString()));
							objMailinatorInboxFormat.setSecondsAgoOfEmail(Long.valueOf(jsonObj2.getJSONObject(i).get("seconds_ago").toString()));
							objEmailInbox.add(objMailinatorInboxFormat);
							Collections.reverse(objEmailInbox);
							break;
						}
					}
				}
			} else {
				log.info("No email received in the response of the provided Mailinator inbox.");
			}

		} catch (Exception ex) {
			log.error("Exception thrown in getEmailsOfInbox method: " + ex.toString());
		}
		return objEmailInbox;
	}
	/**
	 * This method is used to retrieve specific email using its email id
	 *
	 * @param uniqueMailinatorMailID email id given in the email body unique to specific email
	 * @param privateDomain flag which will define if we want to hit mailinator private inbox or public inbox
	 * @return email
	 * @throws Exception
	 */
	public MailinatorMail getSpecificEmail(String uniqueMailinatorMailID,boolean privateDomain) throws Exception {
		MailinatorMail objEmail = new MailinatorMail();
		try {
			String mailinatorSpecificEmailAPI = ConstantURL.MAILINATOR_BASE_URL + "/email?token=%s&msgid=%s&private_domain=%b";
			String emailUrl = String.format(mailinatorSpecificEmailAPI, mailinatorToken, uniqueMailinatorMailID, privateDomain);
			URL url = new URL(emailUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("accept", "application/json");
			int responseCode = con.getResponseCode();
			log.info("Sending 'GET' request to URL : " + url + " And Responce code is: " + responseCode);
			String response = getResponse(responseCode, con);
			JSONObject json = new JSONObject(response);
			JSONObject jsonDataSection = (JSONObject) json.get("data");
			objEmail.setId(jsonDataSection.get("id").toString());
			objEmail.setSecondsAgo(Long.valueOf(jsonDataSection.get("seconds_ago").toString()));
			objEmail.setTo(jsonDataSection.get("to").toString());
			objEmail.setTime(Long.valueOf(jsonDataSection.get("time").toString()));
			objEmail.setSubject(jsonDataSection.get("subject").toString());
			objEmail.setFromFull(jsonDataSection.get("fromfull").toString());
			objEmail.setFrom(jsonDataSection.get("from").toString());

			// Parts / content
			JSONArray jsonParts = (JSONArray) jsonDataSection.get("parts");

			for (Object jsonPart1 : jsonParts) {
				JSONObject jsonPart = (JSONObject) jsonPart1;
				JSONObject jsonPartHeaders = (JSONObject) jsonPart.get("headers");
				objEmail.setHeaders(builderHeaders(jsonPartHeaders));
				objEmail.setBody(jsonPart.get("body").toString());
			}
		} catch (Exception ex) {
			log.error("Exception thrown in getSpecificEmail method:" + ex.toString());
			throw ex;
		}

		return objEmail;
	}

	/**
	 * This method is used to retrieve specific email using its email id
	 *
	 * @param uniqueMailinatorMailID email id given in the email body unique to specific email
	 * @param privateDomain flag which will define if we want to hit mailinator private inbox or public inbox
	 * @return email
	 * @throws Exception
	 */
	public MailinatorMail getSpecificEmailForSettingPage(String subject, String uniqueMailinatorMailID,boolean privateDomain) throws Exception {
		MailinatorMail objEmail = new MailinatorMail();
		try {
				ArrayList<MailinatorInboxFormat> objEmailsInInbox = getEmailsOfInboxForPartner(uniqueMailinatorMailID,privateDomain);
				for (MailinatorInboxFormat mailinatorInboxEmail : objEmailsInInbox) {
					if (mailinatorInboxEmail.getSubjectOfEmail().contains(subject)) {
						objEmail = getSpecificEmail(mailinatorInboxEmail.getIdOfEmail(),privateDomain);
						break;
					}
				}
			if (objEmail == null) {
				log.info("No email was found for the given subject: " + "Support Request from a Partner");
			}
		} catch (Exception ex) {
			log.error("Exception thrown in method getSpecificEmailUsingIncidentCode:" + ex.toString());
			throw ex;
		}
		return objEmail;
	}

	/**
	 * For getting headers of an email
	 *
	 * @param jsonPartHeaders
	 * @return email headers
	 * @throws Exception
	 */
	private static HashMap<String, String> builderHeaders(JSONObject jsonPartHeaders) throws Exception {
		HashMap<String, String> headers = new HashMap<>();
		try {
			Set<String> objHeaderKeys = jsonPartHeaders.keySet();

			for (String headerKey : objHeaderKeys) {
				headers.put(headerKey, jsonPartHeaders.getString(headerKey));
			}
		} catch (Exception ex) {
			log.error("Exception thrown in method builderHeaders:" + ex.toString());
			throw ex;
		}

		return headers;
	}

	/**
	 * This method can be used for getting an email using the incident id - if incident id is given in the subject
	 *
	 * @param incidentID
	 * @return email
	 * @throws Exception
	 */
	public MailinatorMail getSpecificEmailUsingIncidentCode(String incidentID,String mailId,boolean privateDomain) throws Exception {
		MailinatorMail objEmail = null;
		try {
			if (incidentID.trim() != "") {
				ArrayList<MailinatorInboxFormat> objEmailsInInbox = getEmailsOfInbox(mailId,privateDomain);
				for (MailinatorInboxFormat mailinatorInboxEmail : objEmailsInInbox) {
					if (mailinatorInboxEmail.getSubjectOfEmail().contains(incidentID)) {
						objEmail = getSpecificEmail(mailinatorInboxEmail.getIdOfEmail(),privateDomain);
						break;
					}
				}
			} else {
				log.error("Incident id is blank 1 " + incidentID);
			}
			if (objEmail == null) {
				log.info("No email was found for the given incident id: " + incidentID);
			}
		} catch (Exception ex) {
			log.error("Exception thrown in method getSpecificEmailUsingIncidentCode:" + ex.toString());
			throw ex;
		}
		return objEmail;
	}

	/**
	 * Rest API method for getting the response in string format
	 *
	 * @param responseCode
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public String getRawEmailResponse(String incidentID, String mailId, boolean privateDomain) throws Exception {
		String response = null;
		try {
			if (incidentID.trim() != "") {
				ArrayList<MailinatorInboxFormat> objEmailsInInbox = getEmailsOfInbox(mailId, privateDomain);
				for (MailinatorInboxFormat mailinatorInboxEmail : objEmailsInInbox) {
					if (mailinatorInboxEmail.getSubjectOfEmail().contains(incidentID)) {
						response = getRawEmailResponseByMailID(mailinatorInboxEmail.getIdOfEmail(), privateDomain);
						break;
					}
				}
			} else {
				log.error("Incident id is blank 1 " + incidentID);
			}
			if (response == null) {
				log.info("No email was found for the given incident id: " + incidentID);
			}
		} catch (Exception ex) {
			log.error("Exception thrown in method getSpecificEmailUsingIncidentCode:" + ex);
			throw ex;
		}
		log.info(response);
		return response;
	}

	public static String getResponse(int responseCode, HttpURLConnection con) throws Exception {
		StringBuffer response = new StringBuffer();
		Thread.sleep(4000);
		try {
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				log.info("Response from API: " + response.toString());
			} else {
				log.error("REST request did not work!!!");
			}
		} catch (Exception ex) {
			log.error("Exception thrown in method getResponse:" + ex.toString());
			throw ex;
		}
		return response.toString();
	}

	/**
	 * For checking the existence of one string in other string
	 *
	 * @param mainString
	 * @param patternString
	 * @return true or false
	 * @throws Exception
	 */
	public boolean findStringUsingRegEx(String mainString, String patternString) throws Exception {
		boolean foundFlag = false;
		try {
			Pattern p = Pattern.compile(patternString); // the pattern to search for
			Matcher m = p.matcher(mainString);

			// now try to find at least one match
			if (m.find()) {
				log.info(patternString + " found in " + mainString);
				foundFlag = true;
			} else {
				log.info(patternString + " not found in " + mainString);
				foundFlag = false;
			}
		} catch (Exception ex) {
			log.error("Exception thrown in method findStringUsingRegEx" + ex.toString());
			foundFlag = false;
			throw ex;
		}
		return foundFlag;
	}

	/**
	 * For getting case id link from incident email content
	 *
	 * @param incidentCaseID
	 * @param objMailinatorEmail
	 * @return
	 * @throws Exception
	 */
	public String getIncidentCaseIDLink(String incidentCaseID, MailinatorMail objMailinatorEmail, String languageCode) throws Exception {
		String incidentCaseURL = "";
		try {
			String emailBody = objMailinatorEmail.getBody();

			if (!emailBody.trim().equals("")) {
				emailBody = emailBody.replace(" ", "");
				incidentCaseURL = findSubstringUsingRegEx(emailBody, "<b>" + getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.case.id") + ":</b><ahref=", ">" + incidentCaseID + "</a></span>");
			}
		} catch (Exception ex) {
			log.error("Exception thrown in method getIncidentCaseIDLink:" + ex.toString());
			throw ex;
		}
		return incidentCaseURL;
	}

	/**
	 * For finding substring from a string using regular expression
	 *
	 * @param mainString
	 * @param startString
	 * @param endString
	 * @return
	 */
	public String findSubstringUsingRegEx(String mainString, String startString, String endString) throws Exception {
		String stringToBeFetched = " ";

		try {
			startString = startString.replace(" ", "");
			endString = endString.replace(" ", "");
			String pattern = startString + "(.*)" + endString;

			// Create a Pattern object
			Pattern r = Pattern.compile(pattern);

			// Now create matcher object.
			Matcher m = r.matcher(mainString);
			if (m.find()) {
				stringToBeFetched = m.group(0).replace(startString, "").replace(endString, "");
				log.info("Found value: " + stringToBeFetched);
			} else {
				log.info("No match found");
			}

		} catch (Exception ex) {
			log.error("Exception thrown in method findSubstringUsingRegEx:" + ex.toString());
			throw ex;
		}
		return stringToBeFetched;
	}

	/**
	 * For getting company link from incident email content
	 *
	 * @param incidentCompanyName
	 * @param objMailinatorEmail
	 * @return
	 * @throws Exception
	 */
	public String getIncidentCompanyLink(String incidentCompanyName, MailinatorMail objMailinatorEmail, String languageCode) throws Exception {
		String incidentCompanyURL = "";
		try {
			incidentCompanyName = incidentCompanyName.replace(" ", "");
			String emailBody = objMailinatorEmail.getBody();

			if (!emailBody.trim().equals("")) {
				emailBody = emailBody.replace(" ", "");
				incidentCompanyURL = findSubstringUsingRegEx(emailBody, "<b>" + getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.company") + ":</b><ahref=", ">" + incidentCompanyName + "</a></span>");
			}
		} catch (Exception ex) {
			log.error("Exception thrown in method getIncidentCompanyLink:" + ex.toString());
			throw ex;
		}
		return incidentCompanyURL;
	}

	/**
	 * For getting user name link from incident email content
	 *
	 * @param objMailinatorEmail
	 * @return
	 * @throws Exception
	 */
	public String getIncidentUserNameLink(MailinatorMail objMailinatorEmail, String languageCode) throws Exception {
		String incidentUserNameURL = "";
		try {
			String emailBody = objMailinatorEmail.getBody();

			if (!emailBody.trim().equals("")) {
				emailBody = emailBody.replace(" ", "");
				incidentUserNameURL = findSubstringUsingRegEx(emailBody, "<b>" + getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.username") + ":</b><ahref=", "</a>\\(");
				incidentUserNameURL = incidentUserNameURL.substring(0, incidentUserNameURL.indexOf(">"));
			}
		} catch (Exception ex) {
			log.error("Exception thrown in method getIncidentUserNameLink:" + ex.toString());
			throw ex;
		}
		return incidentUserNameURL;
	}

	/**
	 * For getting computer name link from incident email content
	 *
	 * @param incidentComputerName
	 * @param objMailinatorEmail
	 * @return
	 * @throws Exception
	 */
	public String getIncidentComputerNameLink(String incidentComputerName, MailinatorMail objMailinatorEmail, String languageCode) throws Exception {
		String incidentComputerNameURL = "";
		try {
			incidentComputerName = incidentComputerName.replace(" ", "");
			String emailBody = objMailinatorEmail.getBody();

			if (!emailBody.trim().equals("")) {
				emailBody = emailBody.replace(" ", "");
				incidentComputerNameURL = findSubstringUsingRegEx(emailBody, "<b>" + getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.computername") + ":</b><ahref=", ">" + incidentComputerName + "</a></span>");
			}
		} catch (Exception ex) {
			log.error("Exception thrown in method getIncidentComputerNameLink:" + ex.toString());
			throw ex;
		}
		return incidentComputerNameURL;
	}

	/**
	 * For getting incident created on date format in dd/mm/yyyy from dd-MMM-yyyy
	 *
	 * @param incidentCreatedOn -incident Created On date in dd-MMM-yyyy format
	 * @return
	 * @throws Exception
	 */
	public static String convertCreatedOnDateFormat(String incidentCreatedOn) throws Exception {
		String incidentCreatedOnConverted = "";
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("dd MMM yyyy").parse(incidentCreatedOn));
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			incidentCreatedOnConverted = sdf.format(cal.getTime());
			log.info(incidentCreatedOn + " converted by convertCreatedOnDateFormat to " + sdf.format(cal.getTime()));
		} catch (Exception ex) {
			log.error("Exception thrown in method convertCreatedOnDateFormat:" + ex.toString());
			throw ex;
		}
		return incidentCreatedOnConverted;
	}

	/**
	 * This method is used to delete specific email using its email id
	 *
	 * @param uniqueMailinatorMailID email id given in the email body unique to specific email
	 * @param privateDomain flag which will define if we want to hit mailinator private inbox or public inbox
	 * @return boolean
	 */
	public boolean deleteSpecificEmail(String uniqueMailinatorMailID,boolean privateDomain) {
		boolean flag = true;
		try {
			String mailinatorSpecificEmailAPI = ConstantURL.MAILINATOR_BASE_URL + "/delete?token=%s&msgid=%s&private_domain=%b";
			String emailUrl = String.format(mailinatorSpecificEmailAPI, mailinatorToken, uniqueMailinatorMailID, privateDomain);
			URL url = new URL(emailUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("accept", "application/json");
			int responseCode = con.getResponseCode();
			log.info("Sending 'GET' request to URL : " + url + " And Responce code is: " + responseCode);
			if(responseCode == 200)
				flag=true;
			else
				flag = false;
		} catch (Exception ex) {
			flag = false;
			log.error("Exception thrown in deleteSpecificEmail method:" + ex.toString());

		}

		return flag;
	}
	/**
	 * Retrieves the raw email response from the Mailinator API for a specific email ID.
	 *
	 * @param uniqueMailinatorMailID The email Id to retrieve the response for.
	 * @param privateDomain A flag indicating whether the email is in a private domain.
	 * @return The raw email response as a string.
	 * @throws Exception If an error occurs during the API request or response processing.
	 */
	public String getRawEmailResponseByMailID(String uniqueMailinatorMailID, boolean privateDomain) throws Exception {
        String emailResponse;
        try {
            String mailinatorSpecificEmailAPI = ConstantURL.MAILINATOR_BASE_URL + "/email?token=%s&msgid=%s&private_domain=%b";
            String emailUrl = String.format(mailinatorSpecificEmailAPI, mailinatorToken, uniqueMailinatorMailID, privateDomain);
            URL url = new URL(emailUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            int responseCode = con.getResponseCode();
            log.info("Sending 'GET' request to URL : " + url + " And Responce code is: " + responseCode);
			emailResponse = getResponse(responseCode, con);
        } catch (Exception ex) {
            log.error("Exception thrown in getSpecificEmail method:" + ex);
            throw ex;
        }
		return emailResponse;
    }
	
	
	public static void disableSslVerification() {
	    try {
	        TrustManager[] trustAllCerts = new TrustManager[]{
	            new X509TrustManager() {
	                public X509Certificate[] getAcceptedIssuers() { return null; }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
	            }
	        };
	        SSLContext sc = SSLContext.getInstance("TLS");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}