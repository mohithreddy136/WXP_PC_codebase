package com.daasui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class RunTests {
	public static void main(String[] args) {
		// Create object of TestNG Class
		TestNG runner = new TestNG();

		// Create a list of String
		List<String> suitefiles = new ArrayList<String>();

		// TODO: Load the suite file. Don't hard code it
		String fileName = "suites/" + System.getProperty("suiteXmlFile");
		InputStream initialStream = RunTests.class.getClassLoader().getResourceAsStream(fileName);

		// setTestSuites needs a physical file path. Hence save this stream to disk and get its path to set to setTestSuites
		try {
			// Get the byte array of the file stream
			byte[] buffer = new byte[initialStream.available()];
			initialStream.read(buffer);

			// Create a new temp suite file
			File targetFile = new File(fileName);
			targetFile.getParentFile().mkdir();
			targetFile.createNewFile();

			// Save the temp file
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);

			// Add the suite file path.
			suitefiles.add(targetFile.getPath());
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Add xml file which you have to execute
		runner.setTestSuites(suitefiles);

		// finally execute the runner using run method
		runner.run();
	}
}