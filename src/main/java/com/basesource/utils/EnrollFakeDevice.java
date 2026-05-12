package com.basesource.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DeviceVariables;
import com.daasui.constants.PreferenceVariables;
import com.daasui.pages.DeviceDetailsPage;
import com.daasui.pages.DeviceListPage;
import com.daasui.pages.PreferencesPage;
import com.google.common.io.BaseEncoding;

/**
 * This is a enroll fake device class. Calling this class method, user have to pass three parameters e.g. companyName, companyPin and companyEmailID
 *
 */

public class EnrollFakeDevice extends CommonMethod {
	private static Logger log = LogManager.getLogger(EnrollFakeDevice.class);
	private static String deviceName, deviceSerialNumber, deviceId, deviceIdSignature, deviceAuthToken;
	private static String host;
	private static String hostSearchService;
	private Properties enrollFakeDevicePageProperties;
	private ObjectReader enrollFakeDevicePropertiesReader = new ObjectReader();
	static PrivateKey prvKey;
	static PublicKey pubKey;
	public String tenant, parent;
	public static HashMap<String, String> intuneCredentials = PreferencesPage.fetchIntuneCredentilas();
	private EnrollFakeDevice instance;
	
//	public EnrollFakeDevice(WebDriver driver) throws IOException {
//		
//	}
//
	public EnrollFakeDevice getInstance() throws IOException {
		if (instance == null) {
			synchronized (PreferencesPage.class) {
				if (instance == null) {
					instance = new EnrollFakeDevice();

				}
			}
		}
		return instance;
	}

//	public EnrollFakeDevice(WebDriver driver) throws IOException {
//		enrollFakeDevicePageProperties = enrollFakeDevicePageProperties.getObjectRepository("");
//	}

	
	/**
	 * This block will generate the public key and the private key for the device
	 */
	static {
		KeyPairGenerator keyGen = null;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		keyGen.initialize(2048);
		KeyPair mKeyPair = keyGen.genKeyPair();
		prvKey = mKeyPair.getPrivate();
		pubKey = mKeyPair.getPublic();
	}

