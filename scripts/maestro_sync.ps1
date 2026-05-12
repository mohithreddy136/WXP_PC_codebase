$ACCESS_TOKEN = 'YQE3RvJ5sisjzkFvbDZo'
$BASE_URL = 'https://maestro.hppipeline.com'

Add-Type -Assembly "System.IO.Compression.FileSystem";
$AllProtocols = [System.Net.SecurityProtocolType]'Ssl3,Tls,Tls11,Tls12'
[System.Net.ServicePointManager]::SecurityProtocol = $AllProtocols

"Maestro Script started. This script will download a set of component strings in properties format."

$request_params = @{token=$ACCESS_TOKEN; component_names="daas_ui,idm,lhreports,MPI-Incident-management-service-backend-Properties,Device-Registry-Service-Properties,MPI-Reporting-report-html-view-UI-JSON,MPI-Reporting-template-list-UI-JSON,MPI-Generic-Grid-JSON,lhserver,MPI-Generic-Graph-JSON,MPI-Reporting-LHreports_service,MPI-Incident-MicroUI-JSON,daas_reports_ui,roles_meta_service,daas_entitlement_notifier_service,Campaign-backend-properties,swbenchmark_service,dashboard_service,rpl-backend-properties,notification_service,insights_template,insights_export"}
$response = Invoke-RestMethod -Uri $BASE_URL/api/v1/export/components_as_properties -Method GET -Body $request_params

If($response.successful -eq $false) { "First API call was not successful."; Exit 1}

"First API call successful! Job created."
$jobId = $response.job_id
$status_params = @{token=$ACCESS_TOKEN}
$finished = $false

"Polling JobID: $jobId for progress, once every 10 seconds"
$wait_seconds = 0
while($finished -eq $false) {
  $response = Invoke-RestMethod -Uri $BASE_URL/api/v1/jobs/$jobId/status -Method GET -Body $status_params
  If ($response.finished -eq $true) {
    "Job Finished"
    $finished = $true
  } Else {
    $progress = $response.progress
    "Job is $progress% complete ($wait_seconds seconds so far)...."
    Start-Sleep -Second 10
    $wait_seconds += 10
  }
}

"Total job processing took: $wait_seconds seconds"


$work_dir = $PSScriptRoot+"\work_dir"
New-Item -ItemType directory -Path $work_dir -ErrorAction Ignore
# Wipe everything in the folder
Get-ChildItem -Path $work_dir -Include *.* -Recurse | foreach { $_.Delete()}

"Downloading Zip file"
$zip_save_path = "$work_dir\OUT.zip"
Invoke-WebRequest -Uri $BASE_URL/api/v1/jobs/$jobId/download -Method GET -Body $status_params -OutFile $zip_save_path

"Extract zips to working directory"
[System.IO.Compression.ZipFile]::ExtractToDirectory($zip_save_path, $work_dir)

"Delete the used zip package"
Remove-Item $zip_save_path

"Wiping Locales folder"
$locales_folder = $PSScriptRoot + "..\..\locales\"
Get-ChildItem -Path $locales_folder -Include *.* -Recurse | foreach { $_.Delete()}

Get-ChildItem -Path $work_dir -Filter *.zip | ForEach-object {
  $component_name = $_.BaseName -replace '_Properties'
  New-Item -ItemType directory -Path "$locales_folder\$component_name" -ErrorAction Ignore
  "Extracting $_ to Locales folder :: $component_name"
  [System.IO.Compression.ZipFile]::ExtractToDirectory($_.FullName, "$locales_folder\$component_name")
}

"Clean up working dir"
Get-ChildItem -Path $work_dir -Include *.* -Recurse | foreach { $_.Delete()}
