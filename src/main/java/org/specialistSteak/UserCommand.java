package org.specialistSteak;

import picocli.CommandLine;

import java.io.IOException;
import java.util.Scanner;

import static org.specialistSteak.UserData.*;

@CommandLine.Command(
        name = "user",
        description = "Has options related to user data.",
        mixinStandardHelpOptions = true
)
public class UserCommand implements Runnable {
    @CommandLine.Option(names = {"-a", "--add"}, description = "Add a new user.")
    private boolean add;

    @CommandLine.Option(names = {"-d", "--delete"}, description = "Delete a user.")
    private Integer delete;

    @CommandLine.Option(names = {"-l", "--list"}, description = "List all users.")
    private boolean list;

    @CommandLine.Option(names = {"-s", "--set"}, description = "Set a user as the current user.")
    private boolean set;

    @CommandLine.Option(names = {"-c", "--current"}, description = "Print the current user.")
    private boolean current;

    @CommandLine.Option(names = {"-cu", "--changeusername"}, description = "Change the username of the current user.")
    private String changeUsername;

    @CommandLine.Option(names = {"-n", "--new"}, description = "Create new user data.")
    private boolean newUser;

    @CommandLine.Option(names = {"-P", "--print"}, description = "Print user data.")
    private boolean print;

    @Override
    public void run() {

        if(newUser){
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter username: ");
            String username = sc.nextLine();
            System.out.println("Enter API key: ");
            String apiKey = sc.nextLine();
            System.out.println("Enter password: ");
            String password = sc.nextLine();
            addUserData(new UserData(apiKey, username, password));
        }
        saveUserData(userData.toArray(UserData[]::new));
        if(print){
            printUserList();
        }
    }
}
