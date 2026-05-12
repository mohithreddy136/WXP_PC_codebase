package com.basesource.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.basesource.action.CommonMethod;
import com.daasui.constants.ConstantPath;
import com.google.common.base.Strings;

public class ObjectReader {
	private Properties property = new Properties();
	protected final static Logger LOGGER = LogManager.getLogger(ObjectReader.class);
	private String osName = System.getProperty("osName");
	public final Properties getObjectRepository(String filename) throws IOException {
		// Read object repository file
		File propertiesFile = new File(ConstantPath.PROPERTIES_FOLDER_PATH + filename + ".properties");
		InputStream stream;
		if (propertiesFile.exists()) {
			stream = new FileInputStream(propertiesFile);
		} else {
			stream = ObjectReader.class.getClassLoader().getResourceAsStream("properties/" + filename + ".properties");
		}

		// load all objects
		property.load(stream);
		return property;
	}

	public final Properties getLanguageObjectRepository(String filename, String language) throws IOException {
		// Read object repository file
		File propertiesFile = new File(ConstantPath.LOCALISATION_FOLDER_PATH + filename + File.separator + language + ".properties");
		InputStream stream;
		if (propertiesFile.exists()) {
			stream = new FileInputStream(propertiesFile);
		} else {
			// URLs of the resources inside a JAR are case sensitive.
			stream = ObjectReader.class.getClassLoader().getResourceAsStream("locales/" + filename + "/" + language + ".properties");
		}

		// load all objects
		BufferedReader in = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
		property.load(in);
		return property;
	}

	public final Properties getCredentials(String credentials) throws IOException {
		// Read object repository file
		try {
			File propertiesFile =null;
			// Due to case sensitivity on LINUX, convertEnvironmentCase() is used to convert the case environment name in required format to match with properties file name format.
			if (!Strings.isNullOrEmpty(osName) && (osName.toUpperCase().contains("LINUX")))
			{
				credentials = CommonMethod.convertEnvironmentCase(credentials);
			}
			else
				credentials = credentials.toUpperCase();
			if(System.getProperty("uiVersion").equalsIgnoreCase("VENEER3")&&(System.getProperty("environment").equalsIgnoreCase("US-PRODUCTION")||System.getProperty("environment").equalsIgnoreCase("EU-PRODUCTION")))
			{
				propertiesFile = new File(ConstantPath.CREDENTIALS_FOLDER_PATH + File.separator + credentials + "V3.properties");
			}else{
				propertiesFile = new File(ConstantPath.CREDENTIALS_FOLDER_PATH + File.separator + credentials + ".properties");
			}
			InputStream stream;
			if (propertiesFile.exists()) {
				stream = new FileInputStream(propertiesFile);
			} else {
				// URLs of the resources inside a JAR are case sensitive.
				stream = ObjectReader.class.getClassLoader().getResourceAsStream("credentials/" + credentials + ".properties");
			}
	
			// load all objects
			property.load(stream);
		} catch (Exception e) {
			LOGGER.error("Exception in specified environment format. It should match environment specific properties file format. " + e.getMessage());
		}
		return property;
	}
}