package com.basesource.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.InputMismatchException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.launchdarkly.client.LDClient;
import com.launchdarkly.client.LDConfig;
import com.launchdarkly.client.LDUser;

/**
 * This is a Launch Darkly helper static class.
 *
 * <p>
 * Anyone needing to retrieve a LaunchDarkly Feature Toggle should fetch the variation via the <code>enabled(String feature_name, String userID)</code> method
 * </p>
 *
 */

public class LaunchDarkly {
	private volatile LaunchDarkly instance;
	private static final Logger log = LoggerFactory.getLogger(LaunchDarkly.class);
	private static volatile LDClient ldClient = null;
	private static Properties launchDarklyConfigProperties;
	private static ObjectReader launchDarklyPropertiesReader = new ObjectReader();

	/**
	 * This method is for initializing the LaunchDarkly config and return the instance of the LaunchDarkly for the toggle usage
	 */
	public synchronized LaunchDarkly getInstance() {
		if (instance == null) {
			instance = new LaunchDarkly();

			// Mirror same config used in Rails (unicorn.rb)
			LDConfig config = new LDConfig.Builder().startWaitMillis(10000).connectTimeout(10).pollingIntervalMillis(30).stream(true).flushInterval(30).capacity(10000).build();
			// This key is fetched from the command used to run the SUITE i.e. the env set
			// in pom.xml
			String key = getEnvironment(System.getProperty("environment"));
			if (StringUtils.isEmpty(key)) {
				log.error("LaunchDarkly sdk-key is blank... features will all be false");
			} else {
				ldClient = new LDClient(key, config);
				if (ldClient.initialized()) {
					log.info("Initialized LaunchDarkly...");
				} else {
					// In this case, you will never get a positive result, LD will always return
					// false
					log.error("LaunchDarkly is having issues initializing...");
				}
			}
		}
		return instance;
	}

	/**
	 * This method will destroy the singleton instance of LaunchDarkly
	 */
	public void destroy() {
		if (ldClient != null) {
			try {
				ldClient.close();
			} catch (IOException e) {
				if (log.isInfoEnabled()) {
					log.info("LD Failed to close");
					e.printStackTrace();
				}
			} finally {
				ldClient = null;
			}
		}
		instance = null;
	}

	/**
	 * Fetch the current Launch Darkly feature toggle state
	 * 
	 * WARNING: if LaunchDarkly wasn't initialised properly (as seen by errors in the logs) this will always return false.
	 * 
	 * @param feature - the feature toggle string in Launch Darkly
	 * @param userId - the unique identifier for the user requesting the feature - pass null to fetch anonymously.
	 * @param userEmail - if UserId is set then email should be passed too
	 * @return boolean - true, the toggle is on, false, the toggle is off
	 */
	public boolean enabled(String feature, String userId, String userEmail, Boolean defaultValue) {
		if (ldClient == null) {
			if (log.isInfoEnabled()) {
				log.info("LaunchDarkly sdk-key is blank... this feature {} will be defaultValue {}", feature, defaultValue);
			}
			return defaultValue;
		}
		LDUser ldUser = null;

		if (StringUtils.isEmpty(userId)) {
			SecureRandom random = new SecureRandom();
			String anonymous = new BigInteger(128, random).toString(32);
			ldUser = new LDUser.Builder(anonymous).anonymous(true).build();
		} else {
			ldUser = new LDUser.Builder(userId).email(userEmail).build();
		}

		return ldClient.boolVariation(feature, ldUser, defaultValue);
	}

	/**
	 * Fetch the current Launch Darkly feature toggle state
	 * 
	 * WARNING: if LaunchDarkly wasn't initialised properly (as seen by errors in the logs) this will always return false.
	 * 
	 * @param feature - the feature toggle string in Launch Darkly
	 * @param userId - the unique identifier for the user requesting the feature - pass null to fetch anonymously.
	 * @param userEmail - if UserId is set then email should be passed too
	 * @return String - true, the toggle is on, false, the toggle is off
	 */
	public String getValue(String feature, String userId, String userEmail, String defaultValue) {
		if (ldClient == null) {
			if (log.isInfoEnabled()) {
				log.info("LaunchDarkly sdk-key is blank... this feature {} will be defaultValue {}", feature, defaultValue);
			}
			return defaultValue;
		}
		LDUser ldUser = null;
		if (userId == null) {
			SecureRandom random = new SecureRandom();
			String anonymous = new BigInteger(128, random).toString(32);
			ldUser = new LDUser.Builder(anonymous).anonymous(true).build();
		} else {
			ldUser = new LDUser.Builder(userId).email(userEmail).build();
		}
		return ldClient.stringVariation(feature, ldUser, defaultValue);
	}

	/**
	 * Return the SDK key fetched from the launchDarklyProperties file
	 * 
	 * @param environment - Environment for which the toggle is supposed to be used eg: Latest , Stable , Staging , Production
	 */
	protected static String getEnvironment(String environment) {
		try {
			// This will load the LaunchDarklyConfig.properties file
			launchDarklyConfigProperties = launchDarklyPropertiesReader.getObjectRepository("LaunchDarklyConfig");
			// This will fetch the SDK key based on the env passed
			if (environment.toUpperCase().contains("LATEST"))
				return launchDarklyConfigProperties.getProperty("Latest");
			else if (environment.toUpperCase().contains("STABLE"))
				return launchDarklyConfigProperties.getProperty("Stable");
			else if (environment.toUpperCase().contains("STAGE"))
				return launchDarklyConfigProperties.getProperty("Staging");
			else if (environment.toUpperCase().contains("PROD"))
				return launchDarklyConfigProperties.getProperty("Production");
			else if (environment.toUpperCase().contains("PERFORMANCE"))
				return launchDarklyConfigProperties.getProperty("Performance");
			else if (environment.toUpperCase().contains("PENTEST"))
				return launchDarklyConfigProperties.getProperty("Performance");
            else if (environment.toUpperCase().contains("VENEER"))
                return launchDarklyConfigProperties.getProperty("Staging");
			else
				log.info("\"Provided : " + environment + " environment is wrong");
			throw new InputMismatchException("You can use :LATEST , STABLE , STAGING and PRODUCTION only ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
