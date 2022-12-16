package org.hua;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

public class FileHandling {
    public boolean fileChecker (String filename) {
        LogHandler.writeToLogNoThread(Level.INFO,"Checking if the file is mp3 file");
        return filename.endsWith(".mp3");
    }
    public void openerM3u (String filename, ArrayList<String> music) throws IOException { //Η ίδια διαδικασία για m3u
        LogHandler.writeToLogNoThread(Level.INFO,"Opening Reader to read the m3u file");
        Reader in = new FileReader(filename);
        LogHandler.writeToLogNoThread(Level.INFO,"Opening BufferReader to read the Reader");
        BufferedReader in2 = new BufferedReader(in);
        int i=1;
        System.out.println("\n\tPlaylist:");
        LogHandler.writeToLogNoThread(Level.INFO,"Reading first line of the m3u");
        String s = in2.readLine();
        while(s!=null) {
            if(s.contains("#")) {
                s = in2.readLine();
            } else if (s.contains("file:")) {
                LogHandler.writeToLogNoThread(Level.INFO,"Adding music to the List of files that m3u has");
                music.add(s.substring(7));
                System.out.println((i++) + ". " + s.substring(s.lastIndexOf("/") + 1));
                LogHandler.writeToLogNoThread(Level.INFO,"Reading the next line of m3u file");
                s = in2.readLine();
            } else if (s.contains("\\")) {
                LogHandler.writeToLogNoThread(Level.INFO,"Adding music to the List of files that m3u has");
                music.add(s);
                System.out.println((i++) + ". " + s.substring(s.lastIndexOf("\\") + 1));
                LogHandler.writeToLogNoThread(Level.INFO,"Reading the next line of m3u file");
                s = in2.readLine();
            } else {
                LogHandler.writeToLogNoThread(Level.INFO,"Adding music to the List of music that m3u has");
                music.add(s);
                System.out.println((i++) + ". " + s);
                LogHandler.writeToLogNoThread(Level.INFO,"Reading the next line of m3u file");
                s = in2.readLine();
            }
        }
        System.out.println();
    }

    public void opener (final File folder, ArrayList<String> music, ArrayList<String> notmp3) { //Ανοίγει το Directory και ας εμφανίζει τα αρχεία που υπάρχουν

        for (final File contents : Objects.requireNonNull(folder.listFiles())) {
            if (contents.isDirectory()) {
                LogHandler.writeToLogNoThread(Level.INFO,"The path is directory, and now the function will open the directory to find files");
                opener(contents, music, notmp3);
            } else {
                LogHandler.writeToLogNoThread(Level.INFO,"Reading the next music at the List of music");
                if (fileChecker(contents.getName())) {
                    LogHandler.writeToLogNoThread(Level.INFO,"Adding the mp3 file string at the list of");
                    music.add(contents.getAbsolutePath().toString()); //Αν είναι mp3 βάζω το path του τραγουδιού
                } else {
                    LogHandler.writeToLogNoThread(Level.INFO,"Adding non mp3 file at the list with not mp3 files");
                    notmp3.add(contents.getName());
                }
            }
        }
        if (notmp3.size() == 0) {
            System.out.println("All the files are in mp3 format\n");
        } else {
            LogHandler.writeToLogNoThread(Level.INFO,"We're printing the files that arent mp3");
            System.out.println("Ignored files: ");
            for (int i = 0; i < notmp3.size(); i++) {
                System.out.println((i + 1) + ". " + notmp3.get(i));
            }
            System.out.println();
        }
        LogHandler.writeToLogNoThread(Level.INFO,"We're printing the files that are mp3");
        System.out.println("\n\tPlaylist:");
        for (int i = 0; i < music.size(); i++) {
            System.out.println((i + 1) + ". " + music.get(i).substring(music.get(i).lastIndexOf("/") + 1));
        }
    }
}