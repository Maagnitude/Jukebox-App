package org.hua;

import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import java.io.FileInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

public class CommandLine
{

    public static final String ANSI_RED = "\u001B[31m";                 // To use red text color.
    public static final String ANSI_GREEN = "\u001B[32m";               // To use green text color.
    public static final String ANSI_RESET = "\u001B[37m";               // To reset to the default text color.
    private final ArrayList<String> songs=new ArrayList<>();            // Song playlist.
    private final ArrayList<String> oldArray = new ArrayList<>();       // Copy of the song playlist (before the shuffling).
    public ArrayList<String> music = new ArrayList<>();                 // Song playlist.
    public ArrayList<String> notmp3 = new ArrayList<>();                // list of non-mp3 files.
    private InputStream file;
    public CommandLine() {}
    public void loop (Player player, String song) throws PlayerException, FileNotFoundException {
        String songname;
        int counter = 1;
        songname = song.substring(song.lastIndexOf("/") + 1);           // to get the filename, from the path.
        System.out.println("\nNow playing: " + ANSI_GREEN + songname + ANSI_RESET + " (on Repeat one)\n\nYou can always " +
                "quit, by entering " + ANSI_RED + "Ctrl+C" + ANSI_RESET + " or "  + ANSI_RED + "Ctrl+Z\n" + ANSI_RESET);
        while (true){
            file = new FileInputStream(song);
            if (counter == 1) {
                System.out.println(ANSI_GREEN + (counter++) + ANSI_RESET + " time played.");
            } else if (counter == 2) {
                System.out.println(ANSI_GREEN + (counter++) + ANSI_RESET + " times played. Hmm... Just checking, right?");
            } else if (counter == 3) {
                System.out.println(ANSI_GREEN + (counter++) + ANSI_RESET + " times huh? I assume you like this song a lot.");
            } else if (counter == 4) {
                System.out.println(ANSI_GREEN + (counter++) + ANSI_RESET +  " times? Just enter " + ANSI_RED + "Ctrl+C" + ANSI_RESET + " or "  + ANSI_RED + "Ctrl+Z " + ANSI_RESET + "please!");
            } else if (counter == 5) {
                System.out.println(ANSI_GREEN + (counter++) + ANSI_RESET +  " times played. It's okay, you can keep listening to it.");
                // ORIGINAL BLOCK, BUT REMOVED TO MAKE THE SONG PLAYED FOREVER.
//                System.out.println(ANSI_RED + "Last time!" + ANSI_RESET + " I'm ending this madness, myself!");
//                player.play(file);
//                System.out.println("\n...but thanks for using our Media Player!\n");
//                break;
            } else {
                System.out.println(ANSI_GREEN + (counter++) + ANSI_RESET + " times played.");
            }
            player.play(file);
        }
    }
    public void order(Player player, String song, String choice, String path) throws PlayerException, FileNotFoundException{

        int counter1 = 0;
        System.out.println("\nYou can always quit, by entering " + ANSI_RED + "Ctrl+C" + ANSI_RESET + " or "  +
                ANSI_RED + "Ctrl+Z\n" + ANSI_RESET);
        if (choice.equals("order") || choice.equals("mp3Order")) {          // Playlist is played in order.
            for (String item : songs) {
                file = new FileInputStream(item);
                System.out.println("\nNow Playing: " + ANSI_GREEN + item.substring(item.lastIndexOf("/") + 1) + ANSI_RESET);
                if (counter1 == songs.size() - 1) {             // To print the end of the playlist.
                    System.out.println("You reached " + ANSI_RED + "the end of the playlist." + ANSI_RESET + "\nHope you enjoyed it!\n");
                    player.play(file);
                    System.out.println("Thanks for using our media player! " + ANSI_GREEN +  "Have a great day!\n" + ANSI_RESET);
                    try {
                        Thread.sleep(1000);                  // To wait a second before exiting, making it more realistic.
                    } catch (InterruptedException e) {
                        LogHandler.writeToLogNoThread(Level.SEVERE,"RuntimeException error");
                        throw new RuntimeException(e);
                    }
                    return;
                }
                counter1++;
                System.out.println("Next song: " + songs.get(counter1).substring(songs.get(counter1).lastIndexOf("/") + 1));    // To show the song's name, extracted from the path.
                player.play(file);
                System.out.println("Next song loading...");
                try {
                    Thread.sleep(1000);                     // To wait a second before playing the next one, making it more realistic.
                } catch (InterruptedException e) {
                    LogHandler.writeToLogNoThread(Level.SEVERE,"InterruptedException Error. Thread couldn't sleep.");
                    throw new RuntimeException(e);
                }
            }
        } else if (choice.equals("m3uOrder") || song.endsWith(".m3u")) {        // To play songs from m3u file, in order.

            for (String loop : music) {
                if (loop.startsWith("/") || loop.startsWith("..")) {            // To play the m3u file, given with a path
                    file = new FileInputStream(loop);
                } else {
                    file = new FileInputStream( path + loop);             // To play it, without a path given
                }
                System.out.println("\nNow Playing: " + ANSI_GREEN + loop.substring(loop.lastIndexOf("/") + 1) + ANSI_RESET);
                if (counter1 == music.size() - 1) {
                    System.out.println("You reached " + ANSI_RED + "the end of the playlist." + ANSI_RESET + "\nHope you enjoyed it!\n");
                    player.play(file);
                    System.out.println("Thanks for using our media player! " + ANSI_GREEN +  "Have a great day!\n" + ANSI_RESET);
                    try {
                        Thread.sleep(1000);                 // To wait a second before exiting, making it more realistic.
                    } catch (InterruptedException e) {
                        LogHandler.writeToLogNoThread(Level.SEVERE,"InterruptedException Error. Thread couldn't sleep.");
                        throw new RuntimeException(e);
                    }
                    return;
                }
                counter1++;
                System.out.println("Next song: " + music.get(counter1).substring(music.get(counter1).lastIndexOf("/") + 1));
                player.play(file);
                System.out.println("Next song loading...");
                try {
                    Thread.sleep(1000);                     // To wait a second before playing the next one, making it more realistic.
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void random (Player player, String path) throws PlayerException, FileNotFoundException {
        LogHandler.writeToLogNoThread(Level.INFO,"Getting a copy of the original playlist.\n");
        oldArray.addAll(music);  //για να κρατήοσουμε και την παλιά playlist
        LogHandler.writeToLogNoThread(Level.INFO,"Songs are now being shuffled...\n");
        Collections.shuffle(music);
        System.out.println("\nYou can always quit, by entering " + ANSI_RED + "Ctrl+C" + ANSI_RESET + " or "  +
                ANSI_RED + "Ctrl+Z\n" + ANSI_RESET);
        for (int z = 0; z < music.size(); z++) {
            if (music.get(z).startsWith("/")) {
                System.out.println("Now Playing: " + ANSI_GREEN + music.get(z).substring(music.get(z).lastIndexOf("/") + 1) + ANSI_RESET + " (on Shuffle)");
                file = new FileInputStream(music.get(z));
            } else {
                System.out.println("Now Playing: " + ANSI_GREEN + music.get(z) + ANSI_RESET + " (on Shuffle)");
                file = new FileInputStream(path + music.get(z));
            }
            if (z < music.size() - 1) {
                System.out.println("Next song in playlist: " + music.get(z + 1).substring(music.get(z + 1).lastIndexOf("/") + 1) + "\n");
            } else {
                System.out.println("You reached " + ANSI_RED +  "the end of the playlist" + ANSI_RESET + ". Hope you enjoyed it!");
            }
            player.play(file);
            System.out.println("Thanks for using our media player! " + ANSI_GREEN +  "Have a great day!\n" + ANSI_RESET);
        }
        LogHandler.writeToLogNoThread(Level.INFO,"Closing the file");
        player.close();
    }


    public void choice (String song, String choice, Player player, String path) throws PlayerException, IOException {
        switch (choice) {
            case "order":
                FileHandling fileHandling = new FileHandling();
                if (song.endsWith(".m3u")) {        // m3u file
                    LogHandler.writeToLogNoThread(Level.INFO,"Opening the m3u file...\n");
                    fileHandling.openerM3u(song, music);
                    choice = "m3uOrder";
                    LogHandler.writeToLogNoThread(Level.INFO,"Songs are played in order.\n");
                    order(player, song, choice, path);   // played in order.
                } else {
                    final File folder = new File(song);
                    LogHandler.writeToLogNoThread(Level.INFO,"Opening directory including mp3 files...\n");
                    fileHandling.opener(folder, songs, notmp3);//βαζω το path του φακελου και οχι το τραγουδι
                    LogHandler.writeToLogNoThread(Level.INFO,"Songs are played in order.\n");
                    order(player, song, choice, path);
                    LogHandler.writeToLogNoThread(Level.INFO,"Closing the file");
                    file.close();
                }
                break;
            case "loop":
                LogHandler.writeToLogNoThread(Level.INFO,"mp3 file loop...\n");
                loop(player, song);
                break;
            case "random":              // randomly played songs in m3u file
                fileHandling = new FileHandling();
                LogHandler.writeToLogNoThread(Level.INFO,"Opening the m3u file...\n");
                fileHandling.openerM3u(song, music);
                LogHandler.writeToLogNoThread(Level.INFO,"Songs are played randomly.\n");
                random(player, path);
                break;
            case "mp3Order":
                if (song.endsWith(".mp3")) {    // mp3 file
                    LogHandler.writeToLogNoThread(Level.INFO,"Song is played once.\n");
                    file = new FileInputStream(song);
                    System.out.println("\nNow Playing: " + ANSI_GREEN + song.substring(song.lastIndexOf("/") + 1) +
                            ANSI_RESET + " (to the end)\n\nYou can always quit, by entering " + ANSI_RED + "Ctrl+C" +
                            ANSI_RESET + " or " + ANSI_RED + "Ctrl+Z\n" + ANSI_RESET);
                    player.play(file);          // played once.
                } else {                        // directory
                    fileHandling = new FileHandling();
                    final File folder = new File(song);
                    LogHandler.writeToLogNoThread(Level.INFO,"Opening a new FileHandling");
                    fileHandling.opener(folder, songs, notmp3);
                    order(player, song, choice, path);     // played in order.
                    LogHandler.writeToLogNoThread(Level.INFO,"Closing the file");
                    file.close();
                }
                break;
            case "m3uOrder":
                fileHandling = new FileHandling();
                LogHandler.writeToLogNoThread(Level.INFO,"Opening the m3u file");
                fileHandling.openerM3u(song, music);
                LogHandler.writeToLogNoThread(Level.INFO,"Songs from m3u file, are played in order.\n");
                order(player, song, choice, path);
                break;
            default:
                LogHandler.writeToLogNoThread(Level.SEVERE,"Wrong command. Exiting the program...\n");
                System.out.println("Please check the given arguments.\n");
                System.exit(1);
        }
    }
}