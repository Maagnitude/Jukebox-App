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

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[37m";
    private final ArrayList<String> songs=new ArrayList<>();
    private final ArrayList<String> oldArray = new ArrayList<>();
    public ArrayList<String> music = new ArrayList<>();
    public ArrayList<String> notmp3 = new ArrayList<>();
    private InputStream file;
    public CommandLine() {}
    public void loop (Player player, String song) throws PlayerException, FileNotFoundException {
        String songname;
        int counter = 1;
        songname = song.substring(song.lastIndexOf("/") + 1);
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
                System.out.println(ANSI_RED + "Last time!" + ANSI_RESET + " I'm ending this madness, myself!");
                player.play(file);
                System.out.println("\n...but thanks for using our Media Player!\n");
                break;
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
        if (choice.equals("order") || choice.equals("mp3Order")) {
            for (String item : songs) {
                file = new FileInputStream(item);
                System.out.println("\nNow Playing: " + ANSI_GREEN + item.substring(item.lastIndexOf("/") + 1) + ANSI_RESET);
                if (counter1 == songs.size() - 1) {
                    System.out.println("You reached " + ANSI_RED + "the end of the playlist." + ANSI_RESET + "\nHope you enjoyed it!\n");
                    LogHandler.writeToLogNoThread(Level.INFO,"Playing the file");
                    player.play(file);
                    System.out.println("Thanks for using our media player!\n");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        LogHandler.writeToLogNoThread(Level.SEVERE,"RuntimeException error");
                        throw new RuntimeException(e);
                    }
                    return;
                }
                counter1++;
                System.out.println("Next song: " + songs.get(counter1).substring(songs.get(counter1).lastIndexOf("/") + 1));
                player.play(file);
                System.out.println("Next song loading...");
                try {
                    LogHandler.writeToLogNoThread(Level.INFO,"Thread is sleeping for a second");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LogHandler.writeToLogNoThread(Level.SEVERE,"RuntimeException error");
                    throw new RuntimeException(e);
                }
            }
        } else if (choice.equals("m3uOrder") || song.endsWith(".m3u")) {

            for (String loop : music) {
                if (loop.startsWith("/") || loop.startsWith("..")) {
                    file = new FileInputStream(loop);
                } else {
                    file = new FileInputStream( path + loop);
                }
                System.out.println("\nNow Playing: " + ANSI_GREEN + loop.substring(loop.lastIndexOf("/") + 1) + ANSI_RESET);
                if (counter1 == music.size() - 1) {
                    System.out.println("You reached " + ANSI_RED + "the end of the playlist." + ANSI_RESET + "\nHope you enjoyed it!\n");
                    LogHandler.writeToLogNoThread(Level.INFO,"Playing the file");
                    player.play(file);
                    System.out.println("Thanks for using our media player!\n");
                    try {
                        LogHandler.writeToLogNoThread(Level.INFO,"The thread is sleeping for a second");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        LogHandler.writeToLogNoThread(Level.SEVERE,"InterruptedException Error");
                        throw new RuntimeException(e);
                    }
                    return;
                }
                counter1++;
                System.out.println("Next song: " + music.get(counter1).substring(music.get(counter1).lastIndexOf("/") + 1));
                player.play(file);
                System.out.println("Next song loading...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void random (Player player, String path) throws PlayerException, FileNotFoundException {
        LogHandler.writeToLogNoThread(Level.INFO,"We're keeping the old playlist");
        oldArray.addAll(music);  //για να κρατήοσουμε και την παλιά playlist
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
                System.out.println("You reached the end of the playlist. Hope you enjoyed it!");
            }
            LogHandler.writeToLogNoThread(Level.INFO,"Playing the file");
            player.play(file);
        }
        LogHandler.writeToLogNoThread(Level.INFO,"Closing the file");
        player.close();
    }


    public void choice (String song, String choice, Player player, String path) throws PlayerException, IOException {
        switch (choice) {
            case "order":
                FileHandling fileHandling = new FileHandling();
                if (song.endsWith(".m3u")) {
                    LogHandler.writeToLogNoThread(Level.INFO,"Opening the m3u file");
                    fileHandling.openerM3u(song, music);
                    choice = "m3uOrder";
                    LogHandler.writeToLogNoThread(Level.INFO,"the songs will be played with order");
                    order(player, song, choice, path);
                } else {
                    final File folder = new File(song);
                    LogHandler.writeToLogNoThread(Level.INFO,"Opening the directory with mp3 songs");
                    fileHandling.opener(folder, songs, notmp3);//βαζω το path του φακελου και οχι το τραγουδι
                    LogHandler.writeToLogNoThread(Level.INFO,"the songs will be played with order");
                    order(player, song, choice, path);
                    LogHandler.writeToLogNoThread(Level.INFO,"Closing the file");
                    file.close();
                }
                break;
            case "loop":
                LogHandler.writeToLogNoThread(Level.INFO,"Looping the song that you gave");
                loop(player, song);
                break;
            case "random": {
                fileHandling = new FileHandling();
                LogHandler.writeToLogNoThread(Level.INFO,"Opening m3u file");
                fileHandling.openerM3u(song, music);
                LogHandler.writeToLogNoThread(Level.INFO,"Playing the m3u songs randomly");
                random(player, path);
                break;
            }
            case "mp3Order":
                if (song.endsWith(".mp3")) {
                    file = new FileInputStream(song);
                    System.out.println("\nNow Playing: " + ANSI_GREEN + song.substring(song.lastIndexOf("/") + 1) +
                            ANSI_RESET + " (to the end)\n\nYou can always quit, by entering " + ANSI_RED + "Ctrl+C" +
                            ANSI_RESET + " or " + ANSI_RED + "Ctrl+Z\n" + ANSI_RESET);
                    LogHandler.writeToLogNoThread(Level.INFO,"Start playing the mp3 file");
                    player.play(file);
                } else {
                    fileHandling = new FileHandling();
                    final File folder = new File(song);
                    LogHandler.writeToLogNoThread(Level.INFO,"Opening a new FileHandling");
                    fileHandling.opener(folder, songs, notmp3);//βαζω το path του φακελου και οχι το τραγουδι
                    order(player, song, choice, path);
                    LogHandler.writeToLogNoThread(Level.INFO,"Closing the file");
                    file.close();
                }
                break;
            case "m3uOrder": {
                fileHandling = new FileHandling();
                LogHandler.writeToLogNoThread(Level.INFO,"Opening the m3u file");
                fileHandling.openerM3u(song, music);
                LogHandler.writeToLogNoThread(Level.INFO,"Now the songs of m3u will be played");
                order(player, song, choice, path);
                LogHandler.writeToLogNoThread(Level.INFO,"Ending");
                break;
            }
            default:
                LogHandler.writeToLogNoThread(Level.SEVERE,"Wrong command. Exiting the program");
                System.out.println("You gave wrong command");
                System.exit(1);
        }
    }
}