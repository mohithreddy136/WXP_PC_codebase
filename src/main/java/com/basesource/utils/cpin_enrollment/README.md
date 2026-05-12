# CPIN Enrollment Utility

This folder contains resources and scripts for automating device enrollment using CPIN (Corporate Personal Identification Number).

## Folder Contents

- **DeviceEnrolmentLogs.txt**: Log file capturing details and status of device enrollment operations.
- **enrolmentExe**: Executable or script that performs the CPIN enrollment process.
- **enrolmentMetrics.csv**: CSV file containing metrics and results from enrollment runs (e.g., success/failure rates, timing).
- **input.json**: JSON configuration file specifying parameters and settings for the enrollment process.
- **run.sh**: Shell script to execute the enrollment utility, typically used in Unix-like environments.
- **serial_numbers.csv**: CSV file listing device serial numbers to be enrolled.

## Usage

1. Prepare the `input.json` file with the required configuration.
2. List the device serial numbers in `serial_numbers.csv`.
3. Run the enrollment process using `run.sh` (or `enrolmentExe` if on Windows).
4. Monitor `DeviceEnrolmentLogs.txt` for progress and errors.
5. Review `enrolmentMetrics.csv` for summary and performance metrics.

## Notes
- Ensure all files are present and properly formatted before running the enrollment.
- For troubleshooting, consult the log file and verify input data.
- The utility may require specific permissions or environment setup depending on your system.



*Last updated: November 25, 2025*

