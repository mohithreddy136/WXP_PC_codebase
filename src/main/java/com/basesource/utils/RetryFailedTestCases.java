package com.basesource.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTestCases implements IRetryAnalyzer {
	private final static Logger LOGGER = LogManager.getLogger(RetryFailedTestCases.class);
	public int retryCount = 0;
	public int maxRetryCount = 2;
	public int maxRetryCountRegression = 1;
	public static int retryCountVal = 0;

	public void setRetryCount(int retryCount) {
		retryCountVal = retryCount;
	}

	// Below method returns 'true' if the test method has to be retried else 'false'
	// and it takes the 'Result' as parameter of the test method that just ran
	public boolean retry(ITestResult result) {
		try {
		if ((result.getTestContext().getCurrentXmlTest().getIncludedGroups().get(0).toString().contains("PRODUCTION"))||(result.getTestContext().getCurrentXmlTest().getIncludedGroups().get(0).toString().equals("WORKFLOW_PRODUCTION"))) {
			if (retryCount < maxRetryCount) {
				LOGGER.info("Retrying test " + result.getName() + " with status " + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).");
				retryCount++;
				setRetryCount(retryCount);
				return true;
			} else {
				retryCountVal++;
				return false;
			}
		} else {
			if (retryCount < maxRetryCountRegression) {
				LOGGER.info("Retrying test " + result.getName() + " with status " + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).");
				retryCount++;
				setRetryCount(retryCount);
				return true;
			} else {
				retryCountVal++;
				return false;
			}

		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.info(e.getMessage());
			return false;
		}
		
	}

	public String getResultStatusName(int status) {
		String resultName = null;
		if (status == 1)
			resultName = "SUCCESS";
		if (status == 2)
			resultName = "FAILURE";
		if (status == 3)
			resultName = "SKIP";
		return resultName;
	}

}