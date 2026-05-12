#!/bin/bash

# Get the current timestamp
CURRENT_TIMESTAMP=$(date +"%Y%m%d%H%M%S")

# Terminate all Microsoft Edge WebDriver processes
taskkill //F //IM msedgedriver.exe

# Print a success message
echo "Taskkill command executed successfully."

## Extract the environment variable from pom.xml using Maven
#ENV="${environment}"
#echo "Environment: $ENV"  # Debug line
## Extract the suiteXmlFile value from pom.xml using xmlstarlet
#SUITENAME="${suiteXmlFile}"
#echo "suiteXmlFile: $SUITENAME"  # Debug line
#
#USER_DIR=$(pwd)
#USER_DIR=$(wslpath -a "$USER_DIR")

# Define your role ARN and session name
ROLE_ARN="arn:aws:iam::434851320724:role/wex-qa-automation-role"
SESSION_NAME="ProductionMonitoring_$(date +%Y%m%d%H%M%S)"
EXTERNAL_ID="UM03UqItydclientUwiOY914bx"
DURATION_SECONDS=1500  # 25 minutes

echo $SESSION_NAME

# Use AWS STS to assume the role and get temporary credentials
response=$(aws sts assume-role --role-arn $ROLE_ARN --role-session-name $SESSION_NAME --duration-seconds $DURATION_SECONDS --profile "testway" --external-id "UM03UqItydclientUwiOY914bx")

# Extract the credentials from the response using jq
AWS_ACCESS_KEY_ID=$(echo $response | jq -r '.Credentials.AccessKeyId')
AWS_SECRET_ACCESS_KEY=$(echo $response | jq -r '.Credentials.SecretAccessKey')
AWS_SESSION_TOKEN=$(echo $response | jq -r '.Credentials.SessionToken')

# Set the temporary credentials in the AWS CLI configuration
aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID
aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY
aws configure set aws_session_token $AWS_SESSION_TOKEN

echo "Temporary AWS CLI credentials have been configured successfully."

# Optional: Verify the temporary configuration
echo "Configured AWS Access Key ID: $(aws configure get aws_access_key_id)"
echo "Configured AWS Secret Access Key: $(aws configure get aws_secret_access_key)"
echo "Configured AWS Session Token: $(aws configure get aws_session_token)"

report_folder="../target/surefire-reports"
if [ -d "$report_folder" ]; then
    echo "Report folder '$report_folder' exists. Copying files..."
    if [ -d "../test-screenshots" ]; then
        cp -r "../test-screenshots" "$report_folder"
    else
        echo "Source directory '../test-screenshots' does not exist."
    fi

    if [ -d "../test-report" ]; then
        cp -r "../test-report" "$report_folder"
    else
        echo "Source directory '../test-report' does not exist."
    fi
    echo "Files copied successfully to '$report_folder'"
else
    echo "Report folder '$report_folder' does not exist."
fi

# Print the absolute path of the file
RESULT_FILE=$(find .. -name "testng-results.xml" | head -n 1)

if [ -f "$RESULT_FILE" ]; then
    echo "File '$RESULT_FILE' exists. Copying to S3..."
    aws s3 cp "$RESULT_FILE" s3://swapps-builds/Selenium_DaaS_Automation/staging/Test_Results/TestWay/$CURRENT_TIMESTAMP/
    echo "File '$RESULT_FILE' copied successfully."
else
    echo "Error: testng-results.xml not found."
fi

# Use the environment variable in your S3 path
aws s3 cp "../test-screenshots" s3://swapps-builds/Selenium_DaaS_Automation/staging/Test_Results/TestWay/$CURRENT_TIMESTAMP/ --recursive
aws s3 cp "../test-Email" s3://swapps-builds/Selenium_DaaS_Automation/staging/Test_Results/TestWay/$CURRENT_TIMESTAMP/ --recursive
aws s3 cp "../test-report" s3://swapps-builds/Selenium_DaaS_Automation/staging/Test_Results/TestWay/$CURRENT_TIMESTAMP/ --recursive
