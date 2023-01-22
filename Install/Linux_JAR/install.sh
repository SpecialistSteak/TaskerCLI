#!/bin/bash
# I don't really know bash but this should work?
# (Thanks, StackOverflow!)

# Make sure the user has unzip installed
if ! [ -x "$(command -v unzip)" ]; then
  echo "Error: unzip is not installed. Install unzip and try again"
else
  echo "unzip is installed."
fi

if [ "$USER" = "root" ]; then
  echo "Rerun this installer without sudo access."
  exit
elif [ -d "/home/$USER/.Tasker" ]; then
  echo "The .Tasker directory already exists."
  exit
else
  mkdir "/home/$USER/.Tasker"
  mkdir "/home/$USER/.Tasker/src"
  mkdir "/home/$USER/.Tasker/src/main"
  mkdir "/home/$USER/.Tasker/src/main/resources"
fi

# Download the TaskerCLI jar files
wget https://raw.githubusercontent.com/SpecialistSteak/TaskerCLI/master/TaskerCLI.jar -O "/home/$USER/.Tasker/TaskerCLI.jar"
wget https://raw.githubusercontent.com/SpecialistSteak/TaskerCLI/master/Install/Linux_JAR/tasker -O "/home/$USER/.Tasker/tasker"
chmod +x "/home/$USER/.Tasker/tasker"

wget https://raw.githubusercontent.com/SpecialistSteak/TaskerCLI/master/Install/Linux_JAR/uninstall.sh -O "/home/$USER/.Tasker/uninstall.sh"
chmod +x "/home/$USER/.Tasker/uninstall.sh"

wget https://github.com/SpecialistSteak/TaskerCLIJavaDoc/archive/refs/heads/main.zip -O "/home/$USER/.Tasker/javadoc.zip"
unzip "/home/$USER/.Tasker/javadoc.zip" -d "/home/$USER/.Tasker"
mv /home/"$USER"/.Tasker/TaskerCLIJavaDoc-main /home/"$USER"/.Tasker/javadoc
rm "/home/$USER/.Tasker/javadoc.zip"

# Print a message to confirm the installation
echo "TaskerCLI has been installed to the /home/$USER/.Tasker directory."

wget https://raw.githubusercontent.com/SpecialistSteak/TaskerCLI/master/Install/Linux_JAR/installHelper.sh -O "/home/$USER/.Tasker/installHelper.sh"
sh "/home/$USER/.Tasker/installHelper.sh"
rm "/home/$USER/.Tasker/installHelper.sh"

# Check if java version is 19
java -version 2>&1 | grep -oP 'version "?(1\.)?\K\d+' | grep -q '19'
if [ $? -ne 0 ]; then
  echo "Error: Java version is not 19. Please install java version 19."
  exit 1
fi
