package com.basesource.utils;

import com.amazonaws.auth.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClientBuilder;
import com.amazonaws.services.iot.model.DescribeEndpointRequest;
import com.amazonaws.services.iotdata.AWSIotData;
import com.amazonaws.services.iotdata.AWSIotDataClientBuilder;
import com.amazonaws.services.iotdata.model.DeleteThingShadowRequest;
import com.amazonaws.services.iotdata.model.GetThingShadowRequest;
import com.amazonaws.services.iotdata.model.GetThingShadowResult;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.basesource.action.CommonMethod;
import com.daasui.constants.AWSSDKConstants;

import java.net.URI;

public class AWSIOTUtils extends CommonMethod {

    private static AWSIotData iotDataClient;
    private static AWSIot iotClient;
    private static String environment = System.getProperty("environment");

    // New method to clean clients
    public static void resetClients() {
        iotClient = null;
        iotDataClient = null;
    }

    private static AWSCredentialsProvider getAssumedRoleCredentialsFinal(String roleArn, String externalId, String region) {
        AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider("pretest_profile"))
                .withRegion(region)
                .build();

        AssumeRoleRequest assumeRoleRequest = new AssumeRoleRequest()
                .withRoleArn(roleArn)
                .withRoleSessionName("TestSession-" + System.currentTimeMillis())
                .withExternalId(externalId);

        AssumeRoleResult assumeRoleResult = stsClient.assumeRole(assumeRoleRequest);
        Credentials creds = assumeRoleResult.getCredentials();

        return new AWSStaticCredentialsProvider(
                new BasicSessionCredentials(
                        creds.getAccessKeyId(),
                        creds.getSecretAccessKey(),
                        creds.getSessionToken()
                )
        );
    }

    /**
     * Get the AWS IoT client with fresh credentials
     */
    public static AWSIot getIotClient() {
        if (iotClient == null) {
            try {
                String region = getAWSRegion();
                String roleArn = getEnvironmentSpecificData(environment, "AWS_ROLE_ARN");
                String externalId = getEnvironmentSpecificData(environment, "AWS_EXTERNAL_ID");

                // Get fresh credentials for IoT client
                AWSCredentialsProvider iotRoleProvider = getAssumedRoleCredentialsFinal(roleArn, externalId, region);

                // Create client with explicitly assumed role credentials
                iotClient = AWSIotClientBuilder.standard()
                        .withCredentials(iotRoleProvider)
                        .withRegion(region)
                        .build();

            } catch (Exception e) {
                LOGGER.error("Exception initializing AWSIoT client: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to initialize AWS IoT client", e);
            }
        }
        return iotClient;
    }

    /**
     * Get the AWS IoT Data client with fresh credentials
     */
    public static AWSIotData getIotDataClient() {
        if (iotDataClient == null) {
            try {
                String region = getAWSRegion();
                String roleArn = getEnvironmentSpecificData(environment, "AWS_ROLE_ARN");
                String externalId = getEnvironmentSpecificData(environment, "AWS_EXTERNAL_ID");

                AWSCredentialsProvider iotRoleProvider = getAssumedRoleCredentialsFinal(roleArn, externalId, region);

                String endpoint = fetchIotEndpoint(iotRoleProvider);
                /*String endpoint = getIotClient()
                        .describeEndpoint(new DescribeEndpointRequest().withEndpointType(AWSSDKConstants.IOT_DATA_ATS))
                        .getEndpointAddress();*/
                iotDataClient = AWSIotDataClientBuilder.standard()
                        .withCredentials(iotRoleProvider)
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                                "https://" + endpoint, region))
                        .build();
            } catch (Exception e) {
                LOGGER.error("Exception initializing AWSIotData client: {}", e.getMessage(), e);
            }
        }
        return iotDataClient;
    }

    private static AssumeRoleRequest createAssumeRoleRequest() {
        return new AssumeRoleRequest()
                .withRoleArn(getEnvironmentSpecificData(environment, "AWS_ROLE_ARN"))
                .withRoleSessionName("IoTClientSession-" + System.currentTimeMillis())
                .withExternalId(getEnvironmentSpecificData(environment, "AWS_EXTERNAL_ID"));
    }

    private static String fetchIotEndpoint(AWSCredentialsProvider iotRoleProvider) throws Exception {
        try {
        return AWSIotClientBuilder.standard()
                .withCredentials(iotRoleProvider)
                .withRegion(getAWSRegion())
                .build()
                .describeEndpoint(new DescribeEndpointRequest()
                        .withEndpointType(AWSSDKConstants.IOT_DATA_ATS))
                .getEndpointAddress();
        } catch (Exception e) {
            LOGGER.error("Failed to fetch IoT endpoint: {}", e.getMessage(), e);
            throw new RuntimeException("IoT endpoint discovery failed", e);
        }
    }

    /**
     * Get the device shadow for a given thing name and shadow name.
     *
     * @param thingName The name of the IoT thing.
     * @param shadowName The name of the shadow.
     * @return The device shadow as a JSON string.
     */
    public String getDeviceShadow(String thingName, String shadowName) {
        try {
            GetThingShadowRequest request = new GetThingShadowRequest();
            request.setThingName(thingName);
            request.setShadowName(shadowName);

            GetThingShadowResult response = iotDataClient.getThingShadow(request);
            return new String(response.getPayload().array());
        } catch (Exception e) {
            System.err.println("Failed to get device shadow: " + e.getMessage());
            return null;
        }
    }

    /**
     * Delete the device shadow for a given thing name and shadow name.
     *
     * @param thingName The name of the IoT thing.
     * @param shadowName The name of the shadow.
     * @return The response from the delete operation.
     */
    public void deleteDeviceShadow(String thingName, String shadowName) {
        try {
            DeleteThingShadowRequest request = new DeleteThingShadowRequest();
            request.setThingName(thingName);
            request.setShadowName(shadowName);
            getIotDataClient().deleteThingShadow(request);
        } catch (Exception e) {
            LOGGER.error("Error removing shadow:{} for device:{} Reason: {}", shadowName, thingName, e.getStackTrace());
        }
    }

    /**
     * This method is used to get the AWS region based on the environment.
     * @return The AWS region based on the environment.
     */
    public static String getAWSRegion(){
        try {
        if(environment.contains("US")){
            return "us-west-2";
        }
        } catch (Exception e) {
            LOGGER.error("Error getting region", e.getStackTrace());
        }
        return "eu-central-1";
    }
}
