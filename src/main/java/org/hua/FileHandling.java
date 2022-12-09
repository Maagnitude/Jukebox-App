package org.hua;

import java.io.*;
import java.util.ArrayList;
public class FileHandling {
    public boolean fileChecker (String filename) {
        if(filename.endsWith(".mp3")) {
            return true;
        }
        return false;
    }

    public void openerM3u (String filename, ArrayList<String> music) throws IOException { //Η ίδια διαδικασία για m3u
        Reader in = new FileReader(filename);
        BufferedReader in2 = new BufferedReader(in);
        int i=1;
        System.out.println("\n\tPlaylist:");
        String s = in2.readLine();
        while(s!=null) {
                if(s.contains("#")) {
                    s = in2.readLine();
                } else if (s.contains("file:")) {
                    music.add(s.substring(7));
                    System.out.println((i++) + ". " + s.substring(s.lastIndexOf("/")+1));
                    s = in2.readLine();
                } else {
                    music.add(s);
                    System.out.println((i++)+". "+s);
                    s = in2.readLine();
                }
        }
        System.out.println();
    }

    public void opener (final File folder, ArrayList<String> music) { //Ανοίγει το Directory και ας εμφανίζει τα αρχεία που υπάρχουν
        System.out.println("\n\tPlaylist:");
        int j=1;
        for (final File contents : folder.listFiles()) {
            if (contents.isDirectory()) {
                opener(contents, music);
            } else {
                if(fileChecker(contents.getName())) {
                    System.out.println((j++)+". "+contents.getName());
                    music.add(contents.getAbsolutePath().toString()); //Αν είναι mp3 βάζω το path του τραγουδιού
                }
            }
        }
    }
}
