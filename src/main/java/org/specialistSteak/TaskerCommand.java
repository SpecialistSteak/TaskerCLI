package org.specialistSteak;

import picocli.CommandLine.Help.Ansi;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Scanner;

import static org.specialistSteak.utils.CleanString.cleanString;
import static org.specialistSteak.dataType.UserData.*;
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
        if(login) {
            System.out.println("Enter username: ");
            Scanner scan = new Scanner(System.in);
            uname = scan.nextLine();
            cleanString(uname);
            try {
                login(uname);
            } catch (IOException e) {
                errorMessager(e);
                System.exit(1);
            }
        }
        if(helpAll){
            System.out.println(
            Ansi.AUTO.string("@|bold,green,underline TASKER (MAIN COMMAND)|@") +
            """ 
            \nUsage: <main class> [-hlV] [-ha] [COMMAND]
                        -h, --help           Show this help message and exit.
                            -ha, --helpall   Show help for all commands and exit.
                        -l, --login          Login to a user.
                        -V, --version        Print version information and exit.
                      Commands:
                        add     Has options related to creating new tasks.
                        set     Has options related to setting new task values.
                        print   Has options related to printing tasks.
                        delete  Has options related to deleting tasks.
                        user    Has options related to user data.
                        docs    Show all project documentation.
              
            """ +
            Ansi.AUTO.string("@|bold,green,underline ADD (SUBCOMMAND)|@") +
            """
            \nUsage: <main class> add [-chPV] [-a=<ansicolor>] [-cs=<completeCompleted>]
                                              [-p=<priorityImportance>] <taskDescriptionString>
                      Has options related to creating new tasks.
                            <taskDescriptionString>
                      
                        -a, --ansi=<ansicolor>   Set new custom ansi color and exit. Supported
                                                   values: RED, YELLOW, GREEN, BLUE, WHITE, BLACK
                        -c, --complete           Set new task as complete and exit.
                            -cs, --completestatus=<completeCompleted>
                                                 Set new task's completion status and exit.
                        -h, --help               Show this help message and exit.
                        -p, --priority=<priorityImportance>
                                                 Set new custom priority and exit.
                        -P, --print              Print list of tasks and exit.
                        -V, --version            Print version information and exit.
                      
            """ +
            Ansi.AUTO.string("@|bold,green,underline SET (SUBCOMMAND)|@") + """
            \nUsage: <main class> set [-chPV] [-CA] [-IA] [-a=<ansiColor>]
                                              [-aa=<ansiAllColor>] [-cs=<completedStatusCompleted>]
                                              [-d=<descriptionString>] [-p=<priorityImportance>]
                                              [-PA=<priorityAllImportance>] <setIndex>
                      Has options related to setting new task values.
                            <setIndex>
                        -a, --ansi=<ansiColor>     Set new custom ansi color and exit. Supported
                                                     values: RED, YELLOW, GREEN, BLUE, WHITE, BLACK
                            -aa, --ansiall=<ansiAllColor>
                                                   Set all tasks to ansi color and exit. Supported
                                                     values: RED, YELLOW, GREEN, BLUE, WHITE, BLACK
                        -c, --complete             Set task to complete and exit.
                            -CA, --completeall     Set all tasks to complete and exit.
                            -cs, --completedstatus=<completedStatusCompleted>
                                                   Set task's completed status and exit.
                        -d, --description=<descriptionString>
                                                   Set custom description and exit.
                        -h, --help                 Show this help message and exit.
                            -IA, --incompleteall   Set all tasks to incomplete and exit.
                        -p, --priority=<priorityImportance>
                                                   Set custom priority and exit.
                        -P, --print                Print list of tasks and exit.
                            -PA, --priorityall=<priorityAllImportance>
                                                   Set priority of all tasks and exit.
                        -V, --version              Print version information and exit.
              
            """ +
            Ansi.AUTO.string("@|bold,green,underline PRINT (SUBCOMMAND)|@") +
            """
            \nUsage: <main class> print [-fhoPV] [-s=<searchTermString>]
                      Has options related to printing tasks.
                        -f, --filter      Only show incomplete tasks and exit.
                        -h, --help        Show this help message and exit.
                        -o, --order       Order tasks by priority and exit.
                        -P, --PERMANENT   Make changes (search, filter, order) permanent and exit.
                        -s, --search=<searchTermString>
                                          Show all tasks containing search term and exit.
                        -V, --version     Print version information and exit.
                        
            """ +
            Ansi.AUTO.string("@|bold,green,underline DELETE (SUBCOMMAND)|@") + """
            \nUsage: <main class> delete [-achPV] [-ic] [-i=<indexInteger>]
                                                 [-s=<searchTermString>]
                      Has options related to deleting tasks.
                        -a, --all               Delete all tasks and exit.
                        -c, --complete          Delete all complete tasks and exit.
                        -h, --help              Show this help message and exit.
                        -i, --index=<indexInteger>
                                                Delete task at given index and exit.
                            -ic, --incomplete   Delete all complete tasks and exit.
                        -P, --print             Print list of tasks and exit.
                        -s, --search=<searchTermString>
                                                Delete all tasks containing search term and exit.
                        -V, --version           Print version information and exit.
            """);
        }
        else {
            System.out.println(Ansi.AUTO.string(("""
            @|yellow #######    #     #####  #    # ####### ######         #####  #       ###|@
            @|yellow    #      # #   #     # #   #  #       #     #       #     # #        #|@
            @|yellow    #     #   #  #       #  #   #       #     #       #       #        #|@
            @|yellow    #    #     #  #####  ###    #####   ######  ##### #       #        #|@
            @|yellow    #    #######       # #  #   #       #   #         #       #        #|@
            @|yellow    #    #     # #     # #   #  #       #    #        #     # #        #|@
            @|yellow    #    #     #  #####  #    # ####### #     #        #####  ####### ###|@
            Version: 3.26                                    Author: SpecialistSteak
            Welcome to Tasker-CLI!    Type Tasker --help or --helpall to get started""")));
        }
    }
}