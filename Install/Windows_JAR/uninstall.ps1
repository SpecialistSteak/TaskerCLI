#!/bin/bash

Remove-Item "C:\Users\$env:USERNAME\.Tasker\TaskerCLI.jar" -Force
Write-Output "TaskerCLI.jar has been uninstalled."