package org.specialistSteak;

import picocli.CommandLine.Help.Ansi;
import picocli.CommandLine;

@CommandLine.Command(
    version = "3.20",
    mixinStandardHelpOptions = true,
    subcommands = {
            AddCommand.class,
            SetCommand.class,
            PrintCommand.class,
            DeleteCommand.class,
    }
)
public class TaskerCommand implements Runnable {
    //show all help options
    @CommandLine.Option(names = {"-ha", "--helpall"}, description = "Show help for all commands and exit.")
    private boolean helpAll;
    public static void main(String[] args) {
        new CommandLine(new TaskerCommand()).execute(args);
    }

    @Override
    public void run() {
        if(helpAll){
            System.out.println(
            Ansi.AUTO.string("@|bold,green,underline TASKER (MAIN COMMAND)|@") +
            """ 
            \nUsage: <main class> [-hV] [-ha] [COMMAND]
              -h, --help        Show this help message and exit.
              -ha, --helpall    Show help for all commands and exit.
              -V, --version     Print version information and exit.
            Commands:
              add
              set
              print
              delete
              
            """ +
            Ansi.AUTO.string("@|bold,green,underline ADD (SUBCOMMAND)|@") +
            """
            \nUsage: <main class> add [-chPV] [-a=<ansicolor>] [-p=<priorityInteger>]
                                      <taskDescriptionString>
                    <taskDescriptionString>                                                               \s
              -a, --ansi=<ansicolor> Set new custom ansi color and exit. Supported
                                       values: RED, YELLOW, GREEN, BLUE, WHITE, BLACK
              -c, --complete    Set new task to complete and exit.
              -h, --help        Show this help message and exit.
              -p, --priority=<priorityInteger>
                                Set new custom priority and exit.
              -P, --print       Print list of tasks and exit.
              -V, --version     Print version information and exit.
              
            """ +
            Ansi.AUTO.string("@|bold,green,underline SET (SUBCOMMAND)|@") + """
            \nUsage: <main class> set [-chPV] [-CA] [-PA] [-d=<descriptionString>]
                                      [-p=<priorityInteger>]
              -a, --ansi=<ansicolor> Set new custom ansi color and exit. Supported
                                       values: RED, YELLOW, GREEN, BLUE, WHITE, BLACK
                  -aa, --ansiall=<ansiAllColor>
                                     Set all tasks to ansi color and exit. Supported
                                       values: RED, YELLOW, GREEN, BLUE, WHITE, BLACK
              -c, --complete    Set task to complete and exit.
              -CA, --completeall      \s
                                Set all tasks to complete and exit.
              -IA, --incompleteall   \s
                                Set all tasks to incomplete and exit.
              -d, --description=<descriptionString>
                                Set custom description and exit.
              -h, --help        Show this help message and exit.
              -p, --priority=<priorityInteger>
                                Set custom priority and exit.
              -P, --print       Print list of tasks and exit.
              -PA, --priorityall=<priorityAllInteger>\s
                                Set priority of all tasks and exit.
              -V, --version     Print version information and exit.
              
            """ +
            Ansi.AUTO.string("@|bold,green,underline PRINT (SUBCOMMAND)|@") +
            """
            \nUsage: <main class> print [-fhoPV] [-s=<searchTermString>]
              -f, --filter      Only show incomplete tasks and exit.
              -h, --help        Show this help message and exit.
              -o, --order       Order tasks by priority and exit.
              -P, --PERMANENT   Make changes (search, filter, order) permanent and exit.
              -s, --search=<searchTermString>
                                Show all tasks containing search term and exit.
              -V, --version     Print version information and exit.
            
            """ +
            Ansi.AUTO.string("@|bold,green,underline DELETE (SUBCOMMAND)|@") + """
            \nUsage: <main class> delete [-achPV] [-i=<indexInteger>] [-s=<searchTermString>]
              -c, --complete    Delete all complete tasks and exit.
              -ic, --incomplete
                                Delete all incomplete tasks and exit.
              -h, --help        Show this help message and exit.
              -i, --index=<indexInteger>
                                Delete task at given index and exit.
              -P, --print       Print list of tasks and exit.
                                Delete all tasks containing search term and exit.
              -V, --version     Print version information and exit.
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
            Version: 3.20                                    Author: SpecialistSteak
            Welcome to Tasker-CLI!    Type Tasker --help or --helpall to get started""")));
        }
    }
}