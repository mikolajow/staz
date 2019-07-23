import Main.CLI.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main
{
    public static void main(String[] args) {
        Console console = new Console();
        console.printHelp();

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String line;
                line = reader.readLine();
                String[] commandArgs = line.split(" ");
                console.runConsole(commandArgs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

// print -ts VALID_TODAY -es VALID

    } // main
} // class






























