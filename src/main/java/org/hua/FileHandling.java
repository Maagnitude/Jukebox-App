package org.hua;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

public class FileHandling {
    public boolean fileChecker (String filename) {
        return filename.endsWith(".mp3");
    }
    public void openerM3u (String filename, ArrayList<String> music) throws IOException { //Η ίδια διαδικασία για m3u
        LogHandler.writeToLogNoThread(Level.INFO,"Opening Reader to read the m3u file");
        Reader in = new FileReader(filename);
        LogHandler.writeToLogNoThread(Level.INFO,"Opening BufferReader to read the Reader");
        BufferedReader in2 = new BufferedReader(in);
        int i=1;
        LogHandler.writeToLogNoThread(Level.INFO,"Adding mp3 files in m3u playlist.\n");
        System.out.println("\n\tPlaylist:");
        String s = in2.readLine();
        while(s!=null) {
            if(s.contains("#")) {
                s = in2.readLine();
            } else if (s.contains("file:")) {
                music.add(s.substring(7));
                System.out.println((i++) + ". " + s.substring(s.lastIndexOf("/") + 1));
                s = in2.readLine();
            } else if (s.contains("\\")) {
                music.add(s);
                System.out.println((i++) + ". " + s.substring(s.lastIndexOf("\\") + 1));
                s = in2.readLine();
            } else {
                music.add(s);
                System.out.println((i++) + ". " + s);
                s = in2.readLine();
            }
        }
        System.out.println();
    }

    public void opener (final File folder, ArrayList<String> music, ArrayList<String> notmp3) { //Ανοίγει το Directory και ας εμφανίζει τα αρχεία που υπάρχουν

        LogHandler.writeToLogNoThread(Level.INFO,"Adding mp3 and non-mp3 files to their respective lists.");
        for (final File contents : Objects.requireNonNull(folder.listFiles())) {
            if (contents.isDirectory()) {
                opener(contents, music, notmp3);
            } else {
                if (fileChecker(contents.getName())) {
                    music.add(contents.getAbsolutePath().toString()); //Αν είναι mp3 βάζω το path του τραγουδιού
                } else {
                    notmp3.add(contents.getName());
                }
            }
        }
        LogHandler.writeToLogNoThread(Level.INFO,"Files were checked for their format.\n");
        if (notmp3.size() == 0) {
            System.out.println("All the files are in mp3 format!\n");
        } else {
            LogHandler.writeToLogNoThread(Level.INFO,"Printing ignored files... (not mp3)\n");
            System.out.println("Ignored files: ");
            for (int i = 0; i < notmp3.size(); i++) {
                System.out.println((i + 1) + ". " + notmp3.get(i));
            }
            System.out.println();
        }
        LogHandler.writeToLogNoThread(Level.INFO,"Printing mp3 list...\n");
        System.out.println("\n\tPlaylist:");
        for (int i = 0; i < music.size(); i++) {
            System.out.println((i + 1) + ". " + music.get(i).substring(music.get(i).lastIndexOf("/") + 1));
        }
    }
}