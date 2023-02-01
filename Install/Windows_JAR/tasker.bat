@echo off

set CurrentDir=%~dp0
set task=%*
cd %USERPROFILE%\.Tasker\
java -jar %USERPROFILE%\.Tasker\TaskerCLI.jar %task%
cd /d %CurrentDir%
