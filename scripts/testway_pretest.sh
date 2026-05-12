#!/bin/bash

# Disable MSYS path rewriting (CRITICAL for AWS secret keys starting with `C/...`)
export MSYS2_ARG_CONV_EXCL="*"

# Define your role ARN and session name
ROLE_ARN="arn:aws:iam::434851320724:role/wex-qa-automation-role"
SESSION_NAME="ProductionMonitoring_$(date +%Y%m%d%H%M%S)"
EXTERNAL_ID="UM03UqItydclientUwiOY914bx"
DURATION_SECONDS=3600  # 1 hour
PROFILE_NAME="pretest_profile"

# Critical: Unset environment credentials
unset AWS_ACCESS_KEY_ID AWS_SECRET_ACCESS_KEY AWS_SESSION_TOKEN

# Use AWS STS to assume the role and get temporary credentials
response=$(aws sts assume-role --role-arn $ROLE_ARN --role-session-name $SESSION_NAME --duration-seconds $DURATION_SECONDS --profile "testway" --external-id "UM03UqItydclientUwiOY914bx")


# Extract the credentials from the response using jq
AWS_ACCESS_KEY_ID=$(echo $response | jq -r '.Credentials.AccessKeyId')
AWS_SECRET_ACCESS_KEY=$(echo $response | jq -r '.Credentials.SecretAccessKey')
AWS_SESSION_TOKEN=$(echo $response | jq -r '.Credentials.SessionToken')

# Set the temporary credentials in the AWS CLI configuration
aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID --profile $PROFILE_NAME
aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY --profile $PROFILE_NAME
aws configure set aws_session_token $AWS_SESSION_TOKEN --profile $PROFILE_NAME

echo "Temporary AWS CLI credentials have been configured successfully."

# Optional: Verify the temporary configuration
echo "Configured AWS Access Key ID: $(aws configure get aws_access_key_id --profile $PROFILE_NAME)"
echo "Configured AWS Secret Access Key: $(aws configure get aws_secret_access_key --profile $PROFILE_NAME)"
echo "Configured AWS Session Token: $(aws configure get aws_session_token --profile $PROFILE_NAME)"

aws s3 cp s3://swapps-builds/Selenium_DaaS_Automation/production/credentials/ "../credentials/" --recursive --profile $PROFILE_NAME
