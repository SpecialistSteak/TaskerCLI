package org.specialistSteak;

import picocli.CommandLine;

@CommandLine.Command(
        version = "3.01",
        mixinStandardHelpOptions = true,
        subcommands = {
                AddCommand.class,
                SetCommand.class,
                PrintCommand.class,
                DeleteCommand.class
        }
)
public class TaskerCommand implements Runnable {
    @CommandLine.Option(names = {"-ha", "--helpall"}, description = "Show help for all commands and exit.")
    private boolean helpAll;

    public static void main(String[] args) {
        new CommandLine(new TaskerCommand()).execute(args);
    }

    @Override
    public void run() {
        if(helpAll){
            System.out.println("""
                    TASKER (MAIN COMMAND)
                    Usage: <main class> [-hV] [-ha] [COMMAND]
                      -h, --help           Show this help message and exit.
                          -ha, --helpall   Show help for all commands and exit.
                      -V, --version        Print version information and exit.
                    Commands:
                      add
                      set
                      print
                      delete
                                        
                    ADD (SUBCOMMAND)
                    Usage: <main class> add [-chPV] [-p=<priorityInteger>] <TaskDescriptionString>
                          <TaskDescriptionString>
                                        
                      -c, --complete   Set new task to complete and exit.
                      -h, --help       Show this help message and exit.
                      -p, --priority=<priorityInteger>
                                       Set new custom priority and exit.
                      -P, --print      Print list of tasks and exit.
                      -V, --version    Print version information and exit.
                                        
                    SET (SUBCOMMAND)
                    Usage: <main class> set [-chPV] [-CA] [-PA] [-d=<descriptionString>]
                                            [-p=<priorityInteger>]
                      -c, --complete           Set task to complete and exit.
                          -CA, --completeall   Set all tasks to complete and exit.
                          -IA, --incompleteall Set all tasks to incomplete and exit.
                      -d, --description=<descriptionString>
                                               Set custom description and exit.
                      -h, --help               Show this help message and exit.
                      -p, --priority=<priorityInteger>
                                               Set custom priority and exit.
                      -P, --print              Print list of tasks and exit.
                          -PA, --priorityall=<priorityAllInteger>\s
                                               Set priority of all tasks and exit.
                      -V, --version            Print version information and exit.
                                        
                    PRINT (SUBCOMMAND)
                    Usage: <main class> print [-fhoPV] [-s=<searchTermString>]
                      -f, --filter      Only show incomplete tasks and exit.
                      -h, --help        Show this help message and exit.
                      -o, --order       Order tasks by priority and exit.
                      -P, --PERMANENT   Make changes (search, filter, order) permanent and exit.
                      -s, --search=<searchTermString>
                                        Show all tasks containing search term and exit.
                      -V, --version     Print version information and exit.
                                        
                                        
                    DELETE (SUBCOMMAND)
                    Usage: <main class> delete [-achPV] [-i=<indexInteger>] [-s=<searchTermString>]
                      -a, --all        Delete all tasks and exit.
                      -c, --complete   Delete all complete tasks and exit.
                      -ic, --incomplete
                                       Delete all incomplete tasks and exit.
                      -h, --help       Show this help message and exit.
                      -i, --index=<indexInteger>
                                       Delete task at given index and exit.
                      -P, --print      Print list of tasks and exit.
                      -s, --search=<searchTermString>
                                       Delete all tasks containing search term and exit.
                      -V, --version    Print version information and exit.""");
        }
        else {
            System.out.println("""
                    #######    #     #####  #    # ####### ######         #####  #       ###
                       #      # #   #     # #   #  #       #     #       #     # #        #\s
                       #     #   #  #       #  #   #       #     #       #       #        #\s
                       #    #     #  #####  ###    #####   ######  ##### #       #        #\s
                       #    #######       # #  #   #       #   #         #       #        #\s
                       #    #     # #     # #   #  #       #    #        #     # #        #\s
                       #    #     #  #####  #    # ####### #     #        #####  ####### ###
                    Version: 3.02                                    Author: SpecialistSteak
                    Welcome to Tasker-CLI!    Type Tasker --help or --helpall to get started
                    """);
        }
    }
}