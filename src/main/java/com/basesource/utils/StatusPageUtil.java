package com.basesource.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.basesource.action.CommonMethod;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class StatusPageUtil extends CommonMethod {
	public static String pageId;
    public static String apiKey;
	public static final Logger LOGGER = LogManager.getLogger(StatusPageUtil.class);
	 static {
	        try {
	            ObjectReader reader = new ObjectReader();
	            Properties props = reader.getObjectRepository("StatusAPIPage");
	            pageId = props.getProperty("page.id");
	            apiKey = props.getProperty("api.key");
	            RestAssured.baseURI = props.getProperty("base.url");
	        } catch (IOException e) {
	            LOGGER.error("Failed to load base.url for RestAssured", e);
	        }
	    }
	
	 public StatusPageUtil instance;
	 
	    
		public StatusPageUtil getInstance() throws IOException {
			if (instance == null) {
				synchronized (AzureUtil.class) {
					if (instance == null) {
						instance = new StatusPageUtil();
					}
				}
			}
			return instance;
		}

	    private static String getEndpoint(String componentId) {
	        return String.format("/v1/pages/%s/components/%s", pageId, componentId);
	    }

	    public static Response updateComponentStatus(String componentId, String status) {
	    	String endpoint = getEndpoint(componentId);
	    	String body = String.format(
	    	        "{\n" +
	    	        "  \"component\": {\n" +
	    	        "    \"status\": \"%s\",\n" +
	    	        "    \"showcase\": true\n" +
	    	        "  }\n" +
	    	        "}", status);

	        return RestAssured
	                .given()
	                .relaxedHTTPSValidation()
	                .header("Content-Type", "application/json")
	                .header("Authorization", "OAuth " + apiKey)
	                .body(body)
	                .patch(endpoint);
	    }

	    public static String fetchComponentStatus(String componentId) {
	        Response response = RestAssured
	                .given()
	                .relaxedHTTPSValidation()
	                .header("Authorization", "OAuth " + apiKey)
	                .get(getEndpoint(componentId));

	        return response.jsonPath().getString("status");
	    }

}
