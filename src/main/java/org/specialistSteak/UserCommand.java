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

    @CommandLine.Option(names = {"-s", "--set"}, description = "Set a user as the current user.")
    private boolean set;

    @CommandLine.Option(names = {"-c", "--current"}, description = "Print the current user.")
    private boolean current;

    @CommandLine.Option(names = {"-cu", "--changeusername"}, description = "Change the username of the current user.")
    private String changeUsername;

    @CommandLine.Option(names = {"-n", "--new"}, description = "Create new user data.")
    private boolean newUser;

    @CommandLine.Option(names = {"-nc", "--newcurren"}, description = "Create new user data and set to current user.")
    private boolean newCurrent;

    @CommandLine.Option(names = {"-P", "--print"}, description = "Print all user's data.")
    private boolean print;
    @CommandLine.Option(names = {"-Pc", "--printcurrent"}, description = "Print current user's data.")
    private boolean printCurrent;

    @CommandLine.Option(names = {"-l", "--login"}, description = "Login to a user.")
    private boolean login;

    @CommandLine.Option(names = {"-lo", "--logout"}, description = "Logout of a user.")
    private boolean logout;

    @Override
    public void run() {
        try {
            loadAllUserData();
        } catch (Exception e) {
            try{
                fileGen();
            } catch (Exception ex) {
                e.printStackTrace();
                ex.printStackTrace();
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
            Console console = System.console();
            boolean b = true;
            System.out.println("Enter password: ");
            String password = sc.nextLine();
            for(int i = 0; i < userData.size(); i++){

            }
            addUserData(new UserData(apiKey, username, password, newCurrent));
            if (newCurrent) {
                try {
                    lastUsed(userData.size() - 1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(set){
            Scanner sc = new Scanner(System.in);
            printUserList();
            System.out.println("Enter index of user to set as current user: ");
            int index = sc.nextInt();
            //make a newline
            System.out.println();
            saveLastUserData(userData.get(index));
            try {
                lastUsed(index);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        saveUserData(userData.toArray(UserData[]::new));
        saveLastUserData(getLastUsed());
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