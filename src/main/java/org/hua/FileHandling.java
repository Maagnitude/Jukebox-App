package org.hua;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.*;
import java.util.ArrayList;
public class FileHandling {
    public boolean fileChecker(String filename) {
        if (filename.endsWith(".mp3")) {
            return true;
        }
        return false;
    }

    public void openerM3u(String filename, ArrayList<String> music) throws IOException { //Η ίδια διαδικασία για m3u
        Reader in = new FileReader(filename);
        BufferedReader in2 = new BufferedReader(in);
        int i = 1;
        System.out.println("\n\tPlaylist:");
        String s = in2.readLine();
        while (s != null) {
            if (s.contains("#")) {
                s = in2.readLine();
            } else if (s.contains("file:")) {
                music.add(s.substring(7));
                System.out.println((i++) + ". " + s.substring(s.lastIndexOf("/") + 1));
                s = in2.readLine();
            } else {
                music.add(s);
                System.out.println((i++) + ". " + s);
                s = in2.readLine();
            }
        }
        System.out.println();
    }

    public void opener(final File folder, ArrayList<String> music,ArrayList<String> notListed) { //Ανοίγει το Directory και ας εμφανίζει τα αρχεία που υπάρχουν
        System.out.println("\n\tPlaylist:");
        int j = 1;
        for (final File contents : folder.listFiles()) {
            if (contents.isDirectory()) {
                opener(contents, music,notListed);
            } else {
                if (fileChecker(contents.getName())) {
                    System.out.println((j++) + ". " + contents.getName());
                    music.add(contents.getAbsolutePath().toString()); //Αν είναι mp3 βάζω το path του τραγουδιού
                }
                notListed.add(contents.getName());
            }
        }
    }

    public void mp3Reader(String songname) throws InvalidDataException, UnsupportedTagException, IOException {
        Mp3File mp3file = new Mp3File(songname);
        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            System.out.println("Track: " + id3v2Tag.getTrack());
            System.out.println("Artist: " + id3v2Tag.getArtist());
            System.out.println("Title: " + id3v2Tag.getTitle());
            System.out.println("Album: " + id3v2Tag.getAlbum());
            System.out.println("Year: " + id3v2Tag.getYear());
            System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
            System.out.println("Comment: " + id3v2Tag.getComment());
            System.out.println("Lyrics: " + id3v2Tag.getLyrics());
            System.out.println("Composer: " + id3v2Tag.getComposer());
            System.out.println("Publisher: " + id3v2Tag.getPublisher());
            System.out.println("Original artist: " + id3v2Tag.getOriginalArtist());
            System.out.println("Album artist: " + id3v2Tag.getAlbumArtist());
            System.out.println("Copyright: " + id3v2Tag.getCopyright());
            System.out.println("URL: " + id3v2Tag.getUrl());
            System.out.println("Encoder: " + id3v2Tag.getEncoder());
            byte[] albumImageData = id3v2Tag.getAlbumImage();
            if (albumImageData != null) {
                System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
                System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
            }
        }

    }
}
