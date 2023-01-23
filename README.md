<p align="center">
  <img src="https://user-images.githubusercontent.com/102715674/209995213-5f5c3715-3a51-4c86-86cb-97c372a3ffb4.png" alt=""/>
</p> 
<p dir="auto" align="center">
  <img align="center" src="https://img.shields.io/badge/Pico--CLI-Library-red?style=for-the-badge" alt="">&emsp;<img align="center" src="https://img.shields.io/badge/Java-Language-orange?style=for-the-badge" alt="">&emsp;<img align="center" src="https://img.shields.io/badge/Maven-Build_Tool-darkgreen?style=for-the-badge" alt=""></p>

#

### About
<p>Tasker is a robust command line tool designed for efficient and descriptive task management. It allows users to add, set, print, and delete tasks through the command line, as well as change a variety of other attributes. Tasker utilizes picocli for its interface, ensuring that it will have fast performance and adhere to standard git-style terminal command structure for simplicity. Type <code>--help</code> or <code>--helpall</code> for a list of commands.</p>

### Installation

Note: Installation support (aside from a runnable jar) will likely not come to Mac as I don't have any knowledge of MacOS or own a Mac to test it on.

##### Option 1:
##### "Install" the JAR file (Linux)
  1. Download `install.sh` from /Install/Linux_JAR
  2. Run `install.sh` without sudo
  3. Run `tasker` in the terminal (or `tasker --help` for a list of commands)
- If this doesn't work, you may need to make sure the folder is added to PATH, or added to your ~/.bashrc file as an alias 
  - You can do this by running `echo "alias tasker='java -jar /home/$USER/.Tasker/tasker'" >> ~/.bashrc` in the terminal

##### "Install" the JAR file (Windows)
  1. Download `install.ps1` from /Install/Windows_JAR
  2. Run `install.ps1` without admin privileges
  3. Run `.\tasker` in the terminal (or `.\tasker --help` for a list of commands)
- If this doesn't work, you may need to make sure the folder is added to PATH 
  - You can do this by running `setx PATH "%PATH%;C:\Users\%USERNAME%\.Tasker"` in the terminal
<hr>

##### Option 2: Compile the native image 
##### SUPPORT COMING SOON!!!
  1. Download it from <a href="https://github.com/SpecialistSteak/TaskerCLI/archive/refs/heads/master.zip">here</a>
  2. Unzip and open with the IDE of your choice
  3. Follow these instructions to create the native image for your device: <a href="https://www.javacodegeeks.com/2018/11/picocli-graalvm-fast-command-apps.html">here</a>

<h2><p align="center"><code>Thanks! Enjoy TaskerCLI!</p></code></h2>
