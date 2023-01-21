#!/bin/bash

$CURRENT_DIR = (Get-Location).Path
$JAR_FILE = "C:\Users\$env:USERNAME\.Tasker\TaskerCLI.jar"
$BASE_DIR = (Get-Item $MyInvocation.MyCommand.Path).Directory.FullName
Set-Location $BASE_DIR
java -jar $JAR_FILE $args
Set-Location $CURRENT_DIR