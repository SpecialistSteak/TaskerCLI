# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to Semantic Versioning.

## Upcoming changes:
#### Added
- Added support for communicating with Microsoft To-Do
- Added support to streamline GraalVM packaging/support for installation
#### Changed
- Update readme.md
- Performance fixes and optimizations
#### Fixed
- Additional testing and bugfixes

## 3.25 - 2023-01
#### Added
- Added JavaDoc
- Added JavaDoc command line option
- A sh script to install the program
- A unix script to run the program
- A .ps1 script to install the program
- A .ps1 script to run the program
- Additional support for the new GraalVM packaging
- Added more error handling related methods
- Added a 'Dev Mode' to the code for easier debugging
#### Changed
- Updated README.md
- Added more graceful error handling
- Updated dependencies
- Beautified the code
#### Removed
- Removed unused dependencies
- Removed unused code
#### Fixed
- Fixed a bug where the program would crash upon trying to log in
- Fixed a bug where the program would crash upon trying to print the tasklist
- Fixed a bug where Completed would always be null
- Fixed a bug where the isCompleted method was improperly named and would not be saved to the .json file
Note: Sorry for the late update! This was a large bug-fixing and reformatting update, so it took me a little longer than usual.

### 3.24 - 2023-01
#### Added
- More subcommand options to support different Completed and Importance values
- Added support for converting Task object to Microsoft To-Do object
- Added utils for validating strings
- Added Importance enumerator
- Added Completed enumerator
- Added CompletedDate instance variable to Task object
#### Changed
- Changed priority from type int to type Importance
- Organized code into packages
#### Fixed
- An error where the program would not tell the user if the file failed to load
- An error where the program would throw an error upon printing
######
Note: This version is prone to bugs and may not work as intended. Please report any bugs you find to the GitHub repository.

### 3.23 - 2023-01
#### Added
- Added proper support for logging in and logging out
- Added date/time information upon creating a user
#### Fixed
- Fixed a bug where the application would crash when the user tried to add a task
- Fixed a bug where the user could not log in
- Fixed a bug where the lastUserData file could not be deleted
- Fixed a logic error where the program would try to make the files in the root directory of the filesystem

### 3.22 - 2023-01
#### Added
- Added proper support for multiple users using UserData class
- Support for multiple different task-lists
- Added additional information to Task class
- Added @Incomplete annotation as custom interface
- Added "--login" option to main command
#### Removed
- Removed tasks.json

### 3.21 - 2023-01
#### Added
- Added UserData class to hold user data
- Added fields to store relevant user data for Microsoft To-Do
- Added framework to support multiple task-lists
- Added @incomplete annotation to mark incomplete methods
#### Changed
- Moved all storage (.json) files to /src/main/resources

### 3.20 - 2023-01
#### Added
- Added ANSI enum with colors and styles
- Added support for ANSI colored tasks

### 3.12 - 2023-01
#### Added
- Added support for ANSI colors in main command

### 3.12 - 2023-01
#### Added
- CHANGELOG.md added

### 3.11 - 2023-01
#### Added
- Introduced the ability to save date and time information upon creating or editing a task
#### Changed
- Edited readme

### 3.10 - 2022-12
#### Added
- Added the ability to save tasks in json file format
#### Fixed
- Fixed a bug where task file would clear when using a command
- Fixed a bug where the program wouldn't save the file properly

### 3.3 - 2022-12
#### Added
- Added a save option to the print command to allow for saving the printed task list to a file

### 3.2 - 2022-12
#### Added
- Added arguments to the delete subcommand for greater control over task deletion
#### Fixed
- Fixed an error where the delete command would crash the program
- Fixed an error where the delete command wouldn't delete tasks

### 3.1 - 2022-12
#### Added
- Added readme.md
#### Changed
- Completely rebuilt the project using the picocli library for improved functionality

### 2.0 - 2022-12
#### Added
- Added a search subcommand for printing tasks

#### Changed
- Converted the project to a picocli structure for better utilization of the picocli library

### 1.5 - 2022-11
#### Added
- Added set, add, delete, and print methods
- Implemented the ability to save and read from .txt files

#### Changed
- Improved the formatting of the print method within the while loop

### 1.0 - 2022-11
#### Added
- Added functionality for setting and printing all tasks
#### Changed
- Migrated the entire program to a while loop structure

### 0.0 - 2022-09: Initial release
#### Added
- Created task class with
  - Name
  - Priority
  - Completed status
- Created a driver class to make objects