	/**
	 * This method will generate the deviceId and the device signature for the device
	 */
	public void getDeviceID() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		EnrollFakeDevice.pubKey.getEncoded(); // remove this
		deviceId = UUID.randomUUID().toString();
		byte[] data = EnrollFakeDevice.pubKey.getEncoded();
		Signature sign = Signature.getInstance("SHA256withRSA");
		sign.initSign(EnrollFakeDevice.prvKey);
		sign.update(data);
		byte[] signature = sign.sign();
		deviceIdSignature = new String(BaseEncoding.base64().encode(signature));
	}

	/**
	 * This method is generate Device name and Device serial number.
	 */
	public String generateDeviceKeysDetails() throws Exception {
		long deviceNo = System.nanoTime();
		String deviceNum = Long.toString(deviceNo);
		Random random = new Random();
		int val = random.nextInt();
		String Hex = new String();
		Hex = Integer.toHexString(val);
		deviceName = "Fake Device ".concat(Hex);
		System.out.println("deviceName: " + deviceName);
		log.info("Device Name is: " + deviceName);
		deviceSerialNumber = "HPQA03".concat(String.valueOf(deviceNum));
		log.info("Device serial Number is: " + deviceSerialNumber);
		return deviceName;
	}

	/**
	 * This method will make a devicelocation registry call for the environment validation and return response code
	 * 
	 * @param companyPin - Company pin for the device enrollment
	 * @return responseCode - Response code for the location registry API
	 */
	public int getLocationRegistryResponce(String companyPin) throws Exception {
		String apiUrl = host + "api/location-registry/v1/locations/" + companyPin;
		URL url = new URL(apiUrl);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		log.info("Sending 'GET' request to URL : " + url + " And Responce code is: " + responseCode);
		return responseCode;
	}

	/**
	 * This method will make a call userSignIn API for the user detail validation and return userAuthToken
	 * 
	 * @param companyName - Company name for the device enrollment
	 * @param companyEmailId - Company emailId for the device enrollment
	 * @param companyPin - Company pin for the device enrollment
	 * @return authToken - userAuthToken received in the response body for the API
	 */
	public String getSigInResponce(String companyName, String companyEmailId, String companyPin) throws Exception {
		String apiUrl = host + "api/users/sign_in";
		URL url = new URL(apiUrl);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		String body = "{\"user\":{\"authority\":\"hptpm/otp\",\"detected_username\":\"" + companyName + "\",\"email\":\"" + companyEmailId + "\",\"credentials\":\"" + companyPin + "\"}}";
		byte[] outputInBytes = body.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();
		int responseCode = con.getResponseCode();
		log.info("Sending 'POST' request to URL : " + url + " And Responce code is: " + responseCode);
		String response = getResponce(responseCode, con);
		JSONObject json = new JSONObject(response.toString());
		String authToken = (String) json.get("authentication_token");
		log.info("AuthToken from SignIn API is: " + authToken);
		return authToken;
	}
	
	
	
	/**
	 * This method will make a call userSignIn API for IOT device for the user detail validation and return userAuthToken
	 * @param companyPin - Company pin for the device enrollment
	 * @param deviceID   - Device ID of the device we are trying to enroll.
	 * @return authToken - userAuthToken received in the response body for the API
	 */
	public String getIOTSigInResponce(String companyPin, String deviceID) throws Exception {
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		String apiUrl =  getEnvironmentSpecificData(System.getProperty("environment"), "IOTDiscoveryURL");
		URL url = new URL(apiUrl);

		// Set up the proxy
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("web-proxy.in.hpicorp.net", 8080));
		// Set up the connection
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection(proxy);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		String body = "{\"pinType\": \"cpin\",\"pin\": \"" + companyPin + "\",\"deviceId\": \"" + deviceID + "\",\"deviceIdAlt\": \"" + deviceID + "\"}";
		LOGGER.info(body);
		byte[] outputInBytes = body.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();
		int responseCode = con.getResponseCode();
        LOGGER.info("Sending 'POST' request to URL : {} And Response code is: {}", url, responseCode);
		String response = getResponce(responseCode, con);
		LOGGER.info(response);
		JSONObject json = new JSONObject(response.toString());
		String authToken = json.getJSONArray("service_map_data").getJSONObject(0).getString("service_jwt_token");
        LOGGER.info("AuthToken from SignIn API is: {}", authToken);
		return authToken;
	}
	
	/**
	 * This method will generate CSR token for the IOT device we wish to enroll and bring it into active state.
	 * @param deviceID  - DeviceID of the device we want to enroll.
	 * @return csr      - CSR token
	 * @throws Exception
	 */
	public String getIOTCSRGenerator(String deviceID) throws Exception {
		CSRGenerator csr = new CSRGenerator();
		String csrtoken = csr.enrollDevice(deviceID);
		return csrtoken;
	}
	
	
	/**
	 * This method will enroll IOT device into Active state using deviceID, tenantID, authToken & CSR token.
	 * @param serialNumber - Serial number of the device.
	 * @param deviceID     - DeviceID of the device to be enrolled.
	 * @param csrToken     - CSR token generated using deviceID.
	 * @param tenantID     - TenantID of the tenant where the device is to be enrolled.
	 * @param authToken    - Authorization token.
	 * @return String     - sessionID.
	 * @throws Exception
	 */
	public boolean getIOTDeviceEnrollmentResponce(String serialNumber, String deviceID, String csrToken, String tenantID, String authToken) throws Exception {
		String apiUrl = host + "api/1.0/devices/iot/enrollment";
		LOGGER.info("Calling URL: " + apiUrl); // Debug: print the full URL
		URL url = new URL(apiUrl);
		// Set up the proxy
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("web-proxy.in.hpicorp.net", 8080));
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection(proxy);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);

		JSONParser jsonParser = new JSONParser();
		String obj2 = (jsonParser.parse(new FileReader(ConstantPath.IMPORT_PATH+getEnvironmentSpecificData(System.getProperty("environment"), "IOT_ENROLL_DEVICE")))).toString();
		JSONObject obj1 = new JSONObject(obj2);
		obj1.put("csr", csrToken);
		obj1.put("deviceId", deviceID);
		obj1.put("tenantId", tenantID);
		obj1.put("token", authToken);
		((JSONObject)obj1.get("data")).put("device_id", deviceID);
		((JSONObject)obj1.get("data")).put("name", serialNumber);
		((JSONObject)obj1.get("data")).put("serial_number", serialNumber);
		((JSONObject) obj1.get("data")).put("deviceOwnerEmail", "ScriptFakeDevice@yopmail.com");
		((JSONObject) obj1.get("data")).put("deviceOwnerName", "ScriptFakeDeviceUser");
		String body = obj1.toString();
		
		byte[] outputInBytes = body.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();
		int responseCode = con.getResponseCode();
		LOGGER.info("URL : {}", url);
		LOGGER.info("BODY : {}", body);
		LOGGER.info("responsecode : {}", responseCode);
		if(responseCode == 200){
            LOGGER.info("Sending 'POST' request to URL : {} And Response code is: {}", url, body, responseCode);
			return true;
		}
		
		return false;
	}
	

	/**
	 * This method will make a GET call to the server for the public key
	 * 
	 * @param authToken - User Auth token for the provisioning call
	 * @return publicKey - Server public key for the API calls
	 */
	public String getDeviceprovision(String authToken) throws Exception {
		String apiUrl = host + "api/v1/devices/provision?auth_token=" + authToken;
		URL url = new URL(apiUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("accept", "application/json");
		int responseCode = con.getResponseCode();
		log.info("Sending 'GET' request to URL : " + url + " And Responce code is: " + responseCode);
		String response = getResponce(responseCode, con);
		JSONObject json = new JSONObject(response);
		JSONObject jsonObj1 = (JSONObject) json.get("provisioning_signature_chain");
		JSONArray jsonObj2 = (JSONArray) jsonObj1.get("signing_keys");
		JSONObject jsonObj3 = (JSONObject) jsonObj2.get(0);
		String publicKey = (String) jsonObj3.get("public_key");
		log.info("Public Key is: " + publicKey);
		return publicKey;
	}

	/**
	 * This method will validate the response code and convert the response body into string
	 * 
	 * @param responseCode - The response code for the desired API
	 * @param con - HttpConnection object of the desired API
	 * @return response - response converted into String format
	 */
	public static String getResponce(int responseCode, HttpURLConnection con) throws Exception {
		StringBuffer response = new StringBuffer();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			log.info("Response from desired API: " + response.toString());
		} else {
			log.error("API request not worked");
		}
		return response.toString();
	}

	/**
	 * This method will make a enroll device call and return the boolean value based on the response code
	 * 
	 * @param authToken - authToken for the logged in user
	 * @param publicKey - Device public key
	 * @param deviceName - Name of the device which needs to be enrolled
	 * @param deviceId - DeviceId of the device which needs to be enrolled
	 * @param deviceSerialNumber - Serial number of the device which needs to be enrolled
	 * @return boolean - True if the device enroll call passes else false
	 */
	public boolean postDeviceEnroll(String authToken, String publicKey, String deviceName, String deviceId, String deviceSerialNumber) throws Exception {
		String apiUrl = host + "api/devices/enroll?auth_token=" + authToken;
		URL url = new URL(apiUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
//		con.setRequestProperty("Connection", "keep-alive");
//		con.setRequestProperty("Expect", "100-continue");
//		con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//		con.setRequestProperty("accept-charset", "utf-8");
		con.setDoOutput(true);
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
		String strDate = formatter.format(date);
		publicKey = new String(Base64.encodeBase64(EnrollFakeDevice.pubKey.getEncoded()));
		String body = "{\"device\":{\"client_type\":\"Agent\",\"public_key\":\"" + publicKey + "\",\"name\":\"" + deviceName + "\",\"device_type\":\"Notebook\",\"processor\":\"Intel Core i7\",\"memory\":\"16 GB 1600 MHz DDR 3 SDRAM\",\"disk\":\"256 GB SATA SSD\",\"graphics\":\"Awesome Graphics Card\",\"operating_system\":\"Windows 8 Pro\",\"serial_number\":\"" + deviceSerialNumber + "\",\"os_architecture\":\"amd64\",\"os_system_ui_lang\":\"en-us\",\"device_id\":\"" + deviceId
				+ "\",\"manufacturer\":\"Hewlett-Packard\",\"product_number\":\"03a9ed-8b\",\"country_code\":\"US\",\"byod\":false,\"device_model\":\"HP EliteBook 850\"},\"analytics\": {\"date_time\":\"" + strDate
				+ "\",\"analytics_class_version\": \"1.0\",\"client_version\": \"1.23.7.1\",\"enroll_method\": \"Company Pin\",\"enroll_status\": \"\",    \"failure_reason\": \"\",\"silent_param_used\": \"Non-Silent\",\"optin_silent\": \"Loud\",\"enrollment_confirmation_selection\": \"Accepted\"}}\r\n";
		byte[] outputInBytes = body.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();
		int responseCode = con.getResponseCode();
		log.info("Sending 'POST' request to URL : " + url + " And Responce code is: " + responseCode);
		if (responseCode == 204) {
			log.info("Fake Device Enrolled successfully !!");
			return true;
		} else {
			log.info("Fake Device is not enrolled, Please check.");
			return false;
		}

	}

	/**
	 * This method will make a device authentication call and return device authorization token
	 * 
	 * @param deviceId - deviceId for the enrolled device
	 * @param deviceIdSignature - deviceId Signature generated using public key and private key
	 * @return boolean - True if the API response code is 200 else return False
	 */
	public boolean deviceAuthenticate(String deviceId, String deviceIdSignature) throws Exception {
		String apiUrl = host + "api/v1.1/devices/authenticate";
		URL url = new URL(apiUrl);
		// Make a device authentication post call
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		String body = "{\"device\":{\"device_id\":\"" + deviceId + "\" , \"device_id_signature\":\"" + deviceIdSignature + "\"}}";
		con.setDoOutput(true);
		byte[] outputInBytes = body.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();
		// Get the response code for the device authentication call
		int responseCode = con.getResponseCode();
		log.info("Response code for device authenticate: " + responseCode);
		// Validation for the device authentication API response code
		if (responseCode == 200) {
			String response = getResponce(responseCode, con);
			JSONObject json = new JSONObject(response.toString());
			deviceAuthToken = (String) json.get("token");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method will make a get job call to the server and return the GET job array received as the response
	 * 
	 * @param deviceAuthToken - Authorization token for the device
	 * @return getJobArray - JSONArray have the GetJob response
	 */
	public JSONArray deviceGetJob(String deviceAuthToken) throws Exception {
		String apiUrl = host + "api/v1/jobs?auth_token=" + deviceAuthToken;
		URL url = new URL(apiUrl);
		// GET job execution
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("accept", "application/json");
		int responseCode = con.getResponseCode();
		log.info("Sending 'GET' request to URL : " + url + " And Responce code is: " + responseCode);
		String response = getResponce(responseCode, con);
		// GET Job response array blank check
		JSONArray getJobArray = new JSONArray(response); // Check for the Blank GET job
		if (getJobArray.isNull(0)) {
			log.error("Blank get job response received." + getJobArray);
		}
		return getJobArray;
	}

	/**
	 * This method will make a patch API call for the device ownership change and return a closableHttpResponse object
	 * 
	 * @param authToken - Authorization token for the logged in user
	 * @param deviceUid - Device UID which is been generated
	 * @return HttpPatch API response - closableHttpRepsonse object for the Patch API call
	 */
	public CloseableHttpResponse patchDeviceOwnership(String authToken, String deviceUid) throws Exception {
		String apiUrl = host + "api/2.0/devices/" + deviceUid;
		// createDefault method will create simple client connection
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPatch httpPatch = new HttpPatch(apiUrl);
		String body = "{\"byod\":true}";
		// Setting the body for the Patch API
		httpPatch.setEntity(new StringEntity(body));
		// Setting the Headers for the Patch API
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Authorization", "Bearer " + authToken);
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpPatch.addHeader(entry.getKey(), entry.getValue());
		}
		// execute the PATCH call
		CloseableHttpResponse closableHttpResponse = httpClient.execute(httpPatch);
		return closableHttpResponse;
	}

	/**
	 * This method will parse the received authorization Token and return the GSON object
	 * 
	 * @param authToken - The authorization token which needs to be parsed
	 * @return GSON object - parsed authToken
	 */
	public static JSONObject jwtTokenParse(String authToken) throws UnsupportedEncodingException {
		// Need to split the auth token based on the "." so that the body received in the auth token can be decoded
		String[] splitAuth = authToken.split("\\.");
		String b64payload = splitAuth[1];
		// Decoding the body received in the auth token
		String value = new String(Base64.decodeBase64(b64payload), "UTF-8");
		JSONObject jsonString = new JSONObject(value);
		return jsonString;
	}
	
	/**
	 * This method will enroll a fake device and return the device details of the enrolled device same details to be used for submitting an incident
	 * 
	 * @param companyName - Name of the company under which the device is enrolled
	 * @param companyPin - Pin of the company under which the device is enrolled
	 * @param companyEmailId - Email of the user under which the device is enrolled
	 * @return deviceDetails - Hash containing the device details
	 */
	public static HashMap<String, String> enrollFakeDeviceForIncident(String companyName, String companyPin, String companyEmailId) throws Exception {
		EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
		host = enrollFakeDevice.getEnvironmentForIncident(System.getProperty("environment"));
		EnrollFakeDevice fakeDeviceObj = new EnrollFakeDevice().getInstance();
		hostSearchService = fakeDeviceObj.getSearchServiceEnvironmentForIncident(System.getProperty("environment"));
		String deviceName = enrollFakeDevice.generateDeviceKeysDetails();
		HashMap<String, String> deviceEnrollmentDetails = new HashMap<String, String>();
		int response = enrollFakeDevice.getLocationRegistryResponce(companyPin);
		if (response == 200) {
			String authToken = enrollFakeDevice.getSigInResponceForIncident(companyName, companyEmailId, companyPin);
			String publicKey = enrollFakeDevice.getDeviceprovisionForIncident(authToken);
			enrollFakeDevice.getDeviceID();
			enrollFakeDevice.postDeviceEnrollForIncident(authToken, publicKey, deviceName, deviceId, deviceSerialNumber);
			deviceEnrollmentDetails.put("userAuthToken", authToken);
			deviceEnrollmentDetails.put("deviceName", deviceName);
			deviceEnrollmentDetails.put("deviceId", deviceId);
			JSONObject parsedJwtToken = EnrollFakeDevice.jwtTokenParse(authToken);
			//DeviceAuthenticate call to get the device authorization token
			boolean deviceAuthStatus = enrollFakeDevice.deviceAuthenticateForIncident(deviceId, deviceIdSignature);
			Assert.assertTrue(deviceAuthStatus, "Device Authentication failed!! Don't Proceed.");
			String tenantID = parsedJwtToken.getString("tenant");
			deviceEnrollmentDetails.put("tenantId", tenantID);
			deviceEnrollmentDetails.put("host", hostSearchService);
			deviceEnrollmentDetails.put("companyEmailId", companyEmailId);
			deviceEnrollmentDetails.put("deviceSerialNumber", deviceSerialNumber);
		} else {
			Assert.assertFalse(false, "LocationRegistry API response is not 200, fake device enrolement has failed !! Don't Proceed.");
		}
		return deviceEnrollmentDetails;
	}
	


	/**
	 * This method will make a POST call to the SearchService for the alert generated on device ownership change and return true or false based on the response code received
	 * 
	 * @param mspTenantId - msp tenant id of the logged in MSP company
	 * @param tenantId - tenant id of the logged in tenant
	 * @param deviceName - device name of the enrolled device
	 * @param mspAuthToken - authorization token of the MSP
	 * @param logType - log type which needs to be searched on search service
	 * @param logSubType - log subtype which needs to be searched on search service
	 * @return boolean - True if the API response code is 200 else return False
	 */
	public boolean deviceOwnershipChangeAlert(String mspTenantId, String tenantId, String deviceName, String mspAuthToken, String logType, String logSubType) throws Exception {
		String apiUrl = hostSearchService + "services/ccc-search/1.3/tenants/" + mspTenantId + "/multitenanted/_search";
		URL url = new URL(apiUrl);
		// Make a POST call to the search service for the passed query
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "Bearer " + mspAuthToken);
		String body = "{\"query\":\"{\\\"query\\\":{\\\"bool\\\":{\\\"must\\\":[{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"tenantId\\\":\\\"" + tenantId + "\\\"}}]}},{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"deviceType.keyword\\\":\\\"NOTEBOOK\\\"}}]}},{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"logType.keyword\\\":\\\"" + logType + "\\\"}}]}},{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"logSubtype.keyword\\\":\\\"" + logSubType
				+ "\\\"}}]}},{\\\"match\\\":{\\\"deviceName\\\":{\\\"query\\\":\\\"\\\\t " + deviceName
				+ "\\\",\\\"operator\\\":\\\"AND\\\"}}},{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"deleted\\\":false}}]}},{\\\"bool\\\":{\\\"must_not\\\":[{\\\"match\\\":{\\\"type.keyword\\\":\\\"notification\\\"}}]}}]}},\\\"_source\\\":[\\\"id\\\",\\\"logType\\\",\\\"description\\\",\\\"logSubtype\\\",\\\"companyName\\\",\\\"tenantId\\\",\\\"createdAt\\\",\\\"createdAt_scr\\\",\\\"level\\\",\\\"deviceType\\\",\\\"deviceName\\\",\\\"userName\\\",\\\"userId\\\",\\\"deleted\\\",\\\"type\\\",\\\"deviceId\\\"],\\\"from\\\":0,\\\"size\\\":50,\\\"sort\\\":[{\\\"createdAt\\\":{\\\"order\\\":\\\"DESC\\\",\\\"unmapped_type\\\":\\\"long\\\"}}]}\",\"tenant_ids\":[],\"index_list\":[\"logs\"],\"search_type\":\"tenanted\"}";
		con.setDoOutput(true);
		byte[] outputInBytes = body.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();
		log.info("API Url for search service API call: " + apiUrl);
		int responseCode = con.getResponseCode();
		log.info("Response code for search service API call: " + responseCode);
		// Validation of the response code received
		if (responseCode == 200) {
			return true;
		}
		return false;
	}

	/**
	 * This method will validate the GET job response for ChangeDeviceOwnership job
	 * 
	 * @param deviceGetJobArray - JSON array having get job response
	 * @return deviceOwnerShipChangeStatus - True if the ChangeDeviceOwnership is received else return False
	 */
	public boolean changeDeviceOwnershipGetJobValidation(JSONArray deviceGetJobArray) {
		boolean deviceOwnerShipChangeStatus = false;
		// Parse the JSONArray and check for the device ownership change job
		for (int i = 0; i < deviceGetJobArray.length(); i++) {
			JSONObject jsonobject = deviceGetJobArray.getJSONObject(i);
			JSONObject getJobPayload = new JSONObject(jsonobject.get("job_payload").toString());
			String jobType = getJobPayload.getString("job_type");
			log.info("The Job type received in the device get job : " + jobType);
			if (jobType.equalsIgnoreCase("ChangeDeviceOwnership")) {
				deviceOwnerShipChangeStatus = true;
			} else {
				log.info("No Change device Ownership job found!!!");
				deviceOwnerShipChangeStatus = false;
			}
		}
		return deviceOwnerShipChangeStatus;
	}

	/**
	 * This method will make an device unenrollment call and return the boolean value based on the response code received
	 * 
	 * @return Boolean - True if the device is successfully unenrolled else return False
	 */
	public boolean deviceUnenroll() throws IOException {
		String apiUrl = host + "api/v1.1/devices/unenroll?auth_token=" + deviceAuthToken;
		URL url = new URL(apiUrl);
		// Make a device unenrollment post call
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		String body = "{\"device\":{\"device_id\":\"" + deviceId + "\"}}";
		con.setDoOutput(true);
		byte[] outputInBytes = body.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();
		// Get the response code for the device unenrollment call
		int responseCode = con.getResponseCode();
		log.info("Making a device unenrollment call on the API : " + apiUrl + " Response code for device unenrollment: " + responseCode);
		// Validation for the device unenrollment API response code
		if (responseCode == 204) {
			return true;
		}
		return false;
	}

	/**
	 * This method will enroll a fake device and return the device details of the enrolled device
	 * 
	 * @param companyName - Name of the company under which the device is enrolled
	 * @param companyPin - Pin of the company under which the device is enrolled
	 * @param companyEmailId - Email of the user under which the device is enrolled
	 * @return deviceDetails - Hash containing the device details
	 */
	public static HashMap<String, String> enrollFakeDevice(String companyName, String companyPin, String companyEmailId) throws Exception {
		EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
		host = enrollFakeDevice.getEnvironment(System.getProperty("environment"));
		String deviceName = enrollFakeDevice.generateDeviceKeysDetails();
		HashMap<String, String> deviceEnrollmentDetails = new HashMap<String, String>();
		if (!(host.contains("eu"))) {
			int response = enrollFakeDevice.getLocationRegistryResponce(companyPin);
			if (response == 200) {
				String authToken = enrollFakeDevice.getSigInResponce(companyName, companyEmailId, companyPin);
				String publicKey = enrollFakeDevice.getDeviceprovision(authToken);
				enrollFakeDevice.getDeviceID();
				enrollFakeDevice.postDeviceEnroll(authToken, publicKey, deviceName, deviceId, deviceSerialNumber);
				deviceEnrollmentDetails.put("userAuthToken", authToken);
				deviceEnrollmentDetails.put("deviceName", deviceName);
				deviceEnrollmentDetails.put("deviceSerialNumber", deviceSerialNumber);
			} else {
				return deviceEnrollmentDetails;
			}
		} else {
			String authToken = enrollFakeDevice.getSigInResponce(companyName, companyEmailId, companyPin);
			String publicKey = enrollFakeDevice.getDeviceprovision(authToken);
			enrollFakeDevice.getDeviceID();
			enrollFakeDevice.postDeviceEnroll(authToken, publicKey, deviceName, deviceId, deviceSerialNumber);
			deviceEnrollmentDetails.put("userAuthToken", authToken);
			deviceEnrollmentDetails.put("deviceName", deviceName);
			deviceEnrollmentDetails.put("deviceSerialNumber", deviceSerialNumber);
		}
		return deviceEnrollmentDetails;
	}
	
	
	
	/**
	 * This method will enroll a fake IOT device and return the device details of the enrolled device
	 * 
	 * @param companyName - Name of the company under which the device is enrolled
	 * @param companyPin - Pin of the company under which the device is enrolled
	 * @param companyEmailId - Email of the user under which the device is enrolled
	 * @return deviceDetails - Hash containing the device details
	 */
	public boolean enrollIOTFakeDevice(String companyName, String companyPin, String companyEmailId, String serialNumber, String deviceID, String tenantID) throws Exception {
		
		host = getEnvironment(System.getProperty("environment"));
		String authToken = getIOTSigInResponce(companyPin, deviceID);
		String csrToken = getIOTCSRGenerator(deviceID);
		return getIOTDeviceEnrollmentResponce(serialNumber, deviceID, csrToken, tenantID, authToken); 
		
//		return enrollFakeDevice.getIOTDeviceEnrollmentStatus(sessionID, authToken);
		
	}

	

	/**
	 * This method will: 1.Enroll a fake device 2.Make a Patch API call to change the device ownership 3.Make a GET job call for the deviceOwnerShip change job 4.Make a searchService
	 * call for the LOG check
	 * 
	 * @param companyDetails - HashMap for the company details under which device needs to be enrolled
	 * @return boolean - true, if all the API calls passed, false, if any of the call fails
	 */
	public static boolean changeDeviceOwnership(HashMap<String, String> companyDetails) throws Exception {

		EnrollFakeDevice fakeDeviceObj = new EnrollFakeDevice().getInstance();
		hostSearchService = fakeDeviceObj.getSearchServiceEnvironment(System.getProperty("environment"));
		HashMap<String, String> deviceDetails = new HashMap<>();
		boolean getJobStatus = false;
		// Fake device is enrolled and the deviceDetails are returned
		log.info("Device Details: " + companyDetails);
		deviceDetails = EnrollFakeDevice.enrollFakeDevice(companyDetails.get("companyName"), companyDetails.get("companyPin"), companyDetails.get("companyEmailId"));
		log.info("Device Details: " + deviceDetails);
		// Parsing the authorization token received in the response of the user sign in call
		JSONObject parsedJwtToken = EnrollFakeDevice.jwtTokenParse(deviceDetails.get("userAuthToken"));
		String mspTenantID = parsedJwtToken.getString("parent");
		String tenantId = parsedJwtToken.getString("tenant");

		// DeviceAuthenticate call to get the device authorization token
		boolean deviceAuthStatus = fakeDeviceObj.deviceAuthenticate(deviceId, deviceIdSignature);
		if (!deviceAuthStatus) {
			log.info("Device Authentication failed.");
			return false;
		}
		// Patch device call to change the device ownership
		CloseableHttpResponse closableHttpResponseSignIn = fakeDeviceObj.patchDeviceOwnership(deviceDetails.get("userAuthToken"), deviceId);
		// Validating the status code for the Patch API call for the device ownership Change
		int devicePatchStatusCode = closableHttpResponseSignIn.getStatusLine().getStatusCode();
		if (devicePatchStatusCode != 204) {
			return false;
		}
		log.info("Status Code for the Patch API call for device ownership change: " + devicePatchStatusCode);
		// GET Job call for the device to get the ownershipChange payload
		Thread.sleep(3000);
		JSONArray deviceGetJobArray = fakeDeviceObj.deviceGetJob(deviceAuthToken);
		// Check for the ChangeDeviceOwnership job
		getJobStatus = fakeDeviceObj.changeDeviceOwnershipGetJobValidation(deviceGetJobArray);
		log.info("Get job Status: " + getJobStatus);

		// POST call to the search service for the generated alert
		boolean deviceChangeAlertStatus = fakeDeviceObj.deviceOwnershipChangeAlert(mspTenantID, tenantId, deviceDetails.get("deviceName"), companyDetails.get("mspAuthToken"), "Assets", "Ownership Change");
		log.info("Device Change alert status: " + deviceChangeAlertStatus);

		boolean deviceUnerollmentStatus = fakeDeviceObj.deviceUnenroll();
		log.info("Device Unenrollment Status: " + deviceUnerollmentStatus);

		if ((devicePatchStatusCode == 204) && (getJobStatus == true) && (deviceChangeAlertStatus == true) && (deviceUnerollmentStatus == true)) {
			return true;
		}
		return false;
	}

	public String getEnvironment(String enviornment) {
		try {

			enrollFakeDevicePageProperties = enrollFakeDevicePropertiesReader.getObjectRepository("Environment");
			switch (enviornment.toUpperCase()) {
			case "LATEST":
				return enrollFakeDevicePageProperties.getProperty("Latest");

			case "US-STABLE":
				return enrollFakeDevicePageProperties.getProperty("StableUS");

			case "US-PEM":
				return enrollFakeDevicePageProperties.getProperty("PEMUS");

			case "EU-STABLE":
				return enrollFakeDevicePageProperties.getProperty("StableUS");

			case "US-STAGING":
				return enrollFakeDevicePageProperties.getProperty("StagingUS");

			case "EU-STAGING":
				return enrollFakeDevicePageProperties.getProperty("StagingEU");

			case "US-PRODUCTION":
				return enrollFakeDevicePageProperties.getProperty("ProdUS");

			case "EU-PRODUCTION":
				return enrollFakeDevicePageProperties.getProperty("ProdEU");

			case "US-STABLE-WEP":
				return enrollFakeDevicePageProperties.getProperty("StableUS");

			case "US-STAGE-WEP":
				return enrollFakeDevicePageProperties.getProperty("StagingUS");

			case "EU-STAGE-WEP":
				return enrollFakeDevicePageProperties.getProperty("StagingEU");

			case "US-Perf-WEP":
				return enrollFakeDevicePageProperties.getProperty("Perf_USWEP");

			default:
				throw new InputMismatchException("You can use : US-STABLE, EU-STABLE, US-PRODUCTION, EU-PRODUCTION, US-STAGING, EU-STAGING, LATEST only ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getEnvironmentForIncident(String enviornment) {
		try {

			enrollFakeDevicePageProperties = enrollFakeDevicePropertiesReader.getObjectRepository("Environment");
			switch (enviornment.toUpperCase()) {
			case "LATEST":
				return enrollFakeDevicePageProperties.getProperty("Latest");

			case "US-STABLE":
				return enrollFakeDevicePageProperties.getProperty("StableUS");

			case "US-PEM":
				return enrollFakeDevicePageProperties.getProperty("PEMUS");

			case "EU-STABLE":
				return enrollFakeDevicePageProperties.getProperty("StableUS");

			case "US-STAGING":
				return enrollFakeDevicePageProperties.getProperty("StagingUS");

			case "EU-STAGING":
				return enrollFakeDevicePageProperties.getProperty("StagingUS");

			case "US-PRODUCTION":
				return enrollFakeDevicePageProperties.getProperty("ProdUS");

			case "EU-PRODUCTION":
				return enrollFakeDevicePageProperties.getProperty("ProdEU");

			default:
				throw new InputMismatchException("You can use : US-STABLE, EU-STABLE, US-PRODUCTION, EU-PRODUCTION, US-STAGING, EU-STAGING, LATEST only ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getSearchServiceEnvironment(String enviornment) {
		try {

			enrollFakeDevicePageProperties = enrollFakeDevicePropertiesReader.getObjectRepository("Environment");
			switch (enviornment.toUpperCase()) {
			case "LATEST":
				return enrollFakeDevicePageProperties.getProperty("Latest");

			case "US-STABLE":
				return enrollFakeDevicePageProperties.getProperty("StableUSSS");

			case "EU-STABLE":
				return enrollFakeDevicePageProperties.getProperty("StableEUSS");

			case "US-STAGING":
				return enrollFakeDevicePageProperties.getProperty("StagingUS");

			case "EU-STAGING":
				return enrollFakeDevicePageProperties.getProperty("StagingEU");

			case "US-PRODUCTION":
				return enrollFakeDevicePageProperties.getProperty("ProdUSSS");

			case "EU-PRODUCTION":
				return enrollFakeDevicePageProperties.getProperty("ProdEUSS");

			default:
				throw new InputMismatchException("You can use : US-STABLE, EU-STABLE, US-PRODUCTION, EU-PRODUCTION, US-STAGING, EU-STAGING, LATEST only ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getSearchServiceEnvironmentForIncident(String enviornment) {
		try {

			enrollFakeDevicePageProperties = enrollFakeDevicePropertiesReader.getObjectRepository("Environment");
			switch (enviornment.toUpperCase()) {
			case "LATEST":
				return enrollFakeDevicePageProperties.getProperty("Latest");

			case "US-STABLE":
				return enrollFakeDevicePageProperties.getProperty("StableUS");

			case "EU-STABLE":
				return enrollFakeDevicePageProperties.getProperty("StableEU");

			case "US-STAGING":
				return enrollFakeDevicePageProperties.getProperty("StagingUS");

			case "EU-STAGING":
				return enrollFakeDevicePageProperties.getProperty("StagingEU");

			case "US-PRODUCTION":
				return enrollFakeDevicePageProperties.getProperty("ProdUSSS");

			case "EU-PRODUCTION":
				return enrollFakeDevicePageProperties.getProperty("ProdEUSS");

			default:
				throw new InputMismatchException("You can use : US-STABLE, EU-STABLE, US-PRODUCTION, EU-PRODUCTION, US-STAGING, EU-STAGING, LATEST only ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method verifies enrolling Intune fake device based on deviceOsType and verifies emm tool redirecting emm Intune portal
	 * @param deviceOsType - Intune Os type
	 * @param companyName - Tenant name for enrollment 
	 * @param companyEmailId -  tenant email id for enrollment 
	 * @param companyPin - tenant company pin for enrollment 
	 * @param languageCode
	 * @return
	 */
	public final boolean verifyIntuneFakeDeviceEmmToolRedirection(String deviceOsType, String companyName, String companyEmailId, String companyPin, String languageCode ) {
		boolean flag = false;
		try {
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			HashMap<String, String> intuneDeviceData = new HashMap<>();
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			CompanyPin companypinInstance = new CompanyPin();
			EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
			preferencesPage.scrollOnPreferencesPage("preferenceTab");
			preferencesPage.clickByJavaScriptOnPreferencesPage("preferenceTab");
			preferencesPage.verifyEmmToggle(DeviceVariables.INTUNE);
			preferencesPage.configureIntuneOnPreferencesTab(intuneCredentials.get("DOMAIN_NAME"));
			preferencesPage.verifySuccessMessageOfIntuneConfiguration();
			companyPin = companypinInstance.generateCompanyPin(languageCode);
			host = enrollFakeDevice.getEnvironment(System.getProperty("environment"));
			String authToken = enrollFakeDevice.getSigInResponce(companyName, companyEmailId, companyPin);
			String publicKey = enrollFakeDevice.getDeviceprovision(authToken);
			enrollFakeDevice.getDeviceID();
			intuneDeviceData = fetchIntuneFirstDeviceDataBySelectingOSType(deviceOsType);
			if (intuneDeviceData != null) {
				deviceListPage.clickOnElementsOfDeviceListPage("devicesTab");
				removeExistingDevicePresentOnDaas(intuneDeviceData.get(DeviceVariables.INTUNE_SERIAL_NUMBER), languageCode);
				postEMMFakeDeviceEnroll(deviceOsType, authToken, publicKey, intuneDeviceData.get(DeviceVariables.INTUNE_DEVICE_NAME), intuneDeviceData.get(DeviceVariables.INTUNE_SERIAL_NUMBER),
						intuneDeviceData.get(DeviceVariables.INTUNE_OWNERSHIP), intuneDeviceData.get(DeviceVariables.INTUNE_MANUFACTURE), intuneDeviceData.get(DeviceVariables.INTUNE_MODEL), "");
				resetTableConfiguration();
				sleeper(3000);// Requires as fake device enrollments requires few seconds of time to enroll device on daas 
				checkDeviceDetailsBySelectingSerialNumber(intuneDeviceData.get(DeviceVariables.INTUNE_SERIAL_NUMBER));
				deviceDetailsPage.verifyEmmToolButton();
				LOGGER.info("Validating Daas device details with Intune device details");
				HashMap<String, String> deviceDetails = new HashMap<String, String>();
				deviceDetails.put("daasDeviceSerialNumberField", intuneDeviceData.get(DeviceVariables.INTUNE_SERIAL_NUMBER));
				deviceDetails.put("daasDeviceModelField", intuneDeviceData.get(DeviceVariables.INTUNE_MODEL));
				deviceDetails.put("daasDeviceNameField", intuneDeviceData.get(DeviceVariables.INTUNE_DEVICE_NAME));
				deviceDetails.put("daasManufactureField", intuneDeviceData.get(DeviceVariables.INTUNE_MANUFACTURE));
				if (deviceListPage.verifyDaasDeviceDetailsData(deviceDetails)) {
					deviceDetailsPage.verifyEmmToolRedirection(intuneCredentials.get("INTUNE_ID"), intuneCredentials.get("INTUNE_PASSWORD"), DeviceVariables.INTUNE);
					LOGGER.info("Validated Device Os " + deviceOsType + " with serial number " + intuneDeviceData.get(DeviceVariables.INTUNE_SERIAL_NUMBER) + " is enrolled via Intune Portal");
					flag = true;
				} else {
					LOGGER.error("Failed to validate Intune Device data for Serial Number  : " + intuneDeviceData.get(DeviceVariables.INTUNE_SERIAL_NUMBER));
					flag = false;
				}
			} else {
				return flag;
			}

		} catch (Exception e) {
			LOGGER.error("Exception occured in removeExistingDevicePresentOnDaas" + e.getMessage());
		}
		return flag;
	}

	/**
	 * This method will switch from daas portal to Intune portal and selects first device based on deviceOsType on intune portal to fetch Intune device data
	 * 
	 * @param deviceOsType - specify type of intune device data to be fetched from intune portal
	 * @return Hashmap
	 */
	public final HashMap<String, String> fetchIntuneFirstDeviceDataBySelectingOSType(String deviceOsType) {
		HashMap<String, String> intuneDeviceData = new HashMap<>();
		try {
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			createAndSwitchToNewTab();
			LOGGER.info("Switching To Intune Portal");
			getUrl(ConstantURL.AZURE_PORTAL_URL);
			waitForPageLoaded();
			preferencesPage.loginToIntunePortal(intuneCredentials.get("INTUNE_ID"), intuneCredentials.get("INTUNE_PASSWORD"));
			waitForPageLoaded();
			preferencesPage.setIntuneDeviceFilter(deviceOsType);
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("intuneDeviceList");
			waitForPageLoaded();
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("intuneFirstDevice");
			waitForPageLoaded();
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("intuneSeeMoreLink");
			LOGGER.info("Fetching Intune Data for " + deviceOsType + " Device Operating System");
			List<WebElement> intuneDeviceLabelsData = deviceDetailsPage.getElementsOfDeviceDetailsPage("intuneDeviceLabels");
			List<WebElement> intuneDeviceValuesData = deviceDetailsPage.getElementsOfDeviceDetailsPage("intuneDeviceValues");
			for (int intuneCounter = 0; intuneCounter < intuneDeviceLabelsData.size(); intuneCounter++) {
				intuneDeviceData.put(intuneDeviceLabelsData.get(intuneCounter).getText(), intuneDeviceValuesData.get(intuneCounter).getText());
			}
			switchBackToPreviousTab();
			LOGGER.info("Switched To Daas Portal");
		} catch (Exception e) {
			LOGGER.error("Exception occured in fetchIntuneFirstDeviceDataBySelectingOSType" + e.getMessage());
			return intuneDeviceData = null;
		}
		return intuneDeviceData;
	}

	/**
	 * This method verify removing existing device from daas to enroll fake device from emm to avoid conflicts of same serialnumber device
	 * @param serialNumber -serial number to be searched on daas
	 * @return boolean
	 */
	public final static boolean checkDeviceDetailsBySelectingSerialNumber(String serialNumber) {
		boolean flag = false;
		try {
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver());
			deviceDetailsPage = deviceDetailsPage.getInstance();
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver());
			deviceListPage = deviceListPage.getInstance();
			deviceListPage.waitForElementsOfDeviceListPage("serialNumberSearchBox");
			deviceDetailsPage.enterTextForDeviceDetailsPage("serialNumberField", serialNumber);
			if (!deviceListPage.verifyElementsOfDeviceListPage("noElementsDisplayText")) {
				if (deviceListPage.getTextOfDeviceListPage("deviceDetails").equals(serialNumber)) {
					deviceListPage.clickOnElementsOfDeviceListPage("deviceDetails");
					LOGGER.info("Displaying device details for  :" + serialNumber + " device");
					deviceDetailsPage.waitForElementsOfDeviceDetailsPage("daasDeviceNameField");
					return flag = true;
				} else {
					LOGGER.error("Device " + serialNumber + " does not match ");
					return flag;
				}
			} else {
				LOGGER.error("Device " + serialNumber + " not found ");
				return flag;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in checkDeviceDetailsBySelectingSerialNumber" + ex.getMessage());
		}
		return flag;
	}

	/**
	 * This method checks devices list for same serial number of 3rd party serial number to avoid duplicate serial number device conflicts on daas portal 
	 * if same serial number is present on daas device list method will remove the device from device list
	 * @param thirdPartyDeviceSerialNumber - serial number to be searched to remove from daas
	 * @param languageCode
	 * @return
	 */
	public final boolean removeExistingDevicePresentOnDaas(String thirdPartyDeviceSerialNumber, String languageCode) {
		boolean flag = false;
		try {
            DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
            DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			resetTableConfiguration();
			deviceListPage.waitForElementsOfDeviceListPage("serialNumberSearchBox");
			deviceDetailsPage.enterTextForDeviceDetailsPage("serialNumberField", thirdPartyDeviceSerialNumber);
			if (!deviceListPage.verifyElementsOfDeviceListPage("noElementsDisplayText")) {
				if (deviceListPage.getTextOfDeviceListPage("deviceDetails").equals(thirdPartyDeviceSerialNumber)) {
					deviceListPage.clickOnElementsOfDeviceListPage("deviceDetails");
					deviceDetailsPage.waitForElementsOfDeviceDetailsPage("daasDeviceNameField");
					if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("removeButton")) {
						deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("removeButton");
						deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("removeButtonOfPopUp");
						deviceDetailsPage.enterTextForDeviceDetailsPage("serialNumberField", thirdPartyDeviceSerialNumber);
						if (deviceListPage.verifyElementsOfDeviceListPage("noElementsDisplayText")) {
							LOGGER.info("Successfully removed device from daas");
							return flag = true;
						} else {
							return flag;
						}
					} else {
						LOGGER.error("Remove button is not present");
						return flag;
					}
				} else {
					LOGGER.error("Device " + thirdPartyDeviceSerialNumber + " does not match");
					return flag;
				}
			} else {
				LOGGER.info("Device " + thirdPartyDeviceSerialNumber + " not found ");
				return flag = true;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in removeExistingDevicePresentOnDaas" + ex.getMessage());
		}
		return flag;
	}
				
	/**
	 * This method will enroll real Intune ios,windows,android & chromebook based on deviceOsType data provided and returns the boolean value based on the response code
	 * @param deviceOsType 
	 * @param authToken
	 * @param publicKey
	 * @param deviceName
	 * @param deviceSerialNumber
	 * @param deviceOwnership
	 * @param deviceManufacturer
	 * @param deviceModel
	 * @param deviecVersion
	 * @return
	 * @throws Exception
	 */
	public boolean postEMMFakeDeviceEnroll(String deviceOsType, String authToken, String publicKey, String deviceName, String deviceSerialNumber, String deviceOwnership, String deviceManufacturer, String deviceModel, String deviecVersion) throws Exception {
		String apiUrl = null, body = null;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
		String strDate = formatter.format(date);
		if (deviceOwnership.equalsIgnoreCase("Personal")) {
			deviceOwnership = "true";
		} else if (deviceOwnership.equalsIgnoreCase("Company") || deviceOwnership.equalsIgnoreCase("Corporate")) {
			deviceOwnership = "false";
		} else {
			log.error("Error : Ownership values can be either Personal or Company ");
		}
		switch (deviceOsType) {
		case DeviceVariables.IOS_OS:
			apiUrl = host + "api/v1.1/devices/";
			body = "{\"client_type\": \"iOS\",\"public_key\": \"" + publicKey + "\",\"auth_token\": \"" + authToken + "\", \"device_id\": \"" + deviceId + "\",\"device\": {\"byod\": " + deviceOwnership + "," + "\"os\": {\"name\": \"iOS\",\"version\": \"11.4.1\"},\"serial\": \"" + deviceSerialNumber + "\",\"name\": \"" + deviceName + "\",\"device_type\": \"Smartphone\"," + "\"model\": \"" + deviceModel
					+ "\",\"storage\": [{\"size_total\": 11550000,\"encryption_state\": \"Encrypted\",\"size_used\": 7040000}],\"client_version\": \"1.0\",\"make\": \"" + deviceManufacturer + "\"}}\r\n";
			break;
		case DeviceVariables.ANDROID_OS:
			apiUrl = host + "api/v1.1/devices/";
			body = "{\"client_type\": \"Android\",\"public_key\": \"" + publicKey + "\",\"auth_token\": \"" + authToken + "\", \"device_id\": \"" + deviceId + "\",\"device\": {\"byod\": " + deviceOwnership + "," + "\"os\": {\"name\": \"Android\",\"version\": \"7.1\"},\"serial\": \"" + deviceSerialNumber + "\",\"name\": \"" + deviceName + "\",\"device_type\": \"Smartphone\"," + "\"model\": \"" + deviceModel
					+ "\",\"storage\": [{\"size_total\": 11550000,\"encryption_state\": \"Encrypted\",\"size_used\": 7040000}],\"client_version\": \"1.0\",\"make\": \"" + deviceManufacturer + "\"}}\r\n";
			break;
		case DeviceVariables.WINDOWS_OS:
			apiUrl = host + "/api/devices/enroll?auth_token=" + authToken;
			body = "{\"device\":{\"client_type\":\"Agent\",\"public_key\":\"" + publicKey + "\",\"name\":\"" + deviceName + "\",\"device_type\":\"Notebook\",\"processor\":\"Intel Core i7\",\"memory\":\"16 GB 1600 MHz DDR 3 SDRAM\",\"disk\":\"256 GB SATA SSD\",\"graphics\":\"Awesome Graphics Card\",\"operating_system\":\"Windows 8 Pro\",\"serial_number\":\"" + deviceSerialNumber + "\",\"os_architecture\":\"amd64\",\"os_system_ui_lang\":\"en-us\",\"device_id\":\"" + deviceId
					+ "\",\"manufacturer\":\"" + deviceManufacturer + "\",\"product_number\":\"03a9ed-8b\",\"country_code\":\"US\",\"byod\":false,\"device_model\":\"" + deviceModel + "\"},\"analytics\": {\"date_time\":\"" + strDate
					+ "\",\"analytics_class_version\": \"1.0\",\"client_version\": \"1.23.7.1\",\"enroll_method\": \"Company Pin\",\"enroll_status\": \"\",    \"failure_reason\": \"\",\"silent_param_used\": \"Non-Silent\",\"optin_silent\": \"Loud\",\"enrollment_confirmation_selection\": \"Accepted\"}}\r\n";
			break;
		case DeviceVariables.MAC_OS:
			apiUrl = host + "/api/devices/enroll?auth_token=" + authToken;
			body = "{\"device\":{\"client_type\":\"Mac\",\"public_key\":\"" + publicKey + "\",\"name\":\"" + deviceName + "\",\"device_type\":\"Notebook\",\"processor\":\"Intel(R) Core(TM) i7-7700HQ CPU @ 2.80GHz\",\"memory\":\"16 GB 1600 MHz DDR 3 SDRAM\",\"disk\":\"256 GB SATA SSD\",\"graphics\":\"Awesome Graphics Card\",\"operating_system\":\"OS X\",\"serial_number\":\"" + deviceSerialNumber + "\",\"os_architecture\":\"x86_64\",\"os_system_ui_lang\":\"en-us\",\"device_id\":\"" + deviceId
					+ "\",\"manufacturer\":\"" + deviceManufacturer + "\",\"product_number\":\"045hh9ed-8b\",\"country_code\":\"US\",\"byod\":false,\"device_model\":\"" + deviceModel + "\"},\"analytics\": {\"date_time\":\"" + strDate
					+ "\",\"analytics_class_version\": \"1.0\",\"client_version\": \"1.23.7.1\",\"enroll_method\": \"Company Pin\",\"enroll_status\": \"\",    \"failure_reason\": \"\",\"silent_param_used\": \"Non-Silent\",\"optin_silent\": \"Loud\",\"enrollment_confirmation_selection\": \"Accepted\"}}\r\n";
			break;
		case DeviceVariables.CHROMEBOOK:
			apiUrl = host + "api/v1.1/devices/";
			body = "{\"client_type\": \"chrome\",\"public_key\": \"" + publicKey + "\",\"auth_token\": \"" + authToken + "\", \"device_id\": \"" + deviceId + "\",\"device\": {\"byod\": true," + "\"os\": {\"name\": \"Chrome OS \",\"version\": \"" + deviecVersion + "\"},\"serial\": \"" + deviceSerialNumber + "\",\"name\": \"" + deviceName + "\",\"device_type\": \"Notebook\"," + "\"model\": \"" + deviceModel + "\",\"client_version\": \"1.0\",\"make\": \"HP\"}}\r\n";
			break;
		default:
			log.error("Device Operating System : " + deviceOsType + " does not match!!");
		}
		URL url = new URL(apiUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Connection", "keep-alive");
		con.setRequestProperty("Expect", "100-continue");
		con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		con.setRequestProperty("accept-charset", "utf-8");
		con.setDoOutput(true);
		publicKey = new String(Base64.encodeBase64(EnrollFakeDevice.pubKey.getEncoded()));
		log.info("Payload Body = " + body);
		byte[] outputInBytes = body.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();
		int responseCode = con.getResponseCode();
		log.info("Sending 'POST' request to URL : " + url + " And Responce code is: " + responseCode);
		if (responseCode == 204) {
			log.info("Fake Device Enrolled successfully !!");
			return true;
		} else {
			log.info("Fake Device is not enrolled, Please check.");
			return false;
		}
	}
	
	/**
	 * This method verify chrome fake device enrollment 
	 * @param deviceOS - specify device OS typpe 
	 * @param companyName - name of company to be enrolled
	 * @param companyEmailId - email of company to be enrolled
	 * @param companyPin - pin of company to be enrolled
	 * @param languageCode
	 * @return - Boolean value return either true or false
	 */
	public final boolean verifyEMMChromeFakeDaasDevice(String deviceOS, String companyName, String companyEmailId, String companyPin, String languageCode, HashMap<String, String> chromeDeviceData) {
		boolean flag = false;
		try {
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver());
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver());
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver());
			CompanyPin companypinInstance = new CompanyPin();
			EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
			preferencesPage.scrollOnPreferencesPage("preferenceTab");
			preferencesPage.clickByJavaScriptOnPreferencesPage("preferenceTab");
			preferencesPage.clickByJavaScriptOnPreferencesPage("chromebookIntegrationEditButton");
			preferencesPage.enterChromebookIntegrationDetails(PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME, PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD);
			preferencesPage.verifyChromeConfigurationSuccessMessage();
			preferencesPage.scrollOnPreferencesPage("preferenceTab");
			companyPin = companypinInstance.generateCompanyPin(languageCode);
			host = enrollFakeDevice.getEnvironment(System.getProperty("environment"));
			String authToken = enrollFakeDevice.getSigInResponce(companyName, companyEmailId, companyPin);
			String publicKey = enrollFakeDevice.getDeviceprovision(authToken);
			deviceListPage.clickByJavaScriptOnDeviceListPage("devicesTab");
			getDeviceID();
			if (enrollFakeDevice.postEMMFakeDeviceEnroll(deviceOS, authToken, publicKey, chromeDeviceData.get(DeviceVariables.CHROME_SERIAL_NUMBER),
					chromeDeviceData.get(DeviceVariables.CHROME_SERIAL_NUMBER), DeviceVariables.CHROME_OWNERSHIP, null, chromeDeviceData.get(DeviceVariables.CHROME_MODEL),
					chromeDeviceData.get(DeviceVariables.CHROME_VERSION))) {
				refreshPage();
				resetTableConfiguration();
				checkDeviceDetailsBySelectingSerialNumber(chromeDeviceData.get(DeviceVariables.CHROME_SERIAL_NUMBER));
				deviceDetailsPage.verifyEmmToolButton();
				LOGGER.info("Enrolled chrome fake device into daas portal");
				return flag = true;
			} else {
				return flag;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyEMMChromeFakeDaasDevice" + ex.getMessage());
		}
		return flag;
	}
	/**
	 * This method will make a call userSignIn API for the user detail validation and return userAuthToken
	 * 
	 * @param companyName - Company name for the device enrollment
	 * @param companyEmailId - Company emailId for the device enrollment
	 * @param companyPin - Company pin for the device enrollment
	 * @return authToken - userAuthToken received in the response body for the API
	 */
	public String getSigInResponceForIncident(String companyName, String companyEmailId, String companyPin) throws Exception {
		String apiUrl = hostSearchService + "api/users/sign_in";
		URL url = new URL(apiUrl);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		String body = "{\"user\":{\"authority\":\"hptpm/otp\",\"detected_username\":\"" + companyName + "\",\"email\":\"" + companyEmailId + "\",\"credentials\":\"" + companyPin + "\"}}";
		byte[] outputInBytes = body.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();
		int responseCode = con.getResponseCode();
		log.info("Sending 'POST' request to URL : " + url + " And Responce code is: " + responseCode);
		String response = getResponce(responseCode, con);
		JSONObject json = new JSONObject(response.toString());
		String authToken = (String) json.get("authentication_token");
		log.info("AuthToken from SignIn API is: " + authToken);
		return authToken;
	}
	/**
	 * This method will make a GET call to the server for the public key
	 * 
	 * @param authToken - User Auth token for the provisioning call
	 * @return publicKey - Server public key for the API calls
	 */
	public String getDeviceprovisionForIncident(String authToken) throws Exception {
		String apiUrl = hostSearchService + "api/v1/devices/provision?auth_token=" + authToken;
		URL url = new URL(apiUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("accept", "application/json");
		int responseCode = con.getResponseCode();
		log.info("Sending 'GET' request to URL : " + url + " And Responce code is: " + responseCode);
		String response = getResponce(responseCode, con);
		JSONObject json = new JSONObject(response);
		JSONObject jsonObj1 = (JSONObject) json.get("provisioning_signature_chain");
		JSONArray jsonObj2 = (JSONArray) jsonObj1.get("signing_keys");
		JSONObject jsonObj3 = (JSONObject) jsonObj2.get(0);
		String publicKey = (String) jsonObj3.get("public_key");
		log.info("Public Key is: " + publicKey);
		return publicKey;
	}
	/**
	 * This method will make a enroll device call and return the boolean value based on the response code
	 * 
	 * @param authToken - authToken for the logged in user
	 * @param publicKey - Device public key
	 * @param deviceName - Name of the device which needs to be enrolled
	 * @param deviceId - DeviceId of the device which needs to be enrolled
	 * @param deviceSerialNumber - Serial number of the device which needs to be enrolled
	 * @return boolean - True if the device enroll call passes else false
	 */
	public boolean postDeviceEnrollForIncident(String authToken, String publicKey, String deviceName, String deviceId, String deviceSerialNumber) throws Exception {
		String apiUrl = hostSearchService + "api/devices/enroll?auth_token=" + authToken;
		URL url = new URL(apiUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
//		con.setRequestProperty("Connection", "keep-alive");
//		con.setRequestProperty("Expect", "100-continue");
//		con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//		con.setRequestProperty("accept-charset", "utf-8");
		con.setDoOutput(true);
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
		String strDate = formatter.format(date);
		publicKey = new String(Base64.encodeBase64(EnrollFakeDevice.pubKey.getEncoded()));
		String body = "{\"device\":{\"client_type\":\"Agent\",\"public_key\":\"" + publicKey + "\",\"name\":\"" + deviceName + "\",\"device_type\":\"Notebook\",\"processor\":\"Intel Core i7\",\"memory\":\"16 GB 1600 MHz DDR 3 SDRAM\",\"disk\":\"256 GB SATA SSD\",\"graphics\":\"Awesome Graphics Card\",\"operating_system\":\"Windows 8 Pro\",\"serial_number\":\"" + deviceSerialNumber + "\",\"os_architecture\":\"amd64\",\"os_system_ui_lang\":\"en-us\",\"device_id\":\"" + deviceId
				+ "\",\"manufacturer\":\"Hewlett-Packard\",\"product_number\":\"03a9ed-8b\",\"country_code\":\"US\",\"byod\":false,\"device_model\":\"HP EliteBook 850\"},\"analytics\": {\"date_time\":\"" + strDate
				+ "\",\"analytics_class_version\": \"1.0\",\"client_version\": \"1.23.7.1\",\"enroll_method\": \"Company Pin\",\"enroll_status\": \"\",    \"failure_reason\": \"\",\"silent_param_used\": \"Non-Silent\",\"optin_silent\": \"Loud\",\"enrollment_confirmation_selection\": \"Accepted\"}}\r\n";
		byte[] outputInBytes = body.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();
		int responseCode = con.getResponseCode();
		log.info("Sending 'POST' request to URL : " + url + " And Responce code is: " + responseCode);
		if (responseCode == 204) {
			log.info("Fake Device Enrolled successfully !!");
			return true;
		} else {
			log.info("Fake Device is not enrolled, Please check.");
			return false;
		}

	}
	/* This method will make a device authentication call and return device authorization token
			 * 
			 * @param deviceId - deviceId for the enrolled device
			 * @param deviceIdSignature - deviceId Signature generated using public key and private key
			 * @return boolean - True if the API response code is 200 else return False
			 */
			public boolean deviceAuthenticateForIncident(String deviceId, String deviceIdSignature) throws Exception {
				String apiUrl = hostSearchService + "api/v1.1/devices/authenticate";
				URL url = new URL(apiUrl);
				// Make a device authentication post call
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json");
				String body = "{\"device\":{\"device_id\":\"" + deviceId + "\" , \"device_id_signature\":\"" + deviceIdSignature + "\"}}";
				con.setDoOutput(true);
				byte[] outputInBytes = body.getBytes("UTF-8");
				OutputStream os = con.getOutputStream();
				os.write(outputInBytes);
				os.close();
				// Get the response code for the device authentication call
				int responseCode = con.getResponseCode();
				log.info("Response code for device authenticate: " + responseCode);
				// Validation for the device authentication API response code
				if (responseCode == 200) {
					String response = getResponce(responseCode, con);
					JSONObject json = new JSONObject(response.toString());
					deviceAuthToken = (String) json.get("token");
					return true;
				} else {
					return false;
				}
			}

	/**
	 * This method will enroll a fake device with fixed serial number and return the device details of the enrolled device
	 *
	 * @param companyName - Name of the company under which the device is enrolled
	 * @param companyPin - Pin of the company under which the device is enrolled
	 * @param companyEmailId - Email of the user under which the device is enrolled
	 * @return deviceDetails - Hash containing the device details
	 */
	public static HashMap<String, String> enrollFakeDeviceWithFixedSerialNumber(String companyName, String companyPin, String companyEmailId, String deviceSerialNumber) throws Exception {
		EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
		host = enrollFakeDevice.getEnvironment(System.getProperty("environment"));
		String deviceName = enrollFakeDevice.generateDeviceKeysDetails();
		HashMap<String, String> deviceEnrollmentDetails = new HashMap<String, String>();
		if (!(host.contains("eu"))) {
			int response = enrollFakeDevice.getLocationRegistryResponce(companyPin);
			if (response == 200) {
				String authToken = enrollFakeDevice.getSigInResponce(companyName, companyEmailId, companyPin);
				String publicKey = enrollFakeDevice.getDeviceprovision(authToken);
				enrollFakeDevice.getDeviceID();
				enrollFakeDevice.postDeviceEnroll(authToken, publicKey, deviceName, deviceId, deviceSerialNumber);
				deviceEnrollmentDetails.put("userAuthToken", authToken);
				deviceEnrollmentDetails.put("deviceName", deviceName);
				deviceEnrollmentDetails.put("deviceSerialNumber", deviceSerialNumber);
			} else {
				return deviceEnrollmentDetails;
			}
		} else {
			String authToken = enrollFakeDevice.getSigInResponce(companyName, companyEmailId, companyPin);
			String publicKey = enrollFakeDevice.getDeviceprovision(authToken);
			enrollFakeDevice.getDeviceID();
			enrollFakeDevice.postDeviceEnroll(authToken, publicKey, deviceName, deviceId, deviceSerialNumber);
			deviceEnrollmentDetails.put("userAuthToken", authToken);
			deviceEnrollmentDetails.put("deviceName", deviceName);
			deviceEnrollmentDetails.put("deviceSerialNumber", deviceSerialNumber);
		}
		return deviceEnrollmentDetails;
	}

    /**
     * Enrolls a fake IOT device using minimal parameters via REST API.
     */
    public boolean enrollIOTFakeDeviceWithMinimalParams(String cpin, String deviceId, String tenantId, String serialNumber, String productNumber, String biosId) throws Exception {
        host = getEnvironmentSpecificData(System.getProperty("environment"), "hostURL");
        LOGGER.info("Enrollment host URL: {}", host);
        String authToken = null;
        CSRGenerator csr = new CSRGenerator();
        try {
            authToken = enrollDeviceUsingRestApi(cpin, deviceId);
        } catch (Exception ce) {
            LOGGER.error("Network error during fake device enrollment: {}", ce.getMessage());
            return false;
        }
        if (authToken == null || authToken.isEmpty()) {
            LOGGER.error("Failed to obtain auth token for device enrollment. Aborting.");
            return false;
        }
        String csrToken = csr.enrollDevice(deviceId);
        return enrollDeviceEnrollmentRestApi(serialNumber, deviceId, csrToken, tenantId, authToken, productNumber, biosId);
    }

    /**
     * Enrolls a fake device using the ESt REST API and returns the auth token.
     */
    public String enrollDeviceUsingRestApi(String cpin, String deviceId) {
        String apiUrl = getEnvironmentSpecificData(System.getProperty("environment"), "IOTDiscoveryURL");
        Map<String, Object> payload = new HashMap<>();
        
        // If cpin is null, use deviceId as pin value and set pinType to dpin
        if (cpin == null) {
            payload.put("pinType", "dpin");
            payload.put("pin", deviceId);
        } else {
            payload.put("pinType", "cpin");
            payload.put("pin", cpin);
        }
        
        payload.put("deviceId", deviceId);
        payload.put("deviceIdAlt", deviceId);
        try {
            Response response = RestAssured.given()
                    .relaxedHTTPSValidation()
                    .contentType("application/json")
                    .body(payload)
                    .post(apiUrl);
            LOGGER.info("REST API enrollment response: {}", response.getBody().asString());
            if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
                JSONObject json = new JSONObject(response.getBody().asString());
                if (json.has("service_map_data")) {
                    return json.getJSONArray("service_map_data").getJSONObject(0).getString("service_jwt_token");
                }
            }
            return null;
        } catch (Exception e) {
            LOGGER.error("Error during ESt API enrollment: ", e);
            return null;
        }
    }

    /**
     * Builds the IoT enrollment request JSON string using the provided parameters.
     *
     * @param serialNumber Serial number for the device
     * @param deviceId     Device ID of the device
     * @param tenantId     Tenant ID under which the device is enrolled
     * @param accessToken  Access token for authentication
     * @param csr          CSR token for the device
     * @return JSON string for the IoT enrollment request
     */
    private String buildIotEnrollmentRequest(String serialNumber, String deviceId, String tenantId, String accessToken, String csr, String productNumber, String biosId) throws Exception {
        JSONParser jsonParser = new JSONParser();
        String obj2 = (jsonParser.parse(new FileReader(ConstantPath.IMPORT_PATH + getEnvironmentSpecificData(System.getProperty("environment"), "IOT_ENROLL_DEVICE")))).toString();
        JSONObject obj1 = new JSONObject(obj2);
        obj1.put("csr", csr);
        obj1.put("deviceId", deviceId);
        obj1.put("tenantId", tenantId);
        obj1.put("token", accessToken);
        ((JSONObject) obj1.get("data")).put("device_id", deviceId);
        ((JSONObject) obj1.get("data")).put("name", serialNumber);
        ((JSONObject) obj1.get("data")).put("serial_number", serialNumber);
		((JSONObject) obj1.get("data")).put("deviceOwnerEmail", "TBFakeDevice@yopmail.com");
		((JSONObject) obj1.get("data")).put("deviceOwnerName", "TBFakeDeviceUser");
		((JSONObject) obj1.get("data")).put("bod", "02/14/2025");
		((JSONObject) obj1.get("data")).put("client_type", "Agent");
		((JSONObject) obj1.get("data")).put("device_model", "HP EliteBook 6 G1a 14 inch Notebook Next Gen AI PC");
		((JSONObject) obj1.get("data")).put("device_type", "Notebook");
		((JSONObject) obj1.get("data")).put("manufacturer", "HP");
		((JSONObject) obj1.get("data")).put("memory", "32 GB  5600 MHz");
		((JSONObject) obj1.get("data")).put("operating_system", "Microsoft Windows 11 Pro");
		((JSONObject) obj1.get("data")).put("product_number", productNumber);
		((JSONObject) obj1.get("data")).put("uuid", biosId);
        String body = obj1.toString();
        return body;
    }


    /**
     * Enrolls device using REST API for device enrollment response.
     */
    public boolean enrollDeviceEnrollmentRestApi(String serialNumber, String deviceID, String csrToken, String tenantID, String authToken, String productNumber, String biosId) throws Exception {
        String apiUrl = host + "api/1.0/devices/iot/enrollment";
        String payload = buildIotEnrollmentRequest(serialNumber, deviceID, tenantID, authToken, csrToken, productNumber, biosId);

        LOGGER.info("Enrollment API URL: {}", apiUrl);
		LOGGER.info("BODY : {}", payload);
        try {
            Response response = RestAssured.given()
                    .relaxedHTTPSValidation()
                    .contentType("application/json")
                    .body(payload)
                    .post(apiUrl);
            LOGGER.info("URL : {}", apiUrl);
            LOGGER.info("BODY : {}", payload);
            LOGGER.info("responsecode : {}", response.getStatusCode());
            LOGGER.info("responsebody : {}", response.getBody().asString());
            if (response.getStatusCode() == 200) {
                LOGGER.info("Device enrollment succeeded.");
                return true;
            } else {
                LOGGER.error("Device enrollment failed. Response: {}", response.getBody().asString());
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Exception during device enrollment REST API call: ", e);
            return false;
        }
    }

}