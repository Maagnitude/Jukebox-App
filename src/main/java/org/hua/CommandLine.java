package org.hua;

import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import java.io.FileInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class CommandLine
{

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[37m";
    private ArrayList<String> songs=new ArrayList<>();
    private ArrayList<String> oldArray = new ArrayList<>();
    public ArrayList<String> music = new ArrayList<>();
    private InputStream file;
    public CommandLine() {}
    public void loop (Player player, String song) throws PlayerException, FileNotFoundException {
        String songname;
        int counter=1;
        songname = song.substring(song.lastIndexOf("/")+1);
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
        int counter=0;
        if (choice.equals("order") || choice.equals("mp3Order")) {
            for (String loop : songs) {
                file = new FileInputStream(loop);
                System.out.println("\nNow Playing: " + ANSI_GREEN + loop.substring(loop.lastIndexOf("/")+1) + ANSI_RESET);
                if (counter == songs.size()-1) {
                    System.out.println("You reached " + ANSI_RED + "the end of the playlist." + ANSI_RESET + "\nHope you enjoyed it!\n");
                    player.play(file);
                    System.out.println("Thanks for using our media player!\n");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                counter++;
                System.out.println("Next song: " + songs.get(counter).substring(songs.get(counter).lastIndexOf("/")+1));
                player.play(file);
                System.out.println("Next song loading...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (choice.equals("m3uOrder") || song.endsWith(".m3u")) {

            for (String loop : music) {
                if (loop.startsWith("/")) {
                    file = new FileInputStream(loop);
                } else {
                    file = new FileInputStream(path + loop);
                }
                System.out.println("\nNow Playing: " + ANSI_GREEN + loop.substring(loop.lastIndexOf("/")+1) + ANSI_RESET);
                if (counter == music.size()-1) {
                    System.out.println("You reached " + ANSI_RED + "the end of the playlist." + ANSI_RESET + "\nHope you enjoyed it!\n");
                    player.play(file);
                    System.out.println("Thanks for using our media player!\n");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                counter++;
                System.out.println("Next song: " + music.get(counter).substring(music.get(counter).lastIndexOf("/")+1));
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
        oldArray.addAll(music);  //για να κρατήοσουμε και την παλιά playlist
        Collections.shuffle(music);
        for (int z = 0; z < music.size(); z++) {
//            System.out.println("Now Playing: " + ANSI_GREEN + music.get(z) + ANSI_RESET + " (on Shuffle)");
            if (music.get(z).startsWith("/")) {
                System.out.println("Now Playing: " + ANSI_GREEN + music.get(z).substring(music.get(z).lastIndexOf("/")+1) + ANSI_RESET + " (on Shuffle)");
                file = new FileInputStream(music.get(z));
            } else {
                System.out.println("Now Playing: " + ANSI_GREEN + music.get(z) + ANSI_RESET + " (on Shuffle)");
                file = new FileInputStream(path + music.get(z));
            }
//            file = new FileInputStream(path + music.get(z));
            if (z < music.size() - 1) {
                System.out.println("Next song in playlist: " + music.get(z+1).substring(music.get(z+1).lastIndexOf("/") + 1) + "\n");
            } else {
                System.out.println("You reached the end of the playlist. Hope you enjoyed it!");
            }

            player.play(file);
        }
        player.close();
    }


    public void choice (String song, String choice, Player player, String path) throws PlayerException, IOException {
        if(choice.equals("order")){
            if (song.endsWith(".m3u")) {
                FileHandling fileHandling = new FileHandling();
                fileHandling.openerM3u(song,music);
                choice="m3uOrder";
                order(player, song, choice, path);
            } else {
                FileHandling fileHandling = new FileHandling();
                final File folder = new File(song);
                fileHandling.opener(folder, songs);//βαζω το path του φακελου και οχι το τραγουδι
                order(player, song, choice, path);
                file.close();
            }
        } else if (choice.equals("loop")) {
            loop(player,song);
        } else if (choice.equals("random")) {
            FileHandling fileHandling = new FileHandling();
            fileHandling.openerM3u(song,music);
            random(player, path);
        } else if (choice.equals("mp3Order")) {
            if (song.endsWith(".mp3")) {
                file = new FileInputStream(song);
                System.out.println("\nNow Playing: " + ANSI_GREEN + song.substring(song.lastIndexOf("/")+1) +
                        ANSI_RESET + " (to the end)\n\nYou can always quit, by entering " + ANSI_RED + "Ctrl+C" +
                        ANSI_RESET + " or "  + ANSI_RED + "Ctrl+Z\n" + ANSI_RESET);
                player.play(file);
            } else {
                FileHandling fileHandling = new FileHandling();
                final File folder = new File(song);
                fileHandling.opener(folder, songs);//βαζω το path του φακελου και οχι το τραγουδι
                order(player, song, choice, path);
                file.close();
            }
        } else if (choice.equals("m3uOrder")) {
            FileHandling fileHandling = new FileHandling();
            final File folder = new File(song);
            fileHandling.openerM3u(song,music);
            order(player, song, choice, path);
        } else {
            System.out.println("You gave wrong command");
            System.exit(1);
        }
    }
}
