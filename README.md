# Jukebox-App

This is a simple terminal based Jukebox implementation, for our first assignment in Object-Oriented Programming II using Java ( 3rd semester at Harokopio University )

It only works with mp3 and m3u files, and supports the following functionalities:

## Make sure that you've change directory to the project directory
```bash
cd <path-to-project-directory>
```
## Compile the project and create the jar file using:
```bash
mvn package
```
Now the target folder has been created. Inside it there is a jar file named 'JukeBox-1.0-SNAPSHOT.jar'

Let's stay in this directory and use the relative path to the jat file 'target/JukeBox-1.0-SNAPSHOT.jar' in every example.

Also, we will use the test mp3 files in the folder given inside the project directory. 

NOTE: The commands below, work with both absolute and relative paths (for the files given)

## 1. Playing a song to its end
```bash
java -jar target/JukeBox-1.0-SNAPSHOT.jar Songlist/test2.mp3 
```
If you want to give a different song, from a different directory, provide the absolute or relative path to that directory, along with the filename.

```bash
java -jar target/JukeBox-1.0-SNAPSHOT.jar <path-to-song-directory>/testsong.mp3 
```

It can be stopped only by using Ctrl+C or Ctrl+Z.

## 2. Playing a playlist of mp3, created from a directory given
```bash
java -jar target/JukeBox-1.0-SNAPSHOT.jar Songlist
```
or
```bash
java -jar target/JukeBox-1.0-SNAPSHOT.jar Songlist order
```

The user gives the path to the directory and a playlist is being made containing only mp3 files. Then the playlist is being played, and the program stops when the playlist is finished. (every song in it, has been played once) Also, there is a non-mp3 file list created, and printed on the screen.

## 3. Playing a song in loop (Repeat one)
```bash
java -jar target/JukeBox-1.0-SNAPSHOT.jar Songlist/test2.mp3 loop
```
or
```bash
java -jar target/JukeBox-1.0-SNAPSHOT.jar <path-to-song-directory>/testsong.mp3 loop
```

It repeatedly plays a song, and stops only with the use of Ctrl+C or Ctrl+Z

## 4. Playing a playlist created from an m3u file
```bash
java -jar target/JukeBox-1.0-SNAPSHOT.jar Songlist/playlist.m3u
```
or
```bash
java -jar target/JukeBox-1.0-SNAPSHOT.jar Songlist/playlist.m3u order
```

It reads the m3u file, finds the songs (mp3 files) in it and their paths, creates a playlist of them, and plays them in order. It stops when every one of them has been played once.

## 5. Playing a shuffled playlist created from an m3u file
```bash
java -jar target/JukeBox-1.0-SNAPSHOT.jar Songlist/playlist.m3u random
```
It reads the m3u file, finds the songs (mp3 files) in it and their paths, creates a playlist of them, shuffles it and plays them in random. It stops when every one of them has been played once.

The first time you run the program, a log file is created 'app.log', that stores all the logging.
Loggings are also printed on screen. 

There will also be a windowed application. Stay tuned!
