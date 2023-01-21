#!/bin/bash

Remove-Item "C:\Users\$env:USERNAME\.Tasker"
Write-Output "TaskerCLI.jar has been uninstalled."