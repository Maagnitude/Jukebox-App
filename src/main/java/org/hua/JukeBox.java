package org.hua;

import java.io.*;
import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import gr.hua.dit.oop2.musicplayer.PlayerFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class JukeBox{

    public static void main (String[] args) throws IOException {
        final Logger logger = LogManager.getLogger(JukeBox.class);
        CommandLine cmd = new CommandLine();
        File path = new File(args[0]);
        String parent;

        // To take care of the difference in Linux-Windows paths.
        if (path.toString().contains("/")) {
            parent = path.getCanonicalPath().substring(0, path.getCanonicalPath().lastIndexOf("/")) + "/";   // if the argument has a linux-like path, parent holds the path to the file
        } else if (path.toString().contains(":")) {
            parent = path.getCanonicalPath().substring(0, path.getCanonicalPath().lastIndexOf("\\")) + "\\"; // if the argument has a Windows-like path, parent holds the path to the file
        } else {
            parent = "";
        }
        Player p =  PlayerFactory.getPlayer();

        try {
            if (args.length == 2) {
                cmd.choice(args[0], args[1], p, parent);
            } else {
                if (!args[0].endsWith(".m3u")) {
                    cmd.choice(args[0], "mp3Order", p, parent);
                } else {
                    cmd.choice(args[0], "m3uOrder", p, parent);
                }
            }
        } catch (FileNotFoundException e) {                                     // catches a file that doesn't exist in the path given.
            //System.err.println("File " + args[0] +" not found.\n");

            if (args[0].contains("/")) {
                //System.err.println("Please check that you give the correct path, " +
                        //"or that there is no typo in the path given.\n");
                logger.error("Error Message Logged !!!", new FileNotFoundException("Please check that you give the correct path, " +
                        "or that there is no typo in the path given.\n"));
            } else if (args[0].contains("mp3")) {
                //System.err.println("Please check that '" + args[0] + "' is in the same directory with the jar file, " +
                       // "or that there is no typo in the file given.\n");
                logger.error("Error Message Logged !!!", new FileNotFoundException("Please check that '" + args[0] + "' is in the same directory with the jar file, " +
                        "or that there is no typo in the file given.\n"));
            } else if (args[0].contains("m3u")) {
                //System.err.println("Please check that '" + args[0] + "' is in the same directory with the jar file, " +
                        //"or that there is no typo in the file given.\n");
                logger.error("Error Message Logged !!!", new FileNotFoundException("Please check that '" + args[0] + "' is in the same directory with the jar file, " +
                        "or that there is no typo in the file given.\n"));
            } else {
                //System.err.println("Please check that there is not typo in '" + args[0] + "'.\n");
                logger.error("Error Message Logged !!!", new FileNotFoundException("Please check that there is not typo in '" + args[0] + "'.\n"));
            }
        } catch (PlayerException e) {                                                       // catches a problem caused by the player. Maybe a format that the player doesn't support.
            logger.error("Error Message Logged !!!", new PlayerException("Something's wrong with the player: " + e.getMessage()));
        } catch (NullPointerException e) {                                                  // catches a directory that doesn't exist. It only works if there is not file extension
            logger.error("The path you entered has a typo. Please fix it.\n");
            logger.error("Error Message Logged !!!", new NullPointerException("NullError"));// because it sees that as a directory.
        } finally {
            if (p != null){
                logger.info("Closing the player");
                p.close();}
        }
    }
}
