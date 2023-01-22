#!/bin/bash

$CURRENT_DIR = (Get-Location).Path
Set-Location "C:\Users\$env:USERNAME\.Tasker"
java -jar "C:\Users\$env:USERNAME\.Tasker\TaskerCLI.jar" $args
Set-Location $CURRENT_DIR