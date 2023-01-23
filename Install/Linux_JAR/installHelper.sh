#!/bin/bash

# May be kind of useless, but this is just to circumvent sudo so that everything is properly installed

sudo chmod +x "/home/$USER/.Tasker/tasker"
sudo mv /home/"$USER"/.Tasker/tasker /usr/local/bin
