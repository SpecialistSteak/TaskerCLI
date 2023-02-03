package org.specialistSteak;

import picocli.CommandLine;
import picocli.CommandLine.Help.Ansi;

import java.io.IOException;
import java.util.Scanner;

import static org.specialistSteak.dataType.UserData.login;
import static org.specialistSteak.utils.CleanString.cleanString;
import static org.specialistSteak.utils.ErrorStringifer.errorMessager;

@CommandLine.Command(
        version = "3.26",
        mixinStandardHelpOptions = true,
        subcommands = {
                AddCommand.class,
                SetCommand.class,
                PrintCommand.class,
                DeleteCommand.class,
                UserCommand.class,
                DocsCommand.class
        }
)
public class TaskerCommand implements Runnable {
    //show all help options
    @CommandLine.Option(names = {"-ha", "--helpall"}, description = "Show help for all commands and exit.")
    private boolean helpAll;

    @CommandLine.Option(names = {"-l", "--login"}, description = "Login to a user.")
    private boolean login;

    public static void main(String[] args) {
        new CommandLine(new TaskerCommand()).execute(args);
    }

    @Override
    public void run() {
        String uname;
        try {
            if (login) {
                System.out.println("Enter username: ");
                Scanner scan = new Scanner(System.in);
                uname = scan.nextLine();
                cleanString(uname);
                login(uname);
            }
        } catch (IOException e) {
            errorMessager(e);
            System.exit(1);
        }
        if (helpAll) {
            System.out.println("""
                    NOTE: Everything in <> is a required parameter. If there are no <> then nothing is required.
                    Some commands should not be used together. For example, you cannot use --completeall and --incompleteall together.
                                        
                    1. Create a new user account (and login to it automatically):
                      tasker user --newcurrent
                                        
                    2. Add a new task:
                      tasker add "<Taskname>" --ansi <COLOR_VALUE> --complete --completestatus <completed>\s
                        --priority <PRIORITY_VALUE> --print --version --help
                                        
                    3. Set new details for an already made task:
                      tasker set <task_number> --ansi <COLOR_VALUE> --complete --completestatus <completed>\s
                        --completeall --incompleteall --description "<task description>" --priority <PRIORITY_VALUE>\s
                        --priorityall <PRIORITY_VALUE> --print --version --help
                                        
                    4. Print all tasks:
                      tasker print --filter --order --search "<Searchterm>" --PERMANENT --version --help
                                        
                    5. Delete a task:
                      tasker delete --all --complete --incomplete --search "<Searchterm>" --index <task_number>\s
                        --version --help
                                        
                    6. Login to a user:
                      tasker user --current --changeusername "<username>" --delete "<username>" --new --newcurrent\s
                        --print --printcurrent --login --logout --login-print --version --help
                                        
                    7. Open the JavaDoc documentation:
                      tasker docs --version --help       \s
                    """);
        } else {
            System.out.println(Ansi.AUTO.string(("""
                    @|yellow #######    #     #####  #    # ####### ######         #####  #       ###|@
                    @|yellow    #      # #   #     # #   #  #       #     #       #     # #        #|@
                    @|yellow    #     #   #  #       #  #   #       #     #       #       #        #|@
                    @|yellow    #    #     #  #####  ###    #####   ######  ##### #       #        #|@
                    @|yellow    #    #######       # #  #   #       #   #         #       #        #|@
                    @|yellow    #    #     # #     # #   #  #       #    #        #     # #        #|@
                    @|yellow    #    #     #  #####  #    # ####### #     #        #####  ####### ###|@
                    Version: 3.26                                    Author: SpecialistSteak
                    Welcome to Tasker-CLI!    Type Tasker --help or --helpall to get started"""
            )));
        }
    }
}