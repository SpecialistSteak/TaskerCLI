#!/usr/bin/env pwsh

$CurrentDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
$task = $args[0..($args.Count-1)] -join " "
& java -jar "$env:USERPROFILE\.Tasker\TaskerCLI.jar" $task
Set-Location $CurrentDir
