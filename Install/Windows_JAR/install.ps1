#!/bin/bash

New-Item -ItemType directory "C:\Users\$env:USERNAME\.Tasker" -Force
New-Item -ItemType directory "C:\Users\$env:USERNAME\.Tasker\src" -Force
New-Item -ItemType directory "C:\Users\$env:USERNAME\.Tasker\src\main" -Force
New-Item -ItemType directory "C:\Users\$env:USERNAME\.Tasker\src\main\resources" -Force

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

# Download the TaskerCLI jar files
Invoke-WebRequest https://github.com/SpecialistSteak/TaskerCLI/raw/master/TaskerCLI.jar -OutFile "C:\Users\$env:username\.Tasker\TaskerCLI.jar"
Invoke-WebRequest https://raw.githubusercontent.com/SpecialistSteak/TaskerCLI/master/Install/Windows_JAR/tasker.ps1 -OutFile "C:\Users\$env:username\.Tasker\tasker.bat"

Invoke-WebRequest https://raw.githubusercontent.com/SpecialistSteak/TaskerCLI/master/Install/Windows_JAR/uninstall.ps1 -OutFile "C:\Users\$env:username\.Tasker\uninstall.bat"

Invoke-WebRequest https://github.com/SpecialistSteak/TaskerCLIJavaDoc/archive/refs/heads/main.zip -OutFile "C:\Users\$env:username\.Tasker\javadoc.zip"
Expand-Archive -Path "C:\Users\$env:USERNAME\.Tasker\javadoc.zip" -DestinationPath "C:\Users\$env:USERNAME\.Tasker"
Rename-Item "C:\Users\$env:USERNAME\.Tasker\TaskerCLIJavaDoc-main" "C:\Users\$env:USERNAME\.Tasker\javadoc"
Remove-Item "C:\Users\$env:USERNAME\.Tasker\javadoc.zip"

# Save tasker.ps1 to path
$env:Path += ";C:\Users\$env:USERNAME\.Tasker"

# Print a message to confirm the installation
Write-Output "TaskerCLI has been installed to the C:\Users\$env:USERNAME\.Tasker directory."
