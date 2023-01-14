<p align="center">
  <img src="https://user-images.githubusercontent.com/102715674/209995213-5f5c3715-3a51-4c86-86cb-97c372a3ffb4.png"/>
</p> 
<p dir="auto" align="center">
  <img align="center" src="https://img.shields.io/badge/Pico--CLI-Library-red?style=for-the-badge">&emsp;<img align="center" src="https://img.shields.io/badge/Java-Language-orange?style=for-the-badge">&emsp;<img align="center" src="https://img.shields.io/badge/Maven-Build_Tool-darkgreen?style=for-the-badge"></p>
<br></br>

### About
Tasker is a command line tool designed for efficient task management. It allows users to add, set, print, and delete tasks through the command line. Tasker utilizes picocli for its interface, ensuring that it will have fast performance and adhere to standard git-style terminal command structure for simplicity. Type ```--help``` or ```--helpall``` for a list of commands.

### Installation

##### Option 1: Run the jar file
  1. Download the jar file named "TaskerCLI_3.24.jar"
  2. Open your terminal and type ```java -jar /path/to/TaskerCLI_3.24.jar <command> <subcommand>```
  3. To make it more convenient, add the alias to your !/.bash_aliases: ```alias tasker="java -jar /path/to/TaskerCLI_3.24.jar"```
  - Note that this is only a temporary solution and will be replaced further in development

##### Option 2: Compile the native image
  1. Download it from <a href="https://github.com/SpecialistSteak/TaskerCLI/archive/refs/heads/master.zip">here</a>
  2. Unzip and open with the IDE of your choice
  3. Follow these instructions to create the native image for your device: <a href="https://www.javacodegeeks.com/2018/11/picocli-graalvm-fast-command-apps.html">here</a>

<h2><p align="center"><code>Thanks! Enjoy TaskerCLI!</p></code></h2>
