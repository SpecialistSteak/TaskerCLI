package org.specialistSteak;

import picocli.CommandLine;

import java.io.Console;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import static org.specialistSteak.UserData.*;

@CommandLine.Command(
        name = "user",
        description = "Has options related to user data.",
        mixinStandardHelpOptions = true
)
public class UserCommand implements Runnable {
    @CommandLine.Option(names = {"-d", "--delete"}, description = "Delete a user.")
    private Integer delete;

    @CommandLine.Option(names = {"-c", "--current"}, description = "Print the current user.")
    private boolean current;

    @CommandLine.Option(names = {"-cu", "--changeusername"}, description = "Change the username of the current user.")
    private String changeUsername;

    @CommandLine.Option(names = {"-n", "--new"}, description = "Create new user data.")
    private boolean newUser;
    @CommandLine.Option(names = {"-nc", "--newcurrent"}, description = "Create new user data and set to current user.")
    private boolean newCurrent;

    @CommandLine.Option(names = {"-P", "--print"}, description = "Print all usernames.")
    private boolean print;
    @CommandLine.Option(names = {"-Pc", "--printcurrent"}, description = "Print current user's data.")
    private boolean printCurrent;

    @CommandLine.Option(names = {"-l", "--login"}, description = "Login to a user.")
    private String login;

    @CommandLine.Option(names = {"-lo", "--logout"}, description = "Logout of a user.")
    private boolean logout;

    @Override
    public void run() {
        try {
            loadAllUserData();
        } catch (Exception e) {
            System.out.println("Error loading user data. Generating files...");
            try{
                fileGen();
            } catch (Exception ex) {
                System.out.println("Error generating files. Printing stack trace...");
                e.printStackTrace();
                ex.printStackTrace();
            }
        }
        if(login != null){
            try {
                login(login);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(logout) {
            if (lastUserData != null) {
                System.out.println("Logging out...");
                try {
                    logout();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("User not logged in, logout failed.");
            }
        }
        if(delete != null){
            userData.remove(delete.intValue());
        }
        if(changeUsername != null) {
            for(UserData user : userData){
                if(user.isLastUsed()){
                    user.setUsername(changeUsername);
                    lastUserData.setUsername(changeUsername);
                }
            }
        }
        if(newUser || newCurrent){
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter username: ");
            String username = sc.nextLine();
            System.out.println("Enter API key: ");
            String apiKey = sc.nextLine();
            System.out.println("Enter password (press 'enter' for no password): ");
            String password = sc.nextLine();
            if(password.equals("")){
                password = null;
            }
            UserData newUser = new UserData(apiKey, username, password, newCurrent);
            boolean usernameExists = true;
            while (usernameExists) {
                if(checkUserExist(newUser.getUsername())) {
                    System.out.println("User already exists, please enter a new username: ");
                    newUser.setUsername(sc.nextLine());
                } else {
                    usernameExists = false;
                }
            }
            addUserData(newUser);
            if (newCurrent) {
                try {
                    lastUsed(userData.size() - 1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        saveUserData(userData.toArray(UserData[]::new));
        //the '&& !logout' is to make sure that the logout() method works correctly and isn't rewritten by the saveLastUserData() method
        if(getLastUsed() != null && !logout){
            saveLastUserData(getLastUsed());
        }
        if(current){
            System.out.println("Current user: " + lastUserData.getUsername() + "\n");
        }
        if(print){
            printUserList();
        }
        if(printCurrent){
            System.out.println(lastUserData);
        }
    }
}