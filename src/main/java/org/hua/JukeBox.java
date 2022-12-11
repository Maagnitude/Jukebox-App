package org.hua;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import gr.hua.dit.oop2.musicplayer.PlayerFactory;
public class JukeBox{

    public static void main (String[] args) throws IOException {
        CommandLine cmd = new CommandLine();

            File path = new File(args[0]);
            String parent = path.getCanonicalPath().substring(0, path.getCanonicalPath().lastIndexOf("/"))+"/";


        System.out.println("Parent is: " + parent);

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
        }catch (FileNotFoundException e) {
            System.err.println("File " +args[0]+" not found");
        } catch (PlayerException e) {
            System.err.println("Something's wrong with the player: " + e.getMessage());
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedTagException e) {
            throw new RuntimeException(e);
        } finally {
            if (p != null)
                p.close();
        }
    }
}
