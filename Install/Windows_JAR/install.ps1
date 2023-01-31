#!/bin/bash

New-Item -ItemType directory "C:\Users\$env:USERNAME\.Tasker" -Force
New-Item -ItemType directory "C:\Users\$env:USERNAME\.Tasker\src" -Force
New-Item -ItemType directory "C:\Users\$env:USERNAME\.Tasker\src\main" -Force
New-Item -ItemType directory "C:\Users\$env:USERNAME\.Tasker\src\main\resources" -Force

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

# Download the TaskerCLI jar files
Write-Output "Downloading TaskerCLI.jar..."
Invoke-WebRequest https://raw.githubusercontent.com/SpecialistSteak/TaskerCLI/master/TaskerCLI.jar -OutFile "C:\Users\$env:username\.Tasker\TaskerCLI.jar"

Write-Output "Done. Downloading tasker.ps1..."
Invoke-WebRequest https://raw.githubusercontent.com/SpecialistSteak/TaskerCLI/master/Install/Windows_JAR/tasker.ps1 -OutFile "C:\Users\$env:username\.Tasker\tasker.ps1"

Write-Output "Done. Downloading uninstall.ps1..."
Invoke-WebRequest https://raw.githubusercontent.com/SpecialistSteak/TaskerCLI/master/Install/Windows_JAR/uninstall.ps1 -OutFile "C:\Users\$env:username\.Tasker\uninstall.ps1"

Write-Output "Done. Downloading javadoc.zip..."
Invoke-WebRequest https://github.com/SpecialistSteak/TaskerCLIJavaDoc/archive/refs/heads/main.zip -OutFile "C:\Users\$env:username\.Tasker\javadoc.zip"

Write-Output "Done. Extracting javadoc.zip..."

Expand-Archive -Path "C:\Users\$env:USERNAME\.Tasker\javadoc.zip" -DestinationPath "C:\Users\$env:USERNAME\.Tasker"
Rename-Item "C:\Users\$env:USERNAME\.Tasker\TaskerCLIJavaDoc-main" "C:\Users\$env:USERNAME\.Tasker\javadoc"
Remove-Item "C:\Users\$env:USERNAME\.Tasker\javadoc.zip"

Write-Output "Done. Adding .Tasker to PATH..."

# Get the path as a variable
$newPath = "C:\Users\$env:USERNAME\.Tasker"

# Get the current value of the user's PATH environment variable
$currentPath = [Environment]::GetEnvironmentVariable("Path", "User")

# Append the new folder to the user's PATH environment variable
$newPath = "$currentPath;$newPath"

# Check if the new value of the user's PATH environment variable has changed
if ($newPath -ne $currentPath) {
    # Set the new value of the user's PATH environment variable
    [Environment]::SetEnvironmentVariable("Path", $newPath, "User")
}
Write-Output ".Tasker has been added to your PATH."

# Print a message to confirm the installation
Write-Output "Done! TaskerCLI has been installed to the C:\Users\$env:USERNAME\.Tasker directory."
