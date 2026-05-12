#!/bin/bash

# Function to install Go for Linux
install_go_linux() {
    echo "Installing Go for Linux..."
    wget https://golang.org/dl/go1.21.1.linux-amd64.tar.gz
    sudo tar -C /usr/local -xzf go1.21.1.linux-amd64.tar.gz
    echo "export PATH=\$PATH:/usr/local/go/bin" >> ~/.profile
    source ~/.profile
    rm go1.21.1.linux-amd64.tar.gz
    echo "Go installed successfully on Linux."
}

# Function to install Go for Windows
install_go_windows() {
    echo "Installing Go for Windows..."
    GOLANG_URL="https://golang.org/dl/go1.21.1.windows-amd64.msi"
    TEMP_FILE="golang.msi"

    # Download the Golang MSI installer
    curl -o $TEMP_FILE $GOLANG_URL
    if [ $? -ne 0 ]; then
        echo "Failed to download Golang installer for Windows."
        exit 1
    fi

    # Install Golang using msiexec
    echo "Installing Golang MSI..."
    msiexec /i $TEMP_FILE /quiet /norestart
    if [ $? -ne 0 ]; then
        echo "Failed to install Golang on Windows."
        exit 1
    fi

    # Cleanup
    rm $TEMP_FILE
    echo "Go installed successfully on Windows."
}

# Check the operating system and install Go if not already installed
install_go() {
    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
        if ! command -v go &> /dev/null; then
            install_go_linux
        else
            echo "Go is already installed on Linux."
        fi
    elif [[ "$OSTYPE" == "msys"* || "$OSTYPE" == "cygwin"* ]]; then
        if ! command -v go &> /dev/null; then
            install_go_windows
        else
            echo "Go is already installed on Windows."
        fi
    else
        echo "Unsupported operating system: $OSTYPE"
        exit 1
    fi
}

# Determine the script's parent directory
MODULE_PATH=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
echo "Module path set to: $MODULE_PATH"

# Navigate to the module path
cd "$MODULE_PATH" || { echo "Failed to navigate to $MODULE_PATH"; exit 1; }

# Check if the CSV file exists and delete it
CSV_FILE="enrolmentMetrics.csv"
if [ -f "$CSV_FILE" ]; then
    echo "Found existing CSV file '$CSV_FILE'. Deleting..."
    rm "$CSV_FILE"
    if [ $? -ne 0 ]; then
        echo "Failed to delete existing CSV file."
        exit 1
    fi
fi

# Check if the executable exists and delete it
EXECUTABLE="enrolmentExe"

# Run the executable and redirect output to a log file
LOG_FILE="DeviceEnrolmentLogs.txt"
echo "Running the executable and logging output to $LOG_FILE..."
./"$EXECUTABLE" > "$LOG_FILE" 2>&1

if [ $? -ne 0 ]; then
    echo "Execution of the executable failed. Check the log file for details."
    exit 1
fi

# Check if the CSV file is generated
if [ -f "$CSV_FILE" ]; then
    echo "CSV file '$CSV_FILE' has been successfully generated."
else
    echo "CSV file '$CSV_FILE' is missing. Please check the executable's logic."
    exit 1
fi

echo "Execution completed successfully. Logs can be found in $LOG_FILE."
