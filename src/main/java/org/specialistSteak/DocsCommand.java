package org.specialistSteak;

import picocli.CommandLine;

import java.awt.Desktop;
import java.io.File;

import static org.specialistSteak.utils.ErrorStringifer.errorMessager;

@CommandLine.Command(
        name = "docs",
        description = "Show all project documentation.",
        mixinStandardHelpOptions = true
)
public class DocsCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("Opening documentation...");
        try {
            File file = new File("javadoc/index.html");
            if(!Desktop.isDesktopSupported()) {
                System.out.println("Error: Desktop not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) desktop.open(file);
        }
        catch(Exception e) {
            System.out.println("Error loading documentation.");
            errorMessager(e);
        }
    }
}