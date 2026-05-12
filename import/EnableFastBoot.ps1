# Define registry key path for Fast Startup
$registryPath = "HKLM:\SYSTEM\CurrentControlSet\Control\Session Manager\Power"

# Enable Fast Startup by setting the value to 1
Set-ItemProperty -Path $registryPath -Name "HiberbootEnabled" -Value 1 -ErrorAction SilentlyContinue | Out-Null

# Check if Fast Startup is enabled
if ((Get-ItemProperty -Path $registryPath -Name "HiberbootEnabled" -ErrorAction SilentlyContinue).HiberbootEnabled -eq 1) {
    Write-Host "Fast Startup enabled successfully."
    [environment]::Exit(0)
} else {
    Write-Host "Failed to enable Fast Startup."
    [environment]::Exit(1)
}
