<h2>TaskerCLI</h2>
<p align="center">
  <img src="https://user-images.githubusercontent.com/102715674/209995213-5f5c3715-3a51-4c86-86cb-97c372a3ffb4.png"/>
</p> 
<p dir="auto" align="center">
  <img align="center" src="https://img.shields.io/badge/Pico--CLI-Library-red?style=for-the-badge">&emsp;<img align="center" src="https://img.shields.io/badge/Java-Language-orange?style=for-the-badge">&emsp;<img align="center" src="https://img.shields.io/badge/Maven-Tool-darkgreen?style=for-the-badge"></p>
<br></br>

### About
Tasker is a command line tool designed for efficient task management. Its simple design allows users to easily add, set, print, and delete tasks through the command line. Tasker utilizes picocli for its interface, ensuring fast performance and adherence to standard git-style terminal command structure.

### Installation

##### Option 1: Run the jar file
  1. Download the jar file named "_TaskerCLI_-3.01-shaded.jar"
  2. Open your terminal and type ```java -jar /path/to/_TaskerCLI_-3.01-shaded.jar <command> <subcommand>```
  3. To make it more convenient, on linux type ```alias tasker="java -jar /path/to/_TaskerCLI_-3.01-shaded.jar"

##### Option 2: Compile the native image
  1. Download it from <a href="https://github.com/SpecialistSteak/TaskerCLI/archive/refs/heads/master.zip">here</a>
  2. Unzip and open with the IDE of your choice
  3. Follow these instructions to create the native image for your device: <a href="https://www.javacodegeeks.com/2018/11/picocli-graalvm-fast-command-apps.html">here</a>

#### New Additions:
  1. A variety of bugfixes to previous versions
  2. New support for commands and subcommands
  3. A more streamlined interface
  4. An executable jar file
  5. A complete restructure to better utilize picocli subcommands
  
                                                       Thanks! Enjoy TaskerCLI!
