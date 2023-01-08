# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to Semantic Versioning.

## Upcoming changes:
#### Added
- Added support to streamline GraalVM packaging
- Added support for communicating with Microsoft To-Do
- Support for installation
#### Changed
- Performance fixes and optimizations changed
#### Fixed
- More testing

## 3.12 - 2023-01
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