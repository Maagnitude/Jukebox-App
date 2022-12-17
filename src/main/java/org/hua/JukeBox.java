package org.hua;

import java.io.*;
import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import gr.hua.dit.oop2.musicplayer.PlayerFactory;
import java.util.logging.Logger;
import java.util.logging.Level;

public class JukeBox{

    public static void main (String[] args) throws IOException, InterruptedException {

        CommandLine cmd = new CommandLine();
        try {
            String arg = args[0];       // Just to catch an exception if the user doesn't give an argument.
        } catch (ArrayIndexOutOfBoundsException o) {
            System.err.println("Please give at least one argument.");
            System.exit(1);
        }
        File path = new File(args[0]);          // getting the given path
        String parent;

        // To take care of the difference in Linux-Windows paths.
        if (path.toString().contains("/")) {
            parent = path.getCanonicalPath().substring(0, path.getCanonicalPath().lastIndexOf("/")) + "/";   // if the argument has a linux-like path, parent holds the path to the file
        } else if (path.toString().contains(":")) {
            parent = path.getCanonicalPath().substring(0, path.getCanonicalPath().lastIndexOf("\\")) + "\\"; // if the argument has a Windows-like path, parent holds the path to the file
        } else {
            parent = "";
        }
        LogHandler.writeToLogNoThread(Level.INFO,"Opening the player");
        Player p =  PlayerFactory.getPlayer();

        try {
            if (args.length == 2) {             // eg. path-to-song + loop
                cmd.choice(args[0], args[1], p, parent);
            } else {                            // only one arg. eg. path-to-song
                if (!args[0].endsWith(".m3u")) {        // mp3 file or directory
                    cmd.choice(args[0], "mp3Order", p, parent);
                } else {                                // m3u file
                    cmd.choice(args[0], "m3uOrder", p, parent);
                }
            }
        } catch (FileNotFoundException e) {                          // catches a file that doesn't exist in the path given.
            System.err.println("File " + args[0] +" not found.\n");

            if (args[0].contains("/")) {
                LogHandler.writeToLogNoThread(Level.SEVERE, "Something's wrong with the path in the argument.\n");
                System.err.println("Please check that you give the correct path, " +
                        "or that there is no typo in the path given.\n");
            } else if (args[0].contains("mp3")) {
                LogHandler.writeToLogNoThread(Level.SEVERE,"Something's wrong with the given mp3 file.\n");
                System.err.println("Please check that '" + args[0] + "' is in the same directory with the jar file, " +
                        "or that there is no typo in the file given.\n");
            } else if (args[0].contains("m3u")) {
                LogHandler.writeToLogNoThread(Level.SEVERE,"Something's wrong with the given m3u file.\n");
                System.err.println("Please check that '" + args[0] + "' is in the same directory with the jar file, " +
                        "or that there is no typo in the file given.\n");
            } else {
                LogHandler.writeToLogNoThread(Level.SEVERE,"Possible typo in the arguments.\n");
                System.err.println("Please check that there is not typo in '" + args[0] + "'.\n");
            }
        } catch (PlayerException e) {                                                       // catches a problem caused by the player. Maybe a format that the player doesn't support.
            LogHandler.writeToLogNoThread(Level.SEVERE,"Problem with the player: " + e.getMessage());
            System.err.println("Something's wrong with the player: " + e.getMessage());
        } catch (NullPointerException e) {                                                  // catches a directory that doesn't exist. It only works if there is not file extension
            LogHandler.writeToLogNoThread(Level.SEVERE,"Possible typo in the arguments.\n");
            System.err.println("The path you entered has a typo. Please fix it.\n"); // because it sees that as a directory.
        } finally {
            if (p != null){
                LogHandler.writeToLogNoThread(Level.INFO,"Closing the player");
                p.close();}
        }
    }
}
