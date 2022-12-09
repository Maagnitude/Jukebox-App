package org.hua;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import gr.hua.dit.oop2.musicplayer.PlayerFactory;
public class JukeBox{

    public static void main (String[] args) throws IOException {
        CommandLine cmd = new CommandLine();
        Path path = Paths.get(args[0]);
//        System.out.println(args[0]);
//        System.out.println(path);
//        System.out.println(path.getFileName());
//        System.out.println(path.getParent());
//        System.out.println(path.isAbsolute());
//        System.out.println(path.getRoot());
//        System.out.println(path.toAbsolutePath());
////        System.out.println(path.toRealPath());
//        path = path.relativize(path);
//        System.out.println("Relativize: " + path);
//        if (path.isAbsolute()) {
//            Path parent = path.getParent();
//        } else if (path.toString().contains(""))
//



        String onlypath = path.toString().substring(0, path.toString().lastIndexOf("/")+1); //path - without the file
        Player p =  PlayerFactory.getPlayer();

        try {
            if (args.length == 2) {
                cmd.choice(args[0], args[1], p, onlypath);
            } else {
                if (!args[0].endsWith(".m3u")) {
                    cmd.choice(args[0], "mp3Order", p, onlypath);
                } else {
                    cmd.choice(args[0], "m3uOrder", p, onlypath);
                }
            }
        }catch (FileNotFoundException e) {
            System.err.println("File " +args[0]+" not found");
        } catch (PlayerException e) {
            System.err.println("Something's wrong with the player: " + e.getMessage());
        } finally {
            if (p != null)
                p.close();
        }
    }
}